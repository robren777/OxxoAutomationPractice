package WEB.reporteCNT.scripts;

import static org.testng.Assert.assertTrue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import WEB.om.PortalApps;
import WEB.reporteCNT.om.Consulta_Datos_CNT;
import WEB.reporteCNT.om.MenuPrincipal_ReportesCNT;
import WEB.util.GlobalVariables;
import modelo.BaseExecution;
import utils.selenium.MEdgeTest;
import utils.selenium.SeleniumUtil;
/**Desc: Se validara que se genere el  reporte estad�stico  sin agotar la memoria del aplicativo 
 * MTC_FT_002. Generaci�n de Reporte estad�stico 
 * @author Andrea Contreras
 * @date Noviembre 2022
 */
public class ATC_FT_002_Generacion_Reporte_Estadistico extends BaseExecution {

	SeleniumUtil u;

	@Test(dataProvider = "data-provider")
	public void  ATC_FT_002_Generacion_Reporte_Estadistico_test(HashMap<String, String> data) throws Exception {

		/*
		 * Variables
		 ******************************************************************************************/
		String user = GlobalVariables.USER_PORTAL_APPS_6006020;
		String password = GlobalVariables.PASSWORD_PORTAL_APPS_6006020;
		String url = GlobalVariables.URL_PORTAL_APPS;
		String entidad = data.get("Entidad");
        String tipoInfo = data.get("Tipo_Informacion");
		
		JSONArray array = new JSONArray(data.get("Config"));

		/*
		 *                                             PASO 1
		 ******************************************************************************************/
		addStep("Ingresar al portal de aplicaciones "+url+" con credenciales v�lidas"); 
		
		u = new SeleniumUtil(new MEdgeTest(), true);
		u.getDriver().manage().window().maximize();
		
	    PortalApps pa = new PortalApps(u, testCase); 
	    MenuPrincipal_ReportesCNT mn = new MenuPrincipal_ReportesCNT(u, testCase); 
	    Consulta_Datos_CNT cd = new Consulta_Datos_CNT(u, testCase); 
	    
		u.get(url);
		//u.hardWait(3); // tiempo en segundos
		pa.proceedToPortal();
		pa.cicloLogin(user, password);	
		
		
		/*
		 *                                             PASO 2
		 ******************************************************************************************/
		addStep("Seleccionar la aplicaci�n Reportes CNT");
		
		pa.clickPortalReporteCNT();

		/*
		 *                                             PASO 3
		 ******************************************************************************************/
		addStep("Seleccionar la opci�n Administrador funcional -> Consulta datos CNT");
		
		mn.clickAdministradorFuncional();
	    mn.clickConsultaDatosCNT();
		
		cd.visualizaPantallaConsultaDatosCNT();
		
		
		/*
		 *                                             PASO 4
		 ******************************************************************************************/
		addStep("Seleccionar los valores\r\n" + 
				"Entidad: TIENDAS_ADMON\r\n" + 
				"Columnas: Todas las columnas <<Tipo de informaci�n>>\r\n" + 
				"Filtros: Algunas plazas\r\n" + 
				"Genera tu reporte: Excel ");
	
		//PASO 1
		cd.seleccionarEntidad(entidad);
		u.hardWait(15);
		mn.esperarImagenCarga();
		
		//PASO 2
		
		cd.clickAgregaCampos();
		
		cd.seleccionarInformacion(tipoInfo);
		mn.esperarImagenCarga();		
		
		/*
		 * ArrayList <String> columnas = new ArrayList<String>();
		 * for(Map.Entry<String, String> entry : data.entrySet()) { String key =
		 * entry.getKey(); String value = entry.getValue(); if
		 * (key.contains("columnas:") && value.trim().length() > 0) {
		 * columnas.add(value); } }
		 * 
		 * cd.seleccionarColumnas(columnas);//metodo para seleccionar columnas en especifico
		 * */	
		
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
		if(data.get("Config")!="") {
			cd.seleccionarConfiguracion(array);
		}	 
		
		
		//PASO 5
		cd.seleccionarGeneraTuReporte("excel");
		
		/*
		 *                                             PASO 5
		 ******************************************************************************************/	     			
		addStep("Dar clic en el bot�n Genera estad�sticas");
		
		cd.clickBtnGeneraEstadisticas("excel");	
		
		String fecha = cd.obtenerFechaActual("yyyyMMdd");
		
		boolean validacion = cd.abrirExcelEdge2(fecha, 1);
		//boolean validacion = cd.abrirExcelEdge("OpenFile");

		assertTrue(validacion,"No se muestra el reporte en formato Excel");
		
	}

	@Override
	public void beforeTest() {
	}

	@Override
	public String setTestDescription() {
		return "Generacion de Reporte Estadistico";
	}

	@Override
	public String setTestDesigner() {
		return "AutomationQA";
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_002_Generacion_Reporte_Estadistico_test";
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
	  // u.getDriver().quit();
	}
	
}
