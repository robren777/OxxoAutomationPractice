package RDM.ruteoDinamico.tipoDistroUnigis;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

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
 * DESCRIPCION: Validar que se presente la informaci�n del registro creado en pantalla emergente Nuevo Distro
 * @author cristhian.m.gonzalez@accenture.com
 */

public class ATC_FT_010_ValidarNuevoDistro extends BaseExecution{
	
	@Test(dataProvider = "data-provider")
	public void ATC_FT_010_ValidarNuevoDistro_test(HashMap<String, String> data) throws Exception {
		/*
		 * VARIABLES
		 */
		String tipoDistro = data.get("tipoDistro");
		String btnYesConfirmacion = "btnYes";
		String mensajeConfirmacion = "confirmacion";
		String campoTipoDistro = "tipoDistroRD";
		String tablaDistroUnigis = "tablaDistroUnigis";
		String campoDatoCreado = "tipoDistroNuevoD";
		Map<String, String> mapCampoValor = new LinkedHashMap<String, String>();
		mapCampoValor.put("tipoDistroNvoD", tipoDistro);
		mapCampoValor.put("tipoDistroUnigisNvoD", data.get("tipoDistroUnigis"));
		mapCampoValor.put("enviarUnigisNvoD", data.get("enviarUnigis"));
		
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
		addStep("Clic en el bot�n \"Nuevo Distro\"");
		System.out.println("PASO 5");
		
		rd.clickNuevoDistro();
		
		// PASO 6
		addStep("Llenar los campos requeridos para el nuevo distro");
		System.out.println("PASO 6");
		
		for (Entry<String, String> campoValor : mapCampoValor.entrySet()){
			String campo = campoValor.getKey();
			String valor = campoValor.getValue();
			
			rd.establecerDatoCampo(campo, valor);
		}
		
		// PASO 7
		addStep("Clic en el bot�n \"Guardar\"");
		System.out.println("PASO 7");
		
		rd.clickGuardar();
		
		// PASO 8
		addStep("Clic en el bot�n \"Yes\"");
		System.out.println("PASO 8");
		
		rd.validarMensajeCuadroDialogo(mensajeConfirmacion, btnYesConfirmacion);
		
		// PASO 9
		addStep("Validar que se haya creado el registro de Nuevo Distro en la tabla");
		System.out.println("PASO 9");
		
		rd.clickClear();
		rd.establecerDatoCampo(campoTipoDistro, tipoDistro);
		rd.clickExecute();
		
		assertFalse(rd.validarTablaVacia(tablaDistroUnigis), "La tabla est� vac�a");
		assertTrue(rd.validarExistenciaCampo(campoDatoCreado),"No se cre� el registro");

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
				+ " del registro creado en pantalla "
				+ "emergente Nuevo Distro";
	}

	@Override
	public String setTestDesigner() {
		// TODO Auto-generated method stub
		return "cristhian.m.gonzalez@accenture.com";
	}

	@Override
	public String setTestFullName() {
		// TODO Auto-generated method stub
		return "ATC_FT_010_ValidarNuevoDistro_test";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}

}
