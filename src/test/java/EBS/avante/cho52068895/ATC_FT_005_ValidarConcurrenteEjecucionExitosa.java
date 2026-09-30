package EBS.avante.cho52068895;

import static org.testng.Assert.assertEquals;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import org.sikuli.script.Pattern;
import org.testng.annotations.Test;

import EBS.tools.LoginTool;
import EBS.util.appletsMethods.MetodosSikuli;
import EBS.util.pageObjectModel.EBSPaginaInicial;
import modelo.BaseExecution; import EBS.util.GenericMethods; import EBS.util.patternObject.PatternUtil;
import utils.SikuliUtil;
import utils.password.PasswordUtil;
import utils.selenium.IETest;
import utils.selenium.SeleniumUtil;

public class ATC_FT_005_ValidarConcurrenteEjecucionExitosa extends BaseExecution {

	@Test(dataProvider = "data-provider")

	public void ATC_FT_005_ValidarConcurrenteEjecucionExitosa_test(HashMap<String, String> data) throws Exception {
		/*
		 * VARIABLES
		 */
		String ebsUrl = data.get("server");
		String userName = data.get("userName");
		String password = PasswordUtil.decryptPassword(data.get("password"));
		String responsabilidad = data.get("responsabilidad");
		String almacen = data.get("carpeta1");
		String otros = data.get("carpeta2");
		String solicitudes = data.get("carpeta3");
		String requests = data.get("carpeta4");
		String campo1 = data.get("campo1");
		String campo2 = data.get("campo2");
		String campo3 = data.get("campo3");
		String ebsPattern = data.get("ebsPattern")+"\\EBS";
		String text = data.get("text");
		String respaldo = data.get("respaldo");

		/*
		 * UTILERIAS
		 */
		SeleniumUtil u = new SeleniumUtil(new IETest(), true);
		LoginTool inicio = new LoginTool(u, testCase, ebsUrl, userName, password);
		EBSPaginaInicial paginaInicial = new EBSPaginaInicial(u, testCase);
		SikuliUtil sk = new SikuliUtil();
		String pathImg = sk.PatternPath(ebsPattern);
		Pattern pathimg = new Pattern();
		ArrayList<String> ListImage = new ArrayList<String>();
		HashMap<String, Pattern> ImgPatter = new HashMap<String, Pattern>();
		ArrayList<Pattern> patterns = new ArrayList<Pattern>();
		File Fol = new File(pathImg);
		File Folder = new File(pathImg);
		GenericMethods action = new GenericMethods(u);

		MetodosSikuli p = new MetodosSikuli(sk, u, pathimg, ListImage, ImgPatter, patterns, Fol);

		PatternUtil patternUtil = new PatternUtil(sk, u, pathimg, ListImage, ImgPatter, patterns, Folder);

		/*
		 * PASOS DEL CASO DE PRUEBA, MATRIZ: CHO52068895
		 */

		// PASO 1
		addStep("Ingresar al ambiente de EBS.");
		logger.info("EBS URL: " + ebsUrl);
		System.out.println("EBS URL: " + ebsUrl);
		logger.info("User Name: " + userName);
		System.out.println("User Name: " + userName);
		// PASO 2
		addStep("Seleccionar idioma: Espanol Latinoamericano.");
		// PASO 3
		addStep("Ingresar usuario y contrasena.");
		// PASO 4
		addStep("Dar clic en el boton de 'Conectese'.");
		inicio.login();
		// PASO 5
		addStep("Ingresar a la responsabilidad 'MXOXXO-WM17FAA-COORDINADOR EQP FRM'");
		paginaInicial.clickOnResponsabilidad(responsabilidad);
		// PASO 6
		addStep("Seleccionar: Administracion de Almacén -> Otros -> Solicitudes -> Requests");
		paginaInicial.clickOnCarpeta(almacen);
		paginaInicial.clickOnCarpeta(otros);
		paginaInicial.clickOnCarpeta(solicitudes);
		paginaInicial.clickOnCarpeta(requests);
		paginaInicial.clickRunWmCoEqpFrm();
		u.hardWait(10);
		patternUtil.runApplet();
		// PASO 7
		addStep("Clic en opcion Solicitud unica, Aceptar.");
		patternUtil.solicitudUnica();
		// PASO 8
		addStep("Ingresar en el campo Nombre 'XXFC - Reporte de Pedidos Con Reserva de Inventario' ");
		patternUtil.typeAndTab(campo1);
		u.hardWait(5);
		patternUtil.keyAlt("a");
		// PASO 9
		addStep("Clic en el boton de Ejecutar.");
		patternUtil.clickEjecutar();
		u.hardWait(3);
		// PASO 10
		addStep("¿Ejecutar Otra Solicitud? No.");
		patternUtil.ejecutarOtraSolicitud("No");
		u.hardWait(3);
		// PASO 11
		addStep("Dirigirse al menu: Ver(x) -> 'Solicitudes' -> 'Encontrar'.");
		patternUtil.encontrarSolicitudes();
		u.hardWait(3);
		patternUtil.clickEncontrar();
		// PASO 12
		addStep("Dar clic en Ver Detalles");
		patternUtil.clickVisualizarDetalles();
		u.hardWait(3);
		patternUtil.clickAceptarDetalle();
		u.hardWait(3);
		// PASO 13
		addStep("Dar clic en Visualizar Output");
		patternUtil.clickVisualizarOutput();
		u.hardWait(5);
		// PASO 14
		addStep("Dar clic en Save");
		patternUtil.clickSave();
		// PASO 15
		addStep("Abrir el archivo");
		u.hardWait(180);
		patternUtil.clickOpenFile();
		u.hardWait(3);
		patternUtil.keyAlt("y");
		u.hardWait(10);
		// PASO 16
		addStep("Validar que contenga información el archivo");
		patternUtil.existeInformacion();
		u.hardWait(3);
		// PASO 17
		addStep("Dar clic en ejecutar nueva solicitud");
		patternUtil.clickOracle();
		patternUtil.keyAlt("j");
		u.hardWait(3);
		// PASO 18
		addStep("Ingresar en el campo Nombre 'XXFC - Preparar Reservas Inventario Fisico (Paso 1)' ");
		patternUtil.typeAndTab(campo2);
		u.hardWait(5);
		// patternUtil.keyAlt("j");
		// PASO 19
		addStep("Clic en el boton de Ejecutar.");
		patternUtil.clickEjecutar();
		u.hardWait(3);
		// PASO 20
		addStep("¿Ejecutar Otra Solicitud? No.");
		patternUtil.keyAlt("a");
		patternUtil.ejecutarOtraSolicitud("No");
		u.hardWait(3);
		// PASO 21
		addStep("Dirigirse al menu: Ver(x) -> 'Solicitudes' -> 'Encontrar'.");
		patternUtil.encontrarSolicitudes();
		u.hardWait(3);
		patternUtil.clickEncontrar();
		// PASO 22
		addStep("Dar clic en Ver Detalles");
		patternUtil.clickVisualizarDetalles();
		u.hardWait(3);
		patternUtil.clickAceptarDetalle();
		u.hardWait(3);
		// PASO 23
		addStep("Visualizar LOG.");
		patternUtil.keyAlt("z"); // Visualizar el Registro
		assertEquals(patternUtil.getTextDelNavegador("Concurrente", text), text);
		testCase.addBoldTextEvidenceCurrentStep("Validacion exitosa");
		// PASO 24
		addStep("Dar clic en ejecutar nueva solicitud");
		patternUtil.clickOracle();
		patternUtil.keyAlt("j");
		// PASO 25
		addStep("Ingresar en el campo Nombre 'XXFC - Reporte de Pedidos Con Reserva de Inventario' ");
		patternUtil.typeAndTab(campo1);
		u.hardWait(5);
		patternUtil.keyAlt("a");
		addStep("Clic en el boton de Ejecutar.");
		patternUtil.clickEjecutar();
		u.hardWait(3);
		// PASO 26
		addStep("¿Ejecutar Otra Solicitud? No.");
		patternUtil.ejecutarOtraSolicitud("No");
		u.hardWait(3);
		// PASO 27
		addStep("Dirigirse al menu: Ver(x) -> 'Solicitudes' -> 'Encontrar'.");
		patternUtil.encontrarSolicitudes();
		u.hardWait(3);
		patternUtil.clickEncontrar();
		// PASO 28
		addStep("Dar clic en Ver Detalles");
		patternUtil.clickVisualizarDetalles();
		u.hardWait(3);
		patternUtil.clickAceptarDetalle();
		u.hardWait(3);
		// PASO 29
		addStep("Dar clic en Visualizar Output");
		patternUtil.clickVisualizarOutput();
		u.hardWait(5);
		// PASO 30
		addStep("Dar clic en Save");
		patternUtil.clickSave();
		// PASO 31
		addStep("Abrir el archivo");
		u.hardWait(180);
		patternUtil.clickOpenFile();
		u.hardWait(3);
		patternUtil.keyAlt("y");
		u.hardWait(10);
		// PASO 32
		addStep("Validar que NO contenga información el archivo");
		patternUtil.noExisteInformacion();
		u.hardWait(3);
		// PASO 33
		addStep("Dar clic en ejecutar nueva solicitud");
		patternUtil.clickOracle();
		patternUtil.keyAlt("j");
		u.hardWait(3);
		// PASO 34
		addStep("Ingresar en el campo Nombre ' XXFC - Crear Reservas Inventario Fisico (Paso 2)' ");
		patternUtil.typeAndTab(campo3);
		u.hardWait(5);
		// PASO 35
		addStep("Ingresar parametro:" + "Respaldo a procesar: " + respaldo + "Aceptar.");
		patternUtil.typeAndTab(respaldo);
		patternUtil.keyAlt("a");
		// PASO 36
		addStep("Clic en el boton de Ejecutar.");
		patternUtil.clickEjecutar();
		// PASO 37
		addStep("¿Ejecutar Otra Solicitud? No.");
		patternUtil.ejecutarOtraSolicitud("No");
		// PASO 38
		addStep("Dirigirse al menu: Ver(x) -> 'Solicitudes' -> 'Encontrar'.");
		patternUtil.encontrarSolicitudes();
		patternUtil.clickEncontrar();
		// PASO 39
		addStep("Dar clic en Ver Detalles");
		patternUtil.clickVisualizarDetalles();
		u.hardWait(3);
		patternUtil.clickAceptarDetalle();
		u.hardWait(3);
		// PASO 40
		addStep("Dar clic en Visualizar Output");
		patternUtil.clickVisualizarOutput();
		u.hardWait(5);
		// PASO 41
		addStep("Dar clic en Save");
		patternUtil.clickSave();
		// PASO 42
		addStep("Abrir el archivo");
		u.hardWait(180);
		patternUtil.clickOpenFile();
		u.hardWait(3);
		patternUtil.keyAlt("y");
		u.hardWait(10);
		// PASO 43
		addStep("Validar que NO contenga información el archivo");
		patternUtil.noExisteInformacion();
		u.hardWait(3);
		// PASO 44
		addStep("Dar clic en ejecutar nueva solicitud");
		patternUtil.clickOracle();
		patternUtil.keyAlt("j");
		// PASO 45
		addStep("Ingresar en el campo Nombre 'XXFC - Reporte de Pedidos Con Reserva de Inventario' ");
		patternUtil.typeAndTab(campo1);
		u.hardWait(5);
		patternUtil.keyAlt("a");
		addStep("Clic en el boton de Ejecutar.");
		patternUtil.clickEjecutar();
		u.hardWait(3);
		// PASO 46
		addStep("¿Ejecutar Otra Solicitud? No.");
		patternUtil.ejecutarOtraSolicitud("No");
		u.hardWait(3);
		// PASO 47
		addStep("Dirigirse al menu: Ver(x) -> 'Solicitudes' -> 'Encontrar'.");
		patternUtil.encontrarSolicitudes();
		u.hardWait(3);
		patternUtil.clickEncontrar();
		// PASO 48
		addStep("Dar clic en Ver Detalles");
		patternUtil.clickVisualizarDetalles();
		u.hardWait(3);
		patternUtil.clickAceptarDetalle();
		u.hardWait(3);
		// PASO 49
		addStep("Dar clic en Visualizar Output");
		patternUtil.clickVisualizarOutput();
		u.hardWait(5);
		// PASO 50
		addStep("Dar clic en Save");
		patternUtil.clickSave();
		// PASO 51
		addStep("Abrir el archivo");
		u.hardWait(180);
		patternUtil.clickOpenFile();
		u.hardWait(3);
		patternUtil.keyAlt("y");
		u.hardWait(10);
		// PASO 52
		addStep("Validar que contenga información el archivo");
		patternUtil.existeInformacion();
		u.hardWait(3);
		// PASO 53
		addStep("Cerrar ventanas");
		patternUtil.closeWindow();
		u.hardWait(3);
		patternUtil.closeWindow();
		u.hardWait(5);
		patternUtil.ventanaConfirmarSalir("a");
		u.close();
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_005_ValidarConcurrenteEjecucionExitosa_test";
	}

	@Override
	public String setTestDescription() {
		return "Descripcion y Prerequisitos";
	}

	@Override
	public String setTestDesigner() {
		return "JessicaR@hexaware.com";
	}

	@Override
	public String setTestInstanceID() {
		return null;
	}

	@Override
	public String setPrerequisites() {
		return "Validar que al ejecutar el concurrente 'XXFC - Preparar Reservas Inventario Fisico (Paso 1)', dentro de la responsabilidad 'MXOXXO-WM17FAA-COORDINADOR EQP FRM', finalice sin errores. Acceso a la responsabilidad: 'MXOXXO-WM17FAA-COORDINADOR EQP FRM' Verificar que nuestro usuario cuente con permisos para ejecutar los concurrentes (Desarrollador de Aplicaciones -- Aplicación  Consultas -- Común -->Encontrar --> XXFC_RESERVA_INV_USUARIO)";

	}

	@Override
	public void beforeTest() {
		// TODO Auto-generated method stub

	}

}
