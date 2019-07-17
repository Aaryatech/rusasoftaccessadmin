package com.ats.rusaaccessweb.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.ats.rusaaccessweb.common.AccessControll;
import com.ats.rusaaccessweb.common.Constants;
import com.ats.rusaaccessweb.common.DateConvertor;
import com.ats.rusaaccessweb.common.FormValidation;
import com.ats.rusaaccessweb.model.GetChangePrincipalDetails;
import com.ats.rusaaccessweb.model.GetInstituteList;
import com.ats.rusaaccessweb.model.Info;
import com.ats.rusaaccessweb.model.LoginResponse;
import com.ats.rusaaccessweb.model.ModuleJson;
import com.ats.rusaaccessweb.model.UserLogin;
import com.ats.rusaaccessweb.model.dashb.QualityIniGraphResponse;

@Controller
@Scope("session")
public class RegistrationController {

	 
	@RequestMapping(value = "/showChangePrincipalRequestList", method = RequestMethod.GET)
	public String showChangePrincipalRequestList(HttpServletRequest request, HttpServletResponse response, Model model) {

		String mav = new String();

		try {

			HttpSession session = request.getSession();

			List<ModuleJson> newModuleList = (List<ModuleJson>) session.getAttribute("newModuleList");

			 

			Info viewAccess = AccessControll.checkAccess("showChangePrincipalRequestList", "showChangePrincipalRequestList", "1", "0", "0", "0",
					newModuleList);

			if (viewAccess.isError() == false) {
				model.addAttribute("title", "Change Principal List");
				mav = "master/changePrincipalList";

				GetChangePrincipalDetails[] res = Constants.getRestTemplate().getForObject(Constants.url + "/getAllRequestForChangePrincipal",
						GetChangePrincipalDetails[].class);
				List<GetChangePrincipalDetails> list = new ArrayList<>(Arrays.asList(res));

				 
				model.addAttribute("list", list);
				System.out.println("list is"+list.toString());

			} else {

				mav = "redirect:/accessDenied";
			}


		} catch (Exception e) {

			// System.err.println("exception In editJournal at Iqac Contr" +
			// e.getMessage());

			e.printStackTrace();
			mav = "redirect:/accessDenied";

		}
		return mav;
	}

	
	@RequestMapping(value = "/approveChangePrincipal/{facultyId}/{instituteId}", method = RequestMethod.GET)
	public String approveChangePrincipal(HttpServletRequest request, HttpServletResponse response,
			@PathVariable int facultyId,	@PathVariable int instituteId) {

		String redirect = null;
		try {
		  
			
				MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();
				
				 map.add("facultyId",facultyId);
				Info errMsg = Constants.getRestTemplate().postForObject(Constants.url + "changePrincipal", map, Info.class);
				
				if(errMsg.isError()==false) {
					 
					redirect = "redirect:/showChangePrincipalRequestList";
					map = new LinkedMultiValueMap<String, Object>();
					
					 map.add("instituteId",instituteId);
					Info errMsg1 = Constants.getRestTemplate().postForObject(Constants.url + "blockPrevPrincipal", map, Info.class);
					
				}
				
				
				
		 
		} catch (Exception e) {

			System.err.println(" Exception In deleteInstitutes at Master Contr " + e.getMessage());

			e.printStackTrace();

		}

		return redirect;

	}
	
	
	UserLogin editUser = new UserLogin();

	@RequestMapping(value = "/registerUser", method = RequestMethod.GET)
	public String registerUser(HttpServletRequest request, HttpServletResponse response, Model model) {

		String mav = new String();

		try {

			HttpSession session = request.getSession();

			List<ModuleJson> newModuleList = (List<ModuleJson>) session.getAttribute("newModuleList");

			Info viewAccess = AccessControll.checkAccess("registerUser", "registerUser", "1", "0", "0", "0",
					newModuleList);

			if (viewAccess.isError() == false) {

				model.addAttribute("title", "User Registration");
				mav = "master/userRegistraion";
				editUser = new UserLogin();

			} else {

				mav = "redirect:/accessDenied";
			}

		} catch (Exception e) {

			// System.err.println("exception In editJournal at Iqac Contr" +
			// e.getMessage());

			e.printStackTrace();
			mav = "redirect:/accessDenied";

		}
		return mav;
	}

	@RequestMapping(value = "/insertUser", method = RequestMethod.POST)
	public String insertUser(HttpServletRequest request, HttpServletResponse response, Model model) {

		String mav = new String();

		try {
			Info checkUserNameExist = new Info();

			HttpSession session = request.getSession();

			List<ModuleJson> newModuleList = (List<ModuleJson>) session.getAttribute("newModuleList");
			LoginResponse userObj = (LoginResponse) session.getAttribute("userInfo");

			Info viewAccess = AccessControll.checkAccess("registerUser", "registerUser", "0", "1", "0", "0",
					newModuleList);

			if (viewAccess.isError() == false) {

				Date date = new Date();
				SimpleDateFormat dateTimeInGMT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

				String firstname = request.getParameter("firstname");
				String middlename = request.getParameter("middlename");
				String lastname = request.getParameter("lastname");
				String userEmail = request.getParameter("userEmail");
				String designation = request.getParameter("designation");
				String joiningDate = request.getParameter("joiningDate");
				// int isActive = Integer.parseInt(request.getParameter("isActive"));
				int isEdit = Integer.parseInt(request.getParameter("isEdit"));

				Boolean error = false;

				if (FormValidation.Validaton(firstname, "") == true || FormValidation.Validaton(lastname, "") == true
						|| FormValidation.Validaton(userEmail, "email") == true
						|| FormValidation.Validaton(designation, "") == true
						|| FormValidation.Validaton(joiningDate, "date") == true) {

					error = true;
				}

				editUser.setIsBlock(1);
				editUser.setFirstName(firstname.trim().replaceAll("[ ]{2,}", " "));
				editUser.setMiddleName(middlename.trim().replaceAll("[ ]{2,}", " "));
				editUser.setLastName(lastname.trim().replaceAll("[ ]{2,}", " "));
				editUser.setEmail(userEmail.trim().replaceAll("[ ]{2,}", " "));
				editUser.setDesignation(designation.trim().replaceAll("[ ]{2,}", " "));
				editUser.setJoiningDate(DateConvertor.convertToYMD(joiningDate));
				editUser.setExVar1(dateTimeInGMT.format(date));

				if (isEdit == 0 && error == false) {

					String userName = request.getParameter("userName");
					String userPass = request.getParameter("userPass");
					String reuserPass = request.getParameter("reuserPass");

					if (FormValidation.Validaton(userName, "") == true || !userPass.equals(reuserPass)) {

						error = true;
					}

					MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
					map.add("username", userName);
					checkUserNameExist = Constants.getRestTemplate()
							.postForObject(Constants.url + "/checkUserNameExist", map, Info.class);

					editUser.setUserName(userName.trim().replaceAll("[ ]{2,}", " "));
					editUser.setPass(userPass);
				}

				if (error == false && checkUserNameExist.isError() == false) {

					editUser.setExInt1(userObj.getUserId());
					UserLogin res = Constants.getRestTemplate().postForObject(Constants.url + "/rusaUserRegistration",
							editUser, UserLogin.class);

					if (res == null) {
						session.setAttribute("successMsg", "Failed To Add User Information !");
						session.setAttribute("errorMsg", "true");
					} else {
						session.setAttribute("successMsg", "User Infomation added successfully!");
						session.setAttribute("errorMsg", "false");
					}

				} else {
					session.setAttribute("successMsg", "Invalid Data");
					session.setAttribute("errorMsg", "true");
				}

				mav = "redirect:/getRusaUserList";

			} else {

				mav = "redirect:/accessDenied";
			}

		} catch (Exception e) {

			e.printStackTrace();
			HttpSession session = request.getSession();
			session.setAttribute("successMsg", "Failed To Add User Information !");
			session.setAttribute("errorMsg", "true");

		}
		return mav;
	}

	@RequestMapping(value = "/getRusaUserList", method = RequestMethod.GET)
	public String getRusaUserList(HttpServletRequest request, HttpServletResponse response, Model model) {

		String mav = new String();

		try {

			HttpSession session = request.getSession();

			List<ModuleJson> newModuleList = (List<ModuleJson>) session.getAttribute("newModuleList");

			Info viewAccess = AccessControll.checkAccess("getRusaUserList", "getRusaUserList", "1", "0", "0", "0",
					newModuleList);

			if (viewAccess.isError() == false) {

				model.addAttribute("title", "User List");
				mav = "master/rusaUserList";

				LoginResponse[] res = Constants.getRestTemplate().getForObject(Constants.url + "/getAllUserList",
						LoginResponse[].class);
				List<LoginResponse> list = new ArrayList<>(Arrays.asList(res));

				for (int i = 0; i < list.size(); i++) {

					list.get(i).setExVar2(FormValidation.Encrypt(String.valueOf(list.get(i).getUserId())));

				}
				model.addAttribute("list", list);

			} else {

				mav = "redirect:/accessDenied";
			}

		} catch (Exception e) {

			// System.err.println("exception In editJournal at Iqac Contr" +
			// e.getMessage());

			e.printStackTrace();
			mav = "redirect:/accessDenied";

		}
		return mav;
	}

	@RequestMapping(value = "/deleteUser", method = RequestMethod.GET)
	public String deleteUser(HttpServletRequest request, HttpServletResponse response) {

		String mav = new String();

		try {

			HttpSession session = request.getSession();

			List<ModuleJson> newModuleList = (List<ModuleJson>) session.getAttribute("newModuleList");

			Info viewAccess = AccessControll.checkAccess("deleteUser", "getRusaUserList", "0", "0", "0", "1",
					newModuleList);

			if (viewAccess.isError() == false) {

				int userId = Integer.parseInt(FormValidation.DecodeKey(request.getParameter("user")));
				MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
				map.add("userId", userId);
				Info res = Constants.getRestTemplate().postForObject(Constants.url + "/deleteRusaUserRegistration", map,
						Info.class);

				mav = "redirect:/getRusaUserList";

				if (res.isError() == true) {
					session.setAttribute("successMsg", "Failed To Delete User Information !");
					session.setAttribute("errorMsg", "true");
				} else {
					session.setAttribute("successMsg", "User Infomation Deleted successfully!");
					session.setAttribute("errorMsg", "false");
				}

			} else {

				mav = "redirect:/accessDenied";
			}

		} catch (Exception e) {

			// System.err.println("exception In editJournal at Iqac Contr" +
			// e.getMessage());

			e.printStackTrace();
			mav = "redirect:/accessDenied";

		}
		return mav;
	}

	@RequestMapping(value = "/editUser", method = RequestMethod.GET)
	public String editUser(HttpServletRequest request, HttpServletResponse response, Model model) {

		String mav = new String();

		try {

			HttpSession session = request.getSession();

			List<ModuleJson> newModuleList = (List<ModuleJson>) session.getAttribute("newModuleList");

			Info viewAccess = AccessControll.checkAccess("editUser", "getRusaUserList", "0", "0", "1", "0",
					newModuleList);

			if (viewAccess.isError() == false) {
				model.addAttribute("title", "Edit User");
				mav = "master/userRegistraion";

				int userId = Integer.parseInt(FormValidation.DecodeKey(request.getParameter("user")));
				MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
				map.add("userId", userId);
				editUser = Constants.getRestTemplate().postForObject(Constants.url + "/rusaUserbyId", map,
						UserLogin.class);

				model.addAttribute("editUser", editUser);
				model.addAttribute("isEdit", 1);
			} else {

				mav = "redirect:/accessDenied";
			}

		} catch (Exception e) {

			// System.err.println("exception In editJournal at Iqac Contr" +
			// e.getMessage());

			e.printStackTrace();
			mav = "redirect:/accessDenied";

		}
		return mav;
	}

	@RequestMapping(value = "/checkUserNameAvailable", method = RequestMethod.GET)
	public @ResponseBody Info checkUserNameAvailable(HttpServletRequest request, HttpServletResponse response) {

		Info info = new Info();

		try {

			String userName = request.getParameter("userName");
			MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
			map.add("username", userName);
			info = Constants.getRestTemplate().postForObject(Constants.url + "/checkUserNameExist", map, Info.class);

		} catch (Exception e) {

			// System.err.println("exception In editJournal at Iqac Contr" +
			// e.getMessage());

			e.printStackTrace();

		}
		return info;
	}

	@RequestMapping(value = "/changeCredential", method = RequestMethod.GET)
	public String changeCredential(HttpServletRequest request, HttpServletResponse response, Model model) {

		String mav = new String();

		try {

			HttpSession session = request.getSession();
			LoginResponse userObj = (LoginResponse) session.getAttribute("userInfo");

			List<ModuleJson> newModuleList = (List<ModuleJson>) session.getAttribute("newModuleList");

			Info viewAccess = AccessControll.checkAccess("changeCredential", "changeCredential", "1", "0", "0", "0",
					newModuleList);

			if (viewAccess.isError() == false) {

				model.addAttribute("title", "Change Login Credential");
				mav = "master/changeCredential";

				MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
				map.add("userId", userObj.getUserId());
				UserLogin userInfo = Constants.getRestTemplate().postForObject(Constants.url + "/rusaUserbyId", map,
						UserLogin.class);

				model.addAttribute("userInfo", userInfo);

			} else {

				mav = "redirect:/accessDenied";
			}

		} catch (Exception e) {

			// System.err.println("exception In editJournal at Iqac Contr" +
			// e.getMessage());

			e.printStackTrace();
			mav = "redirect:/accessDenied";

		}
		return mav;
	}

	@RequestMapping(value = "/checkpassword", method = RequestMethod.GET)
	public @ResponseBody Info checkpassword(HttpServletRequest request, HttpServletResponse response) {

		Info info = new Info();

		try {

			String currentPass = request.getParameter("currentPass");
			HttpSession session = request.getSession();
			LoginResponse userObj = (LoginResponse) session.getAttribute("userInfo");

			if (currentPass.equals(userObj.getPass())) {
				info.setError(false);
			} else {
				info.setError(true);
			}

		} catch (Exception e) {

			e.printStackTrace();
		}
		return info;
	}

	@RequestMapping(value = "/checkUserNameAvailableChangepass", method = RequestMethod.GET)
	public @ResponseBody Info checkUserNameAvailableChangepass(HttpServletRequest request,
			HttpServletResponse response) {

		Info info = new Info();

		try {

			String userName = request.getParameter("userName");
			HttpSession session = request.getSession();
			LoginResponse userObj = (LoginResponse) session.getAttribute("userInfo");

			if (userName.equals(userObj.getUserName())) {
				info.setError(false);
			} else {

				MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
				map.add("username", userName);
				info = Constants.getRestTemplate().postForObject(Constants.url + "/checkUserNameExist", map,
						Info.class);
			}

		} catch (Exception e) {

			e.printStackTrace();
		}
		return info;
	}

	@RequestMapping(value = "/submitChangepass", method = RequestMethod.POST)
	public String submitChangepass(HttpServletRequest request, HttpServletResponse response, Model model) {

		String mav = new String();

		try {
			Info checkUserNameExist = new Info();

			HttpSession session = request.getSession();

			List<ModuleJson> newModuleList = (List<ModuleJson>) session.getAttribute("newModuleList");
			LoginResponse userObj = (LoginResponse) session.getAttribute("userInfo");

			Info viewAccess = AccessControll.checkAccess("submitChangepass", "changeCredential", "1", "0", "0", "0",
					newModuleList);

			if (viewAccess.isError() == false) {

				Date date = new Date();
				SimpleDateFormat dateTimeInGMT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

				Boolean error = false;

				String currentPass = request.getParameter("currentPass");

				if (currentPass.equals(userObj.getPass())) {

					String userName = request.getParameter("userName");
					String userPass = request.getParameter("userPass");
					String reuserPass = request.getParameter("reuserPass");

					if (FormValidation.Validaton(userName, "") == true || !userPass.equals(reuserPass)) {

						error = true;
					}

					if (userName.equals(userObj.getUserName())) {

						checkUserNameExist.setError(false);

					} else {

						MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
						map.add("username", userName);
						checkUserNameExist = Constants.getRestTemplate()
								.postForObject(Constants.url + "/checkUserNameExist", map, Info.class);

					}

					if (error == false && checkUserNameExist.isError() == false) {

						MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
						map.add("userId", userObj.getUserId());
						map.add("userName", userName);
						map.add("userPass", userPass);

						Info res = Constants.getRestTemplate().postForObject(Constants.url + "/updatePassword", map,
								Info.class);

						if (res.isError() == true) {
							session.setAttribute("successMsg", "Failed to update password !");
							session.setAttribute("errorMsg", "true");
						} else {
							session.setAttribute("successMsg", "password changed successfully!");
							session.setAttribute("errorMsg", "false");
						}

					} else {
						session.setAttribute("successMsg", "Invalid Data");
						session.setAttribute("errorMsg", "true");
					}

				}

				mav = "redirect:/changeCredential";

			} else {

				mav = "redirect:/accessDenied";
			}

		} catch (Exception e) {

			e.printStackTrace();
			HttpSession session = request.getSession();
			session.setAttribute("successMsg", "Failed to update password !");
			session.setAttribute("errorMsg", "true");

		}
		return mav;
	}

}
