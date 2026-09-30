package EBS.cajasInteligentes.cho52071605;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import org.sikuli.script.Pattern;
import org.testng.annotations.Test;

import EBS.tools.LoginTool;
import EBS.util.appletsMethods.MetodosSikuli;
import EBS.util.pageObjectModel.EBSPaginaInicial;
import modelo.BaseExecution;
import utils.SikuliUtil;
import utils.password.PasswordUtil;
import utils.selenium.IETest;
import utils.selenium.SeleniumUtil;

public class MTCFT002_ValidarTxtGeneradoMedianteXXFC_EAM extends BaseExecution {

	@Test(dataProvider = "data-provider")
	public void ATC_FT_002_ValidarTxtGeneradoMedianteXXFC_EAM(HashMap<String, String> data) throws Exception {

		/*
		 * VARIABLES
		 */
		String ebsUrl = data.get("server");
		String userName = data.get("userName");
		String password = PasswordUtil.decryptPassword(data.get("password"));
		String responsabilidad = data.get("responsabilidad");
		String carpeta = data.get("carpeta");
		String campo = data.get("campo");
		String nombreArchivo = data.get("nombreArchivo");
		String nombremitad = data.get("nombremitad");

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
		 * PASOS DEL CASO DE PRUEBA
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
		addStep("Dar clic en el botón de 'Conectese'.");
		inicio.login();

		// PASO 5
		addStep("Ingresar a la responsabilidad 'MXOXXO-EAM10VCZ-EQUIPO-MANTO'.");
		paginaInicial.clickOnResponsabilidad(responsabilidad);

		// PASO 6
		addStep("Seleccionar: Pedidos de Trabajo -> Pedidos de Trabajo -> Ver(x) -> Solicitudes -> Ejecutar Nueva Solicitud.");
		paginaInicial.clickOnCarpeta(carpeta);
		paginaInicial.pedidosTrabajoElement.click();
		p.vers();
		p.clickNuevaSolicitud();

		// PASO 7
		addStep("Ingresar en el campo Nombre: 'XXFC EAM - Validacion de Carga Automatica de Factura Prestador de Servicio', Tab.");
		p.ingresarCampo(campo);

		// PASO 8
		addStep("Capturar el parametro: Nombre Archivo: 20210209110712.txt Aceptar.");
		p.buscarNombreSolicitud(nombremitad);
		p.ingresarNombreArchivo(nombreArchivo);

		// PASO 9
		addStep("Dar clic en Ejecutar.");
		p.ejecutar();

		// PASO 10
		addStep("¿Ejecutar otra solicitud? No.");
		p.otraSolicitud();
		u.hardWait(3);

		// PASO 11
		addStep("Dar clic en Encontrar.");
		p.encontrar();
	}

	@Override
	public void beforeTest() {
		// TODO Auto-generated method stub

	}

	@Override
	public String setPrerequisites() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String setTestDescription() {
		// TODO Auto-generated method stub
		return "Validar el archivo generado y que la factura se muestre como SERIEFOLIO sin caracteres especiales";
	}

	@Override
	public String setTestDesigner() {
		// TODO Auto-generated method stub
		return "edwin.ramirez@neoris.com";
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_002_ValidarTxtGeneradoMedianteXXFC_EAM";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}
}
