package WEB.PortalCobroServiciosPerfiles.om;

import org.openqa.selenium.By;
import WEB.om.MetodosExtra;
import modelo.TestCase;
import utils.selenium.SeleniumUtil;

public class MenuPrincipal_CobroServicios{

	SeleniumUtil u;
	TestCase testCase;
	MetodosExtra me;
	
	
	/*
	 * Localizadores Menu Integridad de datos
	 ********************************************************************************************/
	
	By PortalCobroServicios = By.id("e0_0i");
	
	//Opciones menu
	
	By ReporteCobrosCorrectos = By.id("e0_4o");
	
	/*
	 * Constructor
	 ********************************************************************************************/
	public MenuPrincipal_CobroServicios(SeleniumUtil u, TestCase testCase) {
		super();
		this.u = u;
		this.testCase = testCase;
		this.me = new MetodosExtra(u, testCase);
	}
	
	/*
	 * Metodos Menu Integridad de datos
	 ********************************************************************************************/
	
	/**
	 * Metodo para dar click en cobro de servicios
	 * @author Herbe Cauich
	 */
	public void clickPortalCobroServicios() {
		u.highLight(PortalCobroServicios);
		testCase.addScreenShotCurrentStep(u, "PortalCobroServicios");
		u.unhighLight(PortalCobroServicios);
		u.mouseOver(PortalCobroServicios);
		testCase.addScreenShotCurrentStep(u, "clickPortalCobroServicios");
	}
	
	/**
	 * Metodo para dar click en cobros correctos
	 * @author Herbe Cauich
	 */
	public void clickReporteCobrosCorrectos() {
		u.highLight(ReporteCobrosCorrectos);
		testCase.addScreenShotCurrentStep(u, "ReporteCobrosCorrectos");
		u.unhighLight(ReporteCobrosCorrectos);
		u.click(ReporteCobrosCorrectos);
		testCase.addScreenShotCurrentStep(u, "clickReporteCobrosCorrectos");
	}
	
	
}
