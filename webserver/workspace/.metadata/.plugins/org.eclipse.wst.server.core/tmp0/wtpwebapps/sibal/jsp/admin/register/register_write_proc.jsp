<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<% request.setCharacterEncoding("UTF-8"); %>
<%
	String root_path = request.getContextPath();
%>
<%@ page import = "java.util.*"  %>
<%@ page import = "sips.common.lib.util.*" %>
<%@ page import = "sips.common.lib.value.*" %> 
<%@ page import = "sips.dept.menu.*"   %>
<%@ page import = "sips.dept.contents.Member"   %>

<%
	Member            member     = new Member();
    ParamValue     parmValue     = new ParamValue();
    ResultSetValue rset          = null;
    
    String modeType           = StringUtil.defaultIfEmpty(request.getParameter("modeType"    ), "");  
    String user_cd     = StringUtil.defaultIfEmpty(request.getParameter("user_cd"),"");

    String conference_id     = StringUtil.defaultIfEmpty(request.getParameter("conference_id"),"");
    String  authority    = StringUtil.defaultIfEmpty(request.getParameter("authority"),"");
    
    String b_mode             = "";
        
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
    parmValue.put("authority"    , authority    );
    parmValue.put("user_cd"        , user_cd        );        
    try {	   
        if( isOk ) {
            if( "ins".equals(modeType)){
            	member.memberInsert(parmValue);
            } else if( "mod".equals(modeType)){
            	member.authorityModify(parmValue);
            } else if( "del".equals(modeType)){  
            	 member.memberListDeleteContents(parmValue);  
            }
        } 
        
%>
<form name="frmBrd" action="register.jsp" method="post">
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

