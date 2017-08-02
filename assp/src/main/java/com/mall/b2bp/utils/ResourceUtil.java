package com.mall.b2bp.utils;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Utility for read configuration file
 * 
 * @author AIS
 */
public class ResourceUtil {

	private static final Logger LOG = LoggerFactory.getLogger(ResourceUtil.class);

	private static final String SYSTEM_CONFIG_FILE = "com/mall/b2bp/config/system.properties";

	private ResourceUtil() {
	}

	/**
	 * load system.properties file
	 * 
	 * @return
	 */
	public static Properties getSystemConfig() {
		return loadProperties(SYSTEM_CONFIG_FILE);
	}

	/**
	 * load properties file object by file name
	 * 
	 * @param propertiesFile
	 * @return
	 */
	private static Properties loadProperties(String propertiesFile) {
		Properties properties = new Properties();
		ClassLoader cl = ResourceUtil.class.getClassLoader();
		URL url = cl.getResource(propertiesFile);

		try (InputStream in = url.openStream();) {
			properties.load(in);
		} catch (IOException e) {
			LOG.error("get system config file error: " + e, e);
		}

		return properties;
	}
}
