<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<% request.setCharacterEncoding("UTF-8"); %>
<%
	String root_path = request.getContextPath();
%>
<%@ page import = "sips.common.lib.util.*" %>
<%@ page import = "sips.common.lib.value.*" %>
<%@ page import = "sips.dept.contents.Map" %>
<%@ page import = "sips.dept.contents.Conference" %>
<%  String formName = "frmBoard"; %>
<%
	ParamValue       parmValue = new ParamValue();          
	Map        map     = new Map();
	Conference       conference     = new Conference();
    ResultSetValue   rset      = null;
    ResultSetValue   rset3      = null;

    String user_cd         = (String)session.getAttribute("USER_CD");
    String user_authority         = (String)session.getAttribute("USE_AUTHORITY");
	int    cpage              = (request.getParameter("cpage") == null || "".equals(request.getParameter("cpage"))) ? 0 : Integer.parseInt(request.getParameter("cpage"));   // 현재 페이지

    String modeType           = StringUtil.defaultIfEmpty(request.getParameter("modeType"    ), "ins");
    String b_mode             = StringUtil.defaultIfEmpty(request.getParameter("b_mode"      ), ""); 
    String map_id             = StringUtil.defaultIfEmpty(request.getParameter("map_id"      ), ""); 
    String conference_id             = StringUtil.defaultIfEmpty(request.getParameter("conference_id"      ), ""); 

    String map_1  = "";
    String map_2  = "";
    String map_3  = "";
    String map_4  = "";
    String map_5  = "";
    String map_6  = "";
    String map_7  = "";
    String map_8  = "";
    String map_9  = "";
    String map_10  = "";
    String map_11  = "";
    String hidden_map_1    = "";
    String hidden_map_2    = "";
    String hidden_map_3    = "";
    String hidden_map_4    = "";
    String hidden_map_5    = "";
    String hidden_map_6    = "";
    String hidden_map_7    = "";
    String hidden_map_8    = "";
    String hidden_map_9    = "";
    String hidden_map_10    = "";
    String hidden_map_11    = "";
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


    parmValue.put("conference_id"    , conference_id  );
    parmValue.put("user_cd"        , user_cd  );  
    parmValue.put("user_authority"        , user_authority  );  

    if( !"".equals(conference_id)) {
    	rset = map.getMapInfo(parmValue);
 
        if( "W".equals(b_mode)) {
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
            frm.action   = "map_write_proc.jsp";
            frm.encoding = "multipart/form-data";
            frm.submit();
        }
    }
	function goModifyProc (member_cd) { 
    var frm = document.<%=formName%>;
    alert(frm.title_1.value);
    
	    if( confirm("수정 하시겠습니까?")) {
	        frm.modeType.value = "mod";
	        frm.action   = "map_write_proc.jsp";
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
            frm.action   = "map_write_proc.jsp";
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
  
  
	attachFile = {
	          idx:0,
	          add:function(){ // 파일필드 추가
	               var o = this;
	               var idx = o.idx;
	               var frm = document.<%=formName%>;

	               var div = document.createElement('div');
	               div.style.marginTop = '3px';
	               div.id = 'file' + o.idx;

	               var file = document.all ? document.getElementById('files').cloneNode(true) : document.createElement('input');
	               file.type = 'file';
	               file.name = 'file_' + parseInt(o.idx+1);
	               file.size = '40';
	               file.id = 'fileField' + o.idx;

	               var title = document.all ? document.getElementById('titles').cloneNode(true) : document.createElement('input');
	               title.type = 'text';
	               title.name = 'title_' + parseInt(o.idx+1);
	               title.size = '40';
	               title.id = 'titleField' + o.idx;

	               var btn = document.createElement('input');
	               btn.type = 'button';
	               btn.value = '삭제';
	               btn.onclick = function(){o.del(idx)}
	               btn.style.marginLeft = '5px';

	               div.appendChild(title);
	               div.appendChild(file);
	               div.appendChild(btn);
	               document.getElementById('attachFileDiv').appendChild(div);

	               o.idx++;
	               frm.num.value =  o.idx;
	          },
	          del:function(idx){ // 파일필드 삭제
	               if(document.getElementById('fileField' + idx).value != '' && !confirm('삭제 하시겠습니까?')){
	                    return;
	               }
	               document.getElementById('attachFileDiv').removeChild(document.getElementById('file' + idx));
	               document.getElementById('attachFileDiv').removeChild(document.getElementById('title' + idx));
	          }
	     }
  //-->
</script>
<form name="<%=formName%>" method="post">
<input type="hidden" name="cpage"        value="<%= cpage       %>">
<input type="hidden" name="conference_id" value="<%=conference_id %>">  
<input type="hidden" name="b_mode"       value="<%=b_mode      %>">
<input type="hidden" name="modeType"     value="<%=modeType    %>">
<input type="hidden" name="hidden_map_1"     value="<%=map_1    %>">
<input type="hidden" name="hidden_map_2"     value="<%=map_2    %>">
<input type="hidden" name="hidden_map_3"     value="<%=map_3    %>">
<input type="hidden" name="hidden_map_4"     value="<%=map_4    %>">
<input type="hidden" name="hidden_map_5"     value="<%=map_5    %>">
<input type="hidden" name="hidden_map_6"     value="<%=map_6    %>">
<input type="hidden" name="hidden_map_7"     value="<%=map_7    %>">
<input type="hidden" name="hidden_map_8"     value="<%=map_8    %>">
<input type="hidden" name="hidden_map_9"     value="<%=map_9    %>">
<input type="hidden" name="hidden_map_10"     value="<%=map_10    %>">
<input type="hidden" name="hidden_map_11"     value="<%=map_11    %>">
<input type="hidden" name="num"   >

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
              <td width="210" class="bbbg" style="padding-left:20px; font-weight:bold;">맵이미지
	            <input type="button" value="추가" onclick="attachFile.add()" style="margin-left:5px">
	          </td>
              <td width="574" style="padding-left:20px;">
              	<div id="attachFileDiv">
              	<input type="text" name="title_0" id="titles" value="" size="40">
	            <input type="file" name="file_0" id="files" value="" size="40">
	            </div>
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