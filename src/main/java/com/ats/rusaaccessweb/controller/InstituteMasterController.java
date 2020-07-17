package com.ats.rusaaccessweb.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import com.ats.rusaaccessweb.common.Constants;
import com.ats.rusaaccessweb.model.InstituteMaster;


public class InstituteMasterController {

	
	@RequestMapping(value = "/getInstituteMasterByAishe", method = RequestMethod.GET)
	public @ResponseBody InstituteMaster getInstituteMasterByAishe(HttpServletRequest request,
			HttpServletResponse response) {
		RestTemplate rest = new RestTemplate();
		MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();

		InstituteMaster imaster = null;

		try {

			String aisheCode = request.getParameter("aishe_code");

			map = new LinkedMultiValueMap<String, Object>();

			map.add("aisheCode", aisheCode);

			imaster = Constants.getRestTemplate().postForObject(Constants.url + "getInstituteMasterByAishe", map, InstituteMaster.class);
			if (imaster == null) {
				System.err.println("Null IMASTER");
				imaster = new InstituteMaster();
				imaster.setMhInstId(-1);
			}

			String res = Constants.getRestTemplate().postForObject(Constants.url + "checkAisheCodeDuplication", map, String.class);
			if (res.equals("unique")) {
				System.err.println("Unique");
			} else {
				System.err.println("Already Exists");
				imaster = new InstituteMaster();
			}

		} catch (Exception e) {
			System.err.println("Exce in imaster " + e.getMessage());
			e.printStackTrace();
		}

		return imaster;

	}
	
}
