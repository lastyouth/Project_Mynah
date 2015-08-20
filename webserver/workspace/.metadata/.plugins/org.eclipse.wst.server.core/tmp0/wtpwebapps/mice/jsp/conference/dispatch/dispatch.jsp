<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<% request.setCharacterEncoding("UTF-8"); %> 
<%
	String root_path = request.getContextPath();
%>
<%@ page import = "sips.common.lib.util.*"   %>
<%@ page import = "sips.common.lib.value.*"  %>
<%@ page import = "sips.dept.menu.*"       %>
<%@ page import = "sips.common.lib.util.*" %>
<%@ page import = "sips.common.lib.value.*" %>
<%@ page import = "sips.dept.contents.*" %>
<%@ page import = "sips.dept.menu.*" %>
<%
/*////////////////세션 관리/////////////////////////*/
//String _authority   = (String)session.getAttribute("USE_AUTHORITY");
if ((String)session.getAttribute("USE_AUTHORITY")  ==  null){
	out.println("<script language='javascript'>alert('권한이 없습니다.');location = '/mice/jsp/conference/index.jsp';</script>");
}
/*////////////////세션 관리//////////////////////////*/
	

	ParamValue      menuParam  = new ParamValue();   
 
	String menu_link_url = "";
    String b_mode       = StringUtil.defaultIfEmpty(request.getParameter("b_mode"),"");   
    String conference_id       = (String)session.getAttribute("CONFERENCE_ID");   
    
    if (b_mode == null || "".equals(b_mode) || b_mode.equals("L"))
    	menu_link_url = "/jsp/conference/dispatch/dispatch_list.jsp";
    else  if (b_mode.equals("R"))
    	menu_link_url = "/jsp/conference/dispatch/dispatch_read.jsp";
    else  if (b_mode.equals("M"))
    	menu_link_url = "/jsp/conference/dispatch/dispatch_write.jsp";
    else  if (b_mode.equals("W"))
    	menu_link_url = "/jsp/conference/dispatch/dispatch_write.jsp";
    
    String menu_name       = "발신함";
     
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>::MICE::</title>
<link rel="stylesheet" type="text/css" href="<%=root_path%>/css/Layout_conference.css"/>
<script language="JavaScript" src="<%=root_path%>/js/common_conference.js"></script>
<link rel="stylesheet" href="http://code.jquery.com/ui/1.8.18/themes/base/jquery-ui.css" type="text/css" media="all" />
<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.7.1/jquery.min.js" type="text/javascript"></script>
<script src="http://code.jquery.com/ui/1.8.18/jquery-ui.min.js" type="text/javascript"></script>
<script>
$(function() {
  $( "#datepicker1, #datepicker2" ).datepicker({
    dateFormat: 'yy-mm-dd',
    prevText: '이전 달',
    nextText: '다음 달',
    monthNames: ['1월','2월','3월','4월','5월','6월','7월','8월','9월','10월','11월','12월'],
    monthNamesShort: ['1월','2월','3월','4월','5월','6월','7월','8월','9월','10월','11월','12월'],
    dayNames: ['일','월','화','수','목','금','토'],
    dayNamesShort: ['일','월','화','수','목','금','토'],
    dayNamesMin: ['일','월','화','수','목','금','토'],
    showMonthAfterYear: true,
    yearSuffix: '년'
  });
});
</script>

</head>
<body>

<!--전체 -->
<div id="main-wrapper1">
    <div id="main-wrapper">
        <!--상단 -->
        <%@ include file = "/jsp/conference/include/top.jsp"  %>
		<!--//상단 -->
        <!--왼쪽메뉴 -->
        <div id="left">
            <!--로그인창 -->
            <%@ include file="/jsp/conference/include/login.jsp" %>
            <!--//로그인창 -->   
            <!--메뉴 -->       
			<%@ include file="/jsp/conference/include/left_menu.jsp" %> 
			<script>
				leftMenuHandler('menu_1');
				leftMenuSelection(9,'');
			</script>
            <!--//메뉴 -->
        </div>
        <!--//왼쪽메뉴 -->    
        <!--오른쪽메뉴 -->    
        <div id="contents">
            <!--상단영역 -->
            <div class="top"> 
            <h2><%=menu_name%></h2>
            <span class="navi"><%="HOME > 컨퍼런스 > " + menu_name %></span>
            </div> 
            <!--//상단영역 -->
            <!--테이블 -->
            <jsp:include page="<%=menu_link_url%>" flush="true">
	        <jsp:param name="b_mode"		value="<%=b_mode%>"      />
	        <jsp:param name="conference_id"		value="<%=conference_id%>"      />
	      </jsp:include>    	
          <!--//테이블 -->	                  
      </div>    
        <!--//오른쪽메뉴 -->        
    </div>
</div>
<!--//전체 -->
</body>

</html>