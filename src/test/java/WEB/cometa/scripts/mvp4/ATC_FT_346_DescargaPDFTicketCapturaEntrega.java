package WEB.cometa.scripts.mvp4;

import static org.testng.Assert.assertTrue;

import java.util.HashMap;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import WEB.cometa.om.PortalCometaMVP4;
import modelo.BaseExecution;
import utils.selenium.ChromeTest;
import utils.selenium.SeleniumUtil;

/**DESCRIPCIÓN: "Validar que al hacer clic en el ícono de PDF dentro del ticket Captura / entrega en la pantalla de \"Cambios físicos\", se descargue un archivo PDF que sea visualmente idéntico a lo que se muestra en pantalla"
*
*NOMBRE CASOS DE PRUEBA MANUAL QUE SE ABARCAN:
* MTC_FT_346_Validar Descarga de archivo PDF dentro del ticket Captura / Entrega con usuarios Externos
*
*PREREQUISITOS:
*1. Credenciales para el perfil   Admin, OXXO OS, OXXO Plaza, Administrativo Operativo y Comercial
*2. Acceso a Portal Comenta
*3. Acceso a la pantalla "Cambios Fisicos"
*
*INSUMOS: 
 *Contar con información disponible en tabla xxcm_historico_directos_cf_view.
 *Credenciales creadas por team kyndril en soporte L2 con doble autenticación desactivada
 *URL: jdbc:postgresql://oxzaspocmdbq01.postgres.database.azure.com:5432/cometa
 *Liga externa de red libre https://pruebasweb.oxxo.com/AuthFemcoProx/
 *
* @author Alejandro Valdez
* @date Septiembre 2025
*/
public class ATC_FT_346_DescargaPDFTicketCapturaEntrega extends BaseExecution { 
	SeleniumUtil u;
	 
	@Test(dataProvider = "data-provider")
	public void ATC_FT_346_DescargaPDFTicketCapturaEntrega_test (HashMap<String, String> data) throws Exception {
		testCase.setProject_Name("Portal Web: Cometa");
		
		/*
		 * Variables
		 ******************************************************************************************/
		String user = data.get("user");
		u = new SeleniumUtil(new ChromeTest(), true);
		PortalCometaMVP4 pc = new PortalCometaMVP4(u, testCase); // crea objeto de locator
		
		addStep("Precondicion");
		boolean validacion = pc.obtenerRegistroTipo2(true);
        assertTrue(validacion,"No se obtuvieron datos en la tabla");
        String password = pc.obtenerPasswordPorUsuario(user);
        assertTrue(password != null, "No se encontro password configurada para el usuario: " + user);
		 
        /*
		 * Paso 1 - Ingresar a la liga del portal 
		 */
		addStep("Ingresar a la liga del portal"); 
		u.get(data.get("Url")); 
		u.refresh();
		u.hardWait(1);
		validacion = pc.loginScreen();
		assertTrue(validacion,"No se muestra la pantalla de manera correcta");
		
		/*
		 * Paso 2 - Ingresar credenciales correspondientes al perfil de Cometa para inicio de sesion
		 */
		addStep("Ingresar credenciales correspondientes al perfil de Cometa para inicio de sesion");
		validacion = pc.iniciarSesion(user, password);
		assertTrue(validacion,"No se puede iniciar sesión de manera correcta");
		u.hardWait(12);
		validacion = pc.accessControlScreenSE();
		assertTrue(validacion,"No se muestra la pantalla de manera correcta");
		
		/*
		 * Paso 3 - Ingresar al portal Cometa desde access control
		 */
		addStep("Ingresar al portal Cometa desde access control");
		validacion = pc.ingPortalCometa();
		assertTrue(validacion,"No se pudo seleccionar la opción de manera correcta");
		u.hardWait(8);
		validacion = pc.portalCometaScDirectosComercialSE();
		assertTrue(validacion,"No se muestra la pantalla de manera correcta"); 
		
		/*
		 * Paso 4 - Ingresar a DIRECTOS
		 */
		addStep("Ingresar a DIRECTOS");
		validacion = pc.opcDirectosPortalCometa(false);
		assertTrue(validacion,"No se pudo seleccionar la opción de manera correcta");
		validacion = pc.directosPortalCometaSc();
		assertTrue(validacion,"No se muestra la pantalla de manera correcta");
		
		/*
		 * Paso 5 - Ingresar a "Cambios Físicos" 
		 */
		addStep("Ingresar a \"Cambios Físicos\"");
		validacion = pc.selectCambiosFisicosSE();
		assertTrue(validacion,"No se pudo seleccionar la opción de manera correcta");
		validacion = pc.screenCambiosFisicos();
		assertTrue(validacion,"No se muestra la pantalla de manera correcta");
		
		/*
		 * Paso 6- Realizar algún filtro + Rango de Fechas
		 */
		addStep("Realizar algún filtro + Rango de Fechas");
		validacion = pc.datosRegistroTipoDos();
		validacion = pc.fechasBd(true);
		assertTrue(validacion,"No se permite seleccionar correctamente");
		
		/*
		 * Paso 7 - Seleccionar el botón Consultar
		 */
		addStep("Dar clic en el botón Consultar");
		validacion = pc.opcConsultar(true);
		assertTrue(validacion,"No se permite seleccionar correctamente");
		
		/*
		 * Paso 8 - Seleccionar una fila del grid
		 */
		addStep("Seleccionar un ticket de modificacion");
		validacion = pc.selectRegTipoDosCambiosFisicos(true);
		assertTrue(validacion,"No se permite seleccionar correctamente");
		
		/*
		 * Paso 9 - Validar que muestre el texto "Descarga Comprobante en Formato" 
		 */
		addStep("Validar que muestre el texto \"Descarga Comprobante en Formato\" ");
		validacion = pc.valLabelDescargaComprobanteEnFormato();
		assertTrue(validacion,"No se pudo validar correctamente");
		
		/*
		 * Paso 10 - Seleccionar el Icono PDF
		 */
		addStep("Seleccionar el Icono PDF");
		validacion = pc.clicPDFDescargaIndv();
		assertTrue(validacion,"No se muestra correctamente");
		
		/*
		 * Paso 11 - Validar el nombre del archivo 
		 */
		addStep("Validar el nombre del archivo");
		validacion = pc.validarNombreDescIndvPDF();
		assertTrue(validacion,"No se muestra correctamente");
		
		/*
		 * Paso 12 - Validar contenido del PDF descargado 
		 */
		addStep("Validar contenido del PDF descargado");
		validacion = pc.validarCamposEnPDFConFiltros();
		assertTrue(validacion,"No se muestra correctamente");
		

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
		return "1. Credenciales para el perfil   Admin, OXXO OS, OXXO Plaza, Administrativo Operativo y Comercial\n"
				+ "2. Acceso a Portal Comenta\n"
				+ "3. Acceso a la pantalla \"Cambios Fisicos\"";
	}

	@Override
	public String setTestDescription() {
		// TODO Auto-generated method stub
		return "Validar que al hacer clic en el ícono de PDF dentro del ticket Captura / entrega en la pantalla de \"Cambios físicos\", se descargue un archivo PDF que sea visualmente idéntico a lo que se muestra en pantalla";
	}

	@Override
	public String setTestDesigner() {
		return"Alejandro Valdez";
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_346_DescargaPDFTicketCapturaEntrega_test";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}
}