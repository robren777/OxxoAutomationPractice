package WEB.cometa.scripts.mvp4;

import static org.testng.Assert.assertTrue;

import java.util.HashMap;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import WEB.cometa.om.PortalCometaMVP4;
import modelo.BaseExecution;
import utils.selenium.ChromeTest;
import utils.selenium.SeleniumUtil;

/**DESCRIPCIÓN: Validar que en cada ícono debe ser funcional y actualizar el contenido al hacer clic.
Nota: Se va a revisar las implicaciones de agarrar los PDF desde el storage y en caso de que la funcion de "pagina siguiente" 
no se vea como lo habiamos planeado se revisaran opciones. La prioridad es que si lo tomemos desde el storage 
*
*NOMBRE CASOS DE PRUEBA MANUAL QUE SE ABARCAN:
* MTC_FT_464 Validar que cada ícono de navegación sea funcional y actualizar el contenido al hacer clic, en el modulo "Devoluciones" DIRECTOS
*
*PREREQUISITOS:
1. Credenciales para el perfil DIRECTOS, con usuario  [Admin, OXXO OS, OXXO Plaza, Administrativo Operativo, Comercial, CEDIS] 
2. Liga de Portal Cometa
*
*INSUMOS: 
Existen datos en la tabla xxcm_dv

* @author Jose Pecina
* @date Febrero 2026
*/
public class ATC_FT_464_IconosNavegacionFuncionalesDevoluciones extends BaseExecution { 
	SeleniumUtil u;
	 
	@Test(dataProvider = "data-provider")
	public void ATC_FT_464_IconosNavegacionFuncionalesDevoluciones_test (HashMap<String, String> data) throws Exception {		
	    	testCase.setProject_Name("Portal Cometa");

	        String User = data.get("User");
	        String url = data.get("Url");
	        u = new SeleniumUtil(new ChromeTest(), true);
	        PortalCometaMVP4 pc4 = new PortalCometaMVP4(u, testCase);
	        
	        addStep("Precondicion");
	        boolean ResultPre = pc4.ValidaBDExistanDatosFiltrosGridDirectosDevoluciones(true, User);
	        assertTrue(ResultPre,"No se obtuvieron datos en la tabla xxcm_dv");
	        String Password = pc4.obtenerPasswordPorUsuario(User);
	        assertTrue(Password != null, "No se encontro password configurada para el usuario: " + User);
	        /*
	         *                                             PASO 1
	         ******************************************************************************************/
	        addStep("Ingresar a la liga");
	        boolean resultStep1 = pc4.IngresarURL(url, true);
	        assertTrue(resultStep1,"No se pudo ingresar a la URL");       
	        //Se quita pantalla de seguridad ventana privada si existe
	        pc4.PrivatePortal();     
	        /*
	         *                                             PASO 2
	         ******************************************************************************************/
	        addStep("Ingresar las credenciales correspondientes al perfil de Cometa para el inicio de sesion");
	        boolean resultStep2 = pc4.IniciarSesionNoToken(User, Password, true);
	        assertTrue(resultStep2, "No se pudo iniciar sesion");        
	        /*
	         *                                             PASO 3
	         ******************************************************************************************/
	        addStep("Ingresar a Portal Cometa");
	        boolean resultStep3 = pc4.seleccionarPortalCometaQA(true);
	        assertTrue(resultStep3, "No es visible el elemento de Cometa");
	        /*
	         *                                             PASO 4
	         ******************************************************************************************/
	        addStep("Ingresar a DIRECTOS");
	        boolean resultStep4 = pc4.clicBotonCometaDirectos(true);
	        assertTrue(resultStep4, "No se encontro el elemento Directos");
	        /*
	         *                                             PASO 5
	         ******************************************************************************************/
	        addStep("Seleccionar Devoluciones");
	        boolean resultStep4_1 = pc4.ClicBotonDevoluciones(true);
	        assertTrue(resultStep4_1,"No se encontro el elemento Devoluciones");
	        /*
	         *                                         PASO 10
	         ******************************************************************************************/
	        addStep("Validar que al hacer clic en una fila del grid se abre el ticket de Devoluciones");
	        boolean r12 = pc4.abrirTicketDesdeGridYValidarVista(true); 
	        assertTrue(r12, "No se redirigió correctamente al ticket de Devoluciones");
	        /*
	         *                                         PASO 11
	         ******************************************************************************************/
	        addStep("Validar que el ticket mostrado corresponda exactamente al seleccionado");
	        boolean rA = pc4.validarTicketCoincideConSeleccion(true);
	        assertTrue(rA, "El ticket abierto no corresponde al folio seleccionado en el grid");
	        /*
	         *                                         PASO 12
	         ******************************************************************************************/
	        addStep("Validar que la pantalla muestre correctamente los datos de la devolución");
	        boolean rB = pc4.validarDatosDeDevolucion(true);
	        assertTrue(rB, "Los datos de la vista detalles no coinciden con los del grid/filtros");
	        /*
	         *                                         PASO 13
	         ******************************************************************************************/
	        addStep("Validar que en cada ícono de navegación debe ser funcional y actualizar el contenido al hacer clic");
	        boolean ch = pc4.validarSiguienteYUltimoDistintosAInicial(true);
	        assertTrue(ch, "No funcionan los botones de paginacion de la vista preliminar");


	}

	@Override
	public void beforeTest() {
		// TODO Auto-generated method stub
		return;
		
	}
	@AfterMethod
    public void afterMethod(){
    	u.getDriver().quit();
    }
	@Override
	public String setPrerequisites() {
		// TODO Auto-generated method stub
		return "1. Credenciales para el perfil DIRECTOS, con usuario  [Admin, OXXO OS, OXXO Plaza, Administrativo Operativo, Comercial, CEDIS] \r\n"
				+ "2. Liga de Portal Cometa";
	}

	@Override
	public String setTestDescription() {
		// TODO Auto-generated method stub
		return "Validar que en cada ícono debe ser funcional y actualizar el contenido al hacer clic.\r\n"
				+ "Nota: Se va a revisar las implicaciones de agarrar los PDF desde el storage y en caso de que la funcion de \"pagina siguiente\" "
				+ "no se vea como lo habiamos planeado se revisaran opciones. La prioridad es que si lo tomemos desde el storage ";
	}

	@Override
	public String setTestDesigner() {
		return"Jose Pecina";
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_464_IconosNavegacionFuncionalesDevoluciones_test";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}
}