package WM.homologacion_MVP3;

import static org.testng.Assert.assertFalse;

import java.util.HashMap;

import org.testng.annotations.Test;

import WM.integrationServer.om.PakageManagementNewVersion;
import WM.util.GlobalVariables;
import modelo.BaseExecution;
import utils.password.PasswordUtil;
import utils.selenium.ChromeTest;
import utils.selenium.SeleniumUtil;
import utils.sql.SQLResult;

public class ATC_FT_002_EjecucionCatalagoLlenadoTablasDesdeControlM extends BaseExecution{
	@Test(dataProvider = "data-provider")
	public void ATC_FT_002_EjecucionCatalagoLlenadoTablasDesdeControlM_test(HashMap<String, String> data) throws Exception {
	
/** UTILERIA *********************************************************************/	
		
		utils.sql.SQLUtil dbLog = new utils.sql.SQLUtil(GlobalVariables.DB_HOST_FCWMLQA, GlobalVariables.DB_USER_FCWMLQA,GlobalVariables.DB_PASSWORD_FCWMLQA);
		utils.sql.SQLUtil dbAvebqa = new utils.sql.SQLUtil(GlobalVariables.DB_HOST_AVEBQA, GlobalVariables.DB_USER_AVEBQA, GlobalVariables.DB_PASSWORD_AVEBQA);
		
		SeleniumUtil u;
		PakageManagementNewVersion pok;	
		
		testCase.setProject_Name("I20134 Homologacion Cuentas por Pagar");    
				
		/** VARIABLES *********************************************************************/	

		String selectStatusI = "SELECT ID_ARTICULO,DESCRIPCION,WM_STATUS FROM XXFC.XXFC_XP_CAT_CLAVES_ARTICULO WHERE WM_STATUS = 'L'";
		String tdcIntegrationServerFormat = "select * from (SELECT Tbl.run_id,interface, start_dt, end_dt, status, server "
				+ "FROM WMLOG.WM_LOG_RUN Tbl " + "WHERE INTERFACE LIKE '%OE12MX%' "
				+ "ORDER BY START_DT DESC) where rownum <=1";
		
		String user = data.get("user");
		String ps = PasswordUtil.decryptPassword(data.get("ps"));
		String server = data.get("server");
		String searchedStatus = "R";
		String run_id = "";
		
		
		
		// PASO 1 al 6 *********************************************************************
		
		addStep("Ejecutar el servicio " + data.get("servicio"));
		
		u = new SeleniumUtil(new ChromeTest(), true);
		pok = new PakageManagementNewVersion(u, testCase);
		
		System.out.println(GlobalVariables.DB_HOST_FCWMLQA);
		String contra = "http://" + server + ":5555";
		u.get(contra);
		pok.inicarSesion(user, ps);
		String dateExecution = pok.runIntefaceWM(data.get("interfase"), data.get("servicio"), null);
		System.out.println("Respuesta dateExecution " + dateExecution);
		
		System.out.println(tdcIntegrationServerFormat);
		SQLResult is = dbLog.executeQuery(tdcIntegrationServerFormat);
		run_id = is.getData(0, "RUN_ID");
		String status1 = is.getData(0, "STATUS");
		System.out.println("RUN_ID = " + run_id + "\t Status: " + status1 );

		boolean valuesStatus = status1.equals(searchedStatus);// Valida si se encuentra en estatus R

		while (valuesStatus) {			
			status1 = is.getData(0, "STATUS");
			run_id = is.getData(0, "RUN_ID");
			valuesStatus = status1.equals(searchedStatus);
			u.hardWait(2);				
		}
				// PASO 7 y 8 *********************************************************************
				
				addStep("Validar que la tabla XXFC_XP_CAT_CLAVES_ARTICULO wm_status='L'");		
				
				System.out.println(GlobalVariables.DB_HOST_AVEBQA);

				SQLResult paso7 = executeQuery(dbAvebqa, selectStatusI);		
				
				boolean av3 = paso7.isEmpty();
				
				if (!av3) {
					testCase.addQueryEvidenceCurrentStep(paso7);

				} 

				System.out.println(av3);		
				assertFalse(av3, "No se obtiene informacion de la consulta en la tabla " + data.get("tabla"));
				
				
			}
			
			@Override
			public void beforeTest() {
				// TODO Auto-generated method stub
				
			}

			@Override
			public String setTestDescription() {
				// TODO Auto-generated method stub
				return "Ejecutar mediante control M el JOB que activa  el concurrente \r\n"
						+ "XXFC - Extracción de catálogos para portal de proveedores HOM para el llenado de la tabla XXFC_XP_CAT_CLAVES_ARTICULO y este sea enviado al portal ";
			}

			@Override
			public String setTestDesigner() {
				// TODO Auto-generated method stub
				return "Yair Lizarraga";
			}

			@Override
			public String setTestFullName() {
				// TODO Auto-generated method stub
				return "ATC_FT_002_EjecucionCatalagoLlenadoTablasDesdeControlM_test";
			}

			@Override
			public String setTestInstanceID() {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public String setPrerequisites() {
				return "* Tener acceso a EBS\r\n"
						+ "* Tener acceso a las BD de AVANTE";
			}
		}
