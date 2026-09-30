package WM.test;

import org.json.JSONException;

import utils.password.PasswordUtil;

public class Prueba {
	
	  Class<? extends Prueba> d = this.getClass();
	  String c = d.getName();

	public static void main(String[] args) throws JSONException {

//	    ArrayList<String> user = new ArrayList<String>();
//	    ArrayList<ArrayList<String>> user2 = new ArrayList<ArrayList<String>>();
//
//	    Class thisClass = new Object(){}.getClass();
//	    String className = thisClass.getEnclosingClass().getSimpleName();
//	    String methodName = thisClass.getEnclosingMethod().getName();
//	    
//	    user.add("hola");
//	    user.add("hola2");
//	    user.add("hola3");
//	    user.add("hola4");
//	    
//	    user2.add(user);
//		user.clear();
//		user.add("holarr");
//	    user.add("hola2");
//	    user.add("hola3");
//	    user.add("hola4");
//	    
//	    
	   
		PasswordUtil.generateEncryptPass();
		System.out.println(PasswordUtil.decryptPassword("2A4EBD6EEF48082902C72EFFB08F9BD0"));
	  
	    
	
		//SeleniumUtil u = new SeleniumUtil(new ChromeTest(), true);
	   // u.get("http://10.184.56.194:18080/ControlM/");

	    

	}

}