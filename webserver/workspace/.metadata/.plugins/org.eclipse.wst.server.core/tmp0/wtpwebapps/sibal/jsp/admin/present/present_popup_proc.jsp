<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<% request.setCharacterEncoding("UTF-8"); %>
<%
	String root_path = request.getContextPath();
%>
<%@ page import = "java.util.*"  %>
<%@ page import = "sips.common.lib.util.*" %>
<%@ page import = "sips.common.lib.value.*" %> 
<%@ page import = "sips.dept.menu.*"   %>
<%@ page import = "sips.dept.contents.Present" %>

<%
Present            present     = new Present();
    ParamValue     parmValue     = new ParamValue();
    ResultSetValue rset          = null;

    String conference_id       = StringUtil.defaultIfEmpty(request.getParameter("conference_id"), "");
	String present_id       = StringUtil.defaultIfEmpty(request.getParameter("present_id"), "");
	String searchTarget       = StringUtil.defaultIfEmpty(request.getParameter("searchTarget"), "");  // 검색 조건 (제목/내용)
    String keyword            = StringUtil.defaultIfEmpty(request.getParameter("keyword"     ), "");  // 검색할 키워드
    
    String boxes  = "";
    String duplicateMsg = "";

    if (StringUtil.defaultIfEmpty(request.getParameter("boxes"),null)!= null){
    	String checkBoxes[] = request.getParameterValues("boxes");
    	for (String checkbox : checkBoxes)
    	{
    		boxes =  boxes + checkbox+ ",";
    	}
    	boxes = boxes.substring(0,boxes.length()-1);
    }
    //System.out.println("boxes----------->"+boxes);    //
    String b_mode             = "R";
        
    boolean  isOk = true;

    parmValue.put("gift_user_cds"    , boxes    );
    parmValue.put("conference_id"    , conference_id    );
    parmValue.put("present_id"    , present_id    );
    parmValue.put("searchTarget"    , searchTarget    );
    parmValue.put("keyword"    , keyword    );
    try {	   
        if( isOk ) {
        	present.insertPresentMapping(parmValue);
        }        
%>
<form name="frmBrd" action="present.jsp" method="post">
<input type="hidden" name="conference_id" value="<%=conference_id%>">
<input type="hidden" name="present_id" value="<%=present_id%>">
<input type="hidden" name="b_mode" value="<%=b_mode%>">
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

