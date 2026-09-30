package IMA.MigracionServiciosCashInOut.om;

import org.openqa.selenium.By;

import modelo.TestCase;
import utils.selenium.SeleniumUtil;

public class jobUtil {
	SeleniumUtil u;
	TestCase testCase;
	
	
	By viewPoint = By.xpath("//*[@id=\"monitoringHomeTabSet\"]/div/ul/li[1]/div/a");
	
	
	public jobUtil(SeleniumUtil u, TestCase testCase) {
		super();
		this.u = u;
		this.testCase = testCase;

	}

	/**
	 * Después de cerrar el ViewPoint, da click en el apartado ViewPoint
	 * @author alicia.ceja@gtim.mx
	 */
	public void selectViewPoint() {
		u.click(viewPoint);
	}
}
