package WM.om;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;

import modelo.TestCase;
import utils.selenium.SeleniumUtil;

import java.util.Locale;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;




import java.time.Month;


public class Tableau {
		
	TestCase testCase;
	SeleniumUtil u;
	//Identificacion de objetos en las paginas Web y se asignan a una variable
	By userInput = By.xpath("//input[@name='username']");
	By passwordInput = By.xpath("//input[@name='password']");
	By loginButton = By.xpath("//button[@tb-test-id='button-signin']");
	By accountMenu = By.xpath("//button[@data-tb-test-id='flyout-list-menu-Button']");
	By signoutButton = By.xpath("//div[@data-tb-test-id='flyout-list-menu-signOut-MenuItem']");
	By ordenCompra = By.xpath("(//div//h3//span//div//span[text() = 'Orden Compra']/ancestor::div/following-sibling::div[@class='CFContent']//span)[1]");
	By iframeData = By.xpath("//iframe[@title='Data Visualization']");
	By iframe2 = By.xpath("//iframe[@id='extension_frame_26']");
	By iframe3 = By.xpath("//iframe[@id='extension_frame_34']");
	By iframe4 = By.xpath("//iframe[@id='extension_frame_45']");
	By iframeContent = By.xpath("//iframe[@id='extension-content-frame']");
	By todo = By.xpath("//a[@title='(All)']/preceding-sibling::input[1] | //a[@title='(Todo)']/preceding-sibling::input[1]");
	By purchaseOrder1xpath;
	By inputPO =By.xpath("//textarea[@aria-autocomplete='list']");
	By checkboxOrden = By.xpath("//input[@type='checkbox']");
	//By ocCanvasImg = By.xpath("//div[@class='tab-tvTLSpacer tvimagesNS']//canvas[@height='48']");
	//By botonHover = By.xpath("");
	By btnAceptar = By.xpath("//button[@title='Apply'] | //button[@title='Aplicar']");
	By todoCasillaOC = By.xpath("(//div[@class='tab-vizHeaderWrapper']//div[text()='Todos'])[1]");
	By todoCasillaRSP = By.xpath("");
	By botonMasOC = By.xpath("//span[@class='tab-tvLevelDrillDown tab-levelDrillingIcon tvDrillDown tab-widget']");
	By botonMasRSP = By.xpath("//span[@class='tab-tvLevelDrillDown tab-levelDrillingIcon tvDrillDown tab-widget']");
	//By canvas = By.xpath("(//div[@class='tab-vizHeaderHolderWrapper']/div[1])[8]");
	By validaOC1; 
	By validaOC2; 
	By validaOC3; 
	
	By CheckMarkOC = By.xpath ("(//div[@class='tvimagesContainer'])[43]");
	By CheckMarkAño = By.xpath ("(//div[@class='tvimagesContainer'])[52]");
	By CheckMarkMes = By.xpath ("(//div[@class='tvimagesContainer'])[61]");
	By CheckMarkDia= By.xpath ("(//div[@class='tvimagesContainer'])[70]");
	
	By ComboBoxOC = By.xpath ("(//span[@class='tabComboBoxButton'])[5]");
	By ComboOC = By.xpath("//span[@class='tabComboBoxName' and (contains(text(),'Confirmacion') or contains(text(),'Orden') or contains(text(),'Emision') or contains(text(),'Entrega') or contains(text(),'Cierre'))]");
	OrdersInbound oi = new OrdersInbound(testCase);
	
	LocalDateTime fechaHoraInicial;
	By fechaHoraXpath = By.xpath(
	    "//div[contains(@class,'tab-vizHeaderWrapper') and contains(@aria-label,'Fecha Actualizacion')]"
	);
	
	
	public Tableau(TestCase testCase, SeleniumUtil u) {
		this.testCase=testCase;
		this.u=u;
	}

	/** LoginTableau
	* Inicia sesión en el aplicativo web de Tableau
	*@autor Sergio Robles Ramos
	*@param
	*	-urlTableau (Cadena): URL del aplicatvo Tableau
	*	-userTableau (Cadena): Nombre de usuario para hacer login en tableau
	*	-passwordTableau (Cadena): Contraseña para hacer login en Tableau
	*@return void: Este método no retorna ningún valor
	*@date 09/Diciembre/2025
	*/	
	public void loginTableau (String urlTableau, String userTableau, String passwordTableau) {
		u.get(urlTableau);
		u.waitForLoadPage();
		u.waitWebElement(userInput, 180, 1);
		u.sendKeys(userInput, userTableau);
		u.waitWebElement(passwordInput, 180, 1);
		u.sendKeys(passwordInput, passwordTableau);
		testCase.addScreenShotCurrentStep(u, "Se colocan las credenciales para iniciar sesión en Tableau");
		u.waitWebElement(loginButton, 180, 1);
		u.click(loginButton);
		u.waitForLoadPage();
		u.waitWebElement(iframeData, 60, 1);
		WebElement iframe = u.getDriver().findElement(iframeData);
		u.getDriver().switchTo().frame(iframe);
		u.waitWebElement(ordenCompra, 60, 1);
		testCase.addScreenShotCurrentStep(u, "Validar que se muestra la pantalla inicial de Tableau");
		u.getDriver().switchTo().defaultContent();
	}
	
	/** obtenerHoraTableau
	* Obtiene la fecha y hora del aplicativo tableau
	*@autor Sergio Robles Ramos
	*@param
	*Este método no recibe parámetros
	*@return void: Este método no retorna ningún valor
	*@date 30/Enero/2026
	*/	
	
	public LocalDateTime obtenerHoraTableau() {

	    WebElement iframe;
	    WebElement fechaHoraElemento;

	    iframe = u.getDriver().findElement(iframeData);
	    u.getDriver().switchTo().frame(iframe);

	    u.waitWebElement(fechaHoraXpath, 60, 1);
	    fechaHoraElemento = u.getDriver().findElement(fechaHoraXpath);

	    String ariaInicial = fechaHoraElemento.getAttribute("aria-label");
	    LocalDateTime fechaHoraInicial = convertirFechaDesdeAria(ariaInicial);

	    System.out.println("Fecha/Hora inicial Tableau: " + fechaHoraInicial);

	    u.getDriver().switchTo().defaultContent();

	    return fechaHoraInicial;
	}

	/** esperarActualizacionTableau
	* Compara la hora inicial del script con la hora que muestra el tableau para que cuando la hora del tableau sea mayor, avance el script
	*@autor Sergio Robles Ramos
	*@param
	*	-fechaHoraInicial (Fecha): Fecha y hora al iniciar el script
	*@return void: Este método no retorna ningún valor
	*@date 30/Enero/2026
	*/	
	public void esperarActualizacionTableau(LocalDateTime fechaHoraInicial) {

	    WebElement iframe;
	    WebElement fechaHoraElemento;

	    long inicio = System.currentTimeMillis();
	    long timeout = 45 * 60 * 1000;

	    while (System.currentTimeMillis() - inicio < timeout) {

	        u.getDriver().switchTo().defaultContent();
	        u.getDriver().navigate().refresh();

	        try {
	            Thread.sleep(15000);
	        } catch (InterruptedException e) {
	            e.printStackTrace();
	        }

	        iframe = u.getDriver().findElement(iframeData);
	        u.getDriver().switchTo().frame(iframe);

	        u.waitWebElement(fechaHoraXpath, 60, 1);
	        fechaHoraElemento = u.getDriver().findElement(fechaHoraXpath);

	        String ariaNueva = fechaHoraElemento.getAttribute("aria-label");
	        LocalDateTime fechaHoraNueva = convertirFechaDesdeAria(ariaNueva);

	        System.out.println("Inicial: " + fechaHoraInicial);
	        System.out.println("Actual: " + fechaHoraNueva);

	        if (fechaHoraNueva.isAfter(fechaHoraInicial)) {
	            System.out.println("La fecha/hora se actualizó correctamente.");
	            break;
	        }
	    }

	    u.getDriver().switchTo().defaultContent();
	}

	/** obtenerHoraDesdeSysdate
	* Obtiene la fecha y hora del sistema
	*@autor Sergio Robles Ramos
	*@param
	*Este método no recibe parámetros
	*@return void: Este método no retorna ningún valor
	*@date 30/Enero/2026
	*/
	
	public LocalDateTime obtenerHoraDesdeSysdate() {

	    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
	    LocalDateTime sysdate = LocalDateTime.now();

	    // Normalizamos al MISMO formato que Tableau
	    String fechaFormateada = sysdate.format(formatter);
	    LocalDateTime fechaHoraInicial = LocalDateTime.parse(fechaFormateada, formatter);

	    System.out.println("Fecha/Hora inicial desde Sysdate: " + fechaHoraInicial);

	    return fechaHoraInicial;
	}
	/** convertirFechaDesdeAria
	* Compara la hora inicial del script con la hora que muestra el tableau para que cuando la hora del tableau sea mayor, avance el script
	*@autor Sergio Robles Ramos
	*@param
	*	-ariaLabel (Cadena): Xpath del contenedor con la fecha y hora de actualización de Tableau
	*@date 30/Enero/2026
	*/	
	private LocalDateTime convertirFechaDesdeAria(String ariaLabel) {

	    String fechaTexto = ariaLabel.split(",")[0].trim();
	    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
	    return LocalDateTime.parse(fechaTexto, formatter);
	}
	
	/** busquedaTableau
	 * realiza la búsqueda de hasta tres órdenes de compra en Tableau,
	 * permitiendo consultar múltiples registros dentro de la plataforma
	 *@autor Sergio Robles Ramos
	 *@param
	 *  -String purchaseOrder1 (Cadena): Primera orden de compra a consultar
	 *  -String purchaseOrder2 (Cadena): Segunda orden de compra a consultar
	 *  -String purchaseOrder3 (Cadena): Tercera orden de compra a consultar
	 *
	 *@return void: Este método no retorna ningún valor
	 *@date 11/Diciembre/2025
	 */
	
	public void busquedaTableau (String purchaseOrder1, String purchaseOrder2, String purchaseOrder3) {

	    WebElement iframe = u.getDriver().findElement(iframeData);
	    u.getDriver().switchTo().frame(iframe);

	    try {
	        Thread.sleep(30000);
	    } catch (InterruptedException e) {
	        e.printStackTrace();
	    }

	    u.waitWebElement(ordenCompra, 200, 1);
	    u.click(ordenCompra);

	    try {

	        u.waitWebElement(inputPO, 10, 1);
	        u.click(inputPO);

	        u.waitWebElement(todo, 30, 1);
	        u.click(todo);

	        u.sendKeys(inputPO, purchaseOrder1);
	        try { Thread.sleep(5000); } catch (InterruptedException e) { e.printStackTrace(); }
	        u.waitWebElement(checkboxOrden, 10, 1);
	        u.click(checkboxOrden);
	        u.clear(inputPO);

	        u.click(inputPO);
	        u.sendKeys(inputPO, purchaseOrder2);
	        try { Thread.sleep(5000); } catch (InterruptedException e) { e.printStackTrace(); }
	        u.waitWebElement(checkboxOrden, 10, 1);
	        u.click(checkboxOrden);
	        u.clear(inputPO);

	        u.click(inputPO);
	        u.sendKeys(inputPO, purchaseOrder3);
	        try { Thread.sleep(5000); } catch (InterruptedException e) { e.printStackTrace(); }
	        u.waitWebElement(checkboxOrden, 10, 1);
	        u.click(checkboxOrden);
	        u.clear(inputPO);

	    } catch (Exception e) {

	        System.out.println("Input no clickable, usando búsqueda directa");

	        u.waitWebElement(todo, 30, 1);
	        u.click(todo);
	        
	        By po1 = By.xpath("//a[@title='" + purchaseOrder1 + "']/preceding-sibling::input[1]");
	        By po2 = By.xpath("//a[@title='" + purchaseOrder2 + "']/preceding-sibling::input[1]");
	        By po3 = By.xpath("//a[@title='" + purchaseOrder3 + "']/preceding-sibling::input[1]");

	        u.waitWebElement(po1, 30, 1);
	        u.click(po1);
	        
	        u.waitWebElement(po2, 30, 1);
	        u.click(po2);
	        
	        u.waitWebElement(po3, 30, 1);
	        u.click(po3);
	    }

	    u.waitWebElement(btnAceptar, 10, 1);
	    u.click(btnAceptar);
	}
	
	/** ExpandirdetalleOrdenes
	 * realiza la expansión del detalle de las órdenes dentro de la interfaz,
	 * permitiendo visualizar la información completa de cada orden consultada
	 *@autor Sergio Robles Ramos
	 *@param
	 *  Este método no recibe parámetros
	 *
	 *@return void: Este método no retorna ningún valor
	 *@date 23/Enero/2026
	 */
	
	public void expandirdetalleOrdenes() {
		u.getDriver().navigate().refresh();
		u.waitForLoadPage();
		u.waitWebElement(iframeData, 60, 1);
		WebElement iframe = u.getDriver().findElement(iframeData);
	    u.getDriver().switchTo().frame(iframe);   
	    
		By scrollBar = By.xpath("(//div[@class='tab-tvScrollX tvimages'])[last()]");
		By oc = By.xpath("//span[ text()= 'Orden Compra']");
		By ultimo = By.xpath("((//div[@class='tab-vizTopSceneMargin'])[last()]//div[@class='tab-vizHeaderWrapper'])[14]");
		u.scrollToElement(oc);
		try {
			for (int i = 1; i < 58; i++) {
				By canvas = By.xpath("(//div[@class='tab-vizHeaderHolderWrapper']/div[1])[" + i + "]");
				By botonMas = By.xpath("(//span[@class='tab-tvLevelDrillDown tab-levelDrillingIcon tvDrillDown tab-widget'])[last()]");
				if ((i - 19) % 6 == 0) {
					scrollGradual(scrollBar);
					System.out.println("Scroll");
					Thread.sleep(2000);
					u.waitWebElement(canvas, 60, 1);
				}else {
					u.waitWebElement(canvas, 60, 1);
				}
				u.mouseOver(canvas);
				Thread.sleep(2000);
				try {
				    if (u.waitWebElement(botonMas, 20, 1) != null) {
						u.click(botonMas);
						System.out.println("Click en posicion: " + i);
						Thread.sleep(4000);
				    } 
				}catch (Exception e) {
				        System.out.println("Click omitido en posicion: " + i);
				}
			}
			scrollContainerToLeft(scrollBar);
			testCase.addScreenShotCurrentStep("Inicio ordenes");
			for (int i = 0; i < 8; i++) {
				scrollGradual(scrollBar);
				System.out.println("Scroll");
				Thread.sleep(2000);
				testCase.addScreenShotCurrentStep("Ordenes" + i);
			}
			scrollContainerToRight(scrollBar);
			u.scrollToElement(ultimo);
			testCase.addScreenShotCurrentStep("Fin ordenes");
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		 u.getDriver().switchTo().defaultContent();
	}
	
	/** scrollContainerToLeft
	 * realiza el desplazamiento horizontal de un contenedor hacia la izquierda 
	 *@param
	 *  -By containerLocator (Localizador): Localizador del contenedor a desplazar
	 *@return void: Este método no retorna ningún valor
	 *@date 05/Febrero/2026
	 */
	
	public void scrollContainerToLeft(By containerLocator) {
	    WebElement container = u.getDriver().findElement(containerLocator);
	    ((JavascriptExecutor) u.getDriver())
	        .executeScript("arguments[0].scrollLeft = 0;", container);
	}
	
	
	/** scrollContainerToRight
	 * realiza el desplazamiento horizontal de un contenedor hacia la derecha 
	 *@param
	 *  -By containerLocator (Localizador): Localizador del contenedor a desplazar
	 *@return void: Este método no retorna ningún valor
	 *@date 05/Febrero/2026
	 */
 
	public void scrollContainerToRight(By containerLocator) {
		WebElement container = u.getDriver().findElement(containerLocator);
	    ((JavascriptExecutor) u.getDriver())
	        .executeScript("arguments[0].scrollLeft = arguments[0].scrollWidth;", container);
	}
	
	/** scrollGradual
	 * realiza un desplazamiento horizontal gradual dentro de un contenedor utilizando JavaScript
	 *@param
	 *  -By containerLocator (Localizador): Localizador del contenedor a desplazar
	 *@return void: Este método no retorna ningún valor
	 *@date 05/Febrero/2026
	 */
	
	public void scrollGradual(By containerLocator) {
		WebElement container = u.getDriver().findElement(containerLocator);
	    JavascriptExecutor js = (JavascriptExecutor) u.getDriver();
	    js.executeScript(
	        "const el = arguments[0];" +
	        "const step = Math.max(0, arguments[1] | 0);" +
	        "const maxScroll = Math.max(0, el.scrollWidth - el.clientWidth);" +
	        "if (maxScroll === 0) return;" +
	        "const next = Math.min(el.scrollLeft + step, maxScroll);" +
	        "el.scrollLeft = next;" +
	        "el.dispatchEvent(new Event('scroll', {bubbles:true}));",
	        container, 700
	    );
 
	}
	
	/** LogoutTableau
	 * realiza el cierre de sesión en la plataforma Tableau y cierra el navegador
	 *@param
	 *  Este método no recibe parámetros
	 *@return void: Este método no retorna ningún valor
	 *@date 09/Diciembre/2025
	 */
	
	public void logoutTableau () {
		//Agregar los pasos para cerrar sesion y agregar un waitforloadpage
		u.waitWebElement(accountMenu, 180, 1);
		u.click(accountMenu);
		u.waitWebElement(signoutButton, 180, 1);
		u.click(signoutButton);
		u.close();
	}

	/** scrollIntoView
	 * desplaza la vista hasta hacer visible un elemento específico dentro de la página
	 *@param
	 *  -By locator (Localizador): Localizador del elemento a visualizar
	 *@return void: Este método no retorna ningún valor
	 *@date 23/Enero/2026
	 */
	
	public void scrollIntoView(By locator) {
		WebElement element = u.getDriver().findElement(locator);
		((JavascriptExecutor) u.getDriver())
		        .executeScript("arguments[0].scrollIntoView(true);", element);
	}
	
	/** scrollTableauHorizontal
	 * realiza un desplazamiento horizontal en la barra de scroll de Tableau utilizando acciones del mouse
	 *@param
	 *  Este método no recibe parámetros
	 *@return void: Este método no retorna ningún valor
	 *@date 23/Enero/2026
	 */
	public void scrollTableauHorizontal() {

	    By scrollBar = By.cssSelector("div.tab-tvScrollX.tvimages");
	    WebElement bar = u.waitWebElement(scrollBar, 20, 1);
	    if (bar == null) return;

	    int ancho = bar.getSize().getWidth();
	    int desplazamiento = Math.max(5, ancho / 10);

	    Actions actions = new Actions(u.getDriver());

	    actions
	        .moveToElement(bar, 2, bar.getSize().getHeight() / 2)
	        .clickAndHold()
	        .moveToElement(bar, desplazamiento, bar.getSize().getHeight() / 2)
	        .release()
	        .perform();
	}
	
	/** seleccionarMensaje
	 * selecciona el filtro de "Orden Compra" dentro de Tableau navegando entre iframes
	 *@param
	 *  Este método no recibe parámetros
	 *@return void: Este método no retorna ningún valor
	 *@date 05/Febrero/2026
	 */
	public void seleccionarMensaje() {
		WebElement iframe = u.getDriver().findElement(iframeData);
		u.getDriver().switchTo().frame(iframe);
		
		try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
		
	    u.waitWebElement(ComboOC, 30, 1);
	    u.click(ComboOC);

	    By OpcionOC = By.xpath ("//span[@class='tabMenuItemName' and contains(text(), 'Orden Compra')]");

	    u.waitWebElement(OpcionOC, 30, 1);
	    u.click(OpcionOC);
	    
	    try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
	    
	    u.waitWebElement(CheckMarkOC, 30, 1);
	    u.click(CheckMarkOC);
	    
	    u.getDriver().switchTo().defaultContent();
	}
	/** seleccionarMensajeORDRSP
	 * selecciona el filtro de "Confirmaci[on" dentro de Tableau navegando entre iframes
	 *@param
	 *  Este método no recibe parámetros
	 *@return void: Este método no retorna ningún valor
	 *@date 07/Abril/2026
	 */
	public void seleccionarMensajeORDRSP() {
		WebElement iframe = u.getDriver().findElement(iframeData);
		u.getDriver().switchTo().frame(iframe);
		
		try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
		
	    u.waitWebElement(ComboOC, 30, 1);
	    u.click(ComboOC);

	    By OpcionOC = By.xpath ("//span[@class='tabMenuItemName' and contains(text(), 'Confirmacion')]");

	    u.waitWebElement(OpcionOC, 30, 1);
	    u.click(OpcionOC);
	    
	    try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
	    
	    u.waitWebElement(CheckMarkOC, 30, 1);
	    u.click(CheckMarkOC);
	    
	    u.getDriver().switchTo().defaultContent();
	}
	
	/** seleccionarDiaCombo
	 * selecciona un día específico dentro de un combo en Tableau y aplica el filtro
	 *@param
	 *  -String diaOC (Cadena): Día que se desea seleccionar en el combo
	 *@return void: Este método no retorna ningún valor
	 *@date 05/Febrero/2026
	 */
	public void seleccionarDiaCombo(String diaOC) {
		WebElement iframe = u.getDriver().findElement(iframeData);
		u.getDriver().switchTo().frame(iframe);

	    try {
	        Thread.sleep(10000);
	    } catch (InterruptedException e) {
	        e.printStackTrace();
	    }
	    
	    WebElement iframeSegund = u.getDriver().findElement(iframe4);
		u.getDriver().switchTo().frame(iframeSegund);

	    WebElement iframeExtension = u.getDriver().findElement(iframeContent);
		u.getDriver().switchTo().frame(iframeExtension);

	    try {
	        Thread.sleep(5000);
	    } catch (InterruptedException e) {
	        e.printStackTrace();
	    }

	    By selectDia = By.id("single-select-parameter");
	    WebElement comboDia = u.waitWebElement(selectDia, 30, 1);

	    Select select = new Select(comboDia);
	    select.selectByVisibleText(diaOC);
	   

	    try {
	        Thread.sleep(5000);
	    } catch (InterruptedException e) {
	        e.printStackTrace();
	    }
	    
	    u.getDriver().switchTo().defaultContent();
		u.getDriver().switchTo().frame(iframe);
		
	    u.waitWebElement(CheckMarkDia, 30, 1);
	    u.click(CheckMarkDia);
	    u.getDriver().switchTo().defaultContent();
	    
	    System.out.println("Se selecciono el día: "+diaOC);

	}
	
	/** seleccionarMesCombo
	 * selecciona un mes específico dentro de un combo en Tableau y aplica el filtro
	 *@param
	 *  -String mesOC (Cadena): Mes que se desea seleccionar en el combo
	 *@return void: Este método no retorna ningún valor
	 *@date 05/Febrero/2026
	 */
	
	public void seleccionarMesCombo(String Mes, String mes) {
		WebElement iframe = u.getDriver().findElement(iframeData);
		u.getDriver().switchTo().frame(iframe);
		
		try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
	
		Month month = Month.of(Integer.parseInt(Mes));
		
		Locale localeES = Locale.forLanguageTag("es-ES");

		String nombreMes = month.getDisplayName(TextStyle.FULL, localeES);
		
		nombreMes = nombreMes.substring(0, 1).toUpperCase() + nombreMes.substring(1);
	    
	    WebElement iframeSegund = u.getDriver().findElement(iframe3);
		u.getDriver().switchTo().frame(iframeSegund);

	    WebElement iframeExtension = u.getDriver().findElement(iframeContent);
		u.getDriver().switchTo().frame(iframeExtension);
		
	    try {
	        Thread.sleep(5000);
	    } catch (InterruptedException e) {
	        e.printStackTrace();
	    }
	    
	    By selectMes = By.id("single-select-parameter");
	    WebElement comboMes = u.waitWebElement(selectMes, 30, 1);

	    Select select = new Select(comboMes);
	    select.selectByVisibleText(mes);
	    
	    try {
	        Thread.sleep(10000);
	    } catch (InterruptedException e) {
	        e.printStackTrace();
	    }
	    
	    
	    u.getDriver().switchTo().defaultContent();
		u.getDriver().switchTo().frame(iframe);
		
	    u.waitWebElement(CheckMarkMes, 30, 1);
	    u.click(CheckMarkMes);
	    u.getDriver().switchTo().defaultContent();
	    System.out.println("Se selecciono el Mes: "+mes);
	}
	
	/** seleccionarAñoCombo
	 * selecciona un Año específico dentro de un combo en Tableau y aplica el filtro
	 *@param
	 *  -String añoOC (Cadena): Año que se desea seleccionar en el combo
	 *@return void: Este método no retorna ningún valor
	 *@date 05/Febrero/2026
	 */
	
	public void seleccionarAñoCombo(String añoOC) {
		WebElement iframe = u.getDriver().findElement(iframeData);
		u.getDriver().switchTo().frame(iframe);

	    try {
	        Thread.sleep(10000);
	    } catch (InterruptedException e) {
	        e.printStackTrace();
	    }
	    
	    WebElement iframeSegund = u.getDriver().findElement(iframe2);
		u.getDriver().switchTo().frame(iframeSegund);

	    WebElement iframeExtension = u.getDriver().findElement(iframeContent);
		u.getDriver().switchTo().frame(iframeExtension);

	    try {
	        Thread.sleep(5000);
	    } catch (InterruptedException e) {
	        e.printStackTrace();
	    }

	    By selectAño = By.id("single-select-parameter");
	    WebElement comboAño = u.waitWebElement(selectAño, 30, 1);

	    Select select = new Select(comboAño);
	    select.selectByVisibleText(añoOC);
	   

	    try {
	        Thread.sleep(5000);
	    } catch (InterruptedException e) {
	        e.printStackTrace();
	    }
	    
	    By opcionAño = By.xpath("//option[@value="+"'"+añoOC+"'"+"]");

	    u.waitWebElement(opcionAño, 30, 1);
	    u.click(opcionAño);
	    
	    u.getDriver().switchTo().defaultContent();
		u.getDriver().switchTo().frame(iframe);
		
	    u.waitWebElement(CheckMarkAño, 30, 1);
	    u.click(CheckMarkAño);
	    u.getDriver().switchTo().defaultContent();
	    System.out.println("Se selecciono el año: "+añoOC);

	}
	
	/** BusquedaTableauDESADV
	 * realiza la búsqueda de una orden de compra tipo DESADV en Tableau,
	 * permitiendo consultar la información relacionada al aviso de despacho
	 *@param
	 *  -String purchaseOrder (Cadena): Orden de compra a consultar en Tableau
	 *
	 *@return void: Este método no retorna ningún valor
	 *@date 23/Enero/2026
	 */
	
	public void busquedaTableauDESADV(String purchaseOrder) {

	    WebElement iframe = u.getDriver().findElement(iframeData);
	    u.getDriver().switchTo().frame(iframe);

	    try {
	        Thread.sleep(30000);
	    } catch (InterruptedException e) {
	        e.printStackTrace();
	    }

	    u.waitWebElement(ordenCompra, 200, 1);
	    u.click(ordenCompra);

	    try {

	        // intenta localizar el input
	        WebElement input = u.waitWebElement(inputPO, 10, 1);

	        if (input == null) {
	            throw new Exception("Input no encontrado");
	        }

	        // intenta interactuar con el input
	        u.waitWebElement(todo, 30, 1);
	        u.click(todo);

	        u.click(inputPO);
	        u.sendKeys(inputPO, purchaseOrder);

	        try {
	            Thread.sleep(5000);
	        } catch (InterruptedException e) {
	            e.printStackTrace();
	        }

	        try {
	            if (u.waitWebElement(todo, 5, 1) == null) {
	                System.out.println("no existe boton all");
	            } else {
	                System.out.println("Elemento no deberia existir");
	            }
	        } catch (Exception e) {
	            System.out.println("Ocurrió un error: " + e.getMessage());
	        }

	        u.waitWebElement(checkboxOrden, 10, 1);
	        u.click(checkboxOrden);
	        u.clear(inputPO);

	    } catch (Exception e) {

	        System.out.println("Input no utilizable, usando búsqueda directa");

	        By po1 = By.xpath("//a[@title='" + purchaseOrder + "']/preceding-sibling::input[1]");

	        u.waitWebElement(po1, 30, 1);
	        u.click(po1);
	    }

	    u.waitWebElement(btnAceptar, 10, 1);
	    u.click(btnAceptar);
	}
	
	/** seleccionarAllTableau
	 * selecciona la opción "All" dentro de los filtros de Tableau,
	 * permitiendo visualizar todos los registros disponibles
	 *@param
	 *  Este método no recibe parámetros
	 *
	 *@return void: Este método no retorna ningún valor
	 *@date 23/Enero/2026
	 */
	public void seleccionarAllTableau() {

	    WebElement iframe = u.getDriver().findElement(iframeData);
	    u.getDriver().switchTo().frame(iframe);

	    try {
	        Thread.sleep(30000);
	    } catch (InterruptedException e) {
	        e.printStackTrace();
	    }

	    u.waitWebElement(ordenCompra, 200, 1);
	    u.click(ordenCompra);

	    u.waitWebElement(todo, 30, 1);
	    u.click(todo);

	    u.waitWebElement(btnAceptar, 10, 1);
	    u.click(btnAceptar);

	    // Salir del iframe
	    u.getDriver().switchTo().defaultContent();
	}
	
	/** seleccionarMensajeDesadv
	 * selecciona el filtro "Creacion Entrega" dentro de Tableau para visualizar mensajes DESADV,
	 * navegando a través de iframes y aplicando el filtro correspondiente
	 *@param
	 *  Este método no recibe parámetros
	 *
	 *@return void: Este método no retorna ningún valor
	 *@date 23/Enero/2026
	 */
	
	public void seleccionarMensajeDesadv() {
		WebElement iframe = u.getDriver().findElement(iframeData);
		u.getDriver().switchTo().frame(iframe);
		
		try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
		
	    u.waitWebElement(ComboOC, 30, 1);
	    u.click(ComboOC);

	    By OpcionOC = By.xpath ("//span[@class='tabMenuItemName' and text()='Creacion Entrega']");

	    u.waitWebElement(OpcionOC, 30, 1);
	    u.click(OpcionOC);
	    
	    try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
	    
	    u.waitWebElement(CheckMarkOC, 30, 1);
	    u.click(CheckMarkOC);
	    
	    u.getDriver().switchTo().defaultContent();
	}
	
	/** cajasOrdenadas
	 * selecciona la vista "Cajas Ordenadas" dentro de Tableau,
	 * permitiendo visualizar las cajas ordenadas asociadas a la consulta
	 *@param
	 *  Este método no recibe parámetros
	 *
	 *@return void: Este método no retorna ningún valor
	 *@date 23/Enero/2026
	 */
	public void cajasOrdenadas() {
		WebElement iframe = u.getDriver().findElement(iframeData);
		u.getDriver().switchTo().frame(iframe);
		By cajasOrdenadas = By.xpath("//div[@class='tab-vizHeader' and text()='Cajas Ordenadas']");
		try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
		u.waitWebElement(cajasOrdenadas, 30, 1);
		u.mouseOverAndClick(cajasOrdenadas);
		testCase.addScreenShotCurrentStep(u, "");
		u.getDriver().switchTo().defaultContent();
	}
	/** cajasPendEntrega
	 * selecciona la vista "Cajas Pendientes de Entrega" dentro de Tableau,
	 * mostrando las cajas que aún están pendientes por entregar
	 *@param
	 *  Este método no recibe parámetros
	 *
	 *@return void: Este método no retorna ningún valor
	 *@date 23/Enero/2026
	 */
	public void cajasPendEntrega() {
		WebElement iframe = u.getDriver().findElement(iframeData);
		u.getDriver().switchTo().frame(iframe);
		By cajasOrdenadas = By.xpath("//div[@class='tab-vizHeader' and contains(.,'Cajas Pend')]");
		try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
		u.waitWebElement(cajasOrdenadas, 30, 1);
		u.mouseOverAndClick(cajasOrdenadas);
		testCase.addScreenShotCurrentStep(u, "");
		u.getDriver().switchTo().defaultContent();
	}
	/** cajasDiferencia
	 * selecciona la vista "Cajas Diferencia" dentro de Tableau,
	 * permitiendo visualizar discrepancias en las cajas registradas
	 *@param
	 *  Este método no recibe parámetros
	 *
	 *@return void: Este método no retorna ningún valor
	 *@date 23/Enero/2026
	 */
	public void cajasDiferencia() {
		WebElement iframe = u.getDriver().findElement(iframeData);
		u.getDriver().switchTo().frame(iframe);
		By cajasOrdenadas = By.xpath("//div[@class='tab-vizHeader'and text()= 'Cajas Diferencia']");
		try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
		u.waitWebElement(cajasOrdenadas, 30, 1);
		u.mouseOverAndClick(cajasOrdenadas);
		testCase.addScreenShotCurrentStep(u, "");
		u.getDriver().switchTo().defaultContent();
	}
	/** cajasOrdenadasEntrega
	 * navega entre las vistas "Cajas Diferencia" y "Cajas Para Entrega" en Tableau,
	 * permitiendo validar información relacionada a la preparación y entrega de cajas
	 *@param
	 *  Este método no recibe parámetros
	 *
	 *@return void: Este método no retorna ningún valor
	 *@date 23/Enero/2026
	 */
	public void cajasOrdenadasEntrega() {
	    WebElement iframe = u.getDriver().findElement(iframeData);
	    u.getDriver().switchTo().frame(iframe);

	    By cajasOrdenadas = By.xpath("//div[@class='tab-vizHeader' and text()='Cajas Diferencia']");
	    By cajasEntrega = By.xpath("//div[@class='tab-vizHeader' and contains(text(), 'Cajas Para')]");
	    
	    u.waitWebElement(cajasOrdenadas, 30, 1);
		u.mouseOverAndClick(cajasOrdenadas);
		testCase.addScreenShotCurrentStep(u, "");
		
		u.waitWebElement(cajasEntrega, 30, 1);
		u.mouseOverAndClick(cajasEntrega);
		testCase.addScreenShotCurrentStep(u, "");

		u.getDriver().switchTo().defaultContent();
	}
}
