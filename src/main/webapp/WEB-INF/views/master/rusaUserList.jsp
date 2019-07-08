<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%><%@ taglib
	uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>


<!DOCTYPE html>
<html class=" ">
<head>

<jsp:include page="/WEB-INF/views/include/header.jsp"></jsp:include>
<!-- CORE CSS TEMPLATE - END -->
<c:url var="clearSessionAttribute" value="/clearSessionAttribute" />
</head>
<!-- END HEAD -->

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
		<!-- START CONTENT -->
		<section id="main-content" class=" ">
			<section class="wrapper main-wrapper row" style=''>

				<div class='col-xs-12'>
					<div class="page-title">

						<%-- <div class="pull-left">
							<!-- PAGE HEADING TAG - START -->
							<h1 class="title">${title}</h1>
							<!-- PAGE HEADING TAG - END -->
						</div>
 --%>

					</div>
				</div>
				<div class="clearfix"></div>
				<!-- MAIN CONTENT AREA STARTS -->



				<div class="col-lg-12">
					<section class="box ">
						<header class="panel_header">
							<h2 class="title pull-left">${title}</h2>
							<div class="actions panel_actions pull-right"></div>

						</header>
						<form
							action="${pageContext.request.contextPath}/deleteInstitutes/0"
							method="get" id="insListForm">
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


										<table id="example-1"
											class="table table-striped dt-responsive display">
											<thead>
												<tr>

													<th width="5%">Sr No</th>
													<th>First Name</th>
													<th>Last Name</th>
													<th>Designation</th>
													<th>Joining Date</th>
													<th width="10%">Action</th>
												</tr>
											</thead>

											<tbody>
												<c:set value="0" var="sr"></c:set>
												<c:forEach items="${list}" var="list" varStatus="count">
													<c:if test="${list.userId!=1}">
														<tr>
 
															<c:set value="${sr+1}" var="sr"></c:set>
															<td style="text-align: center;">${sr}</td>
															<td>${list.firstName}</td>

															<td>${list.lastName}</td>

															<td>${list.designation}</td>

															<td>${list.joiningDate}</td>


															<td align="center"><a
																href="${pageContext.request.contextPath}/editUser?user=${list.exVar2}"><span
																	class="glyphicon glyphicon-edit" title="Edit"
																	data-animate=" animated fadeIn " rel="tooltip"></span></a>
																<a
																href="${pageContext.request.contextPath}/deleteUser?user=${list.exVar2}"
																rel="tooltip" data-color-class="danger"
																onClick="return confirm('do you want to delete this record');"
																title="Delete" data-animate=" animated fadeIn "
																data-toggle="tooltip"
																data-original-title="Delete  record"><span
																	class="glyphicon glyphicon-remove"></span></a></td>


														</tr>
													</c:if>
												</c:forEach>
											</tbody>

										</table>

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
	<script>
		function clearSessionAttribute() {

			$.getJSON('${clearSessionAttribute}', {

				ajax : 'true',

			}, function(data) {

			});

		}

		/* function selectedInst(source) {

			checkboxes = document.getElementsByName('instIds');

			for (var i = 0, n = checkboxes.length; i < n; i++) {
				checkboxes[i].checked = source.checked;

			}

		} */
	</script>
	<script type="text/javascript">
		function showEditInstitute(instId) {
			//alert("instId " +instId);
			document.getElementById("edit_inst_id").value = instId;//create this 
			var form = document.getElementById("insListForm");
			form.setAttribute("method", "post");

			form.action = ("showEditInstitute");
			form.submit();

		}
	</script>
</body>
</html>
