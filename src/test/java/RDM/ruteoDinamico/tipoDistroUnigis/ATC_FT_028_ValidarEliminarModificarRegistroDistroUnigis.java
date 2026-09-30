package RDM.ruteoDinamico.tipoDistroUnigis;

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
 * DESCRIPCION: Validar que se pueda modificar un registro despu�s de haber dado clic al bot�n "Cancelar" de la 
 * 				pantalla emergente Eliminar Distro 
 * @author cristhian.m.gonzalez@accenture.com
 */

public class ATC_FT_028_ValidarEliminarModificarRegistroDistroUnigis extends BaseExecution{
	
	@Test(dataProvider = "data-provider")
	public void ATC_FT_028_ValidarEliminarModificarRegistroDistroUnigis_test(HashMap<String, String> data) throws Exception {
		/*
		 * VARIABLES
		 */
		String valorTipoDistro = data.get("tipoDistro");
		String btnYesConfirmacion = "btnYes";
		String btnYesError = "yesRD";
		String mensajeConfirmacion = "confirmacion";
		String mensajeError = "errorTipoDistroVacio";
		String vaciarCampoTipoDistro = "lblTipoDistroNvoD";
		String campoTipoDistro = "tipoDistroNvoD";
		
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
		addStep("Clic en el bot�n \"Execute\"");
		System.out.println("PASO 5");
		
		rd.clickExecute();
		
		// PASO 6
		addStep("Clic en el bot�n \"Elimina Distro\"");
		System.out.println("PASO 6");
		
		rd.clickEliminaDistro();
		
		// PASO 7
		addStep("Clic en el bot�n \"Cancelar\"");
		System.out.println("PASO 7");
		
		rd.clickCancelar();
		
		// PASO 8
		addStep("Clic en el bot�n \"Modifica Distro\"");
		System.out.println("PASO 8");
		
		rd.clickModificaDistro();
		
		// PASO 9
		addStep("Modificar cualquier campo");
		System.out.println("PASO 9");
		
		rd.establecerVacioCampo(vaciarCampoTipoDistro);
		rd.clickGuardar();
		rd.validarMensajeCuadroDialogo(mensajeConfirmacion, btnYesConfirmacion);
		assertTrue(rd.validarMensajeCuadroDialogo(mensajeError, btnYesError), "No se mostr� mensaje de error del campo Tipo Distro");
		rd.establecerDatoCampo(campoTipoDistro, valorTipoDistro);

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
		return "Validar que se pueda modificar un registro despu�s"
				+ " de haber dado clic al bot�n \"Cancelar\" de"
				+ " la pantalla emergente Eliminar Distro ";
	}

	@Override
	public String setTestDesigner() {
		// TODO Auto-generated method stub
		return "cristhian.m.gonzalez@accenture.com";
	}

	@Override
	public String setTestFullName() {
		// TODO Auto-generated method stub
		return "ATC_FT_028_ValidarEliminarModificarRegistroDistroUnigis_test";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}

}
