<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<body>
	<c:url var="setSubModId" value="/setSubModId" />
	<div class="page-sidebar pagescroll">
		<!-- MAIN MENU - START -->
		<div class="page-sidebar-wrapper" id="main-menu-wrapper">
			<!-- USER INFO - START -->
			<%-- <div class="profile-info row">
				<div class="profile-image col-xs-4">
					<a href="#"> <img alt=""
						src="../data/profile/profile.jpg"
						class="img-responsive img-circle">
					</a>
				</div>
				<div class="profile-details col-xs-8">
					<h3>
					<c:set var="tempinstname" value="${sessionScope.instituteInfo.instituteName}"></c:set>
										<c:set var="instName" value="${fn:substring(tempinstname,0,70)}"></c:set>
					
						<a href="#">${instName}</a>
						<!-- Available statuses: online, idle, busy, away and offline -->
						<span class="profile-status online"></span>
					</h3>
					<!-- <p class="profile-title">Address</p> -->
				</div>
			</div> --%>
			<br>
			<!-- USER INFO - END -->
			<ul class='wraplist'>
				<li class='menusection'></li>
				<!-- <li class="open"><a href="index.html"> <i
						class="fa fa-dashboard"></i> <span class="title">Dashboard</span>
				</a></li> -->



				<c:forEach items="${sessionScope.newModuleList}" var="allModuleList"
					varStatus="count">

					<c:choose>
						<c:when
							test="${sessionScope.sessionModuleId==allModuleList.moduleId}">
							<li class="open">
						</c:when>
						<c:otherwise>
							<li class="">
						</c:otherwise>
					</c:choose>
					<a href="javascript:;"> ${allModuleList.iconDiv}<span
						class="title">${allModuleList.moduleName}</span> <c:choose>
							<c:when
								test="${sessionScope.sessionModuleId==allModuleList.moduleId}">
								<span class="arrow open"></span>
							</c:when>
							<c:otherwise>
								<span class="arrow "></span>
							</c:otherwise>
						</c:choose>

					</a>
					<ul class="sub-menu">
						<c:forEach items="${allModuleList.subModuleJsonList}"
							var="allSubModuleList">
							<li><c:choose>
									<c:when
										test="${sessionScope.sessionSubModuleId==allSubModuleList.subModuleId}">
										<a class="active"
											href="${pageContext.request.contextPath}/${allSubModuleList.subModuleMapping}"
											onclick="selectSubMod(${allSubModuleList.subModuleId},${allSubModuleList.moduleId})">
											${allSubModuleList.subModulName}</a>
									</c:when>
									<c:otherwise>
										<a class=""
											href="${pageContext.request.contextPath}/${allSubModuleList.subModuleMapping}"
											onclick="selectSubMod(${allSubModuleList.subModuleId},${allSubModuleList.moduleId})">
											${allSubModuleList.subModulName}</a>
									</c:otherwise>
								</c:choose></li>
						</c:forEach>

					</ul>
					</li>
				</c:forEach>

				<%-- <li class=""><a href="javascript:;"> <i
						class="fa fa-columns"></i> <span class="title">User
							Registration</span> <span class="arrow "></span>
				</a>
					<ul class="sub-menu">
						<li><a class=""
							href="${pageContext.request.contextPath}/showRegisterInstitute">
								Institute Registration</a> <a class=""
							href="${pageContext.request.contextPath}/showPendingInstitute">
								Pending Institute List</a> <a class=""
							href="${pageContext.request.contextPath}/showApprovedInstitute">
								Approved Institute List</a> <a class=""
							href="${pageContext.request.contextPath}/iqacRegistration">IQAC
								Registration</a> <a class=""
							href="${pageContext.request.contextPath}/hodRegistration">HOD
								Registration</a> <a class=""
							href="${pageContext.request.contextPath}/showRegisterStaff">Faculty
								Registration</a> <a class=""
							href="${pageContext.request.contextPath}/showFacultyInfo">Faculty
								Information</a> <a class=""
							href="${pageContext.request.contextPath}/showAdjuntFaculty">Faculty
								Details</a> <a class=""
							href="${pageContext.request.contextPath}/showRegAcc">Account
								Officer Registration</a> <a class=""
							href="${pageContext.request.contextPath}/showRegDean">Dean/R&D
								Registration</a> <a class=""
							href="${pageContext.request.contextPath}/showRegLib">Librarian
								Registration</a> <a class=""
							href="${pageContext.request.contextPath}/showRegStud">Student
								Registration</a> <a class=""
							href="${pageContext.request.contextPath}/showIqacAfterLogin">Fill
								Institute Information</a> <a class=""
							href="${pageContext.request.contextPath}/addFaculty">Department
								Registration</a> <a class=""
							href="${pageContext.request.contextPath}/addPrincipal">Principal
								Registration</a></li>


					</ul></li>



				<li class=""><a href="javascript:;"> <i
						class="fa fa-columns"></i> <span class="title">Institute
							Profile Module </span> <span class="arrow "></span>
				</a>
					<ul class="sub-menu">
 

						<li><a class=""
							href="${pageContext.request.contextPath}/showEContentFacilities">Common
								Details Form </a></li>
 

						<li><a class=""
							href="${pageContext.request.contextPath}/showReforms">Evaluation
								Process and Reforms </a></li>


						<li><a class=""
							href="${pageContext.request.contextPath}/showTransparentGrievance">Mechanism
								of Examination Related Grievances(Transparent) </a></li>


						<li><a class=""
							href="${pageContext.request.contextPath}/showTimeBoundGrievance">Mechanism
								of Examination Related Grievances (Time Bound) </a></li>


						<li><a class=""
							href="${pageContext.request.contextPath}/showEfficienttGrievance">Mechanism
								of Examination Related Grievances (Efficient) </a></li>

						<li><a class=""
							href="${pageContext.request.contextPath}/showStudentPerformance">Student
								Performance </a></li>


						<li><a class=""
							href="${pageContext.request.contextPath}/showCurriculum">Curriculum
								and Cross-cutting Issues </a></li>

						<li><a class=""
							href="${pageContext.request.contextPath}/showUderTakingProv">Provision
								for Undertaking </a></li>

						<li><a class=""
							href="${pageContext.request.contextPath}/showFeedback">Feedback

						</a></li>


						<li><a class=""
							href="${pageContext.request.contextPath}/showProfDevelopment">Governance,
								Leadership and Management (Professional Development ) </a></li>

						<li><a class=""
							href="${pageContext.request.contextPath}/showProfDevelopment">Governance,
								Leadership and Management (Administrative Process ) </a></li>
 
						<li><a class=""
							href="${pageContext.request.contextPath}/showGenderSensitivity">
								Gender Sensitivity Facility </a></li>

						<li><a class=""
							href="${pageContext.request.contextPath}/showEnvConciuosness">
								Environmental Consciousness and Sustainability </a></li>
						<li><a class=""
							href="${pageContext.request.contextPath}/showGreenPractices">
								Green Practices </a></li>

						<li><a class=""
							href="${pageContext.request.contextPath}/showSpecInitiatives">
								Specific Initiatves </a></li>

						<li><a class=""
							href="${pageContext.request.contextPath}/showInstituteOfferingCourse">
								Institute Offers a Course on Humanities </a></li>
 
						<li><a class=""
							href="${pageContext.request.contextPath}/showProgDistinctive">
								Institutional Distinctiveness </a></li>
 

						<li><a class=""
							href="${pageContext.request.contextPath}/showCommitteeDetail">
								Committee Details </a></li>
  
					</ul></li>


				<li class=""><a href="javascript:;"> <i
						class="fa fa-columns"></i> <span class="title">Program
							Details Module </span> <span class="arrow "></span>
				</a>
					<ul class="sub-menu">

						<li><a class=""
							href="${pageContext.request.contextPath}/showProgDetail1">
								Program Details List </a></li>


						<li><a class=""
							href="${pageContext.request.contextPath}/showProgDsh"> Add
								Program Details </a></li>
 
						<li><a class=""
							href="${pageContext.request.contextPath}/showpoPso"> PO/PSO
								Mapping </a></li>



						<li><a class=""
							href="${pageContext.request.contextPath}/showStudAddmit">Student
								Admitted Categorywise </a></li>

						<li><a class=""
							href="${pageContext.request.contextPath}/showStudAddmitLoc">Student
								Admitted Locationwise </a></li>

						<li><a class=""
							href="${pageContext.request.contextPath}/showStudSupp">Student
								Support Scheme </a></li>

						<li><a class=""
							href="${pageContext.request.contextPath}/showStudTran">Student
								Training </a></li>

						<li><a class=""
							href="${pageContext.request.contextPath}/showHighEdu">Progression
								to Higher Education </a></li>

						<li><a class=""
							href="${pageContext.request.contextPath}/showStudAct">Student
								Activity (Organized) </a></li>

						<li><a class=""
							href="${pageContext.request.contextPath}/showStudActAtten">Student
								Activity (Attended)</a></li>


						<li><a class=""
							href="${pageContext.request.contextPath}/showAlumini">Alumini
								Association/Contribution Activity </a></li>


					</ul></li>



				<li class=""><a href="javascript:;"> <i
						class="fa fa-columns"></i> <span class="title">Faculty
							Details</span> <span class="arrow "></span>
				</a>
					<ul class="sub-menu">
						<li><a class=""
							href="${pageContext.request.contextPath}/showPersonalDetails">Personal
								Details </a></li>
						<li><a class=""
							href="${pageContext.request.contextPath}/showAcademicDetails">Academic
								Details </a></li>
						<li><a class=""
							href="${pageContext.request.contextPath}/showMphillDetails">M.Phill/Ph.D.
								Details </a></li>
						<li><a class=""
							href="${pageContext.request.contextPath}/showStudMentor">Student
								Mentoring Details </a></li>




						<li><a class=""
							href="${pageContext.request.contextPath}/showAddPublicationDetailsList">Publication/Presentation
								Details </a></li>
						<li><a class=""
							href="${pageContext.request.contextPath}/showBookPubList">Book
								Publication Details </a></li>
						<li><a class=""
							href="${pageContext.request.contextPath}/showJournalPub">Journal
								Publication Details </a></li>

						<li><a class=""
							href="${pageContext.request.contextPath}/showResearchDetails">Research
								Project Details </a></li>

						<li><a class=""
							href="${pageContext.request.contextPath}/showConsultancyDetails">Consultancy
								Details </a></li>
						<li><a class=""
							href="${pageContext.request.contextPath}/showPatentDetails">Patent
								Details </a></li>

						<li><a class=""
							href="${pageContext.request.contextPath}/showAwardDetails">Award
								Recognition Details </a></li>


						<li><a class=""
							href="${pageContext.request.contextPath}/showOutReachDetails">Out
								Reach Activity </a></li>


						<li><a class=""
							href="${pageContext.request.contextPath}/showOrganized">Organized
								Details </a></li>

						<li><a class=""
							href="${pageContext.request.contextPath}/showOutReachContri">Outreach
								Contribution Details </a></li>

						<li><a class=""
							href="${pageContext.request.contextPath}/showSubDetails">Subject
								Details </a></li>
						<li><a class=""
							href="${pageContext.request.contextPath}/showSWOC">SWOC
								Details </a></li>
						<li><a class=""
							href="${pageContext.request.contextPath}/showPhdGuide">Ph.D.Guidence
								Details </a></li>



					</ul></li>


				<li class=""><a href="javascript:;"> <i
						class="fa fa-columns"></i> <span class="title">Infrastructure
							Module </span> <span class="arrow "></span>
				</a>
					<ul class="sub-menu">

						<li><a class=""
							href="${pageContext.request.contextPath}/showinfra">Infrastructure
								Facilities Form </a></li>
						<li><a class=""
							href="${pageContext.request.contextPath}/showPhysicalFacilities">Physical
								Facilities Form </a></li>
						<li><a class=""
							href="${pageContext.request.contextPath}/showInstruct">Instructional
								Form </a></li>
						<li><a class=""
							href="${pageContext.request.contextPath}/showAdmin">Administrative
								Form </a></li>
						<li><a class=""
							href="${pageContext.request.contextPath}/showAmeneties">Amenities
								Form </a></li>
						<li><a class=""
							href="${pageContext.request.contextPath}/showITinfra">IT
								Infrastructure Form </a></li>
						<li><a class=""
							href="${pageContext.request.contextPath}/showInternetCon">Internet
								Connection Form </a></li>
						<li><a class=""
							href="${pageContext.request.contextPath}/showeContent">e-Content
								Form </a></li>

					</ul></li>


				<li class=""><a href="javascript:;"> <i
						class="fa fa-columns"></i> <span class="title">Budget</span> <span
						class="arrow "></span>
				</a>
					<ul class="sub-menu">

						<li><a class=""
							href="${pageContext.request.contextPath}/budgetInfrastructureFacility">Budget
								on Infrastructure Facility </a></li>

						<li><a class=""
							href="${pageContext.request.contextPath}/budgetOnLibrary">
								Budget on Library Facility </a></li>

						<li><a class=""
							href="${pageContext.request.contextPath}/budgetPhysicalFacility">
								Budget on Physical Facilities </a></li>

						<li><a class=""
							href="${pageContext.request.contextPath}/budgetOnAcadamicSupportFacilities">Academic
								Support Facilities </a></li>

						<li><a class=""
							href="${pageContext.request.contextPath}/budgetOnGreenInitiativesAndWasteMngmnt">Waste
								Management </a></li>

						<li><a class=""
							href="${pageContext.request.contextPath}/budgetOnLibraryBooks">Budget
								on Library Books</a></li>


					</ul></li>



				<li class=""><a href="javascript:;"> <i
						class="fa fa-columns"></i> <span class="title">Library</span> <span
						class="arrow "></span>
				</a>
					<ul class="sub-menu">

						<li><a class=""
							href="${pageContext.request.contextPath}/libraryBasicInfo">Basic
								Information</a></li>

						<li><a class=""
							href="${pageContext.request.contextPath}/showEJournals">e-Journals</a></li>
						<li><a class=""
							href="${pageContext.request.contextPath}/showEShodhSindhu">e-Shodh
								Sindhu</a></li>
						<li><a class=""
							href="${pageContext.request.contextPath}/showEShodhGanga">Shodh
								Ganga Membership</a></li>
						<li><a class=""
							href="${pageContext.request.contextPath}/showEBooks">e-Books</a></li>
						<li><a class=""
							href="${pageContext.request.contextPath}/showDatabase">Databases</a></li>

						<li><a class=""
							href="${pageContext.request.contextPath}/rareBookInformation">Rare
								Books Information</a></li>


					</ul></li>

				<li class=""><a href="javascript:;"> <i
						class="fa fa-columns"></i> <span class="title">Research And
							Innovation </span> <span class="arrow "></span>
				</a>
					<ul class="sub-menu">

						<li><a class=""
							href="${pageContext.request.contextPath}/showLinkage">Research
								And Innovation Linkage Form </a></li>

						<li><a class=""
							href="${pageContext.request.contextPath}/showMOUs"> MOUs Form
						</a></li>

						<li><a class=""
							href="${pageContext.request.contextPath}/showExtAct">Extension
								Activities Form </a></li>

						<li><a class=""
							href="${pageContext.request.contextPath}/showGenIssue">Gender
								Issue Form </a></li>

						<li><a class=""
							href="${pageContext.request.contextPath}/showComAct">Community
								Activities Form </a></li>

						<li><a class=""
							href="${pageContext.request.contextPath}/showSubDetails1">Research
								Centre Detail</a></li>


					</ul></li> --%>



				<li><a href="${pageContext.request.contextPath}/logout"> <i
						class="fa fa-sign-out" aria-hidden="true" style="color: red;"></i> <span class="title">Logout</span>
				</a></li>
			</ul>
			<div class="menustats"></div>
		</div>
		<!-- MAIN MENU - END -->
		<script>
			function selectSubMod(subModId, modId) {

				$.getJSON('${setSubModId}', {
					subModId : subModId,
					modId : modId,
					ajax : 'true'
				});

			}
		</script>
	</div>
</body>