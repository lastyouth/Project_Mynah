<%@ page language = "java" contentType="text/html; charset=UTF-8" %><%@ page import = "sips.common.lib.value.*" %><%@ page import = "sips.common.lib.util.*" %><%@ page import = "sips.dept.contents._Member" %><%
    ParamValue     parmValue = new ParamValue(); 
	_Member         member     = new _Member();
    ResultSetValue rset      = null;
    ResultSetValue rsetP      = null;

    String  platform     = request.getParameter("platform");
    String  user_cd   = request.getParameter("user_cd");
    String  push_key   = request.getParameter("push_key");

    String conference_name   = "";
    String conference_id   = "";
    String start_date   = "";
    String end_date   = "";
    String authoriry_1   = "false";
    String authoriry_2   = "false";
    String authoriry_3   = "false";
    String authoriry_4   = "false";
    String authoriry_5   = "false";
    String authoriry_6   = "false";
    String authoriry_7   = "false";
    String authoriry_8   = "false";
    String authoriry_9   = "false";
    String banner_image   = "";
    String qr_image   = "";
    String info_image   = "";
    String research_authoriry = "";
    String question_authority = "";
    
    parmValue.put("user_cd", user_cd);
    parmValue.put("platform", platform);
    parmValue.put("push_key", push_key);
    rset = member.getAuthority(parmValue);

    if(rset.next()) {
    	conference_id = rset.getString("conference_id");
    	conference_name = rset.getString("conference_name");
    	start_date = rset.getString("start_date");
    	end_date = rset.getString("end_date");
    	authoriry_1 = rset.getString("authoriry_1");
    	authoriry_2 = rset.getString("authoriry_2");
    	authoriry_3 = rset.getString("authoriry_3");
    	authoriry_4 = rset.getString("authoriry_4");
    	authoriry_5 = rset.getString("authoriry_5");
    	authoriry_6 = rset.getString("authoriry_6");
    	authoriry_7 = rset.getString("authoriry_7");
    	authoriry_8 = rset.getString("authoriry_8");
    	authoriry_9 = rset.getString("authoriry_9");
    	banner_image = rset.getString("banner_image");
    	qr_image = rset.getString("qr_images");
    	info_image = rset.getString("info_image");
    	research_authoriry = rset.getString("research_authoriry");
    	question_authority = rset.getString("question_authority");
    }
%><?xml version="1.0" encoding="utf-8"?> 
<ROOT>
	<LOGIN>
  		<CONFERENCE_ID><%=conference_id%></CONFERENCE_ID>
  		<CONFERENCE_NAME><%=conference_name%></CONFERENCE_NAME>
  		<START_DATE><%=start_date%></START_DATE>
  		<END_DATE><%=end_date%></END_DATE>
  		<CONFERENCE_BANNER>/mice/upload/conference/<%=banner_image%></CONFERENCE_BANNER>
  		<CONFERENCE_QR_IMAGE>/mice/upload/qr/<%=qr_image%></CONFERENCE_QR_IMAGE>
  		<CONFERENCE_INFO_IMAGE>/mice/upload/conference/<%=info_image%></CONFERENCE_INFO_IMAGE>
  		<RESEARCH_AUTHORITY><%=research_authoriry%></RESEARCH_AUTHORITY>
  		<QUESTION_AUTHORITY><%=question_authority%></QUESTION_AUTHORITY>
  		<AUTHORITY>
  			<AGENDA><%=authoriry_1%></AGENDA>
  			<BINDER><%=authoriry_2%></BINDER>
  			<SEARCH><%=authoriry_3%></SEARCH>
  			<MESSAGE><%=authoriry_4%></MESSAGE>
  			<MYBRIEFCASE><%=authoriry_5%></MYBRIEFCASE>
  			<MAP><%=authoriry_6%></MAP>
  			<RESEARCH><%=authoriry_7%></RESEARCH>
  			<CONFIGURATION><%=authoriry_8%></CONFIGURATION>
  			<BARCODE><%=authoriry_9%></BARCODE>
  		</AUTHORITY>
  	</LOGIN>
</ROOT>