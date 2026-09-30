package Mobile.cedis.om;

import java.io.IOException;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import modelo.BaseExecution;
import modelo.TestCase;
import utils.AppiumUtil;

public class B11_EncuestaSatisfaccion {
    private TestCase testCase;
	private AppiumUtil au;
	private BaseExecution baseExecution;
	
	//Primer Pantalla - Sección Encabezado
	private By CEDIS_LeyendaEncuestaDeSatisfaccion = By.xpath("//android.widget.TextView[@text=\"ENCUESTA DE SATISFACCIÓN\"]");
	private By CEDIS_LeyendaInstruccion = By.xpath("//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/txt_message\"]");
	private By CEDIS_LeyendaFecha = By.xpath("//android.widget.TextView[@text=\"Fecha:\"]");
	private By CEDIS_ValorFecha = By.xpath("//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/txt_date\"]");
	private By CEDIS_LeyendaHora = By.xpath("//android.widget.TextView[@text=\"Hora:\"]");
	private By CEDIS_ValorHora = By.xpath("//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/txt_hour\"]");
	private By CEDIS_LeyendaRuta = By.xpath("//android.widget.TextView[@text=\"Ruta:\"]");   
	private By CEDIS_ValorRuta = By.xpath("//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/txt_route\"]");
	private By CEDIS_LeyendaCRTienda = By.xpath("//android.widget.TextView[@text=\"CR Tienda:\"]");
	private By CEDIS_ValorCRTienda = By.xpath("//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/txt_cr_store\"]");  
	private By CEDIS_LeyendaTransferencia = By.xpath("//android.widget.TextView[@text=\"Transferencia:\"]");
	private By CEDIS_ValorTransferencia = By.id("com.femco.oxxo.operaciones.mitienda.debug:id/txt_transfer");   
	
	// Botón "COMENZAR"
	private By CEDIS_BotonComenzar = By.xpath("//android.widget.Button[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/nextButton\"]");
	
	//Segunda Pantalla - Sección Primer Pregunta
	private By CEDIS_LeyendaTituloSeccion = By.xpath("//android.widget.TextView[@text=\"Primera Sección\"]");
	private By CEDIS_LeyendaPregunta = By.xpath("//android.widget.TextView[@text=\"¿Cuál es tu rol en la tienda?\"]");
	private By CEDIS_LeyendaMenuDesplegable = By.xpath("//android.widget.EditText[@text=\"Ayudante\"]");
	
	// Botónes "SIGUIENTE" y "Contestar en otro momento"
	private By CEDIS_BotonSiguiente = By.xpath("//android.widget.Button[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/nextButton\"]");
	private By CEDIS_BotonContestarEnOtroMomento = By.xpath("//android.widget.Button[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/cancelButton\"]");
	
	// Seccion de Mensaje desplegable de "Contestar en otro momento"
	private By CEDIS_LeyendaMensajeContestarEnOtroMomento = By.xpath("//android.widget.TextView[@resource-id=\"android:id/message\"]");
	private By CEDIS_BotonCancelar = By.xpath("//android.widget.Button[@resource-id=\"android:id/button2\"]");	
	private By CEDIS_BotonConfirmar = By.xpath("//android.widget.Button[@resource-id=\"android:id/button1\"]");
	
	/**
	 * Constructor de la clase de la pantalla de Encuesta de Satisfaccion
	 * @author Jesús Marcelo Gutiérrez Bravo
	 * @param au (AppiumUtil): objeto de la clase 
	 * @param testCase (TestCase): objeto de la clase
	 * @param baseExecution (BaseExecution): objeto de la clase
	 **/
	
	public B11_EncuestaSatisfaccion(TestCase testCase, AppiumUtil au, BaseExecution baseExecution) {
        this.testCase = testCase;
        this.au = au;
        this.baseExecution = baseExecution;
    }
    
    public void SeleccionarObjeto(String ObjetoPantalla)
    {  	
    	//Funcion para buscar objeto y dar click especifico en la pantalla Creado: Juan Ibarra    	
    	switch (ObjetoPantalla) {
    	case "Boton Comenzar":
    		au.scrollToElement(CEDIS_BotonComenzar);
    		au.click(CEDIS_BotonComenzar);
            break;
    	case "Boton Contestar En Otro Momento":
    		au.scrollToElement(CEDIS_BotonContestarEnOtroMomento);
    		au.click(CEDIS_BotonContestarEnOtroMomento);
            break;
    	case "Boton Siguiente":
    		au.scrollToElement(CEDIS_BotonSiguiente);
    		au.click(CEDIS_BotonSiguiente);
            break;
    	case "Boton Cancelar":
    		au.scrollToElement(CEDIS_BotonCancelar);
    		au.click(CEDIS_BotonCancelar);
            break;
    	case "Boton Confirmar":
    		au.scrollToElement(CEDIS_BotonConfirmar);
    		au.click(CEDIS_BotonConfirmar);
            break;   
        default:
            baseExecution.addStep("Se falla el caso");
            //Se agrega el paso a mostrar en el PDF
    		Assert.fail("No se ha encontrado el objeto "+ ObjetoPantalla, null);
    		//Se agrega un mensaje a mostrar del error, en caso de no ponerlo regresaria leyenda de null
    	}    	
    }
        
    public void ValidacionPantallaEncuestaDeSatisfaccion() throws IOException, InterruptedException {
        // Descripción: Valida la presencia del título, instrucciones, información general y botón "Comenzar" en la pantalla de Encuesta de Satisfacción.
        // Creado: Marcelo Gutiérrez
        // Fecha: 11/02/2025

        System.out.println("Iniciando validación de la pantalla Encuesta de Satisfacción...");

        // Esperar un momento para permitir la carga de los elementos
        Thread.sleep(1000);
        System.out.println("Esperando 1 segundo para carga de elementos...");

        // Validar la presencia del título
        System.out.println("Verificando la presencia del título...");
        if (!au.driver.findElement(CEDIS_LeyendaEncuestaDeSatisfaccion).isEnabled()) {
            System.out.println("ERROR: No se encontró el título ENCUESTA DE SATISFACCIÓN.");
            baseExecution.addStep("No se ha encontrado el Título ENCUESTA DE SATISFACCIÓN");
            testCase.addScreenShotCurrentStep(au, "testImage");
            Assert.fail("No se ha encontrado el Título ENCUESTA DE SATISFACCIÓN", null);
        }

        // Validar la presencia de instrucciones
        System.out.println("Verificando la presencia de las instrucciones...");
        if (au.driver.findElements(CEDIS_LeyendaInstruccion).isEmpty()) {
            System.out.println("ERROR: No se encontró el campo de instrucciones.");
            baseExecution.addStep("No se ha encontrado el campo que contiene las instrucciones de la encuesta.");
            testCase.addScreenShotCurrentStep(au, "testImage");
            Assert.fail("No se ha encontrado el campo de instrucciones", null);
        }

        // Validar la presencia de la fecha y su valor
        System.out.println("Verificando la presencia de la leyenda y el valor de la fecha...");
        if (au.driver.findElements(CEDIS_LeyendaFecha).isEmpty()) {
            System.out.println("ERROR: No se encontró la leyenda de Fecha.");
            baseExecution.addStep("No se ha encontrado la leyenda de Fecha.");
            testCase.addScreenShotCurrentStep(au, "testImage");
            Assert.fail("No se ha encontrado la leyenda de Fecha", null);
        }
        if (au.driver.findElements(CEDIS_ValorFecha).isEmpty()) {
            System.out.println("ERROR: No se encontró el valor de Fecha.");
            baseExecution.addStep("No se ha encontrado el valor de Fecha.");
            testCase.addScreenShotCurrentStep(au, "testImage");
            Assert.fail("No se ha encontrado el valor de Fecha", null);
        }

        // Validar la presencia de la hora y su valor
        System.out.println("Verificando la presencia de la leyenda y el valor de la hora...");
        if (au.driver.findElements(CEDIS_LeyendaHora).isEmpty()) {
            System.out.println("ERROR: No se encontró la leyenda de Hora.");
            baseExecution.addStep("No se ha encontrado la leyenda de Hora.");
            testCase.addScreenShotCurrentStep(au, "testImage");
            Assert.fail("No se ha encontrado la leyenda de Hora", null);
        }
        if (au.driver.findElements(CEDIS_ValorHora).isEmpty()) {
            System.out.println("ERROR: No se encontró el valor de Hora.");
            baseExecution.addStep("No se ha encontrado el valor de Hora.");
            testCase.addScreenShotCurrentStep(au, "testImage");
            Assert.fail("No se ha encontrado el valor de Hora", null);
        }

        // Validar la presencia de la ruta y su valor
        System.out.println("Verificando la presencia de la leyenda y el valor de la ruta...");
        if (au.driver.findElements(CEDIS_LeyendaRuta).isEmpty()) {
            System.out.println("ERROR: No se encontró la leyenda de Ruta.");
            baseExecution.addStep("No se ha encontrado la leyenda de Ruta.");
            testCase.addScreenShotCurrentStep(au, "testImage");
            Assert.fail("No se ha encontrado la leyenda de Ruta", null);
        }
        if (au.driver.findElements(CEDIS_ValorRuta).isEmpty()) {
            System.out.println("ERROR: No se encontró el valor de Ruta.");
            baseExecution.addStep("No se ha encontrado el valor de Ruta.");
            testCase.addScreenShotCurrentStep(au, "testImage");
            Assert.fail("No se ha encontrado el valor de Ruta", null);
        }

        // Validar la presencia del CR Tienda y su valor
        System.out.println("Verificando la presencia de la leyenda y el valor de CR Tienda...");
        if (au.driver.findElements(CEDIS_LeyendaCRTienda).isEmpty()) {
            System.out.println("ERROR: No se encontró la leyenda de CR Tienda.");
            baseExecution.addStep("No se ha encontrado la leyenda de CR Tienda.");
            testCase.addScreenShotCurrentStep(au, "testImage");
            Assert.fail("No se ha encontrado la leyenda de CR Tienda", null);
        }
        if (au.driver.findElements(CEDIS_ValorCRTienda).isEmpty()) {
            System.out.println("ERROR: No se encontró el valor de CR Tienda.");
            baseExecution.addStep("No se ha encontrado el valor de CR Tienda.");
            testCase.addScreenShotCurrentStep(au, "testImage");
            Assert.fail("No se ha encontrado el valor de CR Tienda", null);
        }
        
     // Validar la presencia del CR Tienda y su valor
        System.out.println("Verificando la presencia de la leyenda y el valor de Transferencias...");
        if (au.driver.findElements(CEDIS_LeyendaTransferencia).isEmpty()) {
            System.out.println("ERROR: No se encontró la leyenda de Transferencia.");
            baseExecution.addStep("No se ha encontrado la leyenda de Transferencia.");
            testCase.addScreenShotCurrentStep(au, "testImage");
            Assert.fail("No se ha encontrado la leyenda de Transferencia.", null);
        }
        if (au.driver.findElements(CEDIS_ValorTransferencia).isEmpty()) {
            System.out.println("ERROR: No se encontró el valor de Transferencia.");
            baseExecution.addStep("No se ha encontrado el valor de Transferencia.");
            testCase.addScreenShotCurrentStep(au, "testImage");
            Assert.fail("No se ha encontrado la leyenda de Transferencia.", null);
        }

        // Validar la presencia del botón "Comenzar"
        System.out.println("Verificando la presencia del botón Comenzar...");
        if (au.driver.findElements(CEDIS_BotonComenzar).isEmpty()) {
            System.out.println("ERROR: No se encontró el botón Comenzar.");
            baseExecution.addStep("No se ha encontrado el botón Comenzar.");
            testCase.addScreenShotCurrentStep(au, "testImage");
            Assert.fail("No se ha encontrado el botón Comenzar", null);
        }

        System.out.println("Validación de pantalla Encuesta de Satisfacción finalizada con éxito.");
    }
    
        
    public void ValidacionTextoInstrucciones() throws InterruptedException {  
        // Descripción: Valida que el texto de la instrucción en la Encuesta de Satisfacción sea el correcto.
        // Creado: Marcelo Gutiérrez
        // Fecha: 11/02/2025

        System.out.println("Iniciando validación del texto de instrucciones...");

        // Esperar un momento para permitir la carga del elemento
        Thread.sleep(1000);
        System.out.println("Esperando 1 segundo para carga de elementos...");

        // Obtener el elemento de la instrucción y su texto
        System.out.println("Obteniendo el texto de la instrucción...");
        WebElement elemento = au.driver.findElement(CEDIS_LeyendaInstruccion);
        String descripcionActual = elemento.getText(); 

        // Texto esperado
        String descripcionEsperada = "Ayúdanos a evaluar el servicio de Distribución, responde las siguientes preguntas con tu retroalimentación con base en las entregas realizadas a tu tienda.";

        // Imprimir mensajes para depuración
        System.out.println("Texto actual obtenido: " + descripcionActual);
        System.out.println("Texto esperado: " + descripcionEsperada);

        // Validar si el mensaje mostrado no coincide con el mensaje esperado
        System.out.println("Comparando el texto actual con el esperado...");
        if (!descripcionActual.equals(descripcionEsperada)) {
            System.out.println("ERROR: El texto de las instrucciones no es el esperado.");
            baseExecution.addStep("La descripción de las instrucciones es incorrecta, se esperaba: \"" + descripcionEsperada + "\"");
            testCase.addScreenShotCurrentStep(au, "testImage");
            Assert.fail("La descripción de las instrucciones es incorrecta, se esperaba: \"" + descripcionEsperada + "\"", null);
        }

        System.out.println("Validación exitosa: El texto de las instrucciones es correcto.");
    }
    
    public void ValidacionPantallaPrimeraSeccion() throws IOException, InterruptedException {
        // Descripción: Valida la presencia del título de la sección, la pregunta, el menú desplegable,
        // además de los botones "Siguiente" y "Contestar en otro momento" en la pantalla de Encuesta de Satisfacción.
        // Creado: Marcelo Gutiérrez
        // Fecha: 11/02/2025

        System.out.println("Iniciando validación de la pantalla Encuesta de Satisfacción...");

        // Esperar un momento para permitir la carga de los elementos
        Thread.sleep(1000);
        System.out.println("Esperando 1 segundo para la carga de elementos...");

        // Validar la presencia del título de la sección
        System.out.println("Verificando la presencia del título de la sección...");
        if (!au.driver.findElement(CEDIS_LeyendaTituloSeccion).isEnabled()) {
            System.out.println("ERROR: No se encontró el título de la sección.");
            baseExecution.addStep("No se ha encontrado el título de la sección.");
            testCase.addScreenShotCurrentStep(au, "testImage");
            Assert.fail("No se ha encontrado el título de la sección", null);
        }

        // Validar la presencia de la pregunta de la encuesta
        System.out.println("Verificando la presencia de la pregunta de la encuesta...");
        if (au.driver.findElements(CEDIS_LeyendaPregunta).isEmpty()) {
            System.out.println("ERROR: No se encontró la pregunta de la encuesta.");
            baseExecution.addStep("No se ha encontrado la pregunta de la encuesta.");
            testCase.addScreenShotCurrentStep(au, "testImage");
            Assert.fail("No se ha encontrado la pregunta de la encuesta", null);
        }

        // Validar la presencia del menú desplegable
        System.out.println("Verificando la presencia del menú desplegable...");
        if (au.driver.findElements(CEDIS_LeyendaMenuDesplegable).isEmpty()) {
            System.out.println("ERROR: No se encontró el menú desplegable.");
            baseExecution.addStep("No se ha encontrado el menú desplegable.");
            testCase.addScreenShotCurrentStep(au, "testImage");
            Assert.fail("No se ha encontrado el menú desplegable", null);
        }

        // Validar la presencia del botón "Contestar en otro momento"
        System.out.println("Verificando la presencia del botón 'Contestar en otro momento'...");
        if (au.driver.findElements(CEDIS_BotonContestarEnOtroMomento).isEmpty()) {
            System.out.println("ERROR: No se encontró el botón 'Contestar en otro momento'.");
            baseExecution.addStep("No se ha encontrado el botón 'Contestar en otro momento'.");
            testCase.addScreenShotCurrentStep(au, "testImage");
            Assert.fail("No se ha encontrado el botón 'Contestar en otro momento'", null);
        }

        // Validar la presencia del botón "Siguiente"
        System.out.println("Verificando la presencia del botón 'Siguiente'...");
        if (au.driver.findElements(CEDIS_BotonSiguiente).isEmpty()) {
            System.out.println("ERROR: No se encontró el botón 'Siguiente'.");
            baseExecution.addStep("No se ha encontrado el botón 'Siguiente'.");
            testCase.addScreenShotCurrentStep(au, "testImage");
            Assert.fail("No se ha encontrado el botón 'Siguiente'", null);
        }

        System.out.println("Validación de la pantalla Encuesta de Satisfacción finalizada con éxito.");
    }
    
    public void ValidacionMensajeContestarEnOtroMomento() throws IOException, InterruptedException {
        // Descripción: Valida el mensaje desplegable al dar clic en el botón "Contestar en otro momento"
        // en la pantalla de Encuesta de Satisfacción.
        // Creado: Marcelo Gutiérrez
        // Fecha: 11/02/2025

        System.out.println("Iniciando validación del mensaje 'Contestar en otro momento'...");

        // Esperar un momento para permitir la carga de los elementos
        Thread.sleep(1000);
        System.out.println("Esperando 1 segundo para la carga de elementos...");

        // Validar el mensaje emergente
        System.out.println("Verificando el mensaje emergente...");
        WebElement mensaje = au.driver.findElement(CEDIS_LeyendaMensajeContestarEnOtroMomento);
        String mensajeActual = mensaje.getText();
        String mensajeEsperado = "¿Estas seguro que no deseas continuar? Tu retroalimentación es importante. En caso de no contestarla, la encuesta se detonará en otra visita del CEDIS.";

        System.out.println("Mensaje actual: " + mensajeActual);
        System.out.println("Mensaje esperado: " + mensajeEsperado);

        if (!mensajeActual.equals(mensajeEsperado)) {
            System.out.println("ERROR: El mensaje emergente no coincide con el esperado.");
            baseExecution.addStep("El mensaje emergente no se desplegó correctamente o el texto es incorrecto.");
            testCase.addScreenShotCurrentStep(au, "testImage");
            Assert.fail("El mensaje emergente no coincide con el esperado", null);
        }

        // Validar la presencia del botón "Cancelar"
        System.out.println("Verificando la presencia del botón 'Cancelar'...");
        if (!au.driver.findElement(CEDIS_BotonCancelar).isEnabled()) {
            System.out.println("ERROR: No se encontró el botón 'Cancelar'.");
            baseExecution.addStep("No se ha encontrado el botón 'Cancelar'.");
            testCase.addScreenShotCurrentStep(au, "testImage");
            Assert.fail("No se ha encontrado el botón 'Cancelar'", null);
        }

        // Validar la presencia del botón "Confirmar"
        System.out.println("Verificando la presencia del botón 'Confirmar'...");
        if (au.driver.findElements(CEDIS_BotonConfirmar).isEmpty()) {
            System.out.println("ERROR: No se encontró el botón 'Confirmar'.");
            baseExecution.addStep("No se ha encontrado el botón 'Confirmar'.");
            testCase.addScreenShotCurrentStep(au, "testImage");
            Assert.fail("No se ha encontrado el botón 'Confirmar'", null);
        }

        System.out.println("Validación del mensaje 'Contestar en otro momento' finalizada con éxito.");
    }



    
}    