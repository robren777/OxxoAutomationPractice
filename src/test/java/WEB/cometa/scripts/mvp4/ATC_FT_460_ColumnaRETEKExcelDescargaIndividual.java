package WEB.cometa.scripts.mvp4;

import static org.testng.Assert.assertTrue;

import java.util.HashMap;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import WEB.cometa.om.PortalCometaMVP4;
import modelo.BaseExecution;
import utils.selenium.ChromeTest;
import utils.selenium.SeleniumUtil;

/**DESCRIPCIÓN: "Validar que se visualiza una columna nueva llamada \"RETEK\" en el archivo Excel que se ha descargado de forma individual en Comprobante de Compra"
*
*NOMBRE CASOS DE PRUEBA MANUAL QUE SE ABARCAN:
* MTC_FT_460_Columna RETEK en archivo Excel en descarga Individual
*
*PREREQUISITOS:
*1. Credenciales con usuario   [Admin, Administrativo Operativo, Comercial] validar desde el criterio  
*2. Liga de Portal Cometa 
*3. Contar con un registro de Comprobante de Compra para su descarga
*
*INSUMOS: 
 *Contar con información disponible en tabla public.xxcm_historico_directos_view.
 *Credenciales creadas por team kyndril en soporte L2 con doble autenticación desactivada
 *URL: jdbc:postgresql://oxzaspocmdbq01.postgres.database.azure.com:5432/cometa
 *Liga externa de red libre https://pruebasweb.oxxo.com/AuthFemcoProx/
 *
* @author Alejandro Valdez
* @date Diciembre 2025
*/
public class ATC_FT_460_ColumnaRETEKExcelDescargaIndividual extends BaseExecution { 
	SeleniumUtil u;
	 
	@Test(dataProvider = "data-provider")
	public void ATC_FT_460_ColumnaRETEKExcelDescargaIndividual_test (HashMap<String, String> data) throws Exception {
		testCase.setProject_Name("Portal Web: Cometa");
		
		/*
		 * Variables
		 ******************************************************************************************/
		String user = data.get("user");
		u = new SeleniumUtil(new ChromeTest(), true);
		PortalCometaMVP4 pc = new PortalCometaMVP4(u, testCase); // crea objeto de locator
		
		addStep("Precondicion");
		boolean validacion = pc.validarYObtenerFecha(user);
        assertTrue(validacion,"No se obtuvieron datos en la tabla xxcm_historico_cedis_view");
        String password = pc.obtenerPasswordPorUsuario(user);
        assertTrue(password != null, "No se encontro password configurada para el usuario: " + user);
		
		/*
		 * Pre requisto: Contar con un registro de Comprobante de Compra para su descarga
		 */
        
        u.get(data.get("Url")); 
		u.refresh();
		u.hardWait(1);
        pc.logIntPortal(user, password);
		u.hardWait(8);
		pc.opcDirectosPortalCometa(false);
		pc.opcComprobantesCompra(false);
		u.hardWait(5);
		pc.fechasBd(false);
		pc.opcConsultar(false);
		pc.selectCompComprasReg(false);
		u.hardWait(1);
		
		/*
		 * Paso 1 - Descargar el archivo Excel de manera individual 
		 */
		addStep("Descargar el archivo Excel de manera individual ");
		validacion = pc.clicExcelDescargaIndv(true);
		assertTrue(validacion,"No se pudo seleccionar de manera correcta.");
		
		/*
		 * Paso 2 - Abrir el archivo Excel que se ha descargado
		 * Revisar que en la tabla de Excel incluya la columna RETEK
		 */
		addStep("Descargar el archivo Excel de manera individual ");
		validacion = pc.validarColumnaRetekEnExcel(true);
		assertTrue(validacion,"No se pudo seleccionar de manera correcta.");
		


	}

	@Override
	public void beforeTest() {
		// TODO Auto-generated method stub
		return;
		
	}
	@AfterMethod
    public void afterMethod(){
    	u.getDriver().quit();
    }
	@Override
	public String setPrerequisites() {
		// TODO Auto-generated method stub
		return "1. Credenciales con usuario   [Admin, Administrativo Operativo, Comercial] validar desde el criterio  \n"
				+ "2. Liga de Portal Cometa \n"
				+ "3. Contar con un registro de Comprobante de Compra para su descarga";
	}

	@Override
	public String setTestDescription() {
		// TODO Auto-generated method stub
		return "Validar que se visualiza una columna nueva llamada \"RETEK\" en el archivo Excel que se ha descargado de forma individual en Comprobante de Compra";
	}

	@Override
	public String setTestDesigner() {
		return"Alejandro Valdez";
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_460_ColumnaRETEKExcelDescargaIndividual_test";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}
}