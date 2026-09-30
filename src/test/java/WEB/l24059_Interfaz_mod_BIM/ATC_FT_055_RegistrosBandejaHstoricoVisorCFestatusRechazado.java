package WEB.l24059_Interfaz_mod_BIM;

import static org.testng.Assert.assertTrue;

import java.util.HashMap;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import WEB.util.GlobalVariables;
import WEB.util.PortalEquipamiento.PomPortalEquipamiento;
import WEB.util.PortalEquipamiento.PomPortalEquipamientoBandejaAprobador;
import WEB.util.PortalEquipamiento.PomPortalEquipamientoBandejaHistoricoV;
import modelo.BaseExecution;
import utils.password.PasswordUtil;
import utils.selenium.MEdgeTest;
import utils.selenium.SeleniumUtil;
import utils.sql.SQLResult;

/**
* DESCRIPCION: Verificar que los resultados del grid concuerden con el estatus RECHAZADO.
* 
* NOMBRE CASOS DE PRUEBA MANUAL QUE SE ABARCAN:
* - MTC-FT-055-Registros en la Bandeja Hst�rico Visor de Cuarto Frio con estatus de Aprobacion "Rechazado".
* 
* PREREQUISITOS:
* - Existan registros en la pantalla "Visor WF Controlador CF" con el estatus RECHAZADO
* - En el DataProvider ingresar en la columna estatus el valor a consultar
*
* @author elberth.ruiz@grupotimex.onmicrosoft.com
* 
* @date 12/Mayo/2025
*/

public class ATC_FT_055_RegistrosBandejaHstoricoVisorCFestatusRechazado extends BaseExecution{
	private SeleniumUtil u;

	@Test(dataProvider = "data-provider")
	
	 public void ATC_FT_055_RegistrosBandejaHstoricoVisorCFestatusRechazado_test(HashMap<String, String> data) throws Exception {
		
		testCase.setProject_Name("FrameworkWeb --> Portal de equipamiento V2");
		
		//*******************************************************************************************
		
				SQLResult queryResult;
				String Password = PasswordUtil.decryptPassword(data.get("ps"));
				String Menu1 = data.get("MenuN1");
				String Menu2 = data.get("MenuN2");
				String Status = data.get("Estatus");
				String EX = "";
				//Conexiones a BD
				
				
				//FCIAS***
				utils.sql.SQLUtil dbFCIAS_XXEX = new utils.sql.SQLUtil(GlobalVariables.DB_HOST_FCIASQA_QAVIEW,GlobalVariables.DB_USER_FCIASQA_QAVIEW, GlobalVariables.DB_PASSWORD_FCIASQA_QAVIEW);
				
				u = new SeleniumUtil(new MEdgeTest(),true);
				PomPortalEquipamiento pomPortalEquipamiento = new PomPortalEquipamiento(u, testCase);
				PomPortalEquipamientoBandejaAprobador pomPortalEquipamientoBA = new PomPortalEquipamientoBandejaAprobador(u, testCase);
				PomPortalEquipamientoBandejaHistoricoV pomPortalEquipamientoBHV = new PomPortalEquipamientoBandejaHistoricoV(u, testCase);
				u.get(pomPortalEquipamiento.getUrl());
				//*******************************************************************************************
				/**
				 *                                             PASO 1
				 ******************************************************************************************/	
				addStep("Ingresar al aplicativo Portal de equipamiento v2");
				pomPortalEquipamiento.login(data.get("user"), Password);
				
				
				/**
				 *                                             PASO 2
				 ******************************************************************************************/
				addStep("Damos clic en el aplicativo Expansion >> \"Portal de equipamiento V2\"");
				pomPortalEquipamiento.appPorEquipamientoV2();
				
				/**
				 *                                             PASO 3
				 ******************************************************************************************/
				addStep("Damos clic En el menu damos clic en Articulos especiales >>Articulos especiales>Visor WF Controlador CF");
				pomPortalEquipamientoBA.menuApp(Menu1,Menu2);
				
				/**
				 *                                             PASO 4
				 ******************************************************************************************/
				addStep("Del campo Estatus de aprobacion seleccionamos el valor \"Rechazado\" y damos clic al boton \"Consultar\"");
				pomPortalEquipamientoBHV.BandejaAprob(Status);
				
				
				EX = pomPortalEquipamientoBHV.extrae(Status);
				
				// Querys
				//Se valida la Tabla XXEX_LE_HEADER la cual consulta la pantalla Visor WF Controlador CF
				
				 String QUERY_STATUS = "SELECT ID_LE, FECHA_ELABORACION, CR_TIENDA FROM XXEX.XXEX_LE_HEADER WHERE ID_LE IN (SELECT ID_LE FROM XXEX.Xxex_Alta_Cf WHERE \r\n"
				 		+ "Id_Asignacion IN (SELECT ID_ASIGNACION FROM XXEX.Xxex_Niveles_Aprobacion WHERE \r\n"
				 		+ "Estatus_Aprobacion = '"+Status.toUpperCase()+"')) AND ID_LE IN("+EX+")";
				
				queryResult = executeQuery(dbFCIAS_XXEX, QUERY_STATUS);
				testCase.addQueryEvidenceCurrentStep(queryResult);
				Boolean validacion = false;
				if (queryResult.getRowCount() > 0) 
					validacion = true;
				assertTrue(validacion, "No se encontraro el codigo");
		}
			
	@AfterMethod
	public void afterMethod() {
	u.getDriver().quit();
		
	}
	
	@Override
	public void beforeTest() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String setPrerequisites() {
		// TODO Auto-generated method stub
		return "Existan registros en la pantalla \"Visor WF Controlador CF\" con el estatus RECHAZADO";
	}

	@Override
	public String setTestDescription() {
		// TODO Auto-generated method stub
		return "Verificar que los resultados del grid concuerden con el estatus RECHAZADO";
	}

	@Override
	public String setTestDesigner() {
		// TODO Auto-generated method stub
		return "Elberth Humberto Ruiz Cota";
	}

	@Override
	public String setTestFullName() {
		// TODO Auto-generated method stub
		return "ATC_FT_055_RegistrosBandejaHstoricoVisorCFestatusRechazado_test";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}

}
