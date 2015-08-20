<?xml version="1.0" encoding="utf-8"?> 
<ROOT>
<%@ page language = "java" contentType="text/html; charset=UTF-8" %><%@ page import = "sips.common.lib.value.*" %><%@ page import = "sips.common.lib.util.*" %><%@ page import = "sips.dept.contents._Message" %><%
    ParamValue     parmValue = new ParamValue(); 
	_Message         message     = new _Message();
    ResultSetValue rset      = null;
    
    String rlt = "false";
    int totalCnt = 1;

    String  conference_id     = request.getParameter("conference_id");
    String  message_id   = request.getParameter("message_id");


    parmValue.put("message_id", message_id);
    parmValue.put("conference_id", conference_id);
    
    rset = message.messageDetail(parmValue);

    while(rset.next()) {
    	out.println("		<MESSAGE_ITEM>");
    	out.println("			<MESSAGE_ID>"+rset.getString("message_id")+"</MESSAGE_ID>");
    	out.println("			<MESSAGE_TYPE>"+rset.getString("message_type")+"</MESSAGE_TYPE>");
    	out.println("			<TO_USER_NAME>"+rset.getString("to_user_name")+"</TO_USER_NAME>");
    	out.println("			<TO_USER_CD>"+rset.getString("to_user_cd")+"</TO_USER_CD>");
    	out.println("			<FROM_USER_NAME>"+rset.getString("from_user_name")+"</FROM_USER_NAME>");
    	out.println("			<FROM_USER_CD>"+rset.getString("from_user_cd")+"</FROM_USER_CD>");
    	out.println("			<TITLE>"+rset.getString("title")+"</TITLE>");
    	out.println("			<CONTENTS>"+rset.getString("contents")+"</CONTENTS>");
    	out.println("			<REPLY>"+rset.getString("reply")+"</REPLY>");
    	out.println("			<REG_DATE>"+rset.getString("reg_date")+"</REG_DATE>");
    	out.println("		</MESSAGE_ITEM>");
    	
    }

    rlt = message.messageReceipt(parmValue);//수신 확인
%>
</ROOT>