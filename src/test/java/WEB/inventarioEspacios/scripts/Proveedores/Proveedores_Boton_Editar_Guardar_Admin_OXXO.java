package WEB.inventarioEspacios.scripts.Proveedores;

import java.util.HashMap;

import org.testng.annotations.Test;

import WEB.inventarioEspacios.om.Consulta_Movtos_Eliminacion_Proveedor;
import WEB.inventarioEspacios.om.MenuPrincipalIEP;
import WEB.inventarioEspacios.om.MenuPrincipalOpcionesIEP;
import WEB.inventarioEspacios.om.Proveedores_Boton_Buscar_Adm_OXXO;
import WEB.om.PortalApps;
import modelo.BaseExecution; import WEB.util.GlobalVariables;
import utils.password.PasswordUtil;
import utils.selenium.MEdgeTest;
import utils.selenium.SeleniumUtil;
import utils.sql.SQLUtil;

public class Proveedores_Boton_Editar_Guardar_Admin_OXXO extends BaseExecution{

	SeleniumUtil u;
	@Test(dataProvider = "data-provider")
	
	public void ATC_FT_234_Proveedores_Boton_Editar_Guardar_Admin_OXXO_Test(HashMap<String, String> data) throws Exception {
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
		String IdProveedor = data.get("Proveedor_Id");
		String NomProveedor = data.get("Nombre_Proveedor");
		String NomProvExi = data.get("Nombre_Prov_Exis");
		String email = data.get("Email");
		String emailCorrec = data.get("Email_Correc");

		String oxxoQuery = "";
		String baraQuery = "";
		
		if (negocio == "Oxxo") {
			
			String consulta = "select PROVEEDOR_ID,NOMBRE_PROVEEDOR,EMAIL,LAST_UPDATE_DATE,LAST_UPDATED_BY FROM XXINE.XXINE_CAT_PROVEEDORES WHERE NOMBRE_PROVEEDOR = '%s'";
			oxxoQuery = String.format(consulta, NomProveedor);
			//String consultaDos = String.format(consulta, valor);
			//String query = String.format(consultaDos, fecha);	//Concatena valor String a la consulta

		}else if (negocio.equals("Bara")) {
			
			String consulta = "select PROVEEDOR_ID,NOMBRE_PROVEEDOR,EMAIL,LAST_UPDATE_DATE,LAST_UPDATED_BY FROM XXINE_BA.XXINE_CAT_PROVEEDORES where NOMBRE_PROVEEDOR= '%s'";
			baraQuery = String.format(consulta, NomProveedor);
			
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
		Proveedores_Boton_Buscar_Adm_OXXO pbb = new Proveedores_Boton_Buscar_Adm_OXXO(u, testCase);
		addStep("Seleccionar Menu->Administraci�n->Proveedores");
		pbb.clickMenuSbProveedores();
		
		//								   PASO 7
		addStep("  Se abre la pantalla Proveedores con el siguiente contenido:\r\n"
				+ "-Filtros. \r\n"
				+ "     -Nombre de proveedor.\r\n"
				+ "-Bot�n Nuevo.\r\n"
				+ "-Bot�n Limpiar.\r\n"
				+ "-Bot�n Buscar.\r\n"
				+ "-Grid principal con registros proveedores.\r\n"
				+ "   ID Proveedor/Nombre/Email\r\n"
				+ "-Bot�n Editar.\r\n"
				+ "");
		pbb.clickPantProveedor();
		pbb.clickGridProveedor();		
		
		//	  							   PASO 8
		addStep("Dar clic en el nombre del proveedor o en el bot�n Editar del proveedor que desee modificar.");
		Consulta_Movtos_Eliminacion_Proveedor ep = new Consulta_Movtos_Eliminacion_Proveedor(u, testCase);
		pbb.clickCapturaProv(NomProveedor);
		pbb.clickEditarProv(IdProveedor);
		

		//	  							   PASO 9	
		addStep("El sistema abre la pantalla Edici�n del proveedor con el siguiente contenido:\r\n"
				+ " -Nombre del proveedor.\r\n"
				+ " -Email del proveedor.\r\n"
				+ "Bot�n. Cancelar\r\n"
				+ "Bot�n. Grabar.\r\n"
				+ "");
		boolean ban = false;
		pbb.clickPanNuevoProv(ban);
        
        
		//		                           PASO 10
		addStep("Realizar modificaciones y dar clic en Grabar");
		boolean band = true;
		pbb.clickEmail(email,emailCorrec,band);
		ban = false;
		pbb.clickGuardar();
		
		//		                           PASO 11
		addStep("El sistema muestra mensaje de confirmaci�n con los botones Cancelar y Confirmar");
		boolean bandera = true;
		pbb.clickMsjConfirGuardar(bandera);
		//pbb.clickConfirGuardar();

		//		                           PASO 12
		addStep("Dar clic en el bot�n Cancelar");
		pbb.clickBtnCancelar();
//		cont = true;
//		pbb.clickEmail(email,emailCorrec,cont);
//		pbb.clickGuardar();
//		pbb.clickConfirGuardar();
		
		//		                           PASO 13
		addStep("El sistema cierra el pop-up del mensaje y muestra nuevamente la pantalla de edici�n");
		ban = false;
		pbb.clickPanNuevoProv(ban);

		//		                           PASO 14
		addStep("Dar clic en el bot�n Grabar");
		ban = false;
		pbb.clickGuardar();
		
		//		                           PASO 15
		addStep("El sistema muestra mensaje de confirmaci�n con los botones Cancelar y Confirmar");
		bandera = false;
		pbb.clickMsjConfirGuardar(bandera);
		
		//		                           PASO 16
		addStep("Dar clic en el bot�n Confirmar.");
		pbb.clickConfirGuardar();
		
		//		                           PASO 17
		addStep("El sistema deber� guardar los cambios realizados y mostrar mensaje de confirmaci�n de actualizaci�n, se mostrar� pantalla anterior");
		pbb.clickMensajeActualizado();
		
		//		                           PASO 18
		addStep("ingresar al esquema de BD XXINE a consultar la tabla XXINE_CAT_PROVEEDROES y validar que se guarden los cambios.");
//		System.out.println(GlobalVariables.DB_HOST_FCIASQAS_PORTAL);
//		if (negocio.equals("Oxxo")) {
//			System.out.println("Entro por Oxxo");
//			System.out.println(oxxoQuery);
//		
//			SQLResult queryResult = executeQuery(dbFCIAS, oxxoQuery);
//		
//			boolean empty = ((ISQLResult) queryResult).isEmpty();
//
//			if (empty) {
//			
//				assertFalse(empty, "No Se Actualizo datos del Proveedor " + " de  la tabla XXINE_CAT_PROVEEDORES");
//				
//			}						
//			testCase.addQueryEvidenceCurrentStep(queryResult); //Agrega la consulta tal cual que se hace, siempre y cuando solo use una sola consulta
//			testCase.addTextEvidenceCurrentStep("Se Actualizo correctamente el registro. ");
//		}else if (negocio.equals("Bara")) {
//			System.out.println("Entro por Bara");
//			System.out.println(baraQuery);
//		
//			SQLResult queryResult = executeQuery(dbFCIAS, baraQuery);
//		
//			boolean empty = ((ISQLResult) queryResult).isEmpty();
//
//			if (empty) {
//			
//				assertFalse(empty, "No Se Actualizo datos del Proveedor " + " de  la tabla XXINE_BA.xxine_CAT_PROVEEDORES");
//				
//			}						
//			testCase.addQueryEvidenceCurrentStep(queryResult); //Agrega la consulta tal cual que se hace, siempre y cuando solo use una sola consulta
//			testCase.addTextEvidenceCurrentStep("Se Actualizo correctamente el registro. ");
//
//		}
	}

	@Override
	public void beforeTest() {
	}

	@Override
	public String setTestDescription() {
		return "ATC_FT_234_Proveedores_Boton_Editar_Guardar_Admin_OXXO_Test + Prefil + Negocio";
	}

	@Override
	public String setTestDesigner() {
		return "AutomationQA";
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_234_Proveedores_Boton_Editar_Guardar_Admin_OXXO_Test";
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
