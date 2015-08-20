<%@ page language = "java" contentType="text/html; charset=UTF-8" %><%@ page import = "sips.common.lib.value.*" %><%@ page import = "sips.common.lib.util.*" %><%@ page import = "sips.dept.contents._Message" %><%
    ParamValue     parmValue = new ParamValue(); 
	_Message         message    = new _Message();
    String result = "";

    String  conference_id     = StringUtil.defaultIfEmpty(request.getParameter("conference_id"      ), ""); 
    String  to_user_cd     = StringUtil.defaultIfEmpty(request.getParameter("to_user_cd"      ), ""); 
    String  from_user_cd     = StringUtil.defaultIfEmpty(request.getParameter("from_user_cd"      ), ""); 
    String  promise_date     = StringUtil.defaultIfEmpty(request.getParameter("promise_date"      ), ""); 
    String  promise_hour     = StringUtil.defaultIfEmpty(request.getParameter("promise_hour"      ), ""); 
    String  title     = StringUtil.defaultIfEmpty(request.getParameter("title"      ), ""); 
    if (!"".equals(title))
    	title     = new String(title.getBytes("8859_1"), "utf-8");
    String  contents     = StringUtil.defaultIfEmpty(request.getParameter("contents"      ), ""); 
    if (!"".equals(contents))
    	contents     = new String(contents.getBytes("8859_1"), "utf-8");
    //System.out.println("contents---->"+contents);    

    parmValue.put("conference_id", conference_id);
    parmValue.put("to_user_cd", to_user_cd);
    parmValue.put("from_user_cd", from_user_cd);
    parmValue.put("promise_date", promise_date);
    parmValue.put("promise_hour", promise_hour);
    parmValue.put("title", title);
    parmValue.put("contents", contents);
    result = message.insertPromise(parmValue, request); 
 
%><?xml version="1.0" encoding="utf-8"?> 
<ROOT>
	<RESULT><%=result%></RESULT>
</ROOT>