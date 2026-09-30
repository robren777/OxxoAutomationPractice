package WEB.mermasDeCafe.scripts;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.ibm.icu.text.SimpleDateFormat;

import WEB.mermasDeCafe.om.MermasDeCafe;
import modelo.BaseExecution;
import utils.selenium.ChromeTest;
import utils.selenium.SeleniumUtil;
import utils.sql.SQLResult;
import utils.sql.SQLUtil;

public class ATC_FT_077_Permiso_de_transferencia_INACTIVO_y_fecha_vigente_Usuario_Asesor extends BaseExecution {
	private SeleniumUtil u;
	private MermasDeCafe mdc;

	@Test(dataProvider = "data-provider")
	public void ATC_FT_077_Permiso_de_transferencia_INACTIVO_y_fecha_vigente_Usuario_Asesor_test(
			HashMap<String, String> data) throws Exception {

		String user = data.get("Usuario");
		String ps = data.get("Contrasenia");
		String plaza = data.get("Plaza");
		String tienda = data.get("Tienda");
		String fechaInicial = data.get("FechaInicial");
		String horaInicial = data.get("HoraInicial");
		String fechaFinal = data.get("FechaFinal");
		String horaFinal = data.get("HoraFinal");
		String habilitar = data.get("Habilitar");
		String opcion = data.get("Opcion");
		String id = null;
		String value = null;

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

		SQLUtil BD_FCTPE = new SQLUtil(WEB.util.GlobalVariables.DB_HOST_FCTPE, WEB.util.GlobalVariables.DB_USER_FCTPE,
				WEB.util.GlobalVariables.DB_PASSWORD_FCTPE);

		String consulta = "select * from tpeuser.TPE_POS_CFG where APPLICATION='MERMA_AUTORIZADA' and PLAZA='" + plaza
				+ "'";
		SQLResult connectionResult = executeQuery(BD_FCTPE, consulta);
		int cantidad_Registros = connectionResult.getRowCount();
		Assert.assertFalse(connectionResult.isEmpty(), "no se pudo realizar la conexion a la base de datos");
		Assert.assertTrue(cantidad_Registros > 0, "no se obtuvieron resultados de la consulta.");

		if (cantidad_Registros > 0) {
			List<String> resultado = new ArrayList<>();
			List<String> columnas = new ArrayList<>();
			columnas = connectionResult.getColumnNames();
			resultado = connectionResult.getFirstRow();
			addStep("El resultado de la consulta es: ");
			testCase.addTextEvidenceCurrentStep(columnas.toString());
			testCase.addTextEvidenceCurrentStep(resultado.toString());

			id = connectionResult.getData(0, "ID");

			if (id != null) {
				consulta = "select * from tpeuser.TPE_CFG_GROUP where NAME='CFG_MERMA' AND ID=" + id + "";
				SQLResult connectionResult2 = executeQuery(BD_FCTPE, consulta);
				int cantidad_Registros2 = connectionResult.getRowCount();
				Assert.assertFalse(connectionResult2.isEmpty(), "no se pudo realizar la conexion a la base de datos");
				Assert.assertTrue(cantidad_Registros2 > 0, "no se obtuvieron resultados de la consulta.");

				if (cantidad_Registros2 > 0) {
					addStep("Validar que el ultimo registro el correspondiente de la consulta anterior arroje la información correcta de la tienda y los permisos establecidos en el campo: ");
					value = connectionResult2.getData(0, "VALUE");
				}
			}
		}
		
		addStep("Seleccionar en la columna opciones el lapicito.");
		mdc.clickEditarOpcionesEmp();
		
		addStep("Modificar el permiso a: Empoderamiento Salida de Transferencia: No.");
		mdc.EmpoderamientoMermaLT(opcion);
		
		addStep("Click en Guardar");
		mdc.clickGuardarModal();

		addStep("Presionar \"OK\" En la ventana que se muestra con mensaje: \"Se ha registrado con éxito\"");
		Assert.assertTrue(mdc.validarMensaje(), "no se guardaron los datos");
		mdc.clickOk();

		consulta = "select * from tpeuser.TPE_POS_CFG where APPLICATION='MERMA_AUTORIZADA' and PLAZA='" + plaza + "'";
		SQLResult connectionResult3 = executeQuery(BD_FCTPE, consulta);
		int registros_cr3 = connectionResult3.getRowCount();
		Assert.assertFalse(connectionResult3.isEmpty(), "no se pudo realizar la conexion a la base de datos");
		Assert.assertTrue(registros_cr3 > 0, "no se obtuvieron resultados de la consulta.");
		
		if (registros_cr3 > 0) {

			id = connectionResult3.getData(0, "ID");

			if (id != null) {
				consulta = "select * from tpeuser.TPE_CFG_GROUP where NAME='CFG_MERMA' AND ID=" + id + "";
				SQLResult connectionResult4 = executeQuery(BD_FCTPE, consulta);
				int registros_cr4 = connectionResult4.getRowCount();
				Assert.assertFalse(connectionResult4.isEmpty(), "no se pudo realizar la conexion a la base de datos");
				Assert.assertTrue(registros_cr4 > 0, "no se obtuvieron resultados de la consulta.");

				if (registros_cr4 > 0) {
					addStep("Validar que el ultimo registro el correspondiente de la consulta anterior arroje la información correcta de la tienda y los permisos establecidos en el campo: ");
					value = connectionResult4.getData(0, "VALUE");
				}
			}
		}
		
		BD_FCTPE.close();
		u.close();
		
	}

	@Override
	public void beforeTest() {
	}

	@Override
	public String setTestDescription() {

		return "Validar que al activar el permiso de transferencia entre tienda, se pueda inhabilitar el permiso aún teniendo fecha vigente de acceso al módulo.";
	}

	@Override
	public String setTestDesigner() {
		return "Sharon S. Estrada Martinez";
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_077_Permiso_de_transferencia_INACTIVO_y_fecha_vigente_Usuario_Asesor";
	}

	@Override
	public String setTestInstanceID() {
		return null;
	}

	@Override
	public String setPrerequisites() {
		return "Contar con credenciales de acceso al portal web \"Portal aplicaciones oxxo. Tener acceso a la BD de fcdbwmq1 mediante ORACLE SQL DEVELOPER.";
	}

}
