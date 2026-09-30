package WorkFlows.PromoBara;

import java.util.HashMap;

import org.testng.annotations.Test;

import WorkFlows.util.PromoBara.crearPromocion;
import WorkFlows.util.PromoBara.loginUltimus;
import modelo.BaseExecution;
import utils.selenium.FirefoxTest;
import utils.selenium.SeleniumUtil;

public class ATC_FT_141_CrearPromoPackPerConfig_PromoBara extends BaseExecution{
	
	SeleniumUtil u;
	@Test(dataProvider = "data-provider")
	
	 public void ATC_FT_141_CrearPromoPackPerConfig_PromoBara_test(HashMap<String, String> data) throws Exception {
		
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
		addStep("Inicia Sesion en Ultimus con usuario Categoria");
		logUltimus.login_Ultimus(userUltimus);
		
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
		
		addStep("Hacer clic en el botón 'Activar'");
		creaPromo.btnActivaMec();
		creaPromo.btnAceptar();

		addStep("Hacer clic en el botón 'Guardar'.");
		creaPromo.btnGuardar();
		creaPromo.btnAceptar();

		addStep("Hacer clic en el botón 'Enviar'.");
		creaPromo.btnEnviar();
		creaPromo.btnAceptar();


		addStep("Seleccionar el valor 'Comunicación de imagen'  en el campo 'Tipo de Mecanica'");
		creaPromo.configMecanica(data.get("numComponentes"), data.get("catComp1"), data.get("catCom2"),
				data.get("userCat1"), data.get("userCat2"), data.get("mecanica1"), data.get("mecanica2"));
		
		
		addStep("Hacer clic en el botón 'Buscar UDA'."
				+ "\nAgregar 1 o más artículos en seleccionados");
		creaPromo.btnBuscaUda();
		creaPromo.buscaUda(data.get("buscaUDA_campo"), data.get("buscaUDA_busqueda"), data.get("buscaUDA_valor"));
		creaPromo.btnAgregaUDA();
		

		String infoArti_DatosGenerales[] = data.get("infoArti_DatosGenerales").split(",");
		String infoArti_DatosSinOferta[] = data.get("infoArti_DatosSinOferta").split(",");
		String infoArti_AportaProveedor[] = data.get("infoArti_AportaProveedor").split(",");
		
		String infoArti_CadenaSuministro[] = data.get("infoArti_CadenaSuministro").split(",");
		
		addStep("Llenar información de los artículos");
		creaPromo.datosArticulo("1", "", infoArti_DatosGenerales, infoArti_DatosSinOferta, infoArti_AportaProveedor, data.get("infoArti_DatosConOferta"), infoArti_CadenaSuministro, data.get("infoArti_General"));
	
		creaPromo.btnSegundaCat();
		addStep("Hacer clic en el botón 'Buscar artículo'."
				+ "\nAgregar 1 o más artículos en seleccionados");
		
		creaPromo.btnBuscaArticulo();
		creaPromo.buscaArtiNuevo(data.get("buscaArti_superGrupo"), data.get("buscaArti_categoria"),data.get("buscaArti_subCategoria") , data.get("buscaArti_segmento"), data.get("buscaArti_Subsegmento"));
		creaPromo.btnAgregaArti();
		
		creaPromo.datosArticulo("2", "", infoArti_DatosGenerales, infoArti_DatosSinOferta, infoArti_AportaProveedor, data.get("infoArti_DatosConOferta"), infoArti_CadenaSuministro, data.get("infoArti_General"));

		addStep("Hacer clic en el botón 'Guardar'.");
		creaPromo.btnGuardar();
		creaPromo.btnAceptar();

		addStep("Ingresar a la opción de menú 'Promociones/Buscar',\r\n"
				+ "Captura el folio de la promoción recién creada y seleccionar los campos Año y Periodo "
				+ "a los que corresponde la promoción; y hacer clic en el botón 'Buscar");
		
		
		creaPromo.buscaPromoPack();
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
		return "Terminado. CP141_Pantalla Crear- Validar la creación de una promoción Promopack dentro de un periodo configurado (con usuario Categoría Marca propia o similar)";
	}

	@Override
	public String setTestDesigner() {
		// TODO Auto-generated method stub
		return "Alicia Michelle Ceja Gomez";
	}

	@Override
	public String setTestFullName() {
		// TODO Auto-generated method stub
		return "ATC_FT_141_CrearPromoPackPerConfig_PromoBara_test";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}

}
