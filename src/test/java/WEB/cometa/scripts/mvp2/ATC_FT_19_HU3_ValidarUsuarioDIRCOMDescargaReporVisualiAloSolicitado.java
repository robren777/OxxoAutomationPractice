package WEB.cometa.scripts.mvp2;

import static org.testng.Assert.assertTrue;

import java.util.HashMap;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import WEB.cometa.om.PortalCometa;
import WEB.om.MetodosExtra;
import WEB.util.GlobalVariables;
import modelo.BaseExecution;
import utils.password.PasswordUtil;
import utils.selenium.ChromeTest;
import utils.selenium.SeleniumUtil;

/**Desc:Validar que como usuario DIRECTOS los reportes se visualizan con los encabezados correctos
 * Prerequisitos: Contar con credenciales vÃ¡lidas para el inicio de sesiÃ³n de perfil DIRECTOS (COMERCIAL )
* @author Yair Lizarraga
* @date Octubre 2024
*/
public class ATC_FT_19_HU3_ValidarUsuarioDIRCOMDescargaReporVisualiAloSolicitado extends BaseExecution { 
	SeleniumUtil u;
	 
	@Test(dataProvider = "data-provider")
	public void ATC_FT_19_HU3_ValidarUsuarioDIRCOMDescargaReporVisualiAloSolicitado_test (HashMap<String, String> data) throws Exception {
		testCase.setProject_Name("Portal Web: Cometa");
		
		/*
		 * Variables
		 ******************************************************************************************/
		String user = data.get("user");
		 String password = PasswordUtil.decryptPassword(data.get("password"));;
		String url = GlobalVariables.URL_PORTAL_APPS;
		String proveedor = data.get("proveedor");
		
		u = new SeleniumUtil(new ChromeTest(), true);
		PortalCometa pc = new PortalCometa(u, testCase); // crea objeto de locator
		MetodosExtra me = new MetodosExtra(u, testCase);// crea objeto de locator

		/*
		 *                                             PASO 1
		 ******************************************************************************************/
		addStep("Ingresar a la liga del portal web"); 
		u.get(data.get("Url"));
		u.refresh();
		u.hardWait(3);
		pc.PrivatePortal(); 
		
		/*
		 *                                             PASO 2
		 ******************************************************************************************/
		addStep("Ingresar las credenciales correspondinetes para el inicio de sesion"); 
		pc.IniciarSesion(user, password);
		pc.ClicPortalCometa();
		
		/*
		 *                                             PASO 3
		 ******************************************************************************************/
		addStep("Ingresar a la pagina Comprobantes de Compra"); 
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
		addStep("Dar clic sobre un registro para abrir su comprobante de compra"); 
		
		pc.ClicBotonDescargar();
		addStep("Revisar que el comprobante de compra se descargo y los encabezados se visualizan de acuerdo a los nombres solicitados"); 
		boolean validarDescargas = me.openLastFileDownloadFolder();
		if(validarDescargas)
			testCase.addTextEvidenceCurrentStep("La descarga se realizo correctamente");
		else
			testCase.addTextEvidenceCurrentStep("La descarga no se realizo correctamente");
		assertTrue(validarDescargas, "La descarga se realizo correctamente");
		/*
		 *                                             PASO 5
		 ******************************************************************************************/
		addStep("Revisar que el comprobante de compra se descargo y los encabezados se visualizan de acuerdo a los nombres solicitados"); 
		boolean validarEncabezado = pc.ExcelHeaderValidatorEncabezado();
		if(validarEncabezado)
			testCase.addTextEvidenceCurrentStep("El encabezado es correcto");
		else
			testCase.addTextEvidenceCurrentStep("El encabezado no es correcto");
		assertTrue(validarEncabezado, "El encabezado es correcto");
		
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
		return "Contar con credenciales vÃ¡lidas para el inicio de sesiÃ³n del perfil Administrativo-Directos";
	}

	@Override
	public String setTestDescription() {
		// TODO Auto-generated method stub
		return "Validar que como usuario DIRECTOS los reportes se visualizan con los encabezados correctos";
	}

	@Override
	public String setTestDesigner() {
		return"Yair Lizarraga";
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

	@AfterMethod
	public void afterMethod(){
		u.getDriver().quit();
	}
}