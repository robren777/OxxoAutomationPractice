package WEB.cometa.scripts.mvp2;


import static org.testng.Assert.assertTrue;

import java.util.HashMap;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import WEB.cometa.om.PortalCometa;
import modelo.BaseExecution;
import utils.password.PasswordUtil;
import utils.selenium.ChromeTest;
import utils.selenium.SeleniumUtil;

/**
 * Validar que los excel inviduales y masivos vengan con la region y plaza correcta asi como el orden correcto
 * MTC-FT-38-E1- Validar que al descargar el excel individual y masivo vengan con la regiï¿½n y plaza correctas y el orden correcto
 * @author Jose Zarate - 1000072516
 * @date Octubre 2024
*/
public class ATC_FT_38_E1_ValQueAlDescargarElExcelIndividualYMasivoVenganConLaRegionYPlazaCorrectasYElOrdenCorrecto extends BaseExecution {
	
	SeleniumUtil u;

	@Test(dataProvider = "data-provider")
	public void ATC_FT_38_E1_ValQueAlDescargarElExcelIndividualYMasivoVenganConLaRegionYPlazaCorrectasYElOrdenCorrecto_test (HashMap<String, String> data) throws Exception {
		testCase.setProject_Name("Portal Web: Cometa");

		/*
		 * Variables
		 ******************************************************************************************/
		String user = data.get("user");
		String password = PasswordUtil.decryptPassword(data.get("password"));
		String url = data.get("Url");

		u = new SeleniumUtil(new ChromeTest(), true);
		PortalCometa pc = new PortalCometa(u, testCase);

		/*
		 *                                             PASO 1
		 ******************************************************************************************/
		addStep("Ingresar a la liga");
		u.get(url);
		u.refresh();
		u.hardWait(3);
		testCase.addScreenShotCurrentStep(u, "Pantalla cargada");
		pc.PrivatePortal();

		/*
		 *                                             PASO 2
		 ******************************************************************************************/
		addStep("Ingresar las credenciales correspondientes al perfil de Cometa para el inicio de sesion");
		boolean resultStep2 = pc.IniciarSesionNoToken(user, password);
		if(resultStep2){
			testCase.addTextEvidenceCurrentStep("Se logro iniciar sesion correctamente");
		} else {
			testCase.addTextEvidenceCurrentStep("No se pudo iniciar sesion");
		}
		assertTrue(resultStep2, "No se pudo iniciar sesion");


		/*
		 *                                             PASO 3
		 ******************************************************************************************/
		addStep("Ingresar a Portal Cometa");
		boolean resultStep3 = pc.seleccionarPortalCometaQA();
		if(resultStep3){
			testCase.addTextEvidenceCurrentStep("Se pudo seleccionar el portal Cometa");
		} else{
			testCase.addTextEvidenceCurrentStep("No es visible el elemento de Cometa");
		}
		assertTrue(resultStep3, "No es visible el elemento de Cometa");

		/*
		 *                                             PASO 4
		 ******************************************************************************************/
		addStep("Ingresar a Comprobantes de compra y Resumen");
		boolean resultStep4 = pc.clicBotonCometaDirectos();
		if(resultStep4){
			testCase.addTextEvidenceCurrentStep("Se pudo seleccionar y entrar en Directos");
		} else {
			testCase.addTextEvidenceCurrentStep("No se encontro el elemento Directos");
		}
		assertTrue(resultStep4, "No se encontro el elemento Directos");

		boolean resultStep4_1 = pc.ClicBotonComprobantesDeCompra();
		if(resultStep4_1){
			testCase.addTextEvidenceCurrentStep("Se pudo seleccionar y entrar en Comprobantes de Compra");
		} else {
			testCase.addTextEvidenceCurrentStep("No se encontro el elemento Comprobantes de Compra");
		}
		assertTrue(resultStep4_1,"No se encontro el elemento Comprobantes de Compra");
		
		/*
		 *                                             PASO 5
		 ******************************************************************************************/
		addStep("Realiza una busqueda mediante el llenado de filtros");
		boolean resultStep5 = pc.consultarComprobantes();
		pc.seleccionarFechas(6);
		if(resultStep5){
			testCase.addTextEvidenceCurrentStep("Se realizo la consulta");
		} else {
			testCase.addTextEvidenceCurrentStep("No se pudo realizar la consulta");
		}
		assertTrue(resultStep5, "No se pudo realizar la consulta");
		
		/*
		 *                                             PASO 6
		 ******************************************************************************************/
		addStep("Ir a la parte de Descargar MASIVA de los componentes filtrados y descargar el formato de excel");
		pc.ClicExcelMasivo();
		boolean resultStep6 = pc.clicDescargaMasiva();
		if(resultStep6){
			testCase.addTextEvidenceCurrentStep("Se realiza la descarga masiva de los componentes filtrados");
		} else {
			testCase.addTextEvidenceCurrentStep("No se pudo realiza la descarga masiva de los componentes filtrados");
		}
		assertTrue(resultStep6,"No se pudo realiza la descarga masiva de los componentes filtrados");
		
		/*
		 *                                             PASO 7
		 ******************************************************************************************/
		addStep("Validar que en la descarga de los excel masivos coincidan los encabezados con lo solicitado asi como la region y plaza correctas");
		boolean resultStep7 = pc.ExcelHeaderValidatorComprobanteEntregaDetalles();
		if(resultStep7){
			testCase.addTextEvidenceCurrentStep("Los encabezados coinciden con lo solicitado");
		} else {
			testCase.addTextEvidenceCurrentStep("Los encabezados no coinciden con lo solicitado");
		}
		assertTrue(resultStep7,"Los encabezados no coinciden con lo solicitado");
		
		u.getDriver().quit();
		
	}
	
	@AfterMethod
	public void afterMethod() {
		  u.getDriver().quit();
	}

	@Override
	public void beforeTest() {
		// TODO Auto-generated method stub
		return;
		
	}

	@Override
	public String setPrerequisites() {
		// TODO Auto-generated method stub
		return "Contar con credenciales validas para el inicio de sesion de perfil DIRECTOS";
	}

	@Override
	public String setTestDescription() {
		// TODO Auto-generated method stub
		return "Validar que los excel inviduales y masivos vengan con la region y plaza correcta asi como el orden correcto:\r\n"
				+ "FA\r\n"
				+ "Proveedor\r\n"
				+ "Region\r\n"
				+ "Plaza\r\n"
				+ "CR Tienda\r\n"
				+ "Nombre de la Tienda\r\n"
				+ "Fecha\r\n"
				+ "Hora\r\n"
				+ "Fecha ADMVA\r\n"
				+ "Compras directas ! FOL- GOMA\r\n"
				+ "Orden de Compra\r\n"
				+ "Pedido Adicional \r\n"
				+ "Remision \r\n"
				+ "Tiempo de estadï¿½a\r\n"
				+ "CVE Articulo \r\n"
				+ "Descripciï¿½n\r\n"
				+ "Costo\r\n"
				+ "Precio\r\n"
				+ "UDS (unidades pedidas)\r\n"
				+ "U.COM (unidades compradas)\r\n"
				+ "VALTOT\r\n"
				+ "Total Daï¿½ados\r\n"
				+ "Total Caducos\r\n"
				+ "Total Faltantes";
	}

	@Override
	public String setTestDesigner() {
		return"Jose Zarate";
	}

	@Override
	public String setTestFullName() {
		return "";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}
}
