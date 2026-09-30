/**
 * Automatiza las funciones relacionadas a los items 
 */

package RMS.util.PageObject;

import java.awt.Robot;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import org.sikuli.hotkey.Keys;
import org.sikuli.script.App;
import org.sikuli.script.FindFailed;
import org.sikuli.script.Key;
import org.sikuli.script.Pattern;
import org.sikuli.script.ScreenImage;

import config.Constants;
import modelo.TestCase;
import utils.SikuliUtil;
import utils.password.PasswordUtil;
import utils.selenium.SeleniumUtil;
import utils.sql.SQLUtil;

public class Items extends BaseRMS {
	
	int numeroEvidencia = 0;

	public Items(SikuliUtil sk,
			SeleniumUtil u,
			Robot r,
			Pattern pathimg,
			ArrayList<String> ListImage,
			HashMap<String, Pattern> ImgPatter,
			ArrayList<Pattern> patterns,
			File Fol,
			String status,
			SQLUtil	dbRms,
			HashMap<String, String> data, TestCase testCase) {
		
		super(sk, u, r, pathimg, ListImage, ImgPatter, patterns, Fol, status, dbRms, data, testCase);
		
	}

	/**
	 * Selecciona en la pantalla rtkstrt(menu principal) la opcion:
	 * Item > Items
	 */
	public void menuItemItems() {
		seleccionarMenuRtkstrt("rtkstrt_items", "rtkstrt_items_items");
	}
	 
	/**
	 * Selecciona Action > edit, coloca el numero de item y manda a la siguiente pantalla
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
			sk.getScreen().wait(ImgPatter.get("itemmaster_delete"));
			super.screenshot();
			
		} catch (FindFailed e) {
			e.printStackTrace();
		}
		
	}	
	

	private void OpenBrowser(String URL) {
		try {
//				System.out.println(data.get("rms_url"));
			u.get(URL);
		} catch (Exception e) {
			// TODO: handle exception
		}
	}


	/**
	 * Selecciona Action > edit, coloca el numero de item y manda a la siguiente pantalla
	 */
	public void Login_En_RMS() throws FindFailed {
		ImgPatter = GetPatternsImg(Fol);
		OpenBrowser(data.get("rms_url"));
		r.delay(25000);
		sk.getScreen().type(data.get("rms_user"));
		r.delay(2000);
		sk.getScreen().type(Key.TAB);
		r.delay(1000);
		sk.getScreen().type(PasswordUtil.decryptPassword(data.get("rms_ps")) + Key.TAB + Key.TAB);
		testCase.addScreenShotCurrentStep("ingresar credenciales");
		sk.getScreen().type(Key.ENTER);
		r.delay(15000);
//			sk.getScreen().capture().save("img.png");
	}

	public void navItems() throws FindFailed {
		r.delay(5000);
		sk.getScreen().wait(ImgPatter.get("lbl_items_1"), 15).click();
		r.delay(2000);
		sk.getScreen().wait(ImgPatter.get("lbl_items_2"), 15).doubleClick();
		screenshot();
	}

	public void EditItem() throws FindFailed {

		r.delay(5000);
		sk.getScreen().wait(ImgPatter.get("slct_action"), 15).click();
		r.delay(1000);
		sk.getScreen().wait(ImgPatter.get("slct_editItem"), 15).click();
		r.delay(1000);
		sk.getScreen().type(data.get("Item") + Key.TAB);
		r.delay(1000);
		screenshot();
		sk.getScreen().type(Key.ENTER);
	}

	public void clicSuppliers() throws FindFailed {

		r.delay(3000);
		sk.getScreen().wait(ImgPatter.get("chck_suppliers"), 15).click();
		r.delay(3000);
		screenshot();

	}

	public void appliSupplier() throws FindFailed {

		r.delay(3000);
		sk.getScreen().wait(ImgPatter.get("btn_add"), 15).click();
		r.delay(1000);
		sk.getScreen().wait(ImgPatter.get("itm_menu_supplier"), 15).click();
		r.delay(5000);
		sk.getScreen().wait(ImgPatter.get("itm_opcion_supplier"), 15).doubleClick();
		r.delay(3000);
		sk.getScreen().type(Key.TAB);
		r.delay(1000);
		sk.getScreen().type(Key.TAB);
		r.delay(1000);
		sk.getScreen().type(Key.TAB);
		r.delay(1000);
		sk.getScreen().type(data.get("flete"));
		r.delay(1000);
		sk.getScreen().wait(ImgPatter.get("btn_apply_suplier"), 15).click();
		r.delay(3000);
		screenshot();

	}

	public void clicOriginCountry() throws FindFailed {

		r.delay(3000);
		sk.getScreen().wait(ImgPatter.get("itm_supplier_origin_country_red"), 15).click();
		r.delay(3000);
		screenshot();

	}

	public void selectOriginContry() throws FindFailed {

		r.delay(3000);
		sk.getScreen().type(data.get("OriginContry") + Key.TAB);
		r.delay(1000);
		sk.getScreen().type(Key.ENTER);
		r.delay(1000);
		sk.getScreen().type(Key.ENTER);
		r.delay(3000);
		// String btn_name = sk.getScreen().wait(ImgPatter.get("btn_create"), 15).getText();
		sk.getScreen().type(data.get("UnitCost"));
		r.delay(3000);
		sk.getScreen().find(ImgPatter.get("inner_Size")).below(15).click();
		r.delay(1000);
		sk.getScreen().type(data.get("innerSize") + Key.TAB);
		r.delay(1000);
		sk.getScreen().type(data.get("caseSize") + Key.TAB);
		r.delay(1000);
		sk.getScreen().type(data.get("Tier") + Key.TAB);
		r.delay(1000);
		sk.getScreen().type(data.get("Heigth") + Key.TAB);
		r.delay(1000);
		// System.out.println(btn_name);
		screenshot();
		sk.getScreen().wait(ImgPatter.get("btn_ok"), 15).click();
		r.delay(3000);

	}

	public void clic_btnOK() throws FindFailed {

		r.delay(2000);
		sk.getScreen().wait(ImgPatter.get("btn_ok"), 15).click();
		r.delay(2000);
		screenshot();

	}

	public void clic_btnOK_itmaster() throws FindFailed {

		r.delay(2000);
		sk.getScreen().wait(ImgPatter.get("btn_ok_itmaster"), 15).click();
		r.delay(2000);
		screenshot();

	}

	/**
	 * En la pantalla itemmaster selecciona VAT Maintenance y agregar nuevo impuesto
	 * 
	 * @param store
	 */
	public void vatMaintenance(String store) {
		try {
			sk.getScreen().wait(ImgPatter.get("imw_locations")).doubleClick();
			r.delay(5000);

			super.screenshot();
			
			sk.getScreen().wait(ImgPatter.get("imw_groupType")).click();
			r.delay(1000);
			sk.getScreen().type(data.get("groupType"));
			r.delay(1000);

			sk.getScreen().wait(ImgPatter.get("vatitem_vatregion")).click();

			r.delay(2000);
			sk.getScreen().type(data.get("vatRegion"));
			r.delay(2000);
			sk.getScreen().type(Keys.TAB);
			r.delay(2000);
			sk.getScreen().wait(ImgPatter.get("vatitem_addvat")).click();
			r.delay(2000);
			sk.getScreen().type(data.get("vatCode"));
			r.delay(2000);
			sk.getScreen().type(Keys.TAB);
			r.delay(2000);

			while (true) {
				if (sk.getScreen().exists(ImgPatter.get("itemloc_grouptype_store")) == null) {
					sk.getScreen().wait(ImgPatter.get("itemloc_grouptype_up")).click();

				} else {
					sk.getScreen().wait(ImgPatter.get("itemloc_grouptype_store")).click();
					break;
				}
			}

			sk.getScreen().type(store);
			r.delay(2000);
			sk.getScreen().type(Keys.TAB);
			r.delay(3000);

			sk.getScreen().type(Keys.ENTER);
			r.delay(1000);
			sk.getScreen().type(data.get("store"));
			r.delay(1000);
			sk.getScreen().type(Keys.TAB);
			r.delay(1000);
			sk.getScreen().type(Keys.ENTER);
			r.delay(3000);
			sk.getScreen().wait(ImgPatter.get("imw_btnUOK")).click();
			r.delay(3000);
			
			sk.getScreen().wait(ImgPatter.get("imw_locationLabel")).click();
			r.delay(1000);
			sk.getScreen().type(data.get("groupType"));
			r.delay(1000);
			sk.getScreen().type(Keys.ENTER);
			r.delay(1000);
			sk.getScreen().type(data.get("store"));
			r.delay(1000);
			sk.getScreen().type(Keys.TAB);
			r.delay(1000);
			sk.getScreen().type(Keys.ENTER);
			sk.getScreen().type(data.get("item"));
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
	 * Agrega la subcategoria > segmento > subsegmento del nuevo item
	 */
	public void altaItem() {
		super.action();
		
		try {	
			sk.getScreen().hover(ImgPatter.get("itemfind_action_newitem"));
			this.screenshot();
			sk.getScreen().click();
			
			sk.getScreen().type(data.get("subcategoria"));
			sk.getScreen().type(Keys.TAB);
			r.delay(1000);
			
			sk.getScreen().type(data.get("segmento"));
			sk.getScreen().type(Keys.TAB);
			r.delay(1000);
			
			sk.getScreen().type(data.get("subsegmento"));
			sk.getScreen().type(Keys.TAB);			
			super.screenshot();
			
			sk.getScreen().wait(ImgPatter.get("btn_ok")).click();
			
			sk.getScreen().wait(ImgPatter.get("itemmaster_title"));
			super.screenshot();
		
		} catch (FindFailed e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Captura la informacion para el nuevo item.
	 */
	public void capturarInfoNuevoItem() {
		try {
			sk.getScreen().wait(ImgPatter.get("itemmaster_itemtype")).click();
			String itemType = "itemmaster_itemtype_" + data.get("item_type").toLowerCase();
						
			while (true) {
				if (sk.getScreen().exists(ImgPatter.get(itemType)) == null) {
					sk.getScreen().wait(ImgPatter.get("itemmaster_itemtype_up")).click();
					
				} else {
					sk.getScreen().wait(ImgPatter.get(itemType)).click();
					break;
				}
			}
			
			sk.getScreen().wait(ImgPatter.get("itemmaster_item")).click();
			sk.getScreen().type(data.get("item"));
			sk.getScreen().type(Keys.TAB);
			r.delay(1000);
			
			sk.getScreen().type(data.get("descripcion"));
			sk.getScreen().type(Keys.TAB);
			
			// TERMINAR CHECAR ERRORES
			
		} catch (Exception e) {
			e.printStackTrace();
		}	
		
	}
	
	/**
	 * En la ventana Item Maintenance Window da click en Suppliers
	 */
	public void itemSupplier() {
		try {
			sk.getScreen().hover(ImgPatter.get("itemmaster_suppliers"));
			super.screenshot();
			sk.getScreen().click();
			
		} catch (FindFailed e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Dentro de Item-Supplier agrega la info del supplier
	 */
	public void agregarSupplier() {
		try {
			sk.getScreen().wait(ImgPatter.get("itemsupp_add")).click();
			sk.getScreen().type(data.get("supplier"));
			sk.getScreen().type(Keys.TAB);					
			r.delay(1000);				
			super.screenshot();
			sk.getScreen().type(data.get("itemsupp_apply"));
			


			sk.getScreen().wait(ImgPatter.get("itemloc_reteknote_ok")).click();

		} catch (FindFailed e) {
			e.printStackTrace();
		}

	}

		
	/**
	 * Elimina un item
	 */
	public void cancelarItem() {
		try {						
			sk.getScreen().wait(ImgPatter.get("itemmaster_delete")).click();
			sk.getScreen().wait(ImgPatter.get("itemmaster_retekyesno")).click();
			super.screenshot();
			
			// TERMINAR CON LA CONFIRMACION DE ELIMINAR Y AGREGAR LA CAPTURA DE PANTALLA
			
		} catch (FindFailed e) {
			e.printStackTrace();
		}
	}

	public void screenshot() {
		org.sikuli.script.Region window = App.focusedWindow(); // obtener la ventana con el focus
		ScreenImage image = sk.getScreen().capture(window); // toma la captura
		String savePath = Constants.BASE_PATH + "\\" + Constants.OUTPUT_IMAGES;
		String filename = "evidence" + numeroEvidencia;
		image.save(savePath, filename);
		String imgPath = Constants.OUTPUT_IMAGES + "\\" + filename + ".png";
		testCase.addScreenShotCurrentStep(imgPath, true);
		numeroEvidencia++;

	}

	public void close_RMS() throws FindFailed {
		sk.getScreen().wait(ImgPatter.get("btn_close_itm"), 15).highlight(1, "red").click();
		r.delay(1000);
		sk.getScreen().wait(ImgPatter.get("mn_action"), 5).highlight(1, "red").click();
		sk.getScreen().wait(ImgPatter.get("smn_close"), 5).highlight(1, "red").click();

	}

	public void CloseBrowser() {
		try {
			u.close();
		} catch (Exception e) {
			// TODO: handle exception
		}

	}
	
}

