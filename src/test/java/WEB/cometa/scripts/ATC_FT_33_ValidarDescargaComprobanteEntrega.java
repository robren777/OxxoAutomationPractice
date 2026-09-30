package WEB.cometa.scripts;

import static org.testng.Assert.assertTrue;

import java.util.HashMap;

import org.testng.annotations.Test;

import WEB.cometa.om.PortalCometa;
import WEB.om.MetodosExtra;
import modelo.BaseExecution;
import utils.selenium.ChromeTest;
import utils.selenium.SeleniumUtil;

/**Desc: Validar que los registros mostrados coincidan con las caracteristicas especificadas en el filtro seleccionado
* MTC-FT-32- E6- Validar descarga de Resumen de comprobantes de entrega
* @author Yair Lizarraga
* @date Junio 2024
*/
public class ATC_FT_33_ValidarDescargaComprobanteEntrega extends BaseExecution { 
	SeleniumUtil u;
	 
	@Test(dataProvider = "data-provider")
	public void ATC_FT_33_ValidarDescargaComprobanteEntrega_test (HashMap<String, String> data) throws Exception {
		testCase.setProject_Name("Portal Web: Cometa");
		
		/*
		 * Variables
		 ******************************************************************************************/
		String user = data.get("user");
		String password = data.get("password");
		u = new SeleniumUtil(new ChromeTest(), true);
		PortalCometa pc = new PortalCometa(u, testCase); // crea objeto de locator
		MetodosExtra me = new MetodosExtra(u, testCase);// crea objeto de locator
		/*
		 *                                             PASO 1
		 ******************************************************************************************/
		addStep("Ingresar las credenciales correspondientes al perfil Administrativo-Directos."); 
		u.get(data.get("Url"));
		u.refresh();
		u.hardWait(3);
		pc.PrivatePortal(); 
		pc.IniciarSesion(user, password);
		pc.ClicPortalCometa();
		
		
		/*
		 *                                             PASO 2
		 ******************************************************************************************/
		addStep("Ingresar a la pÃ¡gina de Comprobantes de Compra y Resumen.\r\n"); 
		pc.ClicBotonDirectos();
		pc.ClicBotonComprobantesDeCompra();
		
		
		/*
		 *                                             PASO 3
		 ******************************************************************************************/
		addStep("Seleccionar en el botÃ³n \"ver detalle\" de un comprobante especÃ­fico"); 
		pc.ClicSeleccionarRegsitro();
		
		/*
		 *                                             PASO 4
		 ******************************************************************************************/
		addStep("Dar click en el botÃ³n \"pdf\""); 
		pc.ClicBotonDescargarPDF();
		boolean validarDescargas = me.openLastFileDownloadFolder();
		if(validarDescargas)
			testCase.addTextEvidenceCurrentStep("La descarga se realizo correctamente");
		else
			testCase.addTextEvidenceCurrentStep("La descarga no se realizo correctamente");
		assertTrue(validarDescargas, "La descarga se realizo correctamente");
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
		return "Validar que el botÃ³n de descarga de comprobantes nos de la opciÃ³n de descargar los comprobantes de entrega en formato pdf o excel";
	}

	@Override
	public String setTestDesigner() {
		return"Yair Lizarraga";
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_33_ValidarDescargaComprobanteEntrega_test";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}
}
