
/**
 *	Clase para automatizar el apartado de PMO de RDM
 *
 * @author guerrero.vargas
 */

package RDM.util;

import org.sikuli.script.Key;

import modelo.TestCase;
import utils.selenium.SeleniumUtil;

public class PlanificarDistribucion extends BaseRDM {

	public PlanificarDistribucion(SeleniumUtil u, TestCase testCase, String url) {
		super(u, testCase, url);

	}

	/**
	 * En el menu da click en Distribution Planning XXFC Programacion y Balanceo
	 * XXFC Balanceo Produccion Det
	 * 
	 * @author ricardo.palacios
	 */
	public void clickDistributionPlanningBalanceoProduccionDet() {
		try {
			sk.screen.wait(imgPattern.get("distributionPlanning")).click();
			wait(2);
			sk.screen.wait(imgPattern.get("XxfcProgramacionBalanceo")).click();
			wait(2);
			sk.screen.wait(imgPattern.get("XxfcBalanceoProduccionDet")).click();
			wait(2);

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	/**
	 * En el menu da click en Distribution Planning XXFC Programacion y Balanceo
	 * XXFC Ruteo Dinamico
	 * 
	 * @author ricardo.palacios
	 */
	public void clickDistributionPlanningRuteoDinamico() {
		try {
			sk.screen.wait(imgPattern.get("distributionPlanning")).click();
			wait(2);
			sk.screen.wait(imgPattern.get("XxfcProgramacionBalanceo")).click();
			wait(2);
			sk.screen.wait(imgPattern.get("XxfcRuteoDinamico")).click();
			wait(2);

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	/**
	 * En el menu da click en boton liberacion
	 * 
	 * @author ricardo.palacios
	 */
	public void clickBotonLiberacion() {
		try {
			sk.screen.wait(imgPattern.get("liberacionPlanificarDistribucion")).click();
			wait(2);

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	/**
	 * Ingresar informacion en los parametros: fProduccion, turno, mesaControl
	 * 
	 * @param fProduccion
	 * @param turno
	 * @param mesaControl
	 * @author ricardo.palacios
	 */
	public void ingresarDatosFproduccionTurnoMesaControl(String fProduccion, String turno, String mesaControl) {
		try {
			wait(2);
			sk.screen.type(Key.BACKSPACE);
			sk.screen.wait(imgPattern.get("fProduccion")).offset(90, 0).click();
			wait(3);
			sk.screen.type(Key.BACKSPACE);
			sk.screen.type(Key.BACKSPACE);
			sk.screen.type(Key.BACKSPACE);
			sk.screen.type(Key.BACKSPACE);
			sk.screen.type(Key.BACKSPACE);
			sk.screen.type(fProduccion);
			wait(3);
			sk.screen.wait(imgPattern.get("turno")).offset(90, 0).click();
			wait(3);
			sk.screen.type(Key.BACKSPACE);
			sk.screen.type(Key.BACKSPACE);
			sk.screen.type(Key.BACKSPACE);
			sk.screen.type(Key.BACKSPACE);
			sk.screen.type(Key.BACKSPACE);
			sk.screen.type(turno);
			wait(3);
			sk.screen.wait(imgPattern.get("mesaControl")).offset(90, 0).click();
			wait(3);
			sk.screen.type(Key.BACKSPACE);
			sk.screen.type(Key.BACKSPACE);
			sk.screen.type(Key.BACKSPACE);
			sk.screen.type(Key.BACKSPACE);
			sk.screen.type(Key.BACKSPACE);
			sk.screen.type(mesaControl);
			wait(3);

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	/**
	 * Ingresar informacion en los parametros de ruteo dinamico
	 * 
	 * @param FcreaIni
	 * @param FcreaFin
	 * @param EstProp
	 * @author ricardo.palacios
	 */
	public void ingresarDatosRuteoDinamico(String FcreaIni, String FcreaFin, String EstProp) {
		try {
			wait(2);
			sk.screen.type(Key.BACKSPACE);
			sk.screen.wait(imgPattern.get("FcreaIni")).offset(120, 0).click();
			wait(3);
			this.nBackspace(20);
			sk.screen.type(FcreaIni);
			wait(3);
			sk.screen.wait(imgPattern.get("FcreaFin")).offset(120, 0).click();
			wait(3);
			this.nBackspace(20);
			sk.screen.type(FcreaFin);
			wait(3);
			sk.screen.wait(imgPattern.get("EstProp")).offset(120, 0).click();
			wait(3);
			this.nBackspace(20);
			sk.screen.type(EstProp);
			wait(3);

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	/**
	 * En el menu da click en Distribution Planning XXFC Programacion y Balanceo
	 * XXFC Control de Liberaciones Automaticas
	 * 
	 * @author ricardo.palacios
	 */
	public void clickDistributionPlanningBalanceoLiberacionesAutomaticas() {
		try {
			sk.screen.wait(imgPattern.get("distributionPlanning")).click();
			wait(2);
			sk.screen.wait(imgPattern.get("XxfcProgramacionBalanceo")).click();
			wait(2);
			sk.screen.wait(imgPattern.get("XxfcControlLiberacionesAut")).click();
			wait(2);

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	/**
	 * En el menu da click en Distribution Planning XXFC Programacion y por ruta
	 * 
	 * @author ricardo.palacios
	 */
	public void clickDistributionPlanningBalanceoPorRuta() {
		try {
			sk.screen.wait(imgPattern.get("distributionPlanning")).click();
			wait(2);
			sk.screen.wait(imgPattern.get("XxfcProgramacionBalanceo")).click();
			wait(2);
			sk.screen.wait(imgPattern.get("ProgramacionPporRuta")).click();
			wait(2);

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	/**
	 * Ingresar datos en las opciones fecha inicial, fecha final,  distro
	 * 
	 * @author ricardo.palacios
	 */
	public void ingresarParametrosFechasDistro(String fechaInicial,String fechaFinal,String distro) {

		try {

			sk.screen.wait(imgPattern.get("fechaInicialPd")).offset(112, 0).click();
			wait(3);
			this.nBackspace(20);
			sk.screen.type(fechaInicial);
			wait(3);
			sk.screen.wait(imgPattern.get("fechaFinalPd")).offset(112, 0).click();
			wait(3);
			this.nBackspace(20);
			sk.screen.type(fechaFinal);
			wait(3);
			sk.screen.wait(imgPattern.get("distroPd")).offset(130, 0).click();
			wait(3);
			this.nBackspace(20);
			sk.screen.type(distro);
			wait(3);

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	/**
	 * En el menu da click check de distribucion
	 * 
	 * @author ricardo.palacios
	 */
	public void clickDistributionCheck() {
		try {
			sk.screen.wait(imgPattern.get("checkPlanificarDistribucion")).click();
			wait(2);

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	/**
	 * dar click en RechazarPropDistribucion
	 * 
	 * @author ricardo.palacios
	 */
	public void clickRechazarPropDistribucion() {
		try {
			sk.screen.wait(imgPattern.get("RechazarPropDistribucion")).click();
			wait(2);

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	/**
	 * En el menu da click select All Distribucion
	 * 
	 * @author ricardo.palacios
	 */
	public void clickSelectAllDistribucion() {
		try {
			sk.screen.wait(imgPattern.get("selectAll")).click();
			wait(2);

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	/**
	 * En el menu da click AplicarPropPlanificarDistribucion
	 * 
	 * @author ricardo.palacios
	 */
	public void clickAplicarPropPlanificarDistribucion() {
		try {
			sk.screen.wait(imgPattern.get("AplicarPropPlanificarDistribucion")).click();
			wait(2);

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	/**
	 * En el menu da click AsignarMuellesDistribucion
	 * 
	 * @author ricardo.palacios
	 */
	public void clickAsignarMuellesDistribucion() {
		try {
			sk.screen.wait(imgPattern.get("AsignarMuellesDistribucion")).click();
			wait(2);

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	/**
	 * En el menu da click en boton balancear
	 * 
	 * @author ricardo.palacios
	 */
	public void clickBalancear() {

		try {

			sk.screen.wait(imgPattern.get("balancearPd")).click();
			wait(3);

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	/**
	 * Click boton fecha libera
	 * 
	 * @author ricardo.palacios
	 */
	public void clickFechaLibera() {
		try {
			sk.screen.wait(imgPattern.get("MFechaLibera")).click();
			wait(2);

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	/**
	 * En el menu da click check en fecha de liberacion
	 * 
	 * @author ricardo.palacios
	 */
	public void clickFechaLiberacioncheck() {
		try {

			wait(2);
			sk.screen.wait(imgPattern.get("checkFechaLiberacion")).offset(-20, 0).click();
			wait(2);

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	/**
	 * Ingresar informacion en los parametros:fechaLiberaIni fechaLiberaFin
	 * mesaControl
	 * 
	 * @param fechaLiberaIni
	 * @param fechaLiberaFin
	 * @param mesaControl
	 * @author ricardo.palacios
	 */
	public void ingresarDatosFechas(String fechaLiberaIni, String fechaLiberaFin, String mesaControl) {

		try {
			wait(2);
			sk.screen.wait(imgPattern.get("fechaLiberaIni")).offset(170, 0).click();
			wait(3);
			this.nBackspace(20);
			sk.screen.type(fechaLiberaIni);
			wait(3);
			sk.screen.wait(imgPattern.get("fechaLiberaFin")).offset(170, 0).click();
			wait(3);
			this.nBackspace(20);
			sk.screen.type(fechaLiberaFin);
			wait(3);
			sk.screen.wait(imgPattern.get("MesaControlSinAbreviar")).offset(90, 0).click();
			wait(3);
			this.nBackspace(20);
			sk.screen.type(mesaControl);
			wait(3);

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
}