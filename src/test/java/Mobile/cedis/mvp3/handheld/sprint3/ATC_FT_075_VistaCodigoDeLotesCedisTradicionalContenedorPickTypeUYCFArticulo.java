package Mobile.cedis.mvp3.handheld.sprint3;

import static org.testng.Assert.assertFalse;

import java.util.HashMap;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import Mobile.cedis.om.D01_Conexiones_BD;
import modelo.BaseExecution;
import utils.sql.SQLResult;

public class ATC_FT_075_VistaCodigoDeLotesCedisTradicionalContenedorPickTypeUYCFArticulo extends BaseExecution {
	@Test(dataProvider = "data-provider", enabled = true)
	public void ATC_FT_075_VistaCodigoDeLotesCedisTradicionalContenedorPickTypeUYCFArticulo_Test(HashMap<String, String> data) throws Exception {
		D01_Conexiones_BD POM_Conexiones = new D01_Conexiones_BD(testCase, null, this);
		//Pasos relacionados a crear la conexion con la base de datos de RDM Tlajomulco
		addStep("Se conecta correctamente a la base de datos mediante SQL Developer", "Validar la conexión exitosa");
		POM_Conexiones.verificarConexionRdmMty();

		//Pasos para hacer la consulta y validar que se encuentren las columnas de: tipo de distribucion, ruta original y ruta dinamica
		addStep("Ingresar la consulta: SELECT * FROM (SELECT TSF_NO, DEST_ID, CONTAINER_ID, ITEM_ID, PICK_TYPE, VERIF_CODE FROM XXFC_VERIFICACION_V WHERE ITEM_ID = '7891000248362' ORDER BY TSF_NO\r\n"
				+ ") WHERE ROWNUM <= 2", "Se ejecuto el query exitosamente y se despliegan la vista correctamente con la informacion requerida");
		SQLResult Resultado_Consulta_SQL = executeQuery(POM_Conexiones.connectionMTY, "SELECT * FROM (SELECT TSF_NO, DEST_ID, CONTAINER_ID, ITEM_ID, PICK_TYPE, VERIF_CODE FROM XXFC_VERIFICACION_V WHERE ITEM_ID = '7891000248362' ORDER BY TSF_NO\r\n"
				+ ") WHERE ROWNUM <= 2");
		testCase.addQueryEvidenceCurrentStep(Resultado_Consulta_SQL);
		assertFalse(Resultado_Consulta_SQL.isEmpty(), "No se realizó la consulta de manera correcta");
		
		//Pasos para hacer la consulta y validar que se encuentren las columnas de: tipo de distribucion, ruta original y ruta dinamica
				addStep("Dentro de la consulta busca el articulo clasificado en contenedores mixtos, verifica que la informacion que tiene corresponda a su tipo de Pick_Type (CF y U). Con el siguiente Query: SELECT \"DESCRIPTION\", ITEM_ID,ITEM_MASTER_UDA11, ITEM_MASTER_UDA12 FROM ITEM_MASTER WHERE ITEM_ID = '7891000248362'");
				SQLResult Resultado_Consulta_SQL2 = executeQuery(POM_Conexiones.connectionMTY, "SELECT \"DESCRIPTION\", ITEM_ID,ITEM_MASTER_UDA11, ITEM_MASTER_UDA12 FROM ITEM_MASTER WHERE ITEM_ID = '7891000248362'");
				testCase.addQueryEvidenceCurrentStep(Resultado_Consulta_SQL2);
				assertFalse(Resultado_Consulta_SQL2.isEmpty(), "No se realizó la consulta de manera correcta");

		//Finalizacion del Script y cierre de conexion
		POM_Conexiones.cerrarConexionRdmMty();
	}







	@Override
	public String setTestFullName() {
		return "ATC_FT_075_VistaCodigoDeLotesCedisTradicionalContenedorPickTypeUYCFArticulo";
	}

	@Override
	public String setTestDescription() {
		return "Validar que al consultar la vista de codigo de lotes la informacion que arroja un contenedor de tipo \"U\" (surtido por piezas) tenga la informacion del campo ITEM_MASTER_UDA12 y contenedor de tipo \"CF\" (surtido por cajas) tenga la informacion del campo ITEM_MASTER_UDA11";
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
				+ "*Tener la vista disponible para la BD RDM MTY\r\n"
				+ "*ASN con articulo mixto clasificado en cajas y por piezas\r\n"
				+ "*Tener la informacion de los picks del articulo a consultar (ITEM_MASTER)";
	}

	@BeforeTest
	public void beforeTest() {
	}
}