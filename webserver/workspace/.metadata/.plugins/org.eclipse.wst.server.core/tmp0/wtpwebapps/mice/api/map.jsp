<?xml version="1.0" encoding="utf-8"?> 
<ROOT>
<%@ page language = "java" contentType="text/html; charset=UTF-8" %><%@ page import = "sips.common.lib.value.*" %><%@ page import = "sips.common.lib.util.*" %><%@ page import = "sips.dept.contents.Map" %><%
    ParamValue     parmValue = new ParamValue(); 
	Map         map    = new Map();
    ResultSetValue rset      = null;

    String  conference_id     = request.getParameter("conference_id");

    parmValue.put("conference_id", conference_id);
    rset = map.getMapList(parmValue); 

    while(rset.next()) {
    	out.println("		<MAP_LIST>");
    	out.println("			<TITLE>"+rset.getString("title")+"</TITLE>");
    	out.println("			<MAP>/mice/upload/map/"+rset.getString("map_image")+"</MAP>");
    	out.println("		</MAP_LIST>");
    }
%>
</ROOT>