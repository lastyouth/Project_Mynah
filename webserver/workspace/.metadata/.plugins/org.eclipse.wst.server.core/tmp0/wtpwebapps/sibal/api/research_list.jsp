<?xml version="1.0" encoding="utf-8"?> 
<ROOT>
<%@ page language = "java" contentType="text/html; charset=UTF-8" %><%@ page import = "sips.common.lib.value.*" %><%@ page import = "sips.common.lib.util.*" %><%@ page import = "sips.dept.contents._Research" %><%
    ParamValue     parmValue = new ParamValue(); 
	_Research         research    = new _Research();
    ResultSetValue rset      = null;
    String research_authoriry = "";

    String  conference_id     = request.getParameter("conference_id");
    String  user_cd     = request.getParameter("user_cd");

    parmValue.put("conference_id", conference_id);
    parmValue.put("user_cd", user_cd);
    rset = research.getResearchList(parmValue); 
    //research_authoriry = research.getResearchAuthority(parmValue); 
    //out.println("		<RESEARCH_AUTHORITY>"+research_authoriry+"<RESEARCH_AUTHORITY>");
    while(rset.next()) {
    	out.println("		<RESEARCH_LIST>");
    	out.println("			<RESEARCH_ID>"+rset.getString("research_id")+"</RESEARCH_ID>");
    	out.println("			<USER_CD>"+rset.getString("user_cd")+"</USER_CD>");
    	out.println("			<USER_NAME>"+rset.getString("name")+"</USER_NAME>");
    	out.println("			<RESEARCH_NUM>"+rset.getString("research_num")+"</RESEARCH_NUM>");
    	out.println("			<TITLE>"+rset.getString("title")+"</TITLE>");
    	out.println("			<STAT>"+rset.getString("stat")+"</STAT>");
    	out.println("			<REG_DATE>"+rset.getString("reg_date")+"</REG_DATE>");
    	out.println("		</RESEARCH_LIST>");
    }
%>
</ROOT>