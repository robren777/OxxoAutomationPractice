/**
 * Automatiza las funciones relacionadas a la matriz "generacion de tran types"
 */

package RMS.util.PageObject;

import java.awt.Robot;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import org.sikuli.hotkey.Keys;
import org.sikuli.script.FindFailed;
import org.sikuli.script.Key;
import org.sikuli.script.Pattern;

import modelo.TestCase;
import utils.SikuliUtil;
import utils.selenium.SeleniumUtil;
import utils.sql.SQLUtil;

public class TranTypes extends BaseRMS {

	public TranTypes(SikuliUtil sk, SeleniumUtil u, Robot r, Pattern pathimg, ArrayList<String> ListImage,
			HashMap<String, Pattern> ImgPatter, ArrayList<Pattern> patterns, File Fol, String status, SQLUtil dbRms,
			HashMap<String, String> data, TestCase testCase) {

		super(sk, u, r, pathimg, ListImage, ImgPatter, patterns, Fol, status, dbRms, data, testCase);

	}

	/**
	 * Selecciona en la pantalla rtkstrt(menu principal) la opcion: Items > Items
	 */
	public void menuItemItems() {
		seleccionarMenuRtkstrt("rtkstrt_items", "rtkstrt_items_items");
	}

	/**
	 * Selecciona Action > edit, coloca el numero de item y manda a la siguiente
	 * pantalla
	 */
	public void editItem(String item) {
		super.action();

		try {
			sk.getScreen().hover(ImgPatter.get("itemfind_action_edititem"));
			super.screenshot();
			sk.getScreen().click();

			sk.getScreen().type(item);
			System.out.println(item);
			sk.getScreen().type(Keys.TAB);
			r.delay(1000);
			super.screenshot();

			sk.getScreen().wait(ImgPatter.get("btn_ok_slctd")).click();
			sk.getScreen().wait(ImgPatter.get("itemmaster_delete"));
			super.screenshot();

		} catch (FindFailed e) {
			e.printStackTrace();
		}

	}

	/**
	 * En la pantalla itemmaster selecciona Locations y agrega la tienda a la que se
	 * ligara el articulo
	 * 
	 * @param store
	 */
	public void locations(String store) {
		try {
			sk.getScreen().wait(ImgPatter.get("itemmaster_locations")).doubleClick();
			r.delay(3000);
			sk.getScreen().wait(ImgPatter.get("itemloc_grouptype")).click();

			// ciclo para que en el desplegable Grouptype siga buscando la opcion Store
			while (true) {
				if (sk.getScreen().exists(ImgPatter.get("itemloc_grouptype_store")) == null) {
					sk.getScreen().wait(ImgPatter.get("itemloc_grouptype_up")).click();

				} else {
					sk.getScreen().wait(ImgPatter.get("itemloc_grouptype_store")).click();
					break;
				}
			}

			r.delay(2000);
			sk.getScreen().type(Keys.TAB);
			r.delay(2000);
			sk.getScreen().type(store);
			r.delay(2000);
			sk.getScreen().type(Keys.TAB);
			r.delay(3000);
			sk.getScreen().type(Keys.ENTER);
			r.delay(7000);
			if (sk.getScreen().exists(ImgPatter.get("itemloc_reteknote")) != null) {
				super.screenshot();
			} else {
				System.out.println("La creacion fall�");
				return;
			}

			sk.getScreen().wait(ImgPatter.get("itemloc_reteknote_ok")).click();

		} catch (FindFailed e) {
			e.printStackTrace();
		}

	}

	/**
	 * Modificar la descripcion corta y larga del item
	 * 
	 * @param store
	 */
	public void modificarDescripcion(String store) {
		try {
			sk.getScreen().wait(ImgPatter.get("itemmaster_locations")).doubleClick();
			r.delay(3000);
			sk.getScreen().wait(ImgPatter.get("itemloc_grouptype")).click();

			// ciclo para que en el desplegable Grouptype siga buscando la opcion Store
			while (true) {
				if (sk.getScreen().exists(ImgPatter.get("itemloc_grouptype_store")) == null) {
					sk.getScreen().wait(ImgPatter.get("itemloc_grouptype_up")).click();

				} else {
					sk.getScreen().wait(ImgPatter.get("itemloc_grouptype_store")).click();
					break;
				}
			}

			r.delay(2000);
			sk.getScreen().type(Keys.TAB);
			r.delay(2000);
			sk.getScreen().type(store);
			r.delay(2000);
			sk.getScreen().type(Keys.TAB);
			r.delay(3000);
			sk.getScreen().type(Keys.ENTER);
			r.delay(3000);
			sk.getScreen().wait(ImgPatter.get("itemloc_desc")).right(80).click();
			r.delay(3000);
			sk.getScreen().type(Keys.DELETE);
			r.delay(3000);
			sk.getScreen().type(data.get("desc"));
			r.delay(7000);
			sk.getScreen().wait(ImgPatter.get("itemloc_apply")).click();
			r.delay(7000);
			if (sk.getScreen().exists(ImgPatter.get("itemloc_reteknote")) != null) {
				super.screenshot();
			} else {
				System.out.println("La creacion fall�");
				return;
			}

			sk.getScreen().wait(ImgPatter.get("itemloc_reteknote_ok")).click();

		} catch (FindFailed e) {
			e.printStackTrace();
		}

	}

	/**
	 * Se coloca la informacion del articulo que se ligara a la tienda
	 * 
	 * @param store tienda a la que se le ligara el articulo
	 */
	public void ligarArticulo(String store) {
		try {
			sk.getScreen().wait(ImgPatter.get("itemloc_grouptype")).click();

			while (true) {
				if (sk.getScreen().exists(ImgPatter.get("itemloc_grouptype_store2")) == null) {
					sk.getScreen().wait(ImgPatter.get("itemloc_grouptype_up")).click();

				} else {
					sk.getScreen().wait(ImgPatter.get("itemloc_grouptype_store2")).click();
					break;
				}
			}

			sk.getScreen().type(Keys.TAB);
			r.delay(1000);

			sk.getScreen().type(store);
			r.delay(1000);

			sk.getScreen().wait(ImgPatter.get("btn_ok")).click();

			super.screenshot();

			// OK

		} catch (FindFailed e) {
			e.printStackTrace();
		}
	}

	/**
	 * Selecciona en la pantalla rtkstrt(menu principal) la opcion: Pricing > Retail
	 * Cost Change
	 */
	public void menuPricingRetail() {
		seleccionarMenuRtkstrt("pricing", "rtkstrt_retail_cost_change");

	}

	/**
	 * En pcfind selecciona Action > New y Origin > Cost
	 */
	public void costoNuevo() {
		try {
			sk.getScreen().wait(ImgPatter.get("action")).click();
			sk.getScreen().hover(ImgPatter.get("pcfind_action_new"));
			super.screenshot();
			sk.getScreen().click();

			sk.getScreen().wait(ImgPatter.get("pcfind_origin")).click();
			sk.getScreen().wait(ImgPatter.get("pcfind_origin_cost")).click();

			sk.getScreen().hover(ImgPatter.get("btn_ok"));
			super.screenshot();
			sk.getScreen().click();

		} catch (FindFailed e) {
			e.printStackTrace();
		}

	}

	/**
	 * En suppsku coloca la informacion para hacer el cambio de costo
	 * 
	 * @return id del cambio de costo
	 */
	public String cambiarCosto() {
		String costChangeId = "";

		try {
			sk.getScreen().wait(ImgPatter.get("suppsku_costchange")).right(80).click();
			r.delay(1000);

			costChangeId = copyPaste();

			sk.getScreen().type(Keys.TAB);
			sk.getScreen().type(data.get("price_change_description"));

			sk.getScreen().type(Keys.TAB);
			r.delay(1000);
			sk.getScreen().type(data.get("supplier"));
			sk.getScreen().type(Keys.TAB);
			r.delay(1000);
			sk.getScreen().type(data.get("item"));
			sk.getScreen().type(Keys.TAB);
			r.delay(1000);
			sk.getScreen().type(data.get("country"));
			sk.getScreen().type(Keys.TAB);
			r.delay(1000);
			sk.getScreen().type(data.get("price_change_reason"));
			sk.getScreen().type(Keys.TAB);
			r.delay(1000);
			sk.getScreen().type(data.get("effective_date"));
			sk.getScreen().type(Keys.TAB);
			r.delay(1000);
			super.screenshot();

			sk.getScreen().type(Keys.ENTER);
			sk.getScreen().wait(ImgPatter.get("suppsku_locations")).click();

			r.delay(3000);

			sk.getScreen().wait(ImgPatter.get("suppsku_location"));

			sk.getScreen().wait(ImgPatter.get("suppsku_" + data.get("tipo"))).click();
			sk.getScreen().type(Keys.TAB);

			sk.getScreen().type(data.get("amount"));
			r.delay(1000);

			sk.getScreen().wait(ImgPatter.get("suppsku_loctype")).click();

			// ciclo para que siga buscando la opcion Store dentro del desplegable
			while (true) {
				if (sk.getScreen().exists(ImgPatter.get("suppsku_loctype_store")) == null) {
					sk.getScreen().wait(ImgPatter.get("suppsku_loctype_up")).click();

				} else {
					sk.getScreen().wait(ImgPatter.get("suppsku_loctype_store")).click();
					break;
				}
			}

			r.delay(1000);
			sk.getScreen().type(Keys.TAB);
			sk.getScreen().type(data.get("store"));
			r.delay(1000);
			sk.getScreen().type(Keys.TAB);

			super.screenshot();

			sk.getScreen().type(Keys.ENTER);

			sk.getScreen().wait(ImgPatter.get("suppsku_ok")).click();

		} catch (FindFailed e) {
			e.printStackTrace();
		}

		return costChangeId;

	}

	/**
	 * Da submit a los cambios
	 */
	public void submitCambio() {
		try {
			sk.getScreen().wait(ImgPatter.get("suppsku_status"));

			sk.getScreen().wait(ImgPatter.get("mn_options")).click();
			this.screenshot();

			sk.getScreen().wait(ImgPatter.get("smn_sbmt")).click();

			sk.getScreen().hover(ImgPatter.get("btn_mdl_yes"));
			this.screenshot();
			sk.getScreen().click();

			sk.getScreen().wait(ImgPatter.get("suppsku_status_submitted"));
			super.screenshot();

		} catch (Exception e) {
			System.out.println(e.toString());
		}
	}

	/**
	 * Aprueba los cambios
	 */
	public void aprobarCambio() {
		try {
			sk.getScreen().wait(ImgPatter.get("mn_options_2")).click();
			sk.getScreen().hover(ImgPatter.get("itm_approve"));
			super.screenshot();
			sk.getScreen().click();

			sk.getScreen().hover(ImgPatter.get("btn_mdl_yes1"));
			super.screenshot();
			sk.getScreen().click();

			sk.getScreen().wait(ImgPatter.get("btn_no_approve"));

			sk.getScreen().wait(ImgPatter.get("btn_ok_slctd"));

			// OK

		} catch (Exception e) {
			System.out.println(e.toString());
		}

	}

	/**
	 * En pcfind selecciona Action > New, Origin > Retail, Item Type = Single Item y
	 * captura la informacion del item al que se le cambiara el precio
	 */
	public void precioNuevo() {
		super.action();

		try {
			sk.getScreen().wait(ImgPatter.get("pcfind_action_new")).click();
			sk.getScreen().wait(ImgPatter.get("pcfind_origin")).click();
			sk.getScreen().wait(ImgPatter.get("pcfind_origin_retail")).click();

			super.screenshot();

			sk.getScreen().wait(ImgPatter.get("pcfind_itemtype")).click();
			sk.getScreen().wait(ImgPatter.get("pcfind_itemtype_single")).click();
			r.delay(1000);
			sk.getScreen().type(Keys.TAB);
			r.delay(1000);
			sk.getScreen().type(data.get("item"));
			r.delay(1000);
			sk.getScreen().type(Keys.TAB);
			r.delay(1000);

			super.screenshot();

			sk.getScreen().wait(ImgPatter.get("btn_ok_slctd")).click();

		} catch (FindFailed e) {
			e.printStackTrace();
		}
	}

	/**
	 * En pcskust captura la descripcion y razon de cambio de precio
	 */
	public void agregarZonaPrecio() {
		try {
			sk.getScreen().type(data.get("price_change_description"));
			sk.getScreen().wait(ImgPatter.get("pcskust_reason")).click();
			r.delay(1000);
			sk.getScreen().type(data.get("price_change_reason"));
			r.delay(1000);
			sk.getScreen().wait(ImgPatter.get("pcskust_locations")).click();

			r.delay(1000);
			super.screenshot();

		} catch (FindFailed e) {
			e.printStackTrace();
		}
	}

	/**
	 * En pcstore captura zoneId, va a la pantalla pcskust y captura el tipo de
	 * cambio de precio, monto y aplica los cambios
	 */
	public void capturarCambioPrecio() {
		try {
			sk.getScreen().type(Keys.TAB);
			sk.getScreen().type(data.get("zone_id"));
			r.delay(1000);
			sk.getScreen().type(Keys.TAB);
			r.delay(1000);
			sk.getScreen().wait(ImgPatter.get("btn_apply")).click();
			sk.getScreen().wait(ImgPatter.get("btn_ok")).click();
			sk.getScreen().wait(ImgPatter.get("pcskust_type")).click();
			sk.getScreen().wait(ImgPatter.get("pcskust_type_amount")).click();
			sk.getScreen().wait(ImgPatter.get("pcskust_amount")).click();
			r.delay(1000);
			sk.getScreen().type(data.get("amount"));
			sk.getScreen().type(Keys.TAB);
			sk.getScreen().type(Keys.UP);

			r.delay(1000);
			super.screenshot();

			sk.getScreen().wait(ImgPatter.get("pcskust_apply")).click();

		} catch (FindFailed e) {
			e.printStackTrace();
		}
	}

	/**
	 * En pcfind selecciona Action > New, Origin > Retail, Item Type = Single Item y
	 * captura la informacion del item al que se le cambiara el precio (Metodo para
	 * MultiUnit)
	 */
	public void precioNuevoMU() {
		try {
			// se pide en un mismo caso de prueba que cambie de precio normal y multiunit
			// se refresca la pantalla pcfind
			sk.getScreen().wait(ImgPatter.get("pcfind_refresh")).doubleClick();

			super.action();

			sk.getScreen().wait(ImgPatter.get("pcfind_action_new")).click();
			sk.getScreen().wait(ImgPatter.get("pcfind_origin")).click();
			sk.getScreen().wait(ImgPatter.get("pcfind_origin_retail")).click();

			super.screenshot();

			sk.getScreen().wait(ImgPatter.get("pcfind_itemtype")).click();
			sk.getScreen().wait(ImgPatter.get("pcfind_itemtype_single")).click();
			r.delay(1000);
			sk.getScreen().type(Keys.TAB);
			r.delay(1000);
			sk.getScreen().type(data.get("item_mu"));
			r.delay(1000);
			sk.getScreen().type(Keys.TAB);
			r.delay(1000);

			super.screenshot();

			sk.getScreen().wait(ImgPatter.get("btn_ok_slctd")).click();

		} catch (FindFailed e) {
			e.printStackTrace();
		}

	}

	/**
	 * En pcskust captura la descripcion y razon de cambio de precio (Metodo para
	 * MultiUnit)
	 */
	public void agregarZonaPrecioMU() {
		try {
			sk.getScreen().type(data.get("price_change_description_mu"));
			sk.getScreen().wait(ImgPatter.get("pcskust_reason")).click();
			r.delay(1000);
			sk.getScreen().type(data.get("price_change_reason_mu"));
			r.delay(1000);
			sk.getScreen().wait(ImgPatter.get("pcskust_locations")).click();

			r.delay(1000);
			super.screenshot();

		} catch (FindFailed e) {
			e.printStackTrace();
		}
	}

	/**
	 * En pcstore captura zoneId, va a la pantalla pcskust y captura el tipo de
	 * cambio de precio, monto y aplica los cambios (Metodo para MultiUnit)
	 */
	public void capturarCambioPrecioMU() {
		try {
			sk.getScreen().type(Keys.TAB);
			sk.getScreen().type(data.get("zone_id_mu"));
			r.delay(1000);
			sk.getScreen().type(Keys.TAB);
			r.delay(1000);
			sk.getScreen().wait(ImgPatter.get("btn_apply")).click();
			sk.getScreen().wait(ImgPatter.get("btn_ok")).click();
			sk.getScreen().wait(ImgPatter.get("pcskust_type")).click();
			sk.getScreen().wait(ImgPatter.get("pcskust_type_amount")).click();
			sk.getScreen().wait(ImgPatter.get("pcskust_amount")).click();
			r.delay(1000);
			sk.getScreen().type(data.get("amount_mu"));
			sk.getScreen().type(Keys.TAB);
			sk.getScreen().type(Keys.UP);

			r.delay(1000);
			sk.getScreen().type(Keys.TAB);
			sk.getScreen().type(data.get("multiunits"));

			r.delay(1000);
			sk.getScreen().type(Keys.TAB);
			sk.getScreen().type(data.get("multiretail"));

			sk.getScreen().type(Keys.TAB);

			r.delay(1000);

			super.screenshot();

			sk.getScreen().wait(ImgPatter.get("pcskust_apply")).click();

		} catch (FindFailed e) {
			e.printStackTrace();
		}
	}

	/**
	 * Selecciona en la pantalla rtkstrt(menu principal) la opcion: Control >
	 * XXREO_Control de operaciones > XXREO-Reenviar Cat�logos por Tienda
	 * 
	 * @throws FindFailed
	 */
	public void menuControlReenviar() throws FindFailed {
		seleccionarMenuRtkstrt("rtkstrt_control", "rtkstrt_control_xxreo_control_operaciones");

		try {
			sk.getScreen().wait(ImgPatter.get("rtkstrt_reenviar_catalogos_por_tienda")).click(); // REENVIAR CATALOGO
			this.screenshot();
			sk.getScreen().doubleClick();
			r.delay(1000);

			sk.getScreen().wait(ImgPatter.get("btn_ok_slctd")).click();
			r.delay(8000);
			super.screenshot();

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

	}

	/**
	 * Llena el el formulario de la pantalla Reenviar Catalogo por tienda
	 * 
	 * @throws FindFailed
	 */
	public void formularioReenviar() throws FindFailed {
		r.delay(5000);
		try {

			sk.getScreen().find(ImgPatter.get("rct_location_type")).click();
			r.delay(1500);
			sk.getScreen().wait(ImgPatter.get("rct_store")).click();
			r.delay(1500);
			sk.getScreen().type(Key.TAB);
			r.delay(1500);
			sk.getScreen().type(data.get("store"));
			r.delay(1500);
			sk.getScreen().type(Key.TAB);
			sk.getScreen().type(Key.TAB);
			r.delay(1500);
			sk.getScreen().type(Key.UP);
			r.delay(1500);
			sk.getScreen().type(Key.UP);
			r.delay(1500);
			sk.getScreen().type(Key.TAB);
			r.delay(1500);
			sk.getScreen().type(data.get("item"));
			r.delay(1500);
			sk.getScreen().find(ImgPatter.get(data.get("opcion_reenvio"))).click();
			r.delay(3000);
			sk.getScreen().find(ImgPatter.get("rct_btn_reenviar")).click();
			r.delay(15000);
			sk.getScreen().find(ImgPatter.get("rct_btn_ok")).click();
			r.delay(5000);

			super.screenshot();

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

	}

}
