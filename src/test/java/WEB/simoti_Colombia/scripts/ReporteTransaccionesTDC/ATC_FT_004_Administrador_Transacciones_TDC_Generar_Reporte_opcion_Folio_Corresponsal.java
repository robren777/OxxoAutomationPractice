package WEB.simoti_Colombia.scripts.ReporteTransaccionesTDC;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import java.sql.SQLException;
import java.util.HashMap;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import WEB.simoti_Colombia.om.Login;
import WEB.util.GlobalVariables;
import modelo.BaseExecution;
import utils.password.PasswordUtil;
import utils.selenium.MEdgeTest;
import utils.selenium.SeleniumUtil;
import utils.sql.SQLResult;
import utils.sql.SQLUtil;

public class ATC_FT_004_Administrador_Transacciones_TDC_Generar_Reporte_opcion_Folio_Corresponsal extends BaseExecution{
	SeleniumUtil u;
	
	@Test(dataProvider = "data-provider")
	public void ATC_FT_004_Administrador_Transacciones_TDC_Generar_Reporte_opcion_Folio_Corresponsal_test(HashMap<String, String> data) throws SQLException {
		//Utilerias

		/*
		 * Variables
		 ******************************************************************************************/
		SQLUtil dbSimoti = new SQLUtil(GlobalVariables.DB_HOST_FCTDCQA_QAVIEW, GlobalVariables.DB_USER_FCTDCQA_QAVIEW, GlobalVariables.DB_PASSWORD_FCTDCQA_QAVIEW);
		String user = data.get("User");
		String password = PasswordUtil.decryptPassword(data.get("Password"));
		String URL = data.get("Url");
		String folio = data.get("Folio");
		String tran = data.get("Transaccion");
        String Query = "";
		
		

		// Paso 1
		addStep("El usuario ingresa a la aplicaci�n Access Control V3");
		u = new SeleniumUtil(new MEdgeTest(), true);
		u.getDriver().manage().window().maximize();	
		u.get(URL);
		Login pa = new Login(u, testCase);		
		testCase.addScreenShotCurrentStep("Imagen de login");
		
		// Paso 2
		addStep("El usuario ingresa usuario y contrase�a y da clic en el bot�n �Iniciar Sesi�n�.");
		pa.logginPlatform(user, password);
		u.waitForLoadPage();
		// Paso 3
		addStep("El usuario da clic en opci�n de SIMOTI.");
		pa.click_on_simoti();
		u.waitForLoadPage();
		// Paso 4
		addStep("El usuario despliega el men�: Transacciones Electr�nicas y selecciona el reporte: Transacciones TDC");
		u.hardWait(3);
		pa.transaccionesTDC();

		// Paso 5
		addStep("El usuario selecciona o captura valor en el filtro que se indique:\n"
				+ "-Opcion: <Folio>\n"
				+ "-Folio: <WebMethods>\n"
				+ "-Tipo de Transaccion: <C Corresponsal> \n"
				+ "Dar clic en [Consultar]");
		pa.busquedaFolio();
		pa.capturaDatosFolio(folio,tran);

		//Paso 6
		addStep("- El usuario valida que el sistema muestre los resultados de la consulta en las siguientes columnas, validar que se muestre el tipo de dato adecuado:\n" +
				" \n" +
				"�\tNo. (tipo de dato: Number)\n" +
				"�\tFolio (tipo de dato: Number)\n" +
				"�\tFecha (tipo de dato: Date)\n" +
				"�\tPlaza (tipo de dato: String)\n" +
				"�\tTienda (tipo de dato: String)\n" +
				"�\tCaja (tipo de dato: Number)\n" +
				"�\tAfiliaci�n (tipo de dato: Number)\n" +
				"�\tTarjeta (tipo de dato: String)\n");
		
		boolean validarDatos = pa.validarDatosTabla();
		
		assertTrue(validarDatos, "No se muestran datos");
		
		//Paso 7
		
		if (tran.equals("C Corresponsal")||tran.equals("V Venta")) {

			addStep("El usuario ejecuta el query para validar que los resultados que muestra el sistema sean acorde a los resultados del query." + 
				    "SELECT \n" +
					"    T.FOLIO\n" +
					"    ,TO_CHAR(T.CREATION_DATE,'DD-MM-YYYY HH24:MI:SS') --Fecha\n" +
					"    ,T.PLAZA\n" +
					"\t,T.TIENDA\n" +
					"    ,T.CAJA\n" +
					"    ,SUBSTR(T.TRACK1, 0, 6)||LPAD('X', LENGTH(T.TRACK1)-10, 'X')||SUBSTR(T.TRACK1,-4) --Tarjeta\n" +
					"    ,T.AFFILIATION --Afiliacion\n" +
					"    ,T.ISSUER --Emisor\n" +
					"    ,TO_CHAR(T.AMOUNT/100,'$999,999,999.99') AMOUNT --Monto\n" +
					"    ,T.SW_AUTH --Autorizacion\n" +
					"\t,DECODE(T.REVERSED,1,'Si','No') --Reversado\n" +
					"    ,T.WM_CODE --Codigo de estatus transaccion\n" +
					"    ,TO_CHAR( T.REVERSE_DT,'DD-MM-YYYY HH24:MI:SS' ) --Fecha de reverso\n" +
					"    ,T.SW_REVERSE_CODE --Codigo de reverso\n" +
					"    ,TO_CHAR( T.AUTH_DT, 'DD-MM-YYYY HH24:MI:SS' ) --Fecha de autorizacion\n" +
					"    ,T.SW_AUTH_CODE --Codigo de autorizacion\n" +
					"    ,W.DESCRIPTION --Status\n" +
					"    ,DECODE( T.BANK, \n" +
					"\t\t\t'OTHER',NULL \n" +
					"\t\t\t,'BBVA', NULL\n" +
					"\t\t\t,T.ISSUER ) --Casa Valera\n" +
					"    ,OPERATOR\n" +
					"FROM TPEUSER.TDC_TRANSACTION T\n" +
					"INNER JOIN TPEUSER.TDC_WM_CODE W ON W.WM_CODE = T.WM_CODE \n" +
					"WHERE \n" +
					"T.FOLIO =  1231\n");
			
			Query = "SELECT \n" +
					"    T.FOLIO\n" +
					"    ,TO_CHAR(T.CREATION_DATE,'DD-MM-YYYY HH24:MI:SS') --Fecha\n" +
					"    ,T.PLAZA\n" +
					"\t,T.TIENDA\n" +
					"    ,T.CAJA\n" +
					"    ,SUBSTR(T.TRACK1, 0, 6)||LPAD('X', LENGTH(T.TRACK1)-10, 'X')||SUBSTR(T.TRACK1,-4) --Tarjeta\n" +
					"    ,T.AFFILIATION --Afiliacion\n" +
					"    ,T.ISSUER --Emisor\n" +
					"    ,TO_CHAR(T.AMOUNT/100,'$999,999,999.99') AMOUNT --Monto\n" +
					"    ,T.SW_AUTH --Autorizacion\n" +
					"\t,DECODE(T.REVERSED,1,'Si','No') --Reversado\n" +
					"    ,T.WM_CODE --Codigo de estatus transaccion\n" +
					"    ,TO_CHAR( T.REVERSE_DT,'DD-MM-YYYY HH24:MI:SS' ) --Fecha de reverso\n" +
					"    ,T.SW_REVERSE_CODE --Codigo de reverso\n" +
					"    ,TO_CHAR( T.AUTH_DT, 'DD-MM-YYYY HH24:MI:SS' ) --Fecha de autorizacion\n" +
					"    ,T.SW_AUTH_CODE --Codigo de autorizacion\n" +
					"    ,W.DESCRIPTION --Status\n" +
					"    ,DECODE( T.BANK, \n" +
					"\t\t\t'OTHER',NULL \n" +
					"\t\t\t,'BBVA', NULL\n" +
					"\t\t\t,T.ISSUER ) --Casa Valera\n" +
					"    ,OPERATOR\n" +
					"FROM TPEUSER.TDC_TRANSACTION T\n" +
					"INNER JOIN TPEUSER.TDC_WM_CODE W ON W.WM_CODE = T.WM_CODE \n" +
					"WHERE \n" +
					"T.FOLIO = "+folio;
			
		    }
		
		
		if (tran.equals("D Devoluci�n")) {

			addStep("El usuario ejecuta el query para validar que los resultados que muestra el sistema sean acorde a los resultados del query.\r\n" + 
					"SELECT\r\n" + 
					"T.FOLIO , -- Campo Folio\r\n" + 
					"TO_CHAR(T.CREATION_DATE,'DD-MM-YYYY HH24:MI:SS') , -- Campo Fecha\r\n" + 
					"T.PLAZA , -- Campo Plaza\r\n" + 
					"T.TIENDA , -- Campo Tienda\r\n" + 
					"T.CAJA, -- Campo Caja\r\n" + 
					"SUBSTR(T.TRACK1, 0, 6)||LPAD('X', LENGTH(T.TRACK1)-10, 'X')||SUBSTR(T.TRACK1,-4), -- No identificado \r\n" + 
					"T.AFFILIATION ,-- Campo afiliaci�n \r\n" + 
					"T.ISSUER \r\n" + 
					"-- Este campo se identifica como emisor, confirmar si este es el caso \r\n" + 
					"/*${TRACK1_FORMAT}*/ ,\r\n" + 
					"-- Definir campo de entrada\r\n" + 
					"T.SW_AUTH , -- Campo de autorizaci�n \r\n" + 
					"DECODE(T.REVERSED,1,'Si','No'), -- Campo Reversado\r\n" + 
					"T.WM_CODE , -- Definir campo\r\n" + 
					"TO_CHAR( T.REVERSE_DT,'DD-MM-YYYY HH24:MI:SS' ) , --Definir campo \r\n" + 
					"SW_REVERSE_CODE , --Definir campo\r\n" + 
					"SW_DEV_CODE, -- Definir campo\r\n" + 
					"W.DESCRIPTION, --Definir campo\r\n" + 
					"DECODE( T.BANK, 'OTHER',NULL ,'BBVA',NULL ,T.ISSUER ) �Definir campo\r\n" + 
					",NULL\r\n" + 
					"FROM TPEUSER.TDC_DEVOLUTION T\r\n" + 
					"INNER JOIN TDC_WM_CODE W ON W.WM_CODE=T.WM_CODE\r\n" + 
					"WHERE FOLIO = ?; --Par�metro de entrada \r\n" + 
					"");	
			
			Query = "SELECT\r\n" + 
					"T.FOLIO , -- Campo Folio\r\n" + 
					"TO_CHAR(T.CREATION_DATE,'DD-MM-YYYY HH24:MI:SS') , -- Campo Fecha\r\n" + 
					"T.PLAZA , -- Campo Plaza\r\n" + 
					"T.TIENDA , -- Campo Tienda\r\n" + 
					"T.CAJA, -- Campo Caja\r\n" + 
					"SUBSTR(T.TRACK1, 0, 6)||LPAD('X', LENGTH(T.TRACK1)-10, 'X')||SUBSTR(T.TRACK1,-4), -- No identificado \r\n" + 
					"T.AFFILIATION ,-- Campo afiliaci�n \r\n" + 
					"T.ISSUER \r\n" + 
					"-- Este campo se identifica como emisor, confirmar si este es el caso \r\n" + 
					"/*${TRACK1_FORMAT}*/ ,\r\n" + 
					"-- Definir campo de entrada\r\n" + 
					"T.SW_AUTH , -- Campo de autorizaci�n \r\n" + 
					"DECODE(T.REVERSED,1,'Si','No'), -- Campo Reversado\r\n" + 
					"T.WM_CODE , -- Definir campo\r\n" + 
					"TO_CHAR( T.REVERSE_DT,'DD-MM-YYYY HH24:MI:SS' ) , --Definir campo \r\n" + 
					"SW_REVERSE_CODE , --Definir campo\r\n" + 
					"SW_DEV_CODE, -- Definir campo\r\n" + 
					"W.DESCRIPTION, --Definir campo\r\n" + 
					"DECODE( T.BANK, 'OTHER',NULL ,'BBVA',NULL ,T.ISSUER ) �Definir campo\r\n" + 
					",NULL\r\n" + 
					"FROM TPEUSER.TDC_DEVOLUTION T\r\n" + 
					"INNER JOIN TPEUSER.TDC_WM_CODE W ON W.WM_CODE=T.WM_CODE\r\n" + 
					"WHERE FOLIO = "+folio;
		
		    }

			SQLResult ResultConsulta = executeQuery(dbSimoti, Query);
			System.out.print("consulta" + Query);

			boolean validacionQuery = ResultConsulta.isEmpty();
			System.out.print("validacion" + validacionQuery);

			if (!validacionQuery) {
				testCase.addTextEvidenceCurrentStep("Existen registros");
			} else {
				testCase.addTextEvidenceCurrentStep("No existen registros");
			}
			
			testCase.addQueryEvidenceCurrentStep(ResultConsulta);
			
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
		return null;
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}
	@AfterMethod
	public void afterMethod() {
		//u.getDriver().quit();
	}

}