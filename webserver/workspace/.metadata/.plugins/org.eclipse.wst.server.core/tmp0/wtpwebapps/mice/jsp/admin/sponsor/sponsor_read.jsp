<%@ page language = "java" contentType="text/html; charset=UTF-8" %>
<%
	String root_path = request.getContextPath();
%>
<%@ page import = "sips.common.lib.util.*" %>
<%@ page import = "sips.common.lib.value.*" %>
<%@ page import = "sips.dept.contents.Sponsor" %>
<%@ page import = "sips.dept.menu.*" %>
<%  String formName = "frmBoard"; %>
<%
	ParamValue       parmValue = new ParamValue();      
	Sponsor            sponsor     = new Sponsor();
   
    ResultSetValue   rset      = null;
    ResultSetValue   rsetHis      = null;

    String b_mode             = StringUtil.defaultIfEmpty(request.getParameter("b_mode"      ), ""); 
	int    cpage              = (request.getParameter("cpage") == null || "".equals(request.getParameter("cpage"))) ? 0 : Integer.parseInt(request.getParameter("cpage"));   // 현재 페이지
    String searchTarget       = StringUtil.defaultIfEmpty(request.getParameter("searchTarget"),"");	      // 검색 조건 (제목/내용)
    String keyword            = StringUtil.defaultIfEmpty(request.getParameter("keyword"     ),"");        // 검색할 키워드
    String searchFlag         = StringUtil.defaultIfEmpty(request.getParameter("searchFlag"  ),"");        // 검색플래그
    String sponsor_id         = StringUtil.defaultIfEmpty(request.getParameter("sponsor_id"  ),"");
    String conference_id       = "";

    String explanation = "";
    String logo    = "";
    String detail_image    = "";
    String strMsg             = "";
  
    String modeType           = "";


    boolean isWriter       = false; // 글 작성자인가 아닌가

    parmValue.put("sponsor_id"  , sponsor_id  );  
    parmValue.put("conference_id"  , conference_id  );  
    rset = sponsor.getSponsorInfo(parmValue);
	while( rset.next()){
		explanation    = StringUtil.defaultIfEmpty(rset.getString("explanation"    ), "");
    	logo    = StringUtil.defaultIfEmpty(rset.getString("logo"    ), "");
    	detail_image    = StringUtil.defaultIfEmpty(rset.getString("detail_image"    ), "");
		conference_id    = StringUtil.defaultIfEmpty(rset.getString("conference_id"    ), "");
	}
    
%>		
<script language="JavaScript" type="text/JavaScript">
<!--
    /**
     * 게시물 수정
     */
    function goModifyForm (sponsor_id ) {        
        var frm = document.<%=formName%>;
        
        frm.sponsor_id.value = sponsor_id;
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
<input type="hidden" name="sponsor_id"      value="<%=sponsor_id%>">   
<input type="hidden" name="conference_id"      value="<%=conference_id%>">   
	
    <table width="100%" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td valign="top">
           <table border="0" cellpadding="0" cellspacing="0" style="border-top:2px solid #e0e0e0; border-bottom:2px solid #e0e0e0; width:784px;">
            <tr>
              <td width="210" class="bbbg" style="padding-left:20px; font-weight:bold;">스폰서</td>
              <td width="574" style="padding-left:20px;"><%=explanation%></td>
            </tr>		
            <tr>
              <td class="bbbg" style="padding-left:20px; font-weight:bold;">베너 이미지</td>
              <td style="padding-left:20px;"><img src="/mice/upload/sponsor/<%=logo %>"></td>
            </tr>
            <tr>
              <td class="bbbg" style="padding-left:20px; font-weight:bold;">상세 이미지</td>
              <td style="padding-left:20px;"><img src="/mice/upload/sponsor/<%=detail_image %>"></td>
            </tr>
        </table></td>
      </tr>
      <tr>
        <td height="50" align="center">
					 <a href="javascript:goList();"><img src="/dhsp/images/bt_list.gif" alt="목록" /></a>
                      <a href="javascript:goModifyForm('<%= sponsor_id %>');"><img src="<%=root_path%>/images/bt_modify.gif" alt="수정" /></a>                   
                          
        </td>
      </tr>      
    </table>
</form>		
