package RDM.util;

import org.sikuli.script.FindFailed;
import org.sikuli.script.Key;

import modelo.TestCase;
import utils.selenium.SeleniumUtil;

public class ArticulosValor extends BaseRDM {	
	public ArticulosValor(SeleniumUtil u, TestCase testCase, String url) {
		super(u, testCase, url);
	}
	
	/**
	 * En el menu da click en Support functions > Item Setup > Item Master
	 * 
	 * @author guerrero.vargas
	 */
	public void supportFunctionsItemSetupMaster() throws FindFailed {
		sk.screen.wait(imgPattern.get("supportFunctions")).click();
		sk.screen.wait(imgPattern.get("itemSetup")).click();
		sk.screen.hover(imgPattern.get("itemMasterEditor"));
		screenshot();
		sk.screen.click();
			
		wait(2);
	}
	
	/**
	 * Ingresa el numero de articulo
	 * 
	 * @author guerrero.vargas
	 */
	public void ingresarArticuloExecute(String item) throws FindFailed {
		wait(3);
		screenshot();
		sk.screen.type(item);
		wait(1);
		screenshot();
		sk.screen.type(Key.TAB);
		wait(1);
		screenshot();

	}
	
	/**
	 * Recorre los distintos campos, da doble click en un UDA para abrir la ventana
	 * donde se modifica el uda del articulo, coloca uda10Value y guarda
	 * 
	 * @param uda10Value valor que se le va a colocar al articulo
	 * @return true si no hay mensaje de error, false de lo contrario
	 * @throws FindFailed
	 * @author guerrero.vargas
	 */
	public boolean activarConfigArticulo(String uda10Value) throws FindFailed {		
		sk.screen.type(Key.TAB, Key.SHIFT);
		wait(1);
		screenshot();
		
		sk.screen.wait(imgPattern.get("barRight")).offset(0, -30).doubleClick();
		wait(1);
		screenshot();
		
		if (sk.screen.exists(imgPattern.get("couldNotReserve")) != null) {
			return false;
		}
		
		nShiftTab(8);
		wait(1);
		screenshot();
		
		sk.screen.type(uda10Value);
		wait(1);
		screenshot();
		
		sk.screen.wait(imgPattern.get("uda10Save")).click();
		
		return true;
	}
	
	/**
	 * Valida el nuevo uda del articulo
	 * 
	 * @param uda10Value valor del uda a validar
	 * @return true si tiene el valor correcto, false de lo contrario
	 * @author guerrero.vargas
	 */
	public String validarModificacionUda(String uda10Value) {
		wait(3);
		nShiftTab(8);
		wait(2);
		numLockOff();
		
		sk.screen.type(Key.HOME);
		sk.screen.type(Key.RIGHT, Key.SHIFT);	
		wait(1);
		
		String valor = copyPaste();
				
		return valor;
	}
}
