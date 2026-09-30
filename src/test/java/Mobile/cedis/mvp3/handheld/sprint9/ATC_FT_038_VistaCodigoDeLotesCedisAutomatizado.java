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

public class ATC_FT_038_VistaCodigoDeLotesCedisAutomatizado extends BaseExecution {
	@Test(dataProvider = "data-provider", enabled = true)
	public void ATC_FT_038_VistaCodigoDeLotesCedisAutomatizado_test(
			HashMap<String, String> data) throws Exception {

		D01_Conexiones_BD POM_Conexiones = new D01_Conexiones_BD(testCase, null, this);
		//Pasos relacionados a crear la conexion con la base de datos de RDM Tlajomulco
		addStep("Se conecta correctamente a la base de datos mediante SQL Developer", "Validar la conexión exitosa");
		POM_Conexiones.verificarConexionRDM100();

		//Pasos para hacer la consulta y validar que se encuentren las columnas de: tipo de distribucion, ruta original y ruta dinamica
		addStep("Se ingresa la siguiente consulta: SELECT * FROM XXFC_VERIFICACION_V WHERE TSF_NO = #Transfer", "Se ejecuto el query exitosamente y se despliegan la vista correctamente con la informacion requerida");
		SQLResult Resultado_Consulta_SQL = executeQuery(POM_Conexiones.connectionTlajo, "SELECT TSF_NO, DEST_ID, CONTAINER_ID, ITEM_ID, PICK_TYPE, VERIF_CODE FROM xxfc_verificacion_v WHERE ROWNUM <= 3");
		testCase.addQueryEvidenceCurrentStep(Resultado_Consulta_SQL);
		assertFalse(Resultado_Consulta_SQL.isEmpty(), "No se realizó la consulta de manera correcta");

		//Finalizacion del Script y cierre de conexion
		POM_Conexiones.cerrarConexionRDM100();

	}

	/*CEDIS MVP3: MTC-FT-030/TABLET ACREDITACIÓN "Modificar Dañados y Caducos" - Agregar y quitar artículos dañados y caducos
	  Desc:Validar que las instrucciones que se muestran en la pantalla de dañados en verificación estén actualizadas de acuerdo al cambio de redacción.
	  @autor: Juan Ibarra
	  @date: 08/12/2024
	 */

	@Override
	public String setTestFullName() {
		return "ATC_FT_038_VistaCodigoDeLotesCedisAutomatizado";
	}

	@Override
	public String setTestDescription() {
		return "Validar que la vista contenga lo siguientes campos: \r\n"
				+ "xxfc_posterior_embarque_citems: pedido (tsf_no), tienda (dest_id), contenedor (container_id), artículo, (item_id), tipo de pickeo (pick_type).\r\n"
				+ "item_master: código de verificación (nombre \"verif_code\": item_master_uda11, item_master_uda12).";
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
				+ "*Contar con la BD RDM TLAJOMULCO o RDM100\r\n"
				+ "*Contar con el query de la vista\r\n"
				+ "*Tener la vista disponible para la BD RDM TLAJOMULCO o RDM100";
	}

	@Override
	public void beforeTest() {
	}

}