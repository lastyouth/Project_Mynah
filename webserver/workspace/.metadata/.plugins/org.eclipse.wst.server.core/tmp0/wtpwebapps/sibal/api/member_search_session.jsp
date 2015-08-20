<?xml version="1.0" encoding="utf-8"?> 
<ROOT>
<%@ page language = "java" contentType="text/html; charset=UTF-8" %><%@ page import = "sips.common.lib.value.*" %><%@ page import = "sips.common.lib.util.*" %><%@ page import = "sips.dept.contents._Session" %><%
    ParamValue     parmValue = new ParamValue(); 
	_Session         _session     = new _Session();
    ResultSetValue rset      = null;
    ResultSetValue rsetP      = null;

    String  conference_id     = request.getParameter("conference_id");

    String pre_date   = "";
    int totalCnt = 0;
    int cnt = 1;
    String con_date = "";
    
    parmValue.put("conference_id", conference_id);
    rset = _session.getApiSessionList(parmValue);
    
    out.println("		<SESSION>");
	out.println("			<SESSION_TITLE>전체</SESSION_TITLE>");
	out.println("			<SESSION_ID>0</SESSION_ID>");
	out.println("		</SESSION>");
    while(rset.next()) {
    	out.println("		<SESSION>");
    	out.println("			<SESSION_TITLE>"+rset.getString("title")+"</SESSION_TITLE>");
    	out.println("			<SESSION_ID>"+rset.getString("session_id")+"</SESSION_ID>");
    	out.println("		</SESSION>");
    }
    
%>
</ROOT>