package Mobile.cedis.mvp3.handheld.sprint3;

import static org.testng.Assert.assertFalse;

import java.util.HashMap;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import Mobile.cedis.om.D01_Conexiones_BD;
import modelo.BaseExecution;
import utils.sql.SQLResult;

public class ATC_FT_072_VistaCodigoDeLotesCedisTradicional extends BaseExecution {
	@Test(dataProvider = "data-provider", enabled = true)
	public void ATC_FT_072_VistaCodigoDeLotesCedisTradicional_Test(HashMap<String, String> data) throws Exception {

		D01_Conexiones_BD POM_Conexiones = new D01_Conexiones_BD(testCase, null, this);
		//Pasos relacionados a crear la conexion con la base de datos de RDM Tlajomulco
		addStep("Se conecta correctamente a la base de datos mediante SQL Developer", "Validar la conexión exitosa");
		POM_Conexiones.verificarConexionRdmMty();

		//Pasos para hacer la consulta y validar que se encuentren las columnas de: tipo de distribucion, ruta original y ruta dinamica
		addStep("Ingresar la consulta: SELECT TSF_NO, DEST_ID, CONTAINER_ID, ITEM_ID, PICK_TYPE, VERIF_CODE FROM XXFC_VERIFICACION_V WHERE ROWNUM <= 4", "Ejecutar el query y verificar columnas: TSF_NO, DEST_ID, CONTAINER_ID, ITEM_ID, PICK_TYPE, VERIF_CODE");
		SQLResult Resultado_Consulta_SQL = executeQuery(POM_Conexiones.connectionMTY, "SELECT TSF_NO, DEST_ID, CONTAINER_ID, ITEM_ID, PICK_TYPE, VERIF_CODE FROM XXFC_VERIFICACION_V WHERE ROWNUM <= 4");
		testCase.addQueryEvidenceCurrentStep(Resultado_Consulta_SQL);
		assertFalse(Resultado_Consulta_SQL.isEmpty(), "No se realizó la consulta de manera correcta");

		//Finalizacion del Script y cierre de conexion
		POM_Conexiones.cerrarConexionRdmMty();
	}







	@Override
	public String setTestFullName() {
		return "ATC_FT_072_VistaCodigoDeLotesCedisTradicional";
	}

	@Override
	public String setTestDescription() {
		return "Validar que la vista contenga lo siguientes campos: \r\n"
				+ "xxfc_posterior_embarque_citems: pedido (tsf_no), tienda (dest_id), contenedor (container_id), articulo, (item_id), tipo de pickeo (pick_type).\r\n"
				+ "item_master: codigo de verificacion (nombre \"verif_code\": item_master_uda11, item_master_uda12).";
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
		return "*Contar con VPN\r\n"
				+ "*Contar con la BD RDM MTY\r\n"
				+ "*Contar con el query de la vista\r\n"
				+ "*Tener la vista disponible para la BD RDM MTY";
	}

	@BeforeTest
	public void beforeTest() {
	}
}
