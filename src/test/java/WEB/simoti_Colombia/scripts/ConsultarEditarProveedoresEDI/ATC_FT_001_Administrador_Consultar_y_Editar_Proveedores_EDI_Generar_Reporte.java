package WEB.simoti_Colombia.scripts.ConsultarEditarProveedoresEDI;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import java.sql.SQLException;
import java.util.HashMap;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import WEB.om.PortalApps;
import WEB.simoti_Colombia.om.ConsultarEditarProveedoresEDI_SimotiColombia;
import WEB.simoti_Colombia.om.MenuPrincipal_SimotiColombia;
import WEB.util.GlobalVariables;
import modelo.BaseExecution;
import utils.password.PasswordUtil;
import utils.selenium.MEdgeTest;
import utils.selenium.SeleniumUtil;
import utils.sql.SQLResult;
import utils.sql.SQLUtil;
/**Desc: Validar que se genere el reporte y que se muestren los datos correctamente de acuerdo a los filtros seleccionados.
 * MTC-FT-001 Administrador_Consultar y Editar Proveedores EDI_Generar Reporte
 * Nota. Falta probar el funcionamiento del script con datos
 * @author Andrea Contreras
 * @date 7 Marzo 2023
 */
public class ATC_FT_001_Administrador_Consultar_y_Editar_Proveedores_EDI_Generar_Reporte extends BaseExecution{
	SeleniumUtil u;
	
	@Test(dataProvider = "data-provider")
	public void ATC_FT_001_Administrador_Consultar_y_Editar_Proveedores_EDI_Generar_Reporte_test(HashMap<String, String> data) throws SQLException {
		
		/*
		 * Variables
		 ******************************************************************************************/
		SQLUtil dbRmsCol = new SQLUtil(GlobalVariables.DB_HOST_BDRMSCQA_QAVIEW, GlobalVariables.DB_USER_BDRMSCQA_QAVIEW, GlobalVariables.DB_PASSWORD_BDRMSCQA_QAVIEW);		
		String user = data.get("User");
		String password = PasswordUtil.decryptPassword(data.get("Password"));
		String URL = data.get("Url");
		String plaza = data.get("Plaza");
		u = new SeleniumUtil(new MEdgeTest(), true);
		PortalApps pa = new PortalApps(u, testCase);
		MenuPrincipal_SimotiColombia mp = new MenuPrincipal_SimotiColombia(u, testCase);
		ConsultarEditarProveedoresEDI_SimotiColombia cep = new ConsultarEditarProveedoresEDI_SimotiColombia(u, testCase);
		
		// Paso 1
		addStep("El usuario ingresa a la aplicaci�n Access Control V3");
		
		u.get(URL);
		u.getDriver().manage().window().maximize();	
		u.hardWait(3);
		testCase.addScreenShotCurrentStep(u, "portalAplicaciones");
		pa.proceedToPortal();			
		testCase.addScreenShotCurrentStep(u, "imagenLogin");
		
		// Paso 2
		addStep("El usuario ingresa usuario y contrase�a y da clic en el bot�n �Iniciar Sesi�n�.");
		pa.cicloLogin(user, password);
		
		// Paso 3
		addStep("El usuario da clic en opci�n de SIMOTI.");
		pa.clickSimotiColombia();
		
		// Paso 4
		addStep("El usuario despliega el men�: Transacciones Electr�nicas " + 
				"y selecciona el reporte: Consultar y Editar Proveedores EDI");
		mp.clickTransaccionesElectronicasTDC();;
		u.hardWait(3);
		mp.clickConsultarEditarProveedoresEDI();
		
		// Paso 5
		addStep("El usuario selecciona o captura valor en los filtros que se indique:\r\n" + 
				"*Plaza: <Seleccionar una plaza>\r\n" + 
				"*Cedis: <Deshabilitado> \r\n" + 
				"*Proveedor: <Deshabilitado> \r\n" + 
				"Pulsar el bot�n Consultar\r\n" + 
				"El sistema deshabilita los campos Cedis, Proveedor y muestra la consulta");
		
		cep.capturaFiltros(plaza, "", "");
		
		boolean filtrosDeshabilitados = cep.camposDeshabilitadosCedisProveedor();
		System.out.print(filtrosDeshabilitados);
		
		assertTrue(filtrosDeshabilitados, "El sistema no deshabilita los campos Cedis y Proveedor");
		
		cep.clickConsultarBtn();
		
		// Paso 6
		addStep("El usuario valida que el sistema muestre los resultados de la consulta en las siguientes columnas, validar que se muestre el tipo de dato adecuado:\r\n" + 
				"1. No. (Tipo de dato: Number)\r\n" + 
				"2. Proveedor: (Tipo de dato: Number)\r\n" + 
				"3. Nombre (Tipo de dato: String)\r\n" + 
				"4. Tipo archivo (Tipo de dato: String)\r\n" + 
				"5. est�do (Tipo de dato: String)\r\n" + 
				"6. Editar (link)\r\n" + 
				"7. Ver (link)\r\n" + 
				"8. Editar plaza y buzones (link)\r\n" + 
				"9. Eliminar (link)");
		
		boolean columnas = cep.validarEncabezadosTablaResultados();
		assertTrue(columnas, "No se muestran las columnas");
		
		boolean resultados = cep.validarExistenResultados();		
		assertTrue(resultados, "No se muestran resultados de la consulta");
		
		
		// Paso 7
		addStep("El usuario valida que se muestran los datos acorde a los resultados del query.\r\n" + 
				"SELECT EF.ID, EF.DESCRIPTION || ' (' || EF.FUNCTION || ')', NVL( OPC, '' )\r\n" + 
				"FROM WM_EDI_FUNCTIONS EF\r\n" + 
				"LEFT OUTER JOIN (\r\n" + 
				"SELECT FUNCTION_ID AS FUN_ID, 'SELECTED' AS OPC FROM WM_EDI_SUPPLIER_FUNCTION WHERE SUP_ID = ? ) SUP\r\n" + 
				"ON EF.ID = SUP.FUN_ID; ");
		
		String query = "SELECT DISTINCT CAST( ES.SUPPLIER AS CHAR(10)),\r\n" + 
				"  CAST( ES.FILETYPE AS CHAR(10)) ,\r\n" + 
				"  CAST( ES.STATUS AS CHAR(1) ),\r\n" + 
				"  CAST(DECODE( ES.COST, 0, 'No', 1, 'Si', ES.COST )AS CHAR (3)),\r\n" + 
				"  ID,\r\n" + 
				"  CAST (DECODE( ES.SURTIRPORPIEZA, 0, 'Cajas', 1, 'Piezas', ES.SURTIRPORPIEZA ) AS CHAR(6)),\r\n" + 
				"  CAST(DECODE( ES.FORMAT_TYPE, 1, 'Orden por caja', 2, 'Multitienda', ES.FORMAT_TYPE )AS CHAR(15)),\r\n" + 
				"  ES.MAX_ATTEMPT,\r\n" + 
				"  CAST( S.SUP_NAME AS CHAR(32))\r\n" + 
				"FROM WMUSER.WM_EDI_MAP_SUPPLIER ES,\r\n" + 
				"  SUPS S\r\n" + 
				"WHERE ES.SUPPLIER = S.SUPPLIER\r\n" + 
				"ORDER BY 1";
		
		String query1 = "SELECT DISTINCT CAST( ES.SUPPLIER AS CHAR(10)),\r\n" + 
				" CAST( ES.FILETYPE AS CHAR(10)) ,\r\n" + 
				" CAST( ES.STATUS AS CHAR(1) ),\r\n" + 
				" CAST(DECODE( ES.COST, 0, 'No', 1, 'Si', ES.COST )AS CHAR (3)),\r\n" + 
				" ID\r\n" +  
				"FROM WMUSER.WM_EDI_MAP_SUPPLIER ES,\r\n" + 
				" SUPS S\r\n" + 
				"WHERE ES.SUPPLIER = S.SUPPLIER\r\n" + 
				"ORDER BY 1";
		
		String query2 = "SELECT CAST (DECODE( ES.SURTIRPORPIEZA, 0, 'Cajas', 1, 'Piezas', ES.SURTIRPORPIEZA ) AS CHAR(6)),\r\n" + 
				" CAST(DECODE( ES.FORMAT_TYPE, 1, 'Orden por caja', 2, 'Multitienda', ES.FORMAT_TYPE )AS CHAR(15)),\r\n" + 
				" ES.MAX_ATTEMPT,\r\n" + 
				" CAST( S.SUP_NAME AS CHAR(32))\r\n" + 
				"FROM WMUSER.WM_EDI_MAP_SUPPLIER ES,\r\n" + 
				" SUPS S\r\n" + 
				"WHERE ES.SUPPLIER = S.SUPPLIER\r\n" + 
				"ORDER BY 1";


		SQLResult Resultado1 = executeQuery(dbRmsCol, query1);
		SQLResult Resultado2 = executeQuery(dbRmsCol, query2);
		System.out.print("consulta" + query);

		boolean validacionQuery = Resultado1.isEmpty();

		if (!validacionQuery) {
			testCase.addTextEvidenceCurrentStep("Existen registros");
		} else {
			testCase.addTextEvidenceCurrentStep("No existen registros");
		}
		
		testCase.addTextEvidenceCurrentStep(query);
		testCase.addQueryEvidenceCurrentStep(Resultado1, false);
		testCase.addQueryEvidenceCurrentStep(Resultado2, false);
		
		assertFalse(validacionQuery, "No existen registros en la base de datos");		

						
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

	@Override
	public String setTestDescription() {
		// TODO Auto-generated method stub
		return "";
	}

	@Override
	public String setTestDesigner() {
		// TODO Auto-generated method stub
		return "AutomationQA";
	}

	@Override
	public String setTestFullName() {
		// TODO Auto-generated method stub
		return "ATC_FT_001_Administrador_Consultar_y_Editar_Proveedores_EDI_Generar_Reporte_test";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}
	@AfterMethod
	public void afterMethod() {
		u.getDriver().quit();
	}

}