package EBS.ComisionDiferenciadaSpinByOxxo_MVP1.scripts;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.util.HashMap;

import org.openqa.selenium.ie.InternetExplorerDriver;
import org.sikuli.script.Pattern;
import org.testng.annotations.Test;

import EBS.ComisionDiferenciadaSpinByOxxo_MVP1.om.BaseComisionDif;
import EBS.tools.LoginTool;
import EBS.util.GlobalVariables;
import EBS.util.pageObjectModel.EBSPaginaInicial;
import EBS.util.patternObject.PatternUtil;
import modelo.BaseExecution;
import utils.SikuliUtil;
import utils.selenium.SeleniumUtil;





/*
 *Validar el concurrente reporte para pago de servicios nacionales y corresponsalias
 *pre requisitos 1.-Contar con acceso y permisos necesarios en EBS
2-Contar con la ejecución previa de los concurrente mencionados 

 * @author Alan Perez
 * @fecha 24/01/2025
 * */

public class ATC_FT_008_Ejecutar_Concurrente_XXFC_Reporte_para_pago_de_Servicios_Nacionales_y_Corresponsalias extends BaseExecution {

	@Test(dataProvider = "data-provider")
	
	public void ATC_FT_008_Ejecutar_Concurrente_XXFC_Reporte_para_pago_de_Servicios_Nacionales_y_Corresponsalias_test  (HashMap<String, String> data) throws Exception{
		
		        //Variables globales
		        String ebsUrl = GlobalVariables.Url_Ebs_Comision;
	            String userName = GlobalVariables.User_EBS_C;
	            String password = GlobalVariables.Password_EBS_C;
	            // Ruta relativa (dentro de la estructura de tu proyecto):
	            File file2 = new File("C:\\Central\\FrameworkEBS\\Patterns\\EBS");
                int tab = 1; 
	            int cantidadEnter = 4;
	            //data provider 
				String concurrente = data.get("Concurrente"); 
				String TextoEscribir = data.get("Texto");
				
				//utileria    
				SeleniumUtil uSelenium = new SeleniumUtil(new InternetExplorerDriver());
				LoginTool inicio = new LoginTool(uSelenium, testCase, ebsUrl, userName, password);
				EBSPaginaInicial paginaInicial = new EBSPaginaInicial(uSelenium, testCase);
				HashMap<String, Pattern> ImgPatter = new HashMap<String, Pattern>();
				SikuliUtil sk = new SikuliUtil();
				PatternUtil pattern = new PatternUtil(sk, uSelenium, null, null, ImgPatter, null, file2);

				BaseComisionDif BaseComision = new BaseComisionDif(uSelenium, testCase, sk, ImgPatter);
				
				System.out.println(
						"*****************************************PASO 1 y 2 Login************************************************");
				addStep("Se ingresa a la página de EBS");
				
				assertTrue(inicio.login(),"No se realiza el inicio de sesion");

				
				addStep("Iniciar sesion con el usuario y contraseña al portal de EBS");	
				testCase.addScreenShotCurrentStep(uSelenium, "Portal de EBS");
				
				System.out.println(
						"*****************************************PASO 3************************************************");
				
				addStep("Dar clic sobre la liga \"MXOXXO-GL17ADM-SERVICIOS: Informes: Solicitar: Standard\"  y  después dar clic dar clic sobre ejecutar");
		       
		        assertTrue(paginaInicial.clickOnResponsabilidad("MXOXXO-GL17ADM-SERVICIOS"),"No existe la responsabilidad");

		        
		    	testCase.addScreenShotCurrentStep(uSelenium, "Informes");
				
				assertTrue(BaseComision.ClickOnCarpeta("Informes"),"No encuentra la carpeta informes");

				
				
				assertTrue(BaseComision.ClickOnCarpeta("Solicitar"),"No encuentra la carpeta Financial");

				
				assertTrue(BaseComision.ClickOnCarpeta("Standard"),"No encuentra la carpeta Financial");

				
				addStep("Se ejecuta el aplicativo");
			    //Abrir applet abrir applet y marcar casilla
				testCase.addScreenShotCurrentStep("Se marca la casilla correspondiente");
			
				   assertTrue(BaseComision.RunApplet2(file2),"Se valida que no se ejecute correctamente el aplicativo java casilla warning");

			    
			    testCase.addScreenShotCurrentStep("Se ejecuta el aplicativo");
				//Ejecucion applet clic en ejecutar
			   
			    assertTrue(BaseComision.EjecutarApplet(file2),"No se ejecuta la interfaz java");

			    Thread.sleep(10000);
			    //Se cierra ventana activa 
			    pattern.closeActiveWindow();
			   
			   
			   addStep("Se le da clic en ver y despues en solicitudes");
			   //Se abre el menu ver y Se le da clic al boton de solicitudes 
			   testCase.addScreenShotCurrentStep("Se despliega el menu ver");
			  
			   assertTrue(BaseComision.VerMenu_Solicitudes(file2),"No se valida el menu de ver solicitudes");

			   
			   testCase.addScreenShotCurrentStep("Se da clic en solicitudes");
			  
			   assertTrue(BaseComision.EjecutarNuevaSolicitud(file2),"No se ejecuta nueva solicitud");

			   
			   
			   
			   //Se le da clic en busqueda concurrente
			   addStep("Se le da clic en busqueda y se escribe el nombre del concurrente");
			   testCase.addScreenShotCurrentStep("Se da clic en el botón de busqueda concurrente");
			  
			   assertTrue(BaseComision.BusquedaConcurrente(file2),"No busca el concurrente");

			   testCase.addScreenShotCurrentStep("Se escribe en el cambo el nombre del concurrente");
			  
				  assertTrue(BaseComision.typeInEBS(file2, concurrente),"No se escribe en ebs el nombre");

			   
			   testCase.addScreenShotCurrentStep("Se da clic en boton buscar");
			   
			   assertTrue(BaseComision.BuscarConcurrente(file2),"no se realizó la busqueda");

			   
			   //Tab escribe servicio
			   BaseComision.TabAndType(tab, TextoEscribir);
				
			   BaseComision.Enter(cantidadEnter);
			   
			   uSelenium.hardWait(3);
			 
			   BaseComision.tab(1);
			   BaseComision.Enter(2);
			   
			
			   
			   
			   addStep("Se encuentra la solicitud");
			   
			   assertTrue(BaseComision.EncontrarSolicitud(file2),"No se encontro la solicitud");

			   Thread.sleep(4000);
			   testCase.addScreenShotCurrentStep("Se encuentra la solicitud correspondiente");
			  
	}
	
	
	
	

@Override
public String setTestFullName() {
	return "ATC_FT_008_Ejecutar_Concurrente_XXFC_Reporte_para_pago_de_Servicios_Nacionales_y_Corresponsalias_test";
}

@Override
public String setTestDescription() {
	return "Se Ejecuta el concurrente Reporte para pago de Servicios Nacionales y Corresponsalias";
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
