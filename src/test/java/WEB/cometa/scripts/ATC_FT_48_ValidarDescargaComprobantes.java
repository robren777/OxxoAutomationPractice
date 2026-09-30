package WEB.cometa.scripts;

import static org.testng.Assert.assertTrue;

import java.util.HashMap;

import org.testng.annotations.Test;

import WEB.cometa.om.PortalCometa;
import modelo.BaseExecution;
import utils.password.PasswordUtil;
import utils.selenium.ChromeTest;
import utils.selenium.SeleniumUtil;

/**Desc: Validar que el boton de descarga de comprobantes no de la opcion de descargar los comprobantes de entrega
 * en formato pdf o Excel.
 * MTC-FT-48-E7 - Validar descarga de comprobantes
 * @author Jorge Hernandez
 * @date Julio 2024
*/

public class ATC_FT_48_ValidarDescargaComprobantes extends BaseExecution{

    SeleniumUtil u;

    @Test(dataProvider = "data-provider")
    public void ATC_FT_48_ValidarDescargaComprobantes_test(HashMap<String, String> data) throws Exception{
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
		} else {
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

		u.hardWait(4);

		/*
		 *                                             PASO 5
		 ******************************************************************************************/
        addStep("Dar click en el boton descargar resumen");
		boolean resultStep5 = pc.ClicBotonDescargar();
		if(resultStep5){
			testCase.addTextEvidenceCurrentStep("Se pudo dar click al boton para descargar el archivo");
		} else {
			testCase.addTextEvidenceCurrentStep("No se encontro el elemento para realizar la descarga");
		}
		assertTrue(resultStep5,"No se encontro el elemento para realizar la descarga");

		u.hardWait(8); //necesario para esperar a que se realice la descarga

		boolean resultStep5_1 = pc.openLastFileDownloadFolder("Excel");
		if(resultStep5_1){
			testCase.addTextEvidenceCurrentStep("Se valido la descarga y se abrio el archivo");
		} else {
			testCase.addTextEvidenceCurrentStep("No se encontro el archivo");
		}
		assertTrue(resultStep5_1, "No se encontro el archivo");

		boolean resultStep5_2 = pc.ExcelHeaderValidatorPorEstado();
		if(resultStep5_2){
			testCase.addTextEvidenceCurrentStep("Se validaron los headers de el archivo");
		} else {
			testCase.addTextEvidenceCurrentStep("Los headers no corresponden a los esperados");
		}
		assertTrue(resultStep5_2, "Los headers no corresponden a los esperados");
        u.quit();
    }

	@Override
	public void beforeTest() {
		// TODO Auto-generated method stub

	}

	@Override
	public String setPrerequisites() {
		return "1. Contar con credenciales vÃ¡lidas para el inicio de sesiÃ³n del perfil Oxxo OS-Directos\n";
	}

	@Override
	public String setTestDescription() {
		return "Validar que el boton de descarga de comprobantes no de la opcion de descargar los comprobantes de entrega en formato pdf o Excel.";
	}

	@Override
	public String setTestDesigner() {
		return "Jorge Hernandez Sanchez";
	}

	@Override
	public String setTestFullName() {
		return null;
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}

}
