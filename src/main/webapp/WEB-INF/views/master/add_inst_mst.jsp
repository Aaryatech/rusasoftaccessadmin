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
<c:url var="getInstituteMasterByAishe" value="/getInstituteMasterByAishe" />
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
							action="${pageContext.request.contextPath}/saveMhInst"
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
											<label class="control-label col-sm-2" for="aishe_code">AISHE Code : <span class="text-danger">*</span>
											</label>
											<div class="col-sm-8">
												<input id="aishe_code" class="form-control aishe"
													onchange="trim(this)" placeholder="AISHE Code"
													value="" style="text-align: left;"
													name="aishe_code" type="text"> <span
													class="error_form text-danger" id="error_aishe_code"
													style="display: none;">Enter AISHE Code.</span>
											</div>
											<div class="col-sm-2"><button type="button" onclick="checkAISHE()" class="btn btn-primary" id="">Search</button></div>
										</div>

										<div class="form-group">
											<label class="control-label col-sm-2" for="inst_name">Institute Name : <span class="text-danger">*</span> </label>
											<div class="col-sm-10">
												<input id="inst_name" class="form-control"
													onchange="trim(this)" placeholder="Institute Name"
													value="" style="text-align: left;"
													name="inst_name" type="text"> <span
													class="error_form text-danger" id="error_inst_name"
													style="display: none;">Enter Institute Name.</span>
											</div>
										</div>

										<div class="form-group">
											<label class="control-label col-sm-2" for="dist">District : <span class="text-danger">*</span>
											</label>
											<div class="col-sm-10">
												<input id="dist" class="form-control"
													onchange="trim(this)" placeholder="Last Name"
													value="" style="text-align: left;"
													name="dist" type="text"> <span
													class="error_form text-danger" id="error_dist"
													style="display: none;">Enter District Name.</span>
											</div>
										</div>

										<input type="hidden" name="remove" value="0">

										<div class="form-group">
											<div class="col-sm-offset-2 col-sm-10">
												<button type="submit" id="submtbtn" disabled class="btn btn-primary" id="">Save</button>
												
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
		
		$( "#aishe_code" ).focusout(function() {
			checkAISHE();
			});
		
		$(document)
				.ready(
						function($) {

							$("#form_sample_2")
									.submit(
											function(e) {
												var isError = false;
												var errMsg = "";
												$("#error_aishe_code").hide();
												$("#error_inst_name").hide();
												$("#error_dist").hide();


												if (!$("#aishe_code").val()) {

													isError = true;
													$("#error_aishe_code")
															.show();
												}

												if (!$("#inst_name").val()) {

													isError = true;

													$("#error_inst_name").show();

												}

												if (!$("#dist").val()) {

													isError = true;

													$("#error_dist")
															.show();
												}

												if (!isError) {

													document
															.getElementById("submtbtn").disabled = true;
													return true;

												}
												return false;
											});
						});
		//

		function checkAISHE() {
			var aisheCode = document.getElementById("aishe_code").value;
			if(aisheCode.length>3){
			$("#error_aishe_code").html("");
			document.getElementById("submtbtn").disabled = false;
			//alert(userName.length);
			$.getJSON('${getInstituteMasterByAishe}', {
				aisheCode : aisheCode,
				ajax : 'true',
			}, function(data) {
				//alert(JSON.stringify(data));
				if (parseInt(data.mhInstId)==0) {
					$("#error_aishe_code").html("AISHE Code not available in database, you can add.");
					$("#error_aishe_code").show();
					document.getElementById("inst_name").value="";
					document.getElementById("dist").value="";
				}else if(parseInt(data.mhInstId)<0){
					document.getElementById("submtbtn").disabled = true;
					$("#error_aishe_code").html("Institute already registered with this aishe code.You can not add/edit.");
					$("#error_aishe_code").show();
				}else{
					document.getElementById("aishe_code").value=data.aisheCode;
					document.getElementById("inst_name").value=data.instName;
					document.getElementById("dist").value=data.district;
					$("#error_aishe_code").html("AISHE Code available in database, you can edit.");
					$("#error_aishe_code").show();
				}
			});
			}//end of if aishe code length >4
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