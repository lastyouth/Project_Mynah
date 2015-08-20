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
    ResultSetValue   rset      = null;
    ResultSetValue   rset2      = null;
    ResultSetValue   rset3      = null;
    ResultSetValue   rset4      = null;
    
	int    cpage              = (request.getParameter("cpage") == null || "".equals(request.getParameter("cpage"))) ? 0 : Integer.parseInt(request.getParameter("cpage"));   // 현재 페이지
    String searchTarget       = StringUtil.defaultIfEmpty(request.getParameter("searchTarget"), "");	      // 검색 조건 (제목/내용)
    String keyword            = StringUtil.defaultIfEmpty(request.getParameter("keyword"     ), "");        // 검색할 키워드
    String searchFlag         = StringUtil.defaultIfEmpty(request.getParameter("searchFlag"  ), "");        // 검색플래그
    String user_cd       = (String)session.getAttribute("USER_CD");
    String modeType           = StringUtil.defaultIfEmpty(request.getParameter("modeType"    ), "ins");
    String b_mode             = StringUtil.defaultIfEmpty(request.getParameter("b_mode"      ), ""); 
    String conference_id             = StringUtil.defaultIfEmpty(request.getParameter("conference_id"      ), ""); 
    
    //String admin_user_cd         = (String)session.getAttribute("USER_CD");
   // String admin_authority         = (String)session.getAttribute("USE_AUTHORITY");
    
    String user_id     = "";
    String name    = "";
    String sex    = "";
    String company    = "";
    String  nation_id    = "";
    String title    = "";
    String picture    = "";
    String phone1   = "";
    String phone2  = "";
    String phone3   = "";
    String handphone1   = "";
    String handphone2   = "";
    String handphone3   = "";
    String first_phone   = "h";
    String email    = "";
    String email1    = "";
    String email2   = "";
    String address    = "";
    String authoriry_1    = "";
    String authoriry_2    = "";
    String authoriry_3    = "";
    String authoriry_4    = "";
    String authoriry_5    = "";
    String authoriry_6    = "";
    String authoriry_7   = "";
    String authoriry_8    = "";
    String authoriry_9    = "";
    String research_authoriry = "";
    String question_authority    = "";
    String reg_date    = "";
    String platform    = "";
    String authority = "";
    String conference_name  = "";   
    String strMsg   = "수정";
    String sel_appellation_id     = "";   
    String sel_appellation     = "";   
    String appellation_id     = "";   
    String passwd = "";
    String zipcode = "";
    
    parmValue.put("user_cd"    , user_cd  );

    if( !"".equals(user_cd)) {
        rset = member.getMemberInfo(parmValue);
        while( rset.next()){
        	conference_name    = StringUtil.defaultIfEmpty(rset.getString("conference_name"    ), "");
        	conference_id    = StringUtil.defaultIfEmpty(rset.getString("conference_id"    ), "");
    	    user_id    = StringUtil.defaultIfEmpty(rset.getString("id"    ), "");
    	    name    = StringUtil.defaultIfEmpty(rset.getString("name"    ), "");
    	    sex    = StringUtil.defaultIfEmpty(rset.getString("_sex"    ), "");
    	    company    = StringUtil.defaultIfEmpty(rset.getString("company"    ), "");
    	    nation_id    = StringUtil.defaultIfEmpty(rset.getString("nation_id"    ), "");
    	    appellation_id    = StringUtil.defaultIfEmpty(rset.getString("appellation_id"    ), "");
    	    picture    = StringUtil.defaultIfEmpty(rset.getString("picture"    ), "");
    	    phone1    = StringUtil.defaultIfEmpty(rset.getString("phone1"    ), "");
    	    phone2    = StringUtil.defaultIfEmpty(rset.getString("phone2"    ), "");
    	    phone3    = StringUtil.defaultIfEmpty(rset.getString("phone3"    ), "");
    	    handphone1    = StringUtil.defaultIfEmpty(rset.getString("handphone1"    ), "");
    	    handphone2    = StringUtil.defaultIfEmpty(rset.getString("handphone2"    ), "");
    	    handphone3    = StringUtil.defaultIfEmpty(rset.getString("handphone3"    ), "");
    	    first_phone    = StringUtil.defaultIfEmpty(rset.getString("first_phone"    ), "");
    	    email    = StringUtil.defaultIfEmpty(rset.getString("email"    ), "");
    	    zipcode    = StringUtil.defaultIfEmpty(rset.getString("zipcode"    ), "");
    	    address    = StringUtil.defaultIfEmpty(rset.getString("address"    ), "");
    	    authoriry_1    = StringUtil.defaultIfEmpty(rset.getString("authoriry_1"    ), "");
    	    authoriry_2    = StringUtil.defaultIfEmpty(rset.getString("authoriry_2"    ), "");
    	    authoriry_3    = StringUtil.defaultIfEmpty(rset.getString("authoriry_3"    ), "");
    	    authoriry_4    = StringUtil.defaultIfEmpty(rset.getString("authoriry_4"    ), "");
    	    authoriry_5    = StringUtil.defaultIfEmpty(rset.getString("authoriry_5"    ), "");
    	    authoriry_6    = StringUtil.defaultIfEmpty(rset.getString("authoriry_6"    ), "");
    	    authoriry_7    = StringUtil.defaultIfEmpty(rset.getString("authoriry_7"    ), "");
    	    authoriry_8    = StringUtil.defaultIfEmpty(rset.getString("authoriry_8"    ), "");
    	    authoriry_9    = StringUtil.defaultIfEmpty(rset.getString("authoriry_9"    ), "");
    	    research_authoriry    = StringUtil.defaultIfEmpty(rset.getString("research_authoriry"    ), "");
    	    question_authority    = StringUtil.defaultIfEmpty(rset.getString("question_authority"    ), "");
    	    reg_date    = StringUtil.defaultIfEmpty(rset.getString("reg_date"    ), "");
    	    platform    = StringUtil.defaultIfEmpty(rset.getString("platform"    ), "");
    	    authority    = StringUtil.defaultIfEmpty(rset.getString("_authority"    ), "");
    	    passwd    = StringUtil.defaultIfEmpty(rset.getString("passwd"    ), "");
    	    
    	    if(email==null) {
                System.out.println("[UtilHelper] listToToken() : [[Warnning]] Input data is null.");
            }
            else {
            	int cnt = 0;
            	StringTokenizer st2 = new StringTokenizer(email,"@");
            	while(st2.hasMoreTokens()){
            		   if (cnt == 0 )
            			   email1 = st2.nextToken();
            		   else
            			   email2 = st2.nextToken();
            		cnt++;
            	}
            }
        }
 
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
    
    //parmValue.put("admin_user_cd"    , admin_user_cd  );
    //parmValue.put("admin_authority"    , admin_authority  );
    //rset2 = conference.getConferenceList2(parmValue);
    
    rset3 = member.getNationList(parmValue);
    

    parmValue.put("conference_id"    , conference_id  );
    rset4 = appellation.getAppellationSelectList(parmValue);
%>		
<script language="JavaScript" type="text/JavaScript">
<!--    
	function goModifyProc (user_cd) { 
    var frm = document.<%=formName%>;
    
    if (frm.passwd.value.length != 0){
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
    if (frm.address.value == frm.hidden_address.value) {
        alert("나머지 주소를 입력하세요.");
        frm.address.focus();
        return;
    }
    
    if( confirm("수정 하시겠습니까?")) {
        frm.modeType.value = "mod";
        frm.action   = "member_write_proc.jsp";
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
  //-->
</script>
<form name="<%=formName%>" method="post">
<input type="hidden" name="modeType"     value="<%=modeType    %>">
<input type="hidden" name="hidden_picture"     value="<%=picture    %>">
<input type="hidden" name="user_cd" value="<%=user_cd %>">  
<input type="hidden" name="email"   >
<input type="hidden" name="hidden_address"   >

<table width="100%" border="0" cellspacing="0" cellpadding="0">
      
      <tr>
        <td valign="top"><table border="0" cellpadding="0" cellspacing="0" style="border-top:2px solid #e0e0e0; border-bottom:2px solid #e0e0e0; width:784px;">
            <tr>
              <td width="210" class="bbbg" style="padding-left:20px; font-weight:bold;">컨퍼런스명</td>
              <td width="574" style="padding-left:20px;"><%=conference_name %></td>
            </tr>
            <tr>
              <td width="210" class="bbbg" style="padding-left:20px; font-weight:bold;">아이디</td>
              <td width="574" style="padding-left:20px;"><%=user_id %></td>
            </tr>
            <tr>
              <td width="210" class="bbbg" style="padding-left:20px; font-weight:bold;">이름</td>
              <td width="574" style="padding-left:20px;"><%=name%></td>
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
              <td width="574" style="padding-left:20px;"><%=("male".equals(sex)?"남자":"여자") %></td>
            </tr>
            <tr>
              <td width="210" class="bbbg" style="padding-left:20px; font-weight:bold;">소속</td>
              <td width="574" style="padding-left:20px;"><%=company%></td>
            </tr>
            <tr>
              <td width="210" class="bbbg" style="padding-left:20px; font-weight:bold;">국가</td>
              <td width="574" style="padding-left:20px;">
              	<%
              	while( rset3.next()){
                  	if (nation_id.equals(rset3.getString("nation_id"))){
                  		out.println(rset3.getString("nation"    ));
                  	}            	    
              	}
              	%>
              </td>
            </tr>
            <tr>
              <td width="210" class="bbbg" style="padding-left:20px; font-weight:bold;">호칭</td>
              <td width="574" style="padding-left:20px;">
<%
    if(  rset4.row()==0) { %>
                
<%    
    } else {
        while( rset4.next()){
        	sel_appellation_id     = StringUtil.defaultIfEmpty(rset4.getString("appellation_id" ), "");
        	sel_appellation    = StringUtil.defaultIfEmpty(rset4.getString("appellation"), "");
%>				
			<%= sel_appellation_id.equals(appellation_id) ? sel_appellation : "" %>
			
<%      } 
    }
    %>           
          		</select>
              </td>
            </tr>
            <tr>
              <td width="210" class="bbbg" style="padding-left:20px; font-weight:bold;">사진</td>
              <td width="574" style="padding-left:20px;">
              		<%
              	if (!"".equals(picture)){
              		out.println("<img src=/mice/upload/member/"+picture+"><br>");
              	}
              %>
              <input name="picture" type="file" onchange="file_check_img(this)">
              </td>
            </tr>
            <tr>
              <td width="210" class="bbbg" style="padding-left:20px; font-weight:bold;">핸드폰</td>
              <td width="574" style="padding-left:20px;">
              		<select id="select_phoneFirst" name="handphone1" class="phone">
						<option value="">선택</option>
						<option value="010"  <%= "010".equals(handphone1) ? "selected" : "" %>>010</option>	
						<option value="011"   <%= "011".equals(handphone1) ? "selected" : "" %>>011</option>	
						<option value="016"   <%= "016".equals(handphone1) ? "selected" : "" %>>016</option>	
						<option value="017"  <%= "017".equals(handphone1) ? "selected" : "" %> >017</option>	
						<option value="018"   <%= "018".equals(handphone1) ? "selected" : "" %>>018</option>	
						<option value="019"   <%= "019".equals(handphone1) ? "selected" : "" %>>019</option>	
				</select>
			-
			<input type="text" class="text phone" name="handphone2" maxlength="4" value="<%=handphone2%>" style="width: 40px"  onkeyup="evKey(this)"  />
			-
			<input type="text" class="text phone" name="handphone3" maxlength="4" value="<%=handphone3%>" style="width: 40px"  onkeyup="evKey(this)"  />
			<input type="radio" name="first_phone" value="h"  <%= "h".equals(first_phone) ? "checked" : "" %> >
              </td>
            </tr>
            <tr>
              <td width="210" class="bbbg" style="padding-left:20px; font-weight:bold;">전화번호</td>
              <td width="574" style="padding-left:20px;">
              		<select id="select_phoneFirst" name="phone1" class="phone">
						<option value="">선택</option>
						<option value="02"  <%= "02".equals(phone1) ? "selected" : "" %>>02</option>	
						<option value="031"  <%= "031".equals(phone1) ? "selected" : "" %>>031</option>	
						<option value="032"  <%= "032".equals(phone1) ? "selected" : "" %>>032</option>	
						<option value="033"  <%= "033".equals(phone1) ? "selected" : "" %>>033</option>	
						<option value="041" <%= "041".equals(phone1) ? "selected" : "" %> >041</option>	
						<option value="042"  <%= "042".equals(phone1) ? "selected" : "" %>>042</option>	
						<option value="043"  <%= "043".equals(phone1) ? "selected" : "" %>>043</option>		
						<option value="051"  <%= "051".equals(phone1) ? "selected" : "" %>>051</option>		
						<option value="052"  <%= "052".equals(phone1) ? "selected" : "" %>>052</option>		
						<option value="053"  <%= "053".equals(phone1) ? "selected" : "" %>>053</option>		
						<option value="054"  <%= "054".equals(phone1) ? "selected" : "" %>>054</option>		
						<option value="055" <%= "055".equals(phone1) ? "selected" : "" %> >055</option>		
						<option value="061"  <%= "061".equals(phone1) ? "selected" : "" %>>061</option>		
						<option value="062"  <%= "062".equals(phone1) ? "selected" : "" %>>062</option>		
						<option value="063" <%= "063".equals(phone1) ? "selected" : "" %> >063</option>		
						<option value="064"  <%= "064".equals(phone1) ? "selected" : "" %>>064</option>		
						<option value="070"  <%= "070".equals(phone1) ? "selected" : "" %>>070</option>		
						<option value="0505"  <%= "0505".equals(phone1) ? "selected" : "" %>>0505</option>	
				</select>
			-
			<input type="text" class="text phone" name="phone2" maxlength="4" value="<%=phone2%>" style="width: 40px"  onkeyup="evKey(this)"  />
			-
			<input type="text" class="text phone" name="phone3" maxlength="4" value="<%=phone3%>" style="width: 40px"  onkeyup="evKey(this)"  />
			<input type="radio" name="first_phone" value="p" <%= "p".equals(first_phone) ? "checked" : "" %>>
              </td>
            </tr>
            <tr>
              <td width="210" class="bbbg" style="padding-left:20px; font-weight:bold;">이메일</td>
              <td width="574" style="padding-left:20px;">
              	<input type="text" class="text email" name="email1"  maxlength ="20" value="<%= email1  %>"  style="width: 90px" />
				@
				<input type="text" class="text email" name="email2" id="email"  maxlength ="20" value="<%= email2  %>" style="width: 110px"  />
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
              		<input type="text" name="zipcode" id="zipcode" value="<%=zipcode%>" readonly="readonly"  style="width: 50px">
					<input type="button" value="찾아보기" onclick="zipcode_find();"/>
					<br>
					<input type="text" name="address" id="address" value="<%=address%>"  style="width: 380px">
              </td>
            </tr>
            <tr>
              <td width="210" class="bbbg" style="padding-left:20px; font-weight:bold;">개인정보 보호</td>
              <td width="574" style="padding-left:20px;"><input type="radio" name="question_authority" value="y" <%=(StringUtil.strMatch(question_authority,"y").equals("y")?"checked":"") %>>노출
               <input type="radio" name="question_authority" value="n" <%=(StringUtil.strMatch(question_authority,"n").equals("n")?"checked":"") %>>비노출</td>
            </tr>
        </table></td>
      </tr>
      <tr>
        <td height="50" align="center">
        	<a href="javascript:goModifyProc();"><img src="<%=root_path%>/images/bt_modify.gif" alt="수정" /></a>
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