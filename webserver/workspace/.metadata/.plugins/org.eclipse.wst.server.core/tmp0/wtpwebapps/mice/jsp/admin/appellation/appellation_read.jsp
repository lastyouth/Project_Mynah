<%@ page language = "java" contentType="text/html; charset=UTF-8" %>
<%
	String root_path = request.getContextPath();
%>
<%@ page import = "sips.common.lib.util.*" %>
<%@ page import = "sips.common.lib.value.*" %>
<%@ page import = "sips.dept.contents.Appellation" %>
<%@ page import = "sips.dept.menu.*" %>
<%  String formName = "frmBoard"; %>
<%
	ParamValue       parmValue = new ParamValue();      
	Appellation            appellation     = new Appellation();
   
    ResultSetValue   rset      = null;
    ResultSetValue   rsetHis      = null;

    String b_mode             = StringUtil.defaultIfEmpty(request.getParameter("b_mode"      ), ""); 
	int    cpage              = (request.getParameter("cpage") == null || "".equals(request.getParameter("cpage"))) ? 0 : Integer.parseInt(request.getParameter("cpage"));   // 현재 페이지
    String appellation_id         = StringUtil.defaultIfEmpty(request.getParameter("appellation_id"  ),"");
    String conference_id       = "";

    String appellation_name = "";
    String strMsg             = "";
  
    String modeType           = "";


    boolean isWriter       = false; // 글 작성자인가 아닌가

    parmValue.put("appellation_id"  , appellation_id  );  
    parmValue.put("conference_id"  , conference_id  );  
    rset = appellation.getAppellationInfo(parmValue);
	while( rset.next()){
		appellation_name    = StringUtil.defaultIfEmpty(rset.getString("appellation"    ), "");
		conference_id    = StringUtil.defaultIfEmpty(rset.getString("conference_id"    ), "");
	}
    
%>		
<script language="JavaScript" type="text/JavaScript">
<!--
    /**
     * 게시물 수정
     */
    function goModifyForm (appellation_id ) {        
        var frm = document.<%=formName%>;
        
        frm.appellation_id.value = appellation_id;
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
<input type="hidden" name="appellation_id"      value="<%=appellation_id%>">   
<input type="hidden" name="conference_id"      value="<%=conference_id%>">   
	
    <table width="100%" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td valign="top">
           <table border="0" cellpadding="0" cellspacing="0" style="border-top:2px solid #e0e0e0; border-bottom:2px solid #e0e0e0; width:784px;">
            <tr>
              <td width="210" class="bbbg" style="padding-left:20px; font-weight:bold;">호칭</td>
              <td width="574" style="padding-left:20px;"><%=appellation_name%></td>
            </tr>		
        </table></td>
      </tr>
      <tr>
        <td height="50" align="center">
					 <a href="javascript:goList();"><img src="/dhsp/images/bt_list.gif" alt="목록" /></a>
                      <a href="javascript:goModifyForm('<%= appellation_id %>');"><img src="<%=root_path%>/images/bt_modify.gif" alt="수정" /></a>                   
                          
        </td>
      </tr>      
    </table>
</form>		
