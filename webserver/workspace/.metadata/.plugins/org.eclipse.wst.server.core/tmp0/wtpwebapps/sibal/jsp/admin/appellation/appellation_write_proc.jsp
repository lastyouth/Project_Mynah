<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<% request.setCharacterEncoding("UTF-8"); %>
<%
	String root_path = request.getContextPath();
%>
<%@ page import = "java.util.*"  %>
<%@ page import = "sips.common.lib.util.*" %>
<%@ page import = "sips.common.lib.value.*" %> 
<%@ page import = "sips.dept.contents.Appellation"   %>

<%
	Appellation            appellation     = new Appellation();
    ParamValue     parmValue     = new ParamValue();
    ResultSetValue rset          = null;

    String modeType           = StringUtil.defaultIfEmpty(request.getParameter("modeType"    ), "");  
    String appellation_id           = StringUtil.defaultIfEmpty(request.getParameter("appellation_id"    ), "");  
    String appellation_name           = StringUtil.defaultIfEmpty(request.getParameter("appellation_name"    ), "");  
    String conference_id           = StringUtil.defaultIfEmpty(request.getParameter("conference_id"    ), "");  
    String worker_cd         = (String)session.getAttribute("USER_CD");
    
    String result = "";
    String boxes  = "";
    String duplicateMsg = "";
    String b_mode             = StringUtil.defaultIfEmpty(request.getParameter("b_mode"      ), ""); 
        
    boolean  isOk = true;

    parmValue.put("conference_id"    , conference_id    );
    parmValue.put("appellation_name"    , appellation_name    );
    parmValue.put("appellation_id"    , appellation_id    );
    parmValue.put("worker_cd"    , worker_cd    );
    try {	   
        if( isOk ) {
            if( "ins".equals(modeType)){
            	result = appellation.appellationInsert(parmValue);
            	if ("Complete!".equals(result))
            		b_mode = "L";
            } else if( "mod".equals(modeType)){
            	result = appellation.appellationlModify(parmValue);
            	if ("Complete!".equals(result))
            		b_mode = "L";
            } else if( "del".equals(modeType)){  
            	appellation.appellationDeleteContents(parmValue);  
            }
        } 
%>
<form name="frmBrd" action="appellation.jsp" method="post">
<input type="hidden" name="conference_id" value="<%=conference_id%>">
<% if( "del".equals(modeType)){  %>
<input type="hidden" name="b_mode" value="L">
<% }else{  %>
<input type="hidden" name="b_mode" value="<%=b_mode%>">
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

