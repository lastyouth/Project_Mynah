<?xml version="1.0" encoding="utf-8"?> 
<ROOT>
<%@ page language = "java" contentType="text/html; charset=UTF-8" %><%@ page import = "sips.common.lib.value.*" %><%@ page import = "sips.common.lib.util.*" %><%@ page import = "sips.dept.contents._Memo" %><%
    ParamValue     parmValue = new ParamValue(); 
	_Memo         memo    = new _Memo();
    ResultSetValue rset      = null;

    String  conference_id     = request.getParameter("conference_id");
    String  user_cd     = request.getParameter("user_cd");

    parmValue.put("user_cd", user_cd);
    parmValue.put("conference_id", conference_id);
    rset = memo.getMemoList(parmValue); 

    while(rset.next()) {
    	out.println("		<MEMO_LIST>");
    	out.println("			<MEMO_ID>"+rset.getString("memo_id")+"</MEMO_ID>");
    	out.println("			<TITLE>"+rset.getString("title")+"</TITLE>");
    	out.println("			<REG_DATE>"+rset.getString("reg_date")+"</REG_DATE>");
    	out.println("		</MEMO_LIST>");
    }
%>
</ROOT>