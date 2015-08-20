/*
 * Generated by the Jasper component of Apache Tomcat
 * Version: Apache Tomcat/8.0.24
 * Generated at: 2015-08-14 08:08:24 UTC
 * Note: The last modified time of this file was set to
 *       the last modified time of the source file after
 *       generation to assist with modification tracking.
 */
package org.apache.jsp.jsp.conference.memo;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import sips.common.lib.util.*;
import sips.common.lib.value.*;
import sips.dept.contents._Memo;
import sips.dept.contents.Conference;

public final class memo_005flist_jsp extends org.apache.jasper.runtime.HttpJspBase
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
    _jspx_imports_classes.add("sips.dept.contents._Memo");
    _jspx_imports_classes.add("sips.dept.contents.Conference");
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

	ParamValue       parmValue = new ParamValue();        
	_Memo            memo     = new _Memo();
	Conference            conference     = new Conference();
    ResultSetValue   rset      = null;
    ResultSetValue   rset3      = null;
    
    /* 페이지 관련 파라미터 */
    String b_mode             = StringUtil.defaultIfEmpty(request.getParameter("b_mode"      ), ""); 
    String old_conference_id       = StringUtil.defaultIfEmpty(request.getParameter("conference_id"),"0");   
    int    totalCnt  = 0;                        // 총 게시물 갯수
	int    cpage     = (request.getParameter("cpage") == null || "".equals(request.getParameter("cpage"))) ? 0 : Integer.parseInt(request.getParameter("cpage"));   // 현재 페이지
    int    pageSize  = 10;                       // 한 페이지에 들어갈 게시물 갯수
	int    pageTotal = 0;                        // 총 페이지수
	int    listIndex = 0;
    
    String searchTarget       = StringUtil.defaultIfEmpty(request.getParameter("searchTarget"), "");  // 검색 조건 (제목/내용)
    String keyword            = StringUtil.defaultIfEmpty(request.getParameter("keyword"     ), "");  // 검색할 키워드
    String searchFlag         = StringUtil.defaultIfEmpty(request.getParameter("searchFlag"  ), "");  // 검색플래그
    
    String user_cd         = (String)session.getAttribute("USER_CD");
    String user_authority         = (String)session.getAttribute("USE_AUTHORITY");
    
    String conference_name = "";
    String memo_id = "";
    String title  = "";
    String contents    = "";
    String reg_date  = "";    
    
    DateUtil  dateUtil   = new DateUtil();    
    
    int defaultILine = 45;
    
    parmValue.put("searchTarget"        , searchTarget  );
    parmValue.put("keyword"             , keyword       );
    parmValue.put("searchFlag"          , searchFlag    );
    parmValue.put("CURRENT_PAGE"        , cpage         );       // 페이징에 꼭 필요
    parmValue.put("lineCnt"             , pageSize      );      
    parmValue.put("ORDER_BY"            , " memo.reg_date desc   "      );     
    
 // 컨퍼런스 셀렉트 리스트
    parmValue.put("user_cd"        , user_cd  );  
    parmValue.put("user_authority"        , user_authority  );  

    // 목록
    parmValue.put("conference_id"        , old_conference_id  );  
    rset = memo.getMemoList2(parmValue); 
   
    /*============= 2. 페이징 관련 셋팅 ================*/
    totalCnt  = memo.getMemoTotalRow(parmValue);             // 총 게시물 갯수
	pageTotal = ((totalCnt-1)/pageSize) ;               // 총 페이지수
	listIndex = totalCnt - (pageSize * (cpage));

      out.write("\r\n");
      out.write("<script language=\"JavaScript\" >\r\n");
      out.write("<!--\r\n");
      out.write("    /* 검색 */\r\n");
      out.write("    function goSearch() {\r\n");
      out.write("        board.searchFlag.value = \"OK\";\r\n");
      out.write("        board.cpage.value = \"0\";\r\n");
      out.write("        board.b_mode.value  = \"L\";\r\n");
      out.write("        board.target=\"_self\";\r\n");
      out.write("        board.action = \"");
      out.print( request.getRequestURI() );
      out.write("\";\r\n");
      out.write("        board.submit();\r\n");
      out.write("    }\r\n");
      out.write("\r\n");
      out.write("    /* 목록 페이지로 이동 */\r\n");
      out.write("    function goList() {\r\n");
      out.write("        board.cpage.value        = \"\";\r\n");
      out.write("        board.searchTarget.value = \"\";\r\n");
      out.write("        board.keyword.value      = \"\";\r\n");
      out.write("        board.b_mode.value       = \"L\";\r\n");
      out.write("        board.searchFlag.value   = \"\";\r\n");
      out.write("\r\n");
      out.write("        board.target=\"_self\";\r\n");
      out.write("        board.action = \"");
      out.print( request.getRequestURI() );
      out.write("\";\r\n");
      out.write("        board.submit();\r\n");
      out.write("    }\r\n");
      out.write("\r\n");
      out.write("    /* 쓰기 페이지로 이동 */\r\n");
      out.write("    function goWriteForm() {\r\n");
      out.write("        board.memo_id.value = \"\";\r\n");
      out.write("        board.target=\"_self\";\r\n");
      out.write("        board.action          = \"");
      out.print( request.getRequestURI() );
      out.write("\";\r\n");
      out.write("        board.b_mode.value = \"W\";\r\n");
      out.write("        board.submit();\r\n");
      out.write("    }\r\n");
      out.write("\r\n");
      out.write("    /* 읽기 페이지로 이동 */\r\n");
      out.write("    function goDisplay(memo_id) {\r\n");
      out.write("        board.memo_id.value = memo_id; \r\n");
      out.write("        board.b_mode.value       = \"R\";\r\n");
      out.write("       // board.target             = \"_self\";\r\n");
      out.write("        board.action             = \"");
      out.print( request.getRequestURI() );
      out.write("\";\r\n");
      out.write("        board.submit();\r\n");
      out.write("    }\r\n");
      out.write("\r\n");
      out.write("    /* page 이동 */\r\n");
      out.write("    function goPage( page ) {\r\n");
      out.write("        board.cpage.value     = page;\r\n");
      out.write("        board.b_mode.value = \"L\";\r\n");
      out.write("        board.target=\"_self\";\r\n");
      out.write("        board.action          = \"");
      out.print( request.getRequestURI() );
      out.write("\";\r\n");
      out.write("        board.submit();\r\n");
      out.write("    }\r\n");
      out.write("    \r\n");
      out.write("    function goDelete(){\r\n");
      out.write("        var frm = document.board;\r\n");
      out.write("        var objs = frm.boxes;\r\n");
      out.write("        var isChk = false;\r\n");
      out.write("    \t for (var i=0; i<objs.length; i++){\r\n");
      out.write("         \tif(objs[i].checked)\r\n");
      out.write("         \t\tisChk = true;\r\n");
      out.write("         }\r\n");
      out.write("         if (!isChk) {\r\n");
      out.write("             alert(\"삭제할 리스트 한개 이상 선택하세요.\");\r\n");
      out.write("             return;\r\n");
      out.write("         }\r\n");
      out.write("         \r\n");
      out.write("         if( confirm(\"선택하신 리스트를 삭제 하시겠습니까?\")) {\r\n");
      out.write("        \t frm.modeType.value = \"lDel\";\r\n");
      out.write("             frm.action   = \"member_write_proc.jsp\";\r\n");
      out.write("             frm.submit();\r\n");
      out.write("         }\r\n");
      out.write("    }\r\n");
      out.write("\r\n");
      out.write("//-->\r\n");
      out.write("</script>\r\n");
      out.write("<form name=\"board\" method=\"post\">  \r\n");
      out.write("  <input type=\"hidden\" name=\"cpage\"        value=\"");
      out.print( cpage       );
      out.write("\">\r\n");
      out.write("  <input type=\"hidden\" name=\"memo_id\">  \r\n");
      out.write("  <input type=\"hidden\" name=\"b_mode\"       value=\"");
      out.print(b_mode      );
      out.write("\">\r\n");
      out.write("  <input type=\"hidden\" name=\"searchFlag\"   value=\"");
      out.print(searchFlag  );
      out.write("\">\r\n");
      out.write("  <input type=\"hidden\" name=\"conference_id\"   value=\"");
      out.print(old_conference_id  );
      out.write("\">\r\n");
      out.write("  <input type=\"hidden\" name=\"modeType\" >\r\n");
      out.write("  \r\n");
      out.write("  <table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\"> \r\n");
      out.write("        <td valign=\"top\"><table width=\"100%\" style=\"border:1px solid #d3d3d3;\">\r\n");
      out.write("           <thead>\r\n");
      out.write("                    <th><div align=\"center\">제목</div></th>\r\n");
      out.write("                    <th><div align=\"center\">등록일</div></th>\r\n");
      out.write("            </thead>\r\n");
      out.write("            <tbody>\r\n");
      out.write("    \t\t</tbody>\r\n");

    if( totalCnt==0 && rset.row()==0) { 
      out.write("\r\n");
      out.write("                <tr><td colspan=7 align=\"center\" height=50>:::: 없음 ::::</td></tr>  \r\n");
    
    } else {
        while( rset.next()){
        	memo_id       = StringUtil.defaultIfEmpty(rset.getString("memo_id"   ), ""); 
        	title     = StringUtil.defaultIfEmpty(rset.getString("title" ), "");
        	reg_date    = StringUtil.defaultIfEmpty(rset.getString("reg_date"), "");

      out.write("\t\t\t\t\r\n");
      out.write("\t\t\t<tr>\r\n");
      out.write("\t\t\t\t<td align=\"center\">\r\n");
      out.write("\t\t\t\t\t<a href=\"javascript:goDisplay('");
      out.print(memo_id);
      out.write("');\" class=\"skin\">\r\n");
      out.write("\t\t\t\t\t");
      out.print(title);
      out.write("\r\n");
      out.write("\t\t\t\t\t</a>\r\n");
      out.write("\t\t\t\t</td>\r\n");
      out.write("\t\t\t\t<td align=\"center\"><a href=\"javascript:goDisplay('");
      out.print(memo_id);
      out.write("');\" class=\"skin\">");
      out.print(reg_date);
      out.write("</a></td>\r\n");
      out.write("\t\t\t</tr>\r\n");
      } 
    }
    
      out.write(" \r\n");
      out.write("    \t\t</tbody>\t\t\t\t\t\r\n");
      out.write("        </table></td>\r\n");
      out.write("      </tr>\r\n");
      out.write("      <tr>\r\n");
      out.write("        <td><table width=\"100%\" height=\"30\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\r\n");
      out.write("            <tr>\r\n");
      out.write("                  <td>");
      out.print( cpage + 1 );
      out.write(' ');
      out.write('/');
      out.write(' ');
      out.print( pageTotal + 1 );
      out.write(" page</td>\r\n");
      out.write("                  ");
      out.write("\r\n");
      out.write("                  <td width=\"70%\" align=\"center\">\r\n");
      out.write("                  ");
      out.print( PagingUtil.getDefaultListPageTag(cpage, pageSize, pageTotal) );
      out.write("  \r\n");
      out.write("                  </td>\r\n");
      out.write("                  <td align=\"right\">\r\n");
      out.write("                  \t<a href=\"javascript:goWriteForm();\"><img src=\"");
      out.print(root_path);
      out.write("/images/bt_up.gif\" alt=\"등록\" /></a>\r\n");
      out.write("                  </td>\r\n");
      out.write("            </tr>\r\n");
      out.write("        </table></td>\r\n");
      out.write("      </tr>\r\n");
      out.write("      <tr>\r\n");
      out.write("        <td height=\"50\" align=\"center\" class=\"line_search\"><select name=\"searchTarget\">\r\n");
      out.write("            <option value=\"1\" ");
      out.print( "1".equals(searchTarget) ? "selected" : "" );
      out.write(" >제목</option>\r\n");
      out.write("            <option value=\"2\" ");
      out.print( "2".equals(searchTarget) ? "selected" : "" );
      out.write(" >내용</option>     \r\n");
      out.write("          </select>\r\n");
      out.write("            <input name=\"keyword\" type=\"text\" value=\"");
      out.print( keyword );
      out.write("\" onKeypress=\"if (event.keyCode==13) goSearch();\" class=\"input_box\" size=\"20\">\r\n");
      out.write("            <a href=\"javascript:goSearch();\"><img src=\"");
      out.print(root_path);
      out.write("/images/bt_search.gif\" alt=\"검색\" class=\"sbtn1\"/></a></td>\r\n");
      out.write("      </tr>\r\n");
      out.write("    </table>\r\n");
      out.write("    \r\n");
      out.write("    \r\n");
      out.write("    \r\n");
      out.write("\t\t\r\n");
      out.write("</form> \t\t");
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
