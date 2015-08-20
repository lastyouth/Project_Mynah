<?xml version="1.0" encoding="utf-8"?> 
<ROOT>
<%@ page language = "java" contentType="text/html; charset=UTF-8" %><%@ page import = "sips.common.lib.value.*" %><%@ page import = "sips.common.lib.util.*" %><%@ page import = "sips.dept.contents._Session" %><%
    ParamValue     parmValue = new ParamValue(); 
	_Session         _session     = new _Session();
    ResultSetValue rset      = null;
    ResultSetValue rsetP      = null;

    String  conference_id     = request.getParameter("conference_id");
    String  gubun     = request.getParameter("gubun");

    String pre_date   = "";
    int totalCnt = 0;
    int ttlCnt = 1;
    int cnt = 1;
    String con_date = "";
    
    parmValue.put("conference_id", conference_id);
    rset = _session.getSessionList(parmValue);
    totalCnt = rset.row();
    if ("agenda".equals(gubun)){
    	//totalCnt  = _session.getSessionTotalRow(parmValue);

        while(rset.next()) {
        	con_date = rset.getString("con_date");
        	//if (con_date == null) con_date = "0000-00-00";
        	if (con_date != null){
	        	if (!pre_date.equals(con_date)){
	        		if (cnt != 1)
	        			out.println("	</DAY>");
	        		out.println("	<DAY>");
	        		out.println("		<DATE>"+con_date+"</DATE>");
	        		cnt = 1;
	        	}
	        	out.println("		<SESSION>");
	        	out.println("			<SESSION_TITLE>"+rset.getString("title")+"</SESSION_TITLE>");
	        	out.println("			<SESSION_ID>"+rset.getString("session_id")+"</SESSION_ID>");
	        	out.println("		</SESSION>");
	        	
	        	if (ttlCnt == totalCnt)
	        		out.println("	</DAY>");
	        	pre_date = con_date;
	        	cnt++;
        	}
        	ttlCnt++;
        }
    }else if ("binder".equals(gubun)){
        while(rset.next()) {
        	out.println("		<SESSION>");
        	out.println("			<SESSION_TITLE>"+rset.getString("title")+"</SESSION_TITLE>");
        	out.println("			<SESSION_ID>"+rset.getString("session_id")+"</SESSION_ID>");
        	out.println("		</SESSION>");
        }
    }
    
%>
</ROOT>