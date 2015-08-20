<?xml version="1.0" encoding="utf-8"?> 
<ROOT>
<%@ page language = "java" contentType="text/html; charset=UTF-8" %><%@ page import = "sips.common.lib.value.*" %><%@ page import = "sips.common.lib.util.*" %><%@ page import = "sips.dept.contents.Member" %><%
    ParamValue     parmValue = new ParamValue(); 
	Member         member    = new Member();
    ResultSetValue rset      = null;

    String  conference_id     = request.getParameter("conference_id");
    String  user_cd     = request.getParameter("user_cd");
    String  session_id     = request.getParameter("session_id");

    parmValue.put("conference_id", conference_id);
    parmValue.put("user_cd", user_cd);
    parmValue.put("session_id", session_id);
    rset = member.getApiMemberSearchContents(parmValue); 
    while(rset.next()) {
    	out.println("		<MEMBER_SEARCH_CONTENTS>");
    	out.println("			<USER_ID>"+rset.getString("id")+"</USER_ID>");
    	out.println("			<USER_NAME>"+rset.getString("name")+"</USER_NAME>");
    	out.println("			<COMPANY>"+rset.getString("company")+"</COMPANY>");
    	out.println("			<SEX>"+rset.getString("sex")+"</SEX>");
    	out.println("			<USER_TITLE>"+rset.getString("appellation_name")+"</USER_TITLE>");
    	out.println("			<EMAIL>"+rset.getString("email")+"</EMAIL>");
    	out.println("			<PHONE>"+rset.getString("phone")+"</PHONE>");
    	out.println("			<ADDRESS>"+rset.getString("address")+"</ADDRESS>");
    	if (!"".equals(rset.getString("session_id"))){
        	out.println("		<BINDER>");
        	out.println("			<BINDER_TITLE>"+rset.getString("binder_title")+"</BINDER_TITLE>");
        	out.println("			<CONTENTS>"+rset.getString("binder_contents")+"</CONTENTS>");
        	out.println("			<WRITER>"+rset.getString("binder_writer")+"</WRITER>");
        	out.println("			<ATTACHED>/mice/upload/pdf/"+rset.getString("attached")+"</ATTACHED>");
        	out.println("		</BINDER>");
    	}
    	out.println("		</MEMBER_SEARCH_CONTENTS>");
    }
%>
</ROOT>