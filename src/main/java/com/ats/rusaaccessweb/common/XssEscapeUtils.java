package com.ats.rusaaccessweb.common;

import org.jsoup.Jsoup;

import org.jsoup.safety.Whitelist;

/*<dependency>
<groupId>org.jsoup</groupId>
<artifactId>jsoup</artifactId>
<version>1.8.3</version>
</dependency>
<!-- https://mvnrepository.com/artifact/org.apache.commons/commons-lang3 -->
<dependency>
<groupId>org.apache.commons</groupId>
<artifactId>commons-text</artifactId>
<version>1.4</version>
</dependency>*/
public class XssEscapeUtils {
	// a) in all input type text and textarea without ckeditor or any html editor
	// in both project
	public static String jsoupParse(String str) {
		str = str.replaceAll("\\<.*?\\>", "");
		return Jsoup.parse(str).text();
	}

	// b) in all textarea with ckeditor or any html editor in both project
	public static String jsoupParseClean(String str) {
		return Jsoup.clean(str, Whitelist.relaxed());
	}

	/*
	 * public static String jsoupParse(String str) { return Jsoup.parse(str).text();
	 * }
	 * 
	 * public static String escapeHtml4(String html) { return
	 * StringEscapeUtils.escapeHtml4(html); }
	 * 
	 * public static String unescapeHtml4(String html) { return
	 * StringEscapeUtils.unescapeHtml4(html); }
	 */
}
