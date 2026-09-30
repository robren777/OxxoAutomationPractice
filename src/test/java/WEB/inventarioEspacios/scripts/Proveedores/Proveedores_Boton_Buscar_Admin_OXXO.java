package WEB.inventarioEspacios.scripts.Proveedores;

import java.util.HashMap;

import org.testng.annotations.Test;

import WEB.inventarioEspacios.om.MenuPrincipalIEP;
import WEB.inventarioEspacios.om.MenuPrincipalOpcionesIEP;
import WEB.inventarioEspacios.om.Proveedores_Boton_Buscar_Adm_OXXO;
import WEB.om.PortalApps;
import modelo.BaseExecution; import WEB.util.GlobalVariables;
import utils.password.PasswordUtil;
import utils.selenium.MEdgeTest;
import utils.selenium.SeleniumUtil;

public class Proveedores_Boton_Buscar_Admin_OXXO extends BaseExecution {

	SeleniumUtil u;
	@Test(dataProvider = "data-provider")
	
	public void ATC_FT_225_Proveedores_Boton_Buscar_Admin_OXXO_Test(HashMap<String, String> data) throws Exception {
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
		String proveedorId = data.get("Proveedor_Id");
		String NomProveedor = data.get("Nombre_Proveedor");
		String NomProvExi = data.get("Nombre_Prov_Exis");
		
		
//		String consulta = "select fecha,objeto_eliminado_id,valor from XXINE.XXINE_MOVIMIENTOS_SISTEMA where  objeto_eliminado_id = '%s'";
//		String query = String.format(consulta, proveedorId);
		//String consultaDos = String.format(consulta, valor);
		//String query = String.format(consultaDos, fecha);	//Concatena valor String a la consulta
		
		//									PASO 1
		addStep("Ingresar al portal https://fcportalqa.femcom.net:8443/AccessControl/pages/login_form.jsf con el <Usuario>"); //viene en excel
//		u = new SeleniumUtil(new ChromeTest(), true); //IETest para usar internet explorer
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
		addStep("Seleccionar Menu->Administraci�n->Proveedores");
		Proveedores_Boton_Buscar_Adm_OXXO pbb = new Proveedores_Boton_Buscar_Adm_OXXO(u, testCase);
		pbb.clickMenuSbProveedores();
		
		//								   PASO 7
		addStep(" Se abre la pantalla Proveedores con el siguiente contenido:\r\n"
				+ "-Filtros. \r\n"
				+ "     -Nombre de proveedor.\r\n"
				+ "-Bot�n Nuevo.\r\n"
				+ "-Bot�n Limpiar.\r\n"
				+ "-Bot�n Buscar.\r\n"
				+ "");
		
		pbb.clickPantProveedor();		
				
		//      						   PASO 8
		addStep("Grid principal con registros proveedores.\r\n"
				+ "   ID Proveedor/Nombre/Email\r\n"
				+ "-Bot�n Editar.\r\n"
				+ "");
		pbb.clickGridProveedor();
		
		//	  							   PASO 9	
		addStep("Capturar el nombre de un proveedor que no exista y dar clic en el bot�n Buscar.");
        pbb.clickCapturaProv(NomProveedor);
        
		//		                           PASO 10
		addStep(" El sistema mostrar� el mensaje No hay informaci�n a mostrar dentro del grid principal.");
        pbb.clickMensaje();
        
		//		                           PASO 11
		addStep("Capturar el nombre de un proveedor exitente y dar clic en el bot�n Buscar.");
		Boolean bandera = true;
		pbb.clickCapProvExis(NomProvExi);

		//		                           PASO 12
		addStep(" El sistema deber� mostrar los registros que coincidan con el criterio de b�squeda capturado.");
		pbb.clickGridProveedor();
	}

	@Override
	public void beforeTest() {
	}

	@Override
	public String setTestDescription() {
		return "ATC_FT_225_Proveedores_Bot�n_Buscar_Admin_OXXO_Test + Prefil + Negocio";
	}

	@Override
	public String setTestDesigner() {
		return "AutomationQA";
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_225_Proveedores_Bot�n_Buscar_Admin_OXXO_Test";
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
