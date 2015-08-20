<%@ page language = "java" contentType="text/html; charset=UTF-8" %><%@ page import = "sips.common.lib.value.*" %><%@ page import = "sips.common.lib.util.*" %><%@ page import = "sips.dept.contents._Member" %><%
    ParamValue     parmValue = new ParamValue(); 
	_Member         member     = new _Member();
    ResultSetValue rset      = null;
    ResultSetValue rsetP      = null;

    String  adminId     = request.getParameter("id");
    String  adminPass   = request.getParameter("pw");
    String  app_key   = request.getParameter("app_key");

    String loginFlag   = "false";
    String user_cd = "0";
    
    parmValue.put("id", adminId);
    parmValue.put("pw", adminPass);
    parmValue.put("app_key", app_key);
    rset = member.getAdminMember(parmValue);

    if(rset.next()) {
    	loginFlag = "true";
    	user_cd = rset.getString("user_cd"   );
    }
%><?xml version="1.0" encoding="utf-8"?> 
<ROOT>
	<LOGIN>
  		<LOGINFLAG><%=loginFlag%></LOGINFLAG>
  		<USER_CD><%=user_cd%></USER_CD>
  	</LOGIN>
</ROOT>