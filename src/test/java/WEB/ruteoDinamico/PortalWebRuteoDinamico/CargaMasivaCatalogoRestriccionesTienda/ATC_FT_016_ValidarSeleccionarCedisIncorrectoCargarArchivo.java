package WEB.ruteoDinamico.PortalWebRuteoDinamico.CargaMasivaCatalogoRestriccionesTienda;

import static org.testng.Assert.assertTrue;

import java.util.HashMap;

import org.testng.annotations.Test;

import WEB.util.PortalWebRuteoDinamico.POMCargaMasivaCatalogoRestTienda;
import WEB.util.PortalWebRuteoDinamico.POMLoginPortal;
import config.Constants;
import modelo.BaseExecution;
import utils.selenium.MEdgeTest;
import utils.selenium.SeleniumUtil;

/**
 * PREREQUISITOS: 1.- Tener instalado navegador web (Chrome, Firefox o Microsoft Edge)
				  2.- Acceso al servidor WEB (VPN, Puertos)
 * 
 * 
 * DESCRIPCION: Validar en la tabla de archivos cargados la columna errores sea igual al numero
 * 				de registros en el archivo cargado con el cedis seleccionado incorrecto
 * @author cristhian.m.gonzalez@accenture.com
 */

public class ATC_FT_016_ValidarSeleccionarCedisIncorrectoCargarArchivo extends BaseExecution {
	
	@Test(dataProvider = "data-provider")
	public void ATC_FT_016_ValidarSeleccionarCedisIncorrectoCargarArchivo_test(HashMap<String, String> data) throws Exception {
		/*
		 * VARIABLES
		 */
		String cedis = data.get("cedis");
		String pathFile = Constants.BASE_PATH + "\\" + data.get("pathFile");
		String mensajeModal = data.get("mensajeModal");
		int totalRegistros, erroresRegistros;
		
		/*
		 * UTILERIAS
		 */
		SeleniumUtil u = new SeleniumUtil(new MEdgeTest(), true);
		POMLoginPortal pomLP = new POMLoginPortal(u, testCase);
		POMCargaMasivaCatalogoRestTienda pomCMCRT = new POMCargaMasivaCatalogoRestTienda(u, testCase);
		
		/*
		 * PASOS DEL CASO DE PRUEBA
		 */
		
		// PASO 1
		addStep("Hacer login en Portal Web Ruteo Dinamico");
		System.out.println("PASO 1");
		
		assertTrue(pomLP.loginAccessControl(), "No se pudo iniciar sesion");
		
		// PASO 2
		addStep("Clic en icono PORTAL RUTEO DINAMICO");
		System.out.println("PASO 2");
		
		pomLP.clickPortalRuteoDinamico();
		
		// PASO 3
		addStep("Clic en \"Carga Masiva CRT\"");
		System.out.println("PASO 3");
		
		pomCMCRT.clickCargaMasivaCRT();
		
		// PASO 4
		addStep("Clic en \"Cargar Archivo\"");
		System.out.println("PASO 4");
		
		pomCMCRT.clickCargarArchivo();
		
		// PASO 5
		addStep("Clic en menu desplegable \"Lista de Cedis\" y seleccionar cedis diferente del cedis del archivo");
		System.out.println("PASO 5");
		
		pomCMCRT.seleccionarCedis(cedis);
		
		// PASO 6
		addStep("Seleccionar archivo");
		System.out.println("PASO 6");
		
		pomCMCRT.seleccionarArchivo(pathFile);
		
		// PASO 7
		addStep("Clic en \"Guardar\"");
		System.out.println("PASO 7");
		
		pomCMCRT.clickGuardar();
		
		// PASO 8
		addStep("Clic en \"Aceptar\" del modal");
		System.out.println("PASO 8");
		
		assertTrue(pomCMCRT.validarMensajeModal(mensajeModal), "No se mostro el modal de carga exitosa del archivo");
		
		// PASO 9
		addStep("Validar que todos los registros del archivo cargado se vean con error en la tabla");
		System.out.println("PASO 9");
		
		totalRegistros = Integer.valueOf(pomCMCRT.obtenerDatoTabla(0, 7));
		erroresRegistros = Integer.valueOf(pomCMCRT.obtenerDatoTabla(0, 9));
		assertTrue(pomCMCRT.validarRegistrosConErrorCedisIncorrecto(totalRegistros, erroresRegistros), "No se mostraron los registros"
				+ " errones con el cedis incorrecto");
		
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
		return "1.- Tener instalado navegador web (Chrome, Firefox o Microsoft Edge)"
				+ "2.- Acceso al servidor WEB (VPN, Puertos)";
	}

	@Override
	public String setTestDescription() {
		// TODO Auto-generated method stub
		return "Validar en la tabla de archivos cargados la columna errores"
				+ " sea igual al numero de registros en el archivo cargado"
				+ " con el cedis seleccionado incorrecto";
	}

	@Override
	public String setTestDesigner() {
		// TODO Auto-generated method stub
		return "cristhian.m.gonzalez@accenture.com";
	}

	@Override
	public String setTestFullName() {
		// TODO Auto-generated method stub
		return "ATC_FT_016_ValidarSeleccionarCedisIncorrectoCargarArchivo_test";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}

}
