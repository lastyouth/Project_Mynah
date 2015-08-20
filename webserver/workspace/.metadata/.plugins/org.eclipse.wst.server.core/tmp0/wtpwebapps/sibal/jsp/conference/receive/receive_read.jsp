<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<% request.setCharacterEncoding("UTF-8"); %>
<%
	String root_path = request.getContextPath();
%>
<%@ page import = "sips.common.lib.util.*" %>
<%@ page import = "sips.common.lib.value.*" %>
<%@ page import = "sips.dept.contents._Message" %>
<%@ page import = "sips.dept.menu.*" %>
<%  String formName = "frmBoard"; %>
<%
	ParamValue       parmValue = new ParamValue();          
	_Message        message     = new _Message();
    ResultSetValue   rset      = null;
    
	int    cpage              = (request.getParameter("cpage") == null || "".equals(request.getParameter("cpage"))) ? 0 : Integer.parseInt(request.getParameter("cpage"));   // 현재 페이지
    String searchTarget       = StringUtil.defaultIfEmpty(request.getParameter("searchTarget"), "");	      // 검색 조건 (제목/내용)
    String keyword            = StringUtil.defaultIfEmpty(request.getParameter("keyword"     ), "");        // 검색할 키워드
    String searchFlag         = StringUtil.defaultIfEmpty(request.getParameter("searchFlag"  ), "");        // 검색플래그
    String message_id       = StringUtil.defaultIfEmpty(request.getParameter("message_id"), "");
    String modeType           = StringUtil.defaultIfEmpty(request.getParameter("modeType"    ), "ins");
    String b_mode             = StringUtil.defaultIfEmpty(request.getParameter("b_mode"      ), ""); 
    String conference_id             = StringUtil.defaultIfEmpty(request.getParameter("conference_id"      ), ""); 
    
    String title  = "";
    String contents = "";
    String strMsg             = "";
    String reg_date             = "";
    String message_type             = "";
    String from_user_name             = "";
    String reply             = "";
    String from_user_cd             = "";

    parmValue.put("conference_id"    , conference_id  );
    parmValue.put("message_id"    , message_id  );
    rset = message.messageDetail(parmValue);
    String rlt = message.messageReceipt(parmValue);
    while( rset.next()){
    	message_id       = StringUtil.defaultIfEmpty(rset.getString("message_id"   ), ""); 
    	title     = StringUtil.defaultIfEmpty(rset.getString("title" ), "");
    	reg_date    = StringUtil.defaultIfEmpty(rset.getString("reg_date"), "");
    	message_type    = StringUtil.defaultIfEmpty(rset.getString("message_type"), "");
    	from_user_cd    = StringUtil.defaultIfEmpty(rset.getString("from_user_cd"), "");
    	from_user_name    = StringUtil.defaultIfEmpty(rset.getString("from_user_name"), "");
    	reply    = StringUtil.defaultIfEmpty(rset.getString("reply"), "");
    	reg_date    = StringUtil.defaultIfEmpty(rset.getString("reg_date"), "");
    	contents    = StringUtil.defaultIfEmpty(rset.getString("contents"), "");
    }
%>
<script language="JavaScript" type="text/JavaScript">
<!--    
    function goList() {
        var frm = document.<%=formName%>;
        
        frm.b_mode.value = "L";
        frm.action   = "<%= request.getRequestURI() %>";
        frm.submit();
    }
	function goRegitProc () {
	    var frm = document.<%=formName%>;
	
	    if (frm.title.value == "") {
	        alert("제목을 입력하세요.");
	        frm.title.focus();
	        return;
	    }
	
	    if (frm.contents.value == "") {
	        alert("내용을 입력하세요.");
	        frm.contents.focus();
	        return;
	    }
	
	    if( confirm("<%=strMsg%> 하시겠습니까?")) {
	        frm.action   = "receive_proc.jsp";
	        frm.submit();
	    }
	}
    
	/**
	 * 게시물 삭제
	 */
	function goDeleteForm (message_id) { 
	    var frm = document.<%=formName%>;
	
	    if( confirm("삭제 하시겠습니까?")) {
	        frm.message_id.value = message_id;
	        frm.modeType.value = "del";
	        frm.action   = "receive_proc.jsp";
	        frm.submit();
	    }
	            
	}    
  //-->
</script>
<form name="<%=formName%>" method="post">
<input type="hidden" name="cpage"        value="<%= cpage       %>">
<input type="hidden" name="message_id" value="<%=message_id %>">  
<input type="hidden" name="from_user_cd" value="<%=from_user_cd %>">  
<input type="hidden" name="conference_id" value="<%=conference_id %>">  
<input type="hidden" name="b_mode"       value="<%=b_mode      %>">
<input type="hidden" name="modeType"     value="<%=modeType    %>">

<table width="100%" border="0" cellspacing="0" cellpadding="0">
      
      <tr>
        <td valign="top"><table border="0" cellpadding="0" cellspacing="0" style="border-top:2px solid #e0e0e0; border-bottom:2px solid #e0e0e0; width:784px;">
            <tr>
              <td width="210" class="bbbg" style="padding-left:20px; font-weight:bold;">등록일</td>
              <td width="574" style="padding-left:20px;"><%=reg_date%></td>
            </tr>
            <tr>
              <td width="210" class="bbbg" style="padding-left:20px; font-weight:bold;">구분</td>
              <td width="574" style="padding-left:20px;"><%=message_type%></td>
            </tr>
            <tr>
              <td width="210" class="bbbg" style="padding-left:20px; font-weight:bold;">발신자</td>
              <td width="574" style="padding-left:20px;"><%=from_user_name%></td>
            </tr>
            <tr>
              <td width="210" class="bbbg" style="padding-left:20px; font-weight:bold;">제목</td>
              <td width="574" style="padding-left:20px;"><%=title%></td>
            </tr>
            <tr>
              <td width="210" class="bbbg" style="padding-left:20px; font-weight:bold;">내용</td>
              <td width="574" style="padding-left:20px;"><%=contents %></td>
            </tr>
            <tr>
              <td width="210" class="bbbg" style="padding-left:20px; font-weight:bold;">상태</td>
              <td width="574" style="padding-left:20px;"><%=reply%></td>
            </tr>
            <tr>
              <td width="210" class="bbbg" style="padding-left:20px; font-weight:bold;"> 회신 제목</td>
              <td width="574" style="padding-left:20px;"><input name="title" type="text" value="" class="Ttable" size="100"></td>
            </tr>
            <tr>
              <td width="210" class="bbbg" style="padding-left:20px; font-weight:bold;">회신 내용</td>
              <td width="574" style="padding-left:20px;">
              	<textarea name="contents" rows="5" cols="60"></textarea>
              </td>
            </tr>
        </table></td>
      </tr>
      <tr>
        <td height="50" align="center">
			<a href="javascript:goList();"><img src="<%=root_path%>/images/bt_list.gif" alt="목록" /></a>
            <a href="javascript:goRegitProc();"><img src="<%=root_path%>/images/bt_up.gif" alt="회신" /></a>                  
            <a href="javascript:goDeleteForm('<%= message_id %>');"><img src="<%=root_path%>/images/bt_del.gif" alt="삭제" /></a>
         </td>
      </tr>
    </table>
    
</form>		