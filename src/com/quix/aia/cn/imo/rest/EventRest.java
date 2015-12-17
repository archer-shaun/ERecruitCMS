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
*  20-August-2015    	Nibedita            Email sent after EOP registration 
*  02-Sept-2015          Nibedita          Download file service added
*  18-Nov-2015           Nibedita          Error stored in db
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
import com.quix.aia.cn.imo.data.event.Event;
import com.quix.aia.cn.imo.data.event.EventCandidate;
import com.quix.aia.cn.imo.data.event.EventMaterial;
import com.quix.aia.cn.imo.mapper.AamDataMaintenance;
import com.quix.aia.cn.imo.mapper.AddressBookMaintenance;
import com.quix.aia.cn.imo.mapper.AuditTrailMaintenance;
import com.quix.aia.cn.imo.mapper.CandidateNoteMaintenance;
import com.quix.aia.cn.imo.mapper.EopAttendanceMaintenance;
import com.quix.aia.cn.imo.mapper.EopMaintenance;
import com.quix.aia.cn.imo.mapper.LogsMaintenance;
import com.quix.aia.cn.imo.utilities.EmailNotification;
import com.quix.aia.cn.imo.utilities.LMSUtil;
/**
 * <p>Logic to get Event for Rest service.</p>
 * 
 * @author Jay
 * @version 1.0
 *
 */

@Path("/event")
public class EventRest {

	static Logger log = Logger.getLogger(EventRest.class.getName());
	@GET
	@Path("/getAllEop")
	@Produces({MediaType.APPLICATION_JSON})
	public Response getEvent(@Context HttpServletRequest request,
						   @Context ServletContext context)
	{
		
		log.log(Level.INFO,"EventRest --> getEvent ");
		MsgBeans beans = new MsgBeans();
		String agentId = request.getParameter("agentId");
		String coBranch = request.getParameter("co");
		String candidateCode = request.getParameter("candidateCode");
		candidateCode = null == candidateCode?"":candidateCode;
		ArrayList list = new ArrayList();
		AuditTrailMaintenance auditTrailMaint=new AuditTrailMaintenance();
		try{	

			AamData aamData = AamDataMaintenance.retrieveDataToModel(agentId, coBranch); 
			EopMaintenance objEopMaintenance=new EopMaintenance();
			list = objEopMaintenance.getAllEopRest(aamData, agentId, candidateCode,context);
			
			
			Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").setExclusionStrategies().create(); //.serializeNulls()
		    String json = gson.toJson(list);
		    auditTrailMaint.insertAuditTrail(new AuditTrail("Rest", AuditTrail.MODULE_EOP, AuditTrail.FUNCTION_REST, "SUCCESS"));
			return Response.status(200).entity(json).build();
			
		}catch(Exception e){
			log.log(Level.INFO,"EventRest --> getEvent --> Exception..... ");
			log.log(Level.SEVERE, e.getMessage());
			e.printStackTrace();
			StringWriter errors = new StringWriter();
			e.printStackTrace(new PrintWriter(errors));
			LogsMaintenance logsMain=new LogsMaintenance();
			logsMain.insertLogs("EventRest",Level.SEVERE+"",errors.toString());
			
			auditTrailMaint.insertAuditTrail(new AuditTrail("Rest", AuditTrail.MODULE_EOP, AuditTrail.FUNCTION_FAIL, "FAILED"));
			beans.setCode("500");
			beans.setMassage("Database Error");
			return Response.status(500).entity(new Gson().toJson(beans)).build();
		}
	}
	
	@GET
	@Path("/getAllDeletedEop")
	@Produces({MediaType.APPLICATION_JSON})
	public Response getDeletedEop(@Context HttpServletRequest request,
						   @Context ServletContext context)
	{
		
		log.log(Level.INFO,"EventRest --> getDeletedEop ");
		MsgBeans beans = new MsgBeans();
		AuditTrailMaintenance auditTrailMaint=new AuditTrailMaintenance();
		try{	
			ArrayList list = new ArrayList();
			list = new EopMaintenance().getAllDeletedEopRest();
			Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").setExclusionStrategies().create(); //.serializeNulls()
		    String json = gson.toJson(list);
		    auditTrailMaint.insertAuditTrail(new AuditTrail("Rest", AuditTrail.MODULE_EOP, AuditTrail.FUNCTION_REST, "SUCCESS"));
			return Response.status(200).entity(json).build();
			
		}catch(Exception e){
			log.log(Level.INFO,"EventRest --> getDeletedEop --> Exception..... ");
			log.log(Level.SEVERE, e.getMessage());
			e.printStackTrace();
			StringWriter errors = new StringWriter();
			e.printStackTrace(new PrintWriter(errors));
			LogsMaintenance logsMain=new LogsMaintenance();
			logsMain.insertLogs("EventRest",Level.SEVERE+"",errors.toString());
			
			auditTrailMaint.insertAuditTrail(new AuditTrail("Rest", AuditTrail.MODULE_EOP, AuditTrail.FUNCTION_FAIL, "FAILED"));
			beans.setCode("500");
			beans.setMassage("Database Error");
			return Response.status(500).entity(new Gson().toJson(beans)).build();
		}
	}
	
	@GET
	@Path("/getAllEopPast")
	@Produces({MediaType.APPLICATION_JSON})
	public Response getAllEopPast(@Context HttpServletRequest request,
						   @Context ServletContext context)
	{
		
		log.log(Level.INFO,"EventRest --> getAllEopPast ");
		MsgBeans beans = new MsgBeans();
		String agentId = request.getParameter("agentId");
		String coBranch = request.getParameter("co");
		String candidateCode = request.getParameter("candidateCode");
		candidateCode = null == candidateCode?"":candidateCode;
		ArrayList list = new ArrayList();
		AuditTrailMaintenance auditTrailMaint=new AuditTrailMaintenance();
		try{	

//			AamData aamData = AamDataMaintenance.retrieveDataToModel(agentId, coBranch); 
			EopMaintenance objEopMaintenance=new EopMaintenance();
//			list = objEopMaintenance.getAllEopRestPast(aamData, agentId, candidateCode, context);
			
			list = objEopMaintenance.getAllEopRestPast(agentId, candidateCode, context);
			
			Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").setExclusionStrategies().create(); //.serializeNulls()
		    String json = gson.toJson(list);
		    auditTrailMaint.insertAuditTrail(new AuditTrail("Rest", AuditTrail.MODULE_EOP, AuditTrail.FUNCTION_REST, "SUCCESS"));
			return Response.status(200).entity(json).build();
			
		}catch(Exception e){
			log.log(Level.INFO,"EventRest --> getEvent --> Exception..... ");
			log.log(Level.SEVERE, e.getMessage());
			e.printStackTrace();
			StringWriter errors = new StringWriter();
			e.printStackTrace(new PrintWriter(errors));
			LogsMaintenance logsMain=new LogsMaintenance();
			logsMain.insertLogs("EventRest",Level.SEVERE+"",errors.toString());
			
			auditTrailMaint.insertAuditTrail(new AuditTrail("Rest", AuditTrail.MODULE_EOP, AuditTrail.FUNCTION_FAIL, "FAILED"));
			beans.setCode("500");
			beans.setMassage("Database Error");
			return Response.status(500).entity(new Gson().toJson(beans)).build();
		}
	}
	
	@GET
	@Path("/getAllEopForBranch")
	@Produces({MediaType.APPLICATION_JSON})
	public Response getAllEopForBranch(@Context HttpServletRequest request,
						   @Context ServletContext context)
	{
		
		log.log(Level.INFO,"EventRest --> getAllEopForBranch ");
		MsgBeans beans = new MsgBeans();
		String branchCode = request.getParameter("branchCode");
		ArrayList list = new ArrayList();
		AuditTrailMaintenance auditTrailMaint=new AuditTrailMaintenance();
		try{	

			EopMaintenance objEopMaintenance=new EopMaintenance();
			list = objEopMaintenance.getAllEopBranchRest(branchCode);
			
			Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").setExclusionStrategies().create(); //.serializeNulls()
		    String json = gson.toJson(list);
		    auditTrailMaint.insertAuditTrail(new AuditTrail("Rest", AuditTrail.MODULE_EOP, AuditTrail.FUNCTION_REST, "SUCCESS"));
			return Response.status(200).entity(json).build();
			
		}catch(Exception e){
			log.log(Level.INFO,"EventRest --> getAllEopForBranch --> Exception..... ");
			log.log(Level.SEVERE, e.getMessage());
			e.printStackTrace();
			StringWriter errors = new StringWriter();
			e.printStackTrace(new PrintWriter(errors));
			LogsMaintenance logsMain=new LogsMaintenance();
			logsMain.insertLogs("EventRest",Level.SEVERE+"",errors.toString());
			
			auditTrailMaint.insertAuditTrail(new AuditTrail("Rest", AuditTrail.MODULE_EOP, AuditTrail.FUNCTION_FAIL, "FAILED"));
			beans.setCode("500");
			beans.setMassage("Database Error");
			return Response.status(500).entity(new Gson().toJson(beans)).build();
		}
	}
	
//	[{"eventCode":143,"candidateName":"BHP","servicingAgent":"S00012","dob":"2013-07-04","gender":"F","timeIn":"10:10:00"}]
	@POST
	@Path("/candidateAttend")
	@Consumes(MediaType.TEXT_PLAIN)
	@Produces({MediaType.APPLICATION_JSON})
	public Response candidateAttend(@Context HttpServletRequest request,
						   		   @Context ServletContext context,
						   		   String jsonString)
	{
		log.log(Level.INFO,"EventRest --> candidateAttend");
	    log.log(Level.INFO,"EventRest --> candidateAttend --> Data ...  ::::: "+jsonString);
		boolean status=false;
		MsgBeans beans = new MsgBeans();
		String agentId = request.getParameter("agentId");
		AuditTrailMaintenance auditTrailMaint=new AuditTrailMaintenance();
		EventCandidate candidate = null;
		try{
			
			EopAttendanceMaintenance objMaintenance = new EopAttendanceMaintenance();
			GsonBuilder builder = new GsonBuilder();
	        builder.registerTypeAdapter(Date.class, new JsonDeserializer<Date>() { 
	               @Override  
	               public Date deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            	   		Date date = LMSUtil.convertoDateHHMMSSAMPM1(json.getAsString());
            	   		if(null != date){
            	   			return date;
            	   		}else{
            	   			return  LMSUtil.convertDateToyyyy_mm_dd(json.getAsString());
            	   		}
	               }
	           });
	        
	        Gson googleJson  = builder.create();
	        Type listType = new TypeToken<List<EventCandidate>>(){}.getType();
	        List<EventCandidate> jsonObjList = googleJson.fromJson(jsonString, listType);
	       
	        for(Iterator<EventCandidate> itr = jsonObjList.iterator() ; itr.hasNext() ; ){
	        	candidate = (EventCandidate) itr.next();
			    objMaintenance.candidateAttendanceRest(candidate);
	        }
	        
	        String conditionFieldName[]={"addressCode"};
	        String conditionFieldValue[]={candidate.getEventCandidateCode()};
	        new AddressBookMaintenance().updateAddressBookStatus("4/9", conditionFieldName, conditionFieldValue);
	        new CandidateNoteMaintenance().insertSystemNotes(Integer.parseInt(candidate.getEventCandidateCode()), "EOP Attend", "Candidate Attended in EOP");
		    
		    status=true;
	        auditTrailMaint.insertAuditTrail(new AuditTrail("Rest", AuditTrail.MODULE_EOP, AuditTrail.FUNCTION_REST, "SUCCESS"));
		}
		catch(Exception e){
			log.log(Level.INFO,"EventRest --> candidateAttend --> Exception..... ");
			log.log(Level.SEVERE, e.getMessage());
			e.printStackTrace();
			StringWriter errors = new StringWriter();
			e.printStackTrace(new PrintWriter(errors));
			LogsMaintenance logsMain=new LogsMaintenance();
			logsMain.insertLogs("EventRest",Level.SEVERE+"",errors.toString());
			
			auditTrailMaint.insertAuditTrail(new AuditTrail("Rest", AuditTrail.MODULE_EOP, AuditTrail.FUNCTION_FAIL, "FAILED"));
			beans.setCode("500");
			beans.setMassage("Database Error");
		}
		
		return Response.status(200).entity("[{\"status\":"+status+"}]").build();
	}
	
	
	@GET
	@Path("/getEOPRegisteredCandidate")
	@Produces({MediaType.APPLICATION_JSON})
	public Response getEOPRegistration(@Context HttpServletRequest request,
						   		 	   @Context ServletContext context)
	{
		log.log(Level.INFO,"EventRest --> getEOPRegisteredCandidate");
		MsgBeans beans = new MsgBeans();
		String agentId = request.getParameter("agentId");
		AuditTrailMaintenance auditTrailMaint=new AuditTrailMaintenance();
		String eventCode = request.getParameter("eventCode");
		eventCode = eventCode==null||eventCode.equals("")?"0":eventCode;
		try
		{
			request.setAttribute("isRest", true);
			ArrayList list = new ArrayList();
			EopAttendanceMaintenance objMaintenance = new EopAttendanceMaintenance();
			list = objMaintenance.getAttendanceList(request,Integer.parseInt(eventCode));
			for(Iterator itr=list.iterator();itr.hasNext();){
				EventCandidate eventCandidate = (EventCandidate) itr.next();
				if(null == eventCandidate.getTimeIn()){
					eventCandidate.setAttendanceStatus("N");
				}else{
					eventCandidate.setAttendanceStatus("Y");
				}
			}
			Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").setExclusionStrategies().create(); //.serializeNulls()
		    String json = gson.toJson(list);
		    auditTrailMaint.insertAuditTrail(new AuditTrail("Rest", AuditTrail.MODULE_EOP, AuditTrail.FUNCTION_REST, "SUCCESS"));
			return Response.status(200).entity(json).build();
		}
		catch(Exception e){
			log.log(Level.INFO,"EventRest --> getEOPRegisteredCandidate --> Exception..... ");
			log.log(Level.SEVERE, e.getMessage());
			e.printStackTrace();
			StringWriter errors = new StringWriter();
			e.printStackTrace(new PrintWriter(errors));
			LogsMaintenance logsMain=new LogsMaintenance();
			logsMain.insertLogs("EventRest",Level.SEVERE+"",errors.toString());
			
			auditTrailMaint.insertAuditTrail(new AuditTrail("Rest", AuditTrail.MODULE_EOP, AuditTrail.FUNCTION_FAIL, "FAILED"));
			beans.setCode("500");
			beans.setMassage("Database Error");
			return Response.status(500).entity(new Gson().toJson(beans)).build();
		}
	}
	
	
//	[{"candidateCode":17,"eventCode":143,"candidateName":"BHP","servicingAgent":"S00012","sourceOfReferal":"GGG","age":19,"dob":"2013-07-04 00:00:00","dobStr":"","gender":"F","contactNumber":"1234567890","statusStr":"true","token":""}]
	@POST
	@Path("/candidateRegister")
	@Consumes(MediaType.TEXT_PLAIN)
	@Produces({MediaType.APPLICATION_JSON})
	public Response candidateRegister(@Context HttpServletRequest request,
						   		   @Context ServletContext context,
						   		   String jsonString)
	{
		log.log(Level.INFO,"EventRest --> candidateRegister");
	    log.log(Level.INFO,"EventRest --> candidateRegister --> Data ...  ::::: "+jsonString);
		boolean status=false;
		boolean isDuplicate = false;
		Integer registeredCount = 0;
		MsgBeans beans = new MsgBeans();
		String agentId = request.getParameter("agentId");
		String coBranch = request.getParameter("co");
		 AuditTrailMaintenance auditTrailMaint=new AuditTrailMaintenance();
		try{
			AamData aamData = AamDataMaintenance.retrieveDataToModel(agentId, coBranch); 
			EopAttendanceMaintenance objMaintenance = new EopAttendanceMaintenance();
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
	        Type listType = new TypeToken<List<EventCandidate>>(){}.getType();
	        List<EventCandidate> jsonObjList = googleJson.fromJson(jsonString, listType);
	        EventCandidate candidate = jsonObjList.get(0);  
	        candidate.setAgentName(aamData.getAgentName());
	        candidate.setBuName(aamData.getBu());
	        candidate.setDistName(aamData.getDistrict());
	        candidate.setCityName(aamData.getCity());
	        candidate.setSscName(aamData.getSsc());
	        candidate.setBranchName(aamData.getBranch());
	        candidate.setOfficeName(aamData.getOfficeName());
	        candidate.setAgencyLeaderCode(aamData.getLeaderCode());
	        candidate.setEventCandidateCode(""+candidate.getCandidateCode());
	        
	        if(candidate.getStatusStr() != null && candidate.getStatusStr().equalsIgnoreCase("true"))
	        	candidate.setStatus(true);
	        else
	        	candidate.setStatus(false);
	       
	        	
//	        candidate.setAgencyLeaderName(aamData.getTeamName());

	        List<EventCandidate> list1 = objMaintenance.getAttendanceList(request,candidate.getEventCode());
	        registeredCount = list1.size();
	        
	        if(!objMaintenance.checkDuplicateCandiadteReg(""+candidate.getEventCode(), candidate.getServicingAgent(), candidate.getEventCandidateCode())){
			    status=true; 
		        objMaintenance.createNewCandidateRest(candidate,request, aamData);
		        registeredCount++;
		        AddressBookMaintenance addrBookMain = new AddressBookMaintenance();
		        String emailAddrs = addrBookMain.getEmailAddress(Integer.parseInt(candidate.getEventCandidateCode()));		        

		        if(emailAddrs!=null && emailAddrs.length()>0){
		        	EmailNotification.sendEopRegEmailNotification(candidate,emailAddrs, aamData);
		        }
		        
		        String conditionFieldName[]={"addressCode"};
		        String conditionFieldValue[]={candidate.getEventCandidateCode()};
		        new AddressBookMaintenance().updateAddressBookStatus("2/9", conditionFieldName, conditionFieldValue);
		        new CandidateNoteMaintenance().insertSystemNotes(Integer.parseInt(candidate.getEventCandidateCode()), "EOP Registration", "Candidate Registered in EOP");
		        
		        auditTrailMaint.insertAuditTrail(new AuditTrail("Rest", AuditTrail.MODULE_EOP, AuditTrail.FUNCTION_REST, "SUCCESS"));
	        }else{
	            auditTrailMaint.insertAuditTrail(new AuditTrail("Rest", AuditTrail.MODULE_EOP, AuditTrail.FUNCTION_REST, "FAIL"));
			    isDuplicate =true; 
	        }
	        
		}
		catch(Exception e){
			log.log(Level.INFO,"EventRest --> candidateRegister --> Exception..... ");
			log.log(Level.SEVERE, e.getMessage());
			e.printStackTrace();
			StringWriter errors = new StringWriter();
			e.printStackTrace(new PrintWriter(errors));
			LogsMaintenance logsMain=new LogsMaintenance();
			logsMain.insertLogs("EventRest",Level.SEVERE+"",errors.toString());
			
			auditTrailMaint.insertAuditTrail(new AuditTrail("Rest", AuditTrail.MODULE_EOP, AuditTrail.FUNCTION_FAIL, "FAILED"));
			beans.setCode("500");
			beans.setMassage("Database Error");
		}
		
		return Response.status(200).entity("[{\"status\":"+status+",\"isDuplicate\":"+isDuplicate+",\"registeredCount\":"+registeredCount+"}]").build();
	}
	
	@GET
	@Path("/getEOPRegistrationStatus")
	@Produces({MediaType.APPLICATION_JSON})
	public Response getEOPRegistrationStatus(@Context HttpServletRequest request,
						   		   @Context ServletContext context,
						   		   String jsonString)
	{
		log.log(Level.INFO,"EventRest --> getEOPRegistrationStatus");
		MsgBeans beans = new MsgBeans();
		String agentId = request.getParameter("agentId");
		String coBranch = request.getParameter("co");
		AuditTrailMaintenance auditTrailMaint=new AuditTrailMaintenance();
		String eventCode = request.getParameter("eventCode");
		eventCode = eventCode==null||eventCode.equals("")?"0":eventCode;
		String candidateCode = request.getParameter("candidateCode");
		candidateCode = candidateCode==null||candidateCode.equals("")?"0":candidateCode;
		try
		{
			AamData aamData = AamDataMaintenance.retrieveDataToModel(agentId, coBranch); 
			EopAttendanceMaintenance objMaintenance = new EopAttendanceMaintenance();
			boolean isExist = objMaintenance.checkDuplicateCandiadteReg(eventCode, agentId, candidateCode);
			String responseJsonString="[{\"agentId\":\""+agentId+"\",\"eventCode\":\""+eventCode+"\",\"isExist\":"+isExist+"}]";
		    auditTrailMaint.insertAuditTrail(new AuditTrail("Rest", AuditTrail.MODULE_EOP, AuditTrail.FUNCTION_REST, "SUCCESS"));
			return Response.status(200).entity(responseJsonString).build();
		}
		catch(Exception e){
			log.log(Level.INFO,"EventRest --> getEOPRegistrationStatus --> Exception..... ");
			log.log(Level.SEVERE, e.getMessage());
			e.printStackTrace();
			StringWriter errors = new StringWriter();
			e.printStackTrace(new PrintWriter(errors));
			LogsMaintenance logsMain=new LogsMaintenance();
			logsMain.insertLogs("EventRest",Level.SEVERE+"",errors.toString());
			
			auditTrailMaint.insertAuditTrail(new AuditTrail("Rest", AuditTrail.MODULE_EOP, AuditTrail.FUNCTION_FAIL, "FAILED"));
			beans.setCode("500");
			beans.setMassage("Database Error");
			return Response.status(500).entity(new Gson().toJson(beans)).build();
		}
	}
	
	@GET
	@Path("/deleteEOPRegistration")
	@Produces({MediaType.APPLICATION_JSON})
	public Response deleteEOPRegistration(@Context HttpServletRequest request,
						   		   @Context ServletContext context,
						   		   String jsonString)
	{
		log.log(Level.INFO,"EventRest --> deleteEOPRegistration");
		String responseString = "[{\"status\":";
		MsgBeans beans = new MsgBeans();
		AuditTrailMaintenance auditTrailMaint=new AuditTrailMaintenance();
		String candidateCode = request.getParameter("candidateCode");
		candidateCode = candidateCode==null||candidateCode.equals("")?"0":candidateCode;
		String eventCode = request.getParameter("eventCode");
		eventCode = eventCode==null||eventCode.equals("")?"0":eventCode;
		request.setAttribute("isRest", true);
		try
		{
			EopAttendanceMaintenance objMaintenance = new EopAttendanceMaintenance();
			objMaintenance.deleteCandidateReg(Integer.parseInt(candidateCode), Integer.parseInt(eventCode), request);
		    auditTrailMaint.insertAuditTrail(new AuditTrail("Rest", AuditTrail.MODULE_EOP, AuditTrail.FUNCTION_REST, "SUCCESS"));
		    responseString +=true; 
		}
		catch(Exception e){
			log.log(Level.INFO,"EventRest --> deleteEOPRegistration --> Exception..... ");
			log.log(Level.SEVERE, e.getMessage());
			e.printStackTrace();
			StringWriter errors = new StringWriter();
			e.printStackTrace(new PrintWriter(errors));
			LogsMaintenance logsMain=new LogsMaintenance();
			logsMain.insertLogs("EventRest",Level.SEVERE+"",errors.toString());
			
			auditTrailMaint.insertAuditTrail(new AuditTrail("Rest", AuditTrail.MODULE_EOP, AuditTrail.FUNCTION_FAIL, "FAILED"));
			beans.setCode("500");
			beans.setMassage("Database Error");
		    responseString +=false; 
		}
		
		responseString+="}]";
		return Response.status(200).entity(responseString).build();
	}
	
	@GET
	@Path("/getEOPRegisteredCandidateCount")
	@Produces({MediaType.APPLICATION_JSON})
	public Response getEOPRegisteredCandidateCount(@Context HttpServletRequest request,
						   		 	   @Context ServletContext context)
	{
		log.log(Level.INFO,"EventRest --> getEOPRegisteredCandidateCount");
		String agentId = request.getParameter("agentId");
		AuditTrailMaintenance auditTrailMaint=new AuditTrailMaintenance();
		String eventCode = request.getParameter("eventCode");
		eventCode = eventCode==null||eventCode.equals("")?"0":eventCode;
		Integer registeredCount = 0;
		try
		{
			request.setAttribute("isRest", true);
			ArrayList list = new ArrayList();
			EopAttendanceMaintenance objMaintenance = new EopAttendanceMaintenance();
			
			list = objMaintenance.getAttendanceList(request,Integer.parseInt(eventCode));
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
			logsMain.insertLogs("EventRest",Level.SEVERE+"",errors.toString());
			
			auditTrailMaint.insertAuditTrail(new AuditTrail("Rest", AuditTrail.MODULE_EOP, AuditTrail.FUNCTION_FAIL, "FAILED"));
		}
		
		return Response.status(200).entity("[{\"registeredCount\":"+registeredCount+"}]").build();
	}
	
//	@GET
//	@Path("/getEOPWithRegistered")
//	@Produces({MediaType.APPLICATION_JSON})
//	public Response getEOPWithRegistered(@Context HttpServletRequest request,
//						   		   @Context ServletContext context)
//	{
//		log.log(Level.INFO,"EventRest --> getEOPRegistrationStatus");
//		MsgBeans beans = new MsgBeans();
//		String agentId = request.getParameter("agentId");
//		AuditTrailMaintenance auditTrailMaint=new AuditTrailMaintenance();
//		String candidateCode = request.getParameter("candidateCode");
//		candidateCode = candidateCode==null||candidateCode.equals("")?"0":candidateCode;
//		try
//		{
//			AamData aamData = AamDataMaintenance.retrieveDataToModel(agentId); 
//			EopMaintenance objEopMaintenance=new EopMaintenance();
//			String jsonString = objEopMaintenance.getConditionalEopRest(aamData, candidateCode);
//			
//		    auditTrailMaint.insertAuditTrail(new AuditTrail("Rest", AuditTrail.MODULE_EOP_REG, AuditTrail.FUNCTION_REST, "SUCCESS"));
//			return Response.status(200).entity(jsonString).build();
//		}
//		catch(Exception e){
//			log.log(Level.INFO,"EventRest --> getEOPRegistrationStatus --> Exception..... ");
//			auditTrailMaint.insertAuditTrail(new AuditTrail("Rest", AuditTrail.MODULE_EOP_REG, AuditTrail.FUNCTION_FAIL, "FAILED"));
//			beans.setCode("500");
//			beans.setMassage("Database Error");
//			return Response.status(500).entity(new Gson().toJson(beans)).build();
//		}
//	}
	
	@GET
	@Path("/getCandidateRegisteredEOP")
	@Produces({MediaType.APPLICATION_JSON})
	public Response getCandidateRegisteredEOP(@Context HttpServletRequest request,
						   		   @Context ServletContext context)
	{
		log.log(Level.INFO,"EventRest --> getCandidateRegisteredEOP");
		MsgBeans beans = new MsgBeans();
		List<Event> eventList = new ArrayList();
		Event event = null;
		EventCandidate candidate = new EventCandidate();
		AuditTrailMaintenance auditTrailMaint=new AuditTrailMaintenance();
		String candidateCode = request.getParameter("candidateCode");
		candidateCode = candidateCode==null||candidateCode.equals("")?"0":candidateCode;
		String agentId = request.getParameter("agentId");
		agentId = agentId==null||agentId.equals("")?"0":agentId;
		try
		{
			EopMaintenance objEopMaintenance=new EopMaintenance();
			EopAttendanceMaintenance objEopAttendanceMaintenance=new EopAttendanceMaintenance();
			List<EventCandidate> list= objEopAttendanceMaintenance.getCandidateRegisteredEOP(candidateCode, agentId);
			List<Integer> duplicateList = new ArrayList<Integer>();

			for(Iterator iterator = list.iterator(); iterator.hasNext();){
				candidate = (EventCandidate) iterator.next();
				if(!duplicateList.contains(candidate.getEventCode())){
					duplicateList.add(candidate.getEventCode());
					event = objEopMaintenance.getEvent(candidate.getEventCode());
					if(null == candidate.getTimeIn()){
						event.setAttendedStatus("N");
					}else{
						event.setAttendedStatus("Y");
					}
					eventList.add(event);
				}
			}

			Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").setExclusionStrategies().create(); //.serializeNulls()
		    String json = gson.toJson(eventList);
		    
		    auditTrailMaint.insertAuditTrail(new AuditTrail("Rest", AuditTrail.MODULE_EOP_REG, AuditTrail.FUNCTION_REST, "SUCCESS"));
			return Response.status(200).entity(json).build();
		}
		catch(Exception e){
			log.log(Level.INFO,"EventRest --> getCandidateRegisteredEOP --> Exception..... ");
			log.log(Level.SEVERE, e.getMessage());
			e.printStackTrace();
			StringWriter errors = new StringWriter();
			e.printStackTrace(new PrintWriter(errors));
			LogsMaintenance logsMain=new LogsMaintenance();
			logsMain.insertLogs("EventRest",Level.SEVERE+"",errors.toString());
			
			auditTrailMaint.insertAuditTrail(new AuditTrail("Rest", AuditTrail.MODULE_EOP_REG, AuditTrail.FUNCTION_FAIL, "FAILED"));
			beans.setCode("500");
			beans.setMassage("Database Error");
			return Response.status(500).entity(new Gson().toJson(beans)).build();
		}
	}
	@GET
	@Path("/downloadFile")
	@Produces(MediaType.APPLICATION_OCTET_STREAM)
	public Response downloadFile(@Context HttpServletRequest request,@Context HttpServletResponse response,@Context HttpServletResponse response2,
						   @Context ServletContext context)
	{
		log.log(Level.INFO,"EventRest --> download File ");
		MsgBeans beans = new MsgBeans();
		String eventCode = request.getParameter("eventCode");
		String materialName=request.getParameter("material");
		
		
		AuditTrailMaintenance auditTrailMaint=new AuditTrailMaintenance();
		try{
			if(eventCode!=null){
			
				EventMaterial  eventMat = new EopMaintenance().getEventMaterial(Integer.parseInt(eventCode),materialName);
			   if(eventMat!=null && (eventMat.getMaterialName() !=null && eventMat.getMaterialName().length()>0)){
				    response.setContentLength((int)eventMat.getMaterial().length);
				    //response.setHeader("Content-Transfer-Encoding", "binary");
				    response.setHeader("Content-Disposition","attachment; filename="+eventMat.getMaterialName());
				    response.getOutputStream().write(eventMat.getMaterial(), 0, eventMat.getMaterial().length);
				    response.getOutputStream().flush();
				    
				    
				    auditTrailMaint.insertAuditTrail(new AuditTrail("Rest", AuditTrail.MODULE_EOP, AuditTrail.FUNCTION_REST, "SUCCESS"));
			    }else{	log.log(Level.INFO,"File Not found to download ");
			    
					beans.setCode("500");
					beans.setMassage("Download Error");
					auditTrailMaint.insertAuditTrail(new AuditTrail("Rest", AuditTrail.MODULE_EOP, AuditTrail.FUNCTION_REST, "FAILED"));
					return Response.status(500).entity(new Gson().toJson(beans)).build(); 
			    
			    }
			}else{	log.log(Level.INFO,"Event or Material file Not Passed ");
		    
				beans.setCode("500");
				beans.setMassage("Download Error");
				auditTrailMaint.insertAuditTrail(new AuditTrail("Rest", AuditTrail.MODULE_EOP, AuditTrail.FUNCTION_REST, "FAILED"));
				return Response.status(500).entity(new Gson().toJson(beans)).build(); 
		    
		    }
		   return Response.status(200).build();
			
		}catch(Exception e){
			log.log(Level.INFO,"EventRest --> download File --> Exception..... ");
			log.log(Level.SEVERE, e.getMessage());
			e.printStackTrace();
			StringWriter errors = new StringWriter();
			e.printStackTrace(new PrintWriter(errors));
			LogsMaintenance logsMain=new LogsMaintenance();
			logsMain.insertLogs("EventRest",Level.SEVERE+"",errors.toString());
			
			beans.setCode("500");
			beans.setMassage("Download Error");
			auditTrailMaint.insertAuditTrail(new AuditTrail("Rest", AuditTrail.MODULE_EOP, AuditTrail.FUNCTION_REST, "FAILED"));
			return Response.status(500).entity(new Gson().toJson(beans)).build();
			
		}
		
	}
}
