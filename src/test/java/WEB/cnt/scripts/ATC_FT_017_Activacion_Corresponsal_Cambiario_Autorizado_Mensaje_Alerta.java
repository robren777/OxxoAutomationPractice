package WEB.cnt.scripts;

import java.util.HashMap;

import org.testng.annotations.Test;

import WEB.cnt.om.Login;
import WEB.cnt.om.Permisos_y_Contratos;
import WEB.util.GlobalVariables;
import modelo.BaseExecution;
import utils.password.PasswordUtil;
import utils.selenium.ChromeTest;
import utils.selenium.SeleniumUtil;
import utils.sql.SQLUtil;

public class ATC_FT_017_Activacion_Corresponsal_Cambiario_Autorizado_Mensaje_Alerta extends BaseExecution{

	SeleniumUtil u;

	@Test(dataProvider = "data-provider")

	public void ATC_FT_017_Activacion_Corresponsal_Cambiario_Autorizado_Mensaje_Alerta_test(HashMap<String, String> data) throws Exception {
		/*
		 * Utiler�as
		 ******************************************************************************************/
		SQLUtil dbFCIAS = new SQLUtil(GlobalVariables.DB_HOST_FCIASQAS_PORTAL, GlobalVariables.DB_USER_FCIASQAS_PORTAL,
		GlobalVariables.DB_PASSWORD_FCIASQAS_PORTAL);

		/*
		 * Variables
		 ******************************************************************************************/
		String user = data.get("User");
		String password = PasswordUtil.decryptPassword(data.get("Password"));
		String PlazaE = data.get("PlazaE");
		String Distrito = data.get("Distrito");
		String Tienda = data.get("Tienda");
		String URL =data.get("Url");

//		String mngAlerta = "Usuario y/o Contrase�a Invalidos";
//		String negocio = data.get("Negocio");
//		String perfil = data.get("Perfil");
//		String plaza = data.get("Plaza");
//		String direcc = data.get("Nombre_Dir");
//		String cp = data.get("C�digo_postal");
//		String calle = data.get("Calle");
//		String numero = data.get("N�mero");
//		String entreCalle = data.get("Entre_Calle");
//		String yEntreCalle = data.get("y_entre_Calle");
//		String nomContacto = data.get("Nombre_Contacto");
//		String telContacto = data.get("Tel�fono_Contacto");
//		String comentario = data.get("Comentario");

		String query = "select fecha,nombre_plaza,nombre_envio,xxac_user_id from XXINE.XXINE_MOVIMIENTOS_SISTEMA where nombre_plaza = 'Tijuana' and xxac_user_id = 'CMOXXOHEXAWARE30'";

		// ------------------------------------PASO 1

		addStep("Ingresar al portal "+URL+" con el <Usuario>"+user+""); // viene en
																											// excel
		u = new SeleniumUtil(new ChromeTest(), true); // IETest para usar internet explorer
		Login pa = new Login(u, testCase); // crea objeto de locator
		// PortalApps pa = new PortalApps(u, testCase); // crea objeto de locator
		u.get(data.get("Url")); // toma el valor Url del data provider
		u.hardWait(3); // tiempo en segundos

//		pa.proceedToPortal();    // es para lo de la pagina no segura
		pa.logginInventarioEspacios(user, password);
//		pa.cicloLogin(user, password);
//		
//		
//		
//		//------------------------------------PASO 2
		addStep("Seleccionar la Plaza y dar clic en el bot�n Continuar.");
		pa.clickSeleccionarPlaza(PlazaE);
//		//------------------------------------PASO 3
		addStep("Seleccionar el Distrito y Tienda; a continuaci�n dar clic en el bot�n Continuar");
		pa.clickSeleccionarDistritoTienda(Distrito, Tienda);
		//pa.ScreenVentanaEmergente();
//		mp.verificarInformacion(user, negocio, perfil);
		//                                 PASO 2
		addStep(" Dar clic en el link Permisos y Contratos");
		Permisos_y_Contratos pc = new Permisos_y_Contratos(u, testCase);
		pc.PermCon();
		//pc.clickRegresaventana();

		//                                 PASO 3
		addStep(" Validar que los campos Referencia y D�gito Verificador no se encuentren capturados.");
		pc.validarCodVerificadorYRefPesos();
//		mp.verificarEncabezado("Administrador");
		
		//                                 PASO 4
		addStep("Activar el Checkbox Corresponsal Cambiario.");
		
		
		//								   PASO 5
		addStep("Capturar el campo Referencia a excepci�n del campo D�gito Verificador. ");

		//	  							   PASO 6	
		addStep("Activar el Checkbox Corresponsal Cambiario.");

		//		                           PASO 7
		addStep("Capturar el campo D�gito Verificador a excepci�n del campo Referencia");
								

		//		                           PASO 8
		addStep("Activar el Checkbox Corresponsal Cambiario.");

	}

	@Override
	public void beforeTest() {
	}

	@Override
	public String setTestDescription() {
		return "ATC_FT_017_Activaci�n_Corresponsal_Cambiario_Autorizado_Mensaje_Alerta_Test";
	}

	@Override
	public String setTestDesigner() {
		return "AutomationQA";
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_017_Activacion_Corresponsal_Cambiario_Autorizado_Mensaje_Alerta_test�n_Corresponsal_Cambiario_Autorizado_Mensaje_Alerta_Test";
	}

	@Override
	public String setTestInstanceID() {
		return null;
	}

	@Override
	public String setPrerequisites() {
		return null;
	}

//	@AfterMethod
//	public void afterMethod() {
//		u.getDriver().quit();
//	}

}
