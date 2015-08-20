<%@ page language = "java" contentType="text/html; charset=UTF-8" %>
<%
	String root_path = request.getContextPath();
%>
<%@ page import = "sips.common.lib.util.*" %>
<%@ page import = "sips.common.lib.value.*" %>
<%@ page import = "sips.dept.contents.Present" %>
<%@ page import = "sips.dept.menu.*" %>
<%  String formName = "frmBoard"; %>
<%
	ParamValue       parmValue = new ParamValue();      
Present            present     = new Present();
   
    ResultSetValue   rset      = null;
    ResultSetValue   rset2      = null;

    String b_mode             = StringUtil.defaultIfEmpty(request.getParameter("b_mode"      ), ""); 
	int    cpage              = (request.getParameter("cpage") == null || "".equals(request.getParameter("cpage"))) ? 0 : Integer.parseInt(request.getParameter("cpage"));   // 현재 페이지
    String searchTarget       = StringUtil.defaultIfEmpty(request.getParameter("searchTarget"),"");	      // 검색 조건 (제목/내용)
    String keyword            = StringUtil.defaultIfEmpty(request.getParameter("keyword"     ),"");        // 검색할 키워드
    String searchFlag         = StringUtil.defaultIfEmpty(request.getParameter("searchFlag"  ),"");        // 검색플래그
    String present_id         = StringUtil.defaultIfEmpty(request.getParameter("present_id"  ),"");
    String conference_id       = "";

    String name  = "";
    String manufacturer_id = "";
    String manufacturer_name    = "";
    String manufacturer_ceo    = "";
    String phone    = "";
    String present_name    = "";
    String start_date    = "";
    String end_date    = "";
    String present_image    = "";
    String contents    = "";
    String security    = "";
    String strMsg = "";
    String sel_conference_name = "";
    String sel_conference_id = "";
    String sel_manufacturer_name = "";
    String sel_manufacturer_id = "";
    String sel_manufacturer_ceo = "";
    String sel_phone = "";
    String conference_start_date = "";
    String conference_end_date = "";
    String sel_security = "";
    
    String gift_user_cd     = "";
    String gift_user_id   = "";
    String gift_user_name   = "";
    String gift_user_phone   = "";
    String gift_used_yn    = "";
  
    String modeType           = "";


    boolean isWriter       = false; // 글 작성자인가 아닌가

    parmValue.put("present_id"  , present_id  );  
    rset = present.getPresentInfo(parmValue);
	while( rset.next()){
    	present_name    = StringUtil.defaultIfEmpty(rset.getString("present_name"    ), "");
    	start_date    = StringUtil.defaultIfEmpty(rset.getString("start_date"    ), "");
    	end_date    = StringUtil.defaultIfEmpty(rset.getString("end_date"    ), "");
    	present_image    = StringUtil.defaultIfEmpty(rset.getString("present_image"    ), "");
    	contents    = StringUtil.defaultIfEmpty(rset.getString("contents"    ), "");
    	manufacturer_id    = StringUtil.defaultIfEmpty(rset.getString("manufacturer_id"    ), "");
    	manufacturer_name    = StringUtil.defaultIfEmpty(rset.getString("manufacturer_name"    ), "");
    	manufacturer_ceo    = StringUtil.defaultIfEmpty(rset.getString("manufacturer_ceo"    ), "");
    	phone    = StringUtil.defaultIfEmpty(rset.getString("phone"    ), "");
    	security    = StringUtil.defaultIfEmpty(rset.getString("security"    ), "");
    	conference_id    = StringUtil.defaultIfEmpty(rset.getString("conference_id"    ), "");
	}

    parmValue.put("conference_id"  , conference_id  );  
	rset2 = present.getPresentUserList(parmValue);
    
%>		
<script language="JavaScript" type="text/JavaScript">
<!--
    /**
     * 게시물 수정
     */
    function goModifyForm (present_id ) {        
        var frm = document.<%=formName%>;
        
        frm.present_id.value = present_id;
        frm.target="_self";
        frm.b_mode.value = "M";
        frm.action = "<%=request.getRequestURI()%>";
        frm.submit();
    }  

    /* page 이동 */
    function goList() {
        var frm = document.<%=formName%>;
        
        frm.b_mode.value = "L";
        frm.target="_self";
        frm.action = "<%=request.getRequestURI()%>";
        frm.submit();
    }


    /* 읽기 페이지로 이동 */
    function goDisplay(idx) {
        var frm = document.<%=formName%>;
        
        frm.idx.value          = idx;
        frm.b_mode.value    = "R";
        frm.target="_self";
        frm.action             = "<%= request.getRequestURI() %>";
        frm.submit();
    }

    //레이어 팝업 열기
 	function openLayer(IdName, tpos, lpos){
 		var pop = document.getElementById(IdName);
 		document.all['moveFrame'].src = './present_add_popup.jsp?conference_id=<%=conference_id%>&present_id=<%=present_id%>';
			pop.style.display = "block";
			pop.style.top = tpos + "px";
			pop.style.left = lpos + "px";
 	}

 	//레이어 팝업 닫기
 	function closeLayer(IdName){
 		var pop = document.getElementById(IdName);
 		pop.style.display = "none";
 	}
//-->
</script>
<form name="<%=formName%>" method="post">
<input type="hidden" name="cpage"        value="<%= cpage       %>">
<input type="hidden" name="b_mode"       value="<%=b_mode      %>">
<input type="hidden" name="modeType"     value="<%=modeType    %>">
<input type="hidden" name="present_id"      value="<%=present_id%>">   
<input type="hidden" name="conference_id"      value="<%=conference_id%>">   
	
    <table width="100%" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td valign="top">
           <table border="0" cellpadding="0" cellspacing="0" style="border-top:2px solid #e0e0e0; border-bottom:2px solid #e0e0e0; width:784px;">
            <tr>
              <td width="210" class="bbbg" style="padding-left:20px; font-weight:bold;">업체명</td>
              <td width="574" style="padding-left:20px;"><%=manufacturer_name%></td>
            </tr>		
            <tr>
              <td width="210" class="bbbg" style="padding-left:20px; font-weight:bold;">대표자</td>
              <td width="574" style="padding-left:20px;"><%=manufacturer_ceo%></td>
            </tr>		
            <tr>
              <td width="210" class="bbbg" style="padding-left:20px; font-weight:bold;">연락처</td>
              <td width="574" style="padding-left:20px;"><%=phone%></td>
            </tr>		
            <tr>
              <td width="210" class="bbbg" style="padding-left:20px; font-weight:bold;">보안코드</td>
              <td width="574" style="padding-left:20px;"><%=security%></td>
            </tr>		
            <tr>
              <td width="210" class="bbbg" style="padding-left:20px; font-weight:bold;">선물명</td>
              <td width="574" style="padding-left:20px;"><%=present_name%></td>
            </tr>			
            <tr>
              <td width="210" class="bbbg" style="padding-left:20px; font-weight:bold;">유효기간</td>
              <td width="574" style="padding-left:20px;"><%=start_date%> ~ <%=end_date%></td>
            </tr>		
            <tr>
              <td class="bbbg" style="padding-left:20px; font-weight:bold;">선물 이미지</td>
              <td style="padding-left:20px;"><img src="/mice/upload/present/<%=present_image %>"></td>
            </tr>
            <tr>
              <td class="bbbg" style="padding-left:20px; font-weight:bold;">내용</td>
              <td style="padding-left:20px;"><%=contents %></td>
            </tr>
            <tr>
              <td class="bbbg" style="padding-left:20px; font-weight:bold;">선물 발행 리스트</td>
              <td style="padding-left:20px;">
                <a href="#" onclick="openLayer('layerPop',170,400)"><input type="button" value="선물 사용자 추가/삭제"></a><br>
				<div class="scroll2">
                    <!--테이블 -->
                    <table width="100%" style="border:1px solid #d3d3d3; width:100%;">
                  <thead>
                    <th style="padding-top:5px;"><div align="center">이름</div></th>
                    <th style="padding-top:5px;"><div align="center">아이디</div></th>
                    <th style="padding-top:5px;"><div align="center">전화번호</div></th>
                    <th style="padding-top:5px;"><div align="center">사용여부</div></th>
              	  </thead> 
                  <tbody>
<%
	int total_num = rset2.row();
	int used_num = 0;
	int unuse_num = 0;
	if (total_num==0){ %>
                <tr><td colspan=7 align="center" height=50>:::: 선물 발행 없음 ::::</td></tr>  
<%    
    } else {
        while( rset2.next()){
        	gift_user_cd     = StringUtil.defaultIfEmpty(rset2.getString("gift_user_cd" ), "");
        	gift_user_id    = StringUtil.defaultIfEmpty(rset2.getString("gift_user_id"), "");
        	gift_user_name          = StringUtil.defaultIfEmpty(rset2.getString("gift_user_name"         ), "");
        	gift_user_phone          = StringUtil.defaultIfEmpty(rset2.getString("gift_user_phone"         ), "");
        	gift_used_yn          = StringUtil.defaultIfEmpty(rset2.getString("gift_used_yn"         ), "");
        	if ("사용".equals(gift_used_yn))
        		used_num++;
        	else
        		unuse_num++;
%>
			<tr>
				<td align="center"><%=gift_user_name%></td>
				<td align="center"><%=gift_user_id%></td>
				<td align="center"><%=gift_user_phone%></td>
				<td align="center"><%=gift_used_yn%></td>
			</tr>
<%      } 
    }
    %> 
    		<tr>
				<td align="center" colspan=4>
					지급인원: <b><%=total_num%></b>명 
					/ 사용인원: <b><%=used_num%></b>명 
					/ 미사용인원: <b><%=unuse_num%></b>명
				</td>
			</tr>
                  </tbody>
                </table>
                    <!--//테이블 -->
                </div>     
			  </td>
            </tr>
        </table></td>
      </tr>
      <tr>
        <td height="50" align="center">
					 <a href="javascript:goList();"><img src="/dhsp/images/bt_list.gif" alt="목록" /></a>
                      <a href="javascript:goModifyForm('<%= present_id %>');"><img src="<%=root_path%>/images/bt_modify.gif" alt="수정" /></a>                   
                          
        </td>
      </tr>      
    </table>
</form>		
	
<!-- 팝업 -->
<div  id="layerPop" >
 <iframe id="moveFrame" frameborder='0' height='100%' width='100%' scrolling='no' src=''></iframe>
</div>
<!--// 팝업 -->