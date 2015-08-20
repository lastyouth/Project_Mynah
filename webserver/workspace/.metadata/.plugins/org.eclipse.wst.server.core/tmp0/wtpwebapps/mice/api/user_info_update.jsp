<%@ page language = "java" contentType="text/html; charset=UTF-8" %><%@ page import = "java.util.*"  %><%@ page import = "sips.common.lib.value.*" %><%@ page import = "sips.common.lib.util.*" %><%@ page import = "sips.dept.contents._Member" %><%@ page import = "org.apache.commons.fileupload.servlet.*" %><%@ page import = "org.apache.commons.fileupload.disk.*" %><%@ page import = "org.apache.commons.fileupload.*"%><%@ page import = "java.io.*"    %><%
    ParamValue     parmValue = new ParamValue(); 
	_Member         member     = new _Member();
    
    String user_cd       = "";
    String  name      = "";
    String  company      = "";
    String  appellation_id       = "";
    String phone1   = "";
    String phone2  = "";
    String phone3   = "";
    String  email      = "";
    String street_address = "";
    String street_address_detail = "";
    String city = "";
    String state = "";
    String postal_code = "";
    String nation_id = "";
    String  picture    = "";
    int rtl =  0;
    String rtlMsg = "";
    String flag = "false";
    
//System.out.println("authoriry_3----------->"+authoriry_3);    	
    int    cnt = 0;
    int    cnt2 = 0;
    boolean  isOk = true;
	
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
							if (fileitem.getFieldName().equals("name"))name = fileitem.getString("UTF-8");	
							if (fileitem.getFieldName().equals("company"))company = fileitem.getString("UTF-8");	
							if (fileitem.getFieldName().equals("appellation_id"))appellation_id = fileitem.getString("UTF-8");	
							if (fileitem.getFieldName().equals("phone1"))phone1 = fileitem.getString("UTF-8");		
							if (fileitem.getFieldName().equals("phone2"))phone2 = fileitem.getString("UTF-8");		
							if (fileitem.getFieldName().equals("phone3"))phone3 = fileitem.getString("UTF-8");			
							if (fileitem.getFieldName().equals("email"))email = fileitem.getString("UTF-8");	
							if (fileitem.getFieldName().equals("street_address"))street_address = fileitem.getString("UTF-8");	
							if (fileitem.getFieldName().equals("street_address_detail"))street_address_detail = fileitem.getString("UTF-8");	
							if (fileitem.getFieldName().equals("city"))city = fileitem.getString("UTF-8");	
							if (fileitem.getFieldName().equals("state"))state = fileitem.getString("UTF-8");	
							if (fileitem.getFieldName().equals("postal_code"))postal_code = fileitem.getString("UTF-8");
							if (fileitem.getFieldName().equals("nation_id"))nation_id = fileitem.getString("UTF-8");
							if (fileitem.getFieldName().equals("user_cd"))user_cd = fileitem.getString("UTF-8");
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
											rtlMsg = "GIF,JPG,JPEG,PNG,BMP 확장자만 업로드 가능합니다.";
											out.println("<?xml version='1.0' encoding='utf-8'?>"); 
											out.println("<ROOT>"); 
											out.println("	<BUSINESSCARD_SHARE>"); 
											out.println("  		<FLAG>false</FLAG>"); 
											out.println("  		<MASSEGE>"+rtlMsg+"</MASSEGE>"); 
											out.println("  	</BUSINESSCARD_SHARE>"); 
											out.println("</ROOT>"); 
											if(true) return;
										}
									}
									
									String upload_time = ""+System.currentTimeMillis();
									tmpFileName = upload_time +"." + Ext;
									
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
					

				} else {
					int overSize = (request.getContentLength() / 1000000);
					rtlMsg = "파일의 크기는 10MB까지 입니다. 올리신 파일 용량은"+overSize+"MB입니다";
					out.println("<?xml version='1.0' encoding='utf-8'?>"); 
					out.println("<ROOT>"); 
					out.println("	<BUSINESSCARD_SHARE>"); 
					out.println("  		<FLAG>false</FLAG>"); 
					out.println("  		<MASSEGE>"+rtlMsg+"</MASSEGE>"); 
					out.println("  	</BUSINESSCARD_SHARE>"); 
					out.println("</ROOT>"); 
					if(true) return;
				}
			} else {
				user_cd = request.getParameter("user_cd");
			}
		} catch(Exception e) {
			
		}
		parmValue.put("name"    , name    );
	    parmValue.put("email"    , email    );
	    parmValue.put("company"       , company      );
	    parmValue.put("appellation_id"        , appellation_id        );      
	    parmValue.put("phone1"        , phone1        );          
	    parmValue.put("phone2"        , phone2        );          
	    parmValue.put("phone3"        , phone3        );          
	    parmValue.put("street_address"        , street_address        );        
	    parmValue.put("street_address_detail"        , street_address_detail        );       
	    parmValue.put("city"        , city        );       
	    parmValue.put("state"        , state        );       
	    parmValue.put("postal_code"        , postal_code        );      
	    parmValue.put("nation_id"        , nation_id        );      
	    parmValue.put("picture"        , picture        );       
	    parmValue.put("user_cd"        , user_cd        );    
	    
	    //System.out.println("user_cd------------>"+user_cd);
	         
    try {	   
        if( isOk ) {
        	rtl = member.memberModify(parmValue);
        	if (rtl == 1)
        		flag = "success";
        } 
    }
    catch (Exception e) {
        System.out.println(e.toString());
        out.println(e.toString());
        e.printStackTrace();
    }   
%><?xml version="1.0" encoding="utf-8"?> 
<ROOT>
	<BUSINESSCARD_UPDATE>
  		<FLAG><%=flag%></FLAG>
  	</BUSINESSCARD_UPDATE>
</ROOT>