package EBS.nexxo.cho52084072;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import org.sikuli.script.Pattern;
import org.testng.annotations.Test;

import EBS.Sikuli.MetodosSikuli;
import EBS.Sikuli.PatternUtil;
import EBS.tools.LoginTool;
import EBS.util.pageObjectModel.EBSPaginaInicial;
import modelo.BaseExecution;
import utils.SikuliUtil;
import utils.password.PasswordUtil;
import utils.selenium.IETest;
import utils.selenium.SeleniumUtil;

public class ATC_FT_004_AplicarIncrementodeRentaReclasificacionCondosarrendadores extends BaseExecution {

	@Test(dataProvider = "data-provider")

	public void ATC_FT_004_AplicarIncrementodeRentaReclasificacionCondosarrendadores_test(HashMap<String, String> data)
			throws Exception {
		/*
		 * VARIABLES
		 */
		String ebsUrl = data.get("server");
		String userName = data.get("userName");
		String password = PasswordUtil.decryptPassword(data.get("password"));
		String responsabilidad = data.get("responsabilidad");
		String carpeta = data.get("carpeta");
		String fecha = data.get("fecha");
		String plaza = data.get("plaza");
		String metodoincremento = data.get("metodoIncremento");
		String arrendamiento = data.get("arrendamiento");
		String aplicarincrementos = data.get("aplicarIncrementos");
		String estadopago = data.get("estadopago");

		/*
		 * UTILERIAS
		 */

		SeleniumUtil u = new SeleniumUtil(new IETest(), true);
		LoginTool inicio = new LoginTool(u, testCase, ebsUrl, userName, password);
		EBSPaginaInicial paginaInicial = new EBSPaginaInicial(u, testCase);
		SikuliUtil sk = new SikuliUtil();
		String pathImg = sk.PatternPath("EBS\\"+"EBS");
		Pattern pathimg = new Pattern();
		ArrayList<String> ListImage = new ArrayList<String>();
		HashMap<String, Pattern> ImgPatter = new HashMap<String, Pattern>();
		ArrayList<Pattern> patterns = new ArrayList<Pattern>();
		File Fol = new File(pathImg);
		MetodosSikuli p = new MetodosSikuli(sk, u, pathimg, ListImage, ImgPatter, patterns, Fol);
		PatternUtil pu = new PatternUtil(sk, u, pathimg, ListImage, ImgPatter, patterns, Fol);

		/*
		 * PASOS DEL CASO DE PRUEBA, MATRIZ: CHO52084072
		 */

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
		addStep("Ingresar a la responsabilidad 'MXOXXO-APXXXXX-ADMON-PROVEEDORES_OPE_CAT_OS'");
		paginaInicial.clickOnResponsabilidad(responsabilidad);
		// PASO 6
		addStep("Seleccionar: Incrementos de Rentas -> Calculo de Incrementos de Rentas.");
		paginaInicial.clickOnCarpeta(carpeta);
		paginaInicial.clickCalculoIncrementosRenta();
		p.runApplet();
		p.fechaProceso(fecha, plaza, metodoincremento, arrendamiento, aplicarincrementos);
		p.ejecutar();
		p.otraSolicitud();
		// ------------------------------------
		// PASO 10
		pu.nTAB(9);
		pu.clickAbrir();
		pu.clickAbrir();
		// PASO 10
		addStep("Validamos que no se generan Cronogramas de Pago al Contrato Nuevo.");
		pu.closeWindowInsideApplet();
		pu.f11();
		u.hardWait(5);
		pu.nTAB(10);
		u.hardWait(3);
		pu.enterText(arrendamiento);
		u.hardWait(5);
		pu.ctrlF11();
		u.hardWait(3);
		pu.compararImagenEstadoPago(estadopago);
		pu.closeText();
		pu.closeWindowInsideApplet();
		addStep("Cerrar ventanas");
		u.hardWait(3);
		pu.closeWindow();
		addStep("Cerrar ventanas");
		pu.closeWindow();
		u.hardWait(3);
		pu.closeWindow();
		pu.closeWindow();
		u.hardWait(5);
		pu.ventanaConfirmarSalir("a");
		u.close();

	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_004_AplicarIncrementodeRentaReclasificacionCondosarrendadores_test";
	}

	@Override
	public String setTestDescription() {
		return "Validar sus Cronogramas de Pago Aprobados lleguen al Nuevo Contrato con estado de Retenido y los Provisionales lleguen correctos para contrato con un arrendador Pre-requisitos: Contar con acceso a la responsabilidad 'MXOXXO-PNXXXXX-Admon OS_ACT_FIJO_OS'. NOTA : ES NECESARIO ACTUALIZAR LOS DATOS EN CADA PRUEBA";
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
		return "Contar con acceso a la responsabilidad 'MXOXXO-PNXXXXX-Admon OS_ACT_FIJO_OS'.";
	}

	@Override
	public void beforeTest() {
		
	}

}