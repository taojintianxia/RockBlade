package com.rockblade.sevlet;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

/**
 * 
 * 
 * @author Kane.Sun
 * @version Sep 6, 2013 10:39:32 AM
 * 
 */

public class Test {

	public static void main(String... args) {

		try {
			URL url = new URL("http://hq.sinajs.cn/list=sh600016");
			URLConnection cumtConnection = url.openConnection();
			DataInputStream din = new DataInputStream(cumtConnection.getInputStream());
			String inputLine;
			while ((inputLine = din.readLine()) != null) {
				System.out.println(inputLine);
			}
			din.close();
		} catch (MalformedURLException me) {
		} catch (IOException ioe) {
		}

	}

}
