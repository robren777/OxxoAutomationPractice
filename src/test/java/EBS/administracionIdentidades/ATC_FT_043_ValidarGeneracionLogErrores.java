package EBS.administracionIdentidades;

import static org.testng.Assert.assertFalse;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import org.sikuli.script.Pattern;
import org.testng.annotations.Test;

import EBS.tools.LoginTool;
import EBS.util.GlobalVariables;
import EBS.util.appletsMethods.MetodosSikuli;
import EBS.util.pageObjectModel.EBSPaginaInicial;
import EBS.util.patternObject.PatternUtil;
import modelo.BaseExecution;
import utils.SikuliUtil;
import utils.selenium.IETest;
import utils.selenium.SeleniumUtil;
import utils.sql.SQLResult;

public class ATC_FT_043_ValidarGeneracionLogErrores extends BaseExecution {

	@Test(dataProvider = "data-provider")

	public void ATC_FT_043_ValidarGeneracionLogErrores_test(HashMap<String, String> data) throws Exception {
		/*
		 * VARIABLES
		 */
		String ebsUrl = data.get("server");
		String userName = GlobalVariables.getJreyesHexEbsUsr;
		String password = GlobalVariables.getJreyesHexEbsPwd;
		String ebsPattern = data.get("ebsPattern")+"\\EBS";
		String output = data.get("output");

		String consulta1 = "select * from apps.xxfc_idm_movtos_all where TIPO_EVENTO IN ('CREAR_USR_PRC','INACTIVAR_USR_PRC','REACTIVAR_USR_PRC','ACTUALIZA_USR_PRC','CONSULTA_USR_PRC',\r\n" + 
				"'CAMBIO_RESPO_PRC','ACTUALIZA_CONTRASENA_PRC') AND FECHA_MOVIMIENTO BETWEEN  to_date('20/10/2022','DD/MM/YYYY') AND to_date('24/10/2022','DD/MM/YYYY') AND NOMBRE_USUARIO IN ( \r\n" + 
				"'98767895','98767896','98767897','98767898')";
		/*
		 * UTILERIAS
		 */
		SeleniumUtil u = new SeleniumUtil(new IETest(), true);
		LoginTool inicio = new LoginTool(u, testCase, ebsUrl, userName, password);
		EBSPaginaInicial paginaInicial = new EBSPaginaInicial(u, testCase);
		SikuliUtil sk = new SikuliUtil();
		String pathImg = sk.PatternPath(ebsPattern);
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
		 * PASOS DEL CASO DE PRUEBA, MATRIZ: ADMINISTRACIÓN DE IDENTIDADES
		 */

		// PASO 1
		addStep("Ir a la BD del servidor AVEBQA 12 24");
		u.close();
		u.hardWait(3);
		System.out.println(consulta1);
		// PASO 2
		addStep("Realizar la siguiente consulta select * from apps.xxfc_idm_movtos_all where NOMBRE_USUARIO = '98767896' and TIPO_EVENTO = 'CREAR_USR_PRC'");
		SQLResult validat = EBSAVANTE.executeQuery(consulta1);
		String atributo = validat.getData(0, "OUTPUT");
		boolean validar = atributo.equals(output);
		System.out.print(atributo);
		assertFalse(!validar, "La respuesta no tiene el el resultado esperado.");
		testCase.addTextEvidenceCurrentStep("Validacion Exitosa");

	}

	@Override
	public String setTestFullName() {
		return null;
	}

	@Override
	public String setTestDescription() {
		return "Los parámetros a utilizar serian los siguientes:Nombre de usuario: (Número de empleado).Responsabilidades asignadas.Fecha de asignación de responsabilidades.Fecha de remoción de responsabilidades.Responsabilidades removidas.Fecha de remoción de responsabilidades. Contar con el usuario asignado y la responsabilidad asignada deacuerdo a lo configurado desde IDM en donde se genera un log de errores";
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
		return "Contar con el usuario asignado y la responsabilidad asignada deacuerdo a lo configurado desde IDM en donde se genera un log de errores";
	}

	@Override
	public void beforeTest() {
		// TODO Auto-generated method stub

	}

}