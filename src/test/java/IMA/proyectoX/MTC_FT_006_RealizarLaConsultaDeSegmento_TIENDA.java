package IMA.proyectoX;

import java.util.HashMap;

import org.testng.annotations.Test;

import IMA.util.GlobalVariables;
import IMA.util.IMATools;
import modelo.BaseExecution;
import utils.sql.SQLResult;

public class MTC_FT_006_RealizarLaConsultaDeSegmento_TIENDA extends BaseExecution {
	@Test(dataProvider = "data-provider")
	public void MTC_FT_006_RealizarLaConsultaDeSegmento_TIENDA_test(
			HashMap<String, String> data) throws Exception {

		/**
		 * En este caso de prueba no se utiliza el comando DESC para describir el tipo
		 * de campo, solo se valida que existan las columnas. Se limitan los registros a
		 * 50 para limitar los tiempos de carga en la ejecucion.
		 */

		IMATools ima = new IMATools(null, testCase);
		String query;
		SQLResult connectionResult;

		utils.sql.SQLUtil DB = new utils.sql.SQLUtil(GlobalVariables.DB_HOST_FCIASQAS2, GlobalVariables.DB_USER_FCIASQAS2,
				GlobalVariables.DB_PASSWORD_FCIASQAS2);
		//Paso 1
		query = "SELECT potencial_id, 'Potencial ' || necesidad_desarrollo_desc AS POTENCIAL, potencial_desc POTENCIAL_VALOR\r\n" + 
				"            , 'Overlay ' || necesidad_desarrollo_desc AS OVERLAY, nivel_overlay_id, nivel_overlay_desc AS OVERLAY_VALOR\r\n" + 
				"            , necesidad_desarrollo_id, necesidad_desarrollo_desc\r\n" + 
				"            FROM TIENDAS.T_FORMATO_SEGMENTO_DET \r\n" + 
				"            WHERE ID_TIENDA = '121432'";
		addStep("Se valida la conexion a la base de datos");
		connectionResult = executeQuery(DB, query);
		ima.validarConexion(connectionResult);
		
		//Paso2
		addStep("Se valida que la consulta regrese datos");
		ima.validarContenidoTablas(connectionResult, query);

		testCase.addQueryEvidenceCurrentStep(connectionResult);

	}

	@Override
	public void beforeTest() {
		// TODO Auto-generated method stub

	}

	@Override
	public String setTestDescription() {
		// TODO Auto-generated method stub
		return "Validar consulta en base de datos";
	}

	@Override
	public String setTestDesigner() {
		// TODO Auto-generated method stub
		return "EQUIPO AUTOMATIZACION";
	}

	@Override
	public String setTestFullName() {
		// TODO Auto-generated method stub
		return "MTC_FT_006_RealizarLaConsultaDeSegmento_TIENDA_test";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String setPrerequisites() {
		// TODO Auto-generated method stub
		return null;
	}

}
