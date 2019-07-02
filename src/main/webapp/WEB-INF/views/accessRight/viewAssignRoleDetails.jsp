<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!doctype html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<title>Shiv Admin</title>
<link rel="apple-touch-icon" href="apple-icon.png">
<link rel="shortcut icon"
	href="${pageContext.request.contextPath}/resources/favicon.ico">

<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/assets/css/normalize.css">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/assets/css/bootstrap.min.css">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/assets/css/font-awesome.min.css">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/assets/css/themify-icons.css">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/assets/css/flag-icon.min.css">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/assets/css/cs-skin-elastic.css">

<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/assets/scss/style.css">


<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/assets/css/menu.css">

<link
	href="${pageContext.request.contextPath}/resources/assets/css/lib/vector-map/jqvmap.min.css"
	rel="stylesheet">

<link
	href='https://fonts.googleapis.com/css?family=Open+Sans:400,600,700,800'
	rel='stylesheet' type='text/css'>


<script type="text/javascript"
	src="https://www.gstatic.com/charts/loader.js"></script>
<script type="text/javascript"
	src="//ajax.googleapis.com/ajax/libs/jquery/1.10.2/jquery.min.js"></script>


</head>


<!-- 
<script type="text/javascript"
	src="https://www.gstatic.com/charts/loader.js"></script> -->


<body onload="setData()">
	<c:url var="getChartData" value="/getGraphDataForDistwiseOrderHistory"></c:url>

	<c:url var="getCatOrdQty" value="/getCatOrdQty"></c:url>

	<c:url var="getCatwiseTrend" value="/getCatwiseTrend"></c:url>



	<!-- Left Panel -->
	<jsp:include page="/WEB-INF/views/include/left.jsp"></jsp:include>
	<!-- Left Panel -->


	<!-- Header-->
	<%-- <jsp:include page="/WEB-INF/views/common/right.jsp"></jsp:include> --%>
	<!-- Header-->



	<div class="content mt-3">
		<div class="animated fadeIn">

			<div class="row">
				<c:choose>
					<c:when test="${isError==1}">
						<div class="col-sm-12">
							<div
								class="sufee-alert alert with-close alert-danger alert-dismissible fade show">

								<button type="button" class="close" data-dismiss="alert"
									aria-label="Close">
									<span aria-hidden="true">×</span>
								</button>
								<strong>Data not submitted</strong>
							</div>
						</div>
					</c:when>

					<c:when test="${isError==2}">
						<div class="col-sm-12">
							<div
								class="sufee-alert alert with-close alert-success alert-dismissible fade show">

								<button type="button" class="close" data-dismiss="alert"
									aria-label="Close">
									<span aria-hidden="true">×</span>
								</button>
								<strong>Data Submitted Successfully</strong>
							</div>
						</div>
					</c:when>

				</c:choose>

				<div class="col-xs-12 col-sm-12">
					<div class="card">
						<div class="card-header">
							<div class="col-md-4">
								<strong>${title}</strong>
							</div>
							<div class="col-md-4"></div>
							<div class="col-md-4" align="left">

								<a href="${pageContext.request.contextPath}/showRoleList"><strong>Role
										List</strong></a>

							</div>
						</div>
						<div class="card-body card-block">
							<form id="validation-form"
								action="${pageContext.request.contextPath}/submitCreateRole"
								class="form-horizontal" enctype="multipart/form-data"
								method="post">

								<input type="hidden" name="roleId" id="roleId" value="${roleId}" />
								<!-- <div class="form-group">
									<label class="col-sm-3 col-lg-2 control-label">Enter Role
										Name</label>
									<div class="col-sm-9 col-lg-10 controls">
										<input type="text" name="roleName" id="roleName"
											placeholder="Department Name" class="form-control"
											data-rule-required="true" />
									</div><br/>
								</div> -->


								<div class="box-content">
									<div class="col-md-2">User Name</div>
									<div class="col-md-4">
										<input type="text" name="userName" id="userName"
											placeholder="User Name" class="form-control"
											data-rule-required="true" value="${userName}" readonly />
									</div>
									<br />


								</div>
								<div class="box-content">
									<div class="col-md-2">Role Name</div>
									<div class="col-md-4">
										<input type="text" name="roleName" id="roleName"
											placeholder="Role Name" class="form-control"
											data-rule-required="true" value="${roleName}" readonly />
									</div>
									<br />


								</div>
								<!-- <input type="text" class="form-control" id="roleName" name="roleName"> -->

								<!-- <input type="submit" class="btn btn-info" value="View All" > -->
								<br />

								<div class="row">
									<div class="col-md-12 table-responsive">
										<!-- <table class=" " -->
										<table class="table table-bordered table-striped fill-head "
											style="width: 70%" id="table_grid">
											<thead>
												<tr>
													<td width="50">Sr.No.</td>
													<td width="200">Modules</td>
													<td width="50">View</td>
													<td width="50">Add</td>
													<td width="50">Edit</td>
													<td width="50">Delete</td>

												</tr>
											</thead>

											<!-- <thead>
									<tr>
										<td width="100">Sr.No.</td>
										<td width="500">Modules</td>
										<td width="100">View</td>
										<td width="100">Add</td>
										<td width="100">Edit</td>
										 <td width="100">Delete</td>

									</tr>
								</thead> -->

											<tbody>




												<c:set var="index" value="0" />

												<c:forEach items="${moduleJsonList}" var="moduleJsonList"
													varStatus="count">

													<c:set var="flag" value="0" />

													<c:forEach items="${moduleJsonList.subModuleJsonList}"
														var="subModuleJsonList">
														<%-- 	<c:set var="view" value="" />
											<c:set var="edit" value="" />
											<c:set var="delete" value="" />
											<c:set var="add" value="" /> --%>
														<c:choose>
															<c:when test="${subModuleJsonList.type==0}">
																<c:set var="flag" value="1" />
															</c:when>
														</c:choose>
													</c:forEach>


													<c:choose>
														<c:when test="${flag==1}">
															<tr>
																<!-- 	<td> &nbsp; </td>
											</tr><tr>  -->
																<c:set var="index" value="${index+1 }" />
																<td><c:out value="${index}" /></td>

																<td><b><c:out
																			value="${moduleJsonList.moduleName}" /></b></td>

															</tr>
														</c:when>
													</c:choose>
													<c:forEach items="${moduleJsonList.subModuleJsonList}"
														var="subModuleJsonList">
														<c:choose>
															<c:when test="${subModuleJsonList.type==0}">
																<tr>
																	<td></td>

																	<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<c:out
																			value="${subModuleJsonList.subModulName}" /></td>
																	<c:choose>
																		<c:when test="${subModuleJsonList.view=='visible'}">

																			<td><input type="checkbox"
																				class="check${moduleJsonList.moduleId}"
																				name="${subModuleJsonList.subModuleId}${subModuleJsonList.moduleId}"
																				value="view" checked></td>
																		</c:when>
																		<c:when test="${subModuleJsonList.view=='hidden'}">

																			<td><input type="checkbox"
																				class="check${moduleJsonList.moduleId}"
																				name="${subModuleJsonList.subModuleId}${subModuleJsonList.moduleId}"
																				value="view"></td>


																		</c:when>
																	</c:choose>

																	<c:choose>
																		<c:when
																			test="${subModuleJsonList.addApproveConfig=='visible'}">

																			<td><input type="checkbox"
																				class="check${moduleJsonList.moduleId}"
																				name="${subModuleJsonList.subModuleId}${subModuleJsonList.moduleId}"
																				value="add" checked></td>
																		</c:when>
																		<c:when
																			test="${subModuleJsonList.addApproveConfig=='hidden'}">

																			<td><input type="checkbox"
																				class="check${moduleJsonList.moduleId}"
																				name="${subModuleJsonList.subModuleId}${subModuleJsonList.moduleId}"
																				value="add"></td>


																		</c:when>
																	</c:choose>

																	<c:choose>
																		<c:when
																			test="${subModuleJsonList.editReject=='visible'}">

																			<td><input type="checkbox"
																				class="check${moduleJsonList.moduleId}"
																				name="${subModuleJsonList.subModuleId}${subModuleJsonList.moduleId}"
																				value="edit" checked></td>
																		</c:when>
																		<c:when
																			test="${subModuleJsonList.editReject=='hidden'}">
																			<td><input type="checkbox"
																				class="check${moduleJsonList.moduleId}"
																				name="${subModuleJsonList.subModuleId}${subModuleJsonList.moduleId}"
																				value="edit"></td>
																		</c:when>

																	</c:choose>
																	<c:choose>
																		<c:when
																			test="${subModuleJsonList.deleteRejectApprove=='visible'}">

																			<td><input type="checkbox"
																				class="check${moduleJsonList.moduleId}"
																				name="${subModuleJsonList.subModuleId}${subModuleJsonList.moduleId}"
																				value="delete" checked></td>
																		</c:when>
																		<c:when
																			test="${subModuleJsonList.deleteRejectApprove=='hidden'}">

																			<td><input type="checkbox"
																				class="check${moduleJsonList.moduleId}"
																				name="${subModuleJsonList.subModuleId}${subModuleJsonList.moduleId}"
																				value="delete"></td>


																		</c:when>
																	</c:choose>
																</tr>
															</c:when>
														</c:choose>


													</c:forEach>

												</c:forEach>
											</tbody>
										</table>
										<table class="table table-bordered table-striped fill-head "
											style="width: 70%" id="table_grid">
											<thead>
												<tr>
													<td width="100">Sr.No.</td>
													<td width="500">Modules</td>
													<td width="100">View</td>
													<td width="100">Approve</td>
													<td width="100">Reject</td>
													<td width="100">Reject-Approve</td>

												</tr>
											</thead>

											<tbody>

												<c:forEach items="${moduleJsonList}" var="moduleJsonList"
													varStatus="count">

													<c:set var="flag" value="0" />

													<c:forEach items="${moduleJsonList.subModuleJsonList}"
														var="subModuleJsonList">
														<c:choose>
															<c:when test="${subModuleJsonList.type==1}">
																<c:set var="flag" value="1" />
															</c:when>
														</c:choose>
													</c:forEach>


													<c:choose>
														<c:when test="${flag==1}">
															<tr>
																<!-- 	<td> &nbsp; </td>
											</tr><tr>  -->
																<c:set var="index" value="${index+1 }" />
																<td><c:out value="${index}" /></td>

																<td><b><c:out
																			value="${moduleJsonList.moduleName}" /></b></td>

															</tr>
														</c:when>
													</c:choose>
													<c:forEach items="${moduleJsonList.subModuleJsonList}"
														var="subModuleJsonList">
														<c:choose>
															<c:when test="${subModuleJsonList.type==1}">
																<tr>
																	<td></td>

																	<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<c:out
																			value="${subModuleJsonList.subModulName}" /></td>
																	<c:choose>
																		<c:when test="${subModuleJsonList.view=='visible'}">

																			<td><input type="checkbox"
																				name="${subModuleJsonList.subModuleId}${subModuleJsonList.moduleId}"
																				id="select_to_assign" value="view" checked></td>
																		</c:when>
																		<c:when test="${subModuleJsonList.view=='hidden'}">

																			<td><input type="checkbox"
																				name="${subModuleJsonList.subModuleId}${subModuleJsonList.moduleId}"
																				id="select_to_assign" value="view"></td>


																		</c:when>
																	</c:choose>

																	<c:choose>
																		<c:when
																			test="${subModuleJsonList.addApproveConfig=='visible'}">

																			<td><input type="checkbox"
																				name="${subModuleJsonList.subModuleId}${subModuleJsonList.moduleId}"
																				id="select_to_assign" value="add" checked></td>
																		</c:when>
																		<c:when
																			test="${subModuleJsonList.addApproveConfig=='hidden'}">

																			<td><input type="checkbox"
																				name="${subModuleJsonList.subModuleId}${subModuleJsonList.moduleId}"
																				id="select_to_assign" value="add"></td>


																		</c:when>
																	</c:choose>

																	<c:choose>
																		<c:when
																			test="${subModuleJsonList.editReject=='visible'}">

																			<td><input type="checkbox"
																				name="${subModuleJsonList.subModuleId}${subModuleJsonList.moduleId}"
																				id="select_to_assign" value="edit" checked></td>
																		</c:when>
																		<c:when
																			test="${subModuleJsonList.editReject=='hidden'}">
																			<td><input type="checkbox"
																				name="${subModuleJsonList.subModuleId}${subModuleJsonList.moduleId}"
																				id="select_to_assign" value="edit"></td>
																		</c:when>

																	</c:choose>
																	<c:choose>
																		<c:when
																			test="${subModuleJsonList.deleteRejectApprove=='visible'}">

																			<td><input type="checkbox"
																				name="${subModuleJsonList.subModuleId}${subModuleJsonList.moduleId}"
																				id="select_to_assign" value="delete" checked></td>
																		</c:when>
																		<c:when
																			test="${subModuleJsonList.deleteRejectApprove=='hidden'}">

																			<td><input type="checkbox"
																				name="${subModuleJsonList.subModuleId}${subModuleJsonList.moduleId}"
																				id="select_to_assign" value="delete"></td>


																		</c:when>
																	</c:choose>
																</tr>
															</c:when>
														</c:choose>


													</c:forEach>

												</c:forEach>
											</tbody>
										</table>

										<table class="table table-bordered table-striped fill-head "
											style="width: 70%" id="table_grid">
											<thead>
												<tr>
													<td width="100">Sr.No.</td>
													<td width="500">Modules</td>
													<td width="100">View</td>
													<td width="100">Configure</td>
													<td width="100">Edit</td>
													<td width="100">Delete</td>

												</tr>
											</thead>

											<tbody>

												<c:forEach items="${moduleJsonList}" var="moduleJsonList"
													varStatus="count">

													<c:set var="flag" value="0" />

													<c:forEach items="${moduleJsonList.subModuleJsonList}"
														var="subModuleJsonList">
														<c:choose>
															<c:when test="${subModuleJsonList.type==2}">
																<c:set var="flag" value="1" />
															</c:when>
														</c:choose>
													</c:forEach>


													<c:choose>
														<c:when test="${flag==1}">
															<tr>
																<!-- 	<td> &nbsp; </td>
											</tr><tr>  -->
																<c:set var="index" value="${index+1 }" />
																<td><c:out value="${index}" /></td>

																<td><b><c:out
																			value="${moduleJsonList.moduleName}" /></b></td>

															</tr>
														</c:when>
													</c:choose>
													<c:forEach items="${moduleJsonList.subModuleJsonList}"
														var="subModuleJsonList">
														<c:choose>
															<c:when test="${subModuleJsonList.type==2}">
																<tr>
																	<td></td>

																	<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<c:out
																			value="${subModuleJsonList.subModulName}" /></td>
																	<c:choose>
																		<c:when test="${subModuleJsonList.view=='visible'}">

																			<td><input type="checkbox"
																				name="${subModuleJsonList.subModuleId}${subModuleJsonList.moduleId}"
																				id="select_to_assign" value="view" checked></td>
																		</c:when>
																		<c:when test="${subModuleJsonList.view=='hidden'}">

																			<td><input type="checkbox"
																				name="${subModuleJsonList.subModuleId}${subModuleJsonList.moduleId}"
																				id="select_to_assign" value="view"></td>


																		</c:when>
																	</c:choose>

																	<c:choose>
																		<c:when
																			test="${subModuleJsonList.addApproveConfig=='visible'}">

																			<td><input type="checkbox"
																				name="${subModuleJsonList.subModuleId}${subModuleJsonList.moduleId}"
																				id="select_to_assign" value="add" checked></td>
																		</c:when>
																		<c:when
																			test="${subModuleJsonList.addApproveConfig=='hidden'}">

																			<td><input type="checkbox"
																				name="${subModuleJsonList.subModuleId}${subModuleJsonList.moduleId}"
																				id="select_to_assign" value="add"></td>


																		</c:when>
																	</c:choose>

																	<c:choose>
																		<c:when
																			test="${subModuleJsonList.editReject=='visible'}">

																			<td><input type="checkbox"
																				name="${subModuleJsonList.subModuleId}${subModuleJsonList.moduleId}"
																				id="select_to_assign" value="edit" checked></td>
																		</c:when>
																		<c:when
																			test="${subModuleJsonList.editReject=='hidden'}">
																			<td><input type="checkbox"
																				name="${subModuleJsonList.subModuleId}${subModuleJsonList.moduleId}"
																				id="select_to_assign" value="edit"></td>
																		</c:when>

																	</c:choose>
																	<c:choose>
																		<c:when
																			test="${subModuleJsonList.deleteRejectApprove=='visible'}">

																			<td><input type="checkbox"
																				name="${subModuleJsonList.subModuleId}${subModuleJsonList.moduleId}"
																				id="select_to_assign" value="delete" checked></td>
																		</c:when>
																		<c:when
																			test="${subModuleJsonList.deleteRejectApprove=='hidden'}">

																			<td><input type="checkbox"
																				name="${subModuleJsonList.subModuleId}${subModuleJsonList.moduleId}"
																				id="select_to_assign" value="delete"></td>


																		</c:when>
																	</c:choose>
																</tr>
															</c:when>
														</c:choose>


													</c:forEach>

												</c:forEach>
											</tbody>
										</table>

										<table class="table table-bordered table-striped fill-head "
											style="width: 70%" id="table_grid">
											<thead>
												<tr>
													<td width="100">Sr.No.</td>
													<td width="500">Modules</td>
													<td width="100">View</td>
													<td width="100">End Day Process</td>
													<!-- <td width="100">Reject</td>
										 <td width="100">Reject-Approve</td> -->

												</tr>
											</thead>

											<tbody>

												<c:forEach items="${moduleJsonList}" var="moduleJsonList"
													varStatus="count">

													<c:set var="flag" value="0" />

													<c:forEach items="${moduleJsonList.subModuleJsonList}"
														var="subModuleJsonList">
														<c:choose>
															<c:when test="${subModuleJsonList.type==3}">
																<c:set var="flag" value="1" />
															</c:when>
														</c:choose>
													</c:forEach>


													<c:choose>
														<c:when test="${flag==1}">
															<tr>
																<!-- 	<td> &nbsp; </td>
											</tr><tr>  -->
																<c:set var="index" value="${index+1 }" />
																<td><c:out value="${index}" /></td>

																<td><b><c:out
																			value="${moduleJsonList.moduleName}" /></b></td>

															</tr>
														</c:when>
													</c:choose>
													<c:forEach items="${moduleJsonList.subModuleJsonList}"
														var="subModuleJsonList">
														<c:choose>
															<c:when test="${subModuleJsonList.type==3}">
																<tr>
																	<td></td>

																	<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<c:out
																			value="${subModuleJsonList.subModulName}" /></td>
																	<c:choose>
																		<c:when test="${subModuleJsonList.view=='visible'}">

																			<td><input type="checkbox"
																				name="${subModuleJsonList.subModuleId}${subModuleJsonList.moduleId}"
																				id="select_to_assign" value="view" checked></td>
																		</c:when>
																		<c:when test="${subModuleJsonList.view=='hidden'}">

																			<td><input type="checkbox"
																				name="${subModuleJsonList.subModuleId}${subModuleJsonList.moduleId}"
																				id="select_to_assign" value="view"></td>


																		</c:when>
																	</c:choose>

																	<c:choose>
																		<c:when
																			test="${subModuleJsonList.addApproveConfig=='visible'}">

																			<td><input type="checkbox"
																				name="${subModuleJsonList.subModuleId}${subModuleJsonList.moduleId}"
																				id="select_to_assign" value="add" checked></td>
																		</c:when>
																		<c:when
																			test="${subModuleJsonList.addApproveConfig=='hidden'}">

																			<td><input type="checkbox"
																				name="${subModuleJsonList.subModuleId}${subModuleJsonList.moduleId}"
																				id="select_to_assign" value="add"></td>


																		</c:when>
																	</c:choose>

																	<%-- <c:choose>
														<c:when test="${subModuleJsonList.editReject==1}">

															<td><input type="checkbox" name="select_to_assign"
																id="select_to_assign"
																value="${subModuleJsonList.subModuleId}" 
																 ></td>
														</c:when>
														<c:when test="${subModuleJsonList.editReject==0}">
															<td></td>
														</c:when>

													</c:choose>
													<c:choose>
														<c:when test="${subModuleJsonList.deleteRejectApprove==1}">

															<td><input type="checkbox" name="select_to_assign"
																id="select_to_assign"
																value="${subModuleJsonList.subModuleId}" 
																 ></td>
														</c:when>
														<c:when test="${subModuleJsonList.deleteRejectApprove==0}">

															<td></td>


														</c:when>
													</c:choose> --%>
																</tr>
															</c:when>
														</c:choose>


													</c:forEach>

												</c:forEach>
											</tbody>
										</table>
									</div>
								</div>

								<div class="row">
									<div class="col-md-12" style="text-align: center">
										<input type="submit" class="btn btn-info" value="Submit">

									</div>
								</div>


							</form>
						</div>

					</div>
				</div>
			</div>

		</div>
	</div>
	<!-- Footer -->
	<jsp:include page="/WEB-INF/views/include/footer.jsp"></jsp:include>
	<!-- Footer -->




	<script
		src="${pageContext.request.contextPath}/resources/assets/js/vendor/jquery-2.1.4.min.js"></script>
	<script
		src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.3/umd/popper.min.js"></script>
	<script
		src="${pageContext.request.contextPath}/resources/assets/js/plugins.js"></script>
	<script
		src="${pageContext.request.contextPath}/resources/assets/js/main.js"></script>


	<script
		src="${pageContext.request.contextPath}/resources/assets/js/dashboard.js"></script>
	<script
		src="${pageContext.request.contextPath}/resources/assets/js/widgets.js"></script>
</body>
</html>