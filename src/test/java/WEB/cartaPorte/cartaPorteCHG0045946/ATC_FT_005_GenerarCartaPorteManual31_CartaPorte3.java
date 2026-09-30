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

import WEB.cartaPorte.objectModelPortalCP.AdministracionUnidades;
import WEB.cartaPorte.objectModelPortalCP.CartaPorteManual;
import WEB.cartaPorte.objectModelPortalCP.LoginCP;
import WEB.cartaPorte.objectModelPortalCP.MenuPortal;
import WEB.cartaPorte.objectModelPortalCP.PortalCartaPorte;
import WEB.cartaPorte.objectModelPortalCP.UnidadesDetalleEquipo;
import WEB.estandarizacionHomologacionImpuestos.pageObjectModelHomo.MetodoGeneral;
import WEB.util.MetodosSikuli;
import modelo.BaseExecution;
import utils.SikuliUtil;
import utils.password.PasswordUtil;
import utils.selenium.ChromeTest;
import utils.selenium.SeleniumUtil;

public class ATC_FT_005_GenerarCartaPorteManual31_CartaPorte3 extends BaseExecution{
	//Flujo Carta Porte Manual
	@Test(dataProvider = "data-provider")
	/*
	 * El objetivo general del proyecto es generar carta porte para validar que la documentación se genera con los
	 * correspondientes QRs
	 * 		Carta Porte CEDIS
	 * 		Carta Porte Manual
	 * 		Carta Porte OC
	 * 
	 * REREQUISITOS PARA LA EJECUCION:
	 *  
	 *  *Mantener visible en la barra de tareas el explorador de archivos
	 *  *No tener activa la tecla: Bloq mayúsculas.
	 *   
	*/
	public void ATC_FT_005_GenerarCartaPorteManual31_CartaPorte3_test(HashMap<String, String> data) throws FindFailed {
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
		 * pac= pac con el que se realizará la prueba
		 * registro= Número de registros a agregar
	    */
		String portalurl = data.get("server");
		String userName=data.get("user");
		String password = PasswordUtil.decryptPassword(data.get("password"));
		String pac = data.get("pac");
		String campo=data.get("campo");
		String origen=data.get("origen");
		String tipoCartaPorte = data.get("tipoCartaPorte");
		String check=data.get("check");
		String destino=data.get("destino");
		String tipoArticulo=data.get("tipoArticulo");
		String registro= data.get("registro");
		String nombreChofer=data.get("nombreChofer"); 
		String numeroEconomico=data.get("numeroEconomico");
		String registroIstmo = data.get("registroIstmo");
		String poloOrigen = data.get("poloOrigen");
		String poloDestino = data.get("poloDestino");
		String logisticaInversa = data.get("logisticaInversa");
		String correo=data.get("correo"); 
		String rutaGuardarTira = data.get("rutaGuardarTira");
		testCase.setTest_Description("");
		testCase.setTest_Description(campo+" . PAC de prueba: "+pac);
		SeleniumUtil u=new SeleniumUtil(new ChromeTest(), true);
		LoginCP login = new LoginCP(u, testCase, portalurl);
		MenuPortal portalmenu= new MenuPortal(u, testCase);
		PortalCartaPorte portalCP= new PortalCartaPorte(u, testCase);
		AdministracionUnidades admonUnidades=new AdministracionUnidades(u, testCase);
		UnidadesDetalleEquipo unidadesDetalle=new UnidadesDetalleEquipo(u,testCase);
		CartaPorteManual manual= new CartaPorteManual(u, testCase,portalCP);
		String mensajeSeleccion= "No fue posible seleccionar elemento: ";
		String mensajeEncontrar= "No fue posible encontrar elemento: ";
		String mensajeCaptura= "No fue posible capturar datos ";
		SikuliUtil sk = new SikuliUtil();
		String pathImg = sk.PatternPath("WEB\\"+"CARTAPORTEW");
		Pattern pathimg = new Pattern();
		ArrayList<String> ListImage = new ArrayList<String>();
		HashMap<String, Pattern> ImgPatter = new HashMap<String, Pattern>();
		ArrayList<Pattern> patterns = new ArrayList<Pattern>();
		File Fol = new File(pathImg);
		MetodosSikuli p = new MetodosSikuli(sk, u, pathimg, ListImage, ImgPatter, patterns, Fol);	
		//Preparar datos
		check=check.toUpperCase().charAt(0) + check.substring(1,check.length()).toLowerCase();
		tipoArticulo=tipoArticulo.toUpperCase().charAt(0) + tipoArticulo.substring(1,tipoArticulo.length()).toLowerCase();
		Integer registroTotal=Integer.parseInt(registro); 
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
		assertEquals(seleccionarCPManual, "Carta Porte Manual", "No se encontro el texto: Carta Porte Manual");//PASO 29
		addStep("Capturar campo: Origen");
		String campoActual="origen";
		boolean encontrarCasilla= manual.capturarCasilla(origen, campoActual,true,true);
		assertTrue(mensajeCaptura+campoActual, encontrarCasilla);
		campoActual="check"+check;
		encontrarCasilla=manual.encontrarElemento(campoActual);
		assertTrue(mensajeEncontrar+campoActual, encontrarCasilla);
		
		//PASO 30: Seleccionar checkbox (CEDIS, plaza, Terceros)
		addStep("Seleccionar checkbox");
		encontrarCasilla= manual.seleccionarElemento(campoActual,true);
		assertTrue(mensajeSeleccion+campoActual, encontrarCasilla);
		campoActual="destino";
		encontrarCasilla=manual.encontrarElemento(campoActual);
		manual.encontrarElemento(campoActual);
		assertTrue(mensajeEncontrar+campoActual, encontrarCasilla);
		
		//PASO 31: 
		addStep("Capturar campo: Destino");
		encontrarCasilla= manual.capturarCasilla(destino, campoActual,true,true);
		assertTrue(mensajeCaptura+campoActual, encontrarCasilla);
		campoActual="Mercancias";
		encontrarCasilla=manual.encontrarElemento(campoActual);
		assertTrue(mensajeEncontrar+"texto "+campoActual, encontrarCasilla);
		
		
		String valorFiltro="";
		int contador=1;
		int filas=1;
		boolean captura=true;
		do {
			if(contador>1) {
				portalCP.bajarScroll2("50");
				campoActual="botonAgregar";
				encontrarCasilla= manual.seleccionarElemento(campoActual,false);
				if(contador>10) {
					String valor=String.valueOf(contador);
					valor=valor.substring(valor.length()-1);
					if(valor.compareTo("1")==0) {
						portalCP.bajarScroll2("200");
						campoActual="paginadoAvanza";
						encontrarCasilla= manual.seleccionarElemento(campoActual,false);
						filas=1;
					}
				}
			}
			//PASO 32:
			if(captura) {
				addStep("Seleccionar elemento visual para la busqueda por SKU");
			}
			assertTrue(mensajeSeleccion+campoActual, encontrarCasilla);	
			encontrarCasilla=manual.rellenaArticulos(filas, tipoArticulo,captura);
			assertTrue(mensajeSeleccion+campoActual, encontrarCasilla);	
			campoActual="buscarArticulos";
			encontrarCasilla=manual.encontrarElemento(campoActual);
			assertTrue(mensajeEncontrar+campoActual, encontrarCasilla);
			//PASO 33 Seleccionar Tipo de Articulo(normal o especial)
			if(captura) {
				addStep("Seleccionar Tipo de Articulo");
			}
			campoActual="tipoArticulo";
			encontrarCasilla= manual.seleccionarElemento(campoActual+tipoArticulo,captura);
			assertTrue(mensajeSeleccion+campoActual, encontrarCasilla);
			campoActual="filtrar";
			encontrarCasilla=manual.encontrarElemento(campoActual);
			assertTrue(mensajeEncontrar+campoActual, encontrarCasilla);
			//PASO 34
			if(captura) {
				addStep("Capturar informacion de casilla: Filtrar");
			}
			int tipoArticuloOp=manual.determinaTipoArticulo(tipoArticulo);
			valorFiltro=manual.obtenerValorArticulos(tipoArticuloOp);
			encontrarCasilla= manual.capturarCasilla(valorFiltro, campoActual,false,captura);
			assertTrue(mensajeCaptura+campoActual, encontrarCasilla);
			campoActual="botonBuscarArticulo";
			encontrarCasilla= manual.seleccionarElemento(campoActual,false);
			assertTrue(mensajeSeleccion+campoActual, encontrarCasilla);
			if(captura) {
				addStep("Seleccionar un articulo");
			}
			encontrarCasilla=manual.seleccionaArticulos(captura);
			assertTrue("Error al seleccionar articulo", encontrarCasilla);
			encontrarCasilla=manual.capturaDetalleArticulo(filas);
			assertTrue("Error capturar detalles de artículo", encontrarCasilla);
			
			if(contador==registroTotal) {
				getTestCase().addTextEvidenceCurrentStep("Total de mercancia seleccionada");
				testCase.addScreenShotCurrentStep("MercanciaTotal");
			}
			captura=false;
			contador++;
			filas++;
			
		}while(encontrarCasilla && contador<=registroTotal);
		
		//PASO 37
		addStep("Capturar nombre de transportista");
		portalCP.bajarScroll();
		campoActual="nombreChofer";
		encontrarCasilla= manual.capturarCasilla(nombreChofer, campoActual,true,true);
		assertTrue(mensajeCaptura+campoActual, encontrarCasilla);
		campoActual="numeroEconomico";
		encontrarCasilla=manual.encontrarElemento(campoActual);
		assertTrue(mensajeEncontrar+"texto "+campoActual, encontrarCasilla);
		
		//PASO 38
		addStep("Capturar numero economico de transporte");
		encontrarCasilla= manual.capturarCasilla(numeroEconomico, campoActual,true,true);
		assertTrue(mensajeCaptura+campoActual, encontrarCasilla);
		encontrarCasilla=manual.verificarAutollenado();
		assertTrue("Los campos del transportista no fueron autollenados totalmente", encontrarCasilla);
		
				//LOGISTICA INVERSA
				if(logisticaInversa!=null) {
					if(logisticaInversa.compareToIgnoreCase("si")==0) {
					campoActual="logisticaInversa";
					addStep("Se selecciona la opcion de logistica inversa o devolucion de mercancia");
						encontrarCasilla= manual.seleccionarElemento(campoActual,true);
						assertTrue(mensajeSeleccion+campoActual, encontrarCasilla);
					
				}
				}
				campoActual="generarCartaPorte";
				addStep("Se genera carta porte");
				encontrarCasilla= manual.seleccionarElemento(campoActual,false);
				assertTrue(mensajeSeleccion+campoActual, encontrarCasilla);
						
				encontrarCasilla=manual.determinarEstatusTimbrado();
				assertTrue("Timbrado fallo", encontrarCasilla);
		
		//PASO 20
		addStep("Se visualiza documentacion generada");
		campoActual="capturaCorreo";
		encontrarCasilla= manual.capturarCasilla(correo, campoActual,false,false);
		assertTrue(mensajeCaptura+campoActual, encontrarCasilla);
		campoActual="botonEnviar";
		encontrarCasilla= manual.seleccionarElemento(campoActual,false);
		assertTrue(mensajeSeleccion+campoActual, encontrarCasilla);
		getTestCase().addTextEvidenceCurrentStep("Se muestra tira de impresion");
		u.hardWait(20);
		p.clickImagen("printCP");
		testCase.addScreenShotCurrentStep("TiraImpresion"+portalCP.generarNumeroAleatorio(500, 600));
		u.hardWait(2);
		p.clickImagen("Folder");
		u.hardWait(2);
		sk.screen.type(rutaGuardarTira);
		sk.screen.type(Keys.ENTER);
		u.hardWait(2);
		p.pressTAB(6);
		u.hardWait(2);
		String nombreTira="CartaPorteTiraDocManual_"+String.valueOf(portalCP.generarNumeroAleatorio(300, 201))+".pdf";
		getTestCase().addTextEvidenceCurrentStep("Se guarda documento: "+nombreTira);
		sk.screen.type(nombreTira);
		u.hardWait(20);
		sk.screen.type(Keys.TAB);
		u.hardWait(30);
		p.clickImagen("saveCP");
		u.hardWait(20);
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
		return "Generar carta porte para validar que la documentación se genera con los\r\n"
				+ " correspondientes QRs";
	}
	@Override
	public String setTestDesigner() {
		return "nellyoliviab@hexaware.com";
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_005_GenerarCartaPorteManual31_CartaPorte3_test";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
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
