package WEB.portal_Remesas.scripts;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import java.util.HashMap;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import WEB.om.PortalApps;
import WEB.portal_Remesas.om.GenerarReporteMontos;
import WEB.portal_Remesas.om.OperacionProveedor_RD;
import WEB.util.GlobalVariables;
import config.Constants;
import modelo.BaseExecution;
import utils.selenium.MEdgeTest;
import utils.selenium.SeleniumUtil;
import utils.sql.SQLResult;

public class ATC_FT_001_Cambio_nombre_JDBC extends BaseExecution {

	SeleniumUtil u;

	@Test(dataProvider = "data-provider")

	public void  ATC_FT_001_Cambio_nombre_JDBC_test(HashMap<String, String> data) throws Exception {			
		/*
		 * Variables
		 ******************************************************************************************/
		String user = GlobalVariables.USER_PORTAL_APPS_6005105;
		String password = GlobalVariables.PASSWORD_PORTAL_APPS_6005105;
		String url = GlobalVariables.URL_PORTAL_APPS;
		String proveedor1 = data.get("Proveedor1");
		String opcion1 = data.get("Opcion1");
		String filepath = data.get("File_Path");
		String proveedor2 = data.get("Proveedor2");
		String opcion2 = data.get("Opcion2");
		String plaza = data.get("Plaza");
		String tienda = data.get("Tienda");
		String estatus = data.get("Estatus_Tienda");
		int registrosInsertar = Integer.parseInt(data.get("Regitros_Insertar"));
		
		String queryTokenAccess = "SELECT PLAZA, TIENDA, ENTIDAD, APLICACION, AGENT_ID, SECUENCE FROM TPEUSER.TPE_TOKEN_ACCESS tpe "
				+ " WHERE tpe.ESTATUS IN (1)"
				+ " AND TRUNC(FECHA_MOD) = TRUNC(SYSDATE)"
				+ " ORDER BY FECHA_MOD desc";
		
		String queryTokenAccess2 = "SELECT TOKEN, PARTY_NUMBER, FECHA_MOD, ESTATUS FROM TPEUSER.TPE_TOKEN_ACCESS tpe "
				+ " WHERE tpe.ESTATUS IN (1)"
				+ " AND TRUNC(FECHA_MOD) = TRUNC(SYSDATE)"
				+ " ORDER BY FECHA_MOD desc";
		
		/*
		 * Utiler�as
		 ******************************************************************************************/
		utils.sql.SQLUtil dbFCTPEQA = new utils.sql.SQLUtil(GlobalVariables.DB_HOST_FCTPEQA_QAVIEW, GlobalVariables.DB_USER_FCTPEQA_QAVIEW,GlobalVariables.DB_PASSWORD_FCTPEQA_QAVIEW);
		u = new SeleniumUtil(new MEdgeTest(), true);
		PortalApps pa = new PortalApps(u, testCase); 
		GenerarReporteMontos rm = new GenerarReporteMontos(u, testCase);
		OperacionProveedor_RD op = new OperacionProveedor_RD(u, testCase);		
		
		/*
		 *                                             PASO 1
		 ******************************************************************************************/
		addStep("Entrar a la siguiente liga: "+url); 
		
		u.getDriver().manage().window().maximize();
	    u.get(url);
		u.hardWait(3); 
		pa.proceedToPortal();
		u.hardWait(2); 
		testCase.addScreenShotCurrentStep(u, "ingresarLiga");
		/*
		 *                                             PASO 2
		 ******************************************************************************************/
		addStep("Loguearse con las credenciales");
		pa.cicloLogin(user, password);

		
		/*
		 *                                             PASO 3
		 ******************************************************************************************/
		addStep("Clic en 'Portal Remesas'");
		
		pa.clickPortalRemesas();
		
		/*
		 *                                             PASO 4
		 ******************************************************************************************/
		addStep("Clic en el menu Retiro de Dinero/ Operacion con proveedor");
		
		rm.clickRetiroDinero();
		op.clickOperacionProveedorOp();
		
		/*
		 *                                             PASO 5
		 ******************************************************************************************/
		addStep("Seleccionamos '"+proveedor1+"'");
			
		op.seleccionarProveedor(proveedor1);
		
		/*
		 *                                             PASO 6
		 ******************************************************************************************/	     			
		addStep("Seleccionamos la opcion '"+opcion1+"'");
		
		op.seleccionarOpcion(opcion1);		
		
		/*
		 *                                             PASO 7 y PASO 8
		 ******************************************************************************************/	     			
		addStep("Clic en el boton Examinar");
		testCase.addScreenShotCurrentStep(u, "examinar");
		
		addStep("Seleccionamos el archivo que deseamos cargar");
		
		filepath = Constants.BASE_PATH + "\\" + filepath;
		System.out.println(filepath);
		op.cargarArchivo(filepath);
		
		/*
		 *                                             PASO 9
		 ******************************************************************************************/	     			
		addStep("Clic en el boton 'Load Archivo Tiendas'");
		
		op.clickLoadArchivoTiendasBtn();
		
		boolean validacion = op.validarCargaAgentIDMensaje("La carga de Cargar AgentID fue exitosa.");
		
		assertTrue(validacion,"No se realizo la carga del archivo de tiendas CargaAgentID");
		//span[@class="ui-messages-info-detail"][text()='']
		
		/*
		 *                                             PASO 10
		 ******************************************************************************************/	     			
		addStep("Clic en el menu Retiro de Dinero/ Operacion con proveedor");
		
		rm.clickRetiroDinero();
		op.clickOperacionProveedorOp();
		
		/*
		 *                                             PASO 11
		 ******************************************************************************************/	     			
		addStep("Seleccionamos '"+proveedor2+"'");
		
		op.seleccionarProveedor(proveedor2);
		
		
		/*
		 *                                             PASO 12
		 ******************************************************************************************/	     			
		addStep("Seleccionamos la opcion '"+opcion2+"'");
		
		op.seleccionarOpcion(opcion2);	
		
		/*
		 *                                             PASO 13
		 ******************************************************************************************/	     			
		addStep("Seleccionamos:  -Plaza: "+plaza+ "  -Tiendas: "+tienda+"  -Estatus de la Tienda: "+estatus);
		
		op.selectPlaza(plaza);
		op.selectTienda(tienda);
		op.selectEstatusTienda(estatus);

		/*
		 *                                             PASO 14
		 ******************************************************************************************/	     			
		addStep("Clic en el boton 'Generar Reporte'");
				
		op.clickGenerarReporteBtn();
		op.visualizarResultados();
		
		/*
		 *                                             PASO 15
		 ******************************************************************************************/	     			
		addStep("Realizar el siguiente query en la base de datos FCTPEQA esquema TPEUSER:    SELECT * FROM TPEUSER.TPE_TOKEN_ACCESS tpe  WHERE tpe.ESTATUS IN (1) ORDER BY Fecha_Mod desc;");
			
		SQLResult queryResult1 = executeQuery(dbFCTPEQA, queryTokenAccess);	
		SQLResult queryResult2 = executeQuery(dbFCTPEQA, queryTokenAccess2);
		
		testCase.addQueryEvidenceCurrentStep(queryResult1);
		testCase.addQueryEvidenceCurrentStep(queryResult2);
			
		System.out.print("\nConsulta esta vacia: "+queryResult1.isEmpty());			
		assertFalse(queryResult1.isEmpty(), "No se encontraron resutados");
	
		int registros = queryResult1.getRowCount();
		boolean validarCantidad = false;
		
		if(registros==registrosInsertar)
			validarCantidad = true;
		
		assertTrue(validarCantidad, "No se insertaron los registos de manera correcta");
		
	}

	@Override
	public void beforeTest() {
	}

	@Override
	public String setTestDescription() {
		return "A) Validar que al seleccionar al proveedor MoneyGram en la opci�n carga \"AgentID\" el archivo a adjuntar se cargue exitosa mente y las tiendas del archivo cargado se reflejen en el Reporte del apartado \"Tiendas como Agente MoneyGram\" \r\n" + 
				"B) Validar que la informaci�n se inserte en la tabla TPE_TOKEN_ACCESS";
	}

	@Override
	public String setTestDesigner() {
		return "AutomationQA";
	}

	@Override
	public String setTestFullName() {
		return null;
	}

	@Override
	public String setTestInstanceID() {
		return null;
	}

	@Override
	public String setPrerequisites() {
		return null;
	}

	@AfterMethod
	public void afterMethod() {
		u.getDriver().quit();
	}
	
	public static void main(String[] args) {
		System.out.println(GlobalVariables.FILES_WEB);
		String filePath = GlobalVariables.FILES_WEB + "Files\\portal_Remesas\\cambioNombreJDBC.xlsx";
		System.out.println(filePath);
	}
	
}
