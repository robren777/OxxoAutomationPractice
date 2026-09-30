package RMS.ProcEndtoEnd.scripts;

import java.sql.SQLException;
import java.util.Map;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.Test;

import RMS.ProcEndtoEnd.om.EndtoEnd;
import modelo.BaseExecution;
import utils.password.PasswordUtil;
import utils.selenium.ChromeTest;
import utils.selenium.SeleniumUtil;

/** DESCRIPCIÓN: validar que se realice la captura de una orde de compra desde el aplicativo de RMS16 por carga Masiva TIPO NB
 * 
 *   PREREQUISITOS: 
 *                 1.-Acceso al porta de RMS16
 *                 2.-Tener permiso como soporte funcional 
 *                 3.-Contar con acceso a la BD de FCDASPFR Y FCDASPFR
 *                 4.-Tener un excel con los datos para subir por masiva tipo ods
 *  INSUMOS:
 *         - Supplier activados en E2E 
 *          -Cedis Activos
 *          -Tienda Activos
 *          
 *           @author Maria Isabel Rivera 
 *           @date 05/07/2025 
 *           */


public class ATC_FT_007_CrearOrdendeCompracargamasivaparaCEDIStipoARB extends BaseExecution {

    /** Utilidades **/
    private SeleniumUtil u;
    private EndtoEnd pom;
    private WebDriver driver;
    
    /**
     * Configuración previa a cada test
     */
    //@BeforeMethod
    //public void setUp() {
        
       // System.setProperty("webdriver.chrome.driver", "C:\\webdriver\\chromedriver-win64\\chromedriver.exe");

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
    public void ATC_FT_007_CrearOrdendeCompracargamasivaparaCEDIStipoARB_test(Map<String, String> data) throws InterruptedException, SQLException {

    	testCase.setProject_Name("ProcesEndtoEnd");
        //testCase.setTest_Description("Prueba Automatiza ProcesEndtoEnd");
        //testCase.setTester_Project("Maria Isabel Rivera");
        //testCase.setCurrent_Date("09/08/2025");
    	 u = new SeleniumUtil(new ChromeTest(),true);
         //testCase = new TestCase(0, className); 
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
        
    //testCase.nextStep("Ingresar al Sistema de RMS V16"); 

     addStep("Ingresar al Sistema de RMS V16");
       System.out.println("Abriendo URL: " + linkRMS);
        u.hardWait(1);
        u.get(linkRMS);
        u.getDriver().manage().window().maximize();
        //testCase.addScreenShotCurrentStep("Pantalla de inicio RMS");
        
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
			
		addStep("Seleccionar  'Oxxo'");
		pom.clickOrders2();
		
		addStep("Seleccionar  'OxxoPurchase'");
		pom.clickOxxoPurchase();
			
	    addStep("Seleccionar  'Archivo'");
		pom.subirArchivoCargaMasivaARB();
		
		addStep("Seleccionar 'Archivo'");
		pom.clickBotonUpload();
		
		//addStep("Seleccionar 'Archivo'");
		//pom.clickReviewStatus();
		
		//addStep("Seleccionar 'Orderloads'");
		//pom.clickOrderloads();
		
		addStep("Cierra navengador '");
		pom.cerrarPagina();
				
		
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
        		+ "        		+ \"Contar con acceso a la BD de FCDASPFR Y FCDASPFR."
        		+ "             +  \"Tener un excel con los datos para subir por masiva tipo ARB.ods";
    }

    @Override
    public String setTestDescription() {
        return "Creacion de Orde de compra para cedis de tipo normal desde RMS16 mediante Selenium..";
    }

    @Override
    public String setTestDesigner() {
        return "maria.rivera@grupotimexico.com";
    }

    @Override
    public String setTestFullName() {
        return "ATC_FT_007_CrearOrdendeCompracargamasivaparaCEDIStipoARB";
    }

    @Override
    public String setTestInstanceID() {
        return "RMC-FT-007";
    }
    
    
}
