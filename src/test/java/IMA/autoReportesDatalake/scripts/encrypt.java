package IMA.autoReportesDatalake.scripts;


import utils.password.PasswordUtil;

public class encrypt {

	
	public static void main(String[] args) {
		
//		System.out.println(System.getProperty("user.dir"));
//		
		PasswordUtil util = new PasswordUtil();
//		
//		//"10.184.40.134:1521/OIWMQA.FEMCOM.NET"
//		
//		System.out.println(util.encryptPass("7dlq52qxkj7m6i3w7mb5l3a2xrd57i6gvteorxbwiw4wktbczfcq"));
//		System.out.println("Contra/user");
		System.out.println("hola 2");
		System.out.println(util.encryptPass("AOrkxxLIjmty4EM9PpeQMQOlK1nlgyY2q9axD04vBNDl2k3O6OzfJQQJ99BCACAAAAA5sPCvAAASAZDO3qOo"));
		System.out.println(util.decryptPass("2EB0D40715AD35FBA72415DC1CFDFE1B"));
		System.out.println(util.decryptPass("A5544A354006EE384994CACA76D6289B"));
		System.out.println(util.decryptPass("59AD0A872940379A7D127824B32BD67D48E8B4BA3C0BCB77E6554AFAC104D3185AA7A9EC8E9389CF83D603AC65509079920A3E22D35F979BF33088F796801847"));
//		System.out.println("Host");
//		System.out.println(util.encryptPass("10.184.56.134:1535/FCEBPR"));
//		System.out.println("nueva contra ftp");
		
//		
//		System.out.println(Credentials.EBS_DB.user);
//		System.out.println(Credentials.EBS_DB.password);
//		System.out.println(Credentials.EBS_DB.host);
			
	}
}