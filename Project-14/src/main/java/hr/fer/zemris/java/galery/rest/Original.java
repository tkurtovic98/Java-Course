package hr.fer.zemris.java.galery.rest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.ServletContext;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.json.JSONObject;

import hr.fer.zemris.java.galery.model.PictureDesc;
import hr.fer.zemris.java.galery.util.Util;

/**
 * Class that can deliver original sized
 * images to the client upon request 
 * @author Tomislav Kurtović
 *
 */
@Path("/original")
public class Original {
	@Context
	private ServletContext context;
	 
	/**
	 * Used to get image with the specified name from 
	 * the images folder and return it in order to be 
	 * rendered to the page 
	 * @param name name of the image to be rendered
	 * @return response with status ok if everything goes ok
	 */
	@GET   
	@Path("{name}") 
	@Produces(MediaType.APPLICATION_JSON) 
	public Response getImage(@PathParam("name")String name) {
		List<PictureDesc> pictureDesc = new ArrayList<>();
		try {
			pictureDesc = Util.getPictureDescriptions(context.getRealPath("/WEB-INF/opisnik.txt"));
		  
			PictureDesc largeImage = pictureDesc.stream()
					.filter(pic -> pic.getName().equals(name))
					.collect(Collectors.toList())
					.get(0);
			 
 			JSONObject img = new JSONObject();
			largeImage.setBase64Encoding(Util.getEncodedImage(context.getRealPath("/WEB-INF/slike/" + largeImage.getName())));
			img.put("name",largeImage.getName());
			img.put("description",largeImage.getDescription());
			img.put("tags", largeImage.getTags());  
			img.put("base64", largeImage.getBase64Encoding());
			return Response.status(Status.OK).entity(img.toString()).build();
		} catch(IOException ex) {
			return null;
		}
	}
}
 