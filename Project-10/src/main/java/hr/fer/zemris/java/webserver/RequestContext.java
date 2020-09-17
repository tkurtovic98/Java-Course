package hr.fer.zemris.java.webserver;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * Context that is used by the {@link SmartHttpServer} 
 * in order to print output on browser screen. 
 * it hold various collections of different objects that 
 * hold information to be printed 
 * @author Tomislav Kurtović
 *
 */
public class RequestContext {

	/**
	 * Class that is used to represent the cookies that
	 * the browsers store during user sessions 
	 * @author Tomislav Kurtović
	 *
	 */
	public static class RCCookie {
		
		/**
		 * name of cookie
		 */
		private String name;
		/**
		 * value of cookie
		 */
		private String value;
		/**
		 * domain of cookie 
		 */
		private String domain;
		/**
		 * path of cookie
		 */
		private String path;
		/**
		 * session timeout value 
		 */
		private Integer maxAge;
		/**
		 * indicates wheather or not the cookie is httpOnly or not
		 */
		private boolean httpOnly;
		
		/**
		 * Constructor of the cookie 
		 * @param name  name of cookie
		 * @param value value of cookie
		 * @param maxAge session timeout value 
		 * @param domain domain of cookie 
		 * @param path path of cookie
		 * @param httpOnly indicates wheather or not the cookie is httpOnly or not
		 */
		public RCCookie(String name, String value, Integer maxAge, String domain, String path, boolean httpOnly) {
			super();
			this.name = name;
			this.value = value;
			this.domain = domain;
			this.path = path;
			this.maxAge = maxAge;
			this.httpOnly = httpOnly;
		}

		/**
		 * Gets the cookie name 
		 * @return cookie name 
		 */
		public String getName() {
			return name;
		}

		/**
		 * Gets the cookie value 
		 * @return cookie value 
		 */
		public String getValue() {
			return value;
		}
		/**
		 * Gets the cookie domain 
		 * @return cookie domain 
		 */
		public String getDomain() {
			return domain;
		}
		/**
		 * Gets the cookie path 
		 * @return cookie path 
		 */
		public String getPath() {
			return path;
		}
		/**
		 * Gets the cookie session timeout value 
		 * @return cookie session timeout value 
		 */
		public Integer getMaxAge() {
			return maxAge;
		}
		/**
		 * returns type of cookie
		 * @return
		 */
		public boolean getHttpOnly() {
			return httpOnly;
		}
	}
	/**
	 * output stream of context
	 */
	private OutputStream outputStream;
	/**
	 * charset of outputstream
	 */
	private Charset charset;
	/**
	 * encoding of outputstream, write only
	 */
	private String encoding;
	/**
	 * status code of context, write only
	 */
	private int statusCode;
	/**
	 * status text of context, write only
	 */
	private String statusText;
	/**
	 * mime type of context, write only
	 */
	private String mimeType;
	/**
	 * length of content, write only
	 */
	private Long contentLength;
	
	/**
	 * parameters of context
	 */
	private Map<String, String> parameters;
	/**
	 * temporary parameters of context
	 */
	private Map<String, String> temporaryParameters;
	/**
	 * persistent parameters of context
	 */
	private Map<String, String> persistentParameters;
	/**
	 * output cookies of context
	 */	
	private List<RCCookie> outputCookies;
	/**
	 * flag for usage if header was generated
	 */
	private boolean headerGenerated;
	
	/**
	 * dispatcher of the context, read only
	 */
	private IDispatcher dispatcher;
	
	/**
	 * Session id
	 */
	private String sid;
	
	/**
	 * Constructor of this object 
	 * @param outputStream output stream of the context, must not be null
	 * @param parameters parameters of the context, if null, it is treated as empty
	 * @param persistentParameters persistent parameters of the context, if null, it is treated as empty
	 * @param outputCookies output cookies of the context, if null, it is treated as empty
	 */
	public RequestContext(OutputStream outputStream, Map<String, String> parameters,
			Map<String, String> persistentParameters, List<RCCookie> outputCookies,String sid) {
		this(outputStream, parameters,persistentParameters, outputCookies, null, null, sid);
	}
	
	/**
	 * Constructor of this object
	 * @param outputStream output stream of the context, must not be null
	 * @param parameters parameters of the context, if null, it is treated as empty
	 * @param persistentParameters persistent parameters of the context, if null, it is treated as empty
	 * @param outputCookies output cookies of the context, if null, it is treated as empty
	 * @param temporaryParameters temporary parameters of the context
	 * @param dispatcher dispatcher of the context
	 * @param sid session id of request 
	 */
	public RequestContext (OutputStream outputStream, Map<String, String> parameters,
			Map<String, String> persistentParameters, List<RCCookie> outputCookies, 
			Map<String, String> temporaryParameters, IDispatcher dispatcher, String sid) {
		super();
		this.outputStream = Objects.requireNonNull(outputStream);
		this.parameters = parameters == null ? new HashMap<>() : parameters;
		this.persistentParameters = persistentParameters == null ? new HashMap<>() : persistentParameters;
		this.outputCookies = outputCookies == null ? new ArrayList<>() : outputCookies;
		this.temporaryParameters = temporaryParameters;
		this.dispatcher = dispatcher;
		this.sid = sid;
		encoding = "UTF-8";
		statusCode = 200;
		statusText = "OK";
		mimeType = "text/html";
		contentLength = null;
		headerGenerated = false;
		
	}
	
	/**
	 * Gets the dispatcher of this context 
	 * @return dispatcher of this context
	 */
	public IDispatcher getDispatcher() {
		return dispatcher;
	}
	
	/**
	 * Sets encoding of context, if header has already been generated, throws {@link RuntimeException}
	 * @param encoding encoding of context
	 */
	public void setEncoding(String encoding) {
		if(headerGenerated) throw new RuntimeException("Header has already been generated!");
		this.encoding = encoding;
	}

	/**
	 * Sets status code of context, if header has already been generated, throws {@link RuntimeException}
	 * @param status code status code of context
	 */
	public void setStatusCode(int statusCode) {
		if(headerGenerated) throw new RuntimeException("Header has already been generated!");
		this.statusCode = statusCode;
	}

	/**
	 * Sets status text of context, if header has already been generated, throws {@link RuntimeException}
	 * @param status text status text of context
	 */
	public void setStatusText(String statusText) {
		if(headerGenerated) throw new RuntimeException("Header has already been generated!");
		this.statusText = statusText;
	}

	/**
	 * Sets mime type of context, if header has already been generated, throws {@link RuntimeException}
	 * @param mime type mime type of context
	 */
	public void setMimeType(String mimeType) {
		if(headerGenerated) throw new RuntimeException("Header has already been generated!");
		this.mimeType = mimeType;
	}

	/**
	 * Sets content length of context, if header has already been generated, throws {@link RuntimeException}
	 * @param content length content length of context
	 */
	public void setContentLength(Long contentLength) {
		if(headerGenerated) throw new RuntimeException("Header has already been generated!");
		this.contentLength = contentLength;
	}

	/**
	 * Used to retrieve value from parameters map or null if value does not exist
	 * @param name key of value in map
	 * @return value for the given key
	 */
	public String getParameter(String name) {
		return parameters.get(name);
	}
	/**
	 * Used to retrieve parameters from parameters in a read-only set
	 * @return set of parameters
	 */
	public Set<String> getParameterNames() {
		return Collections.unmodifiableSet(parameters.keySet());
	}
	/**
	 * Used to retrieve value from persistent parameters map or null if value does not exist
	 * @param name key of value in map
	 * @return value for the given key
	 */
	public String getPersistentParameter(String name) {
		return persistentParameters.get(name);
	}
	/**
	 * Used to retrieve persistent parameters from parameters in a read-only set
	 * @return set of parameters
	 */
	public Set<String> getPersistentParameterNames(){
		return Collections.unmodifiableSet(persistentParameters.keySet());
	}
	/**
	 * USed to set persistent parameter in map 
	 * @param name key of value to add
	 * @param value value to add
	 */
	public void setPersistentParameter(String name, String value) {
		persistentParameters.put(name, value);
	}
	/**
	 * Used to remove parameter from persistent parameter map
	 * @param name parameter to remove
	 */
	public void removePersistentParameter(String name) {
		persistentParameters.remove(name);
	}
	/**
	 * Used to retrieve value from temporary parameters map or null if value does not exist
	 * @param name key of value in map
	 * @return value for the given key
	 */
	public String getTemporaryParameter(String name) {
		return temporaryParameters.get(name);
	}
	/**
	 * Used to retrieve temporary parameters from parameters in a read-only set
	 * @return set of parameters
	 */
	public Set<String> getTemporaryParameterNames(){
		return Collections.unmodifiableSet(temporaryParameters.keySet());
	}
	
	/**
	 * USed to set temporary parameter in map 
	 * @param name key of value to add
	 * @param value value to add
	 */
	public void setTemporaryParameter(String name, String value) {
		temporaryParameters.put(name, value);
	}
	/**
	 * Used to remove temporary parameter from temporary parameter map
	 * @param name parameter to remove
	 */
	public void removeTemporaryParameter(String name) {
		temporaryParameters.remove(name);
	}
	
	/**
	 * Used to add new {@link RCCookie} into the context that
	 * is later used in generating outputs
	 * @param rcCookie {@link RCCookie} to add
	 */
	public void addRCCookie(RCCookie rcCookie) {
		if(!outputCookies.contains(rcCookie)) {
			outputCookies.add(rcCookie);
		}
	}
	
	/**
	 * Used to return identifier which is unique for current user session
	 * @return unique identifier
	 */
	public String getSessionID() {
		return this.sid;
	}
	/**
	 * Writes given data into the output stream of this context.
	 * If it is the first call of this method, a header is generated and
	 * then the data is written and the headerGenerated flag is set to true
	 * @param data byte array of data to be written to output stream
	 * @return this {@link RequestContext} with altered output stream
	 * @throws IOException if there is something wrong while writing the data
	 */
	public RequestContext write(byte[] data) throws IOException {
		if(!headerGenerated) {
			generateHeader();
		}
		outputStream.write(data);
		return this;
	}
	/**
	 * Writes given data into the output stream of this context from given offset and for given length.
	 * If it is the first call of this method, a header is generated and
	 * then the data is written and the headerGenerated flag is set to true
	 * @param data byte array of data to be written to output stream
	 * @return this {@link RequestContext} with altered output stream
	 * @throws IOException if there is something wrong while writing the data
	 */
	public RequestContext write(byte[] data, int offset, int len) throws IOException {
		if(!headerGenerated) {
			generateHeader();
		}
		outputStream.write(data, offset, len);
		return this;
	}
	/**
	 * Writes given data into the output stream of this context.
	 * If it is the first call of this method, a header is generated and
	 * then the data is written and the headerGenerated flag is set to true
	 * @param data byte array of data to be written to output stream
	 * @return this {@link RequestContext} with altered output stream
	 * @throws IOException if there is something wrong while writing the data
	 */
	public RequestContext write(String text) throws IOException {
		if(!headerGenerated) {
			generateHeader();
		}
		outputStream.write(text.getBytes(charset));
		return this;
	}
	
	/**
	 * Used to generate header if one of the write methods 
	 * was called for the first time.
	 * @throws IOException if error occures during header generating
	 */
	private void generateHeader() throws IOException {
		charset = Charset.forName(encoding);
		
		StringBuilder builder = new StringBuilder();
		builder.append("HTTP/1.1 " + statusCode + " " + statusText + "\r\n");
		builder.append("Content-Type: ");
		builder.append((mimeType.startsWith("text/")) ? mimeType + "; charset=" + encoding : mimeType);
		builder.append("\r\n");
		
		if(contentLength != null) {
			builder.append("Content-Length: " + contentLength + "\r\n");
		}
		if(!outputCookies.isEmpty()) {
			outputCookies.forEach(cookie -> {
				builder.append("Set-Cookie: " + cookie.name + "=" + '"' + cookie.value +'"' +"; ");
				if(cookie.domain != null) {
					builder.append("Domain ="+ cookie.domain + "; ");
				}
				if(cookie.path != null) {
					builder.append("Path =" + cookie.path + "; ");
				}
				if(cookie.maxAge != null) {
					builder.append("Max-Age =" + cookie.maxAge + "; ");
				}
				if(cookie.httpOnly) {
					builder.append("HttpOnly ");
				}
				builder.append("\r\n");
			}
			);
		}
		builder.append("\r\n");
		outputStream.write(builder.toString().getBytes(charset));
		headerGenerated = true;
	}
}
