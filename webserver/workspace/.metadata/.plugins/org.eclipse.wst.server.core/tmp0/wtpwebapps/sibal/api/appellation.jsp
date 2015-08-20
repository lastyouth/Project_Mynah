<?xml version="1.0" encoding="utf-8"?> 
<ROOT>
<%@ page language = "java" contentType="text/html; charset=UTF-8" %><%@ page import = "sips.common.lib.value.*" %><%@ page import = "sips.common.lib.util.*" %><%@ page import = "sips.dept.contents.Appellation" %><%
    ParamValue     parmValue = new ParamValue(); 
	Appellation         appellation    = new Appellation();
    ResultSetValue rset      = null;

    String  conference_id     = request.getParameter("conference_id");

    parmValue.put("conference_id", conference_id);
    rset = appellation.getAppellationSelectList(parmValue); 

    while(rset.next()) {
    	out.println("		<APPELLATION>");
    	out.println("			<APPELLATION_ID>"+rset.getString("appellation_id")+"</APPELLATION_ID>");
    	out.println("			<APPELLATION_NAME>"+rset.getString("appellation")+"</APPELLATION_NAME>");
    	out.println("		</APPELLATION>");
    }
%>
</ROOT>