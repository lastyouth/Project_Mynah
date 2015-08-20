<%@ page language = "java" contentType="text/html; charset=UTF-8" %><%@ page import = "sips.common.lib.value.*" %><%@ page import = "sips.common.lib.util.*" %><%@ page import = "sips.dept.contents.Banner" %><%
    ParamValue     parmValue = new ParamValue(); 
	Banner         banner     = new Banner();
    ResultSetValue rset      = null;
    ResultSetValue rsetP      = null;


    String banner_1 = "";
    String banner_2 = "";
    String banner_3 = "";
    String banner_4 = "";
    String banner_5 = "";
    String banner_6 = "";
    String banner_7 = "";
    String banner_8 = "";
    String banner_9 = "";
    String banner_10 = "";
    String banner_11 = "";
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

    String  conference_id     = request.getParameter("conference_id");
    
    parmValue.put("conference_id", conference_id);
    rset = banner.getBannerInfoXML(parmValue);

    if(rset.next()) {
    	banner_1 = rset.getString("banner_1");
    	banner_2 = rset.getString("banner_2");
    	banner_3 = rset.getString("banner_3");
    	banner_4 = rset.getString("banner_4");
    	banner_5 = rset.getString("banner_5");
    	banner_6 = rset.getString("banner_6");
    	banner_7 = rset.getString("banner_7");
    	banner_8 = rset.getString("banner_8");
    	banner_9 = rset.getString("banner_9");
    	banner_10 = rset.getString("banner_10");
    	banner_11 = rset.getString("banner_11");
    	landing_url_1 = rset.getString("landing_url_1");
    	landing_url_2 = rset.getString("landing_url_2");
    	landing_url_3 = rset.getString("landing_url_3");
    	landing_url_4 = rset.getString("landing_url_4");
    	landing_url_5 = rset.getString("landing_url_5");
    	landing_url_6 = rset.getString("landing_url_6");
    	landing_url_7 = rset.getString("landing_url_7");
    	landing_url_8 = rset.getString("landing_url_8");
    	landing_url_9 = rset.getString("landing_url_9");
    	landing_url_10 = rset.getString("landing_url_10");
    	landing_url_11 = rset.getString("landing_url_11");
    }
%><?xml version="1.0" encoding="utf-8"?> 
<ROOT>
	<BANNER><%
  			if (!"".equals(banner_1)){
  			%><MAIN_BANNER>/mice/upload/banner/<%=banner_1%></MAIN_BANNER>
  		<MAIN_LANDING_URL><%=landing_url_1%></MAIN_LANDING_URL>
  		<%
  			}
			if (!"".equals(banner_2)){
  		%><AGENDA_BANNER>/mice/upload/banner/<%=banner_2%></AGENDA_BANNER>
	  		<AGENDA_LANDING_URL><%=landing_url_2%></AGENDA_LANDING_URL>
	  		<%
  			} 
  			if (!"".equals(banner_3)){
  		%><BINDER_BANNER>/mice/upload/banner/<%=banner_3%></BINDER_BANNER>
  		<BINDER_LANDING_URL><%=landing_url_3%></BINDER_LANDING_URL>
  		<%
  			} 
  			if (!"".equals(banner_4)){
  		%><SEARCH_BANNER>/mice/upload/banner/<%=banner_4%></SEARCH_BANNER>
  		<SEARCH_LANDING_URL><%=landing_url_4%></SEARCH_LANDING_URL>
  		<%
  			} 
  			if (!"".equals(banner_5)){
  		%><MASSAGE_BANNER>/mice/upload/banner/<%=banner_5%></MASSAGE_BANNER>
  		<MASSAGE_LANDING_URL><%=landing_url_5%></MASSAGE_LANDING_URL>
  		<%
  			} 
  			if (!"".equals(banner_6)){
  		%><MYBRIEFACASE_BANNER>/mice/upload/banner/<%=banner_6%></MYBRIEFACASE_BANNER>
  		<MYBRIEFACASE_LANDING_URL><%=landing_url_6%></MYBRIEFACASE_LANDING_URL>
  		<%
  			} 
  			if (!"".equals(banner_7)){
  		%><MAP_BANNER>/mice/upload/banner/<%=banner_7%></MAP_BANNER>
  		<MAP_LANDING_URL><%=landing_url_7%></MAP_LANDING_URL>
  		<%
  			} 
  			if (!"".equals(banner_8)){
  		%><RESEARCH_BANNER>/mice/upload/banner/<%=banner_8%></RESEARCH_BANNER>
  		<RESEARCH_LANDING_URL><%=landing_url_8%></RESEARCH_LANDING_URL>
  		<%
  			} 
  			if (!"".equals(banner_9)){
  		%><CONFIG_BANNER>/mice/upload/banner/<%=banner_9%></CONFIG_BANNER>
  		<CONFIG_LANDING_URL><%=landing_url_9%></CONFIG_LANDING_URL>
  		<%
  			} 
  			if (!"".equals(banner_10)){
  		%><BARCODE_BANNER>/mice/upload/banner/<%=banner_10%></BARCODE_BANNER>
  		<BARCODE_LANDING_URL><%=landing_url_10%></BARCODE_LANDING_URL>
  		<%
  			} 
  			if (!"".equals(banner_11)){
  		%><INFOMATION_BANNER>/mice/upload/banner/<%=banner_11%></INFOMATION_BANNER>
  		<INFOMATION_LANDING_URL><%=landing_url_11%></INFOMATION_LANDING_URL>
  		<%
  			} 
  		%>
  	</BANNER>
</ROOT>