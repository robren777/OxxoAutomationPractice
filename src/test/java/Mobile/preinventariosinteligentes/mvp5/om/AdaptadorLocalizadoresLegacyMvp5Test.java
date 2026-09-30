package Mobile.preinventariosinteligentes.mvp5.om;

import java.util.Collections;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;

import Mobile.preinventariosinteligentes.mvp5.om
        .AdaptadorLocalizadoresLegacyMvp5.Resultado;

/** Pruebas unitarias del puente de localizadores exclusivo de MVP5. */
public class AdaptadorLocalizadoresLegacyMvp5Test {
    private static final String RELEASE =
            "com.femco.oxxo.operaciones.mitienda";
    private static final String DEBUG =
            AdaptadorLocalizadoresLegacyMvp5.PAQUETE_DEBUG;

    @Test
    public void adaptaIdsXpathYCamposHeredadosARelease() {
        OmPrueba om = new OmPrueba();

        Resultado<OmPrueba> resultado =
                AdaptadorLocalizadoresLegacyMvp5.adaptar(om, RELEASE);

        Assert.assertSame(resultado.om(), om);
        Assert.assertEquals(resultado.idsAdaptados(), 2);
        Assert.assertEquals(resultado.xpathAdaptados(), 1);
        Assert.assertTrue(resultado.noSoportados().isEmpty());
        Assert.assertEquals(valor(om.id), RELEASE + ":id/btn_wkl_continue");
        Assert.assertEquals(
                valor(((OmBase) om).idHeredado),
                RELEASE + ":id/etUserName");
        Assert.assertFalse(valor(om.xpath).contains(DEBUG));
        Assert.assertEquals(valor(om.android), "android:id/button1");
        Assert.assertNull(om.nulo);
    }

    @Test
    public void conservaFlavorDebugCuandoEsElObjetivo() {
        OmPrueba om = new OmPrueba();

        Resultado<OmPrueba> resultado =
                AdaptadorLocalizadoresLegacyMvp5.adaptar(om, DEBUG);

        Assert.assertEquals(valor(resultado.om().id),
                DEBUG + ":id/btn_wkl_continue");
        Assert.assertEquals(resultado.idsAdaptados(), 2);
        Assert.assertEquals(resultado.xpathAdaptados(), 1);
    }

    @Test(expectedExceptions = IllegalStateException.class,
            expectedExceptionsMessageRegExp =
                    ".*conserva localizadores debug no adaptables.*")
    public void fallaTempranoConEstrategiaNoSoportadaEnRelease() {
        AdaptadorLocalizadoresLegacyMvp5.adaptar(
                new OmNoSoportado(),
                RELEASE);
    }

    private static String valor(By localizador) {
        return String.valueOf(
                ((By.Remotable) localizador)
                        .getRemoteParameters().value());
    }

    private static class OmBase {
        private By idHeredado = By.id(DEBUG + ":id/etUserName");
    }

    private static final class OmPrueba extends OmBase {
        private By id = By.id(DEBUG + ":id/btn_wkl_continue");
        private By xpath = By.xpath(
                "//*[@resource-id='" + DEBUG + ":id/uno']"
                        + "//*[@resource-id='" + DEBUG + ":id/dos']");
        private By android = By.id("android:id/button1");
        private By nulo;
    }

    private static final class OmNoSoportado {
        private By personalizado = new By() {
            @Override
            public List<WebElement> findElements(SearchContext contexto) {
                return Collections.emptyList();
            }

            @Override
            public String toString() {
                return "By.personalizado: " + DEBUG + ":id/sin_soporte";
            }
        };
    }
}
