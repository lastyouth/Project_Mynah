<%@ page language = "java" contentType="text/html; charset=UTF-8" %>
<%
	String root_path = request.getContextPath();
%>
<%@ page import = "sips.common.lib.util.*" %>
<%@ page import = "sips.common.lib.value.*" %>
<%@ page import = "sips.dept.contents.Banner" %>
<%  String formName = "frmBoard"; %>
<%
	ParamValue       parmValue = new ParamValue();      
	Banner            banner     = new Banner();
   
    ResultSetValue   rset      = null;
    ResultSetValue   rsetHis      = null;

    String b_mode             = StringUtil.defaultIfEmpty(request.getParameter("b_mode"      ), ""); 
	int    cpage              = (request.getParameter("cpage") == null || "".equals(request.getParameter("cpage"))) ? 0 : Integer.parseInt(request.getParameter("cpage"));   // 현재 페이지
    String banner_id         = StringUtil.defaultIfEmpty(request.getParameter("banner_id"  ),"");
    String conference_id       = "";

    String banner_1  = "";
    String banner_2  = "";
    String banner_3  = "";
    String banner_4  = "";
    String banner_5  = "";
    String banner_6  = "";
    String banner_7  = "";
    String banner_8  = "";
    String banner_9  = "";
    String banner_10  = "";
    String banner_11  = "";
    String landing_url_1 = "";
    String landing_url_2 = "";
    String landing_url_3 = "";
    String landing_url_4 = "";
    String landing_url_5 = "";
    String landing_url_6 = "";
    String landing_url_7 = "";
    String landing_url_8 = "";
    String landing_url_9 = "";
    String landing_url_10 = "";
    String landing_url_11 = "";
    String banner_name = "";
    String strMsg             = "";
  
    String modeType           = "";

    parmValue.put("banner_id"  , banner_id  );  
    parmValue.put("conference_id"  , conference_id  );  
    rset = banner.getBannerInfo(parmValue);
	while( rset.next()){
		banner_id    = StringUtil.defaultIfEmpty(rset.getString("banner_id"    ), "");
    	banner_1    = StringUtil.defaultIfEmpty(rset.getString("banner_1"    ), "");
    	banner_2    = StringUtil.defaultIfEmpty(rset.getString("banner_2"    ), "");
    	banner_3    = StringUtil.defaultIfEmpty(rset.getString("banner_3"    ), "");
    	banner_4    = StringUtil.defaultIfEmpty(rset.getString("banner_4"    ), "");
    	banner_5    = StringUtil.defaultIfEmpty(rset.getString("banner_5"    ), "");
    	banner_6    = StringUtil.defaultIfEmpty(rset.getString("banner_6"    ), "");
    	banner_7    = StringUtil.defaultIfEmpty(rset.getString("banner_7"    ), "");
    	banner_8    = StringUtil.defaultIfEmpty(rset.getString("banner_8"    ), "");
    	banner_9    = StringUtil.defaultIfEmpty(rset.getString("banner_9"    ), "");
    	banner_10    = StringUtil.defaultIfEmpty(rset.getString("banner_10"    ), "");
    	banner_11    = StringUtil.defaultIfEmpty(rset.getString("banner_11"    ), "");
    	landing_url_1    = StringUtil.defaultIfEmpty(rset.getString("landing_url_1"    ), "");
    	landing_url_2    = StringUtil.defaultIfEmpty(rset.getString("landing_url_2"    ), "");
    	landing_url_3    = StringUtil.defaultIfEmpty(rset.getString("landing_url_3"    ), "");
    	landing_url_4    = StringUtil.defaultIfEmpty(rset.getString("landing_url_4"    ), "");
    	landing_url_5    = StringUtil.defaultIfEmpty(rset.getString("landing_url_5"    ), "");
    	landing_url_6    = StringUtil.defaultIfEmpty(rset.getString("landing_url_6"    ), "");
    	landing_url_7    = StringUtil.defaultIfEmpty(rset.getString("landing_url_7"    ), "");
    	landing_url_8    = StringUtil.defaultIfEmpty(rset.getString("landing_url_8"    ), "");
    	landing_url_9    = StringUtil.defaultIfEmpty(rset.getString("landing_url_9"    ), "");
    	landing_url_10    = StringUtil.defaultIfEmpty(rset.getString("landing_url_10"    ), "");
    	landing_url_11    = StringUtil.defaultIfEmpty(rset.getString("landing_url_11"    ), "");
    	conference_id    = StringUtil.defaultIfEmpty(rset.getString("conference_id"    ), "");
	}
    
%>		
<script language="JavaScript" type="text/JavaScript">
<!--
    /**
     * 게시물 수정
     */
    function goModifyForm (banner_id ) {        
        var frm = document.<%=formName%>;
        
        frm.banner_id.value = banner_id;
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
<input type="hidden" name="cpage"        value="<%= cpage       %>">
<input type="hidden" name="b_mode"       value="<%=b_mode      %>">
<input type="hidden" name="modeType"     value="<%=modeType    %>">
<input type="hidden" name="banner_id"      value="<%=banner_id%>">   
<input type="hidden" name="conference_id"      value="<%=conference_id%>">   
	
    <table width="100%" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td valign="top">
           <table border="0" cellpadding="0" cellspacing="0" style="border-top:2px solid #e0e0e0; border-bottom:2px solid #e0e0e0; width:784px;">
            <tr>
              <td width="210" class="bbbg" style="padding-left:20px; font-weight:bold;">메인 베너</td>
              <td width="574" style="padding-left:20px;">
              	<%
              	if (!"".equals(banner_1)){
              		out.println("<a href='"+landing_url_1+"' target='_new'><img src=/mice/upload/banner/"+banner_1+"></a>");
              	}
              %>
              </td>
            </tr>		
            <tr>
              <td width="210" class="bbbg" style="padding-left:20px; font-weight:bold;">Agenda 베너</td>
              <td width="574" style="padding-left:20px;">
              	<%
              	if (!"".equals(banner_2)){
              		out.println("<a href='"+landing_url_2+"' target='_new'><img src=/mice/upload/banner/"+banner_2+">");
              	}
              %>
              </td>
            </tr>	
            <tr>
              <td width="210" class="bbbg" style="padding-left:20px; font-weight:bold;">Binder 베너</td>
              <td width="574" style="padding-left:20px;">
              	<%
              	if (!"".equals(banner_3)){
              		out.println("<a href='"+landing_url_3+"' target='_new'><img src=/mice/upload/banner/"+banner_3+">");
              	}
              %>
              </td>
            </tr>	
            <tr>
              <td width="210" class="bbbg" style="padding-left:20px; font-weight:bold;">Search 베너</td>
              <td width="574" style="padding-left:20px;">
              	<%
              	if (!"".equals(banner_4)){
              		out.println("<a href='"+landing_url_4+"' target='_new'><img src=/mice/upload/banner/"+banner_4+">");
              	}
              %>
              </td>
            </tr>	
            <tr>
              <td width="210" class="bbbg" style="padding-left:20px; font-weight:bold;">Massage 베너</td>
              <td width="574" style="padding-left:20px;">
              	<%
              	if (!"".equals(banner_5)){
              		out.println("<a href='"+landing_url_5+"' target='_new'><img src=/mice/upload/banner/"+banner_5+">");
              	}
              %>
              </td>
            </tr>	
            <tr>
              <td width="210" class="bbbg" style="padding-left:20px; font-weight:bold;">MyBrifcase 베너</td>
              <td width="574" style="padding-left:20px;">
              	<%
              	if (!"".equals(banner_6)){
              		out.println("<a href='"+landing_url_6+"' target='_new'><img src=/mice/upload/banner/"+banner_6+">");
              	}
              %>
              </td>
            </tr>	
            <tr>
              <td width="210" class="bbbg" style="padding-left:20px; font-weight:bold;">Map 베너</td>
              <td width="574" style="padding-left:20px;">
              	<%
              	if (!"".equals(banner_7)){
              		out.println("<a href='"+landing_url_7+"' target='_new'><img src=/mice/upload/banner/"+banner_7+">");
              	}
              %>
              </td>
            </tr>	
            <tr>
              <td width="210" class="bbbg" style="padding-left:20px; font-weight:bold;">설문조사 베너</td>
              <td width="574" style="padding-left:20px;">
              	<%
              	if (!"".equals(banner_8)){
              		out.println("<a href='"+landing_url_8+"' target='_new'><img src=/mice/upload/banner/"+banner_8+">");
              	}
              %>
              </td>
            </tr>	
            <tr>
              <td width="210" class="bbbg" style="padding-left:20px; font-weight:bold;">설정 베너</td>
             <td width="574" style="padding-left:20px;">
              	<%
              	if (!"".equals(banner_9)){
              		out.println("<a href='"+landing_url_9+"' target='_new'><img src=/mice/upload/banner/"+banner_9+">");
              	}
              %>
              </td>
            </tr>	
            <tr>
              <td width="210" class="bbbg" style="padding-left:20px; font-weight:bold;">바코드 베너</td>
              <td width="574" style="padding-left:20px;">
              	<%
              	if (!"".equals(banner_10)){
              		out.println("<a href='"+landing_url_10+"' target='_new'><img src=/mice/upload/banner/"+banner_10+">");
              	}
              %>
              </td>
            </tr>	
            <tr>
              <td width="210" class="bbbg" style="padding-left:20px; font-weight:bold;">도움말 베너</td>
              <td width="574" style="padding-left:20px;">
              	<%
              	if (!"".equals(banner_11)){
              		out.println("<a href='"+landing_url_11+"' target='_new'><img src=/mice/upload/banner/"+banner_11+">");
              	}
              %>
              </td>
            </tr>	
        </table></td>
      </tr>
      <tr>
        <td height="50" align="center">
					 <a href="javascript:goList();"><img src="/dhsp/images/bt_list.gif" alt="목록" /></a>
                      <a href="javascript:goModifyForm('<%= banner_id %>');"><img src="<%=root_path%>/images/bt_modify.gif" alt="수정" /></a>                   
                          
        </td>
      </tr>      
    </table>
</form>		
