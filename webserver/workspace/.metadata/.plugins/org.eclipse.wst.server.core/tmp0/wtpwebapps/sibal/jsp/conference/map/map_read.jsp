<%@ page language = "java" contentType="text/html; charset=UTF-8" %>
<%
	String root_path = request.getContextPath();
%>
<%@ page import = "sips.common.lib.util.*" %>
<%@ page import = "sips.common.lib.value.*" %>
<%@ page import = "sips.dept.contents.Map" %>
<%  String formName = "frmBoard"; %>
<%
	ParamValue       parmValue = new ParamValue();      
	Map            map     = new Map();
   
    ResultSetValue   rset      = null;

    String conference_id         = StringUtil.defaultIfEmpty(request.getParameter("conference_id"  ),"");

    String map_image  = "";
    String title  = "";
    String strMsg             = "";
  
    String modeType           = "";

    parmValue.put("conference_id"  , conference_id  );  
    rset = map.getMapInfo(parmValue);
	
%>		
	
    <table width="100%" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td valign="top">
           <table border="0" cellpadding="0" cellspacing="0" style="border-top:2px solid #e0e0e0; border-bottom:2px solid #e0e0e0; width:784px;">
<%           
	int x = 0;
    while( rset.next()){
    	title    = StringUtil.defaultIfEmpty(rset.getString("title"    ), "");
    	map_image    = StringUtil.defaultIfEmpty(rset.getString("map_image"    ), "");
%>
			<tr>
              <td width="210" class="bbbg" style="padding-left:20px; font-weight:bold;"><%=title %></td>
              <td width="574" style="padding-left:20px;">
              	<%
              	if (!"".equals(map_image)){
              		out.println("<img src=/mice/upload/map/"+map_image+">");
              	}
              %>
              </td>
            </tr>	
<%    	
	}
%>    
        </table></td>
      </tr>
    </table>
