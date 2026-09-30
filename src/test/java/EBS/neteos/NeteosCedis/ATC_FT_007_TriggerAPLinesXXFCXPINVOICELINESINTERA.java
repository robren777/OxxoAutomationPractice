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
 * DESCRIPCION: verifica que se haya realizado la separacion de devoluciones y cargos logisticos que llegan mediante la interface RO3 a la tabla XXFC_XP_INVOICE_LINES_INTER
 * @author ricardor4@hexaware.com
 */
public class ATC_FT_007_TriggerAPLinesXXFCXPINVOICELINESINTERA extends BaseExecution {

	@Test(dataProvider = "data-provider")
	public void ATC_FT_007_TriggerAPLinesXXFCXPINVOICELINESINTERA_test(HashMap<String, String> data) throws Exception {

		/*
		 * VARIABLES
		 */
		String tabla1 = "select INVOICE_ID,INVOICE_LINE_ID,LINE_NUMBER,DESCRIPTION from XXFC.XXFC_XP_INVOICE_LINES_INTER where invoice_id='18003292'";
		

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
		
		// PASO 2
		addStep("Se ejecuta el trigger XXFC_XP_INVOICE_LINES_INTER_AI.");
				
		// PASO 3
		addStep("Se valida que solo existan los registros de artículos y no de Cargos Logísticos.");
		System.out.println(tabla1);
		SQLResult validat = AVANTE.executeQuery(tabla1);
		boolean validaB = validat.isEmpty();

		if (!validaB) {
			testCase.addQueryEvidenceCurrentStep(validat);
		}
		assertFalse("Consulta invalida ", validaB);
		System.out.println(validaB);
		testCase.addBoldTextEvidenceCurrentStep("Validacion exitosa");
		
		u.close();
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_007_TriggerAPLinesXXFCXPINVOICELINESINTERA_test";
	}

	@Override
	public String setTestDescription() {
		return "Validar que se realice la separacion de los registros de devoluciones que llegan mediante la interface RO3 a la tabla XXFC_XP_INVOICE_LINES_INTER";
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
		return "Exista informacion en la tabla XXFC_XP_INVOICE_LINES_INTER  la cual se pobla con informacion enviada desde RMS por medio de la interface R03."
				+ "Existan configurados los valores de conceptos de Cargos Logisticos en Value Set XXFC_RTV_CONCEPTOS";
	}

	@Override
	public void beforeTest() {

	}
}