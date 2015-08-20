<%@ page language = "java" contentType="text/html; charset=UTF-8" %>
<%
	String root_path = request.getContextPath();
%>
<%@ page import = "sips.common.lib.util.*" %>
<%@ page import = "sips.common.lib.value.*" %>
<%@ page import = "sips.dept.contents.Member" %>
<%  String formName = "frmBoard"; %>
<%
	ParamValue       parmValue = new ParamValue();      
	Member            member     = new Member();
   
    ResultSetValue   rset      = null;
    ResultSetValue   rset2      = null;

    String b_mode             = StringUtil.defaultIfEmpty(request.getParameter("b_mode"      ), ""); 
	int    cpage              = (request.getParameter("cpage") == null || "".equals(request.getParameter("cpage"))) ? 0 : Integer.parseInt(request.getParameter("cpage"));   // 현재 페이지
    String searchTarget       = StringUtil.defaultIfEmpty(request.getParameter("searchTarget"),"");	      // 검색 조건 (제목/내용)
    String keyword            = StringUtil.defaultIfEmpty(request.getParameter("keyword"     ),"");        // 검색할 키워드
    String searchFlag         = StringUtil.defaultIfEmpty(request.getParameter("searchFlag"  ),"");        // 검색플래그
    String user_cd         = StringUtil.defaultIfEmpty(request.getParameter("user_cd"  ),"");
    String id     = "";
    String name    = "";
    String sex    = "";
    String company    = "";
    String  nation    = "";
    String title    = "";
    String picture    = "";
    String phone1   = "";
    String phone2  = "";
    String phone3   = "";
    String handphone1   = "";
    String handphone2   = "";
    String handphone3   = "";
    String first_phone   = "";
    String email    = "";
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
    String research_authoriry    = "";
    String question_authority    = "";
    String reg_date    = "";
    String platform    = "";
    String authority = "";
    String conference_name  = "";   
    String appellation_name  = "";   
    String street_address = "";
    String street_address_detail = "";
    String city = "";
    String state = "";
    String postal_code = "";
    String qr_image = "";
  
    String modeType           = "";
    String strMsg             = "";


    boolean isWriter       = false; // 글 작성자인가 아닌가
    
    parmValue.put("user_cd"  , user_cd  );  
    rset = member.getMemberInfo(parmValue);
	while( rset.next()){
		conference_name    = StringUtil.defaultIfEmpty(rset.getString("conference_name"    ), "");
	    id    = StringUtil.defaultIfEmpty(rset.getString("id"    ), "");
	    name    = StringUtil.defaultIfEmpty(rset.getString("name"    ), "");
	    sex    = StringUtil.defaultIfEmpty(rset.getString("sex"    ), "");
	    company    = StringUtil.defaultIfEmpty(rset.getString("company"    ), "");
	    nation    = StringUtil.defaultIfEmpty(rset.getString("nation"    ), "");
	    title    = StringUtil.defaultIfEmpty(rset.getString("title"    ), "");
	    picture    = StringUtil.defaultIfEmpty(rset.getString("picture"    ), "");
	    phone1    = StringUtil.defaultIfEmpty(rset.getString("phone1"    ), "");
	    phone2    = StringUtil.defaultIfEmpty(rset.getString("phone2"    ), "");
	    phone3    = StringUtil.defaultIfEmpty(rset.getString("phone3"    ), "");
	    handphone1    = StringUtil.defaultIfEmpty(rset.getString("handphone1"    ), "");
	    handphone2    = StringUtil.defaultIfEmpty(rset.getString("handphone2"    ), "");
	    handphone3    = StringUtil.defaultIfEmpty(rset.getString("handphone3"    ), "");
	    first_phone    = StringUtil.defaultIfEmpty(rset.getString("first_phone"    ), "");
	    email    = StringUtil.defaultIfEmpty(rset.getString("email"    ), "");
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
	    authority    = StringUtil.defaultIfEmpty(rset.getString("authority"    ), "");
	    appellation_name    = StringUtil.defaultIfEmpty(rset.getString("appellation_name"    ), "");
	    street_address    = StringUtil.defaultIfEmpty(rset.getString("street_address"    ), "");
	    street_address_detail    = StringUtil.defaultIfEmpty(rset.getString("street_address_detail"    ), "");
	    city    = StringUtil.defaultIfEmpty(rset.getString("city"    ), "");
	    state    = StringUtil.defaultIfEmpty(rset.getString("state"    ), "");
	    postal_code    = StringUtil.defaultIfEmpty(rset.getString("postal_code"    ), "");
	    qr_image    = StringUtil.defaultIfEmpty(rset.getString("qr_image"    ), "");
	}
%>		
<script language="JavaScript" type="text/JavaScript">
<!--
    /**
     * 게시물 수정
     */
    function goModifyForm (user_cd ) {        
        var frm = document.<%=formName%>;
        
        frm.user_cd.value = user_cd;
        frm.target="_self";
        frm.b_mode.value = "M";
        frm.action = "<%=request.getRequestURI()%>";
        frm.submit();
    }    
    /**
     * 게시물 삭제
     */
    function goDeleteForm (user_cd) { 
        var frm = document.<%=formName%>;

        if( confirm("삭제 하시겠습니까?")) {
            frm.user_cd.value = user_cd;
            frm.modeType.value = "del";
            frm.action   = "member_write_proc.jsp";
            frm.submit();
        }
                
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

//-->
</script>
<form name="<%=formName%>" method="post">
<input type="hidden" name="cpage"        value="<%= cpage       %>">
<input type="hidden" name="b_mode"       value="<%=b_mode      %>">
<input type="hidden" name="modeType"     value="<%=modeType    %>">
<input type="hidden" name="user_cd"      value="<%=user_cd%>">   
	
    <table width="100%" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td valign="top">
           <table border="0" cellpadding="0" cellspacing="0" style="border-top:2px solid #e0e0e0; border-bottom:2px solid #e0e0e0; width:784px;">
            <tr>
              <td width="210" class="bbbg" style="padding-left:20px; font-weight:bold;">컨퍼런스</td>
              <td width="574" style="padding-left:20px;"><%=conference_name%></td>
            </tr>		
            <tr>
              <td class="bbbg" style="padding-left:20px; font-weight:bold;">권한</td>
              <td style="padding-left:20px;"><%=authority%></td>
            </tr>		
            <tr>
              <td class="bbbg" style="padding-left:20px; font-weight:bold;">아이디</td>
              <td style="padding-left:20px;"><%=id%></td>
            </tr>
            <tr>
              <td class="bbbg" style="padding-left:20px; font-weight:bold;">이름</td>
              <td style="padding-left:20px;"><%=name %></td>
            </tr>
            <tr>
              <td class="bbbg" style="padding-left:20px; font-weight:bold;">성별</td>
              <td style="padding-left:20px;"><%=sex%></td>
            </tr>
            <tr>
              <td class="bbbg" style="padding-left:20px; font-weight:bold;">소속</td>
              <td style="padding-left:20px;"><%=company%></td>
            </tr>
            <tr>
              <td class="bbbg" style="padding-left:20px; font-weight:bold;">직책</td>
              <td style="padding-left:20px;"><%=appellation_name%></td>
            </tr>
            <tr>
              <td class="bbbg" style="padding-left:20px; font-weight:bold;">사진</td>
              <td style="padding-left:20px;">
              	<%
              	if (!"".equals(picture)){
              		out.println("<img src=/mice/upload/member/"+picture+"><br>");
              	}
              %>
              </td>
            </tr>
            <tr>
              <td class="bbbg" style="padding-left:20px; font-weight:bold;">핸드폰</td>
              <td style="padding-left:20px;"><%=handphone1%>-<%=handphone2%>-<%=handphone3%> <%="h".equals(first_phone)? "대표번호" : ""%></td>
            </tr>
            <tr>
              <td class="bbbg" style="padding-left:20px; font-weight:bold;">전화번호</td>
              <td style="padding-left:20px;"><%=phone1%>-<%=phone2%>-<%=phone3%> <%="p".equals(first_phone)? "대표번호" : ""%></td>
            </tr>
            <tr>
              <td class="bbbg" style="padding-left:20px; font-weight:bold;">이메일</td>
              <td style="padding-left:20px;"><%=email%></td>
            </tr>
            <tr>
              <td class="bbbg" style="padding-left:20px; font-weight:bold;">국가</td>
              <td style="padding-left:20px;"><%=nation%></td>
            </tr>
            <tr>
              <td width="210" class="bbbg" style="padding-left:20px; font-weight:bold;">Street address</td>
              <td width="574" style="padding-left:20px;">
              		<%=street_address%><br>
              		<%=street_address_detail%>
              </td>
            </tr>
            <tr>
              <td width="210" class="bbbg" style="padding-left:20px; font-weight:bold;">City</td>
              <td width="574" style="padding-left:20px;">
              		<%=city%>
              </td>
            </tr>
            <tr>
              <td width="210" class="bbbg" style="padding-left:20px; font-weight:bold;">State</td>
              <td width="574" style="padding-left:20px;">
              		<%=state%>
              </td>
            </tr>
            <tr>
              <td width="210" class="bbbg" style="padding-left:20px; font-weight:bold;">Postal Code</td>
              <td width="574" style="padding-left:20px;">
              		<%=postal_code%>
              </td>
            </tr>
            <tr>
              <td class="bbbg" style="padding-left:20px; font-weight:bold;">QR 코드</td>
              <td style="padding-left:20px;">
              	<%
              	if (!"".equals(qr_image)){
              		out.println("<img src=/mice/upload/qr/"+qr_image+"><br>");
              	}
              %>
              </td>
            </tr>
            <tr>
              <td class="bbbg" style="padding-left:20px; font-weight:bold;">메뉴권한</td>
              <td style="padding-left:20px;"><%=authoriry_1%>, <%=authoriry_2%>, <%=authoriry_3%>, <%=authoriry_4%>, <%=authoriry_5%><br> <%=authoriry_6%>, <%=authoriry_7%>, <%=authoriry_8%>, <%=authoriry_9%></td>
            </tr>
            <tr>
              <td class="bbbg" style="padding-left:20px; font-weight:bold;">설문조사 권한</td>
              <td style="padding-left:20px;"><%=("n".equals(research_authoriry)? "비노출":"노출")%></td>
            </tr>
            <tr>
              <td class="bbbg" style="padding-left:20px; font-weight:bold;">개인정보 보호</td>
              <td style="padding-left:20px;"><%=("n".equals(question_authority)? "비노출":"노출")%></td>
            </tr>
            <tr>
              <td class="bbbg" style="padding-left:20px; font-weight:bold;">가입일</td>
              <td style="padding-left:20px;"><%=reg_date%></td>
            </tr>
            <tr>
              <td class="bbbg" style="padding-left:20px; font-weight:bold;">모바일OS</td>
              <td style="padding-left:20px;"><%=platform%></td>
            </tr>
        </table></td>
      </tr>
      <tr>
        <td height="50" align="center">
					 <a href="javascript:goList();"><img src="/dhsp/images/bt_list.gif" alt="목록" /></a>
                      <a href="javascript:goModifyForm('<%= user_cd %>');"><img src="<%=root_path%>/images/bt_modify.gif" alt="수정" /></a>                   
                      <a href="javascript:goDeleteForm('<%= user_cd %>');"><img src="<%=root_path%>/images/bt_del.gif" alt="삭제" /></a>    
        </td>
      </tr>      
    </table>

   
</form>		
