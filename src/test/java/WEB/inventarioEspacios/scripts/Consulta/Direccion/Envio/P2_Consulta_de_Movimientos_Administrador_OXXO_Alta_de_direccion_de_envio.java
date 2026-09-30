package WEB.inventarioEspacios.scripts.Consulta.Direccion.Envio;


import java.util.HashMap;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import WEB.inventarioEspacios.om.AdmonConsultaMovtos;
import WEB.inventarioEspacios.om.Consulta_de_Direccion_de_Envio_IEP;
import WEB.inventarioEspacios.om.MenuPrincipalIEP;
import WEB.inventarioEspacios.om.MenuPrincipalOpcionesIEP;
import WEB.om.PortalApps;
import modelo.BaseExecution; import WEB.util.GlobalVariables;
import utils.password.PasswordUtil;
import utils.selenium.MEdgeTest;
import utils.selenium.SeleniumUtil;
import utils.sql.SQLUtil;

public class P2_Consulta_de_Movimientos_Administrador_OXXO_Alta_de_direccion_de_envio extends BaseExecution {

	SeleniumUtil u;
	@Test(dataProvider = "data-provider")
	
	public void ATC_FT_001_P2_Consulta_de_Movtos_Admdor_OXXO_Alta_de_dir_de_envio_Test(HashMap<String, String> data) throws Exception {
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
		String plaza = data.get("Plaza");
		String direcc = data.get("Nombre_Dir");
		String cp = data.get("C�digo_postal");
		String calle = data.get("Calle");
		String numero = data.get("N�mero");
		String entreCalle = data.get("Entre_Calle");
		String yEntreCalle = data.get("y_entre_Calle");
		String nomContacto = data.get("Nombre_Contacto");
		String telContacto = data.get("Tel�fono_Contacto");
		String comentario = data.get("Comentario");
		String tipoMovto = data.get("Tipo_Movto");
		String fechaInicio = data.get("FechaInicio");
		String fechaFinal = data.get("FechaFinal");
		
		String oxxoQuery = "";
		String baraQuery = "";
		
		if (negocio == "Oxxo") {
			
			String consulta = "select fecha,TIPO_MOVIMIENTO_ID,ENVIO_ID,NOMBRE_ENVIO,XXAC_USER_ID from XXINE.XXINE_MOVIMIENTOS_SISTEMA WHERE NOMBRE_ENVIO = '%s' AND trunc(fecha) = trunc(Sysdate)";
			oxxoQuery = String.format(consulta, direcc);	
			
		}else if (negocio.equals("Bara")) {
			
			String consulta = "select fecha,TIPO_MOVIMIENTO_ID,ENVIO_ID,NOMBRE_ENVIO,XXAC_USER_ID from XXINE_BA.XXINE_MOVIMIENTOS_SISTEMA WHERE NOMBRE_ENVIO = '%s' AND trunc(fecha) = trunc(Sysdate)";
			baraQuery = String.format(consulta, direcc);		
		
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
		addStep(" Verificar la informaci�n del usuario y que se muestre el negocio y el perfil.");
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
		addStep("  Validar que se muestre en el encabezado de la pantalla el Negocio, periodo y estatus del periodo.");
		mp.verificarEncabezado("Administrador");
		
		//                                 PASO 6		
		addStep("Realizar el alta en la pantalla correspondiente.");
		Consulta_de_Direccion_de_Envio_IEP dc = new Consulta_de_Direccion_de_Envio_IEP(u, testCase);
		dc.clickMenuPedidoAdmin(plaza,direcc,cp,calle,numero,entreCalle,yEntreCalle,nomContacto,telContacto,comentario,negocio);
		
		//								   PASO 7
		addStep("Validar en la tabla que se registre el ultimo movimiento. XXINE_CAT_MOVIMIENTOS_SISTEMA");
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
//				assertTrue(empty, "No se guardo la direccion de envio en la tabla XXINE_CAT_MOVIMIENTOS_SISTEMA");
//		
//			}						
//			testCase.addQueryEvidenceCurrentStep(queryResult); //Agrega la consulta tal cual que se hace, siempre y cuando solo use una sola consulta
//			testCase.addTextEvidenceCurrentStep("Se Guardo correctamente el Registro Direccion de envio en la tabla XXINE_CAT_MOVIMIENTOS_SISTEMA: ");
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
//				assertTrue(empty, "No se guardo la direccion de envio en la tabla XXINE_BA.XXINE_MOVIMIENTOS_SISTEMA");
//		
//			}						
//			testCase.addQueryEvidenceCurrentStep(queryResult); //Agrega la consulta tal cual que se hace, siempre y cuando solo use una sola consulta
//			testCase.addTextEvidenceCurrentStep("Se Guardo correctamente el Registro Direccion de envio en la tabla XXINE_CAT_MOVIMIENTOS_SISTEMA: ");
//		}
		
		//      						   PASO 8
		addStep("Seleccionar menu Administrador : Consulta de movimientos");
		dc.clickSeleccionMenuAdmon(negocio);
		
		//	  							   PASO 9	
		addStep("Ingresar filtros y dar click en Buscar");
		AdmonConsultaMovtos cm = new AdmonConsultaMovtos(u, testCase);
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
		return "ATC_FT_001_P2_Consulta_de_Movtos_Admdor_OXXO_Alta_de_dir_de_envio_Test + Perfil + Negocio";
	}

	@Override
	public String setTestDesigner() {
		return "AutomationQA";
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_001_P2_Consulta_de_Movtos_Admdor_OXXO_Alta_de_dir_de_envio_Test";
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
