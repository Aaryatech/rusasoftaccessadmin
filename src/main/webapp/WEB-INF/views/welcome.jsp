<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%><%@ taglib
	uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>


<!DOCTYPE html>
<html class=" ">
<head>
<c:url var="getDashboardGraph" value="/getDashboardGraph"></c:url>
 
<jsp:include page="/WEB-INF/views/include/header.jsp"></jsp:include>
<!-- CORE CSS TEMPLATE - END -->

</head>
<!-- END HEAD -->

<!-- BEGIN BODY -->
<body class="" onload="">
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
			<section class="wrapper main-wrapper row" style=''>

				<div class='col-xs-12'>
					<div class="page-title">

						<div class="content-wrapper">
							<!-- Content Header (Page header) -->
							<section class="content-header">
								<h1>Dashboard</h1>

								<!-- <div class="top_slt_bx">
									<div class="slt_one">
										<div class="select-style">
											<select>
												<option value="All">Academic Year</option>
												<option value="All">2015</option>
												<option value="All">2016</option>
												<option value="All">2017</option>
											</select>
										</div>
									</div>
									<div class="slt_one">
										<div class="select-style">
											<select>
												<option value="All">Financial Year</option>
												<option value="All">2015</option>
												<option value="All">2016</option>
												<option value="All">2017</option>
											</select>
										</div>
									</div>
								</div> -->


								<!-- <ol class="breadcrumb">
        <li><a href="#"><i class="fa fa-dashboard"></i> Home</a></li>
        <li class="active">Dashboard</li>
      </ol>-->
							</section>

							<!-- Main content -->
							<section class="content">
								<div class="row">
									<div class="dashboard_list">



										<div class="col-md-3">
											<div class="dash_one">
												<h2 class="desig_nm">Total Faculties</h2>
												<span class="count">${dashBoardCounts.count1}</span>
												<!--<a href="#" class="dash_button">Button</a>-->
												<!-- 						<p class="dash_note"><span>Note :</span> Some Note Write Here</p>
 -->
											</div>
										</div>


									</div>
								</div>

								<div class="row">
									 
										<div class="col-md-6">

											<div class="box box-primary">
												<div class="box-header with-border">
													<h3 class="box-title">NAAC Accredited Institute</h3>

												</div>
												<div class="box-body chart-responsive">
													<div class="chart" id="graph1" style="height: 300px;"></div>
												</div>

											</div>

										</div>

										<div class="col-md-6">

											<div class="box box-primary">
												<div class="box-header with-border">
													<h3 class="box-title">NBA Applicable Institutes</h3>

												</div>
												<div class="box-body chart-responsive">
													<div class="chart" id="graph2"
														style="height: 300px;"></div>
												</div>

											</div>

										</div>
										
										<div class="col-md-6">

											<div class="box box-primary">
												<div class="box-header with-border">
													<h3 class="box-title">NIRF Participated Institutes</h3>

												</div>
												<div class="box-body chart-responsive">
													<div class="chart" id="graph3"
														style="height: 300px;"></div>
												</div>

											</div>

										</div>
										
										<div class="col-md-6">

											<div class="box box-primary">
												<div class="box-header with-border">
													<h3 class="box-title">THE Accredited Institutes</h3>

												</div>
												<div class="box-body chart-responsive">
													<div class="chart" id="graph4"
														style="height: 300px;"></div>
												</div>

											</div>

										</div>
										
										<div class="col-md-6">

											<div class="box box-primary">
												<div class="box-header with-border">
													<h3 class="box-title">Institutes with Autonomous status</h3>

												</div>
												<div class="box-body chart-responsive">
													<div class="chart" id="graph5"
														style="height: 300px;"></div>
												</div>

											</div>

										</div>

 

								</div>
								<!-- <div class="row">

									left boxes
									<div class="col-md-6">

										<div class="box box-primary">
											<div class="box-header with-border">
												<h3 class="box-title">Sanctioned Intake and No. of
													Students Admitted</h3>

											</div>
											<div class="box-body chart-responsive">
												<div class="chart" id="intake_chart" style="height: 300px;"></div>
											</div>

										</div>



										<div class="box box-primary">
											<div class="box-header with-border">
												<h3 class="box-title">No of Programs</h3>

											</div>
											<div class="box-body chart-responsive">
												<div class="chart" id="sales-chart"
													style="height: 300px; position: relative;"></div>
											</div>

										</div>


									</div>
									end left boxes

									right boxes
									<div class="col-md-6">

										<div class="box box-primary">
											<div class="box-header with-border">
												<h3 class="box-title">Sanctioned Intake and No. of
													Students Admitted Program Wise</h3>

											</div>
											<div class="box-body chart-responsive">
												<div class="chart" id="line-chart" style="height: 300px;"></div>
											</div>

										</div>

										<div class="box box-primary">
											<div class="box-header with-border">
												<h3 class="box-title">Sanctioned Intake and No. of
													Students Admitted</h3>

											</div>
											<div class="box-body chart-responsive">
												<div class="chart" id="intake_chart1" style="height: 300px;"></div>
											</div>

										</div>

									</div>
									end right boxes
								</div> -->
								<!-- /.row -->

							</section>
							<!-- /.content -->
						</div>


					</div>
				</div>
				<div class="clearfix"></div>
				<!-- MAIN CONTENT AREA STARTS -->







				<!-- MAIN CONTENT AREA ENDS -->
			</section>
		</section>
		<!-- END CONTENT -->



	</div>
	<!-- END CONTAINER -->
	<!-- LOAD FILES AT PAGE END FOR FASTER LOADING -->

	<jsp:include page="/WEB-INF/views/include/footer.jsp"></jsp:include>

	<script type="text/javascript"
		src="https://www.gstatic.com/charts/loader.js"></script>
	<!-- Morris.js charts -->
	<script
		src="${pageContext.request.contextPath}/resources/dashb/raphael.min.js"
		type="text/javascript"></script>
	<script
		src="${pageContext.request.contextPath}/resources/dashb/morris.min.js"
		type="text/javascript"></script>
	<script>
		$(function() {
			"use strict";
  
			 
 
				$.getJSON('${getDashboardGraph}',

				{

					ajax : 'true'

				}, function(data) {

					google.charts.load('current', {
						'packages' : [ 'corechart' ]
					});
					google.charts.setOnLoadCallback(drawChart);

					function drawChart() {

						var dataTable = new google.visualization.DataTable();

						dataTable.addColumn('string', 'academic year'); // Implicit domain column.

						dataTable.addColumn('number', 'NAAC Accredited');
						dataTable.addColumn('number', 'Registred Institutes');

						$.each(data.naacRes, function(key, dt) {

							dataTable
									.addRows([

									[ dt.academicYear, dt.count2,
											dt.count1 ]

									]);

						})

						/* slantedTextAngle: 60 */
						var options = {
							hAxis : {
								title : "YEAR",
								textPosition : 'out',
								slantedText : true
							},
							vAxis : {
								title : 'VALUE',
								minValue : 0,
								viewWindow : {
									min : 0
								},
								format : '0',
							},
							colors : [ 'red', 'blue','green' ],
							theme : 'material'
						};
						var chart = new google.visualization.ColumnChart(
								document.getElementById('graph1'));

						chart.draw(dataTable, options);

						//2nd graph

						dataTable = new google.visualization.DataTable();

						dataTable.addColumn('string', 'academic year'); // Implicit domain column.

						dataTable.addColumn('number', 'NBA Accrediated');
						dataTable.addColumn('number', 'NBA Applicable');
						dataTable.addColumn('number', 'Registred Institute');

						$.each(data.nbaRes, function(key, dt) {

							dataTable
									.addRows([

										[ dt.academicYear, dt.count2,
											dt.count3 ,dt.count1 ]

									]);

						})

						chart = new google.visualization.ColumnChart(document
								.getElementById('graph2'));

						chart.draw(dataTable, options);  
						
						//3rd graph
						
						dataTable = new google.visualization.DataTable();

						dataTable.addColumn('string', 'academic year'); // Implicit domain column.

						dataTable.addColumn('number', 'NIRF Accrediated'); 
						dataTable.addColumn('number', 'Registred Institute');

						$.each(data.nirfcRes, function(key, dt) {

							dataTable
									.addRows([

										[ dt.academicYear, dt.count2 ,dt.count1 ]

									]);

						})

						chart = new google.visualization.ColumnChart(document
								.getElementById('graph3'));

						chart.draw(dataTable, options);  
						
						//4th graph
						
						dataTable = new google.visualization.DataTable();

						dataTable.addColumn('string', 'academic year'); // Implicit domain column.

						dataTable.addColumn('number', 'THE Accrediated'); 
						dataTable.addColumn('number', 'Registred Institute');

						$.each(data.theRes, function(key, dt) {

							dataTable
									.addRows([

										[ dt.academicYear, dt.count2 ,dt.count1 ]

									]);

						})

						chart = new google.visualization.ColumnChart(document
								.getElementById('graph4'));

						chart.draw(dataTable, options);  
						
						//5th graph
						
						dataTable = new google.visualization.DataTable();

						dataTable.addColumn('string', 'academic year'); // Implicit domain column.

						dataTable.addColumn('number', 'Autonomous Accrediated'); 
						dataTable.addColumn('number', 'Registred Institute');

						$.each(data.autonomousRes, function(key, dt) {

							dataTable
									.addRows([

										[ dt.academicYear, dt.count2 ,dt.count1 ]

									]);

						})

						chart = new google.visualization.ColumnChart(document
								.getElementById('graph5'));

						chart.draw(dataTable, options);
 
					}

				});
			 
		});
	</script>
</body>
</html>



