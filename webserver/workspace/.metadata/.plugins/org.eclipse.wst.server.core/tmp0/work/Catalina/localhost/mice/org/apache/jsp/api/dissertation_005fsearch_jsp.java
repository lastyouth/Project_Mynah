/*
 * Generated by the Jasper component of Apache Tomcat
 * Version: Apache Tomcat/8.0.24
 * Generated at: 2015-08-14 07:59:24 UTC
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
import sips.dept.contents.Member;

public final class dissertation_005fsearch_jsp extends org.apache.jasper.runtime.HttpJspBase
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
    _jspx_imports_classes.add("sips.dept.contents.Member");
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

      out.write("<?xml version=\"1.0\" encoding=\"utf-8\"?> \r\n");
      out.write("<ROOT>\r\n");

    ParamValue     parmValue = new ParamValue(); 
	Member         member    = new Member();
    ResultSetValue rset      = null;

    String  conference_id     = request.getParameter("conference_id");
    String  search_flag     = request.getParameter("search_flag");
    String  keyword     = StringUtil.defaultIfEmpty(request.getParameter("keyword"),""); 
    if (!"".equals(keyword))
    	keyword     = new String(keyword.getBytes("8859_1"), "utf-8");
    String  session_id     = request.getParameter("session_id");
    //System.out.println("keyword---->"+keyword);    

    parmValue.put("conference_id", conference_id);
    parmValue.put("search_flag", search_flag);
    parmValue.put("keyword", keyword);
    parmValue.put("session_id", session_id);
    rset = member.getApiDissertationSearch(parmValue); 
    while(rset.next()) {
    	out.println("		<DISSERTATION_SEARCH>");
    	out.println("			<BINDER_ID>"+rset.getString("binder_id")+"</BINDER_ID>");
    	if( "presenter".equals(search_flag)) {
    		out.println("			<AGEND_ID>"+rset.getString("agenda_id")+"</AGEND_ID>");
    		out.println("			<PRESENTER>"+rset.getString("presenter")+"</PRESENTER>");
    	}else if( "topic_title".equals(search_flag)) {
    		out.println("			<TOPIC_ID>"+rset.getString("topic_id")+"</TOPIC_ID>");
    	}
    	out.println("			<USER_CD>"+rset.getString("user_cd")+"</USER_CD>");
    	out.println("			<USER_NAME>"+rset.getString("user_name")+"</USER_NAME>");
    	out.println("			<WRITER>"+rset.getString("writer")+"</WRITER>");
		out.println("			<TOPIC_TITLE>"+rset.getString("topic_title")+"</TOPIC_TITLE>");
    	out.println("			<BINDER_TITLE>"+rset.getString("binder_title")+"</BINDER_TITLE>");
    	out.println("		</DISSERTATION_SEARCH>");
    }

      out.write("\r\n");
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
