package IMA.revisionDeDatosOrigen;

import java.util.HashMap;

import org.testng.annotations.Test;

import IMA.util.GlobalVariables;
import IMA.util.IMATools;
import modelo.BaseExecution;
import utils.sql.SQLResult;

public class ATC_FT_015_ValidarDatosOrigenesJob_BI_030_ADEPROVEEDORES_020 extends BaseExecution {
	@Test(dataProvider = "data-provider")
	public void ATC_FT_015_ValidarDatosOrigenesJob_BI_030_ADEPROVEEDORES_020_test(HashMap<String, String> data)
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

		query = "SELECT * FROM BIDMMKT.DM_FACT_VENTA_MES WHERE MES_ID=>>";
		connectionResult = executeQuery(DB, query);
		ima.validarContenidoTablas(connectionResult, query);

		query = "SELECT * FROM BIDMMKT.CFG_000_CONTROL_CARGA";
		connectionResult = executeQuery(DB, query);
		ima.validarContenidoTablas(connectionResult, query);

		query = "SELECT * FROM BIDMMKT.VM_030_CAT_ITEM_LOC";
		connectionResult = executeQuery(DB, query);
		ima.validarContenidoTablas(connectionResult, query);

		query = "SELECT * FROM DWADMIN.DIM_TIENDA";
		connectionResult = executeQuery(DB, query);
		ima.validarContenidoTablas(connectionResult, query);

		query = "SELECT * FROM BIDMMKT.DIM_PRODUCTORETEK";
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
		return "ATC_FT_015_ValidarDatosOrigenesJob_BI_030_ADEPROVEEDORES_020_test";
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
