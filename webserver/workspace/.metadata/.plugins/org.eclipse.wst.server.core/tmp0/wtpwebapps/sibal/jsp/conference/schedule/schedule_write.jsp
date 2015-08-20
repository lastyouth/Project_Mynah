<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<% request.setCharacterEncoding("UTF-8"); %>
<%
	String root_path = request.getContextPath();
%>
<%@ page import = "java.util.*" %>
<%@ page import = "java.text.*" %>
<%@ page import = "sips.common.lib.util.*" %>
<%@ page import = "sips.common.lib.value.*" %>
<%@ page import = "sips.dept.contents._Schedule" %>
<%@ page import = "sips.dept.contents.Conference" %>
<%@ page import = "sips.dept.menu.*" %>
<%  String formName = "frmBoard"; %>
<%
	ParamValue       parmValue = new ParamValue();          
	_Schedule        schedule     = new _Schedule();
	Conference            conference     = new Conference();
    ResultSetValue   rset      = null;
    ResultSetValue   rset5      = null;
    
	int    cpage              = (request.getParameter("cpage") == null || "".equals(request.getParameter("cpage"))) ? 0 : Integer.parseInt(request.getParameter("cpage"));   // 현재 페이지
    String searchTarget       = StringUtil.defaultIfEmpty(request.getParameter("searchTarget"), "");	      // 검색 조건 (제목/내용)
    String keyword            = StringUtil.defaultIfEmpty(request.getParameter("keyword"     ), "");        // 검색할 키워드
    String searchFlag         = StringUtil.defaultIfEmpty(request.getParameter("searchFlag"  ), "");        // 검색플래그
    String schedule_id       = StringUtil.defaultIfEmpty(request.getParameter("schedule_id"), "");
    String modeType           = StringUtil.defaultIfEmpty(request.getParameter("modeType"    ), "ins");
    String b_mode             = StringUtil.defaultIfEmpty(request.getParameter("b_mode"      ), ""); 
    String conference_id             = StringUtil.defaultIfEmpty(request.getParameter("conference_id"      ), ""); 
    
    String title  = "";
    String contents = "";
    String strMsg             = "";
    String conference_date  = "";    
    String start_time  = "";    
    String end_time  = "";    
    String conference_start_date = "";
    String conference_end_date = "";
    String  conference_name    = "";

    parmValue.put("schedule_id"    , schedule_id  );
    parmValue.put("conference_id"    , conference_id  );
    

    if( !"".equals(schedule_id)) {
        rset = schedule.getScheduleContents(parmValue);
        while( rset.next()){
        	schedule_id     = StringUtil.defaultIfEmpty(rset.getString("schedule_id" ), "");
        	conference_date     = StringUtil.defaultIfEmpty(rset.getString("conference_date" ), "");
        	start_time     = StringUtil.defaultIfEmpty(rset.getString("start_time" ), "");
        	end_time     = StringUtil.defaultIfEmpty(rset.getString("end_time" ), "");
        	title    = StringUtil.defaultIfEmpty(rset.getString("title"    ), "");
        	contents    = StringUtil.defaultIfEmpty(rset.getString("contents"    ), "");
        }
 
        if( "Re".equals(b_mode)) {
            strMsg   = "등록";
            
        } else {
            modeType = "mod";
            strMsg   = "수정";
        }
        
    } else {
        modeType = "ins";
        strMsg   = "등록";
    }	
    
    rset5 = conference.getConferenceInfo(parmValue);
    while( rset5.next()){
    	conference_name    = StringUtil.defaultIfEmpty(rset5.getString("name"    ), "");
    	conference_start_date    = StringUtil.defaultIfEmpty(rset5.getString("start_date"    ), "");
    	conference_end_date    = StringUtil.defaultIfEmpty(rset5.getString("end_date"    ), "");
    }
%>
<script language="JavaScript" type="text/JavaScript">
<!--
    /**
     * 게시물 등록
     */
     function goRegitProc () {
         var frm = document.<%=formName%>;
 		if (frm.start_hour.value == "" || frm.start_min.value == "" ) {
             alert("시작 시간을 입력하세요.");
             frm.start_hour.focus();
             return;
 	    }
 		if (frm.start_hour.value > 24 || frm.start_min.value > 59 ) {
             alert("시간 범위가 안맞습니다.");
             frm.start_hour.focus();
             return;
 	    }
 		if (frm.end_hour.value == "" || frm.end_min.value == "" ) {
 	        alert("종료 시간을 입력하세요.");
 	        frm.end_hour.focus();
 	        return;
 	    }
 		if (frm.end_hour.value > 24 || frm.end_min.value > 59 ) {
             alert("시간 범위가 안맞습니다.");
             frm.end_hour.focus();
             return;
 	    }
 		if (frm.start_hour.value+frm.start_min.value >= frm.end_hour.value+frm.end_min.value ) {
             alert("시간 범위가 안맞습니다.");
             frm.end_hour.focus();
             return;
 	    }
 		if (frm.title.value == "") {
             alert("제목을 입력하세요.");
             frm.title.focus();
             return;
         }
 		if (frm.contents.value == "") {
             alert("내용을 입력하세요.");
             frm.contents.focus();
             return;
         }
 		
 		frm.start_time.value = frm.start_hour.value+frm.start_min.value;
         frm.end_time.value = frm.end_hour.value+frm.end_min.value;
         

 		if (frm.start_time.value > frm.end_time.value) {
             alert("컨퍼런스 발표 시간이 잘못되었습니다.");
             frm.start_time.focus();
             return;
         }
 	    
         if( confirm("<%=strMsg%> 하시겠습니까?")) {
            frm.action   = "schedule_write_proc.jsp";
            frm.submit();
        }
    }
	function goModifyProc (member_cd) { 
         var frm = document.<%=formName%>;
 		if (frm.start_hour.value == "" || frm.start_min.value == "" ) {
             alert("시작 시간을 입력하세요.");
             frm.start_hour.focus();
             return;
 	    }
 		if (frm.start_hour.value > 24 || frm.start_min.value > 59 ) {
             alert("시간 범위가 안맞습니다.");
             frm.start_hour.focus();
             return;
 	    }
 		if (frm.end_hour.value == "" || frm.end_min.value == "" ) {
 	        alert("종료 시간을 입력하세요.");
 	        frm.end_hour.focus();
 	        return;
 	    }
 		if (frm.end_hour.value > 24 || frm.end_min.value > 59 ) {
             alert("시간 범위가 안맞습니다.");
             frm.end_hour.focus();
             return;
 	    }
 		if (frm.start_hour.value+frm.start_min.value >= frm.end_hour.value+frm.end_min.value ) {
             alert("시간 범위가 안맞습니다.");
             frm.end_hour.focus();
             return;
 	    }
 		if (frm.title.value == "") {
             alert("제목을 입력하세요.");
             frm.title.focus();
             return;
         }
 		if (frm.contents.value == "") {
             alert("내용을 입력하세요.");
             frm.contents.focus();
             return;
         }
 		
 		frm.start_time.value = frm.start_hour.value+frm.start_min.value;
         frm.end_time.value = frm.end_hour.value+frm.end_min.value;
         

 		if (frm.start_time.value > frm.end_time.value) {
             alert("컨퍼런스 발표 시간이 잘못되었습니다.");
             frm.start_time.focus();
             return;
         }
    
	    if( confirm("수정 하시겠습니까?")) {
	        frm.modeType.value = "mod";
	        frm.action   = "schedule_write_proc.jsp";
	        frm.submit();
	    }
    }    
	
	  
    /**
     * 게시물 삭제
     */
    function goDeleteForm () { 
        var frm = document.<%=formName%>;
        if( confirm("삭제 하시겠습니까?")) {
            frm.modeType.value = "del";
            frm.action   = "schedule_write_proc.jsp";
            frm.submit();
        }
                
    }    
    
    function goList() {
        var frm = document.<%=formName%>;
        
        frm.b_mode.value = "L";
        frm.action   = "<%= request.getRequestURI() %>";
        frm.submit();
    }
  //-->
</script>
<form name="<%=formName%>" method="post">
<input type="hidden" name="cpage"        value="<%= cpage       %>">
<input type="hidden" name="schedule_id" value="<%=schedule_id %>">  
<input type="hidden" name="b_mode"       value="<%=b_mode      %>">
<input type="hidden" name="modeType"     value="<%=modeType    %>">
<input type="hidden" name="start_time" >
<input type="hidden" name="end_time" >

<table width="100%" border="0" cellspacing="0" cellpadding="0">
      
      <tr>
        <td valign="top"><table border="0" cellpadding="0" cellspacing="0" style="border-top:2px solid #e0e0e0; border-bottom:2px solid #e0e0e0; width:784px;">
            <tr>
              <td width="210" class="bbbg" style="padding-left:20px; font-weight:bold;">스케쥴 날짜</td>
              <td width="574" style="padding-left:20px;">
              	<select name="conference_date">
              	<%
              	String select_start_date = "";
              	String select_end_date = "";
              	if ("".equals(conference_start_date))	conference_start_date = "0000-00-00";
              	if ("".equals(conference_end_date))	conference_end_date = "0000-00-00";
              	java.text.SimpleDateFormat format = new java.text.SimpleDateFormat("yyyy-MM-dd");
              	java.util.Date start_date = format.parse(conference_start_date);
              	java.util.Date end_date = format.parse(conference_end_date);
              	Calendar cal = Calendar.getInstance();
              	Calendar cal2 = Calendar.getInstance();
              	cal.setTime(end_date );//종료일을 calendar 변수에 세팅
              	cal2.setTime(start_date );//시작일을 calendar 변수에 세팅
              //종료일 - 시작일 = 총 걸리는 시간
              	long diffMillis = cal.getTimeInMillis() - cal2.getTimeInMillis();
              	int totalDate = Integer.parseInt(Long.toString(diffMillis))/86400000; //(하루는 86400000  밀리second)
              	System.out.println( totalDate+ " : " + diffMillis);

              	String selected = "";
              	out.println("<option value="+conference_start_date+"  "+selected+">"+conference_start_date+"</option>"); 
              	for( int i=0; i <  totalDate ; i++){
              		cal2.add( Calendar.DATE,1); 
                  	Date date_2 = cal2.getTime();
                  	select_start_date =  cal2.get(Calendar.YEAR)+"-" + (cal2.get(Calendar.MONTH)+1)+"-" + cal2.get(Calendar.DATE);
                  	
                  	if (conference_date.equals(select_start_date)){
                  		selected = "selected";
                  	}else{
                  		selected = "";
                  	}
              		out.println("<option value="+select_start_date+"  "+selected+">"+select_start_date+"</option>");            	    
              	}
              	%>
              	</select>
              </td>
            </tr>
            <tr>
              <td width="210" class="bbbg" style="padding-left:20px; font-weight:bold;">스켸쥴 시간</td>
              <td width="574" style="padding-left:20px;">
				<input type="text" name="start_hour" value="<%= !"".equals(start_time)?start_time.substring(0,2):""%>" size="1" maxlength=2> :
              	<input type="text" name="start_min" value="<%= !"".equals(start_time)?start_time.substring(2,4):""%>" size="1" maxlength=2>
              	~
              	<input type="text" name="end_hour" value="<%= !"".equals(end_time)?end_time.substring(0,2):""%>" size="1" maxlength=2> : 
              	<input type="text" name="end_min" value="<%= !"".equals(end_time)?end_time.substring(2,4):""%>" size="1" maxlength=2>
			</td>
            </tr>
            <tr>
              <td width="210" class="bbbg" style="padding-left:20px; font-weight:bold;">제목</td>
              <td width="574" style="padding-left:20px;"><input name="title" type="text" value="<%=title%>" class="Ttable" size="100"></td>
            </tr>
            <tr>
              <td width="210" class="bbbg" style="padding-left:20px; font-weight:bold;">내용</td>
              <td width="574" style="padding-left:20px;">
              	<textarea name="contents" rows="5" cols="60"><%=contents %></textarea>
              </td>
            </tr>
        </table></td>
      </tr>
      <tr>
        <td height="50" align="center">
        <% if(modeType.equals("ins")){ %>
            <a href="javascript:goRegitProc();"><img src="<%=root_path%>/images/bt_up.gif" alt="등록" /></a>
         <% }else{ %>
            <a href="javascript:goModifyProc();"><img src="<%=root_path%>/images/bt_modify.gif" alt="수정" /></a>
         <% } %>   
            <a href="javascript:goList();"><img src="<%=root_path%>/images/bt_list.gif" alt="목록" /></a>
         </td>
      </tr>
    </table>
    
</form>		