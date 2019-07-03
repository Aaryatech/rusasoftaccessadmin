package com.ats.rusaaccessweb.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import com.ats.rusaaccessweb.common.AccessControll;
import com.ats.rusaaccessweb.common.Constants;
import com.ats.rusaaccessweb.model.GetInstituteList;
import com.ats.rusaaccessweb.model.Info;
import com.ats.rusaaccessweb.model.Institute;
import com.ats.rusaaccessweb.model.LoginResponse;
import com.ats.rusaaccessweb.model.ModuleJson;

@Controller
@Scope("session")
public class AccessMasterController {
	
	// instituteList

		@RequestMapping(value = "/showInstituteList", method = RequestMethod.GET)
		public ModelAndView showInstituteList(HttpServletRequest request, HttpServletResponse response) {

			ModelAndView model = null;
			try {

				RestTemplate restTemplate = new RestTemplate();

				HttpSession session = request.getSession();

				List<ModuleJson> newModuleList = (List<ModuleJson>) session.getAttribute("newModuleList");

				Info viewAccess = AccessControll.checkAccess("showInstituteList", "showInstituteList", "1", "0", "0", "0",
						newModuleList);

				if (viewAccess.isError() == false) {

					Info addAccess = AccessControll.checkAccess("showInstituteList", "showInstituteList", "0", "1", "0",
							"0", newModuleList);

					Info editAccess = AccessControll.checkAccess("showInstituteList", "showInstituteList", "0", "0", "1",
							"0", newModuleList);

					Info deleteAccess = AccessControll.checkAccess("showInstituteList", "showInstituteList", "0", "0", "0",
							"1", newModuleList);

					model = new ModelAndView("master/instituteList");

					model.addObject("title", "Verified Institute");

					Institute editInst = new Institute();

					model.addObject("editInst", editInst);

					model.addObject("viewAccess", viewAccess);
					if (addAccess.isError() == false)
						model.addObject("addAccess", 0);

					if (editAccess.isError() == false) {
						System.err.println("Edit acce ==0");
						model.addObject("editAccess", 0);

					}

					if (deleteAccess.isError() == false)
						model.addObject("deleteAccess", 0);

					MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();

					GetInstituteList[] instArray = restTemplate.getForObject(Constants.url + "getAllInstitutes",
							GetInstituteList[].class);
					List<GetInstituteList> instList = new ArrayList<>(Arrays.asList(instArray));

					model.addObject("instList", instList);

				} else {

					model = new ModelAndView("accessDenied");
				}

			} catch (Exception e) {

				System.err.println("exception In showInstituteList at Master Contr" + e.getMessage());

				e.printStackTrace();

			}

			return model;

		}


	@RequestMapping(value = "/showPendingInstitute", method = RequestMethod.GET)
	public ModelAndView showPendingInstitute(HttpServletRequest request, HttpServletResponse response) {
		System.out.println(" Got It ");
		ModelAndView model = null;
		try {

			model = new ModelAndView("master/pendingInstituteList");

			 
			HttpSession session = request.getSession();

			List<ModuleJson> newModuleList = (List<ModuleJson>) session.getAttribute("newModuleList");

			Info viewAccess = AccessControll.checkAccess("showPendingInstitute", "showPendingInstitute", "1", "0", "0",
					"0", newModuleList);

			if (viewAccess.isError() == false) {

				Info addAccess = AccessControll.checkAccess("showPendingInstitute", "showPendingInstitute", "0", "1",
						"0", "0", newModuleList);

				Info delete = AccessControll.checkAccess("showPendingInstitute", "showPendingInstitute", "0", "0", "0",
						"1", newModuleList);

				GetInstituteList[] instArray = Constants.getRestTemplate().getForObject(Constants.url + "getAllPendingInstitutes",
						GetInstituteList[].class);
				List<GetInstituteList> instList = new ArrayList<>(Arrays.asList(instArray));

				model.addObject("pendInstList", instList);

				model.addObject("title", "Institute Verification Pending List");

				if (addAccess.isError() == false) {
					model.addObject("addAccess", 0);

				}
				if (delete.isError() == false) {
					//System.out.println(" delete   Accessable ");
					model.addObject("deleteAccess", 0);

				}

			}

			else {

				model = new ModelAndView("accessDenied");
			}
		} catch (Exception e) {

			System.err.println("exception In pendingInstituteList at AccessMasterController" + e.getMessage());

			e.printStackTrace();

		}

		return model;
	}

	@RequestMapping(value = "/viewInstitutes/{instId}", method = RequestMethod.GET)
	public ModelAndView viewInstitutes(HttpServletRequest request, HttpServletResponse response,
			@PathVariable int instId) {
		ModelAndView model = new ModelAndView();
		MultiValueMap<String, Object> map = null;
		String a = null;
		try {
			HttpSession session = request.getSession();
			/*
			 * List<ModuleJson> newModuleList = (List<ModuleJson>)
			 * session.getAttribute("newModuleList"); Info view =
			 * AccessControll.checkAccess("showPendingInstitute", "showPendingInstitute",
			 * "1", "0", "0", "0", newModuleList);
			 * 
			 * if (view.isError() == true) {
			 * 
			 * model = new ModelAndView("accessDenied");
			 * 
			 * } else {
			 */

			model = new ModelAndView("master/showInstitute");
		
			map = new LinkedMultiValueMap<String, Object>();
			map.add("instId", instId);

			Institute showInst = Constants.getRestTemplate().postForObject(Constants.url + "/showInstituteData", map, Institute.class);

			model.addObject("showInst", showInst);
			/*
			 * Info delete = AccessControll.checkAccess("showPendingInstitute",
			 * "showPendingInstitute", "0", "0", "0", "1", newModuleList);
			 * 
			 * 
			 * 
			 * }
			 */
		} catch (Exception e) {
			e.printStackTrace();
		}

		return model;

	}
	
	// Approve Inst

		@RequestMapping(value = "/approveInstitutes/{instId}", method = RequestMethod.GET)
		public String approveInstitutes(HttpServletRequest request, HttpServletResponse response,
				@PathVariable int instId) {

			String redirect = null;
			try {
				HttpSession session = request.getSession();

				LoginResponse userObj = (LoginResponse) session.getAttribute("userObj");

				List<ModuleJson> newModuleList = (List<ModuleJson>) session.getAttribute("newModuleList");

				Info addAccess = AccessControll.checkAccess("approveInstitutes/{instId}", "showInstituteList", "1", "0",
						"0", "0", newModuleList);

				if (addAccess.isError() == true) {
					redirect = "redirect:/accessDenied";
				} else {

					MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();
					if (instId == 0) {

						System.err.println("Multiple records approve ");
						String[] instIds = request.getParameterValues("instIds");
						//System.out.println("id are" + instIds);

						StringBuilder sb = new StringBuilder();
						for (int i = 0; i < instIds.length; i++) {
							sb = sb.append(instIds[i] + ",");

						}
						String instIdList = sb.toString();
						instIdList = instIdList.substring(0, instIdList.length() - 1);

						map.add("instIdList", instIdList);
						map.add("aprUserId", userObj.getUserId());
					} else {
						map.add("aprUserId", userObj.getUserId());

						System.err.println("Single Record delete ");
						map.add("instIdList", instId);
					}

					Info errMsg = Constants.getRestTemplate().postForObject(Constants.url + "approveInstitutes", map, Info.class);
					redirect = "redirect:/showInstituteList";
				}
			} catch (Exception e) {

				System.err.println(" Exception In deleteInstitutes at Master Contr " + e.getMessage());

				e.printStackTrace();

			}

			return redirect;

		}
}
