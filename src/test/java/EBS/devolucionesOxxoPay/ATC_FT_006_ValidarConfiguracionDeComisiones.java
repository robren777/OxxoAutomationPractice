package EBS.devolucionesOxxoPay;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import org.sikuli.script.Pattern;
import org.testng.annotations.Test;

import EBS.tools.LoginTool;
import EBS.util.appletsMethods.MetodosSikuli;
import EBS.util.pageObjectModel.EBSPaginaInicial;
import modelo.BaseExecution;
import utils.SikuliUtil;
import utils.password.PasswordUtil;
import utils.selenium.IETest;
import utils.selenium.SeleniumUtil;

public class ATC_FT_006_ValidarConfiguracionDeComisiones extends BaseExecution {

	@Test(dataProvider = "data-provider")
	public void ATC_FT_006_ValidarConfiguracionDeComisiones_Test(HashMap<String, String> data) throws Exception {

		/*
		 * VARIABLES
		 */
		String ebsUrl = data.get("server");
		String userName = data.get("userName");
		String password = PasswordUtil.decryptPassword(data.get("password"));
		String responsabilidad = data.get("responsabilidad");
		String nombre = data.get("nombre");
		String tipoItem = data.get("tipoItem");

		/*
		 * UTILERIAS
		 */

		SeleniumUtil u = new SeleniumUtil(new IETest(), true);
		LoginTool inicio = new LoginTool(u, testCase, ebsUrl, userName, password);
		EBSPaginaInicial paginaInicial = new EBSPaginaInicial(u, testCase);
		SikuliUtil sk = new SikuliUtil();
		String pathImg = sk.PatternPath("EBS\\"+"EBS");
		Pattern pathimg = new Pattern();
		ArrayList<String> ListImage = new ArrayList<String>();
		HashMap<String, Pattern> ImgPatter = new HashMap<String, Pattern>();
		ArrayList<Pattern> patterns = new ArrayList<Pattern>();
		File Fol = new File(pathImg);
		MetodosSikuli p = new MetodosSikuli(sk, u, pathimg, ListImage, ImgPatter, patterns, Fol);

		/*
		 * PASOS DEL CASO DE PRUEBA
		 */

		// PASO 1
		addStep("Entrar  a la responsabilidad de MXOXXO-COXXXXX-ADMINISTRADOR --> Tabuladores --> Servicios, Prod. y Formas Pago");
		inicio.login();
		paginaInicial.clickOnResponsabilidad(responsabilidad);
		paginaInicial.carpetaTabuladoresElement.click();
		paginaInicial.runServiciosProdFPagoElement.click();

		// PASO 2
		addStep("Seleccionar value set, Name = 'FEMCO_GL_CUENTA', Clic en botón 'Find'");

		// PASO 3
		addStep("Posicionarse en la celda de Nombre, Clic en F11, capturar  Name = 'TABULADOR DE COBROS DE SERVICIOS OXXO', Ctrl F11");
		p.ingresarNombreTabuladorCobroServicios(nombre);

		// PASO 4
		addStep("Posicionarse en la celda de Tipo de Item, Clic en F11, capturar Tipo de Item = 'Forma de Pago', Ctrl F11");
		p.ingresarTipoItem(tipoItem);
		p.cerrarApplet();
		u.close();
	}

	@Override
	public void beforeTest() {
		// TODO Auto-generated method stub

	}

	@Override
	public String setPrerequisites() {
		// TODO Auto-generated method stub
		return "Tener responsabilidad MXOXXO-GLXXXXX-Super Usuario";
	}

	@Override
	public String setTestDescription() {
		// TODO Auto-generated method stub
		return "Se validara en el tabulador de comisionistas que se configure la forma de pago para devoluciones y la comision que le correspondera al lider de la tienda."
				+ "Prerequisitos: Tener responsabilidad MXOXXO-GLXXXXX-Super Usuario";
	}

	@Override
	public String setTestDesigner() {
		// TODO Auto-generated method stub
		return "edwin.ramirez@neoris.com";
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_006_ValidarConfiguracionDeComisiones_Test";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}
}
