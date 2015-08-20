<?xml version="1.0" encoding="utf-8"?> 
<ROOT>
<%@ page language = "java" contentType="text/html; charset=UTF-8" %><%@ page import = "sips.common.lib.value.*" %><%@ page import = "sips.common.lib.util.*" %><%@ page import = "sips.dept.contents._Session" %><%
    ParamValue     parmValue = new ParamValue(); 
	_Session         _sesseion     = new _Session();
    ResultSetValue rset      = null;
    ResultSetValue rsetP      = null;
    
    String  conference_id     = request.getParameter("conference_id");

    String conference_date   = "";
    String agenda_title   = "";
    String title   = "";
    String writer   = "";
    String presenter   = "";
    String start_time   = "";
    String end_time   = "";
    String summary   = "";
    
    parmValue.put("conference_id", conference_id);
    rset = _sesseion.getBinderSearch(parmValue);

    while(rset.next()) {
    	out.println("		<SEARCH_ITEM>");
    	out.println("			<BINDER_ID>"+rset.getString("binder_id")+"</BINDER_ID>");
    	//out.println("			<USER_CD>"+rset.getString("user_cd")+"</USER_CD>");
    	out.println("			<USER_NAME>"+rset.getString("user_name")+"</USER_NAME>");
    	out.println("			<BINDER_TITLE>"+rset.getString("title")+"</BINDER_TITLE>");
    	//out.println("			<CONTENTS>"+rset.getString("contents")+"</CONTENTS>");
    	out.println("			<WRITER>"+rset.getString("writer")+"</WRITER>");
    	//out.println("			<ATTACHED>/mice/upload/pdf/"+rset.getString("attached")+"</ATTACHED>");
    	//out.println("			<SESSION_TITLE>"+rset.getString("session_title")+"</SESSION_TITLE>");
    	out.println("			<SEX>"+rset.getString("sex")+"</SEX>");
    	out.println("			<COMPANY>"+rset.getString("company")+"</COMPANY>");
    	out.println("			<NATION>"+rset.getString("nation")+"</NATION>");
    	out.println("		</SEARCH_ITEM>");
    	
    }
%>
</ROOT>