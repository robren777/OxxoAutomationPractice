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

public class ATC_FT_039_VistaCodigoDeLotesCedisAutomatizadoContenedorPickTypeCF extends BaseExecution {
	@Test(dataProvider = "data-provider", enabled = true)
	public void ATC_FT_039_VistaCodigoDeLotesCedisAutomatizadoContenedorPickTypeCF_test(
			HashMap<String, String> data) throws Exception {
		D01_Conexiones_BD POM_Conexiones = new D01_Conexiones_BD(testCase, null, this);

		//SCRIPT DEL CASO DE PRUEBA//
		String queryCnt = "SELECT vv.TSF_NO, im.ITEM_ID, vv.PICK_TYPE, vv.VERIF_CODE, im.ITEM_MASTER_UDA11\r\n"
				+ "FROM ITEM_MASTER im\r\n"
				+ "JOIN XXFC_VERIFICACION_V vv ON im.ITEM_ID = vv.ITEM_ID\r\n"
				+ "WHERE vv.TSF_NO = '"+data.get("numTransf")+"' AND vv.ITEM_ID = '"+data.get("SKU")+"'";


		addStep("Se ingresa la siguiente consulta: SELECT vv.TSF_NO, im.ITEM_ID, vv.PICK_TYPE, vv.VERIF_CODE, im.ITEM_MASTER_UDA11\r\n"
				+ "FROM ITEM_MASTER im\r\n"
				+ "JOIN XXFC_VERIFICACION_V vv ON im.ITEM_ID = vv.ITEM_ID\r\n"
				+ "WHERE vv.TSF_NO =  '#transferencia' AND vv.ITEM_ID = '#SKU';" + "Resultado: ?La tabla VERIFIC_CODE contiene la información de la ITEM_MASTER_UDA11 correctamente.");
		System.out.println("Conectando a la BD RDM100.");
		POM_Conexiones.verificarConexionRDM100();
		SQLResult consulta = executeQuery(POM_Conexiones.connectionTlajo, queryCnt);
		assertFalse(consulta.isEmpty(), "No arroja información de ITEM_MASTER correctamente");
		testCase.addQueryEvidenceCurrentStep(consulta);

		System.out.println("Cerrando conexión de RDM100.");
		POM_Conexiones.cerrarConexionRDM100();

	}


	@Override
	public String setTestFullName() {
		return "ATC_FT_039_VistaCodigoDeLotesCedisAutomatizadoContenedorPickTypeCF";
	}

	@Override
	public String setTestDescription() {
		return "Validar que al consultar la vista de código de lotes la información que arroja un contenedor de tipo \"CF\" (surtido como cajas) "
				+ "tenga la información del campo ITEM_MASTER_UDA11";
	}

	@Override
	public String setTestDesigner() {
		return "Jorge Alberto Lugo Martinez";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String setPrerequisites() {
		return "*Contar con VPN\r\n"
				+ "*Contar con la BD RDM TLAJOMULCO\r\n"
				+ "*Contar con el query de la vista\r\n"
				+ "*Tener la vista disponible para la BD RDM TLAJOMULCO\r\n"
				+ "*Tener el SKU del artículo con pick_type \"CF\" del pedido facturado";
	}

	@Override
	public void beforeTest() {
	}

}