 package hr.fer.zemris.java.galery.rest;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.ServletContext;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.json.JSONArray;
import org.json.JSONObject;

import hr.fer.zemris.java.galery.model.PictureDesc;
import hr.fer.zemris.java.galery.util.Util;

@Path("/tags")
public class Tags {
	@Context
	private ServletContext context; 
	
	/**
	 * Used to generate a json array of tags that
	 * were aquired from a txt document
	 * @return json array of tags 
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response generateButtonsHtml() {
		List<PictureDesc> descriptions = new ArrayList<>();
		try {
			descriptions = Util.getPictureDescriptions(context.getRealPath("/WEB-INF/opisnik.txt"));
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
		
		Set<String> uniqueTags = new HashSet<String>();
		for(PictureDesc desc : descriptions) {
			String[] splitTags = desc.getTags().split(",");
			for(String tag : splitTags) {
				uniqueTags.add(tag.trim());
			}
		}
		
		JSONObject tagObject = new JSONObject();
		
		JSONArray tags = new JSONArray(uniqueTags);
		
		tagObject.put("tags", tags);
		
		return Response.status(Status.OK).entity(tagObject.toString()).build();
	}
}
