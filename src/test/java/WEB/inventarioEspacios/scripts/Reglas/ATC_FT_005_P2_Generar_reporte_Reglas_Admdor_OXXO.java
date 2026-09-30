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

public class ATC_FT_005_P2_Generar_reporte_Reglas_Admdor_OXXO extends BaseExecution{

	SeleniumUtil u;
	@Test(dataProvider = "data-provider")
	
	public void ATC_FT_005_P2_Generar_reporte_Reglas_Admdor_OXXO_test(HashMap<String, String> data) throws Exception {
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
		/*String idRegla = data.get("Id_Regla1");
		String idReglaDos = data.get("Id_Regla2");
		String idReglaTres = data.get("Id_Regla3");
		double NumPaginas = Double.parseDouble(data.get("NumPaginas"));
		int PaginasRedo = Integer.parseInt(data.get("PaginasRedo"));
		
		String oxxoQuery = "";
		String baraQuery = "";
		
		if (negocio.toUpperCase().equals("OXXO")) {
			
			String consulta = "select REGLA_ID,NOMBRE,DESCRIPCION,PRECONDICION,LAST_UPDATE_DATE from XXINE.xxine_CAT_REGLAS WHERE REGLA_ID = '%s'";
			oxxoQuery = String.format(consulta, idRegla);
			//String consultaDos = String.format(consulta, valor);
			//String query = String.format(consultaDos, fecha);	//Concatena valor String a la consulta
		
		}else if (negocio.toUpperCase().equals("BARA")) {
			
			String consulta = "select REGLA_ID,NOMBRE,DESCRIPCION,PRECONDICION,LAST_UPDATE_DATE from XXINE_BA.xxine_CAT_REGLAS WHERE REGLA_ID = '%s'";
			baraQuery = String.format(consulta, idRegla);
			
		}*/
		
		//									PASO 1
		addStep("Ingresar al portal https://fcportalqa.femcom.net:8443/AccessControl/pages/login_form.jsf con el <Usuario>"); //viene en excel

		u = new SeleniumUtil(new MEdgeTest(), true);  // para usar navegador Edge
		PortalApps pa = new PortalApps(u, testCase);  // crea objeto de locator
		u.get(data.get("Url"));    //toma el valor Url del data provider
		u.hardWait(3);           // tiempo en segundos
		
		pa.proceedToPortal();    // es para lo de la pagina no segura
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
		addStep("Seleccionar menu ->Administrador->Reglas.");
		Reglas_IEP dc = new Reglas_IEP(u, testCase);
		mp.clickAdministracion();
		mp.clickReglas();
		
		//      						   PASO 7
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
		
		//	  							   PASO 8	
		addStep("Dar clic en el boton Exportar");
		dc.clickBtnExportar();


		//		                           PASO 9
		addStep("El sistema muestra el mensaje \"Al finalizar la generaci�n del reporte se enviar� por correo electr�nico\"\r\n"
				+ "El sistema deber� generar un archivo de Excel con informaci�n de las reglas que aplican a todas las plazas, con las siguientes columnas:\r\n"
				+ "CR Plaza:\r\n"
				+ "Plaza: muestra el nombre de la plaza correspodiente\r\n"
				+ "CR Tienda:\r\n"
				+ "Tienda:\r\n"
				+ "Regla: muestra el nombre que se asigno a la regla en el campo nombre\r\n"
				+ "Descripci�n Regla: muestra el texto que se ingres� en el campo descripci�n\r\n"
				+ "\r\n"
				+ "Al finalizar la generaci�n el sistema deber� enviar el reporte por correo electr�nico al usuario administrador que lo solicit�");
		dc.clickMensajeReporteGenerado();
		//falta la validacion del mensaje
		
		
		
		// Los siguientes pasos no se pueden Automatizar por el momento
		//	  							   PASO 10	
	    /*addStep("Ingresar al correo electronico");
		
		//								   PASO 11
		addStep("Validar que el correo llegue con la siguiente descripci�n:\r\n"
				+ "Asunto: Reporte de Evaluaci�n de Reglas\r\n"
				+ "Cuerpo del correo: A continuaci�n se adjunta el archivo con el Reporte de Evaluaci�n de Reglas\r\n"
				+ "\"Archivo adjunto en formato Excel\" con los siguientes datos:\r\n"
				+ "\r\n"
				+ "CR Plaza\r\n"
				+ "Nombre de plaza:\r\n"
				+ "CR Tienda\r\n"
				+ "Nombre tienda\r\n"
				+ "Regla\r\n"
				+ "Descripci�n de Regla."
				+ "");*/
	}

	@Override
	public void beforeTest() {
	}

	@Override
	public String setTestDescription() {
		return "ATC_FT_005_P2_Generar_reporte_Reglas_Admdor_OXXO";
	}

	@Override
	public String setTestDesigner() {
		return "AutomationQA";
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_005_P2_Generar_reporte_Reglas_Admdor_OXXO_test";
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
