package WEB.cometa.scripts;

import static org.testng.Assert.assertTrue;

import java.util.HashMap;

import org.testng.annotations.Test;

import WEB.cometa.om.PortalCometa;
import modelo.BaseExecution;
import utils.password.PasswordUtil;
import utils.selenium.ChromeTest;
import utils.selenium.SeleniumUtil;

/**Desc: Validar que el botÃ³n de descarga de comprobantes nos de la opciÃ³n de descargar los comprobantes de entrega en formato pdf o Excel.
* MTC-FT-59- E6- Validar descarga de comprobante de entrega â€“ Comercial.
* @author Jorge Hernandez
* @date Junio 2024
*/
public class ATC_FT_59_ValidarDescargaComprobanteEntrega extends BaseExecution{

    SeleniumUtil u;

    @Test(dataProvider = "data-provider")
    public void ATC_FT_59_ValidarDescargaComprobanteEntrega_test(HashMap<String, String> data) throws Exception{
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
        addStep("Seleccionar comprobante para ver detalles");
        boolean resultStep5 = pc.seleccionarPrimerComprobanteCompra();
		if(resultStep5){
			testCase.addTextEvidenceCurrentStep("Se selecciona el comprobante de compra");
		} else {
			testCase.addTextEvidenceCurrentStep("No se pudo seleccionar el comprobante de compra");
		}
		assertTrue(resultStep5, "No se pudo seleccionar el comprobante de compra");

        /*
		 *                                             PASO 6
		 ******************************************************************************************/
        addStep("Dar Click en el boton PDF");
        boolean resultStep_6 = pc.descargarDetallesComprobantePdf();
		if(resultStep_6){
			testCase.addTextEvidenceCurrentStep("Se realiza la descarga de el archivo");
		} else {
			testCase.addTextEvidenceCurrentStep("No se pudo realizar la descarga de el archivo");
		}
		assertTrue(resultStep_6, "No se pudo realizar la descarga de el archivo");

		 /*
		 *                                             PASO 7
		 ******************************************************************************************/
		addStep("Validar descarga archivo PDF");
		boolean resultStep7 = pc.validarDescargaComprobantePDF();
		if(resultStep7){
			testCase.addTextEvidenceCurrentStep("Se valida que el archivo se descargo");
		} else {
			testCase.addTextEvidenceCurrentStep("No se encontro el archivo");
		}
		assertTrue(resultStep7, "No se encontro el archivo");

		boolean resultStep7_1 = pc.openLastFilePDFDownloadFolder("PDF", u);
		if(resultStep7_1){
			testCase.addTextEvidenceCurrentStep("Se abre el archivo");
		} else {
			testCase.addTextEvidenceCurrentStep("No se encontro el archivo");
		}
		assertTrue(resultStep7_1, "No se encontro el archivo");

        /*
		 *                                             PASO 8
		 ******************************************************************************************/
        addStep("Dar click en el boton Excel");
        boolean resultStep_8 = pc.descargarDetallesComprobanteExcel();
		if(resultStep_8){
			testCase.addTextEvidenceCurrentStep("Se realiza la descarga de el archivo");
		} else {
			testCase.addTextEvidenceCurrentStep("No se pudo realizar la descarga de el archivo");
		}
		assertTrue(resultStep_8, "No se pudo realizar la descarga de el archivo");

		 /*
		 *                                             PASO 9
		 ******************************************************************************************/
		addStep("Validar descarga archivo Excel");
		boolean resultStep_9 = pc.validarDescargaResumen();
		if(resultStep_9){
			testCase.addTextEvidenceCurrentStep("Se valida que se descargara el archivo");
		} else {
			testCase.addTextEvidenceCurrentStep("No se encontro el archivo");
		}
		assertTrue(resultStep_9, "No se encontro el archivo");

		boolean resultStep9_1 = pc.openLastFileDownloadFolder("Excel");
		if(resultStep9_1){
			testCase.addTextEvidenceCurrentStep("Se valido la descarga y se abrio el archivo");
		} else {
			testCase.addTextEvidenceCurrentStep("No se encontro el archivo");
		}
		assertTrue(resultStep9_1, "No se encontro el archivo");

		boolean resultStep9_2 = pc.ExcelHeaderValidatorComprobanteEntregaDetalles();
		if(resultStep9_2){
			testCase.addTextEvidenceCurrentStep("Se validaron los headers de el archivo");
		} else {
			testCase.addTextEvidenceCurrentStep("Los headers no corresponden a los esperados");
		}
		assertTrue(resultStep9_2, "Los headers no corresponden a los esperados");

        u.quit();
    }

    @Override
	public void beforeTest() {

	}

	@Override
	public String setPrerequisites() {
		return "1. Contar con credenciales vÃ¡lidas para el inicio de sesiÃ³n del perfil COMERCIAL \n" +
				"de Directos.";
	}

	@Override
	public String setTestDescription() {
		return "Validar que el botÃ³n de descarga de comprobantes nos de la opciÃ³n de \n" +
				"descargar los comprobantes de entrega en formato pdf o Excel.";
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
		return null;
	}

}
