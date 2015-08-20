<?xml version="1.0" encoding="utf-8"?> 
<ROOT>
<%@ page language = "java" contentType="text/html; charset=UTF-8" %><%@ page import = "sips.common.lib.value.*" %><%@ page import = "sips.common.lib.util.*" %><%@ page import = "sips.dept.contents.Present" %><%
    ParamValue     parmValue = new ParamValue(); 
Present         present    = new Present();
    ResultSetValue rset      = null;

    String  present_id     = request.getParameter("present_id");

    parmValue.put("present_id", present_id);
    rset = present.getPresentXMLContents(parmValue); 
    if(rset.next()) {
    	out.println("		<PRESENT_CONTENTS>");
    	out.println("			<PRESENT_ID>"+rset.getString("present_id")+"</PRESENT_ID>");
    	out.println("			<MANUFACTURE_NAME>"+rset.getString("manufacturer_name")+"</MANUFACTURE_NAME>");
    	out.println("			<PRESENT_NAME>"+rset.getString("present_name")+"</PRESENT_NAME>");
    	out.println("			<PRESENT_CONTENTS>"+rset.getString("contents")+"</PRESENT_CONTENTS>");
    	out.println("			<PRESENT_IMAGE>/mice/upload/present/"+rset.getString("present_image")+"</PRESENT_IMAGE>");
    	out.println("		</PRESENT_CONTENTS>");
    }
%>
</ROOT>