package WM.EvolucionRetiroSinTarjeta.HU07;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import org.sikuli.script.Pattern;
import org.testng.annotations.Test;

import EBS.tools.LoginTool;
import EBS.util.appletsMethods.MetodosSikuli;
import EBS.util.pageObjectModel.EBSPaginaInicial;
import EBS.util.patternObject.PatternUtil;
import modelo.BaseExecution;
import utils.SikuliUtil;
import utils.password.PasswordUtil;
import utils.selenium.ChromeTest;
import utils.selenium.SeleniumUtil;

public class ATC_FT_05_Validar_que_la_carta_femsa_del_proceso_de_dispersion_este_generada_FTECH extends BaseExecution {

	@Test(dataProvider = "data-provider")
	public void ATC_FT_05_Validar_que_la_carta_femsa_del_proceso_de_dispersion_este_generada_test(HashMap<String, String> data)
			throws Exception {
		/**
		 * Plataforma de EBS http://fcrebsqa.femcom.net:8011/OA_HTML/AppsLocalLogin.jsp
		 * Desc: Validar que la carta femsa del proceso de dispersion este generada correctamente. Debera generar un archivo Excel donde debe contener los retiros + comisiones.
		 * @author Mario Garza
		 * @date 22/02/2024
		 **/
		
		/*
		 * VARIABLES
		 */
		String ebsUrl = data.get("server"); 
		String userName = data.get("userName");
		//PasswordUtil.generateEncryptPass();
		String password = PasswordUtil.decryptPassword(data.get("password"));
		String responsabilidad = data.get("responsabilidad");
		String ebsPattern = data.get("ebsPattern");
		String carpetaJournal = data.get("carpeta1");
		String linkJournal = data.get("carpeta2"); 
		String origen = data.get("origen");
		String categoria = data.get("categoria"); 
		String periodo = data.get("periodo");
		String tipo = data.get("tipo"); 
		String plaza = data.get("plaza"); 
		String origen1 = data.get("origen1");
		String finalizado = data.get("finalizado");
		

		/*
		 * UTILERIAS
		 */
		SeleniumUtil u = new SeleniumUtil(new ChromeTest(), true);
		LoginTool inicio = new LoginTool(u, testCase, ebsUrl, userName, password);
		EBSPaginaInicial paginaInicial = new EBSPaginaInicial(u, testCase);
		SikuliUtil sk = new SikuliUtil();
		String pathImg = sk.PatternPath("EBS\\"+ebsPattern);
		Pattern pathimg = new Pattern();
		ArrayList<String> ListImage = new ArrayList<String>();
		HashMap<String, Pattern> ImgPatter = new HashMap<String, Pattern>();
		ArrayList<Pattern> patterns = new ArrayList<Pattern>();
		File Fol = new File(pathImg);
		File Folder = new File(pathImg);

		MetodosSikuli p = new MetodosSikuli(sk, u, pathimg, ListImage, ImgPatter, patterns, Fol);

		PatternUtil patternUtil = new PatternUtil(sk, u, pathimg, ListImage, ImgPatter, patterns, Folder);

		/*
		 * PASOS DEL CASO DE PRUEBA, MATRIZ: Proceso de dispersion de RST
		 * Llamada de Metodos, clases y objetos
		 */
		
        // PASO 1
		addStep("Ingresar al ambiente de EBS.");
		logger.info("EBS URL: " + ebsUrl);
		System.out.println("EBS URL: " + ebsUrl);
		logger.info("User Name: " + userName);
		System.out.println("User Name: " + userName);
		addStep("Seleccionar idioma: Español Latinoamericano.");
		// PASO 2
		addStep("Ingresar usuario y contraseña.");
		inicio.login();
		addStep("Dar clic en el boton de 'Conectese'.");
		testCase.addScreenShotCurrentStep("Login");
		// PASO 3
		addStep("Ingresar a la responsabilidad 'GLXXXXX-CARTAS-FEMSA'");
		paginaInicial.clickOnResponsabilidad(responsabilidad);
		testCase.addScreenShotCurrentStep("Responsabilidar");
		addStep("Seleccionar: GLXXXXX-CARTAS-FEMSA -> Journal");
		paginaInicial.clickOnCarpeta(carpetaJournal);
		testCase.addScreenShotCurrentStep("GLXXXXX-CARTAS-FEMSA");
		//paginaInicial.clickOnCarpeta(solicitar);
		addStep("Seleccionar: GLXXXXX-CARTAS-FEMSA -> Journal");
		paginaInicial.clickOnCarpeta(linkJournal);
		testCase.addScreenShotCurrentStep("linkJournal");
	    //paginaInicial.clickStandard17admServicios();
		u.hardWait(8);
		//PASO 4
		addStep("Descargar el ejecutable java frmServlet y se ejecuta");
		patternUtil.frmServlet();
		testCase.addScreenShotCurrentStep("frmServlet");
		u.hardWait(5);
		patternUtil.runApplet();
		//patternUtil.solicitudUnica();
		u.hardWait(5);
		addStep("Selecciona Origen y Categoria y escribe los datos corresponidentes");
		patternUtil.escribirOrigenCategoria(origen, categoria);
		testCase.addScreenShotCurrentStep("Selecciona Origen y Categoria");
		// PASO 6
		addStep("Selecciona los 3 puntitos para buscar la fecha periodo ");
		patternUtil.seleccionaPeriodo(periodo);
		testCase.addScreenShotCurrentStep("Fecha periodo");
		u.hardWait(5);
		// PASO 9 y 10
		u.hardWait(5);
		addStep("Ver solicitudes Ver(x)");
		patternUtil.encontrarSolicitudes();
		testCase.addScreenShotCurrentStep("Encontrar solicitudes");
		u.hardWait(5);
		addStep("Ejecutar nueva solicitud");
		patternUtil.clickEjecutarNuevaSolicitud();
		testCase.addScreenShotCurrentStep("Ejecutar nueva solicitud");
		// PASO 14
		addStep("Selecciona los 3 puntitos y busca el concurrente XXFC - Reporte de Conciliacion FEMCO Cartas" );
		patternUtil.typeAndBuscarReporteConciliacion(tipo);
		testCase.addScreenShotCurrentStep("Tipo de concurrente");
		// PASO 12
		addStep("Selecciona los 3 puntitos y se llenan los siguientes campos" );
		patternUtil.typeAndBuscarCompaña();
		addStep("Selecciona Plaza" );
		patternUtil.typeAndBuscarPlaza(plaza);
		addStep("Selecciona origen" );
		patternUtil.typeAndBuscarOrigen(origen1);
		addStep("Selecciona Cuenta Corriente");
		patternUtil.typeAndBuscarCuentaCorriente();
		addStep("Selecciona Tipo de Reporte" );
		patternUtil.typeAndBuscarTipoReporte();
		addStep("Selecciona Tipo de Salida" );
		patternUtil.typeAndBuscarTipoSalida();
		addStep("Selecciona periodo" );
		patternUtil.typeAndBuscarPeriodo(periodo);
		testCase.addScreenShotCurrentStep("Se llenan los campos Compañia plaza origen cuanta_corriente tipo_reporte tipo salida y perdiodo");
		u.hardWait(10);
		addStep("Selecciona Ejecutar");
		patternUtil.clickEjecutarSolicitud();
		u.hardWait(5);
		patternUtil.ejecutarOtraSolicitud("No");
		testCase.addScreenShotCurrentStep("Ejecutar solicitud");
		addStep("Da click en Encontrar");
		patternUtil.clickEncontrarSo();
		u.hardWait(10);
		testCase.addScreenShotCurrentStep("Refrescar");
		patternUtil.clickEnoctrarYRefrescarDatos();
		addStep("Vizualizar Outpút");
		patternUtil.clickVisualizarOutput();
		testCase.addScreenShotCurrentStep("Visualiza la salida");
		addStep("Descargar el ejecutable java frmServlet y se ejecuta");
		patternUtil.frmservletReporteConciliacion();
		testCase.addScreenShotCurrentStep("Se descarga el archivo y se valida");
		u.hardWait(5);
		addStep("Se visualiza el Detalle");
		patternUtil.reporteConciliacion();
		testCase.addScreenShotCurrentStep("Se visualiza el Detalle");
        // PASO 14 ------Pendiente de realizar 
		patternUtil.clickAceptarParametros();
		patternUtil.closeWindow();
		//patternUtil.ventanaConfirmarSalir("a");
		u.close();

	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_05_Validar_que_la_carta_femsa_del_proceso_de_dispersion_este_generada_correctamente_Debera_generar_un_archivo_Excel_donde_debe_contener_los_retiros_comisiones";
	}

	@Override
	public String setTestDescription() {
		return "Validar que la carta femsa del proceso de dispersion este generada correctamente. Debera generar un archivo Excel donde debe contener los retiros + comisiones. ";
	}

	@Override
	public String setTestDesigner() {
		return "Mario.garza@grupotimexico.com";
	}

	@Override
	public String setTestInstanceID() {
		return null;
	}

	@Override
	public String setPrerequisites() {
		return "Tener la responsabilidad 'GLXXXXX-CARTAS FEMSA'. ";
	}

	@Override
	public void beforeTest() {
		// TODO Auto-generated method stub

	}
}
