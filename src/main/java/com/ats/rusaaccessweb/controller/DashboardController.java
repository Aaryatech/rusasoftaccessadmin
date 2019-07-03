package com.ats.rusaaccessweb.controller;

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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.ats.rusaaccessweb.common.Constants;
import com.ats.rusaaccessweb.model.dashb.QualityIniGraphResponse;

@Controller
@Scope("session")
public class DashboardController {

	@RequestMapping(value = "/showAccredationGraphs", method = RequestMethod.GET)
	public @ResponseBody QualityIniGraphResponse showAccredationGraphs(HttpServletRequest request,
			HttpServletResponse response) {

		QualityIniGraphResponse allAccregp = new QualityIniGraphResponse();

		try {

			allAccregp = Constants.getRestTemplate().getForObject(Constants.url + "/getAllQualIniGraph1",
					QualityIniGraphResponse.class);
 
		} catch (Exception e) {

			System.err.println("exception In editJournal at Iqac Contr" + e.getMessage());

			e.printStackTrace();

		}
		return allAccregp;
	}
	
	

}
