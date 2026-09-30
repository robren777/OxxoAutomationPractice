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
 * DESCRIPCION: Validar campos vacios de pantalla emergente Modificar Distro
 * @author cristhian.m.gonzalez@accenture.com
 */

public class ATC_FT_014_ValidarCamposVaciosModificarDistro extends BaseExecution{
	
	@Test(dataProvider = "data-provider")
	public void ATC_FT_014_ValidarCamposVaciosModificarDistro_test(HashMap<String, String> data) throws Exception {
		/*
		 * VARIABLES
		 */
		String tipoDistro = data.get("distro");
		String btnYesConfirmacion = "btnYes";
		String btnYesError = "yesRD";
		String mensajeConfirmacion = "confirmacion";
		String campoFiltro = "tipoDistroRD";
		ArrayList<String> validacionesNuevoDistro = new ArrayList<String>();
		validacionesNuevoDistro.add("Tipo Distro,tipoDistroNvoD,lblTipoDistroNvoD," + data.get("tipoDistro") + ",llenarCampos"); 
		validacionesNuevoDistro.add("Tipo Distro UNIGIS,tipoDistroUnigisNvoD,lblTipoDistroUnigisNvoD," + data.get("tipoDistroUnigis") + ",llenarCampos");
		validacionesNuevoDistro.add("Enviar a Unigis,enviarUnigisNvoD,lblEnviarUnigisNvoD," + data.get("enviarUnigisDiferenteSN") + ",errorEnvioUnigisVacio");
		validacionesNuevoDistro.add("Enviar a Unigis,enviarUnigisNvoD,lblEnviarUnigisNvoD," + data.get("enviarUnigis") + ",errorEnvioUnigisSN");
		
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
		addStep("Clic en el bot�n \"Modifica Distro\"");
		System.out.println("PASO 7");
		
		rd.clickModificaDistro();
		
		int i = 8;
		for (String validacion : validacionesNuevoDistro){
			String[] dataNvoD = validacion.split(",");
			String nombreCampo = dataNvoD[0];
			String campo = dataNvoD[1];
			String campoVacio = dataNvoD[2];
			String campoDato = dataNvoD[3];
			String mensajeError = dataNvoD[4];
			
			if(mensajeError.contains("errorEnvioUnigisSN")) {
				// PASO 8
				addStep("Clic en el bot�n \"Guardar\" con el campo Enviar a Unigis con valor distinto de S � N");
				System.out.println("PASO " + i);
				
				rd.clickGuardar();
				i++;
				
				// PASO 9
				addStep("Clic en el bot�n \"Yes\"");
				System.out.println("PASO " + i);
				
				rd.validarMensajeCuadroDialogo(mensajeConfirmacion, btnYesConfirmacion);
				i++;
				
				// PASO 10
				addStep("Validar mensaje de el campo Enviar a Unigis solo permite S o N como valor y corregirlo");
				System.out.println("PASO " + i);
				
				assertTrue(rd.validarMensajeCuadroDialogo(mensajeError, btnYesError), "No se mostr� mensaje de error de campo " + nombreCampo);
				rd.nBackspace(1);
				rd.establecerDatoCampo(campo, campoDato);
				i++;
			} else {
				if (mensajeError.contains("llenarCampos")) {
					rd.establecerVacioCampo(campoVacio);
					
					// PASO 8
					addStep("Clic en el bot�n \"Guardar\" con el campo " + nombreCampo + " vacio");
					System.out.println("PASO " + i);
					
					rd.clickGuardar();
					i++;
					
					// PASO 9
					addStep("Validar mensaje del campo " + nombreCampo + " vacio y llenarlo");
					System.out.println("PASO " + i);
					
					assertTrue(rd.validarMensajeCuadroDialogo(mensajeError, btnYesConfirmacion), "No se mostr� mensaje de error del campo " + nombreCampo);
					rd.establecerDatoCampo(campo, campoDato);
					i++;
				} else {
					rd.establecerVacioCampo(campoVacio);
					
					// PASO 8
					addStep("Clic en el bot�n \"Guardar\" con el campo " + nombreCampo + " vacio");
					System.out.println("PASO " + i);
					
					rd.clickGuardar();
					i++;
					
					// PASO 9
					addStep("Clic en el bot�n \"Yes\"");
					System.out.println("PASO " + i);
					
					rd.validarMensajeCuadroDialogo(mensajeConfirmacion, btnYesConfirmacion);
					i++;
					
					// PASO 10
					addStep("Validar mensaje del campo " + nombreCampo + " vacio y llenarlo");
					System.out.println("PASO " + i);
					
					assertTrue(rd.validarMensajeCuadroDialogo(mensajeError, btnYesError), "No se mostr� mensaje de error del campo " + nombreCampo);
					rd.establecerDatoCampo(campo, campoDato);
					i++;
				}
			}
		}

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
		return "Validar campos vacios de pantalla emergente Modificar Distro";
	}

	@Override
	public String setTestDesigner() {
		// TODO Auto-generated method stub
		return "cristhian.m.gonzalez@accenture.com";
	}

	@Override
	public String setTestFullName() {
		// TODO Auto-generated method stub
		return "ATC_FT_014_ValidarCamposVaciosModificarDistro_test";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}

}
