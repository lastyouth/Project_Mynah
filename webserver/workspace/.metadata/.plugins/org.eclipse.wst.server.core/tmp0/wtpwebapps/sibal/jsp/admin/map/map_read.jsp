<%@ page language = "java" contentType="text/html; charset=UTF-8" %>
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
	Map            map     = new Map();
	Conference       conference     = new Conference();
   
    ResultSetValue   rset      = null;
    ResultSetValue   rset3      = null;

    String user_cd         = (String)session.getAttribute("USER_CD");
    String user_authority         = (String)session.getAttribute("USE_AUTHORITY");
    String b_mode             = StringUtil.defaultIfEmpty(request.getParameter("b_mode"      ), ""); 
	int    cpage              = (request.getParameter("cpage") == null || "".equals(request.getParameter("cpage"))) ? 0 : Integer.parseInt(request.getParameter("cpage"));   // 현재 페이지
    String conference_id         = StringUtil.defaultIfEmpty(request.getParameter("conference_id"  ),"");

    String map_image  = "";
    String title  = "";
	String map_id = "";
    String strMsg             = "";
    String sort  = "";
    String sel_conference_name = "";
    String sel_conference_id = "";

    String modeType           = "";
    int map_count           = 0;

    parmValue.put("user_cd"        , user_cd  );  
    parmValue.put("user_authority"        , user_authority  );  
    parmValue.put("conference_id"  , conference_id  );  
    rset3 = conference.getConferenceSelectList(parmValue);
    rset = map.getMapInfo(parmValue);
    map_count = rset.row();
	
%>		
<script language="JavaScript" type="text/JavaScript">
<!--
    /* page 이동 */
    function goList() {
        var frm = document.<%=formName%>;
        
        frm.b_mode.value = "L";
        frm.target="_self";
        frm.action = "<%=request.getRequestURI()%>";
        frm.submit();
    }

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
    function goInsertProc () { 
	    var frm = document.<%=formName%>;
	    if (frm.titles.value == "") {
            alert("맵설명을 선택해주세요.");
            frm.titles.focus();
            return;
        }
	    if (frm.files.value == "") {
            alert("첨부파일을 선택해주세요.");
            frm.files.focus();
            return;
        }
	    
	    if( confirm("등록 하시겠습니까?")) {
	        frm.action   = "map_write_proc.jsp";
	        frm.encoding = "multipart/form-data";
	        frm.submit();
	    }
	}    
    function conference_change(obj)
	{
    	var frm = document.<%=formName%>;
        frm.b_mode.value = "R";
		frm.target="_self";
		frm.action             = "./map.jsp";
		frm.submit();
	}

//-->
</script>
  
    <table width="100%" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td valign="top">
           <table border="0" cellpadding="0" cellspacing="0" style="border-top:2px solid #e0e0e0; border-bottom:2px solid #e0e0e0; width:784px;">
            <form name="<%=formName%>" method="post"> 
			<input type="hidden" name="map_count"  value="<%=map_count%>">   
			<input type="hidden" name="b_mode" >   
           <tr>
              <td width="210" class="bbbg" style="padding-left:20px; font-weight:bold;">컨퍼런스</td>
              <td width="574" style="padding-left:20px;">
<%
	if (map_count == 0){
%>              
              		<select name=conference_id id=conference_id onchange="conference_change(this)">
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
			
<%      	} 
   		}
%> 
          		</select>
<%
	}else{
		while( rset3.next()){
	    	sel_conference_name     = StringUtil.defaultIfEmpty(rset3.getString("name" ), "");
	    	sel_conference_id    = StringUtil.defaultIfEmpty(rset3.getString("conference_id"), "");
	    	if ( conference_id.equals(sel_conference_id) ){
	    		out.print(sel_conference_name);
    			out.print("<input type='hidden' name='conference_id'  value='"+conference_id+"' >");
	    	}
	    } 
	}
%>         		
              </td>
            </tr>
            <tr>
              <td width="210" class="bbbg" style="padding-left:20px; font-weight:bold;">추가<br>
              	iOS : 640 * 96(px)<br>
              	iPad : 720 * 98(px)<br>
              	Android : 640 * 96(px)<br>
              	80KB 이하(JPG, PNG)
              	</td>
              <td width="574" style="padding-left:20px;">
	              	<input type="text" name="titles" id="titles" value="" size="40">
              		<input type='file' name='files' id="files" value="" size="40" onchange="file_check_img(this)">
              		<a href="javascript:goInsertProc();"><img src="<%=root_path%>/images/bt_up.gif" alt="등록" /></a>
               </td>
            </tr>
            </form>
<%           
    while( rset.next()){
    	title    = StringUtil.defaultIfEmpty(rset.getString("title"    ), "");
    	map_image    = StringUtil.defaultIfEmpty(rset.getString("map_image"    ), "");
    	map_id    = StringUtil.defaultIfEmpty(rset.getString("map_id"    ), "");
    	sort    = StringUtil.defaultIfEmpty(rset.getString("sort"    ), "");
%>
			<tr>
              <td width="210" class="bbbg" style="padding-left:20px; font-weight:bold;"><%=title %></td>
              <td width="574" style="padding-left:20px;">
              	<%
              	if (!"".equals(map_image)){
              	%>	
              	<script language="JavaScript" type="text/JavaScript">
				<!--
				function goModifyProc_<%= map_id %> () { 
				    var frm = document.frm_<%= map_id %>;
				    if (frm.titles.value == "") {
			            alert("맵설명을 선택해주세요.");
			            frm.titles.focus();
			            return;
			        }
				    if (frm.files.value == "") {
			            alert("첨부파일을 선택해주세요.");
			            frm.files.focus();
			            return;
			        }
				    if( confirm("수정 하시겠습니까?")) {
				        frm.action   = "map_write_proc.jsp";
				        frm.encoding = "multipart/form-data";
				        frm.submit();
				    }
				}    
				function goDeleteProc_<%= map_id %> () { 
				    var frm = document.frm_<%= map_id %>;
				    if( confirm("삭제 하시겠습니까?")) {
				        frm.action   = "map_write_proc.jsp";
				        frm.encoding = "multipart/form-data";
				        frm.submit();
				    }
				}    
				//-->
              	</script>
              	<form name="frm_<%= map_id %>" method="post">
					<input type="hidden" name="conference_id"      value="<%=conference_id%>">   
					<input type="hidden" name="map_id"  value="<%=map_id%>">   
					<input type="hidden" name="sort"  value="<%=sort%>">   
              		<img src="/mice/upload/map/<%=map_image%>" width="574" ><br>
	              	<input type="text" name="titles" id="titles" value="" size="40">
              		<input type='file' name='files' id="files" value="" size="40" onchange="file_check_img(this)">
              		<a href="javascript:goModifyProc_<%= map_id %>();"><img src="<%=root_path%>/images/bt_modify.gif" alt="수정" /></a>
              		<a href="javascript:goDeleteProc_<%= map_id %>();"><img src="<%=root_path%>/images/bt_del.gif" alt="삭제" /></a>
              	</form>	    
              <%
              	}
              %>
              </td>
            </tr>	
<%    	
	}
%>    
        </table></td>
      </tr>
      <tr>
        <td height="50" align="center">
					 <a href="javascript:goList();"><img src="/dhsp/images/bt_list.gif" alt="목록" /></a>
                          
        </td>
      </tr>      
    </table>

