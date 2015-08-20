<%@ page language = "java" contentType="text/html; charset=UTF-8" %><%@ page import = "sips.common.lib.value.*" %><%@ page import = "sips.common.lib.util.*" %><%@ page import = "sips.dept.contents._Research" %><%
    ParamValue     parmValue = new ParamValue(); 
	_Research         research    = new _Research();
    String result = "";

    String  research_id     = StringUtil.defaultIfEmpty(request.getParameter("research_id"      ), ""); 
    String  research_q_id     = StringUtil.defaultIfEmpty(request.getParameter("research_q_id"      ), ""); 
    String  q_title     = StringUtil.defaultIfEmpty(request.getParameter("q_title"      ), ""); 
    if (!"".equals(q_title))
    	q_title     = new String(q_title.getBytes("8859_1"), "utf-8");

    String  q_answer     = StringUtil.defaultIfEmpty(request.getParameter("q_answer"      ), ""); 
    if (!"".equals(q_answer))
    	q_answer     = new String(q_answer.getBytes("8859_1"), "utf-8");
    //System.out.println("contents---->"+contents);    

    parmValue.put("research_id", research_id);
    parmValue.put("research_q_id", research_q_id);
    parmValue.put("q_title", q_title);
    parmValue.put("q_answer", q_answer);
    result = research.updateResearchQeustion(parmValue); 

%><?xml version="1.0" encoding="utf-8"?> 
<ROOT>
	<RESULT><%=result%></RESULT>
</ROOT>