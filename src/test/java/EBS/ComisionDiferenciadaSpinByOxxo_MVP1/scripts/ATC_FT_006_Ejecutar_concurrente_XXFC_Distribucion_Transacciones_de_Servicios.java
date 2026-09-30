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
 *Validar el concurrente de distribucion de transacciones 
 *pre requisitos 1.-Contar con acceso y permisos necesarios en EBS
2-Contar con la ejecución previa de los concurrente mencionados 

 * @author Alan Perez
 * @fecha 24/01/25
 * */
 

public class ATC_FT_006_Ejecutar_concurrente_XXFC_Distribucion_Transacciones_de_Servicios extends BaseExecution {

	@Test(dataProvider = "data-provider")
	
	public void ATC_FT_06_Ejecutar_concurrente_XXFC_Distribucion_Transacciones_de_Servicios_test (HashMap<String, String> data) throws Exception {
		//Variables globales
		 String ebsUrl = GlobalVariables.Url_Ebs_Comision;
	        String userName = GlobalVariables.User_EBS_C;
	        String password = GlobalVariables.Password_EBS_C;
	     // Ruta relativa (dentro de la estructura de tu proyecto):
	        File file2 = new File("C:\\Central\\FrameworkEBS\\Patterns\\EBS");

	        
	        //data provider 
	        	 
				String concurrente = data.get("Concurrente"); 
			    String FechaIni = data.get("FechaInicial");
			   String FechaFin = data.get("FechaFin");
			   String Plaza = data.get("Plaza");
			   String tipoDis = data.get("Disribucion");
			   //Servicio 
			   String Servicio = data.get("Texto");
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
      
        assertTrue(paginaInicial.clickOnResponsabilidad("MXOXXO-GL17ADM-SERVICIOS"),"No muestra la responsabilidad");
        
    	testCase.addScreenShotCurrentStep(uSelenium, "Informes");
		
assertTrue(BaseComision.ClickOnCarpeta("Informes"),"No se valida la carpeta informes");
		
		
		assertTrue(BaseComision.ClickOnCarpeta("Solicitar"),"No se valida carpeta solicitar");
		
		assertTrue(BaseComision.ClickOnCarpeta("Standard"),"No se valida carpeta standard");
		
		addStep("Se ejecuta el aplicativo");
	    //Abrir applet abrir applet y marcar casilla
		testCase.addScreenShotCurrentStep("No se marca la casilla correspondiente");
	
	   assertTrue(BaseComision.RunApplet2(file2),"Se valida que no se ejecute correctamente el aplicativo java casilla warning");
	    testCase.addScreenShotCurrentStep("No se ejecuta el aplicativo");
		//Ejecucion applet clic en ejecutar
	  
	    assertTrue(BaseComision.EjecutarApplet(file2),"No se ejecuta la interfaz java");
	    Thread.sleep(7000);
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
	   testCase.addScreenShotCurrentStep(" da clic en boton buscar");
	 
	   assertTrue(BaseComision.BuscarConcurrente(file2),"no se realizó la busqueda");
	   addStep("Se le da los tabs correspondientes para llegar a fecha inicial");
	   BaseComision.tab2(9);
	   testCase.addScreenShotCurrentStep("Se da tab correctamente");
	   
	   
	   BaseComision.Escribir(FechaIni);
	   
	   BaseComision.Escribir(FechaFin);
	
	   BaseComision.Escribir(Servicio);
	   BaseComision.tab2(1);
	
	   BaseComision.Escribir(Plaza);
	
	
	   BaseComision.Escribir(tipoDis);
	   //Tab
	   BaseComision.Enter2(1);
	   //Enter
	   addStep("Se ingresan los parametros correspondientes");
	   testCase.addScreenShotCurrentStep("Se llenan los parametros y se da enter");
    
       assertTrue(BaseComision.EnterParam(1), "No se ingresan los parametros");
	
	
	
	 addStep("Se ejecuta la solicitud");
	   testCase.addScreenShotCurrentStep("Se ejecuta la solicitud");
	
	   assertTrue(BaseComision.EjecutarSolicitud(file2),"No se ejecuta la solicitud");
	   
	  addStep("Se encuentra la solicitud");
	
	   assertTrue(BaseComision.EncontrarSolicitud(file2),"No se encontro la solicitud");
	   Thread.sleep(4000);
	   testCase.addScreenShotCurrentStep("Se encuentra la solicitud correspondiente");
	
	   
	   
	   addStep("Se refresca la data");
	 
	   assertTrue(BaseComision.RefreshData(file2),"No se refresca la data");
	   testCase.addScreenShotCurrentStep("Se refresca la data correctamente");
	   addStep("Se valida el output");
	  
	   assertTrue(BaseComision.ValidarOutput(file2),"No se valida el output");
	   Thread.sleep(5000);
	   testCase.addScreenShotCurrentStep("Se da clic en el output");
	}
	

@Override
public String setTestFullName() {
	return "ATC_FT_06_Ejecutar_concurrente_XXFC_Distribución_Transacciones_de_Servicios";
}

@Override
public String setTestDescription() {
	return "Se Ejecuta el concurrente Distribucion de servicios";
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
