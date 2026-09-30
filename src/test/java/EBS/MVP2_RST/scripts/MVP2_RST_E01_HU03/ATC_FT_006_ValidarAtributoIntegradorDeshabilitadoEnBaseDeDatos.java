package EBS.MVP2_RST.scripts.MVP2_RST_E01_HU03;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashMap;

import org.testng.annotations.Test;

import EBS.MVP2_RST.om.mvp2ErstEBS;
import EBS.util.GlobalVariables;
import modelo.BaseExecution;
import utils.sql.SQLResult;
import utils.sql.SQLUtil;


	/**
	 * DESCRIPCION:  Validar el atributo "Integrador" en consulta de proveedores, que permitirá identificar el tipo de comisión por socio 
	 * 
	 * NOMBRE CASOS DE PRUEBA MANUAL QUE SE ABARCAN:
	 * - MTC_FT_006_ValidarAtributoIntegradorDeshabilitadoEnBaseDeDatos
	 *
	 * PREREQUISITOS:
	 * - Tener acceso a plataforma EBS y base de datos. 
	 * -Tener configurado el campo "Integrador" como verdadero en los atributos del proveedor previamente desde la plataforma EBS 
	 *
	 * INSUMOS:
	 * - N/A
	 *
	 * @author Mariana Avitia
	 * @date 27/Marzo/2025
	 */
	public class ATC_FT_006_ValidarAtributoIntegradorDeshabilitadoEnBaseDeDatos extends BaseExecution {
		
		@Test(dataProvider = "data-provider")
		
		public void ATC_FT_006_ValidarAtributoIntegradorDeshabilitadoEnBaseDeDatos_test(
				HashMap<String, String> data) throws Exception{
			
			//Utileria
			mvp2ErstEBS rst = new mvp2ErstEBS(null, testCase);
			SQLUtil dbEBS = new SQLUtil(GlobalVariables.DB_HOST_Ebs, GlobalVariables.DB_USER_Ebs, GlobalVariables.DB_PASSWORD_Ebs);
			
			//Variables
			String query = "SELECT integrador FROM xxfc.XXFC_PROVEEDORES_FORMAS_PAGO WHERE forma_pago_id = 'PYIO24'";
			String columnName = data.get("columnName");
			String estadoIntegrador = data.get("estadoIntegrador");
			System.out.println(
					"*****************************************Validar estado Integrador Habilitado ************************************************");
			
				addStep("Ingresar a la base de datos FCEBSD y Realizar consulta  SELECT * FROM \"APPS\".\"XXFC_PROVEEDORES_FORMAS_PAGO\" \r\n"
						+ "ORDER BY creation_date desc");
				SQLResult resultado = executeQuery(dbEBS, query);
				testCase.addQueryEvidenceCurrentStep(resultado);
				String resultadostring = resultado.getData(0, "INTEGRADOR").toString();
				System.out.println( resultadostring);
				addStep("Validar campo \"Integrador\" tenga en sus valores  una \"N\" de habilitado");
				assertTrue(rst.estadoIntegradorDeshabilitado(resultado, columnName, estadoIntegrador), "No se encontro el estado integrador con valor N para el socio");		
					
		}
		
		@Override
		public void beforeTest() {
			// TODO Auto-generated method stub
			
		}

		@Override
		public String setPrerequisites() {
			// TODO Auto-generated method stub
			return "Tener acceso a plataforma EBS y base de datos. "
					+ "Tener configurado el campo \"Integrador\" como deshabilitado en los atributos del proveedor previamente desde la plataforma EBS ";
		}

		@Override
		public String setTestDescription() {
			// TODO Auto-generated method stub
			return "Validar que los cambios se hayan aplicado y el campo integrador se muestre desabilitado en base de datos";
		}

		@Override
		public String setTestDesigner() {
			// TODO Auto-generated method stub
			return "Mariana Gabriela Avitia";
		}

		@Override
		public String setTestFullName() {
			// TODO Auto-generated method stub
			return "ATC_FT_006_ValidarAtributoIntegradorDeshabilitadoEnBaseDeDatos_test";
		}

		@Override
		public String setTestInstanceID() {
			// TODO Auto-generated method stub
			return null;
		}
		
}



