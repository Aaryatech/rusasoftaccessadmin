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
										action="${pageContext.request.contextPath}/submitCreateRole"
										method="post" name="form_sample_2" id="form_sample_2"
										onsubmit="return confirm('Do you really want to submit the form?');">

										<ul class="nav nav-tabs">
											<li class="active"><a href="#home" data-toggle="tab">
													<i class="fa fa-home"></i> ${title}
											</a></li>

										</ul>

										<div class="tab-content">
											<div class="tab-pane fade in active" id="home">

												<div>
													<div class="form-group">
														<label class="control-label col-sm-2" for="page_name">
															Role Name <span class="text-danger">*</span>
														</label>
														<div class="col-sm-10">
															<input type="text" class="form-control" id="roleName"
																name="roleName" placeholder="Enter Role Name"
																autocomplete="off" value="${editRole.roleName}" required>
															<input type="hidden"  id="roleId"
																name="roleId"  value="${editRole.roleId}"  >
														</div>
													</div>

													<div class="col-xs-12">

														<div class="col-xs-12">

															<table id="example-1"
																class="table table-striped dt-responsive display">
																<thead>
																	<tr>
																		<th width="10%">Sr No</th>
																		<th>Modules</th>
																		<th>View</th>
																		<th>Add</th>
																		<th>Edit</th>
																		<th>Delete</th>


																	</tr>

																</thead>



																<tbody>

																	<c:forEach items="${allModuleList}" var="allModuleList"
																		varStatus="count">
																		<tr>
																			<td><b><c:out value="${count.index+1}" /> </b><input
																				type="checkbox" id="header${allModuleList.moduleId}"
																				name="header${allModuleList.moduleId}"
																				class="select_all"
																				onclick="checkSubmodule(${allModuleList.moduleId})"
																				value="0"></td>
																			<td colspan="5">${allModuleList.iconDiv} &nbsp; <b><c:out
																						value="${allModuleList.moduleName}" /></b></td>
																		</tr>

																		<c:forEach
																			items="${allModuleList.accessRightSubModuleList}"
																			var="allSubModuleList">
																			<tr>
																				<td></td>
																				<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<c:out
																						value="${allSubModuleList.subModulName}" /></td>
																				<td><c:choose>
																						<c:when test="${allSubModuleList.view==1}">
																							<input type="checkbox"
																								id="${allSubModuleList.subModuleId}view${allSubModuleList.moduleId}"
																								class="check${allModuleList.moduleId}"
																								name="${allSubModuleList.subModuleId}view${allSubModuleList.moduleId}"
																								value="1"
																								onclick="changeValue(1,${allSubModuleList.subModuleId},${allSubModuleList.moduleId})"
																								checked>
																						</c:when>
																						<c:otherwise>
																							<input type="checkbox"
																								id="${allSubModuleList.subModuleId}view${allSubModuleList.moduleId}"
																								class="check${allModuleList.moduleId}"
																								name="${allSubModuleList.subModuleId}view${allSubModuleList.moduleId}"
																								value="0"
																								onclick="changeValue(1,${allSubModuleList.subModuleId},${allSubModuleList.moduleId})">
																						</c:otherwise>
																					</c:choose></td>
																				<td><c:choose>
																						<c:when test="${allSubModuleList.addApproveConfig==1}">
																							<input type="checkbox"
																								id="${allSubModuleList.subModuleId}add${allSubModuleList.moduleId}"
																								class="check${allModuleList.moduleId}"
																								name="${allSubModuleList.subModuleId}add${allSubModuleList.moduleId}"
																								value="1"
																								onclick="changeValue(2,${allSubModuleList.subModuleId},${allSubModuleList.moduleId})"
																								checked>
																						</c:when>
																						<c:otherwise>
																							<input type="checkbox"
																								id="${allSubModuleList.subModuleId}add${allSubModuleList.moduleId}"
																								class="check${allModuleList.moduleId}"
																								name="${allSubModuleList.subModuleId}add${allSubModuleList.moduleId}"
																								value="0"
																								onclick="changeValue(2,${allSubModuleList.subModuleId},${allSubModuleList.moduleId})">
																						</c:otherwise>
																					</c:choose></td>
																				<td><c:choose>
																						<c:when test="${allSubModuleList.editReject==1}">
																							<input type="checkbox"
																								class="check${allModuleList.moduleId}"
																								id="${allSubModuleList.subModuleId}edit${allSubModuleList.moduleId}"
																								name="${allSubModuleList.subModuleId}edit${allSubModuleList.moduleId}"
																								value="1"
																								onclick="changeValue(3,${allSubModuleList.subModuleId},${allSubModuleList.moduleId})"
																								checked>
																						</c:when>
																						<c:otherwise>
																							<input type="checkbox"
																								class="check${allModuleList.moduleId}"
																								id="${allSubModuleList.subModuleId}edit${allSubModuleList.moduleId}"
																								name="${allSubModuleList.subModuleId}edit${allSubModuleList.moduleId}"
																								value="0"
																								onclick="changeValue(3,${allSubModuleList.subModuleId},${allSubModuleList.moduleId})">
																						</c:otherwise>
																					</c:choose></td>
																				<td><c:choose>
																						<c:when test="${allSubModuleList.deleteRejectApprove==1}">
																							<input type="checkbox"
																								class="check${allModuleList.moduleId}"
																								id="${allSubModuleList.subModuleId}delete${allSubModuleList.moduleId}"
																								name="${allSubModuleList.subModuleId}delete${allSubModuleList.moduleId}"
																								value="1"
																								onclick="changeValue(4,${allSubModuleList.subModuleId},${allSubModuleList.moduleId})"
																								checked>
																						</c:when>
																						<c:otherwise>
																							<input type="checkbox"
																								class="check${allModuleList.moduleId}"
																								id="${allSubModuleList.subModuleId}delete${allSubModuleList.moduleId}"
																								name="${allSubModuleList.subModuleId}delete${allSubModuleList.moduleId}"
																								value="0"
																								onclick="changeValue(4,${allSubModuleList.subModuleId},${allSubModuleList.moduleId})">
																						</c:otherwise>
																					</c:choose></td>
																			</tr>
																		</c:forEach>

																	</c:forEach>

																</tbody>
															</table>

														</div>
														
														
														<div class="form-group">
													<div class="col-sm-offset-3 col-sm-9">


<button type="submit" id="sub_button" class="btn btn-primary"
													><i class="${sessionScope.addIcon}" aria-hidden="true"></i>&nbsp;&nbsp;Save</button>
														
				</div>
												</div>
														

													</div>


													<div class="clearfix"></div>

												</div>

											</div>
										</div>
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




	<script>
			function checkSubmodule(moduleId) {
				
				 
				$.getJSON('${getSubmoduleList}', {
					moduleId : moduleId,
					ajax : 'true',

				}, function(data) { 
					 
					
					if(document.getElementById("header"+moduleId).checked == true){
						
						for(var i=0 ; i<data.length; i++){
							 
							document.getElementById(data[i]+"view"+moduleId).checked=true;
							 document.getElementById(data[i]+"add"+moduleId).checked=true;
							 document.getElementById(data[i]+"edit"+moduleId).checked=true;
							 document.getElementById(data[i]+"delete"+moduleId).checked=true;
							 document.getElementById(data[i]+"view"+moduleId).value=1;
							 document.getElementById(data[i]+"add"+moduleId).value=1;
							 document.getElementById(data[i]+"edit"+moduleId).value=1;
							 document.getElementById(data[i]+"delete"+moduleId).value=1;
						}
						 
					 }else{
						 for(var i=0 ; i<data.length; i++){
								
								document.getElementById(data[i]+"view"+moduleId).checked=false;
								 document.getElementById(data[i]+"add"+moduleId).checked=false;
								 document.getElementById(data[i]+"edit"+moduleId).checked=false;
								 document.getElementById(data[i]+"delete"+moduleId).checked=false;
								 document.getElementById(data[i]+"view"+moduleId).value=0;
								 document.getElementById(data[i]+"add"+moduleId).value=0;
								 document.getElementById(data[i]+"edit"+moduleId).value=0;
								 document.getElementById(data[i]+"delete"+moduleId).value=0;
							}
					 }
				
				});
 
				 
			}
			
			function changeValue(type,subModuleId,moduleId) {
				 
				 
							 if(type==1){
								 if(document.getElementById(subModuleId+"view"+moduleId).checked == true){
									 
									 document.getElementById(subModuleId+"view"+moduleId).value=1;
									 
								 }else{
									 
									 document.getElementById(subModuleId+"view"+moduleId).value=0;
								 }
								
							 }else if(type==2){
								 if(document.getElementById(subModuleId+"add"+moduleId).checked == true){
									 
								 	document.getElementById(subModuleId+"add"+moduleId).value=1;
								 }else{
									 document.getElementById(subModuleId+"add"+moduleId).value=0;
								 }
							 }else if(type==3){
								 if(document.getElementById(subModuleId+"edit"+moduleId).checked == true){
									 
									 document.getElementById(subModuleId+"edit"+moduleId).value=1;
									 
								 }else{
									 
									 document.getElementById(subModuleId+"edit"+moduleId).value=0;
									 
								 }
								 
							 }else if(type==4){
								 
								 if(document.getElementById(subModuleId+"delete"+moduleId).checked == true){
									 
									 document.getElementById(subModuleId+"delete"+moduleId).value=1;
									 
								 }else{
									 
									 document.getElementById(subModuleId+"delete"+moduleId).value=0;
									 
								 }
								 
							 }
							  
			}
			
		</script>








</body>
</html>