package EBS.moduloCO.comisionistas;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import org.sikuli.script.Pattern;
import org.testng.annotations.Test;

import EBS.tools.LoginTool;
import EBS.util.GenericMethods;
import EBS.util.GlobalVariables;
import EBS.util.appletsMethods.MetodosSikuli;
import EBS.util.pageObjectModel.EBSPaginaInicial;
import EBS.util.patternObject.PatternUtil;
import modelo.BaseExecution;
import utils.SikuliUtil;
import utils.selenium.IETest;
import utils.selenium.SeleniumUtil;

public class ATC_FT_013_ValidarProcesoTimbradoFacturasAnticiposComisionistasPrestamoEspecial extends BaseExecution {
	
	@Test(dataProvider = "data-provider")

	public void ATC_FT_013_ValidarProcesoTimbradoFacturasAnticiposComisionistasPrestamoEspecial_test(HashMap<String, String> data)
			throws Exception {
		/*
		 * VARIABLES
		 */
		String ebsUrl = data.get("server");
		String userName = GlobalVariables.getMduenasHexEbsUsr;
		String password = GlobalVariables.getMduenasHexEbsPwd;
		String responsabilidad = data.get("responsabilidad");
		String prestamo = data.get("carpeta1");
		String ebsPattern = data.get("ebsPattern");
		String comisionista = data.get("comisionista");
		String tipoPrestamo = data.get("tipoPrestamo");
		String plazo = data.get("plazo");
		String inicioDescuento = "SEP-2023";//data.get("inicioDescuento");
		String importe = data.get("importe");
		String descripcion = data.get("descripcion");
		String text = "PR-70578";//data.get("text");
		String concurrente = data.get("concurrente");
		String responsabilidad2 = data.get("responsabilidad2");
		String fecha = "22-SEP-2023";//data.get("fechaCaptura");
		String reportes = data.get("carpeta1");
		String plantilla = data.get("plantilla");

		/*
		 * UTILERIAS
		 */
		SeleniumUtil u = new SeleniumUtil(new IETest(), true);
		LoginTool inicio = new LoginTool(u, testCase, ebsUrl, userName, password);
		EBSPaginaInicial paginaInicial = new EBSPaginaInicial(u, testCase);
		SikuliUtil sk = new SikuliUtil();
		String pathImg = sk.PatternPath("EBS\\"+ebsPattern);
		Pattern pathimg = new Pattern();
		ArrayList<String> ListImage = new ArrayList<String>();
		HashMap<String, Pattern> ImgPatter = new HashMap<String, Pattern>();
		ArrayList<Pattern> patterns = new ArrayList<Pattern>();
		File Fol = new File(pathImg);
		File Folder = new File(pathImg);
		GenericMethods action = new GenericMethods(u);

		MetodosSikuli p = new MetodosSikuli(sk, u, pathimg, ListImage, ImgPatter, patterns, Fol);

		PatternUtil patternUtil = new PatternUtil(sk, u, pathimg, ListImage, ImgPatter, patterns, Folder);

		/*
		 * PASOS DEL CASO DE PRUEBA, MATRIZ: COMISIONISTAS
		 */

		// PASO 1
		addStep("Ingresar al ambiente de EBS.");
		logger.info("EBS URL: " + ebsUrl);
		System.out.println("EBS URL: " + ebsUrl);
		logger.info("User Name: " + userName);
		System.out.println("User Name: " + userName);
		// PASO 2
		addStep("Seleccionar idioma: Espanol Latinoamericano.");
		// PASO 3
		addStep("Ingresar usuario y contrasena.");
		// PASO 4
		addStep("Dar clic en el boton de 'Conectese'.");
		inicio.login();
		// PASO 5
		addStep("Ingresar a la responsabilidad 'MXOXXO-CO10TMP-USUARIO'");
		paginaInicial.clickOnResponsabilidad(responsabilidad);
		// PASO 6
		addStep("Ingresar a la opción Prestamo-> Prestamos");
		paginaInicial.clickOnCarpeta(prestamo);
		u.hardWait(2);
		paginaInicial.clickRunPrestamoCO10CHIUsuario();
		u.hardWait(2);
		paginaInicial.clickRunPrestamosCO10CHIUsuario();
		testCase.addScreenShotCurrentStep("responsabilidad1");
		u.hardWait(25);
		patternUtil.runApplet();
		u.hardWait(25);
		testCase.addScreenShotCurrentStep("pantallaPrestamos");
		// PASO 7
		addStep("Capturar los datos necesarios, dar clic en Registrar.");
		patternUtil.enterText(comisionista);
		u.hardWait(3);
		patternUtil.nTAB(1);
		u.hardWait(3);
		patternUtil.enterText(tipoPrestamo);
		u.hardWait(3);
		patternUtil.nTAB(1);
		u.hardWait(3);
		patternUtil.enterText(plazo);
		u.hardWait(3);
		patternUtil.nTAB(1);
		u.hardWait(3);
		patternUtil.enterText(inicioDescuento);
		u.hardWait(3);
		patternUtil.nTAB(1);
		u.hardWait(3);
		patternUtil.enterText(importe);
		u.hardWait(3);
		patternUtil.nTAB(1);
		u.hardWait(3);
		patternUtil.enterText(descripcion);
		u.hardWait(3);
		patternUtil.keyAlt("c");
		patternUtil.clickImage("guardar");
		testCase.addScreenShotCurrentStep("prestamoDatos");
		patternUtil.keyAlt("r");
		testCase.addScreenShotCurrentStep("reporteDatosMsj");
		patternUtil.keyAlt("a");
		u.hardWait(3);
		/*addStep("Ingresar a la opción Tienda-> Tienda Comisionista");
		paginaInicial.clickOnCarpeta(reportes);
		paginaInicial.clickRunReportesCO10CHIUsuario();
		testCase.addScreenShotCurrentStep("responsabilidad2");
		u.hardWait(10);
		patternUtil.runApplet();
		u.hardWait(20);
		testCase.addScreenShotCurrentStep("pantallaReportes");
		// PASO 7
		addStep("Capturar los datos necesarios, dar clic en Registrar.");
		patternUtil.enterText(descripcion);
		u.hardWait(3);
		patternUtil.nTAB(1);
		u.hardWait(3);
		patternUtil.enterText(plantilla);
		u.hardWait(3);
		patternUtil.nTAB(5);
		u.hardWait(3);
		patternUtil.enterText(comisionista);
		u.hardWait(3);
		patternUtil.nTAB(3);
		u.hardWait(3);
		patternUtil.enterText(importe);
		u.hardWait(3);
		testCase.addScreenShotCurrentStep("reporteDatos");
		patternUtil.keyAlt("r");
		testCase.addScreenShotCurrentStep("reporteDatosMsj");
		patternUtil.keyAlt("a");
		u.hardWait(3);*/
		// PASO 8
		addStep("Validar se genere la factura de AP. Dirigirse al menú Ver(x) -> Solicitudes ->  Encontrar.");
		patternUtil.encontrarSolicitudes();
		testCase.addScreenShotCurrentStep("coEncontraSolicitud");
		patternUtil.clickEncontrar();
		u.hardWait(3);
		patternUtil.nDOWN(1);
		u.hardWait(2);
		patternUtil.clickVisualizarDetalles();
		u.hardWait(2);
		String idSolicitud = patternUtil.copyTextBoxRight("coParametros", 10);
		patternUtil.closeWindowInsideApplet(); //patternUtil.clickImage("aceptarBoton");
	    u.hardWait(5);
		//String idSolicitud = patternUtil.doubleClickAndCopyTextBoxRight("solicitudSeleccionada", 10);
		patternUtil.nUP(1);		
		testCase.addScreenShotCurrentStep("coSolicitud");
		// PASO 9
		addStep("Validar y contabilizar la factura generada");
		patternUtil.clickVisualizarOutput();
		u.hardWait(10);
		testCase.addScreenShotCurrentStep("coVisualizarOutput");
		ArrayList<String> values =  p.getTextContains("PR-");
		String [] a = idSolicitud.split(","); System.out.print("Id Solicitu:"+a[0]);
		assertEquals(p.getText(a[0]), a[0], "No se encontro el ID de la Solicitud");
		//assertEquals(p.getText(text), text, "No se encontro la factura");
		testCase.addBoldTextEvidenceCurrentStep("Validacion exitosa");
		patternUtil.closeWindow();
		u.hardWait(5);
		patternUtil.closeWindowInsideApplet();
		u.hardWait(1);
		patternUtil.closeWindowInsideApplet();
		u.hardWait(1);
		patternUtil.clickImage("cambiarResponsabilidad");
		assertTrue(patternUtil.validatePattern("MxoxxoApxxxxSuperUsuario"),"No se muestra la responsabilidad: "+responsabilidad2);
		patternUtil.clickImage("MxoxxoApxxxxSuperUsuario");
		testCase.addScreenShotCurrentStep("coCambioResponsabilidad1");
		patternUtil.nEnter(1);
		u.hardWait(3);
		patternUtil.keyAlt("b");
		u.hardWait(3);
		patternUtil.keyAlt("b");
		u.hardWait(3);
		patternUtil.nDOWN(1);
		testCase.addScreenShotCurrentStep("coIngresarFacturas");
		u.hardWait(3);
		patternUtil.keyAlt("b");
		u.hardWait(25);
		testCase.addScreenShotCurrentStep("coPantallaFacturas");
		patternUtil.clickImage("lamparabusqueda");
		u.hardWait(10);
		int cont = 0;
		for(String factura : values) {
			if(cont > 0) {
				u.hardWait(1);
				patternUtil.clickImage("lamparabusqueda");
				u.hardWait(10);
			}
			patternUtil.nTAB(5);
			patternUtil.enterText(factura);
	        patternUtil.clickPattern("encontrarBotonO", 10);
	        testCase.addScreenShotCurrentStep("Encontrar");
	        u.hardWait(5);
	        patternUtil.nTAB(7);
	        u.hardWait(5);
	        String numFactura = patternUtil.textCopy();
	        System.out.println("Texto arreglo " + factura); 
	        assertEquals(numFactura, factura, "La primera factura NO concuerda");
	        testCase.addScreenShotCurrentStep("factura");
	        testCase.addTextEvidenceCurrentStep("Factura " + numFactura + " encontrada");
	        cont++;
		}

		// PASO 10
		addStep("Ejecutar el proceso 'XXCO-Proceso de timbrado de facturas de anticipos de comisionistas'.");
		patternUtil.closeWindowInsideApplet();
		u.hardWait(1);
		patternUtil.closeWindowInsideApplet();
		u.hardWait(1);
		patternUtil.clickImage("cambiarResponsabilidad");
		u.hardWait(3);
		assertTrue(patternUtil.validatePattern("MxoxxoCoxxxxAdministrador"),"No se muestra la responsabilidad: "+responsabilidad2);
		patternUtil.clickImage("MxoxxoCoxxxxAdministrador");
		testCase.addScreenShotCurrentStep("coCambioResponsabilidad2");
		patternUtil.nEnter(1);
		u.hardWait(3);
		patternUtil.encontrarSolicitudes();
		testCase.addScreenShotCurrentStep("coNuevaSolicitud");
		u.hardWait(3);
		patternUtil.keyAlt("n");
		u.hardWait(3);
		patternUtil.typeAndTab(concurrente);
		u.hardWait(5);
		patternUtil.enterText(fecha);
		patternUtil.nTAB(1);
		patternUtil.enterText(fecha);
		u.hardWait(5);
		patternUtil.keyAlt("a");
		testCase.addScreenShotCurrentStep("coNuevaSolicitudDatos");
		//assertEquals(patternUtil.getTextDelNavegador("Concurrente", text), text);
		//testCase.addBoldTextEvidenceCurrentStep("Validacion exitosa");
		
		// PASO 10
		/*addStep("Ejecutar el proceso 'XXCO-Proceso de timbrado de facturas de anticipos de comisionistas'.");
		patternUtil.clickOracle();
		patternUtil.keyAlt("j");
		u.hardWait(3);
		patternUtil.typeAndTab(concurrente);
		u.hardWait(5);
		patternUtil.keyAlt("a");
		testCase.addScreenShotCurrentStep("coNuevaSolicitudDatos");*/
		addStep("Clic en el boton de Ejecutar.");
		patternUtil.clickEjecutar();
		testCase.addScreenShotCurrentStep("coNuevaSolicitudEjecutar");
		u.hardWait(3);
		patternUtil.ejecutarOtraSolicitud("No");
		testCase.addScreenShotCurrentStep("coNuevaSolicitudNo");
		u.hardWait(10);
		patternUtil.encontrarSolicitudes();	
		u.hardWait(2);
		patternUtil.keyAlt("t");
		testCase.addScreenShotCurrentStep("coEncontraNuevaSolicitud");
		patternUtil.clickEncontrar();
		u.hardWait(5);
        testCase.addScreenShotCurrentStep("coNuevaSolicitudResultado1");
        
		while(patternUtil.validatePattern("estadoNormalVerde") || patternUtil.validatePattern("estadoStandbyVerde")) {
			u.hardWait(15);
			if(patternUtil.validatePattern("ocRefrescarDatos")) {
				patternUtil.clickPattern("ocRefrescarDatos",10);
			}
		}
		    
		assertTrue(patternUtil.validatePattern("faseFinalizadoPrimeraFila") && patternUtil.validatePattern("estadoNormalPrimeraFila"));	
		testCase.addScreenShotCurrentStep("coNuevaSolicitudResultado2");
		u.hardWait(3);			    
	    /*patternUtil.closeWindow();
		u.hardWait(5);
		patternUtil.ventanaConfirmarSalir("a");
		u.close();*/
		
	}

	@Override
	public String setTestFullName() {
		return null;
	}

	@Override
	public String setTestDescription() {
		return "Verificar muestre información la siguiente información en tablas: Tablas XXCO_CFD_FACTDIG_ENC, XXCO_CFD_FACDIG_ENC_TMP REC_CODIGO_POSTAL (Valor  64480) REC_REGIMEN_FISCAL  (Valor 623) EXPORTACION  (Valor 01) Tablas XXCO_CFD_FACTDIG_LIN, XXCO_CFD_FACDIG_LIN_TMP OBJETO_IMPUESTOS  (Valor 02)";
	}

	@Override
	public String setTestDesigner() {
		return "JessicaR@hexaware.com";
	}

	@Override
	public String setTestInstanceID() {
		return null;
	}

	@Override
	public String setPrerequisites() {
		return "1.- Acceso a responsabilidad 'MXOXXO-CO10TMP-Usuario', 'MXOXXO-COXXXXX-Administrado'. 2.-  Solicitado update en Anticipos existentes ";

	}

	@Override
	public void beforeTest() {
		// TODO Auto-generated method stub

	}

}