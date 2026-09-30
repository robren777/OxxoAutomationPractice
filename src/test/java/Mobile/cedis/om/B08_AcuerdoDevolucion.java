package Mobile.cedis.om;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;
import modelo.BaseExecution;
import modelo.TestCase;
import utils.AppiumUtil;

public class B08_AcuerdoDevolucion {
    private TestCase testCase;
	private AppiumUtil au;
	private BaseExecution baseExecution;
	
	//Sección Encabezado
	private By CEDIS_TituloPantalla = By.xpath("(//android.widget.TextView[@text=\"ACUERDO DE DEVOLUCIÓN\"])[1]");
	private By CEDIS_Fecha = By.xpath("//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/tvDateText\" and @text=\"Fecha: \"]");
	private By CEDIS_ValorFecha = By.xpath("//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/tvTitleDate\"]");
	private By CEDIS_Ruta = By.xpath("//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/tvTitleRute\"]");
	private By CEDIS_RutaDinamica = By.xpath("//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/tvTitleRuteD\"]");
	private By CEDIS_CRTienda = By.xpath("//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/tvTitleStore\"]");
	private By CEDIS_NombreTienda = By.xpath("//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/tvTitleNameStore\"]");	
	private By CEDIS_Lider = By.xpath("//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/tvTitleLeaderStore\"]");
	private By CEDIS_Transferencia = By.xpath("//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/tvTransferNumber\"]");
	
	//Sección Observaciones área Distribución
	private By CEDIS_LeyendaAcuerdoDevolucion = By.xpath("//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/text_view_title_acuerdo\" and @text=\"ACUERDO DE DEVOLUCIÓN\"]");
	private By CEDIS_LeyendaObservacionesAreaDistDevolucion = By.xpath("//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/textView40\" and @text=\"Observaciones área distribución\"]");
    private By CEDIS_DesplegableAreaDistribucion = By.xpath("//android.widget.Spinner[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/spinnerObs\"]");
    private By CEDIS_DesplegableDevolucionCorrecta = By.xpath("//android.widget.TextView[@resource-id=\"android:id/text1\" and @text=\"Devolución correcta\"]");
    private By CEDIS_DesplegableSinComentario = By.xpath("//android.widget.TextView[@resource-id=\"android:id/text1\" and @text=\"Sin comentario\"]");
    private By CEDIS_DesplegableOtros = By.xpath("//android.widget.TextView[@resource-id=\"android:id/text1\" and @text=\"Otros\"]");
    private By CEDIS_DetalleOtrosAreaDistribucion = By.xpath("//android.widget.EditText[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/editTxtComDist\"]");
    private By CEDIS_LeyendaFirmaAreaDistribucion = By.xpath("//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/textView41\" and @text=\"Firma Área Distribución\"]");
    private By CEDIS_AreaFirmaDistribucion = By.xpath("//android.widget.ImageView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/ivSignature\"]");
    private By CEDIS_TituloAcuerdo = By.xpath("//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/text_view_title_acuerdo\"]");
    
	//Sección Acuerdo Firma Distribucion
    private By CEDIS_AcuerdoFirmaDistribucion = By.xpath("//android.widget.TextView[@text=\"ACUERDO DE ENTREGA\"]");
    private By CEDIS_PadFirmaDistribucion = By.xpath("//android.view.View[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/signature_pad\"]");
    private By CEDIS_LimpiarFirma = By.xpath("//android.widget.Button[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/clear_button\" and @text=\"LIMPIAR\"]");
    private By CEDIS_AceptarFirma = By.xpath("//android.widget.Button[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/save_button\" and @text=\"ACEPTAR\"]");
    
    //Sección Observaciones Personal Tienda
    private By CEDIS_LeyendaObservacionesPersonalTienda = By.xpath("//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/textView44\" and @text=\"Observaciones Personal de Tienda\"]");
    private By CEDIS_DesplegablePersonalTienda = By.xpath("//android.widget.Spinner[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/spinnerObsTienda\"]");
    private By CEDIS_DesplegableDevolucionCorrectaPersonal = By.xpath("//android.widget.TextView[@resource-id=\"android:id/text1\" and @text=\"Devolución correcta\"]");
    private By CEDIS_DesplegableSinComentarioPersonal = By.xpath("//android.widget.TextView[@resource-id=\"android:id/text1\" and @text=\"Sin comentario\"]");
    private By CEDIS_DesplegableOtrosPersonal = By.xpath("//android.widget.TextView[@resource-id=\"android:id/text1\" and @text=\"Otros\"]");
    private By CEDIS_DetalleOtrosPersonalTienda = By.xpath("//android.widget.EditText[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/editTxtComTienda\"]");
    private By CEDIS_LeyendaFirmaPersonalTienda = By.xpath("//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/textViewtienda\" and @text=\"Firma Personal Tienda\"]");
    private By CEDIS_AreaFirmaPersonalTienda = By.xpath("//android.widget.ImageView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/ivSignature2\"]");


    //Sección Acuerdo Firma Personal Tienda    
    private By CEDIS_AcuerdoFirmaDistribucionPersonal = By.xpath("//android.widget.TextView[@text=\"ACUERDO DE ENTREGA\"]");
    private By CEDIS_PadFirmaDistribucionPersonal = By.xpath("//android.view.View[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/signature_pad\"]");
    private By CEDIS_LimpiarFirmaPersonal = By.xpath("//android.widget.Button[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/clear_button\" and @text=\"LIMPIAR\"]");
    private By CEDIS_AceptarFirmaPersonal = By.xpath("//android.widget.Button[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/save_button\" and @text=\"ACEPTAR\"]");
    
    private By CEDIS_CumplioProcesoEntrega = By.xpath("//android.widget.CheckBox[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/rbCumplimiento\" and @text=\"Se cumplió con el proceso de entrega\"]");
    private By CEDIS_TerminarRecepcion = By.xpath("//android.widget.Button[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/btnTerminarRecepcion\" and @text=\"TERMINAR RECEPCIÓN\"]");
    private By CEDIS_TerminarDevolucion= By.xpath("//android.widget.Button[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/btnTerminarRecepcion\" and @text=\"TERMINAR DEVOLUCIÓN\"]");
    
    //Sección Mensajes
    private By CEDIS_LeyendaComentarioPorAgregar = By.xpath("//android.widget.TextView[@resource-id=\"android:id/message\" and @text=\"El comentario debe ser agregado para continuar\"]");
    private By CEDIS_LeyendaFirmasObligatorias = By.xpath("//android.widget.TextView[@resource-id=\"android:id/message\" and @text=\"Las Firmas son campos obligatorios\"]");
    private By CEDIS_AceptarMensajes = By.xpath("//android.widget.Button[@resource-id=\"android:id/button1\" and @text=\"ACEPTAR\"]");
    
    private By CEDIS_LeyendaSeguroTerminarAcuerdo = By.xpath("//android.widget.TextView[@resource-id=\"android:id/message\" and @text=\"¿Está seguro que ha terminado de realizar la recepción de los bultos del pedido?\"]");
    private By CEDIS_ConfirmarSeguroTerminarAcuerdo = By.xpath("//android.widget.Button[@resource-id=\"android:id/button1\" and @text=\"CONFIRMAR\"]");
    private By CEDIS_CancelarSeguroTerminarAcuerdo = By.xpath("//android.widget.Button[@resource-id=\"android:id/button2\" and @text=\"CANCELAR\"]");
    
    private By CEDIS_LeyendaTerminarRegistro = By.xpath("//android.widget.TextView[@resource-id=\"android:id/message\" and @text=\"¿Está seguro que ha terminado con el registro de las devoluciones de mercancía y canastillas?\"]");
    private By CEDIS_ConfirmarTerminarRegistro  = By.xpath("//android.widget.Button[@resource-id=\"android:id/button1\" and @text=\"CONFIRMAR\"]");
    private By CEDIS_CancelarTerminarRegistro  = By.xpath("//android.widget.Button[@resource-id=\"android:id/button2\" and @text=\"CANCELAR\"]");

    
    /**
     *  Constructor
     *******************************************************************************************/
	/**
	 * Constructor de la clase Login
	 * @author Alejandro Valdez
	 * @param au (AppiumUtil): objeto de la clase 
	 * @param testCase (TestCase): objeto de la clase
	 * @param baseExecution (BaseExecution): objeto de la clase
	 */
    
    public B08_AcuerdoDevolucion(TestCase testCase, AppiumUtil au, BaseExecution baseExecution) {
        this.testCase = testCase;
        this.au = au;
        this.baseExecution = baseExecution;
    }
    
    public void ValidacionEncabezado() throws IOException {
    	//Funcion que permite validar que se muestran los campo correspondientes al encabezado de la pantalla  Creado: Juan Ibarra
    	if(au.driver.findElement(CEDIS_TituloPantalla).isEnabled()==false) {
    		baseExecution.addStep("Se falla el caso");        
    		Assert.fail("No se ha encontrado el Titulo pantalla ACUERDO DE DEVOLUCIÓN en la parte de inicio", null);
    	}    	
    	if (au.driver.findElements(CEDIS_Fecha).isEmpty()!= false) {    		
    		baseExecution.addStep("Se falla el caso");        
    		Assert.fail("No se ha encontrado el campo de Fecha de la pantalla de ACUERDO DE DEVOLUCIÓN en la parte de inicio", null);
    	}    	
    	if(au.driver.findElements(CEDIS_ValorFecha).isEmpty()!= false) {
    		baseExecution.addStep("Se falla el caso");        
    		Assert.fail("No se ha encontrado el campo de Valor Fecha de la pantalla de ACUERDO DE DEVOLUCIÓN en la parte de inicio", null);
    	}
    	
    	if(au.driver.findElements(CEDIS_Ruta).isEmpty()!= false) {
    		baseExecution.addStep("Se falla el caso");        
    		Assert.fail("No se ha encontrado el campo de Ruta de la pantalla de ACUERDO DE DEVOLUCIÓN en la parte de inicio", null);
    	}    	
    	if(au.driver.findElements(CEDIS_RutaDinamica).isEmpty()!= false) {
    		baseExecution.addStep("Se falla el caso");        
    		Assert.fail("No se ha encontrado el campo de Ruta Dinamica de la pantalla de ACUERDO DE DEVOLUCIÓN en la parte de inicio", null);
    	}
    	if(au.driver.findElements(CEDIS_CRTienda).isEmpty()!= false) {
    		baseExecution.addStep("Se falla el caso");        
    		Assert.fail("No se ha encontrado el campo de CR Tienda de la pantalla de ACUERDO DE DEVOLUCIÓN en la parte de inicio", null);
    	}
    	if(au.driver.findElements(CEDIS_NombreTienda).isEmpty()!= false) {
    		baseExecution.addStep("Se falla el caso");        
    		Assert.fail("No se ha encontrado el campo de Nombre Tienda de la pantalla de ACUERDO DE DEVOLUCIÓN en la parte de inicio", null);
    	}
    	if(au.driver.findElements(CEDIS_Lider).isEmpty()!= false) {
    		baseExecution.addStep("Se falla el caso");        
    		Assert.fail("No se ha encontrado el campo de Lider de la pantalla de ACUERDO DE DEVOLUCIÓN en la parte de inicio", null);
    	}
    	if(au.driver.findElements(CEDIS_Transferencia).isEmpty()!= false) {
    		baseExecution.addStep("Se falla el caso");        
    		Assert.fail("No se ha encontrado el campo de Transferencia de la pantalla de ACUERDO DE DEVOLUCIÓN en la parte de inicio", null);
    	}    	
    }
    
    public void FirmaAreaDistribucion(String tomaEvidencia, String descPaso) throws InterruptedException {
    	//Funcion para realizar la firma del área de Distribución Creado: Juan Ibarra 
    	au.scrollToElement(CEDIS_AreaFirmaDistribucion);
    	au.click(CEDIS_AreaFirmaDistribucion);
    	if (tomaEvidencia.equals("tomaEvidencia")) {
    		au.click(CEDIS_PadFirmaDistribucion);
    		baseExecution.addStep(descPaso);
    		Thread.sleep(2000);
    		testCase.addScreenShotCurrentStep(au, "testImage");			
	    	au.click(CEDIS_AceptarFirma);        
    		}else {
    			au.click(CEDIS_PadFirmaDistribucion);
    	    	au.click(CEDIS_AceptarFirma);        
    		}    	
    }
    
    public void FirmaPersonalTienda(String tomaEvidencia, String descPaso) throws InterruptedException {
    	//Funcion para realizar la firma del área de Distribución Creado: Juan Ibarra 
    	au.scrollToElement(CEDIS_AreaFirmaPersonalTienda);
    	au.click(CEDIS_AreaFirmaPersonalTienda);
    	if (tomaEvidencia.equals("tomaEvidencia")) {
    		au.click(CEDIS_PadFirmaDistribucionPersonal);
    		baseExecution.addStep(descPaso);
    		Thread.sleep(2000);
    		testCase.addScreenShotCurrentStep(au, "testImage");
        	au.click(CEDIS_AceptarFirmaPersonal);                
    		}else {
    			au.click(CEDIS_PadFirmaDistribucionPersonal);
    	    	au.click(CEDIS_AceptarFirmaPersonal);        
    		}    	
    }
    
    public void TerminarRecepcion(String tomaEvidencia, String descPaso) throws InterruptedException {
    	//Funcion para realizar la firma del área de Distribución Creado: Juan Ibarra
    	au.scrollToElement(CEDIS_TerminarDevolucion);
    	au.click(CEDIS_TerminarDevolucion);//REVISAR FLUJOS ALTERNOS PARA LOS MENSAJES 
    	if (tomaEvidencia.equals("tomaEvidencia")) {
    		baseExecution.addStep(descPaso);
    		Thread.sleep(2000);
    		testCase.addScreenShotCurrentStep(au, "testImage");
    		au.click(CEDIS_ConfirmarSeguroTerminarAcuerdo);
    		}else {
    			au.click(CEDIS_ConfirmarSeguroTerminarAcuerdo);
    		}
    	}
    
    public void OpcionAreaDistribucion(String tomaEvidencia, String descPaso, String opcionSeleccionada) throws InterruptedException {
    	//Funcion para realizar la firma del área de Distribución Creado: Juan Ibarra
    	au.scrollToElement(CEDIS_DesplegableAreaDistribucion);
    	au.click(CEDIS_DesplegableAreaDistribucion);
    	if (tomaEvidencia.equals("tomaEvidencia")) {
    		baseExecution.addStep(descPaso);
    		Thread.sleep(2000);
    		testCase.addScreenShotCurrentStep(au, "testImage");
    		switch (opcionSeleccionada) {        	
        	case "Devolucion correcta":
        		au.click(CEDIS_DesplegableDevolucionCorrecta);
        		testCase.addScreenShotCurrentStep(au, "testImage");
                break;
        	case "Sin comentario":    		
        		au.click(CEDIS_DesplegableSinComentario);
        		testCase.addScreenShotCurrentStep(au, "testImage");
                break;
        	case "Otros":
        		au.click(CEDIS_DesplegableOtros);
        		testCase.addScreenShotCurrentStep(au, "testImage");
                break;                
        	default:
                baseExecution.addStep("Se falla el caso");
                //Se agrega el paso a mostrar en el PDF
        		Assert.fail("No se ha encontrado el objeto correspondiente a la opción del combo de Observaciones área distribución "+ opcionSeleccionada, null);
        		//Se agrega un mensaje a mostrar del error, en caso de no ponerlo regresaria leyenda de null
        		}
    		}else {
    			switch (opcionSeleccionada) {        	
            	case "Devolucion correcta":    		
            		au.click(CEDIS_DesplegableDevolucionCorrecta);
                    break;
            	case "Sin comentario":    		
            		au.click(CEDIS_DesplegableSinComentario);
                    break;
            	case "Otros":
            		au.click(CEDIS_DesplegableOtros);
                    break;                
            	default:
                    baseExecution.addStep("Se falla el caso");
                    //Se agrega el paso a mostrar en el PDF
            		Assert.fail("No se ha encontrado el objeto correspondiente a la opción del combo de Observaciones área distribución "+ opcionSeleccionada, null);
            		//Se agrega un mensaje a mostrar del error, en caso de no ponerlo regresaria leyenda de null
            		}
    			}
    	}
    
    public void OpcionPersonalTienda(String tomaEvidencia, String descPaso, String opcionSeleccionada) throws InterruptedException {
    	//Funcion para realizar la firma del área de Distribución Creado: Juan Ibarra
    	au.scrollToElement(CEDIS_DesplegablePersonalTienda);
    	au.click(CEDIS_DesplegablePersonalTienda);
    	if (tomaEvidencia.equals("tomaEvidencia")) {
    		baseExecution.addStep(descPaso);
    		Thread.sleep(2000);
    		testCase.addScreenShotCurrentStep(au, "testImage");
    		switch (opcionSeleccionada) {        	
        	case "Devolucion correcta":    		
        		au.click(CEDIS_DesplegableDevolucionCorrectaPersonal);
        		testCase.addScreenShotCurrentStep(au, "testImage");
                break;
        	case "Sin comentario":    		
        		au.click(CEDIS_DesplegableSinComentarioPersonal);
        		testCase.addScreenShotCurrentStep(au, "testImage");
                break;
        	case "Otros":
        		au.click(CEDIS_DesplegableOtrosPersonal);
        		testCase.addScreenShotCurrentStep(au, "testImage");
                break;                
        	default:
                baseExecution.addStep("Se falla el caso");
                //Se agrega el paso a mostrar en el PDF
        		Assert.fail("No se ha encontrado el objeto correspondiente a la opción del combo de Observaciones Personal de Tienda "+ opcionSeleccionada, null);
        		//Se agrega un mensaje a mostrar del error, en caso de no ponerlo regresaria leyenda de null
        		}
    		}else {
    			switch (opcionSeleccionada) {        	
            	case "Devolucion correcta":    		
            		au.click(CEDIS_DesplegableDevolucionCorrectaPersonal);
                    break;
            	case "Sin comentario":    		
            		au.click(CEDIS_DesplegableSinComentarioPersonal);
                    break;
            	case "Otros":
            		au.click(CEDIS_DesplegableOtrosPersonal);
                    break;                
            	default:
                    baseExecution.addStep("Se falla el caso");
                    //Se agrega el paso a mostrar en el PDF
            		Assert.fail("No se ha encontrado el objeto correspondiente a la opción del combo de Observaciones Personal de Tienda "+ opcionSeleccionada, null);
            		//Se agrega un mensaje a mostrar del error, en caso de no ponerlo regresaria leyenda de null
            		}
    			}
    	}
    
    public void SeleccionarObjeto(String ObjetoPantalla)
    {  	
    	//Funcion para buscar objeto y dar click especifico en la pantalla Creado: Juan Ibarra    	
    	switch (ObjetoPantalla) {
    	case "Desplegable Area Distribucion":    		
    		au.scrollToElement(CEDIS_DesplegableAreaDistribucion);
    		au.click(CEDIS_DesplegableAreaDistribucion);
            break;
    	case "Area Firma Distribucion":    		
    		au.scrollToElement(CEDIS_AreaFirmaDistribucion);
    		au.click(CEDIS_AreaFirmaDistribucion);
            break;
    	case "Desplegable Personal Tienda":    		
    		au.scrollToElement(CEDIS_DesplegablePersonalTienda);
    		au.click(CEDIS_DesplegablePersonalTienda);
            break;
    	case "Area Firma Personal":
    		au.scrollToElement(CEDIS_AreaFirmaPersonalTienda);
    		au.click(CEDIS_AreaFirmaPersonalTienda);
            break;
    	case "Terminar Recepcion":    		
    		au.scrollToElement(CEDIS_TerminarRecepcion);
    		au.click(CEDIS_TerminarRecepcion);
            break;
    	case "Terminar Devolución":    		
    		au.scrollToElement(CEDIS_TerminarDevolucion);
    		au.click(CEDIS_TerminarDevolucion);
            break;
    	case "Aceptar":    		
    		au.click(CEDIS_AceptarMensajes);
            break;
    	case "Confirmar":    		
    		au.click(CEDIS_ConfirmarSeguroTerminarAcuerdo);
            break;
    	case "Cancelar":    		
    		au.click(CEDIS_CancelarSeguroTerminarAcuerdo);
            break;
    	case "Cumplio Proceso":
    		au.scrollToElement(CEDIS_CumplioProcesoEntrega);
    		au.click(CEDIS_CumplioProcesoEntrega);
            break;
    	case "Confirmar Terminar Registro"://Agregado 17-Febrero -2025 -Juan Ibarra   		
    		au.scrollToElement(CEDIS_ConfirmarTerminarRegistro);
    		au.click(CEDIS_ConfirmarTerminarRegistro);
            break;
    	case "Cancelar Terminar Registro"://Agregado 17-Febrero -2025 -Juan Ibarra   		
    		au.scrollToElement(CEDIS_CancelarTerminarRegistro);
    		au.click(CEDIS_CancelarTerminarRegistro);
            break;            
        default:
            baseExecution.addStep("Se falla el caso");
            //Se agrega el paso a mostrar en el PDF
    		Assert.fail("No se ha encontrado el objeto "+ ObjetoPantalla, null);
    		//Se agrega un mensaje a mostrar del error, en caso de no ponerlo regresaria leyenda de null
    	}    	
    }
    
	public void ValidacionBotonTerminarDevolucion() throws InterruptedException {

		au.scrollToElement(CEDIS_TerminarDevolucion);
		if (au.driver.findElement(CEDIS_TerminarDevolucion).isEnabled()) {
			System.out.println("Se muestra el botón Terminar Devolucion");
		} else {
			System.err.println("No se muestra el boton Terminar Devolucion");
		} 
	}
	
	public void moverhacia(String descripcion) {

		switch (descripcion) {
		case "Firma Acuerdo":
			System.out.println("Moviendo hacía Leyenda Acuerdo Devolución");
			au.scrollToElement(CEDIS_TituloAcuerdo);
			break;

		}

	}

	public void sendKeysUsingKeyEvents(AppiumUtil au, String text) {
	    Map<Character, AndroidKey> keyMap = new HashMap<>();
	    keyMap.put('0', AndroidKey.DIGIT_0);
	    keyMap.put('1', AndroidKey.DIGIT_1);
	    keyMap.put('2', AndroidKey.DIGIT_2);
	    keyMap.put('3', AndroidKey.DIGIT_3);
	    keyMap.put('4', AndroidKey.DIGIT_4);
	    keyMap.put('5', AndroidKey.DIGIT_5);
	    keyMap.put('6', AndroidKey.DIGIT_6);
	    keyMap.put('7', AndroidKey.DIGIT_7);
	    keyMap.put('8', AndroidKey.DIGIT_8);
	    keyMap.put('9', AndroidKey.DIGIT_9);
	    keyMap.put('B', AndroidKey.BACK);
	    keyMap.put('E', AndroidKey.ENTER);
	 
	    for (char c : text.toCharArray()) {
	        AndroidKey key = keyMap.get(c);
	        if (key != null) {
	            au.driver.pressKey(new KeyEvent(key));
	        }
	    }
	}
	
	public void subir () throws IOException, InterruptedException {
		
		Runtime.getRuntime().exec("adb shell input swipe 100 180 100 950 200");
		Thread.sleep(2000);

	}
	
    public void ValidarMensajeTerminadoRegistro() throws InterruptedException{  	
    	/*Funcion que permite validar la  descripción del mensaje a mostrar  al en la pantalla al terminar la firma de devolución de un caso iniciado por Devolución
    	 * Creado: Juan Ibarra
    	 * Fecha Creación: 17-Febrero-2025
    	*/
    	Thread.sleep(500);
        WebElement elemento = au.driver.findElement(CEDIS_LeyendaTerminarRegistro);
        String mensajeMostrado = elemento.getText().trim(); // Eliminar espacios al inicio y al final
        // Texto esperado
        String txtWarning = "¿Está seguro que ha terminado con el registro de las devoluciones de mercancía y canastillas?";

        // Normalizar los textos para comparar sin espacios adicionales
        String mensajeMostradoNormalizado = mensajeMostrado.replaceAll("\\s+", " ").trim();
        String txtWarningNormalizado = txtWarning.replaceAll("\\s+", " ").trim();

        // Imprimir mensajes para depuración
        System.out.println("Mensaje mostrado (normalizado): " + mensajeMostradoNormalizado);
        System.out.println("Mensaje esperado (normalizado): " + txtWarningNormalizado);

        // Validar si el mensaje mostrado no coincide con el mensaje esperado
        if (!mensajeMostradoNormalizado.equals(txtWarningNormalizado)) {
        	baseExecution.addStep("El mensaje es incorrecto");        
    		Assert.fail("El mensaje mostrado no coincide con el mensaje esperado", null);        	
        } else {
            System.out.println("El mensaje es correcto.");
        }    
 
      }
	
}    