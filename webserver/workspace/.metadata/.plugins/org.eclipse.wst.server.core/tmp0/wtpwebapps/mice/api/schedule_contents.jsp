<?xml version="1.0" encoding="utf-8"?> 
<ROOT>
<%@ page language = "java" contentType="text/html; charset=UTF-8" %><%@ page import = "sips.common.lib.value.*" %><%@ page import = "sips.common.lib.util.*" %><%@ page import = "sips.dept.contents._Schedule" %><%
    ParamValue     parmValue = new ParamValue(); 
_Schedule         schedule    = new _Schedule();
    ResultSetValue rset      = null;

    String  schedule_id     = request.getParameter("schedule_id");

    parmValue.put("schedule_id", schedule_id);
    rset = schedule.getScheduleContents(parmValue); 
    if(rset.next()) {
    	out.println("		<SCHEDULE_CONTENTS>");
    	out.println("			<SCHEDULE_ID>"+rset.getString("schedule_id")+"</SCHEDULE_ID>");
    	out.println("			<CONFERENCE_DATE>"+rset.getString("conference_date")+"</CONFERENCE_DATE>");
    	out.println("			<START_TIME>"+rset.getString("start_time")+"</START_TIME>");
    	out.println("			<END_TIME>"+rset.getString("end_time")+"</END_TIME>");
    	out.println("			<TITLE>"+rset.getString("title")+"</TITLE>");
    	out.println("			<CONTENTS>"+rset.getString("contents")+"</CONTENTS>");
    	out.println("			<REG_DATE>"+rset.getString("reg_date")+"</REG_DATE>");
    	out.println("		</SCHEDULE_CONTENTS>");
    }
%>
</ROOT>