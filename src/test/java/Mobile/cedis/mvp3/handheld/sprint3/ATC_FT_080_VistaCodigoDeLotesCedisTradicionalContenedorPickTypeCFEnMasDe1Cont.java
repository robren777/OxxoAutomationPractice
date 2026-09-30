package Mobile.cedis.mvp3.handheld.sprint3;

import static org.testng.Assert.assertFalse;

import java.util.HashMap;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import Mobile.cedis.om.D01_Conexiones_BD;
import modelo.BaseExecution;
import utils.sql.SQLResult;
 

public class ATC_FT_080_VistaCodigoDeLotesCedisTradicionalContenedorPickTypeCFEnMasDe1Cont extends BaseExecution {
	@Test(dataProvider = "data-provider", enabled = true) 
	public void ATC_FT_080_VistaCodigoDeLotesCedisTradicionalContenedorPickTypeCFEnMasDe1Cont_test(HashMap<String, String> data) throws Exception {
		D01_Conexiones_BD RDM100 = new D01_Conexiones_BD(testCase, null, this);
		String queryCnt = "SELECT * FROM XXFC_VERIFICACION_V WHERE TSF_NO = '"+data.get("numTransf")+"'";
		String queryPTp1 = "SELECT TSF_NO, DEST_ID, CONTAINER_ID, ITEM_ID "
		+ "FROM XXFC_VERIFICACION_V WHERE TSF_NO = '"+data.get("numTransf")+"' AND PICK_TYPE = '"+data.get("pickType")+"'";
		String queryPTp2 = "SELECT PICK_TYPE, CASEPACK, INNERPACK, UNIT_QTY, VERIF_CODE "
		+ "FROM XXFC_VERIFICACION_V WHERE TSF_NO = '"+data.get("numTransf")+"' AND PICK_TYPE = '"+data.get("pickType")+"'";
		 
	//SCRIPT DEL CASO DE PRUEBA//
		
	/* Paso 1 Se conecta correctamente a la base de datos mediante SQL Developer
	* Resultado: ?Se valida la conexión exitosa.
	*/
	addStep("Se conecta correctamente a la base de datos mediante SQL Developer"
				+ "Resultado: ?Se valida la conexión exitosa.");
    RDM100.verificarConexionRDM100();
		
	/* Paso 2 Se da doble clic en la conexión de RDM MTY y abre una pestaña para ejecutar consultas
    * Resultado: ?Se abre una pestaña en blanco para ejecutar consultas.
	*/	

    
	/* Paso 3 Se ingresa la siguiente consulta: SELECT * FROM XXFC_VERIFICACION_V WHERE TSF_NO = #Transfer
    * Resultado: ?Se ejecutó el query exitosamente y se despliegan la vista correctamente con la información requerida
	*/	
    addStep("Se ingresa la siguiente consulta: SELECT * FROM XXFC_VERIFICACION_V WHERE TSF_NO = #Transfer");			
	SQLResult consultaCnt = executeQuery(RDM100.connectionRDM100, queryCnt);
	SQLResult consultaPTp1 = executeQuery(RDM100.connectionRDM100, queryPTp1);

	
	/* Paso 4  Dentro de la consulta busca el artículo clasificado en mas de 1 contenedor, verifica que la información que tiene corresponda a su tipo de Pick_Type (CF o U)
    *Resultado: ?Se muestra la información correctamente.
	Aún no arroja campos en BD con valores CF para automatizado. 
	
	1. SELECT ITEM_ID, TSF_NO, COUNT(DISTINCT CONTAINER_ID) AS num_contenedores
	FROM XXFC_VERIFICACION_V -- Reemplaza "tu_tabla" por el nombre de tu tabla real
	WHERE PICK_TYPE = 'CF'
	GROUP BY ITEM_ID, TSF_NO
	HAVING COUNT(DISTINCT CONTAINER_ID) > 1;
	
	2. SELECT * FROM XXFC_VERIFICACION_V WHERE TSF_NO = 3120616175
    */
	
	addStep("Dentro de la consulta busca el artículo clasificado en mas de 1 contenedor, verifica que la información que tiene corresponda a su tipo de Pick_Type (CF o U)");
	SQLResult consultaPTp2 = executeQuery(RDM100.connectionRDM100, queryPTp2);
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
	RDM100.cerrarConexionRDM100();
	
	}
	@Override
	public String setTestFullName() {
		return "ATC_FT_080_VistaCodigoDeLotesCedisTradicionalContenedorPickTypeCFEnMasDe1Cont";
	}
	
	@Override
	public String setTestDescription() {
		return "Validar que en la consulta el artículo que se surte por mas de 1 contenedor contenga la información correspondiente a su tipo de pickeo";
	}
	
	@Override
	public String setTestDesigner() {
		return "Ricardo Hernandez Rios";
	}
	
	@Override
	public String setTestInstanceID() {
		return null;
	}
	
	@Override 
	public String setPrerequisites() {
		return "1.Contar con VPN\r\n"
				+ "*Contar con la BD RDM MTY\r\n"
				+ "*Contar con el query de la vista\r\n"
				+ "*Tener la vista disponible para la BD RDM MTY";
	}
	
	@BeforeTest
	public void beforeTest() {
	}	
	
}
