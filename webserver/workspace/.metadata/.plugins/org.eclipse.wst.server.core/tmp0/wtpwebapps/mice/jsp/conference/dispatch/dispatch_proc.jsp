<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<% request.setCharacterEncoding("UTF-8"); %>
<%
	String root_path = request.getContextPath();
%>
<%@ page import = "java.util.*"  %>
<%@ page import = "sips.common.lib.util.*" %>
<%@ page import = "sips.common.lib.value.*" %> 
<%@ page import = "sips.dept.menu.*"   %>
<%@ page import = "sips.dept.contents._Message"   %>

<%
	_Message            message     = new _Message();
    ParamValue     parmValue     = new ParamValue();
    ResultSetValue rset          = null;
    String result = "";

    String modeType           = StringUtil.defaultIfEmpty(request.getParameter("modeType"    ), "");  String  conference_id     = StringUtil.defaultIfEmpty(request.getParameter("conference_id"      ), ""); 
    String  to_user_cd     = StringUtil.defaultIfEmpty(request.getParameter("from_user_cd"      ), ""); 
    String  from_user_cd     = (String)session.getAttribute("USER_CD");
    String  contents     = StringUtil.defaultIfEmpty(request.getParameter("contents"      ), ""); 
    String  title     = StringUtil.defaultIfEmpty(request.getParameter("title"      ), ""); 
    String  reply     = StringUtil.defaultIfEmpty(request.getParameter("message_id"      ), "0"); 
    
    String b_mode             = "";
        
    boolean  isOk = true;

    parmValue.put("conference_id", conference_id);
    parmValue.put("to_user_cd", to_user_cd);
    parmValue.put("from_user_cd", from_user_cd);
    parmValue.put("contents", contents);
    parmValue.put("title", title);
    parmValue.put("reply", reply);
    parmValue.put("message_id", reply);
    try {	   
        if( isOk ) {
            if( "ins".equals(modeType)){
            	result = message.insertMassage(parmValue, request); 
            } else if( "mod".equals(modeType)){
            	//result = message.updateMessage(parmValue);
            } else if( "del".equals(modeType)){  
            	result = message.messageDelete(parmValue);
            }
        }        
%>
<form name="frmBrd" action="dispatch.jsp" method="post">
</form>
<script language="javascript">
	document.frmBrd.submit();
</script>  
  

<%  }
    catch (Exception e) {
        System.out.println(e.toString());
        out.println(e.toString());
        e.printStackTrace();
    }   
%>

