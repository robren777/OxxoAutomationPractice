package Mobile.cedis.mvp3.handheld.sprint3;

import java.util.HashMap;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import Mobile.cedis.om.D01_Conexiones_BD;
import modelo.BaseExecution;
import utils.sql.SQLResult;

public class ATC_FT_078_VistaCodigoDeLotesCedisAutomatizadoContenedorPickTypeU extends BaseExecution {

	@Test(dataProvider = "data-provider", enabled = true)
	public void ATC_FT_078_VistaCodigoDeLotesCedisAutomatizadoContenedorPickTypeU_test(HashMap<String, String> data) throws Exception {
		D01_Conexiones_BD POM_Conexiones = new D01_Conexiones_BD(testCase, null, this);

		//String query1 = "select * from rdm100.xxfc_pav_zone_v";
		String queryCnt = "SELECT * FROM XXFC_VERIFICACION_V WHERE TSF_NO = '"+data.get("numTransf")+"'";
		String queryPTp1 = "SELECT TSF_NO, DEST_ID, CONTAINER_ID, ITEM_ID "
		+ "FROM XXFC_VERIFICACION_V WHERE TSF_NO = '"+data.get("numTransf")+"' AND PICK_TYPE = '"+data.get("pickType")+"'";
		String queryPTp2 = "SELECT PICK_TYPE, CASEPACK, INNERPACK, UNIT_QTY, VERIF_CODE "
		+ "FROM XXFC_VERIFICACION_V WHERE TSF_NO = '"+data.get("numTransf")+"' AND PICK_TYPE = '"+data.get("pickType")+"'";
		
		//SCRIPT DEL CASO DE PRUEBA//
		
		/* Paso 1 Se conecta correctamente a la base de datos mediante SQL Developer
		/* Resultado: Se valida la conexión exitosa
		*/
	    addStep("Se conecta correctamente a la base de datos mediante SQL Developer" + "Resultado: ?Se valida la conexión exitosa.");
		POM_Conexiones.verificarConexionRDM100();		

	    /* Paso 2 Se da doble clic en la conexión de RDM Tlajomulco y abre una pestaña para ejecutar consultas
		* Resultado: Se abre una pestaña en blanco para ejecutar consultas
	    */
			
		/* Paso 3 Se ingresa la siguiente consulta: SELECT * FROM XXFC_VERIFICACION_V WHERE TSF_NO = #Transfer
        /* Resultado: Se ejecutó el query exitosamente y se despliegan la vista correctamente con la información requerida
	    */
	    addStep("Se ingresa la siguiente consulta: SELECT * FROM XXFC_VERIFICACION_V WHERE TSF_NO = #Transfer" + "Resultado: Se ejecutó el query exitosamente y se despliegan la vista correctamente con la información requerida");
		SQLResult consulta = executeQuery(POM_Conexiones.connectionTlajo, queryCnt);
		SQLResult consultaPTp1 = executeQuery(POM_Conexiones.connectionTlajo, queryPTp1);
		testCase.addQueryEvidenceCurrentStep(consulta);
		testCase.addQueryEvidenceCurrentStep(consultaPTp1);

	    
	    /* Paso 4 Dentro de la consulta busca un contenedor cuyo artículo tiene pick type "U", verifica que la información que arroja sea la del campo ITEM_MASTER_UDA12, utiliza el query: select * from ITEM_MASTER where ITEM_ID = '#SKU';
        /* Resultado: ?Arroja la información de ITEM_MASTER_UDA12 correctamente
	    */
	    addStep("Dentro de la consulta busca un contenedor cuyo artículo tiene pick type \"U\", verifica que la información que arroja sea la del campo ITEM_MASTER_UDA12, utiliza el query: select * from ITEM_MASTER where ITEM_ID = '#SKU'" + "Arroja la información de ITEM_MASTER_UDA12 correctamente");
		SQLResult Resultado_Consulta_SQL2_1 = executeQuery(POM_Conexiones.connectionTlajo, "SELECT \"DESCRIPTION\", ITEM_ID, ITEM_MASTER_UDA12 FROM ITEM_MASTER WHERE ITEM_ID = '706460000641'");		
		testCase.addQueryEvidenceCurrentStep(Resultado_Consulta_SQL2_1);
		//Finalizacion del Script y cierre de conexion
		POM_Conexiones.cerrarConexionRdmTlajo();	
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_078_VistaCodigoDeLotesCedisAutomatizadoContenedorPickTypeU";
	}
	
	@Override
	public String setTestDescription() {
		return "Validar que al consultar la vista de código de lotes la información que arroja un contenedor de tipo \"U\" (surtido por pieza) tenga la información del campo ITEM_MASTER_UDA12";
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
				+ "*Contar con la BD RDM TLAJOMULCO\r\n"
				+ "*Contar con el query de la vista\r\n"
				+ "*Tener la vista disponible para la BD RDM TLAJOMULCO.";
	}
	
	@BeforeTest
	public void beforeTest() {
	}
}
