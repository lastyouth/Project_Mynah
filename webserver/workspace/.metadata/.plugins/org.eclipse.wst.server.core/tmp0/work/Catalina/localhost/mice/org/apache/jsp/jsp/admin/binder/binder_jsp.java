/*
 * Generated by the Jasper component of Apache Tomcat
 * Version: Apache Tomcat/8.0.24
 * Generated at: 2015-08-14 08:09:10 UTC
 * Note: The last modified time of this file was set to
 *       the last modified time of the source file after
 *       generation to assist with modification tracking.
 */
package org.apache.jsp.jsp.admin.binder;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import sips.common.lib.util.*;
import sips.common.lib.value.*;
import sips.dept.menu.*;
import sips.common.lib.util.*;
import sips.common.lib.value.*;
import sips.dept.contents.*;
import sips.dept.menu.*;

public final class binder_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent,
                 org.apache.jasper.runtime.JspSourceImports {

  private static final javax.servlet.jsp.JspFactory _jspxFactory =
          javax.servlet.jsp.JspFactory.getDefaultFactory();

  private static java.util.Map<java.lang.String,java.lang.Long> _jspx_dependants;

  static {
    _jspx_dependants = new java.util.HashMap<java.lang.String,java.lang.Long>(4);
    _jspx_dependants.put("/jsp/admin/include/left_menu.jsp", Long.valueOf(1439532670578L));
    _jspx_dependants.put("/jsp/admin/include/login.jsp", Long.valueOf(1439532670485L));
    _jspx_dependants.put("/jsp/admin/include/top.jsp", Long.valueOf(1439532670487L));
    _jspx_dependants.put("/jsp/admin/include/left_menu_admin.jsp", Long.valueOf(1439532670619L));
  }

  private static final java.util.Set<java.lang.String> _jspx_imports_packages;

  private static final java.util.Set<java.lang.String> _jspx_imports_classes;

  static {
    _jspx_imports_packages = new java.util.HashSet<>();
    _jspx_imports_packages.add("sips.common.lib.util");
    _jspx_imports_packages.add("javax.servlet");
    _jspx_imports_packages.add("sips.common.lib.value");
    _jspx_imports_packages.add("javax.servlet.http");
    _jspx_imports_packages.add("sips.dept.contents");
    _jspx_imports_packages.add("sips.dept.menu");
    _jspx_imports_packages.add("javax.servlet.jsp");
    _jspx_imports_classes = null;
  }

  private javax.el.ExpressionFactory _el_expressionfactory;
  private org.apache.tomcat.InstanceManager _jsp_instancemanager;

  public java.util.Map<java.lang.String,java.lang.Long> getDependants() {
    return _jspx_dependants;
  }

  public java.util.Set<java.lang.String> getPackageImports() {
    return _jspx_imports_packages;
  }

  public java.util.Set<java.lang.String> getClassImports() {
    return _jspx_imports_classes;
  }

  public void _jspInit() {
    _el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
    _jsp_instancemanager = org.apache.jasper.runtime.InstanceManagerFactory.getInstanceManager(getServletConfig());
  }

  public void _jspDestroy() {
  }

  public void _jspService(final javax.servlet.http.HttpServletRequest request, final javax.servlet.http.HttpServletResponse response)
        throws java.io.IOException, javax.servlet.ServletException {

final java.lang.String _jspx_method = request.getMethod();
if (!"GET".equals(_jspx_method) && !"POST".equals(_jspx_method) && !"HEAD".equals(_jspx_method) && !javax.servlet.DispatcherType.ERROR.equals(request.getDispatcherType())) {
response.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED, "JSPs only permit GET POST or HEAD");
return;
}

    final javax.servlet.jsp.PageContext pageContext;
    javax.servlet.http.HttpSession session = null;
    final javax.servlet.ServletContext application;
    final javax.servlet.ServletConfig config;
    javax.servlet.jsp.JspWriter out = null;
    final java.lang.Object page = this;
    javax.servlet.jsp.JspWriter _jspx_out = null;
    javax.servlet.jsp.PageContext _jspx_page_context = null;


    try {
      response.setContentType("text/html; charset=UTF-8");
      pageContext = _jspxFactory.getPageContext(this, request, response,
      			null, true, 8192, true);
      _jspx_page_context = pageContext;
      application = pageContext.getServletContext();
      config = pageContext.getServletConfig();
      session = pageContext.getSession();
      out = pageContext.getOut();
      _jspx_out = out;

      out.write('\r');
      out.write('\n');
 request.setCharacterEncoding("UTF-8"); 
      out.write(' ');
      out.write('\r');
      out.write('\n');

	String root_path = request.getContextPath();

      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");

/*////////////////세션 관리/////////////////////////*/
String matchAuth = "1";
boolean _hasAuthAdmin   = false;
String _authority   = (String)session.getAttribute("USE_AUTHORITY");
System.out.println("Authority:"+_authority);
if (_authority  ==  null){
	out.println("<script language='javascript'>alert('관리자 권한이 없습니다.');location = '/mice/jsp/admin/index.jsp';</script>");
}else if (!"".equals(_authority) && (_authority.matches("super") || _authority.matches("admin") )) _hasAuthAdmin = true;
 if(_hasAuthAdmin == false) { 
	out.println("<script language='javascript'>alert('관리자 권한이 없습니다.');history.back();</script>");
	return;
}
/*////////////////세션 관리//////////////////////////*/
	

	ParamValue      menuParam  = new ParamValue();   
 
	String menu_link_url = "";
    String b_mode       = StringUtil.defaultIfEmpty(request.getParameter("b_mode"),"");   
    String conference_id       = StringUtil.defaultIfEmpty(request.getParameter("conference_id"),"");   
    
    if (b_mode.equals("W"))
    	menu_link_url = "/jsp/admin/binder/binder_write.jsp";
    else if (b_mode == null || "".equals(b_mode) || b_mode.equals("L"))
    	menu_link_url = "/jsp/admin/binder/binder_list.jsp";
    else  if (b_mode.equals("R"))
    	menu_link_url = "/jsp/admin/binder/binder_read.jsp";
    else  if (b_mode.equals("M"))
    	menu_link_url = "/jsp/admin/binder/binder_write.jsp";
    
    String menu_name       = "바인더 관리";
    
    b_mode = (b_mode == null || "".equals(b_mode)) ? "L" : b_mode;
     

      out.write("\r\n");
      out.write("<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">\r\n");
      out.write("<html xmlns=\"http://www.w3.org/1999/xhtml\">\r\n");
      out.write("<head>\r\n");
      out.write("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\" />\r\n");
      out.write("<title>::MICE::</title>\r\n");
      out.write("<link rel=\"stylesheet\" type=\"text/css\" href=\"");
      out.print(root_path);
      out.write("/css/Layout.css\"/>\r\n");
 if (_authority.matches("super")){ 
      out.write("\r\n");
      out.write("\t<script language=\"JavaScript\" src=\"");
      out.print(root_path);
      out.write("/js/common.js\"></script>\r\n");
 }else{ 
      out.write("\r\n");
      out.write("\t<script language=\"JavaScript\" src=\"");
      out.print(root_path);
      out.write("/js/common_admin.js\"></script>\r\n");
 }
      out.write("\r\n");
      out.write("<link rel=\"stylesheet\" href=\"http://code.jquery.com/ui/1.8.18/themes/base/jquery-ui.css\" type=\"text/css\" media=\"all\" />\r\n");
      out.write("<script src=\"http://ajax.googleapis.com/ajax/libs/jquery/1.7.1/jquery.min.js\" type=\"text/javascript\"></script>\r\n");
      out.write("<script src=\"http://code.jquery.com/ui/1.8.18/jquery-ui.min.js\" type=\"text/javascript\"></script>\r\n");
      out.write("<script>\r\n");
      out.write("$(function() {\r\n");
      out.write("  $( \"#datepicker1, #datepicker2\" ).datepicker({\r\n");
      out.write("    dateFormat: 'yy-mm-dd',\r\n");
      out.write("    prevText: '이전 달',\r\n");
      out.write("    nextText: '다음 달',\r\n");
      out.write("    monthNames: ['1월','2월','3월','4월','5월','6월','7월','8월','9월','10월','11월','12월'],\r\n");
      out.write("    monthNamesShort: ['1월','2월','3월','4월','5월','6월','7월','8월','9월','10월','11월','12월'],\r\n");
      out.write("    dayNames: ['일','월','화','수','목','금','토'],\r\n");
      out.write("    dayNamesShort: ['일','월','화','수','목','금','토'],\r\n");
      out.write("    dayNamesMin: ['일','월','화','수','목','금','토'],\r\n");
      out.write("    showMonthAfterYear: true,\r\n");
      out.write("    yearSuffix: '년'\r\n");
      out.write("  });\r\n");
      out.write("});\r\n");
      out.write("</script>\r\n");
      out.write("\r\n");
      out.write("</head>\r\n");
      out.write("<body>\r\n");
      out.write("\r\n");
      out.write("<!--전체 -->\r\n");
      out.write("<div id=\"main-wrapper1\">\r\n");
      out.write("    <div id=\"main-wrapper\">\r\n");
      out.write("        <!--상단 -->\r\n");
      out.write("        ");
      out.write('\r');
      out.write('\n');
 
request.setCharacterEncoding("UTF-8"); 
String rootPath3 = request.getContextPath();

      out.write("\r\n");
      out.write("<div id=\"Mtop\">\r\n");
      out.write("\t<h1><img src=\"");
      out.print(rootPath3);
      out.write("/images/ci.gif\" alt=\"dhsp\" /></h1>\r\n");
      out.write("\t<span class=\"Gomain\"><img src=\"");
      out.print(rootPath3);
      out.write("/images/bt_gohome.gif\" alt=\"홈페이지바로가기\" /></span>\r\n");
      out.write("</div>");
      out.write("\r\n");
      out.write("\t\t<!--//상단 -->\r\n");
      out.write("        <!--왼쪽메뉴 -->\r\n");
      out.write("        <div id=\"left\">\r\n");
      out.write("            <!--로그인창 -->\r\n");
      out.write("            ");
      out.write('\r');
      out.write('\n');
 
request.setCharacterEncoding("UTF-8");
String rootPath2 = request.getContextPath();

      out.write("\r\n");
      out.write("<div class=\"login\">\r\n");
      out.write("\t<ul>\r\n");
      out.write("\t\t<li class=\"top\"></li>\r\n");
      out.write("\t\t<!--내용 -->\r\n");
      out.write("\t\t<li class=\"bg\">\r\n");
      out.write("\t\t\t<span class=\"pBlue\"> ");
      out.print((String)session.getAttribute("USER_NAME") );
      out.write(" 관리자님 </span><br>\r\n");
      out.write("\t\t\t<img src=\"");
      out.print(rootPath2);
      out.write("/images/bt_email.gif\" alt=\"이메일\" style=\"margin-right:5px; margin-top:10px;\">\r\n");
      out.write("\t\t\t<a href=\"");
      out.print(rootPath2);
      out.write("/jsp/admin/logout_proc.jsp\"><img src=\"");
      out.print(rootPath2);
      out.write("/images/bt_logout.gif\" alt=\"로그아웃\" style=\"margin-right:5px; margin-top:10px;\"></a>\r\n");
      out.write("\t\t</li>\r\n");
      out.write("\t\t<!--//내용 -->\r\n");
      out.write("\t\t<li class=\"bottom\"></li>\r\n");
      out.write("\t</ul>\r\n");
      out.write("</div>");
      out.write("\r\n");
      out.write("            <!--//로그인창 -->   \r\n");
      out.write("            <!--메뉴 -->       \r\n");
 if (_authority.matches("super")){ 
      out.write('\r');
      out.write('\n');
      out.write('	');
      out.write('\r');
      out.write('\n');
 
request.setCharacterEncoding("UTF-8"); 
String rootPath = request.getContextPath();

      out.write("\r\n");
      out.write("\r\n");
      out.write("<div id=\"left\">\r\n");
      out.write("\t<!--메뉴 -->       \r\n");
      out.write("\t<div class=\"menu\">\r\n");
      out.write("\t\t\t<div class=\"top\">Menu</div>\r\n");
      out.write("\t\t\t<div class=\"bg\">\r\n");
      out.write("\t\t\t\r\n");
      out.write("\t\t\t\t<!--대메뉴..비활성화일시엔 제목옆의 클래스가 off/활성화시엔on -->                    \t\r\n");
      out.write("\t\t\t\t<div class=\"smenu\">\r\n");
      out.write("\t\t\t\t\t<span id=\"menu_1\" class=\"on\"><a href=\"javascript:leftMenuHandler('menu_1');\"><b>컨퍼런스 관리</b></a></span>\r\n");
      out.write("\t\t\t\t\t\t<!--소메뉴 -->\r\n");
      out.write("\t\t\t\t\t\t<ul id=\"sub_menu_1\" class=\"2smenu\" style=\"display:none;\">\r\n");
      out.write("\t\t\t\t\t\t\t<li id=\"job_1\" class=\"soff\"><a href=\"javascript:leftMenuSelection(1,'");
      out.print(rootPath);
      out.write("/jsp/admin/member/member.jsp');\">회원관리</a></li> \r\n");
      out.write("\t\t\t\t\t\t\t<li id=\"job_2\" class=\"soff\"><a href=\"javascript:leftMenuSelection(2,'");
      out.print(rootPath);
      out.write("/jsp/admin/conference_info/conference_info.jsp');\">컨퍼런스 정보 관리</a></li>\r\n");
      out.write("\t\t\t\t\t\t\t<li id=\"job_3\" class=\"soff\"><a href=\"javascript:leftMenuSelection(3,'");
      out.print(rootPath);
      out.write("/jsp/admin/session/session.jsp');\">세션 관리</a></li> \r\n");
      out.write("\t\t\t\t\t\t\t<li id=\"job_4\" class=\"soff\"><a href=\"javascript:leftMenuSelection(4,'");
      out.print(rootPath);
      out.write("/jsp/admin/topic/topic.jsp');\">토픽 관리</a></li> \r\n");
      out.write("\t\t\t\t\t\t\t<li id=\"job_5\" class=\"soff\"><a href=\"javascript:leftMenuSelection(5,'");
      out.print(rootPath);
      out.write("/jsp/admin/appellation/appellation.jsp');\">호칭관리</a></li> \r\n");
      out.write("\t\t\t\t\t\t\t<li id=\"job_6\" class=\"soff\"><a href=\"javascript:leftMenuSelection(6,'");
      out.print(rootPath);
      out.write("/jsp/admin/binder/binder.jsp');\">바인더 관리</a></li> \r\n");
      out.write("\t\t\t\t\t\t\t<li id=\"job_7\" class=\"soff\"><a href=\"javascript:leftMenuSelection(7,'");
      out.print(rootPath);
      out.write("/jsp/admin/agenda/agenda.jsp');\">아젠다 관리</a></li> \r\n");
      out.write("\t\t\t\t\t\t\t<li id=\"job_8\" class=\"soff\"><a href=\"javascript:leftMenuSelection(8,'");
      out.print(rootPath);
      out.write("/jsp/admin/banner/banner.jsp');\">광고 관리</a></li>\r\n");
      out.write("\t\t\t\t\t\t\t<li id=\"job_9\" class=\"soff\"><a href=\"javascript:leftMenuSelection(9,'");
      out.print(rootPath);
      out.write("/jsp/admin/sponsor/sponsor.jsp');\">스폰서 관리</a></li>\r\n");
      out.write("\t\t\t\t\t\t\t<li id=\"job_10\" class=\"soff\"><a href=\"javascript:leftMenuSelection(10,'");
      out.print(rootPath);
      out.write("/jsp/admin/additional/additional.jsp');\">숙박/식당 관리</a></li>\r\n");
      out.write("\t\t\t\t\t\t\t<li id=\"job_11\" class=\"soff\"><a href=\"javascript:leftMenuSelection(11,'");
      out.print(rootPath);
      out.write("/jsp/admin/manufacturer/manufacturer.jsp');\">선물업체 관리</a></li>\r\n");
      out.write("\t\t\t\t\t\t\t<li id=\"job_12\" class=\"soff\"><a href=\"javascript:leftMenuSelection(12,'");
      out.print(rootPath);
      out.write("/jsp/admin/present/present.jsp');\">선물 관리</a></li>\r\n");
      out.write("\t\t\t\t\t\t\t<li id=\"job_13\" class=\"soff\"><a href=\"javascript:leftMenuSelection(13,'");
      out.print(rootPath);
      out.write("/jsp/admin/map/map.jsp');\">행사장맵 관리</a></li>\r\n");
      out.write("\t\t\t\t\t\t\t<li id=\"job_14\" class=\"soff\"><a href=\"javascript:leftMenuSelection(14,'");
      out.print(rootPath);
      out.write("/jsp/admin/help/help.jsp');\">고객문의 관리</a></li>\r\n");
      out.write("\t\t\t\t\t\t\t<li id=\"job_15\" class=\"soff\"><a href=\"javascript:leftMenuSelection(15,'");
      out.print(rootPath);
      out.write("/jsp/admin/qrcode/qrcode.jsp');\">QR 코드 관리</a></li>\r\n");
      out.write("\t\t\t\t\t\t\t<li id=\"job_16\" class=\"soff\"><a href=\"javascript:leftMenuSelection(16,'");
      out.print(rootPath);
      out.write("/jsp/admin/research/research.jsp');\">설문 관리</a></li>\r\n");
      out.write("\t\t\t\t\t\t</ul>                        \r\n");
      out.write("\t\t\t\t</div>\r\n");
      out.write("\t\t\t\t<!--//대메뉴..비활성화일시엔 제목옆의 클래스가 off/활성화시엔on -->      \r\n");
      out.write("\r\n");
      out.write("\t\t\t\t<!--대메뉴..비활성화일시엔 제목옆의 클래스가 off/활성화시엔on -->                    \t\r\n");
      out.write("\t\t\t\t<div class=\"smenu\">\r\n");
      out.write("\t\t\t\t\t<span id=\"menu_2\" class=\"on\"><a href=\"javascript:leftMenuHandler('menu_2');\"><b>관리자 메뉴</b></a></span>\r\n");
      out.write("\t\t\t\t\t\t<!--소메뉴 -->\r\n");
      out.write("\t\t\t\t\t\t<ul id=\"sub_menu_2\" class=\"2smenu\" style=\"display:none;\">\r\n");
      out.write("\t\t\t\t\t\t\t<li id=\"job_17\" class=\"son\"><a href=\"javascript:leftMenuSelection(17,'");
      out.print(rootPath);
      out.write("/jsp/admin/conference/conference.jsp');\">컨퍼런스 관리</a></li>\r\n");
      out.write("\t\t\t\t\t\t\t<li id=\"job_18\" class=\"soff\"><a href=\"javascript:leftMenuSelection(18,'");
      out.print(rootPath);
      out.write("/jsp/admin/nation/nation.jsp');\">국가관리</a></li>\r\n");
      out.write("\t\t\t\t\t\t</ul>\r\n");
      out.write("\t\t\t\t</div>\r\n");
      out.write("\t\t\t\t<!--//대메뉴..비활성화일시엔 제목옆의 클래스가 off/활성화시엔on --> \r\n");
      out.write(" \r\n");
      out.write("\t\t\t</div>                    \r\n");
      out.write("\t\t\t<div class=\"bottom\"></div>                    \r\n");
      out.write("\t</div>\r\n");
      out.write("\t<!--//메뉴 -->\r\n");
      out.write("</div>");
      out.write(' ');
      out.write('\r');
      out.write('\n');
 }else{ 
      out.write('\r');
      out.write('\n');
      out.write('	');
      out.write('\r');
      out.write('\n');
 
request.setCharacterEncoding("UTF-8"); 
String rootPath = request.getContextPath();

      out.write("\r\n");
      out.write("\r\n");
      out.write("<div id=\"left\">\r\n");
      out.write("\t<!--메뉴 -->       \r\n");
      out.write("\t<div class=\"menu\">\r\n");
      out.write("\t\t\t<div class=\"top\">Menu</div>\r\n");
      out.write("\t\t\t<div class=\"bg\">\r\n");
      out.write("\t\t\t\r\n");
      out.write("\t\t\t\t<!--대메뉴..비활성화일시엔 제목옆의 클래스가 off/활성화시엔on -->                    \t\r\n");
      out.write("\t\t\t\t<div class=\"smenu\">\r\n");
      out.write("\t\t\t\t\t<span id=\"menu_1\" class=\"on\"><a href=\"javascript:leftMenuHandler('menu_1');\"><b>컨퍼런스 관리</b></a></span>\r\n");
      out.write("\t\t\t\t\t\t<!--소메뉴 -->\r\n");
      out.write("\t\t\t\t\t\t<ul id=\"sub_menu_1\" class=\"2smenu\" style=\"display:none;\">\r\n");
      out.write("\t\t\t\t\t\t\t<li id=\"job_1\" class=\"soff\"><a href=\"javascript:leftMenuSelection(1,'");
      out.print(rootPath);
      out.write("/jsp/admin/member/member.jsp');\">회원관리</a></li> \r\n");
      out.write("\t\t\t\t\t\t\t<li id=\"job_2\" class=\"soff\"><a href=\"javascript:leftMenuSelection(2,'");
      out.print(rootPath);
      out.write("/jsp/admin/conference_info/conference_info.jsp');\">컨퍼런스 정보 관리</a></li>\r\n");
      out.write("\t\t\t\t\t\t\t<li id=\"job_3\" class=\"soff\"><a href=\"javascript:leftMenuSelection(3,'");
      out.print(rootPath);
      out.write("/jsp/admin/session/session.jsp');\">세션 관리</a></li> \r\n");
      out.write("\t\t\t\t\t\t\t<li id=\"job_4\" class=\"soff\"><a href=\"javascript:leftMenuSelection(4,'");
      out.print(rootPath);
      out.write("/jsp/admin/topic/topic.jsp');\">토픽 관리</a></li> \r\n");
      out.write("\t\t\t\t\t\t\t<li id=\"job_5\" class=\"soff\"><a href=\"javascript:leftMenuSelection(5,'");
      out.print(rootPath);
      out.write("/jsp/admin/appellation/appellation.jsp');\">호칭관리</a></li> \r\n");
      out.write("\t\t\t\t\t\t\t<li id=\"job_6\" class=\"soff\"><a href=\"javascript:leftMenuSelection(6,'");
      out.print(rootPath);
      out.write("/jsp/admin/binder/binder.jsp');\">바인더 관리</a></li> \r\n");
      out.write("\t\t\t\t\t\t\t<li id=\"job_7\" class=\"soff\"><a href=\"javascript:leftMenuSelection(7,'");
      out.print(rootPath);
      out.write("/jsp/admin/agenda/agenda.jsp');\">아젠다 관리</a></li> \r\n");
      out.write("\t\t\t\t\t\t\t<li id=\"job_8\" class=\"soff\"><a href=\"javascript:leftMenuSelection(8,'");
      out.print(rootPath);
      out.write("/jsp/admin/banner/banner.jsp');\">광고 관리</a></li>\r\n");
      out.write("\t\t\t\t\t\t\t<li id=\"job_9\" class=\"soff\"><a href=\"javascript:leftMenuSelection(9,'");
      out.print(rootPath);
      out.write("/jsp/admin/sponsor/sponsor.jsp');\">스폰서 관리</a></li>\r\n");
      out.write("\t\t\t\t\t\t\t<li id=\"job_10\" class=\"soff\"><a href=\"javascript:leftMenuSelection(10,'");
      out.print(rootPath);
      out.write("/jsp/admin/additional/additional.jsp');\">숙박/식당 관리</a></li>\r\n");
      out.write("\t\t\t\t\t\t\t<li id=\"job_11\" class=\"soff\"><a href=\"javascript:leftMenuSelection(11,'");
      out.print(rootPath);
      out.write("/jsp/admin/manufacturer/manufacturer.jsp');\">선물업체 관리</a></li>\r\n");
      out.write("\t\t\t\t\t\t\t<li id=\"job_12\" class=\"soff\"><a href=\"javascript:leftMenuSelection(12,'");
      out.print(rootPath);
      out.write("/jsp/admin/present/present.jsp');\">선물 관리</a></li>\r\n");
      out.write("\t\t\t\t\t\t\t<li id=\"job_13\" class=\"soff\"><a href=\"javascript:leftMenuSelection(13,'");
      out.print(rootPath);
      out.write("/jsp/admin/map/map.jsp');\">행사장맵 관리</a></li>\r\n");
      out.write("\t\t\t\t\t\t\t<li id=\"job_14\" class=\"soff\"><a href=\"javascript:leftMenuSelection(14,'");
      out.print(rootPath);
      out.write("/jsp/admin/help/help.jsp');\">고객문의 관리</a></li>\r\n");
      out.write("\t\t\t\t\t\t\t<li id=\"job_15\" class=\"soff\"><a href=\"javascript:leftMenuSelection(15,'");
      out.print(rootPath);
      out.write("/jsp/admin/qrcode/qrcode.jsp');\">QR 코드 관리</a></li>\r\n");
      out.write("\t\t\t\t\t\t\t<li id=\"job_16\" class=\"soff\"><a href=\"javascript:leftMenuSelection(16,'");
      out.print(rootPath);
      out.write("/jsp/admin/research/research.jsp');\">설문 관리</a></li>\r\n");
      out.write("\t\t\t\t\t\t</ul>                        \r\n");
      out.write("\t\t\t\t</div>\r\n");
      out.write("\t\t\t\t<!--//대메뉴..비활성화일시엔 제목옆의 클래스가 off/활성화시엔on -->  \r\n");
      out.write(" \r\n");
      out.write("\t\t\t</div>                    \r\n");
      out.write("\t\t\t<div class=\"bottom\"></div>                    \r\n");
      out.write("\t</div>\r\n");
      out.write("\t<!--//메뉴 -->\r\n");
      out.write("</div>");
      out.write(' ');
      out.write('\r');
      out.write('\n');
 }
      out.write("      \r\n");
      out.write("\t\t\t<script>\r\n");
      out.write("\t\t\t\tleftMenuHandler('menu_1');\r\n");
      out.write("\t\t\t\tleftMenuSelection(6,'');\r\n");
      out.write("\t\t\t</script>\r\n");
      out.write("            <!--//메뉴 -->\r\n");
      out.write("        </div>\r\n");
      out.write("        <!--//왼쪽메뉴 -->    \r\n");
      out.write("        <!--오른쪽메뉴 -->    \r\n");
      out.write("        <div id=\"contents\">\r\n");
      out.write("            <!--상단영역 -->\r\n");
      out.write("            <div class=\"top\"> \r\n");
      out.write("            <h2>");
      out.print(menu_name);
      out.write("</h2>\r\n");
      out.write("            <span class=\"navi\">");
      out.print("HOME > 관리자메뉴 > " + menu_name );
      out.write("</span>\r\n");
      out.write("            </div> \r\n");
      out.write("            <!--//상단영역 -->\r\n");
      out.write("            <!--테이블 -->\r\n");
      out.write("            ");
      org.apache.jasper.runtime.JspRuntimeLibrary.include(request, response, menu_link_url + ((menu_link_url).indexOf('?')>0? '&': '?') + org.apache.jasper.runtime.JspRuntimeLibrary.URLEncode("b_mode", request.getCharacterEncoding())+ "=" + org.apache.jasper.runtime.JspRuntimeLibrary.URLEncode(String.valueOf(b_mode), request.getCharacterEncoding()) + "&" + org.apache.jasper.runtime.JspRuntimeLibrary.URLEncode("conference_id", request.getCharacterEncoding())+ "=" + org.apache.jasper.runtime.JspRuntimeLibrary.URLEncode(String.valueOf(conference_id), request.getCharacterEncoding()), out, true);
      out.write("    \t\r\n");
      out.write("          <!--//테이블 -->\t                  \r\n");
      out.write("      </div>    \r\n");
      out.write("        <!--//오른쪽메뉴 -->        \r\n");
      out.write("    </div>\r\n");
      out.write("</div>\r\n");
      out.write("<!--//전체 -->\r\n");
      out.write("</body>\r\n");
      out.write("\r\n");
      out.write("</html>");
    } catch (java.lang.Throwable t) {
      if (!(t instanceof javax.servlet.jsp.SkipPageException)){
        out = _jspx_out;
        if (out != null && out.getBufferSize() != 0)
          try {
            if (response.isCommitted()) {
              out.flush();
            } else {
              out.clearBuffer();
            }
          } catch (java.io.IOException e) {}
        if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
        else throw new ServletException(t);
      }
    } finally {
      _jspxFactory.releasePageContext(_jspx_page_context);
    }
  }
}
