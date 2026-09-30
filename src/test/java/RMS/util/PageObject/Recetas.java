/**
 * Automatiza las funciones relacionadas a las recetas 
 */

package RMS.util.PageObject;

import java.awt.Robot;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import org.sikuli.hotkey.Keys;
import org.sikuli.script.FindFailed;
import org.sikuli.script.Pattern;

import modelo.TestCase;
import utils.SikuliUtil;
import utils.selenium.SeleniumUtil;
import utils.sql.SQLUtil;

public class Recetas extends BaseRMS {

	public Recetas(SikuliUtil sk, SeleniumUtil u, Robot r, Pattern pathimg, ArrayList<String> ListImage,
			HashMap<String, Pattern> ImgPatter, ArrayList<Pattern> patterns, File Fol, String status, SQLUtil dbRms,
			HashMap<String, String> data, TestCase testCase) {

		super(sk, u, r, pathimg, ListImage, ImgPatter, patterns, Fol, status, dbRms, data, testCase);

	}

	/**
	 * Selecciona en la pantalla rtkstrt(menu principal) la opcion: Items > FEMSA
	 * Packitem Maintenance
	 */
	public void menuItemsFemsaPackItem() {
		seleccionarMenuRtkstrt("rtkstrt_items", "rtkstrt_items_femsapackitem");
	}

	/**
	 * Captura el PackNo
	 */
	public void capturarPackNo() {
		try {
			sk.getScreen().type(data.get("pack_no"));
			sk.getScreen().type(Keys.TAB);
			r.delay(1000);
			super.screenshot();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * Modifica la cantidad del primer item de la receta, suma o resta milesimas(?)
	 * a la cantidad actual, obtiene la cantidad a modificar del data provider. Ej:
	 * Si en el data tienes 1, se sumara 0.0001 a la cantidad actual del item Si
	 * tienes -2, se restara 0.0002, etc.
	 * 
	 * @return array con el item y la nueva cantidad para usarlas en validaciones
	 *         posteriores
	 */
	public String[] modificarReceta() {
		String item = "";
		String newItemQuantityStr = "";

		try {
			// copia la cantidad actual del item de la pantalla de RMS
			String itemQty = copyPaste();

			// obtiene la cantidad a restar o sumar del data provider y la convierte a int
			int addToQty = Integer.valueOf(data.get("new_quantity"));

			// convierte a entero la cantidad actual y le suma la cantidad del data provider
			int itemQtyInt = (int) (Double.valueOf(itemQty) * 1000 + addToQty);

			// vuelve a convertir a decimal y lo almacena en un string
			newItemQuantityStr = (itemQtyInt / 1000.0) + "";

			r.delay(1000);

			sk.getScreen().type(newItemQuantityStr); // coloca en RMS la nueva cantidad
			super.screenshot();

			sk.getScreen().wait(ImgPatter.get("xxfcpackmaint_item")).click();
			item = super.copyPaste(); // obtiene el numero de item modificado de RMS, se necesita para validaciones

			sk.getScreen().wait(ImgPatter.get("xxfcpackmaint_apply")).click();
			super.screenshot();

			sk.getScreen().hover(ImgPatter.get("xxfcpackmaint_ok"));
			super.screenshot();
			sk.getScreen().click();

			sk.getScreen().hover(ImgPatter.get("xxfcpackmaint_ok_no"));
			super.screenshot();
			sk.getScreen().click();

			sk.getScreen().hover(ImgPatter.get("xxfcpackmaint_x"));
			super.screenshot();
			sk.getScreen().click();

			sk.getScreen().hover(ImgPatter.get("xxfcpackmaint_x_yes"));
			super.screenshot();
			sk.getScreen().click();

			sk.getScreen().wait(ImgPatter.get("rtkstrt"));
			r.delay(2000);
			cerrarVentana();

		} catch (FindFailed e) {
			System.out.println(e.getMessage());
		}

		return new String[] { item, newItemQuantityStr }; // devuelve el item y la nueva cantidad
	}

}