package example.sofarmanager.reuse;

public class  Util 
{
	public static String member_no="";
	public static String sg_id="";
	public static String highest_roll="";
	public static String mailId="";
	
	public static void assign_value_frm_shrd(String mem_no,String sgid,String hgrol,String mail)
		{
			member_no = mem_no;
			sg_id = sgid;
			highest_roll = hgrol;
			mailId = mail;
			System.out.println("member_no = "+mem_no+" sg_id = "+sg_id+" highest_roll = "+highest_roll+" mailId= "+mailId);
		}
}
