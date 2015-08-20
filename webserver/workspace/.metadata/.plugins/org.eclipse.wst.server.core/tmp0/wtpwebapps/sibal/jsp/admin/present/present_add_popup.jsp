<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<% request.setCharacterEncoding("UTF-8"); %>
<%
	String root_path = request.getContextPath();
%>
<%@ page import = "sips.common.lib.util.*" %>
<%@ page import = "sips.common.lib.value.*" %>
<%@ page import = "sips.dept.contents.Present" %>
<%  String formName = "frmBoard"; %>
<%
	ParamValue       parmValue = new ParamValue();     
Present            present     = new Present();
	ResultSetValue   rset      = null;
	

	String conference_id       = StringUtil.defaultIfEmpty(request.getParameter("conference_id"), "");
	String present_id       = StringUtil.defaultIfEmpty(request.getParameter("present_id"), "");
	String searchTarget       = StringUtil.defaultIfEmpty(request.getParameter("searchTarget"), "");  // 검색 조건 (제목/내용)
    String keyword            = StringUtil.defaultIfEmpty(request.getParameter("keyword"     ), "");  // 검색할 키워드
	
	String gift_user_cd     = "";
	String gift_user_id       = "";
	String gift_user_name        = "";
	String gift_user_phone       = "";
	String gift_used_yn           = "";
	String check_used_yn        = "";

    parmValue.put("conference_id"    , conference_id  );
    parmValue.put("present_id"    , present_id  );
    parmValue.put("searchTarget"        , searchTarget  );
    parmValue.put("keyword"             , keyword       );
    
	//if (!"".equals(keyword))
    	rset = present.presentMappingList(parmValue);

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
    var objs = frm.boxes;
    var isChk = false;
	 for (var i=0; i<objs.length; i++){
     	if(objs[i].checked)
     		isChk = true;
     }
//     if (!isChk) {
//    	 alert("선물 지급한 아이디를 리스트 한개 이상 선택하세요.");
//         return;
//     }
     
     if( confirm("선택하신 리스트의 아이디에게 선물을 지급하시겠습니까?")) {
         frm.action   = "present_popup_proc.jsp";
         frm.target="_parent";
         frm.b_mode.value = "R";
         frm.modeType.value = "popIns";
         frm.submit();
         //parent.location.reload();
         closeLayer('layerPop');
     }
}

function goSearch() {
	frmBoard.target="_self";
	frmBoard.action             = "./present_add_popup.jsp";
	frmBoard.submit();

}
  //-->
</script>
</head>
<body>
<form name="<%=formName%>" method="post">
<input type="hidden" name="b_mode"/>
<input type="hidden" name="modeType"/>
<input type="hidden" name="conference_id" value="<%=conference_id %>" />
<input type="hidden" name="present_id" value="<%=present_id %>" />
<div id="main-wrapper1">
    <div id="main-wrapper">
    <div id="contents">
    <div id="layerPopOpen">
                
                <div class="box4">
                	<h3>컨퍼런스 관리자 등록 팝업</h3>          
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
                    <th>
                        <div align="center"><input type="checkbox" id="allbox" onClick="allBox('allbox','boxes');"></div>
                    </th>
                    <th style="padding-top:5px;"><div align="center">이름</div></th>
                    <th style="padding-top:5px;"><div align="center">아이디</div></th>
                    <th style="padding-top:5px;"><div align="center">전화번호</div></th>
                    <th style="padding-top:5px;"><div align="center">사용여부</div></th>
              	  </thead> 
                  <tbody>
					<%
	if (rset.row()==0){ %>
                <tr><td colspan=7 align="center" height=50>:::: 검색 ::::</td></tr>  
<%    
    } else {
        while( rset.next()){
        	gift_user_cd     = StringUtil.defaultIfEmpty(rset.getString("gift_user_cd" ), "");
        	gift_user_id    = StringUtil.defaultIfEmpty(rset.getString("gift_user_id"), "");
        	gift_user_name          = StringUtil.defaultIfEmpty(rset.getString("gift_user_name"         ), "");
        	gift_user_phone          = StringUtil.defaultIfEmpty(rset.getString("gift_user_phone"         ), "");
        	gift_used_yn          = StringUtil.defaultIfEmpty(rset.getString("gift_used_yn"         ), "");
        	check_used_yn          = StringUtil.defaultIfEmpty(rset.getString("check_used_yn"         ), "");
%>				
			<tr>
				<td><div align="center">
			<% if ("사용".equals(gift_used_yn)) { %>
				
			<% } else { %>
				<input type="checkbox" name="boxes" value="<%=gift_user_cd%>" <%=("yes".equals(check_used_yn)?"checked":"") %>>
			<% } %>
				</div></td>
				<td align="center"><%=gift_user_name%></td>
				<td align="center"><%=gift_user_id%></td>
				<td align="center"><%=gift_user_phone%></td>
				<td align="center"><%=gift_used_yn%></td>
			</tr>
<%      } 
    }
    %> 
                  </tbody>
                </table>
                    <!--//테이블 -->
                </div>     
                
                <div class="box4">
					<a href="javascript:goRegitProc();"> <img src="<%=root_path%>/images/bt_up.gif" alt="등록" style="position:relative;top:5px;"/></a>   
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