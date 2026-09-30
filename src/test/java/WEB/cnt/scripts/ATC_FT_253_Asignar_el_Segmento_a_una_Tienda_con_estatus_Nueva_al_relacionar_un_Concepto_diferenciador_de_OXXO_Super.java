package WEB.cnt.scripts;

import static org.testng.Assert.assertTrue;

import java.util.HashMap;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import WEB.cnt.om.Formato_y_Entorno;
import WEB.cnt.om.Login;
import WEB.cnt.om.Planogramacion;
import modelo.BaseExecution;
import utils.password.PasswordUtil;
import utils.selenium.ChromeTest;
import utils.selenium.SeleniumUtil;

public class ATC_FT_253_Asignar_el_Segmento_a_una_Tienda_con_estatus_Nueva_al_relacionar_un_Concepto_diferenciador_de_OXXO_Super extends BaseExecution{
	
	SeleniumUtil u;
	
	@Test(dataProvider = "data-provider")
	
	public void ATC_FT_253_Asignar_el_Segmento_a_una_Tienda_con_estatus_Nueva_al_relacionar_un_Concepto_diferenciador_de_OXXO_Super_test
	(HashMap<String, String> data) {
		
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
		String segmentoMaestro = data.get("Segmento Maestro");
		String potencialHogar = data.get("Potencial Hogar");
		String potencialComida = data.get("Potencial Comida");
		String potencialDesayuno = data.get("Potencial Desayuno");
		String potencialPba = data.get("Potencial Pba Nva");
		String overlayHogar = data.get("Overlay Hogar");
		String overlayComida = data.get("Overlay Comida");
		String overlayDesayuno = data.get("Overlay Desayuno");
		String overlayPba = data.get("Overlay Pba Nva");
		
		/*
		 * PASO 1
		 *****************************************************************************************/
		addStep("Ingresar al portal " + URL + " con el <Usuario>" + user + "");
		u = new SeleniumUtil(new ChromeTest(), true);
		Login pa = new Login(u, testCase);
		u.get(data.get("Url"));
		u.hardWait(3);
		pa.logginInventarioEspacios(user, password);
		
		/*
		 * PASO 2
		 *****************************************************************************************/
		addStep("Seleccionar la Plaza y dar clic en el bot�n Continuar");
		pa.clickSeleccionarPlaza(plazaE);
		
		/*
		 * PASO 3
		 *****************************************************************************************/
		addStep("Seleccionar el Distrito y Tienda; a continuaci�n dar clic en el bot�n Continuar");
		pa.clickSeleccionarDistritoTienda(distrito, tienda);
		
		/*
		 * PASO 4
		 *****************************************************************************************/
		
		addStep("Dar clic en el link formato y entorno");
		Formato_y_Entorno fO =  new Formato_y_Entorno(u, testCase);
		fO.FormatoyEntorno();
		
		/*
		 * PASO 5
		 *****************************************************************************************/
		
		addStep("Usuario selecciona la opci�n de \"OXXO Super\" del combo de \"Concepto Diferenciador\".  ");
		
		fO.Framedatos();
		fO.Diferenciador(diferenciador);
		
		/*
		 * PASO 6
		 *****************************************************************************************/
		addStep("Usuario selecciona el bot�n de \"Asignar\" de la opci�n de Segmento de Tienda.");
		fO.ClicBtbSegmentoTienda();
	
		/*
		 * PASO 7
		 *****************************************************************************************/
		addStep("Usuario selecciona Aceptar.");
		fO.alertaGuardarSegmentoTienda();
		System.out.print(segmentoMaestro);
		
		/*
		 * PASO 8
		 *****************************************************************************************/
		addStep("Usuario selecciona los valores solicitados por el pop-up.");
		fO.SetearElementosNvaPantallaConceptoDiferenciador(segmentoMaestro,potencialHogar, potencialComida, potencialDesayuno,potencialPba,overlayHogar,overlayComida,overlayDesayuno,overlayPba);
		
		/*
		 * PASO 9
		 *****************************************************************************************/
		addStep("Usuario selecciona Aceptar.");
		fO.botonAceptarConceptoDiferenciador();
		
		/*
		 * PASO 10
		 *****************************************************************************************/
		addStep("Sistema mostrara el mensaje de \"�Esta seguro de guardar el Segmento de Tienda?\" junto con los botones de Aceptar y Continuar.");
		Boolean guardar = fO.alertaGuardarSegmentoTienda();
		assertTrue(guardar, "los valores se guardaron con exito");
		
		/*
		 * PASO 11
		 *****************************************************************************************/
		addStep("Usuario confirma que se haya cargado los valores seleccionados en el pop-up de Segmento de Tienda en los campos correspondientes de Formato y Entorno.");
	
		Boolean datosIgual =fO.getElementosConceptoDiferenciador
				(segmentoMaestro, 
						potencialHogar, 
						potencialComida, 
						potencialDesayuno, 
						potencialPba, 
						overlayHogar, 
						overlayComida, 
						overlayDesayuno, 
						overlayPba);
		assertTrue(datosIgual, "Usuario confirma que se haya cargado los valores seleccionados en el pop-up de Segmento de Tienda en los campos correspondientes de Formato y Entrono.");
		
		/*
		 * PASO 12
		 *****************************************************************************************/
		addStep("Usuario captura los dem�s datos solicitados en la pantalla y selecciona Actualizar");
		fO.clickBotonActualizarEntorno();
		
		/*
		 * PASO 13
		 *****************************************************************************************/
		addStep("Sistema guarda la informaci�n capturada y cambia el estatus de la tienda de \"Nueva\" a \"Notificada\", se muestra el mensaje de �La informaci�n ha sido actualizada exitosamente\".");
		Planogramacion pl = new Planogramacion(u, testCase);
	    boolean noti=    pl.NotificacionAlert();
	    assertTrue(!noti, "campo Notificacion no existe, se mandaron los datos correctamente");
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
		return "Validar que al seleccionar el concepto \\\"Oxxo super\\\" para una Tienda con estatus \\\"Nueva\\\" solicite la captura de los siguientes campos:\\r\\n\"\r\n"
				+ "				+ \"-Segmento Maestro\\r\\n\"\r\n"
				+ "				+ \"-Potencial_Hogar\\r\\n\"\r\n"
				+ "				+ \"-Potencial_Comida\\r\\n\"\r\n"
				+ "				+ \"-Potencial_Desayuno\\r\\n\"\r\n"
				+ "				+ \"-Overlay_Hogar\\r\\n\"\r\n"
				+ "				+ \"-Overlay_Comida\\r\\n\"\r\n"
				+ "				+ \"-Overlay_Desayuno\\r\\n\"\r\n"
				+ "				+ \"\\r\\n\"\r\n"
				+ "				+ \"";
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
		u.getDriver().quit();
	}

}
