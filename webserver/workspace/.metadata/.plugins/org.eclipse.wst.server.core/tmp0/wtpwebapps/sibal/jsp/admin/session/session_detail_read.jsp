<%@ page language = "java" contentType="text/html; charset=UTF-8" %>
<%
	String root_path = request.getContextPath();
%>
<%@ page import = "sips.common.lib.util.*" %>
<%@ page import = "sips.common.lib.value.*" %>
<%@ page import = "sips.dept.contents.Session" %>
<%@ page import = "sips.dept.menu.*" %>
<%  String formName = "frmBoard"; %>
<%
	ParamValue       parmValue = new ParamValue();      
	Session            agenda     = new Session();
   
    ResultSetValue   rset      = null;
    ResultSetValue   rset2      = null;
    ResultSetValue   rset3      = null;

    String b_mode             = StringUtil.defaultIfEmpty(request.getParameter("b_mode"      ), ""); 
    String agenda_id             = StringUtil.defaultIfEmpty(request.getParameter("agenda_id"      ), ""); 
    String agenda_detail_id         = StringUtil.defaultIfEmpty(request.getParameter("agenda_detail_id"  ),"");
    String conference_id         = (String)session.getAttribute("CONFERENCE_ID");
  
    String modeType           = "";

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
    String  conference_date    = "";

    
    parmValue.put("conference_id"    , conference_id  );
    parmValue.put("agenda_id"    , agenda_id  );
    parmValue.put("agenda_detail_id"    , agenda_detail_id  );
    
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
	
	rset2 = agenda.getSessionInfo(parmValue);
    while( rset2.next()){
    	conference_name    = StringUtil.defaultIfEmpty(rset2.getString("conference_name"    ), "");
    	agenda_title    = StringUtil.defaultIfEmpty(rset2.getString("title"    ), "");
    	conference_date    = StringUtil.defaultIfEmpty(rset2.getString("conference_date"    ), "");
    }  
    
%>		
<script language="JavaScript" type="text/JavaScript">
<!--
    /**
     * 게시물 수정
     */
    function goModifyForm (agenda_detail_id ) {        
        var frm = document.<%=formName%>;
        
        frm.agenda_detail_id.value = agenda_detail_id;
        frm.target="_self";
        frm.b_mode.value = "DM";
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

//-->
</script>
<form name="<%=formName%>" method="post">
<input type="hidden" name="b_mode"       value="<%=b_mode      %>">
<input type="hidden" name="modeType"     value="<%=modeType    %>">
<input type="hidden" name="agenda_detail_id"      value="<%=agenda_detail_id%>">   
<input type="hidden" name="agenda_id"      value="<%=agenda_id%>">   
	
    <table width="100%" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td valign="top">
           <table border="0" cellpadding="0" cellspacing="0" style="border-top:2px solid #e0e0e0; border-bottom:2px solid #e0e0e0; width:784px;">
            <tr>
              <td width="210" class="bbbg" style="padding-left:20px; font-weight:bold;">컨퍼런스명</td>
              <td width="574" style="padding-left:20px;"><%=conference_name%></td>
            </tr>		
            <tr>
              <td class="bbbg" style="padding-left:20px; font-weight:bold;">세션 제목</td>
              <td style="padding-left:20px;"><%=agenda_title%></td>
            </tr>
            <tr>
              <td class="bbbg" style="padding-left:20px; font-weight:bold;">회원명(ID)</td>
              <td style="padding-left:20px;"><%=user_cd%></td>
            </tr>
            <tr>
              <td class="bbbg" style="padding-left:20px; font-weight:bold;">시간대별 세션제목</td>
              <td style="padding-left:20px;"><%=agenda_detail_title%></td>
            </tr>
            <tr>
              <td class="bbbg" style="padding-left:20px; font-weight:bold;">일정</td>
              <td style="padding-left:20px;"><%=conference_date%>일 <%=start_time%>시~<%=end_time%>시</td>
            </tr>
            <tr>
              <td class="bbbg" style="padding-left:20px; font-weight:bold;">저자</td>
              <td style="padding-left:20px;"><%=writer%></td>
            </tr>
            <tr>
              <td class="bbbg" style="padding-left:20px; font-weight:bold;">발표자</td>
              <td style="padding-left:20px;"><%=presenter%></td>
            </tr>
            <tr>
              <td class="bbbg" style="padding-left:20px; font-weight:bold;">써머리</td>
              <td style="padding-left:20px;"><%=summary%></td>
            </tr>
            <tr>
              <td class="bbbg" style="padding-left:20px; font-weight:bold;">다운로드</td>
              <td style="padding-left:20px;"><a href="/mice/upload/pdf/<%=pdf_url%>"><%=pdf_url%></a></td>
            </tr>
            <tr>
              <td class="bbbg" style="padding-left:20px; font-weight:bold;">등록일</td>
              <td style="padding-left:20px;"><%=reg_date%></td>
            </tr>
        </table></td>
      </tr>
      <tr>
        <td height="50" align="center">
					 <a href="javascript:goList();"><img src="/dhsp/images/bt_list.gif" alt="목록" /></a>
                      <a href="javascript:goModifyForm('<%= agenda_detail_id %>');"><img src="<%=root_path%>/images/bt_modify.gif" alt="수정" /></a>                   
                          
        </td>
      </tr>      
    </table>
</form>		
