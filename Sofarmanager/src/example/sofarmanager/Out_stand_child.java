package example.sofarmanager;

import java.io.Serializable;

public class Out_stand_child implements Serializable {
	String description,due_date,priority,status;

	public Out_stand_child()
	{

	}
	
		
	public String getDescription() {
		return description;
	}

	public void setDescription(String desc) {
		description = desc;
	}

	public String getDuedate() {
		return due_date;
	}

	public void setDuedate(String duedate) {
		due_date = duedate;
	}
	
	public String getPriority() {
		return priority;
	}

	public void setPriority(String prio) {
		priority = prio;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String stat) {
		status = stat;
	}
}
