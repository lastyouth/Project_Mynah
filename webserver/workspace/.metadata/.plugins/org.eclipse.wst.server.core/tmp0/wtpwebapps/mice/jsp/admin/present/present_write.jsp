<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<% request.setCharacterEncoding("UTF-8"); %>
<%
	String root_path = request.getContextPath();
%>
<%@ page import = "java.util.*" %>
<%@ page import = "java.text.*" %>
<%@ page import = "sips.common.lib.util.*" %>
<%@ page import = "sips.common.lib.value.*" %>
<%@ page import = "sips.dept.contents.Present" %>
<%@ page import = "sips.dept.contents.Manufacturer" %>
<%@ page import = "sips.dept.contents.Conference" %>
<%  String formName = "frmBoard"; %>
<%
	ParamValue       parmValue = new ParamValue();      
Present            present     = new Present();
	Conference       conference     = new Conference();
	Manufacturer            manufacturer     = new Manufacturer();
    ResultSetValue   rset      = null;
    ResultSetValue   rset2      = null;
    ResultSetValue   rset3      = null;
    ResultSetValue   rset4      = null;

    String user_cd         = (String)session.getAttribute("USER_CD");
    String user_authority         = (String)session.getAttribute("USE_AUTHORITY");
	int    cpage              = (request.getParameter("cpage") == null || "".equals(request.getParameter("cpage"))) ? 0 : Integer.parseInt(request.getParameter("cpage"));   // 현재 페이지
    String searchTarget       = StringUtil.defaultIfEmpty(request.getParameter("searchTarget"), "");	      // 검색 조건 (제목/내용)
    String keyword            = StringUtil.defaultIfEmpty(request.getParameter("keyword"     ), "");        // 검색할 키워드
    String searchFlag         = StringUtil.defaultIfEmpty(request.getParameter("searchFlag"  ), "");        // 검색플래그

    String conference_id             = StringUtil.defaultIfEmpty(request.getParameter("conference_id"      ), ""); 
    String modeType           = StringUtil.defaultIfEmpty(request.getParameter("modeType"    ), "ins");
    String b_mode             = StringUtil.defaultIfEmpty(request.getParameter("b_mode"      ), ""); 
    String present_id             = StringUtil.defaultIfEmpty(request.getParameter("present_id"      ), ""); 

    String name  = "";
    String manufacturer_id = "";
    String manufacturer_name    = "";
    String manufacturer_ceo    = "";
    String phone    = "";
    String present_name    = "";
    String start_date    = "";
    String end_date    = "";
    String present_image    = "";
    String contents    = "";
    String security    = "";
    String strMsg = "";
    String sel_conference_name = "";
    String sel_conference_id = "";
    String sel_manufacturer_name = "";
    String sel_manufacturer_id = "";
    String sel_manufacturer_ceo = "";
    String sel_phone = "";
    String conference_start_date = "";
    String conference_end_date = "";
    String sel_security = "";
    
    parmValue.put("conference_id"    , conference_id  );
    parmValue.put("present_id"    , present_id  );
    parmValue.put("user_cd"        , user_cd  );  
    parmValue.put("user_authority"        , user_authority  );  

    if( !"".equals(present_id)) {
        rset = present.getPresentInfo(parmValue);
        while( rset.next()){
        	present_name    = StringUtil.defaultIfEmpty(rset.getString("present_name"    ), "");
        	start_date    = StringUtil.defaultIfEmpty(rset.getString("start_date"    ), "");
        	end_date    = StringUtil.defaultIfEmpty(rset.getString("end_date"    ), "");
        	present_image    = StringUtil.defaultIfEmpty(rset.getString("present_image"    ), "");
        	contents    = StringUtil.defaultIfEmpty(rset.getString("contents"    ), "");
        	manufacturer_id    = StringUtil.defaultIfEmpty(rset.getString("manufacturer_id"    ), "");
        	manufacturer_name    = StringUtil.defaultIfEmpty(rset.getString("manufacturer_name"    ), "");
        	manufacturer_ceo    = StringUtil.defaultIfEmpty(rset.getString("manufacturer_ceo"    ), "");
        	phone    = StringUtil.defaultIfEmpty(rset.getString("phone"    ), "");
        	security    = StringUtil.defaultIfEmpty(rset.getString("security"    ), "");
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

    
%>
<script language="JavaScript" type="text/JavaScript">
<!--
    /**
     * 게시물 등록
     */
    function goRegitProc () {
        var frm = document.<%=formName%>;
        

        if (frm.manufacturer_id.value == "") {
            alert("업체명을 선택하세요.");
            frm.manufacturer_id.focus();
            return;
        }
        if (frm.present_name.value == "") {
            alert("선물명을 입력하세요.");
            frm.present_name.focus();
            return;
        }
    	var start_date_obj = document.getElementById('start_date');
    	var start_date_value = start_date_obj.options[start_date_obj.selectedIndex].value;
    	var end_date_obj = document.getElementById('end_date');
    	var end_date_value = end_date_obj.options[end_date_obj.selectedIndex].value;
        if (start_date_value > end_date_value) {
            alert("유효기간 종료일을 확인하세요");
            frm.end_date.focus();
            return;
        }
        if (frm.present_image.value == "") {
            alert("선물 이미지를 등록하세요.");
            frm.present_image.focus();
            return;
        }
        if (frm.contents.value == "") {
            alert("내요을 입력하세요.");
            frm.contents.focus();
            return;
        }

        if( confirm("<%=strMsg%> 하시겠습니까?")) {
            frm.action   = "present_write_proc.jsp";
            frm.encoding = "multipart/form-data";
            frm.submit();
        }
    }
	function goModifyProc (member_cd) { 
    var frm = document.<%=formName%>;
    
	    if( confirm("수정 하시겠습니까?")) {
	        frm.modeType.value = "mod";
	        frm.action   = "present_write_proc.jsp";
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
            frm.action   = "present_write_proc.jsp";
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
    
    function conference_change(obj)
	{
        var frm = document.<%=formName%>;
		//var obj_value = conference_id.value;
		//alert(obj_value);
		frm.target="_self";
		frm.b_mode.value = "W";
		frm.action             = "./present.jsp";
		frm.submit();
	}
    
    function manufacturer_change(obj){
    	var frm = document.<%=formName%>;
		var obj_value = frm.manufacturer_id.value;
<% 
	//선물 업체 셀렉트 리스트
	rset4 = manufacturer.getManufacturerSelectList(parmValue);
	if(  rset4.row()==0) { %>
                    
<%    
        } else {
            while( rset4.next()){
            	sel_manufacturer_id     = StringUtil.defaultIfEmpty(rset4.getString("manufacturer_id" ), "");
            	sel_manufacturer_ceo    = StringUtil.defaultIfEmpty(rset4.getString("manufacturer_ceo"), "");
            	sel_phone    = StringUtil.defaultIfEmpty(rset4.getString("phone"), "");
            	sel_security    = StringUtil.defaultIfEmpty(rset4.getString("security"), "");
%>				
    			if (obj_value == '<%=sel_manufacturer_id%>'){
    				frm.manufacturer_ceo.value = "<%=sel_manufacturer_ceo%>";
    				frm.phone.value = "<%=sel_phone%>";
    				frm.security.value = "<%=sel_security%>";
    			}
    			
<%      } 
        }
%>               	
    	
    	
    }
    function make_start_date(){
    	var frm = document.<%=formName%>;
    	var sel_start_date = frm.start_date;
    	var sel_end_date = frm.end_date;
<%
    rset3 = conference.getConferenceInfo(parmValue);
  	if( rset3.next()){
  		conference_start_date = rset3.getString("start_date");
  		conference_end_date = rset3.getString("end_date");       	    
  	}
%>
    	var start_date = "<%=conference_start_date%>";
    	var end_date = "<%=conference_end_date%>";
    	
    	var sDate = new Date(start_date);
    	var eDate = new Date(end_date);
    	var diff = Math.ceil((eDate.getTime() - sDate.getTime())/(1000*60*60*24));

    	sel_start_date.innerHTML = "";
    	sel_end_date.innerHTML = "";
    	for(var i=0; i <= diff; i++){
    		var Dyear = sDate.getFullYear();
    		var Dmonth = sDate.getMonth()+1;
    		if (Dmonth < 10)
    			Dmonth = '0'+Dmonth;
    		var Dday = sDate.getDate()+i;
    		if (Dday < 10)
    			Dday = '0'+Dday;
    		//alert(Dyear+'-'+Dmonth+'-'+Dday);
    		date_key = Dyear+'-'+Dmonth+'-'+Dday;
    		sel_start_date.options[i] = new Option((date_key),(date_key)); 
    		sel_end_date.options[i] = new Option((date_key),(date_key)); 
    		if (date_key == '<%=start_date%>'){
    			sel_start_date.options[i].selected = true;
    		}
    		if (date_key == '<%=end_date%>'){
    			sel_end_date.options[i].selected = true;
    		}
    		
    	}
    	
    }
    
    function make_end_date(obj){
    	var frm = document.<%=formName%>;
    	var obj = document.getElementById('start_date');
    	var value = obj.options[obj.selectedIndex].value;
    	alert(value);
    }
    
    window.onload=make_start_date;
  //-->
</script>
<form name="<%=formName%>" method="post">
<input type="hidden" name="cpage"        value="<%= cpage       %>">
<input type="hidden" name="present_id" value="<%=present_id %>">  
<input type="hidden" name="b_mode"       value="<%=b_mode      %>">
<input type="hidden" name="modeType"     value="<%=modeType    %>">
<input type="hidden" name="hidden_present_image"     value="<%=present_image    %>">

<table width="100%" border="0" cellspacing="0" cellpadding="0">
       
        <td valign="top"><table border="0" cellpadding="0" cellspacing="0" style="border-top:2px solid #e0e0e0; border-bottom:2px solid #e0e0e0; width:784px;">
            <tr>
              <td width="210" class="bbbg" style="padding-left:20px; font-weight:bold;">컨퍼런스명</td>
              <td width="574" style="padding-left:20px;">
              	<select name="conference_id" id="conference_id" onchange="conference_change(this)">
              			<option value="0" >선택</option>
              	<%
              	String selected = "";
                rset3 = conference.getConferenceSelectList(parmValue);
              	while( rset3.next()){
                  	if (conference_id.equals(rset3.getString("conference_id"))){
                  		selected = "selected";
                  	}else{
                  		selected = "";
                  	}
              		out.println("<option value="+rset3.getString("conference_id"    )+"  "+selected+">"+rset3.getString("name"    )+"</option>");            	    
              	}
              	%>
              		
              	</select>
              </td>
            </tr>
            
            <tr>
              <td width="210" class="bbbg" style="padding-left:20px; font-weight:bold;">업체명</td>
              <td width="574" style="padding-left:20px;">
              	<select name=manufacturer_id id=manufacturer_id onchange="manufacturer_change(this)">
              			<option value="" >선택</option>
<%
	//선물 업체 셀렉트 리스트
	rset4 = manufacturer.getManufacturerSelectList(parmValue);
    if(  rset4.row()==0) { %>
                
<%    
    } else {
        while( rset4.next()){
        	sel_manufacturer_id     = StringUtil.defaultIfEmpty(rset4.getString("manufacturer_id" ), "");
        	sel_manufacturer_name    = StringUtil.defaultIfEmpty(rset4.getString("manufacturer_name"), "");
%>				
			<option value="<%=sel_manufacturer_id%>" <%= sel_manufacturer_id.equals(manufacturer_id) ? "selected" : "" %> ><%=sel_manufacturer_name%></option>
			
<%      } 
    }
    %>           
          		</select>
              </td>
            </tr>
            <tr>
              <td width="210" class="bbbg" style="padding-left:20px; font-weight:bold;">대표자</td>
              <td width="574" style="padding-left:20px;"><input name="manufacturer_ceo" type="text" value="<%=manufacturer_ceo%>" class="Ttable" size="10" readonly></td>
            </tr>
            <tr>
              <td width="210" class="bbbg" style="padding-left:20px; font-weight:bold;">연락처</td>
              <td width="574" style="padding-left:20px;"><input name="phone" type="text" value="<%=phone%>" class="Ttable" size="15" readonly></td>
            </tr>
            <tr>
              <td width="210" class="bbbg" style="padding-left:20px; font-weight:bold;">보안코드</td>
              <td width="574" style="padding-left:20px;"><input name="security" type="text" value="<%=security%>" class="Ttable" size="10" readonly></td>
            </tr>
            <tr>
              <td width="210" class="bbbg" style="padding-left:20px; font-weight:bold;">선물명</td>
              <td width="574" style="padding-left:20px;"><input name="present_name" type="text" value="<%=present_name%>" class="Ttable" size="20"></td>
            </tr>
            <tr>
              <td width="210" class="bbbg" style="padding-left:20px; font-weight:bold;">유효기간</td>
              <td width="574" style="padding-left:20px;">
              		<select name="start_date" id="start_date">
	              	</select>
              		~
              		<select name="end_date" id="end_date">
	              	</select>
              </td>
            </tr>
            <tr>
              <td width="210" class="bbbg" style="padding-left:20px; font-weight:bold;">선물이미지<br>
              	iOS : 640 * 96(px)<br>
              	iPad : 720 * 98(px)<br>
              	Android : 640 * 96(px)<br>
              	80KB 이하(JPG, PNG)
              	</td>
              
              <td width="574" style="padding-left:20px;">
              <%
              	if (!"".equals(present_image)){
              		out.println("<img src=/mice/upload/present/"+present_image+"><br>");
              	}
              %>
              <input name=present_image type="file" value="" class="Ttable" size="35" onchange="file_check_img(this)"></td>
            </tr>
            <tr>
              <td width="210" class="bbbg" style="padding-left:20px; font-weight:bold;">내용</td>
              <td width="574" style="padding-left:20px;">
					<textarea name="contents" rows="5" cols="60"><%=contents %></textarea>
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
            <a href="javascript:goDeleteForm('<%= present_id %>');"><img src="<%=root_path%>/images/bt_del.gif" alt="삭제" /></a>
         <% } %>   
            <a href="javascript:goList();"><img src="<%=root_path%>/images/bt_list.gif" alt="목록" /></a>
         </td>
      </tr>
    </table>
    
</form>		