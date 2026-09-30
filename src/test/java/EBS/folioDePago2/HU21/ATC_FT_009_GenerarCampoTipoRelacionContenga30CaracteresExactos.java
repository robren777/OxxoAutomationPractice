package EBS.folioDePago2.HU21;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.sikuli.script.Pattern;
import org.testng.Assert;
import org.testng.annotations.Test;

import EBS.util.GlobalVariables;
import EBS.util.FolioPagoMVP2.ConstruccionXML;
import EBS.util.appletsMethods.MetodosSikuli;
import modelo.BaseExecution;
import utils.SikuliUtil;
import utils.selenium.IETest;
import utils.selenium.SeleniumUtil;
import utils.sql.SQLResult;
/**
 * DESCRIPCION:
 * Generar un xml donde el campo tipo Relación contenga 30 caracteres exactos
 * 
 * NOMBRE CASO DE PRUEBA QUE ABARCAN:
 * ATC_FT_009_GenerarCampoTipoRelacionContenga30CaracteresExactos
 * 
 * PRERREQUISITOS: 
 * Contar con un archivo xml que contenga en el campo Serie de 1 a 30 caracteres
 * Tener Acceso a https://portalespublicosqa.oxxo.com/AccesssControl/pages/login_form.jsf"
 * Tener usuarios para el portal
 * Tener Acceso a la BD en la instancia AVEBQA
 * Tener Acceso al aplicativo de Oracle EBS
 *
 * INSUMOS
 * Factura contabilizada
 *
 * @author ricardor4@hexaware.com
 * @date 10/Marzo/2025
 */
public class ATC_FT_009_GenerarCampoTipoRelacionContenga30CaracteresExactos extends BaseExecution {

	@Test(dataProvider = "data-provider")
	public void  ATC_FT_009_GenerarCampoTipoRelacionContenga30CaracteresExactos_test(HashMap<String, String> data) throws Exception {

		SikuliUtil sk = new SikuliUtil();
		String pathImg = sk.PatternPath("EBS\\"+"EBS");
		Pattern pathimg = new Pattern();
		ArrayList<String> ListImage = new ArrayList<String>();
		HashMap<String, Pattern> ImgPatter = new HashMap<String, Pattern>();
		ArrayList<Pattern> patterns = new ArrayList<Pattern>();
		File Fol = new File(pathImg);
		SeleniumUtil uinsumos = new SeleniumUtil(new IETest(), true);
		MetodosSikuli p = new MetodosSikuli(sk, uinsumos, pathimg, ListImage, ImgPatter, patterns, Fol);
		ConstruccionXML construccionXML = new ConstruccionXML();
		utils.sql.SQLUtil dbEBS = new utils.sql.SQLUtil(GlobalVariables.DB_HOST_AVEBQA, GlobalVariables.DB_USER_AVEBQA,
				GlobalVariables.DB_PASSWORD_AVEBQA);

		// Variables
		String path = data.get("path");
		String url = data.get("url");

		
		Map<String, String> attributes = construccionXML.obtenerAtributos(path);
		// Declarar variables individuales y asignarles los valores
		String fecha = attributes.get("Fecha");
		String folio = attributes.get("Folio");
		String serie = attributes.get("Serie");
		String rfc = attributes.get("Rfc");
		String importe = attributes.get("Total");
		String tipoDeComprobante = attributes.get("TipoDeComprobante");
		String uuid = attributes.get("UUID");
		String nombreProveedor = "";
		String fechaFactura = "";
		Boolean flag = false;
		
		
		
		 //-------------------------------------------------------------------------------------------------------------------
		//TABLAS A VALIDAR 
		
		String tablaUid = "select VALC_TIPORELACION,VALC_RFC,VALN_FOLIO from apps.xxfc_cfdavalidar where valc_uuid = "+"'"+uuid+"' order by VALD_FECHARECEPCION desc FETCH FIRST 1 ROW ONLY";
		String tablaUidRelacion = "select VALN_FOLIO,VALC_UUIDRELACIONADO from apps.xxfc_cfdavalidar where valc_uuid = "+"'"+uuid+"' order by VALD_FECHARECEPCION desc FETCH FIRST 1 ROW ONLY";
		
		
		/*
		 * PASOS DEL CASO DE PRUEBA
		 */

		// PASO 1
		addStep("Procesar XML en la interfaz FEMSA_WS");
		construccionXML.sendXmlPath(path, url);
		SQLResult consultauid = dbEBS.executeQuery(tablaUid);
		SQLResult consultauidRelacion = dbEBS.executeQuery(tablaUidRelacion);
		Assert.assertNotNull(consultauidRelacion, "El resultado de la consulta es nulo");
		String xmlContent = new String(Files.readAllBytes(Paths.get(path)), "UTF-8");
		testCase.addCodeEvidenceCurrentStep(xmlContent);
		// PASO 2
		addStep("Visualizar que el archivo se haya subido y haya llegado a la interfaz FEMSA_WS con el query:"
				+ "select valc_tiporelacion from apps.xxfc_cfdavalidar where valc_uuid = ");
		testCase.addBoldTextEvidenceCurrentStep("Validar Base de datos");
		testCase.addQueryEvidenceCurrentStep(consultauid);
		testCase.addQueryEvidenceCurrentStep(consultauidRelacion);
		Assert.assertNotNull(consultauidRelacion, "El resultado de la consulta es nulo");
		testCase.addBoldTextEvidenceCurrentStep("Validacion exitosa");
		uinsumos.getDriver().quit();
		
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_009_GenerarCampoTipoRelacionContenga30CaracteresExactos_test";
	}

	@Override
	public String setTestDescription() {
		return "Generar un xml donde el campo tipo Relacion contenga 30 caracteres exactos";
	}

	@Override
	public String setTestDesigner() {
		return "ricardor4@hexaware.com";
	}

	@Override
	public String setTestInstanceID() {
		return null;
	}

	@Override
	public String setPrerequisites() {
		return "Contar con un archivo xml que contenga en el campo tipo relacion 30 caracteres exactos\r\n"
				+ "Tener usuarios para el portal\r\n"
				+ "Tener Acceso a la BD en la instancia AVEBQA\r\n"
				+ "Tener Acceso al aplicativo de Oracle EBS\r\n";
	}

	@Override
	public void beforeTest() {

	}
}