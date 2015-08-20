<%@ page language = "java" contentType="text/html; charset=UTF-8" %><%@ page import = "sips.common.lib.value.*" %><%@ page import = "sips.common.lib.util.*" %><%@ page import = "sips.dept.contents.Present" %><%
    ParamValue     parmValue = new ParamValue(); 
Present         present    = new Present();
    int result = 0;
    String flag = "false";

    String  user_cd     = StringUtil.defaultIfEmpty(request.getParameter("user_cd"      ), ""); 
    String  present_id     = StringUtil.defaultIfEmpty(request.getParameter("present_id"      ), ""); 
    String  security     = StringUtil.defaultIfEmpty(request.getParameter("security_cd"      ), ""); 

    parmValue.put("user_cd", user_cd);
    parmValue.put("present_id", present_id);
    parmValue.put("security", security);
    result = present.inserPresentXML(parmValue); 
    if (result == 1)
    	flag = "success";
 
%><?xml version="1.0" encoding="utf-8"?> 
<ROOT>
	<RESULT><%=flag%></RESULT>
</ROOT>