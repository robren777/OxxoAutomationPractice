package EBS.util.FolioPagoMVP2;

import java.io.File;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Properties;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.JSONObject;
import org.openqa.selenium.By;
import org.sikuli.script.FindFailed;
import org.sikuli.script.Key;
import org.sikuli.script.Region;
import org.sikuli.script.Screen;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpException;

import modelo.BaseExecution;
import modelo.TestCase;
import utils.SikuliUtil;
import utils.controlm.ControlM;
import utils.controlm.pageObject.Control_mInicio;
import utils.selenium.SeleniumUtil;

public class EBSChrome {

	TestCase testCase;
	SeleniumUtil u;
	HashMap<String, String> data;
	int dato;
	int numeroEvidencia = 0;

	private By inputPass = By.id("login-user-password");
	private By txtLoginControlM = By.xpath("//*[@id=\"login-user-name\"]");
	protected static final Logger logger = LoggerFactory.getLogger(BaseExecution.class);

	public SikuliUtil sk = new SikuliUtil();
	HashMap<String, org.sikuli.script.Pattern> ImgPattern;

	public EBSChrome(SeleniumUtil u, TestCase testCase, String ImgPattern) {
		super();
		this.testCase = testCase;
		this.u = u;
		this.ImgPattern = getPatternsImg(ImgPattern);
	}

	public EBSChrome(SeleniumUtil u, TestCase testCase) {
		super();
		this.testCase = testCase;
		this.u = u;
	}

	/**************************************************************************************************
	 * LOCATORS
	 **************************************************************************************************/

	By statusJob = By.xpath("//header[contains(text(), 'Status')]/ancestor::main[1]//span[2]");
	By startTime = By.xpath("//header[contains(text(), 'Start Time ')]/ancestor::main[1]//span[2]");
	By outputJob = By.xpath("//span[contains(text(), 'Output')]");
	By outputText = By.xpath("//div[contains(@class, 'cdk-virtual-scroll-content-wrapper')]");
	By scroll = By.xpath("//cdk-virtual-scroll-viewport");

	/*************************************************************************************************/

	/**
	 * Cierra la ventana enviando la combinacion de teclas 'Alt' + 'F4'
	 *
	 * @author JesusH@hexaware.com
	 */
	public void closeWindow() {
		sk.screen.keyDown(Key.ALT);
		sk.screen.keyDown(Key.F4);
		sk.screen.keyUp(Key.F4);
		sk.screen.keyUp(Key.ALT);
	}

	public static void executeLatestJnlp() throws Exception {
        // Obtener la carpeta de descargas del usuario
        String userHome = System.getProperty("user.home");
        File downloadDir = new File(userHome, "Downloads");

        if (!downloadDir.exists() || !downloadDir.isDirectory()) {
            throw new Exception("La carpeta de descargas no existe o no es válida: " + downloadDir.getAbsolutePath());
        }

        // Obtener el archivo más reciente con extensión .jnlp
        File latestJnlpFile = Arrays.stream(downloadDir.listFiles((dir, name) -> name.endsWith(".jnlp")))
                .max(Comparator.comparingLong(File::lastModified))
                .orElseThrow(() -> new Exception("No se encontró ningún archivo .jnlp en la carpeta de descargas"));

        // Ejecutar el archivo .jnlp
        System.out.println("Ejecutando el archivo: " + latestJnlpFile.getAbsolutePath());
        Process process = new ProcessBuilder("javaws", latestJnlpFile.getAbsolutePath()).start();

        // Esperar a que el proceso termine (opcional)
        process.waitFor();
    }
	
	/**
	 * Espera el mensaje de RUN para iniciar el aplicativode EBS
	 * 
	 * @author 1000068915 - Jorge Hernandez
	 * @return regresa un true o false en funcion de que el inicio del aplicativo se
	 *         realice correctamente
	 * @date 26/Noviembre/2024
	 * 
	 */
	public boolean startEBS() throws FindFailed {
		try {
			waitImage(50, "btn_Keep");
			sk.screen.wait(ImgPattern.get("btn_Keep"), 1).click();
			u.hardWait(1);
			sk.screen.click();

			waitImage(50, "btn_DownloadUnverifiedFile2");
			sk.screen.wait(ImgPattern.get("btn_DownloadUnverifiedFile2"), 1).click();
			u.hardWait(10);

			// Sustitución: Ejecutar el archivo .jnlp más reciente
	        try {
	            executeLatestJnlp();
	        } catch (Exception e) {
	            throw new AssertionError("Error al ejecutar el archivo .jnlp: " + e.getMessage());
	        }

			waitImage(100, "checkboxStart2");
			System.out.println("Se encontro el mensaje para iniciar el aplicativo de EBS");
			sk.screen.wait(ImgPattern.get("checkboxStart2"), 1).click();
			u.hardWait(2);
			u.hardWait(1);
			sk.screen.type(Key.ENTER);

			try {
				waitImage(100, "archivo");
				System.out.println("Cargo correctamente EBS");
				u.hardWait(10);
				return true;
			} catch (Exception e) {
				return false;
			}
		} catch (Exception ex) {
			System.out.println(" -> " + ex);
			throw new AssertionError("Fallo al cargar el aplicativo de EBS");
		}
	}
	
	/**
	 * Espera el mensaje de RUN para iniciar el aplicativode EBS
	 * 
	 * @author 1000068915 - Jorge Hernandez
	 * @return regresa un true o false en funcion de que el inicio del aplicativo se
	 *         realice correctamente
	 * @date 26/Noviembre/2024
	 * 
	 */
	public boolean startEBS3() throws FindFailed {
		try {
			waitImage(50, "btn_Keep");
			sk.screen.wait(ImgPattern.get("btn_Keep"), 1).click();
			u.hardWait(1);
			sk.screen.click();

			waitImage(50, "btn_DownloadUnverifiedFile2");
			sk.screen.wait(ImgPattern.get("btn_DownloadUnverifiedFile2"), 1).click();
			u.hardWait(10);

			// Sustitución: Ejecutar el archivo .jnlp más reciente
	        try {
	            executeLatestJnlp();
	        } catch (Exception e) {
	            throw new AssertionError("Error al ejecutar el archivo .jnlp: " + e.getMessage());
	        }

//			waitImage(100, "checkboxStart2");
//			System.out.println("Se encontro el mensaje para iniciar el aplicativo de EBS");
//			sk.screen.wait(ImgPattern.get("checkboxStart2"), 1).click();
//			u.hardWait(2);
//			u.hardWait(1);
//			sk.screen.type(Key.ENTER);

			try {
				waitImage(100, "archivo");
				System.out.println("Cargo correctamente EBS");
				u.hardWait(10);
				return true;
			} catch (Exception e) {
				return false;
			}
		} catch (Exception ex) {
			System.out.println(" -> " + ex);
			throw new AssertionError("Fallo al cargar el aplicativo de EBS");
		}
	}
	
	public boolean startEBS2() throws FindFailed {
		try {
			waitImage(50, "btn_Keep");
			sk.screen.wait(ImgPattern.get("btn_Keep"), 1).click();
			u.hardWait(1);
			sk.screen.click();

			waitImage(50, "btn_DownloadUnverifiedFile2");
			sk.screen.wait(ImgPattern.get("btn_DownloadUnverifiedFile2"), 1).click();
			u.hardWait(10);

			// Sustitución: Ejecutar el archivo .jnlp más reciente
	        try {
	            executeLatestJnlp();
	        } catch (Exception e) {
	            throw new AssertionError("Error al ejecutar el archivo .jnlp: " + e.getMessage());
	        }

			waitImage(100, "checkboxStart2");
			System.out.println("Se encontro el mensaje para iniciar el aplicativo de EBS");
			sk.screen.wait(ImgPattern.get("checkboxStart2"), 1).click();
			u.hardWait(2);
			u.hardWait(1);
			sk.screen.type(Key.ENTER);

			try {
				System.out.println("Cargo correctamente EBS");
				u.hardWait(1);
				return true;
			} catch (Exception e) {
				return false;
			}
		} catch (Exception ex) {
			System.out.println(" -> " + ex);
			throw new AssertionError("Fallo al cargar el aplicativo de EBS");
		}
	}
	
	public void descargarExcel() throws FindFailed {
		try {
			
			waitImage(50, "btn_Download");
			sk.screen.wait(ImgPattern.get("btn_Download"), 1).click();
			u.hardWait(1);
			
			waitImage(50, "keep2");
			sk.screen.wait(ImgPattern.get("keep2"), 1).click();
			u.hardWait(1);
		} catch (Exception ex) {
			
			System.out.println(" -> " + ex);
			throw new AssertionError("Fallo al cargar el aplicativo de EBS");
			
		}
	}

	/**
	 * Busca la imagen del boton Submit a New Request y da clic en ella
	 * 
	 * @author 1000068915 - Jorge Hernandez
	 * @return regresa un true o false en funcion de que la accion se realize
	 *         correctamente
	 * @date 26/Noviembre/2024
	 */
	public boolean clicSubmitNewRequest() {
		try {
			waitImage(100, "btn_SubmitNewRequest");
			sk.screen.wait(ImgPattern.get("btn_SubmitNewRequest"), 1).highlight("red");
			screenshot();
			sk.screen.click();
			Screen.highlightAllOff();

			return waitImage(10, "lbl_Name");
		} catch (Exception ex) {
			System.out.println(" -> " + ex);
			screenshot();
			return false;
		}
	}

	/**
	 * Llena el apartado de Name en la pantalla para ejecutar concurrentes de EBS
	 * 
	 * @author 1000068915 - Jorge Hernandez
	 * @param concurrente toma el valor del nombre del concurrente a ejecutar
	 * @return regresa un true o false en funcion de que se llene correctamente el
	 *         formulario del concurrente
	 * @date 26/Noviembre/2024
	 */
	public boolean fillNameConcurrente(String concurrente) {
		try {
			waitImage(10, "lbl_Name");
			sk.screen.wait(ImgPattern.get("lbl_Name"), 1).highlight("red").right(30).click();
			screenshot();
			sk.screen.type(concurrente);
			u.hardWait(1);
			sk.screen.type(Key.TAB);
			u.hardWait(1);
			Screen.highlightAllOff();

			return waitImage(10, "btn_OK");
		} catch (Exception ex) {
			System.out.println(" -> " + ex);
			screenshot();
			return false;
		}
	}

	/**
	 * Da clic en el boton OK de la pantalla de Parameters
	 * 
	 * @author 1000068915 - Jorge Hernandez
	 * @return regresa un true o false en funcion de que se de clic en el boton OK
	 *         de la pantalla
	 * @date 26/Noviembre/2024
	 */
	public boolean clicBtnOk() {
		try {
			waitImage(10, "btn_OK");
			sk.screen.wait(ImgPattern.get("btn_OK"), 1).highlight("red");
			screenshot();
			sk.screen.click();
			u.hardWait(1);
			Screen.highlightAllOff();

			return waitDisImage(10, "parametersVoid");
		} catch (Exception ex) {
			System.out.println(" -> " + ex);
			screenshot();
			return false;
		}
	}

	/**
	 * Busca el boton Submit y da clic
	 * 
	 * @author 1000068915 - Jorge Hernandez
	 * @return regresa un true o false en funcion de que funcione correctamente el
	 *         boton Sumbit
	 * @date 26/Noviembre/2024
	 */
	public boolean clicBtnSubmit() {
		try {
			waitImage(10, "btn_Submit");
			sk.screen.wait(ImgPattern.get("btn_Submit"), 1).highlight("red");
			screenshot();
			sk.screen.click();
			Screen.highlightAllOff();

			return waitImage(10, "btn_DesicionNo");
		} catch (Exception ex) {
			System.out.println(" -> " + ex);
			screenshot();
			return false;
		}
	}

	/**
	 * Emula presionar la tecla 'Alt' + alguna tecla que necesitemos (solo una)
	 *
	 * @param tecla Tecla a presionar
	 * @author JesusH@hexaware.com
	 */
	public void keyAlt(String tecla) {
		sk.screen.keyDown(Key.ALT);
		sk.screen.type(tecla);
		sk.screen.keyUp(Key.ALT);
	}

	/**
	 * Obtiene el Id de la ventana emergente
	 * 
	 * @author 1000068915 - Jorge Hernandez
	 * @return regresa el valor del ID del concurrente
	 * @date 26/Noviembre/2024
	 */
	public String getRequestId() {
		try {

			waitImage(10, "popUp");
			u.hardWait(2);
			Region popUpRegion = sk.screen.wait(ImgPattern.get("popUp"), 1);

			int extraWidth = 28; // Ancho adicional para incluir el texto de la ventana
			int extraHeight = 40; // Altura adicional para incluir el texto de la ventana
			Region textRegion = popUpRegion.grow(extraWidth, extraHeight).highlight("red");
			u.hardWait(2);
			String popupText = textRegion.text();

			// Mostrar el texto capturado
			System.out.println("Texto de la ventana emergente: " + popupText);

			String regex = "\\(Request ID = (\\d+)\\)";
			Pattern pattern = Pattern.compile(regex);
			Matcher matcher = pattern.matcher(popupText);

			String extractedId = "";
			if (matcher.find()) {
				extractedId = matcher.group(1); // Captura el texto del primer grupo de captura
				System.out.println("ID extraido: " + extractedId);
			} else {
				System.out.println("No se encontro un ID en el formato esperado.");
				return null;
			}

			return extractedId;
		} catch (Exception ex) {
			System.out.println(" -> " + ex);
			screenshot();
			return null;
		}
	}

	/**
	 * Busca el boton No en la pantalla Desicion y da clic
	 * 
	 * @author 1000068915 - Jorge Hernandez
	 * @return regresa un true o false en funcion de que funcione correctamente el
	 *         boton NO en la pantalla Desicion
	 * @date 26/Noviembre/2024
	 */

	public boolean clicBtnDesicionNo() {
		try {
			waitImage(10, "btn_DesicionNo");
			sk.screen.wait(ImgPattern.get("btn_DesicionNo"), 1).highlight("red");
			screenshot();
			sk.screen.click();
			Screen.highlightAllOff();

			return waitDisImage(10, "btn_DesicionNo");
		} catch (Exception ex) {
			System.out.println(" -> " + ex);
			screenshot();
			return false;
		}
	}

	/**
	 * Llena la pantalla de Specific REquest para buscar la ejecucion de un
	 * concurrente en especifico
	 * 
	 * @author 1000068915 - Jorge Hernandez
	 * @param id toma el valor del concurrente a buscar.
	 * @return regresa un true o false en funcion de que llene correctamente el
	 *         formulario para buscar el concurrente ejecutado
	 * @date 26/Noviembre/2024
	 */
	public boolean fillFindSpecificRequest(String id) {
		try {
			waitImage(20, "radio_specificRequest");
			sk.screen.wait(ImgPattern.get("radio_specificRequest"), 1).highlight("red").click();
			u.hardWait(2);

			waitImage(20, "lbl_RequestID");
			sk.screen.wait(ImgPattern.get("lbl_RequestID"), 1).highlight("red").right(30).click();
			u.hardWait(1);
			sk.screen.type(id);
			u.hardWait(1);

			waitImage(20, "btn_Find");
			sk.screen.wait(ImgPattern.get("btn_Find"), 1).highlight("red");
			screenshot();

			Screen.highlightAllOff();

			sk.screen.wait(ImgPattern.get("btn_Find"), 1).click();

			waitImage(30, "RequestSelected");

			for (int i = 0; i < 100000; i++) {
				try {
					sk.screen.wait(ImgPattern.get("statusConcurrente"), 10).highlight("red");
					Screen.highlightAllOff();
					return true;
				} catch (Exception e) {
					waitImage(10, "btn_RefreshData");
					sk.screen.wait(ImgPattern.get("btn_RefreshData"), 1).click();
					u.hardWait(1);
					sk.screen.wait(ImgPattern.get("RequestSelected"), 1).click();
					u.hardWait(5);
				}
			}
			testCase.addBoldTextEvidenceCurrentStep("No se muestra el concurrente con status Completed");
			return false;
		} catch (Exception ex) {
			System.out.println(" -> " + ex);
			screenshot();
			return false;
		}
	}

	/**
	 * Selecciona el concurrente ejecutado y da clic en el boton view Log
	 * 
	 * @author 1000068915 - Jorge Hernandez
	 * @return regresa un true o false en funcion de que se muestre el log en el
	 *         navegador del concurrente ejecutado
	 * @date 26/Noviembre/2024
	 */
	public boolean clicBtnViewLog() {
		try {
			waitImage(50, "RequestSelected");
			sk.screen.wait(ImgPattern.get("RequestSelected"), 1).right(30).click();
			u.hardWait(1);

			waitImage(10, "btn_ViewLog");
			sk.screen.wait(ImgPattern.get("btn_ViewLog"), 1).highlight("red");
			screenshot();

			Screen.highlightAllOff();

			sk.screen.wait(ImgPattern.get("btn_ViewLog"), 1).click();

			waitImage(5000, "logGoogleChrome");
			screenshot();
			return waitImage(15, "logGoogleChrome");
		} catch (Exception ex) {
			System.out.println(" -> " + ex);
			screenshot();
			return false;
		}
	}

	/**
	 * Busca el item creado en el cuadro de servicios mediante la barra de scroll
	 * 
	 * @author 1000068915 - Jorge Hernandez
	 * @param service toma el valor del servicio creado en el aplicativo de EBS
	 * @return regresa un true o false en funcion de que se encuentre el servicio
	 *         creado
	 * @date 26/Noviembre/2024
	 */
	public boolean searchServicio(String service) {

		By searchService = By.xpath("//span[@title = 'Servicio' and text() = '" + service + "']");

		try {
			u.waitWebElement(searchService, 5, 1);
			u.highLight(searchService);

			u.scrollToElement(searchService);

			try {
				sk.screen.wait(ImgPattern.get("serviceSelected"), 2).hover();
				screenshot();
				return true;
			} catch (Exception f) {
				System.out.println("No semuestra el servicio recien creado con solo buscar el grupo TRY 2");
				for (int i = 0; i <= 20; i++) {
					try {
						waitImage(5, "btn_ScrollDown");
						sk.screen.wait(ImgPattern.get("btn_ScrollDown"), 1).above(7).doubleClick();

						waitImage(5, "serviceSelected");
						sk.screen.wait(ImgPattern.get("serviceSelected"), 1).hover();

						break;

					} catch (Exception e) {
						System.out.println("Buscando el servicio resaltado TRY 3");
					}
				}

				screenshot();
				return true;
			}

		} catch (Exception e) {
			System.out.println("No semuestra el servicio recien creado con solo buscar el grupo");
			try {
				boolean s = false;
				do {
					try {
						waitImage(5, "btn_ScrollDown");
						try {
							sk.screen.wait(ImgPattern.get("btn_ScrollDown"), 1).above(7).doubleClick();
						} catch (Exception i) {
							break;
						}
						u.hardWait(1);
						u.waitWebElement(searchService, 2, 1);
						u.highLight(searchService);

						s = true;

					} catch (Exception ex) {
						System.out.println("Buscando el servicio TRY 4");

					}
				} while (s == false);
				screenshot();
				return true;
			} catch (Exception i) {
				System.out.println("No se encontro el servicio buscado");
				return false;
			}
		}

	}

	/**
	 * Hace un remarcado y da clic en el boton Aplicar para completar el proceso de
	 * actualizacion de un Item
	 *
	 * @author 1000068915 - Jorge Hernandez
	 * @return regresa un true o false en funcion de que se muestre el mensaje de
	 *         confirmacion
	 * @date 26/Noviembre/2024
	 */
	public boolean clicBtnAplicarUpdate() {
		By btnAplicarUpdate = By.xpath("//button[@id = 'btnApply']");
		try {
			u.waitWebElement(btnAplicarUpdate, 20, 1);
			u.highLight(btnAplicarUpdate);
			screenshot();
			u.click(btnAplicarUpdate);

			waitImage(10, "lbl_ItemActualizado");
			sk.screen.wait(ImgPattern.get("lbl_ItemActualizado"), 1).highlight("red");

			screenshot();

			Screen.highlightAllOff();
			return true;
		} catch (Exception e) {
			System.out.println("No se muestra el mensaje  de confirmacion " + e);
			return false;
		}
	}

	/**
	 * Hace un remarcado y da clic en el icono de basura para eliminar items En la
	 * pantalla de items de grupos
	 *
	 * @author 1000068915 - Jorge Hernandez
	 * @param service toma el valor del servicio que se va a eliminar
	 * @return regresa un true o false en funcion de que se muestre el mensaje donde
	 *         confirma la eliminacion del item
	 * @date 26/Noviembre/2024
	 */
	public boolean clicBtnDelete(String service) {
		By btnDeleteItem = By.xpath("//span[@title = 'Servicio' and text() = '" + service
				+ "']/ancestor::tr[1]//a[contains(@id, 'DeleteItem')]");

		try {
			u.waitWebElement(btnDeleteItem, 20, 1);
			u.highLight(btnDeleteItem);
			screenshot();
			u.click(btnDeleteItem);

			waitImage(10, "lbl_ItemEliminado");
			sk.screen.wait(ImgPattern.get("lbl_ItemEliminado"), 1).highlight("red");

			screenshot();

			Screen.highlightAllOff();

			return true;
		} catch (Exception e) {
			System.out.println("No se muestra el mensaje preguntando si queremos eliminar el item " + e);
			return false;
		}
	}

	/**
	 * Hace un remarcado y da clic en el boton OK de la pantalla de confirmacion de
	 * Item Actualizados
	 *
	 * @author 1000068915 - Jorge Hernandez
	 * @param group toma el valor del grupo donde se crea el item
	 * @return regresa un true o false en funcion de que se muestre la lista de
	 *         items del grupo
	 * @date 26/Noviembre/2024
	 */
	public boolean clicBtnOkUpdate(String group) {
		By viewItems = By.xpath("//a[contains(@onclick, '" + group + "')]");
		try {
			waitImage(10, "btn_OkUpdate");
			sk.screen.wait(ImgPattern.get("btn_OkUpdate"), 1).highlight("red");
			screenshot();
			sk.screen.wait(ImgPattern.get("btn_OkUpdate"), 1).doubleClick();

			Screen.highlightAllOff();
			u.waitWebElement(viewItems, 20, 1);

			return true;
		} catch (Exception e) {
			System.out.println("No se muestra la lista de items del grupo " + e);
			return false;
		}
	}

	/**
	 * Hace un remarcado y da clic en el boton OK de la pantalla de confirmacion de
	 * Item Eliminados
	 *
	 * @author 1000068915 - Jorge Hernandez
	 * @return regresa un true o false en funcion de que se muestre el mensaje de
	 *         confirmacion
	 * @date 26/Noviembre/2024
	 */
	public boolean clicYesDelete() {
		By txtRecordDeleted = By.xpath("//li[contains(text(), 'Record deleted Successfully')]");
		try {
			waitImage(10, "btn_YesDeleteItem");
			sk.screen.wait(ImgPattern.get("btn_YesDeleteItem"), 1).highlight("red");
			screenshot();
			sk.screen.wait(ImgPattern.get("btn_YesDeleteItem"), 1).doubleClick();

			Screen.highlightAllOff();
			u.waitWebElement(txtRecordDeleted, 20, 1);
			u.highLight(txtRecordDeleted);
			screenshot();

			System.out.println("******************************************************");
			System.out.println("CASO TERMINADO");
			System.out.println("******************************************************");
			return true;
		} catch (Exception e) {
			System.out.println("No se muestra el mensaje de confirmacion " + e);
			screenshot();
			return false;
		}
	}

	/**
	 * Obtiene la fecha del dia actual con un formato dd-MMM-yyy Por ejemplo:
	 * 14-NOV-2024
	 * 
	 * @author 1000068915 - Jorge Hernandez
	 * @return Regresa el valor de la fecha actual en el formato dd-MMM-yyyy
	 * @date 26/Noviembre/2024
	 */
	public String getActualDate() {

		LocalDate currentDate = LocalDate.now();

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MMM-yyyy");

		String date = currentDate.format(formatter);
		System.out.println(date);

		return date;
	}

	/**
	 * Se dirije al sitio de CTRL - M e inicia sesion con el usuario asignado en el
	 * data PRovider
	 * 
	 * @author 1000068915 - Jorge Hernandez
	 * @param user toma el valor del usuario para CTRL-M del dataprovider password
	 *             toma el valor del password para CTRL-M del dataprovider url toma
	 *             el valor del enlace para iniciar CTRL-M del dataprovider
	 * @return regresa un true o un false en funcion a que se inicie correctamente
	 *         CTRL-M
	 * @date 26/Noviembre/2024
	 */
	public boolean getControlM(String user, String password, String url) {
		try {
			Control_mInicio controlMInicio = new Control_mInicio(u, user, password);
			u.get(url);

			u.waitWebElement(inputPass, 200, 3);
			u.waitWebElement(txtLoginControlM, 120, 3);
			u.clear(txtLoginControlM);
			controlMInicio.logOn();

			screenshot();
			return true;

		} catch (Exception ex) {
			System.out.println(" -> " + ex);
			testCase.addBoldTextEvidenceCurrentStep("No se pudo ingresar a control-M");
			return false;
		}
	}

	/**
	 * Ejecuta el JOB con y regresa un boolean en base al resultado de la ejecucion
	 * del JOB
	 * 
	 * @author 1000068915 - Jorge Hernandez
	 * @param nombreJob toma el valor del JSON con la informacion del JOB del
	 *                  Dataprovider
	 * @return Regresa un true o false en funcion de si el status final del JOB es
	 *         "Ended OK"
	 * @date 26/Noviembre/2024
	 */
	public boolean ejecutarJob(String nombreJob) throws FindFailed, InterruptedException {
		testCase.addTextEvidenceCurrentStep("Ejecuta Job: " + nombreJob);
		JSONObject obj = new JSONObject(nombreJob);

		By btnMsg = By.xpath("//button[contains(text(), 'OK')]");

		for (int i = 0; i <= 15; i++) {
			try {
				u.highLight(btnMsg);
				u.hardWait(1);
				u.click(btnMsg);
				u.hardWait(5);

				System.out.println("Aun se muestra mensaje de renovacion de password");
				u.hardWait(1);
			} catch (Exception e) {

				System.out.println("Ya no se muestra mensaje de renovacion de password");
				break;
			}
		}

		ControlM control = new ControlM(u, testCase, obj);
		try {
			control.searchJob();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		try {
			control.accionSinOutput();
		} catch (Exception e) {
			System.out.println("**************************************************************");
			System.out.println(e.getMessage());
			System.out.println("**************************************************************");
		}

		String resultadoEjecucion = getStartTimeJobStatus();
		System.out.println("Resultado de la ejecucion: " + resultadoEjecucion);
		String output = control.getOutput();
		System.out.println("Output: " + output);
		testCase.addTextEvidenceCurrentStep("Output: " + output);
		// control.closeViewpoint();

		if (resultadoEjecucion.equals("Ended OK")) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Obtiene el status del JOB
	 * 
	 * @author 1000068915 - Jorge Hernandez
	 * @param starTimeI toma el valor de la fecha y hora inicial del JOB
	 * @return Regresa el status actual del JOB
	 * @date 26/Noviembre/2024
	 */
	public String getCurrentJobStatus(String startTimeI) {
		u.hardWait(2);
		String status = null;
		String startTimeS = null;

		u.waitWebElement(statusJob, 10, 1);
		u.highLight(statusJob);

		u.waitWebElement(startTime, 10, 1);
		u.highLight(startTime);

		for (int i = 0; i < 1000000; i++) {
			try {
				u.waitWebElement(statusJob, 10, 1);
				status = u.getText(statusJob);
			} catch (Exception e) {
				System.out.println("No encuentra status");
			}

			try {
				u.waitWebElement(startTime, 10, 1);
				startTimeS = u.getText(startTime);
			} catch (Exception e) {
			}

			if (startTimeS.equals(startTimeI)) {
				if (i % 30 == 0) {
					System.out.println("Esperando el cambio de Start Time");
				}
				u.hardWait(5);
			} else {
				if (status.contains("Executing")) {
					if (i % 30 == 0) {
						System.out.println("Esperando a que termine ejecucion");
					}
					u.click(statusJob);
					u.hardWait(1);
				} else {
					System.out.println("******************************************");
					System.out.println("ESTATUS ACTUAL: " + status);
					System.out.println("******************************************");
					break;
				}
			}
		}

		return status;
	}

	/**
	 * Obtiene el status del JOB
	 * 
	 * @author 1000068915 - Jorge Hernandez
	 * @return Regresa el status actual del JOB
	 * @date 26/Noviembre/2024
	 */
	public boolean getCurrentJobStatus() {
		u.hardWait(2);
		String status = null;

		u.waitWebElement(statusJob, 10, 1);
		u.highLight(statusJob);

		boolean ejecucion = false;

		for (int i = 0; i < 1000000; i++) {
			status = u.getText(statusJob);

			if (status.contains("Executing")) {
				if (i % 30 == 0) {
					System.out.println("Esperando a que termine ejecucion");
					System.out.println("STATUS: " + status);
					ejecucion = true;
				}
				u.click(statusJob);
				u.hardWait(1);
			} else {
				System.out.println("******************************************");
				System.out.println("ESTATUS ACTUAL DESPUES DEL RELEASE: " + status);
				System.out.println("******************************************");

				break;
			}
		}

		return ejecucion;
	}

	/**
	 * Espera a un cambio de status despues de ejecutar un JOB
	 * 
	 * @author 1000068915 - Jorge Hernandez
	 * @statusJobI toma el valor inicial del JOB antes de la ejecucion
	 * @date 10/Enero/2025
	 */
	public void waitChangeStatusJOB(String statusJobI) {
		String status = null;

		u.waitWebElement(statusJob, 10, 1);
		u.highLight(statusJob);

		for (int i = 0; i < 10; i++) {
			status = u.getText(statusJob);
			if (status.equals(statusJobI)) {
				if (i % 3 == 0) {
					System.out.println("SE MUESTRA EL MIMSO STATUS DESPUES DEL RELEASE");
					System.out.println("ESPERANDO CAMBIO");
				}
				u.hardWait(1);
			} else {
				break;
			}
		}

	}

	/**
	 * Obtiene la fecha y hora actual del JOB para compararla en un futuro
	 * 
	 * @author 1000068915 - Jorge Hernandez
	 * @return Regresa la fecha de inicio y la hora actual del JOB
	 * @date 26/Noviembre/2024
	 */
	public String getStartTimeJobStatus() {
		u.hardWait(10);
		String status = null;
		String startTimeS = null;

		u.waitWebElement(statusJob, 10, 1);
		status = u.getText(statusJob);

		u.waitWebElement(startTime, 10, 1);
		startTimeS = u.getText(startTime);

		System.out.println("******************************************");
		System.out.println("ESTATUS DESPUES DEL RELEASE: " + status);
		System.out.println("START TIME DESPUES DEL RELEASE: " + startTimeS);
		System.out.println("******************************************");

		return startTimeS;
	}

	/**
	 * Obtiene el status del JOB
	 * 
	 * @author 1000068915 - Jorge Hernandez
	 * @return Regresa el valor del Output del JOB
	 * @date 26/Noviembre/2024
	 */
	public String getOutput() {
		u.hardWait(5);
		String output = null;

		u.waitWebElement(outputJob, 20, 1);
		u.highLight(outputJob);
		u.click(outputJob);

		u.waitWebElement(outputText, 20, 1);
		u.highLight(outputText);

		output = u.getText(outputText);
		return output;
	}

	/**
	 * Da clic en la opcion Release del JOB a Ejecutar del JOB
	 * 
	 * @author 1000068915 - Jorge Hernandez
	 * @param Se requiere el nombre del JOB tal cual se haya puesto en la columna
	 *           del data provider (Nombred de la columna)
	 * @return Regresa la fecha y tiempo de inicio que obtiene el JOB al darle en la
	 *         opcion Release. Regresa el texto "Ejecutado" si el JOB se ejecuta
	 *         solo con dar en la opcion Release
	 * @date 26/Noviembre/2024
	 */
	public String releaseJOB(String nombreJob) throws FindFailed, InterruptedException {
		testCase.addTextEvidenceCurrentStep("Ejecuta Job: " + nombreJob);
		JSONObject obj = new JSONObject(nombreJob);

		By btnMsg = By.xpath("//button[contains(text(), 'OK')]");

		for (int i = 0; i <= 8; i++) {
			try {
				u.highLight(btnMsg);
				u.hardWait(1);
				u.click(btnMsg);
				u.hardWait(5);

				System.out.println("Aun se muestra mensaje de renovacion de password");
				u.hardWait(1);
			} catch (Exception e) {

				System.out.println("Ya no se muestra mensaje de renovacion de password");
				break;
			}
		}

		ControlM control = new ControlM(u, testCase, obj);
		try {
			control.searchJob();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		try {
			control.accionSinOutput();
		} catch (Exception e) {
			System.out.println("////////////////////////////////////////////////////////////");
			System.out.println(e.getMessage());
			System.out.println("////////////////////////////////////////////////////////////");
		}

		String status = u.getText(statusJob);
		waitChangeStatusJOB(status);
		boolean ejecucion = getCurrentJobStatus();

		if (ejecucion == true) {
			System.out.println("SE EJECUTO SOLO EN EL RELEASE");
			return "Ejecutado";
		}

		String startTime = getStartTimeJobStatus();

		System.out.println("Status actual:" + startTime);

		return startTime;
	}

	/**
	 * Da clic en la opcion Release del JOB a Ejecutar
	 * 
	 * @author 1000068915 - Jorge Hernandez
	 * @param startTime toma el valor de la fecha si hora antes de la ejecucion del
	 *                  JOB. Tambien puede tomar el valor "Ejecutado" si el JOB se
	 *                  ejecuto solo con dar en la opcion de Release. actions1 Toma
	 *                  el valor del JSON del dataprovider para el caso de que se
	 *                  necesite ejecutar mediante la opcion "Run now" actions2 Toma
	 *                  el valor del JSON del dataprovider para el caso de que se
	 *                  necesite ejecutar mediante la opcion "Rerun"
	 * @return Regresa un true en caso de que el status final del JOB sea "Ended OK"
	 * @date 26/Noviembre/2024
	 */
	public boolean ejecutarJOBStatus(String startTime, String actions1, String actions2)
			throws FindFailed, InterruptedException {
		JSONObject obj = null;
		u.waitWebElement(statusJob, 30, 1);
		String status = u.getText(statusJob);
		String resultadoEjecucion;

		if (startTime.equals("Ejecutado")) {
			resultadoEjecucion = getCurrentJobStatus(startTime);
			System.out.println("Resultado de la ejecucion: " + resultadoEjecucion);

			testCase.addScreenShotCurrentStep(resultadoEjecucion);
			String output = getOutput();
			System.out.println("Output: " + output);
			testCase.addTextEvidenceCurrentStep("Output: " + output);
		} else {

			for (int i = 0; i < 1000000; i++) {
				if (status.contains("Wait for Event") || status.contains("Wait User")) {
					obj = new JSONObject(actions1);
					break;
				} else if (status.contains("Executing")) {
					if (i % 30 == 0) {
						System.out.println("Esperando a que termine ejecucion");
					}
					u.click(statusJob);
					u.hardWait(1);
					status = u.getText(statusJob);
					if (status.equals("Ended OK")) {
						testCase.addBoldTextEvidenceCurrentStep("El job termina ejecucion con status Ended OK");
						screenshot();

						return true;
					}

				} else {

					obj = new JSONObject(actions2);
					break;

				}
			}

			ControlM control = new ControlM(u, testCase, obj);

			try {
				control.accionSinOutput();
			} catch (Exception e) {
				System.out.println("**************************************************************");
				System.out.println(e.getMessage());
				System.out.println("**************************************************************");
			}

			resultadoEjecucion = getCurrentJobStatus(startTime);
			System.out.println("Resultado de la ejecucion: " + resultadoEjecucion);
			testCase.addScreenShotCurrentStep(resultadoEjecucion);

			String output = getOutput();
			System.out.println("Output: " + output);
		}
		if (resultadoEjecucion.equals("Ended OK")) {
			testCase.addBoldTextEvidenceCurrentStep("El job termina ejecucion con status Ended OK");
			screenshot();
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Da clic en la opcion RERUN del JOB a Ejecutar
	 * 
	 * @author 1000068915 - Jorge Hernandez
	 * @param job toma el valor del JSON del dataprovider para la informacion del
	 *            JOB
	 * @return Regresa un valor true o false en funcion a si se realiza el cambio en
	 *         el status
	 * @date 26/Noviembre/2024
	 */
	public boolean rerunJOB(String job) throws FindFailed, InterruptedException {
		try {
			JSONObject obj = new JSONObject(job);

			ControlM control = new ControlM(u, testCase, obj);

			try {
				control.accionSinOutput();
			} catch (Exception e) {
				System.out.println("**************************************************************");
				System.out.println(e.getMessage());
				System.out.println("**************************************************************");
			}

			return true;
		} catch (Exception e) {
			System.out.println("No se ha realizado algun cambio de status " + e);
			return false;
		}
	}

	/**
	 * Da clic en la opcion HOLD del JOB a Ejecutar
	 * 
	 * @author 1000068915 - Jorge Hernandez
	 * @param job toma el valor del JSON del dataprovider para la informacion del
	 *            JOB
	 * @return Regresa un valor true o false en funcion a si se realiza el cambio en
	 *         el status
	 * @date 26/Noviembre/2024
	 */
	public boolean holdJOB(String job) throws FindFailed, InterruptedException {
		try {
			JSONObject obj = new JSONObject(job);

			ControlM control = new ControlM(u, testCase, obj);

			try {
				control.accionSinOutput();
			} catch (Exception e) {
				System.out.println("**************************************************************");
				System.out.println(e.getMessage());
				System.out.println("**************************************************************");
			}

			control.closeViewpoint();
			return true;
		} catch (Exception e) {
			System.out.println("No se ha holdeado el JOB " + e);
			return false;
		}
	}

	/**
	 * Crea un HashMap con key = nombre de la imagen y value = obj Pattern(path,
	 * similarity) para utilizar con Sikuli
	 * 
	 * @param dir raiz en donde se van a buscar las imagenes
	 * @return HashMap<String, Pattern>: con el nombre y el obj Pattern de la imagen
	 * @author guerrero.vargas
	 */
	public HashMap<String, org.sikuli.script.Pattern> getPatternsImg(String path) {
		HashMap<String, org.sikuli.script.Pattern> map = new HashMap<String, org.sikuli.script.Pattern>();
		File file = new File(sk.PatternPath("EBS\\"+path));
		File[] files = file.listFiles();

		ArrayList<org.sikuli.script.Pattern> patterns = new ArrayList<org.sikuli.script.Pattern>();

		// agrega el obj Pattern al ArrayList patterns
		for (File f : files) {
			if (f.isDirectory()) {
				// si el file es una carpeta entra a buscar imgs
				map.putAll((getPatternsImg(path + "\\" + f.getName())));

			} else {
				patterns.add(new org.sikuli.script.Pattern(f.getAbsolutePath()).similar((float) 0.95));
			}
		}

		// recorre el ArrayList patters para obtener el path individual de las img
		for (org.sikuli.script.Pattern p : patterns) {
			String fname = p.getFilename();
			fname = fname.replace(".png", ""); // elimina la extension
			fname = fname.replace("\\", "-"); //
			String[] afname = fname.split("-");

			map.put(afname[afname.length - 1], p);
		}

		return map;
	}

	/*
	 * Espera un tiempo definido a que aparezca en la pantalla la imagen solicitada
	 * 
	 * @author 1000068915 - Jorge Hernandez
	 * 
	 * @param time toma el tiempo que esperara a la imagen. image toma el nombre de
	 * la imagen a buscar en la pantalla
	 * 
	 * @return Regresa un true o false en funcion a si se encuentra la imagen en la
	 * pantalla
	 * 
	 * @date 26/Noviembre/2024
	 */
	public boolean waitImage(int time, String image) {
		for (int i = 0; i < time; i++) {
			try {
				sk.screen.wait(ImgPattern.get(image), 2);
				System.out.println("Se muestra la imagen: " + image);
				break;
			} catch (Exception e) {
				if (i % 30 == 0) {
					System.out.println("Esperando imagen " + image + "...");
				}
			}
		}

		try {
			sk.screen.wait(ImgPattern.get(image), 1);
			return true;
		} catch (Exception e) {
			// testCase.addBoldTextEvidenceCurrentStep("No se muestra la imagen " + image);
			return false;
		}
	}

	/*
	 * Espera un tiempo definido a que desaparezca la imagen indicada
	 * 
	 * @author 1000068915 - Jorge Hernandez
	 * 
	 * @param time toma el tiempo que esperara a la imagen. image toma el nombre de
	 * la imagen a buscar en la pantalla
	 * 
	 * @return Regresa un true o false en funcion a si se encuentra la imagen en la
	 * pantalla
	 * 
	 * @date 26/Noviembre/2024
	 * 
	 */
	public boolean waitDisImage(int time, String image) {
		for (int i = 0; i < time; i++) {
			try {
				sk.screen.wait(ImgPattern.get(image), 2);
				System.out.println("Se muestra la imagen: " + image);
			} catch (Exception e) {
				if (i % 30 == 0) {
					System.out.println("Esperando imagen " + image + "...");
				}
				break;
			}
		}

		try {
			sk.screen.wait(ImgPattern.get(image), 1);
			testCase.addBoldTextEvidenceCurrentStep("Aun se muestra la imagen " + image);
			return false;
		} catch (Exception e) {
			return true;
		}
	}

	/*
	 * Toma un screenshot de la pantalla en que se encuentre el paso
	 * 
	 * @author 1000068915 - Jorge Hernandez
	 * 
	 * @date 26/Noviembre/2024
	 */
	public void screenshot() {
		u.hardWait(1);
		String filename = "evidence" + numeroEvidencia;
		testCase.addScreenShotCurrentStep(filename);
		numeroEvidencia++;
		u.hardWait(1);
	}

	/*
	 * Realiza la conexion al servidor SFTP indicado
	 * 
	 * @author 1000068915 - Jorge Hernandez
	 * 
	 * @param host toma el dato host de la conexion SFTP port toma el dato del
	 * puerto de la conexion SFTP user toma el valor del usuario para la conexion
	 * SFTP pasword toma el valor del password para la conexion SFTP ruta toma el
	 * valor de la ruta en donde va a validar el archivo creado
	 * 
	 * @return Regresa true o false en funcion a si encuentra o no el archivo en la
	 * ruta indicada
	 * 
	 * @date 26/Noviembre/2024
	 */
//	public boolean validarCSVGenerado(String host, int port, String user, String password, String ruta,
//			String docName) {
//		LocalDate currentDate = LocalDate.now();
//
//		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("ddMMyyyy");
//
//		String date = currentDate.format(formatter);
//		System.out.println(date);
//
//		docName = docName + date + ".csv";
//		String path = "/" + ruta + "/" + docName;
//
//		System.out.println(path);
//		Session session = null;
//		ChannelSftp channelSftp = null;
//
//		try {
//			// Crear y configurar sesion SSH
//			JSch jsch = new JSch();
//			session = jsch.getSession(user, host, port);
//			session.setPassword(password);
//
//			// Configurar propiedades SSH para evitar problemas de seguridad
//			Properties config = new Properties();
//			config.put("StrictHostKeyChecking", "no");
//			session.setConfig(config);
//
//			// Conectar al servidor
//			session.connect();
//			System.out.println("CONEXION SFTP EXITOSA");
//
//			// Abrir canal SFTP
//			channelSftp = (ChannelSftp) session.openChannel("sftp");
//			channelSftp.connect();
//			System.out.println("Canal SFTP abierto");
//
//			ChannelSftp sftpChannel = (ChannelSftp) session.openChannel("sftp");
//			sftpChannel.connect();
//
//			// Verificar existencia del archivo
//			boolean fileExists = checkFileExists(channelSftp, path);
//			if (fileExists) {
//				// Descargar el archivo y leerlo como InputStream
//				InputStream inputStream = sftpChannel.get(path);
//				BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
//
//				// Obt�n los atributos del archivo
//				SftpATTRS attrs = sftpChannel.stat(path);
//
//				// Convierte la fecha de modificaci�n a un formato legible
//				long mtime = attrs.getMTime() * 1000L; // La fecha se obtiene en segundos, as� que convertimos a
//														// milisegundos
//				SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//
//				String lastModified = sdf.format(new java.util.Date(mtime));
//
//				testCase.addBoldTextEvidenceCurrentStep(
//						"Fecha de ultima modificacion del archivo " + docName + ": " + lastModified);
//
//				// Leer y procesar el CSV
//				@SuppressWarnings("deprecation")
//				CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT.withFirstRecordAsHeader());
//				int recordCount = 0;
//
//				// Mostrar encabezados
//				System.out.println("Encabezados: " + csvParser.getHeaderNames());
//				testCase.addTextEvidenceCurrentStep("Encabezados: " + csvParser.getHeaderNames());
//
//				// Leer filas y mostrar las primeras 20
//				for (CSVRecord record : csvParser) {
//					if (recordCount == 0) {
//						testCase.addBoldTextEvidenceCurrentStep(
//								"SE TOMA UNA MUESTRA DE 20 REGISTROS DEL ARCHIVO CSV GENERADO");
//					}
//					System.out.println(record);
//					testCase.addTextEvidenceCurrentStep("" + record);
//					recordCount++;
//
//					if (recordCount >= 20)
//						break;
//				}
//
//				// Verificar si solo tiene encabezados (sin registros)
//				if (recordCount == 0) {
//					System.out.println("Error: El archivo no tiene registros.");
//					throw new RuntimeException("El archvio no tiene registros");
//				}
//
//				// Cerrar recursos
//				csvParser.close();
//				reader.close();
//				inputStream.close();
//				return true;
//			} else {
//				System.out.println("El archivo no existe en la ruta especificada");
//				return false;
//			}
//
//		} catch (Exception e) {
//			System.out.println("Error en la conexion SFTP: " + e.getMessage());
//			testCase.addTextEvidenceCurrentStep("ERROR EN LA CONEXION SFTP");
//			testCase.addTextEvidenceCurrentStep("Datos de conexion usados:");
//			testCase.addTextEvidenceCurrentStep(host);
//			testCase.addTextEvidenceCurrentStep(user);
//			testCase.addTextEvidenceCurrentStep(password);
//			testCase.addTextEvidenceCurrentStep("" + port);
//			return false;
//
//		} finally {
//			// Cerrar canal y session
//			if (channelSftp != null && channelSftp.isConnected()) {
//				channelSftp.disconnect();
//			}
//			if (session != null && session.isConnected()) {
//				session.disconnect();
//			}
//		}
//	}

	/*
	 * Revisa si existe un documento en un servidor SFTP
	 * 
	 * @author 1000068915 - Jorge Hernandez
	 * 
	 * @param channelSftp toma el valor de la conexion SFTP remoteFilePath toma el
	 * valor de la ruta con el nombre del archivo a buscar
	 * 
	 * @return regresa un true o false en fucion a si se encuentra el archivo en el
	 * servidor
	 * 
	 * @date 26/Noviembre/2024
	 */
	public boolean checkFileExists(ChannelSftp channelSftp, String remoteFilePath) {
		try {
			channelSftp.lstat(remoteFilePath); // lstat lanza una excepci�n si el archivo no existe
			return true;
		} catch (SftpException e) {
			if (e.id == ChannelSftp.SSH_FX_NO_SUCH_FILE) {
				return false; // El archivo no existe
			} else {
				throw new RuntimeException("Error al verificar el archivo: " + e.getMessage());
			}
		}
	}

	/*
	 * Realiza la conexion al servidor SFTP indicado
	 * 
	 * @author 1000068915 - Jorge Hernandez
	 * 
	 * @param host toma el dato host de la conexion SFTP port toma el dato del
	 * puerto de la conexion SFTP user toma el valor del usuario para la conexion
	 * SFTP pasword toma el valor del password para la conexion SFTP ruta toma el
	 * valor de la ruta en donde va a validar el archivo creado
	 * 
	 * @return Regresa true o false en funcion a si se realizo correctamente la
	 * conexion al servidor
	 * 
	 * @date 26/Noviembre/2024
	 */
	@SuppressWarnings("finally")
	public boolean validarConexion(String host, int port, String user, String password) {
		LocalDate currentDate = LocalDate.now();

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("ddMMyyyy");

		String date = currentDate.format(formatter);
		System.out.println(date);

		Session session = null;
		ChannelSftp channelSftp = null;

		try {
			// Crear y configurar sesion SSH
			JSch jsch = new JSch();
			session = jsch.getSession(user, host, port);
			session.setPassword(password);

			// Configurar propiedades SSH para evitar problemas de seguridad
			Properties config = new Properties();
			config.put("StrictHostKeyChecking", "no");
			session.setConfig(config);

			// Conectar al servidor
			session.connect();
			testCase.addTextEvidenceCurrentStep("CONEXION SFTP EXITOSA");
			testCase.addTextEvidenceCurrentStep("Datos de conexion usados:");
			testCase.addTextEvidenceCurrentStep(host);
			testCase.addTextEvidenceCurrentStep(user);
			testCase.addTextEvidenceCurrentStep(password);
			testCase.addTextEvidenceCurrentStep("" + port);

		} catch (Exception e) {
			System.out.println("Error en la conexion SFTP: " + e.getMessage());
			testCase.addTextEvidenceCurrentStep("ERROR EN LA CONEXION SFTP");
			testCase.addTextEvidenceCurrentStep("Datos de conexion usados:");
			testCase.addTextEvidenceCurrentStep(host);
			testCase.addTextEvidenceCurrentStep(user);
			testCase.addTextEvidenceCurrentStep(password);
			testCase.addTextEvidenceCurrentStep("" + port);
			return false;

		} finally {
			// Cerrar canal y session
			if (channelSftp != null && channelSftp.isConnected()) {
				channelSftp.disconnect();
			}
			if (session != null && session.isConnected()) {
				session.disconnect();
			}

			return true;
		}
	}

	/*
	 * Realiza la busqueda de archivos dentro de un servidor SFTP y revisa la ultima
	 * fecha de modificacion
	 * 
	 * @author 1000068915 - Jorge Hernandez
	 * 
	 * @param host toma el dato host de la conexion SFTP port toma el dato del
	 * puerto de la conexion SFTP user toma el valor del usuario para la conexion
	 * SFTP pasword toma el valor del password para la conexion SFTP ruta toma el
	 * valor de la ruta en donde va a validar el archivo creado
	 * 
	 * @return Regresa true o false en funcion a si encuentra o no el archivo en la
	 * ruta indicada
	 * 
	 * @date 26/Noviembre/2024
	 */

	@SuppressWarnings("finally")
	public boolean validarCSVGenerados(String host, int port, String user, String password, String ruta) {
		Session session = null;
		ChannelSftp channelSftp = null;

		try {
			// Crear y configurar sesion SSH
			JSch jsch = new JSch();
			session = jsch.getSession(user, host, port);
			session.setPassword(password);

			// Configurar propiedades SSH para evitar problemas de seguridad
			Properties config = new Properties();
			config.put("StrictHostKeyChecking", "no");
			session.setConfig(config);

			// Conectar al servidor
			session.connect();
			System.out.println("Conexion SFTP exitosa");

			// Abrir canal SFTP
			channelSftp = (ChannelSftp) session.openChannel("sftp");
			channelSftp.connect();
			System.out.println("Canal SFTP abierto");

			ChannelSftp sftpChannel = (ChannelSftp) session.openChannel("sftp");
			sftpChannel.connect();

			// Listar archivos en el directorio remoto
			Vector<?> fileList = sftpChannel.ls(ruta);

			// Formato de fecha para la �ltima modificaci�n
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

			// Iterar sobre la lista de archivos y mostrar el nombre con la fecha de
			// modificaci�n
			testCase.addBoldTextEvidenceCurrentStep("Archivos encontrados en la ruta: " + ruta);
			for (Object obj : fileList) {
				ChannelSftp.LsEntry entry = (ChannelSftp.LsEntry) obj;
				// Obtener la fecha de la �ltima modificaci�n
				long modifiedTime = entry.getAttrs().getMTime() * 1000L; // Convertir a milisegundos
				Date modifiedDate = new Date(modifiedTime);
				String formattedDate = sdf.format(modifiedDate);

				// Imprimir nombre del archivo y fecha de modificaci�n
				System.out.println("Archivo: " + entry.getFilename() + " | �ltima modificaci�n: " + formattedDate);

				testCase.addTextEvidenceCurrentStep(
						"Archivo: " + entry.getFilename() + " | �ltima modificaci�n: " + formattedDate);
			}

		} catch (Exception e) {
			System.out.println("Error en la conexion SFTP: " + e.getMessage());
			testCase.addBoldTextEvidenceCurrentStep("Error en la conexion SFTP");
			testCase.addBoldTextEvidenceCurrentStep("Datos de conexion usados:");
			testCase.addBoldTextEvidenceCurrentStep(host);
			testCase.addBoldTextEvidenceCurrentStep(user);
			testCase.addBoldTextEvidenceCurrentStep(password);
			testCase.addBoldTextEvidenceCurrentStep("" + port);
			return false;

		} finally {
			// Cerrar canal y session
			if (channelSftp != null && channelSftp.isConnected()) {
				channelSftp.disconnect();
			}
			if (session != null && session.isConnected()) {
				session.disconnect();
			}

			return true;
		}
	}

	/*
	 * Cierra los aplicativos de EBS y el navegador Chrome directamente desde un
	 * comando en un archivo .bat
	 * 
	 * @author 1000068915 - Jorge Hernandez
	 * 
	 * @date 26/Noviembre/2024
	 */
//	public void closeAll() {
//		try {
//			Process process = Runtime.getRuntime().exec(GlobalVariables.User_Dir + "\\tools\\killtask.bat");
//			process.waitFor();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}

	/**
	 * Envia la combinacion de teclas 'Alt' + 'n', esto emula el click en el boton
	 * Ejecutar Nueva Solicitud
	 *
	 * @author JoseO@hexaware.com
	 */
	public void clickEjecutarNuevaSolicitud() {
		keyAlt("n");
		u.hardWait(5);
	}

	/**
	 * Método para ingresar el texto que queremos en el campo solicitado
	 *
	 * @param text
	 * @author JessicaR@hexaware.com
	 */
	public void enterText(String text) {
		sk.screen.type(text);
		sk.screen.type(Key.TAB);
	}

	/**
	 * Buscar la imagen del boton ejecutar y le da click
	 *
	 * @author JesusH@hexaware.com
	 * @throws FindFailed
	 */
	public void clickEjecutar() {

		keyAlt("j");
	}

	/**
	 * Emula presionar la tecla ENTER un numero "n" de veces seguidas
	 *
	 * @param n El numero de veces que quieres que presione la tecla ENTER
	 * @author JoseO@Hexaware.com
	 */
	public void nEnter(int n) {

		int x = 1;
		while (x <= n) {
			sk.screen.type(Key.ENTER);
			x = x + 1;
		}
	}

	/**
	 * Realiza un click en la opcion no en el applet
	 *
	 * @author ricardor4@hexaware.com
	 * @throws FindFailed
	 */
	public void clickNo() {

		keyAlt("n");
	}

	/**
	 * Envia la combinacion de teclas 'Alt' + 'e', esto emula el clic en el boton
	 * encontrar
	 *
	 * @author JesusH@hexaware.com
	 */
	public void clickEncontrar() {
		keyAlt("e");
	}

	/**
	 * Valida si existe el patron a buscar
	 *
	 * @param target Imagen a buscar
	 * @return True o False utilizado para hacer un assert
	 * @throws FindFailed
	 * @author JesusH@hexaware.com
	 */
	public Boolean validatePattern(String target) throws FindFailed {
		if (sk.screen.exists(ImgPattern.get(target)) != null) {
			return true;
		} else {
			return false;
		}

	}

	/**
	 * Hace clic en una imagen
	 *
	 * @param pattern Imagen a la que le queremos dar clic
	 * @param timeOut Tiempo de espera para encontrar la imagen
	 * @author JesusH@hexaware.com
	 */
	public void clickPattern(String pattern, Integer timeOut) {
		try {
			System.out.println(pattern);
			sk.screen.wait(ImgPattern.get(pattern), timeOut).click();
		} catch (FindFailed findFailed) {
			logger.error("Error " + findFailed);
			System.out.println("Error " + findFailed);
		}
	}

	/**
	 * Metodo que selecciona boton ver solicitudes
	 * 
	 * @throws FindFailed
	 * @author ricardo.palacios
	 */
	public void vers() throws FindFailed {
		u.hardWait(5);
		sk.screen.type("x", Key.ALT);
		u.hardWait(5);
		sk.screen.type("s");
		u.hardWait(5);

	}

	/**
	 * Método para ingresar el texto en campos de el reporte XXFC-Informe de
	 * Requerimientos de Efectivo Detallado
	 *
	 * @param text
	 * @author RicardoR4@hexaware.com
	 */
	public void camposReporteEfectivo(String unidadOperativa, String pagarvencimiento, String fechaPagoDesde,
			String fechaPagoHasta, String fechaPago, String incluirFacturasNoValidadas, String opcionResumen,
			String totalPorProvedor) {
		sk.screen.type(unidadOperativa);
		u.hardWait(3);
		sk.screen.type(Key.TAB);
		u.hardWait(3);
		sk.screen.type(pagarvencimiento);
		u.hardWait(3);
		sk.screen.type(Key.TAB);
		u.hardWait(3);
		sk.screen.type(fechaPagoDesde);
		u.hardWait(3);
		sk.screen.type(fechaPagoHasta);
		u.hardWait(3);
		sk.screen.type(fechaPago);
		u.hardWait(3);
		sk.screen.type(incluirFacturasNoValidadas);
		u.hardWait(3);
		this.nTAB(2);
		sk.screen.type(opcionResumen);
		this.nTAB(5);
		sk.screen.type(totalPorProvedor);
		u.hardWait(3);

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
	 * Envia la combinacion de teclas 'Alt' + 'a', esto emula el click en el boton
	 * Aceptar de la ventana parametros de una Nueva solicitud
	 *
	 * @author JoseO@hexaware.com
	 */
	public void clickAceptarParametros() {
		keyAlt("a");
	}

	/**
	 * Envia la combinacion de teclas 'Alt' + 'l', esto emula el click en el boton
	 * Visualizar Output
	 *
	 * @author JoseO@hexaware.com
	 */
	public void clickVisualizarOutput() {
		keyAlt("l");
	}

	public void AbrirBuscarFolioExcel(String folio) {
		
		u.hardWait(5);
		sk.screen.keyDown(Key.LEFT);
		u.hardWait(4);
		sk.screen.keyDown(Key.ENTER);
		u.hardWait(4);
		sk.screen.type("f", Key.CTRL);
		u.hardWait(4);
		sk.screen.type(folio);
		sk.screen.type(Key.ENTER);
		u.hardWait(5);
	}

	/**
	 * Metodo que ejecuta un Alt+F4
	 */
	public void altF4() {
		sk.screen.keyDown(Key.ALT + Key.F4);
		sk.screen.keyUp(Key.ALT + Key.F4);
	}
}
