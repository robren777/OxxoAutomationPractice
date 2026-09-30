package WEB.portal_Remesas.ReportesAnalisisPorDia;

import java.util.HashMap;

import org.testng.annotations.Test;

import WEB.om.PortalApps;
import WEB.portal_Remesas.om.GenerarReporteMontos;
import modelo.BaseExecution;
import utils.password.PasswordUtil;
import utils.selenium.MEdgeTest;
import utils.selenium.SeleniumUtil;

public class ATC_FT_057_Generar_repAnalisisXdia_filtrando_XdiaSemana extends BaseExecution{

	SeleniumUtil u;
	@Test(dataProvider = "data-provider")
	
	public void ATC_FT_057_Generar_repAnalisisXdia_filtrando_XdiaSemana_test(HashMap<String, String> data) throws Exception {
		
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
		int filtro = Integer.parseInt(data.get("Filtro"));
		int filtroProv = Integer.parseInt(data.get("FiltroProv"));
		String strFiltro = data.get("CR_Plaza");
		
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
		addStep(" Seleccionar modulo Envios de dinero/Reportes/Analisis por dia ");
		rm.clickSeleccionarEnviosDinero();
		rm.clickOpAnalisisXDias();
		                      
		//                                 PASO 5
		addStep(" Seleccionar todos los filtros de acuerdo a la busqueda  filtrando por dia de la semana y dar clic en generar reporte ");
		rm.clickCapturaFiltros(fechaInicio, fechaFin, zonaOxxo, cRPlaza, cRTienda, proveedor);
		rm.clickDiaSemana(diaSemana);
		rm.clickBotonExportar();
		Boolean band = false;
		rm.clickBtnGenerarReporte(tipoReporte,band);
		
		//                                 PASO 6
		addStep(" Validar que se muestran los resultados de acuerdo a los filtros seleccionados ");
		Boolean validacion = false;
		int descarga = 0;
		rm.cerrarExcelEdge(imagenArchivo, cerrarVent);
		rm.validarDatos(tipoReporte, imagenArchivo,OpcionReporte,filtro, strFiltro,descarga,filtroProv);
		validacion = rm.validarDatos(tipoReporte, imagenArchivo, OpcionReporte,filtro, strFiltro,descarga,filtroProv);
		if(validacion) {
			testCase.addTextEvidenceCurrentStep(" ��Validado: Resultado Exitoso!! ");
		}
		
		rm.clickBotonExportarPant();
		band = false;
		rm.clickBtnGenerarReporte(tipoReporte, band);
		
		Boolean bandera = false;
		rm.clickPosicionaEnBuscar(bandera);
		rm.clickReporteAnalisisXDia(mostrar);

	}
	@Override
	public void beforeTest() {
	}

	@Override
	public String setTestDescription() {
		return "ATC_FT_057_Generar_repAnalisisXdia_filtrando_XdiaSemana_test";
	}

	@Override
	public String setTestDesigner() {
		return "AutomationQA";
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_057_Generar_repAnalisisXdia_filtrando_XdiaSemana_test";
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
