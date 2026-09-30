/**
 * Automatiza las funciones relacionadas a los impuestos.
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

public class Impuestos extends BaseRMS {

	public Impuestos(SikuliUtil sk, SeleniumUtil u, Robot r, Pattern pathimg, ArrayList<String> ListImage,
			HashMap<String, Pattern> ImgPatter, ArrayList<Pattern> patterns, File Fol, String status, SQLUtil dbRms,
			HashMap<String, String> data, TestCase testCase) {

		super(sk, u, r, pathimg, ListImage, ImgPatter, patterns, Fol, status, dbRms, data, testCase);

	}

	/**
	 * Selecciona en la pantalla rtkstrt(menu principal) la opcion: Finance >
	 * XXFC-Captura de ajuste de transacciones
	 */
	public void menuFinanceAjustes() {
		seleccionarMenuRtkstrt("rtkstrt_finance", "rtkstrt_finance_ajuste_transacciones");
	}

	/**
	 * Selecciona en la pantalla rtkstrt(menu principal) la opcion: Items > Items
	 */
	public void menuItemItems() {
		seleccionarMenuRtkstrt("rtkstrt_items", "rtkstrt_items_items");
	}

	/**
	 * Coloca el numero de item y manda a la siguiente pantalla
	 */
	public void editItem() {
		super.action();

		try {
			sk.getScreen().hover(ImgPatter.get("itemfind_action_edititem"));
			super.screenshot();
			sk.getScreen().click();

			sk.getScreen().type(data.get("item"));
			sk.getScreen().type(Keys.TAB);
			r.delay(1000);
			super.screenshot();

			sk.getScreen().wait(ImgPatter.get("btn_ok_slctd")).click();
			// espera a que se muestre un elemento de la siguiente pantalla para tomar
			// captura
			sk.getScreen().wait(ImgPatter.get("itemmaster_delete"));
			super.screenshot();

		} catch (FindFailed e) {
			e.printStackTrace();
		}

	}

	/**
	 * Dentro de la pantalla itemmaster, da click a VAT_Maintenance
	 */
	public void vatMaintenance() {
		try {
			sk.getScreen().hover(ImgPatter.get("itemmaster_vatmaintenance"));
			super.screenshot();
			sk.getScreen().click();

		} catch (FindFailed e) {
			e.printStackTrace();
		}

	}

	/**
	 * Dentro de la pantalla vatitem agrega: Vat region, Vat code y la fecha de
	 * activacion del Vat Code Info extraida del data provider
	 */
	public void vatRegion() {
		try {

			sk.getScreen().type(data.get("vat_region"));			
			r.delay(5000);

			super.screenshot();

			sk.getScreen().type(Keys.TAB);
			sk.getScreen().wait(ImgPatter.get("vatitem_addvat")).click();

			r.delay(2000);
			

			sk.getScreen().type(data.get("vat_code"));
			sk.getScreen().type(Keys.TAB);
			r.delay(2000);
			sk.getScreen().type(Keys.UP);
			sk.getScreen().type(Keys.TAB);
			r.delay(3000);
			
			sk.getScreen().type(Keys.ENTER);
			r.delay(3000);

			sk.getScreen().type(data.get("active_date"));
			sk.getScreen().type(Keys.TAB);
			super.screenshot();

			sk.getScreen().type(Keys.ENTER);
			r.delay(1000);

			cerrarVentana();

		} catch (FindFailed e) {
			e.printStackTrace();
		}

	}

	/**
	 * Captura la info (Plaza > Tienda > Movimiento) para generar el ajuste de la
	 * transaccion informacion obtenida del data provider
	 */
	public void registrarMov() {
		try {
			sk.getScreen().wait(ImgPatter.get("xxfcinaj_plaza")).click();
			r.delay(1000);
			sk.getScreen().type(data.get("plaza"));
			sk.getScreen().type(Keys.TAB);
			r.delay(1000);

			sk.getScreen().type(data.get("tienda"));
			sk.getScreen().type(Keys.TAB);
			r.delay(1000);

			sk.getScreen().type(data.get("movimiento"));
			sk.getScreen().type(Keys.TAB);

			// PREGUNTAR POR EL ERROR DE LOGIN

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

	}

	/**
	 * Entra en el menu Control > Sales Tax > Geocode Maintenance > Edit y coloca
	 * toda la informacion pedida por la pantalla gcdhier sacando la info del data
	 * provider para agregar una tienda a una jurisdiccion
	 */
	public void agregarTiendaJurisdiccion() {
		try {
			seleccionarMenuRtkstrt("rtkstrt_control", "rtkstrt_sales_tax");

			sk.getScreen().wait(ImgPatter.get("rtkstrt_geocode_maintenance")).click();
			super.screenshot();
			sk.getScreen().doubleClick();

			sk.getScreen().wait(ImgPatter.get("rtkstrt_geocode_maintenance_edit")).click();
			super.screenshot();
			sk.getScreen().doubleClick();

			sk.getScreen().wait(ImgPatter.get("gcdhier_country")).click();
			r.delay(1000);

			super.screenshot();

			sk.getScreen().type(data.get("country"));
			r.delay(1000);
			sk.getScreen().type(Keys.TAB);

			sk.getScreen().type(data.get("state"));
			r.delay(1000);
			sk.getScreen().type(Keys.TAB);

			sk.getScreen().wait(ImgPatter.get("gcdhier_search")).click();
			sk.getScreen().hover(ImgPatter.get("gcdhier_store_tax_code"));
			super.screenshot();
			sk.getScreen().click();

			sk.getScreen().wait(ImgPatter.get("gctxcde_add")).click();
			r.delay(1000);

			sk.getScreen().type(data.get("store"));
			sk.getScreen().type(Keys.TAB);

			super.screenshot();

			sk.getScreen().wait(ImgPatter.get("gctxcde_save")).click();
			r.delay(2000);

			sk.getScreen().wait(ImgPatter.get("gctxcde_close")).click();
			sk.getScreen().wait(ImgPatter.get("gctxcde_close_yes"));
			super.screenshot();
			sk.getScreen().wait(ImgPatter.get("gctxcde_close_yes"));

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

	}

	/**
	 * Entra a Control > Sales Tax > Tax Rate Maintenance > Edit y captura la
	 * informacion del item que agregara a una jurisdiccion.
	 */
	public void agregarArticulo() {
		try {
			seleccionarMenuRtkstrt("rtkstrt_control", "rtkstrt_sales_tax");
			sk.getScreen().wait(ImgPatter.get("rtkstrt_tax_rate_maintenance")).doubleClick();
			sk.getScreen().wait(ImgPatter.get("rtkstrt_tax_rate_maintenance_edit")).doubleClick();
			sk.getScreen().wait(ImgPatter.get("taxhier_search_tax_code")).click();
			sk.getScreen().wait(ImgPatter.get("taxhier_search_box_tax_code")).click();
			sk.getScreen().type(data.get("state"));
			sk.getScreen().type(Keys.ENTER);
			r.delay(1000);
			sk.getScreen().type(Keys.ENTER);
			r.delay(1000);
			sk.getScreen().wait(ImgPatter.get("taxhier_filter")).click();
			sk.getScreen().wait(ImgPatter.get("taxhier_tax_codes_rates")).click();
			sk.getScreen().wait(ImgPatter.get("taxhier_item_geocode")).click();
			sk.getScreen().wait(ImgPatter.get("taxcode_add")).click();

			// espera que permita agregar el item a la jurisdiccion
			while (sk.getScreen().exists(ImgPatter.get("taxcode_blue")) == null) {
				// waiting
			}

			r.delay(1000);

			sk.getScreen().type(data.get("item"));
			sk.getScreen().type(Keys.TAB);
			r.delay(1000);

			sk.getScreen().type(data.get("start_date"));
			sk.getScreen().type(Keys.TAB);
			r.delay(1000);

			sk.getScreen().type(data.get("end_date"));
			sk.getScreen().type(Keys.TAB);
			r.delay(1000);

			// GUARDAR CAMBIOS

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

	}

}
