package hr.fer.zemris.java.galery.model;

/**
 * Class that represents a image
 * and its description with tags 
 * assosiated with the picture and
 * its base64 encoding 
 * @author Tomislav Kurtović
 *
 */ 
public class PictureDesc {

	/**
	 * Name of picture
	 */
	private String name;
	/**
	 * Description of picture
	 */
	private String description;
	/**
	 * Tags of picture
	 */
	private String tags;
	/**
	 * base64 encoding of picture
	 */
	private String base64Encoding; 
	
	/**
	 * Constructor of image descriptor
	 * @param name name of image
	 * @param description description of image
	 * @param tags tag of image
	 */
	public PictureDesc(String name, String description, String tags) {
		super();
		this.name = name;
		this.description = description;
		this.tags = tags;
	}

	/**
	 * Returns name of image
	 * @return name of image
	 */
	public String getName() {
		return name;
	}
	/**
	 * Returns description of image
	 * @return description of image
	 */
	public String getDescription() {
		return description;
	}
	/**
	 * Returns tags of image
	 * @return tags of image
	 */
	public String getTags() {
		return tags;
	}
	/**
	 * Sets name of image
	 * @param name name of image
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * Sets description of image
	 * @param description description of image
	 */
	
	public void setDescription(String description) {
		this.description = description;
	}
	/**
	 * Sets tags of image
	 * @param tags tags of image
	 */
	public void setTags(String tags) {
		this.tags = tags;
	}
	/**
	 * Sets base64Encoding of image
	 * @param base64Encoding base64Encoding of image
	 */
	public void setBase64Encoding(String base64Encoding) {
		this.base64Encoding = base64Encoding;
	}
	/**
	 * Returns base64 encoding of image
	 * @return base64 encoding of image
	 */
	public String getBase64Encoding() {
		return base64Encoding;
	}
}
