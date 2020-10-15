package com.ats.rusaaccessweb.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import com.ats.rusaaccessweb.common.AccessControll;
import com.ats.rusaaccessweb.common.Constants;
import com.ats.rusaaccessweb.common.DateConvertor;
import com.ats.rusaaccessweb.common.FormValidation;
import com.ats.rusaaccessweb.common.SessionKeyGen;
import com.ats.rusaaccessweb.common.XssEscapeUtils;
import com.ats.rusaaccessweb.model.Info;
import com.ats.rusaaccessweb.model.InstituteMaster;
import com.ats.rusaaccessweb.model.LoginResponse;
import com.ats.rusaaccessweb.model.ModuleJson;
import com.ats.rusaaccessweb.model.UserLogin;

@Controller
@Scope("session")
public class InstituteMasterController {

	@RequestMapping(value = "/getInstituteMasterByAishe", method = RequestMethod.GET)
	public @ResponseBody InstituteMaster getInstituteMasterByAishe(HttpServletRequest request,
			HttpServletResponse response) {
		RestTemplate rest = new RestTemplate();
		MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();

		InstituteMaster imaster = null;
		HttpSession session = request.getSession();
		try {

			String aisheCode = request.getParameter("aisheCode");

			map = new LinkedMultiValueMap<String, Object>();

			map.add("aisheCode", aisheCode);

			imaster = Constants.getRestTemplate().postForObject(Constants.url + "getInstituteMasterByAishe", map,
					InstituteMaster.class);
			if (imaster == null) {
				//System.err.println("Null IMASTER");
				imaster = new InstituteMaster();
				imaster.setMhInstId(0);
				session.setAttribute("mhInstId", 0);

			} else {
				String res = Constants.getRestTemplate().postForObject(Constants.url + "checkAisheCodeDuplication", map,
						String.class);
				if (res.equals("unique")) {
					//System.err.println("Unique not Present in Mh Institute ");
					session.setAttribute("mhInstId", imaster.getMhInstId());
				} else {
					//System.err.println("Already have  in ");
					imaster.setMhInstId(-1);
				}
			}

		} catch (Exception e) {
			System.err.println("Exce in imaster " + e.getMessage());
			e.printStackTrace();
		}
		//System.err.println("imaster " + imaster.toString());
		return imaster;

	}

	// Author -Sachin Handge
	// Created on -20-07-2020

	@RequestMapping(value = "/showAddMhInstituteMst", method = RequestMethod.GET)
	public String showAddMhInstituteMst(HttpServletRequest request, HttpServletResponse response, Model model) {
		String mav = new String();

		try {

			HttpSession session = request.getSession();
			try {
				session.removeAttribute("mhInstId");
			} catch (Exception e) {
				// TODO: handle exception
			}
			List<ModuleJson> newModuleList = (List<ModuleJson>) session.getAttribute("newModuleList");

			Info viewAccess = AccessControll.checkAccess("showAddMhInstituteMst", "showAddMhInstituteMst", "1", "0", "0", "0",
					newModuleList);

			if (viewAccess.isError() == false) {

				model.addAttribute("title", "Institute Master");
				mav = "master/add_inst_mst";

			} else {

				mav = "redirect:/accessDenied";
			}

		} catch (Exception e) {

			e.printStackTrace();
			mav = "redirect:/accessDenied";

		}
		return mav;
	}

	// saveMhInst

	@RequestMapping(value = "/saveMhInst", method = RequestMethod.POST)
	public String saveMhInst(HttpServletRequest request, HttpServletResponse response, Model model) {

		String mav = new String();

		try {
			HttpSession session = request.getSession();
			String token = request.getParameter("token");
			String key = (String) session.getAttribute("generatedKey");
			InstituteMaster imaster = null;
			if (token.trim().equals(key.trim())) {

				Info checkUserNameExist = new Info();

				List<ModuleJson> newModuleList = (List<ModuleJson>) session.getAttribute("newModuleList");
				LoginResponse userObj = (LoginResponse) session.getAttribute("userInfo");

				Info addAccess = AccessControll.checkAccess("showAddMhInstituteMst", "showAddMhInstituteMst", "0", "1", "0", "0",
						newModuleList);

				if (addAccess.isError() == false) {

					Date date = new Date();
					SimpleDateFormat dateTimeInGMT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

					String aisheCode = request.getParameter("aishe_code").trim().replaceAll("[ ]{2,}", " ");
					String instName = request.getParameter("inst_name").trim().replaceAll("[ ]{2,}", " ");
					String district = request.getParameter("dist").trim().replaceAll("[ ]{2,}", " ");

					Boolean error = false;

					if (FormValidation.Validaton(aisheCode, "") == true
							|| FormValidation.Validaton(instName, "") == true
							|| FormValidation.Validaton(district, "") == true) {

						error = true;
					}
					imaster = new InstituteMaster();

					imaster.setAffUniversity("-9");
					imaster.setAisheCode(aisheCode);
					imaster.setDistrict(district);
					imaster.setEstYear("-9");
					imaster.setInstName(instName);
					imaster.setMhInstId((int) session.getAttribute("mhInstId"));
					imaster.setTaluka("-9");

					InstituteMaster res = null;

					res = Constants.getRestTemplate().postForObject(Constants.url + "/saveInstituteMaster", imaster,
							InstituteMaster.class);

					if (res == null) {
						session.setAttribute("successMsg", "Failed To Add Institute Information !");
						session.setAttribute("errorMsg", "true");
					} else {
						session.setAttribute("successMsg", "Institute Information added successfully!");
						session.setAttribute("errorMsg", "false");
					}

				} else {
					session.setAttribute("successMsg", "Invalid Data");
					session.setAttribute("errorMsg", "true");
				}

				mav = "redirect:/showAddMhInstituteMst";

			} else {

				mav = "redirect:/accessDenied";
			}
			SessionKeyGen.changeSessionKey(request);
		} catch (Exception e) {
			SessionKeyGen.changeSessionKey(request);
			e.printStackTrace();
			HttpSession session = request.getSession();
			session.setAttribute("successMsg", "Failed To Add/Edit Institute Master !");
			session.setAttribute("errorMsg", "true");
		}
		return mav;
	}
}
