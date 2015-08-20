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
	ParamValue      menuParam  = new ParamValue();        
	MenuMaster      menuMgr    = new MenuMaster();
	MenuThirdMaster menuThirdMgr = new MenuThirdMaster();
	ResultSetValue  menuRset   = null;
	ResultSetValue  subjRset   = null;
    String conference_id             = StringUtil.defaultIfEmpty(request.getParameter("conference_id"      ), ""); 
			
	String menu_link_url = "/jsp/conference/register/register_write.jsp";
    
    String menu_name       = "회원 가입";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>::MICE::</title>
<link rel="stylesheet" type="text/css" href="<%=root_path%>/css/Layout_conference.css"/>
<script language="JavaScript" src="<%=root_path%>/js/common_conference.js"></script>
<script language="javascript" src="/common/js/common.js"></script>

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
			</script>
            <!--//메뉴 -->
        </div>
        <!--//왼쪽메뉴 -->    
        <!--오른쪽메뉴 -->    
        <div id="contents">
            <!--상단영역 -->
            <div class="top"> 
            <h2><%=menu_name%></h2>
            <span class="navi"><%="HOME > 컨퍼런스 메뉴 > " + menu_name %></span>
            </div> 
            <!--//상단영역 -->
            <!--테이블 -->
            <jsp:include page="<%=menu_link_url%>" flush="true">
	        <jsp:param name="b_mode"		value="ins"      />
	        <jsp:param name="conference_id"		value="<%=conference_id %>"      />
	      </jsp:include>    	
          <!--//테이블 -->	                  
      </div>    
        <!--//오른쪽메뉴 -->        
    </div>
</div>
<!--//전체 -->
</body>

</html>