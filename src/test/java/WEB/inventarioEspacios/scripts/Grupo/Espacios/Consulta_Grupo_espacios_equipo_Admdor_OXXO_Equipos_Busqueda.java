package WEB.inventarioEspacios.scripts.Grupo.Espacios;

import java.util.HashMap;

import org.testng.annotations.Test;

import WEB.inventarioEspacios.om.ConMov_Eliminacion_Grupo_Espacios;
import WEB.inventarioEspacios.om.MenuPrincipalIEP;
import WEB.inventarioEspacios.om.MenuPrincipalOpcionesIEP;
import WEB.om.PortalApps;
import modelo.BaseExecution; import WEB.util.GlobalVariables;
import utils.password.PasswordUtil;
import utils.selenium.MEdgeTest;
import utils.selenium.SeleniumUtil;

public class Consulta_Grupo_espacios_equipo_Admdor_OXXO_Equipos_Busqueda extends BaseExecution{

	SeleniumUtil u;
	@Test(dataProvider = "data-provider")
	
	public void ATC_FT_457_Consulta_Grupo_espacios_equipo_Admdor_OXXO_Equipos_Busqueda_Test(HashMap<String, String> data) throws Exception {
		/*
		 * Utiler�as
		 ******************************************************************************************/
//		SQLUtil dbFCIAS = new SQLUtil(GlobalVariables.DB_HOST_FCIASQAS_PORTAL, GlobalVariables.DB_USER_FCIASQAS_PORTAL,GlobalVariables.DB_PASSWORD_FCIASQAS_PORTAL);
		
		
		/*
		 * Variables
		 ******************************************************************************************/
		String user = data.get("User");
		String password = PasswordUtil.decryptPassword(data.get("Password"));
//		String mngAlerta = "Usuario y/o Contrase�a Invalidos";
		String negocio = data.get("Negocio");
		String perfil = data.get("Perfil");
		String nombreGrupo = data.get("Nombre_Grupo");
		String comentario = data.get("Comentario");
		String Aplicar = data.get("Aplicar");

		
//		String consulta = "select GRUPO_ESPACIOS_ID,NOMBRE_GRUPO_ESPACIOS,MENSAJE_ERROR,LAST_UPDATE_DATE,APLICAR_VALIDACION from XXINE_BA.xxine_GRUPOS_ESPACIOS WHERE NOMBRE_GRUPO_ESPACIOS = '%s'";
//		String query = String.format(consulta, nombreGrupo);
		//String consultaDos = String.format(consulta, valor);
		//String query = String.format(consultaDos, fecha);	//Concatena valor String a la consulta
		
		//									PASO 1
		addStep("Ingresar al portal https://fcportalqa.femcom.net:8443/AccessControl/pages/login_form.jsf con el <Usuario>"); //viene en excel
		//u = new SeleniumUtil(new ChromeTest(), true); //IETest para usar internet explorer
		SeleniumUtil u = new SeleniumUtil(new MEdgeTest(), true);  // para usar navegador Edge
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
		addStep(" Validar que visualice los MENUS:");
		MenuPrincipalOpcionesIEP mpo = new MenuPrincipalOpcionesIEP(u, testCase);
//		mp.clickMenus();
//		switch(perfil.toUpperCase()) {
//		
//			case "ADMINISTRADOR": 
//				if (negocio.toUpperCase().equals("OXXO") || negocio.toUpperCase().equals("BARA")){
//					mpo.menuAdministrador();
//				} else if (negocio.toUpperCase().equals("MARCA")) {
//					mpo.menuAdministradorM();
//				}
//				
//			break;
//			
//			case "PLAZA": mpo.menuPlaza();
//			break;
//			
//			case "ESTAFETA": mpo.menuEstafeta();
//			break;
//			
//		}

		//                                 PASO 5
		addStep("Validar que se muestre en el encabezado de la pantalla el Negocio, periodo y estatus del periodo.");
		mp.verificarEncabezado("Administrador");
		
		//                                 PASO 6
		addStep("Seleccionar menu Espacios : Consulta de Grupo de espacios/equipos");
		ConMov_Eliminacion_Grupo_Espacios eg = new ConMov_Eliminacion_Grupo_Espacios(u, testCase);
		eg.clickGpoEspaciosEquipos();
		
		//      						   PASO 7
		addStep("\"Los filtros que debe mostrar para poder facilitar la b�squeda son:\r\n"
				+ "1. Nombre de Grupo de Espacio: debe mostrar los grupos de espacio que vayan coincidiendo conforme se escriba en el campo.\r\n"
				+ "La consulta debe mostrar las siguientes columnas:\r\n"
				+ "1. Id Grupo de Espacios\r\n"
				+ "2. Nombre Grupo de Espacios\r\n"
				+ "3. Mensaje de Error\r\n"
				+ "4. Aplica Validaci�n\r\n"
				+ "y los botones de :\r\n"
				+ "\"\"Nuevo\"\"\r\n"
				+ "\"\"Limpiar\"\"\r\n"
				+ "\"\"Buscar\"\"\"\r\n"
				+ "\r\n"
				+ "");
		eg.clickMuestraFiltros();
		eg.clickMuestraGrid();
		
		//	  							   PASO 8	
		addStep("Realizar busqueda de grupo de espacio con los filtros");
		eg.clickBusquedaGpo(nombreGrupo);

		//		                           PASO 9
		addStep("\"\"Se muestran los resultados de la busqueda en el grid con los siguientes datos:\r\n"
				+ "1. Id Grupo de Espacios\r\n"
				+ "2. Nombre Grupo de Espacios\r\n"
				+ "3. Mensaje de Error\r\n"
				+ "4. Aplica Validaci�n\r\n"
				+ "Botones de accion : \"Editar\"\r\n"
				+ "\r\n"
				+ "");
		eg.clickMuestraGrid();
		
	}


	@Override
	public void beforeTest() {
	}

	@Override
	public String setTestDescription() {
		return "ATC_FT_457_Consulta_Grupo_espacios_equipo_Admdor_OXXO_Equipos_Busqueda_Test + Perfil + Negocio";
	}

	@Override
	public String setTestDesigner() {
		return "AutomationQA";
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_457_Consulta_Grupo_espacios_equipo_Admdor_OXXO_Equipos_Busqueda_Test";
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
