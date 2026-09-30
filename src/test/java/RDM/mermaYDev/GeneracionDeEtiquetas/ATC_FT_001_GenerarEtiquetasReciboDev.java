package RDM.mermaYDev.GeneracionDeEtiquetas;

import static org.testng.Assert.assertNotNull;

import java.util.HashMap;

import org.testng.annotations.Test;

import RDM.util.MermaYDev;
import modelo.BaseExecution; import RDM.util.GlobalVariables;
import utils.selenium.IETest;
import utils.selenium.SeleniumUtil;
import utils.sql.SQLUtil;

public class ATC_FT_001_GenerarEtiquetasReciboDev extends BaseExecution {

	@Test(dataProvider = "data-provider")
	public void ATC_FT_001_GeneracionReporteProgramacionCita_Test(HashMap<String, String> data) throws Exception {

		/*
		 * VARIABLES
		 */
		String cantidadEtiquetas = data.get("cantidadEtiquetas");

		/*
		 * UTILERIAS
		 */
		SQLUtil dbRdm = new SQLUtil(GlobalVariables.DB_HOST_RDM, GlobalVariables.DB_USER_RDM,
				GlobalVariables.DB_PASSWORD_RDM);
		SeleniumUtil u = new SeleniumUtil(new IETest());
		MermaYDev m = new MermaYDev(u, testCase, data.get("server"));

		/*
		 * PASOS DEL CASO DE PRUEBA
		 */
		
		System.out.println(dbRdm.getHost());
		
		// PASO 1
		addStep("Ingresar usuario y contrasena en el sistema RDM y presionar el boton \"Login\"");
		System.out.println("PASO 1");
		
		m.logIn();
		
		// PASO 2
		addStep("Dar click en la opcion \"Support Functions\" del menu principal. "
				+ "Dar click en la opcion: Processing / Returns Setup. "
				+ "Dar click en la opcion Reprint / Null Labels.");
		System.out.println("PASO 2");
		
		m.clickMenuSupportProcessingReprint();
		
		// PASO 3
		addStep("Posicionarse en el campo GENERIC LABEL QTY y teclear la cantidad "
				+ "de etiquetas requeridas. "
				+ "Presionar el boton \"Reprint\".");
		System.out.println("PASO 3");
		
		m.reprintEtiquetas(cantidadEtiquetas);
		
		// PASO 4
		addStep("Verificar que en la pestana Destination se muestre la siguiente informacion: "
				+ "-DESTYPE: FILE "
				+ "-DESFORMAT: LABEL "
				+ "Dar click en el boton \"Save\" para desplegar la informacion.");
		System.out.println("PASO 4");
		
		String urlPopUp = m.generarArchivo();
		assertNotNull(urlPopUp, "No se muestra el reporte de reimpresion de etiquetas");
		
		u.close();
				
	}

	@Override
	public void beforeTest() {
	}

	@Override
	public String setPrerequisites() {
		return "1) Permitir ventanas emergentes en internet explorer "
				+ "2) Colocar la fecha de la devolucion que se requiere imprimir la etiqueta";
	}

	@Override
	public String setTestDescription() {
		return "Construido. Mediante el sistema RDM se debe generar las etiquetas para el recibo de devoluciones.";

	}

	@Override
	public String setTestDesigner() {
		return "guerrero.vargas@neoris.com";
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_001_GeneracionReporteProgramacionCita_Test";
	}

	@Override
	public String setTestInstanceID() {
		return null;
	}

}
