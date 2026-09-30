
package WEB.cartaPorte.cartaPorteCHG0045946;

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
import WEB.cartaPorte.objectModelPortalCP.CartaPorteOC;
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

public class ATC_FT_003_Editar_Unidad_OC_CartaPorte3 extends BaseExecution {
	/**
	 * PORTAL CARTA PORTE/ CARTA PORTE / CARTA PORTE OC
	 * 
	 * Cambio a validar: Se agregan campos de capacidad y peso bruto a la pantalla de Unidades
	 * 
	 * DESCRIPCION: 
	 * El objetivo del script es modificar una Unidad desde el Portal de Carta Porte/ Administracion / Unidades.
	 * Se realizará una búsqueda del identificador capturado en el archivo de data provider y al encontrarlo, se editara la información
	 * 	de los campos nuevos (capacidad y peso bruto vehicular).
	 * Se genera la Carta Porte OC, el número económico es el identificador de la Unidad que se modificó.
	 * El peso bruto mostrado se encuentra en toneladas, en la pantalla de unidades se captura por kilos.
	 * Al generar los documentos de Carta Porte OC, se debe imprimir el peso bruto en toneladas con los datos de la Unidad que se modificó.
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
	 *          -Validar disponibilidad de las ordenes de compra en el CEDIS que se ejecutará la prueba
	 *          -Nombre chofer y numero economico, asegurarse que estos datos pertenezcan al origen capturado
	 */
		@Test(dataProvider = "data-provider")
		public void ATC_FT_003_Editar_Unidad_OC_CartaPorte3_test(HashMap<String, String> data) throws FindFailed, SQLException {
			String portalurl = data.get("server");
			String userName=data.get("user");
			String password = PasswordUtil.decryptPassword(data.get("password"));
			String pac = data.get("pac");
			String campo=data.get("campo"); 
			String opcionAdmon=data.get("opcionAdmon");
			String origenAdmon=data.get("origenAdmon");
			String identificador=data.get("identificador");
			String tipoCartaPorte = data.get("tipoCartaPorte");
			String origen = data.get("origen");
			String destino = data.get("destino");
			String ordenCompra = data.get("ordenCompra");
			String nombreChofer = data.get("nombreChofer");
			String capacidad = data.get("capacidad");
			String pesoBrutoVehicular = data.get("pesoBrutoVehicular");
			String correo = data.get("correo");
			String rutaGuardarTira = data.get("rutaGuardarTira");
			testCase.setTest_Description("");
			testCase.setTest_Description(campo+" ."+"\n"+"PAC de prueba: "+pac);
			SeleniumUtil u=new SeleniumUtil(new ChromeTest(), true);
			LoginCP login = new LoginCP(u, testCase, portalurl);
			MenuPortal portalmenu= new MenuPortal(u, testCase);
			PortalCartaPorte portalCP= new PortalCartaPorte(u, testCase);
			CartaPorteOC portalOC= new CartaPorteOC(u, testCase, portalCP);
			AdministracionUnidades admonUnidades=new AdministracionUnidades(u, testCase);
			UnidadesDetalleEquipo unidadesDetalle=new UnidadesDetalleEquipo(u,testCase);
			String mensajeSeleccion= "No fue posible seleccionar elemento: ";
			// #CAMBIO BD
			utils.sql.SQLUtil dbFCPFACQA = new utils.sql.SQLUtil(
					 GlobalVariables.DB_HOST_FCPFACQA,
					 GlobalVariables.DB_USER_FCPFACQA, 
					 GlobalVariables.DB_PAST_FCPFACQA);
			String selectIDORIGEN="SELECT IDORIGEN FROM XXCAP.XXCAP_CEDIS_CAT"
					+ " WHERE DESCRIPCION= '"+origenAdmon+"'";
			String mensajeCaptura= "No fue posible capturar datos ";
			String mensajeEncontrar= "No fue posible encontrar elemento: ";
			
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
			 * Resultado Esperado: Encontrar mensaje: Encontrar menú "Administracion"
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
			addStep("Seleccionar opcion Unidades");
			String seleccionarElemento=portalCP.seleccionarOpcionAdministracion(opcionAdmon);
			assertEquals(seleccionarElemento, "Listado de Equipos", "No se encontro el texto: Listado de Equipos");
			
			/*
			 * PASO 6:
			 * Descripción del paso: Capturar origen del valor a modificar
			 * 
			 * Resultado Esperado: Actualizar registros con el origen capturado
			 */
			addStep("Capturar origen del valor a modificar");
			String campoActual="selectRemove";
			admonUnidades.seleccionarElemento(campoActual,false, null);
			
			campoActual="selectCasillaOrigen";
			admonUnidades.seleccionarElemento(campoActual,false, null);
			
			campoActual="capturaOrigen";
			admonUnidades.capturarCasilla(origenAdmon, campoActual, true, true);
			admonUnidades.buscarYQuitarAlerta();
			utils.sql.SQLResult resultadoOrigen= dbFCPFACQA.executeQuery(selectIDORIGEN);
			String origenId=resultadoOrigen.getFirstData();
			
			campoActual="seleccionaBuscar";
			boolean seleccionarCasilla=admonUnidades.seleccionarElemento(campoActual, false,origenAdmon);
			assertTrue(mensajeSeleccion+campoActual, seleccionarCasilla);
			admonUnidades.bajarScroll("500");
			u.hardWait(8);
			admonUnidades.esperarActualizaTabla(origenId);
			
			/*
			 * PASO 7:
			 * Descripción del paso: Buscar el identificador de la unidad a modificar
			 * 
			 * Resultado Esperado: Seleccionar el registro a editar
			 */
			addStep("Buscar el identificador de la unidad a modificar: "+identificador);
			seleccionarCasilla=admonUnidades.buscaySeleccionaUnidad(identificador,true);
			assertTrue(mensajeSeleccion+"Registro"
					, seleccionarCasilla);
			
			/*
			 * PASO 8:
			 * Descripción del paso: Modificar: Capacidad
			 * 
			 * Resultado Esperado: Continuar con la modificación del Peso Bruto Vehicular
			 */
			addStep("Modificar Numero de Poliza");
			campoActual="numeroPoliza";
			//Después de instalacion cambia de 10 a 30		
			String numeroPoliza=unidadesDetalle.generarNumeroPoliza(30);
			Boolean capturarCasilla=unidadesDetalle.capturarCasilla(numeroPoliza, campoActual,false, true);
			assertTrue(mensajeCaptura+campoActual
					, capturarCasilla);
				
			/*
			 * PASO 9:
			 * Descripción del paso: Seleccionar boton Guardar
			 * 
			 * Resultado Esperado: Regresar al listado de Unidades
			 */	
			addStep("Seleccionar nuevamente boton Guardar");
			campoActual=unidadesDetalle.guardarUnidad();
			assertEquals( campoActual.replace(" ", ""), "¡Equipoactualizadoexitosamente!",
					"No ha sido posible continuar con la informacion capturada");
			
			/*
			 * PASO 10:
			 * Descripción del paso: Seleccionar menú "Carta Porte"
			 * 
			 * Resultado Esperado: Encontrar opción "Carta Porte OC"
			 */	
			addStep("Seleccionar Menu de Carta Porte");
			
			String encontrarOpcionMenuCP=portalCP.encontrarOpcionesCartaPorte(tipoCartaPorte);
			assertEquals(encontrarOpcionMenuCP, "Carta Porte OC", "No se encontro la opcion: Carta Porte OC");
			
			/*
			 * PASO 11:
			 * Descripción del paso: Seleccionar opción "Carta Porte OC"
			 * 
			 * Resultado Esperado: Encontrar texto "Carta Porte OC"
			 */	
			addStep("Seleccionar opcion \"Carta Porte OC\"");
			String seleccionarCPOC=portalCP.seleccionarOpcionCartaPorte(tipoCartaPorte+"_edit");
			u.hardWait(5);	
			assertEquals(seleccionarCPOC, "Carta Porte OC", "No se encontro el texto: Carta Porte OC");
			
			/*
			 * PASO 12:
			 * Descripción del paso: Capturar campo: Origen
			 * 
			 * Resultado Esperado: Encontrar casilla: "Destino"
			 */
			addStep("Capturar campo: Origen");
			campoActual="origen";
			boolean encontrarCasilla= portalOC.capturarCasilla(origen, campoActual,true,true);
			assertTrue(mensajeCaptura+campoActual
					, encontrarCasilla);
			
			campoActual="destino";
			encontrarCasilla=portalOC.encontrarElemento(campoActual);
			assertTrue(mensajeEncontrar+campoActual
					, encontrarCasilla);
			
			/*
			 * PASO 13:
			 * Descripción del paso: Capturar campo: Destino
			 * 
			 * Resultado Esperado: Encontrar casilla: "OC"
			 */		
			addStep("Capturar campo: Destino");
			campoActual="destino";
			encontrarCasilla= portalOC.capturarCasilla(destino, campoActual,true,true);
			assertTrue(mensajeCaptura+campoActual
					, encontrarCasilla);
			
			addStep("Capturar campo: Orden compra");
			campoActual="ordenCompra";
			encontrarCasilla=portalOC.encontrarElemento(campoActual);
			u.hardWait(5);
			assertTrue(mensajeEncontrar+campoActual
					, encontrarCasilla);
			
		
			/*
			 * PASO 14:
			 * Descripción del paso: Agregar artículos
			 * 
			 * Resultado Esperado: mostar una nueva fila de artículo
			 * */
			
			// Generar un arreglo de ordenes de compra separadas por una coma ","
			int contador=0;
			boolean imprime=true;
			String ordenesCompras[] = ordenCompra.split(",");

			do {

				/*
				 * PASO 15: Descripción del paso: Capturar campo: Orden Compra
				 * 
				 * Resultado Esperado: Habilitar botón Buscar
				 */
				campoActual = "ordenCompra";
				encontrarCasilla = portalOC.capturarCasilla(ordenesCompras[contador], campoActual, false, false);
				assertTrue(mensajeCaptura + campoActual
						, encontrarCasilla);
				
				campoActual = "botonBuscar";
				encontrarCasilla = portalOC.encontrarElemento(campoActual);
				assertTrue(mensajeEncontrar + campoActual
						, encontrarCasilla);

				/*
				 * PASO 16: Descripción del paso: Seleccionar botón Buscar
				 * 
				 * Resultado Esperado: Mostrar registros de Orden de compra
				 */
				encontrarCasilla = portalOC.seleccionarElemento(campoActual, false);
				assertTrue(mensajeSeleccion + campoActual
						, encontrarCasilla);
				
				campoActual = "tablaBusqueda";
				encontrarCasilla = portalOC.encontrarElemento(campoActual);
				assertTrue(mensajeEncontrar + campoActual
						, encontrarCasilla);

				/*
				 * PASO 17: Descripción del paso: Seleccionar registro
				 * 
				 * Resultado Esperado: Habilitar elemento visual para mover registro a la
				 * sección derecha
				 */

				campoActual = "botonTiendas";
				encontrarCasilla = portalOC.seleccionarElemento(campoActual, false);
				assertTrue(mensajeSeleccion + campoActual
						, encontrarCasilla);
				
				campoActual = "elementoDerecha";
				encontrarCasilla = portalOC.encontrarElemento(campoActual);
				assertTrue(mensajeEncontrar + campoActual
						, encontrarCasilla);

				/*
				 * PASO 18: Descripción del paso: Seleccionar elemento visual para mover
				 * registro
				 * 
				 * Resultado Esperado: Habilitar casilla: "Nombre chofer"
				 */
				encontrarCasilla = portalOC.seleccionarElemento(campoActual, false);
				assertTrue(mensajeSeleccion + campoActual
						, encontrarCasilla);
				
				contador++;

			} while (contador < ordenesCompras.length);
			
			campoActual="botonTiendasDerecha";
			portalOC.buscarYQuitarAlerta();
			encontrarCasilla=portalOC.seleccionarElemento(campoActual, false);
			assertTrue(mensajeSeleccion+campoActual
					, encontrarCasilla);
			
			campoActual="nombreChofer";
			encontrarCasilla=portalOC.encontrarElemento(campoActual);
			assertTrue(mensajeEncontrar+campoActual
					, encontrarCasilla);
		
			/*
			 * PASO 19:
			 * Descripción del paso: Capturar nombre de transportista
			 * 
			 * Resultado Esperado: Encontrar casilla de número económico de transporte
			 */	
			addStep("Capturar nombre de transportista");
			encontrarCasilla= portalOC.capturarCasilla(nombreChofer, campoActual,true,true);
			assertTrue(mensajeCaptura+campoActual
					, encontrarCasilla);
			campoActual="numeroEconomico";
			encontrarCasilla=portalOC.encontrarElemento(campoActual);
			assertTrue(mensajeEncontrar+campoActual
					, encontrarCasilla);
			
			/*
			 * PASO 20:
			 * Descripción del paso: Capturar número económico de transporte al que recien se modifico su Peso Bruto Vehicular
			 * 
			 * Resultado Esperado: Autollenado de casillas: Placas, Seguros y Peos Bruto Vehicular
			 */	
			addStep("Capturar número económico de transporte al que recien se modifico su Peso Bruto Vehicular");
			encontrarCasilla= portalOC.capturarCasilla(identificador, campoActual,true,true);
			assertTrue(mensajeCaptura+campoActual
					, encontrarCasilla);
			encontrarCasilla=portalOC.verificarAutollenado();
			assertTrue("Los campos del transportista no fueron autollenados totalmente"
					, encontrarCasilla);
			/*
			 * PASO 21:
			 * Descripción del paso: Obtener el valor de Peso bruto en Toneladas
			 * 
			 * Resultado Esperado: Obtener valor esperado
			 */	
			addStep("Validar que el peso bruto vehicular se muestre en toneladas");
			portalCP.bajarScroll();
			pesoBrutoVehicular=portalOC.convertirKiloEnTonelada(pesoBrutoVehicular);
			campoActual="PesoBruto";
			campoActual=portalOC.obtenerPesoBrutoAutollenado(campoActual);
			assertEquals(campoActual, pesoBrutoVehicular ,"El peso bruto vehicular no coincide con la ultima modificacion");
		
			/*
			 * PASO 21:
			 * Descripción del paso: Seleccionar: "Generar Carta Porte"
			 * 
			 * Resultado Esperado: Descarga de archivo .zip con formatos XML y PDF, se muestra pantalla de envio de correo.
			 */	
			addStep("Se generan documentos de carta porte");
			campoActual="generarCartaPorte";
			encontrarCasilla=portalOC.seleccionarElemento(campoActual, false);
			assertTrue(mensajeSeleccion+campoActual, encontrarCasilla);
			
			encontrarCasilla=portalOC.determinarEstatusTimbrado();
			assertTrue("Timbrado fallo", encontrarCasilla);
			
			/*
			 * PASO 22:
			 * Descripción del paso: Captura de correo para enviar documentos
			 * 
			 * Resultado Esperado: Se envia documento .zip con formatos XML y PDF, se muestra visualización de la tira de impresión
			 */	
			addStep("Captura de correo para enviar documentos");
			//Se camptura el correo
			campoActual="capturaCorreo";
			encontrarCasilla= portalOC.capturarCasilla(correo, campoActual,false,false);
			assertTrue(mensajeCaptura+campoActual
					, encontrarCasilla);
			
			//Se da clic en el botón de enviar
			campoActual="botonEnviar";
			encontrarCasilla= portalOC.seleccionarElemento(campoActual, false);
			assertTrue(mensajeSeleccion+campoActual
					, encontrarCasilla);
			getTestCase().addTextEvidenceCurrentStep("Se muestra tira de impresion");
			u.hardWait(10);
			
			//Guardar la tira de impresión
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
			String nombreTira="CartaPorteTiraDocOC_"+String.valueOf(portalCP.generarNumeroAleatorio(300, 201))+".pdf";
			getTestCase().addTextEvidenceCurrentStep("Se guarda documento: "+nombreTira);
			sk.screen.type(nombreTira);
			u.hardWait(10);
			sk.screen.type(Keys.TAB);
			u.hardWait(10);
			p.clickImagen("saveCP");
			u.hardWait(10);
			getTestCase().addTextEvidenceCurrentStep("Las validaciones sobre el contenido de cada documento se realizaran de forma manual");
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
					+ "2.- Informacion para modificar Unidad"+"\n"
					+ "3.- Origen de Cedis o PT"+"\n"
					+ "4.- Destino (Proveedor) "+"\n"
					+ "5.- Ordenes de compra disponibles"+"\n"
					+ "6.- Datos del transportista"+"\n";
		}

		@Override
		public String setTestDescription() {
			return "Modificar una Unidad desde el Portal de Carta Porte/ Administracion / Unidades.\r\n"
					+ "	Se realizará una búsqueda del identificador capturado en el archivo de data provider y al encontrarlo, se editara la información\r\n"
					+ " de los campos nuevos (capacidad y peso bruto vehicular).\r\n"
					+ " Se genera la Carta Porte OC, el número económico es el identificador de la Unidad que se modificó.";
		}

		@Override
		public String setTestDesigner() {
			return "nellyoliviab@hexaware.com";
		}

		@Override
		public String setTestFullName() {
			return "ATC_FT_003_GenerarYEditarUnidad_Manual_CartaPorte3_test";
		}

		@Override
		public String setTestInstanceID() {
			// TODO Auto-generated method stub
			return null;
		}
}
