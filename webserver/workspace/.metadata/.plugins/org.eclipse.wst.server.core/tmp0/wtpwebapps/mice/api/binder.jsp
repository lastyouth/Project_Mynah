<?xml version="1.0" encoding="utf-8"?> 
<ROOT>
<%@ page language = "java" contentType="text/html; charset=UTF-8" %><%@ page import = "sips.common.lib.value.*" %><%@ page import = "sips.common.lib.util.*" %><%@ page import = "sips.dept.contents._Session" %><%
    ParamValue     parmValue = new ParamValue(); 
	_Session         _session     = new _Session();
    ResultSetValue rset      = null;
    ResultSetValue rsetP      = null;

    String  session_id     = request.getParameter("session_id");

    parmValue.put("session_id", session_id);
    rset = _session.getBinderList(parmValue);

    while(rset.next()) {
    	out.println("		<BINDER>");
    	out.println("			<BINDER_ID>"+rset.getString("binder_id")+"</BINDER_ID>");
    	out.println("			<SESSION_ID>"+rset.getString("session_id")+"</SESSION_ID>");
    	out.println("			<USER_CD>"+rset.getString("user_cd")+"</USER_CD>");
    	out.println("			<USER_NAME>"+rset.getString("user_name")+"</USER_NAME>");
    	out.println("			<BINDER_TITLE>"+rset.getString("title")+"</BINDER_TITLE>");
    	out.println("			<WRITER>"+rset.getString("writer")+"</WRITER>");
    	out.println("			<SESSION_TITLE>"+rset.getString("session_title")+"</SESSION_TITLE>");
    	out.println("		</BINDER>");
    }
%>
</ROOT>