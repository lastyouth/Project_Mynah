<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<% request.setCharacterEncoding("UTF-8"); %>
<%
	String root_path = request.getContextPath();
%>
<%@ page import = "java.util.*"  %>
<%@ page import = "sips.common.lib.util.*" %>
<%@ page import = "sips.common.lib.value.*" %> 
<%@ page import = "sips.dept.menu.*"   %>
<%@ page import = "sips.dept.contents.Help"   %>
<%@ page import = "org.apache.commons.fileupload.servlet.*" %>
<%@ page import = "org.apache.commons.fileupload.disk.*" %>
<%@ page import = "org.apache.commons.fileupload.*"%>
<%@ page import = "java.io.*"    %>

<%
Help            help     = new Help();
    ParamValue     parmValue     = new ParamValue();
    ResultSetValue rset          = null;
    
    String conference_id       = (String)session.getAttribute("CONFERENCE_ID");   
    String user_cd       = (String)session.getAttribute("USER_CD");   
    
    String modeType           = "";
    String help_id     = "";
    String title     = "";
    String contents    = "";
    String attached = "";
    String hidden_attached   = "";

    String b_mode             = "";
        
    String searchTarget       = "";
    String keyword            = "";
    String searchFlag         = "";
    String strMsg             = "";    
    int    cnt = 0;
    int    cnt2 = 0;
    boolean  isOk = true;
    String originSavePath = request.getRealPath("\\")+"upload\\help\\";			//저장될 경로
     
    ParamValue     menuParam = new ParamValue();
    MenuContents   menuMgr   = new MenuContents();
    ResultSetValue menuRset  = null;    

    File uploadFile = null;
	int idx = 0;
	String fileName = "";
	String tmpFileName = "";
	String thumbFileName = "";
	String[] strExt;	//파일을 파일명과 확장자 분리.
	String Ext="";		//확장자명
	
	//이미지 파일 체크확장자
	String imgExt = "jpg, JPG, jpeg, JPEG, gif, GIF, bmp, BMP, png, PNG, zip, ZIP, DOC, doc, pdf, PDF, xls, XLS";
    
	try {
		if(FileUpload.isMultipartContent(request)) {
			DiskFileUpload fileupload = new DiskFileUpload();								//메모리에 저장
			fileupload.setSizeMax(1024 * 1024 * 20);										//최대 20M까지 업로드 가능.
			fileupload.setHeaderEncoding("UTF-8");
			
			if(request.getContentLength() < fileupload.getSizeMax()) {
				List<Object> fileItemList = fileupload.parseRequest(request);				//파일 item만 리턴.
				int size = fileItemList.size();
				int j = 0;
				for(int i=0;i < size;i++) {
					FileItem fileitem = (FileItem) fileItemList.get(i); 
					tmpFileName = "";
					if(fileitem.isFormField()){												//파라미터일 경우 true
						if (fileitem.getFieldName().equals("help_id"))help_id = fileitem.getString("UTF-8");	
						if (fileitem.getFieldName().equals("modeType"))modeType = fileitem.getString("UTF-8");	
						if (fileitem.getFieldName().equals("contents"))contents = fileitem.getString("UTF-8");	
						if (fileitem.getFieldName().equals("title")) title = fileitem.getString("UTF-8");	
						//if (fileitem.getFieldName().equals("attached")) attached = fileitem.getString("UTF-8");	
						if (fileitem.getFieldName().equals("hidden_attached")) hidden_attached = fileitem.getString("UTF-8");	
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
										out.println("<script>alert('GIF,JPG,JPEG,PNG,BMP,ZIP, DOC, PDF, XLS 확장자만 업로드 가능합니다.');	");
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
								
																
								if(fileitem.getFieldName().equals("attached")) {
									attached = tmpFileName;
								}
							}
						}
					}
				}
				
				//수정 삭제의 경우 비교.
				if(attached.equals("")) {
					attached = hidden_attached;
				}

			} else {
				int overSize = (request.getContentLength() / 1000000);
				out.println("<script>alert('파일의 크기는 10MB까지 입니다. 올리신 파일 용량은"+overSize+"MB입니다');				");
				out.println("location.href=history.back();													");
				out.println("</script>																			");
				if(true) return;
			}
		} else {
			help_id = request.getParameter("help_id");
			modeType = request.getParameter("modeType");
			title = request.getParameter("title");
			contents = request.getParameter("contents");
		}
	} catch(Exception e) {
		
	}
    
    parmValue.put("help_id"    , help_id    );
    parmValue.put("user_cd"        , user_cd        );    
    parmValue.put("conference_id"       , conference_id       );
    parmValue.put("title"       , title       );
    parmValue.put("contents"       , contents       );
    parmValue.put("attached"       , attached       );
//System.out.println("conference_id------------>"+conference_id);
    try {	   
        if( isOk ) {
            if( "ins".equals(modeType)){
            	help.insertHelp(parmValue);
            } else if( "mod".equals(modeType)){
            	help.updateHelp(parmValue);
            } else if( "del".equals(modeType)){  
            	help.deleteHelp(parmValue);  
            }
        }
%>
<form name="frmBrd" action="help.jsp" method="post">
</form>
<script language="javascript">
	document.frmBrd.submit();
</script>  
  

<%  }
    catch (Exception e) {
        System.out.println(e.toString());
        out.println(e.toString());
        e.printStackTrace();
    }   
%>