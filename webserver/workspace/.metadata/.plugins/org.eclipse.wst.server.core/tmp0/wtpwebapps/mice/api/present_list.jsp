<?xml version="1.0" encoding="utf-8"?> 
<ROOT>
<%@ page language = "java" contentType="text/html; charset=UTF-8" %><%@ page import = "sips.common.lib.value.*" %><%@ page import = "sips.common.lib.util.*" %><%@ page import = "sips.dept.contents.Present" %><%
    ParamValue     parmValue = new ParamValue(); 
Present         present    = new Present();
    ResultSetValue rset      = null;

    String  conference_id     = request.getParameter("conference_id");
    String  user_cd     = request.getParameter("user_cd");

    parmValue.put("user_cd", user_cd);
    parmValue.put("conference_id", conference_id);
    rset = present.getPresentXMLList(parmValue); 

    while(rset.next()) {
    	out.println("		<PRESENT_LIST>");
    	out.println("			<PRESENT_ID>"+rset.getString("present_id")+"</PRESENT_ID>");
    	out.println("			<USED_YN>"+rset.getString("used_yn")+"</USED_YN>");
    	out.println("			<MANUFACTURE_NAME>"+rset.getString("manufacturer_name")+"</MANUFACTURE_NAME>");
    	out.println("			<PRESENT_NAME>"+rset.getString("present_name")+"</PRESENT_NAME>");
    	out.println("			<PRESENT_IMAGE>/mice/upload/present/"+rset.getString("present_image")+"</PRESENT_IMAGE>");
    	out.println("		</PRESENT_LIST>");
    }
%>
</ROOT>