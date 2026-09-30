package WEB.cartaPorte.cartaPorte3CHG0041763;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.testng.Assert.assertEquals;

import java.io.File;
import java.sql.SQLException;
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
import WEB.cartaPorte.objectModelPortalCP.CartaPorteCedis;
import WEB.cartaPorte.objectModelPortalCP.LoginCP;
import WEB.cartaPorte.objectModelPortalCP.MenuPortal;
import WEB.cartaPorte.objectModelPortalCP.PortalCartaPorte;
import WEB.cartaPorte.objectModelPortalCP.UnidadesDetalleEquipo;
import WEB.estandarizacionHomologacionImpuestos.pageObjectModelHomo.MetodoGeneral;
import WEB.util.GlobalVariables;
import WEB.util.MetodosSikuli;
import modelo.BaseExecution;
import utils.SikuliUtil;
import utils.password.PasswordUtil;
import utils.selenium.ChromeTest;
import utils.selenium.SeleniumUtil;

public class ATC_FT_001_Agregar_Unidad_CEDIS_CartaPorte3 extends BaseExecution {
	/**
	 * PORTAL CARTA PORTE/ CARTA PORTE / CARTA PORTE CEDIS
	 * 
	 * Cambio a validar: Se agregan campos de capacidad y peso bruto a la pantalla de Unidades
	 * 
	 * DESCRIPCION: 
	 * El objetivo del script es generar una Unidad desde el Portal de Carta Porte/ Administracion / Unidades.
	 * Al crear la unidad, se genera la Carta Porte CEDIS, el número económico es el identificador capturado en la Unidad que se agrego.
	 * El peso bruto mostrado se encuentra en toneladas, en la pantalla de unidades se captura por kilos.
	 * Al generar los documentos de Carta Porte CEDIS, se debe imprimir el peso bruto en toneladas con los datos de la Unidad que se agrego.
	 * 
	 * REREQUISITOS: 
	 *		1. Levantar ticket para solicitar scceso a la base de datos : 
	 *			Connection Name: FCPFACQA
	 *			Host: 10.80.3.57
	 *			Port: 1522
	 *			SERVICE_NAME=FCPFACQA.FEMCOM.NET
	 *		
	 *      2. Actualizar los datos de Dataprovider:
	 *      	Usuario y contraseña: 6006023--> contactar a myriamguadalupem@hexaware.com para confirmar la contraseña
	 *      	Identificador-----(Identificador - número económico de la unidad)
	 *      	Serie-------------Número de cerie de la unidad
	 *          Placa-------------Placa del vehiculo
	 *          Validar que los suiguientes campos carguen informacion: 
	 *          -mes, día y anio, diaBOL, mesBOL y anioBOL
	 *          -Detalle: ejemplo: 11MAS006:
	 *          	Posicion 0 que corresponde a 1. Se refiere a la seleccion de los primeros check(1.Shipped, 2. Closed y 3. Ambos)
	 * 				Posicion 1 que corresponde a 1. Se refiere a la seleccion de la segunda seccion de chacks(1. Ruta, 2. Ola y 3. Transferencia)
	 * 			-Nombre chofer y numero economico, asegurarse que estos datos pertenezcan al origen capturado
	 * @author nellyoliviab@hexaware.com
	 */
	@Test(dataProvider = "data-provider")
	public void ATC_FT_001_Agregar_Unidad_CEDIS_CartaPorte3_test(HashMap<String, String> data) throws FindFailed, SQLException {
		//Inicio de sesion
		String portalurl = data.get("server");
		String userName=data.get("user");
		String password = PasswordUtil.decryptPassword(data.get("password"));
		// PAC: Monitoreo/Configuracion, es el PAC para generar el timbrado(Check box seleccionado: Konesh o Diverza)
		String pac = data.get("pac");
		// Campo: Descipcion del caso de prueba, se agrega en el DataProvider por si se requiere ejecutar el mismo script 
		//  con diferentes combinaciones de datos
		String campo=data.get("campo"); 
		// Menu para seleccionar unidades
		String opcionAdmon=data.get("opcionAdmon");
		//Datos de Unidad
		String identificador=data.get("identificador");
		String origenUnidad=data.get("origenUnidad");
		String confVehicular=data.get("confVehicular");
		String serie=data.get("serie");
		String modelo=data.get("modelo");
		String subtipoRemolque=data.get("subtipoRemolque");
		String capacidad=data.get("capacidad");
		String aseguradora=data.get("aseguradora");
		String permisoSct=data.get("permisoSCT");
		String crOrigen=data.get("crOrigen");
		String placa=data.get("placa");
		String vigenciaDia=data.get("vigenciaDia");
		String vigenciaMes=data.get("vigenciaMes");
		String vigenciaAnio=data.get("vigenciaAnio");
		String placaRemolque=data.get("placaRemolque");
		String pesoBrutoVehicular=data.get("pesoBrutoVehicular");
		String numeroPoliza=data.get("numeroPoliza");
		String numPermisoSCT=data.get("numPermisoSCT");
		// Datos de Carta Porte CEDIS
		String tipoCartaPorte = data.get("tipoCartaPorte");
		String mes=data.get("mes");
		String dia=data.get("dia");
		String anio=data.get("anio");
		String detalle=data.get("detalle");
		String nombreChofer=data.get("nombreChofer"); 
		String correo=data.get("correo"); 
		String mesBOL=data.get("mesBOL");
		String diaBOL=data.get("diaBOL");
		String anioBOL=data.get("anioBOL");
		String rutaGuardarTira = data.get("rutaGuardarTira");
		String bol = data.get("bol");
		// Informacion que se imprime en el reporte 
		testCase.setTest_Description("");
		testCase.setTest_Description(campo+"\n"+"PAC de prueba: "+pac);
		SeleniumUtil u=new SeleniumUtil(new ChromeTest(), true);
		LoginCP login = new LoginCP(u, testCase, portalurl);
		MenuPortal portalmenu= new MenuPortal(u, testCase);
		PortalCartaPorte portalCP= new PortalCartaPorte(u, testCase);
		AdministracionUnidades admonUnidades=new AdministracionUnidades(u, testCase);
		UnidadesDetalleEquipo unidadesDetalle=new UnidadesDetalleEquipo(u,testCase);
		String mensajeSeleccion= "No fue posible seleccionar elemento: ";
		String mensajeEncontrar= "No fue posible encontrar elemento: ";
		String mensajeCaptura= "No fue posible capturar datos ";
		String mensajeAutollenado="La información obtenida no coincide con la esperada";
		SikuliUtil sk = new SikuliUtil();
		String pathImg = sk.PatternPath("WEB\\"+"CARTAPORTEW");
		Pattern pathimg = new Pattern();
		ArrayList<String> ListImage = new ArrayList<String>();
		HashMap<String, Pattern> ImgPatter = new HashMap<String, Pattern>();
		ArrayList<Pattern> patterns = new ArrayList<Pattern>();
		File Fol = new File(pathImg);
		MetodosSikuli p = new MetodosSikuli(sk, u, pathimg, ListImage, ImgPatter, patterns, Fol);	
		utils.sql.SQLUtil dbFCPFACQA = new utils.sql.SQLUtil(GlobalVariables.DB_HOST_FCPFACQA,
				 GlobalVariables.DB_USER_FCPFACQA_MV, GlobalVariables.DB_PAST_FCPFACQA_MV);
		
		String selectIDORIGEN="SELECT DESCRIPCION FROM XXCAP.XXCAP_CEDIS_CAT"
				+ " WHERE IDORIGEN= '"+origenUnidad+"'";
		CartaPorteCedis portalCedis= new CartaPorteCedis(u,testCase,portalCP);
		MetodoGeneral metodoGeneral= new MetodoGeneral();
		Date fechaInicial= new Date();
		
		/*
		 * PASO 1:
		 * Descripción del paso: Ingresar a la liga de acceso: https://fcportalqa.femcom.net:8443/AccessControl/pages/login_form.jsf
		 * 
		 * Resultado Esperado: Encontrar mensaje: "Bienvenido a Oxxo"
		 */
			
		addStep("Ingresar a la liga de acceso: https://fcportalqa.femcom.net:8443/AccessControl/pages/login_form.jsf");
		u.get(login.getUrl());
		login.proceedToPortal();
		boolean cargarPagina=login.encontrarMensajeInicio();
		testCase.addScreenShotCurrentStep("CargarURL");
		assertTrue( "El sitio no fue cargado satisfactoriamente",cargarPagina);
		
		/*
		 * PASO 2:
		 * Descripción del paso: Capturar credenciales de acceso para iniciar sesión
		 * 
		 * Resultado Esperado: Encontrar mensaje: Encontrar mensaje: "Portal de Aplicaciones"
		 */
		addStep("Capturar credenciales de acceso para iniciar sesión");
		login.cicloLogin2(userName, password);
		String mensajePortal=portalmenu.accesoMenuPortal(userName, password);
		assertTrue("No se encontró mensaje: Portal de Aplicaciones"
		
		, mensajePortal.compareTo("Portal de Aplicaciones")==0);
		/*
		 * PASO 3:
		 * Descripción del paso: Seleccionar "Portal Carta Porte"
		 * 
		 * Resultado Esperado: Encontrar menú "Administracion"
		 */
		addStep("Seleccionar: Portal Carta Porte ");
		portalmenu.seleccionarOpcionCartaPorte();
		boolean encontrarMenuCP=portalCP.encontrarMenuOpcionesCartaPorte();
		assertTrue("No se encontro Menu de Carta Porte"
				
				, encontrarMenuCP);
		/*
		 * PASO 4:
		 * Descripción del paso: Seleccionar menú "Adminsitración"
		 * 
		 * Resultado Esperado: Encontrar opción "Unidades"
		 */
		addStep("Seleccionar Menu Administracion");
		String encontrarOpcionMenuAdmon=portalCP.encontrarOpcionesAdministracione(opcionAdmon,true);
		assertEquals(encontrarOpcionMenuAdmon, "Unidades", "No se encontro la opcion: Unidades"
				);
		/*
		 * PASO 5:
		 * Descripción del paso: Seleccionar opción "Unidades"
		 * 
		 * Resultado Esperado: Encontrar texto: "Listado de Equipo"
		 */
		addStep("Seleccionar opcion Unidades");
		String seleccionarElemento=portalCP.seleccionarOpcionAdministracion(opcionAdmon);
		assertEquals(seleccionarElemento, "Listado de Equipos", "No se encontro el texto: Listado de Equipos"
				);
		
		/*
		 * PASO 6:
		 * Descripción del paso: Seleccionar botón "Nuevo equipo" 
		 * 
		 * Resultado Esperado: Encontrar texto: "Detalle del Equipo"
		 */
		addStep("Seleccionar boton Nuevo Equipo");
		String campoActual="botonNuevoEquipo";
		boolean seleccionarCasilla=admonUnidades.seleccionarElemento(campoActual,true,null);
		assertTrue(mensajeSeleccion+campoActual, seleccionarCasilla);
		campoActual="textoDetalleDeEquipo";
		boolean encontrarCasilla=unidadesDetalle.encontrarElemento(campoActual);
		assertTrue(mensajeEncontrar+campoActual
				
				, encontrarCasilla);
		
		/*
		 * PASO 7:
		 * Descripción del paso: Capturar: Identificador
		 * 
		 * Resultado Esperado: Continuar en casilla Origen
		 */
		addStep("Capturar Identificador");
		campoActual="capturaIdentificador";
		boolean capturarCasilla=unidadesDetalle.capturarCasilla(identificador, campoActual,false, true);
		assertTrue(mensajeCaptura+campoActual
				
				, capturarCasilla);
		
		/*
		 * PASO 8:
		 * Descripción del paso: Capturar: Origen
		 * 
		 * Resultado Esperado: Obtener llenado automático de: Unidad Operativa
		 */
		addStep("Capturar Origen");
		campoActual="capturaOrigen";
		capturarCasilla=unidadesDetalle.capturarCasilla(origenUnidad, campoActual,true, true);
		assertTrue(mensajeCaptura+campoActual
				
				, capturarCasilla);
		getTestCase().addTextEvidenceCurrentStep("Se obtiene la Unidad Operativa a la que pertenece el dato de Origen");
		//Se obtiene el nombre del ID origen capturado en la base de datos
		utils.sql.SQLResult resultadoOrigen= dbFCPFACQA.executeQuery(selectIDORIGEN);
		testCase.addQueryEvidenceCurrentStep(resultadoOrigen);
		campoActual="obtenerUnidadOperativa";
		String autollenado=unidadesDetalle.obtenerAutollenado(campoActual,false);
		String origen=resultadoOrigen.getFirstData();
		// Se realiza la comparación de Origen: el que se obtiene de la base de datos vs el que se carga en automatico en la pantalla
		assertEquals(resultadoOrigen.getFirstData().toLowerCase(), autollenado.toLowerCase()
				, mensajeAutollenado );
		
		/*
		 * PASO 9:
		 * Descripción del paso: Capturar: Conf. Vehicular
		 * Resultado Esperado: Obtener llenado automático de: Descripción
		 */
		addStep("Capturar Conf. Vehicular");
		campoActual="confVehicular";
		capturarCasilla=unidadesDetalle.capturarCasilla(confVehicular, campoActual,true, true);
		assertTrue(mensajeCaptura+campoActual, capturarCasilla);
		campoActual="obtenerDescripcion";
		autollenado=unidadesDetalle.obtenerAutollenado(campoActual,false);
		assertFalse(mensajeAutollenado
				
				, autollenado.isEmpty());
		
		/*
		 * PASO 10:
		 * Descripción del paso: Capturar: Serie
		 * Resultado Esperado: Continuar en casilla Modelo
		 */
		addStep("Capturar Serie");
		campoActual="serie";
		capturarCasilla=unidadesDetalle.capturarCasilla(serie, campoActual,false, true);
		assertTrue(mensajeCaptura+campoActual
				
				, capturarCasilla);
		
		/*
		 * PASO 11:
		 * Descripción del paso: Capturar: Modelo
		 * Resultado Esperado: Continuar en casilla Subtipo Remolque
		 */
		addStep("Capturar Modelo");
		campoActual="modelo";
		capturarCasilla=unidadesDetalle.capturarCasilla(modelo, campoActual,false, true);
		assertTrue(mensajeCaptura+campoActual
				
				, capturarCasilla);
		
		/*
		 * PASO 12:
		 * Descripción del paso: Capturar: Subtipo Remolque
		 * Resultado Esperado: Obtener llenado automático de: Remolque
		 */
		addStep("Capturar Subtipo Remolque");
		campoActual="subtipoRemolque";
		capturarCasilla=unidadesDetalle.capturarCasilla(subtipoRemolque, campoActual,true, true);
		assertTrue(mensajeCaptura+campoActual
				
				, capturarCasilla);
		campoActual="obtenerRemolque";
		autollenado=unidadesDetalle.obtenerAutollenado(campoActual,false);
		assertFalse(mensajeAutollenado
				
				, autollenado.isEmpty());		
		/*
		 * PASO 13:
		 * Descripción del paso: Capturar: Sin captura en campo Capacidad
		 * Resultado Esperado: Continuar en casilla Aseguradora
		 */
		addStep("Se omite la captura del campo capacidad");
		campoActual="capacidad";
		capturarCasilla=unidadesDetalle.capturarCasilla("", campoActual,false, true);
		assertTrue(mensajeCaptura+campoActual, capturarCasilla);
		
		/*
		 * PASO 14:
		 * Descripción del paso: Capturar: Aseguradora
		 * Resultado Esperado: Continuar en casilla Permiso SCT
		 */
		addStep("Capturar Aseguradora");
		campoActual="aseguradora";
		capturarCasilla=unidadesDetalle.capturarCasilla(aseguradora, campoActual,false, true);
		assertTrue(mensajeCaptura+campoActual
				
				, capturarCasilla);
	
		
		/*
		 * PASO 15:
		 * Descripción del paso: Capturar: Permiso SCT
		 * Resultado Esperado: Continuar en casilla CR Origen
		 */
		addStep("Capturar Permiso SCT");
		campoActual="permisoSct";
		capturarCasilla=unidadesDetalle.capturarCasilla(permisoSct, campoActual,false, true);
		assertTrue(mensajeCaptura+campoActual
				
				, capturarCasilla);
		
		/*
		 * PASO 16:
		 * Descripción del paso: Capturar: CR Origen
		 * Resultado Esperado: Continuar en casilla Placa
		 */
		addStep("Capturar CR Origen");
		campoActual="crOrigen";
		capturarCasilla=unidadesDetalle.capturarCasilla(crOrigen, campoActual,false, true);
		assertTrue(mensajeCaptura+campoActual
				
				, capturarCasilla);
		

		/*
		 * PASO 17:
		 * Descripción del paso: Capturar: Placa
		 * Resultado Esperado: Continuar en casilla Vigencia
		 */
		addStep("Capturar Placa");
		campoActual="placa";
		capturarCasilla=unidadesDetalle.capturarCasilla(placa, campoActual,false, true);
		assertTrue(mensajeCaptura+campoActual
				
				, capturarCasilla);
		
		/*
		 * PASO 18:
		 * Descripción del paso: Seleccionar anio, mes y día de vigencia
		 * Resultado Esperado: Continuar en Placa Remolque
		 */
		addStep("Seleccionar fecha de vigencia");
		campoActual="botonVigencia";
		seleccionarCasilla=unidadesDetalle.seleccionarElemento(campoActual, false,null);
		assertTrue(mensajeSeleccion+campoActual
				
				, seleccionarCasilla);
		campoActual="botonAnio";
		seleccionarCasilla=unidadesDetalle.seleccionarElemento(campoActual, false, null);
		assertTrue(mensajeSeleccion+campoActual
				
				, seleccionarCasilla);
		campoActual="vigenciaAnio";
		seleccionarCasilla=unidadesDetalle.seleccionarElemento(campoActual, false, vigenciaAnio);
		assertTrue(mensajeSeleccion+campoActual
				
				, seleccionarCasilla);
		campoActual="vigenciaMes";
		seleccionarCasilla=unidadesDetalle.seleccionarElemento(campoActual, false, vigenciaMes);
		assertTrue(mensajeSeleccion+campoActual
				
				, seleccionarCasilla);
		campoActual="vigenciaDia";
		seleccionarCasilla=unidadesDetalle.seleccionarElemento(campoActual, true, vigenciaDia);
		assertTrue(mensajeSeleccion+campoActual
				
				, seleccionarCasilla);
		
		/*
		 * PASO 19:
		 * Descripción del paso: Capturar: Placa Remolque
		 * Resultado Esperado: Continuar en Número Póliza
		 */
		addStep("Capturar Placa Remolque");
		campoActual="placaRemolque";
		capturarCasilla=unidadesDetalle.capturarCasilla(placaRemolque, campoActual,false, true);
		assertTrue(mensajeCaptura+campoActual
				
				, capturarCasilla);
//NEW		
		/*
		 * PASO 20:
		 * Descripción del paso: Capturar: Peso Bruto Vehicular con valor 0
		 * Resultado Esperado: No permitir la captura
		 */
		addStep("Capturar: Peso Bruto Vehicular");
		campoActual="pesoBrutoVehicular";
		capturarCasilla=unidadesDetalle.capturarCasilla(pesoBrutoVehicular, campoActual,false, true);
		assertTrue(mensajeCaptura+campoActual, capturarCasilla);
		
		
	
		addStep("Capturar Numero de Poliza");
		campoActual="numeroPoliza";
		capturarCasilla=unidadesDetalle.capturarCasilla(numeroPoliza, campoActual,false, true);
		assertTrue(mensajeCaptura+campoActual
				
				, capturarCasilla);
		
		/*
		 * PASO 23:
		 * Descripción del paso: Capturar: Núm Permiso SCT
		 * Resultado Esperado: Encontrar botón: "Guardar"
		 */
		addStep("Capturar Numero de Permiso SCT");
		campoActual="numPermisoSCT";
		capturarCasilla=unidadesDetalle.capturarCasilla(numPermisoSCT, campoActual,false, true);
		assertTrue(mensajeCaptura+campoActual
				
				, capturarCasilla);
		
		/*
		 * PASO 24:
		 * Descripción del paso: Seleccionar botón "Guardar"
		 * Resultado Esperado: Obtener mensaje esperado
		 */
		unidadesDetalle.bajarScroll(250);
		addStep("Seleccionar boton Guardar");
		campoActual=unidadesDetalle.guardarUnidad();
		getTestCase().addTextEvidenceCurrentStep("Estatus de guardado: "+campoActual);
		assertEquals(campoActual.replace(" ", ""), "Equipoagregadoexitosamente!", mensajeSeleccion
				);
		
		

		//PASO 4: 
		addStep("Seleccionar Menu de Carta Porte");
		String encontrarOpcionMenuCP=portalCP.encontrarOpcionesCartaPorte(tipoCartaPorte);
		assertEquals(encontrarOpcionMenuCP, "Carta Porte CEDIS", "No se encontro la opcion: Carta Porte Cedis");
		
		//PASO 5:
		addStep("Seleccionar opcion \"Carta Porte Cedis\"");
		String seleccionarCPCedis=portalCP.seleccionarOpcionCartaPorte(tipoCartaPorte);
		assertEquals(seleccionarCPCedis, "Carta Porte Entrega", "No se encontro el texto: Carta Porte Cedis");
		
		//PASO 6:
		addStep("Capturar origen: "+origen);
		campoActual="origen";
		encontrarCasilla= portalCedis.capturarCasilla(origen, campoActual,true,true);
		assertTrue(mensajeCaptura+campoActual, encontrarCasilla);
		campoActual="fechaFacturacion";
		encontrarCasilla=portalCedis.encontrarElemento(campoActual, null);
		assertTrue(mensajeEncontrar+campoActual, encontrarCasilla);
		
		
		
		//PASO 7:
		addStep("Seleccionar fecha de facturacion: "+anio+"-"+mes+"-"+dia);
		campoActual="fechaFacturacionSeleccion";
		encontrarCasilla= portalCedis.seleccionarElemento(campoActual,false,"");
		assertTrue(mensajeSeleccion+campoActual, encontrarCasilla);
	    //SELECCIONAR AÑO
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
		//ENCONTRAR ESTATUS TRANSFERENCIA
		campoActual="estatusTransferencia";
		encontrarCasilla=portalCedis.encontrarElemento(campoActual, null);
		assertTrue(mensajeEncontrar+campoActual, encontrarCasilla);
		
		
		
	String estatusTransferencia[]=detalle.split(",");
		int contador=0;
		int registros=estatusTransferencia.length;
		boolean imprime=true;
		do {
			String check1=String.valueOf(estatusTransferencia[contador].charAt(0));
			String check2=String.valueOf(estatusTransferencia[contador].charAt(1));
			String descripcion=estatusTransferencia[contador].substring(2, estatusTransferencia[contador].length());
			//PASO 8 Seleccionar opciones de checkbox  1 (Shipped, Closed o Ambos) y 2 (Ruta, Ola, Transferencia)		
			if(imprime) {
				addStep("Seleccion de opciones estatus transferencia");
			}
			campoActual="opciones1";
			encontrarCasilla= portalCedis.seleccionarElemento(campoActual,false, check1);
			assertTrue(mensajeSeleccion+campoActual, encontrarCasilla);
			campoActual="opciones2";
			encontrarCasilla= portalCedis.seleccionarElemento(campoActual,imprime, check2);
			assertTrue(mensajeSeleccion+campoActual, encontrarCasilla);
			campoActual=portalCedis.obtenerOpcion(check2);
			encontrarCasilla=portalCedis.encontrarElemento(campoActual, null);
			assertTrue(mensajeEncontrar+campoActual, encontrarCasilla);
			
			//PASO 9: Capturar descripción en casilla de opción 2
			
				addStep("Capturar descripcion: "+descripcion);
			
			encontrarCasilla= portalCedis.capturarCasilla(descripcion, campoActual,false,true);
			assertTrue(mensajeCaptura+campoActual, encontrarCasilla);
			int cuentaOp=1;
			boolean continuar=false;
			do {
				campoActual="botonBuscar";
				encontrarCasilla=portalCedis.encontrarElemento(campoActual, null);
				assertTrue(mensajeEncontrar+campoActual, encontrarCasilla);
				
				//PASO 10: Seleccionar boton Buscar
				if(imprime) {
					addStep("Seleccionar boton Buscar");
				}
				encontrarCasilla= portalCedis.seleccionarElemento(campoActual,imprime, "");
				assertTrue(mensajeSeleccion+campoActual, encontrarCasilla);
				//encontrarCasilla=portalCedis.seleccionarElemento("botonAlerta", false, "");
				
					continuar=true;
					campoActual="tablaRegistros";
					encontrarCasilla=portalCedis.encontrarElemento(campoActual, null);
				    assertTrue(mensajeEncontrar+campoActual, encontrarCasilla);
				
					//PASO 11: 
					if(imprime) {
					addStep("Seleccionar tiendas");
					}
					campoActual="seleccionaTiendas";
					encontrarCasilla= portalCedis.seleccionarElemento(campoActual,imprime,"");
					assertTrue(mensajeSeleccion+campoActual, encontrarCasilla);
				
				
			cuentaOp++;
			}while(!continuar && cuentaOp<3);
			campoActual="seleccionDerecha";
			encontrarCasilla=portalCedis.encontrarElemento(campoActual, null);
			assertTrue(mensajeEncontrar+campoActual, encontrarCasilla);
					
			//PASO 12: 
			addStep("Seleccionar elemento visual para mover tiendas de la descripcion: "+descripcion);
			encontrarCasilla= portalCedis.seleccionarElemento(campoActual,true, "");
			assertTrue(mensajeSeleccion+campoActual, encontrarCasilla);	
			contador ++;
			imprime=false;
		}while(contador < registros);
						
		if(bol.compareToIgnoreCase("SI")==0) {
			campoActual="fechainicialBOL";
			encontrarCasilla=portalCedis.encontrarElemento(campoActual, null);
			assertTrue(mensajeEncontrar+campoActual, encontrarCasilla);
			//PASO 13:
			addStep("Seleccionar fecha inicial BOL: "+anioBOL+"-"+mesBOL+"-"+diaBOL);
			u.scroll(400);
			encontrarCasilla= portalCedis.seleccionarElemento(campoActual,true, "");
			assertTrue(mensajeSeleccion+campoActual, encontrarCasilla);
			//PASO 14 Seleccionar fecha inicial BOL (año, mes, día)
			addStep("Seleccionar fecha inicial BOL");
			campoActual="botonAnio";
			encontrarCasilla= portalCedis.seleccionarElemento(campoActual,false, anioBOL);
			assertTrue(mensajeSeleccion+campoActual, encontrarCasilla);
			campoActual="anio";
			encontrarCasilla= portalCedis.seleccionarElemento(campoActual,false, anioBOL);
			assertTrue(mensajeSeleccion+campoActual, encontrarCasilla);
			campoActual="mes";
			encontrarCasilla= portalCedis.seleccionarElemento(campoActual,false, mesBOL);
			assertTrue(mensajeSeleccion+campoActual, encontrarCasilla);
			campoActual="dia";
			encontrarCasilla= portalCedis.seleccionarElemento(campoActual,false, diaBOL);
			assertTrue(mensajeSeleccion+campoActual, encontrarCasilla);
			campoActual="botonDevolucion";
			encontrarCasilla= portalCedis.seleccionarElemento(campoActual,true, "");
			assertTrue(mensajeSeleccion+campoActual, encontrarCasilla);
			u.scroll(400);
			campoActual="tablaDevolucion";
			encontrarCasilla=portalCedis.encontrarElemento(campoActual,"");
			assertTrue(mensajeEncontrar+campoActual, encontrarCasilla);
				
			//PASO 16
			addStep("Seleccionar registros de devolucion");
			u.scroll(400);
			campoActual="seleccionarDevolcion";
			encontrarCasilla= portalCedis.seleccionarElemento(campoActual,false, "1");
			assertTrue(mensajeSeleccion+campoActual, encontrarCasilla);
			campoActual="flecha";
			encontrarCasilla= portalCedis.seleccionarElemento(campoActual,true, "");
			assertTrue(mensajeSeleccion+campoActual, encontrarCasilla);
				
			}
		campoActual="nombreChofer";
		encontrarCasilla=portalCedis.encontrarElemento(campoActual, null);
		assertTrue(mensajeEncontrar+campoActual, encontrarCasilla);
		
		addStep("Capturar nombre de transportista");
		encontrarCasilla= portalCedis.capturarCasilla(nombreChofer, campoActual,true,true);
		assertTrue(mensajeCaptura+campoActual
				
				, encontrarCasilla);
		campoActual="numeroEconomico";
		encontrarCasilla=portalCedis.encontrarElemento(campoActual, null);
		assertTrue(mensajeEncontrar+"texto "+campoActual
				
				, encontrarCasilla);
		//PASO 18
		addStep("Capturar numero economico de transporte");
		encontrarCasilla= portalCedis.capturarCasilla(identificador, campoActual,true,true);
		assertTrue(mensajeCaptura+campoActual
				, encontrarCasilla);
		/*
		 * PASO 42:
		 * Descripción del paso: Capturar número económico de transporte con el identificador recien 
		 * agregado en pantalla Unidades..
		 * Resultado Esperado: Autollenado de casillas: Placas, Seguros y Peso Bruto Vehicular convertido a toneladas
		*/
		//	addStep("Validar que el peso bruto vehicular se muestre en toneladas");
		portalCP.bajarScroll();
		addStep("Validar que el peso bruto vehicular se muestre en toneladas");
		portalCP.bajarScroll();
		pesoBrutoVehicular=portalCedis.convertirKiloEnTonelada(pesoBrutoVehicular);
		campoActual="PesoBruto";
		campoActual=portalCedis.obtenerPesoBrutoAutollenado(campoActual);
		assertEquals(campoActual, pesoBrutoVehicular ,"El peso bruto vehicular no coincide con la ultima modificacion");


		
		
	
		
		/*
		 * PASO 43:
		 * Descripción del paso: Seleccionar: "Generar Carta Porte"
		 * Resultado Esperado: Descarga de archivo .zip con formatos XML y PDF, se muestra pantalla de envio de correo.
		*/
		addStep("Se generan documentos de carta porte");
		campoActual="seleccionarCartaPorte";
		portalCP.bajarScroll();
		encontrarCasilla= portalCedis.seleccionarElemento(campoActual,false, mesBOL);
		assertTrue(mensajeSeleccion+campoActual
				
				, encontrarCasilla);
		encontrarCasilla=portalCedis.determinarEstatusTimbrado();
		
		assertTrue("Timbrado fallo "
		, encontrarCasilla);
		
		/*
		 * PASO 44:
		 * Descripción del paso: Captura de correo para enviar documentos
		 * Resultado Esperado: Se envia documento .zip con formatos XML y PDF, se muestra visualización de la tira de impresión
		*/
		addStep("Captura de correo para enviar documentos");
		campoActual="capturaCorreo";
		encontrarCasilla= portalCedis.capturarCasilla(correo, campoActual,false,false);
		assertTrue(mensajeCaptura+campoActual
				
				, encontrarCasilla);
		campoActual="botonEnviar";
		encontrarCasilla= portalCedis.seleccionarElemento(campoActual,false,"");
		assertTrue(mensajeSeleccion+campoActual
				
				, encontrarCasilla);
		getTestCase().addTextEvidenceCurrentStep("Se muestra tira de impresion");
		u.hardWait(60);
		p.clickImagen("printCP");
		String numeroAleatorio=String.valueOf(portalCP.generarNumeroAleatorio(500, 600));
		testCase.addScreenShotCurrentStep(u, "TiraImpresion"+numeroAleatorio);
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
		u.hardWait(30);
		sk.screen.type(Keys.TAB);
		u.hardWait(30);
		p.clickImagen("saveCP");
		u.hardWait(60);
		getTestCase().addTextEvidenceCurrentStep("Las validaciones sobre el contenido de cada documento se realizaran de forma manual");
		u.close(); 	
	}

	public String setTestDescription() {
		return "Generar una Unidad desde el Portal de Carta Porte/ Administracion / Unidades.";
	}
	@Override
	public String setTestDesigner() {
		return "nellyoliviab@hexaware.com";
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_001_Agregar_Unidad_CEDIS_CartaPorte3_test";
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
		return "1.- Usuario con acceso a Portal Carta Porte: https://fcportalqa.femcom.net:8443/AccessControl/pages/login_form.jsf\r\n"
				+ ""+"\n"
				+ "2.- Informacion para crear Unidad"+"\n"
				+ "3.- Origen de Cedis o PT"+"\n"
				+ "4.- Fecha de facturacion "+"\n"
				+ "5.- Estatus de transferencia (Shipped, closed o ambos) y (Ruta, Ola o Transterencia)"+"\n"
				+ "6.- Fecha inicial BOL"+"\n"
				+ "7.- Datos del transportista validos para el Cedis, plaza o terceros";
	}
	@AfterMethod
	public void afterMethod(ITestResult result) {
		MetodoGeneral.obtenerTiempoDeEjecucion(result, testCase);
	}
}
