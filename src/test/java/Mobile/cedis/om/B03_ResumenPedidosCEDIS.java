package Mobile.cedis.om;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import exceptions.FrameworkException;
import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;
import modelo.BaseExecution;
import modelo.TestCase;
import utils.AppiumUtil;

/**
import java.awt.Component;
import java.awt.Container;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
*/

public class B03_ResumenPedidosCEDIS {
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
	
	//Sección RESUMEN ENTREGA DE PEDIDO
	private By CEDIS_ResumenEntregaPedido = By.xpath("//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/tvTitleResumeOrder\" and @text=\"RESUMEN ENTREGA DE PEDIDO\"]");
	private By CEDIS_CodigoContenedorResumen = By.xpath("//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/tvTitleContainerType\" and @text=\"CÓDIGO\r\n"
			+ "CONTENEDOR\"]");
	private By CEDIS_TotalPedidoResumen = By.xpath("//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/tvTitleTotalOrder\" and @text=\"TOTAL\r\n"
			+ "PEDIDO\"]");
	private By CEDIS_TotalEntregadoResumen = By.xpath("//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/tvTitleTotalDelivered\" and @text=\"TOTAL\r\n"
			+ "ENTREGADO\"]");
	private By CEDIS_ResumenCajaPedido = By.xpath("//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/tvContainerType\" and @text=\"Cajas\"]");
    private By CEDIS_ResumenSecosPedido = By.xpath("//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/tvContainerType\" and @text=\"Canastillas Secos\"]");
    private By CEDIS_ResumenRefrigeradosPedido = By.xpath("//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/tvContainerType\" and @text=\"Canastillas Refrigerados\"]");
    
    private By CEDIS_ResumenCajaPedidoTablet = By.xpath("(//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/tvContainerType\" and @text=\"Cajas\"])[1]");
    private By CEDIS_ResumenSecosPedidoTablet = By.xpath("(//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/tvContainerType\" and @text=\"Canastillas Secos\"])[1]");
    private By CEDIS_ResumenRefrigeradosPedidoTablet = By.xpath("(//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/tvContainerType\" and @text=\"Canastillas Refrigerados\"])[1]");
    
    private By CEDIS_ResumenTotalPedido = By.xpath("//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/tvContainerType\" and @text=\"Total Bultos\"]");
    //Los valores de los campos de total de pedido y Total de Entrega de bultos en Resumen de Entrega de Pedido se obtienen con los metodos TotalesBultosPedido y TotalesBultosEntregados respectivamente esto se realizo debido a que los campos de esta sección tienen los mismos atributos.
    
    //Sección ENTRADA DE CAJAS SIN ETIQUETAS/ETIQUETAS DAÑADAS
    private By CEDIS_EntradaCajasSinEtiqueta = By.xpath("//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/tvTitleWO\" and @text=\"ENTRADA DE CAJAS SIN ETIQUETAS/ETIQUETAS DAÑADAS\"]");//Leyenda de entrada de ENTRADA DE CAJAS SIN ETIQUETAS/ETIQUETAS DAÑADAS
    private By CEDIS_DescripcionCajasSinEtiqueta = By.xpath("//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/tvTitleWooType\" and  @text=\"DESCRIPCIÓN\"]");
    private By CEDIS_CodigoContenedorCajasSinEtiqueta = By.xpath("//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/tvTitleTotalDeliveredWoo\" and @text=\"CÓDIGO CONTENEDOR\"]");
    //private By CEDIS_DescripcionContenedorSinEtiqueta = By.xpath("com.femco.oxxo.operaciones.mitienda.debug:id/tvWOtype","text:=.*");
    //private By CEDIS_NumCajasSinEtiqueta = By.xpath("com.femco.oxxo.operaciones.mitienda.debug:id/tvWOcode","text:=.*");    
    private By CEDIS_TotalCajasSinEtiqueta = By.xpath("//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/tvWOtype\" and @text=\"Total\"]");
    
    //Sección DETALLE FALTANTES
    private By CEDIS_DetalleFaltante = By.xpath("//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/tvTitleFaltantes\" and @text=\"DETALLE FALTANTES\"]");
    private By CEDIS_TipoContenedorFaltante = By.xpath("//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/tvTitleContainerType\" and @text=\"TIPO\r\n"
    		+ "CONTENEDOR\"]");
    private By CEDIS_CodigoContenedorFaltante = By.xpath("c//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/tvTitleTotalOrder\" and @text=\"CÓDIGO\r\n"
    		+ "CONTENEDOR\"]");
    private By CEDIS_TotalFaltante = By.xpath("//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/tvTitleTotalDelivered\" and @text=\"TOTAL\"]");
    
    private By CEDIS_LeyendaFaltanteCaja = By.xpath("//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/tvContainerType\" and @text=\"Cajas\"]");
    private By CEDIS_LeyendaFaltanteSeco = By.xpath("//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/tvContainerType\" and @text=\"Canastillas Secos\"]");
    private By CEDIS_LeyendaFaltanteRefrigerado = By.xpath("//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/tvContainerType\" and @text=\"Canastillas Refrigerados\"]");
    
    private By CEDIS_LeyendaFaltanteCajaTablet = By.xpath("(//android.widget.TextView[@resource-id=\\\"com.femco.oxxo.operaciones.mitienda.debug:id/tvContainerType\\\" and @text=\\\"Cajas\\\"])[2]");
    private By CEDIS_LeyendaFaltanteSecoTablet = By.xpath("(//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/tvContainerType\" and @text=\"Canastillas Secos\"])[2]");
    private By CEDIS_LeyendaFaltanteRefrigeradoTablet = By.xpath("(//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/tvContainerType\" and @text=\"Canastillas Refrigerados\"])[2]");
    
    
    //Sección SOBRANTES
    private By CEDIS_Sobrantes = By.xpath("//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/tvTitleLeftoverDamaged\" and @text=\"SOBRANTES\"]");
    private By CEDIS_CodigoContenedorSobrantes = By.xpath("//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/tvTitleContainerType\" and @text=\"CÓDIGO CONTENEDOR\"]");
    //private By CEDIS_SkuSobrantes = By.xpath("com.femco.oxxo.operaciones.mitienda.debug:id/tvLeftoverType","text:=.*");
    private By CEDIS_SobrantesTotalSobrantes = By.xpath("//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/tvLeftoverType\" and @text=\"Total Sobrantes\"]");

    //Sección DAÑADOS
    private By CEDIS_Danados = By.xpath("//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/tvTitleDamaged\" and @text=\"DAÑADOS\"]");
    private By CEDIS_CódigoArticulosDanados = By.xpath("//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/tvTitleContainerType\" and @text=\"CÓDIGO ARTÍCULO\"]");
    private By CEDIS_LeyendaDanados = By.xpath("//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/tvTitleTotalOrder\" and @text=\"DAÑADOS\"]");
    private By CEDIS_TotalDanados = By.xpath("//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/tvDamagedType\" and @text=\"Total Dañados\"]");
    
    private By CEDIS_FirmaAcuerdo =By.id("com.femco.oxxo.operaciones.mitienda.debug:id/btnSignatureOrder");


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
	
    public B03_ResumenPedidosCEDIS(TestCase testCase, AppiumUtil au, BaseExecution baseExecution) {
        this.testCase = testCase;
        this.au = au;
        this.baseExecution = baseExecution;
    }
    
    /**
     * Método para validar encabezado
     *
     * Validar que todos los campos visibles en el encabezado de la pantalla "RECEPCIÓN DE PEDIDO CEDIS" estén correctamente cargados y visibles al iniciar la vista. 
     * Es una verificación crítica para asegurar que los datos clave están disponibles antes de continuar con el flujo de la prueba.
     *
     * @author Juan Ibarra
     * @date 
     * @throws FrameworkException
     */
    
    public void ValidacionEncabezado() throws IOException {
    	//Funcion que permite validar que se muestran los campo correspondientes al encabezado de la pantalla  Creado: Juan Ibarra
    	if(au.driver.findElement(CEDIS_TituloPantalla).isEnabled()==false) {
    		baseExecution.addStep("Se falla el caso");        
    		Assert.fail("No se ha encontrado el Titulo pantalla RECEPCIÓN DE PEDIDO CEDIS en la parte de inicio", null);
    	}    	
    	if (au.driver.findElements(CEDIS_Fecha).isEmpty()!= false) {    		
    		baseExecution.addStep("Se falla el caso");        
    		Assert.fail("No se ha encontrado el campo de Fecha de la pantalla de RECEPCIÓN DE PEDIDO CEDIS en la parte de inicio", null);
    	}    	
    	if(au.driver.findElements(CEDIS_ValorFecha).isEmpty()!= false) {
    		baseExecution.addStep("Se falla el caso");        
    		Assert.fail("No se ha encontrado el campo de Valor Fecha de la pantalla de RECEPCIÓN DE PEDIDO CEDIS en la parte de inicio", null);
    	}    	
    	if(au.driver.findElements(CEDIS_Ruta).isEmpty()!= false) {
    		baseExecution.addStep("Se falla el caso");        
    		Assert.fail("No se ha encontrado el campo de Ruta de la pantalla de RECEPCIÓN DE PEDIDO CEDIS en la parte de inicio", null);
    	}    	
    	if(au.driver.findElements(CEDIS_RutaDinamica).isEmpty()!= false) {
    		baseExecution.addStep("Se falla el caso");        
    		Assert.fail("No se ha encontrado el campo de Ruta Dinamica de la pantalla de RECEPCIÓN DE PEDIDO CEDIS en la parte de inicio", null);
    	}
    	if(au.driver.findElements(CEDIS_CRTienda).isEmpty()!= false) {
    		baseExecution.addStep("Se falla el caso");        
    		Assert.fail("No se ha encontrado el campo de CR Tienda de la pantalla de RECEPCIÓN DE PEDIDO CEDIS en la parte de inicio", null);
    	}
    	if(au.driver.findElements(CEDIS_NombreTienda).isEmpty()!= false) {
    		baseExecution.addStep("Se falla el caso");        
    		Assert.fail("No se ha encontrado el campo de Nombre Tienda de la pantalla de RECEPCIÓN DE PEDIDO CEDIS en la parte de inicio", null);
    	}
    	if(au.driver.findElements(CEDIS_Lider).isEmpty()!= false) {
    		baseExecution.addStep("Se falla el caso");        
    		Assert.fail("No se ha encontrado el campo de Lider de la pantalla de RECEPCIÓN DE PEDIDO CEDIS en la parte de inicio", null);
    	}
    	if(au.driver.findElements(CEDIS_Transferencia).isEmpty()!= false) {
    		baseExecution.addStep("Se falla el caso");        
    		Assert.fail("No se ha encontrado el campo de Transferencia de la pantalla de RECEPCIÓN DE PEDIDO CEDIS en la parte de inicio", null);
    	}
    	if(au.driver.findElements(CEDIS_ResumenEntregaPedido).isEmpty()!= false) {
    		baseExecution.addStep("Se falla el caso");        
    		Assert.fail("No se ha encontrado el campo de Transferencia de la pantalla de RECEPCIÓN DE PEDIDO CEDIS en la parte de inicio", null);
    	}
    	
    }
    
    /**
     * Método para validar el total de bultos pedido
     *
     * Obtener el número total de bultos pedidos que se muestra en la pantalla de resumen de un pedido en un dispositivo handheld.
     *
     * @author Juan Ibarra
     * @date 
     * @throws FrameworkException
     */
    
    public int TotalBultosPedido() throws IOException {
    	//Funcion para obtener el Total de Bultos Pedidos Creado: Juan Ibarra 
    	//Nota.- Funciona para Handheld
    	au.scrollToElement(CEDIS_ResumenTotalPedido);
    	WebElement elemento = au.driver.findElement(CEDIS_ResumenTotalPedido);
    	String posicionobjeto = elemento.getAttribute("bounds");
    	//System.out.println(posicionobjeto);//se imprime la posición del objeto del cual se va utilizar para obtener los valores de los demas objetos 
    	String x1Bulto = posicionobjeto.substring(1,3); //obtiene la posición X1 de Bulto
    	String y1Bulto = posicionobjeto.substring(4,7); //obtiene la posición y1 de Bulto
    	String x2Bulto = posicionobjeto.substring(9,12); //obtiene la posición X2 de Bulto
    	String y2Bulto = posicionobjeto.substring(13,16); //obtiene la posición y2 de Bulto
    	int X2Pedido = Integer.parseInt(x2Bulto)+131;//valor que corresponde a la posición X2 de Total de Pedido
   	    	    	
    	By CEDIS_ResumenPedidoTotal = By.xpath("//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/tvTotalOrder\" and @bounds=\"["+x2Bulto+","+y1Bulto+"]["+X2Pedido+","+y2Bulto+"]\"]");
    	String TotalPedidoBultos = au.driver.findElement(CEDIS_ResumenPedidoTotal).getText();//Se obtiene el valor de Total de Bultos del pedido  
    	//System.out.println("Valor del total de Bultos Pedidos: " +TotalPedidoBultos);
    	int TotalBultos = Integer.parseInt(TotalPedidoBultos);
    	return TotalBultos;    	
        
    }
    
    /**
     * Método para validar total bultos entregados
     *
     * Obtener el número total de bultos entregados que se muestra en la pantalla de resumen de un pedido en un dispositivo handheld.
     *
     * @author Juan Ibarra
     * @date 
     * @throws FrameworkException
     */
    
    public int TotalBultosEntregados() throws IOException {
    	//Funcion para obtener el Total de Bultos Entregados Creado: Juan Ibarra
    	//Nota.- Funciona para Handheld
    	au.scrollToElement(CEDIS_ResumenTotalPedido);
    	WebElement elemento = au.driver.findElement(CEDIS_ResumenTotalPedido);
    	String posicionobjeto = elemento.getAttribute("bounds");
    	//System.out.println(posicionobjeto);//se imprime la posición del objeto del cual se va utilizar para obtener los valores de los demas objetos 
    	String x1Bulto = posicionobjeto.substring(1,3); //obtiene la posición X1 de Bulto
    	String y1Bulto = posicionobjeto.substring(4,7); //obtiene la posición y1 de Bulto
    	String x2Bulto = posicionobjeto.substring(9,12); //obtiene la posición X2 de Bulto
    	String y2Bulto = posicionobjeto.substring(13,16); //obtiene la posición y2 de Bulto
    	
    	int X1Entregado = Integer.parseInt(x2Bulto)+131;//valor que corresponde a la posición X1 de Total Entregado
    	int X2Entregado = Integer.parseInt(x2Bulto)+296;//valor que corresponde a la posición X2 de Total Entregado
    	
    	By CEDIS_ResumenEntregadoTotal = By.xpath("//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/tvTotalDelivered\" and @bounds=\"["+X1Entregado+","+y1Bulto+"]["+X2Entregado+","+y2Bulto+"]\"]");
    	String TotalEntregadoBultos = au.driver.findElement(CEDIS_ResumenEntregadoTotal).getText();//Se obtiene el valor de Total de Bultos Entregados en el pedido 
    	//System.out.println("Valor del total de Bultos Entregados: " +TotalEntregadoBultos);
    	int TotalEntregado = Integer.parseInt(TotalEntregadoBultos);
    	return TotalEntregado;
    }
    
    /**
     * Método para validar total cajas sin etiquetas
     *
     * Obtener el total de cajas sin etiqueta registradas en la pantalla de detalle, específicamente en un dispositivo handheld
     *
     * @author Juan Ibarra
     * @date 
     * @throws FrameworkException
     */
    
    
    public int TotalCajaSinEtiquetas() throws IOException {
    	//Funcion para obtener el Total de Entrada Caja Sin Etiquetas Creado: Juan Ibarra
    	//Nota.- Funciona para Handheld
    	au.scrollToElement(CEDIS_DetalleFaltante);
    	WebElement elemento = au.driver.findElement(CEDIS_TotalCajasSinEtiqueta);
    	String posicionobjeto = elemento.getAttribute("bounds");
    	String x1Bulto = posicionobjeto.substring(1,3); //obtiene la posición X1 de Bulto
    	String y1Bulto = posicionobjeto.substring(4,7); //obtiene la posición y1 de Bulto
    	String x2Bulto = posicionobjeto.substring(9,12); //obtiene la posición X2 de Bulto
    	String y2Bulto = posicionobjeto.substring(13,16); //obtiene la posición y2 de Bulto
    	int X2Pedido = Integer.parseInt(x2Bulto)+161;//valor que corresponde a la posición X2 de Total de Pedido
    	   	    	    	
    	By CEDIS_ValorTotalCajasSinEtiqueta = By.xpath("//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/tvWOcode\" and @bounds=\"["+x2Bulto+","+y1Bulto+"]["+X2Pedido+","+y2Bulto+"]\"]");
    	String TotalCajasSinEtiqueta = au.driver.findElement(CEDIS_ValorTotalCajasSinEtiqueta).getText();//Se obtiene el valor de Total de Bultos del pedido  
    	//System.out.println("Valor del total de Bultos Pedidos: " +TotalPedidoBultos);
    	int TotalCajas = Integer.parseInt(TotalCajasSinEtiqueta);
    	return TotalCajas;
    }
    
    /**
     * Método para validar total sobrantes
     *
     * Obtener el total de sobrantes registrados en la pantalla, específicamente para un dispositivo tipo handheld.
     *
     * @author Juan Ibarra
     * @date 
     * @throws FrameworkException
     */
    
    public int TotalSobrantes () throws IOException {
    	//Funcion para obtener el Total de Sobrabtes Creado: Juan Ibarra
    	//Nota.- Funciona para Handheld
    	au.scrollToElement(CEDIS_LeyendaDanados);
    	WebElement elemento = au.driver.findElement(CEDIS_SobrantesTotalSobrantes);
    	String posicionobjeto = elemento.getAttribute("bounds");
    	String x1Bulto = posicionobjeto.substring(1,3); //obtiene la posición X1 de Bulto
    	String y1Bulto = posicionobjeto.substring(4,7); //obtiene la posición y1 de Bulto
    	String x2Bulto = posicionobjeto.substring(9,12); //obtiene la posición X2 de Bulto
    	String y2Bulto = posicionobjeto.substring(13,16); //obtiene la posición y2 de Bulto
    	int X2Pedido = Integer.parseInt(x2Bulto)+129;//valor que corresponde a la posición X2 de Total de Pedido
    	   	    	    	
    	By CEDIS_ResumenTotalSobrantes = By.xpath("//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/tvTotalLeftover\" and @bounds=\"["+x2Bulto+","+y1Bulto+"]["+X2Pedido+","+y2Bulto+"]\"]");
    	String TotalSobrantes = au.driver.findElement(CEDIS_ResumenTotalSobrantes).getText();//Se obtiene el valor de Total de Bultos del pedido  
    	//System.out.println("Valor del total de Sobrantes: " +TotalSobrantes);
    	int CantidadTotalSobrantes = Integer.parseInt(TotalSobrantes);
    	return CantidadTotalSobrantes;        
    }
    
    /**
     * Método para validar total dañados
     *
     * Obtener el total de productos dañados registrados en la pantalla, especialmente diseñado para dispositivo handheld
     *
     * @author Juan Ibarra
     * @date 
     * @throws FrameworkException
     */
    
    public int TotalDanados () throws IOException {
    	//Funcion para obtener el Total de Dañados Creado: Juan Ibarra
    	//Nota.- Funciona para Handheld
    	au.scrollToElement(CEDIS_FirmaAcuerdo);
    	WebElement elemento = au.driver.findElement(CEDIS_TotalDanados);
    	String posicionobjeto = elemento.getAttribute("bounds");
    	String x1Bulto = posicionobjeto.substring(1,3); //obtiene la posición X1 de Bulto
    	String y1Bulto = posicionobjeto.substring(4,7); //obtiene la posición y1 de Bulto
    	String x2Bulto = posicionobjeto.substring(9,12); //obtiene la posición X2 de Bulto
    	String y2Bulto = posicionobjeto.substring(13,17); //obtiene la posición y2 de Bulto
    	int X2Pedido = Integer.parseInt(x2Bulto)+164;//valor que corresponde a la posición X2 de Total de Pedido
    	   	    	    	
    	By CEDIS_ResumenTotalDanados = By.xpath("//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/tvCounterDamage\" and @bounds=\"["+x2Bulto+","+y1Bulto+"]["+X2Pedido+","+y2Bulto+"]\"]");
    	String TotalDanados = au.driver.findElement(CEDIS_ResumenTotalDanados).getText();//Se obtiene el valor de Total de Bultos del pedido  
    	//System.out.println("Valor del total de Sobrantes: " +TotalDanados);
    	int CantidadTotalDanados = Integer.parseInt(TotalDanados);
    	return CantidadTotalDanados;        
    }
    
    /**
     * Método para seleccionar objeto
     *
     * Este método permite seleccionar y hacer clic en un elemento específico de la pantalla móvil basado en el nombre del objeto que se pasa como parámetro.
     *
     * @author Juan Ibarra
     * @date 
     * @throws FrameworkException
     */
    
    
    public void SeleccionarObjeto(String ObjetoPantalla)
    {  	
    	//Funcion para buscar objeto y dar click especifico en la pantalla Creado: Juan Ibarra    	
    	switch (ObjetoPantalla) {
    	case "Resumen Total Pedido":    
    		au.scrollToElement(CEDIS_ResumenTotalPedido);
    		au.click(CEDIS_ResumenTotalPedido);
            break;   
    	case "Total Cajas Sin Etiqueta":  
    		au.scrollToElement(CEDIS_TotalCajasSinEtiqueta);
    		au.click(CEDIS_TotalCajasSinEtiqueta);
            break;
    	case "Leyenda Faltante Refrigerado":  
    		au.scrollToElement(CEDIS_LeyendaFaltanteRefrigerado);
    		au.click(CEDIS_LeyendaFaltanteRefrigerado);
            break;
    	case "Leyenda Faltante Refrigerado Tablet":  
    		au.scrollToElement(CEDIS_LeyendaFaltanteRefrigeradoTablet);
    		au.click(CEDIS_LeyendaFaltanteRefrigeradoTablet);
            break;
    	case "Sobrantes Total Sobrantes":  
    		au.scrollToElement(CEDIS_SobrantesTotalSobrantes);
    		au.click(CEDIS_SobrantesTotalSobrantes);
            break;
    	case "Total Dañados":  
    		au.scrollToElement(CEDIS_TotalDanados);
    		au.click(CEDIS_TotalDanados);
            break;
    	case "Firma Acuerdo":
    		au.scrollToElement(CEDIS_FirmaAcuerdo);
    		au.click(CEDIS_FirmaAcuerdo);
            break;        	
        default:
            baseExecution.addStep("Se falla el caso");
            //Se agrega el paso a mostrar en el PDF
    		Assert.fail("No se ha encontrado el objeto "+ ObjetoPantalla, null);
    		//Se agrega un mensaje a mostrar del error, en caso de no ponerlo regresaria leyenda de null
    	}    	
    }
    
    /**
     * Método para avanzar sin firmar acuerdo sin evidencia
     *
     * Avanza en el flujo de la aplicación haciendo clic en el botón o sección de firma de acuerdo sin que se capture ninguna evidencia o fotografía.
     *
     * @author Juan Ibarra
     * @date 
     * @throws FrameworkException
     */
    
    public void FirmaAcuerdoSinEvidencia() throws InterruptedException
    {  	
    	//Funcion en la cual se avanza sin Firmar Acuerdo Sin Evidencia Creado: Juan Ibarra
    	au.scrollToElement(CEDIS_FirmaAcuerdo);
    	Thread.sleep(300);    	
    	au.click(CEDIS_FirmaAcuerdo);
    }
    
    /**
     * Método para seccion resumen recepcion
     *
     * Este método realiza un recorrido automático por varias secciones clave del resumen de recepción en la interfaz de usuario y toma capturas de pantalla de cada una.
     *
     * @author Ricardo Hdz
     * @date   23/10/2024
     * @throws FrameworkException
     */
    
  //Funcion en la cual realiza scroll a cada seccion de resumen y toma la fotografia: 	Ricardo Hdz 23/10/2024    
    public void SeccionesResumenRecepcion(String descPaso) throws IOException, InterruptedException {

    	baseExecution.addStep(descPaso);
    	au.scrollToElement(CEDIS_ResumenTotalPedido);
    	testCase.addScreenShotCurrentStep(au, "testImage");			
    	Thread.sleep(1000);	
    	au.scrollToElement(CEDIS_TotalCajasSinEtiqueta);    			
    	testCase.addScreenShotCurrentStep(au, "testImage");			
    	Thread.sleep(1000);	  	
		au.scrollToElement(CEDIS_LeyendaFaltanteRefrigerado);
    	testCase.addScreenShotCurrentStep(au, "testImage");			
    	Thread.sleep(1000);	
		au.scrollToElement(CEDIS_SobrantesTotalSobrantes);
    	testCase.addScreenShotCurrentStep(au, "testImage");	
    	Thread.sleep(1000);	
    	au.scrollToElement(CEDIS_TotalDanados);
    	testCase.addScreenShotCurrentStep(au, "testImage");	
		}
    
    /**
     * Método para validar 
     *
     * Este método simula la entrada de texto en un dispositivo Android utilizando eventos de tecla (key events) a través de Appium. En lugar de ingresar el texto directamente, 
     * envía las pulsaciones de teclas correspondientes para cada carácter del texto, emulando el comportamiento de un teclado físico o virtual.
     *
     * @author Vanessa Torres
     * @date   18/02/2025
     * @throws FrameworkException
     */
    
	public void sendKeysUsingKeyEvents(AppiumUtil au, String text) { //Vanessa Torres 18/02/2025
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
  
    

