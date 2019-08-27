package org.openmrs.module.conceptname.web.controller;


import org.openmrs.*;
import org.openmrs.api.context.Context;
import org.openmrs.module.conceptname.web.controller.DBConnection;

import javax.servlet.http.HttpServletRequest;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;
import javax.servlet.Servlet;
import javax.servlet.ServletContext;
import org.codehaus.jackson.map.ObjectMapper;

import org.openmrs.util.OpenmrsUtil;

public class Utils {

public static DBConnection getNmrsConnectionDetails() {
		
		DBConnection result = new DBConnection();
		
		try {
			
			//            String appDirectory = "";
			//
			//            InputStream inputStream;
			//            Properties props = new Properties();
			//            String OS = (System.getProperty("os.name")).toUpperCase();
			//            /*String prosName = OpenmrsUtil.getRuntimePropertiesFilePathName("openmrs");
			//			String prosName2 = OpenmrsUtil.getRuntimePropertiesFilePathName("nigeriamrs");*/
			//            if (OS.contains("WIN")) {
			//                //get the path for OpenMRS version 2.0.6
			//                appDirectory = System.getenv("WINDIR") + "\\system32\\config\\systemprofile\\Application Data";
			//                appDirectory = Paths.get(appDirectory, "OpenMRS", "openmrs-runtime.properties").toString();
			//                //check if the properties file exists
			//                File _f = new File(appDirectory);
			//                if (!_f.exists()) {
			//                    appDirectory = System.getenv("AppData");
			//                    // old implementation         appDirectory += "\\OpenMRS\\openmrs-runtime.properties";
			//                    appDirectory = Paths.get(appDirectory, "OpenMRS", "openmrs-runtime.properties").toString();
			//                }
			//
			//            } else {
			//                appDirectory = System.getProperty("user.home");
			//                // old implementation          appDirectory += "\\.OpenMRS\\openmrs-runtime.properties";
			//                appDirectory = Paths.get(appDirectory, ".OpenMRS", "openmrs-runtime.properties").toString();
			//            }
			//            //String propFileName = System.getProperty("OPENMRS_INSTALLATION_SCRIPT");
			//            if (null == appDirectory) {
			//                return null;
			//            }
			//            File f = new File(appDirectory);
			//            inputStream = new FileInputStream(f);
			//            //inputStream = this.getClass().getClassLoader().getResourceAsStream(propFileName);
			//
			//            /*Properties p = System.getProperties();
			//			Map<String, String> env = System.getenv();*/
			//            props.load(inputStream);
			//            // throw new FileNotFoundException("property file '" + appDirectory + "' not found in the classpath");
			//starts here
			Properties props = new Properties();
			props = OpenmrsUtil.getRuntimeProperties("openmrs");
			if (props == null) {
				props = OpenmrsUtil.getRuntimeProperties("openmrs-standalone");
				
			}
			
			result.setUsername(props.getProperty("connection.username"));
			result.setPassword(props.getProperty("connection.password"));
			result.setUrl(props.getProperty("connection.url"));
			
		}
		catch (Exception ex) {
			//LoggerUtils.write(Utils.class.getName(), ex.getMessage(), LogFormat.FATAL, LogLevel.live);
			System.out.print(ex);
		}
		
		return result;
		
	}
	
}
