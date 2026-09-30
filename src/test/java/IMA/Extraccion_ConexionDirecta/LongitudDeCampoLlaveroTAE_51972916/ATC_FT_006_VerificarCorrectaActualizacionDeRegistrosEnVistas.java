package IMA.Extraccion_ConexionDirecta.LongitudDeCampoLlaveroTAE_51972916;

import static org.testng.Assert.assertFalse;

import java.util.HashMap;

import org.testng.annotations.Test;

import IMA.util.GlobalVariables;
import modelo.BaseExecution;
import utils.sql.SQLResult;
import utils.sql.SQLUtil;

public class ATC_FT_006_VerificarCorrectaActualizacionDeRegistrosEnVistas extends BaseExecution {

	@Test(dataProvider = "data-provider")
	public void ATC_FT_006_VerificarCorrectaActualizacionDeRegistrosEnVistas_test(HashMap<String, String> data)
			throws Exception {
		/***************************************
		 * BASE DATOS
		 ***************************************/
		SQLUtil dbBI = new SQLUtil(GlobalVariables.DB_HOST_BI, GlobalVariables.DB_USER_BI,
				GlobalVariables.DB_PASSWORD_BI);

		/***************************************
		 * VARIABLES
		 ***************************************/
		String QueryTaeMesop = "select * from (SELECT * FROM BIDMMKT.VM_FCT_TAE_MESOP) WHERE ROWNUM <= 5";

		String QueryGrupoClienteTaeMesop = "select * from (SELECT GRUPOCLIENTE_KEY FROM BIDMMKT.VM_FCT_TAE_MESOP WHERE GRUPOCLIENTE_KEY >99) WHERE ROWNUM <= 5";

		String QueryTaeFacMargen = "select * from (SELECT * FROM BIDMMKT.VM_FCT_TAE_FACTOR_MARGEN) WHERE ROWNUM <= 5";

		String QueryGrupoClienteTaeFacMargen = "select * from (SELECT GRUPOCLIENTE_KEY FROM BIDMMKT.VM_FCT_TAE_FACTOR_MARGEN WHERE GRUPOCLIENTE_KEY >99) WHERE ROWNUM <= 5";

		String QueryTaeControlCarga = "select * from (SELECT * FROM BIDMMKT.VM_CONTROLCARGA_TAE) WHERE ROWNUM <= 5";

		String QueryGCTaeControlCarga = "select * from (SELECT GRUPOCLIENTE_KEY FROM BIDMMKT.VM_CONTROLCARGA_TAE WHERE GRUPOCLIENTE_KEY >99) WHERE ROWNUM <= 5";

		String QueryTaeTransac = "select * from (SELECT * FROM BIDMMKT.VM_FACT_TAE_TRANSACTION) WHERE ROWNUM <= 5";

		String QueryGCTaeTransac = "select * from (SELECT GRUPOCLIENTE_KEY FROM BIDMMKT.VM_FACT_TAE_TRANSACTION WHERE GRUPOCLIENTE_KEY >99) WHERE ROWNUM <= 5";

		/***************************************
		 * PASO 1
		 ***************************************/
		addStep("Validar que se tengan registros en la tabla BIDMMKT.VM_FCT_TAE_MESOP");

		System.out.println(GlobalVariables.DB_HOST_BI);
		System.out.println("\n" + QueryTaeMesop);

		SQLResult ExecuteQuery = executeQuery(dbBI, QueryTaeMesop);

		boolean StatusQuery = ExecuteQuery.isEmpty();
		System.out.println(StatusQuery);
		if (!StatusQuery) {
			testCase.addQueryEvidenceCurrentStep(ExecuteQuery);
		} else {
			testCase.addTextEvidenceCurrentStep("La tabla BIDMMKT.VM_FCT_TAE_MESOP no tiene registros.");
			testCase.addQueryEvidenceCurrentStep(ExecuteQuery);
		}
		assertFalse(StatusQuery, "La tabla BIDMMKT.VM_FCT_TAE_MESOP no tiene registros.");

		/***************************************
		 * PASO 2
		 ***************************************/
		addStep("Validar que el campo GRUPOCLIENTE_KEY tenga registros mayores a 99 en la tabla BIDMMKT.VM_FCT_TAE_MESOP");

		System.out.println(GlobalVariables.DB_HOST_BI);
		System.out.println("\n" + QueryGrupoClienteTaeMesop);

		SQLResult ExecuteQuery2 = executeQuery(dbBI, QueryGrupoClienteTaeMesop);

		boolean StatusQuery2 = ExecuteQuery2.isEmpty();
		System.out.println(StatusQuery2);
		if (!StatusQuery2) {
			testCase.addQueryEvidenceCurrentStep(ExecuteQuery2);
		} else {
			testCase.addTextEvidenceCurrentStep(
					"GrupoCliente_key no tiene registros mayores a 99. Tabla BIDMMKT.VM_FCT_TAE_MESOP.");
			testCase.addQueryEvidenceCurrentStep(ExecuteQuery2);
		}
		assertFalse(StatusQuery2, "GrupoCliente_key no tiene registros mayores a 99. Tabla BIDMMKT.VM_FCT_TAE_MESOP.");

		/***************************************
		 * PASO 3
		 ***************************************/
		addStep("Validar que se tengan registros en la tabla BIDMMKT.VM_FCT_TAE_FACTOR_MARGEN");

		System.out.println(GlobalVariables.DB_HOST_BI);
		System.out.println("\n" + QueryTaeFacMargen);

		SQLResult ExecuteQuery3 = executeQuery(dbBI, QueryTaeFacMargen);

		boolean StatusQuery3 = ExecuteQuery3.isEmpty();
		System.out.println(StatusQuery3);
		if (!StatusQuery3) {
			testCase.addQueryEvidenceCurrentStep(ExecuteQuery3);
		} else {
			testCase.addTextEvidenceCurrentStep("La tabla BIDMMKT.VM_FCT_TAE_FACTOR_MARGEN no tiene registros.");
			testCase.addQueryEvidenceCurrentStep(ExecuteQuery3);
		}
		// la consulta en la tabla no tiene registros con GrupoCliente_key> 99
		assertFalse(StatusQuery3, "La tabla BIDMMKT.VM_FCT_TAE_FACTOR_MARGEN no tiene registros.");

		/***************************************
		 * PASO 4
		 ***************************************/
		addStep("Validar que el campo GRUPOCLIENTE_KEY tenga registros mayores a 99 en la tabla BIDMMKT.VM_FCT_TAE_FACTOR_MARGEN");

		System.out.println(GlobalVariables.DB_HOST_BI);
		System.out.println("\n" + QueryGrupoClienteTaeFacMargen);

		SQLResult ExecuteQuery4 = executeQuery(dbBI, QueryGrupoClienteTaeFacMargen);

		boolean StatusQuery4 = ExecuteQuery4.isEmpty();
		System.out.println(StatusQuery4);
		if (!StatusQuery4) {
			testCase.addQueryEvidenceCurrentStep(ExecuteQuery4);
		} else {
			testCase.addTextEvidenceCurrentStep(
					"GrupoCliente_key no tiene registros mayores a 99. Tabla BIDMMKT.VM_FCT_TAE_FACTOR_MARGEN");
			testCase.addQueryEvidenceCurrentStep(ExecuteQuery4);
		}
		assertFalse(StatusQuery4,
				"GrupoCliente_key no tiene registros mayores a 99. Tabla BIDMMKT.VM_FCT_TAE_FACTOR_MARGEN");

		/***************************************
		 * PASO 5
		 ***************************************/
		addStep("Validar que se tengan registros en la tabla BIDMMKT.VM_CONTROLCARGA_TAE");

		System.out.println(GlobalVariables.DB_HOST_BI);
		System.out.println("\n" + QueryTaeControlCarga);

		SQLResult ExecuteQuery5 = executeQuery(dbBI, QueryTaeControlCarga);

		boolean StatusQuery5 = ExecuteQuery5.isEmpty();
		System.out.println(StatusQuery5);
		if (!StatusQuery5) {
			testCase.addQueryEvidenceCurrentStep(ExecuteQuery5);
		} else {
			testCase.addTextEvidenceCurrentStep("La tabla BIDMMKT.VM_CONTROLCARGA_TAE no tiene registros.");
			testCase.addQueryEvidenceCurrentStep(ExecuteQuery5);
		}
		assertFalse(StatusQuery5, "La tabla BIDMMKT.VM_CONTROLCARGA_TAE no tiene registros.");

		/***************************************
		 * PASO 6
		 ***************************************/
		addStep("Validar que el campo GRUPOCLIENTE_KEY tenga registros mayores a 99 en la tabla BIDMMKT.VM_CONTROLCARGA_TAE");

		System.out.println(GlobalVariables.DB_HOST_BI);
		System.out.println("\n" + QueryGCTaeControlCarga);

		SQLResult ExecuteQuery6 = executeQuery(dbBI, QueryGCTaeControlCarga);

		boolean StatusQuery6 = ExecuteQuery6.isEmpty();
		System.out.println(StatusQuery6);
		if (!StatusQuery6) {
			testCase.addQueryEvidenceCurrentStep(ExecuteQuery6);
		} else {
			testCase.addTextEvidenceCurrentStep(
					"GrupoCliente_key no tiene registros mayores a 99. Tabla BIDMMKT.VM_CONTROLCARGA_TAE");
			testCase.addQueryEvidenceCurrentStep(ExecuteQuery6);
		}
		assertFalse(StatusQuery6,
				"GrupoCliente_key no tiene registros mayores a 99. Tabla BIDMMKT.VM_CONTROLCARGA_TAE");

		/***************************************
		 * PASO 7
		 ***************************************/
		addStep("Validar que se tengan registros en la tabla BIDMMKT.VM_FACT_TAE_TRANSACTION");

		System.out.println(GlobalVariables.DB_HOST_BI);
		System.out.println("\n" + QueryTaeTransac);

		SQLResult ExecuteQuery7 = executeQuery(dbBI, QueryTaeTransac);

		boolean StatusQuery7 = ExecuteQuery7.isEmpty();
		System.out.println(StatusQuery7);
		if (!StatusQuery7) {
			testCase.addQueryEvidenceCurrentStep(ExecuteQuery7);
		} else {
			testCase.addTextEvidenceCurrentStep("La tabla BIDMMKT.VM_FACT_TAE_TRANSACTION no tiene registros.");
			testCase.addQueryEvidenceCurrentStep(ExecuteQuery7);
		}
		assertFalse(StatusQuery7, "La tabla BIDMMKT.VM_FACT_TAE_TRANSACTION no tiene registros.");

		/***************************************
		 * PASO 8
		 ***************************************/
		addStep("Validar que el campo GRUPOCLIENTE_KEY tenga registros mayores a 99 en la tabla BIDMMKT.VM_FACT_TAE_TRANSACTION");

		System.out.println(GlobalVariables.DB_HOST_BI);
		System.out.println("\n" + QueryGCTaeTransac);

		SQLResult ExecuteQuery8 = executeQuery(dbBI, QueryGCTaeTransac);

		boolean StatusQuery8 = ExecuteQuery8.isEmpty();
		System.out.println(StatusQuery8);
		if (!StatusQuery8) {
			testCase.addQueryEvidenceCurrentStep(ExecuteQuery8);
		} else {
			testCase.addTextEvidenceCurrentStep(
					"GrupoCliente_key no tiene registros mayores a 99. Tabla BIDMMKT.VM_FACT_TAE_TRANSACTION");
			testCase.addQueryEvidenceCurrentStep(ExecuteQuery8);
		}
		assertFalse(StatusQuery8,
				"GrupoCliente_key no tiene registros mayores a 99. Tabla BIDMMKT.VM_FACT_TAE_TRANSACTION");

	}

	@Override
	public String setTestFullName() {
		// TODO Auto-generated method stub
		return "ATC_FT_006_VerificarCorrectaActualizacionDeRegistrosEnVistas_test";
	}

	@Override
	public String setTestDescription() {
		// TODO Auto-generated method stub
		return "CP006 - Verificar la correcta actualizaci�n de los registros en las vistas";
	}

	@Override
	public String setTestDesigner() {
		// TODO Auto-generated method stub
		return "EQUIPO AUTOMATIZACION";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void beforeTest() {
		// TODO Auto-generated method stub
	}

	@Override
	public String setPrerequisites() {
		// TODO Auto-generated method stub
		return null;
	}
}
