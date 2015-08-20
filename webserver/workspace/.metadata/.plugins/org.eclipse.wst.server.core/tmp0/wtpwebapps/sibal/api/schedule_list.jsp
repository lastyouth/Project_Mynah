<?xml version="1.0" encoding="utf-8"?> 
<ROOT>
<%@ page language = "java" contentType="text/html; charset=UTF-8" %><%@ page import = "sips.common.lib.value.*" %><%@ page import = "sips.common.lib.util.*" %><%@ page import = "sips.dept.contents._Schedule" %><%
    ParamValue     parmValue = new ParamValue(); 
	_Schedule         schedule    = new _Schedule();
    ResultSetValue rset      = null;

    String  conference_id     = request.getParameter("conference_id");
    String  user_cd     = request.getParameter("user_cd");

    parmValue.put("user_cd", user_cd);
    parmValue.put("conference_id", conference_id);
    rset = schedule.getScheduleList(parmValue); 
    
    String pre_date   = "";
    int totalCnt = 0;
    int ttlCnt = 1;
    int cnt = 1;
    String con_date = "";
    totalCnt = rset.row();
    
    while(rset.next()) {
    	con_date = rset.getString("conference_date");
    	if (!pre_date.equals(con_date)){
    		if (cnt != 1)
    			out.println("	</DAY>");
    		out.println("	<DAY>");
    		out.println("		<CONFERENCE_DATE>"+con_date+"</CONFERENCE_DATE>");
    		cnt = 1;
    	}
    	out.println("		<SCHEDUEL_LIST>");
    	out.println("			<SCHEDUEL_ID>"+rset.getString("schedule_id")+"</SCHEDUEL_ID>");
    	out.println("			<START_TIME>"+rset.getString("start_time")+"</START_TIME>");
    	out.println("			<END_TIME>"+rset.getString("end_time")+"</END_TIME>");
    	out.println("			<TITLE>"+rset.getString("title")+"</TITLE>");
    	out.println("			<REG_DATE>"+rset.getString("reg_date")+"</REG_DATE>");
    	out.println("		</SCHEDUEL_LIST>");
    	
    	if (ttlCnt == totalCnt)
    		out.println("	</DAY>");
    	pre_date = con_date;
    	cnt++;
    	ttlCnt++;
    }
%>
</ROOT>