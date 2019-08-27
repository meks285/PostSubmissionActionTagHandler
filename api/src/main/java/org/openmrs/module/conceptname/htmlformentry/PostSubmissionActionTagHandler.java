package org.openmrs.module.conceptname.htmlformentry;

import java.sql.*;
import java.text.ParseException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.LinkedHashMap;
import java.util.Locale;
import org.junit.Assert;  //Usually gives AutoWired Error
import org.junit.Test;   //Came With junit Assert

import java.util.Map;
import java.util.Calendar;
import java.lang.Object;

import org.joda.time.DateTime;
import org.openmrs.Visit;
import org.openmrs.VisitType;
import org.openmrs.api.VisitService;
import org.openmrs.api.context.Context;
import org.openmrs.module.htmlformentry.CustomFormSubmissionAction;
import org.openmrs.module.htmlformentry.FormEntrySession;
// Database Connection String
import org.openmrs.module.conceptname.htmlformentry.DBConnection;
import org.openmrs.module.conceptname.htmlformentry.Utils;
import org.openmrs.module.conceptname.htmlformentry.DBManager;


/**
 * Usage example: <postSubmissionAction class="org.openmrs.module.xyz.DecideWhereToRedirect"/>
 */
public class PostSubmissionActionTagHandler implements CustomFormSubmissionAction {


	
	public static String patientLocation;
	public static String encounterId;
	public static String patientID;
	public static String patientIDdb;
	public static java.util.Date fromDate;
	public static java.util.Date toDate;
	public static String fromDateStr;
	public static String fromDateStr1;
	public static String fromDateStrYr;
	public static String fromDateStrMm;
	public static String fromDateStrDd;
	public static String vType;
	public static java.util.Date visitDate = null;
	public static String visitUuid ="";
	DBConnection openmrsConn;

    @Override
    public void applyAction(FormEntrySession session) {
 
    	patientLocation = String.valueOf(session.getSubmissionActions().getCurrentEncounter().getLocation());
    	encounterId = String.valueOf(session.getSubmissionActions().getCurrentEncounter().getEncounterId());
        patientID = String.valueOf(session.getPatient());
        fromDate = new DateTime(session.getSubmissionActions().getCurrentEncounter().getEncounterDatetime()).withTime(00, 00, 00, 000).toDate();
        toDate = new DateTime(session.getSubmissionActions().getCurrentEncounter().getEncounterDatetime()).withTime(23, 59, 59, 999).toDate();
        

        //fromDateStr = session.getSubmissionActions().getCurrentEncounter().getEncounterDatetime().toString();       
        //fromDateStr1 = session.getSubmissionActions().getCurrentEncounter().getEncounterDatetime().toString();       
        DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd",Locale.ENGLISH);   
        DateFormat sdfYr = new SimpleDateFormat("yyyy");   
        DateFormat sdfMm = new SimpleDateFormat("MM");   
        DateFormat sdDd = new SimpleDateFormat("dd");
        
        Calendar c = Calendar.getInstance();
        
        fromDateStr = sdf.format(fromDate);
        fromDateStrYr = sdfYr.format(fromDate);
        fromDateStrMm = sdfMm.format(fromDate);
        fromDateStrDd = sdDd.format(fromDate);

        try {c.setTime(sdf.parse(fromDateStr));} catch (ParseException e) {e.printStackTrace();}
        fromDateStr = sdf.format(c.getTime());
        
        try {c.setTime(sdfYr.parse(fromDateStrYr));} catch (ParseException e) {e.printStackTrace();}
        fromDateStrYr = sdfYr.format(c.getTime());
       
        try {c.setTime(sdfMm.parse(fromDateStrMm));} catch (ParseException e) {e.printStackTrace();}
        fromDateStrMm = sdfMm.format(c.getTime());       
        
        try {c.setTime(sdDd.parse(fromDateStrDd));} catch (ParseException e) {e.printStackTrace();}
        fromDateStrDd = sdDd.format(c.getTime());  
        
        patientIDdb = patientID.replaceAll("\\D+","");
        
        
        try{
        	Class.forName("com.mysql.jdbc.Driver");
        	
        	openmrsConn = Utils.getNmrsConnectionDetails();      	
        	Connection con=DriverManager.getConnection(openmrsConn.getUrl(),openmrsConn.getUsername(),openmrsConn.getPassword());       
        	Statement stmt=con.createStatement();

        	ResultSet rs=stmt.executeQuery("SELECT uuid, visit_id,patient_id,DATE_FORMAT(date_started, '%Y-%m-%d') as date_started FROM visit WHERE patient_id='"+patientIDdb+"' AND DATE_FORMAT(date_started, '%Y-%m-%d') = '"+fromDateStr+"'");
        	

        	if (!rs.next()) {
                VisitService vService=Context.getVisitService();
                VisitType vType=Context.getVisitService().getVisitType(1);
                Visit visit1=new Visit();
                visit1.setStartDatetime(fromDate);
                visit1.setLocation(session.getSubmissionActions().getCurrentEncounter().getLocation());
                visit1.setPatient(session.getPatient());
                visit1.setVisitType(vType);
         	   	session.getSubmissionActions().getCurrentEncounter().setVisit(vService.saveVisit(visit1));
           	    vService.endVisit(visit1, toDate);

        	} else {

        	    do {
            		visitDate = rs.getDate("date_started");
            		visitUuid = rs.getString("uuid");
                    VisitService vServiceGetData=Context.getVisitService();    
                    session.getSubmissionActions().getCurrentEncounter().setVisit(vServiceGetData.getVisitByUuid(visitUuid));
     
        	    } while (rs.next());
        	}
        	
/**        	while(rs.next()) {
        		visitDate = rs.getDate("date_started");
        		visitUuid = rs.getString("uuid");
                VisitService vServiceGetData=Context.getVisitService();    
                session.getSubmissionActions().getCurrentEncounter().setVisit(vServiceGetData.getVisitByUuid(visitUuid));
        	}     	
        	if(visitDate == null) {
                VisitService vService=Context.getVisitService();
                VisitType vType=Context.getVisitService().getVisitType(1);
                Visit visit1=new Visit();
                visit1.setStartDatetime(fromDate);
                visit1.setLocation(session.getSubmissionActions().getCurrentEncounter().getLocation());
                visit1.setPatient(session.getPatient());
                visit1.setVisitType(vType);
         	   	session.getSubmissionActions().getCurrentEncounter().setVisit(vService.saveVisit(visit1));
           	    vService.endVisit(visit1, toDate);
        		
        	} */
        	
        	
        	con.close();

        	}catch(Exception e){ System.out.println(e);}
        
        /**
        VisitService vService=Context.getVisitService();
        VisitType vType=Context.getVisitService().getVisitType(1);
        Visit visit1=new Visit();
        visit1.setStartDatetime(fromDate);
        visit1.setLocation(session.getSubmissionActions().getCurrentEncounter().getLocation());
        visit1.setPatient(session.getPatient());
        visit1.setVisitType(vType);
 	   	session.getSubmissionActions().getCurrentEncounter().setVisit(vService.saveVisit(visit1));
   	    vService.endVisit(visit1, toDate);
*/
    }
   	    
    }
