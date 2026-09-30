package WEB.cartaPorte.cartaPorte3CHG0041763;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotEquals;

import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;

import org.sikuli.script.FindFailed;
import org.testng.annotations.Test;

import WEB.cartaPorte.objectModelPortalCP.AdministracionUnidades;
import WEB.cartaPorte.objectModelPortalCP.LoginCP;
import WEB.cartaPorte.objectModelPortalCP.MenuPortal;
import WEB.cartaPorte.objectModelPortalCP.PortalCartaPorte;
import WEB.cartaPorte.objectModelPortalCP.UnidadesDetalleEquipo;
import WEB.estandarizacionHomologacionImpuestos.pageObjectModelHomo.MetodoGeneral;
import WEB.util.GlobalVariables;
import modelo.BaseExecution;
import utils.password.PasswordUtil;
import utils.selenium.ChromeTest;
import utils.selenium.SeleniumUtil;
import utils.sql.SQLUtil;

public class ATC_FT_004_ValidarCaptura_PesoBrutoYCapacidad_Unidad_CartaPorte3 extends BaseExecution {
	/**
	 * PORTAL CARTA PORTE/ ADMINISTRACION / UNIDADES
	 * 
	 * Cambio a validar: Caracteres validos en los campos de: Capacidad y Peso Bruto Vehicular, asi como el valor minimo o maximo establecido
	 * 
	 * DESCRIPCION: 
	 * El objetivo del script es agregar una Unidad desde el Portal de Carta Porte/ Administracion / Unidades.
	 * Se agregara una nueva Unidad capturando caracteres alfabeticos y especiales en los campos de capacidad y peso bruto vehicular.
	 * Los campos deben validar el tipo de informaicón que se captura. Asi como los valores minimos y maximos
	 * 
	 * REREQUISITOS: 
	 *		1. Levantar ticket para solicitar scceso a la base de datos : 
	 *			Connection Name: FCPFACQA
	 *			Host: 10.80.3.57
	 *			Port: 1522
	 *			SERVICE_NAME=FCPFACQA.FEMCOM.NET
	 *      2. Actualizar los datos de Dataprovider:
	 *      	Usuario y contraseña: 6006023--> contactar a myriamguadalupem@hexaware.com para confirmar la contraseña
	 *      	Identificador-----(Identificador - número económico de la unidad)
	 *      	Serie-------------Número de cerie de la unidad
	 *          Placa-------------Placa del vehiculo
	 *          Valor valido al peso bruto vehicular
	 */
	@Test(dataProvider = "data-provider")
	public void ATC_FT_004_ValidarCaptura_PesoBrutoYCapacidad_Unidad_CartaPorte3_test(HashMap<String, String> data) throws FindFailed, SQLException {
		//Inicio de sesion
		String portalurl = data.get("server");
		String userName=data.get("user");
		String password = PasswordUtil.decryptPassword(data.get("password"));
		// PAC: Monitoreo/Configuracion, es el PAC para generar el timbrado(Check box seleccionado: Konesh o Diverza)
		String pac = data.get("pac");
		// Campo: Descipcion del caso de prueba, se agrega en el DataProvider por si se requiere ejecutar el mismo script 
		// con diferentes combinaciones de datos
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
		String aseguradora=data.get("aseguradora");
		String permisoSct=data.get("permisoSCT");
		String crOrigen=data.get("crOrigen");
		String placa=data.get("placa");
		String vigenciaDia=data.get("vigenciaDia");
		String vigenciaMes=data.get("vigenciaMes");
		String vigenciaAnio=data.get("vigenciaAnio");
		String placaRemolque=data.get("placaRemolque");
		String numeroPoliza=data.get("numeroPoliza");
		String numPermisoSCT=data.get("numPermisoSCT");
		String valorValido=data.get("valorValido");
		String valorNoValido=data.get("valorNoValido");
		String capacidad=data.get("capacidad");
		testCase.setTest_Description("");
		testCase.setTest_Description(campo);
		//testCase.setTest_Description(campo+" . PAC de prueba: "+pac);
		SeleniumUtil u=new SeleniumUtil(new ChromeTest(), true);
		LoginCP login = new LoginCP(u, testCase, portalurl);
		SQLUtil dbFCPFACQA = new utils.sql.SQLUtil(GlobalVariables.DB_HOST_FCPFACQA,
				 GlobalVariables.DB_USER_FCPFACQA, GlobalVariables.DB_PAST_FCPFACQA);
		String selectIDORIGEN="SELECT DESCRIPCION FROM XXCAP.XXCAP_CEDIS_CAT"
				+ " WHERE IDORIGEN= '"+origenUnidad+"'";
		MenuPortal portalmenu= new MenuPortal(u, testCase);
		PortalCartaPorte portalCP= new PortalCartaPorte(u, testCase);
		AdministracionUnidades admonUnidades=new AdministracionUnidades(u, testCase);
		UnidadesDetalleEquipo unidadesDetalle=new UnidadesDetalleEquipo(u,testCase);
		String mensajeSeleccion= "No fue posible seleccionar elemento: ";
		String mensajeEncontrar= "No fue posible encontrar elemento: ";
		String mensajeCaptura= "No fue posible capturar datos ";
		String mensajeAutollenado="La información obtenida no coincide con la esperada";
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
		assertTrue( "El sitio no fue cargado satisfactoriamente"
				
				,cargarPagina);				
		/*
		 * PASO 2:
		 * Descripción del paso: Capturar credenciales de acceso para iniciar sesión
		 * 
		 * Resultado Esperado: Encontrar mensaje: "Portal de Aplicaciones"
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
		assertEquals(encontrarOpcionMenuAdmon, "Unidades", "No se encontro la opcion: Unidades"
				);
		/*
		 * PASO 5:
		 * Descripción del paso: Seleccionar opción "Unidades"
		 * 
		 * Resultado Esperado: Encontrar texto: "Listado de Equipo"
		 */
		addStep("Seleccionar opcion \"Unidades\"");
		String seleccionarElemento=portalCP.seleccionarOpcionAdministracion(opcionAdmon);
		assertEquals(seleccionarElemento, "Listado de Equipos", "No se encontro el texto: Listado de Equipos"
				);
		/*
		 * PASO 6:
		 * Descripción del paso: Seleccionar botón "Nuevo equipo" 
		 * 
		 * Resultado Esperado: Encontrar texto: "Detalle del Equipo"
		 */
		addStep("Seleccionar opcion boton Nuevo Equipo");
		String campoActual="botonNuevoEquipo";
		boolean seleccionarCasilla=admonUnidades.seleccionarElemento(campoActual,true, null);
		assertTrue(mensajeSeleccion+campoActual
				
				, seleccionarCasilla);
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
		 * 
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
		 * 
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
		 * 
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
		 * 
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
		 * Descripción del paso: Capturar: Aseguradora
		 * 
		 * Resultado Esperado: Continuar en casilla Permiso SCT
		 */
		addStep("Capturar Aseguradora");
		campoActual="aseguradora";
		capturarCasilla=unidadesDetalle.capturarCasilla(aseguradora, campoActual,false, true);
		assertTrue(mensajeCaptura+campoActual
				
				, capturarCasilla);
		/*
		 * PASO 14:
		 * Descripción del paso: Capturar: Permiso SCT
		 * 
		 * Resultado Esperado:Continuar en casilla CR Origen
		 */
		addStep("Capturar Permiso SCT");
		campoActual="permisoSct";
		capturarCasilla=unidadesDetalle.capturarCasilla(permisoSct, campoActual,false, true);
		assertTrue(mensajeCaptura+campoActual
				
				, capturarCasilla);
		/*
		 * PASO 15:
		 * Descripción del paso: Capturar: CR Origen
		 * 
		 * Resultado Esperado: Continuar en casilla Placa
		 */
		addStep("Capturar CR Origen");
		campoActual="crOrigen";
		capturarCasilla=unidadesDetalle.capturarCasilla(crOrigen, campoActual,false, true);
		assertTrue(mensajeCaptura+campoActual
				
				, capturarCasilla);
		/*
		 * PASO 16:
		 * Descripción del paso: Capturar: Placa
		 * 
		 * Resultado Esperado: Continuar en casilla Vigencia
		 */
		addStep("Capturar Placa");
		campoActual="placa";
		capturarCasilla=unidadesDetalle.capturarCasilla(placa, campoActual,false, true);
		assertTrue(mensajeCaptura+campoActual
				
				, capturarCasilla);
		/*
		 * PASO 17:
		 * Descripción del paso: Seleccionar anio, mes y día de vigencia
		 * 
		 * Resultado Esperado: Continuar en Placa Remolque
		 */
		addStep("Seleccionar fecha de vigencia");
		campoActual="botonVigencia";
		seleccionarCasilla=unidadesDetalle.seleccionarElemento(campoActual, true,null);
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
		seleccionarCasilla=unidadesDetalle.seleccionarElemento(campoActual, false, vigenciaDia);
		assertTrue(mensajeSeleccion+campoActual
				
				, seleccionarCasilla);
		/*
		 * PASO 18:
		 * Descripción del paso: Capturar: Placa Remolque
		 * 
		 * Resultado Esperado: Continuar en Número Póliza
		 */
		addStep("Capturar Placa Remolque");
		campoActual="placaRemolque";
		capturarCasilla=unidadesDetalle.capturarCasilla(placaRemolque, campoActual,false, true);
		assertTrue(mensajeCaptura+campoActual
				
				, capturarCasilla);
		/*
		 * PASO 19:
		 * Descripción del paso: Capturar: Número Póliza
		 * 
		 * Resultado Esperado: Continuar en Número de permiso SCT
		 */
		addStep("Capturar Numero de Poliza");
		campoActual="numeroPoliza";
		capturarCasilla=unidadesDetalle.capturarCasilla(numeroPoliza, campoActual,false, true);
		assertTrue(mensajeCaptura+campoActual
				
				, capturarCasilla);
		/*
		 * PASO 20:
		 * Descripción del paso: Capturar: Núm Permiso SCT
		 * 
		 * Resultado Esperado: Continuar en Peso Bruto Vehicular
		 */
		
		addStep("Capturar Numero de Permiso SCT");
		campoActual="numPermisoSCT";
		capturarCasilla=unidadesDetalle.capturarCasilla(numPermisoSCT, campoActual,false, true);
		assertTrue(mensajeCaptura+campoActual
				
				, capturarCasilla);
		
		
		/*
		 * PASO 21:
		 * Descripción del paso: Validar que no se permita la captura de caracteres especiales en Capacidad
		 * 
		 * Resultado Esperado: No permitir la captura
		 */
		
		addStep("Validar que no se permita la captura de caracteres especiales en Capacidad");
		campoActual="capacidad";
		capturarCasilla=unidadesDetalle.capturarCasilla("@!@/?-", campoActual,false, true);
		assertTrue(mensajeCaptura+campoActual, capturarCasilla);
		autollenado=unidadesDetalle.obtenerTexto(campoActual,false);
		assertEquals(autollenado, "", "Se permitio la captura de caracteres especiales"
				);
			
		
		/*
		 * PASO 22:
		 * Descripción del paso: Capturar solo contenido alfabetico en Capacidad
		 * 
		 * Resultado Esperado: No permitir la captura
		 */
		addStep("Capturar solo contenido alfabetico en Capacidad");
		campoActual="capacidad";
		capturarCasilla=unidadesDetalle.capturarCasilla("CAPACIDAD", campoActual,false, false);
		assertTrue(mensajeCaptura+campoActual, capturarCasilla);
		autollenado=unidadesDetalle.obtenerTexto(campoActual, true);
		assertNotEquals(autollenado, "", "No se capturaron los valores"
				);	

		
		/*
		 * PASO 23:
		 * Descripción del paso: Validar que no se permita la captura de caracteres alfabeticos en Capacidad
		 * 
		 * Resultado Esperado: No permitir la captura
		 */
		
		addStep("Validar que no se permita la captura de caracteres alfabeticos en Peso Bruto Vehicular");
		campoActual="pesoBrutoVehicular";
		capturarCasilla=unidadesDetalle.capturarCasilla("TEXTOO", campoActual,false, true);
		assertTrue(mensajeCaptura+campoActual, capturarCasilla);
		autollenado=unidadesDetalle.obtenerTexto(campoActual,false);
		assertEquals(autollenado, "", "Se permitio la captura de texto"
				);
		
		/*
		 * PASO 24:
		 * Descripción del paso: Validar que no se permita la captura de caracteres especiales en Peso Bruto Vehicular
		 * 
		 * Resultado Esperado: No permitir la captura
		*/
		
		addStep("Validar que no se permita la captura de caracteres especiales en Peso Bruto Vehicular");
		campoActual="pesoBrutoVehicular";
		capturarCasilla=unidadesDetalle.capturarCasilla("%%%%#$@@", campoActual,false, true);
		assertTrue(mensajeCaptura+campoActual, capturarCasilla);
		autollenado=unidadesDetalle.obtenerTexto(campoActual,false);
		assertEquals(autollenado, "", "Se permitio la captura de texto"
				);
		/*
		 * PASO 29:
		 * Descripción del paso: Capturar valor valido en capacidad
		 * 
		 * Resultado Esperado: Permitir la captura
		 */
		addStep("Capturar valor valido en Capacidad");
		campoActual="capacidad";
		capturarCasilla=unidadesDetalle.capturarCasilla(capacidad, campoActual,false, true);
		assertTrue(mensajeCaptura+campoActual, capturarCasilla);
		autollenado=unidadesDetalle.obtenerTexto(campoActual, false);
		assertNotEquals(autollenado, "", "No se capturaron los valores"
				);	
		
		
		/*
		 * PASO 27:
		 * Descripción del paso: Capturar el valor minimo valido en peso bruto vehicular
		 * 
		 * Resultado Esperado: Permitir la captura
		*/
		
		addStep("Capturar el valor de peso bruto vehicular: "+valorValido);
		campoActual="pesoBrutoVehicular";
		capturarCasilla=unidadesDetalle.capturarCasilla(valorValido, campoActual,false, true);
		assertTrue(mensajeCaptura+campoActual, capturarCasilla);

		
		/*
		 * PASO 24:
		 * Descripción del paso: Seleccionar botón "Guardar"
		 * Resultado Esperado: Obtener mensaje esperado
		 */
		unidadesDetalle.bajarScroll(250);
		addStep("Seleccionar boton Guardar");
		campoActual=unidadesDetalle.guardarUnidad();
		getTestCase().addTextEvidenceCurrentStep("Estatus de guardado: "+campoActual);
		assertEquals(campoActual.replace(" ", ""), "Equipoagregadoexitosamente!", mensajeSeleccion);
			

		
		/*
		 * PASO 30:
		 * Descripción del paso: Buscar el Identificador recien agregado
		 * 
		 * Resultado Esperado: Encontrar valor
		 */
		
		addStep("Buscar el Identificador recien agregado: "+identificador);
		campoActual="capturaOrigen";
		capturarCasilla=admonUnidades.capturarCasilla(origen, campoActual,true, false);
		assertTrue(mensajeCaptura+campoActual, capturarCasilla);
		admonUnidades.buscarYQuitarAlerta();	
		campoActual="seleccionaBuscar";
		seleccionarCasilla=admonUnidades.seleccionarElemento(campoActual, false,origen);
		assertTrue(mensajeSeleccion+campoActual, seleccionarCasilla);
		admonUnidades.bajarScroll("500");
		admonUnidades.esperarActualizaTabla(origenUnidad);
		seleccionarCasilla=admonUnidades.buscaySeleccionaUnidad(identificador,true);
		assertTrue(mensajeSeleccion+"Registro", seleccionarCasilla);
//NEW	
		/*
		 * PASO 31:
		 * Descripción del paso: Obtener valor de capacidad actual
		 * 
		 * Resultado Esperado: Encontrar el mismo valor recien capturado
		 */
		
		addStep("Obtener valor de capacidad actual");
		 campoActual="capacidad";
		 campoActual=unidadesDetalle.obtenerTexto(campoActual, true);
		 assertEquals( campoActual, capacidad,
					"El texto encontrado no coincide con el esperado"
							);
		 /*
			 * PASO 32:
			 * Descripción del paso: Obtener valor de Peso Bruto Vehicular recien agregado
			 * 
			 * Resultado Esperado: Encontrar el mismo valor recien capturado
			 */
		 addStep("Obtener valor de Peso Bruto Vehicular");
		 campoActual="pesoBrutoVehicular";
		 campoActual=unidadesDetalle.obtenerTexto(campoActual, true);
		 assertEquals( campoActual, valorValido,
					"El texto encontrado no coincide con el esperado"
							);
		
		 
		 /*
			 * PASO 27:
			 * Descripción del paso: Capturar el valor minimo valido en peso bruto vehicular
			 * 
			 * Resultado Esperado: Permitir la captura
			*/
			
			addStep("Capturar valor no valido de peso bruto vehicular: "+valorNoValido);
			campoActual="pesoBrutoVehicular";
			capturarCasilla=unidadesDetalle.capturarCasilla(valorNoValido, campoActual,false, true);
			assertTrue(mensajeCaptura+campoActual, capturarCasilla);
		 
		 /*
			 * PASO 30:
			 * Descripción del paso: Seleccionar boton Guardar
			 * 
			 * Resultado Esperado: Permitir la captura
			 */
			
			addStep("Seleccionar boton Guardar");
			campoActual=unidadesDetalle.guardarUnidad();
			assertNotEquals(campoActual.replace(" ", ""), "Equipoagregadoexitosamente!", "El valor capturado no deberia ser valido"
					);
			getTestCase().addTextEvidenceCurrentStep("No fue posible guardar el valor: "+valorNoValido);

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
				+ "2.- Informacion para crear Unidad"+"\n";
	}

	@Override
	public String setTestDescription() {
		return "Agregar una Unidad desde el Portal de Carta Porte/ Administracion / Unidades.\r\n"
				+ "	 * Se agregara una nueva Unidad capturando caracteres alfabeticos y especiales en los campos de capacidad y peso bruto vehicular.\r\n"
				+ "	 * Los campos deben validar el tipo de informaicón que se captura. Asi como los valores minimos y maximos";
	}

	@Override
	public String setTestDesigner() {
		return "nellyoliviab@hexaware.com";
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_004_ValidarCaptura_PesoBrutoYCapacidad_Unidad_CartaPorte3";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}

}
