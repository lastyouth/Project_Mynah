<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<% request.setCharacterEncoding("UTF-8"); %>
<%
	String root_path = request.getContextPath();
%>
<%@ page import = "sips.common.lib.util.*" %>
<%@ page import = "sips.common.lib.value.*" %>
<%@ page import = "sips.dept.contents.Member" %>
<%@ page import = "sips.dept.contents.Conference" %>
<%  String formName = "frmBoard"; %>
<%
	ParamValue       parmValue = new ParamValue();          
	Member            member     = new Member();
	Conference            conference     = new Conference();
    ResultSetValue   rset      = null;
    ResultSetValue   rset2      = null;
    ResultSetValue   rset3      = null;
    
	int    cpage              = (request.getParameter("cpage") == null || "".equals(request.getParameter("cpage"))) ? 0 : Integer.parseInt(request.getParameter("cpage"));   // 현재 페이지
    String searchTarget       = StringUtil.defaultIfEmpty(request.getParameter("searchTarget"), "");	      // 검색 조건 (제목/내용)
    String keyword            = StringUtil.defaultIfEmpty(request.getParameter("keyword"     ), "");        // 검색할 키워드
    String searchFlag         = StringUtil.defaultIfEmpty(request.getParameter("searchFlag"  ), "");        // 검색플래그
    String user_cd       = StringUtil.defaultIfEmpty(request.getParameter("user_cd"), "");
    String modeType           = StringUtil.defaultIfEmpty(request.getParameter("modeType"    ), "ins");
    String b_mode             = StringUtil.defaultIfEmpty(request.getParameter("b_mode"      ), ""); 
    
    String id     = "";
    String name    = "";
    String sex    = "";
    String company    = "";
    String  nation_id    = "";
    String title    = "";
    String picture    = "";
    String phone   = "";
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
    String question_authority    = "";
    String reg_date    = "";
    String platform    = "";
    String authority = "";
    String conference_name  = "";   
    String conference_id  = "";   
    String strMsg   = "수정";
    
    parmValue.put("user_cd"    , user_cd  );

    if( !"".equals(user_cd)) {
        rset = member.getMemberInfo(parmValue);
        while( rset.next()){
        	conference_name    = StringUtil.defaultIfEmpty(rset.getString("conference_name"    ), "");
        	conference_id    = StringUtil.defaultIfEmpty(rset.getString("conference_id"    ), "");
    	    id    = StringUtil.defaultIfEmpty(rset.getString("id"    ), "");
    	    name    = StringUtil.defaultIfEmpty(rset.getString("name"    ), "");
    	    sex    = StringUtil.defaultIfEmpty(rset.getString("_sex"    ), "");
    	    company    = StringUtil.defaultIfEmpty(rset.getString("company"    ), "");
    	    nation_id    = StringUtil.defaultIfEmpty(rset.getString("nation_id"    ), "");
    	    title    = StringUtil.defaultIfEmpty(rset.getString("title"    ), "");
    	    picture    = StringUtil.defaultIfEmpty(rset.getString("picture"    ), "");
    	    phone    = StringUtil.defaultIfEmpty(rset.getString("phone"    ), "");
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
    	    question_authority    = StringUtil.defaultIfEmpty(rset.getString("question_authority"    ), "");
    	    reg_date    = StringUtil.defaultIfEmpty(rset.getString("reg_date"    ), "");
    	    platform    = StringUtil.defaultIfEmpty(rset.getString("platform"    ), "");
    	    authority    = StringUtil.defaultIfEmpty(rset.getString("authority"    ), "");
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
    


    rset2 = conference.getConferenceList(parmValue);
    rset3 = member.getNationList(parmValue);
    
	
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
        if( confirm("<%=strMsg%> 하시겠습니까?")) {
            frm.action   = "register_write_proc.jsp";
            frm.encoding = "multipart/form-data";
            frm.submit();
        }
    }
	function goModifyProc (user_cd) { 
    var frm = document.<%=formName%>;
    if( confirm("수정 하시겠습니까?")) {
        frm.modeType.value = "mod";
        frm.action   = "register_write_proc.jsp";
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
    function getSpace(cnt) {
        var spc = "";
        for (i = 0; i < cnt; i++) {
            spc += " ";
        }        
        return spc;
    }
  //-->
</script>
<form name="<%=formName%>" method="post">
<input type="hidden" name="cpage"        value="<%= cpage       %>">
<input type="hidden" name="user_cd" value="<%=user_cd %>">  
<input type="hidden" name="b_mode"       value="<%=b_mode      %>">
<input type="hidden" name="modeType"     value="<%=modeType    %>">

<table width="100%" border="0" cellspacing="0" cellpadding="0">
      
      <tr>
        <td valign="top"><table border="0" cellpadding="0" cellspacing="0" style="border-top:2px solid #e0e0e0; border-bottom:2px solid #e0e0e0; width:784px;">
            <tr>
              <td width="210" class="bbbg" style="padding-left:20px; font-weight:bold;">컨퍼런스명</td>
              <td width="574" style="padding-left:20px;">
              	<select name="conference_id">
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
              <td width="210" class="bbbg" style="padding-left:20px; font-weight:bold;">권한</td>
              <td width="574" style="padding-left:20px;"><input type="radio" name="authority" value="admin"   <%=("admin".equals(authority)?"checked":"") %>>컨퍼런스관리자
               <input type="radio" name="authority" value="normal"   <%=("normal".equals(authority)?"checked":"") %>>일반회원</td>
            </tr>
            </tr>
            <tr>
              <td width="210" class="bbbg" style="padding-left:20px; font-weight:bold;">아이디</td>
              <td width="574" style="padding-left:20px;"><%=id%></td>
            </tr>
            <tr>
              <td width="210" class="bbbg" style="padding-left:20px; font-weight:bold;">이름</td>
              <td width="574" style="padding-left:20px;"><%=name%></td>
            </tr>
            <tr>
              <td width="210" class="bbbg" style="padding-left:20px; font-weight:bold;">성별</td>
              <td width="574" style="padding-left:20px;"><%=sex%></td>
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
                  	if (nation_id.equals(rset3.getString("nation_id")))
                  		out.println(rset3.getString("nation"    ));                		          	    
              	}
              	%>
              </td>
            </tr>
            <tr>
              <td width="210" class="bbbg" style="padding-left:20px; font-weight:bold;">직함</td>
              <td width="574" style="padding-left:20px;"><%=title%></td>
            </tr>
            <tr>
              <td width="210" class="bbbg" style="padding-left:20px; font-weight:bold;">사진</td>
              <td width="574" style="padding-left:20px;"><img src="/mice/upload/pic/<%=picture%>"></td>
            </tr>
            <tr>
              <td width="210" class="bbbg" style="padding-left:20px; font-weight:bold;">전화번호</td>
              <td width="574" style="padding-left:20px;"><%=phone%></td>
            </tr>
            <tr>
              <td width="210" class="bbbg" style="padding-left:20px; font-weight:bold;">이메일</td>
              <td width="574" style="padding-left:20px;"><%=email%></td>
            </tr>
            <tr>
              <td width="210" class="bbbg" style="padding-left:20px; font-weight:bold;">주소</td>
              <td width="574" style="padding-left:20px;"><%=address%></td>
            </tr>
            <tr>
        </table></td>
      </tr>
      <tr>
        <td height="50" align="center">
            <a href="javascript:goModifyProc();"><img src="<%=root_path%>/images/bt_modify.gif" alt="수정" /></a>
            <a href="javascript:goList();"><img src="<%=root_path%>/images/bt_list.gif" alt="목록" /></a>
         </td>
      </tr>
    </table>
    
</form>		