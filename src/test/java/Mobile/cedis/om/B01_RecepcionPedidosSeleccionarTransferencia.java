package Mobile.cedis.om;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.NoSuchElementException;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import exceptions.FrameworkException;
import io.netty.handler.timeout.TimeoutException;
import modelo.BaseExecution;
import modelo.TestCase;
import utils.AppiumUtil;
import utils.password.PasswordUtil;
import utils.sql.SQLResult;
import Mobile.util.GlobalVariables;

public class B01_RecepcionPedidosSeleccionarTransferencia {
    private TestCase testCase;
	private AppiumUtil au;
	private BaseExecution baseExecution;

	//sección encabezado
	private By CEDIS_tituloPantalla = By.xpath("//android.widget.TextView[@text=\"RECEPCIÓN DE PEDIDO CEDIS\"]");
	private By CEDIS_Fecha = By.xpath("//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/tvDateText\" and @text=\"Fecha: \"]");
	private By CEDIS_ValorFecha = By.xpath("//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/tvTitleDate\"]");
	private By CEDIS_Ruta = By.xpath("//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/tvTitleRute\"]");
	private By CEDIS_RutaDinamica = By.xpath("//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/tvTitleRuteD\"]");
	private By CEDIS_CRTienda = By.xpath("//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/tvTitleStore\"]");
	private By CEDIS_NombreTienda = By.xpath("//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/tvTitleNameStore\"]");	
	private By CEDIS_Lider = By.xpath("//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/tvTitleLeaderStore\"]");
    
    //sección busqueda Transferencia
	private By CEDIS_LeyendaTransferencia = By.xpath("//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/textView26\" and @text=\"Transferencia\"]");
	private By CEDIS_Transferencia = By.xpath("//android.widget.EditText[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/ET_EviPedido\" and @text=\"Buscar transferencia\"]");
    private By CEDIS_Buscar = By.xpath("//android.widget.ImageButton[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/btn_EviPedido\"]");
    private By CEDIS_descrEscaner = By.xpath("//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/etAccionable\" and @text=\"Recepción sin escáner\"]");
    private By CEDIS_switchEscaner = By.xpath("//android.widget.Switch[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/swNoScan\" and @checked=\"false\"]");
    private By CEDIS_switchEscanerHabilitado = By.xpath("//android.widget.Switch[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/swNoScan\" and @checked=\"true\"]");

    //sección Listado transferencias
    private By CEDIS_ListaTransferencias = By.xpath("//android.widget.FrameLayout[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/cardView4\"]/android.widget.LinearLayout/android.widget.ScrollView");
    private By CEDIS_UnicaTransferencia = By.id("com.femco.oxxo.operaciones.mitienda.debug:id/text_trans");
    private By CEDIS_PrimeraTransferencia = By.xpath("//androidx.recyclerview.widget.RecyclerView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/rec_transferencia\"]/android.widget.LinearLayout[1]");
    private By CEDIS_SegundaTransferencia = By.xpath("//androidx.recyclerview.widget.RecyclerView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/rec_transferencia\"]/android.widget.LinearLayout[2]");
    private By CEDIS_TerceraTransferencia = By.xpath("//androidx.recyclerview.widget.RecyclerView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/rec_transferencia\"]/android.widget.LinearLayout[3]");
    private By CEDIS_resultadoBuscarTransferencia = By.xpath("//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/text_trans\"]");
    private By CEDIS_NoExistePedido = By.xpath("//android.widget.Toast[@text=\"El número de pedido ingresado no existe\"]");
    private By CEDIS_Reloj = By.xpath("(//android.widget.ImageView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/ivClockSaved\"])");
    private By CEDIS_RelojDos = By.xpath("(//android.widget.ImageView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/ivClockSaved\"])[2]");
    private By CEDIS_RelojUnico = By.xpath("(//android.widget.ImageView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/ivClockSaved\"])[1]");
    private By CEDIS_MensajeLimiteTransferencias = By.xpath("//android.widget.TextView[@resource-id=\"android:id/message\"]");    
    private By CEDIS_AceptarMensaje = By.xpath("//android.widget.Button[@resource-id=\"android:id/button1\" and @text=\"ACEPTAR\"]");
    
    public final String ip = "";
	public static final String userRDM100 = "RDM100";
	public static final String passwordRDM100 = PasswordUtil.decryptPassword("F390207ED6EC2E6FCB56289BFDA0AF7E");
 
   
     /**
	 * Constructor
	 *****************************************************************************************
	 * Constructor de la clase B01_RecepcionPedidosSeleccionarTransferencia
	 *
	 * @param au            (AppiumUtil): objeto de la clase
	 * @param testCase      (TestCase): objeto de la clase
	 * @param baseExecution (BaseExecution): objeto de la clase
	 * @author Juan Ibarra
	 */
	
    public B01_RecepcionPedidosSeleccionarTransferencia(TestCase testCase, AppiumUtil au, BaseExecution baseExecution) {
        this.testCase = testCase;
        this.au = au;
        this.baseExecution = baseExecution;
    }
    
    /**
	 * Método para validar encabezado en pantalla recepcion de pedidos
	 *
	 * Este método asegura que los campos del encabezado de la pantalla de "RECEPCIÓN DE PEDIDO CEDIS" están presentes y activos si falta alguno la prueba falla y se registra el error.
	 *
	 * @author Juan Ibarra
	 * @date 
	 * @throws FrameworkException
	 */
    
    public void ValidacionEncabezado() throws IOException {
    	//Funcion que permite validar que se muestran los campo correspondientes al encabezado de la pantalla  Creado: Juan Ibarra
    	if(au.driver.findElement(CEDIS_tituloPantalla).isEnabled()==false) {
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
    }
    
    /**
	 * Método para seleccionar la primera transferencia 
	 *
	 * Este método realiza la selección de la primera transferencia disponible en la lista y falla la prueba si no hay transferencias para seleccionar.
	 *
	 * @author Juan Ibarra
	 * @date 
	 * @throws FrameworkException
	 */
    
    
    public void SeleccionarPrimeraTransferencia(Boolean SinEscaner) throws InterruptedException {
        /**
         * Método con el cual se realiza la selección de la primera Transferencia listada en la aplicación, en caso de no encontrar una transferencia falla el caso
         * @param SinEscaner valor booleano con el cual al enviar True seleccionar  
         * Autor: Juan Ibarra  
         */
    	           	
        if (SinEscaner) {
        	Thread.sleep(2000);
        	au.scrollToElement(CEDIS_switchEscaner);
            au.click(CEDIS_switchEscaner);
        	Thread.sleep(2000);
        }
        
    	au.scrollToElement(CEDIS_ListaTransferencias);
    	boolean transferencia;
    	transferencia= au.driver.findElements(CEDIS_PrimeraTransferencia).isEmpty(); //Se agrega a un booleano donde defina si el objeto se muestra en pantalla
    	//Se valida que el objeto se muestra en pantalla
    	if (transferencia != true ) {
        	Thread.sleep(2000);
        	au.scrollToElement(CEDIS_PrimeraTransferencia);
    		au.click(CEDIS_PrimeraTransferencia);    		
        	Thread.sleep(2000);
 		}
    	else {
    		baseExecution.addStep("Se falla el caso");
    		//Se agrega el paso a mostrar en el PDF
    		Assert.fail("No se ha encontrado una transferencia que seleccionar en la pantalla, favor de cargar ASN en el punto de venta", null);
    		//Se agrega un mensaje a mostrar del error, en caso de no ponerlo regresaria leyenda de null
    	}
    }
    
    /**
	 * Método para seleccionar segunda transferencia
	 *
	 * El método selecciona la segunda transferencia disponible en la lista y si no encuentra ninguna falla la prueba con un mensaje.
	 *
	 * @author Juan Ibarra
	 * @date 
	 * @throws FrameworkException
	 */
    
    
    public void SeleccionarSegundaTransferencia(Boolean SinEscaner) throws InterruptedException {
        /**
         * Método con el cual se realiza la selección de la segunda Transferencia listada en la aplicación, en caso de no encontrar una transferencia falla el caso
         * @param SinEscaner valor booleano con el cual al enviar True seleccionar  
         * Autor: Juan Ibarra  
         */
    	           	
        if (SinEscaner) {
        	Thread.sleep(2000);
        	au.scrollToElement(CEDIS_switchEscaner);
            au.click(CEDIS_switchEscaner);
        	Thread.sleep(2000);
        }
        
    	au.scrollToElement(CEDIS_ListaTransferencias);
    	boolean transferencia;
    	transferencia= au.driver.findElements(CEDIS_SegundaTransferencia).isEmpty(); //Se agrega a un booleano donde defina si el objeto se muestra en pantalla
    	//Se valida que el objeto se muestra en pantalla
    	if (transferencia != true ) {
        	Thread.sleep(2000);
        	au.scrollToElement(CEDIS_SegundaTransferencia);
    		au.click(CEDIS_SegundaTransferencia);    		
        	Thread.sleep(2000);
 		}
    	else {
    		baseExecution.addStep("Se falla el caso");
    		//Se agrega el paso a mostrar en el PDF
    		Assert.fail("No se ha encontrado una transferencia que seleccionar en la pantalla, favor de cargar ASN en el punto de venta", null);
    		//Se agrega un mensaje a mostrar del error, en caso de no ponerlo regresaria leyenda de null
    	}
    }
    
    /**
	 * Método para seleccionar tercera transferencia
	 *
	 * Este método selecciona la tercera transferencia en la lista y asegura que la prueba falle si no hay transferencias disponibles.
	 *
	 * @author Juan Ibarra
	 * @date 
	 * @throws FrameworkException
	 */
    
    public void SeleccionarTerceraTransferencia(Boolean SinEscaner) throws InterruptedException {
        /**
         * Método con el cual se realiza la selección de la tercera Transferencia listada en la aplicación, en caso de no encontrar una transferencia falla el caso
         * @param SinEscaner valor booleano con el cual al enviar True seleccionar  
         * Autor: Juan Ibarra  
         */
    	           	
        if (SinEscaner) {
        	Thread.sleep(2000);
        	au.scrollToElement(CEDIS_switchEscaner);
            au.click(CEDIS_switchEscaner);
        	Thread.sleep(2000);
        }
        
    	au.scrollToElement(CEDIS_ListaTransferencias);
    	boolean transferencia;
    	transferencia= au.driver.findElements(CEDIS_TerceraTransferencia).isEmpty(); //Se agrega a un booleano donde defina si el objeto se muestra en pantalla
    	//Se valida que el objeto se muestra en pantalla
    	if (transferencia != true ) {
        	Thread.sleep(2000);
        	au.scrollToElement(CEDIS_TerceraTransferencia);
    		au.click(CEDIS_TerceraTransferencia);    		
        	Thread.sleep(2000);
 		}
    	else {
    		baseExecution.addStep("Se falla el caso");
    		//Se agrega el paso a mostrar en el PDF
    		Assert.fail("No se ha encontrado una transferencia que seleccionar en la pantalla, favor de cargar ASN en el punto de venta", null);
    		//Se agrega un mensaje a mostrar del error, en caso de no ponerlo regresaria leyenda de null
    	}
    }
      
    /**
	 * Método para buscar transferencia
	 *
	 * Este método permite buscar una transferencia específica y marcara error si no se encuentra la transferencia buscada.
	 *
	 * @author Juan Ibarra
	 * @date 
	 * @throws FrameworkException
	 */
    
    public void BuscarTransferencia(Boolean SinEscaner, String transferencia)  {
        /**
         * Método con el cual se realiza la busqueda de una transferencia especifica en caso de no encontrar la transferencia falla el caso   
         * @param Numero de transferencia que se buscara dentro de la aplicación
         * Autor: Juan Ibarra
         */
    	if (SinEscaner) {
    		au.click(CEDIS_switchEscaner);
    	}
    	
    	au.driver.hideKeyboard();
        au.sendKeys(CEDIS_Transferencia, transferencia);
    	au.click(CEDIS_Buscar);
    	au.scrollToElement(CEDIS_ListaTransferencias);
    	boolean transferenciabuscada;
    	transferenciabuscada = au.driver.findElements(CEDIS_NoExistePedido).isEmpty(); //Se agrega a un booleano donde defina si el objeto se muestra en pantalla
    	//Se valida que el objeto se muestra en pantalla
    	if (transferenciabuscada != true ) {
    		baseExecution.addStep("Se falla el caso");
    		//Se agrega el paso a mostrar en el PDF
    		Assert.fail("No se ha encontrado la transferencia capturada", null);
    		//Se agrega un mensaje a mostrar del error, en caso de no ponerlo regresaria leyenda de null    			
 		}
    	else {
    		au.click(CEDIS_resultadoBuscarTransferencia);
    	}
    }
    
    /**
	 * Método para buscar primera transfencia
	 *
	 * Este método conecta a la base de datos del punto de venta para obtener la primera transferencia pendiente, luego la busca y selecciona en la app, gestionando errores si no se encuentra.
	 *
	 * @author Juan Ibarra
	 * @date 
	 * @throws FrameworkException
	 */
    
    public void BuscarPrineraTransferencia(Boolean SinEscaner, String IP)  {
        /**
         * Método con el cual se realiza la busqueda de la primera transferencia con la que cuenta el punto de venta en caso de no encontrar la transferencia falla el caso   
         * Autor: Juan Ibarra
         */    
    	String transferencia = null;//variable string con el cual se guarda la transferencia que se encuentre en el punto de venta
		try {
			Connection conn = DriverManager.getConnection("jdbc:sqlserver://"+IP+";databaseName=satelites;encrypt=true;trustServerCertificate=true", GlobalVariables.userXPOS,GlobalVariables.passwordXPOS);//Se define la conexión al punto de venta por la ip
	        String query = "use satelites select top(1) HC_TransferNo_Ref from [hc].[hc_txm_det] where HC_Post_Date is null order by HC_TransferNo_Ref asc";//Query de consulta la primera transferencia 
			SQLResult res = new SQLResult(conn, query);
			transferencia = String.valueOf(res.getFirstData().toString());//Se guarda el valor del numero de transferencia 			
	        conn.close();
	    } catch (Exception e) {	        	    	
	        baseExecution.addStep("Se falla el caso");	
	        Assert.fail("No cuenta con Transferecia a la cual consultar dentro del Punto de Venta, Favor de realizar carga de archivos ASN.");
	    }    	
    	if (SinEscaner) {
    		au.click(CEDIS_switchEscaner);
    	}
    	au.driver.hideKeyboard();
    	au.sendKeys(CEDIS_Transferencia, transferencia);
    	au.click(CEDIS_Buscar);
    	au.scrollToElement(CEDIS_ListaTransferencias);    	
    	au.click(CEDIS_resultadoBuscarTransferencia);
    }
    
    /**
	 * Método para validar transferencias activas
	 *
	 * Este método se conecta a la base de datos de un punto de venta para recuperar y mostrar en consola todas las transferencias activas pendientes.
	 *
	 * @author Juan Ibarra
	 * @date 
	 * @throws FrameworkException
	 */
    
    public void TransferenciasActivas(String IP)  {
    	
    	String transferencias = null;//variable string con el cual se guarda la transferencia que se encuentre en el punto de venta    	
		try {
			System.out.println("Entro al Try");
			Connection conn = DriverManager.getConnection("jdbc:sqlserver://"+IP+";databaseName=satelites;encrypt=true;trustServerCertificate=true", GlobalVariables.userXPOS,GlobalVariables.passwordXPOS);//Se define la conexión al punto de venta por la ip
	        String query = "use satelites select Distinct  HC_TransferNo_Ref from [hc].[hc_txm_det] where HC_Post_Date is null order by HC_TransferNo_Ref asc";//Query de consulta la primera transferencia 
			SQLResult res = new SQLResult(conn, query);
			
			
			//transferencias  = res.getAllData().toString();
			//transferencias = String.valueOf(res.getFirstData().toString());//Se guarda el valor del numero de transferencia 			
			transferencias = String.valueOf(res.getAllData().toString());//Se guarda el valor del numero de transferencia
			//transferencias = String.valueOf(res.getAllData());//Se guarda el valor del numero de transferencia
			//transferencias  = res.getAllData().toString();
			//transferencias  = String.valueOf(res.getAllData().toString());
			//transferencias  = Arrays.toString(String.valueOf(res.getAllData()));			
					
			System.out.println("valor de transferencia "+transferencias);			
	        conn.close();
	    }  catch (Exception e) {	        	    	
        	     System.out.println("Entro a Catch");
        	//baseExecution.addStep("Se falla el caso");	
   	        //Assert.fail("No cuenta con Transferecias a las cuales consultar dentro del Punto de Venta, Favor de realizar carga de archivos ASN.");
   	    }    	
    }
    
    /**
	 * Método para Seleccionar objeto en pantalla
	 *
	 * Método para ubicar y hacer clic en un elemento específico de la interfaz según el nombre proporcionado, mostrara mensaje de error si el objeto no existe.
	 *
	 * @author Juan Ibarra
	 * @date 
	 * @throws FrameworkException
	 */
    
    public void SeleccionarObjeto(String ObjetoPantalla)
    {  	
    	//Funcion para buscar objeto y dar click especifico en la pantalla Creado: Juan Ibarra
    	switch (ObjetoPantalla) {    	
    	case "Sin Escaner":    		
    		au.scrollToElement(CEDIS_switchEscaner);
    		au.click(CEDIS_switchEscaner);
            break;
    	case "Buscar":    		
    		au.scrollToElement(CEDIS_Buscar);
    		au.click(CEDIS_Buscar);
            break;
    	case "Reloj":    		
    		au.scrollToElement(CEDIS_Reloj);
    		au.click(CEDIS_Reloj);
            break;
    	case "Aceptar"://Se agrega opción para el mensaje de Limite de transferencias    		
    		au.scrollToElement(CEDIS_AceptarMensaje);
    		au.click(CEDIS_AceptarMensaje);
            break;   
        default:
            baseExecution.addStep("Se falla el caso");
            //Se agrega el paso a mostrar en el PDF
    		Assert.fail("No se ha encontrado el objeto "+ ObjetoPantalla, null);
    		//Se agrega un mensaje a mostrar del error, en caso de no ponerlo regresaria leyenda de null
    	}    	
    }

    /**
	 * Método para validar la cantidad de transferencias
	 *
	 * Método que valida si existe al menos una transferencia en la posición especificada, para asegurar que se cuenta con la cantidad requerida para realizar el test.
	 *
	 * @author Juan Ibarra
	 * @date 
	 * @throws FrameworkException
	 */
    
    public void ValidarCantidadTransferencias(String Cantidad) throws InterruptedException  {  	
    	//Funcion que valida que se cuente con la cantidad de tranferencias necesarias para validar el caso de prueba a ejecutar 	Creado: Juan Ibarra	Fecha Creación:28-Octubre-2024
    	Thread.sleep(1000);
    	By CantidadTransferencia = By.xpath("//androidx.recyclerview.widget.RecyclerView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/rec_transferencia\"]/android.widget.LinearLayout["+Cantidad+"]");
    	if (au.driver.findElements(CantidadTransferencia).isEmpty()!= false) {
    		baseExecution.addStep("Se falla el caso");        
    		Assert.fail("No se cuenta con la cantidad de "+Cantidad+" Transderencias en la pantalla RECEPCIÓN DE PEDIDO CEDIS requeridas para el caso de prueba", null);
    	}    	    	
    }
    
    /**
	 * Método para validar transferencias activas
	 *
	 * Método para verificar que hay suficientes transferencias activas visibles en pantalla para cumplir con las condiciones del caso de prueba.
	 *
	 * @author Juan Ibarra
	 * @date 
	 * @throws FrameworkException
	 */
    
    public void ValidarTransferenciasActivas(String Cantidad) throws InterruptedException  {  	
    	//Funcion que valida que se cuente con la cantidad de tranferencias necesarias para validar el caso de prueba a ejecutar 	Creado: Juan Ibarra	Fecha Creación:28-Octubre-2024
    	Thread.sleep(1000);
    	//By TransferenciasActivas = By.xpath("//androidx.recyclerview.widget.RecyclerView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/rec_transferencia\"]/android.widget.LinearLayout["+Cantidad+"]");
    	By TransferenciasActivas = By.xpath("(//android.widget.ImageView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/ivClockSaved\"])["+Cantidad+"]");
    	if (au.driver.findElements(TransferenciasActivas).isEmpty()!= false) {
    		baseExecution.addStep("Se falla el caso");        
    		Assert.fail("No se tienen la cantidad de "+Cantidad+" Transderencias activas requeridas para el caso de prueba", null);
    	}    	    	
    }
    
    /**
	 * Método para mover elementos en la pantalla
	 *
	 * Método que permite desplazar la pantalla hacia elementos como la lista de transferencias si el nombre no coincide con una opción válida, se registra un error y falla la prueba.
	 *
	 * @author Juan Ibarra
	 * @date 
	 * @throws FrameworkException
	 */
    
    public void MoverHacia(String Moverse) {
    	
    	switch(Moverse) {
    	case "Transferencia":
    		au.scrollToElement(CEDIS_ListaTransferencias);
    		break;
    	case "Reloj"://valida que se cuente una Transferencia ya iniciada en su atención Agregado por Juan Ibarra 
    		au.scrollToElement(CEDIS_Reloj);
    		break;
    	default:
            baseExecution.addStep("Se falla el caso");
            //Se agrega el paso a mostrar en el PDF
    		Assert.fail("No se ha encontrado el objeto "+ Moverse, null);
    		//Se agrega un mensaje a mostrar del error, en caso de no ponerlo regresaria leyenda de null
    	}
    	
    }    
    
    /**
	 * Método para validar el despliegue de dos transferencias
	 *
	 * Este método asegura que se desplieguen al menos dos transferencias en la interfaz
	 * si solo hay una o ninguna, falla la prueba, documenta el error y toma evidencia.
	 *
	 * @author Jesús Marcelo Gutiérrez Bravo
	 * @date   19/02/2025
	 * @throws FrameworkException
	 */
    
    
    public void ValidarDespliegueDeAlMenosDosTransferencias() throws InterruptedException {
        // Descripción: Método para validar el despliegue de al menos dos transferencias en el listado
        // Autor: Jesús Marcelo Gutiérrez Bravo
        // Fecha: 19/02/2025

        // Esperar 1 segundo para permitir la carga de elementos en la pantalla
        Thread.sleep(1000);

        boolean primeraPresente = false;
        boolean segundaPresente = false;
        boolean unicaPresente = false;

        // Validar si la primera transferencia está presente
        try {
            WebElement Elemento = au.driver.findElement(CEDIS_PrimeraTransferencia);
            primeraPresente = Elemento.isDisplayed();
        } catch (NoSuchElementException e) {
            primeraPresente = false;
        }

        // Validar si la segunda transferencia está presente
        try {
            WebElement Elemento2 = au.driver.findElement(CEDIS_SegundaTransferencia);
            segundaPresente = Elemento2.isDisplayed();
        } catch (NoSuchElementException e) {
            segundaPresente = false;
        }

        // Validar si solo hay una transferencia con el localizador único
        try {
            WebElement ElementoUnico = au.driver.findElement(CEDIS_UnicaTransferencia);
            unicaPresente = ElementoUnico.isDisplayed();
        } catch (NoSuchElementException e) {
            unicaPresente = false;
        }

        // Lógica de validación
        if (primeraPresente && segundaPresente) {
            System.out.println("Despliegue de ambas transferencias: Correcto");
        } else if (unicaPresente || primeraPresente || segundaPresente) {
            // Si solo hay una transferencia presente, se considera fallo porque deberían ser al menos dos
            baseExecution.addStep("Solo se ha desplegado una transferencia cuando deberían ser al menos dos.");
            Assert.fail("Solo se ha desplegado una transferencia cuando deberían ser al menos dos.");
            testCase.addScreenShotCurrentStep(au, "testImage");
        } else {
            // Si ninguna transferencia está visible, también es fallo
            baseExecution.addStep("No se ha desplegado ninguna transferencia por recepcionar");
            Assert.fail("No se ha desplegado ninguna transferencia por recepcionar");
            testCase.addScreenShotCurrentStep(au, "testImage");
        }
    }
    
    public void seleccionarTransferencia(Boolean sinEscaner) throws InterruptedException, IOException {
        /**
         * Método para seleccionar una transferencia.
         * Primero intenta hacer clic en "CEDIS_UnicaTransferencia". Si no lo encuentra,
         * intentará con "CEDIS_PrimeraTransferencia".
         * @param sinEscaner Si es true, desactiva el escáner antes de seleccionar la transferencia.
         * @author Jesús Marcelo Gutiérrez Bravo
         * @date
         * @throws FrameworkException
         */

        // Si se especifica que no se usará el escáner, desactivarlo
        if (sinEscaner) {
            System.out.println("Activando escáner...");
            Thread.sleep(3000);
            au.click(CEDIS_switchEscaner);
            Thread.sleep(3000);
        }

        // Intentar seleccionar "CEDIS_UnicaTransferencia"
        try {
            System.out.println("Intentando seleccionar 'CEDIS_UnicaTransferencia'...");
            au.scrollToElement(CEDIS_UnicaTransferencia);
            Thread.sleep(3000);
            au.click(CEDIS_UnicaTransferencia);
            System.out.println("Transferencia única seleccionada correctamente.");
        } catch (TimeoutException | NoSuchElementException e) {
            System.out.println("No se encontró 'CEDIS_UnicaTransferencia', intentando con 'CEDIS_PrimeraTransferencia'...");
            
            // Si no encuentra "CEDIS_UnicaTransferencia", intentar con "CEDIS_PrimeraTransferencia"
            try {
                au.scrollToElement(CEDIS_PrimeraTransferencia);
                Thread.sleep(3000);
                au.click(CEDIS_PrimeraTransferencia);
                System.out.println("Primera transferencia seleccionada correctamente.");
            } catch (TimeoutException | NoSuchElementException ex) {
                System.out.println("Error: No se pudo seleccionar ninguna transferencia.");
            }
        }
    }
    
    /**
	 * Método para validar el icono de la primer transferencia en proceso
	 *
	 * Método para validar el despliegue del icono en progreso de la primer transferencia
	 *
	 * @author Jesus Marcelo Gutierrez Bravo
	 * @date   19/02/2025
	 * @throws FrameworkException
	 */
    
    public void ValidacionIconoDePrimerTransferenciaEnProceso() throws IOException, InterruptedException {
  	  //Descripcion: Método para validar el despliegue del icono en progreso de la primer transferencia
  	  //Creado: Jesus Marcelo Gutierrez Bravo
  	  //Fecha Creación: 19-02-2025
  	  Thread.sleep(1000);
  	  if(!au.driver.findElement(CEDIS_Reloj).isEnabled()) {
  		baseExecution.addStep("No se ha desplegado el icono de la primer transferencia en progreso");
        Assert.fail("No se ha desplegado el icono de la primer transferencia en progreso");
        testCase.addScreenShotCurrentStep(au, "testImage");
    	} 
  	  System.out.println("Despliegue del primer icono de transferencia en progreso: Correcto");
    }
    
    /**
	 * Método para validar el icono de la segunda transferencia en proceso
	 *
	 * Método para validar el despliegue del icono en progreso de la segunda transferencia
	 *
	 * @author Jesus Marcelo Gutierrez Bravo
	 * @date   19-02-2025
	 * @throws FrameworkException
	 */
    
    public void ValidacionIconoDeSegundaTransferenciaEnProceso() throws IOException, InterruptedException {
    	  //Descripcion: Método para validar el despliegue del icono en progreso de la segunda transferencia
    	  //Creado: Jesus Marcelo Gutierrez Bravo
    	  //Fecha Creación: 19-02-2025
    	  Thread.sleep(1000);
    	  if(!au.driver.findElement(CEDIS_RelojDos).isEnabled()) {
    		baseExecution.addStep("No se ha desplegado el icono de la segunda transferencia en progreso");
          Assert.fail("No se ha desplegado el icono de la segunda transferencia en progreso");
          testCase.addScreenShotCurrentStep(au, "testImage");
      	} 
    	  System.out.println("Despliegue del segundo icono de transferencia en progreso: Correcto");
      }
    
    /**
	 * Método para validar el icono de dos transferencias en proceso
	 *
	 * Este método valida que dos transferencias estén activas y mostradas correctamente con sus respectivos íconos de "en progreso" 
	 * en caso de error se documenta el fallo en la ejecución y se registra evidencia.
	 *
	 * @author Jesus Marcelo Gutierrez Bravo
	 * @date   19-02-2025
	 * @throws FrameworkException
	 */
    
    public void ValidacionIconoSDeDosTransferenciasEnProceso() throws IOException, InterruptedException {
  	  //Descripcion: Método para validar el despliegue del icono en progreso de la segunda transferencia
  	  //Creado: Jesus Marcelo Gutierrez Bravo
  	  //Fecha Creación: 19-02-2025
  	  Thread.sleep(1000);
  	  
  	if(!au.driver.findElement(CEDIS_RelojUnico).isEnabled()) {
  		baseExecution.addStep("No se ha desplegado el icono de la primer transferencia en progreso");
        Assert.fail("No se ha desplegado el icono de la primer transferencia en progreso");
        testCase.addScreenShotCurrentStep(au, "testImage");
    	} 
  	  
  	  if(!au.driver.findElement(CEDIS_RelojDos).isEnabled()) {
  		baseExecution.addStep("No se ha desplegado el icono de la segunda transferencia en progreso");
        Assert.fail("No se ha desplegado el icono de la segunda transferencia en progreso");
        testCase.addScreenShotCurrentStep(au, "testImage");
    	} 
  	  System.out.println("Despliegue del primer y segundo iconos de transferencias en progreso: Correcto");
    }
    
    /**
	 * Método para validar limite de transferencias
	 *
	 * Este método asegura que se muestre correctamente el mensaje de advertencia cuando se alcanza el limite de transferencias 
	 * si el texto no coincide exactamente con el esperado falla.
	 *
	 * @author Juan Ibarra
	 * @date   05-Mar-2025
	 * @throws FrameworkException
	 */
    
    public void ValidarMensajeLimite() throws InterruptedException{  	
    	/*Funcion que permite validar la descripción del mensaje de Limite de transferencias 	
    	Creado: Juan Ibarra		
    	Fecha Creación: 05-Mar-2025
    	 */    	
    	Thread.sleep(1000);
        WebElement elemento = au.driver.findElement(CEDIS_MensajeLimiteTransferencias);
        String mensajeMostrado = elemento.getText().trim(); // Eliminar espacios al inicio y al final
        // Texto esperado
        String txtWarning = "Se alcanzó el límite de transferencias iniciadas para recepción (máx. 2). Favor de finalizar las transferencias anteriores para continuar.";

        // Normalizar los textos para comparar sin espacios adicionales
        String mensajeMostradoNormalizado = mensajeMostrado.replaceAll("\\s+", " ").trim();
        String txtWarningNormalizado = txtWarning.replaceAll("\\s+", " ").trim();

        // Imprimir mensajes para depuración
        System.out.println("Mensaje mostrado (normalizado): " + mensajeMostradoNormalizado);
        System.out.println("Mensaje esperado (normalizado): " + txtWarningNormalizado);

        // Validar si el mensaje mostrado no coincide con el mensaje esperado
        if (!mensajeMostradoNormalizado.equals(txtWarningNormalizado)) {
        	baseExecution.addStep("El mensaje es incorrecto");        
    		Assert.fail("El mensaje mostrado no coincide con el mensaje esperado, se muestra " +mensajeMostradoNormalizado , null);    		
        } else {
            System.out.println("El mensaje es correcto.");
        }    
 
      }
    
}

     
    
    