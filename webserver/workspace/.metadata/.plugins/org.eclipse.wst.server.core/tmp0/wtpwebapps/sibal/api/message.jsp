<%@ page language = "java" contentType="text/html; charset=UTF-8" %><%@ page import = "sips.common.lib.value.*" %><%@ page import = "sips.common.lib.util.*" %><%@ page import = "sips.dept.contents._Message" %><%
    ParamValue     parmValue = new ParamValue(); 
	_Message         message    = new _Message();
    String result = "";

    String  conference_id     = StringUtil.defaultIfEmpty(request.getParameter("conference_id"      ), ""); 
    String  to_user_cd     = StringUtil.defaultIfEmpty(request.getParameter("to_user_cd"      ), ""); 
    String  from_user_cd     = StringUtil.defaultIfEmpty(request.getParameter("from_user_cd"      ), "0"); 
    String  contents     = StringUtil.defaultIfEmpty(request.getParameter("contents"      ), ""); 
    if (!"".equals(contents))
    	contents     = new String(contents.getBytes("8859_1"), "utf-8");

    String  title     = StringUtil.defaultIfEmpty(request.getParameter("title"      ), ""); 
    if (!"".equals(title))
    	title     = new String(title.getBytes("8859_1"), "utf-8");
    String  reply     = StringUtil.defaultIfEmpty(request.getParameter("reply"      ), "0"); 
    //System.out.println("contents---->"+contents);    

    parmValue.put("conference_id", conference_id);
    parmValue.put("to_user_cd", to_user_cd);
    parmValue.put("from_user_cd", from_user_cd);
    parmValue.put("contents", contents);
    parmValue.put("title", title);
    parmValue.put("reply", reply);
    result = message.insertMassage(parmValue, request); 
 
%><?xml version="1.0" encoding="utf-8"?> 
<ROOT>
	<RESULT><%=result%></RESULT>
</ROOT>