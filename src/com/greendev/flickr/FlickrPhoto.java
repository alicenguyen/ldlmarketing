package com.greendev.flickr;

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
				+ "_" + secret + "_q.jpg";
	}
	
	public String getTitle() {
		return title;
	}

}