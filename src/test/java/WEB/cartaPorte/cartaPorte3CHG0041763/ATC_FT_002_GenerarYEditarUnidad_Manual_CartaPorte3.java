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
import org.testng.annotations.Test;

import WEB.cartaPorte.objectModelPortalCP.AdministracionUnidades;
import WEB.cartaPorte.objectModelPortalCP.CartaPorteManual;
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

public class ATC_FT_002_GenerarYEditarUnidad_Manual_CartaPorte3 extends BaseExecution {
	/**
	 * PORTAL CARTA PORTE/ CARTA PORTE / CARTA PORTE MANUAL
	 * 
	 * Cambio a validar: Se agregan campos de capacidad y peso bruto a la pantalla de Unidades
	 * 
	 * DESCRIPCION: 
	 * El objetivo del script es generar una Unidad desde el Portal de Carta Porte/ Administracion / Unidades.
	 * Al crear la unidad, se realizará una búsqueda del identificador capturado y al encontrarlo, se editara la información de los campos nuevos
	 * (capacidad y peso bruto vehicular).
	 * Se genera la Carta Porte Manual , el número económico es el identificador capturado en la Unidad que se agrego y modificó.
	 * El peso bruto mostrado se encuentra en toneladas, en la pantalla de unidades se captura por kilos.
	 * Al generar los documentos de Carta Porte OC, se debe imprimir el peso bruto en toneladas con los datos de la Unidad que modificó.
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
	 *          Validar que los siguientes campos carguen informacion: 
	 *          -Capturar Origen y destino
	 *          -Para estar pruebas se utilizaron artículos especiales, validar la disponibilidad de otros artículos en caso de ser necesario
	 *          -Nombre chofer y numero economico, asegurarse que estos datos pertenezcan al origen capturado
	 * @author nellyoliviab@hexaware.com
	 */
	@Test(dataProvider = "data-provider")
	public void ATC_FT_002_GenerarYEditarUnidad_Manual_CartaPorte3_test(HashMap<String, String> data) throws FindFailed, SQLException {
		//Inicio de sesion
		String portalurl = data.get("server");
		String userName=data.get("user");
		String password = PasswordUtil.decryptPassword(data.get("password"));
		// PAC: Monitoreo/Configuracion, es el PAC para generar el timbrado(Check box seleccionado: Konesh o Diverza)
		String pac = data.get("pac");
		/* Campo: Descipcion del caso de prueba, se agrega en el DataProvider por si se requiere ejecutar el 
		mismo script con diferentes combinaciones de datos */
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
		String rutaGuardarTira = data.get("rutaGuardarTira");
		// Datos de Carta Porte MANUAL
		String tipoCartaPorte = data.get("tipoCartaPorte");
		String check=data.get("check");
		String destino=data.get("destino");
		String tipoArticulo=data.get("tipoArticulo");
		String registro= data.get("registro");
		String nombreChofer=data.get("nombreChofer"); 
		String correo=data.get("correo"); 
		// Informacion que se imprime en el reporte 
		testCase.setTest_Description("");
		testCase.setTest_Description(campo+" ."+"\n"+"PAC de prueba: "+pac);
		SeleniumUtil u=new SeleniumUtil(new ChromeTest(), true);
		LoginCP login = new LoginCP(u, testCase, portalurl);
	/*	utils.sql.SQLUtil dbFCPFACQA = new utils.sql.SQLUtil(GlobalVariables.DB_HOST_FCPFACQA,
				 GlobalVariables.DB_USER_FCPFACQA, GlobalVariables.DB_PAST_FCPFACQA);
		*/
		utils.sql.SQLUtil dbFCPFACQA = new utils.sql.SQLUtil(GlobalVariables.DB_HOST_FCPFACQA,
				 GlobalVariables.DB_USER_FCPFACQA_MV, GlobalVariables.DB_PAST_FCPFACQA_MV);
		MenuPortal portalmenu= new MenuPortal(u, testCase);
		PortalCartaPorte portalCP= new PortalCartaPorte(u, testCase);
		String selectIDORIGEN="SELECT DESCRIPCION FROM XXCAP.XXCAP_CEDIS_CAT"
				+ " WHERE IDORIGEN= '"+origenUnidad+"'";
		AdministracionUnidades admonUnidades=new AdministracionUnidades(u, testCase);
		UnidadesDetalleEquipo unidadesDetalle=new UnidadesDetalleEquipo(u,testCase);
		CartaPorteManual manual= new CartaPorteManual(u, testCase,portalCP);
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
		MetodoGeneral metodoGeneral= new MetodoGeneral();
		Date fechaInicial= new Date();
		//Preparar datos
		check=check.toUpperCase().charAt(0) + check.substring(1,check.length()).toLowerCase();
		tipoArticulo=tipoArticulo.toUpperCase().charAt(0) + tipoArticulo.substring(1,tipoArticulo.length()).toLowerCase();
		Integer registroTotal=Integer.parseInt(registro); 
		
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
		assertTrue( "El sitio no fue cargado satisfactoriamente"
				
				,cargarPagina);	
		/*
		 * PASO 2:
		 * Descripción del paso: Seleccionar "Portal Carta Porte"
		 * 
		 * Resultado Esperado: Encontrar menú "Administracion"
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
		addStep("Seleccionar \"Portal Carta Porte\"");
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
		String encontrarOpcionMenuAdmon=portalCP.encontrarOpcionesAdministracione(opcionAdmon, true);
		assertEquals(encontrarOpcionMenuAdmon
				, "Unidades", "No se encontro la opcion: Unidades"
				);
		/*
		 * PASO 5:
		 * Descripción del paso: Seleccionar opción "Unidades"
		 * 
		 * Resultado Esperado: Encontrar texto: "Listado de Equipo"
		 */ 
		addStep("Seleccionar opcion \"Unidades\"");
		String seleccionarElemento=portalCP.seleccionarOpcionAdministracion(opcionAdmon);
		assertEquals(seleccionarElemento
				, "Listado de Equipos", "No se encontro el texto: Listado de Equipos"
				);
		/*
		 * PASO 6:
		 * Descripción del paso: Seleccionar botón "Nuevo equipo" 
		 * 
		 * Resultado Esperado: Encontrar texto: "Detalle del Equipo"
		 */ 
		addStep("Seleccionar boton Nuevo Equipo");
		String campoActual="botonNuevoEquipo";
		boolean seleccionarCasilla=admonUnidades.seleccionarElemento(campoActual,true, null);
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
		utils.sql.SQLResult resultadoOrigen= dbFCPFACQA.executeQuery(selectIDORIGEN);
		testCase.addQueryEvidenceCurrentStep(resultadoOrigen);
		campoActual="obtenerUnidadOperativa";
		String autollenado=unidadesDetalle.obtenerAutollenado(campoActual,false);
		String origen=resultadoOrigen.getFirstData();
		assertEquals(resultadoOrigen.getFirstData().toLowerCase(), autollenado.toLowerCase(), mensajeAutollenado
				);
		/*
		 * PASO 9:
		 * Descripción del paso: Capturar: Conf. Vehicular
		 * Resultado Esperado: Obtener llenado automático de: Descripción
		 */
		addStep("Capturar Conf. Vehicular");
		campoActual="confVehicular";
		capturarCasilla=unidadesDetalle.capturarCasilla(confVehicular, campoActual,true, true);
		assertTrue(mensajeCaptura+campoActual
				
				, capturarCasilla);
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
//NEW		
		/*
		 * PASO 13:
		 * Descripción del paso: Capturar: Sin captura en campo Capacidad
		 * Resultado Esperado: Continuar en casilla Aseguradora
		 */
		addStep("Capturar Capacidad");
		 campoActual="capacidad";
		 capturarCasilla=unidadesDetalle.capturarCasilla("5T", campoActual,false, true);
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
			addStep("Omitir captura de Peso Bruto Vehicular");
			campoActual="pesoBrutoVehicular";
			capturarCasilla=unidadesDetalle.capturarCasilla("", campoActual,false, true);
			assertTrue(mensajeCaptura+campoActual, capturarCasilla);
		
		/*
		 * PASO 22:
		 * Descripción del paso: Capturar: Número Póliza
		 * Resultado Esperado: Continuar en Núm Permiso SCT
		*/
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
	
		
		  addStep("Se captura Peso Bruto Vehicular valido");
			campoActual="pesoBrutoVehicular";
			capturarCasilla=unidadesDetalle.capturarCasilla("19500", campoActual,false, true);
			assertTrue(mensajeCaptura+campoActual, capturarCasilla);
		
		addStep("Seleccionar  boton Guardar");
		campoActual=unidadesDetalle.guardarUnidad();
		assertEquals( campoActual.replace(" ", ""), "Equipoagregadoexitosamente!",
				"No ha sido posible continuar con la informacion capturada"
						);
		
		/*
		 * PASO 25:
		 * Descripción del paso: Capturar: Peso Bruto Vehicular validod
		 * Resultado Esperado: Encontrar botón: "Guardar"
		*/ 
		addStep("Modificar: Peso Bruto Vehicular");
		campoActual="capturaOrigen";
		capturarCasilla=admonUnidades.capturarCasilla(origen, campoActual,true, false);
		assertTrue(mensajeCaptura+campoActual
				
				, capturarCasilla);
		admonUnidades.buscarYQuitarAlerta();	
		campoActual="seleccionaBuscar";
		seleccionarCasilla=admonUnidades.seleccionarElemento(campoActual, false,origen);
		assertTrue(mensajeSeleccion+campoActual
				
				, seleccionarCasilla);
		admonUnidades.bajarScroll("500");
		admonUnidades.esperarActualizaTabla(origenUnidad);
		seleccionarCasilla=admonUnidades.buscaySeleccionaUnidad(identificador,true);
		assertTrue(mensajeSeleccion+"Registro"
				
				, seleccionarCasilla);
//NEW	
		
		addStep("Obtener valor de capacidad actual");
		 campoActual="capacidad";
		 campoActual=unidadesDetalle.obtenerTexto(campoActual, true);
		 assertEquals( campoActual, "5T",
					"El texto encontrado no coincide con el esperado"
							);
		 
		 addStep("Obtener valor de Peso Bruto Vehicular");
		 campoActual="pesoBrutoVehicular";
		 campoActual=unidadesDetalle.obtenerTexto(campoActual, true);
		 assertEquals( campoActual, "19500",
					"El texto encontrado no coincide con el esperado"
							);
		
		
		addStep("Se modifica el valor de Capacidad");
		 campoActual="capacidad";
		 capturarCasilla=unidadesDetalle.capturarCasilla(capacidad, campoActual,false, true);
		assertTrue(mensajeCaptura+campoActual, capturarCasilla);
		    
		    addStep("Se modifica el valor de Peso Bruto Vehicular");
			campoActual="pesoBrutoVehicular";
			capturarCasilla=unidadesDetalle.capturarCasilla(pesoBrutoVehicular, campoActual,false, true);
			assertTrue(mensajeCaptura+campoActual, capturarCasilla);
		
		
		/*
		 * PASO 26:
		 * Descripción del paso: Seleccionar botón "Guardar"
		 * Resultado Esperado: No se obtiene mensaje adicional
		*/ 		
		addStep("Seleccionar boton Guardar");
		campoActual=unidadesDetalle.guardarUnidad();
		assertEquals(campoActual.replace(" ", ""), "¡Equipoactualizadoexitosamente!");
		admonUnidades.buscarYQuitarAlerta();
		/*
		 * PASO 27:
		 * Descripción del paso: Seleccionar menú "Carta Porte"
		 * Resultado Esperado: Encontrar opción "Carta Porte manual"
		*/ 	
		u.hardWait(5);
		addStep("Seleccionar Menu de Carta Porte");
		String encontrarOpcionMenuCP=portalCP.encontrarOpcionesCartaPorte(tipoCartaPorte);
		assertEquals(encontrarOpcionMenuCP, "Carta Porte Manual", "No se encontro la opcion: Carta Porte Manual"
				);
			
		/*
		 * PASO 28:
		 * Descripción del paso: Seleccionar opción "Carta Porte manual"
		 * Resultado Esperado: Encontrar texto "Carta Porte Manual"
		*/ 	
		addStep("Seleccionar opcion: Carta Porte manual");
		String seleccionarCPManual=portalCP.seleccionarOpcionCartaPorte(tipoCartaPorte);
		assertEquals(seleccionarCPManual, "Carta Porte Manual", "No se encontro el texto: Carta Porte Manual"
				);
		
		/*
		 * PASO 29:
		 * Descripción del paso: Capturar campo: Origen
		 * Resultado Esperado: Encontrar casilla: "Destino"
		*/ 	
		addStep("Capturar campo: Origen");
		campoActual="origen";
		encontrarCasilla= manual.capturarCasilla(origen, campoActual,true,true);
		assertTrue(mensajeCaptura+campoActual
				
				, encontrarCasilla);
		campoActual="check"+check;
		encontrarCasilla=manual.encontrarElemento(campoActual);
		assertTrue(mensajeEncontrar+campoActual
				
				, encontrarCasilla);
		
		/*
		 * PASO 30:
		 * Descripción del paso: Seleccionar checkbox (CEDIS, plaza, Terceros)
		 * Resultado Esperado: Encontrar texto: "Mercancias"
		*/ 	
		addStep("Seleccionar checkbox");
		encontrarCasilla= manual.seleccionarElemento(campoActual,true);
		assertTrue(mensajeSeleccion+campoActual
				
				, encontrarCasilla);
		campoActual="destino";
		encontrarCasilla=manual.encontrarElemento(campoActual);
		manual.encontrarElemento(campoActual);
		assertTrue(mensajeEncontrar+campoActual
				
				, encontrarCasilla);
		/*
		 * PASO 31:
		 * Descripción del paso: Capturar campo: Destino
		 * Resultado Esperado: Habilitar opciones de checkbox
		*/ 	
		addStep("Capturar campo: Destino");
		encontrarCasilla= manual.capturarCasilla(destino, campoActual,true,true);
		assertTrue(mensajeCaptura+campoActual
				
				, encontrarCasilla);
		campoActual="Mercancias";
		encontrarCasilla=manual.encontrarElemento(campoActual);
		assertTrue(mensajeEncontrar+"texto "+campoActual
				
				, encontrarCasilla);
		
		
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
			/*
			 * PASO 32:
			 * Descripción del paso: Seleccionar elemento visual de Buscar en columna de SKU
			 * Resultado Esperado: Mostrar venta "Buscar Articulos"
			*/ 	
			if(captura) {
				addStep("Seleccionar elemento visual para la busqueda por SKU");
			}
			assertTrue(mensajeSeleccion+campoActual
					
					, encontrarCasilla);	
			encontrarCasilla=manual.rellenaArticulos(filas, tipoArticulo,captura);
			assertTrue(mensajeSeleccion+campoActual
					
					, encontrarCasilla);	
			campoActual="buscarArticulos";
			encontrarCasilla=manual.encontrarElemento(campoActual);
			assertTrue(mensajeEncontrar+campoActual
					
					, encontrarCasilla);
			/*
			 * PASO 33:
			 * Descripción del paso: Seleccionar Tipo de Articulo (Normal o Especial)
			 * Resultado Esperado: Habilitar casilla: Filtrar
			*/ 	
			if(captura) {
				addStep("Seleccionar Tipo de Articulo");
			}
			campoActual="tipoArticulo";
			encontrarCasilla= manual.seleccionarElemento(campoActual+tipoArticulo,captura);
			assertTrue(mensajeSeleccion+campoActual
					
					, encontrarCasilla);
			campoActual="filtrar";
			encontrarCasilla=manual.encontrarElemento(campoActual);
			assertTrue(mensajeEncontrar+campoActual
					
					, encontrarCasilla);
			/*
			 * PASO 34:
			 * Descripción del paso: Capturar información de casilla: Filtrar
			 * Resultado Esperado: Seleccionar elemento visual "Buscar"
			*/ 	
			if(captura) {
				addStep("Capturar informacion de casilla: Filtrar");
			}
			int tipoArticuloOp=manual.determinaTipoArticulo(tipoArticulo);
			valorFiltro=manual.obtenerValorArticulos(tipoArticuloOp);
			encontrarCasilla= manual.capturarCasilla(valorFiltro, campoActual,false,captura);
			assertTrue(mensajeCaptura+campoActual
					
					, encontrarCasilla);
			campoActual="botonBuscarArticulo";
			encontrarCasilla= manual.seleccionarElemento(campoActual,false);
			assertTrue(mensajeSeleccion+campoActual
					
					, encontrarCasilla);
			
			/*
			 * PASO 35:
			 * Descripción del paso: Seleccionar artículo
			 * Resultado Esperado: Repetir búsqueda de archivo o seleccionar Buscar
			 * 
			 * PASO36: Se cierra ventana
			*/ 
			if(captura) {
				addStep("Seleccionar un articulo");
			}
			encontrarCasilla=manual.seleccionaArticulos(captura);
			assertTrue("Error al seleccionar articulo"
					
					, encontrarCasilla);
			encontrarCasilla=manual.capturaDetalleArticulo(filas);
			assertTrue("Error capturar detalles de artículo"
					
					, encontrarCasilla);
			
			if(contador==registroTotal) {
				getTestCase().addTextEvidenceCurrentStep("Total de mercancia seleccionada");
				testCase.addScreenShotCurrentStep("MercanciaTotal");
			}
			captura=false;
			contador++;
			filas++;
			
		}while(encontrarCasilla && contador<=registroTotal);
		
		/*
		 * PASO 37:
		 * Descripción del paso: Capturar nombre de transportista
		 * Resultado Esperado: Encontrar casilla de número económico de transporte
		*/ 	
		addStep("Capturar nombre de transportista");
		portalCP.bajarScroll();
		campoActual="nombreChofer";
		encontrarCasilla= manual.capturarCasilla(nombreChofer, campoActual,true,true);
		assertTrue(mensajeCaptura+campoActual
				
				, encontrarCasilla);
		campoActual="numeroEconomico";
		encontrarCasilla=manual.encontrarElemento(campoActual);
		assertTrue(mensajeEncontrar+"texto "+campoActual
				
				, encontrarCasilla);
		
		//PASO 38
		addStep("Capturar numero economico de transporte que corresponde al identificardor recien agregado y modificado");
		encontrarCasilla= manual.capturarCasilla(identificador, campoActual,true,true);
		assertTrue(mensajeCaptura+campoActual
				, encontrarCasilla);
		encontrarCasilla=manual.verificarAutollenado();
		assertTrue("Los campos del transportista no fueron autollenados totalmente"
				, encontrarCasilla);
		
		portalCP.bajarScroll();
		addStep("Validar que el peso bruto vehicular se muestre en toneladas");
		portalCP.bajarScroll();
		pesoBrutoVehicular=manual.convertirKiloEnTonelada(pesoBrutoVehicular);
		campoActual="PesoBruto";
		campoActual=manual.obtenerPesoBrutoAutollenado(campoActual);
		assertEquals(campoActual, pesoBrutoVehicular ,"El peso bruto vehicular no coincide con la ultima modificacion");


		
		
		campoActual="generarCartaPorte";
		getTestCase().addTextEvidenceCurrentStep("Se generan documentos para carta porte");
		encontrarCasilla= manual.seleccionarElemento(campoActual,false);
		assertTrue(mensajeSeleccion+campoActual
				, encontrarCasilla);
				
		encontrarCasilla=manual.determinarEstatusTimbrado();
		assertTrue("Timbrado fallo"
				, encontrarCasilla);

		//PASO 20
		addStep("Captura de correo para enviar documentos");
		campoActual="capturaCorreo";
		encontrarCasilla= manual.capturarCasilla(correo, campoActual,false,false);
		assertTrue(mensajeCaptura+campoActual
				, encontrarCasilla);
		campoActual="botonEnviar";
		encontrarCasilla= manual.seleccionarElemento(campoActual,false);
		assertTrue(mensajeSeleccion+campoActual
				, encontrarCasilla);
		getTestCase().addTextEvidenceCurrentStep("Se muestra tira de impresion");
		u.hardWait(10);
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
		String nombreTira="CartaPorteTiraDocManual_"+String.valueOf(portalCP.generarNumeroAleatorio(300, 201))+".pdf";
		getTestCase().addTextEvidenceCurrentStep("Se guarda documento: "+nombreTira);
		sk.screen.type(nombreTira);
		u.hardWait(10);
		sk.screen.type(Keys.TAB);
		u.hardWait(10);
		p.clickImagen("saveCP");
		u.hardWait(10);
		getTestCase().addTextEvidenceCurrentStep("Las validaciones sobre el contenido de cada documento se realizaran de forma manual"
				);
		u.close(); 		
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
				+ "4.- Destino de Cedis o PT "+"\n"
				+ "5.- Tipos de articulos disponibles (Normal o especial)"+"\n"
				+ "6.- Datos del transportista"+"\n";
	}

	@Override
	public String setTestDescription() {
		return "Generar una Unidad desde el Portal de Carta Porte/ Administracion / Unidades.\r\n"
				+ " Al crear la unidad, se realizará una búsqueda del identificador capturado y al encontrarlo, se editara la información de los campos nuevos\r\n"
				+ " (capacidad y peso bruto vehicular).";
	}

	@Override
	public String setTestDesigner() {
		return "nellyoliviab@hexaware.com";
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_002_GenerarYEditarUnidad_Manual_CartaPorte3_test";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}

}
