package RMS.ProcEndtoEnd.scripts;

import static org.testng.Assert.assertTrue;

import java.sql.SQLException;
import java.util.HashMap;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.Test;

import RMS.ProcEndtoEnd.om.EndtoEnd;
import modelo.BaseExecution;
import utils.password.PasswordUtil;
import utils.selenium.ChromeTest;
import utils.selenium.SeleniumUtil;
import utils.sql.SQLResult;
import utils.sql.SQLUtil;


/** DESCRIPCIÓN: validar que se realice la captura de una orde de compra desde el aplicativo de RMS16
 *               espejo con los mismos productos que la orden original(reple)
 * 
 *   PREREQUISITOS: 
 *                 1.-Acceso al porta de RMS16
 *                 2.-Tener permiso como soporte funcional 
 *                 3.-Contar con acceso a la BD de FCDASPFR Y FCDASPFR
 *  INSUMOS:
 *         - Supplier activados en E2E 
 *          -Cedis Activos
 *          -Tienda Activos
 *          
 *           @author Maria Isabel Rivera 
 *           @date 20/08/2025 
 *           */


@Test 
 public class ATC_FT_014_Crearordenespejoporparcialidad extends BaseExecution {

    /** Utilidades **/
    private SeleniumUtil u;
    private EndtoEnd pom;
    private WebDriver driver;
    
    /**
     * Configuración previa a cada test
     */
    //@BeforeMethod
    //public void setUp() {
       
        //System.setProperty("webdriver.chrome.driver", "C:\\webdriver\\chromedriver-win64\\chromedriver.exe");

        //driver = new ChromeDriver();
        
        //u = new SeleniumUtil(driver);
        //testCase = new TestCase(0, className); 
        //pom = new EndtoEnd(u, testCase); 
    //}

    /**
     * Escenario de prueba que ingresa al sistema RMC
     * @throws InterruptedException 
     * @throws SQLException 
     */
    @Test(dataProvider = "data-provider")
    public void ATC_FT_014_Crearordenespejoporparcialidad_test(HashMap<String, String> data) throws InterruptedException, SQLException {

    	testCase.setProject_Name("ProcesEndtoEnd");
    	 u = new SeleniumUtil(new ChromeTest(),true);
         pom = new EndtoEnd(u, testCase);
      
        
        String usuario = data.get("usuario");
        String contrasena = PasswordUtil.decryptPassword(data.get("contrasena"));
        String linkRMS = data.get("linkRMS");
        WebDriver driver;
        
        String hostRMSDB = data.get("host");
        String passRMSDB = PasswordUtil.decryptPassword(data.get("password"));
        String userRMSDB = data.get("user");

        if (linkRMS == null || linkRMS.isEmpty()) {
            throw new IllegalArgumentException("El linkRMS no está definido en el archivo Excel.");
        }
        
        
   

     addStep("Ingresar al Sistema de RMS V16");
       System.out.println("Abriendo URL: " + linkRMS);
        u.hardWait(1);
        u.get(linkRMS);
        u.getDriver().manage().window().maximize();
        
        
        addStep("Seleccionar 'Seleccionar usuario'");
        pom.clickOption("Menú Usuario", pom.getDropdw());
     
        addStep("Seleccionar 'Seleccionar Conexión'");
        pom.clickOption("Menú Usuario", pom.getOpcionConexion());
        
        System.out.println("usuario: " + usuario);
        System.out.println("contrasena: " + contrasena);
        
        addStep("Ingresar usuario");
        pom.ingresarUsuario(usuario, pom.getInputUsuario());
        
        addStep("Ingresar contrasena");
        pom.ingresarContrasena(contrasena, pom.getInputContrasena());
        
        addStep("Seleccionar boton 'Conexion'");
		pom.clickConexion();
		
		addStep("Seleccionar boton 'Tarea'");
		pom.clickTarea();
		
		addStep("Seleccionar  'Orders'");
		pom.clickOrders();
		
		addStep("Seleccionar  'Crear Order'");
		pom.clickCrearOrden();
		
		addStep("Ingresar supplier site ");
		pom.ingresarSupplierSite();
		
		addStep("Seleccionar OrderType N/B o No básico");
		pom.seleccionarOrderTypeNB();
		
		addStep("Seleccionar 'Seleccionar FechaInicioHoy'");
	    pom.ingresarFechaHoy();
	    
	    addStep("Ingresar fecha 'Not After' +2 días");
	    pom.ingresarFechaFinMasDosDias();
	    
	    addStep("Seleccionar 'Seleccionar campo single Location Order'");
	    pom.marcarCheckboxSbc1();
	    
	    addStep("Seleccionar ubicación 'Warehouse, Almacen'");
	    pom.seleccionarUbicacionPorNombre("Almacén", "Warehouse");
	    	    
	    addStep("Buscar Location con valor 9999999002");
	    pom.ingresarLocation("9999999002");
	    
	    addStep("Confirmar selección con botón OK");
	    pom.clickBotonOK();
	    
        addStep("Seleccionar siguiente fin de semana en calendario");
	    pom.seleccionarProximoFinDeSemana(); 
	    
	    addStep("Ingresar número en PoTypeId");
	    pom.ingresarNumeroPoType30();
	    
	    addStep("Dar clic en botón 'Details'");
	    pom.clickEnDetalles();
	    
	    addStep("Hacer clic en el botón 'Actions'");
	    pom.clickAction();
	    
	    addStep("Hacer clic en el botón 'Agregar'");
	    pom.clickAdd();
	     
	    addStep("Seleccionar 'Articulo'");
	    pom.clickArticulo1(); 
	    
	    addStep("Hacer clic en boton 'Save and Close'");
        pom.clickSaveClose1();
	    
        System.out.println("Antes de dar clic ");
	    addStep("Hacer clic en boton 'Save and Close'");
	    pom.clickSaveClose2(); 
	    	    
	    addStep("Hacer clic en boton 'Submit'");
	    pom.clickSubmit();
	    	    
	    addStep("Hacer clic en boton 'Yes Or Si'");
	    pom.clickConfirmacion1YesoSi1();
	    
	    addStep("Hacer clic en boton 'Approve'");
	    pom.clickApprove();
	    
        addStep("Hacer clic en boton 'Yes Or Si'");
	    pom.clickConfirmacion2YesoSi2();
	    
	    addStep("Hacer clic en boton 'Yes Or Si'");
	    pom.clickConfirmacion2YesoSi3();
	    
	    addStep("Hacer clic en boton 'Guardar y Cerrar'");
	    pom.clickGuardarYCerrar();
	    
    //aqui esta el paso de conexion
	    
	    addStep("Hacer conexion a la BD de RMS");
	    SQLUtil rmsDataBase = new SQLUtil(hostRMSDB,
	    									userRMSDB,
	    									passRMSDB);
	    
	    assertTrue(rmsDataBase.getConn().isValid(10));
	    testCase.addCodeEvidenceCurrentStep("Se realiza conexion a base de datos de RMS");
	    
	    //Consulta a la base
	    addStep("Se manda el query a la DB");
	    SQLResult responseRMS = rmsDataBase.executeQuery("select * from RMS.ORDLOC FETCH FIRST 1 ROWS ONLY ");
	    System.out.println(responseRMS.getFirstData());
	    testCase.addQueryEvidenceCurrentStep(responseRMS);
	    
	    //Cuando termines de usar la DB
	    rmsDataBase.close();
	    
    }

    /** Métodos del framework no implementados **/
    @Override
    public void beforeTest() {
    	// TODO Auto-generated method stub
      // Puedes agregar configuración general antes de los tests
    } 

    @Override
    public String setPrerequisites() {
        return "Acceso al porta de RMS16\\r\\n\"\r\n"
        		+ "        		+ \"Tener permiso como soporte funcional \\r\\n\"\r\n"
        		+ "        		+ \"Contar con acceso a la BD de FCDASPFR Y FCDASPFR";
    }

    @Override
    public String setTestDescription() {
        return "Creacion de Orde de compra para cedis de tipo normal desde RMS16 mediante Selenium.";
    }

    @Override
    public String setTestDesigner() {
        return "maria.rivera@grupotimexico.com";
    }

    @Override
    public String setTestFullName() {
        return "ATC_FT_014_Crearordenespejoporparcialidad";
    }

    @Override
    public String setTestInstanceID() {
        return "RMC-FT-014";
    }
    
    
}
   