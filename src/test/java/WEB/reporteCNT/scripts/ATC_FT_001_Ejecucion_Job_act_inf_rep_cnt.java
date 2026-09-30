package WEB.reporteCNT.scripts;


import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import WEB.om.MetodosExtra;
import WEB.om.PortalApps;
import WEB.reporteCNT.om.Consulta_Datos_CNT;
import WEB.reporteCNT.om.MenuPrincipal_ReportesCNT;
import WEB.util.GlobalVariables;
import modelo.BaseExecution;
import utils.controlm.ControlM;
import utils.controlm.pageObject.Control_mInicio;
import utils.selenium.MEdgeTest;
import utils.selenium.SeleniumUtil;
/**Desc: Se validara que al ejecutar el job act_inf_rep_cnt  finalice con estatus OK  y no se interrumpa la disponibilidad del portal durante su ejecuci�n
 * MTC_FT_001 Ejecuci�n de job act_inf_rep_cnt
 * Nota. Se debe solicitar el job 'act_inf_rep_cnt' antes de su ejecuci�n
 * @author Andrea Contreras
 * @date Noviembre 2022
 */
public class ATC_FT_001_Ejecucion_Job_act_inf_rep_cnt extends BaseExecution {

	SeleniumUtil u;

	@Test(dataProvider = "data-provider")

	public void  ATC_FT_001_Ejecucion_Job_act_inf_rep_cnt_test(HashMap<String, String> data) throws Exception {

		/*
		 * Variables
		 ******************************************************************************************/
		String user = GlobalVariables.USER_PORTAL_APPS_6006020;
		String password = GlobalVariables.PASSWORD_PORTAL_APPS_6006020;
		String url = GlobalVariables.URL_PORTAL_APPS;
		String entidad = data.get("Entidad");
        String tipoInfo = data.get("Tipo_Informacion");
		
		JSONArray config = new JSONArray(data.get("Config"));
		JSONObject obj = new JSONObject(data.get("Job"));
		u = new SeleniumUtil(new MEdgeTest(), true);
		MetodosExtra me = new MetodosExtra(u, testCase);

		/*
		 *                                             PASO 2
		 ******************************************************************************************/
		addStep("Ingresar a Control-M con credenciales válidas ");
		
		Control_mInicio cm = new Control_mInicio(u, GlobalVariables.USER_CTRL_M, GlobalVariables.PASSWORD_CTRL_M);
		u.get(GlobalVariables.URL_CTRL_M);
		u.getDriver().manage().window().maximize();		
		u.hardWait(40);
		u.waitForLoadPage();
		testCase.addScreenShotCurrentStep(u, "ctrl_m1");
		cm.logOn();
		testCase.addScreenShotCurrentStep(u, "ctrl_m2");
		
		/*
		 *                                             PASO 2
		 ******************************************************************************************/
		addStep("Ejecutar el job act_inf_rep_cnt");
		
		//Buscar del job
		ControlM control = new ControlM(u, testCase, obj); 
		boolean flag = control.searchJob(); 

		
		assertFalse(!flag, "No se encontro el job");
				
		//Ejecucion		
		String resultado = control.executeJob();
		
		testCase.addBoldTextEvidenceCurrentStep("Estatus de la ejecución del job:");
		testCase.addTextEvidenceCurrentStep( resultado);
		System.out.println("Resultado de la ejecucion -> " + resultado);
			
		/*
		 *                                             PASO 1
		 ******************************************************************************************/
		addStep("Ingresar al portal de aplicaciones "+url+" con credenciales válidas"); 
		
		SeleniumUtil u1 = new SeleniumUtil(new MEdgeTest(), true);
		
		u1.getDriver().manage().window().maximize();
		u1.get(url);
		
	    PortalApps pa = new PortalApps(u1, testCase); // crea objeto de locator
	    
		//u.get(url);
		pa.proceedToPortal();
		pa.cicloLogin(user, password);	
		
		/*
		 *                                             PASO 2
		 ******************************************************************************************/
		addStep("Seleccionar la aplicación Reportes CNT");

		pa.clickPortalReporteCNT();
		
		/*
		 *                                             PASO 3
		 ******************************************************************************************/
		addStep("Seleccionar la opción Administrador funcional -> Consulta datos CNT");
		
	    MenuPrincipal_ReportesCNT mn = new MenuPrincipal_ReportesCNT(u1, testCase); 
	    Consulta_Datos_CNT cd = new Consulta_Datos_CNT(u1, testCase); 
	    
	    mn.clickAdministradorFuncional();
	    mn.clickConsultaDatosCNT();
	    
	    cd.visualizaPantallaConsultaDatosCNT();
		
		/*
		 *                                             PASO 4
		 ******************************************************************************************/
		addStep("Seleccionar la entidad, columnas y filtros a incluir en el reporte excel");
		
		//PASO 1
		cd.seleccionarEntidad(entidad);
		u.hardWait(15);
		mn.esperarImagenCarga();
				
		//PASO 2
				
		cd.clickAgregaCampos();
			
		cd.seleccionarInformacion(tipoInfo);
		mn.esperarImagenCarga();		
				
		cd.seleccionarTodasLasColumnas();
				
		cd.clickBtnAceptar();	
		mn.esperarImagenCarga();
				
		//PASO 3
		ArrayList <String> filtros = new ArrayList<String>();
				
		for(Map.Entry<String, String> entry : data.entrySet()) {
		    String key = entry.getKey();
		    String value = entry.getValue();	    
		    if (key.contains("filtro:") && value.trim().length() > 0) {	
		    	filtros.add(value);
			}    
		}
				
		cd.seleccionarFiltros(filtros);
			
		//PASO 4
		cd.seleccionarConfiguracion(config); 		
		
		u.hardWait(2);
		/*
		 *                                             PASO 5
		 ******************************************************************************************/	     			
		addStep("Dar clic en el botón Genera estadísticas");
		
		cd.seleccionarGeneraTuReporte("excel");		
        cd.clickBtnGeneraEstadisticas("excel");
		
        String fecha = cd.obtenerFechaActual("yyyyMMdd");
		
		boolean validacion = cd.abrirExcelEdge2(fecha, 1);
		//boolean validacion = cd.abrirExcelEdge("OpenFile");
		
		if(validacion) {
			u1.close();
		}

		assertTrue(validacion,"No se muestra el reporte en formato Excel");

        
		
		/*
		 *                                             PASO 5
		 ******************************************************************************************/	     			
		
		addStep("Consultar el estatus de la ejecución del job");
				
		testCase.addScreenShotCurrentStep(u, "OutputJob");
		
		testCase.addBoldTextEvidenceCurrentStep("Estatus de la ejecución del job:");
		testCase.addTextEvidenceCurrentStep(resultado);
		System.out.println("Resultado de la ejecucion -> " + resultado);	
		
		//Valor del output 
		testCase.addBoldTextEvidenceCurrentStep("Output:");
		testCase.addTextEvidenceCurrentStep(control.getOutput()); 
		System.out.println ("Valor de output :"+control.getOutput());
		  
		//Validacion del caso 		
		boolean casoPasado = true;
		
		if(resultado.equals("Failure")) {
		  casoPasado = false;
		  System.out.println("La ejecucion del Job no fue exitosa");
		  }
		  
		assertTrue(casoPasado, "La ejecucion del Job no fue exitosa");
		  
		control.closeViewpoint();
		 
	}

	@Override
	public void beforeTest() {
	}

	@Override
	public String setTestDescription() {
		return "Ejecucion del Job act_inf_rep_cnt";
	}

	@Override
	public String setTestDesigner() {
		return "AutomationQA";
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_001_Ejecucion_Job_act_inf_rep_cnt_test";
	}

	@Override
	public String setTestInstanceID() {
		return null;
	}

	@Override
	public String setPrerequisites() {
		return null;
	}

	@AfterMethod
	public void afterMethod() {
		u.getDriver().quit();
	}
}
