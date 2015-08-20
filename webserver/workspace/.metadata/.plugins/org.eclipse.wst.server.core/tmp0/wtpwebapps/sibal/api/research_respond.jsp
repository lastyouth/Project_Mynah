<%@ page language = "java" contentType="text/html; charset=UTF-8" %><%@ page import = "sips.common.lib.value.*" %><%@ page import = "sips.common.lib.util.*" %><%@ page import = "sips.dept.contents._Research" %><%
    ParamValue     parmValue = new ParamValue(); 
	_Research         research    = new _Research();
    String result = "";

    String  research_id     = StringUtil.defaultIfEmpty(request.getParameter("research_id"      ), ""); 
    String  respond     = StringUtil.defaultIfEmpty(request.getParameter("respond"      ), ""); 
    String  user_cd     = StringUtil.defaultIfEmpty(request.getParameter("user_cd"      ), ""); 

    parmValue.put("research_id", research_id);
    parmValue.put("respond", respond);
    parmValue.put("user_cd", user_cd);
    result = research.updateResearchResponse(parmValue); 

%><?xml version="1.0" encoding="utf-8"?> 
<ROOT>
	<RESULT><%=result%></RESULT>
</ROOT>