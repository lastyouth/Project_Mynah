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
	
	String result = "";
	
	String dong       = StringUtil.defaultIfEmpty(request.getParameter("dong"), "");
	String zipcode = "";
	String sido = "";
	String gugun = "";
	String bunji = "";
	String seq = "";
    
    parmValue.put("dong"    , dong  );
    if (!"".equals(dong))
    	rset = member.getZipcode(parmValue);

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
        closeLayer('layerPop3');
    }
}

function goNoRegitProc (cnt, tot){

	if (tot == 1){
		parent.document.frmBoard.hidden_address.value = document.frmBoard.address.value;
		parent.document.frmBoard.address.value = document.frmBoard.address.value;
		parent.document.frmBoard.zipcode.value = document.frmBoard.zipcode.value;
	}else{
		parent.document.frmBoard.hidden_address.value = document.frmBoard.address[cnt].value;
		parent.document.frmBoard.address.value = document.frmBoard.address[cnt].value;
		parent.document.frmBoard.zipcode.value = document.frmBoard.zipcode[cnt].value;
	}
	closeLayer('layerPop3');

}

function goSearch() {
    var frm = document.<%=formName%>;
    
    if(frm.dong.value == "")
    {
	     alert("아이디를 입력해 주십시오.");
	     frm.dong.focus();
	     return false;
    }
    
    frmBoard.target="_self";
	frmBoard.action             = "./zipcode_find_popup.jsp";
	frmBoard.submit();
}
  //-->
</script>
</head>
<body>
<form name="<%=formName%>" method="post">
<div id="main-wrapper1">
    <div id="main-wrapper">
    <div id="contents">
    <div id="layerPopOpen">
                
                <div class="box4">
                	<h3>주소 입력 팝업</h3>          
                </div>     
                <!--검색 -->  
                <div class="box3">
                	<input name="dong" type="text" value="<%= dong %>" onKeypress="if (event.keyCode==13) goSearch();" class="input_box" size="20">
			            <a href="javascript:goSearch();"><img src="<%=root_path%>/images/bt_search.gif" alt="검색" class="sbtn1"/></a>
                </div>
                <!--스크롤 테이블 -->               
                <div class="scroll2">
                    <!--테이블 -->
                    <table  style="border:1px solid #d3d3d3; width:100%;">
                  <caption>
                    슬릿팅
                  </caption>
                  <thead>
                    <th style="padding-top:5px;">
                        주소
                    </th>           
              	  </thead> 
                  <tbody>
<%
    if( rset == null) { %>
                <tr><td colspan=7 align="center" height=50>:::: 없음 ::::</td></tr>  
<%    
    } else {
    	int i = 0;
    	int tot = rset.row();
        while( rset.next()){
        	sido       = StringUtil.defaultIfEmpty(rset.getString("sido"   ), ""); 
        	gugun     = StringUtil.defaultIfEmpty(rset.getString("gugun" ), "");
        	dong    = StringUtil.defaultIfEmpty(rset.getString("dong"), "");
        	bunji          = StringUtil.defaultIfEmpty(rset.getString("bunji"         ), "");
        	seq          = StringUtil.defaultIfEmpty(rset.getString("seq"         ), "");
        	zipcode          = StringUtil.defaultIfEmpty(rset.getString("zipcode"         ), "");
%>
			<tr>
				<td align="center">
					<a href="javascript:goNoRegitProc('<%=i %>', '<%=tot %>');" class="skin">
					<%=sido%> <%=gugun%> <%=dong%> <%=bunji%>
					</a>
				<input type="hidden" name=address" id="address" value="<%=sido%> <%=gugun%> <%=dong%> <%=bunji%>">
				<input type="hidden" name=zipcode" id="zipcode" value="<%=zipcode%>">
				</td>
			</tr>
<%      
			i++;	
		} 
    }
    %> 
                </table>
                    <!--//테이블 -->
                </div>          
                <div class="box4">
					<span class="close"><a href="#" onclick="closeLayer('layerPop3')" class="close">닫기</a></span>
                </div>     
                <!--//검색 -->   
		 </div>
		 </div>
		 </div>
	</div>
</form>	
</body>
</html>                