<%@ page language = "java" contentType="text/html; charset=UTF-8" %>
<%
	String root_path = request.getContextPath();
%>
<%@ page import = "sips.common.lib.util.*" %>
<%@ page import = "sips.common.lib.value.*" %>
<%@ page import = "sips.dept.contents.Manufacturer" %>
<%@ page import = "sips.dept.menu.*" %>
<%  String formName = "frmBoard"; %>
<%
	ParamValue       parmValue = new ParamValue();      
Manufacturer            manufacturer     = new Manufacturer();
   
    ResultSetValue   rset      = null;
    ResultSetValue   rsetHis      = null;

    String b_mode             = StringUtil.defaultIfEmpty(request.getParameter("b_mode"      ), ""); 
	int    cpage              = (request.getParameter("cpage") == null || "".equals(request.getParameter("cpage"))) ? 0 : Integer.parseInt(request.getParameter("cpage"));   // 현재 페이지
    String searchTarget       = StringUtil.defaultIfEmpty(request.getParameter("searchTarget"),"");	      // 검색 조건 (제목/내용)
    String keyword            = StringUtil.defaultIfEmpty(request.getParameter("keyword"     ),"");        // 검색할 키워드
    String searchFlag         = StringUtil.defaultIfEmpty(request.getParameter("searchFlag"  ),"");        // 검색플래그
    String manufacturer_id         = StringUtil.defaultIfEmpty(request.getParameter("manufacturer_id"  ),"");
    String conference_id       = "";

    String manufacturer_name = "";
    String manufacturer_ceo    = "";
    String phone1    = "";
    String phone2    = "";
    String phone3    = "";
    String handphone1    = "";
    String handphone2    = "";
    String handphone3    = "";
    String zipcode    = "";
    String address    = "";
    String security    = "";
    String first_phone   = "";
    String strMsg             = "";
    String conference_name             = "";
  
    String modeType           = "";


    boolean isWriter       = false; // 글 작성자인가 아닌가

    parmValue.put("manufacturer_id"  , manufacturer_id  );  
    parmValue.put("conference_id"  , conference_id  );  
    rset = manufacturer.getManufacturerInfo(parmValue);
	while( rset.next()){
		manufacturer_name    = StringUtil.defaultIfEmpty(rset.getString("manufacturer_name"    ), "");
    	manufacturer_ceo    = StringUtil.defaultIfEmpty(rset.getString("manufacturer_ceo"    ), "");
    	phone1    = StringUtil.defaultIfEmpty(rset.getString("phone1"    ), "");
    	phone2    = StringUtil.defaultIfEmpty(rset.getString("phone2"    ), "");
    	phone3    = StringUtil.defaultIfEmpty(rset.getString("phone3"    ), "");
    	handphone1    = StringUtil.defaultIfEmpty(rset.getString("handphone1"    ), "");
    	handphone2    = StringUtil.defaultIfEmpty(rset.getString("handphone2"    ), "");
    	handphone3    = StringUtil.defaultIfEmpty(rset.getString("handphone3"    ), "");
    	zipcode    = StringUtil.defaultIfEmpty(rset.getString("zipcode"    ), "");
    	address    = StringUtil.defaultIfEmpty(rset.getString("address"    ), "");
    	first_phone    = StringUtil.defaultIfEmpty(rset.getString("first_phone"    ), "");
    	security    = StringUtil.defaultIfEmpty(rset.getString("security"    ), "");
    	conference_name    = StringUtil.defaultIfEmpty(rset.getString("conference_name"    ), "");
	}
    
%>		
<script language="JavaScript" type="text/JavaScript">
<!--
    /**
     * 게시물 수정
     */
    function goModifyForm (manufacturer_id ) {        
        var frm = document.<%=formName%>;
        
        frm.manufacturer_id.value = manufacturer_id;
        frm.target="_self";
        frm.b_mode.value = "M";
        frm.action = "<%=request.getRequestURI()%>";
        frm.submit();
    }  

    /* page 이동 */
    function goList() {
        var frm = document.<%=formName%>;
        
        frm.b_mode.value = "L";
        frm.target="_self";
        frm.action = "<%=request.getRequestURI()%>";
        frm.submit();
    }


    /* 읽기 페이지로 이동 */
    function goDisplay(idx) {
        var frm = document.<%=formName%>;
        
        frm.idx.value          = idx;
        frm.b_mode.value    = "R";
        frm.target="_self";
        frm.action             = "<%= request.getRequestURI() %>";
        frm.submit();
    }

//-->
</script>
<form name="<%=formName%>" method="post">
<input type="hidden" name="cpage"        value="<%= cpage       %>">
<input type="hidden" name="b_mode"       value="<%=b_mode      %>">
<input type="hidden" name="modeType"     value="<%=modeType    %>">
<input type="hidden" name="manufacturer_id"      value="<%=manufacturer_id%>">   
<input type="hidden" name="conference_id"      value="<%=conference_id%>">   
	
    <table width="100%" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td valign="top">
           <table border="0" cellpadding="0" cellspacing="0" style="border-top:2px solid #e0e0e0; border-bottom:2px solid #e0e0e0; width:784px;">
            <tr>
              <td width="210" class="bbbg" style="padding-left:20px; font-weight:bold;">컨퍼런스명</td>
              <td width="574" style="padding-left:20px;"><%=conference_name%></td>
            </tr>		
            <tr>
              <td width="210" class="bbbg" style="padding-left:20px; font-weight:bold;">업체명</td>
              <td width="574" style="padding-left:20px;"><%=manufacturer_name%></td>
            </tr>		
            <tr>
              <td class="bbbg" style="padding-left:20px; font-weight:bold;">대표자</td>
              <td width="574" style="padding-left:20px;"><%=manufacturer_ceo%></td>
            </tr>
            <tr>
              <td class="bbbg" style="padding-left:20px; font-weight:bold;">핸드폰</td>
              <td style="padding-left:20px;"><%=handphone1%>-<%=handphone2%>-<%=handphone3%> <%="h".equals(first_phone)? "대표번호" : ""%></td>
            </tr>
            <tr>
              <td class="bbbg" style="padding-left:20px; font-weight:bold;">전화번호</td>
              <td style="padding-left:20px;"><%=phone1%>-<%=phone2%>-<%=phone3%> <%="p".equals(first_phone)? "대표번호" : ""%></td>
            </tr>
            <tr>
              <td class="bbbg" style="padding-left:20px; font-weight:bold;">주소</td>
              <td style="padding-left:20px;"><%=address%></td>
            </tr>
            <tr>
              <td class="bbbg" style="padding-left:20px; font-weight:bold;">보안코드</td>
              <td style="padding-left:20px;"><%=security%></td>
            </tr>
        </table></td>
      </tr>
      <tr>
        <td height="50" align="center">
					 <a href="javascript:goList();"><img src="/dhsp/images/bt_list.gif" alt="목록" /></a>
                      <a href="javascript:goModifyForm('<%= manufacturer_id %>');"><img src="<%=root_path%>/images/bt_modify.gif" alt="수정" /></a>                   
                          
        </td>
      </tr>      
    </table>
</form>		
