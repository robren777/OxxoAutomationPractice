package WEB.updateNomina.scripts.cnt.mex;

import static org.testng.Assert.assertTrue;

import java.util.HashMap;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import WEB.cnt.om.Datos_Generales;
import WEB.cnt.om.Formato_y_Entorno;
import WEB.cnt.om.Login;
import WEB.updateNomina.om.CNT;
import WEB.util.GlobalVariables;
import modelo.BaseExecution;
import utils.password.PasswordUtil;
import utils.selenium.ChromeTest;
import utils.selenium.SeleniumUtil;
import utils.sql.SQLUtil;

public class ATC_FT_001_Captura_de_Datos_de_Tienda_en_CNT_F102_Oxxo_GAS extends BaseExecution {

	SeleniumUtil u;

	@Test(dataProvider = "data-provider")

	public void ATC_FT_001_Captura_de_Datos_de_Tienda_en_CNT_F102_Oxxo_GAS_test(HashMap<String, String> data) throws Exception {
		/*
		 * Utilerias
		 ******************************************************************************************/
		SQLUtil dbFCIAS = new SQLUtil(GlobalVariables.DB_HOST_FCDW2QA_USERAUTO, GlobalVariables.DB_USER_FCDW2QA_USERAUTO,
				GlobalVariables.DB_PASSWORD_FCDW2QA_USERAUTO);

		/*
		 * Variables
		 ******************************************************************************************/
		String user = data.get("User"); //ATC 002
		String password = PasswordUtil.decryptPassword(data.get("Password")); //ATC 002
		String URL = data.get("Url"); //ATC 002
		String PlazaE = data.get("PlazaE");//ATC 002
		String Distrito = data.get("Distrito");//ATC 002
		String Tienda = data.get("Tienda");//ATC 002

		String plaza= data.get("plaza"); //ATC 003
		String NumTelefono= data.get("Telefono"); //ATC 003
		String TelefonoDisponible = data.get("TelefonoDisponible"); //ATC 003
		String Alternativa= data.get("Alternativa"); //ATC 003
		String p_especifique= data.get("Especificar"); //ATC 003

		String Asesor = data.get("Asesor"); //ATC 004

		String cheque = data.get("cheque"); //ATC 005
		String dolares = data.get("dolares"); //ATC 005
		String efectivo = data.get("efectivo"); //ATC 005
		String TDD1 = data.get("TDD1"); //ATC 005
		String TDC1 = data.get("TDC1"); //ATC 005
		String vales = data.get("vales"); //ATC 005

		String Tamano = data.get("Tamano"); //ATC 006
		String Segmento1 = data.get("Segmento"); //ATC 006
		String Ubicacion1 = data.get("Ubicacion"); //ATC 006
		String diferenciador = data.get("Diferenciador"); //ATC 006
		String TiendaE = data.get("TiendaEspejo"); //ATC 006
		String TiendaSis = data.get("TiendaHermana"); //ATC 006
		String Porcentaje = data.get("Porcentaje"); //ATC 006
		String NivelSocioe = data.get("NivelSocioEconomico"); //ATC 006
		String ZonaEco = data.get("ZonaEconomica"); //ATC 006
		String Vial = data.get("Vialidad"); //ATC 006
		String UbicacionEntorno= data.get("UbicacionEntorno"); //ATC 006
		//ATC 007
		String Metros = data.get("Metros");
		String Cajon = data.get("Cajon");
		String PlazaP = data.get("PlazaP");
		String TiendaP = data.get("TiendaP");
		String Correo = data.get("Correo");
		String Fecha = data.get("Fecha");
		String Opera = data.get("Opera");
		String SL = data.get("SL");
		String SM = data.get("SM");
		String SMM = data.get("SMM");
		String SJ = data.get("SJ");
		String SV = data.get("SV");
		String SS = data.get("SS");
		String SD = data.get("SD");
		String Hora_Apertura= data.get("Hora_Apertura");
		String Hora_Cierre= data.get("Hora_Cierre");
		String Predial= data.get("Predial");
		String Cer_Propio= data.get("Cer_Propio");
		String Sindicato= data.get("Sindicato");
		String VTA_VINOS= data.get("VTA_VINOS");
		String  lid_condicion_propiedad= data.get("lid_condicion_propiedad");
		String NOMBRE_ARRENDADOR= data.get("NOMBRE_ARRENDADOR");
		String LID_BANCO_CUENTA_ARRENDADOR= data.get("LID_BANCO_CUENTA_ARRENDADOR");
		String NUM_CUENTA_ARRENDADOR=data.get("NUM_CUENTA_ARRENDADOR");
		String monto_renta=data.get("monto_renta");
		String monto_renta_por_mts2=data.get("monto_renta_por_mts2");
		String lid_tipo_incremento_renta=data.get("lid_tipo_incremento_renta");
		String plazo_contrato=data.get("plazo_contrato");
		String fecha_inicio_contrato=data.get("fecha_inicio_contrato");
		String fecha_fin_contrato=data.get("fecha_fin_contrato");
		String LAST_UPDATE_BY=data.get("LAST_UPDATE_BY");
		String AREA_DEL_TERRENO=data.get("AREA_DEL_TERRENO");

		u = new SeleniumUtil(new ChromeTest(), true);
		Login pa = new Login(u, testCase);
		Datos_Generales po = new Datos_Generales(u, testCase);
		//Metodos para CNT creados para el proyecto Update Nomina
		CNT cntUpdNom = new CNT(u,testCase);

		// PASO 1 ATC 002
		addStep("Ingresar al portal " + URL + " con el <Usuario>" + user + ""); // viene en
		
		u.get(data.get("Url"));
		u.hardWait(3);
		System.out.println(password);
		pa.logginInventarioEspacios(user, password);

		// PASO 2
		addStep("Seleccionar la Plaza y dar clic en el bot�n Continuar.");
		pa.clickSeleccionarPlaza(PlazaE);

		// PASO 3
		addStep("Seleccionar el Distrito y Tienda; a continuaci�n dar clic en el bot�n Continuar");
		pa.clickSeleccionarDistritoTienda(Distrito, Tienda);
		//pa.ScreenVentanaEmergente();

		// PASO 4 ATC 003
		/*
		addStep("Dar Click en Link Datos Generales"); // PENDIENTE
		po.DatosGen();

		// PASO 5
		addStep("Capturar el Código Postal y dar clic en la opción Buscar, Para obtener un Código valido ejecutar el siguiente query en la BD FCIASQA en el Esquema TIENDAS:SELECT ID_PUNTO,CODIGO_POSTAL FROM XXCNT_BIT_GIS_INFO WHERE ID_PUNTO NOT IN (SELECT T_TIENDAS.ID_PUNTO FROM T_TIENDAS WHERE T_TIENDAS.CR_SUPERIOR ='CRPLAZA' AND ID_PUNTO IS NOT NULL);");
		//po.capturaDatosGen("66400");
		String query1 = "SELECT * FROM (SELECT CODIGO_POSTAL, ID_MUNICIPIO FROM TIENDAS.XXCNT_BIT_GIS_INFO " +
				"WHERE ID_PUNTO NOT IN (SELECT T_TIENDAS.ID_PUNTO FROM T_TIENDAS WHERE " +
				"T_TIENDAS.CR_SUPERIOR = 'CRPLAZA' AND ID_PUNTO IS NOT NULL) " +
				"AND CR_PLAZA = '" + plaza + "' AND LENGTH(CODIGO_POSTAL) = 5) " +
				"WHERE ROWNUM = 1";
		System.out.println(query1);
		System.out.println("plaza= "+ plaza);
		SQLResult queryResult = executeQuery(dbFCIAS, query1);

		boolean empty = queryResult.isEmpty();
		String CP = "", ID_MUNICIPIO = "";
		System.out.println(CP);
		System.out.println(ID_MUNICIPIO);
		if (empty) {
			testCase.addTextEvidenceCurrentStep("No se muestran resultados de la consulta");
		} else {
			CP = queryResult.getData(0, "CODIGO_POSTAL");
			ID_MUNICIPIO = queryResult.getData(0, "ID_MUNICIPIO");
		}
		testCase.addQueryEvidenceCurrentStep(queryResult); // Agrega la consulta tal cual que se hace, siempre y cuando
		// solo use una sola consulta
		assertFalse(empty, "No hay datos en la consulta");

		po.capturaDatosGen(CP);

		cntUpdNom.capturarColonia(); //El metodo siempre escoge la segunda opcion


		System.out.println("termina paso 5");

		// PASO 6
		addStep("Seleccionar un Id Punto");
		po.IdPunto();
		//System.out.print(user);
		System.out.println("termina paso 6: Seleccionar un id punto");

		// PASO 7
		addStep("Capturar la Clave de larga distancia");
		po.ClaveLargaDistancia(ID_MUNICIPIO);
		System.out.println("termina paso 7: capturar clave de larga distancia");

		// PASO 8
		addStep("Capturar el Número telefónico.");
		po.NTel(NumTelefono, TelefonoDisponible.toUpperCase(), Alternativa, p_especifique);
		System.out.println("termina paso 8: capturar numero telefonico");

		// PASO 9
		addStep("Seleccionar el Distrito.");
		po.Distrito(Distrito);
		System.out.println("termina paso 9: selecciornar distrito");

		// PASO 10
		addStep("Dar clic en el botón Actualizar.");
		testCase.addScreenShotCurrentStep("Se muestran datos a capturar");
		po.clickBotonActualizar();
		testCase.addScreenShotCurrentStep("Se actualizan Datos Generales");
		System.out.println("termina paso 10: clic al boton actualizar");
		*/

		// PASO 11 ATC 004 ------------------------------------------------------------------------------------------------------------------------------
		/*
		Datos_de_Operacion datosOp = new Datos_de_Operacion(u, testCase);
		addStep("Dar Click en Link Datos de Operacion"); // PENDIENTE
		datosOp.DatoOp();
		System.out.println("termina paso 11");

		// PASO 12
		addStep("Seleccionar una opción en el campo Características de operación de la tienda");
		//datosOp.capturaDatosOp(Asesor); utilizar mejor:
		datosOp.editarAsesor();
		//datosOp.BANCOMEX();
		System.out.println("termina paso 12");

		// PASO 13
		addStep("Dar clic en le boton Actualizar");
		datosOp.clickBotonActualizar();
		boolean msj =datosOp.validacion();
		assertTrue(msj, "No se presento pantalla de cambios guardados");
		System.out.println("termina paso 13");
		*/

		// PASO 14 ATC 005 -------------------------------------------------------------------------------------------------------------------------
		/*
		addStep("Dar clic en el link Permisos y Contratos"); // PENDIENTE
		String queryPermisoYContratos = " select * from (SELECT ID_PUNTO,CODIGO_POSTAL , ID_MUNICIPIO " + "FROM TIENDAS.XXCNT_BIT_GIS_INFO  "
				+ "WHERE ID_PUNTO NOT IN " + "(SELECT T_TIENDAS.ID_PUNTO " + "FROM T_TIENDAS "
				+ "WHERE T_TIENDAS.CR_SUPERIOR ='CRPLAZA' AND ID_PUNTO IS NOT NULL))where rownum = 1";
		Permisos_y_Contratos PermYCon = new Permisos_y_Contratos(u, testCase);
		PermYCon.PermCon();

		// PASO 15
		addStep("Seleccionar al menos una Forma de Pago.");
		PermYCon.EligecheckBox(cheque,dolares,"NO",TDD1, TDC1, vales);

		//PASO 16
		addStep("Dar clic en el botón Actualizar.");
		//PermYCon.clickBotonActualizar();
		//boolean msjPYC =PermYCon.validacion();
		//assertTrue(msjPYC, "No se presento pantalla de cambios guardados");
		cntUpdNom.clickBotonActualizar();
		*/
		//PASO 17 ATC 006 -------------------------------------------------------------------------------------------------------------------
		addStep("Dar clic en el link Formato y Entorno.");
		Formato_y_Entorno fe = new Formato_y_Entorno(u, testCase);
		fe.FormatoyEntorno();

		//PASO 18
		addStep("Seleccionar una opción en el campo Tamaño de tienda.");
		fe.TamanoTienda(Tamano);

		//PASO 19
		addStep("Seleccionar una opción en el campo Segmento al que pertenece.");
		fe.Segmento(Segmento1);

		//PASO 20
		addStep("Seleccionar una opción en el campo Tipo ubicación de tienda.");
		fe.Ubicacion(Ubicacion1);
		fe.Diferenciador(diferenciador);

		//PASO 21
		addStep("Seleccionar una opción en el campo Tienda espejo.");
		fe.TiendaEspejo(TiendaE);

		addStep("Seleccionar una opción en el campo Tienda hermana.");
		fe.TiendaSister(TiendaSis);

		addStep("Capturar el Porcentaje de ajuste.");
		fe.Proce(Porcentaje);

		addStep("Seleccionar una opción en el campo Nivel socioeconómico.");
		fe.NivelSocio(NivelSocioe);

		addStep("Seleccionar una opción en el campo Zona económica.");
		fe.ZonaE(ZonaEco);

		addStep("Dar clic en el botón Actualizar.");
		fe.clickBotonActualizar();

		addStep("Seleccionar en el Link Entorno.");
		fe.BtnEntorno();

		addStep("Seleccionar una opción en el campo Tipo de vialidad.");
		fe.Vialidad(Vial);

		addStep("Seleccionar una opción en el campo Referencia de Ubicación.");
		fe.UbicacionEntorno(UbicacionEntorno);

		addStep("Dar clic en el botón Actualizar.");
		fe.clickBotonActualizarEntorno();
		boolean msjFyE =fe.validacion();
		assertTrue(msjFyE, "No se presento pantalla de cambios guardados");
// ATC 007---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
		String updateTiendas = "update  tiendas.t_tiendas set  correo_electronico='" + Correo + "' where  cr_superior='" + PlazaP + "' and cr_tienda in '" + TiendaP + "'  ";
		String Querytienda = "select id_tienda,nombre, cr_superior, cr_tienda,telefono, correo_electronico,ageb,status from tiendas.t_tiendas where cr_superior='"
				+ PlazaP + "' and cr_tienda in '" + TiendaP + "' ";


		String updateoperacion = "update tiendas.t_operacion "
				+ "set fecha_apertura = to_date('"+Fecha+"' ,'DD-MON-RR HH.MI.SS'), "
				+ "Lid_modo_operacion = '"+Opera+"' ,\r\n"
				+ "SERVICIO_LUNES = '" +SL+"',SERVICIO_MARTES = '" +SM+"',SERVICIO_MIERCOLES = '" +SMM+"',SERVICIO_JUEVES = '" +SJ+"',SERVICIO_VIERNES = '" +SV+"',SERVICIO_SABADO = '" +SS+"',SERVICIO_DOMINGO = '" +SD+"',FECHA_CIERRE = '',LID_MOTIVO_CIERRE ='',HORA_APERTURA='" +Hora_Apertura+"',\r\n"
				+ "HORA_CIERRE='" +Hora_Cierre+"'"
				+ " where id_tienda "
				+ "IN (SELECT ID_TIENDA FROM tiendas.t_tiendas WHERE CR_TIENDA IN '" + TiendaP + "' " + "AND CR_SUPERIOR IN '"
				+ PlazaP + "')";

		String Queryoperacion = "select id_tienda,fecha_apertura,HORA_CIERRE,Lid_modo_operacion from tiendas.t_operacion where id_tienda "
				+ "IN (SELECT ID_TIENDA FROM tiendas.t_tiendas WHERE CR_TIENDA IN '" + TiendaP + "' " + "AND CR_SUPERIOR IN '"
				+ PlazaP + "')";
		String Queryoperacion1 = "select servicio_lunes, servicio_martes, servicio_miercoles, servicio_jueves, servicio_viernes,servicio_sabado,servicio_domingo  from tiendas.t_operacion where id_tienda "
				+ "IN (SELECT ID_TIENDA FROM tiendas.t_tiendas WHERE CR_TIENDA IN '" + TiendaP + "' " + "AND CR_SUPERIOR IN '"
				+ PlazaP + "') ";

		String updatePlanogramacion="UPDATE T_Planogramacion "
				+ "SET (LID_TIPO_IMAGEN,LID_GEOMETRIA,AREA_VENTAS,AREA_BODEGA,AREA_TORTILLERIA,AREA_CARNICERIA,TIENE_CUARTO_FRIO,LARGO_CUARTO_FRIO, "
				+ "NUM_PUERTAS_CUARTO_FRIO,ALTURA_PUERTAS_CUARTO_FRIO,NUM_PARRILLAS_TOTALES,TIENE_CONGELADOR_CERVEZA,TIENE_CONSERVADOR_HIELO, "
				+ "TIENE_ESTACIONAMIENTO,AREA_ESTACIONAMIENTO,PERIMETRO_VENTA_INTERIOR,AREA_TOTAL_LOCAL,ALTURA_PISO_CIELO_FALSO,ALTURA_CENEFA_CIELO_FALSO, "
				+ "ALTURA_MAXIMA_TIENDA,ALTURA_LATERAL_TIENDA,LID_TIPO_TECHO,LID_TIPO_PISO,TIENE_EXCLUSA,NUM_METROS_LINEALES_RACK,NUM_NIVELES_RACK, "
				+ "NUM_CHECKOUT_DERECHOS,NUM_CHECKOUT_IZQUIERDOS,MUEBLES_NUEVA_IMAGEN,TIENE_TOLDOS_LUMINOSOS,TIENE_ANUNCIO_POSTE,TIENE_PORTA_BANNER_EXT)= "
				+ "(select LID_TIPO_IMAGEN,LID_GEOMETRIA,AREA_VENTAS,AREA_BODEGA,AREA_TORTILLERIA,AREA_CARNICERIA,TIENE_CUARTO_FRIO,LARGO_CUARTO_FRIO, "
				+ "NUM_PUERTAS_CUARTO_FRIO,ALTURA_PUERTAS_CUARTO_FRIO,NUM_PARRILLAS_TOTALES,TIENE_CONGELADOR_CERVEZA,TIENE_CONSERVADOR_HIELO, "
				+ "TIENE_ESTACIONAMIENTO,AREA_ESTACIONAMIENTO,PERIMETRO_VENTA_INTERIOR,AREA_TOTAL_LOCAL,ALTURA_PISO_CIELO_FALSO,ALTURA_CENEFA_CIELO_FALSO, "
				+ "ALTURA_MAXIMA_TIENDA,ALTURA_LATERAL_TIENDA,LID_TIPO_TECHO,LID_TIPO_PISO,TIENE_EXCLUSA,NUM_METROS_LINEALES_RACK,NUM_NIVELES_RACK, "
				+ "NUM_CHECKOUT_DERECHOS,NUM_CHECKOUT_IZQUIERDOS,MUEBLES_NUEVA_IMAGEN,TIENE_TOLDOS_LUMINOSOS,TIENE_ANUNCIO_POSTE,TIENE_PORTA_BANNER_EXT "
				+ "from t_planogramacion "
				+ "where id_tienda in (select id_tienda from t_tiendas "
				+ "where cr_superior='10CHI' and cr_tienda ='50REM')) "
				+ "WHERE id_tienda in (select id_tienda from t_tiendas "
				+ "where cr_superior= '" + PlazaP + "' and cr_tienda in ( '" + TiendaP +"'))";
		String updatelegal = "UPDATE tiendas.T_LEGALES SET NUM_CUENTA_PREDIAL='"+Predial+"',PEM_VTA_CER_PROPIO='" +Cer_Propio+"',SINDICATO_AL_QUE_PERTENECE='"+Sindicato+"',PEM_VTA_VINOS='"+VTA_VINOS+"'"
				+ " where id_tienda IN (SELECT ID_TIENDA FROM tiendas.t_tiendas WHERE CR_TIENDA IN '" + TiendaP + "' " + "AND CR_SUPERIOR IN '"+PlazaP+"')";
		String QueryLEGAL = "select ID_TIENDA, NUM_CUENTA_PREDIAL,sindicato_al_que_pertenece,ultima_fecha_recoleccion,staus,pem_vta_vinos from TIENDAS.T_LEGALES where id_tienda "
				+ "IN (SELECT ID_TIENDA FROM tiendas.t_tiendas WHERE CR_TIENDA IN '" + TiendaP + "' " + "AND CR_SUPERIOR IN '"
				+ PlazaP + "')";
		String QueryLEGAL1 = "select ultima_fecha_recoleccion,staus,pem_vta_vinos from TIENDAS.T_LEGALES where id_tienda "
				+ "IN (SELECT ID_TIENDA FROM tiendas.t_tiendas WHERE CR_TIENDA IN '" + TiendaP + "' " + "AND CR_SUPERIOR IN '"
				+ PlazaP + "') ";


		String insertCondiciones = "INSERT INTO t_condiciones_propiedad (id_tienda, lid_condicion_propiedad, NOMBRE_ARRENDADOR,LID_BANCO_CUENTA_ARRENDADOR,NUM_CUENTA_ARRENDADOR,monto_renta,monto_renta_por_mts2,lid_tipo_incremento_renta,plazo_contrato,fecha_inicio_contrato,fecha_fin_contrato,LAST_UPDATE_BY,AREA_DEL_TERRENO,LAST_UPDATE_DATE) "
				+ "VALUES ((select id_tienda FROM tiendas.T_TIENDAS WHERE STATUS = 'S_NUEVA' and id_tienda not in "
				+ "(select id_tienda from tiendas.t_condiciones_propiedad) "
				+ "and CR_SUPERIOR || CR_TIENDA IN  ('"+ PlazaP + "'|| '" + TiendaP + "')) "
				+ ",'" + lid_condicion_propiedad+"','" + NOMBRE_ARRENDADOR + "','" +LID_BANCO_CUENTA_ARRENDADOR+ "','" + NUM_CUENTA_ARRENDADOR+ "', '" + monto_renta + "','" + monto_renta_por_mts2 + "','" +lid_tipo_incremento_renta+"','" +plazo_contrato+ "','"+fecha_inicio_contrato+"', '" +fecha_fin_contrato+"', '" +LAST_UPDATE_BY+"','" +AREA_DEL_TERRENO+"',SYSDATE) "
				+ " ";
		String Condiciones = " select id_tienda,lid_condicion_propiedad,nombre_arrendador, lid_banco_cuenta_arrendador,num_cuenta_arrendador from tiendas.t_condiciones_propiedad where id_tienda "
				+ "IN (SELECT ID_TIENDA FROM tiendas.t_tiendas WHERE CR_TIENDA IN '" + TiendaP + "' " + "AND CR_SUPERIOR IN '"
				+ PlazaP + "') ";
		String Condiciones1 = "select lid_regimen_fiscal_arrendador,monto_renta,monto_renta_mts2_terreno,lid_tipo_incremento_renta,plazo_contrato from tiendas.t_condiciones_propiedad where id_tienda "
				+ "IN (SELECT ID_TIENDA FROM tiendas.t_tiendas WHERE CR_TIENDA IN '" + TiendaP + "' " + "AND CR_SUPERIOR IN '"
				+ PlazaP + "') ";
		String Condiciones2 = "select fecha_fin_contrato,fecha_fin_contrato,rentas_anticipadas,num_meses_de_gracia,opcion_de_compra,derecho_tanto_preferencia from tiendas.t_condiciones_propiedad where id_tienda "
				+ "IN (SELECT ID_TIENDA FROM tiendas.t_tiendas WHERE CR_TIENDA IN '" + TiendaP + "' " + "AND CR_SUPERIOR IN '"
				+ PlazaP + "') ";
		String Condiciones3 = "select clausula_fallec_disolucion,credito_comercial,subarrendamiento_dentro_tienda,monto_subarrendamiento,marca_concesion,giro_concesion,locales_exteriores_rentados from tiendas.t_condiciones_propiedad where id_tienda "
				+ "IN (SELECT ID_TIENDA FROM tiendas.t_tiendas WHERE CR_TIENDA IN '" + TiendaP + "' " + "AND CR_SUPERIOR IN '"
				+ PlazaP + "') ";
		String Condiciones4 = "select se_encuentra_rentado,area_del_terreno,monto_renta_terreno,ultima_fecha_recoleccion,status,last_update_by,last_update_date from tiendas.t_condiciones_propiedad where id_tienda "
				+ "IN (SELECT ID_TIENDA FROM tiendas.t_tiendas WHERE CR_TIENDA IN '" + TiendaP + "' " + "AND CR_SUPERIOR IN '"
				+ PlazaP + "') ";



		String planogramacion = "select  id_tienda,lid_tipo_imagen, lid_geometria,area_ventas, area_bodega,area_tortilleria,area_carniceria, tiene_cuarto_frio from tiendas.t_planogramacion where id_tienda "
				+ "IN (SELECT ID_TIENDA FROM tiendas.t_tiendas WHERE CR_TIENDA IN '" + TiendaP + "' " + "AND CR_SUPERIOR IN '"
				+ PlazaP + "') ";
		String planogramacion1 = "select  largo_cuarto_frio, ancho_cuarto_frio, area_cuarto_frio, num_puertas_cuarto_frio, altura_puertas_cuarto_frio, num_parrillas_totales,tiene_congelador_cerveza from tiendas.t_planogramacion  where id_tienda "
				+ "IN (SELECT ID_TIENDA FROM tiendas.t_tiendas WHERE CR_TIENDA IN '" + TiendaP + "' " + "AND CR_SUPERIOR IN '"
				+ PlazaP + "') ";
		String planogramacion2 = "select num_puertas_congelador_cerveza, tiene_conservador_hielo, tiene_estacionamiento,area_estacionamiento, num_cajones_estacionamiento, area_total_terreno from tiendas.t_planogramacion  where id_tienda "
				+ "IN (SELECT ID_TIENDA FROM tiendas.t_tiendas WHERE CR_TIENDA IN '" + TiendaP + "' " + "AND CR_SUPERIOR IN '"
				+ PlazaP + "') ";
		String planogramacion3 = "select AREA_TOTAL_LOCAL,ALTURA_PISO_CIELO_FALSO,ALTURA_CENEFA_CIELO_FALSO,\r\n"
				+ "ALTURA_MAXIMA_TIENDA,ALTURA_LATERAL_TIENDA,LID_TIPO_TECHO from tiendas.t_planogramacion  where id_tienda "
				+ "IN (SELECT ID_TIENDA FROM tiendas.t_tiendas WHERE CR_TIENDA IN '" + TiendaP + "' " + "AND CR_SUPERIOR IN '"
				+ PlazaP + "') ";
		String planogramacion4 = "select LID_TIPO_PISO,TIENE_EXCLUSA,NUM_METROS_LINEALES_RACK,NUM_NIVELES_RACK from tiendas.t_planogramacion where id_tienda "
				+ "IN (SELECT ID_TIENDA FROM tiendas.t_tiendas WHERE CR_TIENDA IN '" + TiendaP + "' " + "AND CR_SUPERIOR IN '"
				+ PlazaP + "') ";
		String planogramacion5 = "select NUM_CHECKOUT_DERECHOS,NUM_CHECKOUT_IZQUIERDOS,MUEBLES_NUEVA_IMAGEN,TIENE_TOLDOS_LUMINOSOS,TIENE_ANUNCIO_POSTE,TIENE_PORTA_BANNER_EXT from tiendas.t_planogramacion where id_tienda "
				+ "IN (SELECT ID_TIENDA FROM tiendas.t_tiendas WHERE CR_TIENDA IN '" + TiendaP + "' " + "AND CR_SUPERIOR IN '"
				+ PlazaP + "') ";

		/*
		Planogramacion pl = new Planogramacion(u, testCase);
		addStep("Dar clic en el link Planogramación.");
		pl.Plano();
		pl.conge();
//		//		                           PASO 22
		addStep("Capturar el Área del estacionamiento.");
		pl.Estacionamiento(Metros);
//		//		                           PASO 23
		addStep("Seleccionar una opción en el campo Estacionamiento.");
		pl.CajonesEstacionamiento(Cajon);
		pl.poste();
		pl.banner();
//		//		                           PASO 24
		addStep("Dar clic en el botón Actualizar.");
		pl.clickBotonActualizar();
		//		                           PASO 25
		addStep("Para llenar el resto de los campos de las secciones se deberá ejecutar el query (QueryLLenadoPlanogramación) adjunto en este paso en la BD FCIASQA en el Esquema TIENDAS.");
//-----------------------------------CONSULTA TIENDAS
		testCase.addBoldTextEvidenceCurrentStep("Update y consulta de tiendas");

		System.out.print("consulta" + updateTiendas);
		testCase.addTextEvidenceCurrentStep("Query:"+updateTiendas);
		Statement stmt = dbFCIAS.getConn().createStatement();

		stmt.executeUpdate(updateTiendas);



		SQLResult ExecuteQueryTIENDA = executeQuery(dbFCIAS, Querytienda);
		System.out.print("consulta" + Querytienda);
		boolean StatusQueryTIENDA = ExecuteQueryTIENDA.isEmpty();

		if (!StatusQueryTIENDA) {
			testCase.addTextEvidenceCurrentStep("Se encontro correctamente la tabla");
			testCase.addQueryEvidenceCurrentStep(ExecuteQueryTIENDA);
//			CAMBIO = ExecuteQuerybank.getData(0, "DAR_CAMBIO");
		} else {
			testCase.addTextEvidenceCurrentStep("No se encontro ninguna tabla ");
		}
//---------------------------------------CONSULTA OPERATIVA


		testCase.addBoldTextEvidenceCurrentStep("Update y consultas de operacion");
		System.out.print("consulta" + updateoperacion);
		testCase.addTextEvidenceCurrentStep("Query:"+updateoperacion);
		Statement stmt1 = dbFCIAS.getConn().createStatement();

		stmt1.executeUpdate(updateoperacion);






		SQLResult ExecuteQueryop = executeQuery(dbFCIAS, Queryoperacion);
		System.out.print("consulta" + Queryoperacion);
		boolean StatusQueryop = ExecuteQueryop.isEmpty();

		if (!StatusQueryop) {
			testCase.addTextEvidenceCurrentStep("Se encontro correctamente la tabla");
			testCase.addQueryEvidenceCurrentStep(ExecuteQueryop);
//			CAMBIO = ExecuteQuerybank.getData(0, "DAR_CAMBIO");
		} else {
			testCase.addTextEvidenceCurrentStep("No se encontro ninguna tabla ");
		}
		System.out.print("consulta" + Queryoperacion1);
		SQLResult ExecuteQueryop1 = executeQuery(dbFCIAS, Queryoperacion1);
		System.out.print("consulta" + Queryoperacion1);
		boolean StatusQueryop1 = ExecuteQueryop1.isEmpty();

		if (!StatusQueryop1) {
			testCase.addTextEvidenceCurrentStep("Se encontro correctamente la tabla");
			testCase.addQueryEvidenceCurrentStep(ExecuteQueryop1);
//			CAMBIO = ExecuteQuerybank.getData(0, "DAR_CAMBIO");
		} else {
			testCase.addTextEvidenceCurrentStep("No se encontro ninguna tabla ");
		}
//--------------------------------------------CONSULTA LEGAL
		testCase.addBoldTextEvidenceCurrentStep("Update y consulta de legal");
		System.out.print("consulta" + updatelegal);
		testCase.addTextEvidenceCurrentStep("Query:"+updatelegal);
		Statement stmt2 = dbFCIAS.getConn().createStatement();

		stmt2.executeUpdate(updatelegal);




		SQLResult ExecuteQueryLEGAL = executeQuery(dbFCIAS, QueryLEGAL);
		System.out.print("consulta" + QueryLEGAL);
		boolean StatusQueryLEGAL = ExecuteQueryLEGAL.isEmpty();

		if (!StatusQueryLEGAL) {
			testCase.addTextEvidenceCurrentStep("Se encontro correctamente la tabla");
			testCase.addQueryEvidenceCurrentStep(ExecuteQueryLEGAL);
//			CAMBIO = ExecuteQuerybank.getData(0, "DAR_CAMBIO");
		} else {
			testCase.addTextEvidenceCurrentStep("No se encontro ninguna tabla ");
		}
		SQLResult ExecuteQueryLEGAL1 = executeQuery(dbFCIAS, QueryLEGAL1);
		System.out.print("consulta" + QueryLEGAL1);
		boolean StatusQueryLEGAL1 = ExecuteQueryLEGAL1.isEmpty();

		if (!StatusQueryLEGAL1) {
			testCase.addTextEvidenceCurrentStep("Se encontro correctamente la tabla");
			testCase.addQueryEvidenceCurrentStep(ExecuteQueryLEGAL1);
//			CAMBIO = ExecuteQuerybank.getData(0, "DAR_CAMBIO");
		} else {
			testCase.addTextEvidenceCurrentStep("No se encontro ninguna tabla ");
		}
//----------------------------------------------CONSULTA condiciones
		testCase.addBoldTextEvidenceCurrentStep("Insert y consulta de Condiciones");
		System.out.print("consulta" + insertCondiciones);
		testCase.addTextEvidenceCurrentStep("Query:"+insertCondiciones);
		Statement stmtI = dbFCIAS.getConn().createStatement();

		stmtI.executeUpdate(insertCondiciones);
//
		Thread.sleep(5000);
//		SQLResult ExecuteQuerycondiciones0 = executeQuery(dbFCIAS, Condiciones);
		System.out.print("consulta" + Condiciones);
		//boolean StatusQuerycondiciones0 = ExecuteQuerycondiciones0.isEmpty();
		//	assertTrue(StatusQuerycondiciones0 , "Ya se encuentra insertada la informacion de condiciones");


		SQLResult ExecuteQuerycondiciones = executeQuery(dbFCIAS, Condiciones);
		System.out.print("consulta" + Condiciones);
		boolean StatusQuerycondiciones = ExecuteQuerycondiciones.isEmpty();

		if (!StatusQuerycondiciones) {
			testCase.addTextEvidenceCurrentStep("Se encontro correctamente la tabla");
			testCase.addQueryEvidenceCurrentStep(ExecuteQuerycondiciones);
//			CAMBIO = ExecuteQuerybank.getData(0, "DAR_CAMBIO");
		} else {
			testCase.addTextEvidenceCurrentStep("No se encontro ninguna tabla ");
		}
		SQLResult ExecuteQuerycondiciones1 = executeQuery(dbFCIAS, Condiciones1);
		System.out.print("consulta" + Condiciones1);
		boolean StatusQuerycondiciones1 = ExecuteQuerycondiciones1.isEmpty();

		if (!StatusQuerycondiciones1) {
			testCase.addTextEvidenceCurrentStep("Se encontro correctamente la tabla");
			testCase.addQueryEvidenceCurrentStep(ExecuteQuerycondiciones1);
//			CAMBIO = ExecuteQuerybank.getData(0, "DAR_CAMBIO");
		} else {
			testCase.addTextEvidenceCurrentStep("No se encontro ninguna tabla ");
		}
		SQLResult ExecuteQuerycondiciones2 = executeQuery(dbFCIAS, Condiciones2);
		System.out.print("consulta" + Condiciones2);
		boolean StatusQuerycondiciones2 = ExecuteQuerycondiciones2.isEmpty();

		if (!StatusQuerycondiciones2) {
			testCase.addTextEvidenceCurrentStep("Se encontro correctamente la tabla");
			testCase.addQueryEvidenceCurrentStep(ExecuteQuerycondiciones2);
//			CAMBIO = ExecuteQuerybank.getData(0, "DAR_CAMBIO");
		} else {
			testCase.addTextEvidenceCurrentStep("No se encontro ninguna tabla ");
		}
		SQLResult ExecuteQuerycondiciones3 = executeQuery(dbFCIAS, Condiciones3);
		System.out.print("consulta" + Condiciones3);
		boolean StatusQuerycondiciones3 = ExecuteQuerycondiciones3.isEmpty();

		if (!StatusQuerycondiciones3) {
			testCase.addTextEvidenceCurrentStep("Se encontro correctamente la tabla");
			testCase.addQueryEvidenceCurrentStep(ExecuteQuerycondiciones3);
//			CAMBIO = ExecuteQuerybank.getData(0, "DAR_CAMBIO");
		} else {
			testCase.addTextEvidenceCurrentStep("No se encontro ninguna tabla ");
		}
		SQLResult ExecuteQuerycondiciones4 = executeQuery(dbFCIAS, Condiciones4);
		System.out.print("consulta" + Condiciones4);
		boolean StatusQuerycondiciones4 = ExecuteQuerycondiciones4.isEmpty();

		if (!StatusQuerycondiciones4) {
			testCase.addTextEvidenceCurrentStep("Se encontro correctamente la tabla");
			testCase.addQueryEvidenceCurrentStep(ExecuteQuerycondiciones4);
//			CAMBIO = ExecuteQuerybank.getData(0, "DAR_CAMBIO");
		} else {
			testCase.addTextEvidenceCurrentStep("No se encontro ninguna tabla ");
		}

		//----------------------------------------------CONSULTA planogramacion

		System.out.print("consulta" +updatePlanogramacion);
		testCase.addBoldTextEvidenceCurrentStep("Update y consulta de planogramacion");
		testCase.addTextEvidenceCurrentStep("Query:"+updatePlanogramacion);
		Statement stmt5 = dbFCIAS.getConn().createStatement();

		stmt5.executeUpdate(updatePlanogramacion);


		SQLResult ExecuteQueryplanogramacion= executeQuery(dbFCIAS, planogramacion);
		System.out.print("consulta" +planogramacion);
		boolean StatusQueryplanogramacion = ExecuteQueryplanogramacion.isEmpty();

		if (!StatusQueryplanogramacion) {
			testCase.addTextEvidenceCurrentStep("Se encontro correctamente la tabla");
			testCase.addQueryEvidenceCurrentStep(ExecuteQueryplanogramacion);
//			CAMBIO = ExecuteQuerybank.getData(0, "DAR_CAMBIO");
		} else {
			testCase.addTextEvidenceCurrentStep("No se encontro ninguna tabla ");
		}
		SQLResult ExecuteQueryplanogramacion1= executeQuery(dbFCIAS, planogramacion1);
		System.out.print("consulta" +planogramacion1);
		boolean StatusQueryplanogramacion1 = ExecuteQueryplanogramacion1.isEmpty();

		if (!StatusQueryplanogramacion1) {
			testCase.addTextEvidenceCurrentStep("Se encontro correctamente la tabla");
			testCase.addQueryEvidenceCurrentStep(ExecuteQueryplanogramacion1);
//			CAMBIO = ExecuteQuerybank.getData(0, "DAR_CAMBIO");
		} else {
			testCase.addTextEvidenceCurrentStep("No se encontro ninguna tabla ");
		}
		SQLResult ExecuteQueryplanogramacion2= executeQuery(dbFCIAS, planogramacion2);
		System.out.print("consulta" +planogramacion2);
		boolean StatusQueryplanogramacion2 = ExecuteQueryplanogramacion2.isEmpty();

		if (!StatusQueryplanogramacion2) {
			testCase.addTextEvidenceCurrentStep("Se encontro correctamente la tabla");
			testCase.addQueryEvidenceCurrentStep(ExecuteQueryplanogramacion2);
//			CAMBIO = ExecuteQuerybank.getData(0, "DAR_CAMBIO");
		} else {
			testCase.addTextEvidenceCurrentStep("No se encontro ninguna tabla ");
		}
		SQLResult ExecuteQueryplanogramacion3= executeQuery(dbFCIAS, planogramacion3);
		System.out.print("consulta" +planogramacion3);
		boolean StatusQueryplanogramacion3 = ExecuteQueryplanogramacion3.isEmpty();

		if (!StatusQueryplanogramacion3) {
			testCase.addTextEvidenceCurrentStep("Se encontro correctamente la tabla");
			testCase.addQueryEvidenceCurrentStep(ExecuteQueryplanogramacion3);
//			CAMBIO = ExecuteQuerybank.getData(0, "DAR_CAMBIO");
		} else {
			testCase.addTextEvidenceCurrentStep("No se encontro ninguna tabla ");
		}
		SQLResult ExecuteQueryplanogramacion4= executeQuery(dbFCIAS, planogramacion4);
		System.out.print("consulta" +planogramacion4);
		boolean StatusQueryplanogramacion4 = ExecuteQueryplanogramacion4.isEmpty();

		if (!StatusQueryplanogramacion4) {
			testCase.addTextEvidenceCurrentStep("Se encontro correctamente la tabla");
			testCase.addQueryEvidenceCurrentStep(ExecuteQueryplanogramacion4);
//			CAMBIO = ExecuteQuerybank.getData(0, "DAR_CAMBIO");
		} else {
			testCase.addTextEvidenceCurrentStep("No se encontro ninguna tabla ");
		}
		SQLResult ExecuteQueryplanogramacion5= executeQuery(dbFCIAS, planogramacion5);
		System.out.print("consulta" +planogramacion5);
		boolean StatusQueryplanogramacion5 = ExecuteQueryplanogramacion5.isEmpty();

		if (!StatusQueryplanogramacion5) {
			testCase.addTextEvidenceCurrentStep("Se encontro correctamente la tabla");
			testCase.addQueryEvidenceCurrentStep(ExecuteQueryplanogramacion5);
//			CAMBIO = ExecuteQuerybank.getData(0, "DAR_CAMBIO");
		} else {
			testCase.addTextEvidenceCurrentStep("No se encontro ninguna tabla ");
		}
//
		// // PASO 26
		addStep("Dar clic en el link Notificación");
		pl.Notificacion();
//		//		                           PASO 27
		addStep("Dar clic en el botón Aceptar.");
		boolean noti=	pl.NotificacionAlert();
		assertFalse(noti, "Notificacion existe, no se mandaron los datos");
		*/
		}


	@Override
	public void beforeTest() {
	}

	@Override
	public String setTestDescription() {
		return "ATC_FT_002_Acceso_a_CNT";
	}

	@Override
	public String setTestDesigner() {
		return "AutomationQA";
	}

	@Override
	public String setTestFullName() {
		return null;
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