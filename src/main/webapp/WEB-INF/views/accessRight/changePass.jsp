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


	<!-- BEGIN Content -->
	<div id="main-content">
		<!-- BEGIN Page Title -->
		<div class="page-title">
			<div>
				<h1>
					<i class="fa fa-file-o"></i>Change your Password
				</h1>

			</div>
		</div>
		<!-- END Page Title -->



		<!-- BEGIN Main Content -->
		<div class="row">
			<div class="col-md-12">
				<div class="box">
					<div class="box-title">
						<h3>
							<i class="fa fa-bars"></i>Password
						</h3>
						<div class="box-tool">
							<!-- <a href="">Back to List</a> <a data-action="collapse" href="#"><i
									class="fa fa-chevron-up"></i></a> -->
						</div>

					</div>

					<div class="box-content">
						<form action="" method="post" class="form-horizontal"
							id="validation-form" method="post">

							<div class="form-group">
								<label class="col-sm-3 col-lg-2 control-label"> User
									Name</label>
								<div class="col-sm-6 col-lg-4 controls">
									<input type="text" name="user_name" id="user_name"
										class="form-control" value="${uname}"
										data-rule-required="true" readonly />
								</div>

								<label class="col-sm-3 col-lg-2 control-label">Current
									Password </label>
								<div class="col-sm-6 col-lg-4 controls">
									<input type="password" name="cur_pass" id="cur_pass"
										class="form-control" placeholder="Existing Password"
										data-rule-required="true" />
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-3 col-lg-2 control-label">New
									Password </label>
								<div class="col-sm-6 col-lg-4 controls">
									<input type="password" name="new_pass1" id="new_pass1"
										class="form-control" placeholder="New  Password"
										data-rule-required="true" />
								</div>

								<label class="col-sm-3 col-lg-2 control-label">Confirm
									Password </label>
								<div class="col-sm-6 col-lg-4 controls">
									<input type="password" name="new_pass2" id="new_pass2"
										class="form-control" placeholder="Repeat  Password"
										data-rule-required="true" />
								</div>
							</div>

							<div class="row">
								<div class="col-md-12" style="text-align: center">
									<input type="button" onclick="validate()" class="btn btn-info"
										value="Submit">

								</div>
							</div>
						</form>
					</div>
				</div>
			</div>
		</div>
	</div>
	<!-- END Main Content -->
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





	<script type="text/javascript">
		function validate() {
			var valid = true;

			var curEntered = document.getElementById("cur_pass").value;
			var newPass1 = document.getElementById("new_pass1").value;
			var newPass2 = document.getElementById("new_pass2").value;
			var dbPass = $
			{
				curPass
			}
			;

			if (curEntered != dbPass) {
				alert("Current Password did not matched");
				valid = false;

			} else if (newPass1 == newPass2) {

			} else {
				alert("Please Enter Valid password")
				valid = false;

			}

			if (valid) {
				var form = document.getElementById("validation-form")
				form.action = "${pageContext.request.contextPath}/changeUserPass";
				form.submit();
			}

		}
	</script>
</body>
</html>

