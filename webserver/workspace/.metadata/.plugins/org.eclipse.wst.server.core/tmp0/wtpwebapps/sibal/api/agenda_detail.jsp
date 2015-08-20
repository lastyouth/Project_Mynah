<?xml version="1.0" encoding="utf-8"?> 
<ROOT>
<%@ page language = "java" contentType="text/html; charset=UTF-8" %><%@ page import = "sips.common.lib.value.*" %><%@ page import = "sips.common.lib.util.*" %><%@ page import = "sips.dept.contents._Agenda" %><%
    ParamValue     parmValue = new ParamValue(); 
	_Agenda         agenda     = new _Agenda();
    ResultSetValue rset      = null;
    ResultSetValue rsetP      = null;

    String  agenda_id     = request.getParameter("agenda_id");
    String  gubun     = request.getParameter("gubun");

    String pre_date   = "";

    parmValue.put("agenda_id", agenda_id);
    parmValue.put("gubun", gubun);
    rset = agenda.getAgendaDetailList(parmValue);

    while(rset.next()) {
    	if (!pre_date.equals(rset.getString("conference_date"))){
    		out.println("		<DATE>"+rset.getString("conference_date")+"</DATE>");
    	}
    	out.println("		<SESSION_DETAIL>");
    	out.println("			<START_TIME>"+rset.getString("start_time")+"</START_TIME>");
    	out.println("			<END_TIME>"+rset.getString("end_time")+"</END_TIME>");
    	out.println("			<AGENDA_TITLE>"+rset.getString("agenda_title")+"</AGENDA_TITLE>");
    	out.println("			<WRITER>"+rset.getString("user_name")+"</WRITER>");
    	out.println("			<PRESENTER>"+rset.getString("presenter")+"</PRESENTER>");
    	out.println("			<AGENDA_DETAIL_TITLE>"+rset.getString("title")+"</AGENDA_DETAIL_TITLE>");
    	out.println("			<AGENDA_DETAIL_ID>"+rset.getString("agenda_detail_id")+"</AGENDA_DETAIL_ID>");
    	out.println("		</SESSION_DETAIL>");
    	
    	pre_date = rset.getString("conference_date");
    }
%>
</ROOT>