package WM.interfaces.Ajustes_Remesas;

import static org.testng.Assert.assertFalse;

import java.util.HashMap;

import org.testng.annotations.Test;

import WM.util.GlobalVariables;
import modelo.BaseExecution;
import utils.sql.SQLResult;

public class ATC_FT_001_Validar_Estructura_de_bases_de_datos extends BaseExecution{
	@Test(dataProvider = "data-provider")
	public void ATC_FT_001_Validar_Estructura_de_bases_de_datos_test(HashMap<String, String> data) throws Exception {
	
/** UTILERIA *********************************************************************/	
		
		utils.sql.SQLUtil dbTPE = new utils.sql.SQLUtil(GlobalVariables.DB_HOST_FCTPE_QRO, GlobalVariables.DB_USER_FCTPE_QRO,GlobalVariables.DB_PASSWORD_FCTPE_QRO);
		
		testCase.setProject_Name("I23035 Ajustes Remesas");    
				
		/** VARIABLES *********************************************************************/	
		

		String param_tpe = "SELECT ENTITY,CODE,VALUE1,DESCRIPTION FROM TPEUSER.TPE_FR_CONFIG  WHERE application = 'TSF' AND entity IN ('WU','TN','MGI')";
		
			// PASO 1 y 2				
			addStep(" Ingresar a la base de datos TPE Y Validar existan parámetros de configuración para la tabla TPE_FR_CONFIG BD ");		
				
				System.out.println(GlobalVariables.DB_HOST_AVEBQA);

				SQLResult paso1 = executeQuery(dbTPE, param_tpe);		
				
				boolean av1 = paso1.isEmpty();
				
				if (!av1) {
					testCase.addQueryEvidenceCurrentStep(paso1);

				} 	
				assertFalse(av1, "No se obtiene informacion de la consulta en la tabla ");
			}
			
			@Override
			public void beforeTest() {
				
			}

			@Override
			public String setTestDescription() {
				return "Validar existan parámetros configurables de los remesadores en la tabla TPE_CONFIG_REMESA_ESTADO y TPE_CONFIG_TRANSMISOR de la base de datos TPE";
			}

			@Override
			public String setTestDesigner() {
				return "Yair Lizarraga";
			}

			@Override
			public String setTestFullName() {
				return "ATC_FT_001_Validar_Estructura_de_bases_de_datos_test";
			}

			@Override
			public String setTestInstanceID() {
				return null;
			}

			@Override
			public String setPrerequisites() {
				return "**Tener acceso a la BD TPEQRO";
			}
		}
