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

public class ATC_FT_21_CuadreDeIndicadores_BasePromedioTienda_AnalisisServicioNacionalSemanal_RemesasInternacionalesTotal
		extends BaseExecution {

	@Test(dataProvider = "data-provider")
	public void ATC_FT_21_CuadreDeIndicadores_BasePromedioTienda_AnalisisServicioNacionalSemanal_RemesasInternacionalesTotal_test(
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
		addStep("Seleccionar filtro 'Total'");
		ima.seleccionarFiltro("Proveedor Remesas", "Total");

		// Paso 6 Obtener dato de microstrategy
		addStep("Obtener informacion de la columna Retiros Totales Captados");
		element = By.xpath("//*[@id=\"table_UniqueReportID\"]/tbody/tr[69]/td[6]");
		u.scrollToElement(element);
		datoMicrostrategy = ima.obtenerDato(element);
		datoMicrostrategy = datoMicrostrategy.replace(",", "");

		// Paso 7 realizar consulta en base de datos y comparar resultado con la
		// informacion de microstrategy
		query = "select sum(a11.RET_TOT_CAP_NUM)\r\n" + "from	\"BIDMMKT\".\"DM_107_REMESAS_SEM\"	a11\r\n"
				+ "where	(a11.TIENDA_KEY in (select distinct tienda_key from BIDMMKT.vm_tmcb where anio_id = 2021)\r\n"
				+ " and a11.SEMANA_ID in (202113))\r\n" + " and	(a11.RET_TOT_CAP_NUM > 0.0) ";
		addStep("Se valida la conexion a la base de datos y se realiza la consulta en la tabla BIDMMKT.DM_107_REMESAS_SEM");
		connectionResult = executeQuery(DB, query);
		ima.validarConexion(connectionResult);
		testCase.addTextEvidenceCurrentStep(query);

		addStep("Comparar datos de la columna con los datos de la consulta");
		datoSQL = connectionResult.getFirstData();

		assertEquals(datoMicrostrategy, datoSQL);// datoSQL
		System.out.println(datoSQL);

		// Paso 8 Obtener dato de microstrategy
		addStep("Obtener informacion de la columna Retiros Totales Captados");
		element = By.xpath("//*[@id=\"table_UniqueReportID\"]/tbody/tr[69]/td[9]");
		u.scrollToElement(element);
		datoMicrostrategy = ima.obtenerDato(element);
		datoMicrostrategy = datoMicrostrategy.replace(",", "");

		// Paso 9 realizar consulta en base de datos y comparar resultado con la
		// informacion de microstrategy
		query = "select sum(a11.RET_CAPT_EFEC_NUM)\r\n" + "from	\"BIDMMKT\".\"DM_107_REMESAS_SEM\"	a11\r\n"
				+ "where	(a11.TIENDA_KEY in (select distinct tienda_key from BIDMMKT.vm_tmcb where anio_id = 2021)\r\n"
				+ " and a11.SEMANA_ID in (202113))\r\n" + " and	(a11.RET_TOT_CAP_NUM > 0.0) ";
		addStep("Se valida la conexion a la base de datos y se realiza la consulta en la tabla BIDMMKT.DM_107_REMESAS_SEM");
		connectionResult = executeQuery(DB, query);
		ima.validarConexion(connectionResult);
		testCase.addTextEvidenceCurrentStep(query);

		addStep("Comparar datos de la columna con los datos de la consulta");
		datoSQL = connectionResult.getFirstData();

		assertEquals(datoMicrostrategy, datoSQL);// datoSQL
		System.out.println(datoSQL);

		// Paso 10 Obtener dato de microstrategy
		addStep("Obtener informacion de la columna Retiros Totales Captados");
		element = By.xpath("//*[@id=\"table_UniqueReportID\"]/tbody/tr[69]/td[12]");
		u.scrollToElement(element);
		datoMicrostrategy = ima.obtenerDato(element);
		datoMicrostrategy = datoMicrostrategy.replace(",", "");
		datoMicrostrategy = datoMicrostrategy.replace("$", "");

		// Paso 11 realizar consulta en base de datos y comparar resultado con la
		// informacion de microstrategy
		query = "select sum(a11.RETIRO_AMT)\r\n" + "from	\"BIDMMKT\".\"DM_107_REMESAS_SEM\"	a11\r\n"
				+ "where	(a11.TIENDA_KEY in (select distinct tienda_key from BIDMMKT.vm_tmcb where anio_id = 2021)\r\n"
				+ " and a11.SEMANA_ID in (202113))\r\n" + " and	(a11.RET_TOT_CAP_NUM > 0.0) ";
		addStep("Se valida la conexion a la base de datos y se realiza la consulta en la tabla BIDMMKT.DM_107_REMESAS_SEM");
		connectionResult = executeQuery(DB, query);
		ima.validarConexion(connectionResult);
		testCase.addTextEvidenceCurrentStep(query);

		addStep("Comparar datos de la columna con los datos de la consulta");
		datoSQL = connectionResult.getFirstData();

		assertEquals(datoMicrostrategy, datoSQL);// "120828835.4"
		System.out.println(datoSQL);

		// Paso 12 Obtener dato de microstrategy
		addStep("Obtener informacion de la columna Retiros Totales Captados");
		element = By.xpath("//*[@id=\"table_UniqueReportID\"]/tbody/tr[69]/td[3]");
		u.scrollToElement(element);
		datoMicrostrategy = ima.obtenerDato(element);
		datoMicrostrategy = datoMicrostrategy.replace(",", "");

		// Paso 13 realizar consulta en base de datos y comparar resultado con la
		// informacion de microstrategy
		query = "select sum((a11.MARGEN_OPERATIVO_AMT - AJUS_COM_PERD_AMT)+ REAJUS_COMISION_AMT)\r\n"
				+ "from	\"BIDMMKT\".\"DM_107_REMESAS_SEM\"	a11\r\n"
				+ "where	(a11.TIENDA_KEY in (select distinct tienda_key from BIDMMKT.vm_tmcb where anio_id = 2021)\r\n"
				+ " and a11.SEMANA_ID in (202113))\r\n" + " and	(a11.RET_TOT_CAP_NUM > 0.0) ";
		addStep("Se valida la conexion a la base de datos y se realiza la consulta en la tabla BIDMMKT.DM_107_REMESAS_SEM");
		connectionResult = executeQuery(DB, query);
		ima.validarConexion(connectionResult);
		testCase.addTextEvidenceCurrentStep(query);

		addStep("Comparar datos de la columna con los datos de la consulta");
		datoSQL = connectionResult.getFirstData();

		assertEquals(datoMicrostrategy, datoSQL);// "2544939"
		System.out.println(datoSQL);

		// Paso 13 realizar consulta en base de datos y comparar resultado con la
		// informacion de microstrategy
		query = "Select sum(A.MONTO)\r\n" + "From Xxfc.Xxfc_Gl_Envio_Dinero_Detalle A, Xxfc.Xxfc_Gl_Envio_Dinero B\r\n"
				+ "Where A.ID_ENVIO_DINERO=B.ID_ENVIO_DINERO AND B.FORMA_PAGO_ID in ('PYIO21','PYIO22','PYIO18') \r\n"
				+ "AND A.Fecha_ADMINISTRATIVA >= '22/03/2021 00:00:00' and  A.Fecha_ADMINISTRATIVA <= '28/03/2021 00:00:00';\r\n"
				+ "";
		addStep("Se valida la conexion a la base de datos y se realiza la consulta en la tabla BIDMMKT.DM_107_REMESAS_SEM");
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
		return "ATC_FT_21_CuadreDeIndicadores_BasePromedioTienda_AnalisisServicioNacionalSemanal_RemesasInternacionalesTotal_test";
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
