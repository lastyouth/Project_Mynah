<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<% request.setCharacterEncoding("UTF-8"); %>
<%
	String root_path = request.getContextPath();
%>
<%@ page import = "sips.common.lib.util.*" %>
<%@ page import = "sips.common.lib.value.*" %>
<%@ page import = "sips.dept.contents._Memo" %>
<%@ page import = "sips.dept.contents.Conference" %>
<%
	ParamValue       parmValue = new ParamValue();        
	_Memo            memo     = new _Memo();
	Conference            conference     = new Conference();
    ResultSetValue   rset      = null;
    ResultSetValue   rset3      = null;
    
    /* 페이지 관련 파라미터 */
    String b_mode             = StringUtil.defaultIfEmpty(request.getParameter("b_mode"      ), ""); 
    String old_conference_id       = StringUtil.defaultIfEmpty(request.getParameter("conference_id"),"0");   
    int    totalCnt  = 0;                        // 총 게시물 갯수
	int    cpage     = (request.getParameter("cpage") == null || "".equals(request.getParameter("cpage"))) ? 0 : Integer.parseInt(request.getParameter("cpage"));   // 현재 페이지
    int    pageSize  = 10;                       // 한 페이지에 들어갈 게시물 갯수
	int    pageTotal = 0;                        // 총 페이지수
	int    listIndex = 0;
    
    String searchTarget       = StringUtil.defaultIfEmpty(request.getParameter("searchTarget"), "");  // 검색 조건 (제목/내용)
    String keyword            = StringUtil.defaultIfEmpty(request.getParameter("keyword"     ), "");  // 검색할 키워드
    String searchFlag         = StringUtil.defaultIfEmpty(request.getParameter("searchFlag"  ), "");  // 검색플래그
    
    String user_cd         = (String)session.getAttribute("USER_CD");
    String user_authority         = (String)session.getAttribute("USE_AUTHORITY");
    
    String conference_name = "";
    String memo_id = "";
    String title  = "";
    String contents    = "";
    String reg_date  = "";    
    
    DateUtil  dateUtil   = new DateUtil();    
    
    int defaultILine = 45;
    
    parmValue.put("searchTarget"        , searchTarget  );
    parmValue.put("keyword"             , keyword       );
    parmValue.put("searchFlag"          , searchFlag    );
    parmValue.put("CURRENT_PAGE"        , cpage         );       // 페이징에 꼭 필요
    parmValue.put("lineCnt"             , pageSize      );      
    parmValue.put("ORDER_BY"            , " memo.reg_date desc   "      );     
    
 // 컨퍼런스 셀렉트 리스트
    parmValue.put("user_cd"        , user_cd  );  
    parmValue.put("user_authority"        , user_authority  );  

    // 목록
    parmValue.put("conference_id"        , old_conference_id  );  
    rset = memo.getMemoList2(parmValue); 
   
    /*============= 2. 페이징 관련 셋팅 ================*/
    totalCnt  = memo.getMemoTotalRow(parmValue);             // 총 게시물 갯수
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
        board.memo_id.value = "";
        board.target="_self";
        board.action          = "<%= request.getRequestURI() %>";
        board.b_mode.value = "W";
        board.submit();
    }

    /* 읽기 페이지로 이동 */
    function goDisplay(memo_id) {
        board.memo_id.value = memo_id; 
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
             frm.action   = "member_write_proc.jsp";
             frm.submit();
         }
    }

//-->
</script>
<form name="board" method="post">  
  <input type="hidden" name="cpage"        value="<%= cpage       %>">
  <input type="hidden" name="memo_id">  
  <input type="hidden" name="b_mode"       value="<%=b_mode      %>">
  <input type="hidden" name="searchFlag"   value="<%=searchFlag  %>">
  <input type="hidden" name="conference_id"   value="<%=old_conference_id  %>">
  <input type="hidden" name="modeType" >
  
  <table width="100%" border="0" cellspacing="0" cellpadding="0"> 
        <td valign="top"><table width="100%" style="border:1px solid #d3d3d3;">
           <thead>
                    <th><div align="center">제목</div></th>
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
        	memo_id       = StringUtil.defaultIfEmpty(rset.getString("memo_id"   ), ""); 
        	title     = StringUtil.defaultIfEmpty(rset.getString("title" ), "");
        	reg_date    = StringUtil.defaultIfEmpty(rset.getString("reg_date"), "");
%>				
			<tr>
				<td align="center">
					<a href="javascript:goDisplay('<%=memo_id%>');" class="skin">
					<%=title%>
					</a>
				</td>
				<td align="center"><a href="javascript:goDisplay('<%=memo_id%>');" class="skin"><%=reg_date%></a></td>
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
      </tr>
      <tr>
        <td height="50" align="center" class="line_search"><select name="searchTarget">
            <option value="1" <%= "1".equals(searchTarget) ? "selected" : "" %> >제목</option>
            <option value="2" <%= "2".equals(searchTarget) ? "selected" : "" %> >내용</option>     
          </select>
            <input name="keyword" type="text" value="<%= keyword %>" onKeypress="if (event.keyCode==13) goSearch();" class="input_box" size="20">
            <a href="javascript:goSearch();"><img src="<%=root_path%>/images/bt_search.gif" alt="검색" class="sbtn1"/></a></td>
      </tr>
    </table>
    
    
    
		
</form> 		