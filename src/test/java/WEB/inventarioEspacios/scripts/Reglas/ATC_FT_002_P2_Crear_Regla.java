package WEB.inventarioEspacios.scripts.Reglas;

import static org.testng.Assert.assertFalse;
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

public class ATC_FT_002_P2_Crear_Regla extends BaseExecution {

	SeleniumUtil u;
	@Test(dataProvider = "data-provider")
	
	public void ATC_FT_002_P2_Crear_Regla_test(HashMap<String, String> data) throws Exception {
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
		String NombreRegla = data.get("Nombre_Regla");
		String descripcionTxt = data.get("Descripcion");
		String Obligatoria = data.get("Obligatoria");
		String EstatusTxt = data.get("Estatus");
		String nomTienda = data.get("NOMBRE_TIENDA");
		String plaza = data.get("Plaza");
		String sbPlaza = data.get("SubPlaza");
		String espacio = data.get("Select_Espacios");
		String cantidPosCond = data.get("Cantidad");
		String Mensaje = data.get("Mensaje");
		String Year = data.get("A�o");
		String Mes = data.get("Mes");
		String Dia = data.get("Dia");
		String DiaFinal = data.get("Dia_Fin");

		String oxxoQuery = "";
		String baraQuery = "";
		
		if (negocio.toUpperCase().equals("OXXO")) {
			
			String consulta = "select REGLA_ID,NOMBRE,DESCRIPCION,ESTATUS,OBLIGATORIA,PRECONDICION,LAST_UPDATE_DATE from XXINE.xxine_cat_reglas where NOMBRE = '%s'";
			oxxoQuery = String.format(consulta, NombreRegla);
		
		}else if (negocio.toUpperCase().equals("BARA")) {
			
			String consulta = "select REGLA_ID,NOMBRE,DESCRIPCION,ESTATUS,OBLIGATORIA,PRECONDICION,LAST_UPDATE_DATE from XXINE_BA.xxine_cat_reglas where NOMBRE = '%s'";
			baraQuery = String.format(consulta, NombreRegla);
			
		}
		
		//									PASO 1
		addStep("Ingresar al portal https://fcportalqa.femcom.net:8443/AccessControl/pages/login_form.jsf con el <Usuario>"); //viene en excel
//		u = new SeleniumUtil(new ChromeTest(), true); //IETest para usar internet explorer
		u = new SeleniumUtil(new MEdgeTest(), true);
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
		addStep("Se visualizan los filtros y campos:\r\n"
				+ "Nombre: \r\n"
				+ "Descripci�n:\r\n"
				+ "Estatus:\r\n"
				+ "Fecha Creaci�n: \r\n"
				+ "Botones: Nuevo, Exportar, Limpiar y buscar\r\n"
				+ "Id Regla: Muestra el ID que le asign� autom�ticamente el sistema\r\n"
				+ "Nombre: Muestra el nombre que se le asign� a la regla\r\n"
				+ "Descripci�n: Muestra la descripci�n que se le asign� a la regla\r\n"
				+ "Obligatoria: Muestra el valor Si / No que se le asign� desde el alta de la regla\r\n"
				+ "Estatus: Muestra el valor Activo / Inactivo que se le asign� desde el alta de la regla\r\n"
				+ "Fecha Creaci�n: Muestra la fecha en que se cre� la regla\r\n"
				+ "Por cada regla deber� mostrar las acciones Eliminar y Editar \r\n"
				+ "\r\n"
				+ "\r\n"
				+ "");
		dc.clickIdConsultaReglas();
		

		//                                 PASO 8
		addStep("Dar clic en el boton \"Nuevo\" y capturar datos generales.");
        dc.clickBtnNuevo();
        Boolean band = true;
		dc.clickIdReglatxt(NombreRegla,band);
		dc.clickpDatosGrales(descripcionTxt, Obligatoria, EstatusTxt, nomTienda, plaza, sbPlaza);		
		
		//                                 PASO 9
		addStep("El sistema habilita la pantalla Captura de Regla, al dar clic en el bot�n Nuevo. \r\n"
				+ "La pantalla contiene los botones: \r\n"
				+ "Grabar / Limpiar / Cancelar \r\n"
				+ ": \r\n"
				+ "La pantalla se divide en 5 pesta�as (Cada pesta�a tiene su propio boton Grabar / Limpiar / Cancelar):\r\n"
				+ "\r\n"
				+ "General / Condici�n / Postcondici�n / Aplicar a / Mensajes\r\n"
				+ "Al ingresar autom�ticamente la pantalla abre en la pesta�a General, el usuario deber� capturar los siguientes campos: \r\n"
				+ "Nombre: Obligatorio, campo de texto abierto alfanum�rico \r\n"
				+ "Descripci�n: Obligatorio, campo de texto abierto alfanum�rico\r\n"
				+ "Obligatoria: Obligatorio, campo de selecci�n que muestra los valores Si / No. Por default aparece seleccionada la opci�n Si. \r\n"
				+ "Estatus: Obligatorio, campo de selecci�n que muestra los valores Activo / Inactivo. Por default aparece seleccionada la opci�n Activo.\r\n"
				+ "\r\n"
				+ "");
	    dc.clickPanCapturaRegla();
		

		//                                 PASO 10
		addStep("Capturar condiciones de la regla y valida que sea correcta");
		dc.clickPesCondicion(nomTienda);

		//                                 PASO 11
		addStep("\"El sistema debe habilitar la pesta�a Condici�n que servir� para configurar la f�rmula de la regla. La f�rmula es obligatoria y solo se podr� guardar si no se configura previamente de manera correcta. \r\n"
				+ "Al entrar a esta pesta�a aparece el mensaje \"Introduzca la expresi�n que ser� usada como condici�n:\" y se muestran los campos de selecci�n:\r\n"
				+ "Atributos de Tienda: Despliega los campos disponibles de la tabla de tiendas (XXINE_CAT_TIENDAS). Al dar clic en alguna opci�n, se agrega el texto correspondiente a la caja de texto de la f�rmula.\r\n"
				+ "Atributos de Espacio: Despliega los campos disponibles de la tabla de espacios (XXINE_CAT_ESPACIOS). Al dar clic en alguna opci�n, se agrega el texto correspondiente a la caja de texto de la f�rmula.\r\n"
				+ "Cantidad de Id de Espacio: Despliega la lista de espacios completa, ordenada por nombre. Al dar clic en alguna opci�n, se agrega el texto correspondiente a la caja de texto de la f�rmula.\r\n"
				+ "Tambi�n deber� mostrar un tablero para la construcci�n del algoritmo que deber� contener los botones: \r\n"
				+ "Numeraci�n del 0 al 9 y punto decimal \r\n"
				+ "Operaciones aritm�ticas: +, -, *, /, + - \r\n"
				+ "Operadores de comparaci�n: >, <, >=, <=, ==, !, !=\r\n"
				+ "Operadores l�gicos: AND, OR, EQUALS\r\n"
				+ "Acciones: CLR (al dar clic en este bot�n borra todo lo que se haya agregado a la f�rmula) Back (al dar clic en este bot�n borra el �ltimo valor de la expresi�n)\r\n"
				+ "El sistema tambi�n muestra un campo para introducir la f�rmula manualmente o con ayuda del tablero \r\n"
				+ "");
		dc.clickMuestraEtiqCondicion();
		//dc.clickBtnGuardarAplicar();
        
		//                                 PASO 12
		addStep("\"Capturar PostCondici�n");
		dc.clickPesPosCondicion();
        
		//                                 PASO 13
		addStep("El sistema debe habilitar la pesta�a Postcondici�n que servir� para configurar las postcondiciones de la regla, las postcondiciones no son obligatorias. \r\n"
				+ "Las postcondiciones se ejecutan para las reglas que son configuradas como \"Obligatorias\" y se pueden tener cero, una o varias postcondiciones configuradas. Para las reglas que \"No son Obligatorias\" las postcondiciones no tienen efecto (VER DOCUMENTACI�N T�CNICA)\r\n"
				+ "Al ingresar a la pesta�a se muestra el mensaje \"Captura una o m�s postcondiciones:\" \r\n"
				+ "y se muestra el bot�n Agregar, al dar clic en este bot�n se agrega una fila para configurar la postcondici�n con los siguientes campos: \r\n"
				+ "Campo de selecci�n, se enlistan los espacios ordenados por ESPACIO_ID\r\n"
				+ "Campo num�rico, el usuario solo podr� ingresar valores enteros, no debe permitir texto ni decimales\r\n"
				+ "Bot�n eliminar, al dar clic en esta acci�n se elimina la fila de la postcondici�n\r\n"
				+ "");
		String op = "O";
		dc.clickMuestraPesPoscondicion(cantidPosCond,espacio,op);

		//                                 PASO 14	
		addStep("Capturar Aplicar a");
		dc.clickPesAplicarA(plaza, sbPlaza);
		
		//                                 PASO 15	
		addStep("El sistema debe habilitar la pesta�a Aplicar a que servir� para configurar las plazas, subplazas, plazas ccm, tiendas y espacios a los cuales aplicar� la regla. \r\n"
				+ "Esta selecci�n no es obligatoria, sin embargo sino se selecciona al menos una plaza, subplaza, plaza ccm o tienda, la regla no tiene efecto. \r\n"
				+ "Al ingresar a la pesta�a se muestra el mensaje \"Indica las plazas, subplazas, plazas ccm, tiendas y espacios a las que aplicar� la regla\"\r\n"
				+ "El sistema muestra los siguientes campos para filtrar y por cada uno muestra un campo con la selecci�n:\r\n"
				+ "Plazas: Lista de plazas ordenadas alfab�ticamente. Se puede seleccionar una, todas o hacer multiselecci�n con la funci�n Ctrl + clic izquierdo. Al seleccionar un valor, se cargan los valores en cascada para las listas de Subplazas, Plazas CCM y Tiendas.\r\n"
				+ "Subplazas: Lista de subplazas que corresponden a la plaza seleccionada, ordenadas alfab�ticamente. Se puede seleccionar una, todas o hacer multiselecci�n con la funci�n Ctrl + clic izquierdo. \r\n"
				+ "Tiendas: Lista de tiendas que corresponden a la plaza seleccionada, ordenadas alfab�ticamente. Se puede seleccionar una, todas o hacer multiselecci�n con la funci�n Ctrl + clic izquierdo. \r\n"
				+ "Plazas CCM: Lista de plazas CCM que corresponden a la plaza seleccionada, ordenadas alfab�ticamente. Se puede seleccionar una, todas o hacer multiselecci�n con la funci�n Ctrl + clic izquierdo. \r\n"
				+ "Espacios: Lista de espacios disponibles ordenados por ESPACIO_ID. Esta lista es informativa, ya que el sistema la mantiene autom�ticamente obteniendo la lista de espacios a aplicar de la f�rmula configurada, el campo de selecci�n muestra el/los espacios que se configuraron en la regla. Si se agregan m�s espacios del campo de filtro y se guarda la regla, el cambio en este control no tiene efecto. Se puede seleccionar uno, todos o hacer multiselecci�n con la funci�n Ctrl + clic izquierdo. \r\n"
				+ "");
		dc.clickMuestraPesAplicarA();
		
		//                                 PASO 16	
		addStep("Capturar mensaje de la regla");
		dc.clickPesMensaje(Mensaje);
		
		//                                 PASO 17	
		addStep("El sistema debe habilitar la pesta�a Mensajes que servir� para capturar los mensajes de error que aparecer�n en pantalla cuando las reglas no se cumplan.\r\n"
				+ "Al ingresar a la pesta�a se muestra el mensaje \"Capture el mensaje a desplegar cuando no se cumpla la regla\" y el campo de texto abierto alfanum�rico.\r\n"
				+ "");
		dc.clickMuestraPesMensaje();

//                                        PASO 18	
		addStep("Dar clic en guardar");
		dc.clickBtnGuardarAplicar();
		
		//falta agregar validacion de mensaje en pantalla

//    									  PASO 19	

		addStep("El sistema env�a mensaje de confirmaci�n,  carga la pantalla principal de Reglas y guarda los datos en las tablas XXINE_CAT_REGLAS, XXINE_POSTCONDICION_REGLA, XXINE_ESPACIOS_REGLA, XXINE_PLAZAS_CCM_REGLA, XXINE_SUBPLAZAS_REGLA");
		System.out.println(GlobalVariables.DB_HOST_FCIASQAS_PORTAL);
		if (negocio.toUpperCase().equals("OXXO")) {
			System.out.println("Entro por Oxxo");
			System.out.println(oxxoQuery);
		
			SQLResult queryResult = executeQuery(dbFCIAS, oxxoQuery);	
			boolean empty = queryResult.isEmpty();
								
			testCase.addQueryEvidenceCurrentStep(queryResult); //Agrega la consulta tal cual que se hace, siempre y cuando solo use una sola consulta
		
			if (empty) {
				
				assertFalse(empty, "No Se guardo el registro " + NombreRegla + " de  la tabla XXINE_CAT_REGLAS");
		
			}  else  {
				
				testCase.addTextEvidenceCurrentStep("Se Guardo correctamente el registro, Nombre Regla " + NombreRegla);
				
			}
		
		}else if (negocio.toUpperCase().equals("BARA")) {
			System.out.println("Entro por Bara");
			System.out.println(baraQuery);
		
			SQLResult queryResult = executeQuery(dbFCIAS, baraQuery);	
			boolean empty = queryResult.isEmpty();
								
			testCase.addQueryEvidenceCurrentStep(queryResult); //Agrega la consulta tal cual que se hace, siempre y cuando solo use una sola consulta
			
			if (empty) {
				
				assertFalse(empty, "No Se guardo el registro " + NombreRegla + " de  la tabla XXINE_BA.xxine_CAT_REGLAS");
		
			}	else  {
				
				testCase.addTextEvidenceCurrentStep("Se Guardo correctamente el registro, Nombre Regla " + NombreRegla);
				
			}
		}

	}
 
	
	@Override
	public void beforeTest() {
	}

	@Override
	public String setTestDescription() {
		return "ATC_FT_002_P2_Crear_Regla";
	}

	@Override
	public String setTestDesigner() {
		return "AutomationQA";
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_002_P2_Crear_Regla_test";
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
