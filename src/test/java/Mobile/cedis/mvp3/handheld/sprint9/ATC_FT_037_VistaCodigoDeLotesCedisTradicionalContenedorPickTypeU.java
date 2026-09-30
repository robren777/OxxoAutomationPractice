package Mobile.cedis.mvp3.handheld.sprint9;

import static org.testng.Assert.assertFalse;

import java.util.HashMap;

import org.testng.annotations.Test;

/**
import Mobile.cedis.om.A01_Login_Punto_De_Venta;
import Mobile.cedis.om.A02_Login_Lider;
import Mobile.cedis.om.A03_Menus_y_submenus;
import Mobile.cedis.om.A04_MenuHerramientas;
import Mobile.cedis.om.C01_Listado_de_transferencias_a_verificar;
import Mobile.cedis.om.C02_Verificacion_De_Pedido_Y_Permisos;
*/
import Mobile.cedis.om.D01_Conexiones_BD;
import modelo.BaseExecution;
import utils.sql.SQLResult;

public class ATC_FT_037_VistaCodigoDeLotesCedisTradicionalContenedorPickTypeU extends BaseExecution {
	@Test(dataProvider = "data-provider", enabled = true)
	public void ATC_FT_037_VistaCodigoDeLotesCedisTradicionalContenedorPickTypeU_test(
			HashMap<String, String> data) throws Exception {

		D01_Conexiones_BD POM_Conexiones = new D01_Conexiones_BD(testCase, null, this);
		//Pasos relacionados a crear la conexion con la base de datos de RDM Tlajomulco
		addStep("Se conecta correctamente a la base de datos mediante SQL Developer", "Validar la conexión exitosa");
		POM_Conexiones.verificarConexionRdmNubev11();

		//Pasos para hacer la consulta y validar que se encuentren las columnas de: tipo de distribucion, ruta original y ruta dinamica
		addStep("Estando en la BD RDM MTY ingresa la siguiente consulta: SELECT * FROM XXFC_VERIFICACION_V WHERE TSF_NO = #Transfer", "Se ejecuto el query exitosamente y se despliegan la vista correctamente con la informacion requerida");
		SQLResult Resultado_Consulta_SQL = executeQuery(POM_Conexiones.connectionRDMv11, "SELECT TSF_NO, DEST_ID, CONTAINER_ID, ITEM_ID, PICK_TYPE, VERIF_CODE FROM XXFC_VERIFICACION_V WHERE TSF_NO = '3155177806' AND PICK_TYPE = 'U' AND ROWNUM <= 1");
		testCase.addQueryEvidenceCurrentStep(Resultado_Consulta_SQL);
		assertFalse(Resultado_Consulta_SQL.isEmpty(), "No se realizó la consulta de manera correcta");
		
		//Pasos para hacer la consulta y validar que se encuentren las columnas de: tipo de distribucion, ruta original y ruta dinamica
				addStep("Dentro de la consulta busca un contenedor cuyo artículo tiene pick type \"U\", verifica que la información que arroja sea la del campo ITEM_MASTER_UDA12, utiliza el query: select * from ITEM_MASTER where ITEM_ID = '#SKU';", "?Arroja la información de ITEM_MASTER_UDA12 correctamente");
				SQLResult Resultado_Consulta_SQL2 = executeQuery(POM_Conexiones.connectionRDMv11, "SELECT \"DESCRIPTION\", ITEM_ID, ITEM_MASTER_UDA12 FROM ITEM_MASTER WHERE ITEM_ID = '7501024511808'");
				testCase.addQueryEvidenceCurrentStep(Resultado_Consulta_SQL2);
				assertFalse(Resultado_Consulta_SQL2.isEmpty(), "No se realizó la consulta de manera correcta");

		//Finalizacion del Script y cierre de conexion
		POM_Conexiones.cerrarConexionRdmNubev11();
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_037_VistaCodigoDeLotesCedisTradicionalContenedorPickTypeU";
	}

	@Override
	public String setTestDescription() {
		return "Validar que al consultar la vista de código de lotes la información que arroja un contenedor de tipo \"U\" (surtido por piezas) "
				+ "tenga la información del campo ITEM_MASTER_UDA12";
	}

	@Override
	public String setTestDesigner() {
		return "Jorge Alberto Lugo Martínez";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String setPrerequisites() {
		return "*Contar con VPN\r\n"
				+ "*Contar con la BD RDM MTY\r\n"
				+ "*Contar con el query de la vista\r\n"
				+ "*Tener la vista disponible para la BD RDM MTY\r\n"
				+ "*ASN con bultos tipo canastillas con artículos surtidos por pieza";
	}

	@Override
	public void beforeTest() {
	}

}