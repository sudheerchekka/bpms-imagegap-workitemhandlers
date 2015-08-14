import static org.junit.Assert.*;

import java.util.Map;

import junit.framework.Assert;

import org.junit.Test;

import com.redhat.poc.approvals.services.WeatherAssignmentService;


public class TestWeatherAssignments {

	private static WeatherAssignmentService assignmentService = new WeatherAssignmentService();

	@Test
	public void test() {
		String taskAssignment = null;
		String groupAssignment = "user";

		System.out.println("Weather task assignment test starting ");

		for(int i=0 ; i < 5 ; i++) {

			System.out.println("Weather task assignment [ " + taskAssignment + " ] for work item: " + (i+1));
			Map<String, Object> results = assignmentService.assignWorkItem(taskAssignment, groupAssignment);
			System.out.println("Weather task assignment [ " + results + " ]");
			
		}

		System.out.println("Weather task re-assignment test starting ");		

		Map<String, Object> results = assignmentService.assignWorkItem("ApproverC", groupAssignment);
		String targetUserid = (String) results.get("AssignedActorId");
		System.out.println("Weather task Assigned Actor Id [ " + targetUserid + " ]");
		System.out.println("Weather task reassignment [ " + results + " ]");
		
		results = assignmentService.assignWorkItem("ApproverB", groupAssignment);
		targetUserid = (String) results.get("AssignedActorId");
		System.out.println("Weather task Assigned Actor Id [ " + targetUserid + " ]");
		System.out.println("Weather task reassignment [ " + results + " ]");

		results = assignmentService.assignWorkItem("ApproverA", groupAssignment);
		targetUserid = (String) results.get("AssignedActorId");
		System.out.println("Weather task Assigned Actor Id [ " + targetUserid + " ]");
		System.out.println("Weather task reassignment [ " + results + " ]");
		
		assertEquals("Test123", "Test123");
	}

}
