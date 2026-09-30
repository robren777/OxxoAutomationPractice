  package IMA.MVP2_RST.Scripts.E04_HU02;

import static org.testng.Assert.assertTrue;

import java.util.HashMap;

import org.testng.annotations.Test;

import IMA.MVP2_RST.om.RstUtils;
import IMA.util.IMATools;
import modelo.BaseExecution;
import utils.selenium.ChromeTest;
import utils.selenium.SeleniumUtil;

/**
 * DESCRIPCION: Caso de prueba para la validacion de la generacion del reporte de analisis de margen del socio Hotpay
 * 
 * NOMBRE CASOS DE PRUEBA MANUAL QUE SE ABARCAN:
 * - MTC_FT_004_VerificarQueLosCamposConInformacionDeComisionesDelSocioHotpayAparezcanEnElReporteDeAnalisisDeMargen
 * 
 * PREREQUISITOS:
 * - Se tienen que haber realizado transacciones previamente del socio Hotpay
 *
 * INSUMOS:
 * - Tener acceso a la plataforma MicroStrategy
 *
 * @author Herbe Cauich
 * @date 20/Enero/2025
 
 */

public class ATC_FT_004_VerificarQueLosCamposConInformacionDeComisionesDelSocioHotpayAparezcanEnElReporteDeAnalisisDeMargen extends BaseExecution {

	@Test(dataProvider = "data-provider")
	public void ATC_FT_004_VerificarQueLosCamposConInformacionDeComisionesDelSocioHotpayAparezcanEnElReporteDeAnalisisDeMargen_test(HashMap<String, String> data) throws Exception {

		// Utileria
		SeleniumUtil u = new SeleniumUtil(new ChromeTest(), true);
		String semana = data.get("semana");

		IMATools ima = new IMATools(u, testCase);
		RstUtils rstUtils = new RstUtils(u,testCase);


		
		// Paso 1
		addStep("Abrir navegador y loguearse en la pagina de MicroStrategy"
				+ "Ir a la ruta:INFORMACION COMERCIAL>INFORMES COMPARTIDOS>INFORMACION COMERCIAL>TELEFONIA Y SERVICIOS>RESULTADOS SERVICIOS>NACIONAL>RETIROS>RESULTADOS COMERCIALES");
		ima.Login();
		assertTrue(rstUtils.clickInfoComercialQa(),"No se encontro el elemento informacion comercial QA");
		assertTrue(rstUtils.clickInformeCompartido(),"No se encontrol el elemento Informe compartido ");
		assertTrue(rstUtils.clickReportLiberar(),"No se encontro el elemento Reportes por liberar");
		assertTrue(rstUtils.clickInfoComercial(),"No se encontro el elemento Informacion comercial");
		assertTrue(rstUtils.clickPorTema(),"No se encontro el elemento por tema");
		assertTrue(rstUtils.analisisServicio(),"No se encontro el elemento analisis de servicio");
		assertTrue(rstUtils.clickNacional(),"No se encontro el elemento nacional");
		assertTrue(rstUtils.clickAnalisisSemanal(),"No se encontro el elemento analisis semanal");
				
		//paso 2
		addStep("Generar y descargar el Reporte de analsis de margen");
		rstUtils.seleccionarYAgregarSemana(semana);
		assertTrue(rstUtils.seleccionarYAgregarTienda(),"No se encontro el elemento Seleccionar y agregar tienda");
				
		// paso 3
		addStep("Validar Generacion de reporte.");
		assertTrue(rstUtils.clickEjecutarInforme(),"No se encontro el elemento Ejecutar Informe");
		assertTrue(rstUtils.validarGeneracionReporte(),"No se encontro el elemento");
		u.close();
	}

	@Override
	public void beforeTest() {
		// TODO Auto-generated method stub

	}

	@Override
	public String setTestDescription() {
		// TODO Auto-generated method stub
		return "Validar la generación del reporte";
	}

	@Override
	public String setTestDesigner() {
		// TODO Auto-generated method stub
		return "EQUIPO AUTOMATIZACION";
	}

	@Override
	public String setTestFullName() {
		// TODO Auto-generated method stub
		return "ATC_FT_004_VerificarQueLosCamposConInformacionDeComisionesDelSocioHotpayAparezcanEnElReporteDeAnalisisDeMargen_test";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String setPrerequisites() {
		// TODO Auto-generated method stub
		return null;
	}

}
