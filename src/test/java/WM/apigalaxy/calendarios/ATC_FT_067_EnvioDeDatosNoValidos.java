package WM.apigalaxy.calendarios;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.testng.annotations.Test;

import WM.util.ApiGalaxyUtils;
import modelo.BaseExecution;

/**
 * DESCRIPCIÓN:Se envia un set de datos generados para probar cada campo en base
 * a su tipo de dato, en esta prueba se envian los escenarios no validos para
 * cada tipo de dato, el endpoint valida por registro por lo que no se ve la
 * necesidad de hacer una prueba aislada por cada campo, en esta validacion se
 * incluye la falta de envio de keys en algunos registros y el envio de valores
 * con null, se valida el response y se valida que no se genere ni un registro
 * en la db NOMBRE CASOS DE PRUEBA MANUAL QUE SE ABARCAN: MTC-FT-067-Envio de
 * datos no validos
 *
 * PREREQUISITOS: Acceso a la APi La API funcionando Acceso a snowflake Un set
 * de datos que no este en la DB
 *
 * INSUMOS: Post valido 74
 *
 * @author Guillermo Garcia
 * @date 12/05/2026
 */
public class ATC_FT_067_EnvioDeDatosNoValidos extends BaseExecution {
    @Test(dataProvider = "data-provider", enabled = true)
    public void ATC_FT_067_EnvioDeDatosNoValidos_test(HashMap<String, String> data) throws Exception {
	String ambiente = data.get("ambiente");
	ApiGalaxyUtils util = new ApiGalaxyUtils(ambiente, testCase);
	String testId = "74";
	final String EXPECTED_RESULT = "El response debe regresar un codigo 400, en el response el key code debe ser:X400000, el key message debe ser: The client has issued a malformed or illegal request.";
	final String FINAL_DATE = "finalDate";
	final String NO_ENVIAR = "no enviar";
	final String INITIAL_DATE = "initialDate";
	final String PERIODS_ID = "periodsId";
	final String PERIOD_NAME = "periodName";
	final String POSITION = "position";
	final String PROJECTION_PERCENTAGE = "projectionPercentage";
	final String SHORT_PERIOD = "isShortPeriods";
	final String YEAR_CALENDAR = "yearCalendar";
	final String ACT_NAME = "activityName";
	final String N_DAYS = "nDays";
	final String N_DATE = "nDate";
	
	/***************************************
	 * PASO 1 Utilizando el token generar un segundo token desde el endpoint de
	 * OAuth2
	 ***************************************/
	addStep("Utilizando el token generar un segundo token desde el endpoint de OAuth2",
		"Debes utilizar un api token");
	testCase.addCodeEvidenceCurrentStep("Se genera el token con exito");
	// Este paso ocurre dentro de las utils en el metodo que hace la llamada a la
	// api con el proposito que no expire el token por llamada

	/***************************************
	 * PASO 2 Utilizar el token generado como header para las siguiente accion.
	 ***************************************/
	addStep("Utilizar el token generado como header para las siguiente accion.", "Se debe utilizar un OAuth2");
	testCase.addCodeEvidenceCurrentStep("Se utiliza el token");
	// Se agregan estos dos pasos para que los stakeholders esten comodos sabiendo
	// que se utiliza seguridad en la API
	util.deletePromotionalCalendars(testId);

	/***************************************
	 * PASO 3 En snowflake enviar el siguiente querie: SELECT COUNT(1) FROM
	 * DBZDEV_MEX_OXXO.LAND_MARKETPLACE_CAT.XXFC_CALENDARIO_PROMO where periods_id =
	 * 74;
	 ***************************************/
	addStep("En snowflake enviar el siguiente querie:SELECT COUNT(1) FROM DBZDEV_MEX_OXXO.LAND_MARKETPLACE_CAT.XXFC_CALENDARIO_PROMO where periods_id = 74;",
		"Debe regresar 0");
	List<Map<String, Object>> responseQuerieOne = util.executeQuerySnowflake(util.querieCalendarCount(testId));
	testCase.addCodeEvidenceCurrentStep("El total de regsitros con el id 74 son: " + responseQuerieOne.size());
	long count = (long) responseQuerieOne.get(0).get("COUNT(1)");
	assertEquals(count, 0L);
	
	/***************************************
	 * PASO 4 Enviar el response con datos validos, modificando el campo finalDate
	 * con el valor 2026/01/28
	 ***************************************/
	addStep("Enviar el response con datos validos, modificando el campo   finalDate con el valor 2026/01/28",
		EXPECTED_RESULT);
	assertTrue(util.validatePromotionalCalendarsError(FINAL_DATE, "2026/01/28"));
	/***************************************
	 * PASO 5 Enviar el response con datos validos, modificando el campo finalDate
	 * con el valor 2026-1-28
	 ***************************************/
	addStep("Enviar el response con datos validos, modificando el campo   finalDate con el valor     2026-1-28",
		EXPECTED_RESULT);
	assertTrue(util.validatePromotionalCalendarsError(FINAL_DATE, "2026-1-28"));
	/***************************************
	 * PASO 6 Enviar el response con datos validos, modificando el campo finalDate
	 * con el valor 2026-13-01
	 ***************************************/
	addStep("Enviar el response con datos validos, modificando el campo   finalDate con el valor     2026-13-01",
		EXPECTED_RESULT);
	assertTrue(util.validatePromotionalCalendarsError(FINAL_DATE, "2026-13-01"));
	/***************************************
	 * PASO 7 Enviar el response con datos validos, modificando el campo finalDate
	 * con el valor texto
	 ***************************************/
	addStep("Enviar el response con datos validos, modificando el campo   finalDate con el valor     texto",
		EXPECTED_RESULT);
	assertTrue(util.validatePromotionalCalendarsError(FINAL_DATE, "texto"));
	/***************************************
	 * PASO 8 Enviar el response con datos validos, modificando el campo finalDate
	 * con el valor null
	 ***************************************/
	addStep("Enviar el response con datos validos, modificando el campo   finalDate con el valor     null         ",
		EXPECTED_RESULT);
	assertTrue(util.validatePromotionalCalendarsError(FINAL_DATE, null));
	/***************************************
	 * PASO 9 Enviar el response con datos validos, modificando el campo finalDate
	 * con el no enviar
	 ***************************************/
	addStep("Enviar el response con datos validos, modificando el campo   finalDate con el no enviar",
		EXPECTED_RESULT);
	assertTrue(util.validatePromotionalCalendarsError(FINAL_DATE, NO_ENVIAR));
	/***************************************
	 * PASO 10 Enviar el response con datos validos, modificando el campo
	 * initialDate con el valor 08-01-2026
	 ***************************************/
	addStep("Enviar el response con datos validos, modificando el campo   initialDate con el valor     08-01-2026",
		EXPECTED_RESULT);
	assertTrue(util.validatePromotionalCalendarsError(INITIAL_DATE, "08-01-2026"));
	/***************************************
	 * PASO 11 Enviar el response con datos validos, modificando el campo
	 * initialDate con el valor 20260108
	 ***************************************/
	addStep("Enviar el response con datos validos, modificando el campo   initialDate con el valor     20260108",
		EXPECTED_RESULT);
	assertTrue(util.validatePromotionalCalendarsError(INITIAL_DATE, "20260108"));
	/***************************************
	 * PASO 12 Enviar el response con datos validos, modificando el campo
	 * initialDate con el valor 2026-00-10
	 ***************************************/
	addStep("Enviar el response con datos validos, modificando el campo   initialDate con el valor     2026-00-10",
		EXPECTED_RESULT);
	assertTrue(util.validatePromotionalCalendarsError(INITIAL_DATE, "2026-00-10"));
	/***************************************
	 * PASO 13 Enviar el response con datos validos, modificando el campo
	 * initialDate con el valor
	 ***************************************/
	addStep("Enviar el response con datos validos, modificando el campo   initialDate con el valor     ",
		EXPECTED_RESULT);
	assertTrue(util.validatePromotionalCalendarsError(INITIAL_DATE, ""));
	/***************************************
	 * PASO 14 Enviar el response con datos validos, modificando el campo
	 * initialDate con el valor null
	 ***************************************/
	addStep("Enviar el response con datos validos, modificando el campo   initialDate con el valor     null",
		EXPECTED_RESULT);
	assertTrue(util.validatePromotionalCalendarsError(INITIAL_DATE, null));
	/***************************************
	 * PASO 15 Enviar el response con datos validos, modificando el campo
	 * initialDate con el no enviar
	 ***************************************/
	addStep("Enviar el response con datos validos, modificando el campo   initialDate con el no enviar",
		EXPECTED_RESULT);
	assertTrue(util.validatePromotionalCalendarsError(INITIAL_DATE, NO_ENVIAR));
	/***************************************
	 * PASO 16 Enviar el response con datos validos, modificando el campo periodsId
	 * con el valor abc
	 ***************************************/
	addStep("Enviar el response con datos validos, modificando el campo   periodsId con el valor     abc",
		EXPECTED_RESULT);
	assertTrue(util.validatePromotionalCalendarsError(PERIODS_ID, "abc"));
	/***************************************
	 * PASO 17 Enviar el response con datos validos, modificando el campo periodsId
	 * con el valor 12345678901
	 ***************************************/
	addStep("Enviar el response con datos validos, modificando el campo   periodsId con el valor 12345678901",
		EXPECTED_RESULT);
	assertTrue(util.validatePromotionalCalendarsError(PERIODS_ID, 12345678901L));
	/***************************************
	 * PASO 18 Enviar el response con datos validos, modificando el campo periodsId
	 * con el valor -1
	 ***************************************/
	addStep("Enviar el response con datos validos, modificando el campo   periodsId con el valor -1",
		EXPECTED_RESULT);
	assertTrue(util.validatePromotionalCalendarsError(PERIODS_ID, -1));
	/***************************************
	 * PASO 19 Enviar el response con datos validos, modificando el campo periodsId
	 * con el valor 1.5
	 ***************************************/
	addStep("Enviar el response con datos validos, modificando el campo   periodsId con el valor 1.5",
		EXPECTED_RESULT);
	assertTrue(util.validatePromotionalCalendarsError(PERIODS_ID, 1.5));
	/***************************************
	 * PASO 20 Enviar el response con datos validos, modificando el campo periodsId
	 * con el valor null
	 ***************************************/
	addStep("Enviar el response con datos validos, modificando el campo   periodsId con el valor     null",
		EXPECTED_RESULT);
	assertTrue(util.validatePromotionalCalendarsError(PERIODS_ID, null));
	/***************************************
	 * PASO 21 Enviar el response con datos validos, modificando el campo periodsId
	 * con el no enviar
	 ***************************************/
	addStep("Enviar el response con datos validos, modificando el campo   periodsId con el no enviar",
		EXPECTED_RESULT);
	assertTrue(util.validatePromotionalCalendarsError(PERIODS_ID, NO_ENVIAR));
	/***************************************
	 * PASO 22 Enviar el response con datos validos, modificando el campo periodName
	 * con el valor
	 ***************************************/
	addStep("Enviar el response con datos validos, modificando el campo   periodName con el valor     ",
		EXPECTED_RESULT);
	assertTrue(util.validatePromotionalCalendarsError(PERIOD_NAME, ""));
	/***************************************
	 * PASO 23 Enviar el response con datos validos, modificando el campo periodName
	 * con el valor A.repeat(201)
	 ***************************************/
	addStep("Enviar el response con datos validos, modificando el campo   periodName con el valor     A.repeat(201)",
		EXPECTED_RESULT);
	assertTrue(util.validatePromotionalCalendarsError(PERIOD_NAME,
		"AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA"));
	/***************************************
	 * PASO 24 Enviar el response con datos validos, modificando el campo periodName
	 * con el valor 123
	 ***************************************/
	addStep("Enviar el response con datos validos, modificando el campo   periodName con el valor 123",
		EXPECTED_RESULT);
	assertTrue(util.validatePromotionalCalendarsError(PERIOD_NAME, 123));
	/***************************************
	 * PASO 25 Enviar el response con datos validos, modificando el campo periodName
	 * con el valor null
	 ***************************************/
	addStep("Enviar el response con datos validos, modificando el campo   periodName con el valor     null",
		EXPECTED_RESULT);
	assertTrue(util.validatePromotionalCalendarsError(PERIOD_NAME, null));
	/***************************************
	 * PASO 26 Enviar el response con datos validos, modificando el campo periodName
	 * con el no enviar
	 ***************************************/
	addStep("Enviar el response con datos validos, modificando el campo   periodName con el no enviar",
		EXPECTED_RESULT);
	assertTrue(util.validatePromotionalCalendarsError(PERIOD_NAME, NO_ENVIAR));
	/***************************************
	 * PASO 27 Enviar el response con datos validos, modificando el campo periodName
	 * con el valor '; DROP TABLE XXFC_CALENDARIO_PROMO; --
	 ***************************************/
	addStep("Enviar el response con datos validos, modificando el campo   periodName con el valor '; DROP TABLE XXFC_CALENDARIO_PROMO; --",
		EXPECTED_RESULT);
	assertTrue(util.validatePromotionalCalendarsError(PERIOD_NAME, null));
	/***************************************
	 * PASO 28 Enviar el response con datos validos, modificando el campo periodName
	 * con el valor \ OR \1\=\1
	 ***************************************/
	addStep("Enviar el response con datos validos, modificando el campo periodName con el valor \\ OR \\1\\=\\1 ",
		EXPECTED_RESULT);
	assertTrue(util.validatePromotionalCalendarsError(PERIOD_NAME, null));
	/***************************************
	 * PASO 29 Enviar el response con datos validos, modificando el campo position
	 * con el valor uno
	 ***************************************/
	addStep("Enviar el response con datos validos, modificando el campo   position con el valor     uno",
		EXPECTED_RESULT);
	assertTrue(util.validatePromotionalCalendarsError(POSITION, "uno"));
	/***************************************
	 * PASO 30 Enviar el response con datos validos, modificando el campo position
	 * con el valor 12345
	 ***************************************/
	addStep("Enviar el response con datos validos, modificando el campo   position con el valor 12345",
		EXPECTED_RESULT);
	assertTrue(util.validatePromotionalCalendarsError(POSITION, 12345));
	/***************************************
	 * PASO 31 Enviar el response con datos validos, modificando el campo position
	 * con el valor -1
	 ***************************************/
	addStep("Enviar el response con datos validos, modificando el campo   position con el valor -1",
		EXPECTED_RESULT);
	assertTrue(util.validatePromotionalCalendarsError(POSITION, -1));
	/***************************************
	 * PASO 32 Enviar el response con datos validos, modificando el campo position
	 * con el valor 1.5
	 ***************************************/
	addStep("Enviar el response con datos validos, modificando el campo   position con el valor 1.5",
		EXPECTED_RESULT);
	assertTrue(util.validatePromotionalCalendarsError(POSITION, 1.5));
	/***************************************
	 * PASO 33 Enviar el response con datos validos, modificando el campo position
	 * con el valor null
	 ***************************************/
	addStep("Enviar el response con datos validos, modificando el campo   position con el valor     null",
		EXPECTED_RESULT);
	assertTrue(util.validatePromotionalCalendarsError(POSITION, null));
	/***************************************
	 * PASO 34 Enviar el response con datos validos, modificando el campo position
	 * con el no enviar
	 ***************************************/
	addStep("Enviar el response con datos validos, modificando el campo   position con el no enviar",
		EXPECTED_RESULT);
	assertTrue(util.validatePromotionalCalendarsError(POSITION, NO_ENVIAR));
	/***************************************
	 * PASO 35 Enviar el response con datos validos, modificando el campo
	 * projectionPercentage con el valor cinco
	 ***************************************/
	addStep("Enviar el response con datos validos, modificando el campo   projectionPercentage con el valor     cinco",
		EXPECTED_RESULT);
	assertTrue(util.validatePromotionalCalendarsError(PROJECTION_PERCENTAGE, "cinco"));
	/***************************************
	 * PASO 36 Enviar el response con datos validos, modificando el campo
	 * projectionPercentage con el valor 123456
	 ***************************************/
	addStep("Enviar el response con datos validos, modificando el campo   projectionPercentage con el valor 123456",
		EXPECTED_RESULT);
	assertTrue(util.validatePromotionalCalendarsError(PROJECTION_PERCENTAGE, 123456));
	/***************************************
	 * PASO 37 Enviar el response con datos validos, modificando el campo
	 * projectionPercentage con el valor -10
	 ***************************************/
	addStep("Enviar el response con datos validos, modificando el campo   projectionPercentage con el valor -10",
		EXPECTED_RESULT);
	assertTrue(util.validatePromotionalCalendarsError(PROJECTION_PERCENTAGE, -10));
	/***************************************
	 * PASO 38 Enviar el response con datos validos, modificando el campo
	 * projectionPercentage con el valor 10.5
	 ***************************************/
	addStep("Enviar el response con datos validos, modificando el campo   projectionPercentage con el valor 10.5",
		EXPECTED_RESULT);
	assertTrue(util.validatePromotionalCalendarsError(PROJECTION_PERCENTAGE, 10.5));
	/***************************************
	 * PASO 39 Enviar el response con datos validos, modificando el campo
	 * projectionPercentage con el valor null
	 ***************************************/
	addStep("Enviar el response con datos validos, modificando el campo   projectionPercentage con el valor     null",
		EXPECTED_RESULT);
	assertTrue(util.validatePromotionalCalendarsError(PROJECTION_PERCENTAGE, null));
	/***************************************
	 * PASO 40 Enviar el response con datos validos, modificando el campo
	 * projectionPercentage con el no enviar
	 ***************************************/
	addStep("Enviar el response con datos validos, modificando el campo   projectionPercentage con el no enviar",
		EXPECTED_RESULT);
	assertTrue(util.validatePromotionalCalendarsError(PROJECTION_PERCENTAGE, NO_ENVIAR));
	/***************************************
	 * PASO 41 Enviar el response con datos validos, modificando el campo
	 * isShortPeriods con el valor yes
	 ***************************************/
	addStep("Enviar el response con datos validos, modificando el campo   isShortPeriods con el valor     yes",
		EXPECTED_RESULT);
	assertTrue(util.validatePromotionalCalendarsError(SHORT_PERIOD, "yes"));
	/***************************************
	 * PASO 42 Enviar el response con datos validos, modificando el campo
	 * isShortPeriods con el valor 0
	 ***************************************/
	addStep("Enviar el response con datos validos, modificando el campo   isShortPeriods con el valor     0",
		EXPECTED_RESULT);
	assertTrue(util.validatePromotionalCalendarsError(SHORT_PERIOD, "0"));
	/***************************************
	 * PASO 43 Enviar el response con datos validos, modificando el campo
	 * isShortPeriods con el valor 1
	 ***************************************/
	addStep("Enviar el response con datos validos, modificando el campo   isShortPeriods con el valor 1",
		EXPECTED_RESULT);
	assertTrue(util.validatePromotionalCalendarsError(SHORT_PERIOD, 1));
	/***************************************
	 * PASO 44 Enviar el response con datos validos, modificando el campo
	 * isShortPeriods con el valor null
	 ***************************************/
	addStep("Enviar el response con datos validos, modificando el campo   isShortPeriods con el valor     null",
		EXPECTED_RESULT);
	assertTrue(util.validatePromotionalCalendarsError(SHORT_PERIOD, null));
	/***************************************
	 * PASO 45 Enviar el response con datos validos, modificando el campo
	 * isShortPeriods con el no enviar
	 ***************************************/
	addStep("Enviar el response con datos validos, modificando el campo   isShortPeriods con el no enviar",
		EXPECTED_RESULT);
	assertTrue(util.validatePromotionalCalendarsError(SHORT_PERIOD, NO_ENVIAR));
	/***************************************
	 * PASO 46 Enviar el response con datos validos, modificando el campo
	 * yearCalendar con el valor 202A
	 ***************************************/
	addStep("Enviar el response con datos validos, modificando el campo   yearCalendar con el valor     202A",
		EXPECTED_RESULT);
	assertTrue(util.validatePromotionalCalendarsError(YEAR_CALENDAR, "202A"));
	/***************************************
	 * PASO 47 Enviar el response con datos validos, modificando el campo
	 * yearCalendar con el valor 123
	 ***************************************/
	addStep("Enviar el response con datos validos, modificando el campo   yearCalendar con el valor 123",
		EXPECTED_RESULT);
	assertTrue(util.validatePromotionalCalendarsError(YEAR_CALENDAR, 123));
	/***************************************
	 * PASO 48 Enviar el response con datos validos, modificando el campo
	 * yearCalendar con el valor 12345
	 ***************************************/
	addStep("Enviar el response con datos validos, modificando el campo   yearCalendar con el valor 12345",
		EXPECTED_RESULT);
	assertTrue(util.validatePromotionalCalendarsError(YEAR_CALENDAR, 12345));
	/***************************************
	 * PASO 49 Enviar el response con datos validos, modificando el campo
	 * yearCalendar con el valor -2026
	 ***************************************/
	addStep("Enviar el response con datos validos, modificando el campo   yearCalendar con el valor -2026",
		EXPECTED_RESULT);
	assertTrue(util.validatePromotionalCalendarsError(YEAR_CALENDAR, -2026));
	/***************************************
	 * PASO 50 Enviar el response con datos validos, modificando el campo
	 * yearCalendar con el valor null
	 ***************************************/
	addStep("Enviar el response con datos validos, modificando el campo   yearCalendar con el valor     null",
		EXPECTED_RESULT);
	assertTrue(util.validatePromotionalCalendarsError(YEAR_CALENDAR, null));
	/***************************************
	 * PASO 51 Enviar el response con datos validos, modificando el campo
	 * yearCalendar con el no enviar
	 ***************************************/
	addStep("Enviar el response con datos validos, modificando el campo   yearCalendar con el no enviar",
		EXPECTED_RESULT);
	assertTrue(util.validatePromotionalCalendarsError(YEAR_CALENDAR, NO_ENVIAR));
	/***************************************
	 * PASO 52 Enviar el response con datos validos, modificando el campo type con
	 * el valor
	 ***************************************/
	addStep("Enviar el response con datos validos, modificando el campo   type con el valor     ", EXPECTED_RESULT);
	assertTrue(util.validatePromotionalCalendarsError("type", ""));
	/***************************************
	 * PASO 53 Enviar el response con datos validos, modificando el campo type con
	 * el valor A.repeat(51)
	 ***************************************/
	addStep("Enviar el response con datos validos, modificando el campo   type con el valor     A.repeat(51)",
		EXPECTED_RESULT);
	assertTrue(util.validatePromotionalCalendarsError("type", "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA"));
	/***************************************
	 * PASO 54 Enviar el response con datos validos, modificando el campo type con
	 * el valor 123
	 ***************************************/
	addStep("Enviar el response con datos validos, modificando el campo   type con el valor 123", EXPECTED_RESULT);
	assertTrue(util.validatePromotionalCalendarsError("type", 123));
	/***************************************
	 * PASO 55 Enviar el response con datos validos, modificando el campo type con
	 * el valor null
	 ***************************************/
	addStep("Enviar el response con datos validos, modificando el campo   type con el valor     null",
		EXPECTED_RESULT);
	assertTrue(util.validatePromotionalCalendarsError("type", null));
	/***************************************
	 * PASO 56 Enviar el response con datos validos, modificando el campo type con
	 * el no enviar
	 ***************************************/
	addStep("Enviar el response con datos validos, modificando el campo   type con el no enviar", EXPECTED_RESULT);
	assertTrue(util.validatePromotionalCalendarsError("type", NO_ENVIAR));
	/***************************************
	 * PASO 57 Enviar el response con datos validos, modificando el campo
	 * activityName con el valor
	 ***************************************/
	addStep("Enviar el response con datos validos, modificando el campo   activityName con el valor     ",
		EXPECTED_RESULT);
	assertTrue(util.validatePromotionalCalendarsError(ACT_NAME, ""));
	/***************************************
	 * PASO 58 Enviar el response con datos validos, modificando el campo
	 * activityName con el valor A.repeat(601)
	 ***************************************/
	addStep("Enviar el response con datos validos, modificando el campo   activityName con el valor     A.repeat(601)",
		EXPECTED_RESULT);
	assertTrue(util.validatePromotionalCalendarsError(ACT_NAME,
		"AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA"));
	/***************************************
	 * PASO 59 Enviar el response con datos validos, modificando el campo
	 * activityName con el valor 12345
	 ***************************************/
	addStep("Enviar el response con datos validos, modificando el campo   activityName con el valor 12345",
		EXPECTED_RESULT);
	assertTrue(util.validatePromotionalCalendarsError(ACT_NAME, 12345));
	/***************************************
	 * PASO 60 Enviar el response con datos validos, modificando el campo
	 * activityName con el valor null
	 ***************************************/
	addStep("Enviar el response con datos validos, modificando el campo   activityName con el valor     null",
		EXPECTED_RESULT);
	assertTrue(util.validatePromotionalCalendarsError(ACT_NAME, null));
	/***************************************
	 * PASO 61 Enviar el response con datos validos, modificando el campo
	 * activityName con el no enviar
	 ***************************************/
	addStep("Enviar el response con datos validos, modificando el campo   activityName con el no enviar",
		EXPECTED_RESULT);
	assertTrue(util.validatePromotionalCalendarsError(ACT_NAME, NO_ENVIAR));
	/***************************************
	 * PASO 62 Enviar el response con datos validos, modificando el campo
	 * activityName con el valor '; DROP TABLE XXFC_CALENDARIO_PROMO; --
	 ***************************************/
	addStep("Enviar el response con datos validos, modificando el campo   activityName con el valor '; DROP TABLE XXFC_CALENDARIO_PROMO; --",
		EXPECTED_RESULT);
	assertTrue(util.validatePromotionalCalendarsError(ACT_NAME, null));
	/***************************************
	 * PASO 63 Enviar el response con datos validos, modificando el campo
	 * activityName con el valor \ OR \1\=\1
	 ***************************************/
	addStep("Enviar el response con datos validos, modificando el campo periodName con el valor \\ OR \\1\\=\\1 ",
		EXPECTED_RESULT);
	assertTrue(util.validatePromotionalCalendarsError(YEAR_CALENDAR, null));
	/***************************************
	 * PASO 64 Enviar el response con datos validos, modificando el campo nDays con
	 * el valor -367
	 ***************************************/
	addStep("Enviar el response con datos validos, modificando el campo   nDays con el valor -367",
		EXPECTED_RESULT);
	assertTrue(util.validatePromotionalCalendarsError(N_DAYS, -367));
	/***************************************
	 * PASO 65 Enviar el response con datos validos, modificando el campo nDays con
	 * el valor 367
	 ***************************************/
	addStep("Enviar el response con datos validos, modificando el campo   nDays con el valor 367", EXPECTED_RESULT);
	assertTrue(util.validatePromotionalCalendarsError(N_DAYS, 367));
	/***************************************
	 * PASO 66 Enviar el response con datos validos, modificando el campo nDays con
	 * el valor treinta
	 ***************************************/
	addStep("Enviar el response con datos validos, modificando el campo   nDays con el valor     treinta",
		EXPECTED_RESULT);
	assertTrue(util.validatePromotionalCalendarsError(N_DAYS, "treinta"));
	/***************************************
	 * PASO 67 Enviar el response con datos validos, modificando el campo nDays con
	 * el valor 1.5
	 ***************************************/
	addStep("Enviar el response con datos validos, modificando el campo   nDays con el valor 1.5", EXPECTED_RESULT);
	assertTrue(util.validatePromotionalCalendarsError(N_DAYS, 1.5));
	/***************************************
	 * PASO 68 Enviar el response con datos validos, modificando el campo nDays con
	 * el valor null
	 ***************************************/
	addStep("Enviar el response con datos validos, modificando el campo   nDays con el valor     null",
		EXPECTED_RESULT);
	assertTrue(util.validatePromotionalCalendarsError(N_DAYS, null));
	/***************************************
	 * PASO 69 Enviar el response con datos validos, modificando el campo nDays con
	 * el no enviar
	 ***************************************/
	addStep("Enviar el response con datos validos, modificando el campo   nDays con el no enviar", EXPECTED_RESULT);
	assertTrue(util.validatePromotionalCalendarsError(N_DAYS, NO_ENVIAR));
	/***************************************
	 * PASO 70 Enviar el response con datos validos, modificando el campo nDate con
	 * el valor 2025/12/01
	 ***************************************/
	addStep("Enviar el response con datos validos, modificando el campo   nDate con el valor     2025/12/01",
		EXPECTED_RESULT);
	assertTrue(util.validatePromotionalCalendarsError(N_DATE, "2025/12/01"));
	/***************************************
	 * PASO 71 Enviar el response con datos validos, modificando el campo nDate con
	 * el valor 2025-12-1
	 ***************************************/
	addStep("Enviar el response con datos validos, modificando el campo   nDate con el valor     2025-12-1",
		EXPECTED_RESULT);
	assertTrue(util.validatePromotionalCalendarsError(N_DATE, "2025-12-1"));
	/***************************************
	 * PASO 72 Enviar el response con datos validos, modificando el campo nDate con
	 * el valor 2025-99-01
	 ***************************************/
	addStep("Enviar el response con datos validos, modificando el campo   nDate con el valor     2025-99-01",
		EXPECTED_RESULT);
	assertTrue(util.validatePromotionalCalendarsError(N_DATE, "2025-99-01"));
	/***************************************
	 * PASO 73 Enviar el response con datos validos, modificando el campo nDate con
	 * el valor
	 ***************************************/
	addStep("Enviar el response con datos validos, modificando el campo   nDate con el valor     ",
		EXPECTED_RESULT);
	assertTrue(util.validatePromotionalCalendarsError(N_DATE, ""));
	/***************************************
	 * PASO 74 Enviar el response con datos validos, modificando el campo nDate con
	 * el valor null
	 ***************************************/
	addStep("Enviar el response con datos validos, modificando el campo   nDate con el valor     null",
		EXPECTED_RESULT);
	assertTrue(util.validatePromotionalCalendarsError(N_DATE, null));
	/***************************************
	 * PASO 75 Enviar el response con datos validos, modificando el campo nDate con
	 * el no enviar
	 ***************************************/
	addStep("Enviar el response con datos validos, modificando el campo   nDate con el no enviar", EXPECTED_RESULT);
	assertTrue(util.validatePromotionalCalendarsError(N_DATE, NO_ENVIAR));
	/***************************************
	 * PASO 76 En snowflake enviar el siguiente querie: SELECT COUNT(1) FROM
	 * DBZDEV_MEX_OXXO.LAND_MARKETPLACE_CAT.XXFC_CALENDARIO_PROMO where periods_id =
	 * 74;
	 ***************************************/
	addStep("En snowflake enviar el siguiente querie:SELECT COUNT(1) FROM DBZDEV_MEX_OXXO.LAND_MARKETPLACE_CAT.XXFC_CALENDARIO_PROMO where periods_id = 74;",
		"Debe regresar 0");
	List<Map<String, Object>> responseQuerieFinal = util.executeQuerySnowflake(util.querieCalendarCount(testId));
	testCase.addCodeEvidenceCurrentStep("El total de regsitros con el id 74 son: " + responseQuerieFinal.size());
	assertEquals(responseQuerieFinal.size(), 0);

    }

    @Override
    public String setTestFullName() {
	return "ATC_FT_067_EnvioDeDatosNoValidos";
    }

    @Override
    public String setTestDescription() {
	return "Se envia un set de datos generados para probar cada campo en base a su tipo de dato, en esta prueba se envian los escenarios no validos para cada tipo de dato, el endpoint valida por registro por lo que no se ve la necesidad de hacer una prueba aislada por cada campo, en esta validacion se incluye la falta de envio de keys en algunos registros y el envio de valores con null, se valida el response y se valida que no se genere ni un registro en la db";
    }

    @Override
    public String setTestDesigner() {
	return "Guillermo Garcia";
    }

    @Override
    public String setTestInstanceID() {
	return "1";
    }

    @Override
    public String setPrerequisites() {
	return "Acceso a la API\r\n" + "La API funcionando\r\n" + "Acceso a snowflake\r\n"
		+ "Un set de datos que no este en la DB\r\n" + "";

    }

    @Override
    public void beforeTest() {

    }
}
