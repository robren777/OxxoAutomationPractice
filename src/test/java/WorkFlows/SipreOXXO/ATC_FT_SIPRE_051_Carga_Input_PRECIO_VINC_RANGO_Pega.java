package WorkFlows.SipreOXXO;

import static org.testng.Assert.assertFalse;

import java.util.HashMap;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import WorkFlows.util.GlobalVariables;
import WorkFlows.util.SipreOXXO.pomSipreOXXO;
import WorkFlows.util.ultimus.pomUltimus;
import modelo.BaseExecution;
import utils.selenium.FirefoxTest;
import utils.selenium.SeleniumUtil;
import utils.sql.SQLResult;

public class ATC_FT_SIPRE_051_Carga_Input_PRECIO_VINC_RANGO_Pega extends BaseExecution{
	SeleniumUtil u;
	
	@Test(dataProvider = "data-provider")
	 public void ATC_FT_SIPRE_051_Carga_Input_PRECIO_VINC_RANGO_Pega_Test(HashMap<String, String> data) throws Exception {
		testCase.setProject_Name("Workflows Library: Sipre Oxxo");
	
	/**
	 * Variables
	 * 
	 */
		
		
		String tipoArchivo = data.get("TipoArchivo");
		String nomArchivo = data.get("NomArchivo");
		
	


	/**
	 * 
	 * Utilerias
	 */
		u = new SeleniumUtil(new FirefoxTest(), true);
		
	
		String windowUltimus = u.getDriver().getWindowHandle();
		pomSipreOXXO util = new pomSipreOXXO(u,testCase);
		pomUltimus ultimus = new pomUltimus (u);
		utils.sql.SQLUtil dbSipreOxxo = new utils.sql.SQLUtil(GlobalVariables.DB_HOST_SIPREOXXO,
				GlobalVariables.DB_USER_SIPREOXXO, GlobalVariables.DB_PASSWORD_SIPREOXXO);
	
	
		// paso 1
					addStep("Ingresar a SIPRE");
					util.loginPega(GlobalVariables.userSipreOXXOAdminPEGA, GlobalVariables.passSipreOXXOUAdminPEGA);
					util.selectMenuSistemaPrecios();
					testCase.addScreenShotCurrentStep("Login");
					
			// paso 2
					addStep("Seleccionar el Menú Configuración");
					

			// paso 3
					addStep("Seleccionar la opción Carga de Archivos");
					util.clickMenuConfiguracionPega("Carga de archivos");
					testCase.addScreenShotCurrentStep("Carga de archivos");
			// paso 4
					addStep("Seleccionar la opción PRECIO VINC RANGO");
			// paso 5
					addStep("Dar click al botón  Seleccionar Archivo");
			// paso 6
					addStep("Seleccionar el Archivo PRECIO VINC RANGO");
			// paso 7
					addStep("Dar click en el botón Abrir");
			// paso 8
					
					addStep("Dar click en Subir Archivo");
					util.SeleccionarArchivo(tipoArchivo, nomArchivo);
					
					
			// paso 9
					addStep("Se valida que la información subida en el input se encuentre en la consulta: SELECT * FROM input_preciovincrango;");
					
				
					String fechaActual = util.getDate();
					testCase.addTextEvidenceCurrentStep("Fecha de hoy: "+fechaActual);
					String QueryCargaArchivo = "SELECT * FROM input_preciovincrango WHERE trunc(UPDATED_DATE) = TO_DATE('"+fechaActual+"', 'DD/MM/YY') and rownum <= 5";
					SQLResult queryResultCargaArchivo= executeQuery(dbSipreOxxo, QueryCargaArchivo);
					testCase.addQueryEvidenceCurrentStep(queryResultCargaArchivo);
					System.out.println("resultado consulta --->"+queryResultCargaArchivo.isEmpty());
					assertFalse(queryResultCargaArchivo.isEmpty(), "La consulta no muestra resultados de carga del día de hoy");

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
		return "Tener Acceso Dominio Oxxo" + 
				"Contar con Orgchart de sipre" + 
				"Contar con usuarios configurados al orgchart" + 
				"Contar con JFG de perfil adecuado en Ultimus o Pega" + 
				"Contar con Acceso a Control -M" + 
				"Solicitar el Holdeo de los JOBS a ejecutar" + 
				"Contar con Acceso al esquema de XXWF_SIPRE2" + 
				"CONEXION FCIAS";
	}

	@Override
	public String setTestDescription() {
		// TODO Auto-generated method stub
		return "Validar que se pueda realizar una Carga exitosa del INPUT de PRECIO VINC RANGO desde el usuario Administrador";
	}

	@Override
	public String setTestDesigner() {
		// TODO Auto-generated method stub
		return "Misraim Hernandez Monsivais";
	}

	@Override
	public String setTestFullName() {
		// TODO Auto-generated method stub
		return "ATC_FT_SIPRE_051_Carga_Input_PRECIO_VINC_RANGO_Pega_Test";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}

}
