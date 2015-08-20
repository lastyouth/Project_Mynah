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
    ResultSetValue   rset5      = null;
    
	String modeType           = StringUtil.defaultIfEmpty(request.getParameter("modeType"    ), "ins");
    String b_mode             = StringUtil.defaultIfEmpty(request.getParameter("b_mode"      ), ""); 
    String conference_id             = StringUtil.defaultIfEmpty(request.getParameter("conference_id"      ), ""); 
    System.out.println("conference_id--->"+conference_id);
    String user_cd         = (String)session.getAttribute("USER_CD");
    String user_authority         = "";
    
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
    String binder_id = "";

    parmValue.put("conference_id"    , conference_id  );
    parmValue.put("user_cd"    , user_cd  );
    rset5 = binder.getBniderInfo2(parmValue);
    if ( rset5.next()){
    	binder_id    = StringUtil.defaultIfEmpty(rset5.getString("binder_id"    ), "");
    }
    
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
    rset2 = conference.getConferenceList3();

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
<input type="hidden" name="con_admin_user_cd" id="con_admin_user_cd" value="<%=user_cd %>">

<table width="100%" border="0" cellspacing="0" cellpadding="0">
      
      <tr>
        <td valign="top"><table border="0" cellpadding="0" cellspacing="0" style="border-top:2px solid #e0e0e0; border-bottom:2px solid #e0e0e0; width:784px;">
            <tr>
              <td width="210" class="bbbg" style="padding-left:20px; font-weight:bold;">컨퍼런스</td>
              <td width="574" style="padding-left:20px;">
<%    
        while( rset2.next()){
        	sel_conference_name     = StringUtil.defaultIfEmpty(rset2.getString("name" ), "");
        	sel_conference_id    = StringUtil.defaultIfEmpty(rset2.getString("conference_id"), "");
%>			
			<%= sel_conference_id.equals(conference_id) ? sel_conference_name : "" %>	
<%      } 
%>           
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
              <input name="attached" type="file" onchange="file_check_img(this)">
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
         </td>
      </tr>
    </table>
    
</form>		

<!-- 팝업 -->
<div  id="layerPop" >
 <iframe id="moveFrame" frameborder='0' height='100%' width='100%' scrolling='no' src=''></iframe>
</div>
<!--// 팝업 -->