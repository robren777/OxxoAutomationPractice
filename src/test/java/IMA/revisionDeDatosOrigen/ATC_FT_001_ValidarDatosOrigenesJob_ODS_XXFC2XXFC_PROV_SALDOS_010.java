package IMA.revisionDeDatosOrigen;

import java.util.HashMap;

import org.testng.annotations.Test;

import IMA.util.GlobalVariables;
import IMA.util.IMATools;
import modelo.BaseExecution;
import utils.sql.SQLResult;

public class ATC_FT_001_ValidarDatosOrigenesJob_ODS_XXFC2XXFC_PROV_SALDOS_010 extends BaseExecution {
	@Test(dataProvider = "data-provider")
	public void ATC_FT_001_ValidarDatosOrigenesJob_ODS_XXFC2XXFC_PROV_SALDOS_010_test(HashMap<String, String> data)
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

		query = "SELECT * FROM EBS_AP.XXFC_AP_PROV_SALDOS A JOIN BIETL.CFG_000_CONTROL_CARGA C ON ((A.CREATION_DATE BETWEEN C.FECHAINICIO AND C.FECHAFIN))WHERE (C.ID_SUJETO= 2)";
		addStep("Se valida la conexion a la base de datos y se realiza la consulta en la tabla BIDMMKT.DM_FACT_VENTA_MES");
		connectionResult = executeQuery(DB, query);
		ima.validarConexion(connectionResult);
		testCase.addTextEvidenceCurrentStep(query);
		
		addStep("Se valida el contenido de la tabla");
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
		return "ATC_FT_001_ValidarDatosOrigenesJob_ODS_XXFC2XXFC_PROV_SALDOS_010_test";
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