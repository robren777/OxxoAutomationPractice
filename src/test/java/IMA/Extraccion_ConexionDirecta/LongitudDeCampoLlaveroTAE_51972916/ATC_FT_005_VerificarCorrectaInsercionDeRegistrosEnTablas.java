package IMA.Extraccion_ConexionDirecta.LongitudDeCampoLlaveroTAE_51972916;

import static org.testng.Assert.assertFalse;

import java.util.HashMap;

import org.testng.annotations.Test;

import IMA.util.GlobalVariables;
import modelo.BaseExecution;
import utils.sql.SQLResult;
import utils.sql.SQLUtil;

public class ATC_FT_005_VerificarCorrectaInsercionDeRegistrosEnTablas extends BaseExecution {
	@Test(dataProvider = "data-provider")
	public void ATC_FT_005_VerificarCorrectaInsercionDeRegistrosEnTablas_test(HashMap<String, String> data)
			throws Exception {
		/***************************************
		 * BASE DATOS
		 ***************************************/
		SQLUtil dbBI = new SQLUtil(GlobalVariables.DB_HOST_BI, GlobalVariables.DB_USER_BI,
				GlobalVariables.DB_PASSWORD_BI);

		/***************************************
		 * VARIABLES
		 ***************************************/
		String QueryClienteLlavero = "select * from (SELECT * FROM BIDMMKT.DIM_TAE_CLIENTES_LLAVERO) WHERE ROWNUM <= 5";

		String QueryGrupoClienteTae = "select * from (SELECT GRUPOCLIENTE_KEY FROM BIDMMKT.DIM_TAE_CLIENTES_LLAVERO"
				+ " WHERE GRUPOCLIENTE_KEY >99) WHERE ROWNUM <= 5";

		String QueryNumLlavero = "select * from (SELECT * FROM BIDMMKT.FCT_NUM_LLAVEROS) WHERE ROWNUM <= 5";

		String QueryGCKNumLlavero = "select * from (SELECT GRUPOCLIENTE_KEY FROM BIDMMKT.FCT_NUM_LLAVEROS WHERE GRUPOCLIENTE_KEY >99)"
				+ " WHERE ROWNUM <= 5";

		String QueryLlaverosEntregados = "select * from (SELECT * FROM BIDMMKT.FCT_LLAVEROS_ENTREGADOS) WHERE ROWNUM <=5";

		String QueryGCKllaverosEntregados = "select * from (SELECT GRUPOCLIENTE_KEY FROM BIDMMKT.FCT_LLAVEROS_ENTREGADOS WHERE GRUPOCLIENTE_KEY >99)"
				+ " WHERE ROWNUM <=5";

		String QueryTaeNumCli = "select * from (SELECT * FROM BIDMMKT.FCT_TAE_NUMCTES) WHERE ROWNUM <= 5";

		String QueryGCKtaeNumCli = "select * from(SELECT GRUPOCLIENTE_KEY FROM BIDMMKT.FCT_TAE_NUMCTES WHERE GRUPOCLIENTE_KEY >99) "
				+ " WHERE ROWNUM <=5";

		String QueryTaeRenta = "select *from (SELECT * FROM BIDMMKT.FCT_TAE_RENTABILIDAD) WHERE ROWNUM <=5";

		String QueryGCKtaeRenta = "select * from (SELECT GRUPOCLIENTE_KEY FROM BIDMMKT.FCT_TAE_RENTABILIDAD WHERE GRUPOCLIENTE_KEY >99)"
				+ " WHERE ROWNUM <=5";

		String QueryVentaSLlavero = "select *from (SELECT * FROM BIDMMKT.FCT_TAE_VENTAS_SLLAVERO) WHERE ROWNUM <=5";

		String QueryGCKventaSllavero = "select * from (SELECT GRUPOCLIENTE_KEY FROM BIDMMKT.FCT_TAE_VENTAS_SLLAVERO WHERE GRUPOCLIENTE_KEY >99)"
				+ " WHERE ROWNUM <=5";

		String QueryVentaLlavero = "select *from (SELECT * FROM BIDMMKT.FCT_TAE_VTALLAVERO_GPOCTE) WHERE ROWNUM <=5";

		String QueryGCKventaLlavero = "select * from (SELECT GRUPOCLIENTE_KEY FROM BIDMMKT.FCT_TAE_VTALLAVERO_GPOCTE WHERE GRUPOCLIENTE_KEY >99)"
				+ " WHERE ROWNUM <=5";

		String QueryVentaProm = "select *from (SELECT * FROM BIDMMKT.FCT_TAE_VTAPROM_GPOTDA) WHERE ROWNUM <=5";

		String QueryGCKventaProm = "select * from (SELECT GRUPOCLIENTE_KEY FROM BIDMMKT.FCT_TAE_VTAPROM_GPOTDA WHERE GRUPOCLIENTE_KEY >99)"
				+ " WHERE ROWNUM <=5";

		String QueryTmpTaeRenta = "select *from (SELECT * FROM BIDMMKT.TMP_FCT_TAE_RENTABILIDAD) WHERE ROWNUM <=5";

		String QueryGCKtmpTaeRenta = "select * from (SELECT GRUPOCLIENTE_KEY FROM BIDMMKT.TMP_FCT_TAE_RENTABILIDAD WHERE GRUPOCLIENTE_KEY >99)"
				+ " WHERE ROWNUM <=5";

		/***************************************
		 * PASO 1
		 ***************************************/
		addStep("Validar que se tengan registros en la tabla BIDMMKT.DIM_TAE_CLIENTES_LLAVERO");

		System.out.println(GlobalVariables.DB_HOST_BI);
		System.out.println("\n" + QueryClienteLlavero);

		SQLResult ExecuteQuery = executeQuery(dbBI, QueryClienteLlavero);

		boolean StatusQuery = ExecuteQuery.isEmpty();
		System.out.println(StatusQuery);
		if (!StatusQuery) {
			testCase.addQueryEvidenceCurrentStep(ExecuteQuery);
		} else {
			testCase.addTextEvidenceCurrentStep("La tabla BIDMMKT.DIM_TAE_CLIENTES_LLAVERO no tiene registros.");
			testCase.addQueryEvidenceCurrentStep(ExecuteQuery);
		}
		assertFalse(StatusQuery, "La tabla BIDMMKT.DIM_TAE_CLIENTES_LLAVERO no tiene registros.");

		/***************************************
		 * PASO 2
		 ***************************************/
		addStep("Validar que el campo GRUPOCLIENTE_KEY tenga registros mayores a 99 en la tabla BIDMMKT.DIM_TAE_CLIENTES_LLAVERO");

		System.out.println(GlobalVariables.DB_HOST_BI);
		System.out.println("\n" + QueryGrupoClienteTae);

		SQLResult ExecuteQuery2 = executeQuery(dbBI, QueryGrupoClienteTae);

		boolean StatusQuery2 = ExecuteQuery2.isEmpty();
		System.out.println(StatusQuery2);
		if (!StatusQuery2) {
			testCase.addQueryEvidenceCurrentStep(ExecuteQuery2);
		} else {
			testCase.addTextEvidenceCurrentStep(
					"GrupoCliente_key no tiene registros mayores a 99. Tabla BIDMMKT.DIM_TAE_CLIENTES_LLAVERO.");
			testCase.addQueryEvidenceCurrentStep(ExecuteQuery2);
		}
		assertFalse(StatusQuery2,
				"GrupoCliente_key no tiene registros mayores a 99. Tabla BIDMMKT.DIM_TAE_CLIENTES_LLAVERO.");

		/***************************************
		 * PASO 3
		 ***************************************/
		addStep("Validar que se tengan registros en la tabla BIDMMKT.FCT_NUM_LLAVEROS");

		System.out.println(GlobalVariables.DB_HOST_BI);
		System.out.println("\n" + QueryNumLlavero);

		SQLResult ExecuteQuery3 = executeQuery(dbBI, QueryNumLlavero);

		boolean StatusQuery3 = ExecuteQuery3.isEmpty();
		System.out.println(StatusQuery3);
		if (!StatusQuery3) {
			testCase.addQueryEvidenceCurrentStep(ExecuteQuery3);
		} else {
			testCase.addTextEvidenceCurrentStep("La tabla BIDMMKT.FCT_NUM_LLAVEROS no tiene registros.");
			testCase.addQueryEvidenceCurrentStep(ExecuteQuery3);
		}
		assertFalse(StatusQuery3, "La tabla BIDMMKT.FCT_NUM_LLAVEROS no tiene registros.");

		/***************************************
		 * PASO 4
		 ***************************************/
		addStep("Validar que el campo GRUPOCLIENTE_KEY tenga registros mayores a 99 en la tabla BIDMMKT.FCT_NUM_LLAVEROS");

		System.out.println(GlobalVariables.DB_HOST_BI);
		System.out.println("\n" + QueryGCKNumLlavero);

		SQLResult ExecuteQuery4 = executeQuery(dbBI, QueryGCKNumLlavero);

		boolean StatusQuery4 = ExecuteQuery4.isEmpty();
		System.out.println(StatusQuery4);
		if (!StatusQuery4) {
			testCase.addQueryEvidenceCurrentStep(ExecuteQuery4);
		} else {
			testCase.addTextEvidenceCurrentStep(
					"GrupoCliente_key no tiene registros mayores a 99. Tabla BIDMMKT.FCT_NUM_LLAVEROS");
			testCase.addQueryEvidenceCurrentStep(ExecuteQuery4);
		}
		assertFalse(StatusQuery4, "GrupoCliente_key no tiene registros mayores a 99. Tabla BIDMMKT.FCT_NUM_LLAVEROS");

		/***************************************
		 * PASO 5
		 ***************************************/
		addStep("Validar que se tengan registros en la tabla BIDMMKT.FCT_LLAVEROS_ENTREGADOS");

		System.out.println(GlobalVariables.DB_HOST_BI);
		System.out.println("\n" + QueryLlaverosEntregados);

		SQLResult ExecuteQuery5 = executeQuery(dbBI, QueryLlaverosEntregados);

		boolean StatusQuery5 = ExecuteQuery5.isEmpty();
		System.out.println(StatusQuery5);
		if (!StatusQuery5) {
			testCase.addQueryEvidenceCurrentStep(ExecuteQuery5);
		} else {
			testCase.addTextEvidenceCurrentStep("La tabla BIDMMKT.FCT_LLAVEROS_ENTREGADOS no tiene registros.");
			testCase.addQueryEvidenceCurrentStep(ExecuteQuery5);
		}
		assertFalse(StatusQuery5, "La tabla BIDMMKT.FCT_LLAVEROS_ENTREGADOS no tiene registros.");

		/***************************************
		 * PASO 6
		 ***************************************/
		addStep("Validar que el campo GRUPOCLIENTE_KEY tenga registros mayores a 99 en la tabla BIDMMKT.FCT_LLAVEROS_ENTREGADOS");

		System.out.println(GlobalVariables.DB_HOST_BI);
		System.out.println("\n" + QueryGCKllaverosEntregados);

		SQLResult ExecuteQuery6 = executeQuery(dbBI, QueryGCKllaverosEntregados);

		boolean StatusQuery6 = ExecuteQuery6.isEmpty();
		System.out.println(StatusQuery6);
		if (!StatusQuery6) {
			testCase.addQueryEvidenceCurrentStep(ExecuteQuery6);
		} else {
			testCase.addTextEvidenceCurrentStep(
					"GrupoCliente_key no tiene registros mayores a 99. Tabla BIDMMKT.FCT_LLAVEROS_ENTREGADOS");
			testCase.addQueryEvidenceCurrentStep(ExecuteQuery6);
		}
		assertFalse(StatusQuery6,
				"GrupoCliente_key no tiene registros mayores a 99. Tabla BIDMMKT.FCT_LLAVEROS_ENTREGADOS");

		/***************************************
		 * PASO 7
		 ***************************************/
		addStep("Validar que se tengan registros en la tabla BIDMMKT.FCT_TAE_NUMCTES");

		System.out.println(GlobalVariables.DB_HOST_BI);
		System.out.println("\n" + QueryTaeNumCli);

		SQLResult ExecuteQuery7 = executeQuery(dbBI, QueryTaeNumCli);

		boolean StatusQuery7 = ExecuteQuery7.isEmpty();
		System.out.println(StatusQuery7);
		if (!StatusQuery7) {
			testCase.addQueryEvidenceCurrentStep(ExecuteQuery7);
		} else {
			testCase.addTextEvidenceCurrentStep("La tabla BIDMMKT.FCT_TAE_NUMCTES no tiene registros.");
			testCase.addQueryEvidenceCurrentStep(ExecuteQuery7);
		}
		assertFalse(StatusQuery7, "La tabla BIDMMKT.FCT_TAE_NUMCTES no tiene registros.");

		/***************************************
		 * PASO 8
		 ***************************************/
		addStep("Validar que el campo GRUPOCLIENTE_KEY tenga registros mayores a 99 en la tabla BIDMMKT.FCT_TAE_NUMCTES");

		System.out.println(GlobalVariables.DB_HOST_BI);
		System.out.println("\n" + QueryGCKtaeNumCli);

		SQLResult ExecuteQuery8 = executeQuery(dbBI, QueryGCKtaeNumCli);

		boolean StatusQuery8 = ExecuteQuery8.isEmpty();
		System.out.println(StatusQuery8);
		if (!StatusQuery8) {
			testCase.addQueryEvidenceCurrentStep(ExecuteQuery8);
		} else {
			testCase.addTextEvidenceCurrentStep(
					"GrupoCliente_key no tiene registros mayores a 99. Tabla BIDMMKT.FCT_TAE_NUMCTES");
			testCase.addQueryEvidenceCurrentStep(ExecuteQuery8);
		}
		assertFalse(StatusQuery8, "GrupoCliente_key no tiene registros mayores a 99. Tabla BIDMMKT.FCT_TAE_NUMCTES");

		/***************************************
		 * PASO 9
		 ***************************************/
		addStep("Validar que se tengan registros en la tabla BIDMMKT.FCT_TAE_RENTABILIDAD");

		System.out.println(GlobalVariables.DB_HOST_BI);
		System.out.println("\n" + QueryTaeRenta);

		SQLResult ExecuteQuery9 = executeQuery(dbBI, QueryTaeRenta);

		boolean StatusQuery9 = ExecuteQuery9.isEmpty();
		System.out.println(StatusQuery9);
		if (!StatusQuery9) {
			testCase.addQueryEvidenceCurrentStep(ExecuteQuery9);
		} else {
			testCase.addTextEvidenceCurrentStep("La tabla BIDMMKT.FCT_TAE_RENTABILIDAD no tiene registros.");
			testCase.addQueryEvidenceCurrentStep(ExecuteQuery9);
		}
		assertFalse(StatusQuery9, "La tabla BIDMMKT.FCT_TAE_RENTABILIDAD no tiene registros.");

		/***************************************
		 * PASO 10
		 ***************************************/
		addStep("Validar que el campo GRUPOCLIENTE_KEY tenga registros mayores a 99 en la tabla BIDMMKT.FCT_TAE_RENTABILIDAD");

		System.out.println(GlobalVariables.DB_HOST_BI);
		System.out.println("\n" + QueryGCKtaeRenta);

		SQLResult ExecuteQuery10 = executeQuery(dbBI, QueryGCKtaeRenta);

		boolean StatusQuery10 = ExecuteQuery10.isEmpty();
		System.out.println(StatusQuery10);
		if (!StatusQuery10) {
			testCase.addQueryEvidenceCurrentStep(ExecuteQuery10);
		} else {
			testCase.addTextEvidenceCurrentStep(
					"GrupoCliente_key no tiene registros mayores a 99. Tabla BIDMMKT.FCT_TAE_RENTABILIDAD");
			testCase.addQueryEvidenceCurrentStep(ExecuteQuery10);
		}
		assertFalse(StatusQuery10,
				"GrupoCliente_key no tiene registros mayores a 99. Tabla BIDMMKT.FCT_TAE_RENTABILIDAD");

		/***************************************
		 * PASO 11
		 ***************************************/
		addStep("Validar que se tengan registros en la tabla BIDMMKT.FCT_TAE_VENTAS_SLLAVERO");

		System.out.println(GlobalVariables.DB_HOST_BI);
		System.out.println("\n" + QueryVentaSLlavero);

		SQLResult ExecuteQuery11 = executeQuery(dbBI, QueryVentaSLlavero);

		boolean StatusQuery11 = ExecuteQuery11.isEmpty();
		System.out.println(StatusQuery11);
		if (!StatusQuery11) {
			testCase.addQueryEvidenceCurrentStep(ExecuteQuery11);
		} else {
			testCase.addTextEvidenceCurrentStep("La tabla BIDMMKT.FCT_TAE_VENTAS_SLLAVERO no tiene registros.");
			testCase.addQueryEvidenceCurrentStep(ExecuteQuery11);
		}
		assertFalse(StatusQuery11, "La tabla BIDMMKT.FCT_TAE_VENTAS_SLLAVERO no tiene registros.");

		/***************************************
		 * PASO 12
		 ***************************************/
		addStep("Validar que el campo GRUPOCLIENTE_KEY tenga registros mayores a 99 en la tabla BIDMMKT.FCT_TAE_VENTAS_SLLAVERO");

		System.out.println(GlobalVariables.DB_HOST_BI);
		System.out.println("\n" + QueryGCKventaSllavero);

		SQLResult ExecuteQuery12 = executeQuery(dbBI, QueryGCKventaSllavero);

		boolean StatusQuery12 = ExecuteQuery12.isEmpty();
		System.out.println(StatusQuery12);
		if (!StatusQuery12) {
			testCase.addQueryEvidenceCurrentStep(ExecuteQuery12);
		} else {
			testCase.addTextEvidenceCurrentStep(
					"GrupoCliente_key no tiene registros mayores a 99. Tabla BIDMMKT.FCT_TAE_VENTAS_SLLAVERO");
			testCase.addQueryEvidenceCurrentStep(ExecuteQuery12);
		}
		// la consulta en la tabla no tiene registros con GrupoCliente_key> 99
		assertFalse(StatusQuery12,
				"GrupoCliente_key no tiene registros mayores a 99. Tabla BIDMMKT.FCT_TAE_VENTAS_SLLAVERO");

		/***************************************
		 * PASO 13
		 ***************************************/
		addStep("Validar que se tengan registros en la tabla BIDMMKT.FCT_TAE_VTALLAVERO_GPOCTE");

		System.out.println(GlobalVariables.DB_HOST_BI);
		System.out.println("\n" + QueryVentaLlavero);

		SQLResult ExecuteQuery13 = executeQuery(dbBI, QueryVentaLlavero);

		boolean StatusQuery13 = ExecuteQuery13.isEmpty();
		System.out.println(StatusQuery13);
		if (!StatusQuery13) {
			testCase.addQueryEvidenceCurrentStep(ExecuteQuery13);
		} else {
			testCase.addTextEvidenceCurrentStep("La tabla BIDMMKT.FCT_TAE_VTALLAVERO_GPOCTE no tiene registros.");
			testCase.addQueryEvidenceCurrentStep(ExecuteQuery13);
		}
		assertFalse(StatusQuery13, "La tabla BIDMMKT.FCT_TAE_VTALLAVERO_GPOCTE no tiene registros.");

		/***************************************
		 * PASO 14
		 ***************************************/
		addStep("Validar que el campo GRUPOCLIENTE_KEY tenga registros mayores a 99 en la tabla BIDMMKT.FCT_TAE_VTALLAVERO_GPOCTE");

		System.out.println(GlobalVariables.DB_HOST_BI);
		System.out.println("\n" + QueryGCKventaLlavero);

		SQLResult ExecuteQuery14 = executeQuery(dbBI, QueryGCKventaLlavero);

		boolean StatusQuery14 = ExecuteQuery14.isEmpty();
		System.out.println(StatusQuery14);
		if (!StatusQuery14) {
			testCase.addQueryEvidenceCurrentStep(ExecuteQuery14);
		} else {
			testCase.addTextEvidenceCurrentStep(
					"GrupoCliente_key no tiene registros mayores a 99. Tabla BIDMMKT.FCT_TAE_VTALLAVERO_GPOCTE");
			testCase.addQueryEvidenceCurrentStep(ExecuteQuery14);
		}
		assertFalse(StatusQuery14,
				"GrupoCliente_key no tiene registros mayores a 99. Tabla BIDMMKT.FCT_TAE_VTALLAVERO_GPOCTE");

		/***************************************
		 * PASO 15
		 ***************************************/
		addStep("Validar que se tengan registros en la tabla  BIDMMKT.FCT_TAE_VTAPROM_GPOTDA");

		System.out.println(GlobalVariables.DB_HOST_BI);
		System.out.println("\n" + QueryVentaProm);

		SQLResult ExecuteQuery15 = executeQuery(dbBI, QueryVentaProm);

		boolean StatusQuery15 = ExecuteQuery15.isEmpty();
		System.out.println(StatusQuery15);
		if (!StatusQuery15) {
			testCase.addQueryEvidenceCurrentStep(ExecuteQuery15);
		} else {
			testCase.addTextEvidenceCurrentStep("La tabla  BIDMMKT.FCT_TAE_VTAPROM_GPOTDA no tiene registros.");
			testCase.addQueryEvidenceCurrentStep(ExecuteQuery15);
		}
		assertFalse(StatusQuery15, "La tabla  BIDMMKT.FCT_TAE_VTAPROM_GPOTDA no tiene registros.");

		/***************************************
		 * PASO 16
		 ***************************************/
		addStep("Validar que el campo GRUPOCLIENTE_KEY tenga registros mayores a 99 en la tabla  BIDMMKT.FCT_TAE_VTAPROM_GPOTDA");

		System.out.println(GlobalVariables.DB_HOST_BI);
		System.out.println("\n" + QueryGCKventaProm);

		SQLResult ExecuteQuery16 = executeQuery(dbBI, QueryGCKventaProm);

		boolean StatusQuery16 = ExecuteQuery16.isEmpty();
		System.out.println(StatusQuery16);
		if (!StatusQuery16) {
			testCase.addQueryEvidenceCurrentStep(ExecuteQuery16);
		} else {
			testCase.addTextEvidenceCurrentStep(
					"GrupoCliente_key no tiene registros mayores a 99. Tabla  BIDMMKT.FCT_TAE_VTAPROM_GPOTDA");
			testCase.addQueryEvidenceCurrentStep(ExecuteQuery16);
		}
		assertFalse(StatusQuery16,
				"GrupoCliente_key no tiene registros mayores a 99. Tabla  BIDMMKT.FCT_TAE_VTAPROM_GPOTDA");

		/***************************************
		 * PASO 17
		 ***************************************/
		addStep("Validar que se tengan registros en la tabla  BIDMMKT.TMP_FCT_TAE_RENTABILIDAD");

		System.out.println(GlobalVariables.DB_HOST_BI);
		System.out.println("\n" + QueryTmpTaeRenta);

		SQLResult ExecuteQuery17 = executeQuery(dbBI, QueryTmpTaeRenta);

		boolean StatusQuery17 = ExecuteQuery17.isEmpty();
		System.out.println(StatusQuery17);
		if (!StatusQuery17) {
			testCase.addQueryEvidenceCurrentStep(ExecuteQuery17);
		} else {
			testCase.addTextEvidenceCurrentStep("La tabla  BIDMMKT.TMP_FCT_TAE_RENTABILIDAD no tiene registros.");
			testCase.addQueryEvidenceCurrentStep(ExecuteQuery17);
		}
		assertFalse(StatusQuery17, "La tabla  BIDMMKT.TMP_FCT_TAE_RENTABILIDAD no tiene registros.");

		/***************************************
		 * PASO 18
		 ***************************************/
		addStep("Validar que el campo GRUPOCLIENTE_KEY tenga registros mayores a 99 en la tabla  BIDMMKT.TMP_FCT_TAE_RENTABILIDAD");

		System.out.println(GlobalVariables.DB_HOST_BI);
		System.out.println("\n" + QueryGCKtmpTaeRenta);

		SQLResult ExecuteQuery18 = executeQuery(dbBI, QueryGCKtmpTaeRenta);

		boolean StatusQuery18 = ExecuteQuery18.isEmpty();
		System.out.println(StatusQuery18);
		if (!StatusQuery18) {
			testCase.addQueryEvidenceCurrentStep(ExecuteQuery18);
		} else {
			testCase.addTextEvidenceCurrentStep(
					"GrupoCliente_key no tiene registros mayores a 99. Tabla  BIDMMKT.TMP_FCT_TAE_RENTABILIDAD");
			testCase.addQueryEvidenceCurrentStep(ExecuteQuery18);
		}
		assertFalse(StatusQuery18,
				"GrupoCliente_key no tiene registros mayores a 99. Tabla  BIDMMKT.TMP_FCT_TAE_RENTABILIDAD");

	}

	@Override
	public String setTestFullName() {
		// TODO Auto-generated method stub
		return "ATC_FT_005_VerificarCorrectaInsercionDeRegistrosEnTablas_test";
	}

	@Override
	public String setTestDescription() {
		// TODO Auto-generated method stub
		return "CP005 - Verificar la correcta inserci�n de los registros en las tablas";
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
