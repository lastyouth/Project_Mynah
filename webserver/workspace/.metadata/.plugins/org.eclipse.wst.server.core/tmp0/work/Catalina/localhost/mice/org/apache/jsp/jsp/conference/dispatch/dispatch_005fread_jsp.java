/*
 * Generated by the Jasper component of Apache Tomcat
 * Version: Apache Tomcat/8.0.24
 * Generated at: 2015-08-14 08:08:29 UTC
 * Note: The last modified time of this file was set to
 *       the last modified time of the source file after
 *       generation to assist with modification tracking.
 */
package org.apache.jsp.jsp.conference.dispatch;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import sips.common.lib.util.*;
import sips.common.lib.value.*;
import sips.dept.contents._Message;
import sips.dept.menu.*;

public final class dispatch_005fread_jsp extends org.apache.jasper.runtime.HttpJspBase
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

      out.write('\r');
      out.write('\n');
 request.setCharacterEncoding("UTF-8"); 
      out.write('\r');
      out.write('\n');

	String root_path = request.getContextPath();

      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
  String formName = "frmBoard"; 
      out.write('\r');
      out.write('\n');

	ParamValue       parmValue = new ParamValue();          
	_Message        message     = new _Message();
    ResultSetValue   rset      = null;
    
	int    cpage              = (request.getParameter("cpage") == null || "".equals(request.getParameter("cpage"))) ? 0 : Integer.parseInt(request.getParameter("cpage"));   // 현재 페이지
    String searchTarget       = StringUtil.defaultIfEmpty(request.getParameter("searchTarget"), "");	      // 검색 조건 (제목/내용)
    String keyword            = StringUtil.defaultIfEmpty(request.getParameter("keyword"     ), "");        // 검색할 키워드
    String searchFlag         = StringUtil.defaultIfEmpty(request.getParameter("searchFlag"  ), "");        // 검색플래그
    String message_id       = StringUtil.defaultIfEmpty(request.getParameter("message_id"), "");
    String modeType           = StringUtil.defaultIfEmpty(request.getParameter("modeType"    ), "ins");
    String b_mode             = StringUtil.defaultIfEmpty(request.getParameter("b_mode"      ), ""); 
    String conference_id             = StringUtil.defaultIfEmpty(request.getParameter("conference_id"      ), ""); 
    
    String title  = "";
    String contents = "";
    String strMsg             = "";
    String reg_date             = "";
    String message_type             = "";
    String from_user_name             = "";
    String reply             = "";
    String from_user_cd             = "";

    parmValue.put("conference_id"    , conference_id  );
    parmValue.put("message_id"    , message_id  );
    rset = message.messageDetail(parmValue);
    String rlt = message.messageReceipt(parmValue);
    while( rset.next()){
    	message_id       = StringUtil.defaultIfEmpty(rset.getString("message_id"   ), ""); 
    	title     = StringUtil.defaultIfEmpty(rset.getString("title" ), "");
    	reg_date    = StringUtil.defaultIfEmpty(rset.getString("reg_date"), "");
    	message_type    = StringUtil.defaultIfEmpty(rset.getString("message_type"), "");
    	from_user_cd    = StringUtil.defaultIfEmpty(rset.getString("from_user_cd"), "");
    	from_user_name    = StringUtil.defaultIfEmpty(rset.getString("from_user_name"), "");
    	reply    = StringUtil.defaultIfEmpty(rset.getString("reply"), "");
    	reg_date    = StringUtil.defaultIfEmpty(rset.getString("reg_date"), "");
    	contents    = StringUtil.defaultIfEmpty(rset.getString("contents"), "");
    }

      out.write("\r\n");
      out.write("<script language=\"JavaScript\" type=\"text/JavaScript\">\r\n");
      out.write("<!--    \r\n");
      out.write("    function goList() {\r\n");
      out.write("        var frm = document.");
      out.print(formName);
      out.write(";\r\n");
      out.write("        \r\n");
      out.write("        frm.b_mode.value = \"L\";\r\n");
      out.write("        frm.action   = \"");
      out.print( request.getRequestURI() );
      out.write("\";\r\n");
      out.write("        frm.submit();\r\n");
      out.write("    }\r\n");
      out.write("\tfunction goRegitProc () {\r\n");
      out.write("\t    var frm = document.");
      out.print(formName);
      out.write(";\r\n");
      out.write("\t\r\n");
      out.write("\t    if (frm.title.value == \"\") {\r\n");
      out.write("\t        alert(\"제목을 입력하세요.\");\r\n");
      out.write("\t        frm.title.focus();\r\n");
      out.write("\t        return;\r\n");
      out.write("\t    }\r\n");
      out.write("\t\r\n");
      out.write("\t    if (frm.contents.value == \"\") {\r\n");
      out.write("\t        alert(\"내용을 입력하세요.\");\r\n");
      out.write("\t        frm.contents.focus();\r\n");
      out.write("\t        return;\r\n");
      out.write("\t    }\r\n");
      out.write("\t\r\n");
      out.write("\t    if( confirm(\"");
      out.print(strMsg);
      out.write(" 하시겠습니까?\")) {\r\n");
      out.write("\t        frm.action   = \"dispatch_proc.jsp\";\r\n");
      out.write("\t        frm.submit();\r\n");
      out.write("\t    }\r\n");
      out.write("\t}\r\n");
      out.write("    \r\n");
      out.write("\t/**\r\n");
      out.write("\t * 게시물 삭제\r\n");
      out.write("\t */\r\n");
      out.write("\tfunction goDeleteForm (message_id) { \r\n");
      out.write("\t    var frm = document.");
      out.print(formName);
      out.write(";\r\n");
      out.write("\t\r\n");
      out.write("\t    if( confirm(\"삭제 하시겠습니까?\")) {\r\n");
      out.write("\t        frm.message_id.value = message_id;\r\n");
      out.write("\t        frm.modeType.value = \"del\";\r\n");
      out.write("\t        frm.action   = \"dispatch_proc.jsp\";\r\n");
      out.write("\t        frm.submit();\r\n");
      out.write("\t    }\r\n");
      out.write("\t            \r\n");
      out.write("\t}    \r\n");
      out.write("  //-->\r\n");
      out.write("</script>\r\n");
      out.write("<form name=\"");
      out.print(formName);
      out.write("\" method=\"post\">\r\n");
      out.write("<input type=\"hidden\" name=\"cpage\"        value=\"");
      out.print( cpage       );
      out.write("\">\r\n");
      out.write("<input type=\"hidden\" name=\"message_id\" value=\"");
      out.print(message_id );
      out.write("\">  \r\n");
      out.write("<input type=\"hidden\" name=\"from_user_cd\" value=\"");
      out.print(from_user_cd );
      out.write("\">  \r\n");
      out.write("<input type=\"hidden\" name=\"conference_id\" value=\"");
      out.print(conference_id );
      out.write("\">  \r\n");
      out.write("<input type=\"hidden\" name=\"b_mode\"       value=\"");
      out.print(b_mode      );
      out.write("\">\r\n");
      out.write("<input type=\"hidden\" name=\"modeType\"     value=\"");
      out.print(modeType    );
      out.write("\">\r\n");
      out.write("\r\n");
      out.write("<table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\r\n");
      out.write("      \r\n");
      out.write("      <tr>\r\n");
      out.write("        <td valign=\"top\"><table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" style=\"border-top:2px solid #e0e0e0; border-bottom:2px solid #e0e0e0; width:784px;\">\r\n");
      out.write("            <tr>\r\n");
      out.write("              <td width=\"210\" class=\"bbbg\" style=\"padding-left:20px; font-weight:bold;\">등록일</td>\r\n");
      out.write("              <td width=\"574\" style=\"padding-left:20px;\">");
      out.print(reg_date);
      out.write("</td>\r\n");
      out.write("            </tr>\r\n");
      out.write("            <tr>\r\n");
      out.write("              <td width=\"210\" class=\"bbbg\" style=\"padding-left:20px; font-weight:bold;\">구분</td>\r\n");
      out.write("              <td width=\"574\" style=\"padding-left:20px;\">");
      out.print(message_type);
      out.write("</td>\r\n");
      out.write("            </tr>\r\n");
      out.write("            <tr>\r\n");
      out.write("              <td width=\"210\" class=\"bbbg\" style=\"padding-left:20px; font-weight:bold;\">발신자</td>\r\n");
      out.write("              <td width=\"574\" style=\"padding-left:20px;\">");
      out.print(from_user_name);
      out.write("</td>\r\n");
      out.write("            </tr>\r\n");
      out.write("            <tr>\r\n");
      out.write("              <td width=\"210\" class=\"bbbg\" style=\"padding-left:20px; font-weight:bold;\">제목</td>\r\n");
      out.write("              <td width=\"574\" style=\"padding-left:20px;\">");
      out.print(title);
      out.write("</td>\r\n");
      out.write("            </tr>\r\n");
      out.write("            <tr>\r\n");
      out.write("              <td width=\"210\" class=\"bbbg\" style=\"padding-left:20px; font-weight:bold;\">내용</td>\r\n");
      out.write("              <td width=\"574\" style=\"padding-left:20px;\">");
      out.print(StringUtil.newLineToBr(contents) );
      out.write("</td>\r\n");
      out.write("            </tr>\r\n");
      out.write("            <tr>\r\n");
      out.write("              <td width=\"210\" class=\"bbbg\" style=\"padding-left:20px; font-weight:bold;\">상태</td>\r\n");
      out.write("              <td width=\"574\" style=\"padding-left:20px;\">");
      out.print(reply);
      out.write("</td>\r\n");
      out.write("            </tr>\r\n");
      out.write("        </table></td>\r\n");
      out.write("      </tr>\r\n");
      out.write("      <tr>\r\n");
      out.write("        <td height=\"50\" align=\"center\">\r\n");
      out.write("\t\t\t<a href=\"javascript:goList();\"><img src=\"");
      out.print(root_path);
      out.write("/images/bt_list.gif\" alt=\"목록\" /></a>             \r\n");
      out.write("            <a href=\"javascript:goDeleteForm('");
      out.print( message_id );
      out.write("');\"><img src=\"");
      out.print(root_path);
      out.write("/images/bt_del.gif\" alt=\"삭제\" /></a>\r\n");
      out.write("         </td>\r\n");
      out.write("      </tr>\r\n");
      out.write("    </table>\r\n");
      out.write("    \r\n");
      out.write("</form>\t\t");
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
