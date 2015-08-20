<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<% request.setCharacterEncoding("UTF-8"); %>
<%
	String root_path = request.getContextPath();
%>
<%@ page import = "sips.common.lib.util.*" %>
<%@ page import = "sips.common.lib.value.*" %>
<%@ page import = "sips.dept.contents.Appellation" %>
<%@ page import = "sips.dept.contents.Conference" %>
<%  String formName = "frmBoard"; %>
<%
	ParamValue       parmValue = new ParamValue();          
	Appellation        appellation     = new Appellation();
	Conference       conference     = new Conference();
    ResultSetValue   rset      = null;
    ResultSetValue   rset3      = null;

    String user_cd         = (String)session.getAttribute("USER_CD");
    String user_authority         = (String)session.getAttribute("USE_AUTHORITY");
	int    cpage              = (request.getParameter("cpage") == null || "".equals(request.getParameter("cpage"))) ? 0 : Integer.parseInt(request.getParameter("cpage"));   // 현재 페이지
    String searchTarget       = StringUtil.defaultIfEmpty(request.getParameter("searchTarget"), "");	      // 검색 조건 (제목/내용)
    String keyword            = StringUtil.defaultIfEmpty(request.getParameter("keyword"     ), "");        // 검색할 키워드
    String searchFlag         = StringUtil.defaultIfEmpty(request.getParameter("searchFlag"  ), "");        // 검색플래그

    String conference_id             = StringUtil.defaultIfEmpty(request.getParameter("conference_id"      ), ""); 
    String modeType           = StringUtil.defaultIfEmpty(request.getParameter("modeType"    ), "ins");
    String b_mode             = StringUtil.defaultIfEmpty(request.getParameter("b_mode"      ), ""); 
    String appellation_id             = StringUtil.defaultIfEmpty(request.getParameter("appellation_id"      ), ""); 
    
    String appellation_name  = "";
    String strMsg = "";
    String sel_conference_name = "";
    String sel_conference_id = "";
    
    parmValue.put("conference_id"    , conference_id  );
    parmValue.put("appellation_id"    , appellation_id  );
    parmValue.put("user_cd"        , user_cd  );  
    parmValue.put("user_authority"        , user_authority  );  

    if( !"".equals(appellation_id)) {
        rset = appellation.getAppellationInfo(parmValue);
        while( rset.next()){
        	appellation_name    = StringUtil.defaultIfEmpty(rset.getString("appellation"    ), "");
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
    rset3 = conference.getConferenceSelectList(parmValue);
%>
<script language="JavaScript" type="text/JavaScript">
<!--
    /**
     * 게시물 등록
     */
    function goRegitProc () {
        var frm = document.<%=formName%>;
        

        if (frm.appellation_name.value == "") {
            alert("호칭을 입력하세요.");
            frm.appellation_name.focus();
            return;
        }

        if( confirm("<%=strMsg%> 하시겠습니까?")) {
            frm.action   = "appellation_write_proc.jsp";
           // frm.encoding = "multipart/form-data";
            frm.submit();
        }
    }
	function goModifyProc (member_cd) { 
    var frm = document.<%=formName%>;
    
	    if( confirm("수정 하시겠습니까?")) {
	        frm.modeType.value = "mod";
	        frm.action   = "appellation_write_proc.jsp";
	      //  frm.encoding = "multipart/form-data";
	        frm.submit();
	    }
    }    
	
	  
    /**
     * 게시물 삭제
     */
    function goDeleteForm () { 
        var frm = document.<%=formName%>;
        if( confirm("삭제 하시겠습니까?")) {
            frm.modeType.value = "del";
            frm.action   = "appellation_write_proc.jsp";
	      //  frm.encoding = "multipart/form-data";
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
		var enableUploadFileExt = ["bmp","jpg","jpeg","gif","png"];
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
			alert("JPG, GIF, JPEG, BMP, PNG 파일만 등록 가능합니다.");
			uploadImgObj.outerHTML = uploadImgObj.outerHTML;
			return;
		}
	}
  //-->
</script>
<form name="<%=formName%>" method="post">
<input type="hidden" name="cpage"        value="<%= cpage       %>">
<input type="hidden" name="appellation_id" value="<%=appellation_id %>">  
<input type="hidden" name="b_mode"       value="<%=b_mode      %>">
<input type="hidden" name="modeType"     value="<%=modeType    %>">

<table width="100%" border="0" cellspacing="0" cellpadding="0">
       
        <td valign="top"><table border="0" cellpadding="0" cellspacing="0" style="border-top:2px solid #e0e0e0; border-bottom:2px solid #e0e0e0; width:784px;">
            <tr>
              <td width="210" class="bbbg" style="padding-left:20px; font-weight:bold;">컨퍼런스</td>
              <td width="574" style="padding-left:20px;">
              		<select name=conference_id id=conference_id>
<%
    if(  rset3.row()==0) { %>
               <option value="">컨퍼런스 없음</option> 
<%    
    } else {
        while( rset3.next()){
        	sel_conference_name     = StringUtil.defaultIfEmpty(rset3.getString("name" ), "");
        	sel_conference_id    = StringUtil.defaultIfEmpty(rset3.getString("conference_id"), "");
%>				
			<option value="<%=sel_conference_id%>" <%= conference_id.equals(sel_conference_id) ? "selected" : "" %> ><%=sel_conference_name%></option>
			
<%      } 
    }
    %> 
          		</select>
              </td>
            </tr>
            <tr>
              <td width="210" class="bbbg" style="padding-left:20px; font-weight:bold;">호칭</td>
              <td width="574" style="padding-left:20px;"><input name=appellation_name type="text" value="<%=appellation_name%>" class="Ttable" size="50"></td>
            </tr>
        </table></td>
      </tr>
      <tr>
        <td height="50" align="center">
        <% if(modeType.equals("ins")){ %>
            <a href="javascript:goRegitProc();"><img src="<%=root_path%>/images/bt_up.gif" alt="등록" /></a>
         <% }else{ %>
            <a href="javascript:goModifyProc();"><img src="<%=root_path%>/images/bt_modify.gif" alt="수정" /></a>
            <a href="javascript:goDeleteForm('<%= appellation_id %>');"><img src="<%=root_path%>/images/bt_del.gif" alt="삭제" /></a>
         <% } %>   
            <a href="javascript:goList();"><img src="<%=root_path%>/images/bt_list.gif" alt="목록" /></a>
         </td>
      </tr>
    </table>
    
</form>		