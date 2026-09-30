package RDM.rdmBara.ActualizacionRDMBaraCH51947694;

import static org.testng.Assert.assertTrue;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import org.sikuli.script.Pattern;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import RDM.util.metodosAdicionales.MetodosSikulli;
import RDM.util.metodosExtra.LoginRDM;
import modelo.BaseExecution; import RDM.util.GlobalVariables;
import utils.SikuliUtil;
import utils.password.PasswordUtil;
import utils.selenium.IETest;
import utils.selenium.SeleniumUtil;

public class ATC_FT_003_Agendar_cita_para_Orden_de_Compra_seleccionando_mas_de_un_articulo_con_entarimado
		extends BaseExecution {
			SeleniumUtil u = new SeleniumUtil(new IETest(), true);
					/**
	 * REREQUISITOS: 
	 *		1.Orden de Compra para recibir con más de un artículo con más de un artículo  con más de una tarima
		   user = usuario de acceso a rdm bara password = contraseña encriptada para
		 * prov = El proveedor que se usará para registrar. cita = La cita que se va a
		 * consultar y a utilizar para registrar
	 * 
	 * DESCRIPCION:Validar que se considere el tiempo de descarga recalculado al momento de editar el detalle del artículo en la Orden de Compra de acuerdo a los artículos seleccionados a incluir en  la cita a generar seleccionando la opción de Entarimado
	 * 
	 * @author JesusM3@hexaware.com
	 */
	@Test(dataProvider = "data-provider")
	public void ATC_FT_003_Agendar_cita_para_Orden_de_Compra_seleccionando_mas_de_un_articulo_con_entarimado_test(
			HashMap<String, String> data) throws Exception {
		/*
		 * VARIABLES
		 * 
		 * user = usuario de acceso a rdm bara password = contraseña encriptada para
		 * prov = El proveedor que se usará para registrar. cita = La cita que se va a (dp)
		 * consultar y a utilizar para registrar
		 */

		String url = data.get("url");
		String userName = data.get("user");
		String password = PasswordUtil.decryptPassword(data.get("password"));
		String prov = data.get("prov");
		String cita;

		SikuliUtil sk = new SikuliUtil();
		

		String pathImg = sk.PatternPath("RDM\\"+"CH51947694");
		Pattern pathimg = new Pattern();
		ArrayList<String> ListImage = new ArrayList<String>();
		HashMap<String, Pattern> ImgPatter = new HashMap<String, Pattern>();
		ArrayList<Pattern> patterns = new ArrayList<Pattern>();
		File Fol = new File(pathImg);
		MetodosSikulli p = new MetodosSikulli(sk, pathimg, ListImage, ImgPatter, patterns, Fol);
		LoginRDM login = new LoginRDM(u, testCase, url);

		// PASO 1
		addStep("Ingresar a la URL de RDM Bara");
		u.get(login.getUrl());
		u.hardWait(30);
		p.enviarTexto(userName);
		p.nTAB(1);
		p.enviarTexto(password);
		p.nTAB(1);
		u.hardWait(10);
		testCase.addScreenShotCurrentStep("PrimerCaptura");
		p.clickImagen("Login");
		u.hardWait(10);
		testCase.addScreenShotCurrentStep("SegundaCaptura");

		// Paso 2. Ingresar a la ruta: Appointments --> XXFC Appointment Schedule
		p.clickImagen("appointments");
		u.hardWait(10);
		p.clickImagen("appointment_schedule");
		u.hardWait(15);
		testCase.addScreenShotCurrentStep("TerceraCaptura");
		u.hardWait(20);

		// Paso 3. Ingresar la Fecha que se desea agendar la cita en el campo FECHA_CITA
		addStep("Ingresar la Fecha que se desea agendar la cita en el campo FECHA_CITA");
		u.hardWait(5);
		p.doubleClickAndCopyTextBoxRight("fecha_cita", 0);
//		p.clickImagen("fecha_cita");
//		p.clickImagen("fecha_cita");
		u.hardWait(5);
		testCase.addScreenShotCurrentStep("CuartaCaptura");
		u.hardWait(5);

		// Paso 4. Ingresar el Tipo de la Cita a generar en el campo TIPO CITA
		addStep("Ingresar el Tipo de la Cita a generar en el campo TIPO CITA");
		u.hardWait(5);
		p.doubleClickAndCopyTextBoxRight("tipo_cita", 0);
//		p.clickImagen("tipo_cita");
//		p.clickImagen("tipo_cita");
		u.hardWait(5);
		testCase.addScreenShotCurrentStep("QuintaCaptura");
		u.hardWait(5);

		// Paso 5. Ingresar el tipo de transporte en el campo CARRIER
		addStep("Ingresar el tipo de transporte en el campo CARRIER");
		u.hardWait(5);
		p.doubleClickAndCopyTextBoxRight("carrier", 0);
//		p.clickImagen("carrier");
//		p.clickImagen("carrier");
		u.hardWait(5);
		testCase.addScreenShotCurrentStep("SextaCaptura");

		// Paso 6. Ingresar el tipo del Tráiler a recibir en la cita en el campo TRAILER
		addStep("Ingresar el tipo del Tráiler a recibir en la cita en el campo TRAILER");
		u.hardWait(5);
		p.doubleClickAndCopyTextBoxRight("trailer", 0);
//		p.clickImagen("trailer");
//		p.clickImagen("trailer");
		u.hardWait(5);
		testCase.addScreenShotCurrentStep("SeptimaCaptura");

		// Paso 7. Ingresar el número del proveedor en el campo PROVEEDOR
		addStep("Ingresar el número del proveedor en el campo PROVEEDOR");
		u.hardWait(5);
		p.clickImagen("proveedor");
		u.hardWait(3);
		p.enviarTexto(prov);
		u.hardWait(5);
		p.nTAB(1);
		u.hardWait(3);
		testCase.addScreenShotCurrentStep("OctavaCaptura");

		// Paso 8. Seleccionar "Execute"
		addStep("Seleccionar \"Execute\"");
		u.hardWait(5);
		p.clickImagen("execute");
		u.hardWait(5);
		testCase.addScreenShotCurrentStep("NovenaCaptura");

		// Paso 9.Seleccionar una Orden de Compra dando clic en el checkbox
		// correspondiente
		addStep("Seleccionar una Orden de Compra dando clic en el checkbox correspondiente");
		u.hardWait(5);
		p.clickImagen("cita_doble_tarima");
		u.hardWait(5);
		testCase.addScreenShotCurrentStep("DecimaCaptura");

		// Paso 10.Modificar el detalle de la Orden de Compra seleccionada dando doble clic sobre la misma, se selecciona el check box para más de un artículo
		addStep("Modificar el detalle de la Orden de Compra seleccionada dando doble clic sobre la misma, se selecciona el check box para más de un artículo");
		u.hardWait(5);
		p.doubleClickAndCopyTextBoxRight("cita_doble_tarima_checked", 11);
		u.hardWait(10);
		p.clickImagen("order_checkbox_checked");
		u.hardWait(5);
		p.clickImagen("order_checkbox");
		u.hardWait(5);
		p.clickImagen("single_checkbox_checked");
		u.hardWait(5);
		testCase.addScreenShotCurrentStep("DecimoPrimerCaptura");

		// Paso 11. Dar clic en "Save"
		addStep("Dar clic en \"Save\"");
		u.hardWait(5);
		p.clickImagen("save");
		u.hardWait(5);
		testCase.addScreenShotCurrentStep("DecimoSegundaCaptura");

		// Paso 12. Seleccionar el checkbox ENTARIMADO correspondiente a la orden de
		// compra a incluir en la cita
		addStep("Seleccionar el checkbox ENTARIMADO correspondiente a la orden de compra a incluir en la cita");
		u.hardWait(5);
		//p.clickImagen("entarimado_checkbox_checked_yellow");
		u.hardWait(5);
		//p.clickImagen("entarimado_checkbox_yellow");
		u.hardWait(5);
		testCase.addScreenShotCurrentStep("DecimaTercerCaptura");

		// Paso 13. Seleccionar un muelle en el campo MUELLE
		addStep("Seleccionar un muelle en el campo MUELLE");
		u.hardWait(5);
		p.clickImagen("lista_muelle");
		u.hardWait(5);
		p.nDown(8);
		u.hardWait(5);
		p.clickImagen("ok_muelle");
		testCase.addScreenShotCurrentStep("DecimoCuartaCaptura");

		// Paso 14. Dar clic en "Execute Query"
		addStep("Dar clic en \"Execute Query\"");
		u.hardWait(5);
		p.clickImagen("execute");
		testCase.addScreenShotCurrentStep("DecimoCuartaCaptura");

		// Paso 15. Dar doble clic sobre un registro de horario disponible
		addStep("Dar doble clic sobre un registro de horario disponible");
		u.hardWait(5);
		p.doubleClickAndCopyTextBoxRight("disponible", 0);
		u.hardWait(15);
		testCase.addScreenShotCurrentStep("DecimoQuintaCaptura");

		// Paso 16. Validar que los campos HORA INICIO y HORA FIN muestren los valores
		// de acuerdo al horario disponible y al artículo seleccionado con entarimado
		addStep("Validar que los campos HORA INICIO y HORA FIN muestren los valores de acuerdo al horario disponible y al artículo seleccionado con entarimado");
		try {
		    u.hardWait(5);
		    p.clickImagen("hora_inicio_fin");
		    u.hardWait(3);
		} catch (Exception e) {
		    System.out.println("Se produjo una excepción al intentar esperar o hacer clic en la imagen.");
		    throw new AssertionError("Se produjo una excepción al intentar esperar o hacer clic en la imagen. La hora inicio y fin");
		}
		testCase.addScreenShotCurrentStep("DecimoSextaCaptura");

		// Paso 17. Validar que el campo OC LIGADAS muestre el número de las ordenes de
		// compra que fueron seleccionadas
		addStep("Validar que el campo OC LIGADAS muestre el número de las ordenes de compra que fueron seleccionadas");
		try {
		    u.hardWait(3);
		    p.clickImagen("oc_ligadas");
		    u.hardWait(3);
		} catch (Exception e) {
		    System.out.println("Se produjo una excepción al intentar esperar o hacer clic en la imagen.");
		    throw new AssertionError("Se produjo una excepción al intentar esperar o hacer clic en la imagen. oc ligadas");
		}
		testCase.addScreenShotCurrentStep("DecimoSeptimaCaptura");

		// Paso 18. Validar que el campo DESCARGA TOTAL muestre el total de tiempo
		// (minutos) para la descarga de acuerdo al artículo seleccionado y tomando en
		// cuenta el entarimado
		addStep("Validar que el campo DESCARGA TOTAL muestre el total de tiempo (minutos) para la descarga de acuerdo al artículo seleccionado y tomando en cuenta el entarimado");
		try {
		    u.hardWait(3);
		    p.clickImagen("descarga_total");
		    u.hardWait(3);
		} catch (Exception e) {
		    System.out.println("Se produjo una excepción al intentar esperar o hacer clic en la imagen.");
		    throw new AssertionError("Se produjo una excepción al intentar esperar o hacer clic en la imagen. descarga total");
		}
		testCase.addScreenShotCurrentStep("DecimoSeptimaCaptura");

		// Paso 19. Dar clic en "Save"
		addStep("Dar clic en \"Save\"");
		u.hardWait(5);
		p.clickImagen("save_cita");
		u.hardWait(15);
		testCase.addScreenShotCurrentStep("DecimoOctavaCaptura");

		// Paso 20. Confirmar el mensaje seleccionando "Yes"
		addStep("Confirmar el mensaje seleccionando \"Yes\"");
		u.hardWait(5);
		p.clickImagen("yes_cita");
		u.hardWait(15);
		testCase.addScreenShotCurrentStep("Vigésima Captura");

		// copiar cita
		u.hardWait(5);
		p.doubleClickAndCopyTextBoxRight("cita_textbox", 0);
		cita = p.doubleClickAndCopyTextBoxRight("cita_textbox", 0);
		u.hardWait(5);

		// salir
		p.clickImagen("exit_cancel");
		u.hardWait(5);

		// aceptar mensaje de error
		u.hardWait(5);
		p.clickImagen("yes_error");
		u.hardWait(15);

		// Paso 21. Confirmar el mensaje seleccionando "Yes"
		addStep("Validar en la tabla XXFC_APPOINTMENT que se haya creado el registro de la cita y muestre en el campo APPT_DURATION en número total de tiempo de descarga (minutos) calculado en la pantalla");

		String querty1 = "select appt_nbr, appt_duration, entarimado, last_update_date from xxfc_appointment\r\n"
				+ "where appt_nbr='" + cita + "'";

		utils.sql.SQLUtil dbBARAqa = new utils.sql.SQLUtil(GlobalVariables.DB_HOST_RDM_BARA,
				GlobalVariables.DB_USER_RDM_BARA, GlobalVariables.DB_PASSWORD_RDM_BARA);
		utils.sql.SQLResult resultadoContenedor1 = dbBARAqa.executeQuery(querty1);
		testCase.addQueryEvidenceCurrentStep(resultadoContenedor1);

		boolean flag = !resultadoContenedor1.isEmpty();

		assertTrue(flag, "El registro existe en la BD");

		u.close();
	}

	@Override
	public void beforeTest() {
		// TODO Auto-generated method stub

	}

	@AfterMethod
	public void afterTest() {
		if(u!=null)
			u.close();
	}

	@Override
	public String setPrerequisites() {
		return "Orden de Compra para recibir con más de un artículo con más de un artículo  con más de una tarima";
	}

	@Override
	public String setTestDescription() {
		return "Validar que se considere el tiempo de descarga recalculado al momento de editar el detalle del artículo en la Orden de Compra de acuerdo a los artículos seleccionados a incluir en  la cita a generar seleccionando la opción de Entarimado";
	}

	@Override
	public String setTestDesigner() {
		return "JesusM3@hexaware.com";
	}

	@Override
	public String setTestFullName() {
		// TODO Auto-generated method stub
		return "ATC_FT_003_Agendar_cita_para_Orden_de_Compra_seleccionando_mas_de_un_articulo_con_entarimado_test";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}
}
