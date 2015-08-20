<%@ page language = "java" contentType="text/html; charset=UTF-8" %>
<%
	String root_path = request.getContextPath();
%>
<%@ page import = "sips.common.lib.util.*" %>
<%@ page import = "sips.common.lib.value.*" %>
<%@ page import = "sips.dept.contents.Session" %>
<%@ page import = "sips.dept.contents.Binder" %>
<%@ page import = "sips.dept.contents.Conference" %>
<%@ page import = "sips.dept.contents.Member" %>
<%@ page import = "sips.dept.menu.*" %>
<%  String formName = "frmBoard"; %>
<%
	ParamValue       parmValue = new ParamValue();           
	Binder            binder     = new Binder();     
	Member            member     = new Member();  
	Conference            conference     = new Conference();
   
    ResultSetValue   rset      = null;
    ResultSetValue   rset2      = null;
    ResultSetValue   rset3      = null;

    String b_mode             = StringUtil.defaultIfEmpty(request.getParameter("b_mode"      ), ""); 
    String binder_id             = StringUtil.defaultIfEmpty(request.getParameter("binder_id"      ), ""); 
  
    
    		
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
    String sel_topic_title = "";
    String sel_topic_id = "";
    String sel_session_id = "";
    String sel_session_title = "";
    String sel_conference_name = "";
    String sel_conference_id = "";
    String session_id = "";
    String topic_id = "";
    String session_name = "";
    String topic_name = "";
    String user_id = "";
    String writer = "";

    parmValue.put("binder_id"    , binder_id  );
    String conference_id    = binder.getConferenceId(parmValue);
    System.out.println("conference_id------------>"+conference_id);
    parmValue.put("conference_id"    , conference_id  );
    
    rset = binder.getBniderInfo(parmValue);
    while( rset.next()){
    	binder_id    = StringUtil.defaultIfEmpty(rset.getString("binder_id"    ), "");
    	binder_title    = StringUtil.defaultIfEmpty(rset.getString("binder_title"    ), "");
    	contents    = StringUtil.defaultIfEmpty(rset.getString("contents"    ), "");
    	reg_date    = StringUtil.defaultIfEmpty(rset.getString("reg_date"    ), "");
    	attached    = StringUtil.defaultIfEmpty(rset.getString("attached"    ), "");
    	user_name    = StringUtil.defaultIfEmpty(rset.getString("user_name"    ), "");
    	writer    = StringUtil.defaultIfEmpty(rset.getString("writer"    ), "");
    	session_title    = StringUtil.defaultIfEmpty(rset.getString("session_title"    ), "");
    	topic_title    = StringUtil.defaultIfEmpty(rset.getString("topic_title"    ), "");
    	conference_name    = StringUtil.defaultIfEmpty(rset.getString("conference_name"    ), "");
    	user_id    = StringUtil.defaultIfEmpty(rset.getString("user_id"    ), "");
    }
	
	
	
	rset2 = conference.getConferenceInfo(parmValue);
    while( rset2.next()){
    	conference_name    = StringUtil.defaultIfEmpty(rset2.getString("name"    ), "");
    }  
    
%>		
<script language="JavaScript" type="text/JavaScript">
<!--
    /**
     * 게시물 수정
     */
    function goModifyForm (binder_id ) {        
        var frm = document.<%=formName%>;
        
        frm.binder_id.value = binder_id;
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

//-->
</script>
<form name="<%=formName%>" method="post">
<input type="hidden" name="b_mode"       value="<%=b_mode      %>">
<input type="hidden" name="modeType"     value="<%=modeType    %>">
<input type="hidden" name="binder_id"      value="<%=binder_id%>">   
<input type="hidden" name="conference_id"      value="<%=conference_id%>">   
	
    <table width="100%" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td valign="top">
           <table border="0" cellpadding="0" cellspacing="0" style="border-top:2px solid #e0e0e0; border-bottom:2px solid #e0e0e0; width:784px;">
            <tr>
              <td width="210" class="bbbg" style="padding-left:20px; font-weight:bold;">컨퍼런스명</td>
              <td width="574" style="padding-left:20px;"><%=conference_name%></td>
            </tr>		
            <tr>
              <td width="210" class="bbbg" style="padding-left:20px; font-weight:bold;">세션 제목</td>
              <td width="574" style="padding-left:20px;"><%=session_title%></td>
            </tr>		
            <tr>
              <td width="210" class="bbbg" style="padding-left:20px; font-weight:bold;">토픽 제목</td>
              <td width="574" style="padding-left:20px;"><%=topic_title%></td>
            </tr>		
            <tr>
              <td class="bbbg" style="padding-left:20px; font-weight:bold;">회원명(ID)</td>
              <td style="padding-left:20px;"><%=user_name%>(<%=user_id%>) </td>
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
              <td class="bbbg" style="padding-left:20px; font-weight:bold;">내용</td>
              <td style="padding-left:20px;"><%=StringUtil.newLineToBr(contents)%></td>
            </tr>
            <tr>
              <td class="bbbg" style="padding-left:20px; font-weight:bold;">다운로드</td>
              <td style="padding-left:20px;"><a href="/mice/upload/pdf/<%=attached%>"><%=attached%></a></td>
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
                      <a href="javascript:goModifyForm('<%= binder_id %>');"><img src="<%=root_path%>/images/bt_modify.gif" alt="수정" /></a>                   
                          
        </td>
      </tr>      
    </table>
</form>		
