package IMA.Extraccion_ConexionDirecta.LongitudDeCampoLlaveroTAE_51972916;

import static org.testng.Assert.assertFalse;

import java.util.HashMap;

import org.testng.annotations.Test;

import IMA.util.GlobalVariables;
import IMA.util.IMATools;
import modelo.BaseExecution;
import utils.selenium.ChromeTest;
import utils.selenium.SeleniumUtil;
import utils.sql.SQLResult;
import utils.sql.SQLUtil;

public class ATC_FT_010_ValidarSeMuestrenCorrectamenteLosDatosEnLosReportesMIC extends BaseExecution {
	@Test(dataProvider = "data-provider")
	public void ATC_FT_010_ValidarSeMuestrenCorrectamenteLosDatosEnLosReportesMIC_test(HashMap<String, String> data)
			throws Exception {
		/***************************************
		 * BASE DATOS
		 ***************************************/
		SQLUtil dbBI = new SQLUtil(GlobalVariables.DB_HOST_BI, GlobalVariables.DB_USER_BI,
				GlobalVariables.DB_PASSWORD_BI);

		/***************************************
		 * VARIABLES
		 ***************************************/
		String QueryGrupoClienteKeyMes = "select * from (select	a12.PLAZA_ID  PLAZA_ID,"
				+ " a12.NEGOCIO_ID  NEGOCIO_ID, a11.MES_ID  MES_ID, a11.GRUPOCLIENTE_KEY  GRUPOCLIENTE_KEY,"
				+ " sum(a11.RECARGAS_AMT_LLAVERO)  WJXBFS1, sum(a11.RECARGAS_AMT)  WJXBFS2,"
				+ " sum(a11.RECARGAS_NUM_LLAVERO)  WJXBFS3, sum(a11.RECARGAS_NUM)  WJXBFS4"
				+ " from	\"BIDMMKT\".\"FCT_TAE_VTALLAVERO_GPOCTE\"	a11" + " join	\"DWADMIN\".\"DIM_TIENDA\"	a12"
				+ "  on 	(a11.TIENDA_KEY = a12.TIENDA_KEY)" + "where	(a11.MES_ID in (201712)"
				+ " and a11.GRUPOCLIENTE_KEY in (108))" + " group by	a12.PLAZA_ID,"
				+ " a12.NEGOCIO_ID, a11.MES_ID, a11.GRUPOCLIENTE_KEY) WHERE ROWNUM <= 5";

		String QueryGrupoClientesKeyPlaza = "select * from (select	a13.PLAZA_ID  PLAZA_ID,"
				+ " a13.NEGOCIO_ID  NEGOCIO_ID, a12.MES_ID  MES_ID, a11.GRUPOCLIENTE_KEY  GRUPOCLIENTE_KEY,"
				+ " sum(a11.NUMERO_CLIENTES)  WJXBFS1" + " from	\"BIDMMKT\".\"FCT_TAE_NUMCTES\"	a11"
				+ " cross join \"BIDMMKT\".\"DIM_MES_VW\"	a12" + " join	\"DWADMIN\".\"DIM_TIENDA\" a13"
				+ "  on 	(a11.TIENDA_KEY = a13.TIENDA_KEY)" + " where	(a12.MES_ID in (201712)"
				+ " and a11.GRUPOCLIENTE_KEY in (108))" + "group by	a13.PLAZA_ID, a13.NEGOCIO_ID, a12.MES_ID,"
				+ " a11.GRUPOCLIENTE_KEY) where rownum <=5";

		String QueryGrupoClientesKeySet = "select * from (select a13.PLAZA_ID  PLAZA_ID,"
				+ " a13.NEGOCIO_ID  NEGOCIO_ID, a15.PLAZA_DES  PLAZA_DES,"
				+ " a15.PLAZA_CVE  PLAZA_CVE, a12.MES_ID  MES_ID,"
				+ " a12.ANIO_ID  ANIO_ID, a11.GRUPOCLIENTE_KEY  GRUPOCLIENTE_KEY,"
				+ " a14.GRUPOCLIENTE_DES  GRUPOCLIENTE_DES," + " sum(a11.NUMERO_CLIENTES)  WJXBFS1"
				+ " from \"BIDMMKT\".\"FCT_TAE_NUMCTES\"	a11" + " cross join \"BIDMMKT\".\"DIM_MES_VW\"	a12"
				+ " join	\"DWADMIN\".\"DIM_TIENDA\"	a13" + " on (a11.TIENDA_KEY = a13.TIENDA_KEY)"
				+ " join	\"BIDMMKT\".\"DIM_GRUPOCLIENTE_TAE\"	a14"
				+ " on (a11.GRUPOCLIENTE_KEY = a14.GRUPOCLIENTE_KEY)" + " join \"DWADMIN\".\"VM_DIM_PLAZA\"	a15"
				+ " on (a13.NEGOCIO_ID = a15.NEGOCIO_ID and " + " a13.PLAZA_ID = a15.PLAZA_ID)"
				+ "where (a12.MES_ID in (201712)" + " and a11.GRUPOCLIENTE_KEY in (108))"
				+ "group by a13.PLAZA_ID, a13.NEGOCIO_ID, a15.PLAZA_DES,"
				+ " a15.PLAZA_CVE, a15.NEGOCIO_DES, a12.MES_ID," + " a12.ANIO_ID,"
				+ " a11.GRUPOCLIENTE_KEY, a14.GRUPOCLIENTE_DES) WHERE ROWNUM <=5";

		String QueryGrupoClientesKeyTAE = "select * from (select	a12.PLAZA_ID  PLAZA_ID,"
				+ " a12.NEGOCIO_ID  NEGOCIO_ID, a11.GRUPOCLIENTE_KEY  GRUPOCLIENTE_KEY,"
				+ " sum(a11.CANTIDAD_LLAVEROS)  WJXBFS1, sum(a11.INVERSION)  WJXBFS2,"
				+ " ((sum(ABS(a11.VENTA_ADICIONAL)) + sum(a11.VENTA_ADICIONAL)) / 2.0)  WJXBFS3"
				+ " from	\"BIDMMKT\".\"FCT_TAE_RENTABILIDAD\" a11" + " join	\"DWADMIN\".\"DIM_TIENDA\"	a12"
				+ "  on (a11.TIENDA_KEY = a12.TIENDA_KEY)"
				+ " where	(a11.GRUPOCLIENTE_KEY in (108) and ((a11.MES_ID)"
				+ " in (select c21.MES_ID from	\"BIDMMKT\".\"DIM_MES_VW\"	c21"
				+ " cross join \"BIDMMKT\".\"V_TRANSF_12MESANT\"	c22"
				+ " where	c21.MES_ID BETWEEN TO_NUMBER(TO_CHAR(ADD_MONTHS(TO_DATE(MESACT||'01','YYYYMMDD'),-11),'YYYYMM'))"
				+ " AND MESACT AND c22.MESACT in (201712))) and not ((a12.PLAZA_ID = 0 and a12.NEGOCIO_ID = 126)"
				+ " or  (a12.PLAZA_ID=0 and a12.NEGOCIO_ID=126))) group by	a12.PLAZA_ID,"
				+ " a12.NEGOCIO_ID, a11.GRUPOCLIENTE_KEY) WHERE ROWNUM <=5";

		String QueryGrupoClientesKeyTAEtienda = "select *from (select	a11.TIENDA_KEY  TIENDA_KEY,"
				+ " a11.GRUPOCLIENTE_KEY  GRUPOCLIENTE_KEY, sum(a11.CANTIDAD_LLAVEROS)  WJXBFS1,"
				+ " sum(a11.INVERSION)  WJXBFS2, ((sum(ABS(a11.VENTA_ADICIONAL)) + sum(a11.VENTA_ADICIONAL)) / 2.0)  WJXBFS3"
				+ " from	\"BIDMMKT\".\"FCT_TAE_RENTABILIDAD\" a11 join \"DWADMIN\".\"DIM_TIENDA\" a12"
				+ " on (a11.TIENDA_KEY = a12.TIENDA_KEY) where	(a11.GRUPOCLIENTE_KEY in (108)"
				+ " and a12.NEGOCIO_ID in (126) and ((a11.MES_ID) in	(select	c21.MES_ID from	\"BIDMMKT\".\"DIM_MES_VW\"	c21"
				+ " cross join \"BIDMMKT\".\"V_TRANSF_12MESANT\"	c22"
				+ " where	c21.MES_ID BETWEEN TO_NUMBER(TO_CHAR(ADD_MONTHS(TO_DATE(MESACT||'01','YYYYMMDD'),-11),'YYYYMM')) AND MESACT AND c22.MESACT in (201712)))"
				+ " and not ((a12.PLAZA_ID = 0 and a12.NEGOCIO_ID = 126)"
				+ " or  (a12.PLAZA_ID=0 and a12.NEGOCIO_ID=126))) group by	a11.TIENDA_KEY,"
				+ " a11.GRUPOCLIENTE_KEY) WHERE ROWNUM <=5";

		SeleniumUtil u = new SeleniumUtil(new ChromeTest(), true);
		IMATools ima = new IMATools(u, testCase);

		/***************************************
		 * PASO 1
		 ***************************************/
		addStep("Abrir navegador y loguearse en la p�gina de MicroStrategy");
		ima.Login();

		/***************************************
		 * PASO 2
		 ***************************************/
		addStep("Seleccionar la carpeta de Informaci�n comercial");
		ima.Select_FolderProtecPatrimonial();

		addStep("Seleccion de reporte Comportamiento de Clientes en Mes");
		ima.clickOnReporte("Comportamiento de Clientes en Mes");

		/***************************************
		 * PASO 3
		 ***************************************/
		addStep("Aplicar filtros(prompts)");
		ima.generarReporteLlavero();

		/***************************************
		 * PASO 4
		 ***************************************/
		addStep("Validar que el campo GRUPOCLIENTE_KEY tenga registros mayores a 99 Clientes en Mes");

		System.out.println(GlobalVariables.DB_HOST_BI);
		System.out.println("\n" + QueryGrupoClienteKeyMes);
		u.hardWait(6);

		SQLResult ExecuteQuery = executeQuery(dbBI, QueryGrupoClienteKeyMes);

		boolean StatusQuery = ExecuteQuery.isEmpty();
		System.out.println(StatusQuery);
		if (!StatusQuery) {
			testCase.addQueryEvidenceCurrentStep(ExecuteQuery);
		} else {
			testCase.addTextEvidenceCurrentStep(
					"GrupoCliente_key no tiene registros mayores a 99 Clientes en Mes. Tabla BIDMMKT.FCT_TAE_VTALLAVERO_GPOCTE");
			testCase.addQueryEvidenceCurrentStep(ExecuteQuery);
		}
		assertFalse(StatusQuery,
				"GrupoCliente_key no tiene registros mayores a 99 Clientes en Mes. Tabla BIDMMKT.FCT_TAE_VTALLAVERO_GPOCTE");

		/***************************************
		 * PASO 5
		 ***************************************/
		addStep("Seleccionar la carpeta de Informaci�n comercial");
		ima.Select_FolderProtecPatrimonial();

		addStep("Seleccion de reporte Comportamiento de plaza en el tiempo");
		ima.clickOnReporte("Comportamiento de Plaza en el Tiempo");

		/***************************************
		 * PASO 6
		 ***************************************/
		addStep("Aplicar filtros(prompts)");
		ima.generarReporteLlavero();

		/***************************************
		 * PASO 7
		 ***************************************/
		addStep("Validar que el campo GRUPOCLIENTE_KEY tenga registros mayores a 99 Plaza en el Tiempo");

		System.out.println(GlobalVariables.DB_HOST_BI);
		System.out.println("\n" + QueryGrupoClientesKeyPlaza);
		u.hardWait(6);

		SQLResult ExecuteQuery2 = executeQuery(dbBI, QueryGrupoClientesKeyPlaza);

		boolean StatusQuery2 = ExecuteQuery2.isEmpty();
		System.out.println(StatusQuery2);
		if (!StatusQuery2) {
			testCase.addQueryEvidenceCurrentStep(ExecuteQuery2);
		} else {
			testCase.addTextEvidenceCurrentStep(
					"GrupoCliente_key no tiene registros mayores a 99 Plaza en el Tiempo. Tabla BIDMMKT.FCT_TAE_NUMCTES");
			testCase.addQueryEvidenceCurrentStep(ExecuteQuery2);
		}
		assertFalse(StatusQuery2,
				"GrupoCliente_key no tiene registros mayores a 99 Plaza en el Tiempo. Tabla BIDMMKT.FCT_TAE_NUMCTES");

		/***************************************
		 * PASO 8
		 ***************************************/
		addStep("Seleccionar la carpeta de Informaci�n comercial");
		ima.Select_FolderProtecPatrimonial();

		addStep("Seleccion de reporte Numero de Clientes por SET");
		ima.clickOnReporte("Numero de Clientes por SET");

		/***************************************
		 * PASO 9
		 ***************************************/
		addStep("Aplicar filtros(prompts)");
		ima.generarReporteLlavero();

		/***************************************
		 * PASO 10
		 ***************************************/
		addStep("Validar que el campo GRUPOCLIENTE_KEY tenga registros mayores a 99 Clientes por SET");

		System.out.println(GlobalVariables.DB_HOST_BI);
		System.out.println("\n" + QueryGrupoClientesKeySet);
		u.hardWait(6);

		SQLResult ExecuteQuery3 = executeQuery(dbBI, QueryGrupoClientesKeySet);

		boolean StatusQuery3 = ExecuteQuery3.isEmpty();
		System.out.println(StatusQuery3);
		if (!StatusQuery3) {
			testCase.addQueryEvidenceCurrentStep(ExecuteQuery3);
		} else {
			testCase.addTextEvidenceCurrentStep(
					"GrupoCliente_key no tiene registros mayores a 99 Plaza en el Tiempo. Tabla BIDMMKT.FCT_TAE_NUMCTES");
			testCase.addQueryEvidenceCurrentStep(ExecuteQuery3);
		}
		assertFalse(StatusQuery3,
				"GrupoCliente_key no tiene registros mayores a 99 Plaza en el Tiempo. Tabla BIDMMKT.FCT_TAE_NUMCTES");

		/***************************************
		 * PASO 11
		 ***************************************/
		addStep("Seleccionar la carpeta de Informaci�n comercial");
		ima.Select_FolderProtecPatrimonial();

		addStep("Seleccion de reporte Rentabilidad TAE");
		ima.clickOnReporte("Rentabilidad TAE");

		/***************************************
		 * PASO 12
		 ***************************************/
		addStep("Aplicar filtros(prompts)");
		ima.generarRentabilidadTAE();
		u.hardWait(10);

		/***************************************
		 * PASO 13
		 ***************************************/
		addStep("Validar que el campo GRUPOCLIENTE_KEY tenga registros mayores a 99 Rentabilidad TAE");

		System.out.println(GlobalVariables.DB_HOST_BI);
		System.out.println("\n" + QueryGrupoClientesKeyTAE);
		u.hardWait(6);

		SQLResult ExecuteQuery4 = executeQuery(dbBI, QueryGrupoClientesKeyTAE);

		boolean StatusQuery4 = ExecuteQuery4.isEmpty();
		System.out.println(StatusQuery4);
		if (!StatusQuery4) {
			testCase.addQueryEvidenceCurrentStep(ExecuteQuery4);
		} else {
			testCase.addTextEvidenceCurrentStep(
					"GrupoCliente_key no tiene registros mayores a 99 Rentabilidad TAE. Tabla BIDMMKT.FCT_TAE_RENTABILIDAD");
			testCase.addQueryEvidenceCurrentStep(ExecuteQuery4);
		}
		assertFalse(StatusQuery4,
				"GrupoCliente_key no tiene registros mayores a 99 Rentabilidad TAE. Tabla BIDMMKT.FCT_TAE_RENTABILIDAD");

		/***************************************
		 * PASO 14
		 ***************************************/
		addStep("Seleccionar la carpeta de Informaci�n comercial- Tienda");
		ima.Select_FolderTAEtienda();

		addStep("Seleccion de reporte Rentabilidad TAE (Tienda)");
		ima.clickOnReporte("Rentabilidad TAE (Tienda)");

		/***************************************
		 * PASO 15
		 ***************************************/
		addStep("Aplicar filtros(prompts)");
		ima.generarRentabilidadTAEtienda();
		u.hardWait(10);

		/***************************************
		 * PASO 16
		 ***************************************/
		addStep("Validar que el campo GRUPOCLIENTE_KEY tenga registros mayores a 99 Rentabilidad TAE (Tienda)");

		System.out.println(GlobalVariables.DB_HOST_BI);
		System.out.println("\n" + QueryGrupoClientesKeyTAEtienda);
		u.hardWait(6);

		SQLResult ExecuteQuery5 = executeQuery(dbBI, QueryGrupoClientesKeyTAEtienda);

		boolean StatusQuery5 = ExecuteQuery5.isEmpty();
		System.out.println(StatusQuery5);
		if (!StatusQuery5) {
			testCase.addQueryEvidenceCurrentStep(ExecuteQuery5);
		} else {
			testCase.addTextEvidenceCurrentStep(
					"GrupoCliente_key no tiene registros mayores a 99 Rentabilidad TAE Tienda. Tabla BIDMMKT.FCT_TAE_RENTABILIDAD");
			testCase.addQueryEvidenceCurrentStep(ExecuteQuery5);
		}
		assertFalse(StatusQuery5,
				"GrupoCliente_key no tiene registros mayores a 99 Rentabilidad TAE Tienda. Tabla BIDMMKT.FCT_TAE_RENTABILIDAD");

	}

	@Override
	public String setTestFullName() {
		// TODO Auto-generated method stub
		return "ATC_FT_010_ValidarSeMuestrenCorrectamenteLosDatosEnLosReportesMIC_test";
	}

	@Override
	public String setTestDescription() {
		// TODO Auto-generated method stub
		return "CP010 - Validar se muestren correctamente los datos en los reportes de MIC";
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