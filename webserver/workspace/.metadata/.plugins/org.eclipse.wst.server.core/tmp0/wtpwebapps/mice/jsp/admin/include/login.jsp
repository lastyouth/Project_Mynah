<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<% 
request.setCharacterEncoding("UTF-8");
String rootPath2 = request.getContextPath();
%>
<div class="login">
	<ul>
		<li class="top"></li>
		<!--내용 -->
		<li class="bg">
			<span class="pBlue"> <%=(String)session.getAttribute("USER_NAME") %> 관리자님 </span><br>
			<img src="<%=rootPath2%>/images/bt_email.gif" alt="이메일" style="margin-right:5px; margin-top:10px;">
			<a href="<%=rootPath2%>/jsp/admin/logout_proc.jsp"><img src="<%=rootPath2%>/images/bt_logout.gif" alt="로그아웃" style="margin-right:5px; margin-top:10px;"></a>
		</li>
		<!--//내용 -->
		<li class="bottom"></li>
	</ul>
</div>