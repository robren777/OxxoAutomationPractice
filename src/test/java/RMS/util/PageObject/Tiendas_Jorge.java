package RMS.util.PageObject;

import java.awt.Robot;
import java.io.File;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONObject;
import org.sikuli.script.App;
import org.sikuli.script.FindFailed;
import org.sikuli.script.Key;
import org.sikuli.script.Pattern;
import org.sikuli.script.Region;
import org.sikuli.script.Screen;
import org.sikuli.script.ScreenImage;

import config.Constants;
import modelo.TestCase;
import utils.JsonUtil;
import utils.SikuliUtil;
import utils.controlm.ControlM;
import utils.password.PasswordUtil;
import utils.selenium.SeleniumUtil;
import utils.sql.SQLResult;
import utils.sql.SQLUtil;

public class Tiendas_Jorge {

//	private static final TestCase TestCase = null;
	static SQLUtil dbRms;
	static Robot r;
	static SeleniumUtil u;
	static SikuliUtil sk;
	static String User;
	static String Pwd;
	static ArrayList<String> ListImage;
	static ArrayList<Pattern> patterns;
	static HashMap<String, Pattern> ImgPattern;
	static File Fol;
	static Pattern pathimg;
	public static String cb;
	static String status;
	static Boolean st;
	static HashMap<String, String> data;
	static ControlM j;
	static TestCase testCase;
	static JSONObject jobs;
	static String formatDate;
	// private static final DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd
	// HH:mm:ss");
	static String df;
	int numeroEvidencia = 0;

	LocalDate diaActualVAT;

	@SuppressWarnings("static-access")
	public Tiendas_Jorge(SikuliUtil sk, SeleniumUtil u, String cb, Robot r, Pattern pathimg,
			ArrayList<String> ListImage, HashMap<String, Pattern> ImgPattern, ArrayList<Pattern> patterns, File Fol,
			String status, SQLUtil dbRms, Boolean st, HashMap<String, String> data, TestCase testcase)
			throws SQLException {
		this.u = u;
		this.sk = sk;
		this.cb = cb;
		this.r = r;
		this.pathimg = pathimg;
		this.ListImage = ListImage;
		this.ImgPattern = ImgPattern;
		this.patterns = patterns;
		this.Fol = Fol;
		this.status = status;
		this.dbRms = dbRms;
		this.st = st;
		this.data = data;
		this.testCase = testcase;
	}

	/**
	 * @param cb
	 * @author 72843
	 * @Comments regresa un String con el status de la promoci�n
	 * @throws query para consultar estatus de la promoci�n en la tabla PROMHEAD
	 * 
	 */
	private String ValidatedApproving() {
		String SQLQuery = "SELECT STATUS FROM PROMHEAD WHERE PROMOTION = " + cb;
//		String SQLQuery = "SELECT STATUS FROM PROMHEAD WHERE PROMOTION = 4555167";	
//		System.out.println(SQLQuery.toString());		
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

	/**
	 * @author 72843
	 * 
	 * @return status
	 * @Comments valida el estatus en la base de datos de RMS
	 */
	public String Validar_DB() {

		do {
			String rest = ValidatedApproving();
			r.delay(5000);
			status = rest;
			System.out.println("estatus es: " + status);

		} while (status.equals("I"));

		return status;
	}

	public String Validar_DB_DEALS() {

		cb = App.getClipboard();

		do {
			String rest = ValidatedApproving();
			r.delay(5000);
			status = rest;
			System.out.println("estatus es: " + status);

		} while (status.equals("I"));

		return status;
	}

	/**
	 * @author 72843
	 * @param fol
	 * @return ImgPatter
	 * @Comments genera la data de las imagenes a utilizar en la automatizaci�n
	 * 
	 */
	private HashMap<String, Pattern> GetPatternsImg(File fol) {
		File[] files = Fol.listFiles();
		for (File s : files) {
			patterns.add(new Pattern(s.getAbsolutePath()).similar((float) .95));
//			patterns.add( new Pattern(s.getAbsolutePath()).exact());			
		}
		for (Pattern p : patterns) {
			String fname = p.getFilename().toString();
			fname = fname.replace("\\", "-");
			fname = fname.replace(".png", "");
			String[] afname = fname.split("-");
			ImgPattern.put(afname[afname.length - 1], p);
		}

		return ImgPattern;
	}

	public void OpenBrowser() {
		try {
			System.out.println("Driver: " + u.getDriver());
			System.out.println(data.get("rms_url"));
			u.get(data.get("rms_url"));
		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}
	}

	/**
	 * @author 1000068915
	 * @comments Cierra el navegador
	 */

	public void CloseBrowser() {
		try {
			u.close();
		} catch (Exception e) {
		}
	}

	/**
	 * @author 72843
	 * @throws InterruptedException
	 * @Comments realiza login en RMS
	 * 
	 */
	public void Login_En_RMS() throws FindFailed, InterruptedException {
		try {
			ImgPattern = GetPatternsImg(Fol);
			OpenBrowser();

			waitImage(100, "FEMSA_Label");
			sk.screen.wait(ImgPattern.get("btn_max"), 15).click();
			u.hardWait(2);
			waitImage(100, "login_screen");
			u.hardWait(2);
			sk.screen.wait(ImgPattern.get("user"), 10).highlight("red").right(20).click();
			u.hardWait(1);
			sk.screen.type(data.get("rms_user"));
			u.hardWait(2);
			sk.screen.wait(ImgPattern.get("password"), 10).highlight("red").right(20).click();
			u.hardWait(1);
			sk.screen.type(PasswordUtil.decryptPassword(data.get("rms_ps")) + Key.TAB + Key.TAB);
			u.hardWait(1);
			screenshot2();
			u.hardWait(1);
			sk.screen.type(Key.ENTER);
			Screen.highlightAllOff();
			waitImage(20, "menu");
			sk.screen.wait(ImgPattern.get("btn_max"), 15).click();

		} catch (Exception ex) {
			System.out.println(" -> " + ex);
			throw new AssertionError("Fallo al realizar el Login en RMS");
		}
	}

	/**
	 * @author 1000068915
	 * @comments Realiza el login a RMS despues de ejecutar un JOB
	 */
	public void loginEnRMSAfterJob() throws FindFailed, InterruptedException {
		ImgPattern = GetPatternsImg(Fol);
		OpenBrowser();
		sk.screen.wait(ImgPattern.get("btn_max"), 15).highlight(1, "red").click();

		r.delay(25000);
		sk.screen.type(data.get("rms_user"));
		u.hardWait(1);
		sk.screen.type(Key.TAB);
		u.hardWait(1);
		sk.screen.type(PasswordUtil.decryptPassword(data.get("rms_ps")) + Key.TAB + Key.TAB);
		screenshot2();
		sk.screen.type(Key.ENTER);

		u.hardWait(1);

		sk.screen.wait(ImgPattern.get("menu"), 40);
		// sk.screen.wait(ImgPatter.get("btn_max"),15).highlight(1,"red").click();

//		u.hardWait(1);
//		sk.screen.wait(ImgPatter.get("btn_1"),15).highlight(1,"red").click();
//		u.hardWait(1);
//		sk.screen.wait(ImgPatter.get("btn_1"),15).highlight(1,"red").click();
//		u.hardWait(1);
		sk.screen.wait(ImgPattern.get("menu"), 40);
//		r.delay(15000);		

//		sk.screen.capture().save("img.png");
		sk.screen.wait(ImgPattern.get("btn_max"), 15).highlight(1, "red").click();

	}

	/**
	 * @author 1000072843
	 * @comment minimiza la pantalla de RMS
	 */
	public void minimize() throws FindFailed {
		// TODO Auto-generated method stub
		r.delay(3000);

		sk.screen.wait(ImgPattern.get("btn_max"), 15).highlight(1, "red").click();
	}

	/**
	 * @author 1000072843
	 * @comments Selecciona el folder Action, elige la opcion Organizational
	 *           Hierarchy y dar clic en el botón open =======
	 * @param navega al modulo de tiendas
	 * @throws FindFailed
	 * 
	 */
	public void Org_Herarky() throws FindFailed {
		try {
			waitImage(50, "lbl_action");
			u.hardWait(1);
			sk.screen.wait(ImgPattern.get("lbl_action"), 15).highlight("red").doubleClick();

			waitImage(50, "lbl_attrib");
			u.hardWait(1);
			sk.screen.wait(ImgPattern.get("lbl_attrib"), 15).highlight("red").doubleClick();
			u.hardWait(1);
			screenshot2();
			u.hardWait(1);

			Screen.highlightAllOff();
			waitImage(25, "jr_store");
			u.hardWait(1);
			screenshot2();
		} catch (Exception ex) {
			System.out.println(" -> " + ex);
			throw new AssertionError("Fallo al dirigirse a la pantalla Organizational Hierarchy");
		}
	}

	/**
	 * @author 1000068915
	 * @comments Selecciona el menu Action, elige la opcion Merchandise Hierarchy y
	 *           dar clic en open.
	 */
	public void MerchaHerar() throws FindFailed {
		r.delay(10000);

		sk.screen.wait(ImgPattern.get("lbl_action"), 15).highlight(1, "red").click();
		u.hardWait(1);
		sk.screen.wait(ImgPattern.get("lbl_MerchHer"), 15).highlight(1, "red").doubleClick();
		screenshot2();
	}

	/**
	 * @author 1000068915
	 * @comments Selecciona una opcion de la lista de companias
	 */
	public void selectCompania() throws FindFailed {
		// TODO Auto-generated method stub
		r.delay(10000);
		sk.screen.wait(ImgPattern.get("lbl_comerChile"), 15).highlight(1, "red").doubleClick();
		screenshot2();
	}

	/**
	 * @author 1000068915
	 * @comments Captura el supergrupo
	 */
	public void CapSuperGrupo() throws FindFailed {
		// TODO Auto-generated method stub lbl_superGrupo
		r.delay(6000);
		sk.screen.find(ImgPattern.get("lbl_superGrupo")).right(60).click();
		sk.screen.type(data.get("SuperGrupo") + Key.TAB);
		sk.screen.wait(ImgPattern.get("btn_filter"), 15).highlight(1, "red").click();
		u.hardWait(2);
		sk.screen.wait(ImgPattern.get("opt_general"), 15).doubleClick();
		// sk.screen.wait(ImgPatter.get("lbl_categoria"),15).highlight(1,"red").click();
		screenshot2();
	}

	/**
	 * @author 1000068915
	 * @comments Captura la categoria
	 */
	public void CapCatego() throws FindFailed {
		// TODO Auto-generated method stub lbl_superGrupo
		r.delay(6000);
		sk.screen.find(ImgPattern.get("box_categoria")).right(60).click();
		sk.screen.type(data.get("Categoria") + Key.TAB);
		sk.screen.wait(ImgPattern.get("btn_filter"), 15).highlight(1, "red").click();
		u.hardWait(2);
		sk.screen.wait(ImgPattern.get("opt_general"), 15).doubleClick();
		// sk.screen.wait(ImgPatter.get("lbl_subcategoria"),15).highlight(1,"red").click();
		screenshot2();
	}

	/**
	 * @author 1000068915
	 * @comments Captura la subcategoria
	 */
	public void CapsubCatego() throws FindFailed {
		// TODO Auto-generated method stub lbl_superGrupo
		r.delay(6000);
		sk.screen.find(ImgPattern.get("lbl_subcatego")).right(60).click();
		sk.screen.type(data.get("Subcategoria") + Key.TAB);
		sk.screen.wait(ImgPattern.get("btn_filter"), 15).highlight(1, "red").click();
		u.hardWait(2);
		sk.screen.wait(ImgPattern.get("opt_general"), 15).doubleClick();
		// sk.screen.wait(ImgPatter.get("lbl_segmento"),15).highlight(1,"red").click();
		screenshot2();
	}

	/**
	 * @author 1000068915
	 * @comments Captura segmento
	 */
	public void Capsegmento() throws FindFailed {
		// TODO Auto-generated method stub lbl_superGrupo
		r.delay(6000);
		sk.screen.find(ImgPattern.get("lbl_subcatego")).right(60).click();
		sk.screen.type(data.get("Subcategoria") + Key.TAB);
		sk.screen.find(ImgPattern.get("box_Segmento")).right(60).click();
		sk.screen.type(data.get("Segmento") + Key.TAB);
		sk.screen.wait(ImgPattern.get("btn_filter"), 15).highlight(1, "red").click();
		u.hardWait(2);
		sk.screen.wait(ImgPattern.get("opt_general"), 15).doubleClick();
		// sk.screen.wait(ImgPatter.get("lbl_Subseg"),15).highlight(1,"red").click();
		screenshot2();
	}

	/**
	 * @author 1000068915
	 * @comments Da clic en el boton New
	 */
	public void clicBtnNew() throws FindFailed {
		// TODO Auto-generated method stub lbl_superGrupo
		u.hardWait(2);
		sk.screen.type(Key.TAB + data.get("Subcategoria") + Key.TAB);
		sk.screen.type(data.get("Segmento") + Key.TAB);
//		sk.screen.find(ImgPatter.get("box_Segmento")).right(60).click();
		sk.screen.wait(ImgPattern.get("btn_new"), 15).highlight(1, "red").click();
		screenshot2();
	}

	/**
	 * @author 1000068915
	 * @comments Agrega la informacion de subsegmento
	 */
	public void addsubsegmento() throws FindFailed {
		r.delay(3000);
//		sk.screen.find(ImgPatter.get("lbl_subcatego")).right(60).click();

		sk.screen.wait(ImgPattern.get("btn_add_catego"), 15).highlight(1, "red").click();
		sk.screen.type(data.get("Segmento") + Key.TAB + data.get("SegmentoDesc"));
		sk.screen.wait(ImgPattern.get("btn_ok"), 15).highlight(1, "red").click();
		screenshot2();
	}

	/**
	 * 
	 * @author 1000072843
	 * @comments Selecciona la opcion Store
	 */
	public void OpenStore() {
		try {
			waitImage(50, "jr_store");
			u.hardWait(1);
			sk.screen.wait(ImgPattern.get("jr_store"), 15).highlight("red").click();
			u.hardWait(1);
			screenshot2();
			u.hardWait(1);

			Screen.highlightAllOff();
			waitImage(25, "box_store");
			u.hardWait(1);
			screenshot2();
		} catch (Exception ex) {
			System.out.println(" -> " + ex);
			throw new AssertionError("Fallo al seleccionar el campo Store");
		}

	}

	/**
	 * @author 1000072843
	 * @comments Selecciona el boton store, escribe la informacion en el campo y da
	 *           clic en el boton filtro
	 */
	public void CapturaTienta() {
		try {
			waitImage(25, "box_store");
			u.hardWait(1);
			sk.screen.wait(ImgPattern.get("box_store"), 15).highlight("red").click();
			u.hardWait(1);
			sk.screen.type(data.get("Store") + Key.TAB);
			sk.screen.wait(ImgPattern.get("btn_filter"), 15).highlight("red");
			u.hardWait(1);
			screenshot2();
			u.hardWait(3);
			sk.screen.wait(ImgPattern.get("btn_filter"), 15).doubleClick();

			Screen.highlightAllOff();
			waitImage(25, "results");
			u.hardWait(1);
			screenshot2();
		} catch (Exception ex) {
			System.out.println(" -> " + ex);
			throw new AssertionError("Fallo al capturar el numero de tienda");
		}

	}

	/**
	 * @author 1000072843
	 * @comments Modifica por el dato VATRegion y el area
	 */
	public void ModifVATRegion() {
		try {
			waitImage(25, "lbl_VATregion");
			sk.screen.find(ImgPattern.get("lbl_VATregion")).highlight("red").right(60).click();
			u.hardWait(1);
			sk.screen.type(data.get("VAT_Region"));

			waitImage(25, "lbl_area");
			sk.screen.find(ImgPattern.get("lbl_area")).highlight("red").right(60).click();
			u.hardWait(1);
			sk.screen.type(data.get("Area"));

			waitImage(25, "btn_OK_jer");
			sk.screen.wait(ImgPattern.get("btn_OK_jer"), 15).highlight("red");
			u.hardWait(1);
			screenshot2();
			u.hardWait(3);

			sk.screen.wait(ImgPattern.get("btn_OK_jer"), 15).click();

			Screen.highlightAllOff();
			for (int i = 0; i <= 20; i++) {
				try {
					sk.screen.find(ImgPattern.get("msg_LocSec")).click();
					u.hardWait(1);
					screenshot2();
					u.hardWait(1);

					sk.screen.type(Key.ENTER);
					break;
				} catch (Exception e) {
					System.out.println("Esperando mensaje de Location Security");
					u.hardWait(1);
				}
			}

			waitImage(25, "btn_close_jer");
			u.hardWait(1);
			screenshot2();
		} catch (Exception ex) {
			System.out.println(" -> " + ex);
			throw new AssertionError("Fallo al modificar los campos VAT Region y Area");
		}

	}

	public void NewItemVirtual() throws FindFailed {

//		r.delay(5000);	 ItemNewVirtual btn_ok
		sk.screen.wait(ImgPattern.get("ItemNewVirtual"), 20).click();
		u.hardWait(1);
		sk.screen.wait(ImgPattern.get("NewItemVirtual"), 20).click();
		screenshot2();
		sk.screen.type(data.get("Subcategoria") + Key.TAB);
		u.hardWait(2);
		sk.screen.type(data.get("Segmento") + Key.TAB);
		u.hardWait(2);
		sk.screen.type(data.get("Subsegmento") + Key.TAB);
		u.hardWait(2);
		sk.screen.type("No" + Key.TAB + Key.ENTER);
		u.hardWait(2);
//		sk.screen.wait(ImgPatter.get("btn_ok"),20).click();
		screenshot2();
	}

	/**
	 * @author 1000072843
	 * @comments Selecciona la opcion Edit
	 */
	public void ClicBtnEdit() {
		// TODO Auto-generated method stub

		try {
			waitImage(25, "btn_edit");
			u.hardWait(1);
			sk.screen.wait(ImgPattern.get("btn_edit"), 15).highlight("red");
			u.hardWait(1);
			screenshot2();
			u.hardWait(1);
			sk.screen.wait(ImgPattern.get("btn_edit"), 15).click();

			Screen.highlightAllOff();
			waitImage(25, "lbl_VATregion");
			u.hardWait(1);
			screenshot2();
		} catch (Exception ex) {
			System.out.println(" -> " + ex);
			throw new AssertionError("Fallo al dar clic en el boton Edit");
		}

	}

	/**
	 * @author 1000068915
	 * @comments Selecciona el folder items, elegir la opcion items y dar clic en el
	 *           botón open
	 */

	public void navItems() throws FindFailed {

		try {
			waitImage(25, "lbl_items_1");
			sk.screen.wait(ImgPattern.get("lbl_items_1"), 15).highlight("red").doubleClick();
			u.hardWait(1);

			waitImage(25, "lbl_items_2");
			sk.screen.wait(ImgPattern.get("lbl_items_2"), 40).highlight("red").doubleClick();
			u.hardWait(1);
			screenshot2();
			u.hardWait(1);

			Screen.highlightAllOff();
			waitImage(25, "action");
			u.hardWait(1);
			screenshot2();
		} catch (Exception ex) {
			System.out.println(" -> " + ex);
			throw new AssertionError("Fallo al dirigirse a la pantalla Item Find");
		}
	}

	/**
	 * @author 1000068915
	 * @comments Selecciona el folder items, elegir la opcion items y dar clic en el
	 *           botón open
	 */
	public void navItemList() throws FindFailed {
		try {

			waitImage(25, "lbl_items_1");
			u.hardWait(1);
			sk.screen.wait(ImgPattern.get("lbl_items_1"), 15).highlight("red").doubleClick();

			waitImage(25, "lbl_items_1");
			u.hardWait(1);
			sk.screen.wait(ImgPattern.get("lbl_item_List"), 15).highlight("red").doubleClick();
			u.hardWait(1);
			screenshot2();
			u.hardWait(1);

			Screen.highlightAllOff();
			waitImage(25, "actionItems");
			u.hardWait(1);
			screenshot2();

		} catch (Exception ex) {
			System.out.println(" -> " + ex);
			throw new AssertionError("Fallo al dirigirse a la pantalla Item List");
		}
	}

	/**
	 * @author 1000068915
	 * @comments Selecciona el folder items, elegir la opcion items y dar clic en el
	 *           botón open
	 */
	public void navItemsFEMSA() throws FindFailed {
		r.delay(5000);
		sk.screen.wait(ImgPattern.get("lbl_items_1"), 15).doubleClick();
		u.hardWait(2);
		sk.screen.wait(ImgPattern.get("itm_FEMSA"), 15).doubleClick();
		screenshot2();
	}

	/**
	 * @author 1000068915
	 * @comments Selecciona el folder items, elegir la opcion items y dar clic en el
	 *           botón open
	 */
	public void NewItem() throws FindFailed {
		try {

			waitImage(25, "actionItems");
			sk.screen.wait(ImgPattern.get("actionItems"), 1).highlight("red").click();
			u.hardWait(1);

			waitImage(25, "new_LL");
			u.hardWait(1);
			sk.screen.wait(ImgPattern.get("new_LL"), 1).click();
			u.hardWait(1);

			waitImage(25, "btnOK_itemList");
			u.hardWait(1);
			sk.screen.wait(ImgPattern.get("btnOK_itemList"), 1).highlight("red");

			u.hardWait(1);
			screenshot2();
			u.hardWait(1);

			sk.screen.wait(ImgPattern.get("btnOK_itemList"), 1).click();
			Screen.highlightAllOff();
			waitImage(25, "lbl_itemList");
			u.hardWait(1);
			screenshot2();

		} catch (Exception ex) {
			System.out.println(" -> " + ex);
			throw new AssertionError("Fallo al seleccionar la opcion New y presionar Ok");
		}
	}

	/**
	 * @author 1000068915
	 * @comments Seleccionar en el campo Action la opción New item
	 */
	public void NewItem_2() throws FindFailed {

//		r.delay(5000);	 ItemNewVirtual btn_ok
		sk.screen.wait(ImgPattern.get("ItemNewVirtual"), 20).click();
		u.hardWait(1);
		sk.screen.wait(ImgPattern.get("NewItemVirtual"), 20).click();
		screenshot2();
		sk.screen.type(data.get("Subcategoria") + Key.TAB);
		u.hardWait(2);
		sk.screen.type(data.get("Segmento") + Key.TAB);
		u.hardWait(2);
		sk.screen.type(data.get("Subsegmento") + Key.TAB);
		u.hardWait(2);
		sk.screen.type(data.get("PackItem") + Key.TAB + Key.ENTER);
		u.hardWait(2);
//		sk.screen.wait(ImgPatter.get("btn_ok"),20).click();
		screenshot2();
	}

	/**
	 * @author 1000068915
	 * @Comments Llena la pantalla de New Item
	 */
	public void NewItemVirtualYes() throws FindFailed {

//		r.delay(5000);	 ItemNewVirtual btn_ok
		sk.screen.wait(ImgPattern.get("ItemNewVirtual"), 20).click();
		u.hardWait(1);
		sk.screen.wait(ImgPattern.get("NewItemVirtual"), 20).click();
		screenshot2();
		sk.screen.type(data.get("Subcategoria") + Key.TAB);
		u.hardWait(2);
		sk.screen.type(data.get("Segmento") + Key.TAB);
		u.hardWait(2);
		sk.screen.type(data.get("Subsegmento") + Key.TAB);
		u.hardWait(2);
		sk.screen.type("Yes" + Key.TAB + Key.ENTER);
		u.hardWait(2);
//		sk.screen.wait(ImgPatter.get("btn_ok"),20).click();
		screenshot2();
	}

	/**
	 * @author 1000068915
	 * @comments Llenan los campos Description y Comments
	 */

	public void fillBox() throws FindFailed {
		try {
			waitImage(25, "lbl_itemList");
			sk.screen.wait(ImgPattern.get("lbl_itemList"), 1).highlight("red").right(200).click();
			u.hardWait(1);
			sk.screen.type(data.get("ItemName"));
			u.hardWait(1);

			waitImage(25, "lbl_Comments");
			sk.screen.wait(ImgPattern.get("lbl_Comments"), 1).highlight("red").right(20).click();
			u.hardWait(1);
			sk.screen.type(data.get("ItemComment"));

			u.hardWait(1);
			screenshot2();
			u.hardWait(1);

			Screen.highlightAllOff();

		} catch (Exception ex) {
			System.out.println(" -> " + ex);
			throw new AssertionError("Fallo al llenar los campos Item List y Comments");
		}
	}

	/**
	 * @author 1000068915
	 * @comments En el menu Options selecciona la opción select Items
	 */

	public void clicselecItem() throws FindFailed {
		try {
			waitImage(25, "mn_options_Items");
			u.hardWait(1);
			sk.screen.wait(ImgPattern.get("mn_options_Items"), 1).highlight("red").click();
			u.hardWait(1);

			waitImage(25, "select_Items");
			u.hardWait(1);
			sk.screen.wait(ImgPattern.get("select_Items"), 1).highlight("red");
			u.hardWait(1);
			screenshot2();
			u.hardWait(1);

			sk.screen.wait(ImgPattern.get("select_Items"), 1).click();

			Screen.highlightAllOff();
			waitImage(25, "lbl_itemAdd");
			u.hardWait(1);
			screenshot2();

		} catch (Exception ex) {
			System.out.println(" -> " + ex);
			throw new AssertionError("Fallo al dirigirse a la pantalla Select Item");
		}
	}

	/**
	 * @author 1000068915
	 * @comments Llena informacion de la pantalla AddItems
	 */

	public int addItems() throws FindFailed {
		try {
			int items = 0;
			waitImage(25, "lbl_itemAdd");
			sk.screen.wait(ImgPattern.get("lbl_itemAdd"), 1).below(10).click();

			String[] Items = data.get("ListItem").split(",");
			for (String item : Items) {
				sk.screen.type(item + Key.TAB);
				sk.screen.wait(ImgPattern.get("btn_add"), 15).click();
				items++;
			}

			u.hardWait(3);
			waitImage(25, "btn_ok");
			sk.screen.wait(ImgPattern.get("btn_ok"), 1).highlight("red");

			u.hardWait(1);
			screenshot2();
			u.hardWait(1);
			sk.screen.wait(ImgPattern.get("btn_ok"), 1).click();

			Screen.highlightAllOff();
			waitImage(25, "btn_OK_repasado");
			u.hardWait(1);
			screenshot2();

			return items;

		} catch (Exception ex) {
			System.out.println(" -> " + ex);
			throw new AssertionError("Fallo al ingresar los Items");
		}
	}

	/**
	 * @author 1000068915
	 * @comments Da clic en el boton OK de la pantalla Item
	 */

	public int valNoItems() throws FindFailed {
		try {
			waitImage(25, "lbl_totalItems");
			u.hardWait(1);
			sk.screen.wait(ImgPattern.get("lbl_totalItems"), 15).highlight("red").right(20).doubleClick();
			u.hardWait(2);

			sk.screen.keyDown(Key.CTRL);
			sk.screen.keyDown("c");
			sk.screen.keyUp("c");
			sk.screen.keyUp(Key.CTRL);
			cb = App.getClipboard();
			System.out.println(cb);

			int itemNo = Integer.parseInt(cb);
			return itemNo;

		} catch (Exception ex) {
			System.out.println(" -> " + ex);
			throw new AssertionError("Fallo al validar el numero total de items");
		}
	}
	
	/**
	 * @author 1000068915
	 * @comments Da clic en el boton OK de la pantalla Item
	 */

	public int valNoLocations() throws FindFailed {
		try {
			waitImage(25, "lbl_totalLocations");
			u.hardWait(1);
			sk.screen.wait(ImgPattern.get("lbl_totalLocations"), 15).highlight("red").right(20).doubleClick();
			u.hardWait(2);

			sk.screen.keyDown(Key.CTRL);
			sk.screen.keyDown("c");
			sk.screen.keyUp("c");
			sk.screen.keyUp(Key.CTRL);
			cb = App.getClipboard();
			System.out.println(cb);

			int itemNo = Integer.parseInt(cb);
			return itemNo;

		} catch (Exception ex) {
			System.out.println(" -> " + ex);
			throw new AssertionError("Fallo al validar el numero total de Locations");
		}
	}

	/**
	 * @author 1000068915
	 * @comments Da clic en el boton OK de la pantalla Item
	 */

	public void clicBtnOKItem() throws FindFailed {
		try {
			waitImage(25, "btn_OK_repasado");
			u.hardWait(1);
			sk.screen.wait(ImgPattern.get("btn_OK_repasado"), 15).highlight("red");

			u.hardWait(1);
			screenshot2();
			u.hardWait(1);

			sk.screen.wait(ImgPattern.get("btn_OK_repasado"), 15).click();

			Screen.highlightAllOff();
			waitImage(25, "lbl_actionIL");
			u.hardWait(1);

		} catch (Exception ex) {
			System.out.println(" -> " + ex);
			throw new AssertionError("Fallo al dar clic en el boton OK");
		}
	}

	/**
	 * @author 1000068915
	 * @comments Da clic en el boton OK de la pantalla Threshold
	 */

	public void clicBtnOKThreshold() throws FindFailed {
		r.delay(3000);
		sk.screen.wait(ImgPattern.get("threshold_OK"), 15).click();
	}

	/**
	 * @author 1000068915
	 * @comments Da clic en el boton YES de la pantalla Threshold
	 */

	public void clickYes() throws FindFailed {
		r.delay(3000);
		screenshot2();
		sk.screen.wait(ImgPattern.get("threshold_YES"), 15).click();
	}

	/**
	 * @author 1000068915
	 * @comments Navega en a la pantalla lotation List
	 */

	public void navLocList() throws FindFailed {
		try {

			waitImage(25, "lbl_control");
			sk.screen.wait(ImgPattern.get("lbl_control"), 15).highlight("red").doubleClick();
			u.hardWait(1);

			waitImage(25, "scrollBar");
			sk.screen.wait(ImgPattern.get("scrollBar"), 15).click();
			u.hardWait(1);

			waitImage(25, "lbl_locationList");
			sk.screen.wait(ImgPattern.get("lbl_locationList"), 15).highlight("red").doubleClick();
			u.hardWait(1);
			screenshot2();
			u.hardWait(1);

			Screen.highlightAllOff();
			waitImage(25, "lbl_action_LL");
			u.hardWait(1);
			screenshot2();
		} catch (Exception ex) {
			System.out.println(" -> " + ex);
			throw new AssertionError("Fallo al dirigirse a la pantalla CCFC - Suppline");
		}
	}

	/**
	 * @author 1000068915
	 * @param navega a la opcion Pricing del menu y busca Retailcos
	 */
	public void navPricingRetailCost() throws FindFailed {
		sk.screen.wait(ImgPattern.get("pricing"), 50).doubleClick();
		r.delay(5000);
		sk.screen.wait(ImgPattern.get("retailCostChange"), 30).doubleClick();
		r.delay(5000);

		screenshot2();
	}

	/**
	 * @author 1000068915
	 * @param Llena el formulario de RetailCost
	 */
	public void fillRetail() throws FindFailed {
//		r.delay(5000);	 ItemNewVirtual btn_ok
		sk.screen.wait(ImgPattern.get("slct_action"), 20).click();
		u.hardWait(1);
		sk.screen.wait(ImgPattern.get("Item_new"), 20).click();
		u.hardWait(1);
		sk.screen.wait(ImgPattern.get("origin")).click();
		u.hardWait(1);
		sk.screen.wait(ImgPattern.get("origin_retail")).click();
		u.hardWait(1);
		sk.screen.type(Key.TAB + data.get("retailZoneGroup") + Key.TAB);
		u.hardWait(1);
		sk.screen.type(data.get("itemType") + Key.TAB);
		u.hardWait(1);
		sk.screen.type(data.get("Item") + Key.TAB);
		u.hardWait(2);
		screenshot2();
		sk.screen.type(Key.ENTER);
	}

	/**
	 * @author 1000068915
	 * @param Llena el formulario de RetailCost
	 */
	public void fillCost() throws FindFailed {
//		r.delay(5000);	 ItemNewVirtual btn_ok
		sk.screen.wait(ImgPattern.get("slct_action"), 20).click();
		u.hardWait(1);
		sk.screen.wait(ImgPattern.get("Item_new"), 20).click();
		u.hardWait(1);
		sk.screen.wait(ImgPattern.get("origin")).click();
		u.hardWait(1);
		sk.screen.wait(ImgPattern.get("origin_cost")).click();
		u.hardWait(1);
		sk.screen.type(Key.TAB);
		u.hardWait(2);
		screenshot2();
		sk.screen.type(Key.ENTER);
	}

	/**
	 * @author 1000068915
	 * @param Llena el formulario de RetailCost Change Header
	 * @return
	 */
	public String fillRetailCostHeader() throws FindFailed {
		r.delay(5000);
		sk.screen.find(ImgPattern.get("priceChange_No")).right(60).click();
		u.hardWait(1);
		sk.screen.keyDown(Key.CTRL);
		sk.screen.keyDown("c");
		sk.screen.keyUp("c");
		sk.screen.keyUp(Key.CTRL);
		cb = App.getClipboard();
		System.out.println(cb);
		u.hardWait(2);
		sk.screen.type(Key.TAB);
		u.hardWait(2);
		sk.screen.type(data.get("price_change") + Key.TAB);
		u.hardWait(2);

		LocalDate diaActual = LocalDate.now();
		diaActual = diaActual.plusDays(0);
		DateTimeFormatter formatN = DateTimeFormatter.ofPattern("ddMMyy");
		String diaActualFormat = diaActual.format(formatN);

		sk.screen.type(diaActualFormat + Key.TAB);
		u.hardWait(2);

		sk.screen.type(data.get("reason") + Key.TAB);
		u.hardWait(2);

		diaActual = LocalDate.now();
		diaActual = diaActual.plusDays(1);
		diaActualFormat = diaActual.format(formatN);

		sk.screen.type(diaActualFormat + Key.TAB);
		u.hardWait(2);
		screenshot2();
		sk.screen.wait(ImgPattern.get("retailPriceChangeHeader_locations"), 20).click();
		u.hardWait(1);

		return cb;
	}

	/**
	 * @author 1000068915
	 * @param Llena el formulario de RetailCost Change Header
	 */
	public void fillRetailCostSupplier() throws FindFailed {
		r.delay(5000);
		sk.screen.find(ImgPattern.get("costChange_No")).right(60).click();
		u.hardWait(1);
		sk.screen.keyDown(Key.CTRL);
		sk.screen.keyDown("c");
		sk.screen.keyUp("c");
		sk.screen.keyUp(Key.CTRL);
		cb = App.getClipboard();
		System.out.println(cb);
		u.hardWait(2);
		sk.screen.type(Key.TAB);
		u.hardWait(2);
		sk.screen.type(data.get("costChange") + Key.TAB);
		u.hardWait(2);
		sk.screen.type(data.get("Supplier") + Key.TAB);
		u.hardWait(2);
		sk.screen.type(data.get("Item") + Key.TAB);
		u.hardWait(2);
		sk.screen.type(data.get("Country") + Key.TAB);
		u.hardWait(2);
		sk.screen.type(data.get("Reason") + Key.TAB);
		u.hardWait(2);
		sk.screen.type(data.get("effectiveDate"));
		u.hardWait(2);
		screenshot2();
		sk.screen.wait(ImgPattern.get("btn_search"), 20).click();
		u.hardWait(1);
	}

	/**
	 * @author 1000068915
	 * @param Llena el formulario de Price Change Locations Maintenance
	 */
	public void fillPriceChangeLocations() throws FindFailed {
		u.hardWait(2);
		sk.screen.type(Key.TAB);
		u.hardWait(2);
		sk.screen.type(data.get("value_IDZONE") + Key.TAB);
		u.hardWait(2);
		sk.screen.type(data.get("comments") + Key.TAB);
		u.hardWait(2);
		sk.screen.type(data.get("reason") + Key.TAB);
		u.hardWait(2);
		sk.screen.wait(ImgPattern.get("btn_apply"), 20).click();
		u.hardWait(1);
		screenshot2();
		sk.screen.wait(ImgPattern.get("btn_ok"), 20).click();
		u.hardWait(1);
	}

	/**
	 * @author 1000068915
	 * @param Llena el formulario de Price Change Locations Maintenance
	 */
	public void fillCostChangeLocations() throws FindFailed {
		u.hardWait(2);
		sk.screen.wait(ImgPattern.get("costChange_locations"), 20).click();
		u.hardWait(2);
		sk.screen.wait(ImgPattern.get("costChange_fixedCost"), 20).click();
		u.hardWait(2);
		sk.screen.type(Key.TAB);
		u.hardWait(2);
		sk.screen.type(data.get("fixedCost") + Key.TAB);
		u.hardWait(2);
		sk.screen.type(data.get("locType") + Key.TAB);
		u.hardWait(2);
		sk.screen.type(data.get("location"));
		u.hardWait(2);
		screenshot2();
		sk.screen.wait(ImgPattern.get("costChange_apply"), 20).click();
		u.hardWait(2);
		sk.screen.wait(ImgPattern.get("btn_OK_rayado"), 20).click();
		u.hardWait(2);
	}

	/**
	 * @author 1000068915
	 * @param Llenar el apartado de Event-Level PRice Change Default
	 */
	public void fillEventLevelPrice() throws FindFailed {
		u.hardWait(2);
		sk.screen.wait(ImgPattern.get("eventlevel_type"), 20).click();
		u.hardWait(1);
		sk.screen.wait(ImgPattern.get(data.get("changeType")), 20).click();
		u.hardWait(1);
		sk.screen.type(data.get("amount"));
		u.hardWait(2);
		sk.screen.wait(ImgPattern.get("adjust"), 20).click();
		u.hardWait(1);

		if (data.get("adjust") != null) {
			sk.screen.type(data.get("adjust"));
			u.hardWait(1);
			sk.screen.type(Key.TAB);
		} else {
			u.hardWait(1);
			sk.screen.type(Key.TAB);
		}

		u.hardWait(2);
		sk.screen.type(data.get("MultiUnits"));
		u.hardWait(2);
		sk.screen.type(Key.TAB);
		u.hardWait(2);
		sk.screen.type(data.get("MultiRetail"));
		u.hardWait(2);
		screenshot2();
		u.hardWait(1);
		sk.screen.wait(ImgPattern.get("btn_apply"), 20).click();
		r.delay(8000);
		// sk.screen.wait(ImgPatter.get("btn_1"), 20).click();
		// u.hardWait(1);
		sk.screen.wait(ImgPattern.get("item_rpch"), 20).click();
		u.hardWait(1);
	}

	/**
	 * @author 1000068915
	 * @param Llenar el apartado de Event-Level PRice Change Default
	 */
	public boolean verifyNewPrice() throws FindFailed {
		boolean result = false;
		u.hardWait(2);
		sk.screen.wait(ImgPattern.get("itemLocs_rpch"), 20).click();
		u.hardWait(2);
		sk.screen.wait(ImgPattern.get("currentPrice_rpch"), 20).below(30).click();
		u.hardWait(1);

		sk.screen.keyDown(Key.CTRL);
		sk.screen.keyDown("c");
		sk.screen.keyUp("c");
		sk.screen.keyUp(Key.CTRL);
		cb = App.getClipboard();

		r.delay(3000);
		sk.screen.wait(ImgPattern.get("newPrice_rpch"), 20).below(30).click();
		u.hardWait(1);

		sk.screen.keyDown(Key.CTRL);
		sk.screen.keyDown("c");
		sk.screen.keyUp("c");
		sk.screen.keyUp(Key.CTRL);
		String cb2 = App.getClipboard();
		System.out.println(cb + " " + cb2);

		if (cb == cb2) {
			screenshot2();
			result = false;
			testCase.addBoldTextEvidenceCurrentStep("Los precios son iguales");
		} else {
			screenshot2();
			result = true;
			testCase.addBoldTextEvidenceCurrentStep("El precio si cambio");
		}

		r.delay(3000);
		sk.screen.wait(ImgPattern.get("btn_ok"), 20).click();
		r.delay(3000);
		// sk.screen.wait(ImgPatter.get("btn_1"), 20).click();
		// r.delay(3000);
		sk.screen.wait(ImgPattern.get("btnOK_rpch"), 20).click();

		return result;
	}

	/**
	 * @author 1000068915
	 * @param navega a la opcion Control del menu y busca PriceZone Group
	 */
	public void navPriceZoneGroup() throws FindFailed {
		sk.screen.wait(ImgPattern.get("lbl_control"), 15).click();
		u.hardWait(1);
		sk.screen.find(ImgPattern.get("search")).right(60).click();
		u.hardWait(1);
		sk.screen.type("Price Zone Group");
		u.hardWait(1);
		sk.screen.wait(ImgPattern.get("find"), 15).click();
		r.delay(10000);
		boolean imagenEx = sk.screen.has(ImgPattern.get("img_found"));
		if (imagenEx) {
			sk.screen.type(Key.ENTER);
		}
		sk.screen.find(ImgPattern.get(data.get("Content_Of_PZG"))).right(60).click();
		u.hardWait(1);
		sk.screen.wait(ImgPattern.get("btn_open"), 15).click();
		r.delay(5000);

		screenshot2();
	}

	/**
	 * @author 1000068915
	 * @param Llena el formulario de Pricing Zone
	 */
	public void fillPricingZone() throws FindFailed {
		sk.screen.wait(ImgPattern.get("btn_add_pzg"), 15).click();
		u.hardWait(1);
		sk.screen.type(data.get("Zone_Group"));
		u.hardWait(1);
		sk.screen.find(ImgPattern.get("itm_pricing_level")).right(60).click();
		u.hardWait(1);
		sk.screen.find(ImgPattern.get("itm_zone")).right(60).click();
		u.hardWait(1);
		sk.screen.type(data.get("Description") + Key.TAB);
		u.hardWait(1);
		sk.screen.type(data.get("Lize_Zone_Group"));
		u.hardWait(1);
		sk.screen.wait(ImgPattern.get("btn_ok"), 15).click();
		u.hardWait(1);
		screenshot2();
	}

	/**
	 * @author 1000068915
	 * @comments Da clic en la opcion action
	 */
	public void clicActionNew() throws FindFailed {
		try {

			waitImage(25, "lbl_action_LL");

			sk.screen.find(ImgPattern.get("lbl_action_LL")).highlight("red").right(60).click();
			u.hardWait(1);

			waitImage(25, "new_LL");
			sk.screen.wait(ImgPattern.get("new_LL"), 15).click();
			u.hardWait(1);

			waitImage(25, "btnOK_LocationList");
			u.hardWait(1);
			sk.screen.wait(ImgPattern.get("btnOK_LocationList"), 1).highlight("red");

			u.hardWait(1);
			screenshot2();
			u.hardWait(1);

			sk.screen.wait(ImgPattern.get("btnOK_LocationList"), 1).click();

			Screen.highlightAllOff();
			waitImage(25, "lbl_ListNumber");
			u.hardWait(1);
			screenshot2();
		} catch (Exception ex) {
			System.out.println(" -> " + ex);
			throw new AssertionError("Fallo al dirigirse a la pantalla CCFC - Suppline");
		}
	}

	/**
	 * @author 1000068915
	 * @comments Se dirige a la la pantalla criteria
	 */
	public void criteria() throws FindFailed {
		try {
			waitImage(25, "mn_options_Items");
			u.hardWait(1);
			sk.screen.wait(ImgPattern.get("mn_options_Items"), 1).highlight("red").click();
			u.hardWait(1);

			waitImage(25, "criteria");
			u.hardWait(1);
			sk.screen.wait(ImgPattern.get("criteria"), 1).highlight("red");
			u.hardWait(1);
			screenshot2();
			u.hardWait(1);

			sk.screen.wait(ImgPattern.get("criteria"), 1).click();

			Screen.highlightAllOff();
			waitImage(25, "locationtype");
			u.hardWait(1);
			screenshot2();

		} catch (Exception ex) {
			System.out.println(" -> " + ex);
			throw new AssertionError("Fallo al dirigirse a la pantalla Criteria");
		}
	}

	/**
	 * @author 1000068915
	 * @comments Llenan el campo criteria
	 */
	public void Fillcriteria(String locationType) throws FindFailed {
		try {
			waitImage(25, "locationtype");
			sk.screen.wait(ImgPattern.get("locationtype"), 1).highlight("red").right(60).click();
			u.hardWait(1);

			sk.screen.type(locationType);
			u.hardWait(1);
			sk.screen.type(Key.TAB);
			u.hardWait(1);

			waitImage(25, "parentesisR");
			sk.screen.wait(ImgPattern.get("parentesisR"), 1).highlight("red").below(10).click();
			u.hardWait(1);
			sk.screen.type("(");
			u.hardWait(1);

			waitImage(25, "lbl_element");
			sk.screen.wait(ImgPattern.get("lbl_element"), 1).highlight("red").below(10).click();
			u.hardWait(1);
			sk.screen.type(data.get("Element") + Key.TAB);
			u.hardWait(1);

			waitImage(25, "lbl_comparison");
			sk.screen.wait(ImgPattern.get("lbl_comparison"), 1).highlight("red").below(10).click();
			u.hardWait(1);
			sk.screen.type(data.get("Comparison") + Key.TAB);
			u.hardWait(1);

			waitImage(25, "lbl_value");
			sk.screen.wait(ImgPattern.get("lbl_value"), 1).highlight("red").below(10).click();
			u.hardWait(1);
			sk.screen.type(data.get("value"));
			u.hardWait(1);

			waitImage(25, "parentesisL");
			sk.screen.wait(ImgPattern.get("parentesisL"), 1).highlight("red").below(10).click();
			u.hardWait(1);
			sk.screen.type(")");
			u.hardWait(1);

			waitImage(25, "btnOk_LocationListCriteria");
			sk.screen.wait(ImgPattern.get("btnOk_LocationListCriteria"), 1).highlight("red");

			u.hardWait(1);
			screenshot2();
			u.hardWait(1);

			sk.screen.wait(ImgPattern.get("btnOk_LocationListCriteria"), 1).click();

			Screen.highlightAllOff();
			waitImage(25, "lbl_ListNumber");
			u.hardWait(1);
			screenshot2();

		} catch (Exception ex) {
			System.out.println(" -> " + ex);
			throw new AssertionError("Fallo al Llenar la pantalla de Locations");
		}
	}

	/**
	 * @author 1000068915
	 * @comments Llenan los campos Description y Comments
	 */
	public String fillBox_LL() throws FindFailed {
		try {
			waitImage(25, "lbl_ListNumber");
			sk.screen.wait(ImgPattern.get("lbl_ListNumber"), 1).highlight("red").right(30).doubleClick();
			u.hardWait(3);
			sk.screen.keyDown(Key.CTRL);
			sk.screen.keyDown("c");
			sk.screen.keyUp("c");
			sk.screen.keyUp(Key.CTRL);
			cb = App.getClipboard();
			u.hardWait(1);

			sk.screen.wait(ImgPattern.get("lbl_ListNumber"), 1).right(200).click();
			u.hardWait(1);
			sk.screen.type(data.get("Desc"));
			u.hardWait(1);

			waitImage(25, "lbl_Comments");
			sk.screen.wait(ImgPattern.get("lbl_Comments"), 1).highlight("red").right(20).click();
			u.hardWait(1);
			sk.screen.type(data.get("Comment"));

			waitImage(25, "check_batchRebuildInd");
			sk.screen.wait(ImgPattern.get("check_batchRebuildInd"), 1).highlight("red").click();

			u.hardWait(1);
			screenshot2();
			u.hardWait(1);

			Screen.highlightAllOff();

			System.out.println(cb);
			return cb;
		} catch (Exception ex) {
			System.out.println(" -> " + ex);
			throw new AssertionError("Fallo al llenar los campos List y Comments");
		}
	}

	/**
	 * @author 1000068915
	 * @comments Llenan la pantalla de vendor
	 */

	public String fillvendor() throws FindFailed {
		try {

			waitImage(50, "Id_deal");
			sk.screen.wait(ImgPattern.get("Id_deal"), 10).highlight("red").right(60).click();
			u.hardWait(1);
			sk.screen.keyDown(Key.CTRL);
			sk.screen.keyDown("c");
			sk.screen.keyUp("c");
			sk.screen.keyUp(Key.CTRL);

			String dealId = App.getClipboard();

			u.hardWait(1);

			waitImage(50, "dealVendor");
			sk.screen.wait(ImgPattern.get("dealVendor"), 10).highlight("red").right(30).click();
			u.hardWait(1);
			sk.screen.type(data.get("Vendor1"));
			u.hardWait(1);
			sk.screen.type(Key.TAB);
			u.hardWait(1);

			sk.screen.type(data.get("Vendor2"));
			u.hardWait(1);
			sk.screen.type(Key.TAB);
			u.hardWait(1);

			waitImage(50, "dealCurrency");
			sk.screen.wait(ImgPattern.get("dealCurrency"), 10).highlight("red").right(30).click();
			u.hardWait(1);
			sk.screen.type(data.get("Currency"));
			u.hardWait(1);

			waitImage(50, "dealType");
			sk.screen.wait(ImgPattern.get("dealType"), 10).highlight("red").right(30).click();
			u.hardWait(1);
			sk.screen.type(data.get("Type"));
			u.hardWait(1);

			u.hardWait(1);

			LocalDate diaAyer = LocalDate.now();
			// diaAyer = diaAyer.minusDays(5);
			DateTimeFormatter formatNAyer = DateTimeFormatter.ofPattern("ddMMyy");
			String diaAyerFormat = diaAyer.format(formatNAyer);

			LocalDate diaActual = LocalDate.now();
			diaActual = diaActual.plusDays(30);
			DateTimeFormatter formatN = DateTimeFormatter.ofPattern("ddMMyy");
			String diaActualFormat = diaActual.format(formatN);

			waitImage(50, "dealActiveDate");
			sk.screen.wait(ImgPattern.get("dealActiveDate"), 10).highlight("red").right(30).click();
			u.hardWait(1);
			sk.screen.type(diaAyerFormat);
			u.hardWait(1);

			u.hardWait(1);

			waitImage(50, "dealCloseDate");
			sk.screen.wait(ImgPattern.get("dealCloseDate"), 10).highlight("red").right(30).click();
			u.hardWait(1);
			sk.screen.type(diaActualFormat);
			u.hardWait(1);

			if (data.get("recalcularAproveOrders").equals("N")) {
				waitImage(50, "dealRAD");
				sk.screen.wait(ImgPattern.get("dealRAD"), 10).highlight("red").click();
				u.hardWait(1);
			}

			u.hardWait(1);
			screenshot2();
			u.hardWait(1);
			Screen.highlightAllOff();

			sk.screen.wait(ImgPattern.get("btn_applyDeal"), 15).click();

			return dealId;

		} catch (Exception ex) {
			System.out.println(" -> " + ex);
			throw new AssertionError("Fallo al llenar la pantalla Deal Maintenance");
		}
	}

	/**
	 * @author 1000068915
	 * @comments Selecciona la opción Rebuild Location List y dar click en OK
	 */
	public void rebuild() throws FindFailed {

		try {
			waitImage(25, "mn_options_Items");
			u.hardWait(1);
			sk.screen.wait(ImgPattern.get("mn_options_Items"), 1).highlight("red").click();
			u.hardWait(1);

			waitImage(25, "criteria");
			u.hardWait(1);
			sk.screen.wait(ImgPattern.get("Rebuild_Location_List"), 1).highlight("red");
			u.hardWait(1);
			screenshot2();
			u.hardWait(1);

			sk.screen.wait(ImgPattern.get("Rebuild_Location_List"), 1).click();
			Screen.highlightAllOff();

			waitImage(25, "btnOK_RebuildLocationList");
			u.hardWait(1);

		} catch (Exception ex) {
			System.out.println(" -> " + ex);
			throw new AssertionError("Fallo al dirigirse a la pantalla Rebuild Location List");
		}
	}
	
	/**
	 * @author 1000068915
	 * @comments Selecciona la opción Rebuild Location List y dar click en OK
	 */
	public void clicBtnOk_RebuildLocationList() throws FindFailed {

		try {
			waitImage(25, "btnOK_RebuildLocationList");
			u.hardWait(1);
			sk.screen.wait(ImgPattern.get("btnOK_RebuildLocationList"), 1).highlight("red");

			u.hardWait(1);
			screenshot2();
			u.hardWait(1);

			sk.screen.wait(ImgPattern.get("btnOK_RebuildLocationList"), 1).click();

			Screen.highlightAllOff();
			waitImage(25, "lbl_ListNumber");
			
			u.hardWait(1);
			screenshot2();

		} catch (Exception ex) {
			System.out.println(" -> " + ex);
			throw new AssertionError("Fallo al dirigirse a la pantalla Rebuild Location List");
		}
	}


	/**
	 * @author 1000068915
	 * @comments Da clic en boton con el label OK
	 */
	public void clic_btn_OK() throws FindFailed {
		try {
			waitImage(25, "btnOKItems");
			u.hardWait(1);
			sk.screen.wait(ImgPattern.get("btnOKItems"), 1).highlight("red");

			u.hardWait(1);
			screenshot2();
			u.hardWait(1);

			sk.screen.wait(ImgPattern.get("btnOKItems"), 1).click();

			Screen.highlightAllOff();
			waitImage(25, "lbl_action_LL");
			
			u.hardWait(1);
			screenshot2();

		} catch (Exception ex) {
			System.out.println(" -> " + ex);
			throw new AssertionError("Fallo al seleccionar el boton OK en la pantalla Location List HEad");
		}
	}

	/**
	 * @author 1000068915
	 * @comments Da clic en un boton con el label OK y que esta subrayado
	 */
	public void clic_btn_OK_U() throws FindFailed {

		r.delay(10000);
		System.out.println("clic");
		sk.screen.type(Key.ENTER);
		u.hardWait(1);
	}

	/**
	 * @author 1000068915
	 * @comments Da clic en un boton con el label OK
	 */

	public void clic_btn_OK_skuretl() throws FindFailed {

		r.delay(3000);
//		sk.screen.wait(ImgPatter.get("lbl_optionCriteria"),15).click();
////		sk.screen.find(ImgPatter.get("lbl_optionCriteria")).left(60).click();
//		u.hardWait(1);
//		sk.screen.wait(ImgPatter.get("Rebuild_Location_List"),15).click();
//		screenshot2();
//		r.delay(3000);
//		sk.screen.type(Key.ENTER);
		sk.screen.wait(ImgPattern.get("btn_skuretl"), 15).click();
		screenshot2();

		u.hardWait(2);
	}

	/**
	 * @author 1000068915
	 * @comment Navega al menu Items, selecciona el submenú User Defined Atributes y
	 *          elige la opcion Edit
	 * @throws FindFailed
	 */
	public void navUDA() throws FindFailed {
		r.delay(5000);
		sk.screen.wait(ImgPattern.get("lbl_items_1"), 15).click();
		r.delay(5000);
		sk.screen.wait(ImgPattern.get("UDA"), 15).doubleClick();
		r.delay(5000);

	}

	/**
	 * @author 1000068915
	 * @comments Da clic en un boton addUDA
	 */
	public void clic_btn_addUDA() throws FindFailed {

		r.delay(3000);

//		r.delay(3000);	
//		sk.screen.wait(ImgPatter.get("lbl_optionCriteria"),15).click();
////		sk.screen.find(ImgPatter.get("lbl_optionCriteria")).left(60).click();
//		u.hardWait(1);
//		sk.screen.wait(ImgPatter.get("Rebuild_Location_List"),15).click();
//		screenshot2();
//		r.delay(3000);
//		sk.screen.type(Key.ENTER);

		sk.screen.wait(ImgPattern.get("btn_addUDA"), 15).click();
		screenshot2();
	}

	/**
	 * @author 1000068915
	 * @comments Llena la pantalla de UDA
	 */

	public void fillUDA() throws FindFailed {
		r.delay(3000);
		sk.screen.find(ImgPattern.get("flechaabajo")).right(60).click();
		u.hardWait(1);
		sk.screen.keyDown(Key.CTRL);
		sk.screen.keyDown("c");
		sk.screen.keyUp("c");
		sk.screen.keyUp(Key.CTRL);
		cb = App.getClipboard();
		sk.screen.type(Key.TAB);
		sk.screen.type(data.get("UDAdesc") + Key.TAB);
		u.hardWait(1);
		sk.screen.type(data.get("Dialog") + Key.TAB);
		u.hardWait(1);
		sk.screen.type(data.get("Displaytype") + Key.TAB);
		u.hardWait(1);
		sk.screen.type(data.get("DataType") + Key.TAB);
		u.hardWait(1);
		sk.screen.type(data.get("DataLength") + Key.TAB);
		u.hardWait(1);
		sk.screen.type(Key.SPACE + Key.TAB);
		u.hardWait(1);
		sk.screen.wait(ImgPattern.get("btn_addValue"), 15).click();

		u.hardWait(1);
		sk.screen.type(cb + Key.TAB);
		u.hardWait(1);
		clic_btn_OK();
		r.delay(5000);
		sk.screen.wait(ImgPattern.get("btn_ok_warning"), 15).click();
		r.delay(5000);
		screenshot2();
	}

	public void selectUDA() throws FindFailed {
		r.delay(3000);
//		sk.screen.find(ImgPatter.get("flechaabajo")).right(60).click();
//		u.hardWait(1);
//		sk.screen.keyDown(Key.CTRL);
//		sk.screen.keyDown("c");
//		sk.screen.keyUp("c");
//		sk.screen.keyUp(Key.CTRL);
//		cb = App.getClipboard();
//		sk.screen.type(Key.TAB);
//		sk.screen.type(data.get("UDAdesc")+Key.TAB);
//		u.hardWait(1);
//		sk.screen.type(data.get("Dialog")+Key.TAB);
//		u.hardWait(1);
//		sk.screen.type(data.get("Displaytype")+Key.TAB);
//		u.hardWait(1);
//		sk.screen.type(data.get("DataType")+Key.TAB);
//		u.hardWait(1);
//		sk.screen.type(data.get("DataLength")+Key.TAB);
//		u.hardWait(1);
//		sk.screen.type(Key.SPACE+Key.TAB);
//		u.hardWait(1);
		sk.screen.wait(ImgPattern.get("btn_addValue"), 15000).click();

//		u.hardWait(1);
//		sk.screen.type(data.get("UDAValues") + Key.TAB);
		u.hardWait(1);
		screenshot2();
	}

	public void AddUDAValue() throws FindFailed {
		r.delay(3000);
		sk.screen.type(data.get("UDAValues") + Key.TAB);
		u.hardWait(1);
		screenshot2();
	}

	public void NavOrdering() throws FindFailed {
		try {
			waitImage(50, "ordering");
			u.hardWait(1);
			sk.screen.wait(ImgPattern.get("ordering"), 15).highlight("red").doubleClick();
			waitImage(50, "lbl_dealsManagement");
			u.hardWait(1);
			sk.screen.wait(ImgPattern.get("lbl_dealsManagement"), 15).highlight("red").doubleClick();
			u.hardWait(1);
			screenshot2();
			u.hardWait(1);

			Screen.highlightAllOff();
			waitImage(25, "lbl_action_LL");
			u.hardWait(1);
			screenshot2();
		} catch (Exception ex) {
			System.out.println(" -> " + ex);
			throw new AssertionError("Fallo al dirigirse a la pantalla DealsManagement");
		}
	}

	/**
	 * @author 1000068915
	 * @comments Cierra la pantalla de RMS precionando un boton close y dando en la
	 *           opcion de cerrar
	 */

	public void close_OrgHierarchy() throws FindFailed {
		try {
			waitImage(25, "btn_close_jer");
			u.hardWait(1);
			sk.screen.wait(ImgPattern.get("btn_close_jer"), 15).highlight("red").click();

			Screen.highlightAllOff();
			waitImage(20, "menu");
			u.hardWait(1);
		} catch (Exception ex) {
			System.out.println(" -> " + ex);
			throw new AssertionError("Fallo al cerrar la pantalla Organization Hierarchy");
		}

	}

	/**
	 * @author 1000068915
	 * @comments Cierra la pantalla de RMS precionando un boton close y dando en la
	 *           opcion de cerrar
	 */

	public void close_RMS() throws FindFailed {
		try {

			sk.screen.wait(ImgPattern.get("btn_close_jer"), 15).highlight(1, "red").click();
			u.hardWait(1);
			sk.screen.wait(ImgPattern.get("mn_action"), 5).highlight(1, "red").click();
			sk.screen.wait(ImgPattern.get("smn_close"), 5).highlight(1, "red").click();

		} catch (Exception ex) {
			System.out.println(" -> " + ex);
			throw new AssertionError("Fallo al dirigirse a la pantalla DealsManagement");
		}

	}

	/**
	 * @author 1000068915
	 * @comments Cierra la pantalla de RMS repitiendo el paso 1 veces
	 */

	public void close_RMS_Menu() throws FindFailed {
		try {

			waitImage(150, "mn_action");
			u.hardWait(1);
			sk.screen.wait(ImgPattern.get("mn_action"), 60).highlight(1, "red").click();
			u.hardWait(1);

			waitImage(150, "smn_close");
			u.hardWait(1);
			sk.screen.wait(ImgPattern.get("smn_close"), 60).highlight(1, "red").click();
		} catch (Exception ex) {
			System.out.println(" -> " + ex);
			throw new AssertionError("Fallo al llenar cerrar RMS");
		}
	}

	/**
	 * @author 1000068915
	 * @comments Cierra la pantalla de RMS repitiendo el paso 3 veces
	 */
	public void close_RMS_3() throws FindFailed {

		u.hardWait(2);
		sk.screen.wait(ImgPattern.get("mn_action"), 5).highlight(1, "red").click();
		sk.screen.wait(ImgPattern.get("smn_close"), 5).highlight(1, "red").click();
		u.hardWait(2);
		sk.screen.wait(ImgPattern.get("mn_action"), 5).highlight(1, "red").click();
		sk.screen.wait(ImgPattern.get("smn_close"), 5).highlight(1, "red").click();
		u.hardWait(2);
		sk.screen.wait(ImgPattern.get("mn_action"), 5).highlight(1, "red").click();
		sk.screen.wait(ImgPattern.get("smn_close"), 5).highlight(1, "red").click();

	}

	/**
	 * @author 1000068915
	 * @comments Cierra la pantalla de RMS repitiendo el paso 2 veces
	 */
	public void close_RMS_2() throws FindFailed {
		try {

			waitImage(150, "mn_action");
			u.hardWait(1);
			sk.screen.wait(ImgPattern.get("mn_action"), 60).highlight(1, "red").click();
			u.hardWait(1);

			waitImage(150, "smn_close");
			u.hardWait(1);
			sk.screen.wait(ImgPattern.get("smn_close"), 60).highlight(1, "red").click();

			u.hardWait(1);
			waitImage(150, "mn_action");
			u.hardWait(1);
			sk.screen.wait(ImgPattern.get("mn_action"), 60).highlight(1, "red").click();
			u.hardWait(1);

			waitImage(150, "smn_close");
			u.hardWait(1);
			sk.screen.wait(ImgPattern.get("smn_close"), 60).highlight(1, "red").click();
			u.hardWait(1);
		} catch (Exception ex) {
			System.out.println(" -> " + ex);
			throw new AssertionError("Fallo al llenar cerrar RMS");
		}
	}

	/**
	 * @author 1000068915
	 * @comments Cierra la pantalla de RMS precionando un boton close y dando en la
	 *           opcion de cerrar
	 */
	public void close_RMS_lls() throws FindFailed {

		sk.screen.wait(ImgPattern.get("btn_close_lls"), 15).highlight(1, "red").click();
		u.hardWait(1);
		sk.screen.wait(ImgPattern.get("mn_action"), 5).highlight(1, "red").click();
		sk.screen.wait(ImgPattern.get("smn_close"), 5).highlight(1, "red").click();

	}

	/**
	 * @author 1000068915
	 * @comments Cierra la pantalla de RMS cerrando antes las pantallas de dialogo
	 *           de la ultima accion
	 */
	public void close_RMS_deals() throws FindFailed {

		sk.screen.wait(ImgPattern.get("btn_close_deals"), 15).highlight(1, "red").click();
		r.delay(8000);
		sk.screen.wait(ImgPattern.get("btn_yes_deals"), 15).highlight(1, "red").click();
		r.delay(8000);
		sk.screen.wait(ImgPattern.get("mn_action"), 5).highlight(1, "red").click();
		sk.screen.wait(ImgPattern.get("smn_close"), 5).highlight(1, "red").click();
		u.hardWait(2);
		sk.screen.wait(ImgPattern.get("mn_action"), 5).highlight(1, "red").click();
		sk.screen.wait(ImgPattern.get("smn_close"), 5).highlight(1, "red").click();

	}

	/**
	 * @author 1000068915
	 * @comments Cierra la pantalla de RMS repitiendo el paso 4 veces
	 */
	public void close_RMS_retail() throws FindFailed {

		sk.screen.wait(ImgPattern.get("btn_close_deals"), 15).highlight(1, "red").click();
		r.delay(8000);
		sk.screen.wait(ImgPattern.get("mn_action"), 5).highlight(1, "red").click();
		sk.screen.wait(ImgPattern.get("smn_close"), 5).highlight(1, "red").click();
		u.hardWait(2);
		sk.screen.wait(ImgPattern.get("mn_action"), 5).highlight(1, "red").click();
		sk.screen.wait(ImgPattern.get("smn_close"), 5).highlight(1, "red").click();
		u.hardWait(2);
		sk.screen.wait(ImgPattern.get("mn_action"), 5).highlight(1, "red").click();
		sk.screen.wait(ImgPattern.get("smn_close"), 5).highlight(1, "red").click();
		u.hardWait(2);
		sk.screen.wait(ImgPattern.get("mn_action"), 5).highlight(1, "red").click();
		sk.screen.wait(ImgPattern.get("smn_close"), 5).highlight(1, "red").click();

	}

	/**
	 * @author 1000068915
	 * @comments Cierra la pantalla de RMS cerrando antes las pantallas de dialogo
	 *           de la ultima accion
	 */

	public void close_RMS_RPCH() throws FindFailed {

		sk.screen.wait(ImgPattern.get("rpch_btn_OK"), 15).highlight(1, "red").click();
		u.hardWait(2);
		sk.screen.wait(ImgPattern.get("rpch_btn_OK"), 15).highlight(1, "red").click();
		u.hardWait(2);
		sk.screen.wait(ImgPattern.get("btn_ok"), 15).highlight(1, "red").click();
		u.hardWait(2);
		sk.screen.wait(ImgPattern.get("mn_action"), 5).highlight(1, "red").click();
		sk.screen.wait(ImgPattern.get("smn_close"), 5).highlight(1, "red").click();
		u.hardWait(2);
		sk.screen.wait(ImgPattern.get("mn_action"), 5).highlight(1, "red").click();
		sk.screen.wait(ImgPattern.get("smn_close"), 5).highlight(1, "red").click();

	}

	/**
	 * @author 1000068915
	 * @comments Toma un screenshot de la pantalla en curso
	 */

	public void screenshot() {
		Region window = App.focusedWindow(); // obtener la ventana con el focus
		ScreenImage image = sk.screen.capture(window); // toma la captura
		String savePath = Constants.BASE_PATH + "\\" + Constants.OUTPUT_IMAGES;
		String filename = "evidence" + numeroEvidencia;
		image.save(savePath, filename);
		String imgPath = Constants.OUTPUT_IMAGES + "\\" + filename + ".png";
		testCase.addScreenShotCurrentStep(imgPath, true);
		numeroEvidencia++;

	}

	/**
	 * @author 1000068915
	 * @comments Selecciona en el campo Action la opción New y presiona el botón OK
	 */
	public void select_actionNew() throws FindFailed {
		try {
			waitImage(50, "lbl_action_LL");
			sk.screen.find(ImgPattern.get("lbl_action_LL")).highlight("red").right(60).click();
			u.hardWait(1);
			sk.screen.wait(ImgPattern.get("new_LL"), 15).click();
			u.hardWait(1);
			screenshot2();
			u.hardWait(1);
			sk.screen.wait(ImgPattern.get("btnOKItems"), 15).click();
			Screen.highlightAllOff();
			waitImage(50, "Id_deal");
			u.hardWait(1);
			screenshot2();
			u.hardWait(1);

		} catch (Exception ex) {
			System.out.println(" -> " + ex);
			throw new AssertionError("Fallo al seleccionar el action en la pantalla Deal Find");
		}
	}

	/**
	 * @author 1000068915
	 * @comments En la sección Deal Components presiona el boton Insert
	 * @throws FindFailed
	 */
	public void clic_btn_Insert() throws FindFailed {
		try {
			waitImage(50, "btn_insert");
			u.hardWait(1);
			sk.screen.wait(ImgPattern.get("btn_insert"), 15).highlight("red").click();
			u.hardWait(1);
			screenshot2();
			u.hardWait(1);
			Screen.highlightAllOff();
			waitImage(20, "btn_next");
		} catch (Exception ex) {
			System.out.println(" -> " + ex);
			throw new AssertionError("Fallo al dar clic en el boton Insert");
		}

	}

	/**
	 * @author 1000068915
	 * @comments Llena la pantalla Deal Component Type
	 * @throws FindFailed
	 */
	public void fillDealComponentType() throws FindFailed {
		try {
			waitImage(50, "btn_next");
			sk.screen.wait(ImgPattern.get("btn_next"), 15).highlight("red");
			u.hardWait(1);
			screenshot2();
			u.hardWait(1);
			sk.screen.wait(ImgPattern.get("btn_next"), 15).click();

			waitImage(50, "dealTypeCode");
			sk.screen.wait(ImgPattern.get("dealTypeCode"), 10).highlight("red").right(30).click();
			u.hardWait(1);
			sk.screen.type(data.get("DealTypeCode"));
			u.hardWait(1);

			waitImage(50, "dealBillingType");
			sk.screen.wait(ImgPattern.get("dealBillingType"), 10).highlight("red").right(30).click();
			u.hardWait(1);
			sk.screen.type(data.get("BillingType"));
			u.hardWait(1);
			sk.screen.type(Key.TAB);
			u.hardWait(1);

			waitImage(50, "dealDealClass");
			sk.screen.wait(ImgPattern.get("dealDealClass"), 10).highlight("red").right(30).click();
			u.hardWait(1);
			sk.screen.type(data.get("DealClass"));
			u.hardWait(1);
			sk.screen.type(Key.TAB);

			u.hardWait(1);
			screenshot2();
			u.hardWait(1);
			Screen.highlightAllOff();
			sk.screen.wait(ImgPattern.get("btn_next_deal"), 15).click();

		} catch (Exception ex) {
			System.out.println(" -> " + ex);
			throw new AssertionError("Fallo al llenar la informacion de la pantalla Deal Component Type");
		}
	}

	/**
	 * @author 1000068915
	 * @comments Llena la pantalla Conten Threshold
	 * @throws FindFailed
	 */
	public void fillContentThreshold() throws FindFailed {
		try {

			waitImage(50, "dealThreslholdType");
			sk.screen.wait(ImgPattern.get("dealThreslholdType"), 10).highlight("red").right(30).click();
			u.hardWait(1);
			sk.screen.type(data.get("ThresholdValueType"));
			u.hardWait(1);
			sk.screen.type(Key.TAB);
			u.hardWait(1);

			waitImage(50, "dealThreslholdLimitType");
			sk.screen.wait(ImgPattern.get("dealThreslholdLimitType"), 10).highlight("red").right(30).click();
			u.hardWait(1);
			sk.screen.type(data.get("ThresholdLimitType"));
			u.hardWait(1);
			sk.screen.type(Key.TAB);
			u.hardWait(1);

			waitImage(50, "dealThreslholdLimitUOM");
			sk.screen.wait(ImgPattern.get("dealThreslholdLimitUOM"), 10).highlight("red").right(30).click();
			u.hardWait(1);
			sk.screen.type(data.get("ThresholdLimitUOM"));
			u.hardWait(1);
			sk.screen.type(Key.TAB);

			u.hardWait(1);
			screenshot2();
			u.hardWait(1);
			Screen.highlightAllOff();
			sk.screen.wait(ImgPattern.get("btn_next_deal"), 15).click();

		} catch (Exception ex) {
			System.out.println(" -> " + ex);
			throw new AssertionError("Fallo al llenar la informacion de la pantalla Threshold Information");
		}

	}

	/**
	 * @author 1000068915
	 * @comments Llena la pantalla Deal App
	 */
	public void fillDealApp() throws FindFailed {
		try {

			waitImage(50, "dealAplicationTiming");
			sk.screen.wait(ImgPattern.get("dealAplicationTiming"), 10).highlight("red").right(30).click();
			u.hardWait(1);
			sk.screen.type(data.get("DealApplicationTiming"));
			u.hardWait(1);

			waitImage(50, "dealCostApplyType");
			sk.screen.wait(ImgPattern.get("dealCostApplyType"), 10).highlight("red").right(30).click();
			u.hardWait(1);
			sk.screen.type(data.get("CostApplyType"));
			u.hardWait(1);

			if (data.get("PriceCostApplyInd").equals("Y")) {
				waitImage(50, "dealPriceCostApplyInd");
				sk.screen.wait(ImgPattern.get("dealPriceCostApplyInd"), 10).highlight("red").click();
				u.hardWait(1);
			}

			waitImage(50, "dealComments");
			sk.screen.wait(ImgPattern.get("dealComments"), 10).highlight("red").below(30).click();
			u.hardWait(1);
			sk.screen.type(data.get("Comments"));
			u.hardWait(1);
			sk.screen.type(Key.TAB);

			u.hardWait(1);
			screenshot2();
			u.hardWait(1);
			Screen.highlightAllOff();
			u.hardWait(1);
			sk.screen.wait(ImgPattern.get("btn_finish"), 15).click();

		} catch (Exception ex) {
			System.out.println(" -> " + ex);
			throw new AssertionError("Fallo al llenar la informacion de la pantalla Threshold Information");
		}

	}

	/**
	 * @author 1000068915
	 * @comments Da clic en el boton add en la pantalla de deal
	 */
	public void clic_btn_add() throws FindFailed {
		try {
			waitImage(50, "btn_add_deal");
			u.hardWait(1);
			sk.screen.wait(ImgPattern.get("btn_add_deal"), 15).highlight("red").click();
			u.hardWait(1);
			screenshot2();
			u.hardWait(1);
			Screen.highlightAllOff();
			waitImage(50, "lbl_merchLevel");

		} catch (Exception ex) {
			System.out.println(" -> " + ex);
			throw new AssertionError("Fallo al dar clic en el boton Add");
		}

	}

	/**
	 * @author 1000068915
	 * @comments Llena la pantalla creacion de Item
	 */
	public void fillItem() throws FindFailed {
		try {

			waitImage(50, "lbl_merchLevel");
			sk.screen.wait(ImgPattern.get("lbl_merchLevel"), 10).highlight("red").right(30).click();
			u.hardWait(1);
			waitImage(50, "slct_item");
			sk.screen.wait(ImgPattern.get("slct_item"), 15).click();
			u.hardWait(1);
			sk.screen.type(Key.TAB);
			u.hardWait(1);

			waitImage(50, "dealItem");
			sk.screen.wait(ImgPattern.get("dealItem"), 10).highlight("red").right(30).click();
			u.hardWait(1);
			sk.screen.type(data.get("Item"));
			u.hardWait(1);
			sk.screen.type(Key.TAB);
			u.hardWait(1);

		} catch (Exception ex) {
			System.out.println(" -> " + ex);
			throw new AssertionError("Fallo al llenar la pantalla de Add Deal Item");
		}

	}

	/**
	 * @author 1000068915
	 * @comments Llena la pantalla de Location dentro del modulo de ITEMS
	 */

	public void filLocation() throws FindFailed {
		u.hardWait(2);
		sk.screen.type(data.get("OrgLevel") + Key.TAB);
		sk.screen.wait(ImgPattern.get("WareHouse"), 15).click();
		sk.screen.type(Key.TAB + data.get("WareHouse") + Key.TAB);
		screenshot2();
		u.hardWait(2);

	}

	/**
	 * @author 1000068915
	 * @comments Llena la Origin Country dentro del modulo de ITEMs
	 */
	public void fillOriginCountry() throws FindFailed {
		u.hardWait(2);
		sk.screen.type(data.get("OriginCountry") + Key.TAB);
		sk.screen.wait(ImgPattern.get("btn_OK_DealAnual"), 15).click();
		screenshot2();

	}

	/**
	 * @author 1000068915
	 * @comments Llena la pantalla creacion de Item
	 */
	public void fillThresholds() throws FindFailed {

		try {
			waitImage(50, "dealThreslholdP");
			sk.screen.wait(ImgPattern.get("Threshold"), 30).highlight("red").click();
			u.hardWait(1);

			waitImage(50, "lowerLimit");
			sk.screen.find(ImgPattern.get("lowerLimit")).highlight("red").below(20).click();
			u.hardWait(1);
			sk.screen.type(data.get("LowerLimit"));
			u.hardWait(1);

			waitImage(50, "dealUpperLimit");
			sk.screen.find(ImgPattern.get("dealUpperLimit")).highlight("red").below(20).click();
			u.hardWait(1);
			sk.screen.type(data.get("UpperLimit"));
			u.hardWait(1);

			waitImage(50, "dealValueP");
			sk.screen.find(ImgPattern.get("dealValueP")).highlight("red").below(20).click();
			u.hardWait(1);
			sk.screen.type(data.get("Value"));

			if (data.get("TargetLvlInd").equals("Y")) {
				waitImage(50, "dealTargetlblInd");
				sk.screen.wait(ImgPattern.get("dealTargetlblInd"), 10).highlight("red").below(20).click();
				u.hardWait(1);
			}

			u.hardWait(1);
			screenshot2();
			u.hardWait(1);

			Screen.highlightAllOff();

			waitImage(50, "btn_save");
			sk.screen.find(ImgPattern.get("btn_save")).click();

			u.hardWait(5);

		} catch (Exception ex) {
			System.out.println(" -> " + ex);
			throw new AssertionError("Fallo al llenar el apartado Threshold");
		}
	}

	/**
	 * @author 1000068915
	 * @return
	 * @comments Navega al menu option y selecciona la opcion submit
	 */

	public boolean submit() {

		try {
			waitImage(150, "mn_options");
			u.hardWait(1);
			sk.screen.wait(ImgPattern.get("mn_options"), 15).click();

			waitImage(50, "mn_submit");
			sk.screen.wait(ImgPattern.get("mn_submit"), 15).highlight("red");
			u.hardWait(1);
			screenshot2();
			u.hardWait(1);

			sk.screen.wait(ImgPattern.get("mn_submit"), 60).click();
			u.hardWait(1);

			waitImage(50, "statusSubmitted");
			sk.screen.wait(ImgPattern.get("statusSubmitted"), 15).highlight("red");
			u.hardWait(1);
			screenshot2();
			u.hardWait(1);

			Screen.highlightAllOff();
		} catch (Exception ex) {
			System.out.println(" -> " + ex);
			throw new AssertionError("Fallo al llenar seleccionar la opcion Submit");
		}
		return false;

	}

	/**
	 * @author 1000068915
	 * @comment Metodo para dar submit dentro de la barra de tareas y dar clic en la
	 *          opcion YES para la pantalla informativa
	 */
	public void submitPrice() throws FindFailed {
		u.hardWait(2);
		sk.screen.wait(ImgPattern.get("mn_options"), 15).click();
		sk.screen.wait(ImgPattern.get("smn_submit"), 15).click();
		screenshot2();
		u.hardWait(2);
		sk.screen.wait(ImgPattern.get("rpch_btn_yes"), 15).click();

	}

	/**
	 * @author 1000068915
	 * @comment Metodo para dar submit dentro de la barra de tareas y dar clic en la
	 *          opcion NO y YES en los mensajes que se generan
	 */
	public void submitPromo() throws FindFailed {
		u.hardWait(2);
		sk.screen.wait(ImgPattern.get("mn_options"), 15).click();
		sk.screen.wait(ImgPattern.get("smn_submit"), 15).click();
		r.delay(5000);
		screenshot2();
		u.hardWait(2);
		sk.screen.wait(ImgPattern.get("btn_No"), 15).click();
		u.hardWait(2);
		sk.screen.wait(ImgPattern.get("rpch_btn_yes"), 15).click();
		u.hardWait(2);
	}

	/**
	 * @author 1000068915
	 * @comment Metodo para dar approve dentro de la barra de tareas
	 */
	public void approve() throws FindFailed {

		try {

			waitImage(150, "mn_options");
			u.hardWait(1);
			sk.screen.wait(ImgPattern.get("mn_options"), 80).click();

			waitImage(150, "itm_approve");
			sk.screen.wait(ImgPattern.get("itm_approve"), 15).highlight("red");
			u.hardWait(1);
			screenshot2();
			u.hardWait(1);
			sk.screen.wait(ImgPattern.get("itm_approve"), 100).click();

			waitImage(150, "statusApproved");
			sk.screen.wait(ImgPattern.get("statusApproved"), 15).highlight("red");
			u.hardWait(1);
			screenshot2();
			u.hardWait(1);

			Screen.highlightAllOff();

		} catch (Exception ex) {
			System.out.println(" -> " + ex);
			throw new AssertionError("Fallo al llenar seleccionar la opcion Submit");
		}

	}

	/**
	 * @author 1000068915
	 * @comment Metodo para dar approve dentro de la barra de tareas y dar clic en
	 *          la opcion YES para las dos pantalla informativa
	 */
	public void approveRetailPrice() throws FindFailed {

		u.hardWait(2);
		sk.screen.wait(ImgPattern.get("mn_options"), 15).click();
		sk.screen.wait(ImgPattern.get("itm_approve"), 15).click();
		u.hardWait(2);
		sk.screen.wait(ImgPattern.get("rpch_btn_yes"), 15).click();
		u.hardWait(2);
		sk.screen.wait(ImgPattern.get("rpch_btn_yes"), 15).click();
		screenshot2();

	}

	/**
	 * @author 1000068915
	 * @comments Abre la carpeta raíz llamada Control
	 */
	public void OpenControl() throws FindFailed {
		// TODO Auto-generated method stub SubSegNum
		r.delay(5000);
		sk.screen.wait(ImgPattern.get("lbl_control_1"), 15).doubleClick();
		u.hardWait(1);
		screenshot2();

	}

	/**
	 * @author 1000068915
	 * @comments  Escoge la carpeta de “Setup” del menu principal
	 */
	public void Open_Setup() throws FindFailed {
		// TODO Auto-generated method stub
		r.delay(5000);
		sk.screen.wait(ImgPattern.get("folder_setup_1"), 15).doubleClick();
		u.hardWait(1);
		screenshot2();
	}

	/**
	 * @author 1000068915
	 * @comments Escoge la carpeta de Price Zone Group del menú principal
	 */
	public void OpenPriceZoneGroup() throws FindFailed {
		// TODO Auto-generated method stub
		r.delay(5000);
		sk.screen.wait(ImgPattern.get("folder_PZG"), 15).doubleClick();
		u.hardWait(1);
		screenshot2();
	}

	/**
	 * @author 1000068915
	 * @comments Agrega informacion en el campo de categoria
	 */

	public void addcatego() throws FindFailed {
		// TODO Auto-generated method stub btn_add_seg
		r.delay(3000);
		sk.screen.wait(ImgPattern.get("btn_add_seg"), 15).click();
		u.hardWait(1);
		sk.screen.type(data.get("SubSegNum") + Key.TAB);
		u.hardWait(1);
		sk.screen.type(data.get("SegmentoDesc") + Key.TAB);
		u.hardWait(1);
		screenshot2();
		sk.screen.type(Key.ENTER);

	}

	/**
	 * @author 1000068915
	 * @comments Selecciona la opción Edit en Price Zone Group
	 */

	public void clicEditZG() throws FindFailed {
		// TODO Auto-generated method stub
		r.delay(3000);
		sk.screen.wait(ImgPattern.get("lbl_EditZG"), 15).doubleClick();
		screenshot2();

	}

	/**
	 * @author 1000068915
	 * @comments Selecciona la opción Select en Price Zone Group
	 */
	public void SelectGZ() throws FindFailed {
		// TODO Auto-generated method stub
		r.delay(5000);
		sk.screen.find(ImgPattern.get("lbl_ZG")).below(35).click();
		screenshot2();

	}

	/**
	 * @author 1000068915
	 * @comments da clic en el boton Zones
	 */
	public void DoubleClicBTNZones() throws FindFailed {
		// TODO Auto-generated method stub
		r.delay(5000);
		sk.screen.wait(ImgPattern.get("btn_zones"), 15).click();
		screenshot2();

	}

	public void select_stores() throws FindFailed {
		// TODO Auto-generated method stub lbl_store
		r.delay(3000);
		sk.screen.find(ImgPattern.get("lbl_zoneID")).below(60).click();
		sk.screen.wait(ImgPattern.get("btn_stores_ZG"), 15).click();
		screenshot2();
		r.delay(5000);
		sk.screen.find(ImgPattern.get("lbl_store")).below(35).click();
		sk.screen.type(Key.TAB + Key.TAB + data.get("IDZG") + Key.TAB + data.get("PrimaryStore") + Key.TAB
				+ data.get("ED") + Key.TAB);
		sk.screen.type(Key.ENTER);
		r.delay(3000);
		sk.screen.type(Key.ENTER);
		screenshot2();

	}

	/**
	 * @author 1000068915
	 * @comments Selecciona el menu Control, elegir la opción Supplier y después la
	 *           opción Maintenance
	 */

	public void NavMaintenance() throws FindFailed {
		// TODO Auto-generated method stub
		r.delay(5000);
		sk.screen.wait(ImgPattern.get("lbl_control_1"), 15).click();
		r.delay(3000);
		sk.screen.wait(ImgPattern.get("lbl_supplier_m"), 15).doubleClick();
		r.delay(3000);
		sk.screen.wait(ImgPattern.get("lbl_maintence"), 15).doubleClick();
		screenshot2();
	}

	/**
	 * @author 1000068915
	 * @comments Selecciona en el campo Action la opción Edit y seleccionar el
	 *           proveedor y dar click en el botón OK
	 */
	public void selectActionDir() throws FindFailed {
		// TODO Auto-generated method stub

		r.delay(5000);
		sk.screen.wait(ImgPattern.get("actionItems"), 15).click();
		sk.screen.wait(ImgPattern.get("slct_edit_dir"), 15).click();
		r.delay(3000);
		sk.screen.type(Key.TAB);
		u.hardWait(2);
		sk.screen.type(data.get("Supplier") + Key.TAB);
		screenshot2();
		u.hardWait(1);
		sk.screen.type(Key.ENTER);

	}

	/**
	 * Metodo para llenar el campo pack no dentro del formulario de FEMSA en la
	 * crpeta de Items
	 * 
	 * @throws FindFailed
	 */
	public void fillPackNo() throws FindFailed {
		// TODO Auto-generated method stub

		r.delay(5000);
		sk.screen.type(data.get("packNo") + Key.TAB);
		u.hardWait(2);

		sk.screen.keyDown(Key.CTRL);
		sk.screen.keyDown("c");
		sk.screen.keyUp("c");
		sk.screen.keyUp(Key.CTRL);
		cb = App.getClipboard();

		System.out.println(cb);

		sk.screen.type(data.get("quantity"));
		u.hardWait(2);
		sk.screen.wait(ImgPattern.get("FEMSAITM_apply"), 15).click();
		u.hardWait(2);
		sk.screen.wait(ImgPattern.get("btn_ok_location"), 15).click();

	}

	/**
	 * @author 1000068915
	 * @comments En la barra de navegación, en el menú Options seleccionar la opción
	 *           Address
	 */
	public void clicAddress() throws FindFailed {
		// TODO Auto-generated method stub
		r.delay(3000);
		sk.screen.wait(ImgPattern.get("lbl_optionCriteria"), 15).click();
		sk.screen.wait(ImgPattern.get("mn_address"), 15).click();
	}

	/**
	 * @author 1000068915
	 * @comments En el campo Address Type selecciona la opción 01 (Business) y
	 *           modifica los siguientes campos: address, city, state, country,
	 *           name, pone, fax, email
	 */

	public void editAddress01() throws FindFailed {
		// TODO Auto-generated method stub

		r.delay(5000);
		sk.screen.wait(ImgPattern.get("lbl_addressType"), 15).click();
		sk.screen.find(ImgPattern.get("lbl_addressType")).right(45).click();
		u.hardWait(1);
		sk.screen.type(data.get("Adtype") + Key.TAB);
		u.hardWait(1);
		sk.screen.type(data.get("address1") + Key.TAB);
		u.hardWait(1);
		sk.screen.type(data.get("address2") + Key.TAB);
		u.hardWait(1);
		sk.screen.type(data.get("address3") + Key.TAB);
		u.hardWait(1);
		sk.screen.type(data.get("city") + Key.TAB);
		u.hardWait(1);
		sk.screen.type(data.get("state") + Key.TAB);
		u.hardWait(1);
		sk.screen.type(data.get("PC") + Key.TAB);
		u.hardWait(1);
		sk.screen.type(data.get("country") + Key.TAB);
		u.hardWait(1);
		sk.screen.type(data.get("name") + Key.TAB);
		u.hardWait(1);
		sk.screen.type(data.get("phone") + Key.TAB);
		u.hardWait(1);
		sk.screen.type(data.get("fax") + Key.TAB + Key.TAB);
		u.hardWait(1);
		sk.screen.type(data.get("email") + Key.TAB);
		u.hardWait(1);
		screenshot2();
		u.hardWait(2);

	}

	/**
	 * @author 1000068915
	 * @comments En el campo Address Type selecciona la opción 04 (Order) y modifica
	 *           los siguientes campos: address, city, state, country, name, pone,
	 *           fax, email
	 */

	public void editAddress02() throws FindFailed {
		// TODO Auto-generated method stub

		r.delay(5000);
		sk.screen.wait(ImgPattern.get("lbl_addressType"), 15).click();
		sk.screen.find(ImgPattern.get("lbl_addressType")).right(45).click();
		r.delay(5000);
		sk.screen.type(data.get("Adtype1") + Key.TAB);
		r.delay(15000);
		sk.screen.wait(ImgPattern.get("btn_yes_retail"), 15).click();
		r.delay(8000);
		sk.screen.type(data.get("address11") + Key.TAB);
		u.hardWait(1);
		sk.screen.type(data.get("address21") + Key.TAB);
		u.hardWait(1);
		sk.screen.type(data.get("address31") + Key.TAB);
		u.hardWait(1);
		sk.screen.type(data.get("city1") + Key.TAB);
		u.hardWait(1);
		sk.screen.type(data.get("state1") + Key.TAB);
		u.hardWait(1);
		sk.screen.type(data.get("PC1") + Key.TAB);
		u.hardWait(1);
		sk.screen.type(data.get("country1") + Key.TAB);
		u.hardWait(1);
		sk.screen.type(data.get("name1") + Key.TAB);
		u.hardWait(1);
		sk.screen.type(data.get("phone1") + Key.TAB);
		u.hardWait(1);
		sk.screen.type(data.get("fax1") + Key.TAB + Key.TAB);
		u.hardWait(1);
		sk.screen.type(data.get("email1") + Key.TAB);
		u.hardWait(1);
		screenshot2();
		u.hardWait(2);

	}

	/**
	 * @author 1000068915
	 * @comments En el campo Address Type selecciona la opción 03 (Returns) y
	 *           modifica los siguientes campos: address, city, state, country,
	 *           name, pone, fax, email
	 */

	public void editAddress03() throws FindFailed {
		// TODO Auto-generated method stub

		r.delay(5000);
		sk.screen.wait(ImgPattern.get("lbl_addressType"), 15).click();
		sk.screen.find(ImgPattern.get("lbl_addressType")).right(45).click();
		u.hardWait(1);
		sk.screen.type(data.get("Adtype12") + Key.TAB);
		r.delay(5000);
		sk.screen.wait(ImgPattern.get("btn_yes_retail"), 15).click();
		r.delay(8000);
		sk.screen.type(data.get("address112") + Key.TAB);
		u.hardWait(1);
		sk.screen.type(data.get("address212") + Key.TAB);
		u.hardWait(1);
		sk.screen.type(data.get("address312") + Key.TAB);
		u.hardWait(1);
		sk.screen.type(data.get("city12") + Key.TAB);
		u.hardWait(1);
		sk.screen.type(data.get("state12") + Key.TAB);
		u.hardWait(1);
		sk.screen.type(data.get("PC12") + Key.TAB);
		u.hardWait(1);
		sk.screen.type(data.get("country12") + Key.TAB);
		u.hardWait(1);
		sk.screen.type(data.get("name12") + Key.TAB);
		u.hardWait(1);
		sk.screen.type(data.get("phone12") + Key.TAB);
		u.hardWait(1);
		sk.screen.type(data.get("fax12") + Key.TAB + Key.TAB);
		u.hardWait(1);
		sk.screen.type(data.get("email12") + Key.TAB);
		u.hardWait(1);
		screenshot2();
		u.hardWait(2);

	}

	/**
	 * @author 1000068915
	 * @comments Da clic en el boton OK del modulo modificar direccion de
	 *           proveedores
	 */

	public void clic_btn_OK_dir() throws FindFailed {

		r.delay(3000);
//		sk.screen.wait(ImgPatter.get("lbl_optionCriteria"),15).click();
////		sk.screen.find(ImgPatter.get("lbl_optionCriteria")).left(60).click();
//		u.hardWait(1);
//		sk.screen.wait(ImgPatter.get("Rebuild_Location_List"),15).click();
//		screenshot2();
//		r.delay(3000);
//		sk.screen.type(Key.ENTER);
		sk.screen.wait(ImgPattern.get("btn_ok_slctd"), 15).click();
		screenshot2();
	}

	/*
	 * funciones principales
	 * 
	 */

	public void clicImage(String fol, String Img) {

	}

	/**
	 * <<<<<<< HEAD
	 * 
	 * @author 1000068915
	 * @comments Selecciona el menu Control,y dentro Setup y la opcion Price Zone
	 *           Group y presiona new =======
	 * @param ATC_FT_020_CrearNuevaZonadePrecio.java
	 * @throws FindFailed >>>>>>> cf3f884f6677018034634a12bdb3f1ef15714d25
	 */

	public void NavOPZ() throws FindFailed {
		// TODO Auto-generated method stub
		sk.screen.wait(ImgPattern.get("lbl_control"), 20).highlight(1, "red").doubleClick();
		r.delay(5000);
		sk.screen.wait(ImgPattern.get("folder_setup_1"), 20).highlight(1, "red").doubleClick();
		r.delay(3000);
		sk.screen.wait(ImgPattern.get("folder_PZG"), 20).highlight(1, "red").doubleClick();
		r.delay(3000);
		sk.screen.wait(ImgPattern.get("lbl_new"), 20).highlight(1, "red").doubleClick();
		r.delay(3000);
		screenshot2();
	}

	/**
	 * @author 1000068915
	 * @comments Selecciona el menu Control, y dentro Setup y la opcion Price Zone
	 *           Group y presiona edit
	 */
	public void NavOPZ_edit() throws FindFailed {
		// TODO Auto-generated method stub
		sk.screen.wait(ImgPattern.get("lbl_control"), 20).highlight(1, "red").doubleClick();
		r.delay(5000);
		sk.screen.wait(ImgPattern.get("folder_setup_1"), 20).highlight(1, "red").doubleClick();
		r.delay(3000);
		sk.screen.wait(ImgPattern.get("folder_PZG"), 20).highlight(1, "red").doubleClick();
		r.delay(3000);
		sk.screen.wait(ImgPattern.get("lbl_EditZG"), 20).highlight(1, "red").doubleClick();
		r.delay(3000);
		screenshot2();
	}

	/**
	 * @author 1000068915
	 * @comments Da clic al boton add y captura información
	 */
	public void clicbtnaddNZP() throws FindFailed {
		sk.screen.wait(ImgPattern.get("btn_add"), 15000).highlight(1, "red").click();
		u.hardWait(2);
		sk.screen.wait(ImgPattern.get("currentZone"), 20).above(30).click();
		u.hardWait(2);

		sk.screen.keyDown(Key.CTRL);
		sk.screen.keyDown("c");
		sk.screen.keyUp("c");
		sk.screen.keyUp(Key.CTRL);
		cb = App.getClipboard();

		int cbi = Integer.parseInt(cb);
		cbi = cbi + 1;
		cb = cbi + "";

		System.out.println(cb);
		u.hardWait(2);
		sk.screen.wait(ImgPattern.get("btn_add"), 15000).highlight(1, "red").click();
		u.hardWait(2);
		sk.screen.type(cb + Key.TAB + data.get("pricingLevel") + data.get("Desc") + Key.TAB + data.get("LikeZG"));
		u.hardWait(2);
		sk.screen.wait(ImgPattern.get("btn_zones"), 50).highlight(1, "red").click();
		u.hardWait(2);
		screenshot2();

	}

	/**
	 * @author 1000068915 Da clic al boton OK dentro del modulo de ZONA DE PRECIO
	 * @throws FindFailed
	 */
	public void clicbtnOkUL() throws FindFailed {
		// TODO Auto-generated method stub btn_ok_store_underline
		sk.screen.wait(ImgPattern.get("btn_OK_PZG"), 20).highlight(1, "red").click();
		screenshot2();
	}

	/**
	 * @author 1000068915
	 * @comments Capturar los datos correspondientes en los campos package RMS.Size,
	 *           package RMS.UOM y Dar clic en el botón OK.
	 */
	public void fil_ItemVirtual() throws FindFailed {

		sk.screen.wait(ImgPattern.get("ItemType"), 20);
		sk.screen.type(data.get("item_type") + Key.TAB + "Level 1");
		u.hardWait(2);
		sk.screen.type(Key.TAB); // Eaches
		u.hardWait(1);
		// sk.screen.type(Key.TAB); //Eache
		// u.hardWait(1);
		sk.screen.type(data.get("Item") + Key.TAB);
		u.hardWait(2);
		sk.screen.type(data.get("item_desc") + Key.TAB);
		u.hardWait(2);
		sk.screen.type("descripcion corta");
		u.hardWait(2);

		// merchandise
		if (data.get("merchandise").equals("true")) {
			sk.screen.find(ImgPattern.get("merchandise_CHECK")).click();
			r.delay(1500);
			sk.screen.find(ImgPattern.get("merchandise")).click();
			r.delay(1500);
		}

		// forecastable
		if (data.get("forecastable").equals("true")) {
			sk.screen.find(ImgPattern.get("forescastable")).click();
			r.delay(1500);
		}

		// Catch Weight
		if (data.get("catch_weihgt").equals("true")) {
			sk.screen.find(ImgPattern.get("catchWeight")).click();
			r.delay(1500);
		}

		// Constant Dimensions
		if (data.get("constant_dimensions").equals("true")) {
			sk.screen.find(ImgPattern.get("constantDimensions")).click();
			r.delay(1500);
		}

		sk.screen.type(Key.TAB + data.get("retail_zone") + Key.TAB); // Retail Zone
		u.hardWait(2);
		sk.screen.type(data.get("cost_zone") + Key.TAB + Key.TAB); // Cost Zone
		u.hardWait(2);
		sk.screen.type(data.get("Suggested Retail") + Key.TAB + data.get("Selling Unit Retail") + Key.TAB);
//	sk.screen.find(ImgPatter.get(""))
		u.hardWait(2);
		sk.screen.type(Key.TAB); // Eaches
		u.hardWait(1);
		sk.screen.type(Key.TAB); // Standard UOM
		u.hardWait(1);
		sk.screen.find(ImgPattern.get("itm_internalCode")).right(10).click();
		u.hardWait(2);
		sk.screen.type(data.get("internalCode")); // internalcode
		u.hardWait(2);

		screenshot2();
		sk.screen.wait(ImgPattern.get("btn_grocAttributes"), 50).highlight(1, "red").click();

	}

	public void fil_ItemVirtual_SinItem() throws FindFailed {

		sk.screen.wait(ImgPattern.get("ItemType"), 20); // Retek Iem Number
		sk.screen.type(data.get("item_type") + Key.TAB + "Level 1");
		u.hardWait(2);
		sk.screen.type(Key.TAB); // Eaches
		u.hardWait(1);
		// sk.screen.type(Key.TAB); //Eache
		// u.hardWait(1);

		sk.screen.keyDown(Key.CTRL);
		sk.screen.keyDown("c");
		sk.screen.keyUp("c");
		sk.screen.keyUp(Key.CTRL);
		cb = App.getClipboard();

		sk.screen.type(Key.TAB);
		u.hardWait(2);
		sk.screen.type(data.get("item_desc") + Key.TAB);
		u.hardWait(2);
		sk.screen.type("descripcion corta" + Key.TAB);
		u.hardWait(2);
		sk.screen.type(Key.TAB); // merchandise
		u.hardWait(2);
		sk.screen.type(Key.SPACE); // forecastable
		u.hardWait(2);
		sk.screen.type(Key.TAB + Key.SPACE); // Catch Weight
		u.hardWait(2);
		sk.screen.type(Key.TAB + Key.SPACE); // Constant Dimensions
		u.hardWait(2);
		sk.screen.type(Key.TAB + data.get("retail_zone") + Key.TAB); // Retail Zone
		u.hardWait(2);
		sk.screen.type(data.get("cost_zone") + Key.TAB + Key.TAB); // Cost Zone
		u.hardWait(2);
		sk.screen.type(data.get("Suggested Retail") + Key.TAB + data.get("Selling Unit Retail") + Key.TAB);
//	sk.screen.find(ImgPatter.get(""))
		u.hardWait(2);
		sk.screen.type(Key.TAB); // Eaches
		u.hardWait(1);
		sk.screen.type(Key.TAB); // Standard UOM
		u.hardWait(1);
//	sk.screen.find(ImgPatter.get("itm_internalCode")).right(10).click();
//	u.hardWait(1);
//	sk.screen.type(data.get("internalCode")); //Standard UOM
		u.hardWait(2);

		screenshot2();
		sk.screen.wait(ImgPattern.get("btn_grocAttributes"), 50).highlight(1, "red").click();

	}

	/**
	 * @author 1000068915
	 * @comments Llena la pantalla de ITEM MASTER
	 */
	public void FillItemMaster() throws FindFailed {
		// TODO Auto-generated method stub
		sk.screen.wait(ImgPattern.get("img_itemMaster"), 30);
		sk.screen.type(data.get("package RMS.Size") + Key.TAB);
		u.hardWait(2);
		sk.screen.type(data.get("package RMS.UOM") + Key.TAB);
		screenshot2();
		u.hardWait(1);
		sk.screen.wait(ImgPattern.get("btn_ok"), 50).click();

	}

	/**
	 * @author 1000068915
	 * @comments Da clic en el boton supplier
	 */
	public void ClicBtnSupplier() throws FindFailed {
		// TODO Auto-generated method stub

		sk.screen.wait(ImgPattern.get("btn_supplier1"), 30).click();
		screenshot2();

	}

	/**
	 * @author 1000068915
	 * @comments Da clicl el boton Add y selecciona un proveedor de la lista
	 */
	public void AddSupplier() throws FindFailed {

		r.delay(3000);
		sk.screen.wait(ImgPattern.get("btn_add"), 15).click();
		u.hardWait(1);
		sk.screen.wait(ImgPattern.get("itm_menu_supplier"), 15).click();
		r.delay(5000);
		sk.screen.wait(ImgPattern.get("itm_opcion_supplier"), 15).doubleClick();
		r.delay(3000);
		sk.screen.type(Key.TAB);
		u.hardWait(1);
		sk.screen.type(Key.TAB);
		u.hardWait(1);
		sk.screen.type(Key.TAB);
		u.hardWait(1);
		sk.screen.type(data.get("flete"));
		u.hardWait(1);
		sk.screen.wait(ImgPattern.get("btn_apply_suplier"), 15).click();
		r.delay(3000);
		screenshot2();
		u.hardWait(1);

	}

	/**
	 * @author 1000068915
	 * @comments Da clic en la opcion Item-Supplier-Origin-Country
	 */
	public void Clicbtn_ISOC() throws FindFailed {
		// TODO Auto-generated method stub btn_ISOC
		sk.screen.wait(ImgPattern.get("btn_ISOC"), 30).highlight(1, "red").click();
		screenshot2();
	}

	/**
	 * @author 1000068915
	 * @comments Llena la pantalla de Item-Supplier-Origin-Country
	 */
	public void SelectOriginCountry() throws FindFailed {

		sk.screen.type(data.get("originCountry") + Key.TAB);
		sk.screen.wait(ImgPattern.get("btn_create_sup"), 30).highlight(1, "red").click();
		r.delay(3000);
		sk.screen.type(data.get("CaseCost") + Key.TAB + data.get("UnitCost") + Key.TAB);
		r.delay(3000);
		sk.screen.find(ImgPattern.get("lbl_innerSize")).below(20).click();
		u.hardWait(1);
		sk.screen.type(data.get("InnerSize") + Key.TAB + data.get("CaseSize") + Key.TAB);
		u.hardWait(2);
		sk.screen.type(data.get("Tier") + Key.TAB + data.get("Height") + Key.TAB);
		u.hardWait(2);
		screenshot2();

	}

	/**
	 * @author 1000068915
	 * @comments Llena la pantalla package RMS.Type
	 */
	public void FillPackageType() throws FindFailed {
		// TODO Auto-generated method stub btn_OK_supplier
		sk.screen.wait(ImgPattern.get("slct_PackageType"), 30).highlight(1, "red").click();
		sk.screen.wait(ImgPattern.get("opt_each"), 30).highlight(1, "red").click();
		r.delay(3000);
		sk.screen.type("1" + Key.TAB);
		u.hardWait(2);
		sk.screen.type("1" + Key.TAB);
		u.hardWait(2);
		sk.screen.type("1" + Key.TAB);
		u.hardWait(2);
		sk.screen.type(Key.TAB + Key.TAB);
		u.hardWait(2);
		sk.screen.type("1" + Key.TAB + Key.TAB);
		u.hardWait(2);
		sk.screen.type("1" + Key.TAB + Key.TAB);
		u.hardWait(2);
		sk.screen.type(data.get("LiquidVolumen") + Key.TAB + data.get("LUOM"));
		screenshot2();
		sk.screen.wait(ImgPattern.get("btn_OK_supplier"), 30).highlight(1, "red").click();
		r.delay(3000);
		screenshot2();
		u.hardWait(1);
		sk.screen.wait(ImgPattern.get("btn_ok_rbz"), 30).highlight(1, "red").click();

	}

	/**
	 * @author 1000068915
	 * @comments Da clic en la opción Retail by Zone
	 */
	public void clicRetailbyZone() throws FindFailed {
		// TODO Auto-generated method stub btn_RetailbyZone
		sk.screen.wait(ImgPattern.get("btn_RetailbyZone"), 30).highlight(1, "red").click();
		screenshot2();
	}

	/**
	 * @author 1000068915
	 * @comments Da clic en la opción Location
	 */
	public void clicLocations() throws FindFailed {
		// TODO Auto-generated method stub
		sk.screen.wait(ImgPattern.get("locations"), 30).highlight(1, "red").click();
		screenshot2();

	}

	/**
	 * @author 1000068915
	 * @comments Llena la pantalla Locations dentro del porceso de creacion de ITEMS
	 */
	public void ItemLocation() throws FindFailed {
		// TODO Auto-generated method stub btn_apply_location
		sk.screen.wait(ImgPattern.get("GroupType"), 30).highlight(1, "red").click();
		sk.screen.type("Store" + Key.ENTER);
		u.hardWait(1);
		sk.screen.type(data.get("GroupValue"));
		r.delay(8000);
		sk.screen.type(Key.ENTER);
		u.hardWait(2);
		sk.screen.wait(ImgPattern.get("btn_apply_location"), 30).highlight(1, "red").click();
		r.delay(3000);

		screenshot2();
		u.hardWait(1);
		sk.screen.wait(ImgPattern.get("btn_ok_location"), 30).highlight(1, "red").click();
	}

	/**
	 * @author 1000068915
	 * @comments Da clic en la opción Submit
	 */
	public void ClicSubmit() throws FindFailed {
		sk.screen.wait(ImgPattern.get("submit"), 30).highlight(1, "red");
		screenshot2();
		u.hardWait(1);
		sk.screen.wait(ImgPattern.get("submit"), 30).click();
		u.hardWait(1);

	}

	/**
	 * @author 1000068915
	 * @comments Dar clic en la opción Approve y Presionar el botón OK
	 */
	public void ClicApprove() throws FindFailed {
		sk.screen.wait(ImgPattern.get("approve"), 30).highlight(1, "red");
		u.hardWait(1);
		screenshot2();
		sk.screen.wait(ImgPattern.get("approve"), 30).click();
		u.hardWait(1);
		screenshot2();
		sk.screen.wait(ImgPattern.get("btn_ok_approve"), 30).highlight(1, "red").click();
		u.hardWait(2);
	}

	/**
	 * =======
	 * 
	 * public void NavOPZ_edit() throws FindFailed { // TODO Auto-generated method
	 * stub sk.screen.wait(ImgPatter.get("lbl_control"), 20).highlight(1,
	 * "red").doubleClick(); r.delay(5000);
	 * sk.screen.wait(ImgPatter.get("folder_setup_1"), 20).highlight(1,
	 * "red").doubleClick(); r.delay(3000);
	 * sk.screen.wait(ImgPatter.get("folder_PZG"), 20).highlight(1,
	 * "red").doubleClick(); r.delay(3000);
	 * sk.screen.wait(ImgPatter.get("lbl_EditZG"), 20).highlight(1,
	 * "red").doubleClick(); r.delay(3000); screenshot2(); }
	 * 
	 * public void clicbtnaddNZP() throws FindFailed {
	 * sk.screen.wait(ImgPatter.get("btn_add"), 15000).highlight(1, "red").click();
	 * u.hardWait(2); sk.screen.wait(ImgPatter.get("currentZone"),
	 * 20).above(30).click(); u.hardWait(2);
	 * 
	 * sk.screen.keyDown(Key.CTRL); sk.screen.keyDown("c"); sk.screen.keyUp("c");
	 * sk.screen.keyUp(Key.CTRL); cb = App.getClipboard();
	 * 
	 * int cbi = Integer.parseInt(cb); cbi = cbi + 1; cb = cbi + "";
	 * 
	 * System.out.println(cb); u.hardWait(2);
	 * sk.screen.wait(ImgPatter.get("btn_add"), 15000).highlight(1, "red").click();
	 * u.hardWait(2); sk.screen.type(cb + Key.TAB + data.get("pricingLevel") +
	 * data.get("Desc") + Key.TAB + data.get("LikeZG")); u.hardWait(2);
	 * sk.screen.wait(ImgPatter.get("btn_zones"), 50).highlight(1, "red").click();
	 * u.hardWait(2); screenshot2();
	 * 
	 * }
	 * 
	 * public void clicbtnOkUL() throws FindFailed { // TODO Auto-generated method
	 * stub btn_ok_store_underline sk.screen.wait(ImgPatter.get("btn_OK_PZG"),
	 * 20).highlight(1, "red").click(); screenshot2(); }
	 * 
	 * public void fil_ItemVirtual() throws FindFailed {
	 * 
	 * sk.screen.wait(ImgPatter.get("ItemType"), 20);
	 * sk.screen.type(data.get("item_type") + Key.TAB + "Level 1"); u.hardWait(2);
	 * sk.screen.type(Key.TAB); // Eaches u.hardWait(1); // sk.screen.type(Key.TAB);
	 * //Eache // u.hardWait(1); sk.screen.type(data.get("Item") + Key.TAB);
	 * u.hardWait(2); sk.screen.type(data.get("item_desc") + Key.TAB);
	 * u.hardWait(2); sk.screen.type("descripcion corta" + Key.TAB); u.hardWait(2);
	 * sk.screen.type(Key.TAB); // merchandise u.hardWait(2);
	 * sk.screen.type(Key.SPACE); // forecastable u.hardWait(2);
	 * sk.screen.type(Key.TAB + Key.SPACE); // Catch Weight u.hardWait(2);
	 * sk.screen.type(Key.TAB + Key.SPACE); // Constant Dimensions u.hardWait(2);
	 * sk.screen.type(Key.TAB + data.get("retail_zone") + Key.TAB); // Retail Zone
	 * u.hardWait(2); sk.screen.type(data.get("cost_zone") + Key.TAB + Key.TAB); //
	 * Cost Zone u.hardWait(2); sk.screen.type(data.get("Suggested Retail") +
	 * Key.TAB + data.get("Selling Unit Retail") + Key.TAB); //
	 * sk.screen.find(ImgPatter.get("")) u.hardWait(2); sk.screen.type(Key.TAB); //
	 * Eaches u.hardWait(1); sk.screen.type(Key.TAB); // Standard UOM u.hardWait(1);
	 * sk.screen.find(ImgPatter.get("itm_internalCode")).right(10).click();
	 * u.hardWait(1); sk.screen.type(data.get("internalCode")); // Standard UOM
	 * u.hardWait(2);
	 * 
	 * screenshot2(); sk.screen.wait(ImgPatter.get("btn_grocAttributes"),
	 * 50).highlight(1, "red").click();
	 * 
	 * }
	 * 
	 * public void fil_ItemReceta() throws FindFailed {
	 * 
	 * sk.screen.wait(ImgPatter.get("ItemType"), 20);
	 * sk.screen.type(data.get("item_type") + Key.TAB + "Level 1"); u.hardWait(2);
	 * sk.screen.type(Key.TAB); // Eaches u.hardWait(1); // sk.screen.type(Key.TAB);
	 * //Eache // u.hardWait(1);
	 * 
	 * sk.screen.keyDown(Key.CTRL); sk.screen.keyDown("c"); sk.screen.keyUp("c");
	 * sk.screen.keyUp(Key.CTRL); cb = App.getClipboard();
	 * 
	 * int cbi = Integer.parseInt(cb); cbi = cbi + 1; cb = cbi + "";
	 * 
	 * System.out.println(cb);
	 * 
	 * sk.screen.type(data.get("Item") + Key.TAB); u.hardWait(2);
	 * sk.screen.type(data.get("item_desc") + Key.TAB); u.hardWait(2);
	 * sk.screen.type("descripcion corta" + Key.TAB); u.hardWait(2);
	 * sk.screen.type(Key.TAB); // merchandise u.hardWait(2);
	 * sk.screen.type(Key.SPACE); // forecastable u.hardWait(2);
	 * sk.screen.type(Key.TAB + Key.SPACE); // Catch Weight u.hardWait(2);
	 * sk.screen.type(Key.TAB + Key.SPACE); // Constant Dimensions u.hardWait(2);
	 * sk.screen.type(Key.TAB + data.get("retail_zone") + Key.TAB); // Retail Zone
	 * u.hardWait(2); sk.screen.type(data.get("cost_zone") + Key.TAB + Key.TAB); //
	 * Cost Zone u.hardWait(2); sk.screen.type(data.get("Suggested Retail") +
	 * Key.TAB + data.get("Selling Unit Retail") + Key.TAB); //
	 * sk.screen.find(ImgPatter.get("")) u.hardWait(2); sk.screen.type(Key.TAB); //
	 * Eaches u.hardWait(1); sk.screen.type(Key.TAB); // Standard UOM u.hardWait(1);
	 * sk.screen.find(ImgPatter.get("itm_internalCode")).right(10).click();
	 * u.hardWait(1); sk.screen.type(data.get("internalCode")); // Standard UOM
	 * u.hardWait(2);
	 * 
	 * screenshot2(); sk.screen.wait(ImgPatter.get("btn_grocAttributes"),
	 * 50).highlight(1, "red").click();
	 * 
	 * }
	 * 
	 * public void FillItemMaster() throws FindFailed { // TODO Auto-generated
	 * method stub sk.screen.wait(ImgPatter.get("img_itemMaster"), 30);
	 * sk.screen.type(data.get("package RMS.Size") + Key.TAB); u.hardWait(2);
	 * sk.screen.type(data.get("package RMS.UOM") + Key.TAB);
	 * sk.screen.wait(ImgPatter.get("btn_ok"), 50).click(); screenshot2();
	 * 
	 * }
	 * 
	 * public void ClicBtnSupplier() throws FindFailed { // TODO Auto-generated
	 * method stub
	 * 
	 * sk.screen.wait(ImgPatter.get("btn_supplier1"), 30).click(); screenshot2();
	 * 
	 * }
	 * 
	 * public void AddSupplier() throws FindFailed {
	 * 
	 * r.delay(3000); sk.screen.wait(ImgPatter.get("btn_add"), 15).click();
	 * u.hardWait(1); sk.screen.wait(ImgPatter.get("itm_menu_supplier"),
	 * 15).click(); r.delay(5000);
	 * sk.screen.wait(ImgPatter.get("itm_opcion_supplier"), 15).doubleClick();
	 * r.delay(3000); sk.screen.type(Key.TAB); u.hardWait(1);
	 * sk.screen.type(Key.TAB); u.hardWait(1); sk.screen.type(Key.TAB);
	 * u.hardWait(1); sk.screen.type(data.get("flete")); u.hardWait(1);
	 * sk.screen.wait(ImgPatter.get("btn_apply_suplier"), 15).click();
	 * r.delay(3000); screenshot2();
	 * 
	 * }
	 * 
	 * public void Clicbtn_ISOC() throws FindFailed { // TODO Auto-generated method
	 * stub btn_ISOC sk.screen.wait(ImgPatter.get("btn_ISOC"), 30).highlight(1,
	 * "red").click(); screenshot2(); }
	 * 
	 * public void SelectOriginCountry() throws FindFailed { // TODO Auto-generated
	 * method stub
	 * 
	 * sk.screen.type("PE" + Key.TAB);
	 * sk.screen.wait(ImgPatter.get("btn_create_sup"), 30).highlight(1,
	 * "red").click(); sk.screen.type(data.get("CaseCost") + Key.TAB +
	 * data.get("UnitCost") + Key.TAB); r.delay(3000);
	 * sk.screen.find(ImgPatter.get("lbl_innerSize")).below(20).click();
	 * sk.screen.type(data.get("InnerSize") + Key.TAB + data.get("CaseSize") +
	 * Key.TAB); u.hardWait(2); sk.screen.type(data.get("Tier") + Key.TAB +
	 * data.get("Height") + Key.TAB); u.hardWait(2); screenshot2();
	 * 
	 * }
	 * 
	 * public void FillPackageType() throws FindFailed { // TODO Auto-generated
	 * method stub btn_OK_supplier sk.screen.wait(ImgPatter.get("slct_PackageType"),
	 * 30).highlight(1, "red").click(); sk.screen.wait(ImgPatter.get("opt_each"),
	 * 30).highlight(1, "red").click(); r.delay(3000); sk.screen.type("1" +
	 * Key.TAB); u.hardWait(2); sk.screen.type("1" + Key.TAB); u.hardWait(2);
	 * sk.screen.type("1" + Key.TAB); u.hardWait(2); sk.screen.type(Key.TAB +
	 * Key.TAB); u.hardWait(2); sk.screen.type("1" + Key.TAB + Key.TAB);
	 * u.hardWait(2); sk.screen.type("1" + Key.TAB + Key.TAB); u.hardWait(2);
	 * sk.screen.type(data.get("LiquidVolumen") + Key.TAB + data.get("LUOM"));
	 * screenshot2(); sk.screen.wait(ImgPatter.get("btn_OK_supplier"),
	 * 30).highlight(1, "red").click(); r.delay(3000); screenshot2();
	 * sk.screen.wait(ImgPatter.get("btn_ok_rbz"), 30).highlight(1, "red").click();
	 * 
	 * }
	 * 
	 * public void clicRetailbyZone() throws FindFailed { // TODO Auto-generated
	 * method stub btn_RetailbyZone
	 * sk.screen.wait(ImgPatter.get("btn_RetailbyZone"), 30).highlight(1,
	 * "red").click(); screenshot2(); }
	 * 
	 * public void clicLocations() throws FindFailed { // TODO Auto-generated method
	 * stub sk.screen.wait(ImgPatter.get("locations"), 30).highlight(1,
	 * "red").click(); screenshot2();
	 * 
	 * }
	 * 
	 * public void ItemLocation() throws FindFailed { // TODO Auto-generated method
	 * stub btn_apply_location sk.screen.wait(ImgPatter.get("GroupType"),
	 * 30).highlight(1, "red").click(); sk.screen.type("Store" + Key.ENTER);
	 * u.hardWait(1); sk.screen.type(data.get("GroupValue") + Key.TAB);
	 * u.hardWait(2); sk.screen.wait(ImgPatter.get("btn_apply_location"),
	 * 30).highlight(1, "red").click(); r.delay(3000); screenshot2();
	 * sk.screen.wait(ImgPatter.get("btn_ok_location"), 30).highlight(1,
	 * "red").click(); }
	 * 
	 * public void ClicSubmit() throws FindFailed { // TODO Auto-generated method
	 * stub sk.screen.wait(ImgPatter.get("submit"), 30).highlight(1, "red").click();
	 * screenshot2();
	 * 
	 * }
	 * 
	 * public void ClicApprove() throws FindFailed { // TODO Auto-generated method
	 * stub sk.screen.wait(ImgPatter.get("approve"), 30).highlight(1,
	 * "red").click(); screenshot2();
	 * sk.screen.wait(ImgPatter.get("btn_ok_approve"), 30).highlight(1,
	 * "red").click(); screenshot2(); }
	 * 
	 * /** >>>>>>> cf3f884f6677018034634a12bdb3f1ef15714d25
	 * ATC_FT_017_CambiodePrecioNormalItemListyvalidarsuenvioalPOS
	 * 
	 * @throws FindFailed
	 */
	public void NavPricing() throws FindFailed {
		// TODO Auto-generated method stub

		sk.screen.wait(ImgPattern.get("pricing"), 30).highlight(1, "red").click();
		sk.screen.wait(ImgPattern.get("retailCost"), 30).highlight(1, "red").doubleClick();
		screenshot2();

	}

	/**
	 * @author 1000068915
	 * @Comments Navegar al menu Promotions dentro de pricing y seleccionar la
	 *           opcion EDIT
	 */
	public void NavPricingEventsEDIT() throws FindFailed {

		sk.screen.wait(ImgPattern.get("pricing"), 30).highlight(1, "red").click();
		u.hardWait(2);
		sk.screen.wait(ImgPattern.get("pricing_promotionEvents"), 15).doubleClick();
		u.hardWait(2);
		sk.screen.wait(ImgPattern.get("pricing_edit"), 15).doubleClick();
		u.hardWait(2);
		screenshot2();

	}

	/**
	 * @author 1000068915
	 * @Comments Llena la pantalla Event
	 */
	public void fillEvent() throws FindFailed {
		// TODO Auto-generated method stub

		sk.screen.wait(ImgPattern.get("btn_add"), 30).highlight(1, "red").click();
		r.delay(5000);
		sk.screen.type(data.get("event") + Key.TAB);
		u.hardWait(2);
		sk.screen.type(data.get("eventDescription") + Key.TAB);
		u.hardWait(2);
		sk.screen.type(data.get("theme") + Key.TAB);
		u.hardWait(2);
		sk.screen.type(data.get("startDate") + Key.TAB);
		u.hardWait(2);
		sk.screen.type(data.get("endDate") + Key.TAB);
		u.hardWait(2);
		sk.screen.type(data.get("startTime") + Key.TAB);
		u.hardWait(2);
		sk.screen.type(data.get("endTime") + Key.TAB);
		u.hardWait(2);
		screenshot2();
		u.hardWait(2);
		sk.screen.type(Key.ENTER);

	}

	/**
	 * @author 1000068915
	 * @comment Navega hacia la opcion promotions dentro del menu Pricing
	 */
	public void NavPricingPromotions() throws FindFailed {
		// TODO Auto-generated method stub

		sk.screen.wait(ImgPattern.get("pricing_p"), 30).highlight(1, "red").doubleClick();
		sk.screen.wait(ImgPattern.get("promotions"), 30).highlight(1, "red").doubleClick();
		screenshot2();

		r.delay(3000);

	}

	/**
	 * @author 1000068915
	 * @comments Este metodo llena el formulario para crear una nueva promocion,
	 *           acopia el ID de la promocion para proximas consultas Va a la
	 *           pantalla de Store y llena esta pantalla con la informacion de data
	 *           provider
	 */

	public void fillPromotions() throws FindFailed {
		// TODO Auto-generated method stub
		sk.screen.wait(ImgPattern.get("pricing_action"), 20).click();
		u.hardWait(1);
		sk.screen.wait(ImgPattern.get("pricing_new"), 20).click();
		u.hardWait(1);
		sk.screen.type(Key.TAB);
		u.hardWait(2);
		screenshot2();
		sk.screen.type(Key.ENTER);
		u.hardWait(2);

		sk.screen.wait(ImgPattern.get("pricing_lblPromotion"), 20).right(10).click();
		u.hardWait(1);
		sk.screen.keyDown(Key.CTRL);
		sk.screen.keyDown("c");
		sk.screen.keyUp("c");
		sk.screen.keyUp(Key.CTRL);
		r.delay(3000);
		cb = App.getClipboard();

		System.out.println(cb);

		sk.screen.type(Key.TAB);
		u.hardWait(1);
		sk.screen.type(data.get("promotion") + Key.TAB);
		u.hardWait(2);
		sk.screen.type(data.get("promotionDesc") + Key.TAB);
		u.hardWait(2);
		sk.screen.type(data.get("event") + Key.TAB);
		u.hardWait(2);

		LocalDate diaActual = LocalDate.now();
		diaActual = diaActual.plusDays(1);
		DateTimeFormatter formatN = DateTimeFormatter.ofPattern("ddMMyy");
		String diaActualFormat = diaActual.format(formatN);

		sk.screen.wait(ImgPattern.get("pricing_startDate"), 20).right(10).click();
		sk.screen.type(diaActualFormat);
		u.hardWait(1);

		sk.screen.wait(ImgPattern.get("pricing_store"), 30).highlight(1, "red").click();
		r.delay(5000);
		sk.screen.type(Key.TAB);
		u.hardWait(1);
		sk.screen.type(data.get("valueStore") + Key.TAB);
		u.hardWait(2);
		sk.screen.wait(ImgPattern.get("btn_apply"), 30).highlight(1, "red").click();
		r.delay(5000);
		sk.screen.wait(ImgPattern.get("btn_ok"), 30).highlight(1, "red").click();
		r.delay(5000);

	}

	/**
	 * @author 1000068915
	 * @comments Este metodo llena el formulario para crear una nueva promocion, asi
	 *           mismo copia el ID de la promocion para proximas consultas
	 */
	public void fillNewPromotions() throws FindFailed {

		r.delay(5000);
		sk.screen.wait(ImgPattern.get("pricing_action"), 20).click();
		u.hardWait(1);
		sk.screen.wait(ImgPattern.get("pricing_new"), 20).click();
		u.hardWait(1);
		sk.screen.type(Key.TAB);
		u.hardWait(2);
		screenshot2();
		sk.screen.type(Key.ENTER);
		u.hardWait(2);

		sk.screen.wait(ImgPattern.get("pricing_lblPromotion"), 20).right(10).click();
		u.hardWait(1);
		sk.screen.keyDown(Key.CTRL);
		sk.screen.keyDown("c");
		sk.screen.keyUp("c");
		sk.screen.keyUp(Key.CTRL);
		r.delay(3000);
		cb = App.getClipboard();

		System.out.println(cb);

		sk.screen.type(Key.TAB);
		u.hardWait(1);
		sk.screen.type(data.get("promotion") + Key.TAB);
		u.hardWait(2);
		sk.screen.type(data.get("promotionDesc") + Key.TAB);
		u.hardWait(2);
		sk.screen.type(data.get("event") + Key.TAB);

		screenshot2();
		u.hardWait(2);

	}

	/**
	 * @author 1000068915
	 * @comments Este metodo llena el formulario para crear una nueva promocion,
	 *           copia el ID de la promocion para proximas consultas registra la
	 *           informacion de metodos de pago
	 */
	public void fillNewPromotionsTDC_D() throws FindFailed {

		r.delay(5000);
		sk.screen.wait(ImgPattern.get("pricing_action"), 20).click();
		u.hardWait(1);
		sk.screen.wait(ImgPattern.get("pricing_new"), 20).click();
		u.hardWait(1);
		sk.screen.type(Key.TAB);
		u.hardWait(2);
		screenshot2();
		sk.screen.type(Key.ENTER);
		u.hardWait(2);

		sk.screen.wait(ImgPattern.get("pricing_lblPromotion"), 20).right(10).click();
		u.hardWait(1);
		sk.screen.keyDown(Key.CTRL);
		sk.screen.keyDown("c");
		sk.screen.keyUp("c");
		sk.screen.keyUp(Key.CTRL);
		r.delay(3000);
		cb = App.getClipboard();

		System.out.println(cb);

		sk.screen.type(Key.TAB);
		u.hardWait(1);
		sk.screen.type(data.get("promotion") + Key.TAB);
		u.hardWait(2);
		sk.screen.type(data.get("promotionDesc") + Key.TAB);
		u.hardWait(2);
		sk.screen.type(data.get("event"));

		sk.screen.wait(ImgPattern.get("pricing_promotionType"), 20).right(10).click();
		u.hardWait(1);
		sk.screen.type(data.get("promotionType"));
		u.hardWait(2);

		sk.screen.wait(ImgPattern.get("pricing_msgPOS"), 20).right(10).click();
		u.hardWait(1);
		sk.screen.type(data.get("msgPOS"));
		u.hardWait(2);

		sk.screen.wait(ImgPattern.get("pricing_TDC_D"), 20).left(10).click();
		u.hardWait(2);

		sk.screen.type(data.get("bandera") + Key.TAB);
		u.hardWait(2);

		sk.screen.type(data.get("banco") + Key.TAB);
		u.hardWait(2);

		sk.screen.type(data.get("tipo"));
		u.hardWait(2);

		sk.screen.wait(ImgPattern.get("pricing_btnOk_TDC"), 20).click();
		u.hardWait(2);

		screenshot2();
		u.hardWait(2);

	}

	/**
	 * @author 1000068915
	 * @comments Llena la ventana de Store dentro del proceso de crear promociones
	 */
	public void fillNewPromotionStore() throws FindFailed {

		sk.screen.wait(ImgPattern.get("pricing_store"), 30).highlight(1, "red").click();
		r.delay(5000);

		sk.screen.type(data.get("groupType") + Key.TAB);
		u.hardWait(2);

		sk.screen.type(data.get("valueStore") + Key.TAB);
		u.hardWait(2);

		sk.screen.wait(ImgPattern.get("btn_apply"), 30).highlight(1, "red").click();
		r.delay(3000);

		screenshot2();
		u.hardWait(2);

		sk.screen.wait(ImgPattern.get("btn_ok"), 30).highlight(1, "red").click();
		r.delay(5000);

	}

	/**
	 * @author 1000068915
	 * @comments Llena el formulario de promociones seleccionando EDIT como actions
	 */
	public void fillPromotionsEdit() throws FindFailed {
		// TODO Auto-generated method stub
		sk.screen.wait(ImgPattern.get("pricing_action"), 20).click();
		u.hardWait(1);
		sk.screen.wait(ImgPattern.get("pricing_edit"), 20).click();
		u.hardWait(1);
		sk.screen.type(Key.TAB);
		u.hardWait(1);

		sk.screen.type(cb);
		u.hardWait(1);

		sk.screen.type(Key.TAB);
		u.hardWait(1);

		screenshot2();
		r.delay(3000);

		sk.screen.type(Key.ENTER);
		u.hardWait(1);
	}

	/**
	 * @author 1000068915
	 * @comments Llena el formulario de promociones
	 */
	public void fillComplexPromotions() throws FindFailed {
		// TODO Auto-generated method stub
		sk.screen.wait(ImgPattern.get("pricing_action"), 20).click();
		u.hardWait(1);
		sk.screen.wait(ImgPattern.get("pricing_new"), 20).click();
		u.hardWait(1);
		sk.screen.type(Key.TAB);
		u.hardWait(2);
		screenshot2();
		sk.screen.type(Key.ENTER);
		u.hardWait(2);

		sk.screen.wait(ImgPattern.get("pricing_lblPromotion"), 20).right(10).click();
		u.hardWait(1);
		sk.screen.keyDown(Key.CTRL);
		sk.screen.keyDown("c");
		sk.screen.keyUp("c");
		sk.screen.keyUp(Key.CTRL);
		r.delay(3000);
		cb = App.getClipboard();

		System.out.println(cb);

		sk.screen.type(Key.TAB);
		u.hardWait(1);
		sk.screen.type(data.get("promotion") + Key.TAB);
		u.hardWait(2);
		sk.screen.type(data.get("promotionDesc") + Key.TAB);
		u.hardWait(2);
		sk.screen.type(data.get("event") + Key.TAB);
		u.hardWait(2);
		sk.screen.wait(ImgPattern.get("pricing_store"), 30).highlight(1, "red").click();
		r.delay(5000);
		sk.screen.type(Key.TAB);
		u.hardWait(1);
		sk.screen.type(data.get("valueStore") + Key.TAB);
		u.hardWait(2);
		sk.screen.wait(ImgPattern.get("btn_apply"), 30).highlight(1, "red").click();
		r.delay(5000);
		sk.screen.wait(ImgPattern.get("btn_ok"), 30).highlight(1, "red").click();
		r.delay(5000);

	}

	/**
	 * @author 1000068915
	 * @comments Obtiene el ID de una promocion incompleta
	 */
	public void getPromoIncompleta() throws FindFailed {
		sk.screen.wait(ImgPattern.get("pricing_action"), 20).click();
		u.hardWait(1);
		sk.screen.wait(ImgPattern.get("pricing_new"), 20).click();
		u.hardWait(1);
		sk.screen.type(Key.TAB);
		u.hardWait(2);
		screenshot2();
		sk.screen.type(Key.ENTER);
		u.hardWait(2);

		sk.screen.wait(ImgPattern.get("pricing_lblPromotion"), 20).right(10).click();
		u.hardWait(1);
		sk.screen.keyDown(Key.CTRL);
		sk.screen.keyDown("c");
		sk.screen.keyUp("c");
		sk.screen.keyUp(Key.CTRL);
		r.delay(3000);
		cb = App.getClipboard();

		System.out.println(cb);

	}

	/**
	 * @author 1000068915
	 * @comments Navega en la barra de navegacion al menu opcion y selecciona Single
	 *           item dentro del menu Add Items
	 */

	public void optionItems() throws FindFailed {

		u.hardWait(2);
		sk.screen.wait(ImgPattern.get("mn_options"), 15).click();
		u.hardWait(2);
		sk.screen.wait(ImgPattern.get("pricing_addItems"), 15).click();
		u.hardWait(2);
		sk.screen.wait(ImgPattern.get("pricing_singleItem"), 15).click();
		u.hardWait(2);
		screenshot2();

		r.delay(3000);

	}

	public void optionItemsMulti() throws FindFailed {

		u.hardWait(2);
		sk.screen.wait(ImgPattern.get("mn_options"), 15).click();
		u.hardWait(2);
		sk.screen.wait(ImgPattern.get("pricing_addItems"), 15).click();
		u.hardWait(2);
		sk.screen.wait(ImgPattern.get("pricing_multiUtils"), 15).click();
		u.hardWait(2);
		screenshot2();

	}

	/**
	 * @author 1000068915
	 * @comments Va a la barra de tareas y selecciona la opcion options, luego add
	 *           items
	 */
	public void optionAddItems() throws FindFailed {

		u.hardWait(2);
		sk.screen.wait(ImgPattern.get("mn_options"), 15).click();
		u.hardWait(2);
		sk.screen.wait(ImgPattern.get("pricing_addItems"), 15).click();
		u.hardWait(2);

		sk.screen.wait(ImgPattern.get(data.get("optionAddItems")), 15).click();
		u.hardWait(2);
		screenshot2();

	}

	/**
	 * @author 1000068915
	 * @comment Llena el formulario de la pantalla Single Item
	 */

	public void fillSingleItem() throws FindFailed {
		sk.screen.wait(ImgPattern.get("fillItem"), 30).below(10).click();
		u.hardWait(2);

		sk.screen.type(data.get("item") + Key.TAB);
		u.hardWait(2);

		sk.screen.type(data.get("type") + Key.TAB);
		u.hardWait(2);

		sk.screen.type(data.get("changeAmount") + Key.TAB);
		u.hardWait(2);

		sk.screen.type(data.get("adjustType") + Key.TAB);
		u.hardWait(2);

		sk.screen.wait(ImgPattern.get("btn_ok"), 30).highlight(1, "red").click();
		r.delay(5000);
	}

	/**
	 * @author 1000068915
	 * @comments Llena la pantalla de Multi units
	 */

	public void fillMultiUnits() throws FindFailed {

		sk.screen.type(data.get("threshold") + Key.TAB);
		u.hardWait(2);
		sk.screen.type(data.get("thresholdName") + Key.TAB);
		u.hardWait(2);

		String discountType = data.get("discountType");
		System.out.println(discountType);

		if (discountType.equals("PercentOff")) {
			sk.screen.wait(ImgPattern.get("AmountOff"), 20).click();
			u.hardWait(2);

			sk.screen.wait(ImgPattern.get(discountType), 20).click();
			u.hardWait(2);
		} else {
			sk.screen.wait(ImgPattern.get(discountType), 20).click();
			u.hardWait(2);
		}

		sk.screen.wait(ImgPattern.get("thresholdUnit"), 30).below(10).click();
		u.hardWait(2);
		sk.screen.type(data.get("thresholdUnit") + Key.TAB);
		u.hardWait(2);
		sk.screen.type(data.get("discountAmount") + Key.TAB);
		u.hardWait(2);
		sk.screen.wait(ImgPattern.get("multiUnits_items"), 30).highlight(1, "red").click();
		r.delay(5000);
	}

	/**
	 * @author 1000068915
	 * @comments Llena la pantalla Item dentro del apartado Multi Units
	 * @throws FindFailed
	 */

	public void fillMultiUnitsItem() throws FindFailed {

		sk.screen.type(data.get("itemType") + Key.TAB);
		u.hardWait(2);

		sk.screen.type(data.get("item"));
		u.hardWait(2);

		sk.screen.wait(ImgPattern.get("btn_apply"), 30).click();
		r.delay(3000);

		screenshot2();
		u.hardWait(2);

		sk.screen.wait(ImgPattern.get("btn_ok"), 30).highlight(1, "red").click();
		r.delay(5000);
	}

	/**
	 * @author 1000068915
	 * @comments Cierra la pantalla de Store
	 */
	public void setFCierre() throws FindFailed {
		r.delay(5000);
		sk.screen.find(ImgPattern.get("lbl_SCD")).right(60).click();
		sk.screen.type(data.get("StoreCloseDate") + Key.TAB);
		screenshot2();

	}

	/**
	 * @author 1000068915
	 * @comments Cierra la pantalla de stop Orders
	 */
	public void setNCierre() throws FindFailed {
		u.hardWait(2);
		sk.screen.type(data.get("StopOrderDays") + Key.TAB);
		screenshot2();

	}

	/**
	 * @author 1000068915
	 * @comments Actualiza la informacion de la transferencia y da la opcion OK
	 */
	public void UpdateTransferZone() throws FindFailed {
		u.hardWait(2);
		sk.screen.find(ImgPattern.get("lbl_TZ")).right(60).click();
		sk.screen.type(data.get("TransferZone") + Key.TAB);
		screenshot2();
		sk.screen.wait(ImgPattern.get("btn_OK_jer"), 15).highlight(1, "red").click();
		r.delay(3000);
	}

	/**
	 * @author 1000068915
	 * @comments Selecciona la opcion de Edit en los casos de Alta de item
	 */
	public void EditItemVirtual(String item) throws FindFailed {
		try {
			waitImage(25, "action");

			sk.screen.wait(ImgPattern.get("action"), 20).click();
			u.hardWait(1);

			waitImage(25, "EditItem");
			sk.screen.wait(ImgPattern.get("EditItem"), 20).click();
			u.hardWait(2);

			waitImage(25, "artITems");
			sk.screen.wait(ImgPattern.get("artITems"), 20).highlight("red").right(30).click();
			u.hardWait(1);
			sk.screen.type(item);
			u.hardWait(1);
			sk.screen.type(Key.TAB);
			u.hardWait(3);
			screenshot2();
			u.hardWait(3);
			sk.screen.type(Key.ENTER);

			Screen.highlightAllOff();
			waitImage(25, "imw_locations");
		} catch (Exception ex) {
			System.out.println(" -> " + ex);
			throw new AssertionError("Fallo al llenar ingresar el item en la pantalla de Item Maintenance");
		}
	}

	/**
	 * @author 1000068915
	 * @comments Navega a la opcion Item/Single Loc traits
	 */
	public void NavItemSingleLocTraits(String store) throws FindFailed {
		try {
			waitImage(25, "itm_groupType");
			sk.screen.wait(ImgPattern.get("itm_groupType"), 60).highlight("red").right(60).click();
			u.hardWait(1);
			sk.screen.type("Store");
			u.hardWait(1);
			sk.screen.type(Key.TAB);
			u.hardWait(1);

			waitImage(25, "ilGroupValue");
			sk.screen.wait(ImgPattern.get("ilGroupValue"), 60).highlight("red").right(60).click();
			u.hardWait(1);
			sk.screen.type(store);
			u.hardWait(1);
			sk.screen.type(Key.TAB);
			u.hardWait(1);

			waitImage(25, "ilBtnSearch");
			sk.screen.wait(ImgPattern.get("ilBtnSearch"), 60).highlight("red").doubleClick();
			u.hardWait(1);

			waitImage(25, "validarBusqueda");
			u.hardWait(1);

			waitImage(25, "itm_ItemSingle");
			sk.screen.wait(ImgPattern.get("itm_ItemSingle"), 60).highlight("red");
			u.hardWait(1);
			screenshot2();
			u.hardWait(1);
			sk.screen.wait(ImgPattern.get("itm_ItemSingle"), 60).doubleClick();
			u.hardWait(1);
			Screen.highlightAllOff();

			waitImage(25, "labelItemLocTraits");

		} catch (Exception ex) {
			System.out.println(" -> " + ex);
			throw new AssertionError("Fallo al llenar ingresar a la pantalla ITem Location Traits");
		}
	}

	/**
	 * @author 1000068915
	 * @comments Le da clic en el Label "Locatons" dentro de la pantalla de
	 *           articulos
	 */
	public void NavLocations() throws FindFailed {
		try {
			waitImage(25, "imw_locations");
			sk.screen.wait(ImgPattern.get("imw_locations"), 60).highlight("red");
			u.hardWait(1);
			screenshot2();
			u.hardWait(1);
			sk.screen.wait(ImgPattern.get("imw_locations"), 60).doubleClick();
			u.hardWait(1);

			Screen.highlightAllOff();
			waitImage(25, "itm_groupType");
			u.hardWait(1);
		} catch (Exception ex) {
			System.out.println(" -> " + ex);
			throw new AssertionError("Fallo al dirigirse a la pantalla Locations");
		}
	}

	/**
	 * @author 1000068915
	 * @comments Modifica la descripcion corta y larga del articulo
	 */
	public void modifyDesc(String store) throws FindFailed {
		try {

			waitImage(25, "itm_groupType");
			sk.screen.wait(ImgPattern.get("itm_groupType"), 60).highlight("red").right(60).click();
			u.hardWait(1);
			sk.screen.type("Store");
			u.hardWait(1);
			sk.screen.type(Key.TAB);
			u.hardWait(1);

			waitImage(25, "ilGroupValue");
			sk.screen.wait(ImgPattern.get("ilGroupValue"), 60).highlight("red").right(60).click();
			u.hardWait(1);
			sk.screen.type(store);
			u.hardWait(1);
			sk.screen.type(Key.TAB);
			u.hardWait(1);

			waitImage(25, "ilBtnSearch");
			sk.screen.wait(ImgPattern.get("ilBtnSearch"), 60).highlight("red").doubleClick();
			u.hardWait(1);

			waitImage(25, "validarBusqueda");
			u.hardWait(1);

			waitImage(25, "itm_desc");
			sk.screen.wait(ImgPattern.get("itm_desc"), 20).highlight("red").right(60).click();
			u.hardWait(1);

			sk.screen.type(data.get("description"));
			u.hardWait(1);

			waitImage(25, "itm_apply");
			sk.screen.wait(ImgPattern.get("itm_apply"), 20).highlight("red");
			u.hardWait(1);
			screenshot2();
			u.hardWait(1);

			sk.screen.wait(ImgPattern.get("itm_apply"), 20).click();

			waitImage(25, "ilBtnOKSelected");
			Screen.highlightAllOff();
			sk.screen.wait(ImgPattern.get("ilBtnOKSelected"), 20).click();

			try {
				waitImage(5, "msgArticulosPrecio");
				sk.screen.wait(ImgPattern.get("msgArticulosPrecio"), 1).click();
				u.hardWait(1);
				sk.screen.type(Key.ENTER);
			} catch (Exception e) {
				System.out.println("No se muestra mensaje: Los articulos de Consumo Interno deben de tener Precio 0.");
			}

			Screen.highlightAllOff();
			waitImage(25, "locationSelected");
			u.hardWait(1);

		} catch (Exception ex) {
			System.out.println(" -> " + ex);
			throw new AssertionError("Fallo al modificar la descripcion del item");
		}
	}

	/**
	 * @author 1000068915
	 * @comments Valida que no este ligado una tienda con el producto
	 */
	public boolean ValRecordsItemLocation(String store) throws FindFailed {

		try {
			waitImage(25, "itm_groupType");
			sk.screen.wait(ImgPattern.get("itm_groupType"), 60).highlight("red").right(60).click();
			u.hardWait(1);
			sk.screen.type("Store");
			u.hardWait(1);
			sk.screen.type(Key.TAB);
			u.hardWait(1);

			waitImage(25, "ilGroupValue");
			sk.screen.wait(ImgPattern.get("ilGroupValue"), 60).highlight("red").right(60).click();
			u.hardWait(1);
			sk.screen.type(store);
			u.hardWait(1);
			sk.screen.type(Key.TAB);
			u.hardWait(1);

			waitImage(25, "ilBtnSearch");
			sk.screen.wait(ImgPattern.get("ilBtnSearch"), 60).highlight("red").doubleClick();
			u.hardWait(2);

			boolean exist;

			try {
				waitImage(25, "itm_noRecord");
				sk.screen.wait(ImgPattern.get("itm_noRecord"), 5).highlight("red");
				u.hardWait(1);
				screenshot2();
				u.hardWait(1);
				sk.screen.wait(ImgPattern.get("itm_noRecord"), 5).click();
				sk.screen.type(Key.ENTER);

				exist = true;

			} catch (Exception e) {
				System.out.println("La tienda ya tiene ligado el articulo");
				exist = false;
			}

			Screen.highlightAllOff();
			waitImage(25, "btnFalseApply");
			u.hardWait(1);
			return exist;
		} catch (Exception ex) {
			System.out.println(" -> " + ex);
			throw new AssertionError("Fallo al validar si el item ya estaba ligado a la tienda");
		}

	}

	/**
	 * @author 1000068915
	 * @comments Rellena la informacion para ligar la tienda
	 */
	public void fillNuevaTienda(String store) throws FindFailed {
		try {
			waitImage(25, "itm_groupType2");
			sk.screen.wait(ImgPattern.get("itm_groupType2"), 20).highlight("red").right(60).click();
			u.hardWait(2);
			sk.screen.type("Store");
			u.hardWait(2);
			sk.screen.type(Key.TAB);
			u.hardWait(5);

			waitImage(25, "il_GroupValue");
			sk.screen.wait(ImgPattern.get("il_GroupValue"), 20).highlight("red").right(60).click();
			u.hardWait(1);
			sk.screen.type(store);
			u.hardWait(1);
			sk.screen.type(Key.TAB);
			u.hardWait(1);

			waitImage(25, "itm_apply");
			sk.screen.wait(ImgPattern.get("itm_apply"), 20).highlight("red");
			u.hardWait(1);
			screenshot2();
			u.hardWait(1);

			sk.screen.wait(ImgPattern.get("itm_apply"), 20).click();

			waitImage(25, "ilBtnOKSelected");
			Screen.highlightAllOff();
			sk.screen.wait(ImgPattern.get("ilBtnOKSelected"), 20).click();

			try {
				waitImage(5, "msgArticulosPrecio");
				sk.screen.wait(ImgPattern.get("msgArticulosPrecio"), 1).click();
				u.hardWait(1);
				sk.screen.type(Key.ENTER);
			} catch (Exception e) {
				System.out.println("No se muestra mensaje: Los articulos de Consumo Interno deben de tener Precio 0.");
			}

			waitImage(25, "locationSelected");
			u.hardWait(1);

		} catch (Exception ex) {
			System.out.println(" -> " + ex);
			throw new AssertionError("Fallo al validar si el item ya estaba ligado a la tienda");
		}
	}

	/**
	 * @author 1000068915
	 * @comments Da clic en boton con el label OK
	 */
	public void clic_btn_OKItems() throws FindFailed {
		try {
			waitImage(25, "btnOkItems2");
			sk.screen.wait(ImgPattern.get("btnOkItems2"), 1).click();
			u.hardWait(1);

		} catch (Exception ex) {
			System.out.println(" -> " + ex);
			throw new AssertionError("Fallo al dar clic en el boton OK");
		}
	}

	/**
	 * @author 1000068915
	 * @comments Modifica el Store Order Multiiple dentro de un articulo
	 */
	public void modifyStoreOrderMultiple(String store) throws FindFailed {
		try {
			waitImage(25, "itm_groupType");
			sk.screen.wait(ImgPattern.get("itm_groupType"), 60).highlight("red").right(60).click();
			u.hardWait(1);
			sk.screen.type("Store");
			u.hardWait(1);
			sk.screen.type(Key.TAB);
			u.hardWait(1);

			waitImage(25, "ilGroupValue");
			sk.screen.wait(ImgPattern.get("ilGroupValue"), 60).highlight("red").right(60).click();
			u.hardWait(1);
			sk.screen.type(store);
			u.hardWait(1);
			sk.screen.type(Key.TAB);
			u.hardWait(1);

			waitImage(25, "ilBtnSearch");
			sk.screen.wait(ImgPattern.get("ilBtnSearch"), 60).highlight("red").doubleClick();
			u.hardWait(1);

			waitImage(25, "validarBusqueda");
			u.hardWait(1);

			waitImage(25, "itm_SOM");
			sk.screen.wait(ImgPattern.get("itm_SOM"), 20).highlight("red").right(60).click();
			u.hardWait(1);
			sk.screen.type(data.get("SOM"));
			u.hardWait(1);
			sk.screen.type(Key.TAB);
			u.hardWait(1);
			sk.screen.type(Key.TAB);
			u.hardWait(1);

			waitImage(25, "itm_apply");
			sk.screen.wait(ImgPattern.get("itm_apply"), 20).highlight("red");
			u.hardWait(1);
			screenshot2();
			u.hardWait(1);

			sk.screen.wait(ImgPattern.get("itm_apply"), 20).click();

			waitImage(25, "ilBtnOKSelected");
			Screen.highlightAllOff();
			sk.screen.wait(ImgPattern.get("ilBtnOKSelected"), 20).click();

			try {
				waitImage(5, "msgArticulosPrecio");
				sk.screen.wait(ImgPattern.get("msgArticulosPrecio"), 1).click();
				u.hardWait(1);
				sk.screen.type(Key.ENTER);
			} catch (Exception e) {
				System.out.println("No se muestra mensaje: Los articulos de Consumo Interno deben de tener Precio 0.");
			}

			Screen.highlightAllOff();
			waitImage(25, "locationSelected");
			u.hardWait(1);
		} catch (Exception ex) {
			System.out.println(" -> " + ex);
			throw new AssertionError("Fallo al Modificar el campo Store Order Multiple");
		}
	}

	/**
	 * @author 1000068915
	 * @comments Navega a la opcion Item/Single Loc traits
	 */
	public void fillItemLocationTraits() throws FindFailed {
		try {

			waitImage(25, "labelItemLocTraits");

			waitImage(25, "item_scroll");
			sk.screen.wait(ImgPattern.get("item_scroll"), 20).click();
			u.hardWait(1);
			sk.screen.wait(ImgPattern.get("itm_ISMB"), 20).highlight("red").right(60).click();
			u.hardWait(3);
			sk.screen.type(data.get("ISBM"));
			u.hardWait(4);
			sk.screen.type(Key.TAB);
			u.hardWait(2);

			waitImage(25, "itm_btnOKISBM");
			sk.screen.wait(ImgPattern.get("itm_btnOKISBM"), 20).highlight("red");
			u.hardWait(1);
			screenshot2();
			u.hardWait(1);
			Screen.highlightAllOff();
			sk.screen.wait(ImgPattern.get("itm_btnOKISBM"), 20).click();

			waitImage(25, "itm_apply");
			sk.screen.wait(ImgPattern.get("itm_apply"), 20).highlight("red");
			u.hardWait(1);
			screenshot2();
			u.hardWait(1);

			sk.screen.wait(ImgPattern.get("itm_apply"), 20).click();

			waitImage(25, "ilBtnOKSelected");
			Screen.highlightAllOff();
			sk.screen.wait(ImgPattern.get("ilBtnOKSelected"), 20).click();

			try {
				waitImage(5, "msgArticulosPrecio");
				sk.screen.wait(ImgPattern.get("msgArticulosPrecio"), 1).click();
				u.hardWait(1);
				sk.screen.type(Key.ENTER);
			} catch (Exception e) {
				System.out.println("No se muestra mensaje: Los articulos de Consumo Interno deben de tener Precio 0.");
			}

			Screen.highlightAllOff();
			waitImage(25, "locationSelected");
			u.hardWait(1);

		} catch (Exception ex) {
			System.out.println(" -> " + ex);
			throw new AssertionError("Fallo al llenar ingresar a la pantalla ITem Location Traits");
		}
	}

	/**
	 * @author 1000068915
	 * @comments Le da clic en el Label "VAT Maintenance" dentro de la pantalla de
	 *           articulos
	 */
	public void NavVATMaintenance() throws FindFailed {
		try {

			waitImage(25, "itm_VATMaintenance");
			sk.screen.wait(ImgPattern.get("itm_VATMaintenance"), 20).highlight("red");
			u.hardWait(1);
			screenshot2();
			u.hardWait(1);
			sk.screen.wait(ImgPattern.get("itm_VATMaintenance"), 20).doubleClick();

			Screen.highlightAllOff();
			waitImage(25, "itm_VATRegion");
		} catch (Exception ex) {
			System.out.println(" -> " + ex);
			throw new AssertionError("Fallo al dirigirse a la pantalla VAT Item Maintenance");
		}
	}

	/**
	 * @author 1000068915
	 * @comments Rellena la informacion "VAT Maintenance" dentro de la pantalla de
	 *           articulos
	 */
	public void fillVATMaintenance(String vatRegion, String VATCode, String VATType) {
		try {

			waitImage(25, "itm_VATRegion");
			sk.screen.wait(ImgPattern.get("itm_VATRegion"), 20).highlight("red").right(60).click();
			u.hardWait(1);
			sk.screen.type(vatRegion);
			u.hardWait(1);
			sk.screen.type(Key.TAB);
			u.hardWait(1);

			waitImage(25, "itm_addVatCode");
			sk.screen.wait(ImgPattern.get("itm_addVatCode"), 20).highlight("red");

			u.hardWait(1);
			screenshot2();
			u.hardWait(1);

			sk.screen.wait(ImgPattern.get("itm_addVatCode"), 20).click();
			u.hardWait(3);

			waitImage(25, "vat_code");
			sk.screen.wait(ImgPattern.get("vat_code"), 20).highlight("red").click();
			u.hardWait(2);
			sk.screen.type(VATCode);
			u.hardWait(2);
			sk.screen.type(Key.TAB);

			waitImage(25, "vat_type");
			sk.screen.wait(ImgPattern.get("vat_type"), 20).highlight("red").click();
			u.hardWait(2);
			sk.screen.type(VATType);
			u.hardWait(2);
			sk.screen.type(Key.TAB);

			diaActualVAT = LocalDate.now();
			diaActualVAT = diaActualVAT.plusDays(6);
			DateTimeFormatter formatN = DateTimeFormatter.ofPattern("ddMMyy");
			String diaActualFormat = diaActualVAT.format(formatN);
			u.hardWait(2);
			sk.screen.type(diaActualFormat);
			u.hardWait(2);
			sk.screen.type(Key.TAB);
			u.hardWait(2);

			waitImage(25, "btnOK_VAT");

			screenshot2();
			u.hardWait(2);

			Screen.highlightAllOff();
			sk.screen.wait(ImgPattern.get("btnOK_VAT"), 20).click();
			waitImage(25, "labelItemMaintenance");

		} catch (Exception ex) {
			System.out.println(" -> " + ex);
			throw new AssertionError("Fallo al llenar la pantalla VAT Item Maintenance");
		}
	}

	public LocalDate getDate() {
		return diaActualVAT;
	}

	public String getVATType(String VATCode) {
		System.out.println("VATTYPE COMPLETE: " + VATCode);
		String VATTypeSQL = "";

		switch (VATCode) {
		case "Both":
			VATTypeSQL = "B";
			break;

		case "Cost":
			VATTypeSQL = "C";
			break;

		case "Retail":
			VATTypeSQL = "R";
			break;

		default:
			break;
		}

		System.out.println("VATTYPE: " + VATTypeSQL);
		return VATTypeSQL;
	}

	/**
	 * @author 1000068915
	 * @comments Selecciona el folder menu Inventory/XXFC JIT/XXFC suppline
	 */

	public void navXXFCSuppline() {
		try {

			waitImage(25, "inv");
			sk.screen.wait(ImgPattern.get("inv"), 15).highlight("red").doubleClick();
			u.hardWait(1);

			waitImage(25, "inv_XXFCJIT");
			sk.screen.wait(ImgPattern.get("inv_XXFCJIT"), 15).highlight("red").doubleClick();
			u.hardWait(1);

			waitImage(25, "inv_XXFCsuppline");
			sk.screen.wait(ImgPattern.get("inv_XXFCsuppline"), 15).highlight("red").doubleClick();
			u.hardWait(1);

			screenshot2();
			u.hardWait(1);

			Screen.highlightAllOff();
			waitImage(25, "inv_action");
			u.hardWait(1);
			screenshot2();
		} catch (Exception ex) {
			System.out.println(" -> " + ex);
			throw new AssertionError("Fallo al dirigirse a la pantalla CCFC - Suppline");
		}
	}

	/**
	 * @author 1000068915
	 * @comments Selecciona el folder menu Inventory/XXFC JIT/XXFC suppline
	 */

	public void fillEditXXFCSuppline(String suppline, String item, String unidades, String borrar) {
		try {

			waitImage(25, "inv_action");
			sk.screen.wait(ImgPattern.get("inv_action"), 15).highlight("red").right(60).click();
			u.hardWait(2);

			sk.screen.type("E");
			u.hardWait(1);
			sk.screen.type(Key.ENTER);
			u.hardWait(1);

			waitImage(25, "inv_supplyLine");
			sk.screen.wait(ImgPattern.get("inv_supplyLine"), 15).highlight("red").right(60).click();
			u.hardWait(1);
			sk.screen.type(suppline);
			u.hardWait(1);

			waitImage(25, "inv_search");
			sk.screen.wait(ImgPattern.get("inv_search"), 15).highlight("red").click();
			u.hardWait(1);

			try {
				waitImage(10, "msgMoreThanOneRecord");
				u.hardWait(1);
				sk.screen.wait(ImgPattern.get("msgMoreThanOneRecord"), 1).click();
				sk.screen.type(Key.ENTER);
				waitImage(10, "btnSearchDisabled");
			} catch (Exception e) {
				System.out.println("No encontro mensaje 'More than one record'");
			}

			waitImage(25, "inv_addItem");
			sk.screen.wait(ImgPattern.get("inv_addItem"), 15).highlight("red").click();
			u.hardWait(1);

			waitImage(25, "inv_txtboxAddItem");
			sk.screen.wait(ImgPattern.get("inv_txtboxAddItem"), 15).highlight("red").click();
			u.hardWait(1);
			sk.screen.type(item);
			u.hardWait(1);

			waitImage(25, "ind_unidade");
			sk.screen.wait(ImgPattern.get("ind_unidade"), 15).highlight("red").click();
			u.hardWait(1);
			sk.screen.type(unidades);
			u.hardWait(1);
			sk.screen.type(Key.ENTER);

			waitImage(25, "ind_borrar");
			sk.screen.wait(ImgPattern.get("ind_borrar"), 15).highlight("red").click();
			u.hardWait(1);
			sk.screen.type(borrar);
			u.hardWait(1);
			sk.screen.type(Key.ENTER);

			u.hardWait(1);
			screenshot2();
			u.hardWait(2);

			Screen.highlightAllOff();

			waitImage(25, "inv_btnOK");
			sk.screen.wait(ImgPattern.get("inv_btnOK"), 15).click();

			waitImage(25, "inv");
			u.hardWait(1);
		} catch (Exception ex) {
			System.out.println(" -> " + ex);
			throw new AssertionError("Fallo al llenar la pantalla Supply Line Maintenance");
		}
	}

	public void NavPricingEvents() throws FindFailed {
		// TODO Auto-generated method stub

		sk.screen.wait(ImgPattern.get("pricing"), 30).highlight(1, "red").click();
		u.hardWait(2);
		sk.screen.wait(ImgPattern.get("pricing_promotionEvents"), 15).doubleClick();
		u.hardWait(2);
		sk.screen.wait(ImgPattern.get("pricing_edit"), 15).doubleClick();
		u.hardWait(2);
		screenshot2();

	}

	public void close_RMS_MaintenanceSupplier() throws FindFailed {

		u.hardWait(2);
		sk.screen.wait(ImgPattern.get("mn_action"), 5).highlight(1, "red").click();
		sk.screen.wait(ImgPattern.get("smn_close"), 5).highlight(1, "red").click();
		u.hardWait(2);
		sk.screen.wait(ImgPattern.get("mn_action"), 5).highlight(1, "red").click();
		sk.screen.wait(ImgPattern.get("smn_close"), 5).highlight(1, "red").click();
		u.hardWait(2);
		sk.screen.wait(ImgPattern.get("mn_action"), 5).highlight(1, "red").click();
		sk.screen.wait(ImgPattern.get("smn_close"), 5).highlight(1, "red").click();

	}

	/**
	 * @author 1000068915
	 * @comment Metodo para dar approve dentro de la barra de tareas y dar clic en
	 *          la opcion YES para la pantalla informativa
	 */
	public boolean approveRetailPrice1() {
		try {
			u.hardWait(2);
			sk.screen.wait(ImgPattern.get("mn_options"), 50).click();
			sk.screen.wait(ImgPattern.get("itm_approve"), 50).click();
			u.hardWait(2);

			for (int i = 0; i < 90; i++) {
				try {
					sk.screen.wait(ImgPattern.get("rpch_btn_yes"), 2).click();
					System.out.println("Se muestra el mensaje de para dar YES");
					screenshot2();
					break;
				} catch (Exception e) {
					System.out.println("No se muestra el mensaje de para dar YES");
				}
			}

			for (int i = 0; i < 60; i++) {
				try {
					sk.screen.wait(ImgPattern.get("statusApproved"), 2);
					System.out.println("Se muestra el mensaje de status Approved");
					screenshot2();
					return true;
				} catch (Exception e) {
					System.out.println("No se muestra el mensaje de status Approved");
				}
			}

			screenshot2();
			return false;

		} catch (Exception ex) {
			System.out.println(" -> " + ex);
			throw new AssertionError("Fallo al cambiar el status a Approved");
		}

	}

	/**
	 * @author 1000068915
	 * @comments Cierra la pantalla de RMS cerrando antes las pantallas de dialogo
	 *           de la ultima accion
	 */

	public void close_RMS_RPCH2() throws FindFailed {

		sk.screen.wait(ImgPattern.get("rpch_btn_OK"), 15).highlight(1, "red").click();
		u.hardWait(2);
		sk.screen.wait(ImgPattern.get("mn_action"), 5).highlight(1, "red").click();
		sk.screen.wait(ImgPattern.get("smn_close"), 5).highlight(1, "red").click();
		u.hardWait(2);
		sk.screen.wait(ImgPattern.get("mn_action"), 5).highlight(1, "red").click();
		sk.screen.wait(ImgPattern.get("smn_close"), 5).highlight(1, "red").click();

	}

	/**
	 * @author 1000068915
	 * @comments Capturar los datos correspondientes en los campos package RMS.Size,
	 *           package RMS.UOM y Dar clic en el botón OK.
	 */
	public int fil_ItemVirtual2(String item) throws FindFailed {

		int newItem = Integer.parseInt(item);
		newItem = newItem + 1;

		if (data.get("packs") != null) {

			sk.screen.wait(ImgPattern.get(data.get("packs")), 20).click();
			u.hardWait(2);
		}

		sk.screen.wait(ImgPattern.get("ItemType"), 20).right(10).click();
		sk.screen.type(data.get("item_type") + Key.TAB);
		u.hardWait(2);
		// sk.screen.type(data.get("Item") + Key.TAB);
		sk.screen.type(newItem + Key.TAB);
		u.hardWait(2);
		System.out.println("Validando si el numero de ITEM ya existe");
//		if(sk.screen.exists(ImgPatter.get("itm_alreadyExist"), 12) != null) {
//			Match exist = sk.screen.exists(ImgPatter.get("itm_alreadyExist"));
//			newItem = Integer.parseInt(data.get("Item"));
//			while(exist != null){
//			u.hardWait(2);
//			sk.screen.type(Key.ENTER);
//			u.hardWait(2);
//			newItem++;
//			sk.screen.type(newItem + Key.TAB);
//			u.hardWait(2);
//			exist = sk.screen.exists(ImgPatter.get("itm_alreadyExist"), 3);
//			}
//		}
//		else {
//			newItem = Integer.parseInt(data.get("Item"));
//		}
		System.out.println("Validacion terminada");
		sk.screen.type(data.get("item_desc") + Key.TAB);
		u.hardWait(2);
		sk.screen.type(data.get("short_desc"));
		u.hardWait(2);

		// merchandise
		try {
			if (data.get("merchandise").equals("true")) {
				sk.screen.find(ImgPattern.get("merchandise_CHECK")).click();
				r.delay(1500);
				sk.screen.find(ImgPattern.get("merchandise")).click();
				r.delay(1500);
			}
		} catch (FindFailed e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// forecastable
		try {
			if (data.get("forecastable").equals("true")) {
				sk.screen.find(ImgPattern.get("forescastable")).click();
				r.delay(1500);
			}
		} catch (FindFailed e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// Catch Weight
		try {
			if (data.get("catch_weihgt").equals("true")) {
				sk.screen.find(ImgPattern.get("catchWeight")).click();
				r.delay(1500);
			}
		} catch (FindFailed e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// Constant Dimensions
		try {
			if (data.get("constant_dimensions").equals("true")) {
				sk.screen.find(ImgPattern.get("constantDimensions")).click();
				r.delay(1500);
			}
		} catch (FindFailed e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if (sk.screen.exists(ImgPattern.get("retailZoneD")) == null) {

			sk.screen.find(ImgPattern.get("itm_retailZone")).right(10).click();
			sk.screen.type(data.get("retail_zone")); // Retail Zone
			u.hardWait(1);
		}

		if (sk.screen.exists(ImgPattern.get("costZoneD")) == null) {

			sk.screen.find(ImgPattern.get("itm_costZone")).right(10).click();
			sk.screen.type(data.get("cost_zone")); // Cost Zone
			u.hardWait(1);
		}

		if (sk.screen.exists(ImgPattern.get("unitELCD")) == null) {

			sk.screen.find(ImgPattern.get("itm_unitELC")).right(10).click();
			sk.screen.type(data.get("unitELC")); // Unit ELC
			u.hardWait(1);
		}

		if (sk.screen.exists(ImgPattern.get("standardUnitRetailD")) == null) {

			sk.screen.find(ImgPattern.get("itm_standardUnitRetail")).right(10).click();
			sk.screen.type(data.get("standard_Unit_Retail")); // standard_Unit_Retail
			u.hardWait(1);
		}

		if (sk.screen.exists(ImgPattern.get("sellingUnitRetailD"), 5) == null) {

			sk.screen.find(ImgPattern.get("itm_sellingUnitRetail")).right(10).click();
			sk.screen.type(data.get("selling_Unit_Retail")); // selling_Unit_Retail
			u.hardWait(1);
		}

		if (sk.screen.exists(ImgPattern.get("suggestedRetailD")) == null) {

			sk.screen.find(ImgPattern.get("itm_suggestedRetail")).right(10).click();
			sk.screen.type(data.get("suggested_Retail")); // suggested_Retail
			u.hardWait(1);
		}

		// SOM
		sk.screen.find(ImgPattern.get("itm_SOM")).right(10).click();
		sk.screen.type(data.get("Store Order Multiple")); // suggested_Retail
		sk.screen.type(Key.TAB);
		u.hardWait(1);

		// Standar UOM
		if (sk.screen.exists(ImgPattern.get("standardUOMD")) == null) {

			sk.screen.find(ImgPattern.get("itm_standardUOM")).right(10).click();
			sk.screen.type(data.get("Standard UOM")); // Standard UOM
			u.hardWait(2);
		}

//		sk.screen.type(data.get("Suggested Retail") + Key.TAB + data.get("Selling Unit Retail") + Key.TAB);
////	sk.screen.find(ImgPatter.get(""))
//		u.hardWait(2);
//		sk.screen.type(Key.TAB); // Eaches
//		u.hardWait(1);
//		sk.screen.type(Key.TAB); // Standard UOM
////		u.hardWait(1);
////		sk.screen.find(ImgPatter.get("itm_internalCode")).right(10).click();
////		u.hardWait(2);
////		sk.screen.type(data.get("internalCode")); //internalcode
//		u.hardWait(2);

		screenshot2();
		sk.screen.wait(ImgPattern.get("btn_grocAttributes"), 50).highlight(1, "red").click();
		// retailZoneD
		return newItem;
	}

	/**
	 * @author 1000068915
	 * @comments Llena la pantalla de Location dentro del modulo de ITEMS
	 */

	public void fillLocation() throws FindFailed {
		try {

			waitImage(50, "dealOrgLevel");
			sk.screen.wait(ImgPattern.get("dealOrgLevel"), 10).highlight("red").right(30).click();
			u.hardWait(1);
			sk.screen.type(data.get("OrgLevel"));
			u.hardWait(1);
			sk.screen.type(Key.TAB);
			u.hardWait(1);

			waitImage(50, "dealPlaza");
			sk.screen.wait(ImgPattern.get("dealPlaza"), 10).highlight("red").right(30).click();
			u.hardWait(1);
			sk.screen.type(data.get("Plaza"));
			u.hardWait(1);

			waitImage(50, "dealOriginCountry");
			sk.screen.wait(ImgPattern.get("dealOriginCountry"), 10).highlight("red").right(30).click();
			u.hardWait(1);
			sk.screen.type(data.get("OriginCountry"));
			u.hardWait(1);

			screenshot2();
			u.hardWait(1);
			Screen.highlightAllOff();

			waitImage(50, "dealBtnOK");
			sk.screen.wait(ImgPattern.get("dealBtnOK"), 10).click();

			waitImage(50, "dealThreslholdP");

		} catch (Exception ex) {
			System.out.println(" -> " + ex);
			throw new AssertionError("Fallo al llenar el campo Location");
		}
	}

	/**
	 * @author 1000068915
	 * @comment Metodo para dar approve dentro de la barra de tareas
	 */
	public void saveDeal() throws FindFailed {

		try {

			waitImage(50, "btn_close_deals2");
			u.hardWait(1);
			sk.screen.wait(ImgPattern.get("btn_close_deals2"), 10).click();

			waitImage(50, "dealBtnYesSave");
			u.hardWait(1);
			sk.screen.wait(ImgPattern.get("dealBtnYesSave"), 10).click();

			waitImage(50, "lbl_action_LL");

//
//		if (sk.screen.exists(ImgPattern.get("retekErrorDeal"), 20) != null) {
//			System.out.println("Encontro mensaje de error");
//			sk.screen.type(Key.ENTER);
//		} else {
//			sk.screen.wait(ImgPattern.get("btn_yes_deals2"), 15).click();
//			u.hardWait(2);
//		}
		} catch (Exception ex) {
			System.out.println(" -> " + ex);
			throw new AssertionError("Fallo al dar clic en el boton Close");
		}

	}

	public void modStatus(String status) {
		try {
			waitImage(50, "statusItem");
			sk.screen.wait(ImgPattern.get("statusItem"), 10).highlight("red").right(30).click();
			u.hardWait(3);
			sk.screen.type(status);
			u.hardWait(3);
			sk.screen.type(Key.TAB);
			u.hardWait(2);
			sk.screen.type(Key.TAB);
			u.hardWait(1);
			u.hardWait(1);
			screenshot2();
			u.hardWait(1);

			waitImage(25, "itm_apply");
			sk.screen.wait(ImgPattern.get("itm_apply"), 20).highlight("red");
			u.hardWait(1);
			screenshot2();
			u.hardWait(1);

			sk.screen.wait(ImgPattern.get("itm_apply"), 20).click();

			waitImage(25, "ilBtnOKSelected");
			Screen.highlightAllOff();
			sk.screen.wait(ImgPattern.get("ilBtnOKSelected"), 20).click();

			try {
				waitImage(5, "msgArticulosPrecio");
				sk.screen.wait(ImgPattern.get("msgArticulosPrecio"), 1).click();
				u.hardWait(1);
				sk.screen.type(Key.ENTER);
			} catch (Exception e) {
				System.out.println("No se muestra mensaje: Los articulos de Consumo Interno deben de tener Precio 0.");
			}

			waitImage(25, "locationSelected");
			u.hardWait(1);
		} catch (Exception ex) {
			System.out.println(" -> " + ex);
			throw new AssertionError("Fallo al cambiar el status del Item");
		}
	}

	/**
	 * @author 1000068915
	 * @return
	 * @comments Valida que no este ligado una tienda con el producto
	 */
	public void selectLocation(String store) throws FindFailed {
		try {
			waitImage(25, "itm_groupType");
			sk.screen.wait(ImgPattern.get("itm_groupType"), 60).highlight("red").right(60).click();
			u.hardWait(1);
			sk.screen.type("Store");
			u.hardWait(1);
			sk.screen.type(Key.TAB);
			u.hardWait(1);

			waitImage(25, "ilGroupValue");
			sk.screen.wait(ImgPattern.get("ilGroupValue"), 60).highlight("red").right(60).click();
			u.hardWait(1);
			sk.screen.type(store);
			u.hardWait(1);
			sk.screen.type(Key.TAB);
			u.hardWait(1);

			waitImage(25, "ilBtnSearch");
			sk.screen.wait(ImgPattern.get("ilBtnSearch"), 60).highlight("red").doubleClick();
			u.hardWait(2);

		} catch (Exception ex) {
			System.out.println(" -> " + ex);
			throw new AssertionError("Fallo al seleccionar la location");
		}

	}

	/**
	 * @author 1000068915
	 * @comments Da clic en boton con el label OK
	 */
	public void clic_btn_OKItemsU() throws FindFailed {

		r.delay(3000);
		sk.screen.wait(ImgPattern.get("btnOkItems2U"), 15).click();
		screenshot2();
	}

	/**
	 * @author 1000068915
	 * @comments Selecciona la opcion de Edit en los casos de Alta de item donde no
	 *           se tiene un item en el data provider
	 */
	public void EditItemVirtualMod(String item) throws FindFailed {
		r.delay(3000);
		sk.screen.wait(ImgPattern.get("action"), 20).click();
		u.hardWait(1);
		sk.screen.wait(ImgPattern.get("EditItem"), 20).click();

		sk.screen.type(item);
		u.hardWait(1);
		sk.screen.type(Key.TAB);
		u.hardWait(2);
		screenshot2();
		u.hardWait(2);
		sk.screen.type(Key.ENTER);
	}

	public void navEditUDA() {
		try {
			waitImage(25, "UDA_edit");
			sk.screen.wait(ImgPattern.get("UDA_edit"), 60).highlight("red");
			u.hardWait(1);
			screenshot2();
			u.hardWait(1);
			sk.screen.wait(ImgPattern.get("UDA_edit"), 60).doubleClick();
			u.hardWait(1);

			Screen.highlightAllOff();
			waitImage(25, "btn_addText");
			u.hardWait(1);
		} catch (Exception ex) {
			System.out.println(" -> " + ex);
			throw new AssertionError("Fallo al dirigirse a la pantalla EDIT UDA");
		}
	}

	public void editUDA(String id_UDA, String text_UDA) {
		try {
			waitImage(25, "btn_addText");
			sk.screen.wait(ImgPattern.get("btn_addText"), 30).highlight("red").click();
			u.hardWait(2);
			sk.screen.type(id_UDA);
			u.hardWait(1);
			sk.screen.type(Key.TAB);
			u.hardWait(1);
			sk.screen.type(text_UDA);
			u.hardWait(1);
			sk.screen.type(Key.TAB);
			u.hardWait(1);
			waitImage(25, "btn_addTextS");
			screenshot2();
			u.hardWait(1);

			Screen.highlightAllOff();
			sk.screen.wait(ImgPattern.get("btn_OKUDA"), 30).click();

		} catch (Exception ex) {
			System.out.println(" -> " + ex);
			throw new AssertionError("Fallo al editar UDA");
		}
	}

	/**
	 * @author 1000068915
	 * @comments Da clic en el boton Pack Details de la pantalla Item MAintenance
	 */
	public void ClicPackDetails() {
		try {
			sk.screen.wait(ImgPattern.get("pack_Details"), 30).click();
			u.hardWait(1);
		} catch (FindFailed e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * @author 1000068915
	 * @comments Llena los datos de la pantalla Pack Item Detail Maintenance
	 */
	public void fillPackITemDetailMaintenance() {
		try {
			JSONArray jsonArray = new JSONArray(data.get("items"));
			JSONObject jsonObj;

			for (int i = 0; i < jsonArray.length(); i++) {
				sk.screen.wait(ImgPattern.get("add_itm_PIDM"), 20).click();
				jsonObj = (JSONObject) jsonArray.get(i);

				String item = JsonUtil.getJsonElement(jsonObj, "item");
				String cantidad = JsonUtil.getJsonElement(jsonObj, "cantidad");

				try {

					sk.screen.wait(ImgPattern.get("itm_PIDM"), 20).right(10).click();
					u.hardWait(1);

					sk.screen.type(item);
					u.hardWait(1);
					sk.screen.type(Key.TAB);
					u.hardWait(1);
					sk.screen.type(cantidad);
					u.hardWait(1);
					sk.screen.type(Key.TAB);
					u.hardWait(1);
					sk.screen.type(Key.ENTER);
					u.hardWait(2);

				} catch (Exception e) {
					System.out.println(e.getMessage());
				}
			}

			u.hardWait(2);
			screenshot2();
			u.hardWait(2);
			sk.screen.type(Key.ENTER);
			u.hardWait(2);
		} catch (FindFailed e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * @author 1000068915
	 * @comments Va la pantalla Retail By Zone
	 */
	public void clicRetailByZone() {
		try {
			sk.screen.wait(ImgPattern.get("lbl_retailByZone"), 20).doubleClick();
			sk.screen.wait(ImgPattern.get("lbl_IRPBZ"), 20);
			screenshot2();
			u.hardWait(1);
		} catch (FindFailed e) {
			e.printStackTrace();
		}
	}

	/**
	 * @author 1000068915
	 * @comments Da clic en el boton OK de la pantalla Threshold
	 */

	public void clicBtnOKRecetas() throws FindFailed {
		r.delay(3);
		try {
			sk.screen.wait(ImgPattern.get("btn_OK_Receta"), 90).click();
		} catch (FindFailed e) {
			e.printStackTrace();
		}
	}

	/**
	 * @author 1000068915
	 * @comments Va la pantalla Locations dentro de la creaciond e Items
	 */
	public void clicLocation() {
		try {
			sk.screen.wait(ImgPattern.get("lbl_locations"), 120).doubleClick();
			sk.screen.wait(ImgPattern.get("lbl_itemLocations"), 20);
			screenshot2();
			u.hardWait(1);
		} catch (FindFailed e) {
			e.printStackTrace();
		}
	}

	/**
	 * @author 1000068915
	 * @comments Llena la pantalla de Locations dentro de la creacion de un Item
	 */
	public void fillLocations() {
		try {
			sk.screen.wait(ImgPattern.get("lbl_groupType"), 20).right(10).click();
			u.hardWait(1);
			sk.screen.type(data.get("groupType"));
			u.hardWait(1);
			sk.screen.type(Key.TAB);
			u.hardWait(1);
			sk.screen.type(data.get("groupValue"));
			u.hardWait(1);
			sk.screen.type(Key.TAB);
			u.hardWait(1);

			sk.screen.wait(ImgPattern.get("btn_applyR"), 20).click();
			u.hardWait(1);
			sk.screen.wait(ImgPattern.get("btn_OKRU"), 20).click();
			screenshot2();
			u.hardWait(1);
		} catch (FindFailed e) {
			e.printStackTrace();
		}
	}

	/**
	 * @author 1000068915
	 * @comments Da clic en la opcion Submit
	 */
	public void submitReceta() {
		try {
			sk.screen.wait(ImgPattern.get("lbl_submitReceta"), 20).doubleClick();
			r.delay(4000);
			screenshot2();
			u.hardWait(2);
		} catch (FindFailed e) {
			e.printStackTrace();
		}
	}

	/**
	 * @author 1000068915
	 * @comments Da clic en la opcion Approve para creacion de un item/RECETA
	 */
	public void approveRI() {
		try {
			sk.screen.wait(ImgPattern.get("opt_Approve"), 20).doubleClick();
			r.delay(4000);
			screenshot2();
			u.hardWait(2);
		} catch (FindFailed e) {
			e.printStackTrace();
		}
	}

	/**
	 * @author 1000068915
	 * @comments Da clic en la opcion Approve para creacion de un item/RECETA
	 */
	public String validarStatus() {
		try {
			r.delay(4000);
			sk.screen.wait(ImgPattern.get("status_item"), 20).right(15).click();
			r.delay(4000);
			sk.screen.wait(ImgPattern.get("status_item"), 20).right(15).doubleClick();
			u.hardWait(1);
			sk.screen.keyDown(Key.CTRL);
			r.delay(500);
			sk.screen.keyDown("c");
			r.delay(500);
			sk.screen.keyUp("c");
			r.delay(500);
			sk.screen.keyUp(Key.CTRL);
			u.hardWait(1);
			cb = App.getClipboard();
			System.out.println("Status: " + cb);
			return cb;
		} catch (FindFailed e) {
			return "No se encontro status";
		}

	}

	public void waitImage(int time, String image) {
		for (int i = 0; i < time; i++) {
			try {
				sk.screen.wait(ImgPattern.get(image), 2);
				System.out.println("Se muestra la imagen: " + image);
				// screenshot2();
				break;
			} catch (Exception e) {
				if (i % 30 == 0) {
					System.out.println("Esperando imagen " + image + "...");
					u.hardWait(1);
				}
			}
		}

		try {
			sk.screen.wait(ImgPattern.get(image), 2);
		} catch (Exception e) {
			// screenshot2();
			System.out.println("No se muestra la imagen " + image);
		}
	}

	public void screenshot2() {
		String filename = "evidence" + numeroEvidencia;
		testCase.addScreenShotCurrentStep(filename);
		numeroEvidencia++;
	}

}
