<?xml version="1.0" encoding="utf-8"?> 
<ROOT>
<%@ page language = "java" contentType="text/html; charset=UTF-8" %><%@ page import = "sips.common.lib.value.*" %><%@ page import = "sips.common.lib.util.*" %><%@ page import = "sips.dept.contents._Sponsor" %><%
    ParamValue     parmValue = new ParamValue(); 
	_Sponsor         sponsor     = new _Sponsor();
    ResultSetValue rset      = null;
    ResultSetValue rsetP      = null;

    String  conference_id     = request.getParameter("conference_id");
    
    parmValue.put("conference_id", conference_id);
    rset = sponsor.getSponsorList(parmValue);

    while(rset.next()) {
    	out.println("		<SPONSOR>");
    	out.println("			<BANNER_IMAGE>/mice/upload/sponsor/"+rset.getString("logo")+"</BANNER_IMAGE>");
    	out.println("			<DETAIL_IMAGE>/mice/upload/sponsor/"+rset.getString("detail_image")+"</DETAIL_IMAGE>");
    	out.println("		</SPONSOR>");
    }
%>
</ROOT>