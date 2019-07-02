package com.ats.rusaaccessweb.common;
 
import org.springframework.http.client.support.BasicAuthorizationInterceptor;
import org.springframework.web.client.RestTemplate;

public class Constants {

 
	//Web Api Path url
	public static final String url = "http://localhost:8094/";
	//public static final String url = "http://132.148.143.124:8080/rusasoftwebapi/";
	//public static final String url="http://exhibition.aaryatechindia.in:3209/rusasoftwebapi/";
	
	//Report Save Url
	//public static final String REPORT_SAVE = "/home/tomcataaryatechi/exhibition.aaryatechindia.in/tomcat-8.0.18/webapps/tempadmin/rusa_report2019.pdf";
	public static final String REPORT_SAVE = "/home/maddy/ats-11/rusa_report2019.pdf";


	public static RestTemplate rest = new RestTemplate();
	 public static RestTemplate getRestTemplate() {
			rest=new RestTemplate();
			rest.getInterceptors().add(new BasicAuthorizationInterceptor("aaryatech", "Aaryatech@1cr"));
			return rest;

			} 
	
} 