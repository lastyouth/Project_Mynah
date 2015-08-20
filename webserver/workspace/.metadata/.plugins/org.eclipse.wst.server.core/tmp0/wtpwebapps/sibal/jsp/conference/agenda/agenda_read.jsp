<%@ page language = "java" contentType="text/html; charset=UTF-8" %>
<%
	String root_path = request.getContextPath();
%>
<%@ page import = "sips.common.lib.util.*" %>
<%@ page import = "sips.common.lib.value.*" %>
<%@ page import = "sips.dept.contents.Session" %>
<%@ page import = "sips.dept.contents.Agenda" %>
<%@ page import = "sips.dept.contents.Conference" %>
<%@ page import = "sips.dept.contents.Member" %>
<%@ page import = "sips.dept.menu.*" %>
<%  String formName = "frmBoard"; %>
<%
	ParamValue       parmValue = new ParamValue();           
	Agenda            agenda     = new Agenda();     
	Member            member     = new Member();  
	Conference            conference     = new Conference();
   
    ResultSetValue   rset      = null;
    ResultSetValue   rset2      = null;
    ResultSetValue   rset3      = null;

    String b_mode             = StringUtil.defaultIfEmpty(request.getParameter("b_mode"      ), ""); 
    String agenda_id             = StringUtil.defaultIfEmpty(request.getParameter("agenda_id"      ), ""); 
  
    
    		
    String modeType           = "";

    String binder_title    = "";
    String contents    = "";
    String  reg_date    = "";
    String  attached    = "";
    String  user_name    = "";
    String  session_title    = "";
    String  topic_title    = "";
    String  conference_name    = "";
    String strMsg   = "수정";
    String agenda_register = "";
    String result = "비발표자";
    String conference_date = "";
    String presenter = "";
    String summary = "";
    String start_time = "";
    String end_time = "";
    String worker = "";
    String session_name = "";
    String topic_name = "";
    String user_id = "";
    String writer = "";

    parmValue.put("agenda_id"    , agenda_id  );
    String conference_id    = agenda.getConferenceId(parmValue);
    System.out.println("conference_id------------>"+conference_id);
    parmValue.put("conference_id"    , conference_id  );
    
    rset = agenda.getAgendaInfo(parmValue);
    while( rset.next()){
    	conference_date    = StringUtil.defaultIfEmpty(rset.getString("conference_date"    ), "");
    	presenter    = StringUtil.defaultIfEmpty(rset.getString("presenter"    ), "");
    	summary    = StringUtil.defaultIfEmpty(rset.getString("summary"    ), "");
    	start_time    = StringUtil.defaultIfEmpty(rset.getString("start_time"    ), "");
    	end_time    = StringUtil.defaultIfEmpty(rset.getString("end_time"    ), "");
    	user_id    = StringUtil.defaultIfEmpty(rset.getString("user_id"    ), "");
    	reg_date    = StringUtil.defaultIfEmpty(rset.getString("reg_date"    ), "");
    	user_name    = StringUtil.defaultIfEmpty(rset.getString("user_name"    ), "");
    	session_title    = StringUtil.defaultIfEmpty(rset.getString("session_title"    ), "");
    	topic_title    = StringUtil.defaultIfEmpty(rset.getString("topic_title"    ), "");
    	conference_name    = StringUtil.defaultIfEmpty(rset.getString("conference_name"    ), "");
    	writer    = StringUtil.defaultIfEmpty(rset.getString("writer"    ), "");
    	binder_title    = StringUtil.defaultIfEmpty(rset.getString("binder_title"    ), "");
    	worker    = StringUtil.defaultIfEmpty(rset.getString("worker"    ), "");
    }
	
	
	
	rset2 = conference.getConferenceInfo(parmValue);
    while( rset2.next()){
    	conference_name    = StringUtil.defaultIfEmpty(rset2.getString("name"    ), "");
    }  
    
%>		
<script language="JavaScript" type="text/JavaScript">
<!--

    /* page 이동 */
    function goList() {
        var frm = document.<%=formName%>;
        
        frm.b_mode.value = "L";
        frm.target="_self";
        frm.action = "<%=request.getRequestURI()%>";
        frm.submit();
    }

//-->
</script>
<form name="<%=formName%>" method="post">
<input type="hidden" name="b_mode"       value="<%=b_mode      %>">
<input type="hidden" name="modeType"     value="<%=modeType    %>">
<input type="hidden" name="agenda_id"      value="<%=agenda_id%>">   
<input type="hidden" name="conference_id"      value="<%=conference_id%>">   
	
    <table width="100%" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td valign="top">
           <table border="0" cellpadding="0" cellspacing="0" style="border-top:2px solid #e0e0e0; border-bottom:2px solid #e0e0e0; width:784px;">
            <tr>
              <td width="210" class="bbbg" style="padding-left:20px; font-weight:bold;">회원명</td>
              <td style="padding-left:20px;"><%=user_name%></td>
            </tr>	
            <tr>
              <td class="bbbg" style="padding-left:20px; font-weight:bold;">제목</td>
              <td style="padding-left:20px;"><%=binder_title%></td>
            </tr>
            <tr>
              <td class="bbbg" style="padding-left:20px; font-weight:bold;">저자</td>
              <td style="padding-left:20px;"><%=writer%></td>
            </tr>
            <tr>
              <td class="bbbg" style="padding-left:20px; font-weight:bold;">컨퍼런스 발표일</td>
              <td style="padding-left:20px;"><%=conference_date%></td>
            </tr>
            <tr>
              <td class="bbbg" style="padding-left:20px; font-weight:bold;">컨퍼런스 발표시간</td>
              <td style="padding-left:20px;">
					<%= !"".equals(start_time)?start_time.substring(0,2):""%>:<%= !"".equals(start_time)?start_time.substring(2,4):""%> ~  <%= !"".equals(end_time)?end_time.substring(0,2):""%>:<%= !"".equals(end_time)?end_time.substring(2,4):""%>
				</td>
            </tr>
            <tr>
              <td class="bbbg" style="padding-left:20px; font-weight:bold;">발표자</td>
              <td style="padding-left:20px;"><%=presenter%></td>
            </tr>
            <tr>
              <td class="bbbg" style="padding-left:20px; font-weight:bold;">내용 및 요약</td>
              <td style="padding-left:20px;"><%=StringUtil.newLineToBr(summary)%></td>
            </tr>
        </table></td>
      </tr>
      <tr>
        <td height="50" align="center">
					 <a href="javascript:goList();"><img src="/dhsp/images/bt_list.gif" alt="목록" /></a>
        </td>
      </tr>      
    </table>
</form>		
