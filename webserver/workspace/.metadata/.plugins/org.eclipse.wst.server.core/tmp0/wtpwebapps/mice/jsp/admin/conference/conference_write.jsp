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
    ResultSetValue   rset2      = null;
    
	int    cpage              = (request.getParameter("cpage") == null || "".equals(request.getParameter("cpage"))) ? 0 : Integer.parseInt(request.getParameter("cpage"));   // 현재 페이지
    String searchTarget       = StringUtil.defaultIfEmpty(request.getParameter("searchTarget"), "");	      // 검색 조건 (제목/내용)
    String keyword            = StringUtil.defaultIfEmpty(request.getParameter("keyword"     ), "");        // 검색할 키워드
    String searchFlag         = StringUtil.defaultIfEmpty(request.getParameter("searchFlag"  ), "");        // 검색플래그
    String conference_id       = StringUtil.defaultIfEmpty(request.getParameter("conference_id"), "");
    String modeType           = StringUtil.defaultIfEmpty(request.getParameter("modeType"    ), "ins");
    String b_mode             = StringUtil.defaultIfEmpty(request.getParameter("b_mode"      ), ""); 
    
    String name  = "";
    String start_date = "";
    String end_date    = "";
    String reg_date = "";
    String banner_image = "";
    String info_image = "";
    String strMsg             = "";
    String admin_id = "";
    String admin_name = "";
    String admin_user_cd = "";
    String package_name = "";
    
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
            package_name    = StringUtil.defaultIfEmpty(rset.getString("package_name"    ), "");
        }
        rset2 = conference.getConferenceAdmin(parmValue);
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
     //레이어 팝업 열기
  	function openLayer(IdName, tpos, lpos){
  		var pop = document.getElementById(IdName);
  		document.all['moveFrame'].src = './admin_add_popup.jsp?conference_id=<%=conference_id%>';
 			pop.style.display = "block";
 			pop.style.top = tpos + "px";
 			pop.style.left = lpos + "px";
  	}

  	//레이어 팝업 닫기
  	function closeLayer(IdName){
  		var pop = document.getElementById(IdName);
  		pop.style.display = "none";
  	}
  	
    function goRegitProc () {
        var frm = document.<%=formName%>;
        var d = new Date();
        var month     = d.getMonth() + 1;
        var MM   = (("" + month).length == 1) ? "0" + month : "" + mont
        var day       = d.getDate();
        var DD   = (("" + day).length == 1) ? "0" + day : "" + day;
        var today = d.getFullYear()+"-"+MM+"-"+DD;
        var start_date = frm.start_date.value;

        if (frm.name.value == "") {
            alert("컨퍼런스명을 입력하세요.");
            frm.name.focus();
            return;
        }
        
        if (frm.package_name.value == "") {
            alert("패키지네임을 입력하세요.");
            frm.package_name.focus();
            return;
        }
        if ((frm.package_name.value).indexOf('.') <= 0) {
            alert("패키지네임이 잘못되었습니다.");
            frm.package_name.focus();
            return;
        }
        if (frm.start_date.value == "") {
            alert("시작일을 입력하세요.");
            frm.id.focus();
            return;
        }
        if (frm.end_date.value == "") {
            alert("종료일을 선택하세요.");
            frm.repartment.focus();
            return;
        }
        
        if (frm.start_date.value > frm.end_date.value){
        	alert("종료일을 시작일 이전으로 설정해주세요.");
            frm.repartment.focus();
            return;
        }
        
        if ( today >= start_date){
        	alert("시작일을 오늘 이후로 설정해주세요!");
            frm.repartment.focus();
            return;
        }

        if( confirm("<%=strMsg%> 하시겠습니까?")) {
            frm.action   = "conference_write_proc.jsp";
            frm.encoding = "multipart/form-data";
            frm.submit();
        }
    }
	function goModifyProc (member_cd) { 
        var frm = document.<%=formName%>;
        var d = new Date();
        var month     = d.getMonth() + 1;
        var MM   = (("" + month).length == 1) ? "0" + month : "" + mont
        var day       = d.getDate();
        var DD   = (("" + day).length == 1) ? "0" + day : "" + day;
        var today = d.getFullYear()+"-"+MM+"-"+DD;
        var start_date = frm.start_date.value;
    
    if (frm.name.value == "") {
        alert("컨퍼런스명을 입력하세요.");
        frm.name.focus();
        return;
    }
    
    if (frm.package_name.value == "") {
        alert("패키지네임을 입력하세요.");
        frm.package_name.focus();
        return;
    }
    if ((frm.package_name.value).indexOf('.') <= 0) {
        alert("패키지네임이 잘못되었습니다.");
        frm.package_name.focus();
        return;
    }
    if (frm.start_date.value == "") {
        alert("시작일을 입력하세요.");
        frm.id.focus();
        return;
    }
    if (frm.end_date.value == "") {
        alert("종료일을 선택하세요.");
        frm.repartment.focus();
        return;
    }
    
    if (frm.start_date.value > frm.end_date.value){
    	alert("종료일을 시작일 이전으로 설정해주세요.");
        frm.repartment.focus();
        return;
    }
    
    if ( today >= start_date){
    	alert("시작일을 오늘 이후로 설정해주세요!");
        frm.repartment.focus();
        return;
    }
    
	    if( confirm("수정 하시겠습니까?")) {
	        frm.modeType.value = "mod";
	        frm.action   = "conference_write_proc.jsp";
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
            frm.action   = "conference_write_proc.jsp";
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

	function goAdminDelForm (admin_user_cd ) {        
	    var frm = document.<%=formName%>;
	    
	    frm.admin_user_cd.value = admin_user_cd;
	    frm.target="_self";
	    frm.b_mode.value = "AD";
	    frm.modeType.value = "mod";
	    if( confirm("컨퍼런스 권한을 삭제 하시겠습니까?")) {
	        frm.action   = "conference_write_proc.jsp";
	        frm.encoding = "multipart/form-data";
	        frm.submit();
	    }
	}  
  //-->
</script>
<form name="<%=formName%>" method="post">
<input type="hidden" name="cpage"        value="<%= cpage       %>">
<input type="hidden" name="conference_id" value="<%=conference_id %>">  
<input type="hidden" name="b_mode"       value="<%=b_mode      %>">
<input type="hidden" name="modeType"     value="<%=modeType    %>">
<input type="hidden" name="admin_user_cd">   
<input type="hidden" name="hidden_banner_image"     value="<%=banner_image    %>">
<input type="hidden" name="hidden_info_image"     value="<%=info_image    %>">

<table width="100%" border="0" cellspacing="0" cellpadding="0">
      
      <tr>
        <td valign="top"><table border="0" cellpadding="0" cellspacing="0" style="border-top:2px solid #e0e0e0; border-bottom:2px solid #e0e0e0; width:784px;">
            <tr>
              <td width="210" class="bbbg" style="padding-left:20px; font-weight:bold;">컨퍼런스명</td>
              <td width="574" style="padding-left:20px;"><input name="name" type="text" value="<%=name%>" class="Ttable" size="35"></td>
            </tr>
            <tr>
              <td width="210" class="bbbg" style="padding-left:20px; font-weight:bold;">패키지네임</td>
              <td width="574" style="padding-left:20px;"><input name="package_name" type="text" value="<%=package_name%>" class="Ttable" size="35"></td>
            </tr>
            <tr>
              <td width="210" class="bbbg" style="padding-left:20px; font-weight:bold;">시작일</td>
              <td width="574" style="padding-left:20px;"><input type="text" name="start_date" id="datepicker1" value="<%=start_date%>"  class="Ttable" size="10"></td>
            </tr>
            <tr>
              <td width="210" class="bbbg" style="padding-left:20px; font-weight:bold;">종료일</td>
              <td width="574" style="padding-left:20px;"><input type="text" name="end_date" id="datepicker2" value="<%=end_date%>"  class="Ttable" size="10"></td>
            </tr>
            <tr>
              <td width="210" class="bbbg" style="padding-left:20px; font-weight:bold;">베너이미지<br>
              	iOS : 640 * 96(px)<br>
              	iPad : 720 * 98(px)<br>
              	Android : 640 * 96(px)<br>
              	80KB 이하(JPG, PNG)
              </td>
              
              <td width="574" style="padding-left:20px;">
              <%
              	if (!"".equals(banner_image)){
              		out.println("<img src=/mice/upload/conference/"+banner_image+">");
              	}
              %><br>
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
<%if( !"".equals(conference_id)) { %>            
            <tr>
              <td class="bbbg" style="padding-left:20px; font-weight:bold;">컨퍼런스 관리자</td>
              <td style="padding-left:20px;">
              <a href="#" onclick="openLayer('layerPop',170,400)"><input type='button' value='관리자 추가'></a><br><br>
              	<%
              	
              	while( rset2.next()){
              		admin_id    = StringUtil.defaultIfEmpty(rset2.getString("admin_id"    ), "");
            		admin_name    = StringUtil.defaultIfEmpty(rset2.getString("admin_name"    ), "");
            		admin_user_cd    = StringUtil.defaultIfEmpty(rset2.getString("admin_user_cd"    ), "");
            		
            		out.println(admin_name+"("+admin_id+") <a href=javascript:goAdminDelForm('"+admin_user_cd+"')><input type='button' value='삭제'></a> <br>");
            	}
				%>
              </td>
            </tr>
<%} %>            
        </table></td>
      </tr>
      <tr>
        <td height="50" align="center">
        <% if(modeType.equals("ins")){ %>
            <a href="javascript:goRegitProc();"><img src="<%=root_path%>/images/bt_up.gif" alt="등록" /></a>
         <% }else{ %>
            <a href="javascript:goModifyProc();"><img src="<%=root_path%>/images/bt_modify.gif" alt="수정" /></a>
            <a href="javascript:goDeleteForm('<%= conference_id %>');"><img src="<%=root_path%>/images/bt_del.gif" alt="삭제" /></a>
         <% } %>   
            <a href="javascript:goList();"><img src="<%=root_path%>/images/bt_list.gif" alt="목록" /></a>
         </td>
      </tr>
    </table>
    
</form>		
<!-- 팝업 -->
            <div  id="layerPop" >
             <iframe id="moveFrame" frameborder='0' height='100%' width='100%' scrolling='no' src=''></iframe>
            </div>
            <!--// 팝업 -->