package WEB.MantenimientoTiendas;

import static org.testng.Assert.assertTrue;

import java.util.HashMap;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import WEB.util.AccessControl;
import WEB.util.AuxMethods;
import WEB.util.MantenimientoTiendas.MantenimientoTiendas;
import modelo.BaseExecution;
import utils.selenium.MEdgeTest;
import utils.selenium.SeleniumUtil;


public class ATC_FT_006_AdministradorGeneracionReporteExcel_Detalle_de_EncuestasaPlazas_Excel extends BaseExecution {

	SeleniumUtil u;
	
	@Test(dataProvider = "data-provider")
	public void ATC_FT_006_AdministradorGeneracionReporteExcel_Detalle_de_EncuestasaPlazas_Excel_test(HashMap<String, String> data) throws Exception {

		/*
		 * Utilerias
		 *********************************************************************/
		

		/*
		 * Variables
		 ******************************************************************************************/
		
		
		String user = data.get("user");
		String pass = data.get("password");
		String zona = data.get("zonas");
		String plaza = data.get("plaza");
		String fecha_inicial = "04/2023";
		String fecha_final = "04/2023";
		
		u = new SeleniumUtil(new MEdgeTest(), true);
		AccessControl inicio = new AccessControl(u, testCase);
		AuxMethods aux = new AuxMethods(u, testCase);
		MantenimientoTiendas mtto = new MantenimientoTiendas(u, testCase, aux);

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
		
		addStep("Del menu principal posicionarse sobre la opcion 'Reportes'");
		
		mtto.SelectReportes();
		
		
		/**
		 *************************** PASO 4 **********************************
		 *********************************************************************
		 **/
		
		addStep("Seleccionar la opcion 'Reporte Encuesta a Plazas' ");
		
		mtto.ClickREncuestaPlaza();
		
		
		/**
		 *************************** PASO 5 **********************************
		 *********************************************************************
		 **/
		
		addStep("Seleccionar el usuario de los que se quiera generar el reporte ");
		
		mtto.fillConsultaEncuestaProgramPlazas(zona, plaza, fecha_inicial, fecha_final);
		
		testCase.addScreenShotCurrentStep(u, "Filters");
		
		
		/**
		 *************************** PASO 6 **********************************
		 *********************************************************************
		 **/
		
		addStep("Dar click en el boton 'Buscar' ");
		
		mtto.ClickBuscar();
		
		testCase.addScreenShotCurrentStep(u, "Results");
		
		
		/**
		 *************************** PASO 7 **********************************
		 *********************************************************************
		 **/
		
		addStep("Se da clic en el hiperv�nculo 'VER' que aparece en la columna de CHECKLIST");
		
		mtto.ClickCheckListVER();
		
		testCase.addScreenShotCurrentStep(u, "Checklist de Verificacion");
		
		
		
		/**
		 *************************** PASO 8 **********************************
		 *********************************************************************
		 **/
		
		addStep("Se da clic en el icono de Excel , se muestra una ventana emergente "
				+ "para poder descargar el reporte ");
		
		
		mtto.ClickExcelIcon();
		
		testCase.addScreenShotCurrentStep(u, "DownloadXLS");
		
		
		
	}

	@Override
	public void beforeTest() {
	}

	@Override
	public String setTestDescription() {
		return "El usuario sera capaz de visualizar el detalle por registro y de este mismo generar un reporte en Excel";
	}

	@Override
	public String setTestDesigner() {
		return "Jose Onofre";
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_006_AdministradorGeneraci�nReporteExcel_Detalle_de_EncuestasaPlazas_Excel_test";
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


