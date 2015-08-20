<?xml version="1.0" encoding="utf-8"?> 
<ROOT>
<%@ page language = "java" contentType="text/html; charset=UTF-8" %><%@ page import = "sips.common.lib.value.*" %><%@ page import = "sips.common.lib.util.*" %><%@ page import = "sips.dept.contents.Nation" %><%
    ParamValue     parmValue = new ParamValue(); 
	Nation         nation    = new Nation();
    ResultSetValue rset      = null;

    String nation_id = "";
    String national = "";
    
    String  conference_id     = StringUtil.defaultIfEmpty(request.getParameter("conference_id"), ""); 

    rset = nation.getNationXMLList(parmValue);
    
    if ("".equals(conference_id)){
	    out.println("		<NATIONAL_ITEM>");
		out.println("			<NATIONAL_ID>0</NATIONAL_ID>");
		out.println("			<NATIONAL_NAME>전체국가</NATIONAL_NAME>");
		out.println("		</NATIONAL_ITEM>");
    }
    
    while(rset.next()) {
    	out.println("		<NATIONAL_ITEM>");
    	out.println("			<NATIONAL_ID>"+rset.getString("nation_id")+"</NATIONAL_ID>");
    	out.println("			<NATIONAL_NAME>"+rset.getString("nation")+"</NATIONAL_NAME>");
    	out.println("		</NATIONAL_ITEM>");
    	
    }
%>
</ROOT>