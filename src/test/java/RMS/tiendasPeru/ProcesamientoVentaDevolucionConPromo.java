package RMS.tiendasPeru;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import java.util.HashMap;

import org.testng.annotations.Test;

import RMS.util.GlobalVariables;
import RMS.util.JobUtil;
import modelo.BaseExecution;
import utils.selenium.ChromeTest;
import utils.selenium.SeleniumUtil;
import utils.sql.SQLResult;
import utils.sql.SQLUtil;

public class ProcesamientoVentaDevolucionConPromo extends BaseExecution {

	@Test(dataProvider = "data-provider")
	public void ATC_FT_008_Procesamiento_Venta_Devolucion_Promo(HashMap<String, String> data) throws Exception {

		SeleniumUtil u = new SeleniumUtil(new ChromeTest(), true);
		JobUtil job = new JobUtil(u, testCase, data);

		// PASO 1
		addStep("Ingresar al aplicativo de RMS de PERU ingresando las credenciales");
		System.out.println("PASO 1");

		// PASO 2
		addStep("Crear el Evento, irse a la opci�n Pricing, posteriormente \"Promotion Events\"  > Edit.");
		System.out.println("PASO 2");

		// PASO 3
		addStep("Ingresar los datos del evento a crear y se oprime bot�n OK para crear el evento.");
		System.out.println("PASO 3");

		// PASO 4
		addStep("Para crear la Promoci�n dirigirse a la opci�n Pricing del men� Retek > opci�n \"Promotions\" > New ");
		System.out.println("PASO 4");

		// PASO 5
		addStep("Presionar OK para habilitar el formulario, ingresar los datos de la promoci�n");
		System.out.println("PASO 5");

		String promotion = "";

		// PASO 6
		addStep("Posteriormente Dar clic al bot�n \"Store\" y agregar la tienda"
				+ " y presionar el bot�n Apply para aplicar el cambio y" + " presionar OK.");
		System.out.println("PASO 6");

		// PASO 7
		addStep("Ir a la ruta \"Options\" Add �tems y elegir \"Multi Unit\", "
				+ "ingresar los datos correspondientes al producto y presionar OK.");
		System.out.println("PASO 7");

		// PASO 8
		addStep("Ir de nuevo a la ruta \"Options\" y seleccionar opci�n \"Submit\".");
		System.out.println("PASO 8");

		// PASO 9
		addStep("Se ejecuta el Job para la cadena  pcovrl $CONNECT_STRING localizado en el grupo: "
				+ "TQA_PRECIOS_Y_PROMOCIONES_PERU desde Control- M.");
		System.out.println("PASO 9");

		String resultadoEjecucion = job.ejecutar("pcovrl_PERU");
		boolean ejecucionCorrectaJob = job.fueEjecutadoCorrectamente(resultadoEjecucion);

		assertTrue(ejecucionCorrectaJob, "El job pcovrl no se ejecuto correctamente");

		// PASO 10
		addStep("Ir una vez m�s a la ruta \"Options\" y seleccionar opci�n \"Approve\".");
		System.out.println("PASO 10");

		// PASO 11
		addStep("Se ejecuta de nuevo el Job para la cadena pcovrl $CONNECT_STRING "
				+ "localizado en el grupo: TQA_PRECIOS_Y_PROMOCIONES_PERU  desde Control- M.");

		resultadoEjecucion = job.ejecutar("pcovrl_PERU");
		ejecucionCorrectaJob = job.fueEjecutadoCorrectamente(resultadoEjecucion);

		assertTrue(ejecucionCorrectaJob, "El job pcovrl no se ejecuto correctamente");

		// PASO 12
		addStep("Conectarse a la Base de Datos: BDPRMSQ");
		System.out.println("PASO 12");

		SQLUtil dbPrms = new SQLUtil(GlobalVariables.DB_HOST_BDPRMSQ, GlobalVariables.DB_USER_BDPRMSQ,
				GlobalVariables.DB_PASSWORD_BDPRMSQ);

		testCase.addTextEvidenceCurrentStep("Conexion a BDPRMSQ exitosa.");

		// PASO 13
		addStep("Se consulta la tabla PROMSTORE para validar que se encuentra el registro" + " de la promoci�n con id "
				+ promotion + " mediante la siguiente consulta");
		System.out.println("PASO 13");

		String promstore = String.format("SELECT * FROM PROMSTORE WHERE PROMOTION = %s", promotion);
		System.out.println(promstore);

		SQLResult executeQuery = executeQuery(dbPrms, promstore);

		boolean statusQuery = executeQuery.isEmpty();
		if (!statusQuery) {
			testCase.addQueryEvidenceCurrentStep(executeQuery);
		}

		assertFalse(statusQuery, "No se encontraron registros con PROMOTION = " + promotion + " " + "PROMSTORE");

		u.close();

	}

	@Override
	public void beforeTest() {
	}

	@Override
	public String setPrerequisites() {
		return null;
	}

	@Override
	public String setTestDescription() {
		return "Construido. Validar que se pueda generar el folio de promocion " + "desde el aplicativo";
	}

	@Override
	public String setTestDesigner() {
		return "Guerrero Vargas - Equipo RMS";
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_008_Procesamiento_Venta_Devolucion_Promo";
	}

	@Override
	public String setTestInstanceID() {
		return null;
	}

}
