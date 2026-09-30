
package EBS.cfeEnLinea.scripts;

//
import java.io.File;
import java.util.HashMap;

import org.openqa.selenium.chrome.ChromeDriver;
import org.sikuli.script.Pattern;
import org.testng.Assert;
import org.testng.annotations.Test;

import EBS.cfeEnLinea.om.BaseCfeEnLinea;
import EBS.tools.LoginTool;
import EBS.util.GlobalVariables;
import modelo.BaseExecution;
import utils.SikuliUtil;
import utils.selenium.SeleniumUtil;





/**
* DESCRIPCIÓN: Se ejecuta el concurrente generacion de factura cobro de servicio 
* 
* NOMBRE CASOS DE PRUEBA MANUAL QUE SE ABARCAN: 
* 	-MTC_FT_138_Ejecutar_Concurrente_XXFC_Generacion_de_factura_por_cobro_de_servicio
*	
* 
* PREREQUISITOS:
* 	- Tener instalado el JInitiator
* 	- Acceso al aplicativo EBS
* 
* 
* INSUMOS:
* 	-Usuario EBS
*   -Contraseña EBS
*   -URL EBS
*   -Nombre concurrente 
* 
* @author Alan Perez 
* @date 20/Octubre/2025
*/

public class  ATC_FT_138_EjecutarConcurrenteXXFCGeneracionDeFacturaPorCobroDeServicio extends BaseExecution{

	@Test(dataProvider = "data-provider")
	public void ATC_FT_138_EjecutarConcurrenteXXFCGeneracionDeFacturaPorCobroDeServicio_test (HashMap<String, String> data) throws Exception {
		
		String concurrente = data.get("Concurrente");
		
				// Variables globales
				String ebsUrl = GlobalVariables.Url_EBS_CFE;
				String userName = GlobalVariables.User_EBS_CFE_Web;
				String password = GlobalVariables.Password_EBS_CFE_Web;
				
			
				// Ruta relativa (dentro de la estructura de tu proyecto
				File file2 = new File(System.getProperty("user.dir") + "\\Patterns\\EBS");

				// utileria
				SeleniumUtil uSelenium = new SeleniumUtil(new ChromeDriver());
				LoginTool inicio = new LoginTool(uSelenium, testCase, ebsUrl, userName, password);
				HashMap<String, Pattern> ImgPatter = new HashMap<String, Pattern>();
				SikuliUtil sk = new SikuliUtil();
				BaseCfeEnLinea BaseCfe = new BaseCfeEnLinea(uSelenium, testCase, sk, ImgPatter);
				
				
				
				
			
				
		addStep("Se ingresa a la página de EBS e inicia sesion");
		Assert.assertTrue(inicio.login());

		
		addStep("Dar clic sobre la liga \"MXOXXO-GLXXXXX-Super Usuario","  y  después dar clic dar clic sobre ejecutar");
        Assert.assertTrue(BaseCfe.clickOnResponsabilidad("MXOXXO-GLXXXXX-Super Usuario"),"No existe la responsabilidad");

	    addStep("Se da clic en carpeta consulta");
		Assert.assertTrue(BaseCfe.ClickOnCarpeta("Consulta"),"No encuentra la carpeta request");
		
		addStep("Se da clic en la carpeta cuenta");
		Assert.assertTrue(BaseCfe.ClickOnCarpeta("Cuenta"),"No encuentra la carpeta Financial");
		 
		addStep("Se da clicen abrir archivo");
		Assert.assertTrue(BaseCfe.AbrirArchivo());
	
		addStep("Se da clic en ejecutar archivo");
		Assert.assertTrue(BaseCfe.EjecutarArchivoJava());
		
		addStep("Se ejecuta la aplicacion java y carga interfaz EBS");
		Assert.assertTrue(BaseCfe.runApplet3(file2));
		
		
		addStep("Se cierra la ventana activa");
		Assert.assertTrue(BaseCfe.closeActiveWindow());
	    
	    
	    addStep("Se le da clic en ver y despues en solicitudes");
	    Assert.assertTrue(BaseCfe.VerMenu_Solicitudes(file2),"No se valida el menu de ver solicitudes");
		
	
	    
	    addStep("Se da clic en nueva solicitud");
	    Assert.assertTrue(BaseCfe.EjecutarNuevaSolicitud(file2),"No se ejecuta nueva solicitud");
		
	    addStep("Se da clic en el campo nombre EBS");
		Assert.assertTrue(BaseCfe.CampoNombreEBS(file2));
	    
		addStep("Se da clic e ingresa texto XXFC");
	    Assert.assertTrue(BaseCfe.TypeXXFC());
	    
	    
	    addStep("Escribe el nombre del concurrente");
		Assert.assertTrue(BaseCfe.typeInEBS(file2, concurrente),"No se escribe en ebs el nombre");
	   
		
		
		addStep("Se da clic en buscar concurrente");
	    Assert.assertTrue(BaseCfe.BuscarConcurrente(2),"no se realizó la busqueda");
		
	    addStep("Se da clic en aceptar concurrente");
	    Assert.assertTrue(BaseCfe.BtnAceptarConcu(),"No se dio clic en aceptar");	    
	    
	    addStep("Se da clic en Aceptar y ejecutar");
	    Assert.assertTrue(BaseCfe.btnAceptarYEjecutar(),"No se dio clic en aceptar ni ejecutar");
	    
	    addStep("Se da clic en encontrar solicitud");
	    Assert.assertTrue(BaseCfe.EncontrarSolicitud(file2),"No se encontro la solicitud");

	   
	 
	
		
	}



@Override
public String setTestFullName() {
	return "ATC_FT_138_Ejecutar_Concurrente_XXFC_Generacion_de_factura_por_cobro_de_servicio_test";
}

@Override
public String setTestDescription() {
	return "Se Ejecuta el concurrente Generación de factura por cobro";
}

@Override
public String setTestDesigner() {
	return "Alan Perez";
}

@Override
public String setTestInstanceID() {
	return null;
}

@Override
public String setPrerequisites() {
	return "Credenciales vigentes al portal de ebs";
}

@Override
public void beforeTest() {
// TODO Auto-generated method stub
}

}
