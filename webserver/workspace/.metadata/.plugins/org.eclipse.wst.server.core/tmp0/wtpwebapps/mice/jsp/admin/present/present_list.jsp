<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<% request.setCharacterEncoding("UTF-8"); %>
<%
	String root_path = request.getContextPath();
%>
<%@ page import = "sips.common.lib.util.*" %>
<%@ page import = "sips.common.lib.value.*" %>
<%@ page import = "sips.dept.contents.Present" %>
<%@ page import = "sips.dept.contents.Conference" %>
<%@ page import = "sips.dept.menu.*" %>
<%
	ParamValue       parmValue = new ParamValue();        
Present            present     = new Present();
	Conference            conference     = new Conference();
    ResultSetValue   rset      = null;
    ResultSetValue   rset2      = null;
    
    /* 페이지 관련 파라미터 */
    String b_mode             = StringUtil.defaultIfEmpty(request.getParameter("b_mode"      ), ""); 
    String old_conference_id       = StringUtil.defaultIfEmpty(request.getParameter("conference_id"),"0");   
    int    totalCnt  = 0;                        // 총 게시물 갯수
	int    cpage     = (request.getParameter("cpage") == null || "".equals(request.getParameter("cpage"))) ? 0 : Integer.parseInt(request.getParameter("cpage"));   // 현재 페이지
    int    pageSize  = 10;                       // 한 페이지에 들어갈 게시물 갯수
	int    pageTotal = 0;                        // 총 페이지수
	int    listIndex = 0;
    String user_cd         = (String)session.getAttribute("USER_CD");
    String user_authority         = (String)session.getAttribute("USE_AUTHORITY");
    
    String searchTarget       = StringUtil.defaultIfEmpty(request.getParameter("searchTarget"), "");  // 검색 조건 (제목/내용)
    String keyword            = StringUtil.defaultIfEmpty(request.getParameter("keyword"     ), "");  // 검색할 키워드
    String searchFlag         = StringUtil.defaultIfEmpty(request.getParameter("searchFlag"  ), "");  // 검색플래그
    String concat_seq         = StringUtil.defaultIfEmpty(request.getParameter("concat_seq"  ), "0");


    String conference_id = "";
    String conference_name = "";
    String present_id  = "";
    String present_name = "";
    String manufacturer_name = "";
    String phone = "";
    String worker_name = "";
    String reg_date    = "";
    
    DateUtil  dateUtil   = new DateUtil();
    
    
    int defaultILine = 45;
    
    parmValue.put("searchTarget"        , searchTarget  );
    parmValue.put("keyword"             , keyword       );
    parmValue.put("searchFlag"          , searchFlag    );
    parmValue.put("CURRENT_PAGE"        , cpage         );       // 페이징에 꼭 필요
    parmValue.put("lineCnt"             , pageSize      );      
    
    parmValue.put("user_cd"        , user_cd  );  
    parmValue.put("user_authority"        , user_authority  );  
    
    // 컨퍼런스 셀렉트 리스트
    rset2 = conference.getConferenceSelectList(parmValue);

    parmValue.put("conference_id"    , old_conference_id  );

    // 목록
    rset = present.getPresentList(parmValue);

    /*============= 2. 페이징 관련 셋팅 ================*/
    totalCnt  = present.getPresentTotalRow(parmValue);             // 총 게시물 갯수
	pageTotal = ((totalCnt-1)/pageSize) ;               // 총 페이지수
	listIndex = totalCnt - (pageSize * (cpage));
	System.out.println("totalCnt------------>"+totalCnt);
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
        board.present_id.value = "";
        board.target="_self";
        board.action          = "<%= request.getRequestURI() %>";
        board.b_mode.value = "W";
        board.submit();
    }

    /* 읽기 페이지로 이동 */
    function goDisplay(present_id) {
        board.present_id.value = present_id;
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
             frm.action   = "present_write_proc.jsp";
             frm.submit();
         }
    }
    
    function conference_change(obj)
	{
        var frm = document.board;
		//var obj_value = conference_id.value;
		//alert(obj_value);
		frm.target="_self";
		frm.action             = "./present.jsp";
		frm.submit();
	}

//-->
</script>
<form name="board" method="post">  
  <input type="hidden" name="cpage"        value="<%= cpage       %>">
  <input type="hidden" name="present_id">  
  <input type="hidden" name="b_mode"       value="<%=b_mode      %>">
  <input type="hidden" name="searchFlag"   value="<%=searchFlag  %>">
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
        	worker_name    = StringUtil.defaultIfEmpty(rset2.getString("worker_name"), "");
        	reg_date    = StringUtil.defaultIfEmpty(rset2.getString("reg_date"), "");
        	present_id    = StringUtil.defaultIfEmpty(rset2.getString("present_id"), "");
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
                    <th><div align="center">컨퍼런스</div></th>
                    <th><div align="center">선물명</div></th>
                    <th><div align="center">업체명</div></th>
                    <th><div align="center">연락처</div></th>
                    <th><div align="center">등록일</div></th>
                    <th><div align="center">처리자</div></th>
            </thead>
            <tbody>
    		</tbody>
<%
    if( totalCnt==0 && rset.row()==0) { %>
                <tr><td colspan=7 align="center" height=50>:::: 없음 ::::</td></tr>  
<%    
    } else {
        while( rset.next()){
        	manufacturer_name       = StringUtil.defaultIfEmpty(rset.getString("manufacturer_name"   ), ""); 
        	phone       = StringUtil.defaultIfEmpty(rset.getString("phone"   ), ""); 
        	present_name       = StringUtil.defaultIfEmpty(rset.getString("present_name"   ), ""); 
        	reg_date     = StringUtil.defaultIfEmpty(rset.getString("reg_date" ), "");
        	conference_name     = StringUtil.defaultIfEmpty(rset.getString("name" ), "");
        	present_id     = StringUtil.defaultIfEmpty(rset.getString("present_id" ), "");
        	worker_name     = StringUtil.defaultIfEmpty(rset.getString("worker_name" ), "");
%>				
			<tr>
				<td align="center"><a href="javascript:goDisplay('<%=present_id%>');" class="skin"><%=conference_name%></a></td>
				<td align="center">
					<a href="javascript:goDisplay('<%=present_id%>');" class="skin">
					<%=present_name%>
					</a>
				</td>
				<td align="center"><a href="javascript:goDisplay('<%=present_id%>');" class="skin"><%=manufacturer_name%></a></td>
				<td align="center"><a href="javascript:goDisplay('<%=present_id%>');" class="skin"><%=phone%></a></td>
				<td align="center"><a href="javascript:goDisplay('<%=present_id%>');" class="skin"><%=reg_date%></a></td>
				<td align="center"><a href="javascript:goDisplay('<%=present_id%>');" class="skin"><%=worker_name%></a></td>
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
            <option value="1" <%= "1".equals(searchTarget) ? "selected" : "" %> >업체명</option>
          </select>
            <input name="keyword" type="text" value="<%= keyword %>" onKeypress="if (event.keyCode==13) goSearch();" class="input_box" size="20">
            <a href="javascript:goSearch();"><img src="<%=root_path%>/images/bt_search.gif" alt="검색" class="sbtn1"/></a></td>
      </tr>
    </table>
    
    
    
		
</form> 		