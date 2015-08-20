<%@ page language = "java" contentType="text/html; charset=UTF-8" %><%@ page import = "sips.common.lib.value.*" %><%@ page import = "sips.common.lib.util.*" %><%@ page import = "sips.dept.contents._Memo" %><%
    ParamValue     parmValue = new ParamValue(); 
	_Memo         memo    = new _Memo();
    int result = 0;
    String flag = "false";

    String  memo_id     = StringUtil.defaultIfEmpty(request.getParameter("memo_id"      ), ""); 

    parmValue.put("memo_id", memo_id);
    result = memo.deleteMemo(parmValue); 
    if (result == 1)
    	flag = "success";
 
%><?xml version="1.0" encoding="utf-8"?> 
<ROOT>
	<RESULT><%=flag%></RESULT>
</ROOT>