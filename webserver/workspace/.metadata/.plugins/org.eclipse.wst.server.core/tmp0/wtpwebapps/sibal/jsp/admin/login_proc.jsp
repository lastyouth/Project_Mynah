<%-- ---------------------------------------------------------------------------
* File Type   : JSP
* File Name   : /08support/login_proc.jsp
* File Author : lsh
* File Desc   : 
* Create Date : 2006-12-12 09:00 ����
* History     :
--------------------------------------------------------------------------- --%>
<%@ page language = "java" contentType="text/html; charset=UTF-8" %>
<%@ page import = "sips.common.lib.value.*" %>
<%@ page import = "sips.common.lib.util.*" %>
<%@ page import = "sips.dept.contents.*" %>
<%
    ParamValue     parmValue = new ParamValue(); 
	Member            member     = new Member();
    ResultSetValue rset      = null;
    ResultSetValue rsetP      = null;

    String  adminId     = request.getParameter("adminId");
    String  adminPass   = request.getParameter("adminPass");
    String user_cd = "";
    String  id   = "";
    String passwd = "";
    String authority = "";
    String name = "";
    String conference_id = "";
    
    String  actionPage  = "";
    String  loginMsg    = "";
    
    boolean loginFlag   = false;
    
    rsetP = member.getPassword(adminPass);
    if(rsetP.next()) {
    	adminPass  = rsetP.getString("passwd");
      }
    
    parmValue.put("id", adminId);
    rset = member.getAdminMember(parmValue);
	System.out.println("adminId---------->"+adminId);

    if(rset.next()) {

    	user_cd = StringUtil.defaultIfEmpty(rset.getString("user_cd"   ),"");
    	id = StringUtil.defaultIfEmpty(rset.getString("id"   ),"");
    	passwd = StringUtil.defaultIfEmpty(rset.getString("passwd"   ),"");
    	name = StringUtil.defaultIfEmpty(rset.getString("name"   ),"");
    	authority = StringUtil.defaultIfEmpty(rset.getString("authority"   ),"");
    	conference_id = StringUtil.defaultIfEmpty(rset.getString("conference_id"   ),"");
    	System.out.println("adminPass---------->"+adminPass);
    	System.out.println("passwd---------->"+passwd);
        if( passwd.equals(adminPass)) {
            loginFlag = true;

            //set Session
            session.setAttribute("USER_CD"       , user_cd);
            session.setAttribute("USER_ID"       , id);
            session.setAttribute("USER_NAME"     , name);
            session.setAttribute("USE_AUTHORITY"  , authority);
            session.setAttribute("CONFERENCE_ID"  , conference_id);
        } else {
            loginFlag = false;
            loginMsg = "비밀번호가 일치하지 않습니다.";
        }
    }
    else {
    	loginMsg = "로그인 정보가 일치하지 않습니다!.";
    }

    
    if(loginFlag) { 
    	if (authority.matches("super")){
    		actionPage = "/mice/jsp/admin/conference/conference.jsp";
    	}else if(authority.matches("admin")) {
    		actionPage = "/mice/jsp/admin/conference_info/conference_info.jsp";
    	}
    	
%>
<html>
<head>
<script>
    function goAdminPage() {
        frm.action = "<%=actionPage%>";
        frm.target = "_top";
        frm.submit();
    }
</script>
</head>
<body onload="goAdminPage();">
<form name="frm" method="post">
</form>
</body>
</html>
<%  } else { %>
    <script language="javascript">
        alert("<%= loginMsg %>");
        location.href = "/mice/jsp/admin/";
    </script>
<%  } %>
