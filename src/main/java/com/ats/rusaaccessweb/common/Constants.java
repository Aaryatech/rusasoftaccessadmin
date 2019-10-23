package com.ats.rusaaccessweb.common;
 
import org.springframework.http.client.support.BasicAuthorizationInterceptor;
import org.springframework.web.client.RestTemplate;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Font;
import com.itextpdf.text.Font.FontFamily;

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
	 
	 public static Font headFontData = new Font(FontFamily.TIMES_ROMAN, 12, Font.NORMAL, BaseColor.BLACK);
		public static Font tableHeaderFont = new Font(FontFamily.HELVETICA, 12, Font.BOLD, BaseColor.BLACK);
		public static final BaseColor tableHeaderFontBaseColor=BaseColor.WHITE;
		public static final BaseColor baseColorTableHeader=BaseColor.BLUE;

		public static final Font reportNameFont = new Font(FontFamily.TIMES_ROMAN, 14.0f, Font.UNDERLINE, BaseColor.BLACK);

		
		public static float marginLeft=50;
		public static float marginRight=45;
		public static float marginTop=50;
		public static float marginBottom=80;
		
		public static String facHOD = "1";
		public static String facDean = "2";
		public static String facProfessor = "3";
		public static String facAssProf = "4";
		public static String facAssistProf = "5";
		public static String facFaculty = "4,5";
		public static String facAccountant = "6";
		public static String facLibrarian = "7";
		public static String facReader = "8";
		public static String facPrincipal = "9";
		public static String facTrainPlace = "12";   //"10";
		public static String facExternlAct = "12";          //"11";
		public static String facIQAC = "12";
		public static Object sucess_msg;
		public static Object fail_msg;
		public static Object updt_msg;
	
} 