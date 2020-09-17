package hr.fer.zemris.java.webserver;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PushbackInputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import hr.fer.zemris.java.custom.scripting.exec.SmartScriptEngine;
import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParser;
import hr.fer.zemris.java.webserver.RequestContext.RCCookie;

/**
 * Class that is used as a server to 
 * write different messages on a screen.
 * The browser requests are analyzed and appropriate 
 * methods and actions are then called 
 * @author Tomislav Kurtović
 *
 */
public class SmartHttpServer {

	/**
	 * main method of the server that starts it 
	 * @param args path of file with this servers configurations 
	 */
	public static void main(String[] args) {

		new SmartHttpServer(args[0]).start();
	}

	/**
	 * Used to not allow mulitiple clients to acces the sessions collection 
	 */
	private volatile boolean sessionInUse = false;
	
	/**
	 * address of the server 
	 */
	private String address;
	/**
	 * domain name of the server 
	 */
	private String domainName;
	/**
	 * port on which the server is listening on 
	 */
	private int port;
	/**
	 * number of threads that are working to execute actions 
	 */
	private int workerThreads;
	/**
	 * session of the storage of cookies 
	 */
	private int sessionTimeout;
	/**
	 * Map that holds all the mime types a {@link RequestContext} has 
	 */
	private Map<String, String> mimeTypes = new HashMap<>();
	/**
	 * Thread of sever 
	 */
	private ServerThread serverThread;
	/**
	 * pool of server thread 
	 */
	private ExecutorService threadPool;
	/**
	 * document root with all the documents 
	 */
	private Path documentRoot;
	/**
	 * Map of workers 
	 */
	private Map<String, IWebWorker> workersMap = new HashMap<>();
	/**
	 * Stores the sessions of this server 
	 */
	private Map<String, SessionMapEntry> sessions = new HashMap<>();
	/**
	 * Used to generate session id
	 */
	private Random sessionRandom = new Random();
	

	/**
	 * Constructor of the server. 
	 * It instantiates all fields by getting all the configurations from the files 
	 * and storing them in their appropriate containers. 
	 * 
	 * @param configFileName name of file that holds all other configurations 
	 */
	public SmartHttpServer(String configFileName) {
		try (InputStream is = new FileInputStream(configFileName)) {
			Properties properties = new Properties();
			properties.load(is);

			address = properties.getProperty("server.address");
			domainName = properties.getProperty("server.domainName");
			port = Integer.parseInt(properties.getProperty("server.port"));
			workerThreads = Integer.parseInt(properties.getProperty("server.workerThreads"));
			sessionTimeout = Integer.parseInt(properties.getProperty("session.timeout"));
			populateMimeTypes(properties);
			populateWorkersMap(properties);
			documentRoot = Paths.get(properties.getProperty("server.documentRoot"));

		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
	}

	/**
	 * Used to populate that map of workers this server has 
	 * @param properties properties file that hold the information 
	 */
	private void populateWorkersMap(Properties properties) {
		try (InputStream is = new FileInputStream(properties.getProperty("server.workers"))) {
			Properties workersProp = new Properties();
			workersProp.load(is);

			for(String key : workersProp.stringPropertyNames()) {
				Class<?> reference = this.getClass().getClassLoader().loadClass(workersProp.getProperty(key));
				@SuppressWarnings("deprecation")
				Object newObject = reference.newInstance();
				IWebWorker worker = (IWebWorker) newObject;
				
				workersMap.put(key, worker);
			}
		} catch (IOException e) {
			System.out.println(e.getMessage());
		} catch (ClassNotFoundException e) {
			System.out.println(e.getMessage());
		} catch (InstantiationException e) {
			System.out.println(e.getMessage());
		} catch (IllegalAccessException e) {
			System.out.println(e.getMessage());
		}
	}
	/**
	 * Used to populate that map of mime types  this server has 
	 * @param properties properties file that hold the information 
	 */
	private void populateMimeTypes(Properties properties) {
		try (InputStream is = new FileInputStream(properties.getProperty("server.mimeConfig"))) {
			Properties mimeProp = new Properties();
			mimeProp.load(is);

			for (String key : mimeProp.stringPropertyNames()) {
				mimeTypes.put(key, mimeProp.getProperty(key));
			}

		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
	}

	/**
	 * start the server 
	 */
	protected synchronized void start() {
		// start server thread if not already running
		// init threadpool by Executors.newFixedThreadPool()..
		new ServerThread().start();
		
		threadPool = Executors.newFixedThreadPool(workerThreads);
	}

	/**
	 * Stops the server 
	 */
	protected synchronized void stop() {
		// signal server thread to stop running
		// shutdown threadpool
		serverThread.setStop();
		threadPool.shutdown();
	}

	/**
	 * Class that is used by the server in order to executer all 
	 * the actions in a multithreaded environment. 
	 * @author Tomislav Kurtović
	 *
	 */
	protected class ServerThread extends Thread {
		/**
		 * flag for stopping of thread execution 
		 */
		boolean isStopped = false;

		/**
		 * Stops the thread 
		 */
		protected void setStop() {
			isStopped = true;
		}

		
		@Override
		public void run() {
			setSessionCleaner();
			
			// open serverSocket on specified port
			try {
				@SuppressWarnings("resource")
				ServerSocket serverSocket = new ServerSocket();
				serverSocket.bind(new InetSocketAddress(InetAddress.getByName(address), port));
				while (true) {
					Socket client = serverSocket.accept();
					ClientWorker cw = new ClientWorker(client);
					threadPool.submit(cw);
				}
			} catch (IOException e) {
				System.out.println(e.getMessage());
			}
		}


		/**
		 * Daemonic thread that checks if there are some
		 * timeouted sessions in the sessions collection of
		 * this server and if it finds some it removes 
		 * them from the collection 
		 */
		private void setSessionCleaner() {
			Runnable r = ()->{
				while(true) {
					try {
						sleep(300_000);
						if(!sessions.isEmpty()) {
							for(String key : sessions.keySet()) {
								SessionMapEntry entry = sessions.get(key);
								Timestamp time = new Timestamp(System.currentTimeMillis());
								if(time.getTime() > entry.validUntil) {
									sessions.remove(key);
									System.out.println("Removing timed-out session: " + key );
								}
							}
						}
					} catch (InterruptedException e) {
					}
				}
			};
			
			Thread thread = new Thread(r);
			thread.setDaemon(true);
			thread.start();
		}
	}
	/**
	 * Worker of the client that does all the work when it 
	 * comes to analyzing the sent request 
	 * @author Tomislav Kurtović
	 *
	 */
	private class ClientWorker implements Runnable, IDispatcher {
		/**
		 * socket of worker 
		 */
		private Socket cSocket;
		/**
		 * input stream of worker 
		 */
		private PushbackInputStream istream;
		/**
		 * output stream of worker 
		 */
		private OutputStream ostream;
		/**
		 * version of request 
		 */
		private String version;
		/**
		 * method 
		 */
		private String method;
		/**
		 * host of server 
		 */
		private String host;
		/**
		 * parameter map 
		 */
		private Map<String, String> params = new HashMap<>();
		/**
		 * temporary parameter map 
		 */
		private Map<String, String> tempParams = new HashMap<>();
		/**
		 * persistent parameter map 
		 */
		private Map<String, String> permParams = new HashMap<>();
		/**
		 * list of cookies this server stored  
		 */
		private List<RCCookie> outputCookies = new ArrayList<>();
		/**
		 * context used an an output of this server 
		 */
		private RequestContext context = null;
		/**
		 * session id of the user 
		 */
		private String SID;

		/**
		 * Constructor of the worker
		 * @param csocket socket of the server 
		 */
		public ClientWorker(Socket csocket) {
			super();
			this.cSocket = csocket;
		}

		@Override
		public void dispatchRequest(String urlPath) throws Exception {
			internalDispatchRequest(urlPath, false);
		}

		
		/**
		 * Used to analyze the given path in order 
		 * to act accordingly to the user specified action/s 
		 * @param urlPath path of action to execute 
		 * @param directCall flag to see if the method was called internally of not
		 * @throws Exception if something goes wrong 
		 */
		public void internalDispatchRequest(String urlPath, boolean directCall) throws Exception {
			
			if(urlPath.startsWith("/private") && directCall) {
				sendError(ostream, 404, "ERROR");
				return;
			}
			
			if(workersMap.containsKey(urlPath)) {
				if(context == null) {
					context = new RequestContext(ostream, params, permParams, outputCookies, tempParams, this, SID);
				}
				workersMap.get(urlPath).processRequest(context);	
				ostream.flush();
				return;
			}
			
			if(urlPath.startsWith("/ext")) {
				String fqcn = "hr.fer.zemris.java.webserver.workers.";
				String classToLoad = urlPath.substring(urlPath.lastIndexOf("/")+1);
				Class<?> reference = this.getClass().getClassLoader().loadClass(fqcn + classToLoad);
				@SuppressWarnings("deprecation")
				Object newObject = reference.newInstance();
				IWebWorker worker = (IWebWorker) newObject;
				if(context == null) {
					context = new RequestContext(ostream, params, permParams, outputCookies, tempParams, this, SID);
				}
				worker.processRequest(context);
				ostream.flush();
				return;
			}
			
			Path path = documentRoot.resolve(urlPath.substring(1));

			if (path.getRoot() != null && path.getRoot().equals(documentRoot)) {
				sendError(ostream, 403, "Forbidden operation");
				return;
			}

			if (!Files.exists(path) || !Files.isRegularFile(path) || !Files.isReadable(path)) {
				sendError(ostream, 404, "File not found");
				return;
			}

			String fileName = path.getFileName().toString();
			String extension = fileName.substring(fileName.lastIndexOf('.') + 1);
			
			if(extension.equals("smscr")) {
				String fileBody = Files.readString(path);
				SmartScriptParser parser = new SmartScriptParser(fileBody);
				if(context == null) {
					context = new RequestContext(ostream, params, permParams, outputCookies, tempParams, this, SID);
				}
				new SmartScriptEngine(parser.getDocumentNode(), context).execute();
				ostream.flush();
				return;
			} 

			String mimeType = mimeTypes.get(extension) == null ? "application/octet-stream" : mimeTypes.get(extension);

			if(context == null) {
				context = new RequestContext(ostream, params, permParams, outputCookies, tempParams, this, SID);
			}
			context.setMimeType(mimeType);

			serveFIle(context, path);
		}

		@Override
		public void run() {

			try {
				istream = new PushbackInputStream(cSocket.getInputStream());
				ostream = new BufferedOutputStream(cSocket.getOutputStream());

				List<String> headers = readRequest();

				if (headers == null || headers.size() < 1) {
					sendError(ostream, 400, "Bad request");
				}

				String reqPath = extractFromFirstLineAndReturnPath(headers.get(0));
				if (reqPath == null) {
					sendError(ostream, 400, "Error while getting path");
				}

				for (String h : headers) {
					if (h.contains("Host:")) {
						getHost(h);
					}
				}
				host = host == null ? domainName : host;

				checkSession(headers);
				
				String[] requestedPath = reqPath.split("\\?");

				if (requestedPath.length != 1) {
					parseParams(requestedPath[1]);
				}

				internalDispatchRequest(requestedPath[0], true);

			} catch (Exception e) {
			} finally {
				try {
					cSocket.close();
				} catch (IOException e) {
					System.out.println(e.getMessage());
				}
			}
		}

		/**
		 * Checks the request to see if the session is currently 
		 * active and if the server had a session with 
		 * the same session id in the last 10 minutes when 
		 * the session is active 
		 * @param headers header of the request 
		 */
		private void checkSession(List<String> headers) {
			String sidCandidate = null;
			
			for(String s : headers) {
				if(s.startsWith("Cookie:")) {
					s = s.replace("Cookie:", "").trim();
					String[] cookies = s.split("\\;");
					for(String cookie : cookies) {
						if(cookie.startsWith("sid")) {
							sidCandidate = cookie.split("=")[1].replaceAll("\"", "");
						}
					}
				}
			}
			
			while(sessionInUse);
			sessionInUse = true;
			checkCandidate(sidCandidate);
			sessionInUse = false;
		}

		/**
		 * Checks if session id is already stored in the 
		 * sessions collection of this server 
		 * @param sidCandidate candidate for session id 
		 */
		private synchronized void checkCandidate(String sidCandidate) {
			if(sidCandidate == null) {
				generateEntry();
				return;
			}
			SessionMapEntry entry = sessions.get(sidCandidate);
			
			if(entry.host.equals(host)) {
				Timestamp time = new Timestamp(System.currentTimeMillis());
				if(time.getTime() > entry.validUntil) {
					sessions.remove(sidCandidate);
					generateEntry();
					return;
				}
				
				entry.validUntil = time.getTime() + sessionTimeout * 1000;
				permParams = entry.map;
			} else {
				generateEntry();
			}
		}

		/**
		 * Used to generate entry that holds user info 
		 * if no entry was found in session map 
		 */
		private void generateEntry() {
			String sid = generateSessionId();
			Timestamp time = new Timestamp(System.currentTimeMillis());
			SessionMapEntry entry = new SessionMapEntry(sid, host,
					time.getTime() + sessionTimeout * 1000, new ConcurrentHashMap<>());
			sessions.put(sid, entry);
			outputCookies.add(new RCCookie("sid", entry.sid, null, host, "/", true));	
			this.SID = sid;
			
			permParams = entry.map;
		}

		/**
		 * USed to generate 20 random uppercase letters to be 
		 * used as session id of user 
		 * @return session id of user
		 */
		private String generateSessionId() {
			int firstLetter = 65; // letter A
			int lastLetter = 90; // letter Z
			int length = 20;
			StringBuilder builder = new StringBuilder(length);
			
			for(int i = 0; i < length; i++) {
				char c = (char) ((int)(sessionRandom.nextFloat() * (lastLetter - firstLetter + 1)) + firstLetter);
				builder.append(c);
			}
			
			return builder.toString();
		}

		/**
		 * Used to write information from file 
		 * to the screen in user specified way 
		 * @param rc {@link RequestContext} of the server 
		 * @param path path of the file 
		 * @throws IOException if something goes wrong while writing 
		 */
		private void serveFIle(RequestContext rc, Path path) throws IOException {
			long len = Files.size(path);
			rc.setContentLength(len);

			try (InputStream is = new BufferedInputStream(Files.newInputStream(path))) {
				byte[] buff = new byte[1024];
				while (true) {
					int r = is.read(buff);
					if (r < 1)
						break;
					rc.write(buff, 0, r);
				}
				ostream.flush();
			}
		}

		/**
		 * Used to parse the parameters the 
		 * the user sent to the server and 
		 * later use them 
		 * @param params parameters of request 
		 * @throws IOException if something goes wrong while writing file 
		 */
		private void parseParams(String params) throws IOException {
			String[] parsedParams = params.split("\\&");

			for (String s : parsedParams) {
				String[] splitted = s.split("=");
				if (splitted.length != 2) {
					sendError(ostream, 400, "Invalid requested params");
					return;
				}
				this.params.put(splitted[0], splitted[1]);
			}
		}

		/**
		 * Used to get the host from the header that 
		 * was generated from the sent request.
		 * It only remembers the part of the host line that 
		 * does not have the port number in it 
		 * @param h
		 */
		private void getHost(String h) {
			String afterHost = h.replace("Host:", "").trim();

			if (afterHost.matches(".*\\:\\d.*")) {
				afterHost = afterHost.split(":")[0];
			}

			host = afterHost;
		}

		/**
		 * Extracts the arguments from the first line of this header 
		 * @param firstLine first line of the header 
		 * @return the path that the user speciffied 
		 * @throws IOException if something goes wrong while writing file  
		 */
		private String extractFromFirstLineAndReturnPath(String firstLine) throws IOException {
			String[] extract = firstLine.split("\\s+");

			if (extract.length != 3) {
				sendError(ostream, 400, "First line of header is invalid");
				return null;
			}

			method = extract[0].equalsIgnoreCase("GET") ? "GET" : null;
			if (method == null) {
				sendError(ostream, 400, "Method not allowed");
				return null;
			}

			version = extract[2].toUpperCase();
			if (!version.equals("HTTP/1.1") && !version.equals("HTTP/1.0")) {
				sendError(ostream, 400, "HTTP version not supported");
				return null;
			}

			return extract[1];
		}

		/**
		 * Used to read the user specified request 
		 * and generate a list that represents the 
		 * header lines 
		 * @return list of header lines 
		 * @throws IOException if something goes wrong while writing  to file 
		 */
		private List<String> readRequest() throws IOException {
			List<String> list = new ArrayList<>();

			byte[] byteRequest = getByte();
			
			if(byteRequest == null) {
				sendError(ostream, 400, "Bad request");
			}
			
			String request = new String(byteRequest);

			String currLine = null;
			for (String line : request.split("\n")) {
				if (line.isBlank())
					break;
				char c = line.charAt(0);
				if (c == 9 || c == 32) {
					currLine += line;
				} else {
					if (currLine != null) {
						list.add(currLine);
					}
					currLine = line;
				}
			}
			if (!currLine.isEmpty()) {
				list.add(currLine);
			}

			return list;
		}

		/**
		 * 
		 * Gets the bytes from the request in order 
		 * to generate a header from it 
		 * @return byte array of request 
		 * @throws IOException
		 */
		private byte[] getByte() throws IOException {
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			int state = 0;

			l: while (true) {
				int b = istream.read();
				if (b == -1)
					return null;
				if (b != 13) {
					bos.write(b);
				}
				switch (state) {
				case 0:
					if (b == 13) {
						state = 1;
					} else if (b == 10)
						state = 4;
					break;
				case 1:
					if (b == 10) {
						state = 2;
					} else {
						state = 0;
					}
					break;
				case 2:
					if (b == 13) {
						state = 3;
					} else
						state = 0;
					break;
				case 3:
					if (b == 10) {
						break l;
					} else
						state = 0;
					break;
				case 4:
					if (b == 10) {
						break l;
					} else
						state = 0;
					break;
				}
			}
			
			return bos.toByteArray();
		}

		/**
		 * Used to send an error message to the user if action
		 * is not supported by the server s
		 * @param ostream output stream to write to 
		 * @param statusCode status code if something goes wrong 
		 * @param statusMessage message of error  
		 * @throws IOException if something goes wrong while writing to screen 
		 */
		protected void sendError(OutputStream ostream, int statusCode, String statusMessage) throws IOException {
			
			ostream.write(("HTTP/1.1 " + statusCode + " " + statusMessage + "\r\n" +
					"Server: My java server\r\n" +
					"Content-Type: text/plain; charset = UTF-8\r\n"+
					"Content-Length:0\r\n"
					+ "Connection: close\r\n" + "\r\n").getBytes(StandardCharsets.US_ASCII));

			ostream.flush();
		}
	}
	
	/**
	 * Class used to store user session information
	 * @author Tomislav Kurtović
	 *
	 */
	private static class SessionMapEntry {
		/**
		 * Session id of user 
		 */
		private String sid;
		/**
		 * host address 
		 */
		private String host;
		/**
		 * Session timeout 
		 */
		private long validUntil;
		/**
		 * Map that holds user data
		 */
		private Map<String, String> map;
		
		/**
		 * Constructor of the session entry
		 * @param sid Session id of user 
		 * @param host host address 
		 * @param validUntil Session timeout
		 * @param map  Map that holds user data
		 */
		public SessionMapEntry(String sid, String host, long validUntil, Map<String, String> map) {
			super();
			this.sid = sid;
			this.host = host;
			this.validUntil = validUntil;
			this.map = map;
		}
	}
}
