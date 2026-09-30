package WEB.inventarioEspacios.scripts.Proveedores;

import static org.testng.Assert.assertTrue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

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
import utils.sql.ISQLResult;
import utils.sql.SQLResult;
import utils.sql.SQLUtil;

public class ATC_FT_240_Proveedores_Boton_Eliminacion_Multiple_Confirmar_Admin_OXXO extends BaseExecution{

	SeleniumUtil u;
	@Test(dataProvider = "data-provider")
	
	public void ATC_FT_240_Proveedores_Boton_Eliminacion_Multiple_Confirmar_Admin_OXXO_test(HashMap<String, String> data) throws Exception {
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
		//int NumProv = Integer.parseInt(data.get("Numero_proveedor_A_Borrar"));
		String IdProveedor = data.get("Proveedor_Id1");
		String Nom_prov1 = data.get("Nom_prov1");
		String IdProveedor2 = data.get("Proveedor_Id2");
		String Nom_prov2 = data.get("Nom_prov2");
		String IdProveedor3 = data.get("Proveedor_Id3");
		String Nom_prov3 = data.get("Nom_prov3");
		String NomProveedor = data.get("Nombre");
		String NomProvExi = data.get("Nombre_Prov_Exis");
		
		String oxxoQuery = "";
		String baraQuery = "";
		
		//									PASO 1
		addStep("Ingresar al portal https://fcportalqa.femcom.net:8443/AccessControl/pages/login_form.jsf con el <Usuario>"); //viene en excel
		//u = new SeleniumUtil(new ChromeTest(), true); //IETest para usar internet explorer
		SeleniumUtil u = new SeleniumUtil(new MEdgeTest(), true);  // para usar navegador Edge
		//SeleniumUtil u = new SeleniumUtil(new IETest(), true);
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
		Consulta_Movtos_Eliminacion_Proveedor ep = new Consulta_Movtos_Eliminacion_Proveedor(u, testCase);
		addStep("Seleccionar mas de un registro de proveedor, marcando su checkbox correspondiente.");
		String StrProv = "";
		int cont = 0;
		
		ArrayList <String> Proveedores = new ArrayList<String>();
		System.out.println(Nom_prov1);
		for (Map.Entry<String, String> entry : data.entrySet()) {
			String encab = entry.getKey();
			String valor = entry.getValue();
			
			if (encab.contains("Proveedor") && valor.trim().length() > 0 ) {
				Proveedores.add(valor);
				if (cont > 0) 
					
					StrProv += ",";
					StrProv += valor;
					cont++;
			}
		}
		
		if (negocio.toUpperCase().equals("OXXO")) {
			String consulta = "select PROVEEDOR_ID,NOMBRE_PROVEEDOR,EMAIL,LAST_UPDATE_DATE,LAST_UPDATED_BY FROM XXINE.XXINE_CAT_PROVEEDORES WHERE PROVEEDOR_ID IN(%s)";
			oxxoQuery = String.format(consulta, StrProv);
			
			//String consultaDos = String.format(consulta, valor);
			//String query = String.format(consultaDos, fecha);	//Concatena valor String a la consulta
		}else if (negocio.toUpperCase().equals("BARA")) {
			
			String consulta = "select PROVEEDOR_ID,NOMBRE_PROVEEDOR,EMAIL,LAST_UPDATE_DATE,LAST_UPDATED_BY FROM XXINE_BA.xxine_CAT_PROVEEDORES WHERE PROVEEDOR_ID IN(%s)";
			baraQuery = String.format(consulta, StrProv);
		}
		pbb.clickSeleccionMultipleProv(Proveedores);

		//	  							   PASO 9	
		addStep("Dar clic en el bot�n Eliminar");
		ep.clickBtnEliminar();
        
		//		                           PASO 10
		addStep("El sistema mostrar� el mensaje �Esta seguro que desea eliminar el registro?, con las opciones Cancelar y Aceptar");
        ep.clickMensajeConfirmar();

        
		//		                           PASO 11 
		addStep(" Dar clic en el bot�n Confirmar. ");
//		ep.clickBtnConfirmarElim();


		//		                           PASO 12
		addStep(" El sistema realizar� la eliminaci�n del registro exitosamente.");
//		ep.clickMsjConfElim();

		//		                           PASO 13
		addStep("ingresar al esquema de BD XXINE a consultar la tabla XXINE_CAT_PROVEEDROES y validar que se eliminen los registros seleccionados");
		System.out.println(GlobalVariables.DB_HOST_FCIASQAS_PORTAL);
		if (negocio.toUpperCase().equals("OXXO")) {
			System.out.println("Entro por Oxxo");
			System.out.println(oxxoQuery);		
			SQLResult queryResult = executeQuery(dbFCIAS, oxxoQuery);
		
			boolean empty = ((ISQLResult) queryResult).isEmpty();

			if (empty) {
			
				testCase.addTextEvidenceCurrentStep("Se Elimino correctamente el/los registro(s), Id Proveedor(es) Eliminado(s) " + StrProv);
			
		
			}						
			testCase.addQueryEvidenceCurrentStep(queryResult); //Agrega la consulta tal cual que se hace, siempre y cuando solo use una sola consulta
			assertTrue(empty, "No Se elimino el/los Id Proveedor(es) " + StrProv + " de  la tabla XXINE.XXINE_CAT_PROVEEDORES");
		}else if (negocio.toUpperCase().equals("BARA")) {
			System.out.println("Entro por Bara");
			System.out.println(baraQuery);
		
			SQLResult queryResult = executeQuery(dbFCIAS, baraQuery);
		
			boolean empty = ((ISQLResult) queryResult).isEmpty();

			if (empty) {
			
				
				
				testCase.addTextEvidenceCurrentStep("Se Elimino correctamente el/los registro(s) Bara, Id Proveedor(es) Eliminado(s) " + StrProv);
			}						
			testCase.addQueryEvidenceCurrentStep(queryResult); //Agrega la consulta tal cual que se hace, siempre y cuando solo use una sola consulta
			assertTrue(empty, "No Se elimino el/los Id Proveedor(es) Bara: " + StrProv + " de la tabla XXINE_BA.xxine_CAT_PROVEEDORES");
			
		}
	}
	@Override
	public void beforeTest() {
	}

	@Override
	public String setTestDescription() {
		return "ATC_FT_240_Proveedores_Boton_Eliminacion_Multiple_Confirmar_Admin_OXXO_Test + Prefil + Negocio";
	}

	@Override
	public String setTestDesigner() {
		return "AutomationQA";
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_240_Proveedores_Boton_Eliminacion_Multiple_Confirmar_Admin_OXXO_test";
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
