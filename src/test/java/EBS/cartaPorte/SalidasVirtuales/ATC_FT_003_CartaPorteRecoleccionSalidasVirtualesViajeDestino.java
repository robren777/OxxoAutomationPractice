package EBS.cartaPorte.SalidasVirtuales;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import org.sikuli.script.Key;
import org.sikuli.script.Pattern;
import org.testng.annotations.Test;

import EBS.tools.LoginTool;
import EBS.util.GlobalVariables;
import EBS.util.appletsMethods.MetodosSikuli;
import EBS.util.pageObjectModel.EBSPaginaInicial;
import modelo.BaseExecution;
import utils.SikuliUtil;
import utils.selenium.IETest;
import utils.selenium.SeleniumUtil;

public class ATC_FT_003_CartaPorteRecoleccionSalidasVirtualesViajeDestino extends BaseExecution {

	@Test(dataProvider = "data-provider")

	public void ATC_FT_003_CartaPorteRecoleccionSalidasVirtualesViajeDestino_Test(HashMap<String, String> data) throws Exception {
		/*
		 * VARIABLES
		 */
		String ebsUrl = data.get("server");
		String userName = GlobalVariables.getRpalaciosNeoEbsUsr;
		String password = GlobalVariables.getRpalaciosNeoEbsPwd;
		String responsabilidad = data.get("responsabilidad");
		String recurso = data.get("recurso");
		String noPedido = data.get("nopedido");
		String ubicacionOrigen = data.get("ubicacionorigen");
		String horaSalida = data.get("horasalida");
		String horaLlegada = data.get("horallegada");
		String distanciaRecorrida = data.get("distanciarecorrida");
		String email = data.get("email");
		
		/*
		 * UTILERIAS
		 */
		SeleniumUtil u = new SeleniumUtil(new IETest(), true);
		LoginTool inicio = new LoginTool(u, testCase, ebsUrl, userName, password);
		EBSPaginaInicial paginaInicial = new EBSPaginaInicial(u, testCase);
		SikuliUtil sk = new SikuliUtil();
		String pathImg = sk.PatternPath("EBS\\EBS");
		Pattern pathimg = new Pattern();
		ArrayList<String> ListImage = new ArrayList<String>();
		HashMap<String, Pattern> ImgPatter = new HashMap<String, Pattern>();
		ArrayList<Pattern> patterns = new ArrayList<Pattern>();
		File Fol = new File(pathImg);
		MetodosSikuli p = new MetodosSikuli(sk, u, pathimg, ListImage, ImgPatter, patterns, Fol);

		/*
		 * PASOS DEL CASO DE PRUEBA, MATRIZ: SalidasVirtuales
		 */

		// PASO 1
		addStep("Ingresar a EBS AVEBQA a la siguiente ruta: http://fcrebsqa.femcom.net:8011");
		logger.info("EBS URL: " + ebsUrl);
		System.out.println("EBS URL: " + ebsUrl);
		logger.info("User Name: " + userName);
		System.out.println("User Name: " + userName);
		inicio.login();
		// PASO 2
		addStep("Ingresar a la Responsabilidad MXOXXO-WM17FAA-COORDINADOR EQP FRM.");
		paginaInicial.clickOnResponsabilidad(responsabilidad);
		paginaInicial.clickStockLocators();
		// PASO 3
		addStep("Ir a solicitudes y buscar concurrente: XXFC-Carta Porte Recolección Salidas Virtuales (ingesar datos y ejecutar)");
		p.runApplet();
		u.hardWait(3);
		sk.screen.keyDown(Key.F4);
		sk.screen.keyUp(Key.F4);
		// PASO 4
		addStep("Ejecucion del concurrente:Ver > Solicitudes > Ejecutar una nueva Solicitud > Solicitud Unica > Aceptar > XXFC - Actualización Cr Retek");
		testCase.addScreenShotCurrentStep("Ejecucion del concurrente:Ver > Solicitudes > Ejecutar una nueva Solicitud > Solicitud Unica > Aceptar > XXFC - Actualización Cr Retek");
		p.vers();
		p.clickNuevaSolicitud();
		p.buscarArchivoTab(recurso);
		u.hardWait(2);
		p.buscarArchivoTab(noPedido);
		p.nTAB(1);
		p.buscarArchivoTab(ubicacionOrigen);
		p.buscarArchivoTab(horaSalida);
		p.buscarArchivoTab(horaLlegada);
		p.buscarArchivoTab(distanciaRecorrida);
		p.nTAB(1);
		p.buscarArchivoTab(email);
		p.nTAB(1);
		sk.screen.keyDown(Key.ENTER);
		sk.screen.keyUp(Key.ENTER);
		p.vers();
		p.encontrar();
		p.visualizarOutput();
		sk.screen.keyDown(Key.ALT);
		sk.screen.keyDown(Key.F4);
		sk.screen.keyUp(Key.F4);
		sk.screen.keyUp(Key.ALT);
		sk.screen.keyDown(Key.ENTER);
		sk.screen.keyUp(Key.ENTER);
		testCase.addBoldTextEvidenceCurrentStep("Validacion exitosa");
		u.close();

	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_003_CartaPorteRecoleccionSalidasVirtualesViajeDestino_Test";
	}

	@Override
	public String setTestDescription() {
		return "Procesar un Pedido con Viaje y un Destino"
				+ "Nota: Algunos de los parametros que solicita el recurso no se encuentran disponibles";
	}

	@Override
	public String setTestDesigner() {
		return "ricardo.palacios@neoris.com";
	}

	@Override
	public String setTestInstanceID() {
		return null;
	}

	@Override
	public String setPrerequisites() {
		return "";
	}

	@Override
	public void beforeTest() {
		// TODO Auto-generated method stub

	}

}