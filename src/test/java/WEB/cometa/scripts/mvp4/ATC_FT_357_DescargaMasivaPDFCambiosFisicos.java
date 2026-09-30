package WEB.cometa.scripts.mvp4;

import static org.testng.Assert.assertTrue;

import java.util.HashMap;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import WEB.cometa.om.PortalCometaMVP4;
import modelo.BaseExecution;
import utils.selenium.ChromeTest;
import utils.selenium.SeleniumUtil;

/**DESCRIPCIÓN: "Visualizar una leyenda y un icono de PDF para descargar de manera masiva el detalle filtrado y pueda obtener los PDF correspondiente a los filtros aplicados al consultar."
*
*NOMBRE CASOS DE PRUEBA MANUAL QUE SE ABARCAN:
* MTC_FT_357_Validar descarga masiva en PDF en pantalla "Cambios Fisicoas"
*
*PREREQUISITOS:
*1. Credenciales para ingresar al Portal cometa, validar el formato con la liga ux
*2. Liga de Portal Cometa
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
public class ATC_FT_357_DescargaMasivaPDFCambiosFisicos extends BaseExecution { 
	SeleniumUtil u;
	 
	@Test(dataProvider = "data-provider")
	public void ATC_FT_357_DescargaMasivaPDFCambiosFisicos_test (HashMap<String, String> data) throws Exception {
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
		 * Paso 1 - Ingresar a la liga del portal con las con credenciales 
		 */
		addStep("Ingresar a la liga del portal con las con credenciales"); 
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
		 * Paso 4 - Ingresar a "Cambios Físicos" 
		 */
		addStep("Ingresar a \"Cambios Físicos\"");
		validacion = pc.opcDirectosPortalCometa(false);
		assertTrue(validacion,"No se pudo seleccionar la opción de manera correcta");
		validacion = pc.selectCambiosFisicosSE();
		assertTrue(validacion,"No se pudo seleccionar la opción de manera correcta");
		validacion = pc.screenCambiosFisicos();
		assertTrue(validacion,"No se muestra la pantalla de manera correcta");
		
		/*
		 * Paso 5- Realizar algún filtro + Rango de Fechas
		 */
		addStep("Realizar algún filtro + Rango de Fechas");
		validacion = pc.datosRegistroTipoDos();
		validacion = pc.fechasBd(true);
		assertTrue(validacion,"No se permite seleccionar correctamente");
		
		/*
		 * Paso 6 - Seleccionar el botón Consultar
		 */
		addStep("Dar clic en el botón Consultar");
		validacion = pc.opcConsultar(true);
		assertTrue(validacion,"No se permite seleccionar correctamente");
		
		/*
		 * Paso 7 - Validar que se muestre una etiqueta con el texto: ""Descargar de manera masiva los comprobantes filtrados"".
		 */
		addStep("Validar que se muestre una etiqueta con el texto: \"\"Descargar de manera masiva los comprobantes filtrados\"\".");
		validacion = pc.etiquetaDescargaMasiva();
		assertTrue(validacion,"No se muestra correctamente");
		
		/*
		 * Paso 8 - Validar que muestre un ícono de PDF 
		 */
		addStep("Validar que muestre un ícono de PDF ");
		validacion = pc.iconoPdfDescargaMasiva();
		assertTrue(validacion,"No se muestra correctamente");
		
		/*
		 * Paso 9 - Seleccionar el ícono de PDF
		 */
		addStep("Seleccionar el ícono de PDF");
		validacion = pc.clicPdfDescargaMasiva();
		validacion = pc.imgEspereUnMomento();
		assertTrue(validacion,"No se muestra correctamente");
		
		/*
		 * Paso 10 -  Validar que se extraegan correctamente los PDFs
		 */
		addStep("Validar que se extraegan correctamente los PDFs");
		u.hardWait(3);
		validacion = pc.extraerZipSimple(true);
		assertTrue(validacion,"No se pudo realizar de manera correcta");
		
		/*
		 * Paso 11 - Validar que el archivo Zip.
		 */
		addStep("Validar que el archivo Zip.");
		validacion = pc.validarInicialPDFExtra();
		assertTrue(validacion,"No se pudo realizar de manera correcta");
		
	
	
	}

	@Override
	public String setPrerequisites() {
		// TODO Auto-generated method stub
		return "1. Credenciales para ingresar al Portal cometa, validar el formato con la liga ux\n"
				+ "2. Liga de Portal Cometa";
	}

	@Override
	public String setTestDescription() {
		// TODO Auto-generated method stub
		return "Visualizar una leyenda y un icono de PDF para descargar de manera masiva el detalle filtrado y pueda obtener los PDF correspondiente a los filtros aplicados al consultar.";
	}

	@Override
	public String setTestDesigner() {
		return"Alejandro Valdez";
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_357_DescargaMasivaPDFCambiosFisicos_test";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
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
}