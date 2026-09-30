package IMA.ReportesDatalake_MVP3.om;

import java.util.HashMap;
import java.util.Map;

import utils.password.PasswordUtil;

public enum Credentials {
	/**
	 * Accesos utilizados en el proyecto I24043-Automatización Reportes DataLake
	 *  Lider de automatizacion: mariad1@hexaware.com
	 * 
	 * Prerequisitos:
	 * -Tener instalado el framework ScriptXspace version 2.5
	 * 
	 * Insumos:
	 * @author 
	 * @author 
	 * @date	
	 * Fecha estimada de finalizacion de etapa de pruebas: 04/12/2024
	 */
	
	
	/**
	 * Formato:
	 *  <NOMBRE_DB> ("Usuario", "Contrasena","Host","NA"),
	 *  <NOMBRE_FTP> ("Usuario", "Contrasena","Server","Port"),
	 *  <NOMBRE_APP> ("Usuario", "Contrasena","URL","NA"),
	 *  <NOMBRE_CONTROLM> ("Usuario", "Contrasena","URL","NA"),
	 */
	//Ambiente Prod Antiguo
	/*EBS_PROD_OLD("92C4986E2465EDA27D5D04B227BE5FDE",
			"92C4986E2465EDA27D5D04B227BE5FDE",
			"A6903B09C2AC85B0D8B23F4908B7B08BE3342AEEE947C4CCBF31BCE4B9F61E04",
			"NA"),*/
	//Ambiente Dev
	/*EBS_DB_DEV ("92C4986E2465EDA27D5D04B227BE5FDE",
			"92C4986E2465EDA27D5D04B227BE5FDE",
			"36CB55BCC8ACE6628FBF34152CA70D110E1FD029485B3D07867379A5AE8DA35C",
			"NA"),*/
	//Ambiente QA
	/*EBS_DB_QA ("92C4986E2465EDA27D5D04B227BE5FDE",
			"92C4986E2465EDA27D5D04B227BE5FDE",
			"C1F3ACBB704A70FE20E39C98A070597D17B9880B8F1D7EA0E7824A38A357EEB81E20A618B85D50D99797E48A30DEA34A",
			"NA"),*/
	//desconozco el ambiente debe ser qa
	/*RMS_DB_QA ("2EB0D40715AD35FBA72415DC1CFDFE1B",
			"0A0733E127FD929E7DD488492E2C92AE",
			"35983A29CD258FC63D54738EF7E1F0BD278563D78B660BC506F9E7BD07181B23",
			"NA"),*/
	//RMS ACTUAL QA
	/*RMS_DB_QA_ACTUAL ("02BE17874064F0C3F683C86AA8FA2EDD",
			"9BB58DB9D3CE096DE4F53936E3831007",
			"3FC88EC5BD74940C96D60C15F48B2B24D84D54CCF376CCFB4532BA79B398B260",
			"NA"),*/
	//RMS DEV
	RMS_DB_BD_FCRMS ("36C7172F1A9150EED1753B8222DCD9C3",
			"36C7172F1A9150EED1753B8222DCD9C3",
			"31C63931FF04435980811CFDDB36CC2100F26A4FAD20EE82DC45BB80C28397A2FBF4DAF3052545DAB696508A31410C12",
			"NA"),
	//FCDAHPFR QA en esta se valida la tabla de carga
	/* ("02BE17874064F0C3F683C86AA8FA2EDD",
			"9BB58DB9D3CE096DE4F53936E3831007",
			"BCAEB1A68094DB32D0C35E53EF731CCF1C37E35B8113B05F44FC8F0E7876F324D354FF64DA890E6CAD8020DC64EF649D",
			"NA"),*/
	//Datalake DEV
	SF_DL_QA_AM ("DE8408DFF64F40273202C9BF2AC82A50",
			 "51B52D641C65DAAC0821E750ED710718",
			 "1BBED61D8EEC66336CD561DB9270EAC2",
		    "C695D59FA93FF4E26F3C4BC5A4278C92");
	
    private static final Map<String, Credentials> USER = new HashMap<>();
    private static final Map<String, Credentials> PASSWORD = new HashMap<>();
    private static final Map<String, Credentials> HOST = new HashMap<>();
    private static final Map<String, Credentials> PORT = new HashMap<>();
    
    static {
        for (Credentials e : values()) {
        	USER.put(e.user, e);
        	PASSWORD.put(e.password, e);
        	HOST.put(e.host, e);
        	PORT.put(e.port, e);
        }
    }

    public final String user;
    public final String password;
    public final String host;
    public final String port;

    private Credentials(String user, String password, String host, String port) {
        this.user = PasswordUtil.decryptPassword(user);
        this.password = PasswordUtil.decryptPassword(password);
        this.host = PasswordUtil.decryptPassword(host);
        this.port = PasswordUtil.decryptPassword(port);
    }

    public Credentials getUser(String user) {
        return USER.get(user);
    }
    
    public Credentials getPassword(String password) {
        return USER.get(password);
    }
    
    public Credentials getHost(String host) {
        return USER.get(host);
    }

    public Credentials getPort(String port) {
    	return USER.get(port);
    }
    
}
