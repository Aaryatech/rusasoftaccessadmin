<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%><%@ taglib
	uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page import="java.util.UUID"%>
<%@ page import="java.security.MessageDigest"%>
<%@ page import="java.math.BigInteger"%>


<!DOCTYPE html>
<html class=" ">
<head>

<jsp:include page="/WEB-INF/views/include/header.jsp"></jsp:include>
<c:url var="checkUserNameAvailableChangepass"
	value="/checkUserNameAvailableChangepass" />
<c:url var="checkpassword" value="/checkpassword" />
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




						<form class="form-horizontal" id="form_sample_2"
							action="${pageContext.request.contextPath}/submitChangepass"
							method="post">
							
							<%
									UUID uuid = UUID.randomUUID();
									MessageDigest md = MessageDigest.getInstance("MD5");
									byte[] messageDigest = md.digest(String.valueOf(uuid).getBytes());
									BigInteger number = new BigInteger(1, messageDigest);
									String hashtext = number.toString(16);
									session = request.getSession();
									session.setAttribute("generatedKey", hashtext);
								%>
									<input type="hidden" value="<%out.println(hashtext);%>"
											name="token" id="token">

							<div class="content-body">
								<div class="row">
									<c:if test="${sessionScope.successMsg!=null}">
										<div class="col-lg-12">
											<div class="alert alert-success alert-dismissible fade in">
												<button type="button" class="close" data-dismiss="alert"
													aria-label="Close">
													<span aria-hidden="true">×</span>
												</button>
												${sessionScope.successMsg}
											</div>
										</div>
										<%
											session.removeAttribute("successMsg");
										%>
									</c:if>
									<div class="col-xs-12">

										<div id="currentPassDiv">
											<div class="form-group">
												<label class="control-label col-sm-2" for="currentPass">Enter
													Current Password : <span class="text-danger">*</span>
												</label>
												<div class="col-sm-10">
													<input id="currentPass" class="form-control"
														placeholder="Current Password" onchange="trim(this);"
														style="text-align: left;" name="currentPass"
														type="password"> <span
														class="error_form text-danger" id="error_currentPass"
														style="display: none;">Enter Current Password.</span>
												</div>
											</div>

											<div class="form-group">
												<div class="col-sm-offset-2 col-sm-10">
													<button type="button" onclick="checkpassword();"
														class="btn btn-primary" id="">Submit</button>

												</div>
											</div>
										</div>
										<div style="display: none;" id="changePassDiv">
											<div class="form-group">
												<label class="control-label col-sm-2" for="userName">User
													Name : <span class="text-danger">*</span>
												</label>
												<div class="col-sm-10">
													<input id="userName" class="form-control"
														placeholder="User Name" value="${userInfo.userName}"
														onchange="checkUserNameExist();trim(this);"
														style="text-align: left;" name="userName" type="text">
													<span class="error_form text-danger" id="error_userName"
														style="display: none;">Enter User Name.</span>
												</div>
											</div>

											<div class="form-group">
												<label class="control-label col-sm-2" for="userPass">Password
													: <span class="text-danger">*</span>
												</label>
												<div class="col-sm-10">
													<input id="userPass" class="form-control"
														placeholder="Password" style="text-align: left;"
														name="userPass" type="password"> <span
														class="error_form text-danger" id="error_userPass"
														style="display: none;">Enter Password.</span>
												</div>
											</div>

											<div class="form-group">
												<label class="control-label col-sm-2" for="reuserPass">Re-Password
													: <span class="text-danger">*</span>
												</label>
												<div class="col-sm-10">
													<input id="reuserPass" class="form-control"
														placeholder="Re-Password" style="text-align: left;"
														name="reuserPass" type="password"> <span
														class="error_form text-danger" id="error_reuserPass"
														style="display: none;">Enter Password.</span>
												</div>
											</div>


											<div class="form-group">
												<div class="col-sm-offset-2 col-sm-10">
													<button type="submit" class="btn btn-primary" id="">Submit</button>
													<input id="available" value="1" name="available"
														type="hidden">
												</div>
											</div>
										</div>
									</div>
								</div>
							</div>


						</form>

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
	<script>
		function trim(el) {
			el.value = el.value.replace(/(^\s*)|(\s*$)/gi, ""). // removes leading and trailing spaces
			replace(/[ ]{2,}/gi, " "). // replaces multiple spaces with one space 
			replace(/\n +/, "\n"); // Removes spaces after newlines\
			checkSame()
			return;
		}

		function validateEmail(email) {

			var eml = /^([A-Za-z0-9_\-\.])+\@([A-Za-z0-9_\-\.])+\.([A-Za-z]{2,4})$/;

			if (eml.test($.trim(email)) == false) {

				return false;

			}

			return true;

		}

		function numbersOnlyNotZero(id_number) {

			var mob = /^[1-9][0-9]+$/;

			if (mob.test($.trim(id_number)) == false) {

				//alert("Please enter a valid email address .");
				return false;

			}
			return true;
		}

		$(document)
				.ready(
						function($) {

							$("#form_sample_2")
									.submit(
											function(e) {
												var isError = false;
												var errMsg = "";

												$("#error_userName").hide();
												$("#error_userPass").hide();
												$("#error_reuserPass").hide();

												if (!$("#userName").val()) {

													isError = true;
													$("#error_userName").html(
															"Enter User Name");
													$("#error_userName").show();
												}

												if (!$("#userPass").val()) {

													isError = true;
													$("#error_userPass").html(
															"Enter Password");
													$("#error_userPass").show();
												}
												if (!$("#reuserPass").val()) {

													isError = true;
													$("#error_reuserPass")
															.html(
																	"Enter Password");
													$("#error_reuserPass")
															.show();
												}

												if ($("#reuserPass").val() != ""
														&& $("#userPass").val() != ""
														&& $("#userPass").val() != $(
																"#reuserPass")
																.val()) {

													isError = true;

													$("#error_reuserPass")
															.html(
																	"Password not matched");

													$("#error_reuserPass")
															.show();
												}

												if ($("#available").val() == 0) {

													isError = true;
													$("#error_userName")
															.html(
																	"username not available.");
													$("#error_userName").show();
												}
												//alert(isError);
												if (!isError) {

													document
															.getElementById("submtbtn").disabled = true;
													return true;

												}
												return false;
											});
						});
		//

		function checkUserNameExist() {
			$("#error_userName").hide();

			var userName = document.getElementById("userName").value;

			//alert(userName.length);

			$.getJSON('${checkUserNameAvailableChangepass}', {

				userName : userName,
				ajax : 'true',

			}, function(data) {

				//alert(JSON.stringify(data));

				if (data.error == true) {
					document.getElementById("available").value = 0;
					$("#error_userName").html("username not available.");
					$("#error_userName").show();
				} else {
					document.getElementById("available").value = 1;
				}

			});
		}
		function checkpassword() {
			$("#error_currentPass").hide();

			var currentPass = document.getElementById("currentPass").value;

			//alert(userName.length);

			$.getJSON('${checkpassword}', {

				currentPass : currentPass,
				ajax : 'true',

			}, function(data) {

				//alert(JSON.stringify(data));

				if (data.error == true) {

					$("#error_currentPass").html("password is not matched");
					$("#error_currentPass").show();
				} else {
					$("#currentPassDiv").hide();
					$("#changePassDiv").show();
					$("#error_userName").hide();
					$("#error_userPass").hide();
					$("#error_reuserPass").hide();
				}

			});
		}
	</script>

	<script type="text/javascript">
		$(function() {

			$('.datepicker').datepicker({
				autoclose : true,
				format : "dd-mm-yyyy",

			});
		});
	</script>
</body>
</html>