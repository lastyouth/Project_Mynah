<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<% request.setCharacterEncoding("UTF-8"); %>
<%
	String root_path = request.getContextPath();
%>
<%@ page import = "sips.common.lib.util.*" %>
<%@ page import = "sips.common.lib.value.*" %>
<%@ page import = "sips.dept.contents.Session" %>
<%@ page import = "sips.dept.menu.*" %>
<%
	ParamValue       parmValue = new ParamValue();        
	Session            agenda     = new Session();
    ResultSetValue   rset      = null;
    /* 페이지 관련 파라미터 */
    String b_mode             = StringUtil.defaultIfEmpty(request.getParameter("b_mode"      ), ""); 
    String agenda_id             = StringUtil.defaultIfEmpty(request.getParameter("agenda_id"      ), ""); 
    
    String conference_id        = (String)session.getAttribute("CONFERENCE_ID");
    String title = "";
    String start_time    = ""; 
    String end_time    = ""; 
    String user_name    = ""; 
    String reg_date= "";
    String agenda_detail_id= "";

    DateUtil  dateUtil   = new DateUtil();

    parmValue.put("agenda_id"        , agenda_id  );  
    parmValue.put("conference_id"        , conference_id  );  

    // 목록
    rset = agenda.getSessionDetailList(parmValue);
%>
<script language="JavaScript" >
<!--
    /* 검색 */
    function goSearch() {
        board.searchFlag.value = "OK";
        board.b_mode.value  = "L";
        board.target="_self";
        board.action = "<%= request.getRequestURI() %>";
        board.submit();
    }

    /* 목록 페이지로 이동 */
    function goList() {
        board.b_mode.value       = "L";

        board.target="_self";
        board.action = "<%= request.getRequestURI() %>";
        board.submit();
    }

    /* 쓰기 페이지로 이동 */
    function goWriteForm() {
        board.agenda_detail_id.value = "";
        board.target="_self";
        board.action          = "<%= request.getRequestURI() %>";
        board.b_mode.value = "DW";
        board.submit();
    }

    /* 읽기 페이지로 이동 */
    function goDisplay(agenda_detail_id) {
        board.agenda_detail_id.value = agenda_detail_id; 
        board.b_mode.value       = "DR";
       // board.target             = "_self";
        board.action             = "<%= request.getRequestURI() %>";
        board.submit();
    }
    
    function goDelete(){
        var frm = document.board;
        var objs = frm.boxes;
        var isChk = false;
    	 for (var i=0; i<objs.length; i++){
         	if(objs[i].checked)
         		isChk = true;
         }
         if (!isChk) {
             alert("삭제할 리스트 한개 이상 선택하세요.");
             return;
         }
         
         if( confirm("선택하신 리스트를 삭제 하시겠습니까?")) {
        	 frm.modeType.value = "lDel";
             frm.action   = "session_write_proc.jsp";
             frm.submit();
         }
    }

//-->
</script>
<form name="board" method="post">  
  <input type="hidden" name="agenda_detail_id">  
  <input type="hidden" name="b_mode"       value="<%=b_mode      %>">
  <input type="hidden" name="agenda_id"       value="<%=agenda_id      %>">
  <input type="hidden" name="modeType" >
  
  <table width="100%" border="0" cellspacing="0" cellpadding="0"> 
      <tr>
        <td valign="top"><table width="100%" style="border:1px solid #d3d3d3;">
           <thead>
                    <th><div align="center">시간</div></th>
                    <th><div align="center">시간대별 세션정보</div></th>
                    <th><div align="center">등록인</div></th>
                    <th><div align="center">등록일</div></th>
            </thead>
            <tbody>
    		</tbody>
<%
    if( rset.row()==0) { %>
                <tr><td colspan=7 align="center" height=50>:::: 없음 ::::</td></tr>  
<%    
    } else {
        while( rset.next()){
        	agenda_detail_id       = StringUtil.defaultIfEmpty(rset.getString("agenda_detail_id"   ), ""); 
        	title     = StringUtil.defaultIfEmpty(rset.getString("title" ), "");
        	start_time     = StringUtil.defaultIfEmpty(rset.getString("start_time" ), "");
        	end_time     = StringUtil.defaultIfEmpty(rset.getString("end_time" ), "");
        	user_name     = StringUtil.defaultIfEmpty(rset.getString("user_name" ), "");
        	reg_date     = StringUtil.defaultIfEmpty(rset.getString("reg_date" ), "");
%>				
			<tr>
				<td align="center"><%=start_time%> ~ <%=end_time%></td>
				<td align="center">
					<a href="javascript:goDisplay('<%=agenda_detail_id%>');" class="skin">
					<%=title%>
					</a>
				</td>
				<td align="center"><%=user_name%></td>
				<td align="center"><%=reg_date%></td>
			</tr>
<%      } 
    }
    %> 
    		</tbody>					
        </table></td>
      </tr>
      <tr>
        <td><table width="100%" height="30" border="0" cellpadding="0" cellspacing="0">
            <tr>
                  <td align="right">
                  	<a href="javascript:goWriteForm();"><img src="<%=root_path%>/images/bt_up.gif" alt="등록" /></a>
                  </td>
            </tr>
        </table></td>
      </tr>
    </table>
    
    
    
		
</form> 		