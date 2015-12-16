<%--
  - Author(s):          khyati
  - Date:               19-May-2015
  - Copyright Notice:   Copyright (c) 2015 Quix Creation Pte. Ltd. All Rights Reserved.
  - Description:        E-Greeting cards form created
--%>
<%@ page import='java.util.*' %>
<%@ page import='java.text.*' %>
<%@page import="com.quix.aia.cn.imo.constants.SessionAttributes"%>
<%@page import="com.quix.aia.cn.imo.data.locale.LocaleObject"%>
<%@page import="com.quix.aia.cn.imo.constants.RequestAttributes"%>
<%@page import="com.quix.aia.cn.imo.mapper.*"%>
<%@page import="com.quix.aia.cn.imo.data.user.User"%>
<%@page import="com.quix.aia.cn.imo.data.egreeting.*"%>
<%@page import="com.quix.aia.cn.imo.utilities.FormObj"%>
<%@page import="com.quix.aia.cn.imo.constants.FormInfo"%>
<%@page import="com.quix.aia.cn.imo.data.category.FestiveCategory"%>
<%@page import="com.quix.aia.cn.imo.utilities.LMSUtil"%>
<%@page import="com.quix.aia.cn.imo.utilities.ImoUtilityData"%>
<%@page import="com.quix.aia.cn.imo.utilities.SecurityAPI"%>

<script type="text/javascript">
function submitForm(){
	document.eGreetingForm.submit();
}
</script>

<script>
function uploadMaterial(){
	$('#ajaxLoader').find(".lightbox").show();
	//alert($('#ProfileMaterial').val());
	if($('#ProfileMaterial').get(0).files[0] !=undefined){
		
	        
		var materialFile = $('#ProfileMaterial').get(0).files[0];
		var file_name=materialFile.name;
	 	if((file_name.indexOf('.jpeg')>-1) || (file_name.indexOf('.JPEG') >-1)  || (file_name.indexOf('.jpg')>-1) || (file_name.indexOf('.JPG')>-1) 
				 || (file_name.indexOf('.png')>-1) || (file_name.indexOf('.PNG') >-1)  || (file_name.indexOf('.GIF')>-1) || (file_name.indexOf('.gif') >-1)){ 
			//showProgress();
		if(materialFile==undefined)
			materialFile = $('#materialname').html();
		var fd = new FormData();
			fd.append('ProfileMaterial', materialFile);
			var material_name=$("#ProfileMaterial").val();
			var size=$('#ProfileMaterial').get(0).files[0].size;
			if(size<=5242880){
		  $.ajax({
				url : 'UploadMaterial?usedFor=E_Greeting&image_name='+material_name,
				type: "POST",
				data: fd,
			   	processData: false,
			   	contentType: false,
			}).done(function(respond){
				
				$('#materialname').html(file_name);
				$('#ajaxLoader').find(".lightbox").hide();
				  var reader = new FileReader();
			        reader.onload = function (e) {
			            $('#uploadImage').attr('src', e.target.result);
			        }

			        reader.readAsDataURL($('#ProfileMaterial').get(0).files[0]);
			
			});
		//  hideProgress();
			}else{
				$('#ajaxLoader').find(".lightbox").hide();
				alert("Please Upload File Less then 5 MB");
				$('#ProfileMaterial').val('');
			}
		  }else{
				$('#ajaxLoader').find(".lightbox").hide();
			alert("Please upload png/jpg/gif type file");
			$('#ProfileMaterial').val('');
		
		} 
	}else{
		$('#ajaxLoader').find(".lightbox").hide();
		alert("Please Upload a file");
	}
	
}
</script>

<%
String eCode = request.getParameter("E_GREETING_ID");
	LocaleObject localeObj = (LocaleObject) session
			.getAttribute(SessionAttributes.LOCALE_OBJ);
     E_Greeting E_GreetingObj=new E_Greeting();
    boolean modifyFlag = false,errorFlag = false;
    FormObj formDetail = null;
    if(request.getSession().getAttribute(SessionAttributes.FORM_OBJ)!=null)
    {
    	formDetail = (FormObj)request.getSession().getAttribute(SessionAttributes.FORM_OBJ);
    	E_GreetingObj = (E_Greeting)request.getSession().getAttribute(SessionAttributes.FUNCTION_OBJ);
    }
    
    if (formDetail.getFormType().equals(FormInfo.MODIFY_FORM))
    {
    	modifyFlag = true;
    		
    }
    
    User userObj=(User)request.getSession().getAttribute(SessionAttributes.CURR_USER_OBJ);

    EgreetingMaintenance obj=new EgreetingMaintenance();
    ImoUtilityData imoUtilityDate=new ImoUtilityData();
    ArrayList<FestiveCategory> arrFestiveCategory = new ArrayList();
    arrFestiveCategory =obj.getAllFestiveCategory(request);

%>


<div id="maincontainer">
		<div class="head">
			<h2 align="center">
				<%if(modifyFlag == true){ %>
				<%=localeObj.getTranslatedText("Edit E-Greeting")%>
				<%} else {%>
				<%=localeObj.getTranslatedText("Add E-Greeting")%>
				<%} %>
			</h2>
		</div>
		<div class="content" style="background-color:#ffffff;">
			<form action="FormManager" name="eGreetingForm" method="post" class="PT20">
			<input type="hidden" name="token" id="token" value="<%=request.getSession().getAttribute("Token")+"" %>">
				<input type="hidden" name="<%=FormInfo.HIDDEN_ACTION%>" value="SUBMIT"/>
                <input type="hidden" name="EGreetingCode" id="EGreetingCode" value="<%=E_GreetingObj.getEGreetingCode() %>"/> 
				<table class="formDesign" style="width:54%">
					<tr>
						<td colspan="2" style="text-align: center;color:#ec2028;">
						 <%if(request.getAttribute(com.quix.aia.cn.imo.constants.RequestAttributes.ERROR_OBJ) != null){%>
					           <%=localeObj.getTranslatedText(((com.quix.aia.cn.imo.utilities.ErrorObject)request.getAttribute(com.quix.aia.cn.imo.constants.RequestAttributes.ERROR_OBJ)).getErrorMsg())%>
					     <%}%>
						</td>
					</tr>
					<tr>
						<td ><span style="color:#ec2028;">* </span><label><%=localeObj.getTranslatedText("E-Greeting Cards Name")%></label></td>
						<td >
						<%if(request.getAttribute(com.quix.aia.cn.imo.constants.RequestAttributes.ERROR_OBJ) != null){  %>
						<input type="text" id="<%=EgreetingMaintenance.NAME_PARAM %>" name="<%=EgreetingMaintenance.NAME_PARAM %>" class="textObj" value="<%=request.getParameter(EgreetingMaintenance.NAME_PARAM ) %>" maxlength="250">
						<%}else{ %>
						<input type="text" name="<%=EgreetingMaintenance.NAME_PARAM %>" id="<%=EgreetingMaintenance.NAME_PARAM %>"  class="textObj" value="<%=SecurityAPI.encodeHTML(E_GreetingObj.getEGreetingName())%>" maxlength="250" />
						<%} %>
						</td>
						 <td rowspan="3" style="text-align:center;"><img src="<%=E_GreetingObj.getFileLocation() %>"  id = "uploadImage"  name = "uploadImage" height="230px" width="230px" /></td>
					</tr>
					
					<tr>                         
                    	<td><span style="color:#ec2028;">* </span><label ><%=localeObj.getTranslatedText("Festive category")%></label></td>                                    				
                        <td>	
                        	<select name="<%=EgreetingMaintenance.FESTIVE_CATEGORY_PARAM %>" id="<%=EgreetingMaintenance.FESTIVE_CATEGORY_PARAM %>"  class="comboObj">
                   				<option value="%"><%=localeObj.getTranslatedText("All")%></option>
                   				<%
                   				String festive_cat_code="";
                   				if(request.getAttribute(com.quix.aia.cn.imo.constants.RequestAttributes.ERROR_OBJ) != null){  
                   					festive_cat_code=request.getParameter(EgreetingMaintenance.FESTIVE_CATEGORY_PARAM);
		                     	}else{
		                     		festive_cat_code=E_GreetingObj.getFestive_category()+"";
		                     	}
                   				for(int i=0;i<arrFestiveCategory.size();i++){
                   					FestiveCategory categoryObj=(FestiveCategory)arrFestiveCategory.get(i);
                   				%>
                   				<option value="<%=categoryObj.getfestiveCategoryCode()%>" <%=festive_cat_code.equals(categoryObj.getfestiveCategoryCode()+"")?"selected":"" %>><%=SecurityAPI.encodeHTML((categoryObj.getFestiveCategoryName())) %></option>
                   				<%} %>  
                	 		</select>
            	 	    </td>
            	 	   <td></td>
     				</tr>
     				<tr>                         
           				<td>	                                    				
            				<span style="color:#ec2028;">* </span><label ><%=localeObj.getTranslatedText("Material Upload")%></label>
            				</br><label>&nbsp;&nbsp;  <%=localeObj.getTranslatedText("Maximum 5MB")%></label>  
           				</td>
               			<td>	
               				<input name="ProfileMaterial" id="ProfileMaterial" type="file" class="fileObj" onchange="uploadMaterial();" />               
              	 		</td> 
              	 		<td></td>
             		</tr>
             		<tr>
						<td ></td>
						 <%String fname = ""; 
						 if(E_GreetingObj.getFileLocation()!=null && E_GreetingObj.getFileLocation().length() > 0){
							 fname = E_GreetingObj.getFileLocation().substring(E_GreetingObj.getFileLocation().lastIndexOf('/') + 1);
							 fname =  E_GreetingObj.getFileName();
						 }
						 %>
						<td ><div id="materialname"><%=fname %></div></td>
					</tr>
					
				<%-- 	<tr>                         
	                    <td><label><%=localeObj.getTranslatedText("Created By")%></label></td>	                                    				
	                    <td>
	                     <%	String createdBy = "";
	                     	if(request.getAttribute(com.quix.aia.cn.imo.constants.RequestAttributes.ERROR_OBJ) != null){
	                     		createdBy =  request.getParameter(EgreetingMaintenance.CREATED_BY_PARAM );
	                     	}
	                     	else if(modifyFlag){
	                     		createdBy = SecurityAPI.encodeHTML(String.valueOf(E_GreetingObj.getCreated_by()));
	                     	}
	                     	else{
	                     		createdBy = SecurityAPI.encodeHTML(userObj.getStaffName());
	                     	} 
	                      %>	 
	                      <input name="<%=EgreetingMaintenance.CREATED_BY_PARAM %>" id="<%=EgreetingMaintenance.CREATED_BY_PARAM %>" type="text" class="textObj" value="<%=createdBy %>" readonly="readonly" />
	                      </td>                                     			   
                     </tr> --%>
                     <tr >
						<td colspan="2" class="MT30 MB30" style="text-align:center;padding-top:20px">
							<%if(modifyFlag){%>
								<a href="javascript:submitForm()" class="btn1" ><%=localeObj.getTranslatedText("Modify")%></a>
							<%}else{ %>
                            	<a href="javascript:submitForm()" class="btn1 "><%=localeObj.getTranslatedText("Submit")%></a>
                            <%} %>	
							<a href="ContentManager?key=E_Greeting" class="ML10 btn1"><%=localeObj.getTranslatedText("Back")%></a>
						</td>
					 </tr> 
					
				</table>
			</form>
		</div>
</div>
<br>