<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<% request.setCharacterEncoding("UTF-8"); %>
<%
	String root_path = request.getContextPath();
%>
<%@ page import = "sips.common.lib.util.*" %>
<%@ page import = "sips.common.lib.value.*" %>
<%@ page import = "sips.dept.contents.Agenda" %>
<%@ page import = "sips.dept.contents.Session" %>
<%@ page import = "sips.dept.contents.Topic" %>
<%@ page import = "sips.dept.contents.Conference" %>
<%
	ParamValue       parmValue = new ParamValue();        
	Agenda            agenda     = new Agenda();  
	Session            _session     = new Session();
	Topic            topic     = new Topic();
	Conference        conference     = new Conference();
    ResultSetValue   rset      = null;
    ResultSetValue   rset2      = null;
    ResultSetValue   rset3      = null;
    ResultSetValue   rset4      = null;
    
    /* 페이지 관련 파라미터 */
    String b_mode             = StringUtil.defaultIfEmpty(request.getParameter("b_mode"      ), ""); 
    String old_conference_id       = StringUtil.defaultIfEmpty(request.getParameter("conference_id"),"0");   
    String session_id       = StringUtil.defaultIfEmpty(request.getParameter("session_id"),"0");     
    String topic_id       = StringUtil.defaultIfEmpty(request.getParameter("topic_id"),"0");   
    
    String user_cd         = (String)session.getAttribute("USER_CD");
    String user_authority         = (String)session.getAttribute("USE_AUTHORITY");
    int    totalCnt  = 0;                        // 총 게시물 갯수
	int    cpage     = (request.getParameter("cpage") == null || "".equals(request.getParameter("cpage"))) ? 0 : Integer.parseInt(request.getParameter("cpage"));   // 현재 페이지
    int    pageSize  = 10;                       // 한 페이지에 들어갈 게시물 갯수
	int    pageTotal = 0;                        // 총 페이지수
	int    listIndex = 0;
	
    String agenda_id             = ""; 

    String binder_title = "";
    String worker_name = "";
    String sel_session_id = "";
    String sel_session_title = "";
    String sel_conference_name = "";
    String sel_conference_id = "";
    String sel_topic_title = "";
    String sel_topic_id = "";
    String conference_id = "";
    String conference_name = "";
    String session_title = "";
    String topic_title = "";
    String title = "";
    String conference_date    = ""; 
    String reg_date= "";
    String binder_id= "";
    String end_time = "";
    String start_time = "";
    String writer = "";
    String presenter = "";

    DateUtil  dateUtil   = new DateUtil();

    parmValue.put("CURRENT_PAGE"        , cpage         );       // 페이징에 꼭 필요
    parmValue.put("lineCnt"             , pageSize      );      
    parmValue.put("conference_id"        , old_conference_id  );  
    parmValue.put("session_id"        , session_id  );  
    parmValue.put("topic_id"        , topic_id  );  
    
 // 컨퍼런스 셀렉트 리스트
    parmValue.put("user_cd"        , user_cd  );  
    parmValue.put("user_authority"        , user_authority  );  
    rset2 = conference.getConferenceSelectList(parmValue);

    //세션 셀렉트 리스트
    rset3 = _session.getSessionSelectList(parmValue);
    //토픽 셀렉트 리스트
    rset4 = topic.getTopicSelectList(parmValue);

    // 목록
    rset = agenda.getAgendaList(parmValue);
    
    /*============= 2. 페이징 관련 셋팅 ================*/
    totalCnt  = agenda.getAgendaTotalRow(parmValue);          // 총 게시물 갯수
	pageTotal = ((totalCnt-1)/pageSize) ;               // 총 페이지수
	listIndex = totalCnt - (pageSize * (cpage));
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
        board.agenda_id.value = "";
        board.target="_self";
        board.action          = "<%= request.getRequestURI() %>";
        board.b_mode.value = "W";
        board.submit();
    }

    /* 읽기 페이지로 이동 */
    function goDisplay(agenda_id) {
        board.agenda_id.value = agenda_id; 
        board.b_mode.value       = "R";
       // board.target             = "_self";
        board.action             = "<%= request.getRequestURI() %>";
        board.submit();
    }

    /* page 이동 */
    function goPage( page ) {
        board.cpage.value     = page;
        board.b_mode.value = "L";
        board.target="_self";
        board.action          = "<%= request.getRequestURI() %>";
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
             frm.action   = "agenda_write_proc.jsp";
             frm.submit();
         }
    }
    
    function conference_change(obj)
	{
        var frm = document.board;
		//var obj_value = conference_id.value;
		//alert(obj_value);
		frm.target="_self";
		frm.action             = "./agenda.jsp";
		frm.submit();
	}

//-->
</script>
<form name="board" method="post">  
  <input type="hidden" name="cpage"        value="<%= cpage       %>">
  <input type="hidden" name="agenda_id">  
  <input type="hidden" name="b_mode"       value="<%=b_mode      %>">
  <input type="hidden" name="modeType" >
  
  <table width="100%" border="0" cellspacing="0" cellpadding="0"> 
  <tr>
        <td height="50" align="center" class="line_search">
        	컨퍼런스 선택<select name=conference_id id=conference_id onchange="conference_change(this)">
        		<option value="0" <%= "".equals(old_conference_id) ? "selected" : "" %> >전체</option>
<%
    if(  rset2.row()==0) { %>
                
<%    
    } else {
        while( rset2.next()){
        	sel_conference_name     = StringUtil.defaultIfEmpty(rset2.getString("name" ), "");
        	sel_conference_id    = StringUtil.defaultIfEmpty(rset2.getString("conference_id"), "");
%>				
			<option value="<%=sel_conference_id%>" <%= sel_conference_id.equals(old_conference_id) ? "selected" : "" %> ><%=sel_conference_name%></option>
			
<%      } 
    }
    %>           
          		</select>
          		
          		세션 선택<select name=session_id id=session_id onchange="conference_change(this)">
        		<option value="0" <%= "0".equals(session_id) ? "selected" : "" %> >전체</option>
<%
    if(  rset3.row()==0) { %>
                
<%    
    } else {
        while( rset3.next()){
        	sel_session_title     = StringUtil.defaultIfEmpty(rset3.getString("title" ), "");
        	sel_session_id    = StringUtil.defaultIfEmpty(rset3.getString("session_id"), "");
%>				
			<option value="<%=sel_session_id%>" <%= sel_session_id.equals(session_id) ? "selected" : "" %> ><%=sel_session_title%></option>
			
<%      } 
    }
    %>           
          		</select>
          		
          		토픽 선택<select name=topic_id id=topic_id onchange="conference_change(this)">
        		<option value="0" <%= "0".equals(session_id) ? "selected" : "" %> >전체</option>
<%
    if(  rset4.row()==0) { %>
                
<%    
    } else {
        while( rset4.next()){
        	sel_topic_title     = StringUtil.defaultIfEmpty(rset4.getString("title" ), "");
        	sel_topic_id    = StringUtil.defaultIfEmpty(rset4.getString("topic_id"), "");
%>				
			<option value="<%=sel_topic_id%>" <%= sel_topic_id.equals(topic_id) ? "selected" : "" %> ><%=sel_topic_title%></option>
			
<%      } 
    }
    %>           
          		</select>
          	</td>
      </tr>
      <tr>
        <td valign="top"><table width="100%" style="border:1px solid #d3d3d3;">
           <thead>
                    <th><div align="center">컨퍼런스명</div></th>
                    <th><div align="center">발표자</div></th>
                    <th><div align="center">저자</div></th>
                    <th><div align="center">발표일자</div></th>
                    <th><div align="center">발표시간</div></th>
                    <th><div align="center">토픽</div></th>
                    <th><div align="center">바인더 제목</div></th>
                    <th><div align="center">등록인</div></th>
                    <th><div align="center">등록일</div></th>
            </thead>
            <tbody>
    		</tbody>
<%
    if( totalCnt==0 && rset.row()==0) { %>
                <tr><td colspan=7 align="center" height=50>:::: 없음 ::::</td></tr>  
<%    
    } else {
        while( rset.next()){
        	binder_title       = StringUtil.defaultIfEmpty(rset.getString("binder_title"   ), ""); 
        	worker_name       = StringUtil.defaultIfEmpty(rset.getString("worker_name"   ), ""); 
        	session_title       = StringUtil.defaultIfEmpty(rset.getString("session_title"   ), ""); 
        	topic_title     = StringUtil.defaultIfEmpty(rset.getString("topic_title" ), "");
        	reg_date     = StringUtil.defaultIfEmpty(rset.getString("reg_date" ), "");
        	conference_date     = StringUtil.defaultIfEmpty(rset.getString("conference_date" ), "");
        	conference_name     = StringUtil.defaultIfEmpty(rset.getString("conference_name" ), "");
        	binder_title     = StringUtil.defaultIfEmpty(rset.getString("binder_title" ), "");
        	agenda_id     = StringUtil.defaultIfEmpty(rset.getString("agenda_id" ), "");
        	start_time     = StringUtil.defaultIfEmpty(rset.getString("start_time" ), "");
        	end_time     = StringUtil.defaultIfEmpty(rset.getString("end_time" ), "");
        	writer     = StringUtil.defaultIfEmpty(rset.getString("writer" ), "");
        	presenter     = StringUtil.defaultIfEmpty(rset.getString("presenter" ), "");
%>				
			<tr>
				<td align="center"><a href="javascript:goDisplay('<%=agenda_id%>');" class="skin"><%=conference_name%></a></td>
				<td align="center"><a href="javascript:goDisplay('<%=agenda_id%>');" class="skin"><%=presenter%></a></td>
				<td align="center"><a href="javascript:goDisplay('<%=agenda_id%>');" class="skin"><%=writer%></a></td>
				<td align="center"><a href="javascript:goDisplay('<%=agenda_id%>');" class="skin"><%=conference_date%></a></td>
				<td align="center"><a href="javascript:goDisplay('<%=agenda_id%>');" class="skin"><%= !"".equals(start_time)?start_time.substring(0,2):""%>:<%= !"".equals(start_time)?start_time.substring(2,4):""%> ~  <%= !"".equals(end_time)?end_time.substring(0,2):""%>:<%= !"".equals(end_time)?end_time.substring(2,4):""%></a></td>
				<td align="center"><a href="javascript:goDisplay('<%=agenda_id%>');" class="skin"><%=topic_title%></a></td>
				<td align="center">
					<a href="javascript:goDisplay('<%=agenda_id%>');" class="skin">
					<%=binder_title%>
					</a>
				</td>
				<td align="center"><a href="javascript:goDisplay('<%=agenda_id%>');" class="skin"><%=worker_name%></a></td>
				<td align="center"><a href="javascript:goDisplay('<%=agenda_id%>');" class="skin"><%=reg_date%></a></td>
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
                  <td><%= cpage + 1 %> / <%= pageTotal + 1 %> page</td>
                  <%-- 5. 페이지 tag 삽입  --%>
                  <td width="70%" align="center">
                  <%= PagingUtil.getDefaultListPageTag(cpage, pageSize, pageTotal) %>  
                  </td>
                  <td align="right">
                  	<a href="javascript:goWriteForm();"><img src="<%=root_path%>/images/bt_up.gif" alt="등록" /></a>
                  </td>
            </tr>
        </table></td>
      </tr></td>
      </tr>
    </table>
    
    
    
		
</form> 		