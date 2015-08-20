<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<% request.setCharacterEncoding("UTF-8"); %>
<%
	String root_path = request.getContextPath();
%>
<%@ page import = "java.util.*"  %>
<%@ page import = "sips.common.lib.util.*" %>
<%@ page import = "sips.common.lib.value.*" %> 
<%@ page import = "sips.dept.menu.*"   %>
<%@ page import = "sips.dept.contents.Banner" %>
<%@ page import = "org.apache.commons.fileupload.servlet.*" %>
<%@ page import = "org.apache.commons.fileupload.disk.*" %>
<%@ page import = "org.apache.commons.fileupload.*"%>
<%@ page import = "java.io.*"    %>

<%
	Banner      banner     = new Banner();
    ParamValue     parmValue     = new ParamValue();
    ResultSetValue rset          = null;
    
    String conference_id        = "";
    String worker_cd         = (String)session.getAttribute("USER_CD");
    
    String modeType           = "";
    String banner_id     = "";
    String banner_1     = "";
    String banner_2     = "";
    String banner_3     = "";
    String banner_4     = "";
    String banner_5     = "";
    String banner_6     = "";
    String banner_7     = "";
    String banner_8     = "";
    String banner_9     = "";
    String banner_10     = "";
    String banner_11     = "";
    String hidden_banner_1    = "";
    String hidden_banner_2    = "";
    String hidden_banner_3    = "";
    String hidden_banner_4    = "";
    String hidden_banner_5    = "";
    String hidden_banner_6    = "";
    String hidden_banner_7    = "";
    String hidden_banner_8    = "";
    String hidden_banner_9    = "";
    String hidden_banner_10    = "";
    String hidden_banner_11    = "";
    String landing_url_1 = "";
    String landing_url_2 = "";
    String landing_url_3 = "";
    String landing_url_4 = "";
    String landing_url_5 = "";
    String landing_url_6 = "";
    String landing_url_7 = "";
    String landing_url_8 = "";
    String landing_url_9 = "";
    String landing_url_10 = "";
    String landing_url_11 = "";
    String ckb_banner_1 = "";
    String ckb_banner_2 = "";
    String ckb_banner_3 = "";
    String ckb_banner_4 = "";
    String ckb_banner_5 = "";
    String ckb_banner_6 = "";
    String ckb_banner_7 = "";
    String ckb_banner_8 = "";
    String ckb_banner_9 = "";
    String ckb_banner_10 = "";
    String ckb_banner_11 = "";

    String b_mode             = "";
        
    String searchTarget       = "";
    String keyword            = "";
    String searchFlag         = "";
    String strMsg             = "";    
    int    cnt = 0;
    int    cnt2 = 0;
    boolean  isOk = true;
    String originSavePath = request.getRealPath("\\")+"upload\\banner\\";			//저장될 경로
     
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
						if (fileitem.getFieldName().equals("modeType"))modeType = fileitem.getString("UTF-8");	
						if (fileitem.getFieldName().equals("conference_id"))conference_id = fileitem.getString("UTF-8");	
						if (fileitem.getFieldName().equals("banner_id"))banner_id = fileitem.getString("UTF-8");	
						if (fileitem.getFieldName().equals("banner_1"))banner_1 = fileitem.getString("UTF-8");		
						if (fileitem.getFieldName().equals("banner_2"))banner_2 = fileitem.getString("UTF-8");		
						if (fileitem.getFieldName().equals("banner_3"))banner_3 = fileitem.getString("UTF-8");		
						if (fileitem.getFieldName().equals("banner_4"))banner_4 = fileitem.getString("UTF-8");		
						if (fileitem.getFieldName().equals("banner_5"))banner_5 = fileitem.getString("UTF-8");		
						if (fileitem.getFieldName().equals("banner_6"))banner_6 = fileitem.getString("UTF-8");		
						if (fileitem.getFieldName().equals("banner_7"))banner_7 = fileitem.getString("UTF-8");		
						if (fileitem.getFieldName().equals("banner_8"))banner_8 = fileitem.getString("UTF-8");		
						if (fileitem.getFieldName().equals("banner_9"))banner_9 = fileitem.getString("UTF-8");		
						if (fileitem.getFieldName().equals("banner_10"))banner_10 = fileitem.getString("UTF-8");		
						if (fileitem.getFieldName().equals("banner_11"))banner_11 = fileitem.getString("UTF-8");	
						if (fileitem.getFieldName().equals("landing_url_1"))landing_url_1 = fileitem.getString("UTF-8");		
						if (fileitem.getFieldName().equals("landing_url_2"))landing_url_2 = fileitem.getString("UTF-8");		
						if (fileitem.getFieldName().equals("landing_url_3"))landing_url_3 = fileitem.getString("UTF-8");		
						if (fileitem.getFieldName().equals("landing_url_4"))landing_url_4 = fileitem.getString("UTF-8");		
						if (fileitem.getFieldName().equals("landing_url_5"))landing_url_5 = fileitem.getString("UTF-8");		
						if (fileitem.getFieldName().equals("landing_url_6"))landing_url_6 = fileitem.getString("UTF-8");		
						if (fileitem.getFieldName().equals("landing_url_7"))landing_url_7 = fileitem.getString("UTF-8");		
						if (fileitem.getFieldName().equals("landing_url_8"))landing_url_8 = fileitem.getString("UTF-8");		
						if (fileitem.getFieldName().equals("landing_url_9"))landing_url_9 = fileitem.getString("UTF-8");		
						if (fileitem.getFieldName().equals("landing_url_10"))landing_url_10 = fileitem.getString("UTF-8");		
						if (fileitem.getFieldName().equals("landing_url_11"))landing_url_11 = fileitem.getString("UTF-8");	
						if (fileitem.getFieldName().equals("hidden_banner_1"))hidden_banner_1 = fileitem.getString("UTF-8");		
						if (fileitem.getFieldName().equals("hidden_banner_2"))hidden_banner_2 = fileitem.getString("UTF-8");		
						if (fileitem.getFieldName().equals("hidden_banner_3"))hidden_banner_3 = fileitem.getString("UTF-8");		
						if (fileitem.getFieldName().equals("hidden_banner_4"))hidden_banner_4 = fileitem.getString("UTF-8");		
						if (fileitem.getFieldName().equals("hidden_banner_5"))hidden_banner_5 = fileitem.getString("UTF-8");		
						if (fileitem.getFieldName().equals("hidden_banner_6"))hidden_banner_6 = fileitem.getString("UTF-8");		
						if (fileitem.getFieldName().equals("hidden_banner_7"))hidden_banner_7 = fileitem.getString("UTF-8");		
						if (fileitem.getFieldName().equals("hidden_banner_8"))hidden_banner_8 = fileitem.getString("UTF-8");		
						if (fileitem.getFieldName().equals("hidden_banner_9"))hidden_banner_9 = fileitem.getString("UTF-8");		
						if (fileitem.getFieldName().equals("hidden_banner_10"))hidden_banner_10 = fileitem.getString("UTF-8");		
						if (fileitem.getFieldName().equals("hidden_banner_11"))hidden_banner_11 = fileitem.getString("UTF-8");		
						if (fileitem.getFieldName().equals("ckb_banner_1"))ckb_banner_1 = fileitem.getString("UTF-8");		
						if (fileitem.getFieldName().equals("ckb_banner_2"))ckb_banner_2 = fileitem.getString("UTF-8");		
						if (fileitem.getFieldName().equals("ckb_banner_3"))ckb_banner_3 = fileitem.getString("UTF-8");		
						if (fileitem.getFieldName().equals("ckb_banner_4"))ckb_banner_4 = fileitem.getString("UTF-8");		
						if (fileitem.getFieldName().equals("ckb_banner_5"))ckb_banner_5 = fileitem.getString("UTF-8");		
						if (fileitem.getFieldName().equals("ckb_banner_6"))ckb_banner_6 = fileitem.getString("UTF-8");		
						if (fileitem.getFieldName().equals("ckb_banner_7"))ckb_banner_7 = fileitem.getString("UTF-8");		
						if (fileitem.getFieldName().equals("ckb_banner_8"))ckb_banner_8 = fileitem.getString("UTF-8");		
						if (fileitem.getFieldName().equals("ckb_banner_9"))ckb_banner_9 = fileitem.getString("UTF-8");		
						if (fileitem.getFieldName().equals("ckb_banner_10"))ckb_banner_10 = fileitem.getString("UTF-8");		
						if (fileitem.getFieldName().equals("ckb_banner_11"))ckb_banner_11 = fileitem.getString("UTF-8");		
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
								

								if(fileitem.getFieldName().equals("banner_1"))  banner_1 = tmpFileName;
								if(fileitem.getFieldName().equals("banner_2"))  banner_2 = tmpFileName;
								if(fileitem.getFieldName().equals("banner_3"))  banner_3 = tmpFileName;
								if(fileitem.getFieldName().equals("banner_4"))  banner_4 = tmpFileName;
								if(fileitem.getFieldName().equals("banner_5"))  banner_5 = tmpFileName;
								if(fileitem.getFieldName().equals("banner_6"))  banner_6 = tmpFileName;
								if(fileitem.getFieldName().equals("banner_7"))  banner_7 = tmpFileName;
								if(fileitem.getFieldName().equals("banner_8"))  banner_8 = tmpFileName;
								if(fileitem.getFieldName().equals("banner_9"))  banner_9 = tmpFileName;
								if(fileitem.getFieldName().equals("banner_10"))  banner_10 = tmpFileName;
								if(fileitem.getFieldName().equals("banner_11"))  banner_11 = tmpFileName;
								
							}
						}
					}
				}
				
				//수정 삭제의 경우 비교.
			if(banner_1.equals(""))  banner_1 = hidden_banner_1;
			if(banner_2.equals(""))  banner_2 = hidden_banner_2;
			if(banner_3.equals(""))  banner_3 = hidden_banner_3;
			if(banner_4.equals(""))  banner_4 = hidden_banner_4;
			if(banner_5.equals(""))  banner_5 = hidden_banner_5;
			if(banner_6.equals(""))  banner_6 = hidden_banner_6;
			if(banner_7.equals(""))  banner_7 = hidden_banner_7;
			if(banner_8.equals(""))  banner_8 = hidden_banner_8;
			if(banner_9.equals(""))  banner_9 = hidden_banner_9;
			if(banner_10.equals(""))  banner_10 = hidden_banner_10;
			if(banner_11.equals(""))  banner_11= hidden_banner_11;

			} else {
				int overSize = (request.getContentLength() / 1000000);
				out.println("<script>alert('파일의 크기는 10MB까지 입니다. 올리신 파일 용량은"+overSize+"MB입니다');				");
				out.println("location.href=history.back();													");
				out.println("</script>																			");
				if(true) return;
			}
		} else {
			banner_id = request.getParameter("banner_id");
			modeType = request.getParameter("modeType");
		}
	} catch(Exception e) {
		
	}

    parmValue.put("banner_id"    , banner_id    );
    parmValue.put("conference_id"    , conference_id    );
    parmValue.put("banner_1"    , banner_1    );
    parmValue.put("banner_2"    , banner_2    );
    parmValue.put("banner_3"    , banner_3    );
    parmValue.put("banner_4"    , banner_4    );
    parmValue.put("banner_5"    , banner_5    );
    parmValue.put("banner_6"    , banner_6    );
    parmValue.put("banner_7"    , banner_7    );
    parmValue.put("banner_8"    , banner_8    );
    parmValue.put("banner_9"    , banner_9    );
    parmValue.put("banner_10"    , banner_10    );
    parmValue.put("banner_11"    , banner_11    );
    parmValue.put("landing_url_1"    , landing_url_1    );
    parmValue.put("landing_url_2"    , landing_url_2    );
    parmValue.put("landing_url_3"    , landing_url_3    );
    parmValue.put("landing_url_4"    , landing_url_4    );
    parmValue.put("landing_url_5"    , landing_url_5    );
    parmValue.put("landing_url_6"    , landing_url_6    );
    parmValue.put("landing_url_7"    , landing_url_7    );
    parmValue.put("landing_url_8"    , landing_url_8    );
    parmValue.put("landing_url_9"    , landing_url_9    );
    parmValue.put("landing_url_10"    , landing_url_10    );
    parmValue.put("landing_url_11"    , landing_url_11    );
    parmValue.put("ckb_banner_1"    , ckb_banner_1    );
    parmValue.put("ckb_banner_2"    , ckb_banner_2    );
    parmValue.put("ckb_banner_3"    , ckb_banner_3    );
    parmValue.put("ckb_banner_4"    , ckb_banner_4    );
    parmValue.put("ckb_banner_5"    , ckb_banner_5    );
    parmValue.put("ckb_banner_6"    , ckb_banner_6    );
    parmValue.put("ckb_banner_7"    , ckb_banner_7    );
    parmValue.put("ckb_banner_8"    , ckb_banner_8    );
    parmValue.put("ckb_banner_9"    , ckb_banner_9    );
    parmValue.put("ckb_banner_10"    , ckb_banner_10    );
    parmValue.put("ckb_banner_11"    , ckb_banner_11    );
    parmValue.put("worker_cd"    , worker_cd    );
//System.out.println("modeType------------>"+modeType);
    try {	   
        if( isOk ) {
        	if( "ins".equals(modeType)){
            	banner.bannerInsert(parmValue);
            } else if( "mod".equals(modeType)){
            	banner.bannerModify(parmValue);
            } else if( "del".equals(modeType)){  
            	banner.bannerDeleteContents(parmValue);  
            }
        }

%>
<form name="frmBrd" action="banner.jsp" method="post">
<input type="hidden" name="conference_id"     value="<%=conference_id    %>">
<input type="hidden" name="banner_id"     value="<%=banner_id    %>">
<input type="hidden" name="b_mode"     value="W">
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

