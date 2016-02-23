package example.sofarmanager.reuse;

import java.io.Serializable;
import java.util.ArrayList;

public class Parent implements Serializable {
	String Hearder, Subject, CreatedDate;
	ArrayList<Child> childdata;

	public Parent() {
		// TODO Auto-generated constructor stub
	}

	public Parent(String header, String sub, String creatdate,
			ArrayList<Child> data) {
		this.Hearder = header;
		this.Subject = sub;
		this.CreatedDate = creatdate;
		this.childdata = data;

	}

	public String getHearder() {
		return Hearder;
	}

	public void setHearder(String hearder) {
		Hearder = hearder;
	}

	public String getSubject() {
		return Subject;
	}

	public void setSubject(String subject) {
		Subject = subject;
	}

	public String getCreatedDate() {
		return CreatedDate;
	}

	public void setCreatedDate(String createdDate) {
		CreatedDate = createdDate;
	}

	public ArrayList<Child> getChilddata() {
		return childdata;
	}

	public void setChilddata(ArrayList<Child> childdata) {
		this.childdata = childdata;
	}

}
