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
    String  session_id     = request.getParameter("session_id");
    //System.out.println("keyword---->"+keyword);    

    parmValue.put("conference_id", conference_id);
    parmValue.put("search_flag", search_flag);
    parmValue.put("keyword", keyword);
    parmValue.put("session_id", session_id);
    rset = member.getApiDissertationSearch(parmValue); 
    while(rset.next()) {
    	out.println("		<DISSERTATION_SEARCH>");
    	out.println("			<BINDER_ID>"+rset.getString("binder_id")+"</BINDER_ID>");
    	if( "presenter".equals(search_flag)) {
    		out.println("			<AGEND_ID>"+rset.getString("agenda_id")+"</AGEND_ID>");
    		out.println("			<PRESENTER>"+rset.getString("presenter")+"</PRESENTER>");
    	}else if( "topic_title".equals(search_flag)) {
    		out.println("			<TOPIC_ID>"+rset.getString("topic_id")+"</TOPIC_ID>");
    	}
    	out.println("			<USER_CD>"+rset.getString("user_cd")+"</USER_CD>");
    	out.println("			<USER_NAME>"+rset.getString("user_name")+"</USER_NAME>");
    	out.println("			<WRITER>"+rset.getString("writer")+"</WRITER>");
		out.println("			<TOPIC_TITLE>"+rset.getString("topic_title")+"</TOPIC_TITLE>");
    	out.println("			<BINDER_TITLE>"+rset.getString("binder_title")+"</BINDER_TITLE>");
    	out.println("		</DISSERTATION_SEARCH>");
    }
%>
</ROOT>