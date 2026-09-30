package WEB.portal_Remesas.scripts;

import java.util.HashMap;

import org.testng.annotations.Test;

import WEB.om.PortalApps;
import WEB.portal_Remesas.om.GenerarReporteMontos;
import modelo.BaseExecution;
import utils.password.PasswordUtil;
import utils.selenium.MEdgeTest;
import utils.selenium.SeleniumUtil;

public class ATC_FT_014_Remesas_Retiro_de_Dinero_Reporte_de_Analisis_por_Dias_Pantalla extends BaseExecution{

	SeleniumUtil u;
	@Test(dataProvider = "data-provider")
	
	public void ATC_FT_014_Remesas_Retiro_de_Dinero_Reporte_de_Analisis_por_Dias_Pantalla_Test(HashMap<String, String> data) throws Exception {
		
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
		String proveedorDos = data.get("ProveedorDos");
		String mostrar = data.get("Mostrar");
		String imagenArchivo = data.get("Imagen_Archivo");
		String OpcionReporte = data.get("Opcion_Reporte");
		String cerrarVent = data.get("Cerrar_Vent");
//		int filtro = Integer.parseInt(data.get("Filtro"));
//		int filtroProv = Integer.parseInt(data.get("FiltroProv"));
//		String strFiltro = data.get("Proveedor");
		
		//									PASO 1
		addStep(" Ingresar al portal de aplicaciones <http://10.184.32.40:8000/AccessControl/pages/login_form.jsf> "); 
		//u = new SeleniumUtil(new ChromeTest(), true); //IETest para usar internet explorer
		SeleniumUtil u = new SeleniumUtil(new MEdgeTest(), true);  // para usar navegador Edge
		//SeleniumUtil u = new SeleniumUtil(new IETest(), true);
		PortalApps pa = new PortalApps(u, testCase);  // crea objeto de locator
		u.get(data.get("Url"));    //toma el valor Url del data provider
		u.hardWait(3);           // tiempo en segundos
		
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
		addStep(" Seleccionar modulo Retiro de dinero/Reportes/Analisis por dia ");
		rm.clickSeleccionarRetirosDinero();
		rm.clickOpAnalisisXDiasRet();
		                      
		//                                 PASO 5
		addStep(" Seleccionar todos los filtros de acuerdo a la busqueda solicitada ");
		rm.clickCapturaFiltros(fechaInicio, fechaFin, zonaOxxo, cRPlaza, cRTienda, proveedor);
		
		//    							  PASO 6
		addStep(" Selecconar los d�as deseados. ");
		rm.clickDiaSemana(diaSemana);
		rm.clickAgregarImagen();
		
		//                                 PASO 7
		addStep(" Precionar Generar Reporte ");
		Boolean band = false;
		rm.clickBtnGenerarReporte(tipoReporte,band);
		Boolean bandera = false;
		rm.clickPosicionaEnBuscar(bandera);
		rm.clickAgregarImagen();

	}
	@Override
	public void beforeTest() {
	}

	@Override
	public String setTestDescription() {
		return "ATC_FT_014_Remesas_Retiro_de_Dinero_Reporte_de_Analisis_por_Dias_Pantalla_Test";
	}

	@Override
	public String setTestDesigner() {
		return "AutomationQA";
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_014_Remesas_Retiro_de_Dinero_Reporte_de_Analisis_por_Dias_Pantalla_Test";
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
