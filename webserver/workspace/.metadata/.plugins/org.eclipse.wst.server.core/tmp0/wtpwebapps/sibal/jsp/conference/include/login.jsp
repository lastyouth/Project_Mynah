<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<% 
request.setCharacterEncoding("UTF-8"); 
String rootPath1 = request.getContextPath();
%>
<div class="login">
	<ul>
		<li class="top"></li>
		<!--내용 -->
		<li class="bg">
<%
	String _authority   = (String)session.getAttribute("USE_AUTHORITY");
	
	if (_authority  !=  null){
%>		
			<span style="margin-left:25px; margin-top:10px;"><%=(String)session.getAttribute("USER_NAME") %> 회원님 </span><br>
			<span style="margin-left:15px; margin-top:10px;"><A HREF="<%=rootPath1%>/jsp/conference/member/member.jsp?b_mode=W">회원정보수정</a> 
			<a href="<%=rootPath1%>/jsp/conference/logout_proc.jsp">로그아웃</a></span>
<%
	}else{
%>		
		<form name="login" method="post">
			<dd style="margin-left:5px;"><input name="adminId" type="text" class="Ttable" id="adminId" style="margin-left:2px;"  tabindex=1></dd>
		    <dd style="margin-left:5px;"><input name="adminPass" type="password" class="Ttable" id="adminPass" style="margin-left:2px;"  onKeypress="if (event.keyCode==13) goLogin();"  tabindex=2></dd>
		    <dd style="margin-left:5px;"><A HREF="<%=rootPath1%>/jsp/conference/register/register.jsp">회원가입</a> <A HREF="javascript:goLogin();">비밀번호찾기</a></dd>
		    <dd class="bt11"><A HREF="javascript:goLogin();"><img src="<%=rootPath1%>/images/bt_login.gif" alt="로그인" style="position:relative;top:-63px; left:115px; width:60px; height:45px;" OnClick="javascript:goLogin();"></a></dd>
		</form>
<%
	}
%>
		</li>
		<!--//내용 -->
		<li class="bottom"></li>
	</ul>
</div>