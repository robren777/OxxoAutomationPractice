package WEB.inventarioEspacios.scripts.Estatus.Mensajeria;

import java.util.HashMap;

import org.testng.annotations.Test;

import WEB.inventarioEspacios.om.Consulta_Movtos_Eliminacion_Estatus_mensajeria;
import WEB.inventarioEspacios.om.MenuPrincipalIEP;
import WEB.om.PortalApps;
import modelo.BaseExecution; import WEB.util.GlobalVariables;
import utils.password.PasswordUtil;
import utils.selenium.MEdgeTest;
import utils.selenium.SeleniumUtil;
import utils.sql.SQLUtil;

public class ATC_FT_234_P2_Es_Fin_Si_Estatus_mensajeria_Admdor_OXXO extends BaseExecution{
	SeleniumUtil u;
	@Test(dataProvider = "data-provider")
	
	public void ATC_FT_234_P2_Es_Fin_Si_Estatus_mensajeria_Admdor_OXXO_test(HashMap<String, String> data) throws Exception {
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
		String url = data.get("Url");
		String negocio = data.get("Negocio");
		String perfil = data.get("Perfil");
		String idEstatus = data.get("Id_Estatus");
		String tipoPedido = data.get("Tipo_Pedido");
		String Plaza = data.get("Plaza");
		String estatus = data.get("Estatus");
		String folioPedido = data.get("Folio_Pedido");
		String descripEstatus = data.get("Descrip_Estatus");
		String cambiaEstatUno = data.get("Cambia_Estat_Uno");
		String cambiaEstatDos = data.get("Cambia_Estat_Dos");
		Double numPag =   Double.parseDouble(data.get("Num_Paginas")); //convierte un Cadena String a un tipo Double, con decimal 
		//int numPag = Integer.parseInt(NumPaginas);
        int NumPagRedond =  Integer.parseInt(data.get("Num_Paginas_Redond"));
		int totElem =  Integer.parseInt(data.get("Total_Elemet")) ;
		int minimoSob =  Integer.parseInt(data.get("Minimo_Sob")) ;
		
		String oxxoQuery = "";
		String baraQuery = "";
		
		if (negocio == "Oxxo") {
			
			String consulta = "select PEDIDO_HEADER_ID,FOLIO_PEDIDO,ESTATUS,USUARIO_PEDIDO,LAST_UPDATE_DATE from XXINE.xxine_PEDIDOS_HEADER WHERE FOLIO_PEDIDO = '%s'";
			oxxoQuery = String.format(consulta, folioPedido);
			
		}else if (negocio.equals("Bara")) {
			
			String consulta = "select PEDIDO_HEADER_ID,FOLIO_PEDIDO,ESTATUS,USUARIO_PEDIDO,LAST_UPDATE_DATE from XXINE_BA.XXINE_PEDIDOS_HEADER WHERE FOLIO_PEDIDO = '%s'";
			baraQuery = String.format(consulta, folioPedido);
			
		}
		
		//									PASO 1
		addStep("Ingresar al portal https://fcportalqa.femcom.net:8443/AccessControl/pages/login_form.jsf con el <Usuario>"); //viene en excel
		//u = new SeleniumUtil(new ChromeTest(), true); //IETest para usar internet explorer
		SeleniumUtil u = new SeleniumUtil(new MEdgeTest(), true);  // para usar navegador Edge
		PortalApps pa = new PortalApps(u, testCase);  // crea objeto de locator
		System.out.println(url);
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
//		MenuPrincipalOpcionesIEP mpo = new MenuPrincipalOpcionesIEP(u, testCase);
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
		Consulta_Movtos_Eliminacion_Estatus_mensajeria em = new Consulta_Movtos_Eliminacion_Estatus_mensajeria(u, testCase);
		addStep("Seleccionar menu ->Administraci�n->Estatus Mensajeria");
		em.clickEstatusMensajeria();
		//em.MenuAdmonElimEstatusMensajeria(idEstatus);
		
		//		                           PASO 7
		addStep("Validar cual estatus mensajer�a esta en Es Fin = SI");
		System.out.println(numPag);
		if (numPag > NumPagRedond) {
			NumPagRedond = NumPagRedond + 1;
		}
		em.clickBuscaFinSi(NumPagRedond,totElem,minimoSob);
		//      						   PASO 8
		addStep("Ingresar a bandeja de pedido y seleccionar un pedido con estatus autorizado y dar clic en el folio del pedido.");
		em.clickBandejaPedidos(negocio);
		em.clickCapturaPedido(tipoPedido, Plaza, estatus);
		em.clickFolioPedido(folioPedido);
		
		//	  							   PASO 9	
		addStep("Se muestra la pantalla detalle de pedido y los datos de la gu�a");		
		em.clickValidaVentanaDetPedidoGuia();
		
		//	  							   PASO 10	
		addStep("Cambiar  a los estatus diferentes al que esta activo como Es FIN = SI");
		em.clickCambiaStatusActivo();
	
		//	  							   PASO 11	
		addStep("El sistema solo cambia estatus pero no bloque los campos ni finaliza el pedido.");
		Boolean band = true;
		em.clickCambiaEstatus(cambiaEstatUno,cambiaEstatDos,band);
		
		//	  							   PASO 12	
		addStep("Cambiar el estatus al que esta activo como Es Fin = SI");
		band = false;
		em.clickCambiaEstatus(descripEstatus,cambiaEstatDos,band);
		
		//								   PASO 13
		addStep("El sistema bloquea los campos de Gu�a (Siempre y cuando sea la ultima gu�a del pedido por finalizar) y cambia el estatus del pedido a Finalizado. Validar que en la tabla XXINE_PEDIDOS_HEADER el campo se cambie a  ESTATUS= 6.");
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
//				assertFalse(empty, "No Se Modifico el Estatus del Folio: " + folioPedido + " de  la tabla XXINE_PEDIDOS_HEADER");
//		
//			}						
//			testCase.addQueryEvidenceCurrentStep(queryResult); //Agrega la consulta tal cual que se hace, siempre y cuando solo use una sola consulta
//			testCase.addTextEvidenceCurrentStep("Se Modifico correctamente el registro, Folio Modificado: " + folioPedido);
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
//				assertFalse(empty, "No Se Modifico el Estatus del Folio: " + folioPedido + " de  la tabla XXINE_BA.XXINE_PEDIDOS_HEADER");
//		
//			}						
//			testCase.addQueryEvidenceCurrentStep(queryResult); //Agrega la consulta tal cual que se hace, siempre y cuando solo use una sola consulta
//			testCase.addTextEvidenceCurrentStep("Se Modifico correctamente el registro, Folio Modificado: " + folioPedido);
//		
//		}
	}

	@Override
	public void beforeTest() {
	}

	@Override
	public String setTestDescription() {
		return "ATC_FT_234_P2_Es_Fin_Si_Estatus_mensajer�a_Admdor_OXXO_Test + Perfil + Negocio";
	}

	@Override
	public String setTestDesigner() {
		return "AutomationQA";
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_234_P2_Es_Fin_Si_Estatus_mensajeria_Admdor_OXXO_test";
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
