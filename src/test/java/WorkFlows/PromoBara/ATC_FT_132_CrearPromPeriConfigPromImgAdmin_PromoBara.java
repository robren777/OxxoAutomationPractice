package WorkFlows.PromoBara;

import java.util.HashMap;

import org.testng.annotations.Test;

import WorkFlows.util.PromoBara.crearPromocion;
import WorkFlows.util.PromoBara.loginUltimus;
import modelo.BaseExecution;
import utils.selenium.FirefoxTest;
import utils.selenium.SeleniumUtil;

public class ATC_FT_132_CrearPromPeriConfigPromImgAdmin_PromoBara extends BaseExecution{
	
	SeleniumUtil u;
	@Test(dataProvider = "data-provider")
	
	 public void ATC_FT_132_CrearPromPeriConfigPromImgAdmin_PromoBara_test(HashMap<String, String> data) throws Exception {
		
	testCase.setProject_Name("Framework WorkFlows --> PromoBara");
	

	/**
	 * Variables
	 * 
	 */
	String userUltimus= data.get("userUltimus");
	
	/**
	 * 
	 * Utiler�as
	 */
	u = new SeleniumUtil(new FirefoxTest(), true);
	u.get(data.get("url_ultimus"));
	


	loginUltimus logUltimus = new loginUltimus(u, testCase, userUltimus);
	crearPromocion creaPromo = new crearPromocion(u, testCase, userUltimus);

	
	/*-----------------------------------------------------------------------------------------*/
		addStep("Inicia Sesion en Ultimus con usuario Administrador");
		logUltimus.loginAdmin(userUltimus);
		
		u.hardWait(2);
		
		addStep("Ingresar a la pantalla Buscar para crear una nueva Promoción");
		creaPromo.pantallaBuscar_CrearPromocion();
		
		addStep("Seleccionar el valor 'Comunicación de imagen'  en el campo 'Tipo de Mecanica'");
		creaPromo.selectTipoMecanica(data.get("tipoMecanica"));
		
		
		addStep("Capturar información en la pantalla");
		creaPromo.periodoPromocional(data.get("PruebaAutomatizacion01"));
		creaPromo.descripcionMecanica(data.get("descripcionMecanica"));
		creaPromo.coberturas();
		creaPromo.medioPago(data.get("medioPago"));
		System.out.println("\"----->\"+"+data.get("selecNecesidad"));

		creaPromo.selectNecesidad(data.get("selecNecesidad"));

		creaPromo.rentaEspacios(data.get("rentaEspacios"));
		creaPromo.periodoReferencia(data.get("periodoReferencia"));
		creaPromo.paquetePromocional(data.get("paquetePromo"));
		
		addStep("Hacer clic en el botón 'Buscar artículo'."
				+ "\nAgregar 1 o más artículos en seleccionados");
		
		creaPromo.btnBuscaArticulo();
		creaPromo.buscaArtiNuevo(data.get("buscaArti_superGrupo"), data.get("buscaArti_categoria"),data.get("buscaArti_subCategoria") , data.get("buscaArti_segmento"), data.get("buscaArti_Subsegmento"));
		creaPromo.btnAgregaArti();
		
		addStep("Hacer clic en el botón 'Buscar UDA'."
				+ "\nAgregar 1 o más artículos en seleccionados");
		creaPromo.btnBuscaUda();
		creaPromo.buscaUda(data.get("buscaUDA_campo"), data.get("buscaUDA_busqueda"), data.get("buscaUDA_valor"));
		
		creaPromo.btnAgregaUDA();
		addStep("Hacer clic en el botón 'Artículo nuevo'.");
		creaPromo.btnArtiNuevo();
		
		addStep("Hacer clic en el botón 'Guardar'.");
		creaPromo.btnGuardar();
		
		addStep("Hacer clic en el botón 'Aceptar' del mensaje.");
		creaPromo.btnAceptar();
		
		addStep("Hacer clic en el botón 'Enviar'.");
		creaPromo.btnEnviar();
		
		addStep("Hacer clic en el botón 'Aceptar' del mensaje.");
		creaPromo.btnAceptar();
		
		String infoArti_DatosGenerales[] = data.get("infoArti_DatosGenerales").split(",");
		String infoArti_DatosSinOferta[] = data.get("infoArti_DatosSinOferta").split(",");
		String infoArti_AportaProveedor[] = data.get("infoArti_AportaProveedor").split(",");
		
		String infoArti_CadenaSuministro[] = data.get("infoArti_CadenaSuministro").split(",");

	

		
		addStep("Llenar información de los artículos");
		creaPromo.datosArticulo("1", "", infoArti_DatosGenerales, infoArti_DatosSinOferta, infoArti_AportaProveedor, data.get("infoArti_DatosConOferta"), infoArti_CadenaSuministro, data.get("infoArti_General"));
		
		addStep("Hacer clic en el botón 'Guardar'.");
		creaPromo.btnGuardar();
		
		addStep("Hacer clic en el botón 'Aceptar' del mensaje.");
		creaPromo.btnAceptar();
		
		addStep("Hacer clic en el botón 'Enviar'.");
		creaPromo.btnEnviar();
		
		addStep("Hacer clic en el botón 'Aceptar' del mensaje.");
		creaPromo.btnAceptar();
		
		addStep("Seleccionar el CheckBox de algún artículo que ya cuenten con información "
				+ "capturada y hacer clic en el botón 'Copiar renglón'\"");
		
		creaPromo.coppyArticulo("1", "2");
		
		addStep("Seleccionar el CheckBox de un artículo y hacer clic en el botón 'Eliminar'");
		creaPromo.elimiaArti("3");
		
		addStep("Hacer clic en el botón 'Guardar'.");
		creaPromo.btnGuardar();
		addStep("Ingresar a la opción de menú 'Promociones/Buscar',\r\n"
				+ "Captura el folio de la promoción recién creada y seleccionar los campos Año y Periodo "
				+ "a los que corresponde la promoción; y hacer clic en el botón 'Buscar");
		
		String folio = creaPromo.getFolio();
		
		creaPromo.buscaPromo(folio);
		u.hardWait(2);
		u.getDriver().quit();
	}

	@Override
	public void beforeTest() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String setPrerequisites() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String setTestDescription() {
		// TODO Auto-generated method stub
		return "Terminado. CP132 - Pantalla Buscar - Validar link -Crear nueva promoción- dentro de un periodo configurado y crear una promoción Comunicación de imagen (con usuario Administrador)";
	}

	@Override
	public String setTestDesigner() {
		// TODO Auto-generated method stub
		return "Alicia Michelle Ceja Gomez";
	}

	@Override
	public String setTestFullName() {
		// TODO Auto-generated method stub
		return "ATC_FT_132_CrearPromPeriConfigPromImgAdmin_PromoBara";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}

}
