package com.ats.rusaaccessweb.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.context.annotation.Scope;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import com.ats.rusaaccessweb.common.AccessControll;
import com.ats.rusaaccessweb.common.Constants;
import com.ats.rusaaccessweb.common.SessionKeyGen;
import com.ats.rusaaccessweb.common.XssEscapeUtils;
import com.ats.rusaaccessweb.model.AccessRightModule;
import com.ats.rusaaccessweb.model.AccessRightModuleList;
import com.ats.rusaaccessweb.model.AssignRoleDetailList;
import com.ats.rusaaccessweb.model.CreatedRoleList;
import com.ats.rusaaccessweb.model.Info;
import com.ats.rusaaccessweb.model.ModuleJson;
import com.ats.rusaaccessweb.model.SubModuleJson;
import com.ats.rusaaccessweb.model.UserList;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;

@Controller
@Scope("session")
public class AccessRightController {

	/*
	 * <dependency> <groupId>com.google.code.gson</groupId>
	 * <artifactId>gson</artifactId> <version>2.3.1</version> </dependency>
	 */

	public AccessRightModuleList accessRightModuleList;
	int isError = 0;

	@RequestMapping(value = "/callFunctionAccessControlle", method = RequestMethod.GET)
	public String callFunctionAccessControlle(HttpServletRequest request, HttpServletResponse response) {

		try {
			HttpSession session = request.getSession();
			List<ModuleJson> newModuleList = (List<ModuleJson>) session.getAttribute("newModuleList");
			Info info = AccessControll.checkAccess("showRegisterInstitute", "showInstituteList", "0", "1", "0", "0",
					newModuleList);

			// System.out.println(info);

			if (info.isError() == true) {
				return "redirect:/accessDenied";
			}

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return "redirect:/showCreateRole";
	}

	@RequestMapping(value = "/accessDenied", method = RequestMethod.GET)
	public ModelAndView accessDenied(HttpServletRequest request, HttpServletResponse response) {

		ModelAndView model = new ModelAndView("accessDenied");
		try {

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return model;
	}

	@RequestMapping(value = "/sampleForm", method = RequestMethod.GET)
	public ModelAndView sampleForm(HttpServletRequest request, HttpServletResponse response) {

		ModelAndView model = new ModelAndView("sampleForm");
		try {

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return model;
	}

	@RequestMapping(value = "/showCreateRole", method = RequestMethod.GET)
	public ModelAndView showAccessRight(HttpServletRequest request, HttpServletResponse response) {

		ModelAndView model = new ModelAndView("accessRight/createRole");

		// Constants.mainAct = 22;
		// Constants.subAct = 106;
		try {
			HttpSession session = request.getSession();
			List<ModuleJson> newModuleList = (List<ModuleJson>) session.getAttribute("newModuleList");
			Info info = AccessControll.checkAccess("showCreateRole", "showCreateRole", "1", "0", "0", "0",
					newModuleList);

			if (info.isError() == false) {
				accessRightModuleList = Constants.getRestTemplate()
						.getForObject(Constants.url + "getAllModuleAndSubModule", AccessRightModuleList.class);
				// System.out.println("Access List " + accessRightModuleList.toString());
				model.addObject("allModuleList", accessRightModuleList.getAccessRightModuleList());
				model.addObject("title", "Create Role");
				/* isError = 0; */
			} else {
				model = new ModelAndView("accessDenied");
			}

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return model;
	}

	@RequestMapping(value = "/getSubmoduleList", method = RequestMethod.GET)
	public @ResponseBody List<Integer> getSubmoduleList(HttpServletRequest request, HttpServletResponse response) {

		List<Integer> list = new ArrayList<>();
		try {

			int moduleId = Integer.parseInt(request.getParameter("moduleId"));
			// System.out.println(moduleId);
			for (int i = 0; i < accessRightModuleList.getAccessRightModuleList().size(); i++) {

				if (accessRightModuleList.getAccessRightModuleList().get(i).getModuleId() == moduleId) {

					for (int j = 0; j < accessRightModuleList.getAccessRightModuleList().get(i)
							.getAccessRightSubModuleList().size(); j++) {

						list.add(accessRightModuleList.getAccessRightModuleList().get(i).getAccessRightSubModuleList()
								.get(j).getSubModuleId());
					}
					break;
				}
			}

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return list;

	}

	@RequestMapping(value = "/showRoleList", method = RequestMethod.GET)
	public ModelAndView showRoleList(HttpServletRequest request, HttpServletResponse response) {

		ModelAndView model = new ModelAndView("accessRight/roleList");

		try {

			HttpSession session = request.getSession();
			List<ModuleJson> newModuleList = (List<ModuleJson>) session.getAttribute("newModuleList");
			Info info = AccessControll.checkAccess("showRoleList", "showRoleList", "1", "0", "0", "0", newModuleList);

			if (info.isError() == false) {

				CreatedRoleList createdRoleList = Constants.getRestTemplate()
						.getForObject(Constants.url + "getAllAccessRole", CreatedRoleList.class);
				// System.out.println("Access List " + createdRoleList.toString());
				model.addObject("createdRoleList", createdRoleList.getAssignRoleDetailList());
				model.addObject("title", "Roles List");

			} else {
				model = new ModelAndView("accessDenied");
			}

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return model;
	}

	@RequestMapping(value = "/deleteRole/{roleId}", method = RequestMethod.GET)
	public String deleteFlavour(@PathVariable int roleId, HttpServletRequest request, HttpServletResponse response) {

		ModelAndView mav = new ModelAndView("accessRight/roleList");
		HttpSession session = request.getSession();
		List<ModuleJson> newModuleList = (List<ModuleJson>) session.getAttribute("newModuleList");
		Info info1 = AccessControll.checkAccess("showRoleList", "showRoleList", "0", "0", "0", "1", newModuleList);

		if (info1.isError() == false) {

			MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();
			map.add("roleId", roleId);

			Info errorResponse = Constants.getRestTemplate().postForObject(Constants.url + "deleteRole", map,
					Info.class);
			// System.out.println(errorResponse.toString());

			if (errorResponse.isError()) {
				return "redirect:/showRoleList";

			} else {
				return "redirect:/showRoleList";

			}
		} else {
			return "redirect:/accessDenied";
		}
	}

	/*
	 * @RequestMapping(value = "/submitCreateRole", method = RequestMethod.POST)
	 * public String submitAssignRole(HttpServletRequest request,
	 * HttpServletResponse response) {
	 * 
	 * System.err.println("inside submit create role  "); List<AccessRightModule>
	 * accessRightModule = accessRightModuleList.getAccessRightModuleList(); //
	 * List<AccessRightModule> newModuleList=new ArrayList<>(); List<ModuleJson>
	 * moduleJsonList = new ArrayList<>(); System.out.println("Access List " +
	 * accessRightModuleList.toString());
	 * 
	 * for (int i = 0; i < accessRightModule.size(); i++) { List<SubModuleJson>
	 * subModuleJsonList = new ArrayList<>();
	 * 
	 * boolean isPresent = false;
	 * 
	 * List<AccessRightSubModule> accessRightSubModuleList =
	 * accessRightModule.get(i) .getAccessRightSubModuleList(); String[] subModuleId
	 * = null; for (int j = 0; j < accessRightSubModuleList.size(); j++) {
	 * 
	 * try { subModuleId =
	 * request.getParameterValues(accessRightSubModuleList.get(j).getSubModuleId() +
	 * "" + accessRightModule.get(i).getModuleId()); } catch (Exception e) {
	 * System.err.println("Exception in getting subModule Id " + e.getMessage());
	 * e.printStackTrace(); } //
	 * 
	 * System.err.println(" subModuleId =====" + subModuleId); String view =
	 * "hidden"; String add = "hidden"; String edit = "hidden"; String delete =
	 * "hidden"; if (subModuleId != null) {
	 * System.err.println("Inside subModuleId != null" + subModuleId);
	 * 
	 * System.err.println("Length = " + subModuleId.length);
	 * 
	 * for (int p = 0; p < subModuleId.length; p++) {
	 * System.err.println("Sub Mod Id Checked for Module Id : " +
	 * accessRightModule.get(i).getModuleName() + "" + subModuleId[p]);
	 * 
	 * } AccessRightSubModule accessRightSubModule =
	 * accessRightSubModuleList.get(j);
	 * 
	 * SubModuleJson subModuleJson = new SubModuleJson();
	 * 
	 * subModuleJson.setModuleId(accessRightSubModule.getModuleId());
	 * subModuleJson.setSubModuleId(accessRightSubModule.getSubModuleId());
	 * subModuleJson.setSubModuleDesc(accessRightSubModule.getSubModuleDesc());
	 * subModuleJson.setSubModuleMapping(accessRightSubModule.getSubModuleMapping())
	 * ; subModuleJson.setSubModulName(accessRightSubModule.getSubModulName());
	 * subModuleJson.setType(accessRightSubModule.getType());
	 * 
	 * for (int k = 0; k < subModuleId.length; k++) { if
	 * (subModuleId[k].equalsIgnoreCase("view")) { view = new String("visible"); }
	 * else if (subModuleId[k].equalsIgnoreCase("add")) { add = new
	 * String("visible"); } else if (subModuleId[k].equalsIgnoreCase("edit")) { edit
	 * = new String("visible"); } else if
	 * (subModuleId[k].equalsIgnoreCase("delete")) { delete = new String("visible");
	 * } } isPresent = true; subModuleJson.setView(view);
	 * subModuleJson.setEditReject(edit); subModuleJson.setAddApproveConfig(add);
	 * subModuleJson.setDeleteRejectApprove(delete);
	 * subModuleJsonList.add(subModuleJson); } } if (isPresent) {
	 * 
	 * AccessRightModule module = accessRightModule.get(i); ModuleJson moduleJson =
	 * new ModuleJson();
	 * 
	 * moduleJson.setModuleId(module.getModuleId());
	 * moduleJson.setModuleDesc(module.getModuleDesc());
	 * moduleJson.setModuleName(module.getModuleName());
	 * moduleJson.setSubModuleJsonList(subModuleJsonList);
	 * 
	 * moduleJsonList.add(moduleJson);
	 * 
	 * } }
	 * 
	 * if (moduleJsonList != null && !moduleJsonList.isEmpty()) { String roleName =
	 * request.getParameter("roleName"); AssignRoleDetailList assignRoleDetailList =
	 * new AssignRoleDetailList(); ObjectMapper mapper = new ObjectMapper(); try {
	 * int roleId = 0; try { roleId =
	 * Integer.parseInt(request.getParameter("roleId")); } catch (Exception e) {
	 * roleId = 0;
	 * 
	 * } String newsLetterJSON = mapper.writeValueAsString(moduleJsonList);
	 * 
	 * System.out.println("JSON  " + newsLetterJSON);
	 * assignRoleDetailList.setRoleJson(newsLetterJSON);
	 * assignRoleDetailList.setRoleId(roleId); } catch (JsonProcessingException e) {
	 * 
	 * System.err.println("exv in save ass role JsonProcessingException " +
	 * e.getMessage()); e.printStackTrace(); } //
	 * assignRoleDetailList.setAccessRightModuleList(newModuleList);
	 * assignRoleDetailList.setRoleName(roleName);
	 * assignRoleDetailList.setDelStatus(0);
	 * System.out.println("accessRightModule List " +
	 * assignRoleDetailList.toString()); System.out.println("heare"); try { Info
	 * info = rest.postForObject(Constants.url + "saveAssignRole",
	 * assignRoleDetailList, Info.class);
	 * 
	 * if (info.isError() == false) { isError = 2; } else { isError = 1;
	 * 
	 * } } catch (Exception e) { System.err.println("Exc in saveAssignRole  " +
	 * e.getMessage()); e.printStackTrace(); } } return "redirect:/showCreateRole";
	 * }
	 */

	@RequestMapping(value = "/submitCreateRole", method = RequestMethod.POST)
	public String submitAssignRole(HttpServletRequest request, HttpServletResponse response) {

		List<AccessRightModule> accessRightModule = accessRightModuleList.getAccessRightModuleList();

		List<ModuleJson> moduleJsonList = new ArrayList<>();
		String redirect = null;

		try {

			HttpSession session = request.getSession();
			String token = request.getParameter("token");
			String key = (String) session.getAttribute("generatedKey");

			if (token.trim().equals(key.trim())) {
				for (int i = 0; i < accessRightModule.size(); i++) {

					ModuleJson moduleJson = new ModuleJson();
					int isPresent = 0;

					List<SubModuleJson> subModuleJsonList = new ArrayList<>();

					for (int j = 0; j < accessRightModule.get(i).getAccessRightSubModuleList().size(); j++) {

						SubModuleJson subModuleJson = new SubModuleJson();

						String view = request.getParameter(
								accessRightModule.get(i).getAccessRightSubModuleList().get(j).getSubModuleId() + "view"
										+ accessRightModule.get(i).getModuleId());

						/* System.out.println("view " + view); */
						try {
							if (view.equals("1")) {
								isPresent = 1;

								subModuleJson.setView(String.valueOf(view));
								subModuleJson.setSubModuleId(
										accessRightModule.get(i).getAccessRightSubModuleList().get(j).getSubModuleId());
								subModuleJson.setModuleId(accessRightModule.get(i).getModuleId());
								subModuleJson.setSubModulName(accessRightModule.get(i).getAccessRightSubModuleList()
										.get(j).getSubModulName());
								subModuleJson.setSubModuleDesc(accessRightModule.get(i).getAccessRightSubModuleList()
										.get(j).getSubModuleDesc());
								subModuleJson.setSubModuleMapping(accessRightModule.get(i).getAccessRightSubModuleList()
										.get(j).getSubModuleMapping());
								subModuleJson.setOrderBy(
										accessRightModule.get(i).getAccessRightSubModuleList().get(j).getOrderBy());

								try {
									int add = Integer.parseInt(request.getParameter(accessRightModule.get(i)
											.getAccessRightSubModuleList().get(j).getSubModuleId() + "add"
											+ accessRightModule.get(i).getModuleId()));
									subModuleJson.setAddApproveConfig(String.valueOf(add));
								} catch (Exception e) {
									subModuleJson.setAddApproveConfig(String.valueOf(0));
								}

								try {
									int edit = Integer.parseInt(request.getParameter(accessRightModule.get(i)
											.getAccessRightSubModuleList().get(j).getSubModuleId() + "edit"
											+ accessRightModule.get(i).getModuleId()));
									subModuleJson.setEditReject(String.valueOf(edit));
								} catch (Exception e) {
									subModuleJson.setEditReject(String.valueOf(0));
								}

								try {
									int delete = Integer.parseInt(request.getParameter(accessRightModule.get(i)
											.getAccessRightSubModuleList().get(j).getSubModuleId() + "delete"
											+ accessRightModule.get(i).getModuleId()));
									subModuleJson.setDeleteRejectApprove(String.valueOf(delete));
								} catch (Exception e) {
									subModuleJson.setDeleteRejectApprove(String.valueOf(0));
								}

								subModuleJsonList.add(subModuleJson);

							}
						} catch (Exception e) {

						}

					}

					if (isPresent == 1) {
						moduleJson.setModuleId(accessRightModule.get(i).getModuleId());
						moduleJson.setModuleName(accessRightModule.get(i).getModuleName());
						moduleJson.setModuleDesc(accessRightModule.get(i).getModuleDesc());
						moduleJson.setOrderBy(accessRightModule.get(i).getOrderBy());
						moduleJson.setIconDiv(accessRightModule.get(i).getIconDiv());
						moduleJson.setSubModuleJsonList(subModuleJsonList);
						moduleJsonList.add(moduleJson);
					}

				}

				if (moduleJsonList != null && !moduleJsonList.isEmpty()) {
					String roleName = request.getParameter("roleName");
					AssignRoleDetailList assignRoleDetailList = new AssignRoleDetailList();
					ObjectMapper mapper = new ObjectMapper();
					try {

						try {
							int roleId = Integer.parseInt(request.getParameter("roleId"));
							assignRoleDetailList.setRoleId(roleId);
						} catch (Exception e) {

						}

						String newsLetterJSON = mapper.writeValueAsString(moduleJsonList);

						// System.out.println("JSON " + newsLetterJSON);
						assignRoleDetailList.setRoleJson(newsLetterJSON);

					} catch (JsonProcessingException e) {

						e.printStackTrace();
					}

					assignRoleDetailList.setRoleName(XssEscapeUtils.jsoupParse(roleName));
					assignRoleDetailList.setDelStatus(0);
					Info info = Constants.getRestTemplate().postForObject(Constants.url + "saveAssignRole",
							assignRoleDetailList, Info.class);
				}
				redirect = "redirect:/showRoleList";
				// System.out.println("saveAssignRole "+moduleJsonList);
			} else {
				System.err.println("in else");
				redirect = "redirect:/accessDenied";
			}
			SessionKeyGen.changeSessionKey(request);
		} catch (Exception e) {
			e.printStackTrace();
			SessionKeyGen.changeSessionKey(request);
		}

		return redirect;
	}

	@RequestMapping(value = "/editAccessRole/{roleId}", method = RequestMethod.GET)
	public ModelAndView editAccessRole(@PathVariable("roleId") int roleId, HttpServletRequest request,
			HttpServletResponse response) {

		ModelAndView model = new ModelAndView("accessRight/editAccessRole");

		// Constants.mainAct = 22;
		// Constants.subAct = 106;
		// dsad
		HttpSession session = request.getSession();
		List<ModuleJson> newModuleList = (List<ModuleJson>) session.getAttribute("newModuleList");
		Info info1 = AccessControll.checkAccess("showRoleList", "showRoleList", "0", "0", "1", "0", newModuleList);

		if (info1.isError() == false) {
			try {
				accessRightModuleList = Constants.getRestTemplate()
						.getForObject(Constants.url + "getAllModuleAndSubModule", AccessRightModuleList.class);

				MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
				map.add("roleId", roleId);

				AssignRoleDetailList editRole = Constants.getRestTemplate()
						.postForObject(Constants.url + "getRoleByRoleId", map, AssignRoleDetailList.class);
				// System.out.println("Access List " + accessRightModuleList.toString());
				model.addObject("allModuleList", accessRightModuleList.getAccessRightModuleList());
				model.addObject("editRole", editRole);
				ModuleJson[] moduleJson = new Gson().fromJson(editRole.getRoleJson(), ModuleJson[].class);
				List<ModuleJson> moduleJsonList = new ArrayList<ModuleJson>(Arrays.asList(moduleJson));

				// System.out.println("List" +
				// accessRightModuleList.getAccessRightModuleList());

				for (int i = 0; i < accessRightModuleList.getAccessRightModuleList().size(); i++) {

					for (int j = 0; j < moduleJsonList.size(); j++) {

						if (accessRightModuleList.getAccessRightModuleList().get(i).getModuleId() == moduleJsonList
								.get(j).getModuleId()) {

							// System.out.println("match Module " +
							// accessRightModuleList.getAccessRightModuleList().get(i).getModuleName());

							for (int k = 0; k < accessRightModuleList.getAccessRightModuleList().get(i)
									.getAccessRightSubModuleList().size(); k++) {

								for (int m = 0; m < moduleJsonList.get(j).getSubModuleJsonList().size(); m++) {

									if (accessRightModuleList.getAccessRightModuleList().get(i)
											.getAccessRightSubModuleList().get(k).getSubModuleId() == moduleJsonList
													.get(j).getSubModuleJsonList().get(m).getSubModuleId()) {

										// System.out.println("match sub Module " +
										// accessRightModuleList.getAccessRightModuleList().get(i).getAccessRightSubModuleList().get(k).getSubModulName());

										accessRightModuleList.getAccessRightModuleList().get(i)
												.getAccessRightSubModuleList().get(k)
												.setAddApproveConfig(Integer.parseInt(moduleJsonList.get(j)
														.getSubModuleJsonList().get(m).getAddApproveConfig()));
										accessRightModuleList.getAccessRightModuleList().get(i)
												.getAccessRightSubModuleList().get(k).setView(Integer.parseInt(
														moduleJsonList.get(j).getSubModuleJsonList().get(m).getView()));
										accessRightModuleList.getAccessRightModuleList().get(i)
												.getAccessRightSubModuleList().get(k)
												.setEditReject(Integer.parseInt(moduleJsonList.get(j)
														.getSubModuleJsonList().get(m).getEditReject()));
										accessRightModuleList.getAccessRightModuleList().get(i)
												.getAccessRightSubModuleList().get(k)
												.setDeleteRejectApprove(Integer.parseInt(moduleJsonList.get(j)
														.getSubModuleJsonList().get(m).getDeleteRejectApprove()));
										break;
									}

								}

							}

							break;
						}

					}

				}

				// System.out.println("List" +
				// accessRightModuleList.getAccessRightModuleList());
				model.addObject("title", "Edit Access Role");
				model.addObject("moduleJsonList", moduleJsonList);
				isError = 0;

			} catch (Exception e) {
				System.out.println(e.getMessage());
			}

		} else {
			model = new ModelAndView("accessDenied");
		}
		return model;
	}

	@RequestMapping(value = "/showAssignRole", method = RequestMethod.GET)
	public ModelAndView showAssignRloe(HttpServletRequest request, HttpServletResponse response) {

		ModelAndView model = new ModelAndView("accessRight/assignAccessRole1");

		try {

			HttpSession session = request.getSession();
			List<ModuleJson> newModuleList = (List<ModuleJson>) session.getAttribute("newModuleList");
			Info info = AccessControll.checkAccess("showAssignRole", "showAssignRole", "1", "0", "0", "0",
					newModuleList);

			if (info.isError() == false) {

				model.addObject("title", "Assign Role");
				/*
				 * accessRightModuleList = Constants.getRestTemplate()
				 * .getForObject(Constants.url + "getAllModuleAndSubModule",
				 * AccessRightModuleList.class);
				 */

				UserList[] user = Constants.getRestTemplate().getForObject(Constants.url + "/getAllUserList",
						UserList[].class);
				List<UserList> userList = new ArrayList<>(Arrays.asList(user));

				CreatedRoleList createdRoleList = Constants.getRestTemplate()
						.getForObject(Constants.url + "getAllAccessRole", CreatedRoleList.class);

				model.addObject("userList", userList);
				model.addObject("createdRoleList", createdRoleList.getAssignRoleDetailList());
			} else {
				model = new ModelAndView("accessDenied");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return model;
	}

	@RequestMapping(value = "/submitAssignedRole", method = RequestMethod.POST)
	public String submitAssignedRole(HttpServletRequest request, HttpServletResponse response) {

		int roleId = Integer.parseInt(request.getParameter("roleId"));
		int userId = Integer.parseInt(request.getParameter("userId"));
		HttpSession session = request.getSession();
		userId = (int) session.getAttribute("userId");
		System.err.println("UserId  " + userId);

		List<ModuleJson> newModuleList = (List<ModuleJson>) session.getAttribute("newModuleList");
		Info info1 = AccessControll.checkAccess("showAssignRole", "showAssignRole", "0", "1", "0", "0", newModuleList);

		if (info1.isError() == false) {
			try {

				MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();
				map.add("id", userId);
				map.add("roleId", roleId);

				Info info = Constants.getRestTemplate().postForObject(Constants.url + "/updateRoleOfUser", map,
						Info.class);

			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
		} else {
			return "redirect:/accessDenied";
		}
		return "redirect:/showAssignRole";
	}

	@RequestMapping(value = "/showAssignUserDetail/{userId}/{roleId}/{userName}/{roleName}", method = RequestMethod.GET)
	public ModelAndView showAssignUserDetail(@PathVariable int userId, @PathVariable int roleId,
			@PathVariable String userName, @PathVariable String roleName, HttpServletRequest request,
			HttpServletResponse response) {

		ModelAndView model = new ModelAndView("accessRight/viewAssignRoleDetails");

		MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();
		map.add("userId", userId);
		ParameterizedTypeReference<List<ModuleJson>> typeRef = new ParameterizedTypeReference<List<ModuleJson>>() {
		};
		ResponseEntity<List<ModuleJson>> responseEntity = Constants.getRestTemplate()
				.exchange(Constants.url + "getRoleJson", HttpMethod.POST, new HttpEntity<>(map), typeRef);

		List<ModuleJson> newModuleList = responseEntity.getBody();

		List<AccessRightModule> accessRightModuleListRes = accessRightModuleList.getAccessRightModuleList();

		for (int i = 0; i < accessRightModuleListRes.size(); i++) {
			for (int j = 0; j < newModuleList.size(); j++) {
				if (newModuleList.get(j).getModuleId() == accessRightModuleListRes.get(i).getModuleId()) {
					for (int l = 0; l < accessRightModuleListRes.get(i).getAccessRightSubModuleList().size(); l++) {
						boolean flag = false;
						for (int m = 0; m < newModuleList.get(j).getSubModuleJsonList().size(); m++) {
							if (accessRightModuleListRes.get(i).getAccessRightSubModuleList().get(l)
									.getSubModuleId() == newModuleList.get(j).getSubModuleJsonList().get(m)
											.getSubModuleId()) {
								flag = true;
							}
						}
						if (flag == false) {
							SubModuleJson sub = new SubModuleJson();
							sub.setSubModuleId(accessRightModuleListRes.get(i).getAccessRightSubModuleList().get(l)
									.getSubModuleId());
							sub.setView("hidden");
							sub.setSubModuleMapping(accessRightModuleListRes.get(i).getAccessRightSubModuleList().get(l)
									.getSubModuleMapping());
							sub.setEditReject("hidden");
							sub.setSubModuleDesc(accessRightModuleListRes.get(i).getAccessRightSubModuleList().get(l)
									.getSubModuleDesc());
							sub.setSubModulName(accessRightModuleListRes.get(i).getAccessRightSubModuleList().get(l)
									.getSubModulName());
							sub.setType(accessRightModuleListRes.get(i).getAccessRightSubModuleList().get(l).getType());
							sub.setModuleId(
									accessRightModuleListRes.get(i).getAccessRightSubModuleList().get(l).getModuleId());
							sub.setDeleteRejectApprove("hidden");
							sub.setAddApproveConfig("hidden");
							newModuleList.get(j).getSubModuleJsonList().add(sub);
						}
					}

				}

			}

		}
		model.addObject("moduleJsonList", newModuleList);
		model.addObject("userName", userName);
		model.addObject("roleName", roleName);
		model.addObject("roleId", roleId);
		model.addObject("title", "View Access Role");

		return model;
	}

	/*
	 * @RequestMapping(value = "/showPasswordChange", method = RequestMethod.GET)
	 * public ModelAndView showPasswordChange(HttpServletRequest request,
	 * HttpServletResponse response) { Constants.mainAct = 11; Constants.subAct =
	 * 112; ModelAndView model = new ModelAndView("accessRight/changePass");
	 * HttpSession session = request.getSession(); UserResponse userResponse =
	 * (UserResponse) session.getAttribute("UserDetail");
	 * 
	 * String uname = userResponse.getUser().getUsrName();
	 * 
	 * String curPass = userResponse.getUser().getUserPass();
	 * System.out.println("USer Name " + uname + "curPass " + curPass);
	 * model.addObject("uname", uname);
	 * 
	 * model.addObject("curPass", curPass);
	 * 
	 * return model;
	 * 
	 * }
	 * 
	 * @RequestMapping(value = "/changeUserPass", method = RequestMethod.POST)
	 * public String changeUserPass(HttpServletRequest request, HttpServletResponse
	 * response) {
	 * 
	 * ModelAndView model = new ModelAndView("accessRight/changePass"); HttpSession
	 * session = request.getSession(); String newPass =
	 * request.getParameter("new_pass2");
	 * 
	 * System.err.println("NEw Pass =  " + newPass); UserResponse userResponse =
	 * (UserResponse) session.getAttribute("UserDetail");
	 * 
	 * User user = userResponse.getUser();
	 * 
	 * user.setUserPass(newPass); // insertUser Info info =
	 * rest.postForObject(Constants.url + "changeAdminUserPass", user, Info.class);
	 * 
	 * System.err.println("Response of password change = " + info.toString());
	 * 
	 * return "redirect:/sessionTimeOut";
	 * 
	 * }
	 * 
	 * List<GetUserDetail> getUserDetail;
	 * 
	 * GetUserDetail user;
	 * 
	 * @RequestMapping(value = "/showManageUser", method = RequestMethod.GET) public
	 * ModelAndView showManageUser(HttpServletRequest request, HttpServletResponse
	 * response) {
	 * 
	 * ModelAndView model = new ModelAndView("user/userList"); try {
	 * GetUserDetailList getUserDetailList = rest.getForObject(Constants.url +
	 * "getUserDetail", GetUserDetailList.class);
	 * 
	 * getUserDetail = getUserDetailList.getUserDetail();
	 * model.addObject("userList", getUserDetail);
	 * 
	 * RestTemplate restTemplate = new RestTemplate(); GetUserTypeList
	 * getUserTypeList = restTemplate.getForObject(Constants.url + "getAllUserType",
	 * GetUserTypeList.class); DepartmentList departmentList =
	 * restTemplate.getForObject(Constants.url + "getAllDept",
	 * DepartmentList.class); model.addObject("getUserTypeList",
	 * getUserTypeList.getGetUserTypeList()); model.addObject("departmentList",
	 * departmentList.getDepartmentList()); } catch (Exception e) {
	 * e.printStackTrace(); } return model; }
	 * 
	 * @RequestMapping(value = "/editUser11/{userId}", method = RequestMethod.GET)
	 * public ModelAndView editUser(HttpServletRequest request, HttpServletResponse
	 * response,
	 * 
	 * @PathVariable("userId") long userId) {
	 * 
	 * ModelAndView model = new ModelAndView("user/userList");
	 * 
	 * System.err.println("User Id received " + userId);
	 * 
	 * for (int i = 0; i < getUserDetail.size(); i++) {
	 * 
	 * if (getUserDetail.get(i).getUserId() == userId) {
	 * 
	 * user = new GetUserDetail();
	 * 
	 * user = getUserDetail.get(i); break; } }
	 * System.out.println("User Object Received for Edit " + user.toString());
	 * model.addObject("userList", getUserDetail);
	 * 
	 * model.addObject("user", user); model.addObject("submit", 1);
	 * 
	 * RestTemplate restTemplate = new RestTemplate(); GetUserTypeList
	 * getUserTypeList = restTemplate.getForObject(Constants.url + "getAllUserType",
	 * GetUserTypeList.class); DepartmentList departmentList =
	 * restTemplate.getForObject(Constants.url + "getAllDept",
	 * DepartmentList.class); model.addObject("getUserTypeList",
	 * getUserTypeList.getGetUserTypeList()); model.addObject("departmentList",
	 * departmentList.getDepartmentList()); return model;
	 * 
	 * }
	 * 
	 * @RequestMapping(value = "/editUserProcess", method = RequestMethod.POST)
	 * public String editUserProcess(HttpServletRequest request, HttpServletResponse
	 * response) {
	 * 
	 * // ModelAndView model = new ModelAndView("user/userList"); try { String upass
	 * = request.getParameter("upass");
	 * 
	 * int deptId = Integer.parseInt(request.getParameter("dept_id")); int userType
	 * = Integer.parseInt(request.getParameter("user_type")); User editUser = new
	 * User(); user.setDeptId(deptId); user.setExInt1(userType);
	 * user.setUserPass(upass);
	 * 
	 * editUser.setDeptId(deptId); editUser.setExInt1(userType);
	 * editUser.setUserPass(upass); editUser.setUserId(user.getUserId()); Info info
	 * = rest.postForObject(Constants.url + "updateUser", editUser, Info.class);
	 * System.err.println("Update User Response  " + info.toString());
	 * System.err.println("Param for update " + upass + "dept Id " + deptId +
	 * "userType  " + userType); } catch (Exception e) {
	 * System.out.println("Ex in editUserProcess " + e.getMessage());
	 * e.printStackTrace(); }
	 * 
	 * return "redirect:/showManageUser"; }
	 * 
	 * @RequestMapping(value = "/deleteUser11/{userId}", method = RequestMethod.GET)
	 * public String deleteUser(HttpServletRequest request, HttpServletResponse
	 * response,
	 * 
	 * @PathVariable("userId") int userId) {
	 * 
	 * System.err.println("User Id received for Delete " + userId);
	 * 
	 * for (int i = 0; i < getUserDetail.size(); i++) {
	 * 
	 * if (getUserDetail.get(i).getUserId() == userId) {
	 * 
	 * user = new GetUserDetail();
	 * 
	 * user = getUserDetail.get(i); break; } } User editUser = new User();
	 * 
	 * editUser.setUserId(user.getUserId()); editUser.setDelStatus(1);
	 * 
	 * Info info = rest.postForObject(Constants.url + "updateUser", editUser,
	 * Info.class);
	 * 
	 * System.err.println("Update/delete User Response  " + info.toString());
	 * 
	 * return "redirect:/showManageUser"; }
	 */

}
