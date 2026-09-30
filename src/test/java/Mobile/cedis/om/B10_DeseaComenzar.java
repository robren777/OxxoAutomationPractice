package Mobile.cedis.om;

import java.io.IOException;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import modelo.BaseExecution;
import modelo.TestCase;
import utils.AppiumUtil;

public class B10_DeseaComenzar {
    private TestCase testCase;
	private AppiumUtil au;
	private BaseExecution baseExecution;
	
	//Sección Encabezado
	private By CEDIS_TituloPantalla = By.xpath("//android.widget.TextView[@text=\"RECEPCIÓN DE PEDIDO CEDIS\"]");
	private By CEDIS_Fecha = By.xpath("//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/tvDateText\" and @text=\"Fecha: \"]");
	private By CEDIS_ValorFecha = By.xpath("//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/tvTitleDate\"]");
	private By CEDIS_Ruta = By.xpath("//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/tvTitleRute\"]");   
	private By CEDIS_RutaDinamica = By.xpath("//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/tvTitleRuteD\"]");
	private By CEDIS_CRTienda = By.xpath("//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/tvTitleStore\"]");
	private By CEDIS_NombreTienda = By.xpath("//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/tvTitleNameStore\"]");	
	private By CEDIS_Lider = By.xpath("//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/tvTitleLeaderStore\"]");
	private By CEDIS_Transferencia = By.xpath("//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/tvTransferNumber\"]");
	
	private By CEDIS_Cargando = By.xpath("//android.widget.TextView[@resource-id=\"android:id/message\" and @text=\"Cargando\"]");
	
	//Sección Instrucciones 
	private By CEDIS_LeyendaDeseaComenzar = By.xpath("//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/text_view_title_instructions\" and @text=\"¿CÓMO DESEAS COMENZAR?\"]");
	//private By CEDIS_Instrucciones = By.xpath("//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/text_view_body_instruccions\" and @text=\"Puedes comenzar escaneando las cajas y canastillas del pedido, o bien, registrando las devoluciones del pedido. Selecciona la opción deseada.\"]");
	//private By CEDIS_Instrucciones = By.xpath("//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/text_view_body_instruccions\" and @text=\"Puedes comenzar escaneando las cajas y canastillas del\r\n"
		//	+ "pedido, o bien, registrando las devoluciones del pedido.\r\n"
			//+ "Selecciona la opción deseada.\"]");
	
	private By CEDIS_Instrucciones = By.xpath("//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/text_view_body_instruccions\"]");
	
	//Sección opciones
    private By CEDIS_RecepcionPedido = By.xpath("//android.widget.TextView[@text=\"RECEPCIÓN DE PEDIDO\"]");
    private By CEDIS_DevolucionCEDIS = By.xpath("//android.widget.TextView[@text=\"DEVOLUCIONES A CEDIS\"]");
    
    

    /**
 * Constructor
 ******************************************************************************************/
	/**
	 * Constructor de la clase de la pantalla de Como Desea Comenzar
	 * @author Juan Ibarra
	 * @param au (AppiumUtil): objeto de la clase 
	 * @param testCase (TestCase): objeto de la clase
	 * @param baseExecution (BaseExecution): objeto de la clase
	 */
    public B10_DeseaComenzar(TestCase testCase, AppiumUtil au, BaseExecution baseExecution) {
        this.testCase = testCase;
        this.au = au;
        this.baseExecution = baseExecution;
    }
    
        
    public void SeleccionarObjeto(String ObjetoPantalla) throws InterruptedException
    {  	
    	//Funcion para buscar objeto y dar click especifico en la pantalla Creado: Juan Ibarra    	
    	Thread.sleep(1000);
    	switch (ObjetoPantalla) {
    	case "Recepcion Pedido":
    		au.scrollToElement(CEDIS_RecepcionPedido);
    		au.click(CEDIS_RecepcionPedido);
            break;
    	case "Devolucion CEDIS":
    		au.scrollToElement(CEDIS_DevolucionCEDIS);
    		au.click(CEDIS_DevolucionCEDIS);
            break;
    	case "Titulo Pantalla":
    		au.scrollToElement(CEDIS_LeyendaDeseaComenzar);
    		au.click(CEDIS_LeyendaDeseaComenzar);
            break;             
    	    	            
        default:
            baseExecution.addStep("Se falla el caso");
            //Se agrega el paso a mostrar en el PDF
    		Assert.fail("No se ha encontrado el objeto "+ ObjetoPantalla, null);
    		//Se agrega un mensaje a mostrar del error, en caso de no ponerlo regresaria leyenda de null
    	}    	
    }
        
    public void ValidacionEncabezado() throws IOException, InterruptedException {
    	//Funcion que permite validar que se muestran los campo correspondientes al encabezado de la pantalla  Creado: Ricas 
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
    	
    	if(au.driver.findElements(CEDIS_Ruta).isEmpty()!= false) {
    		baseExecution.addStep("Se falla el caso");        
    		Assert.fail("No se ha encontrado el campo de Ruta de la pantalla de FORMATO ÚNICO DE ENTREGA en la parte de inicio", null);
    	}    	
    	if(au.driver.findElements(CEDIS_RutaDinamica).isEmpty()!= false) {
    		baseExecution.addStep("Se falla el caso");        
    		Assert.fail("No se ha encontrado el campo de Ruta Dinamica de la pantalla de FORMATO ÚNICO DE ENTREGA en la parte de inicio", null);
    	}
    	    	
    	if(au.driver.findElements(CEDIS_CRTienda).isEmpty()!= false) {
    		baseExecution.addStep("Se falla el caso");        
    		Assert.fail("No se ha encontrado el campo de CR Tienda de la pantalla de FORMATO ÚNICO DE ENTREGA en la parte de inicio", null);
    	}
    	if(au.driver.findElements(CEDIS_NombreTienda).isEmpty()!= false) {
    		baseExecution.addStep("Se falla el caso");        
    		Assert.fail("No se ha encontrado el campo de Nombre Tienda de la pantalla de FORMATO ÚNICO DE ENTREGA en la parte de inicio", null);
    	}
    	if(au.driver.findElements(CEDIS_Lider).isEmpty()!= false) {
    		baseExecution.addStep("Se falla el caso");        
    		Assert.fail("No se ha encontrado el campo de Lider de la pantalla de FORMATO ÚNICO DE ENTREGA en la parte de inicio", null);
    	}
    	if(au.driver.findElements(CEDIS_Transferencia).isEmpty()!= false) {
    		baseExecution.addStep("Se falla el caso");        
    		Assert.fail("No se ha encontrado el campo de Transferencia de la pantalla de FORMATO ÚNICO DE ENTREGA en la parte de inicio", null);
    	}
    	
    }    
    
    public void Cargando() throws InterruptedException{  	
    	//Funcion que permite dar un tiempo de espera en la carga de la pantalla	Creado: Juan Ibarra		Fecha Creación: 31-Octubre-2024
    	System.out.println("\nIniciando validacion de espera de carga de las Rutas...");
    	Thread.sleep(4000);
    	int intentos = 0;
    	boolean barra = true;  
    	while (barra == true ) {
    		try {
    			WebElement element = au.driver.findElement(CEDIS_Cargando);
    			if (element.isDisplayed()) {
    				Thread.sleep(3000);
    				intentos++;}
    		}catch (NoSuchElementException  e){
    			System.out.println("\nValidado, ya no se muestra la carga de las Rutas.");
    			break;
         	}
    		if (intentos>10) {
    			baseExecution.addStep("Se falla el caso");
    			Assert.fail("\nSe falla el caso, se excedio el tiempo de timeout establecido para la carga de las Rutas.", null);    		  		
    		}
    	}    	
    }
    
    public void ValidarInstrucciones() throws InterruptedException{  	
    	//Funcion que permite validar la  descripción de Instrucciones mostradas en la pantalla		Creado: Juan Ibarra		Fecha Creación: 31-Octubre-2024
    	 // Obtener el texto mostrado en la aplicación
    	Thread.sleep(1000);
        WebElement elemento = au.driver.findElement(CEDIS_Instrucciones);
        String mensajeMostrado = elemento.getText().trim(); // Eliminar espacios al inicio y al final
        // Texto esperado
        String txtWarning = "Puedes comenzar escaneando las cajas y canastillas del pedido, o bien, registrando las devoluciones del pedido. Selecciona la opción deseada.";

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
    
    public void validarBotones() throws InterruptedException {
    	
    	Thread.sleep(1000);
    	au.scrollToElement(CEDIS_DevolucionCEDIS);
    	if(au.driver.findElement(CEDIS_RecepcionPedido).isEnabled()==false) {
    		baseExecution.addStep("Se falla el caso");        
    		Assert.fail("No se ha encontrado el botón RECEPCIÓN DE PEDIDO", null);
    	}  else {
    		System.out.println("Se encontró el botón RECEPCIÓN DE PEDIDO");
    	}  	
    	if (au.driver.findElements(CEDIS_DevolucionCEDIS).isEmpty()!= false) {    		
    		baseExecution.addStep("Se falla el caso");        
    		Assert.fail("No se ha encontrado el botón DEVOLUCIONES A CEDIS", null);
    	} else {
    		System.out.println("Se encontró el botón DEVOLUCIONES A CEDIS");
    	}
  	  
    }
    
}
    
    