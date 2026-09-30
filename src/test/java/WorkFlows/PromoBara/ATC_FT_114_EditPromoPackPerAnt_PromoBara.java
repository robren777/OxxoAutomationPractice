package WorkFlows.PromoBara;

import java.util.HashMap;

import org.testng.annotations.Test;

import WorkFlows.util.PromoBara.crearPromocion;
import WorkFlows.util.PromoBara.editPromocion;
import WorkFlows.util.PromoBara.loginUltimus;
import modelo.BaseExecution;
import utils.selenium.FirefoxTest;
import utils.selenium.SeleniumUtil;

public class ATC_FT_114_EditPromoPackPerAnt_PromoBara extends BaseExecution{
	
	SeleniumUtil u;
	@Test(dataProvider = "data-provider")
	
	 public void ATC_FT_114_EditPromoPackPerAnt_PromoBara_test(HashMap<String, String> data) throws Exception {
		
	testCase.setProject_Name("Framework WorkFlows --> PromoBara");
	

	/**
	 * Variables
	 * 
	 */
	String userUltimus= data.get("userUltimus");
	
	/**
	 * 
	 * Utilerias
	 */
	u = new SeleniumUtil(new FirefoxTest(), true);
	u.get(data.get("url_ultimus"));
	


	loginUltimus logUltimus = new loginUltimus(u, testCase, userUltimus);
	crearPromocion creaPromo = new crearPromocion(u, testCase, userUltimus);

editPromocion editPromo = new editPromocion(u, testCase);

	
	/*-----------------------------------------------------------------------------------------*/
		addStep("Inicia Sesion en Ultimus con usuario Categoria");
		logUltimus.login_Ultimus(userUltimus);
		
		u.hardWait(2);
		
		editPromo.selectPromo(data.get("folio"));
		
		addStep("Capturar informacion en la pantalla");
		creaPromo.periodoPromocional(data.get("PruebaAutomatizacion01"));
		creaPromo.coberturas();
		creaPromo.medioPago(data.get("medioPago"));
		System.out.println("\"----->\"+"+data.get("selecNecesidad"));

		creaPromo.selectNecesidad(data.get("selecNecesidad"));

		creaPromo.rentaEspacios(data.get("rentaEspacios"));
		creaPromo.periodoReferencia(data.get("periodoReferencia"));
		creaPromo.paquetePromocional(data.get("paquetePromo"));
		
		addStep("Hacer clic en el boton 'Buscar articulo'."
				+ "\nAgregar 1 o mas articulos en seleccionados");
		
		creaPromo.btnBuscaArticulo();
		creaPromo.buscaArtiNuevo(data.get("buscaArti_superGrupo"), data.get("buscaArti_categoria"),data.get("buscaArti_subCategoria") , data.get("buscaArti_segmento"), data.get("buscaArti_Subsegmento"));
		creaPromo.btnAgregaArti();

		
		addStep("Hacer clic en el boton 'Buscar UDA'."
				+ "\nAgregar 1 o mas articulos en seleccionados");
		creaPromo.btnBuscaUda();
		creaPromo.buscaUda(data.get("buscaUDA_campo"), data.get("buscaUDA_busqueda"), data.get("buscaUDA_valor"));
		
		creaPromo.btnAgregaUDA();
		
		addStep("Hacer clic en el boton 'Guardar'.");
		creaPromo.btnGuardar();
		addStep("Hacer clic en el boton 'Aceptar' del mensaje.");
		creaPromo.btnAceptar();
		
		addStep("Hacer clic en el boton 'Enviar'.");
		creaPromo.btnEnviar();
		
		addStep("Hacer clic en el boton 'Aceptar' del mensaje.");
		creaPromo.btnAceptar();
		
		String infoArti_DatosGenerales[] = data.get("infoArti_DatosGenerales").split(",");
		String infoArti_DatosSinOferta[] = data.get("infoArti_DatosSinOferta").split(",");
		String infoArti_AportaProveedor[] = data.get("infoArti_AportaProveedor").split(",");
		
		String infoArti_CadenaSuministro[] = data.get("infoArti_CadenaSuministro").split(",");


		
		addStep("Llenar informacion de los articulos");
		creaPromo.datosArticulo("3", "", infoArti_DatosGenerales, infoArti_DatosSinOferta, infoArti_AportaProveedor, data.get("infoArti_DatosConOferta"), infoArti_CadenaSuministro, data.get("infoArti_General"));
		
		addStep("Hacer clic en el boton 'Guardar'.");
		creaPromo.btnGuardar();
		
		addStep("Hacer clic en el boton 'Aceptar' del mensaje.");
		creaPromo.btnAceptar();
		
		addStep("Hacer clic en el boton 'Enviar'.");
		creaPromo.btnEnviar();
		
		addStep("Hacer clic en el boton 'Aceptar' del mensaje.");
		creaPromo.btnAceptar();
		
		
		addStep("Seleccionar el CheckBox de algun articulo que ya cuenten con informacion "
				+ "capturada y hacer clic en el boton 'Copiar renglon'\"");
		
		creaPromo.coppyArticulo("1", "4");
		
		creaPromo.ligaConvenio();
		
		addStep("Hacer clic en el boton 'Guardar'.");
		creaPromo.btnEnviar();
		creaPromo.btnAceptar();

		addStep("Ingresar a la opcion de menu 'Promociones/Buscar',\r\n"
				+ "Captura el folio de la promocion recien creada y seleccionar los campos Año y Periodo "
				+ "a los que corresponde la promocion; y hacer clic en el boton 'Buscar");
		
		
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
		return "Agregar folio en el dataprovider";
	}

	@Override
	public String setTestDescription() {
		// TODO Auto-generated method stub
		return "Terminado. CP114_Pantalla Buscar- Validar la clonacion de una promocion Promopack de periodo anterior (con usuario Categoria Marca propia o similar)";
	}

	@Override
	public String setTestDesigner() {
		// TODO Auto-generated method stub
		return "Alicia Michelle Ceja Gomez";
	}

	@Override
	public String setTestFullName() {
		// TODO Auto-generated method stub
		return "ATC_FT_114_EditPromoPackPerAnt_PromoBara_test";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}

}
