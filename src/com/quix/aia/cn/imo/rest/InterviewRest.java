/*******************************************************************************
* -----------------------------------------------------------------------------
* <br>
* <p><b>Copyright (c) 2014 Quix Creation Pte. Ltd. All Rights Reserved.</b> 
* <br>
* <br>
* This SOURCE CODE FILE, which has been provided by Quix as part
* of a Quix Creations product for use ONLY by licensed users of the product,
* includes CONFIDENTIAL and PROPRIETARY information of Quix Creations.
* <br>
* USE OF THIS SOFTWARE IS GOVERNED BY THE TERMS AND CONDITIONS
* OF THE LICENSE STATEMENT AND LIMITED WARRANTY FURNISHED WITH
* THE PRODUCT.<br>
* <br>
* </p>
* -----------------------------------------------------------------------------
* <br>
* <br>
* Modification History:
* Date              Developer          Change Description
* 10-June-2015          Jay               File Added             
* 14-August-2015    	Maunish            Modified 
* 20-August-2015    	Nibedita            Email sent after Interview registration 
* 02-Sept-2015           Nibedita          Download file service added
* 18-Nov-2015            Nibedita          Error stored in db
***************************************************************************** */

package com.quix.aia.cn.imo.rest;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.reflect.TypeToken;
import com.quix.aia.cn.imo.data.auditTrail.AuditTrail;
import com.quix.aia.cn.imo.data.common.AamData;
import com.quix.aia.cn.imo.data.interview.InterviewCandidate;
import com.quix.aia.cn.imo.data.interview.InterviewMaterial;
import com.quix.aia.cn.imo.mapper.AamDataMaintenance;
import com.quix.aia.cn.imo.mapper.AddressBookMaintenance;
import com.quix.aia.cn.imo.mapper.AuditTrailMaintenance;
import com.quix.aia.cn.imo.mapper.InterviewAttendanceMaintenance;
import com.quix.aia.cn.imo.mapper.InterviewMaintenance;
import com.quix.aia.cn.imo.mapper.LogsMaintenance;
import com.quix.aia.cn.imo.utilities.EmailNotification;
import com.quix.aia.cn.imo.utilities.LMSUtil;

/**
 * <p>Logic to get Interview for Rest service.</p>
 * 
 * @author Jay
 * @version 1.0
 *
 */


@Path("/interview")
public class InterviewRest {
	static Logger log = Logger.getLogger(InterviewRest.class.getName());
	
	@GET
	@Path("/getAllInterview")
	@Produces({MediaType.APPLICATION_JSON})
	public Response getAllInterview(@Context HttpServletRequest request,
						   @Context ServletContext context)
	{
		log.log(Level.INFO,"InterviewRest --> getInterview ");
		MsgBeans beans = new MsgBeans();
		String agentId = request.getParameter("agentId");
		String coBranch = request.getParameter("co");
		String candidateCode = request.getParameter("candidateCode");
		candidateCode = null == candidateCode?"":candidateCode;
		 AuditTrailMaintenance auditTrailMaint=new AuditTrailMaintenance();
		try{
			ArrayList list = new ArrayList();
			InterviewMaintenance objInterviewMaintenance = new InterviewMaintenance();
			AamData aamData = AamDataMaintenance.retrieveDataToModel(agentId, coBranch); 
			list = objInterviewMaintenance.getAllInterviewRest(aamData, agentId, candidateCode);
			Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").setExclusionStrategies().create(); //.serializeNulls()
		    String json = gson.toJson(list);
		    auditTrailMaint.insertAuditTrail(new AuditTrail("Rest", AuditTrail.MODULE_ANNOUNCEMENT, AuditTrail.FUNCTION_REST, "SUCCESS"));
			return Response.status(200).entity(json).build();
		}catch(Exception e){
			log.log(Level.INFO,"InterviewRest --> getInterview --> Exception..... ");
			log.log(Level.SEVERE, e.getMessage());
			e.printStackTrace();
			StringWriter errors = new StringWriter();
			e.printStackTrace(new PrintWriter(errors));
			LogsMaintenance logsMain=new LogsMaintenance();
			logsMain.insertLogs("InterviewRest",Level.SEVERE+"",errors.toString());
			
			auditTrailMaint.insertAuditTrail(new AuditTrail("Rest", AuditTrail.MODULE_ANNOUNCEMENT, AuditTrail.FUNCTION_FAIL, "FAILED"));
			beans.setCode("500");
			beans.setMassage("Database Error");
			return Response.status(500).entity(new Gson().toJson(beans)).build();
		}
	}
	
	@GET
	@Path("/getAllDeletedInterview")
	@Produces({MediaType.APPLICATION_JSON})
	public Response getAllDeletedInterview(@Context HttpServletRequest request,
						   @Context ServletContext context)
	{
		log.log(Level.INFO,"InterviewRest --> getAllDeletedInterview ");
		MsgBeans beans = new MsgBeans();
		 AuditTrailMaintenance auditTrailMaint=new AuditTrailMaintenance();
		try{
			ArrayList list = new ArrayList();
			list = new InterviewMaintenance().getAllDeletedInterviewRest();
			Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").setExclusionStrategies().create(); //.serializeNulls()
		    String json = gson.toJson(list);
		    auditTrailMaint.insertAuditTrail(new AuditTrail("Rest", AuditTrail.MODULE_ANNOUNCEMENT, AuditTrail.FUNCTION_REST, "SUCCESS"));
			return Response.status(200).entity(json).build();
		}catch(Exception e){
			log.log(Level.INFO,"InterviewRest --> getAllDeletedInterview --> Exception..... ");
			log.log(Level.SEVERE, e.getMessage());
			e.printStackTrace();
			StringWriter errors = new StringWriter();
			e.printStackTrace(new PrintWriter(errors));
			LogsMaintenance logsMain=new LogsMaintenance();
			logsMain.insertLogs("InterviewRest",Level.SEVERE+"",errors.toString());
			
			auditTrailMaint.insertAuditTrail(new AuditTrail("Rest", AuditTrail.MODULE_ANNOUNCEMENT, AuditTrail.FUNCTION_FAIL, "FAILED"));
			beans.setCode("500");
			beans.setMassage("Database Error");
			return Response.status(500).entity(new Gson().toJson(beans)).build();
		}
	}
	
	
	@GET
	@Path("/getAllInterviewPast")
	@Produces({MediaType.APPLICATION_JSON})
	public Response getAllInterviewPast(@Context HttpServletRequest request,
						   @Context ServletContext context)
	{
		log.log(Level.INFO,"InterviewRest --> getInterview ");
		MsgBeans beans = new MsgBeans();
		String agentId = request.getParameter("agentId");
		String coBranch = request.getParameter("co");
		String candidateCode = request.getParameter("candidateCode");
		candidateCode = null == candidateCode?"":candidateCode;
		 AuditTrailMaintenance auditTrailMaint=new AuditTrailMaintenance();
		try{
			ArrayList list = new ArrayList();
			InterviewMaintenance objInterviewMaintenance = new InterviewMaintenance();
//			AamData aamData = AamDataMaintenance.retrieveDataToModel(agentId, coBranch); 
//			list = objInterviewMaintenance.getAllInterviewRestPast(aamData, agentId, candidateCode);
			
			list = objInterviewMaintenance.getAllInterviewRestPast(agentId, candidateCode);
			Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").setExclusionStrategies().create(); //.serializeNulls()
		    String json = gson.toJson(list);
		    auditTrailMaint.insertAuditTrail(new AuditTrail("Rest", AuditTrail.MODULE_ANNOUNCEMENT, AuditTrail.FUNCTION_REST, "SUCCESS"));
			return Response.status(200).entity(json).build();
		}catch(Exception e){
			log.log(Level.INFO,"InterviewRest --> getInterview --> Exception..... ");
			log.log(Level.SEVERE, e.getMessage());
			e.printStackTrace();
			StringWriter errors = new StringWriter();
			e.printStackTrace(new PrintWriter(errors));
			LogsMaintenance logsMain=new LogsMaintenance();
			logsMain.insertLogs("InterviewRest",Level.SEVERE+"",errors.toString());
			
			auditTrailMaint.insertAuditTrail(new AuditTrail("Rest", AuditTrail.MODULE_ANNOUNCEMENT, AuditTrail.FUNCTION_FAIL, "FAILED"));
			beans.setCode("500");
			beans.setMassage("Database Error");
			return Response.status(500).entity(new Gson().toJson(beans)).build();
		}
	}
	
	
	@GET
	@Path("/getInterviewRegisteredCandidate")
	@Produces({MediaType.APPLICATION_JSON})
	public Response getEOPRegistration(@Context HttpServletRequest request,
						   		 	   @Context ServletContext context)
	{
		log.log(Level.INFO,"InterviewRest --> getInterviewRegisteredCandidate");
		MsgBeans beans = new MsgBeans();
		AuditTrailMaintenance auditTrailMaint=new AuditTrailMaintenance();
		String interviewCode = request.getParameter("interviewCode");
		interviewCode = interviewCode==null||interviewCode.equals("")?"0":interviewCode;
		try
		{
			request.setAttribute("isRest", true);
			ArrayList list = new ArrayList();
			InterviewAttendanceMaintenance objMaintenance = new InterviewAttendanceMaintenance();
			list = objMaintenance.getAttendanceList(request,Integer.parseInt(interviewCode));
			for(Iterator itr=list.iterator();itr.hasNext();){
				InterviewCandidate interviewCandidate = (InterviewCandidate) itr.next();
				if(null != interviewCandidate.getInterviewResult() && "p".equalsIgnoreCase(interviewCandidate.getInterviewResult())){
					interviewCandidate.setPassedStatus("PASS");
				}else{
					interviewCandidate.setPassedStatus("FAIL");
				}
			}
			Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").setExclusionStrategies().create(); //.serializeNulls()
		    String json = gson.toJson(list);
		    auditTrailMaint.insertAuditTrail(new AuditTrail("Rest", AuditTrail.MODULE_INTERVIEW_REG, AuditTrail.FUNCTION_REST, "SUCCESS"));
			return Response.status(200).entity(json).build();
		}
		catch(Exception e){
			log.log(Level.INFO,"InterviewRest --> getInterviewRegisteredCandidate --> Exception..... ");
			log.log(Level.SEVERE, e.getMessage());
			e.printStackTrace();
			StringWriter errors = new StringWriter();
			e.printStackTrace(new PrintWriter(errors));
			LogsMaintenance logsMain=new LogsMaintenance();
			logsMain.insertLogs("InterviewRest",Level.SEVERE+"",errors.toString());
			
			auditTrailMaint.insertAuditTrail(new AuditTrail("Rest", AuditTrail.MODULE_INTERVIEW_REG, AuditTrail.FUNCTION_FAIL, "FAILED"));
			beans.setCode("500");
			beans.setMassage("Database Error");
			return Response.status(500).entity(new Gson().toJson(beans)).build();
		}
	}
	
	@GET
	@Path("/getInterviewRegisteredCandidateCount")
	@Produces({MediaType.APPLICATION_JSON})
	public Response getInterviewRegisteredCandidateCount(@Context HttpServletRequest request,
						   		 	   @Context ServletContext context)
	{
		log.log(Level.INFO,"InterviewRest --> getInterviewRegisteredCandidateCount");
		String agentId = request.getParameter("agentId");
		AuditTrailMaintenance auditTrailMaint=new AuditTrailMaintenance();
		String interviewCode = request.getParameter("interviewCode");
		interviewCode = interviewCode==null||interviewCode.equals("")?"0":interviewCode;
		Integer registeredCount = 0;
		try
		{
			request.setAttribute("isRest", true);
			ArrayList list = new ArrayList();
			InterviewAttendanceMaintenance objMaintenance = new InterviewAttendanceMaintenance();
			
			list = objMaintenance.getAttendanceList(request,Integer.parseInt(interviewCode));
			registeredCount = list.size();
		    auditTrailMaint.insertAuditTrail(new AuditTrail("Rest", AuditTrail.MODULE_EOP, AuditTrail.FUNCTION_REST, "SUCCESS"));
		}
		catch(Exception e){
			log.log(Level.INFO,"EventRest --> getEOPRegisteredCandidateCount --> Exception..... ");
			log.log(Level.SEVERE, e.getMessage());
			e.printStackTrace();
			StringWriter errors = new StringWriter();
			e.printStackTrace(new PrintWriter(errors));
			LogsMaintenance logsMain=new LogsMaintenance();
			logsMain.insertLogs("InterviewRest",Level.SEVERE+"",errors.toString());
			
			auditTrailMaint.insertAuditTrail(new AuditTrail("Rest", AuditTrail.MODULE_EOP, AuditTrail.FUNCTION_FAIL, "FAILED"));
		}
		
		return Response.status(200).entity("[{\"registeredCount\":"+registeredCount+"}]").build();
	}
	
	
//	[{"candidateCode":0,"interviewCode":86,"candidateName":"BHP","servicingAgent":"S00012","sourceOfReferal":"GGG","age":19,"dob":"2013-07-04 00:00:00","dobStr":"","gender":"F","contactNumber":"1234567890","ccTestResult":"","recruitmentScheme":"HA","p100":0,"interviewResult":"","remarks":"","statusStr":"true","token":""}]
	@POST
	@Path("/candidateRegister")
	@Consumes(MediaType.TEXT_PLAIN)
	@Produces({MediaType.APPLICATION_JSON})
	public Response candidateRegister(@Context HttpServletRequest request,
						   		   @Context ServletContext context,
						   		   String jsonString)
	{
		log.log(Level.INFO,"InterviewRest --> candidateRegister");
	    log.log(Level.INFO,"InterviewRest --> candidateRegister --> Data for Candidate Registration...  ::::: "+jsonString);
		boolean status=false;
		boolean isDuplicate = false;
		Integer registeredCount = 0;
		MsgBeans beans = new MsgBeans();
		String agentId = request.getParameter("agentId");
		String coBranch = request.getParameter("co");
		 AuditTrailMaintenance auditTrailMaint=new AuditTrailMaintenance();
		try{
			AamData aamData = AamDataMaintenance.retrieveDataToModel(agentId, coBranch); 
			InterviewAttendanceMaintenance objMaintenance = new InterviewAttendanceMaintenance();
			GsonBuilder builder = new GsonBuilder();
	        builder.registerTypeAdapter(Date.class, new JsonDeserializer<Date>() { 
	               @Override  
	               public Date deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            	   		Date date = LMSUtil.convertDateToyyyymmddhhmmssDashed(json.getAsString());
            	   		if(null != date){
            	   			return date;
            	   		}else{
            	   			return  LMSUtil.convertDateToyyyy_mm_dd(json.getAsString());
            	   		}
	               }
	           });
	        
	        Gson googleJson  = builder.create();
	        Type listType = new TypeToken<List<InterviewCandidate>>(){}.getType();
	        List<InterviewCandidate> jsonObjList = googleJson.fromJson(jsonString, listType);
	        InterviewCandidate candidate = jsonObjList.get(0);  
	        candidate.setAgentName(aamData.getAgentName());
	        candidate.setBuName(aamData.getBu());
	        candidate.setDistName(aamData.getDistrict());
	        candidate.setCityName(aamData.getCity());
	        candidate.setSscName(aamData.getSsc());
	        candidate.setAgencyLeaderCode(aamData.getLeaderCode());
	        candidate.setInterviewCandidateCode(""+candidate.getCandidateCode());
	        candidate.setBuCode(aamData.getBuCode());
	        candidate.setDistrictCode(aamData.getDistrictCode());
	        candidate.setCityCode(aamData.getCity());
	        candidate.setSscCode(aamData.getSsc());
	        candidate.setOfficeCode(aamData.getOfficeCode());
	        if(candidate.getStatusStr() != null && candidate.getStatusStr().equalsIgnoreCase("true"))
	        	candidate.setStatus(true);
	        else
	        	candidate.setStatus(false);
	        
	        AddressBookMaintenance addrBookMain = new AddressBookMaintenance();
	        String nric = addrBookMain.getNric(Integer.parseInt(candidate.getInterviewCandidateCode()));
	        candidate.setNric(nric);
	        
//	        candidate.setAgencyLeaderName(aamData.getTeamName());
	      
	        if(!objMaintenance.checkDuplicateCandiadteReg(""+candidate.getInterviewCode(), candidate.getServicingAgent(), candidate.getInterviewCandidateCode())){
		        objMaintenance.createNewCandidate(candidate,request);
		        String emailAddrs = addrBookMain.getEmailAddress(Integer.parseInt(candidate.getInterviewCandidateCode()));
		        if(emailAddrs!=null && emailAddrs.length()>0){
		        	EmailNotification.sendInterviewRegEmailNotification(candidate,emailAddrs,aamData);
		        }

		        String conditionFieldName[]={"addressCode"};
		        String conditionFieldValue[]={candidate.getInterviewCandidateCode()};
		        new AddressBookMaintenance().updateAddressBookStatus("2/9", conditionFieldName, conditionFieldValue);
		        
		        List<InterviewCandidate> list1 = objMaintenance.getAttendanceList(request,candidate.getInterviewCode());
		        registeredCount = list1.size();
		        auditTrailMaint.insertAuditTrail(new AuditTrail("Rest", AuditTrail.MODULE_INTERVIEW_REG, AuditTrail.FUNCTION_REST, "SUCCESS"));
			    status=true; 
	        }else{
	            auditTrailMaint.insertAuditTrail(new AuditTrail("Rest", AuditTrail.MODULE_INTERVIEW_REG, AuditTrail.FUNCTION_REST, "FAIL"));
			    isDuplicate =true; 
	        }
		}
		catch(Exception e){
			log.log(Level.INFO,"InterviewRest --> candidateRegister --> Exception..... ");
			log.log(Level.SEVERE, e.getMessage());
			e.printStackTrace();
			StringWriter errors = new StringWriter();
			e.printStackTrace(new PrintWriter(errors));
			LogsMaintenance logsMain=new LogsMaintenance();
			logsMain.insertLogs("InterviewRest",Level.SEVERE+"",errors.toString());
			
			auditTrailMaint.insertAuditTrail(new AuditTrail("Rest", AuditTrail.MODULE_INTERVIEW_REG, AuditTrail.FUNCTION_FAIL, "FAILED"));
			beans.setCode("500");
			beans.setMassage("Database Error");
		}
		
		return Response.status(200).entity("[{\"status\":"+status+",\"isDuplicate\":"+isDuplicate+",\"registeredCount\":"+registeredCount+"}]").build();
	}
	
	@GET
	@Path("/getCandidateInterviewStatus")
	@Produces({MediaType.APPLICATION_JSON})
	public Response getCandidateInterviewStatus(@Context HttpServletRequest request,
						   		   @Context ServletContext context)
	{
		log.log(Level.INFO,"InterviewRest --> getCandidateInterviewStatus");
		MsgBeans beans = new MsgBeans();
		AuditTrailMaintenance auditTrailMaint=new AuditTrailMaintenance();
		String interviewStatus = "";
		String candidateCode = request.getParameter("candidateCode");
		candidateCode = candidateCode==null||candidateCode.equals("")?"0":candidateCode;
		String interviewType = request.getParameter("interviewType");
		interviewType = interviewType==null||interviewType.equals("")?"0":interviewType;
		try
		{
			InterviewAttendanceMaintenance objMaintenance = new InterviewAttendanceMaintenance();
			InterviewCandidate interviewCandidate = objMaintenance.getCandidateInterviewDetail(candidateCode, interviewType);
			
			if(null == interviewCandidate){
				interviewStatus = "No Status";
			}else{
				interviewStatus = interviewCandidate.getInterviewResult();
			}
			
			String responseJsonString = "[{\"interviewStatus\":\""+interviewStatus+"\"}]";
		    auditTrailMaint.insertAuditTrail(new AuditTrail("Rest", AuditTrail.MODULE_INTERVIEW, AuditTrail.FUNCTION_REST, "SUCCESS"));
			return Response.status(200).entity(responseJsonString).build();
		}
		catch(Exception e){
			log.log(Level.INFO,"InterviewRest --> getCandidateInterviewStatus --> Exception..... ");
			log.log(Level.SEVERE, e.getMessage());
			e.printStackTrace();
			StringWriter errors = new StringWriter();
			e.printStackTrace(new PrintWriter(errors));
			LogsMaintenance logsMain=new LogsMaintenance();
			logsMain.insertLogs("InterviewRest",Level.SEVERE+"",errors.toString());
			
			auditTrailMaint.insertAuditTrail(new AuditTrail("Rest", AuditTrail.MODULE_INTERVIEW, AuditTrail.FUNCTION_FAIL, "FAILED"));
			beans.setCode("500");
			beans.setMassage("Database Error");
			return Response.status(500).entity(new Gson().toJson(beans)).build();
		}
	}
	
	@GET
	@Path("/deleteInterviewRegistration")
	@Produces({MediaType.APPLICATION_JSON})
	public Response deleteInterviewRegistration(@Context HttpServletRequest request,
						   		   @Context ServletContext context)
	{
		log.log(Level.INFO,"InterviewRest --> deleteInterviewRegistration");
		String responseString = "[{\"status\":";
		MsgBeans beans = new MsgBeans();
		AuditTrailMaintenance auditTrailMaint=new AuditTrailMaintenance();
		String candidateCode = request.getParameter("candidateCode");
		candidateCode = candidateCode==null||candidateCode.equals("")?"0":candidateCode;
		String interviewCode = request.getParameter("interviewCode");
		interviewCode = interviewCode==null||interviewCode.equals("")?"0":interviewCode;
		request.setAttribute("isRest", true);
		try
		{
			InterviewAttendanceMaintenance objMaintenance = new InterviewAttendanceMaintenance();
			objMaintenance.deleteCandidateReg(Integer.parseInt(candidateCode), Integer.parseInt(interviewCode), request);
		    auditTrailMaint.insertAuditTrail(new AuditTrail("Rest", AuditTrail.MODULE_INTERVIEW, AuditTrail.FUNCTION_REST, "SUCCESS"));
		    responseString +=true; 
		}
		catch(Exception e){
			log.log(Level.INFO,"InterviewRest --> deleteInterviewRegistration --> Exception..... ");
			log.log(Level.SEVERE, e.getMessage());
			e.printStackTrace();
			StringWriter errors = new StringWriter();
			e.printStackTrace(new PrintWriter(errors));
			LogsMaintenance logsMain=new LogsMaintenance();
			logsMain.insertLogs("InterviewRest",Level.SEVERE+"",errors.toString());
			
			auditTrailMaint.insertAuditTrail(new AuditTrail("Rest", AuditTrail.MODULE_INTERVIEW, AuditTrail.FUNCTION_FAIL, "FAILED"));
			beans.setCode("500");
			beans.setMassage("Database Error");
		    responseString +=false; 
		}
		
		responseString+="}]";
		return Response.status(200).entity(responseString).build();

	}
	
	@GET
	@Path("/getCCTestResults")
	@Produces({MediaType.APPLICATION_JSON})
	public Response getCCTestResults(@Context HttpServletRequest request,
						   		   @Context ServletContext context)
	{
		log.log(Level.INFO,"InterviewRest --> getCCTestResults");
		MsgBeans beans = new MsgBeans();
		AuditTrailMaintenance auditTrailMaint=new AuditTrailMaintenance();
		String candidateCode = request.getParameter("candidateCode");
		candidateCode = candidateCode==null||candidateCode.equals("")?"0":candidateCode;
		String agentId = request.getParameter("agentId");
		agentId = agentId==null||agentId.equals("")?"0":agentId;
		String interviewCode = request.getParameter("interviewCode");
		interviewCode = interviewCode==null||interviewCode.equals("")?"0":interviewCode;
		try
		{
			InterviewAttendanceMaintenance objMaintenance = new InterviewAttendanceMaintenance();
			InterviewCandidate interviewCandidate = objMaintenance.getAttendanceCandidateDetails(request, Integer.parseInt(interviewCode), Integer.parseInt(candidateCode));
			String responseJsonString = "[{\"ccTestResult\":\""+interviewCandidate.getCcTestResult()+"\"}]";
		    auditTrailMaint.insertAuditTrail(new AuditTrail("Rest", AuditTrail.MODULE_INTERVIEW, AuditTrail.FUNCTION_REST, "SUCCESS"));
			return Response.status(200).entity(responseJsonString).build();
		}
		catch(Exception e){
			log.log(Level.INFO,"EventRest --> deleteInterviewRegistration --> Exception..... ");
			log.log(Level.SEVERE, e.getMessage());
			e.printStackTrace();
			StringWriter errors = new StringWriter();
			e.printStackTrace(new PrintWriter(errors));
			LogsMaintenance logsMain=new LogsMaintenance();
			logsMain.insertLogs("InterviewRest",Level.SEVERE+"",errors.toString());
			
			auditTrailMaint.insertAuditTrail(new AuditTrail("Rest", AuditTrail.MODULE_INTERVIEW, AuditTrail.FUNCTION_FAIL, "FAILED"));
			beans.setCode("500");
			beans.setMassage("Database Error");
			return Response.status(500).entity(new Gson().toJson(beans)).build();
		}
	}
	@GET
	@Path("/downloadFile")
	@Produces(MediaType.APPLICATION_OCTET_STREAM)
	public Response downloadFile(@Context HttpServletRequest request,@Context HttpServletResponse response,
						   @Context ServletContext context)
	{
		log.log(Level.INFO,"InterviewRest --> download File ");
		MsgBeans beans = new MsgBeans();
		String interviewCode = request.getParameter("interviewCode");
		AuditTrailMaintenance auditTrailMaint=new AuditTrailMaintenance();
		try{
			 InterviewMaterial  intMat = new  InterviewMaintenance().getInterviewMaterial(Integer.parseInt(interviewCode));
			   if(intMat!=null && (intMat.getMaterialName() !=null && intMat.getMaterialName().length()>0)){
					    response.setContentLength((int)intMat.getMaterial().length);
					  //  response.setHeader("Content-Transfer-Encoding", "binary");
					    response.setHeader("Content-Disposition","attachment; filename="+intMat.getMaterialName());
					    response.getOutputStream().write(intMat.getMaterial(), 0, intMat.getMaterial().length);
					    response.getOutputStream().flush();
		    auditTrailMaint.insertAuditTrail(new AuditTrail("Rest", AuditTrail.MODULE_INTERVIEW, AuditTrail.FUNCTION_REST, "SUCCESS"));
		    }else{	log.log(Level.INFO,"File Not found to download ");
		    
				beans.setCode("500");
				beans.setMassage("Download Error");
				auditTrailMaint.insertAuditTrail(new AuditTrail("Rest", AuditTrail.MODULE_INTERVIEW, AuditTrail.FUNCTION_REST, "FAILED"));
				return Response.status(500).entity(new Gson().toJson(beans)).build(); 
		    
		    }
			   return Response.status(200).build();
			
		}catch(Exception e){
			log.log(Level.INFO,"InterviewRest --> download File --> Exception..... ");
			log.log(Level.SEVERE, e.getMessage());
			e.printStackTrace();
			StringWriter errors = new StringWriter();
			e.printStackTrace(new PrintWriter(errors));
			LogsMaintenance logsMain=new LogsMaintenance();
			logsMain.insertLogs("InterviewRest",Level.SEVERE+"",errors.toString());
			
			beans.setCode("500");
			beans.setMassage("Download Error");
			auditTrailMaint.insertAuditTrail(new AuditTrail("Rest", AuditTrail.MODULE_INTERVIEW, AuditTrail.FUNCTION_REST, "FAILED"));
			return Response.status(500).entity(new Gson().toJson(beans)).build();
			
		}
		
	}
	
	
	
	
	@GET
	@Path("/getcandidatDetails")
	@Produces({MediaType.APPLICATION_JSON})
	public Response getcandidatDetails(@Context HttpServletRequest request,
						   		   @Context ServletContext context)
	{
		log.log(Level.INFO,"InterviewRest --> getcandidatDetails");
		MsgBeans beans = new MsgBeans();
		AuditTrailMaintenance auditTrailMaint=new AuditTrailMaintenance();
		String candidateCode = request.getParameter("candidateCode");
		candidateCode = candidateCode==null||candidateCode.equals("")?"0":candidateCode;
		String agentId = request.getParameter("agentId");
		agentId = agentId==null||agentId.equals("")?"0":agentId;
		String interviewCode = request.getParameter("interviewCode");
		interviewCode = interviewCode==null||interviewCode.equals("")?"0":interviewCode;
		
		GsonBuilder builder = new GsonBuilder();
		
		try
		{
			InterviewAttendanceMaintenance objMaintenance = new InterviewAttendanceMaintenance();
			InterviewCandidate interviewCandidate = objMaintenance.getCandidateDetailsRest(request, Integer.parseInt(interviewCode), Integer.parseInt(candidateCode),agentId);
			
			//String responseJsonString = "[{\"ccTestResult\":\""+interviewCandidate.getCcTestResult()+"\"}]";
		    auditTrailMaint.insertAuditTrail(new AuditTrail("Rest", AuditTrail.MODULE_INTERVIEW, AuditTrail.FUNCTION_REST, "SUCCESS"));
			return Response.status(200).entity(builder.setDateFormat("yyyy-MM-dd HH:mm:ss").create().toJson(interviewCandidate)).build();
		}
		catch(Exception e){
			log.log(Level.INFO,"InterviewRest --> deleteInterviewRegistration --> Exception..... ");
			log.log(Level.SEVERE, e.getMessage());
			StringWriter errors = new StringWriter();
			e.printStackTrace(new PrintWriter(errors));
			LogsMaintenance logsMain=new LogsMaintenance();
			logsMain.insertLogs("InterviewRest",Level.SEVERE+"",errors.toString());
			
			auditTrailMaint.insertAuditTrail(new AuditTrail("Rest", AuditTrail.MODULE_INTERVIEW, AuditTrail.FUNCTION_FAIL, "FAILED"));
			beans.setCode("500");
			beans.setMassage("Database Error");
			return Response.status(500).entity(new Gson().toJson(beans)).build();
		}
	}
}
