package WEB.cometa.scripts.mvp4;

import static org.testng.Assert.assertTrue;

import java.util.HashMap;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import WEB.cometa.om.PortalCometaMVP4;
import modelo.BaseExecution;
import utils.selenium.ChromeTest;
import utils.selenium.SeleniumUtil;

/**DESCRIPCIÓN: Validar que las descargas masivas si correspondan a lo que el usario ve en la plataform, 
 * si se filtra 20 oc y ve en el grid que son 20 OC cuando haga la descarga masiva se deben de descargar esas 20 
*
*NOMBRE CASOS DE PRUEBA MANUAL QUE SE ABARCAN:
* MTC_FT_472_Validar descarga masiva DIRECTOS en PDF
*
*PREREQUISITOS:
1. Credenciales para el perfil DIRECTOS, Validar portal comenta
2. Información de la tabla dim. tienda (Proviene del equipo de IMA) 
*
*INSUMOS: 
*Credenciales creadas por team kyndril en soporte L2 con doble autenticación desactivada
*URL: jdbc:postgresql://oxzaspocmdbq01.postgres.database.azure.com:5432/cometa
*Liga externa de red libre https://pruebasweb.oxxo.com/AuthFemcoProx/
*
* @author Alejandro Valdez
* @date Febrero 2026
*/
public class ATC_FT_472_DescargaMasivaPDFDirectos extends BaseExecution { 
	SeleniumUtil u;
	 
	@Test(dataProvider = "data-provider")
	public void ATC_FT_472_DescargaMasivaPDFDirectos_test (HashMap<String, String> data) throws Exception {
		testCase.setProject_Name("Portal Web: Cometa");
		
		/*
		 * Variables
		 ******************************************************************************************/
		String user = data.get("user");
		u = new SeleniumUtil(new ChromeTest(), true);
		PortalCometaMVP4 pc = new PortalCometaMVP4(u, testCase); // crea objeto de locator
		
		addStep("Precondicion");
        boolean validacion = pc.ValidaBDExistanDatosFiltrosGrid(true, user);
        assertTrue(validacion,"No se obtuvieron datos en la tabla xxcm_historico_directos_view");
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
		 * Paso 2 - Ingresar credenciales correspondientes al perfil DIRECTOS usuario para inicio de sesion
		 */
		addStep("Ingresar credenciales correspondientes al perfil DIRECTOS usuario para inicio de sesion");
		validacion = pc.iniciarSesion(user, password);
		assertTrue(validacion,"No se puede iniciar sesión de manera correcta");
		u.hardWait(10);
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
		 * Paso 4 - Ingresar a Directos
		 */
		addStep("Ingresar a Directos");
		validacion = pc.opcDirectosPortalCometa(true);
		assertTrue(validacion,"No se pudo seleccionar la opción de manera correcta");
		
		/*
		 * Paso 5 - Posicionarse al icono CEDIS se despliega una lista o hacer clic sobre el menú cedis
		 */
		addStep("Posicionarse al icono CEDIS se despliega una lista o hacer clic sobre el menú cedis");
		validacion = pc.selecListaDirectos();
		assertTrue(validacion,"No se pudo seleccionar la opción de manera correcta");
		u.hardWait(2);
		validacion = pc.opcListaDirectos();
		assertTrue(validacion,"No se muestra la lista de manera correcta");
		pc.btnEscape();
		
		/*
		 * Paso 6 - Ingresar a Comprobantes de Compra
		 */
		addStep("Ingresar a Comprobantes de Compra");
		validacion = pc.opcComprobantesCompra(false);
		assertTrue(validacion,"No se pudo seleccionar la opción de manera correcta");
		validacion = pc.MensajeEspereDesaparece(false);
		assertTrue(validacion,"No se pudo validar de manera correcta");
		validacion = pc.comprobantesCompraCampos();
		assertTrue(validacion,"No se muestra la pantalla de manera correcta");
		
		/*
		 * Paso 7 - Realizar el filtro en apartado plaza
		 */
        addStep("Realizar el filtro en apartado plaza");
        validacion = pc.busquedaFiltroPlazaFechaDirectos(user, true);
        assertTrue(validacion, "No se pudo realizar la busqueda");
        
        /*
		 * Paso 8 - Validar el numero de ordenes que se muestran en la paginación
		 */
        validacion = pc.MensajeEspereDesaparece(false);
		assertTrue(validacion,"No se pudo validar de manera correcta");
        pc.scrollBott();
        addStep("Validar el numero de ordenes que se muestran en la paginación");
        validacion = pc.obtenerNumRegistros();
        assertTrue(validacion, "No se pudo realizar la busqueda");
        
        /*
		 * Paso 9 - Dar clic en el boton Descargar en PDF
		 */
        pc.scrollTop();
        addStep("Dar clic en el boton Descargar en PDF"); 
		boolean resultStep36 = pc.ClicBotonDescargarMasivoPDF(true);
		assertTrue(resultStep36,"No se encontro el elemento para realizar la descarga");
		validacion = pc.MensajeEspereDesaparece(false);
		assertTrue(validacion,"No se pudo validar de manera correcta");
		boolean resultStep43 = pc.openLastFileZIPDownloadFolder("ZIP");
		assertTrue(resultStep43,"No se encontro el archivo");
		
		/*
		 * Paso 10 - Validar la cantidad de pdf's que se muestran en la descarga masiva 
		 */
        addStep("Validar la cantidad de pdf's que se muestran en la descarga masiva");
        validacion = pc.contarArchivosDentroZip();
        assertTrue(validacion, "No se pudo realizar la busqueda");
        
        /*
		 * Paso 11 - Se compara el dato que arrojo el grid contra el numero que genero el pdf masivo 
		 */
        addStep("Se compara el dato que arrojo el grid contra el numero que genero el pdf masivo");
        validacion = pc.validarCantidadRegistrosVsZip();
        assertTrue(validacion, "No se pudo realizar la busqueda");
        
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
		return "1. Credenciales para el perfil DIRECTOS, Validar portal comenta\r\n"
				+ "2. Información de la tabla dim. tienda (Proviene del equipo de IMA) ";
	}

	@Override
	public String setTestDescription() {
		// TODO Auto-generated method stub
		return "Validar que las descargas masivas si correspondan a lo que el usario ve en la plataform, "
				+ "si se filtra 20 oc y ve en el grid que son 20 OC cuando haga la descarga masiva se deben de descargar esas 20";
	}

	@Override
	public String setTestDesigner() {
		return"Alejandro Valdez";
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_472_DescargaMasivaPDFDirectos";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}
}