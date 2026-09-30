package WEB.inventarioEspacios.scripts.EspaciosEquipos;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import java.util.HashMap;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import WEB.inventarioEspacios.om.Consulta_Movtos_Eliminacion_Espacio;
import WEB.inventarioEspacios.om.MenuPrincipalIEP;
import WEB.inventarioEspacios.om.MenuPrincipalOpcionesIEP;
import WEB.om.PortalApps;
import modelo.BaseExecution; import WEB.util.GlobalVariables;
import utils.password.PasswordUtil;
import utils.selenium.MEdgeTest;
import utils.selenium.SeleniumUtil;
import utils.sql.SQLResult;
import utils.sql.SQLUtil;

// NOTA: Se tienen que llenar minimo este campo "Id_Equipo_Espacio", en su data provider, ya que con este valida y genera la consulta
public class ATC_FT_004_Consulta_Espacios_Equipo_Editar_No_duplicado extends BaseExecution{

	SeleniumUtil u;
	@Test(dataProvider = "data-provider")
	
	public void ATC_FT_004_Consulta_Espacios_Equipo_Editar_No_duplicado_test(HashMap<String, String> data) throws Exception {
		
		SQLUtil dbFCIAS = new SQLUtil(GlobalVariables.DB_HOST_FCIASQAS_PORTAL, GlobalVariables.DB_USER_FCIASQAS_PORTAL,GlobalVariables.DB_PASSWORD_FCIASQAS_PORTAL);
		
		/*
		 * Variables
		 ******************************************************************************************/
		String user = data.get("User");
		String password = PasswordUtil.decryptPassword(data.get("Password"));
//		String mngAlerta = "Usuario y/o Contrase�a Invalidos";
		String negocio = data.get("Negocio");
		String perfil = data.get("Perfil");
		String id_EspEqu = data.get("Id_Equipo_Espacio");
		String sistema = data.get("Sistema");
		String nomEspBuscar = data.get("Nom_Espacio_Eq_buscar");
		String nomEspMod= data.get("Nom_Espacio_Equipo_mod");
		String solicitanteMod = data.get("Solicitante_Mod");
		String grupo = data.get("Grupo");
		String plaza = data.get("Plaza");

		String oxxoQuery = "";
		String baraQuery = "";
		
		if (negocio.toUpperCase().equals("OXXO")) {
			
			String consulta = "select ESPACIO_ID,NOMBRE_ESPACIO,SISTEMA,LAST_UPDATE_DATE,CREATED_BY from XXINE.xxine_CAT_ESPACIOS WHERE ESPACIO_ID = '%s'";
			oxxoQuery = String.format(consulta, id_EspEqu);
			
		}else if (negocio.toUpperCase().equals("BARA")) {
			
			String consulta = "select ESPACIO_ID,NOMBRE_ESPACIO,SISTEMA,LAST_UPDATE_DATE,CREATED_BY from XXINE_BA.XXINE_CAT_ESPACIOS WHERE ESPACIO_ID = '%s'";
			baraQuery = String.format(consulta, id_EspEqu);
	
		}
		
		//									PASO 1
		addStep("Ingresar al portal https://fcportalqa.femcom.net:8443/AccessControl/pages/login_form.jsf con el <Usuario>"); //viene en excel
		//u = new SeleniumUtil(new ChromeTest(), true); //IETest para usar internet explorer
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
		addStep("Verificar la informaci�n del usuario y que se muestre el negocio y el perfil.");
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
		addStep("Validar que se muestre en el encabezado de la pantalla el Negocio, periodo y estatus del periodo.");
        boolean verEncabezado = mp.verificarEncabezado(negocio);
		
		if(verEncabezado)
			testCase.addTextEvidenceCurrentStep("La informaci�n del negocio, periodo y estatus del periodo es correcta");
		else
			testCase.addTextEvidenceCurrentStep("La informaci�n del negocio, periodo y estatus del periodo es incorrecta");
		
		assertTrue(verEncabezado, "No se muestra la informaci�n esperada");
		
		//                                 PASO 6	
		addStep("Seleccionar menu Espacios : Consulta de espacios/equipos");
		Consulta_Movtos_Eliminacion_Espacio ee = new Consulta_Movtos_Eliminacion_Espacio(u, testCase);
		mp.clickEspacios();
		mp.clickConsultaEspaciosEquipos();
		
		//								   PASO 7
		addStep("\"Se muestra la pantalla con los siguientes filtros:\r\n"
				+ "Sistema\r\n"
				+ "Nombre de espacio/equipo\r\n"
				+ "Nombre del solicitante\r\n"
				+ "Plazas\r\n"
				+ "y los botones de :\r\n"
				+ "\"\"Nuevo\"\"\r\n"
				+ "\"\"Limpiar\"\"\r\n"
				+ "\"\"Buscar\"\"\"\r\n"
				+ "\r\n"
				+ "");
        ee.clickPanEspaciosEqpos();		
        
		//      						   PASO 8
		addStep("Realizar una busqueda de <<Sistema>> con los filtros.");
		ee.clickBusquedaFiltros(sistema, nomEspBuscar);
		
		//	  							   PASO 9	
		addStep("Se muestran los resultados de la busqueda en el grid con los siguientes datos:\r\n"
				+ "Sistema\r\n"
				+ "Nombre\r\n"
				+ "Solicitante\r\n"
				+ "Negocio\r\n"
				+ "Grupo de espacios\r\n"
				+ "Solo plazas\r\n"
				+ "Botones de accion : \"\"Editar\"\" \"\"Imagen\"\"\"\r\n"
				+ "\r\n"
				+ "");
		ee.clickResultadosBusqueda();

		//      						   PASO 10
		addStep("Seleccionar el boton \"Editar\"");
		Boolean seleccion = true;
		ee.clickSelEspacioEq(id_EspEqu,seleccion);

		//      						   PASO 11
		addStep("\"Se despliega una nueva pantalla con los siguientes campos:\r\n"
				+ "Sistema\r\n"
				+ "Nombre de equipo/espacio\r\n"
				+ "Nombre del solicitante\r\n"
				+ "Grupo de espacios\r\n"
				+ "Negocio\r\n"
				+ "y los botones : \"\"Cancelar\"\" ,\"\"Guardar\"\"\"\r\n"
				+ "\r\n"
				+ "");
		Boolean bandera = false;
		ee.clickMuestraPanBtnNuevo(bandera);

		//      						   PASO 12
		addStep("Ingresar datos ya existentes y dar clic en \"Guardar\"");
		ee.clickModificaDatos(nomEspMod, solicitanteMod,grupo,plaza);
		Boolean band = false;
		ee.clickBotonGuardar(band);
		
		//      						   PASO 13
		addStep("Se muestra un mensaje en la parte inferior derecha: \"El espacio/equipo ya existe\".");
		ee.clickMensajeGuardado();
		//      						   PASO 14
		addStep("Ingresar a la BD y validar que no se guarden los cambios en la tabla XXINE_CAT_ESPACIOS.");
		System.out.println(GlobalVariables.DB_HOST_FCIASQAS_PORTAL);
		if (negocio.toUpperCase().equals("OXXO")) {
			System.out.println("Entro por Oxxo");
			System.out.println(oxxoQuery);
		
			SQLResult queryResult = executeQuery(dbFCIAS, oxxoQuery);
		
			boolean empty = queryResult.isEmpty();

			if (!empty) {
			
				if (queryResult.getData(0, "NOMBRE_ESPACIO").equals(data.get("Nom_Espacio_Equipo_mod"))) {
					assertFalse(true, "Se Modificaron Datos de Espacio/Equipo " + id_EspEqu + " en  la tabla  XXINE_CAT_ESPACIOS.");
				} else {
					testCase.addTextEvidenceCurrentStep("No Se Modifico el registro, Id Espacio/Equipo Modificado: " + id_EspEqu);
				}
			}	
			
			testCase.addQueryEvidenceCurrentStep(queryResult); //Agrega la consulta tal cual que se hace, siempre y cuando solo use una sola consulta
			
		}else if (negocio.toUpperCase().equals("BARA")) {
			System.out.println("Entro por Bara");
			
			System.out.println(baraQuery);
			
			SQLResult queryResult = executeQuery(dbFCIAS, baraQuery);
			
			boolean empty = queryResult.isEmpty();

			if (!empty) {
				
				if (queryResult.getData(0, "NOMBRE_ESPACIO").equals(data.get("Nom_Espacio_Equipo_mod"))) {
					assertFalse(true, "Se Modificaron Datos de Espacio/Equipo " + id_EspEqu + " en  la tabla  XXINE_BA.XXINE_CAT_ESPACIOS.");
				} else {
					testCase.addTextEvidenceCurrentStep("No Se Modifico el registro, Id Espacio/Equipo Modificado: " + id_EspEqu);
				}
				
			}						
			testCase.addQueryEvidenceCurrentStep(queryResult); //Agrega la consulta tal cual que se hace, siempre y cuando solo use una sola consulta
			
		}
	}

	@Override
	public void beforeTest() {
	}

	@Override
	public String setTestDescription() {
		return "ATC_FT_004_Consulta_Espacios_Equipo_Editar_No_duplicado";
	}

	@Override
	public String setTestDesigner() {
		return "AutomationQA";
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_004_Consulta_Espacios_Equipo_Editar_No_duplicado_test";
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
