package WEB.cartaPorte.cartaPorte3CHG0043349;

import static org.junit.Assert.assertTrue;
import static org.testng.Assert.assertEquals;

import java.io.File;
import java.sql.SQLException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;

import org.sikuli.hotkey.Keys;
import org.sikuli.script.FindFailed;
import org.sikuli.script.Pattern;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import WEB.cartaPorte.objectModelPortalCP.AdministracionParametros;
import WEB.cartaPorte.objectModelPortalCP.CartaPorteManual;
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

public class ATC_FT_018_ConfiguraPACyVariableRFC_CartaPorteManual extends BaseExecution{
	@Test(dataProvider = "data-provider")
	public void ATC_FT_018_ConfiguraPACyVariableRFC_CartaPorteManual_test(HashMap<String, String> data) throws FindFailed, SQLException {
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
		String destino=data.get("destino");
		String tipoArticulo=data.get("tipoArticulo");
		String registro= data.get("registro");
		String nombreChofer=data.get("nombreChofer"); 
		String numeroEconomico=data.get("numeroEconomico");
		String correo=data.get("correo"); 
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
		CartaPorteManual manual= new CartaPorteManual(u, testCase,portalCP);
		String check=data.get("check");
		SikuliUtil sk = new SikuliUtil();
		String pathImg = sk.PatternPath("WEB\\"+"CARTAPORTEW");
		Pattern pathimg = new Pattern();
		ArrayList<String> ListImage = new ArrayList<String>();
		HashMap<String, Pattern> ImgPatter = new HashMap<String, Pattern>();
		ArrayList<Pattern> patterns = new ArrayList<Pattern>();
		File Fol = new File(pathImg);
		MetodosSikuli p = new MetodosSikuli(sk, u, pathimg, ListImage, ImgPatter, patterns, Fol);
		String mensajeEncontrar= "No fue posible encontrar elemento: ";
		String mensajeSeleccion= "No fue posible seleccionar elemento: ";
		String mensajeCaptura= "No fue posible capturar datos ";
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
		addStep("Limpiar y capturar nuevo valor");
		String valorActual=editarVariable.limpiaryCapturarValor(contenidoVariable);
		testCase.addScreenShotCurrentStep("ModificarCasilla");
		campoActual="botonGuardar";
		seleccionarCasilla=editarVariable.encontrarElemento(campoActual);
		assertTrue(mensajeEncontrar+nombreVariable, seleccionarCasilla);
		//PASO 15
		addStep("Seleccionar botón Guardar");
		seleccionarCasilla=editarVariable.seleccionarElemento(campoActual, false);
		assertTrue(mensajeSeleccion+nombreVariable, seleccionarCasilla);
		campoActual="AlertaGuardado";
		campoActual=admonParametro.obtenerTexto(campoActual,true);
		campoActual=campoActual.substring(1,campoActual.length()-1);
		assertEquals(campoActual, "Constante actualizada exitosamente", "No se actualizo exitosamente la variable: "+nombreVariable);
			
		u.getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(15));		
		//PASO 4: 
		addStep("Seleccionar Menu de Carta Porte");
		String encontrarOpcionMenuCP=portalCP.encontrarOpcionesCartaPorte(tipoCartaPorte);
		assertEquals(encontrarOpcionMenuCP, "Carta Porte Manual", "No se encontro la opcion: Carta Porte Manual");
		
		//PASO 5:
		addStep("Seleccionar opcion: Carta Porte manual");
		String seleccionarCPManual=portalCP.seleccionarOpcionCartaPorte(tipoCartaPorte);
		assertEquals(seleccionarCPManual, "Carta Porte Manual", "No se encontro el texto: Carta Porte Manual");//PASO 29
		addStep("Capturar campo: Origen");
		campoActual="origen";
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
		campoActual="logisticaInversa";
		addStep("Se verifica que el campo se ecuentre seleccionado");
		encontrarCasilla= manual.seleccionarElemento(campoActual,true);
		assertTrue("El campo de logistica inversa no se encuentra seleccionado", encontrarCasilla);
							
				
		//PASO 19
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
		encontrarCasilla= manual.seleccionarElemento(campoActual,false);
		assertTrue(mensajeSeleccion+campoActual, encontrarCasilla);
						
		encontrarCasilla=manual.determinarEstatusTimbrado();
		assertTrue("Timbrado fallo", encontrarCasilla);		
		
		//PASO 20
		addStep("Captura de correo para enviar documentos");
		campoActual="capturaCorreo";
		encontrarCasilla= manual.capturarCasilla(correo, campoActual,false,false);
		assertTrue(mensajeCaptura+campoActual, encontrarCasilla);
		campoActual="botonEnviar";
		encontrarCasilla= manual.seleccionarElemento(campoActual,false);
		assertTrue(mensajeSeleccion+campoActual, encontrarCasilla);
		getTestCase().addTextEvidenceCurrentStep("Se muestra tira de impresion");
		u.hardWait(8);
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
		String nombreTira="CartaPorteTiraDocCEDIS_"+String.valueOf(portalCP.generarNumeroAleatorio(300, 201))+".pdf";
		getTestCase().addTextEvidenceCurrentStep("Se guarda documento: "+nombreTira);
		sk.screen.type(nombreTira);
		u.hardWait(15);
		sk.screen.type(Keys.TAB);
		u.hardWait(15);
		p.clickImagen("saveCP");
		u.hardWait(15);
		getTestCase().addTextEvidenceCurrentStep("Validar que en la documentacion, la variable "+nombreVariable+" se actualice como: "+contenidoVariable);
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
		// TODO Auto-generated method stub
		return "Configurar PAP y variable RFC en Carta Porte Manual.";
	}

	@Override
	public String setTestDesigner() {
		return "nellyoliviab@hexaware.com";
	}

	@Override
	public String setTestFullName() {
		// TODO Auto-generated method stub
		return "ATC_FT_018_ConfiguraPACyVariableRFC_CartaPorteManual_test";
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
