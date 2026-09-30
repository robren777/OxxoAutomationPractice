package RMS.util.PageObject;

import java.awt.Robot;
import java.io.File;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONObject;
import org.sikuli.script.App;
import org.sikuli.script.FindFailed;
import org.sikuli.script.Key;
import org.sikuli.script.Pattern;
import org.sikuli.script.Region;
import org.sikuli.script.Screen;
import org.sikuli.script.ScreenImage;
import org.sikuli.script.SikuliException;

import config.Constants;
import modelo.TestCase;
import utils.SikuliUtil;
import utils.controlm.ControlM;
import utils.password.PasswordUtil;
import utils.selenium.SeleniumUtil;
import utils.sql.SQLResult;
import utils.sql.SQLUtil;

public class Tiendas {

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
	private static final DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
	static String df;
	int numeroEvidencia = 0;

	@SuppressWarnings("static-access")
	public Tiendas(SikuliUtil sk, SeleniumUtil u, String cb, Robot r, Pattern pathimg, ArrayList<String> ListImage,
			HashMap<String, Pattern> ImgPatter, ArrayList<Pattern> patterns, File Fol, String status, SQLUtil dbRms,
			Boolean st, HashMap<String, String> data, TestCase testcase) throws SQLException {
		this.u = u;
		this.sk = sk;
		this.cb = cb;
		this.r = r;
		this.pathimg = pathimg;
		this.ListImage = ListImage;
		this.ImgPattern = ImgPatter;
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

	/**
	 * @author 1000072843 Abre el navegador e inserta la URL que se encuentre en el
	 *         DataProvider
	 */
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
	 * @author 1000072843 Cierra el navegador y la instacia del objeto driver de
	 *         selenium.
	 */
	public void CloseBrowser() {
		try {
			u.close();
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	/**
	 * @author 72843
	 * @throws InterruptedException
	 * @Comments realiza loguin en RMS
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
	 * @author 1000072843
	 * @param navega al modulo de tiendas
	 * @throws FindFailed
	 */
	public void Org_Herarky() throws FindFailed {
		// TODO Auto-generated method stub
		r.delay(10000);

		sk.screen.wait(ImgPattern.get("lbl_action"), 15).highlight(1, "red").click();
		sk.screen.wait(ImgPattern.get("lbl_attrib"), 15).highlight(1, "red").doubleClick();
		screenshot2();
	}

	/**
	 * @author 1000072843
	 * @throws FindFailed Navega en el Menú principal de RMS para ingresar al modulo
	 *                    de Herarquía de Mercancías
	 */
	public void MerchaHerar() throws FindFailed {
		try {
			waitImage(40, "lbl_action");
			sk.screen.wait(ImgPattern.get("lbl_action"), 15).highlight("red").click();
			u.hardWait(1);

			waitImage(40, "lbl_MerchHer");
			sk.screen.wait(ImgPattern.get("lbl_MerchHer"), 15).highlight("red").doubleClick();
			u.hardWait(1);
			screenshot2();
			u.hardWait(1);

			Screen.highlightAllOff();
			waitImage(20, "lbl_comer");

		} catch (Exception ex) {
			System.out.println(" -> " + ex);
			throw new AssertionError("Fallo al dirigirse a la pantalla Merchandise Hierarchy Main Form");
		}
	}

	/**
	 * @author 1000072843
	 * @throws FindFailed
	 * Selecciona el país a la que pertenece la compañia del proveedor en este caso esta enfocado a las compañias de PERÚ
	 * NOTA: ES IMPORTANTE MAPEAR PREVIO A LA EJECUCION AL PAIS AL QUE SE VAN A DIRIGIRNLAS PRUEBAS (PERU, MEXICO, CHILE, COLOMBIA, ETC.), 
	 * SE REALIZA IEN EL APARTADO DE "lbl_comerPeru"
	 */
	public void selectCompania() throws FindFailed {
		try {
			waitImage(20, "lbl_comer");
			sk.screen.wait(ImgPattern.get("lbl_comer"), 15).highlight("red");
			u.hardWait(1);
			screenshot2();
			u.hardWait(1);

			sk.screen.wait(ImgPattern.get("lbl_comer"), 15).doubleClick();
			u.hardWait(2);
			sk.screen.wait(ImgPattern.get("lbl_comer"), 15).doubleClick();

			Screen.highlightAllOff();

			waitImage(20, "box_superGrupo");
		} catch (Exception ex) {
			System.out.println(" -> " + ex);
			throw new AssertionError("Fallo al seleccionar compania");
		}
	}

	/**
	 * @author 1000072843
	 * @throws FindFailed Captura el grupo al que pertenece la categoría de la
	 *                    jerarquia a la que se va a modificar.
	 */
	public void CapSuperGrupo(String superGrupo) {
		try {
			waitImage(20, "box_superGrupo");

			sk.screen.find(ImgPattern.get("box_superGrupo")).right(60).click();
			u.hardWait(1);
			sk.screen.type(superGrupo);
			u.hardWait(1);
			sk.screen.type(Key.TAB);
			u.hardWait(1);

			waitImage(20, "btn_filter");
			sk.screen.wait(ImgPattern.get("btn_filter"), 15).doubleClick();
			u.hardWait(1);
			waitImage(20, "found");
			u.hardWait(1);
			screenshot2();
			u.hardWait(1);

			waitImage(20, "lbl_categoria");
			sk.screen.wait(ImgPattern.get("lbl_categoria"), 15).highlight("red");
			u.hardWait(1);
			screenshot2();
			u.hardWait(1);

			sk.screen.wait(ImgPattern.get("lbl_categoria"), 15).click();
			Screen.highlightAllOff();
			waitImage(20, "box_categoria");
		} catch (Exception ex) {
			System.out.println(" -> " + ex);
			throw new AssertionError("Fallo al seleccionar compania");
		}
	}

	/**
	 * @author 1000072843
	 * @return captura la categoría
	 * @throws FindFailed
	 * 
	 */
	public void CapCatego(String categoria) throws FindFailed {
		try {

			waitImage(20, "box_categoria");
			sk.screen.find(ImgPattern.get("box_categoria")).right(60).click();
			u.hardWait(1);
			sk.screen.type(categoria);
			u.hardWait(1);
			sk.screen.type(Key.TAB);
			u.hardWait(1);

			waitImage(20, "btn_filter");
			sk.screen.wait(ImgPattern.get("btn_filter"), 15).doubleClick();
			u.hardWait(1);
			waitImage(20, "found");
			u.hardWait(1);
			screenshot2();
			u.hardWait(1);

			sk.screen.wait(ImgPattern.get("lbl_subcategoria"), 15).highlight("red");
			u.hardWait(1);
			screenshot2();
			u.hardWait(1);

			sk.screen.wait(ImgPattern.get("lbl_subcategoria"), 15).click();

			Screen.highlightAllOff();
			waitImage(20, "lbl_subcatego");
		} catch (Exception ex) {
			System.out.println(" -> " + ex);
			throw new AssertionError("Fallo al seleccionar categoria");
		}
	}

	/**
	 * @author 1000072843
	 * @throws FindFailed Captura la subcategoría de la jeraquía de mercancías
	 */
	public void CapsubCatego(String subcategoria) throws FindFailed {
		try {
			waitImage(20, "lbl_subcatego");
			sk.screen.find(ImgPattern.get("lbl_subcatego")).right(60).click();
			u.hardWait(1);
			sk.screen.type(subcategoria);
			u.hardWait(1);
			sk.screen.type(Key.TAB);
			u.hardWait(1);

			waitImage(20, "btn_filter");
			sk.screen.wait(ImgPattern.get("btn_filter"), 15).doubleClick();
			u.hardWait(1);
			waitImage(20, "found");
			u.hardWait(1);
			screenshot2();
			u.hardWait(1);

			sk.screen.wait(ImgPattern.get("lbl_segmento"), 15).highlight("red");
			u.hardWait(1);
			screenshot2();
			u.hardWait(1);
			sk.screen.wait(ImgPattern.get("lbl_segmento"), 15).click();

			Screen.highlightAllOff();
			waitImage(20, "box_Segmento");
		} catch (Exception ex) {
			System.out.println(" -> " + ex);
			throw new AssertionError("Fallo al seleccionar categoria");
		}
	}

	/**
	 * @author 1000072843
	 * @throws FindFailed Capptura los datos del Segmento de la subcategoría
	 */
	public void Capsegmento(String subcategoria, String segmento) throws FindFailed {
		try {

			waitImage(20, "lbl_subcatego");
			sk.screen.find(ImgPattern.get("lbl_subcatego")).right(60).click();
			u.hardWait(1);
			sk.screen.type(subcategoria);
			u.hardWait(1);
			sk.screen.type(Key.TAB);
			u.hardWait(1);

			waitImage(20, "box_Segmento");
			sk.screen.find(ImgPattern.get("box_Segmento")).right(60).click();
			u.hardWait(1);
			sk.screen.type(segmento);
			u.hardWait(1);
			sk.screen.type(Key.TAB);
			u.hardWait(1);

			waitImage(20, "btn_filter");
			sk.screen.wait(ImgPattern.get("btn_filter"), 15).doubleClick();
			u.hardWait(1);
			waitImage(20, "found");
			u.hardWait(1);
			screenshot2();
			u.hardWait(1);

			sk.screen.wait(ImgPattern.get("lbl_Subseg"), 15).highlight("red");
			u.hardWait(1);
			screenshot2();
			u.hardWait(1);
			sk.screen.wait(ImgPattern.get("lbl_Subseg"), 15).click();

			Screen.highlightAllOff();
			waitImage(20, "boxInputs");
		} catch (Exception ex) {
			System.out.println(" -> " + ex);
			throw new AssertionError("Fallo al seleccionar categoria");
		}
	}

	/**
	 * @author 1000072843
	 * @throws FindFailed Da clic en el boton para agregar un subsegmento a la
	 *                    jerarquína de la mercancía
	 */
	public void clicBtnNew(String subcategoria, String segmento) throws FindFailed {
		try {

			waitImage(20, "boxInputs");

			waitImage(20, "lbl_subcatego");
			sk.screen.find(ImgPattern.get("lbl_subcatego")).right(60).click();
			u.hardWait(1);
			sk.screen.type(subcategoria);
			u.hardWait(1);
			sk.screen.type(Key.TAB);
			u.hardWait(1);

			waitImage(20, "box_Segmento");
			sk.screen.find(ImgPattern.get("box_Segmento")).right(60).click();
			u.hardWait(1);
			sk.screen.type(segmento);
			u.hardWait(1);
			sk.screen.type(Key.TAB);
			u.hardWait(1);

			waitImage(20, "btn_new");
			sk.screen.wait(ImgPattern.get("btn_new"), 15).highlight("red");
			u.hardWait(1);
			screenshot2();
			u.hardWait(1);
			sk.screen.wait(ImgPattern.get("btn_new"), 15).click();

			Screen.highlightAllOff();
			waitImage(20, "btn_add_seg");

		} catch (Exception ex) {
			System.out.println(" -> " + ex);
			throw new AssertionError("Fallo al dar clic en el boton New de la pantalla Merchandise Hierarchy");
		}
	}

	/**
	 * @author 1000072843
	 * @throws FindFailed Agrega el nombre del subsegmento de la categoría de la
	 *                    jerarquía de las mercancias
	 */
	public void addsubsegmento() throws FindFailed {
		try {

			sk.screen.wait(ImgPattern.get("btn_add_catego"), 15).highlight(1, "red").click();
			sk.screen.type(data.get("Segmento") + Key.TAB + data.get("SegmentoDesc"));
			sk.screen.wait(ImgPattern.get("btn_ok"), 15).highlight(1, "red").click();
			screenshot2();

		} catch (Exception ex) {
			System.out.println(" -> " + ex);
			throw new AssertionError("Fallo al dar clic en el boton New de la pantalla Merchandise Hierarchy");
		}
	}

	/**
	 * 
	 * @author 1000072843
	 * @param navega al modulo de tiendas
	 */
	public void OpenStore() {
		// TODO Auto-generated method stub

		try {
			u.hardWait(3);
			sk.screen.wait(ImgPattern.get("jr_store"), 15).highlight(1, "red").click();
			screenshot2();
			u.hardWait(3);
		} catch (FindFailed e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * @author 1000072843
	 * @param navega al modulo de tiendas
	 */
	public void CapturaTienta() {
		// TODO Auto-generated method stub

		try {
			u.hardWait(3);
			sk.screen.wait(ImgPattern.get("box_store"), 15).highlight(1, "red").click();
			sk.screen.type(data.get("Store") + Key.TAB);
			sk.screen.wait(ImgPattern.get("btn_filter"), 15).highlight(1, "red").click();
			screenshot2();
//			sk.screen.capture().save("img.png");			
		} catch (FindFailed e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * @author 1000072843
	 * @param navega al modulo de tiendas
	 */
	public void ModifVATRegion() {

		try {
//			sk.screen.wait(ImgPatter.get("btn_Edit"),15).highlight(1,"red").click();
			r.delay(5000);
			sk.screen.find(ImgPattern.get("lbl_VATregion")).right(60).click();
			sk.screen.type(data.get("VAT_Region") + Key.TAB);
			sk.screen.type(data.get("Area") + Key.TAB);
			u.hardWait(2);
			// sk.screen.type(Key.ENTER);
			sk.screen.wait(ImgPattern.get("btn_OK_jer"), 15).highlight(1, "red").click();
			screenshot2();
		} catch (SikuliException e) {
			e.printStackTrace();
		}

	}

	/**
	 * @author 1000072
	 * @param navega al modulo de tiendas
	 */
	public void ClicBtnEdit() {
		// TODO Auto-generated method stub

		try {
			u.hardWait(3);
			sk.screen.wait(ImgPattern.get("btn_edit"), 15).highlight(1, "red").click();
//			sk.screen.capture().save("img.png");	
			screenshot2();
		} catch (FindFailed e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * @author 1000072843
	 * @throws FindFailed Ingresa la fecha de cierre de tienda
	 */
	public void setFCierre() throws FindFailed {
		r.delay(5000);
		sk.screen.find(ImgPattern.get("lbl_SCD")).right(60).click();
		sk.screen.type(data.get("StoreCloseDate") + Key.TAB);
		screenshot2();

	}

	/**
	 * @author 1000072843
	 * @throws FindFailed Ingresa la orden de cierre de tienda desde DataProvider
	 */
	public void setNCierre() throws FindFailed {
		u.hardWait(2);
		sk.screen.type(data.get("StopOrderDays") + Key.TAB);
		screenshot2();

	}

	/**
	 * @author 1000072843
	 * @throws FindFailed actualiza la zona de transferencia de RMS
	 */
	public void UpdateTransferZone() throws FindFailed {
		u.hardWait(2);
		sk.screen.find(ImgPattern.get("lbl_TZ")).right(60).click();
		sk.screen.type(data.get("TransferZone") + Key.TAB);
		screenshot2();
		sk.screen.wait(ImgPattern.get("btn_OK_jer"), 15).highlight(1, "red").click();
		u.hardWait(3);
	}

	/**
	 * @author 1000072843
	 * @throws FindFailed Navega al modulo de Items en el menu principal de RMS
	 */
	public void navItems() throws FindFailed {
		r.delay(5000);
		sk.screen.wait(ImgPattern.get("lbl_items_1"), 15).click();
		u.hardWait(2);
		sk.screen.wait(ImgPattern.get("lbl_items_2"), 15).doubleClick();
		screenshot2();
	}

	/**
	 * @author 1000072843
	 * @throws FindFailed Navega al modulo de lista de articulos(Items), en el menu
	 *                    principal de RMS
	 */
	public void navItemList() throws FindFailed {
		r.delay(5000);
		sk.screen.wait(ImgPattern.get("lbl_items_1"), 15).click();
		r.delay(10000);
		sk.screen.wait(ImgPattern.get("lbl_item_List"), 15).doubleClick();
		screenshot2();
	}

	/**
	 * @author 1000072843
	 * @throws FindFailed Crea un nuevo Item/Articulo
	 */
	public void NewItem() throws FindFailed {

//		r.delay(5000);	 ItemNewVirtual
		sk.screen.wait(ImgPattern.get("actionItems"), 20).click();
		u.hardWait(1);
		sk.screen.wait(ImgPattern.get("NewItemVirtual2"), 20).click();
		screenshot2();
		sk.screen.type(Key.ENTER);
	}

	/**
	 * crea un Item Virtual nuevo ingresando categoría, subcategoría, segmento y
	 * subsegmento.
	 * 
	 * @throws FindFailed
	 * @author 1000072843
	 */
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
	 * Ingresa nombre del Item y descripción del ITEM
	 * 
	 * @throws FindFailed
	 * @author 1000072843
	 */
	public void fillBox() throws FindFailed {
		sk.screen.type(data.get("ItemName") + Key.TAB);
		u.hardWait(1);
		sk.screen.type(data.get("ItemComment") + Key.TAB);
		u.hardWait(1);
		screenshot2();
//		sk.screen.wait(ImgPatter.get("btnOKItems"),15).click();
	}

	/**
	 * Selecciona un ITEM de la lista de ITEMS
	 * 
	 * @throws FindFailed
	 * @author 1000072843
	 */
	public void clicselecItem() throws FindFailed {
		r.delay(5000);
		sk.screen.wait(ImgPattern.get("mn_options_Items"), 15).click();
		u.hardWait(2);
		sk.screen.wait(ImgPattern.get("select_Items"), 15).click();
		screenshot2();
	}

	/**
	 * Agrega un Item en la lista de ITEMS
	 * 
	 * @throws FindFailed
	 * @author 1000072843
	 */
	public void addItems() throws FindFailed {
		r.delay(5000);
		String[] Items = data.get("ListItem").split(",");
		for (String item : Items) {
			sk.screen.type(item + Key.TAB);
			sk.screen.wait(ImgPattern.get("btn_add"), 15).click();
		}
		u.hardWait(2);
		screenshot2();
		sk.screen.wait(ImgPattern.get("btn_ok"), 15).click();
	}

	/**
	 * Da clic en el boton OK dentro del modulo de lista de Items para agregar un
	 * nuevo Item en la lista.
	 * 
	 * @throws FindFailed
	 * @author 1000072843
	 */
	public void clicBtnOKItem() throws FindFailed {
//		btn_OkRepeat
		r.delay(5000);
//		sk.screen.find(ImgPatter.get("btn_OkRepeat")).left(60).click();
		sk.screen.wait(ImgPattern.get("btn_OK_repasado"), 15).click();
		screenshot2();
	}

	/**
	 * Navega en el menú principal de RMS para ingresar al modulo de location list
	 * 
	 * @throws FindFailed
	 * @author 1000072843
	 */
	public void navLocList() throws FindFailed {
//		btn_OkRepeat
		r.delay(5000);
//		sk.screen.find(ImgPatter.get("btn_OkRepeat")).left(60).click();
		sk.screen.wait(ImgPattern.get("lbl_control"), 15).click();
//		sk.screen.type(Key.DOWN);
		u.hardWait(1);
		sk.screen.find(ImgPattern.get("search")).right(60).click();
		u.hardWait(1);
		sk.screen.type("Location List");
		u.hardWait(1);
		sk.screen.wait(ImgPattern.get("find"), 15).click();
		r.delay(8000);
//		boolean imagenEx = sk.screen.has(ImgPatter.get("img_found"));
//		if (imagenEx){
//			sk.screen.type(Key.ENTER);
//		}
		sk.screen.type(Key.ENTER);
		sk.screen.wait(ImgPattern.get("btn_open"), 15).click();
		r.delay(5000);
		screenshot2();
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
	 * Selecciona la opcion de new para generar una nueva location list y al final
	 * da clic en el boton Enter para crear la nueva lista de Items.
	 * 
	 * @throws FindFailed
	 * @author 1000072843
	 */
	public void clicAction() throws FindFailed {

		r.delay(10000);
//		sk.screen.wait(ImgPatter.get("actionItems"),15).click();
		sk.screen.find(ImgPattern.get("lbl_action_LL")).right(60).click();
//		sk.screen.type("n");
		u.hardWait(1);
		sk.screen.wait(ImgPattern.get("new_LL"), 15).click();
		screenshot2();
		sk.screen.type(Key.ENTER);
//		sk.screen.wait(ImgPatter.get("btnOKItems"),15).click();
	}

	/**
	 * selecciona la opcion criteria para poder llenar los atributos de la location
	 * list
	 * 
	 * @throws FindFailed
	 * @author 1000072843
	 */
	public void criteria() throws FindFailed {

		u.hardWait(3);
		sk.screen.wait(ImgPattern.get("lbl_optionCriteria"), 15).click();
//		sk.screen.find(ImgPatter.get("lbl_optionCriteria")).left(60).click();
		u.hardWait(1);
		sk.screen.wait(ImgPattern.get("criteria"), 15).click();
		screenshot2();
	}

	/**
	 * Ingresa los valores de la location list, locationtype, storeSelected,
	 * elemnts, Comparison, Value, al final clic en el boton OK
	 * 
	 * @throws FindFailed
	 * @author 1000072843
	 */
	public void Fillcriteria() throws FindFailed {

		r.delay(10000);
//		sk.screen.wait(ImgPatter.get("lbl_optionCriteria"),15).click();btnOKItems
		sk.screen.find(ImgPattern.get("locationtype")).right(60).click();
		u.hardWait(1);
		sk.screen.wait(ImgPattern.get("storeSlctd"), 15).click();
		screenshot2();
		sk.screen.type(Key.TAB + Key.TAB);
		u.hardWait(1);
		sk.screen.type(data.get("Element") + Key.TAB);
		u.hardWait(1);
		sk.screen.type(data.get("Comparison") + Key.TAB);
		u.hardWait(1);
		sk.screen.type(data.get("value") + Key.TAB);
		u.hardWait(2);
		screenshot2();
		sk.screen.wait(ImgPattern.get("btnOKItems"), 15).click();
	}

	/**
	 * Ingresar información de la location list y obtiene el ID de la location lista
	 * para ingresarla en una variable globan "cb"
	 * 
	 * @throws FindFailed
	 * @author 1000072843
	 */
	public void fillBox_LL() throws FindFailed {
		sk.screen.type(data.get("Desc") + Key.TAB);
		u.hardWait(1);
		sk.screen.type(data.get("Comment") + Key.TAB);
		u.hardWait(3);
		sk.screen.find(ImgPattern.get("lbl_numberList")).right(60).click();
		u.hardWait(1);
		sk.screen.keyDown(Key.CTRL);
		sk.screen.keyDown("c");
		sk.screen.keyUp("c");
		sk.screen.keyUp(Key.CTRL);
		cb = App.getClipboard();
		u.hardWait(1);

		screenshot2();
//		sk.screen.wait(ImgPatter.get("btnOKItems"),15).click();
	}

	/**
	 * Ingresa los datos del proveedor
	 * 
	 * @throws FindFailed
	 * @author 1000072843
	 */
	public void fillvendor() throws FindFailed {
//		sk.screen.type(data.get("Desc")+Key.TAB);
//		u.hardWait(1);
//		sk.screen.type(data.get("Comment")+Key.TAB);
		r.delay(6000);
		sk.screen.find(ImgPattern.get("Id_deal")).right(60).click();
		u.hardWait(1);
		sk.screen.keyDown(Key.CTRL);
		sk.screen.keyDown("c");
		sk.screen.keyUp("c");
		sk.screen.keyUp(Key.CTRL);
		cb = App.getClipboard();
		u.hardWait(1);
//		u.hardWait(1);
		sk.screen.type(Key.TAB + Key.TAB + data.get("Vendor1") + Key.TAB);
		sk.screen.type(data.get("Vendor2") + Key.TAB);
		sk.screen.type(data.get("Currency") + Key.TAB);
		sk.screen.type(data.get("Type") + Key.TAB);
		sk.screen.type(data.get("ActiveDate") + Key.TAB);
		sk.screen.type(data.get("CloseDate") + Key.TAB);
		screenshot2();
		u.hardWait(1);
		sk.screen.wait(ImgPattern.get("btn_applyDeal"), 15).click();
	}

	/**
	 * Da clic en la opcion del menu Rebuild_Location_List
	 * 
	 * @throws FindFailed
	 * @author 1000072843
	 */
	public void rebuild() throws FindFailed {

		u.hardWait(3);
		sk.screen.wait(ImgPattern.get("lbl_optionCriteria"), 15).click();
//		sk.screen.find(ImgPatter.get("lbl_optionCriteria")).left(60).click();
		u.hardWait(1);
		sk.screen.wait(ImgPattern.get("Rebuild_Location_List"), 15).click();
		screenshot2();
		u.hardWait(3);
		sk.screen.type(Key.ENTER);
//		sk.screen.wait(ImgPatter.get("btnOKItems"),15).click();
	}

	/**
	 * Da clic en el Boton OK para el modulo de ITEMS
	 * 
	 * @throws FindFailed
	 * @author 1000072843
	 */
	public void clic_btn_OK() throws FindFailed {

		u.hardWait(3);
		sk.screen.wait(ImgPattern.get("btnOKItems"), 15).click();
		screenshot2();
	}

	/**
	 * clic en el boton SKUretel
	 * 
	 * @throws FindFailed
	 * @author 1000072843
	 */
	public void clic_btn_OK_skuretl() throws FindFailed {
		u.hardWait(3);
		sk.screen.wait(ImgPattern.get("btn_skuretl"), 15).click();
		screenshot2();
	}

	/**
	 * navega hacia el modulo de UDA dentro del menu principal de RMS
	 * 
	 * @throws FindFailed
	 * @author 1000072843
	 */
	public void navUDA() throws FindFailed {
		r.delay(5000);
		sk.screen.wait(ImgPattern.get("lbl_items_1"), 15).click();
		r.delay(5000);
		sk.screen.wait(ImgPattern.get("UDA"), 15).doubleClick();
		r.delay(5000);
//		sk.screen.type("User Defined Atributes");
//		u.hardWait(1);
//		sk.screen.wait(ImgPatter.get("Edit_func"),15).click();
//		r.delay(5000);
//		boolean imagenEx = sk.screen.has(ImgPatter.get("img_found"));
//		if (imagenEx){
//			sk.screen.type(Key.ENTER); btn_addUDA
//		}
//		sk.screen.type(Key.ENTER);
//		sk.screen.wait(ImgPatter.get("btn_open"),15).click();
		sk.screen.wait(ImgPattern.get("Edit_func"), 15).doubleClick();
		screenshot2();
		r.delay(5000);
	}

	/**
	 * da clic en el Boton AddUDA para crear un nuevo UDA
	 * 
	 * @throws FindFailed
	 * @author 1000072843
	 */
	public void clic_btn_addUDA() throws FindFailed {

//		u.hardWait(3);	
//		sk.screen.wait(ImgPatter.get("lbl_optionCriteria"),15).click();
////		sk.screen.find(ImgPatter.get("lbl_optionCriteria")).left(60).click();
//		u.hardWait(1);
//		sk.screen.wait(ImgPatter.get("Rebuild_Location_List"),15).click();
//		screenshot2();
//		u.hardWait(3);
//		sk.screen.type(Key.ENTER);
		sk.screen.wait(ImgPattern.get("btn_addUDA"), 15).click();
		screenshot2();
	}

	/**
	 * Ingresa los datos para la generación del nuevo UDA
	 * 
	 * @throws FindFailed
	 * @author 1000072843
	 */
	public void fillUDA() throws FindFailed {
		u.hardWait(3);
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
		screenshot2();
	}

	/**
	 * Selecciona un UDA previamente generado desde la lista de UDAs
	 * 
	 * @throws FindFailed
	 * @author 1000072843
	 */
	public void selectUDA() throws FindFailed {
		u.hardWait(3);
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

	/**
	 * Agrega el valor de nuevo UDA
	 * 
	 * @throws FindFailed
	 * @author 1000072843
	 */
	public void AddUDAValue() throws FindFailed {
		u.hardWait(3);
		sk.screen.type(data.get("UDAValues") + Key.TAB);
		u.hardWait(1);
		screenshot2();
	}

	public void NavOrdering() throws FindFailed {
		// TODO Auto-generated method stub
		r.delay(5000);
		sk.screen.wait(ImgPattern.get("ordering"), 15).click();
		r.delay(5000);
		sk.screen.wait(ImgPattern.get("lbl_dealsManagement"), 15).doubleClick();
		r.delay(5000);

	}

	public void close_RMS() throws FindFailed {

		sk.screen.wait(ImgPattern.get("btn_close_jer"), 15).highlight(1, "red").click();
		u.hardWait(1);
		sk.screen.wait(ImgPattern.get("mn_action"), 5).highlight(1, "red").click();
		sk.screen.wait(ImgPattern.get("smn_close"), 5).highlight(1, "red").click();

	}

	/**
	 * @author 1000068915
	 * @comments Cierra la pantalla de RMS repitiendo el paso 1 veces
	 */
	public void close_RMS_Menu() throws FindFailed {

		u.hardWait(1);
		sk.screen.wait(ImgPattern.get("mn_action"), 5).highlight(1, "red").click();
		sk.screen.wait(ImgPattern.get("smn_close"), 5).highlight(1, "red").click();

	}

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

	public void select_action() throws FindFailed {
		// TODO Auto-generated method stub

		r.delay(10000);
//		sk.screen.wait(ImgPatter.get("actionItems"),15).click();
		sk.screen.find(ImgPattern.get("lbl_action_LL")).right(60).click();
//		sk.screen.type("n");
		u.hardWait(1);
		sk.screen.wait(ImgPattern.get("new_LL"), 15).click();
		screenshot2();
//		sk.screen.type(Key.ENTER);
		sk.screen.wait(ImgPattern.get("btnOKItems"), 15).click();
		r.delay(8000);
	}

	public void clic_btn_Insert() throws FindFailed {
		// TODO Auto-generated method stub
		u.hardWait(1);
		sk.screen.wait(ImgPattern.get("btn_insert"), 15).click();
		screenshot2();

	}

	public void fillDealComponentType() throws FindFailed {
		// TODO Auto-generated method stub

		u.hardWait(1);
		sk.screen.wait(ImgPattern.get("btn_next"), 15).click();
		sk.screen.type(data.get("DealTypeCode") + Key.TAB);
		sk.screen.type(data.get("BillingType") + Key.TAB);
		sk.screen.type(data.get("DealClass") + Key.TAB);
		screenshot2();
		u.hardWait(1);
		sk.screen.wait(ImgPattern.get("btn_next_deal"), 15).click();

	}

	public void fillContentThreshold() throws FindFailed {
		// TODO Auto-generated method stub

		u.hardWait(1);
//		sk.screen.wait(ImgPatter.get("btn_next"),15).click();
		sk.screen.type(data.get("ThresholdValueType") + Key.TAB);
		u.hardWait(1);
		sk.screen.type(data.get("ThresholdLimitType") + Key.TAB);
		u.hardWait(1);
		sk.screen.type(data.get("ThresholdLimitUOM") + Key.TAB);
		u.hardWait(1);
		screenshot2();
		u.hardWait(1);
		sk.screen.wait(ImgPattern.get("btn_next_deal"), 15).click();

	}

	public void fillDealApp() throws FindFailed {
		// TODO Auto-generated method stub
		sk.screen.type(data.get("DealApplicationTiming") + Key.TAB);
		sk.screen.type(data.get("CostApplyType") + Key.TAB);
		sk.screen.type(Key.SPACE + Key.TAB);
		sk.screen.type(data.get("Comments") + Key.TAB);
		screenshot2();
		u.hardWait(1);
		sk.screen.wait(ImgPattern.get("btn_finish"), 15).click();

	}

	public void clic_btn_add() throws FindFailed {
		// TODO Auto-generated method stub
		u.hardWait(3);
		sk.screen.wait(ImgPattern.get("btn_add_deal"), 15).click();
		screenshot2();

	}

	public void fillItem() throws FindFailed {
		// TODO Auto-generated method stub
		r.delay(5000);
		sk.screen.find(ImgPattern.get("lbl_merchLevel")).right(60).click();
		sk.screen.wait(ImgPattern.get("slct_item"), 15).click();
		u.hardWait(1);
		sk.screen.type(Key.TAB + Key.TAB + data.get("Item") + Key.TAB);
		screenshot2();
		u.hardWait(1);
	}

	public void filLocation() throws FindFailed {
		// TODO Auto-generated method stub
		u.hardWait(2);
		sk.screen.type(data.get("OrgLevel") + Key.TAB);
		sk.screen.wait(ImgPattern.get("WareHouse"), 15).click();
		sk.screen.type(Key.TAB + data.get("WareHouse") + Key.TAB);
		screenshot2();
		u.hardWait(2);

	}

	public void fillOriginCountry() throws FindFailed {
		// TODO Auto-generated method stub
		u.hardWait(2);
		sk.screen.type(data.get("OriginCountry") + Key.TAB);
		sk.screen.wait(ImgPattern.get("btn_OK_DealAnual"), 15).click();
		screenshot2();

	}

	public void fillThresholds() throws FindFailed {
		// TODO Auto-generated method stub

//		u.hardWait(2);
//		sk.screen.type(data.get("OriginCountry")+Key.TAB);
		sk.screen.wait(ImgPattern.get("Threshold"), 30).click();
		u.hardWait(2);
		sk.screen.find(ImgPattern.get("lowerLimit")).below(50).click();
		sk.screen.type(data.get("LowerLimit") + Key.TAB);
		u.hardWait(1);
		sk.screen.type(data.get("UpperLimit") + Key.TAB);
		u.hardWait(1);
		sk.screen.type(data.get("Value") + Key.TAB);
		u.hardWait(1);
		sk.screen.type(Key.SPACE);
		u.hardWait(1);
		screenshot2();
		sk.screen.wait(ImgPattern.get("btn_save"), 30).click();
		u.hardWait(2);
		screenshot2();
	}

	public void submit() throws FindFailed {
		// TODO Auto-generated method stub

		u.hardWait(2);
//		sk.screen.type(data.get("OriginCountry")+Key.TAB);
		sk.screen.wait(ImgPattern.get("mn_options"), 20).click();
		sk.screen.wait(ImgPattern.get("smn_submit"), 20).click();
		screenshot2();

	}

	public void approve() throws FindFailed {
		// TODO Auto-generated method stub

		u.hardWait(2);
//		sk.screen.type(data.get("OriginCountry")+Key.TAB);
		sk.screen.wait(ImgPattern.get("mn_options"), 20).click();
		sk.screen.wait(ImgPattern.get("itm_approve"), 20).click();
		screenshot2();

	}

	public void OpenControl() throws FindFailed {
		// TODO Auto-generated method stub SubSegNum
		r.delay(5000);
		sk.screen.wait(ImgPattern.get("lbl_control_1"), 15).click();
		u.hardWait(1);
		screenshot2();

	}

	public void Open_Setup() throws FindFailed {
		// TODO Auto-generated method stub
		r.delay(5000);
		sk.screen.wait(ImgPattern.get("folder_setup"), 15).doubleClick();
		u.hardWait(1);
		screenshot2();
	}

	public void OpenPriceZoneGroup() throws FindFailed {
		// TODO Auto-generated method stub
		r.delay(5000);
		sk.screen.wait(ImgPattern.get("folder_PZG"), 15).doubleClick();
		u.hardWait(1);
		screenshot2();
	}

	public void addcatego(String segmentoDesc){
		try {
			waitImage(50, "btn_add_seg");
			waitImage(50, "subsegmentoNumber");
			
			sk.screen.wait(ImgPattern.get("subsegmentoNumber"), 10).doubleClick();
			
			sk.screen.keyDown(Key.CTRL);
		    sk.screen.keyDown("c");
		    
		    sk.screen.keyUp("c");
		    sk.screen.keyUp(Key.CTRL);
		    

		    String subsegmentoNo = App.getClipboard();
		    System.out.println("Subsegmento Actual" + subsegmentoNo);
		    
		    boolean status = false;
		    
		    while(status == false) {
		    	
		    	sk.screen.type(Key.DOWN);
		    	u.hardWait(1);
		    	
		    	try {
		    		waitImage(1, "subsegmentoNumberNew");
		    		sk.screen.wait(ImgPattern.get("subsegmentoNumberNew"), 1).click();
		    		status = true;
		    		break;
		    	}catch (Exception e) {
		    		sk.screen.keyDown(Key.SHIFT);
			    	u.hardWait(1);
				    sk.screen.type(Key.RIGHT);
				    sk.screen.type(Key.RIGHT);
				    sk.screen.type(Key.RIGHT);
				    u.hardWait(1);
				    sk.screen.keyUp(Key.SHIFT);
			    	
			    	sk.screen.keyDown(Key.CTRL);
				    sk.screen.keyDown("c");
				    
				    sk.screen.keyUp("c");
				    sk.screen.keyUp(Key.CTRL);
				    

				    subsegmentoNo = App.getClipboard();
				    System.out.println(subsegmentoNo);
				    
				    System.out.println("Subsegmento Actual" + subsegmentoNo);
		    	}
		    	
		    }
		    
		    int subSegmentoNumber = Integer.parseInt(subsegmentoNo);
		    
		    subSegmentoNumber++;
		    subsegmentoNo = "" + subSegmentoNumber;
		    
		    waitImage(2, "btn_add_seg");
			sk.screen.wait(ImgPattern.get("btn_add_seg"), 15).highlight("red").click();
			u.hardWait(3);
			sk.screen.type(subsegmentoNo);
			u.hardWait(1);
			sk.screen.type(Key.TAB);
			u.hardWait(1);
			
			sk.screen.type(segmentoDesc);
			u.hardWait(1);
			screenshot2();
			u.hardWait(1);
			
			Screen.highlightAllOff();
			waitImage(20, "BtnOkSubSegMain");
			sk.screen.wait(ImgPattern.get("BtnOkSubSegMain"), 15).click();

			waitImage(20, "boxInputs");
			
			waitImage(20, "lbl_subsegmento");
			sk.screen.find(ImgPattern.get("lbl_subsegmento")).right(60).click();
			u.hardWait(1);
			sk.screen.type(subsegmentoNo);
			u.hardWait(1);
			sk.screen.type(Key.TAB);
			
			waitImage(20, "btn_filter");
			sk.screen.wait(ImgPattern.get("btn_filter"), 15).doubleClick();
			u.hardWait(1);
			waitImage(20, "found");
			u.hardWait(1);
			screenshot2();
			u.hardWait(1);
			
			waitImage(20, "btnClose");
			sk.screen.wait(ImgPattern.get("btnClose"), 15).click();

			Screen.highlightAllOff();
			waitImage(20, "menu");
		} catch (Exception ex) {
			System.out.println(" -> " + ex);
			throw new AssertionError("Fallo al agregar un nuevo Subsegmento");
		}

	}

	public void clicEditZG() throws FindFailed {
		// TODO Auto-generated method stub
		u.hardWait(3);
		sk.screen.wait(ImgPattern.get("lbl_EditZG"), 15).doubleClick();
		screenshot2();

	}

	public void SelectGZ() throws FindFailed {
		// TODO Auto-generated method stub
		r.delay(5000);
		sk.screen.find(ImgPattern.get("lbl_ZG")).below(35).click();
		screenshot2();

	}

	public void DoubleClicBTNZones() throws FindFailed {
		// TODO Auto-generated method stub
		r.delay(5000);
		sk.screen.wait(ImgPattern.get("btn_zones"), 15).click();
		screenshot2();

	}

	public void select_stores() throws FindFailed {
		// TODO Auto-generated method stub lbl_store
		u.hardWait(3);
		sk.screen.find(ImgPattern.get("lbl_zoneID")).below(60).click();
		sk.screen.wait(ImgPattern.get("btn_stores_ZG"), 15).click();
		screenshot2();
		r.delay(5000);
		sk.screen.find(ImgPattern.get("lbl_store")).below(35).click();
		sk.screen.type(Key.TAB + Key.TAB + data.get("IDZG") + Key.TAB + data.get("PrimaryStore") + Key.TAB
				+ data.get("ED") + Key.TAB);
		sk.screen.type(Key.ENTER);
		u.hardWait(3);
		sk.screen.type(Key.ENTER);
		screenshot2();

	}

	public void NavMaintenance() throws FindFailed {
		try {
			waitImage(50, "lbl_control_1");

			sk.screen.wait(ImgPattern.get("lbl_control_1"), 15).highlight("red").doubleClick();
			u.hardWait(1);

			waitImage(50, "lbl_supplier_m");
			sk.screen.wait(ImgPattern.get("lbl_supplier_m"), 15).highlight("red").doubleClick();
			u.hardWait(1);

			waitImage(50, "lbl_maintence");
			sk.screen.wait(ImgPattern.get("lbl_maintence"), 15).highlight("red").doubleClick();
			u.hardWait(1);
			screenshot2();

			Screen.highlightAllOff();
			waitImage(25, "actionItems");
			u.hardWait(1);
			screenshot2();

		} catch (Exception ex) {
			System.out.println(" -> " + ex);
			throw new AssertionError("Fallo al llenar el campo Location");
		}
	}

	public void selectActionEditDir() throws FindFailed {
		try {
			waitImage(25, "actionItems");
			sk.screen.wait(ImgPattern.get("actionItems"), 15).click();
			u.hardWait(1);
			waitImage(25, "slct_edit_dir");
			sk.screen.wait(ImgPattern.get("slct_edit_dir"), 15).click();
			u.hardWait(1);
			sk.screen.find(ImgPattern.get("lbl_supplier_dir")).highlight("red").right(60).click();
			u.hardWait(1);
			sk.screen.type(data.get("Supplier"));
			u.hardWait(1);
			sk.screen.type(Key.TAB);
			u.hardWait(1);
			screenshot2();
			u.hardWait(1);
			Screen.highlightAllOff();
			sk.screen.type(Key.ENTER);
			u.hardWait(1);

			waitImage(25, "mn_options");
			u.hardWait(3);

		} catch (Exception ex) {
			System.out.println(" -> " + ex);
			throw new AssertionError("Fallo al llenar la pantalla Supplier Search Window");
		}

	}

	public void clicAddress() throws FindFailed {
		try {
			waitImage(25, "mn_options");
			sk.screen.wait(ImgPattern.get("mn_options"), 15).highlight("red").click();
			u.hardWait(1);
			waitImage(25, "mn_address");
			sk.screen.wait(ImgPattern.get("mn_address"), 15).highlight("red");
			u.hardWait(1);
			screenshot2();
			u.hardWait(1);
			sk.screen.wait(ImgPattern.get("mn_address"), 15).click();

			Screen.highlightAllOff();

			waitImage(25, "lbl_addressType");
			u.hardWait(1);
		} catch (Exception ex) {
			System.out.println(" -> " + ex);
			throw new AssertionError("Fallo al dar clic en la opcion Address en el menu Options");
		}
	}

	public void editAddress01() throws FindFailed {
		try {

			waitImage(25, "lbl_addressType");
			u.hardWait(1);
			sk.screen.wait(ImgPattern.get("lbl_addressType")).highlight("red").right(45).click();
			u.hardWait(1);
			sk.screen.type(data.get("Adtype"));
			u.hardWait(1);

			try {
				waitImage(5, "msgNoAddresses");
				u.hardWait(1);
				sk.screen.wait(ImgPattern.get("msgNoAddresses")).click();
				u.hardWait(1);
				sk.screen.type(Key.ENTER);
				u.hardWait(3);
			} catch (Exception e) {
				System.out.println("No se muestra Mensaje No addresses of this type exist.");
			}

			waitImage(25, "address1");
			sk.screen.wait(ImgPattern.get("address1")).highlight("red").right(45).click();
			u.hardWait(1);
			sk.screen.type(data.get("address1") + Key.TAB);
			u.hardWait(1);
			sk.screen.type(data.get("address2") + Key.TAB);
			u.hardWait(1);
			sk.screen.type(data.get("address3") + Key.TAB);
			u.hardWait(1);

			waitImage(25, "pCity");
			sk.screen.wait(ImgPattern.get("pCity")).highlight("red").right(45).doubleClick();
			u.hardWait(1);
			sk.screen.type(data.get("city"));
			u.hardWait(1);

			waitImage(25, "pdirState");
			sk.screen.wait(ImgPattern.get("pdirState")).highlight("red").right(45).click();
			u.hardWait(1);
			sk.screen.type(data.get("state"));
			u.hardWait(1);

			waitImage(25, "pdirPostalCode");
			sk.screen.wait(ImgPattern.get("pdirPostalCode")).highlight("red").right(45).click();
			u.hardWait(1);
			sk.screen.type(data.get("PC"));
			u.hardWait(1);

			waitImage(25, "pdirCountry");
			sk.screen.wait(ImgPattern.get("pdirCountry")).highlight("red").right(45).click();
			u.hardWait(1);
			sk.screen.type(data.get("country"));
			u.hardWait(1);

			waitImage(25, "pdirName");
			sk.screen.wait(ImgPattern.get("pdirName")).highlight("red").right(45).click();
			u.hardWait(1);
			sk.screen.type(data.get("name"));
			u.hardWait(1);

			waitImage(25, "pdirPhone");
			sk.screen.wait(ImgPattern.get("pdirPhone")).highlight("red").right(45).click();
			u.hardWait(1);
			sk.screen.type(data.get("phone"));
			u.hardWait(1);

			waitImage(25, "pdirFax");
			sk.screen.wait(ImgPattern.get("pdirFax")).highlight("red").right(45).click();
			u.hardWait(1);
			sk.screen.type(data.get("fax"));

			waitImage(25, "pdirEmail");
			sk.screen.wait(ImgPattern.get("pdirEmail")).highlight("red").right(45).click();
			u.hardWait(1);
			sk.screen.type(data.get("email"));

			u.hardWait(1);
			screenshot2();
			u.hardWait(1);

			Screen.highlightAllOff();

		} catch (Exception ex) {
			System.out.println(" -> " + ex);
			throw new AssertionError("Fallo al dar llenar la nueva Direccion");
		}
	}

	public void editAddress02() throws FindFailed {
		try {

			waitImage(25, "lbl_addressType");
			u.hardWait(1);
			sk.screen.wait(ImgPattern.get("lbl_addressType")).highlight("red").right(45).click();
			u.hardWait(1);
			sk.screen.type(data.get("Adtype1"));
			u.hardWait(1);

			try {
				waitImage(5, "msgNoAddresses");
				u.hardWait(1);
				sk.screen.wait(ImgPattern.get("msgNoAddresses")).click();
				u.hardWait(1);
				sk.screen.type(Key.ENTER);
				u.hardWait(3);
			} catch (Exception e) {
				System.out.println("No se muestra Mensaje No addresses of this type exist.");
			}

			waitImage(25, "address1");
			sk.screen.wait(ImgPattern.get("address1")).highlight("red").right(45).click();
			u.hardWait(1);
			sk.screen.type(data.get("address11") + Key.TAB);
			u.hardWait(1);
			sk.screen.type(data.get("address21") + Key.TAB);
			u.hardWait(1);
			sk.screen.type(data.get("address31") + Key.TAB);
			u.hardWait(1);

			waitImage(25, "pCity");
			sk.screen.wait(ImgPattern.get("pCity")).highlight("red").right(45).doubleClick();
			u.hardWait(1);
			sk.screen.type(data.get("city1"));
			u.hardWait(1);

			waitImage(25, "pdirState");
			sk.screen.wait(ImgPattern.get("pdirState")).highlight("red").right(45).click();
			u.hardWait(1);
			sk.screen.type(data.get("state1"));
			u.hardWait(1);

			waitImage(25, "pdirPostalCode");
			sk.screen.wait(ImgPattern.get("pdirPostalCode")).highlight("red").right(45).click();
			u.hardWait(1);
			sk.screen.type(data.get("PC1"));
			u.hardWait(1);

			waitImage(25, "pdirCountry");
			sk.screen.wait(ImgPattern.get("pdirCountry")).highlight("red").right(45).click();
			u.hardWait(1);
			sk.screen.type(data.get("country1"));
			u.hardWait(1);

			waitImage(25, "pdirName");
			sk.screen.wait(ImgPattern.get("pdirName")).highlight("red").right(45).click();
			u.hardWait(1);
			sk.screen.type(data.get("name1"));
			u.hardWait(1);

			waitImage(25, "pdirPhone");
			sk.screen.wait(ImgPattern.get("pdirPhone")).highlight("red").right(45).click();
			u.hardWait(1);
			sk.screen.type(data.get("phone1"));
			u.hardWait(1);

			waitImage(25, "pdirFax");
			sk.screen.wait(ImgPattern.get("pdirFax")).highlight("red").right(45).click();
			u.hardWait(1);
			sk.screen.type(data.get("fax1"));

			waitImage(25, "pdirEmail");
			sk.screen.wait(ImgPattern.get("pdirEmail")).highlight("red").right(45).click();
			u.hardWait(1);
			sk.screen.type(data.get("email1"));

			u.hardWait(1);
			screenshot2();
			u.hardWait(1);

			Screen.highlightAllOff();

		} catch (Exception ex) {
			System.out.println(" -> " + ex);
			throw new AssertionError("Fallo al dar llenar la nueva Direccion");
		}

	}

	public void editAddress03() throws FindFailed {

		try {

			waitImage(25, "lbl_addressType");
			u.hardWait(1);
			sk.screen.wait(ImgPattern.get("lbl_addressType")).highlight("red").right(45).click();
			u.hardWait(1);
			sk.screen.type(data.get("Adtype12"));
			u.hardWait(1);

			try {
				waitImage(5, "msgNoAddresses");
				u.hardWait(1);
				sk.screen.wait(ImgPattern.get("msgNoAddresses")).click();
				u.hardWait(1);
				sk.screen.type(Key.ENTER);
				u.hardWait(3);
			} catch (Exception e) {
				System.out.println("No se muestra Mensaje No addresses of this type exist.");
			}

			waitImage(25, "address1");
			sk.screen.wait(ImgPattern.get("address1")).highlight("red").right(45).click();
			u.hardWait(1);
			sk.screen.type(data.get("address112") + Key.TAB);
			u.hardWait(1);
			sk.screen.type(data.get("address212") + Key.TAB);
			u.hardWait(1);
			sk.screen.type(data.get("address312") + Key.TAB);
			u.hardWait(1);

			waitImage(25, "pCity");
			sk.screen.wait(ImgPattern.get("pCity")).highlight("red").right(45).doubleClick();
			u.hardWait(1);
			sk.screen.type(data.get("city12"));
			u.hardWait(1);

			waitImage(25, "pdirState");
			sk.screen.wait(ImgPattern.get("pdirState")).highlight("red").right(45).click();
			u.hardWait(1);
			sk.screen.type(data.get("state12"));
			u.hardWait(1);

			waitImage(25, "pdirPostalCode");
			sk.screen.wait(ImgPattern.get("pdirPostalCode")).highlight("red").right(45).click();
			u.hardWait(1);
			sk.screen.type(data.get("PC12"));
			u.hardWait(1);

			waitImage(25, "pdirCountry");
			sk.screen.wait(ImgPattern.get("pdirCountry")).highlight("red").right(45).click();
			u.hardWait(1);
			sk.screen.type(data.get("country12"));
			u.hardWait(1);

			waitImage(25, "pdirName");
			sk.screen.wait(ImgPattern.get("pdirName")).highlight("red").right(45).click();
			u.hardWait(1);
			sk.screen.type(data.get("name12"));
			u.hardWait(1);

			waitImage(25, "pdirPhone");
			sk.screen.wait(ImgPattern.get("pdirPhone")).highlight("red").right(45).click();
			u.hardWait(1);
			sk.screen.type(data.get("phone12"));
			u.hardWait(1);

			waitImage(25, "pdirFax");
			sk.screen.wait(ImgPattern.get("pdirFax")).highlight("red").right(45).click();
			u.hardWait(1);
			sk.screen.type(data.get("fax12"));

			waitImage(25, "pdirEmail");
			sk.screen.wait(ImgPattern.get("pdirEmail")).highlight("red").right(45).click();
			u.hardWait(1);
			sk.screen.type(data.get("email12"));

			u.hardWait(1);
			screenshot2();
			u.hardWait(1);

			Screen.highlightAllOff();

		} catch (Exception ex) {
			System.out.println(" -> " + ex);
			throw new AssertionError("Fallo al dar llenar la nueva Direccion");
		}

	}

	public void clic_btn_OK_dir() throws FindFailed {
		try {
			waitImage(25, "btn_ok_slctd");
			sk.screen.wait(ImgPattern.get("btn_ok_slctd"), 15).click();
			waitImage(25, "mn_options");
		} catch (Exception ex) {
			System.out.println(" -> " + ex);
			throw new AssertionError("Fallo al dar clic en el boton OK");
		}
	}

	public void clic_btn_OK_Close() throws FindFailed {
		try {
			waitImage(25, "btnOKClose");
			sk.screen.wait(ImgPattern.get("btnOKClose"), 15).highlight("red");
			u.hardWait(1);
			screenshot2();
			u.hardWait(1);
			Screen.highlightAllOff();
			sk.screen.wait(ImgPattern.get("btnOKClose"), 15).click();
			waitImage(25, "lbl_supplier_dir");

		} catch (Exception ex) {
			System.out.println(" -> " + ex);
			throw new AssertionError("Fallo al dar clic en el boton OK");
		}
	}

	/*
	 * funciones principales
	 * 
	 */

	public void clicImage(String fol, String Img) {

	}

	/**
	 * @param ATC_FT_020_CrearNuevaZonadePrecio.java
	 * @throws FindFailed
	 */

	public void NavOPZ() throws FindFailed {
		// TODO Auto-generated method stub
		sk.screen.wait(ImgPattern.get("lbl_control"), 15000).highlight(1, "red").click();
		sk.screen.wait(ImgPattern.get("folder_setup"), 15000).highlight(1, "red").doubleClick();
		sk.screen.wait(ImgPattern.get("folder_PZG"), 15000).highlight(1, "red").doubleClick();
		sk.screen.wait(ImgPattern.get("lbl_new"), 15000).highlight(1, "red").doubleClick();
		screenshot2();
	}

	public void clicbtnaddNZP() throws FindFailed {
		// TODO Auto-generated method stub

		sk.screen.wait(ImgPattern.get("btn_add"), 15000).highlight(1, "red").click();
		sk.screen.type(data.get("idZone") + Key.TAB + data.get("pricingLevel") + data.get("Desc") + Key.TAB
				+ data.get("LikeZG"));
		sk.screen.wait(ImgPattern.get("btn_zones"), 50).highlight(1, "red").click();
		screenshot2();

	}

	public void clicbtnOkUL() throws FindFailed {
		// TODO Auto-generated method stub btn_ok_store_underline
		sk.screen.wait(ImgPattern.get("btn_OK_PZG"), 20).highlight(1, "red").click();
		screenshot2();
	}

	public void fil_ItemVirtual() throws FindFailed {
		// TODO Auto-generated method stub

		sk.screen.wait(ImgPattern.get("ItemType"), 20);
		sk.screen.type("PLU" + Key.TAB + "Level 1");
		u.hardWait(2);
		sk.screen.type(Key.TAB + data.get("Item") + Key.TAB + "descripcion" + Key.TAB);
		u.hardWait(2);
		sk.screen.type("descripcion corta" + Key.TAB);
		u.hardWait(2);
		sk.screen.type(Key.TAB + Key.SPACE); // measure
		u.hardWait(2);
		sk.screen.type(Key.TAB + Key.SPACE); // forecastable
		u.hardWait(2);
		sk.screen.type(Key.TAB + Key.SPACE + Key.TAB); // Catch Weight
		u.hardWait(2);
		sk.screen.type("1" + Key.TAB); // Retail Zone
		u.hardWait(2);
		sk.screen.type("1" + Key.TAB); // Cost Zone
		u.hardWait(2);
		sk.screen.type(Key.TAB + data.get("Selling Unit Retail") + Key.TAB + data.get("Suggested Retail") + Key.TAB);
//	sk.screen.find(ImgPatter.get(""))
		u.hardWait(2);
		screenshot2();
		sk.screen.wait(ImgPattern.get("btn_grocAttributes"), 50).highlight(1, "red").click();

	}

	public void FillItemMaster() throws FindFailed {
		// TODO Auto-generated method stub
		sk.screen.wait(ImgPattern.get("img_itemMaster"), 30);
		sk.screen.type("1" + Key.TAB);
		u.hardWait(2);
		sk.screen.type("CM" + Key.TAB);
		sk.screen.wait(ImgPattern.get("btn_ok"), 50).click();
		screenshot2();

	}

	public void ClicBtnSupplier() throws FindFailed {
		// TODO Auto-generated method stub

		sk.screen.wait(ImgPattern.get("btn_supplier1"), 30).click();
		screenshot2();

	}

	public void AddSupplier() throws FindFailed {
		// TODO Auto-generated method stub
		sk.screen.wait(ImgPattern.get("btn_addsupplier"), 30).highlight(1, "red").click();
		sk.screen.type(data.get("Supplier") + Key.TAB);
		sk.screen.wait(ImgPattern.get("btn_apply_supplier_1"), 30).highlight(1, "red").click();
		screenshot2();

	}

	public void Clicbtn_ISOC() throws FindFailed {
		// TODO Auto-generated method stub btn_ISOC
		sk.screen.wait(ImgPattern.get("btn_ISOC"), 30).highlight(1, "red").click();
		screenshot2();
	}

	public void SelectOriginCountry() throws FindFailed {
		// TODO Auto-generated method stub

		sk.screen.type("PE" + Key.TAB);
		sk.screen.wait(ImgPattern.get("btn_create_sup"), 30).highlight(1, "red").click();
		sk.screen.type(data.get("CaseCost") + Key.TAB + data.get("UnitCost") + Key.TAB);
		u.hardWait(3);
		sk.screen.find(ImgPattern.get("lbl_innerSize")).below(20).click();
		sk.screen.type(data.get("InnerSize") + Key.TAB + data.get("CaseSize") + Key.TAB);
		u.hardWait(2);
		sk.screen.type(data.get("Tier") + Key.TAB + data.get("Height") + Key.TAB);
		u.hardWait(2);
		screenshot2();

	}

	public void FillPackageType() throws FindFailed {
		// TODO Auto-generated method stub btn_OK_supplier
		sk.screen.wait(ImgPattern.get("slct_PackageType"), 30).highlight(1, "red").click();
		sk.screen.wait(ImgPattern.get("opt_each"), 30).highlight(1, "red").click();
		u.hardWait(3);
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
		u.hardWait(3);
		screenshot2();
		sk.screen.wait(ImgPattern.get("btn_ok_rbz"), 30).highlight(1, "red").click();

	}

	public void clicRetailbyZone() throws FindFailed {
		// TODO Auto-generated method stub btn_RetailbyZone
		sk.screen.wait(ImgPattern.get("btn_RetailbyZone"), 30).highlight(1, "red").click();
		screenshot2();
	}

	public void clicLocations() throws FindFailed {
		// TODO Auto-generated method stub
		sk.screen.wait(ImgPattern.get("locations"), 30).highlight(1, "red").click();
		screenshot2();

	}

	public void ItemLocation() throws FindFailed {
		// TODO Auto-generated method stub btn_apply_location
		sk.screen.wait(ImgPattern.get("GroupType"), 30).highlight(1, "red").click();
		sk.screen.type("Store" + Key.ENTER);
		u.hardWait(1);
		sk.screen.type(data.get("GroupValue") + Key.TAB);
		u.hardWait(2);
		sk.screen.wait(ImgPattern.get("btn_apply_location"), 30).highlight(1, "red").click();
		u.hardWait(3);
		screenshot2();
		sk.screen.wait(ImgPattern.get("btn_ok_location"), 30).highlight(1, "red").click();
	}

	public void ClicSubmit() throws FindFailed {
		// TODO Auto-generated method stub
		sk.screen.wait(ImgPattern.get("submit"), 30).highlight(1, "red").click();
		screenshot2();

	}

	public void ClicApprove() throws FindFailed {
		// TODO Auto-generated method stub
		sk.screen.wait(ImgPattern.get("approve"), 30).highlight(1, "red").click();
		screenshot2();
		sk.screen.wait(ImgPattern.get("btn_ok_approve"), 30).highlight(1, "red").click();
		screenshot2();
	}

	/**
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

	public void Newprice() throws FindFailed {
		// TODO Auto-generated method stub
		sk.screen.wait(ImgPattern.get("lbl_action_item"), 20);
		sk.screen.type("New" + Key.TAB);
		u.hardWait(2);
		sk.screen.type("Retail" + Key.TAB);
		u.hardWait(1);
		sk.screen.type("1" + Key.TAB);
		u.hardWait(1);
		sk.screen.type("Item List" + Key.TAB);
		u.hardWait(1);
		sk.screen.type(data.get("Item") + Key.TAB);
		u.hardWait(2);
		screenshot2();
		sk.screen.type(Key.ENTER);
//		sk.screen.wait(ImgPatter.get("retailCost"), 30).highlight(1, "red").doubleClick();
		screenshot2();

	}

	public String pcskust() throws FindFailed {
		// TODO Auto-generated method stub
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
		sk.screen.type("Descripcion" + Key.TAB + Key.TAB);
		u.hardWait(1);
		sk.screen.type("2");
		u.hardWait(1);
		sk.screen.wait(ImgPattern.get("btn_locations"), 30).highlight(1, "red").click();
		screenshot2();

		return cb;

	}

	/**
	 * @author 1000072843
	 * @throws FindFailed
	 */
	public void pcstore() throws FindFailed {
		// TODO Auto-generated method stub slctType
		r.delay(5000);
		sk.screen.find(ImgPattern.get("slctType")).below(20).click();
		sk.screen.wait(ImgPattern.get("zone"), 20).highlight(1, "red").click();
		sk.screen.type(Key.TAB + "1" + Key.TAB + "Comentario" + Key.TAB);
		u.hardWait(1);
		sk.screen.type(Key.ENTER);
		screenshot2();
		u.hardWait(1);
		sk.screen.wait(ImgPattern.get("OK"), 30).highlight(1, "red").click();
		screenshot2();

	}

	public void resetPOS() throws FindFailed {
		// TODO Auto-generated method stub resetPOS btn_apply_price
		u.hardWait(3);
		sk.screen.find(ImgPattern.get("lbl_type")).below(25).click();
		sk.screen.wait(ImgPattern.get("resetPOS"), 30).highlight(1, "red").click();
		sk.screen.wait(ImgPattern.get("btn_apply_price"), 30).highlight(1, "red").click();
		sk.screen.wait(ImgPattern.get("mdl_ok"), 30).highlight(1, "red").click();
		sk.screen.find(ImgPattern.get("priceChange")).right(45).click();
		sk.screen.keyDown(Key.CTRL);
		sk.screen.keyDown("c");
		sk.screen.keyUp("c");
		sk.screen.keyUp(Key.CTRL);
		cb = App.getClipboard();
		screenshot2();

	}

	public void mdlNO() throws FindFailed {
//		mdl_no
		sk.screen.wait(ImgPattern.get("mdl_no"), 30).highlight(1, "red").click();

	}

	public void modStatus() {
		// TODO Auto-generated method stub

		try {
			sk.screen.wait(ImgPattern.get("slect_Status"), 30).click();
			sk.screen.type("activo");
			screenshot2();
			u.hardWait(2);
		} catch (FindFailed e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void editUDA() {
		// TODO Auto-generated method stub
		try {
			sk.screen.wait(ImgPattern.get("UDA_edit"), 30).click();
			sk.screen.type("9545");
			screenshot2();
			u.hardWait(2);
		} catch (FindFailed e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void clicVATM() {
		// TODO Auto-generated method stub

		try {

			sk.screen.wait(ImgPattern.get("VATM"), 30).click();
			screenshot2();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
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
			throw new AssertionError("Fallo al llenar cerrar RDM");
		}
	}
}
