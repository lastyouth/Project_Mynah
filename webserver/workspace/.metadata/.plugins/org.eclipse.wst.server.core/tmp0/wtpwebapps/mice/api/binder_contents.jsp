<?xml version="1.0" encoding="utf-8"?> 
<ROOT>
<%@ page language = "java" contentType="text/html; charset=UTF-8" %><%@ page import = "sips.common.lib.value.*" %><%@ page import = "sips.common.lib.util.*" %><%@ page import = "sips.dept.contents._Session" %><%@ page import = "sips.dept.contents._Vote" %><%
    ParamValue     parmValue = new ParamValue(); 
	_Session         _session     = new _Session();
	_Vote         vote     = new _Vote();
    ResultSetValue rset      = null;
    ResultSetValue rsetP      = null;

    String rlt = "false";
    int totalCnt = 1;

    String  user_cd     = request.getParameter("user_cd");
    String  conference_id     = request.getParameter("conference_id");
    String  binder_id     = request.getParameter("binder_id");

    parmValue.put("user_cd", user_cd);
    parmValue.put("conference_id", conference_id);
    parmValue.put("binder_id", binder_id);
    
    rset = _session.getBinderContetns(parmValue);
    totalCnt = vote.getVoteCheckCnt(parmValue);
 
    while(rset.next()) {
    	out.println("		<BINDER>");
    	out.println("			<BINDER_ID>"+rset.getString("binder_id")+"</BINDER_ID>");
    	out.println("			<USER_CD>"+rset.getString("user_cd")+"</USER_CD>");
    	out.println("			<SESSION_ID>"+rset.getString("session_id")+"</SESSION_ID>");
    	out.println("			<USER_NAME>"+rset.getString("user_name")+"</USER_NAME>");
    	out.println("			<BINDER_TITLE>"+rset.getString("title")+"</BINDER_TITLE>");
    	out.println("			<CONTENTS>"+rset.getString("contents")+"</CONTENTS>");
    	out.println("			<WRITER>"+rset.getString("writer")+"</WRITER>");
    	out.println("			<ATTACHED>/mice/upload/pdf/"+rset.getString("attached")+"</ATTACHED>");
    	if (totalCnt == 0){
        	out.println("			<VOTE_FLAG>TRUE</VOTE_FLAG>");
        }else{
        	out.println("			<VOTE_FLAG>FALSE</VOTE_FLAG>");
        }
    	out.println("		</BINDER>");
    }
%>
</ROOT>