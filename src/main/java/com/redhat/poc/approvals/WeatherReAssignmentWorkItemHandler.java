package com.redhat.poc.approvals;

import java.util.Map;

import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.manager.RuntimeEngine;
import org.kie.api.runtime.manager.RuntimeManager;
import org.kie.api.runtime.process.WorkItem;
import org.kie.api.runtime.process.WorkItemHandler;
import org.kie.api.runtime.process.WorkItemManager;
import org.kie.api.task.TaskService;
import org.kie.api.task.model.Task;
import org.kie.internal.runtime.manager.context.ProcessInstanceIdContext;

import com.redhat.poc.approvals.services.*;

public class WeatherReAssignmentWorkItemHandler implements WorkItemHandler {

	private static WeatherAssignmentService assignmentService = new WeatherAssignmentService();
	
	private KieSession ksession;
	
	
	public WeatherReAssignmentWorkItemHandler(KieSession ksession) {
		super();
		this.ksession = ksession;
	}

	public void executeWorkItem(WorkItem workItem, WorkItemManager manager) {
		
		String taskid = (String) workItem.getParameter("tskId");
		long taskId = Long.getLong(taskid).longValue();
		
		RuntimeManager runtimeManager = (RuntimeManager) ksession.getEnvironment().get("RuntimeManager");		
		RuntimeEngine engine= runtimeManager.getRuntimeEngine(ProcessInstanceIdContext.get());
		TaskService taskService = engine.getTaskService();
		Task task = taskService.getTaskById(taskId);
		String userid = task.getTaskData().getActualOwner().getId();
		
		//get target user id from your busi logic
		String targetUserid = "";
		taskService.delegate(taskId, userid, targetUserid);
		
		

		manager.completeWorkItem(workItem.getId(), null);		
		
	}

	public void abortWorkItem(WorkItem workItem, WorkItemManager manager) {
		// TODO Auto-generated method stub
		
	}

}
