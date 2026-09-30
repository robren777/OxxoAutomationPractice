package WEB.cartaPorte.cartaPortev3CHG0040003;

import static org.junit.Assert.assertTrue;
import static org.testng.Assert.assertEquals;

import java.util.HashMap;

import org.sikuli.script.FindFailed;
import org.testng.annotations.Test;

import WEB.cartaPorte.objectModelPortalCP.CartaPorteTercerosTiendas;
import WEB.cartaPorte.objectModelPortalCP.LoginCP;
import WEB.cartaPorte.objectModelPortalCP.MenuPortal;
import WEB.cartaPorte.objectModelPortalCP.PortalCartaPorte;
import modelo.BaseExecution;
import utils.password.PasswordUtil;
import utils.selenium.ChromeTest;
import utils.selenium.SeleniumUtil;

public class ATC_FT_006_GeneracionCartaPorteTercerosTiendas_CartaPorte3 extends BaseExecution{

	@Test(dataProvider = "data-provider")
	public void ATC_FT_006_GeneracionCartaPorteTercerosTiendas_CartaPorte3_test(HashMap<String, String> data) throws FindFailed {
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
		String mesInicial=data.get("mesInicial");
		String diaInicial=data.get("diaInicial");
		String anioInicial=data.get("anioInicial");
		SeleniumUtil u = new SeleniumUtil(new ChromeTest(), true);
		
		LoginCP login = new LoginCP(u, testCase, portalurl);
		MenuPortal portalmenu= new MenuPortal(u, testCase);
		PortalCartaPorte portalCP= new PortalCartaPorte(u, testCase);
		
		CartaPorteTercerosTiendas cartaPorteTienda=new CartaPorteTercerosTiendas(u, testCase,portalCP);
		
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
		boolean encontrarMenuCPT=portalCP.encontrarMenuOpcionesCartaPorteTerceros();
		assertTrue("No se encontro Menu de Carta Porte Terceros", encontrarMenuCPT);
		String encontrarOpcionMenuCPT=portalCP.encontrarOpcionesCartaPorteTerceros(tipoCartaPorte);
		assertEquals(encontrarOpcionMenuCPT, "Carta Porte Terceros Tiendas", "No se encontro la opcion: Carta Porte Terceros Tiendas");
		
		//PASO 4: 
		addStep("Seleccionar opcion \"Carta Porte Terceros Tiendas\"");
		String seleccionarCPT=portalCP.seleccionarOpcionCartaPorte(tipoCartaPorte);
		assertEquals(seleccionarCPT, "Terceros Tiendas", "No se encontro el texto: Terceros Tiendas");
		
		//PASO 5
		addStep("Capturar campo: Origen");
		String campoActual="origen";
		boolean encontrarCasilla= cartaPorteTienda.capturarCasilla(origen, campoActual,true,true);
		assertTrue(mensajeCaptura+campoActual, encontrarCasilla);
		campoActual="fechaFacturacion";
		encontrarCasilla=cartaPorteTienda.encontrarElemento(campoActual);
		assertTrue(mensajeEncontrar+campoActual, encontrarCasilla);
		
		//PASO 6
		addStep("Seleccionar elemento visual de selección de fecha");
		encontrarCasilla= cartaPorteTienda.seleccionarElemento(campoActual, true,"");
		assertTrue(mensajeSeleccion+campoActual, encontrarCasilla);
		campoActual="botonAnio";
		encontrarCasilla=cartaPorteTienda.encontrarElemento(campoActual);
		assertTrue(mensajeEncontrar+campoActual, encontrarCasilla);
		
		
		//PASO 7
		addStep("Seleccionar fecha de facturación (anio, mes, dia)");
		encontrarCasilla= cartaPorteTienda.seleccionarElemento(campoActual, false,"");
		assertTrue(mensajeSeleccion+campoActual, encontrarCasilla);
		campoActual="anio";
		encontrarCasilla= cartaPorteTienda.seleccionarElemento(campoActual,false, anio);
		assertTrue(mensajeSeleccion+campoActual, encontrarCasilla);
		campoActual="mes";
		encontrarCasilla= cartaPorteTienda.seleccionarElemento(campoActual,false, mes);
		assertTrue(mensajeSeleccion+campoActual, encontrarCasilla);
		campoActual="dia";
		encontrarCasilla= cartaPorteTienda.seleccionarElemento(campoActual,true, dia);
		assertTrue(mensajeSeleccion+campoActual, encontrarCasilla);
		campoActual="estatusTransferencia";
		encontrarCasilla=cartaPorteTienda.encontrarElemento(campoActual);
		assertTrue(mensajeEncontrar+campoActual, encontrarCasilla);
		
		//PASO 8 Seleccionar opciones de checkbox  1 (Shipped, Closed o Ambos) y 2 (Ruta, Ola, Transferencia)
		addStep("Seleccion de opciones estatus transferencia");
		campoActual="opciones1";
		encontrarCasilla= cartaPorteTienda.seleccionarElemento(campoActual,false, check1);
		assertTrue(mensajeSeleccion+campoActual, encontrarCasilla);
		campoActual="opciones2";
		encontrarCasilla= cartaPorteTienda.seleccionarElemento(campoActual,true, check2);
		assertTrue(mensajeSeleccion+campoActual, encontrarCasilla);
		campoActual=cartaPorteTienda.obtenerOpcion(check2);
		encontrarCasilla=cartaPorteTienda.encontrarElemento(campoActual);
		assertTrue(mensajeEncontrar+campoActual, encontrarCasilla);
		
		//PASO 9
		addStep("Capturar descripción en casilla");
		encontrarCasilla= cartaPorteTienda.capturarCasilla(descripcion, campoActual,false,true);
		assertTrue(mensajeCaptura+campoActual, encontrarCasilla);
		campoActual="botonBuscar";
		encontrarCasilla=cartaPorteTienda.encontrarElemento(campoActual);
		assertTrue(mensajeEncontrar+campoActual, encontrarCasilla);
		
		//PASO11
		addStep("Seleccionar tiendas");
		campoActual="seleccionaTiendas";
		encontrarCasilla= cartaPorteTienda.seleccionarElemento(campoActual,true, "");
		assertTrue(mensajeSeleccion+campoActual, encontrarCasilla);
		campoActual="seleccionDerecha";
		encontrarCasilla=cartaPorteTienda.encontrarElemento(campoActual);
		assertTrue(mensajeEncontrar+campoActual, encontrarCasilla);
		
		//PASO 12
		addStep("Seleccionar elemento visual para mover tiendas");
		encontrarCasilla= cartaPorteTienda.seleccionarElemento(campoActual,false, "");
		assertTrue(mensajeSeleccion+campoActual, encontrarCasilla);
		campoActual="seleccionaTiendasIzquierda";
		encontrarCasilla= cartaPorteTienda.seleccionarElemento(campoActual,true, "");
		assertTrue(mensajeSeleccion+campoActual, encontrarCasilla);
		campoActual="fechainicialBOL";
		encontrarCasilla=cartaPorteTienda.encontrarElemento(campoActual);
		assertTrue(mensajeEncontrar+campoActual, encontrarCasilla);
		
		//PASO 13
		addStep("Seleccionar elemento visual de Fecha Inicial BOL");
		encontrarCasilla= cartaPorteTienda.seleccionarElemento(campoActual,true, "");
		assertTrue(mensajeSeleccion+campoActual, encontrarCasilla);
		campoActual="botonAnio";
		encontrarCasilla= cartaPorteTienda.seleccionarElemento(campoActual,false, "");
		assertTrue(mensajeSeleccion+campoActual, encontrarCasilla);
		
		//PASO 14
		addStep("Seleccionar fecha Inicial BOL");
		campoActual="anio";
		encontrarCasilla= cartaPorteTienda.seleccionarElemento(campoActual,false, anioInicial);
		assertTrue(mensajeSeleccion+campoActual, encontrarCasilla);
		campoActual="mes";
		encontrarCasilla= cartaPorteTienda.seleccionarElemento(campoActual,false, mesInicial);
		assertTrue(mensajeSeleccion+campoActual, encontrarCasilla);
		campoActual="dia";
		encontrarCasilla= cartaPorteTienda.seleccionarElemento(campoActual,true, diaInicial);
		assertTrue(mensajeSeleccion+campoActual, encontrarCasilla);
		portalCP.bajarScroll();
		campoActual="botonBuscarBOL";
		encontrarCasilla=cartaPorteTienda.encontrarElemento(campoActual);
		assertTrue(mensajeEncontrar+campoActual, encontrarCasilla);
		
		//PASO 15
		addStep("Seleccionar boton de busqueda BOL");
		encontrarCasilla= cartaPorteTienda.seleccionarElemento(campoActual,false, anioInicial);
		assertTrue(mensajeSeleccion+campoActual, encontrarCasilla);
		portalCP.bajarScroll();
		campoActual="nombrarArchivo";
		addStep("Capturar campo: Nombre de archivo");
		encontrarCasilla= cartaPorteTienda.capturarCasilla("CartaPorteT"+portalCP.generarNumeroAleatorio(100, 1), campoActual,false,true);
		assertTrue(mensajeCaptura+campoActual, encontrarCasilla);
		campoActual="seleccionarCSV";
		encontrarCasilla=cartaPorteTienda.encontrarElemento(campoActual);
		assertTrue(mensajeEncontrar+campoActual, encontrarCasilla);
		
		//PASO 16
		addStep("Generar archivo CSV");
		encontrarCasilla= cartaPorteTienda.seleccionarElemento(campoActual,true, "");
		assertTrue(mensajeSeleccion+campoActual, encontrarCasilla);
		campoActual="seleccionarXLS";
		encontrarCasilla=cartaPorteTienda.encontrarElemento(campoActual);
		assertTrue(mensajeEncontrar+campoActual, encontrarCasilla);
		
		//PASO 17
		addStep("Seleccionar tipo de archivo XLS");
		encontrarCasilla= cartaPorteTienda.seleccionarElemento(campoActual,true, "");
		assertTrue(mensajeSeleccion+campoActual, encontrarCasilla);
		
		//PASO 18
		campoActual="generarArchivo";
		addStep("Generar archivo XLS");
		encontrarCasilla= cartaPorteTienda.seleccionarElemento(campoActual,true, "");
		assertTrue(mensajeSeleccion+campoActual, encontrarCasilla);
		u.close();
	}
	
	@Override
	public String setTestDescription() {
		return "Validar la no afectacion de los cambios de carta porte 3.0 en el flujo de carta porte terceros tiendas";
	}
	
	@Override
	public String setTestDesigner() {
		return "nellyoliviab@hexaware.com";
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_006_GeneracionCartaPorteTercerosTiendas_CartaPorte3_test";
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
				+ "4.- Fecha de facturacion"+"\n"
				+ "4.- Estatus de transferencia"+"\n"
				+ "5.- Tipo de traslado";
	}

}
