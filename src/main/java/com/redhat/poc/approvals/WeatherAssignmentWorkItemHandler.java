package com.redhat.poc.approvals;

import java.util.Map;

import org.kie.api.runtime.process.WorkItem;
import org.kie.api.runtime.process.WorkItemManager;

import com.redhat.poc.approvals.services.*;

public class WeatherAssignmentWorkItemHandler implements org.kie.api.runtime.process.WorkItemHandler {

	private static WeatherAssignmentService assignmentService = new WeatherAssignmentService();
	
	
	public void executeWorkItem(WorkItem workItem, WorkItemManager manager) {
	try {
		String taskAssignment = (String) workItem.getParameter("ActorId");
		String groupAssignment = (String) workItem.getParameter("GroupId");

		System.out.println("Weather task assignment [ " + taskAssignment + " ] for work item: " + workItem.getId());

		Map<String, Object> results = assignmentService.assignWorkItem(taskAssignment, groupAssignment);
		
		System.out.println("assigned actor id " + results.get("AssignedActorId"));
		System.out.println("assigned Group id " + results.get("AssignedGroupId"));
	
		manager.completeWorkItem(workItem.getId(), results);		
		} catch (Exception e) {
			System.out.println("Weather eassignment aborted for WorkItemID " + workItem.getId());
			manager.abortWorkItem(workItem.getId());
			e.printStackTrace();
		}
	}

	public void abortWorkItem(WorkItem workItem, WorkItemManager manager) {
		// TODO Auto-generated method stub
		
	}

}
