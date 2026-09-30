package EBS.mvp1_FusionDeEstados_H004;

import static org.testng.Assert.assertTrue;

import java.util.HashMap;

import org.testng.annotations.Test;

import EBS.util.GlobalVariables;
import modelo.BaseExecution;


/**
* DESCRIPCIÓN: Validar que se muestren correctamente los campos en la vista que se genera para visualizar la información de la configuración de clusters en value set"
				El layout de la vista contará con las siguientes columnas"
				CR Cluster ID (CR_CLUSTER_ID)
				EF Cedis ID (EF_CEDIS_ID)
				CR Cluster (CR_CLUSTER)
				Descripción de Cluster (CR_DESC_CLUSTER)
				CR CEDIS (CR_CEDIS)
				Descripción de CEDIS (CR_DESC_CEDIS)
*
* NOMBRE CASOS DE PRUEBA MANUAL QUE SE ABARCAN:
*     -ATC_FT_006_ValidarCamposDeLaVista_XXFC_CR_CEDIS_CLUSTERS_V
*  
* PREREQUISITOS:
*				 1.- Tener acceso a aplicacion de QA EBS Mexico. (AVEBQA)
*				 2.- Tener acceso a BD QA EBS Mexico. (AVEBQA)
*				 3.- Tener acceso a Responsabilidad MXOXXO - GLXXXXX - Administrador CEDIS Cluster.
*
* INSUMOS (datos o información que necesita estar preparada o generada para poder ejecutar el script automatizado):
*     -Query de busqueda 1 EBS
*     -Query de busqueda 2 EBS
*     -Query de busqueda comprobador EBS
* @author Jesus Alberto Moreno Delgado
* @date 2/09/2024
*/
public class ATC_FT_006_ValidarCamposDeLaVista_XXFC_CR_CEDIS_CLUSTERS_V extends BaseExecution {

	@Test(dataProvider = "data-provider")
	public void ATC_FT_006_ValidarCamposDeLaVista_XXFC_CR_CEDIS_CLUSTERS_V_test(HashMap<String, String> data)
			throws Exception {

		testCase.setProject_Name("Fusión Estados de Resultados.");
		// Inicializar las variables trayendo la información del Data Provider.
		String queryEBS1 = data.get("ebs1");
		String queryEBS2 = data.get("ebs2");
		String queryComprobador = data.get("query_comprobador");

		// Validar que ningún campo del DataProvider venga como nulo.
		boolean flag = false;
		if (!queryComprobador.isEmpty() || !queryEBS1.isEmpty() || !queryEBS2.isEmpty()) {
			flag = true;
		}
		assertTrue(flag, "Los registros en el Data Provider están completos");

		// Paso 1.
		addStep("Ingresar a la BD de EBS México (AVEBQA)");

		utils.sql.SQLUtil dbEBS = new utils.sql.SQLUtil(GlobalVariables.DB_HOST_AVEBQA, GlobalVariables.DB_USER_AVEBQA,
				GlobalVariables.DB_PASSWORD_AVEBQA);
		utils.sql.SQLResult resultadoContenedor = dbEBS.executeQuery(queryComprobador);
		testCase.addQueryEvidenceCurrentStep(resultadoContenedor);
		flag = false;
		if (!resultadoContenedor.isEmpty()) {
			flag = true;
			assertTrue(flag, "El registro existe en la BD");
		} else {
			assertTrue(flag, "El registro no existe en la BD");
		}

		// Paso 2.
		addStep("Validar que se encuentre creada la vista XXFC_CR_CEDIS_CLUSTERS_V.");
		resultadoContenedor = dbEBS.executeQuery(queryEBS1);
		testCase.addQueryEvidenceCurrentStep(resultadoContenedor);
		flag = false;
		if (!resultadoContenedor.isEmpty()) {
			flag = true;
			assertTrue(flag, "El registro existe en la BD");
		} else {
			assertTrue(flag, "El registro no existe en la BD");
		}

		// Paso 3.
		addStep("Validar que la vista XXFC_CR_CEDIS_CLUSTERS_V contenga los siguientes campos:\r\n"
				+ "- CR Cluster ID (CR_CLUSTER_ID)\r\n"
				+ "- EF Cedis ID (EF_CEDIS_ID)\r\n"
				+ "- CR Cluster (CR_CLUSTER)\r\n"
				+ "- Descripción de Cluster (CR_DESC_CLUSTER)\r\n"
				+ "- CR CEDIS (CR_CEDIS)\r\n"
				+ "- Descripción de CEDIS (CR_DESC_CEDIS)");

		resultadoContenedor = dbEBS.executeQuery(queryEBS2);
		testCase.addQueryEvidenceCurrentStep(resultadoContenedor);
		flag = false;
		if (!resultadoContenedor.isEmpty()) {
			flag = true;
			assertTrue(flag, "El registro existe en la BD");
		} else {
			assertTrue(flag, "El registro no existe en la BD");
		}
	}

	@Override
	public void beforeTest() {
		// TODO Auto-generated method stub

	}

	@Override
	public String setPrerequisites() {
		// TODO Auto-generated method stub
		return "\"1.- Tener acceso a aplicación de QA EBS México. (AVEBQA)\r\n"
				+ "2.- Tener acceso a BD QA EBS México. (AVEBQA)\r\n"
				+ "3.- Tener acceso a Responsabilidad MXOXXO - GLXXXXX - Administrador CEDIS Cluster.\"\r\n"
				+ "";
	}

	@Override
	public String setTestDescription() {
		// TODO Auto-generated method stub
		return "Validar que se muestren correctamente los campos en la vista que se genera para visualizar la información de la configuración de clusters en value set.\r\n"
				+ "El layout de la vista contará con las siguientes columnas:\r\n"
				+ "- CR Cluster ID (CR_CLUSTER_ID)\r\n"
				+ "- EF Cedis ID (EF_CEDIS_ID)\r\n"
				+ "- CR Cluster (CR_CLUSTER)\r\n"
				+ "- Descripción de Cluster (CR_DESC_CLUSTER)\r\n"
				+ "- CR CEDIS (CR_CEDIS)\r\n"
				+ "- Descripción de CEDIS (CR_DESC_CEDIS)";
	}

	@Override
	public String setTestDesigner() {
		// TODO Auto-generated method stub
		return "JesusM3@hexaware.com";
	}

	@Override
	public String setTestFullName() {
		// TODO Auto-generated method stub
		return "ATC_FT_006_ValidarCamposDeLaVista_XXFC_CR_CEDIS_CLUSTERS_V_test";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}

}
