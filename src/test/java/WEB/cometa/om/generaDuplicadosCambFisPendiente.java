package WEB.cometa.om;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import WEB.util.GlobalVariables;
import modelo.TestCase;

public class generaDuplicadosCambFisPendiente {

	private final TestCase testCase;

	/**
	 * Inicializa la utilidad de preparacion de datos para duplicados de Cambios Fisicos Pendientes.
	 * La clase consulta BD, genera duplicados controlados y registra evidencia para el script.
	 *
	 * @param testCase caso de prueba usado para registrar evidencia.
	 *
	 * @author Michelle Ceja
	 * @date 2026-07-28
	 */
	public generaDuplicadosCambFisPendiente(TestCase testCase) {
		this.testCase = testCase;
	}

	public static class AlcanceUsuarioCambFisPendiente {
		public final String usuario;
		public final String perfil;
		public final String tipoFiltro;
		public final String valorFiltro;
		public final boolean consultaGlobal;

		/**
		 * Conserva el alcance de datos configurado para el usuario de Cambios Fisicos Pendientes.
		 * Define si la consulta se ejecuta global o filtrada por plaza, RFC o supplierId.
		 *
		 * @param usuario usuario de Portal COMETA.
		 * @param perfil perfil obtenido desde PCPERFIL.
		 * @param tipoFiltro llave aplicada: GLOBAL, PCPLAZA, PCRFC o PCSUPPLIERID.
		 * @param valorFiltro valor usado para filtrar registros cuando aplica.
		 * @param consultaGlobal true cuando el usuario ve todos los registros.
		 *
		 * @author Michelle Ceja
		 * @date 2026-07-28
		 */
		public AlcanceUsuarioCambFisPendiente(String usuario, String perfil, String tipoFiltro, String valorFiltro,
				boolean consultaGlobal) {
			this.usuario = usuario;
			this.perfil = perfil;
			this.tipoFiltro = tipoFiltro;
			this.valorFiltro = valorFiltro;
			this.consultaGlobal = consultaGlobal;
		}
	}

	public static class ResultadoValidacionDuplicadosCambFisPendiente {
		public final AlcanceUsuarioCambFisPendiente alcanceUsuario;
		public final long totalRegistros;
		public final String idReferencia;
		public final Timestamp fechaRegistroReferencia;
		public final int minimoRegistros;
		public final boolean cumpleMinimo;
		public final long registrosFaltantes;
		public final List<String> idsDuplicadosGenerados;

		/**
		 * Conserva el resultado de validar y preparar duplicados de Cambios Fisicos Pendientes.
		 * Incluye total actual, registro de referencia e IDs generados para limpieza posterior.
		 *
		 * @param alcanceUsuario alcance de consulta resuelto para el usuario.
		 * @param totalRegistros total actual de registros para ese alcance.
		 * @param idReferencia id UUID del registro base usado para duplicar.
		 * @param fechaRegistroReferencia fecha del registro base seleccionado.
		 * @param minimoRegistros minimo requerido para la prueba de performance.
		 * @param cumpleMinimo true si el total actual es mayor o igual al minimo requerido.
		 * @param registrosFaltantes cantidad de registros que faltan para llegar al minimo.
		 * @param idsDuplicadosGenerados lista de IDs UUID insertados durante duplicacion.
		 *
		 * @author Michelle Ceja
		 * @date 2026-07-28
		 */
		public ResultadoValidacionDuplicadosCambFisPendiente(AlcanceUsuarioCambFisPendiente alcanceUsuario,
				long totalRegistros, String idReferencia, Timestamp fechaRegistroReferencia, int minimoRegistros,
				boolean cumpleMinimo, long registrosFaltantes, List<String> idsDuplicadosGenerados) {
			this.alcanceUsuario = alcanceUsuario;
			this.totalRegistros = totalRegistros;
			this.idReferencia = idReferencia;
			this.fechaRegistroReferencia = fechaRegistroReferencia;
			this.minimoRegistros = minimoRegistros;
			this.cumpleMinimo = cumpleMinimo;
			this.registrosFaltantes = registrosFaltantes;
			this.idsDuplicadosGenerados = idsDuplicadosGenerados;
		}
	}

	/**
	 * Valida si el usuario cuenta con el minimo de registros de Cambios Fisicos Pendientes.
	 * Resuelve el alcance del usuario y obtiene el registro base para duplicacion.
	 *
	 * @param usuario usuario de Portal COMETA que se validara en BD.
	 * @param minimoRegistros minimo requerido para ejecutar la prueba de performance.
	 * @return resultado con alcance, total de registros y registro de referencia.
	 * @throws Exception si no se puede resolver el alcance o no existe registro de referencia.
	 *
	 * @author Michelle Ceja
	 * @date 2026-07-28
	 */
	public ResultadoValidacionDuplicadosCambFisPendiente validarMinimoRegistrosPorUsuario(String usuario,
			int minimoRegistros) throws Exception {
		try (Connection connection = abrirConexionCometa()) {
			AlcanceUsuarioCambFisPendiente alcanceUsuario = obtenerAlcanceUsuario(connection, usuario);
			long totalRegistros = obtenerTotalRegistrosPorAlcance(connection, alcanceUsuario);
			ResultadoValidacionDuplicadosCambFisPendiente resultado = obtenerRegistroReferenciaPorAlcance(connection,
					alcanceUsuario, totalRegistros, minimoRegistros);

			registrarResumenValidacion(resultado);
			return resultado;
		} catch (Exception exception) {
			testCase.addCodeEvidenceCurrentStep(
					"Error al validar registros para duplicados de Cambios Fisicos Pendientes: " + exception.getMessage());
			throw new Exception(
					"Error al validar registros para duplicados de Cambios Fisicos Pendientes: " + exception.getMessage());
		}
	}

	/**
	 * Genera los duplicados necesarios para alcanzar el minimo de registros de Cambios Fisicos Pendientes.
	 * Duplica directamente en xxcm_cambios_fisicos_pendientes sin depender de historico correos.
	 *
	 * @param usuario usuario de Portal COMETA que define el alcance de datos.
	 * @param minimoRegistros minimo final requerido para la prueba de performance.
	 * @return resultado posterior a insertar duplicados en la tabla de pendientes.
	 * @throws Exception si falla la insercion o la validacion del minimo requerido.
	 *
	 * @author Michelle Ceja
	 * @date 2026-07-28
	 */
	public ResultadoValidacionDuplicadosCambFisPendiente generarDuplicadosHastaMinimo(String usuario, int minimoRegistros)
			throws Exception {
		try (Connection connection = abrirConexionCometa()) {
			AlcanceUsuarioCambFisPendiente alcanceUsuario = obtenerAlcanceUsuario(connection, usuario);
			long totalInicial = obtenerTotalRegistrosPorAlcance(connection, alcanceUsuario);
			ResultadoValidacionDuplicadosCambFisPendiente referenciaInicial = obtenerRegistroReferenciaPorAlcance(
					connection, alcanceUsuario, totalInicial, minimoRegistros);

			registrarLog("Preparacion real de duplicados", "Usuario: " + usuario + " | Perfil: " + alcanceUsuario.perfil
					+ " | Filtro: " + alcanceUsuario.tipoFiltro + " | Valor: " + alcanceUsuario.valorFiltro
					+ " | Total inicial: " + totalInicial + " | Minimo requerido: " + minimoRegistros + " | Faltantes: "
					+ referenciaInicial.registrosFaltantes + " | ID referencia: " + referenciaInicial.idReferencia);

			if (referenciaInicial.cumpleMinimo) {
				registrarLog("Preparacion real de duplicados",
						"El usuario ya cumple el minimo requerido; no se insertan duplicados.");
				return referenciaInicial;
			}

			if (referenciaInicial.registrosFaltantes > Integer.MAX_VALUE) {
				throw new Exception("La cantidad de registros faltantes excede el limite permitido para generate_series: "
						+ referenciaInicial.registrosFaltantes);
			}

			int duplicadosReales = (int) referenciaInicial.registrosFaltantes;
			List<String> idsDuplicadosGenerados = insertarDuplicadosPendientes(connection, referenciaInicial.idReferencia,
					duplicadosReales);

			long totalFinal = obtenerTotalRegistrosPorAlcance(connection, alcanceUsuario);
			ResultadoValidacionDuplicadosCambFisPendiente resultadoFinal = new ResultadoValidacionDuplicadosCambFisPendiente(
					referenciaInicial.alcanceUsuario, totalFinal, referenciaInicial.idReferencia,
					referenciaInicial.fechaRegistroReferencia, minimoRegistros, totalFinal >= minimoRegistros,
					totalFinal >= minimoRegistros ? 0L : minimoRegistros - totalFinal, idsDuplicadosGenerados);

			registrarResumenDuplicacionReal(referenciaInicial, resultadoFinal, idsDuplicadosGenerados.size());
			return resultadoFinal;
		} catch (Exception exception) {
			testCase.addCodeEvidenceCurrentStep(
					"Error al generar duplicados reales para Cambios Fisicos Pendientes: " + exception.getMessage());
			throw new Exception(
					"Error al generar duplicados reales para Cambios Fisicos Pendientes: " + exception.getMessage());
		}
	}

	/**
	 * Elimina los registros duplicados insertados durante la preparacion de datos para performance.
	 * Ejecuta limpieza directa en xxcm_cambios_fisicos_pendientes usando los IDs UUID generados.
	 *
	 * @param usuario usuario para el cual se insertaron duplicados.
	 * @param idsDuplicadosGenerados lista de IDs UUID a eliminar de xxcm_cambios_fisicos_pendientes.
	 * @throws Exception si falla la eliminacion de registros.
	 *
	 * @author Michelle Ceja
	 * @date 2026-07-28
	 */
	public void eliminarDuplicadosGenerados(String usuario, List<String> idsDuplicadosGenerados) throws Exception {
		if (idsDuplicadosGenerados == null || idsDuplicadosGenerados.isEmpty()) {
			System.out.println("[CLEANUP DUPLICADOS CFP] No hay IDs de duplicados para eliminar. Flujo limpio.");
			return;
		}

		try (Connection connection = abrirConexionCometa()) {
			long totalAntes = contarTablaCambFisPendientes(connection);
			System.out.println("[CLEANUP DUPLICADOS CFP] Iniciando eliminacion de " + idsDuplicadosGenerados.size()
					+ " duplicados para usuario " + usuario);

			long eliminados = 0;
			String sql = "DELETE FROM public.xxcm_cambios_fisicos_pendientes WHERE id::text = ?";
			try (PreparedStatement statement = connection.prepareStatement(sql)) {
				for (String idDuplicado : idsDuplicadosGenerados) {
					statement.setString(1, idDuplicado);
					eliminados += statement.executeUpdate();
				}
			}

			long totalDespues = contarTablaCambFisPendientes(connection);
			System.out.println("[CLEANUP DUPLICADOS CFP] Registros eliminados: " + eliminados + " | Total antes: "
					+ totalAntes + " | Total despues: " + totalDespues);
			System.out.println("[CLEANUP DUPLICADOS CFP] Cleanup de duplicados completado.");
		} catch (Exception exception) {
			System.out.println("[CLEANUP DUPLICADOS CFP] Error durante eliminacion: " + exception.getMessage());
			exception.printStackTrace();
			throw new Exception(
					"Error al eliminar duplicados generados de Cambios Fisicos Pendientes para usuario " + usuario + ": "
							+ exception.getMessage());
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
	 * @date 2026-07-28
	 */
	private Connection abrirConexionCometa() throws Exception {
		return DriverManager.getConnection(GlobalVariables.DB_URL_Cometa, GlobalVariables.DB_USER_Cometa,
				GlobalVariables.DB_PASSWORD_Cometa);
	}

	/**
	 * Obtiene el perfil y las llaves de alcance configuradas para el usuario.
	 * ADMIN y OXXO_OS se consideran globales; OXXO_PLAZA filtra por PCPLAZA; los demas priorizan PCRFC y despues PCSUPPLIERID.
	 *
	 * @param connection conexion activa a BD COMETA.
	 * @param usuario usuario de Portal COMETA.
	 * @return alcance resuelto para construir consultas de Cambios Fisicos Pendientes.
	 * @throws Exception si el usuario no tiene perfil o no tiene llave de alcance cuando la requiere.
	 *
	 * @author Michelle Ceja
	 * @date 2026-07-28
	 */
	private AlcanceUsuarioCambFisPendiente obtenerAlcanceUsuario(Connection connection, String usuario) throws Exception {
		String sqlPerfil = "select key_value " + "from xxcm_user_keys " + "where short_name = 'PCPERFIL' "
				+ "and usuario_id = (select usuario_id from xxcm_usuarios where usuario = ?) " + "limit 1";
		String sqlKeys = "select short_name, key_value " + "from xxcm_user_keys "
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
		if ("ADMIN".equals(perfilNormalizado) || "OXXO_OS".equals(perfilNormalizado) || "5555555".equals(usuario)
				|| "3333333".equals(usuario)) {
			return new AlcanceUsuarioCambFisPendiente(usuario, perfil, "GLOBAL", "SIN FILTRO", true);
		}

		if ("OXXO_PLAZA".equals(perfilNormalizado)) {
			if (pcPlaza.isEmpty()) {
				throw new Exception(
						"El usuario " + usuario + " tiene perfil OXXO_PLAZA pero no tiene PCPLAZA configurado");
			}
			return new AlcanceUsuarioCambFisPendiente(usuario, perfil, "PCPLAZA", pcPlaza, false);
		}

		if (!pcrfc.isEmpty()) {
			return new AlcanceUsuarioCambFisPendiente(usuario, perfil, "PCRFC", pcrfc, false);
		}

		if (!pcSupplierId.isEmpty()) {
			return new AlcanceUsuarioCambFisPendiente(usuario, perfil, "PCSUPPLIERID", pcSupplierId, false);
		}

		throw new Exception("No se obtuvo PCRFC, PCSUPPLIERID o PCPLAZA para el usuario " + usuario);
	}

	/**
	 * Cuenta los registros visibles por alcance para Cambios Fisicos Pendientes.
	 * Para usuarios globales consulta la tabla completa; para usuarios filtrados utiliza la funcion paginada.
	 *
	 * @param connection conexion activa a BD COMETA.
	 * @param alcanceUsuario alcance resuelto para el usuario.
	 * @return total de registros existentes para el alcance.
	 * @throws Exception si ocurre un error al ejecutar el conteo.
	 *
	 * @author Michelle Ceja
	 * @date 2026-07-28
	 */
	private long obtenerTotalRegistrosPorAlcance(Connection connection, AlcanceUsuarioCambFisPendiente alcanceUsuario)
			throws Exception {
		if (alcanceUsuario.consultaGlobal) {
			String sqlGlobal = "select count(*) as total_registros from public.xxcm_cambios_fisicos_pendientes";
			try (PreparedStatement statement = connection.prepareStatement(sqlGlobal);
					ResultSet resultSet = statement.executeQuery()) {
				return resultSet.next() ? resultSet.getLong("total_registros") : 0L;
			}
		}

		String sql = obtenerSqlFuncionPendientesConteo(alcanceUsuario);
		try (PreparedStatement statement = connection.prepareStatement(sql)) {
			asignarParametroAlcance(statement, alcanceUsuario);
			try (ResultSet resultSet = statement.executeQuery()) {
				return resultSet.next() ? resultSet.getLong("total_registros") : 0L;
			}
		}
	}

	/**
	 * Obtiene el registro de referencia para duplicar dentro del alcance del usuario.
	 * En usuarios globales toma la tabla base; en usuarios filtrados toma la funcion paginada.
	 *
	 * @param connection conexion activa a BD COMETA.
	 * @param alcanceUsuario alcance resuelto para el usuario.
	 * @param totalRegistros total actual calculado para el alcance.
	 * @param minimoRegistros minimo requerido para la prueba de performance.
	 * @return resultado con el registro de referencia y el estado contra el minimo.
	 * @throws Exception si no existe un registro de referencia para el alcance.
	 *
	 * @author Michelle Ceja
	 * @date 2026-07-28
	 */
	private ResultadoValidacionDuplicadosCambFisPendiente obtenerRegistroReferenciaPorAlcance(Connection connection,
			AlcanceUsuarioCambFisPendiente alcanceUsuario, long totalRegistros, int minimoRegistros) throws Exception {
		String idReferencia = "";
		Timestamp fechaReferencia = null;

		if (alcanceUsuario.consultaGlobal) {
			String sqlGlobal = "select id::text as id_referencia "
					+ "from public.xxcm_cambios_fisicos_pendientes "
					+ "order by created_datetime_business desc nulls last " + "limit 1";
			try (PreparedStatement statement = connection.prepareStatement(sqlGlobal);
					ResultSet resultSet = statement.executeQuery()) {
				if (resultSet.next()) {
					idReferencia = limpiar(resultSet.getString("id_referencia"));
				}
			}
		} else {
			String sql = obtenerSqlFuncionPendientesReferencia(alcanceUsuario);
			try (PreparedStatement statement = connection.prepareStatement(sql)) {
				asignarParametroAlcance(statement, alcanceUsuario);
				try (ResultSet resultSet = statement.executeQuery()) {
					if (resultSet.next()) {
						idReferencia = limpiar(resultSet.getString("id_referencia"));
					}
				}
			}
		}

		if (idReferencia.isEmpty()) {
			throw new Exception(
					"No existe registro de referencia en Cambios Fisicos Pendientes para el usuario " + alcanceUsuario.usuario);
		}

		boolean cumpleMinimo = totalRegistros >= minimoRegistros;
		long registrosFaltantes = cumpleMinimo ? 0L : minimoRegistros - totalRegistros;
		return new ResultadoValidacionDuplicadosCambFisPendiente(alcanceUsuario, totalRegistros, idReferencia,
				fechaReferencia, minimoRegistros, cumpleMinimo, registrosFaltantes, new ArrayList<>());
	}

	/**
	 * Inserta duplicados en xxcm_cambios_fisicos_pendientes a partir de un registro de referencia.
	 * Genera nuevos IDs UUID con gen_random_uuid y devuelve la lista para cleanup posterior.
	 *
	 * @param connection conexion activa a BD COMETA.
	 * @param idReferencia id UUID del registro que sera duplicado.
	 * @param cantidadDuplicados cantidad de registros a insertar.
	 * @return lista de IDs UUID insertados.
	 * @throws Exception si falla la insercion de duplicados.
	 *
	 * @author Michelle Ceja
	 * @date 2026-07-28
	 */
	private List<String> insertarDuplicadosPendientes(Connection connection, String idReferencia, int cantidadDuplicados)
			throws Exception {
		String sql = "insert into xxcm_cambios_fisicos_pendientes " + "select " + "gen_random_uuid() as id, "
				+ "remision_number, " + "created_datetime_business, " + "quantity_items, " + "total_amount, "
				+ "cr_place, " + "cr_store, " + "cd_nmb_spr, " + "sku, " + "description, " + "quantity_sku, "
				+ "quantity_done, " + "quantity_pending, " + "estatus, "
				+ "coalesce(created_date, current_timestamp), "
				+ "coalesce(modified_date, current_timestamp) "
				+ "from public.xxcm_cambios_fisicos_pendientes "
				+ "cross join generate_series(1, ?::integer) gs " + "where id::text = ? " + "returning id::text";

		List<String> idsGenerados = new ArrayList<>();
		try (PreparedStatement statement = connection.prepareStatement(sql)) {
			statement.setInt(1, cantidadDuplicados);
			statement.setString(2, idReferencia);
			try (ResultSet resultSet = statement.executeQuery()) {
				while (resultSet.next()) {
					idsGenerados.add(resultSet.getString("id"));
				}
			}
		}

		if (idsGenerados.size() != cantidadDuplicados) {
			throw new Exception("No se insertaron todos los duplicados solicitados. Esperados: " + cantidadDuplicados
					+ " | Insertados: " + idsGenerados.size());
		}

		registrarLog("Duplicidad real tabla pendientes", "ID referencia: " + idReferencia + " | Duplicados solicitados: "
				+ cantidadDuplicados + " | Duplicados insertados: " + idsGenerados.size());
		return idsGenerados;
	}

	/**
	 * Construye el SQL de conteo para usuarios filtrados usando la funcion paginada de pendientes.
	 * El conteo usa page_size grande para aproximar el universo completo del filtro del usuario.
	 *
	 * @param alcanceUsuario alcance resuelto para el usuario.
	 * @return consulta SQL para contar registros por alcance filtrado.
	 *
	 * @author Michelle Ceja
	 * @date 2026-07-28
	 */
	private String obtenerSqlFuncionPendientesConteo(AlcanceUsuarioCambFisPendiente alcanceUsuario) {
		if ("PCRFC".equals(alcanceUsuario.tipoFiltro)) {
			return "select count(*) as total_registros "
					+ "from public.get_cambios_fisicos_directos_pendientes_paginated(" + "1, " + "2147483647, "
					+ "array[?], " + "null, " + "null, " + "null, " + "null, " + "null, " + "null, " + "null, "
					+ "null, " + "null, " + "null, " + "null)";
		}

		if ("PCSUPPLIERID".equals(alcanceUsuario.tipoFiltro)) {
			return "select count(*) as total_registros "
					+ "from public.get_cambios_fisicos_directos_pendientes_paginated(" + "1, " + "2147483647, "
					+ "null, " + "array[?::bigint], " + "null, " + "null, " + "null, " + "null, " + "null, "
					+ "null, " + "null, " + "null, " + "null, " + "null)";
		}

		return "select count(*) as total_registros "
				+ "from public.get_cambios_fisicos_directos_pendientes_paginated(" + "1, " + "2147483647, "
				+ "null, " + "null, " + "null, " + "null, " + "null, " + "null, " + "array[?], " + "null, "
				+ "null, " + "null, " + "null, " + "null)";
	}

	/**
	 * Construye el SQL para obtener un registro de referencia en usuarios filtrados.
	 * Devuelve el id UUID y created_date del primer registro visible por el alcance.
	 *
	 * @param alcanceUsuario alcance resuelto para el usuario.
	 * @return consulta SQL para extraer el registro base de duplicacion.
	 *
	 * @author Michelle Ceja
	 * @date 2026-07-28
	 */
	private String obtenerSqlFuncionPendientesReferencia(AlcanceUsuarioCambFisPendiente alcanceUsuario) {
		if ("PCRFC".equals(alcanceUsuario.tipoFiltro)) {
			return "select id::text as id_referencia "
					+ "from public.get_cambios_fisicos_directos_pendientes_paginated(" + "1, " + "1, " + "array[?], "
					+ "null, " + "null, " + "null, " + "null, " + "null, " + "null, " + "null, " + "null, "
					+ "null, " + "null, " + "null)";
		}

		if ("PCSUPPLIERID".equals(alcanceUsuario.tipoFiltro)) {
			return "select id::text as id_referencia "
					+ "from public.get_cambios_fisicos_directos_pendientes_paginated(" + "1, " + "1, " + "null, "
					+ "array[?::bigint], " + "null, " + "null, " + "null, " + "null, " + "null, " + "null, "
					+ "null, " + "null, " + "null, " + "null)";
		}

		return "select id::text as id_referencia "
				+ "from public.get_cambios_fisicos_directos_pendientes_paginated(" + "1, " + "1, " + "null, "
				+ "null, " + "null, " + "null, " + "null, " + "null, " + "array[?], " + "null, " + "null, "
				+ "null, " + "null, " + "null)";
	}

	/**
	 * Asigna el parametro de alcance a una consulta preparada cuando el usuario no es global.
	 * Mantiene la misma posicion de parametro para consultas de conteo y de referencia.
	 *
	 * @param statement consulta preparada que requiere el filtro de alcance.
	 * @param alcanceUsuario alcance resuelto para el usuario.
	 * @throws Exception si falla la asignacion del parametro.
	 *
	 * @author Michelle Ceja
	 * @date 2026-07-28
	 */
	private void asignarParametroAlcance(PreparedStatement statement, AlcanceUsuarioCambFisPendiente alcanceUsuario)
			throws Exception {
		statement.setString(1, alcanceUsuario.valorFiltro);
	}

	/**
	 * Cuenta registros de la tabla xxcm_cambios_fisicos_pendientes para control de limpieza.
	 * Se usa solo para evidenciar el estado antes y despues de eliminar duplicados.
	 *
	 * @param connection conexion activa a BD COMETA.
	 * @return total de registros de xxcm_cambios_fisicos_pendientes.
	 * @throws Exception si falla la consulta.
	 *
	 * @author Michelle Ceja
	 * @date 2026-07-28
	 */
	private long contarTablaCambFisPendientes(Connection connection) throws Exception {
		String sql = "select count(*) as total from public.xxcm_cambios_fisicos_pendientes";
		try (PreparedStatement statement = connection.prepareStatement(sql); ResultSet resultSet = statement.executeQuery()) {
			return resultSet.next() ? resultSet.getLong("total") : 0L;
		}
	}

	/**
	 * Registra en evidencia el resultado de validar el minimo de registros para duplicados.
	 * Indica si el flujo puede continuar sin duplicar o cuantos registros faltan para la preparacion.
	 *
	 * @param resultado resultado de conteo y referencia por alcance del usuario.
	 *
	 * @author Michelle Ceja
	 * @date 2026-07-28
	 */
	private void registrarResumenValidacion(ResultadoValidacionDuplicadosCambFisPendiente resultado) {
		testCase.addBoldTextEvidenceCurrentStep("Validacion de registros para performance Cambios Fisicos Pendientes");
		testCase.addTextEvidenceCurrentStep("Usuario: " + resultado.alcanceUsuario.usuario + " | Perfil: "
				+ resultado.alcanceUsuario.perfil + " | Filtro: " + resultado.alcanceUsuario.tipoFiltro + " | Valor: "
				+ resultado.alcanceUsuario.valorFiltro + " | Total registros: " + resultado.totalRegistros
				+ " | Minimo requerido: " + resultado.minimoRegistros + " | Cumple minimo: " + resultado.cumpleMinimo
				+ " | Registros faltantes: " + resultado.registrosFaltantes + " | ID referencia: "
				+ resultado.idReferencia + " | Fecha referencia: " + resultado.fechaRegistroReferencia);
		System.out.println("[DUPLICADOS CFP] Usuario: " + resultado.alcanceUsuario.usuario + " | Perfil: "
				+ resultado.alcanceUsuario.perfil + " | Filtro: " + resultado.alcanceUsuario.tipoFiltro + " | Valor: "
				+ resultado.alcanceUsuario.valorFiltro + " | Total registros: " + resultado.totalRegistros
				+ " | Minimo requerido: " + resultado.minimoRegistros + " | Cumple minimo: " + resultado.cumpleMinimo
				+ " | Registros faltantes: " + resultado.registrosFaltantes + " | ID referencia: "
				+ resultado.idReferencia + " | Fecha referencia: " + resultado.fechaRegistroReferencia);
	}

	/**
	 * Registra en consola y evidencia el resumen de la generacion real de duplicados.
	 * Confirma el incremento obtenido y si el alcance del usuario ya cumple el minimo requerido.
	 *
	 * @param referenciaAntes resultado base antes de insertar duplicados.
	 * @param resultadoDespues resultado posterior a insertar duplicados.
	 * @param duplicadosInsertados cantidad de duplicados insertados.
	 *
	 * @author Michelle Ceja
	 * @date 2026-07-28
	 */
	private void registrarResumenDuplicacionReal(ResultadoValidacionDuplicadosCambFisPendiente referenciaAntes,
			ResultadoValidacionDuplicadosCambFisPendiente resultadoDespues, int duplicadosInsertados) {
		long incremento = resultadoDespues.totalRegistros - referenciaAntes.totalRegistros;
		String resumen = "Duplicacion real Cambios Fisicos Pendientes | Usuario: " + resultadoDespues.alcanceUsuario.usuario
				+ " | Filtro: " + resultadoDespues.alcanceUsuario.tipoFiltro + " | Valor: "
				+ resultadoDespues.alcanceUsuario.valorFiltro + " | Total antes: " + referenciaAntes.totalRegistros
				+ " | Duplicados insertados: " + duplicadosInsertados + " | Total despues: "
				+ resultadoDespues.totalRegistros + " | Incremento observado: " + incremento + " | Minimo requerido: "
				+ resultadoDespues.minimoRegistros + " | Cumple minimo: " + resultadoDespues.cumpleMinimo
				+ " | Faltantes actuales: " + resultadoDespues.registrosFaltantes;
		testCase.addBoldTextEvidenceCurrentStep("Duplicacion real de registros ejecutada");
		testCase.addTextEvidenceCurrentStep(resumen);
		System.out.println("[DUPLICADOS CFP] " + resumen);
	}

	/**
	 * Escribe mensajes de seguimiento de duplicados en consola y evidencia textual.
	 * Facilita validar paso a paso la preparacion, duplicacion y limpieza de datos.
	 *
	 * @param etapa nombre corto de la etapa ejecutada.
	 * @param detalle detalle de la operacion o resultado observado.
	 *
	 * @author Michelle Ceja
	 * @date 2026-07-28
	 */
	private void registrarLog(String etapa, String detalle) {
		String mensaje = etapa + " | " + detalle;
		System.out.println("[DUPLICADOS CFP] " + mensaje);
		testCase.addTextEvidenceCurrentStep(mensaje);
	}

	/**
	 * Normaliza cadenas nulas para evitar validaciones con espacios o null.
	 * Devuelve una cadena vacia cuando el valor recibido no existe.
	 *
	 * @param valor texto original obtenido desde BD.
	 * @return texto limpio sin espacios laterales o cadena vacia.
	 *
	 * @author Michelle Ceja
	 * @date 2026-07-28
	 */
	private String limpiar(String valor) {
		return valor == null ? "" : valor.trim();
	}

	/**
	 * Aplica en UI los filtros requeridos para consultar Cambios Fisicos Pendientes segun el alcance del usuario.
	 * Para alcance GLOBAL selecciona multiples opciones de Proveedor; para PCPLAZA selecciona la plaza obtenida por query;
	 * para alcances RFC o SupplierId selecciona proveedor obtenido desde BD.
	 *
	 * @param usuario usuario con el que se ejecuta el caso.
	 * @param pc4 instancia de PortalCometaMVP4 usada para interactuar con los filtros de la pantalla.
	 * @param validacionRegistros resultado de precondicion que contiene el alcance resuelto.
	 * @param screenshot true para registrar evidencia visual de los filtros aplicados.
	 * @return descripcion del filtro aplicado para documentar evidencia en el script.
	 * @throws Exception si no es posible aplicar el filtro correspondiente al alcance.
	 *
	 * @author Michelle Ceja
	 * @date 2026-07-28
	 */
	public String aplicarFiltrosNecesariosParaConsulta(String usuario, PortalCometaMVP4 pc4,
			ResultadoValidacionDuplicadosCambFisPendiente validacionRegistros, boolean screenshot) throws Exception {
		if (pc4 == null) {
			throw new Exception("No se puede aplicar filtros porque la instancia de PortalCometaMVP4 es null");
		}
		if (validacionRegistros == null || validacionRegistros.alcanceUsuario == null) {
			throw new Exception("No se puede aplicar filtros porque no existe resultado de validacion de alcance");
		}

		String tipoFiltro = limpiar(validacionRegistros.alcanceUsuario.tipoFiltro).toUpperCase(Locale.ROOT);
		boolean resultado;
		String descripcionFiltro;

		if ("GLOBAL".equals(tipoFiltro)) {
			resultado = pc4.selectListaProveedoryValidarMultipleSelección();
			descripcionFiltro = "Proveedor con seleccion multiple para perfil global (ADMIN/OXXO_OS)";
		} else if ("PCPLAZA".equals(tipoFiltro)) {
			boolean plazaObtenidaDesdeBd = pc4.obtenerPlazaCambiosFisicosPendientes(screenshot, usuario);
			resultado = seleccionarPlazaCfPendientesCompatible(pc4, screenshot, plazaObtenidaDesdeBd);
			descripcionFiltro = plazaObtenidaDesdeBd
					? "Plaza obtenida desde BD y seleccionada en el filtro Plaza"
					: "Plaza seleccionada desde opciones visibles de UI (fallback por alcance PCPLAZA)";
		} else {
			resultado = pc4.obtenerTndyProveedorCambiosFisicosPendientes(screenshot, usuario)
					&& pc4.selectProveedorBd();
			descripcionFiltro = "Proveedor obtenido desde BD para alcance RFC/SupplierId";
		}

		if (!resultado) {
			throw new Exception("No fue posible aplicar el filtro requerido para el alcance " + tipoFiltro
					+ " del usuario " + usuario);
		}

		registrarLog("Filtro aplicado en Cambios Fisicos Pendientes",
				"Usuario: " + usuario + " | Tipo filtro: " + tipoFiltro + " | Detalle: " + descripcionFiltro);
		return descripcionFiltro;
	}

	/**
	 * Ejecuta la seleccion de Plaza en CF Pendientes con compatibilidad entre versiones de PortalCometaMVP4.
	 * Intenta metodos con firma (boolean) y sin parametros para evitar fallos cuando cambia el nombre del metodo.
	 *
	 * @param pc4 instancia activa de PortalCometaMVP4.
	 * @param screenshot bandera de evidencia.
	 * @return true si algun metodo de seleccion de plaza se ejecuta exitosamente.
	 * @throws Exception si no existe un metodo compatible o todos retornan false.
	 *
	 * @author Michelle Ceja
	 * @date 2026-07-28
	 */
	private boolean seleccionarPlazaCfPendientesCompatible(PortalCometaMVP4 pc4, boolean screenshot,
			boolean usarMetodoDependienteDeBd) throws Exception {
		String[] metodosConBoolean = new String[] {
			"selectFiltroPlazaCFPendientesActuales"
		};

		if (usarMetodoDependienteDeBd) {
			for (String nombreMetodo : metodosConBoolean) {
				try {
					Method metodo = pc4.getClass().getMethod(nombreMetodo, boolean.class);
					Object resultado = metodo.invoke(pc4, screenshot);
					if (Boolean.TRUE.equals(resultado)) {
						registrarLog("Seleccion Plaza CF Pendientes", "Metodo ejecutado: " + nombreMetodo + "(boolean)");
						return true;
					}
				} catch (NoSuchMethodException ignored) {
					// Se intenta el siguiente metodo compatible.
				} catch (Exception exception) {
					registrarLog("Seleccion Plaza CF Pendientes",
							"Fallo metodo " + nombreMetodo + "(boolean): " + exception.getMessage());
				}
			}
		}

		String[] metodosSinParametros = new String[] {
			"selectListaPlaza",
			"selectCrPlaza"
		};

		for (String nombreMetodo : metodosSinParametros) {
			try {
				Method metodo = pc4.getClass().getMethod(nombreMetodo);
				Object resultado = metodo.invoke(pc4);
				if (Boolean.TRUE.equals(resultado)) {
					registrarLog("Seleccion Plaza CF Pendientes", "Metodo ejecutado: " + nombreMetodo + "()");
					return true;
				}
			} catch (NoSuchMethodException ignored) {
				// Se intenta el siguiente metodo compatible.
			} catch (Exception exception) {
				registrarLog("Seleccion Plaza CF Pendientes",
						"Fallo metodo " + nombreMetodo + "(): " + exception.getMessage());
			}
		}

		throw new Exception("No existe metodo compatible para seleccionar Plaza en CF Pendientes en PortalCometaMVP4");
	}
}
