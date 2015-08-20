<%@ page language = "java" contentType="text/html; charset=UTF-8" %><%@ page import = "sips.common.lib.value.*" %><%@ page import = "sips.common.lib.util.*" %><%@ page import = "sips.dept.contents._Research" %><%
    ParamValue     parmValue = new ParamValue(); 
	_Research         research    = new _Research();
    ResultSetValue rset      = null;
    int researchCnt = 0;
    int i = 0;
    String flag = "false";
    String research_num = "";

    String  conference_id     = StringUtil.defaultIfEmpty(request.getParameter("conference_id"      ), ""); 
    String  research_id     = StringUtil.defaultIfEmpty(request.getParameter("research_id"      ), ""); 
    String  user_cd     = StringUtil.defaultIfEmpty(request.getParameter("user_cd"      ), ""); 
    String  title     = StringUtil.defaultIfEmpty(request.getParameter("title"      ), ""); 
    if (!"".equals(title))
    	title     = new String(title.getBytes("8859_1"), "utf-8");
    //System.out.println("contents---->"+contents);    

    parmValue.put("user_cd", user_cd);
    if (!"".equals(title)){
        parmValue.put("conference_id", conference_id);
	    parmValue.put("title", title);
	    researchCnt = research.inserResearch_q_insert(parmValue); 
	    if (researchCnt > 0 ){
%><?xml version="1.0" encoding="utf-8"?> 
 	<ROOT>
 		<RESEARCH_NUMBER><%=researchCnt%></RESEARCH_NUMBER>
 		<RESEARCH_TITLE><%=title%></RESEARCH_TITLE>
	</ROOT> 
<%	}else{
%><?xml version="1.0" encoding="utf-8"?> 
<ROOT>
	<RESULT>false</RESULT>
</ROOT>
<%	} 
    }else{
        parmValue.put("research_id", research_id);
    	rset =  research.inserResearch_q_list(parmValue); 
%><?xml version="1.0" encoding="utf-8"?> 
<ROOT>
	<RESEARCH>
<%
		while(rset.next()) {
			if ( i == 0){
				out.println("			<RESEARCH_NUMBER>"+rset.getString("research_num")+"</RESEARCH_NUMBER>");
				out.println("			<RESEARCH_id>"+research_id+"</RESEARCH_id>");
				out.println("			<RESEARCH_TITLE>"+rset.getString("title")+"</RESEARCH_TITLE>");
		    	out.println("			<RESEARCH_STAT>"+rset.getString("stat")+"</RESEARCH_STAT>");
		    	out.println("			<USER_CD>"+rset.getString("user_cd")+"</USER_CD>");
			}
		    	out.println("			<QUESTION_LIST>");
		    	out.println("				<QUESTION_ID>"+StringUtil.defaultIfEmpty(rset.getString("research_q_id"), "")+"</QUESTION_ID>");
		    	out.println("				<QUESTION_NUM>"+StringUtil.defaultIfEmpty(rset.getString("q_num"), "")+"</QUESTION_NUM>");
		    	out.println("				<QUESTION_TITLE>"+StringUtil.defaultIfEmpty(rset.getString("q_title"), "")+"</QUESTION_TITLE>");
		    	out.println("			</QUESTION_LIST>");
		    	i++;
		}
%>
	</RESEARCH>
</ROOT>
<% } %>
		 
