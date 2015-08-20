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
	
	String user_id       = StringUtil.defaultIfEmpty(request.getParameter("user_id"), "");
    
    parmValue.put("user_id"    , user_id  );
    
    result = member.getNewId(parmValue);

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
        closeLayer('layerPop2');
    }
}

function goNoRegitProc (){
    var frm = document.<%=formName%>;
    
	parent.onSetSelectInfo(frm.user_id.value);
	closeLayer('layerPop2');

}

function goSearch() {
    var frm = document.<%=formName%>;
    
    if(frm.user_id.value == "")
    {
	     alert("아이디를 입력해 주십시오.");
	     frm.user_id.focus();
	     return false;
    }
    if(frm.user_id.value.length < 6 || frm.user_id.value.length >12 )
    {
    	alert("회원 아이디는 6 ~ 12자의 영문와 숫자만 사용 할 수 있습니다.");
      	frm.user_id.fucus();
      	return false;
    }
    
    var myregexp = new RegExp('^[A-Za-z]+[0-9]*$');
    if(!myregexp.test(frm.user_id.value))
    {
	      alert("회원아이디는 영문과 숫자로만 입력이 가능합니다.");
	      frm.user_id.fucus();
	      return false;
    }
    
    frmBoard.target="_self";
	frmBoard.action             = "./id_find_popup.jsp";
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
                	<h3>아이디 중복 체크</h3>          
                </div>     
                <!--검색 -->  
                <div class="box3">
                	<input name="user_id" type="text" value="<%= user_id %>" onKeypress="if (event.keyCode==13) goSearch();" class="input_box" size="20">
			            <a href="javascript:goSearch();"><img src="<%=root_path%>/images/bt_search.gif" alt="검색" class="sbtn1"/></a>
                </div>
                <!--스크롤 테이블 -->               
                <div class="box3">
<%
				if ("".equals(result)){
					if ("".equals(user_id)){
						out.println("검색을 이용해서 다른 아이디를 중복확인하세요.");
					}else{
						out.println("사용가능 아이디입니다. 아이디를 클릭하면 하세요.<br><a href=javascript:goNoRegitProc()>"+user_id+"</a>");
					}
				}else{
%>
							중복 된 아이디가 존재합니다.
<%
				}
%>
                </table>
                    <!--//테이블 -->
                </div>          
                <div class="box4">
					<span class="close"><a href="#" onclick="closeLayer('layerPop2')" class="close">닫기</a></span>
                </div>     
                <!--//검색 -->   
		 </div>
		 </div>
		 </div>
	</div>
</form>	
</body>
</html>                