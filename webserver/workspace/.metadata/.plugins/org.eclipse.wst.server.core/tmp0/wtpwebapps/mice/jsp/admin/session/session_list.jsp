<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<% request.setCharacterEncoding("UTF-8"); %>
<%
	String root_path = request.getContextPath();
%>
<%@ page import = "sips.common.lib.util.*" %>
<%@ page import = "sips.common.lib.value.*" %>
<%@ page import = "sips.dept.contents.Session" %>
<%@ page import = "sips.dept.contents.Conference" %>
<%
	ParamValue       parmValue = new ParamValue();        
	Session            sessionBean     = new Session();
	Conference            conference     = new Conference();
    ResultSetValue   rset      = null;
    ResultSetValue   rset2      = null;
    ResultSetValue   rset3      = null;
    
    /* 페이지 관련 파라미터 */
    String b_mode             = StringUtil.defaultIfEmpty(request.getParameter("b_mode"      ), ""); 
    String old_conference_id       = StringUtil.defaultIfEmpty(request.getParameter("conference_id"),"0");   
    
    String user_cd         = (String)session.getAttribute("USER_CD");
    String user_authority         = (String)session.getAttribute("USE_AUTHORITY");

    String con_name = "";
    String conference_id = "";
    String conference_name = "";
    String session_id  = "";
    String title = "";
    String conference_date    = ""; 
    String reg_date= "";

    DateUtil  dateUtil   = new DateUtil();

    parmValue.put("user_cd"        , user_cd  );  
    parmValue.put("user_authority"        , user_authority  );  
    
    // 컨퍼런스 셀렉트 리스트
    rset2 = conference.getConferenceSelectList(parmValue);

    System.out.println("old_conference_id--2->"+old_conference_id);
    System.out.println("conference_id-2-->"+conference_id);

    // 목록
    parmValue.put("conference_id"        , old_conference_id  );  
    rset = sessionBean.getSessionList(parmValue);
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
        board.session_id.value = "";
        board.target="_self";
        board.action          = "<%= request.getRequestURI() %>";
        board.b_mode.value = "W";
        board.submit();
    }

    /* 읽기 페이지로 이동 */
    function goDisplay(session_id) {
        board.session_id.value = session_id; 
        board.b_mode.value       = "M";
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
    
    function conference_change(obj)
	{
        var frm = document.board;
		//var obj_value = conference_id.value;
		//alert(obj_value);
		frm.target="_self";
		frm.action             = "./session.jsp";
		frm.submit();
	}

//-->
</script>
<form name="board" method="post">  
  <input type="hidden" name="session_id">  
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
        	conference_name     = StringUtil.defaultIfEmpty(rset2.getString("name" ), "");
        	conference_id    = StringUtil.defaultIfEmpty(rset2.getString("conference_id"), "");
%>				
			<option value="<%=conference_id%>" <%= conference_id.equals(old_conference_id) ? "selected" : "" %> ><%=conference_name%></option>
			
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
                    <th><div align="center">세션제목</div></th>
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
        	session_id       = StringUtil.defaultIfEmpty(rset.getString("session_id"   ), ""); 
        	title     = StringUtil.defaultIfEmpty(rset.getString("title" ), "");
        	con_name     = StringUtil.defaultIfEmpty(rset.getString("conference_name" ), "");
        	reg_date     = StringUtil.defaultIfEmpty(rset.getString("reg_date" ), "");
%>				
			<tr>
				<td align="center"><a href="javascript:goDisplay('<%=session_id%>');" class="skin"><%=con_name%></a></td>
				<td align="center">
					<a href="javascript:goDisplay('<%=session_id%>');" class="skin">
					<%=title%>
					</a>
				</td>
				<td align="center"><a href="javascript:goDisplay('<%=session_id%>');" class="skin"><%=reg_date%></a></td>
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