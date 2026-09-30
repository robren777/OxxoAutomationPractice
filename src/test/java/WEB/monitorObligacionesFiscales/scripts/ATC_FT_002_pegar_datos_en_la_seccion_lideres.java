package WEB.monitorObligacionesFiscales.scripts;

import java.util.HashMap;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import WEB.monitorObligacionesFiscales.om.Lideres;
import WEB.monitorObligacionesFiscales.om.MenuPrincipal_MonitorObligaciones;
import WEB.om.PortalApps;
import modelo.BaseExecution;
import utils.password.PasswordUtil;
import utils.selenium.IETest;
import utils.selenium.SeleniumUtil;

public class ATC_FT_002_pegar_datos_en_la_seccion_lideres extends BaseExecution{
	SeleniumUtil u;
	@Test(dataProvider = "data-provider")
	public void ATC_FT_002_pegar_datos_en_la_seccion_lideres_test(HashMap<String, String> data) {
		/*
		 * Variables
		 ******************************************************************************************/
		String user = data.get("User");
		String password = PasswordUtil.decryptPassword(data.get("Password"));//String password = data.get("Password");//
		String URL = data.get("Url");
		String nombre = data.get("Nombre");
		String AP = data.get("Apellido_Paterno");
		String AM = data.get("Apellido_Materno");
		String RFC = data.get("RFC");
		String Plaza = data.get("Plaza");
		String CURP = data.get("CURP");
		String FolioFactura = data.get("F_Fiscal");
		String Estado = data.get("Estado");
		String Ciudad = data.get("Ciudad");
		String Colonia = data.get("Colonia");
		String Calle = data.get("Calle");
		String No_Exterior = data.get("No_Exterior");
		String No_Interior = data.get("No_Interior");
		String Entre_Calle = data.get("Entre_Calle");
		String Y_ = data.get("Y");
		String CIEC = data.get("CIEC");
		String Contrasena = data.get("Contrasena");
		String R_Patronal = data.get("R_Patronal");
		String Usuario = data.get("Usuario");
		String Contrasena_IMSS = data.get("Contrasena_IMSS");
		
		u = new SeleniumUtil(new IETest(), true);
		PortalApps pa = new PortalApps(u, testCase);
		MenuPrincipal_MonitorObligaciones mo = new MenuPrincipal_MonitorObligaciones(u,testCase);
		Lideres moL = new Lideres(u,testCase);
		// Paso 1
		addStep("Ingresar al portal de aplicaciones");

		u.get(URL);
		u.hardWait(3);
		pa.proceedToPortalIEsecureSite();

		// Paso 2
		addStep("El usuario ingresa usuario y contraseña y da clic en el botón <Iniciar Sesión>.");
		pa.cicloLogin(user, password);
		// Paso 3
		addStep("El usuario da clic en opción de Monitor de Obligaciones Fiscales.");
		pa.clickPortalMonitorObligacionesFiscales(1);
		//Paso 4
		mo.clickLideres();
		// Paso 5
		addStep("Llenado de datos para seleccion de Lider.");
		moL.ingresarDatosLider(nombre, AP, AM, RFC, Plaza);
		moL.selectLideresTabla(1);
		u.hardWait(3);
		// Paso 6
		addStep("Llenado de Datos generales del Lider.");
		moL.llenarDatosGenerales(CURP, FolioFactura);
		// Paso 7
		addStep("Llenado de Registro SAT del Lider.");
		moL.llenarRegistroSat(CIEC, Contrasena);
		// Paso 8
		addStep("Llenado de Registro IMSS del Lider.");
		moL.llenarRegistroIMSS(R_Patronal, Usuario, Contrasena_IMSS);
		// Paso 9
		addStep("Llenado de Direccion Fiscal del Lider.");
		moL.llenarDireccionFiscal(Estado, Ciudad, Colonia, Calle, No_Exterior, No_Interior, Entre_Calle, Y_);
	}

	@Override
	public void beforeTest() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String setPrerequisites() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String setTestDescription() {
		// TODO Auto-generated method stub
		return "";
	}

	@Override
	public String setTestDesigner() {
		// TODO Auto-generated method stub
		return "AutomationQA";
	}

	@Override
	public String setTestFullName() {
		// TODO Auto-generated method stub
		return "ATC_FT_004_Pegar_datos_en_la_seccion_Tarifas";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}
	@AfterMethod
	public void afterMethod() {
		//u.getDriver().quit();
	}

}

