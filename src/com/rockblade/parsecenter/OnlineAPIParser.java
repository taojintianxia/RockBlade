package com.rockblade.parsecenter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.rockblade.model.OnlineAPIPattern;

/**
 * 
 * 
 * @author Kane.Sun
 * @version Oct 25, 2013 5:19:37 PM
 * 
 */

public abstract class OnlineAPIParser implements Parser {

	protected OnlineAPIPattern onlineAPIPattern = new OnlineAPIPattern();
	
	protected Logger logger = LoggerFactory.getLogger(this.getClass().getSimpleName());

	protected String getOnlineStrDataByStockId(String stockId, OnlineAPIPattern onlineAPIPattern) {
		String data = "";
		try {
			URL url = new URL(onlineAPIPattern.getUrl() + stockId);
			URLConnection urlConnection = url.openConnection();
			InputStreamReader inputStreamReader = new InputStreamReader(urlConnection.getInputStream(), onlineAPIPattern.getEncoding());
			BufferedReader bufferReader = new BufferedReader(inputStreamReader);
			String inputLine;
			if ((inputLine = bufferReader.readLine()) != null) {
				data = inputLine;
			}
			bufferReader.close();
		} catch (MalformedURLException me) {
			//
		} catch (IOException ioe) {
			//
		}

		return data;
	}
}
