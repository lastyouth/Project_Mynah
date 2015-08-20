<%@ page language = "java" contentType="text/html; charset=UTF-8" %><%@ page import = "sips.common.lib.value.*" %><%@ page import = "sips.common.lib.util.*" %><%@ page import = "sips.dept.contents._Member" %><%
    ParamValue     parmValue = new ParamValue(); 
	_Member         member     = new _Member();
    ResultSetValue rset      = null;
    ResultSetValue rsetP      = null;

    String  user_cd     = request.getParameter("user_cd");
    String  delete_user_cd   = request.getParameter("delete_user_cd");

    String flag   = "false";
    int rtl = 0;
    
    parmValue.put("user_cd", user_cd);
    parmValue.put("delete_user_cd", delete_user_cd);
    rtl = member.deleteHolderList(parmValue);
 
    if(rtl == 1) {
    	flag = "true";
    }
%><?xml version="1.0" encoding="utf-8"?> 
<ROOT>
	<DELETE_HOLDER>
  		<FLAG><%=flag%></FLAG>
  	</DELETE_HOLDER>
</ROOT>