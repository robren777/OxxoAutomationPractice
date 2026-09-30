package RDM.ruteoDinamico.restriccionDeTiendas;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import java.util.HashMap;

import org.testng.annotations.Test;

import RDM.util.RuteoDinamicoRDM;
import modelo.BaseExecution;
import utils.selenium.IETest;
import utils.selenium.SeleniumUtil;

/**
 * PREREQUISITOS: - Tener instalado el JInitiator
 * 				  - Acceso al aplicativo RDM (VPN, Puertos)
 * 				  - Usar Microsoft Edge en modo Internet Explorer
 * 
 * 
 * DESCRIPCION: Validar que se presente la informaci�n del registro modificado en pantalla emergente Modificar Tienda
 * @author cristhian.m.gonzalez@accenture.com
 */

public class ATC_FT_021_ValidarModificacionTienda extends BaseExecution{
	
	@Test(dataProvider = "data-provider")
	public void ATC_FT_021_ValidarModificacionTienda_test(HashMap<String, String> data) throws Exception {
		/*
		 * VARIABLES
		 */
		String valorNombrecliente = data.get("nombreCliente");
		String claveCliente = data.get("claveCliente");
		String btnYesConfirmacion = "btnYes";
		String mensajeConfirmacion = "confirmacion";
		String campoFiltro = "clienteRD";
		String vaciarCampoNombreCliente = "lblClienteNombreNvaT";
		String campoNombreCliente = "clienteNombreNvaT";
		String campoDatoActualizado = "clienteNombreModificaT";
		String tablaRestriccionTiendas = "tablaRestriccionTiendas";
		
		/*
		 * UTILERIAS
		 */
		SeleniumUtil u = new SeleniumUtil(new IETest());
		RuteoDinamicoRDM rd = new RuteoDinamicoRDM(u, testCase, data.get("server"));
		
		/*
		 * PASOS DEL CASO DE PRUEBA
		 */
		
		// PASO 1
		addStep("Acceder al enlace http://fcret3.femcom.net:8036/rdmDES36.html por Microsoft Edge");
		System.out.println("PASO 1");
		
		rd.logIn();
		
		// PASO 2
		addStep("Seguir la ruta dando Clic en Distribution Planning -> XXFC-Programacion y Balanceo -> XXFC-Ruteo Dinamico");
		System.out.println("PASO 2");
		
		rd.clickMenuDistPlanProgramBalanceoRuteoDinamico();
		
		// PASO 3
		addStep("Clic en el bot�n \"Ver TSF Unigis\"");
		System.out.println("PASO 3");
		
		rd.clickVerTSFUnigis();
		
		// PASO 4
		addStep("Clic en el bot�n \"Restric. Tiendas\"");
		System.out.println("PASO 4");
		
		rd.clickRestricTiendas();
		
		// PASO 5
		addStep("Establecer en el campo Cliente una clave de Cliente v�lida (editando el campo)");
		System.out.println("PASO 5");
		
		rd.establecerDatoCampo(campoFiltro, claveCliente);
		
		// PASO 6
		addStep("Clic en el bot�n \"Execute\"");
		System.out.println("PASO 6");
		
		rd.clickExecute();
		
		// PASO 7
		addStep("Clic en el bot�n \"Modifica Tienda\"");
		System.out.println("PASO 7");
		
		rd.clickModificaTienda();
		
		// PASO 8
		addStep("Modificar los campos requeridos de la tienda seleccionada");
		System.out.println("PASO 8");
		
		rd.establecerVacioCampo(vaciarCampoNombreCliente);
		rd.establecerDatoCampo(campoNombreCliente, valorNombrecliente);
		
		// PASO 9
		addStep("Clic en el bot�n \"Guardar\"");
		System.out.println("PASO 9");
		
		rd.clickGuardar();
		
		// PASO 10
		addStep("Clic en el bot�n \"Yes\"");
		System.out.println("PASO 10");
		
		rd.validarMensajeCuadroDialogo(mensajeConfirmacion, btnYesConfirmacion);
		
		// PASO 11
		addStep("Validar que se muestre la pantalla de Restricci�n de Tiendas con el registro actualizado");
		System.out.println("PASO 11");
		
		rd.establecerDatoCampo(campoFiltro, claveCliente);
		rd.clickExecute();
		
		assertFalse(rd.validarTablaVacia(tablaRestriccionTiendas), "La tabla est� vac�a");
		assertTrue(rd.validarExistenciaCampo(campoDatoActualizado),"No se actualiz� el registro");

		// CERRAR APLICATIVO RDM
		System.out.println("Cerrando el aplicativo de RDM");
		
		u.close();
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
		return "Validar que se presente la informaci�n"
				+ " del registro modificado en pantalla"
				+ " emergente Modificar Tienda";
	}

	@Override
	public String setTestDesigner() {
		// TODO Auto-generated method stub
		return "cristhian.m.gonzalez@accenture.com";
	}

	@Override
	public String setTestFullName() {
		// TODO Auto-generated method stub
		return "ATC_FT_021_ValidarModificacionTienda_test";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}

}
