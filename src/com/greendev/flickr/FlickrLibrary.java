package com.greendev.flickr;

import java.io.Serializable;

public class FlickrLibrary implements Serializable {
	private FlickrPhoto[] photos;
	private FlickrSet[] set;
	
	/*
	 * Constructor -- FlickrPhoto
	 */
	public FlickrLibrary(FlickrPhoto[] photos){
		this.photos = photos;
	}
	
	/*
	 * Constructor -- FlickrSet
	 */
	public FlickrLibrary(FlickrSet[] set){
		this.set = set;
	}
	
	
	/*
	 * Return all photos in FlickrPhotos
	 */
	public FlickrPhoto[] getPhotos() {
		return photos;
	}
	
	/*
	 * Return all Sets
	 */
	public FlickrSet[] getSets() {
		return set;
	}

}
