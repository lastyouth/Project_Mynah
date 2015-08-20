<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<% request.setCharacterEncoding("UTF-8"); %>
<%
	String root_path = request.getContextPath();
%>
<%@ page import = "sips.common.lib.util.*" %>
<%@ page import = "sips.common.lib.value.*" %>
<%@ page import = "sips.dept.contents._Schedule" %>
<%@ page import = "sips.dept.menu.*" %>
<%  String formName = "frmBoard"; %>
<%
	ParamValue       parmValue = new ParamValue();          
	_Schedule        schedule     = new _Schedule();
    ResultSetValue   rset      = null;
    
	int    cpage              = (request.getParameter("cpage") == null || "".equals(request.getParameter("cpage"))) ? 0 : Integer.parseInt(request.getParameter("cpage"));   // 현재 페이지
    String searchTarget       = StringUtil.defaultIfEmpty(request.getParameter("searchTarget"), "");	      // 검색 조건 (제목/내용)
    String keyword            = StringUtil.defaultIfEmpty(request.getParameter("keyword"     ), "");        // 검색할 키워드
    String searchFlag         = StringUtil.defaultIfEmpty(request.getParameter("searchFlag"  ), "");        // 검색플래그
    String schedule_id       = StringUtil.defaultIfEmpty(request.getParameter("schedule_id"), "");
    String modeType           = StringUtil.defaultIfEmpty(request.getParameter("modeType"    ), "ins");
    String b_mode             = StringUtil.defaultIfEmpty(request.getParameter("b_mode"      ), ""); 
    
    String title  = "";
    String contents = "";
    String strMsg             = "";
    String conference_date  = "";    
    String start_time  = "";    
    String end_time  = "";    
    
    parmValue.put("schedule_id"    , schedule_id  );
    rset = schedule.getScheduleContents(parmValue);
    while( rset.next()){
    	title    = StringUtil.defaultIfEmpty(rset.getString("title"    ), "");
    	contents    = StringUtil.defaultIfEmpty(rset.getString("contents"    ), "");
    	conference_date    = StringUtil.defaultIfEmpty(rset.getString("conference_date"    ), "");
    	start_time    = StringUtil.defaultIfEmpty(rset.getString("start_time"    ), "");
    	end_time    = StringUtil.defaultIfEmpty(rset.getString("end_time"    ), "");
    }
%>
<script language="JavaScript" type="text/JavaScript">
<!--    
    function goList() {
        var frm = document.<%=formName%>;
        
        frm.b_mode.value = "L";
        frm.action   = "<%= request.getRequestURI() %>";
        frm.submit();
    }
	function goModifyForm (schedule_id ) {        
	    var frm = document.<%=formName%>;
	    
	    frm.schedule_id.value = schedule_id;
	    frm.target="_self";
	    frm.b_mode.value = "M";
	    frm.action = "<%=request.getRequestURI()%>";
	    frm.submit();
	}    
	/**
	 * 게시물 삭제
	 */
	function goDeleteForm (schedule_id) { 
	    var frm = document.<%=formName%>;
	
	    if( confirm("삭제 하시겠습니까?")) {
	        frm.schedule_id.value = schedule_id;
	        frm.modeType.value = "del";
	        frm.action   = "schedule_write_proc.jsp";
	        frm.submit();
	    }
	            
	}    
  //-->
</script>
<form name="<%=formName%>" method="post">
<input type="hidden" name="cpage"        value="<%= cpage       %>">
<input type="hidden" name="schedule_id" value="<%=schedule_id %>">  
<input type="hidden" name="b_mode"       value="<%=b_mode      %>">
<input type="hidden" name="modeType"     value="<%=modeType    %>">

<table width="100%" border="0" cellspacing="0" cellpadding="0">
      
      <tr>
        <td valign="top"><table border="0" cellpadding="0" cellspacing="0" style="border-top:2px solid #e0e0e0; border-bottom:2px solid #e0e0e0; width:784px;">
            <tr>
              <td width="210" class="bbbg" style="padding-left:20px; font-weight:bold;">스케쥴 날짜</td>
              <td width="574" style="padding-left:20px;"><%=conference_date%></td>
            </tr>
            <tr>
              <td width="210" class="bbbg" style="padding-left:20px; font-weight:bold;">시간</td>
              <td width="574" style="padding-left:20px;">
              	<%= !"".equals(start_time)?start_time.substring(0,2):""%>:<%= !"".equals(start_time)?start_time.substring(2,4):""%> ~  <%= !"".equals(end_time)?end_time.substring(0,2):""%>:<%= !"".equals(end_time)?end_time.substring(2,4):""%>
              </td>
            </tr>
            <tr>
              <td width="210" class="bbbg" style="padding-left:20px; font-weight:bold;">제목</td>
              <td width="574" style="padding-left:20px;"><%=title%></td>
            </tr>
            <tr>
              <td width="210" class="bbbg" style="padding-left:20px; font-weight:bold;">내용</td>
              <td width="574" style="padding-left:20px;"><%=StringUtil.newLineToBr(contents) %></td>
            </tr>
        </table></td>
      </tr>
      <tr>
        <td height="50" align="center">
			<a href="javascript:goList();"><img src="<%=root_path%>/images/bt_list.gif" alt="목록" /></a>
            <a href="javascript:goModifyForm('<%= schedule_id %>');"><img src="<%=root_path%>/images/bt_modify.gif" alt="수정" /></a>                   
            <a href="javascript:goDeleteForm('<%= schedule_id %>');"><img src="<%=root_path%>/images/bt_del.gif" alt="삭제" /></a>
         </td>
      </tr>
    </table>
    
</form>		