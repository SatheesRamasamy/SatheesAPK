package example.sofarmanager.reuse;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Vector;

import org.json.JSONArray;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.KvmSerializable;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import android.util.Log;

/**
 * @author Muthaiya.M
 * 
 */

public class CallApi extends Vector<String> implements KvmSerializable 
{
	public final String SOAP_ACTION = "http://www.w3.org/2001/XMLSchema";
	// http://tempuri.org/
	// http://tempuri.org/LocateNumber

	public String OPERATION_NAME = "login";
	// LocateNumber

	public final String WSDL_TARGET_NAMESPACE = "http://www.w3.org/2001	/XMLSchema";
	// http://tempuri.org/

	public final String SOAP_ADDRESS = "http://staging.propay.co.za/api/dashboard/Shofar_mobi_index.php";

	// http://www.mobilenumbertracker.com/service/MNT.asmx

	public CallApi() 
		{
			
		}	

	
	//========================================================================================
	// ======================================= New User Registration==========================
	//========================================================================================
	public String newUserRegistration(String id_no1, String email1,	String mobile_no1, String language1, 
									  String password1,String cnf_password1, int platform_id1, 
									  String device_id1,String methodToCall)
	{
		OPERATION_NAME = methodToCall;
		SoapObject request = new SoapObject(WSDL_TARGET_NAMESPACE,	OPERATION_NAME);
		
		// adding parameters
		PropertyInfo pi = new PropertyInfo();
		pi.setName("id_no");
		pi.setValue(id_no1);
		pi.setType(String.class);
		request.addProperty(pi);

		pi = new PropertyInfo();
		pi.setName("email");
		pi.setValue(email1);
		pi.setType(String.class);
		request.addProperty(pi);

		pi = new PropertyInfo();
		pi.setName("mobile");
		pi.setValue(mobile_no1);
		pi.setType(String.class);
		request.addProperty(pi);

		pi = new PropertyInfo();
		pi.setName("language");
		pi.setValue(language1);
		pi.setType(String.class);
		request.addProperty(pi);

		pi = new PropertyInfo();
		pi.setName("pword");
		pi.setValue(password1);
		pi.setType(String.class);
		request.addProperty(pi);

		pi = new PropertyInfo();
		pi.setName("confirm_pass");
		pi.setValue(cnf_password1);
		pi.setType(String.class);
		request.addProperty(pi);

		pi = new PropertyInfo();
		pi.setName("platform_id");
		pi.setValue(platform_id1);
		pi.setType(Integer.class);
		request.addProperty(pi);
		
		pi = new PropertyInfo();
		pi.setName("device_id");
		pi.setValue(device_id1);
		pi.setType(String.class);
		request.addProperty(pi);

		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
		envelope.dotNet = true;
		

		envelope.setOutputSoapObject(request);

		HttpTransportSE httpTransport = new HttpTransportSE(SOAP_ADDRESS);
		Object response = null;

		try {
				httpTransport.call(SOAP_ACTION, envelope);
				response = envelope.getResponse();
			} 
		catch (Exception exception) 
			{
				response = exception.toString();
			}
		System.out.println("newUserRegistration......." + response);
		return response.toString();
	}
	
	
	
	
	
	//========================================================================================
	//=====================================Login Method=======================================
	//========================================================================================
	public String loginMethodCall(String username, String password,	String methodToCall) 
		{
			OPERATION_NAME = methodToCall;
			SoapObject request = new SoapObject(WSDL_TARGET_NAMESPACE,OPERATION_NAME);
			// adding parameters
			PropertyInfo pi = new PropertyInfo();
			pi.setName("username");
			pi.setValue(username);
			pi.setType(String.class);
			request.addProperty(pi);
	
			pi = new PropertyInfo();
			pi.setName("pword");
			pi.setValue(password);
			pi.setType(String.class);
			request.addProperty(pi);
	
			SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
			envelope.dotNet = true;
			envelope.setOutputSoapObject(request);
	
			HttpTransportSE httpTransport = new HttpTransportSE(SOAP_ADDRESS);
			Object response = null;
			try{
					// Log.d("request:",httpTransport.requestDump);
					httpTransport.debug=true;
					httpTransport.call(SOAP_ACTION, envelope);
					String val=httpTransport.requestDump;
					Log.d("Testing", "Test Login"+val);
					response = envelope.getResponse();
			   }
			catch (Exception exception)
				{
					response = exception.toString();
				}
			System.out.println("loginMethodCall......." + response);
			return response.toString();
		}

	
	
	//========================================================================================
	//==================================SG member attendance data=============================
	//========================================================================================
	public String get_Sg_member_attendance(String sg_id, String methodname)
		{
			OPERATION_NAME = "get_small_group_members_and_attendance";
			SoapObject request = new SoapObject(WSDL_TARGET_NAMESPACE,OPERATION_NAME);
			// adding parameters
			PropertyInfo pi = new PropertyInfo();
			pi.setName("sg_id");
			pi.setValue(sg_id);
			pi.setType(String.class);
			request.addProperty(pi);
	
			SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
			envelope.dotNet = true;	
			envelope.setOutputSoapObject(request);
	
			HttpTransportSE httpTransport = new HttpTransportSE(SOAP_ADDRESS);
			String response = null;
			try
				{
					httpTransport.call(SOAP_ACTION, envelope);
					response = envelope.getResponse().toString();
				}
			catch (Exception exception)
				{
					response = exception.toString();
				}
			System.out.println("get_Sg_member_attendance......." + response);
			return response.toString();
		}
	
	//========================================================================================
	//================================GEt mail id=============================================
	//========================================================================================
	public String getMailID(String memno, String methodname) 
		{
			OPERATION_NAME = "get_member_details";
			SoapObject request = new SoapObject(WSDL_TARGET_NAMESPACE,OPERATION_NAME);
			// adding parameters
			PropertyInfo pi = new PropertyInfo();
			pi.setName("mem_no");
			pi.setValue(memno);
			pi.setType(String.class);
			request.addProperty(pi);
	
			SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
			envelope.dotNet = true;
			envelope.setOutputSoapObject(request);
	
			HttpTransportSE httpTransport = new HttpTransportSE(SOAP_ADDRESS);
			String response = null;
			try
			{
				httpTransport.call(SOAP_ACTION, envelope);
				response = envelope.getResponse().toString();
			}
	
			catch (Exception exception)
			{
				response = exception.toString();
			}
	
			System.out.println("get_member_details......." + response);
			return response.toString();
	}


	//========================================================================================
	//============================ Method to get the inbox data ==============================
	//========================================================================================
	public String inbox_get_data_MethodCall(String member_no, String methodToCall)
		{
			OPERATION_NAME = methodToCall;
			SoapObject request = new SoapObject(WSDL_TARGET_NAMESPACE,OPERATION_NAME);
	
			// adding parameters
			PropertyInfo pi = new PropertyInfo();
			pi.setName("mem_no");
			pi.setValue(member_no);
			pi.setType(String.class);
			request.addProperty(pi);
	
			SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
			envelope.dotNet = true;
	
			envelope.setOutputSoapObject(request);
	
			HttpTransportSE httpTransport = new HttpTransportSE(SOAP_ADDRESS);
			String response = null;
	
			try {
					httpTransport.call(SOAP_ACTION, envelope);
					response = envelope.getResponse().toString();
				} 
			catch (Exception exception) 
				{
					response = exception.toString();
				}
			System.out.println("Inbox Data......." + response);
			return response.toString();
		}

	
	
	//========================================================================================
	// =========================Method Get Data For OutStanding Task==========================
	//========================================================================================
	public String get_out_standing_data_MethodCall(String member_no,String methodToCall) 
		{
			OPERATION_NAME = methodToCall;
			SoapObject request = new SoapObject(WSDL_TARGET_NAMESPACE,OPERATION_NAME);
	
			PropertyInfo pi = new PropertyInfo();
			pi.setName("mem_no");
			pi.setValue(member_no);
			pi.setType(String.class);
			request.addProperty(pi);
	
			SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
			envelope.dotNet = true;
	
			envelope.setOutputSoapObject(request);
	
			HttpTransportSE httpTransport = new HttpTransportSE(SOAP_ADDRESS);
			String response = null;
	
			try {
					httpTransport.call(SOAP_ACTION, envelope);
					response = envelope.getResponse().toString();
				} 
			catch (Exception exception) 
				{
					response = exception.toString();
				}
			System.out.println("Outsatnding Task......." + response);
			return response.toString();
		}

	//========================================================================================
	// =============================Verify the security question answer=======================
	//========================================================================================
	public String send_question_ans(String answers,int q_id)
		{
			OPERATION_NAME = "check_security_question_answer";
	
			SoapObject request = new SoapObject(WSDL_TARGET_NAMESPACE,OPERATION_NAME);
	
			PropertyInfo pi = new PropertyInfo();
			pi.setName("answer");
			pi.setValue(answers);
			pi.setType(String.class);
			request.addProperty(pi);
			
			pi = new PropertyInfo();
			pi.setName("question_id");
			pi.setValue(q_id);
			pi.setType(Integer.class);
			request.addProperty(pi);

	
			SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
			envelope.dotNet = true;
	
			envelope.setOutputSoapObject(request);
	
			HttpTransportSE httpTransport = new HttpTransportSE(SOAP_ADDRESS);
			String response = null;
	
			try {
					httpTransport.call(SOAP_ACTION, envelope);
					response = envelope.getResponse().toString();
				} 
			catch (Exception exception) 
				{
					response = exception.toString();
				}
			System.out.println("Sequrity ans......." + response);
			return response.toString();
		}
	
	
	
	
	//========================================================================================
	//========================= Get the security questions====================================
	//========================================================================================
	public String getSecurityQuestion(String mail_id) 
		{
			OPERATION_NAME = "get_security_question";
	
			SoapObject request = new SoapObject(WSDL_TARGET_NAMESPACE,OPERATION_NAME);
	
			PropertyInfo pi = new PropertyInfo();
			pi.setName("email_address");
			pi.setValue(mail_id);
			pi.setType(String.class);
			request.addProperty(pi);
	
			SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
			envelope.dotNet = true;
	
			envelope.setOutputSoapObject(request);
	
			HttpTransportSE httpTransport = new HttpTransportSE(SOAP_ADDRESS);
			String response = null;
	
			try {
					httpTransport.call(SOAP_ACTION, envelope);
					response = envelope.getResponse().toString();
				} 
			catch (Exception exception) 
				{
					response = exception.toString();
				}
			System.out.println("Sequirty Questions......." + response);
			return response.toString();
	}
	
	
	
	
	//========================================================================================
	//=========================== For resetting password =====================================
	//========================================================================================
	public String reset_password(String memNo, String newPass,String cnfPass) 
		{
			OPERATION_NAME = "reset_pass";
	
			SoapObject request = new SoapObject(WSDL_TARGET_NAMESPACE,OPERATION_NAME);
	
			PropertyInfo pi = new PropertyInfo();
			pi.setName("mem_no");
			pi.setValue(memNo);
			pi.setType(String.class);
			request.addProperty(pi);
			
			//System.out.println("memNo ="+memNo);
	
			pi = new PropertyInfo();
			pi.setName("new_pass");
			pi.setValue(newPass);
			pi.setType(String.class);
			request.addProperty(pi);
			
			//System.out.println("new_pass ="+newPass);
	
			pi = new PropertyInfo();
			pi.setName("conf_pass");
			pi.setValue(cnfPass);
			pi.setType(String.class);
			request.addProperty(pi);
			
			//System.out.println("conf_pass ="+cnfPass);
	
			
	
			SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
			envelope.dotNet = true;
			envelope.setOutputSoapObject(request);
	
			HttpTransportSE httpTransport = new HttpTransportSE(SOAP_ADDRESS);
			String response = null;
	
			try {
					httpTransport.call(SOAP_ACTION, envelope);
					response = envelope.getResponse().toString();
				} 
			catch (Exception exception) 
				{
					response = exception.toString();
				}
			System.out.println("ResetPassword = " + response);
			return response.toString();
	}
	
	
	//========================================================================================
	//==============================Update Attendance=========================================
	//========================================================================================

	
	public String update_attendance_MethodCall(String sg_id,String[] members_present, String attendance_date, String mem_no,
			String methodToCall)
	{
		OPERATION_NAME = methodToCall;

		SoapObject request = new SoapObject(WSDL_TARGET_NAMESPACE,OPERATION_NAME);

		
		// adding parameters
		PropertyInfo pi = new PropertyInfo();
		pi.setName("sg_id");
		pi.setValue(sg_id);
		pi.setType(String.class);
		request.addProperty(pi);

		

		JSONArray arr = new JSONArray();
		for(int i=0;i<members_present.length;i++)
			{
				arr.put(members_present[i]);
			}
		//arr.put(11015);
		System.out.println(">>>>>>>>>>>>>>>>>" + arr.toString());

		pi = new PropertyInfo();
		pi.setName("members_present");
		pi.setValue(arr.toString());
		pi.setType(JSONArray.class);
		request.addProperty(pi);

		pi = new PropertyInfo();
		pi.setName("attendance_date");
		pi.setValue(attendance_date);
		pi.setType(String.class);
		request.addProperty(pi);

		pi = new PropertyInfo();
		pi.setName("mem_no");
		pi.setValue(mem_no);
		pi.setType(int.class);
		request.addProperty(pi);

		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
		envelope.dotNet = true;
		envelope.setOutputSoapObject(request);

		HttpTransportSE httpTransport = new HttpTransportSE(SOAP_ADDRESS);
		Object response = null;

		try {
				httpTransport.debug=true;
				httpTransport.call(SOAP_ACTION, envelope);
				response = envelope.getResponse();
			} 
		catch (Exception exception) 
			{
				response = exception;
			}

		System.out.println("Request......" + httpTransport.requestDump);
		System.out.println("update_attendance =" + response);
		return response.toString();
	}
	
	
	//========================================================================================
	// =======================Method Get Data For Attendance =================================
	//========================================================================================

	public String get_attendance_data(String sg_id, String methodToCall) 
		{
			OPERATION_NAME = methodToCall;
	
			SoapObject request = new SoapObject(WSDL_TARGET_NAMESPACE,OPERATION_NAME);
	
			PropertyInfo pi = new PropertyInfo();
			pi.setName("sg_id");
			pi.setValue(sg_id);
			pi.setType(String.class);
			request.addProperty(pi);
	
			SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
			envelope.dotNet = true;
			envelope.setOutputSoapObject(request);
	
			HttpTransportSE httpTransport = new HttpTransportSE(SOAP_ADDRESS);
			Object response = null;
	
			try {
					httpTransport.call(SOAP_ACTION, envelope);
					response = envelope.getResponse();
				} 
			catch (Exception exception) 
				{
					response = exception;
				}
			System.out.println("GetAttendance......." + response);
			return response.toString();
		}

	//========================================================================================
	//==========================Search Sg member==============================================
	//========================================================================================	
	public String Search_SG_member(String searchtext, String basedon,String methodtocall) 
		{
			OPERATION_NAME = methodtocall;
			SoapObject request = new SoapObject(WSDL_TARGET_NAMESPACE,OPERATION_NAME);
			// adding parameters
			PropertyInfo pi = new PropertyInfo();
	
			pi = new PropertyInfo();
			pi.setName("string");
			pi.setValue(searchtext);
			pi.setType(String.class);
			request.addProperty(pi);
	
			pi = new PropertyInfo();
			pi.setName("str_type");
			pi.setValue(basedon);
			pi.setType(String.class);
			request.addProperty(pi);
	
			SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
			envelope.dotNet = true;	
			envelope.setOutputSoapObject(request);
	
			HttpTransportSE httpTransport = new HttpTransportSE(SOAP_ADDRESS);
			String response = null;
			try
				{
					httpTransport.call(SOAP_ACTION, envelope);
					response = envelope.getResponse().toString();
				}
			catch (Exception exception)
				{
					response = exception.toString();	
				}
	
			System.out.println("Search_SG_member......." + response);
			return response.toString();
	}
	
	
	
	//========================================================================================
	//=================================Add SG Member==========================================
	//========================================================================================

	public String addSG_member(int creator_mem_no,int memno,String methodtocall) 
		{
			OPERATION_NAME = methodtocall;
			SoapObject request = new SoapObject(WSDL_TARGET_NAMESPACE,OPERATION_NAME);
			// adding parameters
			PropertyInfo pi = new PropertyInfo();
			
			pi = new PropertyInfo();
			pi.setName("creator_mem_no");
			pi.setValue(creator_mem_no);
			pi.setType(Integer.class);
			request.addProperty(pi);
			
			pi = new PropertyInfo();
			pi.setName("mem_no");
			pi.setValue(memno);
			pi.setType(String.class);
			request.addProperty(pi);
	
					
			
			SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
			envelope.dotNet = true;
			// envelope.headerOut= new Element[1];
			// envelope.headerOut[0] = buildAuthHeader();
	
			envelope.setOutputSoapObject(request);
	
			HttpTransportSE httpTransport = new HttpTransportSE(SOAP_ADDRESS);
			String response = null;
			try
				{
					httpTransport.call(SOAP_ACTION, envelope);
					response = envelope.getResponse().toString();
				}
	
			catch (Exception exception)
				{
					response = exception.toString();
				}
	
			System.out.println("addSG_member......." + response);
			return response.toString();

	}
	
	
	//========================================================================================
	//===================  Adding the security question in Register screen ===================
	//========================================================================================

	public String add_security_question_and_answer(String memNo,String que1,String ans1,String que2,String ans2)
		{
			OPERATION_NAME = "add_security_questions";
			SoapObject request = new SoapObject(WSDL_TARGET_NAMESPACE,OPERATION_NAME);
			// adding parameters
			PropertyInfo pi = new PropertyInfo();
			pi.setName("mem_no");
			pi.setValue(memNo);
			pi.setType(String.class);
			request.addProperty(pi);
			
			pi = new PropertyInfo();
			pi.setName("question1");
			pi.setValue(que1);
			pi.setType(String.class);
			request.addProperty(pi);
			
			pi = new PropertyInfo();
			pi.setName("answer1");
			pi.setValue(ans1);
			pi.setType(String.class);
			request.addProperty(pi);
			
			
			pi = new PropertyInfo();
			pi.setName("question2");
			pi.setValue(que2);
			pi.setType(String.class);
			request.addProperty(pi);
			
			pi = new PropertyInfo();
			pi.setName("answer2");
			pi.setValue(ans2);
			pi.setType(String.class);
			request.addProperty(pi);
	
			SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(	SoapEnvelope.VER11);
			envelope.dotNet = true;
			envelope.setOutputSoapObject(request);
	
			HttpTransportSE httpTransport = new HttpTransportSE(SOAP_ADDRESS);
			String response = null;
	
			try {
					httpTransport.debug=true;
					httpTransport.call(SOAP_ACTION, envelope);
					Log.d("request:", httpTransport.requestDump);
					System.out.println(">>>>>>>>>>>request");
					response = envelope.getResponse().toString();
				} 
			catch (Exception exception) 
				{
					response = exception.toString();
				}
	
			System.out.println("add_security_questions......." + response);
			return response.toString();
		}
	
	//========================================================================================
	//================================Get the SG id===========================================
	//========================================================================================

	public String get_sg_id(String memno) 
		{
			OPERATION_NAME = "get_sg_id";
			SoapObject request = new SoapObject(WSDL_TARGET_NAMESPACE,OPERATION_NAME);
			// adding parameters
			PropertyInfo pi = new PropertyInfo();
			pi.setName("mem_no");
			pi.setValue(memno);
			pi.setType(String.class);
			request.addProperty(pi);
	
			SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(	SoapEnvelope.VER11);
			envelope.dotNet = true;
			
			envelope.setOutputSoapObject(request);
	
			HttpTransportSE httpTransport = new HttpTransportSE(SOAP_ADDRESS);
			String response = null;
	
			try {
					httpTransport.debug=true;
					httpTransport.call(SOAP_ACTION, envelope);
					Log.d("request:", httpTransport.requestDump);
					System.out.println(">>>>>>>>>>>request");
					response = envelope.getResponse().toString();
				} 
			catch (Exception exception) 
				{
					response = exception.toString();
				}
	
			System.out.println("get_sg_id......." + response);
			return response.toString();
	}

	//========================================================================================
	// =====================get Email Recipient list in Email Screen==========================
	//========================================================================================
	public String get_email_receipients(String sgid,String memno,String method)
		{
			OPERATION_NAME = method;
			SoapObject request = new  SoapObject(WSDL_TARGET_NAMESPACE, OPERATION_NAME);
			PropertyInfo pi = new  PropertyInfo();
			
			pi.setName("sg_id");
			pi.setValue(sgid);
			pi.setType(String.class);
			request.addProperty(pi);
			
			pi = new PropertyInfo();
			pi.setName("mem_no");
			pi.setValue(memno);
			pi.setType(String.class);
			request.addProperty(pi);
			
			SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(	SoapEnvelope.VER11);
			envelope.dotNet = true;
			
			envelope.setOutputSoapObject(request);
			
			HttpTransportSE httpTransport = new HttpTransportSE(SOAP_ADDRESS);
			String response = null;
			
			try {
					httpTransport.debug=true;
					httpTransport.call(SOAP_ACTION, envelope);
					Log.d("request:", httpTransport.requestDump);
					System.out.println(">>>>>>>>>>>request");
					response = envelope.getResponse().toString();
				} 
			catch (Exception exception) 
				{
					response = exception.toString();
				}

			System.out.println("get_email_receipients = " + response);
			return response.toString();			
		}
	
	
	//========================================================================================
	//=========================================Sending Email =================================
	//========================================================================================

	public String Email(ArrayList<String>send_to,String from_mem,String sub,String msg,String method)
			{
				OPERATION_NAME = method;//"send_email";
				SoapObject request = new SoapObject(WSDL_TARGET_NAMESPACE,OPERATION_NAME);
				
				PropertyInfo pi = new PropertyInfo();
				
				JSONArray arr = new JSONArray();
				
				for(int i=0;i<send_to.size();i++)
					{
						arr.put(send_to.get(i));
					}
				arr.toString();
					
				pi = new PropertyInfo();
				pi.setName("to");
				pi.setValue(arr.toString());
				pi.setType(JSONArray.class);
				request.addProperty(pi);
				
				pi=new PropertyInfo();
				pi.setName("from_mem_no");
				pi.setValue(from_mem);
				pi.setType(String.class);
				request.addProperty(pi);
					
				pi=new PropertyInfo();
				pi.setName("subject");
				pi.setValue(sub);
				pi.setType(String.class);
				request.addProperty(pi);
				
				pi=new PropertyInfo();
				pi.setName("message");
				pi.setValue(msg);
				pi.setType(String.class);
				request.addProperty(pi);
					
					
				SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
				envelope.dotNet = true;				
				envelope.setOutputSoapObject(request);
					
				HttpTransportSE httpTransport = new HttpTransportSE(SOAP_ADDRESS);
				String response = null;
					
				try	{
						System.out.println("Service");
						//httpTransport.debug=true;
						//Log.d("request", httpTransport.requestDump);
						httpTransport.call(SOAP_ACTION, envelope);
						response = envelope.getResponse().toString();		
					}	
				catch (Exception exception)
					{
						response = exception.toString();
					}	
							
				System.out.println("Email sending=" + response);
				return response.toString();
			}

	
	
	
	//========================================================================================
	//======================= get_small_group_members_and_attendance==========================
	//========================================================================================
	public String get_sgMembersAttendance(String sg_id, String methodToCall) 
		{
			OPERATION_NAME = methodToCall;
			SoapObject request = new SoapObject(WSDL_TARGET_NAMESPACE,OPERATION_NAME);
			// adding parameters
			PropertyInfo pi = new PropertyInfo();
			pi.setName("sg_id");
			pi.setValue(sg_id);
			pi.setType(String.class);
			request.addProperty(pi);
	
			SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(	SoapEnvelope.VER11);
			envelope.dotNet = true;
			envelope.setOutputSoapObject(request);
	
			HttpTransportSE httpTransport = new HttpTransportSE(SOAP_ADDRESS);
			String response = null;
	
			try {
					httpTransport.debug=true;
					httpTransport.call(SOAP_ACTION, envelope);
					Log.d("request:", httpTransport.requestDump);
					System.out.println(">>>>>>>>>>>request");
					response = envelope.getResponse().toString();
				} 
			catch (Exception exception) 
				{
					response = exception.toString();
				}
			System.out.println("get_sgMembersAttendance = " + response);
			return response.toString();
		}
	
	//========================================================================================
	//=============================Get the task type==========================================
	//========================================================================================
	
	public String get_task_type_list(String task_grp_id)
		{
			OPERATION_NAME = "get_task_types";
			SoapObject request = new SoapObject(WSDL_TARGET_NAMESPACE,OPERATION_NAME);

			PropertyInfo pi = new PropertyInfo();
			pi.setName("id");
			pi.setValue(task_grp_id);
			pi.setType(String.class);
			request.addProperty(pi);
	
			SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
			envelope.dotNet = true;
			envelope.setOutputSoapObject(request);
	
			HttpTransportSE httpTransport = new HttpTransportSE(SOAP_ADDRESS);
			String response = null;
	
			try {
					httpTransport.call(SOAP_ACTION, envelope);
					response = envelope.getResponse().toString();
				} 
			catch (Exception exception)
				{
					response = exception.toString();
				}
			System.out.println("get task type = " + response);
			return response.toString();
		}
	
	
	//========================================================================================
	//=============================Get the sub task type======================================
	//========================================================================================
	
	public String get_sub_task_type_list(int task_types_id)
		{
			OPERATION_NAME = "get_subtask_types";
			SoapObject request = new SoapObject(WSDL_TARGET_NAMESPACE,OPERATION_NAME);

			PropertyInfo pi = new PropertyInfo();
			pi.setName("task_type");
			pi.setValue(task_types_id);
			pi.setType(Integer.class);
			request.addProperty(pi);
	
			SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
			envelope.dotNet = true;
			envelope.setOutputSoapObject(request);
	
			HttpTransportSE httpTransport = new HttpTransportSE(SOAP_ADDRESS);
			String response = null;
	
			try {
					httpTransport.call(SOAP_ACTION, envelope);
					response = envelope.getResponse().toString();
				} 
			catch (Exception exception)
				{
					response = exception.toString();
				}
			System.out.println("get sub task type = " + response);
			return response.toString();
		}
	
	
	//========================================================================================
	//==============================Get task Type group=======================================
	//========================================================================================

	public String get_task_type_group()
		{
			String METHOD_NAME = "get_task_types_group";
			
			SoapObject request = new SoapObject(WSDL_TARGET_NAMESPACE, METHOD_NAME);
			
			//PropertyInfo pi = new  PropertyInfo();
			SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
			envelope.dotNet = true;
			envelope.setOutputSoapObject(request);
		
			HttpTransportSE httpTransport = new HttpTransportSE(SOAP_ADDRESS);
			String response = null;
		
			try	{
					System.out.println("Service");
					//httpTransport.debug=true;
					//Log.d("request", httpTransport.requestDump);
					httpTransport.call(SOAP_ACTION, envelope);
					response = envelope.getResponse().toString();		
				}	
			catch (Exception exception)
				{
					response = exception.toString();
				}	
					
			System.out.println("get_task_type_group......." + response);
			return response.toString();
	}
	
	
	
	//========================================================================================
	// ================== method to get all the task owner list ==============================
	//========================================================================================

	public String task_owner_MethodCall(String mem_no, String methodToCall)
		{
			OPERATION_NAME = methodToCall;
			SoapObject request = new SoapObject(WSDL_TARGET_NAMESPACE,OPERATION_NAME);
			// adding parameters
			PropertyInfo pi = new PropertyInfo();
			pi.setName("mem_no");
			int mem = Integer.parseInt(mem_no);
			pi.setValue(mem);
			pi.setType(String.class);
			request.addProperty(pi);
	
			SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
			envelope.dotNet = true;
			envelope.setOutputSoapObject(request);
	
			HttpTransportSE httpTransport = new HttpTransportSE(SOAP_ADDRESS);
			String response = null;
	
			try {
					httpTransport.call(SOAP_ACTION, envelope);
					response = envelope.getResponse().toString();
				} 
			catch (Exception exception) 
				{
					response = exception.toString();
				}
	
			System.out.println("task owner......." + response);
			return response.toString();
		}

	//========================================================================================
	//=================================Add task Method =======================================
	//========================================================================================
	

//add_task($subject,$description,$task_subtype_id,$owner,$mem_no,
//		$due_date,$priority,$attachment_name,$attachment_data,$file_size,$task_owner_cc)
	
	public String post_data_MethodCall(String subject, String description,
			int sub_task_type_id, String owner, String mem_no, String due_date,
			String priority, List<String> task_owner_list,
			String attachment_name, String attachment_data, int file_size,
			String methodToCall) {
		
		OPERATION_NAME = methodToCall;
		SoapObject request1 = new SoapObject(WSDL_TARGET_NAMESPACE,OPERATION_NAME);

		PropertyInfo pi = new PropertyInfo();
		pi.setName("subject");
		pi.setValue(subject);
		pi.setType(String.class);
		request1.addProperty(pi);
		
		System.out.println("sub = "+subject);
		
		pi = new PropertyInfo();
		pi.setName("description");
		pi.setValue(description);
		pi.setType(String.class);
		request1.addProperty(pi);
		System.out.println("sub = "+description);

		pi = new PropertyInfo();
		pi.setName("task_subtype_id");
		pi.setValue(sub_task_type_id);
		pi.setType(String.class);
		request1.addProperty(pi);
		System.out.println("sub_task_type_id = "+sub_task_type_id);

		pi = new PropertyInfo();
		pi.setName("owner");
		pi.setValue(owner);
		pi.setType(String.class);
		request1.addProperty(pi);
		System.out.println("owner = "+owner);

		pi = new PropertyInfo();
		pi.setName("mem_no");
		pi.setValue(mem_no);
		pi.setType(String.class);
		request1.addProperty(pi);
		System.out.println("mem_no = "+mem_no);

		pi = new PropertyInfo();
		pi.setName("due_date");
		pi.setValue(due_date);
		pi.setType(String.class);
		request1.addProperty(pi);
		System.out.println("due_date = "+due_date);

		pi = new PropertyInfo();
		pi.setName("priority");
		pi.setValue(priority);
		pi.setType(String.class);
		request1.addProperty(pi);
		System.out.println("priority = "+priority);
		
		
		
		
		if(attachment_name.equals("nofile"))
			{
				pi = new PropertyInfo();
				pi.setName("attachment_name");
				pi.setValue("null");
				pi.setType(String.class);
				request1.addProperty(pi);
				System.out.println("attachment_name in null = "+attachment_name);
			}
		else
			{
				pi = new PropertyInfo();
				pi.setName("attachment_name");
				pi.setValue(attachment_name);
				pi.setType(String.class);
				request1.addProperty(pi);
				System.out.println("attachment_name = "+attachment_name);
			}
		
		if(attachment_data.equals("nodata"))
			{
				pi = new PropertyInfo();
				pi.setName("attachment_data");
				pi.setValue("null");
				pi.setType(String.class);
				request1.addProperty(pi);
				System.out.println("attachment_data = "+attachment_data);
			}
		else	
			{
				pi = new PropertyInfo();
				pi.setName("attachment_data");
				pi.setValue(attachment_data);
				pi.setType(String.class);
				request1.addProperty(pi);
				System.out.println("attachment_data = "+attachment_data);
			}
		
		if(file_size!=0)
			{
				pi = new PropertyInfo();
				pi.setName("file_size");
				pi.setValue(file_size);
				pi.setType(int.class);
				request1.addProperty(pi);
				System.out.println("file_size = "+file_size);
			}
		else
			{
				pi = new PropertyInfo();
				pi.setName("file_size");
				pi.setValue("null");
				pi.setType(int.class);
				request1.addProperty(pi);
				System.out.println("file_size = "+file_size);
			}	
			
		
		if(!task_owner_list.isEmpty())
			{
				JSONArray arr = new JSONArray();
				
				for(int i=0;i<task_owner_list.size();i++)
					{
						arr.put(task_owner_list.get(i));
						System.out.println("task_owner_list = "+task_owner_list.get(i));
					}
				arr.toString();
			
				pi = new PropertyInfo();
				pi.setName("task_owner_cc");
				pi.setValue(arr.toString());
				pi.setType(JSONArray.class);
				request1.addProperty(pi);
			}
		SoapSerializationEnvelope envelope1 = new SoapSerializationEnvelope(SoapEnvelope.VER11);
		envelope1.dotNet = true;
		envelope1.setOutputSoapObject(request1);

		HttpTransportSE httpTransport = new HttpTransportSE(SOAP_ADDRESS);
		String response1 = null;

		try {
				httpTransport.call(SOAP_ACTION, envelope1);
				response1 = envelope1.getResponse().toString();
			} 
		catch (Exception exception) 
			{
				response1 = exception.toString();
			}

		System.out.println("Add Task = " +response1);
		return response1.toString();
	}

	
	
	
	//========================================================================================
	//================================Get the member for Add SG Member =======================
	//========================================================================================
	public String get_members_in_sg_leader_congregation(String sg_leader_mem_no, String methodtocall)
		{
			OPERATION_NAME = methodtocall;
			SoapObject request = new SoapObject(WSDL_TARGET_NAMESPACE,OPERATION_NAME);
			// adding parameters
			PropertyInfo pi = new PropertyInfo();
			pi.setName("sg_leader_mem_no");
			pi.setValue(sg_leader_mem_no);
			pi.setType(String.class);
			request.addProperty(pi);
	
			SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
			envelope.dotNet = true;
			envelope.setOutputSoapObject(request);
			
			
			HttpTransportSE httpTransport = new HttpTransportSE(SOAP_ADDRESS);
			String response = null;
			try{
					httpTransport.debug=true;
					httpTransport.call(SOAP_ACTION, envelope);
					Log.d("request:", httpTransport.requestDump);
					response = envelope.getResponse().toString();
				}
			catch (Exception exception)
				{
					response = exception.toString();
				}
	
			System.out.println("get_members_in_sg_leader_congregation......." + response);
			return response.toString();
		}
	
	
	//========================================================================================
	//==============================Add the Visitor===========================================
	//========================================================================================

	public String addSmallgroup_Visitor(String creator_mem_no,
			String first_name, String surname, int gender, String dob,
			int marital_status, String street_address, String suburb,
			String city, String email, String mobile, String sg_id,
			String methodToCall) 
		{
			OPERATION_NAME = methodToCall;
			SoapObject request = new SoapObject(WSDL_TARGET_NAMESPACE,	OPERATION_NAME);
			// adding parameters
			PropertyInfo pi = new PropertyInfo();
			pi.setName("creator_mem_no");
			pi.setValue(creator_mem_no);
			pi.setType(String.class);
			request.addProperty(pi);
	
			pi = new PropertyInfo();
			pi.setName("first_name");
			pi.setValue(first_name);
			pi.setType(String.class);
			request.addProperty(pi);
	
			pi = new PropertyInfo();
			pi.setName("surname");
			pi.setValue(surname);
			pi.setType(String.class);
			request.addProperty(pi);
	
			pi = new PropertyInfo();
			pi.setName("gender");
			pi.setValue(gender);
			pi.setType(Integer.class);
			request.addProperty(pi);
	
			pi = new PropertyInfo();
			pi.setName("dob");
			pi.setValue(dob);
			pi.setType(String.class);
			request.addProperty(pi);
	
			pi = new PropertyInfo();
			pi.setName("marital_status");
			pi.setValue(marital_status);
			pi.setType(Integer.class);
			request.addProperty(pi);
	
			pi = new PropertyInfo();
			pi.setName("street_address");
			pi.setValue(street_address);
			pi.setType(String.class);
			request.addProperty(pi);
	
			pi = new PropertyInfo();
			pi.setName("suburb");
			pi.setValue(suburb);
			pi.setType(String.class);
			request.addProperty(pi);
	
			pi = new PropertyInfo();
			pi.setName("city");
			pi.setValue(city);
			pi.setType(String.class);
			request.addProperty(pi);
	
			pi = new PropertyInfo();
			pi.setName("email");
			pi.setValue(email);
			pi.setType(String.class);
			request.addProperty(pi);
	
			pi = new PropertyInfo();
			pi.setName("mobile");
			pi.setValue(mobile);
			pi.setType(String.class);
			request.addProperty(pi);
	
			pi = new PropertyInfo();
			pi.setName("sg_id");
			pi.setValue(sg_id);
			pi.setType(String.class);
			request.addProperty(pi);
	
			SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
			envelope.dotNet = true;	
			envelope.setOutputSoapObject(request);
	
			HttpTransportSE httpTransport = new HttpTransportSE(SOAP_ADDRESS);
			String response = null;
			try{
					httpTransport.call(SOAP_ACTION, envelope);
					response = envelope.getResponse().toString();
			   }
			catch (Exception exception)
				{
					response = exception.toString();
				}
			System.out.println("Add Visitor = " + response);
			return response.toString();

	}
	
	
	//========================================================================================
	//==============================Get Member Details========================================
	//========================================================================================

	public String get_mem_details(String memNo) 
		{
			OPERATION_NAME = "get_member_details";
			SoapObject request = new SoapObject(WSDL_TARGET_NAMESPACE,	OPERATION_NAME);

			PropertyInfo pi = new PropertyInfo();
			pi.setName("mem_no");
			pi.setValue(memNo);
			pi.setType(String.class);
			request.addProperty(pi);
			
			SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
			envelope.dotNet = true;	
			envelope.setOutputSoapObject(request);
	
			HttpTransportSE httpTransport = new HttpTransportSE(SOAP_ADDRESS);
			String response = null;
			try{
					httpTransport.call(SOAP_ACTION, envelope);
					response = envelope.getResponse().toString();
			   }
			catch (Exception exception)
				{
					response = exception.toString();
				}
			System.out.println("get_member_details = " + response);
			return response.toString();
		}
	
	//========================================================================================
	//============================== Update Member Details ===================================
	//========================================================================================	
	public String update_mem_details(String memno,String firstname,String surName,String fullnames,
			String datebirth,String lang,String emailid,String mobileNo,String worknumber,
			String homenumber,String streetaddress,String subur,String citys,String province,String occu) 
		{
			OPERATION_NAME = "update_information";
			SoapObject request = new SoapObject(WSDL_TARGET_NAMESPACE,	OPERATION_NAME);

			PropertyInfo pi = new PropertyInfo();
			pi.setName("mem_no");
			pi.setValue(memno);
			pi.setType(String.class);
			request.addProperty(pi);
			
			pi = new PropertyInfo();
			pi.setName("first_name");
			pi.setValue(firstname);
			pi.setType(String.class);
			request.addProperty(pi);
			
			pi = new PropertyInfo();
			pi.setName("surname");
			pi.setValue(surName);
			pi.setType(String.class);
			request.addProperty(pi);
			
			pi = new PropertyInfo();
			pi.setName("full_names");
			pi.setValue(fullnames);
			pi.setType(String.class);
			request.addProperty(pi);
			
			
			pi = new PropertyInfo();
			pi.setName("date_birth");
			pi.setValue(datebirth);
			pi.setType(Date.class);
			request.addProperty(pi);
			
			
			pi = new PropertyInfo();
			pi.setName("language");
			pi.setValue(lang);
			pi.setType(Integer.class);
			request.addProperty(pi);
			
			
			pi = new PropertyInfo();
			pi.setName("email");
			pi.setValue(emailid);
			pi.setType(String.class);
			request.addProperty(pi);
			
			
			pi = new PropertyInfo();
			pi.setName("mobile");
			pi.setValue(mobileNo);
			pi.setType(String.class);
			request.addProperty(pi);
			
			pi = new PropertyInfo();
			pi.setName("work_number");
			pi.setValue(worknumber);
			pi.setType(String.class);
			request.addProperty(pi);
			
			
			pi = new PropertyInfo();
			pi.setName("home_number");
			pi.setValue(homenumber);
			pi.setType(String.class);
			request.addProperty(pi);
			
			pi = new PropertyInfo();
			pi.setName("street_address");
			pi.setValue(streetaddress);
			pi.setType(String.class);
			request.addProperty(pi);
			
			pi = new PropertyInfo();
			pi.setName("suburb");
			pi.setValue(subur);
			pi.setType(String.class);
			request.addProperty(pi);
			
			
			pi = new PropertyInfo();
			pi.setName("city");
			pi.setValue(citys);
			pi.setType(String.class);
			request.addProperty(pi);
			
			pi = new PropertyInfo();
			pi.setName("province");
			pi.setValue(province);
			pi.setType(String.class);
			request.addProperty(pi);
			
			pi = new PropertyInfo();
			pi.setName("occupation");
			pi.setValue(occu);
			pi.setType(String.class);
			request.addProperty(pi);
			
			SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
			envelope.dotNet = true;	
			envelope.setOutputSoapObject(request);
	
			HttpTransportSE httpTransport = new HttpTransportSE(SOAP_ADDRESS);
			String response = null;
			try{
					httpTransport.call(SOAP_ACTION, envelope);
					response = envelope.getResponse().toString();
			   }
			catch (Exception exception)
				{
					response = exception.toString();
				}
			System.out.println("update_information = " + response);
			return response.toString();
		}
	
	//========================================================================================
	//============================== Verify The Scanned Value=================================
	//========================================================================================	

	public String verify_scanned_code(String booking_ref,String captured_d_id,int capt_by)
		{
			OPERATION_NAME = "capture_registration";
			SoapObject request = new SoapObject(WSDL_TARGET_NAMESPACE,	OPERATION_NAME);

			PropertyInfo pi = new PropertyInfo();
			pi.setName("booking_reference");
			pi.setValue(booking_ref);
			pi.setType(String.class);
			request.addProperty(pi);
			
			pi = new PropertyInfo();
			pi.setName("captured_device_id");
			pi.setValue(captured_d_id);
			pi.setType(String.class);
			request.addProperty(pi);
			
			pi = new PropertyInfo();
			pi.setName("captured_by");
			pi.setValue(capt_by);
			pi.setType(Integer.class);
			request.addProperty(pi);
			
			SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
			envelope.dotNet = true;	
			envelope.setOutputSoapObject(request);
			
			HttpTransportSE httpTransport = new HttpTransportSE(SOAP_ADDRESS);
			String response = null;
			try{
					httpTransport.call(SOAP_ACTION, envelope);
					response = envelope.getResponse().toString();
			   }
			catch (Exception exception)
				{
					response = exception.toString();
				}
			System.out.println("verify scanned code = " + response);
			return response.toString();
		}
	

	@Override
	public Object getProperty(int arg0)
		{
			return null;
		}

	@Override
	public int getPropertyCount()
		{
			return this.size();
		}
	
	@Override
	public void getPropertyInfo(int arg0, Hashtable arg1, PropertyInfo arg2) 
		{
			arg2.name = "string";
			arg2.type = PropertyInfo.STRING_CLASS;
		}
	
	@Override
	public void setProperty(int arg0, Object arg1) 
		{
			this.add(arg1.toString());
		}	
	
}
