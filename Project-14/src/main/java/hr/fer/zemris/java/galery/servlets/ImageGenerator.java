package hr.fer.zemris.java.galery.servlets;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.Response.Status;

import com.google.gson.Gson;

import hr.fer.zemris.java.galery.model.PictureDesc;
import hr.fer.zemris.java.galery.util.Util;

/**
 * Class used to generate images in the 
 * web application upon request from user.
 * @author Tomislav Kurtović
 *
 */
@WebServlet("/images")
public class ImageGenerator extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String tag = req.getParameter("tag");

		if (tag == null) {
			return;
		}

		List<PictureDesc> descriptions = Util.getPictureDescriptions(
				getServletContext().getRealPath("/WEB-INF/opisnik.txt"));

		if (descriptions.isEmpty()) {
			resp.setStatus(Status.BAD_REQUEST.getStatusCode());
			return;
		}

		descriptions = descriptions.stream().filter(desc -> desc.getTags().contains(tag)).collect(Collectors.toList());

		if (!Files.exists(Paths.get(getServletContext().getRealPath("/WEB-INF/thumbnails")))) {
			Files.createDirectory(Paths.get(getServletContext().getRealPath("/WEB-INF/thumbnails")));
		}

		for (PictureDesc desc : descriptions) {
			if (!Files.exists(Paths.get(getServletContext().getRealPath("/WEB-INF/thumbnails/" + desc.getName())))) {
				createImage(getServletContext().getRealPath("/WEB-INF/slike/" + desc.getName()),
						getServletContext().getRealPath("/WEB-INF/thumbnails/" + desc.getName()));

			}
			desc.setBase64Encoding(Util.getEncodedImage(getServletContext().getRealPath("/WEB-INF/thumbnails/" + desc.getName())));
			
		}
		resp.setContentType("application/json;charset=UTF-8");
    
		PictureDesc[] array = new PictureDesc[descriptions.size()];
		descriptions.toArray(array);

		Gson gson = new Gson();
		String json = gson.toJson(array);
		resp.getWriter().write(json);
		resp.getWriter().flush();
	}

	/**
	 * Used to create a new image in the thumbnails folder if the 
	 * image does not already exist in it.
	 * It saves the resized image in the folder when creating it
	 * @param pathToImage string representation of path to original image
	 * @param pathToThumbnail string representation of path to resized image
	 * @throws IOException if something goes wrong while reading image
	 */
	private void createImage(String pathToImage, String pathToThumbnail) throws IOException {
		BufferedImage orig = ImageIO.read(Paths.get(pathToImage).toFile());
		BufferedImage thumbnail = resized(orig,150,150);
		ImageIO.write(thumbnail, "png", Paths.get(pathToThumbnail).toFile());
	}

	/**
	 * Used to resize images
	 * @param orig original image
	 * @param width wanted width of resized image
	 * @param height wanted height of resized image
	 * @return resized image
	 */
	private BufferedImage resized(BufferedImage orig, int width, int height) {
		BufferedImage resized = new BufferedImage(width,height, BufferedImage.TYPE_3BYTE_BGR);
		Graphics2D graphic = resized.createGraphics();
		graphic.drawImage(orig, 0, 0, width, height,null);
		graphic.dispose();
		
		return resized;
	}
}