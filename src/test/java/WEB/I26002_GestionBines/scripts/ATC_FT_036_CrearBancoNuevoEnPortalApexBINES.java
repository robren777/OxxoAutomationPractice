package WEB.I26002_GestionBines.scripts;

import static org.testng.Assert.assertTrue;

import java.util.HashMap;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import WEB.I26002_GestionBines.om.GestionBines;
import modelo.BaseExecution;
import utils.selenium.ChromeTest;
import utils.selenium.MEdgeTest;
import utils.selenium.SeleniumUtil;

public class ATC_FT_036_CrearBancoNuevoEnPortalApexBINES extends BaseExecution{
	
	private SeleniumUtil u;
	private GestionBines gb;
	
	@Test(dataProvider = "data-provider")
	public void ATC_FT_036_CrearBancoNuevoEnPortalApexBINES_test(HashMap<String, String> data) throws Exception {
		
		testCase.setProject_Name("I26002_GestionBines");
		u = new SeleniumUtil(new ChromeTest(), true);
		gb = new GestionBines(u, testCase);
		
		addStep("Loguearse en la aplicación.");
		gb.loginApex(data.get("URL") ,data.get("USER"), data.get("PASSWORD"));
		
		addStep("Se muestran las secciones: Archivo, Cargar Archivo y listado de Archivos "
				+ "Cargados e Información.");
		assertTrue(gb.validateInicioPage(), "No se realizo Login de forma exitosa.");
		
		addStep("Ingresar a la opcion Bancos");
		gb.selectBankOption();
		
		addStep("Dar Clic en Boton de Crear Nuevo Banco");
		gb.clickBtnCreateBank();
		
		addStep("Ingresamos informacion del nuevo banco a agregar");
		gb.typeInfoNewBank(data.get("QATestBank"));
		
		addStep("Dar clic en boton [ACEPTAR] para crear nuevo banco");
		gb.clickBtnAcceptNewBank();
		
		addStep("Buscar nuevo banco creado en BD RMS DAS");
		assertTrue(gb.searchNewDataBank(), "No se encontro nuevo banco agregado");
		
	}
	
	@AfterMethod
	public void cerrarNavegador() {
	    if (u != null) {
	        try {
	            gb.logout();
	        } catch (Exception e) {
	            System.out.println("Error al cerrar sesion: \r\n" + e);
	        } finally {
	            try {
	                u.quit();
	            } catch (Exception e) {
	                System.out.println("Error al cerrar el navegador: \r\n" + e);
	            }
	        }
	    }
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
		return null;
	}

	@Override
	public String setTestDesigner() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String setTestFullName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}
	

}
