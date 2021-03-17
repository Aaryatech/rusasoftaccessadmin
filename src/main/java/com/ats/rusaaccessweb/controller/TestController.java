package com.ats.rusaaccessweb.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.text.StringEscapeUtils;
import org.jsoup.Jsoup;
import org.jsoup.safety.Whitelist;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.HtmlUtils;
import org.springframework.web.util.JavaScriptUtils;

@Controller
@Scope("session")
public class TestController {

	// JavaScriptUtils ju=new JavaScriptUtils();
	@RequestMapping(value = { "/kishore" }, method = RequestMethod.GET)
	public ModelAndView   kishore(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView model=new ModelAndView("abc");
		return model;
	}
	@RequestMapping(value = { "/testJS" }, method = RequestMethod.POST)
	public ModelAndView   testJS(HttpServletRequest request, HttpServletResponse response) {
		
		String inputStr=null;
		inputStr=request.getParameter("inputStr");
		ModelAndView model=new ModelAndView("testJsp");
		System.err.println("my  " +html2text(inputStr));
		
		
		
		
		
		
		return model;
	}
	
	public  String sendMe(String str) {
		System.err.println("In sendMe for "+str+"is " +JavaScriptUtils.javaScriptEscape(str));
		return JavaScriptUtils.javaScriptEscape(str);
	}
	
	
	public static String html2text(String html) {
	    return Jsoup.parse(html).text();
	}
	
	
	//a) in all input type text and textarea withoput ckeditor or any html editor in both project 
	public  String jsoupParse(String str) {
		return Jsoup.parse(str).text();
	}
	
	//b in all  textarea with ckeditor and eny html editor in both project
	public  String jsoupParseClean(String str) {
		return Jsoup.clean(str, Whitelist.relaxed());
	}
	
	
	//c)
	public static String escapeHtml4(String html) {
		return StringEscapeUtils.escapeHtml4(html);
	}
	//d)
	public static String unescapeHtml4(String html) {
		return StringEscapeUtils.unescapeHtml4(html);
	}
}
