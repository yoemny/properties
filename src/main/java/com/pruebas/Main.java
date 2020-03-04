package com.pruebas;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.Scanner;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.pruebas.Main;

public class Main {

	private static final Logger LOG = LogManager.getLogger(Main.class);

	public static void main(String[] args) {
		
		loadJSON("taiwan.json");
		/*
		Properties propertiesConfig = loadProperties("config.properties");
		Properties propertiesMsg = loadProperties("msg.properties");
		printInfoLog(propertiesConfig, "config.start.message");
		printInfoLog(propertiesMsg, "msg.start.message");
		*/
		
	}
/*
	private static Properties loadProperties(String name) {
        Properties prop = new Properties();

		try (InputStream input = Main.class.getClassLoader().getResourceAsStream(name)) {

            if (input == null) {
            	LOG.error("File " + name + " not found");
            	return null;
            }
            //load a properties file from class path, inside static method
            prop.load(new InputStreamReader(input, Charset.forName("UTF-8")));

        } catch (IOException ex) {
            ex.printStackTrace();
        }		
		return prop;
	}

	private static void printInfoLog(Properties properties ,String key) {
		if (properties == null) {
			LOG.info("No properties file for key " + key);
		}else {
			if (key != null) {
				String valueProperty = properties.getProperty(key);
				LOG.info("Value for key "+ key +" : " + valueProperty);
			}else {
		        LOG.info("Need key!!!");
			}
		}
	}
*/
	
	private static List<TestCaseData> obtenerCasos(JsonObject json, List<TestCaseData> casos) {
		List<TestCaseData> nuevos = new ArrayList<TestCaseData>();
    	for(String attr : json.keySet()){
    		System.out.println(attr);
    	}		        	
		return nuevos;
	}

	private static Boolean todosLista(JsonObject json) {
		Boolean result = true;
        if(json.isJsonObject()) {
        	Iterator<String> itr = json.keySet().iterator();
        	while( result && itr.hasNext() ){
        		if ( !json.get(itr.next()).isJsonArray() )
        			result = false;
        	}
        }
		return result;
	}
	
	private static String capitalize(String str) {
	    if(str == null || str.isEmpty()) {
	        return str;
	    }

	    return str.substring(0, 1).toUpperCase() + str.substring(1);
	}
	
	private static TestCaseData obtenerRoot(TestCaseData root, JsonObject json){
		
    	for(String attr : json.keySet()){
    		if ( json.get(attr).isJsonPrimitive() ) {
    			try {
    				String value = json.get(attr).getAsString(); 
					Method method = root.getClass().getMethod("set"+capitalize(attr), String.class);
					method.invoke(root, value);
				} catch (NoSuchMethodException e) {
					e.printStackTrace();
				} catch (SecurityException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					e.printStackTrace();
				}
    		}
    			
    		   
    	}		        	
		
		return root;
	}

	private static JsonObject obtenerRootJSON(JsonObject json){
		JsonObject rootJSON = json.deepCopy();
		List<String> primitivos = new ArrayList<String>();
		for(String attr : json.keySet())
    		if ( json.get(attr).isJsonPrimitive() ) {
    			primitivos.add(attr);
    		}
		for(String attr : primitivos)
			rootJSON.remove(attr);
		
		return rootJSON;
	}
	
	private static void loadJSON(String name) {
		List<TestCaseData> casos = new ArrayList<TestCaseData>();
		try {
				InputStream input = Main.class.getClassLoader().getResourceAsStream(name);
				Scanner sc = new Scanner(input);
				//Reading line by line from scanner to StringBuffer
				StringBuffer sb = new StringBuffer();
			    while(sc.hasNext()){
			    	sb.append(sc.nextLine());
			    }
			    
			    Gson gson = new GsonBuilder().setPrettyPrinting().create();
		        JsonObject json = gson.fromJson(sb.toString(), JsonObject.class);
		        sc.close();
		        if(json.isJsonObject()) {
		        	TestCaseData root = new TestCaseData();
		        	if ( !todosLista(json) ) {
		        		casos.add(obtenerRoot(root, json));
		        		System.out.println("---- "+ casos.get(0).getUserUAT() + " ------");
		        		JsonObject rootJSON = obtenerRootJSON(json);
		        		casos = obtenerCasos(rootJSON, casos);
		        	}else {
		        		casos = obtenerCasos(json, casos);
		        	}
		        	/*
					 JsonArray driver = json.get("driver").getAsJsonArray();
					 if ( driver != null && driver.isJsonArray() ) {
					     Iterator<JsonElement> iterator = driver.iterator();
						 while(iterator.hasNext()) {
							 JsonObject driverObject = iterator.next().getAsJsonObject();
							 String driverName = driverObject.get("name").getAsString();
							 String driverSRC = driverObject.get("src").getAsString();
							 System.out.println(driverName + "   " + driverSRC);
						 
						  }
					 }
					 if( json.has("userUAT")) {
						 String userUAT = json.get("userUAT").getAsString();
						 if(userUAT !=null) {
							 System.out.println(userUAT);
						 }
					 }
					*/ 
		     
		            
		        }
		         
		}catch(Exception e) {
			System.out.println(e.toString());
		}		
	}
}
