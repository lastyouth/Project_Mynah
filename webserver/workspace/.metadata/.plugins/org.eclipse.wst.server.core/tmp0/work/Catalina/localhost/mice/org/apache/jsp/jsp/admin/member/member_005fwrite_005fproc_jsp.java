/*
 * Generated by the Jasper component of Apache Tomcat
 * Version: Apache Tomcat/8.0.24
 * Generated at: 2015-08-19 05:03:08 UTC
 * Note: The last modified time of this file was set to
 *       the last modified time of the source file after
 *       generation to assist with modification tracking.
 */
package org.apache.jsp.jsp.admin.member;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import java.util.*;
import sips.common.lib.util.*;
import sips.common.lib.value.*;
import sips.dept.contents.Member;
import org.apache.commons.fileupload.servlet.*;
import org.apache.commons.fileupload.disk.*;
import org.apache.commons.fileupload.*;
import java.io.*;

public final class member_005fwrite_005fproc_jsp extends org.apache.jasper.runtime.HttpJspBase
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
    _jspx_imports_packages.add("java.io");
    _jspx_imports_packages.add("org.apache.commons.fileupload");
    _jspx_imports_packages.add("javax.servlet.jsp");
    _jspx_imports_packages.add("org.apache.commons.fileupload.servlet");
    _jspx_imports_packages.add("org.apache.commons.fileupload.disk");
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

      out.write('\r');
      out.write('\n');
 request.setCharacterEncoding("UTF-8"); 
      out.write('\r');
      out.write('\n');

	String root_path = request.getContextPath();

      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write(" \r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");

	Member            member     = new Member();
    ParamValue     parmValue     = new ParamValue();
    ResultSetValue rset          = null;
    
    String modeType           = "";
    String user_cd       = "";

    String conference_id       = "";
    String  authority     = "";
    String  name      = "";
    String  sex      = "";
    String  company      = "";
    String  nation_id       = "";
    String  appellation_id       = "";
    String phone1   = "";
    String phone2  = "";
    String phone3   = "";
    String handphone1   = "";
    String handphone2   = "";
    String handphone3   = "";
    String first_phone   = "";
    String  email1      = "";
    String  email2      = "";
    String  address     = "";
    String  address2     = "";
    String  zipcode     = "";
    String  authoriry_1      = "";
    String  authoriry_2       = "";
    String  authoriry_3       = "";
    String  authoriry_4      = "";
    String  authoriry_5      = "";
    String  authoriry_6       = "";
    String  authoriry_7     = "";
    String  authoriry_8      = "";
    String  authoriry_9      = "";
    String  question_authority    = "";
    String  research_authoriry    = "";
    String  picture    = "";
    String hidden_picture    = "";
    String passwd = "";
    String user_id = "";
    String street_address = "";
    String street_address_detail = "";
    String city = "";
    String state = "";
    String postal_code = "";
    String qrcode_id = "";
    
//System.out.println("authoriry_3----------->"+authoriry_3);    	
    String b_mode             = "";
        
    String searchTarget       = "";
    String keyword            = "";
    String searchFlag         = "";
    String strMsg             = "";    
    int    cnt = 0;
    int    cnt2 = 0;
    boolean  isOk = true;
	boolean result = false;
	
	String originSavePath = request.getRealPath("\\")+"upload\\member\\";			//저장될 경로
	
	File uploadFile = null;
	int idx = 0;
	String fileName = "";
	String tmpFileName = "";
	String thumbFileName = "";
	String[] strExt;	//파일을 파일명과 확장자 분리.
	String Ext="";		//확장자명
	
	//이미지 파일 체크확장자
	String imgExt = "jpg, JPG, jpeg, JPEG, gif, GIF, bmp, BMP, png, PNG";
	
      out.write('\r');
      out.write('\n');
      out.write('	');

		try {
			if(FileUpload.isMultipartContent(request)) {
				DiskFileUpload fileupload = new DiskFileUpload();								//메모리에 저장
				fileupload.setSizeMax(1024 * 1024 * 10);										//최대 10M까지 업로드 가능.
				fileupload.setHeaderEncoding("UTF-8");
				
				if(request.getContentLength() < fileupload.getSizeMax()) {
					List<Object> fileItemList = fileupload.parseRequest(request);				//파일 item만 리턴.
					int size = fileItemList.size();
					int j = 0;
					for(int i=0;i < size;i++) {
						FileItem fileitem = (FileItem) fileItemList.get(i); 
						tmpFileName = "";
						if(fileitem.isFormField()){												//파라미터일 경우 true
							if (fileitem.getFieldName().equals("conference_id"))conference_id = fileitem.getString("UTF-8");	
							if (fileitem.getFieldName().equals("authority"))authority = fileitem.getString("UTF-8");	
							if (fileitem.getFieldName().equals("name"))name = fileitem.getString("UTF-8");	
							if (fileitem.getFieldName().equals("sex"))sex = fileitem.getString("UTF-8");	
							if (fileitem.getFieldName().equals("company"))company = fileitem.getString("UTF-8");	
							if (fileitem.getFieldName().equals("nation_id"))nation_id = fileitem.getString("UTF-8");	
							if (fileitem.getFieldName().equals("appellation_id"))appellation_id = fileitem.getString("UTF-8");	
							if (fileitem.getFieldName().equals("phone1"))phone1 = fileitem.getString("UTF-8");		
							if (fileitem.getFieldName().equals("phone2"))phone2 = fileitem.getString("UTF-8");		
							if (fileitem.getFieldName().equals("phone3"))phone3 = fileitem.getString("UTF-8");		
							if (fileitem.getFieldName().equals("handphone1"))handphone1 = fileitem.getString("UTF-8");		
							if (fileitem.getFieldName().equals("handphone2"))handphone2 = fileitem.getString("UTF-8");		
							if (fileitem.getFieldName().equals("handphone3"))handphone3 = fileitem.getString("UTF-8");		
							if (fileitem.getFieldName().equals("first_phone"))first_phone = fileitem.getString("UTF-8");	
							if (fileitem.getFieldName().equals("email1"))email1 = fileitem.getString("UTF-8");	
							if (fileitem.getFieldName().equals("email2"))email2 = fileitem.getString("UTF-8");	
							if (fileitem.getFieldName().equals("address"))address = fileitem.getString("UTF-8");	
							if (fileitem.getFieldName().equals("address2"))address2 = fileitem.getString("UTF-8");	
							if (fileitem.getFieldName().equals("zipcode"))zipcode = fileitem.getString("UTF-8");	
							if (fileitem.getFieldName().equals("authoriry_1"))authoriry_1 = fileitem.getString("UTF-8");	
							if (fileitem.getFieldName().equals("authoriry_2"))authoriry_2 = fileitem.getString("UTF-8");	
							if (fileitem.getFieldName().equals("authoriry_3"))authoriry_3 = fileitem.getString("UTF-8");	
							if (fileitem.getFieldName().equals("authoriry_4"))authoriry_4 = fileitem.getString("UTF-8");	
							if (fileitem.getFieldName().equals("authoriry_5"))authoriry_5 = fileitem.getString("UTF-8");	
							if (fileitem.getFieldName().equals("authoriry_6"))authoriry_6 = fileitem.getString("UTF-8");	
							if (fileitem.getFieldName().equals("authoriry_7"))authoriry_7 = fileitem.getString("UTF-8");	
							if (fileitem.getFieldName().equals("authoriry_8"))authoriry_8 = fileitem.getString("UTF-8");	
							if (fileitem.getFieldName().equals("authoriry_9"))authoriry_9 = fileitem.getString("UTF-8");	
							if (fileitem.getFieldName().equals("question_authority"))question_authority = fileitem.getString("UTF-8");
							if (fileitem.getFieldName().equals("research_authoriry"))research_authoriry = fileitem.getString("UTF-8");
							if (fileitem.getFieldName().equals("picture"))picture = fileitem.getString("UTF-8");
							if (fileitem.getFieldName().equals("passwd"))passwd = fileitem.getString("UTF-8");
							if (fileitem.getFieldName().equals("hidden_picture"))hidden_picture = fileitem.getString("UTF-8");
							if (fileitem.getFieldName().equals("user_id"))user_id = fileitem.getString("UTF-8");
							if (fileitem.getFieldName().equals("modeType"))modeType = fileitem.getString("UTF-8");
							if (fileitem.getFieldName().equals("user_cd"))user_cd = fileitem.getString("UTF-8");
							if (fileitem.getFieldName().equals("street_address"))street_address = fileitem.getString("UTF-8");
							if (fileitem.getFieldName().equals("street_address_detail"))street_address_detail = fileitem.getString("UTF-8");
							if (fileitem.getFieldName().equals("city"))city = fileitem.getString("UTF-8");
							if (fileitem.getFieldName().equals("state"))state = fileitem.getString("UTF-8");
							if (fileitem.getFieldName().equals("postal_code"))postal_code = fileitem.getString("UTF-8");
							if (fileitem.getFieldName().equals("qrcode_id"))qrcode_id = fileitem.getString("UTF-8");
						} else {
							if (!(fileitem.getName()).equals("")) {
								if(fileitem.getSize() > 0) {
									idx = fileitem.getName().lastIndexOf("\\");					//getname()은 경로를 다 가져오기 때문에 lastindexof로 잘라냄.
									
									if(idx == 1)
									{
										idx = fileitem.getName().lastIndexOf("/");								
									}
									fileName = fileitem.getName().substring(idx + 1);
									
									if (!fileName.equals("")) {
										int pos = fileName.lastIndexOf(".");
										Ext = fileName.substring(pos+1);	//파일명과 확장자명 분리
										
										if (imgExt.indexOf(Ext) == -1 )
										{
											out.println("<script>alert('GIF,JPG,JPEG,PNG,BMP 확장자만 업로드 가능합니다.');	");
											//out.println("location.href=history.back();							");
											out.println("</script>");
											if(true) return;
										}
									}
									
									String upload_time = ""+System.currentTimeMillis();
									tmpFileName = upload_time +"." + Ext;
									//thumbFileName = "thumb_"+tmpFileName;
									
									DateUtil.sleep(10);
									
									//실질적인 저장 부분.
									uploadFile = new File(originSavePath, tmpFileName);
									fileitem.write(uploadFile);
									
									if(fileitem.getFieldName().equals("picture")) {
										picture = tmpFileName;
									}
								}
							}
						}
					}
					 System.out.println("picture------------>"+picture);
					
					//수정 삭제의 경우 비교.
					if(picture.equals("")) {
						picture = hidden_picture;
					}

				} else {
					int overSize = (request.getContentLength() / 1000000);
					out.println("<script>alert('파일의 크기는 10MB까지 입니다. 올리신 파일 용량은"+overSize+"MB입니다');				");
					out.println("location.href=history.back();													");
					out.println("</script>																			");
					if(true) return;
				}
			} else {
				user_cd = request.getParameter("user_cd");
				modeType = request.getParameter("modeType");
			}
		} catch(Exception e) {
			
		}
	
	if(!"y".equals(authoriry_1))	authoriry_1 = "n";
    if(!"y".equals(authoriry_2))	authoriry_2 = "n";
    if(!"y".equals(authoriry_3))	authoriry_3 = "n";
    if(!"y".equals(authoriry_4))	authoriry_4 = "n";
    if(!"y".equals(authoriry_5))	authoriry_5 = "n";
    if(!"y".equals(authoriry_6))	authoriry_6 = "n";
    if(!"y".equals(authoriry_7))	authoriry_7 = "n";
    if(!"y".equals(authoriry_8))	authoriry_8 = "n";
    if(!"y".equals(authoriry_9))	authoriry_9 = "n";
    
		parmValue.put("conference_id"    , conference_id    );
		parmValue.put("authority"    , authority    );
		parmValue.put("name"    , name    );
	    parmValue.put("sex"        , sex        );    
	    parmValue.put("company"       , company      );
	    parmValue.put("nation_id"         , nation_id         );
	    parmValue.put("appellation_id"        , appellation_id        );      
	    parmValue.put("phone1"        , phone1        );          
	    parmValue.put("phone2"        , phone2        );          
	    parmValue.put("phone3"        , phone3        );          
	    parmValue.put("handphone1"        , handphone1        );          
	    parmValue.put("handphone2"        , handphone2        );          
	    parmValue.put("handphone3"        , handphone3        );          
	    parmValue.put("first_phone"        , first_phone        );      
	    parmValue.put("email"        , email1+"@"+email2        );      
	    parmValue.put("address"        , address        );        
	    parmValue.put("zipcode"        , zipcode        );      
	    parmValue.put("authoriry_1"        , authoriry_1        );      
	    parmValue.put("authoriry_2"        , authoriry_2       );      
	    parmValue.put("authoriry_3"        , authoriry_3        );      
	    parmValue.put("authoriry_4"        , authoriry_4        );      
	    parmValue.put("authoriry_5"        , authoriry_5        );      
	    parmValue.put("authoriry_6"        , authoriry_6       );      
	    parmValue.put("authoriry_7"        , authoriry_7        );      
	    parmValue.put("authoriry_8"        , authoriry_8        );      
	    parmValue.put("authoriry_9"        , authoriry_9        );      
	    parmValue.put("picture"        , picture        );       
	    parmValue.put("hidden_picture"        , hidden_picture        );       
	    parmValue.put("modeType"        , modeType        );    
	    parmValue.put("user_cd"        , user_cd        );    
	    parmValue.put("user_id"        , user_id        );    
	    parmValue.put("question_authority"        , question_authority        ); 
	    parmValue.put("research_authoriry"        , research_authoriry        ); 
	    parmValue.put("passwd"        , passwd        );    
	    parmValue.put("street_address"        , street_address        );    
	    parmValue.put("street_address_detail"        , street_address_detail        );    
	    parmValue.put("city"        , city        );    
	    parmValue.put("state"        , state        );    
	    parmValue.put("postal_code"        , postal_code        );    
	    parmValue.put("qrcode_id"        , qrcode_id        );    
	    
	   System.out.println("authoriry_9------------>"+authoriry_9);
	    System.out.println("conference_id------------>"+conference_id);
	    System.out.println("user_cd------------>"+user_cd);
	         
    try {	   
        if( isOk ) {
            if( "ins".equals(modeType)){
            	member.memberInsert(parmValue);
            } else if( "mod".equals(modeType)){
            	member.memberModify(parmValue);
            } else if( "del".equals(modeType)){  
            	 member.memberListDeleteContents(parmValue);  
            }
        } 
        

      out.write("\r\n");
      out.write("<form name=\"frmBrd\" action=\"member.jsp\" method=\"post\">\r\n");
      out.write("</form>\r\n");
      out.write("<script language=\"javascript\">\r\n");
      out.write("\tdocument.frmBrd.submit();\r\n");
      out.write("</script>  \r\n");
      out.write("  \r\n");
      out.write("\r\n");
  }
    catch (Exception e) {
        System.out.println(e.toString());
        out.println(e.toString());
        e.printStackTrace();
    }   

      out.write("\r\n");
      out.write("\r\n");
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
