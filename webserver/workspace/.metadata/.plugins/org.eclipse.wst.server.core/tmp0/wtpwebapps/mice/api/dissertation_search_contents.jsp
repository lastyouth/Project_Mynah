<?xml version="1.0" encoding="utf-8"?> 
<ROOT>
<%@ page language = "java" contentType="text/html; charset=UTF-8" %><%@ page import = "sips.common.lib.value.*" %><%@ page import = "sips.common.lib.util.*" %><%@ page import = "sips.dept.contents.Member" %><%
    ParamValue     parmValue = new ParamValue(); 
	Member         member    = new Member();
    ResultSetValue rset      = null;

    //String  binder_id     = request.getParameter("binder_id");
    String  search_flag     = request.getParameter("search_flag");
    String  serial     = request.getParameter("serial");

    //parmValue.put("binder_id", binder_id);
    parmValue.put("search_flag", search_flag);
    parmValue.put("serial", serial);
    rset = member.getApiDissertationSearchContents(parmValue); 
    while(rset.next()) {
    	out.println("		<DISSERTATION_SEARCH>");
    	out.println("			<WRITER>"+rset.getString("writer")+"</WRITER>");
    	out.println("			<ATTACHED>/mice/upload/pdf/"+rset.getString("attached")+"</ATTACHED>");
    	out.println("			<BINDER_TITLE>"+rset.getString("binder_title")+"</BINDER_TITLE>");
		out.println("			<TOPIC_TITLE>"+rset.getString("topic_title")+"</TOPIC_TITLE>");
    	out.println("			<CONTETNS>"+rset.getString("binder_contents")+"</CONTETNS>");
    	if( "agenda".equals(search_flag)){
    		out.println("			<PRESENTER>"+rset.getString("presenter")+"</PRESENTER>");
    		out.println("			<CONFERENCE_DATE>"+rset.getString("conference_date")+"</CONFERENCE_DATE>");
    		out.println("			<START_TIME>"+rset.getString("start_time")+"</START_TIME>");
    		out.println("			<END_TIME>"+rset.getString("end_time")+"</END_TIME>");
    	}
    	out.println("		</DISSERTATION_SEARCH>");
    }
%>
</ROOT>