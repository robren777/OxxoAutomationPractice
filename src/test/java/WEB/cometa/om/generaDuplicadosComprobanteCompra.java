package WEB.cometa.om;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import WEB.util.GlobalVariables;
import modelo.TestCase;

public class generaDuplicadosComprobanteCompra {

	private final TestCase testCase;

	/**
	 * Inicializa la utilidad de preparacion de datos para duplicados de Comprobantes de Compra.
	 * La clase solo consulta BD y registra evidencia del estado encontrado antes de generar duplicados.
	 *
	 * @param testCase caso de prueba usado para registrar evidencia.
	 *
	 * @author Michelle Ceja
	 * @date 2026-07-27
	 */
	public generaDuplicadosComprobanteCompra(TestCase testCase) {
		this.testCase = testCase;
	}

	public static class AlcanceUsuarioComprobanteCompra {
		public final String usuario;
		public final String perfil;
		public final String tipoFiltro;
		public final String valorFiltro;
		public final boolean consultaGlobal;

		/**
		 * Conserva el alcance de datos configurado para el usuario de Comprobantes de Compra.
		 * El alcance define si la consulta se ejecuta global o filtrada por plaza, RFC o supplierId.
		 *
		 * @param usuario usuario de Portal COMETA.
		 * @param perfil perfil obtenido desde PCPERFIL.
		 * @param tipoFiltro llave aplicada: GLOBAL, PCPLAZA, PCRFC o PCSUPPLIERID.
		 * @param valorFiltro valor usado para filtrar registros cuando aplica.
		 * @param consultaGlobal true cuando el usuario ve todos los registros.
		 *
		 * @author Michelle Ceja
		 * @date 2026-07-27
		 */
		public AlcanceUsuarioComprobanteCompra(String usuario, String perfil, String tipoFiltro, String valorFiltro, boolean consultaGlobal) {
			this.usuario = usuario;
			this.perfil = perfil;
			this.tipoFiltro = tipoFiltro;
			this.valorFiltro = valorFiltro;
			this.consultaGlobal = consultaGlobal;
		}
	}

	public static class ResultadoValidacionDuplicadosComprobanteCompra {
		public final AlcanceUsuarioComprobanteCompra alcanceUsuario;
		public final long totalRegistros;
		public final long idComprobanteReferencia;
		public final long idHistoricoReferencia;
		public final Timestamp fechaRegistroReferencia;
		public final int minimoRegistros;
		public final boolean cumpleMinimo;
		public final long registrosFaltantes;
		public final List<Long> idsDuplicadosGenerados;

		/**
		 * Conserva el resultado de validar si el alcance del usuario ya cuenta con registros suficientes.
		 * Incluye el registro mas reciente que se usara como referencia cuando se implemente la duplicacion.
		 *
		 * @param alcanceUsuario alcance de consulta resuelto para el usuario.
		 * @param totalRegistros total actual de registros para ese alcance.
		 * @param idComprobanteReferencia id mas reciente de xxcm_cc dentro del alcance.
		 * @param idHistoricoReferencia id mas reciente de xxcm_historico_correos asociado al comprobante.
		 * @param fechaRegistroReferencia fecha_registro del registro mas reciente.
		 * @param minimoRegistros minimo requerido para la prueba de performance.
		 * @param cumpleMinimo true si el total actual es mayor o igual al minimo requerido.
		 * @param registrosFaltantes cantidad de registros que faltan para llegar al minimo.
		 * @param idsDuplicadosGenerados lista de IDs insertados durante duplicacion. Vacio si no hubo duplicacion.
		 *
		 * @author Michelle Ceja
		 * @date 2026-07-27
		 */
		public ResultadoValidacionDuplicadosComprobanteCompra(AlcanceUsuarioComprobanteCompra alcanceUsuario,
				long totalRegistros, long idComprobanteReferencia, long idHistoricoReferencia,
				Timestamp fechaRegistroReferencia, int minimoRegistros, boolean cumpleMinimo, long registrosFaltantes,
				List<Long> idsDuplicadosGenerados) {
			this.alcanceUsuario = alcanceUsuario;
			this.totalRegistros = totalRegistros;
			this.idComprobanteReferencia = idComprobanteReferencia;
			this.idHistoricoReferencia = idHistoricoReferencia;
			this.fechaRegistroReferencia = fechaRegistroReferencia;
			this.minimoRegistros = minimoRegistros;
			this.cumpleMinimo = cumpleMinimo;
			this.registrosFaltantes = registrosFaltantes;
			this.idsDuplicadosGenerados = idsDuplicadosGenerados;
		}
	}

	/**
	 * Valida si el usuario cuenta con el minimo de registros de Comprobantes de Compra para pruebas de performance.
	 * Resuelve primero el alcance del usuario y localiza el registro mas reciente que podria duplicarse si faltan datos.
	 *
	 * @param usuario usuario de Portal COMETA que se validara en BD.
	 * @param minimoRegistros minimo requerido para ejecutar la prueba de descarga.
	 * @return resultado con alcance, total de registros y registro de referencia para duplicado futuro.
	 * @throws Exception si no se puede resolver el alcance o no existe registro de referencia.
	 *
	 * @author Michelle Ceja
	 * @date 2026-07-27
	 */
	public ResultadoValidacionDuplicadosComprobanteCompra validarMinimoRegistrosPorUsuario(String usuario, int minimoRegistros) throws Exception {
		try (Connection connection = abrirConexionCometa()) {
			AlcanceUsuarioComprobanteCompra alcanceUsuario = obtenerAlcanceUsuario(connection, usuario);
			long totalRegistros = obtenerTotalRegistrosPorAlcance(connection, alcanceUsuario);
			ResultadoValidacionDuplicadosComprobanteCompra resultado = obtenerRegistroMasRecientePorAlcance(
					connection, alcanceUsuario, totalRegistros, minimoRegistros);

			registrarResumenValidacion(resultado);
			return resultado;
		} catch (Exception exception) {
			testCase.addCodeEvidenceCurrentStep("Error al validar registros para duplicados de Comprobantes de Compra: " + exception.getMessage());
			throw new Exception("Error al validar registros para duplicados de Comprobantes de Compra: " + exception.getMessage());
		}
	}

	/**
	 * Abre la conexion a la base de datos de Portal COMETA usando GlobalVariables.
	 * Centraliza el origen de credenciales para mantener consistencia con el framework.
	 *
	 * @return conexion activa hacia BD COMETA.
	 * @throws Exception si no es posible abrir la conexion.
	 *
	 * @author Michelle Ceja
	 * @date 2026-07-27
	 */
	private Connection abrirConexionCometa() throws Exception {
		return DriverManager.getConnection(GlobalVariables.DB_URL_Cometa,
				GlobalVariables.DB_USER_Cometa, GlobalVariables.DB_PASSWORD_Cometa);
	}

	/**
	 * Obtiene el perfil y las llaves de alcance configuradas para el usuario.
	 * ADMIN y OXXO_OS se consideran globales; OXXO_PLAZA filtra por PCPLAZA; los demas priorizan PCRFC y despues PCSUPPLIERID.
	 *
	 * @param connection conexion activa a BD COMETA.
	 * @param usuario usuario de Portal COMETA.
	 * @return alcance resuelto para construir las consultas de conteo y registro referencia.
	 * @throws Exception si el usuario no tiene perfil o no tiene llave de alcance cuando la requiere.
	 *
	 * @author Michelle Ceja
	 * @date 2026-07-27
	 */
	private AlcanceUsuarioComprobanteCompra obtenerAlcanceUsuario(Connection connection, String usuario) throws Exception {
		String sqlPerfil = "select key_value "
				+ "from xxcm_user_keys "
				+ "where short_name = 'PCPERFIL' "
				+ "and usuario_id = (select usuario_id from xxcm_usuarios where usuario = ?) "
				+ "limit 1";
		String sqlKeys = "select short_name, key_value "
				+ "from xxcm_user_keys "
				+ "where short_name in ('PCRFC', 'PCSUPPLIERID', 'PCPLAZA') "
				+ "and usuario_id = (select usuario_id from xxcm_usuarios where usuario = ?)";

		String perfil = "";
		try (PreparedStatement statementPerfil = connection.prepareStatement(sqlPerfil)) {
			statementPerfil.setString(1, usuario);
			try (ResultSet resultSetPerfil = statementPerfil.executeQuery()) {
				if (resultSetPerfil.next()) {
					perfil = limpiar(resultSetPerfil.getString("key_value"));
				}
			}
		}

		if (perfil.isEmpty()) {
			throw new Exception("No se obtuvo PCPERFIL para el usuario " + usuario);
		}

		String pcrfc = "";
		String pcSupplierId = "";
		String pcPlaza = "";
		try (PreparedStatement statementKeys = connection.prepareStatement(sqlKeys)) {
			statementKeys.setString(1, usuario);
			try (ResultSet resultSetKeys = statementKeys.executeQuery()) {
				while (resultSetKeys.next()) {
					String shortName = limpiar(resultSetKeys.getString("short_name"));
					String keyValue = limpiar(resultSetKeys.getString("key_value"));
					if ("PCRFC".equalsIgnoreCase(shortName)) {
						pcrfc = keyValue;
					} else if ("PCSUPPLIERID".equalsIgnoreCase(shortName)) {
						pcSupplierId = keyValue;
					} else if ("PCPLAZA".equalsIgnoreCase(shortName)) {
						pcPlaza = keyValue;
					}
				}
			}
		}

		String perfilNormalizado = perfil.toUpperCase(Locale.ROOT);
		if ("ADMIN".equals(perfilNormalizado) || "OXXO_OS".equals(perfilNormalizado)
				|| "5555555".equals(usuario) || "3333333".equals(usuario)) {
			return new AlcanceUsuarioComprobanteCompra(usuario, perfil, "GLOBAL", "SIN FILTRO", true);
		}

		if ("OXXO_PLAZA".equals(perfilNormalizado)) {
			if (pcPlaza.isEmpty()) {
				throw new Exception("El usuario " + usuario + " tiene perfil OXXO_PLAZA pero no tiene PCPLAZA configurado");
			}
			return new AlcanceUsuarioComprobanteCompra(usuario, perfil, "PCPLAZA", pcPlaza, false);
		}

		if (!pcrfc.isEmpty()) {
			return new AlcanceUsuarioComprobanteCompra(usuario, perfil, "PCRFC", pcrfc, false);
		}

		if (!pcSupplierId.isEmpty()) {
			return new AlcanceUsuarioComprobanteCompra(usuario, perfil, "PCSUPPLIERID", pcSupplierId, false);
		}

		throw new Exception("No se obtuvo PCRFC, PCSUPPLIERID o PCPLAZA para el usuario " + usuario);
	}

	/**
	 * Cuenta los registros de Comprobantes de Compra visibles para el alcance del usuario.
	 * Usa el CTE base de Comprobantes para que el conteo sea equivalente al grid del portal.
	 *
	 * @param connection conexion activa a BD COMETA.
	 * @param alcanceUsuario alcance resuelto para el usuario.
	 * @return total de registros existentes para el alcance.
	 * @throws Exception si ocurre un error al ejecutar el conteo.
	 *
	 * @author Michelle Ceja
	 * @date 2026-07-27
	 */
	private long obtenerTotalRegistrosPorAlcance(Connection connection, AlcanceUsuarioComprobanteCompra alcanceUsuario) throws Exception {
		String sql = obtenerCteComprobantesCompra(alcanceUsuario)
				+ "select count(*) as total_registros from cte_data";

		try (PreparedStatement statement = connection.prepareStatement(sql)) {
			asignarParametroAlcance(statement, alcanceUsuario);
			try (ResultSet resultSet = statement.executeQuery()) {
				return resultSet.next() ? resultSet.getLong("total_registros") : 0L;
			}
		}
	}

	/**
	 * Obtiene el registro mas reciente de Comprobantes de Compra para el alcance del usuario.
	 * El registro obtenido sera la referencia para la etapa posterior de duplicado cuando no se cumpla el minimo.
	 *
	 * @param connection conexion activa a BD COMETA.
	 * @param alcanceUsuario alcance resuelto para el usuario.
	 * @param totalRegistros total actual calculado para el alcance.
	 * @param minimoRegistros minimo requerido para la prueba de performance.
	 * @return resultado con el registro mas reciente y el estado contra el minimo.
	 * @throws Exception si no existe un registro de referencia para el alcance.
	 *
	 * @author Michelle Ceja
	 * @date 2026-07-27
	 */
	private ResultadoValidacionDuplicadosComprobanteCompra obtenerRegistroMasRecientePorAlcance(Connection connection,
			AlcanceUsuarioComprobanteCompra alcanceUsuario, long totalRegistros, int minimoRegistros) throws Exception {
		String sql = obtenerCteComprobantesCompra(alcanceUsuario)
				+ "select id, historico_id, fecha_registro "
				+ "from cte_data "
				+ "order by historico_id desc "
				+ "limit 1";

		try (PreparedStatement statement = connection.prepareStatement(sql)) {
			asignarParametroAlcance(statement, alcanceUsuario);
			try (ResultSet resultSet = statement.executeQuery()) {
				if (!resultSet.next()) {
					throw new Exception("No existe registro de referencia en Comprobantes de Compra para el usuario " + alcanceUsuario.usuario);
				}

				boolean cumpleMinimo = totalRegistros >= minimoRegistros;
				long registrosFaltantes = cumpleMinimo ? 0L : minimoRegistros - totalRegistros;
				return new ResultadoValidacionDuplicadosComprobanteCompra(
						alcanceUsuario,
						totalRegistros,
						resultSet.getLong("id"),
						resultSet.getLong("historico_id"),
						resultSet.getTimestamp("fecha_registro"),
						minimoRegistros,
						cumpleMinimo,
						registrosFaltantes,
						new ArrayList<>());
			}
		}
	}

	/**
	 * Construye el CTE base de Comprobantes de Compra con el filtro del alcance del usuario.
	 * El CTE incluye las columnas necesarias para contar registros e identificar el registro mas reciente.
	 *
	 * @param alcanceUsuario alcance resuelto para el usuario.
	 * @return SQL del CTE cte_data listo para concatenar la consulta final.
	 *
	 * @author Michelle Ceja
	 * @date 2026-07-27
	 */
	private String obtenerCteComprobantesCompra(AlcanceUsuarioComprobanteCompra alcanceUsuario) {
		String whereAlcance = "";
		if (!alcanceUsuario.consultaGlobal) {
			if ("PCPLAZA".equals(alcanceUsuario.tipoFiltro)) {
				whereAlcance = "where ((p.cr_plaza::text || ' '::text) || p.desc_plaza::text) = ? ";
			} else if ("PCRFC".equals(alcanceUsuario.tipoFiltro)) {
				whereAlcance = "where c.proveedor_rfc = ? ";
			} else if ("PCSUPPLIERID".equals(alcanceUsuario.tipoFiltro)) {
				whereAlcance = "where c.proveedor_id::text = ? ";
			}
		}

		return "WITH cte_data AS( "
				+ "select "
				+ "c.id, "
				+ "h.id as historico_id, "
				+ "c.transferencia, "
				+ "h.body, h.estatus, "
				+ "c.id_retek, "
				+ "h.id_retek_assigned_at, "
				+ "c.cr_tienda, "
				+ "p.desc_tienda, "
				+ "(p.cr_plaza::text || ' '::text) || p.desc_plaza::text AS cr_desc_plaza, "
				+ "c.tienda, "
				+ "c.cr_plaza, "
				+ "p.desc_plaza, "
				+ "c.plaza, "
				+ "c.region, "
				+ "c.zona, "
				+ "c.proveedor_rfc rfc, "
				+ "c.proveedor_id supplier, "
				+ "c.proveedor supplier_name, "
				+ "c.fuente_de_abasto, "
				+ "h.fecha_registro, "
				+ "h.updated_at "
				+ "from xxcm_cc c "
				+ "inner join xxcm_historico_correos h on h.id = c.id "
				+ "inner join xxcm_region_plaza_tienda_job_sync p on p.id_retek = c.id_retek and h.id_retek = p.id_retek "
				+ whereAlcance
				+ ") ";
	}

	/**
	 * Asigna el parametro de alcance a una consulta preparada cuando el usuario no es global.
	 * Mantiene la misma posicion de parametro para conteo y busqueda del registro mas reciente.
	 *
	 * @param statement consulta preparada que puede requerir el filtro de alcance.
	 * @param alcanceUsuario alcance resuelto para el usuario.
	 * @throws Exception si ocurre un error al asignar el parametro.
	 *
	 * @author Michelle Ceja
	 * @date 2026-07-27
	 */
	private void asignarParametroAlcance(PreparedStatement statement, AlcanceUsuarioComprobanteCompra alcanceUsuario) throws Exception {
		if (!alcanceUsuario.consultaGlobal) {
			statement.setString(1, alcanceUsuario.valorFiltro);
		}
	}

	/**
	 * Registra en evidencia el resultado de validar el minimo de registros para duplicados de Comprobantes de Compra.
	 * Indica si el flujo puede continuar sin duplicar o cuantos registros faltan para la etapa posterior.
	 *
	 * @param resultado resultado de conteo y registro de referencia del usuario.
	 *
	 * @author Michelle Ceja
	 * @date 2026-07-27
	 */
	private void registrarResumenValidacion(ResultadoValidacionDuplicadosComprobanteCompra resultado) {
		testCase.addBoldTextEvidenceCurrentStep("Validacion de registros para performance Comprobantes de Compra");
		testCase.addTextEvidenceCurrentStep("Usuario: " + resultado.alcanceUsuario.usuario
				+ " | Perfil: " + resultado.alcanceUsuario.perfil
				+ " | Filtro: " + resultado.alcanceUsuario.tipoFiltro
				+ " | Valor: " + resultado.alcanceUsuario.valorFiltro
				+ " | Total registros: " + resultado.totalRegistros
				+ " | Minimo requerido: " + resultado.minimoRegistros
				+ " | Cumple minimo: " + resultado.cumpleMinimo
				+ " | Registros faltantes: " + resultado.registrosFaltantes
				+ " | ID referencia xxcm_cc: " + resultado.idComprobanteReferencia
				+ " | ID referencia historico: " + resultado.idHistoricoReferencia
				+ " | Fecha registro referencia: " + resultado.fechaRegistroReferencia);
		System.out.println("[DUPLICADOS CC] Usuario: " + resultado.alcanceUsuario.usuario
				+ " | Perfil: " + resultado.alcanceUsuario.perfil
				+ " | Filtro: " + resultado.alcanceUsuario.tipoFiltro
				+ " | Valor: " + resultado.alcanceUsuario.valorFiltro
				+ " | Total registros: " + resultado.totalRegistros
				+ " | Minimo requerido: " + resultado.minimoRegistros
				+ " | Cumple minimo: " + resultado.cumpleMinimo
				+ " | Registros faltantes: " + resultado.registrosFaltantes
				+ " | ID referencia xxcm_cc: " + resultado.idComprobanteReferencia
				+ " | ID referencia historico: " + resultado.idHistoricoReferencia
				+ " | Fecha registro referencia: " + resultado.fechaRegistroReferencia);
	}

	/**
	 * Ejecuta una prueba controlada de duplicados para Comprobantes de Compra.
	 * Limpia al inicio registros posteriores al ID de referencia, duplica una cantidad fija en historico correos, sincroniza xxcm_cc y valida el incremento por alcance.
	 *
	 * @param usuario usuario de Portal COMETA que define el alcance de datos.
	 * @param minimoRegistros minimo final esperado para la prueba de performance.
	 * @param cantidadDuplicadosPrueba cantidad fija de duplicados a insertar para validar el flujo antes de duplicar faltantes reales.
	 * @return resultado posterior a la sincronizacion con el nuevo total de registros.
	 * @throws Exception si falla la limpieza, duplicado, sincronizacion o validacion.
	 *
	 * @author Michelle Ceja
	 * @date 2026-07-27
	 */
	public ResultadoValidacionDuplicadosComprobanteCompra generarDuplicadosPruebaControlada(String usuario, int minimoRegistros, int cantidadDuplicadosPrueba) throws Exception {
		try (Connection connection = abrirConexionCometa()) {
			AlcanceUsuarioComprobanteCompra alcanceUsuario = obtenerAlcanceUsuario(connection, usuario);
			long totalInicial = obtenerTotalRegistrosPorAlcance(connection, alcanceUsuario);
			ResultadoValidacionDuplicadosComprobanteCompra referenciaInicial = obtenerRegistroMasRecientePorAlcance(
					connection, alcanceUsuario, totalInicial, minimoRegistros);
			long totalHistoricoInicial = contarHistoricoCorreos(connection);

			registrarLog("Paso 1-2 alcance y referencia", "Usuario: " + usuario
					+ " | Perfil: " + alcanceUsuario.perfil
					+ " | Filtro: " + alcanceUsuario.tipoFiltro
					+ " | Valor: " + alcanceUsuario.valorFiltro
					+ " | Total alcance inicial: " + totalInicial
					+ " | ID historico referencia: " + referenciaInicial.idHistoricoReferencia
					+ " | ID comprobante referencia: " + referenciaInicial.idComprobanteReferencia);
			registrarLog("Paso 3 conteo historico correos", "Total xxcm_historico_correos antes de limpieza: " + totalHistoricoInicial);

			long eliminadosInicio = eliminarHistoricosPosterioresAReferencia(connection, referenciaInicial.idHistoricoReferencia);
			registrarLog("Limpieza inicial de duplicados", "Registros eliminados con id > " + referenciaInicial.idHistoricoReferencia + ": " + eliminadosInicio);
			sincronizarComprobantesCompra(connection);

			long totalTrasLimpieza = obtenerTotalRegistrosPorAlcance(connection, alcanceUsuario);
			ResultadoValidacionDuplicadosComprobanteCompra referenciaTrasLimpieza = obtenerRegistroMasRecientePorAlcance(
					connection, alcanceUsuario, totalTrasLimpieza, minimoRegistros);
			long totalHistoricoAntesDuplicar = contarHistoricoCorreos(connection);
			registrarLog("Validacion despues de limpieza", "Total alcance tras limpieza: " + totalTrasLimpieza
					+ " | Total historico correos: " + totalHistoricoAntesDuplicar
					+ " | ID historico a duplicar: " + referenciaTrasLimpieza.idHistoricoReferencia);

			long duplicadosInsertados = insertarDuplicadosHistoricoCorreos(connection,
					referenciaTrasLimpieza.idHistoricoReferencia, cantidadDuplicadosPrueba);
			long totalHistoricoDespuesInsert = contarHistoricoCorreos(connection);
			registrarLog("Paso 4 duplicidad historico correos", "ID duplicado: " + referenciaTrasLimpieza.idHistoricoReferencia
					+ " | Duplicados solicitados: " + cantidadDuplicadosPrueba
					+ " | Duplicados insertados: " + duplicadosInsertados
					+ " | Total historico correos despues insert: " + totalHistoricoDespuesInsert);

			sincronizarComprobantesCompra(connection);
			long totalTrasDuplicado = obtenerTotalRegistrosPorAlcance(connection, alcanceUsuario);
			ResultadoValidacionDuplicadosComprobanteCompra resultadoFinal = obtenerRegistroMasRecientePorAlcance(
					connection, alcanceUsuario, totalTrasDuplicado, minimoRegistros);
			registrarResumenDuplicacionPrueba(referenciaTrasLimpieza, resultadoFinal, cantidadDuplicadosPrueba);

			long eliminadosFinal = eliminarHistoricosPosterioresAReferencia(connection, referenciaTrasLimpieza.idHistoricoReferencia);
			registrarLog("Limpieza final de duplicados de prueba", "Registros eliminados con id > "
					+ referenciaTrasLimpieza.idHistoricoReferencia + ": " + eliminadosFinal);
			sincronizarComprobantesCompra(connection);
			long totalDespuesLimpiezaFinal = obtenerTotalRegistrosPorAlcance(connection, alcanceUsuario);
			long totalHistoricoDespuesLimpiezaFinal = contarHistoricoCorreos(connection);
			registrarLog("Validacion despues de limpieza final", "Total alcance final: " + totalDespuesLimpiezaFinal
					+ " | Total historico correos final: " + totalHistoricoDespuesLimpiezaFinal);

			return resultadoFinal;
		} catch (Exception exception) {
			testCase.addCodeEvidenceCurrentStep("Error al generar duplicados de prueba para Comprobantes de Compra: " + exception.getMessage());
			throw new Exception("Error al generar duplicados de prueba para Comprobantes de Compra: " + exception.getMessage());
		}
	}

	/**
	 * Genera los duplicados reales necesarios para alcanzar el minimo de registros de Comprobantes de Compra.
	 * Conserva los duplicados generados para que el script pueda ejecutar la prueba de performance con volumen suficiente.
	 *
	 * @param usuario usuario de Portal COMETA que define el alcance de datos.
	 * @param minimoRegistros minimo final requerido para la prueba de performance.
	 * @return resultado posterior a insertar los duplicados faltantes y sincronizar las tablas proyectadas.
	 * @throws Exception si falla la insercion, sincronizacion o validacion del minimo requerido.
	 *
	 * @author Michelle Ceja
	 * @date 2026-07-27
	 */
	public ResultadoValidacionDuplicadosComprobanteCompra generarDuplicadosHastaMinimo(String usuario, int minimoRegistros) throws Exception {
		try (Connection connection = abrirConexionCometa()) {
			AlcanceUsuarioComprobanteCompra alcanceUsuario = obtenerAlcanceUsuario(connection, usuario);
			long totalInicial = obtenerTotalRegistrosPorAlcance(connection, alcanceUsuario);
			ResumenRangoMensualComprobantesCompra resumenMesRecienteInicial = obtenerResumenMesReciente(connection, alcanceUsuario);
			ResultadoValidacionDuplicadosComprobanteCompra referenciaInicial = obtenerRegistroMasRecientePorAlcance(
					connection, alcanceUsuario, resumenMesRecienteInicial.totalRegistrosMes, minimoRegistros);
			long totalHistoricoInicial = contarHistoricoCorreos(connection);

			registrarLog("Preparacion real de duplicados", "Usuario: " + usuario
					+ " | Perfil: " + alcanceUsuario.perfil
					+ " | Filtro: " + alcanceUsuario.tipoFiltro
					+ " | Valor: " + alcanceUsuario.valorFiltro
					+ " | Total alcance inicial: " + totalInicial
					+ " | Fecha Inicio mes reciente: " + resumenMesRecienteInicial.fechaInicio
					+ " | Fecha Fin mes reciente: " + resumenMesRecienteInicial.fechaFin
					+ " | Total mes reciente inicial: " + resumenMesRecienteInicial.totalRegistrosMes
					+ " | Minimo requerido: " + minimoRegistros
					+ " | Faltantes: " + referenciaInicial.registrosFaltantes
					+ " | ID historico a duplicar: " + referenciaInicial.idHistoricoReferencia
					+ " | Total historico correos inicial: " + totalHistoricoInicial);

			if (referenciaInicial.cumpleMinimo) {
				registrarLog("Preparacion real de duplicados", "El usuario ya cumple el minimo requerido; no se insertan duplicados.");
				return referenciaInicial;
			}

			if (referenciaInicial.registrosFaltantes > Integer.MAX_VALUE) {
				throw new Exception("La cantidad de registros faltantes excede el limite permitido para generate_series: " + referenciaInicial.registrosFaltantes);
			}

			long maxIdAntes = obtenerMaxIdHistoricoCorreos(connection);
			int duplicadosReales = (int) referenciaInicial.registrosFaltantes;
			long duplicadosInsertados = insertarDuplicadosHistoricoCorreos(connection,
					referenciaInicial.idHistoricoReferencia, duplicadosReales);
			long maxIdDespues = obtenerMaxIdHistoricoCorreos(connection);
			long totalHistoricoDespuesInsert = contarHistoricoCorreos(connection);
			
			List<Long> idsDuplicadosGenerados = new ArrayList<>();
			if (maxIdDespues >= maxIdAntes) {
				for (long id = maxIdAntes + 1; id <= maxIdDespues; id++) {
					idsDuplicadosGenerados.add(id);
				}
			}
			
			registrarLog("Duplicidad real historico correos", "ID duplicado: " + referenciaInicial.idHistoricoReferencia
					+ " | Duplicados necesarios: " + duplicadosReales
					+ " | Duplicados insertados: " + duplicadosInsertados
					+ " | Total historico correos despues insert: " + totalHistoricoDespuesInsert
					+ " | IDs generados: " + (maxIdAntes + 1) + " a " + maxIdDespues
					+ " | Total IDs para cleanup: " + idsDuplicadosGenerados.size());

			sincronizarComprobantesCompra(connection);
			ResumenRangoMensualComprobantesCompra resumenMesRecienteFinal = obtenerResumenMesReciente(connection, alcanceUsuario);
			long totalFinal = resumenMesRecienteFinal.totalRegistrosMes;
			ResultadoValidacionDuplicadosComprobanteCompra resultadoFinal = new ResultadoValidacionDuplicadosComprobanteCompra(
					referenciaInicial.alcanceUsuario,
					totalFinal,
					referenciaInicial.idComprobanteReferencia,
					referenciaInicial.idHistoricoReferencia,
					referenciaInicial.fechaRegistroReferencia,
					minimoRegistros,
					totalFinal >= minimoRegistros,
					totalFinal >= minimoRegistros ? 0L : minimoRegistros - totalFinal,
					idsDuplicadosGenerados);
			
			registrarResumenDuplicacionReal(referenciaInicial, resultadoFinal, duplicadosInsertados);
			registrarLog("Validacion rango mensual para UI", "Fecha Inicio: " + resumenMesRecienteFinal.fechaInicio
					+ " | Fecha Fin: " + resumenMesRecienteFinal.fechaFin
					+ " | Total mes reciente final: " + resumenMesRecienteFinal.totalRegistrosMes
					+ " | Minimo requerido: " + minimoRegistros);

			return resultadoFinal;
		} catch (Exception exception) {
			testCase.addCodeEvidenceCurrentStep("Error al generar duplicados reales para Comprobantes de Compra: " + exception.getMessage());
			throw new Exception("Error al generar duplicados reales para Comprobantes de Compra: " + exception.getMessage());
		}
	}

	/**
	 * Genera duplicados reales para garantizar el minimo de registros dentro de la ventana movil
	 * de ultimos 7 dias (hoy y los 6 dias anteriores) usada por el filtro de Comprobantes de Compra.
	 * Este flujo no reemplaza al metodo mensual existente; es una variante dedicada para pruebas
	 * de performance Excel vs PDF donde ambos formatos deben consultar exactamente el mismo rango de 7 dias.
	 *
	 * @param usuario usuario de Portal COMETA que define el alcance de datos.
	 * @param minimoRegistros minimo final requerido dentro del rango de 7 dias.
	 * @return resultado posterior a insertar los duplicados faltantes con fecha_registro en la ventana de 7 dias.
	 * @throws Exception si falla la insercion, sincronizacion o validacion del minimo requerido.
	 *
	 * @author Mauricio Gurrola
	 * @date 2026-07-30
	 */
	public ResultadoValidacionDuplicadosComprobanteCompra generarDuplicadosHastaMinimoUltimos7Dias(String usuario, int minimoRegistros) throws Exception {
		try (Connection connection = abrirConexionCometa()) {
			AlcanceUsuarioComprobanteCompra alcanceUsuario = obtenerAlcanceUsuario(connection, usuario);
			LocalDate fechaFin = LocalDate.now();
			LocalDate fechaInicio = fechaFin.minusDays(6);
			long totalRango7DiasInicial = obtenerTotalRegistrosPorAlcanceEnRango(connection, alcanceUsuario, fechaInicio, fechaFin);
			ResultadoValidacionDuplicadosComprobanteCompra referenciaInicial = obtenerRegistroMasRecientePorAlcance(
					connection, alcanceUsuario, totalRango7DiasInicial, minimoRegistros);

			registrarLog("Preparacion real de duplicados (7 dias)", "Usuario: " + usuario
					+ " | Perfil: " + alcanceUsuario.perfil
					+ " | Filtro: " + alcanceUsuario.tipoFiltro
					+ " | Valor: " + alcanceUsuario.valorFiltro
					+ " | Fecha Inicio 7 dias: " + fechaInicio
					+ " | Fecha Fin 7 dias: " + fechaFin
					+ " | Total 7 dias inicial: " + totalRango7DiasInicial
					+ " | Minimo requerido: " + minimoRegistros
					+ " | Faltantes: " + referenciaInicial.registrosFaltantes
					+ " | ID historico a duplicar: " + referenciaInicial.idHistoricoReferencia);

			if (referenciaInicial.cumpleMinimo) {
				registrarLog("Preparacion real de duplicados (7 dias)", "El usuario ya cumple el minimo en ultimos 7 dias; no se insertan duplicados.");
				return referenciaInicial;
			}

			if (referenciaInicial.registrosFaltantes > Integer.MAX_VALUE) {
				throw new Exception("La cantidad de registros faltantes excede el limite permitido para generate_series: " + referenciaInicial.registrosFaltantes);
			}

			long maxIdAntes = obtenerMaxIdHistoricoCorreos(connection);
			int duplicadosReales = (int) referenciaInicial.registrosFaltantes;
			long duplicadosInsertados = insertarDuplicadosHistoricoCorreosUltimos7Dias(connection,
					referenciaInicial.idHistoricoReferencia, duplicadosReales);
			long maxIdDespues = obtenerMaxIdHistoricoCorreos(connection);

			List<Long> idsDuplicadosGenerados = new ArrayList<>();
			if (maxIdDespues >= maxIdAntes) {
				for (long id = maxIdAntes + 1; id <= maxIdDespues; id++) {
					idsDuplicadosGenerados.add(id);
				}
			}

			registrarLog("Duplicidad real historico correos (7 dias)", "ID duplicado: " + referenciaInicial.idHistoricoReferencia
					+ " | Duplicados necesarios: " + duplicadosReales
					+ " | Duplicados insertados: " + duplicadosInsertados
					+ " | IDs generados: " + (maxIdAntes + 1) + " a " + maxIdDespues
					+ " | Total IDs para cleanup: " + idsDuplicadosGenerados.size());

			sincronizarComprobantesCompra(connection);
			long totalRango7DiasFinal = obtenerTotalRegistrosPorAlcanceEnRango(connection, alcanceUsuario, fechaInicio, fechaFin);
			ResultadoValidacionDuplicadosComprobanteCompra resultadoFinal = new ResultadoValidacionDuplicadosComprobanteCompra(
					referenciaInicial.alcanceUsuario,
					totalRango7DiasFinal,
					referenciaInicial.idComprobanteReferencia,
					referenciaInicial.idHistoricoReferencia,
					referenciaInicial.fechaRegistroReferencia,
					minimoRegistros,
					totalRango7DiasFinal >= minimoRegistros,
					totalRango7DiasFinal >= minimoRegistros ? 0L : minimoRegistros - totalRango7DiasFinal,
					idsDuplicadosGenerados);

			registrarResumenDuplicacionReal(referenciaInicial, resultadoFinal, duplicadosInsertados);
			registrarLog("Validacion rango 7 dias para UI", "Fecha Inicio: " + fechaInicio
					+ " | Fecha Fin: " + fechaFin
					+ " | Total 7 dias final: " + totalRango7DiasFinal
					+ " | Minimo requerido: " + minimoRegistros);

			return resultadoFinal;
		} catch (Exception exception) {
			testCase.addCodeEvidenceCurrentStep("Error al generar duplicados reales de ultimos 7 dias para Comprobantes de Compra: " + exception.getMessage());
			throw new Exception("Error al generar duplicados reales de ultimos 7 dias para Comprobantes de Compra: " + exception.getMessage());
		}
	}

	/**
	 * Elimina los registros duplicados que fueron insertados durante la preparacion de datos para performance.
	 * Ejecuta limpeza de xxcm_historico_correos y resincroniza xxcm_cc para garantizar integridad referencial.
	 * Este metodo se ejecuta en @AfterMethod para limpiar el ambiente de prueba tras completar o fallar el script.
	 *
	 * @param usuario usuario para el cual se insertaron duplicados.
	 * @param idsDuplicadosGenerados lista de IDs a eliminar de xxcm_historico_correos.
	 * @throws Exception si falla la eliminacion o sincronizacion.
	 *
	 * @author Michelle Ceja
	 * @date 2026-07-27
	 */
	public void eliminarDuplicadosGenerados(String usuario, List<Long> idsDuplicadosGenerados) throws Exception {
		if (idsDuplicadosGenerados == null || idsDuplicadosGenerados.isEmpty()) {
			System.out.println("[CLEANUP DUPLICADOS] No hay IDs de duplicados para eliminar. Flujo limpio.");
			return;
		}

		try (Connection connection = abrirConexionCometa()) {
			long totalHistoricoAntes = contarHistoricoCorreos(connection);
			System.out.println("[CLEANUP DUPLICADOS] Iniciando eliminacion de " + idsDuplicadosGenerados.size() + " duplicados para usuario " + usuario);

			long eliminados = 0;
			String sql = "DELETE FROM public.xxcm_historico_correos WHERE id = ?";
			try (PreparedStatement statement = connection.prepareStatement(sql)) {
				for (Long idDuplicado : idsDuplicadosGenerados) {
					statement.setLong(1, idDuplicado);
					eliminados += statement.executeUpdate();
				}
			}

			long totalHistoricoDespues = contarHistoricoCorreos(connection);
			System.out.println("[CLEANUP DUPLICADOS] Registros eliminados: " + eliminados + " | Total historico antes: " + totalHistoricoAntes
					+ " | Total historico despues: " + totalHistoricoDespues);

			sincronizarComprobantesCompra(connection);
			System.out.println("[CLEANUP DUPLICADOS] Sincronizacion completada. Base de datos limpia.");
			System.out.println("[CLEANUP DUPLICADOS] Cleanup de duplicados completado: " + eliminados + " registros eliminados.");
		} catch (Exception exception) {
			System.out.println("[CLEANUP DUPLICADOS] Error durante eliminacion: " + exception.getMessage());
			exception.printStackTrace();
			throw new Exception("Error al eliminar duplicados generados para usuario " + usuario + ": " + exception.getMessage());
		}
	}

	/**
	 * Cuenta todos los registros existentes en xxcm_historico_correos solo como informacion de control.
	 * El conteo permite observar en consola el efecto de limpieza e insercion de duplicados.
	 *
	 * @param connection conexion activa a BD COMETA.
	 * @return total de registros de xxcm_historico_correos.
	 * @throws Exception si falla la consulta.
	 *
	 * @author Michelle Ceja
	 * @date 2026-07-27
	 */
	private long contarHistoricoCorreos(Connection connection) throws Exception {
		String sql = "SELECT COUNT(*) AS total FROM public.xxcm_historico_correos";
		try (PreparedStatement statement = connection.prepareStatement(sql);
				ResultSet resultSet = statement.executeQuery()) {
			return resultSet.next() ? resultSet.getLong("total") : 0L;
		}
	}

	/**
	 * Obtiene el ID maximo actual de xxcm_historico_correos.
	 * Se usa para capturar el rango de IDs insertados durante duplicacion.
	 *
	 * @param connection conexion activa a BD COMETA.
	 * @return ID maximo de xxcm_historico_correos, o 0 si la tabla esta vacia.
	 * @throws Exception si falla la consulta.
	 *
	 * @author Michelle Ceja
	 * @date 2026-07-27
	 */
	private long obtenerMaxIdHistoricoCorreos(Connection connection) throws Exception {
		String sql = "SELECT COALESCE(MAX(id), 0) AS max_id FROM public.xxcm_historico_correos";
		try (PreparedStatement statement = connection.prepareStatement(sql);
				ResultSet resultSet = statement.executeQuery()) {
			return resultSet.next() ? resultSet.getLong("max_id") : 0L;
		}
	}

	/**
	 * Elimina registros de historico correos con ID mayor al ID de referencia antes de ejecutar la prueba controlada.
	 * Esta limpieza inicial es temporal para validar el flujo de duplicados y despues se movera al cierre del script.
	 *
	 * @param connection conexion activa a BD COMETA.
	 * @param idHistoricoReferencia ID base que no debe eliminarse.
	 * @return cantidad de registros eliminados.
	 * @throws Exception si falla la eliminacion.
	 *
	 * @author Michelle Ceja
	 * @date 2026-07-27
	 */
	private long eliminarHistoricosPosterioresAReferencia(Connection connection, long idHistoricoReferencia) throws Exception {
		String sql = "DELETE FROM public.xxcm_historico_correos WHERE id > ?";
		try (PreparedStatement statement = connection.prepareStatement(sql)) {
			statement.setLong(1, idHistoricoReferencia);
			return statement.executeUpdate();
		}
	}

	/**
	 * Inserta duplicados en xxcm_historico_correos a partir de un registro de referencia.
	 * Genera nuevos IDs tomando MAX(id) + generate_series para no reutilizar identificadores existentes.
	 *
	 * @param connection conexion activa a BD COMETA.
	 * @param idHistoricoReferencia ID del registro que sera duplicado.
	 * @param cantidadDuplicados cantidad de registros a insertar.
	 * @return cantidad de registros insertados.
	 * @throws Exception si falla la insercion.
	 *
	 * @author Michelle Ceja
	 * @date 2026-07-27
	 */
	private long insertarDuplicadosHistoricoCorreos(Connection connection, long idHistoricoReferencia, int cantidadDuplicados) throws Exception {
		String sql = "INSERT INTO public.xxcm_historico_correos "
				+ "SELECT "
				+ "(SELECT MAX(id) FROM xxcm_historico_correos) + gs, "
				+ "cr_plaza, "
				+ "cr_tienda, "
				+ "body, "
				+ "estatus, "
				+ "transferencia, "
				+ "origen, "
				+ "created_at, "
				+ "updated_at, "
				+ "id_retek, "
				+ "id_retek_assigned_at, "
				+ "is_visible, "
				+ "version_documento, "
				+ "fecha_registro, "
				+ "suppliable_id, "
				+ "ruta_original, "
				+ "ruta_propuesta, "
				+ "data_optimized_at "
				+ "FROM public.xxcm_historico_correos "
				+ "CROSS JOIN generate_series(1, ?::integer) gs "
				+ "WHERE id = ?";
		try (PreparedStatement statement = connection.prepareStatement(sql)) {
			statement.setInt(1, cantidadDuplicados);
			statement.setLong(2, idHistoricoReferencia);
			return statement.executeUpdate();
		}
	}

	/**
	 * Inserta duplicados en xxcm_historico_correos forzando fecha_registro en la ventana de ultimos 7 dias.
	 * Se usa exclusivamente para que el filtro de Comprobantes de Compra (ultimos 7 dias) incremente su volumen.
	 *
	 * @param connection conexion activa a BD COMETA.
	 * @param idHistoricoReferencia ID del registro que sera duplicado.
	 * @param cantidadDuplicados cantidad de registros a insertar.
	 * @return cantidad de registros insertados.
	 * @throws Exception si falla la insercion.
	 *
	 * @author Mauricio Gurrola
	 * @date 2026-07-30
	 */
	private long insertarDuplicadosHistoricoCorreosUltimos7Dias(Connection connection, long idHistoricoReferencia,
			int cantidadDuplicados) throws Exception {
		String sql = "INSERT INTO public.xxcm_historico_correos "
				+ "SELECT "
				+ "(SELECT MAX(id) FROM xxcm_historico_correos) + gs, "
				+ "cr_plaza, "
				+ "cr_tienda, "
				+ "body, "
				+ "estatus, "
				+ "transferencia, "
				+ "origen, "
				+ "created_at, "
				+ "updated_at, "
				+ "id_retek, "
				+ "id_retek_assigned_at, "
				+ "is_visible, "
				+ "version_documento, "
				+ "(CURRENT_DATE - ((gs - 1) % 7))::timestamp as fecha_registro, "
				+ "suppliable_id, "
				+ "ruta_original, "
				+ "ruta_propuesta, "
				+ "data_optimized_at "
				+ "FROM public.xxcm_historico_correos "
				+ "CROSS JOIN generate_series(1, ?::integer) gs "
				+ "WHERE id = ?";
		try (PreparedStatement statement = connection.prepareStatement(sql)) {
			statement.setInt(1, cantidadDuplicados);
			statement.setLong(2, idHistoricoReferencia);
			return statement.executeUpdate();
		}
	}

	/**
	 * Cuenta los registros de Comprobantes de Compra visibles para el alcance del usuario dentro de un rango de fechas.
	 *
	 * @param connection conexion activa a BD COMETA.
	 * @param alcanceUsuario alcance resuelto para el usuario.
	 * @param fechaInicio fecha inicial del rango (inclusive).
	 * @param fechaFin fecha final del rango (inclusive).
	 * @return total de registros existentes para el alcance dentro del rango solicitado.
	 * @throws Exception si ocurre un error al ejecutar el conteo.
	 *
	 * @author Mauricio Gurrola
	 * @date 2026-07-30
	 */
	private long obtenerTotalRegistrosPorAlcanceEnRango(Connection connection,
			AlcanceUsuarioComprobanteCompra alcanceUsuario, LocalDate fechaInicio, LocalDate fechaFin) throws Exception {
		String sql = obtenerCteComprobantesCompra(alcanceUsuario)
				+ "select count(*) as total_registros "
				+ "from cte_data "
				+ "where fecha_registro::date between ? and ?";

		try (PreparedStatement statement = connection.prepareStatement(sql)) {
			int indiceParametro = 1;
			if (!alcanceUsuario.consultaGlobal) {
				statement.setString(indiceParametro++, alcanceUsuario.valorFiltro);
			}
			statement.setObject(indiceParametro++, fechaInicio);
			statement.setObject(indiceParametro, fechaFin);
			try (ResultSet resultSet = statement.executeQuery()) {
				return resultSet.next() ? resultSet.getLong("total_registros") : 0L;
			}
		}
	}

	/**
	 * Sincroniza las tablas proyectadas de Comprobantes de Compra despues de limpiar o insertar historicos.
	 * Ejecuta truncate sobre xxcm_cc y llama el procedimiento sincronizar_datos_comprobantes_compras.
	 *
	 * @param connection conexion activa a BD COMETA.
	 * @throws Exception si falla el truncate o el procedimiento de sincronizacion.
	 *
	 * @author Michelle Ceja
	 * @date 2026-07-27
	 */
	private void sincronizarComprobantesCompra(Connection connection) throws Exception {
		try (PreparedStatement truncateStatement = connection.prepareStatement("TRUNCATE TABLE public.xxcm_cc CASCADE")) {
			truncateStatement.executeUpdate();
		}
		System.out.println("[DUPLICADOS CC] Paso 5.1 truncate xxcm_cc | TRUNCATE TABLE public.xxcm_cc CASCADE ejecutado");

		try (PreparedStatement callStatement = connection.prepareStatement("CALL public.sincronizar_datos_comprobantes_compras()")) {
			callStatement.executeUpdate();
		}
		System.out.println("[DUPLICADOS CC] Paso 5.2 sincronizador comprobantes | CALL public.sincronizar_datos_comprobantes_compras() ejecutado");
	}

	/**
	 * Registra en consola y evidencia el resumen de la prueba controlada de duplicacion.
	 * Compara el total antes y despues de insertar los duplicados solicitados.
	 *
	 * @param referenciaAntes resultado base despues de limpieza inicial.
	 * @param resultadoDespues resultado posterior a insertar duplicados y sincronizar.
	 * @param cantidadDuplicadosPrueba cantidad de duplicados solicitada.
	 *
	 * @author Michelle Ceja
	 * @date 2026-07-27
	 */
	private void registrarResumenDuplicacionPrueba(ResultadoValidacionDuplicadosComprobanteCompra referenciaAntes,
			ResultadoValidacionDuplicadosComprobanteCompra resultadoDespues, int cantidadDuplicadosPrueba) {
		long incremento = resultadoDespues.totalRegistros - referenciaAntes.totalRegistros;
		String resumen = "Duplicacion controlada Comprobantes Compra | Usuario: " + resultadoDespues.alcanceUsuario.usuario
				+ " | Filtro: " + resultadoDespues.alcanceUsuario.tipoFiltro
				+ " | Valor: " + resultadoDespues.alcanceUsuario.valorFiltro
				+ " | Total antes: " + referenciaAntes.totalRegistros
				+ " | Duplicados prueba: " + cantidadDuplicadosPrueba
				+ " | Total despues sync: " + resultadoDespues.totalRegistros
				+ " | Incremento observado: " + incremento
				+ " | Minimo requerido: " + resultadoDespues.minimoRegistros
				+ " | Cumple minimo: " + resultadoDespues.cumpleMinimo
				+ " | Faltantes actuales: " + resultadoDespues.registrosFaltantes
				+ " | Nuevo ID historico referencia: " + resultadoDespues.idHistoricoReferencia;
		testCase.addBoldTextEvidenceCurrentStep("Prueba controlada de duplicados ejecutada");
		testCase.addTextEvidenceCurrentStep(resumen);
		System.out.println("[DUPLICADOS CC] " + resumen);
	}

	/**
	 * Registra en consola y evidencia el resumen de la generacion real de duplicados.
	 * Confirma el incremento obtenido y si el alcance del usuario ya cumple el minimo requerido.
	 *
	 * @param referenciaAntes resultado base antes de insertar duplicados reales.
	 * @param resultadoDespues resultado posterior a insertar duplicados reales y sincronizar.
	 * @param duplicadosInsertados cantidad de duplicados insertados en historico correos.
	 *
	 * @author Michelle Ceja
	 * @date 2026-07-27
	 */
	private void registrarResumenDuplicacionReal(ResultadoValidacionDuplicadosComprobanteCompra referenciaAntes,
			ResultadoValidacionDuplicadosComprobanteCompra resultadoDespues, long duplicadosInsertados) {
		long incremento = resultadoDespues.totalRegistros - referenciaAntes.totalRegistros;
		String resumen = "Duplicacion real Comprobantes Compra | Usuario: " + resultadoDespues.alcanceUsuario.usuario
				+ " | Filtro: " + resultadoDespues.alcanceUsuario.tipoFiltro
				+ " | Valor: " + resultadoDespues.alcanceUsuario.valorFiltro
				+ " | Total antes: " + referenciaAntes.totalRegistros
				+ " | Duplicados insertados: " + duplicadosInsertados
				+ " | Total despues sync: " + resultadoDespues.totalRegistros
				+ " | Incremento observado: " + incremento
				+ " | Minimo requerido: " + resultadoDespues.minimoRegistros
				+ " | Cumple minimo: " + resultadoDespues.cumpleMinimo
				+ " | Faltantes actuales: " + resultadoDespues.registrosFaltantes
				+ " | Nuevo ID historico referencia: " + resultadoDespues.idHistoricoReferencia;
		testCase.addBoldTextEvidenceCurrentStep("Duplicacion real de registros ejecutada");
		testCase.addTextEvidenceCurrentStep(resumen);
		System.out.println("[DUPLICADOS CC] " + resumen);
	}

	/**
	 * Escribe mensajes de seguimiento de duplicados en consola y evidencia textual.
	 * Facilita validar paso a paso la limpieza, insercion y sincronizacion durante la prueba controlada.
	 *
	 * @param etapa nombre corto de la etapa ejecutada.
	 * @param detalle detalle de la operacion o resultado observado.
	 *
	 * @author Michelle Ceja
	 * @date 2026-07-27
	 */
	private void registrarLog(String etapa, String detalle) {
		String mensaje = etapa + " | " + detalle;
		System.out.println("[DUPLICADOS CC] " + mensaje);
		testCase.addTextEvidenceCurrentStep(mensaje);
	}

	/**
	 * Obtiene el resumen del mes mas reciente con registros para el alcance del usuario.
	 * Este resumen se usa para alinear el minimo de performance con el mismo universo del filtro de UI.
	 *
	 * @param connection conexion activa a BD COMETA.
	 * @param alcanceUsuario alcance resuelto para el usuario.
	 * @return resumen con fecha inicio, fecha fin y total del mes mas reciente.
	 * @throws Exception si no existe un mes con registros para el alcance consultado.
	 *
	 * @author Michelle Ceja
	 * @date 2026-07-28
	 */
	private ResumenRangoMensualComprobantesCompra obtenerResumenMesReciente(Connection connection,
			AlcanceUsuarioComprobanteCompra alcanceUsuario) throws Exception {
		try {
			String sql = obtenerCteComprobantesCompra(alcanceUsuario)
					+ ", cte_rango AS( "
					+ "select "
					+ "min(fecha_registro::date) as fecha_inicio, "
					+ "max(fecha_registro::date) as fecha_fin, "
					+ "count(*) as total_registros "
					+ "from cte_data "
					+ "where fecha_registro is not null "
					+ "and date_trunc('month', fecha_registro)::date = ( "
					+ "	select date_trunc('month', max(fecha_registro))::date "
					+ "	from cte_data "
					+ "	where fecha_registro is not null "
					+ ") "
					+ ") "
					+ "select fecha_inicio, fecha_fin, total_registros from cte_rango";

			try (PreparedStatement statement = connection.prepareStatement(sql)) {
				asignarParametroAlcance(statement, alcanceUsuario);
				try (ResultSet resultSet = statement.executeQuery()) {
					if (!resultSet.next() || resultSet.getDate("fecha_inicio") == null || resultSet.getDate("fecha_fin") == null) {
						throw new Exception("No existe un mes reciente con registros en Comprobantes de Compra para el alcance consultado");
					}
					return new ResumenRangoMensualComprobantesCompra(
							resultSet.getDate("fecha_inicio").toString(),
							resultSet.getDate("fecha_fin").toString(),
							resultSet.getLong("total_registros"));
				}
			}
		} catch (Exception exception) {
			throw new Exception("Error al obtener resumen de mes reciente de Comprobantes de Compra: " + exception.getMessage());
		}
	}

	/**
	 * Representa el resumen del mes mas reciente con registros de Comprobantes de Compra.
	 * Conserva fechas de inicio/fin y total de registros para alinear la precondicion con la UI.
	 *
	 * @author Michelle Ceja
	 * @date 2026-07-28
	 */
	private static class ResumenRangoMensualComprobantesCompra {
		public final String fechaInicio;
		public final String fechaFin;
		public final long totalRegistrosMes;

		/**
		 * Inicializa un resumen del mes reciente para Comprobantes de Compra.
		 *
		 * @param fechaInicio fecha minima del mes reciente.
		 * @param fechaFin fecha maxima del mes reciente.
		 * @param totalRegistrosMes total de registros contenidos en ese mes.
		 *
		 * @author Michelle Ceja
		 * @date 2026-07-28
		 */
		public ResumenRangoMensualComprobantesCompra(String fechaInicio, String fechaFin, long totalRegistrosMes) {
			this.fechaInicio = fechaInicio;
			this.fechaFin = fechaFin;
			this.totalRegistrosMes = totalRegistrosMes;
		}
	}

	/**
	 * Normaliza cadenas nulas para evitar validaciones con espacios o null.
	 * Devuelve una cadena vacia cuando el valor recibido no existe.
	 *
	 * @param valor texto original obtenido desde BD.
	 * @return texto limpio sin espacios laterales o cadena vacia.
	 *
	 * @author Michelle Ceja
	 * @date 2026-07-27
	 */
	private String limpiar(String valor) {
		return valor == null ? "" : valor.trim();
	}
}
