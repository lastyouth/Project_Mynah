<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<% request.setCharacterEncoding("UTF-8"); %>
<%
	String root_path = request.getContextPath();
%>
<%@ page import = "java.util.*"  %>
<%@ page import = "sips.common.lib.util.*" %>
<%@ page import = "sips.common.lib.value.*" %> 
<%@ page import = "sips.dept.contents.Manufacturer" %>

<%
	Manufacturer            manufacturer     = new Manufacturer();
    ParamValue     parmValue     = new ParamValue();
    ResultSetValue rset          = null;
    
    String modeType           = StringUtil.defaultIfEmpty(request.getParameter("modeType"    ), "");  
    String manufacturer_id     = StringUtil.defaultIfEmpty(request.getParameter("manufacturer_id"),"");
    
    String manufacturer_name = StringUtil.defaultIfEmpty(request.getParameter("manufacturer_name"),"");
    String manufacturer_ceo    = StringUtil.defaultIfEmpty(request.getParameter("manufacturer_ceo"),"");
    String phone1    = StringUtil.defaultIfEmpty(request.getParameter("phone1"),"");
    String phone2    = StringUtil.defaultIfEmpty(request.getParameter("phone2"),"");
    String phone3    = StringUtil.defaultIfEmpty(request.getParameter("phone3"),"");
    String handphone1    = StringUtil.defaultIfEmpty(request.getParameter("handphone1"),"");
    String handphone2    = StringUtil.defaultIfEmpty(request.getParameter("handphone2"),"");
    String handphone3    = StringUtil.defaultIfEmpty(request.getParameter("handphone3"),"");
    String zipcode    = StringUtil.defaultIfEmpty(request.getParameter("zipcode"),"");
    String address    = StringUtil.defaultIfEmpty(request.getParameter("address"),"");
    String security    = StringUtil.defaultIfEmpty(request.getParameter("security"),"");
    String first_phone   = StringUtil.defaultIfEmpty(request.getParameter("first_phone"),"");
    String conference_id        = StringUtil.defaultIfEmpty(request.getParameter("conference_id"),"");
    String worker_cd   = (String)session.getAttribute("USER_CD");
    
    String b_mode             = "";
        
    String searchTarget       = "";
    String keyword            = "";
    String searchFlag         = "";
    String strMsg             = "";    
    int    cnt = 0;
    int    cnt2 = 0;
    boolean  isOk = true;    
    int rlt =  99;

    parmValue.put("manufacturer_name"    , manufacturer_name    );
    parmValue.put("manufacturer_ceo"    , manufacturer_ceo    );
    parmValue.put("phone1"    , phone1    );
    parmValue.put("phone2"    , phone2    );
    parmValue.put("phone3"    , phone3    );
    parmValue.put("handphone1"    , handphone1    );
    parmValue.put("handphone2"    , handphone2    );
    parmValue.put("handphone3"    , handphone3    );
    parmValue.put("zipcode"    , zipcode    );
    parmValue.put("address"    , address    );
    parmValue.put("first_phone"    , first_phone    );
    parmValue.put("security"    , security    );
    parmValue.put("worker_cd"    , worker_cd    );
    parmValue.put("manufacturer_id"    , manufacturer_id    );
    parmValue.put("conference_id"    , conference_id    );
    try {	   
        if( isOk ) {
            System.out.println("modeType1------------>"+modeType);
            if( "ins".equals(modeType)){
                System.out.println("modeType2------------>"+modeType);
            	 manufacturer.manufacturerInsert(parmValue);
            } else if( "mod".equals(modeType)){
            	manufacturer.manufacturerModify(parmValue);
            } else if( "del".equals(modeType)){  
            	 manufacturer.manufacturerDeleteContents(parmValue);  
            }
        }        

%>
<form name="frmBrd" action="manufacturer.jsp" method="post">
<input type=hidden name=conference_id	value="<%=conference_id%>"      />
</form>
<script language="javascript">
	//var result = "<%=rlt%>";
	//if (result == 0){
	//	alert('시간 설정을 확인해주세요.');
	//	history.back();
	//	return ;
	//}
	document.frmBrd.submit();
</script>  
  

<%  }
    catch (Exception e) {
        System.out.println(e.toString());
        out.println(e.toString());
        e.printStackTrace();
    }   
%>

