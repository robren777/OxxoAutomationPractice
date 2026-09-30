package WEB.cnt.scripts;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import WEB.cnt.om.Formato_y_Entorno;
import WEB.cnt.om.Login;
import modelo.BaseExecution;
import utils.password.PasswordUtil;
import utils.selenium.MEdgeTest;
import utils.selenium.SeleniumUtil;

public class ATC_FT_260_Cancelar_la_asignacion_del_Segmento extends BaseExecution{
	SeleniumUtil u;
	
	@Test(dataProvider = "data-provider")
	public void ATC_FT_260_Cancelar_la_asignacion_del_Segmento_test(HashMap<String, String> data) {
		
		/*
		 * Variables
		 ******************************************************************************************/
		String user = data.get("User");
		String password = PasswordUtil.decryptPassword(data.get("Password"));
		String URL = data.get("Url");
		String plazaE = data.get("PlazaE");
		String tienda = data.get("Tienda");
		String distrito = data.get("Distrito");
		String diferenciador= data.get("Diferenciador");
		
		// Paso 1
		addStep("Ingresar a la <LigaCNT> del portal de aplicaciones.");
		u = new SeleniumUtil(new MEdgeTest(), true);
		u.getDriver().manage().window().maximize();	
		Login pa = new Login(u, testCase);
		u.get(data.get("Url"));
		testCase.addScreenShotCurrentStep("Imagen de login");
		u.hardWait(2);
		boolean validarAcceso = pa.accesoCorrectoPortal();
		assertTrue(validarAcceso, "No se pudo acceder al portal");
		
		// Paso 2
		addStep("Ingresar el usuario y contrase�a para despu�s seleccionar el bot�n de Continuar");
		pa.logginInventarioEspacios(user, password);
		u.waitForLoadPage();
		u.hardWait(2);
		boolean validarPlaza = pa.validarComboBoton();
		assertTrue(validarPlaza, "No se muestran el combo Plaza y boton Continuar");
		
		// Paso 3
		addStep("Selecciona la Plaza con la cual trabajara y selecciona el bot�n de Continuar");
		pa.clickSeleccionarPlaza(plazaE);
		u.waitForLoadPage();
		u.hardWait(1);
		boolean validarDT = pa.validarDistritoTiendas();
		assertTrue(validarDT, "No despliegan las opciones de Distrito y Tiendas");
		
		
		// Paso 4
		addStep("El usuario selecciona una tienda con estatus Nueva que corresponda a Oxxo M�xico y da clic en el bot�n de Continuar.");
		pa.clickSeleccionarDistritoTienda(distrito, tienda);
		u.waitForLoadPage();
		boolean validarPantalla = pa.validarOpcionesVentanaEmergente();
		assertTrue(validarPantalla, "No se muestra la pantalla con las opciones");
		
		// Paso 5
		addStep("Usuario selecciona la opci�n de Formato y Entorno");
		Formato_y_Entorno fO =  new Formato_y_Entorno(u, testCase);
		fO.FormatoyEntorno();
		fO.Framedatos();
		boolean validarFE = fO.validarFormatoyEntorno();
		assertTrue(validarFE, "No se muestra la pantalla de formato y entorno");
		
		// Paso 6
		addStep("Usuario selecciona una opci�n del combo de \"Concepto Diferenciador\" que solicite la captura de los datos de Segmento de Tienda.");
		fO.Diferenciador(diferenciador);
		boolean validarActivacionCampos = fO.validarActivacionCampos();
		System.out.print(validarActivacionCampos);
		boolean validarTextoCampos = fO.ValidarCambioCampoSegmentoTiendaNA();	
		System.out.print(validarTextoCampos);
		testCase.addScreenShotCurrentStep(u, "Paso6");
		assertFalse(!validarActivacionCampos && validarTextoCampos, "El sistema no habilita los campos o carga en automatico el valor NA");			
	
		
		// Paso 7
		addStep("Usuario selecciona el bot�n de \"Asignar\" de la opci�n de Segmento de Tienda.");
		boolean validarBtn = fO.validarBtnAsignarActivo();
		assertTrue(validarBtn, "El sistema no habilita el boton Asignar");					
		fO.click_on_Asignar();
		
		// Paso 8
		addStep("Sistema mostrara el mensaje de \"Si realizo Cambios en la informaci�n, debe de guardar antes de Asignar el Segmento de Tienda �Desea continuar?\" junto con los botones de Aceptar y Cancelar");
		boolean appearsmsg = fO.comprobarMensaje_onAlert("Si realiz� cambios en la informaci�n, debe de guardar antes de Asignar el Segmento de Tienda. �Desea continuar?");
		assertTrue(appearsmsg, "No se encontro el mensaje");
		
		// Paso 9
		addStep("Usuario selecciona Aceptar");
		fO.aceptar_onAlert();
		u.hardWait(2);
		boolean validarPopUp = fO.validarPopUpSegmentoTienda();
		assertTrue(validarPopUp, "No se muestra el pop up Segmento de Tienda");
		boolean validarEPopUp = fO.validarElementosPopUpSegmentoTienda();	
		assertTrue(validarEPopUp, "No se muestran los campos del pop up Segmento de Tienda");
		
		// Paso 10
		addStep("Usuario selecciona el bot�n de Cancelar del pop-up");		
		fO.cancelarOnSegmentodeTienda();
		
		Set<String> s = u.getDriver().getWindowHandles();
		List<String> sq = new ArrayList<>(s);
		u.getDriver().switchTo().window(sq.get(1));
		u.getDriver().manage().window().maximize();
		fO.Framedatos();
		boolean validarFE2 = fO.validarFormatoyEntorno();
		assertTrue(validarFE2, "No se muestra la pantalla de formato y entorno");
		
		String difActual = fO.diferenciadorTexto();
		if(difActual.equals(diferenciador)) {
			Assert.fail("No se descarto la informacion seleccionada anteriormente");
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
		return "";
	}

	@Override
	public String setTestDesigner() {
		// TODO Auto-generated method stub
		return "AutomationQA";
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
	@AfterMethod
	public void afterMethod() {
		//u.getDriver().quit();
	}

}
