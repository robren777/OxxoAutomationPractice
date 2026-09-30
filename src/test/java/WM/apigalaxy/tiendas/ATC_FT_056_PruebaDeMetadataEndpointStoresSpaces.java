package WM.apigalaxy.tiendas;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;

import org.testng.Assert;
import org.testng.annotations.Test;

import WM.apigalaxy.models.Result;
import WM.util.ApiGalaxyUtils;
import modelo.BaseExecution;

/**
 * DESCRIPCIÓN: Se valida el funcionamiento del endpoint de stores spaces,
 * validando que las reglas de los aprametros funcionen adecuadamente. Y los
 * valores regresados en el metadata sean correctos.
 *
 * NOMBRE CASOS DE PRUEBA MANUAL QUE SE ABARCAN: -
 * MTC-FT-056_PruebaDeMetadataEndpointStoresSpaces
 *
 * PREREQUISITOS: Acceso a la API La API funcionando
 *
 * INSUMOS:
 *
 * @author Guillermo Garcia
 * @date 25/05/2026
 */
public class ATC_FT_056_PruebaDeMetadataEndpointStoresSpaces extends BaseExecution {
    @Test(dataProvider = "data-provider", enabled = true)
    public void ATC_FT_056_PruebaDeMetadataEndpointStoresSpaces_test(HashMap<String, String> data) throws Exception {
      /*****************************
       * Variables Data Provider
       **************************************/

      /*****************************
       * Instancias
       **************************************************/
      String ambiente = data.get("ambiente");
      ApiGalaxyUtils util = new ApiGalaxyUtils(ambiente);
      Connection connection = null;
      String queryCount = "SELECT count(1) FROM DBZDEV_MEX_OXXO.LAND_MARKETPLACE_TRAN.OUT_179_RDP_MARKETPLACE_ESPACIOS_TIENDA WHERE TIPO_DELTA = 'ALTA';";
      final String EXPECTED_RESULT_1 = "El metadata debera ser : limit = 1000 returnedItems = 1000 hasMore = true nextCursor = 1001 storesSpaces = array con datos";
      final String EXPECTED_RESULT_2 = "El metadata debera ser : limit = 1 returnedItems = 0 hasMore = false nextCursor = null storesSpaces = vacio";
      final String EXPECTED_RESULT_3 = "El metadata debera ser : limit = 1000 returnedItems = 0 hasMore = false nextCursor = null storesSpaces = vacio";
      final String EXPECTED_RESULT_4 = "El metadata debera ser : limit = 2000 returnedItems = 0 hasMore = false nextCursor = null storesSpaces = vacio";
      final String COMMON_DATE_1 = "1998-01-01";
      final String COMMON_DATE_2 = "2027-01-01";
      final String EXPECTED_ASSERT = "Se valida si todo es correcto en el metadata";
      int count = 0;

      /***************************************
       * PASO 1 Hacer conexion a Snowflake
       ***************************************/
      addStep("Hacer conexion a Snowflake", "Se debe poder hacer conexion");
      try {
	  connection = util.getSnowflakeConnection();
	  if (connection.isValid(10))
	      testCase.addCodeEvidenceCurrentStep("Conexion a SnowFlake arroja estatus OK");

	  /***************************************
	   * PASO 2
	   ***************************************/
	  addStep("Mandar querie que obtenga el total de registros en la tabla de espcaio tiendas SELECT count(1) FROM DBZDEV_MEX_OXXO.LAND_MARKETPLACE_TRAN.OUT_179_RDP_MARKETPLACE_ESPACIOS_TIENDA;",
		  "Almacenar la respuesta");
	  // Se genera un statement para ejecutar las consultas
	  Statement stat = connection.createStatement();
	  // Se manda una consulta y se guarda en un resultSet
	  ResultSet result = stat.executeQuery(queryCount);
	  if (result.next()) {
	      count = result.getInt("COUNT(1)");
	      testCase.addCodeEvidenceCurrentStep("El valor encontrado en el count es: " + count);
	  }
      } catch (SQLException e) {
	  System.out.println("No se pudo cerrar la conexión: " + e.getMessage());
      } finally {
	  // cierre de conexion
	  if (connection != null) {
	      try {
		  connection.close();
	      } catch (SQLException e) {
		  System.out.println("No se pudo cerrar la conexión: " + e.getMessage());
	      }
	  }
      }
      //Nota para el validador, se hardcodean los valores en lugar de pasarlos por dp o variable general porque son especificos de cada paso y no deberian cambiarse
      /***************************************
       * PASO 4
       ***************************************/
      addStep("Mandar una solicitud al endpoint de stores-spaces con los siguientes parametros: limit = vacío updatedDate = 1998-01-01 cursor = 1 y valida el metadata.",
	      EXPECTED_RESULT_1);
      Result resultadoDelMetadata;
      testCase.addCodeEvidenceCurrentStep("Se hace una llamada a " + util.getTokenUrl());
      resultadoDelMetadata = util.sendStoresSpacesAndValidateMetadata(0, COMMON_DATE_1, 1, 1000, 1000, true, 1001,
	      true);
      Assert.assertTrue(resultadoDelMetadata.isFlag(), EXPECTED_ASSERT);
      testCase.addCodeEvidenceCurrentStep(resultadoDelMetadata.getTexto());

      /***************************************
       * PASO 5
       ***************************************/
      addStep("Mandar una solicitud al endpoint de stores-spaces con los siguientes parametros: limit = vacío updatedDate = 1998-01-01 cursor = <Valor del count> y valida el metadata.",
	      "El metadata debera ser : limit = 1000 returnedItems = 1 hasMore = false nextCursor = null storesSpaces = array con datos");
      resultadoDelMetadata = util.sendStoresSpacesAndValidateMetadata(0, COMMON_DATE_1, count - 1, 1000, 1, false, null,
	      true);
      Assert.assertTrue(resultadoDelMetadata.isFlag(), EXPECTED_ASSERT);
      testCase.addCodeEvidenceCurrentStep(resultadoDelMetadata.getTexto());

      /***************************************
       * PASO 6
       ***************************************/
      addStep("Mandar una solicitud al endpoint de stores-spaces con los siguientes parametros: limit = vacío updatedDate = 1998-01-01 cursor = <Valor del count + 1> y valida el metadata.",
	      "El metadata debera ser : limit = 1000 returnedItems = 0 hasMore = false nextCursor = null storesSpaces = array vacio");
      resultadoDelMetadata = util.sendStoresSpacesAndValidateMetadata(0, COMMON_DATE_1, count, 1000, 0, false, null,
	      false);
      Assert.assertTrue(resultadoDelMetadata.isFlag(), EXPECTED_ASSERT);
      testCase.addCodeEvidenceCurrentStep(resultadoDelMetadata.getTexto());

      /***************************************
       * PASO 7
       ***************************************/
      addStep("Mandar una solicitud al endpoint de stores-spaces con los siguientes parametros: limit = vacío updatedDate = 1998-01-01 cursor = vacío y valida el metadata.",
	      EXPECTED_RESULT_1);
      resultadoDelMetadata = util.sendStoresSpacesAndValidateMetadata(0, COMMON_DATE_1, 0, 1000, 1000, true, 1001,
	      true);
      Assert.assertTrue(resultadoDelMetadata.isFlag(), EXPECTED_ASSERT);
      testCase.addCodeEvidenceCurrentStep(resultadoDelMetadata.getTexto());

      /***************************************
       * PASO 8
       ***************************************/
      addStep("Mandar una solicitud al endpoint de stores-spaces con los siguientes parametros: limit = 1 updatedDate = 1998-01-01 cursor = 1 y valida el metadata.",
	      "El metadata debera ser : limit = 1 returnedItems = 1 hasMore = true nextCursor = 2 storesSpaces = array con datos");
      resultadoDelMetadata = util.sendStoresSpacesAndValidateMetadata(1, COMMON_DATE_1, 1, 1, 1, true, 2, true);
      Assert.assertTrue(resultadoDelMetadata.isFlag(), EXPECTED_ASSERT);
      testCase.addCodeEvidenceCurrentStep(resultadoDelMetadata.getTexto());

      /***************************************
       * PASO 9
       ***************************************/
      addStep("Mandar una solicitud al endpoint de stores-spaces con los siguientes parametros: limit = 1 updatedDate = 1998-01-01 cursor = <Valor del count> y valida el metadata.",
	      "El metadata debera ser : limit = 1 returnedItems = 1 hasMore = false nextCursor = null storesSpaces = array con datos");
      resultadoDelMetadata = util.sendStoresSpacesAndValidateMetadata(1, COMMON_DATE_1, count - 1, 1, 1, false, null,
	      true);
      Assert.assertTrue(resultadoDelMetadata.isFlag(), EXPECTED_ASSERT);
      testCase.addCodeEvidenceCurrentStep(resultadoDelMetadata.getTexto());

      /***************************************
       * PASO 10
       ***************************************/
      addStep("Mandar una solicitud al endpoint de stores-spaces con los siguientes parametros: limit = 1 updatedDate = 1998-01-01 cursor = <Valor del count + 1> y valida el metadata.",
	      EXPECTED_RESULT_2);
      resultadoDelMetadata = util.sendStoresSpacesAndValidateMetadata(1, COMMON_DATE_1, count, 1, 0, false, null,
	      false);
      Assert.assertTrue(resultadoDelMetadata.isFlag(), EXPECTED_ASSERT);
      testCase.addCodeEvidenceCurrentStep(resultadoDelMetadata.getTexto());

      /***************************************
       * PASO 11
       ***************************************/
      addStep("Mandar una solicitud al endpoint de stores-spaces con los siguientes parametros: limit = 1 updatedDate = 1998-01-01 cursor = vacío y valida el metadata.",
	      "El metadata debera ser : limit = 1 returnedItems = 1 hasMore = true nextCursor = 2 storesSpaces = array con datos");
      resultadoDelMetadata = util.sendStoresSpacesAndValidateMetadata(1, COMMON_DATE_1, 0, 1, 1, true, 2, true);
      Assert.assertTrue(resultadoDelMetadata.isFlag(), EXPECTED_ASSERT);
      testCase.addCodeEvidenceCurrentStep(resultadoDelMetadata.getTexto());

      /***************************************
       * PASO 12
       ***************************************/
      addStep("Mandar una solicitud al endpoint de stores-spaces con los siguientes parametros: limit = 1000 updatedDate = 1998-01-01 cursor = 1 y valida el metadata.",
	      EXPECTED_RESULT_1);
      resultadoDelMetadata = util.sendStoresSpacesAndValidateMetadata(1000, COMMON_DATE_1, 1, 1000, 1000, true, 1001,
	      true);
      Assert.assertTrue(resultadoDelMetadata.isFlag(), EXPECTED_ASSERT);
      testCase.addCodeEvidenceCurrentStep(resultadoDelMetadata.getTexto());

      /***************************************
       * PASO 13
       ***************************************/
      addStep("Mandar una solicitud al endpoint de stores-spaces con los siguientes parametros: limit = 1000 updatedDate = 1998-01-01 cursor = <Valor del count> y valida el metadata.",
	      "El metadata debera ser : limit = 1000 returnedItems = 1 hasMore = false nextCursor = null storesSpaces = array con datos");
      resultadoDelMetadata = util.sendStoresSpacesAndValidateMetadata(1000, COMMON_DATE_1, count - 1, 1000, 1, false,
	      null, true);
      Assert.assertTrue(resultadoDelMetadata.isFlag(), EXPECTED_ASSERT);
      testCase.addCodeEvidenceCurrentStep(resultadoDelMetadata.getTexto());

      /***************************************
       * PASO 14
       ***************************************/
      addStep("Mandar una solicitud al endpoint de stores-spaces con los siguientes parametros: limit = 1000 updatedDate = 1998-01-01 cursor = <Valor del count + 1> y valida el metadata.",
	      EXPECTED_RESULT_3);
      resultadoDelMetadata = util.sendStoresSpacesAndValidateMetadata(1000, COMMON_DATE_1, count, 1000, 0, false, null,
	      false);
      Assert.assertTrue(resultadoDelMetadata.isFlag(), EXPECTED_ASSERT);
      testCase.addCodeEvidenceCurrentStep(resultadoDelMetadata.getTexto());

      /***************************************
       * PASO 15
       ***************************************/
      addStep("Mandar una solicitud al endpoint de stores-spaces con los siguientes parametros: limit = 1000 updatedDate = 1998-01-01 cursor = vacío y valida el metadata.",
	      EXPECTED_RESULT_1);
      resultadoDelMetadata = util.sendStoresSpacesAndValidateMetadata(1000, COMMON_DATE_1, 0, 1000, 1000, true, 1001,
	      true);
      Assert.assertTrue(resultadoDelMetadata.isFlag(), EXPECTED_ASSERT);
      testCase.addCodeEvidenceCurrentStep(resultadoDelMetadata.getTexto());

      /***************************************
       * PASO 16
       ***************************************/
      addStep("Mandar una solicitud al endpoint de stores-spaces con los siguientes parametros: limit = 2000 updatedDate = 1998-01-01 cursor = 1 y valida el metadata.",
	      "El metadata debera ser : limit = 2000 returnedItems = 2000 hasMore = true nextCursor = 2001 storesSpaces = array con datos");
      resultadoDelMetadata = util.sendStoresSpacesAndValidateMetadata(2000, COMMON_DATE_1, 1, 2000, 2000, true, 2001,
	      true);
      Assert.assertTrue(resultadoDelMetadata.isFlag(), EXPECTED_ASSERT);
      testCase.addCodeEvidenceCurrentStep(resultadoDelMetadata.getTexto());

      /***************************************
       * PASO 17
       ***************************************/
      addStep("Mandar una solicitud al endpoint de stores-spaces con los siguientes parametros: limit = 2000 updatedDate = 1998-01-01 cursor = <Valor del count> y valida el metadata.",
	      "El metadata debera ser : limit = 2000 returnedItems = 1 hasMore = false nextCursor = null storesSpaces = array con datos");
      resultadoDelMetadata = util.sendStoresSpacesAndValidateMetadata(2000, COMMON_DATE_1, count - 1, 2000, 1, false,
	      null, true);
      Assert.assertTrue(resultadoDelMetadata.isFlag(), EXPECTED_ASSERT);
      testCase.addCodeEvidenceCurrentStep(resultadoDelMetadata.getTexto());

      /***************************************
       * PASO 18
       ***************************************/
      addStep("Mandar una solicitud al endpoint de stores-spaces con los siguientes parametros: limit = 2000 updatedDate = 1998-01-01 cursor = <Valor del count + 1> y valida el metadata.",
	      EXPECTED_RESULT_4);
      resultadoDelMetadata = util.sendStoresSpacesAndValidateMetadata(2000, COMMON_DATE_1, count + 1, 2000, 0, false,
	      null, false);
      Assert.assertTrue(resultadoDelMetadata.isFlag(), EXPECTED_ASSERT);
      testCase.addCodeEvidenceCurrentStep(resultadoDelMetadata.getTexto());

      /***************************************
       * PASO 19
       ***************************************/
      addStep("Mandar una solicitud al endpoint de stores-spaces con los siguientes parametros: limit = 2000 updatedDate = 1998-01-01 cursor = vacío y valida el metadata.",
	      "El metadata debera ser : limit = 2000 returnedItems = 2000 hasMore = true nextCursor = 2001 storesSpaces = array con datos");
      resultadoDelMetadata = util.sendStoresSpacesAndValidateMetadata(2000, COMMON_DATE_1, 0, 2000, 2000, true, 2001,
	      true);
      Assert.assertTrue(resultadoDelMetadata.isFlag(), EXPECTED_ASSERT);
      testCase.addCodeEvidenceCurrentStep(resultadoDelMetadata.getTexto());

      /***************************************
       * PASO 20
       ***************************************/
      addStep("Mandar una solicitud al endpoint de stores-spaces con los siguientes parametros: limit = vacío updatedDate = 2027-01-01 cursor = 1 y valida el metadata.",
	      EXPECTED_RESULT_3);
      resultadoDelMetadata = util.sendStoresSpacesAndValidateMetadata(0, COMMON_DATE_2, 1, 1000, 0, false, null, false);
      Assert.assertTrue(resultadoDelMetadata.isFlag(), EXPECTED_ASSERT);
      testCase.addCodeEvidenceCurrentStep(resultadoDelMetadata.getTexto());

      /***************************************
       * PASO 21
       ***************************************/
      addStep("Mandar una solicitud al endpoint de stores-spaces con los siguientes parametros: limit = vacío updatedDate = 2027-01-01 cursor = <Valor del count> y valida el metadata.",
	      "El metadata debera ser : limit = 1000 returnedItems = 1 hasMore = false nextCursor = null storesSpaces = vacio");
      resultadoDelMetadata = util.sendStoresSpacesAndValidateMetadata(0, COMMON_DATE_2, count - 1, 1000, 0, false, null,
	      false);
      Assert.assertTrue(resultadoDelMetadata.isFlag(), EXPECTED_ASSERT);
      testCase.addCodeEvidenceCurrentStep(resultadoDelMetadata.getTexto());

      /***************************************
       * PASO 22
       ***************************************/
      addStep("Mandar una solicitud al endpoint de stores-spaces con los siguientes parametros: limit = vacío updatedDate = 2027-01-01 cursor = <Valor del count + 1> y valida el metadata.",
	      EXPECTED_RESULT_3);
      resultadoDelMetadata = util.sendStoresSpacesAndValidateMetadata(0, COMMON_DATE_1, count, 1000, 0, false, null,
	      false);
      Assert.assertTrue(resultadoDelMetadata.isFlag(), EXPECTED_ASSERT);
      testCase.addCodeEvidenceCurrentStep(resultadoDelMetadata.getTexto());

      /***************************************
       * PASO 23
       ***************************************/
      addStep("Mandar una solicitud al endpoint de stores-spaces con los siguientes parametros: limit = vacío updatedDate = 2027-01-01 cursor = vacío y valida el metadata.",
	      "El metadata debera ser : limit = 1000 returnedItems = 1000 hasMore = false nextCursor = null storesSpaces = vacio");
      resultadoDelMetadata = util.sendStoresSpacesAndValidateMetadata(0, COMMON_DATE_2, 0, 1000, 0, false, null, false);
      Assert.assertTrue(resultadoDelMetadata.isFlag(), EXPECTED_ASSERT);
      testCase.addCodeEvidenceCurrentStep(resultadoDelMetadata.getTexto());

      /***************************************
       * PASO 24
       ***************************************/
      addStep("Mandar una solicitud al endpoint de stores-spaces con los siguientes parametros: limit = 1 updatedDate = 2027-01-01 cursor = 1 y valida el metadata.",
	      EXPECTED_RESULT_2);
      resultadoDelMetadata = util.sendStoresSpacesAndValidateMetadata(1, COMMON_DATE_2, 1, 1, 0, false, null, false);
      Assert.assertTrue(resultadoDelMetadata.isFlag(), EXPECTED_ASSERT);
      testCase.addCodeEvidenceCurrentStep(resultadoDelMetadata.getTexto());

      /***************************************
       * PASO 25
       ***************************************/
      addStep("Mandar una solicitud al endpoint de stores-spaces con los siguientes parametros: limit = 1 updatedDate = 2027-01-01 cursor = <Valor del count> y valida el metadata.",
	      EXPECTED_RESULT_2);
      resultadoDelMetadata = util.sendStoresSpacesAndValidateMetadata(1, COMMON_DATE_2, count - 1, 1, 0, false, null,
	      false);
      Assert.assertTrue(resultadoDelMetadata.isFlag(), EXPECTED_ASSERT);
      testCase.addCodeEvidenceCurrentStep(resultadoDelMetadata.getTexto());

      /***************************************
       * PASO 26
       ***************************************/
      addStep("Mandar una solicitud al endpoint de stores-spaces con los siguientes parametros: limit = 1 updatedDate = 2027-01-01 cursor = <Valor del count + 1> y valida el metadata.",
	      EXPECTED_RESULT_2);
      resultadoDelMetadata = util.sendStoresSpacesAndValidateMetadata(1, COMMON_DATE_2, count, 1, 0, false, null,
	      false);
      Assert.assertTrue(resultadoDelMetadata.isFlag(), EXPECTED_ASSERT);
      testCase.addCodeEvidenceCurrentStep(resultadoDelMetadata.getTexto());

      /***************************************
       * PASO 27
       ***************************************/
      addStep("Mandar una solicitud al endpoint de stores-spaces con los siguientes parametros: limit = 1 updatedDate = 2027-01-01 cursor = vacío y valida el metadata.",
	      EXPECTED_RESULT_2);
      resultadoDelMetadata = util.sendStoresSpacesAndValidateMetadata(1, COMMON_DATE_2, 0, 1, 0, false, null, false);
      Assert.assertTrue(resultadoDelMetadata.isFlag(), EXPECTED_ASSERT);
      testCase.addCodeEvidenceCurrentStep(resultadoDelMetadata.getTexto());

      /***************************************
       * PASO 28
       ***************************************/
      addStep("Mandar una solicitud al endpoint de stores-spaces con los siguientes parametros: limit = 1000 updatedDate = 2027-01-01 cursor = 1 y valida el metadata.",
	      EXPECTED_RESULT_3);
      resultadoDelMetadata = util.sendStoresSpacesAndValidateMetadata(1000, COMMON_DATE_2, 1, 1000, 0, false, null,
	      false);
      Assert.assertTrue(resultadoDelMetadata.isFlag(), EXPECTED_ASSERT);
      testCase.addCodeEvidenceCurrentStep(resultadoDelMetadata.getTexto());

      /***************************************
       * PASO 29
       ***************************************/
      addStep("Mandar una solicitud al endpoint de stores-spaces con los siguientes parametros: limit = 1000 updatedDate = 2027-01-01 cursor = <Valor del count> y valida el metadata.",
	      EXPECTED_RESULT_3);
      resultadoDelMetadata = util.sendStoresSpacesAndValidateMetadata(1000, COMMON_DATE_2, count - 1, 1000, 0, false,
	      null, false);
      Assert.assertTrue(resultadoDelMetadata.isFlag(), EXPECTED_ASSERT);
      testCase.addCodeEvidenceCurrentStep(resultadoDelMetadata.getTexto());

      /***************************************
       * PASO 30
       ***************************************/
      addStep("Mandar una solicitud al endpoint de stores-spaces con los siguientes parametros: limit = 1000 updatedDate = 2027-01-01 cursor = <Valor del count + 1> y valida el metadata.",
	      EXPECTED_RESULT_3);
      resultadoDelMetadata = util.sendStoresSpacesAndValidateMetadata(1000, COMMON_DATE_2, count, 1000, 0, false, null,
	      false);
      Assert.assertTrue(resultadoDelMetadata.isFlag(), EXPECTED_ASSERT);
      testCase.addCodeEvidenceCurrentStep(resultadoDelMetadata.getTexto());

      /***************************************
       * PASO 31
       ***************************************/
      addStep("Mandar una solicitud al endpoint de stores-spaces con los siguientes parametros: limit = 1000 updatedDate = 2027-01-01 cursor = vacío y valida el metadata.",
	      EXPECTED_RESULT_3);
      resultadoDelMetadata = util.sendStoresSpacesAndValidateMetadata(1000, COMMON_DATE_2, 0, 1000, 0, false, null,
	      false);
      Assert.assertTrue(resultadoDelMetadata.isFlag(), EXPECTED_ASSERT);
      testCase.addCodeEvidenceCurrentStep(resultadoDelMetadata.getTexto());

      /***************************************
       * PASO 32
       ***************************************/
      addStep("Mandar una solicitud al endpoint de stores-spaces con los siguientes parametros: limit = 2000 updatedDate = 2027-01-01 cursor = 1 y valida el metadata.",
	      EXPECTED_RESULT_4);
      resultadoDelMetadata = util.sendStoresSpacesAndValidateMetadata(2000, COMMON_DATE_2, 1, 2000, 0, false, null,
	      false);
      Assert.assertTrue(resultadoDelMetadata.isFlag(), EXPECTED_ASSERT);
      testCase.addCodeEvidenceCurrentStep(resultadoDelMetadata.getTexto());

      /***************************************
       * PASO 33
       ***************************************/
      addStep("Mandar una solicitud al endpoint de stores-spaces con los siguientes parametros: limit = 2000 updatedDate = 2027-01-01 cursor = <Valor del count> y valida el metadata.",
	      EXPECTED_RESULT_4);
      resultadoDelMetadata = util.sendStoresSpacesAndValidateMetadata(2000, COMMON_DATE_2, count - 1, 2000, 0, false,
	      null, false);
      Assert.assertTrue(resultadoDelMetadata.isFlag(), EXPECTED_ASSERT);
      testCase.addCodeEvidenceCurrentStep(resultadoDelMetadata.getTexto());

      /***************************************
       * PASO 34
       ***************************************/
      addStep("Mandar una solicitud al endpoint de stores-spaces con los siguientes parametros: limit = 2000 updatedDate = 2027-01-01 cursor = <Valor del count + 1> y valida el metadata.",
	      EXPECTED_RESULT_4);
      resultadoDelMetadata = util.sendStoresSpacesAndValidateMetadata(2000, COMMON_DATE_2, count, 2000, 0, false, null,
	      false);
      Assert.assertTrue(resultadoDelMetadata.isFlag(), EXPECTED_ASSERT);
      testCase.addCodeEvidenceCurrentStep(resultadoDelMetadata.getTexto());

      /***************************************
       * PASO 35
       ***************************************/
      addStep("Mandar una solicitud al endpoint de stores-spaces con los siguientes parametros: limit = 2000 updatedDate = 2027-01-01 cursor = vacío y valida el metadata.",
	      EXPECTED_RESULT_4);
      resultadoDelMetadata = util.sendStoresSpacesAndValidateMetadata(2000, COMMON_DATE_2, 0, 2000, 0, false, null,
	      false);
      Assert.assertTrue(resultadoDelMetadata.isFlag(), EXPECTED_ASSERT);
      testCase.addCodeEvidenceCurrentStep(resultadoDelMetadata.getTexto());
  }

  @Override
  public String setTestFullName() {
      return "ATC_FT_056_PruebaDeMetadataEndpointStoresSpaces";
  }

  @Override
  public String setTestDescription() {
      return "Se valida el funcionamiento del endpoint de stores spaces, validando que las reglas de los aprametros funcionen adecuadamente. Y los valores regresados en el metadata sean correctos.";
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
	return "Acceso a la API\r\n" + "Acceso a Snowflake\r\n" + "Informacion Valida en las tablas de Snowflake\r\n"
		+ "La API funcionando";

    }

    @Override
    public void beforeTest() {

    }
}
