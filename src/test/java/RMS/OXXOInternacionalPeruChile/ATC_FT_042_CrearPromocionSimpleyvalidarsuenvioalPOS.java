package RMS.OXXOInternacionalPeruChile;

import static org.testng.Assert.assertTrue;

import java.awt.Robot;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import org.sikuli.script.App;
import org.sikuli.script.Pattern;
import org.testng.annotations.Test;

import RMS.util.GlobalVariables;
import RMS.util.JobUtil;
import RMS.util.Queries_Tiendas;
import RMS.util.PageObject.Tiendas_Jorge;
import modelo.BaseExecution;
import utils.SikuliUtil;
import utils.selenium.IETest;
import utils.selenium.SeleniumUtil;
import utils.sql.SQLResult;
import utils.sql.SQLUtil;

public class ATC_FT_042_CrearPromocionSimpleyvalidarsuenvioalPOS extends BaseExecution {

	@Test(dataProvider = "data-provider")
	public void ATC_FT_042_CrearPromocionSimpleyvalidarsuenvioalPOS_test(HashMap<String, String> data)
			throws Exception {

		/*
		 * Informacion para consultas en base de datos de PERU
		 */
		
		SQLUtil dbRms = new SQLUtil(GlobalVariables.DB_HOST_BDRMSPERQA, GlobalVariables.DB_USER_BDRMSPERQA,
				GlobalVariables.DB_PASSWORD_BDRMSPERQA);

		String cb = null;
		boolean st = false;
		SeleniumUtil su = new SeleniumUtil(new IETest(), true);
		SikuliUtil sk = new SikuliUtil();
		String pathImg = sk.PatternPath("RMS\\"+"OXXO_Internacional");
		File Fol = new File(pathImg);
		Robot robot = new Robot();
		HashMap<String, Pattern> ImgPatter = new HashMap<String, Pattern>();
		ArrayList<Pattern> patterns = new ArrayList<Pattern>();
		ArrayList<String> ListImage = new ArrayList<String>();
		Pattern pathimg = new Pattern();
		Tiendas_Jorge t = new Tiendas_Jorge(sk, su, cb, robot, pathimg, ListImage, ImgPatter, patterns, Fol, pathImg,
				dbRms, st, data, testCase);
		JobUtil job = new JobUtil(su, testCase, data);
		
		boolean ejecucionCorrectaJob = false;

		SQLResult executeQuery;

		// Paso 1 - 2
		addStep("Ingresar a RMS a trav�s de la siguiente liga: " + data.get("rms_url") + " y hacer login");
		testCase.addScreenShotCurrentStep("Paso1");
		t.Login_En_RMS();

//		// Paso 3 - 4
//		addStep("Proceder a crear el Evento, irse a la opci�n Pricing, posteriormente Promotion Events."
//				+ "Seleccionar e ingresar a la opci�n Edit.");
//		t.NavPricingEventsEDIT();
//
//		// Paso 5 - 7
//		addStep("Dirigirse a la parte inferior de la pantalla, " + "seleccionar la segunda opci�n: Add "
//				+ "y dar clic para agregar un nuevo evento."
//				+ "Seleccionar opci�n OK para aceptar la creaci�n del Evento.");
//		t.fillEvent();

		// Paso 8 - 9
		addStep("Para crear la Promoci�n dirigirse a la opci�n Pricing del men� Retek. "
				+ "Seleccionar e ingresar a la opci�n Promotions.");
		t.NavPricingPromotions();

		// Paso 10 - 17
		addStep("Dirigirse al combo box localizado en la parte media superior y seleccionar opci�n New."
				+ "Seleccionar opci�n OK."
				+ " Ingresamos el nombre de la promoci�n el cual no debe ser mayor a 32 d�gitos o caracteres."
				+ "Ingresar el Evento <<<evento>>> anteriormente creado en el campo Event (el cual contiene la vigencia de la promoci�n a configurar)."
				+ "Dar clic al bot�n Store y agregar la tienda donde se configurar� la promoci�n."
				+ "Presionar bot�n OK para aceptar la configuraci�n de la promoci�n."
				+ "Seleccionar� del combo box Group Type la opci�n� Store� ya que ser� para una sola tienda la prueba."
				+ "Una vez agregada la cobertura dar clic en Apply.");
		t.fillPromotions();

		// Paso 18
		addStep("Irse a la barra de herramientas de la perte superior y seleccionar la opci�n Options, "
				+ "posteriormente �Add �tems�� y elegir �Single Item�.");
		t.optionItems();

		// Paso 19 - 23
		addStep("En la siguiente pantalla agregar los c�digos de barra que estar�n en promoci�n en la columna �tem."
				+ "En la columna Change Type ingresar Fixed Price (prefio fijo)."
				+ "En la columna �Change Amount�� ingresar el precio promocional."
				+ "En la columna �Adjust Type�� ingresar None."
				+ "Dar clic en OK para regresar a la pantalla principal.");
		t.fillSingleItem();

		// Paso 24
		addStep("�Una vez en la pantalla inicial nos vamos a la barra de tareas superior en �Options�, y seleccionamos� �Submit�.");
		t.submitPrice();
		
		t.clic_btn_OK_U();
		
		t.close_RMS_2();
		

		String resultadoEjecucion;
		
		//Paso 25
		addStep("Ejecutar job pcovrlpq_chile en Control-M");

		resultadoEjecucion = job.ejecutar("pcovrlpq_chile");
		ejecucionCorrectaJob = job.fueEjecutadoCorrectamente(resultadoEjecucion);
		assertTrue(ejecucionCorrectaJob, "El pcovrlpq_chile no se ejecuto correctamente");
		
		
		t.loginEnRMSAfterJob();
		
		t.NavPricingPromotions();
		
		t.fillPromotionsEdit();
		
		// Paso 25
		addStep("Volver a la barra de tareas superior en �Options�� y seleccionar �Approved�.");
		t.approveRetailPrice();

		// Paso 26
		addStep("Una vez que pasa a estatus �Approved�, dar clic en OK para concluir con la creaci�n� promoci�n.");
		t.close_RMS_3();
		t.CloseBrowser();

		// Paso 27 - 28
		addStep("Ingresar a la BD de RMS RMS_CHILE."
				+ "Consultar la tabla PROMHEAD para validar que se haya registrado la promoci�n creada, ejecutar la siguiente consulta :"
				+ " SELECT * FROM wmuser.PROMHEAD WHERE PROMOTION=:PROMOTION;");

		cb = App.getClipboard();

		System.out.println(cb);

		String promotion = App.getClipboard();
		String query = String.format(Queries_Tiendas.tbl_wmuser_supp_promhead, promotion);
		System.out.println(query);
		System.out.println("conexion = " + dbRms.getConn().toString());
		executeQuery = executeQuery(dbRms, query);
		if (!executeQuery.isEmpty()) {
			testCase.addQueryEvidenceCurrentStep(executeQuery);
		}

		assertTrue(!executeQuery.isEmpty(), "el item fue encontrado");

		// Paso 29
		addStep("Ingresar a la BD de RMS RMS_CHILE."
				+ "Consultar la tabla PROMSTORE para validar que se muestre en enlace a la tienda que pertence, ejecutar la siguiente consulta:\r\n" +
				" SELECT * FROM wmuser.PROMSTORE WHERE PROMOTION=:PROMOTION;");

		query = String.format(Queries_Tiendas.tbl_wmuser_supp_promstore, promotion);
		System.out.println(query);
		System.out.println("conexion = " + dbRms.getConn().toString());
		executeQuery = executeQuery(dbRms, query);
		if (!executeQuery.isEmpty()) {
			testCase.addQueryEvidenceCurrentStep(executeQuery);
		}

		assertTrue(!executeQuery.isEmpty(), "el item fue encontrado");

	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_042_CrearPromocionSimpleyvalidarsuenvioalPOS_test";
	}

	@Override
	public String setTestDescription() {
		return "ATC_FT_042_CrearPromocionSimpleyvalidarsuenvioalPOS";
	}

	@Override
	public String setTestDesigner() {
		return "Equipo RMS";
	}

	@Override
	public String setTestInstanceID() {
		return null;
	}

	@Override
	public String setPrerequisites() {
		return null;
	}

	@Override
	public void beforeTest() {
	}

}
