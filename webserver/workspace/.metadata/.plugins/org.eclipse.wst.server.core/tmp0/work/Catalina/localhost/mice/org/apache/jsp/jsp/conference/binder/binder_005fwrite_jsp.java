/*
 * Generated by the Jasper component of Apache Tomcat
 * Version: Apache Tomcat/8.0.24
 * Generated at: 2015-08-14 06:51:58 UTC
 * Note: The last modified time of this file was set to
 *       the last modified time of the source file after
 *       generation to assist with modification tracking.
 */
package org.apache.jsp.jsp.conference.binder;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import java.util.*;
import java.text.*;
import sips.common.lib.util.*;
import sips.common.lib.value.*;
import sips.dept.contents.Binder;
import sips.dept.contents.Session;
import sips.dept.contents.Topic;
import sips.dept.contents.Conference;

public final class binder_005fwrite_jsp extends org.apache.jasper.runtime.HttpJspBase
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
    _jspx_imports_packages.add("java.util");
    _jspx_imports_packages.add("java.text");
    _jspx_imports_packages.add("javax.servlet.http");
    _jspx_imports_packages.add("javax.servlet.jsp");
    _jspx_imports_classes = new java.util.HashSet<>();
    _jspx_imports_classes.add("sips.dept.contents.Topic");
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
 request.setCharacterEncoding("UTF-8"); 
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
      out.write("\r\n");
  String formName = "frmBoard"; 
      out.write('\r');
      out.write('\n');

	ParamValue       parmValue = new ParamValue();          
	Binder            binder     = new Binder();     
	Session            _session     = new Session();
	Topic            topic     = new Topic();
	Conference            conference     = new Conference();
    ResultSetValue   rset      = null;
    ResultSetValue   rset2      = null;
    ResultSetValue   rset3      = null;
    ResultSetValue   rset4      = null;
    ResultSetValue   rset5      = null;
    
	String modeType           = StringUtil.defaultIfEmpty(request.getParameter("modeType"    ), "ins");
    String b_mode             = StringUtil.defaultIfEmpty(request.getParameter("b_mode"      ), ""); 
    String conference_id             = StringUtil.defaultIfEmpty(request.getParameter("conference_id"      ), ""); 
    System.out.println("conference_id--->"+conference_id);
    String user_cd         = (String)session.getAttribute("USER_CD");
    String user_authority         = "";
    
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
    String binder_id = "";

    parmValue.put("conference_id"    , conference_id  );
    parmValue.put("user_cd"    , user_cd  );
    rset5 = binder.getBniderInfo2(parmValue);
    if ( rset5.next()){
    	binder_id    = StringUtil.defaultIfEmpty(rset5.getString("binder_id"    ), "");
    }
    
    parmValue.put("binder_id"    , binder_id  );
    		
    if( !"".equals(binder_id)) {
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
        	session_id    = StringUtil.defaultIfEmpty(rset.getString("session_id"    ), "");
        	topic_id    = StringUtil.defaultIfEmpty(rset.getString("topic_id"    ), "");
        	conference_name    = StringUtil.defaultIfEmpty(rset.getString("conference_name"    ), "");
        	user_id    = StringUtil.defaultIfEmpty(rset.getString("user_id"    ), "");
        	user_cd    = StringUtil.defaultIfEmpty(rset.getString("user_cd"    ), "");
        }

        result = binder.getAgendaRegister(parmValue);
        
        if( "Re".equals(b_mode)) {
            strMsg   = "등록";
            
        } else {
            modeType = "mod";
            strMsg   = "수정";
        }
    } else {
        modeType = "ins";
        strMsg   = "등록";
    }    
    
	 // 컨퍼런스 셀렉트 리스트
    rset2 = conference.getConferenceList3();

    //세션 셀렉트 리스트
    rset3 = _session.getSessionSelectList(parmValue);
    //토픽 셀렉트 리스트
    rset4 = topic.getTopicSelectList(parmValue);
    
   // 
    
	

      out.write("\t\t\r\n");
      out.write("<script language=\"JavaScript\" type=\"text/JavaScript\">\r\n");
      out.write("<!--\r\n");
      out.write("\r\n");
      out.write("\twindow.onload = function(){\r\n");
      out.write("\t\t//openLayer('layerPop',170,400);\r\n");
      out.write("\t};\r\n");
      out.write("\t\r\n");
      out.write("\t//레이어 팝업 열기\r\n");
      out.write("\tfunction openLayer(IdName, tpos, lpos){\r\n");
      out.write("\t\tvar pop = document.getElementById(IdName);\r\n");
      out.write("\t\tdocument.all['moveFrame'].src = './binder_user_popup.jsp?conference_id=");
      out.print(conference_id);
      out.write("';\r\n");
      out.write("\t\t\tpop.style.display = \"block\";\r\n");
      out.write("\t\t\tpop.style.top = tpos + \"px\";\r\n");
      out.write("\t\t\tpop.style.left = lpos + \"px\";\r\n");
      out.write("\t}\r\n");
      out.write("\r\n");
      out.write("\t//레이어 팝업 닫기\r\n");
      out.write("\tfunction closeLayer(IdName){\r\n");
      out.write("\t\tvar pop = document.getElementById(IdName);\r\n");
      out.write("\t\tpop.style.display = \"none\";\r\n");
      out.write("\t}\r\n");
      out.write("\t\r\n");
      out.write("\tfunction conference_change(obj)\r\n");
      out.write("\t{\r\n");
      out.write("        var frm = document.");
      out.print(formName);
      out.write(";\r\n");
      out.write("\t\tvar obj_value = conference_id.value;\r\n");
      out.write("\t\t//alert(obj_value);\r\n");
      out.write("\t\tfrm.target=\"_self\";\r\n");
      out.write("\t\tfrm.b_mode.value = \"W\";\r\n");
      out.write("\t\tfrm.action             = \"./binder.jsp\";\r\n");
      out.write("\t\tfrm.submit();\r\n");
      out.write("\t}\r\n");
      out.write("\r\n");
      out.write("    /**\r\n");
      out.write("     * 게시물 등록\r\n");
      out.write("     */\r\n");
      out.write("    function goRegitProc () {\r\n");
      out.write("        var frm = document.");
      out.print(formName);
      out.write(";\r\n");
      out.write("\t\tif (frm.session_id.value == \"0\") {\r\n");
      out.write("            alert(\"세션을 선택하세요.\");\r\n");
      out.write("            frm.session_id.focus();\r\n");
      out.write("            return;\r\n");
      out.write("        }\r\n");
      out.write("\t\tif (frm.topic_id.value == \"0\") {\r\n");
      out.write("            alert(\"토픽을 선택하세요.\");\r\n");
      out.write("            frm.topic_id.focus();\r\n");
      out.write("            return;\r\n");
      out.write("        }\r\n");
      out.write("\t\tif (frm.binder_title.value == \"\") {\r\n");
      out.write("            alert(\"제목을 입력하세요.\");\r\n");
      out.write("            frm.binder_title.focus();\r\n");
      out.write("            return;\r\n");
      out.write("        }\r\n");
      out.write("\t\tif (frm.writer.value == \"\") {\r\n");
      out.write("            alert(\"저자를 입력하세요.\");\r\n");
      out.write("            frm.writer.focus();\r\n");
      out.write("            return;\r\n");
      out.write("        }\r\n");
      out.write("\t\tif (frm.contents.value == \"\" ) {\r\n");
      out.write("            alert(\"내용을 입력하세요.\");\r\n");
      out.write("            frm.contents.focus();\r\n");
      out.write("            return;\r\n");
      out.write("\t    }\r\n");
      out.write("\t\tif (frm.attached.value == \"\"  && frm.hidden_attached.value == \"\" ) {\r\n");
      out.write("\t        alert(\"자료를 첨부하세요.\");\r\n");
      out.write("\t        frm.attached.focus();\r\n");
      out.write("\t        return;\r\n");
      out.write("\t    }\r\n");
      out.write("\t    \r\n");
      out.write("        if( confirm(\"");
      out.print(strMsg);
      out.write(" 하시겠습니까?\")) {\r\n");
      out.write("            frm.action   = \"binder_write_proc.jsp\";\r\n");
      out.write("\t        frm.modeType.value = \"ins\";\r\n");
      out.write("            frm.encoding = \"multipart/form-data\";\r\n");
      out.write("            frm.submit();\r\n");
      out.write("        }\r\n");
      out.write("    }\r\n");
      out.write("\tfunction goModifyProc () { \r\n");
      out.write("        var frm = document.");
      out.print(formName);
      out.write(";\r\n");
      out.write("\t\tif (frm.session_id.value == \"0\") {\r\n");
      out.write("            alert(\"세션을 선택하세요.\");\r\n");
      out.write("            frm.session_id.focus();\r\n");
      out.write("            return;\r\n");
      out.write("        }\r\n");
      out.write("\t\tif (frm.topic_id.value == \"0\") {\r\n");
      out.write("            alert(\"토픽을 선택하세요.\");\r\n");
      out.write("            frm.topic_id.focus();\r\n");
      out.write("            return;\r\n");
      out.write("        }\r\n");
      out.write("\t\tif (frm.binder_title.value == \"\") {\r\n");
      out.write("            alert(\"제목을 입력하세요.\");\r\n");
      out.write("            frm.binder_title.focus();\r\n");
      out.write("            return;\r\n");
      out.write("        }\r\n");
      out.write("\t\tif (frm.writer.value == \"\") {\r\n");
      out.write("            alert(\"저자를 입력하세요.\");\r\n");
      out.write("            frm.writer.focus();\r\n");
      out.write("            return;\r\n");
      out.write("        }\r\n");
      out.write("\t\tif (frm.contents.value == \"\" ) {\r\n");
      out.write("            alert(\"내용을 입력하세요.\");\r\n");
      out.write("            frm.contents.focus();\r\n");
      out.write("            return;\r\n");
      out.write("\t    }\r\n");
      out.write("\t\tif (frm.attached.value == \"\"  && frm.hidden_attached.value == \"\" ) {\r\n");
      out.write("\t        alert(\"자료를 첨부하세요.\");\r\n");
      out.write("\t        frm.attached.focus();\r\n");
      out.write("\t        return;\r\n");
      out.write("\t    }\r\n");
      out.write("\t    \r\n");
      out.write("    \tif( confirm(\"수정 하시겠습니까?\")) {\r\n");
      out.write("\t        frm.modeType.value = \"mod\";\r\n");
      out.write("\t        frm.action   = \"binder_write_proc.jsp\";\r\n");
      out.write("\t        frm.encoding = \"multipart/form-data\";\r\n");
      out.write("\t        frm.submit();\r\n");
      out.write("    \t}\r\n");
      out.write("            \r\n");
      out.write("\t}    \r\n");
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
      out.write("    function getSpace(cnt) {\r\n");
      out.write("        var spc = \"\";\r\n");
      out.write("        for (i = 0; i < cnt; i++) {\r\n");
      out.write("            spc += \" \";\r\n");
      out.write("        }        \r\n");
      out.write("        return spc;\r\n");
      out.write("    }\r\n");
      out.write("    \r\n");
      out.write("\r\n");
      out.write("    //이미지 확장자 체크\r\n");
      out.write("  \tfunction file_check_img(uploadImgObj)\r\n");
      out.write("  \t{\r\n");
      out.write("  \t\tvar enableUploadFileExt = [\"pdf\"];\r\n");
      out.write("  \t\tvar uploadImgObjName = uploadImgObj.value;\r\n");
      out.write("  \t\tvar upArrLeng = enableUploadFileExt.length;\r\n");
      out.write("  \t\tvar chkExe = uploadImgObjName.split(\".\");\r\n");
      out.write("  \t\t\r\n");
      out.write("  \t\tvar chkFiles = false;\r\n");
      out.write("  \t\t\r\n");
      out.write("  \t\tfor(var i=0;i<upArrLeng;i++)\r\n");
      out.write("  \t\t{\r\n");
      out.write("  \t\t\tif(enableUploadFileExt[i] == chkExe[1].toLowerCase())\r\n");
      out.write("  \t\t\t{\r\n");
      out.write("  \t\t\t\tchkFiles = true;\r\n");
      out.write("  \t\t\t\tbreak;\r\n");
      out.write("  \t\t\t}\r\n");
      out.write("  \t\t}\r\n");
      out.write("  \t\tif(!chkFiles)\r\n");
      out.write("  \t\t{\r\n");
      out.write("  \t\t\talert(\"\tPDF 파일만 등록 가능합니다.\");\r\n");
      out.write("  \t\t\tuploadImgObj.outerHTML = uploadImgObj.outerHTML;\r\n");
      out.write("  \t\t\treturn;\r\n");
      out.write("  \t\t}\r\n");
      out.write("  \t}\r\n");
      out.write("\t\r\n");
      out.write("\tfunction goDeleteForm () { \r\n");
      out.write("        var frm = document.");
      out.print(formName);
      out.write(";\r\n");
      out.write("        if( confirm(\"삭제 하시겠습니까?\")) {\r\n");
      out.write("            frm.modeType.value = \"del\";\r\n");
      out.write("            frm.action   = \"binder_write_proc.jsp\";\r\n");
      out.write("\t        frm.encoding = \"multipart/form-data\";\r\n");
      out.write("            frm.submit();\r\n");
      out.write("        }\r\n");
      out.write("                \r\n");
      out.write("    }    \r\n");
      out.write("  //-->\r\n");
      out.write("</script>\r\n");
      out.write("<form name=\"");
      out.print(formName);
      out.write("\" method=\"post\">\r\n");
      out.write("<input type=\"hidden\" name=\"binder_id\" value=\"");
      out.print(binder_id );
      out.write("\">  \r\n");
      out.write("<input type=\"hidden\" name=\"b_mode\"       value=\"");
      out.print(b_mode      );
      out.write("\">\r\n");
      out.write("<input type=\"hidden\" name=\"modeType\"     value=\"");
      out.print(modeType    );
      out.write("\">\r\n");
      out.write("<input type=\"hidden\" name=\"hidden_attached\" value=\"");
      out.print(attached);
      out.write("\">\r\n");
      out.write("<input type=\"hidden\" name=\"con_admin_user_cd\" id=\"con_admin_user_cd\" value=\"");
      out.print(user_cd );
      out.write("\">\r\n");
      out.write("\r\n");
      out.write("<table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\r\n");
      out.write("      \r\n");
      out.write("      <tr>\r\n");
      out.write("        <td valign=\"top\"><table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" style=\"border-top:2px solid #e0e0e0; border-bottom:2px solid #e0e0e0; width:784px;\">\r\n");
      out.write("            <tr>\r\n");
      out.write("              <td width=\"210\" class=\"bbbg\" style=\"padding-left:20px; font-weight:bold;\">컨퍼런스</td>\r\n");
      out.write("              <td width=\"574\" style=\"padding-left:20px;\">\r\n");
    
        while( rset2.next()){
        	sel_conference_name     = StringUtil.defaultIfEmpty(rset2.getString("name" ), "");
        	sel_conference_id    = StringUtil.defaultIfEmpty(rset2.getString("conference_id"), "");

      out.write("\t\t\t\r\n");
      out.write("\t\t\t");
      out.print( sel_conference_id.equals(conference_id) ? sel_conference_name : "" );
      out.write('	');
      out.write('\r');
      out.write('\n');
      } 

      out.write("           \r\n");
      out.write("              </td>\r\n");
      out.write("            </tr>\r\n");
      out.write("            <tr>\r\n");
      out.write("              <td width=\"210\" class=\"bbbg\" style=\"padding-left:20px; font-weight:bold;\">세션 제목</td>\r\n");
      out.write("              <td width=\"574\" style=\"padding-left:20px;\">\r\n");
      out.write("              \t\t<select name=session_id id=session_id>\r\n");
      out.write("              \t\t\t<option value=\"0\" >선택</option>\r\n");

    if(  rset3.row()==0) { 
      out.write("\r\n");
      out.write("                \r\n");
    
    } else {
        while( rset3.next()){
        	sel_session_title     = StringUtil.defaultIfEmpty(rset3.getString("title" ), "");
        	sel_session_id    = StringUtil.defaultIfEmpty(rset3.getString("session_id"), "");

      out.write("\t\t\t\t\r\n");
      out.write("\t\t\t<option value=\"");
      out.print(sel_session_id);
      out.write('"');
      out.write(' ');
      out.print( sel_session_id.equals(session_id) ? "selected" : "" );
      out.write(' ');
      out.write('>');
      out.print(sel_session_title);
      out.write("</option>\r\n");
      out.write("\t\t\t\r\n");
      } 
    }
    
      out.write("           \r\n");
      out.write("          \t\t</select>\r\n");
      out.write("              </td>\r\n");
      out.write("            </tr>\r\n");
      out.write("            <tr>\r\n");
      out.write("              <td width=\"210\" class=\"bbbg\" style=\"padding-left:20px; font-weight:bold;\">토픽 제목</td>\r\n");
      out.write("              <td width=\"574\" style=\"padding-left:20px;\">\r\n");
      out.write("              \t\t<select name=topic_id id=topic_id>\r\n");
      out.write("              \t\t\t<option value=\"0\" >선택</option>\r\n");

    if(  rset4.row()==0) { 
      out.write("\r\n");
      out.write("                \r\n");
    
    } else {
        while( rset4.next()){
        	sel_topic_title     = StringUtil.defaultIfEmpty(rset4.getString("title" ), "");
        	sel_topic_id    = StringUtil.defaultIfEmpty(rset4.getString("topic_id"), "");

      out.write("\t\t\t\t\r\n");
      out.write("\t\t\t<option value=\"");
      out.print(sel_topic_id);
      out.write('"');
      out.write(' ');
      out.print( sel_topic_id.equals(topic_id) ? "selected" : "" );
      out.write(' ');
      out.write('>');
      out.print(sel_topic_title);
      out.write("</option>\r\n");
      out.write("\t\t\t\r\n");
      } 
    }
    
      out.write("           \r\n");
      out.write("          \t\t</select>\r\n");
      out.write("              </td>\r\n");
      out.write("            </tr>\r\n");
      out.write("            <tr>\r\n");
      out.write("              <td width=\"210\" class=\"bbbg\" style=\"padding-left:20px; font-weight:bold;\">제목</td>\r\n");
      out.write("              <td width=\"574\" style=\"padding-left:20px;\"><input type=\"text\" name=\"binder_title\" value=\"");
      out.print(binder_title);
      out.write("\"></td>\r\n");
      out.write("            </tr>\r\n");
      out.write("            <tr>\r\n");
      out.write("              <td width=\"210\" class=\"bbbg\" style=\"padding-left:20px; font-weight:bold;\">저자</td>\r\n");
      out.write("              <td width=\"574\" style=\"padding-left:20px;\"><input type=\"text\" name=\"writer\" value=\"");
      out.print(writer);
      out.write("\"></td>\r\n");
      out.write("            </tr>\r\n");
      out.write("            <tr>\r\n");
      out.write("              <td width=\"210\" class=\"bbbg\" style=\"padding-left:20px; font-weight:bold;\">내용</td>\r\n");
      out.write("              <td width=\"574\" style=\"padding-left:20px;\">\r\n");
      out.write("\t\t\t\t\t<textarea name=\"contents\" rows=\"5\" cols=\"60\">");
      out.print(contents );
      out.write("</textarea>\r\n");
      out.write("\t\t\t  </td>\r\n");
      out.write("            </tr>\r\n");
      out.write("            <tr>\r\n");
      out.write("              <td width=\"210\" class=\"bbbg\" style=\"padding-left:20px; font-weight:bold;\">다운로드</td>\r\n");
      out.write("              <td width=\"574\" style=\"padding-left:20px;\">\r\n");
      out.write("              ");

              	if (!"".equals(attached)){
              		out.println("<a href=/mice/upload/pdf/"+attached+">"+attached+"</a><BR>");
              	}
              
      out.write("\r\n");
      out.write("              <input name=\"attached\" type=\"file\" onchange=\"file_check_img(this)\">\r\n");
      out.write("\t\t\t  </td>\r\n");
      out.write("            </tr>\r\n");
      out.write("        </table></td>\r\n");
      out.write("      </tr>\r\n");
      out.write("      <tr>\r\n");
      out.write("        <td height=\"50\" align=\"center\">\r\n");
      out.write("        ");
 if(modeType.equals("ins")){ 
      out.write("\r\n");
      out.write("            <a href=\"javascript:goRegitProc();\"><img src=\"");
      out.print(root_path);
      out.write("/images/bt_up.gif\" alt=\"등록\" /></a>\r\n");
      out.write("         ");
 }else{ 
      out.write("\r\n");
      out.write("            <a href=\"javascript:goModifyProc();\"><img src=\"");
      out.print(root_path);
      out.write("/images/bt_modify.gif\" alt=\"수정\" /></a>\r\n");
      out.write("            <a href=\"javascript:goDeleteForm('");
      out.print( conference_id );
      out.write("');\"><img src=\"");
      out.print(root_path);
      out.write("/images/bt_del.gif\" alt=\"삭제\" /></a>\r\n");
      out.write("         ");
 } 
      out.write("\r\n");
      out.write("         </td>\r\n");
      out.write("      </tr>\r\n");
      out.write("    </table>\r\n");
      out.write("    \r\n");
      out.write("</form>\t\t\r\n");
      out.write("\r\n");
      out.write("<!-- 팝업 -->\r\n");
      out.write("<div  id=\"layerPop\" >\r\n");
      out.write(" <iframe id=\"moveFrame\" frameborder='0' height='100%' width='100%' scrolling='no' src=''></iframe>\r\n");
      out.write("</div>\r\n");
      out.write("<!--// 팝업 -->");
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
