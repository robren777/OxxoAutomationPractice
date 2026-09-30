package Mobile.cedis.om;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import exceptions.FrameworkException;
import io.appium.java_client.AppiumBy;
import modelo.BaseExecution;
import modelo.TestCase;
import utils.AppiumUtil;



public class A04_MenuHerramientas {
	private TestCase testCase;
	private AppiumUtil au;
	private BaseExecution baseExecution;

	private By CEDIS_LeyendaHerramientas = By.xpath("//android.widget.TextView[@text=\"Herramientas\"]");
	private By CEDIS_Btn_RecepcionPedidosCedis = By.id("com.femco.oxxo.operaciones.mitienda.debug:id/cedisRepBtn");
	private By CEDIS_Btn_VerificacionMercanciaCedis = By
			.id("com.femco.oxxo.operaciones.mitienda.debug:id/cedisVeriPed");
	private By CEDIS_Btn_ConsultaFUEDigital = By.id("com.femco.oxxo.operaciones.mitienda.debug:id/btnConsultarTiras");
	private By CEDIS_Leyenda_ConsultaFUEDigital = By.xpath("//android.widget.TextView[@text='Consulta FUE Digital']");
	private By CEDIS_Btn_ConsultarTirasEntrega = By
			.id("com.femco.oxxo.operaciones.mitienda.debug:id/BtnConsultarTiras");
	private By CEDIS_Btn_VerificacionMercanciaDirectos = By
			.id("com.femco.oxxo.operaciones.mitienda.debug:id/directosVerificar");
	private By CEDIS_Btn_CambiosFisicosProductosDirectos = By
			.id("com.femco.oxxo.operaciones.mitienda.debug:id/CambiosFisicos");
	private By CEDIS_Btn_DevolucionesProductosDirectos = By
			.id("com.femco.oxxo.operaciones.mitienda.debug:id/DevolucionesArticulos");

	/**
	 * Constructor
	 *
	 * Constructor de la clase A04_MenuHerramientas
	 * 
	 * @author MArcerlo Gutierrez
	 * @param au            (AppiumUtil): objeto de la clase
	 * @param testCase      (TestCase): objeto de la clase
	 * @param baseExecution (BaseExecution): objeto de la clase
	 */

	public A04_MenuHerramientas(TestCase testCase, AppiumUtil au, BaseExecution baseExecution) {
		this.testCase = testCase;
		this.au = au;
		this.baseExecution = baseExecution;
	}

	/**
	 * Método para validar recepcion de pedidos cedis
	 *
	 * El método clickBtnRecepcionPedidosCEDIS() realiza un clic en el botón de recepción de pedidos del CEDIS y luego avanza en el proceso de permisos o autenticación dentro del área de distribución 
	 * mediante la llamada al método AvanzarNecesitaPermisos() de la clase A05_LoginAreaDistribucion.
	 *
	 * @author Marcerlo Gutierrez
	 * @date 
	 * @throws FrameworkException
	 */
	
	public void clickBtnRecepcionPedidosCEDIS() throws InterruptedException {
		au.click(CEDIS_Btn_RecepcionPedidosCedis);
		Mobile.cedis.om.A05_LoginAreaDistribucion login = new Mobile.cedis.om.A05_LoginAreaDistribucion(testCase, au, baseExecution);
		login.AvanzarNecesitaPermisos();
	}

	/**
	 * Método para verificacion de mercancia cedis
	 *
	 * Este método encapsula una acción de clic sobre un botón específico en la interfaz de usuario de una aplicación, 
	 * y probablemente se usa como parte de un flujo de pruebas automatizadas.
	 *
	 * @author Marcerlo Gutierrez
	 * @date 
	 * @throws FrameworkException
	 */
	
	public void clickBtnVerificacionMercanciaCEDIS() {
		au.click(CEDIS_Btn_VerificacionMercanciaCedis);
	}

	/**
	 * Método para validar consulta FUE digital
	 *
	 *Este método realizar un clic sobre el botón de "Consulta FUE Digital".
	 *
	 * @author Marcerlo Gutierrez
	 * @date 
	 * @throws FrameworkException
	 */
	
	public void clickBtnConsultaFUEDigital() {// Se agrega busqueda de nueva opción de Consulta FUE Digital 13-12-24
		au.click(CEDIS_Btn_ConsultaFUEDigital);
	}

	/**
	 * Método para validar el menu de Herramientas
	 *
	 * El método busca y valida directamente un texto en la interfaz, asegurándose de que sea exactamente "Consulta FUE Digital",
	 * si el texto no coincide, muestra un error.
	 *
	 * @author Marcerlo Gutierrez
	 * @date 
	 * @throws FrameworkException
	 */
	
	public void validarTextoConsultaFUEDigitalSinWait() {
		try {
			// Buscar directamente el elemento mediante XPath
			WebElement textoElemento = au.driver
					.findElement(AppiumBy.xpath("//*[contains(@text, 'Consulta FUE Digital')]"));

			// Obtener el texto del elemento
			String textoActual = textoElemento.getText();

			// Validar que el texto sea el esperado
			Assert.assertEquals(textoActual, "Consulta FUE Digital", "Error: El texto no coincide.");
			System.out.println("Validación exitosa: El texto es 'Consulta FUE Digital'.");

		} catch (Exception e) {
			System.err.println("Error al validar el texto del elemento: " + e.getMessage());
		}
	}

	/**
	 * Método para validar metodoConFindElement
	 *
	 * Este método realiza una detección secuencial de elementos en app, busca un botón principal por ID, dentro de él busca un botón por ID dentro de este, 
	 * busca un TextView con el texto exacto "Consulta FUE Digital" usando XPath y captura e informa errores si algo falla durante la búsqueda.
	 *
	 * @author Marcerlo Gutierrez
	 * @date 
	 * @throws FrameworkException
	 */
	
	public void metodoConFindElement() {
		try {
			System.out.println("Iniciando detección del elemento principal por ID...");
			WebElement elementoPadre = au.driver
					.findElement(By.id("com.femco.oxxo.operaciones.mitienda.debug:id/btnConsultarTiras"));
			System.out.println("Elemento principal detectado correctamente.");

			System.out.println("Iniciando detección del elemento hijo por ID...");
			WebElement elementoHijo = elementoPadre
					.findElement(By.id("com.femco.oxxo.operaciones.mitienda.debug:id/btnConsultarTirasEntregas"));
			System.out.println("Elemento hijo detectado correctamente.");

			System.out.println("Iniciando detección del texto dentro del hijo usando XPath...");
			WebElement textoElemento = elementoHijo
					.findElement(By.xpath("//android.widget.TextView[@text='Consulta FUE Digital']"));
			System.out.println("Texto detectado correctamente: " + textoElemento.getText());
		} catch (Exception e) {
			System.err.println("Error detectando elementos: " + e.getMessage());
		}
	}

	
	/**
	 * Método para consultar tiras de entrega
	 *
	 * Este método es una acción que hace clic en el botón "Consultar Tiras Entrega"
	 *
	 * @author Marcerlo Gutierrez
	 * @date 
	 * @throws FrameworkException
	 */
	
	public void clickBtnConsultarTirasEntrega() {
		au.click(CEDIS_Btn_ConsultarTirasEntrega);
	}

	/**
	 * Método para Verificacion mercancia directos
	 *
	 * Este método realiza la acción de hacer clic en el botón "Verificación Mercancía Directos".
	 *
	 * @author Marcerlo Gutierrez
	 * @date 
	 * @throws FrameworkException
	 */
	
	public void clickBtnVerificacionMercanciaDirectos() {
		au.click(CEDIS_Btn_VerificacionMercanciaDirectos);
	}

	/**
	 * Método para validar cambios fisicos de productos directos
	 *
	 *Este método realiza la acción de hacer clic en el botón "Cambios Fisicos Produccion Directos".
	 *
	 * @author Marcerlo Gutierrez
	 * @date 
	 * @throws FrameworkException
	 */
	
	public void clickBtnCambiosFisicosProductosDirectos() {
		au.click(CEDIS_Btn_CambiosFisicosProductosDirectos);
	}

	/**
	 * Método para validar Devoluciones Productos Directos
	 *
	 *Este método realiza la acción de hacer clic en el botón "Devoluciones Productos Directos".
	 *
	 * @author Marcerlo Gutierrez
	 * @date 
	 * @throws FrameworkException
	 */
	
	public void clickBtnDevolucionesProductosDirectos() {
		au.click(CEDIS_Btn_DevolucionesProductosDirectos);
	}

	
	/**
	 * Método para validar pantalla Herramientas
	 *
	 * Este método valida que los elementos principales de la pantalla "Herramientas" estén disponibles y activos; 
	 * si alguno falla, registra el error, toma evidencia y finaliza la prueba.
	 *
	 * @author Marcerlo Gutierrez
	 * @date 
	 * @throws FrameworkException
	 */
	
	public void ValidacionPantallaHerramientas() throws InterruptedException {
		Thread.sleep(1000);
		

		if (au.driver.findElement(CEDIS_LeyendaHerramientas).isEnabled() == false) {
			baseExecution.addStep("Se falla el caso");
			Assert.fail("No se ha encontrado que se posicione en la pantalla con el titulo: Herramientas", null);
			testCase.addScreenShotCurrentStep(au, "testImage");
		}

		if (au.driver.findElement(CEDIS_Btn_RecepcionPedidosCedis).isEnabled() == false) {
			baseExecution.addStep("Se falla el caso");
			Assert.fail("No se ha encontrado el botón: Recepcion de Pedidos CEDIS", null);
			testCase.addScreenShotCurrentStep(au, "testImage");
		}

		if (au.driver.findElement(CEDIS_Btn_VerificacionMercanciaCedis).isEnabled() == false) {
			baseExecution.addStep("Se falla el caso");
			Assert.fail("No se ha encontrado el botón: Verificacion de Mercancia Pedidos CEDIS", null);
			testCase.addScreenShotCurrentStep(au, "testImage");
		}

		if (au.driver.findElement(CEDIS_Btn_ConsultaFUEDigital).isEnabled() == false) {
			baseExecution.addStep("Se falla el caso");
			Assert.fail("No se ha encontrado el botón: Consulta FUE Digital", null);
			testCase.addScreenShotCurrentStep(au, "testImage");
		}
	}

	/**
	 * Método para validar leyenda de consulta FUE digital
	 *
	 * El método valida que el texto "Consulta FUE Digital" sea limpiado y compara el texto después de limpiarlo y registra el resultado, incluyendo un fallo con captura si la validación no pasa.
	 *
	 * @author Marcerlo Gutierrez
	 * @date 
	 * @throws FrameworkException
	 */
	
	public void ValidacionLeyendaConsultaFUEDigital() throws InterruptedException {
	    System.out.println("Iniciando validación de la leyenda Consulta FUE Digital...");
	    Thread.sleep(1000);

	    // Obtener el texto del TextView usando XPath
	    WebElement leyendaElemento = au.driver.findElement(By.xpath("//android.widget.TextView[contains(@text, 'Consulta') and contains(@text, 'FUE Digital')]"));
	    String leyendaTexto = leyendaElemento.getText();
	    
	    System.out.println("Texto sin procesar de la leyenda: " + leyendaElemento);

	    // Limpiar ambos textos antes de comparar
	    String textoActual = limpiarTexto(leyendaTexto);
	    String textoEsperado = limpiarTexto("Consulta \r\nFUE Digital");

	    System.out.println("Texto actual (limpio): " + textoActual);
	    System.out.println("Texto esperado (limpio): " + textoEsperado);

	    if (!textoActual.equalsIgnoreCase(textoEsperado)) {
	        System.out.println("ERROR: Los textos no coinciden.");
	        baseExecution.addStep("Se falla el caso");
	        Assert.fail("No se ha encontrado la leyenda: Consulta FUE Digital", null);
	        testCase.addScreenShotCurrentStep(au, "testImage");
	    } else {
	        System.out.println("Validación exitosa.");
	    }
	}

	
	/**
	 * Método para limpiar texto
	 *
	 * Este método elimina saltos de línea y espacios sobrantes de un texto, devolviéndolo en una sola línea limpia.
	 *
	 * @author Marcerlo Gutierrez
	 * @date 
	 * @throws FrameworkException
	 */
	
	// Método para limpiar texto eliminando espacios extras y saltos de línea
	private String limpiarTexto(String texto) {
	    return texto.replaceAll("[\\r\\n]+", " ").trim(); // Reemplaza saltos de línea y tabulaciones por un espacio
	}



	
}