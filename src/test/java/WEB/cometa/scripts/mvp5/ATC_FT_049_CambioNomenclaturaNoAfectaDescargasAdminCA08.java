package WEB.cometa.scripts.mvp5;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertTrue;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
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
 * DESCRIPCIÓN: Verificar que el cambio de nomenclatura en las descargas del modulo Devoluciones del Portal COMETA no afecte la cantidad ni el contenido de los comprobantes descargados en formato Excel y PDF al consultar como ADMIN CA08.
 *
 * NOMBRE CASOS DE PRUEBA MANUAL QUE SE ABARCAN:
 *     - MTC_FT_049 Verificar que el cambio de nomenclatura no afecta cantidad ni contenido de descargas como ADMIN CA08
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
 * @date 08/07/2026
 */
public class ATC_FT_049_CambioNomenclaturaNoAfectaDescargasAdminCA08 extends BaseExecution {

    private SeleniumUtil u;

    @Test(dataProvider = "data-provider")
    public void ATC_FT_049_CambioNomenclaturaNoAfectaDescargasAdminCA08_test(HashMap<String, String> data) throws Exception {
       
    	
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
			
			
			addStep("Seleccionar un rango de fechas con datos disponibles y dar clic en Consultar");
		
			validacion = devP.SelecFecha(true);
			assertTrue(validacion,"No se permite seleccionar correctamente las fechas de inicio y fin en el filtro de fecha");
			
			addStep("Dar clic en el boton Consultar");
	   	validacion = pc.opcConsultar(true);
	   	assertTrue(validacion,"El botón se encuentra deshabilitado");
			
			addStep("Registrar el total N de comprobantes visualizados en el contador del grid");
			int cantidadElementsGrid = devP.contarElementosGrid();
	
			addStep("Dar clic en 'Descargar resumen de todos los comprobantes filtrados (Excel)'");
	
			validacion = pc.etiquetaDescargaResumen();
			assertTrue(validacion,"No se muestra correctamente");
			validacion = pc.iconoExcelResumen();
			assertTrue(validacion,"No se muestra correctamente");
			validacion = pc.clicExcelResumen(false);
			validacion = pc.imgEspereUnMomento();
			validacion = pc.CerrarPopUpCentroDescargayComputoDEV(false);
			assertTrue(validacion,"No se muestra correctamente");
			
			addStep("Abrir el archivo resumen Excel y contar el numero de filas de comprobantes");
			assertTrue(pc.validarDescargaComprobanteMasivaExc1(true),"El nombre del archivo descargado no cumple con la nomenclatura esperada");
			devP.contarLineasExcelResumenDevolucion();
			
			addStep("Regresar a Devoluciones con el mismo filtro y dar clic en 'Descargar de manera masiva los comprobantes filtrados (Excel)'");
			validacion = pc.etiquetaDescargaResumen();
			assertTrue(validacion,"No se muestra correctamente");
			validacion = pc.iconoExcelResumen();
			assertTrue(validacion,"No se muestra correctamente");
			validacion = pc.clicExcelResumen(false);
			validacion = pc.imgEspereUnMomento();
			validacion = pc.CerrarPopUpCentroDescargayComputoDEV(false);
			assertTrue(validacion,"No se muestra correctamente");
			
			addStep("Abrir el concentrado Excel y contar el numero de filas de comprobantes");
			assertTrue(pc.validarDescargaComprobanteMasivaExc1(true),"El nombre del archivo descargado no cumple con la nomenclatura esperada");
	
			int contenidoExcel =devP.contarLineasExcelResumenDevolucion();
	
	   	assertEquals(contenidoExcel, cantidadElementsGrid,"El numero de elementos del Grid y registros de Excel son diferentes");
	
			addStep("Ajustar el filtro a rango de 7 dias exactos, consultar y dar clic en 'Descargar de manera masiva los comprobantes filtrados (PDF)'");
			validacion = devP.SelecFecha(true);
			assertTrue(validacion,"No se permite seleccionar correctamente las fechas de inicio y fin en el filtro de fecha");
			validacion = pc.opcConsultar(true);
	   	assertTrue(validacion,"El botón se encuentra deshabilitado");	
	   	boolean resultStep36 = pc.ClicBotonDescargarMasivoPDF(true);
			assertTrue(resultStep36,"No se encontro el elemento para realizar la descarga");    
			
			
			addStep("Localizar y abrir el PDF que contiene el CR y Folio documentados en la vista preliminar ");
			assertTrue(devP.validarDescargaComprobanteMasivaPDF(true),"El nombre del archivo descargado no cumple con la nomenclatura esperada");
			devPd.setVariablesValidacion(devP.crTiendaFV,devP.folio,devP.fecha);
			assertTrue(devPd.validaDescargaZipDevolucion(true),"Error al validar el contenido del zip");
	   	addStep("Contar la cantidad total de PDFs extraidos del ZIP");
	   	int archivosPDF = devPd.contarArchivosPdfEnZip();
	   	testCase.addTextEvidenceCurrentStep("Cantidad de archivos PDF encontrados: "+archivosPDF);
	   	assertEquals(archivosPDF, cantidadElementsGrid,"El numero de elementos del Grid y cantidad de archivos PDF en el zip, son diferentes");
			
			addStep("Seleccionar una muestra de 3 comprobantes del grid y comparar sus datos clave con los archivos descargados");
			testCase.addTextEvidenceCurrentStep("Valida primer registro del grid");
			devP.abrirVentanaDesdeGrid(1);
	  		assertTrue(devP.validarDatosGlobalesEnPdf(true),"Los valores no coinciden");
	       assertTrue(pc.ClickBtnPDF(true), "No se encontró botón PDF");
	       assertTrue(devP.validaDescargaIndividualPdf(true), "Error al validar PDF");   	 	
	       devP.cierraVistaPreliminar(); 
	       
	       testCase.addTextEvidenceCurrentStep("Valida segundo registro del grid");
			devP.abrirVentanaDesdeGrid(2);
	  		assertTrue(devP.validarDatosGlobalesEnPdf(true),"Los valores no coinciden");
	       assertTrue(pc.ClickBtnPDF(true), "No se encontró botón PDF");
	       assertTrue(devP.validaDescargaIndividualPdf(true), "Error al validar PDF");   	 	
	       devP.cierraVistaPreliminar(); 
	       
	       testCase.addTextEvidenceCurrentStep("Valida tercer registro del grid");
			devP.abrirVentanaDesdeGrid(3);
	  		assertTrue(devP.validarDatosGlobalesEnPdf(true),"Los valores no coinciden");
	       assertTrue(pc.ClickBtnPDF(true), "No se encontró botón PDF");
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
				+ "* 	- No utilizar VPN OXXO (Red libre de preferencia)\r\n"
				+ "* 	- Credenciales para el perfil DIRECTOS, Validar portal comenta";
	}

	@Override
	public String setTestDescription() {
		// TODO Auto-generated method stub
		return "Verificar que el cambio de nomenclatura en las descargas del modulo Devoluciones del Portal COMETA no afecte la cantidad ni el contenido de los comprobantes descargados en formato Excel y PDF al consultar como ADMIN CA08.";
	}

	@Override
	public String setTestDesigner() {
		// TODO Auto-generated method stub
		return "Michelle Ceja";
	}

	@Override
	public String setTestFullName() {
		// TODO Auto-generated method stub
		return "ATC_FT_049_CambioNomenclaturaNoAfectaDescargasAdminCA08_test";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}
}
