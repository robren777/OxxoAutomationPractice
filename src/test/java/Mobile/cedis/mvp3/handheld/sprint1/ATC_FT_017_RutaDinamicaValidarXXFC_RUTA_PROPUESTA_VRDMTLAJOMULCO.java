package Mobile.cedis.mvp3.handheld.sprint1;

import static org.testng.Assert.assertFalse;

import java.util.HashMap;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import Mobile.cedis.om.D01_Conexiones_BD;
import modelo.BaseExecution;
import utils.sql.SQLResult;

public class ATC_FT_017_RutaDinamicaValidarXXFC_RUTA_PROPUESTA_VRDMTLAJOMULCO extends BaseExecution {
	@Test(dataProvider = "data-provider", enabled = true)
	public void ATC_FT_017_RutaDinamicaValidarXXFC_RUTA_PROPUESTA_VRDMTLAJOMULCO_test(HashMap<String, String> data) throws Exception {

		D01_Conexiones_BD POM_Conexiones = new D01_Conexiones_BD(testCase, null, this);
		//Pasos relacionados a crear la conexion con la base de datos de RDM Tlajomulco
		addStep("Se conecta correctamente a la base de datos mediante SQL Developer", "Validar la conexión exitosa");
		POM_Conexiones.verificarConexionRDM100();

		//Pasos para hacer la consulta y validar que se encuentren las columnas de: tipo de distribucion, ruta original y ruta dinamica
		addStep("Ingresar la consulta: SELECT TIPO_DISTRO, RUTA_ORIGINAL, RUTA_PROPUESTA_FL, CRPLAZA, CR_TIENDA FROM rdm100.xxfc_rutas_prop_fl", "Ejecutar el query y verificar columnas: Tipo de distribución, Ruta original y Ruta dinámica");
		SQLResult Resultado_Consulta_SQL = executeQuery(POM_Conexiones.connectionTlajo, "SELECT TIPO_DISTRO, RUTA_ORIGINAL, RUTA_PROPUESTA_FL, CRPLAZA, CR_TIENDA FROM rdm100.xxfc_rutas_prop_fl WHERE CR_TIENDA = 'GAT'");
		testCase.addQueryEvidenceCurrentStep(Resultado_Consulta_SQL);
		assertFalse(Resultado_Consulta_SQL.isEmpty(), "No se realizó la consulta de manera correcta");

		//Finalizacion del Script y cierre de conexion
		POM_Conexiones.cerrarConexionRDM100();
	}







	@Override
	public String setTestFullName() {
		return "ATC_FT_017_RutaDinamicaValidarXXFC_RUTA_PROPUESTA_VRDMTLAJOMULCO";
	}

	@Override
	public String setTestDescription() {
		return "Validar que dentro de la tabla: \"xxfc_rutas_prop_fl\" existan y se guarden datos del: Tipo de distribución, además de la ruta original y la ruta dinámica. Para RDM TLAJOMULCO";
	}

	@Override
	public String setTestDesigner() {
		return "Marcelo Gutiérrez";
	}

	@Override
	public String setTestInstanceID() {
		return null;
	}

	@Override
	public String setPrerequisites() {
		return "\"1. Haber realizado un flujo de recepción con Handheld\n" +
				"2. Haber realizado un flujo de verificación  y acreditación con Handheld\n" +
				"3. Tener accesos a la base de datos: RDM TLAJOMULCO\n" +
				"4. VPN\n" +
				"5. Estar conectado a RDM TLAJOMULCO\"";
	}

	@BeforeTest
	public void beforeTest() {
	}
}
