package EBS.MVP2_RST.om;

import java.awt.AWTException;
import java.awt.Desktop;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Comparator;

import org.sikuli.api.robot.Key;
import org.sikuli.script.FindFailed;
import org.sikuli.script.Pattern;
import org.testng.Assert;

import EBS.util.pageObjectModel.EBSLogin;
import modelo.TestCase;
import utils.SikuliUtil;
import utils.selenium.SeleniumUtil;
import utils.sql.SQLResult;


/**
 * DESCRIPCION: Clase de utileria para manejo de ERST MVP2 EBS.
 * 
 * @author Roberto Renteria
 * @date 24/MARZO/2025
 */
public class mvp2ErstEBS {
	
	EBSLogin inicio;
	String url;
	String userName;
	String password;
	SeleniumUtil u;
	TestCase testCase;
	SikuliUtil sk;
	String pathImg;
	File FolderPath;
	BaseComisionDif bc;

	public mvp2ErstEBS(SeleniumUtil u, TestCase testCase) {
		this.u = u;
		this.testCase = testCase;
		this.sk = new SikuliUtil();
		this.pathImg = sk.PatternPath("EBS\\"+"EBS");
		this.FolderPath = new File(pathImg);
	}
	
	
	/**
	 * Método para abrir un aplicativo de oracle EBS Java usando Edge
	 * @author Roberto Rentería
	 * @throws Exception 
	 * @date 24/Marzo/2025
	 */	
	public void abrirAppletEdge() throws Exception {
		
		try {
		Pattern btnconservar = new Pattern(FolderPath +"\\btnConservarEdge.png").similar((float).75);
		Pattern frmservlet = new Pattern(FolderPath +"\\abrirArchivoEdge.png").similar((float).75);
		Pattern aceptoLosRiesgosCheckbox = new Pattern(FolderPath +"\\aceptoLosRiesgosCheckbox.png").similar((float).75);
		Pattern ejecutarW11 = new Pattern(FolderPath +"\\ejecutarW11.png").similar((float).75);
		
		if (sk.screen.exists(btnconservar,20)!=null) {
			sk.screen.click(btnconservar);	
		}
		
		if (sk.screen.exists(frmservlet,20)!=null) {
			sk.screen.click(frmservlet);	
		}
		
		if (sk.screen.exists(aceptoLosRiesgosCheckbox,20)!=null) {
			sk.screen.click(aceptoLosRiesgosCheckbox);	
		}
		
		if (sk.screen.exists(ejecutarW11,20)!=null) {
			sk.screen.click(ejecutarW11);	
		}
		}catch(Exception e) {
			testCase.addCodeEvidenceCurrentStep("Error: " + e.getMessage());
			throw new Exception("Error: " + e.getMessage());
		}
		
	}
	
	/**
	 * Método para abrir un aplicativo de oracle EBS Java usando Chrome
	 * @author Roberto Rentería
	 * @throws Exception 
	 * @date 24/Marzo/2025
	 */	
	public void abrirAppletChrome() throws Exception {
		
		try {
		Pattern btnconservar = new Pattern(FolderPath +"\\btnConservarChrome.png").similar((float).80);
		Pattern contDesc = new Pattern(FolderPath +"\\continuarDescargaChrome.png").similar((float).75);
		Pattern descSinVer = new Pattern(FolderPath +"\\descargarArchivosinVerificar.png").similar((float).75);
		Pattern aceptoLosRiesgosCheckbox = new Pattern(FolderPath +"\\aceptoLosRiesgosCheckbox.png").similar((float).75);
		Pattern ejecutarW11 = new Pattern(FolderPath +"\\ejecutarW11.png").similar((float).75);	
		
		
		if (sk.screen.exists(btnconservar,20)!=null) {
			sk.screen.click(btnconservar);	
		}
		
		if (sk.screen.exists(contDesc,20)!=null) {
			sk.screen.click(contDesc);	
		}
		
		if (sk.screen.exists(descSinVer,20)!=null) {
			sk.screen.click(descSinVer);	
		}
		
		String home = System.getProperty("user.home");
		File directoryDownloads = new File(home+"/Downloads");
		
		File latestFile = this.getLastModifiedFile(directoryDownloads);
		
		u.hardWait(5);
		Desktop.getDesktop().open(latestFile);
		u.hardWait(5);
					
		
		if (sk.screen.exists(aceptoLosRiesgosCheckbox,30)!=null) {
			sk.screen.click(aceptoLosRiesgosCheckbox);	
		}
		
		if (sk.screen.exists(ejecutarW11,20)!=null) {
			sk.screen.click(ejecutarW11);	
		}
		}catch(Exception e) {
			testCase.addCodeEvidenceCurrentStep("Error: " + e.getMessage());
			throw new Exception("Error: " + e.getMessage());
		}
		
	}
	
	/**
	 * Método para obtener el nombre del ultimo archivo de determinado directorio
	 * @author Roberto Rentería
	 * @param directory - file con la ruta del folder a buscar
	 * @throws Exception 
	 * @date 24/Marzo/2025
	 */	
	public File getLastModifiedFile(File directory) throws Exception {
	try {
	    File[] files = directory.listFiles();
	   if (files.length == 0) return null;
	    Arrays.sort(files, new Comparator<File>() {
	        @SuppressWarnings("deprecation")
			public int compare(File o1, File o2) {
	            return new Long(o2.lastModified()).compareTo(o1.lastModified()); 
	        }});
	    return files[0];
	}catch(Exception e) {
		testCase.addCodeEvidenceCurrentStep("Error: " + e.getMessage());
		throw new Exception("Error: " + e.getMessage());
	}
	}
	
	/**
	 * Método para dar clic en el boton Cancelar Solicitud
	 * @author Roberto Rentería
	 * @throws Exception 
	 * @date 24/Marzo/2025
	 */	
	public void clickCancelar() throws Exception {
	try {
		Pattern btnCancelar = new Pattern(FolderPath +"\\cancelarsolicitud.png").similar((float).75);
		
		if (sk.screen.exists(btnCancelar,20)!=null) {
			testCase.addScreenShotCurrentStep("");
			sk.screen.click(btnCancelar);	
		}
	}catch(Exception e) {
		testCase.addCodeEvidenceCurrentStep("Error: " + e.getMessage());
		throw new Exception("Error: " + e.getMessage());
	}
	}
	
	/**
	 * Método para dar clic en el boton Aceptar forma de pago
	 * @author Roberto Rentería
	 * @throws Exception 
	 * @date 24/Marzo/2025
	 */	
	public void aceptarFormaPago() throws Exception {
	try {
		Pattern btnAceptar = new Pattern(FolderPath +"\\aceptararchivo.png").similar((float).75);
		
		if (sk.screen.exists(btnAceptar,20)!=null) {
			testCase.addScreenShotCurrentStep("");
			sk.screen.click(btnAceptar);	
		}
	}catch(Exception e) {
		testCase.addCodeEvidenceCurrentStep("Error: " + e.getMessage());
		throw new Exception("Error: " + e.getMessage());
	}
	}
	
	/**
	 * Método para dar clic en el menu Ver
	 * @author Roberto Rentería
	 * @throws Exception 
	 * @date 24/Marzo/2025
	 */	
	public void verMenuSol() throws Exception {
		try {
		Pattern btnVer = new Pattern(FolderPath +"\\ver.png").similar((float).75);
		Pattern solicitudes = new Pattern(FolderPath +"\\solicitudes.png").similar((float).75);
		
		if (sk.screen.exists(btnVer,20)!=null) {
			sk.screen.click(btnVer);	
		}
		
		if (sk.screen.exists(solicitudes,20)!=null) {
			testCase.addScreenShotCurrentStep("");
			sk.screen.click(solicitudes);	
		}
		}catch(Exception e) {
			testCase.addCodeEvidenceCurrentStep("Error: " + e.getMessage());
			throw new Exception("Error: " + e.getMessage());
		}
	}
	

	/**
	 * Método para escribir el nombre del concurrente a buscar
	 * @author Roberto Rentería
	 * @date 24/Marzo/2025
	 * @param concurrente - texto con el nombre del concurrente a buscar
	 * @throws Exception 
	 */	
	public void typeConcurrente(String concurrente) throws Exception {
		try {
		
		Pattern txtbox_Nombre = new Pattern(FolderPath +"\\nombreclick.png").similar((float).75);
			
		if (sk.screen.exists(txtbox_Nombre,20)!=null) {
			sk.screen.click(txtbox_Nombre);
			u.hardWait(5);
			//sk.screen.type(concurrente);
			sk.screen.paste(concurrente);
			testCase.addScreenShotCurrentStep("");
		}
		}catch(Exception e) {
			testCase.addCodeEvidenceCurrentStep("Error: " + e.getMessage());
			throw new Exception("Error: " + e.getMessage());
		}
	}
	
	/**
	 * Método para dar clic en el botón Ejecutar
	 * @author Roberto Rentería
	 * @throws Exception 
	 * @date 24/Marzo/2025
	 */	
	public void clickEjecutar() throws Exception {
		try {
		Pattern btnEjecutarEBS = new Pattern(FolderPath +"\\btnEjecutarEBS.png").similar((float).75);
		
		if (sk.screen.exists(btnEjecutarEBS,20)!=null) {
			sk.screen.click(btnEjecutarEBS);
			testCase.addScreenShotCurrentStep("");
		}
		}catch(Exception e) {
			testCase.addCodeEvidenceCurrentStep("Error: " + e.getMessage());
			throw new Exception("Error: " + e.getMessage());
		}
	}
	
	/**
	 * Método para dar clic en el botón Aceptar concurrente
	 * @author Roberto Rentería
	 * @throws Exception 
	 * @date 24/Marzo/2025
	 */	
	public void aceptarConcurrente() throws Exception {
		try {
		Pattern aceptar = new Pattern(FolderPath +"\\aceptarFormaPago.png").similar((float).75);
		
		if (sk.screen.exists(aceptar,20)!=null) {
			testCase.addScreenShotCurrentStep("");
			sk.screen.click(aceptar);	
		}
		}catch(Exception e) {
			testCase.addCodeEvidenceCurrentStep("Error: " + e.getMessage());
			throw new Exception("Error: " + e.getMessage());
		}
	}
	
	/**
	 * Método para dar clic en el boton cerrar run reports
	 * @author Mariana Avitia
	 * @throws Exception 
	 * @date 26/Marzo/2025
	 */	
	public void clickCerrar() throws Exception {
	try {
		u.hardWait(2);
		Pattern btnCerrar = new Pattern(FolderPath +"\\closeRunReports.png").similar((float).75);
		
		if (sk.screen.exists(btnCerrar,20)!=null) {
			testCase.addScreenShotCurrentStep("");
			sk.screen.wait(btnCerrar).highlight(2, "red");
			sk.screen.click(btnCerrar);	
		}
	}catch(Exception e) {
		testCase.addCodeEvidenceCurrentStep("Error: " + e.getMessage());
		throw new Exception("Error: " + e.getMessage());
	}
	}
	
	/**
	 * Método para escribir el nombre de la forma de pago
	 * @author Roberto Rentería
	 * @date 24/Marzo/2025
	 * @param fp - texto con el nombre de la forma de pago a buscar
	 * @throws Exception 
	 */	
	public void typeFormaPago(String fp) throws Exception {
		try {
		Pattern formaPago = new Pattern(FolderPath +"\\formaPagoRST.png").similar((float).75);
		
		if (sk.screen.exists(formaPago,20)!=null) {
			sk.screen.type(fp);
			testCase.addScreenShotCurrentStep("");
		}
		}catch(Exception e) {
			testCase.addCodeEvidenceCurrentStep("Error: " + e.getMessage());
			throw new Exception("Error: " + e.getMessage());
		}
	}
	
	/**
	 * Método para escribir la fecha inicio
	 * @author Roberto Rentería
	 * @throws Exception 
	 * @date 24/Marzo/2025
	 */	
	public void typeFechaIni(String date1) throws Exception {
		try {
		u.hardWait(2);
		sk.screen.type(Key.TAB);
		sk.screen.type(date1);
		u.hardWait(4);
		testCase.addScreenShotCurrentStep("");
		}catch(Exception e) {
			testCase.addCodeEvidenceCurrentStep("Error: " + e.getMessage());
			throw new Exception("Error: " + e.getMessage());
		}
		
	}
	
	/**
	 * Método para escribir la fecha final
	 * @author Roberto Rentería
	 * @throws Exception 
	 * @date 24/Marzo/2025
	 */	
	public void typeFechaFinal(String date1) throws Exception {
		try {
		u.hardWait(2);
		sk.screen.type(Key.TAB);
		sk.screen.type(date1);
		testCase.addScreenShotCurrentStep("");
		}catch(Exception e) {
			testCase.addCodeEvidenceCurrentStep("Error: " + e.getMessage());
			throw new Exception("Error: " + e.getMessage());
		}
		
	}
	
	/**
	 * Método para obtener la fecha actual
	 * @author Roberto Rentería
	 * @throws Exception 
	 * @date 24/Marzo/2025
	 */	
	public String getCurrentDate() throws Exception {
		try {
		String PATTERN="dd-MMM-yyyy";
		SimpleDateFormat dateFormat=new SimpleDateFormat();
		dateFormat.applyPattern(PATTERN);
		String date1=dateFormat.format(Calendar.getInstance().getTime());
		
		return date1;
		}catch(Exception e) {
			testCase.addCodeEvidenceCurrentStep("Error: " + e.getMessage());
			throw new Exception("Error: " + e.getMessage());
		}
		
	}
	
	/**
	 * Método para escribir las fechas en el reporte de evidencias
	 * @author Roberto Rentería
	 * @throws Exception 
	 * @date 24/Marzo/2025
	 */	
	public void validateFechas() throws Exception {
		try {
		String date1 = this.getCurrentDate();
		
		testCase.addCodeEvidenceCurrentStep("La fecha a considerar es la del dia de hoy: " + date1);
		}catch(Exception e) {
			testCase.addCodeEvidenceCurrentStep("Error: " + e.getMessage());
			throw new Exception("Error: " + e.getMessage());
		}
	}
	
	/**
	 * Método para dar clic en el botón Ejecutar al finalizar el llenado
	 * @author Roberto Rentería
	 * @throws Exception 
	 * @date 24/Marzo/2025
	 */	
	public void clicEjecutar() throws Exception {
		try {
		u.hardWait(3);
		Pattern ejecutar = new Pattern(FolderPath +"\\ejecutar.png").similar((float).75);
		
		if (sk.screen.exists(ejecutar,20)!=null) {
			sk.screen.click(ejecutar);	
		}
		}catch(Exception e) {
			testCase.addCodeEvidenceCurrentStep("Error: " + e.getMessage());
			throw new Exception("Error: " + e.getMessage());
		}
	}
	
	/**
	 * Método para dar clic en el botón No
	 * @author Roberto Rentería
	 * @throws Exception 
	 * @date 24/Marzo/2025
	 */	
	public void clicNo() throws Exception {
		try {
		Pattern no = new Pattern(FolderPath +"\\no.png").similar((float).75);
		
		if (sk.screen.exists(no,20)!=null) {
			testCase.addScreenShotCurrentStep("Click btn No");
			sk.screen.click(no);	
		}
		}catch(Exception e) {
			testCase.addCodeEvidenceCurrentStep("Error: " + e.getMessage());
			throw new Exception("Error: " + e.getMessage());
		}
	}
	
	/**
	 * Método para dar clic en el botón Encontrar
	 * @author Roberto Rentería
	 * @throws Exception 
	 * @date 24/Marzo/2025
	 */	
	public void clicEncontrar() throws Exception {
		try {
		Pattern encontrar = new Pattern(FolderPath +"\\encontrar.png").similar((float).75);
		
		if (sk.screen.exists(encontrar,20)!=null) {
			sk.screen.click(encontrar);	
			testCase.addScreenShotCurrentStep("Click botón Find");
		}
		}catch(Exception e) {
			testCase.addCodeEvidenceCurrentStep("Error: " + e.getMessage());
			throw new Exception("Error: " + e.getMessage());
		}
	}
	
	/**
	 * Método para dar clic en el botón Refrescar Datos
	 * @author Roberto Rentería
	 * @throws Exception 
	 * @date 24/Marzo/2025
	 */	
	public void clicRefrescar() throws Exception {
		try {
		Pattern refresh = new Pattern(FolderPath +"\\RefrescarDatos_EBS.png").similar((float).75);
		u.hardWait(17);
		
		if (sk.screen.exists(refresh,20)!=null) {
			sk.screen.click(refresh);	
			testCase.addScreenShotCurrentStep("");
		}
		}catch(Exception e) {
			testCase.addCodeEvidenceCurrentStep("Error: " + e.getMessage());
			throw new Exception("Error: " + e.getMessage());	
		}
	}
	
	/**
	 * Método para dar clic en el botón Visualizar Registro
	 * @author Roberto Rentería
	 * @throws Exception 
	 * @date 24/Marzo/2025
	 */	
	public void clicVRegistro() throws Exception {
		try {
		u.hardWait(4);
		Pattern vreg = new Pattern(FolderPath +"\\visualizarregistro.png").similar((float).75);
		if (sk.screen.exists(vreg,20)!=null) {
			sk.screen.click(vreg);	
			testCase.addScreenShotCurrentStep("");
		}
		}catch(Exception e) {
			testCase.addCodeEvidenceCurrentStep("Error: " + e.getMessage());
			throw new Exception("Error: " + e.getMessage());
		}
	}
	
	/**
	 * Método para obtener el log al visualizar el registro
	 * @author Roberto Rentería
	 * @throws Exception 
	 * @date 24/Marzo/2025
	 */	
	public void getLog() throws Exception {
		
		try {
		u.hardWait(7);
		
		sk.screen.type("a",Key.CTRL);
		
		u.hardWait(5);
		
		sk.screen.type("c",Key.CTRL);
		
		u.hardWait(3);
		Clipboard c = Toolkit.getDefaultToolkit().getSystemClipboard();
		testCase.addCodeEvidenceCurrentStep(c.getData(DataFlavor.stringFlavor).toString());
		}catch(Exception e) {
			testCase.addCodeEvidenceCurrentStep("Error: " + e.getMessage());
			throw new Exception("Error: " + e.getMessage());
		}
		
	}
	
	/**
	 * Método para terminar un proceso.
	 * @param processName - arreglo con el nombre de los procesos a terminar
	 *@Author Roberto Renteria
	 */
	public void killProcess(String[] processName) {
try {
		for (String process : processName ) {
			Process p;
			try {
				p = Runtime.getRuntime().exec("taskkill /F /IM " + process);
				p.waitFor();
			} catch (Exception e) {
				System.out.println(e.toString());
			}	
		}
	}catch(Exception e) {
		System.out.println(e.toString());
	}
}
	
	
	/*
	 * metodo utilizado para ingresar a ventana de request
	 * @param 
	 * @author Mariana Avitia
	 * @throws Exception
	 * @date 27/Marzo/2025
	 */
	public boolean viewRequest() throws Exception {
		
        // Seleccionar una imagen para buscar en la pantalla (esto depende de tus patrones
        Pattern view = new Pattern(FolderPath +"\\viewEBS.png").similar((float).75);
      
        
        try {
        // Verificar si el patrón se encuentra
        if (sk.screen.exists(view,30)!=null) {
        	
            sk.screen.click(view);
          
            Pattern requests = new Pattern(FolderPath +"\\requests.png").similar((float).75); 
    	        try {
    	        // Verificar si el patrón se encuentra

    	            if (sk.screen.exists(requests, 30) != null) {
    	                sk.screen.click(requests);
    	                testCase.addScreenShotCurrentStep("Se abre menú de Request");
    	              return true;
    	            }
    	        
    	        else {
    	                System.out.println("El patrón no se encontró dentro del tiempo de espera.");
    	              
    	        return false;
    	        }
    	        } catch(Exception e) {
    	            System.out.println("No se encontro el elemento" + e);
    	            testCase.addCodeEvidenceCurrentStep("Error: " + e.getMessage());
    	        }  
    	           return false;
        
       
        }
        
        }catch(Exception e) {
        	System.out.println("a" + e);
        	testCase.addCodeEvidenceCurrentStep("Error: " + e.getMessage());
        	}
		return false;
        }
	
	/*
	 * metodo utilizado para subir nueva solicitud
	 * @param 
	 * @author Mariana Avitia
	 * @throws FindFailed, AWTException
	 * @date 27/Marzo/2025
	 */
	public boolean submitNewRequest() throws FindFailed, Exception {
		
        String pathImg = sk.PatternPath("EBS\\"+"EBS");
    	File FolderPath = new File(pathImg);
       
        // Seleccionar una imagen para buscar en la pantalla (esto depende de tus patrones)
        String imageName = "aceptarE.png";  // Asegúrate de que este nombre esté disponible en ImgPattern
        //Pattern pattern = ImgPattern.get(imageName);  // Obtén el patrón del HashMap usando el nombre de la imagen
        Pattern pattern = new Pattern(FolderPath +"\\submitNewRequest.png").similar((float).70);        
          
        // Verificar si el patrón se encuentra
        if (sk.screen.exists(pattern,30)!=null) {
            System.out.println("Esperando a que el patrón aparezca..."+imageName);

          
                System.out.println("Patrón encontrado, haciendo clic..."+ imageName);
                sk.screen.wait(pattern).highlight(2, "red");
                sk.screen.click(pattern);
                u.hardWait(2);
                testCase.addScreenShotCurrentStep("Se ejecuta una nueva solicitud");
                return true;
        }
        else {
        	System.out.println("No se encontro la imagen");
        	Assert.fail("fallo al encontrar el elemento");
        	testCase.addCodeEvidenceCurrentStep("fallo al encontrar el elemento");
        	return false;
        	}
        
	}
	/*
	 * metodo utilizado para hacer click en btn Find 
	 * @param 
	 * @author Mariana Avitia
	 * @throws FindFailed, AWTException
	 * @date 27/Marzo/2025
	 */
	public boolean find() throws FindFailed, AWTException {
		
        String pathImg = sk.PatternPath("EBS\\"+"EBS");
    	File FolderPath = new File(pathImg);
       
        // Seleccionar una imagen para buscar en la pantalla (esto depende de tus patrones)
        String imageName = "aceptarE.png";  // Asegúrate de que este nombre esté disponible en ImgPattern
        //Pattern pattern = ImgPattern.get(imageName);  // Obtén el patrón del HashMap usando el nombre de la imagen
        Pattern pattern = new Pattern(FolderPath +"\\find.png").similar((float).70); 
        
        testCase.addScreenShotCurrentStep("Click en buscar");
        // Verificar si el patrón se e
        if (sk.screen.exists(pattern,30)!=null) {
            System.out.println("Esperando a que el patrón aparezca..."+imageName);

          
                System.out.println("Patrón encontrado, haciendo clic..."+ imageName);
                sk.screen.wait(pattern).highlight(2, "red");
                sk.screen.click(pattern);
                u.hardWait(2);
                return true;
        }
        else {
        	System.out.println("No se encontro la imagen");
        	Assert.fail("fallo al encontrar el elemento");
        	testCase.addCodeEvidenceCurrentStep("fallo al encontrar el elemento");
        	return false;
        	}
        
	}
	/*
	 * metodo utilizado para hacer click al btn ok cuando se han ingresado los parametros
	 * @param 
	 * @author Mariana Avitia
	 * @throws FindFailed, AWTException
	 * @date 27/Marzo/2025
	 */
	public boolean okParameters() throws FindFailed, AWTException {
		
        String pathImg = sk.PatternPath("EBS\\"+"EBS");
    	File FolderPath = new File(pathImg);
       
        // Seleccionar una imagen para buscar en la pantalla (esto depende de tus patrones)
        String imageName = "aceptarE.png";  // Asegúrate de que este nombre esté disponible en ImgPattern
        //Pattern pattern = ImgPattern.get(imageName);  // Obtén el patrón del HashMap usando el nombre de la imagen
        Pattern pattern = new Pattern(FolderPath +"\\okParameters.png").similar((float).70);        
        
        // Verificar si el patrón se e
        if (sk.screen.exists(pattern,30)!=null) {
            System.out.println("Esperando a que el patrón aparezca..."+imageName);

          
                System.out.println("Patrón encontrado, haciendo clic..."+ imageName);
                sk.screen.wait(pattern).highlight(2, "red");
                sk.screen.click(pattern);
                testCase.addScreenShotCurrentStep("Click botón OK");
                u.hardWait(2);
                return true;
        }
        else {
        	System.out.println("No se encontro la imagen");
        	Assert.fail("fallo al encontrar el elemento");
        	testCase.addCodeEvidenceCurrentStep("fallo al encontrar el elemento");
        	return false;
        	}
        
	}
	/*
	 * metodo utilizado para subir nueva solicitud
	 * @param 
	 * @author Mariana Avitia
	 * @throws FindFailed, AWTException
	 * @date 27/Marzo/2025
	 */
	public boolean submitRequest() throws FindFailed, AWTException {
		
        String pathImg = sk.PatternPath("EBS\\"+"EBS");
    	File FolderPath = new File(pathImg);
       
        // Seleccionar una imagen para buscar en la pantalla (esto depende de tus patrones)
        String imageName = "aceptarE.png";  // Asegúrate de que este nombre esté disponible en ImgPattern
        //Pattern pattern = ImgPattern.get(imageName);  // Obtén el patrón del HashMap usando el nombre de la imagen
        Pattern pattern = new Pattern(FolderPath +"\\submitRequest.png").similar((float).70);        
        
        // Verificar si el patrón se e
        if (sk.screen.exists(pattern,30)!=null) {
            System.out.println("Esperando a que el patrón aparezca..."+imageName);

          
                System.out.println("Patrón encontrado, haciendo clic..."+ imageName);
                sk.screen.wait(pattern).highlight(2, "red");
                sk.screen.click(pattern);
                testCase.addScreenShotCurrentStep("Solicitud de reporte creada exitosamente");
                u.hardWait(2);
                return true;
        }
        else {
        	System.out.println("No se encontro la imagen");
        	Assert.fail("fallo al encontrar el elemento");
        	testCase.addCodeEvidenceCurrentStep("fallo al encontrar el elemento");
        	return false;
        	}
        
	}
	
	/*
	 * metodo utilizado para ver el resultado del request
	 * @param 
	 * @author Mariana Avitia
	 * @throws FindFailed, AWTException
	 * @date 27/Marzo/2025
	 */
	public boolean viewOutput() throws FindFailed, AWTException {
		
        String pathImg = sk.PatternPath("EBS\\"+"EBS");
    	File FolderPath = new File(pathImg);
       
        Pattern pattern = new Pattern(FolderPath +"\\viewOutput.png").similar((float).70);        
        
        // Verificar si el patrón se e
        if (sk.screen.exists(pattern,30)!=null) {
                sk.screen.wait(pattern).highlight(2, "red");
                sk.screen.click(pattern);
                testCase.addScreenShotCurrentStep("Click botón view Output");
                u.hardWait(2);
                return true;
        }
        else {
        	System.out.println("No se encontro la imagen");
        	Assert.fail("fallo al encontrar el elemento");
        	testCase.addCodeEvidenceCurrentStep("fallo al encontrar el elemento");
        	return false;
        	}
        
	}
	
	/**
	 * Método para abrir un aplicativo de oracle EBS Java usando Chrome
	 * @author Roberto Rentería
	 * @throws Exception 
	 * @date 24/Marzo/2025
	 */	
	public void abrirReprte() throws Exception {
		
		try {
		Pattern btnconservar = new Pattern(FolderPath +"\\btnConservarChrome.png").similar((float).80);
		Pattern abrirArchivo = new Pattern(FolderPath +"\\abrirReporteEdge.png").similar((float).75);
		
		if (sk.screen.exists(btnconservar,30)!=null) {
			sk.screen.wait(btnconservar).highlight(2, "red");
			sk.screen.click(btnconservar);	
		}
		
		if (sk.screen.exists(abrirArchivo,20)!=null) {
			u.hardWait(1);
			sk.screen.wait(abrirArchivo).highlight(2, "red");
            sk.screen.click(abrirArchivo);
			;	
		}
		
		String home = System.getProperty("user.home");
		File directoryDownloads = new File(home+"/Downloads");
		
		File latestFile = this.getLastModifiedFile(directoryDownloads);
		
		u.hardWait(5);
		Desktop.getDesktop().open(latestFile);
		u.hardWait(5);
					

	
		}catch(Exception e) {
			testCase.addCodeEvidenceCurrentStep("Error: " + e.getMessage());
			throw new Exception("Error: " + e.getMessage());
			}
		
	}
	
	/**
	 * Método para abrir reporte excel descragado
	 * @author Roberto Rentería
	 * @throws Exception 
	 * @date 24/Marzo/2025
	 */	
	public void abrirReprteExcel() throws Exception {
		
		try {
		Pattern btnconservar = new Pattern(FolderPath +"\\btnConservarChrome.png").similar((float).75);
		Pattern abrirArchivo = new Pattern(FolderPath +"\\siExcel.png").similar((float).75);
		if (sk.screen.exists(btnconservar,20)!=null) {
			sk.screen.click(btnconservar);	
		}
		
		if (sk.screen.exists(abrirArchivo,20)!=null) {
			u.hardWait(7);
			sk.screen.wait(abrirArchivo).highlight(2, "red");
            sk.screen.click(abrirArchivo);
			;	
		}
		
		String home = System.getProperty("user.home");
		File directoryDownloads = new File(home+"/Downloads");
		
		File latestFile = this.getLastModifiedFile(directoryDownloads);
		
		
		u.hardWait(5);
		Desktop.getDesktop().open(latestFile);
		u.hardWait(5);
					

	
		}catch(Exception e) {
			testCase.addCodeEvidenceCurrentStep("Error: " + e.getMessage());
			throw new Exception("Error: " + e.getMessage());
			}
		
	}
	
	/**
	 * Método para validar formato de reporte consolidado de cartas os
	 * @author Roberto Rentería
	 * @throws Exception 
	 * @date 24/Marzo/2025
	 */	
	public boolean validarReporte() throws Exception {
		
		try {
		Pattern campos = new Pattern(FolderPath +"\\camposReporteOS.png").similar((float).80);
		if (sk.screen.exists(campos,30)!=null) {
			sk.screen.wait(campos).highlight(5, "blue");
			testCase.addScreenShotCurrentStep("Campos encontrados");
			return true;
		}
		
		}catch(Exception e) {
			testCase.addCodeEvidenceCurrentStep("El reporte no tiene el formato esperado" );
			throw new Exception("Error: " + e.getMessage());
			
			}
		return false;
		
	}
	/*
	 * metodo utilizado para seleccionar servicio Fintech
	 * @param folder
	 * @author Mariana Avitia
	 * @throws FindFailed, AWTException
	 * @date 27/Marzo/2025
	 */
	public boolean servicioFtech() throws FindFailed, AWTException {
		
        String pathImg = sk.PatternPath("EBS\\"+"EBS");
    	File FolderPath = new File(pathImg);
           
        Pattern ftech = new Pattern(FolderPath +"\\ftech.png").similar((float).65); 
        Pattern okServicio = new Pattern(FolderPath +"\\okServicio.png").similar((float).70);
        
        		if(sk.screen.exists(ftech,30)!=null) {
                System.out.println("Esperando a que el patrón aparezca...");

                    System.out.println("Patrón encontrado, haciendo clic...");
                    sk.screen.wait(ftech).highlight(2, "red");
                    sk.screen.click(ftech);
                    u.hardWait(2);
                    sk.screen.wait(okServicio).highlight(2, "red");
                    sk.screen.click(okServicio);
                    testCase.addScreenShotCurrentStep("Se ingresa servicio");
                    return true;
            
        		}else {
            	System.out.println("No se encontro el servicio");
            	Assert.fail("fallo al encontrar el elemento");
            	testCase.addCodeEvidenceCurrentStep("fallo al encontrar el elemento");
            	return false;
            	}     	
        }
	/*
	 * metodo utilizado para seleccionar servicio Conekta
	 * @param folder
	 * @author Mariana Avitia
	 * @throws FindFailed, AWTException
	 * @date 27/Marzo/2025
	 */
	public boolean servicioCnkta() throws FindFailed, AWTException {
		
        String pathImg = sk.PatternPath("EBS\\"+"EBS");
    	File FolderPath = new File(pathImg);
       
        Pattern cnkta = new Pattern(FolderPath +"\\cnkta.png").similar((float).70);
        Pattern okServicio = new Pattern(FolderPath +"\\okServicio.png").similar((float).70);
        
        		if(sk.screen.exists(cnkta,30)!=null) {
                System.out.println("Esperando a que el patrón aparezca...");

                    System.out.println("Patrón encontrado, haciendo clic...");
                    sk.screen.wait(cnkta).highlight(2, "red");
                    sk.screen.click(cnkta);
                    u.hardWait(2);
                    sk.screen.wait(okServicio).highlight(2, "red");
                    sk.screen.click(okServicio);
                    testCase.addScreenShotCurrentStep("Se ingresa servicio");
                    return true;
            
        		}else {
            	System.out.println("No se encontro el servicio");
            	Assert.fail("fallo al encontrar el elemento");
            	testCase.addCodeEvidenceCurrentStep("fallo al encontrar el elemento");
            	return false;
            	}     	
        }
	/*
	 * metodo utilizado para seleccionar servicio Hotpay
	 * @param folder
	 * @author Mariana Avitia
	 * @throws FindFailed, Exception
	 * @date 27/Marzo/2025
	 */
	public boolean servicioHpay() throws FindFailed, Exception {
		
        String pathImg = sk.PatternPath("EBS\\"+"EBS");
    	File FolderPath = new File(pathImg);
       
        Pattern hpay = new Pattern(FolderPath +"\\hpay.png").similar((float).65);    
        Pattern okServicio = new Pattern(FolderPath +"\\okServicio.png").similar((float).70);
        
        		if(sk.screen.exists(hpay,30)!=null) {
                System.out.println("Esperando a que el patrón aparezca...");

                    System.out.println("Patrón encontrado, haciendo clic...");
                    sk.screen.wait(hpay).highlight(2, "red");
                    sk.screen.click(hpay);
                    u.hardWait(2);
                    sk.screen.wait(okServicio).highlight(2, "red");
                    sk.screen.click(okServicio);
                    testCase.addScreenShotCurrentStep("Se ingresa servicio");
                    return true;
            
        		}else {
            	System.out.println("No se encontro el servicio");
            	Assert.fail("fallo al encontrar el elemento");
            	testCase.addCodeEvidenceCurrentStep("fallo al encontrar el elemento");
            	return false;
            	}     	
        }
	
	
	/*
	 * Metodo para validar Y estado en columna Integrador
	 * @param SQLResult resultSet, String columnName, String estadoIntegrador
	 * @author Mariana Avitia
	 * @throws 
	 * @date 27/Marzo/2025
	 */
		public boolean estadoIntegradorHabilitado(SQLResult resultSet, String columnName, String estadoIntegrador) {
	       
	                String columnValue = resultSet.getData(0,columnName);
	             // Verificar si columnValue no es null y si es igual a estadoIntegrador
	                if (columnValue != null && columnValue.equals(estadoIntegrador)) {
	                	testCase.addCodeEvidenceCurrentStep("Integrador habilitado en base de datos");
	                    return true; // Retornar true si los valores son iguales
	                }
	                testCase.addCodeEvidenceCurrentStep("Resultado no esperado");
	                return false;             
	  }
		
		/*
		 * Metodo para validar N estado en columna Integrador
		 * @param SQLResult resultSet, String columnName, String estadoIntegrador
		 * @author Mariana Avitia
		 * @throws 
		 * @date 27/Marzo/2025
		 */
		public boolean estadoIntegradorDeshabilitado(SQLResult resultSet, String columnName, String estadoIntegrador) {
       
			String columnValue = resultSet.getData(0,columnName);
			// Verificar si columnValue no es null y si es igual a estadoIntegrador
			if (columnValue != null && columnValue.equals(estadoIntegrador)) {
				testCase.addCodeEvidenceCurrentStep("Integrador deshabilitado en base de datos");
				return true; // Retornar true si los valores son iguales
			}
			    testCase.addCodeEvidenceCurrentStep("Resultado no esperado");
			    return false;
		}
				
		/*
	     * Metodo para iniciar sesion en la plataforma de EBS ingles
	     *
		 * @author Mariana Avitia
		 * @since 27/03/2025
		 */
		 public boolean loginIngles(String url, String userName, String password) {
			 
			 if(url!=null){
			 inicio.navigate(url);
			 inicio.setCredentials();
			 testCase.addScreenShotCurrentStep(u, "Login");
			 inicio.clickLogIn();
			 testCase.addScreenShotCurrentStep(u, "Portal de EBS");
			 return true;
					}
			else {
				testCase.addCodeEvidenceCurrentStep("Resultado no esperado");
				return false;
			}
				}

}
