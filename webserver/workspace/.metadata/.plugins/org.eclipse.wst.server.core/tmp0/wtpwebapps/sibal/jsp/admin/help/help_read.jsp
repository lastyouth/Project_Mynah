<%@ page language = "java" contentType="text/html; charset=UTF-8" %>
<%
	String root_path = request.getContextPath();
%>
<%@ page import = "sips.common.lib.util.*" %>
<%@ page import = "sips.common.lib.value.*" %>
<%@ page import = "sips.dept.contents.Help" %>
<%@ page import = "sips.dept.menu.*" %>
<%  String formName = "frmBoard"; %>
<%
	ParamValue       parmValue = new ParamValue();      
Help            help     = new Help();
   
    ResultSetValue   rset      = null;
    ResultSetValue   rsetHis      = null;

    String b_mode             = StringUtil.defaultIfEmpty(request.getParameter("b_mode"      ), ""); 
	int    cpage              = (request.getParameter("cpage") == null || "".equals(request.getParameter("cpage"))) ? 0 : Integer.parseInt(request.getParameter("cpage"));   // 현재 페이지
    String searchTarget       = StringUtil.defaultIfEmpty(request.getParameter("searchTarget"),"");	      // 검색 조건 (제목/내용)
    String keyword            = StringUtil.defaultIfEmpty(request.getParameter("keyword"     ),"");        // 검색할 키워드
    String searchFlag         = StringUtil.defaultIfEmpty(request.getParameter("searchFlag"  ),"");        // 검색플래그
    String help_id         = StringUtil.defaultIfEmpty(request.getParameter("help_id"  ),"");
    String conference_id       = "";

    String title = "";
    String contents    = "";
    String reply    = "";
    String attached    = "";
    String writer    = "";
    String company    = "";
    String appellation    = "";
    String email    = "";
    String phone    = "";
    String strMsg             = "";
  
    String modeType           = "";


    boolean isWriter       = false; // 글 작성자인가 아닌가

    parmValue.put("help_id"  , help_id  );  
    parmValue.put("conference_id"  , conference_id  );  
    rset = help.getHelpAdminContents(parmValue); 
	while( rset.next()){
		title    = StringUtil.defaultIfEmpty(rset.getString("title"    ), "");
		contents    = StringUtil.defaultIfEmpty(rset.getString("contents"    ), "");
		reply    = StringUtil.defaultIfEmpty(rset.getString("reply"    ), "");
		attached    = StringUtil.defaultIfEmpty(rset.getString("attached"    ), "");
		writer    = StringUtil.defaultIfEmpty(rset.getString("writer"    ), "");
		company    = StringUtil.defaultIfEmpty(rset.getString("company"    ), "");
		email    = StringUtil.defaultIfEmpty(rset.getString("email"    ), "");
		phone    = StringUtil.defaultIfEmpty(rset.getString("phone"    ), "");
		appellation    = StringUtil.defaultIfEmpty(rset.getString("appellation"    ), "");
		conference_id    = StringUtil.defaultIfEmpty(rset.getString("conference_id"    ), "");
	}
    
%>		
<script language="JavaScript" type="text/JavaScript">
<!--
    /**
     * 게시물 수정
     */
    function goModifyForm (help_id ) {        
        var frm = document.<%=formName%>;
        
        frm.help_id.value = help_id;
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
<input type="hidden" name="help_id"      value="<%=help_id%>">   
<input type="hidden" name="conference_id"      value="<%=conference_id%>">   
	
    <table width="100%" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td valign="top">
           <table border="0" cellpadding="0" cellspacing="0" style="border-top:2px solid #e0e0e0; border-bottom:2px solid #e0e0e0; width:784px;">
            <tr>
              <td width="210" class="bbbg" style="padding-left:20px; font-weight:bold;">제목</td>
              <td width="574" style="padding-left:20px;"><%=title%></td>
            </tr>		
            <tr>
              <td width="210" class="bbbg" style="padding-left:20px; font-weight:bold;">이름</td>
              <td width="574" style="padding-left:20px;"><%=writer%></td>
            </tr>		
            <tr>
              <td width="210" class="bbbg" style="padding-left:20px; font-weight:bold;">소속</td>
              <td width="574" style="padding-left:20px;"><%=company%></td>
            </tr>		
            <tr>
              <td width="210" class="bbbg" style="padding-left:20px; font-weight:bold;">호칭</td>
              <td width="574" style="padding-left:20px;"><%=appellation%></td>
            </tr>		
            <tr>
              <td width="210" class="bbbg" style="padding-left:20px; font-weight:bold;">이메일</td>
              <td width="574" style="padding-left:20px;"><%=email%></td>
            </tr>		
            <tr>
              <td width="210" class="bbbg" style="padding-left:20px; font-weight:bold;">연락처</td>
              <td width="574" style="padding-left:20px;"><%=phone%></td>
            </tr>	
            <tr>
              <td width="210" class="bbbg" style="padding-left:20px; font-weight:bold;">내용</td>
              <td width="574" style="padding-left:20px;"><%=StringUtil.newLineToBr(contents) %></td>
            </tr>
            <tr>
              <td class="bbbg" style="padding-left:20px; font-weight:bold;">첨부파일</td>
              <td style="padding-left:20px;"><a href="/mice/upload/help/<%=attached %>" target="_new">첨부파일</a></td>
            </tr>
            <tr>
              <td width="210" class="bbbg" style="padding-left:20px; font-weight:bold;">답변</td>
              <td width="574" style="padding-left:20px;"><%=("".equals(reply)? "미답변" : StringUtil.newLineToBr(reply)) %></td>
            </tr>
        </table></td>
      </tr>
      <tr>
        <td height="50" align="center">
					 <a href="javascript:goList();"><img src="/dhsp/images/bt_list.gif" alt="목록" /></a>
                      <a href="javascript:goModifyForm('<%= help_id %>');">답변</a>                   
                          
        </td>
      </tr>      
    </table>
</form>		
