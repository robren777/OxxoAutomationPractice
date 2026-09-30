package EBS.ivaImpuestoEstatal;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import org.sikuli.script.Key;
import org.sikuli.script.Pattern;
import org.testng.annotations.Test;

import EBS.tools.LoginTool;
import EBS.util.GenericMethods;
import EBS.util.GlobalVariables;
import EBS.util.pageObjectModel.ebsInternational.EBSPaginaInicialColombia;
import EBS.util.patternObject.PatternUtil;
import modelo.BaseExecution;
import utils.SikuliUtil;
import utils.password.PasswordUtil;
import utils.selenium.IETest;
import utils.selenium.SeleniumUtil;

public class ATC_FT_003_Impuesto_estatal_NO_Aplica_VENTA extends BaseExecution {

	@Test(dataProvider = "data-provider")
	public void ATC_FT_003_Impuesto_estatal_NO_Aplica_VENTA_Test(HashMap<String, String> data)
			throws Exception {

		/*
		 * VARIABLES
		 */
		String ebsUrl = data.get("server");
		String userName = data.get("userName");
		String password = PasswordUtil.decryptPassword(data.get("password"));
		String responsabilidad = data.get("responsabilidad");
		String Origen = data.get("StrOrigen");
		String Mayor = data.get("StrMayor");
		String ValorEspecifico = data.get("StrValorEspecifico");
		String FechaInicio = data.get("StrFechaInicio");
		String FechaFin = data.get("StrFechaFin");
		String lote = data.get("StrLote");
		String periodo = data.get("StrPeriodo");
		String ebsPattern = data.get("ebsPattern");
		
		HashMap<String, Pattern> ImgPatter = new HashMap<String, Pattern>();
		ArrayList<Pattern> patterns = new ArrayList<Pattern>();
		ArrayList<String> ListImage = new ArrayList<String>();
		Pattern pathimg = new Pattern();
		/*
		 * UTILERIAS
		 */
		SeleniumUtil u = new SeleniumUtil(new IETest(), true);
		LoginTool inicio = new LoginTool(u, testCase, ebsUrl, userName, password);
		EBSPaginaInicialColombia paginaInicial = new EBSPaginaInicialColombia(u, testCase);
		SikuliUtil sk = new SikuliUtil();
		GenericMethods action = new GenericMethods(u);
		String pathImg = sk.PatternPath("EBS\\"+ebsPattern);
		File Folder = new File(pathImg);
		PatternUtil patternUtil = new PatternUtil(sk, u, pathimg, ListImage, ImgPatter, patterns, Folder);
		
		/*
		 * PASOS DEL CASO DE PRUEBA
		 */
		
		//**Nota: Se requiere colocar los datos de prueba apropiados para validar el caso.

		// PASO 1
		addStep("Ingresar a la liga: http://oxvebsaq1.femcom.net:8000");
		inicio.loginColombia();

		// PASO 2
		addStep("Ingresar a EBS con la responsabilidad COOXXO – GLXXXXX-SUPER USUARIO – Asientos-Import –Run");
		
		paginaInicial.clicEnResponsabilidad(responsabilidad);
		/// Asientos-Import –Run
		paginaInicial.clicAsientosImportRun();

		// PASO 3
		addStep("Capturar los siguientes parámetros: Origen, Mayor,Valor Específico, Rango de Fechas Inicial y Final");
	
		patternUtil.cancelarAlerta();
		patternUtil.cancelarAlerta();
		u.hardWait(60);
		patternUtil.appletMaximize();
		patternUtil.typeAndTab(Origen);
		patternUtil.typeAndTab(Mayor);
		patternUtil.nDOWN(3);
		sk.screen.keyDown(Key.TAB);
		u.hardWait(5);
		patternUtil.typeAndTab(ValorEspecifico);
		//patternUtil.clickPattern("txtFechaInicioIDGrupo", 30);
		//**Temporal
		sk.screen.wait(GlobalVariables.PATTERN_EBS+"EBSColombia\\txtFechaInicioIDGrupo.PNG", 15).click();
		patternUtil.typeAndTab(FechaInicio);
		patternUtil.typeAndTab(FechaFin);
		
		// PASO 4
		addStep("Ejecutar el botón IMPORTAR");
		patternUtil.keyAlt("m");
		patternUtil.keyAlt("S");
		u.screenShotFull("Número de Solicitud");
		patternUtil.keyAlt("A");
		
		// PASO 5
		addStep("Ingresar a la Opción de VER – Solicitudes/Encontrar");
		patternUtil.encontrarSolicitudes();
		
		// PASO 6
		addStep("Localizar el número de Solicitud que se genero y dar clic en la opción de Vizualizar Output");
		patternUtil.keyAlt("o");
		u.hardWait(20);
		patternUtil.keyAlt("R");
		u.hardWait(5);
		patternUtil.keyAlt("s");
		u.screenShotFull("Vizualizar Output");
		//patternUtil.focusWindow("Head_Output", 30);
		//**Temporal
		sk.screen.wait(GlobalVariables.PATTERN_EBS+"EBSColombia\\Head_Output.PNG", 15).click();
		patternUtil.closeWindow();
		u.hardWait(5);
		
		// PASO 7
		addStep("Ingresar a la opción de 'Asientos – Enter'");
		//Cerra Pantalla
		patternUtil.closeActiveWindow();
		//Cerra Pantalla
		patternUtil.closeActiveWindow();
		u.hardWait(5);
		//patternUtil.doubleClickPattern("asientosGL", 30);
		//**Temporal
		sk.screen.wait(GlobalVariables.PATTERN_EBS+"EBSColombia\\asientosGL.PNG", 20).doubleClick();
		//patternUtil.doubleClickPattern("enterGL", 30);
		//**Temporal
		sk.screen.wait(GlobalVariables.PATTERN_EBS+"EBSColombia\\enterGL.PNG", 20).doubleClick();
		u.screenShotFull("Opción de “ Asientos – Enter");
		
		
		// PASO 8
		addStep("Capturar Lote/Periodo/Encontrar");
		patternUtil.encontrarAsientos(lote, periodo);
		patternUtil.keyAlt("c");
		u.hardWait(20);
		u.screenShotFull("Capturar Lote Periodo");
		
		// PASO 9
		addStep("Dar clic en la opción de 'REVISAR ASIENTO'");
		patternUtil.clickRevisarAsiento();
		u.hardWait(20);
		u.screenShotFull("REVISAR ASIENTO");
		
	}

	@Override
	public void beforeTest() {
		// TODO Auto-generated method stub

	}

	@Override
	public String setPrerequisites() {
		// TODO Auto-generated method stub
		return "Se requiere validar que al tener un transacción de venta de cerveza el impuesto estatal No aplica y No debe ser agregado al valor para el cálculo del IVA en el  ticket de venta.";
	}

	@Override
	public String setTestDescription() {
		// TODO Auto-generated method stub
		return "Se requiere validar que al tener un transacción de venta de cerveza el impuesto estatal No aplica y No debe ser agregado al valor para el cálculo del IVA en el  ticket de venta.";
	}

	@Override
	public String setTestDesigner() {
		// TODO Auto-generated method stub
		return "misraimH@hexaware.com";
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_003_Impuesto_estatal_NO_Aplica_VENTA_Test";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}
}
