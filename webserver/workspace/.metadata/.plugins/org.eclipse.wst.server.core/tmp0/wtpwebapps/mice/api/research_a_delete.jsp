<%@ page language = "java" contentType="text/html; charset=UTF-8" %><%@ page import = "sips.common.lib.value.*" %><%@ page import = "sips.common.lib.util.*" %><%@ page import = "sips.dept.contents._Research" %><%
    ParamValue     parmValue = new ParamValue(); 
	_Research         research    = new _Research();
    String result = "";

    String  a_num     = StringUtil.defaultIfEmpty(request.getParameter("a_num"      ), ""); 
    String  research_q_id     = StringUtil.defaultIfEmpty(request.getParameter("research_q_id"      ), ""); 

    parmValue.put("a_num", a_num);
    parmValue.put("research_q_id", research_q_id);
    result = research.updateResearchADelete(parmValue); 

%><?xml version="1.0" encoding="utf-8"?> 
<ROOT>
	<RESULT><%=result%></RESULT>
</ROOT> 