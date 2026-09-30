package EBS.om;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.NoSuchElementException;

import org.apache.poi.EncryptedDocumentException;
import org.openqa.selenium.winium.WiniumDriver;
import org.sikuli.script.FindFailed;
import org.sikuli.script.Match;
import org.sikuli.script.Pattern;
import org.sikuli.script.Screen;
import org.sikuli.script.ScreenImage;

import modelo.TestCase;
import utils.ExcelUtil;
import utils.SikuliUtil;
import utils.selenium.SeleniumUtil;

public class CorresponsaliasBAZ_EBS {

	WiniumDriver driver;
	TestCase testCase;

	SikuliUtil sk;
	String pathImg;
	File FolderPath;
	SeleniumUtil u2;

	public CorresponsaliasBAZ_EBS(SeleniumUtil u2, TestCase testCase) {

		this.testCase = testCase;
		this.sk = new SikuliUtil();
		this.pathImg = sk.PatternPath("EBS");
		this.FolderPath = new File(pathImg);
		this.u2 = u2;
	}

	/**
	 * Metodo donde se valida que una imagen cualquiera exista
	 * 
	 * @param patternName nombre de patron generalizado
	 * @author monica.soto
	 * @throws Exception
	 * @date 16/12/2024
	 */
	public void validatePattern(String patternName) throws Exception {

		try {
			Pattern pattern = new Pattern(FolderPath + "\\" + patternName + ".png").similar((float) .70);
			u2.hardWait(5);
			if (sk.screen.exists(pattern, 20) != null) {
				testCase.addTextEvidenceCurrentStep("Si existe.");
				testCase.addScreenShotCurrentStep(patternName);
			} else {
				cerrarApplet();
				throw new NoSuchElementException("No se detectó " + patternName);
			}
		} catch (Exception e) {

			testCase.addCodeEvidenceCurrentStep("Error: " + e.getMessage());
			throw new Exception("Error: " + e.getMessage());
		}
	}

	/**
	 * Metodo donde se valida que una imagen cualquiera exista y se presiona
	 * 
	 * @param patternName nombre de patron generalizado
	 * @author monica.soto
	 * @throws Exception
	 * @date 16/12/2024
	 */
	public void validatePatternAndClick(String patternName) throws Exception {

		try {
			u2.hardWait(5);

			Pattern pattern = new Pattern(FolderPath + "\\" + patternName + ".png").similar((float) .70);
			if (sk.screen.exists(pattern, 20) != null) {
				testCase.addTextEvidenceCurrentStep("Se presiona el botón");
				testCase.addScreenShotCurrentStep(patternName);
				sk.screen.click(pattern);
				u2.hardWait(5);

			} else {
				cerrarApplet();
				throw new NoSuchElementException("No se detectó " + patternName);
			}
		} catch (Exception e) {

			// testCase.addCodeEvidenceCurrentStep("Error: " + e.getMessage());
			// throw new Exception("Error: " + e.getMessage());
			testCase.addScreenShotCurrentStep("Output");
		}
	}

	/**
	 * Metodo donde se valida que una imagen cualquiera exista y se presiona, sin
	 * dar evidencia
	 * 
	 * @param patternName nombre de patron generalizado
	 * @author monica.soto
	 * @throws Exception
	 * @date 16/12/2024
	 */
	public void clickPatternNoEvidence(String patternName) throws Exception {

		try {
			Pattern pattern = new Pattern(FolderPath + "\\" + patternName + ".png").similar((float) .70);
			if (sk.screen.exists(pattern, 20) != null) {
				sk.screen.click(pattern);
				u2.hardWait(5);

			} else {
				cerrarApplet();
				throw new NoSuchElementException("No se detectó " + patternName);
			}
			// throw new NoSuchElementException("");
		} catch (Exception e) {

			testCase.addCodeEvidenceCurrentStep("Error: " + e.getMessage());
			throw new Exception("Error: " + e.getMessage());
		}
	}

	/**
	 * Metodo donde se valida que una imagen cualquiera exista y se escribe un texto
	 * 
	 * @param patternName nombre de patron generalizado texto que se va a escribir
	 * @author monica.soto
	 * @throws Exception
	 * @date 16/12/2024
	 */
	public void validatePatternAndWrite(String patternName, String texto) throws Exception {

		try {
			Pattern pattern = new Pattern(FolderPath + "\\" + patternName + ".png").similar((float) .70);
			if (sk.screen.exists(pattern, 20) != null) {
				testCase.addTextEvidenceCurrentStep("Se escribe en el input");
				testCase.addScreenShotCurrentStep(patternName);
				sk.screen.click(pattern);
				sk.screen.type(texto);
				u2.hardWait(5);

			} else {
				cerrarApplet();
				throw new NoSuchElementException("No se detectó " + patternName);
			}
		} catch (Exception e) {

			testCase.addCodeEvidenceCurrentStep("Error: " + e.getMessage());
			throw new Exception("Error: " + e.getMessage());
		}
	}

	/**
	 * Metodo donde se escribe un texto sobre una imagen sin validarse
	 * 
	 * @param texto a escribir
	 * @author monica.soto
	 * @throws Exception
	 * @date 16/12/2024
	 */
	public void typeOnPattern(String texto) throws Exception {

		try {
			sk.screen.type(texto);
			u2.hardWait(5);
		} catch (Exception e) {

			testCase.addCodeEvidenceCurrentStep("Error: " + e.getMessage());
			throw new Exception("Error: " + e.getMessage());
		}
	}

	/**
	 * Metodo para manejar la ventana de advertencia que aparece al abrir un applet
	 *
	 * @author monica.soto
	 * @throws Exception
	 * @date 16/12/2024
	 */
	public void runApplet() throws Exception {
		System.out.println("Run Applet");
		u2.hardWait(20);//SE AGREGO PARA VALIDAR EL TIEMPO DE ESPERA DE ABRIR APPLET DE JAVA
		Pattern aceptoRiesgosYDeseoEjecutar = new Pattern(FolderPath + "\\aceptoRiesgosYDeseoEjecutar.png")
				.similar((float) .70);
		Pattern ejecutarApplet = new Pattern(FolderPath + "\\ejecutarApplet.png").similar((float) .70);

		try {
			// se da click primero a la advertencia de aceptar el riesgo
			if (sk.screen.exists(aceptoRiesgosYDeseoEjecutar, 20) != null) {
				sk.screen.click(aceptoRiesgosYDeseoEjecutar);
			}
			// se da click a Ejecutar
			if (sk.screen.exists(ejecutarApplet, 20) != null) {
				sk.screen.click(ejecutarApplet);
			}
		} catch (FindFailed e) {
			testCase.addCodeEvidenceCurrentStep("Error: " + e.getMessage());
			throw new Exception("Error: " + e.getMessage());
		}
		u2.hardWait(6);
		appletMaximize();
		testCase.addScreenShotCurrentStep("Applicativo frmservlet");

	}

	/**
	 * Maximiza la ventana enviando la combinacion de teclas WIN + UP
	 *
	 * @author monica.soto
	 * @throws Exception
	 * @date 16/12/2024
	 */
	public void appletMaximize() throws AWTException {

		Robot robot = new Robot();
		robot.keyPress(KeyEvent.VK_WINDOWS);
		robot.keyPress(KeyEvent.VK_UP);
		robot.keyRelease(KeyEvent.VK_WINDOWS);
		robot.keyRelease(KeyEvent.VK_UP);
	}

	/**
	 * Se presiona enter
	 *
	 * @author monica.soto
	 * @throws Exception
	 * @date 16/12/2024
	 */
	public void clickEnter() throws AWTException {

		Robot robot = new Robot();
		robot.keyPress(KeyEvent.VK_ENTER);
		robot.keyRelease(KeyEvent.VK_ENTER);
	}

	/**
	 * Se ingresan los parámetros requeridos para esta ejecución
	 *
	 * @param fechaInicio fecha de inicio de la transaccion fechaFin fecha fin de la
	 *                    transaccion numServicio numero de servicio plaza plaza de
	 *                    la transaccion (no obligatorio) tipoDistribucion tipo de
	 *                    distribucion
	 * @author monica.soto
	 * @throws Exception
	 * @date 16/12/2024
	 */
	public void ingresarParametrosDistribucionTransaccionesServicios(String fechaInicio, String fechaFin,
			String numServicio, String plaza, String tipoDistribucion) throws Exception {

		try {
			Robot robot = new Robot();

			u2.hardWait(5);
			System.out.println("se entra a la funcion de parametros");

			// primero se escribe la fecha de inicio
			sk.screen.type(fechaInicio);

			// luego se escribe la fecha fin
			sk.screen.type(fechaFin);

			// luego se escribe el numero de servicio
			sk.screen.type(numServicio);

			robot.keyPress(KeyEvent.VK_TAB);
			robot.keyRelease(KeyEvent.VK_TAB);

			if (plaza != "") {
				// luego se escribe el nombre de plaza
				sk.screen.type(plaza);
			} else {
				robot.keyPress(KeyEvent.VK_TAB);
				robot.keyRelease(KeyEvent.VK_TAB);
			}

			// luego se escribe el tipo de distribución
			sk.screen.type(tipoDistribucion);

			u2.hardWait(5);
		} catch (Exception e) {

			testCase.addCodeEvidenceCurrentStep("Error: " + e.getMessage());
			throw new Exception("Error: " + e.getMessage());
		}
	}

	/**
	 * Se ingresan los parámetros requeridos para esta ejecución
	 *
	 * @param tipoReporte tipo de reporte que se usará para el proceso proveedor
	 *                    tipo de proveedor(no obligatorio) numServicio (no
	 *                    obligatorio) fechaInicioTrans fecha inicio de transaccion
	 *                    (no obligatorio) fechaFinTrans fecha fin de la transaccion
	 *                    (no obligatorio) grupo (no obligatorio)
	 * @author monica.soto
	 * @throws Exception
	 * @date 16/12/2024
	 */
	public void ingresarParametrosProcesoFSNCorresponsalia(String tipoReporte, String proveedor, String numServicio,
			String fechaInicioTrans, String fechaFinTrans, String grupo) throws Exception {
		try {
			Robot robot = new Robot();

			u2.hardWait(5);
			System.out.println("se entra a la funcion de parametros");

			// primero se escribe el tipo de reporte
			sk.screen.type(tipoReporte);

			robot.keyPress(KeyEvent.VK_TAB);
			robot.keyRelease(KeyEvent.VK_TAB);

			if (proveedor != "") {
				// primero se escribe el tipo de proveedor
				clickPatternNoEvidence("puntosProveedor");
				clickPatternNoEvidence("buscarPorcentaje");
				sk.screen.type(proveedor);

			}
			robot.keyPress(KeyEvent.VK_TAB);
			robot.keyRelease(KeyEvent.VK_TAB);

			if (numServicio != "") {
				// primero se escribe el tipo de numServicio
				sk.screen.type(numServicio);
			}

			robot.keyPress(KeyEvent.VK_TAB);
			robot.keyRelease(KeyEvent.VK_TAB);

			if (fechaInicioTrans != "") {
				// despues se agrega la fecha
				sk.screen.type(fechaInicioTrans);
			} else {
				robot.keyPress(KeyEvent.VK_TAB);
				robot.keyRelease(KeyEvent.VK_TAB);
			}

			if (fechaFinTrans != "") {
				// despues se agrega la fecha
				sk.screen.type(fechaFinTrans);
			}
			robot.keyPress(KeyEvent.VK_TAB);
			robot.keyRelease(KeyEvent.VK_TAB);

			if (grupo != "") {
				// despues se agrega el grupo
				sk.screen.type(grupo);
			}
			u2.hardWait(5);
		} catch (Exception e) {

			testCase.addCodeEvidenceCurrentStep("Error: " + e.getMessage());
			throw new Exception("Error: " + e.getMessage());
		}
	}

	/**
	 * Metodo donde se entra a la opcion de solicitudes
	 *
	 * @author monica.soto
	 * @throws Exception
	 * @date 16/12/2024
	 */
	public void entrarSolicitudes() throws Exception {

		try {
			Pattern patternVer = new Pattern(FolderPath + "\\ver.png").similar((float) .50);
			Pattern patternSolicitudes = new Pattern(FolderPath + "\\solicitudes.png").similar((float) .50);

			if (sk.screen.exists(patternVer, 20) != null) {
				sk.screen.click(patternVer);
				sk.screen.click(patternSolicitudes);
				testCase.addScreenShotCurrentStep("solicitudes");

			} else {
				cerrarApplet();
				throw new NoSuchElementException("No se detectó");
			}
		} catch (Exception e) {

			testCase.addCodeEvidenceCurrentStep("Error: " + e.getMessage());
			throw new Exception("Error: " + e.getMessage());
		}

	}

	/**
	 * Se cierra el aplicativo
	 *
	 * @author monica.soto
	 * @throws Exception
	 * @date 16/12/2024
	 */
	public void cerrarApplet() throws Exception {

		try {
			Robot robot = new Robot();
			Pattern cerrarapplet = new Pattern(FolderPath + "\\cerrarapplet2.png").similar((float) .50);
			Pattern appletIcon = new Pattern(FolderPath + "\\appletIcon.png").similar((float) .50);
			Pattern appletLogo = new Pattern(FolderPath + "\\OracleLogo.png").similar((float) .50);

			if (sk.screen.exists(appletLogo, 20) == null) {
				sk.screen.click(appletIcon);
			}
			sk.screen.click(cerrarapplet);
			robot.keyPress(KeyEvent.VK_ENTER);
			robot.keyRelease(KeyEvent.VK_ENTER);
		} catch (Exception e) {

			testCase.addCodeEvidenceCurrentStep("Error: " + e.getMessage());
			throw new Exception("Error: " + e.getMessage());
		}
	}

	/**
	 * Metodo que presiona un boton siempre y cuando exista algo
	 *
	 * @param patternClick patron que sufrirá el doble click validatePattern patron
	 *                     que será validado si existe o no
	 * @author monica.soto
	 * @throws Exception
	 * @date 16/12/2024
	 */
	public void clickWhileExist(String patternClick, String validatePattern) throws Exception {

		try {
			Boolean boolPattern = boolValidatePattern(validatePattern);

			while (boolPattern) {
				doubleClickPattern(patternClick, 5);
				boolPattern = boolValidatePattern(validatePattern);
			}
		} catch (Exception e) {

			testCase.addCodeEvidenceCurrentStep("Error: " + e.getMessage());
			throw new Exception("Error: " + e.getMessage());
		}
	}

	/**
	 * Metodo para darle doble click a un botón
	 *
	 * @param target nombre de patron generalizado timeOut cantidad de timeout
	 * @author monica.soto
	 * @throws Exception
	 * @date 16/12/2024
	 */
	public void doubleClickPattern(String target, Integer timeOut) throws Exception {

		Pattern pattern = new Pattern(FolderPath + "\\" + target + ".png").similar((float) .50);

		try {
			sk.screen.doubleClick(pattern);
		} catch (FindFailed findFailed) {
			cerrarApplet();
			testCase.addCodeEvidenceCurrentStep("No se detectó " + target);
			throw new Exception("No se detectó " + target);
		}
	}

	/**
	 * Metodo que valida algo retornando un bool
	 *
	 * @param target nombre de patron generalizado
	 * @author monica.soto
	 * @return boolean
	 * @throws Exception
	 * @date 16/12/2024
	 */
	public Boolean boolValidatePattern(String target) throws Exception {

		try {
			Pattern pattern = new Pattern(FolderPath + "\\" + target + ".png").similar((float) .50);

			if (sk.screen.exists(pattern, 20) != null) {
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {

			testCase.addCodeEvidenceCurrentStep("Error: " + e.getMessage());
			throw new Exception("Error: " + e.getMessage());
		}
	}

	/**
	 * Se ingresan los parámetros requeridos para esta ejecución
	 *
	 * @param compania tipo de compañia plaza tipo de plaza (no obligatorio) origen
	 *                 (no obligatorio) cuentaCorriente valor de la cuenta corriente
	 *                 tipoReporte tipo de reporte (no obligatorio) tipoSalida tipo
	 *                 de salida periodo de la transaccion(mes y año)
	 * @author monica.soto
	 * @throws Exception
	 * @date 16/12/2024
	 */
	public void ingresarParametrosXXFCReporteConciliacionFEMCOCartas(String compania, String plaza, String origen,
			String cuentaCorriente, String tipoReporte, String tipoSalida, String periodo) throws Exception {

		try {
			Robot robot = new Robot();

			u2.hardWait(5);
			System.out.println("se entra a la funcion de parametros");

			// primero se escribe el nombre de compañia
			sk.screen.type(compania);

			u2.hardWait(5);

			// robot.keyPress(KeyEvent.VK_TAB);
			// robot.keyRelease(KeyEvent.VK_TAB);

			if (plaza != "") {
				// luego se escribe el nombre de plaza

				validatePatternAndClick("puntos");
				validatePatternAndWrite("buscarPorcentaje", plaza);
				clickEnter();

			} else {
				robot.keyPress(KeyEvent.VK_TAB);
				robot.keyRelease(KeyEvent.VK_TAB);
			}

			if (origen != "") {
				sk.screen.type(origen);
			}

			robot.keyPress(KeyEvent.VK_TAB);
			robot.keyRelease(KeyEvent.VK_TAB);

			sk.screen.type(cuentaCorriente);

			robot.keyPress(KeyEvent.VK_TAB);
			robot.keyRelease(KeyEvent.VK_TAB);

			if (tipoReporte != "") {
				sk.screen.type(tipoReporte);
			}

			robot.keyPress(KeyEvent.VK_TAB);
			robot.keyRelease(KeyEvent.VK_TAB);

			sk.screen.type(tipoSalida);

			sk.screen.type(periodo);

			u2.hardWait(5);

		} catch (Exception e) {

			testCase.addCodeEvidenceCurrentStep("Error: " + e.getMessage());
			throw new Exception("Error: " + e.getMessage());
		}
	}

	/**
	 * Método para cerrar el Excel
	 *
	 * @author monica.soto
	 * @throws Exception
	 * @date 16/12/2024
	 */
	public void cerrarExcel() throws AWTException {

		Robot robot = new Robot();
		robot.keyPress(KeyEvent.VK_ALT);
		robot.keyPress(KeyEvent.VK_F4);
		robot.keyRelease(KeyEvent.VK_ALT);
		robot.keyRelease(KeyEvent.VK_F4);
	}

	/**
	 * Metodo donde se valida que una imagen cualquiera se presiona, sin necesidad
	 * de validar
	 *
	 * @param patternName nombre de patron generalizado
	 * @author monica.soto
	 * @throws Exception
	 * @date 16/12/2024
	 */
	public void clickPatternNotValidated(String patternName) throws Exception {

		try {
			u2.hardWait(5);

			Pattern pattern = new Pattern(FolderPath + "\\" + patternName + ".png").similar((float) .60);
			if (sk.screen.exists(pattern, 20) != null) {
				sk.screen.click(pattern);
				u2.hardWait(5);
			} else
				System.out.println("No existe la imagen" + patternName);
		} catch (Exception e) {

			testCase.addCodeEvidenceCurrentStep("Error: " + e.getMessage());
			throw new Exception("Error: " + e.getMessage());
		}
	}

	/**
	 * Se ingresan los parámetros requeridos para esta ejecución
	 *
	 * @param pagarVencimiento si/no tipoVencimiento si/no (no obligatorio)
	 *                         fechaLimiteDesde inicio de la fecha limite
	 *                         fechaLimiteHasta fin de la fecha limite fechaPago
	 *                         fecha de pago incluirFacturas si/no (no obligatorio)
	 *                         grupoPago (no obligatorio) opcResumen si/no (no
	 *                         obligatorio) metodoPago (no obligatorio) divisa (no
	 *                         obligatorio) prioridadAlta (no obligatorio)
	 *                         prioridadBaja (no obligatorio) totalProveedor (no
	 *                         obligatorio) proveedor (no obligatorio) desdeImporte
	 *                         (no obligatorio) hastaImporte (no obligatorio)
	 *                         grupoEjecucion (no obligatorio) filtrarPorGrupos (no
	 *                         obligatorio)
	 * @author monica.soto
	 * @throws Exception
	 * @date 16/12/2024
	 */
	public void ingresarParametroInformeRequerimientosDeEfectivoDetallado(String pagarVencimiento,
			String tipoVencimiento, String fechaLimiteDesde, String fechaLimiteHasta, String fechaPago,
			String incluirFacturas, String grupoPago, String opcResumen, String metodoPago, String divisa,
			String prioridadAlta, String prioridadBaja, String totalProveedor, String proveedor, String desdeImporte,
			String hastaImporte, String grupoEjecucion, String filtrarPorGrupos) throws Exception {

		try {
			Robot robot = new Robot();
			u2.hardWait(5);
			System.out.println("se entra a la funcion de parametros");

			// primero se escribe el tipo de reporte
			sk.screen.type(pagarVencimiento);

			robot.keyPress(KeyEvent.VK_TAB);
			robot.keyRelease(KeyEvent.VK_TAB);

			if (tipoVencimiento != "") {
				clickPatternNotValidated(tipoVencimiento);
				clickPatternNotValidated("aceptarperi");
			}

			sk.screen.type(fechaLimiteDesde);

			sk.screen.type(fechaLimiteHasta);

			sk.screen.type(fechaPago);

			sk.screen.type(incluirFacturas);

			robot.keyPress(KeyEvent.VK_TAB);
			robot.keyRelease(KeyEvent.VK_TAB);

			if (grupoPago != "") {
				sk.screen.type(grupoPago);
			}

			robot.keyPress(KeyEvent.VK_TAB);
			robot.keyRelease(KeyEvent.VK_TAB);

			sk.screen.type(opcResumen);

			robot.keyPress(KeyEvent.VK_TAB);
			robot.keyRelease(KeyEvent.VK_TAB);

			if (metodoPago != "") {
				sk.screen.type(metodoPago);
			}

			robot.keyPress(KeyEvent.VK_TAB);
			robot.keyRelease(KeyEvent.VK_TAB);

			if (divisa != "") {
				sk.screen.type(divisa);
			}

			robot.keyPress(KeyEvent.VK_TAB);
			robot.keyRelease(KeyEvent.VK_TAB);

			if (prioridadAlta != "") {
				sk.screen.type(prioridadAlta);
			}

			robot.keyPress(KeyEvent.VK_TAB);
			robot.keyRelease(KeyEvent.VK_TAB);

			if (prioridadBaja != "") {
				sk.screen.type(prioridadBaja);
			}

			robot.keyPress(KeyEvent.VK_TAB);
			robot.keyRelease(KeyEvent.VK_TAB);

			if (totalProveedor != "") {
				sk.screen.type(totalProveedor);
			}

			robot.keyPress(KeyEvent.VK_TAB);
			robot.keyRelease(KeyEvent.VK_TAB);

			if (proveedor != "") {
				sk.screen.type(proveedor);
			}

			robot.keyPress(KeyEvent.VK_TAB);
			robot.keyRelease(KeyEvent.VK_TAB);

			if (desdeImporte != "") {
				sk.screen.type(desdeImporte);
			}

			robot.keyPress(KeyEvent.VK_TAB);
			robot.keyRelease(KeyEvent.VK_TAB);

			if (hastaImporte != "") {
				sk.screen.type(hastaImporte);
			}

			robot.keyPress(KeyEvent.VK_TAB);
			robot.keyRelease(KeyEvent.VK_TAB);

			if (grupoEjecucion != "") {
				sk.screen.type(grupoEjecucion);
			}

			robot.keyPress(KeyEvent.VK_TAB);
			robot.keyRelease(KeyEvent.VK_TAB);

			if (filtrarPorGrupos != "") {
				sk.screen.type(filtrarPorGrupos);
			}
		} catch (Exception e) {

			testCase.addCodeEvidenceCurrentStep("Error: " + e.getMessage());
			throw new Exception("Error: " + e.getMessage());
		}
	}

	/**
	 * Metodo donde se valida que una imagen cualquiera exista y se presiona, sin
	 * dar evidencia, si no existe la primera imagen valida la segunda
	 *
	 * @param patternName nombre de patron generalizado patternSecondName nombre de
	 *                    patron generalizado
	 * @author monica.soto
	 * @throws Exception
	 * @date 25/04/2025
	 */
	public void checkTwoPatternNoEvidenceClick(String patternName, String patternSecondName) throws Exception {

		try {
			Pattern pattern = new Pattern(FolderPath + "\\" + patternName + ".png").similar((float) .70);
			Pattern patternSecond = new Pattern(FolderPath + "\\" + patternSecondName + ".png").similar((float) .70);

			if (sk.screen.exists(pattern, 20) != null) {
				sk.screen.click(pattern);
				u2.hardWait(5);

			} else if (sk.screen.exists(patternSecond, 20) != null) {
				sk.screen.click(patternSecond);
				u2.hardWait(5);
			}

		} catch (Exception e) {

			testCase.addScreenShotCurrentStep("Archivo XXFC Reporte Conciliacion FEMCO Cartas");
			throw new Exception("Error: " + e.getMessage());
		}
	}

	/**
	 * Metodo para obtener el numero de Folio del ID de la solicitud para obtener el
	 * output del reporte: XXFC___Reporte_de_Conciliacion
	 *
	 * @param Return Numeros: Retorna el valor del Id de la solicitud, para su
	 *               posterior busqueda en el Output y obtener el reporte
	 * 
	 * @author monica.soto
	 * @throws Exception
	 * @date 25/04/2025
	 */
	public String ExtractNumbersFromText() {

		// Captura la región de la pantalla donde se encuentra el texto
		Screen screen = new Screen();
		String numeros = "";

		screen.x = 725;
		screen.y = 320;
		screen.w = 90;
		screen.h = 70;

		screen.highlight(2);

		ScreenImage file = screen.capture(725, 320, 90, 70);
		System.out.println("Saved screen as " + file);

		// Busca la imagen en la pantalla
		try {
			Match match = screen.find(file);

			// Si se encuentra la imagen, captura el texto
			if (match != null) {
				String textoCompleto = match.text();

				// Utiliza una expresión regular para extraer los números del texto
				numeros = textoCompleto.replaceAll("\\D+", "");

				// Imprime los números encontrados
				System.out.println("Números encontrados: " + numeros);
			} else {
				System.out.println("Imagen no encontrada");
			}
		} catch (FindFailed e) {
			e.printStackTrace();
		}
		return numeros;

	}

	/**
	 * Metodo para Validar el contenido del Archivo Excel
	 *
	 * @param
	 * 
	 * @author antonio.martinez
	 * @throws IOException
	 * @throws EncryptedDocumentException
	 * @throws Exception
	 * @date 25/04/2025
	 */
	public void ValidarContenidoExcel() throws EncryptedDocumentException, IOException {

		String excelPath = "C://Users/GTIM/Downloads/XXFC___Reporte_de_Conciliacion_300425.xls";
		File excelFile = new File(excelPath);

		if (!excelFile.exists()) {
			System.out.println("El archivo no se creo: " + excelPath);
		} else {
			System.out.println("El archivo se creó correctamente en: " + excelPath);
		}

		ExcelUtil excelUtil = new ExcelUtil(excelPath, false);
		excelUtil.open(); // abre el documento Excel
		ArrayList<String> hojas = excelUtil.getSheetNames();

		boolean hojaExistente = hojas.contains("Detalle");

		if (hojaExistente) {
			System.out.println("La pestaña se encuentra dentro del archivo: ");

		} else {
			System.out.println("No se encontro la pestaña dentro del archivo: ");

		}

		String valorCelda = excelUtil.getCell(0, 11, 1);
		System.out.println("el valor de la Celda es : " + valorCelda);
		testCase.addTextEvidenceCurrentStep("el valor de la Celda Origen es : " + valorCelda);

	}

}
