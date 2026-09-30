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
 * Validar que se tomen en cuenta las plazas y regiones correctas aun en CNT esten desordenadas 
 * MTC-FT-30-E1 - Validar en perfiles DIRECTOS que en el grid se vea la region y plaza correcta
 * prerequisistos: Contar con credenciales vÃ¡lidas para el perfil de DIRECTOS
 * @author Jose Zarate - 1000072516
 * @date Octubre 2024
*/
public class ATC_FT_30_E1_ValidarEnPerfilesDIRECTOSqueEnElGridSeVeaLaRegionYPlazaCorrecta extends BaseExecution {
	
	SeleniumUtil u;

	@Test(dataProvider = "data-provider")
	public void ATC_FT_30_E1_ValidarEnPerfilesDIRECTOSqueEnElGridSeVeaLaRegionYPlazaCorrecta_test (HashMap<String, String> data) throws Exception {
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
		 *                                             PASO 4
		 ******************************************************************************************/
		addStep("Verificar los encabezados y el orden de estos mismos");
		boolean resultStep5 = pc.validarGrid();
		if(resultStep5){
			testCase.addTextEvidenceCurrentStep("Los encabezados estan correctos y siguen el orden");
		} else {
			testCase.addTextEvidenceCurrentStep("No se los encabezados estan incorrectos o no tienen el orden");
		}
		assertTrue(resultStep5,"No se los encabezados estan incorrectos o no tienen el orden");
		
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
		return "Validar que se tomen en cuenta las plazas y regiones correctas aï¿½n en CNT estï¿½n desordenadas\r\n"
				+ "Columnas:\r\n"
				+ "\r\n"
				+ "1. FA\r\n"
				+ "\r\n"
				+ "2. Nombredel Proveedor\r\n"
				+ "\r\n"
				+ "3. Region - Venia como plaza\r\n"
				+ "\r\n"
				+ "4. Plaza - Venia como Region \r\n"
				+ "\r\n"
				+ "5. CRTienda\r\n"
				+ "\r\n"
				+ "6. Nombrede Tienda\r\n"
				+ "\r\n"
				+ "7. Fecha\r\n"
				+ "\r\n"
				+ "8. Ordende Compra\r\n"
				+ "\r\n"
				+ "9. Remision\r\n"
				+ "\r\n"
				+ "10. FolioGOMA\r\n"
				+ "\r\n"
				+ "11. FechaADMVA\r\n"
				+ "\r\n"
				+ "12. TotalGeneral a la Venta\r\n"
				+ "\r\n"
				+ "13. Cifradel Proveedor\r\n"
				+ "\r\n"
				+ "14. Movs. Valorizados\r\n"
				+ "\r\n"
				+ "15. TotalCosto\r\n"
				+ "\r\n"
				+ "";
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
