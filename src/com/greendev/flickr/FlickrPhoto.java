package com.greendev.flickr;

/**
 * Fickr image sizes.
 * 
 * s	small square 75x75
 * q	large square 150x150
 * t	thumbnail, 100 on longest side
 * m	small, 240 on longest side
 * n	small, 320 on longest side
 * -	medium, 500 on longest side
 * z	medium 640, 640 on longest side
 * c	medium 800, 800 on longest side 
 * b	large, 1024 on longest side*
 * o	original image, either a jpg, gif or png, depending on source format
 * @author Alice
 *
 */
public class FlickrPhoto {
	private String id;
	private String owner;
	private String secret;
	private String server;
	private String title;
	private String farm;

	public FlickrPhoto(String _id, String _secret, String _server,
			String _title, String _farm) {
		id = _id;

		secret = _secret;
		server = _server;
		title = _title;
		farm = _farm;
	}

	public String makeURL() {
		return "http://farm" + farm + ".static.flickr.com/" + server + "/" + id
				+ "_" + secret + "_z.jpg";
	}

	public String makeThumbURL() {
		return "http://farm" + farm + ".static.flickr.com/" + server + "/" + id
				+ "_" + secret + "_n.jpg";  // 
	}
	
	public String getTitle() {
		return title;
	}

}