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
 *Validar el concurrente Reporte de conciliacion FEMCO cartas
 *pre requisitos 1.-Contar con acceso y permisos necesarios en EBS
2-Contar con la ejecución previa de los concurrente mencionados 

 * @author Alan Perez
 * @fecha 24/01/25
 * */

public class ATC_FT_017_Ejecutar_Concurrentes_XXFC_Reporte_de_Conciliacion_FEMCO_Cartas extends BaseExecution {

	@Test(dataProvider = "data-provider")

public void ATC_FT_017_Ejecutar_Concurrentes_XXFC_Reporte_de_Conciliacion_FEMCO_Cartas_test (HashMap<String, String> data) throws Exception{

	//Variables globales
	 String ebsUrl = GlobalVariables.Url_Ebs_Comision;
        String userName = GlobalVariables.User_EBS_C;
        String password = GlobalVariables.Password_EBS_C;
     // Ruta relativa (dentro de la estructura de tu proyecto):
        File file2 = new File(GlobalVariables.PATTERN_EBS+"EBS");

        
        //data provider 
        
		String concurrente = data.get("Concurrente"); 
		
		
    //utileria    
	SeleniumUtil uSelenium = new SeleniumUtil(new InternetExplorerDriver());
	LoginTool inicio = new LoginTool(uSelenium, testCase, ebsUrl, userName, password);
	EBSPaginaInicial paginaInicial = new EBSPaginaInicial(uSelenium, testCase);
	HashMap<String, Pattern> ImgPatter = new HashMap<String, Pattern>();
	SikuliUtil sk = new SikuliUtil();
	PatternUtil pattern = new PatternUtil(sk, uSelenium, null, null, ImgPatter, null, file2);
	
	
	BaseComisionDif BaseComision = new BaseComisionDif(uSelenium, testCase, sk, ImgPatter);
	
	
	//Comienza codigo 
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


	assertTrue(BaseComision.ClickOnCarpeta("Solicitar"),"No encuentra la carpeta Solicitar");

	
	assertTrue(BaseComision.ClickOnCarpeta("Standard"),"No encuentra la carpeta standard");

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
    
    
 
  //cambio de responsabilidad 
    
    assertTrue(BaseComision.CambioResponsabilidad(file2, "GLXX-Super_Usuario.png"),"No se realiza el cambio");

    addStep("Se le da clic en ver y despues en solicitudes");
	   //Se abre el menu ver y Se le da clic al boton de solicitudes 
	   testCase.addScreenShotCurrentStep("Se despliega el menu ver");
	 
    assertTrue(BaseComision.VerMenu_Solicitudes(file2),"No detecta el menu");
    testCase.addScreenShotCurrentStep("Se da clic en solicitudes");
	  
	   assertTrue(BaseComision.EjecutarNuevaSolicitud(file2),"No se ejecuta solicitud");
	   
	 //Se le da clic en busqueda concurrente
	   addStep("Se le da clic en busqueda y se escribe el nombre del concurrente");
	   testCase.addScreenShotCurrentStep("Se da clic en el botón de busqueda concurrente");
	   
	   assertTrue(BaseComision.BusquedaConcurrente(file2),"No busca el concurrente");

	   testCase.addScreenShotCurrentStep("Se escribe en el campo el nombre del concurrente");
	 
		  assertTrue(BaseComision.typeInEBS(file2, concurrente),"No se escribe en ebs el nombre");

	   
	   testCase.addScreenShotCurrentStep("Se da clic en boton buscar");
	  
	   assertTrue(BaseComision.BuscarConcurrente(file2),"no se realizó la busqueda");

	   
	   
	   //parametros
	   //parametro 1 Compañia 99
	   BaseComision.tab2(9);
	   //Escribir compañia
	   BaseComision.Compania("99");
	   BaseComision.tab2(1);
	   //Buscar compañia 99 y clic
	   BaseComision.Compania99Clic(file2);
	   
	   
	   //parametro plaza
	   BaseComision.Plaza("10MON");
	   //Cuenta corriente
	   BaseComision.tab2(1);
	   BaseComision.CuentaCorriente("19EC0007");
	   
	   
	   //tipo de reporte
	   BaseComision.tab2(2);
	  
	   
	   //Tipo de salida
	   BaseComision.TipoDeSalida("D");
	  
	   
	   //fecha
	   BaseComision.FechaPago("NOV-24");
	   BaseComision.tab2(1);
	   
	   BaseComision.KeyDown(2);
	   //Aceptar parametro
	   BaseComision.btnAceptar(file2);
  
	 //Aceptar parametros
		  BaseComision.EnterParam(1);
		  
		  addStep("Se ejecuta la solicitud");
		   testCase.addScreenShotCurrentStep("Se ejecuta la solicitud");
		  
			  assertTrue(BaseComision.EjecutarSolicitud(file2),"No se ejecuta la solicitud"); 

		   
		   addStep("Se encuentra la solicitud");
		  
		   assertTrue(BaseComision.EncontrarSolicitud(file2),"No se encontro la solicitud");

		   Thread.sleep(4000);
		   testCase.addScreenShotCurrentStep("Se encuentra la solicitud correspondiente");
		   
		   addStep("Se refresca la data");
		  
		   assertTrue(BaseComision.RefreshData(file2),"No se refresca la data");
		   Thread.sleep(4000);
		   testCase.addScreenShotCurrentStep("Se refresca la data correctamente");
		   addStep("Se valida el output");
		   
		   assertTrue(BaseComision.ValidarOutput(file2),"No se valida el output");
		   Thread.sleep(4000);
		   testCase.addScreenShotCurrentStep("Se da clic en el output");
		   
		   addStep("Abrir el archivo XLS");
		 
		   assertTrue(BaseComision.AbrirXLS(file2),"No se abre el xls");
		   Thread.sleep(4000);
		   testCase.addScreenShotCurrentStep("Se abre correctamente el archivo xls");
	}

@Override
public String setTestFullName() {
	return "ATC_FT_017_Ejecutar_Concurrentes_XXFC_Reporte_de_Conciliacion_FEMCO_Cartas";
}

@Override
public String setTestDescription() {
	return "Se Ejecuta el concurrente CARTAS FEMCO";
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
