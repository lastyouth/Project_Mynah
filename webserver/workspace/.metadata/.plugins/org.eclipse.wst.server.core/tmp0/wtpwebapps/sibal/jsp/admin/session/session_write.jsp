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
<%  String formName = "frmBoard"; %>
<%
	ParamValue       parmValue = new ParamValue();          
	Session            _session     = new Session();
	Conference       conference     = new Conference();
    ResultSetValue   rset      = null;
    ResultSetValue   rset2      = null;
    ResultSetValue   rset3      = null;

    String user_cd         = (String)session.getAttribute("USER_CD");
    String user_authority         = (String)session.getAttribute("USE_AUTHORITY");
	String modeType           = StringUtil.defaultIfEmpty(request.getParameter("modeType"    ), "ins");
    String b_mode             = StringUtil.defaultIfEmpty(request.getParameter("b_mode"      ), ""); 
    String session_id             = StringUtil.defaultIfEmpty(request.getParameter("session_id"      ), ""); 
    String conference_id             = ""; 
    
    String title    = "";
    String conference_date    = "";
    String company    = "";
    String  reg_date    = "";
    String strMsg   = "수정";
    String conference_name = "";
    String conference_start_date = "";
    String conference_end_date = "";
    String sel_conference_name = "";
    String sel_conference_id = "";
    
    parmValue.put("session_id"    , session_id  );
    parmValue.put("user_cd"        , user_cd  );  
    parmValue.put("user_authority"        , user_authority  );  

    if( !"".equals(session_id)) {
        rset = _session.getSessionInfo(parmValue);
        while( rset.next()){
        	title    = StringUtil.defaultIfEmpty(rset.getString("title"    ), "");
        	conference_date    = StringUtil.defaultIfEmpty(rset.getString("conference_date"    ), "");
        	reg_date    = StringUtil.defaultIfEmpty(rset.getString("reg_date"    ), "");
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
        

        if (frm.title.value == "") {
            alert("세션제목을 입력하세요.");
            frm.title.focus();
            return;
        }
        if( confirm("<%=strMsg%> 하시겠습니까?")) {
            frm.action   = "session_write_proc.jsp";
            //frm.encoding = "multipart/form-data";
            frm.submit();
        }
    }
	function goModifyProc (user_cd) { 
    var frm = document.<%=formName%>;

    if (frm.title.value == "") {
        alert("세션제목을 입력하세요.");
        frm.title.focus();
        return;
    }
    if( confirm("수정 하시겠습니까?")) {
        frm.modeType.value = "mod";
        frm.action   = "session_write_proc.jsp";
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
    

    /**
     * 게시물 삭제
     */
    function goDeleteForm () { 
        var frm = document.<%=formName%>;
        if( confirm("삭제 하시겠습니까?")) {
            frm.modeType.value = "del";
            frm.action   = "session_write_proc.jsp";
            frm.submit();
        }
                
    }    
  //-->
</script>
<form name="<%=formName%>" method="post">
<input type="hidden" name="session_id" value="<%=session_id %>">  
<input type="hidden" name="b_mode"       value="<%=b_mode      %>">
<input type="hidden" name="modeType"     value="<%=modeType    %>">

<table width="100%" border="0" cellspacing="0" cellpadding="0">
      
      <tr>
        <td valign="top"><table border="0" cellpadding="0" cellspacing="0" style="border-top:2px solid #e0e0e0; border-bottom:2px solid #e0e0e0; width:784px;">
            <tr>
              <td width="210" class="bbbg" style="padding-left:20px; font-weight:bold;">컨퍼런스명</td>
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
            </tr>
            <tr>
              <td width="210" class="bbbg" style="padding-left:20px; font-weight:bold;">세션제목</td>
              <td width="574" style="padding-left:20px;"><input type="text" name="title" value="<%=title%>" size="60"></td>
            </tr>
            <%
            if (!"".equals(reg_date)){
            %>
            <tr>
              <td width="210" class="bbbg" style="padding-left:20px; font-weight:bold;">등록일</td>
              <td width="574" style="padding-left:20px;"><%=reg_date%></td>
            </tr>
            <%
            }
            %>
        </table></td>
      </tr>
      <tr>
        <td height="50" align="center">
        <% if(modeType.equals("ins")){ %>
            <a href="javascript:goRegitProc();"><img src="<%=root_path%>/images/bt_up.gif" alt="등록" /></a>
         <% }else{ %>
            <a href="javascript:goModifyProc();"><img src="<%=root_path%>/images/bt_modify.gif" alt="수정" /></a>
            <a href="javascript:goDeleteForm();"><img src="<%=root_path%>/images/bt_del.gif" alt="삭제" /></a>
         <% } %>   
            <a href="javascript:goList();"><img src="<%=root_path%>/images/bt_list.gif" alt="목록" /></a>
         </td>
      </tr>
    </table>
    
</form>		