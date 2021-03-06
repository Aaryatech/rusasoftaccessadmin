package com.ats.rusaaccessweb.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.ats.rusaaccessweb.common.AccessControll;
import com.ats.rusaaccessweb.common.Constants;
import com.ats.rusaaccessweb.common.EmailUtility;
import com.ats.rusaaccessweb.common.FormValidation;
import com.ats.rusaaccessweb.common.XssEscapeUtils;
import com.ats.rusaaccessweb.model.GetInstituteList;
import com.ats.rusaaccessweb.model.Info;
import com.ats.rusaaccessweb.model.ModuleJson;

@Controller
@Scope("session")
public class MailController {

	List<GetInstituteList> instList = new ArrayList<>();
	
	@RequestMapping(value = "/sendMail", method = RequestMethod.GET)
	public String callFunctionAccessControlle(HttpServletRequest request, HttpServletResponse response, Model model) {

		String ret = new String();

		try {

			HttpSession session = request.getSession();
			List<ModuleJson> newModuleList = (List<ModuleJson>) session.getAttribute("newModuleList");
			Info info = AccessControll.checkAccess("sendMail", "sendMail", "1", "0", "0", "0", newModuleList);

			// System.out.println(info);

			if (info.isError() == true) {
				ret = "redirect:/accessDenied";
			} else {
				ret = "mail/mailcontent";
				MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();

				GetInstituteList[] instArray = Constants.getRestTemplate().getForObject(Constants.url + "getAllInstitutes",
						GetInstituteList[].class);
				instList = new ArrayList<>(Arrays.asList(instArray));
				model.addAttribute("title", "Send Mail");
				model.addAttribute("instList", instList);
			}

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return ret;
	}
	static String senderEmail ="techrusa1@gmail.com";// "atsinfosoft@gmail.com";
	static String senderPassword ="@Rusamah";// "atsinfosoft@123";

	@RequestMapping(value = "/submitSendMail", method = RequestMethod.POST)
	public String submitSendMail(@RequestParam("files") List<MultipartFile> files, HttpServletRequest request,
			HttpServletResponse response) {

		String ret = new String();

		try {
			HttpSession session = request.getSession();
			String token = request.getParameter("token");
			String key = (String) session.getAttribute("generatedKey");
System.err.println("token " +token);
System.err.println("key " +key);
			if (token.trim().equals(key.trim())) {

				List<ModuleJson> newModuleList = (List<ModuleJson>) session.getAttribute("newModuleList");
				Info info = AccessControll.checkAccess("submitSendMail", "sendMail", "0", "1", "0", "0", newModuleList);

				// System.out.println(info);

				if (info.isError() == true) {
					ret = "redirect:/accessDenied";
				} else {
					ret = "redirect:/sendMail";

					String[] instituteId = request.getParameterValues("instituteId");
					String subject = request.getParameter("subject");
					String message = request.getParameter("message");
					String ccToAddress = request.getParameter("ccto");
					String emails = "-";

					for (int i = 0; i < instituteId.length; i++) {

						for (int j = 0; j < instList.size(); j++) {

							if (instList.get(j).getInstituteId() == Integer.parseInt(instituteId[i])) {

								emails = emails + "," + instList.get(j).getEmail();
								break;
							}

						}

					}
					// System.out.println("emails" + emails.substring(2, emails.length()));
					emails = emails.substring(2, emails.length());

					if (emails.split(",").length > 0 && FormValidation.Validaton(subject, "") == false) {

						Info send = EmailUtility.sendEmail(senderEmail,ccToAddress, senderPassword, emails,
								XssEscapeUtils.jsoupParse(subject), XssEscapeUtils.jsoupParseClean(message), files);

					}

				}
			} else {
				System.err.println("in else");
				ret = "redirect:/accessDenied";
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return ret;
	}
	
	@RequestMapping(value = "/checkDate", method = RequestMethod.GET)
	public String checkDate(HttpServletRequest request, HttpServletResponse response, Model model) {

		String ret = new String();

		try {
 
			String date = request.getParameter("date");
			
				ret = "mail/mailcontent";
				Boolean booln = FormValidation.Validaton(date, "date");
				
				System.out.println(booln);

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return ret;
	}

}
