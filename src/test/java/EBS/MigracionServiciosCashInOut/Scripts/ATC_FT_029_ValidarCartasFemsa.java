package EBS.MigracionServiciosCashInOut.Scripts;

import static org.testng.Assert.assertNotNull;

import java.util.HashMap;

import org.testng.annotations.Test;

import EBS.util.GlobalVariables;
import modelo.BaseExecution;
import utils.sql.SQLResult;
/**
* DESCRIPCIÓN: Realiza la busqueda de las cartas femsa
* 
* NOMBRE CASOS DE PRUEBA MANUAL QUE SE ABARCAN: 
* 	-MTC-FT-029- Validar cartas Femsa
* 
* PREREQUISITOS:
* 	- Ejecucion del script ATC_FT_026_ValidarConcurrenteProcesoDispersion
* 	-Acceso a la db EBS
* 
* INSUMOS:
* 
* @author Michelle Ceja
* @date 25/Noviembre/2024
*/
public class ATC_FT_029_ValidarCartasFemsa extends BaseExecution {
	
	@Test(dataProvider = "data-provider")

	public void ATC_FT_029_ValidarCartasFemsa_test(HashMap<String, String> data)
			throws Exception {
		
		testCase.setProject_Name("I24053 - Migración servicios Cash-in y Cash-out");

		/*
		 * VARIABLES
		 */
	
		String id = data.get("id");
		
		String consulta1 = "SELECT CARTA_FEMSA_ID, PIE, FECHA_TRANSACCION,FECHA_APLICACION,DESCRIPCION_CORTA FROM XXFC.xxfc_cartas_femsa WHERE CARTA_FEMSA_ID = "+id;
		String consulta2 = "SELECT CARTA_FEMSA_LINEA_ID, CARTA_FEMSA_ID,CREATION_DATE, ORACLE_CR, LAST_UPDATE_DATE FROM XXFC.xxfc_cartas_femsa_lineas WHERE CARTA_FEMSA_ID = "+id;

		/*
		 * UTILERIAS
		 */
		
		utils.sql.SQLUtil EBSAVANTE = new utils.sql.SQLUtil(GlobalVariables.DB_HOST_AVEBQA,
				GlobalVariables.DB_USER_AVEBQA, GlobalVariables.DB_PASSWORD_AVEBQA);

		/*
		 * PASOS DEL CASO DE PRUEBA, MATRIZ: RST Devoluciones OXXO PAY 
		 */

		// PASO 1
		addStep("Conectarse a la base de datos EBS 12.2.4");
		System.out.println(consulta1);
		// PASO 2 
		addStep("Ejecutar el query:  SELECT * FROM XXFC.xxfc_cartas_femsa WHERE CARTA_FEMSA_ID = "+id);
		SQLResult validat = EBSAVANTE.executeQuery(consulta1);
		testCase.addQueryEvidenceCurrentStep(validat);
		assertNotNull(validat, "La respuesta no tiene el el resultado esperado.");
		
		//PASO 3
		
		addStep("Ejecutar el query:  SELECT * FROM XXFC.xxfc_cartas_femsa_lineas WHERE CARTA_FEMSA_ID = "+id);
		SQLResult validat2 = EBSAVANTE.executeQuery(consulta2);
		testCase.addQueryEvidenceCurrentStep(validat2);
		assertNotNull(validat2, "La respuesta no tiene el el resultado esperado.");
		
		

		
		EBSAVANTE.close();

	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_029_ValidarCartasFemsa_test";
	}

	@Override
	public String setTestDescription() {
		return "Validar que después de efectuar el proceso de dispersion, esta genere las cartas femsa";
	}

	@Override
	public String setTestDesigner() {
		return "Alicia Michelle Ceja Gomez";
	}

	@Override
	public String setTestInstanceID() {
		return null;
	}

	@Override
	public String setPrerequisites() {
		return "Acceso aplicación EBS. Ejecutar el proceso de dispersion";
	}

	@Override
	public void beforeTest() {
		// TODO Auto-generated method stub

	}

}