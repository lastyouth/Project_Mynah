<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<% request.setCharacterEncoding("UTF-8"); %>
<%
	String root_path = request.getContextPath();
%>
<%@ page import = "java.util.*"  %>
<%@ page import = "sips.common.lib.util.*" %>
<%@ page import = "sips.common.lib.value.*" %> 
<%@ page import = "sips.dept.menu.*"   %>
<%@ page import = "sips.dept.contents._Memo"   %>

<%
	_Memo            memo     = new _Memo();
    ParamValue     parmValue     = new ParamValue();
    ResultSetValue rset          = null;

    String user_cd         = (String)session.getAttribute("USER_CD");
    String modeType           = StringUtil.defaultIfEmpty(request.getParameter("modeType"    ), "");  
    String memo_id           = StringUtil.defaultIfEmpty(request.getParameter("memo_id"    ), "");  
    String title           = StringUtil.defaultIfEmpty(request.getParameter("title"    ), "");  
    String contents           = StringUtil.defaultIfEmpty(request.getParameter("contents"    ), "");  
    //System.out.println("boxes----------->"+boxes);    //
    String b_mode             = "";
    int result = 0;
        
    boolean  isOk = true;

    parmValue.put("user_cd"    , user_cd    );
    parmValue.put("memo_id"    , memo_id    );
    parmValue.put("title"    , title    );
    parmValue.put("contents"    , contents    );
    try {	   
        if( isOk ) {
            if( "ins".equals(modeType)){
            	result = memo.inserMemo(parmValue);
            } else if( "mod".equals(modeType)){
            	result = memo.updateMemo(parmValue);
            } else if( "del".equals(modeType)){  
            	result = memo.deleteMemo(parmValue);  
            }
        }        
%>
<form name="frmBrd" action="memo.jsp" method="post">
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

