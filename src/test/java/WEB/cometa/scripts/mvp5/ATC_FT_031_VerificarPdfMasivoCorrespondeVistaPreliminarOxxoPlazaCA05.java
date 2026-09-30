// file: cometa/om/DevolucionesDescargaMasivaPdfPage.java

package WEB.cometa.scripts.mvp5;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertTrue;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.file.Files;
import java.text.Normalizer;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.openqa.selenium.WebElement;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import WEB.cometa.om.PortalCometaMVP4;
import WEB.cometa.om.devolucionesPage;
import WEB.cometa.om.devolucionesPageDescargas;
import modelo.BaseExecution;
import utils.selenium.ChromeTest;
import utils.selenium.MEdgeTest;
import utils.selenium.SeleniumUtil;
import azure.annotation.AzureTestInfo;

/**
 * DESCRIPCIÓN: Verificar que el PDF masivo descargado en el modulo Devoluciones del Portal COMETA corresponde al comprobante mostrado en la vista preliminar para el perfil OXXO_PLAZA CA05, validando que la nomenclatura del archivo 
 * y la informacion del comprobante coincidan exactamente en CR Tienda, Folio, Fecha y demas campos visibles.
 *
 * NOMBRE CASOS DE PRUEBA MANUAL QUE SE ABARCAN:
 *     - MTC_FT_031 Verificar que el PDF masivo descargado corresponde al comprobante de la vista preliminar como OXXO_PLAZA CA05
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
public class ATC_FT_031_VerificarPdfMasivoCorrespondeVistaPreliminarOxxoPlazaCA05 extends BaseExecution {

    private SeleniumUtil u;

    @Test(dataProvider = "data-provider")
    public void ATC_FT_031_VerificarPdfMasivoCorrespondeVistaPreliminarOxxoPlazaCA05_test(HashMap<String, String> data) throws Exception {
    	
 testCase.setProject_Name("I22089-Evolución del proceso de recibo en Tienda Cedis | MVP5");
    	 
 /*
	 * Variables
	 ******************************************************************************************/
	String user = data.get("user");
	u = new SeleniumUtil(new MEdgeTest(), true);
	PortalCometaMVP4 pc = new PortalCometaMVP4(u, testCase); // crea objeto de locator
	devolucionesPage devP = new devolucionesPage (u, testCase);
	devolucionesPageDescargas devPd = new devolucionesPageDescargas(u, testCase);
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
	devP.validarDatosGlobalesEnPdf(true);
	addStep("Cerrar la vista preliminar del comprobante");
	devP.cierraVistaPreliminar(); 
	
	addStep("Dar clic en el boton 'Descargar de manera masiva los comprobantes filtrados (PDF)'");

	validacion = pc.etiquetaDescargaMasiva();
	pc.ClicBotonDescargarMasivoPDF(true);
	
	addStep("Ir al Centro de Descarga, filtrar por fecha de la ejecucion y descargar el archivo ZIP generado");
	
	boolean resultStep37 = pc.ClicBotonCentroDescarga(true);
	assertTrue(resultStep37,"No se pudo dar click en botón descarga");

     addStep("Seleccionar rango de fechas");
     boolean resultStep10 = pc.FiltroFechaCentroDescarga(true);
	assertTrue(resultStep10,"No se pudo seleccionar fecha");

    addStep("Se valida que se registró la última descarga realizada");
    boolean resultStep11 = pc.validarUltimaDescargaRegistrada(true);
    assertTrue(resultStep11,"No se obtuvo la última descarga realizada");
	
	
	addStep("Descomprimir el ZIP ");
	devP.descargaUltimoZip(true);
	addStep("Localizar y abrir el PDF que contiene el CR y Folio documentados en la vista preliminar ");
	assertTrue(devP.validarDescargaComprobanteMasivaPDF(true),"El nombre del archivo descargado no cumple con la nomenclatura esperada");
	devPd.setVariablesValidacion(devP.crTiendaFV,devP.folio,devP.fecha);
	assertTrue(devPd.validaDescargaZipDevolucion(true),"Error al validar el contenido del zip");
				
	
	 
		
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
				+ "* 	- No utilizar VPN OXXO (Red libre de preferencia)\r\n"
				+ "* 	- Credenciales para el perfil DIRECTOS, Validar portal comenta";
	}

	@Override
	public String setTestDescription() {
		// TODO Auto-generated method stub
		return "Verificar que el PDF masivo descargado en el modulo Devoluciones del Portal COMETA corresponde al comprobante mostrado en la vista preliminar para el perfil OXXO_PLAZA CA05, validando que la nomenclatura del archivo y la informacion del comprobante coincidan exactamente en CR Tienda, Folio, Fecha y demas campos visibles.";
	}

	@Override
	public String setTestDesigner() {
		// TODO Auto-generated method stub
		return "Michelle Ceja";
	}

	@Override
	public String setTestFullName() {
		// TODO Auto-generated method stub
		return "ATC_FT_031_VerificarPdfMasivoCorrespondeVistaPreliminarOxxoPlazaCA05_test";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}



   
}
