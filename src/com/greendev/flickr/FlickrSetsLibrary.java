package com.greendev.flickr;

import java.util.ArrayList;
import java.util.List;

import android.os.Message;

public class FlickrSetsLibrary {
	private static volatile FlickrSetsLibrary instance = null;
	public static String a = "hi";
	public static FlickrSet[] fSets;

	/* Portfolio variables */
	public static String[] campImgs, campThumbs, campDesc;
	public static String[] pressImgs, pressThumbs, pressDesc;
	public static String[] gdImgs, gdThumbs, gdDesc;
	public static String[] webImgs, webThumbs, webDesc;
	public static String[] dmImgs, dmThumbs, dmDesc;
	public static String[] packImgs, packThumbs, packDesc;
	public static String[] boothImgs, boothThumbs, boothDesc;
	
	/* Gallery variables */
	public static Object[] setOfSetImgs;
	public static Object[] setOfSetDescs;
	public static Object[] setOfSetThumbs;
	public static String[] setNames;
	public static String[] setsThumbUrls;
		
	private FlickrSetsLibrary() {
	}

	public static FlickrSetsLibrary getInstance() {
		if (instance == null) {
			synchronized (FlickrSetsLibrary.class) {
				if (instance == null) {
					instance = new FlickrSetsLibrary();
				}
			}
		}
		return instance;
	}

	public void setFlickrSets(FlickrSet[] s) {
		this.fSets = s;
	}

	public void createUrlPortfolio() {
		for (int i = 0; i < fSets.length; i++) {
			// Campaigns
			if (fSets[i].getName().equals("campaigns")) {
				// Get photos from Flickr
				FlickrPhoto[] setPhotos = fSets[i].fetchPhotos();
				// Get photo URLs
				campImgs = new String[setPhotos.length];
				campThumbs = new String[setPhotos.length];
				campDesc = new String[setPhotos.length];
				for (int j = 0; j < setPhotos.length; j++) {
					campImgs[j] = setPhotos[j].makeURL();
					campThumbs[j] = setPhotos[j].makeThumbURL();
					campDesc[j] = setPhotos[j].getTitle();
				}
			} else if (fSets[i].getName().equals("press")) {
				// Get photos from Flickr
				FlickrPhoto[] setPhotos = fSets[i].fetchPhotos();
				// Get photo URLs
				pressImgs = new String[setPhotos.length];
				pressThumbs = new String[setPhotos.length];
				pressDesc = new String[setPhotos.length];
				for (int j = 0; j < setPhotos.length; j++) {
					pressImgs[j] = setPhotos[j].makeURL();
					pressThumbs[j] = setPhotos[j].makeThumbURL();
					pressDesc[j] = setPhotos[j].getTitle();
				}
			} else if (fSets[i].getName().equals("graphic designs")) {
				// Get photos from Flickr
				FlickrPhoto[] setPhotos = fSets[i].fetchPhotos();
				// Get photo URLs
				gdImgs = new String[setPhotos.length];
				gdThumbs = new String[setPhotos.length];
				gdDesc = new String[setPhotos.length];
				for (int j = 0; j < setPhotos.length; j++) {
					gdImgs[j] = setPhotos[j].makeURL();
					gdThumbs[j] = setPhotos[j].makeThumbURL();
					gdDesc[j] = setPhotos[j].getTitle();
				}

			} else if (fSets[i].getName().equals("websites")) {
				// Get photos from Flickr
				FlickrPhoto[] setPhotos = fSets[i].fetchPhotos();
				// Get photo URLs
				webImgs = new String[setPhotos.length];
				webThumbs = new String[setPhotos.length];
				webDesc = new String[setPhotos.length];
				for (int j = 0; j < setPhotos.length; j++) {
					webImgs[j] = setPhotos[j].makeURL();
					webThumbs[j] = setPhotos[j].makeThumbURL();
					webDesc[j] = setPhotos[j].getTitle();
				}
			} else if (fSets[i].getName().equals("digital marketing")) {
				// Get photos from Flickr
				FlickrPhoto[] setPhotos = fSets[i].fetchPhotos();
				// Get photo URLs
				dmImgs = new String[setPhotos.length];
				dmThumbs = new String[setPhotos.length];
				dmDesc = new String[setPhotos.length];
				for (int j = 0; j < setPhotos.length; j++) {
					dmImgs[j] = setPhotos[j].makeURL();
					dmThumbs[j] = setPhotos[j].makeThumbURL();
					dmDesc[j] = setPhotos[j].getTitle();
				}

			} else if (fSets[i].getName().equals("packaging")) {
				// Get photos from Flickr
				FlickrPhoto[] setPhotos = fSets[i].fetchPhotos();
				// Get photo URLs
				packImgs = new String[setPhotos.length];
				packThumbs = new String[setPhotos.length];
				packDesc = new String[setPhotos.length];
				for (int j = 0; j < setPhotos.length; j++) {
					packImgs[j] = setPhotos[j].makeURL();
					packThumbs[j] = setPhotos[j].makeThumbURL();
					packDesc[j] = setPhotos[j].getTitle();
				}

			} else if (fSets[i].getName().equals("booth")) {
				// Get photos from Flickr
				FlickrPhoto[] setPhotos = fSets[i].fetchPhotos();
				// Get photo URLs
				boothImgs = new String[setPhotos.length];
				boothThumbs = new String[setPhotos.length];
				boothDesc = new String[setPhotos.length];
				for (int j = 0; j < setPhotos.length; j++) {
					boothImgs[j] = setPhotos[j].makeURL();
					boothThumbs[j] = setPhotos[j].makeThumbURL();
					boothDesc[j] = setPhotos[j].getTitle();
				}

			} else {
				continue;
			}

		}
	}
	
	public void createUrlGallery() {

		List<FlickrSet> list = new ArrayList<FlickrSet>();

		/* get the number of non-portfolio albums */
		for (int i = 0; i < fSets.length; i++) {
			String setName = fSets[i].getName();
			if (!isPortfolioSet(setName)) {
				list.add(fSets[i]);
			}
		}

		FlickrSet[] fSets = list.toArray(new FlickrSet[list.size()]);

		setOfSetImgs = new Object[fSets.length];
		setOfSetDescs = new Object[fSets.length];
		setOfSetThumbs = new Object[fSets.length];

		setNames = new String[fSets.length];
		setsThumbUrls = new String[fSets.length];

		for (int i = 0; i < fSets.length; i++) {
			/* filter out any portfolio sets */
			String setName = fSets[i].getName();
			if (!isPortfolioSet(setName)) {

				/* get the photos of set i */
				FlickrPhoto[] setPhotos = fSets[i].fetchPhotos();

				/* Get random image url for the thumbnail */
				String randThumbUrl = fSets[i].getRandomThumbUrl();

				/* store the name of set i into the array */
				setNames[i] = setName;
				setsThumbUrls[i] = randThumbUrl;

				/* set's containers */
				String[] aSetOfImgs = new String[setPhotos.length];
				String[] aSetOfThumbs = new String[setPhotos.length];
				String[] aSetOfDesc = new String[setPhotos.length];

				/* getting set's urls and descriptions */
				for (int j = 0; j < setPhotos.length; j++) {
					aSetOfImgs[j] = setPhotos[j].makeURL();
					aSetOfThumbs[j] = setPhotos[j].makeThumbURL();
					aSetOfDesc[j] = setPhotos[j].getTitle();
				}

				/* then storing it to the Object array */
				setOfSetImgs[i] = aSetOfImgs;
				setOfSetDescs[i] = aSetOfThumbs;
				setOfSetThumbs[i] = aSetOfThumbs;
			}
		}
	}
	
	/*
	 * Checks if the set belongs in Portfolio. TRUE -- is a portfolio FALSE --
	 * is not; is a gallery set.
	 */
	private boolean isPortfolioSet(String setName) {
		return setName.equals("campaigns") || setName.equals("press")
				|| setName.equals("graphic designs")
				|| setName.equals("websites")
				|| setName.equals("digital marketing")
				|| setName.equals("packaging") || setName.equals("booth");
	}
	
	
}
