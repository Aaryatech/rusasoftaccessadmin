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
<body onload="getProgramTypeByProgram()">
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





								<div class="panel-group primary" id="accordion-2" role="tablist"
									aria-multiselectable="true">

									<!-- Criteria 1 -->
									
									

									<div class="panel-body">
										<div class="col-lg-10">1] Institutewise Various
											Accreditation Status Report</div>
										<div class="col-lg-2">
											<a href="#" onclick="getProgReport(0,'showVariousAccreReport')"><i
												class="fa fa-file-excel-o" style="color: green;"
												aria-hidden="true"></i>&nbsp;Excel</a>&nbsp;&nbsp;&nbsp;&nbsp;<a
												href="#" onclick="getProgReport(1,'showVariousAccreReport')"><i
												class="fa fa-file-pdf-o" style="color: red;"
												aria-hidden="true"></i>&nbsp;PDF</a>
										</div>
									</div>


									<div class="panel-body">
										<div class="col-lg-10">2] Institutewise NAAC
											Accreditation Status Report</div>
										<div class="col-lg-2">
											<a href="#" onclick="getProgReport(0,'showNaacAccreReport')"><i
												class="fa fa-file-excel-o" style="color: green;"
												aria-hidden="true"></i>&nbsp;Excel</a>&nbsp;&nbsp;&nbsp;&nbsp;<a
												href="#" onclick="getProgReport(1,'showNaacAccreReport')"><i
												class="fa fa-file-pdf-o" style="color: red;"
												aria-hidden="true"></i>&nbsp;PDF</a>
										</div>
									</div>

									<div class="panel-body">
										<div class="col-lg-10">3] Institutewise NBA
											Accreditation Status Report</div>
										<div class="col-lg-2">
											<a href="#"
												onclick="getProgReport(0,'showNbaAccreReport')"><i
												class="fa fa-file-excel-o" style="color: green;"
												aria-hidden="true"></i>&nbsp;Excel</a>&nbsp;&nbsp;&nbsp;&nbsp;<a
												href="#"
												onclick="getProgReport(1,'showNbaAccreReport')"><i
												class="fa fa-file-pdf-o" style="color: red;"
												aria-hidden="true"></i>&nbsp;PDF</a>
										</div>
									</div>
									<div class="panel-body">
										<div class="col-lg-10">4] Institutewise NIRF
											Accreditation Status Report</div>
										<div class="col-lg-2">
											<a href="#"
												onclick="getProgReport(0,'showNirfAccreReport')"><i
												class="fa fa-file-excel-o" style="color: green;"
												aria-hidden="true"></i>&nbsp;Excel</a>&nbsp;&nbsp;&nbsp;&nbsp;<a
												href="#"
												onclick="getProgReport(1,'showNirfAccreReport')"><i
												class="fa fa-file-pdf-o" style="color: red;"
												aria-hidden="true"></i>&nbsp;PDF</a>
										</div>
									</div>
									<div class="panel-body">
										<div class="col-lg-10">5] Institutewise THE
											Accreditation Status Report</div>
										<div class="col-lg-2">
											<a href="#"
												onclick="getProgReport(0,'showTheAccreReport')"><i
												class="fa fa-file-excel-o" style="color: green;"
												aria-hidden="true"></i>&nbsp;Excel</a>&nbsp;&nbsp;&nbsp;&nbsp;<a
												href="#"
												onclick="getProgReport(1,'showTheAccreReport')"><i
												class="fa fa-file-pdf-o" style="color: red;"
												aria-hidden="true"></i>&nbsp;PDF</a>
										</div>
									</div>
									
									<div class="panel-body">
										<div class="col-lg-10">6] Curricular, Co-Curricular and Extra Curricular Activities</div>
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
									
									 
									
									 

									<input type="hidden" id="p" name="p" value="0">




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
		//use this function for all reports just get mapping form action name dynamically as like of prm from every report pdf,excel function	
		function getProgReport(prm, mapping) {
			if (prm == 1) {
				document.getElementById("p").value = "1";
			}

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
			//alert("programType" + programType);

			var valid = true;

			/* if (programType == null || programType == "") {
				valid = false;
				alert("Please Select Program");
			} */

			$.getJSON('${getProgramTypeByProgram}', {
				programType : programType,
				ajax : 'true',
			},

			function(data) {
				//alert(data);

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