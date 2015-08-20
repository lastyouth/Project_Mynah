<%@ page language = "java" contentType="text/html; charset=UTF-8" %>
<%
	String root_path = request.getContextPath();
%>
<%@ page import = "sips.common.lib.util.*" %>
<%@ page import = "sips.common.lib.value.*" %>
<%@ page import = "sips.dept.contents.Conference" %>
<%@ page import = "sips.dept.menu.*" %>
<%  String formName = "frmBoard"; %>
<%
	ParamValue       parmValue = new ParamValue();      
	Conference            conference     = new Conference();
   
    ResultSetValue   rset      = null;
    ResultSetValue   rsetHis      = null;

    String b_mode             = StringUtil.defaultIfEmpty(request.getParameter("b_mode"      ), ""); 
    String conference_id         = StringUtil.defaultIfEmpty(request.getParameter("conference_id"      ), ""); 

    String name  = "";
    String start_date = "";
    String end_date    = "";
    String reg_date = "";
    String banner_image = "";
    String info_image = "";
    String strMsg             = "";
  
    String modeType           = "";

    parmValue.put("conference_id"  , conference_id  );  
    rset = conference.getConferenceInfo(parmValue);
	while( rset.next()){
		end_date    = StringUtil.defaultIfEmpty(rset.getString("end_date"    ), "");
        start_date    = StringUtil.defaultIfEmpty(rset.getString("start_date"    ), "");
        name    = StringUtil.defaultIfEmpty(rset.getString("name"    ), "");
        reg_date    = StringUtil.defaultIfEmpty(rset.getString("reg_date"    ), "");
        banner_image    = StringUtil.defaultIfEmpty(rset.getString("banner_image"    ), "");
        info_image    = StringUtil.defaultIfEmpty(rset.getString("info_image"    ), "");
	}
    
%>		
<script language="JavaScript" type="text/JavaScript">
<!--
    /**
     * 게시물 수정
     */
    function goModifyForm (conference_id ) {        
        var frm = document.<%=formName%>;
        
        frm.conference_id.value = conference_id;
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
<input type="hidden" name="conference_id"      value="<%=conference_id%>">   
	
    <table width="100%" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td valign="top">
           <table border="0" cellpadding="0" cellspacing="0" style="border-top:2px solid #e0e0e0; border-bottom:2px solid #e0e0e0; width:784px;">
            <tr>
              <td width="210" class="bbbg" style="padding-left:20px; font-weight:bold;">컨퍼런스 이름</td>
              <td width="574" style="padding-left:20px;"><%=name%></td>
            </tr>		
            <tr>
              <td class="bbbg" style="padding-left:20px; font-weight:bold;">시작일</td>
              <td style="padding-left:20px;"><%=start_date%></td>
            </tr>
            <tr>
              <td class="bbbg" style="padding-left:20px; font-weight:bold;">종료일</td>
              <td style="padding-left:20px;"><%=end_date%></td>
            </tr>
            <tr>
              <td class="bbbg" style="padding-left:20px; font-weight:bold;">베너이미지</td>
              <td style="padding-left:20px;">
               <%
              	if (!"".equals(banner_image))
              		out.println("<img src=/mice/upload/conference/"+banner_image+">");
              %>
              </td>
            </tr>
            <tr>
              <td class="bbbg" style="padding-left:20px; font-weight:bold;">INFO 이미지</td>
              <td style="padding-left:20px;">
               <%
              	if (!"".equals(info_image))
              		out.println("<img src=/mice/upload/conference/"+info_image+">");
              %>
              </td>
            </tr>
            <tr>
              <td class="bbbg" style="padding-left:20px; font-weight:bold;">등록일</td>
              <td style="padding-left:20px;"><%=reg_date%></td>
            </tr>
        </table></td>
      </tr>
      <tr>
        <td height="50" align="center">
                      <a href="javascript:goModifyForm('<%= conference_id %>');"><img src="<%=root_path%>/images/bt_modify.gif" alt="수정" /></a>                   
                          
        </td>
      </tr>      
    </table>
</form>		
