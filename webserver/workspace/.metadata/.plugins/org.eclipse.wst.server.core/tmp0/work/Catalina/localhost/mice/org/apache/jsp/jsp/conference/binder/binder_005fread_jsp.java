/*
 * Generated by the Jasper component of Apache Tomcat
 * Version: Apache Tomcat/8.0.24
 * Generated at: 2015-08-19 09:14:34 UTC
 * Note: The last modified time of this file was set to
 *       the last modified time of the source file after
 *       generation to assist with modification tracking.
 */
package org.apache.jsp.jsp.conference.binder;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import sips.common.lib.util.*;
import sips.common.lib.value.*;
import sips.dept.contents.Session;
import sips.dept.contents.Binder;
import sips.dept.contents.Conference;
import sips.dept.contents.Member;
import sips.dept.menu.*;

public final class binder_005fread_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent,
                 org.apache.jasper.runtime.JspSourceImports {

  private static final javax.servlet.jsp.JspFactory _jspxFactory =
          javax.servlet.jsp.JspFactory.getDefaultFactory();

  private static java.util.Map<java.lang.String,java.lang.Long> _jspx_dependants;

  private static final java.util.Set<java.lang.String> _jspx_imports_packages;

  private static final java.util.Set<java.lang.String> _jspx_imports_classes;

  static {
    _jspx_imports_packages = new java.util.HashSet<>();
    _jspx_imports_packages.add("sips.common.lib.util");
    _jspx_imports_packages.add("javax.servlet");
    _jspx_imports_packages.add("sips.common.lib.value");
    _jspx_imports_packages.add("javax.servlet.http");
    _jspx_imports_packages.add("sips.dept.menu");
    _jspx_imports_packages.add("javax.servlet.jsp");
    _jspx_imports_classes = new java.util.HashSet<>();
    _jspx_imports_classes.add("sips.dept.contents.Member");
    _jspx_imports_classes.add("sips.dept.contents.Conference");
    _jspx_imports_classes.add("sips.dept.contents.Binder");
    _jspx_imports_classes.add("sips.dept.contents.Session");
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

	String root_path = request.getContextPath();

      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
  String formName = "frmBoard"; 
      out.write('\r');
      out.write('\n');

	ParamValue       parmValue = new ParamValue();           
	Binder            binder     = new Binder();     
	Member            member     = new Member();  
	Conference            conference     = new Conference();
   
    ResultSetValue   rset      = null;
    ResultSetValue   rset2      = null;
    ResultSetValue   rset3      = null;

    String b_mode             = StringUtil.defaultIfEmpty(request.getParameter("b_mode"      ), ""); 
    String binder_id             = StringUtil.defaultIfEmpty(request.getParameter("binder_id"      ), ""); 
  
    
    		
    String modeType           = "";

    String binder_title    = "";
    String contents    = "";
    String  reg_date    = "";
    String  attached    = "";
    String  user_name    = "";
    String  session_title    = "";
    String  topic_title    = "";
    String  conference_name    = "";
    String strMsg   = "수정";
    String agenda_register = "";
    String result = "비발표자";
    String sel_topic_title = "";
    String sel_topic_id = "";
    String sel_session_id = "";
    String sel_session_title = "";
    String sel_conference_name = "";
    String sel_conference_id = "";
    String session_id = "";
    String topic_id = "";
    String session_name = "";
    String topic_name = "";
    String user_id = "";
    String writer = "";

    parmValue.put("binder_id"    , binder_id  );
    String conference_id    = binder.getConferenceId(parmValue);
    System.out.println("conference_id------------>"+conference_id);
    parmValue.put("conference_id"    , conference_id  );
    
    rset = binder.getBniderInfo(parmValue);
    while( rset.next()){
    	binder_id    = StringUtil.defaultIfEmpty(rset.getString("binder_id"    ), "");
    	binder_title    = StringUtil.defaultIfEmpty(rset.getString("binder_title"    ), "");
    	contents    = StringUtil.defaultIfEmpty(rset.getString("contents"    ), "");
    	reg_date    = StringUtil.defaultIfEmpty(rset.getString("reg_date"    ), "");
    	attached    = StringUtil.defaultIfEmpty(rset.getString("attached"    ), "");
    	user_name    = StringUtil.defaultIfEmpty(rset.getString("user_name"    ), "");
    	writer    = StringUtil.defaultIfEmpty(rset.getString("writer"    ), "");
    	session_title    = StringUtil.defaultIfEmpty(rset.getString("session_title"    ), "");
    	topic_title    = StringUtil.defaultIfEmpty(rset.getString("topic_title"    ), "");
    	conference_name    = StringUtil.defaultIfEmpty(rset.getString("conference_name"    ), "");
    	user_id    = StringUtil.defaultIfEmpty(rset.getString("user_id"    ), "");
    }
	
	
	
	rset2 = conference.getConferenceInfo(parmValue);
    while( rset2.next()){
    	conference_name    = StringUtil.defaultIfEmpty(rset2.getString("name"    ), "");
    }  
    

      out.write("\t\t\r\n");
      out.write("<script language=\"JavaScript\" type=\"text/JavaScript\">\r\n");
      out.write("<!--\r\n");
      out.write("    /**\r\n");
      out.write("     * 게시물 수정\r\n");
      out.write("     */\r\n");
      out.write("    function goModifyForm (binder_id ) {        \r\n");
      out.write("        var frm = document.");
      out.print(formName);
      out.write(";\r\n");
      out.write("        \r\n");
      out.write("        frm.binder_id.value = binder_id;\r\n");
      out.write("        frm.target=\"_self\";\r\n");
      out.write("        frm.b_mode.value = \"M\";\r\n");
      out.write("        frm.action = \"");
      out.print(request.getRequestURI());
      out.write("\";\r\n");
      out.write("        frm.submit();\r\n");
      out.write("    }  \r\n");
      out.write("\r\n");
      out.write("    /* page 이동 */\r\n");
      out.write("    function goList() {\r\n");
      out.write("        var frm = document.");
      out.print(formName);
      out.write(";\r\n");
      out.write("        \r\n");
      out.write("        frm.b_mode.value = \"L\";\r\n");
      out.write("        frm.target=\"_self\";\r\n");
      out.write("        frm.action = \"");
      out.print(request.getRequestURI());
      out.write("\";\r\n");
      out.write("        frm.submit();\r\n");
      out.write("    }\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("    /* 읽기 페이지로 이동 */\r\n");
      out.write("    function goDisplay(idx) {\r\n");
      out.write("        var frm = document.");
      out.print(formName);
      out.write(";\r\n");
      out.write("        \r\n");
      out.write("        frm.idx.value          = idx;\r\n");
      out.write("        frm.b_mode.value    = \"R\";\r\n");
      out.write("        frm.target=\"_self\";\r\n");
      out.write("        frm.action             = \"");
      out.print( request.getRequestURI() );
      out.write("\";\r\n");
      out.write("        frm.submit();\r\n");
      out.write("    }\r\n");
      out.write("\r\n");
      out.write("//-->\r\n");
      out.write("</script>\r\n");
      out.write("<form name=\"");
      out.print(formName);
      out.write("\" method=\"post\">\r\n");
      out.write("<input type=\"hidden\" name=\"b_mode\"       value=\"");
      out.print(b_mode      );
      out.write("\">\r\n");
      out.write("<input type=\"hidden\" name=\"modeType\"     value=\"");
      out.print(modeType    );
      out.write("\">\r\n");
      out.write("<input type=\"hidden\" name=\"binder_id\"      value=\"");
      out.print(binder_id);
      out.write("\">   \r\n");
      out.write("<input type=\"hidden\" name=\"conference_id\"      value=\"");
      out.print(conference_id);
      out.write("\">   \r\n");
      out.write("\t\r\n");
      out.write("    <table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\r\n");
      out.write("      <tr>\r\n");
      out.write("        <td valign=\"top\">\r\n");
      out.write("           <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" style=\"border-top:2px solid #e0e0e0; border-bottom:2px solid #e0e0e0; width:784px;\">\r\n");
      out.write("            <tr>\r\n");
      out.write("              <td width=\"210\" class=\"bbbg\" style=\"padding-left:20px; font-weight:bold;\">세션 제목</td>\r\n");
      out.write("              <td width=\"574\" style=\"padding-left:20px;\">");
      out.print(session_title);
      out.write("</td>\r\n");
      out.write("            </tr>\t\t\r\n");
      out.write("            <tr>\r\n");
      out.write("              <td width=\"210\" class=\"bbbg\" style=\"padding-left:20px; font-weight:bold;\">토픽 제목</td>\r\n");
      out.write("              <td width=\"574\" style=\"padding-left:20px;\">");
      out.print(topic_title);
      out.write("</td>\r\n");
      out.write("            </tr>\t\t\r\n");
      out.write("            <tr>\r\n");
      out.write("              <td class=\"bbbg\" style=\"padding-left:20px; font-weight:bold;\">회원명</td>\r\n");
      out.write("              <td style=\"padding-left:20px;\">");
      out.print(user_name);
      out.write("</td>\r\n");
      out.write("            </tr>\t\r\n");
      out.write("            <tr>\r\n");
      out.write("              <td class=\"bbbg\" style=\"padding-left:20px; font-weight:bold;\">제목</td>\r\n");
      out.write("              <td style=\"padding-left:20px;\">");
      out.print(binder_title);
      out.write("</td>\r\n");
      out.write("            </tr>\r\n");
      out.write("            <tr>\r\n");
      out.write("              <td class=\"bbbg\" style=\"padding-left:20px; font-weight:bold;\">저자</td>\r\n");
      out.write("              <td style=\"padding-left:20px;\">");
      out.print(writer);
      out.write("</td>\r\n");
      out.write("            </tr>\r\n");
      out.write("            <tr>\r\n");
      out.write("              <td class=\"bbbg\" style=\"padding-left:20px; font-weight:bold;\">내용</td>\r\n");
      out.write("              <td style=\"padding-left:20px;\">");
      out.print(StringUtil.newLineToBr(contents));
      out.write("</td>\r\n");
      out.write("            </tr>\r\n");
      out.write("            <tr>\r\n");
      out.write("              <td class=\"bbbg\" style=\"padding-left:20px; font-weight:bold;\">다운로드</td>\r\n");
      out.write("              <td style=\"padding-left:20px;\"><a href=\"/mice/upload/pdf/");
      out.print(attached);
      out.write('"');
      out.write('>');
      out.print(attached);
      out.write("</a></td>\r\n");
      out.write("            </tr>\r\n");
      out.write("        </table></td>\r\n");
      out.write("      </tr>\r\n");
      out.write("      <tr>\r\n");
      out.write("        <td height=\"50\" align=\"center\">\r\n");
      out.write("\t\t\t\t\t <a href=\"javascript:goList();\"><img src=\"/dhsp/images/bt_list.gif\" alt=\"목록\" /></a>\r\n");
      out.write("        </td>\r\n");
      out.write("      </tr>      \r\n");
      out.write("    </table>\r\n");
      out.write("</form>\t\t\r\n");
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
