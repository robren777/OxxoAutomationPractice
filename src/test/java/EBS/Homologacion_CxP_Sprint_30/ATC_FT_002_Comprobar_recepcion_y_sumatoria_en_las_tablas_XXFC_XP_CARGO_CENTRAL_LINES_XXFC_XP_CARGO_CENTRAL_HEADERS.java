package EBS.Homologacion_CxP_Sprint_30;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.testng.annotations.Test;

import EBS.util.GlobalVariables;
import modelo.BaseExecution;

/**
* DESCRIPCIÓN: Se valida que la sumatoria de los importes mas el impuesto de la tabla XXFC_XP_CARGO_CENTRAL_LINES sea igual al monto reflejado en la tabla XXFC_XP_CARGO_CENTRAL_HEADERS
*
* NOMBRE CASOS DE PRUEBA MANUAL QUE SE ABARCAN:
*     -ATC_FT_002_Comprobar_recepcion_y_sumatoria_en_las_tablas_XXFC_XP_CARGO_CENTRAL_LINES_XXFC_XP_CARGO_CENTRAL_HEADERS
*  
* PREREQUISITOS:
*	Tener acceso a las BD de AVEBQA
*
* INSUMOS (datos o información que necesita estar preparada o generada para poder ejecutar el script automatizado):
*     - NA
* @author Jesus Alberto Moreno Delgado
* @date 09/01/2025
*/
public class ATC_FT_002_Comprobar_recepcion_y_sumatoria_en_las_tablas_XXFC_XP_CARGO_CENTRAL_LINES_XXFC_XP_CARGO_CENTRAL_HEADERS
		extends BaseExecution {

	@Test(dataProvider = "data-provider")
	public void ATC_FT_002_Comprobar_recepcion_y_sumatoria_en_las_tablas_XXFC_XP_CARGO_CENTRAL_LINES_XXFC_XP_CARGO_CENTRAL_HEADERS_test(
			HashMap<String, String> data) throws Exception {

		/*
		 * VARIABLES
		 */
		String url = data.get("url");
		String path = data.get("path");
		String queryWMLOG = data.get("wmlog1");
		String wm_run_id = "";
		String queryEBS1 = data.get("ebs1");
		String queryEBS2 = data.get("ebs2");
		String queryEBS3 = data.get("ebs3");
		String queryEBS4_1 = data.get("ebs4_1");
		String queryEBS4_2 = data.get("ebs4_2");
		String queryEBS5_1 = data.get("ebs5_1");
		String queryEBS5_2 = data.get("ebs5_2");
		String queryComprobador = data.get("query_comprobador");
		boolean flag;
		String idHeaderFromEBS1 = null;
		String invoiceNum = null;
		String total_calculado = null;
		String importe = null;
		String CALCULATED_AMOUNT_WITH_TAX = null;
		String AMOUNT = null;

		// Validar que ningÃºn campo del DataProvider venga como nulo.
		flag = false;
		if (!url.isEmpty() || !queryComprobador.isEmpty() || !path.isEmpty() || !queryWMLOG.isEmpty()
				|| !queryEBS1.isEmpty() || !queryEBS2.isEmpty() || !queryEBS3.isEmpty()) {
			flag = true;
		}
		assertTrue(flag, "Los registros en el Data Provider estan completos");

		/*
		 * PASOS DEL CASO DE PRUEBA
		 */

		// .1 Ingresar a la BD de AVBQA
		addStep("Ingresar a la BD de AVBQA\r\n");

		utils.sql.SQLUtil dbEBS = new utils.sql.SQLUtil(GlobalVariables.DB_HOST_AVEBQA, GlobalVariables.DB_USER_AVEBQA,
				GlobalVariables.DB_PASSWORD_AVEBQA);
		utils.sql.SQLResult resultadoContenedor = dbEBS.executeQuery(queryComprobador);
		testCase.addQueryEvidenceCurrentStep(resultadoContenedor);
		flag = false;
		if (!resultadoContenedor.isEmpty()) {
			flag = true;
		}
		assertTrue(flag, "El registro existe en la BD");

		// 2. Validar que la información de xpertal llegue a la tablas staggin:
		// XXFC_XP_CARGO_CENTRAL_HEADERS, XXFC_XP_CARGO_CENTRAL_LINES y
		// XXFC_XP_RELACION_UUIDS_TMP

		addStep("Validar que la información de xpertal llegue a la tablas staggin:\r\n"
				+ "	XXFC_XP_CARGO_CENTRAL_HEADERS, XXFC_XP_CARGO_CENTRAL_LINES y\r\n"
				+ "	XXFC_XP_RELACION_UUIDS_TMP");

		String jsonContent = new String(Files.readAllBytes(Paths.get(path)));
		testCase.addBoldTextEvidenceCurrentStep("JSON Utilizado en la prueba: ");
		testCase.addBoldTextEvidenceCurrentStep(jsonContent);

		try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
			HttpPost httpPost = new HttpPost(url);
			StringEntity requestEntity = new StringEntity(jsonContent, ContentType.APPLICATION_JSON);
			httpPost.setEntity(requestEntity);
			HttpResponse response = httpClient.execute(httpPost);
			int statusCode = response.getStatusLine().getStatusCode();

			flag = false;
			if (statusCode == 200) {
				flag = true;
				assertTrue(flag, "La solicitud se ha enviado correctamente. Código de estado: " + statusCode);
				System.out.println("La solicitud se ha enviado correctamente. Código de estado: " + statusCode);
			} else {
				System.out.println("Se produjo un error al enviar la solicitud. Código de estado: " + statusCode);
				assertTrue(flag, "Se produjo un error al enviar la solicitud. Código de estado: " + statusCode);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		// Tomar los datos de WMLOG
		utils.sql.SQLUtil dbWM = new utils.sql.SQLUtil(GlobalVariables.DB_HOST_FCWMLQAv2,
				GlobalVariables.DB_USER_FCWMLQAv2, GlobalVariables.DB_PASSWORD_FCWMLQAv2);
		resultadoContenedor = dbWM.executeQuery(queryWMLOG);
		flag = false;
		if (!resultadoContenedor.isEmpty()) {
			flag = true;
			wm_run_id = resultadoContenedor.getData(0, 0);
		}
		assertTrue(flag, "El registro existe en la BD");

		// queryEBS1
		dbEBS = new utils.sql.SQLUtil(GlobalVariables.DB_HOST_AVEBQA, GlobalVariables.DB_USER_AVEBQA,
				GlobalVariables.DB_PASSWORD_AVEBQA);
		resultadoContenedor = dbEBS.executeQuery(queryEBS1 + wm_run_id);
		testCase.addBoldTextEvidenceCurrentStep("Tabla XXFC_XP_CARGO_CENTRAL_HEADERS: ");
		testCase.addQueryEvidenceCurrentStep(resultadoContenedor);
		flag = false;
		if (!resultadoContenedor.isEmpty()) {
			flag = true;
		}
		assertTrue(flag, "El registro existe en la BD");

		if (!resultadoContenedor.isEmpty()) {
			idHeaderFromEBS1 = resultadoContenedor.getData(0, 1);
		}

		// queryEBS2

		resultadoContenedor = dbEBS.executeQuery(queryEBS2 + "AND id_header = " + idHeaderFromEBS1);
		testCase.addBoldTextEvidenceCurrentStep("Tabla XXFC_XP_CARGO_CENTRAL_LINES: ");
		testCase.addQueryEvidenceCurrentStep(resultadoContenedor);
		flag = false;
		if (!resultadoContenedor.isEmpty())
			invoiceNum = resultadoContenedor.getData(0, "invoice_num");
		flag = true;
		assertTrue(flag, "El registro existe en la BD");
		System.out.println(invoiceNum);

		// queryEBS3

		resultadoContenedor = dbEBS.executeQuery(queryEBS3);
		testCase.addBoldTextEvidenceCurrentStep("Tabla XXFC_XP_RELACION_UUIDS_TMP: ");
		testCase.addQueryEvidenceCurrentStep(resultadoContenedor);
		flag = false;
		if (!resultadoContenedor.isEmpty())
			flag = true;
		assertTrue(flag, "El registro existe en la BD");

		/*
		 * PASO 3: "Validar que la cantidad de piezas por el costo unitario sea igual al
		 * importe con el siguiente query: SELECT INVOICE_NUM, SUM(PIEZAS *
		 * COSTO_UNITARIO) AS TOTAL_CALCULADO, SUM(IMPORTE) AS IMPORTE FROM
		 * APPS.XXFC_XP_CARGO_CENTRAL_LINES WHERE INVOICE_NUM IN ('NUM FACTURA') GROUP
		 * BY INVOICE_NUM"
		 */
		addStep("Validar que la cantidad de piezas por el costo unitario sea igual al importe con el siguiente query: SELECT INVOICE_NUM, SUM(PIEZAS * COSTO_UNITARIO) AS TOTAL_CALCULADO, SUM(IMPORTE) AS IMPORTE\r\n"
				+ "		FROM APPS.XXFC_XP_CARGO_CENTRAL_LINES\r\n" + "		WHERE INVOICE_NUM IN ('NUM FACTURA')\r\n"
				+ "		GROUP BY INVOICE_NUM");

		// queryEBS4
		dbEBS = new utils.sql.SQLUtil(GlobalVariables.DB_HOST_AVEBQA, GlobalVariables.DB_USER_AVEBQA,
				GlobalVariables.DB_PASSWORD_AVEBQA);
		resultadoContenedor = dbEBS.executeQuery(queryEBS4_1 + " '" + invoiceNum + "' " + queryEBS4_2);
		testCase.addQueryEvidenceCurrentStep(resultadoContenedor);
		flag = false;
		if (!resultadoContenedor.isEmpty()) {
			flag = true;
			total_calculado = resultadoContenedor.getData(0, 1);
			importe = resultadoContenedor.getData(0, 2);

			if (total_calculado.equals(importe)) {
				testCase.addBoldTextEvidenceCurrentStep("El campo Total Calculado e Importe cuadran");
			} else {
				testCase.addBoldTextEvidenceCurrentStep("El campo Total Calculado e Importe no cuadran");
			}
		}
		assertTrue(flag, "El registro existe en la BD");
		assertEquals(total_calculado, importe, "El campo Total Calculado e Importe no cuadran");

		/*
		 * Paso 4 "Validar que el importe mas el impuesto de la tabla
		 * XXFC_XP_CARGO_CENTRAL_LINES sea igual al monto de la tabla
		 * XXFC_XP_CARGO_CENTRAL_HEADERS con el siguiente query: SELECT h.INVOICE_NUM,
		 * SUM( CASE WHEN l.CODIGO_IMPUESTO = 'TASAEXENTO' THEN l.IMPORTE WHEN
		 * l.CODIGO_IMPUESTO = 'TGIVAAFIJO' THEN l.IMPORTE + (l.IMPORTE * 0.16) ELSE 0
		 * END ) AS CALCULATED_AMOUNT_WITH_TAX, h.AMOUNT FROM
		 * APPS.XXFC_XP_CARGO_CENTRAL_LINES l JOIN APPS.XXFC_XP_CARGO_CENTRAL_HEADERS h
		 * ON l.INVOICE_NUM = h.INVOICE_NUM WHERE l.INVOICE_NUM IN ('3567','1111111120')
		 * GROUP BY l.INVOICE_NUM, h.AMOUNT, h.INVOICE_NUM"
		 * 
		 */

		addStep("\"Validar que el importe mas el impuesto de la tabla XXFC_XP_CARGO_CENTRAL_LINES sea igual al monto de la tabla XXFC_XP_CARGO_CENTRAL_HEADERS con el siguiente query:\r\n"
				+ "SELECT h.INVOICE_NUM,\r\n" + "    SUM(\r\n" + "        CASE\r\n"
				+ "            WHEN l.CODIGO_IMPUESTO = 'TASAEXENTO' THEN l.IMPORTE\r\n"
				+ "            WHEN l.CODIGO_IMPUESTO = 'TGIVAAFIJO' THEN l.IMPORTE + (l.IMPORTE * 0.16)\r\n"
				+ "            ELSE 0\r\n" + "        END\r\n" + "    ) AS CALCULATED_AMOUNT_WITH_TAX, h.AMOUNT\r\n"
				+ "FROM\r\n" + "    APPS.XXFC_XP_CARGO_CENTRAL_LINES l\r\n" + "JOIN\r\n"
				+ "    APPS.XXFC_XP_CARGO_CENTRAL_HEADERS h ON l.INVOICE_NUM = h.INVOICE_NUM\r\n" + "WHERE\r\n"
				+ "    l.INVOICE_NUM IN ('3567','1111111120')\r\n" + "GROUP BY\r\n"
				+ "    l.INVOICE_NUM, h.AMOUNT, h.INVOICE_NUM\"\r\n" + "");

		// queryEBS5
		dbEBS = new utils.sql.SQLUtil(GlobalVariables.DB_HOST_AVEBQA, GlobalVariables.DB_USER_AVEBQA,
				GlobalVariables.DB_PASSWORD_AVEBQA);
		resultadoContenedor = dbEBS.executeQuery(queryEBS5_1 + " '" + invoiceNum + "' " + queryEBS5_2);
		testCase.addQueryEvidenceCurrentStep(resultadoContenedor);
		flag = false;
		if (!resultadoContenedor.isEmpty()) {
			flag = true;
			CALCULATED_AMOUNT_WITH_TAX = resultadoContenedor.getData(0, 1);
			AMOUNT = resultadoContenedor.getData(0, 2);

			if (CALCULATED_AMOUNT_WITH_TAX.equals(AMOUNT)) {
				testCase.addBoldTextEvidenceCurrentStep("El campo CALCULATED_AMOUNT_WITH_TAX y AMOUNT cuadran");
			} else {
				testCase.addBoldTextEvidenceCurrentStep("El campo CALCULATED_AMOUNT_WITH_TAX y AMOUNT no cuadran");
			}
		}
		assertTrue(flag, "El registro existe en la BD");
		assertEquals(total_calculado, importe, "El campo CALCULATED_AMOUNT_WITH_TAX y AMOUNT cuadran");

	}

	@Override
	public void beforeTest() {
		// TODO Auto-generated method stub

	}

	@Override
	public String setPrerequisites() {
		// TODO Auto-generated method stub
		return "Tener acceso a las BD de AVEBQA\r\n" + "";
	}

	@Override
	public String setTestDescription() {
		// TODO Auto-generated method stub
		return "Se valida que la sumatoria de los importes mas el impuesto de la tabla XXFC_XP_CARGO_CENTRAL_LINES sea igual al monto reflejado en la tabla XXFC_XP_CARGO_CENTRAL_HEADERS\r\n"
				+ "";
	}

	@Override
	public String setTestDesigner() {
		// TODO Auto-generated method stub
		return "JesusM3@Hexaware.com";
	}

	@Override
	public String setTestFullName() {
		// TODO Auto-generated method stub
		return "ATC_FT_002_Comprobar_recepcion_y_sumatoria_en_las_tablas_XXFC_XP_CARGO_CENTRAL_LINES_XXFC_XP_CARGO_CENTRAL_HEADERS_XXFC_XP_RELACION_UUIDS_TMP_test";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}

}
