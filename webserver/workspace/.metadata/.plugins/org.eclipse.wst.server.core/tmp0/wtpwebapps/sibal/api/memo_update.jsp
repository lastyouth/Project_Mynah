<%@ page language = "java" contentType="text/html; charset=UTF-8" %><%@ page import = "sips.common.lib.value.*" %><%@ page import = "sips.common.lib.util.*" %><%@ page import = "sips.dept.contents._Memo" %><%
    ParamValue     parmValue = new ParamValue(); 
	_Memo         memo    = new _Memo();
    int result = 0;
    String flag = "false";

    String  memo_id     = StringUtil.defaultIfEmpty(request.getParameter("memo_id"      ), ""); 
    String  contents     = StringUtil.defaultIfEmpty(request.getParameter("contents"      ), ""); 
    if (!"".equals(contents))
    	contents     = new String(contents.getBytes("8859_1"), "utf-8");

    String  title     = StringUtil.defaultIfEmpty(request.getParameter("title"      ), ""); 
    if (!"".equals(title))
    	title     = new String(title.getBytes("8859_1"), "utf-8");
    //System.out.println("contents---->"+contents);    

    parmValue.put("memo_id", memo_id);
    parmValue.put("contents", contents);
    parmValue.put("title", title);
    result = memo.updateMemo(parmValue); 
    if (result == 1)
    	flag = "success";
 
%><?xml version="1.0" encoding="utf-8"?> 
<ROOT>
	<RESULT><%=flag%></RESULT>
</ROOT>