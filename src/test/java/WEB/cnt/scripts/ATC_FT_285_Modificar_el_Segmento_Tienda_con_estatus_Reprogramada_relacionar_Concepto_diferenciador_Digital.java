package WEB.cnt.scripts;

import static org.testng.Assert.assertTrue;

import java.util.HashMap;

import org.testng.annotations.Test;

import WEB.cnt.om.Formato_y_Entorno;
import WEB.cnt.om.Login;
import WEB.util.GlobalVariables;
import modelo.BaseExecution;
import utils.password.PasswordUtil;
import utils.selenium.MEdgeTest;
import utils.selenium.SeleniumUtil;
import utils.sql.SQLResult;
import utils.sql.SQLUtil;

public class ATC_FT_285_Modificar_el_Segmento_Tienda_con_estatus_Reprogramada_relacionar_Concepto_diferenciador_Digital extends BaseExecution{

	SeleniumUtil u;

	@Test(dataProvider = "data-provider")

	public void ATC_FT_285_Modificar_el_Segmento_Tienda_con_estatus_Reprogramada_relacionar_Concepto_diferenciador_Digital_test(HashMap<String, String> data) throws Exception {
		/*
		 * Utilerias
		 ******************************************************************************************/
		SQLUtil dbFCIAS = new SQLUtil(GlobalVariables.DB_HOST_FCIASQAS_PORTAL, GlobalVariables.DB_USER_FCIASQAS_PORTAL,
				GlobalVariables.DB_PASSWORD_FCIASQAS_PORTAL);

		String cp = data.get("CP");
		String num = data.get("num");
		testCase.setTest_Description(num+""+cp);

		/*
		 * Variables
		 ******************************************************************************************/
		String user = data.get("User");
		String password = PasswordUtil.decryptPassword(data.get("Password"));
		String url = data.get("Url");
		String plaza = data.get("Plaza");
		String distrito = data.get("Distrito");
		String tienda = data.get("Tienda");
		String URL = data.get("Url");
		String msjAlerta = data.get("MensajeAlerta");
		String tiendaP = data.get("TiendaP");
		String plazaP  = data.get("PlazaP ");
		String diferenciadorModificar1 = data.get("DiferenciadorModificar1");
		String diferenciadorModificar2 = data.get("DiferenciadorModificar2");

		String query1 = "SELECT * FROM TIENDAS.T_FORMATO_SEGMENTO" + 
				" WHERE ID_TIENDA IN (SELECT ID_TIENDA FROM TIENDAS.T_TIENDAS" + 
				" WHERE CR_SUPERIOR = '"+plazaP+"'" + 
				" AND CR_TIENDA = '"+tiendaP+"')";
		
		String query2 = "SELECT T.CR_SUPERIOR, T.CR_TIENDA, S.MAESTRO_SEGMENTO_DESC, SD.POTENCIAL_DESC, SD.NECESIDAD_DESARROLLO_DESC, SD.NIVEL_OVERLAY_DESC" + 
				" FROM TIENDAS.T_TIENDAS T, TIENDAS.T_FORMATO_SEGMENTO S, TIENDAS.T_FORMATO_SEGMENTO_DET SD" + 
				" WHERE T.ID_TIENDA = S.ID_TIENDA" + 
				" AND S.ID_TIENDA = SD.ID_TIENDA" + 
				" AND T.CR_TIENDA = '"+tiendaP+"'";

		// ------------------------------------PASO 1

		addStep("Ingresar a la "+url+" del portal de aplicaciones"); 
		
		u = new SeleniumUtil(new MEdgeTest(), true);
		Login pa = new Login(u, testCase); 
		u.get(url);
		u.getDriver().manage().window().maximize();
		u.hardWait(3); 

		// ------------------------------------PASO 2
		
		addStep("Ingresar el usuario <UsuarioJefe> para despu�s seleccionar el bot�n de Continuar"); 
		
		pa.logginInventarioEspacios(user, password);
		
		
		// ------------------------------------PASO 3
		addStep("Usuario selecciona la Plaza con la cual trabajara y selecciona el bot�n de Continuar");
		pa.clickSeleccionarPlaza(plaza);
		
		// ------------------------------------PASO 4
		addStep("El usuario selecciona una tienda con estatus \"Reprogramada\" que corresponda a Oxxo M�xico y da clic en el bot�n de Continuar.");
		pa.clickSeleccionarDistritoTienda(distrito, tienda);
		
		// ------------------------------------PASO 5
		addStep("Usuario selecciona la opci�n de Formato y Entorno");
		Formato_y_Entorno fe = new Formato_y_Entorno(u, testCase);
		fe.FormatoyEntorno();

		// ------------------------------------PASO 6
		addStep("Usuario selecciona la opci�n de '" + diferenciadorModificar1 + "' del combo de \"Concepto Diferenciador\".");

		fe.Framedatos();

		fe.Diferenciador(diferenciadorModificar1);

		
		boolean validarValores = fe.ValidarCambioCampoSegmentoTiendaNA();
		
		if(validarValores) {
			testCase.addTextEvidenceCurrentStep("Resultado Esperado: El sistema precargar� los siguientes valores en los campos correspondientes al �Segmento de Tienda� los cu�les estar�n inactivados:  \r\n" + 
					"-Segmento Maestro: NA\r\n" + 
					"-Potencial_Hogar: NA\r\n" + 
					"-Potencial_Comida: NA\r\n" + 
					"-Potencial_Desayuno: NA\r\n" + 
					"-Overlay_Hogar: NA\r\n" + 
					"-Overlay_Comida: NA\r\n" + 
					"-Overlay_Desayuno: NA\r\n" + 
					"");
		}else {
			testCase.addTextEvidenceCurrentStep("El sistena no cargo en automatico el valor NA en los campos");
		}
		
		testCase.addScreenShotCurrentStep(u, "CamposValorNA");
		
		// ------------------------------------PASO 7
		addStep("Usuario confirma que se haya precargado los valores en los campos de Segmento de Tienda");
	
         testCase.addScreenShotCurrentStep(u, "ValoresPrecargados");
		
		assertTrue(validarValores, "No se precargaron los valores");
		
		// ------------------------------------PASO 8
		addStep("Usuario captura los dem�s datos solicitados en la pantalla y selecciona Actualizar");
		
		fe.clickBotonActualizarEntorno();	
		
		boolean alerta = fe.comprobarMensaje_onAlert(msjAlerta);
		
		fe.aceptar_onAlert();
		
	    assertTrue(alerta, "No se mostro el muestra el mensaje: La informacion de \"FORMATO Y ENTORNO\" ha sido grabada.");
		
		// ------------------------------------PASO 9
		addStep("Se revisa que se haya relacionado la relaci�n de los valores de \"Segmento Tienda\" a la tienda seleccionada.");

		SQLResult executeQuery1 = executeQuery(dbFCIAS, query1);
		SQLResult executeQuery2 = executeQuery(dbFCIAS, query2);
		
		boolean StatusQueryLEGAL = executeQuery1.isEmpty();
		
		testCase.addQueryEvidenceCurrentStep(executeQuery1);
		testCase.addQueryEvidenceCurrentStep(executeQuery2);
		
		if (!StatusQueryLEGAL) {
	
			String diferenciadorBD = executeQuery1.getData(0, "CONCEPTO_DIFERENCIADOR");
			
			if (!diferenciadorBD.toUpperCase().equals(diferenciadorModificar1.toUpperCase())) {
				//Assert.fail("La tienda no cuenta con un Concepto diferenciador de "+diferenciadorModificar1);
			}
		} else {
			//Assert.fail("No se encontro registro de la tienda en las tablas");
		}
		
		
		// ------------------------------------PASO 10
		addStep("Usuario cambia de pantallas para regresar a la pantalla de \"Formato y Entorno\".");
		
		fe.FormatoyEntorno();		
		
		// ------------------------------------PASO 11
		addStep("Usuario intenta cambiar la opci�n del Concepto Diferenciador");

		fe.Framedatos();
		fe.Diferenciador(diferenciadorModificar2);

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