<?xml version="1.0" encoding="utf-8"?> 
<ROOT>
<%@ page language = "java" contentType="text/html; charset=UTF-8" %><%@ page import = "sips.common.lib.value.*" %><%@ page import = "sips.common.lib.util.*" %><%@ page import = "sips.dept.contents.Infomation" %><%
    ParamValue     parmValue = new ParamValue(); 
	Infomation         infomation    = new Infomation();
    ResultSetValue rset      = null;

    String  conference_id     = request.getParameter("conference_id");

    parmValue.put("conference_id", conference_id);
    rset = infomation.getInfomationList(parmValue); 

    while(rset.next()) {
    	out.println("		<INFOMAION_LIST>");
    	out.println("			<IMAGE>/mice/upload/infomation/"+rset.getString("info_image")+"</IMAGE>");
    	out.println("		</INFOMAION_LIST>");
    }
%>
</ROOT>