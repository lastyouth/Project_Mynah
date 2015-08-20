<%@ page language = "java" contentType="text/html; charset=UTF-8" %><%@ page import = "sips.common.lib.value.*" %><%@ page import = "sips.common.lib.util.*" %><%@ page import = "sips.dept.contents.Member" %><%
    ParamValue     parmValue = new ParamValue(); 
	Member         member     = new Member();
    int rlt = 0;
    String result = "false";

    String  random     = request.getParameter("random");
    String  share_user     = request.getParameter("share_user");
    String  allow_user     = request.getParameter("allow_user");

    parmValue.put("random", random);
    parmValue.put("share_user", share_user);
    parmValue.put("allow_user", allow_user);
    rlt = member.getMemberInfo2(parmValue);
    if (rlt > 0)
    	result = "true";
%><?xml version="1.0" encoding="utf-8"?>
<ROOT>
	<BUSINESSCARD_SHARE>
  		<FLAG><%=result %></FLAG>
  	</BUSINESSCARD_SHARE>
</ROOT>