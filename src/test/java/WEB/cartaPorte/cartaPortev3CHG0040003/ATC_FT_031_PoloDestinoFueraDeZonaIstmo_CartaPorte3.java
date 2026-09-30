package WEB.cartaPorte.cartaPortev3CHG0040003;

import static org.junit.Assert.assertTrue;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

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

public class ATC_FT_031_PoloDestinoFueraDeZonaIstmo_CartaPorte3 extends BaseExecution{
	//Flujo Carta Porte Manual
	@Test(dataProvider = "data-provider")
	public void ATC_FT_031_PoloDestinoFueraDeZonaIstmo_CartaPorte3_test(HashMap<String, String> data) throws FindFailed {
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
		String tipoArticulo=data.get("tipoArticulo"); 
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
		String registro= data.get("registro");
		SeleniumUtil u = new SeleniumUtil(new ChromeTest(), true);
		SikuliUtil sk = new SikuliUtil();
		String pathImg = sk.PatternPath("WEB\\"+"CARTAPORTEW");
		Pattern pathimg = new Pattern();
		ArrayList<String> ListImage = new ArrayList<String>();
		HashMap<String, Pattern> ImgPatter = new HashMap<String, Pattern>();
		ArrayList<Pattern> patterns = new ArrayList<Pattern>();
		File Fol = new File(pathImg);
		MetodosSikuli p = new MetodosSikuli(sk, u, pathimg, ListImage, ImgPatter, patterns, Fol);	
		//SeleniumUtil u = new SeleniumUtil(new MEdgeTest(), true);
		
		LoginCP login = new LoginCP(u, testCase, portalurl);
		MenuPortal portalmenu= new MenuPortal(u, testCase);
		PortalCartaPorte portalCP= new PortalCartaPorte(u, testCase);
		CartaPorteManual manual= new CartaPorteManual(u, testCase,portalCP);
		
		String mensajeCaptura= "No fue posible capturar datos ";
		String mensajeEncontrar= "No fue posible encontrar elemento: ";
		String mensajeSeleccion= "No fue posible seleccionar elemento: ";
		
		 
		//Preparar datos
		check=check.toUpperCase().charAt(0) + check.substring(1,check.length()).toLowerCase();
		tipoArticulo=tipoArticulo.toUpperCase().charAt(0) + tipoArticulo.substring(1,tipoArticulo.length()).toLowerCase();
		Integer registroTotal=Integer.parseInt(registro); 
		//PASO 1:
		addStep("Ingresar a la liga de acceso: https://fcportalqa.femcom.net:8443/AccessControl/pages/login_form.jsf");
		u.get(login.getUrl());
		login.proceedToPortal();
		boolean cargarPagina=login.encontrarMensajeInicio();
		testCase.addScreenShotCurrentStep("CargarURL");
		assertTrue( "El sitio no fue cargado satisfactoriamente",cargarPagina);
		//PASO 2: 
		addStep("Capturar credenciales de acceso para iniciar sesión");
		login.cicloLogin2(userName, password);
		String mensajePortal=portalmenu.accesoMenuPortal(userName, password);
		assertTrue("No se encontró mensaje: Portal de Aplicaciones", mensajePortal.compareTo("Portal de Aplicaciones")==0);

		//PASO 3: 
		addStep("Seleccionar \"Portal Carta Porte\"");
		portalmenu.seleccionarOpcionCartaPorte();
		boolean encontrarMenuCP=portalCP.encontrarMenuOpcionesCartaPorte();
		assertTrue("No se encontro Menu de Carta Porte", encontrarMenuCP);
		
		//PASO 4: 
		addStep("Seleccionar Menu de Carta Porte");
		String encontrarOpcionMenuCP=portalCP.encontrarOpcionesCartaPorte(tipoCartaPorte);
		assertEquals(encontrarOpcionMenuCP, "Carta Porte Manual", "No se encontro la opcion: Carta Porte Manual");
		
		//PASO 5:
		addStep("Seleccionar opcion \"Carta Porte manual\"");
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
			//PASO 9:
			if(captura) {
				addStep("Seleccionar elemento visual para la busqueda por SKU");
			}
			assertTrue(mensajeSeleccion+campoActual, encontrarCasilla);	
			encontrarCasilla=manual.rellenaArticulos(filas, tipoArticulo,captura);
			assertTrue(mensajeSeleccion+campoActual, encontrarCasilla);	
			campoActual="buscarArticulos";
			encontrarCasilla=manual.encontrarElemento(campoActual);
			assertTrue(mensajeEncontrar+campoActual, encontrarCasilla);
			//PASO 10 Seleccionar Tipo de Articulo(normal o especial)
			if(captura) {
				addStep("Seleccionar Tipo de Articulo");
			}
			campoActual="tipoArticulo";
			encontrarCasilla= manual.seleccionarElemento(campoActual+tipoArticulo,captura);
			assertTrue(mensajeSeleccion+campoActual, encontrarCasilla);
			campoActual="filtrar";
			encontrarCasilla=manual.encontrarElemento(campoActual);
			assertTrue(mensajeEncontrar+campoActual, encontrarCasilla);
			//PASO 11
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
		
		
		
		//NUEVOS CAMPOS PESO BRUTO
		campoActual="pesoBruto";
		if(pesoBruto.compareTo("")==0) {
			addStep("Peso Bruto actual");
			encontrarCasilla=manual.encontrarElemento(campoActual);
			assertTrue(mensajeEncontrar+campoActual, encontrarCasilla);
		}else {
			addStep("Se modifica valor del peso bruto");
			encontrarCasilla= manual.capturarCasilla(pesoBruto, campoActual,false,true);
			assertTrue(mensajeCaptura+campoActual, encontrarCasilla);
		}
		//REGISTRO ISTMO
		if(registroIstmo!=null) {
			campoActual="registroIstmo";
			addStep("Se selecciona registro Istmo");
			if(registroIstmo.compareToIgnoreCase("SI")==0) {
				encontrarCasilla= manual.seleccionarElemento(campoActual,true);
				assertTrue(mensajeSeleccion+campoActual, encontrarCasilla);
				addStep("Capturar polo origen de la zona Istmo");
				campoActual="poloOrigen";
				encontrarCasilla= manual.capturarCasilla(poloOrigen, campoActual,true,true);
				assertTrue(mensajeCaptura+campoActual, encontrarCasilla);
				addStep("Capturar polo destino fuera de la zona Istmo");
				campoActual="poloDestino";
				encontrarCasilla= manual.capturarCasilla(poloDestino, campoActual,true,true);
				assertFalse(encontrarCasilla, "Polo Destino no debe encontrarse en el listado de zona Istmo");

			}
		}
				
		//LOGISTICA INVERSA
		if(logisticaInversa!=null) {
			campoActual="logisticaInversa";
			addStep("Se selecciona la opcion de logistica inversa o devolucion de mercancia");
			if(logisticaInversa.compareToIgnoreCase("SI")==0) {
				encontrarCasilla= manual.seleccionarElemento(campoActual,true);
				assertTrue(mensajeSeleccion+campoActual, encontrarCasilla);
			}
		}
		
		addStep("Seleccionar Generar Carta Porte");
		campoActual="generarCartaPorte";
		getTestCase().addTextEvidenceCurrentStep("Se generan documentos para carta porte");
		encontrarCasilla= manual.seleccionarElemento(campoActual,true);
		assertTrue(mensajeSeleccion+campoActual, encontrarCasilla);
		
		addStep("Se muestra mensaje de error en zona Istmo");
		campoActual="mensaje";
		encontrarCasilla=manual.encontrarElemento(campoActual);
		assertTrue("Polo Destino de registro Istmo no debe ser valido", encontrarCasilla);
		
		
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
		return "Validar que no se genere Carta Porte si se captura un Polo Destino fuera de zono Istmo";
	}
	@Override
	public String setTestDesigner() {
		return "nellyoliviab@hexaware.com";
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_031_PoloDestinoFueraDeZonaIstmo_CartaPorte3_test";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}

}
