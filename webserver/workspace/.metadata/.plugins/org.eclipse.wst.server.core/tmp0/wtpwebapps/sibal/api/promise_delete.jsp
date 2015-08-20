<?xml version="1.0" encoding="utf-8"?> 
<ROOT>
<%@ page language = "java" contentType="text/html; charset=UTF-8" %><%@ page import = "sips.common.lib.value.*" %><%@ page import = "sips.common.lib.util.*" %><%@ page import = "sips.dept.contents._Message" %><%
    ParamValue     parmValue = new ParamValue(); 
	_Message         message     = new _Message();
    ResultSetValue rset      = null;
    ResultSetValue rsetP      = null;
    
    String rlt = "false";
    int totalCnt = 1;

    String  promise_id   = request.getParameter("promise_id");
    String  user_cd   = request.getParameter("user_cd");


    parmValue.put("promise_id", promise_id);
    parmValue.put("user_cd", user_cd);
    
    rlt = message.promiseDelete(parmValue);
    
    out.println("		<PROMISE_DELETE>");
	out.println("			<FLAG>"+rlt+"</FLAG>");
	out.println("		</PROMISE_DELETE>");
%>
</ROOT>