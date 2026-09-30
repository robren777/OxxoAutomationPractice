package Mobile.cedis.om;

import java.io.IOException;

import org.openqa.selenium.By;
import org.testng.Assert;

import modelo.BaseExecution;
import modelo.TestCase;
import utils.AppiumUtil;

public class B04_AcuerdoEntrega {
    private TestCase testCase;
	private AppiumUtil au;
	private BaseExecution baseExecution;
	

	//Sección Encabezado 
	private By CEDIS_TituloPantalla = By.xpath("(//android.widget.TextView[@text=\"ACUERDO DE ENTREGA\"])[1]");
	private By CEDIS_Fecha = By.xpath("//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/tvDateText\" and @text=\"Fecha: \"]");
	private By CEDIS_ValorFecha = By.xpath("//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/tvTitleDate\"]");
	private By CEDIS_Ruta = By.xpath("//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/tvTitleRute\"]");
	private By CEDIS_RutaDinamica = By.xpath("//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/tvTitleRuteD\"]");
	private By CEDIS_CRTienda = By.xpath("//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/tvTitleStore\"]");
	private By CEDIS_NombreTienda = By.xpath("//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/tvTitleNameStore\"]");	
	private By CEDIS_Lider = By.xpath("//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/tvTitleLeaderStore\"]");
	private By CEDIS_Transferencia = By.xpath("//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/tvTransferNumber\"]");
	
	//Sección Observaciones Area
	private By CEDIS_LeyendaAcuerdoEntrega = By.xpath("//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/text_view_title_acuerdo\" and @text=\"ACUERDO DE ENTREGA\"]");
	private By CEDIS_LeyendaObservacionesAreaDist = By.xpath("//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/textView40\" and @text=\"Observaciones área distribución\"]");
	private By CEDIS_DesplegableAreaDistribucionEntrega = By.xpath("//android.widget.Spinner[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/spinnerObs\"]");
	private By CEDIS_DesplegableEntregable = By.xpath("//android.widget.TextView[@resource-id=\"android:id/text1\" and @text=\"Entrega correcta\"]");
    private By CEDIS_DesplegableSinComentarioEntrega = By.xpath("//android.widget.TextView[@resource-id=\"android:id/text1\" and @text=\"Sin comentario\"]");
    private By CEDIS_DesplegableOtrosEntrega = By.xpath("//android.widget.TextView[@resource-id=\"android:id/text1\" and @text=\"Otros\"]");
    private By CEDIS_DetalleOtrosAreaEntrega = By.xpath("//android.widget.EditText[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/editTxtComDist\"]");
    private By CEDIS_LeyendaFirmaAreaDistribucionEntrega = By.xpath("//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/textView41\" and @text=\"Firma Área Distribución\"]");
    private By CEDIS_AreaFirmaDistribucionEntrega = By.xpath("//android.widget.ImageView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/ivSignature\"]");
    private By CEDIS_AcuerdoFirmaDistribucionEntrega = By.xpath("//android.widget.TextView[@text=\"ACUERDO DE ENTREGA\"]");
      
    //Sección Firma Acuerdo Entrega
    private By CEDIS_PadFirmaDistribucionEntrega = By.xpath("//android.view.View[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/signature_pad\"]");
    private By CEDIS_LimpiarFirmaEntrega = By.xpath("//android.widget.Button[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/clear_button\" and @text=\"LIMPIAR\"]");
    private By CEDIS_AceptarFirmaEntrega = By.xpath("//android.widget.Button[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/save_button\" and @text=\"ACEPTAR\"]");
    
    //Sección Observaciones Tienda
    private By CEDIS_LeyendaObsPersonalTiendaEntrega = By.xpath("//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/textView44\" and @text=\"Observaciones Personal de Tienda\"]");
    private By CEDIS_DesplegablePersonalTiendaEntrega = By.xpath("//android.widget.Spinner[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/spinnerObsTienda\"]");
    private By CEDIS_DesplegableEntregablePersonal = By.xpath("//android.widget.TextView[@resource-id=\"android:id/text1\" and @text=\"Entrega correcta\"]");
    private By CEDIS_DesplegableSinComentarioEntregaPersonal = By.xpath("//android.widget.TextView[@resource-id=\"android:id/text1\" and @text=\"Sin comentario\"]");
    private By CEDIS_DesplegableOtrosEntregaPersonal = By.xpath("//android.widget.TextView[@resource-id=\"android:id/text1\" and @text=\"Otros\"]");        
    private By CEDIS_DetalleOtrosPersonalTiendaEntrega = By.xpath("//android.widget.EditText[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/editTxtComTienda\"]");
    private By CEDIS_LeyendaFirmaPersonalTiendaEntrega = By.xpath("//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/textViewtienda\" and @text=\"Firma Personal Tienda\"]");
    private By CEDIS_AreaFirmaPersonalTiendaEntrega = By.xpath("//android.widget.ImageView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/ivSignature2\"]");

    //Sección Firma DistribucionEntrega
    private By CEDIS_AcuerdoFirmaDistribucionEntregaPersonal = By.xpath("//android.widget.TextView[@text=\"ACUERDO DE ENTREGA\"]");
    private By CEDIS_PadFirmaDistribucionEntregaPersonal = By.xpath("//android.view.View[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/signature_pad\"]");
    private By CEDIS_LimpiarFirmaEntregaPersonal = By.xpath("//android.widget.Button[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/clear_button\" and @text=\"LIMPIAR\"]");
    private By CEDIS_AceptarFirmaEntregaPersonal = By.xpath("//android.widget.Button[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/save_button\" and @text=\"ACEPTAR\"]");
    
    
    private By CEDIS_TerminarRecepcionEntrega = By.xpath("//android.widget.Button[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/btnTerminarRecepcion\" and @text=\"TERMINAR RECEPCIÓN\"]");
    
    //Mensajes
    private By CEDIS_LeyendaFirmasObligatorias = By.xpath("//android.widget.TextView[@resource-id=\"android:id/message\" and @text=\"Las Firmas son campos obligatorios\"]");
    private By CEDIS_LeyendaComentarioPendiente = By.xpath("//android.widget.TextView[@resource-id=\"android:id/message\" and @text=\"El comentario debe ser agregado para continuar\"]");
    private By CEDIS_BotonAceptar = By.xpath("//android.widget.Button[@resource-id=\"android:id/button1\" and @text=\"ACEPTAR\"]");
    
    private By CEDIS_LeyendaSeguroTerminarAcuerdoEntrega = By.xpath("//android.widget.TextView[@resource-id=\"android:id/message\" and @text=\"¿Está seguro que ha terminado de realizar la recepción de los bultos del pedido?\"]");
    private By CEDIS_ConfirmarSeguroTerminarAcuerdoEntrega = By.xpath("//android.widget.Button[@resource-id=\"android:id/button1\" and @text=\"CONFIRMAR\"]");
    private By CEDIS_CancelarSeguroTerminarAcuerdoEntrega = By.xpath("//android.widget.Button[@resource-id=\"android:id/button2\" and @text=\"CANCELAR\"]");
    
    private By CEDIS_LeyendaFaltantesEntrega = By.xpath("//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/textViewCenter\"]");
    private By CEDIS_RegresarEscaneoEntrega = By.xpath("//android.widget.Button[@resource-id=\"android:id/button2\" and @text=\"REGRESAR A ESCANEO\"]");
    private By CEDIS_ContinuarEntrega = By.xpath("//android.widget.Button[@resource-id=\"android:id/button1\" and @text=\"CONTINUAR\"]");

    
    /**
	 * Constructor
	 *****************************************************************************************
	 * Constructor de la clase B03_ResumenPedidosCEDIS
	 *
	 * @param au            (AppiumUtil): objeto de la clase
	 * @param testCase      (TestCase): objeto de la clase
	 * @param baseExecution (BaseExecution): objeto de la clase
	 * @author Juan Ibarra
	 */
	
    public B04_AcuerdoEntrega(TestCase testCase, AppiumUtil au, BaseExecution baseExecution) {
        this.testCase = testCase;
        this.au = au;
        this.baseExecution = baseExecution;
    }
    
    public void ValidacionEncabezado() throws IOException {
    	//Funcion que permite validar que se muestran los campo correspondientes al encabezado de la pantalla  Creado: Juan Ibarra
    	if(au.driver.findElement(CEDIS_TituloPantalla).isEnabled()==false) {
    		baseExecution.addStep("Se falla el caso");        
    		Assert.fail("No se ha encontrado el Titulo pantalla ACUERDO DE ENTREGA en la parte de inicio", null);
    	}    	
    	if (au.driver.findElements(CEDIS_Fecha).isEmpty()!= false) {    		
    		baseExecution.addStep("Se falla el caso");        
    		Assert.fail("No se ha encontrado el campo de Fecha de la pantalla de ACUERDO DE ENTREGA en la parte de inicio", null);
    	}    	
    	if(au.driver.findElements(CEDIS_ValorFecha).isEmpty()!= false) {
    		baseExecution.addStep("Se falla el caso");        
    		Assert.fail("No se ha encontrado el campo de Valor Fecha de la pantalla de ACUERDO DE ENTREGA en la parte de inicio", null);
    	}    	
    	if(au.driver.findElements(CEDIS_Ruta).isEmpty()!= false) {
    		baseExecution.addStep("Se falla el caso");        
    		Assert.fail("No se ha encontrado el campo de Ruta de la pantalla de ACUERDO DE ENTREGA en la parte de inicio", null);
    	}    	
    	if(au.driver.findElements(CEDIS_RutaDinamica).isEmpty()!= false) {
    		baseExecution.addStep("Se falla el caso");        
    		Assert.fail("No se ha encontrado el campo de Ruta Dinamica de la pantalla de ACUERDO DE ENTREGA en la parte de inicio", null);
    	}
    	if(au.driver.findElements(CEDIS_CRTienda).isEmpty()!= false) {
    		baseExecution.addStep("Se falla el caso");        
    		Assert.fail("No se ha encontrado el campo de CR Tienda de la pantalla de ACUERDO DE ENTREGA en la parte de inicio", null);
    	}
    	if(au.driver.findElements(CEDIS_NombreTienda).isEmpty()!= false) {
    		baseExecution.addStep("Se falla el caso");        
    		Assert.fail("No se ha encontrado el campo de Nombre Tienda de la pantalla de ACUERDO DE ENTREGA en la parte de inicio", null);
    	}
    	if(au.driver.findElements(CEDIS_Lider).isEmpty()!= false) {
    		baseExecution.addStep("Se falla el caso");        
    		Assert.fail("No se ha encontrado el campo de Lider de la pantalla de ACUERDO DE ENTREGA en la parte de inicio", null);
    	}
    	if(au.driver.findElements(CEDIS_Transferencia).isEmpty()!= false) {
    		baseExecution.addStep("Se falla el caso");        
    		Assert.fail("No se ha encontrado el campo de Transferencia de la pantalla de ACUERDO DE ENTREGA en la parte de inicio", null);
    	}    	
    }
    
    public void FirmaAreaDistribucion(String tomaEvidencia, String descPaso) throws InterruptedException {
    	//Funcion para realizar la firma del área de Distribución Creado: Juan Ibarra 
    	au.scrollToElement(CEDIS_AreaFirmaDistribucionEntrega);
    	au.click(CEDIS_AreaFirmaDistribucionEntrega);
    	if (tomaEvidencia.equals("tomaEvidencia")) {
    		au.click(CEDIS_PadFirmaDistribucionEntrega);
    		baseExecution.addStep(descPaso);
    		Thread.sleep(2000);
    		testCase.addScreenShotCurrentStep(au, "testImage");			
	    	au.click(CEDIS_AceptarFirmaEntrega);        
    		}else {
    			au.click(CEDIS_PadFirmaDistribucionEntrega);
    	    	au.click(CEDIS_AceptarFirmaEntrega);        
    		}    	
    }
    
    public void FirmaPersonalTienda(String tomaEvidencia, String descPaso) throws InterruptedException {
    	//Funcion para realizar la firma del área de Distribución Creado: Juan Ibarra 
    	au.scrollToElement(CEDIS_AreaFirmaPersonalTiendaEntrega);
    	au.click(CEDIS_AreaFirmaPersonalTiendaEntrega);
    	Thread.sleep(300); 
    	//if(au.driver.findElement(CEDIS_ContinuarEntrega).isEnabled()) {
    	if (au.driver.findElements(CEDIS_ContinuarEntrega).isEmpty()!= true) {
    		au.click(CEDIS_ContinuarEntrega);
    	}
    	if (tomaEvidencia.equals("tomaEvidencia")) {
    		au.click(CEDIS_PadFirmaDistribucionEntregaPersonal);
    		baseExecution.addStep(descPaso);
    		Thread.sleep(2000);
    		testCase.addScreenShotCurrentStep(au, "testImage");
        	au.click(CEDIS_AceptarFirmaEntregaPersonal);                
    		}else {
    			au.click(CEDIS_PadFirmaDistribucionEntregaPersonal);
    	    	au.click(CEDIS_AceptarFirmaEntregaPersonal);        
    		}    	
    }
    
    public void TerminarRecepcion(String tomaEvidencia, String descPaso) throws InterruptedException {
    	//Funcion para realizar la firma del área de Distribución Creado: Juan Ibarra
    	au.scrollToElement(CEDIS_TerminarRecepcionEntrega);
    	au.click(CEDIS_TerminarRecepcionEntrega);
    	if (tomaEvidencia.equals("tomaEvidencia")) {
    		baseExecution.addStep(descPaso);
    		Thread.sleep(2000);
    		testCase.addScreenShotCurrentStep(au, "testImage");
    		au.click(CEDIS_ConfirmarSeguroTerminarAcuerdoEntrega);
    		}else {
    			au.click(CEDIS_ConfirmarSeguroTerminarAcuerdoEntrega);
    		}
    	}
    
    public void OpcionAreaDistribucion(String tomaEvidencia, String descPaso, String opcionSeleccionada) throws InterruptedException {
    	//Funcion para realizar la firma del área de Distribución Creado: Juan Ibarra
    	au.scrollToElement(CEDIS_DesplegableAreaDistribucionEntrega);
    	au.click(CEDIS_DesplegableAreaDistribucionEntrega);
    	if (tomaEvidencia.equals("tomaEvidencia")) {
    		baseExecution.addStep(descPaso);
    		Thread.sleep(2000);
    		testCase.addScreenShotCurrentStep(au, "testImage");
    		switch (opcionSeleccionada) {        	
        	case "Entrega correcta":    		
        		au.click(CEDIS_DesplegableEntregable);
        		testCase.addScreenShotCurrentStep(au, "testImage");
                break;
        	case "Sin comentario":    		
        		au.click(CEDIS_DesplegableSinComentarioEntrega);
        		testCase.addScreenShotCurrentStep(au, "testImage");
                break;
        	case "Otros":
        		au.click(CEDIS_DesplegableOtrosEntrega);
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
            	case "Entrega correcta":    		
            		au.click(CEDIS_DesplegableEntregable);
                    break;
            	case "Sin comentario":    		
            		au.click(CEDIS_DesplegableSinComentarioEntrega);
                    break;
            	case "Otros":
            		au.click(CEDIS_DesplegableOtrosEntrega);
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
    	au.scrollToElement(CEDIS_DesplegablePersonalTiendaEntrega);
    	au.click(CEDIS_DesplegablePersonalTiendaEntrega);
    	if (tomaEvidencia.equals("tomaEvidencia")) {
    		baseExecution.addStep(descPaso);
    		Thread.sleep(2000);
    		testCase.addScreenShotCurrentStep(au, "testImage");
    		switch (opcionSeleccionada) {        	
        	case "Entrega correcta":    		
        		au.click(CEDIS_DesplegableEntregablePersonal);
        		testCase.addScreenShotCurrentStep(au, "testImage");
                break;
        	case "Sin comentario":    		
        		au.click(CEDIS_DesplegableSinComentarioEntregaPersonal);
        		testCase.addScreenShotCurrentStep(au, "testImage");
                break;
        	case "Otros":
        		au.click(CEDIS_DesplegableOtrosEntregaPersonal);
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
            	case "Entrega correcta":    		
            		au.click(CEDIS_DesplegableEntregablePersonal);
                    break;
            	case "Sin comentario":    		
            		au.click(CEDIS_DesplegableSinComentarioEntregaPersonal);
                    break;
            	case "Otros":
            		au.click(CEDIS_DesplegableOtrosEntregaPersonal);
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
    	case "Desplegable Area Distribucion Entrega":    		
    		au.scrollToElement(CEDIS_DesplegableAreaDistribucionEntrega);
    		au.click(CEDIS_DesplegableAreaDistribucionEntrega);
            break;
    	case "Area Firma Distribucion":    		
    		au.scrollToElement(CEDIS_AreaFirmaDistribucionEntrega);
    		au.click(CEDIS_AreaFirmaDistribucionEntrega);
            break;
    	case "Desplegable Personal Tienda":    		
    		au.scrollToElement(CEDIS_DesplegablePersonalTiendaEntrega);
    		au.click(CEDIS_DesplegablePersonalTiendaEntrega);
            break;
    	case "Area Firma Personal":    		
    		au.scrollToElement(CEDIS_AreaFirmaPersonalTiendaEntrega);
    		au.click(CEDIS_AreaFirmaPersonalTiendaEntrega);
            break;
    	case "Terminar Recepcion":    		
    		au.scrollToElement(CEDIS_TerminarRecepcionEntrega);
    		au.click(CEDIS_TerminarRecepcionEntrega);
            break;
    	case "Aceptar":    		
    		au.scrollToElement(CEDIS_BotonAceptar);
    		au.click(CEDIS_BotonAceptar);
            break;
    	case "Confirmar":    		
    		au.scrollToElement(CEDIS_ConfirmarSeguroTerminarAcuerdoEntrega);
    		au.click(CEDIS_ConfirmarSeguroTerminarAcuerdoEntrega);
            break;
    	case "Cancelar":    		
    		au.scrollToElement(CEDIS_CancelarSeguroTerminarAcuerdoEntrega);
    		au.click(CEDIS_CancelarSeguroTerminarAcuerdoEntrega);
            break;
    	case "Continuar Entrega":    		
    		au.scrollToElement(CEDIS_ContinuarEntrega);
    		au.click(CEDIS_ContinuarEntrega);
            break;
    	case "Regresar Entrega":    		
    		au.scrollToElement(CEDIS_RegresarEscaneoEntrega);
    		au.click(CEDIS_RegresarEscaneoEntrega);
            break; 
        default:
            baseExecution.addStep("Se falla el caso");
            //Se agrega el paso a mostrar en el PDF
    		Assert.fail("No se ha encontrado el objeto "+ ObjetoPantalla, null);
    		//Se agrega un mensaje a mostrar del error, en caso de no ponerlo regresaria leyenda de null
    	}    	
    }

}    