<?xml version="1.0" encoding="utf-8"?> 
<ROOT>
<%@ page language = "java" contentType="text/html; charset=UTF-8" %><%@ page import = "sips.common.lib.value.*" %><%@ page import = "sips.common.lib.util.*" %><%@ page import = "sips.dept.contents.Member" %><%
    ParamValue     parmValue = new ParamValue(); 
	Member         member    = new Member();
    ResultSetValue rset      = null;

    String  conference_id     = request.getParameter("conference_id");
    String  search_flag     = request.getParameter("search_flag");
    String  keyword     = StringUtil.defaultIfEmpty(request.getParameter("keyword"),""); 
    if (!"".equals(keyword))
    	keyword     = new String(keyword.getBytes("8859_1"), "utf-8");

    parmValue.put("conference_id", conference_id);
    parmValue.put("search_flag", search_flag);
    parmValue.put("keyword", keyword);
    rset = member.getApiQuestionMemberSearch(parmValue); 
    while(rset.next()) {
    	out.println("		<MEMBER_SEARCH>");
    	out.println("			<USER_CD>"+rset.getString("user_cd")+"</USER_CD>");
    	out.println("			<USER_NAME>"+rset.getString("name")+"</USER_NAME>");
    	out.println("			<COMPANY>"+rset.getString("company")+"</COMPANY>");
    	out.println("			<NATIONAL_NAME>"+rset.getString("nation")+"</NATIONAL_NAME>");
    	out.println("		</MEMBER_SEARCH>");
    }
%>
</ROOT>