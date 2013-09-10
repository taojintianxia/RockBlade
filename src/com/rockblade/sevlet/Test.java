package com.rockblade.sevlet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * 
 * @author Kane.Sun
 * @version Sep 6, 2013 10:39:32 AM
 * 
 */

public class Test {

	final static Logger logger = LoggerFactory.getLogger(Test.class);

	public static void main(String... args) {
		logger.error(" Temperature set to {}. Old temperature was {}. ");
	}

}
