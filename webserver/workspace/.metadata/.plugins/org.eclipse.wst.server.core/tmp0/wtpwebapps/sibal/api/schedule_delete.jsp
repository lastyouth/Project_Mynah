<%@ page language = "java" contentType="text/html; charset=UTF-8" %><%@ page import = "sips.common.lib.value.*" %><%@ page import = "sips.common.lib.util.*" %><%@ page import = "sips.dept.contents._Schedule" %><%
    ParamValue     parmValue = new ParamValue(); 
_Schedule         schedule    = new _Schedule();
    int result = 0;
    String flag = "false";

    String  schedule_id     = StringUtil.defaultIfEmpty(request.getParameter("schedule_id"      ), ""); 

    parmValue.put("schedule_id", schedule_id);
    result = schedule.deleteSchedule(parmValue); 
    if (result == 1)
    	flag = "success";
 
%><?xml version="1.0" encoding="utf-8"?> 
<ROOT>
	<RESULT><%=flag%></RESULT>
</ROOT>