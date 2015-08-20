/*
 * Generated by the Jasper component of Apache Tomcat
 * Version: Apache Tomcat/8.0.24
 * Generated at: 2015-08-14 07:53:39 UTC
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
import sips.dept.contents._Research;

public final class research_005fq_jsp extends org.apache.jasper.runtime.HttpJspBase
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
    _jspx_imports_classes.add("sips.dept.contents._Research");
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
	_Research         research    = new _Research();
    ResultSetValue rset      = null;
    int researchCnt = 0;
    int i = 0;
    String flag = "false";
    String research_num = "";

    String  conference_id     = StringUtil.defaultIfEmpty(request.getParameter("conference_id"      ), ""); 
    String  research_id     = StringUtil.defaultIfEmpty(request.getParameter("research_id"      ), ""); 
    String  user_cd     = StringUtil.defaultIfEmpty(request.getParameter("user_cd"      ), ""); 
    String  title     = StringUtil.defaultIfEmpty(request.getParameter("title"      ), ""); 
    if (!"".equals(title))
    	title     = new String(title.getBytes("8859_1"), "utf-8");
    //System.out.println("contents---->"+contents);    

    parmValue.put("user_cd", user_cd);
    if (!"".equals(title)){
        parmValue.put("conference_id", conference_id);
	    parmValue.put("title", title);
	    researchCnt = research.inserResearch_q_insert(parmValue); 
	    if (researchCnt > 0 ){

      out.write("<?xml version=\"1.0\" encoding=\"utf-8\"?> \r\n");
      out.write(" \t<ROOT>\r\n");
      out.write(" \t\t<RESEARCH_NUMBER>");
      out.print(researchCnt);
      out.write("</RESEARCH_NUMBER>\r\n");
      out.write(" \t\t<RESEARCH_TITLE>");
      out.print(title);
      out.write("</RESEARCH_TITLE>\r\n");
      out.write("\t</ROOT> \r\n");
	}else{

      out.write("<?xml version=\"1.0\" encoding=\"utf-8\"?> \r\n");
      out.write("<ROOT>\r\n");
      out.write("\t<RESULT>false</RESULT>\r\n");
      out.write("</ROOT>\r\n");
	} 
    }else{
        parmValue.put("research_id", research_id);
    	rset =  research.inserResearch_q_list(parmValue); 

      out.write("<?xml version=\"1.0\" encoding=\"utf-8\"?> \r\n");
      out.write("<ROOT>\r\n");
      out.write("\t<RESEARCH>\r\n");

		while(rset.next()) {
			if ( i == 0){
				out.println("			<RESEARCH_NUMBER>"+rset.getString("research_num")+"</RESEARCH_NUMBER>");
				out.println("			<RESEARCH_id>"+research_id+"</RESEARCH_id>");
				out.println("			<RESEARCH_TITLE>"+rset.getString("title")+"</RESEARCH_TITLE>");
		    	out.println("			<RESEARCH_STAT>"+rset.getString("stat")+"</RESEARCH_STAT>");
		    	out.println("			<USER_CD>"+rset.getString("user_cd")+"</USER_CD>");
			}
		    	out.println("			<QUESTION_LIST>");
		    	out.println("				<QUESTION_ID>"+StringUtil.defaultIfEmpty(rset.getString("research_q_id"), "")+"</QUESTION_ID>");
		    	out.println("				<QUESTION_NUM>"+StringUtil.defaultIfEmpty(rset.getString("q_num"), "")+"</QUESTION_NUM>");
		    	out.println("				<QUESTION_TITLE>"+StringUtil.defaultIfEmpty(rset.getString("q_title"), "")+"</QUESTION_TITLE>");
		    	out.println("			</QUESTION_LIST>");
		    	i++;
		}

      out.write("\r\n");
      out.write("\t</RESEARCH>\r\n");
      out.write("</ROOT>\r\n");
 } 
      out.write("\r\n");
      out.write("\t\t \r\n");
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