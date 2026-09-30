package WM.apigalaxy.folios;

import static org.testng.Assert.assertTrue;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.testng.annotations.Test;

import WM.util.ApiGalaxyUtils;
import io.restassured.response.Response;
import modelo.BaseExecution;

/**
 * DESCRIPCIÓN: Se valida el envio de get sin parametros, el paginado, el
 * funcionamiento del filtro de fecha, del filtro de id, del filtro que decide
 * si retorna el ultimo y del tamano de la pagina.
 *
 * NOMBRE CASOS DE PRUEBA MANUAL QUE SE ABARCAN: -MTC-FT-043-Validacion
 * funcionalidad de parametros endpoint agreement/folios
 *
 * PREREQUISITOS: - Acceso a la API - Acceso a Snowflake - Informacion Valida en
 * las tablas de Snowflake - La API funcionando
 *
 * INSUMOS: - totalCount, es un valor calculador basado en las respuestas de los
 * dos primeros queries que se envian, pageMaxima, es un valor calculado
 * dependiendo del totalCount idUnica, valor obtenido por querie en el paso 2
 * idCon2registros, valor obtenido por querie en el paso 2 idNoExistente, enviar
 * un id no encontrado en el querie del paso 2 fechaNoExistente, enviar una
 * fecha futura
 * 
 * @author Guillermo garcia
 * @date 29/05/2026
 */
public class ATC_FT_043_ValidacionFuncionalidadDeParametrosEndpointAgreementFolios extends BaseExecution {

    @Test(dataProvider = "data-provider", enabled = true)
    public void ATC_FT_043_ValidacionFuncionalidadDeParametrosEndpointAgreementFolios_test(HashMap<String, String> data)
	    throws Exception {
	/*****************************
	 * Instancias
	 **************************************************/
	String ambiente = data.get("ambiente");
	ApiGalaxyUtils util = new ApiGalaxyUtils(ambiente, testCase);

	/***************************************
	 * PASO 1 Hacer conexion a Snowflake
	 ***************************************/
	addStep("Hacer conexion a Snowflake", "Se debe poder hacer conexion");
	testCase.addCodeEvidenceCurrentStep("Se puede hacer conexion");
	String querieTodo = "SELECT * FROM DBZDEV_MEX_OXXO.CONS_MKT_PROMOCIONES.OUT_260_MKT_CONVENIOS;";
	String querieIdUnico = "SELECT ID_MARKETPLACE "
		+ "FROM DBZDEV_MEX_OXXO.CONS_MKT_PROMOCIONES.OUT_260_MKT_CONVENIOS " + "GROUP BY ID_MARKETPLACE "
		+ "HAVING COUNT(*) = 1 " + "LIMIT 1;";
	String querieIdMultiple = "SELECT ID_MARKETPLACE, " + "COUNT(*) AS TOTAL_REGISTROS "
		+ "FROM DBZDEV_MEX_OXXO.CONS_MKT_PROMOCIONES.OUT_260_MKT_CONVENIOS " + "GROUP BY ID_MARKETPLACE "
		+ "HAVING COUNT(*) > 1 " + "ORDER BY TOTAL_REGISTROS DESC " + "LIMIT 1;";
	String querieCountIdUnico = "SELECT COUNT(DISTINCT ID_MARKETPLACE) AS TOTAL_MARKETPLACES "
		+ "FROM DBZDEV_MEX_OXXO.CONS_MKT_PROMOCIONES.OUT_260_MKT_CONVENIOS;";
	final String NO_ENVIAR = "no enviar";
	final String FECHA = "1998-01-01";
	final String FECHA_NEG = "2998-01-01";
	final String VALORES = "valores";
	final String VACIO = "vacio";
	final String FALSO = "false";
	Response response;
	/***************************************
	 * PASO 2 Mandar querie para obtener el total de registros de la DB
	 ***************************************/
	addStep("Mandar querie para obtener el total de registros de la DB", "Almacenar la respuesta");
	List<Map<String, Object>> responseQuerieTodo = util.executeQuerySnowflake(querieTodo);
	int totalCount = responseQuerieTodo.size();
	testCase.addCodeEvidenceCurrentStep("El total de regsitros son: " + totalCount);

	/***************************************
	 * PASO 3 Mandar querie para obtener un id con mas de 1 registro y la cantidad
	 * de registros que tiene y un id unico
	 ***************************************/
	addStep("Mandar querie para obtener un id con mas de 1 registro y la cantidad de registros que tiene y un id unico",
		"Almacenar la respuesta");
	List<Map<String, Object>> responseQuerieUnico = util.executeQuerySnowflake(querieIdUnico);
	int idUnico = ((Long) responseQuerieUnico.get(0).get("ID_MARKETPLACE")).intValue();
	responseQuerieUnico = util.executeQuerySnowflake(querieCountIdUnico);
	int totalUnico = ((Long) responseQuerieUnico.get(0).get("TOTAL_MARKETPLACES")).intValue();
	List<Map<String, Object>> responseQuerieMulti = util.executeQuerySnowflake(querieIdMultiple);
	int idMulti = ((Long) responseQuerieMulti.get(0).get("ID_MARKETPLACE")).intValue();
	int cantidadMulti = ((Long) responseQuerieMulti.get(0).get("TOTAL_REGISTROS")).intValue();

	/***************************************
	 * PASO 4 Enviar una consulta a la API con los siguientes valores:page =
	 * 1pageSize = 1createdDate = 1998-01-01marketPlaceId = no enviarreturnLatest =
	 * no enviary validar los valores del response
	 ***************************************/
	addStep("Enviar una consulta a la API con los siguientes valores:page = 1pageSize = 1createdDate = 1998-01-01marketPlaceId = no enviarreturnLatest = no enviary validar los valores del response",
		"Los valores del response deben ser:page: 1pageSize: 1totalItems: <totalCount>totalPages: <totalCount>data: Array con valores");
	response = util.getAgreementFolios(0, 1, 1, FECHA, NO_ENVIAR);
	assertTrue(util.validateMetadataAgreements(response, 1, 1, totalCount, totalCount, VALORES));

	/***************************************
	 * PASO 5 Enviar una consulta a la API con los siguientes valores:page =
	 * 2pageSize = 1createdDate = 1998-01-01marketPlaceId = no enviarreturnLatest =
	 * no enviary validar los valores del response
	 ***************************************/
	addStep("Enviar una consulta a la API con los siguientes valores:page = 2pageSize = 1createdDate = 1998-01-01marketPlaceId = no enviarreturnLatest = no enviary validar los valores del response",
		"Los valores del response deben ser:page: 2pageSize: 1totalItems: <totalCount>totalPages: <totalCount>data: Array con valores");
	response = util.getAgreementFolios(0, 2, 1, FECHA, NO_ENVIAR);
	assertTrue(util.validateMetadataAgreements(response, 2, 1, totalCount, totalCount, VALORES));

	/***************************************
	 * PASO 6 Enviar una consulta a la API con los siguientes valores:page =
	 * <pageMaxima>pageSize = 1createdDate = 1998-01-01marketPlaceId = no
	 * enviarreturnLatest = no enviary validar los valores del response
	 ***************************************/
	addStep("Enviar una consulta a la API con los siguientes valores:page = <pageMaxima>pageSize = 1createdDate = 1998-01-01marketPlaceId = no enviarreturnLatest = no enviary validar los valores del response",
		"Los valores del response deben ser:page: <pageMaxima>pageSize: 1totalItems: <totalCount>totalPages: <totalCount>data: Array con valores");
	response = util.getAgreementFolios(0, totalCount, 1, FECHA, NO_ENVIAR);
	assertTrue(util.validateMetadataAgreements(response, totalCount, 1, totalCount, totalCount, VALORES));

	/***************************************
	 * PASO 7 Enviar una consulta a la API con los siguientes valores:page =
	 * 1pageSize = no enviarcreatedDate = 1998-01-01marketPlaceId =
	 * <idNoExistente>returnLatest = truey validar los valores del response
	 ***************************************/
	addStep("Enviar una consulta a la API con los siguientes valores:page = 1pageSize = no enviarcreatedDate = 1998-01-01marketPlaceId = <idNoExistente>returnLatest = truey validar los valores del response",
		"Los valores del response deben ser:page: 1pageSize: 1000totalItems: 0totalPages: 0data: Array vacio");
	response = util.getAgreementFolios(708888888, 1, 0, FECHA, "true");
	assertTrue(util.validateMetadataAgreements(response, 1, 1000, 0, 0, VACIO));

	/***************************************
	 * PASO 8 Enviar una consulta a la API con los siguientes valores:page =
	 * 1pageSize = 1createdDate = 1998-01-01marketPlaceId = <idUnico>returnLatest =
	 * truey validar los valores del response
	 ***************************************/
	addStep("Enviar una consulta a la API con los siguientes valores:page = 1pageSize = 1createdDate = 1998-01-01marketPlaceId = <idUnico>returnLatest = truey validar los valores del response",
		"Los valores del response deben ser:page: 1pageSize: 1totalItems: 1totalPages: 1data: Array con valores");
	response = util.getAgreementFolios(idUnico, 1, 1, FECHA, "true");
	assertTrue(util.validateMetadataAgreements(response, 1, 1, 1, 1, VALORES));

	/***************************************
	 * PASO 9 Enviar una consulta a la API con los siguientes valores:page =
	 * 1pageSize = 1000createdDate = 1998-01-01marketPlaceId = no enviarreturnLatest
	 * = falsey validar los valores del response
	 ***************************************/
	addStep("Enviar una consulta a la API con los siguientes valores:page = 1pageSize = 1000createdDate = 1998-01-01marketPlaceId = no enviarreturnLatest = falsey validar los valores del response",
		"Los valores del response deben ser:page: 1pageSize: 1000totalItems: <totalCount>totalPages: <totalCount/1000>data: Array con valores");
	response = util.getAgreementFolios(0, 1, 1000, FECHA, NO_ENVIAR);
	assertTrue(util.validateMetadataAgreements(response, 1, 1000, totalCount, 2, VALORES));

	/***************************************
	 * PASO 10 Enviar una consulta a la API con los siguientes valores:page =
	 * 1pageSize = 2000createdDate = no enviarmarketPlaceId =
	 * <IdCon2Registros>returnLatest = no enviary validar los valores del response
	 ***************************************/
	addStep("Enviar una consulta a la API con los siguientes valores:page = 1pageSize = 2000createdDate = no enviarmarketPlaceId = <IdCon2Registros>returnLatest = no enviary validar los valores del response",
		"Los valores del response deben ser:page: 1pageSize: 2000totalItems: <IdCount>totalPages: <totalCount/2000>data: Array con valores");
	response = util.getAgreementFolios(idMulti, 1, 2000, NO_ENVIAR, NO_ENVIAR);
	assertTrue(util.validateMetadataAgreements(response, 1, 2000, cantidadMulti, util.divisionRounded(totalCount, 2000),
		VALORES));

	/***************************************
	 * PASO 11 Enviar una consulta a la API con los siguientes valores:page =
	 * <pageMaxima>pageSize = 1createdDate = no enviarmarketPlaceId = no
	 * enviarreturnLatest = falsey validar los valores del response
	 ***************************************/
	addStep("Enviar una consulta a la API con los siguientes valores:page = <pageMaxima>pageSize = 1createdDate = no enviarmarketPlaceId = no enviarreturnLatest = falsey validar los valores del response",
		"Los valores del response deben ser:page: <pageMaxima>pageSize: 1totalItems: <totalCount>totalPages: <totalCount>data: Array con valores");
	response = util.getAgreementFolios(0, totalUnico, 1, NO_ENVIAR, FALSO);
	assertTrue(util.validateMetadataAgreements(response, totalUnico, 1, totalUnico, totalUnico, VALORES));

	/***************************************
	 * PASO 12 Enviar una consulta a la API con los siguientes valores:page =
	 * <pageMaxima>pageSize = 1000createdDate = <fechaNoValida>marketPlaceId =
	 * <idUnico>returnLatest = truey validar los valores del response
	 ***************************************/
	addStep("Enviar una consulta a la API con los siguientes valores:page = <pageMaxima>pageSize = 1000createdDate = <fechaNoValida>marketPlaceId = <idUnico>returnLatest = truey validar los valores del response",
		"Los valores del response deben ser:page: <pageMaxima>pageSize: 1000totalItems: 0totalPages: 0data: Array vacio");
	response = util.getAgreementFolios(idUnico, cantidadMulti, 1000, FECHA_NEG, "true");
	assertTrue(util.validateMetadataAgreements(response, cantidadMulti, 1000, 0, 0, VACIO));

	/***************************************
	 * PASO 13 Enviar una consulta a la API con los siguientes valores:page =
	 * <pageMaxima>pageSize = 2000createdDate = 1998-01-01marketPlaceId =
	 * <idNoExistente>returnLatest = falsey validar los valores del response
	 ***************************************/
	addStep("Enviar una consulta a la API con los siguientes valores:page = <pageMaxima>pageSize = 2000createdDate = 1998-01-01marketPlaceId = <idNoExistente>returnLatest = falsey validar los valores del response",
		"Los valores del response deben ser:page: <pageMaxima>pageSize: 2000totalItems: 0totalPages: 0data: Array vacio");
	response = util.getAgreementFolios(70899999, totalCount, 2000, FECHA, FALSO);
	assertTrue(util.validateMetadataAgreements(response, totalCount, 2000, 0, 0, VACIO));

	/***************************************
	 * PASO 14 Enviar una consulta a la API con los siguientes valores:page =
	 * <pageMaxima>pageSize = no enviarcreatedDate = no enviarmarketPlaceId =
	 * <IdCon2Registros>returnLatest = no enviary validar los valores del response
	 ***************************************/
	addStep("Enviar una consulta a la API con los siguientes valores:page = <pageMaxima>pageSize = no enviarcreatedDate = no enviarmarketPlaceId = <IdCon2Registros>returnLatest = no enviary validar los valores del response",
		"Los valores del response deben ser:page: <pageMaxima>pageSize: 1000totalItems: <IdCount>totalPages: <totalCount/1000>data: Array con valores");
	response = util.getAgreementFolios(idMulti, 1, 0, NO_ENVIAR, NO_ENVIAR);
	assertTrue(util.validateMetadataAgreements(response, 1, 1000, cantidadMulti, util.divisionRounded(cantidadMulti, 1000),
		VALORES));

	/***************************************
	 * PASO 15 Enviar una consulta a la API con los siguientes valores:page = <page
	 * no existente>pageSize = 1createdDate = 1998-01-01marketPlaceId =
	 * <idUnico>returnLatest = falsey validar los valores del response
	 ***************************************/
	addStep("Enviar una consulta a la API con los siguientes valores:page = <page no existente>pageSize = 1createdDate = 1998-01-01marketPlaceId = <idUnico>returnLatest = falsey validar los valores del response",
		"Los valores del response deben ser:page: <page no existente>pageSize: 1totalItems: 1totalPages: 1data: Array vacio");
	response = util.getAgreementFolios(idUnico, totalCount + 1000, 1, FECHA, FALSO);
	assertTrue(util.validateMetadataAgreements(response, totalCount + 1000, 1, 1, 1, VACIO));

	/***************************************
	 * PASO 16 Enviar una consulta a la API con los siguientes valores:page = <page
	 * no existente>pageSize = 1000createdDate = no enviarmarketPlaceId = no
	 * enviarreturnLatest = truey validar los valores del response
	 ***************************************/
	addStep("Enviar una consulta a la API con los siguientes valores:page = <page no existente>pageSize = 1000createdDate = no enviarmarketPlaceId = no enviarreturnLatest = truey validar los valores del response",
		"Los valores del response deben ser:page: <page no existente>pageSize: 1000totalItems: totalPages: 0data: Array vacio");
	response = util.getAgreementFolios(0, totalCount + 1000, 1000, NO_ENVIAR, "true");
	assertTrue(util.validateMetadataAgreements(response, totalCount + 1000, 1000, totalUnico, 2, VACIO));

	/***************************************
	 * PASO 17 Enviar una consulta a la API con los siguientes valores:page = <page
	 * no existente>pageSize = 2000createdDate = <fechaNoValida>marketPlaceId =
	 * <IdCon2Registros>returnLatest = falsey validar los valores del response
	 ***************************************/
	addStep("Enviar una consulta a la API con los siguientes valores:page = <page no existente>pageSize = 2000createdDate = <fechaNoValida>marketPlaceId = <IdCon2Registros>returnLatest = falsey validar los valores del response",
		"Los valores del response deben ser:page: <page no existente>pageSize: 2000totalItems: 0totalPages: 0data: Array vacio");
	response = util.getAgreementFolios(idMulti, totalCount + 1000, 2000, FECHA_NEG, FALSO);
	assertTrue(util.validateMetadataAgreements(response, totalCount + 1000, 2000, 0, 0, VACIO));

	/***************************************
	 * PASO 18 Enviar una consulta a la API con los siguientes valores:page = <page
	 * no existente>pageSize = no enviarcreatedDate = 1998-01-01marketPlaceId =
	 * <idNoExistente>returnLatest = no enviary validar los valores del response
	 ***************************************/
	addStep("Enviar una consulta a la API con los siguientes valores:page = <page no existente>pageSize = no enviarcreatedDate = 1998-01-01marketPlaceId = <idNoExistente>returnLatest = no enviary validar los valores del response",
		"Los valores del response deben ser:page: <page no existente>pageSize: 1000totalItems: 0totalPages: 0data: Array vacio");
	response = util.getAgreementFolios(70899999, totalCount + 1000, 0, FECHA, NO_ENVIAR);
	assertTrue(util.validateMetadataAgreements(response, totalCount + 1000, 1000, 0, 0, VACIO));

	/***************************************
	 * PASO 19 Enviar una consulta a la API con los siguientes valores:page = no
	 * enviarpageSize = 1createdDate = <fechaNoValida>marketPlaceId = no
	 * enviarreturnLatest = truey validar los valores del response
	 ***************************************/
	addStep("Enviar una consulta a la API con los siguientes valores:page = no enviarpageSize = 1createdDate = <fechaNoValida>marketPlaceId = no enviarreturnLatest = truey validar los valores del response",
		"Los valores del response deben ser:page: 1pageSize: 1totalItems: 0totalPages: 0data: Array vacio");
	response = util.getAgreementFolios(0, 0, 1, FECHA_NEG, "true");
	assertTrue(util.validateMetadataAgreements(response, 1, 1, 0, 0, VACIO));

	/***************************************
	 * PASO 20 Enviar una consulta a la API con los siguientes valores:page = no
	 * enviarpageSize = 1000createdDate = 1998-01-01marketPlaceId =
	 * <idUnico>returnLatest = falsey validar los valores del response
	 ***************************************/
	addStep("Enviar una consulta a la API con los siguientes valores:page = no enviarpageSize = 1000createdDate = 1998-01-01marketPlaceId = <idUnico>returnLatest = falsey validar los valores del response",
		"Los valores del response deben ser:page: 1pageSize: 1000totalItems: 1totalPages: 1data: Array con valores");
	response = util.getAgreementFolios(idUnico, 0, 1000, FECHA, FALSO);
	assertTrue(util.validateMetadataAgreements(response, 1, 1000, 1, 1, VALORES));

	/***************************************
	 * PASO 21 Enviar una consulta a la API con los siguientes valores:page = no
	 * enviarpageSize = 2000createdDate = no enviarmarketPlaceId =
	 * <idNoExistente>returnLatest = no enviary validar los valores del response
	 ***************************************/
	addStep("Enviar una consulta a la API con los siguientes valores:page = no enviarpageSize = 2000createdDate = no enviarmarketPlaceId = <idNoExistente>returnLatest = no enviary validar los valores del response",
		"Los valores del response deben ser:page: 1pageSize: 2000totalItems: 0totalPages: 0data: Array vacio");
	response = util.getAgreementFolios(70899999, 0, 2000, NO_ENVIAR, NO_ENVIAR);
	assertTrue(util.validateMetadataAgreements(response, 1, 2000, 0, 0, VACIO));

	/***************************************
	 * PASO 22 Enviar una consulta a la API con los siguientes valores:page = no
	 * enviarpageSize = no enviarcreatedDate = 1998-01-01marketPlaceId =
	 * <IdCon2Registros>returnLatest = truey validar los valores del response
	 ***************************************/
	addStep("Enviar una consulta a la API con los siguientes valores:page = no enviarpageSize = no enviarcreatedDate = 1998-01-01marketPlaceId = <IdCon2Registros>returnLatest = truey validar los valores del response",
		"Los valores del response deben ser:page: 1pageSize: 1000totalItems: 1totalPages: 1data: Array con valores");
	response = util.getAgreementFolios(idMulti, 0, 0, FECHA, "true");
	assertTrue(util.validateMetadataAgreements(response, 1, 1000, 1, 1, VALORES));

	/***************************************
	 * PASO 23 Enviar una consulta a la API con los siguientes valores:page =
	 * 1pageSize = 1createdDate = <fechaNoValida>marketPlaceId =
	 * <IdCon2Registros>returnLatest = falsey validar los valores del response
	 ***************************************/
	addStep("Enviar una consulta a la API con los siguientes valores:page = 1pageSize = 1createdDate = <fechaNoValida>marketPlaceId = <IdCon2Registros>returnLatest = falsey validar los valores del response",
		"Los valores del response deben ser:page: 1pageSize: 1totalItems: 0totalPages: 0data: Array vacio");
	response = util.getAgreementFolios(idMulti, 1, 1, FECHA_NEG, FALSO);
	assertTrue(util.validateMetadataAgreements(response, 1, 1, 0, 0, VACIO));

	/***************************************
	 * PASO 24 Enviar una consulta a la API con los siguientes valores:page =
	 * <pageMaxima>pageSize = 2000createdDate = no enviarmarketPlaceId =
	 * <idUnico>returnLatest = no enviary validar los valores del response
	 ***************************************/
	addStep("Enviar una consulta a la API con los siguientes valores:page = <pageMaxima>pageSize = 2000createdDate = no enviarmarketPlaceId = <idUnico>returnLatest = no enviary validar los valores del response",
		"Los valores del response deben ser:page: <pageMaxima>pageSize: 2000totalItems: 1totalPages: <totalCount/2000>data: Array con valores");
	response = util.getAgreementFolios(idUnico, 1, 2000, NO_ENVIAR, NO_ENVIAR);
	assertTrue(util.validateMetadataAgreements(response, 1, 2000, 1, util.divisionRounded(totalCount, 2000), VALORES));

    }

    @Override
    public void beforeTest() {
    }

    @Override
    public String setPrerequisites() {
	return "Acceso a la API\\nAcceso a Snowflake\\nInformacion Valida en las tablas de Snowflake\\nLa API funcionando";
    }

    @Override
    public String setTestDescription() {
	return "Se valida el envio de get sin parametros, el paginado, el funcionamiento del filtro de fecha, del filtro de id, del filtro que decide si retorna el ultimo y  del tamano de la pagina.";
    }

    @Override
    public String setTestDesigner() {
	return "Guillermo Garcia";
    }

    @Override
    public String setTestFullName() {
	return "ATC_FT_043_ValidacionFuncionalidadDeParametrosEndpointAgreementFolios";
    }

    @Override
    public String setTestInstanceID() {
	return null;
    }

}
