package com.rockblade.helper;

import com.rockblade.parsecenter.OnlineAPIParser;

import static com.rockblade.util.StockUtil.*;

public class StockParserFactory {

	public static OnlineAPIParser getOnlineAPIParser() throws InstantiationException, IllegalAccessException, ClassNotFoundException {
		String className = PropertiesHelper.getInstance().getValue(ONLINE_API);
		return (OnlineAPIParser) Class.forName(className).newInstance();
	}

}
