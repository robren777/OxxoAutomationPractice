/**
 * Automatiza las funciones relacionadas a las promociones
 */

package RMS.util.PageObject;

import java.awt.Robot;
import java.io.File;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONObject;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.sikuli.script.FindFailed;
import org.sikuli.script.Key;
import org.sikuli.script.Pattern;

import RMS.util.Queries;
import modelo.TestCase;
import utils.JsonUtil;
import utils.SikuliUtil;
import utils.password.PasswordUtil;
import utils.selenium.SeleniumUtil;
import utils.sql.SQLResult;
import utils.sql.SQLUtil;

public class PromocionesComplejas extends BaseRMS {

	String fechaInicio = "";
	String fechaFin = "";

	String st;

	public PromocionesComplejas(SikuliUtil sk, SeleniumUtil u, Robot r, Pattern pathimg, ArrayList<String> ListImage,
			HashMap<String, Pattern> ImgPatter, ArrayList<Pattern> patterns, File Fol, String status, SQLUtil dbRms,
			HashMap<String, String> data, TestCase testCase) {

		super(sk, u, r, pathimg, ListImage, ImgPatter, patterns, Fol, status, dbRms, data, testCase);

	}

	/**
	 * Selecciona en la pantalla rtkstrt(menu principal) la opcion: Pricing >
	 * Promotions
	 */
	public void menuPricingPromotions() {
		// mandas como argumento el nombre de la imagen que vas a utilizar
		seleccionarMenuRtkstrt("pricing", "promotions");
	}

	/**
	 * Selecciona la opcion para crear una promocion nueva
	 * 
	 * @return el folio de la promocion
	 */
	public String CreateNewPromo() {
		r.delay(5000);
		String promotion = "";

		try {
			action();
			sk.getScreen().wait(ImgPatter.get("neww"), 15).click();
			sk.getScreen().wait(ImgPatter.get("btn_ok"), 15).click();
			r.delay(5000);

			sk.getScreen().find(ImgPatter.get("box_promo")).right(80).click();
			r.delay(1000);

			promotion = copyPaste(); // copia el folio de la promocion

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		return promotion;
	}

	/**
	 * Llena los datos de la promocion
	 */
	public void confPromoCompleja() {
		try {

			this.configurePROMHEADRMS(); // ingresa la info de la promo

			// si en el data provider existe la col fecha_inicio o fecha_fin
			// y tiene datos, le configura fechas a la promo
			if (this.configurarFecha()) {
				this.colocarFechas();
			}

			// si en el data provider existe la col hora_inicio y hora_fin
			// y tiene datos, le configura hora y dias a la promo
			if (this.configurarHora()) {
				this.configurarDiaYHora();
			}

			super.screenshot();

			this.obtenerFechas();

			this.ConfigurepromstRMS();

			// MENU
			sk.getScreen().wait(ImgPatter.get("mn_options"), 5).click();
			sk.getScreen().wait(ImgPatter.get("smn_ai"), 5).hover();
			sk.getScreen().wait(ImgPatter.get("smn_mu"), 5).click();

			this.addItems(this.data.get("tipo_descuento"));

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	/**
	 * Llena los datos de la promocion
	 */
	public void confPromoComplejaPromopack() {
		try {

			this.configurePROMHEADRMS(); // ingresa la info de la promo

			// si en el data provider existe la col fecha_inicio o fecha_fin
			// y tiene datos, le configura fechas a la promo
			if (this.configurarFecha()) {
				this.colocarFechas();
			}

			// si en el data provider existe la col hora_inicio y hora_fin
			// y tiene datos, le configura hora y dias a la promo
			if (this.configurarHora()) {
				this.configurarDiaYHora();
			}

			super.screenshot();

			this.obtenerFechas();

			this.ConfigurepromstRMS();

			// MENU
			sk.getScreen().wait(ImgPatter.get("mn_options"), 5).click();
			sk.getScreen().wait(ImgPatter.get("smn_ai"), 5).hover();
			sk.getScreen().wait(ImgPatter.get("smn_mu"), 5).click();

			this.addItems(this.data.get("tipo_descuento"));

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	/**
	 * Da submit a la promocion
	 */
	public void submitPromo() {
		try {
			sk.getScreen().wait(ImgPatter.get("mn_options")).click();
			super.screenshot();
			sk.getScreen().wait(ImgPatter.get("smn_sbmt")).click();
			r.delay(10000);
			sk.getScreen().wait(ImgPatter.get("btn_No")).click();
			r.delay(5000);
			// sk.getScreen().wait(ImgPatter.get("btn_No")).click();
			sk.getScreen().wait(ImgPatter.get("btn_mdl_yes")).click();
			sk.getScreen().wait(ImgPatter.get("btn_ok_slctd")).click();
			// sk.getScreen().wait(ImgPatter.get("btn_close")).click();
			// sk.getScreen().wait(ImgPatter.get("mn_action")).click();
			// sk.getScreen().wait(ImgPatter.get("smn_close")).click();
			r.delay(3000);
		} catch (Exception e) {
			System.out.println(e.toString());
		}
	}

	/**
	 * Cierra RMS
	 */
	public void closeRMS() {
		try {
			sk.getScreen().wait(ImgPatter.get("btn_close")).click();
			sk.getScreen().wait(ImgPatter.get("mn_action")).click();
			sk.getScreen().wait(ImgPatter.get("smn_close")).click();
			r.delay(3000);
		} catch (Exception e) {
			System.out.println(e.toString());
		}
	}

	/**
	 * Ingresa la informaci�n de la promo en la ventana de la PROMHEAD
	 */
	public void configurePROMHEADRMS() {
		try {
			r.delay(1000);
			sk.getScreen().type(Key.TAB);
			r.delay(1000);
			sk.getScreen().type(data.get("nombre_promo"));
			r.delay(1000);
			sk.getScreen().type(Key.TAB);
			r.delay(1000);
			sk.getScreen().type(data.get("descripcion_promo"));
			r.delay(1000);
			sk.getScreen().type(Key.TAB);
			r.delay(1000);
			sk.getScreen().type(data.get("event"));
			r.delay(1000);
			sk.getScreen().type(Key.TAB);
			r.delay(1000);

			LocalDate diaActual = LocalDate.now();
			diaActual = diaActual.plusDays(1);
			DateTimeFormatter formatN = DateTimeFormatter.ofPattern("ddMMyy");
			String diaActualFormat = diaActual.format(formatN);

			sk.getScreen().wait(ImgPatter.get("pricing_startDate"), 20).right(10).click();
			sk.getScreen().type(diaActualFormat);
			r.delay(1000);

			String promoType = data.get("promType");
			
			switch (promoType) {
			case "S":
				sk.getScreen().wait(ImgPatter.get("box_promoType"),20).right(10).click();
				r.delay(1000);
				sk.getScreen().type(data.get("promType") + Key.TAB);
				r.delay(1000);
				break;
			case "E":
				sk.getScreen().wait(ImgPatter.get("box_promoType"),20).right(10).click();
				r.delay(1000);
				sk.getScreen().type(data.get("promType") + Key.TAB);
				r.delay(1000);
				
				sk.getScreen().wait(ImgPatter.get("msgPOS"),20).right(10).click();
				r.delay(1000);
				sk.getScreen().type(data.get("MsgPOS") + Key.TAB);
				r.delay(1000);
				
				sk.getScreen().wait(ImgPatter.get(data.get("methodOfPayment")),20).left(20).click();
				r.delay(1000);
				
				sk.getScreen().wait(ImgPatter.get("bandera"),20);
				r.delay(1000);
				sk.getScreen().type(data.get("Bandera") + Key.TAB);
				r.delay(1000);
				
				sk.getScreen().type(data.get("Banco") + Key.TAB);
				r.delay(1000);
				
				sk.getScreen().type(data.get("Tipo") + Key.TAB);
				r.delay(3000);
				
				sk.getScreen().wait(ImgPatter.get("btn_OK_payment"),20).click();
				r.delay(1000);
				
				break;
				
			case "M":
				sk.getScreen().wait(ImgPatter.get("box_promoType"),20).right(10).click();
				r.delay(1000);
				sk.getScreen().type(data.get("promType") + Key.TAB);
				r.delay(1000);
				
				sk.getScreen().wait(ImgPatter.get("msgPOS"),20).right(10).click();
				r.delay(1000);
				sk.getScreen().type(data.get("MsgPOS") + Key.TAB);
				r.delay(1000);
				
				sk.getScreen().wait(ImgPatter.get(data.get("methodOfPayment")),20).left(20).click();
				r.delay(1000);
				
				sk.getScreen().wait(ImgPatter.get("bandera"),20);
				r.delay(1000);
				sk.getScreen().type(data.get("Bandera") + Key.TAB);
				r.delay(1000);
				
				sk.getScreen().type(data.get("Banco") + Key.TAB);
				r.delay(1000);
				
				sk.getScreen().type(data.get("Tipo") + Key.TAB);
				r.delay(3000);
				
				sk.getScreen().wait(ImgPatter.get("btn_OK_payment"),20).click();
				r.delay(1000);
				
				break;
			default:
				sk.getScreen().wait(ImgPatter.get("box_promoType"),20).right(10).click();
				r.delay(1000);
				sk.getScreen().type(data.get("promType") + Key.TAB);
				r.delay(1000);
					break;
			}
			
			r.delay(2000);
			if (data.get("startTime")!= null) {
				sk.getScreen().wait(ImgPatter.get("start_time_promo"),20).right(10).click();
				r.delay(1000);
				sk.getScreen().type(data.get("startTime") + Key.TAB);
				r.delay(1000);
			}
			
			if (data.get("endTime")!= null) {
				sk.getScreen().wait(ImgPatter.get("end_time_promo"),20).right(10).click();
				r.delay(1000);
				sk.getScreen().type(data.get("endTime") + Key.TAB);
				r.delay(1000);
			}
			
			super.screenshot();

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	/**
	 * En la ventana promst agrega las tiendas para la promocion
	 */
	public void ConfigurepromstRMS() {
		r.delay(5000);
		String[] tiendas = this.obtenerTiendas();

		try {
			sk.getScreen().wait(ImgPatter.get("btn_store"), 5).click();
//			if (sk.getScreen().exists(ImgPatter.get("retek_error")) != null) {
//				System.out.println("DEBES COLOCAR HORA DE INICIO Y FIN");
//				return;
//			}
			for (int i = 0; i < tiendas.length; i++) {
				sk.getScreen().wait(ImgPatter.get("slct_groupt"), 5).click();
				sk.getScreen().wait(ImgPatter.get("item_store"), 5).click();
				sk.getScreen().type(Key.TAB);
				sk.getScreen().type(tiendas[i] + Key.TAB);
				r.delay(2000);
				sk.getScreen().wait(ImgPatter.get("btn_apply"), 5).click();
				r.delay(1000);
				super.screenshot();

				if (i == tiendas.length - 1) {
					sk.getScreen().wait(ImgPatter.get("btn_ok"), 5).click();
				} else {
					sk.getScreen().wait(ImgPatter.get("btn_add_store"), 5).click();

				}

			}
			r.delay(1000);

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

	}

	/**
	 * Agrega componentes e items a las promociones
	 * 
	 * @param tipoDeDcto Tipo de promocion que se va a aplicar (percentOff,
	 *                   amountOff, fixedAmount)
	 */
	private void addItems(String tipoDeDcto) {
		JSONArray jsonArray = new JSONArray(data.get("items"));
		String thresholdNumber = data.get("thresholdNumber");
		JSONObject jsonObj;

		for (int i = 0; i < jsonArray.length(); i++) {
			jsonObj = (JSONObject) jsonArray.get(i);

			String nombre = JsonUtil.getJsonElement(jsonObj, "nombre");
			String[] items = jsonObj.get("items").toString().split(",");

			try {
				r.delay(2000);
				sk.getScreen().type(thresholdNumber + (i + 1));
				r.delay(1000);
				sk.getScreen().type(Key.TAB);
				sk.getScreen().type(nombre);

				this.clickDcto(tipoDeDcto);

				r.delay(2000);

				sk.getScreen().type(data.get("threshold_unit"));
				sk.getScreen().type(Key.TAB);
				sk.getScreen().type(data.get("threshold_amount"));
				sk.getScreen().type(Key.TAB);
				r.delay(2000);

				super.screenshot();

				sk.getScreen().wait(ImgPatter.get("btn_item"), 5).click();

				for (int j = 0; j < items.length; j++) {
					sk.getScreen().wait(ImgPatter.get("slct_items"), 20).right(10).click();
					r.delay(1000);
					//itemType(data.get("itemType"));
					sk.getScreen().type(data.get("itemType"));
					
					r.delay(1000);
					sk.getScreen().type(Key.TAB);
					r.delay(1000);
					sk.getScreen().type(Key.TAB);
					
					sk.getScreen().type(items[j].replaceAll(" ", ""));
					r.delay(1000);
					sk.getScreen().type(Key.TAB);
					r.delay(2000);
					sk.getScreen().wait(ImgPatter.get("btn_apply_underline"), 5).click();
					r.delay(1000);
					if (j == (items.length - 1)) {
						r.delay(1000);
						sk.getScreen().wait(ImgPatter.get("btn_ok"), 5).click();
						r.delay(1000);
						super.screenshot();
					} else {
						r.delay(1000);
						sk.getScreen().wait(ImgPatter.get("btn_add_items"), 5).click();
						r.delay(1000);
					}

				}

				r.delay(2000);

				if (i == (jsonArray.length() - 1)) {
					sk.getScreen().wait(ImgPatter.get("btn_ok_slctd"), 5).click();
				} else {
					sk.getScreen().wait(ImgPatter.get("btn_ok_repeat"), 5).click();
				}

			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
		}

	}

	/**
	 * Agrega el item type al que se le aplicara la promocion
	 * 
	 * @param itemType tipo de item que se va a seleccionar
	 */
	private void itemType(String itemType) {
		try {
			if (itemType == null || itemType.isEmpty() || itemType.equals("singleItem")) {
				sk.getScreen().wait(ImgPatter.get("single_item")).click();
				return;
			}

			if (itemType.equals(itemType)) {
				sk.getScreen().wait(ImgPatter.get("item_list")).click();
				return;
			}

		} catch (FindFailed e) {
			e.printStackTrace();
		}
	}

	/**
	 * Obtiene tiendas para agregar a la promo
	 * 
	 * @param estatus de las ligas de las tiendas (completas, incompletas, todas)
	 * @return arreglo con los numeros de tienda
	 */
	public String[] obtenerTiendas(String ligas) {
		String stores = "";

		if (ligas.equals("completas")) {
			stores = data.get("ligas_completas");
		} else if (ligas.equals("incompletas")) {
			stores = data.get("ligas_incompletas");
		} else if (ligas.equals("todas")) {
			stores = data.get("stores");
		}

		if (stores.isEmpty()) {
			return new String[] { null };
		}

		String[] array = stores.split(",");

		for (int i = 0; i < array.length; i++) {
			array[i] = array[i].replace(" ", "");
		}

		return array;
	}

	/**
	 * Obtiene todas las tiendas para agregar a la promo
	 * 
	 * @return arreglo con los numeros de tienda
	 */
	private String[] obtenerTiendas() {
		return obtenerTiendas("todas");
	}

	/**
	 * Selecciona el tipo de descuento (percentOff, amountOff o fixedAmount)
	 * 
	 * @param tipoDeDcto descuento que se va a seleccionar
	 */
	private void clickDcto(String tipoDeDcto) {
		try {
			switch (tipoDeDcto) {
			case "percentOff":
				sk.getScreen().wait(ImgPatter.get("dto_amount"), 5).click();
				sk.getScreen().wait(ImgPatter.get("dto_percent"), 5).click();
				break;
			case "amountOff":
				sk.getScreen().wait(ImgPatter.get("dto_amount"), 5).click();
				break;
			case "fixedAmount":
				sk.getScreen().wait(ImgPatter.get("dto_fixed"), 5).click();
				sk.getScreen().type(Key.TAB);
				break;
			}

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	/**
	 * Coloca fecha_inicio y fecha_fin en la promocion
	 */
	private void colocarFechas() {
		String fechaInicio = data.get("fecha_inicio");
		String fechaFin = data.get("fecha_fin");

		try {
			// si no esta vacia coloca la fecha_inicio
			if (!fechaInicio.isEmpty()) {
				sk.getScreen().wait(ImgPatter.get("start_date_text").targetOffset(100, 0)).click();
				sk.getScreen().type(fechaInicio);
			}

			r.delay(2000);

			// si no esta vacia coloca la fecha_fin
			if (!fechaFin.isEmpty()) {
				sk.getScreen().wait(ImgPatter.get("end_date_text").targetOffset(100, 0)).click();
				sk.getScreen().type(fechaFin);
			}

			r.delay(1000);
			sk.getScreen().type(Key.TAB);

		} catch (FindFailed e) {
			e.printStackTrace();
		}

	}

	/**
	 * Coloca horas y dias en los que estara activa la promocion
	 */
	private void configurarDiaYHora() {
		String horaInicio = data.get("hora_inicio");
		String horaFin = data.get("hora_fin");
		/*
		 * if (horaInicio.equals("")) { horaInicio = "0001"; }
		 * 
		 * if (horaFin.equals("")) { horaFin = "2359"; }
		 */

		try {
			sk.getScreen().wait(ImgPatter.get("start_time").targetOffset(50, 0), 5).click();
			sk.getScreen().type(horaInicio);
			sk.getScreen().type(Key.TAB);
			sk.getScreen().wait(ImgPatter.get("end_time").targetOffset(50, 0), 5).click();
			sk.getScreen().type(horaFin);
			sk.getScreen().type(Key.TAB);
			r.delay(1000);
			sk.getScreen().wait(ImgPatter.get("scheduling_check"), 5).click();
			r.delay(1000);
			sk.getScreen().wait(ImgPatter.get("scheduling_tuesday").targetOffset(25, 0), 5).click();
			r.delay(1000);
			sk.getScreen().wait(ImgPatter.get("scheduling_thursday").targetOffset(25, 0), 5).click();
			r.delay(1000);
			sk.getScreen().wait(ImgPatter.get("btn_scheduling_ok"), 5).click();

		} catch (FindFailed e) {
			e.printStackTrace();
		}
	}

	/**
	 * En la pantalla - coloca el folio de la promo para poder aprobarla
	 * 
	 * @param promotion folio de la promocion que se quiere ver
	 * @throws FindFailed
	 */
	public void viewPromo(String promotion) throws FindFailed {
		r.delay(3000);
		action();
		//sk.getScreen().find(ImgPatter.get("lbl_action")).right(60).click();
		sk.getScreen().wait(ImgPatter.get("itm_edit_promo")).click();
		r.delay(1000);
		sk.getScreen().find(ImgPatter.get("box_promo")).right(80).click();
		sk.getScreen().type(promotion + Key.TAB);
		r.delay(1000);
		sk.getScreen().wait(ImgPatter.get("btn_ok_slctd")).click();
		r.delay(5000);

		while (sk.getScreen().exists(ImgPatter.get("btn_ok_warning")) != null) {
			sk.getScreen().wait(ImgPatter.get("btn_ok_warning")).click();
			r.delay(2000);
		}

	}

	/**
	 * Aprueba la promocion
	 * @throws FindFailed 
	 * @throws InterruptedException 
	 */
	public void aprobarPromo() throws FindFailed, InterruptedException {
			sk.getScreen().wait(ImgPatter.get("mn_options_2")).click();
			sk.getScreen().wait(ImgPatter.get("itm_approve")).click();
			super.screenshot();

			r.delay(3000);

			sk.getScreen().wait(ImgPatter.get("btn_no_approve"), 600).click();
			r.delay(3000);
			sk.getScreen().wait(ImgPatter.get("btn_no_approve"), 600).click();
			r.delay(3000);

			sk.getScreen().wait(ImgPatter.get("btn_post_approve_ok_selected"), 100).click();
			sk.getScreen().wait(ImgPatter.get("btn_close"), 600).click();
			sk.getScreen().wait(ImgPatter.get("mn_action"), 600).click();
			sk.getScreen().wait(ImgPatter.get("smn_close"), 600).click();

	}

	/**
	 * Corrobora si la tienda ligado un articulo en la tabla ITEM_LOC recibiendo
	 * como parametro un SQLResult, se recorre y se verifica si tienda se encuentra
	 * en el resultado
	 * 
	 * @param tienda      tienda que se va a validar
	 * @param queryResult resultado del query que se va a recorrer
	 * @return true si existe la tienda en queryResult, de lo contrario false
	 */
	public boolean validarLigas(String tienda, SQLResult queryResult) {
		for (int i = 0; i < queryResult.getRowCount(); i++) {

			if (queryResult.getRow(i).contains(tienda)) {
				return true;
			}
		}

		return false;
	}

	/**
	 * Al crear la promocion obtiene la fecha de inicio y fecha fin
	 */
	private void obtenerFechas() {
		try {
			sk.getScreen().wait(ImgPatter.get("start_date_text").targetOffset(100, 0), 5).click();
			r.delay(1000);

			this.fechaInicio = copyPaste();

			r.delay(1000);

			sk.getScreen().wait(ImgPatter.get("end_date_text").targetOffset(100, 0), 5).click();
			r.delay(1000);
			this.fechaFin = copyPaste();

		} catch (FindFailed e) {
			System.out.println(e.getMessage());
		}

	}

	/**
	 * Si existen las columnas fecha_inicio o fecha_fin y tiene datos, significa que
	 * se requiere configurar la fecha de la promocion
	 * 
	 * @return true si hay columna fecha_inicio o fecha_fin con datos, de lo
	 *         contrario false
	 */
	private boolean configurarFecha() {
		// si no existen las columnas significa que no se requiere configurar fecha
		if ((data.get("fecha_inicio") == null || data.get("fecha_fin") == null)) {
			return false;
		}

		// si existen las columnas pero estan vacias tampoco se configuran las fechas
		if (data.get("fecha_inicio").equals("") || data.get("fecha_fin").equals("")) {
			return false;
		}

		return true; // existe fecha_inicio o fecha_fin con datos
	}

	/**
	 * Si existen las columnas hora_inicio y hora_fin y tiene datos, significa que
	 * se requiere configurar hora en la promocion
	 * 
	 * @return true si hay columna hora_inicio y fecha_fin con datos, de lo
	 *         contrario false
	 */
	private boolean configurarHora() {
		if (data.get("hora_inicio") == null && data.get("hora_fin") == null) {
			return false;
		}

		if (data.get("hora_inicio").equals("") && data.get("hora_fin").equals("")) {
			System.out.println("Debes colocar hora de inicio y fin en el Data Provider");
			return false;
		}

		return true;
	}

	/**
	 * Submit o approve una promocion segun lo indique el data provider
	 * @throws InterruptedException 
	 * @throws FindFailed 
	 */
	public void cambioStatus() throws FindFailed, InterruptedException {
		if (data.get("accion").equals("submit")) {
			this.submitPromo();
		} else if (data.get("accion").equals("approve")) {
			this.aprobarPromo();
		}
	}

	/**
	 * Obtiene la fecha de inicio para realizar validaciones
	 * 
	 * @return string con la fecha de inicio en formato ddMMyy Hmm
	 */
	public String obtenerFechaInicio() {
		String fecha = data.get("fecha_inicio");
		String hora = !this.data.get("hora_inicio").isEmpty() ? this.data.get("hora_inicio") : "0000";

		if (fecha.isEmpty()) {
			return this.fechaInicio + " " + hora;
		}

		fecha += " " + hora;

		return fecha;
	}

	/**
	 * Obtiene la fecha fin para realizar validaciones
	 * 
	 * @return string con la fecha de fin en formato ddMMyy Hmm
	 */
	public String obtenerFechaFin() {
		String fecha = data.get("fecha_fin");
		String hora = !this.data.get("hora_fin").isEmpty() ? this.data.get("hora_fin") : "0000";

		if (fecha.isEmpty()) {
			return this.fechaFin + " " + hora;
		}

		fecha += " " + hora;

		return fecha;
	}

	public void CloseBrowser() {
		try {
			u.close();
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	/**
	 * Llena los datos de la promocion
	 */
	public void confPromoComplejaItemList() {
		try {

			this.configurePROMHEADRMS(); // ingresa la info de la promo

			// si en el data provider existe la col fecha_inicio o fecha_fin
			// y tiene datos, le configura fechas a la promo
			if (this.configurarFecha()) {
				this.colocarFechas();
			}

			// si en el data provider existe la col hora_inicio y hora_fin
			// y tiene datos, le configura hora y dias a la promo
			if (this.configurarHora()) {
				this.configurarDiaYHora();
			}

			super.screenshot();

			this.obtenerFechas();

			this.ConfigurepromstRMS();

			// MENU
			sk.getScreen().wait(ImgPatter.get("mn_options"), 5).click();
			sk.getScreen().wait(ImgPatter.get("smn_ai"), 5).hover();
			sk.getScreen().wait(ImgPatter.get("smn_mu"), 5).click();

			System.out.println(data.get("tipo_descuento"));
			this.addItemList(this.data.get("tipo_descuento"));

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	/**
	 * Agrega componentes e items a las promociones
	 * 
	 * @param tipoDeDcto Tipo de promocion que se va a aplicar (percentOff,
	 *                   amountOff, fixedAmount)
	 */
	private void addItemList(String tipoDeDcto) {
		String thresholdNumber = "10001";
		String itemList = data.get("itemList");
		String nombre = data.get("nombre");

		try {
			r.delay(1000);
			sk.getScreen().type(thresholdNumber);
			r.delay(1000);
			sk.getScreen().type(Key.TAB);
			sk.getScreen().type(nombre);

			this.clickDcto(tipoDeDcto);

			r.delay(1000);

			sk.getScreen().type(data.get("threshold_unit"));
			sk.getScreen().type(Key.TAB);
			sk.getScreen().type(data.get("threshold_amount"));
			sk.getScreen().type(Key.TAB);
			r.delay(1000);

			super.screenshot();

			sk.getScreen().wait(ImgPatter.get("btn_item"), 5).click();

			sk.getScreen().wait(ImgPatter.get("slct_items"), 5).click();
			r.delay(1000);

			sk.getScreen().type("Item_list");
			sk.getScreen().type(Key.TAB);

			sk.getScreen().type(itemList);
			sk.getScreen().type(Key.TAB);
			r.delay(2000);

			sk.getScreen().type(Key.ENTER);
			r.delay(3000);

			sk.getScreen().wait(ImgPatter.get("btn_ok_itemList"), 20).click();
			r.delay(2000);

			sk.getScreen().wait(ImgPatter.get("btn_ok_itemList_U"), 20).click();
			r.delay(2000);

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	/**
	 * @param cb
	 * @author 72843
	 * @return regresa un String con el status de la promoción
	 * @throws query para consultar estatus de la promoción en la tabla PROMHEAD
	 * 
	 */
	public String ValidatedApproving() {
		// String SQLQuery = "SELECT STATUS FROM PROMHEAD WHERE PROMOTION = "+ cb ;
		String SQLQuery = "SELECT STATUS FROM PROMHEAD WHERE PROMOTION = 4555069";
		// System.out.println(SQLQuery.toString());
		SQLResult res = null;
		try {
			res = dbRms.executeQuery(SQLQuery);
			System.out.println(res.getData(0, 0).toString());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return res.getData(0, 0).toString();

	}

	public String RunDB() {
		do {
			String rest = ValidatedApproving();
			r.delay(5000);
			status = rest;
			System.out.println("estatus es: " + status);

		} while (status.equals("I"));

		return status;
	}

	// genera una data de paths de las imagenes.
	public HashMap<String, Pattern> GetPatternsImg(File fol) {
		File[] files = Fol.listFiles();
		for (File s : files) {
			patterns.add(new Pattern(s.getAbsolutePath()).similar((float) .95));
			// patterns.add( new Pattern(s.getAbsolutePath()).exact());
		}
		for (Pattern p : patterns) {
			String fname = p.getFilename().toString();
			fname = fname.replace("\\", "-");
			fname = fname.replace(".png", "");
			String[] afname = fname.split("-");
			ImgPatter.put(afname[afname.length - 1], p);
		}

		return ImgPatter;
	}

	public void OpenBrowser() {
		try {
			u.get(url);
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	// loguin de RMS
	public void loguiRMS() {
		try {
			ImgPatter = GetPatternsImg(Fol);
			OpenBrowser();
			r.delay(8000);

			sk.getScreen().type(User);
			r.delay(1000);
			sk.getScreen().type(Key.TAB);
			r.delay(1000);
			sk.getScreen().type(Pwd + Key.TAB + Key.TAB);
			sk.getScreen().type(Key.ENTER);
			r.delay(10000);
			sk.getScreen().wait(ImgPatter.get("pricing"), 15).click();
			sk.getScreen().wait(ImgPatter.get("promotions"), 15).doubleClick();
		} catch (FindFailed e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void confPromoCompleja(String tipoDcto, boolean conDiaYHora) {
		try {
			this.configurePROMHEADRMS();
			this.ConfigurepromstRMS();

			if (conDiaYHora) {
				this.configurarDiaYHora();
			}

			// MENU
			sk.getScreen().wait(ImgPatter.get("mn_options"), 5).click();
			sk.getScreen().wait(ImgPatter.get("smn_ai"), 5).hover();
			sk.getScreen().wait(ImgPatter.get("smn_mu"), 5).click();

			this.addItems(tipoDcto);

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	public void confPromoCompleja(String tipoDcto) {
		confPromoCompleja(tipoDcto, false);
	}

	public void confPromoComplejaInicioHoyFinManana(String tipoDcto, boolean hoy, boolean manana) {
		try {
			this.configurePROMHEADRMS();
			this.colocarFechas(hoy, manana);
			this.ConfigurepromstRMS();

			// MENU
			sk.getScreen().wait(ImgPatter.get("mn_options"), 5).click();
			sk.getScreen().wait(ImgPatter.get("smn_ai"), 5).hover();
			sk.getScreen().wait(ImgPatter.get("smn_mu"), 5).click();

			this.addItems(tipoDcto);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	public void approvepromo() {

		try {
//				loguiRMS();
//				r.delay(9000);
//				sk.getScreen().find(ImgPatter.get("lbl_action")).right(60).click();
//				sk.getScreen().wait(ImgPatter.get("itm_edit_promo"), 5).click();
//				r.delay(1000);
//				sk.getScreen().find(ImgPatter.get("box_promo")).right(80).click();
//				sk.getScreen().type("" + Key.TAB);
//				r.delay(2000);
//				sk.getScreen().wait(ImgPatter.get("btn_ok_slctd"), 5).click();
//				r.delay(3000);
//
//				sk.getScreen().wait(ImgPatter.get("mn_options"), 5).click();
//				sk.getScreen().wait(ImgPatter.get("itm_approve"), 5).click();
//
//				do {
//
//					st = sk.getScreen().has(ImgPatter.get("btn_mdl_yes"));
//					System.out.println(st.toString());
//					r.delay(59000);
//					System.out.println("entro");
//				} while (st.equals(false));
//
//				sk.getScreen().wait(ImgPatter.get("btn_No"), 5).click();
//				r.delay(1000);
//				sk.getScreen().wait(ImgPatter.get("btn_mdl_yes"), 5).click();
//				sk.getScreen().wait(ImgPatter.get("btn_mdl_yes"), 5).click();
//				sk.getScreen().wait(ImgPatter.get("btn_ok_slctd"), 5).click();
//				sk.getScreen().wait(ImgPatter.get("btn_close"), 5).click();
//				sk.getScreen().wait(ImgPatter.get("mn_action"), 5).click();
//				sk.getScreen().wait(ImgPatter.get("smn_close"), 5).click();

		} catch (Exception e) {
			System.out.println(e.toString());
		}
	}

	public void editPromo() {
		try {
			sk.getScreen().wait(ImgPatter.get("action"), 15).click();
			sk.getScreen().wait(ImgPatter.get("itm_edit_promo"), 15).click();
			r.delay(1000);
			sk.getScreen().find(ImgPatter.get("box_promo")).right(80).click();
			sk.getScreen().type(data.get("promotion") + Key.TAB);
			r.delay(2000);
			sk.getScreen().wait(ImgPatter.get("btn_ok_slctd"), 15).click();
			r.delay(2000);
			sk.getScreen().wait(ImgPatter.get("btn_store"), 15).click();
			r.delay(1000);
			sk.getScreen().wait(ImgPatter.get("mn_options"), 15).click();
			sk.getScreen().wait(ImgPatter.get("promotion_mass_change"), 15).click();

			sk.getScreen().wait(ImgPatter.get("mass_change_group"), 15).click();
			sk.getScreen().wait(ImgPatter.get("promotion_store"), 15).click();
			sk.getScreen().type(Key.TAB);
			r.delay(1000);
			sk.getScreen().type(data.get("valueStore"));
			sk.getScreen().type(Key.TAB);
			sk.getScreen().type(Key.ENTER);
			r.delay(1000);
			sk.getScreen().wait(ImgPatter.get("update_end_date"), 15).click();
			sk.getScreen().type(this.obtenerFechaInicioPromo());
			sk.getScreen().type(Key.TAB);
			sk.getScreen().wait(ImgPatter.get("btn_ok_mass"), 15).click();
			r.delay(2000);
			sk.getScreen().wait(ImgPatter.get("btn_ok_warning"), 15).click();
			r.delay(1000);
			sk.getScreen().wait(ImgPatter.get("btn_ok_mass_underline"), 15).click();
			r.delay(2000);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private String obtenerFechaInicioPromo() throws SQLException {
		SQLResult executeQuery = dbRms.executeQuery(Queries.FECHA_INICIO + data.get("promotion"));
		String[] fecha = executeQuery.getData(0, "START_DATE").substring(0, 10).split("-");
		String fechaInicio = "";

		for (int i = fecha.length - 1; i >= 0; i--) {
			fechaInicio += fecha[i];
		}

		return fechaInicio;
	}

	private String obtenerDia(int agregarDia) {
		String formatDate = "";
		try {
			LocalDateTime fecha = LocalDateTime.now();
			fecha = fecha.plusDays(agregarDia);
			DateTimeFormatter dt = DateTimeFormatter.ofPattern("ddMMyy");
			formatDate = fecha.format(dt);

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		return formatDate;
	}

	private String obtenerDia() {
		return obtenerDia(0);
	}

	public void colocarFechas(boolean hoy, boolean manana) {
		String fechaInicio = hoy ? obtenerDia() : obtenerDia(1);
		String fechaFin = manana ? obtenerDia(1) : obtenerDia();

		try {
			sk.getScreen().wait(ImgPatter.get("start_date_text").targetOffset(100, 0), 5).click();
			sk.getScreen().type(fechaInicio);
			sk.getScreen().type(Key.TAB);
			r.delay(1000);
			sk.getScreen().wait(ImgPatter.get("end_date_text").targetOffset(100, 0), 5).click();
			sk.getScreen().type(fechaFin);

			sk.getScreen().type(Key.TAB);

		} catch (FindFailed e) {
			e.printStackTrace();
		}

	}

	public void minimizar() {
		System.out.println("MMINIMIZA");
		WebDriver driver = this.u.getDriver();
		Point point = driver.manage().window().getPosition();
		Dimension d = driver.manage().window().getSize();

		driver.manage().window().setSize(new Dimension(0, 0));
		driver.manage().window().setPosition(new Point((d.getHeight() - point.getX()), (d.getWidth() - point.getY())));
		System.out.println("NO MINIMIZA");
	}

	/**
	 * @author 72843
	 * @throws InterruptedException
	 * @Comments realiza login en RMS
	 * 
	 */
	public void Login_En_RMS() throws FindFailed, InterruptedException {
		ImgPatter = GetPatternsImg(Fol);
		OpenBrowser();

		r.delay(12000);
		sk.getScreen().type(data.get("rms_user"));
		sk.getScreen().type(Key.TAB);
		r.delay(4000);
		sk.getScreen().type(PasswordUtil.decryptPassword(data.get("rms_ps")) + Key.TAB + Key.TAB);
		screenshot();
		sk.getScreen().type(Key.ENTER);
		r.delay(1000);
		sk.getScreen().wait(ImgPatter.get("menu"), 40);
		sk.getScreen().wait(ImgPatter.get("btn_max"), 15).highlight(1, "red").click();
	}

	/**
	 * @author 1000068915
	 * @comment Metodo para dar submit dentro de la barra de tareas y dar clic en la
	 *          opcion NO y YES en los mensajes que se generan
	 */
	public void submitPromo2() throws FindFailed {
		r.delay(2000);
		sk.getScreen().wait(ImgPatter.get("mn_options"), 15).click();
		sk.getScreen().wait(ImgPatter.get("smn_submit"), 15).click();
		r.delay(5000);
		screenshot();
		r.delay(2000);
		sk.getScreen().wait(ImgPatter.get("btn_No"), 15).click();
		r.delay(2000);
		sk.getScreen().wait(ImgPatter.get("rpch_btn_yes"), 15).click();
		r.delay(2000);
	}
	
	
	public void confPromoSimple(String tipoDcto) {
		try {
			this.configurePROMHEADRMS();
			this.ConfigurepromstRMS();

			// MENU
			sk.getScreen().wait(ImgPatter.get("mn_options"), 5).click();
			r.delay(1000);
			sk.getScreen().wait(ImgPatter.get("smn_ai"), 5).hover();
			r.delay(1000);
			sk.getScreen().wait(ImgPatter.get("smn_si"), 5).click();
			r.delay(1000);
			sk.getScreen().type(data.get("item") + Key.TAB);
			r.delay(1000);
			sk.getScreen().type(data.get("discountType") + Key.TAB);
			r.delay(1000);
			sk.getScreen().type(data.get("changeAmount") + Key.TAB);
			r.delay(1000);
			sk.getScreen().type(data.get("adjustType") + Key.TAB);
			r.delay(1000);
			
			sk.getScreen().wait(ImgPatter.get("btn_ok"), 5).click();
			

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	
	public String bajaPromo() {
		r.delay(5000);
		String promotion = "";

		try {
			action();
			r.delay(1000);
			sk.getScreen().type("Edit");
			r.delay(1000);
			sk.getScreen().type(Key.TAB);
			r.delay(1000);
			
			sk.getScreen().type(data.get("promotion") + Key.TAB);
			r.delay(1000);
			
			sk.getScreen().type(Key.ENTER);
			r.delay(1000);

			sk.getScreen().wait(ImgPatter.get("btn_store"), 20).click();
			r.delay(2000);
			sk.getScreen().wait(ImgPatter.get("mn_options"), 15).click();
			r.delay(1000);
			sk.getScreen().wait(ImgPatter.get("option_PSMC"), 5).click();
			r.delay(2000);
					
			String[] tiendas = this.obtenerTiendas();

			for (int i = 0; i < tiendas.length; i++) {
				sk.getScreen().wait(ImgPatter.get("selectGroupType"), 5).click();
				sk.getScreen().wait(ImgPatter.get("item_store"), 5).click();
				sk.getScreen().type(Key.TAB);
				sk.getScreen().type(tiendas[i] + Key.TAB);
				r.delay(2000);
				sk.getScreen().type(Key.ENTER);
				r.delay(1000);
				super.screenshot();

				if (i == tiendas.length - 1) {
					sk.getScreen().wait(ImgPatter.get("update_end_date"), 5).left(5).click();
					break;
				}
			}
			
			
			LocalDate diaActual = LocalDate.now();
			diaActual = diaActual.plusDays(1);
			DateTimeFormatter formatN = DateTimeFormatter.ofPattern("ddMMyy");
			String diaActualFormat = diaActual.format(formatN);
			
			r.delay(2000);
			sk.getScreen().type(diaActualFormat);
			sk.getScreen().type(Key.TAB);
			r.delay(1000);
			sk.getScreen().type(Key.TAB);
			r.delay(1000);
			sk.getScreen().type(Key.ENTER);
			r.delay(1000);
			
			if (sk.getScreen().exists(ImgPatter.get("retek_warning"), 10) != null) {
				sk.getScreen().type(Key.ENTER);
			}
			
			r.delay(3000);
			sk.getScreen().type(Key.ENTER);
			r.delay(2000);
			sk.getScreen().type(Key.ENTER);

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		return promotion;
	}
	
	public void close_RMS_2() throws FindFailed {
		r.delay(2000);
		sk.getScreen().wait(ImgPatter.get("mn_action"), 5).highlight(1, "red").click();
		sk.getScreen().wait(ImgPatter.get("smn_close"), 5).highlight(1, "red").click();
		r.delay(2000);
		sk.getScreen().wait(ImgPatter.get("mn_action"), 5).highlight(1, "red").click();
		sk.getScreen().wait(ImgPatter.get("smn_close"), 5).highlight(1, "red").click();
	}

	public void Crear_Promocion() {
		// TODO Auto-generated method stub

	}

	public void Configurar_Promocion_Simple_Item_List() {
		// TODO Auto-generated method stub

	}

	public void Submit_Promocion() {
		// TODO Auto-generated method stub

	}

	public String Validar_DB() {
		return status;
		// TODO Auto-generated method stub

	}

	public void Aprovar_Promocion() {
		// TODO Auto-generated method stub

	}
}