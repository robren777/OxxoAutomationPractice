package WEB.cartaPorte.cartaPorteCHG0045946;

import static org.junit.Assert.assertTrue;
import static org.testng.Assert.assertEquals;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import org.sikuli.hotkey.Keys;
import org.sikuli.script.FindFailed;
import org.sikuli.script.Pattern;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import WEB.cartaPorte.objectModelPortalCP.CartaPorteOC;
import WEB.cartaPorte.objectModelPortalCP.LoginCP;
import WEB.cartaPorte.objectModelPortalCP.MenuPortal;
import WEB.cartaPorte.objectModelPortalCP.PortalCartaPorte;
import WEB.estandarizacionHomologacionImpuestos.pageObjectModelHomo.MetodoGeneral;
import WEB.util.MetodosSikuli;
import modelo.BaseExecution;
import utils.SikuliUtil;
import utils.password.PasswordUtil;
import utils.selenium.ChromeTest;
import utils.selenium.SeleniumUtil;

public class ATC_FT_006_GenerarCartaPorteOC31_CartaPorte3 extends BaseExecution {
	SeleniumUtil u; 
	/*
	 *El objetivo general del proyecto es generar carta porte para validar que la documentación se genera con los
	 * correspondientes QRs
	 * 		Carta Porte CEDIS
	 * 		Carta Porte Manual
	 * 		Carta Porte OC
	 *   
	*/
	@Test(dataProvider = "data-provider")
	public void ATC_FT_006_GenerarCartaPorteOC31_CartaPorte3_test(HashMap<String, String> data) throws FindFailed {
		/*
		 * VARIABLES
		 * 
		 * portalUrl = Url para acceder al portal WEB carta porte
		 * userName = usuario de acceso
		 * password = contraseña encriptada para acceder a la aplicación
		 * tipoCartaPorte = cedis, manual u oc
		 * origen = CEDIS o PT origen
		 * destino = CEDIS o PT destino
		 * ordenCompra = numero de Orden de compra
		 * nombreChofer= nombre del chofer, relacionado con el CEDIS o PT origen capturado
		 * numeroEconomico = numeroEconomico de transporte relacionado con el nombre del Chofer
		 * correo = correo adicional para enviar los documentos
		 * campo= descripcion que se muestra en la  de los testcase
		 * pesoBruto = vacio para enviar y modificar el valor en los casos que sea 0, o vacio para solo para encontrar el elemento.
		 * registroIstmo= SI para seleccionar la casilla No para no seleccionar
		 * poloOrigen = Origen que pertenezca a la zona Istmo
		 * poloDestino = Destino que pertenezca a la zona Istmo
		 * logisticaInversa= Si para seleccionar y no para no seleccionar
	    */
		String portalurl = data.get("server");
		String userName=data.get("user");
		String password = PasswordUtil.decryptPassword(data.get("password"));
		String tipoCartaPorte = data.get("tipoCartaPorte");
		String pac = data.get("pac");
		String campo=data.get("campo"); 
		String origen=data.get("origen");
		String destino = data.get("destino");
		String ordenCompra = data.get("ordenCompra");
		String nombreChofer = data.get("nombreChofer");
		String numeroEconomico = data.get("numeroEconomico");
		String correo = data.get("correo");
		String rutaGuardarTira = data.get("rutaGuardarTira");
		testCase.setTest_Description("");
		testCase.setTest_Description(campo+" . PAC de prueba: "+pac);

		String logisticaInversa = data.get("logisticaInversa");
		
		SeleniumUtil u=new SeleniumUtil(new ChromeTest(), true);
		SikuliUtil sk = new SikuliUtil();
		String pathImg = sk.PatternPath("WEB\\"+"CARTAPORTEW");
		Pattern pathimg = new Pattern();
		ArrayList<String> ListImage = new ArrayList<String>();
		HashMap<String, Pattern> ImgPatter = new HashMap<String, Pattern>();
		ArrayList<Pattern> patterns = new ArrayList<Pattern>();
		File Fol = new File(pathImg);
		MetodosSikuli p = new MetodosSikuli(sk, u, pathimg, ListImage, ImgPatter, patterns, Fol);
		testCase.setTest_Description("");
		testCase.setTest_Description(campo+" . PAC de prueba: "+pac);
		
		String mensajeCaptura= "No fue posible capturar datos ";
		String mensajeEncontrar= "No fue posible encontrar elemento: ";
		String mensajeSeleccion= "No fue posible seleccionar elemento: ";
		
		LoginCP login = new LoginCP(u, testCase, portalurl);
		PortalCartaPorte portalCP= new PortalCartaPorte(u, testCase);
		CartaPorteOC portalOC= new CartaPorteOC(u, testCase, portalCP);
		MenuPortal portalmenu= new MenuPortal(u, testCase);
		MetodoGeneral metodoGeneral= new MetodoGeneral();
		Date fechaInicial= new Date();
		//PASO 1:
		addStep("Ingresar a la liga de acceso: https://fcportalqa.femcom.net:8443/AccessControl/pages/login_form.jsf");
		u.get(login.getUrl());
		login.proceedToPortal();
		boolean cargarPagina=login.encontrarMensajeInicio();
		testCase.addScreenShotCurrentStep("CargarURL");
		assertTrue( "El sitio no fue cargado satisfactoriamente",cargarPagina);
				
		//PASO 2: 
		addStep("Capturar credenciales de acceso para iniciar sesion");
		login.cicloLogin2(userName, password);
		String mensajePortal=portalmenu.accesoMenuPortal(userName, password);
		assertTrue("No se encontró mensaje: Portal de Aplicaciones", mensajePortal.compareTo("Portal de Aplicaciones")==0);
		
		//PASO 3: 
		addStep("Seleccionar \"Portal Carta Porte\"");
		portalmenu.seleccionarOpcionCartaPorte();
		boolean encontrarMenuCP=portalCP.encontrarMenuOpcionesCartaPorte();
		assertTrue("No se encontro Menu de Carta Porte", encontrarMenuCP);
		
		/*
		 * PASO 11:
		 * Descripción del paso: Seleccionar menú "Carta Porte"
		 * 
		 * Resultado Esperado: Encontrar opción "Carta Porte OC"
		 */	
		addStep("Seleccionar Menu de Carta Porte");
		u.hardWait(5);
		String encontrarOpcionMenuCP=portalCP.encontrarOpcionesCartaPorte(tipoCartaPorte);
		assertEquals(encontrarOpcionMenuCP, "Carta Porte OC", "No se encontro la opcion: Carta Porte OC");
		
		
		/*
		 * PASO 12:
		 * Descripción del paso: Seleccionar opción "Carta Porte OC"
		 * 
		 * Resultado Esperado: Encontrar texto "Carta Porte OC"
		 */	
		addStep("Seleccionar opcion \"Carta Porte OC\"");
		String seleccionarCPOC=portalCP.seleccionarOpcionCartaPorte(tipoCartaPorte);
		assertEquals(seleccionarCPOC, "Carta Porte OC", "No se encontro el texto: Carta Porte OC");
		
		/*
		 * PASO 13:
		 * Descripción del paso: Capturar campo: Origen
		 * 
		 * Resultado Esperado: Encontrar casilla: "Destino"
		 */	
		addStep("Capturar Origen: "+origen);
		String campoActual="origen";
		boolean encontrarCasilla= portalOC.capturarCasilla(origen, campoActual,true,true);
		assertTrue(mensajeCaptura+campoActual, encontrarCasilla);
		campoActual="destino";
		encontrarCasilla=portalOC.encontrarElemento(campoActual);
		assertTrue(mensajeEncontrar+campoActual
		, encontrarCasilla);
		/*
		 * PASO 14:
		 * Descripción del paso: Capturar campo: Destino
		 * 
		 * Resultado Esperado: Encontrar casilla: "OC"
		 */		
		addStep("Capturar campo Destino; "+destino);
		campoActual="destino";
		encontrarCasilla= portalOC.capturarCasilla(destino, campoActual,true,true);
		assertTrue(mensajeCaptura+campoActual, encontrarCasilla);
		campoActual="ordenCompra";
		encontrarCasilla=portalOC.encontrarElemento(campoActual);
		assertTrue(mensajeEncontrar+campoActual, encontrarCasilla);
		
		addStep("Se captura la inforacion de ordenes de compra: "+ordenCompra.substring(0,(ordenCompra.length()-1)));
		int contador=0;
		// arreglo de ordenes de compra separadas por una ,
		String ordenesCompras[]=ordenCompra.split(",");
		
		do {
			
				/*
				 * PASO 15:
				 * Descripción del paso: Capturar campo: Orden Compra
				 * 
				 * Resultado Esperado: Habilitar botón Buscar
				 */	
			
			campoActual="ordenCompra";
			encontrarCasilla= portalOC.capturarCasilla(ordenesCompras[contador], campoActual,false,false);
			assertTrue(mensajeCaptura+campoActual, encontrarCasilla);
			campoActual="botonBuscar";
			//portalOC.buscarYQuitarAlerta();
			encontrarCasilla=portalOC.encontrarElemento(campoActual);
			assertTrue(mensajeEncontrar+campoActual, encontrarCasilla);
		
			
				/*
				 * PASO 16:
				 * Descripción del paso: Seleccionar botón Buscar
				 * 
				 * Resultado Esperado: Mostrar registros de Orden de compra
				 */	
			encontrarCasilla=portalOC.seleccionarElemento(campoActual, false);
			assertTrue(mensajeSeleccion+campoActual, encontrarCasilla);
			campoActual="tablaBusqueda";
			encontrarCasilla=portalOC.encontrarElemento(campoActual);
			assertTrue(mensajeEncontrar+campoActual, encontrarCasilla);
			//portalOC.buscarYQuitarAlerta();
			
				/*
				 * PASO 17:
				 * Descripción del paso: Seleccionar registro
				 * 
				 * Resultado Esperado: Habilitar elemento visual para mover registro a la sección derecha
				 */	
				
			campoActual="botonTiendas";
			encontrarCasilla=portalOC.seleccionarElemento(campoActual, false);
			assertTrue(mensajeSeleccion+campoActual, encontrarCasilla);
			campoActual="elementoDerecha";
			//portalOC.buscarYQuitarAlerta();
			encontrarCasilla=portalOC.encontrarElemento(campoActual);
			assertTrue(mensajeEncontrar+campoActual, encontrarCasilla);
			
			
				/*
				 * PASO 18:
				 * Descripción del paso: Seleccionar elemento visual para mover registro
				 * 
				 * Resultado Esperado: Habilitar casilla: "Nombre chofer"
				 */	
				
			encontrarCasilla=portalOC.seleccionarElemento(campoActual, false);
			assertTrue(mensajeSeleccion+campoActual, encontrarCasilla);
			contador++;
			
		}while(contador<ordenesCompras.length);
		
		campoActual="nombreChofer";
		encontrarCasilla=portalOC.encontrarElemento(campoActual);
		assertTrue(mensajeEncontrar+campoActual, encontrarCasilla);
		testCase.addScreenShotCurrentStep("ORDENESDECOMPRA");
		/*
		 * PASO 19:
		 * Descripción del paso: Capturar nombre de transportista
		 * 
		 * Resultado Esperado: Encontrar casilla de número económico de transporte
		 */	
		addStep("Capturar nombre de transportista: "+nombreChofer);
		encontrarCasilla= portalOC.capturarCasilla(nombreChofer, campoActual,true,true);
		assertTrue(mensajeCaptura+campoActual, encontrarCasilla);
		campoActual="numeroEconomico";
		encontrarCasilla=portalOC.encontrarElemento(campoActual);
		assertTrue(mensajeEncontrar+campoActual, encontrarCasilla);
		
		/*
		 * PASO 20:
		 * Descripción del paso: Capturar número económico de transporte al que recien se modifico su Peso Bruto Vehicular
		 * 
		 * Resultado Esperado: Autollenado de casillas: Placas, Seguros y Peos Bruto Vehicular
		 */	
		addStep("Capturar numero economico; "+numeroEconomico);
		encontrarCasilla= portalOC.capturarCasilla(numeroEconomico, campoActual,true,true);
		assertTrue(mensajeCaptura+campoActual, encontrarCasilla);
		encontrarCasilla=portalOC.verificarAutollenado();
		assertTrue("Los campos del transportista no fueron autollenados totalmente", encontrarCasilla);
		
		
		
		//REGISTRO ISTMO
		//LOGISTICA INVERSA
		if(logisticaInversa!=null) {
			if(logisticaInversa.compareToIgnoreCase("si")==0) {
			campoActual="logisticaInversa";
			addStep("Se selecciona la opcion de logistica inversa o devolucion de mercancia");
				encontrarCasilla= portalOC.seleccionarElemento(campoActual,true);
				assertTrue(mensajeSeleccion+campoActual, encontrarCasilla);
			
			}
		}
	
		/*
		 * PASO 21:
		 * Descripción del paso: Seleccionar: "Generar Carta Porte"
		 * 
		 * Resultado Esperado: Descarga de archivo .zip con formatos XML y PDF, se muestra pantalla de envio de correo.
		 */
		portalCP.bajarScroll();
		addStep("Se generan documentos de carta porte");
		campoActual="generarCartaPorte";
		encontrarCasilla=portalOC.seleccionarElemento(campoActual, false);
		assertTrue(mensajeSeleccion+campoActual
				
				, encontrarCasilla);
		encontrarCasilla=portalOC.determinarEstatusTimbrado();
		assertTrue("Timbrado fallo", encontrarCasilla);
		
		/*
		 * PASO 22:
		 * Descripción del paso: Captura de correo para enviar documentos
		 * 
		 * Resultado Esperado: Se envia documento .zip con formatos XML y PDF, se muestra visualización de la tira de impresión
		 */	
		addStep("Captura de correo para enviar documentos");
		campoActual="capturaCorreo";
		encontrarCasilla= portalOC.capturarCasilla(correo, campoActual,false,false);
		assertTrue(mensajeCaptura+campoActual, encontrarCasilla);
		campoActual="botonEnviar";
		encontrarCasilla= portalOC.seleccionarElemento(campoActual, false);
		assertTrue(mensajeSeleccion+campoActual, encontrarCasilla);
		getTestCase().addTextEvidenceCurrentStep("Se muestra tira de impresion");
		u.hardWait(15);
		p.clickImagen("printCP");
		testCase.addScreenShotCurrentStep("TiraImpresion"+portalCP.generarNumeroAleatorio(500, 600));
		u.hardWait(15);
		p.clickImagen("Folder");
		u.hardWait(2);
		sk.screen.type(rutaGuardarTira);
		sk.screen.type(Keys.ENTER);
		u.hardWait(2);
		p.pressTAB(6);
		u.hardWait(2);
		String nombreTira="CartaPorteTiraDocOC_"+String.valueOf(portalCP.generarNumeroAleatorio(300, 201))+".pdf";
		getTestCase().addTextEvidenceCurrentStep("Se guarda documento: "+nombreTira);
		sk.screen.type(nombreTira);
		u.hardWait(15);
		sk.screen.type(Keys.TAB);
		u.hardWait(15);
		p.clickImagen("saveCP");
		u.hardWait(15);
		getTestCase().addTextEvidenceCurrentStep("Las validaciones sobre el contenido de cada documento se realizaran de forma manual");
		u.close(); 	 
	}
	
	@Override
	public String setTestDescription() {
		return "Generar carta porte para validar que la documentación se genera con los\r\n"
				+ " correspondientes QRs";
	}
	@Override
	public String setTestDesigner() {
		return "nellyoliviab@hexaware.com";
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_006_GenerarCartaPorteOC31_CartaPorte3_test";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void beforeTest() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String setPrerequisites() {
		return "1.- Acceso a la plataforma: https://fcportalqa.femcom.net:8443/AccessControl/pages/login_form.jsf\r\n"
				+ ""+"\n"
				+ "2.- Origen de Cedis o PT"+"\n"
				+ "3.- Destino de Cedis o PT "+"\n"
				+ "4.- Orden de compra"+"\n"
				+ "5.- Datos del transportista validos para el origen capturado";
	}
	@AfterMethod
	public void obtenerTiempoDeEjecucion(ITestResult result) {
		long ms = result.getEndMillis() - result.getStartMillis();
		int ss = (int) (ms / 1000) % 60;
		int mm = (int) (ms / (1000 * 60) % 60);
		int hh = (int) ((ms / (1000 * 60 * 60)) % 24);
		String tiempoDeEjecucion = String.format("%02d:%02d:%02d", hh, mm, ss);

		getTestCase().addTextEvidenceCurrentStep("Tiempo de ejecución:  " + tiempoDeEjecucion);
		System.out.println("Tiempo de ejecución:  " + tiempoDeEjecucion);
	}
}
