<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<% request.setCharacterEncoding("UTF-8"); %>
<%
	String root_path = request.getContextPath();
%>
<%@ page import = "sips.common.lib.util.*" %>
<%@ page import = "sips.common.lib.value.*" %>
<%@ page import = "sips.dept.contents.Member" %>
<%@ page import = "sips.dept.menu.*" %>
<%
	ParamValue       parmValue = new ParamValue();        
	Member            member     = new Member();
    ResultSetValue   rset      = null;
    
    /* 페이지 관련 파라미터 */
    String b_mode             = StringUtil.defaultIfEmpty(request.getParameter("b_mode"      ), ""); 
    int    totalCnt  = 0;                        // 총 게시물 갯수
	int    cpage     = (request.getParameter("cpage") == null || "".equals(request.getParameter("cpage"))) ? 0 : Integer.parseInt(request.getParameter("cpage"));   // 현재 페이지
    int    pageSize  = 10;                       // 한 페이지에 들어갈 게시물 갯수
	int    pageTotal = 0;                        // 총 페이지수
	int    listIndex = 0;
    
    String searchTarget       = StringUtil.defaultIfEmpty(request.getParameter("searchTarget"), "");  // 검색 조건 (제목/내용)
    String keyword            = StringUtil.defaultIfEmpty(request.getParameter("keyword"     ), "");  // 검색할 키워드
    String searchFlag         = StringUtil.defaultIfEmpty(request.getParameter("searchFlag"  ), "");  // 검색플래그
    String concat_seq         = StringUtil.defaultIfEmpty(request.getParameter("concat_seq"  ), "0");
    
    String conference_name = "";
    String authority = "";
    String user_cd        = "";
    String name  = "";
    String id    = "";
    String company  = "";
    String reg_date = "";
    
    
    DateUtil  dateUtil   = new DateUtil();
    
    
    int defaultILine = 45;
    
    parmValue.put("searchTarget"        , searchTarget  );
    parmValue.put("keyword"             , keyword       );
    parmValue.put("searchFlag"          , searchFlag    );
    parmValue.put("CURRENT_PAGE"        , cpage         );       // 페이징에 꼭 필요
    parmValue.put("lineCnt"             , pageSize      );      
    parmValue.put("ORDER_BY"            , " user.reg_date desc   "      );     
    
   

    // 목록
    rset = member.getMemberBoardList(parmValue);
   
    /*============= 2. 페이징 관련 셋팅 ================*/
    totalCnt  = member.getMemberTotalRow(parmValue);             // 총 게시물 갯수
	pageTotal = ((totalCnt-1)/pageSize) ;               // 총 페이지수
	listIndex = totalCnt - (pageSize * (cpage));
    
    
%>
<script language="JavaScript" >
<!--
    /* 검색 */
    function goSearch() {
        board.searchFlag.value = "OK";
        board.cpage.value = "0";
        board.b_mode.value  = "L";
        board.target="_self";
        board.action = "<%= request.getRequestURI() %>";
        board.submit();
    }

    /* 목록 페이지로 이동 */
    function goList() {
        board.cpage.value        = "";
        board.searchTarget.value = "";
        board.keyword.value      = "";
        board.b_mode.value       = "L";
        board.searchFlag.value   = "";

        board.target="_self";
        board.action = "<%= request.getRequestURI() %>";
        board.submit();
    }

    /* 쓰기 페이지로 이동 */
    function goWriteForm() {
        board.user_cd.value = "";
        board.target="_self";
        board.action          = "<%= request.getRequestURI() %>";
        board.b_mode.value = "W";
        board.submit();
    }

    /* 읽기 페이지로 이동 */
    function goDisplay(user_cd) {
        board.user_cd.value = user_cd; 
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
    
    /* 공지사항 읽기 페이지로 이동 */
    function goNoticeDisplay(contents_seq) {

        board.contents_seq.value = contents_seq;
        board.noticeFlag.value   = "Y";
        board.b_mode.value       = "R";
        board.target="_self";
        board.action             = "<%= request.getRequestURI() %>";
        board.submit();

    }
    
    /* 이전게시판 팝업 */
    function goPrevBoard(prevUrl) {
        winOpen = window.open('', 'prevBoard','width=600,height=670,statusbar=0,scrollbars=1');
        
        if( winOpen != null ){
	        board.target = "prevBoard";
	        board.action = prevUrl;
	        board.submit();
	    }
	    else {
	        return;
	    }
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
             frm.action   = "member_write_proc.jsp";
             frm.submit();
         }
    }

//-->
</script>
<form name="board" method="post">  
  <input type="hidden" name="cpage"        value="<%= cpage       %>">
  <input type="hidden" name="user_cd">  
  <input type="hidden" name="b_mode"       value="<%=b_mode      %>">
  <input type="hidden" name="searchFlag"   value="<%=searchFlag  %>">
  <input type="hidden" name="noticeFlag" >
  <input type="hidden" name="modeType" >
  
  <table width="100%" border="0" cellspacing="0" cellpadding="0"> 
      <tr>
        <td valign="top"><table width="100%" style="border:1px solid #d3d3d3;">
           <thead>
                    <th><div align="center">이름(아이디)</div></th>
                    <th><div align="center">컨퍼런스</div></th>
                    <th><div align="center">권한</div></th>
                    <th><div align="center">소속</div></th>
                    <th><div align="center">날짜</div></th>
            </thead>
            <tbody>
    		</tbody>
<%
    if( totalCnt==0 && rset.row()==0) { %>
                <tr><td colspan=7 align="center" height=50>:::: 없음 ::::</td></tr>  
<%    
    } else {
        while( rset.next()){
        	user_cd       = StringUtil.defaultIfEmpty(rset.getString("user_cd"   ), ""); 
        	name     = StringUtil.defaultIfEmpty(rset.getString("name" ), "");
        	id    = StringUtil.defaultIfEmpty(rset.getString("id"), "");
            company          = StringUtil.defaultIfEmpty(rset.getString("company"         ), "");
            conference_name          = StringUtil.defaultIfEmpty(rset.getString("conference_name"         ), "");
            authority          = StringUtil.defaultIfEmpty(rset.getString("authority"         ), "");
            reg_date         = StringUtil.defaultIfEmpty(rset.getString("reg_date"        ), "");    
%>				
			<tr>
				<td align="center">
					<a href="javascript:goDisplay('<%=user_cd%>');" class="skin">
					<%=name%>(<%=id%>)
					</a>
				</td>
				<td align="center"><a href="javascript:goDisplay('<%=user_cd%>');" class="skin"><%=conference_name%></a></td>
				<td align="center"><a href="javascript:goDisplay('<%=user_cd%>');" class="skin"><%=authority%></a></td>
				<td align="center"><a href="javascript:goDisplay('<%=user_cd%>');" class="skin"><%=company%></a></td>
				<td align="center"><a href="javascript:goDisplay('<%=user_cd%>');" class="skin"><%=reg_date%></a></td>
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
                  </td>
            </tr>
        </table></td>
      </tr>
      <tr>
        <td height="50" align="center" class="line_search"><select name="searchTarget">
            <option value="1" <%= "1".equals(searchTarget) ? "selected" : "" %> >이름</option>
            <option value="2" <%= "2".equals(searchTarget) ? "selected" : "" %> >아이디</option>            
            <option value="3" <%= "3".equals(searchTarget) ? "selected" : "" %> >컨퍼런스</option>
          </select>
            <input name="keyword" type="text" value="<%= keyword %>" onKeypress="if (event.keyCode==13) goSearch();" class="input_box" size="20">
            <a href="javascript:goSearch();"><img src="<%=root_path%>/images/bt_search.gif" alt="검색" class="sbtn1"/></a></td>
      </tr>
    </table>
    
    
    
		
</form> 		