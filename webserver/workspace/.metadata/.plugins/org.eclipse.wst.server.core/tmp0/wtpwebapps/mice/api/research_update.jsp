<%@ page language = "java" contentType="text/html; charset=UTF-8" %><%@ page import = "sips.common.lib.value.*" %><%@ page import = "sips.common.lib.util.*" %><%@ page import = "sips.dept.contents._Research" %><%
    ParamValue     parmValue = new ParamValue(); 
	_Research         research    = new _Research();
    String result = "";

    String  research_id     = StringUtil.defaultIfEmpty(request.getParameter("research_id"      ), ""); 
    String  title     = StringUtil.defaultIfEmpty(request.getParameter("title"      ), ""); 
    if (!"".equals(title))
    	title     = new String(title.getBytes("8859_1"), "utf-8");

    parmValue.put("research_id", research_id);
    parmValue.put("title", title);
    result = research.updateResearch(parmValue); 
%><?xml version="1.0" encoding="utf-8"?> 
<ROOT>
	<RESULT><%=result%></RESULT>
</ROOT>