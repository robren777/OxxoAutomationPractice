package Mobile.cedis.om;

import java.io.IOException;
import java.time.Duration;
import java.util.List;

/* import static org.testng.Assert.assertEquals;
import java.time.Duration;
import modelo.BaseExecution;
import modelo.TestCase;
import utils.AppiumUtil; */

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import io.appium.java_client.AppiumBy;
import io.netty.handler.timeout.TimeoutException;
import modelo.BaseExecution;
import modelo.TestCase;
import utils.AppiumUtil;

public class C02_Verificacion_De_Pedido_Y_Permisos {
    private TestCase testCase;
	private AppiumUtil au;
	private BaseExecution baseExecution; 

    // -------------------------------- Identificadores Verificacion_de_pedido_y_permisos-------------------------------- //
	
    private By CEDIS_TitutoPermiso = By.xpath("//android.widget.TextView[@resource-id=\"android:id/alertTitle\" and @text=\"Necesita permiso(s)\"]");
    private By CEDIS_MensajeConfirmacionPermiso = By.xpath("//android.widget.TextView[@resource-id=\\\"android:id/message\\\" and @text=\"Se requieren algunos permisos para realizar tareas en la aplicación.\"]");
    private By CEDIS_BotonAceptar = By.xpath("//android.widget.Button[@resource-id=\"android:id/button1\"]");
    private By CEDIS_btnMientrasAppenUso = By.xpath("//android.widget.Button[@resource-id=\"com.android.permissioncontroller:id/permission_allow_foreground_only_button\"]");
    private By CEDIS_btnPermitir = By.xpath("//android.widget.Button[@resource-id=\"com.android.permissioncontroller:id/permission_allow_button\"]");

    
    // -------------------------------- Identificadores Pantalla_Verificacion-------------------------------- //

    private By CEDIS_LeyendaVerificacionMercancia = By.xpath("//android.widget.TextView[@text=\"VERIFICACIÓN DE MERCANCÍA PEDIDO CEDIS\"]");
    
    private By CEDIS_ProgresoBarra = By.xpath("//android.widget.ProgressBar[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/progressBar\"]");
    
    //private By CEDIS_ProgresoBarra = AppOxxo.MobileObject("com.femco.oxxo.operaciones.mitienda.debug:id/progressBar","nativeclass:=android\.widget\.ProgressBar");
       
    
    private By CEDIS_IconoAyudaVerificacion = By.id("com.femco.oxxo.operaciones.mitienda.debug:id/tutorial");
    
    private By CEDIS_IconoPreguntasFrecuentesVerificacion = By.id("com.femco.oxxo.operaciones.mitienda.debug:id/help");
    
    private By CEDIS_CampoFiltrarInformacion = By.id("com.femco.oxxo.operaciones.mitienda.debug:id/auto_search");
    
    private By CEDIS_TextoNoEscaneo = By.xpath("//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/tv_question\" and @text=\"¿Qué pasa si NO escaneo todos los bultos en el proceso de verificación?\"]");
  
    private By CEDIS_RespuestaNoEscaneo = By.xpath("//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/tv_answer\" and @text=\"Se deberán escanear todos los bultos en caso de NO hacerlo, corres el riesgo de NO verificar todos los artículos seleccionados.\"]");
    
    private By CEDIS_SiFotografias = By.xpath("//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/tv_question\" and @text=\"¿Qué pasa si las fotografías que tomé NO muestran la mercancía visible?\"]");
    
    private By CEDIS_RespuestaSiFotografias = By.xpath("//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/tv_answer\" and @text=\"Devoluciones CEDIS no tendrá evidencias por lo que corremos el riesgo de que las devoluciones sean rechazadas.\"]");
    
    private By CEDIS_SiDetecta = By.xpath("//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/tv_question\" and @text=\"¿Qué pasa si se detectan faltantes, sobrantes, dañados o caducos en el pedido?\"]");
    
    private By CEDIS_RespuestaSiDetecta = By.xpath("//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/tv_answer\" and @text=\"Al finalizar el proceso de verificación y la captura de dañados y/o caducos, en automático se generarán los bols de devolución. (Hay que entregar la mercancía en la próxima visita de CEDIS).\"]");
    
    private By CEDIS_NoProceso = By.xpath("//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/tv_question\" and @text=\"¿Qué pasa si no hago el proceso de verificación dentro de las primeras 24 hrs?\"]");
    
    private By CEDIS_RespuestaNoProceso = By.xpath("//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/tv_answer\" and @text=\"El pedido pasará a estatus vencido y no se podrá verificar.\"]");
    
    private By CEDIS_RespuestaVisualizarEstatus = By.xpath("//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/tv_answer\" and @text=\"En la pantalla principal, podrás ver el resumen de cada transferencia con estatus Verificado.\"]");
    
    private By CEDIS_CerrarAyuda = By.xpath("com.femco.oxxo.operaciones.mitienda.debug:id/btn_close");
    
    
    private By CEDIS_Tutorial = By.xpath("//android.widget.Button[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/tutorial\" and @text=\"text:=Tutorial\"]");
    //private By CEDIS_TutorialSelecionaPedido = AppOxxo.MobileLabel("com.femco.oxxo.operaciones.mitienda.debug:id/tv_title","text:=Selecciona el pedido a verificar");
//Falta detalle de  la parte de Tutorial
   
    
    //ENCABEZADO
    private By CEDIS_LogoOxxoVerificacion = By.id("com.femco.oxxo.operaciones.mitienda.debug:id/imgResume");
    
    private By CEDIS_FechaVerificacion = By.id("com.femco.oxxo.operaciones.mitienda.debug:id/tvDateText");
    
    private By CEDIS_DetalleFechaVerificacion = By.id("com.femco.oxxo.operaciones.mitienda.debug:id/tvTitleDate");
    
    private By CEDIS_RutaVerificacion = By.id("com.femco.oxxo.operaciones.mitienda.debug:id/tvTitleRute");
    
    private By CEDIS_RutaDinamicaVerificacion = By.id("com.femco.oxxo.operaciones.mitienda.debug:id/tvTitleRuteD");
    
    private By CEDIS_CRVerificacion = By.id("com.femco.oxxo.operaciones.mitienda.debug:id/tvTitleStore");
    
    private By CEDIS_NombreTiendaVerificacion = By.id("com.femco.oxxo.operaciones.mitienda.debug:id/tvTitleNameStore");
 
    private By CEDIS_NumeroDeTransferencia = By.id("com.femco.oxxo.operaciones.mitienda.debug:id/tvTransferNumber");
 
    //PANTALLA DE VERIFICACION
    
    private By CEDIS_LeyendaInstrucciones = By.id("com.femco.oxxo.operaciones.mitienda.debug:id/text_view_title_instructions");
    
    private By CEDIS_PasosInstrucciones = By.id("com.femco.oxxo.operaciones.mitienda.debug:id/text_view_body_instruccions");
    
    private By CEDIS_LeyendaBultosTotales = By.xpath("//android.widget.TextView[@text=\"Bultos totales:\"]"); 
    
    private By CEDIS_LeyendaBultosVerificados = By.xpath("//android.widget.TextView[@text=\"Bultos Verificados:\"]");
    
    private By CEDIS_CantidadBultosTotales = By.id("com.femco.oxxo.operaciones.mitienda.debug:id/tvBultosTotal");
    
    private By CEDIS_CantidadBultosVerificados = By.id("com.femco.oxxo.operaciones.mitienda.debug:id/tvBultosVerificados");
    
    private By CEDIS_BotonBultos = By.xpath("//android.widget.Button[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/buttonLista\" and @text=\"BULTOS\"]");
      
    private By CEDIS_BotonEscanear = By.xpath("//android.widget.Button[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/buttonFinishVerify\" and @text=\"text:=COMIENZA A ESCANEAR\"]");
    
    private By CEDIS_SeleccionarBulto = By.id("android:id/alertTitle");
    
    private By CEDIS_BultoSeleccionable = By.xpath("//android.widget.ListView[@resource-id=\"android:id/select_dialog_listview\"]");
    
    private By CEDIS_ArticuloCanastilla = By.xpath("//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/encabezado_titulo\"]");
    
    private By CEDIS_LeyendaEvidenciaBulto = By.id("com.femco.oxxo.operaciones.mitienda.debug:id/encabezado_nombre_foto");
    
    private By CEDIS_FotoArticulo = By.xpath("//android.widget.ImageButton[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/foto\"]");

    private By CEDIS_IconoFotografia = By.xpath("//android.widget.ImageView[@content-desc=\"Obturador\"]");
    
    private By CEDIS_CheckFotoVerificacionPaloma = By.xpath("//android.widget.ImageView[@content-desc=\"Listo\"]");
    
    private By CEDIS_CheckFotoVerificacionCancelarFotoX = By.id("org.codeaurora.snapcam:id/btn_cancel");
    
    private By CEDIS_MensajeConfirmarFotoVerificacionOpcionSi = By.xpath("//android.widget.Button[@resource-id=\"android:id/button1\"]");

    private By CEDIS_MensajeConfirmarFotoVerificacionOpcionNo = By.xpath("//android.widget.Button[@resource-id=\"android:id/button2\"]");
    
    private By CEDIS_LeyendaGuardarFoto = By.xpath("//android.widget.TextView[@resource-id=\"android:id/message\"]");
    
    private By CEDIS_ArticuloVerificar = By.xpath("//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/title_item_description\" and @elementId=\"00000000-0000-004a-ffff-ffff0000061d\"]");
        
    private By CEDIS_SkuVerificar = By.xpath("//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/title_item_sku\" and @elementId=\"00000000-0000-004a-ffff-ffff0000061f\"]");
    
    private By CEDIS_CheckPorVerificar = By.xpath("//android.widget.CheckBox[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/checkBoxItemsVerify\" and @checked=\"false\"]");
    
    private By CEDIS_CheckVerificado = By.xpath("//android.widget.CheckBox[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/checkBoxItemsVerify\" and @checked=\"true\"]");
    
    private By CEDIS_CantidadArticulo = By.xpath("//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/tv_qty_required\"]");
    
    private By CEDIS_CantidadArticuloVerificado = By.xpath("(//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/textViewItemsVerify\"])[1]");
    
    private By CEDIS_DisminuirArticulo = By.xpath("(//android.widget.ImageView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/imageMinusItems\"])[1]");
    
    private By CEDIS_AumentarArticulo = By.xpath("//android.widget.ImageView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/imagePlusItems\"]");
    
    private By CEDIS_BotonAumentarTercerArticulo = By.xpath("(//android.widget.ImageView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/imagePlusItems\"])[3]");
    
    //private By CEDIS_CantidadArticulo2 = By.xpath("(//android.view.ViewGroup[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/containerViewItem\"])[2]/android.widget.LinearLayout[2]" );
    //private By CEDIS_CantidadArticulo2 = By.xpath("(//android.view.ViewGroup[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/containerViewItem\"])[2]/android.widget.LinearLayout[2] and //android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/tv_qty_required\"]");
    //private By CEDIS_CantidadArticulo2 = By.className("android.widget.LinearLayout").xpath("//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/tv_qty_required\"]");
    //private By CEDIS_CantidadArticulo2 = By.xpath("(//android.view.ViewGroup[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/containerViewItem\"])[2]/android.widget.LinearLayout[2]").xpath("//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/tv_qty_required\"]");
    //private By CEDIS_CantidadArticulo2 = By.xpath("(//android.view.ViewGroup[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/containerViewItem\"])[2]/android.widget.LinearLayout[2]").xpath("//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/tv_qty_required\" and @text=\"CANTIDAD 4\"]");
    
    private By CEDIS_CantidadArticulo2 = By.xpath("(//android.view.ViewGroup[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/containerViewItem\"])[3]/android.widget.LinearLayout[2]");
    		
    //private By CEDIS_CantidadArticulo2 = By.xpath("(//android.view.ViewGroup[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/containerViewItem\"])[2]/android.widget.LinearLayout[2]".intern("com.femco.oxxo.operaciones.mitienda.debug:id/tv_qty_required");
    //private By CEDIS_CantidadArticulo2 = By.xpath("//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/tv_qty_required\" and @index=\"8\"]");
    //private By CEDIS_CantidadArticulo2 = By.xpath("//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/tv_qty_required\" and @bounds=\"[482,901][688,935]\"]");
    
    		
    private By CEDIS_AumentarArticulo2 = By.xpath("(//android.widget.ImageView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/imagePlusItems\"])[2]");
      
    private By CEDIS_LeyendaArticuloPendiente = By.xpath("//android.widget.TextView[@resource-id=\"android:id/message\"]");
    
    private By CEDIS_AceptarLeyendaArticuloPendiente = By.xpath("//android.widget.Button[@resource-id=\"android:id/button1\"]");
    
    private By CEDIS_LeyendaDiferenteCantidad = By.xpath("//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/textViewCenter\"]");
    
    private By CEDIS_NoDiferenteCantidad = By.xpath("//android.widget.Button[@resource-id=\"android:id/button2\"]");
    
    private By CEDIS_SiDiferenteCantidad = By.xpath("//android.widget.Button[@resource-id=\"android:id/button1\"]");
    
    private By CEDIS_ContinuarVerificacion = By.xpath("//android.widget.Button[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/buttonFinishVerify\" and @text=\"CONTINUAR\"]");
      
    private By CEDIS_LeyendaDanadosVerificacion = By.xpath("//android.widget.TextView[@resource-id=\"android:id/message\"]");

   private By CEDIS_NoLeyendaDanadosVerificacion = By.xpath("//android.widget.Button[@resource-id=\"android:id/button2\" and @text=\"NO\"]");
     
    private By CEDIS_SiLeyendaDanadosVerificacion = By.xpath("//android.widget.Button[@resource-id=\"android:id/button1\" and @text=\"SI\"]");
    
    private By CEDIS_LeyendaCaducosVerificacion = By.xpath("//android.widget.TextView[@resource-id=\"android:id/message\" and @text=\"¿Hay artículos Caducos en este Pedido?\"]");
        
    private By CEDIS_NoLeyendaCaducosVerificacion = By.xpath("//android.widget.Button[@resource-id=\"android:id/button2\" and @text=\"NO\"]");
 
    private By CEDIS_SiLeyendaCaducosVerificacion = By.xpath("//android.widget.Button[@resource-id=\"android:id/button1\" and @text=\"SI\"]");
    
    private By CEDIS_LeyendaVerificacionExitosa = By.xpath("//android.widget.TextView[@resource-id=\"android:id/message\" and @text=\"El proceso de Verificación fue exitoso\"]");
    
    private By CEDIS_AceptarVerificacionExitosa = By.xpath("//android.widget.Button[@resource-id=\"android:id/button1\" and @text=\"ACEPTAR\"]");
    
    private By CEDIS_Bulto549645 = By.xpath("//android.widget.Button[@text=\"549645\"]");
    private By CEDIS_Bulto549652 = By.xpath("//android.widget.Button[@text=\"549652\"]");
    
    private By CEDIS_BotonRegresar= By.xpath("//android.widget.Button[@resource-id=\"android:id/button1\" and @text=\"REGRESAR\"]");
    private By CEDIS_BotonContinuar= By.xpath("//android.widget.Button[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/buttonFinishVerifyResume\" and @text=\"CONTINUAR\"]");
    
    //List<String> aumentar = new ArrayList<>();

    
    
    public C02_Verificacion_De_Pedido_Y_Permisos(TestCase testCase, AppiumUtil au, BaseExecution baseExecution) {
		this.testCase = testCase;
		this.au = au;
		this.baseExecution = baseExecution;
	}
    
   // public void necesitaPermisos()
   // {
   // 	BUENO
   // 	boolean botonAceptar = au.driver.findElement(CEDIS_BotonAceptar).isDisplayed();
   // 	if (botonAceptar = true) {
	//		au.click(CEDIS_BotonAceptar);
	//   	au.click(CEDIS_btnMientrasAppenUso);
	//    	au.click(CEDIS_btnPermitir);	
	    	
	  
	 //       }
    public void necesitaPermisos() //IBARRA
    {
    	au.driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
    	//boolean botonAceptar = au.driver.findElement(CEDIS_BotonAceptar).isDisplayed();
    	//if (botonAceptar = true) {
        if (au.driver.findElement(CEDIS_btnMientrasAppenUso).isDisplayed()) {
			au.click(CEDIS_btnMientrasAppenUso);	    	
	    	au.click(CEDIS_btnPermitir);	  
	        }
    	else {
    		System.out.println("No se muestra las opciones de Necesita permisos");
    	}
	        
		} 	
   
    public void necesitaPermisostablet() //IBARRA
    {
    	
	  	au.click(CEDIS_btnPermitir);	
	  
  }
    
    //public void barraProceso() // BUENO
    //{
    	// int intentos = 0;
    //	boolean barra = au.driver.findElement(CEDIS_ProgresoBarra).isDisplayed();
    //	while (barra = true ) {
    	//	try {
          //      Thread.sleep(9000); // Tiempo de espera para que cargue la pantalla
        //    } catch (InterruptedException e) {
        //        e.printStackTrace();
        //    }
    	//	intentos++;
    	//	if (intentos > 12) {
    	//		System.out.println("La pantalla sigue cargando la barra de procesos");
    	//		break;
    	//		}   		
   // 	}    	
    
    public void barraProceso()  throws InterruptedException//Metodo mejorado
    {
    	au.driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(1));
    	int intentos = 0;
    	boolean barra = true;
    		while (barra == true ) {
       		 try {
      			 WebElement element = au.driver.findElement(CEDIS_ProgresoBarra);
      			 if (element.isDisplayed()) {
      				Thread.sleep(4000);
      				intentos++;}
         	}catch (NoSuchElementException  e){
         		System.out.println("La pantalla ya no muestra la barra de carga");
         		break;
         	}
      		 
      		 if (intentos>40) {
      			baseExecution.addStep("Se falla el caso");
      			Assert.fail("Se ha superado los 80 segundos de tolerancia", null);      			
      		 }
      		 }   

    		}
        
   
    public void clickBultos()
    {
    	au.scrollToElement(CEDIS_BotonBultos);
    	au.click(CEDIS_BotonBultos);
    	au.click(CEDIS_BultoSeleccionable);    	 
    	 
   }
    
    public void clickBultosTablet() throws InterruptedException
    {
    	au.click(CEDIS_BotonBultos);
    	au.click(CEDIS_BultoSeleccionable);    	 
    	 
   }
   
    public void tomarFoto() throws InterruptedException
    {
			Thread.sleep(2000);
    	au.click(CEDIS_FotoArticulo);
			Thread.sleep(2000);
    	au.click(CEDIS_IconoFotografia);
			Thread.sleep(2000);
    	au.click(CEDIS_CheckFotoVerificacionPaloma);
			Thread.sleep(2000);
    	//au.driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
    	au.click(CEDIS_MensajeConfirmarFotoVerificacionOpcionSi); 
			Thread.sleep(2000);
   }
    
    public void CapturarFotografiaBultoOpcionNo() throws InterruptedException {
    	au.click(CEDIS_FotoArticulo);
		au.click(CEDIS_IconoFotografia);
		Thread.sleep(2000);
		au.click(CEDIS_FotoArticulo);
		Thread.sleep(3000);
		au.click(CEDIS_CheckFotoVerificacionPaloma);
		au.click(CEDIS_MensajeConfirmarFotoVerificacionOpcionNo);
	}
    
    public void verificarArticulos()//
    {
    	au.click(CEDIS_CheckPorVerificar);
    	//au.scrollToElement(CEDIS_ContinuarVerificacion); 
    	String textocantidad = au.driver.findElement(CEDIS_CantidadArticulo).getText();
    	String valor = textocantidad.substring(9);
		System.out.println(valor);
		int aumentar = Integer.parseInt(valor);
		
    	for (int i = 0; i < aumentar; i++) {
    	  		au.click(CEDIS_AumentarArticulo);//Se agrega el artículo
    	}
    	
    	try {
    	int n= 2;	
    	for (int i2 = 0; i2 < 10; i2++)	{
    	
    		boolean validacion;
    		try {
    		 validacion= au.driver.findElement(CEDIS_CheckPorVerificar).isDisplayed();
    		
    	}catch (NoSuchElementException  e) {
    		validacion= false; 
    		System.out.println("No se ha encontrado otra casilla por verificadr, se continua el flujo ");
    		break; 
    	}
    		if (validacion==true ) {
    	    	au.click(CEDIS_CheckPorVerificar);

    		}else 
    		{
    			break;
    		}
    	String sn= Integer.toString(n);	
    	By CEDIS_CantidadArticulo2 = By.xpath("(//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/tv_qty_required\"]) ["+sn+"]");
    	String textocantidad2 = au.driver.findElement(CEDIS_CantidadArticulo2).getText();
    	String valor2 = textocantidad2.substring(9);
		System.out.println(valor2);
		int aumentar2 = Integer.parseInt(valor2);
		
    	for (int i = 0; i < aumentar2; i++) {
    	  		au.click(CEDIS_AumentarArticulo2);//Se agrega el artículo
    	}
    	n++;
    	}
    	} catch (NoSuchElementException  e){
    		System.out.println("No se ha encontrado otra cantidad de articulo, se continua el flujo ");
    	}
    }
    
    public void continuarSinDanCad()
    {
    	au.scrollToElement(CEDIS_ContinuarVerificacion);
    	au.scrollToElement(CEDIS_ContinuarVerificacion);
    	au.click(CEDIS_ContinuarVerificacion);
    	au.click(CEDIS_NoLeyendaDanadosVerificacion);    	 
    	au.click(CEDIS_NoLeyendaCaducosVerificacion);
    	au.click(CEDIS_AceptarVerificacionExitosa);  
   
   }
    public void continuarDanados() throws InterruptedException //Se crea metodo para posicionarse en la pantalla de dañados 14/08/2024 12:00pm
        {
        	au.scrollToElement(CEDIS_ContinuarVerificacion);
        	au.scrollToElement(CEDIS_ContinuarVerificacion);
        	au.click(CEDIS_ContinuarVerificacion);

        	// Se agrega validación de que muestre el botón de Regresar  
        	boolean validacion;
     		try {
     			validacion= au.driver.findElement(CEDIS_BotonRegresar).isDisplayed();
     		
     	   }catch (NoSuchElementException  e) {
     		validacion= false; 
     		System.out.println("No se ha encontrado el boton Regresar");

     	   }
     		if (validacion==true ) {
     			
     			au.click(CEDIS_BotonRegresar);
 
     		}else 
     		{
     			Assert.fail("No se ha encontrado el boton Regresar");
     		}
        	// hasta este punto es donde se agrego validación, las demas acciónes continuan como se tenia previamente
     		     		
        	au.click(CEDIS_SiLeyendaDanadosVerificacion);
        	au.click(CEDIS_AceptarVerificacionExitosa);
        }
        
    public void continuarCaducosDirecto() throws InterruptedException //Se crea metodo para posicionarse en la pantalla de caducos 14/08/2024 12:00pm
        {
        	au.scrollToElement(CEDIS_ContinuarVerificacion);
        	au.scrollToElement(CEDIS_ContinuarVerificacion);
    		Thread.sleep(1000);
        	au.click(CEDIS_ContinuarVerificacion);
    		Thread.sleep(1000);
        	au.click(CEDIS_NoLeyendaDanadosVerificacion);
    		Thread.sleep(1000);
        	au.click(CEDIS_SiLeyendaCaducosVerificacion);
    		Thread.sleep(1000);
        	au.click(CEDIS_AceptarVerificacionExitosa);
        }

    public void Cancelar_Fotografia_Bulto_a_Verificar() throws InterruptedException {
    	au.click(CEDIS_FotoArticulo); 
		au.click(CEDIS_IconoFotografia);
		Thread.sleep(2000);
		au.click(CEDIS_CheckFotoVerificacionCancelarFotoX);
	}
    

    public void VerificarArticuloCheck() {
    	au.click(CEDIS_CheckPorVerificar);
    }
    
    public void VerificadoArticuloCheck() {
    	au.click(CEDIS_CheckVerificado);
    }
    
    public void AumentarYDisminuirCantidades(Object increaseButton, Object decreaseButton) {
    	 ((AppiumUtil) increaseButton).click(CEDIS_AumentarArticulo);
		((AppiumUtil) decreaseButton).click(CEDIS_DisminuirArticulo);
    }
    
    public void AlertaArticulosPendientesVerificar () {
        // Identificadores de elementos mensaje alerta
        String expectedMessage = "Se tienen articulos pendientes de verificar";
        String messageElementId = "CEDIS_LeyendaArticuloPendiente"; // ID del mensaje
        String acceptButtonId = "CEDIS_AceptarLeyendaArticuloPendiente"; // ID del botón Aceptar
        // Clic boton aceptar
        au.click(CEDIS_AceptarLeyendaArticuloPendiente);
    }

    
	private boolean elementExists(By cEDIS_ProgresoBarra2, int i) {
		// TODO Auto-generated method stub
		return false;
	}
	
	public void ClickBtnContinuar () {
        // Identificadores de elementos mensaje alerta
       
        boolean validacion;
		try {
			au.scrollToElement(CEDIS_BotonContinuar);
			validacion= au.driver.findElement(CEDIS_BotonContinuar).isDisplayed();
		
	}catch (NoSuchElementException  e) {
		validacion= false; 
		System.out.println("No se ha encontrado el boton Continuar");

	}
		if (validacion==true ) {
			
			au.click(CEDIS_BotonContinuar);
			au.click(CEDIS_SiLeyendaDanadosVerificacion);
		}else 
		{
			Assert.fail("No se ha encontrado el boton Continuar");
		}
 
    }
	
	
	
	//Metodos creados por marcelo
	public void darClickBotonBultos() throws InterruptedException {
	    //Marcelo Gtz
	    //Método para hacer scroll hasta el boton de Continuar y luego hacer click sobre el boton de Bultos y ahi termina
	    System.out.println("Iniciando el click en el boton llamado Bultos");
	    Thread.sleep(300);
	    au.scrollToElement(CEDIS_BotonEscanear);
	    Thread.sleep(500);
	    au.click(CEDIS_BotonBultos);
	    Thread.sleep(500);
	}

	public void darClickPrimerBulto() throws InterruptedException {
	    //Marcelo Gtz
	    //Método para dar clic en el primer bulto 549645 y ahi termina
	    System.out.println("Iniciando el click en el bulto 549645");
	    Thread.sleep(300);
	    au.click(CEDIS_Bulto549645);
	    Thread.sleep(500);
	}

	public void darClickSegundoBulto() throws InterruptedException {
	    //Marcelo Gtz
	    //Método para dar clic en el segundo bulto 549652 y ahi termina
	    System.out.println("Iniciando el click en el bulto 549652");
	    Thread.sleep(300);
	    au.click(CEDIS_Bulto549652);
	    Thread.sleep(500);
	}

	//Metodo Compartido por Marcelo se actualiza 20/08/2024
	public void verificarArticulo() throws InterruptedException, IOException {
		au.scrollToElement(CEDIS_BotonAumentarTercerArticulo);
	    // Encuentra el elemento padre (RecyclerView)
	    WebElement parentElement = au.driver.findElement(By.xpath("//androidx.recyclerview.widget.RecyclerView[@resource-id='com.femco.oxxo.operaciones.mitienda.debug:id/rvProductByPackage']"));

	    // Encuentra todos los hijos del elemento padre (cada artículo)
	    List<WebElement> childElements = parentElement.findElements(By.xpath(".//android.view.ViewGroup[@resource-id='com.femco.oxxo.operaciones.mitienda.debug:id/containerViewItem']"));

	    // Imprime el número de artículos encontrados
	    int numeroDeHijos = childElements.size();
	    System.out.println("Número de artículos: " + numeroDeHijos);

	    // Iterar sobre cada artículo
	    for (int i = 0; i < numeroDeHijos; i++) {
	       // Crear un By para identificar el artículo actual
	       By articuloBy = By.xpath(".//android.view.ViewGroup[@resource-id='com.femco.oxxo.operaciones.mitienda.debug:id/containerViewItem'][" + (i + 1) + "]");

	       // Encontrar el artículo después de desplazar
	       WebElement articulo = au.driver.findElement(articuloBy);

	       // Clic en el CheckBox para seleccionar el artículo relativo al articulo actual
	       WebElement checkBox = articulo.findElement(By.xpath(".//android.widget.CheckBox[@resource-id='com.femco.oxxo.operaciones.mitienda.debug:id/checkBoxItemsVerify']"));
	       checkBox.click();

	       // Obtener la cantidad ingresada en el artículo relativo al articulo actual
	       WebElement cantidadIngresadaElement = articulo.findElement(By.xpath(".//android.widget.TextView[@resource-id='com.femco.oxxo.operaciones.mitienda.debug:id/textViewItemsVerify' and contains(@text, '')]"));

	       // Extraer el texto que contiene la cantidad ingresada (por ejemplo, "0")
	       String cantidadIngresadaTexto = cantidadIngresadaElement.getText();
	       int cantidadIngresada = Integer.parseInt(cantidadIngresadaTexto);
	       System.out.println("Cantidad ingresada artículo número #" + (i + 1) + ": " + cantidadIngresada);

	       // Obtener la cantidad esperada para ese artículo relativo al articulo actual
	       WebElement cantidadEsperadaElement = articulo.findElement(By.xpath(".//android.widget.TextView[@resource-id='com.femco.oxxo.operaciones.mitienda.debug:id/tv_qty_required' and contains(@text, 'CANTIDAD')]"));
	       String cantidadEsperadaTexto = cantidadEsperadaElement.getText();
	       String[] partes = cantidadEsperadaTexto.split(" ");
	       int cantidadEsperada = Integer.parseInt(partes[partes.length - 1]);
	       System.out.println("Cantidad esperada artículo número #" + (i + 1) + ": " + cantidadEsperada);

	       // Comparar y ajustar la cantidad
	       if (cantidadIngresada < cantidadEsperada) {
	          // Aumentar la cantidad hasta que coincida con la esperada
	          for (int j = cantidadIngresada; j < cantidadEsperada; j++) {
	             // Construir el XPath relativo al articulo actual para el botón de aumentar
	             WebElement botonAumentar = articulo.findElement(By.xpath(".//android.widget.ImageView[@resource-id='com.femco.oxxo.operaciones.mitienda.debug:id/imagePlusItems']"));
	             botonAumentar.click();
	             Thread.sleep(300);  // Añadir un pequeño retraso para simular la acción del usuario
	          }
	       } else if (cantidadIngresada > cantidadEsperada) {
	          // Disminuir la cantidad hasta que coincida con la esperada
	          for (int j = cantidadIngresada; j > cantidadEsperada; j--) {
	             // Construir el XPath relativo al articulo actual para el botón de disminuir
	             WebElement botonDisminuir = articulo.findElement(By.xpath(".//android.widget.ImageView[@resource-id='com.femco.oxxo.operaciones.mitienda.debug:id/imageMinusItems']"));
	             botonDisminuir.click();
	             Thread.sleep(300);  // Añadir un pequeño retraso para simular la acción del usuario
	          }
	       }

	       // Verificar que la cantidad está correcta
	       System.out.println("Artículo " + (i + 1) + ": Cantidad ajustada correctamente a " + cantidadEsperada);
	    }

	    System.out.println("Proceso de verificación y ajuste completado.");
	}

	public void tomarFotoTablet() //metodo creado ricardo 30/08/2024
	{
		au.click(CEDIS_FotoArticulo);
		au.click(CEDIS_IconoFotografia);
		au.click(CEDIS_CheckFotoVerificacionPaloma);
		au.driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
		au.click(CEDIS_MensajeConfirmarFotoVerificacionOpcionSi);
	}
	
	public void tomarFotoTabletv2() throws InterruptedException { // método creado por Ricardo 30/08/2024
	    boolean fotoConfirmada = false;
	    while (!fotoConfirmada) {
	        // Iniciamos el proceso de foto
	        au.click(CEDIS_FotoArticulo);
	        Thread.sleep(3000);
	        au.click(CEDIS_IconoFotografia);
	        Thread.sleep(3000);
	        au.click(CEDIS_CheckFotoVerificacionPaloma);
	        Thread.sleep(3000);
	        au.driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
	        Thread.sleep(3000);
	        
	        try {
	            // Intentamos confirmar la foto dando click en "Sí"
	            au.click(CEDIS_MensajeConfirmarFotoVerificacionOpcionSi);
	            Thread.sleep(3000);
	            fotoConfirmada = true; // ¡Éxito! La foto se confirmó
	        } catch (Exception e) {
	            // Si no se pudo dar click en "Sí", damos click en "No" y reintentamos
	            au.click(CEDIS_MensajeConfirmarFotoVerificacionOpcionNo);
	            Thread.sleep(3000);
	            System.out.println("Error al confirmar foto, reintentando... (la tecnología a veces nos hace pruebas)");
	        }
	    }
	}


	//Metodo Compartido por Ricardo seactualiza 30/08/2024
	public void verificarArticuloTablet() throws InterruptedException {
		au.scrollToElement(CEDIS_BotonBultos);
		// Encuentra el elemento padre (RecyclerView)
		WebElement parentElement = au.driver.findElement(By.xpath("//androidx.recyclerview.widget.RecyclerView[@resource-id='com.femco.oxxo.operaciones.mitienda.debug:id/rvProductByPackage']"));

		// Encuentra todos loshijos del elemento padre (cada artículo)
		List<WebElement> childElements = parentElement.findElements(By.xpath(".//android.view.ViewGroup[@resource-id='com.femco.oxxo.operaciones.mitienda.debug:id/containerViewItem']"));

		// Imprime el número deartículos encontrados
		int numeroDeHijos = childElements.size();
		System.out.println("Número de artículos: " + numeroDeHijos);

		// Iterar sobre cada artículo
		for (int i = 0; i < numeroDeHijos; i++) {
			// Crear un By paraidentificar el artículo actual
			By articuloBy = By.xpath(".//android.view.ViewGroup[@resource-id='com.femco.oxxo.operaciones.mitienda.debug:id/containerViewItem'][" + (i + 1) + "]");

			Thread.sleep(500); // Añadirun pequeño retraso para asegurar que el desplazamiento se complete

			// Encontrar el artículo despuésde desplazar
			WebElement articulo = au.driver.findElement(articuloBy);

			// Clic en el CheckBox paraseleccionar el artículo relativo al articulo actual
			WebElement checkBox = articulo.findElement(By.xpath(".//android.widget.CheckBox[@resource-id='com.femco.oxxo.operaciones.mitienda.debug:id/checkBoxItemsVerify']"));
			checkBox.click();
			Thread.sleep(500); // Añadirun pequeño retraso para simular la accióndel usuario

			// Obtener la cantidadingresada en el artículo relativo al articulo actual
			WebElement cantidadIngresadaElement = articulo.findElement(By.xpath(".//android.widget.TextView[@resource-id='com.femco.oxxo.operaciones.mitienda.debug:id/textViewItemsVerify' and contains(@text, '')]"));

			// Extraer el texto quecontiene la cantidad ingresada (por ejemplo, "0")
			String cantidadIngresadaTexto = cantidadIngresadaElement.getText();
			int cantidadIngresada = Integer.parseInt(cantidadIngresadaTexto);
			System.out.println("Cantidad ingresada artículo número #" + (i + 1) + ": " + cantidadIngresada);

			// Obtener la cantidadesperada para ese artículo relativo alarticulo actual
			WebElement cantidadEsperadaElement = articulo.findElement(By.xpath(".//android.widget.TextView[@resource-id='com.femco.oxxo.operaciones.mitienda.debug:id/tv_qty_required' and contains(@text, 'CANTIDAD')]"));
			String cantidadEsperadaTexto = cantidadEsperadaElement.getText();
			String[] partes = cantidadEsperadaTexto.split(" ");
			int cantidadEsperada = Integer.parseInt(partes[partes.length - 1]);
			System.out.println("Cantidad esperada artículo número #" + (i + 1) + ": " + cantidadEsperada);

			// Comparar y ajustar lacantidad
			if (cantidadIngresada < cantidadEsperada) {
				// Aumentar la cantidadhasta que coincida con la esperada
				for (int j = cantidadIngresada; j < cantidadEsperada; j++) {
					// Construir el XPath relativoal articulo actual para el botón de aumentar
					WebElement botonAumentar = articulo.findElement(By.xpath(".//android.widget.ImageView[@resource-id='com.femco.oxxo.operaciones.mitienda.debug:id/imagePlusItems']"));
					botonAumentar.click();
					Thread.sleep(500);  // Añadir un pequeño retrasopara simular la acción del usuario
				}
			} else if (cantidadIngresada > cantidadEsperada) {
				// Disminuir la cantidadhasta que coincida con la esperada
				for (int j = cantidadIngresada; j > cantidadEsperada; j--) {
					// Construir el XPath relativoal articulo actual para el botón de disminuir
					WebElement botonDisminuir = articulo.findElement(By.xpath(".//android.widget.ImageView[@resource-id='com.femco.oxxo.operaciones.mitienda.debug:id/imageMinusItems']"));
					botonDisminuir.click();
					Thread.sleep(500);  // Añadir un pequeño retrasopara simular la acción del usuario
				}
			}

			// Verificar que lacantidad está correcta
			System.out.println("Artículo " + (i + 1) + ": Cantidad ajustada correctamente a " + cantidadEsperada);
		}

		System.out.println("Proceso de verificación y ajuste completado.");
	}
	public void continuarDanadosTablet() throws InterruptedException //Se crea metodo para posicionarseen la pantalla de dañados 30/08/2024 12:00pm
	{

		au.click(CEDIS_ContinuarVerificacion);
		Thread.sleep(1000);
		au.click(CEDIS_SiLeyendaDanadosVerificacion);
		au.click(CEDIS_AceptarVerificacionExitosa);
	}

	public void continuarDanadosHH() throws InterruptedException //Se crea metodo para posicionarseen la pantalla de dañados 30/08/2024 12:00pm
	{
		//Marcelo, Método para terminar de verificar y pasar a "Dañados"
		au.scrollToElement(CEDIS_ContinuarVerificacion);
		Thread.sleep(1000);
		au.click(CEDIS_ContinuarVerificacion);
		Thread.sleep(1000);
		au.click(CEDIS_SiLeyendaDanadosVerificacion);
		Thread.sleep(1000);
		au.click(CEDIS_AceptarVerificacionExitosa);
	}
	
	public void hacerClickEnElPrimerBultoDelPadre() throws InterruptedException {
		//Método para hacer click en el primer bulto listado, en la ventana: "Seleccionar Bulto". Creado por: Marcelo Gtz
	    // Mensaje de inicio del método
	    System.out.println("Iniciando el método: hacerClickEnElPrimerBultoDelPadre_Marcelo");

	    // Localizar el elemento padre
	    System.out.println("Buscando el elemento padre que contiene los bultos...");
	    Thread.sleep(1000);
	    WebElement elementoPadre = au.driver.findElement(AppiumBy.xpath("//android.widget.ListView[@resource-id='android:id/select_dialog_listview']"));
	    System.out.println("Elemento padre encontrado.");

	    // Encontrar los hijos dentro del padre
	    System.out.println("Obteniendo la lista de hijos (bultos) del elemento padre...");
	    Thread.sleep(1000);
	    List<WebElement> bultos = elementoPadre.findElements(AppiumBy.xpath(".//android.widget.Button"));
	    System.out.println("Número de bultos encontrados: " + bultos.size());

	    // Validar si hay al menos un bulto disponible
	    if (bultos.isEmpty()) {
	        System.out.println("No se encontraron bultos dentro del elemento padre. Finalizando el método.");
	        return;
	    }

	    // Realizar clic en el primer bulto
	    System.out.println("Preparándose para hacer clic en el primer bulto...");
	    Thread.sleep(1000);
	    bultos.get(0).click();
	    Thread.sleep(1000);
	    System.out.println("Clic en el primer bulto realizado con éxito.");

	    // Mensaje de finalización del método
	    System.out.println("Finalización del método: hacerClickEnElPrimerBultoDelPadre_Marcelo");
	}
	
	public void hacerClickEnElSegundoBultoDelPadre() throws InterruptedException {
		//Método para hacer click en el segundo bulto listado, en la ventana: "Seleccionar Bulto". Creado por: Marcelo Gtz
	    // Mensaje de inicio del método
	    System.out.println("Iniciando el método: hacerClickEnElSegundoBultoDelPadre_Marcelo");

	    // Localizar el elemento padre
	    System.out.println("Buscando el elemento padre que contiene los bultos...");
	    Thread.sleep(1000);
	    WebElement elementoPadre = au.driver.findElement(AppiumBy.xpath("//android.widget.ListView[@resource-id='android:id/select_dialog_listview']"));
	    System.out.println("Elemento padre encontrado.");

	    // Encontrar los hijos dentro del padre
	    System.out.println("Obteniendo la lista de hijos (bultos) del elemento padre...");
	    Thread.sleep(1000);
	    List<WebElement> bultos = elementoPadre.findElements(AppiumBy.xpath(".//android.widget.Button"));
	    System.out.println("Número de bultos encontrados: " + bultos.size());

	    // Validar si hay al menos dos bultos disponibles
	    if (bultos.size() < 2) {
	        System.out.println("No se encontraron suficientes bultos dentro del elemento padre. Finalizando el método.");
	        return;
	    }

	    // Realizar clic en el segundo bulto
	    System.out.println("Preparándose para hacer clic en el segundo bulto...");
	    Thread.sleep(1000);
	    bultos.get(1).click();
	    Thread.sleep(1000);
	    System.out.println("Clic en el segundo bulto realizado con éxito.");

	    // Mensaje de finalización del método
	    System.out.println("Finalización del método: hacerClickEnElSegundoBultoDelPadre_Marcelo");
	}
	
	public void hacerClickEnBultoDelPadre(int indiceBulto) throws InterruptedException {
	    // Descripción: Método para hacer clic en un bulto específico dentro de la lista.
	    // Nota: El índice comienza en 1 en lugar de 0. Por ejemplo: C02_Verificacion.hacerClickEnBultoDelPadre(1);
		// Autor: Marcelo Gtz
	    
	    Thread.sleep(1000); // Espera para permitir la carga de los elementos
	    
	    // Localizar el elemento padre que contiene la lista de bultos
	    WebElement elementoPadre = au.driver.findElement(AppiumBy.xpath("//android.widget.ListView[@resource-id='android:id/select_dialog_listview']"));
	    
	    // Obtener la lista de bultos dentro del elemento padre
	    List<WebElement> bultos = elementoPadre.findElements(AppiumBy.xpath(".//android.widget.Button"));

	    // Ajustar el índice para que inicie en 1 (se resta 1 para que coincida con la lista basada en 0)
	    int indiceAjustado = indiceBulto - 1;

	    // Validar si el índice es válido
	    if (indiceAjustado < 0 || indiceAjustado >= bultos.size()) {
	        return; // Sale del método si el índice es inválido
	    }

	    // Hacer clic en el bulto correspondiente
	    Thread.sleep(1000);
	    bultos.get(indiceAjustado).click();
	    Thread.sleep(1000);
	}


	
	
	// Autor: Marcelo Gtz
	// Método Para esperar la pantalla de carga de verificación.
	public void EsperaPantallaVerificacion(int milisegundos) throws InterruptedException {
	    System.out.println("Iniciando método: EsperaPantallaVerificacion");
	    System.out.println("Iniciando espera de " + (milisegundos / 1000) + " segundos...");

	    int segundos = milisegundos / 1000;
	    for (int i = 1; i <= segundos; i++) {
	        Thread.sleep(1000);
	        System.out.println("Han pasado " + i + " segundos...");
	    }

	    System.out.println("Espera finalizada.");
	    System.out.println("Finalizando método: EsperaPantallaVerificacion");
	}
	
	
	
	// Autor: Marcelo Gtz
	// Método para esperar la desaparición del loader cuando el elemento ya no está presente en el DOM.
	public void esperaDinamicaLoaderDeVerificacion(int tiempoMaximoMilisegundos) {
	    System.out.println("Iniciando el método: esperaDinamicaLoaderDeVerificacion");
	    System.out.println("Esperando hasta " + (tiempoMaximoMilisegundos / 1000) + " segundos para que desaparezca el loader de verificación...");

	    // Convertir el tiempo máximo en segundos para WebDriverWait
	    int tiempoMaximoSegundos = tiempoMaximoMilisegundos / 1000;

	    // Crear un WebDriverWait
	    WebDriverWait wait = new WebDriverWait(au.driver, Duration.ofSeconds(tiempoMaximoSegundos));

	    try {
	        // Usar WebDriverWait para esperar hasta que el loader ya no esté presente en el DOM
	        wait.until(driver -> {
	            try {
	                // Intentar encontrar el loader
	                WebElement loader = au.driver.findElement(AppiumBy.id("com.femco.oxxo.operaciones.mitienda.debug:id/progressBar"));
	                System.out.println("Loader aún visible... esperando.");
	                return false; // El loader aún está presente, no se cumple la condición
	            } catch (NoSuchElementException e) {
	                // Si el elemento no es encontrado, significa que ya no está en el DOM
	                System.out.println("Loader ha desaparecido del DOM.");
	                return true; // El loader ya no está en el DOM, se cumple la condición
	            }
	        });

	        System.out.println("Loader desapareció exitosamente.");
	        
	        // Asegúrate de que el flujo siga después de que el loader desaparezca
	        // Llamar al siguiente método o acción aquí, por ejemplo:
	        // siguienteMetodo();

	    } catch (TimeoutException e) {
	        // Este mensaje se imprime si el loader no desaparece dentro del tiempo especificado
	        System.out.println("Error: El loader no desapareció dentro del tiempo especificado de " + (tiempoMaximoMilisegundos / 1000) + " segundos.");
	    }

	    System.out.println("Finalizando el método: esperaDinamicaLoaderDeVerificacion");
	}
	
	//Autor: Jorge Alberto Lugo Martinez 06/02/2025
	//Metodo para validar la información del encabezado
	public void validarEncabezadoRutasVerificacion() throws InterruptedException 
	{
    	if(au.driver.findElement(CEDIS_LogoOxxoVerificacion).isEnabled()==false) {
    		baseExecution.addStep("Se falla el caso");        
    		Assert.fail("No se ha encontrado el Logo de Oxxo de la pantalla de VERIFICACIÓN DE MERCANCÍA CEDIS en la parte de inicio", null);
    	}    	
    	if (au.driver.findElements(CEDIS_DetalleFechaVerificacion).isEmpty()!= false) {    		
    		baseExecution.addStep("Se falla el caso");        
    		Assert.fail("No se ha encontrado el dato de la Fecha de la pantalla de VERIFICACIÓN DE MERCANCÍA CEDIS en la parte de inicio", null);
    	}    	
    	if(au.driver.findElements(CEDIS_FechaVerificacion).isEmpty()!= false) {
    		baseExecution.addStep("Se falla el caso");        
    		Assert.fail("No se ha encontrado el campo de Fecha de la pantalla de VERIFICACIÓN DE MERCANCÍA CEDIS en la parte de inicio", null);
    	}
    	if(au.driver.findElements(CEDIS_RutaVerificacion).isEmpty()!= false) {
    		baseExecution.addStep("Se falla el caso");        
    		Assert.fail("No se ha encontrado el dato de la Ruta de la pantalla de VERIFICACIÓN DE MERCANCÍA CEDIS en la parte de inicio", null);
    	}
    	if(au.driver.findElements(CEDIS_RutaDinamicaVerificacion).isEmpty()!= false) {
    		baseExecution.addStep("Se falla el caso");        
    		Assert.fail("No se ha encontrado el dato de la Ruta Dinamica de la pantalla de VERIFICACIÓN DE MERCANCÍA CEDIS en la parte de inicio", null);
    	}
    	if(au.driver.findElements(CEDIS_CRVerificacion).isEmpty()!= false) {
    		baseExecution.addStep("Se falla el caso");        
    		Assert.fail("No se ha encontrado el dato del CR de Tienda de la pantalla de VERIFICACIÓN DE MERCANCÍA CEDIS en la parte de inicio", null);
    	}
    	if(au.driver.findElements(CEDIS_NombreTiendaVerificacion).isEmpty()!= false) {
    		baseExecution.addStep("Se falla el caso");        
    		Assert.fail("No se ha encontrado el dato del nombre de la Tienda de la pantalla de VERIFICACIÓN DE MERCANCÍA CEDIS en la parte de inicio", null);
    	}
    	if(au.driver.findElements(CEDIS_NumeroDeTransferencia).isEmpty()!= false) {
    		baseExecution.addStep("Se falla el caso");        
    		Assert.fail("No se ha encontrado el dato del número de la transferencia de la pantalla de VERIFICACIÓN DE MERCANCÍA CEDIS en la parte de inicio", null);
    	}
	}	
	//Metodo Compartido por Ricardo se actualiza 11/02/2025
	
	public void VerificarArticulos() throws InterruptedException, IOException {
		
		Runtime.getRuntime().exec("adb shell input swipe 20 1003 20 900 45");//
		// Encuentra el elemento padre (RecyclerView)
	    WebElement parentElement = au.driver.findElement(By.xpath("//androidx.recyclerview.widget.RecyclerView[@resource-id='com.femco.oxxo.operaciones.mitienda.debug:id/rvProductByPackage']"));

	    // Encuentra todos los hijos del elemento padre (cada artículo)
	    List<WebElement> childElements = parentElement.findElements(By.xpath(".//android.view.ViewGroup[@resource-id='com.femco.oxxo.operaciones.mitienda.debug:id/containerViewItem']"));

	    // Imprime el número de artículos encontrados
	    int numeroDeHijos = childElements.size();
	    System.out.println("Número de artículos: " + numeroDeHijos);

	    // Iterar sobre cada artículo
	    for (int i = 0; i < numeroDeHijos; i++) {
	       // Crear un By para identificar el artículo actual
	       By articuloBy = By.xpath(".//android.view.ViewGroup[@resource-id='com.femco.oxxo.operaciones.mitienda.debug:id/containerViewItem'][" + (i + 1) + "]");

	       // Encontrar el artículo después de desplazar
	       WebElement articulo = au.driver.findElement(articuloBy);

	       // Clic en el CheckBox para seleccionar el artículo relativo al articulo actual
	       WebElement checkBox = articulo.findElement(By.xpath(".//android.widget.CheckBox[@resource-id='com.femco.oxxo.operaciones.mitienda.debug:id/checkBoxItemsVerify']"));
	       checkBox.click();
	       Thread.sleep(100); // Añadir un pequeño retraso para simular la acción del usuario

	       // Obtener la cantidad ingresada en el artículo relativo al articulo actual
	       WebElement cantidadIngresadaElement = articulo.findElement(By.xpath(".//android.widget.TextView[@resource-id='com.femco.oxxo.operaciones.mitienda.debug:id/textViewItemsVerify' and contains(@text, '')]"));

	       // Extraer el texto que contiene la cantidad ingresada (por ejemplo, "0")
	       String cantidadIngresadaTexto = cantidadIngresadaElement.getText();
	       int cantidadIngresada = Integer.parseInt(cantidadIngresadaTexto);
	       System.out.println("Cantidad ingresada artículo número #" + (i + 1) + ": " + cantidadIngresada);

	       // Obtener la cantidad esperada para ese artículo relativo al articulo actual
	       WebElement cantidadEsperadaElement = articulo.findElement(By.xpath(".//android.widget.TextView[@resource-id='com.femco.oxxo.operaciones.mitienda.debug:id/tv_qty_required' and contains(@text, 'CANTIDAD')]"));
	       String cantidadEsperadaTexto = cantidadEsperadaElement.getText();
	       String[] partes = cantidadEsperadaTexto.split(" ");
	       int cantidadEsperada = Integer.parseInt(partes[partes.length - 1]);
	       System.out.println("Cantidad esperada artículo número #" + (i + 1) + ": " + cantidadEsperada);

	       // Comparar y ajustar la cantidad
	       if (cantidadIngresada < cantidadEsperada) {
	          // Aumentar la cantidad hasta que coincida con la esperada
	          for (int j = cantidadIngresada; j < cantidadEsperada; j++) {
	             // Construir el XPath relativo al articulo actual para el botón de aumentar
	             WebElement botonAumentar = articulo.findElement(By.xpath(".//android.widget.ImageView[@resource-id='com.femco.oxxo.operaciones.mitienda.debug:id/imagePlusItems']"));
	             botonAumentar.click();
	             Thread.sleep(300);  // Añadir un pequeño retraso para simular la acción del usuario
	          }
	       } else if (cantidadIngresada > cantidadEsperada) {
	          // Disminuir la cantidad hasta que coincida con la esperada
	          for (int j = cantidadIngresada; j > cantidadEsperada; j--) {
	             // Construir el XPath relativo al articulo actual para el botón de disminuir
	             WebElement botonDisminuir = articulo.findElement(By.xpath(".//android.widget.ImageView[@resource-id='com.femco.oxxo.operaciones.mitienda.debug:id/imageMinusItems']"));
	             botonDisminuir.click();
	             Thread.sleep(300);  // Añadir un pequeño retraso para simular la acción del usuario
	          }
	       }

	       // Verificar que la cantidad está correcta
	       System.out.println("Artículo " + (i + 1) + ": Cantidad ajustada correctamente a " + cantidadEsperada);
	    }

	    System.out.println("Proceso de verificación y ajuste completado.");
	}


}     
