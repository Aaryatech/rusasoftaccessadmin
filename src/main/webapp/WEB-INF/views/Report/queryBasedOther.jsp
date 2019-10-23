<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%><%@ taglib
	uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>



<!DOCTYPE html>
<html class=" ">
<head>

<jsp:include page="/WEB-INF/views/include/header.jsp"></jsp:include>
<!-- CORE CSS TEMPLATE - END -->
</head>
<!-- END HEAD -->

<style>
.image-preview-input {
	position: relative;
	overflow: hidden;
	margin: 0px;
	color: #333;
	background-color: #fff;
	border-color: #ccc;
}

.image-preview-input input[type=file] {
	position: absolute;
	top: 0;
	right: 0;
	margin: 0;
	padding: 0;
	font-size: 20px;
	cursor: pointer;
	opacity: 0;
	filter: alpha(opacity = 0);
}

.image-preview-input-title {
	margin-left: 2px;
}
</style>


<!-- BEGIN BODY -->
<body><!-- onload="getProgramTypeByProgram()" -->
	<!-- START TOPBAR -->
	<c:url value="/getProgramTypeByProgram" var="getProgramTypeByProgram"></c:url>

	<jsp:include page="/WEB-INF/views/include/topbar.jsp"></jsp:include>
	<!-- END TOPBAR -->
	<!-- START CONTAINER -->
	<div class="page-container row-fluid container-fluid">

		<!-- SIDEBAR - START -->

		<jsp:include page="/WEB-INF/views/include/left.jsp"></jsp:include>
		<!--  SIDEBAR - END -->
		<!-- START CONTENT -->
		<section id="main-content" class=" ">
			<section class="wrapper main-wrapper row" style="">

				<div class="col-xs-12">
					<div class="page-title">

						<!-- 	<div class="pull-left">
							PAGE HEADING TAG - START
							<h1 class="title">Add Department</h1>
							PAGE HEADING TAG - END
						</div> -->
					</div>
				</div>
				<div class="clearfix"></div>
				<!-- MAIN CONTENT AREA STARTS -->
				<div class="col-lg-12">
					<section class="box ">
						<header class="panel_header">
							<h2 class="title pull-left">${title}</h2>
							<div class="actions panel_actions pull-right">
								<%-- 	<a href="${pageContext.request.contextPath}/showDeptList"><button
										type="button" class="btn btn-info">Back</button></a> --%>
							</div>
						</header>
						<div class="content-body">

							<form id="reportForm">
								<div class="row" style="padding-bottom: 2%;">

									<div class="form-group">
										<label class="control-label col-sm-1" for="status">Academic
											Year <span class="text-danger"></span>
										</label>
										<div class="col-sm-3">
											<select id="ac_year" name="ac_year"
												placeholder="Select Institute" class="form-control">

												<c:forEach items="${acaYearList}" var="acYear">
													<option value="${acYear.yearId}">${acYear.academicYear}</option>
												</c:forEach>
												<option value="-5">Last Five Years</option>
											</select> <span class="error_form text-danger" id="prog_type_field"
												style="display: none;">Please select program type</span>
										</div>



										<label class="control-label col-sm-2" for="instituteId">
											 Institute <span class="text-danger">*</span> :
										</label>
										<div class="col-sm-6">
											<select id="instituteId" name="instituteId" class="">
											<option>Select Institute</option>
											
												<c:forEach items="${instList}" var="instList">

													<option value="${instList.instituteId}">${instList.instituteName}</option>

												</c:forEach>

											</select> <span class="error_form text-danger" id="error_instituteId"
												style="display: none;">Please Select Institute</span>
										</div>
									</div>


								</div>
								
								<div class="row" style="padding-bottom: 2%;">
									<div class="form-group">
									
										<label class="control-label col-sm-1" for="status">Program
											<span class="text-danger"></span>
										</label>
										<div class="col-sm-3">
											<select id="prog_type" name="prog_type" class="form-control"
												onchange="getProgramTypeByProgram()">

												<c:forEach items="${progTypeList}" var="progType">
													<option value="${progType.programId}">${progType.programName}</option> 
												</c:forEach>

											</select> <span class="error_form text-danger" id="prog_type_field"
												style="display: none;">Please select program type</span>
										</div>
											
										<label class="control-label col-sm-2" for="page_order">
											Program Type<span class="text-danger"></span>
										</label>
										<div class="col-sm-3">

											<select id="prog_name" name="prog_name" class="form-control"
												required>

											</select>

											<%-- <input type="text" class="form-control" id="prog_name"
															value="${trainPlace.programName}" onchange="trim(this)" name="prog_name"
															placeholder="Name of Program" maxlength="100"> --%>
											<span class="error_form text-danger" id="prog_name_field"
												style="display: none;">Please enter program name</span>
										</div>
									
									</div>								
								</div>
								
								<div class="row" style="padding-bottom: 0px;">
									<div class="form-group">
										<label class="control-label col-sm-1" for="catId">Category
											<span class="text-danger"></span>
										</label>
										<div class="col-sm-3">
											<select id="catId" name="catId" class="form-control">

												<c:forEach items="${castList}" var="castList">
													<option value="${castList.castId}">${castList.castName}</option>
												</c:forEach>

											</select> <span class="error_form text-danger" id="error_catId"
												style="display: none;">Please select Category</span>
										</div>


										<label class="control-label col-sm-1" for="catId">E-Content
											Development Facility <span class="text-danger"></span>
										</label>
										<div class="col-sm-3">
											<select id="e_contentType" name="e_contentType"
												class="form-control">

												<option value="Media Center">Media Center</option>
												<option value="Recording Facility">Recording
													Facility</option>
												<option value="Lecture Capturing System">Lecture
													Capturing System</option>

											</select>
										</div>
									</div>
								</div>


								<br>

								<div class="panel-group primary" id="accordion-2" role="tablist"
									aria-multiselectable="true">

									<!-- Criteria 1 -->


<div class="panel-body">
										<div class="col-lg-10">1] Curricular, Co-Curricular and Extra Curricular Activities</div>
										<div class="col-lg-2">
											<a href="#"
												onclick="getProgReport(0,'coExCurricularAct')"><i
												class="fa fa-file-excel-o" style="color: green;"
												aria-hidden="true"></i>&nbsp;Excel</a>&nbsp;&nbsp;&nbsp;&nbsp;<a
												href="#"
												onclick="getProgReport(1,'coExCurricularAct')"><i
												class="fa fa-file-pdf-o" style="color: red;"
												aria-hidden="true"></i>&nbsp;PDF</a>
										</div>
									</div>
 
									<div class="panel-body">
										<div class="col-lg-10">2] Placement of UG & PG Students
											Reports</div>
										<div class="col-lg-2">
											<a href="#" onclick="getProgReport(0,'showUgPgStudReport')"><i
												class="fa fa-file-excel-o" style="color: green;"
												aria-hidden="true"></i>&nbsp;Excel</a>&nbsp;&nbsp;&nbsp;&nbsp;<a
												href="#" onclick="getProgReport(1,'showUgPgStudReport')"><i
												class="fa fa-file-pdf-o" style="color: red;"
												aria-hidden="true"></i>&nbsp;PDF</a>
										</div>
									</div>


									<div class="panel-body">
										<div class="col-lg-10">3] Details Regarding Anti-ragging
											Squad And Sexual Harassment</div>
										<div class="col-lg-2">
											<a href="#"
												onclick="getProgReport(0,'showAntiRagHarashmentReport')"><i
												class="fa fa-file-excel-o" style="color: green;"
												aria-hidden="true"></i>&nbsp;Excel</a>&nbsp;&nbsp;&nbsp;&nbsp;<a
												href="#"
												onclick="getProgReport(1,'showAntiRagHarashmentReport')"><i
												class="fa fa-file-pdf-o" style="color: red;"
												aria-hidden="true"></i>&nbsp;PDF</a>
										</div>
									</div>
									
										<div class="panel-body">
										<div class="col-lg-10">4] Competitive Exam (No. of Student appeared for Competitive Exam at various levels)</div>
										<div class="col-lg-2">
											<a href="#"
												onclick="getProgReport(0,'getCompitetiveExmReport')"><i
												class="fa fa-file-excel-o" style="color: green;"
												aria-hidden="true"></i>&nbsp;Excel</a>&nbsp;&nbsp;&nbsp;&nbsp;<a
												href="#"
												onclick="getProgReport(1,'getCompitetiveExmReport')"><i
												class="fa fa-file-pdf-o" style="color: red;"
												aria-hidden="true"></i>&nbsp;PDF</a>
										</div>
									</div>
									
										
									<div class="panel-body">
										<div class="col-lg-10">5] Value Added Course List</div>
										<div class="col-lg-2">
											<a href="#"
												onclick="getProgReport(0,'showValueAddedCourseReport')"><i
												class="fa fa-file-excel-o" style="color: green;"
												aria-hidden="true"></i>&nbsp;Excel</a>&nbsp;&nbsp;&nbsp;&nbsp;<a
												href="#"
												onclick="getProgReport(1,'showValueAddedCourseReport')"><i
												class="fa fa-file-pdf-o" style="color: red;"
												aria-hidden="true"></i>&nbsp;PDF</a>
										</div>
									</div>
									
											
									<div class="panel-body">
										<div class="col-lg-10">6] Subject Wise Research</div>
										<div class="col-lg-2">
											<a href="#"
												onclick="getProgReport(0,'getSubjctRsrchReport')"><i
												class="fa fa-file-excel-o" style="color: green;"
												aria-hidden="true"></i>&nbsp;Excel</a>&nbsp;&nbsp;&nbsp;&nbsp;<a
												href="#"
												onclick="getProgReport(1,'getSubjctRsrchReport')"><i
												class="fa fa-file-pdf-o" style="color: red;"
												aria-hidden="true"></i>&nbsp;PDF</a>
										</div>
									</div>
									
										<div class="panel-body">
										<div class="col-lg-10">7] Male Female Ratio</div>
										<div class="col-lg-2">
											<a href="#"
												onclick="getProgReport(0,'showMaleFemaleRatioReport')"><i
												class="fa fa-file-excel-o" style="color: green;"
												aria-hidden="true"></i>&nbsp;Excel</a>&nbsp;&nbsp;&nbsp;&nbsp;<a
												href="#"
												onclick="getProgReport(1,'showMaleFemaleRatioReport')"><i
												class="fa fa-file-pdf-o" style="color: red;"
												aria-hidden="true"></i>&nbsp;PDF</a>
										</div>
									</div>
									
									
									<div class="panel-body">
										<div class="col-lg-10">8] District Wise Institute Details</div>
										<div class="col-lg-2">
											<a href="#"
												onclick="getProgReport(0,'showDistrictwiseInstReport')"><i
												class="fa fa-file-excel-o" style="color: green;"
												aria-hidden="true"></i>&nbsp;Excel</a>&nbsp;&nbsp;&nbsp;&nbsp;<a
												href="#"
												onclick="getProgReport(1,'showDistrictwiseInstReport')"><i
												class="fa fa-file-pdf-o" style="color: red;"
												aria-hidden="true"></i>&nbsp;PDF</a>
										</div>
									</div>
									
									<div class="panel-body">
										<div class="col-lg-10">9] Grants Received for Research</div>
										<div class="col-lg-2">
											<a href="#"
												onclick="getProgReport(0,'getGrntRecvResrchReprt')"><i
												class="fa fa-file-excel-o" style="color: green;"
												aria-hidden="true"></i>&nbsp;Excel</a>&nbsp;&nbsp;&nbsp;&nbsp;<a
												href="#"
												onclick="getProgReport(1,'getGrntRecvResrchReprt')"><i
												class="fa fa-file-pdf-o" style="color: red;"
												aria-hidden="true"></i>&nbsp;PDF</a>
										</div>
									</div>
									
									
								
									
									<div class="panel-body">
										<div class="col-lg-10">10] Students Participated in Sports (National/International)</div>
										<div class="col-lg-2">
											<a href="#"
												onclick="getProgReport(0,'showStudentParticipatedReport')"><i
												class="fa fa-file-excel-o" style="color: green;"
												aria-hidden="true"></i>&nbsp;Excel</a>&nbsp;&nbsp;&nbsp;&nbsp;<a
												href="#"
												onclick="getProgReport(1,'showStudentParticipatedReport')"><i
												class="fa fa-file-pdf-o" style="color: red;"
												aria-hidden="true"></i>&nbsp;PDF</a>
										</div>
									</div>
									
									<div class="panel-body">
										<div class="col-lg-10">11] Teachers participation in Research</div>
										<div class="col-lg-2">
											<a href="#"
												onclick="getProgReport(0,'getFacultyRsrchReport')"><i
												class="fa fa-file-excel-o" style="color: green;"
												aria-hidden="true"></i>&nbsp;Excel</a>&nbsp;&nbsp;&nbsp;&nbsp;<a
												href="#"
												onclick="getProgReport(1,'getFacultyRsrchReport')"><i
												class="fa fa-file-pdf-o" style="color: red;"
												aria-hidden="true"></i>&nbsp;PDF</a>
										</div>
									</div>
									
									
									<div class="panel-body">
										<div class="col-lg-10">12] Students Participated in NSS And NCC</div>
										<div class="col-lg-2">
											<a href="#"
												onclick="getProgReport(0,'showStudentParticipatedNssNccReport')"><i
												class="fa fa-file-excel-o" style="color: green;"
												aria-hidden="true"></i>&nbsp;Excel</a>&nbsp;&nbsp;&nbsp;&nbsp;<a
												href="#"
												onclick="getProgReport(1,'showStudentParticipatedNssNccReport')"><i
												class="fa fa-file-pdf-o" style="color: red;"
												aria-hidden="true"></i>&nbsp;PDF</a>
										</div>
									</div>
									
									<div class="panel-body">
										<div class="col-lg-10">13]  Functional MoU (State/National/International)</div>
										<div class="col-lg-2">
											<a href="#"
												onclick="getProgReport(0,'showFunctionalMouReport')"><i
												class="fa fa-file-excel-o" style="color: green;"
												aria-hidden="true"></i>&nbsp;Excel</a>&nbsp;&nbsp;&nbsp;&nbsp;<a
												href="#"
												onclick="getProgReport(1,'showFunctionalMouReport')"><i
												class="fa fa-file-pdf-o" style="color: red;"
												aria-hidden="true"></i>&nbsp;PDF</a>
										</div>
									</div>
									
									<input type="hidden" id="p" name="p" value="0"> <input
										type="hidden" id="temp_ac_year" name="temp_ac_year" value="0">


								</div>
							</form>
						</div>
					</section>
				</div>
				<!-- MAIN CONTENT AREA ENDS -->
			</section>
		</section>
		<!-- END CONTENT -->
	</div>
	<!-- END CONTAINER -->
	<!-- LOAD FILES AT PAGE END FOR FASTER LOADING -->

	<jsp:include page="/WEB-INF/views/include/footer.jsp"></jsp:include>

	<script type="text/javascript">
		$("#instituteId").select2({
			allowClear : true
		}).on(
				'select2-open',
				function() {
					// Adding Custom Scrollbar
					$(this).data('select2').results.addClass('overflow-hidden')
							.perfectScrollbar();
				});
	</script>
	<script type="text/javascript">
		//use this function for all reports just get mapping form action name dynamically as like of prm from every report pdf,excel function	
		function getProgReport(prm, mapping) {
			if (prm == 1) {
				document.getElementById("p").value = "1";
			}

			var el = document.getElementById('ac_year');
			var text = el.options[el.selectedIndex].innerHTML;
			document.getElementById("temp_ac_year").value = text;

			var form = document.getElementById("reportForm");

			form.setAttribute("target", "_blank");
			form.setAttribute("method", "post");

			form.action = ("${pageContext.request.contextPath}/" + mapping + "/");

			form.submit();
			document.getElementById("p").value = "0";
		}
	</script>

	<script type="text/javascript">
		function getProgramTypeByProgram() {

			var programType = document.getElementById("prog_type").value;
			var instituteId = document.getElementById("instituteId").value;
			///alert("instituteId-------" + instituteId);

			var valid = true;
			
			if (instituteId=="" && instituteId==0) {
				
				//isError=true;
				
				$("#error_instituteId").show()
				
				} else {
					$("#error_instituteId").hide()
				}
	          

			/* if (programType == null || programType == "") {
				valid = false;
				alert("Please Select Program");
			} */

			$.getJSON('${getProgramTypeByProgram}', {
				programType : programType,
				instituteId: instituteId,
				
				ajax : 'true',
			},

			function(data) {
				
				//alert(JSON.stringify(data));

				var html;
				var len = data.length;
				for (var i = 0; i < len; i++) {

					html += '<option value="' + data[i].programId + '">'
							+ data[i].nameOfProgram + '</option>';

				}
				html += '</option>';

				$('#prog_name').html(html);
				$("#prog_name").trigger("chosen:updated");

			});
			//alert("Hi")
		}
	</script>

</body>
</html>