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
<c:url var="checkUserNameAvailable" value="/checkUserNameAvailable" />
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
<body class=" ">
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

						<form class="form-horizontal" id="form_sample_2"
							action="${pageContext.request.contextPath}/insertUser"
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

									<div class="col-xs-12">


										<div class="form-group">
											<label class="control-label col-sm-2" for="firstname">First
												Name : <span class="text-danger">*</span>
											</label>
											<div class="col-sm-10">
												<input id="firstname" class="form-control"
													onchange="trim(this)" placeholder="First Name"
													value="${editUser.firstName}" style="text-align: left;"
													name="firstname" type="text"> <span
													class="error_form text-danger" id="error_firstname"
													style="display: none;">Enter First Name.</span>
											</div>
										</div>

										<div class="form-group">
											<label class="control-label col-sm-2" for="middlename">Middle
												Name : </label>
											<div class="col-sm-10">
												<input id="middlename" class="form-control"
													onchange="trim(this)" placeholder="Middle Name"
													value="${editUser.middleName}" style="text-align: left;"
													name="middlename" type="text"> <span
													class="error_form text-danger" id="error_middlename"
													style="display: none;">Enter Middle Name.</span>
											</div>
										</div>

										<div class="form-group">
											<label class="control-label col-sm-2" for="lastname">Last
												Name : <span class="text-danger">*</span>
											</label>
											<div class="col-sm-10">
												<input id="lastname" class="form-control"
													onchange="trim(this)" placeholder="Last Name"
													value="${editUser.lastName}" style="text-align: left;"
													name="lastname" type="text"> <span
													class="error_form text-danger" id="error_lastname"
													style="display: none;">Enter Last Name.</span>
											</div>
										</div>

										<div class="form-group">
											<label class="control-label col-sm-2" for="userEmail">Email
												: <span class="text-danger">*</span>
											</label>
											<div class="col-sm-10">
												<input id="userEmail" class="form-control"
													onchange="trim(this)" placeholder="Email"
													value="${editUser.email}" style="text-align: left;"
													name="userEmail" type="text"> <span
													class="error_form text-danger" id="error_userEmail"
													style="display: none;">Enter Valid Email.</span>
											</div>
										</div>

										<div class="form-group">
											<label class="control-label col-sm-2" for="designation">Designation
												: <span class="text-danger">*</span>
											</label>
											<div class="col-sm-10">
												<input id=designation class="form-control"
													onchange="trim(this)" placeholder="Designation"
													value="${editUser.designation}" style="text-align: left;"
													name="designation" type="text"> <span
													class="error_form text-danger" id="error_designation"
													style="display: none;">Enter Designation.</span>
											</div>
										</div>

										<div class="form-group">
											<label class="control-label col-sm-2" for="joiningDate">Joining
												Date : <span class="text-danger">*</span>
											</label>
											<div class="col-sm-10">
												<input id="joiningDate" class="form-control datepicker"
													onchange="trim(this)" data-end-date="0d" data-format="dd-mm-yyyy" placeholder="Joining Date"
													value="${editUser.joiningDate}" style="text-align: left;"
													name="joiningDate" type="text" autocomplete="off">
												<span class="error_form text-danger" id="error_joiningDate"
													style="display: none;">Enter Joining Designation.</span>
											</div>
										</div>


										<input type="hidden" name="remove" value="0">


										<c:choose>
											<c:when test="${isEdit==1}">
												<input id="isEdit" value="1" name="isEdit" type="hidden">

											</c:when>

											<c:otherwise>
												<input id="isEdit" value="0" name="isEdit" type="hidden">
												<hr>
												<div class="row">
													<div class="col-xs-12">
														<h4 class="title pull-left">LOGIN DETAIL</h4>
													</div>
												</div>

												<div class="form-group">
													<label class="control-label col-sm-2" for="userName">User
														Name : <span class="text-danger">*</span>
													</label>
													<div class="col-sm-10">
														<input id="userName" class="form-control"
															placeholder="User Name" value="${editUser.userName}"
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
														<input id="userPass" class="form-control" 															placeholder="Password" value="${editUser.pass}"
															style="text-align: left;" name="userPass" type="password">
														<span class="error_form text-danger" id="error_userPass"
															style="display: none;">Enter Password.</span>
													</div>
												</div>

												<div class="form-group">
													<label class="control-label col-sm-2" for="reuserPass">Re enter-Password
														: <span class="text-danger">*</span>
													</label>
													<div class="col-sm-10">
														<input id="reuserPass" class="form-control" 
															placeholder="Re-Password" value="${editUser.pass}"
															style="text-align: left;" name="reuserPass"
															type="password"> <span
															class="error_form text-danger" id="error_reuserPass"
															style="display: none;">Re Enter Password.</span>
													</div>
												</div>

											</c:otherwise>
										</c:choose>

										<div class="form-group">
											<div class="col-sm-offset-2 col-sm-10">
												<button type="submit" class="btn btn-primary" id="">Submit</button>
												<input id="available" value="1" name="available" type="hidden">
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
			//checkSame() akki
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
												$("#error_firstname").hide();
												$("#error_lastname").hide();
												$("#error_userEmail").hide();
												$("#error_designation").hide();
												$("#error_joiningDate").hide();

												var isEdit = $("#isEdit").val();

												if (!$("#firstname").val()) {

													isError = true;
													$("#error_firstname")
															.show();
												}

												if (!$("#lastname").val()) {

													isError = true;

													$("#error_lastname").show();

												}

												if (!$("#userEmail").val()
														|| !validateEmail($(
																"#userEmail")
																.val())) {

													isError = true;

													$("#error_userEmail")
															.show();
												}

												if (!$("#designation").val()) {

													isError = true;

													$("#error_designation")
															.show();
												}

												if (!$("#joiningDate").val()) {

													isError = true;

													$("#error_joiningDate")
															.show();
												}

												if (isEdit == 0) {

													$("#error_userName").hide();
													$("#error_userPass").hide();
													$("#error_reuserPass")
															.hide();

													if (!$("#userName").val()) {

														isError = true;
														$("#error_userName")
																.html(
																		"Enter User Name");
														$("#error_userName")
																.show();
													}

													if (!$("#userPass").val()) {

														isError = true;
														$("#error_userPass")
																.html(
																		"Enter Password");
														$("#error_userPass")
																.show();
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
															&& $("#userPass")
																	.val() != ""
															&& $("#userPass")
																	.val() != $(
																	"#reuserPass")
																	.val()) {

														isError = true;

														$("#error_reuserPass")
																.html(
																		"Password not matched");

														$("#error_reuserPass")
																.show();
													}
												}
												
												if ($("#available").val()==0) {

													isError = true;
													$("#error_userName").html("username not available.");
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

			$.getJSON('${checkUserNameAvailable}', {

				userName : userName,
				ajax : 'true',

			}, function(data) {

				//alert(JSON.stringify(data));

				if (data.error == true) {
					document.getElementById("available").value=0;
					$("#error_userName").html("username not available.");
					$("#error_userName").show();
				}else{
					document.getElementById("available").value=1;
				}

			});
		}
	</script>

	<script type="text/javascript">
		/* $(function() {

			$('.datepicker').datepicker({
				autoclose : true,
				format : "dd-mm-yyyy",

			});
		}); */
	</script>
</body>
</html>