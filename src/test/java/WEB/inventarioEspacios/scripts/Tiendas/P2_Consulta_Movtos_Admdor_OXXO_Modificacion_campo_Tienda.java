package WEB.inventarioEspacios.scripts.Tiendas;

import static org.testng.Assert.assertFalse;

import java.util.HashMap;

import org.testng.annotations.Test;

import WEB.inventarioEspacios.om.AdmonConsultaMovtos;
import WEB.inventarioEspacios.om.Consulta_Movtos_Modificacion_campo_Tienda;
import WEB.inventarioEspacios.om.Consulta_de_Direccion_de_Envio_IEP;
import WEB.inventarioEspacios.om.MenuPrincipalIEP;
import WEB.inventarioEspacios.om.MenuPrincipalOpcionesIEP;
import WEB.om.PortalApps;
import modelo.BaseExecution; import WEB.util.GlobalVariables;
import utils.password.PasswordUtil;
import utils.selenium.MEdgeTest;
import utils.selenium.SeleniumUtil;
import utils.sql.ISQLResult;
import utils.sql.SQLResult;
import utils.sql.SQLUtil;

public class P2_Consulta_Movtos_Admdor_OXXO_Modificacion_campo_Tienda extends BaseExecution {

	SeleniumUtil u;
	@Test(dataProvider = "data-provider")
	
	public void ATC_FT_055_P2_Consulta_Movtos_Admdor_OXXO_Modif_campo_Tienda_Test(HashMap<String, String> data) throws Exception {
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
		String DescripTda = data.get("Descrip_Tienda");
		String NomTdaModif = data.get("NomTienda_Modif");
		String tipoMovto = data.get("Tipo_Movto");
		String fechaInicio = data.get("FechaInicio");
		String fechaFinal = data.get("FechaFinal");
		
		String oxxoQuery = "";
		String baraQuery = "";
		
		if (negocio.toUpperCase().equals("OXXO") || negocio.toUpperCase().equals("MARCA")) {
			
			String consulta = "select FECHA,NOMBRE_PLAZA,NOMBRE_SUBPLAZA,TIENDA_ID,NOMBRE_TIENDA,CAMPO,VALOR,XXAC_USER_ID from XXINE.XXINE_MOVIMIENTOS_SISTEMA where trunc(fecha) = trunc(Sysdate) and valor is not null and nombre_tienda = '%s'";
			oxxoQuery = String.format(consulta, NomTdaModif);
		
		}else if (negocio.toUpperCase().equals("BARA")) {
			
			String consulta = "select FECHA,NOMBRE_PLAZA,NOMBRE_SUBPLAZA,TIENDA_ID,NOMBRE_TIENDA,CAMPO,VALOR,XXAC_USER_ID from XXINE_BA.xxine_MOVIMIENTOS_SISTEMA where trunc(fecha) = trunc(Sysdate) and valor is not null and nombre_tienda = '%s'";
			baraQuery = String.format(consulta, NomTdaModif);		
		}
		
		//									PASO 1
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
		addStep("Realizar una modificacion en la pantalla correspondiente.");
		Consulta_Movtos_Modificacion_campo_Tienda mt = new Consulta_Movtos_Modificacion_campo_Tienda(u, testCase);
		mt.clicModificacionTienda(DescripTda, NomTdaModif, negocio);
		
		//								   PASO 7
		addStep("Validar en la tabla que se registre el ultimo movimiento. XXINE_CAT_MOVIMIENTOS_SISTEMA");
		System.out.println(GlobalVariables.DB_HOST_FCIASQAS_PORTAL);
		if (negocio.toUpperCase().equals("OXXO") || negocio.toUpperCase().equals("MARCA")) {
			System.out.println("Entro por Oxxo");
			System.out.println(oxxoQuery);
		
			SQLResult queryResult = executeQuery(dbFCIAS, oxxoQuery);
		
			boolean empty = ((ISQLResult) queryResult).isEmpty();

			if (empty) {
			
				assertFalse(empty, "No Se Modifico el nombre Tienda " + NomTdaModif + " de  la tabla XXINE.XXINE_MOVIMIENTOS_SISTEMA");
		
			}						
			testCase.addQueryEvidenceCurrentStep(queryResult); //Agrega la consulta tal cual que se hace, siempre y cuando solo use una sola consulta
			testCase.addTextEvidenceCurrentStep("Se Actualizo correctamente el registro, Nombre Tienda Modificado de " + DescripTda + " A " + NomTdaModif);
		}else if (negocio.toUpperCase().contentEquals("BARA")) {
			System.out.println("Entro por Bara");
			System.out.println(baraQuery);
		
			SQLResult queryResult = executeQuery(dbFCIAS, baraQuery);
		
			boolean empty = ((ISQLResult) queryResult).isEmpty();

			if (empty) {
			
				assertFalse(empty, "No Se Modifico el nombre Tienda " + NomTdaModif + " de  la tabla XXINE_BA.xxine_MOVIMIENTOS_SISTEMA");
		
			}						
			testCase.addQueryEvidenceCurrentStep(queryResult); //Agrega la consulta tal cual que se hace, siempre y cuando solo use una sola consulta
			testCase.addTextEvidenceCurrentStep("Se Actualizo correctamente el registro, Nombre Tienda Modificado de " + DescripTda + " A " + NomTdaModif);
			
		}
				
		//      						   PASO 8
		Consulta_de_Direccion_de_Envio_IEP dc = new Consulta_de_Direccion_de_Envio_IEP(u, testCase);
		addStep("Seleccionar menu Administrador : Consulta de movimientos");
		mp.clickMenus();
		dc.clickSbMenuConsultaMov(negocio);
		//dc.clickSeleccionMenuAdmon();
		
		//	  							   PASO 9	
		AdmonConsultaMovtos cm = new AdmonConsultaMovtos(u, testCase);
		addStep("Ingresar filtros y dar click en Buscar");
		u.hardWait(5);
		cm.clickIngresaFiltros(user,tipoMovto,fechaInicio,fechaFinal);

		//		                           PASO 10
		addStep("Se muestran los resultados en pantalla con los campos:\r\n"
				+ "ID\r\n"
				+ "FECHA\r\n"
				+ "NOMBRE TIPO DE MOVIMIENTO\r\n"
				+ "CVE PLAZA\r\n"
				+ "NOMBRE PLAZA\r\n"
				+ "NOMBRE SUBPLAZA\r\n"
				+ "CR TIENDA\r\n"
				+ "NOMBRE TIENDA\r\n"
				+ "DIRECCION\r\n"
				+ "ESPACIO ID\r\n"
				+ "NOMBRE ESPACIO\r\n"
				+ "ELIMINADO ID\r\n"
				+ "CAMPO\r\n"
				+ "VALOR\r\n"
				+ "USUARIO\r\n"
				+ "NOMBRE\r\n"
				+ "PATERNO\r\n"
				+ "MATERNO\r\n"
				+ "POSICION\r\n"
				+ "");
        cm.clickMuestraCamposPasoDiez();
	}

	@Override
	public void beforeTest() {
	}

	@Override
	public String setTestDescription() {
		return "ATC_FT_055_P2_Consulta_Movtos_Admdor_OXXO_Modif_campo_Tienda_Test + Perfil + Negocio";
	}

	@Override
	public String setTestDesigner() {
		return "AutomationQA";
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_055_P2_Consulta_Movtos_Admdor_OXXO_Modif_campo_Tienda_Test";
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
