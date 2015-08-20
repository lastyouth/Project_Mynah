<?xml version="1.0" encoding="utf-8"?> 
<ROOT>
<%@ page language = "java" contentType="text/html; charset=UTF-8" %><%@ page import = "sips.common.lib.value.*" %><%@ page import = "sips.common.lib.util.*" %><%@ page import = "sips.dept.contents._Memo" %><%
    ParamValue     parmValue = new ParamValue(); 
	_Memo         memo    = new _Memo();
    ResultSetValue rset      = null;

    String  memo_id     = request.getParameter("memo_id");

    parmValue.put("memo_id", memo_id);
    rset = memo.getMemoContents(parmValue); 
    if(rset.next()) {
    	out.println("		<MEMO_CONTENTS>");
    	out.println("			<MEMO_ID>"+rset.getString("memo_id")+"</MEMO_ID>");
    	out.println("			<TITLE>"+rset.getString("title")+"</TITLE>");
    	out.println("			<CONTENTS>"+rset.getString("contents")+"</CONTENTS>");
    	out.println("			<REG_DATE>"+rset.getString("reg_date")+"</REG_DATE>");
    	out.println("		</MEMO_CONTENTS>");
    }
%>
</ROOT>