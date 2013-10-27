package com.rockblade.helper;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import com.rockblade.util.StockUtil;

public class PropertiesHelper {

	private static Properties props = new Properties();
	private static Map<String, String> propertiesMap = new HashMap<>();

	private PropertiesHelper() {
	};

	private static volatile PropertiesHelper INSTANCE;

	public static PropertiesHelper getInstance() {

		if (INSTANCE != null) {
			return INSTANCE;
		} else if (INSTANCE == null) {
			synchronized (PropertiesHelper.class) {
				if (INSTANCE == null) {
					INSTANCE = new PropertiesHelper();
				}
			}
		}

		return INSTANCE;
	}

	private void initPropertiesMap() {
		propertiesMap.put("online_stock_parser", "SinaOnlineAPIParser");
		propertiesMap.put("sina_stock_url", "http://hq.sinajs.cn/list=");
		propertiesMap.put("wangyi_stock_url", "http://api.money.126.net/data/feed/");
		propertiesMap.put("google_stock_url", "http://finance.google.com/finance/info?client=ig&q=");

		for (Map.Entry<String, String> entry : propertiesMap.entrySet()) {
			props.setProperty(entry.getKey(), entry.getValue());
		}
	}

	private void initPropertiesFile() throws IOException {
		File propertiesFile = new File(StockUtil.PROPERTIES_FILE);
		FileInputStream propertiesFileInputStream = new FileInputStream(propertiesFile);
		props.load(propertiesFileInputStream);
	}

	private void launchBackUpPlan() {
		initPropertiesMap();
	}

	public String getValue(String key) {
		try {
			if (props.isEmpty()) {
				initPropertiesFile();
			}
		} catch (IOException ex) {
			launchBackUpPlan();
		}

		return props.getProperty(key);
	}
}
