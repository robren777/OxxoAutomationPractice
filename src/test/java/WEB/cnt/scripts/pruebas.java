package WEB.cnt.scripts;

import java.util.HashMap;

import org.testng.annotations.Test;

import WEB.cnt.om.Login;
import WEB.cnt.om.Planogramacion;
import WEB.util.GlobalVariables;
import modelo.BaseExecution;
import utils.password.PasswordUtil;
import utils.selenium.ChromeTest;
import utils.selenium.SeleniumUtil;
import utils.sql.SQLUtil;

public class pruebas extends BaseExecution {

	SeleniumUtil u;

	@Test(dataProvider = "data-provider")

	public void ATC_FT_007_Captura_de_Datos_de_Tienda_en_CNT_Planogramacion_Test(HashMap<String, String> data)
			throws Exception {
		/*
		 * Utiler�as
		 ******************************************************************************************/
		SQLUtil dbFCIAS = new SQLUtil(GlobalVariables.DB_HOST_FCDW2QA_PORTAL, GlobalVariables.DB_USER_FCDW2QA_PORTAL,
				GlobalVariables.DB_PASSWORD_FCDW2QA_PORTAL);
//PREREQUISITOS: LLENAR EL EXCEL CON TIENDA ESPEJO Y HERMANA, PARA QUE ELIGA UNA DE LAS OPCIONES DE AHI
                // en el update de programacion ver la tienda y plaza si aun es valida, de ahi se copiara la info a nuestra plaza y tienda
		/*
		 * Variables
		 ******************************************************************************************/
		String user = data.get("User");
		String password = PasswordUtil.decryptPassword(data.get("Password"));
		String PlazaE = data.get("PlazaE");
		String Distrito = data.get("Distrito");
		String Tienda = data.get("Tienda");
		String URL = data.get("Url");
		String cheque = data.get("cheque");
		String dolares = data.get("dolares");
		String efectivo = data.get("efectivo");
		String TDD1 = data.get("TDD1");
		String TDC1 = data.get("TDC1");
		String vales = data.get("vales");
		String Tamano = data.get("Tamano");
		String Segmento1 = data.get("Segmento");
		String Ubicacion1 = data.get("Ubicacion");
		String diferenciador = data.get("Diferenciador");
		String TiendaE = data.get("TiendaEspejo");
		String TiendaSis = data.get("TiendaHermana");
		String Porcentaje = data.get("Porcentaje");
		String NivelSocioe = data.get("NivelSocioEconomico");
		String ZonaEco = data.get("ZonaEconomica");
		String Vial = data.get("Vialidad");
		String UbicacionEntorno = data.get("UbicacionEntorno");
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
				+ "where cr_superior='10CHI' and cr_tienda in ('50REM') )) "
				+ "WHERE id_tienda in (select id_tienda from t_tiendas "
				+ "where cr_superior= '" + PlazaP + "' and cr_tienda in ( '" + TiendaP +"')";
		String updatelegal = "UPDATE tiendas.T_LEGALES SET NUM_CUENTA_PREDIAL='"+Predial+"',PEM_VTA_CER_PROPIO='" +Cer_Propio+"',SINDICATO_AL_QUE_PERTENECE='"+Sindicato+"',PEM_VTA_VINOS='"+VTA_VINOS+"'"
				+ " where id_tienda IN (SELECT ID_TIENDA FROM tiendas.t_tiendas WHERE CR_TIENDA IN '" + TiendaP + "' " + "AND CR_SUPERIOR IN '"+PlazaP+"')";
		String QueryLEGAL = "select ID_TIENDA, NUM_CUENTA_PREDIAL,sindicato_al_que_pertenece,ultima_fecha_recoleccion,staus,pem_vta_vinos from TIENDAS.T_LEGALES where id_tienda "
				+ "IN (SELECT ID_TIENDA FROM tiendas.t_tiendas WHERE CR_TIENDA IN '" + TiendaP + "' " + "AND CR_SUPERIOR IN '"
				+ PlazaP + "')";
		String QueryLEGAL1 = "select ultima_fecha_recoleccion,staus,pem_vta_vinos from TIENDAS.T_LEGALES where id_tienda "
				+ "IN (SELECT ID_TIENDA FROM tiendas.t_tiendas WHERE CR_TIENDA IN '" + TiendaP + "' " + "AND CR_SUPERIOR IN '"
				+ PlazaP + "') ";
		
		
		String insertCondiciones = "INSERT INTO t_condiciones_propiedad (id_tienda, lid_condicion_propiedad, NOMBRE_ARRENDADOR,LID_BANCO_CUENTA_ARRENDADOR,NUM_CUENTA_ARRENDADOR,monto_renta,monto_renta_por_mts2,lid_tipo_incremento_renta,plazo_contrato,fecha_inicio_contrato,fecha_fin_contrato,LAST_UPDATE_BY,AREA_DEL_TERRENO,LAST_UPDATE_DATE) "
				+ "VALUES ((select id_tienda FROM tiendas.T_TIENDAS WHERE STATUS = 'S_NUEVA' and id_tienda not in\r\n"
				+ "(select id_tienda from tiendas.t_condiciones_propiedad)\r\n"
				+ "AND CR_SUPERIOR || CR_TIENDA IN ('"+ PlazaP + "'|| '" + TiendaP + "')) "
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
		
		// ------------------------------------PASO 1

		addStep("Ingresar al portal " + URL + " con el <Usuario>" + user + ""); // viene en
																				// excel
		u = new SeleniumUtil(new ChromeTest(), true); // IETest para usar internet explorer
		Login pa = new Login(u, testCase); // crea objeto de locator
		// PortalApps pa = new PortalApps(u, testCase); // crea objeto de locator
		u.get(data.get("Url")); // toma el valor Url del data provider
		u.hardWait(3); // tiempo en segundos

//		pa.proceedToPortal();    // es para lo de la pagina no segura
		pa.logginInventarioEspacios(user, password);

//		//------------------------------------PASO 2
		addStep("Seleccionar la Plaza y dar clic en el bot�n Continuar");
		pa.clickSeleccionarPlaza(PlazaE);
//		//------------------------------------PASO 3
		addStep("Seleccionar el Distrito y Tienda; a continuaci�n dar clic en el bot�n Continuar");
		pa.clickSeleccionarDistritoTienda(Distrito, Tienda);

//		// PASO 7
//		addStep("Dar clic en el link Formato y Entorno.");
//		Formato_y_Entorno fe = new Formato_y_Entorno(u, testCase);
//		fe.FormatoyEntorno();
//
////		//      						   PASO 8
//		addStep("Seleccionar una opci�n en el campo Tama�o de tienda.");
//		fe.TamanoTienda(Tamano);
//
////		
////		//	  							   PASO 9	
//
//		addStep("Seleccionar una opci�n en el campo Segmento al que pertenece.");
//		fe.Segmento(Segmento1);
//
////		//		                           PASO 10
//		addStep("Seleccionar una opci�n en el campo Tipo ubicaci�n de tienda.");
//		fe.Ubicacion(Ubicacion1);
//		fe.Diferenciador(diferenciador);
////		//		                           PASO 11
//		addStep("Seleccionar una opci�n en el campo Tienda espejo.");
//		fe.TiendaEspejo(TiendaE);
////		//		                           PASO 12
//		addStep("Seleccionar una opci�n en el campo Tienda hermana.");
//		fe.TiendaSister(TiendaSis);
////		//		                           PASO 13
//		addStep("Capturar el Porcentaje de ajuste.");
//		fe.Proce(Porcentaje);
////		//		                           PASO 14
//		addStep("Seleccionar una opci�n en el campo Nivel socioecon�mico.");
//		fe.NivelSocio(NivelSocioe);
////		//		                           PASO 15
//		addStep("Seleccionar una opci�n en el campo Zona econ�mica.");
//		fe.ZonaE(ZonaEco);
////		//		                           PASO 16
//		addStep("Dar clic en el bot�n Actualizar.");
//		fe.clickBotonActualizar();


//		//		                           PASO 21
		Planogramacion pl = new Planogramacion(u, testCase);
//				pl.Notificacion();
//		//		                           PASO 27
		addStep("Dar clic en el bot�n Aceptar.");
		pl.NotificacionAlert();
	}

	@Override
	public void beforeTest() {
	}

	@Override
	public String setTestDescription() {
		return "ATC_FT_007_Captura_de_Datos_de_Tienda_en_CNT_Planogramacion_Test";
	}

	@Override
	public String setTestDesigner() {
		return "AutomationQA";
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_007_Captura_de_Datos_de_Tienda_en_CNT_Planogramacion_Test";
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
