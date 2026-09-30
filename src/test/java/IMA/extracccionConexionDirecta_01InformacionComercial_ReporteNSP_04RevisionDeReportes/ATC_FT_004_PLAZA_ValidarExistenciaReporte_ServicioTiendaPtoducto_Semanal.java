package IMA.extracccionConexionDirecta_01InformacionComercial_ReporteNSP_04RevisionDeReportes;

import static org.testng.Assert.assertTrue;

import java.util.HashMap;

import org.openqa.selenium.By;
import org.testng.annotations.Test;

import modelo.BaseExecution;
import IMA.util.IMATools;
import utils.selenium.ChromeTest;
import utils.selenium.SeleniumUtil;

public class ATC_FT_004_PLAZA_ValidarExistenciaReporte_ServicioTiendaPtoducto_Semanal extends BaseExecution {

	@Test(dataProvider = "data-provider")
	public void ATC_FT_004_PLAZA_ValidarExistenciaReporte_ServicioTiendaPtoducto_Semanal_test(
			HashMap<String, String> data) throws Exception {

		// Utileria
		SeleniumUtil u = new SeleniumUtil(new ChromeTest(), true);
		By icono = By.xpath("//*[@id=\"FolderList\"]/tbody/tr[8]/td[1]/a/span/span");

		IMATools ima = new IMATools(u, testCase);

		System.out.println(icono);
		// paso 1
		addStep("Ingresar a la siguiente dirección: "
				+ "Información Comercial >Informes compartidos > NSP > Plaza en Microstrategy y "
				+ "seleccionar el reporte Nivel de Servicio y Venta Perdida (Semanal)");
		ima.Login();
		ima.goToRouteSubFolder("Información Comercial QA", "NSP/Plaza");

		addStep("Se valida el icono de creacion del reporte");
		boolean exist = ima.validarElemento(icono);
		
		assertTrue(exist, "No existe el icono");
		
		u.hardWait(3);
		u.close();
	}

	@Override
	public void beforeTest() {
		// TODO Auto-generated method stub

	}

	@Override
	public String setTestDescription() {
		// TODO Auto-generated method stub
		return "Validar que el reporte Nivel de Servicio Tienda-Producto (Semanal) se encuentre en la carpeta Plaza";
	}

	@Override
	public String setTestDesigner() {
		// TODO Auto-generated method stub
		return "EQUIPO AUTOMATIZACION";
	}

	@Override
	public String setTestFullName() {
		// TODO Auto-generated method stub
		return "ATC_FT_004_PLAZA_ValidarExistenciaReporte_ServicioTiendaPtoducto_Semanal";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String setPrerequisites() {
		// TODO Auto-generated method stub
		return null;
	}

}