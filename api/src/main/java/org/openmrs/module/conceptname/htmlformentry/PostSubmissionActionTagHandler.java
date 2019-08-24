package org.openmrs.module.conceptname.htmlformentry;

import java.sql.Date;
import java.util.LinkedHashMap;

import java.util.Map;
import java.lang.Object;

import org.joda.time.DateTime;
import org.openmrs.Visit;
import org.openmrs.VisitType;
import org.openmrs.api.VisitService;
import org.openmrs.api.context.Context;
import org.openmrs.module.htmlformentry.CustomFormSubmissionAction;
import org.openmrs.module.htmlformentry.FormEntrySession;

/**
 * Usage example: <postSubmissionAction class="org.openmrs.module.xyz.DecideWhereToRedirect"/>
 */
public class PostSubmissionActionTagHandler implements CustomFormSubmissionAction {


	
	public static String patientLocation;
	public static String encounterId;
	public static String patientID;
	public static java.util.Date fromDate;
	public static java.util.Date toDate;
	//public static integer visitId;

    @Override
    public void applyAction(FormEntrySession session) {
        // check in to any scheduled appointments at the specified location on that date
    	patientLocation = String.valueOf(session.getSubmissionActions().getCurrentEncounter().getLocation());
    	encounterId = String.valueOf(session.getSubmissionActions().getCurrentEncounter().getEncounterId());
        patientID = String.valueOf(session.getPatient());
        fromDate = new DateTime(session.getSubmissionActions().getCurrentEncounter().getEncounterDatetime()).withTime(00, 00, 00, 000).toDate();
        toDate = new DateTime(session.getSubmissionActions().getCurrentEncounter().getEncounterDatetime()).withTime(23, 59, 59, 999).toDate();
 
        VisitService vService=Context.getVisitService();
        VisitType vType=Context.getVisitService().getVisitType(1);
        Visit visit1=new Visit();
        visit1.setStartDatetime(fromDate);
        visit1.setLocation(session.getSubmissionActions().getCurrentEncounter().getLocation());
        visit1.setPatient(session.getPatient());
        visit1.setVisitType(vType);
 	   	session.getSubmissionActions().getCurrentEncounter().setVisit(vService.saveVisit(visit1));
   	    vService.endVisit(visit1, toDate);
       

        //vService.saveVisit(visit1);
        //session.getSubmissionActions().getCurrentEncounter().getVisit().getVisitId();
        //session.getSubmissionActions().getCurrentEncounter().setVisit(visit);

    }

	
/**	public Map<String, String> getLinks() {
		LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();
		map.put("/module/conceptname/manage.form", "conceptname.manage");
		return map; 
	} */
    
}