<%@ page language = "java" contentType="text/html; charset=UTF-8" %>
<%
	String root_path = request.getContextPath();
%>
<%@ page import = "sips.common.lib.util.*" %>
<%@ page import = "sips.common.lib.value.*" %>
<%@ page import = "sips.dept.contents.Additional" %>
<%@ page import = "sips.dept.menu.*" %>
<%  String formName = "frmBoard"; %>
<%
	ParamValue       parmValue = new ParamValue();      
	Additional            additional     = new Additional();

    ResultSetValue   rset      = null;
    ResultSetValue   rsetHis      = null;

    String b_mode             = StringUtil.defaultIfEmpty(request.getParameter("b_mode"      ), ""); 
	int    cpage              = (request.getParameter("cpage") == null || "".equals(request.getParameter("cpage"))) ? 0 : Integer.parseInt(request.getParameter("cpage"));   // 현재 페이지
    String additional_id         = StringUtil.defaultIfEmpty(request.getParameter("additional_id"  ),"");
    String conference_id       = "";

    String gubun  = "";
    String latitude  = "";
    String longitude  = "";
    String main_title  = "";
    String sub_title  = "";
    String contents  = "";
    String strMsg             = "";
    String phone1   = "";
    String phone2  = "";
    String phone3   = "";
    String handphone1   = "";
    String handphone2   = "";
    String handphone3   = "";
    String first_phone   = "h";
    String address    = "";
    String zipcode = "";
  
    String modeType           = "";


    boolean isWriter       = false; // 글 작성자인가 아닌가

    parmValue.put("additional_id"  , additional_id  );  
    parmValue.put("conference_id"  , conference_id  );  
    rset = additional.getAdditionalInfo(parmValue);
	while( rset.next()){
    	gubun    = StringUtil.defaultIfEmpty(rset.getString("gubun2"    ), "");
    	latitude    = StringUtil.defaultIfEmpty(rset.getString("latitude"    ), "");
    	longitude    = StringUtil.defaultIfEmpty(rset.getString("longitude"    ), "");
    	main_title    = StringUtil.defaultIfEmpty(rset.getString("main_title"    ), "");
    	sub_title    = StringUtil.defaultIfEmpty(rset.getString("sub_title"    ), "");
    	contents    = StringUtil.defaultIfEmpty(rset.getString("contents"    ), "");
		conference_id    = StringUtil.defaultIfEmpty(rset.getString("conference_id"    ), "");
	    phone1    = StringUtil.defaultIfEmpty(rset.getString("phone1"    ), "");
	    phone2    = StringUtil.defaultIfEmpty(rset.getString("phone2"    ), "");
	    phone3    = StringUtil.defaultIfEmpty(rset.getString("phone3"    ), "");
	    handphone1    = StringUtil.defaultIfEmpty(rset.getString("handphone1"    ), "");
	    handphone2    = StringUtil.defaultIfEmpty(rset.getString("handphone2"    ), "");
	    handphone3    = StringUtil.defaultIfEmpty(rset.getString("handphone3"    ), "");
	    first_phone    = StringUtil.defaultIfEmpty(rset.getString("first_phone"    ), "");
	    zipcode    = StringUtil.defaultIfEmpty(rset.getString("zipcode"    ), "");
	    address    = StringUtil.defaultIfEmpty(rset.getString("address"    ), "");
	}
    
%>		
<script language="JavaScript" type="text/JavaScript">
<!--
    /**
     * 게시물 수정
     */
    function goModifyForm (additional_id ) {        
        var frm = document.<%=formName%>;
        
        frm.additional_id.value = additional_id;
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
<input type="hidden" name="additional_id"      value="<%=additional_id%>">   
<input type="hidden" name="conference_id"      value="<%=conference_id%>">   
	
    <table width="100%" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td valign="top">
           <table border="0" cellpadding="0" cellspacing="0" style="border-top:2px solid #e0e0e0; border-bottom:2px solid #e0e0e0; width:784px;">
            <tr>
              <td width="210" class="bbbg" style="padding-left:20px; font-weight:bold;">숙박/식당</td>
              <td width="574" style="padding-left:20px;"><%=gubun%></td>
            </tr>		
             <tr>
              <td width="210" class="bbbg" style="padding-left:20px; font-weight:bold;">타이틀</td>
              <td width="574" style="padding-left:20px;"><%=main_title%></td>
            </tr>	
             <!-- tr>
              <td width="210" class="bbbg" style="padding-left:20px; font-weight:bold;">위치</td>
              <td width="574" style="padding-left:20px;">위도 : <%=latitude%> 경도 : <%=longitude%></td>
            </tr -->
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
              <td width="210" class="bbbg" style="padding-left:20px; font-weight:bold;">내용</td>
              <td width="574" style="padding-left:20px;"><%=StringUtil.newLineToBr(contents)%></td>
            </tr>	
        </table></td>
      </tr>
      <tr>
        <td height="50" align="center">
					 <a href="javascript:goList();"><img src="/dhsp/images/bt_list.gif" alt="목록" /></a>
                      <a href="javascript:goModifyForm('<%= additional_id %>');"><img src="<%=root_path%>/images/bt_modify.gif" alt="수정" /></a>                   
                          
        </td>
      </tr>      
    </table>
</form>		
