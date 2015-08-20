<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<% request.setCharacterEncoding("UTF-8"); %>
<%
	String root_path = request.getContextPath();
%>
<%@ page import = "sips.common.lib.util.*" %>
<%@ page import = "sips.common.lib.value.*" %>
<%@ page import = "sips.dept.contents.Banner" %>
<%@ page import = "sips.dept.contents.Conference" %>
<%  String formName = "frmBoard"; %>
<%
	ParamValue       parmValue = new ParamValue();          
	Banner        banner     = new Banner();
	Conference       conference     = new Conference();
    ResultSetValue   rset      = null;
    ResultSetValue   rset3      = null;

    String user_cd         = (String)session.getAttribute("USER_CD");
    String user_authority         = (String)session.getAttribute("USE_AUTHORITY");
	int    cpage              = (request.getParameter("cpage") == null || "".equals(request.getParameter("cpage"))) ? 0 : Integer.parseInt(request.getParameter("cpage"));   // 현재 페이지

	String conference_id             = ""; 
    String modeType           = StringUtil.defaultIfEmpty(request.getParameter("modeType"    ), "ins");
    String b_mode             = StringUtil.defaultIfEmpty(request.getParameter("b_mode"      ), ""); 
    String banner_id             = StringUtil.defaultIfEmpty(request.getParameter("banner_id"      ), ""); 

    String banner_1  = "";
    String banner_2  = "";
    String banner_3  = "";
    String banner_4  = "";
    String banner_5  = "";
    String banner_6  = "";
    String banner_7  = "";
    String banner_8  = "";
    String banner_9  = "";
    String banner_10  = "";
    String banner_11  = "";
    String hidden_banner_1    = "";
    String hidden_banner_2    = "";
    String hidden_banner_3    = "";
    String hidden_banner_4    = "";
    String hidden_banner_5    = "";
    String hidden_banner_6    = "";
    String hidden_banner_7    = "";
    String hidden_banner_8    = "";
    String hidden_banner_9    = "";
    String hidden_banner_10    = "";
    String hidden_banner_11    = "";
    String landing_url_1 = "";
    String landing_url_2 = "";
    String landing_url_3 = "";
    String landing_url_4 = "";
    String landing_url_5 = "";
    String landing_url_6 = "";
    String landing_url_7 = "";
    String landing_url_8 = "";
    String landing_url_9 = "";
    String landing_url_10 = "";
    String landing_url_11 = "";
    String strMsg = "";
    String sel_conference_name = "";
    String sel_conference_id = "";


    parmValue.put("banner_id"    , banner_id  );
    parmValue.put("user_cd"        , user_cd  );  
    parmValue.put("user_authority"        , user_authority  );  

    if( !"".equals(banner_id)) {
        rset = banner.getBannerInfo(parmValue);
        while( rset.next()){
        	banner_id    = StringUtil.defaultIfEmpty(rset.getString("banner_id"    ), "");
        	banner_1    = StringUtil.defaultIfEmpty(rset.getString("banner_1"    ), "");
        	banner_2    = StringUtil.defaultIfEmpty(rset.getString("banner_2"    ), "");
        	banner_3    = StringUtil.defaultIfEmpty(rset.getString("banner_3"    ), "");
        	banner_4    = StringUtil.defaultIfEmpty(rset.getString("banner_4"    ), "");
        	banner_5    = StringUtil.defaultIfEmpty(rset.getString("banner_5"    ), "");
        	banner_6    = StringUtil.defaultIfEmpty(rset.getString("banner_6"    ), "");
        	banner_7    = StringUtil.defaultIfEmpty(rset.getString("banner_7"    ), "");
        	banner_8    = StringUtil.defaultIfEmpty(rset.getString("banner_8"    ), "");
        	banner_9    = StringUtil.defaultIfEmpty(rset.getString("banner_9"    ), "");
        	banner_10    = StringUtil.defaultIfEmpty(rset.getString("banner_10"    ), "");
        	banner_11    = StringUtil.defaultIfEmpty(rset.getString("banner_11"    ), "");
        	landing_url_1    = StringUtil.defaultIfEmpty(rset.getString("landing_url_1"    ), "");
        	landing_url_2    = StringUtil.defaultIfEmpty(rset.getString("landing_url_2"    ), "");
        	landing_url_3    = StringUtil.defaultIfEmpty(rset.getString("landing_url_3"    ), "");
        	landing_url_4    = StringUtil.defaultIfEmpty(rset.getString("landing_url_4"    ), "");
        	landing_url_5    = StringUtil.defaultIfEmpty(rset.getString("landing_url_5"    ), "");
        	landing_url_6    = StringUtil.defaultIfEmpty(rset.getString("landing_url_6"    ), "");
        	landing_url_7    = StringUtil.defaultIfEmpty(rset.getString("landing_url_7"    ), "");
        	landing_url_8    = StringUtil.defaultIfEmpty(rset.getString("landing_url_8"    ), "");
        	landing_url_9    = StringUtil.defaultIfEmpty(rset.getString("landing_url_9"    ), "");
        	landing_url_10    = StringUtil.defaultIfEmpty(rset.getString("landing_url_10"    ), "");
        	landing_url_11    = StringUtil.defaultIfEmpty(rset.getString("landing_url_11"    ), "");
        	conference_id    = StringUtil.defaultIfEmpty(rset.getString("conference_id"    ), "");
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
        
        if( confirm("<%=strMsg%> 하시겠습니까?")) {
            frm.action   = "banner_write_proc.jsp";
            frm.encoding = "multipart/form-data";
            frm.submit();
        }
    }
	function goModifyProc (member_cd) { 
    var frm = document.<%=formName%>;
    
	    if( confirm("수정 하시겠습니까?")) {
	        frm.modeType.value = "mod";
	        frm.action   = "banner_write_proc.jsp";
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
            frm.action   = "banner_write_proc.jsp";
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
<input type="hidden" name="banner_id" value="<%=banner_id %>">  
<input type="hidden" name="b_mode"       value="<%=b_mode      %>">
<input type="hidden" name="modeType"     value="<%=modeType    %>">
<input type="hidden" name="hidden_banner_1"     value="<%=banner_1    %>">
<input type="hidden" name="hidden_banner_2"     value="<%=banner_2    %>">
<input type="hidden" name="hidden_banner_3"     value="<%=banner_3    %>">
<input type="hidden" name="hidden_banner_4"     value="<%=banner_4    %>">
<input type="hidden" name="hidden_banner_5"     value="<%=banner_5    %>">
<input type="hidden" name="hidden_banner_6"     value="<%=banner_6    %>">
<input type="hidden" name="hidden_banner_7"     value="<%=banner_7    %>">
<input type="hidden" name="hidden_banner_8"     value="<%=banner_8    %>">
<input type="hidden" name="hidden_banner_9"     value="<%=banner_9    %>">
<input type="hidden" name="hidden_banner_10"     value="<%=banner_10    %>">
<input type="hidden" name="hidden_banner_11"     value="<%=banner_11    %>">

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
              <td width="210" class="bbbg" style="padding-left:20px; font-weight:bold;">메인 베너</td>
              <td width="574" style="padding-left:20px;">
              <%
              	if (!"".equals(banner_1)){
              		out.println("<a href='"+landing_url_1+"' target='_new'><img src=/mice/upload/banner/"+banner_1+"></a><br>");
              	}
              %>
              	<input name="banner_1" type="file" value="" class="Ttable" size="35" onchange="file_check_img(this)">
               <%
              	if (!"".equals(banner_1)){
              		out.println("<input name='ckb_banner_1' type='checkbox' value='del'>삭제");
              	}
              %>
              <br>
              <input name="landing_url_1" type="text" value="<%=landing_url_1%>" size="60">
              </td>
            </tr>
            <tr>
              <td width="210" class="bbbg" style="padding-left:20px; font-weight:bold;">Agenda 베너</td>
              <td width="574" style="padding-left:20px;">
              		<%
              	if (!"".equals(banner_2)){
              		out.println("<a href='"+landing_url_2+"' target='_new'><img src=/mice/upload/banner/"+banner_2+"></a><br>");
              	}
              %>
              <input name="banner_2" type="file" value="" class="Ttable" size="35" onchange="file_check_img(this)">
               <%
              	if (!"".equals(banner_2)){
              		out.println("<input name='ckb_banner_2' type='checkbox' value='del'>삭제");
              	}
              %>
              <br>
              <input name="landing_url_2" type="text" value="<%=landing_url_2%>" size="60">
              </td>
            </tr>
            <tr>
              <td width="210" class="bbbg" style="padding-left:20px; font-weight:bold;">Binder 베너</td>
              <td width="574" style="padding-left:20px;">
              		<%
              	if (!"".equals(banner_3)){
              		out.println("<a href='"+landing_url_3+"' target='_new'><img src=/mice/upload/banner/"+banner_3+"></a><br>");
              	}
              %>
              <input name="banner_3" type="file" value="" class="Ttable" size="35" onchange="file_check_img(this)">
               <%
              	if (!"".equals(banner_3)){
              		out.println("<input name='ckb_banner_3' type='checkbox' value='del'>삭제");
              	}
              %>
              <br>
              <input name="landing_url_3" type="text" value="<%=landing_url_3%>" size="60">
              </td>
            </tr>
            <tr>
              <td width="210" class="bbbg" style="padding-left:20px; font-weight:bold;">Search 베너</td>
              <td width="574" style="padding-left:20px;">
              		<%
              	if (!"".equals(banner_4)){
              		out.println("<a href='"+landing_url_4+"' target='_new'><img src=/mice/upload/banner/"+banner_4+"></a><br>");
              	}
              %>
              <input name="banner_4" type="file" value="" class="Ttable" size="35" onchange="file_check_img(this)">
               <%
              	if (!"".equals(banner_4)){
              		out.println("<input name='ckb_banner_4' type='checkbox' value='del'>삭제");
              	}
              %>
              <br>
              <input name="landing_url_4" type="text" value="<%=landing_url_4%>" size="60">
              </td>
            </tr>
            <tr>
              <td width="210" class="bbbg" style="padding-left:20px; font-weight:bold;">Massage 베너</td>
              <td width="574" style="padding-left:20px;">
              		<%
              	if (!"".equals(banner_5)){
              		out.println("<a href='"+landing_url_5+"' target='_new'><img src=/mice/upload/banner/"+banner_5+"></a><br>");
              	}
              %>
              <input name="banner_5" type="file" value="" class="Ttable" size="35" onchange="file_check_img(this)">
               <%
              	if (!"".equals(banner_5)){
              		out.println("<input name='ckb_banner_5' type='checkbox' value='del'>삭제");
              	}
              %>
              <br>
              <input name="landing_url_5" type="text" value="<%=landing_url_5%>" size="60">
              </td>
            </tr>
            <tr>
              <td width="210" class="bbbg" style="padding-left:20px; font-weight:bold;">MyBrifcase 베너</td>
              <td width="574" style="padding-left:20px;">
              		<%
              	if (!"".equals(banner_6)){
              		out.println("<a href='"+landing_url_6+"' target='_new'><img src=/mice/upload/banner/"+banner_6+"></a><br>");
              	}
              %>
              <input name="banner_6" type="file" value="" class="Ttable" size="35" onchange="file_check_img(this)">
               <%
              	if (!"".equals(banner_6)){
              		out.println("<input name='ckb_banner_6' type='checkbox' value='del'>삭제");
              	}
              %>
              <br>
              <input name="landing_url_6" type="text" value="<%=landing_url_6%>" size="60">
              </td>
            </tr>
            <tr>
              <td width="210" class="bbbg" style="padding-left:20px; font-weight:bold;">Map 베너</td>
              <td width="574" style="padding-left:20px;">
              		<%
              	if (!"".equals(banner_7)){
              		out.println("<a href='"+landing_url_7+"' target='_new'><img src=/mice/upload/banner/"+banner_7+"></a><br>");
              	}
              %>
              <input name="banner_7" type="file" value="" class="Ttable" size="35" onchange="file_check_img(this)">
               <%
              	if (!"".equals(banner_7)){
              		out.println("<input name='ckb_banner_7' type='checkbox' value='del'>삭제");
              	}
              %>
              <br>
              <input name="landing_url_7" type="text" value="<%=landing_url_7%>" size="60">
              </td>
            </tr>
            <tr>
              <td width="210" class="bbbg" style="padding-left:20px; font-weight:bold;">설문조사 베너</td>
              <td width="574" style="padding-left:20px;">
              		<%
              	if (!"".equals(banner_8)){
              		out.println("<a href='"+landing_url_8+"' target='_new'><img src=/mice/upload/banner/"+banner_8+"></a><br>");
              	}
              %>
              <input name="banner_8" type="file" value="" class="Ttable" size="35" onchange="file_check_img(this)">
               <%
              	if (!"".equals(banner_8)){
              		out.println("<input name='ckb_banner_8' type='checkbox' value='del'>삭제");
              	}
              %>
              <br>
              <input name="landing_url_8" type="text" value="<%=landing_url_8%>" size="60">
              </td>
            </tr>
            <tr>
              <td width="210" class="bbbg" style="padding-left:20px; font-weight:bold;">설정 베너</td>
              <td width="574" style="padding-left:20px;">
              		<%
              	if (!"".equals(banner_9)){
              		out.println("<a href='"+landing_url_9+"' target='_new'><img src=/mice/upload/banner/"+banner_9+"></a><br>");
              	}
              %>
              <input name="banner_9" type="file" value="" class="Ttable" size="35" onchange="file_check_img(this)">
               <%
              	if (!"".equals(banner_9)){
              		out.println("<input name='ckb_banner_9' type='checkbox' value='del'>삭제");
              	}
              %>
              <br>
              <input name="landing_url_9" type="text" value="<%=landing_url_9%>" size="60">
              </td>
            </tr>
            <tr>
              <td width="210" class="bbbg" style="padding-left:20px; font-weight:bold;">바코드 베너</td>
              <td width="574" style="padding-left:20px;">
              		<%
              	if (!"".equals(banner_10)){
              		out.println("<a href='"+landing_url_10+"' target='_new'><img src=/mice/upload/banner/"+banner_10+"></a><br>");
              	}
              %>
              <input name="banner_10" type="file" value="" class="Ttable" size="35" onchange="file_check_img(this)">
               <%
              	if (!"".equals(banner_10)){
              		out.println("<input name='ckb_banner_10' type='checkbox' value='del'>삭제");
              	}
              %>
              <br>
              <input name="landing_url_10" type="text" value="<%=landing_url_10%>" size="60">
              </td>
            </tr>
            <tr>
              <td width="210" class="bbbg" style="padding-left:20px; font-weight:bold;">도움말 베너</td>
              <td width="574" style="padding-left:20px;">
              		<%
              	if (!"".equals(banner_11)){
              		out.println("<a href='"+landing_url_11+"' target='_new'><img src=/mice/upload/banner/"+banner_11+"></a><br>");
              	}
              %>
              <input name="banner_11" type="file" value="" class="Ttable" size="35" onchange="file_check_img(this)">
               <%
              	if (!"".equals(banner_11)){
              		out.println("<input name='ckb_banner_11' type='checkbox' value='del'>삭제");
              	}
              %>
              <br>
              <input name="landing_url_11" type="text" value="<%=landing_url_11%>" size="60">
              </td>
            </tr>
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