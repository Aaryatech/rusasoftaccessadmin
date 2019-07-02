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
										action="${pageContext.request.contextPath}/submitAssignedRole"
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
															Username <span class="text-danger">*</span>
														</label>
														<div class="col-sm-3">
															<input type="text" class="form-control" id="userName"
																name="userName" placeholder="User Name"
																autocomplete="off" readonly> <input type="hidden"
																id="userId" name="userId">
														</div>
													</div>

													<div class="form-group">
														<label class="control-label col-sm-2" for="page_name">
															Select Role <span class="text-danger">*</span>
														</label>
														<div class="col-sm-3">
															<select name="roleId" class="form-control" id="roleId"
																required>
																<option value="">Select Role</option>

																<c:forEach items="${createdRoleList}"
																	var="createdRoleList">
																	<option value="${createdRoleList.roleId}">${createdRoleList.roleName}</option>
																</c:forEach>

															</select>
														</div>
													</div>

													<div class="col-xs-12">

														<div class="col-xs-12">

															<table id="example-1"
																class="table table-striped dt-responsive display">
																<thead>
																	<tr>
																		<th width="10%">Sr No</th>
																		<th>User Name</th>
																		<th>Assign Role</th>
																		<th>Action</th>
																	</tr>

																</thead>

																<tbody>

																	<c:forEach items="${userList}" var="userList"
																		varStatus="count">
																		<tr>
																			<td><c:out value="${count.index+1}" /></td>
																			<td><c:out value="${userList.userName}" /></td>
																			<td><c:forEach items="${createdRoleList}"
																					var="createdRoleList">
																					<c:if
																						test="${userList.roleId==createdRoleList.roleId}">
																						<c:out value="${createdRoleList.roleName}" />
																					</c:if>
																				</c:forEach></td>
																			<td><a title="Edit" rel="tooltip"
																				data-color-class="detail"
																				data-animate=" animated fadeIn " href="#"
																				data-toggle="tooltip" data-original-title="Edit"
																				onclick="getUserName(/${userList.userName}/,${userList.userId})"><span
																					class="glyphicon glyphicon-edit"></span></a></td>
																		</tr>


																	</c:forEach>

																</tbody>
															</table>

														</div>
														<div class="form-group">
															<div class="col-sm-offset-2 col-sm-10">
																<button type="submit" class="btn btn-primary">Submit</button>
																<button type="reset" class="btn btn-default">Reset</button>
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
		 

		function getUserName(userName,userId) {
			
 			//alert(userName);
			document.getElementById("userName").value = userName;
			document.getElementById("userId").value = userId

		}
	</script>








</body>
</html>