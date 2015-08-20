<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<% request.setCharacterEncoding("UTF-8"); %>
<%
	String root_path = request.getContextPath();
%>
<%@ page import = "java.util.*" %>
<%@ page import = "java.text.*" %>
<%@ page import = "sips.common.lib.util.*" %>
<%@ page import = "sips.common.lib.value.*" %>
<%@ page import = "sips.dept.contents.Binder" %>
<%@ page import = "sips.dept.contents.Session" %>
<%@ page import = "sips.dept.contents.Topic" %>
<%@ page import = "sips.dept.contents.Conference" %>
<%  String formName = "frmBoard"; %>
<%
	ParamValue       parmValue = new ParamValue();          
	Binder            binder     = new Binder();     
	Session            _session     = new Session();
	Topic            topic     = new Topic();
	Conference            conference     = new Conference();
    ResultSetValue   rset      = null;
    ResultSetValue   rset2      = null;
    ResultSetValue   rset3      = null;
    ResultSetValue   rset4      = null;
    
	String modeType           = StringUtil.defaultIfEmpty(request.getParameter("modeType"    ), "ins");
    String b_mode             = StringUtil.defaultIfEmpty(request.getParameter("b_mode"      ), ""); 
    String conference_id             = StringUtil.defaultIfEmpty(request.getParameter("conference_id"      ), ""); 
    String binder_id             = StringUtil.defaultIfEmpty(request.getParameter("binder_id"      ), ""); 
    System.out.println("conference_id--->"+conference_id);
    String user_cd         = "";
    String user_authority         = "";
    

    String admin_user_cd         = (String)session.getAttribute("USER_CD");
    String admin_authority         = (String)session.getAttribute("USE_AUTHORITY");
    
    String binder_title    = "";
    String contents    = "";
    String  reg_date    = "";
    String  attached    = "";
    String  user_name    = "";
    String  session_title    = "";
    String  topic_title    = "";
    String  conference_name    = "";
    String strMsg   = "수정";
    String agenda_register = "";
    String result = "비발표자";
    String sel_topic_title = "";
    String sel_topic_id = "";
    String sel_session_id = "";
    String sel_session_title = "";
    String sel_conference_name = "";
    String sel_conference_id = "";
    String session_id = "";
    String topic_id = "";
    String session_name = "";
    String topic_name = "";
    String user_id = "";
    String writer = "";
    
    parmValue.put("conference_id"    , conference_id  );
    parmValue.put("binder_id"    , binder_id  );
    		
    if( !"".equals(binder_id)) {
        rset = binder.getBniderInfo(parmValue);
        while( rset.next()){
        	binder_id    = StringUtil.defaultIfEmpty(rset.getString("binder_id"    ), "");
        	binder_title    = StringUtil.defaultIfEmpty(rset.getString("binder_title"    ), "");
        	contents    = StringUtil.defaultIfEmpty(rset.getString("contents"    ), "");
        	reg_date    = StringUtil.defaultIfEmpty(rset.getString("reg_date"    ), "");
        	attached    = StringUtil.defaultIfEmpty(rset.getString("attached"    ), "");
        	user_name    = StringUtil.defaultIfEmpty(rset.getString("user_name"    ), "");
        	writer    = StringUtil.defaultIfEmpty(rset.getString("writer"    ), "");
        	session_title    = StringUtil.defaultIfEmpty(rset.getString("session_title"    ), "");
        	topic_title    = StringUtil.defaultIfEmpty(rset.getString("topic_title"    ), "");
        	session_id    = StringUtil.defaultIfEmpty(rset.getString("session_id"    ), "");
        	topic_id    = StringUtil.defaultIfEmpty(rset.getString("topic_id"    ), "");
        	conference_name    = StringUtil.defaultIfEmpty(rset.getString("conference_name"    ), "");
        	user_id    = StringUtil.defaultIfEmpty(rset.getString("user_id"    ), "");
        	user_cd    = StringUtil.defaultIfEmpty(rset.getString("user_cd"    ), "");
        }

        result = binder.getAgendaRegister(parmValue);
        
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
    
 // 컨퍼런스 셀렉트 리스트
    //rset2 = conference.getConferenceSelectList2(parmValue);
 	parmValue.put("admin_user_cd"    , admin_user_cd  );
    parmValue.put("admin_authority"    , admin_authority  );
    rset2 = conference.getConferenceList2(parmValue);

    //세션 셀렉트 리스트
    rset3 = _session.getSessionSelectList(parmValue);
    //토픽 셀렉트 리스트
    rset4 = topic.getTopicSelectList(parmValue);
    
   // 
    
	
%>		
<script language="JavaScript" type="text/JavaScript">
<!--

	window.onload = function(){
		//openLayer('layerPop',170,400);
	};
	
	//레이어 팝업 열기
	function openLayer(IdName, tpos, lpos){
		var pop = document.getElementById(IdName);
		document.all['moveFrame'].src = './binder_user_popup.jsp?conference_id=<%=conference_id%>';
			pop.style.display = "block";
			pop.style.top = tpos + "px";
			pop.style.left = lpos + "px";
	}

	//레이어 팝업 닫기
	function closeLayer(IdName){
		var pop = document.getElementById(IdName);
		pop.style.display = "none";
	}
	
	function conference_change(obj)
	{
        var frm = document.<%=formName%>;
		var obj_value = conference_id.value;
		//alert(obj_value);
		frm.target="_self";
		frm.b_mode.value = "W";
		frm.action             = "./binder.jsp";
		frm.submit();
	}

    /**
     * 게시물 등록
     */
    function goRegitProc () {
        var frm = document.<%=formName%>;
		if (frm.conference_id.value == "0") {
            alert("컨퍼런스를 선택하세요.");
            frm.conference_id.focus();
            return;
        }
		if (frm.session_id.value == "0") {
            alert("세션을 선택하세요.");
            frm.session_id.focus();
            return;
        }
		if (frm.topic_id.value == "0") {
            alert("토픽을 선택하세요.");
            frm.topic_id.focus();
            return;
        }
		if (frm.lecturer_name.value == "") {
            alert("회원을 선택하세요.");
            return;
        }
		if (frm.binder_title.value == "") {
            alert("제목을 입력하세요.");
            frm.binder_title.focus();
            return;
        }
		if (frm.writer.value == "") {
            alert("저자를 입력하세요.");
            frm.writer.focus();
            return;
        }
		if (frm.contents.value == "" ) {
            alert("내용을 입력하세요.");
            frm.contents.focus();
            return;
	    }
		if (frm.attached.value == ""  && frm.hidden_attached.value == "" ) {
	        alert("자료를 첨부하세요.");
	        frm.attached.focus();
	        return;
	    }
	    
        if( confirm("<%=strMsg%> 하시겠습니까?")) {
            frm.action   = "binder_write_proc.jsp";
	        frm.modeType.value = "ins";
            frm.encoding = "multipart/form-data";
            frm.submit();
        }
    }
	function goModifyProc () { 
        var frm = document.<%=formName%>;
		if (frm.conference_id.value == "0") {
            alert("컨퍼런스를 선택하세요.");
            frm.conference_id.focus();
            return;
        }
		if (frm.session_id.value == "0") {
            alert("세션을 선택하세요.");
            frm.session_id.focus();
            return;
        }
		if (frm.topic_id.value == "0") {
            alert("토픽을 선택하세요.");
            frm.topic_id.focus();
            return;
        }
		if (frm.lecturer_name.value == "") {
            alert("회원을 선택하세요.");
            return;
        }
		if (frm.binder_title.value == "") {
            alert("제목을 입력하세요.");
            frm.binder_title.focus();
            return;
        }
		if (frm.writer.value == "") {
            alert("저자를 입력하세요.");
            frm.writer.focus();
            return;
        }
		if (frm.contents.value == "" ) {
            alert("내용을 입력하세요.");
            frm.contents.focus();
            return;
	    }
		if (frm.attached.value == ""  && frm.hidden_attached.value == "" ) {
	        alert("자료를 첨부하세요.");
	        frm.attached.focus();
	        return;
	    }
	    
    	if( confirm("수정 하시겠습니까?")) {
	        frm.modeType.value = "mod";
	        frm.action   = "binder_write_proc.jsp";
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
    function getSpace(cnt) {
        var spc = "";
        for (i = 0; i < cnt; i++) {
            spc += " ";
        }        
        return spc;
    }
    

    //이미지 확장자 체크
  	function file_check_img(uploadImgObj)
  	{
  		var enableUploadFileExt = ["pdf"];
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
    
  	function lecturer_find( ){
        var frm = document.<%=formName%>;
        if (frm.conference_id.value == "0") {
            alert("컨퍼런스를 선택후 아이디를 찾을수 있습니다.");
            frm.conference_id.focus();
            return;
        }
		if (frm.session_id.value == "0") {
            alert("세션을 선택후 아이디를 찾을수 있습니다.");
            frm.session_id.focus();
            return;
        }
		if (frm.topic_id.value == "0") {
            alert("토픽을 선택후 아이디를 찾을수 있습니다.");
            frm.topic_id.focus();
            return;
        }
  		var pop = document.getElementById('layerPop');
  		var conference_id = frm.conference_id.value;
  		document.all['moveFrame'].src = './binder_user_popup.jsp?conference_id='+conference_id;
 			pop.style.display = "block";
 			pop.style.top =  "170px";
 			pop.style.left = "400px";
  	}
	
	function onSetSelectInfo(key, value) {
        var frm = document.<%=formName%>;
		//validation
		for( var i = 0; i <= frm.elements.length - 1; i++ ){
 			if( frm.elements[i].name == "con_admin_user_cd" )
 			{
 				if(frm.elements[i].value == key ){
 					alert("이미 등록된 관리자입니다.");
 					return;
 				}
 			}
		}
		//값 넣기.
		for( var i = 0; i <= frm.elements.length - 1; i++ ){
 			if( frm.elements[i].name == "con_admin_user_cd" )
 			{
 				frm.elements[i].value = key;
 			}
 			
 			if( frm.elements[i].name == "lecturer_name" )
 			{
 				frm.elements[i].value = value;
 			}
			}
	}
	
	var oTbl;
	//Row 추가
	function insRow() {      
	    var frm = document.<%=formName%>;
			for( var i = 0; i <= frm.elements.length - 1; i++ ){
 			if( frm.elements[i].name == "lecturer_name" )
 			{
     			if( !frm.elements[i].value ){
         			alert("관리자를 선택해주세요.");
            		frm.elements[i].focus();
 					return;
      			}
 			}
			}
		
		oTbl = document.getElementById("addTable");
			var rowlength = oTbl.rows.length;
			var oRow = oTbl.insertRow(rowlength);
			oRow.onmouseover=function(){oTbl.clickedRowIndex=this.rowIndex;}; //clickedRowIndex - 클릭한 Row의 위치를 확인;
			var oCell = oRow.insertCell();

			var frmTag = "<input type=\"hidden\" name=\"con_admin_user_cd\" id=\"con_admin_user_cd\" readonly=\"readonly\">\n";
		frmTag += "<input type=\"text\" name=\"lecturer_name\" id=\"lecturer_name\" readonly=\"readonly\">\n";
		frmTag += "<input type=\"button\" value=\"찾아보기\" onclick=\"lecturer_find();\"/>\n";
		frmTag += "<input type=button value='삭제' onClick='removeRow()' style='cursor:hand'>";
			oCell.innerHTML = frmTag;
	}
	
	function modifyRow() {
		oTbl = document.getElementById("addTable");
			var rowlength = oTbl.rows.length;
			var oRow = oTbl.insertRow(rowlength);
			oRow.onmouseover=function(){oTbl.clickedRowIndex=this.rowIndex;}; //clickedRowIndex - 클릭한 Row의 위치를 확인;
			var oCell = oRow.insertCell();

			var frmTag = "<input type=\"hidden\" name=\"con_admin_user_cd\" id=\"con_admin_user_cd\" readonly=\"readonly\">\n";
		frmTag += "<input type=\"text\" name=\"lecturer_name\" id=\"lecturer_name\" readonly=\"readonly\">\n";
		frmTag += "<input type=\"button\" value=\"찾아보기\" onclick=\"lecturer_find();\"/>\n";
		frmTag += "<input type=button value='삭제' onClick='removeRow()' style='cursor:hand'>";
			oCell.innerHTML = frmTag;
		}
	
	function goDeleteForm () { 
        var frm = document.<%=formName%>;
        if( confirm("삭제 하시겠습니까?")) {
            frm.modeType.value = "del";
            frm.action   = "binder_write_proc.jsp";
	        frm.encoding = "multipart/form-data";
            frm.submit();
        }
                
    }    
  //-->
</script>
<form name="<%=formName%>" method="post">
<input type="hidden" name="binder_id" value="<%=binder_id %>">  
<input type="hidden" name="b_mode"       value="<%=b_mode      %>">
<input type="hidden" name="modeType"     value="<%=modeType    %>">
<input type="hidden" name="hidden_attached" value="<%=attached%>">

<table width="100%" border="0" cellspacing="0" cellpadding="0">
      
      <tr>
        <td valign="top"><table border="0" cellpadding="0" cellspacing="0" style="border-top:2px solid #e0e0e0; border-bottom:2px solid #e0e0e0; width:784px;">
            <tr>
              <td width="210" class="bbbg" style="padding-left:20px; font-weight:bold;">컨퍼런스</td>
              <td width="574" style="padding-left:20px;">
              		<select name=conference_id id=conference_id onchange="conference_change(this)">
              			<option value="0" >선택</option>
<%
    if(  rset2.row()==0) { %>
                
<%    
    } else {
        while( rset2.next()){
        	sel_conference_name     = StringUtil.defaultIfEmpty(rset2.getString("name" ), "");
        	sel_conference_id    = StringUtil.defaultIfEmpty(rset2.getString("conference_id"), "");
%>				
			<option value="<%=sel_conference_id%>" <%= sel_conference_id.equals(conference_id) ? "selected" : "" %> ><%=sel_conference_name%></option>
			
<%      } 
    }
    %>           
          		</select>
              </td>
            </tr>
            <tr>
              <td width="210" class="bbbg" style="padding-left:20px; font-weight:bold;">세션 제목</td>
              <td width="574" style="padding-left:20px;">
              		<select name=session_id id=session_id>
              			<option value="0" >선택</option>
<%
    if(  rset3.row()==0) { %>
                
<%    
    } else {
        while( rset3.next()){
        	sel_session_title     = StringUtil.defaultIfEmpty(rset3.getString("title" ), "");
        	sel_session_id    = StringUtil.defaultIfEmpty(rset3.getString("session_id"), "");
%>				
			<option value="<%=sel_session_id%>" <%= sel_session_id.equals(session_id) ? "selected" : "" %> ><%=sel_session_title%></option>
			
<%      } 
    }
    %>           
          		</select>
              </td>
            </tr>
            <tr>
              <td width="210" class="bbbg" style="padding-left:20px; font-weight:bold;">토픽 제목</td>
              <td width="574" style="padding-left:20px;">
              		<select name=topic_id id=topic_id>
              			<option value="0" >선택</option>
<%
    if(  rset4.row()==0) { %>
                
<%    
    } else {
        while( rset4.next()){
        	sel_topic_title     = StringUtil.defaultIfEmpty(rset4.getString("title" ), "");
        	sel_topic_id    = StringUtil.defaultIfEmpty(rset4.getString("topic_id"), "");
%>				
			<option value="<%=sel_topic_id%>" <%= sel_topic_id.equals(topic_id) ? "selected" : "" %> ><%=sel_topic_title%></option>
			
<%      } 
    }
    %>           
          		</select>
              </td>
            </tr>
            <tr>
              <td width="210" class="bbbg" style="padding-left:20px; font-weight:bold;">회원명(ID)</td>
              <td width="574" style="padding-left:20px;">
							<input type="hidden" name="con_admin_user_cd" id="con_admin_user_cd" readonly="readonly" value="<%=user_cd %>">
							<input type="text" name="lecturer_name" id="lecturer_name" readonly="readonly" value="<%=user_name %>">
							<input type="button" value="찾아보기" onclick="lecturer_find();"/>
              </td>
            </tr>
            <!-- tr>
              <td class="bbbg" style="padding-left:20px; font-weight:bold;">지정된 발표자 여부</td>
              <td style="padding-left:20px;"><%=result%></td>
            </tr -->
            <tr>
              <td width="210" class="bbbg" style="padding-left:20px; font-weight:bold;">제목</td>
              <td width="574" style="padding-left:20px;"><input type="text" name="binder_title" value="<%=binder_title%>"></td>
            </tr>
            <tr>
              <td width="210" class="bbbg" style="padding-left:20px; font-weight:bold;">저자</td>
              <td width="574" style="padding-left:20px;"><input type="text" name="writer" value="<%=writer%>"></td>
            </tr>
            <tr>
              <td width="210" class="bbbg" style="padding-left:20px; font-weight:bold;">내용</td>
              <td width="574" style="padding-left:20px;">
					<textarea name="contents" rows="5" cols="60"><%=contents %></textarea>
			  </td>
            </tr>
            <tr>
              <td width="210" class="bbbg" style="padding-left:20px; font-weight:bold;">다운로드</td>
              <td width="574" style="padding-left:20px;">
              <%
              	if (!"".equals(attached)){
              		out.println("<a href=/mice/upload/pdf/"+attached+">"+attached+"</a><BR>");
              	}
              %>
              <input name="attached" type="file" value="" class="Ttable" size="35" onchange="file_check_img(this)">
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