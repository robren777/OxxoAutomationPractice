
/**
 *	Clase para automatizar el apartado de Facturacion
 *
 * @author ricardo.palacios
 */

package RDM.util;

import org.sikuli.script.FindFailed;
import org.sikuli.script.Key;

import modelo.TestCase;
import utils.selenium.SeleniumUtil;

public class Facturacion extends BaseRDM {

	public Facturacion(SeleniumUtil u, TestCase testCase, String url) {
		super(u, testCase, url);

	}

	/**
	 * Click en XXFC-Wave Status
	 * 
	 * @throws FindFailed
	 * @author ricardo.palacios
	 */
	public void clickWaveStatus() throws FindFailed {

			sk.screen.wait(imgPattern.get("distributionPlanning")).click();
			wait(3);
			sk.screen.wait(imgPattern.get("reports")).click();
			wait(3);
			sk.screen.wait(imgPattern.get("XxfcWaveStatusDistroIntercedis")).click();
			wait(3);

	}
	
	/**
	 * En el menu da click en check de facturacion
	 * 
	 * @throws FindFailed
	 * @author ricardo.palacios
	 */
	public void clickEjecutarCheckFacturacion() throws FindFailed {

			sk.screen.wait(imgPattern.get("checkIntercedisAprovacionFacturacion")).click();
			wait(3);

	}
	
	/**
	 * Ingresar status y distro
	 * 
	 * @throws FindFailed
	 * @author ricardo.palacios
	 */
	public void ingresarStatusDistro(String status, String distro) throws FindFailed {

			wait(3);
			sk.screen.wait(imgPattern.get("Distrointercedisuno")).offset(90, 0).click();
			this.nBackspace(20);
			wait(3);
			sk.screen.type(distro);
			wait(3);
			sk.screen.wait(imgPattern.get("statusIntercedis")).offset(90, 0).click();
			this.nBackspace(20);
			wait(3);
			sk.screen.wait(imgPattern.get("statusIntercedis")).offset(90, 0).click();
			this.nBackspace(20);
			sk.screen.type(status);
			
	}
	
}