package WEB.portal_Remesas.scripts;

import static org.testng.Assert.assertTrue;

import java.util.HashMap;

import org.openqa.selenium.By;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import WEB.om.PortalApps;
import WEB.portal_Remesas.om.GenerarReporteMontos;
import config.Constants;
import modelo.BaseExecution;
import utils.password.PasswordUtil;
import utils.selenium.MEdgeTest;
import utils.selenium.SeleniumUtil;

public class ATC_FT_023_Remesas_Retiro_Dinero_Operacion_con_Proveedor_Registro_manual_tiendas extends BaseExecution{

	SeleniumUtil u;
	@Test(dataProvider = "data-provider")
	
	public void ATC_FT_023_Remesas_Retiro_Dinero_Operacion_con_Proveedor_Registro_manual_tiendas_Test(HashMap<String, String> data) throws Exception {
		
		/*
		 * Variables
		 ******************************************************************************************/
		String user = data.get("User");
		String password = PasswordUtil.decryptPassword(data.get("Password"));
		String filepath = data.get("File_Path");
		
//		String mngAlerta = "Usuario y/o Contrase�a Invalidos";

		String proveedor = data.get("Proveedor");
//		String descargas = data.get("Descargas");
//		String cascaron = data.get("Cascaron");
//		String open = data.get("Open");
//		By xmlArchivoCargado = By.xpath("//*[@id=\"frmAltaManual:acordeon:uploader\"]/div[2]/table/tbody/tr/td[2]");

		//                                 PASO 1
		addStep(" Ingresar a la pesta�a de Retiro de Dinero, Operacion con Proveedor ");
		
		SeleniumUtil u = new SeleniumUtil(new MEdgeTest(), true);  // para usar navegador Edge
		u.getDriver().manage().window().maximize();
		PortalApps pa = new PortalApps(u, testCase);  // crea objeto de locator
		u.get(data.get("Url"));    //toma el valor Url del data provider		
		u.hardWait(3);           // tiempo en segundos
		
		pa.proceedToPortal();    // es para lo de la pagina no segura			
		testCase.addScreenShotCurrentStep(u, "capturaInicioSesion");
		
		pa.cicloLogin(user, password);
	
		GenerarReporteMontos rm = new GenerarReporteMontos(u, testCase);
		rm.clickSeleccionarPortalRemesas();


		rm.clickOperacionConProveedor();
		
		//                                 PASO 2	
		addStep(" Seleccionar de la lista el proveedor MoneyGram ");
		addStep("Seleccionar en el enlace Registro manual de tiendas");	
		addStep("Dar click en Generar manualmente el n�mero de tiendas	"); //Este paso abarca los pasos 2, 3 y 4
		rm.clickSeleccionProveedor(proveedor);

        //                                  PASO 5
		addStep(" Presionar Examinar ");
		addStep(" Seleccionar un archivo de formato Excel ");
		addStep(" Presionar Abrir. Carga el archivo correctamente ");//este paso abarca los pasos 5, 6 y 7
		
		//try {
			
			filepath = Constants.BASE_PATH + "\\" + filepath;	
			System.out.print(filepath);
			By uploadInput = By.xpath("//input[@id='frmAltaManual:acordeon:uploader_input']");
			u.scrollToElement(By.xpath("//div[@id='frmAltaManual:acordeon:uploader']"));
			u.highLight(uploadInput);
			testCase.addScreenShotCurrentStep(u, "cargarArchivo");
			u.sendKeys(uploadInput, filepath);
			testCase.addScreenShotCurrentStep(u, "cargarArchivo");
			//u.highLight(By.xpath("//*[@id=\"frmAltaManual:acordeon:uploader\"]//table//button"));
			
		/*}catch(Exception e) {		    
			testCase.addScreenShotCurrentStep(u, "Error");
			Assert.fail();
		}*/

		filepath = filepath.replace("\\", "-");	
		String [] cadena = filepath.split("-");
		System.out.print(cadena[cadena.length-1]);
		boolean validarCarga = rm.validarCargaArchivo(cadena[7]);
		
		assertTrue(validarCarga, "No se cargo el archivo");

		
	}
	@Override
	public void beforeTest() {
	}

	@Override
	public String setTestDescription() {
		return "ATC_FT_023_Remesas_Retiro_Dinero_Operacion_con_Proveedor_Registro_manual_tiendas_Test";
	}

	@Override
	public String setTestDesigner() {
		return "AutomationQA";
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_023_Remesas_Retiro_Dinero_Operacion_con_Proveedor_Registro_manual_tiendas_Test";
	}

	@Override
	public String setTestInstanceID() {
		return null;
	}

	@Override
	public String setPrerequisites() {
		return null;
	}
	
	@AfterMethod
	public void afterMethod() {
//		u.getDriver().quit();
	}


}
