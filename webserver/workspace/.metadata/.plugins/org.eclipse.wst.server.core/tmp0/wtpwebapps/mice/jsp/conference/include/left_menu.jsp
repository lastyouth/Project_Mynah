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
					<span id="menu_1" class="on"><a href="javascript:leftMenuHandler('menu_1');"><b>컨퍼런스</b></a></span>
						<!--소메뉴 -->
						<ul id="sub_menu_1" class="2smenu" style="display:none;">
							<li id="job_1" class="soff"><a href="javascript:leftMenuSelection(1,'<%=rootPath%>/jsp/conference/binder/binder.jsp?b_mode=W');">초록 관리</a></li> 
							<li id="job_2" class="soff"><a href="javascript:leftMenuSelection(2,'<%=rootPath%>/jsp/conference/agenda/agenda.jsp');">아젠다</a></li>
							<li id="job_3" class="soff"><a href="javascript:leftMenuSelection(3,'<%=rootPath%>/jsp/conference/binder/binder.jsp');">초록조회</a></li> 
							<li id="job_4" class="soff"><a href="javascript:leftMenuSelection(4,'<%=rootPath%>/jsp/conference/member/member.jsp');">참가자조회</a></li> 
							<li id="job_5" class="soff"><a href="javascript:leftMenuSelection(5,'<%=rootPath%>/jsp/conference/map/map.jsp');">행사장조회</a></li> 
							<li id="job_6" class="soff"><a href="javascript:leftMenuSelection(6,'<%=rootPath%>/jsp/conference/memo/memo.jsp');">메모관리</a></li> 
							<li id="job_7" class="soff"><a href="javascript:leftMenuSelection(7,'<%=rootPath%>/jsp/conference/schedule/schedule.jsp');">스케줄관리</a></li> 
							<li id="job_8" class="soff"><a href="javascript:leftMenuSelection(8,'<%=rootPath%>/jsp/conference/receive/receive.jsp');">메세지 수신함</a></li>
							<li id="job_9" class="soff"><a href="javascript:leftMenuSelection(9,'<%=rootPath%>/jsp/conference/dispatch/dispatch.jsp');">메세지 발신함</a></li>
							<li id="job_10" class="soff"><a href="javascript:leftMenuSelection(10,'<%=rootPath%>/jsp/conference/help/help.jsp');">고객문의</a></li>
						</ul>                        
				</div>
 
			</div>                    
			<div class="bottom"></div>                    
	</div>
	<!--//메뉴 -->
</div>