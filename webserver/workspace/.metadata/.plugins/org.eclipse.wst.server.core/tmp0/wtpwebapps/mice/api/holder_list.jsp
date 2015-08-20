<?xml version="1.0" encoding="utf-8"?> 
<ROOT>
<%@ page language = "java" contentType="text/html; charset=UTF-8" %><%@ page import = "sips.common.lib.value.*" %><%@ page import = "sips.common.lib.util.*" %><%@ page import = "sips.dept.contents._Member" %><%
    ParamValue     parmValue = new ParamValue(); 
	_Member         member    = new _Member();
    ResultSetValue rset      = null;

    String  conference_id     = request.getParameter("conference_id");
    String  user_cd     = request.getParameter("user_cd");
    String  name     = request.getParameter("name");

    parmValue.put("user_cd", user_cd);
    parmValue.put("name", name);
    parmValue.put("conference_id", conference_id);
    rset = member.getHolderList(parmValue); 

    while(rset.next()) {
    	out.println("		<HOLDER_LIST>");
    	out.println("			<USER_CD>"+rset.getString("user_cd")+"</USER_CD>");
    	out.println("			<USER_NAME>"+rset.getString("name")+"</USER_NAME>");
    	out.println("			<COMPANY>"+rset.getString("company")+"</COMPANY>");
    	out.println("			<APPELLATION_NAME>"+rset.getString("appellation")+"</APPELLATION_NAME>");
    	out.println("			<BUSINESSCARD_SHARE>"+rset.getString("businesscard_share")+"</BUSINESSCARD_SHARE>");
    	out.println("		</HOLDER_LIST>");
    }
%>
</ROOT>