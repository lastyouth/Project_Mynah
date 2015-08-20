<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<% request.setCharacterEncoding("UTF-8"); %>
<%
	String root_path = request.getContextPath();
%>
<%@ page import = "sips.common.lib.util.*" %>
<%@ page import = "sips.common.lib.value.*" %>
<%@ page import = "sips.dept.contents.Additional" %>
<%@ page import = "sips.dept.contents.Conference" %>
<%  String formName = "frmBoard"; %>
<%
	ParamValue       parmValue = new ParamValue();          
	Additional        additional     = new Additional();
	Conference       conference     = new Conference();
    ResultSetValue   rset      = null;
    ResultSetValue   rset3      = null;
 
    String user_cd         = (String)session.getAttribute("USER_CD");
    String user_authority         = (String)session.getAttribute("USE_AUTHORITY");
	int    cpage              = (request.getParameter("cpage") == null || "".equals(request.getParameter("cpage"))) ? 0 : Integer.parseInt(request.getParameter("cpage"));   // 현재 페이지
    String searchTarget       = StringUtil.defaultIfEmpty(request.getParameter("searchTarget"), "");	      // 검색 조건 (제목/내용)
    String keyword            = StringUtil.defaultIfEmpty(request.getParameter("keyword"     ), "");        // 검색할 키워드
    String searchFlag         = StringUtil.defaultIfEmpty(request.getParameter("searchFlag"  ), "");        // 검색플래그

    String conference_id             = StringUtil.defaultIfEmpty(request.getParameter("conference_id"      ), ""); 
    String modeType           = StringUtil.defaultIfEmpty(request.getParameter("modeType"    ), "ins");
    String b_mode             = StringUtil.defaultIfEmpty(request.getParameter("b_mode"      ), ""); 
    String additional_id             = StringUtil.defaultIfEmpty(request.getParameter("additional_id"      ), ""); 

    String gubun  = "";
    String latitude  = "";
    String longitude  = "";
    String main_title  = "";
    String sub_title  = "";
    String contents  = "";
    String strMsg = "";
    String sel_conference_name = "";
    String sel_conference_id = "";
    String phone1   = "";
    String phone2  = "";
    String phone3   = "";
    String handphone1   = "";
    String handphone2   = "";
    String handphone3   = "";
    String first_phone   = "h";
    String address    = "";
    String zipcode = "";
    
    parmValue.put("conference_id"    , conference_id  );
    parmValue.put("additional_id"    , additional_id  );
    parmValue.put("user_cd"        , user_cd  );  
    parmValue.put("user_authority"        , user_authority  );  

    if( !"".equals(additional_id)) {
        rset = additional.getAdditionalInfo(parmValue);
        while( rset.next()){
        	gubun    = StringUtil.defaultIfEmpty(rset.getString("gubun"    ), "");
        	latitude    = StringUtil.defaultIfEmpty(rset.getString("latitude"    ), "");
        	longitude    = StringUtil.defaultIfEmpty(rset.getString("longitude"    ), "");
        	main_title    = StringUtil.defaultIfEmpty(rset.getString("main_title"    ), "");
        	sub_title    = StringUtil.defaultIfEmpty(rset.getString("sub_title"    ), "");
        	contents    = StringUtil.defaultIfEmpty(rset.getString("contents"    ), "");
    	    phone1    = StringUtil.defaultIfEmpty(rset.getString("phone1"    ), "");
    	    phone2    = StringUtil.defaultIfEmpty(rset.getString("phone2"    ), "");
    	    phone3    = StringUtil.defaultIfEmpty(rset.getString("phone3"    ), "");
    	    handphone1    = StringUtil.defaultIfEmpty(rset.getString("handphone1"    ), "");
    	    handphone2    = StringUtil.defaultIfEmpty(rset.getString("handphone2"    ), "");
    	    handphone3    = StringUtil.defaultIfEmpty(rset.getString("handphone3"    ), "");
    	    first_phone    = StringUtil.defaultIfEmpty(rset.getString("first_phone"    ), "");
    	    zipcode    = StringUtil.defaultIfEmpty(rset.getString("zipcode"    ), "");
    	    address    = StringUtil.defaultIfEmpty(rset.getString("address"    ), "");
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
    rset3 = conference.getConferenceSelectList(parmValue);
%>
<script language="JavaScript" type="text/JavaScript">
<!--
    /**
     * 게시물 등록
     */
    function goRegitProc () {
        var frm = document.<%=formName%>;
        

        if (frm.gubun.value == "") {
            alert("숙박/식당을 선택하세요.");
            frm.gubun.focus();
            return;
        }
        if (frm.main_title.value == "") {
            alert("타이틀을 입력하세요.");
            frm.main_title.focus();
            return;
        }
        if (frm.latitude.value == "") {
            alert("위치 찾기를 클릭해서 위도/경도를 입력하세요.");
            frm.latitude.focus();
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
	    if (frm.address.value == frm.hidden_address.value) {
	        alert("나머지 주소를 입력하세요.");
	        frm.address.focus();
	        return;
	    }
        if (frm.contents.value == "") {
            alert("내용을 입력하세요.");
            frm.contents.focus();
            return;
        }

        if( confirm("<%=strMsg%> 하시겠습니까?")) {
            frm.action   = "additional_write_proc.jsp";
           // frm.encoding = "multipart/form-data";
            frm.submit();
        }
    }
	function goModifyProc (member_cd) { 
	    var frm = document.<%=formName%>;
	    
	    if (frm.gubun.value == "") {
	        alert("숙박/식당을 선택하세요.");
	        frm.gubun.focus();
	        return;
	    }
	    if (frm.main_title.value == "") {
	        alert("타이틀을 입력하세요.");
	        frm.main_title.focus();
	        return;
	    }
	    if (frm.latitude.value == "") {
	        alert("위치 찾기를 클릭해서 위도/경도를 입력하세요.");
	        frm.latitude.focus();
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
	    if (frm.address.value == frm.hidden_address.value) {
	        alert("나머지 주소를 입력하세요.");
	        frm.address.focus();
	        return;
	    }
	    if (frm.contents.value == "") {
	        alert("내용을 입력하세요.");
	        frm.contents.focus();
	        return;
	    }
    
	    if( confirm("수정 하시겠습니까?")) {
	        frm.modeType.value = "mod";
	        frm.action   = "additional_write_proc.jsp";
	      //  frm.encoding = "multipart/form-data";
	        frm.submit();
	    }
    }    
	function evKey(el) { 
		   var v = el.value.replace(/[^0-9]/g, ""); 
		   if(v.length > 4) v = v.slice(0, 4) + "." + v.slice(4); 
		   if(v.length > 7) v = v.slice(0, 7) + "." + v.slice(7, 9); 
		   el.value = v; 
		} 
	  
    /**
     * 게시물 삭제
     */
    function goDeleteForm () { 
        var frm = document.<%=formName%>;
        if( confirm("삭제 하시겠습니까?")) {
            frm.modeType.value = "del";
            frm.action   = "additional_write_proc.jsp";
	      //  frm.encoding = "multipart/form-data";
            frm.submit();
        }
                
    }    
    
    function goList() {
        var frm = document.<%=formName%>;
        
        frm.b_mode.value = "L";
        frm.action   = "<%= request.getRequestURI() %>";
        frm.submit();
    }

    function zipcode_find( ){
        var frm = document.<%=formName%>;
  		var pop = document.getElementById('layerPop3');
  		document.all['moveFrame2'].src = './zipcode_find_popup.jsp';
 			pop.style.display = "block";
 			pop.style.top =  "170px";
 			pop.style.left = "400px";
  	}

	//레이어 팝업 닫기
	function closeLayer(IdName){
		var pop = document.getElementById(IdName);
		pop.style.display = "none";
	}
	
	function onSetSelectInfo(t1, t2) {
        var frm = document.<%=formName%>;
		//값 넣기.
		for( var i = 0; i <= frm.elements.length - 1; i++ ){
 			if( frm.elements[i].name == "latitude" )
 			{
 				frm.elements[i].value = t1;
 			}
 			
 			if( frm.elements[i].name == "longitude" )
 			{
 				frm.elements[i].value = t2;
 			}
		}
	}
	
	function find_map( ){
  		var pop = document.getElementById('layerPop');
  		document.all['moveFrame'].src = './find_map_popup.jsp';
 			pop.style.display = "block";
 			pop.style.top =  "50px";
 			pop.style.left = "200px";
  	}
  //-->
</script>
<form name="<%=formName%>" method="post">
<input type="hidden" name="cpage"        value="<%= cpage       %>">
<input type="hidden" name="additional_id" value="<%=additional_id %>">  
<input type="hidden" name="b_mode"       value="<%=b_mode      %>">
<input type="hidden" name="modeType"     value="<%=modeType    %>">
<input type="hidden" name="hidden_address"   >

<table width="100%" border="0" cellspacing="0" cellpadding="0">
       
        <td valign="top"><table border="0" cellpadding="0" cellspacing="0" style="border-top:2px solid #e0e0e0; border-bottom:2px solid #e0e0e0; width:784px;">
            <tr>
              <td width="210" class="bbbg" style="padding-left:20px; font-weight:bold;">컨퍼런스</td>
              <td width="574" style="padding-left:20px;">
              		<select name=conference_id id=conference_id>
<%
    if(  rset3.row()==0) { %>
               <option value="">컨퍼런스 없음</option> 
<%    
    } else {
        while( rset3.next()){
        	sel_conference_name     = StringUtil.defaultIfEmpty(rset3.getString("name" ), "");
        	sel_conference_id    = StringUtil.defaultIfEmpty(rset3.getString("conference_id"), "");
%>				
			<option value="<%=sel_conference_id%>" <%= conference_id.equals(sel_conference_id) ? "selected" : "" %> ><%=sel_conference_name%></option>
			
<%      } 
    }
    %> 
          		</select>
              </td>
            </tr>
            <tr>
              <td width="210" class="bbbg" style="padding-left:20px; font-weight:bold;">숙박/식당</td>
              <td width="574" style="padding-left:20px;">
              		<select name=gubun id=gubun>
               			<option value="">선택</option> 
               			<option value="lodge" <%= "lodge".equals(gubun) ? "selected" : "" %> >숙박</option>
               			<option value="restaurant" <%= "restaurant".equals(gubun) ? "selected" : "" %> >식당</option>
          			</select>
              </td>
            </tr>
            <tr>
              <td width="210" class="bbbg" style="padding-left:20px; font-weight:bold;">타이틀</td>
              <td width="574" style="padding-left:20px;"><input name=main_title type="text" value="<%=main_title%>" class="Ttable" size="50"></td>
            </tr>
            <tr>
              <td width="210" class="bbbg" style="padding-left:20px; font-weight:bold;">위치</td>
              <td width="574" style="padding-left:20px;">
              <input type="button" value="위치 찾기" onclick="find_map();"/><br>
					위도 : <input type="text" name="latitude" id="latitude" readonly="readonly" value="<%=latitude %>"><br>
					경도 : <input type="text" name="longitude" id="longitude" readonly="readonly" value="<%=longitude %>" >
							
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
              <td width="210" class="bbbg" style="padding-left:20px; font-weight:bold;">주소</td>
              <td width="574" style="padding-left:20px;">
              		<input type="text" name="zipcode" id="zipcode" value="<%=zipcode%>" readonly="readonly"  style="width: 50px">
					<input type="button" value="찾아보기" onclick="zipcode_find();"/>
					<br>
					<input type="text" name="address" id="address" value="<%=address%>"   style="width: 380px">
              </td>
            </tr>
            <tr>
              <td width="210" class="bbbg" style="padding-left:20px; font-weight:bold;">내용</td>
              <td width="574" style="padding-left:20px;">
              	<textarea name="contents" rows="5" cols="60"><%=contents %></textarea>
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
            <a href="javascript:goDeleteForm('<%= additional_id %>');"><img src="<%=root_path%>/images/bt_del.gif" alt="삭제" /></a>
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

<div  id="layerPop3" >
 <iframe id="moveFrame2" frameborder='0' height='100%' width='100%' scrolling='no' src=''></iframe>
</div>
<!--// 팝업 -->