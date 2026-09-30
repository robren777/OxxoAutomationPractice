package WEB.cartaPorte.cartaPortev3CHG0040003;

import static org.junit.Assert.assertTrue;
import static org.testng.Assert.assertEquals;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import org.sikuli.hotkey.Keys;
import org.sikuli.script.FindFailed;
import org.sikuli.script.Pattern;
import org.testng.annotations.Test;

import WEB.cartaPorte.objectModelPortalCP.CartaPorteManual;
import WEB.cartaPorte.objectModelPortalCP.LoginCP;
import WEB.cartaPorte.objectModelPortalCP.MenuPortal;
import WEB.cartaPorte.objectModelPortalCP.PortalCartaPorte;
import WEB.util.MetodosSikuli;
import modelo.BaseExecution;
import utils.SikuliUtil;
import utils.password.PasswordUtil;
import utils.selenium.ChromeTest;
import utils.selenium.SeleniumUtil;

public class ATC_FT_007_GenerarCartaPorteManualMasivo_CartaPorte3 extends BaseExecution{
	//Flujo Carta Porte Manual
	@Test(dataProvider = "data-provider")
	/*
	 * El objetivo general del proyecto es actualizar la version de la carta porte a la 3.0 segun las
	 * espacificaciones del SAT, se modificaran los siguientes flujos de portal WEB:
	 * 		Carta Porte CEDIS
	 * 		Carta Porte Manual
	 * 		Carta Porte OC
	 *   
	*/
	public void AATC_FT_007_GenerarCartaPorteManualMasivo_CartaPorte3_test(HashMap<String, String> data) throws FindFailed {
		/*
		 * VARIABLES
		 * 
		 * portalUrl = Url para acceder al portal WEB carta porte
		 * userName = usuario de acceso
		 * password = contraseña encriptada para acceder a la aplicación
		 * tipoCartaPorte = cedis, manual u oc
		 * origen = CEDIS o PT origen
		 * destino = CEDIS o PT destino
		 * check = Opcion de sección de checkbox: // cedis / plaza/ terceros
		 * tipoArticulo = especial o normal
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
		String origen=data.get("origen");
		String destino=data.get("destino");
		String check=data.get("check");
		String nombreChofer=data.get("nombreChofer"); 
		String numeroEconomico=data.get("numeroEconomico"); 
		String correo=data.get("correo"); 
		String campo=data.get("campo"); 
		//Nuevos campos
		String pesoBruto = data.get("pesoBruto");
		String registroIstmo = data.get("registroIstmo");
		String poloOrigen = data.get("poloOrigen");
		String poloDestino = data.get("poloDestino");
		String logisticaInversa = data.get("logisticaInversa");
		String pac = data.get("pac");	
	
		SikuliUtil sk = new SikuliUtil();
		String pathImg = sk.PatternPath("WEB\\"+"CARTAPORTEW");
		Pattern pathimg = new Pattern();
		ArrayList<String> ListImage = new ArrayList<String>();
		HashMap<String, Pattern> ImgPatter = new HashMap<String, Pattern>();
		ArrayList<Pattern> patterns = new ArrayList<Pattern>();
		File Fol = new File(pathImg);
		SeleniumUtil u=new SeleniumUtil(new ChromeTest(), true);
		MetodosSikuli p = new MetodosSikuli(sk, u, pathimg, ListImage, ImgPatter, patterns, Fol);	
		//SeleniumUtil u = new SeleniumUtil(new MEdgeTest(), true);
		testCase.setTest_Description("");
		testCase.setTest_Description(campo+" . PAC de prueba: "+pac);
		
		LoginCP login = new LoginCP(u, testCase, portalurl);
		MenuPortal portalmenu= new MenuPortal(u, testCase);
		PortalCartaPorte portalCP= new PortalCartaPorte(u, testCase);
		CartaPorteManual manual= new CartaPorteManual(u, testCase,portalCP);
		
		String mensajeCaptura= "No fue posible capturar datos ";
		String mensajeEncontrar= "No fue posible encontrar elemento: ";
		String mensajeSeleccion= "No fue posible seleccionar elemento: ";
		
		 
		//Preparar datos
		check=check.toUpperCase().charAt(0) + check.substring(1,check.length()).toLowerCase();
		
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
		assertTrue("No se encontro mensaje: Portal de Aplicaciones", mensajePortal.compareTo("Portal de Aplicaciones")==0);

		//PASO 3: 
		addStep("Seleccionar: Portal Carta Porte");
		portalmenu.seleccionarOpcionCartaPorte();
		boolean encontrarMenuCP=portalCP.encontrarMenuOpcionesCartaPorte();
		assertTrue("No se encontro Menu de Carta Porte", encontrarMenuCP);
		
		//PASO 4: 
		addStep("Seleccionar Menu de Carta Porte");
		String encontrarOpcionMenuCP=portalCP.encontrarOpcionesCartaPorte(tipoCartaPorte);
		assertEquals(encontrarOpcionMenuCP, "Carta Porte Manual", "No se encontro la opcion: Carta Porte Manual");
		
		//PASO 5:
		addStep("Seleccionar opcion: Carta Porte manual");
		String seleccionarCPManual=portalCP.seleccionarOpcionCartaPorte(tipoCartaPorte);
		assertEquals(seleccionarCPManual, "Carta Porte Manual", "No se encontro el texto: Carta Porte Manual");
		
		//PASO 6:
		addStep("Capturar campo: Origen");
		String campoActual="origen";
		boolean encontrarCasilla= manual.capturarCasilla(origen, campoActual,true,true);
		assertTrue(mensajeCaptura+campoActual, encontrarCasilla);
		campoActual="check"+check;
		encontrarCasilla=manual.encontrarElemento(campoActual);
		assertTrue(mensajeEncontrar+campoActual, encontrarCasilla);
		
		
		//PASO 7: Seleccionar checkbox (CEDIS, plaza, Terceros)
		addStep("Seleccionar checkbox");
		encontrarCasilla= manual.seleccionarElemento(campoActual,true);
		assertTrue(mensajeSeleccion+campoActual, encontrarCasilla);
		campoActual="destino";
		encontrarCasilla=manual.encontrarElemento(campoActual);
		manual.encontrarElemento(campoActual);
		assertTrue(mensajeEncontrar+campoActual, encontrarCasilla);
		
			
		//PASO 8: 
		addStep("Capturar campo: Destino");
		encontrarCasilla= manual.capturarCasilla(destino, campoActual,true,true);
		assertTrue(mensajeCaptura+campoActual, encontrarCasilla);
		campoActual="Mercancias";
		encontrarCasilla=manual.encontrarElemento(campoActual);
		assertTrue(mensajeEncontrar+"texto "+campoActual, encontrarCasilla);
		
		//PASO 9
		addStep("Seleccionar carga masiva");
		
		manual.cargaMasiva();		
		
		
		//PASO13-14
		addStep("Capturar nombre de transportista");
		portalCP.bajarScroll();
		campoActual="nombreChofer";
		encontrarCasilla= manual.capturarCasilla(nombreChofer, campoActual,true,true);
		assertTrue(mensajeCaptura+campoActual, encontrarCasilla);
		campoActual="numeroEconomico";
		encontrarCasilla=manual.encontrarElemento(campoActual);
		assertTrue(mensajeEncontrar+"texto "+campoActual, encontrarCasilla);
		
		//PASO 15
		addStep("Capturar numero economico de transporte");
		encontrarCasilla= manual.capturarCasilla(numeroEconomico, campoActual,true,true);
		assertTrue(mensajeCaptura+campoActual, encontrarCasilla);
		encontrarCasilla=manual.verificarAutollenado();
		assertTrue("Los campos del transportista no fueron autollenados totalmente", encontrarCasilla);
		
		
		//PASO 16
		addStep("Generar Carta Porte");
		//Nombrar archivo CVS y XML
		campoActual="nombrarArchivo";
		getTestCase().addTextEvidenceCurrentStep("Se nombra el archivo CSV");
		encontrarCasilla= manual.capturarCasilla("CartaPorteCSV"+portalCP.generarNumeroAleatorio(100, 1), campoActual,false,false);
		assertTrue(mensajeCaptura+campoActual, encontrarCasilla);
		campoActual="seleccionarCSV";
		encontrarCasilla= manual.seleccionarElemento(campoActual,true);
		assertTrue(mensajeSeleccion+campoActual, encontrarCasilla);
		campoActual="generarArchivo";
		encontrarCasilla= manual.seleccionarElemento(campoActual,false);
		assertTrue(mensajeSeleccion+campoActual, encontrarCasilla);
		getTestCase().addTextEvidenceCurrentStep("Se nombra el archivo XML");
		campoActual="nombrarArchivo";
		encontrarCasilla= manual.capturarCasilla("CartaPorteXLS"+portalCP.generarNumeroAleatorio(200, 101), campoActual,false,false);
		assertTrue(mensajeCaptura+campoActual, encontrarCasilla);
		campoActual="seleccionarXLS";
		encontrarCasilla= manual.seleccionarElemento(campoActual,true);
		assertTrue(mensajeSeleccion+campoActual, encontrarCasilla);
		campoActual="generarArchivo";
		encontrarCasilla= manual.seleccionarElemento(campoActual,false);
		assertTrue(mensajeSeleccion+campoActual, encontrarCasilla);
		campoActual="generarCartaPorte";
		getTestCase().addTextEvidenceCurrentStep("Se generan documentos para carta porte");
		encontrarCasilla= manual.seleccionarElemento(campoActual,true);
		assertTrue(mensajeSeleccion+campoActual, encontrarCasilla);
		
		//PASO 17
		addStep("Captura de correo para enviar documentos");
		campoActual="capturaCorreo";
		encontrarCasilla= manual.capturarCasilla(correo, campoActual,false,false);
		assertTrue(mensajeCaptura+campoActual, encontrarCasilla);
		campoActual="botonEnviar";
		encontrarCasilla= manual.seleccionarElemento(campoActual,true);
		assertTrue(mensajeSeleccion+campoActual, encontrarCasilla);
		
		getTestCase().addTextEvidenceCurrentStep("Se muestra tira de impresion");
		p.clickImagen("printCP");
		testCase.addScreenShotCurrentStep("TiraImpresion"+portalCP.generarNumeroAleatorio(500, 600));
		u.hardWait(2);
		String nombreTira="CartaPorteTiraDocManual_"+String.valueOf(portalCP.generarNumeroAleatorio(300, 201))+".pdf";
		getTestCase().addTextEvidenceCurrentStep("Se guarda documento: "+nombreTira);
		sk.screen.type(nombreTira);
		u.hardWait(2);
		sk.screen.type(Keys.TAB);
		u.hardWait(2);
		p.clickImagen("saveCP");
		u.hardWait(6);
		getTestCase().addTextEvidenceCurrentStep("Las validaciones sobre el contenido de cada documento se realizaran de forma manual");
		
		
		u.close(); 
		
	}

	@Override
	public void beforeTest() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String setPrerequisites() {
		return "1.- Acceso a la plataforma: https://fcportalqa.femcom.net:8443/AccessControl/pages/login_form.jsf\r\n"
				+ ""+"\n"
				+ "2.- Origen de Cedis, plaza o terceros"+"\n"
				+ "3.- Destino de Cedis, plaza o terceros "+"\n"
				+ "4.- Articulos existentes (normales o especiales)"
				+ "5.- Datos del transportista validos para el Cedis, plaza o terceros";
	}

	
	@Override
	public String setTestDescription() {
		return "Actualizar la version de la carta porte a la 3.0 segun las\r\n"
				+ " especificaciones del SAT, se modificaran los siguientes flujos de portal WEB";
	}
	@Override
	public String setTestDesigner() {
		return "nellyoliviab@hexaware.com";
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_007_GenerarCartaPorteManualMasivo_CartaPorte3_test";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}

}
