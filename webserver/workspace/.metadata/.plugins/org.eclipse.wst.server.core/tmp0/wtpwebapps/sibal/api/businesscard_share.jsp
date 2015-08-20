<%@ page language = "java" contentType="text/html; charset=UTF-8" %><%@ page import = "sips.common.lib.value.*" %><%@ page import = "sips.common.lib.util.*" %><%@ page import = "sips.dept.contents._Member" %><%
    ParamValue     parmValue = new ParamValue(); 
	_Member         member     = new _Member();
    ResultSetValue rset      = null;
    ResultSetValue rsetP      = null;

    String  user_cd     = request.getParameter("user_cd");
    String  share   = request.getParameter("share");
    String  random   = request.getParameter("random");

    String flag   = "false";
    int rlt = 0;
    
    parmValue.put("user_cd", user_cd);
    parmValue.put("share", share);
    parmValue.put("random", random);
    rlt = member.updateBusinesscardShare(parmValue);
 
    if(rlt==1) {
    	flag = "true";
    }
%><?xml version="1.0" encoding="utf-8"?> 
<ROOT>
	<BUSINESSCARD_SHARE>
  		<FLAG><%=flag%></FLAG>
  	</BUSINESSCARD_SHARE>
</ROOT>