package Mobile.cedis.mvp3.handheld.sprint3;

import static org.testng.Assert.assertFalse;

import java.util.HashMap;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import Mobile.cedis.om.D01_Conexiones_BD;
import modelo.BaseExecution;
import utils.sql.SQLResult;

public class ATC_FT_077_VistaCodigoDeLotesCedisAutomatizadoContenedorPickTypeCF extends BaseExecution {
	
	@Test(dataProvider = "data-provider", enabled = true)
	
	public void ATC_FT_077_VistaCodigoDeLotesCedisAutomatizadoContenedorPickTypeCF_test(HashMap<String, String> data) throws Exception {
		D01_Conexiones_BD POM_Conexiones = new D01_Conexiones_BD(testCase, null, this);

		//SCRIPT DEL CASO DE PRUEBA//
		String queryCnt = "SELECT * FROM XXFC_VERIFICACION_V WHERE TSF_NO = '"+data.get("numTransf")+"'";
		String queryCF = "SELECT * FROM ITEM_MASTER where ITEM_ID = '"+ "7501064101410'";

		/* Paso 1 Se conecta correctamente a la base de datos mediante SQL Developer
        /* Resultado: Se valida la conexión exitosa
		*/
		addStep("Se conecta correctamente a la base de datos mediante SQL Developer" + "Resultado: ?Se valida la conexión exitosa.");
		POM_Conexiones.verificarConexionRDM100();
		

		/* Paso 2 Se da doble clic en la conexión de RDM Tlajomulco y abre una pestaña para ejecutar consultas
        /* Resultado: Se abre una pestaña en blanco para ejecutar consultas
		*/
		
		/* Paso 3 Se ingresa la siguiente consulta: SELECT * FROM XXFC_VERIFICACION_V WHERE TSF_NO = #Transfer
        /* Resultado: Se ejecutó el query exitosamente y se despliegan la vista correctamente con la información requerida
	    */	
		addStep("Se ingresa la siguiente consulta: SELECT * FROM XXFC_VERIFICACION_V WHERE TSF_NO = #Transfer" + "Resultado: Se ejecutó el query exitosamente y se despliegan la vista correctamente con la información requerida");
		SQLResult consulta = executeQuery(POM_Conexiones.connectionTlajo, queryCnt);
		assertFalse(consulta.isEmpty(), "No arroja información de ITEM_MASTER correctamente");
		testCase.addQueryEvidenceCurrentStep(consulta);

		
		
		/* Paso 4 Dentro de la consulta busca un contenedor cuyo artículo tiene pick type "CF", verifica que la información que arroja sea la del campo ITEM_MASTER_UDA11, utiliza el query: select * from ITEM_MASTER where ITEM_ID = '#SKU';
        /* Resultado: Arroja la información de ITEM_MASTER_UDA11 correctamente
	    */	
		addStep("Dentro de la consulta busca un contenedor cuyo artículo tiene pick type CF, verifica que la información que arroja sea la del campo ITEM_MASTER_UDA11, utiliza el query: select * from ITEM_MASTER where ITEM_ID = '#SKU'");	
		SQLResult consulta2 = executeQuery(POM_Conexiones.connectionTlajo, "SELECT \"DESCRIPTION\", ITEM_ID, ITEM_MASTER_UDA11 FROM ITEM_MASTER WHERE ITEM_ID = '7501064101410'");		
		assertFalse(consulta2.isEmpty(), "No arroja información de ITEM_ID correctamente");
		testCase.addQueryEvidenceCurrentStep(consulta2);
	
	}
	
	
	@Override
	public String setTestFullName() {
		return "ATC_FT_077_VistaCodigoDeLotesCedisAutomatizadoContenedorPickTypeCF";
	}
	
	@Override
	public String setTestDescription() {
		return "Validar que al consultar la vista de código de lotes la información que arroja un contenedor de tipo \"CF\" (surtido como cajas) tenga la información del campo ITEM_MASTER_UDA11";
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
				+ "*Tener la vista disponible para la BD RDM TLAJOMULCO";
	}
	
	@BeforeTest
	public void beforeTest() {
	}
}

