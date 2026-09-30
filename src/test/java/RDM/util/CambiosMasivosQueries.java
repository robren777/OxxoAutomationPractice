package RDM.util;

import java.sql.SQLException;
import java.util.logging.Logger;

import modelo.TestCase;
import utils.sql.SQLResult;
import utils.sql.SQLUtil;

public class CambiosMasivosQueries {

	TestCase testCase;

	private static final Logger logger = Logger.getLogger(CambiosMasivosQueries.class.getName());

	public CambiosMasivosQueries(TestCase testcase) {
		this.testCase = testcase;
	}

	public SQLResult executeQuery(SQLUtil sqlUtil, String query) throws SQLException {
		return sqlUtil.executeQuery(query);
	}

	/**
	 * Columnas tipo distribucion
	 */
	private String tipoDistribucionColumns = "                     tipo_distro       AS tipo";

	/**
	 * Columnas ruta
	 */
	private String rutaColumns = "                     route";

	/**
	 * Columnas dia de generacion
	 */
	private String diaGenColumns = "                     dia_gen_ped       AS generacion,"
			+ "                     dia_ini_entrega   AS entrega,"
			+ "                     TO_CHAR(h_ini_entrega,'HH24:MI') AS h_ini_entrega,"
			+ "                     TO_CHAR(h_fin_entrega,'HH24:MI') AS h_fin_entrega";

	/**
	 * Columnas dia de programacion
	 */
	private String diaProgColumns = "                     dia_prog,"
			+ "                     TO_CHAR(h_prog,'HH24:MI') AS h_prog," + "                     dia_ini_prod,"
			+ "                     TO_CHAR(h_ini_prod,'HH24:MI') AS h_ini_prod," + "                     dia_fin_prod,"
			+ "                     TO_CHAR(h_fin_prod,'HH24:MI') AS h_fin_prod";

	/**
	 * Columnas dia de verificacion
	 */
	private String diaVerificacionColumns = "                     dia_ini_verificacion,"
			+ "                     TO_CHAR(h_ini_verificacion,'HH24:MI') AS h_ini_verificacion,"
			+ "                     dia_fin_verificacion,"
			+ "                     TO_CHAR(h_fin_verificacion,'HH24:MI') AS h_fin_verificacion";

	/**
	 * Columnas dia de carga
	 */
	private String diaCargaColumns = "                     dia_ini_carga,"
			+ "                     TO_CHAR(h_ini_carga,'HH24:MI') AS h_ini_carga,"
			+ "                     dia_fin_carga,"
			+ "                     TO_CHAR(h_fin_carga,'HH24:MI') AS h_fin_carga";

	/**
	 * Columnas dia de salida
	 */
	private String diaSalidaColumns = "                     dia_salida,"
			+ "                     TO_CHAR(h_salida,'HH24:MI') AS h_salida";

	/**
	 * Columnas dia de disposicion en uni cedis
	 */
	private String diaDisposicionUniCedisColumns = "                     dia_disp_uni_cedis,"
			+ "                     TO_CHAR(h_disp_uni_cedis,'HH24:MI') AS h_disp_uni_cedis";

	/**
	 * Columnas dia de llegada PT
	 */
	private String diaLlegadaPtColumns = "                     dia_llegada_pt,"
			+ "                     TO_CHAR(h_llegada_pt,'HH24:MI') AS h_llegada_pt";

	/**
	 * Columnas dia de salida PT
	 */
	private String diaSalidaPtColumns = "                     dia_salida_pt,"
			+ "                     TO_CHAR(h_salida_pt,'HH24:MI') AS h_salida_pt";

	/**
	 * queryPlaza se utiliza para consultar los distintos valores que aparecen en la
	 * lista de valores del filtro plaza necesita un parametro de entrada que es el
	 * tipo de servicio, por default consultaremos con el valor 'ALL'
	 *
	 * @param servicio
	 * @return query en formato String
	 */
	public String queryPlaza(String servicio) {
		String query = "SELECT DISTINCT " + "substr(store_name10,1,5) plaza " + "FROM "
				+ "rdm100.carrier_service_route a, " + "rdm100.route_dest b, " + "rdm100.xxfc_store_v c, "
				+ "rdm100.route r, " + "rdm100.xxfc_pmo_tdistrib_servicio ser " + "WHERE "
				+ "a.facility_id = b.facility_id " + "AND a.facility_id = r.facility_id " + "AND a.route = b.route "
				+ "AND a.service_code = ser.servicio " + "AND ser.servicio = DECODE('" + servicio
				+ "','ALL',ser.servicio,substr('" + servicio + "',instr('" + servicio
				+ "',ser.servicio),length(ser.servicio) ) ) " + "AND ser.dest_type = c.dest_type "
				+ "AND b.dest_id = c.store " + "AND a.route = r.route " + "AND c.default_wh IN ( " + "SELECT "
				+ "primary_vwh " + "FROM " + "rdm100.xxfc_con_prog_wh_view " + "WHERE " + "primary_vwh IS NOT NULL "
				+ ")";

		return query;
	}

	/**
	 * Query de los resultados mostrados en la pantalla de cambios masivos, buscando
	 * por servicio, plaza y dia de generacion
	 * 
	 * @param servicio
	 * @param plaza
	 * @param diaGen
	 * @return
	 */
	public String queryDiaGen(String servicio, String plaza, String diaGen) {
		String query = "SELECT" + "     *" + " FROM" + "     (" + "         SELECT" + "             *" + "         FROM"
				+ "             (" + "                 SELECT" + "                     tipo_distro,"
				+ "                     route," + "                     dia_gen_ped,"
				+ "                     dia_ini_entrega,"
				+ "                     TO_CHAR(h_ini_entrega,'HH24:MI')  AS h_ini_entrega,"
				+ "                     TO_CHAR(h_fin_entrega,'HH24:MI') AS h_fin_entrega,"
				+ "                     dia_prog," + "                     TO_CHAR(h_prog,'HH24:MI') AS h_prog,"
				+ "                     dia_ini_prod,"
				+ "                     TO_CHAR(h_ini_prod,'HH24:MI') AS h_ini_prod,"
				+ "                     dia_fin_prod,"
				+ "                     TO_CHAR(h_fin_prod,'HH24:MI') AS h_fin_prod,"
				+ "                     dia_ini_verificacion,"
				+ "                     TO_CHAR(h_ini_verificacion,'HH24:MI') AS h_ini_verificacion,"
				+ "                     dia_fin_verificacion,"
				+ "                     TO_CHAR(h_fin_verificacion,'HH24:MI') AS h_fin_verificacion,"
				+ "                     dia_ini_carga,"
				+ "                     TO_CHAR(h_ini_carga,'HH24:MI') AS h_ini_carga,"
				+ "                     dia_fin_carga,"
				+ "                     TO_CHAR(h_fin_carga,'HH24:MI') AS h_fin_carga,"
				+ "                     dia_salida," + "                     TO_CHAR(h_salida,'HH24:MI') AS h_salida,"
				+ "                     dia_disp_uni_cedis,"
				+ "                     TO_CHAR(h_disp_uni_cedis,'HH24:MI') AS h_disp_uni_cedis,"
				+ "                     dia_llegada_pt,"
				+ "                     TO_CHAR(h_llegada_pt,'HH24:MI') AS h_llegada_pt,"
				+ "                     dia_salida_pt,"
				+ "                     TO_CHAR(h_salida_pt,'HH24:MI') AS h_salida_pt" + "                 FROM"
				+ "                     rdm100.xxfc_pmo_route" + "                 WHERE"
				+ "                     status = 'A'" + "                     AND route IN ("
				+ "                         SELECT" + "                             b.route"
				+ "                         FROM" + "                             rdm100.carrier_service_route a,"
				+ "                             rdm100.route_dest b,"
				+ "                             rdm100.xxfc_store_v c," + "                             rdm100.route r,"
				+ "                             rdm100.xxfc_pmo_tdistrib_servicio ser"
				+ "                         WHERE" + "                             a.facility_id = b.facility_id"
				+ "                             AND a.facility_id = r.facility_id"
				+ "                             AND a.service_code = ser.servicio"
				+ "                             AND ser.servicio = DECODE('" + servicio
				+ "','ALL',ser.servicio,substr('" + servicio + "',instr('" + servicio + "',ser.servicio"
				+ "                             ),length(ser.servicio) ) )"
				+ "                             AND ser.dest_type = c.dest_type"
				+ "                             AND b.dest_id = c.store"
				+ "                             AND a.route = r.route"
				+ "                             AND c.default_wh IN (" + "                                 SELECT"
				+ "                                     primary_vwh" + "                                 FROM"
				+ "                                     rdm100.xxfc_con_prog_wh_view"
				+ "                                 WHERE"
				+ "                                     primary_vwh IS NOT NULL" + "                             )"
				+ "                             AND substr(store_name10,1,5) ='" + plaza + "'"
				+ "                         GROUP BY" + "                             b.route"
				+ "                     )" + "                 ORDER BY" + "                     route ASC"
				+ "             )" + "         WHERE" + "             DIA_GEN_PED = '" + diaGen + "'" + "     )";
		return query;
	}

	/**
	 * queryTipoDistribucionDiaGenPed se utiliza para consultar por tipo de
	 * distribucion, muestra la primera parte de la consulta para tener informacion
	 * completa deben ejecutarse otros queries: queryTipoDistribucionDiaProg,
	 * queryTipoDistribucionDiaIniVerificacion, queryTipoDistribucionDiaIniCarga,
	 * queryTipoDistribucionDiaDispUniCedis
	 *
	 * @param servicio
	 * @param plaza            *REQUERIDO
	 * @param tipoDistribucion
	 * @return query en formato String
	 */
	public String queryTipoDistribucionDiaGenPed(String servicio, String plaza, String tipoDistribucion) {
		String query = "SELECT\n" + "    *\n" + "FROM\n" + "    (\n" + "        SELECT\n" + "            *\n"
				+ "        FROM\n" + "            (\n" + "                SELECT\n" + "                    *\n"
				+ "                FROM\n" + "                    (\n" + "                        SELECT\n"
				+ "                            tipo_distro,\n" + "                            route,\n"
				+ "                            dia_gen_ped,\n" + "                            dia_ini_entrega,\n"
				+ "                            TO_CHAR(h_ini_entrega,'HH24:MI') AS\t h_ini_entrega,\n"
				+ "                            TO_CHAR(h_fin_entrega,'HH24:MI') AS\t h_fin_entrega\n"
				+ "                        FROM\n" + "                            rdm100.xxfc_pmo_route\n"
				+ "                        WHERE\n" + "                            status = 'A'\n"
				+ "                            AND route IN (\n" + "                                SELECT\n"
				+ "                                    b.route\n" + "                                FROM\n"
				+ "                                    rdm100.carrier_service_route a,\n"
				+ "                                    rdm100.route_dest b,\n"
				+ "                                    rdm100.xxfc_store_v c,\n"
				+ "                                    rdm100.route r,\n"
				+ "                                    rdm100.xxfc_pmo_tdistrib_servicio ser\n"
				+ "                                WHERE\n"
				+ "                                    a.facility_id = b.facility_id\n"
				+ "                                    AND a.facility_id = r.facility_id\n"
				+ "                                    AND a.service_code = ser.servicio\n"
				+ "                                    AND ser.servicio = DECODE('" + servicio
				+ "','ALL',ser.servicio,substr('" + servicio + "',instr('" + servicio + "',ser.servicio\n"
				+ "                                    ),length(ser.servicio) ) )\n"
				+ "                                    AND ser.dest_type = c.dest_type\n"
				+ "                                    AND b.dest_id = c.store\n"
				+ "                                    AND a.route = r.route\n"
				+ "                                    AND c.default_wh IN (\n"
				+ "                                        SELECT\n"
				+ "                                            primary_vwh\n"
				+ "                                        FROM\n"
				+ "                                            rdm100.xxfc_con_prog_wh_view\n"
				+ "                                        WHERE\n"
				+ "                                            primary_vwh IS NOT NULL\n"
				+ "                                    )\n"
				+ "                                    AND substr(store_name10,1,5) ='" + plaza + "'\n"
				+ "                                GROUP BY\n" + "                                    b.route\n"
				+ "                            )\n" + "                        ORDER BY\n"
				+ "                            route ASC\n" + "                    )\n" + "                WHERE\n"
				+ "                    tipo_distro ='" + tipoDistribucion + "'\n" + "            )\n" + "    )\n"
				+ "WHERE\n" + "    ROWNUM <= 10";
		return query;
	}

	/**
	 * queryTipoDistribucionDiaProg se utiliza para consultar por tipo de
	 * distribucion, muestra la primera parte de la consulta para tener informacion
	 * completa deben ejecutarse otros queries: queryTipoDistribucionDiaGenPed,
	 * queryTipoDistribucionDiaIniVerificacion, queryTipoDistribucionDiaIniCarga,
	 * queryTipoDistribucionDiaDispUniCedis
	 *
	 * @param servicio
	 * @param plaza            *REQUERIDO
	 * @param tipoDistribucion
	 * @return query en formato String
	 */
	public String queryTipoDistribucionDiaProg(String servicio, String plaza, String tipoDistribucion) {
		String query = "SELECT\n" + "    *\n" + "FROM\n" + "    (\n" + "        SELECT\n" + "            *\n"
				+ "        FROM\n" + "            (\n" + "                SELECT\n" + "                    *\n"
				+ "                FROM\n" + "                    (\n" + "                        SELECT\n"
				+ "                            tipo_distro,\n" + "                            dia_prog,\n"
				+ "                            TO_CHAR(h_prog,'HH24:MI') AS\th_prog,\n"
				+ "                            dia_ini_prod,\n"
				+ "                            TO_CHAR(h_ini_prod,'HH24:MI') AS\th_ini_prod,\n"
				+ "                            dia_fin_prod,\n"
				+ "                            TO_CHAR(h_fin_prod,'HH24:MI') AS\th_fin_prod\n"
				+ "                        FROM\n" + "                            rdm100.xxfc_pmo_route\n"
				+ "                        WHERE\n" + "                            status = 'A'\n"
				+ "                            AND route IN (\n" + "                                SELECT\n"
				+ "                                    b.route\n" + "                                FROM\n"
				+ "                                    rdm100.carrier_service_route a,\n"
				+ "                                    rdm100.route_dest b,\n"
				+ "                                    rdm100.xxfc_store_v c,\n"
				+ "                                    rdm100.route r,\n"
				+ "                                    rdm100.xxfc_pmo_tdistrib_servicio ser\n"
				+ "                                WHERE\n"
				+ "                                    a.facility_id = b.facility_id\n"
				+ "                                    AND a.facility_id = r.facility_id\n"
				+ "                                    AND a.service_code = ser.servicio\n"
				+ "                                    AND ser.servicio = DECODE('" + servicio
				+ "','ALL',ser.servicio,substr('" + servicio + "',instr('" + servicio + "',ser.servicio\n"
				+ "                                    ),length(ser.servicio) ) )\n"
				+ "                                    AND ser.dest_type = c.dest_type\n"
				+ "                                    AND b.dest_id = c.store\n"
				+ "                                    AND a.route = r.route\n"
				+ "                                    AND c.default_wh IN (\n"
				+ "                                        SELECT\n"
				+ "                                            primary_vwh\n"
				+ "                                        FROM\n"
				+ "                                            rdm100.xxfc_con_prog_wh_view\n"
				+ "                                        WHERE\n"
				+ "                                            primary_vwh IS NOT NULL\n"
				+ "                                    )\n"
				+ "                                    AND substr(store_name10,1,5) ='" + plaza + "'\n"
				+ "                                GROUP BY\n" + "                                    b.route\n"
				+ "                            )\n" + "                        ORDER BY\n"
				+ "                            route ASC\n" + "                    )\n" + "                WHERE\n"
				+ "                    tipo_distro ='" + tipoDistribucion + "'\n" + "            )\n" + "    )\n"
				+ "WHERE\n" + "    ROWNUM <= 10";
		return query;
	}

	/**
	 * queryTipoDistribucionDiaIniVerificacion se utiliza para consultar por tipo de
	 * distribucion, muestra la primera parte de la consulta para tener informacion
	 * completa deben ejecutarse otros queries: queryTipoDistribucionDiaGenPed,
	 * queryTipoDistribucionDiaProg, queryTipoDistribucionDiaIniCarga,
	 * queryTipoDistribucionDiaDispUniCedis
	 *
	 * @param servicio
	 * @param plaza            *REQUERIDO
	 * @param tipoDistribucion
	 * @return query en formato String
	 */
	public String queryTipoDistribucionDiaIniVerificacion(String servicio, String plaza, String tipoDistribucion) {
		String query = "SELECT\n" + "    *\n" + "FROM\n" + "    (\n" + "        SELECT\n" + "            *\n"
				+ "        FROM\n" + "            (\n" + "                SELECT\n" + "                    *\n"
				+ "                FROM\n" + "                    (\n" + "                        SELECT\n"
				+ "                            tipo_distro,\n"
				+ "                            dia_ini_verificacion AS\tdia_ini_ver,\n"
				+ "                            TO_CHAR(h_ini_verificacion,'HH24:MI') AS\th_ini_ver,\n"
				+ "                            dia_fin_verificacion AS\tdia_fin_ver,\n"
				+ "                            TO_CHAR(h_fin_verificacion,'HH24:MI') AS\th_fin_ver\n"
				+ "                        FROM\n" + "                            rdm100.xxfc_pmo_route\n"
				+ "                        WHERE\n" + "                            status = 'A'\n"
				+ "                            AND route IN (\n" + "                                SELECT\n"
				+ "                                    b.route\n" + "                                FROM\n"
				+ "                                    rdm100.carrier_service_route a,\n"
				+ "                                    rdm100.route_dest b,\n"
				+ "                                    rdm100.xxfc_store_v c,\n"
				+ "                                    rdm100.route r,\n"
				+ "                                    rdm100.xxfc_pmo_tdistrib_servicio ser\n"
				+ "                                WHERE\n"
				+ "                                    a.facility_id = b.facility_id\n"
				+ "                                    AND a.facility_id = r.facility_id\n"
				+ "                                    AND a.service_code = ser.servicio\n"
				+ "                                    AND ser.servicio = DECODE('" + servicio
				+ "','ALL',ser.servicio,substr('" + servicio + "',instr('" + servicio + "',ser.servicio\n"
				+ "                                    ),length(ser.servicio) ) )\n"
				+ "                                    AND ser.dest_type = c.dest_type\n"
				+ "                                    AND b.dest_id = c.store\n"
				+ "                                    AND a.route = r.route\n"
				+ "                                    AND c.default_wh IN (\n"
				+ "                                        SELECT\n"
				+ "                                            primary_vwh\n"
				+ "                                        FROM\n"
				+ "                                            rdm100.xxfc_con_prog_wh_view\n"
				+ "                                        WHERE\n"
				+ "                                            primary_vwh IS NOT NULL\n"
				+ "                                    )\n"
				+ "                                    AND substr(store_name10,1,5) ='" + plaza + "'\n"
				+ "                                GROUP BY\n" + "                                    b.route\n"
				+ "                            )\n" + "                        ORDER BY\n"
				+ "                            route ASC\n" + "                    )\n" + "                WHERE\n"
				+ "                    tipo_distro ='" + tipoDistribucion + "'\n" + "            )\n" + "    )\n"
				+ "WHERE\n" + "    ROWNUM <= 10";
		return query;
	}

	/**
	 * queryTipoDistribucionDiaIniCarga se utiliza para consultar por tipo de
	 * distribucion, muestra la primera parte de la consulta para tener informacion
	 * completa deben ejecutarse otros queries: queryTipoDistribucionDiaGenPed,
	 * queryTipoDistribucionDiaIniVerificacion, queryTipoDistribucionDiaProg,
	 * queryTipoDistribucionDiaDispUniCedis
	 *
	 * @param servicio
	 * @param plaza            *REQUERIDO
	 * @param tipoDistribucion
	 * @return query en formato String
	 */
	public String queryTipoDistribucionDiaIniCarga(String servicio, String plaza, String tipoDistribucion) {
		String query = "SELECT\n" + "    *\n" + "FROM\n" + "    (\n" + "        SELECT\n" + "            *\n"
				+ "        FROM\n" + "            (\n" + "                SELECT\n" + "                    *\n"
				+ "                FROM\n" + "                    (\n" + "                        SELECT\n"
				+ "                            tipo_distro,\n" + "                            dia_ini_carga,\n"
				+ "                            TO_CHAR(h_ini_carga,'HH24:MI') AS\th_ini_carga,\n"
				+ "                            dia_fin_carga,\n"
				+ "                            TO_CHAR(h_fin_carga,'HH24:MI') AS\th_fin_carga,\n"
				+ "                            dia_salida,\n"
				+ "                            TO_CHAR(h_salida,'HH24:MI') AS\th_salida\n"
				+ "                        FROM\n" + "                            rdm100.xxfc_pmo_route\n"
				+ "                        WHERE\n" + "                            status = 'A'\n"
				+ "                            AND route IN (\n" + "                                SELECT\n"
				+ "                                    b.route\n" + "                                FROM\n"
				+ "                                    rdm100.carrier_service_route a,\n"
				+ "                                    rdm100.route_dest b,\n"
				+ "                                    rdm100.xxfc_store_v c,\n"
				+ "                                    rdm100.route r,\n"
				+ "                                    rdm100.xxfc_pmo_tdistrib_servicio ser\n"
				+ "                                WHERE\n"
				+ "                                    a.facility_id = b.facility_id\n"
				+ "                                    AND a.facility_id = r.facility_id\n"
				+ "                                    AND a.service_code = ser.servicio\n"
				+ "                                    AND ser.servicio = DECODE('" + servicio
				+ "','ALL',ser.servicio,substr('" + servicio + "',instr('" + servicio + "',ser.servicio\n"
				+ "                                    ),length(ser.servicio) ) )\n"
				+ "                                    AND ser.dest_type = c.dest_type\n"
				+ "                                    AND b.dest_id = c.store\n"
				+ "                                    AND a.route = r.route\n"
				+ "                                    AND c.default_wh IN (\n"
				+ "                                        SELECT\n"
				+ "                                            primary_vwh\n"
				+ "                                        FROM\n"
				+ "                                            rdm100.xxfc_con_prog_wh_view\n"
				+ "                                        WHERE\n"
				+ "                                            primary_vwh IS NOT NULL\n"
				+ "                                    )\n"
				+ "                                    AND substr(store_name10,1,5) ='" + plaza + "'\n"
				+ "                                GROUP BY\n" + "                                    b.route\n"
				+ "                            )\n" + "                        ORDER BY\n"
				+ "                            route ASC\n" + "                    )\n" + "                WHERE\n"
				+ "                    tipo_distro ='" + tipoDistribucion + "'\n" + "            )\n" + "    )\n"
				+ "WHERE\n" + "    ROWNUM <= 10";
		return query;
	}

	/**
	 * queryTipoDistribucionDiaDispUniCedis se utiliza para consultar por tipo de
	 * distribucion, muestra la primera parte de la consulta para tener informacion
	 * completa deben ejecutarse otros queries: queryTipoDistribucionDiaGenPed,
	 * queryTipoDistribucionDiaIniVerificacion, queryTipoDistribucionDiaProg,
	 * queryTipoDistribucionDiaIniCarga
	 *
	 * @param servicio
	 * @param plaza            *REQUERIDO
	 * @param tipoDistribucion
	 * @return query en formato String
	 */
	public String queryTipoDistribucionDiaDispUniCedis(String servicio, String plaza, String tipoDistribucion) {
		String query = "SELECT\n" + "    *\n" + "FROM\n" + "    (\n" + "        SELECT\n" + "            *\n"
				+ "        FROM\n" + "            (\n" + "                SELECT\n" + "                    *\n"
				+ "                FROM\n" + "                    (\n" + "                        SELECT\n"
				+ tipoDistribucionColumns + "," + diaDisposicionUniCedisColumns + "," + diaLlegadaPtColumns + ","
				+ diaSalidaPtColumns + "                        FROM\n"
				+ "                            rdm100.xxfc_pmo_route\n" + "                        WHERE\n"
				+ "                            status = 'A'\n" + "                            AND route IN (\n"
				+ "                                SELECT\n" + "                                    b.route\n"
				+ "                                FROM\n"
				+ "                                    rdm100.carrier_service_route a,\n"
				+ "                                    rdm100.route_dest b,\n"
				+ "                                    rdm100.xxfc_store_v c,\n"
				+ "                                    rdm100.route r,\n"
				+ "                                    rdm100.xxfc_pmo_tdistrib_servicio ser\n"
				+ "                                WHERE\n"
				+ "                                    a.facility_id = b.facility_id\n"
				+ "                                    AND a.facility_id = r.facility_id\n"
				+ "                                    AND a.service_code = ser.servicio\n"
				+ "                                    AND ser.servicio = DECODE('" + servicio
				+ "','ALL',ser.servicio,substr('" + servicio + "',instr('" + servicio + "',ser.servicio\n"
				+ "                                    ),length(ser.servicio) ) )\n"
				+ "                                    AND ser.dest_type = c.dest_type\n"
				+ "                                    AND b.dest_id = c.store\n"
				+ "                                    AND a.route = r.route\n"
				+ "                                    AND c.default_wh IN (\n"
				+ "                                        SELECT\n"
				+ "                                            primary_vwh\n"
				+ "                                        FROM\n"
				+ "                                            rdm100.xxfc_con_prog_wh_view\n"
				+ "                                        WHERE\n"
				+ "                                            primary_vwh IS NOT NULL\n"
				+ "                                    )\n"
				+ "                                    AND substr(store_name10,1,5) ='" + plaza + "'\n"
				+ "                                GROUP BY\n" + "                                    b.route\n"
				+ "                            )\n" + "                        ORDER BY\n"
				+ "                            route ASC\n" + "                    )\n" + "                WHERE\n"
				+ "                    tipo_distro ='" + tipoDistribucion + "'\n" + "            )\n" + "    )\n"
				+ "WHERE\n" + "    ROWNUM <= 10";
		return query;
	}

	/**
	 * Query de la visualizacion de la pantalla de cambios masivos buscando por ruta
	 * y plaza, visualiza los dias de generacion
	 * 
	 * @author 2000132763
	 * @param servicio
	 * @param plaza
	 * @param ruta
	 * @return
	 */
	public String queryRutaDiaGen(String servicio, String plaza, String ruta) {
		String query = "SELECT\n" + "     *\n" + " FROM\n" + "     (\n" + "         SELECT\n" + "             *\n"
				+ "         FROM\n" + "             (\n" + "                 SELECT\n" + rutaColumns + ","
				+ diaGenColumns + "                 FROM\n" + "                     rdm100.xxfc_pmo_route\n"
				+ "                 WHERE\n" + "                     status = 'A'\n"
				+ "                     AND route IN (\n" + "                         SELECT\n"
				+ "                             b.route\n" + "                         FROM\n"
				+ "                             rdm100.carrier_service_route a,\n"
				+ "                             rdm100.route_dest b,\n"
				+ "                             rdm100.xxfc_store_v c,\n"
				+ "                             rdm100.route r,\n"
				+ "                             rdm100.xxfc_pmo_tdistrib_servicio ser\n"
				+ "                         WHERE\n" + "                             a.facility_id = b.facility_id\n"
				+ "                             AND a.facility_id = r.facility_id\n"
				+ "                             AND a.service_code = ser.servicio\n"
				+ "                             AND ser.servicio = DECODE('" + servicio
				+ "','ALL',ser.servicio,substr('" + servicio + "',instr('" + servicio
				+ "',ser.servicio),length(ser.servicio) ) )\n"
				+ "                             AND ser.dest_type = c.dest_type\n"
				+ "                             AND b.dest_id = c.store\n"
				+ "                             AND a.route = r.route\n"
				+ "                             AND c.default_wh IN (\n" + "                                 SELECT\n"
				+ "                                     primary_vwh\n" + "                                 FROM\n"
				+ "                                     rdm100.xxfc_con_prog_wh_view\n"
				+ "                                 WHERE\n"
				+ "                                     primary_vwh IS NOT NULL\n" + "                             )\n"
				+ "                             AND substr(store_name10,1,5) = '" + plaza + "'\n"
				+ "                         GROUP BY\n" + "                             b.route\n"
				+ "                     )\n" + "                 ORDER BY\n" + "                     route ASC\n"
				+ "             )\n" + "         WHERE\n" + "             route = '" + ruta + "'\n" + "     )";
		return query;
	}

	/**
	 * Query de la visualizacion de la pantalla de cambios masivos buscando por ruta
	 * y plaza, visualiza los dias de programacion
	 * 
	 * @author 2000132763
	 * @param servicio
	 * @param plaza
	 * @param ruta
	 * @return
	 */
	public String queryRutaDiaProg(String servicio, String plaza, String ruta) {
		String query = "SELECT\n" + "     *\n" + " FROM\n" + "     (\n" + "         SELECT\n" + "             *\n"
				+ "         FROM\n" + "             (\n" + "                 SELECT\n" + rutaColumns + ","
				+ diaProgColumns + "                 FROM\n" + "                     rdm100.xxfc_pmo_route\n"
				+ "                 WHERE\n" + "                     status = 'A'\n"
				+ "                     AND route IN (\n" + "                         SELECT\n"
				+ "                             b.route\n" + "                         FROM\n"
				+ "                             rdm100.carrier_service_route a,\n"
				+ "                             rdm100.route_dest b,\n"
				+ "                             rdm100.xxfc_store_v c,\n"
				+ "                             rdm100.route r,\n"
				+ "                             rdm100.xxfc_pmo_tdistrib_servicio ser\n"
				+ "                         WHERE\n" + "                             a.facility_id = b.facility_id\n"
				+ "                             AND a.facility_id = r.facility_id\n"
				+ "                             AND a.service_code = ser.servicio\n"
				+ "                             AND ser.servicio = DECODE('" + servicio
				+ "','ALL',ser.servicio,substr('" + servicio + "',instr('" + servicio
				+ "',ser.servicio),length(ser.servicio) ) )\n"
				+ "                             AND ser.dest_type = c.dest_type\n"
				+ "                             AND b.dest_id = c.store\n"
				+ "                             AND a.route = r.route\n"
				+ "                             AND c.default_wh IN (\n" + "                                 SELECT\n"
				+ "                                     primary_vwh\n" + "                                 FROM\n"
				+ "                                     rdm100.xxfc_con_prog_wh_view\n"
				+ "                                 WHERE\n"
				+ "                                     primary_vwh IS NOT NULL\n" + "                             )\n"
				+ "                             AND substr(store_name10,1,5) = '" + plaza + "'\n"
				+ "                         GROUP BY\n" + "                             b.route\n"
				+ "                     )\n" + "                 ORDER BY\n" + "                     route ASC\n"
				+ "             )\n" + "         WHERE\n" + "             route = '" + ruta + "'\n" + "     )";
		return query;
	}

	/**
	 * Query de la visualizacion de la pantalla de cambios masivos buscando por ruta
	 * y plaza, visualiza los dias de verificacion
	 * 
	 * @author 2000132763
	 * @param servicio
	 * @param plaza
	 * @param ruta
	 * @return
	 */
	public String queryRutaDiaIniVerificacion(String servicio, String plaza, String ruta) {
		String query = "SELECT\n" + "     *\n" + " FROM\n" + "     (\n" + "         SELECT\n" + "             *\n"
				+ "         FROM\n" + "             (\n" + "                 SELECT\n" + rutaColumns + ","
				+ diaVerificacionColumns + "                 FROM\n" + "                     rdm100.xxfc_pmo_route\n"
				+ "                 WHERE\n" + "                     status = 'A'\n"
				+ "                     AND route IN (\n" + "                         SELECT\n"
				+ "                             b.route\n" + "                         FROM\n"
				+ "                             rdm100.carrier_service_route a,\n"
				+ "                             rdm100.route_dest b,\n"
				+ "                             rdm100.xxfc_store_v c,\n"
				+ "                             rdm100.route r,\n"
				+ "                             rdm100.xxfc_pmo_tdistrib_servicio ser\n"
				+ "                         WHERE\n" + "                             a.facility_id = b.facility_id\n"
				+ "                             AND a.facility_id = r.facility_id\n"
				+ "                             AND a.service_code = ser.servicio\n"
				+ "                             AND ser.servicio = DECODE('" + servicio
				+ "','ALL',ser.servicio,substr('" + servicio + "',instr('" + servicio + "',ser.servicio\n"
				+ "                             ),length(ser.servicio) ) )\n"
				+ "                             AND ser.dest_type = c.dest_type\n"
				+ "                             AND b.dest_id = c.store\n"
				+ "                             AND a.route = r.route\n"
				+ "                             AND c.default_wh IN (\n" + "                                 SELECT\n"
				+ "                                     primary_vwh\n" + "                                 FROM\n"
				+ "                                     rdm100.xxfc_con_prog_wh_view\n"
				+ "                                 WHERE\n"
				+ "                                     primary_vwh IS NOT NULL\n" + "                             )\n"
				+ "                             AND substr(store_name10,1,5) ='" + plaza + "'\n"
				+ "                         GROUP BY\n" + "                             b.route\n"
				+ "                     )\n" + "                 ORDER BY\n" + "                     route ASC\n"
				+ "             )\n" + "         WHERE\n" + "             route = '" + ruta + "'\n" + "     )";
		return query;
	}

	/**
	 * Query de la visualizacion de la pantalla de cambios masivos buscando por ruta
	 * y plaza, visualiza los dias de inicio de carga
	 * 
	 * @author 2000132763
	 * @param servicio
	 * @param plaza
	 * @param ruta
	 * @return
	 */
	public String queryRutaDiaIniCarga(String servicio, String plaza, String ruta) {
		String query = "SELECT\n" + "     *\n" + " FROM\n" + "     (\n" + "         SELECT\n" + "             *\n"
				+ "         FROM\n" + "             (\n" + "                 SELECT\n" + rutaColumns + ","
				+ diaCargaColumns + "                 FROM\n" + "                     rdm100.xxfc_pmo_route\n"
				+ "                 WHERE\n" + "                     status = 'A'\n"
				+ "                     AND route IN (\n" + "                         SELECT\n"
				+ "                             b.route\n" + "                         FROM\n"
				+ "                             rdm100.carrier_service_route a,\n"
				+ "                             rdm100.route_dest b,\n"
				+ "                             rdm100.xxfc_store_v c,\n"
				+ "                             rdm100.route r,\n"
				+ "                             rdm100.xxfc_pmo_tdistrib_servicio ser\n"
				+ "                         WHERE\n" + "                             a.facility_id = b.facility_id\n"
				+ "                             AND a.facility_id = r.facility_id\n"
				+ "                             AND a.service_code = ser.servicio\n"
				+ "                             AND ser.servicio = DECODE('" + servicio
				+ "','ALL',ser.servicio,substr('" + servicio + "',instr('" + servicio + "',ser.servicio\n"
				+ "                             ),length(ser.servicio) ) )\n"
				+ "                             AND ser.dest_type = c.dest_type\n"
				+ "                             AND b.dest_id = c.store\n"
				+ "                             AND a.route = r.route\n"
				+ "                             AND c.default_wh IN (\n" + "                                 SELECT\n"
				+ "                                     primary_vwh\n" + "                                 FROM\n"
				+ "                                     rdm100.xxfc_con_prog_wh_view\n"
				+ "                                 WHERE\n"
				+ "                                     primary_vwh IS NOT NULL\n" + "                             )\n"
				+ "                             AND substr(store_name10,1,5) ='" + plaza + "'\n"
				+ "                         GROUP BY\n" + "                             b.route\n"
				+ "                     )\n" + "                 ORDER BY\n" + "                     route ASC\n"
				+ "             )\n" + "         WHERE\n" + "             route = '" + ruta + "'\n" + "     )";
		return query;
	}

	/**
	 * Query de la visualizacion de la pantalla de cambios masivos buscando por ruta
	 * y plaza, visualiza los dias de disposicion en unicedis
	 * 
	 * @author 2000132763
	 * @param servicio
	 * @param plaza
	 * @param ruta
	 * @return
	 */
	public String queryRutaDiaDispUniCedis(String servicio, String plaza, String ruta) {
		String query = "SELECT\n" + "     *\n" + " FROM\n" + "     (\n" + "         SELECT\n" + "             *\n"
				+ "         FROM\n" + "             (\n" + "                 SELECT\n" + rutaColumns + ","
				+ diaDisposicionUniCedisColumns + "                 FROM\n"
				+ "                     rdm100.xxfc_pmo_route\n" + "                 WHERE\n"
				+ "                     status = 'A'\n" + "                     AND route IN (\n"
				+ "                         SELECT\n" + "                             b.route\n"
				+ "                         FROM\n" + "                             rdm100.carrier_service_route a,\n"
				+ "                             rdm100.route_dest b,\n"
				+ "                             rdm100.xxfc_store_v c,\n"
				+ "                             rdm100.route r,\n"
				+ "                             rdm100.xxfc_pmo_tdistrib_servicio ser\n"
				+ "                         WHERE\n" + "                             a.facility_id = b.facility_id\n"
				+ "                             AND a.facility_id = r.facility_id\n"
				+ "                             AND a.service_code = ser.servicio\n"
				+ "                             AND ser.servicio = DECODE('" + servicio
				+ "','ALL',ser.servicio,substr('" + servicio + "',instr('" + servicio + "',ser.servicio\n"
				+ "                             ),length(ser.servicio) ) )\n"
				+ "                             AND ser.dest_type = c.dest_type\n"
				+ "                             AND b.dest_id = c.store\n"
				+ "                             AND a.route = r.route\n"
				+ "                             AND c.default_wh IN (\n" + "                                 SELECT\n"
				+ "                                     primary_vwh\n" + "                                 FROM\n"
				+ "                                     rdm100.xxfc_con_prog_wh_view\n"
				+ "                                 WHERE\n"
				+ "                                     primary_vwh IS NOT NULL\n" + "                             )\n"
				+ "                             AND substr(store_name10,1,5) ='" + plaza + "'\n"
				+ "                         GROUP BY\n" + "                             b.route\n"
				+ "                     )\n" + "                 ORDER BY\n" + "                     route ASC\n"
				+ "             )\n" + "         WHERE\n" + "             route = '" + ruta + "'\n" + "     )";
		return query;
	}

	/**
	 * Query de la visualizacion de la pantalla de cambios masivos buscando por ruta
	 * y plaza, visualiza los dias de llegada pt
	 * 
	 * @author 2000132763
	 * @param servicio
	 * @param plaza
	 * @param ruta
	 * @return
	 */
	public String queryRutaLlegadaPt(String servicio, String plaza, String ruta) {
		String query = "SELECT\n" + "     *\n" + " FROM\n" + "     (\n" + "         SELECT\n" + "             *\n"
				+ "         FROM\n" + "             (\n" + "                 SELECT\n" + rutaColumns + ","
				+ diaLlegadaPtColumns + "                 FROM\n" + "                     rdm100.xxfc_pmo_route\n"
				+ "                 WHERE\n" + "                     status = 'A'\n"
				+ "                     AND route IN (\n" + "                         SELECT\n"
				+ "                             b.route\n" + "                         FROM\n"
				+ "                             rdm100.carrier_service_route a,\n"
				+ "                             rdm100.route_dest b,\n"
				+ "                             rdm100.xxfc_store_v c,\n"
				+ "                             rdm100.route r,\n"
				+ "                             rdm100.xxfc_pmo_tdistrib_servicio ser\n"
				+ "                         WHERE\n" + "                             a.facility_id = b.facility_id\n"
				+ "                             AND a.facility_id = r.facility_id\n"
				+ "                             AND a.service_code = ser.servicio\n"
				+ "                             AND ser.servicio = DECODE('" + servicio
				+ "','ALL',ser.servicio,substr('" + servicio + "',instr('" + servicio + "',ser.servicio\n"
				+ "                             ),length(ser.servicio) ) )\n"
				+ "                             AND ser.dest_type = c.dest_type\n"
				+ "                             AND b.dest_id = c.store\n"
				+ "                             AND a.route = r.route\n"
				+ "                             AND c.default_wh IN (\n" + "                                 SELECT\n"
				+ "                                     primary_vwh\n" + "                                 FROM\n"
				+ "                                     rdm100.xxfc_con_prog_wh_view\n"
				+ "                                 WHERE\n"
				+ "                                     primary_vwh IS NOT NULL\n" + "                             )\n"
				+ "                             AND substr(store_name10,1,5) ='" + plaza + "'\n"
				+ "                         GROUP BY\n" + "                             b.route\n"
				+ "                     )\n" + "                 ORDER BY\n" + "                     route ASC\n"
				+ "             )\n" + "         WHERE\n" + "             route = '" + ruta + "'\n" + "     )";
		return query;
	}

	/**
	 * Query de la visualizacion de la pantalla de cambios masivos buscando por ruta
	 * y plaza, visualiza los dias de salida pt
	 * 
	 * @author 2000132763
	 * @param servicio
	 * @param plaza
	 * @param ruta
	 * @return
	 */
	public String queryRutaSalidaPt(String servicio, String plaza, String ruta) {
		String query = "SELECT\n" + "     *\n" + " FROM\n" + "     (\n" + "         SELECT\n" + "             *\n"
				+ "         FROM\n" + "             (\n" + "                 SELECT\n" + rutaColumns + ","
				+ diaSalidaPtColumns + "                 FROM\n" + "                     rdm100.xxfc_pmo_route\n"
				+ "                 WHERE\n" + "                     status = 'A'\n"
				+ "                     AND route IN (\n" + "                         SELECT\n"
				+ "                             b.route\n" + "                         FROM\n"
				+ "                             rdm100.carrier_service_route a,\n"
				+ "                             rdm100.route_dest b,\n"
				+ "                             rdm100.xxfc_store_v c,\n"
				+ "                             rdm100.route r,\n"
				+ "                             rdm100.xxfc_pmo_tdistrib_servicio ser\n"
				+ "                         WHERE\n" + "                             a.facility_id = b.facility_id\n"
				+ "                             AND a.facility_id = r.facility_id\n"
				+ "                             AND a.service_code = ser.servicio\n"
				+ "                             AND ser.servicio = DECODE('" + servicio
				+ "','ALL',ser.servicio,substr('" + servicio + "',instr('" + servicio + "',ser.servicio\n"
				+ "                             ),length(ser.servicio) ) )\n"
				+ "                             AND ser.dest_type = c.dest_type\n"
				+ "                             AND b.dest_id = c.store\n"
				+ "                             AND a.route = r.route\n"
				+ "                             AND c.default_wh IN (\n" + "                                 SELECT\n"
				+ "                                     primary_vwh\n" + "                                 FROM\n"
				+ "                                     rdm100.xxfc_con_prog_wh_view\n"
				+ "                                 WHERE\n"
				+ "                                     primary_vwh IS NOT NULL\n" + "                             )\n"
				+ "                             AND substr(store_name10,1,5) ='" + plaza + "'\n"
				+ "                         GROUP BY\n" + "                             b.route\n"
				+ "                     )\n" + "                 ORDER BY\n" + "                     route ASC\n"
				+ "             )\n" + "         WHERE\n" + "             route = '" + ruta + "'\n" + "     )";
		return query;
	}

	/*
	 * Queries para verificar el dia de generacion de pedido
	 */

	/**
	 * Query dia de generacion con columnas referentes al dia de generacion
	 * 
	 * @param servicio
	 * @param plaza
	 * @param diaGen
	 * @return
	 */
	public String queryDiaGenDiaGen(String servicio, String plaza, String diaGen) {
		String query = "SELECT" + "     *" + " FROM" + "     (" + "         SELECT" + "             *" + "         FROM"
				+ "             (" + "                 SELECT" + "                     tipo_distro,"
				+ "                     route," + "                     dia_gen_ped,"
				+ "                     dia_ini_entrega,"
				+ "                     TO_CHAR(h_ini_entrega,'HH24:MI')  AS h_ini_entrega,"
				+ "                     TO_CHAR(h_fin_entrega,'HH24:MI') AS h_fin_entrega" + "                 FROM"
				+ "                     rdm100.xxfc_pmo_route" + "                 WHERE"
				+ "                     status = 'A'" + "                     AND route IN ("
				+ "                         SELECT" + "                             b.route"
				+ "                         FROM" + "                             rdm100.carrier_service_route a,"
				+ "                             rdm100.route_dest b,"
				+ "                             rdm100.xxfc_store_v c," + "                             rdm100.route r,"
				+ "                             rdm100.xxfc_pmo_tdistrib_servicio ser"
				+ "                         WHERE" + "                             a.facility_id = b.facility_id"
				+ "                             AND a.facility_id = r.facility_id"
				+ "                             AND a.service_code = ser.servicio"
				+ "                             AND ser.servicio = DECODE('" + servicio
				+ "','ALL',ser.servicio,substr('" + servicio + "',instr('" + servicio + "',ser.servicio"
				+ "                             ),length(ser.servicio) ) )"
				+ "                             AND ser.dest_type = c.dest_type"
				+ "                             AND b.dest_id = c.store"
				+ "                             AND a.route = r.route"
				+ "                             AND c.default_wh IN (" + "                                 SELECT"
				+ "                                     primary_vwh" + "                                 FROM"
				+ "                                     rdm100.xxfc_con_prog_wh_view"
				+ "                                 WHERE"
				+ "                                     primary_vwh IS NOT NULL" + "                             )"
				+ "                             AND substr(store_name10,1,5) ='" + plaza + "'"
				+ "                         GROUP BY" + "                             b.route"
				+ "                     )" + "                 ORDER BY" + "                     route ASC"
				+ "             )" + "         WHERE" + "             DIA_GEN_PED = '" + diaGen + "'" + "     )";
		return query;

	}

	/**
	 * Query dia de generacion con columnas referentes al dia de programacion
	 * 
	 * @param servicio
	 * @param plaza
	 * @param diaGen
	 * @return
	 */
	public String queryDiaGenDiaProg(String servicio, String plaza, String diaGen) {
		String query = "SELECT" + "     *" + " FROM" + "     (" + "         SELECT" + "             *" + "         FROM"
				+ "             (" + "                 SELECT" + "                     dia_gen_ped,"
				+ "                     route," + "                     dia_prog,"
				+ "                     TO_CHAR(h_prog,'HH24:MI') AS h_prog," + "                     dia_ini_prod,"
				+ "                     TO_CHAR(h_ini_prod,'HH24:MI') AS h_ini_prod,"
				+ "                     dia_fin_prod,"
				+ "                     TO_CHAR(h_fin_prod,'HH24:MI') AS h_fin_prod" + "                 FROM"
				+ "                     rdm100.xxfc_pmo_route" + "                 WHERE"
				+ "                     status = 'A'" + "                     AND route IN ("
				+ "                         SELECT" + "                             b.route"
				+ "                         FROM" + "                             rdm100.carrier_service_route a,"
				+ "                             rdm100.route_dest b,"
				+ "                             rdm100.xxfc_store_v c," + "                             rdm100.route r,"
				+ "                             rdm100.xxfc_pmo_tdistrib_servicio ser"
				+ "                         WHERE" + "                             a.facility_id = b.facility_id"
				+ "                             AND a.facility_id = r.facility_id"
				+ "                             AND a.service_code = ser.servicio"
				+ "                             AND ser.servicio = DECODE('" + servicio
				+ "','ALL',ser.servicio,substr('" + servicio + "',instr('" + servicio + "',ser.servicio"
				+ "                             ),length(ser.servicio) ) )"
				+ "                             AND ser.dest_type = c.dest_type"
				+ "                             AND b.dest_id = c.store"
				+ "                             AND a.route = r.route"
				+ "                             AND c.default_wh IN (" + "                                 SELECT"
				+ "                                     primary_vwh" + "                                 FROM"
				+ "                                     rdm100.xxfc_con_prog_wh_view"
				+ "                                 WHERE"
				+ "                                     primary_vwh IS NOT NULL" + "                             )"
				+ "                             AND substr(store_name10,1,5) ='" + plaza + "'"
				+ "                         GROUP BY" + "                             b.route"
				+ "                     )" + "                 ORDER BY" + "                     route ASC"
				+ "             )" + "         WHERE" + "             DIA_GEN_PED = '" + diaGen + "'" + "     )";
		return query;
	}

	/**
	 * Query dia de generacion con columnas referentes al dia de inicio de
	 * verificacion
	 * 
	 * @param servicio
	 * @param plaza
	 * @param diaGen
	 * @return
	 */
	public String queryDiaGenDiaIniVerificacion(String servicio, String plaza, String diaGen) {
		String query = "SELECT" + "     *" + " FROM" + "     (" + "         SELECT" + "             *" + "         FROM"
				+ "             (" + "                 SELECT" + "                     route,"
				+ "                     dia_gen_ped," + "                     dia_ini_verificacion,"
				+ "                     TO_CHAR(h_ini_verificacion,'HH24:MI') AS h_ini_verificacion,"
				+ "                     dia_fin_verificacion,"
				+ "                     TO_CHAR(h_fin_verificacion,'HH24:MI') AS h_fin_verificacion"
				+ "                 FROM" + "                     rdm100.xxfc_pmo_route" + "                 WHERE"
				+ "                     status = 'A'" + "                     AND route IN ("
				+ "                         SELECT" + "                             b.route"
				+ "                         FROM" + "                             rdm100.carrier_service_route a,"
				+ "                             rdm100.route_dest b,"
				+ "                             rdm100.xxfc_store_v c," + "                             rdm100.route r,"
				+ "                             rdm100.xxfc_pmo_tdistrib_servicio ser"
				+ "                         WHERE" + "                             a.facility_id = b.facility_id"
				+ "                             AND a.facility_id = r.facility_id"
				+ "                             AND a.service_code = ser.servicio"
				+ "                             AND ser.servicio = DECODE('" + servicio
				+ "','ALL',ser.servicio,substr('" + servicio + "',instr('" + servicio + "',ser.servicio"
				+ "                             ),length(ser.servicio) ) )"
				+ "                             AND ser.dest_type = c.dest_type"
				+ "                             AND b.dest_id = c.store"
				+ "                             AND a.route = r.route"
				+ "                             AND c.default_wh IN (" + "                                 SELECT"
				+ "                                     primary_vwh" + "                                 FROM"
				+ "                                     rdm100.xxfc_con_prog_wh_view"
				+ "                                 WHERE"
				+ "                                     primary_vwh IS NOT NULL" + "                             )"
				+ "                             AND substr(store_name10,1,5) ='" + plaza + "'"
				+ "                         GROUP BY" + "                             b.route"
				+ "                     )" + "                 ORDER BY" + "                     route ASC"
				+ "             )" + "         WHERE" + "             DIA_GEN_PED = '" + diaGen + "'" + "     )";
		return query;
	}

	/**
	 * Query dia de generacion con columnas referentes al dia de inicio de carga
	 * 
	 * @param servicio
	 * @param plaza
	 * @param diaGen
	 * @return
	 */
	public String queryDiaGenDiaIniCarga(String servicio, String plaza, String diaGen) {
		String query = "SELECT" + "     *" + " FROM" + "     (" + "         SELECT" + "             *" + "         FROM"
				+ "             (" + "                 SELECT" + "                     route,"
				+ "                     dia_gen_ped," + "                     dia_ini_carga,"
				+ "                     TO_CHAR(h_ini_carga,'HH24:MI') AS h_ini_carga,"
				+ "                     dia_fin_carga,"
				+ "                     TO_CHAR(h_fin_carga,'HH24:MI') AS h_fin_carga" + "                 FROM"
				+ "                     rdm100.xxfc_pmo_route" + "                 WHERE"
				+ "                     status = 'A'" + "                     AND route IN ("
				+ "                         SELECT" + "                             b.route"
				+ "                         FROM" + "                             rdm100.carrier_service_route a,"
				+ "                             rdm100.route_dest b,"
				+ "                             rdm100.xxfc_store_v c," + "                             rdm100.route r,"
				+ "                             rdm100.xxfc_pmo_tdistrib_servicio ser"
				+ "                         WHERE" + "                             a.facility_id = b.facility_id"
				+ "                             AND a.facility_id = r.facility_id"
				+ "                             AND a.service_code = ser.servicio"
				+ "                             AND ser.servicio = DECODE('" + servicio
				+ "','ALL',ser.servicio,substr('" + servicio + "',instr('" + servicio + "',ser.servicio"
				+ "                             ),length(ser.servicio) ) )"
				+ "                             AND ser.dest_type = c.dest_type"
				+ "                             AND b.dest_id = c.store"
				+ "                             AND a.route = r.route"
				+ "                             AND c.default_wh IN (" + "                                 SELECT"
				+ "                                     primary_vwh" + "                                 FROM"
				+ "                                     rdm100.xxfc_con_prog_wh_view"
				+ "                                 WHERE"
				+ "                                     primary_vwh IS NOT NULL" + "                             )"
				+ "                             AND substr(store_name10,1,5) ='" + plaza + "'"
				+ "                         GROUP BY" + "                             b.route"
				+ "                     )" + "                 ORDER BY" + "                     route ASC"
				+ "             )" + "         WHERE" + "             DIA_GEN_PED = '" + diaGen + "'" + "     )";
		return query;
	}

	/**
	 * Query dia de generacion con columnas referentes al dia de llegada PT
	 * 
	 * @param servicio
	 * @param plaza
	 * @param diaGen
	 * @return
	 */
	public String queryDiaGenLlegadaPt(String servicio, String plaza, String diaGen) {
		String query = "SELECT" + "     *" + " FROM" + "     (" + "         SELECT" + "             *" + "         FROM"
				+ "             (" + "                 SELECT" + "                     route,"
				+ "                     dia_gen_ped," + "                     dia_ini_entrega,"
				+ "                     dia_llegada_pt,"
				+ "                     TO_CHAR(h_llegada_pt,'HH24:MI') AS h_llegada_pt" + "                 FROM"
				+ "                     rdm100.xxfc_pmo_route" + "                 WHERE"
				+ "                     status = 'A'" + "                     AND route IN ("
				+ "                         SELECT" + "                             b.route"
				+ "                         FROM" + "                             rdm100.carrier_service_route a,"
				+ "                             rdm100.route_dest b,"
				+ "                             rdm100.xxfc_store_v c," + "                             rdm100.route r,"
				+ "                             rdm100.xxfc_pmo_tdistrib_servicio ser"
				+ "                         WHERE" + "                             a.facility_id = b.facility_id"
				+ "                             AND a.facility_id = r.facility_id"
				+ "                             AND a.service_code = ser.servicio"
				+ "                             AND ser.servicio = DECODE('" + servicio
				+ "','ALL',ser.servicio,substr('" + servicio + "',instr('" + servicio + "',ser.servicio"
				+ "                             ),length(ser.servicio) ) )"
				+ "                             AND ser.dest_type = c.dest_type"
				+ "                             AND b.dest_id = c.store"
				+ "                             AND a.route = r.route"
				+ "                             AND c.default_wh IN (" + "                                 SELECT"
				+ "                                     primary_vwh" + "                                 FROM"
				+ "                                     rdm100.xxfc_con_prog_wh_view"
				+ "                                 WHERE"
				+ "                                     primary_vwh IS NOT NULL" + "                             )"
				+ "                             AND substr(store_name10,1,5) ='" + plaza + "'"
				+ "                         GROUP BY" + "                             b.route"
				+ "                     )" + "                 ORDER BY" + "                     route ASC"
				+ "             )" + "         WHERE" + "             DIA_GEN_PED = '" + diaGen + "'" + "     )";
		return query;
	}

	/**
	 * Query dia de generacion con columnas referentes al dia de salida PT
	 * 
	 * @param servicio
	 * @param plaza
	 * @param diaGen
	 * @return
	 */
	public String queryDiaGenSalidaPt(String servicio, String plaza, String diaGen) {
		String query = "SELECT" + "     *" + " FROM" + "     (" + "         SELECT" + "             *" + "         FROM"
				+ "             (" + "                 SELECT" + "                     route,"
				+ "                     dia_gen_ped," + "                     dia_ini_entrega,"
				+ "                     dia_salida_pt,"
				+ "                     TO_CHAR(h_salida_pt,'HH24:MI') AS h_salida_pt" + "                 FROM"
				+ "                     rdm100.xxfc_pmo_route" + "                 WHERE"
				+ "                     status = 'A'" + "                     AND route IN ("
				+ "                         SELECT" + "                             b.route"
				+ "                         FROM" + "                             rdm100.carrier_service_route a,"
				+ "                             rdm100.route_dest b,"
				+ "                             rdm100.xxfc_store_v c," + "                             rdm100.route r,"
				+ "                             rdm100.xxfc_pmo_tdistrib_servicio ser"
				+ "                         WHERE" + "                             a.facility_id = b.facility_id"
				+ "                             AND a.facility_id = r.facility_id"
				+ "                             AND a.service_code = ser.servicio"
				+ "                             AND ser.servicio = DECODE('" + servicio
				+ "','ALL',ser.servicio,substr('" + servicio + "',instr('" + servicio + "',ser.servicio"
				+ "                             ),length(ser.servicio) ) )"
				+ "                             AND ser.dest_type = c.dest_type"
				+ "                             AND b.dest_id = c.store"
				+ "                             AND a.route = r.route"
				+ "                             AND c.default_wh IN (" + "                                 SELECT"
				+ "                                     primary_vwh" + "                                 FROM"
				+ "                                     rdm100.xxfc_con_prog_wh_view"
				+ "                                 WHERE"
				+ "                                     primary_vwh IS NOT NULL" + "                             )"
				+ "                             AND substr(store_name10,1,5) ='" + plaza + "'"
				+ "                         GROUP BY" + "                             b.route"
				+ "                     )" + "                 ORDER BY" + "                     route ASC"
				+ "             )" + "         WHERE" + "             DIA_GEN_PED = '" + diaGen + "'" + "     )";
		return query;
	}

	/**
	 * Query dia de generacion con columnas referentes al dia de disposicion en uni
	 * cedis
	 * 
	 * @param servicio
	 * @param plaza
	 * @param diaGen
	 * @return
	 */
	public String queryDiaGenDispUniCedis(String servicio, String plaza, String diaGen) {
		String query = "SELECT" + "     *" + " FROM" + "     (" + "         SELECT" + "             *" + "         FROM"
				+ "             (" + "                 SELECT" + "                     route,"
				+ "                     dia_gen_ped," + "                     dia_disp_uni_cedis,"
				+ "                     TO_CHAR(h_disp_uni_cedis,'HH24:MI') AS h_disp_uni_cedis"
				+ "                 FROM" + "                     rdm100.xxfc_pmo_route" + "                 WHERE"
				+ "                     status = 'A'" + "                     AND route IN ("
				+ "                         SELECT" + "                             b.route"
				+ "                         FROM" + "                             rdm100.carrier_service_route a,"
				+ "                             rdm100.route_dest b,"
				+ "                             rdm100.xxfc_store_v c," + "                             rdm100.route r,"
				+ "                             rdm100.xxfc_pmo_tdistrib_servicio ser"
				+ "                         WHERE" + "                             a.facility_id = b.facility_id"
				+ "                             AND a.facility_id = r.facility_id"
				+ "                             AND a.service_code = ser.servicio"
				+ "                             AND ser.servicio = DECODE('" + servicio
				+ "','ALL',ser.servicio,substr('" + servicio + "',instr('" + servicio + "',ser.servicio"
				+ "                             ),length(ser.servicio) ) )"
				+ "                             AND ser.dest_type = c.dest_type"
				+ "                             AND b.dest_id = c.store"
				+ "                             AND a.route = r.route"
				+ "                             AND c.default_wh IN (" + "                                 SELECT"
				+ "                                     primary_vwh" + "                                 FROM"
				+ "                                     rdm100.xxfc_con_prog_wh_view"
				+ "                                 WHERE"
				+ "                                     primary_vwh IS NOT NULL" + "                             )"
				+ "                             AND substr(store_name10,1,5) ='" + plaza + "'"
				+ "                         GROUP BY" + "                             b.route"
				+ "                     )" + "                 ORDER BY" + "                     route ASC"
				+ "             )" + "         WHERE" + "             DIA_GEN_PED = '" + diaGen + "'" + "     )";
		return query;
	}

	/**
	 * Query dia de generacion con columnas referentes al dia de salida
	 * 
	 * @param servicio
	 * @param plaza
	 * @param diaGen
	 * @return
	 */
	public String queryDiaGendiaSalida(String servicio, String plaza, String diaGen) {
		String query = "SELECT" + "     *" + " FROM" + "     (" + "         SELECT" + "             *" + "         FROM"
				+ "             (" + "                 SELECT" + "                     route,"
				+ "                     dia_gen_ped," + "                     dia_salida,"
				+ "                     TO_CHAR(h_salida,'HH24:MI') AS h_salida" + "                 FROM"
				+ "                     rdm100.xxfc_pmo_route" + "                 WHERE"
				+ "                     status = 'A'" + "                     AND route IN ("
				+ "                         SELECT" + "                             b.route"
				+ "                         FROM" + "                             rdm100.carrier_service_route a,"
				+ "                             rdm100.route_dest b,"
				+ "                             rdm100.xxfc_store_v c," + "                             rdm100.route r,"
				+ "                             rdm100.xxfc_pmo_tdistrib_servicio ser"
				+ "                         WHERE" + "                             a.facility_id = b.facility_id"
				+ "                             AND a.facility_id = r.facility_id"
				+ "                             AND a.service_code = ser.servicio"
				+ "                             AND ser.servicio = DECODE('" + servicio
				+ "','ALL',ser.servicio,substr('" + servicio + "',instr('" + servicio + "',ser.servicio"
				+ "                             ),length(ser.servicio) ) )"
				+ "                             AND ser.dest_type = c.dest_type"
				+ "                             AND b.dest_id = c.store"
				+ "                             AND a.route = r.route"
				+ "                             AND c.default_wh IN (" + "                                 SELECT"
				+ "                                     primary_vwh" + "                                 FROM"
				+ "                                     rdm100.xxfc_con_prog_wh_view"
				+ "                                 WHERE"
				+ "                                     primary_vwh IS NOT NULL" + "                             )"
				+ "                             AND substr(store_name10,1,5) ='" + plaza + "'"
				+ "                         GROUP BY" + "                             b.route"
				+ "                     )" + "                 ORDER BY" + "                     route ASC"
				+ "             )" + "         WHERE" + "             DIA_GEN_PED = '" + diaGen + "'" + "     )";
		return query;
	}

	/*
	 * Queries para verificar el dia de ventana de entrega //INICIO
	 */

	/**
	 * Query de los resultados mostrados en la pantalla de cambios masivos, buscando
	 * por servicio, plaza y dia de entrega
	 * 
	 * @param servicio
	 * @param plaza
	 * @param diaEnt
	 * @return
	 */
	public String queryDiaEnt(String servicio, String plaza, String diaEnt) {
		String query = "SELECT" + "     *" + " FROM" + "     (" + "         SELECT" + "             *" + "         FROM"
				+ "             (" + "                 SELECT" + "                     tipo_distro,"
				+ "                     route," + "                     dia_gen_ped,"
				+ "                     dia_ini_entrega,"
				+ "                     TO_CHAR(h_ini_entrega,'HH24:MI') AS h_ini_entrega,"
				+ "                     TO_CHAR(h_fin_entrega,'HH24:MI') AS h_fin_entrega,"
				+ "                     dia_prog," + "                     TO_CHAR(h_prog,'HH24:MI') AS h_prog,"
				+ "                     dia_ini_prod,"
				+ "                     TO_CHAR(h_ini_prod,'HH24:MI') AS h_ini_prod,"
				+ "                     dia_fin_prod,"
				+ "                     TO_CHAR(h_fin_prod,'HH24:MI') AS h_fin_prod,"
				+ "                     dia_ini_verificacion,"
				+ "                     TO_CHAR(h_ini_verificacion,'HH24:MI') AS h_ini_verificacion,"
				+ "                     dia_fin_verificacion,"
				+ "                     TO_CHAR(h_fin_verificacion,'HH24:MI') AS h_fin_verificacion,"
				+ "                     dia_ini_carga,"
				+ "                     TO_CHAR(h_ini_carga,'HH24:MI') AS h_ini_carga,"
				+ "                     dia_fin_carga,"
				+ "                     TO_CHAR(h_fin_carga,'HH24:MI') AS h_fin_carga,"
				+ "                     dia_salida," + "                     TO_CHAR(h_salida,'HH24:MI') AS h_salida,"
				+ "                     dia_disp_uni_cedis,"
				+ "                     TO_CHAR(h_disp_uni_cedis,'HH24:MI') AS h_disp_uni_cedis,"
				+ "                     dia_llegada_pt,"
				+ "                     TO_CHAR(h_llegada_pt,'HH24:MI') AS h_llegada_pt,"
				+ "                     dia_salida_pt,"
				+ "                     TO_CHAR(h_salida_pt,'HH24:MI') AS h_salida_pt" + "                 FROM"
				+ "                     rdm100.xxfc_pmo_route" + "                 WHERE"
				+ "                     status = 'A'" + "                     AND route IN ("
				+ "                         SELECT" + "                             b.route"
				+ "                         FROM" + "                             rdm100.carrier_service_route a,"
				+ "                             rdm100.route_dest b,"
				+ "                             rdm100.xxfc_store_v c," + "                             rdm100.route r,"
				+ "                             rdm100.xxfc_pmo_tdistrib_servicio ser"
				+ "                         WHERE" + "                             a.facility_id = b.facility_id"
				+ "                             AND a.facility_id = r.facility_id"
				+ "                             AND a.service_code = ser.servicio"
				+ "                             AND ser.servicio = DECODE('" + servicio
				+ "','ALL',ser.servicio,substr('" + servicio + "',instr('" + servicio + "',ser.servicio"
				+ "                             ),length(ser.servicio) ) )"
				+ "                             AND ser.dest_type = c.dest_type"
				+ "                             AND b.dest_id = c.store"
				+ "                             AND a.route = r.route"
				+ "                             AND c.default_wh IN (" + "                                 SELECT"
				+ "                                     primary_vwh" + "                                 FROM"
				+ "                                     rdm100.xxfc_con_prog_wh_view"
				+ "                                 WHERE"
				+ "                                     primary_vwh IS NOT NULL" + "                             )"
				+ "                             AND substr(store_name10,1,5) ='" + plaza + "'"
				+ "                         GROUP BY" + "                             b.route"
				+ "                     )" + "                 ORDER BY" + "                     route ASC"
				+ "             )" + "         WHERE" + "             DIA_INI_ENTREGA = '" + diaEnt + "'" + "     )";
		return query;
	}

	/**
	 * Query dia de entrega con columnas referentes al dia de generacion
	 * 
	 * @param servicio
	 * @param plaza
	 * @param diaEnt
	 * @return
	 */
	public String queryDiaEntDiaGen(String servicio, String plaza, String diaEnt) {
		String query = "SELECT" + "     *" + " FROM" + "     (" + "         SELECT" + "             *" + "         FROM"
				+ "             (" + "                 SELECT" + "                     tipo_distro,"
				+ "                     route," + "                     dia_gen_ped,"
				+ "                     dia_ini_entrega,"
				+ "                     TO_CHAR(h_ini_entrega,'HH24:MI') AS h_ini_entrega,"
				+ "                     TO_CHAR(h_fin_entrega,'HH24:MI') AS h_fin_entrega" + "                 FROM"
				+ "                     rdm100.xxfc_pmo_route" + "                 WHERE"
				+ "                     status = 'A'" + "                     AND route IN ("
				+ "                         SELECT" + "                             b.route"
				+ "                         FROM" + "                             rdm100.carrier_service_route a,"
				+ "                             rdm100.route_dest b,"
				+ "                             rdm100.xxfc_store_v c," + "                             rdm100.route r,"
				+ "                             rdm100.xxfc_pmo_tdistrib_servicio ser"
				+ "                         WHERE" + "                             a.facility_id = b.facility_id"
				+ "                             AND a.facility_id = r.facility_id"
				+ "                             AND a.service_code = ser.servicio"
				+ "                             AND ser.servicio = DECODE('" + servicio
				+ "','ALL',ser.servicio,substr('" + servicio + "',instr('" + servicio + "',ser.servicio"
				+ "                             ),length(ser.servicio) ) )"
				+ "                             AND ser.dest_type = c.dest_type"
				+ "                             AND b.dest_id = c.store"
				+ "                             AND a.route = r.route"
				+ "                             AND c.default_wh IN (" + "                                 SELECT"
				+ "                                     primary_vwh" + "                                 FROM"
				+ "                                     rdm100.xxfc_con_prog_wh_view"
				+ "                                 WHERE"
				+ "                                     primary_vwh IS NOT NULL" + "                             )"
				+ "                             AND substr(store_name10,1,5) ='" + plaza + "'"
				+ "                         GROUP BY" + "                             b.route"
				+ "                     )" + "                 ORDER BY" + "                     route ASC"
				+ "             )" + "         WHERE" + "             DIA_INI_ENTREGA = '" + diaEnt + "'" + "     )";
		return query;

	}

	/**
	 * Query dia de entrega con columnas referentes al dia de programacion
	 * 
	 * @param servicio
	 * @param plaza
	 * @param diaEnt
	 * @return
	 */
	public String queryDiaEntDiaProg(String servicio, String plaza, String diaEnt) {
		String query = "SELECT" + "     *" + " FROM" + "     (" + "         SELECT" + "             *" + "         FROM"
				+ "             (" + "                 SELECT" + "                     route,"
				+ "                     dia_ini_entrega," + "                     dia_prog,"
				+ "                     TO_CHAR(h_prog,'HH24:MI') AS h_prog," + "                     dia_ini_prod,"
				+ "                     TO_CHAR(h_ini_prod,'HH24:MI') AS h_ini_prod,"
				+ "                     dia_fin_prod,"
				+ "                     TO_CHAR(h_fin_prod,'HH24:MI') AS h_fin_prod" + "                 FROM"
				+ "                     rdm100.xxfc_pmo_route" + "                 WHERE"
				+ "                     status = 'A'" + "                     AND route IN ("
				+ "                         SELECT" + "                             b.route"
				+ "                         FROM" + "                             rdm100.carrier_service_route a,"
				+ "                             rdm100.route_dest b,"
				+ "                             rdm100.xxfc_store_v c," + "                             rdm100.route r,"
				+ "                             rdm100.xxfc_pmo_tdistrib_servicio ser"
				+ "                         WHERE" + "                             a.facility_id = b.facility_id"
				+ "                             AND a.facility_id = r.facility_id"
				+ "                             AND a.service_code = ser.servicio"
				+ "                             AND ser.servicio = DECODE('" + servicio
				+ "','ALL',ser.servicio,substr('" + servicio + "',instr('" + servicio + "',ser.servicio"
				+ "                             ),length(ser.servicio) ) )"
				+ "                             AND ser.dest_type = c.dest_type"
				+ "                             AND b.dest_id = c.store"
				+ "                             AND a.route = r.route"
				+ "                             AND c.default_wh IN (" + "                                 SELECT"
				+ "                                     primary_vwh" + "                                 FROM"
				+ "                                     rdm100.xxfc_con_prog_wh_view"
				+ "                                 WHERE"
				+ "                                     primary_vwh IS NOT NULL" + "                             )"
				+ "                             AND substr(store_name10,1,5) ='" + plaza + "'"
				+ "                         GROUP BY" + "                             b.route"
				+ "                     )" + "                 ORDER BY" + "                     route ASC"
				+ "             )" + "         WHERE" + "             DIA_INI_ENTREGA = '" + diaEnt + "'" + "     )";
		return query;
	}

	/**
	 * Query dia de entrega con columnas referentes al dia de inicio de verificacion
	 * 
	 * @param servicio
	 * @param plaza
	 * @param diaEnt
	 * @return
	 */
	public String queryDiaEntDiaIniVerificacion(String servicio, String plaza, String diaEnt) {
		String query = "SELECT" + "     *" + " FROM" + "     (" + "         SELECT" + "             *" + "         FROM"
				+ "             (" + "                 SELECT" + "                     route,"
				+ "                     dia_ini_entrega," + "                     dia_ini_verificacion,"
				+ "                     TO_CHAR(h_ini_verificacion,'HH24:MI') AS h_ini_verificacion,"
				+ "                     dia_fin_verificacion,"
				+ "                     TO_CHAR(h_fin_verificacion,'HH24:MI') AS h_fin_verificacion"
				+ "                 FROM" + "                     rdm100.xxfc_pmo_route" + "                 WHERE"
				+ "                     status = 'A'" + "                     AND route IN ("
				+ "                         SELECT" + "                             b.route"
				+ "                         FROM" + "                             rdm100.carrier_service_route a,"
				+ "                             rdm100.route_dest b,"
				+ "                             rdm100.xxfc_store_v c," + "                             rdm100.route r,"
				+ "                             rdm100.xxfc_pmo_tdistrib_servicio ser"
				+ "                         WHERE" + "                             a.facility_id = b.facility_id"
				+ "                             AND a.facility_id = r.facility_id"
				+ "                             AND a.service_code = ser.servicio"
				+ "                             AND ser.servicio = DECODE('" + servicio
				+ "','ALL',ser.servicio,substr('" + servicio + "',instr('" + servicio + "',ser.servicio"
				+ "                             ),length(ser.servicio) ) )"
				+ "                             AND ser.dest_type = c.dest_type"
				+ "                             AND b.dest_id = c.store"
				+ "                             AND a.route = r.route"
				+ "                             AND c.default_wh IN (" + "                                 SELECT"
				+ "                                     primary_vwh" + "                                 FROM"
				+ "                                     rdm100.xxfc_con_prog_wh_view"
				+ "                                 WHERE"
				+ "                                     primary_vwh IS NOT NULL" + "                             )"
				+ "                             AND substr(store_name10,1,5) ='" + plaza + "'"
				+ "                         GROUP BY" + "                             b.route"
				+ "                     )" + "                 ORDER BY" + "                     route ASC"
				+ "             )" + "         WHERE" + "             DIA_INI_ENTREGA = '" + diaEnt + "'" + "     )";
		return query;
	}

	/**
	 * Query dia de entrega con columnas referentes al dia de inicio de carga
	 * 
	 * @param servicio
	 * @param plaza
	 * @param diaEnt
	 * @return
	 */
	public String queryDiaEntDiaIniCarga(String servicio, String plaza, String diaEnt) {
		String query = "SELECT" + "     *" + " FROM" + "     (" + "         SELECT" + "             *" + "         FROM"
				+ "             (" + "                 SELECT" + "                     route,"
				+ "                     dia_ini_entrega," + "                     dia_ini_carga,"
				+ "                     TO_CHAR(h_ini_carga,'HH24:MI') AS h_ini_carga,"
				+ "                     dia_fin_carga,"
				+ "                     TO_CHAR(h_fin_carga,'HH24:MI') AS h_fin_carga" + "                 FROM"
				+ "                     rdm100.xxfc_pmo_route" + "                 WHERE"
				+ "                     status = 'A'" + "                     AND route IN ("
				+ "                         SELECT" + "                             b.route"
				+ "                         FROM" + "                             rdm100.carrier_service_route a,"
				+ "                             rdm100.route_dest b,"
				+ "                             rdm100.xxfc_store_v c," + "                             rdm100.route r,"
				+ "                             rdm100.xxfc_pmo_tdistrib_servicio ser"
				+ "                         WHERE" + "                             a.facility_id = b.facility_id"
				+ "                             AND a.facility_id = r.facility_id"
				+ "                             AND a.service_code = ser.servicio"
				+ "                             AND ser.servicio = DECODE('" + servicio
				+ "','ALL',ser.servicio,substr('" + servicio + "',instr('" + servicio + "',ser.servicio"
				+ "                             ),length(ser.servicio) ) )"
				+ "                             AND ser.dest_type = c.dest_type"
				+ "                             AND b.dest_id = c.store"
				+ "                             AND a.route = r.route"
				+ "                             AND c.default_wh IN (" + "                                 SELECT"
				+ "                                     primary_vwh" + "                                 FROM"
				+ "                                     rdm100.xxfc_con_prog_wh_view"
				+ "                                 WHERE"
				+ "                                     primary_vwh IS NOT NULL" + "                             )"
				+ "                             AND substr(store_name10,1,5) ='" + plaza + "'"
				+ "                         GROUP BY" + "                             b.route"
				+ "                     )" + "                 ORDER BY" + "                     route ASC"
				+ "             )" + "         WHERE" + "             DIA_INI_ENTREGA = '" + diaEnt + "'" + "     )";
		return query;
	}

	/**
	 * Query dia de entrega con columnas referentes al dia de llegada PT
	 * 
	 * @param servicio
	 * @param plaza
	 * @param diaEnt
	 * @return
	 */
	public String queryDiaEntLlegadaPt(String servicio, String plaza, String diaEnt) {
		return "SELECT" + "     *" + " FROM" + "     (" + "         SELECT" + "             *" + "         FROM"
				+ "             (" + "                 SELECT" + "                     route,"
				+ "                     dia_ini_entrega," + "                     dia_llegada_pt,"
				+ "                     TO_CHAR(h_llegada_pt,'HH24:MI') AS h_llegada_pt" + "                 FROM"
				+ "                     rdm100.xxfc_pmo_route" + "                 WHERE"
				+ "                     status = 'A'" + "                     AND route IN ("
				+ "                         SELECT" + "                             b.route"
				+ "                         FROM" + "                             rdm100.carrier_service_route a,"
				+ "                             rdm100.route_dest b,"
				+ "                             rdm100.xxfc_store_v c," + "                             rdm100.route r,"
				+ "                             rdm100.xxfc_pmo_tdistrib_servicio ser"
				+ "                         WHERE" + "                             a.facility_id = b.facility_id"
				+ "                             AND a.facility_id = r.facility_id"
				+ "                             AND a.service_code = ser.servicio"
				+ "                             AND ser.servicio = DECODE('" + servicio
				+ "','ALL',ser.servicio,substr('" + servicio + "',instr('" + servicio + "',ser.servicio"
				+ "                             ),length(ser.servicio) ) )"
				+ "                             AND ser.dest_type = c.dest_type"
				+ "                             AND b.dest_id = c.store"
				+ "                             AND a.route = r.route"
				+ "                             AND c.default_wh IN (" + "                                 SELECT"
				+ "                                     primary_vwh" + "                                 FROM"
				+ "                                     rdm100.xxfc_con_prog_wh_view"
				+ "                                 WHERE"
				+ "                                     primary_vwh IS NOT NULL" + "                             )"
				+ "                             AND substr(store_name10,1,5) ='" + plaza + "'"
				+ "                         GROUP BY" + "                             b.route"
				+ "                     )" + "                 ORDER BY" + "                     route ASC"
				+ "             )" + "         WHERE" + "             DIA_INI_ENTREGA = '" + diaEnt + "'" + "     )";
	}

	/**
	 * Query dia de entrega con columnas referentes al dia de salida PT
	 * 
	 * @param servicio
	 * @param plaza
	 * @param diaEnt
	 * @return
	 */
	public String queryDiaEntSalidaPt(String servicio, String plaza, String diaEnt) {
		String query = "SELECT" + " *" + "FROM" + "    (" + "        SELECT" + "            *" + "        FROM"
				+ "            (" + "                SELECT" + "                    route,"
				+ "                    dia_ini_entrega," + "                    dia_salida_pt,"
				+ "                    TO_CHAR(h_salida_pt,'HH24:MI') AS h_salida_pt" + "                FROM"
				+ "                    rdm100.xxfc_pmo_route" + "                WHERE"
				+ "                    status = 'A'" + "                    AND route IN ("
				+ "                        SELECT" + "                            b.route"
				+ "                        FROM" + "                            rdm100.carrier_service_route a,"
				+ "                            rdm100.route_dest b,"
				+ "                            rdm100.xxfc_store_v c," + "                            rdm100.route r,"
				+ "                            rdm100.xxfc_pmo_tdistrib_servicio ser" + "                        WHERE"
				+ "                            a.facility_id = b.facility_id"
				+ "                            AND a.facility_id = r.facility_id"
				+ "                            AND a.service_code = ser.servicio"
				+ "                            AND ser.servicio = DECODE('" + servicio + "','ALL',ser.servicio,substr('"
				+ servicio + "',instr('" + servicio + "',ser.servicio)"
				+ "                           ,length(ser.servicio) ) )"
				+ "                            AND ser.dest_type = c.dest_type"
				+ "                            AND b.dest_id = c.store"
				+ "                            AND a.route = r.route"
				+ "                            AND c.default_wh IN (" + "                                SELECT"
				+ "                                    primary_vwh" + "                                FROM"
				+ "                                    rdm100.xxfc_con_prog_wh_view"
				+ "                                WHERE"
				+ "                                    primary_vwh IS NOT NULL" + "                            )"
				+ "                            AND substr(store_name10,1,5) ='" + plaza + "'"
				+ "                        GROUP BY" + "                            b.route" + "                    )"
				+ "                ORDER BY" + "                    route ASC" + "            )" + "        WHERE"
				+ "            dia_ini_entrega ='" + diaEnt + "'" + "    )";
		return query;
	}

	/**
	 * Query dia de entrega con columnas referentes al dia de disposicion en uni
	 * cedis
	 * 
	 * @param servicio
	 * @param plaza
	 * @param diaEnt
	 * @return
	 */
	public String queryDiaEntDispUniCedis(String servicio, String plaza, String diaEnt) {
		String query = "SELECT" + "     *" + " FROM" + "     (" + "         SELECT" + "             *" + "         FROM"
				+ "             (" + "                 SELECT" + "                     route,"
				+ "                     dia_ini_entrega," + "                     dia_disp_uni_cedis,"
				+ "                     TO_CHAR(h_disp_uni_cedis,'HH24:MI') AS h_disp_uni_cedis"
				+ "                 FROM" + "                     rdm100.xxfc_pmo_route" + "                 WHERE"
				+ "                     status = 'A'" + "                     AND route IN ("
				+ "                         SELECT" + "                             b.route"
				+ "                         FROM" + "                             rdm100.carrier_service_route a,"
				+ "                             rdm100.route_dest b,"
				+ "                             rdm100.xxfc_store_v c," + "                             rdm100.route r,"
				+ "                             rdm100.xxfc_pmo_tdistrib_servicio ser"
				+ "                         WHERE" + "                             a.facility_id = b.facility_id"
				+ "                             AND a.facility_id = r.facility_id"
				+ "                             AND a.service_code = ser.servicio"
				+ "                             AND ser.servicio = DECODE('" + servicio
				+ "','ALL',ser.servicio,substr('" + servicio + "',instr('" + servicio + "',ser.servicio"
				+ "                             ),length(ser.servicio) ) )"
				+ "                             AND ser.dest_type = c.dest_type"
				+ "                             AND b.dest_id = c.store"
				+ "                             AND a.route = r.route"
				+ "                             AND c.default_wh IN (" + "                                 SELECT"
				+ "                                     primary_vwh" + "                                 FROM"
				+ "                                     rdm100.xxfc_con_prog_wh_view"
				+ "                                 WHERE"
				+ "                                     primary_vwh IS NOT NULL" + "                             )"
				+ "                             AND substr(store_name10,1,5) ='" + plaza + "'"
				+ "                         GROUP BY" + "                             b.route"
				+ "                     )" + "                 ORDER BY" + "                     route ASC"
				+ "             )" + "         WHERE" + "             DIA_INI_ENTREGA = '" + diaEnt + "'" + "     )";
		return query;
	}

	/**
	 * Query dia de entrega con columnas referentes al dia de salida
	 * 
	 * @param servicio
	 * @param plaza
	 * @param diaEnt
	 * @return
	 */
	public String queryDiaEntdiaSalida(String servicio, String plaza, String diaEnt) {
		String query = "SELECT" + "     *" + " FROM" + "     (" + "         SELECT" + "             *" + "         FROM"
				+ "             (" + "                 SELECT" + "                     route,"
				+ "                     dia_ini_entrega," + "                     dia_salida,"
				+ "                     TO_CHAR(h_salida,'HH24:MI') AS h_salida" + "                 FROM"
				+ "                     rdm100.xxfc_pmo_route" + "                 WHERE"
				+ "                     status = 'A'" + "                     AND route IN ("
				+ "                         SELECT" + "                             b.route"
				+ "                         FROM" + "                             rdm100.carrier_service_route a,"
				+ "                             rdm100.route_dest b,"
				+ "                             rdm100.xxfc_store_v c," + "                             rdm100.route r,"
				+ "                             rdm100.xxfc_pmo_tdistrib_servicio ser"
				+ "                         WHERE" + "                             a.facility_id = b.facility_id"
				+ "                             AND a.facility_id = r.facility_id"
				+ "                             AND a.service_code = ser.servicio"
				+ "                             AND ser.servicio = DECODE('" + servicio
				+ "','ALL',ser.servicio,substr('" + servicio + "',instr('" + servicio + "',ser.servicio"
				+ "                             ),length(ser.servicio) ) )"
				+ "                             AND ser.dest_type = c.dest_type"
				+ "                             AND b.dest_id = c.store"
				+ "                             AND a.route = r.route"
				+ "                             AND c.default_wh IN (" + "                                 SELECT"
				+ "                                     primary_vwh" + "                                 FROM"
				+ "                                     rdm100.xxfc_con_prog_wh_view"
				+ "                                 WHERE"
				+ "                                     primary_vwh IS NOT NULL" + "                             )"
				+ "                             AND substr(store_name10,1,5) ='" + plaza + "'"
				+ "                         GROUP BY" + "                             b.route"
				+ "                     )" + "                 ORDER BY" + "                     route ASC"
				+ "             )" + "         WHERE" + "             DIA_INI_ENTREGA = '" + diaEnt + "'" + "     )";
		return query;
	}

	/**
	 * Se utiliza para correr los diferentes queries que se utilizan para verificar
	 * la informacion de la consulta por "dia de ventana de entrega"
	 *
	 * @param dbConecta Conexión a la base de datos
	 * @param servicio  Servicio a consultar, generalmente con el valor "ALL"
	 * @param plaza     Plaza que se desea consultar
	 * @param diaEnt    Dia de ventana de entrega que se desea consultar, acepta
	 *                  valores 1,2,3,4,5,6,7 que serian los equivalentes a DO, LU,
	 *                  MA, MI, JU, VI ,SA
	 * @throws SQLException Error de conexión o algun parametro que no se envia de
	 *                      manera correcta o en el formato adecuado
	 */
	public void diaEntQueries(SQLUtil dbConecta, String servicio, String plaza, String diaEnt) throws SQLException {

		SQLResult queryDiaEntDiaProgResult = executeQuery(dbConecta, queryDiaEntDiaProg(servicio, plaza, diaEnt));
		logger.info(String.valueOf(queryDiaEntDiaProgResult.isEmpty()));
		testCase.addQueryEvidenceCurrentStep(queryDiaEntDiaProgResult);

		SQLResult queryDiaEntDiaIniVerificacionResult = executeQuery(dbConecta,
				queryDiaEntDiaIniVerificacion(servicio, plaza, diaEnt));
		logger.info(String.valueOf(queryDiaEntDiaIniVerificacionResult.isEmpty()));
		testCase.addQueryEvidenceCurrentStep(queryDiaEntDiaIniVerificacionResult);

		SQLResult queryDiaEntDiaIniCargaResult = executeQuery(dbConecta,
				queryDiaEntDiaIniCarga(servicio, plaza, diaEnt));
		logger.info(String.valueOf(queryDiaEntDiaIniCargaResult.isEmpty()));
		testCase.addQueryEvidenceCurrentStep(queryDiaEntDiaIniCargaResult);

		SQLResult queryDiaEntLlegadaPtResult = executeQuery(dbConecta, queryDiaEntLlegadaPt(servicio, plaza, diaEnt));
		logger.info(String.valueOf(queryDiaEntLlegadaPtResult.isEmpty()));
		testCase.addQueryEvidenceCurrentStep(queryDiaEntLlegadaPtResult);

		SQLResult queryDiaEntSalidaPtResult = executeQuery(dbConecta, queryDiaEntSalidaPt(servicio, plaza, diaEnt));
		logger.info(String.valueOf(queryDiaEntSalidaPtResult.isEmpty()));
		testCase.addQueryEvidenceCurrentStep(queryDiaEntSalidaPtResult);

		SQLResult queryDiaEntDispUniCedisResult = executeQuery(dbConecta,
				queryDiaEntDispUniCedis(servicio, plaza, diaEnt));
		logger.info(String.valueOf(queryDiaEntDispUniCedisResult.isEmpty()));
		testCase.addQueryEvidenceCurrentStep(queryDiaEntDispUniCedisResult);

		SQLResult queryDiaEntdiaSalidaResult = executeQuery(dbConecta, queryDiaEntdiaSalida(servicio, plaza, diaEnt));
		logger.info(String.valueOf(queryDiaEntdiaSalidaResult.isEmpty()));
		testCase.addQueryEvidenceCurrentStep(queryDiaEntdiaSalidaResult);

	}

	/*
	 * Queries para verificar el dia de ventana de entrega //FIN
	 */

	/**
	 * Se utiliza para correr los diferentes queries que se utilizan para verificar
	 * la informacion de la consulta por "dia de generación pedido"
	 *
	 * @param dbConecta Conexión a la base de datos
	 * @param servicio  Servicio a consultar, generalmente con el valor "ALL"
	 * @param plaza     Plaza que se desea consultar
	 * @param diaGen    Dia de ventana de entrega que se desea consultar, acepta
	 *                  valores 1,2,3,4,5,6,7 que serian los equivalentes a DO, LU,
	 *                  MA, MI, JU, VI ,SA
	 * @throws SQLException Error de conexión o algun parametro que no se envia de
	 *                      manera correcta o en el formato adecuado
	 */
	public void diaGenQueries(SQLUtil dbConecta, String servicio, String plaza, String diaGen) throws SQLException {

		SQLResult a = executeQuery(dbConecta, queryDiaGenDiaGen(servicio, plaza, diaGen));
		logger.info(String.valueOf(a.isEmpty()));
		testCase.addQueryEvidenceCurrentStep(a);

		SQLResult b = executeQuery(dbConecta, queryDiaGenDiaProg(servicio, plaza, diaGen));
		logger.info(String.valueOf(b.isEmpty()));
		testCase.addQueryEvidenceCurrentStep(b);

		SQLResult c = executeQuery(dbConecta, queryDiaGenDiaIniVerificacion(servicio, plaza, diaGen));
		logger.info(String.valueOf(c.isEmpty()));
		testCase.addQueryEvidenceCurrentStep(c);

		SQLResult d = executeQuery(dbConecta, queryDiaGenDiaIniCarga(servicio, plaza, diaGen));
		logger.info(String.valueOf(d.isEmpty()));
		testCase.addQueryEvidenceCurrentStep(d);

		SQLResult e = executeQuery(dbConecta, queryDiaGenLlegadaPt(servicio, plaza, diaGen));
		logger.info(String.valueOf(e.isEmpty()));
		testCase.addQueryEvidenceCurrentStep(e);

		SQLResult f = executeQuery(dbConecta, queryDiaGenSalidaPt(servicio, plaza, diaGen));
		logger.info(String.valueOf(f.isEmpty()));
		testCase.addQueryEvidenceCurrentStep(f);

		SQLResult g = executeQuery(dbConecta, queryDiaGenDispUniCedis(servicio, plaza, diaGen));
		logger.info(String.valueOf(g.isEmpty()));
		testCase.addQueryEvidenceCurrentStep(g);

		SQLResult h = executeQuery(dbConecta, queryDiaGendiaSalida(servicio, plaza, diaGen));
		logger.info(String.valueOf(h.isEmpty()));
		testCase.addQueryEvidenceCurrentStep(h);

	}

	/**
	 * Query utilizado para filtrar por dia de generacion y especificando el numero
	 * de resultados deseados
	 * 
	 * @author 2000132763
	 * @param column
	 * @param plaza
	 * @param diaGenPed
	 * @param limit
	 * @return
	 */
	public String diaGenQueryLimit(String column, String plaza, String diaGenPed, String limit) {
		String query = "SELECT\n" + "    *\n" + "FROM\n" + "    (\n" + "        SELECT\n" + "            '" + column
				+ "'\n" + "        FROM\n" + "            (\n" + "                SELECT\n" + "                    *\n"
				+ "                FROM\n" + "                    (\n" + "                        SELECT\n"
				+ "                            tipo_distro,\n" + "                            route,\n"
				+ "                            dia_gen_ped,\n" + "                            dia_ini_entrega,\n"
				+ "                            TO_CHAR(h_ini_entrega,'HH24:MI') AS h_ini_entrega,\n"
				+ "                            TO_CHAR(h_fin_entrega,'HH24:MI') AS h_fin_entrega\n"
				+ "                        FROM\n" + "                            rdm100.xxfc_pmo_route\n"
				+ "                        WHERE\n" + "                            status = 'A'\n"
				+ "                            AND route IN (\n" + "                                SELECT\n"
				+ "                                    b.route\n" + "                                FROM\n"
				+ "                                    rdm100.carrier_service_route a,\n"
				+ "                                    rdm100.route_dest b,\n"
				+ "                                    rdm100.xxfc_store_v c,\n"
				+ "                                    rdm100.route r,\n"
				+ "                                    rdm100.xxfc_pmo_tdistrib_servicio ser\n"
				+ "                                WHERE\n"
				+ "                                    a.facility_id = b.facility_id\n"
				+ "                                    AND a.facility_id = r.facility_id\n"
				+ "                                    AND a.service_code = ser.servicio\n"
				+ "                                    AND ser.servicio = DECODE('ALL','ALL',ser.servicio,substr('ALL',instr('ALL',ser.servicio),length\n"
				+ "                                    (ser.servicio) ) )\n"
				+ "                                    AND ser.dest_type = c.dest_type\n"
				+ "                                    AND b.dest_id = c.store\n"
				+ "                                    AND a.route = r.route\n"
				+ "                                    AND c.default_wh IN (\n"
				+ "                                        SELECT\n"
				+ "                                            primary_vwh\n"
				+ "                                        FROM\n"
				+ "                                            rdm100.xxfc_con_prog_wh_view\n"
				+ "                                        WHERE\n"
				+ "                                            primary_vwh IS NOT NULL\n"
				+ "                                    )\n"
				+ "                                    AND substr(store_name10,1,5) = '" + plaza + "'\n"
				+ "                                GROUP BY\n" + "                                    b.route\n"
				+ "                            )\n" + "                        ORDER BY\n"
				+ "                            route ASC\n" + "                    )\n" + "                WHERE\n"
				+ "                    dia_gen_ped = '" + diaGenPed + "'\n" + "            )\n" + "    )\n" + "WHERE\n"
				+ "    ROWNUM <= '" + limit + "'";
		return query;
	}

	/**
	 * Mostrar los resultados de buscar por Plaza, Ruta
	 * 
	 * @param dbConecta
	 * @param servicio
	 * @param plaza
	 * @param ruta
	 * @throws SQLException
	 */
	public void rutaQueries(SQLUtil dbConecta, String servicio, String plaza, String ruta) throws SQLException {

		SQLResult a = executeQuery(dbConecta, queryRutaDiaGen(servicio, plaza, ruta));
		logger.info(String.valueOf(a.isEmpty()));
		testCase.addQueryEvidenceCurrentStep(a);

		SQLResult d = executeQuery(dbConecta, queryRutaDiaProg(servicio, plaza, ruta));
		logger.info(String.valueOf(d.isEmpty()));
		testCase.addQueryEvidenceCurrentStep(d);

		SQLResult f = executeQuery(dbConecta, queryRutaDiaIniVerificacion(servicio, plaza, ruta));
		logger.info(String.valueOf(f.isEmpty()));
		testCase.addQueryEvidenceCurrentStep(f);

		SQLResult e = executeQuery(dbConecta, queryRutaDiaIniCarga(servicio, plaza, ruta));
		logger.info(String.valueOf(e.isEmpty()));
		testCase.addQueryEvidenceCurrentStep(e);

		SQLResult h = executeQuery(dbConecta, queryRutaSalida(servicio, plaza, ruta));
		logger.info(String.valueOf(h.isEmpty()));
		testCase.addQueryEvidenceCurrentStep(h);

		SQLResult g = executeQuery(dbConecta, queryRutaDiaDispUniCedis(servicio, plaza, ruta));
		logger.info(String.valueOf(g.isEmpty()));
		testCase.addQueryEvidenceCurrentStep(g);

		SQLResult b = executeQuery(dbConecta, queryRutaLlegadaPt(servicio, plaza, ruta));
		logger.info(String.valueOf(b.isEmpty()));
		testCase.addQueryEvidenceCurrentStep(b);

		SQLResult c = executeQuery(dbConecta, queryRutaSalidaPt(servicio, plaza, ruta));
		logger.info(String.valueOf(c.isEmpty()));
		testCase.addQueryEvidenceCurrentStep(c);

	}

	/**
	 * Query ruta que muestra las columnas de dia de salida
	 * 
	 * @author 2000132763
	 * @param servicio
	 * @param plaza
	 * @param ruta
	 * @return
	 */
	public String queryRutaSalida(String servicio, String plaza, String ruta) {
		return "SELECT\n" + "     *\n" + " FROM\n" + "     (\n" + "         SELECT\n" + "             *\n"
				+ "         FROM\n" + "             (\n" + "                 SELECT\n" + rutaColumns + ","
				+ diaSalidaColumns + "                 FROM\n" + "                     rdm100.xxfc_pmo_route\n"
				+ "                 WHERE\n" + "                     status = 'A'\n"
				+ "                     AND route IN (\n" + "                         SELECT\n"
				+ "                             b.route\n" + "                         FROM\n"
				+ "                             rdm100.carrier_service_route a,\n"
				+ "                             rdm100.route_dest b,\n"
				+ "                             rdm100.xxfc_store_v c,\n"
				+ "                             rdm100.route r,\n"
				+ "                             rdm100.xxfc_pmo_tdistrib_servicio ser\n"
				+ "                         WHERE\n" + "                             a.facility_id = b.facility_id\n"
				+ "                             AND a.facility_id = r.facility_id\n"
				+ "                             AND a.service_code = ser.servicio\n"
				+ "                             AND ser.servicio = DECODE('" + servicio
				+ "','ALL',ser.servicio,substr('" + servicio + "',instr('" + servicio + "',ser.servicio\n"
				+ "                             ),length(ser.servicio) ) )\n"
				+ "                             AND ser.dest_type = c.dest_type\n"
				+ "                             AND b.dest_id = c.store\n"
				+ "                             AND a.route = r.route\n"
				+ "                             AND c.default_wh IN (\n" + "                                 SELECT\n"
				+ "                                     primary_vwh\n" + "                                 FROM\n"
				+ "                                     rdm100.xxfc_con_prog_wh_view\n"
				+ "                                 WHERE\n"
				+ "                                     primary_vwh IS NOT NULL\n" + "                             )\n"
				+ "                             AND substr(store_name10,1,5) ='" + plaza + "'\n"
				+ "                         GROUP BY\n" + "                             b.route\n"
				+ "                     )\n" + "                 ORDER BY\n" + "                     route ASC\n"
				+ "             )\n" + "         WHERE\n" + "             route = '" + ruta + "'\n" + "     )";
	}

	/**
	 * Query tiendas pmo dia fin
	 * 
	 * @author 2000132763
	 * @param ruta
	 * @param dia_gen
	 * @return
	 */
	public String queryTiendasPmoFin(String ruta, String dia_gen) {
		return "SELECT" + " route \t AS \t ruta," + " dia_gen_ped \t AS \t d_gen_p," + " crplaza \t AS \t cr_plaza,"
				+ " tipo_distro \t AS \t td," + " dest_id \t AS \t tienda," + " dia_est_entrega \t AS \t d_est_e,"
				+ " TO_CHAR(h_est_entrega,'HH24:MI') \t AS \t hora " + " FROM" + " rdm100.xxfc_pmo_tienda" + " WHERE"
				+ " route ='" + ruta + "'" + " AND dia_gen_ped = '" + dia_gen + "'" + " ORDER BY"
				+ " load_sequence ASC";
	}

	/**
	 * Query tiendas pmo dia inicio
	 * 
	 * @author 2000132763
	 * @param ruta
	 * @param dia_gen
	 * @return
	 */
	public String queryTiendasPmoIni(String ruta, String dia_gen) {
		return "SELECT" + " route \t AS \t ruta," + " dia_gen_ped \t AS \t d_gen_p,"
				+ " load_sequence \t AS \t load_seq," + " dia_ini_vent_entrega \t AS \t d_ini_v,"
				+ " TO_CHAR(h_ini_vent_entrega,'HH24:MI') \t AS \t h_ini," + " dia_fin_vent_entrega \t AS \t d_fin_v,"
				+ " TO_CHAR(h_fin_vent_entrega,'HH24:MI') \t AS \t h_fin," + " lsp " + " FROM"
				+ " rdm100.xxfc_pmo_tienda" + " WHERE" + " route ='" + ruta + "'" + " AND dia_gen_ped = '" + dia_gen
				+ "'" + " ORDER BY" + " load_sequence ASC";
	}

	/**
	 * Utilizado para crear la visualizacion de la tabla de ruta
	 * 
	 * @param dbConecta
	 * @param servicio
	 * @param plaza
	 * @param ruta
	 * @param tf
	 * @throws SQLException
	 */
	public void rutaQueries(SQLUtil dbConecta, String servicio, String plaza, String ruta, Boolean tf)
			throws SQLException {

		SQLResult a = executeQuery(dbConecta, queryRutaDiaGen(servicio, plaza, ruta));
		logger.info(String.valueOf(a.isEmpty()));
		testCase.addQueryEvidenceCurrentStep(a, tf);

		SQLResult d = executeQuery(dbConecta, queryRutaDiaProg(servicio, plaza, ruta));
		logger.info(String.valueOf(d.isEmpty()));
		testCase.addQueryEvidenceCurrentStep(d, tf);

		SQLResult f = executeQuery(dbConecta, queryRutaDiaIniVerificacion(servicio, plaza, ruta));
		logger.info(String.valueOf(f.isEmpty()));
		testCase.addQueryEvidenceCurrentStep(f, tf);

		SQLResult e = executeQuery(dbConecta, queryRutaDiaIniCarga(servicio, plaza, ruta));
		logger.info(String.valueOf(e.isEmpty()));
		testCase.addQueryEvidenceCurrentStep(e, tf);

		SQLResult h = executeQuery(dbConecta, queryRutaSalida(servicio, plaza, ruta));
		logger.info(String.valueOf(h.isEmpty()));
		testCase.addQueryEvidenceCurrentStep(h, tf);

		SQLResult g = executeQuery(dbConecta, queryRutaDiaDispUniCedis(servicio, plaza, ruta));
		logger.info(String.valueOf(g.isEmpty()));
		testCase.addQueryEvidenceCurrentStep(g, tf);

		SQLResult b = executeQuery(dbConecta, queryRutaLlegadaPt(servicio, plaza, ruta));
		logger.info(String.valueOf(b.isEmpty()));
		testCase.addQueryEvidenceCurrentStep(b, tf);

		SQLResult c = executeQuery(dbConecta, queryRutaSalidaPt(servicio, plaza, ruta));
		logger.info(String.valueOf(c.isEmpty()));
		testCase.addQueryEvidenceCurrentStep(c, tf);

	}

	/**
	 * Consulta de la tabla " RDM100.SHIP_DEST"
	 * 
	 * @return
	 */
	public String queryShipDest() {
		String query = "SELECT\n" + "* \n" + "FROM\n" + "    (\n" + "        SELECT\n" + "            facility_id,\n"
				+ "            dest_id,\n" + "            description,\n" + "            address1,\n"
				+ "            address2,\n" + "            city,\n" + "            state,\n" + "            zip,\n"
				+ "            dest_type\n" + "        FROM\n" + "            rdm100.ship_dest\n" + "    )\n"
				+ "WHERE\n" + "    ROWNUM < 20\n";
		return query;
	}

	/**
	 * Consulta de la tabla "RDM100.XXFC_PMO_TDISTRIB_SERVICIO"
	 * 
	 * @return
	 */
	public String queyPmoTdistribServicio() {
		String query = "SELECT\n" + "        tipo_distrib,\n" + "        servicio,\n" + "        status,\n"
				+ "        t_distrib_detalle,\n" + "        dest_type,\n" + "        dur_devolucion\n" + "    FROM\n"
				+ "        rdm100.xxfc_pmo_tdistrib_servicio\n";
		return query;
	}

	/**
	 * Query pmo tpo pedido (toda la tabla)
	 * 
	 * @return
	 */
	public String queryTpoPedido() {
		String query = "SELECT\n" + "    *\n" + "FROM\n" + "    RDM100.XXFC_PMO_TPO_PEDIDO";
		return query;
	}

	/**
	 * Crea la visualizacion de los detalles del servicio
	 * 
	 * @param dbConecta
	 * @throws SQLException
	 */
	public void detalleServicios(SQLUtil dbConecta) throws SQLException {

		SQLResult queryShipDestResult = executeQuery(dbConecta, queryShipDest());
		logger.info(String.valueOf(queryShipDestResult.isEmpty()));
		testCase.addQueryEvidenceCurrentStep(queryShipDestResult);

		SQLResult queyPmoTdistribServicioResult = executeQuery(dbConecta, queyPmoTdistribServicio());
		logger.info(String.valueOf(queyPmoTdistribServicioResult.isEmpty()));
		testCase.addQueryEvidenceCurrentStep(queyPmoTdistribServicioResult);

		SQLResult queryTpoPedidoResult = executeQuery(dbConecta, queryTpoPedido());
		logger.info(String.valueOf(queryTpoPedidoResult.isEmpty()));
		testCase.addQueryEvidenceCurrentStep(queryTpoPedidoResult);
	}

	/**
	 * Consulta en la tabla "XXFC_PMO_ROUTE_BKP"
	 *
	 * Se dividira en 14 parte por la cantidad de datos a mostrar
	 *
	 * @param ruta           Ingresar una ruta valida. Por ejemplo "GDL101"
	 * @param nombreProyecto Ingresar el nombre del proyecto que se guardo
	 *                       previamente. Por ejemplo "PruebaBTEP602DDMMAAAA"
	 * @return Retornara la informacion de la ruta previamente guardada.
	 * @throws SQLException
	 */
	public String queryPmoRouteBkp01(String ruta, String nombreProyecto) throws SQLException {
		String query = "SELECT\n" + "    NOM_PROYECTO,\n" + "    FECHA_RESPALDO,\n" + "    FACILITY_ID,\n"
				+ "    CEDIS,\n" + "    TIPO_DISTRO\n" + "FROM\n" + "    RDM100.XXFC_PMO_ROUTE_BKP\n" + "WHERE\n"
				+ "    ROUTE = '" + ruta + "'\n" + "    AND NOM_PROYECTO = '" + nombreProyecto + "'";
		return query;
	}

	/**
	 * Consulta en la tabla "XXFC_PMO_ROUTE_BKP"
	 *
	 * Se dividira en 14 parte por la cantidad de datos a mostrar
	 *
	 * @param ruta           Ingresar una ruta valida. Por ejemplo "GDL101"
	 * @param nombreProyecto Ingresar el nombre del proyecto que se guardo
	 *                       previamente. Por ejemplo "PruebaBTEP602DDMMAAAA"
	 * @return Retornara la informacion de la ruta previamente guardada.
	 * @throws SQLException
	 */
	public String queryPmoRouteBkp02(String ruta, String nombreProyecto) throws SQLException {
		String query = "SELECT\n" + "    CARRIER_CODE,\n" + "    SERVICE_CODE\n" + "    ROUTE,\n"
				+ "    ROUTE_DESCRIPTION\n" + "FROM\n" + "    RDM100.XXFC_PMO_ROUTE_BKP\n" + "WHERE\n" + "    ROUTE = '"
				+ ruta + "'\n" + "    AND NOM_PROYECTO = '" + nombreProyecto + "'";
		return query;
	}

	/**
	 * Consulta en la tabla "XXFC_PMO_ROUTE_BKP"
	 *
	 * Se dividira en 14 parte por la cantidad de datos a mostrar
	 *
	 * @param ruta           Ingresar una ruta valida. Por ejemplo "GDL101"
	 * @param nombreProyecto Ingresar el nombre del proyecto que se guardo
	 *                       previamente. Por ejemplo "PruebaBTEP602DDMMAAAA"
	 * @return Retornara la informacion de la ruta previamente guardada.
	 * @throws SQLException
	 */
	public String queryPmoRouteBkp03(String ruta, String nombreProyecto) throws SQLException {
		String query = "SELECT\n" + "    DIA_GEN_PED,\n" + "    TO_CHAR(H_GEN_PED,'HH24:MI') AS H_GEN_PED,\n"
				+ "    DIA_PROG,\n" + "    TO_CHAR(H_PROG,'HH24:MI') AS H_PROG,\n" + "    DIA_INI_PROD\n" + "FROM\n"
				+ "    RDM100.XXFC_PMO_ROUTE_BKP\n" + "WHERE\n" + "    ROUTE = '" + ruta + "'\n"
				+ "    AND NOM_PROYECTO = '" + nombreProyecto + "'";
		return query;
	}

	/**
	 * Consulta en la tabla "XXFC_PMO_ROUTE_BKP"
	 *
	 * Se dividira en 14 parte por la cantidad de datos a mostrar
	 *
	 * @param ruta           Ingresar una ruta valida. Por ejemplo "GDL101"
	 * @param nombreProyecto Ingresar el nombre del proyecto que se guardo
	 *                       previamente. Por ejemplo "PruebaBTEP602DDMMAAAA"
	 * @return Retornara la informacion de la ruta previamente guardada.
	 * @throws SQLException
	 */
	public String queryPmoRouteBkp04(String ruta, String nombreProyecto) throws SQLException {
		String query = "SELECT\n" + "    TO_CHAR(H_INI_PROD,'HH24:MI') AS H_INI_PROD,\n" + "    DIA_FIN_PROD,\n"
				+ "    TO_CHAR(H_FIN_PROD,'HH24:MI') AS H_FIN_PROD\n" + "FROM\n" + "    RDM100.XXFC_PMO_ROUTE_BKP\n"
				+ "WHERE\n" + "    ROUTE = '" + ruta + "'\n" + "    AND NOM_PROYECTO = '" + nombreProyecto + "'";
		return query;
	}

	/**
	 * Consulta en la tabla "XXFC_PMO_ROUTE_BKP"
	 *
	 * Se dividira en 14 parte por la cantidad de datos a mostrar
	 *
	 * @param ruta           Ingresar una ruta valida. Por ejemplo "GDL101"
	 * @param nombreProyecto Ingresar el nombre del proyecto que se guardo
	 *                       previamente. Por ejemplo "PruebaBTEP602DDMMAAAA"
	 * @return Retornara la informacion de la ruta previamente guardada.
	 * @throws SQLException
	 */
	public String queryPmoRouteBkp05(String ruta, String nombreProyecto) throws SQLException {
		String query = "SELECT\n" + "    DIA_INI_CARGA,\n" + "    TO_CHAR(H_INI_CARGA,'HH24:MI') AS H_INI_CARGA,\n"
				+ "    DIA_FIN_CARGA,\n" + "    TO_CHAR(H_FIN_CARGA,'HH24:MI') AS H_FIN_CARGA\n" + "FROM\n"
				+ "    RDM100.XXFC_PMO_ROUTE_BKP\n" + "WHERE\n" + "    ROUTE = '" + ruta + "'\n"
				+ "    AND NOM_PROYECTO = '" + nombreProyecto + "'";
		return query;
	}

	/**
	 * Consulta en la tabla "XXFC_PMO_ROUTE_BKP"
	 *
	 * Se dividira en 14 parte por la cantidad de datos a mostrar
	 *
	 * @param ruta           Ingresar una ruta valida. Por ejemplo "GDL101"
	 * @param nombreProyecto Ingresar el nombre del proyecto que se guardo
	 *                       previamente. Por ejemplo "PruebaBTEP602DDMMAAAA"
	 * @return Retornara la informacion de la ruta previamente guardada.
	 * @throws SQLException
	 */
	public String queryPmoRouteBkp06(String ruta, String nombreProyecto) throws SQLException {
		String query = "SELECT\n" + "    DIA_SALIDA,\n" + "    TO_CHAR(H_SALIDA,'HH24:MI') AS H_SALIDA\n" + "FROM\n"
				+ "    RDM100.XXFC_PMO_ROUTE_BKP\n" + "WHERE\n" + "    ROUTE = '" + ruta + "'\n"
				+ "    AND NOM_PROYECTO = '" + nombreProyecto + "'";
		return query;
	}

	/**
	 * Consulta en la tabla "XXFC_PMO_ROUTE_BKP"
	 *
	 * Se dividira en 14 parte por la cantidad de datos a mostrar
	 *
	 * @param ruta           Ingresar una ruta valida. Por ejemplo "GDL101"
	 * @param nombreProyecto Ingresar el nombre del proyecto que se guardo
	 *                       previamente. Por ejemplo "PruebaBTEP602DDMMAAAA"
	 * @return Retornara la informacion de la ruta previamente guardada.
	 * @throws SQLException
	 */
	public String queryPmoRouteBkp07(String ruta, String nombreProyecto) throws SQLException {
		String query = "SELECT\n" + "    RECORRIDO,\n" + "    DIA_INI_ENTREGA,\n"
				+ "    TO_CHAR(H_INI_ENTREGA,'HH24:MI') AS H_INI_ENTREGA,\n" + "    DIA_FIN_ENTREGA,\n"
				+ "    TO_CHAR(H_FIN_ENTREGA,'HH24:MI') AS H_FIN_ENTREGA\n" + "FROM\n"
				+ "    RDM100.XXFC_PMO_ROUTE_BKP\n" + "WHERE\n" + "    ROUTE = '" + ruta + "'\n"
				+ "    AND NOM_PROYECTO = '" + nombreProyecto + "'";
		return query;
	}

	/**
	 * Consulta en la tabla "XXFC_PMO_ROUTE_BKP"
	 *
	 * Se dividira en 14 parte por la cantidad de datos a mostrar
	 *
	 * @param ruta           Ingresar una ruta valida. Por ejemplo "GDL101"
	 * @param nombreProyecto Ingresar el nombre del proyecto que se guardo
	 *                       previamente. Por ejemplo "PruebaBTEP602DDMMAAAA"
	 * @return Retornara la informacion de la ruta previamente guardada.
	 * @throws SQLException
	 */
	public String queryPmoRouteBkp08(String ruta, String nombreProyecto) throws SQLException {
		String query = "SELECT\n" + "    CICLO_ATN,\n" + "    CICLO_TOT,\n" + "    DIA_DISP_UNI_CEDIS AS D_D_UNICEDIS,\n"
				+ "    TO_CHAR(H_DISP_UNI_CEDIS,'HH24:MI') AS H_DISP_UNI_CEDIS,\n" + "    LS\n" + "    FROM\n"
				+ "    RDM100.XXFC_PMO_ROUTE_BKP\n" + "WHERE\n" + "    ROUTE = '" + ruta + "'\n"
				+ "    AND NOM_PROYECTO = '" + nombreProyecto + "'";
		return query;
	}

	/**
	 * Consulta en la tabla "XXFC_PMO_ROUTE_BKP"
	 *
	 * Se dividira en 14 parte por la cantidad de datos a mostrar
	 *
	 * @param ruta           Ingresar una ruta valida. Por ejemplo "GDL101"
	 * @param nombreProyecto Ingresar el nombre del proyecto que se guardo
	 *                       previamente. Por ejemplo "PruebaBTEP602DDMMAAAA"
	 * @return Retornara la informacion de la ruta previamente guardada.
	 * @throws SQLException
	 */
	public String queryPmoRouteBkp09(String ruta, String nombreProyecto) throws SQLException {
		String query = "SELECT\n" + "    PO_GENERATION_CUTOFF_TIME AS POGEN_CUTOFFTIME,\n" + "    PO_GENERATION_TIME AS PO_GEN_TIME,\n" + "    STATUS,\n"
				+ "    USER_ID\n" + "FROM\n" + "    RDM100.XXFC_PMO_ROUTE_BKP\n" + "WHERE\n" + "    ROUTE = '" + ruta
				+ "'\n" + "    AND NOM_PROYECTO = '" + nombreProyecto + "'";
		return query;
	}

	/**
	 * Consulta en la tabla "XXFC_PMO_ROUTE_BKP"
	 *
	 * Se dividira en 14 parte por la cantidad de datos a mostrar
	 *
	 * @param ruta           Ingresar una ruta valida. Por ejemplo "GDL101"
	 * @param nombreProyecto Ingresar el nombre del proyecto que se guardo
	 *                       previamente. Por ejemplo "PruebaBTEP602DDMMAAAA"
	 * @return Retornara la informacion de la ruta previamente guardada.
	 * @throws SQLException
	 */
	public String queryPmoRouteBkp10(String ruta, String nombreProyecto) throws SQLException {
		String query = "SELECT\n" + "    SEM_GEN_PED,\n" + "    SEM_PROG,\n" + "    SEM_INI_PROD AS SEM_I_PROD,\n"
				+ "    SEM_FIN_PROD AS SE_FN_PROD,\n" + "    SEM_INI_CARGA AS SM_I_CARGA\n" + "FROM\n" + "    RDM100.XXFC_PMO_ROUTE_BKP\n"
				+ "WHERE\n" + "    ROUTE = '" + ruta + "'\n" + "    AND NOM_PROYECTO = '" + nombreProyecto + "'";
		return query;
	}

	/**
	 * Consulta en la tabla "XXFC_PMO_ROUTE_BKP"
	 *
	 * Se dividira en 14 parte por la cantidad de datos a mostrar
	 *
	 * @param ruta           Ingresar una ruta valida. Por ejemplo "GDL101"
	 * @param nombreProyecto Ingresar el nombre del proyecto que se guardo
	 *                       previamente. Por ejemplo "PruebaBTEP602DDMMAAAA"
	 * @return Retornara la informacion de la ruta previamente guardada.
	 * @throws SQLException
	 */
	public String queryPmoRouteBkp11(String ruta, String nombreProyecto) throws SQLException {
		String query = "SELECT\n" + "    SEM_FIN_CARGA,\n" + "    SEM_SALIDA,\n" + "    SEM_INI_ENTREGA AS SM_I_ENTR,\n"
				+ "    SEM_FIN_ENTREGA AS SM_FN_ENTR,\n" + "    SEM_DISP_UNI_CEDIS AS S_DP_UNICEDIS\n" + "FROM\n" + "    RDM100.XXFC_PMO_ROUTE_BKP\n"
				+ "WHERE\n" + "    ROUTE = '" + ruta + "'\n" + "    AND NOM_PROYECTO = '" + nombreProyecto + "'";
		return query;
	}

	/**
	 * Consulta en la tabla "XXFC_PMO_ROUTE_BKP"
	 *
	 * Se dividira en 14 parte por la cantidad de datos a mostrar
	 *
	 * @param ruta           Ingresar una ruta valida. Por ejemplo "GDL101"
	 * @param nombreProyecto Ingresar el nombre del proyecto que se guardo
	 *                       previamente. Por ejemplo "PruebaBTEP602DDMMAAAA"
	 * @return Retornara la informacion de la ruta previamente guardada.
	 * @throws SQLException
	 */
	public String queryPmoRouteBkp12(String ruta, String nombreProyecto) throws SQLException {
		String query = "SELECT\n" + "    DIA_INI_VERIFICACION,\n"
				+ "    TO_CHAR(H_INI_VERIFICACION,'HH24:MI') AS H_INI_VERIFICACION,\n" + "    DIA_FIN_VERIFICACION AS DIA_FN_VER,\n"
				+ "    TO_CHAR(H_FIN_VERIFICACION,'HH24:MI') AS H_FIN_VERIFICACION,\n" + "    DIA_LLEGADA_PT\n"
				+ "    FROM\n" + "    RDM100.XXFC_PMO_ROUTE_BKP\n" + "WHERE\n" + "    ROUTE = '" + ruta + "'\n"
				+ "    AND NOM_PROYECTO = '" + nombreProyecto + "'";
		return query;
	}

	/**
	 * Consulta en la tabla "XXFC_PMO_ROUTE_BKP"
	 *
	 * Se dividira en 14 parte por la cantidad de datos a mostrar
	 *
	 * @param ruta           Ingresar una ruta valida. Por ejemplo "GDL101"
	 * @param nombreProyecto Ingresar el nombre del proyecto que se guardo
	 *                       previamente. Por ejemplo "PruebaBTEP602DDMMAAAA"
	 * @return Retornara la informacion de la ruta previamente guardada.
	 * @throws SQLException
	 */
	public String queryPmoRouteBkp13(String ruta, String nombreProyecto) throws SQLException {
		String query = "SELECT\n" + "    TO_CHAR(H_LLEGADA_PT,'HH24:MI') AS H_LLEGADA_PT,\n" + "    DIA_SALIDA_PT AS D_SAL_PT,\n"
				+ "    TO_CHAR(H_SALIDA_PT,'HH24:MI') AS H_SALIDA_PT,\n" + "    SEM_INI_VERIFICACION AS SM_I_VER,\n"
				+ "    SEM_FIN_VERIFICACION AS SM_FN_VER\n" + "FROM\n" + "    RDM100.XXFC_PMO_ROUTE_BKP\n" + "WHERE\n"
				+ "    ROUTE = '" + ruta + "'\n" + "    AND NOM_PROYECTO = '" + nombreProyecto + "'";
		return query;
	}

	/**
	 * Consulta en la tabla "XXFC_PMO_ROUTE_BKP"
	 *
	 * Se dividira en 14 parte por la cantidad de datos a mostrar
	 *
	 * @param ruta           Ingresar una ruta valida. Por ejemplo "GDL101"
	 * @param nombreProyecto Ingresar el nombre del proyecto que se guardo
	 *                       previamente. Por ejemplo "PruebaBTEP602DDMMAAAA"
	 * @return Retornara la informacion de la ruta previamente guardada.
	 * @throws SQLException
	 */
	public String queryPmoRouteBkp14(String ruta, String nombreProyecto) throws SQLException {
		String query = "SELECT\n" + "    SEM_LLEGADA_PT,\n" + "    SEM_SALIDA_PT\n" + "FROM\n"
				+ "    RDM100.XXFC_PMO_ROUTE_BKP\n" + "WHERE\n" + "    ROUTE = '" + ruta + "'\n"
				+ "    AND NOM_PROYECTO = '" + nombreProyecto + "'";
		return query;
	}

	/**
	 * Consulta en la tabla "XXFC_PMO_TIENDA_BKP"
	 *
	 * Se dividira en 9 parte por la cantidad de datos a mostrar
	 *
	 * @param ruta           Ingresar una ruta valida. Por ejemplo "GDL101"
	 * @param nombreProyecto Ingresar el nombre del proyecto que se guardo
	 *                       previamente. Por ejemplo "PruebaBTEP602DDMMAAAA"
	 * @return Retornara la informacion de la ruta previamente guardada.
	 * @throws SQLException
	 */
	public String queryPmoTiendaBkp01(String ruta, String nombreProyecto) throws SQLException {
		String query = "SELECT\n" + "    *\n" + "FROM\n" + "    (\n" + "        SELECT\n"
				+ "            nom_proyecto,\n" + "            fecha_respaldo,\n" + "            route\n"
				+ "        FROM\n" + "            rdm100.xxfc_pmo_tienda_bkp\n" + "        WHERE\n"
				+ "            nom_proyecto = '" + nombreProyecto + "'\n" + "            AND route = '" + ruta + "'\n"
				+ "        ORDER BY\n" + "            load_sequence ASC\n" + "    )\n" + "WHERE\n" + "    ROWNUM <= 20";
		return query;
	}

	/**
	 * Consulta en la tabla "XXFC_PMO_TIENDA_BKP"
	 *
	 * Se dividira en 9 parte por la cantidad de datos a mostrar
	 *
	 * @param ruta           Ingresar una ruta valida. Por ejemplo "GDL101"
	 * @param nombreProyecto Ingresar el nombre del proyecto que se guardo
	 *                       previamente. Por ejemplo "PruebaBTEP602DDMMAAAA"
	 * @return Retornara la informacion de la ruta previamente guardada.
	 * @throws SQLException
	 */
	public String queryPmoTiendaBkp02(String ruta, String nombreProyecto) throws SQLException {
		String query = "SELECT\n" + "    *\n" + "FROM\n" + "    (\n" + "        SELECT\n" + "            dia_gen_ped,\n"
				+ "            dest_id,\n" + "            load_sequence\n" + "        FROM\n"
				+ "            rdm100.xxfc_pmo_tienda_bkp\n" + "        WHERE\n" + "            nom_proyecto = '"
				+ nombreProyecto + "'\n" + "            AND route = '" + ruta + "'\n" + "        ORDER BY\n"
				+ "            load_sequence ASC\n" + "    )\n" + "WHERE\n" + "    ROWNUM <= 20";
		return query;
	}

	/**
	 * Consulta en la tabla "XXFC_PMO_TIENDA_BKP"
	 *
	 * Se dividira en 9 parte por la cantidad de datos a mostrar
	 *
	 * @param ruta           Ingresar una ruta valida. Por ejemplo "GDL101"
	 * @param nombreProyecto Ingresar el nombre del proyecto que se guardo
	 *                       previamente. Por ejemplo "PruebaBTEP602DDMMAAAA"
	 * @return Retornara la informacion de la ruta previamente guardada.
	 * @throws SQLException
	 */
	public String queryPmoTiendaBkp03(String ruta, String nombreProyecto) throws SQLException {
		String query = "SELECT\n" + "    *\n" + "FROM\n" + "    (\n" + "        SELECT\n"
				+ "            dia_ini_vent_entrega,\n"
				+ "            TO_CHAR(h_ini_vent_entrega,'HH24:MI') AS h_ini_vent_entrega,\n"
				+ "            dia_fin_vent_entrega,\n"
				+ "            TO_CHAR(h_fin_vent_entrega,'HH24:MI') AS h_fin_vent_entrega\n" + "        FROM\n"
				+ "            rdm100.xxfc_pmo_tienda_bkp\n" + "        WHERE\n" + "            nom_proyecto = '"
				+ nombreProyecto + "'\n" + "            AND route = '" + ruta + "'\n" + "        ORDER BY\n"
				+ "            load_sequence ASC\n" + "    )\n" + "WHERE\n" + "    ROWNUM <= 20";
		return query;
	}

	/**
	 * Consulta en la tabla "XXFC_PMO_TIENDA_BKP"
	 *
	 * Se dividira en 9 parte por la cantidad de datos a mostrar
	 *
	 * @param ruta           Ingresar una ruta valida. Por ejemplo "GDL101"
	 * @param nombreProyecto Ingresar el nombre del proyecto que se guardo
	 *                       previamente. Por ejemplo "PruebaBTEP602DDMMAAAA"
	 * @return Retornara la informacion de la ruta previamente guardada.
	 * @throws SQLException
	 */
	public String queryPmoTiendaBkp04(String ruta, String nombreProyecto) throws SQLException {
		String query = "SELECT\n" + "    *\n" + "FROM\n" + "    (\n" + "        SELECT\n" + "            lsp,\n"
				+ "            lssep1,\n" + "            lssep2,\n" + "            cedis,\n" + "            crplaza\n"
				+ "        FROM\n" + "            rdm100.xxfc_pmo_tienda_bkp\n" + "        WHERE\n"
				+ "            nom_proyecto = '" + nombreProyecto + "'\n" + "            AND route = '" + ruta + "'\n"
				+ "        ORDER BY\n" + "            load_sequence ASC\n" + "    )\n" + "WHERE\n" + "    ROWNUM <= 20";
		return query;
	}

	/**
	 * Consulta en la tabla "XXFC_PMO_TIENDA_BKP"
	 *
	 * Se dividira en 9 parte por la cantidad de datos a mostrar
	 *
	 * @param ruta           Ingresar una ruta valida. Por ejemplo "GDL101"
	 * @param nombreProyecto Ingresar el nombre del proyecto que se guardo
	 *                       previamente. Por ejemplo "PruebaBTEP602DDMMAAAA"
	 * @return Retornara la informacion de la ruta previamente guardada.
	 * @throws SQLException
	 */
	public String queryPmoTiendaBkp05(String ruta, String nombreProyecto) throws SQLException {
		String query = "SELECT\n" + "    *\n" + "FROM\n" + "    (\n" + "        SELECT\n" + "            tipo_pedido\n"
				+ "        FROM\n" + "            rdm100.xxfc_pmo_tienda_bkp\n" + "        WHERE\n"
				+ "            nom_proyecto = '" + nombreProyecto + "'\n" + "            AND route = '" + ruta + "'\n"
				+ "        ORDER BY\n" + "            load_sequence ASC\n" + "    )\n" + "WHERE\n" + "    ROWNUM <= 20";
		return query;
	}

	/**
	 * Consulta en la tabla "XXFC_PMO_TIENDA_BKP"
	 *
	 * Se dividira en 9 parte por la cantidad de datos a mostrar
	 *
	 * @param ruta           Ingresar una ruta valida. Por ejemplo "GDL101"
	 * @param nombreProyecto Ingresar el nombre del proyecto que se guardo
	 *                       previamente. Por ejemplo "PruebaBTEP602DDMMAAAA"
	 * @return Retornara la informacion de la ruta previamente guardada.
	 * @throws SQLException
	 */
	public String queryPmoTiendaBkp06(String ruta, String nombreProyecto) throws SQLException {
		String query = "SELECT\n" + "    *\n" + "FROM\n" + "    (\n" + "        SELECT\n" + "            crossdock,\n"
				+ "            attribute1,\n" + "            attribute2\n" + "        FROM\n"
				+ "            rdm100.xxfc_pmo_tienda_bkp\n" + "        WHERE\n" + "            nom_proyecto = '"
				+ nombreProyecto + "'\n" + "            AND route = '" + ruta + "'\n" + "        ORDER BY\n"
				+ "            load_sequence ASC\n" + "    )\n" + "WHERE\n" + "    ROWNUM <= 20";
		return query;
	}

	/**
	 * Consulta en la tabla "XXFC_PMO_TIENDA_BKP"
	 *
	 * Se dividira en 9 parte por la cantidad de datos a mostrar
	 *
	 * @param ruta           Ingresar una ruta valida. Por ejemplo "GDL101"
	 * @param nombreProyecto Ingresar el nombre del proyecto que se guardo
	 *                       previamente. Por ejemplo "PruebaBTEP602DDMMAAAA"
	 * @return Retornara la informacion de la ruta previamente guardada.
	 * @throws SQLException
	 */
	public String queryPmoTiendaBkp07(String ruta, String nombreProyecto) throws SQLException {
		String query = "SELECT\n" + "    *\n" + "FROM\n" + "    (\n" + "        SELECT\n" + "            status,\n"
				+ "            insert_date,\n" + "            update_date\n" + "        FROM\n"
				+ "            rdm100.xxfc_pmo_tienda_bkp\n" + "        WHERE\n" + "            nom_proyecto = '"
				+ nombreProyecto + "'\n" + "            AND route = '" + ruta + "'\n" + "        ORDER BY\n"
				+ "            load_sequence ASC\n" + "    )\n" + "WHERE\n" + "    ROWNUM <= 20";
		return query;
	}

	/**
	 * Consulta en la tabla "XXFC_PMO_TIENDA_BKP"
	 *
	 * Se dividira en 9 parte por la cantidad de datos a mostrar
	 *
	 * @param ruta           Ingresar una ruta valida. Por ejemplo "GDL101"
	 * @param nombreProyecto Ingresar el nombre del proyecto que se guardo
	 *                       previamente. Por ejemplo "PruebaBTEP602DDMMAAAA"
	 * @return Retornara la informacion de la ruta previamente guardada.
	 * @throws SQLException
	 */
	public String queryPmoTiendaBkp08(String ruta, String nombreProyecto) throws SQLException {
		String query = "SELECT\n" + "    *\n" + "FROM\n" + "    (\n" + "        SELECT\n" + "            user_id,\n"
				+ "            tipo_distro,\n" + "            dia_est_entrega,\n"
				+ "            TO_CHAR(h_est_entrega,'HH24:MI') AS h_est_entrega\n" + "        FROM\n"
				+ "            rdm100.xxfc_pmo_tienda_bkp\n" + "        WHERE\n" + "            nom_proyecto = '"
				+ nombreProyecto + "'\n" + "            AND route = '" + ruta + "'\n" + "        ORDER BY\n"
				+ "            load_sequence ASC\n" + "    )\n" + "WHERE\n" + "    ROWNUM <= 20";
		return query;
	}

	/**
	 * Consulta en la tabla "XXFC_PMO_TIENDA_BKP"
	 *
	 * Se dividira en 9 parte por la cantidad de datos a mostrar
	 *
	 * @param ruta           Ingresar una ruta valida. Por ejemplo "GDL101"
	 * @param nombreProyecto Ingresar el nombre del proyecto que se guardo
	 *                       previamente. Por ejemplo "PruebaBTEP602DDMMAAAA"
	 * @return Retornara la informacion de la ruta previamente guardada.
	 * @throws SQLException
	 */
	public String queryPmoTiendaBkp09(String ruta, String nombreProyecto) throws SQLException {
		String query = "SELECT\n" + "    *\n" + "FROM\n" + "    (\n" + "        SELECT\n" + "            verify_int,\n"
				+ "            verify_ext\n" + "        FROM\n" + "            rdm100.xxfc_pmo_tienda_bkp\n"
				+ "        WHERE\n" + "            nom_proyecto = '" + nombreProyecto + "'\n"
				+ "            AND route = '" + ruta + "'\n" + "        ORDER BY\n" + "            load_sequence ASC\n"
				+ "    )\n" + "WHERE\n" + "    ROWNUM <= 20";
		return query;
	}

	/**
	 * Crea la visualizacion de las tablas route y tienda bkp
	 * 
	 * @param dbConecta
	 * @param ruta
	 * @param nombreProyecto
	 * @throws SQLException
	 */
	public void consultaBKP(SQLUtil dbConecta, String ruta, String nombreProyecto, boolean tf) throws SQLException {
		SQLResult queryPmoRouteBkp01Result = executeQuery(dbConecta, queryPmoRouteBkp01(ruta, nombreProyecto));
		logger.info(String.valueOf(queryPmoRouteBkp01Result.isEmpty()));
		testCase.addQueryEvidenceCurrentStep(queryPmoRouteBkp01Result, tf);

		SQLResult queryPmoRouteBkp02Result = executeQuery(dbConecta, queryPmoRouteBkp02(ruta, nombreProyecto));
		logger.info(String.valueOf(queryPmoRouteBkp02Result.isEmpty()));
		testCase.addQueryEvidenceCurrentStep(queryPmoRouteBkp02Result, tf);

		SQLResult queryPmoRouteBkp03Result = executeQuery(dbConecta, queryPmoRouteBkp03(ruta, nombreProyecto));
		logger.info(String.valueOf(queryPmoRouteBkp03Result.isEmpty()));
		testCase.addQueryEvidenceCurrentStep(queryPmoRouteBkp03Result, tf);

		SQLResult queryPmoRouteBkp04Result = executeQuery(dbConecta, queryPmoRouteBkp04(ruta, nombreProyecto));
		logger.info(String.valueOf(queryPmoRouteBkp04Result.isEmpty()));
		testCase.addQueryEvidenceCurrentStep(queryPmoRouteBkp04Result, tf);

		SQLResult queryPmoRouteBkp05Result = executeQuery(dbConecta, queryPmoRouteBkp05(ruta, nombreProyecto));
		logger.info(String.valueOf(queryPmoRouteBkp05Result.isEmpty()));
		testCase.addQueryEvidenceCurrentStep(queryPmoRouteBkp05Result, tf);

		SQLResult queryPmoRouteBkp06Result = executeQuery(dbConecta, queryPmoRouteBkp06(ruta, nombreProyecto));
		logger.info(String.valueOf(queryPmoRouteBkp06Result.isEmpty()));
		testCase.addQueryEvidenceCurrentStep(queryPmoRouteBkp06Result, tf);

		SQLResult queryPmoRouteBkp07Result = executeQuery(dbConecta, queryPmoRouteBkp07(ruta, nombreProyecto));
		logger.info(String.valueOf(queryPmoRouteBkp07Result.isEmpty()));
		testCase.addQueryEvidenceCurrentStep(queryPmoRouteBkp07Result, tf);

		SQLResult queryPmoRouteBkp08Result = executeQuery(dbConecta, queryPmoRouteBkp08(ruta, nombreProyecto));
		logger.info(String.valueOf(queryPmoRouteBkp08Result.isEmpty()));
		testCase.addQueryEvidenceCurrentStep(queryPmoRouteBkp08Result, tf);

		SQLResult queryPmoRouteBkp09Result = executeQuery(dbConecta, queryPmoRouteBkp09(ruta, nombreProyecto));
		logger.info(String.valueOf(queryPmoRouteBkp09Result.isEmpty()));
		testCase.addQueryEvidenceCurrentStep(queryPmoRouteBkp09Result, tf);

		SQLResult queryPmoRouteBkp10Result = executeQuery(dbConecta, queryPmoRouteBkp10(ruta, nombreProyecto));
		logger.info(String.valueOf(queryPmoRouteBkp10Result.isEmpty()));
		testCase.addQueryEvidenceCurrentStep(queryPmoRouteBkp10Result, tf);

		SQLResult queryPmoRouteBkp11Result = executeQuery(dbConecta, queryPmoRouteBkp11(ruta, nombreProyecto));
		logger.info(String.valueOf(queryPmoRouteBkp11Result.isEmpty()));
		testCase.addQueryEvidenceCurrentStep(queryPmoRouteBkp11Result, tf);

		SQLResult queryPmoRouteBkp12Result = executeQuery(dbConecta, queryPmoRouteBkp12(ruta, nombreProyecto));
		logger.info(String.valueOf(queryPmoRouteBkp12Result.isEmpty()));
		testCase.addQueryEvidenceCurrentStep(queryPmoRouteBkp12Result, tf);

		SQLResult queryPmoRouteBkp13Result = executeQuery(dbConecta, queryPmoRouteBkp13(ruta, nombreProyecto));
		logger.info(String.valueOf(queryPmoRouteBkp13Result.isEmpty()));
		testCase.addQueryEvidenceCurrentStep(queryPmoRouteBkp13Result, tf);

		SQLResult queryPmoRouteBkp14Result = executeQuery(dbConecta, queryPmoRouteBkp14(ruta, nombreProyecto));
		logger.info(String.valueOf(queryPmoRouteBkp14Result.isEmpty()));
		testCase.addQueryEvidenceCurrentStep(queryPmoRouteBkp14Result, tf);

		SQLResult queryPmoTiendaBkp01Result = executeQuery(dbConecta, queryPmoTiendaBkp01(ruta, nombreProyecto));
		logger.info(String.valueOf(queryPmoTiendaBkp01Result.isEmpty()));
		testCase.addQueryEvidenceCurrentStep(queryPmoTiendaBkp01Result, tf);

		SQLResult queryPmoTiendaBkp02Result = executeQuery(dbConecta, queryPmoTiendaBkp02(ruta, nombreProyecto));
		logger.info(String.valueOf(queryPmoTiendaBkp02Result.isEmpty()));
		testCase.addQueryEvidenceCurrentStep(queryPmoTiendaBkp02Result, tf);

		SQLResult queryPmoTiendaBkp03Result = executeQuery(dbConecta, queryPmoTiendaBkp03(ruta, nombreProyecto));
		logger.info(String.valueOf(queryPmoTiendaBkp03Result.isEmpty()));
		testCase.addQueryEvidenceCurrentStep(queryPmoTiendaBkp03Result, tf);

		SQLResult queryPmoTiendaBkp04Result = executeQuery(dbConecta, queryPmoTiendaBkp04(ruta, nombreProyecto));
		logger.info(String.valueOf(queryPmoTiendaBkp04Result.isEmpty()));
		testCase.addQueryEvidenceCurrentStep(queryPmoTiendaBkp04Result, tf);

		SQLResult queryPmoTiendaBkp05Result = executeQuery(dbConecta, queryPmoTiendaBkp05(ruta, nombreProyecto));
		logger.info(String.valueOf(queryPmoTiendaBkp05Result.isEmpty()));
		testCase.addQueryEvidenceCurrentStep(queryPmoTiendaBkp05Result, tf);

		SQLResult queryPmoTiendaBkp06Result = executeQuery(dbConecta, queryPmoTiendaBkp06(ruta, nombreProyecto));
		logger.info(String.valueOf(queryPmoTiendaBkp06Result.isEmpty()));
		testCase.addQueryEvidenceCurrentStep(queryPmoTiendaBkp06Result, tf);

		SQLResult queryPmoTiendaBkp07Result = executeQuery(dbConecta, queryPmoTiendaBkp07(ruta, nombreProyecto));
		logger.info(String.valueOf(queryPmoTiendaBkp07Result.isEmpty()));
		testCase.addQueryEvidenceCurrentStep(queryPmoTiendaBkp07Result, tf);

		SQLResult queryPmoTiendaBkp08Result = executeQuery(dbConecta, queryPmoTiendaBkp08(ruta, nombreProyecto));
		logger.info(String.valueOf(queryPmoTiendaBkp08Result.isEmpty()));
		testCase.addQueryEvidenceCurrentStep(queryPmoTiendaBkp08Result, tf);

		SQLResult queryPmoTiendaBkp09Result = executeQuery(dbConecta, queryPmoTiendaBkp09(ruta, nombreProyecto));
		logger.info(String.valueOf(queryPmoTiendaBkp09Result.isEmpty()));
		testCase.addQueryEvidenceCurrentStep(queryPmoTiendaBkp09Result, tf);
	}

	/**
	 * Consulta en la tabla "XXFC_PMO_ROUTE_BKP"
	 *
	 * Se dividira en 7 parte por la cantidad de datos a mostrar
	 *
	 * @param nombreProyecto Ingresar el nombre del proyecto que se guardo
	 *                       previamente. Por ejemplo "PruebaBTEP602DDMMAAAA"
	 * @return Retornara la informacion de la ruta previamente guardada.
	 * @throws SQLException
	 */
	public String query2PmoRouteBkp01(String nombreProyecto) throws SQLException {
		String query = "SELECT\n" + "    *\n" + "FROM\n" + "    (\n" + "SELECT\n" + "    NOM_PROYECTO AS NOM_PROYEC,\n"
				+ "    FECHA_RESPALDO AS FECH_RESPAL,\n" + "    FACILITY_ID,\n" + "    CEDIS\n"  + "FROM\n"
				+ "    RDM100.XXFC_PMO_ROUTE_BKP\n" + "WHERE\n" + "    NOM_PROYECTO = '" + nombreProyecto + "'"
				+ "    )\n" + "WHERE\n" + "    ROWNUM <= 10";
		return query;
	}

	/**
	 * Consulta en la tabla "XXFC_PMO_ROUTE_BKP"
	 *
	 * Se dividira en 7 parte por la cantidad de datos a mostrar
	 *
	 * @param nombreProyecto Ingresar el nombre del proyecto que se guardo
	 *                       previamente. Por ejemplo "PruebaBTEP602DDMMAAAA"
	 * @return Retornara la informacion de la ruta previamente guardada.
	 * @throws SQLException
	 */
	public String query2PmoRouteBkp01_1(String nombreProyecto) throws SQLException {
		String query = "SELECT\n" + "    *\n" + "FROM\n" + "    (\n" + "SELECT\n" + "    TIPO_DISTRO AS TP_DISTR,\n"
				+ "    CARRIER_CODE AS CARR_CODE,\n" + "    SERVICE_CODE AS SERV_CODE,\n" + "    ROUTE,\n" + "    ROUTE_DESCRIPTION\n" + "FROM\n"
				+ "    RDM100.XXFC_PMO_ROUTE_BKP\n" + "WHERE\n" + "    NOM_PROYECTO = '" + nombreProyecto + "'"
				+ "    )\n" + "WHERE\n" + "    ROWNUM <= 10";
		return query;
	}
	
	/**
	 * Consulta en la tabla "XXFC_PMO_ROUTE_BKP"
	 *
	 * Se dividira en 7 parte por la cantidad de datos a mostrar
	 *
	 * @param nombreProyecto Ingresar el nombre del proyecto que se guardo
	 *                       previamente. Por ejemplo "PruebaBTEP602DDMMAAAA"
	 * @return Retornara la informacion de la ruta previamente guardada.
	 * @throws SQLException
	 */
	public String query2PmoRouteBkp02(String nombreProyecto) throws SQLException {
		String query = "SELECT\n" + "    *\n" + "FROM\n" + "    (\n" + "SELECT\n" + "    DIA_GEN_PED,\n"
				+ "    TO_CHAR(H_GEN_PED,'HH24:MI') AS H_GEN_PED,\n" + "    DIA_PROG,\n"
				+ "    TO_CHAR(H_PROG,'HH24:MI') AS H_PROG,\n" + "    DIA_INI_PROD,\n"
				+ "    TO_CHAR(H_INI_PROD,'HH24:MI') AS H_INI_PROD,\n" + "    DIA_FIN_PROD,\n"
				+ "    TO_CHAR(H_FIN_PROD,'HH24:MI') AS H_FIN_PROD\n" + "FROM\n" + "    RDM100.XXFC_PMO_ROUTE_BKP\n"
				+ "WHERE\n" + "    NOM_PROYECTO = '" + nombreProyecto + "'" + "    )\n" + "WHERE\n"
				+ "    ROWNUM <= 10";
		return query;
	}

	/**
	 * Consulta en la tabla "XXFC_PMO_ROUTE_BKP"
	 *
	 * Se dividira en 7 parte por la cantidad de datos a mostrar
	 *
	 * @param nombreProyecto Ingresar el nombre del proyecto que se guardo
	 *                       previamente. Por ejemplo "PruebaBTEP602DDMMAAAA"
	 * @return Retornara la informacion de la ruta previamente guardada.
	 * @throws SQLException
	 */
	public String query2PmoRouteBkp03(String nombreProyecto) throws SQLException {
		String query = "SELECT\n" + "    *\n" + "FROM\n" + "    (\n" + "SELECT\n" + "    DIA_INI_CARGA,\n"
				+ "    TO_CHAR(H_INI_CARGA,'HH24:MI') AS H_INI_CARGA,\n" + "    DIA_FIN_CARGA,\n"
				+ "    TO_CHAR(H_FIN_CARGA,'HH24:MI') AS H_FIN_CARGA,\n" + "    DIA_SALIDA,\n"
				+ "    TO_CHAR(H_SALIDA,'HH24:MI') AS H_SALIDA\n" + "FROM\n" + "    RDM100.XXFC_PMO_ROUTE_BKP\n"
				+ "WHERE\n" + "    NOM_PROYECTO = '" + nombreProyecto + "'" + "    )\n" + "WHERE\n"
				+ "    ROWNUM <= 10";
		return query;
	}

	/**
	 * Consulta en la tabla "XXFC_PMO_ROUTE_BKP"
	 *
	 * Se dividira en 7 parte por la cantidad de datos a mostrar
	 *
	 * @param nombreProyecto Ingresar el nombre del proyecto que se guardo
	 *                       previamente. Por ejemplo "PruebaBTEP602DDMMAAAA"
	 * @return Retornara la informacion de la ruta previamente guardada.
	 * @throws SQLException
	 */
	public String query2PmoRouteBkp04(String nombreProyecto) throws SQLException {
		String query = "SELECT\n" + "    *\n" + "FROM\n" + "    (\n" + "SELECT\n" + "    RECORRIDO,\n"
				+ "    DIA_INI_ENTREGA,\n" + "    TO_CHAR(H_INI_ENTREGA,'HH24:MI') AS H_INI_ENTREGA,\n"
				+ "    DIA_FIN_ENTREGA,\n" + "    TO_CHAR(H_FIN_ENTREGA,'HH24:MI') AS H_FIN_ENTREGA\n" + "FROM\n"
				+ "    RDM100.XXFC_PMO_ROUTE_BKP\n" + "WHERE\n" + "    NOM_PROYECTO = '" + nombreProyecto + "'"
				+ "    )\n" + "WHERE\n" + "    ROWNUM <= 10";
		return query;
	}

	/**
	 * Consulta en la tabla "XXFC_PMO_ROUTE_BKP"
	 *
	 * Se dividira en 7 parte por la cantidad de datos a mostrar
	 *
	 * @param nombreProyecto Ingresar el nombre del proyecto que se guardo
	 *                       previamente. Por ejemplo "PruebaBTEP602DDMMAAAA"
	 * @return Retornara la informacion de la ruta previamente guardada.
	 * @throws SQLException
	 */
	public String query2PmoRouteBkp05(String nombreProyecto) throws SQLException {
		String query = "SELECT\n" + "    *\n" + "FROM\n" + "    (\n" + "SELECT\n" + "    CICLO_ATN,\n" 
				+ "    DIA_DISP_UNI_CEDIS,\n"
				+ "    TO_CHAR(H_DISP_UNI_CEDIS,'HH24:MI') AS H_DP_UNICEDIS,\n" + "    LS,\n"
				+ "    USER_ID\n" + "FROM\n" + "    RDM100.XXFC_PMO_ROUTE_BKP\n" + "WHERE\n" + "    NOM_PROYECTO = '"
				+ nombreProyecto + "'" + "    )\n" + "WHERE\n" + "    ROWNUM <= 10";
		return query;
	}

	/**
	 * Consulta en la tabla "XXFC_PMO_ROUTE_BKP"
	 *
	 * Se dividira en 7 parte por la cantidad de datos a mostrar
	 *
	 * @param nombreProyecto Ingresar el nombre del proyecto que se guardo
	 *                       previamente. Por ejemplo "PruebaBTEP602DDMMAAAA"
	 * @return Retornara la informacion de la ruta previamente guardada.
	 * @throws SQLException
	 */
	public String query2PmoRouteBkp06(String nombreProyecto) throws SQLException {
		String query = "SELECT\n" + "    *\n" + "FROM\n" + "    (\n" + "SELECT\n" + "    SEM_GEN_PED,\n"
				+ "    SEM_PROG,\n" + "    SEM_INI_PROD AS S_I_PROD,\n" + "    SEM_FIN_PROD AS S_F_PROD,\n" + "    SEM_INI_CARGA AS S_I_CAR,\n"
				+ "    SEM_FIN_CARGA AS S_F_CAR,\n" + "    SEM_SALIDA AS SEM_SAL,\n" + "    SEM_INI_ENTREGA AS S_I_ENT,\n" + "    SEM_FIN_ENTREGA AS S_F_ENT,\n"
				+ "    SEM_DISP_UNI_CEDIS AS S_D_UNICEDIS\n" + "FROM\n" + "    RDM100.XXFC_PMO_ROUTE_BKP\n" + "WHERE\n"
				+ "    NOM_PROYECTO = '" + nombreProyecto + "'" + "    )\n" + "WHERE\n" + "    ROWNUM <= 10";
		return query;
	}

	/**
	 * Consulta en la tabla "XXFC_PMO_ROUTE_BKP"
	 *
	 * Se dividira en 7 parte por la cantidad de datos a mostrar
	 *
	 * @param nombreProyecto Ingresar el nombre del proyecto que se guardo
	 *                       previamente. Por ejemplo "PruebaBTEP602DDMMAAAA"
	 * @return Retornara la informacion de la ruta previamente guardada.
	 * @throws SQLException
	 */
	public String query2PmoRouteBkp07(String nombreProyecto) throws SQLException {
		String query = "SELECT\n" + "    *\n" + "FROM\n" + "    (\n" + "SELECT\n" + "    DIA_INI_VERIFICACION AS D_I_VER,\n"
				+ "    TO_CHAR(H_INI_VERIFICACION,'HH24:MI') AS H_I_VER,\n" + "    DIA_FIN_VERIFICACION AS D_F_VER,\n"
				+ "    TO_CHAR(H_FIN_VERIFICACION,'HH24:MI') AS H_F_VER,\n" + "    DIA_LLEGADA_PT AS D_LLE_PT,\n"
				+ "    TO_CHAR(H_LLEGADA_PT,'HH24:MI') AS H_LLEG_PT\n" + "   \n" + "FROM\n"
				+ "    RDM100.XXFC_PMO_ROUTE_BKP\n" + "WHERE\n" + "    NOM_PROYECTO = '" + nombreProyecto + "'"
				+ "    )\n" + "WHERE\n" + "    ROWNUM <= 10";
		return query;
	}
	
	/**
	 * Consulta en la tabla "XXFC_PMO_ROUTE_BKP"
	 *
	 * Se dividira en 7 parte por la cantidad de datos a mostrar
	 *
	 * @param nombreProyecto Ingresar el nombre del proyecto que se guardo
	 *                       previamente. Por ejemplo "PruebaBTEP602DDMMAAAA"
	 * @return Retornara la informacion de la ruta previamente guardada.
	 * @throws SQLException
	 */
	public String query2PmoRouteBkp07_1(String nombreProyecto) throws SQLException {
		String query = "SELECT\n" + "    *\n" + "FROM\n" + "    (\n" + "SELECT\n" +  "    DIA_SALIDA_PT AS DIA_SAL_PT,\n"
				+ "    TO_CHAR(H_SALIDA_PT,'HH24:MI') AS H_SAL_PT,\n" + "    SEM_INI_VERIFICACION AS SEMINI_VER,\n"
				+ "    SEM_FIN_VERIFICACION AS SEMFI_VER,\n" + "    SEM_LLEGADA_PT AS LLEG_PT,\n" + "    SEM_SALIDA_PT AS SAL_PT\n" + "FROM\n"
				+ "    RDM100.XXFC_PMO_ROUTE_BKP\n" + "WHERE\n" + "    NOM_PROYECTO = '" + nombreProyecto + "'"
				+ "    )\n" + "WHERE\n" + "    ROWNUM <= 10";
		return query;
	}

	/**
	 * Consulta en la tabla "XXFC_PMO_TIENDA_BKP"
	 *
	 * Se dividira en 4 parte por la cantidad de datos a mostrar
	 *
	 * @param nombreProyecto Ingresar el nombre del proyecto que se guardo
	 *                       previamente. Por ejemplo "PruebaBTEP602DDMMAAAA"
	 * @return Retornara la informacion de la ruta previamente guardada.
	 * @throws SQLException
	 */
	public String query2PmoTiendaBkp01(String nombreProyecto) throws SQLException {
		String query = "SELECT\n" + "    *\n" + "FROM\n" + "    (\n" + "        SELECT\n"
				+ "            nom_proyecto,\n" + "            fecha_respaldo,\n" + "            route,\n"
				+ "            dia_gen_ped,\n" + "            dest_id,\n" + "            load_sequence\n"
				+ "        FROM\n" + "            rdm100.xxfc_pmo_tienda_bkp\n" + "        WHERE\n"
				+ "            nom_proyecto = '" + nombreProyecto + "'\n" + "        ORDER BY\n"
				+ "            load_sequence ASC\n" + "    )\n" + "WHERE\n" + "    ROWNUM <= 20";
		return query;
	}

	/**
	 * Consulta en la tabla "XXFC_PMO_TIENDA_BKP"
	 *
	 * Se dividira en 4 parte por la cantidad de datos a mostrar
	 *
	 * @param nombreProyecto Ingresar el nombre del proyecto que se guardo
	 *                       previamente. Por ejemplo "PruebaBTEP602DDMMAAAA"
	 * @return Retornara la informacion de la ruta previamente guardada.
	 * @throws SQLException
	 */
	public String query2PmoTiendaBkp02(String nombreProyecto) throws SQLException {
		String query = "SELECT\n" + "    *\n" + "FROM\n" + "    (\n" + "        SELECT\n"
				+ "            dia_ini_vent_entrega AS d_i_v_ent,\n"
				+ "            TO_CHAR(h_ini_vent_entrega,'HH24:MI') AS h_i_v_ent,\n"
				+ "            dia_fin_vent_entrega AS d_f_v_ent\n"
				+ "        FROM\n" + "            rdm100.xxfc_pmo_tienda_bkp\n"
				+ "        WHERE\n" + "            nom_proyecto = '" + nombreProyecto + "'\n" + "        ORDER BY\n"
				+ "            load_sequence ASC\n" + "    )\n" + "WHERE\n" + "    ROWNUM <= 20";
		return query;
	}
	
	/**
	 * Consulta en la tabla "XXFC_PMO_TIENDA_BKP"
	 *
	 * Se dividira en 4 parte por la cantidad de datos a mostrar
	 *
	 * @param nombreProyecto Ingresar el nombre del proyecto que se guardo
	 *                       previamente. Por ejemplo "PruebaBTEP602DDMMAAAA"
	 * @return Retornara la informacion de la ruta previamente guardada.
	 * @throws SQLException
	 */
	public String query2PmoTiendaBkp02_1(String nombreProyecto) throws SQLException {
		String query = "SELECT\n" + "    *\n" + "FROM\n" + "    (\n" + "        SELECT\n"
				+ "            TO_CHAR(h_fin_vent_entrega,'HH24:MI') AS h_f_v_ent,\n" + "            lsp,\n"
				+ "            cedis,\n" + "            crplaza,\n"
				+ "            tipo_pedido\n" + "        FROM\n" + "            rdm100.xxfc_pmo_tienda_bkp\n"
				+ "        WHERE\n" + "            nom_proyecto = '" + nombreProyecto + "'\n" + "        ORDER BY\n"
				+ "            load_sequence ASC\n" + "    )\n" + "WHERE\n" + "    ROWNUM <= 20";
		return query;
	}
	
	/**
	 * Consulta en la tabla "XXFC_PMO_TIENDA_BKP"
	 *
	 * Se dividira en 4 parte por la cantidad de datos a mostrar
	 *
	 * @param nombreProyecto Ingresar el nombre del proyecto que se guardo
	 *                       previamente. Por ejemplo "PruebaBTEP602DDMMAAAA"
	 * @return Retornara la informacion de la ruta previamente guardada.
	 * @throws SQLException
	 */
	public String query2PmoTiendaBkp03(String nombreProyecto) throws SQLException {
		String query = "SELECT\n" + "    *\n" + "FROM\n" + "    (\n" + "        SELECT\n" + "            crossdock,\n"
				+ "            attribute1,\n" + "            attribute2,\n" + "            status,\n"
				+ "            insert_date,\n" + "            update_date\n" + "        FROM\n"
				+ "            rdm100.xxfc_pmo_tienda_bkp\n" + "        WHERE\n" + "            nom_proyecto = '"
				+ nombreProyecto + "'\n" + "        ORDER BY\n" + "            load_sequence ASC\n" + "    )\n"
				+ "WHERE\n" + "    ROWNUM <= 20";
		return query;
	}

	/**
	 * Consulta en la tabla "XXFC_PMO_TIENDA_BKP"
	 *
	 * Se dividira en 4 parte por la cantidad de datos a mostrar
	 *
	 * @param nombreProyecto Ingresar el nombre del proyecto que se guardo
	 *                       previamente. Por ejemplo "PruebaBTEP602DDMMAAAA"
	 * @return Retornara la informacion de la ruta previamente guardada.
	 * @throws SQLException
	 */
	public String query2PmoTiendaBkp04(String nombreProyecto) throws SQLException {
		String query = "SELECT\n" + "    *\n" + "FROM\n" + "    (\n" + "        SELECT\n" + "            user_id,\n"
				+ "            tipo_distro,\n" + "            dia_est_entrega,\n"
				+ "            TO_CHAR(h_est_entrega,'HH24:MI') AS h_est_entrega,\n" + "            verify_int,\n"
				+ "            verify_ext\n" + "        FROM\n" + "            rdm100.xxfc_pmo_tienda_bkp\n"
				+ "        WHERE\n" + "            nom_proyecto = '" + nombreProyecto + "'\n" + "        ORDER BY\n"
				+ "            load_sequence ASC\n" + "    )\n" + "WHERE\n" + "    ROWNUM <= 20";
		return query;
	}

	/**
	 * Crea la visualizacion de las tablas route y tienda bkp
	 * 
	 * @param dbConecta
	 * @param nombreProyecto
	 * @throws SQLException
	 */
	public void consultaBKP02(SQLUtil dbConecta, String nombreProyecto, boolean tf) throws SQLException {
		SQLResult queryPmoRouteBkp01Result = executeQuery(dbConecta, query2PmoRouteBkp01(nombreProyecto));
		logger.info(String.valueOf(queryPmoRouteBkp01Result.isEmpty()));
		testCase.addQueryEvidenceCurrentStep(queryPmoRouteBkp01Result, tf);
		
		SQLResult queryPmoRouteBkp01_1Result = executeQuery(dbConecta, query2PmoRouteBkp01_1(nombreProyecto));
		logger.info(String.valueOf(queryPmoRouteBkp01_1Result.isEmpty()));
		testCase.addQueryEvidenceCurrentStep(queryPmoRouteBkp01_1Result, tf);

		SQLResult queryPmoRouteBkp02Result = executeQuery(dbConecta, query2PmoRouteBkp02(nombreProyecto));
		logger.info(String.valueOf(queryPmoRouteBkp02Result.isEmpty()));
		testCase.addQueryEvidenceCurrentStep(queryPmoRouteBkp02Result, tf);

		SQLResult queryPmoRouteBkp03Result = executeQuery(dbConecta, query2PmoRouteBkp03(nombreProyecto));
		logger.info(String.valueOf(queryPmoRouteBkp03Result.isEmpty()));
		testCase.addQueryEvidenceCurrentStep(queryPmoRouteBkp03Result, tf);

		SQLResult queryPmoRouteBkp04Result = executeQuery(dbConecta, query2PmoRouteBkp04(nombreProyecto));
		logger.info(String.valueOf(queryPmoRouteBkp04Result.isEmpty()));
		testCase.addQueryEvidenceCurrentStep(queryPmoRouteBkp04Result, tf);

		SQLResult queryPmoRouteBkp05Result = executeQuery(dbConecta, query2PmoRouteBkp05(nombreProyecto));
		logger.info(String.valueOf(queryPmoRouteBkp05Result.isEmpty()));
		testCase.addQueryEvidenceCurrentStep(queryPmoRouteBkp05Result, tf);

		SQLResult queryPmoRouteBkp06Result = executeQuery(dbConecta, query2PmoRouteBkp06(nombreProyecto));
		logger.info(String.valueOf(queryPmoRouteBkp06Result.isEmpty()));
		testCase.addQueryEvidenceCurrentStep(queryPmoRouteBkp06Result, tf);

		SQLResult queryPmoRouteBkp07Result = executeQuery(dbConecta, query2PmoRouteBkp07(nombreProyecto));
		logger.info(String.valueOf(queryPmoRouteBkp07Result.isEmpty()));
		testCase.addQueryEvidenceCurrentStep(queryPmoRouteBkp07Result, tf);
		
		SQLResult queryPmoRouteBkp07_1Result = executeQuery(dbConecta, query2PmoRouteBkp07_1(nombreProyecto));
		logger.info(String.valueOf(queryPmoRouteBkp07_1Result.isEmpty()));
		testCase.addQueryEvidenceCurrentStep(queryPmoRouteBkp07_1Result, tf);

		SQLResult queryPmoTiendaBkp01Result = executeQuery(dbConecta, query2PmoTiendaBkp01(nombreProyecto));
		logger.info(String.valueOf(queryPmoTiendaBkp01Result.isEmpty()));
		testCase.addQueryEvidenceCurrentStep(queryPmoTiendaBkp01Result, tf);

		SQLResult queryPmoTiendaBkp02Result = executeQuery(dbConecta, query2PmoTiendaBkp02(nombreProyecto));
		logger.info(String.valueOf(queryPmoTiendaBkp02Result.isEmpty()));
		testCase.addQueryEvidenceCurrentStep(queryPmoTiendaBkp02Result, tf);
		
		SQLResult queryPmoTiendaBkp02_1Result = executeQuery(dbConecta, query2PmoTiendaBkp02_1(nombreProyecto));
		logger.info(String.valueOf(queryPmoTiendaBkp02_1Result.isEmpty()));
		testCase.addQueryEvidenceCurrentStep(queryPmoTiendaBkp02_1Result, tf);

		SQLResult queryPmoTiendaBkp03Result = executeQuery(dbConecta, query2PmoTiendaBkp03(nombreProyecto));
		logger.info(String.valueOf(queryPmoTiendaBkp03Result.isEmpty()));
		testCase.addQueryEvidenceCurrentStep(queryPmoTiendaBkp03Result, tf);

		SQLResult queryPmoTiendaBkp04Result = executeQuery(dbConecta, query2PmoTiendaBkp04(nombreProyecto));
		logger.info(String.valueOf(queryPmoTiendaBkp04Result.isEmpty()));
		testCase.addQueryEvidenceCurrentStep(queryPmoTiendaBkp04Result, tf);
	}

	/**
	 * Consulta en la tabla "XXFC_PMO_ROUTE"
	 *
	 * Se dividira en 8 parte por la cantidad de datos a mostrar
	 *
	 * @param ruta Ingresar una ruta valida. Por ejemplo "GDL101"
	 * @return Retornara la informacion previamente consultada.
	 * @throws SQLException
	 */
	public String queryPmoRoute01(String ruta) throws SQLException {
		String query = "SELECT\n" + "    *\n" + "FROM\n" + "    (\n" + "SELECT\n" + "    facility_id,\n"
				+ "    cedis,\n" + "    tipo_distro,\n" + "    carrier_code,\n" + "    service_code,\n" + "    route,\n"
				+ "    route_description\n" + "FROM\n" + "    rdm100.xxfc_pmo_route\n" + "WHERE\n" + "    route = '"
				+ ruta + "'" + "    )\n" + "WHERE\n" + "    ROWNUM <= 20";
		return query;
	}

	/**
	 * Consulta en la tabla "XXFC_PMO_ROUTE"
	 *
	 * Se dividira en 8 parte por la cantidad de datos a mostrar
	 *
	 * @param ruta Ingresar una ruta valida. Por ejemplo "GDL101"
	 * @return Retornara la informacion previamente consultada.
	 * @throws SQLException
	 */
	public String queryPmoRoute02(String ruta) throws SQLException {
		String query = "SELECT\n" + "    *\n" + "FROM\n" + "    (\n" + "SELECT\n" + "    dia_gen_ped AS d_gen_ped,\n"
				+ "    TO_CHAR(h_gen_ped,'HH24:MI') AS h_gen_ped,\n" + "    dia_prog,\n"
				+ "    TO_CHAR(h_prog,'HH24:MI') AS h_prog,\n" + "    dia_ini_prod AS d_i_prod,\n"
				+ "    TO_CHAR(h_ini_prod,'HH24:MI') AS h_ini_prod,\n" + "    dia_fin_prod AS d_f_prod,\n"
				+ "    TO_CHAR(h_fin_prod,'HH24:MI') AS h_fin_prod,\n" + "    dia_ini_carga AS d_i_carga,\n"
				+ "    TO_CHAR(h_ini_carga,'HH24:MI') AS h_i_carga\n" + "FROM\n" + "    rdm100.xxfc_pmo_route\n"
				+ "WHERE\n" + "    route = '" + ruta + "'" + "    )\n" + "WHERE\n" + "    ROWNUM <= 20";
		return query;
	}

	/**
	 * Consulta en la tabla "XXFC_PMO_ROUTE"
	 *
	 * Se dividira en 8 parte por la cantidad de datos a mostrar
	 *
	 * @param ruta Ingresar una ruta valida. Por ejemplo "GDL101"
	 * @return Retornara la informacion previamente consultada.
	 * @throws SQLException
	 */
	public String queryPmoRoute03(String ruta) throws SQLException {
		String query = "SELECT\n" + "    *\n" + "FROM\n" + "    (\n" + "SELECT\n" + "    dia_fin_carga,\n"
				+ "    TO_CHAR(h_fin_carga,'HH24:MI') AS h_fin_carga,\n" + "    dia_salida,\n"
				+ "    TO_CHAR(h_salida,'HH24:MI') AS h_salida,\n" + "    recorrido,\n" + "    dia_ini_entrega,\n"
				+ "    TO_CHAR(h_ini_entrega,'HH24:MI') AS h_ini_entrega,\n" + "    dia_fin_entrega,\n"
				+ "    TO_CHAR(h_fin_entrega,'HH24:MI') AS h_fin_entrega\n" + "FROM\n" + "    rdm100.xxfc_pmo_route\n"
				+ "WHERE\n" + "    route = '" + ruta + "'" + "    )\n" + "WHERE\n" + "    ROWNUM <= 20";
		return query;
	}

	/**
	 * Consulta en la tabla "XXFC_PMO_ROUTE"
	 *
	 * Se dividira en 8 parte por la cantidad de datos a mostrar
	 *
	 * @param ruta Ingresar una ruta valida. Por ejemplo "GDL101"
	 * @return Retornara la informacion previamente consultada.
	 * @throws SQLException
	 */
	public String queryPmoRoute04(String ruta) throws SQLException {
		String query = "SELECT\n" + "    *\n" + "FROM\n" + "    (\n" + "SELECT\n" + "    ciclo_atn,\n"
				+ "    ciclo_tot,\n" + "    dia_disp_uni_cedis,\n"
				+ "    TO_CHAR(h_disp_uni_cedis,'HH24:MI') AS h_d_unicedi,\n" + "    ls,\n"
				+ "    po_generation_cutoff_time,\n" + "    po_generation_time,\n" + "    status\n" + "FROM\n"
				+ "    rdm100.xxfc_pmo_route\n" + "WHERE\n" + "    route = '" + ruta + "'" + "    )\n" + "WHERE\n"
				+ "    ROWNUM <= 20";
		return query;
	}

	/**
	 * Consulta en la tabla "XXFC_PMO_ROUTE"
	 *
	 * Se dividira en 8 parte por la cantidad de datos a mostrar
	 *
	 * @param ruta Ingresar una ruta valida. Por ejemplo "GDL101"
	 * @return Retornara la informacion previamente consultada.
	 * @throws SQLException
	 */
	public String queryPmoRoute05(String ruta) throws SQLException {
		String query = "SELECT\n" + "    *\n" + "FROM\n" + "    (\n" + "SELECT\n" + "    attribute1,\n"
				+ "    attribute2,\n" + "    fecha_insert,\n" + "    fecha_update,\n" + "    user_id\n" + "FROM\n"
				+ "    rdm100.xxfc_pmo_route\n" + "WHERE\n" + "    route = '" + ruta + "'" + "    )\n" + "WHERE\n"
				+ "    ROWNUM <= 20";
		return query;
	}

	/**
	 * Consulta en la tabla "XXFC_PMO_ROUTE"
	 *
	 * Se dividira en 8 parte por la cantidad de datos a mostrar
	 *
	 * @param ruta Ingresar una ruta valida. Por ejemplo "GDL101"
	 * @return Retornara la informacion previamente consultada.
	 * @throws SQLException
	 */
	public String queryPmoRoute06(String ruta) throws SQLException {
		String query = "SELECT\n" + "    *\n" + "FROM\n" + "    (\n" + "SELECT\n" + "    sem_gen_ped,\n"
				+ "    sem_prog,\n" + "    sem_ini_prod,\n" + "    sem_fin_prod,\n" + "    sem_ini_carga,\n"
				+ "    sem_fin_carga,\n" + "    sem_salida,\n" + "    sem_ini_entrega,\n" + "    sem_fin_entrega,\n"
				+ "    sem_disp_uni_cedis\n" + "FROM\n" + "    rdm100.xxfc_pmo_route\n" + "WHERE\n" + "    route = '"
				+ ruta + "'" + "    )\n" + "WHERE\n" + "    ROWNUM <= 20";
		return query;
	}

	/**
	 * Consulta en la tabla "XXFC_PMO_ROUTE"
	 *
	 * Se dividira en 8 parte por la cantidad de datos a mostrar
	 *
	 * @param ruta Ingresar una ruta valida. Por ejemplo "GDL101"
	 * @return Retornara la informacion previamente consultada.
	 * @throws SQLException
	 */
	public String queryPmoRoute07(String ruta) throws SQLException {
		String query = "SELECT\n" + "    *\n" + "FROM\n" + "    (\n" + "SELECT\n" + "    dia_ini_verificacion,\n"
				+ "    TO_CHAR(h_ini_verificacion,'HH24:MI') AS h_i_ver,\n" + "    dia_fin_verificacion AS d_f_ver,\n"
				+ "    TO_CHAR(h_fin_verificacion,'HH24:MI') AS h_f_ver,\n" + "    dia_llegada_pt AS d_lle_pt,\n"
				+ "    TO_CHAR(h_llegada_pt,'HH24:MI') AS h_lle_pt,\n" + "    dia_salida_pt d_sal_pt,\n"
				+ "    TO_CHAR(h_salida_pt,'HH24:MI') AS h_sal_pt\n" + "FROM\n" + "    rdm100.xxfc_pmo_route\n"
				+ "WHERE\n" + "    route = '" + ruta + "'" + "    )\n" + "WHERE\n" + "    ROWNUM <= 20";
		return query;
	}

	/**
	 * Consulta en la tabla "XXFC_PMO_ROUTE"
	 *
	 * Se dividira en 8 parte por la cantidad de datos a mostrar
	 *
	 * @param ruta Ingresar una ruta valida. Por ejemplo "GDL101"
	 * @return Retornara la informacion previamente consultada.
	 * @throws SQLException
	 */
	public String queryPmoRoute08(String ruta) throws SQLException {
		String query = "SELECT\n" + "    *\n" + "FROM\n" + "    (\n" + "SELECT\n" + "    sem_ini_verificacion,\n"
				+ "    sem_fin_verificacion,\n" + "    sem_llegada_pt,\n" + "    sem_salida_pt\n" + "FROM\n"
				+ "    rdm100.xxfc_pmo_route\n" + "WHERE\n" + "    route = '" + ruta + "'" + "    )\n" + "WHERE\n"
				+ "    ROWNUM <= 20";
		return query;
	}

	/**
	 * Consulta en la tabla "XXFC_PMO_TIENDA"
	 *
	 * Se dividira en 7 parte por la cantidad de datos a mostrar
	 *
	 * @param ruta  Ingresar una ruta valida. Por ejemplo "GDL101"
	 * @param plaza Ingresar una plaza valida. Por ejemplo "10GUD"
	 * @return Retornara la informacion previamente consultada.
	 * @throws SQLException
	 */
	public String queryPmoTienda01(String plaza, String ruta) throws SQLException {
		String query = "SELECT\n" + "    *\n" + "FROM\n" + "    (\n" + "        SELECT\n" + "            route,\n"
				+ "            dia_gen_ped,\n" + "            dest_id,\n" + "            load_sequence,\n"
				+ "            dia_ini_vent_entrega,\n"
				+ "            TO_CHAR(h_ini_vent_entrega,'HH24:MI') AS h_ini_vent_entrega,\n"
				+ "            dia_fin_vent_entrega,\n"
				+ "            TO_CHAR(h_fin_vent_entrega,'HH24:MI') AS h_fin_vent_entrega\n" + "        FROM\n"
				+ "            rdm100.xxfc_pmo_tienda\n" + "        WHERE\n" + "            crplaza = '" + plaza + "'\n"
				+ "            AND route = '" + ruta + "'\n" + "        ORDER BY\n" + "            load_sequence ASC\n"
				+ "    )\n" + "WHERE\n" + "    ROWNUM <= 20";
		return query;
	}

	/**
	 * Consulta en la tabla "XXFC_PMO_TIENDA"
	 *
	 * Se dividira en 7 parte por la cantidad de datos a mostrar
	 *
	 * @param ruta  Ingresar una ruta valida. Por ejemplo "GDL101"
	 * @param plaza Ingresar una plaza valida. Por ejemplo "10GUD"
	 * @return Retornara la informacion previamente consultada.
	 * @throws SQLException
	 */
	public String queryPmoTienda02(String plaza, String ruta) throws SQLException {
		String query = "SELECT\n" + "    *\n" + "FROM\n" + "    (\n" + "        SELECT\n" + "            lsp,\n"
				+ "            lssep1,\n" + "            lssep2,\n" + "            cedis,\n" + "            crplaza,\n"
				+ "            tipo_pedido\n" + "        FROM\n" + "            rdm100.xxfc_pmo_tienda\n"
				+ "        WHERE\n" + "            crplaza = '" + plaza + "'\n" + "            AND route = '" + ruta
				+ "'\n" + "        ORDER BY\n" + "            load_sequence ASC\n" + "    )\n" + "WHERE\n"
				+ "    ROWNUM <= 20";
		return query;
	}

	/**
	 * Consulta en la tabla "XXFC_PMO_TIENDA"
	 *
	 * Se dividira en 7 parte por la cantidad de datos a mostrar
	 *
	 * @param ruta  Ingresar una ruta valida. Por ejemplo "GDL101"
	 * @param plaza Ingresar una plaza valida. Por ejemplo "10GUD"
	 * @return Retornara la informacion previamente consultada.
	 * @throws SQLException
	 */
	public String queryPmoTienda03(String plaza, String ruta) throws SQLException {
		String query = "SELECT\n" + "    *\n" + "FROM\n" + "    (\n" + "        SELECT\n" + "            crossdock,\n"
				+ "            attribute1,\n" + "            attribute2,\n" + "            status,\n"
				+ "            insert_date,\n" + "            update_date\n" + "        FROM\n"
				+ "            rdm100.xxfc_pmo_tienda\n" + "        WHERE\n" + "            crplaza = '" + plaza + "'\n"
				+ "            AND route = '" + ruta + "'\n" + "        ORDER BY\n" + "            load_sequence ASC\n"
				+ "    )\n" + "WHERE\n" + "    ROWNUM <= 20";
		return query;
	}

	/**
	 * Consulta en la tabla "XXFC_PMO_TIENDA"
	 *
	 * Se dividira en 7 parte por la cantidad de datos a mostrar
	 *
	 * @param ruta  Ingresar una ruta valida. Por ejemplo "GDL101"
	 * @param plaza Ingresar una plaza valida. Por ejemplo "10GUD"
	 * @return Retornara la informacion previamente consultada.
	 * @throws SQLException
	 */
	public String queryPmoTienda04(String plaza, String ruta) throws SQLException {
		String query = "SELECT\n" + "    *\n" + "FROM\n" + "    (\n" + "        SELECT\n" + "            user_id,\n"
				+ "            tipo_distro,\n" + "            dia_est_entrega,\n"
				+ "            TO_CHAR(h_est_entrega,'HH24:MI') AS h_est_entrega,\n" + "            verify_int,\n"
				+ "            verify_ext\n" + "        FROM\n" + "            rdm100.xxfc_pmo_tienda\n"
				+ "        WHERE\n" + "            crplaza = '" + plaza + "'\n" + "            AND route = '" + ruta
				+ "'\n" + "        ORDER BY\n" + "            load_sequence ASC\n" + "    )\n" + "WHERE\n"
				+ "    ROWNUM <= 20";
		return query;
	}

	/**
	 * Genera la visualizacion de la tabla Route y Tienda
	 * 
	 * @param dbConecta
	 * @param plaza
	 * @param ruta
	 * @throws SQLException
	 */
	public void consultaRutaTienda(SQLUtil dbConecta, String plaza, String ruta) throws SQLException {

		SQLResult queryPmoRoute01Result = executeQuery(dbConecta, queryPmoRoute01(ruta));
		logger.info(String.valueOf(queryPmoRoute01Result.isEmpty()));
		testCase.addQueryEvidenceCurrentStep(queryPmoRoute01Result);

		SQLResult queryPmoRoute02Result = executeQuery(dbConecta, queryPmoRoute02(ruta));
		logger.info(String.valueOf(queryPmoRoute02Result.isEmpty()));
		testCase.addQueryEvidenceCurrentStep(queryPmoRoute02Result);

		SQLResult queryPmoRoute03Result = executeQuery(dbConecta, queryPmoRoute03(ruta));
		logger.info(String.valueOf(queryPmoRoute03Result.isEmpty()));
		testCase.addQueryEvidenceCurrentStep(queryPmoRoute03Result);

		SQLResult queryPmoRoute04Result = executeQuery(dbConecta, queryPmoRoute04(ruta));
		logger.info(String.valueOf(queryPmoRoute04Result.isEmpty()));
		testCase.addQueryEvidenceCurrentStep(queryPmoRoute04Result);

		SQLResult queryPmoRoute05Result = executeQuery(dbConecta, queryPmoRoute05(ruta));
		logger.info(String.valueOf(queryPmoRoute05Result.isEmpty()));
		testCase.addQueryEvidenceCurrentStep(queryPmoRoute05Result);

		SQLResult queryPmoRoute06Result = executeQuery(dbConecta, queryPmoRoute06(ruta));
		logger.info(String.valueOf(queryPmoRoute06Result.isEmpty()));
		testCase.addQueryEvidenceCurrentStep(queryPmoRoute06Result);

		SQLResult queryPmoRoute07Result = executeQuery(dbConecta, queryPmoRoute07(ruta));
		logger.info(String.valueOf(queryPmoRoute07Result.isEmpty()));
		testCase.addQueryEvidenceCurrentStep(queryPmoRoute07Result);

		SQLResult queryPmoRoute08Result = executeQuery(dbConecta, queryPmoRoute08(ruta));
		logger.info(String.valueOf(queryPmoRoute08Result.isEmpty()));
		testCase.addQueryEvidenceCurrentStep(queryPmoRoute08Result);

		SQLResult queryPmoTienda01Result = executeQuery(dbConecta, queryPmoTienda01(plaza, ruta));
		logger.info(String.valueOf(queryPmoTienda01Result.isEmpty()));
		testCase.addQueryEvidenceCurrentStep(queryPmoTienda01Result);

		SQLResult queryPmoTienda02Result = executeQuery(dbConecta, queryPmoTienda02(plaza, ruta));
		logger.info(String.valueOf(queryPmoTienda02Result.isEmpty()));
		testCase.addQueryEvidenceCurrentStep(queryPmoTienda02Result);

		SQLResult queryPmoTienda03Result = executeQuery(dbConecta, queryPmoTienda03(plaza, ruta));
		logger.info(String.valueOf(queryPmoTienda03Result.isEmpty()));
		testCase.addQueryEvidenceCurrentStep(queryPmoTienda03Result);

		SQLResult queryPmoTienda04Result = executeQuery(dbConecta, queryPmoTienda04(plaza, ruta));
		logger.info(String.valueOf(queryPmoTienda04Result.isEmpty()));
		testCase.addQueryEvidenceCurrentStep(queryPmoTienda04Result);
	}

	/**
	 * Consulta en la tabla "RDM100.XXFC_PMO_CARGA_STG"
	 *
	 * Se dividira en 6 parte por la cantidad de datos a mostrar
	 *
	 * @return Retornara la informacion de la tabla previamente carga desde IMA.
	 * @throws SQLException
	 */
	public String queryPmoCargaStg01() throws SQLException {
		String query = "SELECT\n" + "    *\n" + "FROM\n" + "    (\n" + "        SELECT\n" + "            CEDIS,\n"
				+ "            CRPLAZA,\n" + "            DEST_ID,\n" + "            NOMBRE,\n" + "            CR,\n"
				+ "            TIPO_RUTA,\n" + "            SERVICE_CODE AS SERV_CODE \n" + "        FROM\n"
				+ "            RDM100.XXFC_PMO_CARGA_STG\n" + "    )\n" + "WHERE\n" + "    ROWNUM < 20";
		return query;
	}

	/**
	 * Consulta en la tabla "RDM100.XXFC_PMO_CARGA_STG"
	 *
	 * Se dividira en 6 parte por la cantidad de datos a mostrar
	 *
	 * @return Retornara la informacion de la tabla previamente carga desde IMA.
	 * @throws SQLException
	 */
	public String queryPmoCargaStg02() throws SQLException {
		String query = "SELECT\n" + "    *\n" + "FROM\n" + "    (\n" + "        SELECT\n" + "            ROUTE,\n"
				+ "            ROUTE_DESCRIPTION AS R_DESCRIPT,\n" + "            TIPO_PEDIDOS_RUTA AS TIPO_PED_RUTA ,\n" + "            DIA_GEN_PED,\n"
				+ "            DIA_RECEPCION,\n" + "            DIA_LIBERACION,\n" + "            H_LIBERACION\n"
				+ "        FROM\n" + "            RDM100.XXFC_PMO_CARGA_STG\n" + "    )\n" + "WHERE\n"
				+ "    ROWNUM < 20";
		return query;
	}

	/**
	 * Consulta en la tabla "RDM100.XXFC_PMO_CARGA_STG"
	 *
	 * Se dividira en 6 parte por la cantidad de datos a mostrar
	 *
	 * @return Retornara la informacion de la tabla previamente carga desde IMA.
	 * @throws SQLException
	 */
	public String queryPmoCargaStg03() throws SQLException {
		String query = "SELECT\n" + "    *\n" + "FROM\n" + "    (\n" + "        SELECT\n"
				+ "            DIA_INI_PROD AS D_I_PROD,\n" + "            H_INI_PROD AS H_I_PROD,\n" + "            DIA_INI_CARGA AS D_I_CARGA,\n"
				+ "            H_INI_CARGA AS H_I_CARGA,\n" + "            DIA_SALIDA,\n" + "            H_SALIDA,\n"
				+ "            DIA_SALIDA_PT AS D_SAL_PT,\n" + "            H_SALIDA_PT AS H_SAL_PT\n" + "        FROM\n"
				+ "            RDM100.XXFC_PMO_CARGA_STG\n" + "    )\n" + "WHERE\n" + "    ROWNUM < 20";
		return query;
	}

	/**
	 * Consulta en la tabla "RDM100.XXFC_PMO_CARGA_STG"
	 *
	 * Se dividira en 6 parte por la cantidad de datos a mostrar
	 *
	 * @return Retornara la informacion de la tabla previamente carga desde IMA.
	 * @throws SQLException
	 */
	public String queryPmoCargaStg04() throws SQLException {
		String query = "SELECT\n" + "    *\n" + "FROM\n" + "    (\n" + "        SELECT\n" + "            RECORRIDO,\n"
				+ "            CICLO_ATN_EN_TIENDA AS C_ATN_TIE,\n" + "            CICLO_TOT,\n"
				+ "            DIA_DISP_UNI_CEDIS AS D_DP_UNICEDIS,\n" + "            H_DISP_UNI_CEDIS AS H_DP_UNICEDIS,\n" + "            SEC_CARGA\n"
				+ "        FROM\n" + "            RDM100.XXFC_PMO_CARGA_STG\n" + "    )\n" + "WHERE\n"
				+ "    ROWNUM < 20";
		return query;
	}

	/**
	 * Consulta en la tabla "RDM100.XXFC_PMO_CARGA_STG"
	 *
	 * Se dividira en 6 parte por la cantidad de datos a mostrar
	 *
	 * @return Retornara la informacion de la tabla previamente carga desde IMA.
	 * @throws SQLException
	 */
	public String queryPmoCargaStg05() throws SQLException {
		String query = "SELECT\n" + "    *\n" + "FROM\n" + "    (\n" + "        SELECT\n"
				+ "            DIA_INI_VENT_ENTREGA AS D_I_V_ENT,\n" + "            H_INI_VENT_ENTREGA AS H_I_V_ENT,\n"
				+ "            DIA_FIN_VENT_ENTREGA AS D_F_V_ENT,\n" + "            H_FIN_VENT_ENTREGA AS H_F_V_ENT,\n"
				+ "            LINEA_DE_SURTIDO AS LN_SURTIDO,\n" + "            HORA_OBJETIVO\n" + "        FROM\n"
				+ "            RDM100.XXFC_PMO_CARGA_STG\n" + "    )\n" + "WHERE\n" + "    ROWNUM < 20";
		return query;
	}

	/**
	 * Consulta en la tabla "RDM100.XXFC_PMO_CARGA_STG"
	 *
	 * Se dividira en 6 parte por la cantidad de datos a mostrar
	 *
	 * @return Retornara la informacion de la tabla previamente carga desde IMA.
	 * @throws SQLException
	 */
	public String queryPmoCargaStg06() throws SQLException {
		String query = "SELECT\n" + "    *\n" + "FROM\n" + "    (\n" + "        SELECT\n" + "            VERIF_INT,\n"
				+ "            VERIF_EXT,\n" + "            ERROR,\n" + "            COD_ERROR\n" + "        FROM\n"
				+ "            RDM100.XXFC_PMO_CARGA_STG\n" + "    )\n" + "WHERE\n" + "    ROWNUM < 20";
		return query;
	}

	/**
	 * Genera visualizacion de PMO CARGA STG
	 * 
	 * @param dbConecta
	 * @throws SQLException
	 */
	public void consultaPmoCargaStg(SQLUtil dbConecta, boolean tf) throws SQLException {

		SQLResult queryPmoCargaStg01Result = executeQuery(dbConecta, queryPmoCargaStg01());
		logger.info(String.valueOf(queryPmoCargaStg01Result.isEmpty()));
		testCase.addQueryEvidenceCurrentStep(queryPmoCargaStg01Result, tf);

		SQLResult queryPmoCargaStg02Result = executeQuery(dbConecta, queryPmoCargaStg02());
		logger.info(String.valueOf(queryPmoCargaStg02Result.isEmpty()));
		testCase.addQueryEvidenceCurrentStep(queryPmoCargaStg02Result, tf);

		SQLResult queryPmoCargaStg03Result = executeQuery(dbConecta, queryPmoCargaStg03());
		logger.info(String.valueOf(queryPmoCargaStg03Result.isEmpty()));
		testCase.addQueryEvidenceCurrentStep(queryPmoCargaStg03Result, tf);

		SQLResult queryPmoCargaStg04Result = executeQuery(dbConecta, queryPmoCargaStg04());
		logger.info(String.valueOf(queryPmoCargaStg04Result.isEmpty()));
		testCase.addQueryEvidenceCurrentStep(queryPmoCargaStg04Result, tf);

		SQLResult queryPmoCargaStg05Result = executeQuery(dbConecta, queryPmoCargaStg05());
		logger.info(String.valueOf(queryPmoCargaStg05Result.isEmpty()));
		testCase.addQueryEvidenceCurrentStep(queryPmoCargaStg05Result, tf);

		SQLResult queryPmoCargaStg06Result = executeQuery(dbConecta, queryPmoCargaStg06());
		logger.info(String.valueOf(queryPmoCargaStg06Result.isEmpty()));
		testCase.addQueryEvidenceCurrentStep(queryPmoCargaStg06Result, tf);

	}

	/**
	 * Consulta en la tabla "XXFC_PMO_CONFIG_RUTA_CICLO"
	 *
	 * @return Retornara la informacion De la configuracion RUTA CICLO.
	 * @throws SQLException
	 */
	public String queryPmoConfigRutaCiclo() throws SQLException {
		String query = "SELECT \n" + "    TIPO_DISTRIBUCION AS TIPO_DISTRI,\n" + "    TIPO_RUTA,\n" + "    PLAZA,\n"
				+ "    TIEMPO_CICLO,\n" + "    TO_CHAR(H_PRODUCCION, 'HH24:MI') AS H_PROD,\n"
				+ "    TO_CHAR(H_VERIFICACION, 'HH24:MI') AS H_VERIFI,\n"
				+ "    TO_CHAR(H_CARGA, 'HH24:MI') AS H_CARGA,\n"
				+ "    TO_CHAR(H_CORTE_JIT, 'HH24:MI') AS H_CORTE_JIT,\n"
				+ "    TO_CHAR(H_CORTE_ENVIO, 'HH24:MI') AS H_CORTE_ENVIO\n" + "FROM RDM100.XXFC_PMO_CONFIG_RUTA_CICLO";
		return query;
	}

	/**
	 * Consulta en la tabla "XXFC_PMO_ROUTE_LOAD"
	 *
	 * Se dividira en 9 parte por la cantidad de datos a mostrar
	 *
	 * @return Retornara la informacion previamente consultada.
	 * @throws SQLException
	 */
	public String queryPmoRouteLoad01() throws SQLException {
		String query = "SELECT \n" + "    * \n" + "FROM\n" + "    (\n" + "        SELECT \n"
				+ "            FACILITY_ID, CEDIS, TIPO_DISTRO, CARRIER_CODE, SERVICE_CODE, ROUTE, ROUTE_DESCRIPTION \n"
				+ "        FROM \n" + "            RDM100.XXFC_PMO_ROUTE_LOAD\n" + "    )\n" + "WHERE ROWNUM < 20";
		return query;
	}

	/**
	 * Consulta en la tabla "XXFC_PMO_ROUTE_LOAD"
	 *
	 * Se dividira en 9 parte por la cantidad de datos a mostrar
	 *
	 * @return Retornara la informacion previamente consultada.
	 * @throws SQLException
	 */
	public String queryPmoRouteLoad02() throws SQLException {
		String query = "SELECT \n" + "    * \n" + "FROM\n" + "    (\n" + "        SELECT \n"
				+ "            DIA_GEN_PED,\n" + "            TO_CHAR(H_GEN_PED, 'HH24:MI') AS H_GEN_PED,\n"
				+ "            DIA_PROG,\n" + "            TO_CHAR(H_PROG, 'HH24:MI') AS H_PROG,\n"
				+ "            DIA_INI_PROD,\n" + "            TO_CHAR(H_INI_PROD, 'HH24:MI') AS H_INI_PROD,\n"
				+ "            DIA_FIN_PROD,\n" + "            TO_CHAR(H_FIN_PROD, 'HH24:MI') AS H_FIN_PROD\n"
				+ "        FROM \n" + "            RDM100.XXFC_PMO_ROUTE_LOAD\n" + "    )\n" + "WHERE ROWNUM < 20";
		return query;
	}

	/**
	 * Consulta en la tabla "XXFC_PMO_ROUTE_LOAD"
	 *
	 * Se dividira en 9 parte por la cantidad de datos a mostrar
	 *
	 * @return Retornara la informacion previamente consultada.
	 * @throws SQLException
	 */
	public String queryPmoRouteLoad03() throws SQLException {
		String query = "SELECT \n" + "    * \n" + "FROM\n" + "    (\n" + "        SELECT \n"
				+ "            DECODE(DIA_INI_CARGA,1,'DO',2,'LU',3,'MA',4,'MI',5,'JU',6,'VI',7,'SA') AS DIA_INI_CARGA,\n"
				+ "            TO_CHAR(H_INI_CARGA, 'HH24:MI') AS H_INI_CARGA,\n"
				+ "            DECODE(DIA_FIN_CARGA,1,'DO',2,'LU',3,'MA',4,'MI',5,'JU',6,'VI',7,'SA') AS DIA_FIN_CARGA,\n"
				+ "            TO_CHAR(H_FIN_CARGA, 'HH24:MI') AS H_FIN_CARGA,\n"
				+ "            DECODE(DIA_SALIDA,1,'DO',2,'LU',3,'MA',4,'MI',5,'JU',6,'VI',7,'SA') AS DIA_SALIDA,\n"
				+ "            TO_CHAR(H_SALIDA, 'HH24:MI') AS H_SALIDA,\n" + "            RECORRIDO\n"
				+ "        FROM \n" + "            RDM100.XXFC_PMO_ROUTE_LOAD\n" + "    )\n" + "WHERE ROWNUM < 20";
		return query;
	}

	/**
	 * Consulta en la tabla "XXFC_PMO_ROUTE_LOAD"
	 *
	 * Se dividira en 9 parte por la cantidad de datos a mostrar
	 *
	 * @return Retornara la informacion previamente consultada.
	 * @throws SQLException
	 */
	public String queryPmoRouteLoad04() throws SQLException {
		String query = "SELECT \n" + "    * \n" + "FROM\n" + "    (\n" + "        SELECT \n"
				+ "            DECODE(DIA_INI_ENTREGA,1,'DO',2,'LU',3,'MA',4,'MI',5,'JU',6,'VI',7,'SA') AS DIA_INI_ENTREGA,\n"
				+ "            TO_CHAR(H_INI_ENTREGA, 'HH24:MI') AS H_INI_ENTREGA,\n"
				+ "            DECODE(DIA_FIN_ENTREGA,1,'DO',2,'LU',3,'MA',4,'MI',5,'JU',6,'VI',7,'SA') AS DIA_FIN_ENTREGA,\n"
				+ "            TO_CHAR(H_FIN_ENTREGA, 'HH24:MI') AS H_FIN_ENTREGA,\n"
				+ "            CICLO_ATN, CICLO_TOT, \n" + "            DIA_DISP_UNI_CEDIS,\n"
				+ "            TO_CHAR(H_DISP_UNI_CEDIS, 'HH24:MI') AS H_DISP_UNI_CEDIS\n" + "        FROM \n"
				+ "            RDM100.XXFC_PMO_ROUTE_LOAD\n" + "    )\n" + "WHERE ROWNUM < 20";
		return query;
	}

	/**
	 * Consulta en la tabla "XXFC_PMO_ROUTE_LOAD"
	 *
	 * Se dividira en 9 parte por la cantidad de datos a mostrar
	 *
	 * @return Retornara la informacion previamente consultada.
	 * @throws SQLException
	 */
	public String queryPmoRouteLoad05() throws SQLException {
		String query = "SELECT \n" + "    * \n" + "FROM\n" + "    (\n" + "        SELECT \n"
				+ "            LS, PO_GENERATION_CUTOFF_TIME, PO_GENERATION_TIME, STATUS,\n"
				+ "            ATTRIBUTE1, ATTRIBUTE2\n" + "        FROM \n"
				+ "            RDM100.XXFC_PMO_ROUTE_LOAD\n" + "    )\n" + "WHERE ROWNUM < 20";
		return query;
	}

	/**
	 * Consulta en la tabla "XXFC_PMO_ROUTE_LOAD"
	 *
	 * Se dividira en 9 parte por la cantidad de datos a mostrar
	 *
	 * @return Retornara la informacion previamente consultada.
	 * @throws SQLException
	 */
	public String queryPmoRouteLoad06() throws SQLException {
		String query = "SELECT \n" + "    * \n" + "FROM\n" + "    (\n" + "        SELECT \n"
				+ "            FECHA_INSERT, FECHA_UPDATE, USER_ID, SEM_GEN_PED, SEM_PROG\n" + "        FROM \n"
				+ "            RDM100.XXFC_PMO_ROUTE_LOAD\n" + "    )\n" + "WHERE ROWNUM < 20";
		return query;
	}

	/**
	 * Consulta en la tabla "XXFC_PMO_ROUTE_LOAD"
	 *
	 * Se dividira en 9 parte por la cantidad de datos a mostrar
	 *
	 * @return Retornara la informacion previamente consultada.
	 * @throws SQLException
	 */
	public String queryPmoRouteLoad07() throws SQLException {
		String query = "SELECT \n" + "    * \n" + "FROM\n" + "    (\n" + "        SELECT \n"
				+ "            SEM_INI_PROD, SEM_FIN_PROD, SEM_INI_CARGA, SEM_FIN_CARGA, SEM_SALIDA, SEM_INI_ENTREGA, SEM_FIN_ENTREGA\n"
				+ "        FROM \n" + "            RDM100.XXFC_PMO_ROUTE_LOAD\n" + "    )\n" + "WHERE ROWNUM < 20";
		return query;
	}

	/**
	 * Consulta en la tabla "XXFC_PMO_ROUTE_LOAD"
	 *
	 * Se dividira en 9 parte por la cantidad de datos a mostrar
	 *
	 * @return Retornara la informacion previamente consultada.
	 * @throws SQLException
	 */
	public String queryPmoRouteLoad08() throws SQLException {
		String query = "SELECT \n" + "    * \n" + "FROM\n" + "    (\n" + "        SELECT \n"
				+ "            SEM_DISP_UNI_CEDIS, DIA_INI_VERIFICACION,\n"
				+ "            TO_CHAR(H_INI_VERIFICACION, 'HH24:MI') AS H_INI_VERI,\n"
				+ "            DIA_FIN_VERIFICACION,\n"
				+ "            TO_CHAR(H_FIN_VERIFICACION, 'HH24:MI') AS H_FIN_VERI,\n"
				+ "            DIA_LLEGADA_PT,\n" + "            TO_CHAR(H_LLEGADA_PT, 'HH24:MI') AS H_LLEGADA_PT\n"
				+ "        FROM \n" + "            RDM100.XXFC_PMO_ROUTE_LOAD\n" + "    )\n" + "WHERE ROWNUM < 20";
		return query;
	}

	/**
	 * Consulta en la tabla "XXFC_PMO_ROUTE_LOAD"
	 *
	 * Se dividira en 9 parte por la cantidad de datos a mostrar
	 *
	 * @return Retornara la informacion previamente consultada.
	 * @throws SQLException
	 */
	public String queryPmoRouteLoad09() throws SQLException {
		String query = "SELECT \n" + "    * \n" + "FROM\n" + "    (\n" + "        SELECT \n"
				+ "            DIA_SALIDA_PT,\n" + "            TO_CHAR(H_SALIDA_PT, 'HH24:MI') AS H_SALIDA_PT,\n"
				+ "            SEM_INI_VERIFICACION, SEM_FIN_VERIFICACION, SEM_LLEGADA_PT, SEM_SALIDA_PT, ERROR, COD_ERROR\n"
				+ "        FROM \n" + "            RDM100.XXFC_PMO_ROUTE_LOAD\n" + "    )\n" + "WHERE ROWNUM < 20";
		return query;
	}

	/**
	 * Consulta en la tabla "XXFC_PMO_TIENDA_LOAD"
	 *
	 * Se dividira en 4 parte por la cantidad de datos a mostrar
	 *
	 * @return Retornara la informacion previamente consultada.
	 * @throws SQLException
	 */
	public String queryPmoTiendaLoad01() throws SQLException {
		String query = "SELECT \n" + "    * \n" + "FROM\n" + "    (\n" + "        SELECT\n" + "            ROUTE,\n"
				+ "            DIA_GEN_PED,\n" + "            DEST_ID,\n" + "            LOAD_SEQUENCE,\n"
				+ "            DIA_INI_VENT_ENTREGA AS DIA_INI_VENT_ENT,\n"
				+ "            TO_CHAR(H_INI_VENT_ENTREGA,'HH24:MI') as H_INI_VENT_ENT,\n"
				+ "            DIA_FIN_VENT_ENTREGA AS DIA_FIN_VENT_ENT,\n"
				+ "            TO_CHAR(H_FIN_VENT_ENTREGA,'HH24:MI') as H_FIN_VENT_ENT\n" + "        FROM\n"
				+ "            RDM100.XXFC_PMO_TIENDA_LOAD\n" + "    )        \n" + "WHERE ROWNUM < 20";
		return query;
	}

	/**
	 * Consulta en la tabla "XXFC_PMO_TIENDA_LOAD"
	 *
	 * Se dividira en 4 parte por la cantidad de datos a mostrar
	 *
	 * @return Retornara la informacion previamente consultada.
	 * @throws SQLException
	 */
	public String queryPmoTiendaLoad02() throws SQLException {
		String query = "SELECT \n" + "    * \n" + "FROM\n" + "    (\n" + "        SELECT\n" + "            LSP,\n"
				+ "            LSSEP1,\n" + "            LSSEP2,\n" + "            CEDIS,\n" + "            CRPLAZA,\n"
				+ "            TIPO_PEDIDO,\n" + "            CROSSDOCK\n" + "        FROM\n"
				+ "            RDM100.XXFC_PMO_TIENDA_LOAD\n" + "    )        \n" + "WHERE ROWNUM < 20";
		return query;
	}

	/**
	 * Consulta en la tabla "XXFC_PMO_TIENDA_LOAD"
	 *
	 * Se dividira en 4 parte por la cantidad de datos a mostrar
	 *
	 * @return Retornara la informacion previamente consultada.
	 * @throws SQLException
	 */
	public String queryPmoTiendaLoad03() throws SQLException {
		String query = "SELECT \n" + "    * \n" + "FROM\n" + "    (\n" + "        SELECT\n"
				+ "            ATTRIBUTE1,\n" + "            ATTRIBUTE2,\n" + "            STATUS,\n"
				+ "            INSERT_DATE,\n" + "            UPDATE_DATE,\n" + "            USER_ID,\n"
				+ "            TIPO_DISTRO\n" + "        FROM\n" + "            RDM100.XXFC_PMO_TIENDA_LOAD\n"
				+ "    )        \n" + "WHERE ROWNUM < 20";
		return query;
	}

	/**
	 * Consulta en la tabla "XXFC_PMO_TIENDA_LOAD"
	 *
	 * Se dividira en 4 parte por la cantidad de datos a mostrar
	 *
	 * @return Retornara la informacion previamente consultada.
	 * @throws SQLException
	 */
	public String queryPmoTiendaLoad04() throws SQLException {
		String query = "SELECT \n" + "    * \n" + "FROM\n" + "    (\n" + "        SELECT\n"
				+ "            DIA_EST_ENTREGA,\n" + "            H_EST_ENTREGA,\n" + "            VERIFY_INT,\n"
				+ "            VERIFY_EXT,\n" + "            ERROR,\n" + "            COD_ERROR\n" + "        FROM\n"
				+ "            RDM100.XXFC_PMO_TIENDA_LOAD\n" + "    )        \n" + "WHERE ROWNUM < 20";
		return query;
	}

	/**
	 * Genera visualizacion de PMO LOADS
	 * 
	 * @param dbConecta
	 * @throws SQLException
	 */
	public void consultaPmoLoads(SQLUtil dbConecta, Boolean tf) throws SQLException {

		SQLResult queryPmoConfigRutaCicloResult = executeQuery(dbConecta, queryPmoConfigRutaCiclo());
		logger.info(String.valueOf(queryPmoConfigRutaCicloResult.isEmpty()));
		testCase.addQueryEvidenceCurrentStep(queryPmoConfigRutaCicloResult, tf);

		SQLResult queryPmoRouteLoad01Result = executeQuery(dbConecta, queryPmoRouteLoad01());
		logger.info(String.valueOf(queryPmoRouteLoad01Result.isEmpty()));
		testCase.addQueryEvidenceCurrentStep(queryPmoRouteLoad01Result, tf);

		SQLResult queryPmoRouteLoad02Result = executeQuery(dbConecta, queryPmoRouteLoad02());
		logger.info(String.valueOf(queryPmoRouteLoad02Result.isEmpty()));
		testCase.addQueryEvidenceCurrentStep(queryPmoRouteLoad02Result, tf);

		SQLResult queryPmoRouteLoad03Result = executeQuery(dbConecta, queryPmoRouteLoad03());
		logger.info(String.valueOf(queryPmoRouteLoad03Result.isEmpty()));
		testCase.addQueryEvidenceCurrentStep(queryPmoRouteLoad03Result, tf);

		SQLResult queryPmoRouteLoad04Result = executeQuery(dbConecta, queryPmoRouteLoad04());
		logger.info(String.valueOf(queryPmoRouteLoad04Result.isEmpty()));
		testCase.addQueryEvidenceCurrentStep(queryPmoRouteLoad04Result, tf);

		SQLResult queryPmoRouteLoad05Result = executeQuery(dbConecta, queryPmoRouteLoad05());
		logger.info(String.valueOf(queryPmoRouteLoad05Result.isEmpty()));
		testCase.addQueryEvidenceCurrentStep(queryPmoRouteLoad05Result, tf);

		SQLResult queryPmoRouteLoad06Result = executeQuery(dbConecta, queryPmoRouteLoad06());
		logger.info(String.valueOf(queryPmoRouteLoad06Result.isEmpty()));
		testCase.addQueryEvidenceCurrentStep(queryPmoRouteLoad06Result, tf);

		SQLResult queryPmoRouteLoad07Result = executeQuery(dbConecta, queryPmoRouteLoad07());
		logger.info(String.valueOf(queryPmoRouteLoad07Result.isEmpty()));
		testCase.addQueryEvidenceCurrentStep(queryPmoRouteLoad07Result, tf);

		SQLResult queryPmoRouteLoad08Result = executeQuery(dbConecta, queryPmoRouteLoad08());
		logger.info(String.valueOf(queryPmoRouteLoad08Result.isEmpty()));
		testCase.addQueryEvidenceCurrentStep(queryPmoRouteLoad08Result, tf);

		SQLResult queryPmoRouteLoad09Result = executeQuery(dbConecta, queryPmoRouteLoad09());
		logger.info(String.valueOf(queryPmoRouteLoad09Result.isEmpty()));
		testCase.addQueryEvidenceCurrentStep(queryPmoRouteLoad09Result, tf);

		SQLResult queryPmoTiendaLoad01Result = executeQuery(dbConecta, queryPmoTiendaLoad01());
		logger.info(String.valueOf(queryPmoTiendaLoad01Result.isEmpty()));
		testCase.addQueryEvidenceCurrentStep(queryPmoTiendaLoad01Result, tf);

		SQLResult queryPmoTiendaLoad02Result = executeQuery(dbConecta, queryPmoTiendaLoad02());
		logger.info(String.valueOf(queryPmoTiendaLoad02Result.isEmpty()));
		testCase.addQueryEvidenceCurrentStep(queryPmoTiendaLoad02Result, tf);

		SQLResult queryPmoTiendaLoad03Result = executeQuery(dbConecta, queryPmoTiendaLoad03());
		logger.info(String.valueOf(queryPmoTiendaLoad03Result.isEmpty()));
		testCase.addQueryEvidenceCurrentStep(queryPmoTiendaLoad03Result, tf);

		SQLResult queryPmoTiendaLoad04Result = executeQuery(dbConecta, queryPmoTiendaLoad04());
		logger.info(String.valueOf(queryPmoTiendaLoad04Result.isEmpty()));
		testCase.addQueryEvidenceCurrentStep(queryPmoTiendaLoad04Result, tf);
	}

	/*
	 * Queries Backup tables
	 */

	/**
	 * Query para buscar en la tabla xxfc_pmo_tienda_bkp, por dia de generacion, dia
	 * de inicio de ventana de entrega, ruta y nombre del respaldo
	 * 
	 * @param nom_proyecto
	 * @param route
	 * @param dia_gen_ped
	 * @param dia_ini_vent_entrega
	 * @return
	 */
	public String pmoTiendaBkpAll(String nom_proyecto, String route, String dia_gen_ped, String dia_ini_vent_entrega) {
		return "SELECT\n" + "    *\n" + "FROM\n" + "    rdm100.xxfc_pmo_tienda_bkp\n" + "WHERE\n"
				+ "    nom_proyecto = '" + nom_proyecto + "'\n" + "    AND route = '" + route + "'\n"
				+ "    AND dia_gen_ped = '" + dia_gen_ped + "'\n" + "    AND dia_ini_vent_entrega = '"
				+ dia_ini_vent_entrega + "'";
	}

	/**
	 * Query para buscar en la tabla xxfc_pmo_tienda_bkp, por dia de generacion, dia
	 * de inicio de ventana de entrega, ruta
	 * 
	 * @param route
	 * @param dia_gen_ped
	 * @param dia_ini_vent_entrega
	 * @return
	 */
	public String pmoTiendaAll(String route, String dia_gen_ped, String dia_ini_vent_entrega) {
		return "SELECT\n" + "    *\n" + "FROM\n" + "    rdm100.xxfc_pmo_tienda_bkp\n" + "WHERE\n" + "    route = '"
				+ route + "'\n" + "    AND dia_gen_ped = '" + dia_gen_ped + "'\n" + "    AND dia_ini_vent_entrega = '"
				+ dia_ini_vent_entrega + "'";
	}

	/**
	 * Query para buscar para comprobar que los registros de la tabla
	 * xxfc_pmo_tienda esten el la tabla xxfc_pmo_tienda_bkp, por dia de generacion,
	 * dia de inicio de ventana de entrega, ruta y nombre del respaldo
	 * 
	 * @param route
	 * @param dia_gen_ped
	 * @param dia_ini_vent_entrega
	 * @param nom_proyecto
	 * @return
	 */
	public String pmoTiendaIn(String route, String dia_gen_ped, String dia_ini_vent_entrega, String nom_proyecto) {
		return "SELECT\n" + "    *\n" + "FROM\n" + "    rdm100.xxfc_pmo_tienda\n" + "WHERE\n" + "    ( crplaza,\n"
				+ "      route,\n" + "      dia_gen_ped,\n" + "      dia_ini_vent_entrega ) IN (\n" + "        SELECT\n"
				+ "            crplaza,\n" + "            route,\n" + "            dia_gen_ped,\n"
				+ "            dia_ini_vent_entrega\n" + "        FROM\n" + "            rdm100.xxfc_pmo_tienda_bkp\n"
				+ "        WHERE\n" + "            route = '" + route + "'\n" + "            AND dia_gen_ped = '"
				+ dia_gen_ped + "'\n" + "            AND dia_ini_vent_entrega = '" + dia_ini_vent_entrega + "'\n"
				+ "            AND nom_proyecto = '" + nom_proyecto + "')";
	}

	/**
	 * Query para buscar para comprobar que los registros de la tabla
	 * xxfc_pmo_tienda_bkp esten el la tabla xxfc_pmo_tienda, por dia de generacion,
	 * dia de inicio de ventana de entrega, ruta y nombre del respaldo
	 * 
	 * @param route
	 * @param dia_gen_ped
	 * @param dia_ini_vent_entrega
	 * @param nom_proyecto
	 * @return
	 */
	public String pmoTiendaBkpIn(String route, String dia_gen_ped, String dia_ini_vent_entrega, String nom_proyecto) {
		return "SELECT\n" + "    *\n" + "FROM\n" + "    rdm100.xxfc_pmo_tienda_bkp\n" + "WHERE\n" + "    ( crplaza,\n"
				+ "      route,\n" + "      dia_gen_ped,\n" + "      dia_ini_vent_entrega ) IN (\n" + "        SELECT\n"
				+ "            crplaza,\n" + "            route,\n" + "            dia_gen_ped,\n"
				+ "            dia_ini_vent_entrega\n" + "        FROM\n" + "            rdm100.xxfc_pmo_tienda\n"
				+ "        WHERE\n" + "            route = '" + route + "'\n" + "            AND dia_gen_ped = '"
				+ dia_gen_ped + "'\n" + "            AND dia_ini_vent_entrega = '" + dia_ini_vent_entrega + "'\n"
				+ "    )\n" + "    AND rdm100.xxfc_pmo_tienda_bkp.nom_proyecto = '" + nom_proyecto + "'";
	}

	/**
	 * Query para buscar tiendas con pedidos que se encuentren en status A
	 * 
	 * @param route
	 * @return
	 */
	public String tiendaConPedidosStatusA(String route) {
		return "SELECT\n" + "    cr_plaza,\n" + "    cr_tienda,\n" + "    ruta,\n" + "    pmo_route,\n"
				+ "    status \n" + "FROM\n" + "    (\n" + "        SELECT\n" + "            *\n" + "        FROM\n"
				+ "            rdm100.xxfc_base_tipo_distro d\n" + "        WHERE\n" + "            d.ruta = '" + route
				+ "'\n" + "            AND status = 'A'\n" + "            AND d.dest_id IN (\n"
				+ "                SELECT\n" + "                    t.dest_id\n" + "                FROM\n"
				+ "                    rdm100.xxfc_pmo_tienda t\n" + "                WHERE\n"
				+ "                    t.route = d.ruta\n" + "            )\n" + "    )\n" + "WHERE\n"
				+ "    ROWNUM <= 10";
	}

	/**
	 * Query para buscar los respaldos de la tabla xxfc_pmo_route_bkp, usando el
	 * nombre de respaldo
	 * 
	 * @param nomProyecto
	 * @return
	 */
	public String pmoRouteBkp(String nomProyecto) {
		return "SELECT\n" + "    nom_proyecto   AS bkp,\n" + "    fecha_respaldo as fecha,\n"
				+ "    tipo_distro    AS td,\n" + "    route          AS ruta,\n"
				+ "    DECODE(dia_gen_ped,1,'DO',2,'LU',3,'MA',4,'MI',5,'JU',6,'VI',7,'SA') AS gen,\n"
				+ "    DECODE(dia_prog,1,'DO',2,'LU',3,'MA',4,'MI',5,'JU',6,'VI',7,'SA') AS prog,\n"
				+ "    TO_CHAR(h_prog,'HH24:MI') AS h_prog,\n"
				+ "    DECODE(dia_ini_entrega,1,'DO',2,'LU',3,'MA',4,'MI',5,'JU',6,'VI',7,'SA') AS ent,\n"
				+ "    TO_CHAR(h_ini_entrega,'HH24:MI') AS h_ini,\n"
				+ "    DECODE(dia_fin_entrega,1,'DO',2,'LU',3,'MA',4,'MI',5,'JU',6,'VI',7,'SA') AS fin,\n"
				+ "    TO_CHAR(h_fin_entrega,'HH24:MI') AS h_fin\n" + "FROM\n" + "    rdm100.xxfc_pmo_route_bkp\n"
				+ "WHERE\n" + "    nom_proyecto = '" + nomProyecto + "'";
	}

	/**
	 * Query acceso de usuarios de rdm
	 * 
	 * @param userID
	 * @return
	 */
	public String dmsUserAccess(String userID) {
		return "SELECT\n" + "    facility_id as facility,\n" + "    user_id as userID ,\n"
				+ "    user_privilege as privilegio,\n" + "    user_logdate as logdate,\n"
				+ "    user_class as clase,\n" + "    CASE\n" + "        WHEN user_class NOT IN (\n"
				+ "            'SISTEMAS OS',\n" + "            'SOPORTE_OS',\n"
				+ "            'COORDINADOR DE SISTEMAS',\n" + "            'ANALISTA DE SISTEMAS'\n"
				+ "        ) THEN 'usuario no autorizado'\n" + "        ELSE 'usuario autorizado'\n"
				+ "    END AS accesos\n" + "FROM\n" + "    dms_user\n" + "WHERE\n" + "    user_id = '" + userID + "'";
	}

	/**
	 * Numero de elementos de un bkp, filtrando nombre de bkp y ruta
	 * 
	 * @param nomProyecto
	 * @param route
	 * @return
	 */
	public String countTiendaBkp(String nomProyecto, String route) {
		return "SELECT\n" + "    COUNT(*)\n" + "FROM\n" + "    rdm100.xxfc_pmo_tienda_bkp\n" + "WHERE\n"
				+ "    nom_proyecto = '" + nomProyecto + "'\n" + "    AND route = '" + route + "'";
	}

	/**
	 * Numero de elementos de un bkp, filtrando ruta
	 * 
	 * @param route
	 * @return
	 */
	public String countTienda(String route) {
		return "SELECT\n" + "    COUNT(*)\n" + "FROM\n" + "    rdm100.xxfc_pmo_tienda_bkp\n" + "WHERE\n"
				+ "    route = '" + route + "'";
	}

	/**
	 * Numero de elementos de un bkp, filtrando por nombre de proyecto y ruta
	 * 
	 * @param nomProyecto
	 * @param route
	 * @return
	 */
	public String countRouteBkp(String nomProyecto, String route) {
		return "SELECT\n" + "    COUNT(*)\n" + "FROM\n" + "    rdm100.xxfc_pmo_route_bkp\n" + "WHERE\n"
				+ "    nom_proyecto = '" + nomProyecto + "'\n" + "    AND route = '" + route + "'";
	}

	/**
	 * Numero de elementos de una ruta
	 * 
	 * @param route
	 * @return
	 */
	public String countRoute(String route) {
		return "SELECT\n" + "    COUNT(*)\n" + "FROM\n" + "    rdm100.xxfc_pmo_route\n" + "WHERE\n" + "    route = '"
				+ route + "'";
	}

	/**
	 * Pmo route dia_disp_uni_cedis
	 * 
	 * @param route
	 * @return
	 */
	public String getQuerypmoRoute1(String route) {
		return "SELECT dia_disp_uni_cedis as d_disp_unicedis, "
				+ "TO_CHAR(h_disp_uni_cedis,'HH24:MI:SS') AS h_disp_unicedis, " + "ls, "
				+ "po_generation_cutoff_time as po_gen_cutoff, " + "po_generation_time as po_gen_time "
				+ "FROM rdm100.xxfc_pmo_route " + "WHERE route = '" + route + "'";
	}

	/**
	 * Pmo route status
	 * 
	 * @param route
	 * @return
	 */
	public String getQuerypmoRoute2(String route) {
		return "SELECT status, " + "attribute1, " + "attribute2, " + "fecha_insert, " + "fecha_update "
				+ "FROM rdm100.xxfc_pmo_route " + "WHERE route = '" + route + "'";
	}

	/**
	 * Pmo route user_id
	 * 
	 * @param route
	 * @return
	 */
	public String getQuerypmoRoute3(String route) {
		return "SELECT user_id, " + "sem_gen_ped as s_gen_ped, " + "sem_prog as s_prog, "
				+ "sem_ini_prod as s_ini_prod, " + "sem_fin_prod as s_fin_prod " + "FROM rdm100.xxfc_pmo_route "
				+ "WHERE route = '" + route + "'";
	}

	/**
	 * Pmo route sem_ini_carga
	 * 
	 * @param route
	 * @return
	 */
	public String getQuerypmoRoute4(String route) {
		return "SELECT sem_ini_carga as s_ini_carga, " + "sem_fin_carga as s_fin_carga, " + "sem_salida as s_salida, "
				+ "sem_ini_entrega as s_ini_entrega, " + "sem_fin_entrega as s_fin_entrega "
				+ "FROM rdm100.xxfc_pmo_route " + "WHERE route = '" + route + "'";
	}

	/**
	 * Pmo route sem_disp_uni_cedis
	 * 
	 * @param route
	 * @return
	 */
	public String getQuerypmoRoute5(String route) {
		return "SELECT sem_disp_uni_cedis as s_disp_unicedis, " + "dia_ini_verificacion as d_ini_veri, "
				+ "TO_CHAR(h_ini_verificacion,'HH24:MI:SS') AS h_ini_veri, " + "dia_fin_verificacion as d_fin_veri, "
				+ "TO_CHAR(h_fin_verificacion,'HH24:MI:SS') AS h_fin_veri " + "FROM rdm100.xxfc_pmo_route "
				+ "WHERE route = '" + route + "'";
	}

	/**
	 * Pmo route dia_llegada_pt
	 * 
	 * @param route
	 * @return
	 */
	public String getQuerypmoRoute6(String route) {
		return "SELECT dia_llegada_pt as d_llegada_pt, " + "TO_CHAR(h_llegada_pt,'HH24:MI:SS') AS h_llegada_pt, "
				+ "dia_salida_pt as d_salida_pt, " + "TO_CHAR(h_salida_pt,'HH24:MI:SS') AS h_salida_pt, "
				+ "sem_ini_verificacion as s_ini_veri " + "FROM rdm100.xxfc_pmo_route " + "WHERE route = '" + route
				+ "'";
	}

	/**
	 * Pmo route sem_fin_verificacion
	 * 
	 * @param route
	 * @return
	 */
	public String getQuerypmoRoute7(String route) {
		return "SELECT sem_fin_verificacion as s_fin_ver, " + "sem_llegada_pt as s_llegada_pt, "
				+ "sem_salida_pt as s_salida_pt " + "FROM rdm100.xxfc_pmo_route " + "WHERE route = '" + route + "'";
	}

	/**
	 * Genera visualizacion de PMO ROUTE filtrando por route
	 * 
	 * @param dbConecta
	 * @param route
	 * @param tf
	 * @throws SQLException
	 */
	public void pmoRouteQueries(SQLUtil dbConecta, String route, Boolean tf) throws SQLException {

		SQLResult a = executeQuery(dbConecta, getQuerypmoRoute1(route));
		logger.info(String.valueOf(a.isEmpty()));
		testCase.addQueryEvidenceCurrentStep(a, tf);
		SQLResult b = executeQuery(dbConecta, getQuerypmoRoute2(route));
		logger.info(String.valueOf(b.isEmpty()));
		testCase.addQueryEvidenceCurrentStep(b, tf);
		SQLResult c = executeQuery(dbConecta, getQuerypmoRoute3(route));
		logger.info(String.valueOf(c.isEmpty()));
		testCase.addQueryEvidenceCurrentStep(c, tf);
		SQLResult d = executeQuery(dbConecta, getQuerypmoRoute4(route));
		logger.info(String.valueOf(d.isEmpty()));
		testCase.addQueryEvidenceCurrentStep(d, tf);
		SQLResult f = executeQuery(dbConecta, getQuerypmoRoute5(route));
		logger.info(String.valueOf(f.isEmpty()));
		testCase.addQueryEvidenceCurrentStep(f, tf);
		SQLResult g = executeQuery(dbConecta, getQuerypmoRoute6(route));
		logger.info(String.valueOf(g.isEmpty()));
		testCase.addQueryEvidenceCurrentStep(g, tf);
		SQLResult h = executeQuery(dbConecta, getQuerypmoRoute7(route));
		logger.info(String.valueOf(h.isEmpty()));
		testCase.addQueryEvidenceCurrentStep(h, tf);
	}

	/**
	 * Filtra por ruta, todas las columnas
	 * 
	 * @param route
	 * @return
	 */
	public String pmoRoute(String route) {
		return "SELECT\n" + "    *\n" + "FROM\n" + "    rdm100.xxfc_pmo_route\n" + "WHERE\n" + "    route = '" + route
				+ "'";
	}

	/**
	 * Route backpup nom_proyecto
	 * 
	 * @param route
	 * @param nomProyecto
	 * @return
	 */
	public String getRouteBkpQuery1(String route, String nomProyecto) {
		return "SELECT nom_proyecto, " + "fecha_respaldo, " + "facility_id, " + "cedis, " + "tipo_distro "
				+ "FROM rdm100.xxfc_pmo_route_bkp " + "WHERE route = '" + route + "'" + "AND nom_proyecto = '"
				+ nomProyecto + "'";
	}

	/**
	 * Route backpup carrier_code
	 * 
	 * @param route
	 * @param nomProyecto
	 * @return
	 */
	public String getRouteBkpQuery2(String route, String nomProyecto) {
		return "SELECT carrier_code, " + "service_code, " + "route, " + "route_description, "
				+ "DECODE(dia_gen_ped,1,'DO',2,'LU',3,'MA',4,'MI',5,'JU',6,'VI',7,'SA') AS dia_gen_ped "
				+ "FROM rdm100.xxfc_pmo_route_bkp " + "WHERE route = '" + route + "'" + "AND nom_proyecto = '"
				+ nomProyecto + "'";
	}

	/**
	 * Route backpup h_gen_ped
	 * 
	 * @param route
	 * @param nomProyecto
	 * @return
	 */
	public String getRouteBkpQuery3(String route, String nomProyecto) {
		return "SELECT TO_CHAR(h_gen_ped,'HH24:MI:SS') AS h_gen_ped, "
				+ "DECODE(dia_prog,1,'DO',2,'LU',3,'MA',4,'MI',5,'JU',6,'VI',7,'SA') AS dia_prog, "
				+ "TO_CHAR(h_prog,'HH24:MI:SS') AS h_prog, "
				+ "DECODE(dia_ini_prod,1,'DO',2,'LU',3,'MA',4,'MI',5,'JU',6,'VI',7,'SA') AS dia_ini_prod, "
				+ "TO_CHAR(h_ini_prod,'HH24:MI:SS') AS h_ini_prod " + "FROM rdm100.xxfc_pmo_route_bkp "
				+ "WHERE route = '" + route + "'" + "AND nom_proyecto = '" + nomProyecto + "'";
	}

	/**
	 * Route backpup dia_fin_prod
	 * 
	 * @param route
	 * @param nomProyecto
	 * @return
	 */
	public String getRouteBkpQuery4(String route, String nomProyecto) {
		return "SELECT DECODE(dia_fin_prod,1,'DO',2,'LU',3,'MA',4,'MI',5,'JU',6,'VI',7,'SA') AS dia_fin_prod, "
				+ "TO_CHAR(h_fin_prod,'HH24:MI:SS') AS h_fin_prod, "
				+ "DECODE(dia_ini_carga,1,'DO',2,'LU',3,'MA',4,'MI',5,'JU',6,'VI',7,'SA') AS dia_ini_carga, "
				+ "TO_CHAR(h_ini_carga,'HH24:MI:SS') AS h_ini_carga, "
				+ "DECODE(dia_fin_carga,1,'DO',2,'LU',3,'MA',4,'MI',5,'JU',6,'VI',7,'SA') AS dia_fin_carga "
				+ "FROM rdm100.xxfc_pmo_route_bkp " + "WHERE route = '" + route + "'" + "AND nom_proyecto = '"
				+ nomProyecto + "'";
	}

	/**
	 * Route backpup h_fin_carga
	 * 
	 * @param route
	 * @param nomProyecto
	 * @return
	 */
	public String getRouteBkpQuery5(String route, String nomProyecto) {
		return "SELECT TO_CHAR(h_fin_carga,'HH24:MI:SS') AS h_fin_carga, "
				+ "DECODE(dia_salida,1,'DO',2,'LU',3,'MA',4,'MI',5,'JU',6,'VI',7,'SA') AS dia_salida, "
				+ "TO_CHAR(h_salida,'HH24:MI:SS') AS h_salida, " + "recorrido, "
				+ "DECODE(dia_ini_entrega,1,'DO',2,'LU',3,'MA',4,'MI',5,'JU',6,'VI',7,'SA') AS dia_ini_entrega "
				+ "FROM rdm100.xxfc_pmo_route_bkp " + "WHERE route = '" + route + "'" + "AND nom_proyecto = '"
				+ nomProyecto + "'";
	}

	/**
	 * Route backpup h_ini_entrega
	 * 
	 * @param route
	 * @param nomProyecto
	 * @return
	 */
	public String getRouteBkpQuery6(String route, String nomProyecto) {
		return "SELECT TO_CHAR(h_ini_entrega,'HH24:MI:SS') AS h_ini_entrega, "
				+ "DECODE(dia_fin_entrega,1,'DO',2,'LU',3,'MA',4,'MI',5,'JU',6,'VI',7,'SA') AS dia_fin_entrega, "
				+ "TO_CHAR(h_fin_entrega,'HH24:MI:SS') AS h_fin_entrega, " + "ciclo_atn, " + "ciclo_tot "
				+ "FROM rdm100.xxfc_pmo_route_bkp " + "WHERE route = '" + route + "'" + "AND nom_proyecto = '"
				+ nomProyecto + "'";
	}

	/**
	 * Route backpup dia_disp_uni_cedis
	 * 
	 * @param route
	 * @param nomProyecto
	 * @return
	 */
	public String getRouteBkpQuery7(String route, String nomProyecto) {
		return "SELECT DECODE(dia_disp_uni_cedis,1,'DO',2,'LU',3,'MA',4,'MI',5,'JU',6,'VI',7,'SA') AS dia_disp_uni_cedis, "
				+ "TO_CHAR(h_disp_uni_cedis,'HH24:MI:SS') AS h_disp_uni_cedis, " + "ls, "
				+ "po_generation_cutoff_time, " + "po_generation_time " + "FROM rdm100.xxfc_pmo_route_bkp "
				+ "WHERE route = '" + route + "'" + "AND nom_proyecto = '" + nomProyecto + "'";
	}

	/**
	 * Route backpup status
	 * 
	 * @param route
	 * @param nomProyecto
	 * @return
	 */
	public String getRouteBkpQuery8(String route, String nomProyecto) {
		return "SELECT status, " + "attribute1, " + "attribute2, " + "fecha_insert, " + "fecha_update "
				+ "FROM rdm100.xxfc_pmo_route_bkp " + "WHERE route = '" + route + "'" + "AND nom_proyecto = '"
				+ nomProyecto + "'";
	}

	/**
	 * Route backpup user_id
	 * 
	 * @param route
	 * @param nomProyecto
	 * @return
	 */
	public String getRouteBkpQuery9(String route, String nomProyecto) {
		return "SELECT user_id, " + "sem_gen_ped, " + "sem_prog, " + "sem_ini_prod, " + "sem_fin_prod, "
				+ "sem_ini_carga, " + "sem_fin_carga " + "FROM rdm100.xxfc_pmo_route_bkp " + "WHERE route = '" + route
				+ "'" + "AND nom_proyecto = '" + nomProyecto + "'";
	}

	/**
	 * Genera la visualizacion de la tabla route backup
	 * 
	 * @param dbConecta
	 * @param route
	 * @param nomProyecto
	 * @param tf
	 * @throws SQLException
	 */
	public void pmoRouteBkpQueries(SQLUtil dbConecta, String route, String nomProyecto, Boolean tf)
			throws SQLException {

		SQLResult getRouteBkpQuery1 = executeQuery(dbConecta, getRouteBkpQuery1(route, nomProyecto));
		logger.info(String.valueOf(getRouteBkpQuery1.isEmpty()));
		testCase.addQueryEvidenceCurrentStep(getRouteBkpQuery1, tf);
		SQLResult getRouteBkpQuery2 = executeQuery(dbConecta, getRouteBkpQuery2(route, nomProyecto));
		logger.info(String.valueOf(getRouteBkpQuery2.isEmpty()));
		testCase.addQueryEvidenceCurrentStep(getRouteBkpQuery2, tf);
		SQLResult getRouteBkpQuery3 = executeQuery(dbConecta, getRouteBkpQuery3(route, nomProyecto));
		logger.info(String.valueOf(getRouteBkpQuery3.isEmpty()));
		testCase.addQueryEvidenceCurrentStep(getRouteBkpQuery3, tf);
		SQLResult getRouteBkpQuery4 = executeQuery(dbConecta, getRouteBkpQuery4(route, nomProyecto));
		logger.info(String.valueOf(getRouteBkpQuery4.isEmpty()));
		testCase.addQueryEvidenceCurrentStep(getRouteBkpQuery4, tf);
		SQLResult getRouteBkpQuery5 = executeQuery(dbConecta, getRouteBkpQuery5(route, nomProyecto));
		logger.info(String.valueOf(getRouteBkpQuery5.isEmpty()));
		testCase.addQueryEvidenceCurrentStep(getRouteBkpQuery5, tf);
		SQLResult getRouteBkpQuery6 = executeQuery(dbConecta, getRouteBkpQuery6(route, nomProyecto));
		logger.info(String.valueOf(getRouteBkpQuery6.isEmpty()));
		testCase.addQueryEvidenceCurrentStep(getRouteBkpQuery6, tf);
		SQLResult getRouteBkpQuery7 = executeQuery(dbConecta, getRouteBkpQuery7(route, nomProyecto));
		logger.info(String.valueOf(getRouteBkpQuery7.isEmpty()));
		testCase.addQueryEvidenceCurrentStep(getRouteBkpQuery7, tf);
		SQLResult getRouteBkpQuery8 = executeQuery(dbConecta, getRouteBkpQuery8(route, nomProyecto));
		logger.info(String.valueOf(getRouteBkpQuery8.isEmpty()));
		testCase.addQueryEvidenceCurrentStep(getRouteBkpQuery8, tf);
		SQLResult getRouteBkpQuery9 = executeQuery(dbConecta, getRouteBkpQuery9(route, nomProyecto));
		logger.info(String.valueOf(getRouteBkpQuery9.isEmpty()));
		testCase.addQueryEvidenceCurrentStep(getRouteBkpQuery9, tf);

	}

	/**
	 * Query utilizado para comparar los valores de las tablas route/route_bkp
	 * 
	 * @author 2000132763
	 * @param nomProyecto
	 * @param route
	 * @return
	 */
	public String pmoRouteCompare(String nomProyecto, String route) {
		return "SELECT\n" + "    facility_id,\n" + "    cedis,\n" + "    tipo_distro,\n" + "    carrier_code,\n"
				+ "    service_code,\n" + "    route,\n" + "    route_description,\n" + "    dia_gen_ped,\n"
				+ "    h_gen_ped,\n" + "    dia_prog,\n" + "    h_prog,\n" + "    dia_ini_prod,\n" + "    h_ini_prod,\n"
				+ "    dia_fin_prod,\n" + "    h_fin_prod,\n" + "    dia_ini_carga,\n" + "    h_ini_carga,\n"
				+ "    dia_fin_carga,\n" + "    h_fin_carga,\n" + "    dia_salida,\n" + "    h_salida,\n"
				+ "    recorrido,\n" + "    dia_ini_entrega,\n" + "    h_ini_entrega,\n" + "    dia_fin_entrega,\n"
				+ "    h_fin_entrega,\n" + "    ciclo_atn,\n" + "    ciclo_tot,\n" + "    dia_disp_uni_cedis,\n"
				+ "    h_disp_uni_cedis,\n" + "    ls,\n" + "    po_generation_cutoff_time,\n"
				+ "    po_generation_time,\n" + "    status,\n" + "    attribute1,\n" + "    attribute2,\n"
				+ "    sem_gen_ped,\n" + "    sem_prog,\n" + "    sem_ini_prod,\n" + "    sem_fin_prod,\n"
				+ "    sem_ini_carga,\n" + "    sem_fin_carga,\n" + "    sem_salida,\n" + "    sem_ini_entrega,\n"
				+ "    sem_fin_entrega,\n" + "    sem_disp_uni_cedis,\n" + "    dia_ini_verificacion,\n"
				+ "    h_ini_verificacion,\n" + "    dia_fin_verificacion,\n" + "    h_fin_verificacion,\n"
				+ "    dia_llegada_pt,\n" + "    h_llegada_pt,\n" + "    dia_salida_pt,\n" + "    h_salida_pt,\n"
				+ "    sem_ini_verificacion,\n" + "    sem_fin_verificacion,\n" + "    sem_llegada_pt,\n"
				+ "    sem_salida_pt\n" + "FROM\n" + "    rdm100.xxfc_pmo_route_bkp\n" + "WHERE\n"
				+ "    rdm100.xxfc_pmo_route_bkp.nom_proyecto = '" + nomProyecto + "'\n" + "MINUS\n" + "SELECT\n"
				+ "    facility_id,\n" + "    cedis,\n" + "    tipo_distro,\n" + "    carrier_code,\n"
				+ "    service_code,\n" + "    route,\n" + "    route_description,\n" + "    dia_gen_ped,\n"
				+ "    h_gen_ped,\n" + "    dia_prog,\n" + "    h_prog,\n" + "    dia_ini_prod,\n" + "    h_ini_prod,\n"
				+ "    dia_fin_prod,\n" + "    h_fin_prod,\n" + "    dia_ini_carga,\n" + "    h_ini_carga,\n"
				+ "    dia_fin_carga,\n" + "    h_fin_carga,\n" + "    dia_salida,\n" + "    h_salida,\n"
				+ "    recorrido,\n" + "    dia_ini_entrega,\n" + "    h_ini_entrega,\n" + "    dia_fin_entrega,\n"
				+ "    h_fin_entrega,\n" + "    ciclo_atn,\n" + "    ciclo_tot,\n" + "    dia_disp_uni_cedis,\n"
				+ "    h_disp_uni_cedis,\n" + "    ls,\n" + "    po_generation_cutoff_time,\n"
				+ "    po_generation_time,\n" + "    status,\n" + "    attribute1,\n" + "    attribute2,\n"
				+ "    sem_gen_ped,\n" + "    sem_prog,\n" + "    sem_ini_prod,\n" + "    sem_fin_prod,\n"
				+ "    sem_ini_carga,\n" + "    sem_fin_carga,\n" + "    sem_salida,\n" + "    sem_ini_entrega,\n"
				+ "    sem_fin_entrega,\n" + "    sem_disp_uni_cedis,\n" + "    dia_ini_verificacion,\n"
				+ "    h_ini_verificacion,\n" + "    dia_fin_verificacion,\n" + "    h_fin_verificacion,\n"
				+ "    dia_llegada_pt,\n" + "    h_llegada_pt,\n" + "    dia_salida_pt,\n" + "    h_salida_pt,\n"
				+ "    sem_ini_verificacion,\n" + "    sem_fin_verificacion,\n" + "    sem_llegada_pt,\n"
				+ "    sem_salida_pt\n" + "FROM\n" + "    rdm100.xxfc_pmo_route\n" + "WHERE\n"
				+ "    rdm100.xxfc_pmo_route.route = '" + route + "'";
	}

	/**
	 * Query utilizado para comparar los valores de las tablas tienda/tienda_bkp
	 * 
	 * @author 2000132763
	 * @param nomProyecto
	 * @param route
	 * @return
	 */
	public String pmoTiendaCompare(String nomProyecto, String route) {
		return "SELECT\n" + "     route,\n"
				+ "     DECODE(dia_gen_ped,1,'DO',2,'LU',3,'MA',4,'MI',5,'JU',6,'VI',7,'SA') AS dia_gen_ped,\n"
				+ "     dest_id,\n" + "     load_sequence,\n"
				+ "     DECODE(dia_ini_vent_entrega,1,'DO',2,'LU',3,'MA',4,'MI',5,'JU',6,'VI',7,'SA') AS dia_ini_vent_entrega,\n"
				+ "     TO_CHAR(h_ini_vent_entrega,'HH24:MI') AS h_ini_vent_entrega,\n"
				+ "     DECODE(dia_fin_vent_entrega,1,'DO',2,'LU',3,'MA',4,'MI',5,'JU',6,'VI',7,'SA') AS dia_fin_vent_entrega,\n"
				+ "     TO_CHAR(h_fin_vent_entrega,'HH24:MI') AS h_fin_vent_entrega,\n" + "     lsp,\n"
				+ "     lssep1,\n" + "     lssep2,\n" + "     cedis,\n" + "     crplaza,\n" + "     tipo_pedido,\n"
				+ "     crossdock,\n" + "     attribute1,\n" + "     attribute2,\n" + "     status,\n"
				+ "     tipo_distro,\n"
				+ "     DECODE(dia_est_entrega,1,'DO',2,'LU',3,'MA',4,'MI',5,'JU',6,'VI',7,'SA') AS dia_est_entrega,\n"
				+ "     TO_CHAR(h_est_entrega,'HH24:MI') AS h_est_entrega,\n" + "     verify_int,\n"
				+ "     verify_ext\n" + " FROM\n" + "     rdm100.xxfc_pmo_tienda_bkp\n" + " WHERE\n"
				+ "     rdm100.xxfc_pmo_tienda_bkp.nom_proyecto = '" + nomProyecto + "'\n" + " MINUS\n" + " SELECT\n"
				+ "     route,\n"
				+ "     DECODE(dia_gen_ped,1,'DO',2,'LU',3,'MA',4,'MI',5,'JU',6,'VI',7,'SA') AS dia_gen_ped,\n"
				+ "     dest_id,\n" + "     load_sequence,\n"
				+ "     DECODE(dia_ini_vent_entrega,1,'DO',2,'LU',3,'MA',4,'MI',5,'JU',6,'VI',7,'SA') AS dia_ini_vent_entrega,\n"
				+ "     TO_CHAR(h_ini_vent_entrega,'HH24:MI') AS h_ini_vent_entrega,\n"
				+ "     DECODE(dia_fin_vent_entrega,1,'DO',2,'LU',3,'MA',4,'MI',5,'JU',6,'VI',7,'SA') AS dia_fin_vent_entrega,\n"
				+ "     TO_CHAR(h_fin_vent_entrega,'HH24:MI') AS h_fin_vent_entrega,\n" + "     lsp,\n"
				+ "     lssep1,\n" + "     lssep2,\n" + "     cedis,\n" + "     crplaza,\n" + "     tipo_pedido,\n"
				+ "     crossdock,\n" + "     attribute1,\n" + "     attribute2,\n" + "     status,\n"
				+ "     tipo_distro,\n"
				+ "     DECODE(dia_est_entrega,1,'DO',2,'LU',3,'MA',4,'MI',5,'JU',6,'VI',7,'SA') AS dia_est_entrega,\n"
				+ "     TO_CHAR(h_est_entrega,'HH24:MI') AS h_est_entrega,\n" + "     verify_int,\n"
				+ "     verify_ext\n" + " FROM\n" + "     rdm100.xxfc_pmo_tienda\n" + " WHERE\n"
				+ "     rdm100.xxfc_pmo_tienda.route = '" + route + "'";
	}

	/**
	 * Genera visualizacion Tienda backup
	 * 
	 * @param dbConecta
	 * @param nomProyecto
	 * @param tf
	 * @throws SQLException
	 */
	public void pmoTiendaBkpQueries(SQLUtil dbConecta, String nomProyecto, Boolean tf) throws SQLException {

		SQLResult getTiendaBkpQueryRoute = executeQuery(dbConecta, getTiendaBkpQueryRoute(nomProyecto));
		logger.info(String.valueOf(getTiendaBkpQueryRoute.isEmpty()));
		testCase.addQueryEvidenceCurrentStep(getTiendaBkpQueryRoute, tf);
		SQLResult getTiendaBkpQueryDiaVent = executeQuery(dbConecta, getTiendaBkpQueryDiaVent(nomProyecto));
		logger.info(String.valueOf(getTiendaBkpQueryDiaVent.isEmpty()));
		testCase.addQueryEvidenceCurrentStep(getTiendaBkpQueryDiaVent, tf);
		SQLResult getTiendaBkpQueryCedis = executeQuery(dbConecta, getTiendaBkpQueryCedis(nomProyecto));
		logger.info(String.valueOf(getTiendaBkpQueryCedis.isEmpty()));
		testCase.addQueryEvidenceCurrentStep(getTiendaBkpQueryCedis, tf);
		SQLResult getTiendaBkpQueryTipoDistro = executeQuery(dbConecta, getTiendaBkpQueryTipoDistro(nomProyecto));
		logger.info(String.valueOf(getTiendaBkpQueryTipoDistro.isEmpty()));
		testCase.addQueryEvidenceCurrentStep(getTiendaBkpQueryTipoDistro, tf);
	}

	/**
	 * Tienda bkp route
	 * 
	 * @param nomProyecto
	 * @return
	 */
	public String getTiendaBkpQueryRoute(String nomProyecto) {
		return "SELECT\n" + "    route,\n" + "    dest_id,\n"
				+ "    DECODE(dia_gen_ped,1,'DO',2,'LU',3,'MA',4,'MI',5,'JU',6,'VI',7,'SA') AS d_gen_ped,\n"
				+ "    load_sequence   AS load_seq,\n" + "    lsp\n" + "FROM\n" + "    rdm100.xxfc_pmo_tienda_bkp\n"
				+ "WHERE\n" + "    rdm100.xxfc_pmo_tienda_bkp.nom_proyecto = '" + nomProyecto + "'";
	}

	/**
	 * Tienda bkp dia_ini_vent_entrega
	 * 
	 * @param nomProyecto
	 * @return
	 */
	public String getTiendaBkpQueryDiaVent(String nomProyecto) {
		return "SELECT\n"
				+ "    DECODE(dia_ini_vent_entrega,1,'DO',2,'LU',3,'MA',4,'MI',5,'JU',6,'VI',7,'SA') AS d_ini_vent_ent,\n"
				+ "    TO_CHAR(h_ini_vent_entrega,'HH24:MI') AS h_ini_vent_ent,\n"
				+ "    DECODE(dia_fin_vent_entrega,1,'DO',2,'LU',3,'MA',4,'MI',5,'JU',6,'VI',7,'SA') AS d_fin_vent_ent,\n"
				+ "    TO_CHAR(h_fin_vent_entrega,'HH24:MI') AS h_fin_vent_ent\n" + "FROM\n"
				+ "    rdm100.xxfc_pmo_tienda_bkp\n" + "WHERE\n" + "    rdm100.xxfc_pmo_tienda_bkp.nom_proyecto = '"
				+ nomProyecto + "'";
	}

	/**
	 * Tienda bkp cedis
	 * 
	 * @param nomProyecto
	 * @return
	 */
	public String getTiendaBkpQueryCedis(String nomProyecto) {
		return "SELECT\n" + "    cedis,\n" + "    crplaza,\n" + "    tipo_pedido,\n" + "    status\n" + "FROM\n"
				+ "    rdm100.xxfc_pmo_tienda_bkp\n" + "WHERE\n" + "    rdm100.xxfc_pmo_tienda_bkp.nom_proyecto = '"
				+ nomProyecto + "'";
	}

	/**
	 * Tienda bkp tipo_distro
	 * 
	 * @param nomProyecto
	 * @return
	 */
	public String getTiendaBkpQueryTipoDistro(String nomProyecto) {
		return "SELECT\n" + "    tipo_distro,\n"
				+ "    DECODE(dia_est_entrega,1,'DO',2,'LU',3,'MA',4,'MI',5,'JU',6,'VI',7,'SA') AS dia_est_ent,\n"
				+ "    TO_CHAR(h_est_entrega,'HH24:MI') AS h_est_ent,\n" + "    verify_int,\n" + "    verify_ext\n"
				+ " FROM\n" + "     rdm100.xxfc_pmo_tienda_bkp\n" + " WHERE\n"
				+ "     rdm100.xxfc_pmo_tienda_bkp.nom_proyecto = '" + nomProyecto + "'";
	}

	/**
	 * Tienda route
	 * 
	 * @param nomProyecto
	 * @return
	 */
	public String getTiendaQueryRoute(String route) {
		return "SELECT\n" + "    route,\n" + "    dest_id,\n"
				+ "    DECODE(dia_gen_ped,1,'DO',2,'LU',3,'MA',4,'MI',5,'JU',6,'VI',7,'SA') AS d_gen_ped,\n"
				+ "    load_sequence   AS load_seq,\n" + "    lsp\n" + " FROM\n" + "     rdm100.xxfc_pmo_tienda\n"
				+ " WHERE\n" + "     rdm100.xxfc_pmo_tienda.route = 'BTEP302'";
	}

	/**
	 * Tienda dia_ini_vent_entrega
	 * 
	 * @param nomProyecto
	 * @return
	 */
	public String getTiendaQueryDiaVent(String route) {
		return "SELECT\n"
				+ "    DECODE(dia_ini_vent_entrega,1,'DO',2,'LU',3,'MA',4,'MI',5,'JU',6,'VI',7,'SA') AS d_ini_vent_ent,\n"
				+ "    TO_CHAR(h_ini_vent_entrega,'HH24:MI') AS h_ini_vent_ent,\n"
				+ "    DECODE(dia_fin_vent_entrega,1,'DO',2,'LU',3,'MA',4,'MI',5,'JU',6,'VI',7,'SA') AS d_fin_vent_ent,\n"
				+ "    TO_CHAR(h_fin_vent_entrega,'HH24:MI') AS h_fin_vent_ent\n" + " FROM\n"
				+ "     rdm100.xxfc_pmo_tienda\n" + " WHERE\n" + "     rdm100.xxfc_pmo_tienda.route = 'BTEP302'";
	}

	/**
	 * Tienda cedis
	 * 
	 * @param nomProyecto
	 * @return
	 */
	public String getTiendaQueryCedis(String route) {
		return "SELECT\n" + "    cedis,\n" + "    crplaza,\n" + "    tipo_pedido,\n" + "    status\n" + " FROM\n"
				+ "     rdm100.xxfc_pmo_tienda\n" + " WHERE\n" + "     rdm100.xxfc_pmo_tienda.route = 'BTEP302'";
	}

	/**
	 * Tienda tipo_distro
	 * 
	 * @param nomProyecto
	 * @return
	 */
	public String getTiendaQueryTipoDistro(String route) {
		return "SELECT\n" + "    tipo_distro,\n"
				+ "    DECODE(dia_est_entrega,1,'DO',2,'LU',3,'MA',4,'MI',5,'JU',6,'VI',7,'SA') AS dia_est_ent,\n"
				+ "    TO_CHAR(h_est_entrega,'HH24:MI') AS h_est_ent,\n" + "    verify_int,\n" + "    verify_ext\n"
				+ " FROM\n" + "     rdm100.xxfc_pmo_tienda\n" + " WHERE\n"
				+ "     rdm100.xxfc_pmo_tienda.route = 'BTEP302'";
	}

	/**
	 * Ejecuta una serie de queries y agrega la evidencia al caso, esto para mejorar
	 * la legibilidad del resultado de la consulta
	 *
	 * @param dbConecta
	 * @param route
	 * @param tf
	 * @throws SQLException
	 * @author JesusH4@hexaware.com
	 */
	public void pmoTiendaQueries(SQLUtil dbConecta, String route, Boolean tf) throws SQLException {

		SQLResult getTiendaQueryRoute = executeQuery(dbConecta, getTiendaQueryRoute(route));
		logger.info(String.valueOf(getTiendaQueryRoute.isEmpty()));
		testCase.addQueryEvidenceCurrentStep(getTiendaQueryRoute, tf);
		SQLResult getTiendaQueryDiaVent = executeQuery(dbConecta, getTiendaQueryDiaVent(route));
		logger.info(String.valueOf(getTiendaQueryDiaVent.isEmpty()));
		testCase.addQueryEvidenceCurrentStep(getTiendaQueryDiaVent, tf);
		SQLResult getTiendaQueryCedis = executeQuery(dbConecta, getTiendaQueryCedis(route));
		logger.info(String.valueOf(getTiendaQueryCedis.isEmpty()));
		testCase.addQueryEvidenceCurrentStep(getTiendaQueryCedis, tf);
		SQLResult getTiendaQueryTipoDistro = executeQuery(dbConecta, getTiendaQueryTipoDistro(route));
		logger.info(String.valueOf(getTiendaQueryTipoDistro.isEmpty()));
		testCase.addQueryEvidenceCurrentStep(getTiendaQueryTipoDistro, tf);
	}

	/**
	 * Query PMO Tienda Bkp
	 *
	 * @param route
	 * @return
	 * @author JesusH4@hexaware.com
	 */
	public String pmoTiendaBkp(String nomProyecto) {
		return "SELECT\n" + "     *\n" + " FROM\n" + "     rdm100.xxfc_pmo_tienda_bkp\n" + " WHERE\n"
				+ "     rdm100.xxfc_pmo_tienda_bkp.nom_proyecto = '" + nomProyecto + "'";
	}

	/**
	 * Query PMO Tienda
	 *
	 * @param route
	 * @return
	 * @author JesusH4@hexaware.com
	 */
	public String pmoTienda(String route) {
		return "SELECT\n" + "     *\n" + " FROM\n" + "     rdm100.xxfc_pmo_tienda\n" + " WHERE\n"
				+ "     rdm100.xxfc_pmo_tienda.route = '" + route + "'";
	}

	/**
	 * Query utilizado para buscar por plaza
	 *
	 * @param plaza
	 * @return
	 * @author JesusH4@hexaware.com
	 */
	public String queryBuscarPorPlaza(String plaza) {
		return "SELECT" + "     tipo_distro," + "     route,"
				+ "     DECODE(dia_gen_ped,1,'DO',2,'LU',3,'MA',4,'MI',5,'JU',6,'VI',7,'SA') AS dia_gen_ped,"
				+ "     DECODE(dia_ini_entrega,1,'DO',2,'LU',3,'MA',4,'MI',5,'JU',6,'VI',7,'SA') AS dia_ini_entrega,"
				+ "     TO_CHAR(h_ini_entrega,'HH24:MI') AS h_ini_entrega,"
				+ "     TO_CHAR(h_fin_entrega,'HH24:MI') AS h_fin_entrega,"
				+ "     DECODE(dia_prog,1,'DO',2,'LU',3,'MA',4,'MI',5,'JU',6,'VI',7,'SA') AS dia_prog,"
				+ "     TO_CHAR(h_prog,'HH24:MI') AS h_prog,"
				+ "     DECODE(dia_ini_prod,1,'DO',2,'LU',3,'MA',4,'MI',5,'JU',6,'VI',7,'SA') AS dia_ini_prod,"
				+ "     TO_CHAR(h_ini_prod,'HH24:MI') AS h_ini_prod,"
				+ "     DECODE(dia_fin_prod,1,'DO',2,'LU',3,'MA',4,'MI',5,'JU',6,'VI',7,'SA') AS dia_fin_prod,"
				+ "     TO_CHAR(h_fin_prod,'HH24:MI') AS h_fin_prod,"
				+ "     DECODE(dia_ini_verificacion,1,'DO',2,'LU',3,'MA',4,'MI',5,'JU',6,'VI',7,'SA') AS dia_ini_verificacion,"
				+ "     TO_CHAR(h_ini_verificacion,'HH24:MI') AS h_ini_verificacion,"
				+ "     DECODE(dia_fin_verificacion,1,'DO',2,'LU',3,'MA',4,'MI',5,'JU',6,'VI',7,'SA') AS dia_fin_verificacion,"
				+ "     TO_CHAR(h_fin_verificacion,'HH24:MI') AS h_fin_verificacion,"
				+ "     DECODE(dia_ini_carga,1,'DO',2,'LU',3,'MA',4,'MI',5,'JU',6,'VI',7,'SA') AS dia_ini_carga,"
				+ "     TO_CHAR(h_ini_carga,'HH24:MI') AS h_ini_carga,"
				+ "     DECODE(dia_fin_carga,1,'DO',2,'LU',3,'MA',4,'MI',5,'JU',6,'VI',7,'SA') AS dia_fin_carga,"
				+ "     TO_CHAR(h_fin_carga,'HH24:MI') AS h_fin_carga,"
				+ "     DECODE(dia_salida,1,'DO',2,'LU',3,'MA',4,'MI',5,'JU',6,'VI',7,'SA') AS dia_salida,"
				+ "     TO_CHAR(h_salida,'HH24:MI') AS h_salida,"
				+ "     DECODE(dia_disp_uni_cedis,1,'DO',2,'LU',3,'MA',4,'MI',5,'JU',6,'VI',7,'SA') AS dia_disp_uni_cedis,"
				+ "     TO_CHAR(h_disp_uni_cedis,'HH24:MI') AS h_disp_uni_cedis,"
				+ "     DECODE(dia_llegada_pt,1,'DO',2,'LU',3,'MA',4,'MI',5,'JU',6,'VI',7,'SA') AS dia_llegada_pt,"
				+ "     TO_CHAR(h_llegada_pt,'HH24:MI') AS h_llegada_pt,"
				+ "     DECODE(dia_salida_pt,1,'DO',2,'LU',3,'MA',4,'MI',5,'JU',6,'VI',7,'SA') AS dia_salida_pt,"
				+ "     TO_CHAR(h_salida_pt,'HH24:MI') AS h_salida_pt" + " FROM" + "     rdm100.xxfc_pmo_route"
				+ " WHERE" + "     status = 'A'" + "     AND route IN (" + "         SELECT" + "             b.route"
				+ "         FROM" + "             rdm100.carrier_service_route a," + "             rdm100.route_dest b,"
				+ "             rdm100.xxfc_store_v c," + "             rdm100.route r,"
				+ "             rdm100.xxfc_pmo_tdistrib_servicio ser" + "         WHERE"
				+ "             a.facility_id = b.facility_id" + "             AND a.facility_id = r.facility_id"
				+ "             AND a.service_code = ser.servicio"
				+ "             AND ser.servicio = DECODE('ALL','ALL',ser.servicio,substr('ALL',instr('ALL',ser.servicio),length(ser.servicio) ) )"
				+ "             AND ser.dest_type = c.dest_type" + "             AND b.dest_id = c.store"
				+ "             AND a.route = r.route" + "             AND c.default_wh IN ("
				+ "                 SELECT" + "                     primary_vwh" + "                 FROM"
				+ "                     rdm100.xxfc_con_prog_wh_view" + "                 WHERE"
				+ "                     primary_vwh IS NOT NULL" + "             )"
				+ "             AND substr(store_name10,1,5) ='" + plaza + "'" + "         GROUP BY"
				+ "             b.route" + "     )" + " ORDER BY" + "     route ASC";
	}

	/**
	 * Query que muestra las columnas de la consulta queryBuscarPorPlaza
	 * tipo_distro, route, dia_gen_ped, dia_ini_entrega, h_ini_entrega
	 * 
	 * @param plaza
	 * @return
	 * @author JesusH4@hexaware.com
	 */
	public String getQueryTipoDistro(String plaza) {
		return "SELECT " + "tipo_distro, " + "route, " + "dia_gen_ped, " + "dia_ini_entrega, " + "h_ini_entrega "
				+ "FROM (" + queryBuscarPorPlaza(plaza) + " )";
	}

	/**
	 * Query que muestra las columnas de la consulta queryBuscarPorPlaza
	 * h_fin_entrega, dia_prog, h_prog, dia_ini_prod, h_ini_prod
	 * 
	 * @param plaza
	 * @return
	 * @author JesusH4@hexaware.com
	 */
	public String getQueryFinEntrega(String plaza) {
		return "SELECT " + "h_fin_entrega, " + "dia_prog, " + "h_prog, " + "dia_ini_prod, " + "h_ini_prod " + "FROM  ("
				+ queryBuscarPorPlaza(plaza) + " )";
	}

	/**
	 * Query que muestra las columnas de la consulta queryBuscarPorPlaza
	 * dia_fin_prod, h_fin_prod, dia_ini_verificacion, h_ini_verificacion,
	 * dia_fin_verificacion
	 * 
	 * @param plaza
	 * @return
	 * @author JesusH4@hexaware.com
	 */
	public String getQueryFinProd(String plaza) {
		return "SELECT " + "dia_fin_prod, " + "h_fin_prod, " + "dia_ini_verificacion, " + "h_ini_verificacion, "
				+ "dia_fin_verificacion " + "FROM  (" + queryBuscarPorPlaza(plaza) + " )";
	}

	/**
	 * Query que muestra las columnas de la consulta queryBuscarPorPlaza
	 * h_fin_verificacion, dia_ini_carga, h_ini_carga, dia_fin_carga, h_fin_carga
	 * 
	 * @param plaza
	 * @return
	 * @author JesusH4@hexaware.com
	 */
	public String getQueryFinVerificacion(String plaza) {
		return "SELECT " + "h_fin_verificacion, " + "dia_ini_carga, " + "h_ini_carga, " + "dia_fin_carga, "
				+ "h_fin_carga " + "FROM  (" + queryBuscarPorPlaza(plaza) + " )";
	}

	/**
	 * Query que muestra las columnas de la consulta queryBuscarPorPlaza dia_salida,
	 * h_salida, dia_disp_uni_cedis, h_disp_uni_cedis, dia_llegada_pt
	 * 
	 * @param plaza
	 * @return
	 * @author JesusH4@hexaware.com
	 */
	public String getQuerySalida(String plaza) {
		return "SELECT " + "dia_salida, " + "h_salida, " + "dia_disp_uni_cedis, " + "h_disp_uni_cedis, "
				+ "dia_llegada_pt " + "FROM  (" + queryBuscarPorPlaza(plaza) + " )";
	}

	/**
	 * Query que muestra las columnas de la consulta queryBuscarPorPlaza
	 * h_llegada_pt, dia_salida_pt, h_salida_pt
	 * 
	 * @param plaza
	 * @return
	 * @author JesusH4@hexaware.com
	 */
	public String getQueryLlegadaPT(String plaza) {
		return "SELECT " + "h_llegada_pt, " + "dia_salida_pt, " + "h_salida_pt " + "FROM  ("
				+ queryBuscarPorPlaza(plaza) + " )";
	}

	/**
	 * Metodo usando para ejecutar el query de busqueda por plaza, segmentando las
	 * columnas para mejorar la visualizacion en la evidencia, agrega unicamente la
	 * evidencia de la tabla
	 * 
	 * @param dbConecta conexion a base de datos
	 * @param plaza     plaza a buscar
	 * @param tf        define si quieres incluir el SQL Statement junto con los
	 *                  resultados "true", "false" para incluir solo los resultados
	 * @throws SQLException
	 * @author JesusH4@hexaware.com
	 */
	public void cambiosMasivosPmoPlaza(SQLUtil dbConecta, String plaza, Boolean tf) throws SQLException {

		SQLResult getQueryTipoDistroResult = executeQuery(dbConecta, getQueryTipoDistro(plaza));
		logger.info(String.valueOf(getQueryTipoDistroResult.isEmpty()));
		testCase.addQueryEvidenceCurrentStep(getQueryTipoDistroResult, tf);

		SQLResult getQueryFinEntregaResult = executeQuery(dbConecta, getQueryFinEntrega(plaza));
		logger.info(String.valueOf(getQueryFinEntregaResult.isEmpty()));
		testCase.addQueryEvidenceCurrentStep(getQueryFinEntregaResult, tf);

		SQLResult getQueryFinProdResult = executeQuery(dbConecta, getQueryFinProd(plaza));
		logger.info(String.valueOf(getQueryFinProdResult.isEmpty()));
		testCase.addQueryEvidenceCurrentStep(getQueryFinProdResult, tf);

		SQLResult getQueryFinVerificacionResult = executeQuery(dbConecta, getQueryFinVerificacion(plaza));
		logger.info(String.valueOf(getQueryFinVerificacionResult.isEmpty()));
		testCase.addQueryEvidenceCurrentStep(getQueryFinVerificacionResult, tf);

		SQLResult getQuerySalidaResult = executeQuery(dbConecta, getQuerySalida(plaza));
		logger.info(String.valueOf(getQuerySalidaResult.isEmpty()));
		testCase.addQueryEvidenceCurrentStep(getQuerySalidaResult, tf);

		SQLResult getQueryLlegadaPTResult = executeQuery(dbConecta, getQueryLlegadaPT(plaza));
		logger.info(String.valueOf(getQueryLlegadaPTResult.isEmpty()));
		testCase.addQueryEvidenceCurrentStep(getQueryLlegadaPTResult, tf);
	}

	/**
	 * Query utilizado para buscar por plaza y tipo distro
	 *
	 * @param plaza
	 * @param tipoDistro
	 * @return
	 * @author JesusH4@hexaware.com
	 */
	public String queryBuscarPorPlazaTipoDistro(String plaza, String tipoDistro) {
		return "SELECT" + "     *" + " FROM" + "     (" + "         SELECT" + "             *" + "         FROM"
				+ "             (" + "                 SELECT" + "                     tipo_distro,"
				+ "                     route,"
				+ "                     DECODE(dia_gen_ped,1,'DO',2,'LU',3,'MA',4,'MI',5,'JU',6,'VI',7,'SA') AS dia_gen_ped,"
				+ "                     DECODE(dia_ini_entrega,1,'DO',2,'LU',3,'MA',4,'MI',5,'JU',6,'VI',7,'SA') AS dia_ini_entrega,"
				+ "                     TO_CHAR(h_ini_entrega,'HH24:MI') as h_ini_entrega,"
				+ "                     TO_CHAR(h_fin_entrega,'HH24:MI') as h_fin_entrega,"
				+ "                     DECODE(dia_prog,1,'DO',2,'LU',3,'MA',4,'MI',5,'JU',6,'VI',7,'SA') AS dia_prog,"
				+ "                     TO_CHAR(h_prog,'HH24:MI') as h_prog,"
				+ "                     DECODE(dia_ini_prod,1,'DO',2,'LU',3,'MA',4,'MI',5,'JU',6,'VI',7,'SA') AS dia_ini_prod,"
				+ "                     TO_CHAR(h_ini_prod,'HH24:MI') as h_ini_prod,"
				+ "                     DECODE(dia_fin_prod,1,'DO',2,'LU',3,'MA',4,'MI',5,'JU',6,'VI',7,'SA') AS dia_fin_prod,"
				+ "                     TO_CHAR(h_fin_prod,'HH24:MI') as h_fin_prod,"
				+ "                     DECODE(dia_ini_verificacion,1,'DO',2,'LU',3,'MA',4,'MI',5,'JU',6,'VI',7,'SA') AS dia_ini_verificacion,"
				+ "                     TO_CHAR(h_ini_verificacion,'HH24:MI') as h_ini_verificacion,"
				+ "                     DECODE(dia_fin_verificacion,1,'DO',2,'LU',3,'MA',4,'MI',5,'JU',6,'VI',7,'SA') AS dia_fin_verificacion,"
				+ "                     TO_CHAR(h_fin_verificacion,'HH24:MI') as h_fin_verificacion,"
				+ "                     DECODE(dia_ini_carga,1,'DO',2,'LU',3,'MA',4,'MI',5,'JU',6,'VI',7,'SA') AS dia_ini_carga,"
				+ "                     TO_CHAR(h_ini_carga,'HH24:MI') as h_ini_carga,"
				+ "                     DECODE(dia_fin_carga,1,'DO',2,'LU',3,'MA',4,'MI',5,'JU',6,'VI',7,'SA') AS dia_fin_carga,"
				+ "                     TO_CHAR(h_fin_carga,'HH24:MI') as h_fin_carga,"
				+ "                     DECODE(dia_salida,1,'DO',2,'LU',3,'MA',4,'MI',5,'JU',6,'VI',7,'SA') AS dia_salida,"
				+ "                     TO_CHAR(h_salida,'HH24:MI') as h_salida,"
				+ "                     DECODE(dia_disp_uni_cedis,1,'DO',2,'LU',3,'MA',4,'MI',5,'JU',6,'VI',7,'SA') AS dia_disp_uni_cedis,"
				+ "                     TO_CHAR(h_disp_uni_cedis,'HH24:MI') as h_disp_uni_cedis,"
				+ "                     DECODE(dia_llegada_pt,1,'DO',2,'LU',3,'MA',4,'MI',5,'JU',6,'VI',7,'SA') AS dia_llegada_pt,"
				+ "                     TO_CHAR(h_llegada_pt,'HH24:MI') as h_llegada_pt,"
				+ "                     DECODE(dia_salida_pt,1,'DO',2,'LU',3,'MA',4,'MI',5,'JU',6,'VI',7,'SA') AS dia_salida_pt,"
				+ "                     TO_CHAR(h_salida_pt,'HH24:MI') as h_salida_pt" + "                 FROM"
				+ "                     rdm100.xxfc_pmo_route" + "                 WHERE"
				+ "                     status = 'A'" + "                     AND route IN ("
				+ "                         SELECT" + "                             b.route"
				+ "                         FROM" + "                             rdm100.carrier_service_route a,"
				+ "                             rdm100.route_dest b,"
				+ "                             rdm100.xxfc_store_v c," + "                             rdm100.route r,"
				+ "                             rdm100.xxfc_pmo_tdistrib_servicio ser"
				+ "                         WHERE" + "                             a.facility_id = b.facility_id"
				+ "                             AND a.facility_id = r.facility_id"
				+ "                             AND a.service_code = ser.servicio"
				+ "                             AND ser.servicio = DECODE('ALL','ALL',ser.servicio,substr('ALL',instr('ALL',ser.servicio"
				+ "                             ),length(ser.servicio) ) )"
				+ "                             AND ser.dest_type = c.dest_type"
				+ "                             AND b.dest_id = c.store"
				+ "                             AND a.route = r.route"
				+ "                             AND c.default_wh IN (" + "                                 SELECT"
				+ "                                     primary_vwh" + "                                 FROM"
				+ "                                     rdm100.xxfc_con_prog_wh_view"
				+ "                                 WHERE"
				+ "                                     primary_vwh IS NOT NULL" + "                             )"
				+ "                             AND substr(store_name10,1,5) ='" + plaza + "'"
				+ "                         GROUP BY" + "                             b.route"
				+ "                     )" + "                 ORDER BY" + "                     route ASC"
				+ "             )" + "         WHERE" + "             tipo_distro ='" + tipoDistro + "'" + "     )";
	}

	/**
	 * Query que muestra las columnas de la consulta queryBuscarPorPlazaTipoDistro
	 * tipo_distro, route, dia_gen_ped, dia_ini_entrega, h_ini_entrega
	 * 
	 * @param plaza
	 * @param tipoDistro
	 * @return
	 * @author JesusH4@hexaware.com
	 */
	public String getQueryTipoDistro(String plaza, String tipoDistro) {
		return "SELECT " + "tipo_distro, " + "route, " + "dia_gen_ped, " + "dia_ini_entrega, " + "h_ini_entrega "
				+ "FROM (" + queryBuscarPorPlazaTipoDistro(plaza, tipoDistro) + " )";
	}

	/**
	 * Query que muestra las columnas de la consulta queryBuscarPorPlazaTipoDistro
	 * h_fin_entrega, dia_prog, h_prog, dia_ini_prod, h_ini_prod
	 * 
	 * @param plaza
	 * @param tipoDistro
	 * @return
	 * @author JesusH4@hexaware.com
	 */
	public String getQueryFinEntrega(String plaza, String tipoDistro) {
		return "SELECT " + "h_fin_entrega, " + "dia_prog, " + "h_prog, " + "dia_ini_prod, " + "h_ini_prod " + "FROM  ("
				+ queryBuscarPorPlazaTipoDistro(plaza, tipoDistro) + " )";
	}

	/**
	 * Query que muestra las columnas de la consulta queryBuscarPorPlazaTipoDistro
	 * dia_fin_prod, h_fin_prod, dia_ini_verificacion, h_ini_verificacion,
	 * dia_fin_verificacion
	 * 
	 * @param plaza
	 * @param tipoDistro
	 * @return
	 * @author JesusH4@hexaware.com
	 */
	public String getQueryFinProd(String plaza, String tipoDistro) {
		return "SELECT " + "dia_fin_prod, " + "h_fin_prod, " + "dia_ini_verificacion, " + "h_ini_verificacion, "
				+ "dia_fin_verificacion " + "FROM  (" + queryBuscarPorPlazaTipoDistro(plaza, tipoDistro) + " )";
	}

	/**
	 * Query que muestra las columnas de la consulta queryBuscarPorPlazaTipoDistro
	 * h_fin_verificacion, dia_ini_carga, h_ini_carga, dia_fin_carga, h_fin_carga
	 * 
	 * @param plaza
	 * @param tipoDistro
	 * @return
	 * @author JesusH4@hexaware.com
	 */
	public String getQueryFinVerificacion(String plaza, String tipoDistro) {
		return "SELECT " + "h_fin_verificacion, " + "dia_ini_carga, " + "h_ini_carga, " + "dia_fin_carga, "
				+ "h_fin_carga " + "FROM  (" + queryBuscarPorPlazaTipoDistro(plaza, tipoDistro) + " )";
	}

	/**
	 * Query que muestra las columnas de la consulta queryBuscarPorPlazaTipoDistro
	 * dia_salida, h_salida, dia_disp_uni_cedis, h_disp_uni_cedis, dia_llegada_pt
	 * 
	 * @param plaza
	 * @param tipoDistro
	 * @return
	 * @author JesusH4@hexaware.com
	 */
	public String getQuerySalida(String plaza, String tipoDistro) {
		return "SELECT " + "dia_salida, " + "h_salida, " + "dia_disp_uni_cedis, " + "h_disp_uni_cedis, "
				+ "dia_llegada_pt " + "FROM  (" + queryBuscarPorPlazaTipoDistro(plaza, tipoDistro) + " )";
	}

	/**
	 * Query que muestra las columnas de la consulta queryBuscarPorPlazaTipoDistro
	 * h_llegada_pt, dia_salida_pt, h_salida_pt
	 * 
	 * @param plaza
	 * @param tipoDistro
	 * @return
	 * @author JesusH4@hexaware.com
	 */
	public String getQueryLlegadaPT(String plaza, String tipoDistro) {
		return "SELECT " + "h_llegada_pt, " + "dia_salida_pt, " + "h_salida_pt " + "FROM  ("
				+ queryBuscarPorPlazaTipoDistro(plaza, tipoDistro) + " )";
	}

	/**
	 * Metodo usando para ejecutar el query de busqueda por plaza, segmentando las
	 * columnas para mejorar la visualizacion en la evidencia, agrega unicamente la
	 * evidencia de la tabla
	 * 
	 * @param dbConecta  conexion a base de datos
	 * @param plaza      plaza a buscar
	 * @param tipoDistro tipo de distribucion a buscar
	 * @param tf         define si quieres incluir el SQL Statement junto con los
	 *                   resultados "true", "false" para incluir solo los resultados
	 * @throws SQLException
	 * @author JesusH4@hexaware.com
	 */
	public void cambiosMasivosPmoPlazaTipoDistro(SQLUtil dbConecta, String plaza, String tipoDistro, Boolean tf)
			throws SQLException {

		SQLResult getQueryTipoDistroResult = executeQuery(dbConecta, getQueryTipoDistro(plaza, tipoDistro));
		logger.info(String.valueOf(getQueryTipoDistroResult.isEmpty()));
		testCase.addQueryEvidenceCurrentStep(getQueryTipoDistroResult, tf);

		SQLResult getQueryFinEntregaResult = executeQuery(dbConecta, getQueryFinEntrega(plaza, tipoDistro));
		logger.info(String.valueOf(getQueryFinEntregaResult.isEmpty()));
		testCase.addQueryEvidenceCurrentStep(getQueryFinEntregaResult, tf);

		SQLResult getQueryFinProdResult = executeQuery(dbConecta, getQueryFinProd(plaza, tipoDistro));
		logger.info(String.valueOf(getQueryFinProdResult.isEmpty()));
		testCase.addQueryEvidenceCurrentStep(getQueryFinProdResult, tf);

		SQLResult getQueryFinVerificacionResult = executeQuery(dbConecta, getQueryFinVerificacion(plaza, tipoDistro));
		logger.info(String.valueOf(getQueryFinVerificacionResult.isEmpty()));
		testCase.addQueryEvidenceCurrentStep(getQueryFinVerificacionResult, tf);

		SQLResult getQuerySalidaResult = executeQuery(dbConecta, getQuerySalida(plaza, tipoDistro));
		logger.info(String.valueOf(getQuerySalidaResult.isEmpty()));
		testCase.addQueryEvidenceCurrentStep(getQuerySalidaResult, tf);

		SQLResult getQueryLlegadaPTResult = executeQuery(dbConecta, getQueryLlegadaPT(plaza, tipoDistro));
		logger.info(String.valueOf(getQueryLlegadaPTResult.isEmpty()));
		testCase.addQueryEvidenceCurrentStep(getQueryLlegadaPTResult, tf);
	}

	/**
	 * Query utilizado para buscar por plaza y ruta
	 *
	 * @param plaza
	 * @param ruta
	 * @return
	 * @author JesusH4@hexaware.com
	 */
	public String queryBuscarPorPlazaRuta(String plaza, String ruta) {
		return "SELECT" + "     *" + " FROM" + "     (" + "         SELECT" + "             *" + "         FROM"
				+ "             (" + "                 SELECT" + "                     tipo_distro,"
				+ "                     route,"
				+ "                     DECODE(dia_gen_ped,1,'DO',2,'LU',3,'MA',4,'MI',5,'JU',6,'VI',7,'SA') AS dia_gen_ped,"
				+ "                     DECODE(dia_ini_entrega,1,'DO',2,'LU',3,'MA',4,'MI',5,'JU',6,'VI',7,'SA') AS dia_ini_entrega,"
				+ "                     TO_CHAR(h_ini_entrega,'HH24:MI') AS h_ini_entrega,"
				+ "                     TO_CHAR(h_fin_entrega,'HH24:MI') AS h_fin_entrega,"
				+ "                     DECODE(dia_prog,1,'DO',2,'LU',3,'MA',4,'MI',5,'JU',6,'VI',7,'SA') AS dia_prog,"
				+ "                     TO_CHAR(h_prog,'HH24:MI') AS h_prog,"
				+ "                     DECODE(dia_ini_prod,1,'DO',2,'LU',3,'MA',4,'MI',5,'JU',6,'VI',7,'SA') AS dia_ini_prod,"
				+ "                     TO_CHAR(h_ini_prod,'HH24:MI') AS h_ini_prod,"
				+ "                     DECODE(dia_fin_prod,1,'DO',2,'LU',3,'MA',4,'MI',5,'JU',6,'VI',7,'SA') AS dia_fin_prod,"
				+ "                     TO_CHAR(h_fin_prod,'HH24:MI') AS h_fin_prod,"
				+ "                     DECODE(dia_ini_verificacion,1,'DO',2,'LU',3,'MA',4,'MI',5,'JU',6,'VI',7,'SA') AS dia_ini_verificacion,"
				+ "                     TO_CHAR(h_ini_verificacion,'HH24:MI') AS h_ini_verificacion,"
				+ "                     DECODE(dia_fin_verificacion,1,'DO',2,'LU',3,'MA',4,'MI',5,'JU',6,'VI',7,'SA') AS dia_fin_verificacion,"
				+ "                     TO_CHAR(h_fin_verificacion,'HH24:MI') AS h_fin_verificacion,"
				+ "                     DECODE(dia_ini_carga,1,'DO',2,'LU',3,'MA',4,'MI',5,'JU',6,'VI',7,'SA') AS dia_ini_carga,"
				+ "                     TO_CHAR(h_ini_carga,'HH24:MI') AS h_ini_carga,"
				+ "                     DECODE(dia_fin_carga,1,'DO',2,'LU',3,'MA',4,'MI',5,'JU',6,'VI',7,'SA') AS dia_fin_carga,"
				+ "                     TO_CHAR(h_fin_carga,'HH24:MI') AS h_fin_carga,"
				+ "                     DECODE(dia_salida,1,'DO',2,'LU',3,'MA',4,'MI',5,'JU',6,'VI',7,'SA') AS dia_salida,"
				+ "                     TO_CHAR(h_salida,'HH24:MI') AS h_salida,"
				+ "                     DECODE(dia_disp_uni_cedis,1,'DO',2,'LU',3,'MA',4,'MI',5,'JU',6,'VI',7,'SA') AS dia_disp_uni_cedis,"
				+ "                     TO_CHAR(h_disp_uni_cedis,'HH24:MI') AS h_disp_uni_cedis,"
				+ "                     DECODE(dia_llegada_pt,1,'DO',2,'LU',3,'MA',4,'MI',5,'JU',6,'VI',7,'SA') AS dia_llegada_pt,"
				+ "                     TO_CHAR(h_llegada_pt,'HH24:MI') AS h_llegada_pt,"
				+ "                     DECODE(dia_salida_pt,1,'DO',2,'LU',3,'MA',4,'MI',5,'JU',6,'VI',7,'SA') AS dia_salida_pt,"
				+ "                     TO_CHAR(h_salida_pt,'HH24:MI') AS h_salida_pt" + "                 FROM"
				+ "                     rdm100.xxfc_pmo_route" + "                 WHERE"
				+ "                     status = 'A'" + "                     AND route IN ("
				+ "                         SELECT" + "                             b.route"
				+ "                         FROM" + "                             rdm100.carrier_service_route a,"
				+ "                             rdm100.route_dest b,"
				+ "                             rdm100.xxfc_store_v c," + "                             rdm100.route r,"
				+ "                             rdm100.xxfc_pmo_tdistrib_servicio ser"
				+ "                         WHERE" + "                             a.facility_id = b.facility_id"
				+ "                             AND a.facility_id = r.facility_id"
				+ "                             AND a.service_code = ser.servicio"
				+ "                             AND ser.servicio = DECODE('ALL','ALL',ser.servicio,substr('ALL',instr('ALL',ser.servicio"
				+ "                             ),length(ser.servicio) ) )"
				+ "                             AND ser.dest_type = c.dest_type"
				+ "                             AND b.dest_id = c.store"
				+ "                             AND a.route = r.route"
				+ "                             AND c.default_wh IN (" + "                                 SELECT"
				+ "                                     primary_vwh" + "                                 FROM"
				+ "                                     rdm100.xxfc_con_prog_wh_view"
				+ "                                 WHERE"
				+ "                                     primary_vwh IS NOT NULL" + "                             )"
				+ "                             AND substr(store_name10,1,5) ='" + plaza + "'"
				+ "                         GROUP BY" + "                             b.route"
				+ "                     )" + "                 ORDER BY" + "                     route ASC"
				+ "             )" + "         WHERE" + "             route ='" + ruta + "'" + "     )";
	}

	/**
	 * Query que muestra las columnas de la consulta queryBuscarPorPlazaRuta
	 * tipo_distro, route, dia_gen_ped, dia_ini_entrega, h_ini_entrega
	 *
	 * @param plaza
	 * @param ruta
	 * @return
	 * @author JesusH4@hexaware.com
	 */
	public String getQueryTipoDistroRuta(String plaza, String ruta) {
		return "SELECT " + "tipo_distro, " + "route, " + "dia_gen_ped, " + "dia_ini_entrega, " + "h_ini_entrega "
				+ "FROM (" + queryBuscarPorPlazaRuta(plaza, ruta) + " )";
	}

	/**
	 * Query que muestra las columnas de la consulta queryBuscarPorPlazaRuta
	 * h_fin_entrega, dia_prog, h_prog, dia_ini_prod, h_ini_prod
	 *
	 * @param plaza
	 * @param ruta
	 * @return
	 * @author JesusH4@hexaware.com
	 */
	public String getQueryFinEntregaRuta(String plaza, String ruta) {
		return "SELECT " + "h_fin_entrega, " + "dia_prog, " + "h_prog, " + "dia_ini_prod, " + "h_ini_prod " + "FROM  ("
				+ queryBuscarPorPlazaRuta(plaza, ruta) + " )";
	}

	/**
	 * Query que muestra las columnas de la consulta queryBuscarPorPlazaRuta
	 * dia_fin_prod, h_fin_prod, dia_ini_verificacion, h_ini_verificacion,
	 * dia_fin_verificacion
	 *
	 * @param plaza
	 * @param ruta
	 * @return
	 * @author JesusH4@hexaware.com
	 */
	public String getQueryFinProdRuta(String plaza, String ruta) {
		return "SELECT " + "dia_fin_prod, " + "h_fin_prod, " + "dia_ini_verificacion, " + "h_ini_verificacion, "
				+ "dia_fin_verificacion " + "FROM  (" + queryBuscarPorPlazaRuta(plaza, ruta) + " )";
	}

	/**
	 * Query que muestra las columnas de la consulta queryBuscarPorPlazaRuta
	 * h_fin_verificacion, dia_ini_carga, h_ini_carga, dia_fin_carga, h_fin_carga
	 *
	 * @param plaza
	 * @param ruta
	 * @return
	 * @author JesusH4@hexaware.com
	 */
	public String getQueryFinVerificacionRuta(String plaza, String ruta) {
		return "SELECT " + "h_fin_verificacion, " + "dia_ini_carga, " + "h_ini_carga, " + "dia_fin_carga, "
				+ "h_fin_carga " + "FROM  (" + queryBuscarPorPlazaRuta(plaza, ruta) + " )";
	}

	/**
	 * Query que muestra las columnas de la consulta queryBuscarPorPlazaRuta
	 * dia_salida, h_salida, dia_disp_uni_cedis, h_disp_uni_cedis, dia_llegada_pt
	 *
	 * @param plaza
	 * @param ruta
	 * @return
	 * @author JesusH4@hexaware.com
	 */
	public String getQuerySalidaRuta(String plaza, String ruta) {
		return "SELECT " + "dia_salida, " + "h_salida, " + "dia_disp_uni_cedis, " + "h_disp_uni_cedis, "
				+ "dia_llegada_pt " + "FROM  (" + queryBuscarPorPlazaRuta(plaza, ruta) + " )";
	}

	/**
	 * Query que muestra las columnas de la consulta queryBuscarPorPlazaRuta
	 * h_llegada_pt, dia_salida_pt, h_salida_pt
	 *
	 * @param plaza
	 * @param ruta
	 * @return
	 * @author JesusH4@hexaware.com
	 */
	public String getQueryLlegadaPTRuta(String plaza, String ruta) {
		return "SELECT " + "h_llegada_pt, " + "dia_salida_pt, " + "h_salida_pt " + "FROM  ("
				+ queryBuscarPorPlazaRuta(plaza, ruta) + " )";
	}

	/**
	 * Metodo usando para ejecutar el query de busqueda por plaza, segmentando las
	 * columnas para mejorar la visualizacion en la evidencia, agrega unicamente la
	 * evidencia de la tabla
	 *
	 * @param dbConecta conexion a base de datos
	 * @param plaza     plaza a buscar
	 * @param ruta      ruta a buscar
	 * @param tf        define si quieres incluir el SQL Statement junto con los
	 *                  resultados "true", "false" para incluir solo los resultados
	 * @throws SQLException
	 * @author JesusH4@hexaware.com
	 */
	public void cambiosMasivosPmoPlazaRuta(SQLUtil dbConecta, String plaza, String ruta, Boolean tf)
			throws SQLException {

		SQLResult getQueryTipoDistroResult = executeQuery(dbConecta, getQueryTipoDistroRuta(plaza, ruta));
		logger.info(String.valueOf(getQueryTipoDistroResult.isEmpty()));
		testCase.addQueryEvidenceCurrentStep(getQueryTipoDistroResult, tf);

		SQLResult getQueryFinEntregaResult = executeQuery(dbConecta, getQueryFinEntregaRuta(plaza, ruta));
		logger.info(String.valueOf(getQueryFinEntregaResult.isEmpty()));
		testCase.addQueryEvidenceCurrentStep(getQueryFinEntregaResult, tf);

		SQLResult getQueryFinProdResult = executeQuery(dbConecta, getQueryFinProdRuta(plaza, ruta));
		logger.info(String.valueOf(getQueryFinProdResult.isEmpty()));
		testCase.addQueryEvidenceCurrentStep(getQueryFinProdResult, tf);

		SQLResult getQueryFinVerificacionResult = executeQuery(dbConecta, getQueryFinVerificacionRuta(plaza, ruta));
		logger.info(String.valueOf(getQueryFinVerificacionResult.isEmpty()));
		testCase.addQueryEvidenceCurrentStep(getQueryFinVerificacionResult, tf);

		SQLResult getQuerySalidaResult = executeQuery(dbConecta, getQuerySalidaRuta(plaza, ruta));
		logger.info(String.valueOf(getQuerySalidaResult.isEmpty()));
		testCase.addQueryEvidenceCurrentStep(getQuerySalidaResult, tf);

		SQLResult getQueryLlegadaPTResult = executeQuery(dbConecta, getQueryLlegadaPTRuta(plaza, ruta));
		logger.info(String.valueOf(getQueryLlegadaPTResult.isEmpty()));
		testCase.addQueryEvidenceCurrentStep(getQueryLlegadaPTResult, tf);
	}

	/**
	 * Query utilizado para buscar por plaza y dia generacion pedido
	 *
	 * @param plaza
	 * @param diaGen
	 * @return
	 * @author JesusH4@hexaware.com
	 */
	public String queryBuscarPorPlazaDiaGen(String plaza, String diaGen) {
		return "SELECT" + "     *" + " FROM" + "     (" + "         SELECT" + "             *" + "         FROM"
				+ "             (" + "                 SELECT" + "    tipo_distro," + "    route,"
				+ "    DECODE(dia_gen_ped,1,'DO',2,'LU',3,'MA',4,'MI',5,'JU',6,'VI',7,'SA') AS dia_gen_ped,"
				+ "    DECODE(dia_ini_entrega,1,'DO',2,'LU',3,'MA',4,'MI',5,'JU',6,'VI',7,'SA') AS dia_ini_entrega,"
				+ "    TO_CHAR(h_ini_entrega,'HH24:MI') AS h_ini_entrega,"
				+ "    TO_CHAR(h_fin_entrega,'HH24:MI') AS h_fin_entrega,"
				+ "    DECODE(dia_prog,1,'DO',2,'LU',3,'MA',4,'MI',5,'JU',6,'VI',7,'SA') AS dia_prog,"
				+ "    TO_CHAR(h_prog,'HH24:MI') AS h_prog,"
				+ "    DECODE(dia_ini_prod,1,'DO',2,'LU',3,'MA',4,'MI',5,'JU',6,'VI',7,'SA') AS dia_ini_prod,"
				+ "    TO_CHAR(h_ini_prod,'HH24:MI') AS h_ini_prod,"
				+ "    DECODE(dia_fin_prod,1,'DO',2,'LU',3,'MA',4,'MI',5,'JU',6,'VI',7,'SA') AS dia_fin_prod,"
				+ "    TO_CHAR(h_fin_prod,'HH24:MI') AS h_fin_prod,"
				+ "    DECODE(dia_ini_verificacion,1,'DO',2,'LU',3,'MA',4,'MI',5,'JU',6,'VI',7,'SA') AS dia_ini_verificacion,"
				+ "    TO_CHAR(h_ini_verificacion,'HH24:MI') AS h_ini_verificacion,"
				+ "    DECODE(dia_fin_verificacion,1,'DO',2,'LU',3,'MA',4,'MI',5,'JU',6,'VI',7,'SA') AS dia_fin_verificacion,"
				+ "    TO_CHAR(h_fin_verificacion,'HH24:MI') AS h_fin_verificacion,"
				+ "    DECODE(dia_ini_carga,1,'DO',2,'LU',3,'MA',4,'MI',5,'JU',6,'VI',7,'SA') AS dia_ini_carga,"
				+ "    TO_CHAR(h_ini_carga,'HH24:MI') AS h_ini_carga,"
				+ "    DECODE(dia_fin_carga,1,'DO',2,'LU',3,'MA',4,'MI',5,'JU',6,'VI',7,'SA') AS dia_fin_carga,"
				+ "    TO_CHAR(h_fin_carga,'HH24:MI') AS h_fin_carga,"
				+ "    DECODE(dia_salida,1,'DO',2,'LU',3,'MA',4,'MI',5,'JU',6,'VI',7,'SA') AS dia_salida,"
				+ "    TO_CHAR(h_salida,'HH24:MI') AS h_salida,"
				+ "    DECODE(dia_disp_uni_cedis,1,'DO',2,'LU',3,'MA',4,'MI',5,'JU',6,'VI',7,'SA') AS dia_disp_uni_cedis,"
				+ "    TO_CHAR(h_disp_uni_cedis,'HH24:MI') AS h_disp_uni_cedis,"
				+ "    DECODE(dia_llegada_pt,1,'DO',2,'LU',3,'MA',4,'MI',5,'JU',6,'VI',7,'SA') AS dia_llegada_pt,"
				+ "    TO_CHAR(h_llegada_pt,'HH24:MI') AS h_llegada_pt,"
				+ "    DECODE(dia_salida_pt,1,'DO',2,'LU',3,'MA',4,'MI',5,'JU',6,'VI',7,'SA') AS dia_salida_pt,"
				+ "    TO_CHAR(h_salida_pt,'HH24:MI') AS h_salida_pt" + "                 FROM"
				+ "                     rdm100.xxfc_pmo_route" + "                 WHERE"
				+ "                     status = 'A'" + "                     AND route IN ("
				+ "                         SELECT" + "                             b.route"
				+ "                         FROM" + "                             rdm100.carrier_service_route a,"
				+ "                             rdm100.route_dest b,"
				+ "                             rdm100.xxfc_store_v c," + "                             rdm100.route r,"
				+ "                             rdm100.xxfc_pmo_tdistrib_servicio ser"
				+ "                         WHERE" + "                             a.facility_id = b.facility_id"
				+ "                             AND a.facility_id = r.facility_id"
				+ "                             AND a.service_code = ser.servicio"
				+ "                             AND ser.servicio = DECODE('ALL','ALL',ser.servicio,substr('ALL',instr('ALL',ser.servicio"
				+ "                             ),length(ser.servicio) ) )"
				+ "                             AND ser.dest_type = c.dest_type"
				+ "                             AND b.dest_id = c.store"
				+ "                             AND a.route = r.route"
				+ "                             AND c.default_wh IN (" + "                                 SELECT"
				+ "                                     primary_vwh" + "                                 FROM"
				+ "                                     rdm100.xxfc_con_prog_wh_view"
				+ "                                 WHERE"
				+ "                                     primary_vwh IS NOT NULL" + "                             )"
				+ "                             AND substr(store_name10,1,5) ='" + plaza + "'"
				+ "                         GROUP BY" + "                             b.route"
				+ "                     )" + "                 ORDER BY" + "                     route ASC"
				+ "             )" + "         WHERE" + "             DIA_GEN_PED = '" + diaGen + "'" + "     )";
	}

	/**
	 * Query que muestra las columnas de la consulta queryBuscarPorPlazaDiaGen
	 * tipo_distro, route, dia_gen_ped, dia_ini_entrega, h_ini_entrega
	 *
	 * @param plaza
	 * @param diaGen
	 * @return
	 * @author JesusH4@hexaware.com
	 */
	public String getQueryTipoDistroDiaGen(String plaza, String diaGen) {
		return "SELECT " + "tipo_distro, " + "route, " + "dia_gen_ped, " + "dia_ini_entrega, " + "h_ini_entrega "
				+ "FROM (" + queryBuscarPorPlazaDiaGen(plaza, diaGen) + " )";
	}

	/**
	 * Query que muestra las columnas de la consulta queryBuscarPorPlazaDiaGen
	 * h_fin_entrega, dia_prog, h_prog, dia_ini_prod, h_ini_prod
	 *
	 * @param plaza
	 * @param diaGen
	 * @return
	 * @author JesusH4@hexaware.com
	 */
	public String getQueryFinEntregaDiaGen(String plaza, String diaGen) {
		return "SELECT " + "h_fin_entrega, " + "dia_prog, " + "h_prog, " + "dia_ini_prod, " + "h_ini_prod " + "FROM  ("
				+ queryBuscarPorPlazaDiaGen(plaza, diaGen) + " )";
	}

	/**
	 * Query que muestra las columnas de la consulta queryBuscarPorPlazaDiaGen
	 * dia_fin_prod, h_fin_prod, dia_ini_verificacion, h_ini_verificacion,
	 * dia_fin_verificacion
	 *
	 * @param plaza
	 * @param diaGen
	 * @return
	 * @author JesusH4@hexaware.com
	 */
	public String getQueryFinProdDiaGen(String plaza, String diaGen) {
		return "SELECT " + "dia_fin_prod, " + "h_fin_prod, " + "dia_ini_verificacion, " + "h_ini_verificacion, "
				+ "dia_fin_verificacion " + "FROM  (" + queryBuscarPorPlazaDiaGen(plaza, diaGen) + " )";
	}

	/**
	 * Query que muestra las columnas de la consulta queryBuscarPorPlazaDiaGen
	 * h_fin_verificacion, dia_ini_carga, h_ini_carga, dia_fin_carga, h_fin_carga
	 *
	 * @param plaza
	 * @param diaGen
	 * @return
	 * @author JesusH4@hexaware.com
	 */
	public String getQueryFinVerificacionDiaGen(String plaza, String diaGen) {
		return "SELECT " + "h_fin_verificacion, " + "dia_ini_carga, " + "h_ini_carga, " + "dia_fin_carga, "
				+ "h_fin_carga " + "FROM  (" + queryBuscarPorPlazaDiaGen(plaza, diaGen) + " )";
	}

	/**
	 * Query que muestra las columnas de la consulta queryBuscarPorPlazaDiaGen
	 * dia_salida, h_salida, dia_disp_uni_cedis, h_disp_uni_cedis, dia_llegada_pt
	 *
	 * @param plaza
	 * @param diaGen
	 * @return
	 * @author JesusH4@hexaware.com
	 */
	public String getQuerySalidaDiaGen(String plaza, String diaGen) {
		return "SELECT " + "dia_salida, " + "h_salida, " + "dia_disp_uni_cedis, " + "h_disp_uni_cedis, "
				+ "dia_llegada_pt " + "FROM  (" + queryBuscarPorPlazaDiaGen(plaza, diaGen) + " )";
	}

	/**
	 * Query que muestra las columnas de la consulta queryBuscarPorPlazaDiaGen
	 * h_llegada_pt, dia_salida_pt, h_salida_pt
	 *
	 * @param plaza
	 * @param diaGen
	 * @return
	 * @author JesusH4@hexaware.com
	 */
	public String getQueryLlegadaPTDiaGen(String plaza, String diaGen) {
		return "SELECT " + "h_llegada_pt, " + "dia_salida_pt, " + "h_salida_pt " + "FROM  ("
				+ queryBuscarPorPlazaDiaGen(plaza, diaGen) + " )";
	}

	/**
	 * Metodo usando para ejecutar el query de busqueda por plaza, segmentando las
	 * columnas para mejorar la visualizacion en la evidencia, agrega unicamente la
	 * evidencia de la tabla
	 *
	 * @param dbConecta conexion a base de datos
	 * @param plaza     plaza a buscar
	 * @param diaGen    dia generacion pedido a buscar
	 * @param tf        define si quieres incluir el SQL Statement junto con los
	 *                  resultados "true", "false" para incluir solo los resultados
	 * @throws SQLException
	 * @author JesusH4@hexaware.com
	 */
	public void cambiosMasivosPmoPlazaDiaGen(SQLUtil dbConecta, String plaza, String diaGen, Boolean tf)
			throws SQLException {

		SQLResult getQueryTipoDistroDiaGen = executeQuery(dbConecta, getQueryTipoDistroDiaGen(plaza, diaGen));
		logger.info(String.valueOf(getQueryTipoDistroDiaGen.isEmpty()));
		testCase.addQueryEvidenceCurrentStep(getQueryTipoDistroDiaGen, tf);

		SQLResult getQueryFinEntregaDiaGen = executeQuery(dbConecta, getQueryFinEntregaDiaGen(plaza, diaGen));
		logger.info(String.valueOf(getQueryFinEntregaDiaGen.isEmpty()));
		testCase.addQueryEvidenceCurrentStep(getQueryFinEntregaDiaGen, tf);

		SQLResult getQueryFinProdDiaGen = executeQuery(dbConecta, getQueryFinProdDiaGen(plaza, diaGen));
		logger.info(String.valueOf(getQueryFinProdDiaGen.isEmpty()));
		testCase.addQueryEvidenceCurrentStep(getQueryFinProdDiaGen, tf);

		SQLResult getQueryFinVerificacionDiaGen = executeQuery(dbConecta, getQueryFinVerificacionDiaGen(plaza, diaGen));
		logger.info(String.valueOf(getQueryFinVerificacionDiaGen.isEmpty()));
		testCase.addQueryEvidenceCurrentStep(getQueryFinVerificacionDiaGen, tf);

		SQLResult getQuerySalidaDiaGen = executeQuery(dbConecta, getQuerySalidaDiaGen(plaza, diaGen));
		logger.info(String.valueOf(getQuerySalidaDiaGen.isEmpty()));
		testCase.addQueryEvidenceCurrentStep(getQuerySalidaDiaGen, tf);

		SQLResult getQueryLlegadaPTDiaGen = executeQuery(dbConecta, getQueryLlegadaPTDiaGen(plaza, diaGen));
		logger.info(String.valueOf(getQueryLlegadaPTDiaGen.isEmpty()));
		testCase.addQueryEvidenceCurrentStep(getQueryLlegadaPTDiaGen, tf);
	}

	/**
	 * Query utilizado para buscar por plaza y dia entrega
	 *
	 * @param plaza
	 * @param diaEnt
	 * @return
	 * @author JesusH4@hexaware.com
	 */
	public String queryBuscarPorPlazaDiaEnt(String plaza, String diaEnt) {
		return "SELECT" + "     *" + " FROM" + "     (" + "         SELECT" + "             *" + "         FROM"
				+ "             (" + "                 SELECT" + "                     tipo_distro,"
				+ "                     route,"
				+ "                     DECODE(dia_gen_ped,1,'DO',2,'LU',3,'MA',4,'MI',5,'JU',6,'VI',7,'SA') AS dia_gen_ped,"
				+ "                     DECODE(dia_ini_entrega,1,'DO',2,'LU',3,'MA',4,'MI',5,'JU',6,'VI',7,'SA') AS dia_ini_entrega,"
				+ "                     TO_CHAR(h_ini_entrega,'HH24:MI') AS h_ini_entrega,"
				+ "                     TO_CHAR(h_fin_entrega,'HH24:MI') AS h_fin_entrega,"
				+ "                     DECODE(dia_prog,1,'DO',2,'LU',3,'MA',4,'MI',5,'JU',6,'VI',7,'SA') AS dia_prog,"
				+ "                     TO_CHAR(h_prog,'HH24:MI') AS h_prog,"
				+ "                     DECODE(dia_ini_prod,1,'DO',2,'LU',3,'MA',4,'MI',5,'JU',6,'VI',7,'SA') AS dia_ini_prod,"
				+ "                     TO_CHAR(h_ini_prod,'HH24:MI') AS h_ini_prod,"
				+ "                     DECODE(dia_fin_prod,1,'DO',2,'LU',3,'MA',4,'MI',5,'JU',6,'VI',7,'SA') AS dia_fin_prod,"
				+ "                     TO_CHAR(h_fin_prod,'HH24:MI') AS h_fin_prod,"
				+ "                     DECODE(dia_ini_verificacion,1,'DO',2,'LU',3,'MA',4,'MI',5,'JU',6,'VI',7,'SA') AS dia_ini_verificacion,"
				+ "                     TO_CHAR(h_ini_verificacion,'HH24:MI') AS h_ini_verificacion,"
				+ "                     DECODE(dia_fin_verificacion,1,'DO',2,'LU',3,'MA',4,'MI',5,'JU',6,'VI',7,'SA') AS dia_fin_verificacion,"
				+ "                     TO_CHAR(h_fin_verificacion,'HH24:MI') AS h_fin_verificacion,"
				+ "                     DECODE(dia_ini_carga,1,'DO',2,'LU',3,'MA',4,'MI',5,'JU',6,'VI',7,'SA') AS dia_ini_carga,"
				+ "                     TO_CHAR(h_ini_carga,'HH24:MI') AS h_ini_carga,"
				+ "                     DECODE(dia_fin_carga,1,'DO',2,'LU',3,'MA',4,'MI',5,'JU',6,'VI',7,'SA') AS dia_fin_carga,"
				+ "                     TO_CHAR(h_fin_carga,'HH24:MI') AS h_fin_carga,"
				+ "                     DECODE(dia_salida,1,'DO',2,'LU',3,'MA',4,'MI',5,'JU',6,'VI',7,'SA') AS dia_salida,"
				+ "                     TO_CHAR(h_salida,'HH24:MI') AS h_salida,"
				+ "                     DECODE(dia_disp_uni_cedis,1,'DO',2,'LU',3,'MA',4,'MI',5,'JU',6,'VI',7,'SA') AS dia_disp_uni_cedis,"
				+ "                     TO_CHAR(h_disp_uni_cedis,'HH24:MI') AS h_disp_uni_cedis,"
				+ "                     DECODE(dia_llegada_pt,1,'DO',2,'LU',3,'MA',4,'MI',5,'JU',6,'VI',7,'SA') AS dia_llegada_pt,"
				+ "                     TO_CHAR(h_llegada_pt,'HH24:MI') AS h_llegada_pt,"
				+ "                     DECODE(dia_salida_pt,1,'DO',2,'LU',3,'MA',4,'MI',5,'JU',6,'VI',7,'SA') AS dia_salida_pt,"
				+ "                     TO_CHAR(h_salida_pt,'HH24:MI') AS h_salida_pt" + "                 FROM"
				+ "                     rdm100.xxfc_pmo_route" + "                 WHERE"
				+ "                     status = 'A'" + "                     AND route IN ("
				+ "                         SELECT" + "                             b.route"
				+ "                         FROM" + "                             rdm100.carrier_service_route a,"
				+ "                             rdm100.route_dest b,"
				+ "                             rdm100.xxfc_store_v c," + "                             rdm100.route r,"
				+ "                             rdm100.xxfc_pmo_tdistrib_servicio ser"
				+ "                         WHERE" + "                             a.facility_id = b.facility_id"
				+ "                             AND a.facility_id = r.facility_id"
				+ "                             AND a.service_code = ser.servicio"
				+ "                             AND ser.servicio = DECODE('ALL','ALL',ser.servicio,substr('ALL',instr('ALL',ser.servicio"
				+ "                             ),length(ser.servicio) ) )"
				+ "                             AND ser.dest_type = c.dest_type"
				+ "                             AND b.dest_id = c.store"
				+ "                             AND a.route = r.route"
				+ "                             AND c.default_wh IN (" + "                                 SELECT"
				+ "                                     primary_vwh" + "                                 FROM"
				+ "                                     rdm100.xxfc_con_prog_wh_view"
				+ "                                 WHERE"
				+ "                                     primary_vwh IS NOT NULL" + "                             )"
				+ "                             AND substr(store_name10,1,5) ='" + plaza + "'"
				+ "                         GROUP BY" + "                             b.route"
				+ "                     )" + "                 ORDER BY" + "                     route ASC"
				+ "             )" + "         WHERE" + "             dia_ini_entrega ='" + diaEnt + "'" + "     )";
	}

	/**
	 * Query que muestra las columnas de la consulta queryBuscarPorPlazaDiaEnt
	 * tipo_distro, route, dia_gen_ped, dia_ini_entrega, h_ini_entrega
	 *
	 * @param plaza
	 * @param diaEnt
	 * @return
	 * @author JesusH4@hexaware.com
	 */
	public String getQueryTipoDistroDiaEnt(String plaza, String diaEnt) {
		return "SELECT " + "tipo_distro, " + "route, " + "dia_gen_ped, " + "dia_ini_entrega, " + "h_ini_entrega "
				+ "FROM (" + queryBuscarPorPlazaDiaEnt(plaza, diaEnt) + " )";
	}

	/**
	 * Query que muestra las columnas de la consulta queryBuscarPorPlazaDiaEnt
	 * h_fin_entrega, dia_prog, h_prog, dia_ini_prod, h_ini_prod
	 *
	 * @param plaza
	 * @param diaEnt
	 * @return
	 * @author JesusH4@hexaware.com
	 */
	public String getQueryFinEntregaDiaEnt(String plaza, String diaEnt) {
		return "SELECT " + "h_fin_entrega, " + "dia_prog, " + "h_prog, " + "dia_ini_prod, " + "h_ini_prod " + "FROM  ("
				+ queryBuscarPorPlazaDiaEnt(plaza, diaEnt) + " )";
	}

	/**
	 * Query que muestra las columnas de la consulta queryBuscarPorPlazaDiaEnt
	 * dia_fin_prod, h_fin_prod, dia_ini_verificacion, h_ini_verificacion,
	 * dia_fin_verificacion
	 *
	 * @param plaza
	 * @param diaEnt
	 * @return
	 * @author JesusH4@hexaware.com
	 */
	public String getQueryFinProdDiaEnt(String plaza, String diaEnt) {
		return "SELECT " + "dia_fin_prod, " + "h_fin_prod, " + "dia_ini_verificacion, " + "h_ini_verificacion, "
				+ "dia_fin_verificacion " + "FROM  (" + queryBuscarPorPlazaDiaEnt(plaza, diaEnt) + " )";
	}

	/**
	 * Query que muestra las columnas de la consulta queryBuscarPorPlazaDiaEnt
	 * h_fin_verificacion, dia_ini_carga, h_ini_carga, dia_fin_carga, h_fin_carga
	 *
	 * @param plaza
	 * @param diaEnt
	 * @return
	 * @author JesusH4@hexaware.com
	 */
	public String getQueryFinVerificacionDiaEnt(String plaza, String diaEnt) {
		return "SELECT " + "h_fin_verificacion, " + "dia_ini_carga, " + "h_ini_carga, " + "dia_fin_carga, "
				+ "h_fin_carga " + "FROM  (" + queryBuscarPorPlazaDiaEnt(plaza, diaEnt) + " )";
	}

	/**
	 * Query que muestra las columnas de la consulta queryBuscarPorPlazaDiaEnt
	 * dia_salida, h_salida, dia_disp_uni_cedis, h_disp_uni_cedis, dia_llegada_pt
	 *
	 * @param plaza
	 * @param diaEnt
	 * @return
	 * @author JesusH4@hexaware.com
	 */
	public String getQuerySalidaDiaEnt(String plaza, String diaEnt) {
		return "SELECT " + "dia_salida, " + "h_salida, " + "dia_disp_uni_cedis, " + "h_disp_uni_cedis, "
				+ "dia_llegada_pt " + "FROM  (" + queryBuscarPorPlazaDiaEnt(plaza, diaEnt) + " )";
	}

	/**
	 * Query que muestra las columnas de la consulta queryBuscarPorPlazaDiaEnt
	 * h_llegada_pt, dia_salida_pt, h_salida_pt
	 *
	 * @param plaza
	 * @param diaEnt
	 * @return
	 * @author JesusH4@hexaware.com
	 */
	public String getQueryLlegadaPTDiaEnt(String plaza, String diaEnt) {
		return "SELECT " + "h_llegada_pt, " + "dia_salida_pt, " + "h_salida_pt " + "FROM  ("
				+ queryBuscarPorPlazaDiaEnt(plaza, diaEnt) + " )";
	}

	/**
	 * Metodo usando para ejecutar el query de busqueda por plaza, segmentando las
	 * columnas para mejorar la visualizacion en la evidencia, agrega unicamente la
	 * evidencia de la tabla
	 *
	 * @param dbConecta conexion a base de datos
	 * @param plaza     plaza a buscar
	 * @param diaEnt    dia entrega a buscar
	 * @param tf        define si quieres incluir el SQL Statement junto con los
	 *                  resultados "true", "false" para incluir solo los resultados
	 * @throws SQLException
	 * @author JesusH4@hexaware.com
	 */
	public void cambiosMasivosPmoPlazaDiaEnt(SQLUtil dbConecta, String plaza, String diaEnt, Boolean tf)
			throws SQLException {

		SQLResult getQueryTipoDistroDiaEnt = executeQuery(dbConecta, getQueryTipoDistroDiaEnt(plaza, diaEnt));
		logger.info(String.valueOf(getQueryTipoDistroDiaEnt.isEmpty()));
		testCase.addQueryEvidenceCurrentStep(getQueryTipoDistroDiaEnt, tf);

		SQLResult getQueryFinEntregaDiaEnt = executeQuery(dbConecta, getQueryFinEntregaDiaEnt(plaza, diaEnt));
		logger.info(String.valueOf(getQueryFinEntregaDiaEnt.isEmpty()));
		testCase.addQueryEvidenceCurrentStep(getQueryFinEntregaDiaEnt, tf);

		SQLResult getQueryFinProdDiaEnt = executeQuery(dbConecta, getQueryFinProdDiaEnt(plaza, diaEnt));
		logger.info(String.valueOf(getQueryFinProdDiaEnt.isEmpty()));
		testCase.addQueryEvidenceCurrentStep(getQueryFinProdDiaEnt, tf);

		SQLResult getQueryFinVerificacionDiaEnt = executeQuery(dbConecta, getQueryFinVerificacionDiaEnt(plaza, diaEnt));
		logger.info(String.valueOf(getQueryFinVerificacionDiaEnt.isEmpty()));
		testCase.addQueryEvidenceCurrentStep(getQueryFinVerificacionDiaEnt, tf);

		SQLResult getQuerySalidaDiaEnt = executeQuery(dbConecta, getQuerySalidaDiaEnt(plaza, diaEnt));
		logger.info(String.valueOf(getQuerySalidaDiaEnt.isEmpty()));
		testCase.addQueryEvidenceCurrentStep(getQuerySalidaDiaEnt, tf);

		SQLResult getQueryLlegadaPTDiaEnt = executeQuery(dbConecta, getQueryLlegadaPTDiaEnt(plaza, diaEnt));
		logger.info(String.valueOf(getQueryLlegadaPTDiaEnt.isEmpty()));
		testCase.addQueryEvidenceCurrentStep(getQueryLlegadaPTDiaEnt, tf);
	}

	/**
	 * Query para buscar por plaza, ruta y dia de generacion, mostrando todas las
	 * columnas
	 * 
	 * @author JesusH4@hexaware.com
	 * @param plaza
	 * @param route
	 * @param diaGen
	 * @return
	 */
	public String queryBuscarPorPlazaRutaDiaGen(String plaza, String route, String diaGen) {
		return "SELECT * FROM (" + queryBuscarPorPlazaRuta(plaza, route) + " )  WHERE dia_gen_ped ='" + diaGen + "'";
	}

	/**
	 * Query que muestra las columnas de la consulta queryBuscarPorPlazaDiaEnt
	 * tipo_distro, route, dia_gen_ped, dia_ini_entrega, h_ini_entrega
	 *
	 * @param plaza
	 * @param ruta
	 * @return
	 * @author JesusH4@hexaware.com
	 */
	public String getQueryTipoDistroPlazaRuta(String plaza, String ruta) {
		return "SELECT " + "tipo_distro, " + "route, " + "dia_gen_ped, " + "dia_ini_entrega, " + "h_ini_entrega "
				+ "FROM (" + queryBuscarPorPlazaRuta(plaza, ruta) + " )";
	}

	/**
	 * Query que muestra las columnas de la consulta queryBuscarPorPlazaDiaEnt
	 * h_fin_entrega, dia_prog, h_prog, dia_ini_prod, h_ini_prod
	 *
	 * @param plaza
	 * @param ruta
	 * @return
	 * @author JesusH4@hexaware.com
	 */
	public String getQueryFinEntregaPlazaRuta(String plaza, String ruta) {
		return "SELECT " + "h_fin_entrega, " + "dia_prog, " + "h_prog, " + "dia_ini_prod, " + "h_ini_prod " + "FROM  ("
				+ queryBuscarPorPlazaRuta(plaza, ruta) + " )";
	}

	/**
	 * Query que muestra las columnas de la consulta queryBuscarPorPlazaDiaEnt
	 * dia_fin_prod, h_fin_prod, dia_ini_verificacion, h_ini_verificacion,
	 * dia_fin_verificacion
	 *
	 * @param plaza
	 * @param ruta
	 * @return
	 * @author JesusH4@hexaware.com
	 */
	public String getQueryFinProdPlazaRuta(String plaza, String ruta) {
		return "SELECT " + "dia_fin_prod, " + "h_fin_prod, " + "dia_ini_verificacion, " + "h_ini_verificacion, "
				+ "dia_fin_verificacion " + "FROM  (" + queryBuscarPorPlazaRuta(plaza, ruta) + " )";
	}

	/**
	 * Query que muestra las columnas de la consulta queryBuscarPorPlazaDiaEnt
	 * h_fin_verificacion, dia_ini_carga, h_ini_carga, dia_fin_carga, h_fin_carga
	 *
	 * @param plaza
	 * @param ruta
	 * @return
	 * @author JesusH4@hexaware.com
	 */
	public String getQueryFinVerificacionPlazaRuta(String plaza, String ruta) {
		return "SELECT " + "h_fin_verificacion, " + "dia_ini_carga, " + "h_ini_carga, " + "dia_fin_carga, "
				+ "h_fin_carga " + "FROM  (" + queryBuscarPorPlazaRuta(plaza, ruta) + " )";
	}

	/**
	 * Query que muestra las columnas de la consulta queryBuscarPorPlazaDiaEnt
	 * dia_salida, h_salida, dia_disp_uni_cedis, h_disp_uni_cedis, dia_llegada_pt
	 *
	 * @param plaza
	 * @param ruta
	 * @return
	 * @author JesusH4@hexaware.com
	 */
	public String getQuerySalidaPlazaRuta(String plaza, String ruta) {
		return "SELECT " + "dia_salida, " + "h_salida, " + "dia_disp_uni_cedis, " + "h_disp_uni_cedis, "
				+ "dia_llegada_pt " + "FROM  (" + queryBuscarPorPlazaRuta(plaza, ruta) + " )";
	}

	/**
	 * Query que muestra las columnas de la consulta queryBuscarPorPlazaDiaEnt
	 * h_llegada_pt, dia_salida_pt, h_salida_pt
	 *
	 * @param plaza
	 * @param ruta
	 * @return
	 * @author JesusH4@hexaware.com
	 */
	public String getQueryLlegadaPTPlazaRuta(String plaza, String ruta) {
		return "SELECT " + "h_llegada_pt, " + "dia_salida_pt, " + "h_salida_pt " + "FROM  ("
				+ queryBuscarPorPlazaRuta(plaza, ruta) + " )";
	}

	/**
	 * Metodo usando para ejecutar el query de busqueda por plaza, segmentando las
	 * columnas para mejorar la visualizacion en la evidencia, agrega unicamente la
	 * evidencia de la tabla
	 *
	 * @param dbConecta conexion a base de datos
	 * @param plaza     plaza a buscar
	 * @param ruta      ruta a buscar
	 * @param tf        define si quieres incluir el SQL Statement junto con los
	 *                  resultados "true", "false" para incluir solo los resultados
	 * @throws SQLException
	 * @author JesusH4@hexaware.com
	 */
	public void cambiosMasivosPmoPlazaPlazaRuta(SQLUtil dbConecta, String plaza, String ruta, Boolean tf)
			throws SQLException {

		SQLResult getQueryTipoDistroPlazaRuta = executeQuery(dbConecta, getQueryTipoDistroPlazaRuta(plaza, ruta));
		logger.info(String.valueOf(getQueryTipoDistroPlazaRuta.isEmpty()));
		testCase.addQueryEvidenceCurrentStep(getQueryTipoDistroPlazaRuta, tf);

		SQLResult getQueryFinEntregaPlazaRuta = executeQuery(dbConecta, getQueryFinEntregaPlazaRuta(plaza, ruta));
		logger.info(String.valueOf(getQueryFinEntregaPlazaRuta.isEmpty()));
		testCase.addQueryEvidenceCurrentStep(getQueryFinEntregaPlazaRuta, tf);

		SQLResult getQueryFinProdPlazaRuta = executeQuery(dbConecta, getQueryFinProdPlazaRuta(plaza, ruta));
		logger.info(String.valueOf(getQueryFinProdPlazaRuta.isEmpty()));
		testCase.addQueryEvidenceCurrentStep(getQueryFinProdPlazaRuta, tf);

		SQLResult getQueryFinVerificacionPlazaRuta = executeQuery(dbConecta,
				getQueryFinVerificacionPlazaRuta(plaza, ruta));
		logger.info(String.valueOf(getQueryFinVerificacionPlazaRuta.isEmpty()));
		testCase.addQueryEvidenceCurrentStep(getQueryFinVerificacionPlazaRuta, tf);

		SQLResult getQuerySalidaPlazaRuta = executeQuery(dbConecta, getQuerySalidaPlazaRuta(plaza, ruta));
		logger.info(String.valueOf(getQuerySalidaPlazaRuta.isEmpty()));
		testCase.addQueryEvidenceCurrentStep(getQuerySalidaPlazaRuta, tf);

		SQLResult getQueryLlegadaPTPlazaRuta = executeQuery(dbConecta, getQueryLlegadaPTPlazaRuta(plaza, ruta));
		logger.info(String.valueOf(getQueryLlegadaPTPlazaRuta.isEmpty()));
		testCase.addQueryEvidenceCurrentStep(getQueryLlegadaPTPlazaRuta, tf);
	}

	/**
	 * Query para buscar por plaza, ruta, dia de generacion y dia de ventana de
	 * entrega, mostrando todas las columnas
	 * 
	 * @author JesusH4@hexaware.com
	 * @param plaza
	 * @param route
	 * @param diaGen
	 * @param diaEnt
	 * @return
	 */
	public String queryBuscarPorPlazaRutaDiaGenEnt(String plaza, String route, String diaGen, String diaEnt) {
		return "SELECT * FROM (" + queryBuscarPorPlazaRutaDiaGen(plaza, route, diaGen) + " )  WHERE dia_ini_entrega ='"
				+ diaEnt + "'";
	}

	/**
	 * Query para buscar por plaza, ruta, dia de generacion, dia de ventana de
	 * entrega y tipo de distribucion mostrando todas las columnas
	 * 
	 * @author JesusH4@hexaware.com
	 * @param plaza
	 * @param route
	 * @param diaGen
	 * @param diaEnt
	 * @param tipoDistro
	 * @return
	 */
	public String queryBuscarPorPlazaRutaDiaGenEntTipoDistro(String plaza, String route, String diaGen, String diaEnt,
			String tipoDistro) {
		return "SELECT * FROM (" + queryBuscarPorPlazaRutaDiaGenEnt(plaza, route, diaGen, diaEnt)
				+ " )  WHERE tipo_distro ='" + tipoDistro + "'";
	}

	/**
	 * Query utilizado para buscar por ruta y dia ventana, en la tabla tienda
	 *
	 * @param ruta
	 * @param diaEnt
	 * @return
	 * @author JesusH4@hexaware.com
	 */
	public String queryPmoTiendaDiaEnt(String ruta, String diaEnt) {
		return "SELECT crplaza AS cr_plaza, " + "tipo_distro AS td, " + " route AS ruta, " + " dest_id AS tienda, "
				+ "DECODE(dia_gen_ped,1,'DO',2,'LU',3,'MA',4,'MI',5,'JU',6,'VI',7,'SA') AS dia_gen_ped, "
				+ "load_sequence AS load_seq, "
				+ "DECODE(dia_ini_vent_entrega,1,'DO',2,'LU',3,'MA',4,'MI',5,'JU',6,'VI',7,'SA') AS dia_ini_vent_entrega, "
				+ "TO_CHAR(h_ini_vent_entrega,'HH24:MI') AS h_ini, "
				+ "DECODE(dia_fin_vent_entrega,1,'DO',2,'LU',3,'MA',4,'MI',5,'JU',6,'VI',7,'SA') AS dia_fin_vent_entrega, "
				+ "TO_CHAR(h_fin_vent_entrega,'HH24:MI') AS h_fin, " + "lsp, "
				+ "DECODE(dia_est_entrega,1,'DO',2,'LU',3,'MA',4,'MI',5,'JU',6,'VI',7,'SA') AS dia_est_entrega, "
				+ "TO_CHAR(h_est_entrega,'HH24:MI') AS hora " + " FROM " + "rdm100.xxfc_pmo_tienda " + " WHERE "
				+ " route ='" + ruta + "' " + "AND dia_ini_vent_entrega ='" + diaEnt + "'" + " ORDER BY "
				+ "load_sequence ASC";
	}

	/**
	 * Query que muestra las columnas de la consulta queryPmoTienda cr_plaza, td,
	 * ruta, tienda, dia_gen_ped, load_seq
	 *
	 * @param ruta
	 * @param diaEnt
	 * @return
	 * @author JesusH4@hexaware.com
	 */
	public String getQueryPmoTiendaADiaEnt(String ruta, String diaEnt) {
		return "SELECT " + " cr_plaza, " + " td, " + " ruta, " + " tienda, " + " dia_gen_ped, " + " load_seq "
				+ " FROM  ( " + queryPmoTiendaDiaEnt(ruta, diaEnt) + " )";
	}

	/**
	 * Query que muestra las columnas de la consulta queryPmoTienda
	 * dia_ini_vent_entrega, h_ini, dia_fin_vent_entrega, h_fin, lsp,
	 * dia_est_entrega, hora
	 *
	 * @param ruta
	 * @param diaEnt
	 * @return
	 * @author JesusH4@hexaware.com
	 */
	public String getQueryPmoTiendaBDiaEnt(String ruta, String diaEnt) {
		return "SELECT " + " dia_ini_vent_entrega, " + " h_ini, " + " dia_fin_vent_entrega, " + " h_fin, " + " lsp, "
				+ " dia_est_entrega, " + " hora " + " FROM  ( " + queryPmoTiendaDiaEnt(ruta, diaEnt) + " )";
	}

	/**
	 * Metodo usando para ejecutar el query de busqueda por plaza, segmentando las
	 * columnas para mejorar la visualizacion en la evidencia, agrega unicamente la
	 * evidencia de la tabla
	 *
	 * @param dbConecta conexion a base de datos
	 * @param ruta
	 * @param diaGen
	 * @param tf        define si quieres incluir el SQL Statement junto con los
	 *                  resultados "true", "false" para incluir solo los resultados
	 * @throws SQLException
	 * @author JesusH4@hexaware.com
	 */
	public void cambiosMasivosPmoTiendaDiaEnt(SQLUtil dbConecta, String ruta, String diaEnt, Boolean tf)
			throws SQLException {

		SQLResult getQueryPmoTiendaADiaEnt = executeQuery(dbConecta, getQueryPmoTiendaADiaEnt(ruta, diaEnt));
		logger.info(String.valueOf(getQueryPmoTiendaADiaEnt.isEmpty()));
		testCase.addQueryEvidenceCurrentStep(getQueryPmoTiendaADiaEnt, tf);

		SQLResult getQueryPmoTiendaBDiaEnt = executeQuery(dbConecta, getQueryPmoTiendaBDiaEnt(ruta, diaEnt));
		logger.info(String.valueOf(getQueryPmoTiendaBDiaEnt.isEmpty()));
		testCase.addQueryEvidenceCurrentStep(getQueryPmoTiendaBDiaEnt, tf);

	}

	/**
	 * Query utilizado para buscar por ruta y dia generacion pedido, en la tabla
	 * tienda
	 *
	 * @param ruta
	 * @param diaGen
	 * @return
	 * @author JesusH4@hexaware.com
	 */
	public String queryPmoTienda(String ruta, String diaGen) {
		return "SELECT crplaza AS cr_plaza, " + "tipo_distro AS td, " + " route AS ruta, " + " dest_id AS tienda, "
				+ "DECODE(dia_gen_ped,1,'DO',2,'LU',3,'MA',4,'MI',5,'JU',6,'VI',7,'SA') AS dia_gen_ped, "
				+ "load_sequence AS load_seq, "
				+ "DECODE(dia_ini_vent_entrega,1,'DO',2,'LU',3,'MA',4,'MI',5,'JU',6,'VI',7,'SA') AS dia_ini_vent_entrega, "
				+ "TO_CHAR(h_ini_vent_entrega,'HH24:MI') AS h_ini, "
				+ "DECODE(dia_fin_vent_entrega,1,'DO',2,'LU',3,'MA',4,'MI',5,'JU',6,'VI',7,'SA') AS dia_fin_vent_entrega, "
				+ "TO_CHAR(h_fin_vent_entrega,'HH24:MI') AS h_fin, " + "lsp, "
				+ "DECODE(dia_est_entrega,1,'DO',2,'LU',3,'MA',4,'MI',5,'JU',6,'VI',7,'SA') AS dia_est_entrega, "
				+ "TO_CHAR(h_est_entrega,'HH24:MI') AS hora " + " FROM " + "rdm100.xxfc_pmo_tienda " + " WHERE "
				+ " route ='" + ruta + "' " + "AND dia_gen_ped ='" + diaGen + "'" + " ORDER BY " + "load_sequence ASC";
	}

	/**
	 * Query que muestra las columnas de la consulta queryPmoTienda cr_plaza, td,
	 * ruta, tienda, dia_gen_ped, load_seq
	 *
	 * @param ruta
	 * @param diaGen
	 * @return
	 * @author JesusH4@hexaware.com
	 */
	public String getQueryPmoTiendaA(String ruta, String diaGen) {
		return "SELECT " + " cr_plaza, " + " td, " + " ruta, " + " tienda, " + " dia_gen_ped, " + " load_seq "
				+ " FROM  ( " + queryPmoTienda(ruta, diaGen) + " )";
	}

	/**
	 * Query que muestra las columnas de la consulta queryPmoTienda
	 * dia_ini_vent_entrega, h_ini, dia_fin_vent_entrega, h_fin, lsp,
	 * dia_est_entrega, hora
	 *
	 * @param ruta
	 * @param diaGen
	 * @return
	 * @author JesusH4@hexaware.com
	 */
	public String getQueryPmoTiendaB(String ruta, String diaGen) {
		return "SELECT " + " dia_ini_vent_entrega, " + " h_ini, " + " dia_fin_vent_entrega, " + " h_fin, " + " lsp, "
				+ " dia_est_entrega, " + " hora " + " FROM  ( " + queryPmoTienda(ruta, diaGen) + " )";
	}

	/**
	 * Metodo usando para ejecutar el query de busqueda por plaza, segmentando las
	 * columnas para mejorar la visualizacion en la evidencia, agrega unicamente la
	 * evidencia de la tabla
	 *
	 * @param dbConecta conexion a base de datos
	 * @param ruta
	 * @param diaGen
	 * @param tf        define si quieres incluir el SQL Statement junto con los
	 *                  resultados "true", "false" para incluir solo los resultados
	 * @throws SQLException
	 * @author JesusH4@hexaware.com
	 */
	public void cambiosMasivosPmoTienda(SQLUtil dbConecta, String ruta, String diaGen, Boolean tf) throws SQLException {

		SQLResult getQueryPmoTiendaA = executeQuery(dbConecta, getQueryPmoTiendaA(ruta, diaGen));
		logger.info(String.valueOf(getQueryPmoTiendaA.isEmpty()));
		testCase.addQueryEvidenceCurrentStep(getQueryPmoTiendaA, tf);

		SQLResult getQueryPmoTiendaB = executeQuery(dbConecta, getQueryPmoTiendaB(ruta, diaGen));
		logger.info(String.valueOf(getQueryPmoTiendaB.isEmpty()));
		testCase.addQueryEvidenceCurrentStep(getQueryPmoTiendaB, tf);

	}

	/**
	 * Query que busca resultados por plaza
	 *
	 * @param plaza
	 * @return
	 * @author JesusH4@hexaware.com
	 */
	public String getPmoRouteDias(String plaza) {
		return "SELECT\r\n" + "     tipo_distro,\r\n" + "     route,\r\n"
				+ "     DECODE(dia_gen_ped,1,'DO',2,'LU',3,'MA',4,'MI',5,'JU',6,'VI',7,'SA') AS dia_gen_ped,\r\n"
				+ "     DECODE(dia_ini_entrega,1,'DO',2,'LU',3,'MA',4,'MI',5,'JU',6,'VI',7,'SA') AS dia_ini_entrega,\r\n"
				+ "     DECODE(dia_prog,1,'DO',2,'LU',3,'MA',4,'MI',5,'JU',6,'VI',7,'SA') AS dia_prog,\r\n"
				+ "     DECODE(dia_ini_prod,1,'DO',2,'LU',3,'MA',4,'MI',5,'JU',6,'VI',7,'SA') AS dia_ini_prod,\r\n"
				+ "     DECODE(dia_fin_prod,1,'DO',2,'LU',3,'MA',4,'MI',5,'JU',6,'VI',7,'SA') AS dia_fin_prod,\r\n"
				+ "     DECODE(dia_ini_verificacion,1,'DO',2,'LU',3,'MA',4,'MI',5,'JU',6,'VI',7,'SA') AS dia_ini_verificacion,\r\n"
				+ "     DECODE(dia_fin_verificacion,1,'DO',2,'LU',3,'MA',4,'MI',5,'JU',6,'VI',7,'SA') AS dia_fin_verificacion,\r\n"
				+ "     DECODE(dia_ini_carga,1,'DO',2,'LU',3,'MA',4,'MI',5,'JU',6,'VI',7,'SA') AS dia_ini_carga,\r\n"
				+ "     DECODE(dia_fin_carga,1,'DO',2,'LU',3,'MA',4,'MI',5,'JU',6,'VI',7,'SA') AS dia_fin_carga,\r\n"
				+ "     DECODE(dia_salida,1,'DO',2,'LU',3,'MA',4,'MI',5,'JU',6,'VI',7,'SA') AS dia_salida,\r\n"
				+ "     DECODE(dia_disp_uni_cedis,1,'DO',2,'LU',3,'MA',4,'MI',5,'JU',6,'VI',7,'SA') AS dia_disp_uni_cedis,\r\n"
				+ "     DECODE(dia_llegada_pt,1,'DO',2,'LU',3,'MA',4,'MI',5,'JU',6,'VI',7,'SA') AS dia_llegada_pt,\r\n"
				+ "     DECODE(dia_salida_pt,1,'DO',2,'LU',3,'MA',4,'MI',5,'JU',6,'VI',7,'SA') AS dia_salida_pt\r\n"
				+ " FROM\r\n" + "     rdm100.xxfc_pmo_route\r\n" + " WHERE\r\n" + "     status = 'A'\r\n"
				+ "     AND route IN (\r\n" + "         SELECT\r\n" + "             b.route\r\n" + "         FROM\r\n"
				+ "             rdm100.carrier_service_route a,\r\n" + "             rdm100.route_dest b,\r\n"
				+ "             rdm100.xxfc_store_v c,\r\n" + "             rdm100.route r,\r\n"
				+ "             rdm100.xxfc_pmo_tdistrib_servicio ser\r\n" + "         WHERE\r\n"
				+ "             a.facility_id = b.facility_id\r\n"
				+ "             AND a.facility_id = r.facility_id\r\n"
				+ "             AND a.service_code = ser.servicio\r\n"
				+ "             AND ser.servicio = DECODE('ALL','ALL',ser.servicio,substr('ALL',instr('ALL',ser.servicio),length(ser.servicio) ) )\r\n"
				+ "             AND ser.dest_type = c.dest_type\r\n" + "             AND b.dest_id = c.store\r\n"
				+ "             AND a.route = r.route\r\n" + "             AND c.default_wh IN (\r\n"
				+ "                 SELECT\r\n" + "                     primary_vwh\r\n" + "                 FROM\r\n"
				+ "                     rdm100.xxfc_con_prog_wh_view\r\n" + "                 WHERE\r\n"
				+ "                     primary_vwh IS NOT NULL\r\n" + "             )\r\n"
				+ "             AND substr(store_name10,1,5) = '" + plaza + "'\r\n" + "         GROUP BY\r\n"
				+ "             b.route\r\n" + "     )\r\n" + " ORDER BY\r\n" + "     route ASC";
	}

	/**
	 * Query filtrando por ruta
	 * 
	 * @param plaza
	 * @param route
	 * @return
	 * @author JesusH4@hexaware.com
	 */
	public String getPmoRouteDiasRoute(String plaza, String route) {
		return "SELECT " + " * " + " FROM  ( " + getPmoRouteDias(plaza) + " ) " + " WHERE\r\n" + "     route = '"
				+ route + "'";
	}

	/**
	 * Query filtrando por ruta, dia generacion
	 * 
	 * @param plaza
	 * @param route
	 * @param dia
	 * @return
	 * @author JesusH4@hexaware.com
	 */
	public String getPmoRouteDiasRoute(String plaza, String route, String dia) {
		return "SELECT " + " * " + " FROM  ( " + getPmoRouteDiasRoute(plaza, route) + " ) " + " WHERE\r\n"
				+ "     dia_gen_ped = '" + dia + "'";
	}

	/**
	 * Genera visualizacion de las tablas XXFC_PMO_ROUTE_LOAD y
	 * XXFC_PMO_CONFIG_RUTA_CICLO
	 * 
	 * @param dbConecta
	 * @param tf        define si quieres incluir el SQL Statement junto con los
	 *                  resultados "true", "false" para incluir solo los resultados
	 * @throws SQLException
	 */
	public void consultaPmoRouteLoads(SQLUtil dbConecta, Boolean tf) throws SQLException {

		SQLResult queryPmoConfigRutaCicloResult = executeQuery(dbConecta, queryPmoConfigRutaCiclo());
		logger.info(String.valueOf(queryPmoConfigRutaCicloResult.isEmpty()));
		testCase.addQueryEvidenceCurrentStep(queryPmoConfigRutaCicloResult, tf);

		SQLResult queryPmoRouteLoad03Result = executeQuery(dbConecta, queryPmoRouteLoad03());
		logger.info(String.valueOf(queryPmoRouteLoad03Result.isEmpty()));
		testCase.addQueryEvidenceCurrentStep(queryPmoRouteLoad03Result, tf);

		SQLResult queryPmoRouteLoad04Result = executeQuery(dbConecta, queryPmoRouteLoad04());
		logger.info(String.valueOf(queryPmoRouteLoad04Result.isEmpty()));
		testCase.addQueryEvidenceCurrentStep(queryPmoRouteLoad04Result, tf);
	}

	/**
	 * PARTE 1 Consulta a la tabla RDM100.XXFC.PMO.ROUTE
	 * 
	 * @param plaza
	 * @return
	 */
	public String getHu07PmoRoute01(String plaza) {
		return "SELECT\n" + "    tipo_distro,\n" + "    route,\n" + "    dia_gen_ped,\n" + "    dia_ini_entrega\n"
				+ "FROM\n" + "    rdm100.xxfc_pmo_route\n" + "WHERE\n" + "    status = 'A'\n" + "    AND route IN (\n"
				+ "        SELECT\n" + "            b.route\n" + "        FROM\n"
				+ "            rdm100.carrier_service_route a,\n" + "            rdm100.route_dest b,\n"
				+ "            rdm100.xxfc_store_v c,\n" + "            rdm100.route r,\n"
				+ "            rdm100.xxfc_pmo_tdistrib_servicio ser\n" + "        WHERE\n"
				+ "            a.facility_id = b.facility_id\n" + "            AND a.facility_id = r.facility_id\n"
				+ "            AND a.service_code = ser.servicio\n"
				+ "            AND ser.servicio = DECODE('ALL','ALL',ser.servicio,substr('ALL',instr('ALL',ser.servicio),length(ser.servicio\n"
				+ "            ) ) )\n" + "            AND ser.dest_type = c.dest_type\n"
				+ "            AND b.dest_id = c.store\n" + "            AND a.route = r.route\n"
				+ "            AND c.default_wh IN (\n" + "                SELECT\n"
				+ "                    primary_vwh\n" + "                FROM\n"
				+ "                    rdm100.xxfc_con_prog_wh_view\n" + "                WHERE\n"
				+ "                    primary_vwh IS NOT NULL\n" + "            )\n"
				+ "            AND substr(store_name10,1,5) =" + "'" + plaza + "' " + "        GROUP BY\n"
				+ "            b.route\n" + "    )\n" + "ORDER BY\n" + "    route ASC";
	}

	/**
	 * PARTE 2 Consulta a la tabla RDM100.XXFC.PMO.ROUTE
	 * 
	 * @param plaza
	 * @return
	 */
	public String getHu07PmoRoute02(String plaza) {
		return "SELECT\n" + "    TO_CHAR(h_ini_entrega,'HH24:MI:SS') AS h_ini_entrega,\n"
				+ "    TO_CHAR(h_fin_entrega,'HH24:MI:SS') AS h_fin_entrega,\n" + "    dia_prog,\n"
				+ "    TO_CHAR(h_prog,'HH24:MI:SS') AS h_prog\n" + "FROM\n" + "    rdm100.xxfc_pmo_route\n" + "WHERE\n"
				+ "    status = 'A'\n" + "    AND route IN (\n" + "        SELECT\n" + "            b.route\n"
				+ "        FROM\n" + "            rdm100.carrier_service_route a,\n"
				+ "            rdm100.route_dest b,\n" + "            rdm100.xxfc_store_v c,\n"
				+ "            rdm100.route r,\n" + "            rdm100.xxfc_pmo_tdistrib_servicio ser\n"
				+ "        WHERE\n" + "            a.facility_id = b.facility_id\n"
				+ "            AND a.facility_id = r.facility_id\n" + "            AND a.service_code = ser.servicio\n"
				+ "            AND ser.servicio = DECODE('ALL','ALL',ser.servicio,substr('ALL',instr('ALL',ser.servicio),length(ser.servicio\n"
				+ "            ) ) )\n" + "            AND ser.dest_type = c.dest_type\n"
				+ "            AND b.dest_id = c.store\n" + "            AND a.route = r.route\n"
				+ "            AND c.default_wh IN (\n" + "                SELECT\n"
				+ "                    primary_vwh\n" + "                FROM\n"
				+ "                    rdm100.xxfc_con_prog_wh_view\n" + "                WHERE\n"
				+ "                    primary_vwh IS NOT NULL\n" + "            )\n"
				+ "            AND substr(store_name10,1,5) =" + "'" + plaza + "' " + "        GROUP BY\n"
				+ "            b.route\n" + "    )\n" + "ORDER BY\n" + "    route ASC";
	}

	/**
	 * PARTE 3 Consulta a la tabla RDM100.XXFC.PMO.ROUTE
	 * 
	 * @param plaza
	 * @return
	 */
	public String getHu07PmoRoute03(String plaza) {
		return "SELECT\n" + "    dia_ini_prod,\n" + "    TO_CHAR(h_ini_prod,'HH24:MI:SS') AS h_ini_prod,\n"
				+ "    dia_fin_prod,\n" + "    TO_CHAR(h_fin_prod,'HH24:MI:SS') AS h_fin_prod\n" + "FROM\n"
				+ "    rdm100.xxfc_pmo_route\n" + "WHERE\n" + "    status = 'A'\n" + "    AND route IN (\n"
				+ "        SELECT\n" + "            b.route\n" + "        FROM\n"
				+ "            rdm100.carrier_service_route a,\n" + "            rdm100.route_dest b,\n"
				+ "            rdm100.xxfc_store_v c,\n" + "            rdm100.route r,\n"
				+ "            rdm100.xxfc_pmo_tdistrib_servicio ser\n" + "        WHERE\n"
				+ "            a.facility_id = b.facility_id\n" + "            AND a.facility_id = r.facility_id\n"
				+ "            AND a.service_code = ser.servicio\n"
				+ "            AND ser.servicio = DECODE('ALL','ALL',ser.servicio,substr('ALL',instr('ALL',ser.servicio),length(ser.servicio\n"
				+ "            ) ) )\n" + "            AND ser.dest_type = c.dest_type\n"
				+ "            AND b.dest_id = c.store\n" + "            AND a.route = r.route\n"
				+ "            AND c.default_wh IN (\n" + "                SELECT\n"
				+ "                    primary_vwh\n" + "                FROM\n"
				+ "                    rdm100.xxfc_con_prog_wh_view\n" + "                WHERE\n"
				+ "                    primary_vwh IS NOT NULL\n" + "            )\n"
				+ "            AND substr(store_name10,1,5) =" + "'" + plaza + "' " + "        GROUP BY\n"
				+ "            b.route\n" + "    )\n" + "ORDER BY\n" + "    route ASC";
	}

	/**
	 * PARTE 4 Consulta a la tabla RDM100.XXFC.PMO.ROUTE
	 * 
	 * @param plaza
	 * @return
	 */
	public String getHu07PmoRoute04(String plaza) {
		return "SELECT\n" + "    dia_ini_verificacion,\n"
				+ "    TO_CHAR(h_ini_verificacion,'HH24:MI:SS') AS h_ini_verificacion,\n"
				+ "    dia_fin_verificacion,\n" + "    TO_CHAR(h_fin_verificacion,'HH24:MI:SS') AS h_fin_verificacion\n"
				+ "FROM\n" + "    rdm100.xxfc_pmo_route\n" + "WHERE\n" + "    status = 'A'\n" + "    AND route IN (\n"
				+ "        SELECT\n" + "            b.route\n" + "        FROM\n"
				+ "            rdm100.carrier_service_route a,\n" + "            rdm100.route_dest b,\n"
				+ "            rdm100.xxfc_store_v c,\n" + "            rdm100.route r,\n"
				+ "            rdm100.xxfc_pmo_tdistrib_servicio ser\n" + "        WHERE\n"
				+ "            a.facility_id = b.facility_id\n" + "            AND a.facility_id = r.facility_id\n"
				+ "            AND a.service_code = ser.servicio\n"
				+ "            AND ser.servicio = DECODE('ALL','ALL',ser.servicio,substr('ALL',instr('ALL',ser.servicio),length(ser.servicio\n"
				+ "            ) ) )\n" + "            AND ser.dest_type = c.dest_type\n"
				+ "            AND b.dest_id = c.store\n" + "            AND a.route = r.route\n"
				+ "            AND c.default_wh IN (\n" + "                SELECT\n"
				+ "                    primary_vwh\n" + "                FROM\n"
				+ "                    rdm100.xxfc_con_prog_wh_view\n" + "                WHERE\n"
				+ "                    primary_vwh IS NOT NULL\n" + "            )\n"
				+ "            AND substr(store_name10,1,5) =" + "'" + plaza + "' " + "        GROUP BY\n"
				+ "            b.route\n" + "    )\n" + "ORDER BY\n" + "    route ASC";
	}

	/**
	 * PARTE 5 Consulta a la tabla RDM100.XXFC.PMO.ROUTE
	 * 
	 * @param plaza
	 * @return
	 */
	public String getHu07PmoRoute05(String plaza) {
		return "SELECT\n" + "    dia_ini_carga,\n" + "    TO_CHAR(h_ini_carga,'HH24:MI:SS') AS h_ini_carga,\n"
				+ "    dia_fin_carga,\n" + "    TO_CHAR(h_fin_carga,'HH24:MI:SS') AS h_fin_carga\n" + "FROM\n"
				+ "    rdm100.xxfc_pmo_route\n" + "WHERE\n" + "    status = 'A'\n" + "    AND route IN (\n"
				+ "        SELECT\n" + "            b.route\n" + "        FROM\n"
				+ "            rdm100.carrier_service_route a,\n" + "            rdm100.route_dest b,\n"
				+ "            rdm100.xxfc_store_v c,\n" + "            rdm100.route r,\n"
				+ "            rdm100.xxfc_pmo_tdistrib_servicio ser\n" + "        WHERE\n"
				+ "            a.facility_id = b.facility_id\n" + "            AND a.facility_id = r.facility_id\n"
				+ "            AND a.service_code = ser.servicio\n"
				+ "            AND ser.servicio = DECODE('ALL','ALL',ser.servicio,substr('ALL',instr('ALL',ser.servicio),length(ser.servicio\n"
				+ "            ) ) )\n" + "            AND ser.dest_type = c.dest_type\n"
				+ "            AND b.dest_id = c.store\n" + "            AND a.route = r.route\n"
				+ "            AND c.default_wh IN (\n" + "                SELECT\n"
				+ "                    primary_vwh\n" + "                FROM\n"
				+ "                    rdm100.xxfc_con_prog_wh_view\n" + "                WHERE\n"
				+ "                    primary_vwh IS NOT NULL\n" + "            )\n"
				+ "            AND substr(store_name10,1,5) =" + "'" + plaza + "' " + "        GROUP BY\n"
				+ "            b.route\n" + "    )\n" + "ORDER BY\n" + "    route ASC";
	}

	/**
	 * PARTE 6 Consulta a la tabla RDM100.XXFC.PMO.ROUTE
	 * 
	 * @param plaza
	 * @return
	 */
	public String getHu07PmoRoute06(String plaza) {
		return "SELECT\n" + "    dia_salida,\n" + "    TO_CHAR(h_salida,'HH24:MI:SS') AS h_salida,\n"
				+ "    dia_disp_uni_cedis,\n" + "    TO_CHAR(h_disp_uni_cedis,'HH24:MI:SS') AS h_disp_uni_cedis\n"
				+ "FROM\n" + "    rdm100.xxfc_pmo_route\n" + "WHERE\n" + "    status = 'A'\n" + "    AND route IN (\n"
				+ "        SELECT\n" + "            b.route\n" + "        FROM\n"
				+ "            rdm100.carrier_service_route a,\n" + "            rdm100.route_dest b,\n"
				+ "            rdm100.xxfc_store_v c,\n" + "            rdm100.route r,\n"
				+ "            rdm100.xxfc_pmo_tdistrib_servicio ser\n" + "        WHERE\n"
				+ "            a.facility_id = b.facility_id\n" + "            AND a.facility_id = r.facility_id\n"
				+ "            AND a.service_code = ser.servicio\n"
				+ "            AND ser.servicio = DECODE('ALL','ALL',ser.servicio,substr('ALL',instr('ALL',ser.servicio),length(ser.servicio\n"
				+ "            ) ) )\n" + "            AND ser.dest_type = c.dest_type\n"
				+ "            AND b.dest_id = c.store\n" + "            AND a.route = r.route\n"
				+ "            AND c.default_wh IN (\n" + "                SELECT\n"
				+ "                    primary_vwh\n" + "                FROM\n"
				+ "                    rdm100.xxfc_con_prog_wh_view\n" + "                WHERE\n"
				+ "                    primary_vwh IS NOT NULL\n" + "            )\n"
				+ "            AND substr(store_name10,1,5) =" + "'" + plaza + "' " + "        GROUP BY\n"
				+ "            b.route\n" + "    )\n" + "ORDER BY\n" + "    route ASC";
	}

	/**
	 * PARTE 7 Consulta a la tabla RDM100.XXFC.PMO.ROUTE
	 * 
	 * @param plaza
	 * @return
	 */
	public String getHu07PmoRoute07(String plaza) {
		return "SELECT\n" + "    dia_salida_pt,\n" + "    TO_CHAR(h_salida_pt,'HH24:MI:SS') AS h_salida_pt\n" + "FROM\n"
				+ "    rdm100.xxfc_pmo_route\n" + "WHERE\n" + "    status = 'A'\n" + "    AND route IN (\n"
				+ "        SELECT\n" + "            b.route\n" + "        FROM\n"
				+ "            rdm100.carrier_service_route a,\n" + "            rdm100.route_dest b,\n"
				+ "            rdm100.xxfc_store_v c,\n" + "            rdm100.route r,\n"
				+ "            rdm100.xxfc_pmo_tdistrib_servicio ser\n" + "        WHERE\n"
				+ "            a.facility_id = b.facility_id\n" + "            AND a.facility_id = r.facility_id\n"
				+ "            AND a.service_code = ser.servicio\n"
				+ "            AND ser.servicio = DECODE('ALL','ALL',ser.servicio,substr('ALL',instr('ALL',ser.servicio),length(ser.servicio\n"
				+ "            ) ) )\n" + "            AND ser.dest_type = c.dest_type\n"
				+ "            AND b.dest_id = c.store\n" + "            AND a.route = r.route\n"
				+ "            AND c.default_wh IN (\n" + "                SELECT\n"
				+ "                    primary_vwh\n" + "                FROM\n"
				+ "                    rdm100.xxfc_con_prog_wh_view\n" + "                WHERE\n"
				+ "                    primary_vwh IS NOT NULL\n" + "            )\n"
				+ "            AND substr(store_name10,1,5) =" + "'" + plaza + "' " + "        GROUP BY\n"
				+ "            b.route\n" + "    )\n" + "ORDER BY\n" + "    route ASC";
	}

	/**
	 * consultaPmoRouteHu07 - Resultados del query en partes
	 * 
	 * @param dbConecta
	 * @param plaza
	 * @param tf
	 * @throws SQLException
	 */
	public void consultaPmoRouteHu07(SQLUtil dbConecta, String plaza, Boolean tf) throws SQLException {

		SQLResult getHu07PmoRoute01Result = executeQuery(dbConecta, getHu07PmoRoute01(plaza));
		logger.info(String.valueOf(getHu07PmoRoute01Result.isEmpty()));
		testCase.addQueryEvidenceCurrentStep(getHu07PmoRoute01Result, tf);

		SQLResult getHu07PmoRoute02Result = executeQuery(dbConecta, getHu07PmoRoute02(plaza));
		logger.info(String.valueOf(getHu07PmoRoute02Result.isEmpty()));
		testCase.addQueryEvidenceCurrentStep(getHu07PmoRoute02Result, tf);

		SQLResult getHu07PmoRoute03Result = executeQuery(dbConecta, getHu07PmoRoute03(plaza));
		logger.info(String.valueOf(getHu07PmoRoute03Result.isEmpty()));
		testCase.addQueryEvidenceCurrentStep(getHu07PmoRoute03Result, tf);

		SQLResult getHu07PmoRoute04Result = executeQuery(dbConecta, getHu07PmoRoute04(plaza));
		logger.info(String.valueOf(getHu07PmoRoute04Result.isEmpty()));
		testCase.addQueryEvidenceCurrentStep(getHu07PmoRoute04Result, tf);

		SQLResult getHu07PmoRoute05Result = executeQuery(dbConecta, getHu07PmoRoute05(plaza));
		logger.info(String.valueOf(getHu07PmoRoute05Result.isEmpty()));
		testCase.addQueryEvidenceCurrentStep(getHu07PmoRoute05Result, tf);

		SQLResult getHu07PmoRoute06Result = executeQuery(dbConecta, getHu07PmoRoute06(plaza));
		logger.info(String.valueOf(getHu07PmoRoute06Result.isEmpty()));
		testCase.addQueryEvidenceCurrentStep(getHu07PmoRoute06Result, tf);
	}

}
