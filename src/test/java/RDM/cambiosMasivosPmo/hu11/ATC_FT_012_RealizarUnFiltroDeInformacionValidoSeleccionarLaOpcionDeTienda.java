package RDM.cambiosMasivosPmo.hu11;

import static org.testng.Assert.assertTrue;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import org.sikuli.script.Pattern;
import org.testng.annotations.Test;

import RDM.util.CambiosMasivos;
import modelo.BaseExecution; import RDM.util.GlobalVariables;
import utils.SikuliUtil;
import utils.selenium.IETest;
import utils.selenium.SeleniumUtil;

/**
 * DESCRIPCIÓN: Realizar una prueba que nos permita visualizar la informacion consultada de una Ruta en la ventana de Tienda"
 * 
 * NOMBRE DE LOS CASOS DE PRUEBA MANUAL QUE SE ABARCAN:
 *  - MTC_FT_012_RealizarUnFiltroDeInformacionValidoSeleccionarLaOpcionDeTienda
 *  
 * PRERREQUISITOS: Contar con la liga de acceso a RDM Contar con credenciales
 * para RDM Tener acceso a las tablas PMO Route Log y PMO Tienda Log
 *
 * Realizar una prueba que nos permita visualizar la informacion consultada de
 * una Ruta en la ventana de Tienda
 * 
 * @author ricardor4@hexaware.com
 */
public class ATC_FT_012_RealizarUnFiltroDeInformacionValidoSeleccionarLaOpcionDeTienda extends BaseExecution {

	@Test(dataProvider = "data-provider")
	public void ATC_FT_012_RealizarUnFiltroDeInformacionValidoSeleccionarLaOpcionDeTienda_test(
			HashMap<String, String> data) throws Exception {

		/*
		 * VARIABLES
		 */
		String plaza = data.get("plaza");
		String p = data.get("p");
		String diaGen = data.get("diaGen");
		String ruta = data.get("ruta");
		String fecha1 = data.get("fecha1");
		String fecha2 = data.get("fecha2");

		/*
		 * UTILERIAS
		 */
		SikuliUtil sk = new SikuliUtil();
		SeleniumUtil u = new SeleniumUtil(new IETest());
		String pathImg = sk.PatternPath("RDM\\"+"CambiosMasivos");
		Pattern pathimg = new Pattern();
		ArrayList<String> ListImage = new ArrayList<String>();
		HashMap<String, Pattern> ImgPatter = new HashMap<String, Pattern>();
		ArrayList<Pattern> patterns = new ArrayList<Pattern>();
		File Fol = new File(pathImg);
		CambiosMasivos c = new CambiosMasivos(sk, u, pathimg, ListImage, ImgPatter, patterns, Fol, testCase);

		/*
		 * PASOS DEL CASO DE PRUEBA
		 */

		// PASO 1
		addStep("Ingresar a la liga http://10.180.49.86:8071");
		System.out.println("PASO 1");
		// PASO 2
		addStep("Ingresar a RDM como Coordinador y/o Analista de Sistemas" + "Capturar:" + "- Usuario" + "- Contraseña"
				+ "Y dar clic al boton de Logon");
		System.out.println("PASO 2");
		assertTrue(c.logInCambiosMasivos(), "Error al loguear en pagina inicial");
		// PASO 3
		addStep("Dar clic al menu: Support Functions > Reports > XXFC-PMO_Historico_PMO");
		System.out.println("PASO 3");
		assertTrue(c.clickHistoricoPmo(), "Error al loguear en pagina inicial");
		// PASO 4
		addStep("Validar que se cuenten con los filtros:" + "- Plaza" + "- Tipo distribucion" + "- Día generacion"
				+ "- Día entrega" + "- Ruta" + "- Fecha"
				+ "Y validar que se encuentre el botón de Exportar Deshabilitado en caso de no haber informacion previamente cargada");
		System.out.println("PASO 4");
		assertTrue(c.ingresarPlazaFechas2(plaza, p, diaGen, ruta, fecha1, fecha2), "Error al loguear en pagina inicial");
		u.hardWait(20);
		// PASO 5
		addStep("Seleccionar el filtro  Plaza y buscar informacion");
		System.out.println("PASO 5");
		assertTrue(c.validarcheck2(), "");
		// PASO 6
		addStep("Dar clic al botón de Log Tienda");
		System.out.println("PASO 6");
		assertTrue(c.clickLogTienda(),"No se visualiza el boton LogTienda");
		c.click("ReporteExportarTienda");
		assertTrue(c.validarInfoLogTienda13(), "Informacion Validada");
		testCase.addBoldTextEvidenceCurrentStep("Validacion Exitosa");
		u.quit();
	}

	@Override
	public void beforeTest() {
	}

	@Override
	public String setPrerequisites() {
		return "1. Edge" + "2.-Tener acceso a la aplicación de RDM"
				+ "3.- Tener acceso a las tablas PMO Route Log y PMO Tienda Log";
	}

	@Override
	public String setTestDescription() {
		return "Realizar una prueba que nos permita visualizar la informacion consultada de una Ruta en la ventana de Tienda";
	}

	@Override
	public String setTestDesigner() {
		return "RicardoR4@hexaware.com";
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_012_RealizarUnFiltroDeInformacionValidoSeleccionarLaOpcionDeTienda_test";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}

}