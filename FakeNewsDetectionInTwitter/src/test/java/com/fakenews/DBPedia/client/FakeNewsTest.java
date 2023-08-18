package com.fakenews.DBPedia.client;
import java.util.List;
import org.junit.Test;
import com.fakenews.DBPedia.client.DBPediaSpotlightConnector;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;

import com.fakenews.Twitter4j.manager.TweetManager;
import com.fakenews.Twitter4j.manager.TwitterCriteria;
import com.fakenews.Twitter4j.model.Tweet;
import com.fakenews.CosineSimilarity.Cosine;
import com.fakenews.SentimentalAnalysis.main.SentimentalAnalysis;
import org.junit.Test;
import static org.junit.Assert.*;

public class FakeNewsTest {
	private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm");
	private static final String USERNAME = "Username: ";
	private static final String RETWEETS = "Retweets: ";
	private static final String TEXT = "Text: ";
	private static final String MENTIONS = "Mentions: ";
	private static final String HASHTAGS = "Hashtags: ";
	
	
	@Test
    public void testForOneTweet(){
		String newLine = System.getProperty("line.separator");
		try
		{
			BufferedWriter bw = new BufferedWriter(new FileWriter("output_got1.csv"));
			bw.write("user tweet;timesofindia;ndtv;the hindu;DeccanHerald;CBSnews;time");
			bw.newLine();
			File csvFile = new File("/home/guruthor/Documents/dataset120.csv");
			BufferedReader br = new BufferedReader(new FileReader(csvFile));
			String line = "";
			
			while ((line = br.readLine()) != null) 
			{
				String[] arr = line.split(",");
				/*User Tweet*/
				String date = arr[2]; 
		        String[] words=date.split("-");
		        String d="";
		        d=words[2]+"-"+words[1]+"-"+words[0]; 
		        bw.write(String.format("%s;",arr[1]));
		        System.out.println("User Tweet:  "+arr[1]+""+newLine);
		        
		        bw.write(String.format("%s;",Trusted(arr[1],"timesofindia",d)));
		        bw.write(String.format("%s;",Trusted(arr[1],"ndtv",d)));
		        bw.write(String.format("%s;",Trusted(arr[1],"the_hindu",d))); 
		        bw.write(String.format("%s;",Trusted(arr[1],"DeccanHerald",d))); 
		        bw.write(String.format("%s;",Trusted(arr[1],"CBSnews",d)));
		        bw.write(String.format("%s;",Trusted(arr[1],"time",d)));
		        bw.newLine();
				System.out.println(  arr[1]);
			}
			bw.close();
		}
		catch(IOException ex)
		{
			ex.printStackTrace();
		}
		 
		 
}
public   String Trusted(String tweet, String agency, String date)
{
	String status="";
	double score=0;
	String trusted="";
	String newLine = System.getProperty("line.separator");
	System.out.println("---------------------------------------------------------------------------------------------------------------------------------------------------------------------------"+newLine);
	 int sum=0;
	 int day = Integer.parseInt(date.substring(8));
	 
	 /*Keyword Extraction*/
	 String keywords=searchKeywords(tweet);
	 if(keywords.length()==0)
		  System.out.println("No KeyWords Found from DBPedia");
	 else
	 {
     System.out.println("Keywords from DBpedia:  "+keywords+""+newLine);
     
		/*Searching Tweets using Twitter4j by setting the following parameters*/
		TwitterCriteria criteria = TwitterCriteria.create();
		criteria.setUsername(agency);
		criteria.setSince(date.substring(0,8)+(day-1));
		criteria.setUntil(date.substring(0,8)+(day+1));
		criteria.setQuerySearch(keywords);
		criteria.setMaxTweets(100);
		
		for (Tweet t : TweetManager.getTweets(criteria)) {
			sum+=1;
			System.out.println("tweet "+sum+" : "+t.getText()+""+newLine);
	        String keyword=searchKeywords(t.getText());
	        if(keyword.length()==0)
				  System.out.println("No KeyWords Found from DBPedia");
	        else
	        {
	        System.out.println("Keywords from DBpedia:  "+keyword+""+newLine);
	        /*Cosine Similarity On Trusted Tweets*/
	        
	        Cosine instance = new Cosine();
	        double result = instance.similarity(keywords, keyword);
	        if(result>score)
	        {	
	        	score=result;
	        	trusted=t.getText();
	        }
		}}
		System.out.println("Selected Tweet From "+agency+":  "+trusted+""+newLine);
		System.out.println("Cosine Similarity Score of Keywords of Trusted Agency Tweet and User Tweet Is: "+score+""+newLine);
		
		
		
		
		
	
		/*Doing sentimental Analysis*/
		SentimentalAnalysis s=new SentimentalAnalysis();
		float user_score=s.tweetscore(tweet);
		System.out.println("Sentimental Analysis Score Of User Tweet:   "+user_score+""+newLine);
		float trusted_score=s.tweetscore(trusted);
		System.out.println("Sentimental Analysis Score Of Trusted Tweet:   "+trusted_score+""+newLine);
		System.out.println("Agency= "+agency+""+newLine);
		if(sum==0)
			{
			System.out.println("Result = No Tweets Found "+""+newLine);
			status="No Tweets Found";
			}
		else if(((user_score>0.0)&&(trusted_score>0.0))||((user_score<0.0)&&(trusted_score<0.0))||((user_score==0.0)&&(trusted_score==0.0)))
			{
			System.out.println("Result = Genuine"+""+newLine);
			status="Genuine";
			}
		else
			{
			System.out.println("Result = Fake"+""+newLine);
			status="Fake";
			}
		System.out.println("---------------------------------------------------------------------------------------------------------------------------------------------------------------------------"+newLine);
	
	 }
	 return status;	 		
}
	public String searchKeywords(String tweet) {
		/*Keyword Extraction*/
        DBPediaSpotlightConnector connector = new DBPediaSpotlightConnector();
        List<String> A= connector.getDBPediaContextsForATweet(tweet);
        String keywords="";
        for (int i = 0; i < A.size(); i++) 
        	keywords=keywords+A.get(i)+" ";
		
		return keywords;
	}

}
