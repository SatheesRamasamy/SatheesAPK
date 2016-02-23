package example.sofarmanager;

import java.io.Serializable;
import java.util.ArrayList;

public class Out_satnd_parent implements Serializable {
	String subject, description,due_date,priority,status;
	ArrayList<Out_stand_child> childdata;

	public Out_satnd_parent() 
	{
	
	}

	public Out_satnd_parent(String sub,String desc,String dueDate,String prior,String stat,ArrayList<Out_stand_child> data) {
		this.subject = sub;
		this.description = desc;
		this.due_date = dueDate;
		this.priority = prior;
		this.status = stat;
		this.childdata = data;
	}

	public String getSubject() {
		return subject;
	}
	public void setSubject(String sub) {
		subject = sub;
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
	
	public ArrayList<Out_stand_child> getChilddata() {
		return childdata;
	}

	public void setChilddata(ArrayList<Out_stand_child> childdata) {
		this.childdata = childdata;
	}

}
