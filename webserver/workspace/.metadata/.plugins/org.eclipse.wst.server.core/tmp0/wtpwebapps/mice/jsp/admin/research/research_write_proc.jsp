<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<% request.setCharacterEncoding("UTF-8"); %>
<%
	String root_path = request.getContextPath();
%>
<%@ page import = "java.util.*"  %>
<%@ page import = "sips.common.lib.util.*" %>
<%@ page import = "sips.common.lib.value.*" %> 
<%@ page import = "sips.dept.contents.Research"   %>

<%
	Research            research     = new Research();
    ParamValue     parmValue     = new ParamValue();
    ResultSetValue rset          = null;

    String modeType           = StringUtil.defaultIfEmpty(request.getParameter("modeType"    ), "");  
    String research_id           = StringUtil.defaultIfEmpty(request.getParameter("research_id"    ), "");   
    String title           = StringUtil.defaultIfEmpty(request.getParameter("title"    ), "");   
    String q_title           = StringUtil.defaultIfEmpty(request.getParameter("q_title"    ), "");   
    String conference_id           = StringUtil.defaultIfEmpty(request.getParameter("conference_id"    ), "");  
    String research_q_id           = StringUtil.defaultIfEmpty(request.getParameter("research_q_id"    ), "");  
    String worker_cd         = (String)session.getAttribute("USER_CD");
    
    String result = "";
    String duplicateMsg = "";
    String b_mode             = StringUtil.defaultIfEmpty(request.getParameter("b_mode"      ), ""); 
    String a_titles  = "";
    
    if (StringUtil.defaultIfEmpty(request.getParameter("a_title"),null)!= null){
    	String a_input_titles[] = request.getParameterValues("a_title");
    	for (String input : a_input_titles)
    	{
    		if(!"".equals(input))
    			a_titles =  a_titles + input+ ",";
    	}
    	a_titles = a_titles.substring(0,a_titles.length()-1);
    }
        
    boolean  isOk = true;
    
    parmValue.put("conference_id"    , conference_id    );
    parmValue.put("research_q_id"    , research_q_id    );
    parmValue.put("title"    , title    );
    parmValue.put("research_id"    , research_id    );
    parmValue.put("q_title"    , q_title    );
    parmValue.put("a_titles"    , a_titles    );
    parmValue.put("worker_cd"    , worker_cd    );
    try {	   
        if( isOk ) {
            if( "ins".equals(modeType)){
            	research_id = research.questionInsert(parmValue);
            } else if( "del".equals(modeType)){  
            	research.researchDeleteContents(parmValue);  
            }else if( "start".equals(modeType)){  
            	research.researchStartStat(parmValue);  
            }else if( "end".equals(modeType)){  
            	research.researchEndStat(parmValue); 
            }else if( "qmod".equals(modeType)){  
            	research.researchQModify(parmValue); 
            }else if( "qdel".equals(modeType)){
                research.researchlQDelete(parmValue);
            } 
        }
%>
<form name="frmBrd" action="research.jsp" method="post">
<input type="hidden" name="conference_id" value="<%=conference_id%>">
<% if( "del".equals(modeType)){  %>
<input type="hidden" name="b_mode" value="L">
<% }else{  %>
<input type="hidden" name="b_mode" value="<%=b_mode%>">
<input type="hidden" name="research_id" value="<%=research_id%>">
<% }  %>
</form>
<script language="javascript">
<%
if (!"".equals(result)){ out.println("alert('"+result+"');");}
%>
	document.frmBrd.submit();
</script>  
  

<%  }
    catch (Exception e) {
        System.out.println(e.toString());
        out.println(e.toString());
        e.printStackTrace();
    }   
%>

