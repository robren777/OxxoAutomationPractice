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


public class ATC_FT_005_Administrador_ReportedeEncuestasAplicadas_ConsultarAvancedeEncuestasAplicadas extends BaseExecution {

	SeleniumUtil u;
	
	@Test(dataProvider = "data-provider")
	public void ATC_FT_005_Administrador_ReportedeEncuestasAplicadas_ConsultarAvancedeEncuestasAplicadas_test(HashMap<String, String> data) throws Exception {

		/*
		 * Utilerias
		 *********************************************************************/
		

		/*
		 * Variables
		 ******************************************************************************************/
		
		
		String user = data.get("user");
		String pass = data.get("password");
		String nombreEncuesta = data.get("nombre_encuesta");
		String version = data.get("version");
		String plaza = data.get("plaza");
		String tienda = data.get("tienda");
		String usuarioEnc = data.get("usuario");
		String fecha = "04/2023";//data.get("fecha");
		
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
		
		addStep("Seleccionar la opcion 'Reportes de Encuestas' ");
		
		mtto.ClickReportEncuesta();
		
		assertTrue(mtto.IsConsultaEncuestasProgramadas());
		
		/**
		 *************************** PASO 5 **********************************
		 *********************************************************************
		 **/
		
		addStep("Informar los siguientes campos y dar clic en el boton 'Buscar' ");
		
		mtto.FillBusquedaEncuestaProgramada(nombreEncuesta, version, plaza, tienda, usuarioEnc, fecha);
		
		testCase.addScreenShotCurrentStep(u, "EncuestaProgramadaFiltros");
		
		mtto.ClickBuscar();
		
		testCase.addScreenShotCurrentStep(u, "EncuestaProgResult");
		
		/**
		 *************************** PASO 6 **********************************
		 *********************************************************************
		 **/
		
		addStep("Hacer click en 'PREGUNTAS' ");
		
		mtto.ClickPreguntas();
		
		testCase.addScreenShotCurrentStep(u, "PreguntasEncuestaProgramada");
		
	}

	@Override
	public void beforeTest() {
	}

	@Override
	public String setTestDescription() {
		return " Validar que el sistema podr� consultar el progreso (% de avance) de las encuestas aplicadas"
				+ " y ver las im�genes que el usuario asocie a las respuestas de cada pregunta.";
	}

	@Override
	public String setTestDesigner() {
		return "Jose Onofre";
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_005_Administrador_ReportedeEncuestasAplicadas_ConsultarAvancedeEncuestasAplicadas_test";
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


