<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<% request.setCharacterEncoding("UTF-8"); %>
<%
	String root_path = request.getContextPath();
%>
<%@ page import = "java.util.*"  %>
<%@ page import = "sips.common.lib.util.*" %>
<%@ page import = "sips.common.lib.value.*" %> 
<%@ page import = "sips.dept.menu.*"   %>
<%@ page import = "sips.dept.contents._Schedule"   %>

<%
	_Schedule            schedule     = new _Schedule();
    ParamValue     parmValue     = new ParamValue();
    ResultSetValue rset          = null;

    String user_cd         = (String)session.getAttribute("USER_CD");
    String modeType           = StringUtil.defaultIfEmpty(request.getParameter("modeType"    ), "");  
    String schedule_id           = StringUtil.defaultIfEmpty(request.getParameter("schedule_id"    ), "");  
    String title           = StringUtil.defaultIfEmpty(request.getParameter("title"    ), "");  
    String conference_date           = StringUtil.defaultIfEmpty(request.getParameter("conference_date"    ), "");  
    String contents           = StringUtil.defaultIfEmpty(request.getParameter("contents"    ), "");  
    String start_time           = StringUtil.defaultIfEmpty(request.getParameter("start_time"    ), "");  
    String end_time           = StringUtil.defaultIfEmpty(request.getParameter("end_time"    ), "");  
    //System.out.println("boxes----------->"+boxes);    //
    String b_mode             = "";
    int result = 0;
        
    boolean  isOk = true;

    parmValue.put("user_cd"    , user_cd    );
    parmValue.put("schedule_id"    , schedule_id    );
    parmValue.put("title"    , title    );
    parmValue.put("contents"    , contents    );
    parmValue.put("conference_date"    , conference_date    );
    parmValue.put("start_time"    , start_time    );
    parmValue.put("end_time"    , end_time    );
    try {	   
        if( isOk ) {
            if( "ins".equals(modeType)){
            	result = schedule.inserSchedule(parmValue);
            } else if( "mod".equals(modeType)){
            	result = schedule.updateSchedule(parmValue);
            } else if( "del".equals(modeType)){  
            	result = schedule.deleteSchedule(parmValue);  
            }
        }        
%>
<form name="frmBrd" action="schedule.jsp" method="post">
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

