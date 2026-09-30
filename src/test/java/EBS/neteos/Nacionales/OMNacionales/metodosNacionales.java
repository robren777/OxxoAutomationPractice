package EBS.neteos.Nacionales.OMNacionales;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import EBS.util.locales.Sikuli.CommonMethodsSikuli;
import modelo.TestCase;
import utils.selenium.SeleniumUtil;

public class metodosNacionales {
    SeleniumUtil u;
    TestCase testCase;
    //Frames
    By frame = By.xpath("//frameset[@title='Content']/frame");
    //VariablesGlobalesNacionales
    By carpetaMXOXXO_GL17ADM_SERVICIOS_ADTIVO_OS = By.xpath("//*[contains(text(),'MXOXXO-GL17ADM-SERVICIOS_ADTIVO_OS')]");
    By apartadoWebFrecuencia_Facturacion_SN_Corresponsalias = By.xpath("//*[@id=\"LIMXOXXO-GL17ADM-SERVICIOS_ADTIVO_OS\"]/a");
    By tipoServicio = By.xpath("//*[@id='lovTipoServicio']");
    By btnAplicar = By.xpath("//*[@id='btnApply']");
    By btnBuscarGrupos = By.xpath("//*[@id='SearchGpoNet']");
    By btnBusquedaVerItem = By.xpath("//*[@id='N8:imgBuscarItems1:0']/img");
    By btnCrear = By.xpath("//*[@id='btnCreateNetItem']");
    By btnActualizarItem = By.xpath("//*[@id='tblItemsNetRN:Content']/tbody/tr/td[5]/a/img");
    By btnBorrarGrpEjecCorresponsaliasYServNacionales = By.xpath("//*[@id='resultGruposRN1:Content']/tbody/tr/td[7]/a/img");
    By btnEliminarItem = By.xpath("//*[@id='tblItemsNetRN:Content']/tbody/tr/td[6]/a/img");
    By btnBusquedaTipoServicio = By.xpath("//*[@id='lovTipoServicio__xc_1']/a/img");
    By txtProveedor = By.xpath("//*[@id='lovProv']");
    By btnBusquedaProveedor = By.xpath("//*[@id='lovProv__xc_1']/a/img");
    By txtServicio = By.xpath("//*[@id='lovItem']");
    By btnBusquedaServicio = By.xpath("//*[@id='lovItem__xc_1']/a/img");
    By txtSeleccionarHora = By.xpath("//*[@id='msgTxtHrEjec']");
    By btnCrearItemGuardarDatos = By.xpath("//*[@id='btnApplyItem']");
    By btnGruposItems = By.xpath("//*[@id='btnGruposItem']");
    By btnDesconectarse = By.xpath("//*[@id='globalHeaderID']/tbody/tr/td[13]/a/div/img");
    By btnOKActualizacionItem = By.xpath("//*[@id='OADialogPopupRN']/span[2]/table/tbody/tr/td[2]/button");
    By btnSiEliminarGrupo = By.xpath("//*[@id='OADialogPopupRN']/span[2]/table/tbody/tr/td[3]/button");
    By btnLimpiar = By.xpath("//*[@id='ClearNet']");
    //Seccion creacion de grupos
    By msjConfirmacionCreacionDeGruposCorresponsaliasYNacionales = By.xpath("//*[@id='OADialogPopupRN']/div/div/table/tbody/tr/td[3]/table/tbody/tr/td/h1");
    By btnConsultarGrupos = By.xpath("//*[@id='OADialogPopupRN']/span[2]/table/tbody/tr/td[3]/button");
    //Seccion Mensajes para Nacionales
    By msjConfirmacionCreacionGrupo = By.xpath("//*[@id='FwkErrorBeanId']/tbody/tr/td/table/tbody/tr/td/div[1]/div/table/tbody/tr/td[3]/table/tbody/tr/td/h1");
    By msjConfirmacionCreacionItem = By.xpath("//*[@id='FwkErrorBeanId']/tbody/tr/td/table/tbody/tr/td/div[3]");
    By msjConfirmacionActualizacionItem = By.xpath("//*[@id='OADialogPopupRN']/div/div/table/tbody/tr/td[3]/table/tbody/tr/td/h1");
    By msjAdvertenciaElimiacionDeGrupo = By.xpath("//*[@id='OADialogPopupRN']/div/div/table/tbody/tr/td[3]/table/tbody/tr/td/h1");
    By msjGeneralDeProcesoEliminacionDeGrupo = By.xpath("//*[@id='FwkErrorBeanId']/tbody/tr/td/table/tbody/tr/td");
    By msjEliminacionDeGrupoCorrectamente = By.xpath("//*[@id='FwkErrorBeanId']/tbody/tr/td/table/tbody/tr/td/ol/li[2]");
    //SeccionGrupoEjecucion
    By btnGrupoEjecucion = By.xpath("//*[@id='Grupo__xc_1']/a/img");
    By txtGrupoEjecucion = By.xpath("//*[@id='Grupo']");
    By btnIR = By.xpath("//*[@id='_LOVResFrm']/table[1]/tbody/tr[4]/td/table/tbody/tr/td/div/div/button");
    By btnRadioButtonGrpEje = By.xpath("//*[@id='LovGruposNetVO1:Content']/tbody/tr[2]/td[1]/span/input");
    By btnSeleccionarGrpEje = By.xpath("//*[@id='LovGruposNetRN']/div/div[1]/table/tbody/tr/td[2]/table/tbody/tr/td[3]/button");
    //SeccionTipoServicio
    By txtTipoServicio = By.xpath("//*[@id='Servicio']");
    By btnTipoServicio = By.xpath("//*[@id='Servicio__xc_1']/a/img");
    By btnRadioButtonServiciosNacionales = By.xpath("//*[@id='tblTipoServiciosRN:Content']/tbody/tr[2]/td[1]/span/input");
    By btnRadiobuttonCorresponsalias = By.xpath("//*[@id='tblTipoServiciosRN:Content']/tbody/tr[2]/td[1]/span/input");
    By btnSeleccionarTipoServicio = By.xpath("//*[@id='LovServiciosNetRN']/div/div[1]/table/tbody/tr/td[2]/table/tbody/tr/td[3]/button");
    //SeccionNumerosDeServicio
    By btnRadioButtonNumeroServicio = By.xpath("//*[@id='tblItemServiciosNetRN:Content']/tbody/tr[2]/td[1]/span/input");
    By btnSeleccionarNumeroServicio = By.xpath("//*[@id='LovItemsNetRN']/div/div[1]/table/tbody/tr/td[2]/table/tbody/tr/td[3]/button");
    //SeccionNumerosDeServicio
    By btnRadioButtonNumeroProveedor = By.xpath("//*[@id='tblProveedoresRN:Content']/tbody/tr[2]/td[1]/span/input");
    By btnSeleccionarNumeroProveedor = By.xpath("//*[@id='LovProveedoresNetRN']/div/div[1]/table/tbody/tr/td[2]/table/tbody/tr/td[3]/button");

    public metodosNacionales(SeleniumUtil u, TestCase testCase) {
        super();
        this.u = u;
        this.testCase = testCase;
    }

    /**
     * Metodo para abrir la carpeta MXOXXO_GL17ADM_SERVICIOS_ADTIVO_OS en la pagina de EBS
     *
     * @param nombreCarpeta Author David Quiroz Rodriguez
     */
    public void abrirCarpetaGL17ADM(String nombreCarpeta) {
        u.highLight(carpetaMXOXXO_GL17ADM_SERVICIOS_ADTIVO_OS);
        u.hardWait(2);
        testCase.addScreenShotCurrentStep("El usuario da click en " + nombreCarpeta);
        u.click(carpetaMXOXXO_GL17ADM_SERVICIOS_ADTIVO_OS);
        u.unhighLight(carpetaMXOXXO_GL17ADM_SERVICIOS_ADTIVO_OS);
        u.waitForLoadPage();
    }

    /**
     * Metodo para abrir la aplicacion web Frecuencia_Facturacion_SN_Corresponsalias de la carpeta padre MXOXXO_GL17ADM_SERVICIOS_ADTIVO_OS en la pagina de EBS
     *
     * @Author David Quiroz Rodriguez
     */
    public void openFrecuencia_Facturacion() {
        u.highLight(apartadoWebFrecuencia_Facturacion_SN_Corresponsalias);
        u.hardWait(2);
        u.unhighLight(apartadoWebFrecuencia_Facturacion_SN_Corresponsalias);
        testCase.addScreenShotCurrentStep("Clic en facturacion");
        u.click(apartadoWebFrecuencia_Facturacion_SN_Corresponsalias);
        u.waitForLoadPage();
    }

    /**
     * Metodo para dar click en el boton aplicar en la pagina de EBS
     *
     * @Author David Quiroz Rodriguez
     */
    public void theUserClickBtnAplicar() {
        u.highLight(btnAplicar);
        testCase.addScreenShotCurrentStep("El usuario da click en boton Aplicar");
        u.unhighLight(btnAplicar);
        u.click(btnAplicar);
    }

    /**
     * Metodo para buscar y seleccionar el grupo de ejecucion en la pagina de EBS
     *
     * @param grupoEjecucion
     * @Author David Quiroz Rodriguez
     */
    public void buscarYseleccionarGrupoEjecucion(String grupoEjecucion) {
        u.sendKeys(txtGrupoEjecucion, grupoEjecucion);
        testCase.addScreenShotCurrentStep("Captura de grupo de ejecucion");
        u.click(btnGrupoEjecucion);
        u.hardWait(5);
        //Cambiar a nueva pestaña
        Object[] windowHandlers = u.getDriver().getWindowHandles().toArray();
        u.getDriver().switchTo().window((String) windowHandlers[1]);
        u.getDriver().switchTo().frame(0);
        u.click(btnIR);
        u.hardWait(5);
        u.getDriver().switchTo().window((String) windowHandlers[1]);
        u.getDriver().switchTo().frame(0);
        testCase.addScreenShotCurrentStep("Dar click radio button");
        u.click(btnRadioButtonGrpEje);
        testCase.addScreenShotCurrentStep("Dar click en boton seleccionar");
        u.highLight(btnSeleccionarGrpEje);
        u.click(btnSeleccionarGrpEje);
        u.getDriver().switchTo().window((String) windowHandlers[0]);
    }

    /**
     * Metodo para buscar y seleccionar tipo de servicio en la seccion de Nacionales
     *
     * @param tipoServicio
     * @Author David Quiroz Rodriguez
     */
    public void buscarYSeleccionarTipoServicioNacionales(String tipoServicio) {
        Object[] windowHandlers2 = u.getDriver().getWindowHandles().toArray();
        u.getDriver().switchTo().window((String) windowHandlers2[0]);
        u.hardWait(5);
        u.sendKeys(txtTipoServicio, tipoServicio);
        testCase.addScreenShotCurrentStep("Captura de tipo de servicio");
        u.click(btnTipoServicio);
        u.hardWait(5);
        //Cambiar a nueva pestaña
        Object[] windowHandlers3 = u.getDriver().getWindowHandles().toArray();
        u.getDriver().switchTo().window((String) windowHandlers3[1]);
        u.getDriver().switchTo().frame(0);
        u.click(btnIR);
        u.getDriver().switchTo().window((String) windowHandlers3[1]);
        u.getDriver().switchTo().frame(0);
        testCase.addScreenShotCurrentStep("Dar click radio button Servicios Nacionales");
        u.click(btnRadioButtonServiciosNacionales);
        testCase.addScreenShotCurrentStep("Dar click en boton seleccionar");
        u.highLight(btnSeleccionarTipoServicio);
        u.click(btnSeleccionarTipoServicio);
        u.getDriver().switchTo().window((String) windowHandlers3[0]);
        u.waitForLoadPage();
    }

    /**
     * Metodo para buscar y seleccionar el grupo de ejecuion en el aparatado de creacion de grupos de ejecucion
     *
     * @param grupoEjecucion
     * @Author David Quiroz Rodriguez
     */
    public void buscarYseleccionarGrupoEjecucionParaCreacionDeGrupo(String grupoEjecucion) {
        u.sendKeys(tipoServicio, grupoEjecucion);
        testCase.addScreenShotCurrentStep("Captura de grupo de ejecucion");
        u.click(btnBusquedaTipoServicio);
        u.hardWait(5);
        //Cambiar a nueva pestaña
        Object[] windowHandlers = u.getDriver().getWindowHandles().toArray();
        u.getDriver().switchTo().window((String) windowHandlers[1]);
        u.getDriver().switchTo().frame(0);
        u.click(btnRadiobuttonCorresponsalias);
        testCase.addScreenShotCurrentStep("Dar click en boton seleccionar");
        u.highLight(btnSeleccionarTipoServicio);
        testCase.addScreenShotCurrentStep("Dar click radio button");
        u.click(btnSeleccionarTipoServicio);
        u.getDriver().switchTo().window((String) windowHandlers[0]);
    }

    /**
     * Metodo para dar click en el boton consultar despues de crear grupo de ejecucion
     *
     * @Author David Quiroz Rodriguez
     */
    public void botonConsultarGruposCreacionGrupos() {
        WebElement dialogFrame = u.getDriver().findElement(By.xpath("//*[@id=\"iframedefaultDialogPopup\"]"));
        u.getDriver().switchTo().frame(dialogFrame);
        u.hardWait(3);
        u.getDriver().switchTo().frame(0);
        u.highLight(msjConfirmacionCreacionDeGruposCorresponsaliasYNacionales);
        u.highLight(btnConsultarGrupos);
        testCase.addScreenShotCurrentStep("Seleccionamos el boton Consultar Grupos");
        u.click(btnConsultarGrupos);
        //Regresar a la primera pantalla para no quedarse en el frame
        u.hardWait(3);
        Object[] windowHandlers = u.getDriver().getWindowHandles().toArray();
        u.getDriver().switchTo().window((String) windowHandlers[0]);

    }

    /**
     * Metodo para dar click en el boton 'Ok' despues de modificar un valor en el item de grupo de ejecucion en la pagina de EBS
     *
     * @Author David Quiroz Rodriguez
     */
    public void botonOkActualizacionItem() {
        WebElement dialogFrame = u.getDriver().findElement(By.xpath("//*[@id='iframedefaultDialogPopup']"));
        u.getDriver().switchTo().frame(dialogFrame);
        u.hardWait(3);
        u.getDriver().switchTo().frame(0);
        u.highLight(msjConfirmacionActualizacionItem);
        u.highLight(btnOKActualizacionItem);
        testCase.addScreenShotCurrentStep("Seleccionamos el boton OK");
        u.click(btnOKActualizacionItem);
        //Regresar a la primera pantalla para no quedarse en el frame
        u.hardWait(3);
        Object[] windowHandlers = u.getDriver().getWindowHandles().toArray();
        u.getDriver().switchTo().window((String) windowHandlers[0]);
    }

    /**
     * Metodo para generar la busqueda de grupo en la pagina de EBS
     *
     * @Author David Quiroz Rodriguez
     */
    public void busquedaGrupo() {
        Object[] windowHandlers = u.getDriver().getWindowHandles().toArray();
        u.getDriver().switchTo().window((String) windowHandlers[0]);
        u.hardWait(5);
        u.highLight(btnBuscarGrupos);
        testCase.addScreenShotCurrentStep("Se da click en el boton buscar");
        u.click(btnBuscarGrupos);
        u.hardWait(5);
    }

    /**
     * Metodo para generar la busqueda de items en el apartado de Nacionales en la pagina de EBS
     *
     * @Author David Quiroz Rodriguez
     */
    public void busquedaDeItems() {
        Object[] windowHandlers = u.getDriver().getWindowHandles().toArray();
        u.getDriver().switchTo().window((String) windowHandlers[0]);
        u.highLight(btnBusquedaVerItem);
        testCase.addScreenShotCurrentStep("Se da click en el boton de la lupa para ver items");
        u.click(btnBusquedaVerItem);
    }

    /**
     * Metodo para dar click en el boton crear en el apartado de Nacionales en la pagina de EBS
     *
     * @Author David Quiroz Rodriguez
     */
    public void crearItem() {
        u.highLight(btnCrear);
        testCase.addScreenShotCurrentStep("Se da click en el boton Crear");
        u.click(btnCrear);
    }

    /**
     * Metodo para dar click en el boton actualizar item en el apartado de Nacionales en la pagina de EBS
     *
     * @Author David Quiroz Rodriguez
     */
    public void actualizarItem() {
        u.highLight(btnActualizarItem);
        testCase.addScreenShotCurrentStep("Se da click en el boton Actualizar");
        u.click(btnActualizarItem);
    }

    /**
     * Metodo para buscar y seleccionar el numero de servicio en el apartado de Nacionales en la pagina de EBS
     *
     * @param numeroServicio
     * @Author David Quiroz Rodriguez
     */
    public void buscarYSeleccionarNumeroServicio(String numeroServicio) {
        u.sendKeys(txtServicio, numeroServicio);
        testCase.addScreenShotCurrentStep("Se ingresa el numero de servicio para la busqueda");
        u.click(btnBusquedaServicio);
        //Cambiar a nueva pestaña
        u.hardWait(5);
        Object[] windowHandlers4 = u.getDriver().getWindowHandles().toArray();
        u.getDriver().switchTo().window((String) windowHandlers4[1]);
        u.getDriver().switchTo().frame(0);
        //u.click(btnIR);
        u.highLight(btnRadioButtonNumeroServicio);
        testCase.addScreenShotCurrentStep("Seleccionar boton radio button de numero servicio");
        u.click(btnRadioButtonNumeroServicio);
        u.highLight(btnSeleccionarNumeroServicio);
        testCase.addScreenShotCurrentStep("Seleccionar el boton Seleccionar");
        u.click(btnSeleccionarNumeroServicio);
        u.getDriver().switchTo().window((String) windowHandlers4[0]);
    }

    /**
     * Metodo para buscar y seleccionar el numero de proveedor en el apartado de Nacionales en la pagina de EBS
     *
     * @param numeroProveedor
     * @Author David Quiroz Rodriguez
     */
    public void buscarYSeleccionarNumeroProveedor(String numeroProveedor) {
        u.sendKeys(txtProveedor, numeroProveedor);
        testCase.addScreenShotCurrentStep("Se ingresa el numero de servicio para la busqueda");
        u.click(btnBusquedaProveedor);
        //Cambiar a nueva pestaña
        u.hardWait(5);
        Object[] windowHandlers4 = u.getDriver().getWindowHandles().toArray();
        u.getDriver().switchTo().window((String) windowHandlers4[1]);
        u.getDriver().switchTo().frame(0);
        //u.click(btnIR);
        u.highLight(btnRadioButtonNumeroProveedor);
        testCase.addScreenShotCurrentStep("Seleccionar boton radio button de numero servicio");
        u.click(btnRadioButtonNumeroProveedor);
        u.highLight(btnSeleccionarNumeroProveedor);
        testCase.addScreenShotCurrentStep("Seleccionar el boton Seleccionar");
        u.click(btnSeleccionarNumeroProveedor);
        u.getDriver().switchTo().window((String) windowHandlers4[0]);
    }

    /**
     * Metodo para ingresar la hora en la parte de creación de items en el apartado de nacionales en la pagina de EBS
     *
     * @param horaAsignada
     */
    public void ingresarHora(String horaAsignada) {
        u.hardWait(5);
        Object[] windowHandlers5 = u.getDriver().getWindowHandles().toArray();
        u.getDriver().switchTo().window((String) windowHandlers5[0]);
        u.sendKeys(txtSeleccionarHora, horaAsignada);
        testCase.addScreenShotCurrentStep("Se Ingresa la hora");
    }

    /**
     * Metodo para modificar la hora en la parte de actualizacion de items en el apartado de nacionales en la pagina de EBS
     *
     * @param nuevaHoraAsignada
     */
    public void modificarHora(String nuevaHoraAsignada) {
        u.hardWait(5);
        Object[] windowHandlers5 = u.getDriver().getWindowHandles().toArray();
        u.getDriver().switchTo().window((String) windowHandlers5[0]);
        u.clear(txtSeleccionarHora);
        u.sendKeys(txtSeleccionarHora, nuevaHoraAsignada);
        testCase.addScreenShotCurrentStep("Se ingresa la nueva hora al registro");
    }

    /**
     * Metodo para dar click en el boton crear item en el apartado de creacion de grupos de ejecucion de nacionales en la pagina de EBS
     *
     * @Author David Quiroz Rodriguez
     */
    public void crearItemServicio() {
        u.highLight(btnCrearItemGuardarDatos);
        testCase.addScreenShotCurrentStep("Dar click en el boton Crear Item");
        u.click(btnCrearItemGuardarDatos);
    }

    /**
     * Metodo para confirmar que se muestre el mensaje de creacion de item de ejecucion en el grupo de ejecucion en nacionales en la pagina de EBS
     *
     * @Author David Quiroz Rodriguez
     */
    public void mensajeConfirmacionCreacion() {
        String mensajeObtenido = u.getText(msjConfirmacionCreacionGrupo);
        String mensajeObtenido2 = u.getText(msjConfirmacionCreacionItem);
        testCase.addScreenShotCurrentStep("Mensjaes de confirmacion de creacion de item grupos");
        if (mensajeObtenido.equals("Confirmación") && mensajeObtenido2.equals("Item creado correctamente")) {
            System.out.println("Se muestran los dos mensaje que confirman la creacion del grupo de ejecucion");
        } else {
            System.out.println("Los mensajes de confirmacion de grupo no son los correctos");
        }
    }

    /**
     * Metodo para dar click en el boton grupos en el apartado de items de servicio de grupo de ejecucion en nacionales en la pagina de EBS
     *
     * @Author David Quiroz Rodriguez
     */
    public void consultarGrupos() {
        u.highLight(btnGruposItems);
        testCase.addScreenShotCurrentStep("Dar click en el boton Grupos");
        u.click(btnGruposItems);
    }

    /**
     * Metodo para borrar grupo de ejecucion de corresponsalias o servicios nacionales del apartado de Nacionales en la pagina de EBS
     *
     * @Author David Quiroz Rodriguez
     */
    public void borrarGrupoEjecucionCorresponsaliasYServiciosNacionales() {
        u.highLight(btnBorrarGrpEjecCorresponsaliasYServNacionales);
        testCase.addScreenShotCurrentStep("Se selecciona el boton borrar grupo de ejecucion");
        u.click(btnBorrarGrpEjecCorresponsaliasYServNacionales);
    }

    /**
     * Metodo para seleccionar el boton Si del pop up de advertencia de eliminacion de grupo de ejecucion de corresponsalias o Servicios Nacionales de Nacionales de la pagina de EBS
     *
     * @Author David Quiroz Rodriguez
     */
    public void botonSiEliminarGrupoOItemsEjecucionCorresponsaliasYServiciosNacionales() {
        WebElement dialogFrame = u.getDriver().findElement(By.xpath("//*[@id='iframedefaultDialogPopup']"));
        u.getDriver().switchTo().frame(dialogFrame);
        u.hardWait(3);
        u.getDriver().switchTo().frame(0);
        u.highLight(msjAdvertenciaElimiacionDeGrupo);
        u.highLight(btnSiEliminarGrupo);
        testCase.addScreenShotCurrentStep("Seleccionamos el boton Si");
        u.click(btnSiEliminarGrupo);
        //Regresar a la primera pantalla para no quedarse en el frame
        u.hardWait(3);
        Object[] windowHandlers = u.getDriver().getWindowHandles().toArray();
        u.getDriver().switchTo().window((String) windowHandlers[0]);

    }

    /**
     * Metodo para confirmar los mensajes de eliminacion del grupo de ejecucion de corresponsalias o servicios nacionales de Nacionales de la pagina de EBS
     *
     * @Author David Quiroz Rodriguez
     */
    public void confirmacionDeEliminacionDeGrupo() {
        u.highLight(msjGeneralDeProcesoEliminacionDeGrupo);
        u.highLight(msjEliminacionDeGrupoCorrectamente);
        testCase.addScreenShotCurrentStep("Mensajes de eliminacion y pasos seguidos");
    }

    /**
     * Metodo para seleccionar el boton de limpiar de la busqueda de grupos de ejecucion de corresponsalias o servicios nacionales de Nacionales de la pagina de EBS
     * Author David Quiroz Rodriguez
     */
    public void botonLimpiarRegistrosDeBusqueda() {
        u.highLight(btnLimpiar);
        testCase.addScreenShotCurrentStep("Se da click en el boton limpiar");
        u.click(btnLimpiar);
    }

    /**
     * Metodo para seleccionar el boton Eliminar item en la tabla de items del grupo de ejecucion de corresponsalias o servicios nacionales en Nacionales en la pagina de EBS
     * Author David Quiroz Rodriguez
     */
    public void eliminarItemDeGrupoDeEjecucionCorresponsaliasOServNacionales(){
        u.highLight(btnEliminarItem);
        testCase.addScreenShotCurrentStep("Seleccionamos el boton eliminar de la table de los items");
        u.click(btnEliminarItem);
    }

    /**
     * Metodo para cerrar sesion en la pagina de EBS.
     *
     * @Author David Quiroz Rodriguez
     */
    public void cerrarSesionEBS() {
        u.highLight(btnDesconectarse);
        testCase.addScreenShotCurrentStep("Cerrar login de EBS");
        u.click(btnDesconectarse);
        u.hardWait(5);
        testCase.addScreenShotCurrentStep("Pagina inicio");
    }

    /**
     * Metodo para abrir el serverlet de Java en el navegador de Chrome
     * @Author David Quiroz Rodriguez
     */
    public void abrirServerletDeJavaDesdeDescargas(){
        u.hardWait(5);
        CommonMethodsSikuli.theUserClicksOn("btnDescargasChrome");
        testCase.addScreenShotCurrentStep("Seleccionamos el boton de descargas del navegador");
        CommonMethodsSikuli.theUserClicksOn("btnKeepChrome");
        testCase.addScreenShotCurrentStep("Seleccionamos el boton de keep");
        CommonMethodsSikuli.theUserClicksOn("btnDesbloquearDescargaServerlet");
        testCase.addScreenShotCurrentStep("Seleccionamos el boton para desbloquear la descarga del serverlet");
        CommonMethodsSikuli.theUserClicksOn("btnDownloadUnverifiedFile");
        testCase.addScreenShotCurrentStep("Seleccionamos el boton de download unverified");
        CommonMethodsSikuli.theUserClicksOn("btnDescargasChrome");
        testCase.addScreenShotCurrentStep("Seleccionamos el serverlet de java");
        CommonMethodsSikuli.theUserClicksOn("btnServerlertAppJava");
    }

    /**
     * Metodo para abrir un reporte tipo .xls desde el sitio de descargas del navegador de Chrome
     * @Author David Quiroz Rodriguez
     */
    public void abrirReporteHistorialDeGruposEjecucion(){
        u.hardWait(5);
        CommonMethodsSikuli.theUserClicksOn("btnDescargasChrome");
        testCase.addScreenShotCurrentStep("Seleccionamos el boton de descargas del navegador");
        CommonMethodsSikuli.theUserClicksOn("btnKeepChrome");

        testCase.addScreenShotCurrentStep("Seleccionamos el boton de keep");
        u.hardWait(5);
        CommonMethodsSikuli.theUserClicksOn("btnLinkReporteHistorial");
        testCase.addScreenShotCurrentStep("Seleccionamos el link de abrir reporte de historial");
    }
    /**
     * Metodo para abrir un reporte tipo .xls desde el sitio de descargas del navegador de Chrome
     * @Author David Quiroz Rodriguez
     */
    public void abrirReportesEnGeneralXXFC(){
        u.hardWait(5);
        CommonMethodsSikuli.theUserClicksOn("btnDescargasChrome");
        testCase.addScreenShotCurrentStep("Seleccionamos el boton de descargas del navegador");
        CommonMethodsSikuli.theUserClicksOn("btnKeepChrome");

        testCase.addScreenShotCurrentStep("Seleccionamos el boton de keep");
        u.hardWait(5);
        CommonMethodsSikuli.theUserClicksOn("btnLinkReportesXXFC");
        testCase.addScreenShotCurrentStep("Seleccionamos el link apartado del reporte");
    }
}
