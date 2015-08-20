<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<% request.setCharacterEncoding("UTF-8"); %>
<%
	String root_path = request.getContextPath();
%>
<%@ page import = "java.util.*"  %>
<%@ page import = "sips.common.lib.util.*" %>
<%@ page import = "sips.common.lib.value.*" %> 
<%@ page import = "sips.dept.menu.*"   %>
<%@ page import = "sips.dept.contents.Topic"   %>

<%
	Topic            topic   = new Topic();
    ParamValue     parmValue     = new ParamValue();
    ResultSetValue rset          = null;
    
    String modeType           = StringUtil.defaultIfEmpty(request.getParameter("modeType"    ), "");  
    String topic_id     = StringUtil.defaultIfEmpty(request.getParameter("topic_id"),"");

    String conference_id     = StringUtil.defaultIfEmpty(request.getParameter("conference_id"),"");
    String  title    = StringUtil.defaultIfEmpty(request.getParameter("title"),"");

    String b_mode             = StringUtil.defaultIfEmpty(request.getParameter("b_mode"      ), ""); 
        
    String result = "";
    String searchTarget       = "";
    String keyword            = "";
    String searchFlag         = "";
    String strMsg             = "";    
    int    cnt = 0;
    int    cnt2 = 0;
    boolean  isOk = true;
     
    ParamValue     menuParam = new ParamValue();
    MenuContents   menuMgr   = new MenuContents();
    ResultSetValue menuRset  = null;    
    

    parmValue.put("conference_id"    , conference_id    );
    parmValue.put("topic_id"    , topic_id    );
    parmValue.put("title"    , title    );
    try {	   
        if( isOk ) {
            if( "ins".equals(modeType)){
            	result = topic.topicInsert(parmValue);
            	if ("Complete!".equals(result))
            		b_mode = "L";
            } else if( "mod".equals(modeType)){
            	result = topic.topicModify(parmValue);
            	if ("Complete!".equals(result))
            		b_mode = "L";
            } else if( "del".equals(modeType)){  
            	result = topic.topicDeleteContents(parmValue);  
            	if ("Complete!".equals(result))
        		b_mode = "L";
            }
        }        
%>
<form name="frmBrd" action="topic.jsp" method="post">
<input type="hidden" name="conference_id" value="<%=conference_id%>">
<input type="hidden" name="b_mode" value="<%=b_mode%>">
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

