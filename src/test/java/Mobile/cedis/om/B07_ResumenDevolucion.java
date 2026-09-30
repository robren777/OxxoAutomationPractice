package Mobile.cedis.om;

import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;
import modelo.BaseExecution;
import modelo.TestCase;
import utils.AppiumUtil;

public class B07_ResumenDevolucion {
    private TestCase testCase;
	private AppiumUtil au;
	private BaseExecution baseExecution;
	
	//Sección Encabezado
		private By CEDIS_TituloPantalla = By.xpath("//android.widget.TextView[@text=\"RESUMEN DEVOLUCIÓN\"]");
		private By CEDIS_Fecha = By.xpath("//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/tvDateText\" and @text=\"Fecha: \"]");
		private By CEDIS_ValorFecha = By.xpath("//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/tvTitleDate\"]");
		private By CEDIS_Ruta = By.xpath("//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/tvTitleRute\"]");
		private By CEDIS_RutaDinamica = By.xpath("//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/tvTitleRuteD\"]");
		private By CEDIS_CRTienda = By.xpath("//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/tvTitleStore\"]");
		private By CEDIS_NombreTienda = By.xpath("//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/tvTitleNameStore\"]");	
		private By CEDIS_Lider = By.xpath("//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/tvTitleLeaderStore\"]");
		private By CEDIS_Transferencia = By.xpath("//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/tvTransferNumber\"]");


		private By CEDIS_LeyendaResumenDevolucion = By.xpath("//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/tvTitleResumeOrder\" and @text=\"RESUMEN DE DEVOLUCIÓN\"]");
	    private By CEDIS_LeyendaDevolucionContenedores = By.xpath("//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/tvTitleResumeDevolucionMercancia\" and @text=\"DEVOLUCIÓN DE CONTENEDORES\"]");
	    
	    //Sección Canastillas
	    private By CEDIS_LeyendaCanastillas = By.xpath("//android.widget.TextView[@text=\" CANASTILLAS\"]");
	    private By CEDIS_LeyendaAzul = By.xpath("//android.widget.TextView[@text=\"Azul\"]");
	    private By CEDIS_CantidadAzul = By.xpath("//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/txtAzul\"]");   
	    private By CEDIS_LeyendaAmarilla= By.xpath("//android.widget.TextView[@text=\"Amarilla\"]");
	    private By CEDIS_CantidadAmarilla= By.xpath("//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/txtAmarilla\"]");	    
	    private By CEDIS_LeyendaVerde = By.xpath("//android.widget.TextView[@text=\"Verde\"]");
	    private By CEDIS_CantidadVerde = By.xpath("//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/txtVerde\"]");	    	    
	    private By CEDIS_LeyendaNaranja = By.xpath("//android.widget.TextView[@text=\"Naranja\"]");
	    private By CEDIS_CantidadNaranja = By.xpath("//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/txtNaranja\"]");	    	    
	    private By CEDIS_LeyendaOtro = By.xpath("//android.widget.TextView[@text=\"Otro\"]");
	    private By CEDIS_CantidadOtro = By.xpath("//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/txtOtro\"]");	    
	    private By CEDIS_LeyendaTotalCanastillas = By.xpath("(//android.widget.TextView[@text=\"Total\"])[1]");
	    private By CEDIS_CantidadTotalCanastillas = By.xpath("//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/txtTotalColor\"]");
	    
	    //Sección Contenedores 
	    private By CEDIS_LeyendaContenedores = By.xpath("//android.widget.TextView[@text=\" CONTENEDORES\"]");
	    private By CEDIS_LeyendaTapas = By.xpath("//android.widget.TextView[@text=\"Tapas de canastillas\"]");
	    private By CEDIS_CantidadTapas = By.xpath("//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/txtTapas\"]");   
	    private By CEDIS_LeyendaEnvase = By.xpath("//android.widget.TextView[@text=\"Envases vacios\"]");
	    private By CEDIS_CantidadEnvase = By.xpath("//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/txtEnvases\"]");	    
	    private By CEDIS_LeyendaTotalContenedores = By.xpath("(//android.widget.TextView[@text=\"Total\"])[2]");
	    private By CEDIS_CantidadTotalContenedores = By.xpath("//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/txtTotalTapas\"]");
	    	    
	    //Sección Bol´s 
	    private By CEDIS_LeyendaBOL = By.xpath("//android.widget.TextView[@text=\"BOL´S DE DEVOLUCIÓN\"]");
	    private By CEDIS_LeyendaBOLDevolver = By.xpath("//android.widget.TextView[@text=\" BOL´S A DEVOLVER\"]");
	    private By CEDIS_LeyendaBOLEspecial = By.xpath("//android.widget.TextView[@text=\"Especial\"]");
	    private By CEDIS_BOLEspecial = By.xpath("//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/txtBolEspecial\"]");
	    private By CEDIS_LeyendaBOLNormal = By.xpath("//android.widget.TextView[@text=\"Normal\"]");
	    private By CEDIS_BOLNormal = By.xpath("//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/txtBolNormal\"]");
	    private By CEDIS_LeyendaTotalBOL = By.xpath("(//android.widget.TextView[@text=\"Total\"])[2]");
	    private By CEDIS_CantidadTotalBOL = By.xpath("//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/txtTotalBols\"]");	        

	    //Sección Canastillas con Devolución 
	    private By CEDIS_LeyendaCanastillaDevolucion = By.xpath("//android.widget.TextView[@text=\"CANASTILLAS CON DEVOLUCIÓN\"]");	    
	    private By CEDIS_CodigoContenedor = By.xpath("//android.widget.TextView[@text=\" CÓDIGO CONTENEDOR\"]");
	    private By CEDIS_RegistroContenedor = By.xpath("//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/txtCodigos\"]");
	    private By CEDIS_LeyendaTotalContenedor = By.xpath("(//android.widget.TextView[@text=\"Total\"])[3]");
	    private By CEDIS_CantidadTotalContenedor = By.xpath("//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/txtTotalCodigos\"]");
	    
	    	    
	    private By CEDIS_FirmaAcuerdoDevolucion = By.xpath("//android.widget.Button[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/btnSignatureOrder\" and @text=\"FIRMA DE ACUERDO\"]");


    /**
     *  Constructor
     *   ******************************************************************************************/
	   /**
	 * Constructor de la clase Login
	 * @author Alejandro Valdez
	 * @param au (AppiumUtil): objeto de la clase 
	 * @param testCase (TestCase): objeto de la clase
	 * @param baseExecution (BaseExecution): objeto de la clase
	 */
	    
    public B07_ResumenDevolucion(TestCase testCase, AppiumUtil au, BaseExecution baseExecution) {
        this.testCase = testCase;
        this.au = au;
        this.baseExecution = baseExecution;
    }
    
   
    public void ValidacionEncabezado() throws IOException {
    	//Funcion que permite validar que se muestran los campo correspondientes al encabezado de la pantalla  Creado: Juan Ibarra
    	if(au.driver.findElement(CEDIS_TituloPantalla).isEnabled()==false) {
    		baseExecution.addStep("Se falla el caso");        
    		Assert.fail("No se ha encontrado el Titulo pantalla RESUMEN DEVOLUCIÓN en la parte de inicio", null);
    	}    	
    	if (au.driver.findElements(CEDIS_Fecha).isEmpty()!= false) {    		
    		baseExecution.addStep("Se falla el caso");        
    		Assert.fail("No se ha encontrado el campo de Fecha de la pantalla de RESUMEN DEVOLUCIÓN en la parte de inicio", null);
    	}    	
    	if(au.driver.findElements(CEDIS_ValorFecha).isEmpty()!= false) {
    		baseExecution.addStep("Se falla el caso");        
    		Assert.fail("No se ha encontrado el campo de Valor Fecha de la pantalla de RESUMEN DEVOLUCIÓN en la parte de inicio", null);
    	}    	
    	if(au.driver.findElements(CEDIS_Ruta).isEmpty()!= false) {
    		baseExecution.addStep("Se falla el caso");        
    		Assert.fail("No se ha encontrado el campo de Ruta de la pantalla de RESUMEN DEVOLUCIÓN en la parte de inicio", null);
    	}    	
    	if(au.driver.findElements(CEDIS_RutaDinamica).isEmpty()!= false) {
    		baseExecution.addStep("Se falla el caso");        
    		Assert.fail("No se ha encontrado el campo de Ruta Dinamica de la pantalla de RESUMEN DEVOLUCIÓN en la parte de inicio", null);
    	}
    	if(au.driver.findElements(CEDIS_CRTienda).isEmpty()!= false) {
    		baseExecution.addStep("Se falla el caso");        
    		Assert.fail("No se ha encontrado el campo de CR Tienda de la pantalla de RESUMEN DEVOLUCIÓN en la parte de inicio", null);
    	}
    	if(au.driver.findElements(CEDIS_NombreTienda).isEmpty()!= false) {
    		baseExecution.addStep("Se falla el caso");        
    		Assert.fail("No se ha encontrado el campo de Nombre Tienda de la pantalla de RESUMEN DEVOLUCIÓN en la parte de inicio", null);
    	}
    	if(au.driver.findElements(CEDIS_Lider).isEmpty()!= false) {
    		baseExecution.addStep("Se falla el caso");        
    		Assert.fail("No se ha encontrado el campo de Lider de la pantalla de RESUMEN DEVOLUCIÓN en la parte de inicio", null);
    	}
    	if(au.driver.findElements(CEDIS_Transferencia).isEmpty()!= false) {
    		baseExecution.addStep("Se falla el caso");        
    		Assert.fail("No se ha encontrado el campo de Transferencia de la pantalla de RESUMEN DEVOLUCIÓN en la parte de inicio", null);
    	}    	
    }
    
    public void ClickFirmaAcuerdo(String tomaEvidencia,String descPaso) throws InterruptedException {
    	//Funcion que toma la foto de la Canastilla, toma en cuenta solo una Canastilla de Devolucion, si se agregan mas el identificador cambiara  Creado: Juan Ibarra
    	au.scrollToElement(CEDIS_FirmaAcuerdoDevolucion);
    	if (tomaEvidencia.equals("tomaEvidencia")) {
    		baseExecution.addStep(descPaso);    		
    		testCase.addScreenShotCurrentStep(au, "testImage");
    		Thread.sleep(2000);
        	au.click(CEDIS_FirmaAcuerdoDevolucion);        	
    		}else {
    			au.click(CEDIS_FirmaAcuerdoDevolucion);
    		}    		
    }    
    
    public void BuscarSeccion(String ObjetoPantalla)
    {  	
    	//Funcion para buscar objeto y dar click especifico en la pantalla Creado: Juan Ibarra    	
    	switch (ObjetoPantalla) {    	
    	case "Sección Canastillas":    		
    		au.scrollToElement(CEDIS_CantidadTotalCanastillas);
            break;
    	case "Sección Contenedores":    		
    		au.scrollToElement(CEDIS_CantidadTotalContenedores);
            break;
    	case "Sección Bols":    		
    		au.scrollToElement(CEDIS_CantidadTotalBOL);
            break;    	       
    	case "Sección Devolución":    		
    		au.scrollToElement(CEDIS_FirmaAcuerdoDevolucion);
            break;
    	case "Leyenda Devolución": //Se agrega busqueda leyenda devolucion: Vanessa Torres 17/02/25
    		au.scrollToElement(CEDIS_LeyendaResumenDevolucion);
    		break;
        default:
            baseExecution.addStep("Se falla el caso"); //Se agrega el paso a mostrar en el PDF
    		Assert.fail("No se ha encontrado la "+ ObjetoPantalla, null); //Se agrega un mensaje a mostrar del error, en caso de no ponerlo regresaria leyenda de null
    	}    	
    }
    
    public int CantidadColor (String Color) throws IOException {
    	//Funcion para obtener el valor de canastillas por color  Creado: Juan Ibarra Fecha: 22-Octubre-2024
    	switch (Color) {
    	case "Azul":
    		au.scrollToElement(CEDIS_CantidadAzul);    			
    		String TotalAzul = au.driver.findElement(CEDIS_CantidadAzul).getText();//Se obtiene el valor de Total de Canastilla  
        	int CantidadAzul= Integer.parseInt(TotalAzul);
        	return CantidadAzul;
        case "Amarilla":
        	au.scrollToElement(CEDIS_CantidadAmarilla);    			
    		String TotalAmarilla = au.driver.findElement(CEDIS_CantidadAmarilla).getText();//Se obtiene el valor de Total de Canastilla  
        	int CantidadAmarilla= Integer.parseInt(TotalAmarilla);
        	return CantidadAmarilla;        	
		case "Verde":
			au.scrollToElement(CEDIS_CantidadVerde);    			
    		String TotalVerde = au.driver.findElement(CEDIS_CantidadVerde).getText();//Se obtiene el valor de Total de Canastilla  
        	int CantidadVerde= Integer.parseInt(TotalVerde);
        	return CantidadVerde;			
        case "Naranja":
        	au.scrollToElement(CEDIS_CantidadNaranja);    			
    		String TotalNaranja= au.driver.findElement(CEDIS_CantidadNaranja).getText();//Se obtiene el valor de Total de Canastilla  
        	int CantidadNaranja= Integer.parseInt(TotalNaranja);
        	return CantidadNaranja;        	
        case "Otro":
        	au.scrollToElement(CEDIS_CantidadOtro);    			
    		String TotalOtro = au.driver.findElement(CEDIS_CantidadOtro).getText();//Se obtiene el valor de Total de Canastilla  
        	int CantidadOtro= Integer.parseInt(TotalOtro);
        	return CantidadOtro;        	        
        default:
        baseExecution.addStep("Se falla el caso");
        //Se agrega el paso a mostrar en el PDF
		Assert.fail("No se ha encontrado la canastilla del color "+ Color + " mencionado", null);
		//Se agrega un mensaje a mostrar del error, en caso de no ponerlo regresaria leyenda de null
		}
		return 0;    	     
    }
    
    public int TotalCanastillas () throws IOException {
    	//Funcion para obtener el Total de Canastillas Creado: Juan Ibarra Fecha: 22-Octubre-2024
    	au.scrollToElement(CEDIS_CantidadTotalCanastillas);
    	String TotalCanastilla = au.driver.findElement(CEDIS_CantidadTotalCanastillas).getText();//Se obtiene el valor de Total de Canastilla  
    	int CantidadCanastillas= Integer.parseInt(TotalCanastilla);
    	return CantidadCanastillas;        
    }
    
    public int CantidadContenedor (String Contenedor) throws IOException {
    	//Funcion para obtener el valor de canastillas por color  Creado: Juan Ibarra Fecha: 22-Octubre-2024
    	switch (Contenedor) {
    	case "Tapas":
    		au.scrollToElement(CEDIS_CantidadTapas);    			
    		String TotalTapas = au.driver.findElement(CEDIS_CantidadTapas).getText();//Se obtiene el valor de Total de Canastilla  
        	int CantidadTapas= Integer.parseInt(TotalTapas);
        	return CantidadTapas;
        case "Envases":
        	au.scrollToElement(CEDIS_CantidadEnvase);    			
    		String TotalEnvases= au.driver.findElement(CEDIS_CantidadEnvase).getText();//Se obtiene el valor de Total de Canastilla  
        	int CantidadEnvases= Integer.parseInt(TotalEnvases);
        	return CantidadEnvases;        			        	        
        default:
        baseExecution.addStep("Se falla el caso");
        //Se agrega el paso a mostrar en el PDF
		Assert.fail("No se ha encontrado el contenedor "+ Contenedor + " mencionado", null);
		//Se agrega un mensaje a mostrar del error, en caso de no ponerlo regresaria leyenda de null
		}
    	return 0;           
    }
    
    public int TotalContenedor () throws IOException {
    	//Funcion para obtener el Total de Canastillas Creado: Juan Ibarra Fecha: 22-Octubre-2024
    	au.scrollToElement(CEDIS_CantidadTotalContenedores);
    	String TotalContenedor = au.driver.findElement(CEDIS_CantidadTotalContenedores).getText();//Se obtiene el valor de Total de Canastilla  
    	int CantidadContenedor= Integer.parseInt(TotalContenedor);
    	return CantidadContenedor;        
    }
    
    public int TotalBol () throws IOException {
    	//Funcion para obtener el Total de Canastillas Creado: Juan Ibarra Fecha: 22-Octubre-2024
    	au.scrollToElement(CEDIS_CantidadTotalBOL);
    	String TotalBol = au.driver.findElement(CEDIS_CantidadTotalBOL).getText();//Se obtiene el valor de Total de Canastilla  
    	int CantidadBol= Integer.parseInt(TotalBol);
    	return CantidadBol;        
    }
    
    public int TotalDevolucion () throws IOException {
    	//Funcion para obtener el Total de Canastillas Creado: Juan Ibarra Fecha: 22-Octubre-2024
    	au.scrollToElement(CEDIS_CantidadTotalContenedor);
    	String TotalCanastillas = au.driver.findElement(CEDIS_CantidadTotalContenedor).getText();//Se obtiene el valor de Total de Canastilla  
    	int CantidadCanastillas= Integer.parseInt(TotalCanastillas);
    	return CantidadCanastillas;        
    }
    
    public void ValidacionPantallaResumenDevolucion() throws IOException, InterruptedException {
  	  //Funcion para validar la pantalla Resumen de Devolucion
  	  //Creado: Jesus Marcelo Gutierrez Bravo
  	  //Fecha Creación: 27-01-2025
  	  Thread.sleep(1000);
  	  au.scrollToElement(CEDIS_TituloPantalla);
  	  Thread.sleep(1000);
  	  if(au.driver.findElement(CEDIS_TituloPantalla).isEnabled()==false) {
    		baseExecution.addStep("Se falla el caso");        
    		Assert.fail("No se ha encontrado la pantalla/sección de Resumen de Devolución", null);
    	} 
    }
    
    public void ValidacionSeccionDevolucionDeContenedores() throws IOException, InterruptedException {
  	  //Descripcion: Método para validar la pantalla Resumen de Devolucion
  	  //Creado: Jesus Marcelo Gutierrez Bravo
  	  //Fecha Creación: 27-01-2025
  	  Thread.sleep(1000);
  	  au.scrollToElement(CEDIS_LeyendaDevolucionContenedores);
  	  Thread.sleep(1000);
  	  if(au.driver.findElement(CEDIS_LeyendaDevolucionContenedores).isEnabled()==false) {
    		baseExecution.addStep("Se falla el caso");        
    		Assert.fail("No se ha encontrado la pantalla/sección de Devolucion de Contenedores", null);
    	} 
    }
    
    public void ValidacionTablaCanastillas() throws IOException, InterruptedException {
        // Método para validar la tabla 'Canastillas'.
        // 1. Verifica la visibilidad de la tabla.
        // 2. Obtiene las cantidades de cada tipo de canastilla.
        // 3. Suma las cantidades obtenidas, excluyendo los valores de '0'.
        // 4. Compara la cantidad obtenida con la suma total esperada.
        // 5. Si la cantidad no coincide con la suma total, el caso falla.
        
        // Creado: Jesus Marcelo Gutierrez Bravo
        // Fecha Creación: 05-02-2025
        
        Thread.sleep(1000);
        au.scrollToElement(CEDIS_LeyendaContenedores);
        Thread.sleep(1000);
        
        WebDriverWait wait = new WebDriverWait(au.driver, Duration.ofSeconds(10));
        WebElement leyendaCanastillas;
        
        if(au.driver.findElement(CEDIS_LeyendaAzul).isEnabled()==false) {
    		baseExecution.addStep("No se ha encontrado fila con el color: Azul");        
    		Assert.fail("No se ha encontrado fila con el color: Azul", null);
    		testCase.addScreenShotCurrentStep(au, "testImage");
    	} 
        
        if(au.driver.findElement(CEDIS_LeyendaAmarilla).isEnabled()==false) {
    		baseExecution.addStep("No se ha encontrado fila con el color: Amarilla");        
    		Assert.fail("No se ha encontrado fila con el color: Amarilla", null);
    		testCase.addScreenShotCurrentStep(au, "testImage");
    	} 
        
        if(au.driver.findElement(CEDIS_LeyendaVerde).isEnabled()==false) {
    		baseExecution.addStep("No se ha encontrado fila con el color: Verde");        
    		Assert.fail("No se ha encontrado fila con el color: Verde", null);
    		testCase.addScreenShotCurrentStep(au, "testImage");
    	} 
        
        if(au.driver.findElement(CEDIS_LeyendaNaranja).isEnabled()==false) {
    		baseExecution.addStep("No se ha encontrado fila con el color: Naranja");        
    		Assert.fail("No se ha encontrado fila con el color: Naranja", null);
    		testCase.addScreenShotCurrentStep(au, "testImage");
    	} 
        
        if(au.driver.findElement(CEDIS_LeyendaOtro).isEnabled()==false) {
    		baseExecution.addStep("No se ha encontrado fila con la opcion: Otro");        
    		Assert.fail("No se ha encontrado fila con la opcion: Otro", null);
    		testCase.addScreenShotCurrentStep(au, "testImage");
    	} 
        
        try {
            leyendaCanastillas = wait.until(ExpectedConditions.visibilityOfElementLocated(CEDIS_LeyendaCanastillas));
        } catch (TimeoutException e) {
            baseExecution.addStep("Se falla el caso");
            Assert.fail("No se ha encontrado la Tabla de Canastillas");
    		testCase.addScreenShotCurrentStep(au, "testImage");
            return;
        }
        
        // 🔹 Obtener las cantidades de cada tipo de canastilla
        int cantidadAzul = obtenerValorNumerico(wait, CEDIS_CantidadAzul);
        int cantidadAmarilla = obtenerValorNumerico(wait, CEDIS_CantidadAmarilla);
        int cantidadVerde = obtenerValorNumerico(wait, CEDIS_CantidadVerde);
        int cantidadNaranja = obtenerValorNumerico(wait, CEDIS_CantidadNaranja);
        int cantidadOtro = obtenerValorNumerico(wait, CEDIS_CantidadOtro);
        
        // 🔹 Sumar las cantidades excluyendo los valores de '0'
        int sumaTotalCanastillas = 0;
        if (cantidadAzul > 0) sumaTotalCanastillas += cantidadAzul;
        if (cantidadAmarilla > 0) sumaTotalCanastillas += cantidadAmarilla;
        if (cantidadVerde > 0) sumaTotalCanastillas += cantidadVerde;
        if (cantidadNaranja > 0) sumaTotalCanastillas += cantidadNaranja;
        if (cantidadOtro > 0) sumaTotalCanastillas += cantidadOtro;
        
        // 🔹 Validamos la fila de suma total
        au.scrollToElement(CEDIS_LeyendaContenedores);
        int sumaTotalEsperada = obtenerValorNumerico(wait, CEDIS_CantidadTotalCanastillas);
        
        // 🔹 Comparar la cantidad de canastillas con la suma total esperada
        if (sumaTotalCanastillas != sumaTotalEsperada) {
            baseExecution.addStep("Se falla el caso");
            Assert.fail("El número de canastillas no coincide con la suma total en la tabla 'Canastillas'.");
    		testCase.addScreenShotCurrentStep(au, "testImage");
        }
    }
    
    public void ValidacionTablaContenedores() throws IOException, InterruptedException {
        // Descripción: Método para validar la pantalla "Resumen de Devolución" verificando la tabla de Contenedores.
        // Se revisan las leyendas de cada sección y se valida que la suma de los valores sea correcta.
        // Autor: Jesus Marcelo Gutierrez Bravo
        // Fecha Creación: 27-01-2025

        // Se inicializa WebDriverWait con un tiempo de espera máximo de 10 segundos
        WebDriverWait wait = new WebDriverWait(au.driver, Duration.ofSeconds(10));

        // 🔹 Validar la presencia de la tabla "Contenedores"
        au.scrollToElement(CEDIS_LeyendaContenedores);
        WebElement leyendaContenedores = wait.until(ExpectedConditions.visibilityOfElementLocated(CEDIS_LeyendaContenedores));
        if (!leyendaContenedores.isEnabled()) {
            baseExecution.addStep("Se falla el caso");
            Assert.fail("No se ha encontrado la tabla de Contenedores");
        }

        // 🔹 Validar la presencia de la leyenda "Tapas de Canastillas"
        au.scrollToElement(CEDIS_LeyendaTapas);
        WebElement leyendaTapas = wait.until(ExpectedConditions.visibilityOfElementLocated(CEDIS_LeyendaTapas));
        if (!leyendaTapas.isEnabled()) {
            baseExecution.addStep("Se falla el caso");
            Assert.fail("No se ha encontrado la sección de Tapas de Canastillas");
        }

        // 🔹 Validar la presencia de la leyenda "Envases Vacíos"
        au.scrollToElement(CEDIS_LeyendaEnvase);
        WebElement leyendaEnvase = wait.until(ExpectedConditions.visibilityOfElementLocated(CEDIS_LeyendaEnvase));
        if (!leyendaEnvase.isEnabled()) {
            baseExecution.addStep("Se falla el caso");
            Assert.fail("No se ha encontrado la sección de Envases Vacíos");
        }

        // 🔹 Obtener los valores numéricos de la tabla para realizar la validación de suma
        int CantidadTapas = obtenerValorNumerico(wait, CEDIS_CantidadTapas);
        int CantidadEnvases = obtenerValorNumerico(wait, CEDIS_CantidadEnvase);
        int CantidadTotalContenedores = obtenerValorNumerico(wait, CEDIS_CantidadTotalContenedores);

        // 🔹 Validar que la suma de "Tapas de Canastillas" + "Envases Vacíos" sea igual al "Total de Contenedores"
        if (CantidadTapas + CantidadEnvases != CantidadTotalContenedores) {
            baseExecution.addStep("Se falla el caso");
            Assert.fail("La suma de Tapas y Envases no coincide con el Total de Contenedores");
        }
    }
    
    public void ValidacionSeccionBOLsDevolucion() throws IOException, InterruptedException {
    	  //Funcion para validar la pantalla Resumen de Devolucion
    	  //Creado: Jesus Marcelo Gutierrez Bravo
    	  //Fecha Creación: 05-02-2025
    	  Thread.sleep(1000);
    	  au.scrollToElement(CEDIS_LeyendaTotalBOL);
    	  Thread.sleep(1000);
    	  if(au.driver.findElement(CEDIS_LeyendaBOL).isEnabled()==false) {
      		baseExecution.addStep("Se falla el caso");        
      		Assert.fail("No se ha encontrado la pantalla/sección de BOL´S DE DEVOLUCIÓN", null);
      	} 
      }
      
      public void ValidacionTablaBOLsADevolver() throws IOException, InterruptedException {
          // Método para validar la tabla 'BOLs A DEVOLVER'.
          // 1. Verifica la visibilidad de la tabla.
          // 2. Obtiene los valores de los BOLs de los elementos normales y especiales.
          // 3. Separa los BOLs cuando están en un solo string separados por comas.
          // 4. Filtra los valores vacíos y excluye los valores de '0'.
          // 5. Compara la cantidad obtenida con la suma total esperada.
          // 6. Si la cantidad no coincide con la suma total, el caso falla.
          
          // Creado: Jesus Marcelo Gutierrez Bravo
          // Fecha Creación: 05-02-2025
          
          Thread.sleep(1000);
          au.scrollToElement(CEDIS_LeyendaCanastillaDevolucion);
          Thread.sleep(1000);
          
          WebDriverWait wait = new WebDriverWait(au.driver, Duration.ofSeconds(10));
          WebElement leyendaBOLDevolver;
          
          try {
              leyendaBOLDevolver = wait.until(ExpectedConditions.visibilityOfElementLocated(CEDIS_LeyendaBOLDevolver));
          } catch (TimeoutException e) {
              baseExecution.addStep("Se falla el caso");
              Assert.fail("No se ha encontrado la Tabla de BOL´S A DEVOLVER");
              return;
          }
          
          // 🔹 Validamos si existen registros de BOLs
          List<WebElement> elementosBOLEspecial = au.driver.findElements(CEDIS_BOLEspecial);
          List<WebElement> elementosBOLNormal = au.driver.findElements(CEDIS_BOLNormal);
          
          List<String> bolsFiltrados = new ArrayList<>();
          
          for (WebElement bolElement : elementosBOLEspecial) {
              String textoBOLs = bolElement.getText().trim();
              if (!textoBOLs.isEmpty()) {
                  for (String bol : textoBOLs.split(",")) {
                      if (!bol.trim().equals("0")) {
                          bolsFiltrados.add(bol.trim());
                      }
                  }
              }
          }
          
          for (WebElement bolElement : elementosBOLNormal) {
              String textoBOLs = bolElement.getText().trim();
              if (!textoBOLs.isEmpty()) {
                  for (String bol : textoBOLs.split(",")) {
                      if (!bol.trim().equals("0")) {
                          bolsFiltrados.add(bol.trim());
                      }
                  }
              }
          }
          
          int cantidadFilasConBOL = bolsFiltrados.size();
          
          // 🔹 Validamos la fila de suma total
          au.scrollToElement(CEDIS_CantidadTotalBOL);
          String textoSumaTotal = au.driver.findElement(CEDIS_CantidadTotalBOL).getText().trim();
          
          int sumaTotalFilas = 0;
          try {
              sumaTotalFilas = Integer.parseInt(textoSumaTotal);
          } catch (NumberFormatException e) {
              baseExecution.addStep("Se falla el caso");
              Assert.fail("El valor total de BOLs no es un número válido: " + textoSumaTotal);
          }
          
          // 🔹 Comparar la cantidad de BOLs con la suma total
          if (cantidadFilasConBOL != sumaTotalFilas) {
              baseExecution.addStep("Se falla el caso");
              Assert.fail("El número de BOLs no coincide con la suma total en la tabla 'BOLs A DEVOLVER'.");
          }
      }
    
    public void ValidacionSeccionCanastillasConDevolucion() throws IOException, InterruptedException {
    	  //Funcion para validar la Sección de Canastillas Con Devolución
    	  //Creado: Jesus Marcelo Gutierrez Bravo
    	  //Fecha Creación: 04-02-2025
    	  Thread.sleep(1000);
    	  au.scrollToElement(CEDIS_LeyendaCanastillaDevolucion);
    	  Thread.sleep(1000);
    	  if(au.driver.findElement(CEDIS_LeyendaCanastillaDevolucion).isEnabled()==false) {
      		baseExecution.addStep("Se falla el caso");        
      		Assert.fail("No se ha encontrado la Tabla de Canastillas Con Devolución", null);     		
      		
      	} 
      }

    public void ValidacionTablaCanastillasConDevolucion() throws IOException, InterruptedException {
        // Descripción: Método para validar la tabla "Canastillas con Devolución".
        // Se cuenta el número de códigos en las filas y se valida contra la fila de suma total.
        // Creado: Jesus Marcelo Gutierrez Bravo
        // Fecha Creación: 04-02-2025

        WebDriverWait wait = new WebDriverWait(au.driver, Duration.ofSeconds(10));

        // 🔹 Esperamos que la tabla "Canastillas con Devolución" sea visible
        au.scrollToElement(CEDIS_LeyendaCanastillaDevolucion);
        WebElement leyendaCanastillasDevolucion = wait.until(ExpectedConditions.visibilityOfElementLocated(CEDIS_LeyendaCanastillaDevolucion));
        if (!leyendaCanastillasDevolucion.isEnabled()) {
            baseExecution.addStep("Se falla el caso");
            Assert.fail("No se ha encontrado la tabla 'Canastillas con Devolución'");
        }

        // 🔹 Validamos si existen registros de códigos
        List<WebElement> elementosCodigo = au.driver.findElements(CEDIS_RegistroContenedor);
        int cantidadFilasConCodigo = 0; // Valor por defecto si no hay códigos

        if (!elementosCodigo.isEmpty()) {
            // 🔹 Obtener el texto con los códigos
            WebElement contenedorCodigo = elementosCodigo.get(0);
            String textoCódigos = contenedorCodigo.getText().trim();

            // 🔹 Dividir el texto por saltos de línea y filtrar líneas vacías
            String[] codigos = textoCódigos.split("\\r?\\n");
            List<String> codigosFiltrados = new ArrayList<>();
            for (String codigo : codigos) {
                if (!codigo.trim().isEmpty()) {
                    codigosFiltrados.add(codigo);
                }
            }
            cantidadFilasConCodigo = codigosFiltrados.size();
        }

        // 🔹 Validamos la fila de suma total
        au.scrollToElement(CEDIS_CantidadTotalContenedor);
        String textoSumaTotal = au.driver.findElement(CEDIS_CantidadTotalContenedor).getText().trim();
        int sumaTotalFilas = Integer.parseInt(textoSumaTotal);

        // 🔹 Comparar la cantidad de códigos con la suma total
        if (cantidadFilasConCodigo != sumaTotalFilas) {
            baseExecution.addStep("Se falla el caso");
            Assert.fail("El número de códigos no coincide con la suma total en la tabla 'Canastillas con Devolución'.");
        } else {
    		System.out.println("\n");
        }
    }
    
    public void ValidacionBotonFirmaDeAcuerdo() throws IOException, InterruptedException {
  	  //Descripcion: Funcion para validar la Sección de Canastillas Con Devolución
  	  //Creado: Jesus Marcelo Gutierrez Bravo
  	  //Fecha Creación: 05-02-2025
		Thread.sleep(1000);
    	BuscarSeccion("Sección Devolución");
		Thread.sleep(1000);
  	  		if(au.driver.findElement(CEDIS_FirmaAcuerdoDevolucion).isEnabled()==false) {
  	  			baseExecution.addStep("Se falla el caso");        
  	  			Assert.fail("No se ha encontrado el Botón: FIRMA DE ACUERDO", null);
    	} 
    }






    

    /**
     * Método para obtener un número de alguna parte de la tabla (canastillas, tapas, envases, etc.).
     * Toma el valor del locator como texto y lo convierte a entero para poder hacer operaciones matemáticas.
     *
     * @param wait    Instancia de WebDriverWait para esperar a que el elemento sea visible.
     * @param locator Ubicación del elemento en la página web.
     * @return El valor numérico del elemento, convertido a entero.
     */
    private int obtenerValorNumerico(WebDriverWait wait, By locator) {
        // Esperar hasta que el elemento sea visible y obtener su texto
        WebElement elemento = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
        String texto = elemento.getText().trim();

        // Intentar convertir el texto a número
        try {
            return Integer.parseInt(texto);
        } catch (NumberFormatException e) {
            baseExecution.addStep("Error al convertir el valor: " + texto);
            Assert.fail("El valor obtenido no es un número válido: " + texto);
            return 0; // Este retorno nunca se ejecutará porque `Assert.fail()` detendrá la ejecución
        }
    }
    
    public void subir () throws InterruptedException, IOException {
    	
    	Runtime.getRuntime().exec("adb shell input swipe 100 180 100 950 200");
		Thread.sleep(2000);
    }
    
    public void sendKeysUsingKeyEvents(AppiumUtil au, String text) { //Vanessa Torres 20/02/2025
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
}

    
    
    
    
    