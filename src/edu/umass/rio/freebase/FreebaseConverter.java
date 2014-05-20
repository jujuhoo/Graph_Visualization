/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.umass.rio.freebase;
import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestFactory;
import com.google.api.client.http.HttpResponse;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.jayway.jsonpath.JsonPath;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.HashMap;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
/**
 *
 * @author jujuhoo
 */
public class FreebaseConverter {
 public static String API_KEY = "AIzaSyAp1H9mLr1MxtnqgUVh8M3i04zDEONKBLk";
 static public HashMap<Integer, String> ID_NAME_MAP = new HashMap<Integer, String>();
  static public HashMap<String, Integer> NAME_ID_MAP = new HashMap<String, Integer>();
  static public  String GetMid(Integer i){
      String mid = ID_NAME_MAP.get(i);
      mid = mid.replaceAll("_actor", "");
      mid = mid.replaceAll("_director", "");
      return mid;
  }
  public static String GetName(String key){
	  String output = null;
	    try {
	        HttpTransport httpTransport = new NetHttpTransport();
	        HttpRequestFactory requestFactory = httpTransport.createRequestFactory();
	        JSONParser parser = new JSONParser();
	        GenericUrl url = new GenericUrl("https://www.googleapis.com/freebase/v1/search");
	        url.put("query", key);
	       // url.put("filter", "(all type:/music/artist created:\"The Lady Killer\")");
	        url.put("limit", "10");
	        url.put("indent", "true");
                url.put("domain", "/film");
	        url.put("key", FreebaseConverter.API_KEY);
	        HttpRequest request = requestFactory.buildGetRequest(url);
	        HttpResponse httpResponse = request.execute();
	        JSONObject response = (JSONObject)parser.parse(httpResponse.parseAsString());
	        JSONArray results = (JSONArray)response.get("result");
	        
	        for (Object result : results) {
	        	output = JsonPath.read(result,"$.name").toString();
                        break;
	        }
	      } catch (Exception ex) {
	        ex.printStackTrace();
	      } 
	    return output;
  }
 static public void loadIndex(String path){
        try {
            int count=0;
            FileReader reader = new FileReader(path);
            BufferedReader br = new BufferedReader(reader);
            String str = "";
            while ((str = br.readLine()) != null) {
                String[] subStrings = str.split("\t");
                String[] names = subStrings[1].split("/");
                String name = names[names.length - 1];
                name = name.replaceAll(">", "");
                name ="/"+name.substring(0, name.length()).replace(".","/");
                ID_NAME_MAP.put(new Integer(subStrings[0]),name);
                //NAME_ID_MAP.put(name,new Integer(subStrings[0]));
                count++;
                //System.out.println(subStrings[0]+" "+names[names.length-1]);
                if(count % 10000==0){
                    System.out.println("Loaded "+count+" items");
                }
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }  
}
