package hr.fer.zemris.java.webserver.workers;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

import hr.fer.zemris.java.webserver.IWebWorker;
import hr.fer.zemris.java.webserver.RequestContext;

/**
* {@link IWebWorker} that is used to draw a circle on to 
* the screen  
* @author Tomislav Kurtović
*
*/
public class CircleWorker implements IWebWorker  {
	
	@Override
	public void processRequest(RequestContext context) throws Exception {
		BufferedImage bim = new BufferedImage(200, 200, BufferedImage.TYPE_3BYTE_BGR);
		Graphics2D g2d = bim.createGraphics();
		g2d.setColor(Color.RED);
		g2d.fillOval(0, 0, bim.getWidth(), bim.getHeight());
		g2d.dispose();
		
		context.setMimeType("image/png");
		
		try {
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			ImageIO.write(bim, "png", bos);
			context.write(bos.toByteArray());
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	
	
	
	
	
	
	
	
	
}
