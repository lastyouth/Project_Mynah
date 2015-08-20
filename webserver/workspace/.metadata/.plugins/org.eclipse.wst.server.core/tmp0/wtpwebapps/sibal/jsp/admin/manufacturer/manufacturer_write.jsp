<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<% request.setCharacterEncoding("UTF-8"); %>
<%
	String root_path = request.getContextPath();
%>
<%@ page import = "sips.common.lib.util.*" %>
<%@ page import = "sips.common.lib.value.*" %>
<%@ page import = "sips.dept.contents.Manufacturer" %>
<%@ page import = "sips.dept.contents.Conference" %>
<%  String formName = "frmBoard"; %>
<%
	ParamValue       parmValue = new ParamValue();          
Manufacturer            manufacturer     = new Manufacturer();
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
    String manufacturer_id             = StringUtil.defaultIfEmpty(request.getParameter("manufacturer_id"      ), ""); 
    
    String name  = "";
    String strMsg = "";
    String sel_conference_name = "";
    String sel_conference_id = "";
    
    String manufacturer_name = "";
    String manufacturer_ceo    = "";
    String phone1    = "";
    String phone2    = "";
    String phone3    = "";
    String handphone1    = "";
    String handphone2    = "";
    String handphone3    = "";
    String zipcode    = "";
    String address    = "";
    String security    = "";
    String first_phone   = "h";
    
    parmValue.put("conference_id"    , conference_id  );
    parmValue.put("manufacturer_id"    , manufacturer_id  );
    parmValue.put("user_cd"        , user_cd  );  
    parmValue.put("user_authority"        , user_authority  );  

    if( !"".equals(manufacturer_id)) {
        rset = manufacturer.getManufacturerInfo(parmValue);
        while( rset.next()){
        	manufacturer_name    = StringUtil.defaultIfEmpty(rset.getString("manufacturer_name"    ), "");
        	manufacturer_ceo    = StringUtil.defaultIfEmpty(rset.getString("manufacturer_ceo"    ), "");
        	phone1    = StringUtil.defaultIfEmpty(rset.getString("phone1"    ), "");
        	phone2    = StringUtil.defaultIfEmpty(rset.getString("phone2"    ), "");
        	phone3    = StringUtil.defaultIfEmpty(rset.getString("phone3"    ), "");
        	handphone1    = StringUtil.defaultIfEmpty(rset.getString("handphone1"    ), "");
        	handphone2    = StringUtil.defaultIfEmpty(rset.getString("handphone2"    ), "");
        	handphone3    = StringUtil.defaultIfEmpty(rset.getString("handphone3"    ), "");
        	zipcode    = StringUtil.defaultIfEmpty(rset.getString("zipcode"    ), "");
        	address    = StringUtil.defaultIfEmpty(rset.getString("address"    ), "");
        	first_phone    = StringUtil.defaultIfEmpty(rset.getString("first_phone"    ), "");
        	security    = StringUtil.defaultIfEmpty(rset.getString("security"    ), "");
        	conference_id    = StringUtil.defaultIfEmpty(rset.getString("conference_id"    ), "");
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
        

        if (frm.manufacturer_name.value == "") {
            alert("업체명을 입력하세요.");
            frm.manufacturer_name.focus();
            return;
        }
        if (frm.manufacturer_ceo.value == "") {
            alert("대표자를 입력하세요.");
            frm.manufacturer_ceo.focus();
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
        if (frm.security.value == "") {
            alert("보안코드를 생성하세요.");
            frm.security.focus();
            return;
        }

        if( confirm("<%=strMsg%> 하시겠습니까?")) {
            frm.action   = "manufacturer_write_proc.jsp";
            //frm.encoding = "multipart/form-data";
            frm.submit();
        }
    }
	function goModifyProc (member_cd) { 
	    var frm = document.<%=formName%>;
	    if (frm.manufacturer_name.value == "") {
	        alert("업체명을 입력하세요.");
	        frm.manufacturer_name.focus();
	        return;
	    }
	    if (frm.manufacturer_ceo.value == "") {
	        alert("대표자를 입력하세요.");
	        frm.manufacturer_ceo.focus();
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
        if (frm.security.value == "") {
            alert("보안코드를 생성하세요.");
            frm.security.focus();
            return;
        }
	    
	    if( confirm("수정 하시겠습니까?")) {
	        frm.modeType.value = "mod";
	        frm.action   = "manufacturer_write_proc.jsp";
	        //frm.encoding = "multipart/form-data";
	        frm.submit();
	    }
    }    
	
	  
    /**
     * 게시물 삭제
     */
    function goDeleteForm () { 
        var frm = document.<%=formName%>;
        if( confirm("삭제 하시겠습니까?")) {
            frm.modeType.value = "del";
            frm.action   = "manufacturer_write_proc.jsp";
	        //frm.encoding = "multipart/form-data";
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
    
    function make_security()
    {
        var frm = document.<%=formName%>;
        var text = "";
        var possible = "0123456789";

        for( var i=0; i < 6; i++ )
            text += possible.charAt(Math.floor(Math.random() * possible.length));

        frm.security.value = text;
    }
  //-->
</script>
<form name="<%=formName%>" method="post">
<input type="hidden" name="cpage"        value="<%= cpage       %>">
<input type="hidden" name="manufacturer_id" value="<%=manufacturer_id %>">  
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
              <td width="210" class="bbbg" style="padding-left:20px; font-weight:bold;">업체명</td>
              <td width="574" style="padding-left:20px;"><input name="manufacturer_name" type="text" value="<%=manufacturer_name%>" class="Ttable" size="30"></td>
            </tr>
            <tr>
              <td width="210" class="bbbg" style="padding-left:20px; font-weight:bold;">대표자</td>
              <td width="574" style="padding-left:20px;"><input name="manufacturer_ceo" type="text" value="<%=manufacturer_ceo%>" class="Ttable" size="10"></td>
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
					<input type="text" name="address" id="address" value="<%=address%>"  style="width: 380px">
              </td>
            </tr>
            <tr>
              <td width="210" class="bbbg" style="padding-left:20px; font-weight:bold;">보안코드</td>
              <td width="574" style="padding-left:20px;"><input name="security" type="text" value="<%=security%>" class="Ttable" size="10" readonly> <a href="javascript:make_security()">생성</a></a></td>
            </tr>
        </table></td>
      </tr>
      <tr>
        <td height="50" align="center">
        <% if(modeType.equals("ins")){ %>
            <a href="javascript:goRegitProc();"><img src="<%=root_path%>/images/bt_up.gif" alt="등록" /></a>
         <% }else{ %>
            <a href="javascript:goModifyProc();"><img src="<%=root_path%>/images/bt_modify.gif" alt="수정" /></a>
            <a href="javascript:goDeleteForm('<%= manufacturer_id %>');"><img src="<%=root_path%>/images/bt_del.gif" alt="삭제" /></a>
         <% } %>   
            <a href="javascript:goList();"><img src="<%=root_path%>/images/bt_list.gif" alt="목록" /></a>
         </td>
      </tr>
    </table>
    
</form>		
<!-- 팝업 -->
<div  id="layerPop3" >
 <iframe id="moveFrame2" frameborder='0' height='100%' width='100%' scrolling='no' src=''></iframe>
</div>
<!--// 팝업 -->