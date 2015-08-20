<?xml version="1.0" encoding="utf-8"?> 
<ROOT>
<%@ page language = "java" contentType="text/html; charset=UTF-8" %><%@ page import = "sips.common.lib.value.*" %><%@ page import = "sips.common.lib.util.*" %><%@ page import = "sips.dept.contents._Research" %><%
    ParamValue     parmValue = new ParamValue(); 
	_Research         research    = new _Research();
    ResultSetValue rset      = null;
    int cnt = 0;
    int tot = 0;
    int pre_research_q_id = 0;

    String  research_id     = request.getParameter("research_id");
    String  research_q_id     = request.getParameter("research_q_id");

    parmValue.put("research_id", research_id);
    parmValue.put("research_q_id", research_q_id);
    rset = research.getResearchAnswer(parmValue); 

    tot = rset.row();
    while(rset.next()) {
    	if (cnt == 0){
    		out.println("		<RESEARCH_id>"+rset.getString("research_id")+"</RESEARCH_id>");
    		out.println("		<RESEARCH_TITLE>"+rset.getString("title")+"</RESEARCH_TITLE>");
    		out.println("		<RESEARCH_STAT>"+rset.getString("stat")+"</RESEARCH_STAT>");
    		out.println("		<USER_CD>"+rset.getString("user_cd")+"</USER_CD>");
    	}
    	if (pre_research_q_id > 0 &&  pre_research_q_id < rset.getInt("research_q_id")){
        	out.println("		</RESEARCH_QUESTION>");
    	}
    	if (pre_research_q_id != rset.getInt("research_q_id")){
	    	out.println("		<RESEARCH_QUESTION>");
	    	out.println("			<QUESTION_ID>"+rset.getString("research_q_id")+"</QUESTION_ID>");
	    	out.println("			<QUESTION_TITLE>"+rset.getString("q_title")+"</QUESTION_TITLE>");
	    	out.println("			<QUESTION_NUM>"+rset.getString("q_num")+"</QUESTION_NUM>");
    	}
    	out.println("			<RESEARCH_ANSWER>");
    	out.println("				<ANSWER_NUM>"+rset.getString("a_num")+"</ANSWER_NUM>");
    	out.println("				<ANSWER_TITLE>"+rset.getString("a_title")+"</ANSWER_TITLE>");
    	out.println("			</RESEARCH_ANSWER>");
    	cnt++;
    	if (tot == cnt){
        	out.println("		</RESEARCH_QUESTION>");
    	}
		pre_research_q_id = rset.getInt("research_q_id");
    	
    }
%>
</ROOT>