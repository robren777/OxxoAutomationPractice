package WEB.inventarioEspacios.scripts.Reglas;

import static org.testng.Assert.assertTrue;

import java.util.HashMap;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import WEB.inventarioEspacios.om.MenuPrincipalIEP;
import WEB.inventarioEspacios.om.MenuPrincipalOpcionesIEP;
import WEB.inventarioEspacios.om.Reglas_IEP;
import WEB.om.PortalApps;
import modelo.BaseExecution; import WEB.util.GlobalVariables;
import utils.password.PasswordUtil;
import utils.selenium.MEdgeTest;
import utils.selenium.SeleniumUtil;
import utils.sql.SQLResult;
import utils.sql.SQLUtil;

public class ATC_FT_001_P2_Consulta_Reglas_Limpiar extends BaseExecution {

	SeleniumUtil u;
	@Test(dataProvider = "data-provider")
	
	public void ATC_FT_001_P2_Consulta_Reglas_Limpiar_test(HashMap<String, String> data) throws Exception {
		
		SQLUtil dbFCIAS = new SQLUtil(GlobalVariables.DB_HOST_FCIASQAS_PORTAL, GlobalVariables.DB_USER_FCIASQAS_PORTAL,GlobalVariables.DB_PASSWORD_FCIASQAS_PORTAL);
		
		
		/*
		 * Variables
		 ******************************************************************************************/
		String user = data.get("User");
		String password = PasswordUtil.decryptPassword(data.get("Password"));
//		String mngAlerta = "Usuario y/o Contrase�a Invalidos";
		String negocio = data.get("Negocio");
		String perfil = data.get("Perfil");
		String NombreRegla = data.get("Nombre_Regla");
		String descripcionTxt = data.get("Descripcion");
		String EstatusTxt = data.get("Estatus");
		String Year = data.get("A�o");
		String Mes = data.get("Mes");
		String Dia = data.get("Dia");
		String DiaFinal = data.get("Dia_Fin");
		
		
//		//									PASO 1
		addStep("Ingresar al portal https://fcportalqa.femcom.net:8443/AccessControl/pages/login_form.jsf con el <Usuario>"); //viene en excel
		//u = new SeleniumUtil(new ChromeTest(), true); //IETest para usar Google Chrome
		u = new SeleniumUtil(new MEdgeTest(), true);  // para usar navegador Edge
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
		addStep(" Verificar la informaci�n del usuario y que se muestre el negocio y el perfil.");
		MenuPrincipalIEP mp = new MenuPrincipalIEP(u, testCase);
		String db = "";
		switch(negocio.toUpperCase()) {
			case "BARA": 
				db = "XXINE_BA";
			break;
			
			default: db = "XXINE";
			break;
		
		}
		
		String query = "select XXAC_USERNAME from " + db + ".xxine_users_empleados_v where XXAC_USER_ID = '" + user + "'";
		SQLResult ExecuteQuery = executeQuery(dbFCIAS, query);
		String nameUser = ExecuteQuery.getData(0, "XXAC_USERNAME");
		
		boolean validacionInfo = mp.verificarInformacion(nameUser, negocio, perfil);
			
		if (validacionInfo) {
			testCase.addTextEvidenceCurrentStep("Se muestra la informacion del usuario, negocio y perfil");
		}else {
			testCase.addTextEvidenceCurrentStep("La informacion del usuario y/o negocio y/o perfil no son correctas");
		}
		
		assertTrue(validacionInfo, "No se muestra la informaci�n esperada");
		
		
		//                                 PASO 4
		addStep(" Validar que visualice los MENUS:");
		MenuPrincipalOpcionesIEP mpo = new MenuPrincipalOpcionesIEP(u, testCase);
		mp.clickMenus();
		switch(perfil.toUpperCase()) {
		
			case "ADMINISTRADOR": 
				if (negocio.toUpperCase().equals("OXXO") || negocio.toUpperCase().equals("BARA")){
					mpo.menuAdministrador();
				} else if (negocio.toUpperCase().equals("MARCA")) {
					mpo.menuAdministradorM();
				}
				
			break;
			
			case "PLAZA": mpo.menuPlaza();
			break;
			
			case "ESTAFETA": mpo.menuEstafeta();
			break;
			
		}
                      
		//                                 PASO 5
		addStep("  Validar que se muestre en el encabezado de la pantalla el Negocio, periodo y estatus del periodo.");
		boolean verEncabezado = mp.verificarEncabezado(negocio);
		
		if(verEncabezado)
			testCase.addTextEvidenceCurrentStep("La informaci�n del negocio, periodo y estatus del periodo es correcta");
		else
			testCase.addTextEvidenceCurrentStep("La informaci�n del negocio, periodo y estatus del periodo es incorrecta");
		
		assertTrue(verEncabezado, "No se muestra la informaci�n esperada");
		
		//                                 PASO 6
		addStep("Seleccionar menu Administrador : Reglas");
		Reglas_IEP dc = new Reglas_IEP(u, testCase);
		mp.clickAdministracion();
		mp.clickReglas();
		
		//                                 PASO 7
		addStep("\"Al ingresar a la pantalla \"Consulta de Reglas\" la tabla muestra todas las reglas ordenadas de forma descendente por el campo Id Regla, la informaci�n es solo lectura: \r\n"
				+ "Id Regla: Muestra el ID que le asign� autom�ticamente el sistema\r\n"
				+ "Nombre: Muestra el nombre que se le asign� a la regla\r\n"
				+ "Descripci�n: Muestra la descripci�n que se le asign� a la regla");
		dc.clickIdConsultaReglas();
		

		//                                 PASO 8
		addStep("Realizar la busqueda con los filtros y dar clic en buscar.");		
		Boolean band = true;
		dc.clickIdReglatxt(NombreRegla,band);
		dc.clickDescripTxt(descripcionTxt);
		dc.clickStatus(EstatusTxt);
		dc.clickFechaIni(Year,Mes,Dia);
		dc.clickFechaFin(Year,Mes,DiaFinal);
		dc.clickBotonBuscar();
		
		//                                 PASO 9
		addStep("El usuario administrador podr� filtrar la informaci�n de la tabla por:\r\n"
				+ "Nombre: Campo de texto alfanum�rico que filtra por el nombre de la regla\r\n"
				+ "Descripci�n: Campo de texto alfanum�rico que filtra por la descripci�n de la regla\r\n"
				+ "Estatus: Muestra dos valores, Activo e Inactivo. por default el sistema tiene seleccionada la opci�n Activo\r\n"
				+ "Fecha Creaci�n: Este filtro debe permitir seleccionar un rango de fechas, mostrando un calendario para su selecci�n\r\n"
				+ "Al finalizar de capturar uno o varios filtros, el usuario deber� dar clic en el bot�n Buscar, el sistema realiza la b�squeda de acuerdo con los valores capturados y la tabla se actualiza con los valores que coincidan con dicha b�squeda. \r\n"
				+ "Tambi�n se muestra el bot�n Limpiar, al dar clic sobre este bot�n el sistema borra los valores seleccionados/capturados en todos los filtros.\r\n"
				+ "");
		dc.clickIdConsultaReglas();

		//                                 PASO 10
		addStep("Dar clic en el boton limpiar");
		dc.clickBotonLimpiar();

		//                                 PASO 11
		addStep("\"El sistema resetea los valores de los filtros y en los campos de resultado de busqueda aparecen todas las reglas existentes.");
		dc.clickSeLimpian();
        
		//                                 PASO 12
		addStep("\"Realizar una nueva busqueda con los filtros.");
		band = true;
		dc.clickIdReglatxt(NombreRegla,band);
		dc.clickDescripTxt(descripcionTxt);
		dc.clickStatus(EstatusTxt);
		dc.clickFechaIni(Year,Mes,Dia);
		dc.clickFechaFin(Year,Mes,DiaFinal);
		dc.clickBotonBuscar();
        
		//                                 PASO 13
		addStep("Validar que la nueva busqueda concuerde con los filtros seleccionados.");
		dc.clickIdConsultaReglas();

	}
 
	
	@Override
	public void beforeTest() {
	}

	@Override
	public String setTestDescription() {
		return "ATC_FT_001_P2_Consulta_Reglas_Limpiar";
	}

	@Override
	public String setTestDesigner() {
		return "AutomationQA";
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_001_P2_Consulta_Reglas_Limpiar_test";
	}

	@Override
	public String setTestInstanceID() {
		return null;
	}

	@Override
	public String setPrerequisites() {
		return null;
	}
	
	@AfterMethod
	public void afterMethod() {
		u.getDriver().quit();
	}

}
