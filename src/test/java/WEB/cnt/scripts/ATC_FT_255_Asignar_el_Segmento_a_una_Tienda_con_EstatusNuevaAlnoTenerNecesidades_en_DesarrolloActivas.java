package WEB.cnt.scripts;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import java.util.HashMap;

import org.testng.annotations.Test;

import WEB.cnt.om.Formato_y_Entorno;
import WEB.cnt.om.Login;
import modelo.BaseExecution;
import utils.password.PasswordUtil;
import utils.selenium.MEdgeTest;
import utils.selenium.SeleniumUtil;
public class ATC_FT_255_Asignar_el_Segmento_a_una_Tienda_con_EstatusNuevaAlnoTenerNecesidades_en_DesarrolloActivas extends BaseExecution{
	SeleniumUtil u;
	
	@Test(dataProvider = "data-provider")
	public void ATC_FT_255_Asignar_el_Segmento_a_una_Tienda_con_EstatusNuevaAlnoTenerNecesidades_en_DesarrolloActivas_test(HashMap<String, String> data) {
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
		addStep("Ingresar al portal " + URL + " del portal de aplicaciones");
		u = new SeleniumUtil(new MEdgeTest(), true);
		Login pa = new Login(u, testCase);
		u.get(data.get("Url"));
		u.getDriver().manage().window().maximize();
		u.hardWait(3);
		
		
		/*
		 * PASO 2
		 *****************************************************************************************/	
		
		addStep("Ingresar el usuario '"+user+"' para despu�s seleccionar el bot�n de Continuar");
		pa.logginInventarioEspacios(user, password);
		
		/*
		 * PASO 3
		 *****************************************************************************************/
		addStep("Usuario selecciona la Plaza con la cual trabajara y selecciona el bot�n de Continuar");
		pa.clickSeleccionarPlaza(plazaE);
		
		/*
		 * PASO 4
		 *****************************************************************************************/
		addStep("El usuario selecciona una tienda con estatus Nueva que corresponda a Oxxo M�xico y da clic en el bot�n de Continuar.");
		pa.clickSeleccionarDistritoTienda(distrito, tienda);
		
		/*
		 * PASO 5
		 *****************************************************************************************/
		addStep("Usuario selecciona la opci�n de Formato y Entorno");
		Formato_y_Entorno fe =  new Formato_y_Entorno(u, testCase);
		fe.FormatoyEntorno();
		
		
		/*
		 * PASO 6
		 *****************************************************************************************/
		addStep("El sistema muestra la pantalla de \"Formato y Entorno\" con sus campos correspondientes");
		
		
		/*
		 * PASO 6
		 *****************************************************************************************/
		addStep("Usuario selecciona una opci�n del combo de \"Concepto Diferenciador\" que solicite los campos del Segmento de tienda.");
		fe.Framedatos();
		fe.Diferenciador(diferenciador);
		
		
		/*
		 * PASO 6
		 *****************************************************************************************/
		addStep("El sistema habilitara el bot�n de \"Asignar\" y muestra los siguientes campos vac�os solicit�ndolos como requeridos ");

		boolean botonAsignar = fe.ElementBtnAsingar();
		fe.ElementDisConValidacionDeTexto1();
		
		assertTrue(botonAsignar, "El sistema no habilita el bot�n \"Asignar\"");
		
	
		/*
		 * PASO 6
		 *****************************************************************************************/
		addStep("Usuario selecciona el bot�n de \"Asignar\" de la opci�n de Segmento de Tienda.");
			
		fe.ClicBtbSegmentoTienda();
		
		
		/*
		 * PASO 6
		 *****************************************************************************************/
		addStep("Sistema mostrara el mensaje de \"Si realizo Cambios en la informaci�n, debe de guardar antes de Asignar el Segmento de Tienda �Desea continuar?\" junto con los botones de Aceptar y Cancelar");
	
		//fe.alertaGuardarSegmentoTienda();
			
		//boolean msj = fe.confirmarMensajeAlerta();
		
		boolean msj = fe.existeMensajeAlerta();		
	
		assertTrue(msj, "El sistema no muestra el mensaje");
		/*
		 * PASO 7
		 *****************************************************************************************/
		addStep("Usuario selecciona Aceptar");
		
		fe.confirmarMensajeAlerta();
			
		//fe.alertSegmentoTienda();
		
		/*
		 * PASO 6
		 *****************************************************************************************/
		addStep("Sistema muestra el pop-up de Segmento de Tienda con los campos requeridos de:\r\n" + 
				"    -Segmento Maestro\r\n" + 
				"	-Potencial_Hogar\r\n" + 
				"	-Potencial_Comida\r\n" + 
				"	-Potencial_Desayuno\r\n" + 
				"	-Overlay_Hogar\r\n" + 
				"	-Overlay_Comida\r\n" + 
				"	-Overlay_Desayuno\r\n" + 
				"    Con los botones de Aceptar y Cancelar\r\n" + 
				"    No se podr� seleccionar los valores ya que se encuentran activos valores en los Nuevos Cat�logos.");
		
		fe.popUpSegmentoTienda();
		
		/*
		 * PASO 8
		 *****************************************************************************************/
		addStep("Usuario confirma que no se permite concluir la captura al no poder seleccionar Potencial y su correspondiente Overlay.");
		
        boolean validacionCampos = fe.ElementDis();
		assertFalse(validacionCampos, "Los campos no se encuentran deshabilitados"); 

		
	}
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
		return "ATC_FT_255_Asignar_el_Segmento_a_una_Tienda_con_EstatusNuevaAlnoTenerNecesidades_en_DesarrolloActivas_test";
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
}
