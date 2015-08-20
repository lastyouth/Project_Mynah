<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<% request.setCharacterEncoding("UTF-8"); %>
<% 
	String root_path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>::MICE::</title>
<link rel="stylesheet" type="text/css" href="<%=root_path%>/css/Layout.css"/>
<script language="JavaScript">
<!--
	function MM_preloadImages() { //v3.0
	  var d=document; if(d.images){ if(!d.MM_p) d.MM_p=new Array();
	    var i,j=d.MM_p.length,a=MM_preloadImages.arguments; for(i=0; i<a.length; i++)
	    if (a[i].indexOf("#")!=0){ d.MM_p[j]=new Image; d.MM_p[j++].src=a[i];}}
	}
//-->
</script>

<script language="javascript">
<!--
    function goLogin() {
        
        if( document.login.adminId.value == "" ) {
        	alert("아이디를 입력하세요.");
            document.login.adminId.focus();
            return;
        }
        
        if( document.login.adminPass.value == "" ) {
        	alert("비밀번호를 입력하세요.");
            document.login.adminPass.focus();
            return;
        } 
    
        document.login.action = "login_proc.jsp";
        document.login.submit();
    }
//-->    
</script>  
</head> 

<body>
<form name="login" method="post">
<!--전체 -->
<div id="main-wrapper1">
    <div id="main-wrapper">
	<!--login -->
    <dl class="loginp">
    	<!-- dt>
    	  <div align="center"><img src="<%=root_path%>/images/login_ci.gif" alt="동훈에스피(유)" style="margin-bottom:40px;"></div>
    	</dt -->
    	<dd style="margin-left:85px;">아이디 <input name="adminId" type="text" class="Ttable" id="adminId" style="margin-left:22px;"  tabindex=1></dd>
      <dd style="margin-left:85px;">비밀번호 <input name="adminPass" type="password" class="Ttable" id="adminPass" style="margin-left:10px;"  onKeypress="if (event.keyCode==13) goLogin();"  tabindex=2></dd>
      <dd class="bt11"><A HREF="javascript:goLogin();"><img src="<%=root_path%>/images/bt_login.gif" alt="로그인" style="position:relative;top:-54px; left:315px;"></a></dd>              
    </dl>
	<!--//login -->    
    </div>
</div>
<!--//전체 -->

</form>
</body>
</html>