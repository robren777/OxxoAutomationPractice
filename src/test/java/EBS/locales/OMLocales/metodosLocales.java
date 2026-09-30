package EBS.locales.OMLocales;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import EBS.util.pageObjectModel.EBSLogin;
import modelo.TestCase;
import utils.selenium.SeleniumUtil;

public class metodosLocales {
    SeleniumUtil u;
    TestCase testCase;
    EBSLogin inicio;
    String url;
    String userName;
    String password;
    private static WebDriver driver;
    //Variables globales
    By getCarpetaHelpDesk = By.xpath("//*[contains(text(),'MXOXXO-HELP-DESK')]");
    By appUsuarios = By.xpath("//*[@id=\"LIMXOXXO-HELP-DESK\"]/a/div[2]");
    By carpetaResponsabilidad = By.xpath("//*[contains(text(),'Administrador de Sistema')]");
    By carpetaApp = By.xpath("//div[contains(@class, 'textdivresp') and contains(text(), 'Aplicaci')]");
    By carpetaAccion = By.xpath("//div[contains(@class, 'textdivresp') and contains(text(), 'Validaci')]");
    By appValoresAdminSystem = By.xpath("//div[contains(@class, 'textdiv') and contains(text(), 'Valores')]");
    By frecuenciaFacturacion = By.xpath("//*[@id=\"LIMXOXXO-AR10MON-TRANSACCIONES\"]/a");
    //Variables de frameset
    //Variables de Botones
    By btncrearGrupo = By.xpath("//*[@id=\"btnCreateNet\"]");
    By btnBuscarTipoServicio = By.xpath("//*[@id=\"lovTipoServicio__xc_1\"]/a/img");
    By btnAplicarCambios = By.xpath("//*[@id=\"btnApply\"]");
    By btnConsultarGrupos = By.xpath("//*[@id=\"OADialogPopupRN\"]/span[2]/table/tbody/tr/td[3]/button");
    By tipoServicio = By.xpath("//*[@id=\"lovTipoServicio\"]");
    By btnTipoServicioIrFrame = By.xpath("//*[@id=\"_LOVResFrm\"]/table[1]/tbody/tr[4]/td/table/tbody/tr/td/div/div/button");
    By btnRadioButonLocal = By.xpath("//*[@id=\"N1:N8:0\"]");
    By btnSeleccionarTipoServicio = By.xpath("//*[@id=\"LovServiciosNetRN\"]/div/div[1]/table/tbody/tr/td[2]/table/tbody/tr/td[3]/button");
    By btnApagarCerrarSesion = By.xpath("//*[@id='_oafOraBodyContainer']/table/tbody/tr[1]/td[2]/table/tbody/tr/td[2]/a/div/img");
    // Variables inputs
    By inputGrupoEjecucion = By.xpath("//*[@id=\"grupoEjecucion\"]");
    By inputBusquedaGrupoEjecucion = By.xpath("//*[@id=\"Grupo\"]");
    //Variables de Leyendas
    By leyendaGrupos = By.xpath("//*[@id=\"p_SwanPageLayout\"]/div[1]/div[1]/table/tbody/tr/td/h1");
    By leyendaCrearGrupos = By.xpath("//*[@id=\"p_SwanPageLayout\"]/div[1]/div[1]/table/tbody/tr/td[1]/h1");
    By msjErrorCreacionDeGrupo = By.xpath("//*[@id='oafusercontent']/div/div/table/tbody/tr[1]/td/h1");
    By mjsErrorContactarAdministrador = By.xpath("//*[@id='oafusercontent']/div/div/table/tbody/tr[2]/td");
    By msjErrorCreacionItem=By.xpath("//*[@id='FwkErrorBeanId']/tbody/tr/td/table/tbody/tr/td/div[1]/div/table/tbody/tr/td[3]/table/tbody/tr/td/h1");
    By msjErrorServicioItem=By.xpath("//*[@id='FwkErrorBeanId']/tbody/tr/td/table/tbody/tr/td/div[3]");

    public metodosLocales(SeleniumUtil u, TestCase testCase, String url, String userName, String password) {
        super();
        this.u = u;
        this.testCase = testCase;
        this.url = url;
        this.userName = userName;
        this.password = password;
        inicio = new EBSLogin(u, testCase, url, userName, password);
    }

    /**
     * Metodo para hacer login en la pagina web de EBS con su captura de pantalla
     *
     * @author David Quiroz Rodriguez
     */
    public void LoginIEEBS() {
        inicio.navigate(url);
        u.hardWait(5);
        testCase.addScreenShotCurrentStep("Cargando Pagina Login");
        inicio.setLanguage();
        u.hardWait(5);
        testCase.addScreenShotCurrentStep("Seleccionar idioma");
        inicio.setCredentials();
        testCase.addScreenShotCurrentStep("Ingresar usuario y clave ");
        inicio.clickLogIn();
    }

    /**
     * Metodo para seleccionar el nombre de una carpeta o link de la pagina EBS
     *
     * @param valueName
     * @author David Quiroz Rodriguez
     */
    public void theUserSelectsTheValueOfTheEBSPage(String valueName) {
        By objetoAEncontrarPorNombre = By.xpath("//*[contains(text(),'" + valueName + "')]");
        u.highLight(objetoAEncontrarPorNombre);
        testCase.addScreenShotCurrentStep("El usuario da click en " + valueName);
        u.unhighLight(objetoAEncontrarPorNombre);
        u.click(objetoAEncontrarPorNombre);
        u.waitForLoadPage();
    }

    /**
     * Metodo para seleccionar la aplicacion Usuarios en la carpeta indicada
     *
     * @param carpetaName
     * @author David Quiroz Rodriguez
     */
    public void theUserSelectTheAppJavaUsuarios(String carpetaName) {
        u.highLight(appUsuarios);
        testCase.addScreenShotCurrentStep("El usuario da click en la aplicacion Usuarios de la carpeta" + carpetaName);
        u.unhighLight(appUsuarios);
        u.click(appUsuarios);
    }

    /**
     * Metodo para seleccionar la aplicacion valores del apartado de la carpeta Administacion de valores.
     *
     * @author David Quiroz Rodriguez
     */
    public void theUserSelectTheAppJavaValores() {
        u.click(carpetaResponsabilidad);
        u.hardWait(7);
        u.click(carpetaApp);
        u.hardWait(7);
        u.click(carpetaAccion);
        u.hardWait(7);
        u.highLight(appValoresAdminSystem);
        testCase.addScreenShotCurrentStep("El usuario da click en la aplicacion Valores de la carpeta Administracion de Sistemas");
        u.unhighLight(appValoresAdminSystem);
        u.click(appValoresAdminSystem);
    }

    //Metodos para la creacion de grupos de ejecucion y exclucion

    /**
     * Metodo para seleccionar el apartado de frecuencia de facturación de la carpeta AR10MON
     *
     * @author David Quiroz Rodriguez
     */
    public void theUserSelectTheLinkFrecuenciaFacturacion() {
        u.highLight(frecuenciaFacturacion);
        u.hardWait(2);
        u.unhighLight(frecuenciaFacturacion);
        testCase.addScreenShotCurrentStep("Clic en facturacion");
        u.click(frecuenciaFacturacion);
    }

    /**
     * Metodo para esperar la etiqueta de grupo de proveedores
     *
     * @author David Quiroz Rodriguez
     */
    public void theUserWaitsLeyendaProveedores() {
        u.highLight(leyendaGrupos);
        testCase.addScreenShotCurrentStep("Se muestra Pantalla Grupos de Proveedores");
        u.unhighLight(leyendaGrupos);
    }

    /**
     * Metodo para seleccionar el boton de crear grupo en el apartado de creacion de grupos de ejecucion
     *
     * @author David Quiroz Rodriguez
     */
    public void theUserSelectBotonCrearGrupo() {
        u.highLight(btncrearGrupo);
        testCase.addScreenShotCurrentStep("Se selecciona el boton crear grupo");
        u.unhighLight(btncrearGrupo);
        u.click(btncrearGrupo);
    }

    /**
     * Metodo para esperar la leyenda de crear grupos
     *
     * @author David Quiroz Rodriguez
     */
    public void theUserWaitsLeyendaCrearGrupo() {
        u.highLight(leyendaCrearGrupos);
        testCase.addScreenShotCurrentStep("Se muestra apartado Crear Grupo Ejecucion");
        u.unhighLight(leyendaCrearGrupos);
    }

    /**
     * Metodo para enviar texto al campo de grupo de ejecucion
     *
     * @param value
     * @author David Quiroz Rodriguez
     */
    public void theUserSentTextInGrupoEjecucion(String value) {
        u.sendKeys(inputGrupoEjecucion, value);
        testCase.addScreenShotCurrentStep("Se ingresa " + value + " en el campo Grupo Ejecucion");
    }

    /**
     * Metodo para que el usuario seleccione el tipo de servicio en el apartado de busqueda de tipo de servicio
     *
     * @author David Quiroz Rodriguez
     */
    public void theUserSelectsTipoServicio() {
        u.hardWait(3);
        u.click(btnBuscarTipoServicio);
    }

    //Actualizacion de Metodos para creacion de grupos locales

    /**
     * Metodo para validacion de mensaje de error en la creacion de grupo
     * @param grupoEjecucion
     * @author David Quiroz Rodriguez
     */
    public void mensajeErrorCreacionDeGrupo(String grupoEjecucion) {
        System.out.println("Se muestra error en la pagina porque ya existe ese grupo creado");
        u.highLight(msjErrorCreacionDeGrupo);
        testCase.addScreenShotCurrentStep("Mensaje de error");
        u.highLight(mjsErrorContactarAdministrador);
        testCase.addTextEvidenceCurrentStep("Se muestra el mensaje de error ya que el grupo de ejecuion " + grupoEjecucion + " a crear ya existe");
    }

    /**
     * Metodo para validacion de error en la asignacion de servicio o proveedor
     * @param numeroProveedorOServicio
     * @author David Quiroz Rodriguez
     */
    public void mensajeErrorCrearItem(String numeroProveedorOServicio){
        u.highLight(msjErrorCreacionItem);
        u.highLight(msjErrorServicioItem);
        testCase.addScreenShotCurrentStep("Mensajes de error al crear item");
        testCase.addTextEvidenceCurrentStep("El servicio o proveedor "+ numeroProveedorOServicio +" ya se encuentra asignado al grupo de ejecucion");
        System.out.println("El de servicio o proveedor "+ numeroProveedorOServicio +" ya se encuentra asignado al grupo de ejecucion");

    }

    /**
     * Metodo para cerrar sesion en EBS cuando se muestra el boton de apagado
     * @author David Quiroz Rodriguez
     */
    public void cerrarSesionEnBotonApagado() {
        u.highLight(btnApagarCerrarSesion);
        testCase.addScreenShotCurrentStep("Cerrar login de EBS");
        u.click(btnApagarCerrarSesion);
        u.hardWait(5);
        testCase.addScreenShotCurrentStep("Pagina inicio");
    }
}
