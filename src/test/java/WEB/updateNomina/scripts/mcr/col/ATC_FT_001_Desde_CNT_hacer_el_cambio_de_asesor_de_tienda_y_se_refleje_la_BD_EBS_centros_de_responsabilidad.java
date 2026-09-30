package WEB.updateNomina.scripts.mcr.col;

import java.util.HashMap;

import org.json.JSONObject;
import org.testng.annotations.Test;

import WEB.cnt.om.Datos_de_Operacion;
import WEB.updateNomina.om.UpdateNomina;
import modelo.BaseExecution;
import utils.controlm.ControlM;
import utils.controlm.pageObject.Control_mInicio;
import utils.password.PasswordUtil;
import utils.selenium.ChromeTest;
import utils.selenium.SeleniumUtil;
import utils.sql.SQLResult;


public class ATC_FT_001_Desde_CNT_hacer_el_cambio_de_asesor_de_tienda_y_se_refleje_la_BD_EBS_centros_de_responsabilidad extends BaseExecution {

	 @Test(dataProvider = "data-provider")
	    public void ATC_FT_001_Desde_CNT_hacer_el_cambio_de_asesor_de_tienda_y_se_refleje_la_BD_EBS_centros_de_responsabilidad_test(HashMap<String, String> data) throws Exception {

		 //VARIABLES
		 String serverAVEBQA = data.get("serverAVEBQA");
		 String usernameAVEBQA = data.get("usernameAVEBQA");
		 String passwordAVEBQA = PasswordUtil.decryptPassword(data.get("passwordAVEBQA"));

		 String usernameCNT = data.get("usernameCNT");
		 String passwordCNT = PasswordUtil.decryptPassword(data.get("passwordCNT"));

		 String controlMUser = data.get("controlMUser");
		 String controlMPwd = PasswordUtil.decryptPassword(data.get("controlMPwd"));

		 JSONObject obj = new JSONObject(data.get("job"));

		 //QUERIES
		 //String query0 = "SELECT * FROM apps.xxfc_centros_responsabilidad WHERE retek_cr = '33089'";
		 String query0 = "SELECT ORACLE_CR_SUPERIOR,RETEK_CR,ORACLE_CR,RETEK_ASESOR,LAST_UPDATED_DATE,DESCRIPCION,RETEK_ASESOR_NOMBRE,LAST_UPDATE_DATE FROM apps.xxfc_centros_responsabilidad WHERE retek_cr = '33089'";

		 //UTILERIAS WEB
		 SeleniumUtil u = new SeleniumUtil(new ChromeTest(), true);
		 Control_mInicio CM = new Control_mInicio(u, controlMUser, controlMPwd); //esto es para el inicio de sesion
		 ControlM controlM = new ControlM(u, testCase, obj); //esto es para buscar y ejecutar el job designado en el data provider
		 //Login login = new Login (u, testCase);
		 WEB.cnt.om.Login pa = new WEB.cnt.om.Login(u, testCase);
		 UpdateNomina updNom = new UpdateNomina(u,testCase);

		 Datos_de_Operacion datosDeOperacion = new Datos_de_Operacion(u,testCase);

		 //UTILERIAS SQL
		 utils.sql.SQLUtil ebsAVEBQA = new utils.sql.SQLUtil(serverAVEBQA, usernameAVEBQA, passwordAVEBQA);


		 // PASO 1
		 addStep("\"ingresar a la BD EBS \n" +
				 "usuario:APPSVIEW\n" +
				 "host: oxfdbfinq00.femcom.net\n" +
				 "puerto: 1535\n" +
				 "servicio: AVEBQA\"\n");
		 System.out.println(serverAVEBQA);
		 System.out.println(usernameAVEBQA);
		 System.out.println(passwordAVEBQA);
		 System.out.println("Paso 1: Conexi�n con BD FCWM6QA exitosa -> "+ ebsAVEBQA.toString());

		 // PASO 2
		 addStep("Validar el asesor que tiene asignada la tienda Select  * from apps.xxfc_centros_responsabilidad;where retek_cr = and retek_asesor_nombre ='';");
		 SQLResult ejecucionQuery = ebsAVEBQA.executeQuery(query0);
		 System.out.println(ejecucionQuery);
		 testCase.addQueryEvidenceCurrentStep(ejecucionQuery);

		 // PASO 3
		 addStep("Ingresar al portal http://fcowlq07.femcom.net:8000/tiendas/html/login.html con el <Usuario> pruebas.web");
		 u.get("https://opvcntnp1.femcom.net:8443/colombia/tiendas/html/login.html");
		 System.out.println(usernameCNT);
		 System.out.println(passwordCNT);
		 pa.logginInventarioEspacios(usernameCNT, passwordCNT);

		 // PASO 4
		 addStep("Elegir tienda para realizar el cambio de asesor");
		 pa.clickSeleccionarPlaza("10CEN - Region Centro");

		 // PASO 5
		 addStep("Ingresar a datos de operaci�n y hacer el cambio de asesor y dar clic en guardar");
		 pa.clickSeleccionarTienda("500EZ - Multicentro BOG (OPERATIVA)");
		 datosDeOperacion.DatoOp(); //para ingresar a la seccion datos de operacion
		 //String Asesor = "Empleado Especial";
		 //datosDeOperacion.capturaDatosOp(Asesor);
		 datosDeOperacion.editarAsesor();
		 datosDeOperacion.clickBotonActualizar();
		 
		 

		 // PASO 6
		 addStep("Ingresar a Control -M con usuario y contrase�a valido http://10.184.56.194:18080/ControlM/");
		 // PASO 7
		 addStep("ejecutar la interfaz CO1");
		 //updNom.logInControlMyEjecutarJob(CM, controlM);

		 //PASO 8
		 addStep("\"ingresar a la BD EBS \n" +
				 "usuario:APPSVIEW\n" +
				 "host: oxfdbfinq00.femcom.net\n" +
				 "puerto: 1535\n" +
				 "servicio: AVEBQA\"\n");
		 // PASO 9
		 addStep("Validar el cambio de asesor que tiene asignada la tienda Select  * from apps.xxfc_centros_responsabilidad;where retek_cr = and retek_asesor_nombre ='';");
		 SQLResult ejecucionQuery2 = ebsAVEBQA.executeQuery(query0);
		 testCase.addQueryEvidenceCurrentStep(ejecucionQuery2);

	    }

	    @Override
	    public String setTestFullName() {
	        return "ATC_FT_009_Envio_de_pago de_nomina_y_generacion_de_polizas_a_traves_de_la_interface_OS1_MX_F097";
	    }

	    @Override
	    public String setTestDescription() {
	        return "Ejecucion de la interfaz   FEMSA_OS1_MX de avante para enviar informacion de polizas de SAP_INBOUND_DOCS hacia SAP_RPY_DETL y contabilizaci�n de p�lizas en GL_INTERFACE";
	    }

	    @Override
	    public String setTestDesigner() {
	        return "Mario Alberto De Leon Rodriguez";
	    }

	    @Override
	    public String setTestInstanceID() {
	        return null;
	    }

	    @Override
	    public String setPrerequisites() {
	        return "Acceso a EBS, CNT, Control M";
	    }

	    @Override
	    public void beforeTest() {

	    }


}
