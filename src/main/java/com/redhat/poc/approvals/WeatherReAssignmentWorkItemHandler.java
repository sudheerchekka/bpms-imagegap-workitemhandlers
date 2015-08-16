package com.redhat.poc.approvals;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.drools.core.process.instance.impl.WorkItemImpl;
import org.jbpm.workflow.instance.node.HumanTaskNodeInstance;
import org.jbpm.workflow.instance.node.WorkItemNodeInstance;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.manager.RuntimeEngine;
import org.kie.api.runtime.manager.RuntimeManager;
import org.kie.api.runtime.process.NodeInstance;
import org.kie.api.runtime.process.WorkItem;
import org.kie.api.runtime.process.WorkItemHandler;
import org.kie.api.runtime.process.WorkItemManager;
import org.kie.api.runtime.process.WorkflowProcessInstance;
import org.kie.api.task.TaskService;
import org.kie.api.task.model.OrganizationalEntity;
import org.kie.api.task.model.PeopleAssignments;
import org.kie.api.task.model.Status;
import org.kie.api.task.model.Task;
import org.kie.api.task.model.TaskData;
import org.kie.api.task.model.User;
import org.kie.internal.runtime.manager.RuntimeManagerRegistry;
import org.kie.internal.runtime.manager.context.ProcessInstanceIdContext;

import com.redhat.poc.approvals.services.*;

public class WeatherReAssignmentWorkItemHandler implements WorkItemHandler {

	private static WeatherAssignmentService assignmentService = new WeatherAssignmentService();
	
	private KieSession ksession;
	
	
    public WeatherReAssignmentWorkItemHandler() {
		
		
	}
	public WeatherReAssignmentWorkItemHandler(KieSession ksession) {
		
		this.ksession = ksession;
	}

	public void executeWorkItem(WorkItem workItem, WorkItemManager manager) {
		
		String taskid = (String) workItem.getParameter("taskId");
		//long taskId = ;//Long.getLong(taskid).longValue();
		long taskId=0;
		long workid=0;
		 String deploymentId  = ((WorkItemImpl)workItem).getDeploymentId()  ;
		
		deploymentId = deploymentId == null ? "" : deploymentId;
       
        final long processInstanceId = workItem.getProcessInstanceId();
	try {
		
		
		RuntimeManager runtimeManager = RuntimeManagerRegistry.get().getManager(deploymentId);
		RuntimeEngine engine = runtimeManager.getRuntimeEngine(ProcessInstanceIdContext.get(processInstanceId));
		WorkflowProcessInstance instnace = (WorkflowProcessInstance)ksession.getProcessInstance(processInstanceId);
		Collection<NodeInstance> nodeInstances = instnace.getNodeInstances();
		int status=0;
		for (NodeInstance node : nodeInstances){
			WorkItemNodeInstance workItemNodeInstance = (WorkItemNodeInstance) node;
			if(node instanceof HumanTaskNodeInstance ){
				
				
				HumanTaskNodeInstance htNodeInstan =(HumanTaskNodeInstance) node; 
				String htname = htNodeInstan.getHumanTaskNode().getName();
				System.out.println("htname  taskId111" + htname);
				
				if(htname.equals("Approve Task")){
					//WorkItemImpl workItm = (WorkItemImpl)workItemNodeInstance.getWorkItem();
					//taskId = htNodeInstan.getHumanTaskNode().get
					WorkItem workItm = workItemNodeInstance.getWorkItem();
					workid = workItm.getId();
					 status = workItm.getState();
					
					//System.out.println("actual taskId111" + taskId);
					System.out.println("actual status" + status);
				}
			}
         //  WorkItem workItm = workItemNodeInstance.getWorkItem();
		
		}
		
		TaskService taskService = engine.getTaskService();
		System.out.println("actual taskId" + taskId);
		Task task = taskService.getTaskByWorkItemId(workid);
		
		PeopleAssignments as = task.getPeopleAssignments();
		List<OrganizationalEntity> ord = as.getPotentialOwners();
		String userid = null;
		for (OrganizationalEntity org : ord){
			userid = org.getId();
			System.out.println("org potential owner" + org.getId());
		}
		if(task == null){
			System.out.println("tsk is null skId" + taskId);
		}else{
			taskId = task.getId();
			System.out.println("tsk is n skId" + taskId); 
		}
		//String userid = task.getTaskData().getActualOwner().getId();
		
		userid = ord.get(0).getId();
		TaskData tdata = task.getTaskData();
		if(tdata == null){
			
			System.out.println("data is null owner" + userid);
			
		}else {
			User user = tdata.getActualOwner();
			String actualowner = null;
			if(user == null){
				
				System.out.println("no actual owner" );
			}else{
				actualowner = user.getId();
			}
			if(actualowner!=null){
				userid = actualowner;
			}
			Status stat = tdata.getStatus();
			if (stat == Status.InProgress){
				status = 1;
			}

			System.out.println("data  not null owner" + userid);
			System.out.println("actual owner" + userid);
			
		}
		System.out.println("actual owner from task data " + userid);
		
		//get target user id from your business logic
		String targetUserid = "";
		
		Map<String, Object> results = assignmentService.assignWorkItem(userid, "analyst");

		targetUserid = (String) results.get("AssignedActorId");
		
		System.out.println("actual targetUserid " + targetUserid);
		if ( status == 0){
			//userid = "Administrator";
		}
		if (targetUserid != null) {
			
			System.out.println("task will be assigned to " + userid);
			
			taskService.delegate(taskId, userid, targetUserid);
			//taskService.forward(taskId, userid, targetUserid);
			
		} else {
			System.out.println("task releasd to " + userid);
			taskService.release(taskId, userid);
		}
		
		//manager.completeWorkItem(workItem.getId(), results);	
		manager.completeWorkItem(workItem.getId(), null);	
	} catch (Exception e) {
		System.out.println("Weather reassignment aborted for WorkItemID " + workItem.getId());
		manager.abortWorkItem(workItem.getId());
		e.printStackTrace();	
		}
	}

	public void abortWorkItem(WorkItem workItem, WorkItemManager manager) {
		// TODO Auto-generated method stub
		
	}

}
