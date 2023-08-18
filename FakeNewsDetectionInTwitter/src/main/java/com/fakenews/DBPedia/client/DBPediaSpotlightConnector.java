package com.fakenews.DBPedia.client;

import com.fakenews.DBPedia.domain.Annotation;
import com.fakenews.DBPedia.domain.SpotlightResponse;
import com.fakenews.DBPedia.domain.SurfaceForm;
import com.google.common.collect.Lists;
import com.google.gson.Gson;
import com.sun.net.httpserver.HttpHandler;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.List;

public class DBPediaSpotlightConnector {
    private static final String SPOTLIGHT_CANDIDATES_BASE_QUERY = "http://localhost:9080/rest/candidates?";
    private static final String SPOTLIGHT_CANDIDATES_PARAM_TEXT = "text=";

    public SpotlightResponse getSpotlightReponse(final String textInput){
    	String newLine = System.getProperty("line.separator");
        final String url = SPOTLIGHT_CANDIDATES_BASE_QUERY +
                SPOTLIGHT_CANDIDATES_PARAM_TEXT +
                URLEncoder.encode(textInput);
        SpotlightResponse spotlightResponse = null;
        String respnseBody = null;
        HttpClient httpClient = HttpClientBuilder.create().build();
        HttpGet getRequest = new HttpGet(url);
        getRequest.addHeader("accept", "application/json");


        try {
            HttpResponse response = httpClient.execute(getRequest);
            respnseBody = EntityUtils.toString(response.getEntity());
            Gson gson = new Gson();
            spotlightResponse = gson.fromJson(respnseBody, SpotlightResponse.class);
            System.out.println("Response from Spotlight:  "+spotlightResponse+""+newLine);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return  spotlightResponse;
    }

    public List<String> getDBPediaContextsForATweet(final String tweet){
        List<String> contexts = Lists.newArrayList();
        SpotlightResponse response = getSpotlightReponse(tweet);
        for(SurfaceForm surfaceForm : response.getAnnotation().getSurfaceForm()){
            contexts.add(surfaceForm.getName());
        }
        return contexts;
    }

}
