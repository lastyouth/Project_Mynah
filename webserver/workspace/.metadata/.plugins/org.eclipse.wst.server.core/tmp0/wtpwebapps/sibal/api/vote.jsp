<?xml version="1.0" encoding="utf-8"?> 
<ROOT>
<%@ page language = "java" contentType="text/html; charset=UTF-8" %><%@ page import = "sips.common.lib.value.*" %><%@ page import = "sips.common.lib.util.*" %><%@ page import = "sips.dept.contents._Vote" %><%
    ParamValue     parmValue = new ParamValue(); 
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
    
    totalCnt = vote.getVoteCheckCnt(parmValue);
    
    if (totalCnt == 0){
    	rlt = vote.voteInsert(parmValue);
    	out.println("		<VOTE>");
    	out.println("			<FLAG>"+rlt+"</FLAG>");
    	out.println("		</VOTE>");
    }else{
    	out.println("		<VOTE>");
    	out.println("			<FLAG>duplicate</FLAG>");
    	out.println("		</VOTE>");
    }
%>
</ROOT>