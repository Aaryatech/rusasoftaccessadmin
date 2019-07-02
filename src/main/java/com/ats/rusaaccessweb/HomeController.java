package com.ats.rusaaccessweb;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import com.ats.rusaaccessweb.common.Constants;
import com.ats.rusaaccessweb.model.LoginResponse;
import com.ats.rusaaccessweb.model.ModuleJson; 
 
 

/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {
	
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	
	/**
	 * Simply selects the home view to render by returning its name.
	 */
	/*@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Locale locale, Model model) {
		logger.info("Welcome home! The client locale is {}.", locale);
		
		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);
		
		String formattedDate = dateFormat.format(date);
		
		model.addAttribute("serverTime", formattedDate );
		
		return "home";
	}*/
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String showLoginForm(HttpServletRequest request, HttpServletResponse response,Model model) {

		String mav = new String();
		 
		try {

			mav = "login" ;
			 
		} catch (Exception e) {

			//System.err.println("exception In showCMSForm at home Contr" + e.getMessage());

			e.printStackTrace();

		}

		return mav;

	}
	
	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public String logout(HttpSession session) {
		// System.out.println("User Logout");

		session.invalidate();
		return "redirect:/";
	}
	
	@RequestMapping("/loginProcess")
	public String helloWorld(HttpServletRequest request, HttpServletResponse res, Model model) throws IOException {
		 
		String mav =  "login" ;
		HttpSession session = request.getSession();

		String name = request.getParameter("username");
		String password = request.getParameter("userpassword");
		 
		res.setContentType("text/html");
		 

		try {
		

			if (name.equalsIgnoreCase("") || password.equalsIgnoreCase("") || name == null || password == null) {

				mav = "login" ;
				model.addAttribute("msg", "Enter  Login Credentials");

			} else {
				MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();
				map.add("username", name);
				map.add("password", password);
				map.add("isBlock", 1);

				LoginResponse userObj = Constants.getRestTemplate().postForObject(Constants.url + "login", map, LoginResponse.class);
			 

				if (userObj.getIsError() == false) {

					map = new LinkedMultiValueMap<String, Object>();
					map.add("roleId", userObj.getRoleId()); 

					List<ModuleJson> newModuleList = new ArrayList<>();
					
					try {
						ParameterizedTypeReference<List<ModuleJson>> typeRef = new ParameterizedTypeReference<List<ModuleJson>>() {
						};
						ResponseEntity<List<ModuleJson>> responseEntity = Constants.getRestTemplate().exchange(
								Constants.url + "getRoleJsonByRoleId", HttpMethod.POST, new HttpEntity<>(map),
								typeRef);

						 newModuleList = responseEntity.getBody();
 
					} catch (Exception e) {
						e.printStackTrace();
					}
					  
					session.setAttribute("newModuleList", newModuleList);
					mav = "redirect:/showRoleList" ;
				}

			}
		} catch (Exception e) {
			e.printStackTrace();

			mav = "login" ;
			model.addAttribute("msg", "Enter Valid  Login Credentials");
			 
		}

		 
		return mav;

	}
	
}
