
package WEB.cometa.scripts.mvp5;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertTrue;
import java.io.File;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.openqa.selenium.By;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import WEB.cometa.om.PortalCometaMVP4;
import WEB.cometa.om.devolucionesPage;
import modelo.BaseExecution;
import utils.selenium.ChromeTest;
import utils.selenium.MEdgeTest;
import utils.selenium.SeleniumUtil;
import azure.annotation.AzureTestInfo;

/**
 * DESCRIPCIÓN: Validar que un usuario con perfil OXXO_OS pueda ingresar al Portal COMETA, consultar comprobantes de devoluciones en Directos y descargar de forma individual los archivos Excel y PDF verificando que la nomenclatura corresponda al formato DEVOLUCION_CR_FOLIO_DD_MM_AAAA.
 *
 * NOMBRE CASOS DE PRUEBA MANUAL QUE SE ABARCAN:
 *     - MTC_FT_035 Descargar Excel y PDF individual como OXXO_OS y verificar nomenclatura DEVOLUCION_CR_FOLIO_DD_MM_AAAA CA06
 *
PREREQUISITOS:
* 	- Validar que existan datos en la BD
* 	- No utilizar VPN OXXO (Red libre de preferencia)
* 	- Credenciales para el perfil DIRECTOS, Validar portal comenta
* INSUMOS:
*  	-Credenciales creadas por team kyndril en soporte L2 con doble autenticación desactivada
* 	-Contar con información en la tabla public.xxcm_historico_correos filtrando por la fecha más reciente con la columna "origen = directos" y "estatus = true"
* 		URL: jdbc:postgresql://oxzaspocmdbq01.postgres.database.azure.com:5432/cometa
* 	- Liga externa de red libre https://pruebasweb.oxxo.com/AuthFemcoProx/
 *
 * @author Michelle Ceja 
 * @date 07/07/2026
 */
public class ATC_FT_035_DescargaExcelPdfIndividualOXXOOSDevolucionCA06 extends BaseExecution {

    private SeleniumUtil u;

    @Test(dataProvider = "data-provider")
    public void ATC_FT_035_DescargaExcelPdfIndividualOXXOOSDevolucionCA06_test(HashMap<String, String> data) throws Exception {
    	
    	
    	testCase.setProject_Name("I22089-Evolución del proceso de recibo en Tienda Cedis | MVP5");
   	 
   	 /*
		 * Variables
		 ******************************************************************************************/
		String user = data.get("user");
		u = new SeleniumUtil(new MEdgeTest(), true);
		PortalCometaMVP4 pc = new PortalCometaMVP4(u, testCase); // crea objeto de locator
		devolucionesPage devP = new devolucionesPage (u, testCase);
		boolean validacion =  devP.obtenerRegistroUsuario(user);
        assertTrue(validacion,"No muestra información de manera correcta");
        devP.actualizaVariables();
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
		 * Paso 4 - Ingresar a la pantalla "Devoluciones"
		 */
		addStep("Ingresar a la pantalla \"Devoluciones\"");
		pc.opcDirectosPortalCometa(false);
		validacion = pc.selectDevolucionesSE();
		assertTrue(validacion,"No se pudo seleccionar la opción de manera correcta");
		validacion = pc.screenDevoluciones();
		assertTrue(validacion,"No se muestra la pantalla de manera correcta");
		
		/*
		 * Paso 5 - Seleccionar cualquier filtro + rango de fechas
		 */
		addStep("Seleccionar fecha inicio y fecha fin en el filtro de fecha");
		validacion = devP.SelecFecha(true);
		assertTrue(validacion,"No se permite seleccionar correctamente las fechas de inicio y fin en el filtro de fecha");
		
		addStep("Dar clic en el boton Consultar");
		validacion = pc.opcConsultar(true);
		assertTrue(validacion,"El botón se encuentra deshabilitado");
		
		addStep("Validar que al hacer clic en una fila del grid se abre el ticket de Devoluciones");
		assertTrue(devP.abrirTicketDesdeGridYValidarVista(true),"No se realiza clic en el registro del grid");

		 
		
		addStep("Registrar los valores exactos de CR (solo clave), Folio y Fecha visibles en pantalla");
		assertTrue(devP.validarDatosGlobalesEnPdf(true),"Los valores no coinciden");//agregar en la evidencia los datos

		addStep("Abrir el archivo Excel descargado y verificar que el contenido corresponde al comprobante visualizado");
	
	    boolean rC = pc.ClickBtnExcel(false);
       assertTrue(rC, "No se encontró botón Excel");
       assertTrue(devP.validaDescargaIndividual(),"Error al validar archivo excel");
      
		addStep("Dar clic en el icono PDF del mismo comprobante visualizado");
       assertTrue(pc.ClickBtnPDF(true), "No se encontró botón PDF");

		addStep("Verificar que el nombre del PDF descargado contiene el CR, Folio y Fecha del comprobante registrado y valida contenido");
       assertTrue(devP.validaDescargaIndividualPdf(true), "Error al validar PDF");

    	
    	
    }

	@Override
	public void beforeTest() {
		// TODO Auto-generated method stub
		
	}

	  @AfterMethod
	    public void afterMethod(){
	    	u.getDriver().quit();
	    }


	@Override
	public String setPrerequisites() {
		// TODO Auto-generated method stub
		return "- Validar que existan datos en la BD\r\n"
		+ "- No utilizar VPN OXXO (Red libre de preferencia)\r\n"
		+ "- Credenciales para el perfil DIRECTOS, Validar portal comenta";
	}


	@Override
	public String setTestDescription() {
		// TODO Auto-generated method stub
		return "Validar que un usuario con perfil OXXO_OS pueda ingresar al Portal COMETA, consultar comprobantes de devoluciones en Directos y descargar de forma individual los archivos Excel y PDF verificando que la nomenclatura corresponda al formato DEVOLUCION_CR_FOLIO_DD_MM_AAAA.";
	}

	@Override
	public String setTestDesigner() {
		// TODO Auto-generated method stub
		return "Michelle Ceja";
	}

	@Override
	public String setTestFullName() {
		// TODO Auto-generated method stub
		return "ATC_FT_035_DescargaExcelPdfIndividualOXXOOSDevolucionCA06_test";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}
}
