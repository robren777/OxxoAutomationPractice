package WEB.mermasDeCafe.scripts;

import java.util.Date;
import java.util.HashMap;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import com.ibm.icu.text.SimpleDateFormat;

import WEB.mermasDeCafe.om.MermasDeCafe;
import modelo.BaseExecution;
import utils.selenium.ChromeTest;
import utils.selenium.SeleniumUtil;

public class ATC_FT_066_Permiso_de_transferencia_usuario_Asesor extends BaseExecution {

	private SeleniumUtil u;
	private MermasDeCafe mdc;

	@Test(dataProvider = "data-provider")
	public void ATC_FT_066_Permiso_de_transferencia_usuario_Asesor_test(HashMap<String, String> data) throws Exception {
		String user = data.get("Usuario");
		String ps = data.get("Contrasenia");
		String tienda = data.get("Tienda");
		String fechaInicial = data.get("FechaInicial");
		String horaInicial = data.get("HoraInicial");
		String fechaFinal = data.get("FechaFinal");
		String horaFinal = data.get("HoraFinal");
		String habilitar = data.get("Habilitar");

		Date date = new Date();
		SimpleDateFormat DateFor = new SimpleDateFormat("yyyy-MM-dd");
		String fecha = DateFor.format(date);

		u = new SeleniumUtil(new ChromeTest(), true);
		mdc = new MermasDeCafe(u, testCase);

		addStep("Ingresar a Access Control");
		mdc.Ingresar(user, ps);

		addStep("Ingresar a Merma Autorizada en POS");
		mdc.clickMermaAutorizada();

		addStep("Ingresar a Transferencia");
		mdc.clickTransferencias();

		addStep("Ingresar a Transferencia entre Tiendas");
		mdc.clickTransferenciaEntreTiendas();

		addStep("Se da click en el combo Tienda");
		mdc.clickTienda();
		addStep("Se selecciona una tienda");
		mdc.seleccionarTienda(tienda);

		addStep("Se selecciona una fecha inicial y la hora de inicio de vigencia de Transferencia");
		mdc.seleccionarFechaInicial(fechaInicial, horaInicial);

		addStep("Se selecciona una fecha final y la hora de vencimiento de vigencia de Transferencia");
		mdc.seleccionarFechaFinal(fechaFinal, horaFinal);

		addStep("Se selecciona la opción \"SI\" en el check de Empoderamiento Salida de Transferencia");
		mdc.empoderamientoSalidadeTransferencia(habilitar);

		addStep("Se presiona el botón guardar.");
		mdc.clickGuardar();

		addStep("Presionar \"OK\" En la ventana que se muestra con mensaje: \"Se ha registrado con éxito\"");
		Assert.assertTrue(mdc.validarMensaje(), "no se guardaron los datos");
		mdc.clickOk();

		addStep("Validar en el listado de la tabla \"Catálogo de Empoderamiento de Salida de Transferencia\" se vea reflejado el nuevo registro.");
		Assert.assertTrue(mdc.validarActualizacionRegistro(fecha), "no se muestra el nuevo registro en la tabla");
	}

	@Override
	public void beforeTest() {
	}

	@Override
	public String setTestDescription() {

		return "Validar que con usuario Asesor pueda asignar permisos de transferencia entre tiendas al Lider en la plataforma Web en un tiempo establecido, este usuario solo podrá otorgar permisos a sus tiendas asignadas.";
	}

	@Override
	public String setTestDesigner() {
		return "Sharon S. Estrada Martinez";
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_066_Permiso_de_transferencia_usuario_Asesor_test";
	}

	@Override
	public String setTestInstanceID() {
		return null;
	}

	@Override
	public String setPrerequisites() {
		return "Contar con credenciales de acceso al portal web \"Portal aplicaciones oxxo.";
	}

	@AfterMethod
	public void afterMethod() {
		u.quit();
	}
}
