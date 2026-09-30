package RDM.cambiosMasivosPmo.hu013;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import java.util.HashMap;
import java.util.logging.Logger;

import org.testng.annotations.Test;

import RDM.util.CambiosMasivosQueries;
import azure.annotation.AzureTestInfo;
import modelo.BaseExecution; import RDM.util.GlobalVariables;
import utils.sql.SQLResult;
import utils.sql.SQLUtil;

@AzureTestInfo(areaPath = "I23084-Cambios Masivos PMO\\MVP1-Cambios Masivos PMO", iterationPath = "I23084-Cambios Masivos PMO\\Sprint MVP1\\Sprint 1.11", tags = "RLS-2", userStoryId = 35522)

/**
 * DESCRIPCIÓN: Verificar los cambios después de una recuperación, los datos
 * guardados en la tabla XXFC_PMO_TIENDA_BKP que pasan a la XXFC_PMO_TIENDA
 *
 * NOMBRE CASOS DE PRUEBA MANUAL QUE SE ABARCAN:
 *
 * MTC-FT-010 Verificar la tienda en base de datos
 * 
 * PREREQUISITOS:
 *
 * 1.- Edge 2.- Tener acceso a la aplicación 3.- Restaurar un respaldo 4.- Tener
 * acceso a la base de datos FCRD71CU (RDM_TLAJOULCO) 5.- Tener acceso a las
 * tablas XXFC_PMO_ROUTE, XXFC_PMO_TIENDA, XXFC_PMO_ROUTE_BKP,
 * XXFC_PMO_TIENDA_BKP
 * 
 * @author JesusH4@hexaware.com
 * @date 03/01/2025
 */
public class ATC_FT_010_VerificarLaTiendaEnBaseDeDatos extends BaseExecution {
	/*
	 * Logger
	 */
	private static final Logger logger = Logger.getLogger(ATC_FT_010_VerificarLaTiendaEnBaseDeDatos.class.getName());

	@Test(dataProvider = "data-provider")
	public void ATC_FT_010_VerificarLaTiendaEnBaseDeDatos_test(HashMap<String, String> data) throws Exception {
		testCase.setProject_Name("I23084-Cambios Masivos PMO");
		/*
		 * Variables
		 */
		String ruta = data.get("ruta");
		String nomProyecto = data.get("nomProyecto");

		/*
		 * Utilerias SQL y Sikulix
		 */
		SQLUtil dbConecta = new SQLUtil(GlobalVariables.DB_HOST_FCRD71CU, GlobalVariables.DB_USER_FCRD71CU,
				GlobalVariables.DB_PASSWORD_FCRD71CU);
		CambiosMasivosQueries cq = new CambiosMasivosQueries(testCase);
		/*
		 * PASOS DEL CASO DE PRUEBA
		 */

		// Paso 1
		String paso1 = "Ingresa a la base de datos";
		addStep(paso1);
		logger.info(paso1);
		assertTrue(dbConecta.getConn().isValid(10));
		testCase.addTextEvidenceCurrentStep("Conectarse a: " + GlobalVariables.DB_HOST_FCRD71CU);

		// Paso 2
		String paso2 = "Buscar los datos referentes a la ruta en la tabla de respaldo de tienda";
		addStep(paso2);
		logger.info(paso2);
		testCase.addTextEvidenceCurrentStep("Nombre del respaldo: " + nomProyecto);
		testCase.addTextEvidenceCurrentStep("Nombre de la ruta: " + ruta);
		testCase.addBoldTextEvidenceCurrentStep("Tabla PMO TIENDA BKP");
		SQLResult pmoTiendaBkp = executeQuery(dbConecta, cq.pmoTiendaBkp(nomProyecto));
		assertFalse(pmoTiendaBkp.isEmpty(), "No hay Resultados");
		cq.pmoTiendaBkpQueries(dbConecta, nomProyecto, false);

		// Paso 3
		String paso3 = "Buscar los datos referentes a la ruta en la tabla de tienda";
		addStep(paso3);
		logger.info(paso3);
		SQLResult pmoTienda = executeQuery(dbConecta, cq.pmoTienda(ruta));
		assertFalse(pmoTienda.isEmpty(), "No hay Resultados");
		testCase.addTextEvidenceCurrentStep("Nombre de la ruta: " + ruta);
		testCase.addBoldTextEvidenceCurrentStep("Tabla PMO TIENDA");
		cq.pmoTiendaQueries(dbConecta, ruta, false);

		// Paso 4
		String paso4 = "Valida que la información que estaba contenida en la tabla de respaldo coincida con la tabla de tienda";
		addStep(paso4);
		logger.info(paso4);

		SQLResult c = executeQuery(dbConecta, cq.pmoTiendaCompare(nomProyecto, ruta));
		assertTrue(c.isEmpty());
		testCase.addTextEvidenceCurrentStep("Exito, todos los registros de PMO TIENDA BKP estan en PMO TIENDA");
		dbConecta.close();
	}

	@Override
	public void beforeTest() {

	}

	@Override
	public String setPrerequisites() {
		return "1.- Edge\n" + "2.- Tener acceso a la aplicación\n" + "3.- Restaurar un respaldo\n"
				+ "4.- Tener acceso a la base de datos FCRD71CU (RDM_TLAJOULCO)\n"
				+ "5.- Tener acceso a las tablas XXFC_PMO_ROUTE, XXFC_PMO_TIENDA,  XXFC_PMO_ROUTE_BKP, XXFC_PMO_TIENDA_BKP\n";
	}

	@Override
	public String setTestDescription() {
		return "Verificar los cambios después de una recuperación, los datos guardados en la tabla XXFC_PMO_TIENDA_BKP que pasan a la XXFC_PMO_TIENDA";
	}

	@Override
	public String setTestDesigner() {
		return "JesusH4@hexaware.com";
	}

	@Override
	public String setTestFullName() {
		return null;
	}

	@Override
	public String setTestInstanceID() {
		return null;
	}

}
