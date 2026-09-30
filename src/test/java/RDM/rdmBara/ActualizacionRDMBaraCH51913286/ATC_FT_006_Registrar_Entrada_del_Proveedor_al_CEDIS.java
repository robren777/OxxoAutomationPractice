package RDM.rdmBara.ActualizacionRDMBaraCH51913286;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import org.sikuli.script.Pattern;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import RDM.util.metodosAdicionales.MetodosSikulli;
import RDM.util.metodosExtra.LoginRDM;
import modelo.BaseExecution;
import utils.SikuliUtil;
import utils.password.PasswordUtil;
import utils.selenium.IETest;
import utils.selenium.SeleniumUtil;

public class ATC_FT_006_Registrar_Entrada_del_Proveedor_al_CEDIS extends BaseExecution{
	SeleniumUtil u = new SeleniumUtil(new IETest(), true);
	/**
	 * REREQUISITOS: 
	 *		1.Haber ingresado a la aplicación,
			que el proveedor cuente con citas agendadas vigentes, haber ejecutado CP005
			cita = La cita que se va a consultar y a utilizar para registrar (dp)
	 * 
	 * DESCRIPCION: Usuario registra la entrada del Proveedor a CEDIS
	 * 
	 * @author JesusM3@hexaware.com
	 */
	@Test(dataProvider = "data-provider")
	public void ATC_FT_006_Registrar_Entrada_del_Proveedor_al_CEDIS_test(HashMap<String, String> data)
			throws Exception {
		/*
		 * VARIABLES
		 * 
		 * user = usuario de acceso a rdm bara password = contraseña encriptada para
		 * cita = La cita que se va a consultar y a utilizar para registrar
		 */

		String url = data.get("url");
		String userName = data.get("user");
		String password = PasswordUtil.decryptPassword(data.get("password"));
		String cita;

		SikuliUtil sk = new SikuliUtil();
		

		String pathImg = sk.PatternPath("RDM\\"+"CH51913286");
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

		// Obtener una cita
		p.clickImagen("appointments");
		u.hardWait(10);
		p.clickImagen("appointment_schedule");
		u.hardWait(15);
		p.clickImagen("citas");
		u.hardWait(10);
		p.clickImagen("lista_citas");
		u.hardWait(10);
		p.nDown(50);
		u.hardWait(3);
		p.nUp(1);
		u.hardWait(3);
		p.clickImagen("ok");
		u.hardWait(10);
		p.doubleClickAndCopyTextBoxRight("cita_combobox", 100);
		cita = p.doubleClickAndCopyTextBoxRight("cita_combobox", 100);
		u.hardWait(10);
		p.clickImagen("exit");
		u.hardWait(10);
		p.clickImagen("minimizar");

		// Paso 2. Ingresar a la ruta: Receiving Allocation/XXFC-Registro de Proveedores
		addStep("Ingresar a la ruta: Receiving Allocation/XXFC-Registro de Proveedores");
		p.clickImagen("receiving_allocation");
		u.hardWait(10);
		p.clickImagen("xxfc_registro_de_proveedores");
		u.hardWait(10);
		testCase.addScreenShotCurrentStep("TerceraCaptura");
		u.hardWait(20);

		// Paso 3. Se captura Folio de la Cita o se captura código de barras
		addStep("Se captura Folio de la Cita o se captura código de barras");
		p.enviarTexto(cita);
		u.hardWait(5);
		testCase.addScreenShotCurrentStep("CuartaCaptura");
		u.hardWait(5);

		// Paso 4. Presionar la tecla TAB
		addStep("Presionar la tecla TAB");
		p.nTAB(1);
		u.hardWait(10);
		testCase.addScreenShotCurrentStep("QuintaCaptura");
		u.hardWait(5);

		// Paso 5. Hacer clic en el Botón "Registrar"
		addStep("Hacer clic en el Botón Registrar");
		try {
		    u.hardWait(5);
		    p.clickImagen("registrar");
		    u.hardWait(10);
		} catch (Exception e) {
		    System.out.println("Se produjo una excepción al intentar esperar o hacer clic en la imagen.");
		    throw new AssertionError("Se produjo una excepción al intentar esperar o hacer clic en la imagen. boton registrar");
		}

		testCase.addScreenShotCurrentStep("SeptimaCaptura");

		// Paso 6. Dar click en "Yes"
		addStep("Dar click en Yes");
		try {
		    u.hardWait(5);
		    p.clickImagen("yes");
		    u.hardWait(10);
		} catch (Exception e) {
		    System.out.println("Se produjo una excepción al intentar esperar o hacer clic en la imagen.");
		    throw new AssertionError("Se produjo una excepción al intentar esperar o hacer clic en la imagen. boton yes al registrar");
		}

		testCase.addScreenShotCurrentStep("OctavaCaptura");

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
		return "\"Haber ingresado a la aplicación,\r\n"
				+ "que el proveedor cuente con citas agendadas vigentes, haber ejecutado CP001\"";
	}

	@Override
	public String setTestDescription() {
		return "Usuario registra la llegada del Proveedor ingresando el Folio y/o capturando código de barras";
	}

	@Override
	public String setTestDesigner() {
		return "JesusM3@hexaware.com";
	}

	@Override
	public String setTestFullName() {
		// TODO Auto-generated method stub
		return "ATC_FT_006_Registrar_Entrada_del_Proveedor_al_CEDIS_test";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}
}
