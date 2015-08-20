<?xml version="1.0" encoding="utf-8"?> 
<ROOT>
<%@ page language = "java" contentType="text/html; charset=UTF-8" %><%@ page import = "sips.common.lib.value.*" %><%@ page import = "sips.common.lib.util.*" %><%@ page import = "sips.dept.contents._Message" %><%
    ParamValue     parmValue = new ParamValue(); 
	_Message         message    = new _Message();
    ResultSetValue rset      = null;
    ResultSetValue rset2      = null;
    ResultSetValue rset3      = null;


    String  conference_id     = request.getParameter("conference_id");
    String  promise_id   = request.getParameter("promise_id");


    parmValue.put("promise_id", promise_id);
    parmValue.put("conference_id", conference_id);
    rset = message.promiseDetail(parmValue);
 
    while(rset.next()) {
		    	out.println("		<PROMISE_ITEM>");
		    	out.println("			<PROMISE_ID>"+rset.getString("promise_id")+"</PROMISE_ID>");
		    	out.println("			<FROM_USER_NAME>"+rset.getString("from_user_name")+"</FROM_USER_NAME>");
		    	out.println("			<FROM_USER_CD>"+rset.getString("from_user_cd")+"</FROM_USER_CD>");
		    	rset2 = message.getDetailToUser(parmValue);
		    	while(rset2.next()) {
			    	if (rset.getString("promise_id").equals(rset2.getString("promise_id")) && !rset2.getString("to_user_cd").equals(rset.getString("from_user_cd"))){
				    	out.println("			<TO_USER>");
				    	out.println("				<TO_USER_NAME>"+rset2.getString("to_user_name")+"</TO_USER_NAME>");
				    	out.println("				<TO_USER_CD>"+rset2.getString("to_user_cd")+"</TO_USER_CD>");
				    	out.println("			</TO_USER>");
			    	}
		    	}
		    	out.println("			<PROMISE_DATE>"+rset.getString("promise_date")+"</PROMISE_DATE>");
		    	out.println("			<PROMISE_HOUR>"+rset.getString("promise_hour")+"</PROMISE_HOUR>");
		    	out.println("			<TITLE>"+rset.getString("title")+"</TITLE>");
		    	out.println("			<CONTENTS>"+rset.getString("contents")+"</CONTENTS>");
		    	out.println("			<REG_DATE>"+rset.getString("reg_date")+"</REG_DATE>");
		    	out.println("		</PROMISE_ITEM>");
    }

    message.promiseReceipt(parmValue);//수신 확인
%>
</ROOT>