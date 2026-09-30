package WEB.cometa.om;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import WEB.util.GlobalVariables;
import modelo.TestCase;

public class generaDuplicadosCambiosFisicos {

	private final TestCase testCase;

	/**
	 * Inicializa la utilidad de preparacion de datos para duplicados de Cambios Fisicos.
	 * La clase consulta BD, genera duplicados controlados y registra evidencia del proceso.
	 *
	 * @param testCase caso de prueba usado para registrar evidencia.
	 *
	 * @author Michelle Ceja
	 * @date 2026-07-27
	 */
	public generaDuplicadosCambiosFisicos(TestCase testCase) {
		this.testCase = testCase;
	}

	public static class AlcanceUsuarioCambiosFisicos {
		public final String usuario;
		public final String perfil;
		public final String tipoFiltro;
		public final String valorFiltro;
		public final boolean consultaGlobal;

		/**
		 * Conserva el alcance de datos configurado para el usuario de Cambios Fisicos.
		 * Define si la consulta se ejecuta global o filtrada por plaza, RFC o supplierId.
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
		public AlcanceUsuarioCambiosFisicos(String usuario, String perfil, String tipoFiltro, String valorFiltro, boolean consultaGlobal) {
			this.usuario = usuario;
			this.perfil = perfil;
			this.tipoFiltro = tipoFiltro;
			this.valorFiltro = valorFiltro;
			this.consultaGlobal = consultaGlobal;
		}
	}

	public static class ResultadoValidacionDuplicadosCambiosFisicos {
		public final AlcanceUsuarioCambiosFisicos alcanceUsuario;
		public final long totalRegistros;
		public final long idCambioFisicoReferencia;
		public final long idHistoricoReferencia;
		public final Timestamp fechaRegistroReferencia;
		public final int minimoRegistros;
		public final boolean cumpleMinimo;
		public final long registrosFaltantes;
		public final List<Long> idsDuplicadosGenerados;

		/**
		 * Conserva el resultado de validar y preparar duplicados de Cambios Fisicos.
		 * Incluye totales, registro de referencia y los IDs generados para limpieza posterior.
		 *
		 * @param alcanceUsuario alcance de consulta resuelto para el usuario.
		 * @param totalRegistros total actual de registros para ese alcance.
		 * @param idCambioFisicoReferencia id mas reciente de xxcm_cf dentro del alcance.
		 * @param idHistoricoReferencia id historico asociado al registro de Cambios Fisicos.
		 * @param fechaRegistroReferencia fecha del registro mas reciente.
		 * @param minimoRegistros minimo requerido para la prueba de performance.
		 * @param cumpleMinimo true si el total actual es mayor o igual al minimo requerido.
		 * @param registrosFaltantes cantidad de registros que faltan para llegar al minimo.
		 * @param idsDuplicadosGenerados lista de IDs insertados durante duplicacion.
		 *
		 * @author Michelle Ceja
		 * @date 2026-07-27
		 */
		public ResultadoValidacionDuplicadosCambiosFisicos(AlcanceUsuarioCambiosFisicos alcanceUsuario,
				long totalRegistros, long idCambioFisicoReferencia, long idHistoricoReferencia,
				Timestamp fechaRegistroReferencia, int minimoRegistros, boolean cumpleMinimo, long registrosFaltantes,
				List<Long> idsDuplicadosGenerados) {
			this.alcanceUsuario = alcanceUsuario;
			this.totalRegistros = totalRegistros;
			this.idCambioFisicoReferencia = idCambioFisicoReferencia;
			this.idHistoricoReferencia = idHistoricoReferencia;
			this.fechaRegistroReferencia = fechaRegistroReferencia;
			this.minimoRegistros = minimoRegistros;
			this.cumpleMinimo = cumpleMinimo;
			this.registrosFaltantes = registrosFaltantes;
			this.idsDuplicadosGenerados = idsDuplicadosGenerados;
		}
	}

	/**
	 * Valida si el usuario cuenta con el minimo de registros de Cambios Fisicos para pruebas de performance.
	 * Resuelve el alcance del usuario y localiza el registro mas reciente como referencia de duplicacion.
	 *
	 * @param usuario usuario de Portal COMETA que se validara en BD.
	 * @param minimoRegistros minimo requerido para ejecutar la prueba de descarga.
	 * @return resultado con alcance, total de registros y registro de referencia.
	 * @throws Exception si no se puede resolver el alcance o no existe registro de referencia.
	 *
	 * @author Michelle Ceja
	 * @date 2026-07-27
	 */
	public ResultadoValidacionDuplicadosCambiosFisicos validarMinimoRegistrosPorUsuario(String usuario, int minimoRegistros) throws Exception {
		try (Connection connection = abrirConexionCometa()) {
			AlcanceUsuarioCambiosFisicos alcanceUsuario = obtenerAlcanceUsuario(connection, usuario);
			long totalRegistros = obtenerTotalRegistrosPorAlcance(connection, alcanceUsuario);
			ResultadoValidacionDuplicadosCambiosFisicos resultado = obtenerRegistroMasRecientePorAlcance(
					connection, alcanceUsuario, totalRegistros, minimoRegistros);

			registrarResumenValidacion(resultado);
			return resultado;
		} catch (Exception exception) {
			testCase.addCodeEvidenceCurrentStep("Error al validar registros para duplicados de Cambios Fisicos: " + exception.getMessage());
			throw new Exception("Error al validar registros para duplicados de Cambios Fisicos: " + exception.getMessage());
		}
	}

	/**
	 * Abre la conexion a la base de datos de Portal COMETA usando GlobalVariables.
	 * Centraliza el origen de credenciales del framework para las consultas de preparacion.
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
	 * @return alcance resuelto para construir las consultas de Cambios Fisicos.
	 * @throws Exception si el usuario no tiene perfil o no tiene llave de alcance cuando la requiere.
	 *
	 * @author Michelle Ceja
	 * @date 2026-07-27
	 */
	private AlcanceUsuarioCambiosFisicos obtenerAlcanceUsuario(Connection connection, String usuario) throws Exception {
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
			return new AlcanceUsuarioCambiosFisicos(usuario, perfil, "GLOBAL", "SIN FILTRO", true);
		}

		if ("OXXO_PLAZA".equals(perfilNormalizado)) {
			if (pcPlaza.isEmpty()) {
				throw new Exception("El usuario " + usuario + " tiene perfil OXXO_PLAZA pero no tiene PCPLAZA configurado");
			}
			return new AlcanceUsuarioCambiosFisicos(usuario, perfil, "PCPLAZA", pcPlaza, false);
		}

		if (!pcrfc.isEmpty()) {
			return new AlcanceUsuarioCambiosFisicos(usuario, perfil, "PCRFC", pcrfc, false);
		}

		if (!pcSupplierId.isEmpty()) {
			return new AlcanceUsuarioCambiosFisicos(usuario, perfil, "PCSUPPLIERID", pcSupplierId, false);
		}

		throw new Exception("No se obtuvo PCRFC, PCSUPPLIERID o PCPLAZA para el usuario " + usuario);
	}

	/**
	 * Cuenta los registros de Cambios Fisicos visibles para el alcance del usuario.
	 * Usa el CTE base de Cambios Fisicos para que el conteo sea equivalente a la tabla proyectada xxcm_cf.
	 *
	 * @param connection conexion activa a BD COMETA.
	 * @param alcanceUsuario alcance resuelto para el usuario.
	 * @return total de registros existentes para el alcance.
	 * @throws Exception si ocurre un error al ejecutar el conteo.
	 *
	 * @author Michelle Ceja
	 * @date 2026-07-27
	 */
	private long obtenerTotalRegistrosPorAlcance(Connection connection, AlcanceUsuarioCambiosFisicos alcanceUsuario) throws Exception {
		String sql = obtenerCteCambiosFisicos(alcanceUsuario)
				+ "select count(*) as total_registros from cte_data";

		try (PreparedStatement statement = connection.prepareStatement(sql)) {
			asignarParametroAlcance(statement, alcanceUsuario);
			try (ResultSet resultSet = statement.executeQuery()) {
				return resultSet.next() ? resultSet.getLong("total_registros") : 0L;
			}
		}
	}

	/**
	 * Obtiene el registro mas reciente de Cambios Fisicos para el alcance del usuario.
	 * El registro obtenido se usa como referencia cuando se requieren duplicados para cumplir el minimo.
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
	private ResultadoValidacionDuplicadosCambiosFisicos obtenerRegistroMasRecientePorAlcance(Connection connection,
			AlcanceUsuarioCambiosFisicos alcanceUsuario, long totalRegistros, int minimoRegistros) throws Exception {
		String sql = obtenerCteCambiosFisicos(alcanceUsuario)
				+ "select id, historico_id, fecha_registro "
				+ "from cte_data "
				+ "order by historico_id desc "
				+ "limit 1";

		try (PreparedStatement statement = connection.prepareStatement(sql)) {
			asignarParametroAlcance(statement, alcanceUsuario);
			try (ResultSet resultSet = statement.executeQuery()) {
				if (!resultSet.next()) {
					throw new Exception("No existe registro de referencia en Cambios Fisicos para el usuario " + alcanceUsuario.usuario);
				}

				boolean cumpleMinimo = totalRegistros >= minimoRegistros;
				long registrosFaltantes = cumpleMinimo ? 0L : minimoRegistros - totalRegistros;
				return new ResultadoValidacionDuplicadosCambiosFisicos(
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
	 * Construye el CTE base de Cambios Fisicos con el filtro del alcance del usuario.
	 * El CTE incluye las columnas necesarias para contar registros e identificar el id mas reciente de xxcm_cf.
	 *
	 * @param alcanceUsuario alcance resuelto para el usuario.
	 * @return SQL del CTE cte_data listo para concatenar la consulta final.
	 *
	 * @author Michelle Ceja
	 * @date 2026-07-27
	 */
	private String obtenerCteCambiosFisicos(AlcanceUsuarioCambiosFisicos alcanceUsuario) {
		String whereAlcance = "";
		if (!alcanceUsuario.consultaGlobal) {
			if ("PCPLAZA".equals(alcanceUsuario.tipoFiltro)) {
				whereAlcance = "where cf.cr_desc_plaza = ? ";
			} else if ("PCRFC".equals(alcanceUsuario.tipoFiltro)) {
				whereAlcance = "where cf.proveedor_rfc = ? ";
			} else if ("PCSUPPLIERID".equals(alcanceUsuario.tipoFiltro)) {
				whereAlcance = "where cf.proveedor_id::text = ? ";
			}
		}

		return "WITH cte_data AS( "
				+ "select "
				+ "cf.id, "
				+ "cf.id as historico_id, "
				+ "cf.fecha as fecha_registro "
				+ "from public.xxcm_cf cf "
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
	private void asignarParametroAlcance(PreparedStatement statement, AlcanceUsuarioCambiosFisicos alcanceUsuario) throws Exception {
		if (!alcanceUsuario.consultaGlobal) {
			statement.setString(1, alcanceUsuario.valorFiltro);
		}
	}

	/**
	 * Registra en evidencia el resultado de validar el minimo de registros para duplicados de Cambios Fisicos.
	 * Indica si el flujo puede continuar sin duplicar o cuantos registros faltan para preparar datos.
	 *
	 * @param resultado resultado de conteo y registro de referencia del usuario.
	 *
	 * @author Michelle Ceja
	 * @date 2026-07-27
	 */
	private void registrarResumenValidacion(ResultadoValidacionDuplicadosCambiosFisicos resultado) {
		testCase.addBoldTextEvidenceCurrentStep("Validacion de registros para performance Cambios Fisicos");
		testCase.addTextEvidenceCurrentStep("Usuario: " + resultado.alcanceUsuario.usuario
				+ " | Perfil: " + resultado.alcanceUsuario.perfil
				+ " | Filtro: " + resultado.alcanceUsuario.tipoFiltro
				+ " | Valor: " + resultado.alcanceUsuario.valorFiltro
				+ " | Total registros: " + resultado.totalRegistros
				+ " | Minimo requerido: " + resultado.minimoRegistros
				+ " | Cumple minimo: " + resultado.cumpleMinimo
				+ " | Registros faltantes: " + resultado.registrosFaltantes
				+ " | ID referencia xxcm_cf: " + resultado.idCambioFisicoReferencia
				+ " | ID referencia historico: " + resultado.idHistoricoReferencia
				+ " | Fecha registro referencia: " + resultado.fechaRegistroReferencia);
		System.out.println("[DUPLICADOS CF] Usuario: " + resultado.alcanceUsuario.usuario
				+ " | Perfil: " + resultado.alcanceUsuario.perfil
				+ " | Filtro: " + resultado.alcanceUsuario.tipoFiltro
				+ " | Valor: " + resultado.alcanceUsuario.valorFiltro
				+ " | Total registros: " + resultado.totalRegistros
				+ " | Minimo requerido: " + resultado.minimoRegistros
				+ " | Cumple minimo: " + resultado.cumpleMinimo
				+ " | Registros faltantes: " + resultado.registrosFaltantes
				+ " | ID referencia xxcm_cf: " + resultado.idCambioFisicoReferencia
				+ " | ID referencia historico: " + resultado.idHistoricoReferencia
				+ " | Fecha registro referencia: " + resultado.fechaRegistroReferencia);
	}

	/**
	 * Genera los duplicados reales necesarios para alcanzar el minimo de registros de Cambios Fisicos.
	 * Inserta duplicados en xxcm_historico_correos, resincroniza xxcm_cf y devuelve los IDs para limpieza.
	 *
	 * @param usuario usuario de Portal COMETA que define el alcance de datos.
	 * @param minimoRegistros minimo final requerido para la prueba de performance.
	 * @return resultado posterior a insertar los duplicados faltantes y sincronizar xxcm_cf.
	 * @throws Exception si falla la insercion, sincronizacion o validacion del minimo requerido.
	 *
	 * @author Michelle Ceja
	 * @date 2026-07-27
	 */
	public ResultadoValidacionDuplicadosCambiosFisicos generarDuplicadosHastaMinimo(String usuario, int minimoRegistros) throws Exception {
		try (Connection connection = abrirConexionCometa()) {
			AlcanceUsuarioCambiosFisicos alcanceUsuario = obtenerAlcanceUsuario(connection, usuario);
			long totalInicial = obtenerTotalRegistrosPorAlcance(connection, alcanceUsuario);
			ResumenRangoMensualCambiosFisicos resumenMesRecienteInicial = obtenerResumenMesReciente(connection, alcanceUsuario);
			ResultadoValidacionDuplicadosCambiosFisicos referenciaInicial = obtenerRegistroMasRecientePorAlcance(
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

			sincronizarCambiosFisicos(connection);
			ResumenRangoMensualCambiosFisicos resumenMesRecienteFinal = obtenerResumenMesReciente(connection, alcanceUsuario);
			long totalFinal = resumenMesRecienteFinal.totalRegistrosMes;
			ResultadoValidacionDuplicadosCambiosFisicos resultadoFinal = new ResultadoValidacionDuplicadosCambiosFisicos(
					referenciaInicial.alcanceUsuario,
					totalFinal,
					referenciaInicial.idCambioFisicoReferencia,
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
			testCase.addCodeEvidenceCurrentStep("Error al generar duplicados reales para Cambios Fisicos: " + exception.getMessage());
			throw new Exception("Error al generar duplicados reales para Cambios Fisicos: " + exception.getMessage());
		}
	}

	/**
	 * Elimina los registros duplicados insertados durante la preparacion de datos para performance.
	 * Ejecuta limpieza de xxcm_historico_correos y resincroniza xxcm_cf para dejar el ambiente consistente.
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
			System.out.println("[CLEANUP DUPLICADOS CF] No hay IDs de duplicados para eliminar. Flujo limpio.");
			return;
		}

		try (Connection connection = abrirConexionCometa()) {
			long totalHistoricoAntes = contarHistoricoCorreos(connection);
			System.out.println("[CLEANUP DUPLICADOS CF] Iniciando eliminacion de " + idsDuplicadosGenerados.size() + " duplicados para usuario " + usuario);

			long eliminados = 0;
			String sql = "DELETE FROM public.xxcm_historico_correos WHERE id = ?";
			try (PreparedStatement statement = connection.prepareStatement(sql)) {
				for (Long idDuplicado : idsDuplicadosGenerados) {
					statement.setLong(1, idDuplicado);
					eliminados += statement.executeUpdate();
				}
			}

			long totalHistoricoDespues = contarHistoricoCorreos(connection);
			System.out.println("[CLEANUP DUPLICADOS CF] Registros eliminados: " + eliminados + " | Total historico antes: " + totalHistoricoAntes
					+ " | Total historico despues: " + totalHistoricoDespues);

			sincronizarCambiosFisicos(connection);
			System.out.println("[CLEANUP DUPLICADOS CF] Sincronizacion completada. Base de datos limpia.");
			System.out.println("[CLEANUP DUPLICADOS CF] Cleanup de duplicados completado: " + eliminados + " registros eliminados.");
		} catch (Exception exception) {
			System.out.println("[CLEANUP DUPLICADOS CF] Error durante eliminacion: " + exception.getMessage());
			exception.printStackTrace();
			throw new Exception("Error al eliminar duplicados generados para usuario " + usuario + ": " + exception.getMessage());
		}
	}

	/**
	 * Cuenta todos los registros existentes en xxcm_historico_correos como informacion de control.
	 * El conteo permite observar en consola el efecto de insercion y limpieza de duplicados.
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
	 * Ejecuta el Paso 5 de sincronizadores de las tablas de Cambios Fisicos.
	 * Primero realiza truncate sobre xxcm_cf y despues sincroniza historico correos con las tablas proyectadas.
	 *
	 * @param connection conexion activa a BD COMETA.
	 * @throws Exception si falla el truncate o el procedimiento de sincronizacion.
	 *
	 * @author Michelle Ceja
	 * @date 2026-07-27
	 */
	private void sincronizarCambiosFisicos(Connection connection) throws Exception {
		String queryTruncateCambiosFisicos = "TRUNCATE TABLE public.xxcm_cf CASCADE;";
		String querySincronizadorCambiosFisicos = "CALL public.sincronizar_datos_cambiosfisicos();";

		try (PreparedStatement truncateStatement = connection.prepareStatement(queryTruncateCambiosFisicos)) {
			truncateStatement.executeUpdate();
		}
		System.out.println("[DUPLICADOS CF] Paso 5.1 realizar truncate a la tabla | " + queryTruncateCambiosFisicos + " ejecutado");

		try (PreparedStatement callStatement = connection.prepareStatement(querySincronizadorCambiosFisicos)) {
			callStatement.executeUpdate();
		}
		System.out.println("[DUPLICADOS CF] Paso 5.2 sincronizador de la historico correos a las tablas proyectadas | " + querySincronizadorCambiosFisicos + " ejecutado");
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
	private void registrarResumenDuplicacionReal(ResultadoValidacionDuplicadosCambiosFisicos referenciaAntes,
			ResultadoValidacionDuplicadosCambiosFisicos resultadoDespues, long duplicadosInsertados) {
		long incremento = resultadoDespues.totalRegistros - referenciaAntes.totalRegistros;
		String resumen = "Duplicacion real Cambios Fisicos | Usuario: " + resultadoDespues.alcanceUsuario.usuario
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
		System.out.println("[DUPLICADOS CF] " + resumen);
	}

	/**
	 * Escribe mensajes de seguimiento de duplicados en consola y evidencia textual.
	 * Facilita validar paso a paso la insercion, sincronizacion y limpieza durante la prueba.
	 *
	 * @param etapa nombre corto de la etapa ejecutada.
	 * @param detalle detalle de la operacion o resultado observado.
	 *
	 * @author Michelle Ceja
	 * @date 2026-07-27
	 */
	private void registrarLog(String etapa, String detalle) {
		String mensaje = etapa + " | " + detalle;
		System.out.println("[DUPLICADOS CF] " + mensaje);
		testCase.addTextEvidenceCurrentStep(mensaje);
	}

	/**
	 * Obtiene un rango mensual de fechas desde BD usando el campo fecha de xxcm_cf.
	 * Reutiliza el mismo alcance que se usa para obtener los IDs de referencia de Cambios Fisicos.
	 *
	 * @param usuario usuario de Portal COMETA usado para resolver perfil y llaves de alcance.
	 * @return mapa con fechaInicio, fechaFin, totalRegistros, perfil, filtroAplicado y valorFiltro obtenidos desde BD.
	 * @throws Exception si no existe un rango con registros o si falla la consulta.
	 *
	 * @author Michelle Ceja
	 * @date 2026-07-27
	 */
	public HashMap<String, String> obtenerRangoFechasCambiosFisicosDesdeBD(String usuario) throws Exception {
		try (Connection connection = abrirConexionCometa()) {
			AlcanceUsuarioCambiosFisicos alcanceUsuario = obtenerAlcanceUsuario(connection, usuario);
			String sql = obtenerCteCambiosFisicos(alcanceUsuario)
					+ ", cte_rango AS( "
					+ "select "
					+ "date_trunc('month', fecha_registro)::date as mes, "
					+ "min(fecha_registro::date)::text as fecha_inicio, "
					+ "max(fecha_registro::date)::text as fecha_fin, "
					+ "count(*) as total_registros "
					+ "from cte_data "
					+ "where fecha_registro is not null "
					+ "group by date_trunc('month', fecha_registro)::date "
					+ "order by max(fecha_registro::date) desc "
					+ "limit 1 "
					+ ") "
					+ "select fecha_inicio, fecha_fin, total_registros from cte_rango";

			try (PreparedStatement statement = connection.prepareStatement(sql)) {
				asignarParametroAlcance(statement, alcanceUsuario);
				try (ResultSet resultSet = statement.executeQuery()) {
					if (!resultSet.next()) {
						throw new Exception("No existe un rango mensual de fecha con registros en Cambios Fisicos para el usuario " + usuario);
					}

					HashMap<String, String> rangoFechas = new HashMap<>();
					rangoFechas.put("fechaInicio", resultSet.getString("fecha_inicio"));
					rangoFechas.put("fechaFin", resultSet.getString("fecha_fin"));
					rangoFechas.put("totalRegistros", resultSet.getString("total_registros"));
					rangoFechas.put("perfil", alcanceUsuario.perfil);
					rangoFechas.put("filtroAplicado", alcanceUsuario.tipoFiltro);
					rangoFechas.put("valorFiltro", alcanceUsuario.valorFiltro);

					testCase.addBoldTextEvidenceCurrentStep("Rango obtenido desde BD para Cambios Fisicos");
					testCase.addTextEvidenceCurrentStep("Usuario: " + usuario
							+ " | Perfil: " + alcanceUsuario.perfil
							+ " | Filtro aplicado: " + alcanceUsuario.tipoFiltro
							+ " | Valor filtro: " + alcanceUsuario.valorFiltro
							+ " | Fecha Inicio: " + rangoFechas.get("fechaInicio")
							+ " | Fecha Fin: " + rangoFechas.get("fechaFin")
							+ " | Total registros BD: " + rangoFechas.get("totalRegistros"));
					System.out.println("[BD CAMBIOS FISICOS] Usuario: " + usuario
							+ " | Perfil: " + alcanceUsuario.perfil
							+ " | Filtro aplicado: " + alcanceUsuario.tipoFiltro
							+ " | Valor filtro: " + alcanceUsuario.valorFiltro
							+ " | Fecha Inicio: " + rangoFechas.get("fechaInicio")
							+ " | Fecha Fin: " + rangoFechas.get("fechaFin")
							+ " | Total registros BD: " + rangoFechas.get("totalRegistros"));

					return rangoFechas;
				}
			}
		} catch (Exception exception) {
			testCase.addCodeEvidenceCurrentStep("Error al obtener rango de fechas de Cambios Fisicos desde BD: " + exception.getMessage());
			throw new Exception("Error al obtener rango de fechas de Cambios Fisicos desde BD: " + exception.getMessage());
		}
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
	private ResumenRangoMensualCambiosFisicos obtenerResumenMesReciente(Connection connection,
			AlcanceUsuarioCambiosFisicos alcanceUsuario) throws Exception {
		try {
			String sql = obtenerCteCambiosFisicos(alcanceUsuario)
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
						throw new Exception("No existe un mes reciente con registros en Cambios Fisicos para el alcance consultado");
					}
					return new ResumenRangoMensualCambiosFisicos(
							resultSet.getDate("fecha_inicio").toString(),
							resultSet.getDate("fecha_fin").toString(),
							resultSet.getLong("total_registros"));
				}
			}
		} catch (Exception exception) {
			throw new Exception("Error al obtener resumen de mes reciente de Cambios Fisicos: " + exception.getMessage());
		}
	}

	/**
	 * Representa el resumen del mes mas reciente con registros de Cambios Fisicos.
	 * Conserva fechas de inicio/fin y total de registros para alinear la precondicion con la UI.
	 *
	 * @author Michelle Ceja
	 * @date 2026-07-28
	 */
	private static class ResumenRangoMensualCambiosFisicos {
		public final String fechaInicio;
		public final String fechaFin;
		public final long totalRegistrosMes;

		/**
		 * Inicializa un resumen del mes reciente para Cambios Fisicos.
		 *
		 * @param fechaInicio fecha minima del mes reciente.
		 * @param fechaFin fecha maxima del mes reciente.
		 * @param totalRegistrosMes total de registros contenidos en ese mes.
		 *
		 * @author Michelle Ceja
		 * @date 2026-07-28
		 */
		public ResumenRangoMensualCambiosFisicos(String fechaInicio, String fechaFin, long totalRegistrosMes) {
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
