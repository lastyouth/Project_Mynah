<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<% request.setCharacterEncoding("UTF-8"); %>
<%
	String root_path = request.getContextPath();
%>
<%@ page import = "sips.common.lib.util.*" %>
<%@ page import = "sips.common.lib.value.*" %>
<%@ page import = "sips.dept.contents.Member" %>
<%  String formName = "frmBoard"; %>
<%
	ParamValue       parmValue = new ParamValue();          
	Member        member     = new Member();
	ResultSetValue   rset      = null;
	
	
	String conference_id       = StringUtil.defaultIfEmpty(request.getParameter("conference_id"), "");
	String searchTarget       = StringUtil.defaultIfEmpty(request.getParameter("searchTarget"), "");  // 검색 조건 (제목/내용)
    String keyword            = StringUtil.defaultIfEmpty(request.getParameter("keyword"     ), "");  // 검색할 키워드
    
	String name  = "";
    String user_cd = "";
    String phone    = "";
    String user_id = "";
    
    parmValue.put("conference_id"    , conference_id  );
    parmValue.put("searchTarget"        , searchTarget  );
    parmValue.put("keyword"             , keyword       );
    
	//if (!"".equals(keyword))
    	rset = member.getConferenceAdminSearch2(parmValue);

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

function goNoRegitProc (){
    var frm = document.<%=formName%>;
	var user_cd = frm.user_cd.value;
	var user_name = frm.user_name.value;
	parent.onSetSelectInfo(user_cd,user_name);
	closeLayer('layerPop');

}

function goSearch() {
	frmBoard.target="_self";
	frmBoard.action             = "./binder_user_popup.jsp";
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
                <div class="box3">
                	<select name="searchTarget">
			            <option value="1" <%= "1".equals(searchTarget) ? "selected" : "" %> >이름</option>
			            <option value="2" <%= "2".equals(searchTarget) ? "selected" : "" %> >아이디</option>
			          </select>
			            <input name="keyword" type="text" value="<%= keyword %>" onKeypress="if (event.keyCode==13) goSearch();" class="input_box" size="20">
			            <a href="javascript:goSearch();"><img src="<%=root_path%>/images/bt_search.gif" alt="검색" class="sbtn1"/></a>
                             
                </div>
                <!--스크롤 테이블 -->               
                <div class="scroll2">
                    <!--테이블 -->
                    <table width="100%" style="border:1px solid #d3d3d3; width:100%;">
                  <thead>
                    <th style="padding-top:5px;"><div align="center">아이디</div></th>
                    <th style="padding-top:5px;"><div align="center">이름</div></th>
                    <th style="padding-top:5px;"><div align="center">전화번호</div></th>
              	  </thead> 
                  <tbody>
					<%
	if (rset.row()==0){ %>
                <tr><td colspan=7 align="center" height=50>:::: 검색 ::::</td></tr>  
<%    
    } else {
        while( rset.next()){
        	name     = StringUtil.defaultIfEmpty(rset.getString("name" ), "");
        	user_cd    = StringUtil.defaultIfEmpty(rset.getString("user_cd"), "");
        	user_id    = StringUtil.defaultIfEmpty(rset.getString("id"), "");
        	phone          = StringUtil.defaultIfEmpty(rset.getString("phone"         ), "");
%>				
			<tr>
				<td align="center">
				<a href="javascript:goDisplay('<%=user_cd%>','<%=name%>','<%=user_id%>');" class="skin"><%=user_id%></a>
				</td>
				<td align="center"><%=name%></td>
				<td align="center"><%=phone%></td>
			</tr>
<%      } 
    }
    %> 
                  </tbody>
                </table>
                    <!--//테이블 -->
                </div>     
                <div class="box3"><input type="hidden" name="user_name"><input type="hidden" name="user_cd">
					아이디 <input type="text" name="user_id"><a href="javascript:goNoRegitProc();"> <img src="<%=root_path%>/images/bt_up.gif" alt="등록" style="position:relative;top:5px;"/></a>               
                </div>            
                <div class="box4">
					<span class="close"><a href="#" onclick="closeLayer('layerPop')" class="close">닫기</a></span>
                </div>     
                <!--//검색 -->   
		 </div>
		 </div>
		 </div>
	</div>
</form>	
</body>
</html>                