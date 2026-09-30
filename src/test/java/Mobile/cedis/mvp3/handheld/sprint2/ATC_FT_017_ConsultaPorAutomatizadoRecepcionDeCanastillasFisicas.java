package Mobile.cedis.mvp3.handheld.sprint2;

import static org.testng.Assert.assertFalse;

import java.util.HashMap;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import Mobile.cedis.om.D01_Conexiones_BD;
import modelo.BaseExecution;
import utils.sql.SQLResult;

public class ATC_FT_017_ConsultaPorAutomatizadoRecepcionDeCanastillasFisicas extends BaseExecution {
	@Test(dataProvider = "data-provider", enabled = true)
	public void ATC_FT_017_ConsultaPorAutomatizadoRecepcionDeCanastillasFisicas_test(HashMap<String, String> data) throws Exception {
 
		D01_Conexiones_BD rdmTlajo = new D01_Conexiones_BD(testCase, null, this);
		//String query1 = "select * from rdm100.xxfc_pav_zone_v LIMIT 10";
		//String query2 = "Select * from wmuser.xxfc_pav_zone_v where DISTRO_NBR = '"+data.get("numTrans")+"'";
		String parte1 = "select DISTRO_NBR, ZONE, DEST_ID, TIPO_CONTENEDOR, TIPO_SURT "
				+ "from rdm100.xxfc_pav_zone_v where distro_nbr = '"+data.get("numTransf")+"'";
		String parte2 = "select CONTAINER_ID, ITEM_ID, CONTAINER_QTY, GENERIC_CONTAINER_ID "
				+ "from rdm100.xxfc_pav_zone_v where distro_nbr = '"+data.get("numTransf")+"'";
		String parte3 = "select TAPA, CARRIT, CANAST, ENVASE "
				+ "from rdm100.xxfc_pav_zone_v where distro_nbr = '"+data.get("numTransf")+"'";
			//SCRIPT DEL CASO DE PRUEBA//
			
			//Paso #1: Ingresar a la BD de RDM_TLAJOULCO
			addStep("Ingresar a la BD de RDM_TLAJOULCO");
	        
			//Paso #2: ? Se tiene acceso a la BD automatizado con las crendeciales.
	        addStep("Resultado Esperado: ? Se tiene acceso a la BD automatizado con las crendeciales.");  
	        rdmTlajo.verificarConexionRdmTlajo();

			//Paso #3: Ejecutar la consulta: select * from rdm100.xxfc_pav_zone_v 
	
			//Paso #4: ? La consulta arroja información en la tabla seleccionada. 

		
			//Paso #5: Ejecutar la consulta: Select * from wmuser.xxfc_pav_zone_v WHERE DISTRO_NBR = '#transferencia'; 
	        addStep("Ejecutar la consulta: Select * from wmuser.xxfc_pav_zone_v WHERE DISTRO_NBR = '#transferencia'");			
			SQLResult consultap1 = executeQuery(rdmTlajo.connectionTlajo, parte1);
			SQLResult consultap2 = executeQuery(rdmTlajo.connectionTlajo, parte2);
			SQLResult consultap3 = executeQuery(rdmTlajo.connectionTlajo, parte3);
			
			//Paso #6: "? La consulta arroja información del numero de la transferencia colocada. Campos nuevos; *Canast"
			addStep("Resultado Esperado: ? La consulta arroja información del numero de la transferencia colocada. Campos nuevos: *Canast");
			boolean validarconsultap1 = consultap1.isEmpty();
			if (!validarconsultap1)
			{
				testCase.addTextEvidenceCurrentStep("La consulta encontró información de manera correcta.");
				System.out.println("La consulta encontró información de manera correcta.");
				testCase.addQueryEvidenceCurrentStep(consultap1);
			}
			assertFalse(validarconsultap1, "No se realizó la consulta de manera correcta");
			
			
			boolean validarconsultap2 = consultap2.isEmpty();
			if (!validarconsultap2)
			{
				testCase.addTextEvidenceCurrentStep("La consulta encontró información de manera correcta.");
				System.out.println("La consulta encontró información de manera correcta.");
				testCase.addQueryEvidenceCurrentStep(consultap2);
			}
			assertFalse(validarconsultap2, "No se realizó la consulta de manera correcta");
			
			
			boolean validarconsultap3 = consultap3.isEmpty();
			if (!validarconsultap3)
			{
				testCase.addTextEvidenceCurrentStep("La consulta encontró información de manera correcta.");
				System.out.println("La consulta encontró información de manera correcta.");
				testCase.addQueryEvidenceCurrentStep(consultap3);
			}
			assertFalse(validarconsultap3, "No se realizó la consulta de manera correcta");
			
			Thread.sleep(3000);
			rdmTlajo.cerrarConexionRdmTlajo();
	}
	 	
	@Override
	public String setTestFullName() {
		return "ATC_FT_017_ConsultaPorAutomatizadoRecepcionDeCanastillasFisicas";
	}
	
	@Override
	public String setTestDescription() {
		return "Validar que al momento de realizar la consulta de un bulto tipo canastilla se almacene el SKU correspondiente de esta misma.";
	}
	
	@Override
	public String setTestDesigner() {
		return "Jorge Alberto Lugo Martinez";
	}
	
	@Override
	public String setTestInstanceID() {
		return null;
	}
	
	@Override 
	public String setPrerequisites() {
		return "1.Usuario y Contraseña de Aplicación conexión a BD rdm. \r\n"
				+ "Nombre	RDM_TLAJOULCO\r\n"
				+ "Usuario	6006118\r\n"
				+ "Contraseña	Comercio1124061*\r\n"
				+ "Host	10.180.49.87\r\n"
				+ "Puerto	1535\r\n"
				+ "SID	FCRD71CU\r\n"
				+ "2.  Numero de la transferencia a utilizar\r\n"
				+ "3. Pedido contenga bultos tipo canastilla";
	}
	
	@BeforeTest
	public void beforeTest() {
	}
	
}