package EBS.oxxoPay;

import static org.testng.Assert.assertFalse;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import org.sikuli.script.Pattern;
import org.testng.annotations.Test;

import EBS.Sikuli.MetodosSikuli;
import EBS.Sikuli.PatternUtil;
import EBS.tools.LoginTool;
import EBS.util.GlobalVariables;
import EBS.util.pageObjectModel.EBSPaginaInicial;
import modelo.BaseExecution;
import utils.SikuliUtil;
import utils.password.PasswordUtil;
import utils.selenium.IETest;
import utils.selenium.SeleniumUtil;
import utils.sql.SQLResult;

public class ATC_FT_008_ValidarTablaMovimientosTipoRetiro extends BaseExecution {
	
	@Test(dataProvider = "data-provider")

	public void ATC_FT_008_ValidarTablaMovimientosTipoRetiro_test(HashMap<String, String> data)
			throws Exception {
		/*
		 * VARIABLES
		 */
		String ebsUrl = data.get("server");
		String userName = data.get("userName");
		String password = PasswordUtil.decryptPassword(data.get("password"));
		String ebsPattern = data.get("ebsPattern");
		String formaPago = data.get("formaPago");
		String id1 = data.get("id1");
		String id2 = data.get("id2");
		
		String consulta1 = "SELECT * FROM XXFC.XXFC_GL_RST_ENCA where forma_pago = 'PYIO49'";
		
		String consulta2 = "SELECT * FROM XXFC.XXFC_GL_RST_LINEAS where forma_pago = 'PYIO49' AND id_rst_encabezado IN (386,385)";

		/*
		 * UTILERIAS
		 */
		SeleniumUtil u = new SeleniumUtil(new IETest(), true);
		LoginTool inicio = new LoginTool(u, testCase, ebsUrl, userName, password);
		EBSPaginaInicial paginaInicial = new EBSPaginaInicial(u, testCase);
		SikuliUtil sk = new SikuliUtil();
		String pathImg = sk.PatternPath("EBS\\"+ebsPattern);
		Pattern pathimg = new Pattern();
		ArrayList<String> ListImage = new ArrayList<String>();
		HashMap<String, Pattern> ImgPatter = new HashMap<String, Pattern>();
		ArrayList<Pattern> patterns = new ArrayList<Pattern>();
		File Fol = new File(pathImg);
		File Folder = new File(pathImg);

		MetodosSikuli p = new MetodosSikuli(sk, u, pathimg, ListImage, ImgPatter, patterns, Fol);

		PatternUtil patternUtil = new PatternUtil(sk, u, pathimg, ListImage, ImgPatter, patterns, Folder);
		
		utils.sql.SQLUtil EBSAVANTE = new utils.sql.SQLUtil(GlobalVariables.DB_HOST_AVEBQA,
				GlobalVariables.DB_USER_AVEBQA, GlobalVariables.DB_PASSWORD_AVEBQA);

		/*
		 * PASOS DEL CASO DE PRUEBA, MATRIZ: RST Devoluciones OXXO PAY 
		 */

		// PASO 1
		addStep("Conectarse a la base de datos EBS 12.2.4");
		System.out.println(consulta1);
		u.close();
		// PASO 2 
		addStep("Ejecutar el query: select * from xxfc.XXFC_MPC_GL_CONFIG_CTAS where cuenta in ('15TS0125','51OI3807','15TO0026','DEVOXPAY');");
		SQLResult validat = EBSAVANTE.executeQuery(consulta1);
		String atributo = validat.getData(0, "FORMA_PAGO");
		boolean validar = atributo.equals(formaPago);
		System.out.print(atributo);
		assertFalse(!validar, "La respuesta no tiene el el resultado esperado.");
		testCase.addTextEvidenceCurrentStep("Validacion Exitosa");
		// PASO 3
		addStep("SELECT * FROM XXFC.XXFC_GL_RST_LINEAS where forma_pago = 'PYIO49' AND id_rst_encabezado = ''; ");
		SQLResult validat2 = EBSAVANTE.executeQuery(consulta2);
		String atributo3 = validat2.getData(0, "ID_RST_ENCABEZADO");
		boolean validar3 = atributo3.equals(id1);
		System.out.print(atributo3);
		assertFalse(!validar3, "La respuesta no tiene el el resultado esperado.");
		testCase.addTextEvidenceCurrentStep("Validacion Exitosa");
		
		String atributo4 = validat2.getData(0,"ID_RST_ENCABEZADO");
		boolean validar4 = atributo4.equals(id2);
		System.out.println(atributo4);
		assertFalse(!validar4, "La respuesta no tiene el el resultado esperado.");
		testCase.addTextEvidenceCurrentStep("Validacion Exitosa");
		
		EBSAVANTE.close();

	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_008_ValidarTablaMovimientosTipoRetiro_test";
	}

	@Override
	public String setTestDescription() {
		return "Validar que lleguen a la tabla XXFC_GL_RST_ENCA, XXFC_GL_RST_LINEAS movimientos de tipo retiro. Tener acceso a BD, Ejecutar el proceso de Dispersión. ";
	}

	@Override
	public String setTestDesigner() {
		return "JessicaR@hexaware.com";
	}

	@Override
	public String setTestInstanceID() {
		return null;
	}

	@Override
	public String setPrerequisites() {
		return "Tener acceso a BD, Ejecutar el proceso de Dispersión. ";
	}

	@Override
	public void beforeTest() {
		// TODO Auto-generated method stub

	}
}