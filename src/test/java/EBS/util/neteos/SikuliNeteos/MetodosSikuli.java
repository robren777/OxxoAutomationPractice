package EBS.util.neteos.SikuliNeteos;

import java.awt.HeadlessException;
import java.awt.Toolkit;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import org.apache.poi.EncryptedDocumentException;
import org.sikuli.script.Button;
import org.sikuli.script.FindFailed;
import org.sikuli.script.Key;
import org.sikuli.script.Pattern;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import modelo.BaseExecution;
import utils.ExcelUtil;
import utils.SikuliUtil;
import utils.selenium.SeleniumUtil;

public class MetodosSikuli {

	SikuliUtil sk;
	SeleniumUtil u;
	Pattern pathimg;
	ArrayList<String> ListImage;
	HashMap<String, Pattern> ImgPatter;
	ArrayList<Pattern> patterns;
	File Fol;
	protected static final Logger logger = LoggerFactory.getLogger(BaseExecution.class);

	public MetodosSikuli(SikuliUtil sk, SeleniumUtil u, Pattern pathimg, ArrayList<String> ListImage,
			HashMap<String, Pattern> ImgPatter, ArrayList<Pattern> patterns, File Fol) {
		this.u = u;
		this.sk = sk;
		this.pathimg = pathimg;
		this.ListImage = ListImage;
		this.ImgPatter = ImgPatter;
		this.patterns = patterns;
		this.Fol = Fol;
	}

	public void verificarEstado(String patternToSearch, Integer timeOut) {
		PatternUtil patternUtil = new PatternUtil(sk, u, pathimg, ListImage, ImgPatter, patterns, Fol);
		MonitoringPattern monitoringPattern = new MonitoringPattern(sk, patternUtil, ImgPatter, Fol, patternToSearch);
		Thread threadRunJob = new Thread(monitoringPattern);
		threadRunJob.start();
		int time = 0;
		while (threadRunJob.isAlive() && time <= timeOut) {
			try {
				Thread.sleep(2000);
				time = time + 2000;

			} catch (InterruptedException e) {
				logger.error(String.valueOf(e));
				e.printStackTrace();
			}

		}

		if (threadRunJob.isAlive()) {
			monitoringPattern.stopping();
		}

	}

	/**
	 * Metodo donde se obtiene la ruta de las imagenes
	 * 
	 * @param fol
	 * @return ImgPatter
	 * @author ricardo.palacios
	 */
	public HashMap<String, Pattern> getPatternsImg(File fol) {
		File[] files = Fol.listFiles();
		for (File s : files) {
			patterns.add(new Pattern(s.getAbsolutePath()).similar((float) .95));
		}
		for (Pattern p : patterns) {
			String fname = p.getFilename().toString();
			fname = fname.replace("\\", "-");
			fname = fname.replace(".png", "");
			fname = fname.replace(".PNG", "");
			String[] afname = fname.split("-");
			ImgPatter.put(afname[afname.length - 1], p);
		}

		return ImgPatter;
	}

	/**
	 * Maneja la pantalla de solicitud unica, verifica si la imagen existe y
	 * presiona la tecla Enter
	 * 
	 * @throws FindFailed
	 */
	public void cancelarSolicitudUnica() throws FindFailed {

		ImgPatter = getPatternsImg(Fol);
		sk.screen.wait(ImgPatter.get("cancelarsolicitud"), 15).click();
	}

	/**
	 * Maximiza la ventana enviando la combinacion de teclas WIN + UP
	 */
	public void appletMaximize() {
		sk.screen.keyDown(Key.WIN + Key.UP);
		sk.screen.keyUp(Key.WIN + Key.UP);
	}

	/**
	 * Metodo para manejar la ventana de advertencia que aparece al abrir un applet
	 *
	 * @author JesusH@hexaware.com
	 */
	public void runApplet() {
		System.out.println("Run Applet");
		ImgPatter = getPatternsImg(Fol);
		try {

			sk.screen.exists(ImgPatter.get("doYouWantToRunThisApplication"));
			sk.screen.click(ImgPatter.get("checkBoxSecurityWarning"));
			sk.screen.type(Key.ENTER);
			u.hardWait(10);

		} catch (FindFailed e) {
			logger.error("Tiempo de ejecucion excedido " + e);
			System.out.println("Tiempo de ejecucion excedido " + e);
		}
		u.hardWait(5);
		appletMaximize();
	}

	/**
	 * Escribe el texto en una caja de texto que ya tenga el cursor activo y
	 * presiona la tecla TAB
	 *
	 * @param text
	 * @author ricardo.palacios
	 */
	public void typeAndTab(String text) {
		sk.screen.type(text);
		sk.screen.type(Key.TAB);
	}

	/**
	 * Metodo que acepta la solicitud unica en los applets
	 * 
	 * @throws FindFailed
	 * @author ricardo.palacios
	 */
	public void inicio() throws FindFailed {

		ImgPatter = getPatternsImg(Fol);
		sk.screen.wait(ImgPatter.get("iniciook"), 15).click();

	}

	/**
	 * Metodo que da click en el navegador de applets
	 * 
	 * @throws FindFailed
	 * @author ricardo.palacios
	 */
	public void navegador() throws FindFailed {

		ImgPatter = getPatternsImg(Fol);
		sk.screen.wait(ImgPatter.get("navegador"), 15).click();

	}

	/**
	 * Metodo que cierra una ventana de el navegador
	 * 
	 * @throws FindFailed
	 * @author ricardo.palacios
	 */
	public void cerrar() throws FindFailed {

		ImgPatter = getPatternsImg(Fol);
		sk.screen.wait(ImgPatter.get("cerrar"), 15).click();

	}

	/**
	 * Metodo que da click en drop down de estado
	 * 
	 * @throws FindFailed
	 * @author ricardo.palacios
	 */
	public void clickTipoEstado() throws FindFailed {

		ImgPatter = getPatternsImg(Fol);
		sk.screen.wait(ImgPatter.get("aprobacion"), 15).click();

	}

	/**
	 * Metodo que da click en pestana estado
	 * 
	 * @throws FindFailed
	 * @author ricardo.palacios
	 */
	public void clickPestanaEstado() throws FindFailed {

		ImgPatter = getPatternsImg(Fol);
		sk.screen.wait(ImgPatter.get("estado"), 15).click();

	}

	/**
	 * Metodo que escribe en fecha del proceso
	 * 
	 * @param fecha
	 * @param plaza
	 * @param metodoIncremento
	 * @param arrendamiento
	 * @param aplicarIncrementos
	 * @throws FindFailed
	 */
	public void fechaProceso(String fecha, String plaza, String metodoIncremento, String arrendamiento,
			String aplicarIncrementos) throws FindFailed {

		ImgPatter = getPatternsImg(Fol);
		sk.screen.wait(ImgPatter.get("fechaproceso"), 15).click();
		u.hardWait(2);
		sk.screen.type(fecha);
		sk.screen.type(Key.TAB);
		sk.screen.type(plaza);
		u.hardWait(1);
		sk.screen.type(metodoIncremento);
		sk.screen.type(Key.TAB);
		u.hardWait(1);
		sk.screen.type(arrendamiento);
		sk.screen.type(Key.TAB);
		u.hardWait(1);
		sk.screen.keyDown(Key.DELETE);
		sk.screen.keyUp(Key.DELETE);
		sk.screen.keyDown(Key.DELETE);
		sk.screen.keyUp(Key.DELETE);
		u.hardWait(1);
		sk.screen.type(aplicarIncrementos);
		sk.screen.keyDown(Key.ENTER);
		sk.screen.keyUp(Key.ENTER);
		sk.screen.keyDown(Key.DOWN);
		sk.screen.keyUp(Key.DOWN);
		u.hardWait(1);
		sk.screen.keyDown(Key.ENTER);
		sk.screen.keyUp(Key.ENTER);

	}

	/**
	 * Metodo que cierra las ventanas
	 * 
	 * @throws FindFailed
	 * @author ricardo.palacios
	 */
	public void cerrarTodo() throws FindFailed {

		ImgPatter = getPatternsImg(Fol);
		sk.screen.wait(ImgPatter.get("case"), 15).click();
		sk.screen.wait(ImgPatter.get("cerrarventana"), 15).click();
		sk.screen.wait(ImgPatter.get("desechar"), 15).click();

	}

	/**
	 * Metodo para ingresar fechas de inicio y fin
	 * 
	 * @param fechainicio
	 * @param fechafin
	 * @throws FindFailed
	 * @author ricardo.palacios
	 */
	public void ingresarFechaInicioFechaFin(String fechainicio, String fechafin) throws FindFailed {

		sk.screen.type(Key.TAB);
		sk.screen.type(Key.TAB);
		sk.screen.type(fechainicio);
		sk.screen.type(fechafin);

	}

	/**
	 * Metodo para ingresar campo el applet 52070300
	 * 
	 * @param campo
	 * @throws FindFailed
	 * @author ricardo.palacios
	 */
	public void ingresarCampo(String campo) throws FindFailed {

		sk.screen.type(campo);
		sk.screen.type(Key.TAB);
		System.out.println("Tab already clicked");

	}

	/**
	 * Metodo para abrir responsabilidades
	 * 
	 * @throws FindFailed
	 * @author ricardo.palacios
	 */
	public void abrirResp() throws FindFailed {

		u.hardWait(2);
		sk.screen.wait(ImgPatter.get("1"), 15).click();
		sk.screen.wait(ImgPatter.get("2"), 15).click();
		u.hardWait(2);

	}

	/**
	 * Metodo para abrir solicitudes internas
	 * 
	 * @throws FindFailed
	 * @author ricardo.palacios
	 */
	public void abrirSolicitudesInternas() throws FindFailed {

		u.hardWait(2);
		sk.screen.wait(ImgPatter.get("solicitudesinternas"), 15).click();
		u.hardWait(1);
		sk.screen.keyDown(Key.ENTER);
		sk.screen.keyUp(Key.ENTER);
		u.hardWait(1);
		sk.screen.keyDown(Key.ENTER);
		sk.screen.keyUp(Key.ENTER);

	}

	/**
	 * Metodo para escribir solicitud a buscar
	 * 
	 * @param ns Numero de solicitud
	 * @throws FindFailed
	 * @author ricardo.palacios
	 */
	public void EscribirNumeroSolicitud(String ns) throws FindFailed {

		u.hardWait(2);
		sk.screen.keyDown(Key.DELETE);
		sk.screen.keyUp(Key.DELETE);
		sk.screen.keyDown(Key.TAB);
		sk.screen.keyUp(Key.TAB);
		sk.screen.type(ns);
		sk.screen.keyDown(Key.ENTER);
		sk.screen.keyUp(Key.ENTER);

		sk.screen.keyDown(Key.TAB);
		sk.screen.keyUp(Key.TAB);
		sk.screen.keyDown(Key.TAB);
		sk.screen.keyUp(Key.TAB);
		sk.screen.keyDown(Key.TAB);
		sk.screen.keyUp(Key.TAB);
		sk.screen.keyDown(Key.TAB);
		sk.screen.keyUp(Key.TAB);
		sk.screen.keyDown(Key.TAB);
		sk.screen.keyUp(Key.TAB);
		sk.screen.keyDown(Key.TAB);
		sk.screen.keyUp(Key.TAB);
		sk.screen.keyDown(Key.TAB);
		sk.screen.keyUp(Key.TAB);
		sk.screen.keyDown(Key.TAB);
		sk.screen.keyUp(Key.TAB);
		sk.screen.keyDown(Key.TAB);
		sk.screen.keyUp(Key.TAB);
		sk.screen.keyDown(Key.TAB);
		sk.screen.keyUp(Key.TAB);

	}

	/**
	 * Metodo para escribir solicitud a buscar
	 * 
	 * @param ns Numero de solicitud
	 * @throws FindFailed
	 * @author ricardo.palacios
	 */
	public void EscribirNumeroSolicitud2(String ns) throws FindFailed {

		u.hardWait(2);
		sk.screen.keyDown(Key.DELETE);
		sk.screen.keyUp(Key.DELETE);
		sk.screen.keyDown(Key.TAB);
		sk.screen.keyUp(Key.TAB);
		sk.screen.type(ns);
		sk.screen.keyDown(Key.ENTER);
		sk.screen.keyUp(Key.ENTER);
		u.hardWait(1);
		sk.screen.keyDown(Key.ENTER);
		sk.screen.keyUp(Key.ENTER);
		u.hardWait(1);
		sk.screen.keyDown(Key.ENTER);
		sk.screen.keyUp(Key.ENTER);

		sk.screen.keyDown(Key.TAB);
		sk.screen.keyUp(Key.TAB);
		sk.screen.keyDown(Key.TAB);
		sk.screen.keyUp(Key.TAB);
		sk.screen.keyDown(Key.TAB);
		sk.screen.keyUp(Key.TAB);
		sk.screen.keyDown(Key.TAB);
		sk.screen.keyUp(Key.TAB);
		sk.screen.keyDown(Key.TAB);
		sk.screen.keyUp(Key.TAB);
		sk.screen.keyDown(Key.TAB);
		sk.screen.keyUp(Key.TAB);
		sk.screen.keyDown(Key.TAB);
		sk.screen.keyUp(Key.TAB);
		sk.screen.keyDown(Key.TAB);
		sk.screen.keyUp(Key.TAB);
		sk.screen.keyDown(Key.TAB);
		sk.screen.keyUp(Key.TAB);
		sk.screen.keyDown(Key.TAB);
		sk.screen.keyUp(Key.TAB);

	}

	/**
	 * Metodo para buscar responsabilidad activa
	 * 
	 * @param resp Resp a buscar
	 * @throws FindFailed
	 * @author ricardo.palacios
	 */
	public void buscarResp(String resp) throws FindFailed {

		sk.screen.keyDown(Key.F11);
		sk.screen.keyUp(Key.F11);
		sk.screen.type(resp);
		sk.screen.keyDown(Key.CTRL);
		sk.screen.keyDown(Key.F11);
		sk.screen.keyUp(Key.F11);
		sk.screen.keyUp(Key.CTRL);

	}

	/**
	 * Metodo para ingresar campo el applet 52070300
	 * 
	 * @param navegador
	 * @throws FindFailed
	 * @author ricardo.palacios
	 */
	public void buscarResponsabilidad(String navegador) throws FindFailed {

		u.hardWait(3);
		sk.screen.wait(ImgPatter.get("buscarmx"), 15).click();
		sk.screen.type(navegador);
		sk.screen.wait(ImgPatter.get("buscarnavegador"), 15).click();

	}

	/**
	 * Metodo que ingresa los parametros de compania y periodo
	 * 
	 * @param compania
	 * @param periodo
	 * @throws FindFailed
	 * @author ricardo.palacios
	 */
	public void ingresarParametros(String compania, String periodo) throws FindFailed {
		sk.screen.mouseMove(14);
		sk.screen.click();
		sk.screen.type(compania);
		ImgPatter = getPatternsImg(Fol);
		sk.screen.wait(ImgPatter.get("puntos"), 15).click();
		sk.screen.type(periodo);

	}

	/**
	 * Metodo para aceptar archivo
	 * 
	 * @throws FindFailed
	 * @author ricardo.palacios
	 */
	public void aceptarArchivo1() throws FindFailed {

		ImgPatter = getPatternsImg(Fol);
		sk.screen.wait(ImgPatter.get("aceptararchivo"), 15).click();

	}

	/**
	 * Metodo para aceptar archivo
	 * 
	 * @throws FindFailed
	 * @author ricardo.palacios
	 */
	public void aceptarArchivo() throws FindFailed {

		ImgPatter = getPatternsImg(Fol);
		sk.screen.type(Key.TAB);
		sk.screen.wait(ImgPatter.get("acepuno"), 15).click();

	}

	/**
	 * Metodo para dar click el boton ejecutar
	 * 
	 * @throws FindFailed
	 * @author ricardo.palacios
	 */
	public void ejecutar() throws FindFailed {

		ImgPatter = getPatternsImg(Fol);
		sk.screen.wait(ImgPatter.get("ejecutar"), 15).click();

	}

	/**
	 * Metodo para no realizar otra solicitud
	 * 
	 * @throws FindFailed
	 * @author ricardo.palacios
	 */
	public void otraSolicitud() throws FindFailed {

		ImgPatter = getPatternsImg(Fol);
		sk.screen.wait(ImgPatter.get("otrasolicitud"), 15).click();

	}

	/**
	 * Metodo que selecciona boton ver solicitudes
	 * 
	 * @throws FindFailed
	 * @author ricardo.palacios
	 */
	public void vers() throws FindFailed {
		u.hardWait(3);
		ImgPatter = getPatternsImg(Fol);
		sk.screen.wait(ImgPatter.get("ver"), 15).click();
		u.hardWait(1);
		sk.screen.wait(ImgPatter.get("solicitudes"), 15).click();

	}

	/**
	 * Metodo que selecciona boton encontrar
	 * 
	 * @throws FindFailed
	 * @author ricardo.palacios
	 */
	public void encontrar() throws FindFailed {

		u.hardWait(3);
		ImgPatter = getPatternsImg(Fol);
		sk.screen.wait(ImgPatter.get("encontrar"), 15).click();

	}

	/**
	 * Metodo que selecciona boton encontrar en boton de tipo distinto
	 * 
	 * @throws FindFailed
	 * @author ricardo.palacios
	 */
	public void encontrarBotonLargo() throws FindFailed {

		ImgPatter = getPatternsImg(Fol);
		sk.screen.wait(ImgPatter.get("encontrarlargo"), 15).click();

	}

	/**
	 * Metodo que selecciona boton ejecutar nueva solicitud
	 * 
	 * @throws FindFailed
	 * @author ricardo.palacios
	 */
	public void ejecutarNuevaSolicitud() throws FindFailed {

		u.hardWait(3);
		ImgPatter = getPatternsImg(Fol);
		sk.screen.wait(ImgPatter.get("nuevasolicitud"), 15).click();

	}

	/**
	 * Metodo para visualizar output
	 * 
	 * @throws FindFailed
	 * @author ricardo.palacios
	 */
	public void visualizarOutput() throws FindFailed {

		ImgPatter = getPatternsImg(Fol);
		sk.screen.wait(ImgPatter.get("visualizaroutput"), 15).click();

	}

	/**
	 * Metodo para extraer un dato de un archivo excel
	 * 
	 * @param nombreArchivo
	 * @param numeroDeHoja
	 * @param numeroDeRenglon
	 * @param numeroDeColumna
	 * @return contenidoCelda
	 * @throws EncryptedDocumentException
	 * @throws IOException
	 * @author ricardo.palacios
	 */
	public String extraerDatoExcel(String nombreArchivo, int numeroDeHoja, int numeroDeColumna, int numeroDeRenglon)
			throws EncryptedDocumentException, IOException {

		String currentUser = System.getProperty("user.name"); // Obtener el user actual del equipo
		String rutaDelArchivo = "C:\\Users\\" + currentUser + "\\Downloads\\" + nombreArchivo;
		System.out.println("la ruta del archivo es : " + rutaDelArchivo);
		ExcelUtil hoja = new ExcelUtil(rutaDelArchivo);
		hoja.open();
		String contenidoCelda = hoja.getCell(numeroDeHoja, numeroDeRenglon, numeroDeColumna);
		return contenidoCelda;

	}

	/**
	 * Metodo para visualizar output
	 * 
	 * @throws FindFailed
	 * @author edwin.ramirez@neoris.com
	 */
	public void visualizarRegistro() throws FindFailed {
		ImgPatter = getPatternsImg(Fol);
		sk.screen.mouseUp();
		sk.screen.wait(ImgPatter.get("visualizarregistro"), 15).click();
	}

	/**
	 * Metodo para visualizar registro
	 * 
	 * @throws FindFailed
	 * @author ricardo.palacios
	 */
	public void visualizaRegistro() throws FindFailed {

		ImgPatter = getPatternsImg(Fol);
		sk.screen.wait(ImgPatter.get("registro"), 15).click();

	}

	/**
	 * Metodo que cierra boton con foco
	 * 
	 * @throws FindFailed
	 * @author ricardo.palacios
	 */
	public void cerrarconfoco() throws FindFailed {

		ImgPatter = getPatternsImg(Fol);
		sk.screen.wait(ImgPatter.get("acepuno"), 15).click();

	}

	/**
	 * Metodo que ejecuta boton ejecutar
	 * 
	 * @throws FindFailed
	 * @author ricardo.palacios
	 */
	public void ejecutarBoton() throws FindFailed {

		ImgPatter = getPatternsImg(Fol);
		sk.screen.wait(ImgPatter.get("ejecutar"), 15).click();

	}

	/**
	 * Metodo que ejecuta boton no
	 * 
	 * @throws FindFailed
	 * @author ricardo.palacios
	 */
	public void ejecutarBotonNo() throws FindFailed {

		ImgPatter = getPatternsImg(Fol);
		sk.screen.wait(ImgPatter.get("no"), 15).click();

	}

	/**
	 * Metodo que ejecuta boton anexos
	 * 
	 * @throws FindFailed
	 * @author ricardo.palacios
	 */
	public void ejecutarBotonAnexos() throws FindFailed {

		ImgPatter = getPatternsImg(Fol);
		sk.screen.wait(ImgPatter.get("anexo"), 15).click();

	}

	/**
	 * Metodo que cierra una ventana
	 * 
	 * @throws FindFailed
	 * @author ricardo.palacios
	 */
	public void cerrar1() throws FindFailed {

		ImgPatter = getPatternsImg(Fol);
		sk.screen.wait(ImgPatter.get("cerrar"), 15).click();

	}

	/**
	 * Metodo que cierra una ventana
	 * 
	 * @throws FindFailed
	 * @author ricardo.palacios
	 */
	public void cerrarNavegador() throws FindFailed {

		u.hardWait(3);
		sk.screen.keyDown(Key.ALT);
		sk.screen.keyDown(Key.F4);
		sk.screen.keyUp(Key.F4);
		sk.screen.keyUp(Key.ALT);
		u.hardWait(3);
		ImgPatter = getPatternsImg(Fol);
		sk.screen.wait(ImgPatter.get("cerrar2"), 15).click();

	}

	/**
	 * Metodo que cierra ventanas de un applet
	 * 
	 * @throws FindFailed
	 * @author ricardo.palacios
	 */
	public void cerrarTodoN() throws FindFailed {

		ImgPatter = getPatternsImg(Fol);
		sk.screen.wait(ImgPatter.get("cerraralterno"), 15).click();
		sk.screen.wait(ImgPatter.get("cerrar2"), 15).click();
		sk.screen.wait(ImgPatter.get("cierra"), 15).click();
		sk.screen.wait(ImgPatter.get("aceptarcerrar"), 15).click();

	}

	/**
	 * Metodo que cierra solicitud entera
	 * 
	 * @throws FindFailed
	 * @author ricardo.palacios
	 */
	public void cerrarSolicitudEntera() throws FindFailed {

		ImgPatter = getPatternsImg(Fol);
		u.hardWait(3);
		sk.screen.wait(ImgPatter.get("cerrar2"), 15).click();
		u.hardWait(3);
		sk.screen.wait(ImgPatter.get("cerrar2"), 15).click();
		u.hardWait(3);
		sk.screen.wait(ImgPatter.get("cerrar2"), 15).click();

	}

	/**
	 * Metodo que cierra todas las ventanas
	 * 
	 * @throws FindFailed
	 * @author ricardo.palacios
	 */
	public void cerrarTodo1() throws FindFailed {

		ImgPatter = getPatternsImg(Fol);
		sk.screen.click(Key.ESC);
		sk.screen.wait(ImgPatter.get("cerrar2"), 15).click();
		sk.screen.wait(ImgPatter.get("cierra"), 15).click();
		sk.screen.wait(ImgPatter.get("aceptarcerrar"), 15).click();

	}

	/**
	 * Metodo que cierra todas las ventanas
	 * 
	 * @throws FindFailed
	 * @author ricardo.palacios
	 */
	public void c() throws FindFailed {

		sk.screen.keyDown(Key.ALT);
		sk.screen.keyDown(Key.F4);
		sk.screen.keyUp(Key.F4);
		sk.screen.keyUp(Key.ALT);
		u.hardWait(3);
		sk.screen.keyDown(Key.ALT);
		sk.screen.keyDown(Key.F4);
		sk.screen.keyUp(Key.F4);
		sk.screen.keyUp(Key.ALT);
		ImgPatter = getPatternsImg(Fol);
		sk.screen.wait(ImgPatter.get("aceptarcerrar"), 15).click();

	}

	/**
	 * Emula presionar la tecla 'Alt' + alguna tecla que necesitemos (solo una)
	 * 
	 * @param x
	 * @author JesusH@hexaware.com
	 */
	public void keyAlt(String x) {
		sk.screen.keyDown(Key.ALT);
		sk.screen.type(x);
		sk.screen.keyUp(Key.ALT);
	}

	/**
	 * Ventana de confirmacion para salir de Oracle Applications
	 * 
	 * @param x
	 * @throws FindFailed
	 */
	public void ventanaConfirmarSalir(String x) throws FindFailed {
		ImgPatter = getPatternsImg(Fol);
		sk.screen.exists(ImgPatter.get("deseaSalir"));
		keyAlt(x);
		u.hardWait(5);
	}

	/**
	 * Metodo que cierra todas las ventanas
	 * 
	 * @param x Tecla que se presionara
	 * @throws FindFailed
	 * @author ricardo.palacios
	 */
	public void cerrarResp(String x) throws FindFailed {

		u.hardWait(3);
		sk.screen.keyDown(Key.ALT);
		sk.screen.keyDown(Key.F4);
		sk.screen.keyUp(Key.F4);
		sk.screen.keyUp(Key.ALT);
		ImgPatter = getPatternsImg(Fol);
		sk.screen.exists(ImgPatter.get("deseaSalir"));
		keyAlt(x);

	}

	/**
	 * Cierra la ventana enviando la combinacion de teclas 'Alt' + 'F4'mas ventana
	 * de desea cerrar
	 * 
	 * @throws FindFailed
	 * @author JesusH@hexaware.com
	 */
	public void closeWindow1() throws FindFailed {

		u.hardWait(6);
		ImgPatter = getPatternsImg(Fol);
		sk.screen.wait(ImgPatter.get("cerrar2")).click();
		u.hardWait(6);
		sk.screen.wait(ImgPatter.get("cerrar2")).click();
		u.hardWait(6);
		sk.screen.wait(ImgPatter.get("cerrar2")).click();
		u.hardWait(3);
		sk.screen.keyDown(Key.ENTER);
		sk.screen.keyUp(Key.ENTER);

	}

	/**
	 * Metodo que ingresa texto en una caja de texto
	 * 
	 * @param campoSvc
	 * @throws FindFailed
	 * @author ricardo.palacios
	 */
	public void ingresarCampoSvc(String campoSvc) throws FindFailed {

		sk.screen.type(campoSvc);
		sk.screen.type(Key.TAB);

	}

	/**
	 * Metodo que escribe en el campo archivo
	 * 
	 * @param archivo
	 * @throws FindFailed
	 * @author ricardo.palacios
	 */
	public void buscarArchivo(String archivo) throws FindFailed {

		sk.screen.type(archivo);

	}

	/**
	 * Metodo que escribe en el campo archivo y da un tab
	 * 
	 * @param archivo
	 * @throws FindFailed
	 * @author ricardo.palacios
	 */
	public void buscarArchivoTab(String archivo) throws FindFailed {

		sk.screen.type(archivo);
		sk.screen.type(Key.TAB);

	}

	/**
	 * Metodo que escribe y acepta campos plaza fechauno y fechados
	 * 
	 * @param plaza
	 * @param fechauno
	 * @param fechados
	 * @throws FindFailed
	 * @author ricardo.palacios
	 */
	public void CamposPlazaFechas(String plaza, String fechauno, String fechados) throws FindFailed {

		sk.screen.type(plaza);
		sk.screen.type(fechauno);
		sk.screen.type(Key.TAB);
		sk.screen.type(fechados);

	}

	/**
	 * Metodo que escribe y acepta campos plaza fechauno y fechados sin plaza
	 * 
	 * @param fechauno
	 * @param fechados
	 * @throws FindFailed
	 * @author ricardo.palacios
	 */
	public void CamposPlazaFechassinplaza(String fechauno, String fechados) throws FindFailed {

		sk.screen.type(Key.TAB);
		sk.screen.type(fechauno);
		sk.screen.type(Key.TAB);
		sk.screen.type(fechados);

	}

	/**
	 * Metodo que cierra todas las ventanas
	 * 
	 * @throws FindFailed
	 * @author ricardo.palacios
	 */
	public void cerrarTodo2() throws FindFailed {

		ImgPatter = getPatternsImg(Fol);
		sk.screen.wait(ImgPatter.get("cerrar"), 15).click();
		sk.screen.wait(ImgPatter.get("cerrar2"), 15).click();
		sk.screen.wait(ImgPatter.get("cierra"), 15).click();
		sk.screen.wait(ImgPatter.get("aceptarcerrar"), 15).click();

	}

	/**
	 * Metodo que cierra todas las ventanas
	 * 
	 * @throws FindFailed
	 * @author ricardo.palacios
	 */
	public void cerrarconteclas() throws FindFailed {

		ImgPatter = getPatternsImg(Fol);
		u.hardWait(8);
		sk.screen.keyDown(Key.ESC);
		sk.screen.wait(ImgPatter.get("cerrar2"), 15).click();
		sk.screen.wait(ImgPatter.get("cierra"), 15).click();
		sk.screen.wait(ImgPatter.get("aceptarcerrar"), 15).click();

	}

	/**
	 * Metodo para aceptar la advertencia
	 * 
	 * @throws FindFailed
	 */
	public void aceptarPrecaucion() throws FindFailed {
		sk.screen.wait(ImgPatter.get("aceptarWarning"), 15).click();
	}

	/**
	 * Metodo para dar click en importacion de solicitud interna
	 * 
	 * @throws FindFailed
	 */
	public void ClickImportacionSolicitudInterna() throws FindFailed {
		sk.screen.wait(ImgPatter.get("impsol"), 15).click();
	}

	/**
	 * Metodo para dar click en conciliacion tesoreria
	 * 
	 * @throws FindFailed
	 */
	public void ClickConciliacionTesoreria() throws FindFailed {
		sk.screen.wait(ImgPatter.get("conciliaciontesoreria"), 15).click();
	}

	/**
	 * Metodo que da click en el navegador mostrando el Registro
	 * 
	 * @author edwin.ramirez
	 * @throws FindFailed
	 */
	public void clickRegistroNavegador() throws FindFailed {
		sk.screen.wait(ImgPatter.get("registronavegador"), 15).click();
	}

	/**
	 * Metodo que da click en el navegador en base a una imagen, selecciona toda el
	 * texto y busca un String dentro del texto.
	 * 
	 * @param nameImgToClick
	 * @param strToFind
	 * @return String con el texto
	 * @throws FindFailed
	 * @throws IOException
	 * @throws UnsupportedFlavorException
	 * @throws HeadlessException
	 * @author edwin.ramirez
	 */
	public String getTextDelNavegador(String nameImgToClick, String strToFind)
			throws FindFailed, HeadlessException, UnsupportedFlavorException, IOException {
		sk.screen.wait(ImgPatter.get(nameImgToClick), 15).click(); // Encuentra la imagen a dar click en el navegador

		sk.screen.keyDown(Key.CTRL); // Presiona CTRL+A para marcar todo el texto y suelta las teclas
		sk.screen.keyDown("a");
		sk.screen.keyUp(Key.CTRL);
		sk.screen.keyUp("a");

		sk.screen.keyDown(Key.CTRL); // Presiona CTRL+C para copiar todo el texto y suelta las teclas
		sk.screen.keyDown("c");
		sk.screen.keyUp(Key.CTRL);
		sk.screen.keyUp("c");
		// El texto que copiamos
		String textFound = (String) Toolkit.getDefaultToolkit().getSystemClipboard().getData(DataFlavor.stringFlavor);
		System.out.println(textFound); // Imprime el texto que copiamos
		String find = strToFind; // El String a buscar dentro de lo que copiamos
		String findResult = ""; // Lo que regresaermos

		int i = textFound.indexOf(find);
		if (i > 0) {
			findResult = textFound.substring(i, i + find.length());
			System.out.println(textFound.substring(i, i + find.length()));
		} else {
			findResult = "string not found";
			System.out.println("string not found");
		}

		sk.screen.mouseDown(Button.LEFT);
		sk.screen.mouseUp(Button.LEFT);
		return findResult; // Si lo encuentra lo regresa

	}

	/**
	 * Metodo que da click en el boton Ejecutar Nueva Solicitud
	 * 
	 * @throws FindFailed
	 * @author edwin.ramirez
	 */
	public void clickNuevaSolicitud() throws FindFailed {
		ImgPatter = getPatternsImg(Fol);
		sk.screen.wait(ImgPatter.get("nuevasolicitud"), 15).click();
	}

	/**
	 * Metodo que llena el campo Nombre del Archivo
	 * 
	 * @param nombreArchivo
	 * @throws FindFailed
	 * @author edwin.ramirez
	 */
	public void ingresarNombreArchivo(String nombreArchivo) throws FindFailed {
		sk.screen.type(nombreArchivo);
		sk.screen.wait(ImgPatter.get("aceptararchivo"), 15).click();
	}

	/**
	 * Metodo que busca dentro del filtro de Nombre
	 * 
	 * @param nombreSolicitud
	 * @throws FindFailed
	 * @author edwin.ramirez
	 */
	public void buscarNombreSolicitud(String nombreSolicitud) throws FindFailed {
		sk.screen.wait(ImgPatter.get("informesbuscar"), 15).click();
		sk.screen.type(Key.BACKSPACE);
		sk.screen.type(Key.BACKSPACE);
		sk.screen.type(Key.BACKSPACE);
		sk.screen.type(Key.BACKSPACE);
		sk.screen.type(Key.BACKSPACE);
		sk.screen.type(nombreSolicitud);
		sk.screen.type(Key.ENTER);
	}

	/**
	 * Metodo que da click en el campo Nombre de Nueva Solicitud
	 *
	 * @throws FindFailed
	 * @author edwin.ramirez
	 */
	public void clickNombre() throws FindFailed {
		sk.screen.wait(ImgPatter.get("nombreclick"), 15).click();
	}

	/**
	 * Metodo que da click en los 3 campos dentro del campo Nombre
	 * 
	 * @throws FindFailed
	 * @author edwin.ramirez
	 */
	public void clickPuntosNombre() throws FindFailed {
		sk.screen.wait(ImgPatter.get("puntosbusqueda"), 15).click();
	}

	/**
	 * Metodo que da click en el boton de compra
	 * 
	 * @throws FindFailed
	 * @author ricardo.palacios
	 */
	public void clickCompra() throws FindFailed {
		sk.screen.wait(ImgPatter.get("compra02"), 15).click();
	}

	/**
	 * Metodo que llena los campos de Fecha Inicio y Fecha Fin por parametro
	 * 
	 * @param fechainicio String con la fecha de inicio DD/MMM/AAAA
	 * @param fechafin    String con la fecha de fin DD/MMM/AAAA
	 * @throws FindFailed
	 * @author edwin.ramirez
	 */
	public void ingresarFechas(String fechainicio, String fechafin) throws FindFailed {

		sk.screen.type(Key.TAB);
		sk.screen.type(Key.TAB);
		sk.screen.type(Key.TAB);
		sk.screen.type(fechainicio);
		sk.screen.type(Key.TAB);
		sk.screen.wait(ImgPatter.get("aceptarcerrar"), 15).click();
	}

	/**
	 * Metodo que selecciona Juego Valores y llena el campo Nombre en Encontrar
	 * Juego de Valores
	 *
	 * @param nombre
	 * @throws FindFailed
	 * @author edwin.ramirez
	 */
	public void llenarJuegoValores(String nombre) throws FindFailed {
		ImgPatter = getPatternsImg(Fol);
		sk.screen.wait(ImgPatter.get("juegovalores2"), 15).click();
		sk.screen.wait(ImgPatter.get("nombrevalores"), 15).click();
		sk.screen.type(nombre);
		u.hardWait(2);
		sk.screen.wait(ImgPatter.get("encontrar2"), 15).click();

	}
	
	/**
	 * Emula presionar la tecla TAB un numero "n" de veces seguidas
	 *
	 * @param n El numero de veces que quieres que presione la tecla TAB
	 */
	public void nTAB(int n) {
		int x = 1;
		while (x <= n) {
			sk.screen.type(Key.TAB);
			x = x + 1;
		}
	}

	/**
	 * Metodo particular usado en MTCFT001_ValidarConfiguracionDeCuentasEnGL del
	 * paquete devolucionesOxxoPay Consulta los datos de tres Valores
	 * 
	 * @param valor1
	 * @param valor2
	 * @param valor3
	 * @throws FindFailed
	 * @author edwin.ramirez
	 */
	public void introducirValores(String valor1, String valor2, String valor3) throws FindFailed {
		sk.screen.wait(ImgPatter.get("valorazul"), 15).click();

		sk.screen.type(Key.F11);
		u.hardWait(2);
		sk.screen.type(valor1);
		sk.screen.keyDown(Key.CTRL + Key.F11);
		sk.screen.keyUp(Key.CTRL + Key.F11);
		u.hardWait(2);

		sk.screen.type(Key.F11);
		u.hardWait(2);
		sk.screen.type(valor2);
		sk.screen.keyDown(Key.CTRL + Key.F11);
		sk.screen.keyUp(Key.CTRL + Key.F11);
		u.hardWait(2);

		sk.screen.type(Key.F11);
		u.hardWait(2);
		sk.screen.type(valor3);
		sk.screen.keyDown(Key.CTRL + Key.F11);
		sk.screen.keyUp(Key.CTRL + Key.F11);
		u.hardWait(2);
	}

	/**
	 * Metodo que cierra el Applet en 2 pasos Da Alt+F4 y luego dentro del applet
	 * confirma
	 *
	 * @throws FindFailed
	 * @author edwin.ramirez
	 */
	public void cerrarApplet() throws FindFailed {
		sk.screen.keyDown(Key.ALT + Key.F4);
		sk.screen.keyUp(Key.ALT + Key.F4);
		sk.screen.keyDown(Key.ENTER);
		sk.screen.keyUp(Key.ENTER);
	}

	/**
	 * Metodo que inserta un Tipo y realiza la busqueda
	 * 
	 * @param tipo
	 * @param codigo
	 * @throws FindFailed
	 */
	public void tipoConsultaGL(String tipo, String codigo) throws FindFailed {
		ImgPatter = getPatternsImg(Fol);
		sk.screen.wait(ImgPatter.get("tipo"), 15).click();
		sk.screen.type(tipo);
		sk.screen.keyUp(Key.CTRL + Key.F11);
		sk.screen.keyDown(Key.CTRL + Key.F11);
		sk.screen.keyUp(Key.TAB);
		sk.screen.type(codigo);
		sk.screen.keyUp(Key.CTRL + Key.F11);
		sk.screen.keyDown(Key.CTRL + Key.F11);
	}

	/**
	 * Metodo que ingresa el numero Da varios TAB para moverse en la celda
	 * 
	 * @param ns Numero de solicitud
	 * @throws FindFailed
	 * @author ricardo.palacios@neoris.com
	 */
	public void EscribirNumeroSolicitudConEnter(String ns) throws FindFailed {

		u.hardWait(2);
		sk.screen.keyDown(Key.DELETE);
		sk.screen.keyUp(Key.DELETE);
		sk.screen.keyDown(Key.TAB);
		sk.screen.keyUp(Key.TAB);
		sk.screen.type(ns);
		sk.screen.keyDown(Key.ENTER);
		sk.screen.keyUp(Key.ENTER);
		sk.screen.keyDown(Key.ENTER);
		sk.screen.keyUp(Key.ENTER);
		sk.screen.keyDown(Key.ENTER);
		sk.screen.keyUp(Key.ENTER);
		sk.screen.keyDown(Key.ENTER);
		sk.screen.keyUp(Key.ENTER);
		sk.screen.keyDown(Key.TAB);
		sk.screen.keyUp(Key.TAB);
		sk.screen.keyDown(Key.TAB);
		sk.screen.keyUp(Key.TAB);
		sk.screen.keyDown(Key.TAB);
		sk.screen.keyUp(Key.TAB);
		sk.screen.keyDown(Key.TAB);
		sk.screen.keyUp(Key.TAB);
		sk.screen.keyDown(Key.TAB);
		sk.screen.keyUp(Key.TAB);
		sk.screen.keyDown(Key.TAB);
		sk.screen.keyUp(Key.TAB);
		sk.screen.keyDown(Key.TAB);
		sk.screen.keyUp(Key.TAB);
		sk.screen.keyDown(Key.TAB);
		sk.screen.keyUp(Key.TAB);
		sk.screen.keyDown(Key.TAB);
		sk.screen.keyUp(Key.TAB);
		sk.screen.keyDown(Key.TAB);
		sk.screen.keyUp(Key.TAB);
		sk.screen.keyDown(Key.TAB);
		sk.screen.keyUp(Key.TAB);
		sk.screen.keyDown(Key.TAB);
		sk.screen.keyUp(Key.TAB);

	}

	/**
	 * Metodo que ingresa el n�mero Da varios TAB para moverse en la celda
	 * 
	 * @author JessicaR@hexaware.com
	 * @param ns
	 * @throws FindFailed
	 */
	public void EscribirNumeroSolicitudConEnterOtro(String ns) throws FindFailed {

		u.hardWait(2);
		sk.screen.keyDown(Key.DELETE);
		sk.screen.keyUp(Key.DELETE);
		sk.screen.keyDown(Key.TAB);
		sk.screen.keyUp(Key.TAB);
		sk.screen.type(ns);
		sk.screen.keyDown(Key.ENTER);
		sk.screen.keyUp(Key.ENTER);
		sk.screen.keyDown(Key.TAB);
		sk.screen.keyUp(Key.TAB);
		sk.screen.keyDown(Key.TAB);
		sk.screen.keyUp(Key.TAB);
		sk.screen.keyDown(Key.TAB);
		sk.screen.keyUp(Key.TAB);
		sk.screen.keyDown(Key.TAB);
		sk.screen.keyUp(Key.TAB);
		sk.screen.keyDown(Key.TAB);
		sk.screen.keyUp(Key.TAB);
		sk.screen.keyDown(Key.TAB);
		sk.screen.keyUp(Key.TAB);
		sk.screen.keyDown(Key.TAB);
		sk.screen.keyUp(Key.TAB);
		sk.screen.keyDown(Key.TAB);
		sk.screen.keyUp(Key.TAB);
		sk.screen.keyDown(Key.TAB);
		sk.screen.keyUp(Key.TAB);
		sk.screen.keyDown(Key.TAB);
		sk.screen.keyUp(Key.TAB);
		sk.screen.keyDown(Key.TAB);
		sk.screen.keyUp(Key.TAB);
		sk.screen.keyDown(Key.TAB);
		sk.screen.keyUp(Key.TAB);
		sk.screen.keyDown(Key.TAB);
		sk.screen.keyUp(Key.TAB);
		sk.screen.keyDown(Key.TAB);
		sk.screen.keyUp(Key.TAB);

	}

	public void introducirValor(String valor) throws FindFailed {
		sk.screen.wait(ImgPatter.get("valorazul00"), 15).click();

		sk.screen.type(Key.F11);
		u.hardWait(2);
		sk.screen.type(valor);
		sk.screen.keyDown(Key.CTRL + Key.F11);
		sk.screen.keyUp(Key.CTRL + Key.F11);
		u.hardWait(2);
	}

	/**
	 * Metodo para comparar la informacion que se muestra en la celda
	 * 
	 * @param formaPago   Forma de pago
	 * @param fechaInicio Fecha de inicio
	 * @param fechaFin    Fecha fin
	 * @author ricardo.palacios
	 */
	public void parametros(String formaPago, String fechaInicio, String fechaFin) {
		sk.screen.type(formaPago);
		sk.screen.type(Key.TAB);
		sk.screen.type(fechaInicio);

		sk.screen.type(Key.TAB);
		sk.screen.type(fechaFin);
		sk.screen.type(Key.ENTER);
	}

	/**
	 * Metodo para escribir fechas en celdas
	 * 
	 * @param fechaInicio
	 * @param fechaFin
	 * @author ricardo.palacios
	 */
	public void parametrosFactura(String fechaInicio, String fechaFin) {

		sk.screen.type(Key.TAB);
		sk.screen.type(fechaInicio);
		sk.screen.type(fechaFin);
		sk.screen.type(Key.ENTER);
	}

	/**
	 * Metodo para ingresar un nombre que contiene un acento. Recibe el String en
	 * dos y en medio escribe "o" con acento
	 * 
	 * @author edwin.ramirez
	 * @param mitad1
	 * @param mitad2
	 * @throws FindFailed
	 */
	public void ingresarNombreAcentos(String mitad1, String mitad2) throws FindFailed {
		sk.screen.type(mitad1);
		sk.screen.keyDown(Key.ALT + Key.NUM1 + Key.NUM6 + Key.NUM2);
		sk.screen.keyUp(Key.ALT + Key.NUM1 + Key.NUM6 + Key.NUM2);
		sk.screen.type(mitad2);
		sk.screen.type(Key.TAB);

	}

	public String compararCelda(String strToFind)
			throws FindFailed, HeadlessException, UnsupportedFlavorException, IOException {
		sk.screen.keyDown(Key.CTRL); // Presiona CTRL+C para copiar todo el texto y suelta las teclas
		sk.screen.keyDown("c");
		sk.screen.keyUp(Key.CTRL);
		sk.screen.keyUp("c");
		// El texto que copiamos
		String textFound = (String) Toolkit.getDefaultToolkit().getSystemClipboard().getData(DataFlavor.stringFlavor);
		System.out.println(textFound); // Imprime el texto que copiamos
		String find = strToFind; // El String a buscar dentro de lo que copiamos
		String findResult = ""; // Lo que regresaermos

		int i = textFound.indexOf(find);
		if (i > 0) {
			findResult = textFound.substring(i, i + find.length());
			System.out.println(textFound.substring(i, i + find.length()));
		} else {
			System.out.println("El string No fue correcto encontrado fue: " + findResult);
		}
		sk.screen.keyDown(Key.CTRL);
		sk.screen.keyDown("a");
		sk.screen.keyUp(Key.CTRL);
		sk.screen.keyUp("a");
		return findResult; // Si lo encuentra lo regresa

	}

	/**
	 * Metodo para buscar responsabilidad activa
	 * 
	 * @throws FindFailed
	 * @author ricardo.palacios
	 */
	public void validarCambio() throws FindFailed {

		sk.screen.keyDown(Key.TAB);
		sk.screen.keyUp(Key.TAB);
		sk.screen.keyDown(Key.TAB);
		sk.screen.keyUp(Key.TAB);
		sk.screen.keyDown(Key.TAB);
		sk.screen.keyUp(Key.TAB);
		sk.screen.keyDown(Key.TAB);
		sk.screen.keyUp(Key.TAB);

	}

	/**
	 * Metodo que copia el contenido del clipboard y le concatena una variable
	 * 
	 * @param variable
	 * @return
	 * @throws HeadlessException
	 * @throws UnsupportedFlavorException
	 * @throws IOException
	 * @author ricardo.palacios
	 */
	public String textoCopiadoConvariable(String variable)
			throws HeadlessException, UnsupportedFlavorException, IOException {
		sk.screen.keyDown(Key.CTRL); // Presiona CTRL+C para copiar el texto y suelta las teclas
		sk.screen.keyDown("c");
		sk.screen.keyUp("c");
		sk.screen.keyUp(Key.CTRL);
		// El texto que copiamos
		String textFound = (String) Toolkit.getDefaultToolkit().getSystemClipboard().getData(DataFlavor.stringFlavor)
				+ variable;
		System.out.println("Texto copiado " + textFound); // Imprime el texto que copiamos
		logger.info("Texto copiado " + textFound);
		return textFound;
	}

	/**
	 * Metodo que ingresa un Nombre en la ventana Tabulador de Cobro de Servicios, y
	 * ejecuta una busqueda con ese nombre.
	 * 
	 * @param nombre String con el nombre a buscar
	 * @throws FindFailed
	 */
	public void ingresarNombreTabuladorCobroServicios(String nombre) throws FindFailed {
		u.hardWait(10);
		sk.screen.keyDown(Key.F11);
		sk.screen.keyUp(Key.F11);
		sk.screen.type(nombre);
		sk.screen.keyDown(Key.CTRL + Key.F11);
		sk.screen.keyUp(Key.CTRL + Key.F11);
	}

	/**
	 * Metodo que ingresa un Tipo Item en la ventana Tabulador de Cobro de
	 * Servicios, y ejecuta una busqueda con ese Item.
	 * 
	 * @param tipoItem String con el Item a buscar
	 * @throws FindFailed
	 */
	public void ingresarTipoItem(String tipoItem) throws FindFailed {
		u.hardWait(10);
		ImgPatter = getPatternsImg(Fol);
		sk.screen.wait(ImgPatter.get("tiposervicio"), 15).click();
		sk.screen.wait(ImgPatter.get("tiposervicio"), 15).click();
		sk.screen.keyDown(Key.F11);
		sk.screen.keyUp(Key.F11);
		sk.screen.type(tipoItem);
		sk.screen.keyDown(Key.CTRL + Key.F11);
		sk.screen.keyUp(Key.CTRL + Key.F11);
	}

	public void cerrarDobleAltF4() throws FindFailed {
		u.hardWait(3);
		sk.screen.keyDown(Key.ALT + Key.F4);
		sk.screen.keyUp(Key.ALT + Key.F4);

		sk.screen.keyDown(Key.ALT + Key.F4);
		sk.screen.keyUp(Key.ALT + Key.F4);
		sk.screen.wait(ImgPatter.get("aceptarcerrar"), 15).click();
	}

	public void enter() {
		sk.screen.keyDown(Key.ENTER);
		sk.screen.keyUp(Key.ENTER);
	}

	/**
	 * Da click en el estado Normal de un solicitud
	 * 
	 * @throws FindFailed
	 */
	public void estadoNormal() throws FindFailed {
		sk.screen.wait(ImgPatter.get("estadonormal"), 15).click();
	}

	/**
	 * Da click en la lampara de busqueda de los applets
	 * 
	 * @throws FindFailed
	 * @author ricardo.palacios
	 */
	public void lamparaBusqueda() throws FindFailed {
		sk.screen.wait(ImgPatter.get("lamparabusqueda"), 15).click();
		u.hardWait(2);
	}
	
	/**
	 * Da click en caja de texto de CR los applets
	 * 
	 * @throws FindFailed
	 * @author ricardo.palacios
	 */
	public void clickCR() throws FindFailed {
		sk.screen.wait(ImgPatter.get("cr"), 15).click();
		u.hardWait(2);
	}
	
	/**
	 * Metodo que llena los campos de la ventana Parametros con 7 campos.
	 * 
	 * @author edwin.ramirez
	 * @param compania
	 * @param plaza
	 * @param origen
	 * @param cuentaCorriente
	 * @param tipoSalida
	 * @param periodo
	 * @throws FindFailed
	 */
	public void parametros7campos(String compania, String plaza, String origen, String cuentaCorriente,
			String tipoSalida, String periodo) throws FindFailed {
		sk.screen.wait(ImgPatter.get("compania"), 15).click();
		sk.screen.type(compania);

		sk.screen.keyDown(Key.BACKSPACE);
		sk.screen.keyUp(Key.BACKSPACE);
		sk.screen.type(plaza);

		sk.screen.type(origen);

		sk.screen.keyDown(Key.TAB);
		sk.screen.keyUp(Key.TAB);
		sk.screen.type(cuentaCorriente);

		sk.screen.keyDown(Key.TAB);
		sk.screen.keyUp(Key.TAB);
		sk.screen.keyDown(Key.TAB);
		sk.screen.keyUp(Key.TAB);
		sk.screen.type(tipoSalida);

		sk.screen.type(periodo);

		sk.screen.wait(ImgPatter.get("aceptararchivo"), 15).click();
	}

	/**
	 * Metodo que escribe en el campo Entidad de la ventana Parametros, da click en
	 * aceptar.
	 * 
	 * @param entidad El nombre de la entidad a escribir.
	 * @throws FindFailed
	 * @author edwin.ramirez
	 */
	public void parametrosEntidad(String entidad) throws FindFailed {
		u.hardWait(3);
		sk.screen.type(entidad);
		sk.screen.wait(ImgPatter.get("aceptararchivo"), 15).click();
	}

	/**
	 * Método que selecciona el texto de una celda
	 * 
	 * 
	 * @return textFound, si lo encuentra lo regresa
	 * @throws FindFailed
	 * @throws HeadlessException
	 * @throws UnsupportedFlavorException
	 * @throws IOException
	 * @author ricardo.palacios@neoris.com
	 */
	public String controlCopiar() throws FindFailed, HeadlessException, UnsupportedFlavorException, IOException {

		String textFound = "";
		// Toolkit.getDefaultToolkit().getSystemClipboard().getData(DataFlavor.stringFlavor).equals("");
		sk.screen.keyDown(Key.CTRL); // Presiona CTRL+C para copiar todo el texto y suelta las teclas
		sk.screen.keyDown("c");
		sk.screen.keyUp(Key.CTRL);
		sk.screen.keyUp("c");
		// El texto que copiamos
		textFound = (String) Toolkit.getDefaultToolkit().getSystemClipboard().getData(DataFlavor.stringFlavor);
		System.out.println(textFound); // Imprime el texto que copiamos
		return textFound; // Si lo encuentra lo regresa

	}

	public void insertarText(String usuario, String contrasena) throws FindFailed {

		sk.screen.type(usuario);
		sk.screen.keyDown(Key.TAB);
		sk.screen.keyUp(Key.TAB);
		sk.screen.type(contrasena);
		sk.screen.keyDown(Key.ENTER);
		sk.screen.keyUp(Key.ENTER);
	}

	/**
	 * Metodo que cierra las ventanas
	 * 
	 * @throws FindFailed
	 * @author ricardo.palacios
	 */
	public void cerrarV() throws FindFailed {

		ImgPatter = getPatternsImg(Fol);
		sk.screen.wait(ImgPatter.get("cerrarventana"), 15).click();

	}

	/**
	 * Metodo cuya funcion es cerra el applet usando la combinacion de Alt+F4 y
	 * despues Enter para aceptar confirmacion
	 * 
	 * @throws FindFailed
	 * @author edwin.ramirez
	 */
	public void altF4Aceptar() throws FindFailed {
		sk.screen.keyDown(Key.ALT + Key.F4);
		sk.screen.keyUp(Key.ALT + Key.F4);

		sk.screen.keyDown(Key.ENTER);
		sk.screen.keyUp(Key.ENTER);
	}

	/**
	 * Metodo para ingresar texto en un campo sin dar Enter o TAB
	 * 
	 * @param campo
	 * @throws FindFailed
	 * @author edwin.ramirez
	 */
	public void soloIngresarCampo(String campo) throws FindFailed {
		sk.screen.type(campo);
	}

	/**
	 * Metodo cuya funcion es ejecutar TAB de teclado la cantidad de veces dadas por
	 * parametro
	 * 
	 * @param num Cantidad de veces a ejecutar un TAB
	 * @author edwin.ramirez
	 */
	public void presionarTabNVeces(int num) {
		int n = num;
		while (n > 0) {
			sk.screen.type(Key.TAB);
			u.hardWait(2);
			n--;
			System.out.println("Quedan: " + n + " por hacer.");
		}
	}

	/**
	 * Metodo que ejecuta un Alt+F4
	 */
	public void altF4() {
		sk.screen.keyDown(Key.ALT + Key.F4);
		sk.screen.keyUp(Key.ALT + Key.F4);
	}
	
	public String getText(String strToFind)
			throws FindFailed, HeadlessException, UnsupportedFlavorException, IOException {
		sk.screen.click();
		sk.screen.keyDown(Key.CTRL); // Presiona CTRL+A para marcar todo el texto y suelta las teclas
		sk.screen.keyDown("a");
		sk.screen.keyUp(Key.CTRL);
		sk.screen.keyUp("a");

		sk.screen.keyDown(Key.CTRL); // Presiona CTRL+C para copiar todo el texto y suelta las teclas
		sk.screen.keyDown("c");
		sk.screen.keyUp(Key.CTRL);
		sk.screen.keyUp("c");
		// El texto que copiamos
		String textFound = (String) Toolkit.getDefaultToolkit().getSystemClipboard().getData(DataFlavor.stringFlavor);
		System.out.println(textFound); // Imprime el texto que copiamos
		String find = strToFind; // El String a buscar dentro de lo que copiamos
		String findResult = ""; // Lo que regresaermos

		int i = textFound.indexOf(find);
		if (i > 0) {
			findResult = textFound.substring(i, i + find.length());
			System.out.println(textFound.substring(i, i + find.length()));
		} else {
			findResult = "string not found";
			System.out.println("string not found");
		}

		sk.screen.mouseDown(Button.LEFT);
		sk.screen.mouseUp(Button.LEFT);
		return findResult; // Si lo encuentra lo regresa

	}
}