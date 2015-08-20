<%@ page language = "java" contentType="text/html; charset=UTF-8" %>
<%
	String root_path = request.getContextPath();
%>
<%@ page import = "sips.common.lib.util.*" %>
<%@ page import = "sips.common.lib.value.*" %>
<%@ page import = "sips.dept.contents.Research" %>
<%@ page import = "sips.dept.contents.Conference" %>
<%  String formName = "frmBoard"; %>
<%
	ParamValue       parmValue = new ParamValue();      
	Research            research     = new Research();
	Conference       conference     = new Conference();
   
    ResultSetValue     rset      = null;
    ResultSetValue   rset3      = null;

    String user_cd         = (String)session.getAttribute("USER_CD");
    String user_authority         = (String)session.getAttribute("USE_AUTHORITY");
    String b_mode             = StringUtil.defaultIfEmpty(request.getParameter("b_mode"      ), ""); 
	int    cpage              = (request.getParameter("cpage") == null || "".equals(request.getParameter("cpage"))) ? 0 : Integer.parseInt(request.getParameter("cpage"));   // 현재 페이지
    String conference_id         = StringUtil.defaultIfEmpty(request.getParameter("conference_id"  ),"");

    String research_image  = "";
    String title  = "";
	String research_id = "";
    String strMsg             = "";
    String research_q_id  = "";
    String q_num  = "";
    String q_title  = "";
    String a_num   = "";
    String a_title   = "";
    String sel_conference_name = "";
    String sel_conference_id = "";

    String modeType           = "";
    int research_count           = 0;

    parmValue.put("user_cd"        , user_cd  );  
    parmValue.put("user_authority"        , user_authority  );  
    parmValue.put("conference_id"  , conference_id  );  
    rset3 = conference.getConferenceSelectList(parmValue);
    rset = research.getResearchInfo(parmValue);
    research_count = rset.row();
	
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
    function goInsertProc () { 
	    var frm = document.<%=formName%>;
	    if (frm.title.value == "") {
            alert("설문제목을 입력해주세요.");
            frm.title.focus();
            return;
        }
	    if (frm.q_title.value == "") {
            alert("질문을 입력해주세요.");
            frm.q_title.focus();
            return;
        }
	    if ($("#a_title_a").val() == "" && $("#a_title_ba").val() == "") {
            alert("답변을 두개 이상 입력해주세요.");
            return;
        }
	    
	    if( confirm("등록 하시겠습니까?")) {
	    	frm.modeType.value = "ins";
	        frm.action   = "research_write_proc.jsp";
	        frm.submit();
	    }
	}    
    function conference_change(obj)
	{
    	var frm = document.<%=formName%>;
        frm.b_mode.value = "R";
		frm.target="_self";
		frm.action             = "./research.jsp";
		frm.submit();
	}

//-->
</script>
  
    <table width="100%" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td valign="top">
           <table border="0" cellpadding="0" cellspacing="0" style="border-top:2px solid #e0e0e0; border-bottom:2px solid #e0e0e0; width:784px;">
            <form name="<%=formName%>" method="post"> 
	        <input type="hidden" name="research_id" id="research_id" value="<%=research_id %>">
			<input type="hidden" name="conference_id"      value="<%=conference_id%>">  
			<input type="hidden" name="research_count"  value="<%=research_count%>">   
			<input type="hidden" name="b_mode" >   
			<input type="hidden" name="modeType" >   
           <tr>
              <td width="210" class="bbbg" style="padding-left:20px; font-weight:bold;">컨퍼런스</td>
              <td width="574" style="padding-left:20px;">
<%
	if (research_count == 0){
%>              
              		<select name=conference_id id=conference_id onchange="conference_change(this)">
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
			
<%      	} 
   		}
%> 
          		</select>
<%
	}else{
		while( rset3.next()){
	    	sel_conference_name     = StringUtil.defaultIfEmpty(rset3.getString("name" ), "");
	    	sel_conference_id    = StringUtil.defaultIfEmpty(rset3.getString("conference_id"), "");
	    	if ( conference_id.equals(sel_conference_id) ){
	    		out.print(sel_conference_name);
    			out.print("<input type='hidden' name='conference_id'  value='"+conference_id+"' >");
	    	}
	    } 
	}
%>         		
              </td>
            </tr>
            <tr>
              <td width="210" class="bbbg" style="padding-left:20px; font-weight:bold;">설문 제목</td>
              <td width="574" style="padding-left:20px;">
	              	<input type="text" name="title" id="title" value="<%=title %>" size="40">
               </td>
            </tr>
            </tr>
            <tr>
              <td width="210" class="bbbg" style="padding-left:20px; font-weight:bold;">문제 등록</td>
              <td width="574" style="padding-left:20px;">
	              	질문 <input type="text" name="q_title" id="q_title" value="" size="40"><br>
	              	a.<input type="text" name="a_title" id="a_title_a" value="" size="40"><br>
	              	b.<input type="text" name="a_title" id="a_title_b" value="" size="40"><br>
	              	c.<input type="text" name="a_title" id="a_title_c" value="" size="40"><br>
	              	d.<input type="text" name="a_title" id="a_title_d" value="" size="40"><br>
              		e.<input type="text" name="a_title" id="a_title_e" value="" size="40">
               </td>
            </tr>
	      <tr>
	        <td height="50" align="center" colspan=2>
	        	시작 or 종료
          		<a href="javascript:goDeleteProc_<%= research_q_id %>();"><img src="<%=root_path%>/images/bt_del.gif" alt="삭제" /></a>
           		<a href="javascript:goInsertProc();"><img src="<%=root_path%>/images/bt_up.gif" alt="등록" /></a>
	        </td>
	      </tr>      
            </form>
<%           
    while( rset.next()){
    	research_q_id    = StringUtil.defaultIfEmpty(rset.getString("research_q_id"    ), "");
    	q_num    = StringUtil.defaultIfEmpty(rset.getString("q_num"    ), "");
    	q_title    = StringUtil.defaultIfEmpty(rset.getString("q_title"    ), "");
    	a_num    = StringUtil.defaultIfEmpty(rset.getString("a_num"    ), "");
    	a_title    = StringUtil.defaultIfEmpty(rset.getString("a_title"    ), "");
%>
			<tr>
              <td width="210" class="bbbg" style="padding-left:20px; font-weight:bold;"><%=q_num %>번 설문문제</td>
              <td width="574" style="padding-left:20px;">
              	<%
              	if (!"".equals(research_image)){
              	%>	
              	<script language="JavaScript" type="text/JavaScript">
				<!--
				function goModifyProc_<%= research_q_id %> () { 
				    var frm = document.frm_<%= research_q_id %>;
				    if (frm.q_title.value == "") {
			            alert("질문을 입력해주세요.");
			            frm.q_title.focus();
			            return;
			        }
				    if ($("#a_title_a").val() == "" && $("#a_title_ba").val() == "") {
			            alert("답변을 두개 이상 입력해주세요.");
			            return;
			        }
				    if( confirm("수정 하시겠습니까?")) {
				        frm.action   = "research_write_proc.jsp";
				        frm.encoding = "multipart/form-data";
				        frm.submit();
				    }
				}    
				function goDeleteProc_<%= research_q_id %> () { 
				    var frm = document.frm_<%= research_q_id %>;
				    if( confirm("삭제 하시겠습니까?")) {
				        frm.action   = "research_write_proc.jsp";
				        frm.encoding = "multipart/form-data";
				        frm.submit();
				    }
				}    
				//-->
              	</script>
              	<form name="frm_<%= research_q_id %>" method="post"> 
					<input type="hidden" name="research_id"  value="<%=research_q_id%>">
					질문 <input type="text" name="q_title" id="q_title" value="" size="40"><br>
	              	a.<input type="text" name="a_title" id="a_title_a" value="" size="40"><br>
	              	b.<input type="text" name="a_title" id="a_title_b" value="" size="40"><br>
	              	c.<input type="text" name="a_title" id="a_title_c" value="" size="40"><br>
	              	d.<input type="text" name="a_title" id="a_title_d" value="" size="40"><br>
              		e.<input type="text" name="a_title" id="a_title_e" value="" size="40"><br>
              		<a href="javascript:goModifyProc_<%= research_q_id %>();"><img src="<%=root_path%>/images/bt_modify.gif" alt="수정" /></a>
              		<a href="javascript:goDeleteProc_<%= research_q_id %>();"><img src="<%=root_path%>/images/bt_del.gif" alt="삭제" /></a>
              	</form>	    
              <%
              	}
              %>
              </td>
            </tr>	
<%    	
	}
%>    
        </table></td>
      </tr>
      <tr>
        <td height="50" align="center">
					 <a href="javascript:goList();"><img src="/dhsp/images/bt_list.gif" alt="목록" /></a>
                          
        </td>
      </tr>      
    </table>

