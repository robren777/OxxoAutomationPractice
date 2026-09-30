package EBS.neteos.NeteosCedis;

import static org.junit.Assert.assertFalse;

import java.util.HashMap;

import org.testng.annotations.Test;

import EBS.util.GlobalVariables;
import modelo.BaseExecution;
import utils.selenium.MEdgeTest;
import utils.selenium.SeleniumUtil;
import utils.sql.SQLResult;
/**
 * PRERREQUISITOS: haber ejecutado la interface RO3_MX
 * tener ordenes con devoluciones y cargos logisticos
 *
 * DESCRIPCION: verifica que se haya realizado la separacion de devoluciones y cargos logisticos que llegan mediante la interface RO3 a la tabla XXFC_XP_INVOICES_INTERFACE
 * @author ricardor4@hexaware.com
 */
public class ATC_FT_006_TriggerAPHeadersXXFCXPINVOICESINTERFACEAI extends BaseExecution {

	@Test(dataProvider = "data-provider")
	public void ATC_FT_006_TriggerAPHeadersXXFCXPINVOICESINTERFACEAI_test(HashMap<String, String> data) throws Exception {

		/*
		 * VARIABLES
		 */
		String tabla1 = "select INVOICE_ID,DESCRIPTION,INVOICE_DATE from APPS.AP_INVOICES_INTERFACE where invoice_id='18003292'";
		String tabla1stage = "select INVOICE_ID,INVOICE_NUM,INVOICE_DATE from XXFC.XXFC_XP_INVOICES_INTERFACE where invoice_id='18003292'";
			
     	/*
		 * UTILERIAS
		 */
		SeleniumUtil u = new SeleniumUtil(new MEdgeTest(), true);
		utils.sql.SQLUtil 	AVANTE = new utils.sql.SQLUtil(GlobalVariables.DB_HOST_AVEBQA,
				GlobalVariables.DB_USER_AVEBQA, GlobalVariables.DB_PASSWORD_AVEBQA);
		/*
		 * PASOS DEL CASO DE PRUEBA
		 */

		
		// PASO 1
		addStep("Acceder a Control M ");
		 
		// PASO 1
		addStep("Se ejecuta el trigger XXFC_XP_INVOICES_INTERFACE_AI.");
				
		// PASO 4
		addStep("Se valida que se realice la separacion de los registros de devoluciones para las dos tablas AP_INVOICES_INTERFACE y XXFC_XP_INVOICES_INTERFACE.");
		
		//---------------------------- AP_INVOICES_INTERFACE
		
		System.out.println(tabla1);
		SQLResult validat = AVANTE.executeQuery(tabla1);
		boolean validaa = validat.isEmpty();

		if (!validaa) {
			testCase.addQueryEvidenceCurrentStep(validat);
		}
		assertFalse("Consulta invalida ", validaa);
		System.out.println(validaa);
		testCase.addBoldTextEvidenceCurrentStep("Validacion exitosa");
		
		//---------------------------- XXFC.XXFC_XP_INVOICE_LINES_INTER
		
		System.out.println(tabla1stage);
		SQLResult validat2 = AVANTE.executeQuery(tabla1stage);
		boolean validab = validat2.isEmpty();

		if (!validab) {
			testCase.addQueryEvidenceCurrentStep(validat2);
		}
		assertFalse("Consulta invalida ", validab);
		System.out.println(validab);
		testCase.addBoldTextEvidenceCurrentStep("Validacion exitosa");
		
		// PASO 5
		addStep("Se valida que se poble tambien la tabla AP_INVOICES_INTERFACE con los registros que llegan a la tabla Stage.");
		//---------------------------- AP_INVOICES_INTERFACE
		
				System.out.println(tabla1);
				SQLResult validat3 = AVANTE.executeQuery(tabla1);
				boolean validaa3 = validat3.isEmpty();

				if (!validaa3) {
					testCase.addQueryEvidenceCurrentStep(validat3);
				}
				assertFalse("Consulta invalida ", validaa3);
				System.out.println(validaa3);
				testCase.addBoldTextEvidenceCurrentStep("Validacion exitosa");
				
		u.close();
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_006_TriggerAPHeadersXXFCXPINVOICESINTERFACEAI_test";
	}

	@Override
	public String setTestDescription() {
		return "Validar que se realice la separacion de los registros de devoluciones que llegan mediante la interface RO3 a la tabla XXFC_XP_INVOICES_INTERFACE";
	}

	@Override
	public String setTestDesigner() {
		return "ricardor4@hexaware.com";
	}

	@Override
	public String setTestInstanceID() {
		return null;
	}

	@Override
	public String setPrerequisites() {
		return "Exista informacion en la tabla XXFC_XP_INVOICES_INTERFACE la cual se pobla con informacion enviada desde RMS por medio de la interface R03."
				+ "Existan configurados los valores de conceptos de Cargos Logisticos en Value Set XXFC_RTV_CONCEPTOS";
	}

	@Override
	public void beforeTest() {

	}
}