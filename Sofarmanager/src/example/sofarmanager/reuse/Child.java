package example.sofarmanager.reuse;

import java.io.Serializable;

public class Child implements Serializable {
	String Description, Duedate, Status;

	public Child() {
		// TODO Auto-generated constructor stub
	}

	public String getDescription() {
		return Description;
	}

	public void setDescription(String description) {
		Description = description;
	}

	public String getDuedate() {
		return Duedate;
	}

	public void setDuedate(String duedate) {
		Duedate = duedate;
	}

	public String getStatus() {
		return Status;
	}

	public void setStatus(String status) {
		Status = status;
	}
	
	

}
