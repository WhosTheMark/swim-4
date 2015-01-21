package fr.insa.toulouse.tp2g2.mainApp;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class TestUtilities {

	public static String retrieveFileContent(String filename) {
		String content = "";
		BufferedReader input = null;
	    try {
	    	input = new BufferedReader(new FileReader(filename));
	        StringBuilder sb = new StringBuilder();
	        String line = input.readLine();

	        while (line != null) {
	            sb.append(line);
	            sb.append(System.lineSeparator());
	            line = input.readLine();
	        }
	        content = sb.toString();
	    }catch(IOException exception) {
	    	
	    } finally {
	    	if(input != null) {
	    		try {
					input.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	    	}
	    }
	    return content;
	}
}
