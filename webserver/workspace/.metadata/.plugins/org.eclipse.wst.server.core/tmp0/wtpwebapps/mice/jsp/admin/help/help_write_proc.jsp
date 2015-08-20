<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<% request.setCharacterEncoding("UTF-8"); %>
<%
	String root_path = request.getContextPath();
%>
<%@ page import = "java.util.*"  %>
<%@ page import = "sips.common.lib.util.*" %>
<%@ page import = "sips.common.lib.value.*" %> 
<%@ page import = "sips.dept.menu.*"   %>
<%@ page import = "sips.dept.contents.Help"   %>

<%
	Help            help     = new Help();
    ParamValue     parmValue     = new ParamValue();
    ResultSetValue rset          = null;

    String modeType           = StringUtil.defaultIfEmpty(request.getParameter("modeType"    ), "");  
    String help_id           = StringUtil.defaultIfEmpty(request.getParameter("help_id"    ), "");  
    String reply           = StringUtil.defaultIfEmpty(request.getParameter("reply"    ), "");  
    String worker_cd         = (String)session.getAttribute("USER_CD");
    String b_mode             = "";
        
    boolean  isOk = true;

    parmValue.put("worker_cd"    , worker_cd    );
    parmValue.put("reply"    , reply    );
    parmValue.put("help_id"    , help_id    );
    try {	   
        if( isOk ) {
        	help.updateHelpReply(parmValue);
        }       
%>
<form name="frmBrd" action="help.jsp" method="post">
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

