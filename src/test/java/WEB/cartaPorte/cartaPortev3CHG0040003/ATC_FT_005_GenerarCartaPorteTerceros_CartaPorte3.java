package WEB.cartaPorte.cartaPortev3CHG0040003;

import static org.junit.Assert.assertTrue;
import static org.testng.Assert.assertEquals;

import java.util.HashMap;

import org.sikuli.script.FindFailed;
import org.testng.annotations.Test;

import WEB.cartaPorte.objectModelPortalCP.CartaPorteTerceros;
import WEB.cartaPorte.objectModelPortalCP.LoginCP;
import WEB.cartaPorte.objectModelPortalCP.MenuPortal;
import WEB.cartaPorte.objectModelPortalCP.PortalCartaPorte;
import modelo.BaseExecution;
import utils.password.PasswordUtil;
import utils.selenium.ChromeTest;
import utils.selenium.SeleniumUtil;

public class ATC_FT_005_GenerarCartaPorteTerceros_CartaPorte3 extends BaseExecution{
	
	@Test(dataProvider = "data-provider")
	public void ATC_FT_005_GenerarCartaPorteTerceros_CartaPorte3_test(HashMap<String, String> data) throws FindFailed {
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
		String tipoCartaPorte =data.get("tipoCartaPorte");
		String origen =data.get("origen");
		String destino =data.get("destino");
		String mes=data.get("mes");
		String dia=data.get("dia");
		String anio=data.get("anio");
		String check1=data.get("check1");
		String check2=data.get("check2");
		String descripcion=data.get("descripcion");
		String tipoTraslado=data.get("tipoTraslado");
		
		SeleniumUtil u = new SeleniumUtil(new ChromeTest(), true);
		LoginCP login = new LoginCP(u, testCase, portalurl);
		PortalCartaPorte portalCP= new PortalCartaPorte(u, testCase);
		MenuPortal portalmenu= new MenuPortal(u, testCase);
		CartaPorteTerceros portalTerceros= new CartaPorteTerceros(u, testCase, portalCP);
		
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
		assertEquals(encontrarOpcionMenuCPT, "Carta Porte Terceros", "No se encontro la opcion: Carta Porte Terceros");
		
		//PASO 4: 
		addStep("Seleccionar opcion \"Carta Porte Terceros\"");
		String seleccionarCPT=portalCP.seleccionarOpcionCartaPorte(tipoCartaPorte);
		assertEquals(seleccionarCPT, "Carta Porte Terceros", "No se encontro el texto: Carta Porte Terceros");
		
		//PASO 5
		addStep("Capturar campo: Origen");
		String campoActual="origen";
		boolean encontrarCasilla= portalTerceros.capturarCasilla(origen, campoActual,true,true);
		assertTrue(mensajeCaptura+campoActual, encontrarCasilla);
		campoActual="destino";
		encontrarCasilla=portalTerceros.encontrarElemento(campoActual);
		assertTrue(mensajeEncontrar+campoActual, encontrarCasilla);
				
		//PASO 6:
		addStep("Capturar campo: Destino");
		campoActual="destino";
		encontrarCasilla= portalTerceros.capturarCasilla(destino, campoActual,true,true);
		assertTrue(mensajeCaptura+campoActual, encontrarCasilla);
		campoActual="fechaFacturacion";
		encontrarCasilla=portalTerceros.encontrarElemento(campoActual);
		assertTrue(mensajeEncontrar+campoActual, encontrarCasilla);
		
		//PASO 7:
		addStep("Seleccionar elemento visual de selección de fecha");
		encontrarCasilla= portalTerceros.seleccionarElemento(campoActual,true,"");
		assertTrue(mensajeCaptura+campoActual, encontrarCasilla);
		
		//PASO 8:
		addStep("Seleccionar fecha de facturación (año, mes, día)");
		campoActual="botonAnio";
		encontrarCasilla= portalTerceros.seleccionarElemento(campoActual,false, anio);
		assertTrue(mensajeSeleccion+campoActual, encontrarCasilla);
		campoActual="anio";
		encontrarCasilla= portalTerceros.seleccionarElemento(campoActual,false, anio);
		assertTrue(mensajeSeleccion+campoActual, encontrarCasilla);
		campoActual="mes";
		encontrarCasilla= portalTerceros.seleccionarElemento(campoActual,false, mes);
		assertTrue(mensajeSeleccion+campoActual, encontrarCasilla);
		campoActual="dia";
		encontrarCasilla= portalTerceros.seleccionarElemento(campoActual,true, dia);
		assertTrue(mensajeSeleccion+campoActual, encontrarCasilla);
		campoActual="estatusTransferencia";
		encontrarCasilla=portalTerceros.encontrarElemento(campoActual);
		assertTrue(mensajeEncontrar+campoActual, encontrarCasilla);
		
		//PASO 9 Seleccionar opciones de checkbox  1 (Shipped, Closed o Ambos) y 2 (Ruta, Ola, Transferencia)
		addStep("Seleccion de opciones estatus transferencia");
		campoActual="opciones1";
		encontrarCasilla= portalTerceros.seleccionarElemento(campoActual,false, check1);
		assertTrue(mensajeSeleccion+campoActual, encontrarCasilla);
		campoActual="opciones2";
		encontrarCasilla= portalTerceros.seleccionarElemento(campoActual,true, check2);
		assertTrue(mensajeSeleccion+campoActual, encontrarCasilla);
		campoActual=portalTerceros.obtenerOpcion(check2);
		encontrarCasilla=portalTerceros.encontrarElemento(campoActual);
		assertTrue(mensajeEncontrar+campoActual, encontrarCasilla);
		
		//PASO 10: Capturar descripción en casilla de opción 2
		addStep("Capturar descripción en casilla");
		encontrarCasilla= portalTerceros.capturarCasilla(descripcion, campoActual,false,true);
		assertTrue(mensajeCaptura+campoActual, encontrarCasilla);
		campoActual="botonBuscar";
		encontrarCasilla=portalTerceros.encontrarElemento(campoActual);
		assertTrue(mensajeEncontrar+campoActual, encontrarCasilla);
		
		//PASO 11: Seleccionar boton Buscar
		addStep("Seleccionar boton Buscar");
		encontrarCasilla= portalTerceros.seleccionarElemento(campoActual,true, "");
		assertTrue(mensajeSeleccion+campoActual, encontrarCasilla);
		campoActual="tablaRegistros";
		encontrarCasilla=portalTerceros.encontrarElemento(campoActual);
		assertTrue(mensajeEncontrar+campoActual, encontrarCasilla);
		
		//PASO 12: Seleccionar boton Buscar
		addStep("Seleccionar tiendas");
		campoActual="seleccionaTiendas";
		encontrarCasilla= portalTerceros.seleccionarElemento(campoActual,true, "");
		assertTrue(mensajeSeleccion+campoActual, encontrarCasilla);
		campoActual="seleccionDerecha";
		encontrarCasilla=portalTerceros.encontrarElemento(campoActual);
		assertTrue(mensajeEncontrar+campoActual, encontrarCasilla);
		
		//PASO 13: 
		addStep("Seleccionar elemento visual para mover tiendas");
		encontrarCasilla= portalTerceros.seleccionarElemento(campoActual,true, "");
		assertTrue(mensajeSeleccion+campoActual, encontrarCasilla);
		campoActual="tipoTraslado";
		encontrarCasilla=portalTerceros.encontrarElemento(campoActual);
		assertTrue(mensajeEncontrar+campoActual, encontrarCasilla);
		
		
		//PASO 14 Seleccionar opción (Todos, Refrigerados, Secos, Ambos)
		addStep("Seleccionar Tipo Traslado");
		encontrarCasilla= portalTerceros.seleccionarElemento(campoActual,true, tipoTraslado);
		assertTrue(mensajeSeleccion+campoActual, encontrarCasilla);
		campoActual="nombrarArchivo";
		encontrarCasilla=portalTerceros.encontrarElemento(campoActual);
		assertTrue(mensajeEncontrar+campoActual, encontrarCasilla);
		
		//PASO 15
		portalCP.bajarScroll();
		addStep("Capturar campo: Nombre de archivo");
		encontrarCasilla= portalTerceros.capturarCasilla("CartaPorteT"+portalCP.generarNumeroAleatorio(100, 1), campoActual,false,true);
		assertTrue(mensajeCaptura+campoActual, encontrarCasilla);
		campoActual="seleccionarCSV";
		encontrarCasilla=portalTerceros.encontrarElemento(campoActual);
		assertTrue(mensajeEncontrar+campoActual, encontrarCasilla);
		
		//PASO16
		addStep("Seleccionar tipo de archivo CSV");
		encontrarCasilla= portalTerceros.seleccionarElemento(campoActual,true, "");
		assertTrue(mensajeSeleccion+campoActual, encontrarCasilla);
		campoActual="generarArchivo";
		encontrarCasilla=portalTerceros.encontrarElemento(campoActual);
		assertTrue(mensajeEncontrar+campoActual, encontrarCasilla);
		
		//PASO 17
		addStep("Generar archivo CSV");
		encontrarCasilla= portalTerceros.seleccionarElemento(campoActual,true, "");
		assertTrue(mensajeSeleccion+campoActual, encontrarCasilla);
		campoActual="seleccionarXLS";
		encontrarCasilla=portalTerceros.encontrarElemento(campoActual);
		assertTrue(mensajeEncontrar+campoActual, encontrarCasilla);
		
		//PASO 18
		addStep("Seleccionar tipo de archivo XLS");
		encontrarCasilla= portalTerceros.seleccionarElemento(campoActual,true, "");
		assertTrue(mensajeSeleccion+campoActual, encontrarCasilla);
		
		//PASO 19
		campoActual="generarArchivo";
		addStep("Generar archivo XLS");
		encontrarCasilla= portalTerceros.seleccionarElemento(campoActual,true, "");
		assertTrue(mensajeSeleccion+campoActual, encontrarCasilla);
		
		u.close();
		
		
				
	}

	
	@Override
	public String setTestDescription() {
		return "Validar la no afectacion de los cambios de carta porte 3.0 en el flujo de carta porte terceros";
	}
	
	@Override
	public String setTestDesigner() {
		return "nellyoliviab@hexaware.com";
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_005_GenerarCartaPorteTerceros_CartaPorte3_test";
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
