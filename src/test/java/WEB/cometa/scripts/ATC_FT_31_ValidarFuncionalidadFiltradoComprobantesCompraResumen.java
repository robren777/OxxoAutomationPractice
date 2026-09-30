package WEB.cometa.scripts;

import static org.testng.Assert.assertTrue;

import java.util.HashMap;

import org.testng.annotations.Test;

import WEB.cometa.om.PortalCometa;
import modelo.BaseExecution;
import utils.selenium.ChromeTest;
import utils.selenium.SeleniumUtil;

/**Desc: Validar que los registros mostrados coincidan con las caracterÃ­sticas especificadas en el filtro seleccionado.
* MTC-FT-31 -E6- Validar la funcionalidad de filtrado en Comprobantes de Compra Resumen
* @author Yair Lizarraga
* @date Junio 2024
*/
public class ATC_FT_31_ValidarFuncionalidadFiltradoComprobantesCompraResumen extends BaseExecution { 
	SeleniumUtil u;
	 
	@Test(dataProvider = "data-provider")
	public void ATC_FT_31_ValidarFuncionalidadFiltradoComprobantesCompraResumen_test (HashMap<String, String> data) throws Exception {
		testCase.setProject_Name("Portal Web: Cometa");
		
		/*
		 * Variables
		 ******************************************************************************************/
		String user = data.get("user");
		String password = data.get("password");
		u = new SeleniumUtil(new ChromeTest(), true);
		PortalCometa pc = new PortalCometa(u, testCase); // crea objeto de locator
		/*
		 *                                             PASO 1
		 ******************************************************************************************/
		addStep("Ingresar las credenciales correspondientes al perfil Administrativo-Directos."); 
		u.get(data.get("Url"));
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
		addStep("Seleccionar una caracterÃ­stica especÃ­fica para el filtrado de los registros\r\n"
				+ "Resultado Esperado: Los registros cambian y solo se pueden visualizar aquellos que cumplan con la caracterÃ­stica filtrada.\r\n"
				+ ""); 
		pc.SeleccionarFA();
		pc.SeleccionarFecha();
		pc.ClicBotonConsultar();
		
		/*
		 *                                             PASO4 
		 ******************************************************************************************/
		addStep("Seleccionar del filtro de calendario una fecha sin datos.\r\n"
				+ "Resultado Esperado: El portal regresa un mensaje de error pidiendo que se seleccione una fecha vÃ¡lida.\r\n"); 
		pc.SeleccionarFechaErronea();
		boolean validarMenuDirectos = pc.ClicBotonConsultar();
		if(validarMenuDirectos)
			testCase.addTextEvidenceCurrentStep("El Menu es correcto");
		else
			testCase.addTextEvidenceCurrentStep("El Menu no es correcto");
		assertTrue(validarMenuDirectos, "El Menu es correcto");
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
		return "Validar que los registros mostrados coincidan con las caracterÃ­sticas especificadas en el filtro seleccionado";
	}

	@Override
	public String setTestDesigner() {
		return"Yair Lizarraga";
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_31_ValidarFuncionalidadFiltradoComprobantesCompraResumen_test";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}
}
