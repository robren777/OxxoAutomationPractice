package EBS.oxxoPay;

import static org.testng.Assert.assertFalse;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import org.sikuli.script.Pattern;
import org.testng.annotations.Test;

import EBS.tools.LoginTool;
import EBS.util.GlobalVariables;
import EBS.util.pageObjectModel.EBSPaginaInicial;
import modelo.BaseExecution;
import utils.SikuliUtil;
import utils.password.PasswordUtil;
import utils.selenium.IETest;
import utils.selenium.SeleniumUtil;
import utils.sql.SQLResult;

public class ATC_FT_010_ValidarCartasFemsa extends BaseExecution {
	
	@Test(dataProvider = "data-provider")

	public void ATC_FT_010_ValidarCartasFemsa_test(HashMap<String, String> data)
			throws Exception {
		/*
		 * VARIABLES
		 */
		String ebsUrl = data.get("server");
		String userName = data.get("userName");
		String password = PasswordUtil.decryptPassword(data.get("password"));
		String ebsPattern = data.get("ebsPattern");
		String fechaAplicacion = data.get("fechaAplicacion");
		String descripcionCorta = data.get("descripcionCorta");
		String id = data.get("id");
		
		String consulta1 = "SELECT * FROM XXFC.xxfc_cartas_femsa WHERE CARTA_FEMSA_ID = 1249520";
		String consulta2 = "SELECT * FROM XXFC.xxfc_cartas_femsa_lineas WHERE CARTA_FEMSA_ID = 1249520";

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
		addStep("Ejecutar el query:  SELECT * FROM XXFC.xxfc_cartas_femsa WHERE CARTA_FEMSA_ID = 1412283;");
		SQLResult validat = EBSAVANTE.executeQuery(consulta1);
		String atributo = validat.getData(0, "FECHA_APLICACION");
		boolean validar = atributo.equals(fechaAplicacion);
		System.out.print(atributo);
		assertFalse(!validar, "La respuesta no tiene el el resultado esperado.");
		testCase.addTextEvidenceCurrentStep("Validacion Exitosa");
		
		String atributo2 = validat.getData(0,"DESCRIPCION_CORTA");
		boolean validar2 = atributo2.equals(descripcionCorta);
		System.out.println(atributo2);
		assertFalse(!validar2, "La respuesta no tiene el el resultado esperado.");
		testCase.addTextEvidenceCurrentStep("Validacion Exitosa");
		
		// PASO 3
		addStep("Ejecutar el Query SELECT * FROM XXFC.xxfc_cartas_femsa_lineas WHERE CARTA_FEMSA_ID = 1412283;");
		SQLResult validat2 = EBSAVANTE.executeQuery(consulta2);
		String atributo3 = validat2.getData(0, "CARTA_FEMSA_ID");
		boolean validar3 = atributo3.equals(id);
		System.out.print(atributo3);
		assertFalse(!validar3, "La respuesta no tiene el el resultado esperado.");
		testCase.addTextEvidenceCurrentStep("Validacion Exitosa");
		
		String atributo4 = validat.getData(0,"CREATION_DATE");
		boolean validar4 = atributo4.equals(fechaAplicacion);
		System.out.println(atributo4);
		assertFalse(!validar4, "La respuesta no tiene el el resultado esperado.");
		testCase.addTextEvidenceCurrentStep("Validacion Exitosa");
		
		EBSAVANTE.close();

	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_010_ValidarCartasFemsa_test";
	}

	@Override
	public String setTestDescription() {
		return "Validar que las cuentas contables y la cuenta auxiliar existan en la tabla 'XXFC_MPC_GL_CONFIG_CTAS'. Ejecutar el script mediante soporte de la creación de cuentas. ";
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
		return "Ejecutar el script mediante soporte de la creación de cuentas. ";
	}

	@Override
	public void beforeTest() {
		// TODO Auto-generated method stub

	}

}