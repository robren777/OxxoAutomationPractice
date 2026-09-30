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

public class ATC_FT_037_EditarBancoEnPortalApexBINES extends BaseExecution {

	private SeleniumUtil u;
	private GestionBines gb;

	@Test(dataProvider = "data-provider")
	public void ATC_FT_037_EditarBancoEnPortalApexBINES_test(HashMap<String, String> data) throws Exception {

		testCase.setProject_Name("I26002_GestionBines");
		u = new SeleniumUtil(new ChromeTest(), true);
		gb = new GestionBines(u, testCase);

		addStep("Loguearse en la aplicación.");
		gb.loginApex(data.get("URL"), data.get("USER"), data.get("PASSWORD"));

		addStep("Se muestran las secciones: Archivo, Cargar Archivo y listado de Archivos "
				+ "Cargados e Información.");
		assertTrue(gb.validateInicioPage(), "No se realizo Login de forma exitosa.");

		addStep("Ingresar a la opcion Bancos");
		gb.selectBankOption();

		addStep("Obtener el ultimo banco creado desde BD");
		String bancoABuscar = gb.getLastCreatedBank();

		addStep("Ingresar nombre de banco a buscar y dar clic en boton GO para encontrar el banco");
		gb.typeBankSearchBar(bancoABuscar);
		gb.clickBtnGoSearchBar();

		addStep("Dar clic en boton de editar banco buscado");
		gb.clickBtnEditBank();

		addStep("Invertir el status actual del banco (Activo <-> Inactivo)");
		String nuevoStatus = gb.toggleBankStatus();

		addStep("Guardar cambios del banco editado");
		gb.saveBankChanges();

		addStep("Validar nuevo status del banco editado en BD RMS DAS");
		assertTrue(gb.validateBankStatus(bancoABuscar, nuevoStatus), "El status del banco no se actualizo correctamente en BD");
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