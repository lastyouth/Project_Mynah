<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<% request.setCharacterEncoding("UTF-8"); %>
<%
	String root_path = request.getContextPath();
%>
<%@ page import = "java.util.*"  %>
<%@ page import = "sips.common.lib.util.*" %>
<%@ page import = "sips.common.lib.value.*" %> 
<%@ page import = "sips.dept.menu.*"   %>
<%@ page import = "sips.dept.contents.Binder" %>
<%@ page import = "org.apache.commons.fileupload.servlet.*" %>
<%@ page import = "org.apache.commons.fileupload.disk.*" %>
<%@ page import = "org.apache.commons.fileupload.*"%>
<%@ page import = "java.io.*"    %>

<%
	Binder      binder     = new Binder();
    ParamValue     parmValue     = new ParamValue();
    ResultSetValue rset          = null;
    
    String modeType           = "";
    String b_mode             = "";
    String strMsg             = "";    

    String conference_id     = "";
    String binder_id     = "";
    String session_id     = "";
    String topic_id    = "";
    String con_admin_user_cd       = "";
    String binder_title         = "";
    String writer         = "";
    String contents         = "";
    String attached         = "";
    String hidden_attached         = "";

    boolean  isOk = true;    

    String  _extensionList     = "PDF";
    String   extensionList    = _extensionList.toLowerCase();
    java.util.HashMap hmParam = new java.util.HashMap();
    FileUtil       file          = new FileUtil();
    File uploadedFile    = null;
    String path_nm            = request.getRealPath("/");
    String tmpFileName = "";
    String   fileExt          = "";
    
  //request가 multipart/form-data 형식인지 확인한다. 형식확인
    boolean isMultipart = ServletFileUpload.isMultipartContent(request);
    if (isMultipart) {
        try {
            // 서버측에서 전송받은 파일을 관리하기 위한 객체 
            // 각종 제약조건을 걸수 있다.
            DiskFileItemFactory factory = new DiskFileItemFactory();
            
            // 메모리에 저장할 최대크기 지정
            int yourMaxMemorySize = 1024 * 3;
            factory.setSizeThreshold(yourMaxMemorySize);                      
            
            // 저장하기 위한 서버측 임시디렉토리 설정
            File yourTempDirectory = new File(path_nm+"upload/temp");
            factory.setRepository(yourTempDirectory);
            
            // 파일 업로드를 다루는 객체 생성 
            ServletFileUpload upload = new ServletFileUpload(factory);
            
            // request를 통해 업로드된 각각의 파일을 List로 추출
            List items = upload.parseRequest(request);
            
            // 참고 코드 
            //out.print("List의 크기 : " + items.size());
            
            // Process the uploaded items
            // 업로드된 각각의 파일 처리 
            // List를 Iterator로 변환
            Iterator iterFile = items.iterator();

            

            while (iterFile.hasNext()) {
                FileItem item = (FileItem) iterFile.next();    
                if (item.isFormField()) {
                    String fieldName  = item.getFieldName();
                    String fieldValue = item.getString("UTF-8");                    
                         
                    hmParam.put(fieldName, fieldValue);
                } else {                     
                    String  fieldName   = item.getFieldName();
                    String fieldValue = item.getString("UTF-8");                    
                         
                    hmParam.put(fieldName, fieldValue);
                    String  fileName    = item.getName();
                    String  contentType = item.getContentType();
                    
                    if( fileName !=null  ) {
                    
                        // 업로드된 파일을 서버측의 파일로 저장
                        // 업로드된 파일이 메모리에 있던 또는 임시 디렉토리에
                        // 저장되어 있던 관계 없다. 
                        // 위의 정보 중 fileName 으로 부터
                        // 경로명을 제외한 파일명(파일 이름만 확인)
                                       
                        tmpFileName = (String)hmParam.get("attached");    
                        fileExt = file.getFileExtension(fileName);
                        
                        attached = fileName.substring(fileName.lastIndexOf("\\") + 1);  

                        fileExt = fileExt.substring(1,fileExt.length());
                        fileExt = fileExt.toLowerCase();
                        if( extensionList.indexOf(fileExt) == -1 ) {
                            isOk = false; 
%>
                            <script language="javascript">
                                alert("업로드 할 수 없는 파일 형식입니다.");
                                history.back();
                            </script>
<%                  } 
                        if(isOk){
                            if( !"".equals(attached)) {
                               uploadedFile = new File(path_nm + "/upload/pdf/" + attached);
                               item.write(uploadedFile);        
                               parmValue.put("attached"       , attached       );   
                               System.out.println("attached------------>"+attached);
                               
                            }
                        } 
                        
                    }        
                                     
                                
                }
            }
            //coil.xlsUploadInsert(uploadedFile);
           
            
        } catch (Exception e) {
             System.out.println("Exception:" + e.toString());
             System.out.println("attached------------>"+hidden_attached);
        }
    } else {
       System.out.println("Multipart/form-data 형식이 아니다. 파일 전송 아니다.");
    }
    
    
    modeType           = (String)hmParam.get("modeType"); 
    conference_id     = (String)hmParam.get("conference_id"); 
    session_id    = (String)hmParam.get("session_id"); 
    topic_id       = (String)hmParam.get("topic_id"); 
    con_admin_user_cd       = (String)hmParam.get("con_admin_user_cd"); 
    binder_title       = (String)hmParam.get("binder_title"); 
    writer       = (String)hmParam.get("writer"); 
    contents       = (String)hmParam.get("contents"); 
    binder_id       = (String)hmParam.get("binder_id"); 
    hidden_attached       = (String)hmParam.get("hidden_attached"); 
    if ("".equals(attached)) {
    	parmValue.put("attached"       , hidden_attached       );   
    }
                System.out.println("modeType------------>"+modeType);
    
    parmValue.put("conference_id"    , conference_id    );
    parmValue.put("session_id"        , session_id        );    
    parmValue.put("topic_id"       , topic_id       );   
    parmValue.put("con_admin_user_cd"       , con_admin_user_cd       );   
    parmValue.put("binder_title"       , binder_title       );   
    parmValue.put("writer"       , writer       );   
    parmValue.put("contents"       , contents       );   
    parmValue.put("binder_id"       , binder_id       );   
    
    try {	   
        if( isOk ) {
            if( "ins".equals(modeType)){
            	binder.BinderInsert(parmValue);
            } else if( "mod".equals(modeType)){
            	binder.binderModify(parmValue);
            } else if( "del".equals(modeType)){  
            	binder.binderDeleteContents(parmValue);  
            }
        }

%>
<form name="frmBrd" action="binder.jsp" method="post">
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

