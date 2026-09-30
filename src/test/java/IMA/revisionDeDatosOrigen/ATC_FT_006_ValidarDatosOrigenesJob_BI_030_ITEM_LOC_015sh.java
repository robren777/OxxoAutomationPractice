package IMA.revisionDeDatosOrigen;

import java.util.HashMap;

import org.testng.annotations.Test;

import IMA.util.GlobalVariables;
import IMA.util.IMATools;
import modelo.BaseExecution;
import utils.sql.SQLResult;

public class ATC_FT_006_ValidarDatosOrigenesJob_BI_030_ITEM_LOC_015sh extends BaseExecution {
	@Test(dataProvider = "data-provider")
	public void ATC_FT_006_ValidarDatosOrigenesJob_BI_030_ITEM_LOC_015sh_test(HashMap<String, String> data)
			throws Exception {

		IMATools ima = new IMATools(null, testCase);
		String query;
		SQLResult connectionResult;

		/**
		 * No se indica que base de datos se debe usar, en este caso se usa una para
		 * rellenar codigo.
		 */
		utils.sql.SQLUtil DB = new utils.sql.SQLUtil(GlobalVariables.DB_HOST_BIMTTO, GlobalVariables.DB_USER_BIMTTO,
				GlobalVariables.DB_PASSWORD_BIMTTO);

		query = "SELECT * FROM BI_CATS.VM_030_ITEM_LOC";
		connectionResult = executeQuery(DB, query);
		ima.validarContenidoTablas(connectionResult, query);

		query = "SELECT * FROM BI_CATS.V_000_DIM_PRODUCTORETEK\r\n" + 
				"where PRODUCTO_KEY in \r\n" + 
				"(SELECT� DISTINCT PRODUCTO_KEY��� FROM��� BIDMMKT.VM_030_CAT_ITEM_LOC @DBL_001_FCDW4PRD.BIDMMKT\r\n" + 
				"WHERE ID_PROVEEDOR IN \r\n" + 
				"(\r\n" + 
				"207298283, 2785, 40863, 173037, 67255, 8802, 10472, 323738, 7847, 17491, 207227186, 207248338,\r\n" + 
				"627764, 896785, 16295, 1035, 14826, 14827, 19637, 19638, 22216, 70580, 61988, 59661, 29113, 70614,\r\n" + 
				"68150, 202631, 201051, 175, 222004, 185705, 16656236, 56395223, 49069151, 16886, 175761299, 9982,\r\n" + 
				"59086, 351741, 2183, 4814, 16507, 217798825, 207249454, 207272067, 207290553, 207299272, 207327711,\r\n" + 
				"207371430, 207399097, 207309912, 207343300));";
		connectionResult = executeQuery(DB, query);
		ima.validarContenidoTablas(connectionResult, query);

		query = "SELECT * FROM BI_CATS.V_001_DIM_TIENDA";
		connectionResult = executeQuery(DB, query);
		ima.validarContenidoTablas(connectionResult, query);

		query = "SELECT * FROM BI_CATS.V_000_PROVEEDOR_FUENTEABASTO\r\n" + 
				"where ID_PROVEEDOR IN\r\n" + 
				"( 207298283, 2785, 40863, 173037, 67255, 8802, 10472, 323738, 7847, 17491, 207227186, 207248338,\r\n" + 
				"627764, 896785, 16295, 1035, 14826, 14827, 19637, 19638, 22216, 70580, 61988, 59661, 29113, 70614,\r\n" + 
				"68150, 202631, 201051, 175, 222004, 185705, 16656236, 56395223, 49069151, 16886, 175761299, 9982,\r\n" + 
				"59086, 351741, 2183, 4814, 16507, 217798825, 207249454, 207272067, 207290553, 207299272, 207327711,\r\n" + 
				"207371430, 207399097, 207309912, 207343300);";
		connectionResult = executeQuery(DB, query);
		ima.validarContenidoTablas(connectionResult, query);
	}

	@Override
	public void beforeTest() {
		// TODO Auto-generated method stub

	}

	@Override
	public String setTestDescription() {
		// TODO Auto-generated method stub
		return "Validar datos para Job";
	}

	@Override
	public String setTestDesigner() {
		// TODO Auto-generated method stub
		return "EQUIPO AUTOMATIZACION";
	}

	@Override
	public String setTestFullName() {
		// TODO Auto-generated method stub
		return "ATC_FT_006_ValidarDatosOrigenesJob_BI_030_ITEM_LOC_015sh_test";
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
