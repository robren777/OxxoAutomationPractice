package WorkFlows.estandarizacionHomologacionImpuestos.homologacionSIV_CHG0039717;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import WorkFlows.estandarizacionHomologacionImpuestos.pageObjectModelHomo.MetodoGeneral;
import WorkFlows.util.GlobalVariables;
import WorkFlows.util.Siv.pomSIV;
import WorkFlows.util.Siv.utilSiv_BuscarArticulo;
import WorkFlows.util.Siv.utilSiv_Conjuntos;
import WorkFlows.util.Siv.utilSiv_DatosBasicos;
import WorkFlows.util.Siv.utilSiv_EnvioMuestraFisica;
import WorkFlows.util.Siv.utilSiv_InboxPendientes;
import WorkFlows.util.Siv.utilSiv_fuenteAbasto;
import WorkFlows.util.ultimus.pomUltimus;
import modelo.BaseExecution;
import utils.selenium.FirefoxTest;
import utils.selenium.SeleniumUtil;
import utils.sql.SQLResult;

/**
* DESCRIPCIÓN:
* Validar dato adicional de una categoría por medio 
* del alta de artículo PLU de la misma categoría con 
* usuario CATEGORY aprobado el alta con un usuario
* CATALOGOS.
* 
* NOMBRE CASOS DE PRUEBA MANUAL QUE SE ABARCAN: 
* - MTC-FT-040 Validar la asignación de un PLU para un item de la categoría Vinos y Licores con la relación de FA CEDIS-NP
* - MTC-FT-041 Validar la asignación de un PLU para un item de la categoría Vinos y Licores con la relación de FA DIRECTA-PLANOGRAMABLE
* - MTC-FT-042 Validar la asignación de un PLU para un item de la categoría Higiene y Hogar con la relación de FA CEDIS-NP
* - MTC-FT-043 Validar la asignación de un PLU para un item de la categoría Higiene y Hogar con la relación de FA DIRECTA-PLANOGRAMABLE
* - MTC-FT-044 Validar la asignación de un PLU para un item de la categoría Fast Food Alimentos con la relación de FA CEDIS-NP
* - MTC-FT-045 Validar la asignación de un PLU para un item de la categoría Fast Food Alimentos con la relación de FA DIRECTA-PLANOGRAMABLE
* 
* PREREQUISITOS:
* - Accesso a las bases de datos:
* 		FCDASUAT
* 		FCMOMUAT (RMS16)
* 		FCRMSQA (RMS100 MEXICO SID)
* 		FCPFACQA (virtual)
* 		FCPVEQA (virtual)
* 
* INSUMOS:
* - DataProvider con la informacion del articulo a dar de alta
* 
* @author Angel Del Rio Portales
* @date 20/Enero/2024
*/

public class ATC_FT_003_Category_Alta_PLU_AprobarCatalogos_Planogramador_EHIA extends BaseExecution {
	private SeleniumUtil u;
	private pomSIV pom;
	private pomUltimus ultimus;
	private utilSiv_InboxPendientes inbox;
	private utilSiv_BuscarArticulo buscar;
	private utilSiv_DatosBasicos datosBasicos;
	private utilSiv_fuenteAbasto fuenteAbasto;
	private utilSiv_EnvioMuestraFisica muestraFisica;
	private utilSiv_Conjuntos conjunto;
	private String windowPega = "";

	@Test(dataProvider = "data-provider")

	public void ATC_003_Category_Alta_PLU_AprobarCatalogos_Planogramador_EHIA_test(HashMap<String, String> data)
			throws Exception {
		
		testCase.setProject_Name("Estandarizacion y Homologacion de Impuestos en Articulos");

		/// GET DATA FROM INPUT FILE ///
		// - Login -//
		String usuarioCategoria = data.get("usuarioCategoria");
		String usuarioCatalogo = data.get("usuarioCatalogo");

		// - Datos Basicos -//
		String descripcionCorta = data.get("descripcionCorta");
		String descripcionLarga = data.get("descripcionLarga");
		String superGrupo = data.get("superGrupo");
		String categoria = data.get("categoria");
		String subCategoria = data.get("subCategoria");
		String segmento = data.get("segmento");
		String subSegmento = data.get("subSegmento");
		String storeOrderMultiple = data.get("storeOrderMultiple");
		String unidadMedida = data.get("unidadMedida");
		String unidadVenta = data.get("unidadVenta");
		String codigoFiscal = data.get("codigoFiscal");
		String precioSugerido = data.get("precioSugerido");
		String checkOxxo = data.get("checkOxxo");
		String checkBara = data.get("checkBara");
		String presentacion = data.get("presentacion");
		String udaPresentacion = data.get("udaPresentacion");
		String contenido = data.get("contenido");
		String unidadContenido = data.get("unidadContenido");
		String gradosAlcohol = data.get("gradosAlcohol");
		String udaGradosAlcohol = data.get("udaGradosAlcohol");
		String diasPublicacion = data.get("diasPublicacion");
		String fechaPublicacion = data.get("fechaPublicacion");
		String base = data.get("base");
		String excluir = data.get("excluir");
		String peatonal = data.get("peatonal");
		String turOasis = data.get("turOasis");
		String mixtas = data.get("mixtas");
		String bara = data.get("bara");
		String marcaProveedor = data.get("marcaProveedor");
		String udaMarcaProveedor = data.get("udaMarcaProveedor");
		String marcaArticulo = data.get("marcaArticulo");
		String udaMarcaArticulo = data.get("udaMarcaArticulo");
		String checkMerma = data.get("checkMerma");
		String checkCandadoVenta = data.get("checkCandadoVenta");
		String udaLibre = data.get("udaLibre");

		// - Conjuntos -//
		String planogrupos = data.get("planogrupos");
		String segmentos = data.get("segmentos");
		String muebles = data.get("muebles");
		String plazas = data.get("plazas");
		String conjuntos = data.get("conjuntos");

		// - Fuente Abasto -//
		String razonSocialFuentesAbasto = data.get("razonSocial:FuentesAbasto");
		String fuenteAbastoPlazas = data.get("fuenteAbasto:Plazas");
		String arrayEach[] = data.get("vol_each").split(",");
		String arrayIner[] = data.get("vol_inner").split(",");
		String arrayCase[] = data.get("vol_case").split(",");
		String cama = data.get("camaTI");
		String altura = data.get("alturaHI");
		String costoBruto = data.get("costoBrutoUnitario");
		String objVenta = data.get("objVenta");
		String ventaMinEx = data.get("ventaMinEx");
		String CBPLU = data.get("CBPLU");
		String plaza = data.get("plaza");
		String porcentaje = data.get("porcentaje");

		// - Muestra Fisica -//
		String metodoEnvio = data.get("metodoEnvio");
		String personaQueEnvia = data.get("personaQueEnvia");
		String empresaPaqueteria = data.get("empresaPaqueteria");
		String direccionRecepcion = data.get("direccionRecepcion");
		String infoAdicional = data.get("infoAdicional");
		String telefono = data.get("telefono");
		String numGuia = data.get("numGuia");
		String fechaEnvio = data.get("fechaEnvio");
		String fechaEsperada = data.get("fechaEsperada");
		String nombreImagen = data.get("nombreImagen");

		/// INICIALIZACION DE POMS ///
		u = new SeleniumUtil(new FirefoxTest(), true);
		windowPega = u.getDriver().getWindowHandle();
		pom = new pomSIV(u, testCase);
		ultimus = new pomUltimus(u);
		inbox = new utilSiv_InboxPendientes(u, testCase);
		buscar = new utilSiv_BuscarArticulo(u, testCase);
		datosBasicos = new utilSiv_DatosBasicos(u, testCase);
		fuenteAbasto = new utilSiv_fuenteAbasto(u, testCase);
		muestraFisica = new utilSiv_EnvioMuestraFisica(u, testCase);
		conjunto = new utilSiv_Conjuntos(u, testCase);
		final String BASE_PATH = System.getProperty("user.dir");
		
		// Variables
		String cb;
		String plu;
		Boolean band;
		
		cb = "0";

		/// LOGIN ULTIMUS ///
		addStep("Iniciar sesion con usuario: " + usuarioCategoria);
		ultimus.loginUltimus(usuarioCategoria, "");
		testCase.addScreenShotCurrentStep(u, "Inicio de sesion en Ultimus_" + usuarioCategoria);
		
		/// INICIAR Y ABRIR SIV ///
		addStep("Ingresar al portal de SIV");
		ultimus.openSivNet();
		ultimus.loadingUltimus();
		testCase.addScreenShotCurrentStep(u, "Se inició sesón en SIV");

		/// 1.Datos básicos///
	 	/// AGREGAR ITEM ///
		addStep("Agregar Item: buscar por codigo de barras");
		inbox.agregarItem();
		u.hardWait(2);
		
		//Ingresar PLU
		buscar.enterCB(cb);
		testCase.addScreenShotCurrentStep(u, "CB ingresado");
		buscar.busquedaPredeterminada();

		/// CAPTURAR DATOS ///
		addStep("Capturar los Datos Basicos para el Item: " + cb);
		buscar.capturarDatos();

		/// INGRESAR DATOS BASICOS ///
		datosBasicos.enterDescripcionCorta(descripcionCorta);
		datosBasicos.enterDescripcionLarga(descripcionLarga);

		// - Datos Basicos - Estructura de Categoría -//
		datosBasicos.selectSuperGrupo(superGrupo);
		datosBasicos.selectCategoria(categoria);
		datosBasicos.selectSubCategoria(subCategoria);
		datosBasicos.selectSegmento(segmento);
		datosBasicos.selectSubSegmento(subSegmento);
		testCase.addScreenShotCurrentStep(u, "DatosBasicosEstructuraCategoria");
		
		// Validar Codigo SAT vs API
		pom.logL("Validar que el textbox Código SAT de Datos Basicos coincida con la información de la API y la tabla CAT_PROD_SERV");
		addStep("Validar que el textbox Código SAT de Datos Basicos coincida con la información de la API y la tabla CAT_PROD_SERV");
		u.hardWait(2);
		String codigoSAT = datosBasicos.getCodigoSAT();
		testCase.addScreenShotCurrentStep(u, "obtenerCodigoSAT");
		
		String uriSatCode = String.format(data.get("uriSatCode"));
		String supergrupoAPI = data.get("supergrupoApiSatCode");
		String categoriaAPI = data.get("categoriaApiSatCode");
		String subcategoriaAPI = data.get("subcategoriaApiSatCode");
		String segmentoAPI = data.get("segmentoApiSatCode");
		String subsegmentoAPI = data.get("subsegmentoApiSatCode");
	
		String codigoSATAPI = datosBasicos.getSatCodeAPI(uriSatCode, supergrupoAPI, categoriaAPI, subcategoriaAPI, segmentoAPI, subsegmentoAPI);
		assertEquals(codigoSATAPI, codigoSAT, "No coincide el código SAT con la API");
		
		// Validar con tabla CAT_PROD_SERV
		try {
			SQLResult queryResult;
//					utils.sql.SQLUtil dbFCPFACQA = new utils.sql.SQLUtil(GlobalVariables.DB_HOST_FCPFACQA_VIRTUAL,
//							GlobalVariables.DB_USER_FCPFACQA_VIRTUAL, GlobalVariables.DB_PASSW_FCPFACQA_VIRTUAL);
			utils.sql.SQLUtil dbFCPFACQA = new utils.sql.SQLUtil(GlobalVariables.DB_HOST_FCPFACQA,
					 GlobalVariables.DB_USER_FCPFACQA, GlobalVariables.DB_PASSWORD_FCPFACQA); 
			String queryCatProdServ = "select CODIGO_SAT, ESTATUS, ESTATUS_RMS, ID_CAT_PROD_SERV from XXSAT.XXSAT_CAT_PROD_SERV "
					+ "	where supergrupo = '"+supergrupoAPI+"' and categoria = '"+categoriaAPI+"' and sub_categoria = '"+subcategoriaAPI+"'"
							+ " and segmento = '"+segmentoAPI+"' and sub_segmento = '"+subsegmentoAPI+"'";

			pom.logM(queryCatProdServ);

			queryResult = executeQuery(dbFCPFACQA, queryCatProdServ);
			testCase.addQueryEvidenceCurrentStep(queryResult);
			Boolean validacion = false; String codigoSatBD = "";
			if (queryResult.getRowCount() > 0) {
				validacion = true;
				codigoSatBD = queryResult.getData(0, "CODIGO_SAT");
			}
													
			assertTrue(validacion, "No se encontro informacion en la tabla XXSAT_CAT_PROD_SERV");
			assertEquals(codigoSatBD, codigoSAT, "El dato Codigo SAT no coincide con el dato del campo CODIGO_SAT en la tabla XXSAT_CAT_PROD_SERV");
			
			String queryRmsDivision = "select DIVISION, DIV_NAME from XXCFD.RMS_DIVISION WHERE DIVISION = " + supergrupoAPI; //supergrupo
			String queryRmsGroups = "select  GROUP_NO, GROUP_NAME, DIVISION from XXCFD.RMS_GROUPS WHERE GROUP_NO = " + categoriaAPI + " AND DIVISION = " + supergrupoAPI; //Categoría
			String queryRmsDeps = "select DEPT, DEPT_NAME, GROUP_NO from XXCFD.RMS_DEPS WHERE DEPT = " + subcategoriaAPI + "  AND GROUP_NO = " + categoriaAPI; //Subcategoría
			String queryRmsClass = "select * from XXCFD.RMS_CLASS WHERE CLASS = " + segmentoAPI + " AND DEPT = " + subcategoriaAPI; //Segmento
			String queryRmsSubclass = "select * from XXCFD.RMS_SUBCLASS WHERE SUBCLASS  = " + subsegmentoAPI + " AND DEPT = " + subcategoriaAPI + " AND CLASS = " + segmentoAPI; //Subsegmento
			
			pom.logM(queryRmsDivision);
			pom.logM(queryRmsGroups);
			pom.logM(queryRmsDeps);
			pom.logM(queryRmsClass);
			pom.logM(queryRmsSubclass);

			queryResult = executeQuery(dbFCPFACQA, queryRmsDivision);
			testCase.addQueryEvidenceCurrentStep(queryResult);
			Boolean validacionRms1 = queryResult.getRowCount() > 0;
			assertTrue(validacionRms1, "No se encontro el dato en la tabla RMS_DIVISION");

			queryResult = executeQuery(dbFCPFACQA, queryRmsGroups);
			testCase.addQueryEvidenceCurrentStep(queryResult);
			Boolean validacionRms2 = queryResult.getRowCount() > 0;
			assertTrue(validacionRms2, "No se encontro el dato en la tabla RMS_GROUPS");

			queryResult = executeQuery(dbFCPFACQA, queryRmsDeps);
			testCase.addQueryEvidenceCurrentStep(queryResult);
			Boolean validacionRms3 = queryResult.getRowCount() > 0;
			assertTrue(validacionRms3, "No se encontro el dato en la tabla RMS_DEPS");

			queryResult = executeQuery(dbFCPFACQA, queryRmsClass);
			testCase.addQueryEvidenceCurrentStep(queryResult);
			Boolean validacionRms4 = queryResult.getRowCount() > 0;
			assertTrue(validacionRms4, "No se encontro el dato en la tabla RMS_CLASS");

			queryResult = executeQuery(dbFCPFACQA, queryRmsSubclass);
			testCase.addQueryEvidenceCurrentStep(queryResult);		
			Boolean validacionRms5 = queryResult.getRowCount() > 0;
			assertTrue(validacionRms5, "No se encontro el dato en la tabla RMS_SUBCLASS");

		
		} catch (SQLException e) {
			pom.logM(" -> " + e);
			throw new AssertionError("Fallo al ejecutar el query");
		}
		
		
		// Validar Datos Adicionales vs API
		pom.logL("Validar que el textbox Datos Adicionales de Datos Basicos coincida con la información de la API y la tabla DATO_REQUERIDO");
		addStep("Validar que el textbox Datos Adicionales de Datos Basicos coincida con la información de la API y la tabla DATO_REQUERIDO");
		
		u.hardWait(2);
		String datosAdicionales = datosBasicos.getAdditionalData();
		testCase.addScreenShotCurrentStep(u, "obtenerDatosAdicionales");
		
		String uriDA = String.format(data.get("uriAdditionalData"));
		String datosAdicionalesAPI = datosBasicos.getAdditionalDataAPI(uriDA, supergrupoAPI, categoriaAPI, subcategoriaAPI);

		assertEquals(datosAdicionalesAPI, datosAdicionales, "No coinciden los datos adicionales con la API");
		
		// Validar con tabla DATO_REQUERIDO
		try {
			SQLResult queryResult;
//					utils.sql.SQLUtil dbFCPFACQA = new utils.sql.SQLUtil(GlobalVariables.DB_HOST_FCPFACQA_VIRTUAL,
//							GlobalVariables.DB_USER_FCPFACQA_VIRTUAL, GlobalVariables.DB_PASSW_FCPFACQA_VIRTUAL);
			utils.sql.SQLUtil dbFCPFACQA = new utils.sql.SQLUtil(GlobalVariables.DB_HOST_FCPFACQA,
				 GlobalVariables.DB_USER_FCPFACQA, GlobalVariables.DB_PASSWORD_FCPFACQA); 
			String queryDatoRequerido = "select ID, ID_TIPO_DATO, VALOR_DEFAULT, ACTIVE from XXSAT.XXSAT_DATO_REQUERIDO"
					+ " where ID_SUPER_GRUPO = " + supergrupoAPI
					+ " and ID_CATEGORIA = " + categoriaAPI
					+ " and ID_SUB_CATEGORIA = " + subcategoriaAPI;
			
			pom.logM(queryDatoRequerido);

			queryResult = executeQuery(dbFCPFACQA, queryDatoRequerido);
			testCase.addQueryEvidenceCurrentStep(queryResult);
			Boolean validacion = false; 
			String idDatoAdicionalBD = "", datoAdicionalBD = "";
			if (queryResult.getRowCount() > 0) {
				validacion = true;
				idDatoAdicionalBD = queryResult.getData(0, "ID_TIPO_DATO");
			}

			String queryDatoRequerido2 = "select ID, VALOR_CATALOGO, DESCRIPCION, CREATE_DATETIME, ACTIVE from XXSAT.XXSAT_VALOR_CATALOGO" 
					+ " where ID = " + idDatoAdicionalBD;
			pom.logM(queryDatoRequerido2);
			queryResult = executeQuery(dbFCPFACQA, queryDatoRequerido2);
			testCase.addQueryEvidenceCurrentStep(queryResult);
			
			if (queryResult.getRowCount() > 0) {
				datoAdicionalBD = queryResult.getData(0, "VALOR_CATALOGO");
			}

			assertTrue(validacion, "No se encontro informacion en la tabla XXSAT_DATO_REQUERIDO");
			assertEquals(datoAdicionalBD, datosAdicionales, "El dato de Datos Adicionales no coincide con el dato del campo ID_TIPO_DATO en la tabla XXSAT_DATO_REQUERIDO");
		
		} catch (SQLException e) {
			pom.logM(" -> " + e);
			throw new AssertionError("Fallo al ejecutar el query");
		}

		addStep("Llenar el apartado Propiedades de Ventas, Informacion Comercial e Informacion de Contenido");
		// - Datos Basicos - Propiedades de Ventas-//
		datosBasicos.selectStoreOrderMultiple(storeOrderMultiple);
		datosBasicos.selectUnidadMedida(unidadMedida);
		datosBasicos.selectUnidadVenta(unidadVenta);

		// - Datos Basicos - Información Comercial-//
		datosBasicos.selectCodigoFiscal(codigoFiscal);
		datosBasicos.enterPrecioSugerido(precioSugerido);

		if (checkOxxo.equalsIgnoreCase("SI")) {
			datosBasicos.selectOxxo(true);
		} else {
			datosBasicos.selectOxxo(false);
		}
		if (checkBara.equalsIgnoreCase("SI")) {
			datosBasicos.selectBara(true);
		} else {
			datosBasicos.selectBara(false);
		}

		// - Datos Basicos - Información Contenido-//
		datosBasicos.enterPresentacion(presentacion, udaPresentacion);
		datosBasicos.enterContenido(contenido);
		datosBasicos.selectUnidadContenido(unidadContenido);
		datosBasicos.enterGradosAlcohol(gradosAlcohol, udaGradosAlcohol);
		testCase.addScreenShotCurrentStep(u, "Datos ingresados parte 1");

		datosBasicos.enterBase(base);
		datosBasicos.enterExcluir(excluir);
		datosBasicos.enterPeatonal(peatonal);
		datosBasicos.enterTurOasis(turOasis);
		datosBasicos.enterMixtas(mixtas);
		if (checkBara.equalsIgnoreCase("SI")) {
			datosBasicos.enterBara(bara);
		}

		datosBasicos.enterMarcaProveedor(marcaProveedor, udaMarcaProveedor);
		datosBasicos.enterMarcaArticulo(marcaArticulo, udaMarcaArticulo);
		datosBasicos.selectMerma(checkMerma);
		datosBasicos.selectCandado(checkCandadoVenta);

		datosBasicos.enterUdaLibre(udaLibre);
		testCase.addScreenShotCurrentStep(u, "Datos ingresados parte 2");
		
		pom.logL("Aparece un panel de campos de Datos Adicionales, se captura el campo " + datosAdicionales);
		addStep("Aparece un panel de campos de Datos Adicionales, se captura el campo " + datosAdicionales);
				
		
		// - Datos Basicos - DatosAdicionales -//
		//Datos del request
		String[] respuestaGetIeps;
		String uriIeps = data.get("uriIeps");//del dataprovider
		String gradosAlcoholDatosAdicionales = data.get("gradoAlcoholDA"); //del dataprovider
		String gradosToxicidadDatosAdicionales = data.get("gradosToxicidadDA"); //del dataprovider
		String tipoEnvasadoDatosAdicionales = data.get("tipoEnvasadoDA"); //del dataprovider
		String gramosDatosAdicionales = data.get("gramosDA");//del dataprovider
		String kilocaloriasDatosAdicionales = data.get("kilocaloriasDA");//del dataprovider
		
		//Para guardar dato del portal
		String descripcionDatosAdicionalesSIV = "";
		String iepsDatosAdicionalesSIV = "";
		String rangoPorcentajeAlcoholSIV = "";
		
		//Para guardar dato de la respuesta del request
		String rangoPorcentajeAlcoholRespuestaGetIeps = "";
		String descripcionRespuestaGetIeps = "";
		String iepsRespuestaGetIeps = "";				
		
		switch (datosAdicionales) {
		case "Grados de alcohol":
			datosBasicos.enterGradosAlcoholSAT(gradosAlcoholDatosAdicionales);
			testCase.addScreenShotCurrentStep(u, "enterGradosAlcoholSAT");
			
			//obtener datos del portal de SIV 
			rangoPorcentajeAlcoholSIV = datosBasicos.getRangoPorcentajeAlcoholSAT();
			descripcionDatosAdicionalesSIV = datosBasicos.getDescripcionSATieps();
			iepsDatosAdicionalesSIV = datosBasicos.getIepsSAT();
			
			datosBasicos.scrollSiguiente();
			testCase.addScreenShotCurrentStep(u, "descripcionIepsSAT");
			
			addStep("Validar que los campos Descripcion y IEPS encontrado coincidan con la respuesta de API SAT y la tabla XXSAT_CAT_GRADOS_ALCOHOL");
			pom.logL("Validar que los campos Descripcion y IEPS encontrado coincidan con la respuesta de API SAT y la tabla XXSAT_CAT_GRADOS_ALCOHOL");

			//obtener información del request API
			respuestaGetIeps = datosBasicos.getIepsAPI(uriIeps, gradosAlcoholDatosAdicionales, "1");
			rangoPorcentajeAlcoholRespuestaGetIeps = "Desde " + respuestaGetIeps[6] + " - Hasta " + respuestaGetIeps[7];
			descripcionRespuestaGetIeps = respuestaGetIeps[4];
			iepsRespuestaGetIeps = respuestaGetIeps[8];
			
			assertEquals(rangoPorcentajeAlcoholSIV, rangoPorcentajeAlcoholRespuestaGetIeps, "El rango de porcentaje de alcochol no es igual al obtenido del request");
			assertEquals(descripcionDatosAdicionalesSIV, descripcionRespuestaGetIeps, "La descripción no es igual al obtenido del request");
			assertEquals(iepsDatosAdicionalesSIV, iepsRespuestaGetIeps, "El IEPS no es igual al obtenido del request");					
			
			Double gradosAlcoholDA =  Double.parseDouble(gradosAlcoholDatosAdicionales);
			String idCatGradosAlcohol = "";
			if (gradosAlcoholDA == 0 ) {
				idCatGradosAlcohol = "1";
			}else if(gradosAlcoholDA >= 0.1 && gradosAlcoholDA <= 14) {
				idCatGradosAlcohol = "2";
			}else if(gradosAlcoholDA >= 14.1 && gradosAlcoholDA <= 20) {
				idCatGradosAlcohol = "3";
			}else if(gradosAlcoholDA >= 20.1 && gradosAlcoholDA <= 100) {
				idCatGradosAlcohol = "4";
			}
			
			try {
				SQLResult queryResult, queryResult1, queryResult2;
//						utils.sql.SQLUtil dbFCPFACQA = new utils.sql.SQLUtil(GlobalVariables.DB_HOST_FCPFACQA_VIRTUAL,
//								GlobalVariables.DB_USER_FCPFACQA_VIRTUAL, GlobalVariables.DB_PASSW_FCPFACQA_VIRTUAL);
			utils.sql.SQLUtil dbFCPFACQA = new utils.sql.SQLUtil(GlobalVariables.DB_HOST_FCPFACQA,
						 GlobalVariables.DB_USER_FCPFACQA, GlobalVariables.DB_PASSWORD_FCPFACQA); 
				String querySatCatGradosAlcohol1 = "SELECT ID_CAT_GRADOS_ALCOHOL, GRADOS_ALCOHOL, DESCRIPCION"
						+ " FROM XXSAT.XXSAT_CAT_GRADOS_ALCOHOL" 
						+ " WHERE ID_CAT_GRADOS_ALCOHOL = " + idCatGradosAlcohol;
				
				String querySatCatGradosAlcohol2 = "SELECT RANGO_DESDE, RANGO_HASTA, IEPS, ACTIVE"
						+ " FROM XXSAT.XXSAT_CAT_GRADOS_ALCOHOL" 
						+ " WHERE ID_CAT_GRADOS_ALCOHOL = " + idCatGradosAlcohol;
									
				pom.logM(querySatCatGradosAlcohol1);
				pom.logM(querySatCatGradosAlcohol2);
				queryResult1 = executeQuery(dbFCPFACQA, querySatCatGradosAlcohol1);
				queryResult2 = executeQuery(dbFCPFACQA, querySatCatGradosAlcohol2);
				testCase.addQueryEvidenceCurrentStep(queryResult1);
				testCase.addQueryEvidenceCurrentStep(queryResult2);
				
				Boolean validacion = false; String iepsBD = "";
				if (queryResult2.getRowCount() > 0) {
					validacion = true;
					iepsBD = queryResult2.getData(0, "IEPS");
					iepsBD = datosBasicos.agregarDecimal(iepsBD);
				}
				
				assertTrue(validacion, "No se encontro informacion en la tabla XXSAT_CAT_GRADOS_ALCOHOL");
				assertEquals(iepsBD, iepsDatosAdicionalesSIV, "El dato IEPS del portal no coincide con el dato del campo IEPS en la tabla XXSAT_CAT_GRADOS_ALCOHOL");
			
				String querySatValorCatalogo = "SELECT ID, ID_TIPO_CATALOGO, VALOR_CATALOGO, ACTIVE, DESCRIPCION" 
						+ " FROM XXSAT.XXSAT_VALOR_CATALOGO WHERE ID = 1 AND VALOR_CATALOGO = 'Grados de alcohol'";
						
				pom.logM(querySatValorCatalogo);
				queryResult = executeQuery(dbFCPFACQA, querySatValorCatalogo);
				testCase.addQueryEvidenceCurrentStep(queryResult);
				
				Boolean validacionCatalogo = false;
				if (queryResult.getRowCount() > 0) {
					validacionCatalogo = true;
				}
				
				assertTrue(validacionCatalogo, "No se encontro informacion en la tabla XXSAT_VALOR_CATALOGO");
				
				
			} catch (SQLException e) {
				pom.logM(" -> " + e);
				throw new AssertionError("Fallo al ejecutar el query");
			}
								
			break;
			
		case "Calorías":
			datosBasicos.enterGramosSAT(gramosDatosAdicionales);
			datosBasicos.enterKilocaloriasSAT(kilocaloriasDatosAdicionales);
			testCase.addScreenShotCurrentStep(u, "enterGramosKilocaloriasSAT");
			
			descripcionDatosAdicionalesSIV = datosBasicos.getDescripcionSATieps();
			iepsDatosAdicionalesSIV = datosBasicos.getIepsSAT();
			
			datosBasicos.scrollSiguiente();
			testCase.addScreenShotCurrentStep(u, "descripcionIepsSAT");
			
			addStep("Validar que los campos Descripcion y IEPS encontrado coincidan con la respuesta de API SAT y la tabla XXSAT_CAT_CALORIAS");
			pom.logL("Validar que los campos Descripcion y IEPS encontrado coincidan con la respuesta de API SAT y la tabla XXSAT_CAT_CALORIAS");

			//obtener información del request API
			respuestaGetIeps = datosBasicos.getIepsAPI(uriIeps, kilocaloriasDatosAdicionales, "2");
			descripcionRespuestaGetIeps = respuestaGetIeps[4];
			iepsRespuestaGetIeps = respuestaGetIeps[8];
			
			assertEquals(descripcionDatosAdicionalesSIV, descripcionRespuestaGetIeps, "La descripción no es igual al obtenido del request");
			assertEquals(iepsDatosAdicionalesSIV, iepsRespuestaGetIeps, "El IEPS no es igual al obtenido del request");					
			
			Double caloriasDA =  Double.parseDouble(kilocaloriasDatosAdicionales);
			String idCatCalorias = "";
			if(caloriasDA >= 0 && caloriasDA <= 274) {
				idCatCalorias = "1";
			}else if(caloriasDA >= 275 && caloriasDA <= 20000) {
				idCatCalorias = "2";
			}
			
			try {
				SQLResult queryResult;
//						utils.sql.SQLUtil dbFCPFACQA = new utils.sql.SQLUtil(GlobalVariables.DB_HOST_FCPFACQA_VIRTUAL,
//								GlobalVariables.DB_USER_FCPFACQA_VIRTUAL, GlobalVariables.DB_PASSW_FCPFACQA_VIRTUAL);
				utils.sql.SQLUtil dbFCPFACQA = new utils.sql.SQLUtil(GlobalVariables.DB_HOST_FCPFACQA,
						 GlobalVariables.DB_USER_FCPFACQA, GlobalVariables.DB_PASSWORD_FCPFACQA); 
				String querySatCatCalorias = "SELECT ID_CAT_CALORIA, DESCRIPCION, RANGO_DESDE,"
						+ " RANGO_HASTA, IEPS, ACTIVE"
						+ " FROM XXSAT.XXSAT_CAT_CALORIAS" 
						+ " WHERE ID_CAT_CALORIA = " + idCatCalorias;
									
				pom.logM(querySatCatCalorias);
				queryResult = executeQuery(dbFCPFACQA, querySatCatCalorias);
				testCase.addQueryEvidenceCurrentStep(queryResult);
				
				Boolean validacion = false; String iepsBD = "";
				if (queryResult.getRowCount() > 0) {
					validacion = true;
					iepsBD = queryResult.getData(0, "IEPS");
					iepsBD = datosBasicos.agregarDecimal(iepsBD);
				}
				
				assertTrue(validacion, "No se encontro informacion en la tabla XXSAT_CAT_CALORIAS");
				assertEquals(iepsBD, iepsDatosAdicionalesSIV, "El dato IEPS del portal no coincide con el dato del campo IEPS en la tabla XXSAT_CAT_CALORIAS");
			
				String querySatValorCatalogo = "SELECT ID, ID_TIPO_CATALOGO, VALOR_CATALOGO, ACTIVE, DESCRIPCION" 
						+ " FROM XXSAT.XXSAT_VALOR_CATALOGO WHERE ID = 3 AND VALOR_CATALOGO = 'Calorías'";
						
				pom.logM(querySatValorCatalogo);
				queryResult = executeQuery(dbFCPFACQA, querySatValorCatalogo);
				testCase.addQueryEvidenceCurrentStep(queryResult);
				
				Boolean validacionCatalogo = false;
				if (queryResult.getRowCount() > 0) {
					validacionCatalogo = true;
				}
				
				assertTrue(validacionCatalogo, "No se encontro informacion en la tabla XXSAT_VALOR_CATALOGO");
				
				
			} catch (SQLException e) {
				pom.logM(" -> " + e);
				throw new AssertionError("Fallo al ejecutar el query");
			}				
			
			break;
			
		case "Nivel de Toxicidad":
			datosBasicos.selectGradosToxicidadSAT(gradosToxicidadDatosAdicionales);
			testCase.addScreenShotCurrentStep(u, "enterGradosToxicidadSAT");
			
			descripcionDatosAdicionalesSIV = datosBasicos.getDescripcionSATieps();
			iepsDatosAdicionalesSIV = datosBasicos.getIepsSAT();

			datosBasicos.scrollSiguiente();
			testCase.addScreenShotCurrentStep(u, "descripcionIepsSAT");
			
			addStep("Validar que los campos Descripcion y IEPS encontrado coincidan con la respuesta de API SAT y la tabla XXSAT_CAT_TOXICIDAD");
			pom.logL("Validar que los campos Descripcion y IEPS encontrado coincidan con la respuesta de API SAT y la tabla XXSAT_CAT_TOXICIDAD");
			
			//obtener información del request API
			respuestaGetIeps = datosBasicos.getIepsAPI(uriIeps, gradosToxicidadDatosAdicionales, "3");
			descripcionRespuestaGetIeps = respuestaGetIeps[4];
			iepsRespuestaGetIeps = respuestaGetIeps[8];
			
			assertEquals(descripcionDatosAdicionalesSIV, descripcionRespuestaGetIeps, "La descripción no es igual al obtenido del request");
			assertEquals(iepsDatosAdicionalesSIV, iepsRespuestaGetIeps, "El IEPS no es igual al obtenido del request");					
			
			try {
				SQLResult queryResult;
//						utils.sql.SQLUtil dbFCPFACQA = new utils.sql.SQLUtil(GlobalVariables.DB_HOST_FCPFACQA_VIRTUAL,
//								GlobalVariables.DB_USER_FCPFACQA_VIRTUAL, GlobalVariables.DB_PASSW_FCPFACQA_VIRTUAL);
				utils.sql.SQLUtil dbFCPFACQA = new utils.sql.SQLUtil(GlobalVariables.DB_HOST_FCPFACQA,
						 GlobalVariables.DB_USER_FCPFACQA, GlobalVariables.DB_PASSWORD_FCPFACQA); 
				String querySatCatToxicidad = "SELECT ID_CAT_TOXICIDAD, TOXICIDAD,"
						+ " DESCRIPCION, IEPS, ACTIVE"
						+ " FROM XXSAT.XXSAT_CAT_TOXICIDAD" 
						+ " WHERE TOXICIDAD = " + gradosToxicidadDatosAdicionales;
									
				pom.logM(querySatCatToxicidad);
				queryResult = executeQuery(dbFCPFACQA, querySatCatToxicidad);
				testCase.addQueryEvidenceCurrentStep(queryResult);
				
				Boolean validacion = false; String iepsBD = "";
				if (queryResult.getRowCount() > 0) {
					validacion = true;
					iepsBD = queryResult.getData(0, "IEPS");
					iepsBD = datosBasicos.agregarDecimal(iepsBD);
				}
				
				assertTrue(validacion, "No se encontro informacion en la tabla XXSAT_CAT_TOXICIDAD");
				assertEquals(iepsBD, iepsDatosAdicionalesSIV, "El dato IEPS del portal no coincide con el dato del campo IEPS en la tabla XXSAT_CAT_TOXICIDAD");
			
				String querySatValorCatalogo = "SELECT ID, ID_TIPO_CATALOGO, VALOR_CATALOGO, ACTIVE, DESCRIPCION" 
						+ " FROM XXSAT.XXSAT_VALOR_CATALOGO WHERE ID = 2 AND VALOR_CATALOGO = 'Nivel de Toxicidad'";
						
				pom.logM(querySatValorCatalogo);
				queryResult = executeQuery(dbFCPFACQA, querySatValorCatalogo);
				testCase.addQueryEvidenceCurrentStep(queryResult);
				
				Boolean validacionCatalogo = false;
				if (queryResult.getRowCount() > 0) {
					validacionCatalogo = true;
				}
				
				assertTrue(validacionCatalogo, "No se encontro informacion en la tabla XXSAT_VALOR_CATALOGO");
				
				
			} catch (SQLException e) {
				pom.logM(" -> " + e);
				throw new AssertionError("Fallo al ejecutar el query");
			}				
			
			break;
			
		// Actualmente no se utiliza Tipo de Envasado	
		case "Tipo de Envasado":
			datosBasicos.selectTipoEnvasadoSAT(tipoEnvasadoDatosAdicionales);
			testCase.addScreenShotCurrentStep(u, "enterTipoEnvasadoSAT");
			
			respuestaGetIeps = datosBasicos.getIepsAPI(uriIeps, tipoEnvasadoDatosAdicionales, "4");
			descripcionDatosAdicionalesSIV = datosBasicos.getDescripcionSATieps();
			iepsDatosAdicionalesSIV = datosBasicos.getIepsSAT();
			descripcionRespuestaGetIeps = respuestaGetIeps[5];
			iepsRespuestaGetIeps = respuestaGetIeps[9];
			
			assertEquals(descripcionDatosAdicionalesSIV, descripcionRespuestaGetIeps, "La descripción no es igual al obtenido del request");
			assertEquals(iepsDatosAdicionalesSIV, iepsRespuestaGetIeps, "El IEPS no es igual al obtenido del request");			
			
			try {
				SQLResult queryResult;
//						utils.sql.SQLUtil dbFCPFACQA = new utils.sql.SQLUtil(GlobalVariables.DB_HOST_FCPFACQA_VIRTUAL,
//								GlobalVariables.DB_USER_FCPFACQA_VIRTUAL, GlobalVariables.DB_PASSW_FCPFACQA_VIRTUAL);
				utils.sql.SQLUtil dbFCPFACQA = new utils.sql.SQLUtil(GlobalVariables.DB_HOST_FCPFACQA,
						 GlobalVariables.DB_USER_FCPFACQA, GlobalVariables.DB_PASSWORD_FCPFACQA); 
				String querySatCatTipoEnvase = "SELECT ID_CAT_TIPO_ENVASE, TIPO_ENVASE,"
						+ " DESCRIPCION, IEPS, ACTIVE"
						+ " FROM XXSAT.XXSAT_CAT_TIPO_ENVASE" 
						+ " WHERE TIPO_ENVASE = " + tipoEnvasadoDatosAdicionales;
									
				pom.logM(querySatCatTipoEnvase);
				queryResult = executeQuery(dbFCPFACQA, querySatCatTipoEnvase);
				testCase.addQueryEvidenceCurrentStep(queryResult);
				
				Boolean validacion = false; String iepsBD = "";
				if (queryResult.getRowCount() > 0) {
					validacion = true;
					iepsBD = queryResult.getData(0, "IEPS");
					iepsBD = datosBasicos.agregarDecimal(iepsBD);
				}
				
				assertTrue(validacion, "No se encontro informacion en la tabla XXSAT_CAT_TIPO_ENVASE");
				assertEquals(iepsBD, iepsDatosAdicionalesSIV, "El dato IEPS del portal no coincide con el dato del campo IEPS en la tabla XXSAT_CAT_TIPO_ENVASE");
			
				utils.sql.SQLUtil dbXXVA_SIV3 = new utils.sql.SQLUtil(GlobalVariables.DB_HOST_FCPVEQA2,
						GlobalVariables.DB_USER_FCPVEQA2, GlobalVariables.DB_PASSWORD_FCPVEQA2);
				String querySatTipoEnvasado = "SELECT * FROM XXVA_SIV3.XXVA_SAT_TIPO_ENVASADO"
						+ " WHERE ENVASADO = " + tipoEnvasadoDatosAdicionales
						+ " AND ID_RELACION = '"+cb+"'";
				pom.logM(querySatTipoEnvasado);
				queryResult = executeQuery(dbXXVA_SIV3, querySatTipoEnvasado);
				testCase.addQueryEvidenceCurrentStep(queryResult);
				
				Boolean validacionTE = false;
				if (queryResult.getRowCount() > 0) {
					validacionTE = true;	
				}
				assertTrue(validacionTE, "No se encontro informacion en la tabla XXVA_SAT_TIPO_ENVASADO");
				
				
			} catch (SQLException e) {
				pom.logM(" -> " + e);
				throw new AssertionError("Fallo al ejecutar el query");
			}				
			
			break;

			default:
				throw new AssertionError("Dato Adicional invalido");
		}
		
		// - Datos Basicos - Impuestos encontrados -//
		
		pom.logL("Debajo de \\\"IEPS Encontrado\\\" aparece la nueva etiqueta de \\\"Desglose de Impuestos\\\" reemplazando a \\\"Impuestos a la Venta\\\" donde debajo del impuesto por región aparecen los impuestos a la venta y/o a la compra dependiendo de si su región lo requiere con el tipo de item.");
		addStep("Debajo de \"IEPS Encontrado\" aparece la nueva etiqueta de \"Desglose de Impuestos\" reemplazando a \"Impuestos a la Venta\" donde debajo del impuesto por región aparecen los impuestos a la venta y/o a la compra dependiendo de si su región lo requiere con el tipo de item.\r\n"
				+ "	Validar en la BD FCPVEQA el desglose de impuestos por subcategoría");
		
		String dept = subcategoriaAPI;//desde el dataprovider
		
		boolean impuestosNoEncontrados = datosBasicos.impuestosNoEncontradosDisplayed();

		datosBasicos.scrollSiguiente();
		testCase.addScreenShotCurrentStep(u, "tablaImpuestosEncontrados");
		
		try {						
			String queryVatCodes = "";
			String vatCodeDescViejo = "";
			String[] vatCodeDescViejoArr;
			String vatCodeViejo = "";
			String vatCodeNuevo = "";
			
			utils.sql.SQLUtil dbFCDASUAT = new utils.sql.SQLUtil(GlobalVariables.DB_HOST_FCDASUAT,
					GlobalVariables.DB_USER_FCDASUAT, GlobalVariables.DB_PASSWORD_FCDASUAT);
			
			// Impuestos desde el DAS
			String queryVatDeps = "select * from rms16_v.v_vat_deps where vat_region < 1000 and dept = " + dept;
			pom.logM(queryVatDeps);
			SQLResult queryResultVatDeps = executeQuery(dbFCDASUAT, queryVatDeps);
			testCase.addTextEvidenceCurrentStep("Impuestos desde el DAS:");
			testCase.addQueryEvidenceCurrentStep(queryResultVatDeps);
			
			if (impuestosNoEncontrados) {
				pom.logL("No se encontron impuestos");
				for (int k = 0; k < queryResultVatDeps.getRowCount(); k++) {
					vatCodeViejo = queryResultVatDeps.getData(k, "VAT_CODE");
					queryVatCodes = "select VAT_CODE, replace(vat_code_desc, ' ', '') VAT_CODE_DESC from rms16_v.v_vat_codes where VAT_CODE = '" + vatCodeViejo + "'";
					pom.logM(queryVatCodes);
					SQLResult queryResultVatCodes = executeQuery(dbFCDASUAT, queryVatCodes);
					testCase.addQueryEvidenceCurrentStep(queryResultVatCodes);
					vatCodeDescViejo = queryResultVatCodes.getData(0, "VAT_CODE_DESC");
					pom.logM("Vat Code Desc viejo: " + vatCodeDescViejo);
					
					vatCodeDescViejoArr = vatCodeDescViejo.split(";");
					iepsDatosAdicionalesSIV = datosBasicos.removerDecimal(iepsDatosAdicionalesSIV);

					// Validar que no exista Vat Code Desc con el nuevo IEPS
					testCase.addTextEvidenceCurrentStep("Se valida que no exista el nuevo impuesto con IEPS " + iepsDatosAdicionalesSIV);
					queryVatCodes = "select * from rms16_v.v_vat_codes "
							+ "where replace(VAT_CODE_DESC, ' ', '') like '" + vatCodeDescViejoArr[0] + ";" + iepsDatosAdicionalesSIV + "%'";
					pom.logM(queryVatCodes);
					queryResultVatCodes = executeQuery(dbFCDASUAT, queryVatCodes);
					testCase.addQueryEvidenceCurrentStep(queryResultVatCodes);
					assertTrue(queryResultVatCodes.getRowCount() == 0, "Se encontron impuestos");
				}
				
			} else {
				// Si se encuentran impuestos
				
				List<String[]> listaValoresImpuestoVenta = datosBasicos.getListaValoresImpuestosVenta();

				// int cantidadFilasTabla = valoresImpuestoVenta.length;
				int cantidadFilasTabla = listaValoresImpuestoVenta.size();
				int cont = 0;
				for (int k = 0; k < cantidadFilasTabla; k++) {
					String[] fila = listaValoresImpuestoVenta.get(k);

					String region = "";
					if (fila[0].equals("Frontera")) {// valoresImpuestoVenta[k][0]
						region = "2";
					} else if (fila[0].equals("Interior")) {
						region = "1";
					} else if (fila[0].equals("Bara")) {
						region = "4";
					}

					String tipo = "";
					if (fila[1].equals("Compra")) {// valoresImpuestoVenta[k][1]
						tipo = "C";
					} else if (fila[1].equals("Venta")) {
						tipo = "R";
					} else if (fila[1].equals("Compra/Venta")) {
						tipo = "B";
					}

					String queryVatDepsConValores = "select * from rms16_v.v_vat_deps"
							+ " where vat_region < 1000 and dept = " + dept + " and vat_region = " + region
							+ " and vat_type = '" + tipo + "'";
					pom.logM(queryVatDepsConValores);
					SQLResult queryResultVatDepsConValores = executeQuery(dbFCDASUAT, queryVatDepsConValores);
					testCase.addQueryEvidenceCurrentStep(queryResultVatDepsConValores);
					vatCodeViejo = queryResultVatDepsConValores.getData(0, "VAT_CODE");

					queryVatCodes = "select VAT_CODE, replace(vat_code_desc, ' ', '') VAT_CODE_DESC from rms16_v.v_vat_codes where VAT_CODE = '" + vatCodeViejo + "'";
					pom.logM(queryVatCodes);
					SQLResult queryResultVatCodes = executeQuery(dbFCDASUAT, queryVatCodes);
					testCase.addQueryEvidenceCurrentStep(queryResultVatCodes);
					vatCodeDescViejo = queryResultVatCodes.getData(0, "VAT_CODE_DESC");
					pom.logM("Vat Code Desc viejo: " + vatCodeDescViejo);

					vatCodeDescViejoArr = vatCodeDescViejo.split(";");
					iepsDatosAdicionalesSIV = datosBasicos.removerDecimal(iepsDatosAdicionalesSIV);

					// Validar que exista Vat Code Desc con el nuevo IEPS
					testCase.addTextEvidenceCurrentStep("Se valida que exista el nuevo impuesto con IEPS " + iepsDatosAdicionalesSIV);
					queryVatCodes = "select * from rms16_v.v_vat_codes "
							+ "where replace(VAT_CODE_DESC, ' ', '') like '" + vatCodeDescViejoArr[0] + ";" + iepsDatosAdicionalesSIV + "%'";
					pom.logM(queryVatCodes);
					queryResultVatCodes = executeQuery(dbFCDASUAT, queryVatCodes);
					testCase.addQueryEvidenceCurrentStep(queryResultVatCodes);
					vatCodeNuevo = queryResultVatCodes.getData(0, "VAT_CODE");
					if (queryResultVatCodes.getRowCount() > 0) {
						cont++;
					}
					assertEquals(vatCodeNuevo, fila[2], "No coincide el impuesto con el nuevo IEPS");

				}

				// comparar filas encontradas en la BD vs cantidad de filas obtenidas del portal
				assertEquals(cont, cantidadFilasTabla, "No se encontro la información de impuesto a la venta que se muestra en el portal en la tabla V_VAT_CODES");
			}

		} catch (SQLException e) {
			pom.logM(" -> " + e);
			throw new AssertionError("Fallo al ejecutar el query");
		}		


		/// EXTRAS ///
		datosBasicos.selectDiasPublicacion(diasPublicacion);
		datosBasicos.selectFechaPublicacion(fechaPublicacion);

		pom.logL("Guardar datos basicos");
		addStep("Guardar datos basicos");
		datosBasicos.siguiente();
		
		pom.logL("Validar las siguientes consultas de la BD FCPVEQA verificando que el item se guardó correctamente");
		addStep("Validar las siguientes consultas de la BD FCPVEQA verificando que el item se guardó correctamente");
		
		try {
			SQLResult queryResult;
			utils.sql.SQLUtil dbXXVA_SIV3 = new utils.sql.SQLUtil(GlobalVariables.DB_HOST_FCPVEQA2,
					GlobalVariables.DB_USER_FCPVEQA2, GlobalVariables.DB_PASSWORD_FCPVEQA2);
		
			String iepsSatVatRegionBD = "";
			String querySatVatRegion1 = "SELECT ID_SVC, ID_RELACION, TIPO_CATALOGO, VAT_REGION, VAT_TYPE, VAT_CODE FROM XXVA_SIV3.XXVA_SAT_VAT_CODES WHERE ID_RELACION = '"+cb+"' ORDER BY ID_SVC desc";
			String querySatVatRegion2 = "SELECT IVA, IEPS, SAT_CODE, VALOR_CATALOGO FROM XXVA_SIV3.XXVA_SAT_VAT_CODES WHERE ID_RELACION = '"+cb+"' ORDER BY ID_SVC desc";
			pom.logM(querySatVatRegion1);
			pom.logM(querySatVatRegion2);
			SQLResult queryResultP1 = executeQuery(dbXXVA_SIV3, querySatVatRegion1);
			SQLResult queryResultP2 = executeQuery(dbXXVA_SIV3, querySatVatRegion2);
			testCase.addQueryEvidenceCurrentStep(queryResultP1);
			testCase.addQueryEvidenceCurrentStep(queryResultP2);
			
			Boolean validacion = false;
			if (queryResultP1.getRowCount() > 0) {
				validacion = true;
				iepsSatVatRegionBD = queryResultP2.getData(0, "IEPS");
				iepsSatVatRegionBD = datosBasicos.removerDecimal(iepsSatVatRegionBD);
			}
			
			if (impuestosNoEncontrados) {
				testCase.addTextEvidenceCurrentStep("Nota: La tabla XXVA_SAT_VAT_CODES no se llena al no encontrar impuestos");
				pom.logM("Nota: la tabla XXVA_SAT_VAT_CODES no se llena al no encontrar impuestos");
				assertFalse(validacion, "Se encontro información del item en la tabla XXVA_SAT_VAT_CODES");
			} else {
				assertTrue(validacion, "No se encontro información del item en la tabla XXVA_SAT_VAT_CODES");
				assertEquals(iepsSatVatRegionBD, iepsDatosAdicionalesSIV, "El dato IEPS de la tabla XXVA_SAT_VAT_CODES no coincide con el mostrado en el portal");
			
				switch (datosAdicionales) {
				case "Grados de alcohol":
					String querySatGradosAlcohol = "SELECT * FROM XXVA_SIV3.XXVA_SAT_GRADOS_ALCOHOL WHERE ID_RELACION = '"+cb+"'";
					pom.logM(querySatGradosAlcohol);
					queryResult = executeQuery(dbXXVA_SIV3, querySatGradosAlcohol);
					testCase.addQueryEvidenceCurrentStep(queryResult);
					Boolean validacionGA = false;
					if (queryResult.getRowCount() > 0)
						validacionGA = true;					
					assertTrue(validacionGA, "No se encontro información del item en la tabla XXVA_SAT_GRADOS_ALCOHOL");
					
					break;
					
				case "Calorías":	
					String querySatEtiCalorica = "SELECT * FROM XXVA_SIV3.XXVA_SAT_ETI_CALORICA WHERE ID_RELACION = '"+cb+"'";
					pom.logM(querySatEtiCalorica);
					queryResult = executeQuery(dbXXVA_SIV3, querySatEtiCalorica);
					testCase.addQueryEvidenceCurrentStep(queryResult);
					Boolean validacionC = false;
					if (queryResult.getRowCount() > 0)
						validacionC = true;					
					assertTrue(validacionC, "No se encontro información del item en la tabla XXVA_SAT_ETI_CALORICA");
					
					break;
					
				case "Nivel de Toxicidad":
					String querySatGradosToxicidad = "SELECT * FROM XXVA_SIV3.XXVA_SAT_GRADOS_TOXICIDAD WHERE ID_RELACION = '"+cb+"'";
					pom.logM(querySatGradosToxicidad);
					queryResult = executeQuery(dbXXVA_SIV3, querySatGradosToxicidad);
					testCase.addQueryEvidenceCurrentStep(queryResult);
					Boolean validacionGT = false;
					if (queryResult.getRowCount() > 0)
						validacionGT = true;					
					assertTrue(validacionGT, "No se encontro información del item en la tabla XXVA_SAT_GRADOS_TOXICIDAD");
					
					break;
				
				// Actualmente no se utiliza Tipo de Envasado	
				case "Tipo de Envasado":
					String querySatTipoEnvasado = "SELECT * FROM XXVA_SIV3.XXVA_SAT_TIPO_ENVASADO WHERE ID_RELACION = '"+cb+"'";
					pom.logM(querySatTipoEnvasado);
					queryResult = executeQuery(dbXXVA_SIV3, querySatTipoEnvasado);
					testCase.addQueryEvidenceCurrentStep(queryResult);
					Boolean validacionTE = false;
					if (queryResult.getRowCount() > 0)
						validacionTE = true;					
					assertTrue(validacionTE, "No se encontro información del item en la tabla XXVA_SAT_TIPO_ENVASADO");
					
					break;
				}
			}
			
		} catch (SQLException e) {
			pom.logM(" -> " + e);
			throw new AssertionError("Fallo al ejecutar el query");
		}
		
		pom.addDataToDataProviderFile(this.getDataProviderPath(), "Sheet1", 4, cb);
		testCase.addScreenShotCurrentStep(u, "CB en inbox");
			
		/// 2.Conjuntos ///
		/// IR AL MODULO DE CONJUNTOS ///
		addStep("Ir a Conjuntos");
		u.hardWait(4);
		inbox.seleccionarItemPendiente(cb);
		inbox.conjuntos();
		u.hardWait(2);
		testCase.addScreenShotCurrentStep(u, "Modulo Conjuntos");

		if (pom.msgModal().contains("No se han configurado reglas de precio para Bara"))
			pom.optionMsgModal("OK");

		addStep("Seleccionar los Conjuntos");
		u.hardWait(2);
		conjunto.seleccionarOpcion("Planogrupos", planogrupos);

		conjunto.buscarSegmentos();
		u.hardWait(2);
		conjunto.seleccionarOpcion("Segmentos", segmentos);

		conjunto.buscarMuebles();
		u.hardWait(2);
		conjunto.seleccionarOpcion("Muebles", muebles);

		conjunto.buscarPlazas();
		u.hardWait(2);
		conjunto.seleccionarOpcion("Regiones", plazas);

		conjunto.buscarConjuntos();
		u.hardWait(2);
		conjunto.seleccionarOpcion("Conjuntos", conjuntos);

		testCase.addScreenShotCurrentStep(u, "Conjuntos seleccionados");

		addStep("Seleccionar Cobertura Objetivo");
		conjunto.buscar();
		conjunto.coberturaObjetivo();
		conjunto.seleccionarObjetivos();
		testCase.addScreenShotCurrentStep(u, "Cobertura Objetivos");

		addStep("Configurar Coberturas");
		conjunto.configurar();
		u.hardWait(3);
		conjunto.porcentaje();
		u.hardWait(1);

		conjunto.guardarConfiguracion();
		u.hardWait(1);

		conjunto.salir();
		u.hardWait(2);
		testCase.addScreenShotCurrentStep(u, "Coberturas OK");
		
		/// 3.Fuentes de Abasto ///
		/// IR AL MODULO FUENTE DE ABASTO ///
		addStep("Ir a Fuente Abasto");
		u.hardWait(3);
		inbox.seleccionarItemPendiente(cb);
		inbox.fuenteAbasto();
		u.hardWait(3);
		testCase.addScreenShotCurrentStep(u, "Modulo Fuente Abasto");

		addStep("Seleccionar las n razon social");
		u.hardWait(1);
		fuenteAbasto.buscarFuenteAbasto();
		fuenteAbasto.seleccionarFuentesAbasto(razonSocialFuentesAbasto);
		fuenteAbasto.aceptarFuenteAbasto();
		fuenteAbasto.seleccionaRadioFuenteAbasto2(razonSocialFuentesAbasto);

		addStep("Seleccionar las plazas para cada fuente de abasto");
		u.hardWait(1);
		fuenteAbasto.relacionarPlazas(fuenteAbastoPlazas);
		testCase.addScreenShotCurrentStep(u, "Crear relacion");

		fuenteAbasto.quitarDuplicidadRelaciones(razonSocialFuentesAbasto);
		u.hardWait(2);

		fuenteAbasto.seleccionarCheckboxEncabezado(true);
		addStep("Configuracion Tecnica");
		fuenteAbasto.configTecnica();

		if (pom.msgModal().contains("para un Cedis:"))
			pom.optionMsgModal("OK");

		for (int i = 0; i < arrayEach.length; i++) {
			pom.logM("Array Each ---> : " + arrayEach[i]);
		}
		testCase.addTextEvidenceCurrentStep("Volumentria");
		fuenteAbasto.vol_each(arrayEach);

		fuenteAbasto.vol_inner(arrayIner);

		fuenteAbasto.vol_case(arrayCase);

		fuenteAbasto.tarima(cama, altura);
		testCase.addScreenShotCurrentStep(u, "Volumetria");

		testCase.addTextEvidenceCurrentStep("Costo Bruto y Ligar Documentos");
		fuenteAbasto.abastoConfigArticulo(costoBruto);

		String pathCartaCompro = BASE_PATH + "\\Config\\Input\\Files\\Siv\\CARTA_COMPROMISO_OXXO.docx";
		String pathFichaTec = BASE_PATH + "\\Config\\Input\\Files\\Siv\\FICHA_TECNICA_OXXO.docx";

		fuenteAbasto.subirArchivos(pathCartaCompro, pathFichaTec);

		addStep("Establecer los Parametros de Indicadores");
		fuenteAbasto.paramIndicadores(objVenta, ventaMinEx);

		addStep("Calculo De Pronostico de Ventas");
		fuenteAbasto.buscaArticulo(CBPLU);
		fuenteAbasto.calPronostVenta(plaza, porcentaje);
		 
		/// 4.Muestra física ///
		/// IR AL MODULO ENVIO MUESTRA FISICA ///
		addStep("Ir a Envio Muestra Fisica");
		u.hardWait(2);
		inbox.seleccionarItemPendientePLU(cb, descripcionCorta);
		inbox.envioMuestraFisica();
		testCase.addScreenShotCurrentStep(u, "Envio Muestra Fisica");

		addStep("Capturar Envio: " + metodoEnvio);
		muestraFisica.seleccionarItemInboxEnvioMuestraFisicaPLU(cb, descripcionCorta);
		muestraFisica.capturarNuevoEnvio();

		String pathImage = BASE_PATH + "\\Config\\Input\\Files\\Siv\\Photos\\" + nombreImagen;
		u.hardWait(3);

		System.out.println(metodoEnvio.toUpperCase());

		switch (metodoEnvio.toUpperCase()) {
		case "ENTREGA PERSONAL":
			muestraFisica.entregaPersonal(personaQueEnvia, telefono);
			break;
		case "PAQUETERIA":
			muestraFisica.paqueteria(personaQueEnvia, empresaPaqueteria, direccionRecepcion, infoAdicional, telefono,
					numGuia, fechaEnvio, fechaEsperada);
			break;
		case "SIN MUESTRA FISICA":
			muestraFisica.sinMuestraFisica();
			break;
		case "UPLOAD FOTOGRAFIA":
			muestraFisica.uploadFotografia(personaQueEnvia, empresaPaqueteria, direccionRecepcion, infoAdicional,
					telefono, numGuia, fechaEnvio, fechaEsperada, pathImage);
			break;
		default:
			throw new AssertionError("La opcion no es valida: " + metodoEnvio);
		}

		u.hardWait(1);
		muestraFisica.grabarEnvio();

		addStep("Enviar Muestra Fisica");
		u.hardWait(1);
		muestraFisica.seleccionarItemInboxEnvioMuestraFisicaPLU(cb, descripcionCorta);
		muestraFisica.enviarMuestraFisica();

		muestraFisica.salirMuestraFisica();
		u.hardWait(1);
		testCase.addScreenShotCurrentStep(u, "CB status");

		/// 5.Aprobación con Catálogos ///
		/// CERRAR LA VENTANA DE SIV ///
		ultimus.cerrarVentanaPortal(windowPega);

		/// Cerrar sesion en Ultimus ///
		addStep("Cerrar sesion en Ultimus");
		ultimus.cerrarSesionUltimus();
		u.hardWait(2);
		testCase.addScreenShotCurrentStep(u, "Sesion Cerrada");
		
		/// LOGUEARSE CON USUARIO CATALOGO ///
		addStep("Iniciar sesion con usuario: " + usuarioCatalogo);
		ultimus.loginUltimus(usuarioCatalogo, "");
		testCase.addScreenShotCurrentStep(u, "Inicio de sesion en Ultimus_" + usuarioCatalogo);

		/// INICIAR Y ABRIR SIV ///
		addStep("Ingresar al portal de SIV");
		ultimus.openSivNet();
		ultimus.loadingUltimus();
		testCase.addScreenShotCurrentStep(u, "Se inició sesón en SIV");
		
		/// SELECCIONAR EL MENU CATALOGO MUESTRA FISICA ///
		addStep("Seleccionar el menu: Inbox Catalogo Muestra Fisica");
		inbox.menuInboxCatalogoMuestraFisica();

		/// SELECCIONAR LA CATEGORIA CORRESPONDIENTE ///
		addStep("Seleccionar la categoria e ir a Muestra Fisica");
		inbox.seleccionarCategoriaTablaCatalogos(categoria);
		inbox.muestraFisicaCat();
		u.hardWait(1);

		/// ACEPTAR MUESTRA FISICA ///
		addStep("Seleccionar el Codigo de Barras y Aceptar");
		inbox.seleccionarItemInboxCatalogosPLU(descripcionCorta);
		inbox.aceptarItemEnCatalogos();

		u.hardWait(1);
		testCase.addScreenShotCurrentStep(u, "CB aceptado");

		/// CERRAR LA VENTANA DE SIV ///
		ultimus.cerrarVentanaPortal(windowPega);

		/// Cerrar sesion en Ultimus ///
		addStep("Cerrar sesion en Ultimus");
		ultimus.cerrarSesionUltimus();
		u.hardWait(1);
		testCase.addScreenShotCurrentStep(u, "Sesion Cerrada");

		/// LOGIN ULTIMUS ///
		addStep("Iniciar sesion con usuario: " + usuarioCategoria);
		ultimus.loginUltimus(usuarioCategoria, "");
		testCase.addScreenShotCurrentStep(u, "Inicio de sesion en Ultimus_" + usuarioCategoria);

		/// INICIAR Y ABRIR SIV ///
		addStep("Ingresar al portal de SIV");
		ultimus.openSivNet();
		ultimus.loadingUltimus();
		testCase.addScreenShotCurrentStep(u, "Se inició sesón en SIV");

		/// SELECCIONAR EL CB EN LA TABLA DE PENDIENTES ///
		addStep("Validar que los modulos estan en OK");
		inbox.seleccionarItemPendiente(cb);

		/// VERIFICAR COLUMNAS EN OK ///
		inbox.verificarColumnasEnOK(cb);
		testCase.addTextEvidenceCurrentStep("Los tres modulos estan en OK");

		/// ENVIAR SELECCION ///
		addStep("Enviar Seleccion");
		inbox.enviarSeleccion();
		u.hardWait(1);

		if (pom.msgModal().contains("El ciclo seleccionado para los siguientes"))
			pom.optionMsgModal("ACEPTAR");

		/// CERRAR LA VENTANA DE SIV ///
		ultimus.cerrarVentanaPortal(windowPega);

		/// Cerrar sesion en Ultimus ///
		addStep("Cerrar sesion en Ultimus");
		ultimus.cerrarSesionUltimus();
		u.hardWait(1);
		testCase.addScreenShotCurrentStep(u, "Sesion Cerrada");

		/// REVISAR ESTATUS ///

		
		addStep("Validar en la Base de Datos el estatus del Codigo de Barras");
		try {
			SQLResult queryResult;
			utils.sql.SQLUtil dbXXVA_SIV3 = new utils.sql.SQLUtil(GlobalVariables.DB_HOST_FCPVEQA2,
					GlobalVariables.DB_USER_FCPVEQA2, GlobalVariables.DB_PASSWORD_FCPVEQA2);
			String query_ItemStatus = "select item_rtk, item_transaccion_id, step_label, incidenteultimus_id from  XXVA_SIV3.xxva_item_incident_ultimus where item_RTK = '"
					+ cb + "' and create_date >= trunc(sysdate)-1";
			System.out.println(query_ItemStatus);

			queryResult = executeQuery(dbXXVA_SIV3, query_ItemStatus);
			testCase.addQueryEvidenceCurrentStep(queryResult);
			Boolean validacion = false;
			if (queryResult.getRowCount() > 0) 
				validacion = true;
			assertTrue(validacion, "No se encontro el codigo de Barras");

		} catch (SQLException e) {
			System.out.println(" -> " + e);
			throw new AssertionError("Fallo al ejecutar el query");
		}
		
		
		/**
		 * -----------------------------------------------------------------
		 * APROBACIONES
		 * ----------------------------------------------------------------- *
		 */

		/**
		 * -------------------- CATALOGOS --------------------- *
		 */
		/// LOGUEARSE CON USUARIO CATALOGO ///
		addStep("Iniciar sesion con usuario: " + usuarioCatalogo);
		ultimus.loginUltimus(usuarioCatalogo, "");
		testCase.addScreenShotCurrentStep(u, "Inicio de sesion en Ultimus_" + usuarioCatalogo);

		/// INICIAR Y ABRIR SIV ///
		addStep("Ingresar al portal de SIV");
		ultimus.openSivNet();
		ultimus.loadingUltimus();
		testCase.addScreenShotCurrentStep(u, "Inicio de sesion en SIV");

		/// SELECCIONAR EL CATEGORIA CORRESPONDIENTE ///
		addStep("Seleccionar la Categoria e ir a Altas");
		u.hardWait(4);
		inbox.seleccionarCategoriaTablaCatalogos(categoria);
		inbox.altasCatalogos();

		/// SELECCIONAR CB EN ALTAS CATALOGOS ///
		addStep("Seleccionar el codigo de barras y abrir Datos Basicos");
		u.hardWait(2);
		inbox.seleccionarItemInboxAltasCatalogos(cb);
		inbox.datosBasicosAltas();
		pom.waitLoadingModal();
		
		/// ACEPTAR DATOS BASICOS ///
		addStep("Modificar el PLU");
		String textModal;
		
		try {
			utils.sql.SQLUtil dbFCRMSQA = new utils.sql.SQLUtil(GlobalVariables.DB_HOST_RMS_MEX,
					GlobalVariables.DB_USER_RMS_MEX, GlobalVariables.DB_PASSWORD_RMS_MEX);
			utils.sql.SQLUtil dbFCMOMUAT = new utils.sql.SQLUtil(GlobalVariables.DB_HOST_FCMOMUAT,
					GlobalVariables.DB_USER_FCMOMUAT, GlobalVariables.DB_PASSWORD_FCMOMUAT);
			String queryRMS10 = "select subitem from("
					+ "  select item + 1 as subitem from RMS100.ITEM_MASTER where ITEM < 99999) subtabla"
					+ " where subitem not in("
					+ "  select item from RMS100.ITEM_MASTER)";
			pom.logM(queryRMS10);
			String queryRMS16;
			SQLResult queryResult1, queryResult2;
			queryResult1 = executeQuery(dbFCRMSQA, queryRMS10);
			
			Random r = new Random();
			int randomCol;
			do {
				band = false;
				randomCol = r.nextInt(queryResult1.getRowCount());
				plu = queryResult1.getData(randomCol, "subitem");
				// Validar que el PLU tampoco exista en RMS16
		        queryRMS16 = "select * from RMS.ITEM_MASTER where ITEM = " + plu;
		        pom.logM(queryRMS16);
		        queryResult2 = executeQuery(dbFCMOMUAT, queryRMS16);
		        if(queryResult2.getRowCount() > 0)
		        	band = true;

			} while (band);
			
			testCase.addTextEvidenceCurrentStep("PLU disponible: " + plu);
			datosBasicos.enterPLU(plu);
			
			do {
				textModal = pom.msgModal();
				if (textModal.contains("nuevo PLU")) {
					pom.optionMsgModal("OK");
					u.hardWait(1);
					pom.waitLoadingModal();
				}
			} while (textModal.isBlank());
			testCase.addScreenShotCurrentStep("PLU asignado");
			
		} catch(Exception e) {
			throw new AssertionError("Fallo al ejecutar el query para buscar un PLU " + e);
		}
		
		pom.logL("Aceptar Datos Basicos");
		addStep("Aceptar Datos Basicos");
		inbox.aceptarInfoBasicaCatalogos();
		do {
			textModal = pom.msgModal();
			if (textModal.contains("Aceptado exitoso")) {
				pom.optionMsgModal("OK");
				u.hardWait(1);
				pom.waitLoadingModal();
			} else if (textModal.contains("Debe seleccionar")) {
				testCase.addBoldTextEvidenceCurrentStep("FALTA INFORMACION");
				pom.optionMsgModal("OK");
				u.hardWait(1);
				pom.waitLoadingModal();
				datosBasicos.enterMarcaProveedor(marcaProveedor, udaMarcaProveedor);
				datosBasicos.enterMarcaArticulo(marcaArticulo, udaMarcaArticulo);
				inbox.aceptarInfoBasicaCatalogos();
				pom.optionMsgModal("OK");
			}
		} while (textModal.isBlank());

		/// SELECCIONAR CB EN ALTAS CATALOGOS ///
		addStep("Seleccionar el codigo de barras e ir a Fuente Abasto");
		u.hardWait(1);
		inbox.seleccionarItemInboxAltasCatalogos(cb);
		inbox.fuenteAbastoAltas();

		/// ACEPTAR FUENTE DE ABASTO ///
		addStep("Seleccionar todas las fuentes de abasto y aceptar");
		u.hardWait(1);
		inbox.checkTodosFuenteAbastoValidacion(true);
		inbox.aceptarFuenteAbastoValidacion();
		u.hardWait(1);
		inbox.aceptarComentarioFuenteAbastoValidacion();
		u.hardWait(1);
		inbox.regresarFuenteAbastoValidacion();

		/// SELECCIONAR CB EN ALTAS CATALOGOS ///
		addStep("Seleccionar CB y vaciar Inbox Altas");
		u.hardWait(1);
		inbox.seleccionarItemInboxAltasCatalogos(cb);
		inbox.vaciarInboxAltas();
		u.hardWait(2);
		pom.msgEjecucion();
		testCase.addScreenShotCurrentStep(u, "Vaciar Inbox Altas");

		/// CERRAR LA VENTANA DE SIV ///
		ultimus.cerrarVentanaPortal(windowPega);

		/// Cerrar sesion en Ultimus ///
		addStep("Cerrar sesion en Ultimus");
		ultimus.cerrarSesionUltimus();
		u.hardWait(1);
		testCase.addScreenShotCurrentStep(u, "Sesion Cerrada");

		addStep("Validar en la Base de Datos el estatus del Codigo de Barras");
		try {
			SQLResult queryResult;
			utils.sql.SQLUtil dbXXVA_SIV3 = new utils.sql.SQLUtil(GlobalVariables.DB_HOST_FCPVEQA2,
					GlobalVariables.DB_USER_FCPVEQA2, GlobalVariables.DB_PASSWORD_FCPVEQA2);
			String query_ItemStatus = "select item_rtk, item_transaccion_id, step_label, incidenteultimus_id from  XXVA_SIV3.xxva_item_incident_ultimus where item_RTK = '"
					+ cb + "' and create_date >= trunc(sysdate)-1";
			System.out.println(query_ItemStatus);

			queryResult = executeQuery(dbXXVA_SIV3, query_ItemStatus);
			testCase.addQueryEvidenceCurrentStep(queryResult);
			Boolean validacion = false;
			if (queryResult.getRowCount() > 0) 
				validacion = true;
			assertTrue(validacion, "No se encontro el codigo de Barras");

		} catch (SQLException e) {
			System.out.println(" -> " + e);
			throw new AssertionError("Fallo al ejecutar el query");
		}

		/// FIN DE FLUJO ///

	}

	@Override
	public void beforeTest() {
		// TODO Auto-generated method stub

	}

	@Override
	public String setPrerequisites() {
		// TODO Auto-generated method stub
		return "Ingresar los datos en el data-provider correspondiente";
	}

	@Override
	public String setTestDescription() {
		// TODO Auto-generated method stub
		return "Plaza: Alta de CB Nuevo";
	}

	@Override
	public String setTestDesigner() {
		// TODO Auto-generated method stub
		return "Angel Del Rio";
	}

	@Override
	public String setTestFullName() {
		// TODO Auto-generated method stub
		return "ATC_003_Category_Alta_PLU_AprobarCatalogos_Planogramador_EHIA";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}

	@AfterMethod
	public void afterMethod(ITestResult result) {
		MetodoGeneral.obtenerTiempoDeEjecucion(result, testCase);
		u.getDriver().quit();
	}

}
