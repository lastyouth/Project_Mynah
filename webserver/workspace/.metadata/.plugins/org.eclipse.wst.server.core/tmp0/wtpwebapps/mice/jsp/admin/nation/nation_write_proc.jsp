<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<% request.setCharacterEncoding("UTF-8"); %>
<%
	String root_path = request.getContextPath();
%>
<%@ page import = "java.util.*"  %>
<%@ page import = "sips.common.lib.util.*" %>
<%@ page import = "sips.common.lib.value.*" %> 
<%@ page import = "sips.dept.menu.*"   %>
<%@ page import = "sips.dept.contents.Nation"   %>

<%
	Nation            nation     = new Nation();
    ParamValue     parmValue     = new ParamValue();
    ResultSetValue rset          = null;

    String modeType           = StringUtil.defaultIfEmpty(request.getParameter("modeType"    ), "");  
    String nation_id           = StringUtil.defaultIfEmpty(request.getParameter("nation_id"    ), "");  
    String national           = StringUtil.defaultIfEmpty(request.getParameter("national"    ), "");  
    String use_yn           = StringUtil.defaultIfEmpty(request.getParameter("use_yn"    ), "");  
    
    String boxes  = "";
    String duplicateMsg = "";

    if (StringUtil.defaultIfEmpty(request.getParameter("boxes"),null)!= null){
    	String checkBoxes[] = request.getParameterValues("boxes");
    	for (String checkbox : checkBoxes)
    	{
    		boxes =  boxes + checkbox+ ",";
    	}
    	boxes = boxes.substring(0,boxes.length()-1);
    }
    //System.out.println("boxes----------->"+boxes);    //
    String b_mode             = "";
        
    boolean  isOk = true;

    parmValue.put("nation_ids"    , boxes    );
    parmValue.put("national"    , national    );
    parmValue.put("nation_id"    , nation_id    );
    parmValue.put("use_yn"    , use_yn    );
    try {	   
        if( isOk ) {
            if( "ins".equals(modeType)){
            	duplicateMsg = nation.nationInsert(parmValue);
            } else if( "mod".equals(modeType)){
            	duplicateMsg = nation.nationlModify(parmValue);
            } else if( "del".equals(modeType)){  
            	//material.materialDeleteContents(parmValue);  
            }else if( "lDel".equals(modeType)){  
            	//System.out.println("memberListDeleteContents");
            	nation.nationlListDeleteContents(parmValue);  
            }
        }        
%>
<form name="frmBrd" action="nation.jsp" method="post">
</form>
<script language="javascript">
	document.frmBrd.submit();
</script>  
  

<%  }
    catch (Exception e) {
        System.out.println(e.toString());
        out.println(e.toString());
        e.printStackTrace();
    }   
%>

