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

import WEB.cartaPorte.objectModelPortalCP.CartaPorteCedis;
import WEB.cartaPorte.objectModelPortalCP.LoginCP;
import WEB.cartaPorte.objectModelPortalCP.MenuPortal;
import WEB.cartaPorte.objectModelPortalCP.PortalCartaPorte;
import WEB.util.MetodosSikuli;
import modelo.BaseExecution;
import utils.SikuliUtil;
import utils.password.PasswordUtil;
import utils.selenium.ChromeTest;
import utils.selenium.SeleniumUtil;

public class ATC_FT_032_PoloOrigenFueraDeZonaIstmo_CartaPorte3 extends BaseExecution{
	@Test(dataProvider = "data-provider")
	public void ATC_FT_032_PoloOrigenFueraDeZonaIstmo_CartaPorte3_test(HashMap<String, String> data) throws FindFailed {
		/*
		 * VARIABLES
		 * 
		 * ebsUrl= Url para acceder al portal WEB carta porte
		 * userName = usuario de acceso
		 * password = contraseña encriptada para acceder a la aplicación
		 * tipoCartaPorte = cedis, manual u oc
		 * origen = CEDIS o PT origen
		 * mes = mes en formato de texto, ejemplo: enero, febrero...
		 * dia= número de dia de facturacion
		 * anio= anio de facturacion
		 * check1 = Primera sección de checkbox: // Shipped: 1 /Closed: 2/ Ambos: 3
		 * check2 = Segunda sección de checkbox:// Ruta: 1 /Ola: 2/ Transferencia: 3
		 * descripcion = valor de la ruta, ola o transaccion
		 * mesBOL = mesBOL en formato de texto, ejemplo: enero, febrero...
		 * diaBOL = número de dia de facturacion
		 * anioBOL = anio de facturacion
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
		String mes=data.get("mes");
		String dia=data.get("dia");
		String anio=data.get("anio");
		String check1=data.get("check1");// Shipped: 1 /Closed: 2/ Ambos: 3
		String check2=data.get("check2");// Ruta: 1 /Ola: 2/ Transferencia: 3
		String descripcion=data.get("descripcion");
		String mesBOL=data.get("mesBOL");
		String diaBOL=data.get("diaBOL");
		String anioBOL=data.get("anioBOL");
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
		
		
		SeleniumUtil u = new SeleniumUtil(new ChromeTest(), true);
		SikuliUtil sk = new SikuliUtil();
		String pathImg = sk.PatternPath("WEB\\"+"CARTAPORTEW");
		Pattern pathimg = new Pattern();
		ArrayList<String> ListImage = new ArrayList<String>();
		HashMap<String, Pattern> ImgPatter = new HashMap<String, Pattern>();
		ArrayList<Pattern> patterns = new ArrayList<Pattern>();
		File Fol = new File(pathImg);
		MetodosSikuli p = new MetodosSikuli(sk, u, pathimg, ListImage, ImgPatter, patterns, Fol);	
		
		PortalCartaPorte portalCP= new PortalCartaPorte(u, testCase);
		CartaPorteCedis portalCedis= new CartaPorteCedis(u,testCase,portalCP);
		
		LoginCP login = new LoginCP(u, testCase, portalurl);
		MenuPortal portalmenu= new MenuPortal(u, testCase);
		
		String mensajeCaptura= "No fue posible capturar datos ";
		String mensajeEncontrar= "No fue posible encontrar elemento: ";
		String mensajeSeleccion= "No fue posible seleccionar elemento: ";
		
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
		assertEquals(encontrarOpcionMenuCP, "Carta Porte CEDIS", "No se encontro la opcion: Carta Porte Cedis");
		
		//PASO 5:
		addStep("Seleccionar opcion \"Carta Porte Cedis\"");
		String seleccionarCPCedis=portalCP.seleccionarOpcionCartaPorte(tipoCartaPorte);
		assertEquals(seleccionarCPCedis, "Carta Porte Entrega", "No se encontro el texto: Carta Porte Cedis");
		
		//PASO 6:
		addStep("Capturar campo: Origen");
		String campoActual="origen";
		boolean encontrarCasilla= portalCedis.capturarCasilla(origen, campoActual,true,true);
		assertTrue(mensajeCaptura+campoActual, encontrarCasilla);
		campoActual="fechaFacturacion";
		encontrarCasilla=portalCedis.encontrarElemento(campoActual, "");
		assertTrue(mensajeEncontrar+campoActual, encontrarCasilla);
		
		//PASO 7:
		addStep("Seleccionar elemento visual de selección de fecha");
		encontrarCasilla= portalCedis.seleccionarElemento(campoActual,true,"");
		assertTrue(mensajeSeleccion+campoActual, encontrarCasilla);
		
		//PASO 8
		addStep("Seleccionar fecha de facturación (año, mes, día)");
		campoActual="botonAnio";
		encontrarCasilla= portalCedis.seleccionarElemento(campoActual,false, anio);
		assertTrue(mensajeSeleccion+campoActual, encontrarCasilla);
		campoActual="anio";
		encontrarCasilla= portalCedis.seleccionarElemento(campoActual,false, anio);
		assertTrue(mensajeSeleccion+campoActual, encontrarCasilla);
		campoActual="mes";
		encontrarCasilla= portalCedis.seleccionarElemento(campoActual,false, mes);
		assertTrue(mensajeSeleccion+campoActual, encontrarCasilla);
		campoActual="dia";
		encontrarCasilla= portalCedis.seleccionarElemento(campoActual,true, dia);
		assertTrue(mensajeSeleccion+campoActual, encontrarCasilla);
		campoActual="estatusTransferencia";
		encontrarCasilla=portalCedis.encontrarElemento(campoActual, "");
		assertTrue(mensajeEncontrar+campoActual, encontrarCasilla);
		
		//PASO 9 Seleccionar opciones de checkbox  1 (Shipped, Closed o Ambos) y 2 (Ruta, Ola, Transferencia)		
		addStep("Seleccion de opciones estatus transferencia");
		campoActual="opciones1";
		encontrarCasilla= portalCedis.seleccionarElemento(campoActual,false, check1);
		assertTrue(mensajeSeleccion+campoActual, encontrarCasilla);
		campoActual="opciones2";
		encontrarCasilla= portalCedis.seleccionarElemento(campoActual,true, check2);
		assertTrue(mensajeSeleccion+campoActual, encontrarCasilla);
		campoActual=portalCedis.obtenerOpcion(check2);
		encontrarCasilla=portalCedis.encontrarElemento(campoActual,"");
		assertTrue(mensajeEncontrar+campoActual, encontrarCasilla);
		
		//PASO 10: Capturar descripción en casilla de opción 2
		addStep("Capturar descripción en casilla");
		encontrarCasilla= portalCedis.capturarCasilla(descripcion, campoActual,false,true);
		assertTrue(mensajeCaptura+campoActual, encontrarCasilla);
		campoActual="botonBuscar";
		encontrarCasilla=portalCedis.encontrarElemento(campoActual, "");
		assertTrue(mensajeEncontrar+campoActual, encontrarCasilla);
		
		//PASO 11: Seleccionar boton Buscar
		addStep("Seleccionar boton Buscar");
		encontrarCasilla= portalCedis.seleccionarElemento(campoActual,true, "");
		assertTrue(mensajeSeleccion+campoActual, encontrarCasilla);
		try {
		u.getDriver().switchTo().alert().accept();
		}catch (Exception e) {
			
		}
		campoActual="tablaRegistros";
		u.hardWait(5);
		encontrarCasilla=portalCedis.encontrarElemento(campoActual,"5");
		assertTrue(mensajeEncontrar+campoActual, encontrarCasilla);
		
		//PASO 12: Seleccionar boton Buscar
		addStep("Seleccionar tiendas");
		campoActual="seleccionaTiendas";
		encontrarCasilla= portalCedis.seleccionarElemento(campoActual,true, "5");
		assertTrue(mensajeSeleccion+campoActual, encontrarCasilla);
		campoActual="seleccionDerecha";
		encontrarCasilla=portalCedis.encontrarElemento(campoActual,"");
		assertTrue(mensajeEncontrar+campoActual, encontrarCasilla);
				
		//PASO 13: 
		addStep("Seleccionar elemento visual para mover tiendas");
		encontrarCasilla= portalCedis.seleccionarElemento(campoActual,false, "");
		assertTrue(mensajeSeleccion+campoActual, encontrarCasilla);
		campoActual="reSeleccionaTiendas";
		encontrarCasilla= portalCedis.seleccionarElemento(campoActual,true, "");
		assertTrue(mensajeSeleccion+campoActual, encontrarCasilla);
		portalCP.bajarScroll();
		campoActual="nombreChofer";
		encontrarCasilla=portalCedis.encontrarElemento(campoActual,"");
		assertTrue(mensajeEncontrar+campoActual, encontrarCasilla);
		
		//PASO 17
		addStep("Capturar nombre de transportista");
		encontrarCasilla= portalCedis.capturarCasilla(nombreChofer, campoActual,true,true);
		assertTrue(mensajeCaptura+campoActual, encontrarCasilla);
		campoActual="numeroEconomico";
		encontrarCasilla=portalCedis.encontrarElemento(campoActual,"");
		assertTrue(mensajeEncontrar+"texto "+campoActual, encontrarCasilla);
		
		//PASO 18
		addStep("Capturar numero economico de transporte");
		encontrarCasilla= portalCedis.capturarCasilla(numeroEconomico, campoActual,true,true);
		assertTrue(mensajeCaptura+campoActual, encontrarCasilla);
		encontrarCasilla=portalCedis.verificarAutollenado();
		assertTrue("Los campos del transportista no fueron autollenados totalmente", encontrarCasilla);
		
		//NUEVOS CAMPOS PESO BRUTO
		campoActual="pesoBruto";
		if(pesoBruto.compareTo("")==0) {
			addStep("Peso Bruto actual");
			encontrarCasilla=portalCedis.encontrarElemento(campoActual,"");
			assertTrue(mensajeEncontrar+campoActual, encontrarCasilla);
		}else {
			addStep("Se modifica valor del peso bruto");
			encontrarCasilla= portalCedis.capturarCasilla(pesoBruto, campoActual,false,true);
			assertTrue(mensajeCaptura+campoActual, encontrarCasilla);
		}
		//REGISTRO ISTMO
		if(registroIstmo!=null) {
			campoActual="registroIstmo";
			addStep("Se selecciona registro Istmo");
			if(registroIstmo.compareToIgnoreCase("SI")==0) {
				encontrarCasilla= portalCedis.seleccionarElemento(campoActual,true, "");
				assertTrue(mensajeSeleccion+campoActual, encontrarCasilla);
				addStep("Capturar polo origen fuera de la zona Istmo");
				campoActual="poloOrigen";
				encontrarCasilla= portalCedis.capturarCasilla(poloOrigen, campoActual,true,true);
				assertFalse(encontrarCasilla, "Polo Origen no debe encontrarse en el listado de zona Istmo");
				addStep("Capturar polo destino dentro de la zona Istmo");
				campoActual="poloDestino";
				encontrarCasilla= portalCedis.capturarCasilla(poloDestino, campoActual,true,true);
				assertTrue(mensajeCaptura+campoActual, encontrarCasilla);
				
			}
		}
		addStep("Seleccionar: Generar Carta Porte");
		campoActual="seleccionarCartaPorte";
		portalCP.bajarScroll();
		encontrarCasilla= portalCedis.seleccionarElemento(campoActual,true, mesBOL);
		assertTrue(mensajeSeleccion+campoActual, encontrarCasilla);
		
		addStep("Se muestra mensaje de error en zona Istmo");
		campoActual="mensaje";
		encontrarCasilla=portalCedis.encontrarElemento(campoActual,"");
		assertTrue("Polo Origen Istmo no debe ser valido", encontrarCasilla);
		
		u.close(); 
		
	}
	

	public String setTestDescription() {
		return "Validar que no se genere Carta Porte si se captura un Polo Origen fuera de zono Istmo";
	}
	@Override
	public String setTestDesigner() {
		return "nellyoliviab@hexaware.com";
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_032_PoloOrigenFueraDeZonaIstmo_CartaPorte3_test";
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
				+ "3.- Fecha de facturacion "+"\n"
				+ "4.- Estatus de transferencia (Shipped, closed o ambos) y (Ruta, Ola o Transterencia)"+"\n"
				+ "5.- Fecha inicial BOL"+"\n"
				+ "6.- Datos del transportista validos para el Cedis, plaza o terceros";
	}
}
