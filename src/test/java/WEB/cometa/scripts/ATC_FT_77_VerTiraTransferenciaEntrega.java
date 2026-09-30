package WEB.cometa.scripts;

import static org.testng.Assert.assertTrue;

import java.util.HashMap;

import org.testng.annotations.Test;

import WEB.cometa.om.PortalCometa;
import modelo.BaseExecution;
import utils.selenium.ChromeTest;
import utils.selenium.SeleniumUtil;

/**Desc: Validar que el menu del modulo nos lleve a los diferentes procesos habilitados para el mismo
* MTC-FT-02-E1 - Verificar acesos del MÃ³dulo de Cedis
* @author Yair Lizarraga
* @date Junio 2024
*/
public class ATC_FT_77_VerTiraTransferenciaEntrega extends BaseExecution { 
	SeleniumUtil u;
	 
	@Test(dataProvider = "data-provider")
	public void ATC_FT_77_VerTiraTransferenciaEntrega_test (HashMap<String, String> data) throws Exception {
		testCase.setProject_Name("Portal Web: Cometa");
		
		/*
		 * Variables
		 ******************************************************************************************/
		String user = data.get("user");
		String password = data.get("password");
		String crplaza = data.get("crplaza");
		u = new SeleniumUtil(new ChromeTest(), true);
		PortalCometa pc = new PortalCometa(u, testCase); // crea objeto de locator
		
		
												
		/*
		 *                                      	PASO 1       
		 ******************************************************************************************/
		addStep("Ingresar las credenciales correspondientes al perfil de Cedis para el inicio de sesiÃ³n");
		u.get(data.get("Url")); // toma el valor Url del data provider
		u.hardWait(3); // tiempo en segundos
		pc.PrivatePortal(); 
		pc.IniciarSesion(user, password);
		pc.ClicPortalCometa();
		/*
		 *                                             PASO 2
		 ******************************************************************************************/
		addStep("Ingresar a la pÃ¡gina de RecepciÃ³n de Transferencias y Devoluciones");
		pc.ClicBotonCedis();
		pc.ClicBotonRecepcionDeTransferencias();

		/*
		 *                                             PASO 3
		 ******************************************************************************************/
		addStep("Ingresar una ruta en su respectivo campo y dar click en \"Consultar\"");
		pc.BuscarCRPlaza(crplaza);
		
		/*
		 *                                             PASO 4
		 ******************************************************************************************/
		addStep("Dar click en el botÃ³n \"limpiar filtros\"");
		boolean validarVerTransferencia = pc.ClicVerTransferencia();;
		if(validarVerTransferencia)
			testCase.addTextEvidenceCurrentStep("El menu se muestra correctamente");
		else
			testCase.addTextEvidenceCurrentStep("El menu no se muestra correctamente");
		assertTrue(validarVerTransferencia, "El menu se muestra correctamente");
	}

	@Override
	public void beforeTest() {
		// TODO Auto-generated method stub
		return;
		
	}

	@Override
	public String setPrerequisites() {
		// TODO Auto-generated method stub
		return "Contar con credenciales vÃ¡lidas para el inicio de sesiÃ³n del perfil Directos";
	}

	@Override
	public String setTestDescription() {
		// TODO Auto-generated method stub
		return "Validar que funcione el boton de limpiar transferencia de entrega";
	}

	@Override
	public String setTestDesigner() {
		return"Yair Lizarraga";
	}


	@Override
	public String setTestFullName() {
		return "ATC_FT_77_VerTiraTransferenciaEntrega_test";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}
}