package com.fakenews.SentimentalAnalysis.main;
import java.awt.List;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Map;
import java.util.Scanner;


public class SentimentalAnalysis {
	
	public float tweetscore(String tweet) {
		try
		{
			float tweetscore=0;
			int count=0;
			ArrayList<String> stopwords= new ArrayList<String>();
			BufferedReader stop = new BufferedReader(new FileReader("Data/stopwords.txt"));
			String line = "";
			while ((line = stop.readLine()) != null)
			{
			  stopwords.add(line);
			}
			Map<String, String> map = new HashMap<String, String>();
	        BufferedReader in = new BufferedReader(new FileReader("Data/AFINN"));
	        line="";
	        while ((line = in.readLine()) != null) 
	        {
	            String parts[] = line.split("\t");
	            map.put(parts[0], parts[1]);
	            count++;
	        }
	        in.close();
	        		
	        		String[] word=tweet.split(" ");
	        		for(int i=0; i<word.length;i++)
	        		{
	        			if(stopwords.contains(word[i].toLowerCase()))
	        			{	
	        			}
	        			else
	        			{
	        				if(map.get(word[i])!=null)
	        				{
	        					String wordscore= map.get(word[i].toLowerCase());
	        					tweetscore=(float) tweetscore + Integer.parseInt(wordscore);
	        				}
	        			}
	        		}
	        		Map<String, Float> sentiment= new HashMap<String, Float>();
	        		//sentiment.put(tweet, tweetscore);
	        		//System.out.println(tweetscore);
	        		//System.out.println(sentiment.toString());	
	        		return tweetscore;
		}
		catch(FileNotFoundException e)
		{
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return 0;
	}

}
