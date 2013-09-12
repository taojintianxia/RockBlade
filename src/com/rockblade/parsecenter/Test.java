package com.rockblade.parsecenter;

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
		String str = "var hq_str_sh601006=\"大秦铁路,7.56,7.59,7.61,7.65,7.49,7.61,7.62,40158041,304571906,67525,7.61,374245,7.60,481400,7.59,106000,7.58,222000,7.57,223313,7.62,384330,7.63,246500,7.64,656312,7.65,178360,7.66,2013-09-12,15:01:05,00\";";
		System.out.println(str.substring(13, str.indexOf("=") ));
	}

}
