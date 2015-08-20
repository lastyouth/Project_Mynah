<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<% request.setCharacterEncoding("UTF-8"); %>
<%
	String root_path = request.getContextPath();
%>
<%@ page import = "sips.common.lib.util.*" %>
<%@ page import = "sips.common.lib.value.*" %>
<%@ page import = "sips.dept.contents.Conference" %>
<%@ page import = "sips.dept.menu.*" %>
<%  String formName = "frmBoard"; %>
<%
	ParamValue       parmValue = new ParamValue();          
	Conference        conference     = new Conference();
    ResultSetValue   rset      = null;
    
	int    cpage              = (request.getParameter("cpage") == null || "".equals(request.getParameter("cpage"))) ? 0 : Integer.parseInt(request.getParameter("cpage"));   // 현재 페이지
    String searchTarget       = StringUtil.defaultIfEmpty(request.getParameter("searchTarget"), "");	      // 검색 조건 (제목/내용)
    String keyword            = StringUtil.defaultIfEmpty(request.getParameter("keyword"     ), "");        // 검색할 키워드
    String searchFlag         = StringUtil.defaultIfEmpty(request.getParameter("searchFlag"  ), "");        // 검색플래그
    String conference_id       = StringUtil.defaultIfEmpty(request.getParameter("conference_id"), "");
    String modeType           = StringUtil.defaultIfEmpty(request.getParameter("modeType"    ), "ins");
    String b_mode             = StringUtil.defaultIfEmpty(request.getParameter("b_mode"      ), ""); 
    String user_authority         = (String)session.getAttribute("USE_AUTHORITY");
    
    String name  = "";
    String start_date = "";
    String end_date    = "";
    String reg_date = "";
    String banner_image = "";
    String info_image = "";
    String strMsg             = "";
    
    parmValue.put("conference_id"    , conference_id  );
    

    if( !"".equals(conference_id)) {
        rset = conference.getConferenceInfo(parmValue);
        while( rset.next()){
        	end_date    = StringUtil.defaultIfEmpty(rset.getString("end_date"    ), "");
            start_date    = StringUtil.defaultIfEmpty(rset.getString("start_date"    ), "");
            name    = StringUtil.defaultIfEmpty(rset.getString("name"    ), "");
            end_date    = StringUtil.defaultIfEmpty(rset.getString("end_date"    ), "");
            banner_image    = StringUtil.defaultIfEmpty(rset.getString("banner_image"    ), "");
            info_image    = StringUtil.defaultIfEmpty(rset.getString("info_image"    ), "");
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

        if( confirm("<%=strMsg%> 하시겠습니까?")) {
            frm.action   = "conference_info_write_proc.jsp";
            frm.encoding = "multipart/form-data";
            frm.submit();
        }
    }
	function goModifyProc (member_cd) { 
        var frm = document.<%=formName%>;
    
	    if( confirm("수정 하시겠습니까?")) {
	        frm.modeType.value = "mod";
	        frm.action   = "conference_info_write_proc.jsp";
	        frm.encoding = "multipart/form-data";
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
            frm.action   = "conference_info_write_proc.jsp";
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
  //-->
</script>
<form name="<%=formName%>" method="post">
<input type="hidden" name="cpage"        value="<%= cpage       %>">
<input type="hidden" name="conference_id" value="<%=conference_id %>">  
<input type="hidden" name="b_mode"       value="<%=b_mode      %>">
<input type="hidden" name="modeType"     value="<%=modeType    %>">
<input type="hidden" name="hidden_banner_image"     value="<%=banner_image    %>">
<input type="hidden" name="hidden_info_image"     value="<%=info_image    %>">

<table width="100%" border="0" cellspacing="0" cellpadding="0">
      
      <tr>
        <td valign="top"><table border="0" cellpadding="0" cellspacing="0" style="border-top:2px solid #e0e0e0; border-bottom:2px solid #e0e0e0; width:784px;">
            <tr>
              <td width="210" class="bbbg" style="padding-left:20px; font-weight:bold;">컨퍼런스명</td>
              <td width="574" style="padding-left:20px;"><input name="name" type="text" value="<%=name%>" class="Ttable" size="35" readonly ></td>
            </tr>
            <tr>
              <td width="210" class="bbbg" style="padding-left:20px; font-weight:bold;">베너이미지</td>
              
              <td width="574" style="padding-left:20px;">
              <%
              	if (!"".equals(banner_image)){
              		out.println("<img src=/mice/upload/conference/"+banner_image+">");
              	}
              %>
              <br>
              <input name="banner_image" type="file" value="" class="Ttable" size="35"></td>
            </tr>
            <tr>
              <td width="210" class="bbbg" style="padding-left:20px; font-weight:bold;">INFO 이미지</td>
              
              <td width="574" style="padding-left:20px;">
              <%
              	if (!"".equals(info_image)){
              		out.println("<img src=/mice/upload/conference/"+info_image+">");
              	}
              %><br>
              <input name="info_image" type="file" value="" class="Ttable" size="35"></td>
            </tr>
        </table></td>
      </tr>
      <tr>
        <td height="50" align="center">
            <a href="javascript:goModifyProc();"><img src="<%=root_path%>/images/bt_modify.gif" alt="수정" /></a>
         </td>
      </tr>
    </table>
    
</form>		