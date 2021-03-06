/*
 * Generated by the Jasper component of Apache Tomcat
 * Version: Apache Tomcat/8.0.24
 * Generated at: 2015-08-14 07:52:41 UTC
 * Note: The last modified time of this file was set to
 *       the last modified time of the source file after
 *       generation to assist with modification tracking.
 */
package org.apache.jsp.api;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import sips.common.lib.value.*;
import sips.common.lib.util.*;
import sips.dept.contents._Member;

public final class authoriry_jsp extends org.apache.jasper.runtime.HttpJspBase
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
    _jspx_imports_packages.add("javax.servlet.jsp");
    _jspx_imports_classes = new java.util.HashSet<>();
    _jspx_imports_classes.add("sips.dept.contents._Member");
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


    ParamValue     parmValue = new ParamValue(); 
	_Member         member     = new _Member();
    ResultSetValue rset      = null;
    ResultSetValue rsetP      = null;

    String  platform     = request.getParameter("platform");
    String  user_cd   = request.getParameter("user_cd");
    String  push_key   = request.getParameter("push_key");

    String conference_name   = "";
    String conference_id   = "";
    String start_date   = "";
    String end_date   = "";
    String authoriry_1   = "false";
    String authoriry_2   = "false";
    String authoriry_3   = "false";
    String authoriry_4   = "false";
    String authoriry_5   = "false";
    String authoriry_6   = "false";
    String authoriry_7   = "false";
    String authoriry_8   = "false";
    String authoriry_9   = "false";
    String banner_image   = "";
    String qr_image   = "";
    String info_image   = "";
    String research_authoriry = "";
    String question_authority = "";
    
    parmValue.put("user_cd", user_cd);
    parmValue.put("platform", platform);
    parmValue.put("push_key", push_key);
    rset = member.getAuthority(parmValue);

    if(rset.next()) {
    	conference_id = rset.getString("conference_id");
    	conference_name = rset.getString("conference_name");
    	start_date = rset.getString("start_date");
    	end_date = rset.getString("end_date");
    	authoriry_1 = rset.getString("authoriry_1");
    	authoriry_2 = rset.getString("authoriry_2");
    	authoriry_3 = rset.getString("authoriry_3");
    	authoriry_4 = rset.getString("authoriry_4");
    	authoriry_5 = rset.getString("authoriry_5");
    	authoriry_6 = rset.getString("authoriry_6");
    	authoriry_7 = rset.getString("authoriry_7");
    	authoriry_8 = rset.getString("authoriry_8");
    	authoriry_9 = rset.getString("authoriry_9");
    	banner_image = rset.getString("banner_image");
    	qr_image = rset.getString("qr_images");
    	info_image = rset.getString("info_image");
    	research_authoriry = rset.getString("research_authoriry");
    	question_authority = rset.getString("question_authority");
    }

      out.write("<?xml version=\"1.0\" encoding=\"utf-8\"?> \r\n");
      out.write("<ROOT>\r\n");
      out.write("\t<LOGIN>\r\n");
      out.write("  \t\t<CONFERENCE_ID>");
      out.print(conference_id);
      out.write("</CONFERENCE_ID>\r\n");
      out.write("  \t\t<CONFERENCE_NAME>");
      out.print(conference_name);
      out.write("</CONFERENCE_NAME>\r\n");
      out.write("  \t\t<START_DATE>");
      out.print(start_date);
      out.write("</START_DATE>\r\n");
      out.write("  \t\t<END_DATE>");
      out.print(end_date);
      out.write("</END_DATE>\r\n");
      out.write("  \t\t<CONFERENCE_BANNER>/mice/upload/conference/");
      out.print(banner_image);
      out.write("</CONFERENCE_BANNER>\r\n");
      out.write("  \t\t<CONFERENCE_QR_IMAGE>/mice/upload/qr/");
      out.print(qr_image);
      out.write("</CONFERENCE_QR_IMAGE>\r\n");
      out.write("  \t\t<CONFERENCE_INFO_IMAGE>/mice/upload/conference/");
      out.print(info_image);
      out.write("</CONFERENCE_INFO_IMAGE>\r\n");
      out.write("  \t\t<RESEARCH_AUTHORITY>");
      out.print(research_authoriry);
      out.write("</RESEARCH_AUTHORITY>\r\n");
      out.write("  \t\t<QUESTION_AUTHORITY>");
      out.print(question_authority);
      out.write("</QUESTION_AUTHORITY>\r\n");
      out.write("  \t\t<AUTHORITY>\r\n");
      out.write("  \t\t\t<AGENDA>");
      out.print(authoriry_1);
      out.write("</AGENDA>\r\n");
      out.write("  \t\t\t<BINDER>");
      out.print(authoriry_2);
      out.write("</BINDER>\r\n");
      out.write("  \t\t\t<SEARCH>");
      out.print(authoriry_3);
      out.write("</SEARCH>\r\n");
      out.write("  \t\t\t<MESSAGE>");
      out.print(authoriry_4);
      out.write("</MESSAGE>\r\n");
      out.write("  \t\t\t<MYBRIEFCASE>");
      out.print(authoriry_5);
      out.write("</MYBRIEFCASE>\r\n");
      out.write("  \t\t\t<MAP>");
      out.print(authoriry_6);
      out.write("</MAP>\r\n");
      out.write("  \t\t\t<RESEARCH>");
      out.print(authoriry_7);
      out.write("</RESEARCH>\r\n");
      out.write("  \t\t\t<CONFIGURATION>");
      out.print(authoriry_8);
      out.write("</CONFIGURATION>\r\n");
      out.write("  \t\t\t<BARCODE>");
      out.print(authoriry_9);
      out.write("</BARCODE>\r\n");
      out.write("  \t\t</AUTHORITY>\r\n");
      out.write("  \t</LOGIN>\r\n");
      out.write("</ROOT>");
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
