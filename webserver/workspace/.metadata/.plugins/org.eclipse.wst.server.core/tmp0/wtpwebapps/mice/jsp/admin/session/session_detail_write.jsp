<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<% request.setCharacterEncoding("UTF-8"); %>
<%
	String root_path = request.getContextPath();
%>
<%@ page import = "java.util.*" %>
<%@ page import = "java.text.*" %>
<%@ page import = "sips.common.lib.util.*" %>
<%@ page import = "sips.common.lib.value.*" %>
<%@ page import = "sips.dept.contents.Session" %>
<%@ page import = "sips.dept.contents.Conference" %>
<%@ page import = "sips.dept.contents.Member" %>
<%  String formName = "frmBoard"; %>
<%
	ParamValue       parmValue = new ParamValue();          
	Session            agenda     = new Session();     
	Member            member     = new Member();
	Conference            conference     = new Conference();
    ResultSetValue   rset      = null;
    ResultSetValue   rset2      = null;
    ResultSetValue   rset3      = null;
    
	String modeType           = StringUtil.defaultIfEmpty(request.getParameter("modeType"    ), "ins");
    String b_mode             = StringUtil.defaultIfEmpty(request.getParameter("b_mode"      ), ""); 
    String agenda_id             = StringUtil.defaultIfEmpty(request.getParameter("agenda_id"      ), ""); 
    String agenda_detail_id             = StringUtil.defaultIfEmpty(request.getParameter("agenda_detail_id"      ), ""); 
    String conference_id        = (String)session.getAttribute("CONFERENCE_ID");
    
    String conference_name    = "";
    String agenda_title    = "";
    String agenda_detail_title    = "";
    String  summary    = "";
    String  writer    = "";
    String  presenter    = "";
    String  update_no    = "";
    String  reg_date    = "";
    String  pdf_url    = "";
    String  start_time    = "";
    String  end_time    = "";
    String  user_cd    = "";
    String strMsg   = "수정";
    
    parmValue.put("conference_id"    , conference_id  );
    parmValue.put("agenda_id"    , agenda_id  );
    parmValue.put("agenda_detail_id"    , agenda_detail_id  );

    if( !"".equals(agenda_detail_id)) {
        rset = agenda.getSessionDetailInfo(parmValue);
        while( rset.next()){
        	agenda_detail_title    = StringUtil.defaultIfEmpty(rset.getString("agenda_detail_title"    ), "");
        	summary    = StringUtil.defaultIfEmpty(rset.getString("summary"    ), "");
        	writer    = StringUtil.defaultIfEmpty(rset.getString("writer"    ), "");
        	presenter    = StringUtil.defaultIfEmpty(rset.getString("presenter"    ), "");
        	update_no    = StringUtil.defaultIfEmpty(rset.getString("update_no"    ), "");
        	start_time    = StringUtil.defaultIfEmpty(rset.getString("start_time"    ), "");
        	end_time    = StringUtil.defaultIfEmpty(rset.getString("end_time"    ), "");
        	pdf_url    = StringUtil.defaultIfEmpty(rset.getString("pdf_url"    ), "");
        	user_cd    = StringUtil.defaultIfEmpty(rset.getString("user_cd"    ), "");
        	reg_date    = StringUtil.defaultIfEmpty(rset.getString("reg_date"    ), "");
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
    
    rset2 = agenda.getSessionInfo(parmValue);
    while( rset2.next()){
    	conference_name    = StringUtil.defaultIfEmpty(rset2.getString("conference_name"    ), "");
    	agenda_title    = StringUtil.defaultIfEmpty(rset2.getString("title"    ), "");
    }  
    
    rset3 = member.getSessionMemberList(parmValue);
    
	
%>		
<script language="JavaScript" type="text/JavaScript">
<!--
    /**
     * 게시물 등록
     */
    function goRegitProc () {
        var frm = document.<%=formName%>;
        

        if (frm.title.value == "") {
            alert("세션제목을 입력하세요.");
            frm.title.focus();
            return;
        }if (frm.user_cd.value == "0") {
            alert("세션을 등록할 회원이 없습니다.");
            frm.user_cd.focus();
            return;
        }
        if( confirm("<%=strMsg%> 하시겠습니까?")) {
            frm.action   = "session_detail_write_proc.jsp";
            frm.encoding = "multipart/form-data";
            frm.submit();
        }
    }
	function goModifyProc () { 
    var frm = document.<%=formName%>;
    if( confirm("수정 하시겠습니까?")) {
        frm.modeType.value = "mod";
        frm.action   = "session_detail_write_proc.jsp";
        frm.encoding = "multipart/form-data";
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
<input type="hidden" name="agenda_detail_id" value="<%=agenda_detail_id %>">  
<input type="hidden" name="agenda_id" value="<%=agenda_id %>">  
<input type="hidden" name="b_mode"       value="<%=b_mode      %>">
<input type="hidden" name="modeType"     value="<%=modeType    %>">

<table width="100%" border="0" cellspacing="0" cellpadding="0">
      
      <tr>
        <td valign="top"><table border="0" cellpadding="0" cellspacing="0" style="border-top:2px solid #e0e0e0; border-bottom:2px solid #e0e0e0; width:784px;">
            <tr>
              <td width="210" class="bbbg" style="padding-left:20px; font-weight:bold;">컨퍼런스명</td>
              <td width="574" style="padding-left:20px;"><%=conference_name %></td>
            </tr>
            <tr>
              <td width="210" class="bbbg" style="padding-left:20px; font-weight:bold;">세션 제목</td>
              <td width="574" style="padding-left:20px;"><%=agenda_title %></td>
            </tr>
            <tr>
              <td width="210" class="bbbg" style="padding-left:20px; font-weight:bold;">회원명(ID)</td>
              <td width="574" style="padding-left:20px;">
              	<select name="user_cd">
              <%
              String selected = "";
              if( rset3.row()==0) { 
            	  out.println("<option value='0'>세션 등록할 회원이 없습니다.</option>");  
              }else{
	              while( rset3.next()){
	            	  if (user_cd.equals(rset3.getString("user_cd"))){
	                		selected = "selected";
	                	}else{
	                		selected = "";
	                	}
	            		out.println("<option value="+rset3.getString("user_cd"    )+"  "+selected+">"+rset3.getString("name"    )+"("+rset3.getString("id"    )+")</option>");    
	              }
              }
				%>
				</select>
              </td>
            </tr>
            <tr>
              <td width="210" class="bbbg" style="padding-left:20px; font-weight:bold;">시간대별 세션제목</td>
              <td width="574" style="padding-left:20px;"><input type="text" name="agenda_detail_title" value="<%=agenda_detail_title %>"></td>
            </tr>
            </tr>
            <tr>
              <td width="210" class="bbbg" style="padding-left:20px; font-weight:bold;">일정</td>
              <td width="574" style="padding-left:20px;">
              	<input type="text" name="start_time" value="<%=start_time%>" size="2" maxlength=2>
              	~
              	<input type="text" name="end_time" value="<%=end_time%>" size="2" maxlength=2>
              </td>
            </tr>
            <tr>
              <td width="210" class="bbbg" style="padding-left:20px; font-weight:bold;">저자</td>
              <td width="574" style="padding-left:20px;"><input type="text" name="writer" value="<%=writer%>"></td>
            </tr>
            <tr>
              <td width="210" class="bbbg" style="padding-left:20px; font-weight:bold;">발표자</td>
              <td width="574" style="padding-left:20px;"><input type="text" name="presenter" value="<%=presenter%>"></td>
            </tr>
            <tr>
              <td width="210" class="bbbg" style="padding-left:20px; font-weight:bold;">써머리</td>
              <td width="574" style="padding-left:20px;">
					<textarea name="summary" ><%=summary %></textarea>
			  </td>
            </tr>
            <tr>
              <td width="210" class="bbbg" style="padding-left:20px; font-weight:bold;">다운로드</td>
              <td width="574" style="padding-left:20px;">
			  		<%=pdf_url%><br>
			  		<input type="file" name="pdf_url">
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
         <% } %>   
            <a href="javascript:goList();"><img src="<%=root_path%>/images/bt_list.gif" alt="목록" /></a>
         </td>
      </tr>
    </table>
    
</form>		