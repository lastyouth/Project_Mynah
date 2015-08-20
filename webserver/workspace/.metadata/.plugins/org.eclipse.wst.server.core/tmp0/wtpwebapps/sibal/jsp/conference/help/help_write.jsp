<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<% request.setCharacterEncoding("UTF-8"); %>
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
	Help        help     = new Help();
    ResultSetValue   rset      = null;
    
	int    cpage              = (request.getParameter("cpage") == null || "".equals(request.getParameter("cpage"))) ? 0 : Integer.parseInt(request.getParameter("cpage"));   // 현재 페이지
    String searchTarget       = StringUtil.defaultIfEmpty(request.getParameter("searchTarget"), "");	      // 검색 조건 (제목/내용)
    String keyword            = StringUtil.defaultIfEmpty(request.getParameter("keyword"     ), "");        // 검색할 키워드
    String searchFlag         = StringUtil.defaultIfEmpty(request.getParameter("searchFlag"  ), "");        // 검색플래그
    String help_id       = StringUtil.defaultIfEmpty(request.getParameter("help_id"), "");
    String modeType           = StringUtil.defaultIfEmpty(request.getParameter("modeType"    ), "ins");
    String b_mode             = StringUtil.defaultIfEmpty(request.getParameter("b_mode"      ), ""); 
    
    String title  = "";
    String contents = "";
    String attached = "";
    String strMsg             = "";
    
    parmValue.put("help_id"    , help_id  );
    

    if( !"".equals(help_id)) {
        rset = help.getHelpContents(parmValue);
        while( rset.next()){
        	title    = StringUtil.defaultIfEmpty(rset.getString("title"    ), "");
        	contents    = StringUtil.defaultIfEmpty(rset.getString("contents"    ), "");
        	attached    = StringUtil.defaultIfEmpty(rset.getString("attached"    ), "");
        }
 
        if( "Re".equals(b_mode)) {
            strMsg   = "등록";
            
        } else {
            modeType = "mod";
            strMsg   = "수정";
        }
        
    } else {
        modeType = "ins";
        strMsg   = "등록";
    }	
%>
<script language="JavaScript" type="text/JavaScript">
<!--
    /**
     * 게시물 등록
     */
    function goRegitProc () {
        var frm = document.<%=formName%>;
        

        if (frm.title.value == "") {
            alert("제목을 입력하세요.");
            frm.title.focus();
            return;
        }

        if( confirm("<%=strMsg%> 하시겠습니까?")) {
            frm.action   = "help_write_proc.jsp";
	        frm.encoding = "multipart/form-data";
            frm.submit();
        }
    }
	function goModifyProc (member_cd) { 
    var frm = document.<%=formName%>;
    
	    if( confirm("수정 하시겠습니까?")) {
	        frm.modeType.value = "mod";
	        frm.action   = "help_write_proc.jsp";
	        frm.encoding = "multipart/form-data";
	        frm.submit();
	    }
    }    
	
	  
    /**
     * 게시물 삭제
     */
    function goDeleteForm () { 
        var frm = document.<%=formName%>;
        if( confirm("비활성화 하시겠습니까?")) {
            frm.modeType.value = "del";
            frm.action   = "help_write_proc.jsp";
	        frm.encoding = "multipart/form-data";
            frm.submit();
        }
                
    }    
    
    function goList() {
        var frm = document.<%=formName%>;
        
        frm.b_mode.value = "L";
        frm.action   = "<%= request.getRequestURI() %>";
        frm.submit();
    }
    
  //이미지 확장자 체크
  	function file_check_img(uploadImgObj)
  	{
  		var enableUploadFileExt = ["pdf","zip","gif","jpg","png","xls","doc","hwp"];
  		var uploadImgObjName = uploadImgObj.value;
  		var upArrLeng = enableUploadFileExt.length;
  		var chkExe = uploadImgObjName.split(".");
  		
  		var chkFiles = false;
  		
  		for(var i=0;i<upArrLeng;i++)
  		{
  			if(enableUploadFileExt[i] == chkExe[1].toLowerCase())
  			{
  				chkFiles = true;
  				break;
  			}
  		}
  		if(!chkFiles)
  		{
  			alert("	PDF 파일만 등록 가능합니다.");
  			uploadImgObj.outerHTML = uploadImgObj.outerHTML;
  			return;
  		}
  	}
  //-->
</script>
<form name="<%=formName%>" method="post">
<input type="hidden" name="cpage"        value="<%= cpage       %>">
<input type="hidden" name="help_id" value="<%=help_id %>">  
<input type="hidden" name="b_mode"       value="<%=b_mode      %>">
<input type="hidden" name="modeType"     value="<%=modeType    %>">
<input type="hidden" name="hidden_attached"     value="<%=attached    %>">

<table width="100%" border="0" cellspacing="0" cellpadding="0">
      
      <tr>
        <td valign="top"><table border="0" cellpadding="0" cellspacing="0" style="border-top:2px solid #e0e0e0; border-bottom:2px solid #e0e0e0; width:784px;">
            <tr>
              <td width="210" class="bbbg" style="padding-left:20px; font-weight:bold;">제목</td>
              <td width="574" style="padding-left:20px;"><input name="title" type="text" value="<%=title%>" class="Ttable" size="400"></td>
            </tr>
            <tr>
              <td width="210" class="bbbg" style="padding-left:20px; font-weight:bold;">내용</td>
              <td width="574" style="padding-left:20px;">
              	<textarea name="contents" rows="5" cols="60"><%=contents %></textarea>
              </td>
            </tr>
            <tr>
              <td width="210" class="bbbg" style="padding-left:20px; font-weight:bold;">첨부파일</td>
              <td width="574" style="padding-left:20px;">
              <%
              	if (!"".equals(attached)){
              		out.println("<a href=/mice/upload/help/"+attached+">"+attached+"</a><BR>");
              	}
              %>
              <input name="attached" type="file" value="" onchange="file_check_img(this)">
			  </td>
        </table></td>
      </tr>
      <tr>
        <td height="50" align="center">
        <% if(modeType.equals("ins")){ %>
            <a href="javascript:goRegitProc();"><img src="<%=root_path%>/images/bt_up.gif" alt="등록" /></a>
         <% }else{ %>
            <a href="javascript:goModifyProc();"><img src="<%=root_path%>/images/bt_modify.gif" alt="수정" /></a>
         <% } %>   
            <a href="javascript:goList();"><img src="<%=root_path%>/images/bt_list.gif" alt="목록" /></a>
         </td>
      </tr>
    </table>
    
</form>		