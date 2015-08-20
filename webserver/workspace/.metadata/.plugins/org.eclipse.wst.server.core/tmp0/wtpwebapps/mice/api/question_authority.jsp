<%@ page language = "java" contentType="text/html; charset=UTF-8" %><%@ page import = "java.util.*"  %><%@ page import = "sips.common.lib.value.*" %><%@ page import = "sips.common.lib.util.*" %><%@ page import = "sips.dept.contents._Member" %><%@ page import = "org.apache.commons.fileupload.servlet.*" %><%@ page import = "org.apache.commons.fileupload.disk.*" %><%@ page import = "org.apache.commons.fileupload.*"%><%@ page import = "java.io.*"    %><%
    ParamValue     parmValue = new ParamValue(); 
	_Member         member     = new _Member();

    int rtl =  0;
    String flag = "false";

    String  user_cd     = StringUtil.defaultIfEmpty(request.getParameter("user_cd"      ), ""); 

    parmValue.put("user_cd"        , user_cd        );    
    rtl = member.memberAuthorityModify(parmValue);
	if (rtl == 1)
		flag = "success";
 %><?xml version="1.0" encoding="utf-8"?> 
<ROOT>
	<RESULT><%=flag%></RESULT>
</ROOT>