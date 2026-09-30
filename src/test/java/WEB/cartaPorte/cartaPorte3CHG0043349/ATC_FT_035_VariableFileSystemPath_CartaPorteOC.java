package WEB.cartaPorte.cartaPorte3CHG0043349;

import static org.junit.Assert.assertTrue;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;

import java.io.File;
import java.sql.SQLException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;

import org.sikuli.script.FindFailed;
import org.sikuli.script.Pattern;
import org.testng.annotations.Test;

import WEB.cartaPorte.objectModelPortalCP.AdministracionParametros;
import WEB.cartaPorte.objectModelPortalCP.CartaPorteOC;
import WEB.cartaPorte.objectModelPortalCP.EditarConstante;
import WEB.cartaPorte.objectModelPortalCP.LoginCP;
import WEB.cartaPorte.objectModelPortalCP.MenuPortal;
import WEB.cartaPorte.objectModelPortalCP.MonitoreoPAC;
import WEB.cartaPorte.objectModelPortalCP.PortalCartaPorte;
import WEB.util.MetodosSikuli;
import modelo.BaseExecution;
import utils.SikuliUtil;
import utils.password.PasswordUtil;
import utils.selenium.ChromeTest;
import utils.selenium.SeleniumUtil;

public class ATC_FT_035_VariableFileSystemPath_CartaPorteOC extends BaseExecution{
	@Test(dataProvider = "data-provider")
	public void  ATC_FT_035_VariableFileSystemPath_CartaPorteOC_test(HashMap<String, String> data) throws FindFailed, SQLException {
		String portalurl = data.get("server");
		String userName=data.get("user");
		String password = PasswordUtil.decryptPassword(data.get("password"));
		String pac = data.get("pac");
		String campo=data.get("campo"); 
		String opcionAdmon=data.get("opcionAdmon");
		String nombreVariable=data.get("nombreVariable");
		String contenidoVariable=data.get("contenidoVariable");
		String tipoCartaPorte = data.get("tipoCartaPorte");
		String origen=data.get("origen");
		String destino = data.get("destino");
		String ordenCompra = data.get("ordenCompra");
		String nombreChofer = data.get("nombreChofer");
		String numeroEconomico = data.get("numeroEconomico");
		String correo = data.get("correo");
		String rutaGuardarTira = data.get("rutaGuardarTira");
		String configuraPAC=data.get("configuraPAC"); 
		// Informacion que se imprime en el reporte 
		testCase.setTest_Description("");
		testCase.setTest_Description(campo+"\n"+" . PAC de prueba: "+pac);
		String opcionMonitoreo=data.get("opcionMonitoreo"); 
		SeleniumUtil u=new SeleniumUtil(new ChromeTest(), true);
		LoginCP login = new LoginCP(u, testCase, portalurl);
		MenuPortal portalmenu= new MenuPortal(u, testCase);
		PortalCartaPorte portalCP= new PortalCartaPorte(u, testCase);	
		MonitoreoPAC monitoreoPAC= new MonitoreoPAC(u,testCase);
		AdministracionParametros admonParametro=new AdministracionParametros(u,testCase);
		EditarConstante editarVariable=new EditarConstante(u,testCase);
		CartaPorteOC portalOC= new CartaPorteOC(u, testCase, portalCP);
		String mensajeEncontrar= "No fue posible encontrar elemento: ";
		String mensajeSeleccion= "No fue posible seleccionar elemento: ";
		String mensajeCaptura= "No fue posible capturar datos ";
		SikuliUtil sk = new SikuliUtil();
		String pathImg = sk.PatternPath("WEB\\"+"CARTAPORTEW");
		Pattern pathimg = new Pattern();
		ArrayList<String> ListImage = new ArrayList<String>();
		HashMap<String, Pattern> ImgPatter = new HashMap<String, Pattern>();
		ArrayList<Pattern> patterns = new ArrayList<Pattern>();
		File Fol = new File(pathImg);
		MetodosSikuli p = new MetodosSikuli(sk, u, pathimg, ListImage, ImgPatter, patterns, Fol);
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
		
		/*
		 * PASO 3:
		 * Descripción del paso: Seleccionar "Portal Carta Porte"
		 * 
		 * Resultado Esperado: Encontrar menú "Administracion"
		 */
		addStep("Seleccionar: Portal Carta Porte ");
		portalmenu.seleccionarOpcionCartaPorte();
		boolean encontrarMenuCP=portalCP.encontrarMenuOpcionesCartaPorte();
		assertTrue("No se encontro Menu de Carta Porte", encontrarMenuCP);

		/*
		 * PASO 4:
		 * Descripción del paso: Seleccionar menú "Adminsitración"
		 * 
		 * Resultado Esperado: Encontrar opción "Unidades"
		 */
		String encontrarOpcionMenu="";
		String seleccionarElemento="";
		String campoActual="";
		boolean seleccionarCasilla=false;
		if(configuraPAC.compareToIgnoreCase("SI")==0) {
			addStep("Seleccionar Menu Monitoreo");
			encontrarOpcionMenu=portalCP.encontrarOpcionesMonitoreo(opcionMonitoreo);
			assertEquals(encontrarOpcionMenu, "Configuración PAC", "No se encontro la opcion: Configuración PAC");
			//PASO 5
			addStep("Seleccionar opcion Configuración PAC");
			seleccionarElemento=portalCP.seleccionarOpcionMonitoreo(opcionMonitoreo);
			assertEquals(seleccionarElemento, "PAC Configuración", "No se encontro el texto:  PAC Configuración");
			//PASO 6
			addStep("Seleccionar Konesh en Campo Pac Activo");
			campoActual="botonPAC";
			seleccionarCasilla=monitoreoPAC.seleccionarElemento(campoActual,true,pac);
			assertTrue(mensajeSeleccion+campoActual, seleccionarCasilla);
			//PASO 7
			addStep("Seleccionar Guardar");
			campoActual="botonGuardar";
			seleccionarCasilla=monitoreoPAC.seleccionarElemento(campoActual,true,null);
			assertTrue(mensajeSeleccion+campoActual, seleccionarCasilla);
		}
		//PASO 8
		addStep("Seleccionar Parametros Generales en Menu Administracion");
		campoActual="parametros";
		encontrarOpcionMenu=portalCP.encontrarOpcionesAdministracione(opcionAdmon,false);
		assertEquals(encontrarOpcionMenu, "Parametros Generales", "No se encontro la opcion: Parametros Generales");
		seleccionarElemento=portalCP.seleccionarOpcionAdministracion(campoActual);
		assertEquals(seleccionarElemento, "Listado de Constantes", "No se encontro el texto: Listado de Equipos");
		//PASO 9y 10
		addStep("Buscar variable: "+nombreVariable);
		seleccionarCasilla=admonParametro.encontrarYEditarVariable(nombreVariable, this, true);
		assertTrue(mensajeEncontrar+nombreVariable, seleccionarCasilla);
		//PASO11
		addStep("Obtener valor actual");
		String valorActual=editarVariable.limpiaryCapturarValor(contenidoVariable);
		testCase.addScreenShotCurrentStep("ModificarCasilla");
		campoActual="botonCancelar";
		seleccionarCasilla=editarVariable.encontrarElemento(campoActual);
		assertTrue(mensajeEncontrar+nombreVariable, seleccionarCasilla);
		campoActual="botonCancelar";
		seleccionarCasilla=editarVariable.seleccionarElemento(campoActual, false);
		assertTrue(mensajeSeleccion+nombreVariable, seleccionarCasilla);
		//PASO 4: 
		addStep("Seleccionar Menu de Carta Porte");
		String encontrarOpcionMenuCP=portalCP.encontrarOpcionesCartaPorte(tipoCartaPorte);
		assertEquals(encontrarOpcionMenuCP, "Carta Porte OC", "No se encontro la opcion: Carta Porte OC");
		u.getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		//PASO 5:
		String seleccionarCPOC="";
		do {
			addStep("Seleccionar opcion \"Carta Porte OC\"");
			seleccionarCPOC=portalCP.seleccionarOpcionCartaPorte(tipoCartaPorte);
		}while(seleccionarCPOC.compareTo("Carta Porte OC")!=0);
		assertEquals(seleccionarCPOC, "Carta Porte OC", "No se encontro el texto: Carta Porte OC");
		
		//PASO 6:
		addStep("Capturar campo: Origen");
		campoActual="origen";
		boolean encontrarCasilla= portalOC.capturarCasilla(origen, campoActual,true,true);
		assertTrue(mensajeCaptura+campoActual, encontrarCasilla);
		campoActual="destino";
		encontrarCasilla=portalOC.encontrarElemento(campoActual);
		assertTrue(mensajeEncontrar+campoActual, encontrarCasilla);//PASO 7:
		
		//PASO 7
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
			u.getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
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
		
		
		
		addStep("Obtener valor de campo: Logistica inversa");
		campoActual="estatusLogisticaInversa";
		String obtenerValor=portalOC.obtenerTexto(campoActual);
		assertEquals(obtenerValor, "Seleccionado", "El valor inicial de Logistica Inversa debe msotrarse seleccionable");
		getTestCase().addTextEvidenceCurrentStep("El estatus se mantiene seleccionado");
		
	
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
		u.hardWait(15);
		boolean tiraImpresion=true;
		try {
			p.clickImagen("printCP");
		}catch (Exception e) {
			tiraImpresion=false;
		}
		
		assertFalse(tiraImpresion, "El documento no deberia generarse.");
		getTestCase().addTextEvidenceCurrentStep("Validar que no se genere el documento de Tira de impresion y ademas, el  documento .ZIP se encuentre vacio");
				
		u.close();
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

	@Override
	public String setTestDescription() {
		// TODO Auto-generated method stub
		return "Obtener Variable File System Path para Carta Porte OC.";
	}

	@Override
	public String setTestDesigner() {
		return "nellyoliviab@hexaware.com";
	}
	@Override
	public String setTestFullName() {
		// TODO Auto-generated method stub
		return "ATC_FT_035_VariableFileSystemPath_CartaPorteOC_test";
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

}

