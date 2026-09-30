package RMS.util;

import utils.password.PasswordUtil;

public class GlobalVariables {

	// wmlostatusRunPR1FCWMLQA
	public static final String DB_USER_FCWMLQA = "QAVIEW";
	public static final String DB_PASSWORD_FCWMLQA = PasswordUtil.decryptPassword("A5544A354006EE384994CACA76D6289B");
	public static final String DB_HOST_FCWMLQA = "10.184.48.217:1535/FCWMLQA.FEMCOM.NET";

	// RMS 
	public static final String DB_USER_FCRMSQA = "6004010";
	public static final String DB_PASSWORD_FCRMSQA = PasswordUtil.decryptPassword("2B3BFACC41B993CCD206CE41B3141C7B7AB3A50C19B6F1C7DFCDCAE9E6F1ADF7");
	public static final String DB_HOST_FCRMSQA = "10.184.37.12:1521/FCRMSQA.FEMCOM.NET";

	// RMS COL
	public static final String DB_USER_BDRMSCQA = "QAVIEW";
	public static final String DB_PASSWORD_BDRMSCQA = PasswordUtil.decryptPassword("A5544A354006EE384994CACA76D6289B");
	public static final String DB_HOST_BDRMSCQA = "10.185.48.24:1547/BDRMSCQA.FEMCOM.NET";

//	credenciales ControlMz
	public static final String CtrlM_User = "68915";
	public static final String CtrlM_Pss = PasswordUtil.decryptPassword("E209D7CFB881F56DC7F563F4DEBCA615");
	public static final String CtrlM_URL = "http://10.184.56.194:18080/ControlM/";
	
//	public static final String CtrlM_User = "gconflo";
//	public static final String CtrlM_Pss = "AA8D0C7C58EDB654458BF76A7977E7EF";
//	public static final String CtrlM_URL = "http://10.184.56.194:18080/ControlM/";
	
//	varables globales de RMS Colombia Conecta	

	public static final String DB_HOST_BDPRMSQ = "";
	public static final String DB_USER_BDPRMSQ = "";
	public static final String DB_PASSWORD_BDPRMSQ = "";

//	RMS Per�
	public static final String DB_USER_BDRMSPERQA = "RMS100";
	public static final String DB_PASSWORD_BDRMSPERQA = PasswordUtil
			.decryptPassword("5F83B010547B5DA3D27275203D3B31DA");
	public static final String DB_HOST_BDRMSPERQA = "10.186.3.53:1527/BDPRMSQ.FEMCOM.NET";

	// RMS Chile
	public static final String DB_USER_BDCHRMSCQA = "RMS100";
	public static final String DB_PASSWORD_BDCHRMSCQA = PasswordUtil
			.decryptPassword("1CD10F979189DA0768DA27AFB5970122");
	public static final String DB_HOST_BDCHRMSCQA = "10.186.3.56:1541/BDCHRMSQ.FEMCOM.NET";

	// credenciales RMS
	public static final String RMS_User = "RMS100";
	public static final String RMS_Pss = PasswordUtil.decryptPassword("99F7A0CB3DEA09AA207C2A33BC5B99C6");
	public static final String RMS_URL = "http://10.184.16.184:8029/rmsQA.html";

	// credenciales RMS
	public static final String RMS_User_CHILE = "RMS100";
	public static final String RMS_Pss_CHILE = PasswordUtil.decryptPassword("1CD10F979189DA0768DA27AFB5970122");
	public static final String RMS_URL_CHILE = "http://10.186.3.54:8032/RMSCHLQA.html";

	// credenciales RMS Col
	public static final String RMS_UserCol = "RMS100";
	public static final String RMS_PssCol = PasswordUtil.decryptPassword("E294EC6DCC58CB4368980D8CCDD201BD");
	public static final String RMS_URLCol = "http://10.185.48.23:8035";

	public static final String RMS_User2 = "6004010";
	public static final String RMS_Pss2 = PasswordUtil.decryptPassword("0B94EA65458E93BEF1EF9CBE1DB28A5C");
	
	// RDM 01CU
	public static final String DB_USER_RDM = "RDMVIEW";
	public static final String DB_PASSWORD_RDM = PasswordUtil.decryptPassword("8F338A480F532676F0F11974F87B176A");
	public static final String DB_HOST_RDM = "10.180.56.86:1535/FCRD01CU.FEMCOM.NET";
	
	// RDM RDM63CU
	public static final String DB_USER_RDM_63 = "rdm100";
	public static final String DB_PASSWORD_RDM_63 = PasswordUtil.decryptPassword("35B0A9998A32FE5AE0581E6F785E2F80");
	public static final String DB_HOST_RDM_63 = "10.186.13.15:1521/FCRD63CU.FEMCOM.NET";

	// RDM Veracruz
	public static final String DB_USER_RDM_Veracruz = "nreader";
	public static final String DB_PASSWORD_RDM_Veracruz = PasswordUtil.decryptPassword("9BB58DB9D3CE096DE4F53936E3831007");
	public static final String DB_HOST_RDM_Veracruz = "10.184.37.20:1522/FCRDMST3";
	
	// FCRD01CU
	public static final String DB_USER_RDMVIEW = "RDMVIEW";
	public static final String DB_PASSWORD_RDMVIEW= PasswordUtil.decryptPassword("8F338A480F532676F0F11974F87B176A");
	public static final String DB_HOST_RDMVIEW = "10.180.56.86:1535/FCRD01CU";
	
	// FCRD01CU
	public static final String DB_USER_RDMVIEW2 = "RDMVIEW";
	public static final String DB_PASSWORD_RDMVIEW2=PasswordUtil.decryptPassword("8F338A480F532676F0F11974F87B176A");
	public static final String DB_HOST_RDMVIEW2 = "fcrdmq01.femcom.net:1535/FCRD01CU.FEMCOM.NET";
	
	// RMS 
	public static final String DB_USER_FCWM6QA = "QAVIEW";
	public static final String DB_PASSWORD_FCWM6QA = PasswordUtil.decryptPassword("A5544A354006EE384994CACA76D6289B");
	public static final String DB_HOST_FCWM6QA = "10.184.48.216:1535/fcwm6QA.femcom.net";

}
