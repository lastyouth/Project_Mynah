<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<% request.setCharacterEncoding("UTF-8"); %>
<%
	String root_path = request.getContextPath();
%>
<%@ page import = "sips.common.lib.util.*" %>
<%@ page import = "sips.common.lib.value.*" %>
<%@ page import = "sips.dept.contents.Binder" %>
<%@ page import = "sips.dept.contents.Session" %>
<%@ page import = "sips.dept.contents.Topic" %>
<%  String formName = "frmBoard"; %>
<%
	ParamValue       parmValue = new ParamValue();          
	Binder        binder     = new Binder();
	Session     _session     = new Session();
	Topic         topic     = new Topic();
	ResultSetValue   rset      = null;
    ResultSetValue   rset3      = null;
    ResultSetValue   rset4      = null;
	
	
	String conference_id       = StringUtil.defaultIfEmpty(request.getParameter("conference_id"), "");
	String searchTarget       = StringUtil.defaultIfEmpty(request.getParameter("searchTarget"), "");  // 검색 조건 (제목/내용)
    String keyword            = StringUtil.defaultIfEmpty(request.getParameter("keyword"     ), "");  // 검색할 키워드
    String session_id            = StringUtil.defaultIfEmpty(request.getParameter("session_id"     ), "");
    String topic_id            = StringUtil.defaultIfEmpty(request.getParameter("topic_id"     ), "");
    
	String binder_id  = "";
    String title = "";
    String user_name    = "";
    String reg_date = "";
    String writer = "";
    String sel_topic_title = "";
    String sel_topic_id = "";
    String sel_session_id = "";
    String sel_session_title = "";
    String  session_title    = "";
    String  topic_title    = "";
    String contents = "";
	
    parmValue.put("conference_id"    , conference_id  );
    parmValue.put("searchTarget"        , searchTarget  );
    parmValue.put("keyword"             , keyword       );
    parmValue.put("session_id"             , session_id       );
    parmValue.put("topic_id"             , topic_id       );
    
	//if (!"".equals(keyword))
   	rset = binder.getFindBinder(parmValue);

   	//세션 셀렉트 리스트
    rset3 = _session.getSessionSelectList(parmValue);
    //토픽 셀렉트 리스트
    rset4 = topic.getTopicSelectList(parmValue);

%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>::MICE::</title>
<link rel="stylesheet" type="text/css" href="/mice/css/Layout.css"/>

<script language="JavaScript" src="/mice/js/common.js"></script>
<script language="JavaScript" type="text/JavaScript">
<!--
//레이어 팝업 닫기
	function closeLayer(IdName){
		var pop = parent.document.getElementById(IdName);
		pop.style.display = "none";
	}
	
function goRegitProc () {
    var frm = document.<%=formName%>;
    

    if (frm.admin_user_cd.value == "") {
        alert("바인더 등록할 아이디를 입력하세요.");
        frm.admin_user_cd.focus();
        return;
    }

    if( confirm("선택 아이디의 바인더를 계속 등록 합니다..")) {
        frm.action   = "binder_user_popup.jsp";
        frm.target="_parent";
        frm.b_mode.value = "R";
        frm.modeType.value = "popIns";
        frm.submit();
        //parent.location.reload();
        closeLayer('layerPop');
    }
}

function goNoRegitProc (cnt, tot){

	if (tot == 1){
		parent.document.frmBoard.binder_id.value = document.frmBoard.binder_id.value;
		parent.document.frmBoard.binder_writer.value = document.frmBoard.writer.value;
		parent.document.frmBoard.binder_title.value = document.frmBoard.title.value;
		parent.document.frmBoard.binder_contents.value = document.frmBoard.contents.value;
		parent.document.frmBoard.binder_user_name.value = document.frmBoard.user_name.value;
	}else{
		parent.document.frmBoard.binder_id.value = document.frmBoard.binder_id[cnt].value;
		parent.document.frmBoard.binder_writer.value = document.frmBoard.writer[cnt].value;
		parent.document.frmBoard.binder_title.value = document.frmBoard.title[cnt].value;
		parent.document.frmBoard.binder_contents.value = document.frmBoard.contents[cnt].value;
		parent.document.frmBoard.binder_user_name.value = document.frmBoard.user_name[cnt].value;
	}
	closeLayer('layerPop');

}

function goSearch() {
	frmBoard.target="_self";
	frmBoard.action             = "./find_binder_popup.jsp";
	frmBoard.submit();

}

function goDisplay(user_cd, user_name,user_id){
    var frm = document.<%=formName%>;
	frm.user_cd.value = user_cd;
	frm.user_name.value = user_name;	
	frm.user_id.value = user_id;	
}
  //-->
</script>
</head>
<body>
<form name="<%=formName%>" method="post">
<input type="hidden" name="b_mode"/>
<input type="hidden" name="modeType"/>
<input type="hidden" name="conference_id" value="<%=conference_id %>" />
<div id="main-wrapper1">
    <div id="main-wrapper">
    <div id="contents">
    <div id="layerPopOpen">
                
                <div class="box4">
                	<h3>바인더 등록 팝업</h3>          
                </div>     
                <!--검색 -->  
                <div class="box33">
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
          		<br>
                	<select name="searchTarget">
			            <option value="1" <%= "1".equals(searchTarget) ? "selected" : "" %> > 이름</option>
			            <option value="2" <%= "2".equals(searchTarget) ? "selected" : "" %> >제목</option>
			          </select>
			            <input name="keyword" type="text" value="<%= keyword %>" onKeypress="if (event.keyCode==13) goSearch();" class="input_box" size="20">
			            <a href="javascript:goSearch();"><img src="<%=root_path%>/images/bt_search.gif" alt="검색" class="sbtn1"/></a>
                             
                </div>
                <!--스크롤 테이블 -->               
                <div class="scroll2">
                    <!--테이블 -->
                    <table width="100%" style="border:1px solid #d3d3d3; width:100%;">
                  <thead>
                    <th style="padding-top:5px;"><div align="center">토픽</div></th>
                    <th style="padding-top:5px;"><div align="center">제목</div></th>
                    <th style="padding-top:5px;"><div align="center">이름</div></th>
                    <th style="padding-top:5px;"><div align="center">등록일</div></th>
              	  </thead> 
                  <tbody>
					<%
	if (rset.row()==0){ %>
                <tr><td colspan=7 align="center" height=50>:::: 검색 ::::</td></tr>  
<%    
    } else {
    	int i = 0;
    	int tot = rset.row();
        while( rset.next()){
        	binder_id     = StringUtil.defaultIfEmpty(rset.getString("binder_id" ), "");
        	title    = StringUtil.defaultIfEmpty(rset.getString("title"), "");
        	user_name    = StringUtil.defaultIfEmpty(rset.getString("user_name"), "");
        	reg_date          = StringUtil.defaultIfEmpty(rset.getString("reg_date"         ), "");
        	writer          = StringUtil.defaultIfEmpty(rset.getString("writer"         ), "");
        	contents          = StringUtil.defaultIfEmpty(rset.getString("contents"         ), "");
        	topic_title          = StringUtil.defaultIfEmpty(rset.getString("topic_title"         ), "");
%>				
			<tr>
				<td align="center">
				<a href="javascript:goNoRegitProc('<%=i %>', '<%=tot %>');" class="skin"><%=topic_title%></a>
				</td>
				<td align="center">
				<a href="javascript:goNoRegitProc('<%=i %>', '<%=tot %>');" class="skin"><%=title%></a>
				</td>
				<td align="center"><a href="javascript:goNoRegitProc('<%=i %>', '<%=tot %>');" class="skin"><%=user_name%></a></td>
				<td align="center"><a href="javascript:goNoRegitProc('<%=i %>', '<%=tot %>');" class="skin"><%=reg_date%></a></td>
				<input type="hidden" name=contents" id="contents" value="<%=contents%>">
				<input type="hidden" name=user_name" id="user_name" value="<%=user_name%>">
				<input type="hidden" name=binder_id" id="binder_id" value="<%=binder_id%>">
				<input type="hidden" name=title" id="title" value="<%=title%>">
				<input type="hidden" name=writer" id="writer" value="<%=writer%>">
			</tr>
<%      
			i++;	
        } 
    }
    %> 
                  </tbody>
                </table>
                    <!--//테이블 -->
                </div>          
                <div class="box4">
					<span style="align:left"> * 선택할 바인더의 제목을 클릭하세요</span><span class="close"><a href="#" onclick="closeLayer('layerPop')" class="close">닫기</a></span>
                </div>     
                <!--//검색 -->   
		 </div>
		 </div>
		 </div>
	</div>
</form>	
</body>
</html>                