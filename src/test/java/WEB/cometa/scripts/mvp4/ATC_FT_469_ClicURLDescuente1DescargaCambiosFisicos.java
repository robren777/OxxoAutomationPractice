package WEB.cometa.scripts.mvp4;

import static org.testng.Assert.assertTrue;

import java.util.HashMap;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import WEB.cometa.om.PortalCometaMVP4;
import modelo.BaseExecution;
import utils.selenium.ChromeTest;
import utils.selenium.SeleniumUtil;

/**DESCRIPCIÓN: Validar que al dar click en descargar a un documento correspondiente de Cambios Físicos, se descuente la descarga finalizada del botón notificación
*
*NOMBRE CASOS DE PRUEBA MANUAL QUE SE ABARCAN:
* MTC_FT_469 Validar que al dar click en la URL se descuente 1 descarga en descargas finalizadas en Cambios Físicos
*
*PREREQUISITOS:
1. Credenciales para el perfil DIRECTOS, con usuario  [Admin, OXXO OS, OXXO Plaza, Administrativo Operativo, Comercial y CEDIS] 
2. Liga de Portal Cometa
3. Contar con un documento de origen Cambios Físicos pendiente de descarga en el centro de descargas
*
*INSUMOS: 
Existen datos en la tabla xxcm_cf

* @author Jose Pecina
* @date Febrero 2026
*/
public class ATC_FT_469_ClicURLDescuente1DescargaCambiosFisicos extends BaseExecution { 
	SeleniumUtil u;
	 
	@Test(dataProvider = "data-provider")
	public void ATC_FT_469_ClicURLDescuente1DescargaCambiosFisicos_test (HashMap<String, String> data) throws Exception {
testCase.setProject_Name("Portal Web: Cometa");
		
		/*
		 * Variables
		 ******************************************************************************************/
		String user = data.get("user");
		u = new SeleniumUtil(new ChromeTest(), true);
		PortalCometaMVP4 pc = new PortalCometaMVP4(u, testCase); // crea objeto de locator
		
		/*
		 * Pre requisto: Crear un registro con estatus Fallido
		 */
		String password = pc.obtenerPasswordPorUsuario(user);
        assertTrue(password != null, "No se encontro password configurada para el usuario: " + user);
        u.get(data.get("Url")); 
		u.refresh();
		u.hardWait(1);
        pc.logIntPortal(user, password);
		u.hardWait(8);
		pc.opcDirectosPortalCometa(false);
		pc.selectCambiosFisicosSE();
		pc.MensajeEspereDesaparece(false);
		u.hardWait(5);
		pc.SelecFechaCFMenosMil(user, false);
		pc.opcConsultar(false);
		pc.MensajeEspereDesaparece(false);
		pc.clicExcelResumen(false);
		pc.MensajeEspereDesaparece(false);
		u.hardWait(2);
		pc.btnEscape();
		u.hardWait(3);
		pc.logoutPC(false);
		u.hardWait(4);
	
		/*
		 * Paso 1 - Ingresar a la liga del portal
		 */
		addStep("Ingresar a la liga del portal"); 
		u.hardWait(5);
		boolean validacion = pc.loginScreen();
		assertTrue(validacion,"No se muestra la pantalla de manera correcta");
		
		/*
		 * Paso 2 - Ingresar credenciales correspondientes al perfil DIRECTOS usuario para inicio de sesion
		 */
		addStep("Ingresar credenciales correspondientes al perfil DIRECTOS usuario para inicio de sesion");
		validacion = pc.iniciarSesion(user, password);
		assertTrue(validacion,"No se puede iniciar sesion de manera correcta");
		u.hardWait(10);
		validacion = pc.accessControlScreenSE();
		assertTrue(validacion,"No se muestra la pantalla de manera correcta");
		
		/*
		 * Paso 3 - Ingresar al portal Cometa desde access control
		 */
		addStep("Ingresar al portal Cometa desde access control");
		validacion = pc.ingPortalCometa();
		assertTrue(validacion,"No se pudo seleccionar la opcion de manera correcta");
		u.hardWait(8);
		validacion = pc.portalCometaScDirectosComercialSE();
		assertTrue(validacion,"No se muestra la pantalla de manera correcta");
		
		/*
		 * Paso 4 - Se da clic en meno centro de descarga
		 */
		addStep("Se da clic en meno centro de descarga");
		validacion = pc.opcCentroDescargas(true);
		assertTrue(validacion,"No se pudo seleccionar la opcion de manera correcta");
		validacion = pc.scCentroDeDescargas();
		assertTrue(validacion,"No se muestra la pantalla de manera correcta");
		
		/*
		 * Paso 5 - Seleccionar rango de fechas
		 */
		addStep("Seleccionar rango de fechas");
		u.hardWait(2);
		validacion = pc.FiltroRangoFechasCentroDescarga(1,true);
		assertTrue(validacion,"No se pudo seleccionar la opcion de manera correcta");
		pc.opcConsultar(false);
		pc.contDescargas(false);
		
		/*
		 * Paso 6 - Dar click en URL en el campo descarga
		 */
        assertTrue(validacion,"No se obtuvieron datos en la tabla xxcm_centro_descargas");
		addStep("Dar click en URL en el campo descarga");
		validacion = pc.selUrlDescarga(true);
		assertTrue(validacion,"No se pudo seleccionar la opcion de manera correcta");
		
		/*
		 * Paso 7 - Dar click en boton Inicio Externo
		 */
		addStep("Dar click en boton Inicio Externo");
		validacion = pc.opcInicioExterno(true);
		assertTrue(validacion,"No se pudo seleccionar la opcion de manera correcta");
		
		/*
		 * Paso 8 - Dar click en el icono de campana (Notificaciones)
		 */
		addStep("Dar click en el icono de campana (Notificaciones)");
		validacion = pc.btnBell(true);
		assertTrue(validacion,"No se pudo seleccionar la opcion de manera correcta");
		
		/*
		 * Paso 10 - Se valida la cantidad de descargas finalizadas
		 */
		addStep("Se valida la cantidad de descargas finalizadas");
		validacion = pc.contDescargasDescontandoFinalizada(true);
		assertTrue(validacion,"No se pudo validar de manera correcta");
		
		/*
		 * Finalizar el proceso
		 */
		pc.eliminarUltimoArchivoDescargado();
		u.hardWait(1);
		u.quit();  


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
		return "1. Credenciales para el perfil DIRECTOS, con usuario  [Admin, OXXO OS, OXXO Plaza, Administrativo Operativo, Comercial y CEDIS] \r\n"
				+ "2. Liga de Portal Cometa\r\n"
				+ "3. Contar con un documento de origen Cambios Físicos pendiente de descarga en el centro de descargas";
	}

	@Override
	public String setTestDescription() {
		// TODO Auto-generated method stub
		return "Validar que al dar click en descargar a un documento correspondiente de Cambios Físicos, se descuente la descarga finalizada del botón notificación";
	}

	@Override
	public String setTestDesigner() {
		return"Jose Pecina";
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_469_ClicURLDescuente1DescargaCambiosFisicos_test";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}
}