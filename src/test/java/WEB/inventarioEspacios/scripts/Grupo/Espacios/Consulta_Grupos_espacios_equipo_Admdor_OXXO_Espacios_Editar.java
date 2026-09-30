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
import utils.sql.SQLUtil;

public class Consulta_Grupos_espacios_equipo_Admdor_OXXO_Espacios_Editar extends BaseExecution{

	SeleniumUtil u;
	@Test(dataProvider = "data-provider")
	
	public void ATC_FT_459_Consulta_Grupos_espacios_equipo_Admdor_OXXO_Espacios_Editar_Test(HashMap<String, String> data) throws Exception {
		/*
		 * Utiler�as
		 ******************************************************************************************/
		SQLUtil dbFCIAS = new SQLUtil(GlobalVariables.DB_HOST_FCIASQAS_PORTAL, GlobalVariables.DB_USER_FCIASQAS_PORTAL,GlobalVariables.DB_PASSWORD_FCIASQAS_PORTAL);
		
		
		/*
		 * Variables
		 ******************************************************************************************/
		String user = data.get("User");
		String password = PasswordUtil.decryptPassword(data.get("Password"));
//		String mngAlerta = "Usuario y/o Contrase�a Invalidos";
		String negocio = data.get("Negocio");
		String perfil = data.get("Perfil");
		String IdGrupo = data.get("Id_Grupo");
		String BuscarGrupo = data.get("Busca_Nombre_Grupo");
		String nombreGrupo = data.get("Nombre_Grupo");
		String comentario = data.get("Comentario");
		String Aplicar = data.get("Aplicar");

		String oxxoQuery = "";
		String baraQuery = "";
		
		if (negocio == "Oxxo") {
			
			String consulta = "select GRUPO_ESPACIOS_ID,NOMBRE_GRUPO_ESPACIOS,MENSAJE_ERROR,LAST_UPDATE_DATE,APLICAR_VALIDACION from XXINE.xxine_GRUPOS_ESPACIOS WHERE NOMBRE_GRUPO_ESPACIOS = '%s'";
			oxxoQuery = String.format(consulta, nombreGrupo);
			
		}else if (negocio.equals("Bara")) {
			
			String consulta = "select GRUPO_ESPACIOS_ID,NOMBRE_GRUPO_ESPACIOS,MENSAJE_ERROR,LAST_UPDATE_DATE,APLICAR_VALIDACION from XXINE_BA.xxine_GRUPOS_ESPACIOS WHERE NOMBRE_GRUPO_ESPACIOS = '%s'";
			baraQuery = String.format(consulta, nombreGrupo);
			
		}
		
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
		eg.clickBusquedaGpo(BuscarGrupo);

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

		//								PASO 10
		addStep("Seleccionar el boton Editar");
		eg.clickEditarGpo(IdGrupo);
//		eg.clikBtnGuardar();
		
		//								PASO 11
		addStep("El sistema debe permitir la edici�n de los Grupos de Espacios.. Al seleccionar un Grupo de Espacios y presionar el bot�n de Editar, podr� cambiar:\r\n"
				+ "1. Nombre de Grupo de Espacios\r\n"
				+ "2. Mensaje de Error\r\n"
				+ "3. Aplicar Validaci�n: Muestra las opciones Si o No, s�lo puede seleccionar una opci�n.\r\n"
				+ "");
		Boolean bandera = false;
		eg.clickMuestraPantNuevo(bandera);
		
		//								PASO 12
		addStep("Modificar los datos y presionar el boton Guardar");
		eg.clickCapturaDatos(nombreGrupo,comentario,Aplicar);
		eg.clikBtnGuardar(bandera);
		
		//								PASO 13
		addStep("Se muestra un mensaje en la parte inferior derecha con la confirmacion de edicion.");
		eg.clickMensajeCrea();
		
		//								   PASO 14
		addStep("Ingresar a la BD XXINE y validar que se guarde el nuevo registro en la tabla XXINE_GRUPOS_ESPACIOS");
//		System.out.println(GlobalVariables.DB_HOST_FCIASQAS_PORTAL);
//		if (negocio.equals("Oxxo")) {
//			System.out.println("Entro por Oxxo");
//			System.out.println(oxxoQuery);
//		
//			SQLResult queryResult = executeQuery(dbFCIAS, oxxoQuery);
//		
//			boolean empty = queryResult.isEmpty();
//
//			if (empty) {
//			
//			
//				assertFalse(empty, "No Se Modifico el Nombre de Grupo de Espacio: "+ nombreGrupo + " en la tabla XXINE.xxine_GRUPOS_ESPACIOS");
//		
//			}						
//			testCase.addQueryEvidenceCurrentStep(queryResult); //Agrega la consulta tal cual que se hace, siempre y cuando solo use una sola consulta
//			testCase.addTextEvidenceCurrentStep("Se Modifico correctamente el registro, Nombre de Grupo de Espacio: " + nombreGrupo);
//		}else if (negocio.equals("Bara")) {
//			System.out.println("Entro por Bara");
//			System.out.println(baraQuery);
//		
//			SQLResult queryResult = executeQuery(dbFCIAS, baraQuery);
//		
//			boolean empty = queryResult.isEmpty();
//
//			if (empty) {
//			
//			
//				assertFalse(empty, "No Se Modifico el Nombre de Grupo de Espacio: "+ nombreGrupo + " en la tabla XXINE_BA.xxine_GRUPOS_ESPACIOS");
//		
//			}						
//			testCase.addQueryEvidenceCurrentStep(queryResult); //Agrega la consulta tal cual que se hace, siempre y cuando solo use una sola consulta
//			testCase.addTextEvidenceCurrentStep("Se Modifico correctamente el registro, Nombre de Grupo de Espacio: " + nombreGrupo);
//		}
		
	}

	@Override
	public void beforeTest() {
	}

	@Override
	public String setTestDescription() {
		return "ATC_FT_459_Consulta_Grupos_espacios_equipo_Admdor_OXXO_Espacios_Editar_Test + Perfil + Negocio";
	}

	@Override
	public String setTestDesigner() {
		return "AutomationQA";
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_459_Consulta_Grupos_espacios_equipo_Admdor_OXXO_Espacios_Editar_Test";
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
