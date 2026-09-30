package Mobile.cedis.mvp3.handheld.sprint2;

import static org.testng.Assert.assertFalse;

import java.util.HashMap;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import Mobile.cedis.om.D01_Conexiones_BD;
import modelo.BaseExecution;
import utils.sql.SQLResult;

public class ATC_FT_018_ConsultaPorTradicionalRecepcionDeCanastillasFisicas extends BaseExecution {
	@Test(dataProvider = "data-provider", enabled = true)
	public void ATC_FT_018_ConsultaPorTradicionalRecepcionDeCanastillasFisicas_test(HashMap<String, String> data) throws Exception {
 
	D01_Conexiones_BD rdmMTY = new D01_Conexiones_BD(testCase, null, this);
	//String query1 = "select * from rdm100.xxfc_pav_zone_v";
	//String query2 = "Select * from wmuser.xxfc_pav_zone_v where DISTRO_NBR = '"+data.get("numTransf")+"'";
	String parte1 = "select DISTRO_NBR, ZONE, DEST_ID, TIPO_CONTENEDOR, TIPO_SURT "
			+ "from rdm100.xxfc_pav_zone_v where distro_nbr = '"+data.get("numTransf")+"'";
	String parte2 = "select CONTAINER_ID, ITEM_ID, CONTAINER_QTY, GENERIC_CONTAINER_ID "
			+ "from rdm100.xxfc_pav_zone_v where distro_nbr = '"+data.get("numTransf")+"'";
	String parte3 = "select TAPA, CARRIT, CANAST, ENVASE "
			+ "from rdm100.xxfc_pav_zone_v where distro_nbr = '"+data.get("numTransf")+"'";

		//SCRIPT DEL CASO DE PRUEBA//
		
		//Paso #1: Ingresar a la BD de RDMQA-MTY
		addStep("Ingresar a la BD de RDMQA-MTY");
        
		//Paso #2: ? Se tiene acceso a la BD tradicional con las crendeciales.
        addStep("Resultado Esperado: ? Se tiene acceso a la BD tradicional con las crendeciales.");  
        rdmMTY.verificarConexionRdmMty();

		//Paso #3: Ejecutar la consulta: select * from rdm100.xxfc_pav_zone_v 
		/*addStep("Ejecutar la consulta: select * from rdm100.xxfc_pav_zone_v");	
		SQLResult consulta1 = executeQuery(rdmMTY.connectionMTY, query1);*/
		
		//Paso #4: ? La consulta arroja información en la tabla seleccionada. 
		//addStep("Resultado Esperado: ? La consulta arroja información en la tabla seleccionada. ");
		//SQLResult consulta1 = executeQuery(D01_Conexiones_BD.connectionMTY, query1);  
		/*boolean validarconsulta1 = consulta1.isEmpty();
		if (!validarconsulta1)
		{
			testCase.addTextEvidenceCurrentStep("La consulta encontró información de manera correcta");
		}
		testCase.addQueryEvidenceCurrentStep(consulta1);
		assertFalse(validarconsulta1, "No se realizó la consulta de manera correcta");*/
				
		//Paso #5: Ejecutar la consulta: Select * from wmuser.xxfc_pav_zone_v WHERE DISTRO_NBR = '#transferencia'; 
		addStep("Ejecutar la consulta: Select * from wmuser.xxfc_pav_zone_v WHERE DISTRO_NBR = '#transferencia';");			
		//SQLResult consulta2 = executeQuery(rdmMTY.connectionMTY, query2);
		SQLResult consultap1 = executeQuery(rdmMTY.connectionMTY, parte1);
		SQLResult consultap2 = executeQuery(rdmMTY.connectionMTY, parte2);
		SQLResult consultap3 = executeQuery(rdmMTY.connectionMTY, parte3);
		
		//Paso #6: "? La consulta arroja información del numero de la transferencia colocada. Campos nuevos; *Canast"
		addStep("Resultado Esperado: ? La consulta arroja información del numero de la transferencia colocada. Campos nuevos: *Canast");
		/*boolean validarconsulta2 = consulta2.isEmpty();
		if (!validarconsulta2)
		{
			testCase.addTextEvidenceCurrentStep("La consulta encontró información de manera correcta.");
			System.out.println("La consulta encontró información de manera correcta.");
		}
		assertFalse(validarconsulta2, "No se realizó la consulta de manera correcta");*/
		
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
		
		
		//CERRAR CONEXIÓN
		Thread.sleep(3000);
		rdmMTY.cerrarConexionRdmMty();
}
	 	
	@Override
	public String setTestFullName() {
		return "ATC_FT_018_ConsultaPorTradicionalRecepcionDeCanastillasFisicas";
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
				+ "Nombre	RDMQA-MTY\r\n"
				+ "Usuario	RDMVIEW\r\n"
				+ "Contraseña	rdmview01*\r\n"
				+ "Host	fcrdmq01.femcom.net\r\n"
				+ "Puerto	1535\r\n"
				+ "SID	FCRD01CU.\r\n"
				+ "2.  Numero de la transferencia a utilizar\r\n"
				+ "3. Pedido contenga bultos tipo canastilla";
	}
	
	@BeforeTest
	public void beforeTest() {
	}
	
}