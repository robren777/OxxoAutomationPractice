package RDM.ruteoDinamico.tipoDistroUnigis;

import static org.testng.Assert.assertTrue;

import java.util.ArrayList;
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
 * DESCRIPCION: Validar que ya no se presente la informaci�n del distro seleccionado al dar click en el bot�n 
 * 				"Yes" de la pantalla emergente de confirmaci�n para eliminar el distro
 * @author cristhian.m.gonzalez@accenture.com
 */

public class ATC_FT_020_ValidarEliminacionDistro extends BaseExecution{
	
	@Test(dataProvider = "data-provider")
	public void ATC_FT_020_ValidarEliminacionDistro_test(HashMap<String, String> data) throws Exception {
		/*
		 * VARIABLES
		 */
		String tipoDistro = data.get("tipoDistro");
		String btnYesConfirmacion = "btnYes";
		String mensajeConfirmacion = "confirmacion";
		String campoFiltro = "tipoDistroRD";
		String campoDato = "tipoDistroNvoD";
		String mensajeDatoNoEncontrado = "datoNoEncontrado";
		String tablaDistroUnigis = "tablaDistroUnigis";
		ArrayList<String> camposEliminaDistro = new ArrayList<String>();
		camposEliminaDistro.add("lblTipoDistroNvoD"); 
		camposEliminaDistro.add("lblTipoDistroUnigisNvoD");
		camposEliminaDistro.add("lblEnviarUnigisNvoD");
		
		
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
		addStep("Clic en el bot�n \"Distro UNIGIS\"");
		System.out.println("PASO 4");
		
		rd.clickDistroUnigis();
		
		// PASO 5
		addStep("Establecer en el campo Tipo Distro una clave de Distro v�lida (editando el campo)");
		System.out.println("PASO 5");
		
		rd.establecerDatoCampo(campoFiltro, tipoDistro);
		
		// PASO 6
		addStep("Clic en el bot�n \"Execute\"");
		System.out.println("PASO 6");
		
		rd.clickExecute();
		
		// PASO 7
		addStep("Clic en el bot�n \"Elimina Distro\"");
		System.out.println("PASO 7");
		
		rd.clickEliminaDistro();
		
		// PASO 8
		addStep("Validar los campos de informaci�n bloqueados para editar");
		System.out.println("PASO 8");
		
		for (String campo : camposEliminaDistro) {
			rd.establecerVacioCampo(campo);
		}
		
		// PASO 9
		addStep("Clic en el bot�n \"Eliminar\"");
		System.out.println("PASO 9");
		
		rd.clickEliminar();
		
		// PASO 10
		addStep("Clic en el bot�n \"Yes\"");
		System.out.println("PASO 10");
		
		rd.validarMensajeCuadroDialogo(mensajeConfirmacion, btnYesConfirmacion);
		
		// PASO 11
		addStep("Validar que se muestre la pantalla de Tipo Distro a Unigis sin el registro en la tabla");
		System.out.println("PASO 11");
		
		rd.clickClear();
		rd.establecerDatoCampo(campoFiltro, tipoDistro);
		rd.clickExecute();
		
		assertTrue(rd.validarMensajeCuadroDialogo(mensajeDatoNoEncontrado, btnYesConfirmacion),"No se mostr� mensaje de datos no encontrados");
		assertTrue(rd.validarTablaVacia(tablaDistroUnigis), "La tabla no est� vac�a");
		assertTrue(rd.validarExistenciaCampo(campoDato),"No se realizo la eliminacion del registro");

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
		return "Validar que ya no se presente la informaci�n"
				+ " del distro seleccionado al dar click en el"
				+ " bot�n \"Yes\" de la pantalla emergente de"
				+ " confirmaci�n para eliminar el distro";
	}

	@Override
	public String setTestDesigner() {
		// TODO Auto-generated method stub
		return "cristhian.m.gonzalez@accenture.com";
	}

	@Override
	public String setTestFullName() {
		// TODO Auto-generated method stub
		return "ATC_FT_020_ValidarEliminacionDistro_test";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}

}
