package WEB.simoti_Colombia.scripts.ReporteTransaccionesTDC;

import static org.testng.Assert.assertTrue;

import java.sql.SQLException;
import java.util.HashMap;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import WEB.simoti_Colombia.om.Login;
import modelo.BaseExecution;
import utils.password.PasswordUtil;
import utils.selenium.MEdgeTest;
import utils.selenium.SeleniumUtil;

public class ATC_FT_005_Administrador_Transacciones_TDC_Generar_Reporte_opcion_Folio_Venta extends BaseExecution{
	SeleniumUtil u;
	
	@Test(dataProvider = "data-provider")
	public void ATC_FT_005_Administrador_Transacciones_TDC_Generar_Reporte_opcion_Folio_Venta_test(HashMap<String, String> data) throws SQLException {
		//Utilerias

		/*SQLUtil dbSC = new SQLUtil(GlobalVariables.DB_HOST_FCWM6QA_QAVIEW, GlobalVariables.DB_USER_FCWM6QA_QAVIEW,
				GlobalVariables.DB_PASSWORD_FCWM6QA_QAVIEW);*/
		
		/*
		 * Variables
		 ******************************************************************************************/
		String user = data.get("User");
		String password = PasswordUtil.decryptPassword(data.get("Password"));
		String URL = data.get("Url");
		String folio = data.get("Folio");
		String tran = data.get("Transaccion");		
		String query ="";
		
		// Paso 1
		addStep("El usuario ingresa a la aplicaci�n Access Control V3");
		u = new SeleniumUtil(new MEdgeTest(), true);
		u.get(URL);
		Login pa = new Login(u, testCase);		
		testCase.addScreenShotCurrentStep("Imagen de login");
		
		// Paso 2
		addStep("El usuario ingresa usuario y contrase�a y da clic en el bot�n �Iniciar Sesi�n�.");
		pa.logginPlatform(user, password);
		
		// Paso 3
		addStep("El usuario da clic en opci�n de SIMOTI.");
		pa.click_on_simoti();
		
		// Paso 4
		addStep("El usuario despliega el men�: Transacciones Electr�nicas y selecciona el reporte: Transacciones TDC");
		pa.transaccionesTDC();
		
		// Paso 5
		addStep("El usuario selecciona o captura valor en el filtro que se indique:\n"
				+ "-Opcion: <Folio>\n"
				+ "-Folio: <WebMethods>\n"
				+ "-Tipo de Transaccion: <V Venta> \n"
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
						"�\tTarjeta (tipo de dato: String)\n"+
						"�\\tEmisor(tipo de dato: String)\\n\""+
						"�\\tMonto (tipo de dato: Number)\\n\""+
						"�\\tAutorización (tipo de dato: Number)\\n\""+
						"�\\tReversado (tipo de dato: String)\\n\""+
						"�\\tEstatus (tipo de dato: String)\\n\""+
						"�\\tError (tipo de dato: String)\\n\""+
						"�\\tCasa Valera (tipo de dato: String)\\n\""
						);
				boolean validarDatos = pa.validarDatosTabla();
				
				assertTrue(validarDatos, "No se muestran datos");

				if (tran.equals("V Venta")) {

					addStep("SELECT \n" +
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
							"T.FOLIO =  123\n");
                   /*
					SQLResult ResultConsulta = executeQuery(dbSimoti, Query);
					System.out.print("consulta" + Query);

					boolean validacionQuery = ResultConsulta.isEmpty();

					if (!validacionQuery) {
						testCase.addTextEvidenceCurrentStep("Existen registros");
					} else {
						testCase.addTextEvidenceCurrentStep("No existen registros");
					}
					
					testCase.addQueryEvidenceCurrentStep(ResultConsulta);
					
					assertFalse(validacionQuery, "No existen registros en la base de datos");	

					*/
				}
		
		
		
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
		return "ATC_FT_005_Administrador_Transacciones_TDC_Generar_Reporte_opcion_Folio_Venta";
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