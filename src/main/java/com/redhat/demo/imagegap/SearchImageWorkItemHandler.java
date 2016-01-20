package com.redhat.demo.imagegap;

import org.kie.api.runtime.process.WorkItem;
import org.kie.api.runtime.process.WorkItemHandler;
import org.kie.api.runtime.process.WorkItemManager;

public class SearchImageWorkItemHandler implements WorkItemHandler {

	public void executeWorkItem(WorkItem workItem, WorkItemManager workItemManager) {
		try {
			
			String keyword = (String) workItem.getParameter("keyword");
			System.out.println("***** Searching Image in Database...");

			// TODO: JDBC Query

			workItemManager.completeWorkItem(workItem.getId(), null);

		} catch (Exception e) {
			System.out.println("***** Search Image in Database aborted for WorkItemID " + workItem.getId());
			workItemManager.abortWorkItem(workItem.getId());
			e.printStackTrace();
		}
	}

	public void abortWorkItem(WorkItem workItem, WorkItemManager manager) {
		System.out.println("***** Search Image in Database aborted for WorkItemID " + workItem.getId());

	}

}
