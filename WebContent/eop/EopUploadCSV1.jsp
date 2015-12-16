<%--
  - Author(s):          Jay
  - Date:               07-May-2015
  - Copyright Notice:   Copyright (c) 2015 Quix Creation Pte. Ltd. All Rights Reserved.
  - Description:        GUI to search Announcement
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="com.quix.aia.cn.imo.utilities.MsgObject"%>
<%@page import="com.quix.aia.cn.imo.utilities.ErrorObject"%>
<%@page import="com.quix.aia.cn.imo.constants.RequestAttributes"%>
<%@page import="com.quix.aia.cn.imo.constants.FormInfo"%>
<%@page import="com.quix.aia.cn.imo.constants.SessionAttributes"%>
<%@page import="com.quix.aia.cn.imo.data.locale.LocaleObject"%>

<%
LocaleObject localeObj = (LocaleObject)session.getAttribute(SessionAttributes.LOCALE_OBJ);
String localKey = localeObj.getKey();
%>

<script type="text/javascript">
$(document).ready(function(){
	 $("#submit").hide();
});
 function uploadCSV(lang){
	 $('#ajaxLoader').find(".lightbox").show();
	if($('input[type=file]').get(0).files[0] !=undefined){
		var csvFile = $('input[type=file]').get(0).files[0];
		var file_extension=csvFile.name;
		if((file_extension.indexOf('.xlsx')>-1) || (file_extension.indexOf('.XLSX')>-1) ||
				(file_extension.indexOf('.xls')>-1) || (file_extension.indexOf('.XLS')>-1)){
		var fd = new FormData();
		fd.append('csvFile', csvFile);
		var fileName=$("#csv_file").val();
		$('#downloadlink').hide();
		  $.ajax({
				url : 'UploadMaterial?image_name='+fileName,
				type: "POST",
				data: fd,
			   	processData: false,
			   	contentType: false,
			}).done(function(respond){
				
			});
		  
		  $("#submit").show();
		}
		else{
			 if(lang == 'CN')
				 alert("请上传文件扩展XLSX或XLS");
			 else
				alert("Please Upload file with extension xlsx OR xls");
			if(document.getElementById('csv_file') != null) 
				 document.getElementById('csv_file').outerHTML = document.getElementById('csv_file').outerHTML;
		}
   }
  else{
	  if(lang == 'CN')
		alert("请上传文件");
	  else
		  alert("Please Upload a file");
   }
	$('#ajaxLoader').find(".lightbox").hide();
 }
function eopCSVSubmit(action) 
{
 document.eopCSVForm.actionType.value = action ;
 document.eopCSVForm.submit() ;
  
}

</script>

   	<div id="maincontainer">	
   					<div class="head"><h2 align="center"><%=localeObj.getTranslatedText("Eop Maintenance Upload CSV")%></h2></div>   	
   							<div class="content" style="background-color:#ffffff;">
   							<form name="eopCSVForm" method="post" action="FormManager" class="PT20">
 											<input type="hidden" name="token" id="token" value="<%=request.getSession().getAttribute("Token")+"" %>">
 											<input type="hidden" name="actionType"/>
	                           					<table class="formDesign">
	                           					<tr>
	                           					 <%if(request.getAttribute(RequestAttributes.ERROR_OBJ) == null){%>
		                                  		  <td colspan="2" style="text-align: center"><span style="color:#ec2028;"></span></td>
		                                   		<%}else{%>
		                                   		  <td colspan="2" style="text-align: center"><span style="color:#ec2028;"><%=localeObj.getTranslatedText(((ErrorObject)request.getAttribute(RequestAttributes.ERROR_OBJ)).getErrorMsg())%></span></td>
		                                   		<%}%>
												</tr>
                                				<tr>                         
			                                    	<td>	                                    				
				                                    	<label ><%=localeObj.getTranslatedText("Upload EOP CSV")%></label> 
			                                    	</td>
			                                    	<td>	
			                                    	<input type="file" name="csv_file" id="csv_file" class="fileObj"  onchange="javascript:uploadCSV('<%=localKey%>')"/> 
			                                    	</td>
                                        			<td>	<a href="format/EopScheduleUpload.xls"  target="_new"><%=localeObj.getTranslatedText("Sample Template")%></a></td>                                 			   
                                      			    </tr>
                            		 				
                            		 				<tr><td colspan="3"></td></tr><tr><td colspan="3"></td></tr>
                                      			   
                                      			    <tr>                         
                                        			<td colspan="2" class="MT30 MB30" style="text-align:center;padding-top:20px">
                                        				<a href="#" class="btn1 "  id="submit" onclick="javascript:eopCSVSubmit('<%=FormInfo.SUBMIT%>')"><%=localeObj.getTranslatedText("Submit")%></a> &nbsp;&nbsp;
                            		 					<a href="ContentManager?key=EopMaintenance" class="btn1"><%=localeObj.getTranslatedText("Back")%></a> 
                            		 					
                            		 				<%
                            		 				    if(request.getSession().getAttribute("Summarypath")!=null && request.getSession().getAttribute("strbuf")!=null ){ 
                                       	 	            String summaryPath=(String)request.getSession().getAttribute("Summarypath");
                                       	 	        	request.getSession().removeAttribute("strbuf");
                                       	 	       		request.getSession().removeAttribute("Summarypath");
                                       	 			%>
                                       	 				<a href="<%=summaryPath %>" target="_new" class="btn1" id="downloadlink"><%=localeObj.getTranslatedText("Download Error Summary")%> </a>
                                       	 			<%
                                       	 		      }
                                       	 			%>
                                       	 			</td>                                     			   
                                      			    </tr>
                            		 				
                                       				</table>
                                       			 </form>
                                           
                 
					</div>
				</div>
			


  