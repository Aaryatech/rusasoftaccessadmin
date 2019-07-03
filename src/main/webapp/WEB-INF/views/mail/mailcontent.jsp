<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%><%@ taglib
	uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>



<!DOCTYPE html>
<html class=" ">
<head>

<jsp:include page="/WEB-INF/views/include/header.jsp"></jsp:include>
<c:url var="getSubmoduleList" value="/getSubmoduleList" />
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
<body class=" " onload="hideText()">
	<!-- START TOPBAR -->
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

				<%-- <div class="col-xs-12">
					<div class="page-title">

						<div class="pull-left">
							<!-- PAGE HEADING TAG - START -->
							<h1 class="title">${title}</h1>
							<!-- PAGE HEADING TAG - END -->
						</div>


					</div>
				</div> --%>
				<div class="clearfix"></div>
				<!-- MAIN CONTENT AREA STARTS -->

				<div class="col-lg-12"></div>



				<div class="col-lg-12">
					<section class="box ">

						<header class="panel_header">
							<h2 class="title pull-left">${title}</h2>

							<div class="actions panel_actions pull-right">
								<%-- <a href="#myModal1" data-toggle="modal"><button
										type="submit" class="btn btn-info">Add</button></a> <a
									href="${pageContext.request.contextPath}/publicationList"><button
										type="button" class="btn btn-info">Back</button></a> <a
									class="box_toggle fa fa-chevron-down"></a> --%>
							</div>

						</header>


						<div class="content-body">
							<div class="row">
								<div class="col-md-12">
									<form class="form-horizontal"
										action="${pageContext.request.contextPath}/submitSendMail"
										method="post" name="form_sample_2" id="form_sample_2" enctype="multipart/form-data">


										<div class="form-group">
											<label class="control-label col-sm-2" for="instituteId">
												Select Institute <span class="text-danger">*</span> :
											</label>
											<div class="col-sm-10">
												<select id="instituteId" name="instituteId" class=""
													multiple placeholder="Select Institute" required="required">
													<c:forEach items="${instList}" var="instList">

														<option value="${instList.instituteId}">${instList.instituteName}</option>

													</c:forEach>
													 
												</select> <span class="error_form text-danger" id="dept_id_field"
													style="display: none;">Please Select Institute</span>
											</div>
										</div>

										<div class="form-group">
											<label class="control-label col-sm-2" for="subject">Subject
												<span class="text-danger">*</span> :
											</label>
											<div class="col-sm-10">

												<input class="form-control" name="subject" id="subject"
													placeholder="Subject" type="text" required>

											</div>

										</div>

										<div class="form-group">
											<label class="control-label col-sm-2" for="message">Message
												<span class="text-danger">*</span> :
											</label>
											<!-- <div class="col-sm-10">

												<textarea rows="3" cols="3" class="form-control"
													style="width: 100%; height: 150px; font-size: 14px; line-height: 23px; padding: 15px;"
													name="message" id="message" placeholder="Message"></textarea>

											</div> -->
											<div class="col-sm-10">
												<textarea class="ckeditor" name="message" id="message"> </textarea>
											</div>
										</div>


										<div class="form-group">
											<label class="control-label col-sm-2" for="files">Attach
												File : </label>
											<div class="col-sm-10">

												<input class="form-control" name="files" id="files"
													placeholder="Subject" type="file"
													accept=".jpg,.pdf,.doc,.png,.gif,.zip" multiple="multiple">

											</div>

										</div>

										<div class="form-group">
											<div class="col-sm-offset-2 col-sm-10">
												<button type="submit" class="btn btn-primary">Send
													Mail</button>

											</div>
										</div>


										<div class="clearfix"></div>

									</form>
								</div>

							</div>
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
		var wasSubmitted = false;
		function checkBeforeSubmit() {
			if (!wasSubmitted) {
				var x = confirm("Do you really want to submit the form?");
				if (x == true) {
					wasSubmitted = true;

					document.getElementById("sub2").disabled = true;

					return wasSubmitted;
				}
			}
			return false;
		}

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

	<script>
		function checkSubmodule(moduleId) {

			$
					.getJSON(
							'${getSubmoduleList}',
							{
								moduleId : moduleId,
								ajax : 'true',

							},
							function(data) {

								if (document
										.getElementById("header" + moduleId).checked == true) {

									for (var i = 0; i < data.length; i++) {

										document.getElementById(data[i]
												+ "view" + moduleId).checked = true;
										document.getElementById(data[i] + "add"
												+ moduleId).checked = true;
										document.getElementById(data[i]
												+ "edit" + moduleId).checked = true;
										document.getElementById(data[i]
												+ "delete" + moduleId).checked = true;
										document.getElementById(data[i]
												+ "view" + moduleId).value = 1;
										document.getElementById(data[i] + "add"
												+ moduleId).value = 1;
										document.getElementById(data[i]
												+ "edit" + moduleId).value = 1;
										document.getElementById(data[i]
												+ "delete" + moduleId).value = 1;
									}

								} else {
									for (var i = 0; i < data.length; i++) {

										document.getElementById(data[i]
												+ "view" + moduleId).checked = false;
										document.getElementById(data[i] + "add"
												+ moduleId).checked = false;
										document.getElementById(data[i]
												+ "edit" + moduleId).checked = false;
										document.getElementById(data[i]
												+ "delete" + moduleId).checked = false;
										document.getElementById(data[i]
												+ "view" + moduleId).value = 0;
										document.getElementById(data[i] + "add"
												+ moduleId).value = 0;
										document.getElementById(data[i]
												+ "edit" + moduleId).value = 0;
										document.getElementById(data[i]
												+ "delete" + moduleId).value = 0;
									}
								}

							});

		}

		function changeValue(type, subModuleId, moduleId) {

			if (type == 1) {
				if (document.getElementById(subModuleId + "view" + moduleId).checked == true) {

					document.getElementById(subModuleId + "view" + moduleId).value = 1;

				} else {

					document.getElementById(subModuleId + "view" + moduleId).value = 0;
				}

			} else if (type == 2) {
				if (document.getElementById(subModuleId + "add" + moduleId).checked == true) {

					document.getElementById(subModuleId + "add" + moduleId).value = 1;
				} else {
					document.getElementById(subModuleId + "add" + moduleId).value = 0;
				}
			} else if (type == 3) {
				if (document.getElementById(subModuleId + "edit" + moduleId).checked == true) {

					document.getElementById(subModuleId + "edit" + moduleId).value = 1;

				} else {

					document.getElementById(subModuleId + "edit" + moduleId).value = 0;

				}

			} else if (type == 4) {

				if (document.getElementById(subModuleId + "delete" + moduleId).checked == true) {

					document.getElementById(subModuleId + "delete" + moduleId).value = 1;

				} else {

					document.getElementById(subModuleId + "delete" + moduleId).value = 0;

				}

			}

		}
	</script>








</body>
</html>