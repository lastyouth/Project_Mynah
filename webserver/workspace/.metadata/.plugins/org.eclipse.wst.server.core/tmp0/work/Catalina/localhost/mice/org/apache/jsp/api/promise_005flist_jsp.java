/*
 * Generated by the Jasper component of Apache Tomcat
 * Version: Apache Tomcat/8.0.24
 * Generated at: 2015-08-14 08:38:13 UTC
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
import sips.dept.contents._Message;

public final class promise_005flist_jsp extends org.apache.jasper.runtime.HttpJspBase
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
    _jspx_imports_classes.add("sips.dept.contents._Message");
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
	_Message         message    = new _Message();
    ResultSetValue rset      = null;
    ResultSetValue rset2      = null;
    ResultSetValue rset3      = null;


    String  conference_id     = request.getParameter("conference_id");
    String  user_cd   = request.getParameter("user_cd");


    parmValue.put("conference_id", conference_id);
    parmValue.put("user_cd", user_cd);
    rset = message.getPromiseXMLList(parmValue);
 
    while(rset.next()) {
    	//rset3 = message.getToUserXMLList(parmValue);
    	//while(rset3.next()) {
    		//if (rset.getString("promise_id").equals(rset3.getString("promise_id")) && user_cd.equals(rset3.getString("to_user_cd")) && "y".equals(rset3.getString("use_yn"))){
		    	out.println("		<PROMISE_LIST>");
		    	out.println("			<PROMISE_ID>"+rset.getString("promise_id")+"</PROMISE_ID>");
		    	out.println("			<FROM_USER_NAME>"+rset.getString("from_user_name")+"</FROM_USER_NAME>");
		    	out.println("			<FROM_USER_CD>"+rset.getString("from_user_cd")+"</FROM_USER_CD>");

		        parmValue.put("promise_id", rset.getString("promise_id"));
		    	rset2 = message.getToUserXMLList(parmValue);
		    	while(rset2.next()) {
			    	if (rset.getString("promise_id").equals(rset2.getString("promise_id")) && !rset2.getString("to_user_cd").equals(rset.getString("from_user_cd"))){
				    	out.println("			<TO_USER>");
				    	out.println("				<TO_USER_NAME>"+rset2.getString("to_user_name")+"</TO_USER_NAME>");
				    	out.println("				<TO_USER_CD>"+rset2.getString("to_user_cd")+"</TO_USER_CD>");
				    	out.println("			</TO_USER>");
			    	}
		    	}
		    	out.println("			<CANCLE_STAT>"+rset2.getString("cancle_yn")+"</CANCLE_STAT>");
		    	out.println("			<PROMISE_DATE>"+rset.getString("promise_date")+"</PROMISE_DATE>");
		    	out.println("			<PROMISE_HOUR>"+rset.getString("promise_hour")+"</PROMISE_HOUR>");
		    	out.println("			<TITLE>"+rset.getString("title")+"</TITLE>");
		    	out.println("			<RECEIPT>"+rset.getString("receipt")+"</RECEIPT>");
		    	out.println("			<REG_DATE>"+rset.getString("reg_date")+"</REG_DATE>");
		    	out.println("		</PROMISE_LIST>");
    		//}
    	//}
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
