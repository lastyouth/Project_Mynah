<%-- ---------------------------------------------------------------------------
* File Type   : JSP
* File Name   : /08support/login_proc.jsp
* File Author : lsh
* File Desc   : 
* Create Date : 2006-12-12 09:00 ����
* History     :
--------------------------------------------------------------------------- --%>
<%@ page language = "java" contentType="text/html; charset=UTF-8" %>
<%@ page import = "java.util.*"     %>
<%@ page import = "sips.common.lib.value.*" %>
<%@ page import = "sips.common.lib.util.*" %>
<%
    
session.setAttribute("USER_ID"       , null);
session.setAttribute("USER_NAME"     , null);
session.setAttribute("USER_DEPARTMENT", null);
session.setAttribute("USE_CONTACT_NUMBER"  , null);
session.setAttribute("USE_TYPE"  , null);
session.setAttribute("USE_AUTHORITY"  , null);
%>
    <script language="javascript">
    alert("로그아웃 되었습니다.");
        top.location = "/mice/jsp/conference/";
    </script>
