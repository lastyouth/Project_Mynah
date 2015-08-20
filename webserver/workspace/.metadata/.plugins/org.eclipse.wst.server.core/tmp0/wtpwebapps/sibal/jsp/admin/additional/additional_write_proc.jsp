<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<% request.setCharacterEncoding("UTF-8"); %>
<%
	String root_path = request.getContextPath();
%>
<%@ page import = "java.util.*"  %>
<%@ page import = "sips.common.lib.util.*" %>
<%@ page import = "sips.common.lib.value.*" %> 
<%@ page import = "sips.dept.contents.Additional"   %>

<%
	Additional            additional     = new Additional();
    ParamValue     parmValue     = new ParamValue();
    ResultSetValue rset          = null;

    String modeType           = StringUtil.defaultIfEmpty(request.getParameter("modeType"    ), "");  
    String additional_id           = StringUtil.defaultIfEmpty(request.getParameter("additional_id"    ), "");  
    String conference_id           = StringUtil.defaultIfEmpty(request.getParameter("conference_id"    ), "");  
    String gubun           = StringUtil.defaultIfEmpty(request.getParameter("gubun"    ), "");  
    String main_title           = StringUtil.defaultIfEmpty(request.getParameter("main_title"    ), "");  
    String sub_title           = StringUtil.defaultIfEmpty(request.getParameter("sub_title"    ), "");  
    String latitude           = StringUtil.defaultIfEmpty(request.getParameter("latitude"    ), "");  
    String longitude           = StringUtil.defaultIfEmpty(request.getParameter("longitude"    ), "");  
    String contents           = StringUtil.defaultIfEmpty(request.getParameter("contents"    ), "");  
    String worker_cd         = (String)session.getAttribute("USER_CD");
    String phone1   = StringUtil.defaultIfEmpty(request.getParameter("phone1"    ), "");
    String phone2  = StringUtil.defaultIfEmpty(request.getParameter("phone2"    ), "");
    String phone3   = StringUtil.defaultIfEmpty(request.getParameter("phone3"    ), "");
    String handphone1   = StringUtil.defaultIfEmpty(request.getParameter("handphone1"    ), "");
    String handphone2   = StringUtil.defaultIfEmpty(request.getParameter("handphone2"    ), "");
    String handphone3   = StringUtil.defaultIfEmpty(request.getParameter("handphone3"    ), "");
    String first_phone   = StringUtil.defaultIfEmpty(request.getParameter("first_phone"    ), "");
    String  address     = StringUtil.defaultIfEmpty(request.getParameter("address"    ), "");
    String  zipcode     = StringUtil.defaultIfEmpty(request.getParameter("zipcode"    ), "");
    
    String boxes  = "";
    String duplicateMsg = "";
    String b_mode             = "";
        
    boolean  isOk = true;

    parmValue.put("phone1"    , phone1    );
    parmValue.put("phone2"    , phone2    );
    parmValue.put("phone3"    ,  phone3   );
    parmValue.put("handphone1"    , handphone1    );
    parmValue.put("handphone2"    , handphone2    );
    parmValue.put("handphone3"    , handphone3    );
    parmValue.put("first_phone"    , first_phone    );
    parmValue.put("address"    , address    );
    parmValue.put("zipcode"    , zipcode    );
    parmValue.put("conference_id"    , conference_id    );
    parmValue.put("gubun"    , gubun    );
    parmValue.put("main_title"    , main_title    );
    parmValue.put("sub_title"    , sub_title    );
    parmValue.put("latitude"    , latitude    );
    parmValue.put("longitude"    , longitude    );
    parmValue.put("contents"    , contents    );
    parmValue.put("worker_cd"    , worker_cd    );
    parmValue.put("additional_id"    , additional_id    );
    try {	   
        if( isOk ) {
            if( "ins".equals(modeType)){
            	additional.additionalInsert(parmValue);
            } else if( "mod".equals(modeType)){
            	additional.additionalModify(parmValue);
            } else if( "del".equals(modeType)){  
            	additional.additionalDeleteContents(parmValue);  
            }
        } 
%>
<form name="frmBrd" action="additional.jsp" method="post">
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

