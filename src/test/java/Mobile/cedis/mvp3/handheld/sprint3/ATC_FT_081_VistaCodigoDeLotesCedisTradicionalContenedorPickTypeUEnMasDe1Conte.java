package Mobile.cedis.mvp3.handheld.sprint3;

import static org.testng.Assert.assertFalse;

import java.util.HashMap;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import Mobile.cedis.om.D01_Conexiones_BD;
import modelo.BaseExecution;
import utils.sql.SQLResult;

public class ATC_FT_081_VistaCodigoDeLotesCedisTradicionalContenedorPickTypeUEnMasDe1Conte extends BaseExecution {
	@Test(dataProvider = "data-provider", enabled = true)
	public void ATC_FT_081_VistaCodigoDeLotesCedisTradicionalContenedorPickTypeUEnMasDe1Conte_test(HashMap<String, String> data) throws Exception {
 
	D01_Conexiones_BD rdmMTY = new D01_Conexiones_BD(testCase, null, this);
	String queryCnt = "SELECT * FROM XXFC_VERIFICACION_V WHERE TSF_NO = '"+data.get("numTransf")+"'";
	String queryPTp1 = "SELECT TSF_NO, DEST_ID, CONTAINER_ID, ITEM_ID "
			+ "FROM XXFC_VERIFICACION_V WHERE TSF_NO = '"+data.get("numTransf")+"' AND PICK_TYPE = '"+data.get("pickType")+"'";
	String queryPTp2 = "SELECT PICK_TYPE, CASEPACK, INNERPACK, UNIT_QTY, VERIF_CODE "
			+ "FROM XXFC_VERIFICACION_V WHERE TSF_NO = '"+data.get("numTransf")+"' AND PICK_TYPE = '"+data.get("pickType")+"'";

		//SCRIPT DEL CASO DE PRUEBA//
		
		//Paso #1: Se conecta correctamente a la base de datos mediante SQL Developer
		addStep("Se conecta correctamente a la base de datos mediante SQL Developer");
        
		//Paso #2: ?Se valida la conexión exitosa.
        addStep("Resultado Esperado: ? Se valida la conexión exitosa.");  
        rdmMTY.verificarConexionRdmMty();

		//Paso #3: Se da doble clic en la conexión de RDM MTY y abre una pestaña para ejecutar consultas
		
		
		//Paso #4: ?Se abre una pestaña en blanco para ejecutar consultas
	
				
		//Paso #5: Se ingresa la siguiente consulta: SELECT * FROM XXFC_VERIFICACION_V WHERE TSF_NO = #Transfer 
		addStep("Se ingresa la siguiente consulta: SELECT * FROM XXFC_VERIFICACION_V WHERE TSF_NO = #Transfer");			
		SQLResult consultaCnt = executeQuery(rdmMTY.connectionMTY, queryCnt);
		
		
		//Paso #6: "?Se ejecutó el query exitosamente y se despliegan la vista correctamente con la información requerida"
		addStep("Resultado Esperado: ?Se ejecutó el query exitosamente y se despliegan la vista correctamente con la información requerida");
		
		boolean validarconsultaCnt = consultaCnt.isEmpty();
		if (!validarconsultaCnt)
		{
			testCase.addTextEvidenceCurrentStep("La consulta encontró información de manera correcta.");
			System.out.println("La consulta encontró información de manera correcta.");
			testCase.addQueryEvidenceCurrentStep(consultaCnt);
		}
		assertFalse(validarconsultaCnt, "No se realizó la consulta de manera correcta.");

		//Paso #7: Dentro de la consulta busca el artículo clasificado en mas de 1 contenedor, verifica que la información que tiene corresponda a su tipo de Pick_Type (CF o U)
		addStep("Dentro de la consulta busca el artículo clasificado en mas de 1 contenedor, verifica que la información que tiene corresponda a su tipo de Pick_Type (CF o U)");
		SQLResult consultaPTp1 = executeQuery(rdmMTY.connectionMTY, queryPTp1);
		SQLResult consultaPTp2 = executeQuery(rdmMTY.connectionMTY, queryPTp2);
		
		//Paso #8: ?Se muestra la información correctamente
		addStep("Resultado Esperado: ?Se muestra la información correctamente");
		boolean validarconsultaPTp1 = consultaPTp1.isEmpty();
		if (!validarconsultaPTp1)
		{
			testCase.addTextEvidenceCurrentStep("La consulta encontró información de manera correcta.");
			System.out.println("La consulta encontró información de manera correcta.");
			testCase.addQueryEvidenceCurrentStep(consultaPTp1);
		}
		assertFalse(validarconsultaPTp1, "No se realizó la consulta de manera correcta.");
		
		boolean validarconsultaPTp2 = consultaPTp2.isEmpty();
		if (!validarconsultaPTp2)
		{
			testCase.addTextEvidenceCurrentStep("La consulta encontró información de manera correcta.");
			System.out.println("La consulta encontró información de manera correcta.");
			testCase.addQueryEvidenceCurrentStep(consultaPTp2);
		}
		assertFalse(validarconsultaPTp2, "No se realizó la consulta de manera correcta.");
		
		//CERRAR CONEXIÓN
		Thread.sleep(3000);
		rdmMTY.cerrarConexionRdmMty();
}
	 	
	@Override
	public String setTestFullName() {
		return "ATC_FT_081_VistaCodigoDeLotesCedisTradicionalContenedorPickTypeUEnMasDe1Contenedor";
	}
	
	@Override
	public String setTestDescription() {
		return "Validar que en la consulta el artículo que se surte por mas de 1 contenedor contenga la información correspondiente a su tipo de pickeo";
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
		return "*Contar con VPN\r\n"
				+ "*Contar con la BD RDM MTY\r\n"
				+ "*Contar con el query de la vista\r\n"
				+ "*Tener la vista disponible para la BD RDM MTY";
	}
	
	@BeforeTest
	public void beforeTest() {
	}
	
}