<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<% request.setCharacterEncoding("UTF-8"); %>
<%
	String root_path = request.getContextPath();
%>
<%@ page import = "java.util.*" %>
<%@ page import = "java.text.*" %>
<%@ page import = "sips.common.lib.util.*" %>
<%@ page import = "sips.common.lib.value.*" %>
<%@ page import = "sips.dept.contents.Agenda" %>
<%@ page import = "sips.dept.contents.Conference" %>
<%  String formName = "frmBoard"; %>
<%
	ParamValue       parmValue = new ParamValue();          
	Agenda            agenda     = new Agenda();     
	Conference            conference     = new Conference();
    ResultSetValue   rset      = null;
    ResultSetValue   rset2      = null;
    ResultSetValue   rset3      = null;
    ResultSetValue   rset4      = null;
    ResultSetValue   rset5      = null;
    
	String modeType           = StringUtil.defaultIfEmpty(request.getParameter("modeType"    ), "ins");
    String b_mode             = StringUtil.defaultIfEmpty(request.getParameter("b_mode"      ), ""); 
    String conference_id             = StringUtil.defaultIfEmpty(request.getParameter("conference_id"      ), ""); 
    String agenda_id             = StringUtil.defaultIfEmpty(request.getParameter("agenda_id"      ), ""); 
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
    String binder_id = "";
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
    String  start_time    = "";
    String  end_time    = "";
    String conference_start_date = "";
    String conference_end_date = "";
    String conference_date = "";
    String presenter = "";
    String summary = "";
    String binder_contents = "";
    
    
    parmValue.put("conference_id"    , conference_id  );
    parmValue.put("agenda_id"    , agenda_id  );
    		
    if( !"".equals(agenda_id)) {
        rset = agenda.getAgendaInfo(parmValue);
        while( rset.next()){
        	conference_date    = StringUtil.defaultIfEmpty(rset.getString("conference_date"    ), "");
        	presenter    = StringUtil.defaultIfEmpty(rset.getString("presenter"    ), "");
        	summary    = StringUtil.defaultIfEmpty(rset.getString("summary"    ), "");
        	start_time    = StringUtil.defaultIfEmpty(rset.getString("start_time"    ), "");
        	end_time    = StringUtil.defaultIfEmpty(rset.getString("end_time"    ), "");
        	user_id    = StringUtil.defaultIfEmpty(rset.getString("user_id"    ), "");
        	reg_date    = StringUtil.defaultIfEmpty(rset.getString("reg_date"    ), "");
        	user_name    = StringUtil.defaultIfEmpty(rset.getString("user_name"    ), "");
        	session_title    = StringUtil.defaultIfEmpty(rset.getString("session_title"    ), "");
        	topic_title    = StringUtil.defaultIfEmpty(rset.getString("topic_title"    ), "");
        	conference_name    = StringUtil.defaultIfEmpty(rset.getString("conference_name"    ), "");
        	writer    = StringUtil.defaultIfEmpty(rset.getString("writer"    ), "");
        	binder_title    = StringUtil.defaultIfEmpty(rset.getString("binder_title"    ), "");
        	binder_contents    = StringUtil.defaultIfEmpty(rset.getString("binder_contents"    ), "");
        }

        //result = binder.getAgendaRegister(parmValue);
        
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
    
   // 
    rset5 = conference.getConferenceInfo(parmValue);
    while( rset5.next()){
    	conference_name    = StringUtil.defaultIfEmpty(rset5.getString("name"    ), "");
    	conference_start_date    = StringUtil.defaultIfEmpty(rset5.getString("start_date"    ), "");
    	conference_end_date    = StringUtil.defaultIfEmpty(rset5.getString("end_date"    ), "");
    }
	
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
		//alert(obj_value);
		frm.target="_self";
		frm.b_mode.value = "W";
		frm.action             = "./agenda.jsp";
		frm.submit();
	}

    /**
     * 게시물 등록
     */
    function goRegitProc () {
        var frm = document.<%=formName%>;
        var frm = document.<%=formName%>;
		if (frm.conference_id.value == "0") {
            alert("컨퍼런스를 선택하세요.");
            frm.conference_id.focus();
            return;
        }
		if (frm.start_hour.value == "" || frm.start_min.value == "" ) {
            alert("시작 시간을 입력하세요.");
            frm.start_hour.focus();
            return;
	    }
		if (frm.start_hour.value > 24 || frm.start_min.value > 59 ) {
            alert("시간 범위가 안맞습니다.");
            frm.start_hour.focus();
            return;
	    }
		if (frm.end_hour.value == "" || frm.end_min.value == "" ) {
	        alert("종료 시간을 입력하세요.");
	        frm.end_hour.focus();
	        return;
	    }
		if (frm.end_hour.value > 24 || frm.end_min.value > 59 ) {
            alert("시간 범위가 안맞습니다.");
            frm.end_hour.focus();
            return;
	    }
		if (frm.start_hour.value+frm.start_min.value >= frm.end_hour.value+frm.end_min.value ) {
            alert("시간 범위가 안맞습니다.");
            frm.end_hour.focus();
            return;
	    }
		if (frm.presenter.value == "") {
            alert("저자를 입력하세요.");
            frm.presenter.focus();
            return;
        }
		if (frm.summary.value == "") {
            alert("써머리를 입력하세요.");
            frm.summary.focus();
            return;
        }
		
		frm.start_time.value = frm.start_hour.value+frm.start_min.value;
        frm.end_time.value = frm.end_hour.value+frm.end_min.value;
        

		if (frm.start_time.value > frm.end_time.value) {
            alert("컨퍼런스 발표 시간이 잘못되었습니다.");
            frm.start_time.focus();
            return;
        }
	    
        if( confirm("<%=strMsg%> 하시겠습니까?")) {
        	
            frm.action   = "agenda_write_proc.jsp";
	        frm.modeType.value = "ins";
           // frm.encoding = "multipart/form-data";
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
		if (frm.start_hour.value == "" || frm.start_min.value == "" ) {
            alert("시작 시간을 입력하세요.");
            frm.start_hour.focus();
            return;
	    }
		if (frm.end_hour.value == "" || frm.end_min.value == "" ) {
	        alert("종료 시간을 입력하세요.");
	        frm.end_hour.focus();
	        return;
	    }
		if (frm.presenter.value == "") {
            alert("저자를 입력하세요.");
            frm.presenter.focus();
            return;
        }
		if (frm.summary.value == "") {
            alert("내용 및 요약을 입력하세요.");
            frm.summary.focus();
            return;
        }
		
		frm.start_time.value = frm.start_hour.value+frm.start_min.value;
        frm.end_time.value = frm.end_hour.value+frm.end_min.value;
        

		if (frm.start_time.value > frm.end_time.value) {
            alert("컨퍼런스 발표 시간이 잘못되었습니다.");
            frm.start_time.focus();
            return;
        }
	    
    	if( confirm("수정 하시겠습니까?")) {
	        frm.modeType.value = "mod";
	        frm.action   = "agenda_write_proc.jsp";
	       // frm.encoding = "multipart/form-data";
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
    
  	function binder_find( ){
        var frm = document.<%=formName%>;
        if (frm.conference_id.value == "0") {
            alert("컨퍼런스를 선택후 아이디를 찾을수 있습니다.");
            frm.conference_id.focus();
            return;
        }
  		var pop = document.getElementById('layerPop');
  		var conference_id = frm.conference_id.value;
  		document.all['moveFrame'].src = './find_binder_popup.jsp?conference_id='+conference_id;
 			pop.style.display = "block";
 			pop.style.top =  "50px";
 			pop.style.left = "200px";
  	}
	
	function onSetSelectInfo(key, value, writer) {
        var frm = document.<%=formName%>;
		//값 넣기.
		for( var i = 0; i <= frm.elements.length - 1; i++ ){
 			if( frm.elements[i].name == "binder_id" )
 			{
 				frm.elements[i].value = key;
 			}
 			
 			if( frm.elements[i].name == "binder_title" )
 			{
 				frm.elements[i].value = value;
 			}
 			
 			if( frm.elements[i].name == "binder_writer" )
 			{
 				frm.elements[i].value = writer;
 			}
		}
	}
	
	/**
     * 게시물 삭제
     */
    function goDeleteForm () { 
        var frm = document.<%=formName%>;
        if( confirm("삭제 하시겠습니까?")) {
            frm.modeType.value = "del";
            frm.action   = "agenda_write_proc.jsp";
	       // frm.encoding = "multipart/form-data";
            frm.submit();
        }
                
    }    
	
	function binder_check1(n){
        var frm = document.<%=formName%>;
		if (n) {
			frm.presenter.value = frm.binder_user_name.value;
		}else{
			frm.presenter.value = "";
		}
	}
	
	function binder_check2(n){
        var frm = document.<%=formName%>;
		if (n) {
			frm.summary.value = frm.binder_contents.value;
		}else{
			frm.summary.value = "";
		}
	}
  //-->
</script>
<form name="<%=formName%>" method="post">
<input type="hidden" name="agenda_id" value="<%=agenda_id %>">  
<input type="hidden" name="b_mode"       value="<%=b_mode      %>">
<input type="hidden" name="modeType"     value="<%=modeType    %>">
<input type="hidden" name="start_time" >
<input type="hidden" name="end_time" >
<input type="hidden" name="binder_user_name" value="<%=writer    %>">
<input type="hidden" name="binder_contents"  value="<%=binder_contents    %>" >

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
              <td width="210" class="bbbg" style="padding-left:20px; font-weight:bold;">바인더</td>
              <td width="574" style="padding-left:20px;">
              		<input type="button" value="바인더찾기" onclick="binder_find();"/><br>
              		<input type="hidden" name="binder_id" id="binder_id" readonly="readonly" value="<%=binder_id %>">
					저자 : <input type="text" name="binder_writer" id="binder_writer" readonly="readonly" value="<%=writer %>"><br>
					제목 : <input type="text" name="binder_title" id="binder_title" readonly="readonly" value="<%=binder_title %>" size="70">
							
              </td>
            </tr>
            <tr>
              <td width="210" class="bbbg" style="padding-left:20px; font-weight:bold;">컨퍼런스 발표일</td>
              <td width="574" style="padding-left:20px;">
              	<select name="conference_date">
              	<%
              	String select_start_date = "";
              	String select_end_date = "";
              	if ("".equals(conference_start_date))	conference_start_date = "0000-00-00";
              	if ("".equals(conference_end_date))	conference_end_date = "0000-00-00";
              	java.text.SimpleDateFormat format = new java.text.SimpleDateFormat("yyyy-MM-dd");
              	java.util.Date start_date = format.parse(conference_start_date);
              	java.util.Date end_date = format.parse(conference_end_date);
              	Calendar cal = Calendar.getInstance();
              	Calendar cal2 = Calendar.getInstance();
              	cal.setTime(end_date );//종료일을 calendar 변수에 세팅
              	cal2.setTime(start_date );//시작일을 calendar 변수에 세팅
              //종료일 - 시작일 = 총 걸리는 시간
              	long diffMillis = cal.getTimeInMillis() - cal2.getTimeInMillis();
              	int totalDate = Integer.parseInt(Long.toString(diffMillis))/86400000; //(하루는 86400000  밀리second)
              	System.out.println( totalDate+ " : " + diffMillis);

              	String selected = "";
              	out.println("<option value="+conference_start_date+"  "+selected+">"+conference_start_date+"</option>"); 
              	for( int i=0; i <  totalDate ; i++){
              		cal2.add( Calendar.DATE,1); 
                  	Date date_2 = cal2.getTime();
                  	select_start_date =  cal2.get(Calendar.YEAR)+"-" + (cal2.get(Calendar.MONTH)+1)+"-" + cal2.get(Calendar.DATE);
                  	
                  	if (conference_date.equals(rset2.getString("select_start_date"))){
                  		selected = "selected";
                  	}else{
                  		selected = "";
                  	}
              		out.println("<option value="+select_start_date+"  "+selected+">"+select_start_date+"</option>");            	    
              	}
              	%>
              	</select>
              </td>
            </tr>
            <tr>
              <td width="210" class="bbbg" style="padding-left:20px; font-weight:bold;">컨퍼런스 발표시간</td>
              <td width="574" style="padding-left:20px;">
				<input type="text" name="start_hour" value="<%= !"".equals(start_time)?start_time.substring(0,2):""%>" size="1" maxlength=2> :
              	<input type="text" name="start_min" value="<%= !"".equals(start_time)?start_time.substring(2,4):""%>" size="1" maxlength=2>
              	~
              	<input type="text" name="end_hour" value="<%= !"".equals(end_time)?end_time.substring(0,2):""%>" size="1" maxlength=2> : 
              	<input type="text" name="end_min" value="<%= !"".equals(end_time)?end_time.substring(2,4):""%>" size="1" maxlength=2>
			</td>
            </tr>
            <tr>
              <td width="210" class="bbbg" style="padding-left:20px; font-weight:bold;">발표자</td>
              <td width="574" style="padding-left:20px;"><input type="text" name="presenter" value="<%=presenter%>">
              	<input type="checkbox" name="user_name_same" vaule="1" onClick="binder_check1(this.checked)"> Binder 상동
              </td>
            </tr>
            <tr>
              <td width="210" class="bbbg" style="padding-left:20px; font-weight:bold;">내용 및 요약</td>
              <td width="574" style="padding-left:20px;">
					<textarea name="summary" rows="5" cols="60"><%=summary %></textarea>
					<input type="checkbox" name="contents_same" vaule="1" onClick="binder_check2(this.checked)"> Binder 상동
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