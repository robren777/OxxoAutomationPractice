package Mobile.cedis.om;

import java.io.IOException;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Locale;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import com.ibm.icu.text.SimpleDateFormat;

import io.appium.java_client.AppiumBy;
import modelo.BaseExecution;
import modelo.TestCase;
import utils.AppiumUtil;

public class E01_ConsultaFUE {
    private TestCase testCase;
	private AppiumUtil au;
	private BaseExecution baseExecution;
	
	//Sección Encabezado
	private By CEDIS_TituloPantalla = By.xpath("//android.widget.TextView[@text=\"Consulta FUE Digital\"]");
	
	private By CEDIS_TituloPantallaFUE = By.xpath("//android.widget.TextView[@text=\"FORMATO ÚNICO DE ENTREGA\"]");
	
	private By CEDIS_ValorFecha = By.xpath("//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/tvTitleDate\"]");
	
	private By CEDIS_ImagenTienda = By.id("com.femco.oxxo.operaciones.mitienda.debug:id/imgResume");
	
	private By CEDIS_Fecha = By.xpath("//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/tvDateText\"]");
	
	private By CEDIS_Ruta = By.xpath("//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/tvTitleRute\"]");
	
	private By CEDIS_RutaDinamica = By.xpath("//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/tvTitleRuteD\"]");

	private By CEDIS_CRTienda = By.xpath("//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/tvTitleStore\"]");

	private By CEDIS_NombreTienda = By.xpath("//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/tvTitleNameStore\"]");	

	private By CEDIS_LiderTienda = By.xpath("//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/tvTitleLeaderStore\"]");

	private By CEDIS_Transferencia = By.xpath("//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/tvTransferNumber\"]");

	

	//Seccion Tranferencia	
	private By CEDIS_RadioTransferencia = By.xpath("//android.widget.RadioButton[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/radiobutton_transferencias_mercancia\" and @text=\"Transferencia\"]");
	private By CEDIS_CampoTransferencia = By.xpath("//android.widget.EditText[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/et_autocomplete_transferencia\"]");
	private By CEDIS_CampoFecha = By.xpath("//android.widget.EditText[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/et_verificacion_mercancia_fecha\"]");
	private By CEDIS_Buscar = By.xpath("//android.widget.Button[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/button_filter_verificacion_mercancia\" and @text=\"BUSCAR\"]");
	private By CEDIS_PrimerTransferenciaListada = By.xpath("(//android.widget.LinearLayout[@resource-id='com.femco.oxxo.operaciones.mitienda.debug:id/ll_body_container_search_verify_item'])[1]");
	private By CEDIS_LeyendaPrimerTransferenciaListada = By.xpath("//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/tvTitleTransferencia\"]");
	private By CEDIS_LeyendaPrimerFechaTransferenciaListada = By.xpath("//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/tvTitleFecha\"]");
	private By CEDIS_LeyendaSegundaFechaTransferenciaListada = By.xpath("(//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/tvTitleFecha\"])[2]");
	private By CEDIS_LeyendaTerceraFechaTransferenciaListada = By.xpath("(//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/tvTitleFecha\"])[3]");
	private By CEDIS_MensajeFechaIncorrecta = By.xpath("//android.widget.TextView[@resource-id=\"android:id/message\"]");
	private By CEDIS_MensajeTransferenciaIncorrecta = By.xpath("//android.widget.TextView[@resource-id=\"android:id/message\"]");

	
	//Seccion Fecha
	private By CEDIS_RadioFecha = By.xpath("//android.widget.RadioButton[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/radiobutton_fecha_mercancia\" and @text=\"Fecha\"]");	
	private By CEDIS_CapturarFecha = By.xpath("//android.widget.EditText[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/et_verificacion_mercancia_fecha\" and @text=\"Ej: DD/MM/AAAA\"]");
	private By CEDIS_Calendario = By.xpath("//android.widget.ImageView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/imageCalendarFilter\"]");
	private By CEDIS_Año = By.xpath("//android.widget.TextView[@resource-id=\"android:id/date_picker_header_year\"]");
	private By CEDIS_AceptarMensajeDesplegable = By.xpath("//android.widget.Button[@resource-id='android:id/button1']");
	//Al seleccionar Año se desplegara  la lista de años, el seleccionar un año especifico se dejo dentro del metodo SeleccionarFecha
	private By CEDIS_FechaActual = By.xpath("//android.widget.TextView[@resource-id=\"android:id/date_picker_header_date\"]");	
	private By CEDIS_CancelarMes = By.xpath("//android.widget.Button[@resource-id=\"android:id/button2\" and @text=\"CANCELAR\"]");
	private By CEDIS_AceptarMes = By.xpath("//android.widget.Button[@resource-id=\"android:id/button1\" and @text=\"ACEPTAR\"]");
	private By CEDIS_MesActual = By.xpath("//android.view.View[@resource-id=\"android:id/month_view\"]");	
	private By CEDIS_MesAnterior = By.xpath("//android.widget.ImageButton[@content-desc=\"Mes anterior\"]");
	private By CEDIS_MesSiguiente = By.xpath("//android.widget.ImageButton[@content-desc=\"Mes siguiente\"]");	
	
	//Seccion Navegación
	private By CEDIS_ColumnaTransferencia = By.xpath("//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/tvTitleTransferenciaHeader\" and @text=\"TRANSFERENCIA\"]");
	private By CEDIS_ColumnaRecepcion = By.xpath("//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/tvTitleFechaHeader\" and @text=\"RECEPCIÓN\"]");
	private By CEDIS_NavegacionIzquierda = By.xpath("//android.widget.ImageButton[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/button_left_filter_mercancia\"]");
	private By CEDIS_NavegacionDerecha = By.xpath("//android.widget.ImageButton[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/button_rigth_filter_mercancia\"]");
	private By CEDIS_Pagina = By.xpath("//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/text_view_page\"]");	
    
	//Seccion FUE
	private By CEDIS_LeyendaEntregados = By.xpath("//android.widget.TextView[@text=\"ENTREGADOS\"]");
	private By CEDIS_LeyendaCajasSinEtiqueta = By.xpath("//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/tvWOFUE\"]");
	private By CEDIS_LeyendaSobrantes = By.xpath("//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/textSDob\"]");
	private By CEDIS_LeyendaFaltantes = By.xpath("//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/textView36\"]");
	private By CEDIS_LeyendaDanados = By.xpath("//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/textDdob\"]");
	private By CEDIS_LeyendaCanastillasRefrigerados = By.xpath("//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/tvContainerType\" and @text=\"Canastillas Refrigerados\"]");
	private By CEDIS_LeyendaCajas = By.xpath("//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/tvContainerType\" and @text=\"Cajas\"]");
	private By CEDIS_LeyendaFirmasRecepcion = By.xpath("//android.widget.TextView[@text=\"FIRMA FALTANTE/SOBRANTE/DAÑADO\"]");
	private By CEDIS_LeyendaFirmaAreaDistribucion = By.xpath("//android.widget.TextView[@text=\"Firma Área de distribución\"]");
	private By CEDIS_LeyendaDevolucionCanastillas = By.xpath("//android.widget.TextView[@text=\"DEVOLUCIÓN DE CANASTILLAS\"]");
	private By CEDIS_LeyendaCanastillasConDevolucion = By.xpath("//android.widget.TextView[@text=\"CANASTILLAS CON DEVOLUCIÓN\"]");
	private By CEDIS_LeyendaDevolucionContenedores = By.xpath("//android.widget.TextView[@text=\"DEVOLUCIÓN DE CONTENEDORES\"]");
	private By CEDIS_LeyendaFirmaDevoluciones = By.xpath("//android.widget.TextView[@text=\"FIRMA DEVOLUCIONES\"]");
	private By CEDIS_LeyendaObservaciones = By.xpath("//android.widget.TextView[@text=\"Observaciones Área de distribución\"]");
	private By CEDIS_LeyendaMovimientoTienda = By.xpath("//android.widget.TextView[@text=\"Movimiento Tienda\"]");
	private By CEDIS_BotonVolver = By.xpath("//android.widget.Button[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/btnFinish\"]");

	private By CEDIS_Token = By.xpath("//android.widget.Toast[@text=\"El Token ha expirado\"]");
	private By CEDIS_Reintentar = By.xpath("//android.widget.Button[@resource-id=\"android:id/button1\"and @text=\"REINTENTAR\"]");
	private By CEDIS_Salir = By.xpath("//android.widget.Button[@resource-id=\"android:id/button2\"and @text=\"SALIR\"]");
	private By CEDIS_Cargando = By.xpath("//android.widget.TextView[@text=\"Cargando\"]");
	

    /** 
     * Constructor
     * *****************************************************************************************/
	/**
	 * Constructor de la clase de la pantalla de Consulta FUE Digital
	 * @author Juan Ibarra
	 * @param au (AppiumUtil): objeto de la clase 
	 * @param testCase (TestCase): objeto de la clase
	 * @param baseExecution (BaseExecution): objeto de la clase
	 */
    public E01_ConsultaFUE(TestCase testCase, AppiumUtil au, BaseExecution baseExecution) {
        this.testCase = testCase;
        this.au = au;
        this.baseExecution = baseExecution;
    }
    
        
    public void SeleccionarObjeto(String ObjetoPantalla)
    {  	
    	//Funcion para buscar objeto y dar click especifico en la pantalla Creado: Juan Ibarra    	
    	switch (ObjetoPantalla) {
    	case "Radio Transferencia":
    		au.scrollToElement(CEDIS_RadioTransferencia);
    		au.click(CEDIS_RadioTransferencia);
            break;
    	case "Campo Transferencia":
    		au.scrollToElement(CEDIS_CampoTransferencia);
    		au.click(CEDIS_CampoTransferencia);
            break;
    	case "Buscar":
    		au.scrollToElement(CEDIS_Buscar);
    		au.click(CEDIS_Buscar);
            break;            
    	case "Radio Fecha":
    		au.scrollToElement(CEDIS_RadioFecha);
    		au.click(CEDIS_RadioFecha);
            break;    	
    	case "Capturar Fecha":
    		au.scrollToElement(CEDIS_CapturarFecha);
    		au.click(CEDIS_CapturarFecha);
            break;
    	case "Calendario":
    		au.scrollToElement(CEDIS_Calendario);
    		au.click(CEDIS_Calendario);
            break;
    	case "Cancelar Fecha":
    		au.scrollToElement(CEDIS_CancelarMes);
    		au.click(CEDIS_CancelarMes);
            break;
    	case "Aceptar Fecha":
    		au.scrollToElement(CEDIS_AceptarMes);
    		au.click(CEDIS_AceptarMes);
            break;
    	case "Mes Anterior":
    		au.scrollToElement(CEDIS_MesAnterior);
    		au.click(CEDIS_MesAnterior);
            break;
    	case "Mes Siguiente":
    		au.scrollToElement(CEDIS_MesSiguiente);
    		au.click(CEDIS_MesSiguiente);
            break;
    	case "Navegacion Izquierda":
    		au.scrollToElement(CEDIS_NavegacionIzquierda);
    		au.click(CEDIS_NavegacionIzquierda);
            break;
    	case "Navegacion Derecha":
    		au.scrollToElement(CEDIS_NavegacionDerecha);
    		au.click(CEDIS_NavegacionDerecha);
            break;            
    	case "Aceptar Mensaje":
    		au.click(CEDIS_AceptarMensajeDesplegable);
            break;
    	case "Entrar Primer Transferencia":
    		au.scrollToElement(CEDIS_PrimerTransferenciaListada);
        	au.click(CEDIS_PrimerTransferenciaListada);
            break;
        default:
            baseExecution.addStep("Se falla el caso");
            //Se agrega el paso a mostrar en el PDF
    		Assert.fail("No se ha encontrado el objeto "+ ObjetoPantalla, null);
    		//Se agrega un mensaje a mostrar del error, en caso de no ponerlo regresaria leyenda de null
    	}    	
    }
        
    public void ValidacionEncabezado() throws IOException, InterruptedException {
    	//Funcion que permite validar que se muestran los campo correspondientes al encabezado de la pantalla  Creado: Juan Ibarra	 Fecha Creación: 31 Octubre 2024
    	Thread.sleep(1000);    	
    	if(au.driver.findElement(CEDIS_TituloPantalla).isEnabled()==false) {
    		baseExecution.addStep("Se falla el caso");        
    		Assert.fail("No se ha encontrado el Titulo pantalla FORMATO ÚNICO DE ENTREGA en la parte de inicio", null);
    	}    	
    	if (au.driver.findElements(CEDIS_Fecha).isEmpty()!= false) {    		
    		baseExecution.addStep("Se falla el caso");        
    		Assert.fail("No se ha encontrado el campo de Fecha de la pantalla de FORMATO ÚNICO DE ENTREGA en la parte de inicio", null);
    	}    	
    	if(au.driver.findElements(CEDIS_ValorFecha).isEmpty()!= false) {
    		baseExecution.addStep("Se falla el caso");        
    		Assert.fail("No se ha encontrado el campo de Valor Fecha de la pantalla de FORMATO ÚNICO DE ENTREGA en la parte de inicio", null);
    	}    	
    	if(au.driver.findElements(CEDIS_CRTienda).isEmpty()!= false) {
    		baseExecution.addStep("Se falla el caso");        
    		Assert.fail("No se ha encontrado el campo de CR Tienda de la pantalla de FORMATO ÚNICO DE ENTREGA en la parte de inicio", null);
    	}
    	if(au.driver.findElements(CEDIS_NombreTienda).isEmpty()!= false) {
    		baseExecution.addStep("Se falla el caso");        
    		Assert.fail("No se ha encontrado el campo de Nombre Tienda de la pantalla de FORMATO ÚNICO DE ENTREGA en la parte de inicio", null);
    	}    	
    }    
    
    public void ValidacionCampos() throws IOException, InterruptedException {
    	//Funcion que permite validar que se muestran los campo correspondientes al encabezado de la pantalla  Creado: Juan Ibarra	 Fecha Creación: 31 Octubre 2024
    	Thread.sleep(1000);    	
    	if(au.driver.findElement(CEDIS_RadioTransferencia).isEnabled()==false) {
    		baseExecution.addStep("Se falla el caso");        
    		Assert.fail("No se ha encontrado el radio botón de Transferencia en la pantalla", null);
    	}    	
    	if (au.driver.findElements(CEDIS_RadioFecha).isEmpty()!= false) {    		
    		baseExecution.addStep("Se falla el caso");        
    		Assert.fail("No se ha encontrado el radio botón de Fecha en la pantalla", null);
    	}    	
    	if(au.driver.findElements(CEDIS_ColumnaTransferencia).isEmpty()!= false) {
    		baseExecution.addStep("Se falla el caso");        
    		Assert.fail("No se ha encontrado la columna de Transferencia en la pantalla", null);
    	}    	
    	if(au.driver.findElements(CEDIS_ColumnaRecepcion).isEmpty()!= false) {
    		baseExecution.addStep("Se falla el caso");        
    		Assert.fail("No se ha encontrado la columna de Recepción en la pantalla", null);
    	}
    	if(au.driver.findElements(CEDIS_NavegacionIzquierda).isEmpty()!= false) {
    		baseExecution.addStep("Se falla el caso");        
    		Assert.fail("No se ha encontrado la fecha de navegación izquierda en la pantalla", null);
    	}
    	if(au.driver.findElements(CEDIS_NavegacionDerecha).isEmpty()!= false) {
    		baseExecution.addStep("Se falla el caso");        
    		Assert.fail("No se ha encontrado la fecha de navegación derecha en la pantalla", null);
    	}
    }
    
    public void IngresarFechaPosterior() throws IOException, InterruptedException {
        System.out.println("Iniciando selección de fecha...");

        // Obtener la fecha actual
        LocalDate hoy = LocalDate.now();
        System.out.println("Fecha actual del sistema: " + hoy);

        // Incrementar la fecha (por ejemplo, 1 día después)
        LocalDate fechaPosterior = hoy.plusDays(1);
        System.out.println("Fecha seleccionada (un día después): " + fechaPosterior);

        // Formatear la fecha en el formato esperado (ejemplo: "07 febrero 2025")
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMMM yyyy", new Locale("es", "ES"));
        String fechaFormateada = fechaPosterior.format(formatter).toLowerCase();
        System.out.println("Fecha formateada para XPath: " + fechaFormateada);

        // Construir el xpath dinámicamente
        String xpathFecha = "//android.view.View[@content-desc=\"" + fechaFormateada + "\"]";
        System.out.println("XPath generado: " + xpathFecha);

        // Buscar y seleccionar la fecha
        try {
            WebElement fechaElemento = au.driver.findElement(By.xpath(xpathFecha));
            System.out.println("Elemento encontrado, realizando clic...");
            fechaElemento.click();
            System.out.println("Fecha seleccionada exitosamente.");
        } catch (Exception e) {
            System.err.println("ERROR: No se pudo encontrar o hacer clic en la fecha.");
            e.printStackTrace();
        }
    }

    public void ValidarIngresoTransferencia() throws IOException, InterruptedException {
        System.out.println("Iniciando validación del ingreso de transferencia...");

        // Pausa de 1 segundo
        Thread.sleep(1000);
        System.out.println("Esperando 1 segundo para asegurar carga de elementos...");

        // Verificar si el campo de ingreso está habilitado
        WebElement campoTransferencia = au.driver.findElement(CEDIS_CampoTransferencia);
        if (!campoTransferencia.isEnabled()) {
            System.out.println("ERROR: El campo de transferencia no está habilitado.");
            baseExecution.addStep("Se falla el caso");
            testCase.addScreenShotCurrentStep(au, "testImage");
            Assert.fail("No se ha desplegado correctamente el campo de ingreso de número de transferencia", null);
        }
        System.out.println("Campo de transferencia habilitado correctamente.");

        // Obtener valores de transferencia
        String TransferenciaEsperada = campoTransferencia.getText();
        System.out.println("Transferencia esperada (capturada del campo de ingreso): " + TransferenciaEsperada);

        WebElement primerTransferencia = au.driver.findElement(CEDIS_LeyendaPrimerTransferenciaListada);
        String TransferenciaActual = primerTransferencia.getText();
        System.out.println("Transferencia actual (capturada del listado): " + TransferenciaActual);

        // Comparación
        if (!TransferenciaActual.equals(TransferenciaEsperada)) { // Cambié '==' por '.equals()'
            System.out.println("ERROR: La transferencia listada no coincide con la esperada.");
            baseExecution.addStep("Se falla el caso");
            testCase.addScreenShotCurrentStep(au, "testImage");
            Assert.fail("No se ha desplegado correctamente el campo de ingreso de número de transferencia", null);
        }
        System.out.println("Validación exitosa: la transferencia ingresada coincide con la esperada.");
    }
    
    public void ValidarIngresoFecha() throws IOException, InterruptedException {
        System.out.println("Iniciando validación del ingreso de transferencia...");

        // Pausa de 1 segundo
        Thread.sleep(1000);
        System.out.println("Esperando 1 segundo para asegurar carga de elementos...");

        // Verificar si el campo de ingreso está habilitado
        WebElement campoFecha = au.driver.findElement(CEDIS_CampoFecha);
        if (!campoFecha.isEnabled()) {
            System.out.println("ERROR: El campo de fecha no está habilitado.");
            baseExecution.addStep("Se falla el caso");
            testCase.addScreenShotCurrentStep(au, "testImage");
            Assert.fail("No se ha desplegado correctamente el campo de ingreso de fecha", null);
        }
        System.out.println("Campo de transferencia habilitado correctamente.");

        // Obtener valores de transferencia
        String FechaEsperada = campoFecha.getText();
        System.out.println("Transferencia esperada (capturada del campo de ingreso): " + FechaEsperada);

        WebElement primerFecha = au.driver.findElement(CEDIS_LeyendaPrimerFechaTransferenciaListada);
        String FechaActual = primerFecha.getText();
        System.out.println("Transferencia actual (capturada del listado): " + FechaActual);

        // Comparación
        if (!FechaActual.equals(FechaEsperada)) { // Cambié '==' por '.equals()'
            System.out.println("ERROR: La transferencia listada no coincide con la esperada.");
            baseExecution.addStep("Se falla el caso");
            testCase.addScreenShotCurrentStep(au, "testImage");
            Assert.fail("No se ha desplegado correctamente el campo de ingreso de número de transferencia", null);
        }
        System.out.println("Validación exitosa: la transferencia ingresada coincide con la esperada.");
    }
    
    public void ValidarDespliegueCalendario() throws IOException, InterruptedException {
        Thread.sleep(1000);
        WebElement calendario = au.driver.findElement(CEDIS_Calendario);
        if(!calendario.isDisplayed()) {
    		baseExecution.addStep("Se falla el caso");        
            Assert.fail("No se ha desplegado correctamente el calendario", null);
            testCase.addScreenShotCurrentStep(au, "testImage");
    	}
    }
    
    public void ValidarMensajeFechaIncorrecta() throws IOException, InterruptedException {
        Thread.sleep(1000);
        WebElement Mensaje = au.driver.findElement(CEDIS_MensajeFechaIncorrecta);
        String MensajeActual = Mensaje.getText();
        String MensajeEsperado = "No se encontró un pedido en la fecha previamente consultada";
        
        
        if(!MensajeActual.equals(MensajeEsperado)) {
    		baseExecution.addStep("Se falla el caso");        
            Assert.fail("No se ha desplegado correctamente el mensaje y/o el texto del mismo", null);
            testCase.addScreenShotCurrentStep(au, "testImage");
    	}
    }
    
    public void ValidarMensajeTransferenciaIncorrecta() throws IOException, InterruptedException {
        Thread.sleep(1000);
        WebElement Mensaje = au.driver.findElement(CEDIS_MensajeTransferenciaIncorrecta);
        String MensajeActual = Mensaje.getText();
        String MensajeEsperado = "El número de pedido ingresado no existe o fue recibido por punto de venta (XPOS)";
        
        System.out.println("Actual: " + MensajeActual);
        System.out.println("Esperado: " + MensajeEsperado);
        
        
        if(!MensajeActual.equals(MensajeEsperado)) {
    		baseExecution.addStep("Se falla el caso");        
            Assert.fail("No se ha desplegado correctamente el mensaje y/o el texto del mismo", null);
            testCase.addScreenShotCurrentStep(au, "testImage");
    	}
    }
    
    public void ValidarOrdenTransferencias() throws IOException, InterruptedException {
        // Esperar un momento para asegurar que las fechas estén visibles
        Thread.sleep(1000);

        // Obtener las fechas de las transferencias listadas
        WebElement FechaRecienteWe = au.driver.findElement(CEDIS_LeyendaPrimerFechaTransferenciaListada);
        WebElement FechaAntiguaWe = au.driver.findElement(CEDIS_LeyendaSegundaFechaTransferenciaListada);
        WebElement FechaMasAntiguaWe = au.driver.findElement(CEDIS_LeyendaTerceraFechaTransferenciaListada);

        // Extraer los valores de las fechas
        String FechaRecientetxt = FechaRecienteWe.getText();
        String FechaAntiguatxt = FechaAntiguaWe.getText();
        String FechaMasAntiguatxt = FechaMasAntiguaWe.getText();

        // Formato para convertir los textos de fechas en objetos Date
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

        try {
            // Convertir los textos de fecha a Date
            Date FechaReciente = sdf.parse(FechaRecientetxt);
            Date FechaAntigua = sdf.parse(FechaAntiguatxt);
            Date FechaMasAntigua = sdf.parse(FechaMasAntiguatxt);

            // Validar que las fechas estén ordenadas de la más reciente a la más antigua
            if (!(FechaReciente.compareTo(FechaAntigua) >= 0 && FechaAntigua.compareTo(FechaMasAntigua) >= 0)) {
                baseExecution.addStep("Se falla el caso");
                Assert.fail("No se ha realizado el orden correcto de despliegue de transferencias", null);
                testCase.addScreenShotCurrentStep(au, "testImage");
            }
        } catch (ParseException e) {
            // Capturar error si la conversión de fecha falla
            Assert.fail("Error en la conversión de fechas", e);
        }
    }
    
    public void ValidacionTituloPantallaYDatosGenerales() throws IOException {
    	if(au.driver.findElement(CEDIS_TituloPantallaFUE).isEnabled()==false) {
    		baseExecution.addStep("No se ha encontrado el Titulo: FORMATO ÚNICO DE ENTREGA");        
    		Assert.fail("No se ha encontrado el Titulo: FORMATO ÚNICO DE ENTREGA", null);
            testCase.addScreenShotCurrentStep(au, "testImage");
    	}    	
    	if (au.driver.findElements(CEDIS_ImagenTienda).isEmpty()!= false) {    		
    		baseExecution.addStep("No se ha encontrado la Imagen de la Tienda de OXXO");        
    		Assert.fail("No se ha encontrado la Imagen de la Tiena de OXXO", null);
            testCase.addScreenShotCurrentStep(au, "testImage");
    	}    	
    	if(au.driver.findElements(CEDIS_Fecha).isEmpty()!= false) {
    		baseExecution.addStep("No se ha encontrado la Fecha");        
    		Assert.fail("No se ha encontrado la Fecha", null);
            testCase.addScreenShotCurrentStep(au, "testImage");
    	}    	
    	if(au.driver.findElements(CEDIS_Ruta).isEmpty()!= false) {
    		baseExecution.addStep("No se ha encontrado la Ruta");        
    		Assert.fail("No se ha encontrado la Ruta", null);
            testCase.addScreenShotCurrentStep(au, "testImage");
    	}    	
    	if(au.driver.findElements(CEDIS_RutaDinamica).isEmpty()!= false) {
    		baseExecution.addStep("No se ha encontrado la Ruta Dinamica");        
    		Assert.fail("No se ha encontrado la Ruta Dinamica", null);
            testCase.addScreenShotCurrentStep(au, "testImage");
    	}
    	if(au.driver.findElements(CEDIS_CRTienda).isEmpty()!= false) {
    		baseExecution.addStep("No se ha encontrado el CR Tienda");        
    		Assert.fail("No se ha encontrado el CR Tienda", null);
            testCase.addScreenShotCurrentStep(au, "testImage");
    	}
    	if(au.driver.findElements(CEDIS_NombreTienda).isEmpty()!= false) {
    		baseExecution.addStep("No se ha encontrado el Nombre Tienda");        
    		Assert.fail("No se ha encontrado el Nombre Tienda", null);
            testCase.addScreenShotCurrentStep(au, "testImage");
    	}
    	if(au.driver.findElements(CEDIS_LiderTienda).isEmpty()!= false) {
    		baseExecution.addStep("No se ha encontrado el Lider Tienda");        
    		Assert.fail("No se ha encontrado el Lider Tienda", null);
            testCase.addScreenShotCurrentStep(au, "testImage");
    	}
    	if(au.driver.findElements(CEDIS_Transferencia).isEmpty()!= false) {
    		baseExecution.addStep("No se ha encontrado la Transferencia");        
    		Assert.fail("No se ha encontrado la Transferencia", null);
            testCase.addScreenShotCurrentStep(au, "testImage");
    	}    	
    }
    
    public void ValidacionEntregados() throws IOException {
    	au.scrollToElement(CEDIS_LeyendaCajasSinEtiqueta);
    
    	if(au.driver.findElement(CEDIS_LeyendaEntregados).isEnabled() == false) {
    		baseExecution.addStep("No se ha encontrado la Sección: ENTREGADOS");        
    		Assert.fail("No se ha encontrado la Sección: ENTREGADOS", null);
            testCase.addScreenShotCurrentStep(au, "testImage");
    	}
    }
    
    public void ValidacionCajaSinEtiqueta() throws IOException {
    	au.scrollToElement(CEDIS_LeyendaFaltantes);
    
    	if(au.driver.findElement(CEDIS_LeyendaCajasSinEtiqueta).isEnabled() == false) {
    		baseExecution.addStep("No se ha encontrado la Sección: Cajas Sin Etiqueta");        
    		Assert.fail("No se ha encontrado la Sección: Cajas Sin Etiqueta", null);
            testCase.addScreenShotCurrentStep(au, "testImage");
    	}
    }
    
    public void ValidacionFaltantes() throws IOException {
    	au.scrollToElement(CEDIS_LeyendaCajas);
    
    	if(au.driver.findElement(CEDIS_LeyendaFaltantes).isEnabled() == false) {
    		baseExecution.addStep("No se ha encontrado la Sección: Faltantes");        
    		Assert.fail("No se ha encontrado la Sección: Faltantes", null);
            testCase.addScreenShotCurrentStep(au, "testImage");
    	}
    }
    
    public void ValidacionSobrantes() throws IOException {
    	au.scrollToElement(CEDIS_LeyendaDanados);
    
    	if(au.driver.findElement(CEDIS_LeyendaSobrantes).isEnabled() == false) {
    		baseExecution.addStep("No se ha encontrado la Sección: Sobrantes");        
    		Assert.fail("No se ha encontrado la Sección: Sobrantes", null);
            testCase.addScreenShotCurrentStep(au, "testImage");
    	}
    }
    
    public void ValidacionDanados() throws IOException {
    	au.scrollToElement(CEDIS_LeyendaFirmasRecepcion);
    	
    	if(au.driver.findElement(CEDIS_LeyendaDanados).isEnabled() == false) {
    		baseExecution.addStep("No se ha encontrado la Sección: Danados");        
    		Assert.fail("No se ha encontrado la Sección: Danados", null);
            testCase.addScreenShotCurrentStep(au, "testImage");
    	}
    }
    
    public void ValidacionFirmaRecepcion() throws IOException {
    	au.scrollToElement(CEDIS_LeyendaFirmaAreaDistribucion);
    	
    	if(au.driver.findElement(CEDIS_LeyendaFirmasRecepcion).isEnabled() == false) {
    		baseExecution.addStep("No se ha encontrado la Sección: Firma Faltante/Sobrante/Danado");        
    		Assert.fail("No se ha encontrado la Sección: Firma Faltante/Sobrante/Danado", null);
            testCase.addScreenShotCurrentStep(au, "testImage");
    	}
    }
    
    public void ValidacionDevolucionCanastillas() throws IOException {
    	au.scrollToElement(CEDIS_LeyendaCanastillasConDevolucion);
    	
    	if(au.driver.findElement(CEDIS_LeyendaDevolucionCanastillas).isEnabled() == false) {
    		baseExecution.addStep("No se ha encontrado la Sección: Devolucion de Canastillas");        
    		Assert.fail("No se ha encontrado la Sección: Devolucion de Canastillas", null);
            testCase.addScreenShotCurrentStep(au, "testImage");
    	}
    }
    
    public void ValidacionCanastillasConDevolucion() throws IOException {
    	au.scrollToElement(CEDIS_LeyendaDevolucionContenedores);
    	
    	if(au.driver.findElement(CEDIS_LeyendaCanastillasConDevolucion).isEnabled() == false) {
    		baseExecution.addStep("No se ha encontrado la Sección: Canastillas con Devolucion");        
    		Assert.fail("No se ha encontrado la Sección: Canastillas con Devolucion", null);
            testCase.addScreenShotCurrentStep(au, "testImage");
    	}
    }
    
    public void ValidacionDevolucionContenedores() throws IOException {
    	au.scrollToElement(CEDIS_LeyendaFirmaDevoluciones);
    	
    	if(au.driver.findElement(CEDIS_LeyendaDevolucionContenedores).isEnabled() == false) {
    		baseExecution.addStep("No se ha encontrado la Sección: Canastillas con Devolucion");        
    		Assert.fail("No se ha encontrado la Sección: Canastillas con Devolucion", null);
            testCase.addScreenShotCurrentStep(au, "testImage");
    	}
    }
    
    public void ValidacionFirmasDevoluciones() throws IOException {
    	au.scrollToElement(CEDIS_LeyendaObservaciones);
    	
    	if(au.driver.findElement(CEDIS_LeyendaFirmaDevoluciones).isEnabled() == false) {
    		baseExecution.addStep("No se ha encontrado la Sección: Canastillas con Devolucion");        
    		Assert.fail("No se ha encontrado la Sección: Canastillas con Devolucion", null);
            testCase.addScreenShotCurrentStep(au, "testImage");
    	}
    }
    
    public void ValidacionMovimientoTiendaYBotonVolver() throws IOException {
    	au.scrollToElement(CEDIS_BotonVolver);
    	
    	if(au.driver.findElement(CEDIS_LeyendaMovimientoTienda).isEnabled() == false) {
    		baseExecution.addStep("No se ha encontrado la Sección: Canastillas con Devolucion");        
    		Assert.fail("No se ha encontrado la Sección: Canastillas con Devolucion", null);
            testCase.addScreenShotCurrentStep(au, "testImage");
    	}
    	
    	if(au.driver.findElement(CEDIS_BotonVolver).isEnabled() == false) {
    		baseExecution.addStep("No se ha encontrado la Sección: Canastillas con Devolucion");        
    		Assert.fail("No se ha encontrado la Sección: Canastillas con Devolucion", null);
            testCase.addScreenShotCurrentStep(au, "testImage");
    	}
    }
    
    public void IngresoTransferencia() throws IOException, InterruptedException {
    	// Función que hace scroll al listado de transferencias, toma el numero de la primer transferencia y luego lo ingresa en el campo, sin dar clic en el boton de buscar, de 
    	// forma que lo detecte e ingrese de forma dinamica
        // Creado: Marcelo Gutiérrez
        // Fecha Creación: 24 Enero 2025
    	Thread.sleep(1000);  	
    	au.scrollToElement(CEDIS_PrimerTransferenciaListada);
    	Thread.sleep(1000);  	
        try {
            System.out.println("Iniciando la búsqueda del contenedor padre...");
            // XPath para el contenedor padre
            String parentXPath = "(//android.widget.LinearLayout[@resource-id='com.femco.oxxo.operaciones.mitienda.debug:id/ll_body_container_search_verify_item'])[1]";
            
            // Localizar el contenedor padre
            WebElement parentElement = au.driver.findElement(By.xpath(parentXPath));
            System.out.println("Contenedor padre encontrado exitosamente.");

            System.out.println("Buscando el elemento hijo dentro del contenedor...");
            // XPath para el elemento hijo dentro del contenedor
            String childXPath = ".//android.widget.TextView[@resource-id='com.femco.oxxo.operaciones.mitienda.debug:id/tvTitleTransferencia']";
            
            // Localizar el elemento hijo relativo al contenedor
            WebElement childElement = parentElement.findElement(By.xpath(childXPath));
            System.out.println("Elemento hijo encontrado exitosamente.");

            // Obtener el texto del elemento hijo
            String text = childElement.getText();
            System.out.println("Texto del elemento hijo: " + text);
            au.sendKeys(CEDIS_CampoTransferencia, text);
        } catch (NoSuchElementException e) {
            System.out.println("Error: No se encontró uno de los elementos. Verifica los XPath proporcionados.");
            e.printStackTrace();
        } catch (Exception e) {
            System.out.println("Error inesperado al buscar o procesar los elementos.");
            e.printStackTrace();
        }
    }
    
    public void IngresoTransferenciaIncorrecta () throws IOException, InterruptedException {
    	// Función que una vez seleccionado el campo para ingresar transferencia, ingresa un numero dummy, para asi posteriormente arroje el mensaje de error correspondiente
        // Creado: Marcelo Gutiérrez
        // Fecha Creación: 24 Enero 2025
            au.sendKeys(CEDIS_CampoTransferencia, "123456789");

    }
    
    public void IngresoFecha() throws IOException, InterruptedException {
        // Función que permite ingresar la fecha del primer pedido listado de forma dinámica
        // Creado: Marcelo Gutiérrez
        // Fecha Creación: 24 Enero 2025
        Thread.sleep(1000); 
        au.scrollToElement(CEDIS_PrimerTransferenciaListada);
        Thread.sleep(1000); 

        try {
            System.out.println("Iniciando la búsqueda del contenedor padre...");
            // XPath para el contenedor padre
            String parentXPath = "(//android.widget.LinearLayout[@resource-id='com.femco.oxxo.operaciones.mitienda.debug:id/ll_body_container_search_verify_item'])[1]";
            
            // Localizar el contenedor padre
            WebElement parentElement = au.driver.findElement(By.xpath(parentXPath));
            System.out.println("Contenedor padre encontrado exitosamente.");

            System.out.println("Buscando el elemento hijo dentro del contenedor...");
            // XPath para el elemento hijo dentro del contenedor
            String childXPath = "//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/tvTitleFecha\"]";
            
            // Localizar el elemento hijo relativo al contenedor
            WebElement childElement = parentElement.findElement(By.xpath(childXPath));
            System.out.println("Elemento hijo encontrado exitosamente.");

            // Obtener el texto del elemento hijo
            String text = childElement.getText();
            System.out.println("Texto del elemento hijo: " + text);

            // Aquí envías el texto al campo de transferencia
            au.sendKeys(CEDIS_CapturarFecha, text);

        } catch (NoSuchElementException e) {
            System.out.println("Error: No se encontró uno de los elementos. Verifica los XPath proporcionados.");
            e.printStackTrace();
        } catch (Exception e) {
            System.out.println("Error inesperado al buscar o procesar los elementos.");
            e.printStackTrace();
        }
    }

    public void EntrarEnPrimerTransferencia () throws IOException, InterruptedException {
    	// Función que detecta la primer transferencia listada, la ingresa en el campo, la busca y entra, de forma dinamica
        // Creado: Marcelo Gutiérrez
        // Fecha Creación: 24 Enero 2025
    	Thread.sleep(1000);  	
    	au.scrollToElement(CEDIS_PrimerTransferenciaListada);
    	Thread.sleep(1000);  	
        try {
            System.out.println("Iniciando la búsqueda del contenedor padre...");
            // XPath para el contenedor padre
            String parentXPath = "(//android.widget.LinearLayout[@resource-id='com.femco.oxxo.operaciones.mitienda.debug:id/ll_body_container_search_verify_item'])[1]";
            
            // Localizar el contenedor padre
            WebElement parentElement = au.driver.findElement(By.xpath(parentXPath));
            System.out.println("Contenedor padre encontrado exitosamente.");

            System.out.println("Buscando el elemento hijo dentro del contenedor...");
            // XPath para el elemento hijo dentro del contenedor
            String childXPath = ".//android.widget.TextView[@resource-id='com.femco.oxxo.operaciones.mitienda.debug:id/tvTitleTransferencia']";
            
            // Localizar el elemento hijo relativo al contenedor
            WebElement childElement = parentElement.findElement(By.xpath(childXPath));
            System.out.println("Elemento hijo encontrado exitosamente.");

            // Obtener el texto del elemento hijo
            String text = childElement.getText();
            System.out.println("Texto del elemento hijo: " + text);
            au.sendKeys(CEDIS_CampoTransferencia, text);
        	Thread.sleep(1000);  	
        	au.click(CEDIS_PrimerTransferenciaListada);
        	Thread.sleep(1000);  	
        } catch (NoSuchElementException e) {
            System.out.println("Error: No se encontró uno de los elementos. Verifica los XPath proporcionados.");
            e.printStackTrace();
        } catch (Exception e) {
            System.out.println("Error inesperado al buscar o procesar los elementos.");
            e.printStackTrace();
        }
    }
    
    
    public void BusquedaTransferencia (String Transferencia) throws IOException, InterruptedException {
    	//Funcion que permite realizar la busqueda de una transferncia especifica	Creado: Juan Ibarra	 Fecha Creación: 01 Noviembre 2024	Pendiente validar con transferencias listaddas 
    	au.click(CEDIS_RadioTransferencia);    	
    	au.driver.hideKeyboard();
        au.sendKeys(CEDIS_CampoTransferencia, Transferencia);       
        //Thread.sleep(1000);
        //au.click(CEDIS_CampoTransferencia);
        //Runtime.getRuntime().exec("adb shell input keyevent 4");// realiza la acción de Back en la handheld
        au.click(CEDIS_Buscar);
    }
    
    public void CapturarFecha (String Fecha) throws IOException, InterruptedException {
    	//Funcion que permite realizar la busqueda de una transferncia especifica	Creado: Juan Ibarra	 Fecha Creación: 01 Noviembre 2024	Pendiente validar 
    	au.click(CEDIS_RadioTransferencia);    	
    	au.driver.hideKeyboard();
        au.sendKeys(CEDIS_CapturarFecha, Fecha);       
        //Thread.sleep(1000);
        //au.click(CEDIS_CampoTransferencia);            
        Runtime.getRuntime().exec("adb shell input keyevent 66");//realiza el enter en la handheld   	    	
    }
    
    public void SeleccionarFecha (String Fecha) throws IOException, InterruptedException {
    	//Funcion que permite realizar la busqueda de una fecha en especifica en el mes actual o meses anteriores 	Creado: Juan Ibarra	 Fecha Creación: 01 Noviembre 2024
    	//Se requiere caapturar el dato de fecha con formato dd/mm/aaaa
    	au.click(CEDIS_RadioFecha);
    	au.click(CEDIS_Calendario);
    	//au.click(CEDIS_Año);    	    	
    	//String Anio = Fecha.substring(6);    	
    	String Mes = Fecha.substring(3,5);    	
    	int  MesEntero = Integer.parseInt(Mes);//Se cambia el valor del mes de string a entero para realizar la busqueda despues 
    	String Dia = Fecha.substring(0,2);    	
    	//By anio = By.xpath("//android.widget.TextView[@resource-id=\"android:id/text1\" and @text=\""+Anio+"\"]");//Seleccionar 
    	//au.click(anio);
    	WebElement elemento = au.driver.findElement(CEDIS_FechaActual);
    	String MesActual = elemento.getAttribute("text");    	
    	String valorMes = MesActual.substring(11);    	    	
    	switch (valorMes) {//Se obtiene el valor del mes actual para posteriormente ser usado en la selecccion del mes solicitado
    	case "ene.":
    		valorMes = "01";
    		break;
    	case "feb.":
    		valorMes = "02";
    		break;
    	case "mar.":
    		valorMes = "03";
    		break;
    	case "abr.":
    		valorMes = "04";    		
    		break;
    	case "may.":
    		valorMes = "05";    		
    		break;
    	case "jun.":
    		valorMes = "06";    		
    		break;
    	case "jul.":
    		valorMes = "07";
    		break;
    	case "ago.":
    		valorMes = "08";
    		break;
    	case "sep.":
    		valorMes = "09";
    		break;
    	case "oct.":
    		valorMes = "10";
    		break;
    	case "nov.":
    		valorMes = "11";
    		break;
    	case "dic.":
    		valorMes = "12";
    		break;
    	default:
    		  System.out.println("No capturo un valor valido de la fecha " + valorMes);
    		  baseExecution.addStep("Se falla el caso, No capturo un valor valido de la fecha " + valorMes);
    	}
    	
    	int valorMesNumero = Integer.parseInt(valorMes);//Se cambia el valo del mes de string a entero    	
    	if (valorMesNumero != MesEntero) {//Se valida si el mes actual no es el que se requiere     		
    		while (valorMesNumero != MesEntero) {
        		au.click(CEDIS_MesAnterior);//Se avanza hasta encontrar el mes esperado        		
        		valorMesNumero = valorMesNumero-1;
        	}    		
    	}
    	    	
    	Dia = Dia.replaceFirst("^0", ""); //Elimina el cero al inicia en caso de contar con una feha del 01 al 09    	
    	By diaseleccionar = AppiumBy.androidUIAutomator("new UiSelector().text(\""+Dia+"\")"); //Patametriza el valor del día a seleccionar en pantalla
    	au.click(diaseleccionar);//Se selecciona el día deseado    
    }
   
    public String BuscarPrimeraTransferencia () throws IOException, InterruptedException {
    	//Funcion que permite obtener el valor de la primera transferncia mostrada la cual se regresa como valor para su busqueda	
    	//Creado: Juan Ibarra	 Fecha Creación: 25 Febrero 2025	 
    	WebElement transferencia = au.driver.findElement(CEDIS_LeyendaPrimerTransferenciaListada);
        String valorTransferencia = transferencia.getAttribute("text");        
        return valorTransferencia;        
    }
    
    public void Cargando() throws InterruptedException, IOException{  	
    	//Funcion que permite dar un tiempo de espera en la carga de la pantalla ademas de validar que no muestre el mensaje de Token	Creado: Juan Ibarra		Fecha Creación: 03-Marzo-2025    	
    	int intentos = 0;
    	Thread.sleep(500);
    	while (intentos < 10 ) {
    		
    		if(au.driver.findElements(CEDIS_LeyendaPrimerTransferenciaListada).isEmpty()!= true ) {
    			System.out.println("La pantalla ya no muestra la barra de carga en la pantalla de Consulta FUE Digitla");
    			break;
        	}    		
    		intentos++;
    	  	    	
    	try {
			// Espera el tiempo determinado antes de volver a intentar
            System.out.println("Campo no encontrado. Intentando nuevamente, esperando que deje de cargar la pantalla");
            Thread.sleep(100);                
        } catch (InterruptedException e) {
            System.err.println("Error al esperar entre intentos: " + e.getMessage());               
            System.out.println("La pantalla no muestra la pantalla de Consulta FUE Digitla");
			break;  		    
        }
    	
    	Thread.sleep(5000);
    	
    	if (au.driver.findElements(CEDIS_Reintentar).isEmpty()!= true) {    		
    		au.click(CEDIS_Reintentar);
    		Cargando();
    	}	
			
	    	//if(au.driver.findElements(CEDIS_Token).isEmpty()) {
	    	//if(au.driver.findElement(CEDIS_Token).isDisplayed()) {    
	    	
	    	if(au.driver.findElements(CEDIS_LeyendaPrimerTransferenciaListada).isEmpty()!= false) {    		
	    		//Se valida que se muestre el mensaje de Token, en caso de mostrarse se logea a Recepción para que deje de mostrarse el Token
	    		Thread.sleep(2000);
	    		if (au.driver.findElements(CEDIS_Reintentar).isEmpty()!= true) {
	        		au.click(CEDIS_Reintentar);
	        		Cargando();
	        	}  
	    		Runtime.getRuntime().exec("adb shell input keyevent 4");// realiza la acción de Back en la handheld	  
	    		Thread.sleep(1000);
	    		if (au.driver.findElements(CEDIS_Reintentar).isEmpty()!= true) {
	        		au.click(CEDIS_Reintentar);
	        		Cargando();
	        	}
	    		Mobile.cedis.om.A04_MenuHerramientas menuCedis = new Mobile.cedis.om.A04_MenuHerramientas(testCase, au, baseExecution);        		
	    		menuCedis.clickBtnRecepcionPedidosCEDIS();// Selecciona la opcion de Recepción de Pedidos CCEDIS
	    		
	    		//Se realiza el logeo en la recepción para que no se presente del defecto productivo de donde marca error de Token 
	    		Mobile.cedis.om.A05_LoginAreaDistribucion loginRuta = new Mobile.cedis.om.A05_LoginAreaDistribucion(testCase, au, baseExecution);
	    		loginRuta.LoginAreaDistribucion();//Se logea en Area de Distribución sin tomar evidencia
	    		
	    		Mobile.cedis.om.B01_RecepcionPedidosSeleccionarTransferencia transferencia = new Mobile.cedis.om.B01_RecepcionPedidosSeleccionarTransferencia(testCase, au, baseExecution);
	    		Thread.sleep(1000);
	    		transferencia.ValidacionEncabezado();		
	    		Runtime.getRuntime().exec("adb shell input keyevent 4");//Se raliza un regreso a la pantalla anterior
	    		Thread.sleep(500);
	    		Runtime.getRuntime().exec("adb shell input keyevent 4");//Se raliza un regreso a la pantalla anterior
	    		menuCedis.clickBtnConsultaFUEDigital();   		
			}
				
    	
    	if (au.driver.findElements(CEDIS_Reintentar).isEmpty()!= true) {
    		au.click(CEDIS_Reintentar);
    		Cargando();
    	}   	  	
    	    	
    	if (intentos>5) {
			baseExecution.addStep("Se falla el caso");
			Assert.fail("Se ha superado el tiempo de tolerancia en cargar la pantalla", null);
    	}
    	}
    }
    
    //Fin OM    
}
    