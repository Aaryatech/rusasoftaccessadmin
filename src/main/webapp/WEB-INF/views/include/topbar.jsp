<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<c:url value="/setAcaYearInSession" var="setAcaYearInSession"></c:url>

<div class='page-topbar '>
	<div class='logo-area'></div>
	<div class='quick-area'>
	 
	<div class="pull-left">
            <ul class="info-menu left-links list-inline list-unstyled">
                <li class="sidebar-toggle-wrap"><a href="#" data-toggle="sidebar" class="sidebar_toggle"> <i class="fa fa-bars"></i>
                </a></li>

            </ul>
        </div>
        
		<div class='pull-left'>
			<ul class="info-menu right-links list-inline list-unstyled">
				<li class="profile"><a href="#" data-toggle="dropdown"
					class="toggle"> <span>Academic Year <span id="topYear">${sessionScope.acYearValue}</span> <i
							class="fa fa-calendar"></i>
					</span>
				</a>
					<ul class="dropdown-menu profile animated fadeIn">
 					
						<c:forEach items="${sessionScope.acaYearList}" var="acYear">
							<li><a href="#" id="${acYear.yearId}"  
								onclick="setAcaYearInSession(${acYear.yearId},'${acYear.academicYear}')"
								class="act-class">${acYear.academicYear}</a></li>
						</c:forEach>
					</ul></li>

			 </ul>
		</div>
		
		
		
		<%-- <div class='pull-left'>
			<ul class="info-menu right-links list-inline list-unstyled">
				<li class="profile"><a href="#" data-toggle="dropdown"
					class="toggle"> <span>Academic Year ${sessionScope.acYearId} <i
							class="fa fa-calendar"></i>
					</span>
				</a>
<!-- 					<ul class="dropdown-menu profile animated fadeIn">
 -->					<select id="acYear1" name="acYear1" class="form-control" onclick="setAcaYearInSession(${acYear.yearId},${acYear.academicYear})">
						<c:forEach items="${sessionScope.acaYearList}" var="acYear">
							<li><a href="#" id="${acYear.yearId}"
								onclick="setAcaYearInSession(${acYear.yearId},${acYear.academicYear})"
								class="act-class">${acYear.academicYear}</a></li>
								<option value="${acYear.yearId}">${acYear.academicYear}</option>
						
						</c:forEach></select>
					</ul><!-- </li> -->







			<!-- </ul> -->
		</div> --%>
		<!-- <div class='pull-left' id= "ac_year">
			<ul class="info-menu right-links list-inline list-unstyled">
				<li class="profile"><a href="#" data-toggle="dropdown"
					class="toggle">  <span>Academic Year
							<input type="text" id="sel_ac_year" name="sel_ac_year">  
					</span>
				</a>
					</li>

			</ul>
		</div> -->
		<div class='pull-right'>
			<ul class="info-menu right-links list-inline list-unstyled">
				<li class="profile"><a href="#" data-toggle="dropdown"
					class="toggle"> <img
						src="${pageContext.request.contextPath}/resources/assets/images/avatar.png"
						alt="user-image" class="img-circle img-inline"> <span>${sessionScope.userObj.staff.facultyFirstName}
							<i class="fa fa-angle-down"></i>
					</span>
				</a>
					<ul class="dropdown-menu profile animated fadeIn">

						<!-- <li><a href="#profile"> <i class="fa fa-user"></i>
								Profile
						</a></li> -->

						
				 	<li class="last"><a href="${pageContext.request.contextPath}/changeMobNoForm/${sessionScope.userObj.getStaff().getFacultyId()}"> <i
								class="fa fa-lock"></i> Change Mobile No.
						</a></li> 
						
							<li class="last"><a href="${pageContext.request.contextPath}/changeEmailIdForm/${sessionScope.userObj.getStaff().getFacultyId()}"> <i
								class="fa fa-lock"></i> Change Email
						</a></li> 
						
						<li class="last"><a href="${pageContext.request.contextPath}/logout"> <i
								class="fa fa-lock"></i> Logout
						</a></li>
					</ul></li>




			</ul>
		</div>
	</div>

</div>

<script>
function setAcaYearInSession(yearId,yearValue) {
	//alert("yearValue " +yearValue);
	$("#topYear").html(yearValue);
	//alert("Year Id  " +yearId);
	$.getJSON('${setAcaYearInSession}', {
		
		yearId : yearId,
		yearValue  : yearValue,

		ajax : 'true',

	}, function(data) {
		//alert("Data  " +JSON.stringify(data));
		//data.academicYear
		//$("#topYear").html(data.academicYear );
		
		location.reload(true);
		//$("#topYear").html(data.academicYear );
		$("#topYear").html(yearValue);
		 
	});
	
}

</script>