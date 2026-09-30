package EBS.activoFijo.folioDeEntrada;

import static org.testng.Assert.assertTrue;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import org.sikuli.script.Pattern;
import org.testng.annotations.Test;

import EBS.util.GlobalVariables;
import EBS.util.FolioPagoMVP2.EBSChrome;
import EBS.util.appletsMethods.MetodosSikuli;
import EBS.util.pageObjectModel.EBSLogin;
import EBS.util.pageObjectModel.EBSPaginaInicial;
import modelo.BaseExecution;
import utils.SikuliUtil;
import utils.selenium.ChromeTest;
import utils.selenium.IETest;
import utils.selenium.SeleniumUtil;

/**
* DESCRIPCIÓN: Validar que se genere el folio de entrada y se actualice la tabla intermedia con los artículos de uso 04..
* 
* NOMBRE CASOS DE PRUEBA MANUAL QUE SE ABARCAN: 
* 	-MTC-FT-049 GeneracionDeFolioDeEntrada
* 
* PREREQUISITOS:
* 	- Periodo Abierto de Compras
* 	- Periodo Abierto de Inventarios
* 
* INSUMOS:
* 	- Ordenes de compra pendientes a ser recibidas
* 
* @author Jesus Moreno
* @date 16/Febrero/2026
*/
public class ATC_FT_049_GeneracionDeFolioDeEntrada extends BaseExecution {

	@Test(dataProvider = "data-provider")
	public void ATC_FT_049_GeneracionDeFolioDeEntrada_test(HashMap<String, String> data) throws Exception {

		// UTILERIAS
		SikuliUtil sk = new SikuliUtil();
		String pathImg = sk.PatternPath("EBS\\EBS");
		Pattern pathimg = new Pattern();
		ArrayList<String> ListImage = new ArrayList<String>();
		HashMap<String, Pattern> ImgPatter = new HashMap<String, Pattern>();
		ArrayList<Pattern> patterns = new ArrayList<Pattern>();
		File Fol = new File(pathImg);
		SeleniumUtil u = new SeleniumUtil(new ChromeTest());
		EBSPaginaInicial paginaInicialEBS = new EBSPaginaInicial(u, testCase);
		String PatternsImg = "EBS_Folio_Pago";
		EBSChrome ebs = new EBSChrome(u, testCase, PatternsImg);
		MetodosSikuli p = new MetodosSikuli(sk, u, pathimg, ListImage, ImgPatter, patterns, Fol);
		utils.sql.SQLUtil dbEBS = new utils.sql.SQLUtil(GlobalVariables.DB_HOST_AVEBQA, GlobalVariables.DB_USER_AVEBQA,
				GlobalVariables.DB_PASSWORD_AVEBQA);

		// Variables
		String server = data.get("server");
		String responsabilidad = data.get("responsabilidad");
		String carpeta = data.get("carpeta");
		String subCarpeta = data.get("subCarpeta");
		String responsabilidad1 = data.get("responsabilidad1");
		String carpeta1 = data.get("carpeta1");
		String subCarpeta1 = data.get("subCarpeta1");
		String subCarpeta2 = data.get("subCarpeta2");
		String logoutEBS = data.get("logoutEBS");
		String OC = "";
		String invoiceOC = data.get("invoiceOC");
		String concurrente = data.get("concurrente");
		String queryEBS1 = data.get("queryEBS1");
		String queryEBS2 = data.get("queryEBS2");
		String busqueda = data.get("busqueda");
		String unidadOperativa = data.get("unidadOperativa");
		String tipoOC = data.get("tipoOC");
		String tipoDivisa = data.get("tipoDivisa");
		String orgEnvio = data.get("orgEnvio");
		String rangoInf = data.get("rangoInf");
		String rangoSup = data.get("rangoSup");
		String articulo = "";
		String tipoDeUso = "";
		String articulosBloqueados = "32520";

		Boolean flag = false;

		// Buscando Orden de Compra
		// Paso 1. Ingresar a EBS --> Clic en Language y seleccionar Español -->
		// Ingresar usuario y contraseña --> Clic en Conéctese
		addStep("Ingresar a EBS --> Clic en Language y seleccionar Español --> Ingresar usuario y contraseña --> Clic en Conéctese");
		EBSLogin login = new EBSLogin(u, testCase, server, GlobalVariables.getJesusHexEbsUsr,
				GlobalVariables.getJesusHexEbsPwd);
		login.navigate(server);
		login.setCredentials();
		login.setLanguage();
		testCase.addScreenShotCurrentStep("Paso 1");
		login.clickLogIn();
		
		p.validarImagen2("carpetaEBS");
		p.presionarESC();
		
		
		// Paso 2. Seleccionar la responsabilidad de MXOXXO-POXXXXX- Super
		// Usuario/Ordenes de Compra/Purchase Order Summary
		addStep("Seleccionar la responsabilidad de MXOXXO-POXXXXX- Super Usuario/Ordenes de Compra/Purchase Order Summary");
		paginaInicialEBS.clickOnResponsabilidad2(responsabilidad);
		u.hardWait(1);
		paginaInicialEBS.clickOnResponsabilidad2(carpeta);
		paginaInicialEBS.clickOnResponsabilidad2(subCarpeta);
		u.hardWait(1);
		ebs.startEBS();
		testCase.addScreenShotCurrentStep("Paso 2");

		// Paso 3. Capturar los parámetros de encabezado
//				Unidad Operativa
//				Tipo
//				Org Envío 
//				Rangos Fecha
		//
//				Capturar parámetros sección Rangos Fecha:
//				Aprobado
		addStep("Capturar los parámetros de encabezado\r\n" + "Unidad Operativa\r\n" + "Tipo\r\n" + "Org Envío \r\n"
				+ "Rangos Fecha\r\n" + "\r\n" + "Capturar parámetros sección Rangos Fecha:\r\n" + "Aprobado");
		p.pegarTexto(unidadOperativa);
		p.nTAB(2);
		p.pegarTexto(tipoOC);
		p.nTAB(1);
		u.hardWait(1);
		p.pegarTexto(tipoDivisa);
		p.nTAB(1);
		p.pegarTexto("PLANNET");
		p.nTAB(2);
		u.hardWait(1);
		p.pegarTexto(orgEnvio);
		p.clickImagen("rangosFecha");
		p.validarImagen2("rangosFechaON");
		p.nTAB(2);
		//p.pegarTexto(rangoInf);
		p.nTAB(1);
		u.hardWait(2);
		//p.pegarTexto(rangoSup);
		testCase.addScreenShotCurrentStep("Paso 3");

		// Paso 4. Clic en el botón Encontrar
		addStep("Clic en el botón Encontrar");
		p.clickImagen("encontrarOC3");
		p.validarImagen2("envioOC");
		testCase.addScreenShotCurrentStep("Paso 4");

		// Paso 5. Tomar una Orden de Compra que tenga artículos de uso 04 con el query
		// select segment1, attribute1 from apps.mtl_system_items_b
		// where segment1 IN ('articulo');
		addStep("Tomar una Orden de Compra que tenga artículos de uso 04 con el query select segment1, attribute1 from apps.mtl_system_items_b\r\n"
				+ "where segment1 IN ('articulo');");
		p.clickImagen("lineaOC");
		
//		p.validarImagen2("ordenadoPorLinea");
//		p.nTAB(4);
//
//		utils.sql.SQLResult resultadoContenedor = dbEBS.executeQuery(queryEBS1 + "'" + articulo + "'");
//
//		flag = false;
//		int safety = 0;
//		int maxSafety = 1000;
//		while (!flag && safety < maxSafety) {
//			articulo = p.copiarTextoEnVariable(articulo);
//			System.out.println("Probando artículo: " + articulo);
//			resultadoContenedor = dbEBS.executeQuery(queryEBS1 + "'" + articulo + "'");
//			boolean tieneUso04 = false;
//			try {
//				int filas = -1;
//				try {
//					filas = resultadoContenedor.getRowCount();
//				} catch (Throwable t) {
//				}
//				if (filas >= 0) {
//					for (int r = 0; r < filas; r++) {
//						tipoDeUso = resultadoContenedor.getData(1, r);
//						if ("04".equals(tipoDeUso)) {
//							tieneUso04 = true;
//							break;
//						}
//					}
//				} else {
//					int r = 0;
//					while (true) {
//						tipoDeUso = null;
//						try {
//							tipoDeUso = resultadoContenedor.getData(1, r);
//						} catch (Throwable t) {
//							break;
//						}
//						if (tipoDeUso == null)
//							break;
//						if ("04".equals(tipoDeUso)) {
//							tieneUso04 = true;
//							break;
//						}
//						r++;
//					}
//				}
//			} catch (Exception e) {
//				System.err.println("Error leyendo resultadoContenedor para " + articulo + ": " + e.getMessage());
//				logger.error("Error leyendo resultadoContenedor", e);
//			}
//
//			if (tieneUso04) {
//				System.out.println("El artículo " + articulo + " es de uso 04");
//				p.nShiftTAB(4);
//				u.hardWait(4);
//				flag = true;
//				OC = p.copiarTextoEnVariable(OC);
//			} else {
//				System.out.println(
//						"El artículo " + articulo + " es de uso distinto a 04, probando siguiente artículo ...");
//				p.nArrowDown(1);
//			}
//			safety++;
//		}
//		if (!flag) {
//			System.out.println("No se encontró ningún artículo con uso 04 tras " + safety + " iteraciones.");
//		}
		
		// java
		// Número objetivo de OCs a recolectar
		int NUM_OC = 100;

		p.validarImagen2("ordenadoPorLinea");
		p.nTAB(4);
		


		utils.sql.SQLResult resultadoContenedor = dbEBS.executeQuery(queryEBS1 + "'" + articulo + "'");

		flag = false;
		int safety = 0;
		int maxSafety = 50;
		java.util.List<String> ocList = new java.util.ArrayList<>();
		java.util.Set<String> ocSet = new java.util.HashSet<>(); // para evitar duplicados


		// java
		// Lista negra de artículos a omitir (puedes agregar más)
		java.util.Set<String> blackList = new java.util.HashSet<>();
		blackList.add("32520");

		// ... (asegúrate de que NUM_OC, ocList, ocSet, etc. estén definidos previamente)

		while (!flag && safety < maxSafety && ocList.size() < NUM_OC) {
		    // Obtener artículo actual desde la UI
		    try {
		        articulo = p.copiarTextoEnVariable(articulo);
		    } catch (Exception e) {
		        logger.error("Fallo copiando artículo en iteración " + safety + ", intentando siguiente fila", e);
		        System.out.println("Fallo copiando artículo, moviéndose al siguiente...");
		        p.nArrowDown(1);
		        safety++;
		        continue;
		    }

		    String articuloTrim = (articulo != null) ? articulo.trim() : "";
		    // Comprobar lista negra antes de hacer la consulta
		    if (!articuloTrim.isEmpty() && blackList.contains(articuloTrim)) {
		        System.out.println("Artículo en lista negra, se omite: " + articuloTrim);
		        logger.info("Artículo en lista negra: " + articuloTrim + ". Avanzando al siguiente.");
		        p.nArrowDown(1);
		        safety++;
		        continue;
		    }

		    System.out.println("Probando artículo: " + articuloTrim);
		    resultadoContenedor = dbEBS.executeQuery(queryEBS1 + "'" + articuloTrim + "'");
		    boolean tieneUso04 = false;
		    try {
		        int filas = -1;
		        try {
		            filas = resultadoContenedor.getRowCount();
		        } catch (Throwable t) {
		            // método no disponible, usaremos fallback
		        }
		        if (filas >= 0) {
		            for (int r = 0; r < filas; r++) {
		                tipoDeUso = resultadoContenedor.getData(1, r);
		                if ("04".equals(tipoDeUso)) {
		                    tieneUso04 = true;
		                    break;
		                }
		            }
		        } else {
		            int r = 0;
		            while (true) {
		                tipoDeUso = null;
		                try {
		                    tipoDeUso = resultadoContenedor.getData(1, r);
		                } catch (Throwable t) {
		                    break;
		                }
		                if (tipoDeUso == null) break;
		                if ("04".equals(tipoDeUso)) {
		                    tieneUso04 = true;
		                    break;
		                }
		                r++;
		            }
		        }
		    } catch (Exception e) {
		        System.err.println("Error leyendo resultadoContenedor para " + articuloTrim + ": " + e.getMessage());
		        logger.error("Error leyendo resultadoContenedor", e);
		    }

		    if (tieneUso04) {
		        System.out.println("El artículo " + articuloTrim + " es de uso 04. Intentando capturar OC.");
		        // Ir a la columna OC
		        p.nShiftTAB(4);
		        u.hardWait(1);

		        // Intentar copiar la OC con reintentos (hasta 3)
		        String ocCaptured = null;
		        int maxRetries = 3;
		        int attempt = 0;
		        while (attempt < maxRetries) {
		            try {
		                ocCaptured = p.copiarTextoEnVariable(OC);
		                if (ocCaptured != null) ocCaptured = ocCaptured.trim();
		                if (ocCaptured != null && !ocCaptured.isEmpty()) {
		                    break; // éxito
		                } else {
		                    logger.warn("OC copiada vacía en intento " + (attempt + 1));
		                }
		            } catch (Exception e) {
		                logger.warn("Error copiando OC en intento " + (attempt + 1), e);
		            }
		            attempt++;
		            u.hardWait(1); // pequeña espera antes de reintento
		        }

		        if (ocCaptured != null && !ocCaptured.isEmpty()) {
		            // Añadir solo si no está ya en la lista (evitar duplicados)
		            if (!ocSet.contains(ocCaptured)) {
		                ocSet.add(ocCaptured);
		                ocList.add(ocCaptured);
		                System.out.println("OC válida capturada y guardada: " + ocCaptured);
		                logger.info("OC válida capturada: " + ocCaptured);
		            } else {
		                System.out.println("OC ya existente (se descarta): " + ocCaptured);
		            }
		        } else {
		            System.out.println("No se pudo capturar OC válida tras " + maxRetries + " intentos para el artículo: " + articuloTrim);
		            logger.warn("No se pudo capturar OC tras reintentos para artículo: " + articuloTrim);
		        }
		        // Volver a columna Artículos para continuar
		        p.nTAB(4);
		        u.hardWait(1);
		        // Si aún no hemos recogido el número objetivo, avanzar a la siguiente fila
		        if (ocList.size() < NUM_OC) {
		            p.nArrowDown(1);
		        }
		        // Si alcanzamos la cantidad deseada salimos del bucle
		        if (ocList.size() >= NUM_OC) {
		            flag = true;
		        }
		    } else {
		        System.out.println("El artículo " + articuloTrim + " es de uso distinto a 04, probando siguiente artículo ...");
		        p.nArrowDown(1);
		    }
		    safety++;
		}

		if (!flag && ocList.size() < NUM_OC) {
		    System.out.println("No se logró obtener " + NUM_OC + " OC válidas tras " + safety + " iteraciones. OCs obtenidas: " + ocList.size());
		} else {
		    System.out.println("Proceso finalizado. OCs obtenidas: " + ocList.size());
		}
		
		
		

		
		testCase.addScreenShotCurrentStep("Paso 5");
		testCase.addBoldTextEvidenceCurrentStep("Lista de OC encontradas con artículos de uso 04: ");
		
		// java
		// Imprimir resumen y detalle de las OCs recolectadas
		System.out.println("OCs recolectadas: " + ocList.size());
		if (ocList.isEmpty()) {
		    System.out.println("No se encontraron OCs válidas.");
		} else {
		    // Lista completa en una sola línea
		    System.out.println("Lista completa de OCs: " + ocList);
		    // Detalle por línea con índice
		    for (int i = 0; i < ocList.size(); i++) {
		        System.out.println("OC[" + (i + 1) + "]: " + ocList.get(i));
		        testCase.addBoldTextEvidenceCurrentStep("OC[" + (i + 1) + "]: " + ocList.get(i));
		    }
		}
		
		
		p.altF4();
		p.enter();
		paginaInicialEBS.clickOnResponsabilidad2(logoutEBS);

		// Paso 6. Ingresar a EBS --> Clic en Language y seleccionar Español --> Ingresar
		// usuario y contraseña --> Clic en Conéctese
		addStep("Ingresar a EBS --> Clic en Language y seleccionar Español "
				+ "--> Ingresar usuario y contraseña --> Clic en Conéctese");
		login = new EBSLogin(u, testCase, server, GlobalVariables.get5032838EbsUsr,
				GlobalVariables.get5032838HexEbsPwd);
		login.navigate(server);
		login.setCredentials();
		login.setLanguage();
		testCase.addScreenShotCurrentStep("Paso x1");
		login.clickLogIn();
		p.validarImagen2("carpetaEBS");
		p.presionarESC();

		// Paso 7. Seleccionar la responsabilidad de
		// MXOXXO-IN17FAA-Admon_ACT_FIJO_OS/Transacciones/Recepción/Receipts
		addStep("Seleccionar la responsabilidad de MXOXXO-IN17FAA-Admon_ACT_FIJO_OS/Transacciones/Recepción/Receipts");

		paginaInicialEBS.clickOnResponsabilidad2(responsabilidad1);
		u.hardWait(1);
		paginaInicialEBS.clickOnResponsabilidad2(carpeta1);
		u.hardWait(1);
		paginaInicialEBS.clickOnResponsabilidad2(subCarpeta1);
		u.hardWait(1);
		paginaInicialEBS.clickOnResponsabilidad2(subCarpeta2);
		ebs.startEBS();
		p.validarImagen2("orgs");
		testCase.addScreenShotCurrentStep("Paso 2");

		// Paso 8. Seleccionar el código: ACA
		addStep("Seleccionar el código:\r\n" + "ACA");
		testCase.addScreenShotCurrentStep("Paso 2");
		p.enter();

		// Paso 9. Capturar el campo Orden de Compra/Clic en el botón Encontrar
		addStep("Capturar el campo Orden de Compra/Clic en el botón Encontrar");

		// java
		int totalOCs = ocList.size();
		boolean accepted = false;

//		// Situarse en el field de la OC
//		p.nTAB(2);
//
//		for (int idx = 0; idx < totalOCs && !accepted; idx++) {
//		    String ocToTest = ocList.get(idx);
//		    System.out.println("OC a probar: " + ocToTest);
//
//		    // Pegar la OC y buscar
//		    p.pegarTexto(ocToTest);
//		    p.clickImagen("findOC");
//		    u.hardWait(1); // espera breve para que la UI reaccione
//
//		    boolean error = p.validarImagenBoolean("error2");
//		    boolean error2 = p.validarImagenBoolean("recepcionesACAFreeze");
//		    if (error) {
//		        // Cerrar la ventanita de error, limpiar registro y volver a posicionarse
//		        p.enter();
//		        u.hardWait(1);
//		        p.clickImagen("limpiarRegistro");
//		        u.hardWait(1);
//		        p.nTAB(2);
//		    } else {
//		        // OC aceptada -> asignar y marcar éxito
//		        OC = ocToTest;
//		        System.out.println("OC aceptada: " + OC);
//		        accepted = true;
//		    }
//		}
//
//		if (!accepted) {
//		    throw new AssertionError("No se encontró ninguna OC candidata en la lista.");
//		}
		
		// java
		// Situarse en el field de la OC
		p.nTAB(2);
		testCase.addScreenShotCurrentStep("Paso 9");

		for (int idx = 0; idx < totalOCs && !accepted; idx++) {
		    String ocToTest = ocList.get(idx);
		    System.out.println("OC a probar: " + ocToTest);

		    // Pegar la OC y buscar
		    p.pegarTexto(ocToTest);
		    p.clickImagen("findOC");
		    u.hardWait(1); // espera breve para que la UI reaccione

		    boolean error = p.validarImagenBoolean("error2");
		    boolean stillOnScreen = p.validarImagenBoolean("recepcionesACAFreeze");

		    if (error) {
		        // Se mostró la ventana de error clásica: cerrarla, limpiar y reposicionar
		        System.out.println("Error clásico detectado para OC: " + ocToTest);
		        p.enter();
		        u.hardWait(1);
		        p.clickImagen("limpiarRegistro");
		        u.hardWait(1);
		        p.nTAB(2);
		    } else if (stillOnScreen) {
		        // No avanzó de pantalla (la pantalla sigue presente): la OC no es válida
		        System.out.println("La pantalla no avanzó (recepcionesACAFreeze) para OC: " + ocToTest + ". Se descarta.");
		        // Limpiar y volver a posicionarse para probar la siguiente OC
		        try {
		            p.clickImagen("limpiarRegistro");
		        } catch (Throwable t) {
		            // Si limpiarRegistro no está disponible, intentar enviar ESC/Enter como fallback
		            try { p.enter(); } catch (Throwable ignore) {}
		        }
		        u.hardWait(1);
		        p.nTAB(2);
		    } else {
		        // No se detectó error ni permanencia en la misma pantalla -> OC aceptada
		        OC = ocToTest;
		        System.out.println("OC aceptada: " + OC);
		        accepted = true;
		    }
		}

		if (!accepted) {
		    throw new AssertionError("No se encontró ninguna OC candidata en la lista.");
		}
		



		// Paso 10. Clic en el botón que se encuentra dentro [ ]
		addStep("Clic en el botón que se encuentra dentro [  ]");
		testCase.addScreenShotCurrentStep("Paso 8");

		// Paso 11. Capturar el campo Factura/Clic en el botón aceptar
		addStep("Capturar el campo Factura/Clic en el botón aceptar");
		p.clickImagen("invoiceField");
		p.validarImagen2("shipmentHeaders");
		p.pegarTexto(invoiceOC);
		testCase.addScreenShotCurrentStep("Paso 9");
		p.enter();

		// Paso 12. Seleccionar el check de las líneas que se van a recibir/Clic en el
		// botón Guardar
		addStep("Seleccionar el check de las líneas que se van a recibir/Clic en el botón Guardar");
		p.validarImagen2("recepcionesACA");
		p.clickImagen("edicion");
		p.clickImagen("seleccionarTodo");

		flag = true;
		while (flag) {
			flag = p.validarImagenBoolean("nota");
			if (flag) {
				p.enter();
				u.hardWait(1);
			}
		}
		testCase.addScreenShotCurrentStep("Paso 10");
		p.clickImagen("save");

		// Paso 8. Clic en Ver/Solicitudes/Encontrar
		addStep("Clic en Ver/Solicitudes/Encontrar");
		p.clickImagen("verConcurrentes");
		p.clickImagen("solicitudesConcurrentes");
		p.validarImagen2("encontrarSolicitudes2");
		p.clickImagen("encontrarSolicitud3");
		p.validarImagen2("refrescoAutomatico");

		flag = true;
		while (flag) {

			flag = p.validarImagenBoolean("normal");

			if (flag) {
				p.clickImagen("refresh2");
				u.hardWait(5);
			} else {
				System.out.println("Concurrente terminado");
			}
		}
		testCase.addScreenShotCurrentStep("Paso 11");

		// Paso 9. Abrir el concurrente XXFC-Reporte de Entradas de Almacén
		// (Valorizaciones)
		addStep("Abrir el concurrente XXFC-Reporte de Entradas de Almacén (Valorizaciones)");
		p.clickImagen("encontrar_solicitudes");
		p.validarImagen2("solicitud_especifica");
		p.clickImagen("solicitud_especifica");
		p.validarImagen2("nombre_concurrente");
		p.clickImagen("nombre_concurrente");
		u.hardWait(1);
		p.pegarTexto(concurrente);
		p.validarImagen2("encontrar_concurrente");
		p.clickImagen("encontrar_concurrente");
		p.validarImagen2("solicitudes2");
		testCase.addScreenShotCurrentStep("Paso 12");
		p.validarImagen2("refrescoAutomatico");
		p.clickImagen("visualizarRegistro2");
		u.hardWait(5);
		p.keyCtrl("F");
		p.pegarTexto(busqueda);
		p.enter();
		testCase.addScreenShotCurrentStep("Paso 12.1");
		p.altF4();
		p.validarImagen2("refrescoAutomatico");
		p.clickImagen("visualizarOutput2");
		p.validarImagen2("archivo2");
		u.hardWait(5);
		testCase.addScreenShotCurrentStep("Paso 12.2");
		p.altF4();
		p.validarImagen2("refrescoAutomatico");
		p.altF4();
		p.enter();
		u.close();
		u.quit();

		// Paso 13. Validar la tabla intermedia xxfa_sn_data_details
		addStep("Validar la tabla intermedia xxfa_sn_data_details");
		resultadoContenedor = dbEBS.executeQuery(queryEBS2 + " '" + invoiceOC + "'");
		testCase.addBoldTextEvidenceCurrentStep("Registro insertado en tabla intermedia: ");
		testCase.addQueryEvidenceCurrentStep(resultadoContenedor);

	}

	@Override
	public void beforeTest() {
		// TODO Auto-generated method stub
	}

	@Override
	public String setPrerequisites() {
		// TODO Auto-generated method stub
		return "\r\n"
				+ "Periodo Abierto de Compras \r\n"
				+ "Periodo Abierto de Inventarios";
	}

	@Override
	public String setTestDesigner() {
		// TODO Auto-generated method stub
		return "JesusM3@hexaware.com";
	}

	@Override
	public String setTestFullName() {
		// TODO Auto-generated method stub
		return "ATC_FT_049_GeneracionDeFolioDeEntrada_test";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String setTestDescription() {
		// TODO Auto-generated method stub
		return "Validar que se genere el folio de entrada y se actualice la tabla intermedia con los artículos de uso 04.";
	}

}