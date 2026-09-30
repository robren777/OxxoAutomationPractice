package WEB.ruteoDinamico.PortalWebRuteoDinamico.ConsultaCatalogoRestriccionesTienda;

import static org.testng.Assert.assertTrue;

import java.util.HashMap;

import org.testng.annotations.Test;

import WEB.util.GlobalVariables;
import WEB.util.PortalWebRuteoDinamico.POMCargaMasivaCatalogoRestTienda;
import WEB.util.PortalWebRuteoDinamico.POMConsultaCatalogoRestTienda;
import WEB.util.PortalWebRuteoDinamico.POMLoginPortal;
import config.Constants;
import modelo.BaseExecution;
import utils.selenium.MEdgeTest;
import utils.selenium.SeleniumUtil;
import utils.sql.SQLUtil;

/**
 * PREREQUISITOS: 1.- Tener instalado navegador web (Chrome, Firefox o Microsoft Edge)
				  2.- Acceso al servidor WEB (VPN, Puertos)
 * 
 * 
 * DESCRIPCION: Validar que el boton "Confirmar" de la tabla Preconfirmacion este deshabilitado 
 * 				cuando no haya ningun registro nuevo y ningun registro a actualizar
 * @author cristhian.m.gonzalez@accenture.com
 */

public class ATC_FT_010_ValidarBotonConfirmarDeshabilitado extends BaseExecution {
	
	@Test(dataProvider = "data-provider")
	public void ATC_FT_010_ValidarBotonConfirmarDeshabilitado_test(HashMap<String, String> data) throws Exception {
		/*
		 * VARIABLES
		 */
		String queryUpdateRegistro = data.get("queryUpdateRegistro");
		String pathFile = Constants.BASE_PATH + "\\" + data.get("pathFile");
		String mensajeModal = data.get("mensajeModal");
		String cedis = data.get("cedis");
		String mensaje = data.get("mensajeAlerta");
		int result;
		
		/*
		 * UTILERIAS
		 */
		SeleniumUtil u = new SeleniumUtil(new MEdgeTest(), true);
		SQLUtil dbRdm = new SQLUtil(GlobalVariables.DB_HOST_RDM_RDQA, GlobalVariables.DB_USER_RDM_RD,
				GlobalVariables.DB_PASSWORD_RDM_RDQA);
		POMLoginPortal pomLP = new POMLoginPortal(u, testCase);
		POMCargaMasivaCatalogoRestTienda pomCMCRT = new POMCargaMasivaCatalogoRestTienda(u, testCase);
		POMConsultaCatalogoRestTienda pomCCRT = new POMConsultaCatalogoRestTienda(u, testCase);
		
		/*
		 * PASOS DEL CASO DE PRUEBA
		 */
		
		// EJECUTAR CONSULTA PARA ACTUALIZAR UN REGISTRO DE PRUEBA PARA PROBAR FUNCIONALIDAD VER DETALLE REGISTROS ACTUALIZADOS RDM
		addStep("Se ejecuta query para actualizar un registro de prueba y poder realizar la confirmacion de registros");
		
		testCase.addCodeEvidenceCurrentStep(queryUpdateRegistro);
		result = dbRdm.executeUpdate(queryUpdateRegistro);
		
		// PASO 1
		addStep("Hacer login en Portal Web Ruteo Dinamico");
		System.out.println("PASO 1");
		
		if (result > 0) {
			assertTrue(pomLP.loginAccessControl(), "No se pudo iniciar sesion");
		}
		
		// PASO 2
		addStep("Clic en icono PORTAL RUTEO DINAMICO");
		System.out.println("PASO 2");
		
		pomLP.clickPortalRuteoDinamico();
		
		// PASO 3
		addStep("Realizar proceso de carga de archivo catalogo restriccion tiendas con registro actualizado");
		System.out.println("PASO 3");
		
		pomCMCRT.clickCargaMasivaCRT();
		pomCMCRT.clickCargarArchivo();
		pomCMCRT.seleccionarCedis(cedis);
		pomCMCRT.seleccionarArchivo(pathFile);
		pomCMCRT.clickGuardar();
		assertTrue(pomCMCRT.validarMensajeModal(mensajeModal), "No se mostro el modal de carga exitosa del archivo");
		
		// PASO 4
		addStep("Clic en \"Consultar Catalogo Restriccion Tiendas\"");
		System.out.println("PASO 4");
		
		pomCCRT.clickConsultarCatalogoRestriccionTiendas();
		
		// PASO 5
		addStep("Clic en menu desplegable \"Lista de Cedis\" y seleccionar cedis");
		System.out.println("PASO 5");
		
		pomCCRT.seleccionarCedis(cedis);
		
		// PASO 6
		addStep("Clic en \"Consultar\"");
		System.out.println("PASO 6");
		
		pomCCRT.clickConsultar();
		
		// PASO 7
		addStep("Clic en \"Preconfirmacion\"");
		System.out.println("PASO 7");
		
		pomCCRT.clickPreconfirmacion();
		
		// PASO 8
		addStep("Clic en \"Confirmar\"");
		System.out.println("PASO 8");
		
		pomCCRT.clickConfirmar();
		pomCCRT.validarMensajeAlerta(mensaje);
		
		// PASO 9
		addStep("Clic en \"Preconfirmacion\"");
		System.out.println("PASO 9");
				
		pomCCRT.clickPreconfirmacion();
		
		// PASO 10
		addStep("Validar boton \"Confirmar\" desactivado");
		System.out.println("PASO 10");
		
		assertTrue(pomCCRT.validarBotonConfirmacionDeshabilitado(cedis), "No se mostro desactivado el boton Confirmar");
		
		// CERRAR WEB
		System.out.println("Cerrando Portal Web Ruteo Dinamico");
		
		u.quit();
	}

	@Override
	public void beforeTest() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String setPrerequisites() {
		// TODO Auto-generated method stub
		return "1.- Tener instalado navegador web (Chrome, Firefox o Microsoft Edge)\r\n"
				+ "2.- Acceso al servidor WEB (VPN, Puertos)";
	}

	@Override
	public String setTestDescription() {
		// TODO Auto-generated method stub
		return "Validar que el boton \"Confirmar\" de la tabla Preconfirmacion"
				+ " este deshabilitado cuando no haya ningun registro nuevo y"
				+ " ningun registro a actualizar";
	}

	@Override
	public String setTestDesigner() {
		// TODO Auto-generated method stub
		return "cristhian.m.gonzalez@accenture.com";
	}

	@Override
	public String setTestFullName() {
		// TODO Auto-generated method stub
		return "ATC_FT_010_ValidarBotonConfirmarDeshabilitado_test";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}

}
