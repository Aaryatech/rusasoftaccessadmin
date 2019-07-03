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
#main-content section {
	position: absolute;
	top: 0;
	bottom: 0;
	left: 0;
	right: 0;
	margin: auto;
}
</style>


<!-- BEGIN BODY -->
<body onload="showIsReg()" class=" ">
	<c:url value="/checkUniqueField" var="checkUniqueField"></c:url>
	<!-- START TOPBAR -->
	<!-- END TOPBAR -->
	<!-- START CONTAINER -->
	<div class="page-container row-fluid container-fluid">

		<!-- SIDEBAR - START -->

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

				<!-- MAIN CONTENT AREA STARTS -->




				<div class="col-lg-12">
					<section class="box ">

						<header class="panel_header">
							<h2 align="center">INSTITUTE REGISTRATION</h2>

							<div class="actions panel_actions pull-right">
								<a href="${pageContext.request.contextPath}/showPendingInstitute"><button
										type="button" class="btn btn-info">Back</button></a>
							</div>

						</header>


						<div class="content-body">
							<div class="row">
								<div class="col-md-12">


									<!-- 	<ul class="nav nav-tabs">
										<li class="active"><a href="#home" data-toggle="tab">
												<i class="fa fa-home"></i> Register Form
										</a></li>


									</ul>

									<div class="tab-content">
										<div class="tab-pane fade in active" id="home">
 -->
									<form class="form-horizontal"
										action="#"
										method="post" name="form_sample_2" id="form_sample_2">

										<input type="hidden" id="inst_id" name="inst_id"
											value="${showInst.instituteId}">

										<div class="row">
											<div class="col-md-12">
												<!-- <div class="col-sm-2"></div> -->

												<!-- <p class="desc text-danger fontsize11">Notice : This
													form strictly need to be filled by Institutes coming under
													RUSA Maharashtra Only. You can access RUSA portal only
													after authorisation done by RUSA officials.</p>
 -->
												<div class="form-group">
													<label class="control-label col-sm-3" for="page_name">Institute
														Name<span class="text-danger">*</span>
													</label>
													<div class="col-sm-7">
														<input type="text" maxlength="100" onchange="trim(this)"
															class="form-control" id="inst_name" readonly
															value="${showInst.instituteName}" name="inst_name"
															placeholder="Complete Name of Institute" required>
													</div>
												</div>
												<div class="form-group">
													<label class="control-label col-sm-3" for="page_name">AISHE
														Code <span class="text-danger">*</span>
													</label>
													<div class="col-sm-7">
														<input type="text" maxlength="50" onchange="trim(this)"
															class="form-control" id="aishe_code" readonly
															value="${showInst.aisheCode}" name="aishe_code"
															required>
													</div>
												</div>




												<div class="form-group">
													<label class="control-label col-sm-3" for="page_order">Institute
														Address<span class="text-danger">*</span>
													</label>
													<div class="col-sm-7">
														<input type="text" onchange="trim(this)" maxlength="200"
															class="form-control" id="inst_add" readonly
															value="${showInst.instituteAdd}" name="inst_add"
															required>
													</div>
												</div>


												<div class="form-group">
													<label class="control-label col-sm-3" for="planning">2F/12B
														Registration <span class="text-danger">*</span>
													</label>
													<div class="col-sm-7">

													
														
													<c:if test="${showInst.isRegistration==1}">
														<input type="radio" id="is_registration"
																	name="is_registration" value="1"disabled="disabled" checked> Yes
																	
														<input type="radio" id="is_registration" disabled="disabled"
																	name="is_registration" value="0" >No
																	</c:if>
																	
													<c:if test="${showInst.isRegistration==0}">
														<input type="radio" id="is_registration"
																	name="is_registration" disabled="disabled" value="1" > Yes
																	
														<input type="radio" id="is_registration"
																	name="is_registration" disabled="disabled" checked  value="0" >No
													</c:if>
													</div>
													

												</div>
												<div class="form-group" id="abc">
													<label class="control-label col-sm-3" for="page_order">Date
														of Registration <span class="text-danger">*</span>
													</label>


													<div class="col-sm-7">
														<input type="text" class="form-control datepicker" autocomplete="off"
															id="reg_date" value="${showInst.regDate}" name="reg_date" readonly
															 required>
													</div>
												</div>

												<div class="form-group">
													<label class="control-label col-sm-3" for="page_order">Trust/Society
														Name <span class="text-danger">*</span>
													</label>
													<div class="col-sm-7">
														<input type="text" onchange="trim(this)" maxlength="100"
															class="form-control" id="trusty_name" readonly
															value="${showInst.trustName}"  required>
													</div>
												</div>

												<div class="form-group">
													<label class="control-label col-sm-3" for="page_order">Trust/Society
														Address <span class="text-danger">*</span>
													</label>
													<div class="col-sm-7">
														<input type="text" onchange="trim(this)" maxlength="200"
															class="form-control" id="trusty_add" readonly
															value="${showInst.trustAdd}" name="trusty_add" required>
													</div>
												</div>

												<div class="form-group">
													<label class="control-label col-sm-3" for="page_order">Official
														Contact No <span class="text-danger">*</span>
													</label>
													<div class="col-sm-7">
														<input type="text" value="${showInst.trustContactNo}" onchange="trim(this)"
															maxlength="15" class="form-control" id="trusty_con_no"
															name="trusty_con_no" readonly required>
													</div>
												</div>

												<div class="form-group">
													<label class="control-label col-sm-3" for="page_order">
														Chairman/President Name<span class="text-danger">*</span>
													</label>
													<div class="col-sm-7">
														<input type="text" onchange="trim(this)" maxlength="200"
															class="form-control" id="pres_name" readonly
															value="${showInst.presidentName}" name="pres_name" required>
													</div>
												</div>

												<div class="form-group">
													<label class="control-label col-sm-3" for="page_order">Chairman
														Contact No <span class="text-danger">*</span>
													</label>
													<div class="col-sm-7">
														<input type="text" value="${showInst.presidenContact}"
															maxlength="15" class="form-control" id="pres_contact"
															name="pres_contact" readonly required>
													</div>
												</div>

												<div class="form-group">
													<label class="control-label col-sm-3" for="page_order">Email
														ID <span class="text-danger">*</span>
													</label>
													<div class="col-sm-7">
														<input type="email" onchange="trim(this)" readonly
															class="form-control" id="pres_email"
															value="${showInst.presidentEmail}" name="pres_email"
															placeholder="Chairman/President Email Id"
															required>
													</div>
												</div>




												<div class="form-group">
													<label class="control-label col-sm-3" for="page_order">
														Principal Name <span class="text-danger">*</span>
													</label>
													<div class="col-sm-7"> 
														<input type="text" maxlength="100" onchange="trim(this)"
															class="form-control" id="princ_name" readonly
															value="${showInst.principalName}" name="princ_name"
															placeholder="Name of Principal" required>
													</div>
												</div>

												<div class="form-group">
													<label class="control-label col-sm-3" for="page_order">Mobile
														No <span class="text-danger">*</span>
													</label>
													<div class="col-sm-7">
														<input type="text" pattern="^[1-9]{1}[0-9]{9}$"
															maxlength="10" class="form-control" id="princ_contact"
															value="${showInst.contactNo}" name="princ_contact"
														  readonly oninput="checkUnique(this.value,1)" required>
														
													</div>

												</div>

												<div class="form-group">
													<label class="control-label col-sm-3" for="page_order">Email
														ID <span class="text-danger">*</span>
													</label>
													<div class="col-sm-7">
														<input type="email" onchange="trim(this)" 
															class="form-control" id="princ_email"
															value="${showInst.email}" readonly name="princ_email">
													</div>
												</div>


												<div class="form-group">
													<div class="col-sm-offset-3 col-sm-7">
													
													
													<a href="${pageContext.request.contextPath}/approveInstitutes/${showInst.instituteId}""><button
										type="button" class="btn btn-primary">Approve</button></a>
														<%-- <a href="${pageContext.request.contextPath}/approveInstitutes/${showInst.instituteId}">
														<button class="btn btn-primary">Approve</button></a> --%>
														<!-- <button type="reset" class="btn btn-default">Reset</button> -->
													</div>
												</div>



											</div>
										</div>

									</form>
								</div>
								<div class="clearfix"></div>
							</div>
							<div class="col-sm-3"></div>

						
						</div>
					</section>



				</div>
			</section>

		</section>
	</div>



	<!-- MAIN CONTENT AREA ENDS -->

	<!-- END CONTENT -->

	

	<script type="text/javascript">
		function showDiv(value) {

			if (value == 1) {
				//alert(value);
				document.getElementById("abc").style.display = "block";
			} else {
				//alert(value);
				document.getElementById("abc").style.display = "none";
			}
		}

		function setDate(value) {
			//alert("Value " +value)
			if (value == 0) {
				//alert(value)
				document.getElementById("reg_date").removeAttribute("required");
				document.getElementById("abc").style.display = "none";

				//alert(value)
			} else {
				//alert(value)
				document.getElementById("reg_date").setAttribute("required",
						"true");
				document.getElementById("abc").style.display = "block";

				//alert(value)

			}

		}

		function getCOPO() {
			//alert("hii");

			var iqacName = document.getElementById("inst_name").value
			var aishe_code = document.getElementById("aishe_code").value
			var inst_add = document.getElementById("inst_add").value
			var is_registration = document.getElementById("is_registration").value
			var reg_date = document.getElementById("reg_date").value
			var trusty_name = document.getElementById("trusty_name").value
			var trusty_add = document.getElementById("trusty_add").value
			var trusty_con_no = document.getElementById("trusty_con_no").value
			var pres_name = document.getElementById("pres_name").value
			var pres_contact = document.getElementById("pres_contact").value
			var pres_email = document.getElementById("pres_email").value
			var princ_name = document.getElementById("princ_name").value
			var princ_contact = document.getElementById("princ_contact").value
			var princ_email = document.getElementById("princ_email").value
			var temp;
			var temp1;

			if (is_registration == 1) {
				alert(reg_date);
				temp = "Yes";
				$('#reg_date1').html(reg_date);

			} else {
				alert("no...");
				temp = "No";
				temp1 = "-";
				$('#reg_date1').html(temp1);

			}

			$('#inst_Name1').html(iqacName);
			$('#aishe_code1').html(aishe_code);
			$('#inst_Add1').html(inst_add);
			$('#is_reg1').html(temp);

			$('#trust_Name1').html(trusty_name);
			$('#trust_Add1').html(trusty_add);
			$('#trust_Con1').html(trusty_con_no);

			$('#chairman_Name1').html(pres_name);
			$('#Chair_Con1').html(pres_contact);
			$('#chair_Email1').html(pres_email);
			$('#princi_Name1').html(princ_name);
			$('#princi_Con1').html(princ_contact);
			$('#princi_Email1').html(princ_email);

		}
		function getOpt() {
			//submit afrer showing details on modal dialogue
			var form = document.getElementById("form_sample_2");
			form.submit();

		}

		function showIsReg() {

			var x = $
			{
				editInst.instituteId
			}

			if (x > 0) {

				var isReg = $
				{
					editInst.isRegistration
				}
				;
				//alert("Is Reg " +isReg);
				if (isReg == 0) {

					document.getElementById("abc").style.display = "none";
					document.getElementById("reg_date").removeAttribute(
							"required");

				} else {
					document.getElementById("abc").style.display = "block";

				}

			}

		}
	</script>


	<!-- END CONTAINER -->
	<!-- LOAD FILES AT PAGE END FOR FASTER LOADING -->
	<script type="text/javascript">
		$(function() {

			$('.datepicker').datepicker({
				autoclose : true,
				format : "dd-mm-yyyy",
				changeYear : true,
				changeMonth : true
			});
		});

		function checkUnique(inputValue, valueType) {
			//alert(inputValue);

			var primaryKey = $
			{
				editInst.instituteId
			}
			;
			//alert("Primary key"+primaryKey);
			var isEdit = 0;
			if (primaryKey > 0) {
				isEdit = 1;
			}
			//alert("Is Edit " +isEdit);
			var valid = false;
			if (valueType == 1) {
				//alert("Its Mob no");
				if (inputValue.length == 10) {
					valid = true;
					//alert("Len 10")
				} else {
					valid = false;
				}
			} else if (valueType == 2) {
				//alert("Its Email " );

				var mailformat = /^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+$/;
				if (inputValue.match(mailformat)) {
					valid = true;
					//alert("Valid Email Id");
				} else {
					valid = false;
					//alert("InValid Email Id");
				}
			}
			if (valid == true)
				$
						.getJSON(
								'${checkUniqueField}',
								{

									inputValue : inputValue,
									valueType : valueType,
									primaryKey : primaryKey,
									isEdit : isEdit,
									tableId : 1,

									ajax : 'true',

								},
								function(data) {

									//alert("Data  " +JSON.stringify(data));
									if (data.error == true) {
										if (valueType == 2) {
											document
													.getElementById("princ_email").value = "";

											alert("This Email Id is Already Exist in Database. Please Login with Your Credential.");

										} else {
											document
													.getElementById("princ_contact").value = "";

											alert("This Mobile No is Already Exist in Database. Please Login with Your Credential.");
										}
									}
								});
		}
	</script>

	<script type="text/javascript">
		var wasSubmitted = false;
		function checkBeforeSubmit() {
			if (!wasSubmitted) {
				var x = confirm("Do you really want to submit the form?");
				if (x == true) {
					wasSubmitted = true;
					document.getElementById("sub_button").disabled = true;
					return wasSubmitted;
				}
			}
			return false;
		}
		function trim(el) {
			el.value = el.value.replace(/(^\s*)|(\s*$)/gi, ""). // removes leading and trailing spaces
			replace(/[ ]{2,}/gi, " "). // replaces multiple spaces with one space 
			replace(/\n +/, "\n"); // Removes spaces after newlines
			return;
		}
	</script>

	<jsp:include page="/WEB-INF/views/include/footer.jsp"></jsp:include>



</body>
</html>