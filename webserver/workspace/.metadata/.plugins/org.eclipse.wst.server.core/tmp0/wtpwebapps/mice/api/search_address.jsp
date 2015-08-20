<?xml version="1.0" encoding="utf-8"?> 
<ROOT>
<%@ page language = "java" contentType="text/html; charset=UTF-8" %><%@ page import = "sips.common.lib.value.*" %><%@ page import = "sips.common.lib.util.*" %><%@ page import = "sips.dept.contents.Member" %><%
    ParamValue     parmValue = new ParamValue(); 
	Member         member    = new Member();
    ResultSetValue rset      = null;
    
    String zipcode = "";
	String sido = "";
	String gugun = "";
	String bunji = "";

    String  dong     = request.getParameter("dong");
    if (!"".equals(dong))
    	dong     = new String(dong.getBytes("8859_1"), "utf-8");
    parmValue.put("dong", dong);
    rset = member.getZipcode(parmValue); 

    while(rset.next()) {
    	sido       = StringUtil.defaultIfEmpty(rset.getString("sido"   ), ""); 
    	gugun     = StringUtil.defaultIfEmpty(rset.getString("gugun" ), "");
    	dong    = StringUtil.defaultIfEmpty(rset.getString("dong"), "");
    	bunji          = StringUtil.defaultIfEmpty(rset.getString("bunji"         ), "");
    	zipcode          = StringUtil.defaultIfEmpty(rset.getString("zipcode"         ), "");
    	
    	out.println("		<ADDRESS_LIST>");
    	out.println("			<ZIPCODE>"+rset.getString("zipcode")+"</ZIPCODE>");
    	out.println("			<ADDRESS>"+sido+" "+gugun+" "+dong+" "+bunji+" </ADDRESS>");
    	out.println("		</ADDRESS_LIST>");
    }
%>
</ROOT>