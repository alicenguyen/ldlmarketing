package com.greendev.flickr;

public class FlickrPhoto {
	String id;
	String owner;
	String secret;
	String server;
	String title;
	String farm;

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

}