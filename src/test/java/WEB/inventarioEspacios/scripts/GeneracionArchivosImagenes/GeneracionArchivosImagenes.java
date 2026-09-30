package WEB.inventarioEspacios.scripts.GeneracionArchivosImagenes;

import java.util.HashMap;

import org.testng.annotations.Test;

import WEB.inventarioEspacios.om.DetallePops;
import WEB.inventarioEspacios.om.MenuPrincipalIEP;
import WEB.om.PortalApps;
import modelo.BaseExecution;
import utils.password.PasswordUtil;
import utils.selenium.MEdgeTest;
import utils.selenium.SeleniumUtil;

public class GeneracionArchivosImagenes extends BaseExecution {
	SeleniumUtil u;
	@Test(dataProvider = "data-provider")
	
	public void ATC_FT_002_GeneracionArchivosImagenes_Test(HashMap<String, String> data) throws Exception {
		/*
		 * Variables
		 ******************************************************************************************/
		String user = data.get("User");
		String password = PasswordUtil.decryptPassword(data.get("Password"));
//		String mngAlerta = "Usuario y/o Contraseña Invalidos";
		String nombrePop = data.get("NombrePop");
		String IdPops = data.get("Id_Pops");
		
		
		
//		//									PASO 1
		
		addStep("Ingresar al aplicativo inventario de espacios publicitarios v2."); //viene en excel
//		u = new SeleniumUtil(new ChromeTest(), true); //IETest para usar internet explorer
		SeleniumUtil u = new SeleniumUtil(new MEdgeTest(), true);
		PortalApps pa = new PortalApps(u, testCase);  // crea objeto de locator
		u.get(data.get("Url"));    //toma el valor Url del data provider
		u.hardWait(3);           // tiempo en segundos
		
		pa.proceedToPortal();    // es para lo de la pagina no segura
		//pa.logginInventarioEspacios(user, password);
		pa.cicloLogin(user, password);
		pa.clickPortalInventarioEspacios();
		
		
		//		                            PASO 2
		
		addStep("Seleccionar Menu -->POP-->POPS");
		MenuPrincipalIEP mp = new MenuPrincipalIEP(u, testCase);
		mp.clickMenus();
        mp.clickPops();
        mp.clickPopsOp();
		
        //                                   PASO 3
	addStep("Se visualiza la pantalla de consulta de POPs con el campo de busqueda:\r\n"
			+ "\r\n"
			+ "* Nombre de POP\r\n"
			+ "* Periodo\r\n"
			+ "* Proveedor\r\n"
			+ "* Tipo de POP\r\n"
			+ "");
	DetallePops dp = new DetallePops(u, testCase);
	dp.clickFiltroNombrePops();
       //                                    PASO 4
	addStep("Seleccionar filtros y dar click en boton buscar:");
	dp.clickNombrePop(nombrePop);
	dp.clickBtnBuscarPop();
	
		// 									 PASO 5
	addStep("Seleccionar boton de \"Imagen");
//    dp.clickImagenPop();
	dp.clickBuscaIdPops(IdPops);
	// 									 PASO 6
	addStep("Se visualiza una ventana modal con los botones:\r\n"
			+ "Elegir Archivo\r\n"
			+ "Cancelar\r\n"
			+ "Aceptar\r\n"
			+ "\r\n"
			+ "");
	dp.clickVentanaImagen();

	// 									 PASO 7 y 8
	addStep("Seleccionar Examinar y se abre el explorador de archivos de windows: "
			+ "Seleccionar una imagen y subirla.\r\n"
			+ "");
	dp.clickSelArchivo();

	
	// 									 PASO 9
	addStep("\"Se visualiza una ventana modal con la imagen nueva y los datos:\r\n"
			+ "Nombre:\r\n"
			+ "Fecha de carga:\r\n"
			+ "Tamaño:\r\n"
			+ "Y los botones:\r\n"
			+ "\r\n"
			+ "Cancelar\r\n"
			+ "Aceptar\"\r\n"
			+ "\r\n"
			+ "");
	dp.clickSeVisualiza();

	// 									 PASO 10,11 y 12
	// Nota: no podemos validar con automatización, ya que habla sobre ftp, se revisara si es posible la automatizacion de este
	//addStep("\"Dar click en el boton \"Aceptar\".");
	//addStep("\"Ingresar al <<Repositorio de Archivos>>  para validar que se guarde la nueva imagen y no genere duplicados.");
	//addStep("\"Volver a consultar el pop con la imagen guardada y que se muestre en el app.");
	
	}
	
	
	@Override
	public void beforeTest() {
	}

	@Override
	public String setTestDescription() {
		return "ATC_FT_002_GeneracionArchivosImagenes_Test";
	}

	@Override
	public String setTestDesigner() {
		return "AutomationQA";
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_002_GeneracionArchivosImagenes_Test";
	}

	@Override
	public String setTestInstanceID() {
		return null;
	}

	@Override
	public String setPrerequisites() {
		return null;
	}
	
//	@AfterMethod
//	public void afterMethod() {
//		u.getDriver().quit();
//	}
}


