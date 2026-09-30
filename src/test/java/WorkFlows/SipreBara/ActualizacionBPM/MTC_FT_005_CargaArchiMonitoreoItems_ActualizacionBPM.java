package WorkFlows.SipreBara.ActualizacionBPM;

import java.util.HashMap;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import WorkFlows.util.GlobalVariables;
import WorkFlows.util.SipreBara.pomCargaArchivos;
import WorkFlows.util.SipreBara.pom_Login_PEGA;
import modelo.BaseExecution;
import utils.selenium.FirefoxTest;
import utils.selenium.SeleniumUtil;

public class MTC_FT_005_CargaArchiMonitoreoItems_ActualizacionBPM extends BaseExecution {
	SeleniumUtil u;
	
	@Test(dataProvider = "data-provider")
	
	 public void ATC_FT_005_CargaArchiMonitoreoItems_ActualizacionBPM_Test(HashMap<String, String> data) throws Exception {
		
	testCase.setProject_Name("Framework WorkFlows --> SIPRE BARA");
	

	/**
	 * Variables
	 * 
	 */
	String userPega = data.get("userPega");
	String userSipre = GlobalVariables.SIPRE_BARA_USER;
	String psSipre = GlobalVariables.SIPRE_BARA_PS;
	String BASE_PATH = System.getProperty("user.dir");

	
	/**
	 * 
	 * Utiler�as
	 */
	
	
	SeleniumUtil u = new SeleniumUtil(new FirefoxTest(), true);
	u.get(data.get("url"));
	pomCargaArchivos cargaArchivos = new pomCargaArchivos(u, testCase);
	pom_Login_PEGA pomPega = new pom_Login_PEGA(u,testCase, userPega );
	
	
	
	/*-----------------------------------------------------------------------------------------*/
	addStep("Inicia Sesi�n en PEGA");
	pomPega.iniciaSesion();
	
	
	pomPega.cambiarVentana(1);

	    
	/*-----------------------------------------------------------------------------------------*/
	    addStep("Posicionar el cursor en la opci�n 'Configuraci�n' y "
			+ "despu�s dar clic en la opci�n reportes 'Carga de archivos'");
		
		cargaArchivos.selectCargaArchivo();
	/*-----------------------------------------------------------------------------------------*/
		addStep("Seleccionar el tipo de archivo a subir");
		cargaArchivos.selecTipoArchivo("4.- MONITOREO DE ITEMS");
		/*-----------------------------------------------------------------------------------------*/
		addStep("Subir archivo Correcto");
		  String path = BASE_PATH+"\\archivosSipreBara\\04-Monitoreo de Items.xlsx";
		 // String nombreHoja = "Listado";
		  cargaArchivos.subirArchivo(path);
		 // cargaArchivos.validaRowsDataGridArchivo(path, nombreHoja);

		  u.hardWait(2);


	/*-----------------------------------------------------------------------------------------*/
		addStep("Subir archivos que no cumplan con el formato esperado");
		
		  String docx = BASE_PATH+"\\archivosSipreBara\\Archivos Incorrectos\\archivoDocx.docx";
		  String xlsx = BASE_PATH+"\\archivosSipreBara\\Archivos Incorrectos\\archivoFormatoIncorrecto.xlsx";
		  String txt = BASE_PATH+"\\archivosSipreBara\\Archivos Incorrectos\\txtVacio.txt";

		cargaArchivos.validaArchivosIncorrectos(docx, xlsx, txt );

		 u.getDriver().quit();
	
	}
	
	
	
	
	
	@Override
	public void beforeTest() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String setPrerequisites() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String setTestDescription() {
		// TODO Auto-generated method stub
		return "Validar la carga de archivo Monitoreo de �tems Configuraci�n Carga de archivos";
	}

	@Override
	public String setTestDesigner() {
		// TODO Auto-generated method stub
		return "Alicia Michelle Ceja Gomez";
	}

	@Override
	public String setTestFullName() {
		// TODO Auto-generated method stub
		return "ATC_FT_005_CargaArchiMonitoreoItems_ActualizacionBPM_Test";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}
	
	@AfterMethod
    public void afterMethod() {
        u.getDriver().quit();
    }

}
