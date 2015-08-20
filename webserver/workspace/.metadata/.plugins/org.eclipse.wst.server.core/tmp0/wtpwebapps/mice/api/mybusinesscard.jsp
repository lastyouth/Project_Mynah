<%@ page language = "java" contentType="text/html; charset=UTF-8" %><%@ page import = "sips.common.lib.value.*" %><%@ page import = "sips.common.lib.util.*" %><%@ page import = "sips.dept.contents.Member" %><%
    ParamValue     parmValue = new ParamValue(); 
	Member         member     = new Member();
    ResultSetValue rset      = null;
    ResultSetValue rsetP      = null;


    String name = "";
    String company = "";
    String appellation_id = "";
    String appellation_name = "";
    String phone1 = "";
    String phone2 = "";
    String phone3 = "";
    String email = "";
    String picture = "";
    String street_address = "";
    String street_address_detail = "";
    String city = "";
    String state = "";
    String postal_code = "";
    String businesscard_share = "";
    String nation = "";
    String nation_id = "";

    String  user_cd     = request.getParameter("user_cd");
    
    parmValue.put("user_cd", user_cd);
    rset = member.getMemberInfo(parmValue);

    if(rset.next()) {
    	name = rset.getString("name");
    	company = rset.getString("company");
    	appellation_id = rset.getString("appellation_id");
    	appellation_name = rset.getString("appellation_name");
    	email = rset.getString("email");
    	picture = rset.getString("picture");
    	street_address = rset.getString("street_address");
    	street_address_detail = rset.getString("street_address_detail");
    	city = rset.getString("city");
    	state = rset.getString("state");
    	postal_code = rset.getString("postal_code");
    	businesscard_share = rset.getString("businesscard_share");
    	nation = rset.getString("nation");
    	nation_id = rset.getString("nation_id");
    	
    	if ("h".equals(rset.getString("first_phone"))){
    		phone1 = rset.getString("handphone1");
        	phone2 = rset.getString("handphone2");
        	phone3 = rset.getString("handphone3");
    	}else{
    		phone1 = rset.getString("phone1");
        	phone2 = rset.getString("phone2");
        	phone3 = rset.getString("phone3");
    	}
    }
%><?xml version="1.0" encoding="utf-8"?> 
<ROOT>
	<BUSINESS_CARD>
  		<USER_NAME><%=name%></USER_NAME>
  		<COMPANY><%=company%></COMPANY>
  		<APPELLATION_ID><%=appellation_id%></APPELLATION_ID>
  		<APPELLATION_NAME><%=appellation_name%></APPELLATION_NAME>
  		<EMAIL><%=email%></EMAIL>
  		<STREET_ADDRESS><%=street_address%></STREET_ADDRESS>
  		<STREET_ADDRESS_DETAIL><%=street_address_detail%></STREET_ADDRESS_DETAIL>
  		<CITY><%=city%></CITY>
  		<STATE><%=state%></STATE>
  		<POSTAL_CODE><%=postal_code%></POSTAL_CODE>
  		<PHONE_1><%=phone1%></PHONE_1>
  		<PHONE_2><%=phone2%></PHONE_2>
  		<PHONE_3><%=phone3%></PHONE_3>
  		<PICTURE>/mice/upload/member/<%=picture%></PICTURE>
  		<BUSINESSCARD_SHARE><%=businesscard_share%></BUSINESSCARD_SHARE>
  		<NATION><%=nation%></NATION>
  		<NATION_ID><%=nation_id%></NATION_ID>
  	</BUSINESS_CARD>
</ROOT>