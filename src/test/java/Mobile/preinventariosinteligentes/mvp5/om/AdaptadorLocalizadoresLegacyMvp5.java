package Mobile.preinventariosinteligentes.mvp5.om;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.openqa.selenium.By;

/**
 * Adapta en memoria los localizadores de una instancia legacy usada por MVP5.
 * No modifica las clases legacy ni afecta las instancias creadas por sus casos.
 */
final class AdaptadorLocalizadoresLegacyMvp5 {
    static final String PAQUETE_DEBUG =
            "com.femco.oxxo.operaciones.mitienda.debug";

    private AdaptadorLocalizadoresLegacyMvp5() {
    }

    static <T> Resultado<T> adaptar(T omLegacy, String paqueteObjetivo) {
        if (omLegacy == null) {
            throw new IllegalArgumentException("El OM legacy no puede ser null");
        }
        if (paqueteObjetivo == null || paqueteObjetivo.trim().isEmpty()) {
            throw new IllegalArgumentException(
                    "El appPackage objetivo no puede estar vacio");
        }

        Auditoria auditoria = new Auditoria(
                omLegacy.getClass().getSimpleName(),
                paqueteObjetivo.trim());
        Class<?> tipo = omLegacy.getClass();
        while (tipo != null && tipo != Object.class) {
            adaptarCamposDeclarados(omLegacy, tipo, auditoria);
            tipo = tipo.getSuperclass();
        }
        auditoria.validarCompatibilidadRelease();
        return auditoria.resultado(omLegacy);
    }

    private static void adaptarCamposDeclarados(
            Object omLegacy,
            Class<?> tipo,
            Auditoria auditoria) {
        for (Field campo : tipo.getDeclaredFields()) {
            if (!By.class.isAssignableFrom(campo.getType())
                    || Modifier.isStatic(campo.getModifiers())) {
                continue;
            }
            boolean accesible = campo.canAccess(omLegacy);
            try {
                campo.setAccessible(true);
                By original = (By) campo.get(omLegacy);
                By adaptado = adaptarLocalizador(
                        original,
                        tipo.getSimpleName() + "." + campo.getName(),
                        auditoria);
                if (adaptado != original) {
                    campo.set(omLegacy, adaptado);
                }
            } catch (IllegalAccessException exception) {
                throw new IllegalStateException(
                        "No fue posible adaptar el localizador "
                                + tipo.getSimpleName() + "." + campo.getName(),
                        exception);
            } finally {
                campo.setAccessible(accesible);
            }
        }
    }

    private static By adaptarLocalizador(
            By original,
            String nombreCampo,
            Auditoria auditoria) {
        if (original == null) {
            return null;
        }
        if (!(original instanceof By.Remotable)) {
            auditoria.registrarNoSoportadoSiAplica(
                    nombreCampo,
                    original.toString());
            return original;
        }

        By.Remotable.Parameters parametros =
                ((By.Remotable) original).getRemoteParameters();
        Object valorCrudo = parametros.value();
        if (!(valorCrudo instanceof String)) {
            auditoria.registrarNoSoportadoSiAplica(
                    nombreCampo,
                    String.valueOf(valorCrudo));
            return original;
        }
        String valor = (String) valorCrudo;
        if (!valor.contains(PAQUETE_DEBUG)) {
            return original;
        }

        String valorAdaptado = valor.replace(
                PAQUETE_DEBUG,
                auditoria.paqueteObjetivo);
        if ("id".equals(parametros.using())) {
            auditoria.idsAdaptados++;
            return By.id(valorAdaptado);
        }
        if ("xpath".equals(parametros.using())) {
            auditoria.xpathAdaptados++;
            return By.xpath(valorAdaptado);
        }

        auditoria.noSoportados.add(
                nombreCampo + "[" + parametros.using() + "]");
        return original;
    }

    static final class Resultado<T> {
        private final T om;
        private final String nombreOm;
        private final String paqueteObjetivo;
        private final int idsAdaptados;
        private final int xpathAdaptados;
        private final List<String> noSoportados;

        private Resultado(
                T om,
                String nombreOm,
                String paqueteObjetivo,
                int idsAdaptados,
                int xpathAdaptados,
                List<String> noSoportados) {
            this.om = om;
            this.nombreOm = nombreOm;
            this.paqueteObjetivo = paqueteObjetivo;
            this.idsAdaptados = idsAdaptados;
            this.xpathAdaptados = xpathAdaptados;
            this.noSoportados = Collections.unmodifiableList(
                    new ArrayList<>(noSoportados));
        }

        T om() {
            return om;
        }

        int idsAdaptados() {
            return idsAdaptados;
        }

        int xpathAdaptados() {
            return xpathAdaptados;
        }

        List<String> noSoportados() {
            return noSoportados;
        }

        String evidencia() {
            return "OM_LEGACY=" + nombreOm
                    + "|APP_PACKAGE=" + paqueteObjetivo
                    + "|IDS_ADAPTADOS=" + idsAdaptados
                    + "|XPATH_ADAPTADOS=" + xpathAdaptados
                    + "|NO_SOPORTADOS=" + noSoportados.size();
        }
    }

    private static final class Auditoria {
        private final String nombreOm;
        private final String paqueteObjetivo;
        private final List<String> noSoportados = new ArrayList<>();
        private int idsAdaptados;
        private int xpathAdaptados;

        private Auditoria(String nombreOm, String paqueteObjetivo) {
            this.nombreOm = nombreOm;
            this.paqueteObjetivo = paqueteObjetivo;
        }

        private void registrarNoSoportadoSiAplica(
                String nombreCampo,
                String valor) {
            if (valor != null && valor.contains(PAQUETE_DEBUG)) {
                noSoportados.add(nombreCampo);
            }
        }

        private void validarCompatibilidadRelease() {
            if (!PAQUETE_DEBUG.equals(paqueteObjetivo)
                    && !noSoportados.isEmpty()) {
                throw new IllegalStateException(
                        "El OM " + nombreOm
                                + " conserva localizadores debug no adaptables: "
                                + String.join(", ", noSoportados));
            }
        }

        private <T> Resultado<T> resultado(T om) {
            return new Resultado<>(
                    om,
                    nombreOm,
                    paqueteObjetivo,
                    idsAdaptados,
                    xpathAdaptados,
                    noSoportados);
        }
    }
}
