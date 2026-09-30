package RDM.rdmBara.ActualizacionRDMBaraCH51987957;
import static org.testng.Assert.assertTrue;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

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

public class ATC_FT_001_Cambio_de_CASEPACK_Ejecutandolo_desde_RDM_Boton_EJEC_DEMANDA extends BaseExecution {
	SeleniumUtil u = new SeleniumUtil(new IETest(), true);
	/**
	 * REREQUISITOS: 1. Tener acceso a la aplicación RDM 2. Mantener la resolución
	 * de pantalla en 100%
	 * 
	 * DESCRIPCION: Validar que se realice el cambio de CASEPACK en la tabla
	 * "XXFC_ITEMS_EXCEPTIONS", al realizarlo desde RDM usando el botón "EJEC
	 * DEMANDA"
	 * 
	 * @author JesusM3@hexaware.com
	 */

	@Test(dataProvider = "data-provider")
	public void ATC_FT_001_Cambio_de_CASEPACK_Ejecutandolo_desde_RDM_Boton_EJEC_DEMANDA_test(
			HashMap<String, String> data) throws Exception {

		/*
		 * VARIABLES url = Link al aplicativo RDM user = usuario de acceso a RDM
		 * password = contraseña encriptada para acceder a RDM
		 */

		String url = data.get("url");
		String userName = data.get("user");
		String password = PasswordUtil.decryptPassword(data.get("password"));

		SikuliUtil sk = new SikuliUtil();
		

		String pathImg = sk.PatternPath("RDM\\"+"CH51987957");
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
		try {
	    	p.clickImagen("ValidarLogin");
	    	u.hardWait(10);
	    }catch (Exception e) {
	    	throw new RuntimeException("No se puede validar la pantalla principal de RDM Bara.");
	    }

		// Paso 2. Ingresar a la ruta Support Functions / Item Setup / XXFC Item Master
		// Editor
		addStep("Ingresar a la ruta Support Functions / Item Setup / XXFC Item Master Editor");
		p.clickImagen("support_functions");
		u.hardWait(10);
		p.clickImagen("item_setup");
		u.hardWait(10);
		p.clickImagen("xxfc_item_master_editor");
		testCase.addScreenShotCurrentStep("TerceraCaptura");
		u.hardWait(20);
		try{
			p.clickImagen("ValidarPantallaItemMaster");
			u.hardWait(10);
		}catch(Exception e){
			throw new RuntimeException("No se puede validar la pantalla principal item master editor.");
		}

		// Paso 3. Dar clic en el botón 'Optimal PACK'
		addStep("Dar clic en el botón 'Optimal PACK'");
		p.clickImagen("optimal_pack");
		u.hardWait(10);
		testCase.addScreenShotCurrentStep("CuartaCaptura");
		u.hardWait(5);
		try{
			p.clickImagen("ValidarPantallaEmaqueOptimo");
			u.hardWait(10);
		}catch(Exception e){
			throw new RuntimeException("No se puede validar la pantalla principal item master editor.");
		}

		// Paso 4. Dar clic al botón "EJECUTAR"
		addStep("Dar clic en el botón 'Ejecutar'");
		p.clickImagen("execute");
		u.hardWait(10);
		testCase.addScreenShotCurrentStep("QuintaCaptura");
		u.hardWait(5);

		// Paso 5. Seleccionar cualquier articulo y darle doble clic
		addStep("Seleccionar cualquier articulo y darle doble clic");
		p.doubleClickAndCopyTextBoxRight("modificar_reference", 100);
		u.hardWait(5);
		testCase.addScreenShotCurrentStep("Sexta Captura");
		u.hardWait(5);

		/*
		 * Paso 6. "Modificar el valor del CASEPACK en el campo "CASEPACK EO""
		 */
		addStep("Capturar la nueva información para los campos: -Casepack EO\r\n"
				+ "		 * -Vigencia Ini -Vigencia Fin");
		int numeroAleatorio = new Random().nextInt(99) + 2;
		// Convertir el número aleatorio a String
		String numeroComoString = String.valueOf(numeroAleatorio);

		// Enviar el texto
		p.enviarTexto(numeroComoString);
		u.hardWait(5);
		testCase.addScreenShotCurrentStep("Captura paso seis");
		u.hardWait(5);

		// Paso 7. Dar clic al botón "SAVE"
		addStep("Dar clic al botón \"SAVE\"");
		p.clickImagen("save");
		u.hardWait(5);
		testCase.addScreenShotCurrentStep("Septima Captura");
		u.hardWait(5);

		// Paso 8. Dar clic al botón "YES" para confirmar el cambio de "CASEPACK EO"
		addStep("Dar clic al botón 'YES' para confirmar el cambio de 'CASEPACK EO'");
		p.clickImagen("save_yes");
		u.hardWait(5);
		testCase.addScreenShotCurrentStep("Octava Captura");
		u.hardWait(5);

		// Paso 9. Revisar la tabla "xxfc_eo_items_v" para validar que se realizara el
		// cambio de CASEPACK
		addStep("Revisar la tabla \"xxfc_eo_items_v\" para validar que se realizara el cambio de CASEPACK");
		String querty1 = "select STD_CASEPACK from xxfc_eo_items_v";
		utils.sql.SQLUtil dbBARAqa = new utils.sql.SQLUtil(GlobalVariables.DB_HOST_RDM_BARA,
				GlobalVariables.DB_USER_RDM_BARA, GlobalVariables.DB_PASSWORD_RDM_BARA);
		utils.sql.SQLResult resultadoContenedor1 = dbBARAqa.executeQuery(querty1);
		String CasepackAnterior = resultadoContenedor1.getFirstData();
		u.hardWait(5);
		testCase.addQueryEvidenceCurrentStep(resultadoContenedor1);
		u.hardWait(5);

		// Paso 10. Seleccionar el articulo usando el checkbox
		addStep("Seleccionar el articulo usando el checkbox");
		p.clickImagen("down_arrow");
		u.hardWait(5);
		p.clickImagen("blue_checkbox");
		testCase.addScreenShotCurrentStep("Novena Captura");
		u.hardWait(5);

		// Paso 11. Dar clic al botón "EJEC. DEMANDA"
		addStep("Dar clic al botón \"EJEC. DEMANDA\"");
		p.clickImagen("ejecutar_demanda");
		testCase.addScreenShotCurrentStep("Decimo primera Captura");
		u.hardWait(5);

		// Paso 12. Volver a ejecutar la consulta en la tabla "xxfc_eo_items_v"
		// para validar que no muestre información del Item que se le modificó el
		// CASEPACK
		addStep("Revisar la tabla \"xxfc_eo_items_v\" para validar que se realizara el cambio de CASEPACK");
		querty1 = "select STD_CASEPACK from xxfc_eo_items_v";
		dbBARAqa = new utils.sql.SQLUtil(GlobalVariables.DB_HOST_RDM_BARA,
				GlobalVariables.DB_USER_RDM_BARA, GlobalVariables.DB_PASSWORD_RDM_BARA);
		resultadoContenedor1 = dbBARAqa.executeQuery(querty1);
		String CasepackNuevo = resultadoContenedor1.getFirstData();
		testCase.addQueryEvidenceCurrentStep(resultadoContenedor1);
		boolean flag = false;
		if(CasepackAnterior.equalsIgnoreCase(CasepackNuevo))
	    	flag=true;
	    assertTrue(flag, "El registro existe en la BD");
		u.hardWait(5);
		System.out.println("Paso 12 terminado");

		// Paso 13. Validar que se visualice el cambio de CASEPACK en la tabla
		// "ITEM_MASTER"
		addStep("Validar que se visualice el cambio de CASEPACK en la tabla \"ITEM_MASTER\"");
		querty1 = "select COUNT(*) from ITEM_MASTER where STD_CASEPACK = '" + CasepackNuevo +"'";
		dbBARAqa = new utils.sql.SQLUtil(GlobalVariables.DB_HOST_RDM_BARA, GlobalVariables.DB_USER_RDM_BARA,
				GlobalVariables.DB_PASSWORD_RDM_BARA);
		resultadoContenedor1 = dbBARAqa.executeQuery(querty1);
		testCase.addQueryEvidenceCurrentStep(resultadoContenedor1);
		flag = false;
		if(!resultadoContenedor1.isEmpty())
	    	flag=true;
	    assertTrue(flag, "El registro existe en la BD");
		u.hardWait(5);
		u.hardWait(5);
		System.out.println("Paso 13 terminado");

		// Paso 14. Validar que se visualice el cambio de CASEPACK en la tabla
		// "ITEM_MASTER"
		addStep("Validar que se visualice el cambio de CASEPACK en la tabla \"ITEM_MASTER\"");
		querty1 = "select COUNT(*) from ITEM_MASTER where STD_CASEPACK = '" + CasepackNuevo +"'";
		dbBARAqa = new utils.sql.SQLUtil(GlobalVariables.DB_HOST_RDM_BARA, GlobalVariables.DB_USER_RDM_BARA,
				GlobalVariables.DB_PASSWORD_RDM_BARA);
		resultadoContenedor1 = dbBARAqa.executeQuery(querty1);
		testCase.addQueryEvidenceCurrentStep(resultadoContenedor1);
		u.hardWait(5);
		 flag = false;
		if(!resultadoContenedor1.isEmpty())
	    	flag=true;
	    assertTrue(flag, "El registro existe en la BD");
		u.hardWait(5);
		System.out.println("Paso 14 terminado");
		
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
		return "Que existan registros en la vista XXFC_EO_ITEMS_V";
	}

	@Override
	public String setTestDescription() {
		return "Validar que se realice el cambio de CASEPACK en la tabla \"XXFC_ITEMS_EXCEPTIONS\", al realizarlo desde RDM usando el botón \"EJEC DEMANDA\"";
	}

	@Override
	public String setTestDesigner() {
		return "JesusM3@hexaware.com";
	}

	@Override
	public String setTestFullName() {
		// TODO Auto-generated method stub
		return "ATC_FT_001_Cambio_de_CASEPACK_Ejecutandolo_desde_RDM_Boton_EJEC_DEMANDA_test";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}

}
