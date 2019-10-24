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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.ats.rusaaccessweb.common.Constants;
import com.ats.rusaaccessweb.model.Cast;
import com.ats.rusaaccessweb.model.GetInstituteList;
import com.ats.rusaaccessweb.model.Program;
import com.ats.rusaaccessweb.model.ProgramType;
import com.ats.rusaaccessweb.model.dashb.AcademicYear;

@Controller
@Scope("session")
public class RusaReportController {
	List<GetInstituteList> instList = new ArrayList<>();

	@RequestMapping(value = "/showQueryBasedOtherReports", method = RequestMethod.GET)
	public ModelAndView showQueryBasedReports(HttpServletRequest request, HttpServletResponse response) {

		ModelAndView model = null;
		try {
			MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();

			model = new ModelAndView("Report/queryBasedOther");
			model.addObject("title", "Query Based Reports");
			GetInstituteList[] instArray = Constants.getRestTemplate().getForObject(Constants.url + "getAllInstitutes",
					GetInstituteList[].class);
			instList = new ArrayList<>(Arrays.asList(instArray));

			model.addObject("instList", instList);
			map = new LinkedMultiValueMap<String, Object>();
			map.add("type", 1);

			AcademicYear[] quolArray = Constants.getRestTemplate()
					.postForObject(Constants.url + "getAcademicYearListByTypeId", map, AcademicYear[].class);
			List<AcademicYear> acaYearList = new ArrayList<>(Arrays.asList(quolArray));

			model.addObject("acaYearList", acaYearList);

			ProgramType[] progTypes = Constants.getRestTemplate().getForObject(Constants.url + "getAllProgramType",
					ProgramType[].class);
			List<ProgramType> progTypeList = new ArrayList<>(Arrays.asList(progTypes));
			model.addObject("progTypeList", progTypeList);

			Cast[] catsArray = Constants.getRestTemplate().getForObject(Constants.url + "getAllCastCategory",
					Cast[].class);
			List<Cast> castList = new ArrayList<>(Arrays.asList(catsArray));
			System.err.println("castList " + castList.toString());

			model.addObject("castList", castList);

		} catch (Exception e) {

			System.err.println("Exce in showReports " + e.getMessage());
			e.printStackTrace();

		}

		return model;
	}

	@RequestMapping(value = "/getProgramTypeByProgram", method = RequestMethod.GET)
	public @ResponseBody List<Program> getProgramTypeByProgram(HttpServletRequest request,
			HttpServletResponse response) {
		List<Program> list = new ArrayList<>();
		HttpSession session = request.getSession();
		try {
			MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();

			int programType = Integer.parseInt(request.getParameter("programType"));
			int instituteId = Integer.parseInt(request.getParameter("instituteId"));

			System.out.println("prev inst id " + session.getAttribute("instituteId"));

			session.setAttribute("instituteId", instituteId);
			System.out.println("new inst id " + session.getAttribute("instituteId"));
			map.add("programTypeId", programType);
			map.add("instituteId", instituteId);

			Program[] program = Constants.getRestTemplate().postForObject(Constants.url + "/getProgramByProgramTypeId",
					map, Program[].class);
			list = new ArrayList<Program>(Arrays.asList(program));

		} catch (Exception e) {
			// System.err.println("Exce in getProgramTypeByProgram " + e.getMessage());
			e.printStackTrace();
		}

		return list;

	}
}
