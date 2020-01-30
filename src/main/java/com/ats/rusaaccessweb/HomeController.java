package com.ats.rusaaccessweb;

import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.net.InetAddress;
import java.security.MessageDigest;
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
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.tags.HtmlEscapeTag;
import org.springframework.web.util.JavaScriptUtils;

import com.ats.rusaaccessweb.common.Constants;
import com.ats.rusaaccessweb.common.DateConvertor;
import com.ats.rusaaccessweb.model.AdminLoginLog;
import com.ats.rusaaccessweb.model.LoginResponse;
import com.ats.rusaaccessweb.model.ModuleJson;
import com.ats.rusaaccessweb.model.dashb.GetCountsForDash;
import com.ats.rusaaccessweb.model.dashb.GetInstInfoCount;
import com.ats.rusaaccessweb.model.dashb.QualityIniGraphResponse;

/**
 * Handles requests for the application home page.
 */
@Controller
@Scope("session")
public class HomeController {

	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);

	/**
	 * Simply selects the home view to render by returning its name.
	 */
	/*
	 * @RequestMapping(value = "/", method = RequestMethod.GET) public String
	 * home(Locale locale, Model model) {
	 * logger.info("Welcome home! The client locale is {}.", locale);
	 * 
	 * Date date = new Date(); DateFormat dateFormat =
	 * DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);
	 * 
	 * String formattedDate = dateFormat.format(date);
	 * 
	 * model.addAttribute("serverTime", formattedDate );
	 * 
	 * return "home"; }
	 */

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String showLoginForm(HttpServletRequest request, HttpServletResponse response, Model model) {

		/*
		 * String text = "<%@ abcd  %>"; System.err.println("text before  " + text);
		 * text = text.replaceAll("\\<.*?\\>", ""); System.err.println("text " + text);
		 */

		String mav = new String();

		try {

			mav = "login";

		} catch (Exception e) {
			mav = "login";
			// System.err.println("exception In showCMSForm at home Contr" +
			// e.getMessage());

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

	LinkedHashMap<String, Integer> tempMap = new LinkedHashMap<>();
	LinkedHashMap<String, Calendar> userTimeMap = new LinkedHashMap<>();

	@RequestMapping("/loginProcess")
	public String helloWorld(HttpServletRequest request, HttpServletResponse res, Model model) throws IOException {

		String mav = "login";
		HttpSession session = request.getSession();

		String name = request.getParameter("username");
		String password = request.getParameter("userpassword");

		res.setContentType("text/html");

		try {

			String captcha = session.getAttribute("captcha_security").toString();
			String verifyCaptcha = request.getParameter("captcha");

			if (captcha.equals(verifyCaptcha)) {

				if (name.equalsIgnoreCase("") || password.equalsIgnoreCase("") || name == null || password == null) {
					Random randChars = new Random();
					String sImageCode = (Long.toString(Math.abs(randChars.nextLong()), 36)).substring(0, 6);
					session.setAttribute("captcha_security", sImageCode);
					mav = "login";
					model.addAttribute("msg", "Enter  Login Credentials");

				} else {
					int validLogin = 0;
					Calendar time = Calendar.getInstance();
					time = DateConvertor.getCurTime();
					try {
						time = userTimeMap.get(name);
					} catch (Exception e) {
						time = DateConvertor.getCurTime();
						System.err.println("in catch " + e.getMessage());
						e.printStackTrace();
					}
					System.err.println("time " + time + "DateConvertor.getCurTime()  time " + DateConvertor.getCurTime());

					if (userTimeMap.get(name) == null) {
						validLogin = 1;

					} else if (DateConvertor.getCurTime().getTime().after(time.getTime())) {
						System.err.println("time exceed");
						validLogin = 1;
					}else {
						model.addAttribute("msg",
								"Your have crossed no. of invalid login attempts. Account blocked for some time!!");
				
						validLogin =0;
					}

					if (validLogin == 1) {

						/*
						 * removed as discussed with Sumit Suma Soft Sir MessageDigest md =
						 * MessageDigest.getInstance("MD5"); byte[] messageDigest =
						 * md.digest(password.getBytes()); BigInteger number = new BigInteger(1,
						 * messageDigest); String hashtext = number.toString(16);
						 */

						MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();
						map.add("username", name);
						map.add("password", password);
						map.add("isBlock", 1);

						LoginResponse userObj = Constants.getRestTemplate().postForObject(Constants.url + "login", map,
								LoginResponse.class);

						if (userObj.getIsError() == false) {
							tempMap.remove(userObj.getEmail());
							session.setAttribute("userInfo", userObj);

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
							mav = "redirect:/dashboard";

							session.setAttribute("sessionModuleId", 0);
							session.setAttribute("sessionSubModuleId", 0);

							InetAddress addr = InetAddress.getByName(request.getRemoteAddr());
							String hostName = addr.getHostName();
							String userAgent = request.getHeader("User-Agent");

							Date date = new Date();
							SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
							AdminLoginLog saveLoginLogs = new AdminLoginLog();
							saveLoginLogs.setIpAddress(hostName);
							saveLoginLogs.setUserAgent(userAgent);
							saveLoginLogs.setLoginDate(sf.format(date));
							saveLoginLogs.setUserId(userObj.getUserId());

							AdminLoginLog resp = Constants.getRestTemplate()
									.postForObject(Constants.url + "/rusaLoginLog", saveLoginLogs, AdminLoginLog.class);

						} // end of if time match

					} else {
						Random randChars = new Random();
						String sImageCode = (Long.toString(Math.abs(randChars.nextLong()), 36)).substring(0, 6);
						session.setAttribute("captcha_security", sImageCode);
						model.addAttribute("msg", "Enter  Login Credentials");
					}

				}
			} else {
				Random randChars = new Random();
				String sImageCode = (Long.toString(Math.abs(randChars.nextLong()), 36)).substring(0, 6);
				session.setAttribute("captcha_security", sImageCode);

				mav = "login";
				model.addAttribute("msg", "Invalid Text");

				if (tempMap.containsKey(name)) {
					tempMap.put(name, tempMap.get(name) + 1);
				} else {
					tempMap.put(name, 1);
				}

				if (tempMap.get(name) > 4) {
					userTimeMap.put(name, DateConvertor.getTimePlus30Min());

					model.addAttribute("msg",
							"Your have crossed no. of invalid login attempts. Account blocked for some time!!");
				}

				System.err.println("map " + tempMap.toString());

			}
		} catch (Exception e) {
			e.printStackTrace();

			mav = "login";
			model.addAttribute("msg", "Enter Valid  Login Credentials");

		}
		return mav;

	}

	@RequestMapping(value = "/dashboard", method = RequestMethod.GET)
	public ModelAndView dashboard(HttpServletRequest request, HttpServletResponse response) {

		ModelAndView model = new ModelAndView();
		try {

			model = new ModelAndView("welcome");
			model.addObject("title", "DASHBOARD");

			GetCountsForDash dashBoardCounts = Constants.getRestTemplate()
					.getForObject(Constants.url + "/getInstituteCount", GetCountsForDash.class);
			// System.out.println("InstCount="+dashBoardCounts.getCount1());
			model.addObject("dashBoardCounts", dashBoardCounts);

			/****************************
			 * Mahendra 01/08/2019
			 *********************************/

			GetCountsForDash dashRegInstCount = Constants.getRestTemplate()
					.getForObject(Constants.url + "/getRegInstituteCount", GetCountsForDash.class);
			model.addObject("dashRegInstCount", dashRegInstCount);

			GetCountsForDash dashAutoInstCount = Constants.getRestTemplate()
					.getForObject(Constants.url + "/getAutoInstituteCount", GetCountsForDash.class);
			model.addObject("dashAutoInstCount", dashAutoInstCount);

			GetInstInfoCount dashInstInfoCount = Constants.getRestTemplate()
					.getForObject(Constants.url + "/getInstituteOtherInfoCount", GetInstInfoCount.class);
			model.addObject("dashInstInfoCount", dashInstInfoCount);

		} catch (Exception e) {

			System.err.println("exception In Rusa Access Daashboard at home Contr" + e.getMessage());

			e.printStackTrace();

		}

		return model;
	}

	@RequestMapping(value = "/getDashboardGraph", method = RequestMethod.GET)
	public @ResponseBody QualityIniGraphResponse getDashboardGraph(HttpServletRequest request,
			HttpServletResponse response) {

		QualityIniGraphResponse qualityIniGraphResponse = new QualityIniGraphResponse();

		try {

			qualityIniGraphResponse = Constants.getRestTemplate().getForObject(Constants.url + "/getAllQualIniGraph1",
					QualityIniGraphResponse.class);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return qualityIniGraphResponse;
	}

	@RequestMapping(value = "/setSubModId", method = RequestMethod.GET)
	public @ResponseBody void setSubModId(HttpServletRequest request, HttpServletResponse response) {
		int subModId = Integer.parseInt(request.getParameter("subModId"));
		int modId = Integer.parseInt(request.getParameter("modId"));

		HttpSession session = request.getSession();
		session.setAttribute("sessionModuleId", modId);
		session.setAttribute("sessionSubModuleId", subModId);
		session.removeAttribute("exportExcelList");
	}

	@RequestMapping(value = "/sessionTimeOut", method = RequestMethod.GET)
	public String sessionTimeOut(HttpSession session) {
		System.out.println("User Logout");

		session.invalidate();
		return "redirect:/";
	}

}
