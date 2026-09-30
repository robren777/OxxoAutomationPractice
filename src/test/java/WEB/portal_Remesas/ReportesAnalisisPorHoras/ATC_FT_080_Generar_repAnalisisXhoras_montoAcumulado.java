package WEB.portal_Remesas.ReportesAnalisisPorHoras;

import java.util.HashMap;

import org.openqa.selenium.By;
import org.testng.annotations.Test;

import WEB.om.PortalApps;
import WEB.portal_Remesas.om.GenerarReporteMontos;
import modelo.BaseExecution;
import utils.password.PasswordUtil;
import utils.selenium.MEdgeTest;
import utils.selenium.SeleniumUtil;

public class ATC_FT_080_Generar_repAnalisisXhoras_montoAcumulado extends BaseExecution{

	SeleniumUtil u;
	@Test(dataProvider = "data-provider")
	
	public void ATC_FT_080_Generar_repAnalisisXhoras_montoAcumulado_test(HashMap<String, String> data) throws Exception {
		
		/*
		 * Variables
		 ******************************************************************************************/
		String user = data.get("User");
		String password = PasswordUtil.decryptPassword(data.get("Password"));
//		String mngAlerta = "Usuario y/o Contrase�a Invalidos";
		String negocio = data.get("Negocio");
		String perfil = data.get("Perfil");

		String fechaInicio = data.get("Fecha_Inicio");
		String fechaFin = data.get("Fecha_Fin");
		String zonaOxxo = data.get("Zona_Oxxo");
		String cRPlaza = data.get("CR_Plaza");
		String cRTienda = data.get("CR_Tienda");
		String proveedor = data.get("Proveedor");
		String tipoReporte = data.get("Tipo_Reporte");
		String numRegistros = data.get("Numero_registros");
		String diaSemana = data.get("Dia_Semana");
		String mostrar = data.get("Mostrar");
		String opReporte = data.get("Opcion_Reporte");
		
		By xmlChekMonto = By.xpath("//*[@id=\"form1:modo\"]/tbody/tr/td[3]/div/div[2]/span");
		By xmlChekTransa = By.xpath("//*[@id=\"form1:modo\"]/tbody/tr/td[1]/div/div[2]");
		
		//									PASO 1
		addStep(" Ingresar al portal de aplicaciones <http://10.184.32.40:8000/AccessControl/pages/login_form.jsf> "); 
		//u = new SeleniumUtil(new ChromeTest(), true); //IETest para usar internet explorer
		SeleniumUtil u = new SeleniumUtil(new MEdgeTest(), true);  // para usar navegador Edge
		//SeleniumUtil u = new SeleniumUtil(new IETest(), true);
		PortalApps pa = new PortalApps(u, testCase);  // crea objeto de locator
		u.get(data.get("Url"));    //toma el valor Url del data provider
		u.hardWait(3);           // tiempo en segundos
		
//		Alert alert = driver.switchTo().alert();
//		driver.switchTo().alert();
		
		
		pa.proceedToPortal();    // es para lo de la pagina no segura
		GenerarReporteMontos rm = new GenerarReporteMontos(u, testCase);
		//pa.logginInventarioEspacios(user, password);	
		rm.clickAgregarImagen();
		
		//		                            PASO 2
		
		addStep(" Ingresar el usuario, password y dar clic en entrar ");
		pa.cicloLogin(user, password);

		//                                  PASO 3
		addStep(" Seleccionar el menu Portal Remesas ");	
		rm.clickSeleccionarPortalRemesas();

		//                                 PASO 4
		addStep(" Seleccionar modulo Envios de dinero/Reportes/Analisis por horas ");
		rm.clickSeleccionarEnviosDinero();
		rm.clickOpAnalisisXHoras();
		                      
		//                                 PASO 5
		addStep(" Seleccionar todos los filtros de acuerdo a la busqueda solicitada, seleccionado por horas con monto acumulado y dar clic en generar reporte ");
		rm.clickCapturaFiltros(fechaInicio, fechaFin, zonaOxxo, cRPlaza, cRTienda, proveedor);
//		rm.clickSeleccionHorario();
		rm.clickMontoAcumulado();
		Boolean band = true;
		rm.clickBtnGenerarReporte(tipoReporte,band);
		
		//                                 PASO 6
		addStep(" Validar que se muestran los resultados de acuerdo a los filtros seleccionados y horas con montos acumulados ");
		boolean bandera = false;
		boolean validacion = false;
		rm.clickPosicionaEnBuscar(bandera);
		validacion = rm.validaMostrarReporte(xmlChekTransa,xmlChekMonto,mostrar,opReporte);
		
		if(validacion) {
			testCase.addTextEvidenceCurrentStep(" ��Validado: Monto Acumulado Exitoso!! " + opReporte);
		}
		rm.clickReporteAnalisisXHoras(mostrar);
		

	}
	@Override
	public void beforeTest() {
	}

	@Override
	public String setTestDescription() {
		return "ATC_FT_080_Generar_repAnalisisXhoras_montoAcumulado_test";
	}

	@Override
	public String setTestDesigner() {
		return "AutomationQA";
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_080_Generar_repAnalisisXhoras_montoAcumulado_test";
	}

	@Override
	public String setTestInstanceID() {
		return null;
	}

	@Override
	public String setPrerequisites() {
		return null;
	}
	
//	@AfterMethod
//	public void afterMethod() {
//		u.getDriver().quit();
//	}


}
