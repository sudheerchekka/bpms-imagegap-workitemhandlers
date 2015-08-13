package com.redhat.poc.approvals;

import java.util.Map;

import org.kie.api.runtime.process.WorkItem;
import org.kie.api.runtime.process.WorkItemManager;

import com.redhat.poc.approvals.services.*;

public class WeatherAssignmentWorkItemHandler implements org.kie.api.runtime.process.WorkItemHandler {

	private static WeatherAssignmentService assignmentService = new WeatherAssignmentService();
	
	
	public void executeWorkItem(WorkItem workItem, WorkItemManager manager) {
		
		String taskAssignment = (String) workItem.getParameter("ActorId");
		String groupAssignment = (String) workItem.getParameter("GroupId");

		System.out.println("Weather task assignment [ " + taskAssignment + " ] for work item: " + workItem.getId());

		Map<String, Object> results = assignmentService.assignWorkItem(taskAssignment, groupAssignment);
		
		manager.completeWorkItem(workItem.getId(), results);		
		
	}

	public void abortWorkItem(WorkItem workItem, WorkItemManager manager) {
		// TODO Auto-generated method stub
		
	}

}
