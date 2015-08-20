<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<% 
request.setCharacterEncoding("UTF-8"); 
String rootPath = request.getContextPath();
%>

<div id="left">
	<!--메뉴 -->       
	<div class="menu">
			<div class="top">Menu</div>
			<div class="bg">
			
				<!--대메뉴..비활성화일시엔 제목옆의 클래스가 off/활성화시엔on -->                    	
				<div class="smenu">
					<span id="menu_1" class="on"><a href="javascript:leftMenuHandler('menu_1');"><b>컨퍼런스 관리</b></a></span>
						<!--소메뉴 -->
						<ul id="sub_menu_1" class="2smenu" style="display:none;">
							<li id="job_1" class="soff"><a href="javascript:leftMenuSelection(1,'<%=rootPath%>/jsp/admin/member/member.jsp');">회원관리</a></li> 
							<li id="job_2" class="soff"><a href="javascript:leftMenuSelection(2,'<%=rootPath%>/jsp/admin/conference_info/conference_info.jsp');">컨퍼런스 정보 관리</a></li>
							<li id="job_3" class="soff"><a href="javascript:leftMenuSelection(3,'<%=rootPath%>/jsp/admin/session/session.jsp');">세션 관리</a></li> 
							<li id="job_4" class="soff"><a href="javascript:leftMenuSelection(4,'<%=rootPath%>/jsp/admin/topic/topic.jsp');">토픽 관리</a></li> 
							<li id="job_5" class="soff"><a href="javascript:leftMenuSelection(5,'<%=rootPath%>/jsp/admin/appellation/appellation.jsp');">호칭관리</a></li> 
							<li id="job_6" class="soff"><a href="javascript:leftMenuSelection(6,'<%=rootPath%>/jsp/admin/binder/binder.jsp');">바인더 관리</a></li> 
							<li id="job_7" class="soff"><a href="javascript:leftMenuSelection(7,'<%=rootPath%>/jsp/admin/agenda/agenda.jsp');">아젠다 관리</a></li> 
							<li id="job_8" class="soff"><a href="javascript:leftMenuSelection(8,'<%=rootPath%>/jsp/admin/banner/banner.jsp');">광고 관리</a></li>
							<li id="job_9" class="soff"><a href="javascript:leftMenuSelection(9,'<%=rootPath%>/jsp/admin/sponsor/sponsor.jsp');">스폰서 관리</a></li>
							<li id="job_10" class="soff"><a href="javascript:leftMenuSelection(10,'<%=rootPath%>/jsp/admin/additional/additional.jsp');">숙박/식당 관리</a></li>
							<li id="job_11" class="soff"><a href="javascript:leftMenuSelection(11,'<%=rootPath%>/jsp/admin/manufacturer/manufacturer.jsp');">선물업체 관리</a></li>
							<li id="job_12" class="soff"><a href="javascript:leftMenuSelection(12,'<%=rootPath%>/jsp/admin/present/present.jsp');">선물 관리</a></li>
							<li id="job_13" class="soff"><a href="javascript:leftMenuSelection(13,'<%=rootPath%>/jsp/admin/map/map.jsp');">행사장맵 관리</a></li>
							<li id="job_14" class="soff"><a href="javascript:leftMenuSelection(14,'<%=rootPath%>/jsp/admin/help/help.jsp');">고객문의 관리</a></li>
							<li id="job_15" class="soff"><a href="javascript:leftMenuSelection(15,'<%=rootPath%>/jsp/admin/qrcode/qrcode.jsp');">QR 코드 관리</a></li>
							<li id="job_16" class="soff"><a href="javascript:leftMenuSelection(16,'<%=rootPath%>/jsp/admin/research/research.jsp');">설문 관리</a></li>
						</ul>                        
				</div>
				<!--//대메뉴..비활성화일시엔 제목옆의 클래스가 off/활성화시엔on -->      

				<!--대메뉴..비활성화일시엔 제목옆의 클래스가 off/활성화시엔on -->                    	
				<div class="smenu">
					<span id="menu_2" class="on"><a href="javascript:leftMenuHandler('menu_2');"><b>관리자 메뉴</b></a></span>
						<!--소메뉴 -->
						<ul id="sub_menu_2" class="2smenu" style="display:none;">
							<li id="job_17" class="son"><a href="javascript:leftMenuSelection(17,'<%=rootPath%>/jsp/admin/conference/conference.jsp');">컨퍼런스 관리</a></li>
							<li id="job_18" class="soff"><a href="javascript:leftMenuSelection(18,'<%=rootPath%>/jsp/admin/nation/nation.jsp');">국가관리</a></li>
						</ul>
				</div>
				<!--//대메뉴..비활성화일시엔 제목옆의 클래스가 off/활성화시엔on --> 
 
			</div>                    
			<div class="bottom"></div>                    
	</div>
	<!--//메뉴 -->
</div>