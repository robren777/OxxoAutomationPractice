package IMA.remesas;

import static org.testng.Assert.assertEquals;

import java.util.HashMap;

import org.openqa.selenium.By;
import org.testng.annotations.Test;

import IMA.util.GlobalVariables;
import IMA.util.IMATools;
import modelo.BaseExecution;
import utils.selenium.ChromeTest;
import utils.selenium.SeleniumUtil;
import utils.sql.SQLResult;

public class ATC_FT_22_CuadreDeIndicadores_BasePromedioTienda_AnalisisServicioNacionalSemanal_RemesasInternacionalesWesternUnion
		extends BaseExecution {

	@Test(dataProvider = "data-provider")
	public void ATC_FT_22_CuadreDeIndicadores_BasePromedioTienda_AnalisisServicioNacionalSemanal_RemesasInternacionalesWesternUnion_test(
			HashMap<String, String> data) throws Exception {

		// Utileria
		SeleniumUtil u = new SeleniumUtil(new ChromeTest(), true);
		String report = data.get("Report");
		String query;
		SQLResult connectionResult;
		By element;
		String datoMicrostrategy;
		String datoSQL;
		utils.sql.SQLUtil DB = new utils.sql.SQLUtil(GlobalVariables.DB_HOST_FCDW4QA, GlobalVariables.DB_USER_FCDW4QA,
				GlobalVariables.DB_PASSWORD_FCDW4QA);

		IMATools ima = new IMATools(u, testCase);

		System.out.println(report);
		// paso 1
		addStep("Abrir navegador y loguearse en la pagina de MicroStrategy");
		ima.Login();

		// Paso 2
		addStep("Ir a carpeta Remesas");
		ima.goToRoute("Información Comercial QA",
				"Información Comercial/Telefonía y Servicios/Resultados Servicios/Nacional/Remesas");

		// Paso 3
		addStep("Seleccionar una opcion en cada filtro y Ejecutar Reporte");
		ima.EjecutarInformeReporteSeleccionado(report);

		// Paso 4 Aplicar filtro Total
		addStep("Seleccionar filtro 'Western Union'");
		ima.seleccionarFiltro("Proveedor Remesas", "Western Union");
		u.hardWait(20);
		
		// Paso 6 Obtener dato de microstrategy
		addStep("Obtener informacion de la columna Retiros Totales Captados");
		element = By.xpath("//*[@id=\"table_UniqueReportID\"]/tbody/tr[70]/td[10]");
		u.scrollToElement(element);
		datoMicrostrategy = ima.obtenerDato(element);
		datoMicrostrategy = datoMicrostrategy.replace(",", "");

		// Paso 7 realizar consulta en base de datos y comparar resultado con la
		// informacion de microstrategy
		query = "select sum(RET_TOT_CAP_NUM) from BIDMMKT.DM_107_REMESAS_MMT WHERE mes_id='202101' and PROVEEDOR_REMESA_KEY='1' ";
		addStep("Se valida la conexion a la base de datos y se realiza la consulta en la tabla BIDMMKT.DM_107_REMESAS_MMT");
		connectionResult = executeQuery(DB, query);
		ima.validarConexion(connectionResult);
		testCase.addTextEvidenceCurrentStep(query);

		addStep("Comparar datos de la columna con los datos de la consulta");
		datoSQL = connectionResult.getFirstData();

		assertEquals(datoMicrostrategy, datoSQL);//"121279"
		System.out.println(datoSQL);

		// Paso 8 Obtener dato de microstrategy
		addStep("Obtener informacion de la columna Retiros Totales Captados en efectivo");
		element = By.xpath("//*[@id=\"table_UniqueReportID\"]/tbody/tr[70]/td[16]");
		u.scrollToElement(element);
		datoMicrostrategy = ima.obtenerDato(element);
		datoMicrostrategy = datoMicrostrategy.replace(",", "");

		// Paso 9 realizar consulta en base de datos y comparar resultado con la
		// informacion de microstrategy
		query = "select sum(RET_CAPT_EFEC_NUM) from BIDMMKT.DM_107_REMESAS_MMT WHERE mes_id='202101' and PROVEEDOR_REMESA_KEY='1'";
		addStep("Se valida la conexion a la base de datos y se realiza la consulta en la tabla BIDMMKT.DM_107_REMESAS_MMT");
		connectionResult = executeQuery(DB, query);
		ima.validarConexion(connectionResult);
		testCase.addTextEvidenceCurrentStep(query);

		addStep("Comparar datos de la columna con los datos de la consulta");
		datoSQL = connectionResult.getFirstData();

		assertEquals(datoMicrostrategy, datoSQL);//"116327"
		System.out.println(datoSQL);

		// Paso 10 Obtener dato de microstrategy
		addStep("Obtener informacion de la columna Monto Retiro");
		element = By.xpath("//*[@id=\"table_UniqueReportID\"]/tbody/tr[70]/td[22]");
		u.scrollToElement(element);
		datoMicrostrategy = ima.obtenerDato(element);
		datoMicrostrategy = datoMicrostrategy.replace(",", "");
		datoMicrostrategy = datoMicrostrategy.replace("$", "");

		// Paso 11 realizar consulta en base de datos y comparar resultado con la
		// informacion de microstrategy
		query = "select sum(RETIRO_AMT) from BIDMMKT.DM_107_REMESAS_MMT WHERE mes_id='202101' and PROVEEDOR_REMESA_KEY='1'";
		addStep("Se valida la conexion a la base de datos y se realiza la consulta en la tabla BIDMMKT.DM_107_REMESAS_MMT");
		connectionResult = executeQuery(DB, query);
		ima.validarConexion(connectionResult);
		testCase.addTextEvidenceCurrentStep(query);

		addStep("Comparar datos de la columna con los datos de la consulta");
		datoSQL = connectionResult.getFirstData();

		assertEquals(datoMicrostrategy, datoSQL);//"152187665.4"
		System.out.println(datoSQL);
		
		// Paso 12 Obtener dato de microstrategy
		addStep("Obtener informacion de la columna Monto Retiro en efectivo");
		element = By.xpath("//*[@id=\"table_UniqueReportID\"]/tbody/tr[70]/td[4]");
		u.scrollToElement(element);
		datoMicrostrategy = ima.obtenerDato(element);
		datoMicrostrategy = datoMicrostrategy.replace(",", "");
		datoMicrostrategy = datoMicrostrategy.replace("$", "");

		// Paso 13 realizar consulta en base de datos y comparar resultado con la
		// informacion de microstrategy
		query = "select sum(RETIRO_EFEC_AMT) from BIDMMKT.DM_107_REMESAS_MMT WHERE mes_id='202101' and PROVEEDOR_REMESA_KEY='1';";
		addStep("Se valida la conexion a la base de datos y se realiza la consulta en la tabla BIDMMKT.DM_107_REMESAS_MMT");
		connectionResult = executeQuery(DB, query);
		ima.validarConexion(connectionResult);
		testCase.addTextEvidenceCurrentStep(query);

		addStep("Comparar datos de la columna con los datos de la consulta");
		datoSQL = connectionResult.getFirstData();

		assertEquals(datoMicrostrategy, datoSQL);//"2344043.7"
		System.out.println(datoSQL);
		
		// Paso 14 Obtener dato de microstrategy
		addStep("Obtener informacion de la columna Total de Margen Neto");
		element = By.xpath("//*[@id=\"table_UniqueReportID\"]/tbody/tr[70]/td[4]");
		u.scrollToElement(element);
		datoMicrostrategy = ima.obtenerDato(element);
		datoMicrostrategy = datoMicrostrategy.replace(",", "");
		datoMicrostrategy = datoMicrostrategy.replace("$", "");

		// Paso 15 realizar consulta en base de datos y comparar resultado con la
		// informacion de microstrategy
		query = "select sum((MARGEN_OPERATIVO_AMT - AJUS_COM_PERD_AMT) + REAJUS_COMISION_AMT) from BIDMMKT.DM_107_REMESAS_MMT WHERE mes_id='202101' and PROVEEDOR_REMESA_KEY='1'";
		addStep("Se valida la conexion a la base de datos y se realiza la consulta en la tabla BIDMMKT.DM_107_REMESAS_MMT");
		connectionResult = executeQuery(DB, query);
		ima.validarConexion(connectionResult);
		testCase.addTextEvidenceCurrentStep(query);

		addStep("Comparar datos de la columna con los datos de la consulta");
		datoSQL = connectionResult.getFirstData();

		assertEquals(datoMicrostrategy, datoSQL);//"2344043.7"
		System.out.println(datoSQL);

		// Paso 16 realizar consulta en base de datos y comparar resultado con la
		// informacion de microstrategy
		query = "Select A.*, B.FORMA_PAGO_ID\r\n" + 
				"From Xxfc.Xxfc_Gl_Envio_Dinero_Detalle A, Xxfc.Xxfc_Gl_Envio_Dinero B\r\n" + 
				"Where A.ID_ENVIO_DINERO=B.ID_ENVIO_DINERO AND B.FORMA_PAGO_ID in ('PYIO18') \r\n" + 
				"AND A.Fecha_Transaccion >= '01/01/2021 00:00:00' and  A.Fecha_Transaccion <= '31/01/2021 00:00:00'";
		addStep("Se valida la conexion a la base de datos y se realiza la consulta en la tabla BIDMMKT.DM_107_REMESAS_MMT");
		connectionResult = executeQuery(DB, query);
		ima.validarConexion(connectionResult);
		testCase.addTextEvidenceCurrentStep(query);

		addStep("Comparar datos de la columna con los datos de la consulta");
		datoSQL = connectionResult.getFirstData();

		System.out.println(datoSQL);

		u.hardWait(5);
		u.close();
	}

	@Override
	public void beforeTest() {
		// TODO Auto-generated method stub

	}

	@Override
	public String setTestDescription() {
		// TODO Auto-generated method stub
		return "Validar informacion mostrada por un filtro en especifico";
	}

	@Override
	public String setTestDesigner() {
		// TODO Auto-generated method stub
		return "EQUIPO AUTOMATIZACION";
	}

	@Override
	public String setTestFullName() {
		// TODO Auto-generated method stub
		return "ATC_FT_22_CuadreDeIndicadores_BasePromedioTienda_AnalisisServicioNacionalSemanal_RemesasInternacionalesWesternUnion_test";
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
