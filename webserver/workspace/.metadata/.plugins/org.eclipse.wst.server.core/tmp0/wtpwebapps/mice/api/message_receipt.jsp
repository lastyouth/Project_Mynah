<?xml version="1.0" encoding="utf-8"?> 
<ROOT>
<%@ page language = "java" contentType="text/html; charset=UTF-8" %><%@ page import = "sips.common.lib.value.*" %><%@ page import = "sips.common.lib.util.*" %><%@ page import = "sips.dept.contents._Message" %><%
    ParamValue     parmValue = new ParamValue(); 
	_Message         message     = new _Message();
    ResultSetValue rset      = null;
    ResultSetValue rsetP      = null;
    
    String rlt = "false";
    int totalCnt = 1;

    String  conference_id     = request.getParameter("conference_id");
    String  message_id   = request.getParameter("message_id");


    parmValue.put("message_id", message_id);
    parmValue.put("conference_id", conference_id);
    
    rlt = message.messageReceipt(parmValue);
   
    out.println("		<MESSAGE_RECEIPT>");
	out.println("			<FLAG>"+rlt+"</FLAG>");
	out.println("		</MESSAGE_RECEIPT>");
%>
</ROOT>