package WEB.inventarioEspacios.scripts.SubPlazas;

import java.util.HashMap;

import org.testng.annotations.Test;

import WEB.inventarioEspacios.om.MenuPrincipalIEP;
import WEB.inventarioEspacios.om.SubPlaza_IEP;
import WEB.om.PortalApps;
import modelo.BaseExecution; import WEB.util.GlobalVariables;
import utils.password.PasswordUtil;
import utils.selenium.ChromeTest;
import utils.selenium.SeleniumUtil;

public class P2_SUBPLAZAS_Admdor_OXXO_Busqueda_Limpiar extends BaseExecution {

	SeleniumUtil u;
	@Test(dataProvider = "data-provider")
	
	public void ATC_FT_058_P2_SUBPLAZAS_Admdor_OXXO_Busqueda_Limpiar(HashMap<String, String> data) throws Exception {
		/*
		 * Variables
		 ******************************************************************************************/
		String user = data.get("User");
		String password = PasswordUtil.decryptPassword(data.get("Password"));
//		String mngAlerta = "Usuario y/o Contrase�a Invalidos";
		String negocio = data.get("Negocio");
		String perfil = data.get("Perfil");
		String subplaza = data.get("SubPlaza");
	
		
		
		
//		//									PASO 1
		addStep("Ingresar al portal https://fcportalqa.femcom.net:8443/AccessControl/pages/login_form.jsf con el <Usuario>"); //viene en excel
		u = new SeleniumUtil(new ChromeTest(), true); //IETest para usar internet explorer
		PortalApps pa = new PortalApps(u, testCase);  // crea objeto de locator
		u.get(data.get("Url"));    //toma el valor Url del data provider
		u.hardWait(3);           // tiempo en segundos
		
		pa.proceedToPortal();    // es para lo de la pagina no segura
		//pa.logginInventarioEspacios(user, password);
		pa.cicloLogin(user, password);
		
		//		                            PASO 2
		addStep("Seleccionar Inventario de Espacios");
		pa.clickPortalInventarioEspacios();
		//                                  PASO 3
		addStep("Verificar la informaci�n del usuario y que se muestre el negocio y el perfil.");
		MenuPrincipalIEP mp = new MenuPrincipalIEP(u, testCase);
		mp.verificarInformacion(user, negocio, perfil);
		//                                 PASO 4
		addStep(" Validar que visualice los MENUS:");    //  PENDIENTE
		                      
		//                                 PASO 5
		addStep("  Validar que se muestre en el encabezado de la pantalla el Negocio, periodo y estatus del periodo.");
		mp.verificarEncabezado("Administrador");
		
		//                                 PASO 6
		addStep("Seleccionar menu Administrador : SubPlazas");
		mp.clickAdministracion();
		SubPlaza_IEP sp = new SubPlaza_IEP(u, testCase);
		sp.clickSubPlaza();
		
		//                                 PASO 7
		addStep("\"Se muestra la pantalla con los filtros:\r\n"
				+ "Nombre Subplaza\r\n"
				+ "y los botones de \"\"Nuevo\"\",\"\"Limpiar\"\" y \"\"Buscar\"\"\"\r\n"
				+ "");		
		sp.clickCampos();
		

		//                                 PASO 8
		addStep("Ingresar filtros y dar click en Buscar");		
		sp.clickIngresaFiltroBuscar(subplaza);
		sp.clickBtnBuscar();
		
		//                                 PASO 9
		addStep("\"Se muestran los resultados en pantalla con los campos:\n"
				+ "ID \n"
				+ "NOMBRE \n"
				+ "CLAVE PLAZA \n"
				+ "NOMBRE PLAZA \n"
				+ "BOTON \"\"EDITAR");
		sp.clickResPasoNueve();

		//                                 PASO 10
		addStep("Dar clic en el boton limpiar");
		Boolean cont = false;
		sp.clickBtnLimpiar(cont);

		//                                 PASO 11
		addStep("Se limpia la busqueda y todos los filtros.");
		sp.clickLimpiaFiltros();
        
		//                                 PASO 12
		addStep("\"Se realiza una nueva busqueda con los filtros y dar click en \"Buscar\"");
		subplaza = data.get("SubPlazaDos");
		sp.clickIngresaFiltroBuscar(subplaza);
		sp.clickBtnBuscar();
		
        
		//                                 PASO 13
		addStep("\"Se muestran los nuevos resultados en pantalla con los campos :\r\n"
				+ "ID\r\n"
				+ "NOMBRE\r\n"
				+ "CLAVE PLAZA\r\n"
				+ "NOMBRE PLAZA\r\n"
				+ "BOTON \"\"EDITAR\"\"\"\r\n"
				+ "");
		sp.clickResPasoNueve();

	}
 
	
	
	@Override
	public void beforeTest() {
	}

	@Override
	public String setTestDescription() {
		return "Construido.  Validar que se muestre  la alerta de \"Usuario y/o contrase�a invalidos\" y no permite el ingreso al usuario";
	}

	@Override
	public String setTestDesigner() {
		return "AutomationQA";
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_058_P2_SUBPLAZAS_Admdor_OXXO_Busqueda_Limpiar";
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
