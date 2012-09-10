package com.greendev.ldlmarketing.twitter;

import java.util.ArrayList;

import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONObject;
import org.json.JSONArray;
import org.json.simple.parser.JSONParser;


/**
 * Manages calling the Twitter API and formatting the return data for easy use.
 * 
 * @author Alice Nguyen
 * @since Sept. 8, 2012
 * 
 */
public class Tweet {
	public String username;
	public String message;
	public String image_url;

	public Tweet(String username, String message, String url) {
		this.username = username;
		this.message = message;
		this.image_url = url;
	}

	/*
	 * Generates the API call and process the return data into our Tweet
	 * objects.
	 */
//	public ArrayList<Tweet> getTweets(String searchTerm, int page) {
//		String searchUrl = "http://search.twitter.com/search.json?q=@" +
//							searchTerm + "&rpp=100&page=" + page;
//		
//		ArrayList<Tweet> tweets = new ArrayList<Tweet>();
//		
//		HttpClient client = new DefaultHttpClient();
//		HttpGet get = new HttpGet(searchUrl);
//		
//		ResponseHandler<String> responseHandler = new BasicResponseHandler();
//		
//		String responseBody = null;
//		try {
//			responseBody = client.execute(get, responseHandler);
//		} catch(Exception ex) {
//			ex.printStackTrace();
//		}
//		
//		JSONObject jsonObject = null;
//		JSONParser parser = new JSONParser();
//		
//		
//		//TODO
////		try {
////			Object obj = parser.parse(responseBody);
////			jsonObject = (JSONObject) obj;
////		}
//		
//		
//	}

}
