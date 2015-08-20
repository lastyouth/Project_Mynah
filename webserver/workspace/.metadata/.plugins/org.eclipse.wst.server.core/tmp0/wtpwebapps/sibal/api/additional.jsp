<?xml version="1.0" encoding="utf-8"?> 
<ROOT>
<%@ page language = "java" contentType="text/html; charset=UTF-8" %><%@ page import = "sips.common.lib.value.*" %><%@ page import = "sips.common.lib.util.*" %><%@ page import = "sips.dept.contents._Additional" %><%
    ParamValue     parmValue = new ParamValue(); 
	_Additional         additional    = new _Additional();
    ResultSetValue rset      = null;

    String  conference_id     = request.getParameter("conference_id");

    parmValue.put("conference_id", conference_id);
    rset = additional.getApAdditional(parmValue); 

    while(rset.next()) {
    	out.println("		<ADDITIONAL_ITEM>");
    	out.println("			<FLAG>"+rset.getString("gubun")+"</FLAG>");
    	out.println("			<LATITUDE>"+rset.getString("latitude")+"</LATITUDE>");
    	out.println("			<LONGITUDE>"+rset.getString("longitude")+"</LONGITUDE>");
    	out.println("			<MAIN_TITLE>"+rset.getString("main_title")+"</MAIN_TITLE>");
    	out.println("			<PHONE>"+rset.getString("primary_phone")+"</PHONE>");
    	out.println("			<ADDRESS>"+rset.getString("address")+"</ADDRESS>");
    	out.println("			<CONTENTS>"+rset.getString("contents")+"</CONTENTS>");
    	out.println("		</ADDITIONAL_ITEM>");
    }
%>
</ROOT>