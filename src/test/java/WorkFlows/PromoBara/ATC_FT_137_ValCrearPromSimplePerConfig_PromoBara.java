package WorkFlows.PromoBara;

import java.util.HashMap;

import org.testng.annotations.Test;

import WorkFlows.util.PromoBara.crearPromocion;
import WorkFlows.util.PromoBara.loginUltimus;
import modelo.BaseExecution;
import utils.selenium.FirefoxTest;
import utils.selenium.SeleniumUtil;

public class ATC_FT_137_ValCrearPromSimplePerConfig_PromoBara extends BaseExecution{
	
	
	SeleniumUtil u;
	@Test(dataProvider = "data-provider")
	
	 public void ATC_FT_137_ValCrearPromSimplePerConfig_PromoBara_test(HashMap<String, String> data) throws Exception {
		
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

	
	/*-----------------------------------------------------------------------------------------*/
	
	/*-----------------------------------------------------------------------------------------*/
		addStep("Inicia Sesion en Ultimus con usuario Categoria");
		logUltimus.login_Ultimus(userUltimus);
		
		u.hardWait(2);
		
		addStep("Ingresar a la pantalla Buscar para crear una nueva Promoción");
		creaPromo.pantallaBuscar_CrearPromocion();
		u.getDriver().manage().window().maximize();
		
		addStep("Hacer clic en la opción 'Salir' (opción ubicada en la parte superior derecha de la pantalla).");
		creaPromo.btnSalir();
		
		addStep("Hacer clic en el botón Cancelar");
		creaPromo.btnCancelar();

		addStep("Hacer clic en el botón 'Salir' (botón ubicado en la parte inferior derecha de la pantalla)");
		creaPromo.btnSalirInferior();
		
		addStep("Hacer clic en el botón Cancelar");
		creaPromo.btnCancelar();
		
		addStep("Hacer clic en el botón 'Buscar artículo'");
		creaPromo.btnBuscaArti();
		
		addStep("Hacer clic en el botón 'Aceptar' del mensaje.");
		creaPromo.btnAceptar();
		
		addStep("Hacer clic en el botón 'Buscar UDA'.");
		creaPromo.btnBuscaUda();
		
		addStep("Hacer clic en el botón 'Aceptar' del mensaje.");
		creaPromo.btnAceptar();
		
		addStep("Hacer clic en el botón 'Aceptar' del mensaje.");
		creaPromo.btnArtiNuevo();
		
		addStep("Hacer clic en el botón 'Aceptar' del mensaje.");
		creaPromo.btnAceptar();
		
		addStep("Hacer clic en el botón 'Guardar'");
		creaPromo.btnGuardar();
		
		addStep("Hacer clic en el botón 'Aceptar' del mensaje.");
		creaPromo.btnAceptar();
		
		addStep("Hacer clic en el botón 'Enviar'.");
		creaPromo.btnEnviar();
		
		addStep("Hacer clic en el botón 'Aceptar' del mensaje.");
		creaPromo.btnAceptar();
		
		addStep("Capturar el objetivo de la promoción en el campo 'Descripción de mecánica'.");
		creaPromo.descripcionMecanica(data.get("descripcionMecanica"));
		
		addStep("Hacer clic en la pestaña 'Coberturas'");
		creaPromo.coberturas();
		
		addStep("Hacer clic en la pestaña 'Alta de promoción'");
		creaPromo.pantallaAltaPromo();
		
		addStep("Configurar los campos correspondientes a 'Medios de pago'.");
		creaPromo.medioPago(data.get("medioPago"));
		
		addStep("Hacer clic en el botón 'Guardar'");
		creaPromo.btnGuardar();
		
		addStep("Hacer clic en el botón 'Aceptar' del mensaje.");
		creaPromo.btnAceptar();
		addStep("Seleccionar un valor del campo 'Necesidad'");
		creaPromo.selectNecesidad(data.get("selecNecesidad"));
		
		addStep("Seleccionar la opción 'No' del campo 'Ingreso renta espacios'.");
		creaPromo.rentaEspacios(data.get("rentaEspacios"));
		
		addStep("Seleccionar un valor del campo 'Periodo de referencia'.");
		creaPromo.periodoReferencia(data.get("periodoReferencia"));
		
		addStep("Hacer clic en el botón 'Guardar'");
		creaPromo.btnGuardar();
		
		addStep("Hacer clic en el botón 'Aceptar' del mensaje.");
		creaPromo.btnAceptar();
		
		addStep("Hacer clic en el botón 'Enviar'.");
		creaPromo.btnEnviar();
		
		addStep("Hacer clic en el botón 'Aceptar' del mensaje.");
		creaPromo.btnAceptar();
		
		addStep("Hacer clic en el botón 'Buscar artículo'");
		creaPromo.btnBuscaArti();
		
		addStep("Hacer clic en el botón 'Aceptar' del mensaje.");
		creaPromo.btnAceptar();
		
		addStep("Seleccionar un paquete promocional");
		creaPromo.paquetePromocional(data.get("paquetePromo"));

		addStep("Seleccionar un valor en el campo 'Tipo de Mecanica'");
		creaPromo.selectTipoMecanica(data.get("tipoMecanica"));
		
		addStep("Hacer clic en el botón 'Buscar artículo'");
		creaPromo.btnBuscaArti();
		
		addStep("Buscar artículo");
		creaPromo.buscaArtiNuevo(data.get("buscaArti_superGrupo"), data.get("buscaArti_categoria"),data.get("buscaArti_subCategoria") , data.get("buscaArti_segmento"), data.get("buscaArti_Subsegmento"));
		
		creaPromo.buscaArtiNuevo(data.get("buscaArti_superGrupo"), data.get("buscaArti_categoria"),data.get("buscaArti_subCategoria") , data.get("buscaArti_segmento"), data.get("buscaArti_Subsegmento"));
		
		addStep("Hacer clic en el icono de flecha que apunta hacia la derecha.");
		creaPromo.btnFlechaAgregaTodos();
		
		addStep("Hacer clic en el icono de flecha que apunta hacia la izquierda.");
		creaPromo.btnFlechaQuitaTodos();
		creaPromo.btnLimpiar();
		
		creaPromo.buscaArtiNuevo(data.get("buscaArti_superGrupo"), data.get("buscaArti_categoria"),data.get("buscaArti_subCategoria") , data.get("buscaArti_segmento"), data.get("buscaArti_Subsegmento"));
		
		addStep("Hacer clic en el botón 'Agregar'.");
		creaPromo.btnAgregaArti();

		addStep("Hacer clic en el botón 'Buscar UDA'.");
		creaPromo.btnBuscaUda();
		addStep("Hacer clic en el botón Cancelar");
		creaPromo.btnCancelarUDA();
		
		addStep("Hacer clic en el botón 'Buscar UDA'.");
		creaPromo.btnBuscaUda();
		creaPromo.buscaUda(data.get("buscaUDA_campo"), data.get("buscaUDA_busqueda"), data.get("buscaUDA_valor"));
		
		addStep("Hacer clic en el botón 'Agregar'.");
		creaPromo.btnAgregaUDA();

		addStep("Hacer clic en el botón 'Guardar'");
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

		addStep("Hacer clic en el botón 'Artículo nuevo'.");
		creaPromo.btnArtiNuevo();
		
		addStep("Seleccionar el CheckBox de un artículo y hacer clic en el botón 'Eliminar'");
		creaPromo.elimiaArti("3");
		
		addStep("Hacer clic en el botón 'Guardar'.");
		creaPromo.btnGuardar();

		
		addStep("Ingresar a la opción de menú 'Promociones/Buscar',\r\n"
				+ "Captura el folio de la promoción recién creada y seleccionar los campos Año y Periodo "
				+ "a los que corresponde la promoción; y hacer clic en el botón 'Buscar");
		
		String folio = creaPromo.getFolio();
		
		creaPromo.buscaPromo(folio);
		
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
		return "Terminado. CP137_Pantalla Crear- Validar la creación de una promoción Simple dentro de un periodo configurado (con usuario Categoría Marca propia o similar)";
	}

	@Override
	public String setTestDesigner() {
		// TODO Auto-generated method stub
		return "Alicia Michelle Ceja Gomez";
	}

	@Override
	public String setTestFullName() {
		// TODO Auto-generated method stub
		return "ATC_FT_137_ValCrearPromSimplePerConfig_PromoBara_test";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}

}
