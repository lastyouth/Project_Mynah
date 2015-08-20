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
String matchAuth = "1";
boolean _hasAuthAdmin   = false;
String _authority   = (String)session.getAttribute("USE_AUTHORITY");
System.out.println("Authority:"+_authority);
if (_authority  ==  null){
	out.println("<script language='javascript'>alert('관리자 권한이 없습니다.');location = '/mice/jsp/admin/index.jsp';</script>");
}else if (!"".equals(_authority) && _authority.matches("super")) _hasAuthAdmin = true;
 if(_hasAuthAdmin == false) { 
	out.println("<script language='javascript'>alert('관리자 권한이 없습니다.');history.back();</script>");
	return;
}
/*////////////////세션 관리//////////////////////////*/
	

	ParamValue      menuParam  = new ParamValue();        
	MenuMaster      menuMgr    = new MenuMaster();
	MenuThirdMaster menuThirdMgr = new MenuThirdMaster();
	ResultSetValue  menuRset   = null;
	ResultSetValue  subjRset   = null;
			
	String menu_link_url = "";
	 
    String b_mode       = StringUtil.defaultIfEmpty(request.getParameter("b_mode"),"");   
    
    if (b_mode.equals("W"))
    	menu_link_url = "/jsp/admin/register/register_write.jsp";
    else if (b_mode == null || "".equals(b_mode) || b_mode.equals("L"))
    	menu_link_url = "/jsp/admin/register/register_list.jsp";
    else  if (b_mode.equals("R"))
    	menu_link_url = "/jsp/admin/register/register_read.jsp";
    else  if (b_mode.equals("M"))
    	menu_link_url = "/jsp/admin/register/register_write.jsp";
    
    String menu_name       = "컨퍼런스 등록";
    
    b_mode = (b_mode == null || "".equals(b_mode)) ? "L" : b_mode;
     
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>::MICE::</title>
<link rel="stylesheet" type="text/css" href="<%=root_path%>/css/Layout.css"/>
<script language="JavaScript" src="<%=root_path%>/js/common.js"></script>
<script language="javascript" src="/common/js/common.js"></script>

</head>
<body>

<!--전체 -->
<div id="main-wrapper1">
    <div id="main-wrapper">
        <!--상단 -->
        <%@ include file = "/jsp/admin/include/top.jsp"  %>
		<!--//상단 -->
        <!--왼쪽메뉴 -->
        <div id="left">
            <!--로그인창 -->
            <%@ include file="/jsp/admin/include/login.jsp" %>
            <!--//로그인창 -->   
            <!--메뉴 -->       
            <%@ include file="/jsp/admin/include/left_menu.jsp" %> 
			<script>
				leftMenuHandler('menu_1');
				leftMenuSelection(2,'');
			</script>
            <!--//메뉴 -->
        </div>
        <!--//왼쪽메뉴 -->    
        <!--오른쪽메뉴 -->    
        <div id="contents">
            <!--상단영역 -->
            <div class="top"> 
            <h2><%=menu_name%></h2>
            <span class="navi"><%="HOME > 관리자메뉴 > " + menu_name %></span>
            </div> 
            <!--//상단영역 -->
            <!--테이블 -->
            <jsp:include page="<%=menu_link_url%>" flush="true">
	        <jsp:param name="b_mode"		value="<%=b_mode%>"      />
	      </jsp:include>    	
          <!--//테이블 -->	                  
      </div>    
        <!--//오른쪽메뉴 -->        
    </div>
</div>
<!--//전체 -->
</body>

</html>