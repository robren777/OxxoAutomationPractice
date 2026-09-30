package RDM.cambiosMasivosPmo.hu11;

import static org.testng.Assert.assertFalse;
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
import utils.sql.SQLResult;

/**
 * DESCRIPCIÓN: Realizar una prueba que nos permita visualizar la informacion consultada de una Ruta en la ventana de Tienda y tambien nos permita exporta l ainformacion a un archivo PDF
 * 
 * NOMBRE DE LOS CASOS DE PRUEBA MANUAL QUE SE ABARCAN:
 *  - MTC_FT_013_ExportarLaInformacionDeLogTienda
 *  
 * PRERREQUISITOS: Contar con la liga de acceso a RDM Contar con credenciales
 * para RDM Tener acceso a las tablas PMO Route Log y PMO Tienda Log
 *
 * Realizar una prueba que nos permita visualizar la informacion consultada de
 * una Ruta en la ventana de Tienda y tambien nos permita exporta l ainformacion
 * a un archivo PDF
 * 
 * @author ricardor4@hexaware.com
 */
public class ATC_FT_013_ExportarLaInformacionDeLogTienda extends BaseExecution {

	@Test(dataProvider = "data-provider")
	public void ATC_FT_013_ExportarLaInformacionDeLogTienda_test(HashMap<String, String> data) throws Exception {

		/*
		 * VARIABLES
		 */
		String plaza = data.get("plaza");
		String p = data.get("p");
		String diaGen = data.get("diaGen");
		String ruta = data.get("ruta");
		String fecha1 = data.get("fecha1");
		String fecha2 = data.get("fecha2");
		String consulta1 = "select ROUTE,DIA_GEN_PED,LOAD_SEQUENCE,DIA_INI_VENT_ENTREGA AS DIA_INI_VENT_ENT,H_INI_VENT_ENTREGA AS H_INI_VENT_ENT from RDM100.XXFC_PMO_TIENDA_LOG WHERE TIPO_DISTRO = '"+ p +"' AND ROUTE = '"+ ruta +"' AND FECHA_RESPALDO BETWEEN TO_DATE('"+ fecha1 +"', 'DD/MM/YYYY') AND TO_DATE('"+ fecha2 +"', 'DD/MM/YYYY') AND DIA_GEN_PED = "+ diaGen +"";

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
		utils.sql.SQLUtil RDM = new utils.sql.SQLUtil(GlobalVariables.DB_HOST_FCRD71CU,
				GlobalVariables.DB_USER_FCRD71CU, GlobalVariables.DB_PASSWORD_FCRD71CU);

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
		assertTrue(c.clickLogTienda(), "No se visualiza el boton LogTienda");
		// PASO 7
		addStep("Dar clic al boton Export Tienda");
		System.out.println("PASO 7");
		c.click("ReporteExportarTienda");
		testCase.addScreenShotCurrentStep(" ");
		u.hardWait(10);
		assertTrue(c.validarReporte13PMO(), "Informacion Validada");
		// PASO 8
		addStep("Validar que se descargue el archivo PDF corresponda y verificar la con la información de la tabla");
		System.out.println("PASO 8");
		System.out.println(consulta1);
		SQLResult c1 = RDM.executeQuery(consulta1);
		assertFalse(executeQuery(RDM, consulta1).isEmpty());
		testCase.addQueryEvidenceCurrentStep(c1);
		u.hardWait(4);
		RDM.close();
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
		return "Realizar una prueba que nos permita visualizar la informacion consultada de una Ruta en la ventana de Tienda y tambien nos permita exporta l ainformacion a un archivo PDF";
	}

	@Override
	public String setTestDesigner() {
		return "RicardoR4@hexaware.com";
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_013_ExportarLaInformacionDeLogTienda_test";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}

}