package WEB.inventarioEspacios.scripts.Tiendas;

import java.util.HashMap;

import org.testng.annotations.Test;

import WEB.inventarioEspacios.om.MenuPrincipalIEP;
import WEB.inventarioEspacios.om.MenuPrincipalOpcionesIEP;
import WEB.inventarioEspacios.om.Tiendas_IEP;
import WEB.om.PortalApps;
import modelo.BaseExecution; import WEB.util.GlobalVariables;
import utils.password.PasswordUtil;
import utils.selenium.MEdgeTest;
import utils.selenium.SeleniumUtil;

public class P2_Tiendas_Admdor_OXXO_Busqueda_Limpiar extends BaseExecution {

	SeleniumUtil u;
	@Test(dataProvider = "data-provider")
	
	public void ATC_FT_098_P2_Tdas_Admdor_OXXO_Busqueda_Limpiar_Test(HashMap<String, String> data) throws Exception {
		/*
		 * Variables
		 ******************************************************************************************/
		String user = data.get("User");
		String password = PasswordUtil.decryptPassword(data.get("Password"));
//		String mngAlerta = "Usuario y/o Contrase�a Invalidos";
		String negocio = data.get("Negocio");
		String perfil = data.get("Perfil");
		String nomPlaza = data.get("Nombre_Plaza");
		String modelo = data.get("Modelo");
		String descripTienda = data.get("Descrip_Tienda");
		String nomPlazaDos = data.get("Nombre_Plaza_Dos");
		String modeloDos = data.get("Modelo_Dos");
		String descripTiendaDos = data.get("Descrip_Tienda_dos");
		
		
//		//									PASO 1
			
		addStep("Ingresar al portal https://fcportalqa.femcom.net:8443/AccessControl/pages/login_form.jsf con el <Usuario>"); //viene en excel
//		u = new SeleniumUtil(new ChromeTest(), true); //IETest para usar internet explorer
		SeleniumUtil u = new SeleniumUtil(new MEdgeTest(), true);
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
		addStep("  Validar que se muestre en el encabezado de la pantalla el Negocio, periodo y estatus del periodo.");
		mp.verificarEncabezado("Administrador");
		
		//                                 PASO 6
		addStep("Seleccionar menu Administrador : Tiendas");
		Tiendas_IEP dt = new Tiendas_IEP(u, testCase);
		dt.clickAdmorTiendas(negocio);
		
		//                                 PASO 7
		addStep("\"Se muestra la pantalla con los filtros:");
		dt.clickFiltrosTiendas();

		//                                 PASO 8
		addStep("Ingresar filtros y dar click en Buscar");
		dt.clickIngresaFiltrosTda(nomPlaza,modelo,descripTienda);
		dt.clickBusquedaTda();

		//                                 PASO 9
		addStep("\"Se muestran los resultados en pantalla con los campos:");
		dt.clickResultadosBtonBuscar();

		//                                 PASO 10
		addStep("\"Dar click en \"Limpiar\"");
		dt.clickBotonLimpiar();

		//                                 PASO 11
		addStep("\"Los campos de la busqueda se limpian correctamente.");
        dt.clickFltosTdasPasoNueve();
        
		//                                 PASO 12
		addStep("\"Ingresar filtros y dar click en Buscar nuevamente.");
        dt.clickIngresaFiltrosTdaDos(nomPlazaDos,modeloDos);
        dt.clickDescripTdaDos(descripTiendaDos);
        
		//                                 PASO 13
		addStep("\"Los resultados de la busqueda son diferentes a la primera busqueda.");
		dt.clickResultadosBtonBuscar();
//        dt.clickResultadosDiferentes();

	}
 
	
	
	@Override
	public void beforeTest() {
	}

	@Override
	public String setTestDescription() {
		return "ATC_FT_098_P2_Tdas_Admdor_OXXO_Busqueda_Limpiar_Test + Perfil + Negocio";
	}

	@Override
	public String setTestDesigner() {
		return "AutomationQA";
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_098_P2_Tdas_Admdor_OXXO_Busqueda_Limpiar_Test";
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
