package Mobile.cedis.mvp3.handheld.sprint9;

import static org.testng.Assert.assertFalse;

import java.util.HashMap;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import Mobile.cedis.om.D01_Conexiones_BD;
import modelo.BaseExecution;
import utils.sql.SQLResult;

public class ATC_FT_076_Vista_Codigo_de_lotes_Cedis_Automatizado extends BaseExecution {
	@Test(dataProvider = "data-provider", enabled = true)
	public void ATC_FT_076_Vista_Codigo_de_lotes_Cedis_Automatizado_Test(HashMap<String, String> data) throws Exception {
		D01_Conexiones_BD POM_Conexiones = new D01_Conexiones_BD(testCase, null, this);
		//Pasos relacionados a crear la conexion con la base de datos de RDM Tlajomulco
		addStep("Se conecta correctamente a la base de datos mediante SQL Developer", "Validar la conexión exitosa");
		POM_Conexiones.verificarConexionRDM100();

		//Pasos para hacer la consulta y validar que se encuentren las columnas de: tipo de distribucion, ruta original y ruta dinamica
		addStep("Se ingresa la siguiente consulta: SELECT TSF_NO, DEST_ID, CONTAINER_ID, ITEM_ID, PICK_TYPE, VERIF_CODE FROM xxfc_verificacion_v WHERE ROWNUM <= 3", "Se ejecuto el query exitosamente y se despliegan la vista correctamente con la informacion requerida");
		SQLResult Resultado_Consulta_SQL = executeQuery(POM_Conexiones.connectionTlajo, "SELECT TSF_NO, DEST_ID, CONTAINER_ID, ITEM_ID, PICK_TYPE, VERIF_CODE FROM xxfc_verificacion_v WHERE ROWNUM <= 3");
		testCase.addQueryEvidenceCurrentStep(Resultado_Consulta_SQL);
		assertFalse(Resultado_Consulta_SQL.isEmpty(), "No se realizó la consulta de manera correcta");

		//Finalizacion del Script y cierre de conexion
		POM_Conexiones.cerrarConexionRDM100();
	}







	@Override
	public String setTestFullName() {
		return "ATC-FT-023 Recepción de producto Cerveza con envases retornables ";
	}

	@Override
	public String setTestDescription() {
		return "Validar que al realizar una recepción de un pedido mediante escaner con producto ENVASE se registre la entrada tanto para el producto como para el envase con cedis automatizado";
	}

	@Override
	public String setTestDesigner() {
		return "Marcelo Gutiérrez";
	}

	@Override
	public String setTestInstanceID() {
		return null;
	}

	@Override
	public String setPrerequisites() {
		return "1.Usuario y Contraseña de Aplicación conexión a BD RDM TLA\r\n"
				+ "2.  Numero de la transferencia a utilizar\r\n"
				+ "3. Pedido contenga bultos tipo canastilla\r\n"
				+ "4. Contar con un ASN con artículo cerveza que contenga envase retornable";
	}

	@BeforeTest
	public void beforeTest() {
	}
}