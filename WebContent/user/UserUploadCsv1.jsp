<%--
  - Author(s):          Jay
  - Date:               07-May-2015
  - Copyright Notice:   Copyright (c) 2015 Quix Creation Pte. Ltd. All Rights Reserved.
  - Description:        GUI to search Announcement
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="com.quix.aia.cn.imo.constants.FormInfo"%>
<%@page import="com.quix.aia.cn.imo.data.user.User"%>
<%@page import="com.quix.aia.cn.imo.interfaces.FormPageInterface"%>
<%@page import="com.quix.aia.cn.imo.constants.RequestAttributes"%>
<%@page import="com.quix.aia.cn.imo.mapper.UserMaintenance"%>
<%@page import="com.quix.aia.cn.imo.constants.SessionAttributes"%>
<%@page import="com.quix.aia.cn.imo.constants.PagingInfo"%>
<%@page import="com.quix.aia.cn.imo.data.locale.LocaleObject"%>
<script src="js/imocn.js" type="text/javascript" charset="utf-8"></script>
<script src="js/lms.js" type="text/javascript"></script>

<%

LocaleObject localeObj = (LocaleObject)session.getAttribute(SessionAttributes.LOCALE_OBJ);
String localKey = localeObj.getKey();
User userObj = (User)request.getSession().getAttribute("currUserObj");

%>
 <script type="text/javascript">
 $( document ).ready(function() {
	 $("#submit").hide();
	 
	});
function uploadCSV(lang){
		$('#ajaxLoader').find(".lightbox").show();	
		$("#submit").hide();
		var csvFile = $('#csv_file').get(0).files[0];
		var file_extension=csvFile.name;
		if((file_extension.indexOf('.xlsx')>-1) || (file_extension.indexOf('.XLSX')>-1) ||
				(file_extension.indexOf('.xls')>-1) || (file_extension.indexOf('.XLS')>-1)){
		//	showProgress();
		var fd = new FormData();
		fd.append('csvFile', csvFile);
		var fileName=$("#csv_file").val();
		
		  $.ajax({
				url : 'UploadMaterial?image_name='+fileName,
				type: "POST",
				data: fd,
			   	processData: false,
			   	contentType: false,
			}).done(function(respond){
				
			});
		  $('#ajaxLoader').find(".lightbox").hide();
			if(lang == 'CN')
				 alert("文档上载成功");
			else
		 		 alert("Xcel file Uploaded Successfully");
		  $("#submit").show();
		 
		}
		else{
			 $('#ajaxLoader').find(".lightbox").hide();
			 if(lang == 'CN')
				 document.getElementById("p1").innerHTML = "请上传文件扩展XLSX或XLS";
			 else
				document.getElementById("p1").innerHTML = "Please Upload file with extension xlsx OR xls";
		}
		 $('#ajaxLoader').find(".lightbox").hide();	
}

function userCSVSubmit(action,lang) 
{
	var csvFile = $('input[type=file]').get(0).files[0];
	if(csvFile==null){
		 if(lang == 'CN')
			 alert("先上传文件");
		 else
			alert("First Upload File");
		return false;
		
	}
	$('#ajaxLoader').find(".lightbox").show();	
 document.administrationForm.actionType.value = action ;
 document.administrationForm.submit() ;
  
}
</script>
<style>
@media only screen and (min-device-width: 768px) and (max-device-width: 1024px) {.class_limit{width:260px;}}
@media screen and (min-width:0\0) and (min-resolution: +72dpi) {.class_limit{width:270px;}}
</style>


		<div id="maincontainer">
					<div class="head">
					<h2 align="center"><%=localeObj.getTranslatedText("User Management Upload CSV")%></h2></div>  	
						
					</div>
					<div class="content" style="background-color:#ffffff;">
<form method="post" action="FormManager" name="administrationForm"  >
<input type="hidden" name="token" id="token" value="<%=request.getSession().getAttribute("Token")+"" %>">
<input type="hidden" id="actionType" name="actionType">
<table class="formDesign MB20" >
					<tr>
						<td colspan="2" style="text-align: center">
						<span style="color:#ec2028;">
                     	   <%if(request.getAttribute(com.quix.aia.cn.imo.constants.RequestAttributes.ERROR_OBJ) != null){%>
											
							<%=localeObj.getTranslatedText(((com.quix.aia.cn.imo.utilities.ErrorObject)request.getAttribute(com.quix.aia.cn.imo.constants.RequestAttributes.ERROR_OBJ)).getErrorMsg())%>
							<%}%>                          					
                     </span></td>  <div style="text-shadow: red;" id="p1" name="p1">  </div>
                     <%request.removeAttribute(com.quix.aia.cn.imo.constants.RequestAttributes.ERROR_OBJ);  %>
					</tr>

                   		<tr>                         
                        	<td >	                                    				
                         	<label ><%=localeObj.getTranslatedText("Upload User CSV")%></label> 
                        	</td>
                        	
                        	<td >	
                        	<input type="file" name="csv_file" id="csv_file" class="fileObj" onChange="javascript:uploadCSV('<%=localKey %>')" /> 
                        	</td>
                        	<td>	<a href="format/UserManagement.xls"  target="_new"><%=localeObj.getTranslatedText("Sample Template")%></a></td>
                           			
               		 	</tr>
                            <tr></tr>        
                            </table>
                         <table style="width:55%;">  			   
           			    <tr >                         
             			<td colspan="2" class="MT30 MB30" style="text-align:center;padding-top:20px">
             				<a href="#" class="btn1 "  id="submit" name="submit" onclick="javascript:userCSVSubmit('<%=FormInfo.SUBMIT%>','<%=localKey %>')"><%=localeObj.getTranslatedText("Submit")%></a> &nbsp;&nbsp;
 		 					<a href="ContentManager?key=<%=(String)((com.quix.aia.cn.imo.utilities.PageObj)request.getSession().getAttribute(com.quix.aia.cn.imo.constants.SessionAttributes.PAGE_OBJ)).getKey()%>" class="btn1"><%=localeObj.getTranslatedText("Back")%></a> &nbsp;&nbsp;
            	 			
            	 				<%if(request.getSession().getAttribute("Summarypath")!=null && request.getSession().getAttribute("strbuf")!=null ){ 
            	 				
            	 				String summaryPath=(String)request.getSession().getAttribute("Summarypath");
            	 				%>
            	 					 <a href="<%=summaryPath %>" target="_new" class="btn1"><%=localeObj.getTranslatedText("Download Error Summary")%> </a>
            	 				<% }  request.getSession().removeAttribute("Summarypath");
            	 				request.getSession().removeAttribute("strbuf");%>
            	 			
            	 			
            	 			</td>                                     			   
           			    </tr>
 		 				
			
			</table>
			</form>
     	</div>
    