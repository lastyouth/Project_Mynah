<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<% request.setCharacterEncoding("UTF-8"); %>
<%
	String root_path = request.getContextPath();
%>
<%@ page import = "java.util.*"  %>
<%@ page import = "sips.common.lib.util.*" %>
<%@ page import = "sips.common.lib.value.*" %> 
<%@ page import = "sips.dept.menu.*"   %>
<%@ page import = "sips.dept.contents.Present" %>
<%@ page import = "org.apache.commons.fileupload.servlet.*" %>
<%@ page import = "org.apache.commons.fileupload.disk.*" %>
<%@ page import = "org.apache.commons.fileupload.*"%>
<%@ page import = "java.io.*"    %>

<%
Present            present     = new Present();
    ParamValue     parmValue     = new ParamValue();
    ResultSetValue rset          = null;

    String worker_cd   = (String)session.getAttribute("USER_CD");
    String conference_id        = "";
    
    String modeType           = "";
    String present_id     = "";
    String present_name    = "";
    String start_date    = "";
    String end_date    = "";
    String present_image    = "";
    String contents    = "";
    String hidden_present_image    = "";
    String manufacturer_id = "";

    String b_mode             = "";
        
    String searchTarget       = "";
    String keyword            = "";
    String searchFlag         = "";
    String strMsg             = "";    
    int    cnt = 0;
    int    cnt2 = 0;
    boolean  isOk = true;
    String originSavePath = request.getRealPath("\\")+"upload\\present\\";			//저장될 경로
     
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
	String imgExt = "jpg, JPG, jpeg, JPEG, gif, GIF, bmp, BMP, png, PNG";
    
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
						if (fileitem.getFieldName().equals("conference_id"))conference_id = fileitem.getString("UTF-8");	
						if (fileitem.getFieldName().equals("modeType"))modeType = fileitem.getString("UTF-8");	
						if (fileitem.getFieldName().equals("present_id"))present_id = fileitem.getString("UTF-8");	
						if (fileitem.getFieldName().equals("manufacturer_id"))manufacturer_id = fileitem.getString("UTF-8");	
						if (fileitem.getFieldName().equals("present_name")) present_name = fileitem.getString("UTF-8");	
						if (fileitem.getFieldName().equals("start_date")) start_date = fileitem.getString("UTF-8");	
						if (fileitem.getFieldName().equals("end_date")) end_date = fileitem.getString("UTF-8");	
						if (fileitem.getFieldName().equals("contents")) contents = fileitem.getString("UTF-8");	
						if (fileitem.getFieldName().equals("hidden_present_image")) hidden_present_image = fileitem.getString("UTF-8");	
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
								
																
								if(fileitem.getFieldName().equals("present_image")) {
									present_image = tmpFileName;
								}
							}
						}
					}
				}
				
				//수정 삭제의 경우 비교.
				if(present_image.equals("")) {
					present_image = hidden_present_image;
				}

			} else {
				int overSize = (request.getContentLength() / 1000000);
				out.println("<script>alert('파일의 크기는 10MB까지 입니다. 올리신 파일 용량은"+overSize+"MB입니다');				");
				out.println("location.href=history.back();													");
				out.println("</script>																			");
				if(true) return;
			}
		} else {
			present_id = request.getParameter("present_id");
			modeType = request.getParameter("modeType");
		}
	} catch(Exception e) {
		
	}

    parmValue.put("present_id"    , present_id    );
    parmValue.put("manufacturer_id"    , manufacturer_id    );
    parmValue.put("present_name"        , present_name        );    
    parmValue.put("conference_id"       , conference_id       );
    parmValue.put("start_date"       , start_date       );
    parmValue.put("end_date"       , end_date       );
    parmValue.put("contents"       , contents       );
    parmValue.put("present_image"       , present_image       );
    parmValue.put("worker_cd"    , worker_cd    );
//System.out.println("conference_id------------>"+conference_id);
    try {	   
        if( isOk ) {
            if( "ins".equals(modeType)){
            	present.presentInsert(parmValue);
            } else if( "mod".equals(modeType)){
            	present.presentModify(parmValue);
            } else if( "del".equals(modeType)){  
            	present.presentDeleteContents(parmValue);  
            }
        }
        
%>
<form name="frmBrd" action="present.jsp" method="post">
<input type="hidden" name="conference_id"     value="<%=conference_id    %>">
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

