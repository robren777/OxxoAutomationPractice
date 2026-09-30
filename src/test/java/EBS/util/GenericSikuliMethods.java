package EBS.util;

import org.sikuli.script.Key;

import utils.SikuliUtil;

public class GenericSikuliMethods {

	/**
	 * Metodo Estandar para efectuar la accion ALT + F4 para cerrar ventanas
	 * 
	 * @param sk SikuliUtil
	 * @author Jonofre@Hexaware.com
	 */
	public void MasterSikuliClose(SikuliUtil sk) {

		try {
			sk.screen.keyDown(Key.ALT);
			sk.screen.keyDown(Key.F4);
			sk.screen.keyUp();
		} finally {
			sk.screen.keyUp();
		}

	}

}
