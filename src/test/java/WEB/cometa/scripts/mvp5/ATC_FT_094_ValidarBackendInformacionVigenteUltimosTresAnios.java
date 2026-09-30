package WEB.cometa.scripts.mvp5;

import static org.testng.Assert.assertTrue;

import java.util.HashMap;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import WEB.cometa.om.devolucionesEspecialesPage;
import modelo.BaseExecution;
import utils.selenium.SeleniumUtil;

/**DESCRIPCION: Validar que el backend de Devoluciones Especiales considere solo informacion vigente de los ultimos tres anios.
 * *
*NOMBRE CASOS DE PRUEBA MANUAL QUE SE ABARCAN:
* MTC_FT_094 - Validar que el backend considere solo la informacion vigente de los ultimos tres anios
* 
*PREREQUISITOS:
*1. Acceso tecnico a la base de datos PostgreSQL COMETA
*2.Liga de Portal Cometa
*
*INSUMOS: 
*Credenciales tecnicas configuradas en GlobalVariables para consultar COMETA.
*Liga externa de red libre https://pruebasweb.oxxo.com/AuthFemcoProx/
*
* @author Michelle Ceja
* @date Julio 2026
*/
public class ATC_FT_094_ValidarBackendInformacionVigenteUltimosTresAnios extends BaseExecution {

	SeleniumUtil u;

	@Test(dataProvider = "data-provider")
	public void ATC_FT_094_ValidarBackendInformacionVigenteUltimosTresAnios_test(HashMap<String, String> data) throws Exception {
		testCase.setProject_Name("I22089-Evolucion del proceso de recibo en Tienda Cedis | MVP5");

		devolucionesEspecialesPage devEspecialesPage = new devolucionesEspecialesPage(u, testCase);

		String perfil = "BACKEND";

		/*
		 * PRECONDICION
		 ***************************************************************************/
		addStep("Precondicion: validar acceso tecnico a PostgreSQL COMETA con perfil " + perfil);
		testCase.addBoldTextEvidenceCurrentStep("Validacion backend con acceso tecnico a PostgreSQL COMETA.");
		System.out.println("[PERFIL EN PRUEBA] Perfil: " + perfil);

		/*
		 *                                             PASO 1
		 ***************************************************************************/
		addStep("Acceder a la base de datos PostgreSQL COMETA con usuario tecnico autorizado");
		boolean resultStep1 = devEspecialesPage.validarBackendInformacionVigenteUltimosTresAnios(false);
		assertTrue(resultStep1, "No fue posible validar la informacion vigente de los ultimos tres anios en backend COMETA");
		testCase.addTextEvidenceCurrentStep("Conexion a PostgreSQL COMETA establecida correctamente y consulta backend ejecutada.");

		/*
		 *                                             PASO 2
		 ***************************************************************************/
		addStep("Ejecutar consulta de apoyo sobre historico con fechas dentro y fuera de la ventana permitida");
		testCase.addTextEvidenceCurrentStep("Resultado de consulta backend:\n"
				+ devEspecialesPage.obtenerUltimoResultadoConsultaBackendUltimosTresAnios());

		/*
		 *                                             PASO 3
		 ***************************************************************************/
		addStep("Validar que los registros fuera del rango de tres anios no se consideren en la consulta");
		testCase.addTextEvidenceCurrentStep("Resultado de consulta backend:\n"
				+ devEspecialesPage.obtenerUltimoResultadoConsultaBackendUltimosTresAnios());

		/*
		 *                                             PASO 4
		 ***************************************************************************/
		addStep("Verificar comportamiento conforme a la parametrizacion vigente");
		testCase.addTextEvidenceCurrentStep("Rango de fechas validado: "
				+ devEspecialesPage.obtenerUltimoRangoFechasBackendValidado() + ".");

	}

	@AfterMethod
	public void afterMethod() {
		if (u != null) {
			u.quit();
		}

	}

	@Override
	public void beforeTest() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String setPrerequisites() {
		// TODO Auto-generated method stub
		return "*1. Acceso tecnico a la base de datos PostgreSQL COMETA      \r\n"
				+ "*2.Liga de Portal Cometa";
	}

	@Override
	public String setTestDescription() {
		return "Validar que el backend de Devoluciones Especiales considere solo informacion vigente de los ultimos tres anios.";
	}

	@Override
	public String setTestDesigner() {
		// TODO Auto-generated method stub
		return "Michelle Ceja";
	}

	@Override
	public String setTestFullName() {
		// TODO Auto-generated method stub
		return "ATC_FT_094_ValidarBackendInformacionVigenteUltimosTresAnios_test";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}
}