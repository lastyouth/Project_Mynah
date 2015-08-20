<?xml version="1.0" encoding="utf-8"?> 
<ROOT>
<%@ page language = "java" contentType="text/html; charset=UTF-8" %><%@ page import = "sips.common.lib.value.*" %><%@ page import = "sips.common.lib.util.*" %><%@ page import = "sips.dept.contents._Session" %><%
    ParamValue     parmValue = new ParamValue(); 
	_Session         _session     = new _Session();
    ResultSetValue rset      = null;
    ResultSetValue rsetP      = null;

    String  agenda_id     = request.getParameter("agenda_id");

    String pre_date   = "";

    parmValue.put("agenda_id", agenda_id);
    rset = _session.getAgendaContetns(parmValue);

    while(rset.next()) {
    	if (!pre_date.equals(rset.getString("conference_date"))){
    		out.println("		<DATE>"+rset.getString("conference_date")+"</DATE>");
    	}
    	out.println("		<AGENDA_CONTENTS>");
    	out.println("			<AGENDA_ID>"+rset.getString("agenda_id")+"</AGENDA_ID>");
    	out.println("			<USER_CD>"+rset.getString("user_cd")+"</USER_CD>");
    	out.println("			<SESSION_ID>"+rset.getString("session_id")+"</SESSION_ID>");
    	out.println("			<USER_NAME>"+rset.getString("user_name")+"</USER_NAME>");
    	out.println("			<START_TIME>"+rset.getString("start_time")+"</START_TIME>");
    	out.println("			<END_TIME>"+rset.getString("end_time")+"</END_TIME>");
    	out.println("			<AGENDA_TITLE>"+rset.getString("title")+"</AGENDA_TITLE>");
    	out.println("			<CONTENTS>"+rset.getString("contents")+"</CONTENTS>");
    	out.println("			<SUMMARY>"+rset.getString("summary")+"</SUMMARY>");
    	out.println("			<WRITER>"+rset.getString("writer")+"</WRITER>");
    	out.println("			<PRESENTER>"+rset.getString("presenter")+"</PRESENTER>");
    	out.println("			<ATTACHED>/mice/upload/pdf/"+rset.getString("attached")+"</ATTACHED>");
    	out.println("		</AGENDA_CONTENTS>");
    	
    	pre_date = rset.getString("conference_date");
    }
%>
</ROOT>