package WEB.cometa.om;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import WEB.util.GlobalVariables;
import modelo.TestCase;

/**
 * Helper para generar y eliminar duplicados en xxcm_historico_correos durante pruebas
 * de Devoluciones Especiales que requieren un minimo de registros en el grid.
 * Siempre llamar eliminarDuplicados() en @AfterMethod para garantizar limpieza.
 *
 * @author Alicia Ceja
 * @date Agosto 2026
 */
public class generaDuplicadosDevEsp {

	private final TestCase testCase;
	// IDs insertados en esta sesion; se usan para la limpieza posterior.
	private final List<Long> idsGenerados = new ArrayList<>();

	public generaDuplicadosDevEsp(TestCase testCase) {
		this.testCase = testCase;
	}

	/**
	 * Obtiene el SUPPLIERID del usuario desde xxcm_user_keys.
	 * Retorna null si el usuario es Admin (sin restriccion de proveedor).
	 *
	 * @param usuario numero de usuario a consultar.
	 * @return supplierId como Long, o null si no existe.
	 */
	public Long obtenerSupplierIdPorUsuario(String usuario) {
		String sql = "SELECT key_value FROM xxcm_user_keys "
				+ "WHERE short_name = 'PCSUPPLIERID' "
				+ "AND usuario_id = (SELECT usuario_id FROM xxcm_usuarios WHERE usuario = ?)";
		try (Connection conn = getConnection();
				PreparedStatement ps = conn.prepareStatement(sql)) {
			ps.setString(1, usuario);
			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {
					String val = rs.getString("key_value");
					return (val != null && !val.trim().isEmpty()) ? Long.parseLong(val.trim()) : null;
				}
			}
		} catch (Exception e) {
			System.out.println("[DUPLICADOS DEV ESP] Error al obtener supplierId para " + usuario + ": " + e.getMessage());
		}
		return null;
	}

	/**
	 * Cuenta los registros del grid de Devoluciones Especiales visibles para el usuario.
	 * Usa filtro de proveedor para CEDIS; sin filtro para Admin.
	 *
	 * @param usuario numero de usuario a consultar.
	 * @return total de registros disponibles en el grid.
	 */
	public int contarRegistrosGrid(String usuario) {
		Long supplierId = obtenerSupplierIdPorUsuario(usuario);
		String supplierParam = supplierId != null ? "ARRAY[" + supplierId + "]::bigint[]" : "NULL";
		String sql = "SELECT COUNT(*) FROM public.get_xxcm_cedis_de_proyeccion_grid_paginated("
				+ "1, 2000000, " + supplierParam + ", NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL"
				+ ") AS p INNER JOIN xxcm_historico_correos h ON p.id = h.id";
		try (Connection conn = getConnection();
				PreparedStatement ps = conn.prepareStatement(sql);
				ResultSet rs = ps.executeQuery()) {
			if (rs.next()) {
				int total = rs.getInt(1);
				System.out.println("[DUPLICADOS DEV ESP] Registros actuales en grid para usuario " + usuario + ": " + total);
				return total;
			}
		} catch (Exception e) {
			System.out.println("[DUPLICADOS DEV ESP] Error al contar registros para " + usuario + ": " + e.getMessage());
			testCase.addCodeEvidenceCurrentStep("Error al contar registros del grid en BD: " + e.getMessage());
		}
		return 0;
	}

	/**
	 * Genera N duplicados del registro mas reciente del grid visible para el usuario.
	 * Los IDs insertados quedan registrados internamente para su eliminacion con eliminarDuplicados().
	 *
	 * @param usuario   numero de usuario que determina el filtro de proveedor.
	 * @param cantidad  numero de duplicados a generar.
	 * @return true si se generaron los duplicados correctamente.
	 */
	public boolean generarDuplicados(String usuario, int cantidad) {
		Long supplierId = obtenerSupplierIdPorUsuario(usuario);
		String supplierParam = supplierId != null ? "ARRAY[" + supplierId + "]::bigint[]" : "NULL";

		String sqlIdFuente = "SELECT h.id FROM public.get_xxcm_cedis_de_proyeccion_grid_paginated("
				+ "1, 2000000, " + supplierParam + ", NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL"
				+ ") AS p INNER JOIN xxcm_historico_correos h ON p.id = h.id ORDER BY h.id DESC LIMIT 1";

		try (Connection conn = getConnection()) {
			long idFuente;
			try (PreparedStatement ps = conn.prepareStatement(sqlIdFuente);
					ResultSet rs = ps.executeQuery()) {
				if (!rs.next()) {
					System.out.println("[DUPLICADOS DEV ESP] No se encontro registro fuente para duplicar.");
					testCase.addCodeEvidenceCurrentStep("No se encontro registro fuente para generar duplicados.");
					return false;
				}
				idFuente = rs.getLong("id");
			}
			System.out.println("[DUPLICADOS DEV ESP] Registro fuente id=" + idFuente + " | cantidad a generar=" + cantidad);

			String sqlInsert = "INSERT INTO public.xxcm_historico_correos "
					+ "SELECT (SELECT MAX(id) FROM xxcm_historico_correos) + gs, "
					+ "cr_plaza, cr_tienda, body, estatus, transferencia, origen, "
					+ "created_at, updated_at, id_retek, id_retek_assigned_at, "
					+ "is_visible, version_documento, fecha_registro, suppliable_id, "
					+ "ruta_original, ruta_propuesta, data_optimized_at "
					+ "FROM public.xxcm_historico_correos "
					+ "CROSS JOIN generate_series(1, ?) gs "
					+ "WHERE id = ? "
					+ "RETURNING id";

			try (PreparedStatement ps = conn.prepareStatement(sqlInsert)) {
				ps.setInt(1, cantidad);
				ps.setLong(2, idFuente);
				try (ResultSet rs = ps.executeQuery()) {
					while (rs.next()) {
						idsGenerados.add(rs.getLong("id"));
					}
				}
			}

			boolean exito = idsGenerados.size() == cantidad;
			System.out.println("[DUPLICADOS DEV ESP] IDs generados: " + idsGenerados);
			testCase.addTextEvidenceCurrentStep("Duplicados generados en BD: " + idsGenerados.size());
			return exito;
		} catch (Exception e) {
			System.out.println("[DUPLICADOS DEV ESP] Error al generar duplicados: " + e.getMessage());
			testCase.addCodeEvidenceCurrentStep("Error al generar duplicados en BD: " + e.getMessage());
			return false;
		}
	}

	/**
	 * Llama al procedimiento almacenado cedis_de_sincronizar_datos() para que
	 * los duplicados queden visibles en el grid de Devoluciones Especiales.
	 *
	 * @return true si el procedimiento se ejecuto sin errores.
	 */
	public boolean sincronizarDatos() {
		try (Connection conn = getConnection();
				PreparedStatement ps = conn.prepareStatement("CALL cedis_de_sincronizar_datos()")) {
			ps.execute();
			System.out.println("[DUPLICADOS DEV ESP] cedis_de_sincronizar_datos() ejecutado correctamente.");
			return true;
		} catch (Exception e) {
			System.out.println("[DUPLICADOS DEV ESP] Error al sincronizar: " + e.getMessage());
			testCase.addCodeEvidenceCurrentStep("Error al ejecutar cedis_de_sincronizar_datos(): " + e.getMessage());
			return false;
		}
	}

	/**
	 * Elimina todos los duplicados generados en esta sesion desde xxcm_historico_correos.
	 * Debe llamarse siempre en @AfterMethod, independientemente del resultado del test.
	 *
	 * @return true si la eliminacion se completo sin errores.
	 */
	public boolean eliminarDuplicados() {		if (idsGenerados.isEmpty()) {
			System.out.println("[DUPLICADOS DEV ESP] No hay duplicados registrados para eliminar.");
			return true;
		}
		String sqlDelete = "DELETE FROM xxcm_historico_correos WHERE id = ?";
		try (Connection conn = getConnection();
				PreparedStatement ps = conn.prepareStatement(sqlDelete)) {
			for (Long id : idsGenerados) {
				ps.setLong(1, id);
				ps.addBatch();
			}
			ps.executeBatch();
			System.out.println("[DUPLICADOS DEV ESP] Duplicados eliminados: " + idsGenerados);
			idsGenerados.clear();
			return true;
		} catch (Exception e) {
			System.out.println("[DUPLICADOS DEV ESP] Error al eliminar duplicados: " + e.getMessage());
			return false;
		}
	}

	private Connection getConnection() throws Exception {
		return DriverManager.getConnection(
				GlobalVariables.DB_URL_Cometa,
				GlobalVariables.DB_USER_Cometa,
				GlobalVariables.DB_PASSWORD_Cometa);
	}

	/**
	 * Cuenta los registros del grid y genera duplicados si no se alcanza el minimo requerido.
	 * Encapsula la logica de precondicion para que los scripts solo realicen la llamada.
	 *
	 * @param usuario            usuario del perfil en prueba.
	 * @param minimoRegistrosStr valor del campo minimoRegistros del data provider; si es null o vacio usa 21.
	 * @return true si el minimo ya esta cubierto o si los duplicados se generaron y sincronizaron correctamente.
	 */
	public boolean prepararMinimoRegistros(String usuario, String minimoRegistrosStr) {
		int minimoRegistros = 21;
		try {
			if (minimoRegistrosStr != null && !minimoRegistrosStr.trim().isEmpty()) {
				minimoRegistros = Integer.parseInt(minimoRegistrosStr.trim());
			}
		} catch (NumberFormatException ignore) {}

		int registrosActuales = contarRegistrosGrid(usuario);
		testCase.addTextEvidenceCurrentStep("Registros en el grid de Devoluciones Especiales: " + registrosActuales + " | Minimo requerido: " + minimoRegistros);

		int faltantes = Math.max(0, minimoRegistros - registrosActuales);
		if (faltantes == 0) {
			System.out.println("[DUPLICADOS DEV ESP] Minimo alcanzado (" + registrosActuales + " >= " + minimoRegistros + "). No se generan duplicados.");
			return true;
		}

		boolean resultado = generarDuplicados(usuario, faltantes) && sincronizarDatos();
		if (!resultado) {
			testCase.addCodeEvidenceCurrentStep("No fue posible generar los duplicados para alcanzar " + minimoRegistros + " registros.");
		}
		return resultado;
	}
}
