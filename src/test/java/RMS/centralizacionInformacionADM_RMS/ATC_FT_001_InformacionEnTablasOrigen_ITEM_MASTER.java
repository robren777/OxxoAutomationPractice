package RMS.centralizacionInformacionADM_RMS;

import static org.testng.Assert.assertTrue;

import java.util.HashMap;

import org.testng.annotations.Test;

import modelo.BaseExecution;
import RMS.util.DbUtils;
import RMS.util.GlobalVariables;
import utils.sql.SQLResult;
import utils.sql.SQLUtil;

public class ATC_FT_001_InformacionEnTablasOrigen_ITEM_MASTER extends BaseExecution {

	@Test(dataProvider = "data-provider")
	public void ATC_FT_001_InformacionEnTablasOrigen_ITEM_MASTER_test(HashMap<String, String> data) throws Exception {

		SQLUtil dbRms = new SQLUtil(GlobalVariables.DB_HOST_BDRMSCQA, GlobalVariables.DB_USER_BDRMSCQA,
				GlobalVariables.DB_PASSWORD_BDRMSCQA);

		SQLResult connectionResult;
		String query;
		DbUtils dbu = new DbUtils();

		// Paso 1
		addStep("Ingresar a BD de RMS_COL" + "Realizar la siguiente consulta:" + "select * from ITEM_MASTER\r\n"
				+ "order by LAST_UPDATE_DATETIME desc;\r\n" + "");
		query = "Sselect * from ITEM_MASTER\r\n" + "where rownum <=10\r\n" + "order by LAST_UPDATE_DATETIME desc;";
		connectionResult = executeQuery(dbRms, query);
		boolean result = dbu.validarConexion(connectionResult);
		assertTrue(result, "Fallo de conexion con la base de datos");

	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_001_InformacionEnTablasOrigen_ITEM_MASTER_test";
	}

	@Override
	public String setTestDescription() {
		return "ATC_FT_001_InformacionEnTablasOrigen_ITEM_MASTER";
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
