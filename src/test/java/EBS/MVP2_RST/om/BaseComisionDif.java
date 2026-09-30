package EBS.MVP2_RST.om;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.sikuli.script.FindFailed;
import org.sikuli.script.Match;
import org.sikuli.script.Pattern;
import org.sikuli.script.Screen;
import org.testng.Assert;

import modelo.TestCase;
import utils.SikuliUtil;
import utils.selenium.SeleniumUtil;

public class BaseComisionDif {

	private SeleniumUtil u;
	private WebDriverWait wait;
	private TestCase testCase;
	private WebDriver driver;
	
	//Variables
	SikuliUtil sk;
	ArrayList<Pattern> patterns;
	File Folder;
	HashMap<String, Pattern> ImgPattern;
	ArrayList<String> ListImage;
	Pattern pathImg;

	// objects

	By footerboxEbs = By.id("FooterBox");
	By TXTUser = By.id("usernameField");
	By TXTPassword = By.id("passwordField");

	// Fin objects

	public BaseComisionDif(SeleniumUtil u, TestCase testCase, SikuliUtil sk, HashMap<String, Pattern> ImgPattern) {

		this.u = u;
		this.sk = sk;
		this.driver = u.getDriver();
		this.wait = new WebDriverWait(driver, Duration.ofSeconds(40));
		this.testCase = testCase;
		this.ImgPattern = ImgPattern;
	}

	/*
	 * metodo utilizado para navegar a la pagina de EBS
	 * @param folder
	 * @author Alan Perez
	 * @date 12/12/2024    	 */
	public void GoToEBSPage() {
		String url = "http://fcrebsqa.femcom.net:8011/OA_HTML/AppsLocalLogin.jsp?requestUrl=http%3A%2F%2Ffcrebsqa.femcom.net%3A8011%2FOA_HTML%2FOA.jsp%3Fpage%3D%2Foracle%2Fapps%2Ffnd%2Fframework%2Fnavigate%2Fwebui%2FNewHomePG%26homePage%3DY%26OAPB%3DFWK_HOMEPAGE_BRAND%26transactionid%3D570987066%26oapc%3D2%26oas%3D4ppKB3RdwGUZ9ZtkXsf3GA..&cancelUrl=http%3A%2F%2Ffcrebsqa.femcom.net%3A8011%2FOA_HTML%2FAppsLogin&langCode=US#dummyAnchor";
		u.get(url);
		u.getDriver().getTitle();
		u.getDriver().manage().window().maximize();
		u.getText(footerboxEbs);
	}

	/*
	 * metodo utilizado para ingresar las credenciales
	 * @param user, Password
	 * @author Alan Perez
	 * @date 12/12/2024   	 */
	public void IngresoCredenciales(String User, String Password) {
		u.highLight(TXTUser);
		u.getDriver().findElement(TXTUser).click();
		driver.findElement(TXTUser).sendKeys(User);
		driver.findElement(TXTPassword).click();
		driver.findElement(TXTPassword).sendKeys(Password);
	}

	By Request = By.xpath("//a[normalize-space()='Request']");
	By Financial = By.xpath("//a[ normalize-space()='Financial']");
	By Informes = By.xpath("//a[ normalize-space()='Informes']");
	By Solicitar = By.xpath("//a[normalize-space()='Solicitar']");
	By Standard = By.xpath("//a[normalize-space()='Standard']");
	By Pagos = By.xpath("//a[normalize-space()='Pagos']");
	By Consulta = By.xpath("//a[normalize-space()='Consulta']");

	By requestC = By.xpath("//div[normalize-space()='Request']");
	By financialC = By.xpath("//div[normalize-space()='Financial']");
	By Abrir = By.xpath("//a[text()='Abrir']");
	By formaPago = By.xpath("//div[normalize-space()='Prov - Forma Pago']");
	
	/*
	 * metodo utilizado para ingresar las credenciales
	 * @author Alan Perez
	 * @date 12/12/2024   	 */
	public void ClickOnAbrir() {
		// TODO Auto-generated method stub
		
			By obj = Abrir;
			waitElementToBeClickable(obj,50);
			u.getDriver().manage().window().maximize();
			u.highLight(obj);
//			u.unhighLight(obj);
			u.click(obj);
			u.hardWait(2);
		}

	/*
	 * metodo utilizado para regresar las carpetas que se tienen en EBS
	 * @param folder
	 * @author Alan Perez
	 * @date 12/12/2024 	 */
	// Metodo para validar carpetas
	public By carpeta_c(String Carpeta) {
		switch (Carpeta) {
		case "request":
			return Request;

		case "Financial":
			return Financial;

		case "Informes":
			return Informes;
			
		case "Standard":
			return Standard;
		case "Pagos":
			return Pagos;

		case "Consulta":
			return Consulta;
			
			case "Solicitar":
				return Solicitar;
				
			case "requestC":
				return requestC;
				
			case "financialC":
				return financialC;
				
			case "formaPago":
				return formaPago;
		}
		return null;
	}

	/*
	 * metodo utilizado para esperar a que un elemento sea clickeable
	 * @param elemento, seconds
	 * @author Alan Perez
	 * @date 12/12/2024 	 */
	public void waitElementToBeClickable(By elemento, int seconds) {

		WebDriverWait wait = new WebDriverWait(u.getDriver(), Duration.ofSeconds(seconds));
		wait.until(ExpectedConditions.elementToBeClickable(elemento));
	}

	/** metodo utilizado para realizar clic en una carpeta
	 * @param folder
	 * @author Alan Perez
	 * @date 12/12/2024    */
	public boolean ClickOnCarpeta(String carpeta) {
		// TODO Auto-generated method stub
		try {
			By obj = carpeta_c(carpeta);
			waitElementToBeClickable(obj, 50);
			u.getDriver().manage().window().maximize();
			u.highLight(obj);
			u.unhighLight(obj);
			u.click(obj);
			u.hardWait(2);
			testCase.addScreenShotCurrentStep(u, "Click en la carpeta: " + carpeta);
			return true;
		} catch (Exception e) {

			System.out.println("Error Responsabilidad no encontrada " + e);

			return false;
		}
	}

	/*
	 * metodo utilizado para abrir EBS y validar la ruta de descarga
	 * @param folder
	 * @author Alan Perez
	 * @date 12/12/2024  	 */
	public void AbrirEBS() {
		// TODO Auto-generated method stub
		// Se ingresa a la carpeta descargas
		// Ruta de la carpeta de descargas
		// Obtener la ruta de la carpeta de descargas del usuario
		String userHome = System.getProperty("user.home"); // Ruta base del usuario (en Windows, sería C:/Users/[usuario])
		String downloadDirectory = userHome + "/Downloads"; // Ruta completa hacia la carpeta Downloads

		// Palabra clave que está en el nombre del archivo
		String keyword = "frmservlet"; // Ajusta esta palabra a la que siempre tendrá el archivo

		// Obtener la lista de archivos en la carpeta de descargas
		File folder = new File(downloadDirectory);
		File[] listOfFiles = folder.listFiles();

		if (listOfFiles != null) {
			for (File file : listOfFiles) {
				if (file.isFile() && file.getName().contains(keyword)) {
					System.out.println("Archivo encontrado: " + file.getName());

					// Intentar abrir el archivo con el programa predeterminado del sistema
					try {
						if (System.getProperty("os.name").toLowerCase().contains("win")) {
							// En Windows
							Runtime.getRuntime().exec("cmd /c start " + file.getAbsolutePath());
						} else if (System.getProperty("os.name").toLowerCase().contains("mac")) {
							// En MacOS
							Runtime.getRuntime().exec("open " + file.getAbsolutePath());
						} else {
							// En Linux
							Runtime.getRuntime().exec("xdg-open " + file.getAbsolutePath());
						}
					} catch (IOException e) {
						e.printStackTrace();
					}

					break; // Detener el ciclo una vez que encontramos el archivo
				}
			}
		} else {
			System.out.println("No se pudo acceder a la carpeta de descargas.");
			Assert.fail("fallo al encontrar el elemento");
		}
	}

	/*
	 * metodo utilizado para utilizar las imagenes cargadas
	 * @param folder
	 * @author Alan Perez
	 * @date 12/12/2024 
	 */
	HashMap<String, Pattern> getPatternsImg(File Folder) {
		sk.screen = new Screen();
		if (Folder.exists() && Folder.isDirectory()) {
			File[] files = Folder.listFiles();
			for (File s : files) {
				patterns.add(new Pattern(s.getAbsolutePath()).similar((float) .95));
			}
			for (Pattern p : patterns) {
				String fname = p.getFilename().toString();
				fname = fname.replace("\\", "-");
				fname = fname.replace(".png", "");
				fname = fname.replace(".PNG", "");
				String[] afname = fname.split("-");
				ImgPattern.put(afname[afname.length - 1], p);
			}
		}
		return ImgPattern;
	}

	/**
	 * Metodo para manejar la ventana de advertencia que aparece al abrir un applet
	 * @author JesusH@hexaware.com
	 * @throws FindFailed
	 * @date 12/12/2024 	 */
	public void runApplet(File Folder) throws FindFailed {

		System.out.println("Run Applet");
		System.out.println("El folder es: " + Folder);

		ImgPattern = getPatternsImg(Folder);
		System.out.println("Folder es:" + Folder);
		Screen screen = new Screen();
		String fol = Folder.toString();

		System.out.println("El path es el siguiente: ");
		// Ruta de la imagen que se buscará
		Pattern pattern = new Pattern(fol); // Crear el patrón con la imagen

		System.out.println("Esperando a que el patrón aparezca...");
		Match match = screen.wait(pattern, 10); // Esperar hasta 10 segundos

		// Paso 4: Comprobar si el patrón fue encontrado
		if (match != null) {
			screen.click(match);
		} else {
			System.out.println("El patrón no se encontró dentro del tiempo de espera.");
			Assert.fail("fallo al encontrar el elemento");
		}
	}

	/*
	 * metodo utilizado para realizar cambio de responsabilidad
	 * @param folder, PngName
	 * @author Alan Perez
	 * @throws FindFailed	 
	 * @date 12/12/2024    */
	public boolean CambioResponsabilidad(File folder, String PngName) throws FindFailed {

		try {
			HashMap<String, Pattern> ImgPattern = getPatternsImg2(folder);
			System.out.println("Patrones cargados del folder: " + folder);

			// Crear una instancia de Screen de Sikuli
			Screen screen = new Screen();

			// Switch responsabilidad btn
			String switchR = "switch_responsability.png"; // Asegúrate de que este nombre esté disponible en ImgPattern
			Pattern pattern = ImgPattern.get(switchR); // Obtén el patrón del HashMap usando el nombre de la imagen
			Match match = screen.wait(pattern, 10);

			System.out.println("Patrón encontrado, haciendo clic...");
			screen.click(match);

			// Seleccion de APX_SuperUsuario
			String APX = "APX_SuperUsuario.png"; // Asegúrate de que este nombre esté disponible en ImgPattern
			Pattern pattern2 = ImgPattern.get(PngName); // Obtén el patrón del HashMap usando el nombre de la imagen
			Match match2 = screen.wait(pattern2, 15);

			System.out.println("Patrón encontrado, haciendo clic...");
			screen.click(match2);

			String Acept = "AceptarEBS.png";
			Pattern pattern5 = ImgPattern.get(Acept);
			Match match5 = screen.wait(pattern5, 15);
			System.out.println("Patrón encontrado, haciendo clic...");
			screen.click(match5);

			// Seleccionar ver request
			String verRequest = "Ver_request.png"; // Asegúrate de que este nombre esté disponible en ImgPattern
			Pattern pattern3 = ImgPattern.get(verRequest); // Obtén el patrón del HashMap usando el nombre de la imagen
			Match match3 = screen.wait(pattern3, 10);

			System.out.println("Patrón encontrado, haciendo clic...");
			screen.click(match3);

			// Seleccionar Solicitudes
			String Solicitud = "SolicitudesEBS.png"; // Asegúrate de que este nombre esté disponible en ImgPattern
			Pattern pattern4 = ImgPattern.get(Solicitud); // Obtén el patrón del HashMap usando el nombre de la imagen
			Match match4 = screen.wait(pattern4, 20);

			System.out.println("Patrón encontrado, haciendo clic...");
			screen.click(match4);
			return true;
		} catch (Exception e) {
			Assert.fail("fallo al encontrar el elemento");
			return false;
		}
	}

	/** metodo utilizado para ejecutar archivo en java, se mejora el metodo 1
	 *@param folder
	 * @author Alan Perez
	 * @throws FindFailed
	 * @date 12/12/2024	 */
	// mejora en metodo
	public boolean RunApplet2(File folder) throws FindFailed {
		// Llamar al método que obtiene los patrones de las imágenes dentro del folder
		HashMap<String, Pattern> ImgPattern = getPatternsImg2(folder);
		System.out.println("Patrones cargados del folder: " + folder);

		// Crear una instancia de Screen de Sikuli
		Screen screen = new Screen();

		// Seleccionar una imagen para buscar en la pantalla
		String imageName = "EBS_AceptoRiesgos.png"; // Asegúrate de que este nombre esté en ImgPattern
		Pattern pattern = ImgPattern.get(imageName);

		try {
			if (pattern != null) {
				System.out.println("Esperando a que el patrón aparezca...");

				// Esperar hasta que el patrón aparezca en la pantalla durante 10 segundos
				Match match = screen.wait(pattern, 10);
				testCase.addScreenShotCurrentStep(u, "Se localiza la casilla");
				
				// Si el patrón es encontrado, hacer clic
				if (match != null) {
					System.out.println("Patrón encontrado, haciendo clic...");
					screen.click(match);
					u.hardWait(2);
					testCase.addScreenShotCurrentStep(u, "Se marca la casilla");
					return true;
				}
			}
		} catch (Exception e) {
			System.out.println("No se mostró el elemento.");
		}

		return false; // Si el elemento no apareció o hubo un error, devuelve false
	}

	/** metodo utilizado para ejecutar el archivo java
	 * @param folder
	 * @author Alan Perez
	 * @throws FindFailed
	 * @date 12/12/2024 	 */
	public boolean EjecutarApplet(File folder) throws FindFailed {
		// Llamar al método que obtiene los patrones de las imágenes dentro del folder
		HashMap<String, Pattern> ImgPattern = getPatternsImg2(folder);
		System.out.println("Patrones cargados del folder: " + folder);

		// Crear una instancia de Screen de Sikuli
		Screen screen2 = new Screen();

		// Seleccionar una imagen para buscar en la pantalla
		String imageName = "Ejecutar_EBS_Comision.png"; // Asegúrate de que este nombre esté en ImgPattern
		Pattern pattern = ImgPattern.get(imageName);

		try {
			if (pattern != null) {
				System.out.println("Esperando a que el patrón aparezca: " + imageName);

				// Esperar hasta que el patrón aparezca en la pantalla durante 15 segundos
				Match match = screen2.wait(pattern, 15);

				// Si el patrón es encontrado, hacer clic
				if (match != null) {
					System.out.println("Patrón encontrado, haciendo clic: " + imageName);
					screen2.click(match);
					testCase.addScreenShotCurrentStep(u, "Se ejecuta correctamente el applet");
					return true;
				}
			}
		} catch (Exception e) {
			System.out.println("No se encontró el elemento: " + e.getMessage());
		}

		return false; // Si el elemento no apareció o hubo un error, devuelve false
	}

	/*
	 * metodo utilizado para obtener los patrones de las imagenes dentro de una
	 * carpeta
	 * @param folder
	 * @author Alan Perez
	 * @date 12/12/2024 	 
	 */
	
	public HashMap<String, Pattern> getPatternsImg2(File folder) {
		HashMap<String, Pattern> ImgPattern = new HashMap<>();

		// Asegúrate de que la carpeta exista y contenga archivos
		if (folder.exists() && folder.isDirectory()) {
			// Obtener todos los archivos dentro de la carpeta
			File[] files = folder.listFiles();

			if (files != null) {
				// Recorrer todos los archivos
				for (File file : files) {
					// Solo procesar archivos PNG
					if (file.isFile() && file.getName().endsWith(".png")) {
						// Crear un patrón para cada archivo de imagen y agregarlo al HashMap
						Pattern pattern = new Pattern(file.getAbsolutePath()).similar(0.95f);
						ImgPattern.put(file.getName(), pattern);
					}
				}
			}
		} else {
			System.out.println("La carpeta no existe o no es un directorio válido.");
			Assert.fail("fallo al encontrar el elemento");
		}

		return ImgPattern;
	}

	/** metodo utilizado para ver menu de solicitudes
	 * @param folder
	 * @author Alan Perez
	 * @throws AWTException
	 * @date 12/12/2024	 */
	public boolean AceptarConcurrente(File folder) throws AWTException {
		// TODO Auto-generated method stub
		// Llamar al método que obtiene los patrones de las imágenes dentro del folder
		HashMap<String, Pattern> ImgPattern = getPatternsImg2(folder);
		System.out.println("Patrones cargados del folder: " + folder);

		// Crear una instancia de Screen de Sikuli
		Screen screen = new Screen();
		Robot robot = new Robot();
		// Seleccionar una imagen para buscar en la pantalla (esto depende de tus patrones)
		String imageName = "aceptarperi.png"; // Asegúrate de que este nombre esté disponible en ImgPattern
		Pattern pattern = ImgPattern.get(imageName); // Obtén el patrón del HashMap usando el nombre de la imagen

		try {
			// Verificar si el patrón se encuentra
			if (pattern != null) {
				System.out.println("Esperando a que el patrón aparezca..." + imageName);

				// Esperar hasta que el patrón aparezca en la pantalla durante 10 segundos
				Match match = screen.wait(pattern, 30); // Esperar hasta 10 segundos

				// Si el patrón es encontrado, hacer clic
				if (match != null) {
					System.out.println("Patrón encontrado, haciendo clic..." + imageName);
					screen.click(match);
					testCase.addScreenShotCurrentStep(u, "Se hizo clic en el botón 'Aceptar Concurrente'.");

					return true;
				}

				else {
					System.out.println("No encontro el pattern" + imageName);
					testCase.addScreenShotCurrentStep(u, "No se encontró el patrón 'aceptarperi'.");
					return false;
				}
			}
		} catch (Exception e) {
			System.out.println(e);
			testCase.addScreenShotCurrentStep(u, "Excepción al buscar patrón 'aceptarperi': " + e.getMessage());
			return false;
		}
		return false;
	}

	/** Método que obtiene los patrones de las imágenes AceptarFecha dentro del folder y si se encuenta le da clic
	 * @param folder
	 * @author Rafael Ramos
	 * @throws AWTException
	 * @date 12/03/2025 	 */	
	public boolean AceptarFecha(File folder) throws AWTException {
		// TODO Auto-generated method stub
		// Llamar al método que obtiene los patrones de las imágenes dentro del folder
		HashMap<String, Pattern> ImgPattern = getPatternsImg2(folder);
		System.out.println("Patrones cargados del folder: " + folder);

		// Crear una instancia de Screen de Sikuli
		Screen screen = new Screen();
		Robot robot = new Robot();
		// Seleccionar una imagen para buscar en la pantalla (esto depende de tus patrones)
		String imageName = "acepuno.png"; // Asegúrate de que este nombre esté disponible en ImgPattern
		Pattern pattern = ImgPattern.get(imageName); // Obtén el patrón del HashMap usando el nombre de la imagen

		try {
			// Verificar si el patrón se encuentra
			if (pattern != null) {
				System.out.println("Esperando a que el patrón aparezca..." + imageName);

				// Esperar hasta que el patrón aparezca en la pantalla durante 10 segundos
				Match match = screen.wait(pattern, 30); // Esperar hasta 10 segundos

				// Si el patrón es encontrado, hacer clic
				if (match != null) {
					System.out.println("Patrón encontrado, haciendo clic..." + imageName);
					screen.click(match);
					testCase.addScreenShotCurrentStep(u, "Se hizo clic en el botón de aceptar fecha.");
					return true;
				}

				else {
					System.out.println("No encontro el pattern" + imageName);
					testCase.addScreenShotCurrentStep(u, "No se encontró el patrón 'acepuno' en pantalla.");
					return false;
				}
			}
		} catch (Exception e) {
			System.out.println(e);
			testCase.addScreenShotCurrentStep(u, "Error al buscar patrón 'acepuno': " + e.getMessage());
			return false;
		}
		return false;
	}

	/** Método que obtiene los patrones de las imágenes VerMenu_Solicitudes dentro del folder y si se encuenta le da clic
	 * @param folder
	 * @author Rafael Ramos
	 * @date 12/03/2025 	 */	
	public boolean VerMenu_Solicitudes(File folder) {
		// TODO Auto-generated method stub
		// Llamar al método que obtiene los patrones de las imágenes dentro del folder
		HashMap<String, Pattern> ImgPattern = getPatternsImg2(folder);
		System.out.println("Patrones cargados del folder: " + folder);

		// Crear una instancia de Screen de Sikuli
		Screen screen2 = new Screen();

		// Seleccionar una imagen para buscar en la pantalla (esto depende de tus patrones)
		String imageName = "Ver_EBS.png"; // Asegúrate de que este nombre esté disponible en ImgPattern
		Pattern pattern = ImgPattern.get(imageName); // Obtén el patrón del HashMap usando el nombre de la imagen

		try {
			// Verificar si el patrón se encuentra
			if (pattern != null) {
				System.out.println("Esperando a que el patrón aparezca ver_ebs..." + imageName);

				// Esperar hasta que el patrón aparezca en la pantalla durante 10 segundos
				Match match = screen2.wait(pattern, 30); // Esperar hasta 10 segundos

				// Si el patrón es encontrado, hacer clic
				if (match != null) {
					System.out.println("Patrón encontrado, haciendo clic..." + imageName);
					screen2.click(match);
					testCase.addScreenShotCurrentStep(u, "Se hizo clic en 'Ver_EBS'.");
					// Screen screen3 = new Screen();

					// Seleccionar una imagen para buscar en la pantalla (esto depende de tus patrones)
					String imageName3 = "solicitudes.png"; // Asegúrate de que este nombre esté disponible en ImgPattern
					Pattern pattern3 = ImgPattern.get(imageName3); // Obtén el patrón del HashMap usando el nombre de la imagen

					try {
						// Verificar si el patrón se encuentra
						if (pattern3 != null) {
							System.out.println("Esperando a que el patrón aparezca..." + imageName3);

							// Esperar hasta que el patrón aparezca en la pantalla durante 10 segundos
							Match match2 = screen2.wait(pattern3, 30); // Esperar hasta 10 segundos

							// Si el patrón es encontrado, hacer clic
							if (match != null) {
								System.out.println("Patrón encontrado, haciendo clic..." + imageName3);
								screen2.click(match2);
								testCase.addScreenShotCurrentStep(u, "Se hizo clic en 'solicitudes'.");
								return true;
							}
						} else {
							System.out.println("El patrón no se encontró dentro del tiempo de espera.");

							return false;
						}
					} catch (Exception e) {
						System.out.println("No se encontro el elemento" + e);

						return false;
					}
				}
			} else {
				System.out.println("El patrón no se encontró dentro del tiempo de espera.");
				Assert.fail("fallo al encontrar el elemento de Ver");
			}
		} catch (Exception e) {
			System.out.println("No se encontro el elemento" + e);
			Assert.fail("fallo al encontrar el elemento de Solicitud");
		}
		return false;
	}

	/** Método que obtiene los patrones de las imágenes dentro del folder y si se encuenta le da clic
	 * @param folder
	 * @author Rafael Ramos
	 * @date 12/03/2025
	 */	
	public boolean CambioResponsabilidad(File folder) {
		// Llamar al método que obtiene los patrones de las imágenes dentro del folder
		HashMap<String, Pattern> ImgPattern = getPatternsImg2(folder);
		System.out.println("Patrones cargados del folder: " + folder);

		// Crear una instancia de Screen de Sikuli
		Screen screen = new Screen();

		// Seleccionar una imagen para buscar en la pantalla (esto depende de tus patrones)
		String imageName = "CambioResponsabilidad.png"; // Asegúrate de que este nombre esté disponible en ImgPattern
		Pattern pattern = ImgPattern.get(imageName); // Obtén el patrón del HashMap usando el nombre de la imagen

		try {
			// Verificar si el patrón se encuentra
			if (pattern != null) {
				System.out.println("Esperando a que el patrón aparezca..." + imageName);

				// Esperar hasta que el patrón aparezca en la pantalla durante 10 segundos
				Match match = screen.wait(pattern, 30); // Esperar hasta 10 segundos

				// Si el patrón es encontrado, hacer clic
				if (match != null) {
					System.out.println("Patrón encontrado, haciendo clic..." + imageName);
					screen.click(match);
					testCase.addScreenShotCurrentStep(u, "Cambio de responsabilidad exitoso");
					return true;
				}

				else {
					System.out.println("No encontro el pattern" + imageName);
					testCase.addScreenShotCurrentStep(u, "No se encontró el botón de Cambio de Responsabilidad");
					return false;
				}
			}
		} catch (Exception e) {
			System.out.println(e);
			testCase.addScreenShotCurrentStep(u, "Error al intentar cambiar responsabilidad");
			return false;
		}

		return false;	        
	  }
	  
	/** metodo utilizado para dar clic en los tres puntos para busqueda
	 * @param folder
	 * @author Mariana Avitia 
	 * @date 12/03/2025 */
	  public boolean clickEnPuntos(File folder) {
			// Llamar al método que obtiene los patrones de las imágenes dentro del folder
		        HashMap<String, Pattern> ImgPattern = getPatternsImg2(folder);
		        System.out.println("Patrones cargados del folder: " + folder);

		        // Crear una instancia de Screen de Sikuli
		        Screen screen = new Screen();
		       
		        // Seleccionar una imagen para buscar en la pantalla (esto depende de tus patrones)
		        String imageName = "puntos.png";  // Asegúrate de que este nombre esté disponible en ImgPattern
		        Pattern pattern = ImgPattern.get(imageName);  // Obtén el patrón del HashMap usando el nombre de la imagen
		      
		        try {
		        // Verificar si el patrón se encuentra
		        if (pattern != null) {
		            System.out.println("Esperando a que el patrón aparezca..." + imageName);
		            
		            // Esperar hasta que el patrón aparezca en la pantalla durante 10 segundos
		            Match match = screen.wait(pattern, 30);  // Esperar hasta 10 segundos
		          
		            // Si el patrón es encontrado, hacer clic
		            if (match != null) {
		                System.out.println("Patrón encontrado, haciendo clic..."+ imageName);
		                screen.click(match);
		            return true;
		            }
			  
		                else {
		                System.out.println("No encontro el pattern" + imageName);	
		              
		                return false;
		                }
		                }
		        }
		        catch(Exception e) {
		        System.out.println(e);
		       return false;
		        }
				return false;
		        
		  }
	  
	  /** metodo utilizado para dar clic en cancelar
		 * @param folder
		 * @author Mariana Avitia	
		 * @date 12/03/2025	 */
	  public boolean clickEnCancelar(File folder) {
			// Llamar al método que obtiene los patrones de las imágenes dentro del folder
		        HashMap<String, Pattern> ImgPattern = getPatternsImg2(folder);
		        System.out.println("Patrones cargados del folder: " + folder);

		        // Crear una instancia de Screen de Sikuli
		        Screen screen = new Screen();
		       
		        // Seleccionar una imagen para buscar en la pantalla (esto depende de tus patrones)
		        String imageName = "case.png";  // Asegúrate de que este nombre esté disponible en ImgPattern
		        Pattern pattern = ImgPattern.get(imageName);  // Obtén el patrón del HashMap usando el nombre de la imagen
		      
		        try {
		        // Verificar si el patrón se encuentra
		        if (pattern != null) {
		            System.out.println("Esperando a que el patrón aparezca..." + imageName);
		            
		            // Esperar hasta que el patrón aparezca en la pantalla durante 10 segundos
		            Match match = screen.wait(pattern, 30);  // Esperar hasta 10 segundos
		          
		            // Si el patrón es encontrado, hacer clic
		            if (match != null) {
		                System.out.println("Patrón encontrado, haciendo clic..."+ imageName);
		                screen.click(match);
		            return true;
		            }
			  
		                else {
		                System.out.println("No encontro el pattern" + imageName);	
		              
		                return false;
		                }
		                }
		        }
		        catch(Exception e) {
		        System.out.println(e);
		       return false;
		        }
				return false;
		  }
	  
	  /*
		 * metodo utilizado para buscar un concurrente haciendo clic
		 * @param folder
		 * @author Alan Perez	 
		 * @date 12/12/2024     */
	  public boolean BusquedaConcurrente1(File folder) {
	// Llamar al método que obtiene los patrones de las imágenes dentro del folder
	        HashMap<String, Pattern> ImgPattern = getPatternsImg2(folder);
	        System.out.println("Patrones cargados del folder: " + folder);
			return false;
	}

	/** Método que obtiene los patrones de las imágenes AbrirSuperUsuario dentro del folder y si se encuenta le da clic
	 * @param folder
	 * @author Rafael Ramos
	 * @date 12/03/2025 */	
	public boolean AbrirSuperUsuario(File folder) {
		// Llamar al método que obtiene los patrones de las imágenes dentro del folder
		HashMap<String, Pattern> ImgPattern = getPatternsImg2(folder);
		System.out.println("Patrones cargados del folder: " + folder);

		// Crear una instancia de Screen de Sikuli
		Screen screen = new Screen();

		// Seleccionar una imagen para buscar en la pantalla (esto depende de tus patrones)
		String imageName = "AbrirNuevoSuperUsuario.png"; // Asegúrate de que este nombre esté disponible en ImgPattern
		Pattern pattern = ImgPattern.get(imageName); // Obtén el patrón del HashMap usando el nombre de la imagen

		try {
			// Verificar si el patrón se encuentra
			if (pattern != null) {
				System.out.println("Esperando a que el patrón aparezca..." + imageName);

				// Esperar hasta que el patrón aparezca en la pantalla durante 10 segundos
				Match match = screen.wait(pattern, 30); // Esperar hasta 10 segundos

				// Si el patrón es encontrado, hacer clic
				if (match != null) {
					System.out.println("Patrón encontrado, haciendo clic..." + imageName);
					screen.click(match);
					testCase.addScreenShotCurrentStep(u, "Se hizo clic en 'AbrirNuevoSuperUsuario'");
					return true;
				}

				else {
					System.out.println("No encontro el pattern" + imageName);
					testCase.addScreenShotCurrentStep(u, "No se encontró el botón para abrir nuevo superusuario");
					return false;
				}
			}
		} catch (Exception e) {
			System.out.println(e);
			testCase.addScreenShotCurrentStep(u, "Error al intentar abrir nuevo superusuario: " + e.getMessage());
			return false;
		}
		return false;
	}

	/** Método que obtiene los patrones de las imágenes botonEncontrar dentro del folder y si se encuenta le da clic
	 * @param folder
	 * @author Rafael Ramos
	 * @date 12/03/2025 */
	public boolean botonEncontrar(File folder) {
		// Llamar al método que obtiene los patrones de las imágenes dentro del folder
		HashMap<String, Pattern> ImgPattern = getPatternsImg2(folder);
		System.out.println("Patrones cargados del folder: " + folder);

		// Crear una instancia de Screen de Sikuli
		Screen screen = new Screen();

		// Seleccionar una imagen para buscar en la pantalla (esto depende de tus patrones)
		String imageName = "encontrarlotes.png"; // Asegúrate de que este nombre esté disponible en ImgPattern
		Pattern pattern = ImgPattern.get(imageName); // Obtén el patrón del HashMap usando el nombre de la imagen

		try {
			// Verificar si el patrón se encuentra
			if (pattern != null) {
				System.out.println("Esperando a que el patrón aparezca..." + imageName);

				// Esperar hasta que el patrón aparezca en la pantalla durante 10 segundos
				Match match = screen.wait(pattern, 30); // Esperar hasta 10 segundos

				// Si el patrón es encontrado, hacer clic
				if (match != null) {
					System.out.println("Patrón encontrado, haciendo clic..." + imageName);
					screen.click(match);
					testCase.addScreenShotCurrentStep(u, "Se hizo clic en el botón 'Encontrar'.");

					return true;
				}

				else {
					System.out.println("No encontro el pattern" + imageName);
					testCase.addScreenShotCurrentStep(u, "No se encontró el patrón 'encontrar'.");
					return false;
				}
			}
		} catch (Exception e) {
			System.out.println(e);
			testCase.addScreenShotCurrentStep(u, "Excepción al buscar patrón 'encontrar': " + e.getMessage());
			return false;
		}
		return false;
	}

	/** Método que obtiene los patrones de las imágenes botonAsientoContable dentro del folder y si se encuenta le da clic
	 * @param folder
	 * @author Rafael Ramos
	 * @date 12/03/2025 */
	public boolean botonAsientoContable(File folder) {
		// Llamar al método que obtiene los patrones de las imágenes dentro del folder
		HashMap<String, Pattern> ImgPattern = getPatternsImg2(folder);
		System.out.println("Patrones cargados del folder: " + folder);

		// Crear una instancia de Screen de Sikuli
		Screen screen = new Screen();

		// Seleccionar una imagen para buscar en la pantalla (esto depende de tus patrones)
		String imageName = "revisarasiento.png"; // Asegúrate de que este nombre esté disponible en ImgPattern
		Pattern pattern = ImgPattern.get(imageName); // Obtén el patrón del HashMap usando el nombre de la imagen

		try {
			// Verificar si el patrón se encuentra
			if (pattern != null) {
				System.out.println("Esperando a que el patrón aparezca..." + imageName);

				// Esperar hasta que el patrón aparezca en la pantalla durante 10 segundos
				Match match = screen.wait(pattern, 30); // Esperar hasta 10 segundos

				// Si el patrón es encontrado, hacer clic
				if (match != null) {
					System.out.println("Patrón encontrado, haciendo clic..." + imageName);
					testCase.addScreenShotCurrentStep(u, "Patrón 'revisarasiento' encontrado. Procediendo a hacer clic.");
					screen.click(match);
					testCase.addScreenShotCurrentStep(u, "Se hizo clic en el botón 'Revisar asiento contable'.");
					return true;
				}

				else {
					System.out.println("No encontro el pattern" + imageName);
					testCase.addScreenShotCurrentStep(u, "No se encontró el patrón 'revisarasiento'.");
					return false;
				}
			}
		} catch (Exception e) {
			System.out.println(e);
			testCase.addScreenShotCurrentStep(u, "Excepción al buscar patrón 'revisarasiento': " + e.getMessage());

			return false;
		}
		return false;
	}
	
	/** Método que obtiene los patrones de las imágenes totaloxpay dentro del folder y si se encuenta le da clic
	 * @param folder
	 * @author Rafael Ramos
	 * @date 12/03/2025 */
	public boolean totaloxpay(File folder) {
		// Llamar al método que obtiene los patrones de las imágenes dentro del folder
		HashMap<String, Pattern> ImgPattern = getPatternsImg2(folder);
		System.out.println("Patrones cargados del folder: " + folder);

		// Crear una instancia de Screen de Sikuli
		Screen screen = new Screen();

		// Seleccionar una imagen para buscar en la pantalla (esto depende de tus patrones)
		String imageName = "totaloxpay.png"; // Asegúrate de que este nombre esté disponible en ImgPattern
		Pattern pattern = ImgPattern.get(imageName); // Obtén el patrón del HashMap usando el nombre de la imagen

		try {
			// Verificar si el patrón se encuentra
			if (pattern != null) {
				System.out.println("Esperando a que el patrón aparezca..." + imageName);

				// Esperar hasta que el patrón aparezca en la pantalla durante 10 segundos
				Match match = screen.wait(pattern, 10); // Esperar hasta 10 segundos
				 testCase.addScreenShotCurrentStep("Patrón encontrado o no encontrado: " + imageName);

				// Si el patrón es encontrado, hacer clic
				if (match != null) {
					System.out.println("Patrón encontrado, haciendo clic..." + imageName);
					screen.click(match);
					testCase.addScreenShotCurrentStep("Clic realizado en el patrón encontrado.");
					return true;
				}

				else {
					System.out.println("No encontro el pattern" + imageName);

					return false;
				}
			}
		} catch (Exception e) {
			System.out.println(e);
			return false;
		}
		return false;
	}

	/**  Método que obtiene los patrones de las imágenes dentro del folder y si lo encuentra le da clic
	 *  @param folder
	 *  @author Rafael Ramos 
	 *  @date 12/03/2025 	 */
	public boolean totalhotpay(File folder) {
		// Llamar al método que obtiene los patrones de las imágenes dentro del folder
		HashMap<String, Pattern> ImgPattern = getPatternsImg2(folder);
		System.out.println("Patrones cargados del folder: " + folder);

		// Crear una instancia de Screen de Sikuli
		Screen screen = new Screen();

		// Seleccionar una imagen para buscar en la pantalla (esto depende de tus patrones)
		String imageName = "totalhotpay.png"; // Asegúrate de que este nombre esté disponible en ImgPattern
		Pattern pattern = ImgPattern.get(imageName); // Obtén el patrón del HashMap usando el nombre de la imagen

		try {
			// Verificar si el patrón se encuentra
			if (pattern != null) {
				System.out.println("Esperando a que el patrón aparezca..." + imageName);

				// Esperar hasta que el patrón aparezca en la pantalla durante 10 segundos
				Match match = screen.wait(pattern, 30); // Esperar hasta 10 segundos

				// Si el patrón es encontrado, hacer clic
				if (match != null) {
					System.out.println("Patrón encontrado, haciendo clic..." + imageName);
					screen.click(match);
					return true;
				}

				else {
					System.out.println("No encontro el pattern" + imageName);

					return false;
				}
			}
		} catch (Exception e) {
			System.out.println(e);
			return false;
		}
		return false;
	}

	/** Método que obtiene los patrones de las imágenes buscarsuperusuario dentro del folder y si se encuenta le da clic
	 * @param folder
	 * @author Rafael Ramos
	 * @date 12/03/2025   */	
	public boolean buscarsuperusuario(File folder) {
		// Llamar al método que obtiene los patrones de las imágenes dentro del folder
		HashMap<String, Pattern> ImgPattern = getPatternsImg2(folder);
		System.out.println("Patrones cargados del folder: " + folder);

		// Crear una instancia de Screen de Sikuli
		Screen screen = new Screen();

		// Seleccionar una imagen para buscar en la pantalla (esto depende de tus patrones)
		String imageName = "buscarsuperusuario.png"; // Asegúrate de que este nombre esté disponible en ImgPattern
		Pattern pattern = ImgPattern.get(imageName); // Obtén el patrón del HashMap usando el nombre de la imagen

		try {
			// Verificar si el patrón se encuentra
			if (pattern != null) {
				System.out.println("Esperando a que el patrón aparezca..." + imageName);

				// Esperar hasta que el patrón aparezca en la pantalla durante 10 segundos
				Match match = screen.wait(pattern, 30); // Esperar hasta 10 segundos

				// Si el patrón es encontrado, hacer clic
				if (match != null) {
					System.out.println("Patrón encontrado, haciendo clic..." + imageName);
					screen.click(match);
					testCase.addScreenShotCurrentStep(u, "Se hizo clic en el campo de búsqueda de superusuario");
					return true;
				}

				else {
					System.out.println("No encontro el pattern" + imageName);
					testCase.addScreenShotCurrentStep(u, "No se encontró el campo de búsqueda de superusuario");

					return false;
				}
			}
		} catch (Exception e) {
			System.out.println(e);
			testCase.addScreenShotCurrentStep(u, "Error al buscar el superusuario: " + e.getMessage());
			return false;
		}
		return false;
	}

	/** metodo utilizado para ejecutar nueva solicitud
	 * @param folder
	 * @author Alan Perez
	 * @date 12/02/2024	 */
	public boolean EjecutarNuevaSolicitud(File folder) {
		// Llamar al método que obtiene los patrones de las imágenes dentro del folder
		HashMap<String, Pattern> ImgPattern = getPatternsImg2(folder);
		System.out.println("Patrones cargados del folder: " + folder);

		// Crear una instancia de Screen de Sikuli
		Screen screen = new Screen();

		// Seleccionar una imagen para buscar en la pantalla (esto depende de tus patrones)
		String imageName = "nuevasolicitud.png"; // Asegúrate de que este nombre esté disponible en ImgPattern
		Pattern pattern = ImgPattern.get(imageName); // Obtén el patrón del HashMap usando el nombre de la imagen

		try {
			// Verificar si el patrón se encuentra
			if (pattern != null) {
				System.out.println("Esperando a que el patrón aparezca..." + imageName);

				// Esperar hasta que el patrón aparezca en la pantalla durante 10 segundos
				Match match = screen.wait(pattern, 30); // Esperar hasta 10 segundos

				// Si el patrón es encontrado, hacer clic
				if (match != null) {
					System.out.println("Patrón encontrado, haciendo clic..." + imageName);
					screen.click(match);
					testCase.addScreenShotCurrentStep("Clic realizado en el patrón encontrado.");
					return true;
				}

				else {
					System.out.println("No encontro el pattern" + imageName);

					return false;
				}
			}
		} catch (Exception e) {
			System.out.println(e);
			return false;
		}
		return false;
	}

	/** metodo utilizado para buscar un concurrente haciendo clic
	 * @param folder
	 * @author Alan Perez
	 * @date 12/12/2024	 */
	public boolean BusquedaConcurrente(File folder) {
		// Llamar al método que obtiene los patrones de las imágenes dentro del folder
		HashMap<String, Pattern> ImgPattern = getPatternsImg2(folder);
		System.out.println("Patrones cargados del folder: " + folder);

		// Crear una instancia de Screen de Sikuli
		Screen screen = new Screen();

		// Seleccionar una imagen para buscar en la pantalla (esto depende de tus patrones)
		String imageName = "puntosbusqueda.png"; // Asegúrate de que este nombre esté disponible en ImgPattern
		Pattern pattern = ImgPattern.get(imageName); // Obtén el patrón del HashMap usando el nombre de la imagen
		testCase.addScreenShotCurrentStep("Se da clic en el botón de busqueda concurrente");
		try {
			// Verificar si el patrón se encuentra
			if (pattern != null) {
				System.out.println("Esperando a que el patrón aparezca..." + imageName);

				// Esperar hasta que el patrón aparezca en la pantalla durante 10 segundos
				Match match = screen.wait(pattern, 30); // Esperar hasta 10 segundos
				testCase.addScreenShotCurrentStep("Patrón encontrado o no encontrado: " + imageName);
				// Si el patrón es encontrado, hacer clic
				if (match != null) {
					System.out.println("Patrón encontrado, haciendo clic..." + imageName);
					screen.click(match);
					testCase.addScreenShotCurrentStep("Clic realizado en el patrón encontrado.");
					return true;
				}

				else {
					System.out.println("No encontro el pattern" + imageName);
					Assert.fail("fallo al encontrar el elemento");
					return false;
				}
			}
		} catch (Exception e) {
			System.out.println(e);
			return false;
		}
		return false;
	}

	/** metodo utilizado para escribir en EBS
	 * @param folder, concurrente
	 * @author Alan Perez
	 * @throws AWTException
	 * @date 12/12/2024	 */
	public boolean typeInEBS(File folder, String concurrente) throws AWTException {
		// TODO Auto-generated method stub
		// Llamar al método que obtiene los patrones de las imágenes dentro del folder
		HashMap<String, Pattern> ImgPattern = getPatternsImg2(folder);
		System.out.println("Patrones cargados del folder: " + folder);
		// Crear una instancia de Screen de Sikuli
		Screen screen = new Screen();

		// Seleccionar una imagen para buscar en la pantalla (esto depende de tus patrones)
		String imageName = "TextBox_Buscar_EBS.png"; // Asegúrate de que este nombre esté disponible en ImgPattern
		Pattern pattern = ImgPattern.get(imageName); // Obtén el patrón del HashMap usando el nombre de la imagen

		Robot robot = new Robot();
		testCase.addScreenShotCurrentStep("Se escribe en el campo");
		try {
			// Verificar si el patrón se encuentra
			if (pattern != null) {
				System.out.println("Esperando a que el patrón aparezca..." + imageName);

				// Esperar hasta que el patrón aparezca en la pantalla durante 10 segundos
				Match match = screen.wait(pattern, 30); // Esperar hasta 10 segundos

				// Si el patrón es encontrado, hacer clic
				if (match != null) {
					System.out.println("Patrón encontrado, haciendo clic..." + imageName);
					screen.click(match);
					testCase.addScreenShotCurrentStep("Clic realizado en el patrón encontrado.");

					// Recorrer cada carácter del texto a escribir
					for (char c : concurrente.toCharArray()) {
						// Simular la pulsación de cada tecla
						int keyCode = KeyEvent.getExtendedKeyCodeForChar(c);
						robot.keyPress(keyCode);
						robot.keyRelease(keyCode);
					}
					testCase.addScreenShotCurrentStep("Texto ingresado: " + concurrente);
					u.hardWait(2);
					return true;
				}

				else {
					System.out.println("No encontro el pattern" + imageName);
					return false;
				}
			}
		} catch (Exception e) {
			System.out.println(e);

			return false;
		}
		return false;
	}

	/** Método que busca un campo de texto en la pantalla usando una imagen y luego escribir una cadena (nombre de superusuario)
	 * @param folder
	 * @param superusuario
	 * @throws AWTException
	 * @author Rafael Ramos
	 * @date 12/03/2025 	 */	
	public boolean typeSuperUsuario(File folder, String superusuario) throws AWTException {
		// TODO Auto-generated method stub
		// Llamar al método que obtiene los patrones de las imágenes dentro del folder
		HashMap<String, Pattern> ImgPattern = getPatternsImg2(folder);
		System.out.println("Patrones cargados del folder: " + folder);
		// Crear una instancia de Screen de Sikuli
		Screen screen = new Screen();

		// Seleccionar una imagen para buscar en la pantalla (esto depende de tus patrones)
		String imageName = "busquedasuperusuario.png"; // Asegúrate de que este nombre esté disponible en ImgPattern
		Pattern pattern = ImgPattern.get(imageName); // Obtén el patrón del HashMap usando el nombre de la imagen

		Robot robot = new Robot();

		try {
			// Verificar si el patrón se encuentra
			if (pattern != null) {
				System.out.println("Esperando a que el patrón aparezca..." + imageName);

				// Esperar hasta que el patrón aparezca en la pantalla durante 10 segundos
				Match match = screen.wait(pattern, 30); // Esperar hasta 10 segundos

				// Si el patrón es encontrado, hacer clic
				if (match != null) {
					System.out.println("Patrón encontrado, haciendo clic..." + imageName);
					screen.click(match);
					u.hardWait(2);
					testCase.addScreenShotCurrentStep(u, "Campo de superusuario encontrado. Ingresando datos: " + superusuario);
					
					// Recorrer cada carácter del texto a escribir
					for (char c : superusuario.toCharArray()) {
						// Simular la pulsación de cada tecla
						int keyCode = KeyEvent.getExtendedKeyCodeForChar(c);
						robot.keyPress(keyCode);
						robot.keyRelease(keyCode);
					}
					u.hardWait(2);
					testCase.addScreenShotCurrentStep(u, "Superusuario ingresado: " + superusuario);
					return true;
				}

				else {
					System.out.println("No encontro el pattern" + imageName);
					testCase.addScreenShotCurrentStep(u, "No se encontró el campo para superusuario");
					return false;
				}
			}
		} catch (Exception e) {
			System.out.println(e);
			testCase.addScreenShotCurrentStep(u, "Error al ingresar el superusuario: " + e.getMessage());
			return false;
		}
		return false;
	}

	/** Método que obtiene los patrones de las imágenes typeInServicio dentro del folder
	 * @param folder
	 * @param servicio
	 * @throws AWTException
	 * @author Rafael Ramos
	 * @date 12/03/2025 	 */	
	public boolean typeInServicio(File folder, String servicio) throws AWTException {
		// TODO Auto-generated method stub
		// Llamar al método que obtiene los patrones de las imágenes dentro del folder
		HashMap<String, Pattern> ImgPattern = getPatternsImg2(folder);
		System.out.println("Patrones cargados del folder: " + folder);
		// Crear una instancia de Screen de Sikuli
		Screen screen = new Screen();

		// Seleccionar una imagen para buscar en la pantalla (esto depende de tus patrones)
		String imageName = "TextBox_Buscar_Servicio.png"; // Asegúrate de que este nombre esté disponible en ImgPattern
		Pattern pattern = ImgPattern.get(imageName); // Obtén el patrón del HashMap usando el nombre de la imagen

		Robot robot = new Robot();

		try {
			// Verificar si el patrón se encuentra
			if (pattern != null) {
				System.out.println("Esperando a que el patrón aparezca..." + imageName);

				// Esperar hasta que el patrón aparezca en la pantalla durante 10 segundos
				Match match = screen.wait(pattern, 30); // Esperar hasta 10 segundos

				// Si el patrón es encontrado, hacer clic
				if (match != null) {
					System.out.println("Patrón encontrado, haciendo clic..." + imageName);
					screen.click(match);
					testCase.addScreenShotCurrentStep("Clic realizado en el patrón encontrado.");
					// Recorrer cada carácter del texto a escribir
					for (char c : servicio.toCharArray()) {
						// Simular la pulsación de cada tecla
						int keyCode = KeyEvent.getExtendedKeyCodeForChar(c);
						robot.keyPress(keyCode);
						robot.keyRelease(keyCode);
					}
					testCase.addScreenShotCurrentStep("Texto ingresado: " + servicio);
					u.hardWait(2);
					return true;
				}

				else {
					System.out.println("No encontro el pattern" + imageName);
					return false;
				}
			}
		} catch (Exception e) {
			System.out.println(e);

			return false;
		}
		return false;
	}

	/** Método que obtiene los patrones de las imágenes typeInFecha dentro del folder
	 * @param folder
	 * @param fechaI
	 * @throws AWTException
	 * @author Rafael Ramos
	 * @date 12/03/2025 	 */	
	public boolean typeInFecha(File folder, String fechaI) throws AWTException {
		// TODO Auto-generated method stub
		// Llamar al método que obtiene los patrones de las imágenes dentro del folder
		HashMap<String, Pattern> ImgPattern = getPatternsImg2(folder);
		System.out.println("Patrones cargados del folder: " + folder);
		// Crear una instancia de Screen de Sikuli
		Screen screen = new Screen();

		// Seleccionar una imagen para buscar en la pantalla (esto depende de tus patrones)
		String imageName = "fechaIni.png"; // Asegúrate de que este nombre esté disponible en ImgPattern
		Pattern pattern = ImgPattern.get(imageName); // Obtén el patrón del HashMap usando el nombre de la imagen

		Robot robot = new Robot();

		try {
			// Verificar si el patrón se encuentra
			if (pattern != null) {
				System.out.println("Esperando a que el patrón aparezca..." + imageName);

				// Esperar hasta que el patrón aparezca en la pantalla durante 10 segundos
				Match match = screen.wait(pattern, 30); // Esperar hasta 10 segundos

				// Si el patrón es encontrado, hacer clic
				if (match != null) {
					System.out.println("Patrón encontrado, haciendo clic..." + imageName);
					screen.click(match);

					// Recorrer cada carácter del texto a escribir
					for (char c : fechaI.toCharArray()) {
						// Simular la pulsación de cada tecla
						int keyCode = KeyEvent.getExtendedKeyCodeForChar(c);
						robot.keyPress(keyCode);
						robot.keyRelease(keyCode);
					}
					u.hardWait(3);
					return true;
				}

				else {
					System.out.println("No encontro el pattern" + imageName);
					return false;
				}
			}
		} catch (Exception e) {
			System.out.println(e);

			return false;
		}
		return false;
	}
	
	/*
	 * metodo utilizado para escribir en EBS
	 * @param folder, concurrente
	 * @author Alan Perez
	 * @throws AWTException
	 * @date 12/12/2025	 */
	public boolean typeInFormaPago(String formaPago) throws FindFailed, AWTException {
		
        String pathImg = sk.PatternPath("EBS\\"+"EBS");
    	File FolderPath = new File(pathImg);
       
        // Seleccionar una imagen para buscar en la pantalla (esto depende de tus patrones)
        String imageName = "formaPago.png";  // Asegúrate de que este nombre esté disponible en ImgPattern
        //Pattern pattern = ImgPattern.get(imageName);  // Obtén el patrón del HashMap usando el nombre de la imagen
        Pattern pattern = new Pattern(FolderPath +"\\formaPago.png").similar((float).50);        
        
        // Verificar si el patrón se e
        if (sk.screen.exists(pattern,30)!=null) {
            System.out.println("Esperando a que el patrón aparezca..."+imageName);

          
                System.out.println("Patrón encontrado, haciendo clic..."+ imageName);
                sk.screen.wait(pattern).highlight(2, "red");
                sk.screen.click();
                sk.screen.type(formaPago);
                testCase.addScreenShotCurrentStep(u, "Ingresa forma de pago");
                u.hardWait(2);
                return true;
        }
        else {
        	System.out.println("No se encontro la imagen");
        	Assert.fail("fallo al encontrar el elemento");
        	return false;
        	}
  }

	/** metodo utilizado para buscar una concurrente
	 * @param folder
	 * @author Alan Perez
	 * @throws AWTException
	 * @date 12/03/2025	 */
	public boolean BuscarConcurrente(File folder) throws AWTException {
		// TODO Auto-generated method stub
		// Llamar al método que obtiene los patrones de las imágenes dentro del folder
		HashMap<String, Pattern> ImgPattern = getPatternsImg2(folder);
		System.out.println("Patrones cargados del folder: " + folder);

		// Crear una instancia de Screen de Sikuli
		Screen screen = new Screen();
		Robot robot = new Robot();
		// Seleccionar una imagen para buscar en la pantalla (esto depende de tus patrones)
		String imageName = "BuscarEBS_Solicitud.png"; // Asegúrate de que este nombre esté disponible en ImgPattern
		Pattern pattern = ImgPattern.get(imageName); // Obtén el patrón del HashMap usando el nombre de la imagen

		try {
			// Verificar si el patrón se encuentra
			if (pattern != null) {
				System.out.println("Esperando a que el patrón aparezca..." + imageName);

				// Esperar hasta que el patrón aparezca en la pantalla durante 10 segundos
				Match match = screen.wait(pattern, 30); // Esperar hasta 10 segundos

				// Si el patrón es encontrado, hacer clic
				if (match != null) {
					System.out.println("Patrón encontrado, haciendo clic..." + imageName);
					screen.click(match);
					testCase.addScreenShotCurrentStep("Clic realizado en el patrón encontrado.");
					robot.keyPress(KeyEvent.VK_ENTER);
					return true;
				}

				else {
					System.out.println("No encontro el pattern" + imageName);
					return false;
				}
			}
		} catch (Exception e) {
			System.out.println(e);
			return false;
		}
		return false;
	}

	/** Método que obtiene los patrones de las imágenes puntosbusqueda dentro del folder
	 * @param folder
	 * @throws AWTException
	 * @author Rafael Ramos
	 * @date 12/03/2025 	 */	
	public boolean puntosbusquedamanual(File folder) throws AWTException {
		// TODO Auto-generated method stub
		// Llamar al método que obtiene los patrones de las imágenes dentro del folder
		HashMap<String, Pattern> ImgPattern = getPatternsImg2(folder);
		System.out.println("Patrones cargados del folder: " + folder);

		// Crear una instancia de Screen de Sikuli
		Screen screen = new Screen();
		Robot robot = new Robot();
		// Seleccionar una imagen para buscar en la pantalla (esto depende de tus patrones)
		String imageName = "puntosbusqueda.png"; // Asegúrate de que este nombre esté disponible en ImgPattern
		Pattern pattern = ImgPattern.get(imageName); // Obtén el patrón del HashMap usando el nombre de la imagen

		try {
			// Verificar si el patrón se encuentra
			if (pattern != null) {
				System.out.println("Esperando a que el patrón aparezca..." + imageName);

				// Esperar hasta que el patrón aparezca en la pantalla durante 10 segundos
				Match match = screen.wait(pattern, 30); // Esperar hasta 10 segundos

				// Si el patrón es encontrado, hacer clic
				if (match != null) {
					System.out.println("Patrón encontrado, haciendo clic..." + imageName);
					screen.click(match);
					robot.keyPress(KeyEvent.VK_ENTER);
					return true;
				}

				else {
					System.out.println("No encontro el pattern" + imageName);
					return false;
				}
			}
		} catch (Exception e) {
			System.out.println(e);
			return false;
		}
		return false;
	}

	/** metodo utilizado para ejecutar una solicitud
	 * @param folder
	 * @author Alan Perez
	 * @throws AWTException
	 * @date 12/03/2025 	 */
	public boolean EjecutarSolicitud(File folder) throws AWTException {
		// TODO Auto-generated method stub
		// Llamar al método que obtiene los patrones de las imágenes dentro del folder
		HashMap<String, Pattern> ImgPattern = getPatternsImg2(folder);
		System.out.println("Patrones cargados del folder: " + folder);

		// Crear una instancia de Screen de Sikuli
		Screen screen = new Screen();
		Robot robot = new Robot();
		// Seleccionar una imagen para buscar en la pantalla (esto depende de tus patrones)
		String imageName = "ejecutarSolicitud.png"; // Asegúrate de que este nombre esté disponible en ImgPattern
		Pattern pattern = ImgPattern.get(imageName); // Obtén el patrón del HashMap usando el nombre de la imagen

		try {
			// Verificar si el patrón se encuentra
			if (pattern != null) {
				System.out.println("Esperando a que el patrón aparezca..." + imageName);

				// Esperar hasta que el patrón aparezca en la pantalla durante 10 segundos
				Match match = screen.wait(pattern, 30); // Esperar hasta 10 segundos

				// Si el patrón es encontrado, hacer clic
				if (match != null) {
					System.out.println("Patrón encontrado, haciendo clic..." + imageName);
					screen.click(match);
					testCase.addScreenShotCurrentStep("Clic realizado en el patrón encontrado.");
					return true;
				}

				else {
					System.out.println("No encontro el pattern" + imageName);
					return false;
				}
			}
		} catch (Exception e) {
			System.out.println(e);
			return false;
		}
		return false;
	}

	/** metodo utilizado para encontrar una solicitud
	 * @param folder
	 * @author Alan Perez
	 * @date 12/03/2025	 */
	public boolean noHacerMasSolicitudes(File folder) {
		// TODO Auto-generated method stub
		// Llamar al método que obtiene los patrones de las imágenes dentro del folder
		HashMap<String, Pattern> ImgPattern = getPatternsImg2(folder);
		System.out.println("Patrones cargados del folder: " + folder);

		// Crear una instancia de Screen de Sikuli
		Screen screen = new Screen();

		// Seleccionar una imagen para buscar en la pantalla (esto depende de tus patrones)
		String imageName = "no.png"; // Asegúrate de que este nombre esté disponible en ImgPattern
		Pattern pattern = ImgPattern.get(imageName); // Obtén el patrón del HashMap usando el nombre de la imagen

		try {
			// Verificar si el patrón se encuentra
			if (pattern != null) {
				System.out.println("Esperando a que el patrón aparezca..." + imageName);

				// Esperar hasta que el patrón aparezca en la pantalla durante 10 segundos
				Match match = screen.wait(pattern, 30); // Esperar hasta 10 segundos

				// Si el patrón es encontrado, hacer clic
				if (match != null) {
					System.out.println("Patrón encontrado, haciendo clic..." + imageName);
					screen.click(match);
					testCase.addScreenShotCurrentStep("Clic realizado en el patrón encontrado.");
					return true;
				}

				else {
					System.out.println("No encontro el pattern" + imageName);
					return false;
				}
			}
		} catch (Exception e) {
			System.out.println(e);
			return false;
		}
		return false;
	}

	/** método que obtiene los patrones de las imágenes EncontrarSolicitud dentro del folder
	 * @param folder
	 * @author Rafael Ramos
	 * @date 12/03/2025	*/
		public boolean EncontrarSolicitud(File folder) {
		// TODO Auto-generated method stub
		// Llamar al método que obtiene los patrones de las imágenes dentro del folder
		HashMap<String, Pattern> ImgPattern = getPatternsImg2(folder);
		System.out.println("Patrones cargados del folder: " + folder);

		// Crear una instancia de Screen de Sikuli
		Screen screen = new Screen();

		// Seleccionar una imagen para buscar en la pantalla (esto depende de tus patrones)
		String imageName = "encontrarSolicitud1.png"; // Asegúrate de que este nombre esté disponible en ImgPattern
		Pattern pattern = ImgPattern.get(imageName); // Obtén el patrón del HashMap usando el nombre de la imagen

		try {
			// Verificar si el patrón se encuentra
			if (pattern != null) {
				System.out.println("Esperando a que el patrón aparezca..." + imageName);

				// Esperar hasta que el patrón aparezca en la pantalla durante 10 segundos
				Match match = screen.wait(pattern, 30); // Esperar hasta 10 segundos

				// Si el patrón es encontrado, hacer clic
				if (match != null) {
					System.out.println("Patrón encontrado, haciendo clic..." + imageName);
					screen.click(match);
					testCase.addScreenShotCurrentStep("Clic realizado en el patrón encontrado.");
					return true;
				}

				else {
					System.out.println("No encontro el pattern" + imageName);
					return false;
				}
			}
		} catch (Exception e) {
			System.out.println(e);
			return false;
		}
		return false;
	}

	/*
	 * metodo utilizado para enviar cierta cantidad de tab y escribir un texto
	 * @param numberoftab, text
	 * @author Alan Perez
	 * @throws AWTException, InterruptedException 	
	 * @date 12/12/2024 */
	public void TabAndType(int numberOfTabs, String text) throws AWTException, InterruptedException {
		// TODO Auto-generated method stub
		Robot robot = new Robot();

		// Enviar las tabulaciones (TAB)
		for (int i = 0; i < numberOfTabs; i++) {
			robot.keyPress(KeyEvent.VK_TAB); // Simula la tecla TAB
			robot.keyRelease(KeyEvent.VK_TAB); // Libera la tecla TAB
			u.hardWait(1); // Opcional: pequeña pausa para evitar que el Robot sea demasiado rápido
		}
		// Escribe el texto
		for (char c : text.toCharArray()) {
			robot.keyPress(KeyEvent.getExtendedKeyCodeForChar(c)); // Simula la tecla de cada carácter
			robot.keyRelease(KeyEvent.getExtendedKeyCodeForChar(c)); // Libera la tecla
			u.hardWait(1); // Pausa opcional entre caracteres
		}
		u.hardWait(5);
	}

	/*
	 * metodo utilizado para enviar cierta cantidad de enter
	 * @param numberoftab
	 * @author Alan Perez
	 * @throws AWTException, InterruptedException
	 * @date 12/12/2024 	 */
	public void tab(int numberOfTab) throws AWTException, InterruptedException {
		Robot robot = new Robot();
		for (int i = 0; i < numberOfTab; i++) {
			robot.keyPress(KeyEvent.VK_ENTER); // Simula la tecla TAB
			robot.keyRelease(KeyEvent.VK_ENTER); // Libera la tecla TAB
			u.hardWait(1); // Opcional: pequeña pausa para evitar que el Robot sea demasiado rápido
		}
	}

	/** metodo utilizado para enviar cierta cantidad de tab
	 * @param numberoftab
	 * @author Alan Perez
	 * @throws AWTException, InterruptedException
	 * @date 12/03/2025	 */
	public void tab2(int numberOfTab) throws AWTException, InterruptedException {
		Robot robot = new Robot();
		for (int i = 0; i < numberOfTab; i++) {
			robot.keyPress(KeyEvent.VK_TAB); // Simula la tecla TAB
			robot.keyRelease(KeyEvent.VK_TAB); // Libera la tecla TAB
			u.hardWait(1); // Opcional: pequeña pausa para evitar que el Robot sea demasiado rápido
			//testCase.addScreenShotCurrentStep("Clic realizado en el patrón encontrado.");
		}
	}

	/*
	 * metodo utilizado para enviar el numero de veces la tecla fecha abajo
	 * @param numberoftab
	 * @author Alan Perez
	 * @throws AWTException, InterruptedException
	 * @date 12/12/2024  	 */
	public void KeyDown(int numberOfTab) throws AWTException, InterruptedException {
		Robot robot = new Robot();
		for (int i = 0; i < numberOfTab; i++) {
			robot.keyPress(KeyEvent.VK_DOWN); // Simula la tecla TAB
			robot.keyRelease(KeyEvent.VK_DOWN); // Libera la tecla TAB
			u.hardWait(1); // Opcional: pequeña pausa para evitar que el Robot sea demasiado rápido
		}
	}

	/*
	 * metodo utilizado para dar enter en los parametros
	 * @param numberofenter
	 * @author Alan Perez
	 * @throws AWTException 	
	 * @date 12/12/2024 */
	public boolean EnterParam(int NumberOfEnter) throws AWTException {
		Robot robot = new Robot();
		if (robot != null) {
			robot.keyPress(KeyEvent.VK_ENTER); // Simula la tecla TAB
			robot.keyRelease(KeyEvent.VK_ENTER); // Libera la tecla TAB

			return true;
		} else {
			System.out.println("No se dio enter");
			return false;
		}
	}

	/*
	 * metodo utilizado para dar un tab y despues enter
	 * @param numberofenter
	 * @author Alan Perez
	 * @throws AWTException, InterruptedException 	 
	 * @date 12/12/2024	*/
	public void Enter(int numberOfEnter) throws AWTException, InterruptedException {
		// TODO Auto-generated method stub
		Robot robot = new Robot();
		robot.keyPress(KeyEvent.VK_TAB); // Simula la tecla TAB
		robot.keyRelease(KeyEvent.VK_TAB); // Libera la tecla TAB
		// Enviar las tabulaciones (TAB)
		for (int i = 0; i < numberOfEnter; i++) {
			robot.keyPress(KeyEvent.VK_ENTER); // Simula la tecla TAB
			robot.keyRelease(KeyEvent.VK_ENTER); // Libera la tecla TAB
			u.hardWait(1); // Opcional: pequeña pausa para evitar que el Robot sea demasiado rápido
		}
	}

	/*
	 * metodo utilizado para hacer clic en el buscador de concurrentes
	 * @param folder
	 * @author Alan Perez	
	 * @date 12/12/2024 */
	public boolean ConcurrenteName(File folder) {

		// TODO Auto-generated method stub
		// Llamar al método que obtiene los patrones de las imágenes dentro del folder
		HashMap<String, Pattern> ImgPattern = getPatternsImg2(folder);
		System.out.println("Patrones cargados del folder: " + folder);

		// Crear una instancia de Screen de Sikuli
		Screen screen = new Screen();

		// Seleccionar una imagen para buscar en la pantalla (esto depende de tus patrones)
		String imageName = "nombreclick.png"; // Asegúrate de que este nombre esté disponible en ImgPattern
		Pattern pattern = ImgPattern.get(imageName); // Obtén el patrón del HashMap usando el nombre de la imagen

		try {
			// Verificar si el patrón se encuentra
			if (pattern != null) {
				System.out.println("Esperando a que el patrón aparezca..." + imageName);

				// Esperar hasta que el patrón aparezca en la pantalla durante 10 segundos
				Match match = screen.wait(pattern, 30); // Esperar hasta 10 segundos

				// Si el patrón es encontrado, hacer clic
				if (match != null) {
					System.out.println("Patrón encontrado, haciendo clic..." + imageName);
					screen.click(match);
					u.hardWait(5); 
					return true;
				}

				else {
					System.out.println("No encontro el pattern" + imageName);

					return false;
				}
			}
		} catch (Exception e) {
			System.out.println(e);
			return false;
		}
		return false;
	}

	/*
	 * metodo utilizado para dar clic en el parametro servicio
	 * @param folder
	 * @author Alan Perez 	 
	 * @date 12/12/2024*/
	public boolean ParametroServicio(File folder) {
		// TODO Auto-generated method stub
		// Llamar al método que obtiene los patrones de las imágenes dentro del folder
		HashMap<String, Pattern> ImgPattern = getPatternsImg2(folder);
		System.out.println("Patrones cargados del folder: " + folder);

		// Crear una instancia de Screen de Sikuli
		Screen screen = new Screen();

		// Seleccionar una imagen para buscar en la pantalla (esto depende de tus patrones)
		String imageName = "Servicio_EBS.png"; // Asegúrate de que este nombre esté disponible en ImgPattern
		Pattern pattern = ImgPattern.get(imageName); // Obtén el patrón del HashMap usando el nombre de la imagen

		try {
			// Verificar si el patrón se encuentra
			if (pattern != null) {
				System.out.println("Esperando a que el patrón aparezca..." + imageName);

				// Esperar hasta que el patrón aparezca en la pantalla durante 10 segundos
				Match match = screen.wait(pattern, 30); // Esperar hasta 10 segundos

				// Si el patrón es encontrado, hacer clic
				if (match != null) {
					System.out.println("Patrón encontrado, haciendo clic..." + imageName);
					screen.click(match);
					u.hardWait(5);
					Robot robot = new Robot();
					robot.keyPress(KeyEvent.VK_TAB);
					robot.keyRelease(KeyEvent.VK_TAB);
					robot.keyPress(KeyEvent.VK_TAB);
					robot.keyRelease(KeyEvent.VK_TAB);
					// Libera la tecla TAB
					return true;
				}

				else {
					System.out.println("No encontro el pattern" + imageName);
					Assert.fail("fallo al encontrar el elemento");
					return false;
				}
			}
		} catch (Exception e) {
			System.out.println(e);
			return false;
		}
		return false;
	}

	/** metodo utilizado para escribir algun texto
	 * @param texto
	 * @author Alan Perez
	 * @throws AWTException, InterruptedException
	 * @date 12/03/2025 	 */
	public void Escribir(String texto) throws InterruptedException, AWTException {
		// Escribe el texto
		Robot robot = new Robot();
		for (char c : texto.toCharArray()) {
			robot.keyPress(KeyEvent.getExtendedKeyCodeForChar(c)); // Simula la tecla de cada carácter
			robot.keyRelease(KeyEvent.getExtendedKeyCodeForChar(c)); // Libera la tecla
			testCase.addScreenShotCurrentStep("Se ingresa texto: ");
			u.hardWait(2); // Pausa opcional entre caracteres
		}
		u.hardWait(5);
	}

	/*
	 * metodo utilizado para enviar el servicio
	 * @param servicio
	 * @author Alan Perez
	 * @throws AWTException, InterruptedException 	
	 * @date 12/12/2024 */
	public boolean EnvioParametro(String servicio) throws InterruptedException, AWTException {
		// TODO Auto-generated method stub
		if (servicio != null) {
			Escribir(servicio);
			Robot robot = new Robot();
			robot.keyPress(KeyEvent.VK_ENTER);
			robot.keyRelease(KeyEvent.VK_ENTER);
			return true;
		}
		return false;
	}

	/*
	 * metodo utilizado para dar enter correspondiente
	 * @param number
	 * @author Alan Perez
	 * @throws AWTException 
	 * @date 12/12/2024*/
	public void Enter3(int number) throws AWTException {
		Robot robot = new Robot();
		robot.keyPress(KeyEvent.VK_ENTER);
		robot.keyRelease(KeyEvent.VK_ENTER);
	}

	/*
	 * metodo utilizado para escribir el Informe correspondiente
	 * @param Informe
	 * @author Alan Perez
	 * @throws AWTException, InterruptedException 	
	 * @date 12/12/2024 */
	public void parametrosInforme(String Informe) throws AWTException, InterruptedException {
		Escribir(Informe);

	}

	/*
	 * metodo utilizado para escribir la fechaPagoDesde correspondiente
	 * @param fecha
	 * @author Alan Perez
	 * @throws AWTException, InterruptedException 	
	 * @date 12/12/2024 */
	public void FechaPagoDesde(String fecha) throws AWTException, InterruptedException {
		Escribir(fecha);
	}

	/*
	 * metodo utilizado para escribir la fechaPagoHasta correspondiente
	 * @param fecha
	 * @author Alan Perez
	 * @throws AWTException, InterruptedException 	
	 * @date 12/12/2024 */
	public void FechaPagoHasta(String fecha) throws AWTException, InterruptedException {
		Escribir(fecha);
	}

	/*
	 * metodo utilizado para escribir la fecha correspondiente
	 * @param fecha
	 * @author Alan Perez
	 * @throws AWTException, InterruptedException 	
	 * @date 12/12/2024 */
	public void FechaPago(String fecha) throws AWTException, InterruptedException {
		Escribir(fecha);
	}

	/*
	 * metodo utilizado para escribir yes/no correspondiente
	 * @param YesNo
	 * @author Alan Perez
	 * @throws AWTException, InterruptedException 	
	 * @date 12/12/2024 */
	public void IncluirFacturas(String YesNo) throws AWTException, InterruptedException {
		Escribir(YesNo);
	}

	/*
	 * metodo utilizado para escribir yes/no correspondiente
	 * @param YesNo
	 * author Alan Perez
	 * @throws AWTException, InterruptedException 	
	 * @date 12/12/2024 */
	public void OpcionResumen(String YesNo) throws AWTException, InterruptedException {
		Escribir(YesNo);
	}

	/*
	 * metodo utilizado para escribir yes/no correspondiente
	 * @param YesNo
	 * @author Alan Perez 
	 * @throws AWTException, InterruptedException 	
	 * @date 12/12/2024 */
	public void TotalProveedor(String YesNo) throws AWTException, InterruptedException {
		Escribir(YesNo);
	}

	/*
	 * metodo utilizado para escribir la plaza correspondiente
	 * @param com
	 * @author Alan Perez
	 * @throws AWTException, InterruptedException 	
	 * @date 12/12/2024 */
	public void Plaza(String plaza) throws InterruptedException, AWTException {
		Escribir(plaza);
	}

	/*
	 * metodo utilizado para escribir la compañia correspondiente
	 * @param com
	 * @author Alan Perez
	 * @throws AWTException, InterruptedException 	
	 * @date 12/12/2024 */
	public void Compañia(String com) throws InterruptedException, AWTException {
		Escribir(com);
	}

	/*
	 * metodo utilizado para hacer clic en el parametro compañia 99
	 * @param folder
	 * @author Alan Perez
	 * @throws AWTException, InterruptedException, FindFailed
	 * @date 12/12/2024	 */
	// parametros
	public void Compañia99Clic(File folder) throws InterruptedException, AWTException, FindFailed {
		// TODO Auto-generated method stub
		// Llamar al método que obtiene los patrones de las imágenes dentro del folder
		HashMap<String, Pattern> ImgPattern = getPatternsImg2(folder);
		System.out.println("Patrones cargados del folder: " + folder);

		// Crear una instancia de Screen de Sikuli
		Screen screen = new Screen();

		// Seleccionar una imagen para buscar en la pantalla (esto depende de tus patrones)
		String imageName = "Compañia99_EBS.png"; // Asegúrate de que este nombre esté disponible en ImgPattern
		Pattern pattern = ImgPattern.get(imageName); // Obtén el patrón del HashMap usando el nombre de la imagen

		// Verificar si el patrón se encuentra
		if (pattern != null) {
			System.out.println("Esperando a que el patrón aparezca..." + imageName);

			// Esperar hasta que el patrón aparezca en la pantalla durante 10 segundos
			Match match = screen.wait(pattern, 30); // Esperar hasta 10 segundos

			System.out.println("Patrón encontrado, haciendo clic..." + imageName);
			screen.doubleClick(match);
			testCase.addScreenShotCurrentStep("Clic realizado en el patrón encontrado.");
			u.hardWait(5);

		} else {
			Assert.fail("fallo al encontrar el elemento");
		}
	}

	/*
	 * metodo utilizado para dar click en compañia
	 * @param folder
	 * @author Alan Perez
	 * @throws AWTException, InterruptedException, Finfailed
	 * @date 12/12/2024*/
	public void CompañiaClic(File folder) throws InterruptedException, AWTException, FindFailed {
	        // Crear una instancia de Screen de Sikuli
	        Screen screen = new Screen();
	       
	        // Seleccionar una imagen para buscar en la pantalla (esto depende de tus patrones)
	        String imageName = "compania.png";  // Asegúrate de que este nombre esté disponible en ImgPattern
	        Pattern pattern = ImgPattern.get(imageName);  // Obtén el patrón del HashMap usando el nombre de la imagen
	        
	        
	        // Verificar si el patrón se encuentra
	        if (pattern != null) {
	            System.out.println("Esperando a que el patrón aparezca..."+imageName);
	            
	            // Esperar hasta que el patrón aparezca en la pantalla durante 10 segundos
	            Match match = screen.wait(pattern, 30);  // Esperar hasta 10 segundos
	         
	                System.out.println("Patrón encontrado, haciendo clic..."+ imageName);
	                screen.doubleClick(match);
                 u.hardWait(5);
             
	        }
	        else {
	        	Assert.fail("fallo al encontrar el elemento");
	        }
	  }

	/*
	 * metodo utilizado para realizar tab
	 * @param folder
	 * @author Alan Perez
	 * @throws AWTException, InterruptedException 	
	 * @date 12/12/2024 */
	public void Enter2(int numberOfEnter) throws AWTException, InterruptedException {
		// TODO Auto-generated method stub
		Robot robot = new Robot();
		for (int i = 0; i < numberOfEnter; i++) {
			robot.keyPress(KeyEvent.VK_ENTER); // Simula la tecla TAB
			robot.keyRelease(KeyEvent.VK_ENTER); // Libera la tecla TAB
			u.hardWait(1); // Opcional: pequeña pausa para evitar que el Robot sea demasiado rápido
		}
	}

	/*
	 * metodo utilizado para realizar clic en el boton si, despues de abrir las opciones
	 * @param folder
	 * @author Alan Perez
	 * @throws FindFailed 	
	 * @date 12/12/2024 */
	public void ParamSi(File folder) throws FindFailed {
		// TODO Auto-generated method stub
		// Llamar al método que obtiene los patrones de las imágenes dentro del folder
		HashMap<String, Pattern> ImgPattern = getPatternsImg2(folder);
		System.out.println("Patrones cargados del folder: " + folder);

		// Crear una instancia de Screen de Sikuli
		Screen screen = new Screen();

		// Seleccionar una imagen para buscar en la pantalla (esto depende de tus patrones)
		String imageName = "YesParam_EBS.png"; // Asegúrate de que este nombre esté disponible en ImgPattern
		Pattern pattern = ImgPattern.get(imageName); // Obtén el patrón del HashMap usando el nombre de la imagen

		System.out.println(pattern);
		// Verificar si el patrón se encuentra
		if (pattern != null) {
			System.out.println("Esperando a que el patrón aparezca..." + imageName);

			// Esperar hasta que el patrón aparezca en la pantalla durante 10 segundos
			Match match = screen.wait(pattern, 30); // Esperar hasta 10 segundos

			System.out.println("Patrón encontrado, haciendo clic..." + imageName);
			screen.click(match);
		} else {
			System.out.println("No se encontro la imagen");
			Assert.fail("fallo al encontrar el elemento");
		}
	}

	/*
	 * metodo utilizado para realizar clic en el boton vencimiento
	 * @param folder
	 * @author Alan Perez
	 * @throws FindFailed 	
	 * @date 12/12/2024 */
	public void FieldVencimiento(File folder) throws FindFailed {
		// TODO Auto-generated method stub
		// Llamar al método que obtiene los patrones de las imágenes dentro del folder
		HashMap<String, Pattern> ImgPattern = getPatternsImg2(folder);
		System.out.println("Patrones cargados del folder: " + folder);

		// Crear una instancia de Screen de Sikuli
		Screen screen = new Screen();

		// Seleccionar una imagen para buscar en la pantalla (esto depende de tus patrones)
		String imageName = "PagarVencimiento_EBS.png"; // Asegúrate de que este nombre esté disponible en ImgPattern
		Pattern pattern = ImgPattern.get(imageName); // Obtén el patrón del HashMap usando el nombre de la imagen

		// Verificar si el patrón se encuentra
		if (pattern != null) {
			System.out.println("Esperando a que el patrón aparezca..." + imageName);

			// Esperar hasta que el patrón aparezca en la pantalla durante 10 segundos
			Match match = screen.wait(pattern, 30); // Esperar hasta 10 segundos

			System.out.println("Patrón encontrado, haciendo clic..." + imageName);
			screen.click(match);
		} else {
			System.out.println("No se encontro la imagen");
			Assert.fail("fallo al encontrar el elemento");
		}
	}

	/*
	 * metodo utilizado para realizar clic en el boton aceptar
	 * @param
	 * @author Alan Perez
	 * @throws FindFailed 	 
	 * @date 12/12/2024*/
	public void btnAceptar(File folder) throws FindFailed {
		// Llamar al método que obtiene los patrones de las imágenes dentro del folder
		HashMap<String, Pattern> ImgPattern = getPatternsImg2(folder);
		System.out.println("Patrones cargados del folder: " + folder);

		// Crear una instancia de Screen de Sikuli
		Screen screen = new Screen();

		// Seleccionar una imagen para buscar en la pantalla (esto depende de tus patrones)
		String imageName = "AceptarEBS.png"; // Asegúrate de que este nombre esté disponible en ImgPattern
		Pattern pattern = ImgPattern.get(imageName); // Obtén el patrón del HashMap usando el nombre de la imagen

		// Verificar si el patrón se encuentra
		if (pattern != null) {
			System.out.println("Esperando a que el patrón aparezca..." + imageName);

			// Esperar hasta que el patrón aparezca en la pantalla durante 10 segundos
			Match match = screen.wait(pattern, 30); // Esperar hasta 10 segundos

			System.out.println("Patrón encontrado, haciendo clic..." + imageName);
			screen.click(match);
		} else {
			System.out.println("No se encontro la imagen");
			Assert.fail("fallo al encontrar el elemento");
		}
	}

	/** metodo utilizado para dar clic btn Aceptar de ventana emergente
	 * @param folder
	 * @author Mariana Avitia
	 * @throws FindFailed, AWTException
	 * @date 12/03/2025	 */
	public boolean btnAceptarEmergente() throws FindFailed, AWTException {
		
        String pathImg = sk.PatternPath("EBS\\"+"EBS");
    	File FolderPath = new File(pathImg);
       
        // Seleccionar una imagen para buscar en la pantalla (esto depende de tus patrones)
        String imageName = "aceptarE.png";  // Asegúrate de que este nombre esté disponible en ImgPattern
        //Pattern pattern = ImgPattern.get(imageName);  // Obtén el patrón del HashMap usando el nombre de la imagen
        Pattern pattern = new Pattern(FolderPath +"\\okError.png").similar((float).55);        
        
        // Verificar si el patrón se e
        if (sk.screen.exists(pattern,30)!=null) {
            System.out.println("Esperando a que el patrón aparezca..."+imageName);

          
                System.out.println("Patrón encontrado, haciendo clic..."+ imageName);
                sk.screen.wait(pattern).highlight(2, "red");
                sk.screen.click(pattern);
                u.hardWait(2);
                return true;
        }
        else {
        	System.out.println("No se encontro la imagen");
        	Assert.fail("fallo al encontrar el elemento");
        	return false;
        	}
 	}
	
	/*
	 * metodo utilizado para realizar clic en el boton aceptar
	 * @param folder
	 * @author Alan Perez
	 * @throws FindFailed, AWTException 
	 * @date 12/12/2024	 */
	public boolean btnAceptar() throws FindFailed, AWTException {
		
        String pathImg = sk.PatternPath("EBS\\"+"EBS");
    	File FolderPath = new File(pathImg);
       
        // Seleccionar una imagen para buscar en la pantalla (esto depende de tus patrones)
        String imageName = "aceptarE.png";  // Asegúrate de que este nombre esté disponible en ImgPattern
        //Pattern pattern = ImgPattern.get(imageName);  // Obtén el patrón del HashMap usando el nombre de la imagen
        Pattern pattern = new Pattern(FolderPath +"\\aceptarcerrar.png").similar((float).50);        
        
        // Verificar si el patrón se e
        if (sk.screen.exists(pattern,30)!=null) {
            System.out.println("Esperando a que el patrón aparezca..."+imageName);

          
                System.out.println("Patrón encontrado, haciendo clic..."+ imageName);
                sk.screen.wait(pattern).highlight(2, "red");
                sk.screen.click(pattern);
                u.hardWait(2);
                return true;
        }
        else {
        	System.out.println("No se encontro la imagen");
        	Assert.fail("fallo al encontrar el elemento");
        	return false;
        	}
	}
	
	/** metodo utilizado para realizar clic en el boton actualizar forma pago (lapiz)
	 * @param folder 
	 * @author Mariana Avitia
	 * @throws FindFailed, AWTException
	 * @date 12/03/2025	 */
	public boolean btnActualizarFormaPago() throws FindFailed, AWTException {
		
        String pathImg = sk.PatternPath("EBS\\"+"EBS");
    	File FolderPath = new File(pathImg);
       
        // Seleccionar una imagen para buscar en la pantalla (esto depende de tus patrones)
        String imageName = "actualizar.png";  // Asegúrate de que este nombre esté disponible en ImgPattern
        //Pattern pattern = ImgPattern.get(imageName);  // Obtén el patrón del HashMap usando el nombre de la imagen
        Pattern pattern = new Pattern(FolderPath +"\\actualizar.png").similar((float).55);        
        
        // Verificar si el patrón se e
        if (sk.screen.exists(pattern,30)!=null) {
            System.out.println("Esperando a que el patrón aparezca..."+imageName);

          
                System.out.println("Patrón encontrado, haciendo clic..."+ imageName);
                sk.screen.wait(pattern).highlight(2, "red");
                sk.screen.click(pattern);
                testCase.addScreenShotCurrentStep(u, "Click en actualizar forma de pago");
                u.hardWait(2);
                return true;
        }
        else {
        	System.out.println("No se encontro la imagen");
        	Assert.fail("fallo al encontrar el elemento");
        	testCase.addScreenShotCurrentStep("No se encontro boton para actualizar forma de pago");
        	return false;
        	}
        
	}
	
	/** metodo utilizado para realizar clic en el boton actualizar forma pago (lapiz)
	 * @param folder
	 * @author Mariana Avitia
	 * @throws FindFailed, AWTException
	 * @date 12/03/2025   */
	public boolean btnAplicarCambiosFormaPago() throws FindFailed, AWTException {
		
        String pathImg = sk.PatternPath("EBS\\"+"EBS");
    	File FolderPath = new File(pathImg);
       
        // Seleccionar una imagen para buscar en la pantalla (esto depende de tus patrones)
        String imageName = "btnAplicar.png";  // Asegúrate de que este nombre esté disponible en ImgPattern
        //Pattern pattern = ImgPattern.get(imageName);  // Obtén el patrón del HashMap usando el nombre de la imagen
        Pattern pattern = new Pattern(FolderPath +"\\btnAplicar.png").similar((float).55);        
        
        // Verificar si el patrón se e
        if (sk.screen.exists(pattern,30)!=null) {
            System.out.println("Esperando a que el patrón aparezca..."+imageName);

          
                System.out.println("Patrón encontrado, haciendo clic..."+ imageName);
                sk.screen.wait(pattern).highlight(2, "red");
                sk.screen.click(pattern);
                testCase.addScreenShotCurrentStep(u, "Aplicar cambios");
                u.hardWait(2);
                return true;
        }
        else {
        	testCase.addScreenShotCurrentStep("No se encontro la imagen");
        	Assert.fail("fallo al encontrar el elemento");
        	return false;
        	}
  	}
	
	/*
	 * metodo utilizado para realizar clic en el boton aceptar
	 * @param folder 
	 * @author Alan Perez
	 * @throws FindFailed, AWTException 	 
	 * @date 12/12/2024*/
	public boolean btnEjecutar() throws FindFailed, AWTException {
		
        String pathImg = sk.PatternPath("EBS\\"+"EBS");
    	File FolderPath = new File(pathImg);
       
        // Seleccionar una imagen para buscar en la pantalla (esto depende de tus patrones)
        String imageName = "ejecutarSoli.png";  // Asegúrate de que este nombre esté disponible en ImgPattern
        //Pattern pattern = ImgPattern.get(imageName);  // Obtén el patrón del HashMap usando el nombre de la imagen
        Pattern pattern = new Pattern(FolderPath +"\\ejecutar.png").similar((float).55);        
        
        // Verificar si el patrón se e
        if (sk.screen.exists(pattern,30)!=null) {
            System.out.println("Esperando a que el patrón aparezca..."+imageName);

          
                System.out.println("Patrón encontrado, haciendo clic..."+ imageName);
                sk.screen.wait(pattern).highlight(2, "red");
                sk.screen.click(pattern);
                u.hardWait(2);
                return true;
        }
        else {
        	System.out.println("No se encontro la imagen");
        	Assert.fail("fallo al encontrar el elemento");
        	return false;
        	}
}
	
	/*
	 * metodo utilizado para realizar uso de la tecla keydown
	 * @param folder
	 * @author Alan Perez
	 * @throws InterruptedException, AWTException 	
	 * @date 12/12/2024 */
	public void keydown() throws AWTException, InterruptedException {
		// TODO Auto-generated method stub
		Robot robot = new Robot();
		u.hardWait(5);
		// Simular la tecla de flecha hacia abajo (KeyEvent.VK_DOWN)
		robot.keyPress(KeyEvent.VK_DOWN); // Simula la presionar la tecla de flecha hacia abajo
		robot.keyRelease(KeyEvent.VK_DOWN);
		u.hardWait(1); // Opcional: pequeña pausa para evitar que el Robot sea demasiado rápido
	}

	/*
	 * metodo utilizado para realizar enter
	 * @param folder
	 * @author Alan Perez
	 * @throws InterruptedException, AWTException 
	 * @date 12/12/2024	 */
	public void keyEnterCD() throws AWTException, InterruptedException {
		// TODO Auto-generated method stub
		Robot robot = new Robot();
		u.hardWait(5);
		// Simular la tecla de flecha hacia abajo (KeyEvent.VK_DOWN)
		robot.keyPress(KeyEvent.VK_ENTER); // Simula la presionar la tecla de flecha hacia abajo
		robot.keyRelease(KeyEvent.VK_ENTER);
		u.hardWait(1); // Opcional: pequeña pausa para evitar que el Robot sea demasiado rápido
	}

	/*
	 * metodo utilizado para escribir la cuenta
	 * @param cuenta
	 * @author Alan Perez
	 * @throws InterruptedException, AWTException 	
	 * @date 12/12/2024 */
	public void CuentaCorriente(String cuenta) throws InterruptedException, AWTException {
		// TODO Auto-generated method stub
		Escribir(cuenta);
	}

	/*
	 * metodo utilizado para hacer clic en tipo reporte
	 * @param folder
	 * @author Alan Perez
	 * @throws InterruptedException, FindFailed 	
	 * @date 12/12/2024 */
	public void ClicTipoReporte(File folder) throws FindFailed, InterruptedException {
		// Llamar al método que obtiene los patrones de las imágenes dentro del folder
		HashMap<String, Pattern> ImgPattern = getPatternsImg2(folder);
		System.out.println("Patrones cargados del folder: " + folder);

		// Crear una instancia de Screen de Sikuli
		Screen screen = new Screen();
		u.hardWait(5);
		// Seleccionar una imagen para buscar en la pantalla (esto depende de tus patrones)
		String imageName = "ReporteA_EBS.png"; // Asegúrate de que este nombre esté disponible en ImgPattern
		Pattern pattern = ImgPattern.get(imageName); // Obtén el patrón del HashMap usando el nombre de la imagen

		// Verificar si el patrón se encuentra
		if (pattern != null) {
			System.out.println("Esperando a que el patrón aparezca..." + imageName);

			// Esperar hasta que el patrón aparezca en la pantalla durante 10 segundos
			Match match = screen.wait(pattern, 30); // Esperar hasta 10 segundos

			System.out.println("Patrón encontrado, haciendo clic..." + imageName);
			screen.doubleClick(match);
		} else {
			System.out.println("No se encontro la imagen");
			Assert.fail("fallo al encontrar el elemento");
		}
	}

	/*
	 * metodo utilizado para escribir algun texto en el parametro correspondiente
	 * @param string
	 * @author Alan Perez
	 * @throws InterruptedException, AWTException 	
	 * @date 12/12/2024 */
	public void TipoDeSalida(String string) throws InterruptedException, AWTException {
		// TODO Auto-generated method stub
		u.hardWait(7);
		Escribir(string);
	}

	/** metodo utilizado para validar el output de los concurrentes
	 * @param folder
	 * @author Alan Perez
	 * @throws InterruptedException, FindFailed
	 * @date 12/12/2024	 */
	public boolean ValidarOutput(File folder) throws FindFailed, InterruptedException {
		// TODO Auto-generated method stub
		// Llamar al método que obtiene los patrones de las imágenes dentro del folder
		HashMap<String, Pattern> ImgPattern = getPatternsImg2(folder);
		System.out.println("Patrones cargados del folder: " + folder); 

		// Crear una instancia de Screen de Sikuli
		Screen screen = new Screen();
		u.hardWait(5);
		// Seleccionar una imagen para buscar en la pantalla (esto depende de tus patrones)
		String imageName = "VisualizarOutput_EBS.png"; // Asegúrate de que este nombre esté disponible en ImgPattern
		Pattern pattern = ImgPattern.get(imageName); // Obtén el patrón del HashMap usando el nombre de la imagen

		// Verificar si el patrón se encuentra
		if (pattern != null) {
			System.out.println("Esperando a que el patrón aparezca..." + imageName);

			// Esperar hasta que el patrón aparezca en la pantalla durante 10 segundos
			Match match = screen.wait(pattern, 30); // Esperar hasta 10 segundos

			System.out.println("Patrón encontrado, haciendo clic..." + imageName);
			screen.click(match);
			u.hardWait(5);
			testCase.addScreenShotCurrentStep("Clic realizado en el patrón encontrado output vacio validado.");
			return true;
		} else {
			System.out.println("No se encontro la imagen");

			return false;
		}
	}

	/** metodo utilizado para dar clic en refrescar data
	 * @param folder
	 * @author Alan Perez
	 * @throws InterruptedException, FindFailed
	 * @date 12/12/2024 	 */
	public boolean RefreshData(File folder) throws InterruptedException, FindFailed {
		// TODO Auto-generated method stub
		// Llamar al método que obtiene los patrones de las imágenes dentro del folder
		HashMap<String, Pattern> ImgPattern = getPatternsImg2(folder);
		System.out.println("Patrones cargados del folder: " + folder);

		// Crear una instancia de Screen de Sikuli
		Screen screen = new Screen();
		u.hardWait(8);
		// Seleccionar una imagen para buscar en la pantalla (esto depende de tus patrones)
		String imageName = "RefrescarDatos_EBS1.png"; // Asegúrate de que este nombre esté disponible en ImgPattern
		Pattern pattern = ImgPattern.get(imageName); // Obtén el patrón del HashMap usando el nombre de la imagen

		// Verificar si el patrón se encuentra
		if (pattern != null) {
			System.out.println("Esperando a que el patrón aparezca..." + imageName);

			// Esperar hasta que el patrón aparezca en la pantalla durante 10 segundos
			Match match = screen.wait(pattern, 30); // Esperar hasta 10 segundos
			u.hardWait(7);
			System.out.println("Patrón encontrado, haciendo clic..." + imageName);
			screen.click(match);
			testCase.addScreenShotCurrentStep("Clic realizado en el patrón encontrado.");
			return true;
		} else {

			System.out.println("No se encontro la imagen");
			Assert.fail("fallo al encontrar el elemento");
			return false;
		}
	}

	/*
	 * metodo utilizado para dar clic en el output para validar el excel
	 * @param folder
	 * @author Alan Perez
	 * @throws InterruptedException, FindFailed 
	 * @date 12/12/2024	 */
	public boolean AbrirXLS(File folder) throws InterruptedException, FindFailed {
		// TODO Auto-generated method stub
		// Llamar al método que obtiene los patrones de las imágenes dentro del folder
		HashMap<String, Pattern> ImgPattern = getPatternsImg2(folder);
		System.out.println("Patrones cargados del folder: " + folder);

		// Crear una instancia de Screen de Sikuli
		Screen screen = new Screen();
		u.hardWait(5);
		// Seleccionar una imagen para buscar en la pantalla (esto depende de tuspatrones)
		String imageName = "AbrirReporteFEMCO_CARTAS.png"; // Asegúrate de que este nombre esté disponible en ImgPattern
		Pattern pattern = ImgPattern.get(imageName); // Obtén el patrón del HashMap usando el nombre de la imagen

		// Verificar si el patrón se encuentra
		if (pattern != null) {
			System.out.println("Esperando a que el patrón aparezca..." + imageName);

			// Esperar hasta que el patrón aparezca en la pantalla durante 10 segundos
			Match match = screen.wait(pattern, 30); // Esperar hasta 10 segundos

			System.out.println("Patrón encontrado, haciendo clic..." + imageName);
			screen.click(match);
			testCase.addScreenShotCurrentStep("Clic realizado en el patrón encontrado.");
			return true;
		} else {
			System.out.println("No se encontro la imagen");

			return false;
		}
	}

	/*
	 * metodo utilizado para dar clic en el param distribucion
	 * @param folder
	 * @author Alan Perez
	 * @throws InterruptedException, FindFailed
	 * @date 12/12/2024	 */
	public void DistribucionBus(File folder) throws InterruptedException, FindFailed {
		// TODO Auto-generated method stub
		// Llamar al método que obtiene los patrones de las imágenes dentro del folder
		HashMap<String, Pattern> ImgPattern = getPatternsImg2(folder);
		System.out.println("Patrones cargados del folder: " + folder);

		// Crear una instancia de Screen de Sikuli
		Screen screen = new Screen();
		u.hardWait(5);
		// Seleccionar una imagen para buscar en la pantalla (esto depende de tus
		// patrones)
		String imageName = "Buscar_EBSDistribucion.png"; // Asegúrate de que este nombre esté disponible en ImgPattern
		Pattern pattern = ImgPattern.get(imageName); // Obtén el patrón del HashMap usando el nombre de la imagen

		// Verificar si el patrón se encuentra
		if (pattern != null) {
			System.out.println("Esperando a que el patrón aparezca..." + imageName);

			// Esperar hasta que el patrón aparezca en la pantalla durante 10 segundos
			Match match = screen.wait(pattern, 30); // Esperar hasta 10 segundos

			System.out.println("Patrón encontrado, haciendo clic..." + imageName);
			screen.click(match);
			testCase.addScreenShotCurrentStep("Clic realizado en el patrón encontrado.");
		} else {
			System.out.println("No se encontro la imagen");
			Assert.fail("fallo al encontrar el elemento");
		}
	}

	/** metodo utilizado para dar clic en ver
	 * @param folder
	 * @author Mariana Avitia
	 * @throws AWTException 
	 * @date 12/03/2025	 */
public boolean clickVer(File folder) throws AWTException {
	// TODO Auto-generated method stub
	// Llamar al método que obtiene los patrones de las imágenes dentro del folder
    HashMap<String, Pattern> ImgPattern = getPatternsImg2(folder);
    System.out.println("Patrones cargados del folder: " + folder);

    // Crear una instancia de Screen de Sikuli
    Screen screen = new Screen();
    // Seleccionar una imagen para buscar en la pantalla (esto depende de tus patrones)
    String imageName = "ver.png";  // Asegúrate de que este nombre esté disponible en ImgPattern
    Pattern pattern = ImgPattern.get(imageName);  // Obtén el patrón del HashMap usando el nombre de la imagen
    
    try {
    // Verificar si el patrón se encuentra
    if (pattern != null) {
        System.out.println("Esperando a que el patrón aparezca..."+imageName);
        
        // Esperar hasta que el patrón aparezca en la pantalla durante 10 segundos
        Match match = screen.wait(pattern, 30);  // Esperar hasta 10 segundos
       
        // Si el patrón es encontrado, hacer clic
        if (match != null) {
            System.out.println("Patrón encontrado, haciendo clic..."+ imageName);
            screen.click(match); 
            return true;
        }
  
            else {
            System.out.println("No encontro el pattern" + imageName);
            return false;
            }	                }
    }
    catch(Exception e) {
    System.out.println(e);
    return false;
    }
	return false;
}

/*
 * metodo utilizado para validar que exista el campo Integrador en tabla Detalles del provedor 
 * @param folder 
 * @author Alan Perez
 * @throws FindFailed, AWTException
 * @date 12/12/2024 */
public boolean existeIntegrador() throws FindFailed, AWTException {
	
    String pathImg = sk.PatternPath("EBS\\"+"EBS");
	File FolderPath = new File(pathImg);
   
    // Seleccionar una imagen para buscar en la pantalla (esto depende de tus patrones)
    String imageName = "campoIntegrador.png";  // Asegúrate de que este nombre esté disponible en ImgPattern
    //Pattern pattern = ImgPattern.get(imageName);  // Obtén el patrón del HashMap usando el nombre de la imagen
    Pattern pattern = new Pattern(FolderPath +"\\campoIntegrador.png").similar((float).80);        
    
    // Verificar si el patrón se e
    if (sk.screen.exists(pattern,30)!=null) {
        System.out.println("Esperando a que el patrón aparezca..."+imageName);

      
            System.out.println("Patrón encontrado, haciendo clic..."+ imageName);
            sk.screen.wait(pattern).highlight(2, "red");
            u.hardWait(2);
            Assert.assertTrue(true, "Se encontro el campo Integrador");
            return true;
    }
    else {
    	System.out.println("No se encontro el campo integrador");
    	Assert.fail("No se encontro el campo integrador");
    	return false;
    	}
    
}

/** metodo utilizado para validar que exista el checkbox Integrador en Actualizar Proveedor Relacionado a Forma de Pago
 * @param folder 
 * @author Mariana Avitia
 * @throws FindFailed, AWTException 
 * @date 12/03/2025 */
public boolean existeCheckBoxIntegrador() throws FindFailed, AWTException {
	
    String pathImg = sk.PatternPath("EBS\\"+"EBS");
	File FolderPath = new File(pathImg);
   
    // Seleccionar una imagen para buscar en la pantalla (esto depende de tus patrones)
    String imageName = "integradorChecked.png and integradorUnchecked";  // Asegúrate de que este nombre esté disponible en ImgPattern
    //Pattern pattern = ImgPattern.get(imageName);  // Obtén el patrón del HashMap usando el nombre de la imagen
    Pattern pattern = new Pattern(FolderPath +"\\integradorChecked.png").similar((float).80); 
    Pattern pattern2 = new Pattern (FolderPath +"\\integradorUnchecked.png").similar((float).80);
    
    // Verificar si el patrón se e
    if (sk.screen.exists(pattern,30)!=null || sk.screen.exists(pattern2,30)!=null) {
        System.out.println("Esperando a que el patrón aparezca..."+imageName);
      
            System.out.println("Patrón encontrado, haciendo clic..."+ imageName);
            sk.screen.wait(pattern).highlight(2, "red");
            sk.screen.wait(pattern2).highlight(2, "red");
            u.hardWait(2);
            Assert.assertTrue(true, "Se encontro el campo Integrador");
            testCase.addScreenShotCurrentStep(u, "Checkbox integrador");
            return true;
    }
    else {
    	testCase.addScreenShotCurrentStep("No se encontro el campo integrador");
    	Assert.fail("No se encontro el campo integrador");
    	return false;
    	}
    
}

/** metodo utilizado para dar click en checkbox Integrador en Actualizar Proveedor Relacionado a Forma de Pago y habilitarlo
 * @param folder
 * @author Mariana Avitia
 * @throws FindFailed, AWTException
 * @date 12/03/2025 */
public boolean checkIntegrador() throws FindFailed, AWTException {
	
    String pathImg = sk.PatternPath("EBS\\"+"EBS");
	File FolderPath = new File(pathImg);
   
    // Seleccionar una imagen para buscar en la pantalla (esto depende de tus patrones)
    String imageName = "integradorChecked2.png and integradorUnchecked2.png";  // Asegúrate de que este nombre esté disponible en ImgPattern
    //Pattern pattern = ImgPattern.get(imageName);  // Obtén el patrón del HashMap usando el nombre de la imagen
    Pattern pattern = new Pattern(FolderPath +"\\integradorChecked2.png").similar((float).80); 
    Pattern pattern2 = new Pattern (FolderPath +"\\integradorUnchecked2.png").similar((float).80);
    
    // Verificar si el patrón se encuentre
    if (sk.screen.exists(pattern,30)!=null) {
        System.out.println("Esperando a que el patrón aparezca..."+imageName);
      
            System.out.println("Patrón encontrado, haciendo clic..."+ imageName);
            sk.screen.wait(pattern).highlight(2, "red");
            sk.screen.doubleClick();
            u.hardWait(2);
            Assert.assertTrue(true, "Se encontro el campo Integrador ya activado");
            return true;
    }
    else if (sk.screen.exists(pattern2,30)!=null) {
        System.out.println("Esperando a que el patrón aparezca..."+imageName);
        
        System.out.println("Patrón encontrado, haciendo clic..."+ imageName);
        sk.screen.wait(pattern2).highlight(2, "red");
        sk.screen.click();
        u.hardWait(2);
        Assert.assertTrue(true, "Se encontro el campo Integrador ya activado");
        return true;
    }
    else {
    	System.out.println("No se encontro el campo integrador");
    	Assert.fail("No se encontro el campo integrador");
    	return false;
    	}
  }

/** metodo utilizado para dar click en el checkbox Integrador en Actualizar Proveedor Relacionado a Forma de Pago y deshabilitarlo
 * @param folder 
 * @author Mariana Avitia
 * @throws FindFailed, AWTException
 * @date 12/03/2025 */
public boolean uncheckIntegrador() throws FindFailed, AWTException {
	
    String pathImg = sk.PatternPath("EBS\\"+"EBS");
	File FolderPath = new File(pathImg);
   
    // Seleccionar una imagen para buscar en la pantalla (esto depende de tus patrones)
    String imageName = "integradorChecked.png and integradorUnchecked";  // Asegúrate de que este nombre esté disponible en ImgPattern
    //Pattern pattern = ImgPattern.get(imageName);  // Obtén el patrón del HashMap usando el nombre de la imagen
    Pattern pattern = new Pattern(FolderPath +"\\integradorChecked2.png").similar((float).80); 
    Pattern pattern2 = new Pattern (FolderPath +"\\integradorUnchecked2.png").similar((float).80);
    
 // Verificar si el patrón se encuentre
    if (sk.screen.exists(pattern2,30)!=null) {
        System.out.println("Esperando a que el patrón aparezca..."+imageName);
      
            System.out.println("Patrón encontrado, haciendo clic..."+ imageName);
            sk.screen.wait(pattern2).highlight(2, "red");
            sk.screen.doubleClick();
            testCase.addScreenShotCurrentStep(u, "Checkbox integrador");
            u.hardWait(2);
            Assert.assertTrue(true, "Se encontro el campo Integrador ya activado");
            return true;
    }
    else if (sk.screen.exists(pattern,30)!=null) {
        System.out.println("Esperando a que el patrón aparezca..."+imageName);
        
        System.out.println("Patrón encontrado, haciendo clic..."+ imageName);
        sk.screen.wait(pattern).highlight(2, "red");
        sk.screen.click();
        testCase.addScreenShotCurrentStep(u, "Checkbox integrador");
        u.hardWait(2);
        Assert.assertTrue(true, "Se encontro el campo Integrador ya activado");
        return true;
    }
    else {
    	testCase.addScreenShotCurrentStep("No se encontro el campo integrador");
    	Assert.fail("No se encontro el campo integrador");
    	return false;
    	}
}

/** metodo utilizado para validar el mensaje de cambios aplicados en Proveedor Forma de Pago
 * @param folder
 * @author Mariana Avitia
 * @throws FindFailed, AWTException
 * @date 12/03/2025 */
public boolean mensajeCambiosAplicadosProveedorFormaPago() throws FindFailed, AWTException {
	
    String pathImg = sk.PatternPath("EBS\\"+"EBS");
	File FolderPath = new File(pathImg);
   
    // Seleccionar una imagen para buscar en la pantalla (esto depende de tus patrones)
    String imageName = "mensajeConfirmacion";  // Asegúrate de que este nombre esté disponible en ImgPattern
    //Pattern pattern = ImgPattern.get(imageName);  // Obtén el patrón del HashMap usando el nombre de la imagen
    Pattern pattern = new Pattern(FolderPath +"\\mensajeConfirmacion.png").similar((float).80); 
    
    // Verificar si el patrón se e
    if (sk.screen.exists(pattern,30)!=null) {
        System.out.println("Esperando a que el patrón aparezca..."+imageName);
      
            System.out.println("Patrón encontrado, haciendo clic..."+ imageName);
            sk.screen.wait(pattern).highlight(2, "red");
            u.hardWait(2);
            Assert.assertTrue(true, "Cambios aplicados");
            testCase.addScreenShotCurrentStep(u, "Se encontro mensaje");
            return true;
    }
    else {
    	testCase.addScreenShotCurrentStep("No se encontro el campo integrador");
    	Assert.fail("No se encontro el campo integrador");
    	return false;
    	}
	}
}
