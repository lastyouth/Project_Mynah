/*
 * Generated by the Jasper component of Apache Tomcat
 * Version: Apache Tomcat/8.0.24
 * Generated at: 2015-08-14 08:07:59 UTC
 * Note: The last modified time of this file was set to
 *       the last modified time of the source file after
 *       generation to assist with modification tracking.
 */
package org.apache.jsp.jsp.conference.member;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import java.util.*;
import sips.common.lib.util.*;
import sips.common.lib.value.*;
import sips.dept.contents.Member;
import sips.dept.contents.Conference;
import sips.dept.contents.Appellation;

public final class member_005fwrite_jsp extends org.apache.jasper.runtime.HttpJspBase
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
    _jspx_imports_packages.add("javax.servlet.http");
    _jspx_imports_packages.add("javax.servlet.jsp");
    _jspx_imports_classes = new java.util.HashSet<>();
    _jspx_imports_classes.add("sips.dept.contents.Appellation");
    _jspx_imports_classes.add("sips.dept.contents.Member");
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
      out.write("\r\n");
      out.write("\r\n");
  String formName = "frmBoard"; 
      out.write('\r');
      out.write('\n');

	ParamValue       parmValue = new ParamValue();          
	Member            member     = new Member();
	Conference            conference     = new Conference();
	Appellation            appellation     = new Appellation();
    ResultSetValue   rset      = null;
    ResultSetValue   rset2      = null;
    ResultSetValue   rset3      = null;
    ResultSetValue   rset4      = null;
    
	int    cpage              = (request.getParameter("cpage") == null || "".equals(request.getParameter("cpage"))) ? 0 : Integer.parseInt(request.getParameter("cpage"));   // 현재 페이지
    String searchTarget       = StringUtil.defaultIfEmpty(request.getParameter("searchTarget"), "");	      // 검색 조건 (제목/내용)
    String keyword            = StringUtil.defaultIfEmpty(request.getParameter("keyword"     ), "");        // 검색할 키워드
    String searchFlag         = StringUtil.defaultIfEmpty(request.getParameter("searchFlag"  ), "");        // 검색플래그
    String user_cd       = (String)session.getAttribute("USER_CD");
    String modeType           = StringUtil.defaultIfEmpty(request.getParameter("modeType"    ), "ins");
    String b_mode             = StringUtil.defaultIfEmpty(request.getParameter("b_mode"      ), ""); 
    String conference_id             = StringUtil.defaultIfEmpty(request.getParameter("conference_id"      ), ""); 
    
    //String admin_user_cd         = (String)session.getAttribute("USER_CD");
   // String admin_authority         = (String)session.getAttribute("USE_AUTHORITY");
    
    String user_id     = "";
    String name    = "";
    String sex    = "";
    String company    = "";
    String  nation_id    = "";
    String title    = "";
    String picture    = "";
    String phone1   = "";
    String phone2  = "";
    String phone3   = "";
    String handphone1   = "";
    String handphone2   = "";
    String handphone3   = "";
    String first_phone   = "h";
    String email    = "";
    String email1    = "";
    String email2   = "";
    String address    = "";
    String authoriry_1    = "";
    String authoriry_2    = "";
    String authoriry_3    = "";
    String authoriry_4    = "";
    String authoriry_5    = "";
    String authoriry_6    = "";
    String authoriry_7   = "";
    String authoriry_8    = "";
    String authoriry_9    = "";
    String research_authoriry = "";
    String question_authority    = "";
    String reg_date    = "";
    String platform    = "";
    String authority = "";
    String conference_name  = "";   
    String strMsg   = "수정";
    String sel_appellation_id     = "";   
    String sel_appellation     = "";   
    String appellation_id     = "";   
    String passwd = "";
    String zipcode = "";
    
    parmValue.put("user_cd"    , user_cd  );

    if( !"".equals(user_cd)) {
        rset = member.getMemberInfo(parmValue);
        while( rset.next()){
        	conference_name    = StringUtil.defaultIfEmpty(rset.getString("conference_name"    ), "");
        	conference_id    = StringUtil.defaultIfEmpty(rset.getString("conference_id"    ), "");
    	    user_id    = StringUtil.defaultIfEmpty(rset.getString("id"    ), "");
    	    name    = StringUtil.defaultIfEmpty(rset.getString("name"    ), "");
    	    sex    = StringUtil.defaultIfEmpty(rset.getString("_sex"    ), "");
    	    company    = StringUtil.defaultIfEmpty(rset.getString("company"    ), "");
    	    nation_id    = StringUtil.defaultIfEmpty(rset.getString("nation_id"    ), "");
    	    appellation_id    = StringUtil.defaultIfEmpty(rset.getString("appellation_id"    ), "");
    	    picture    = StringUtil.defaultIfEmpty(rset.getString("picture"    ), "");
    	    phone1    = StringUtil.defaultIfEmpty(rset.getString("phone1"    ), "");
    	    phone2    = StringUtil.defaultIfEmpty(rset.getString("phone2"    ), "");
    	    phone3    = StringUtil.defaultIfEmpty(rset.getString("phone3"    ), "");
    	    handphone1    = StringUtil.defaultIfEmpty(rset.getString("handphone1"    ), "");
    	    handphone2    = StringUtil.defaultIfEmpty(rset.getString("handphone2"    ), "");
    	    handphone3    = StringUtil.defaultIfEmpty(rset.getString("handphone3"    ), "");
    	    first_phone    = StringUtil.defaultIfEmpty(rset.getString("first_phone"    ), "");
    	    email    = StringUtil.defaultIfEmpty(rset.getString("email"    ), "");
    	    zipcode    = StringUtil.defaultIfEmpty(rset.getString("zipcode"    ), "");
    	    address    = StringUtil.defaultIfEmpty(rset.getString("address"    ), "");
    	    authoriry_1    = StringUtil.defaultIfEmpty(rset.getString("authoriry_1"    ), "");
    	    authoriry_2    = StringUtil.defaultIfEmpty(rset.getString("authoriry_2"    ), "");
    	    authoriry_3    = StringUtil.defaultIfEmpty(rset.getString("authoriry_3"    ), "");
    	    authoriry_4    = StringUtil.defaultIfEmpty(rset.getString("authoriry_4"    ), "");
    	    authoriry_5    = StringUtil.defaultIfEmpty(rset.getString("authoriry_5"    ), "");
    	    authoriry_6    = StringUtil.defaultIfEmpty(rset.getString("authoriry_6"    ), "");
    	    authoriry_7    = StringUtil.defaultIfEmpty(rset.getString("authoriry_7"    ), "");
    	    authoriry_8    = StringUtil.defaultIfEmpty(rset.getString("authoriry_8"    ), "");
    	    authoriry_9    = StringUtil.defaultIfEmpty(rset.getString("authoriry_9"    ), "");
    	    research_authoriry    = StringUtil.defaultIfEmpty(rset.getString("research_authoriry"    ), "");
    	    question_authority    = StringUtil.defaultIfEmpty(rset.getString("question_authority"    ), "");
    	    reg_date    = StringUtil.defaultIfEmpty(rset.getString("reg_date"    ), "");
    	    platform    = StringUtil.defaultIfEmpty(rset.getString("platform"    ), "");
    	    authority    = StringUtil.defaultIfEmpty(rset.getString("_authority"    ), "");
    	    passwd    = StringUtil.defaultIfEmpty(rset.getString("passwd"    ), "");
    	    
    	    if(email==null) {
                System.out.println("[UtilHelper] listToToken() : [[Warnning]] Input data is null.");
            }
            else {
            	int cnt = 0;
            	StringTokenizer st2 = new StringTokenizer(email,"@");
            	while(st2.hasMoreTokens()){
            		   if (cnt == 0 )
            			   email1 = st2.nextToken();
            		   else
            			   email2 = st2.nextToken();
            		cnt++;
            	}
            }
        }
 
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
    
    //parmValue.put("admin_user_cd"    , admin_user_cd  );
    //parmValue.put("admin_authority"    , admin_authority  );
    //rset2 = conference.getConferenceList2(parmValue);
    
    rset3 = member.getNationList(parmValue);
    

    parmValue.put("conference_id"    , conference_id  );
    rset4 = appellation.getAppellationSelectList(parmValue);

      out.write("\t\t\r\n");
      out.write("<script language=\"JavaScript\" type=\"text/JavaScript\">\r\n");
      out.write("<!--    \r\n");
      out.write("\tfunction goModifyProc (user_cd) { \r\n");
      out.write("    var frm = document.");
      out.print(formName);
      out.write(";\r\n");
      out.write("    \r\n");
      out.write("    if (frm.passwd.value.length != 0){\r\n");
      out.write("\t    if(frm.passwd.value.length < 6 || frm.passwd.value.length >12 )\r\n");
      out.write("\t    {\r\n");
      out.write("\t    \talert(\"비밀번호는 6 ~ 12자까지 사용 할 수 있습니다.\");\r\n");
      out.write("\t      \tfrm.passwd.fucus();\r\n");
      out.write("\t      \treturn false;\r\n");
      out.write("\t    }\r\n");
      out.write("\t\r\n");
      out.write("\t    if (frm.passwd.value != frm.repasswd.value) {\r\n");
      out.write("\t        alert(\"비밀번호가 상이합니다..\");\r\n");
      out.write("\t        frm.repasswd.focus();\r\n");
      out.write("\t        return;\r\n");
      out.write("\t    }\r\n");
      out.write("    }\r\n");
      out.write("\r\n");
      out.write("    if (frm.handphone1.value == \"\") {\r\n");
      out.write("        alert(\"핸드폰 번호를 입력하세요.\");\r\n");
      out.write("        frm.handphone1.focus();\r\n");
      out.write("        return;\r\n");
      out.write("    }\r\n");
      out.write("    if (frm.handphone2.value == \"\") {\r\n");
      out.write("        alert(\"핸드폰 번호를 입력하세요.\");\r\n");
      out.write("        frm.handphone2.focus();\r\n");
      out.write("        return;\r\n");
      out.write("    }\r\n");
      out.write("    if (frm.handphone3.value == \"\") {\r\n");
      out.write("        alert(\"핸드폰 번호를 입력하세요.\");\r\n");
      out.write("        frm.handphone3.focus();\r\n");
      out.write("        return;\r\n");
      out.write("    }\r\n");
      out.write("    if (frm.email1.value == \"\" || frm.email2.value == \"\") {\r\n");
      out.write("        alert(\"이메일 주소를 입력하세요..\");\r\n");
      out.write("        frm.email1.focus();\r\n");
      out.write("        return;\r\n");
      out.write("    }\r\n");
      out.write("    if (frm.address.value == frm.hidden_address.value) {\r\n");
      out.write("        alert(\"나머지 주소를 입력하세요.\");\r\n");
      out.write("        frm.address.focus();\r\n");
      out.write("        return;\r\n");
      out.write("    }\r\n");
      out.write("    \r\n");
      out.write("    if( confirm(\"수정 하시겠습니까?\")) {\r\n");
      out.write("        frm.modeType.value = \"mod\";\r\n");
      out.write("        frm.action   = \"member_write_proc.jsp\";\r\n");
      out.write("        frm.encoding = \"multipart/form-data\";\r\n");
      out.write("        frm.submit();\r\n");
      out.write("    }\r\n");
      out.write("            \r\n");
      out.write("}    \r\n");
      out.write("    function getSpace(cnt) {\r\n");
      out.write("        var spc = \"\";\r\n");
      out.write("        for (i = 0; i < cnt; i++) {\r\n");
      out.write("            spc += \" \";\r\n");
      out.write("        }        \r\n");
      out.write("        return spc;\r\n");
      out.write("    }\r\n");
      out.write("    \r\n");
      out.write("   \r\n");
      out.write("\r\n");
      out.write("\t//레이어 팝업 닫기\r\n");
      out.write("\tfunction closeLayer(IdName){\r\n");
      out.write("\t\tvar pop = document.getElementById(IdName);\r\n");
      out.write("\t\tpop.style.display = \"none\";\r\n");
      out.write("\t}\r\n");
      out.write("    \r\n");
      out.write("\t\r\n");
      out.write("    function id_find( ){\r\n");
      out.write("        var frm = document.");
      out.print(formName);
      out.write(";\r\n");
      out.write("  \t\tvar pop = document.getElementById('layerPop2');\r\n");
      out.write("  \t\tdocument.all['moveFrame'].src = './id_find_popup.jsp';\r\n");
      out.write(" \t\t\tpop.style.display = \"block\";\r\n");
      out.write(" \t\t\tpop.style.top =  \"170px\";\r\n");
      out.write(" \t\t\tpop.style.left = \"400px\";\r\n");
      out.write("  \t}\r\n");
      out.write("    function zipcode_find( ){\r\n");
      out.write("        var frm = document.");
      out.print(formName);
      out.write(";\r\n");
      out.write("  \t\tvar pop = document.getElementById('layerPop3');\r\n");
      out.write("  \t\tdocument.all['moveFrame2'].src = './zipcode_find_popup.jsp';\r\n");
      out.write(" \t\t\tpop.style.display = \"block\";\r\n");
      out.write(" \t\t\tpop.style.top =  \"170px\";\r\n");
      out.write(" \t\t\tpop.style.left = \"400px\";\r\n");
      out.write("  \t}\r\n");
      out.write("    \r\n");
      out.write("    function onSetSelectInfo(value) {\r\n");
      out.write("        var frm = document.");
      out.print(formName);
      out.write(";\r\n");
      out.write("\t\t//값 넣기.\r\n");
      out.write("\t\tfor( var i = 0; i <= frm.elements.length - 1; i++ ){\r\n");
      out.write(" \t\t\tif( frm.elements[i].name == \"user_id\" )\r\n");
      out.write(" \t\t\t{\r\n");
      out.write(" \t\t\t\tfrm.elements[i].value = value;\r\n");
      out.write(" \t\t\t}\r\n");
      out.write("\t\t}\r\n");
      out.write("\t}\r\n");
      out.write("    \r\n");
      out.write("    function email_change(obj){\r\n");
      out.write("        var frm = document.");
      out.print(formName);
      out.write(";\r\n");
      out.write("\t\tfrm.email2.value = obj.value;\r\n");
      out.write("      \tfrm.email1.fucus();\r\n");
      out.write("    }\r\n");
      out.write("    \r\n");
      out.write("  //이미지 확장자 체크\r\n");
      out.write("\tfunction file_check_img(uploadImgObj)\r\n");
      out.write("\t{\r\n");
      out.write("\t\tvar enableUploadFileExt = [\"bmp\",\"jpg\",\"jpeg\",\"gif\",\"png\"];\r\n");
      out.write("\t\tvar uploadImgObjName = uploadImgObj.value;\r\n");
      out.write("\t\tvar upArrLeng = enableUploadFileExt.length;\r\n");
      out.write("\t\tvar chkExe = uploadImgObjName.split(\".\");\r\n");
      out.write("\t\t\r\n");
      out.write("\t\tvar chkFiles = false;\r\n");
      out.write("\t\t\r\n");
      out.write("\t\tfor(var i=0;i<upArrLeng;i++)\r\n");
      out.write("\t\t{\r\n");
      out.write("\t\t\tif(enableUploadFileExt[i] == chkExe[1].toLowerCase())\r\n");
      out.write("\t\t\t{\r\n");
      out.write("\t\t\t\tchkFiles = true;\r\n");
      out.write("\t\t\t\tbreak;\r\n");
      out.write("\t\t\t}\r\n");
      out.write("\t\t}\r\n");
      out.write("\t\tif(!chkFiles)\r\n");
      out.write("\t\t{\r\n");
      out.write("\t\t\talert(\"JPG, GIF, JPEG, BMP, PNG 파일만 등록 가능합니다.\");\r\n");
      out.write("\t\t\tuploadImgObj.outerHTML = uploadImgObj.outerHTML;\r\n");
      out.write("\t\t\treturn;\r\n");
      out.write("\t\t}\r\n");
      out.write("\t}\r\n");
      out.write("  \r\n");
      out.write("\tfunction evKey(el) { \r\n");
      out.write("\t\t   var v = el.value.replace(/[^0-9]/g, \"\"); \r\n");
      out.write("\t\t   if(v.length > 4) v = v.slice(0, 4) + \".\" + v.slice(4); \r\n");
      out.write("\t\t   if(v.length > 7) v = v.slice(0, 7) + \".\" + v.slice(7, 9); \r\n");
      out.write("\t\t   el.value = v; \r\n");
      out.write("\t\t} \r\n");
      out.write("  //-->\r\n");
      out.write("</script>\r\n");
      out.write("<form name=\"");
      out.print(formName);
      out.write("\" method=\"post\">\r\n");
      out.write("<input type=\"hidden\" name=\"modeType\"     value=\"");
      out.print(modeType    );
      out.write("\">\r\n");
      out.write("<input type=\"hidden\" name=\"hidden_picture\"     value=\"");
      out.print(picture    );
      out.write("\">\r\n");
      out.write("<input type=\"hidden\" name=\"user_cd\" value=\"");
      out.print(user_cd );
      out.write("\">  \r\n");
      out.write("<input type=\"hidden\" name=\"email\"   >\r\n");
      out.write("<input type=\"hidden\" name=\"hidden_address\"   >\r\n");
      out.write("\r\n");
      out.write("<table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\r\n");
      out.write("      \r\n");
      out.write("      <tr>\r\n");
      out.write("        <td valign=\"top\"><table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" style=\"border-top:2px solid #e0e0e0; border-bottom:2px solid #e0e0e0; width:784px;\">\r\n");
      out.write("            <tr>\r\n");
      out.write("              <td width=\"210\" class=\"bbbg\" style=\"padding-left:20px; font-weight:bold;\">컨퍼런스명</td>\r\n");
      out.write("              <td width=\"574\" style=\"padding-left:20px;\">");
      out.print(conference_name );
      out.write("</td>\r\n");
      out.write("            </tr>\r\n");
      out.write("            <tr>\r\n");
      out.write("              <td width=\"210\" class=\"bbbg\" style=\"padding-left:20px; font-weight:bold;\">아이디</td>\r\n");
      out.write("              <td width=\"574\" style=\"padding-left:20px;\">");
      out.print(user_id );
      out.write("</td>\r\n");
      out.write("            </tr>\r\n");
      out.write("            <tr>\r\n");
      out.write("              <td width=\"210\" class=\"bbbg\" style=\"padding-left:20px; font-weight:bold;\">이름</td>\r\n");
      out.write("              <td width=\"574\" style=\"padding-left:20px;\">");
      out.print(name);
      out.write("</td>\r\n");
      out.write("            </tr>\r\n");
      out.write("            <tr>\r\n");
      out.write("              <td width=\"210\" class=\"bbbg\" style=\"padding-left:20px; font-weight:bold;\">비밀번호</td>\r\n");
      out.write("              <td width=\"574\" style=\"padding-left:20px;\"><input type=\"password\" name=\"passwd\" value=\"\"></td>\r\n");
      out.write("            </tr>\r\n");
      out.write("            <tr>\r\n");
      out.write("              <td width=\"210\" class=\"bbbg\" style=\"padding-left:20px; font-weight:bold;\">비밀번호 확인</td>\r\n");
      out.write("              <td width=\"574\" style=\"padding-left:20px;\"><input type=\"password\" name=\"repasswd\" value=\"\"></td>\r\n");
      out.write("            </tr>\r\n");
      out.write("            <tr>\r\n");
      out.write("              <td width=\"210\" class=\"bbbg\" style=\"padding-left:20px; font-weight:bold;\">성별</td>\r\n");
      out.write("              <td width=\"574\" style=\"padding-left:20px;\">");
      out.print(("male".equals(sex)?"남자":"여자") );
      out.write("</td>\r\n");
      out.write("            </tr>\r\n");
      out.write("            <tr>\r\n");
      out.write("              <td width=\"210\" class=\"bbbg\" style=\"padding-left:20px; font-weight:bold;\">소속</td>\r\n");
      out.write("              <td width=\"574\" style=\"padding-left:20px;\">");
      out.print(company);
      out.write("</td>\r\n");
      out.write("            </tr>\r\n");
      out.write("            <tr>\r\n");
      out.write("              <td width=\"210\" class=\"bbbg\" style=\"padding-left:20px; font-weight:bold;\">국가</td>\r\n");
      out.write("              <td width=\"574\" style=\"padding-left:20px;\">\r\n");
      out.write("              \t");

              	while( rset3.next()){
                  	if (nation_id.equals(rset3.getString("nation_id"))){
                  		out.println(rset3.getString("nation"    ));
                  	}            	    
              	}
              	
      out.write("\r\n");
      out.write("              </td>\r\n");
      out.write("            </tr>\r\n");
      out.write("            <tr>\r\n");
      out.write("              <td width=\"210\" class=\"bbbg\" style=\"padding-left:20px; font-weight:bold;\">호칭</td>\r\n");
      out.write("              <td width=\"574\" style=\"padding-left:20px;\">\r\n");

    if(  rset4.row()==0) { 
      out.write("\r\n");
      out.write("                \r\n");
    
    } else {
        while( rset4.next()){
        	sel_appellation_id     = StringUtil.defaultIfEmpty(rset4.getString("appellation_id" ), "");
        	sel_appellation    = StringUtil.defaultIfEmpty(rset4.getString("appellation"), "");

      out.write("\t\t\t\t\r\n");
      out.write("\t\t\t");
      out.print( sel_appellation_id.equals(appellation_id) ? sel_appellation : "" );
      out.write("\r\n");
      out.write("\t\t\t\r\n");
      } 
    }
    
      out.write("           \r\n");
      out.write("          \t\t</select>\r\n");
      out.write("              </td>\r\n");
      out.write("            </tr>\r\n");
      out.write("            <tr>\r\n");
      out.write("              <td width=\"210\" class=\"bbbg\" style=\"padding-left:20px; font-weight:bold;\">사진</td>\r\n");
      out.write("              <td width=\"574\" style=\"padding-left:20px;\">\r\n");
      out.write("              \t\t");

              	if (!"".equals(picture)){
              		out.println("<img src=/mice/upload/member/"+picture+"><br>");
              	}
              
      out.write("\r\n");
      out.write("              <input name=\"picture\" type=\"file\" onchange=\"file_check_img(this)\">\r\n");
      out.write("              </td>\r\n");
      out.write("            </tr>\r\n");
      out.write("            <tr>\r\n");
      out.write("              <td width=\"210\" class=\"bbbg\" style=\"padding-left:20px; font-weight:bold;\">핸드폰</td>\r\n");
      out.write("              <td width=\"574\" style=\"padding-left:20px;\">\r\n");
      out.write("              \t\t<select id=\"select_phoneFirst\" name=\"handphone1\" class=\"phone\">\r\n");
      out.write("\t\t\t\t\t\t<option value=\"\">선택</option>\r\n");
      out.write("\t\t\t\t\t\t<option value=\"010\"  ");
      out.print( "010".equals(handphone1) ? "selected" : "" );
      out.write(">010</option>\t\r\n");
      out.write("\t\t\t\t\t\t<option value=\"011\"   ");
      out.print( "011".equals(handphone1) ? "selected" : "" );
      out.write(">011</option>\t\r\n");
      out.write("\t\t\t\t\t\t<option value=\"016\"   ");
      out.print( "016".equals(handphone1) ? "selected" : "" );
      out.write(">016</option>\t\r\n");
      out.write("\t\t\t\t\t\t<option value=\"017\"  ");
      out.print( "017".equals(handphone1) ? "selected" : "" );
      out.write(" >017</option>\t\r\n");
      out.write("\t\t\t\t\t\t<option value=\"018\"   ");
      out.print( "018".equals(handphone1) ? "selected" : "" );
      out.write(">018</option>\t\r\n");
      out.write("\t\t\t\t\t\t<option value=\"019\"   ");
      out.print( "019".equals(handphone1) ? "selected" : "" );
      out.write(">019</option>\t\r\n");
      out.write("\t\t\t\t</select>\r\n");
      out.write("\t\t\t-\r\n");
      out.write("\t\t\t<input type=\"text\" class=\"text phone\" name=\"handphone2\" maxlength=\"4\" value=\"");
      out.print(handphone2);
      out.write("\" style=\"width: 40px\"  onkeyup=\"evKey(this)\"  />\r\n");
      out.write("\t\t\t-\r\n");
      out.write("\t\t\t<input type=\"text\" class=\"text phone\" name=\"handphone3\" maxlength=\"4\" value=\"");
      out.print(handphone3);
      out.write("\" style=\"width: 40px\"  onkeyup=\"evKey(this)\"  />\r\n");
      out.write("\t\t\t<input type=\"radio\" name=\"first_phone\" value=\"h\"  ");
      out.print( "h".equals(first_phone) ? "checked" : "" );
      out.write(" >\r\n");
      out.write("              </td>\r\n");
      out.write("            </tr>\r\n");
      out.write("            <tr>\r\n");
      out.write("              <td width=\"210\" class=\"bbbg\" style=\"padding-left:20px; font-weight:bold;\">전화번호</td>\r\n");
      out.write("              <td width=\"574\" style=\"padding-left:20px;\">\r\n");
      out.write("              \t\t<select id=\"select_phoneFirst\" name=\"phone1\" class=\"phone\">\r\n");
      out.write("\t\t\t\t\t\t<option value=\"\">선택</option>\r\n");
      out.write("\t\t\t\t\t\t<option value=\"02\"  ");
      out.print( "02".equals(phone1) ? "selected" : "" );
      out.write(">02</option>\t\r\n");
      out.write("\t\t\t\t\t\t<option value=\"031\"  ");
      out.print( "031".equals(phone1) ? "selected" : "" );
      out.write(">031</option>\t\r\n");
      out.write("\t\t\t\t\t\t<option value=\"032\"  ");
      out.print( "032".equals(phone1) ? "selected" : "" );
      out.write(">032</option>\t\r\n");
      out.write("\t\t\t\t\t\t<option value=\"033\"  ");
      out.print( "033".equals(phone1) ? "selected" : "" );
      out.write(">033</option>\t\r\n");
      out.write("\t\t\t\t\t\t<option value=\"041\" ");
      out.print( "041".equals(phone1) ? "selected" : "" );
      out.write(" >041</option>\t\r\n");
      out.write("\t\t\t\t\t\t<option value=\"042\"  ");
      out.print( "042".equals(phone1) ? "selected" : "" );
      out.write(">042</option>\t\r\n");
      out.write("\t\t\t\t\t\t<option value=\"043\"  ");
      out.print( "043".equals(phone1) ? "selected" : "" );
      out.write(">043</option>\t\t\r\n");
      out.write("\t\t\t\t\t\t<option value=\"051\"  ");
      out.print( "051".equals(phone1) ? "selected" : "" );
      out.write(">051</option>\t\t\r\n");
      out.write("\t\t\t\t\t\t<option value=\"052\"  ");
      out.print( "052".equals(phone1) ? "selected" : "" );
      out.write(">052</option>\t\t\r\n");
      out.write("\t\t\t\t\t\t<option value=\"053\"  ");
      out.print( "053".equals(phone1) ? "selected" : "" );
      out.write(">053</option>\t\t\r\n");
      out.write("\t\t\t\t\t\t<option value=\"054\"  ");
      out.print( "054".equals(phone1) ? "selected" : "" );
      out.write(">054</option>\t\t\r\n");
      out.write("\t\t\t\t\t\t<option value=\"055\" ");
      out.print( "055".equals(phone1) ? "selected" : "" );
      out.write(" >055</option>\t\t\r\n");
      out.write("\t\t\t\t\t\t<option value=\"061\"  ");
      out.print( "061".equals(phone1) ? "selected" : "" );
      out.write(">061</option>\t\t\r\n");
      out.write("\t\t\t\t\t\t<option value=\"062\"  ");
      out.print( "062".equals(phone1) ? "selected" : "" );
      out.write(">062</option>\t\t\r\n");
      out.write("\t\t\t\t\t\t<option value=\"063\" ");
      out.print( "063".equals(phone1) ? "selected" : "" );
      out.write(" >063</option>\t\t\r\n");
      out.write("\t\t\t\t\t\t<option value=\"064\"  ");
      out.print( "064".equals(phone1) ? "selected" : "" );
      out.write(">064</option>\t\t\r\n");
      out.write("\t\t\t\t\t\t<option value=\"070\"  ");
      out.print( "070".equals(phone1) ? "selected" : "" );
      out.write(">070</option>\t\t\r\n");
      out.write("\t\t\t\t\t\t<option value=\"0505\"  ");
      out.print( "0505".equals(phone1) ? "selected" : "" );
      out.write(">0505</option>\t\r\n");
      out.write("\t\t\t\t</select>\r\n");
      out.write("\t\t\t-\r\n");
      out.write("\t\t\t<input type=\"text\" class=\"text phone\" name=\"phone2\" maxlength=\"4\" value=\"");
      out.print(phone2);
      out.write("\" style=\"width: 40px\"  onkeyup=\"evKey(this)\"  />\r\n");
      out.write("\t\t\t-\r\n");
      out.write("\t\t\t<input type=\"text\" class=\"text phone\" name=\"phone3\" maxlength=\"4\" value=\"");
      out.print(phone3);
      out.write("\" style=\"width: 40px\"  onkeyup=\"evKey(this)\"  />\r\n");
      out.write("\t\t\t<input type=\"radio\" name=\"first_phone\" value=\"p\" ");
      out.print( "p".equals(first_phone) ? "checked" : "" );
      out.write(">\r\n");
      out.write("              </td>\r\n");
      out.write("            </tr>\r\n");
      out.write("            <tr>\r\n");
      out.write("              <td width=\"210\" class=\"bbbg\" style=\"padding-left:20px; font-weight:bold;\">이메일</td>\r\n");
      out.write("              <td width=\"574\" style=\"padding-left:20px;\">\r\n");
      out.write("              \t<input type=\"text\" class=\"text email\" name=\"email1\"  maxlength =\"20\" value=\"");
      out.print( email1  );
      out.write("\"  style=\"width: 90px\" />\r\n");
      out.write("\t\t\t\t@\r\n");
      out.write("\t\t\t\t<input type=\"text\" class=\"text email\" name=\"email2\" id=\"email\"  maxlength =\"20\" value=\"");
      out.print( email2  );
      out.write("\" style=\"width: 110px\"  />\r\n");
      out.write("\t\t\t\t<select class=\"email_selector\"  onchange=\"email_change(this)\">\r\n");
      out.write("\t\t\t\t\t<option value=\"\">선택</option>\r\n");
      out.write("\t\t\t\t\t<option value=\"naver.com\">naver.com</option>\r\n");
      out.write("\t\t\t\t\t<option value=\"chol.com\">chol.com</option>\r\n");
      out.write("\t\t\t\t\t<option value=\"dreamwiz.com\">dreamwiz.com</option>\r\n");
      out.write("\t\t\t\t\t<option value=\"empal.com\">empal.com</option>\r\n");
      out.write("\t\t\t\t\t<option value=\"freechal.com\">freechal.com</option>\r\n");
      out.write("\t\t\t\t\t<option value=\"gmail.com\">gmail.com</option>\r\n");
      out.write("\t\t\t\t\t<option value=\"hanafos.com\">hanafos.com</option>\r\n");
      out.write("\t\t\t\t\t<option value=\"hanmail.net\">hanmail.net</option>\r\n");
      out.write("\t\t\t\t\t<option value=\"hitel.net\">hitel.net</option>\r\n");
      out.write("\t\t\t\t\t<option value=\"hotmail.com\">hotmail.com</option>\r\n");
      out.write("\t\t\t\t\t<option value=\"korea.com\">korea.com</option>\r\n");
      out.write("\t\t\t\t\t<option value=\"lycos.co.kr\">lycos.co.kr</option>\r\n");
      out.write("\t\t\t\t\t<option value=\"nate.com\">nate.com</option>\r\n");
      out.write("\t\t\t\t\t<option value=\"netian.com\">netian.com</option>\r\n");
      out.write("\t\t\t\t\t<option value=\"yahoo.co.kr\">yahoo.co.kr</option>\r\n");
      out.write("\t\t\t\t</select>\r\n");
      out.write("              \t\t\r\n");
      out.write("              </td>\r\n");
      out.write("            </tr>\r\n");
      out.write("            <tr>\r\n");
      out.write("              <td width=\"210\" class=\"bbbg\" style=\"padding-left:20px; font-weight:bold;\">주소</td>\r\n");
      out.write("              <td width=\"574\" style=\"padding-left:20px;\">\r\n");
      out.write("              \t\t<input type=\"text\" name=\"zipcode\" id=\"zipcode\" value=\"");
      out.print(zipcode);
      out.write("\" readonly=\"readonly\"  style=\"width: 50px\">\r\n");
      out.write("\t\t\t\t\t<input type=\"button\" value=\"찾아보기\" onclick=\"zipcode_find();\"/>\r\n");
      out.write("\t\t\t\t\t<br>\r\n");
      out.write("\t\t\t\t\t<input type=\"text\" name=\"address\" id=\"address\" value=\"");
      out.print(address);
      out.write("\"  style=\"width: 380px\">\r\n");
      out.write("              </td>\r\n");
      out.write("            </tr>\r\n");
      out.write("            <tr>\r\n");
      out.write("              <td width=\"210\" class=\"bbbg\" style=\"padding-left:20px; font-weight:bold;\">개인정보 보호</td>\r\n");
      out.write("              <td width=\"574\" style=\"padding-left:20px;\"><input type=\"radio\" name=\"question_authority\" value=\"y\" ");
      out.print((StringUtil.strMatch(question_authority,"y").equals("y")?"checked":"") );
      out.write(">노출\r\n");
      out.write("               <input type=\"radio\" name=\"question_authority\" value=\"n\" ");
      out.print((StringUtil.strMatch(question_authority,"n").equals("n")?"checked":"") );
      out.write(">비노출</td>\r\n");
      out.write("            </tr>\r\n");
      out.write("        </table></td>\r\n");
      out.write("      </tr>\r\n");
      out.write("      <tr>\r\n");
      out.write("        <td height=\"50\" align=\"center\">\r\n");
      out.write("        \t<a href=\"javascript:goModifyProc();\"><img src=\"");
      out.print(root_path);
      out.write("/images/bt_modify.gif\" alt=\"수정\" /></a>\r\n");
      out.write("         </td>\r\n");
      out.write("      </tr>\r\n");
      out.write("    </table>\r\n");
      out.write("    \r\n");
      out.write("</form>\t\t\r\n");
      out.write("\r\n");
      out.write("<!-- 팝업 -->\r\n");
      out.write("<div  id=\"layerPop2\" >\r\n");
      out.write(" <iframe id=\"moveFrame\" frameborder='0' height='100%' width='100%' scrolling='no' src=''></iframe>\r\n");
      out.write("</div>\r\n");
      out.write("<div  id=\"layerPop3\" >\r\n");
      out.write(" <iframe id=\"moveFrame2\" frameborder='0' height='100%' width='100%' scrolling='no' src=''></iframe>\r\n");
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
