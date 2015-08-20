<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<% request.setCharacterEncoding("UTF-8"); %>
<%
	String root_path = request.getContextPath();
%>
<%@ page import = "java.util.*"  %>
<%@ page import = "sips.common.lib.util.*" %>
<%@ page import = "sips.common.lib.value.*" %> 
<%@ page import = "sips.dept.contents.Agenda"   %>

<%
	Agenda            agenda   = new Agenda();
    ParamValue     parmValue     = new ParamValue();
    ResultSetValue rset          = null;
    
    String modeType           = StringUtil.defaultIfEmpty(request.getParameter("modeType"    ), "");  
    String agenda_id     = StringUtil.defaultIfEmpty(request.getParameter("agenda_id"),"");

    String conference_date     = StringUtil.defaultIfEmpty(request.getParameter("conference_date"),"");
    String  presenter    = StringUtil.defaultIfEmpty(request.getParameter("presenter"),"");
    String  summary    = StringUtil.defaultIfEmpty(request.getParameter("summary"),"");
    String  start_time    = StringUtil.defaultIfEmpty(request.getParameter("start_time"),"");
    String  end_time    = StringUtil.defaultIfEmpty(request.getParameter("end_time"),"");
    String  binder_id    = StringUtil.defaultIfEmpty(request.getParameter("binder_id"),"");
    String conference_id        = StringUtil.defaultIfEmpty(request.getParameter("conference_id"),"");
    String worker_cd   = (String)session.getAttribute("USER_CD");
    
    String b_mode             = "";
        
    String searchTarget       = "";
    String keyword            = "";
    String searchFlag         = "";
    String strMsg             = "";    
    int    cnt = 0;
    int    cnt2 = 0;
    boolean  isOk = true;    
    int rlt =  99;

    parmValue.put("conference_date"    , conference_date    );
    parmValue.put("presenter"    , presenter    );
    parmValue.put("summary"    , summary    );
    parmValue.put("start_time"    , start_time    );
    parmValue.put("end_time"    , end_time    );
    parmValue.put("binder_id"    , binder_id    );
    parmValue.put("worker_cd"    , worker_cd    );
    parmValue.put("agenda_id"    , agenda_id    );
    parmValue.put("conference_id"    , conference_id    );
    try {	   
        if( isOk ) {
            if( "ins".equals(modeType)){
            	rlt = agenda.agendaInsert(parmValue);
            } else if( "mod".equals(modeType)){
            	rlt = agenda.agendaModify(parmValue);
            } else if( "del".equals(modeType)){  
            	 agenda.agendaDeleteContents(parmValue);  
            }
        }        

%>
<form name="frmBrd" action="agenda.jsp" method="post">
<input type=hidden name=conference_id	value="<%=conference_id%>"      />
</form>
<script language="javascript">
	var result = "<%=rlt%>";
	if (result == 0){
		alert('시간 설정을 확인해주세요.');
		history.back();
		return ;
	}
	document.frmBrd.submit();
</script>  
  

<%  }
    catch (Exception e) {
        System.out.println(e.toString());
        out.println(e.toString());
        e.printStackTrace();
    }   
%>

