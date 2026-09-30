package WEB.inventarioEspacios.scripts.Pops;

import static org.testng.Assert.assertTrue;

import java.util.HashMap;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import WEB.inventarioEspacios.om.DetallePops;
import WEB.inventarioEspacios.om.MenuPrincipalIEP;
import WEB.inventarioEspacios.om.MenuPrincipalOpcionesIEP;
import WEB.om.PortalApps;
import modelo.BaseExecution;
import utils.password.PasswordUtil;
import utils.selenium.ChromeTest;
import utils.selenium.SeleniumUtil;

public class ConsultaPop_SubirImagen extends BaseExecution{
	SeleniumUtil u;
	@Test(dataProvider = "data-provider")
	public void ATC_FT_004_ConsultaPOPsSubirCambiarImagen_Test(HashMap<String, String> data) throws Exception {	
		/*
		 * Variables
		 ******************************************************************************************/		

		String user = data.get("User");
		String password = PasswordUtil.decryptPassword(data.get("Password"));
		String negocio = data.get("Negocio");
		String perfil = data.get("Perfil");
		String periodo = data.get("Periodo");
		String nombrePop = data.get("NombrePop");
		String periodoPop = data.get("PeriodoPop");
		String tipoPop = data.get("TipoPop");
		String proveedorPop = data.get("ProveedorPop");
		String path = data.get("Path");
		String caso = data.get("Caso");
		
		testCase.setTest_Description("Consulta de POPs - Subir/Cambiar Imagen - "+tipoPop+" - "+ perfil+" "+negocio);
		/**
		 *                                             PASO 1
		 ******************************************************************************************/
		
		addStep("Ingresar al portal");
		
		u = new SeleniumUtil(new ChromeTest(), true);
		PortalApps pa = new PortalApps(u, testCase);		
		u.get(pa.getUrl());
		
		pa.proceedToPortal();
		u.waitForLoadPage();
		pa.cicloLogin(user, password);
		
		/**
		 *                                             PASO 2
		 ******************************************************************************************/
		 
		addStep("Seleccionar Inventario de Espacios");
		
		pa.clickPortalInventarioEspacios();
		
		/**
		 *                                             PASO 3
		 ******************************************************************************************/
		
		addStep("Verificar la informaci�n del usuario y que se muestre el negocio y el perfil.");
		
		MenuPrincipalIEP mp = new MenuPrincipalIEP(u, testCase);
		MenuPrincipalOpcionesIEP mpo = new MenuPrincipalOpcionesIEP(u, testCase);
		
	    boolean verInfo = mp.verificarInformacion(user, negocio, perfil);
		if(verInfo)
			testCase.addTextEvidenceCurrentStep("La informaci�n del usuario, negocio y perfil es correcta");
		else
			testCase.addTextEvidenceCurrentStep("La informaci�n del usuario, negocio y/o perfil es incorrecta");
		
		//assertTrue(verInfo, "No se muestra la informaci�n esperada");
		
		/**
		 *                                             PASO 4
		 ******************************************************************************************/
		
		addStep("Validar que visualice los MENUS:");
		
		mp.clickMenus();
		switch(perfil.toUpperCase()) {
			case "ADMINISTRADOR": 
				if (negocio.toUpperCase().equals("OXXO") || negocio.toUpperCase().equals("BARA")){
					mpo.menuAdministrador();
				} else if (negocio.toUpperCase().equals("MARCA")) {
					mpo.menuAdministradorM();
				}
				
			break;
			
			case "PLAZA": mpo.menuPlaza();
			break;
			
			case "ESTAFETA": mpo.menuEstafeta();
			break;
		}
		
		/**
		 *                                             PASO 5
		 ******************************************************************************************/
		
		addStep("Validar que se muestre en el encabezado de la pantalla el Negocio, periodo y estatus del periodo.");
		
		boolean verEncabezado = mp.verificarEncabezado(negocio);
		
		if(verEncabezado)
			testCase.addTextEvidenceCurrentStep("La informaci�n del negocio, periodo y estatus del periodo es correcta");
		else
			testCase.addTextEvidenceCurrentStep("La informaci�n del negocio, periodo y estatus del periodo es incorrecta");
		
		//assertTrue(verEncabezado, "No se muestra la informaci�n esperada");
		
		
		/**
		 *                                             PASO 6
		 ******************************************************************************************/
		
		addStep("Seleccionar Menu -->POP-->POPS");
		
	    mp.clickMenus();
		mp.clickPops();
		mp.clickPopsOp();
		
		
		/**
		 *                                             PASO 7
		 ******************************************************************************************/
		addStep("Se visualiza la pantalla de consulta de POPs con el campo de busqueda: * Nombre de POP, * Periodo, * Proveedor, * Tipo de POP");
		
		DetallePops dp = new DetallePops(u, testCase);
		boolean validarCampos = dp.validarCampos();
		
		assertTrue(validarCampos, "No se muestra la informaci�n esperada");
		
		/**
		 *                                             PASO 8
		 ******************************************************************************************/
		addStep("Ingresar la siguiente informacion: * Nombre de POP, * Periodo, * Proveedor, * Tipo de POP y dar clic a boton 'Buscar'");
		
		if(!nombrePop.isEmpty())
		   dp.clickNombrePop(nombrePop);
		if(!periodoPop.isEmpty())
		   dp.selectPeriodoPop(periodoPop);
		if(!proveedorPop.isEmpty())
           dp.selectProveedorPop(proveedorPop);
		if(!tipoPop.isEmpty())
		   dp.selectTipoPop(tipoPop);
		
		dp.clickBtnBuscarPop();
		
		/**
		 *                                             PASO 9
		 ******************************************************************************************/
		addStep("Seleccionar boton de 'Imagen'");
		
		 dp.clickImagenIconPop(nombrePop);
		
		/**
		 *                                             PASO 10
		 ******************************************************************************************/
		addStep("Se visualiza una ventana modal con la imagen y los datos: Nombre, Fecha de carga, Tama�o. Y los botones: Cancelar, Aceptar");
		
		boolean valVentanaImg = false;
		
		switch(caso.toUpperCase()) {
		   case "SUBIR":
			   valVentanaImg = dp.validarVentanaSubirImagen();
			   System.out.print("subir");
			   break;
		   case "CAMBIAR":
			   valVentanaImg = dp.validarVentanaCambiarImagen();
			   System.out.print("cambiar");
			   break;
		}
		
		assertTrue(valVentanaImg, "No se muestra la ventana con la informaci�n esperada");
		 
		/**
		 *                                             PASO 11
		 ******************************************************************************************/
		addStep("Seleccionar 'Choose File' y cargar una imagen (.jpg, .png, .jpeg, .gif, .tiff) y subirla.");
		
		dp.uploadImg(path);		
		
		/**
		 *                                             PASO 12
		 ******************************************************************************************/
		addStep("Se visualiza una ventana modal con la nueva imagen y los datos: Nombre, Fecha de carga, Tama�o. Y los botones: Cancelar, Aceptar");
		
		String [] array = path.split("\\\\");
		
		boolean valCambioImg = dp.validarImagenCargada(array[array.length-1]);
			
		assertTrue(valCambioImg, "No se muestra la ventana con la nueva imagen e informaci�n esperada");
			
		
		/**
		 *                                             PASO 13
		 ******************************************************************************************/
		addStep("Dar click en el boton 'Aceptar'.");
		
		dp.clickBtnAceptarImagen();
		boolean validarMsg = dp.validarMsgImgCargada();
		
		assertTrue(validarMsg, "No se cambio la imagen");
		/**
		 *                                             PASO 14
		 ******************************************************************************************/
		//addStep("Ingresar al <<Repositorio de Archivos>>  para validar que se guarde la nueva imagen y no genere duplicados.");

		


	}

	@Override
	public void beforeTest() {
	}

	@Override
	public String setTestDescription() {
		return "";
	}

	@Override
	public String setTestDesigner() {
		return "AutomationQA";
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_004_ConsultaPOPsSubirCambiarImagen_Test";
	}

	@Override
	public String setTestInstanceID() {
		return null;
	}

	@Override
	public String setPrerequisites() {
		return null;
	}
	
	@AfterMethod
	public void afterMethod() {
		//u.getDriver().quit();
	}
}
