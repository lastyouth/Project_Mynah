<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<% request.setCharacterEncoding("UTF-8"); %>
<%
	String root_path = request.getContextPath();
%>
<%@ page import = "java.util.*" %>
<%@ page import = "sips.common.lib.util.*" %>
<%@ page import = "sips.common.lib.value.*" %>
<%@ page import = "sips.dept.contents.Member" %>
<%@ page import = "sips.dept.contents.Conference" %>
<%@ page import = "sips.dept.contents.Appellation" %>
<%  String formName = "frmBoard"; %>
<%
	ParamValue       parmValue = new ParamValue();          
	Member            member     = new Member();
	Conference            conference     = new Conference();
	Appellation            appellation     = new Appellation();
    ResultSetValue   rset2      = null;
    ResultSetValue   rset3      = null;
    ResultSetValue   rset4      = null;
    
	String modeType           = StringUtil.defaultIfEmpty(request.getParameter("modeType"    ), "ins");
    String b_mode             = StringUtil.defaultIfEmpty(request.getParameter("b_mode"      ), ""); 
    String conference_id             = StringUtil.defaultIfEmpty(request.getParameter("conference_id"      ), ""); 
    String conference_name  = "";   
    String strMsg   = "수정";
    String sel_appellation_id     = "";   
    String sel_appellation     = "";   
    String appellation_id     = "";   
    String passwd = "";
    String zipcode = "";
    String nation_id = "";

    modeType = "ins";
    strMsg   = "등록";
    
    rset2 = conference.getConferenceList3();
    rset3 = member.getNationList(parmValue);
    parmValue.put("conference_id"    , conference_id  );
    rset4 = appellation.getAppellationSelectList(parmValue);
%>		
<script language="JavaScript" type="text/JavaScript">
<!--
    /**
     * 게시물 등록
     */
    function goRegitProc () {
        var frm = document.<%=formName%>;
        

        if (frm.name.value == "") {
            alert("이름을 입력하세요.");
            frm.name.focus();
            return;
        }
        
        if(frm.passwd.value.length < 6 || frm.passwd.value.length >12 )
        {
        	alert("비밀번호는 6 ~ 12자까지 사용 할 수 있습니다.");
          	frm.passwd.fucus();
          	return false;
        }

        if (frm.passwd.value != frm.repasswd.value) {
            alert("비밀번호가 상이합니다..");
            frm.repasswd.focus();
            return;
        }

        if (frm.appellation_id.value == "") {
            alert("호칭을 선택해주세요.");
            frm.appellation_id.focus();
            return;
        }

        if (frm.handphone1.value == "") {
            alert("핸드폰 번호를 입력하세요.");
            frm.handphone1.focus();
            return;
        }
        if (frm.handphone2.value == "") {
            alert("핸드폰 번호를 입력하세요.");
            frm.handphone2.focus();
            return;
        }
        if (frm.handphone3.value == "") {
            alert("핸드폰 번호를 입력하세요.");
            frm.handphone3.focus();
            return;
        }
        if (frm.email1.value == "" || frm.email2.value == "") {
            alert("이메일 주소를 입력하세요..");
            frm.email1.focus();
            return;
        }
        if (frm.zipcode.value == "") {
            alert("우편번호를 입력하세요.");
            frm.zipcode.focus();
            return;
        }
        if (frm.address.value == "") {
            alert("나머지 주소를 입력하세요.");
            frm.address.focus();
            return;
        }
        
        if( confirm("<%=strMsg%> 하시겠습니까?")) {
            frm.action   = "register_write_proc.jsp";
            frm.encoding = "multipart/form-data";
            frm.submit();
        }
    }
    function getSpace(cnt) {
        var spc = "";
        for (i = 0; i < cnt; i++) {
            spc += " ";
        }        
        return spc;
    }
    
   

	//레이어 팝업 닫기
	function closeLayer(IdName){
		var pop = document.getElementById(IdName);
		pop.style.display = "none";
	}
    
	
    function id_find( ){
        var frm = document.<%=formName%>;
  		var pop = document.getElementById('layerPop2');
  		document.all['moveFrame'].src = './id_find_popup.jsp';
 			pop.style.display = "block";
 			pop.style.top =  "170px";
 			pop.style.left = "400px";
  	}
    function zipcode_find( ){
        var frm = document.<%=formName%>;
  		var pop = document.getElementById('layerPop3');
  		document.all['moveFrame2'].src = './zipcode_find_popup.jsp';
 			pop.style.display = "block";
 			pop.style.top =  "170px";
 			pop.style.left = "400px";
  	}
    
    function onSetSelectInfo(value) {
        var frm = document.<%=formName%>;
		//값 넣기.
		for( var i = 0; i <= frm.elements.length - 1; i++ ){
 			if( frm.elements[i].name == "user_id" )
 			{
 				frm.elements[i].value = value;
 			}
		}
	}
    
    function conference_change(obj)
	{
        var frm = document.<%=formName%>;
		//var obj_value = conference_id.value;
		//alert(obj_value);
		frm.target="_self";
		frm.b_mode.value = "W";
		frm.action             = "./register.jsp";
		frm.submit();
	}
    
    function email_change(obj){
        var frm = document.<%=formName%>;
		frm.email2.value = obj.value;
      	frm.email1.fucus();
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
  
	function evKey(el) { 
		   var v = el.value.replace(/[^0-9]/g, ""); 
		   if(v.length > 4) v = v.slice(0, 4) + "." + v.slice(4); 
		   if(v.length > 7) v = v.slice(0, 7) + "." + v.slice(7, 9); 
		   el.value = v; 
		} 
	
	function allCheck(){
        var frm = document.<%=formName%>;
        
        if (frm.all.checked == true){
	        frm.authoriry_1.checked = true;
	        frm.authoriry_2.checked = true;
	        frm.authoriry_3.checked = true;
	        frm.authoriry_4.checked = true;
	        frm.authoriry_5.checked = true;
	        frm.authoriry_6.checked = true;
	        frm.authoriry_7.checked = true;
	        frm.authoriry_8.checked = true;
	        frm.authoriry_9.checked = true;
        }else{
        	frm.authoriry_1.checked = false;
	        frm.authoriry_2.checked = false;
	        frm.authoriry_3.checked = false;
	        frm.authoriry_4.checked = false;
	        frm.authoriry_5.checked = false;
	        frm.authoriry_6.checked = false;
	        frm.authoriry_7.checked = false;
	        frm.authoriry_8.checked = false;
	        frm.authoriry_9.checked = false;
        }
	}
  //-->
</script>
<form name="<%=formName%>" method="post">
<input type="hidden" name="b_mode"       value="<%=b_mode      %>">
<input type="hidden" name="modeType"     value="<%=modeType    %>">
<input type="hidden" name="email"   >
<input type="hidden" name="hidden_address"   >

<table width="100%" border="0" cellspacing="0" cellpadding="0">
      
      <tr>
        <td valign="top"><table border="0" cellpadding="0" cellspacing="0" style="border-top:2px solid #e0e0e0; border-bottom:2px solid #e0e0e0; width:784px;">
            <tr>
              <td width="210" class="bbbg" style="padding-left:20px; font-weight:bold;">컨퍼런스명</td>
              <td width="574" style="padding-left:20px;">
              	<select name="conference_id" id="conference_id" onchange="conference_change(this)">
              			<option value="0" >선택</option>
              	<%
              	String selected = "";
              	while( rset2.next()){
                  	if (conference_id.equals(rset2.getString("conference_id"))){
                  		selected = "selected";
                  	}else{
                  		selected = "";
                  	}
              		out.println("<option value="+rset2.getString("conference_id"    )+"  "+selected+">"+rset2.getString("name"    )+"</option>");            	    
              	}
              	%>
              	</select>
              </td>
            </tr>
            <tr>
              <td width="210" class="bbbg" style="padding-left:20px; font-weight:bold;">아이디</td>
              <td width="574" style="padding-left:20px;">
					<input type="text" name="user_id" id="user_id" readonly="readonly" value="">
					<input type="button" value="찾아보기" onclick="id_find();"/>
              </td>
            </tr>
            <tr>
              <td width="210" class="bbbg" style="padding-left:20px; font-weight:bold;">이름</td>
              <td width="574" style="padding-left:20px;"><input type="text" name="name" value=""></td>
            </tr>
            <tr>
              <td width="210" class="bbbg" style="padding-left:20px; font-weight:bold;">비밀번호</td>
              <td width="574" style="padding-left:20px;"><input type="password" name="passwd" value=""></td>
            </tr>
            <tr>
              <td width="210" class="bbbg" style="padding-left:20px; font-weight:bold;">비밀번호 확인</td>
              <td width="574" style="padding-left:20px;"><input type="password" name="repasswd" value=""></td>
            </tr>
            <tr>
              <td width="210" class="bbbg" style="padding-left:20px; font-weight:bold;">성별</td>
              <td width="574" style="padding-left:20px;"><input type="radio" name="sex" value="male" checked>남자 
              <input type="radio" name="sex" value="female">여자</td>
            </tr>
            <tr>
              <td width="210" class="bbbg" style="padding-left:20px; font-weight:bold;">소속</td>
              <td width="574" style="padding-left:20px;"><input type="text" name="company"></td>
            </tr>
            <tr>
              <td width="210" class="bbbg" style="padding-left:20px; font-weight:bold;">국가</td>
              <td width="574" style="padding-left:20px;">
              <select name="nation_id">
              	<%
              	String selected3 = "";
              	while( rset3.next()){
                  	if (nation_id.equals(rset3.getString("nation_id"))){
                  		selected3 = "selected";
                  	}else{
                  		selected3 = "";
                  	}
              		out.println("<option value="+rset3.getString("nation_id"    )+"  "+selected3+">"+rset3.getString("nation"    )+"</option>");            	    
              	}
              	%>
              		
              	</select>
              </td>
            </tr>
            <tr>
              <td width="210" class="bbbg" style="padding-left:20px; font-weight:bold;">호칭</td>
              <td width="574" style="padding-left:20px;">
              	<select name=appellation_id id=appellation_id>
              			<option value="" >선택</option>
<%
    if(  rset4.row()==0) { %>
                
<%    
    } else {
        while( rset4.next()){
        	sel_appellation_id     = StringUtil.defaultIfEmpty(rset4.getString("appellation_id" ), "");
        	sel_appellation    = StringUtil.defaultIfEmpty(rset4.getString("appellation"), "");
%>				
			<option value="<%=sel_appellation_id%>" <%= sel_appellation_id.equals(appellation_id) ? "selected" : "" %> ><%=sel_appellation%></option>
			
<%      } 
    }
    %>           
          		</select>
              </td>
            </tr>
            <tr>
              <td width="210" class="bbbg" style="padding-left:20px; font-weight:bold;">사진</td>
              <td width="574" style="padding-left:20px;">
              	<input name="picture" type="file" onchange="file_check_img(this)">
              </td>
            </tr>
            <tr>
              <td width="210" class="bbbg" style="padding-left:20px; font-weight:bold;">핸드폰</td>
              <td width="574" style="padding-left:20px;">
              		<select id="select_phoneFirst" name="handphone1" class="phone">
						<option value="">선택</option>
						<option value="010">010</option>	
						<option value="011" >011</option>	
						<option value="016" >016</option>	
						<option value="017" >017</option>	
						<option value="018" >018</option>	
						<option value="019" >019</option>	
				</select>
			-
			<input type="text" class="text phone" name="handphone2" maxlength="4" value="" style="width: 40px"  onkeyup="evKey(this)"  />
			-
			<input type="text" class="text phone" name="handphone3" maxlength="4" value="" style="width: 40px"  onkeyup="evKey(this)"  />
			<input type="radio" name="first_phone" value="h"   checked>
              </td>
            </tr>
            <tr>
              <td width="210" class="bbbg" style="padding-left:20px; font-weight:bold;">전화번호</td>
              <td width="574" style="padding-left:20px;">
              		<select id="select_phoneFirst" name="phone1" class="phone">
						<option value="">선택</option>
						<option value="02"  >02</option>	
						<option value="031">031</option>	
						<option value="032">032</option>	
						<option value="033">033</option>	
						<option value="041">041</option>	
						<option value="042">042</option>	
						<option value="043">043</option>		
						<option value="051">051</option>		
						<option value="052">052</option>		
						<option value="053">053</option>		
						<option value="054">054</option>		
						<option value="055">055</option>		
						<option value="061">061</option>		
						<option value="062">062</option>		
						<option value="063">063</option>		
						<option value="064">064</option>		
						<option value="070">070</option>		
						<option value="0505">>0505</option>	
				</select>
			-
			<input type="text" class="text phone" name="phone2" maxlength="4" value="" style="width: 40px"  onkeyup="evKey(this)"  />
			-
			<input type="text" class="text phone" name="phone3" maxlength="4" value="" style="width: 40px"  onkeyup="evKey(this)"  />
			<input type="radio" name="first_phone" value="p">
              </td>
            </tr>
            <tr>
              <td width="210" class="bbbg" style="padding-left:20px; font-weight:bold;">이메일</td>
              <td width="574" style="padding-left:20px;">
              	<input type="text" class="text email" name="email1"  maxlength ="20" value=""  style="width: 90px" />
				@
				<input type="text" class="text email" name="email2" id="email"  maxlength ="20" value="" style="width: 110px"  />
				<select class="email_selector"  onchange="email_change(this)">
					<option value="">선택</option>
					<option value="naver.com">naver.com</option>
					<option value="chol.com">chol.com</option>
					<option value="dreamwiz.com">dreamwiz.com</option>
					<option value="empal.com">empal.com</option>
					<option value="freechal.com">freechal.com</option>
					<option value="gmail.com">gmail.com</option>
					<option value="hanafos.com">hanafos.com</option>
					<option value="hanmail.net">hanmail.net</option>
					<option value="hitel.net">hitel.net</option>
					<option value="hotmail.com">hotmail.com</option>
					<option value="korea.com">korea.com</option>
					<option value="lycos.co.kr">lycos.co.kr</option>
					<option value="nate.com">nate.com</option>
					<option value="netian.com">netian.com</option>
					<option value="yahoo.co.kr">yahoo.co.kr</option>
				</select>
              		
              </td>
            </tr>
            <tr>
              <td width="210" class="bbbg" style="padding-left:20px; font-weight:bold;">주소</td>
              <td width="574" style="padding-left:20px;">
              		<input type="text" name="zipcode" id="zipcode" value="" readonly="readonly"  style="width: 50px">
					<input type="button" value="찾아보기" onclick="zipcode_find();"/>
					<br>
					<input type="text" name="address" id="address" value=""  style="width: 380px">
              </td>
            </tr>
            <tr>
              <td width="210" class="bbbg" style="padding-left:20px; font-weight:bold;">개인정보 보호</td>
              <td width="574" style="padding-left:20px;"><input type="radio" name="question_authority" value="y" checked>노출
               <input type="radio" name="question_authority" value="n" >비노출</td>
            </tr>
        </table></td>
      </tr>
      <tr>
        <td height="50" align="center">
        	<a href="javascript:goRegitProc();"><img src="<%=root_path%>/images/bt_up.gif" alt="등록" /></a>
         </td>
      </tr>
    </table>
    
</form>		

<!-- 팝업 -->
<div  id="layerPop2" >
 <iframe id="moveFrame" frameborder='0' height='100%' width='100%' scrolling='no' src=''></iframe>
</div>
<div  id="layerPop3" >
 <iframe id="moveFrame2" frameborder='0' height='100%' width='100%' scrolling='no' src=''></iframe>
</div>
<!--// 팝업 -->