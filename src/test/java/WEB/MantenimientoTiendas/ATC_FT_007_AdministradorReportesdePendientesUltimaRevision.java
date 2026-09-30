package WEB.MantenimientoTiendas;

import static org.testng.Assert.assertTrue;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import org.sikuli.script.Pattern;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import WEB.util.AccessControl;
import WEB.util.AuxMethods;
import WEB.util.SikuliMethodsUtil;
import WEB.util.MantenimientoTiendas.MantenimientoTiendas;
import modelo.BaseExecution;
import utils.SikuliUtil;
import utils.selenium.MEdgeTest;
import utils.selenium.SeleniumUtil;


public class ATC_FT_007_AdministradorReportesdePendientesUltimaRevision extends BaseExecution {

	SeleniumUtil u;
	
	@Test(dataProvider = "data-provider")
	public void ATC_FT_007_AdministradorReportesdePendientesUltimaRevision_test(HashMap<String, String> data) throws Exception {

		/*
		 * Utilerias
		 *********************************************************************/
		

		/*
		 * Variables
		 ******************************************************************************************/
		
		
		String user = data.get("user");
		String pass = data.get("password");
		String nombreEncuesta = data.get("nombre_encuesta");
		String plaza = data.get("plaza");
		String version = data.get("version");
		String Pattern = data.get("Pattern");
		
		
		u = new SeleniumUtil(new MEdgeTest(), true);
		AccessControl inicio = new AccessControl(u, testCase);
		AuxMethods aux = new AuxMethods(u, testCase);
		MantenimientoTiendas mtto = new MantenimientoTiendas(u, testCase, aux);
		//ExcelUtil ex = new ExcelUtil("C:\\Users\\1000068937\\Downloads\\Re_check list infraestructura_10VCZ_null_42023.xls");

		HashMap<String, Pattern> ImgPattern = new HashMap<String, Pattern>();
		ArrayList<Pattern> patterns = new ArrayList<Pattern>();
		ArrayList<String> ListImage = new ArrayList<String>();
		Pattern pathimg = new Pattern();
		
		
		SikuliUtil sk = new SikuliUtil();
		String pathImg = sk.PatternPath("WEB\\"+Pattern);
		File Folder = new File(pathImg);
		SikuliMethodsUtil skli = new SikuliMethodsUtil(sk, u, pathimg, ListImage, ImgPattern, patterns, Folder, testCase);
	
		testCase.setTest_Description(data.get("id") + data.get("description"));
		
		testCase.setPrerequisites(data.get("prerequisites"));
		

		
		/**
		 *************************** PASO 1 **********************************
		 *********************************************************************
		 **/
		
		addStep("Ingresar a la liga 'https://fcowlq08.femcom.net/AccessControl/pages/login_form.jsf' ");

		/**
		 * Inicio del flujo
		 */
		aux.logP("1. Ingresar a Access Control");
		inicio.loginAccessControl(user, pass);
		
		testCase.addTextEvidenceCurrentStep("Se accedio correctamente al Portal de Access Control");
		
		
		/**
		 *************************** PASO 2 **********************************
		 *********************************************************************
		 **/
		
		addStep("Dar clic en el portal de 'Checklist de Verificacion y de Recepcion'");

		aux.logS("2. Scroll hasta el apartado de 'Expansion'");
		aux.logP("3. Click en el apartado de 'Checklist de Verificacion y de Recepcion'");
		mtto.ClickChklistVerifRecept();

		assertTrue(mtto.IsChklstVerifYRecep());
		

		/**
		 *************************** PASO 3 **********************************
		 *********************************************************************
		 **/
		
		addStep("Del menu principal posicionarse sobre la opcion 'Reporte de Pendientes'");
		
		mtto.SelectReportesPendientes();
	

		/**
		 *************************** PASO 4 **********************************
		 *********************************************************************
		 **/
		
		addStep("Seleccionar la opci�n 'Ultima Revision' ");
		
		mtto.ClickUltimaRevision();
		
		
		/**
		 *************************** PASO 5 **********************************
		 *********************************************************************
		 **/
		
		addStep("Llenar los campos y dar clic en el boton Buscar.");
		
		mtto.BusquedaPendienteUltimaRevision(nombreEncuesta, version, plaza);
		
		testCase.addScreenShotCurrentStep(u, "BusquedaPend");
		
		
		/**
		 *************************** PASO 6 **********************************
		 *********************************************************************
		 **/
		
		addStep("Dar clic al boton Excel y revisar que se despliegue correcto el reporte en Excel.");
		
		mtto.ClickExcelIconPendientes();
		
		skli.clickOpenFileXls();
		u.hardWait(20);
		skli.CloseCurrentWindow();
		
		testCase.addTextEvidenceCurrentStep("Se abre y se muestra el contenido del archivo de Excel");
		
		
		/**
		 *************************** PASO 7 **********************************
		 *********************************************************************
		 **/
		
		addStep("Dar click al boton Regresar");
		
		mtto.ClickIconRegresar();
		
		testCase.addTextEvidenceCurrentStep("Se Retorna a la pagina de 'Reporte de Pendientes de Ultima Revision' ");
		
		testCase.addScreenShotCurrentStep(u, "Final");
		
	}
	

	@Override
	public void beforeTest() {
	}

	@Override
	public String setTestDescription() {
		return "Validar la funcionalidad del apartado de Ultima Revision";
	}

	@Override
	public String setTestDesigner() {
		return "Jose Onofre";
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_007_AdministradorReportesdePendientesUltimaRevision_test";
	}

	@Override
	public String setTestInstanceID() {
		return null;
	}

	@Override
	public String setPrerequisites() {
		return "Tener acceso al porta de Checklist de Verificacion y de Recepcion";
	}

	@AfterMethod
	public void afterMethod() {
		u.getDriver().quit();
	}
	
}


