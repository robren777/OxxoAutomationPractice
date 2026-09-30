package Mobile.cedis.om;

import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import exceptions.FrameworkException;
import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;
import modelo.BaseExecution;
import modelo.TestCase;
import utils.AppiumUtil;




public class B02_RecepcionPedidosCEDIS {
    private TestCase testCase;
	private AppiumUtil au;
	private BaseExecution baseExecution;

	//sección encabezado
	private By CEDIS_LeyendaRecepcionPedidosCEDIS = By.xpath("//android.widget.TextView[@text=\"RECEPCIÓN DE PEDIDO CEDIS\"]");
	private By CEDIS_Fecha = By.xpath("//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/tvDateText\" and @text=\"Fecha: \"]");
	private By CEDIS_ValorFecha = By.xpath("//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/tvTitleDate\"]");
	private By CEDIS_Ruta = By.xpath("//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/tvTitleRute\"]");
	private By CEDIS_RutaDinamica = By.xpath("//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/tvTitleRuteD\"]");
	private By CEDIS_CRTienda = By.xpath("//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/tvTitleStore\"]");
	private By CEDIS_NombreTienda = By.xpath("//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/tvTitleNameStore\"]");	
	private By CEDIS_Lider = By.xpath("//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/tvTitleLeaderStore\"]");
	private By CEDIS_Transferencia = By.xpath("//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/tvTransferNumber\"]");
	
	//sección de camara 
	private By CEDIS_LeyendaEvidenciaPedido = By.xpath("//android.widget.EditText[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/etImageNamePhotoInclude\" and @text=\"Evidencia del pedido\"]");
	private By CEDIS_btn_agregarEvidencia = By.xpath("//android.widget.ImageButton[@content-desc=\"ContentDescription\"]");
	private By CEDIS_btn_tomarFoto = By.xpath("//android.widget.ImageView[@content-desc=\"Obturador\"]");
	private By CEDIS_btn_cancelarFoto = By.xpath("//android.widget.ImageView[@content-desc=\"Cancelar\"]");
	private By CEDIS_btn_repetirFoto = By.xpath("//android.widget.ImageView[@content-desc=\"Repetir\"]");
	private By CEDIS_btn_confirmarFoto = By.xpath("//android.widget.ImageView[@content-desc=\"Listo\"]");
	private By CEDIS_LeyendaFotoObligatoria = By.xpath("//android.widget.TextView[@resource-id=\"android:id/message\" and @text=\"La fotografía es un campo obligatorio, se requieren sean tomadas las 3 fotografías\"]");
	private By CEDIS_AceptarLeyendaFotoObligatoria = By.xpath("//android.widget.Button[@resource-id=\"android:id/button1\" and @text=\"ACEPTAR\"]");
	private By CEDIS_LeyendMaximoFoto = By.xpath("//android.widget.TextView[@resource-id=\"android:id/message\" and @text= \"Sólo se permite agregar máximo 3 fotografías\"]");
	private By CEDIS_SeccionFotos = By.xpath("//androidx.recyclerview.widget.RecyclerView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/rvNameCameraInclude\"]");
	private By CEDIS_NombreFoto = By.xpath("//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/tvNamePhoto\"]");
	private By CEDIS_ContenedorPadreDeLasTresFotografiasDeEvidencia = By.id("com.femco.oxxo.operaciones.mitienda.debug:id/rvNameCameraInclude");
	
	//Sección campos Sin Escaner 
	private By CEDIS_CampoIngresarCodigoContenedor = By.id("com.femco.oxxo.operaciones.mitienda.debug:id/editTextNoScan");
	private By CEDIS_LeyendaContenedor = By.xpath("//android.widget.TextView[@text=\"CONTENEDOR\"]");
	//private By CEDIS_IngresarContenedor = By.xpath("//android.widget.EditText[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/editTextNoScan\" and @text=\"Ingresa código de contenedor\"]");
	private By CEDIS_IngresarContenedor = By.xpath("//android.widget.EditText[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/editTextNoScan\"]");
	//el mensaje de "El código de contenedor ingresado no es válido" esta en el objeto de CEDIS_LeyendaCodigoNoValido  
	
	//Secciones de Bultos
	private By CEDIS_SeccionCaja = By.xpath("//android.widget.TextView[@text=\"CAJA\"]");
	private By CEDIS_TotalPedidoCajas = By.xpath("//android.widget.EditText[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/lblTotalCajas\"]");
	private By CEDIS_TotalEntregadoCajas = By.xpath("//android.widget.EditText[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/etCountDamaged\"]");

	private By CEDIS_SeccionSecos = By.xpath("//android.widget.TextView[@text=\"CANASTILLAS SECOS\"]");
	private By CEDIS_TotalPedidoCanastillas = By.xpath("//android.widget.EditText[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/lblcanastillasSecTotal\"]");
    private By CEDIS_TotalEntregadoCanastillas = By.xpath("//android.widget.EditText[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/lblEntregadoSecos\"]");
    
    private By CEDIS_SeccionRefrigerados = By.xpath("//android.widget.TextView[@text=\"CANASTILLAS REFRIGERADOS\"]");
    private By CEDIS_TotalPedidoRefrigerados = By.xpath("//android.widget.EditText[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/lblPedidoTotalRefri\"]");
    private By CEDIS_TotalEntregadoRefrigerados = By.xpath("//android.widget.EditText[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/lblEntregadoTotalRefri\"]");
    
    private By CEDIS_LeyendaTotalBultos = By.xpath("//android.widget.TextView[@text=\"TOTAL BULTOS\"]");
    private By CEDIS_TotalPedidoBultos = By.xpath("//android.widget.EditText[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/totalPedido\"]");
    private By CEDIS_TotalEntregadoBultos = By.xpath("//android.widget.EditText[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/totalEntregado\"]");

    //Sección de Detalles del pedido
    private By CEDIS_btn_MostrarDetalles = By.xpath("//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/details\" and @text=\"MOSTRAR DETALLES\"]");
    private By CEDIS_btn_OcultarDetalles = By.xpath("//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/details\" and @text=\"OCULTAR DETALLES\"]");
    private By CEDIS_DetallesFaltantes = By.xpath("//android.widget.TextView[@text=\"DETALLES FALTANTES\"]");
    private By CEDIS_ColumnaTipo = By.xpath("//android.widget.TextView[@text=\"Tipo Cont.\"]");
    private By CEDIS_ColumnaCodigo = By.xpath("//android.widget.TextView[@text=\"Código\"]");
    private By CEDIS_ColumnaTotal = By.xpath("//android.widget.TextView[@text=\"Total\"]");
    private By CEDIS_DetallesCajas = By.xpath("//android.widget.TextView[@text=\"Cajas\"]");
    private By CEDIS_TotalDetallesCajas = By.xpath("//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/lblCajasDet\"]");
    private By CEDIS_SeccionDetalleCajas = By.xpath("//androidx.recyclerview.widget.RecyclerView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/recyclerDetail\"]");
    private By CEDIS_DetallesSecos = By.xpath("//android.widget.TextView[@text=\"Secos\"]");
    private By CEDIS_TotalDetallesSecos = By.xpath("//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/lblSecosDet\"]");
    private By CEDIS_SeccionDetalleSecos = By.xpath("//androidx.recyclerview.widget.RecyclerView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/recyclerDetailSecos\"]");
    private By CEDIS_DetallesRefrigerados = By.xpath("//android.widget.TextView[@text=\"Refrigerados\"]");
    private By CEDIS_TotalDetallesRefrigerados = By.xpath("//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/lblRefriDet\"]");
    private By CEDIS_SeccionDetalleRefrigerados = By.xpath("//androidx.recyclerview.widget.RecyclerView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/recyclerDetailRefrigerados\"]");
    
    //botón Terminar
    private By CEDIS_btn_Terminar = By.xpath("//android.widget.Button[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/btnFinish\" and @text=\"TERMINAR\"]");
    
    //Mensajes con opcion de Cancelar y confirmar
    private By CEDIS_LeyendaCajaRegistrada = By.xpath("//android.widget.TextView[@resource-id=\"android:id/message\" and @text=\"Esta caja ya está registrada\"]");
    private By CEDIS_LeyendaCanastillaNoEncontradoPedido = By.xpath("//android.widget.TextView[@resource-id=\"android:id/message\" and @text=\"La canastilla que ha ingresado no se encuentra dentro del pedido\"]");
    private By CEDIS_LeyendaCodigoNoEncontradoPedido = By.xpath("//android.widget.TextView[@resource-id=\"android:id/message\" and @text=\"El código que ha ingresado no se encuentra en el pedido\"]");
    private By CEDIS_LeyendaCanastillaRegistrada = By.xpath("//android.widget.TextView[@resource-id=\"android:id/message\" and @text=\"Esta canastilla ya está registrada\"]");
    private By CEDIS_LeyendaPendientes = By.xpath("//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/textViewCenter\" and @text=\"Hay bultos pendientes por escanear, ¿Está seguro que desea continuar?\"]");
    private By CEDIS_LeyendaTerminarRecepcionBultos = By.xpath("//android.widget.TextView[@resource-id=\"android:id/message\" and @text=\"¿Está seguro que ha terminado de realizar la recepción de los bultos del pedido?\"]");
    private By CEDIS_LeyendaTerminarSobrantesDanados = By.xpath("//android.widget.TextView[@resource-id=\"android:id/message\" and @text=\"¿Está seguro que ha terminado de capturar los Sobrantes y Dañados del pedido?\"]");
    private By CEDIS_LeyendaSegTermBultoPed = By.xpath("//android.widget.TextView[@resource-id=\"android:id/message\" and @text=\"¿Está seguro que ha terminado de realizar la recepción de los bultos del pedido?\"]");
    private By CEDIS_LeyendaNoTomadoEvidenciaArticuloyEscaneadoSobrante = By.xpath("//android.widget.TextView[@resource-id=\"android:id/message\" and @text=\"No se ha tomado la evidencia del artículo ni escaneado el artículo. Favor de escanear y tomar la imagen de evidencia del artículo para continuar.\"]");
    private By CEDIS_LeyendaNoTomadoEvidenciaArticuloSobrante = By.xpath("//android.widget.TextView[@resource-id=\"android:id/message\" and @text=\"No se ha tomado la evidencia del artículo. Favor de tomar la imagen de evidencia del artículo para continuar.\"]");
    private By CEDIS_LeyendaNoEscaneadoArticulo = By.xpath("//android.widget.TextView[@resource-id=\"android:id/message\"and @text=\"No se ha escaneado el artículo.Favor de escanear el artículo para continuar.\"]");
    
    private By CEDIS_btn_Cancelar = By.xpath("//android.widget.Button[@resource-id=\"android:id/button2\" and @text=\"CANCELAR\"]");
    private By CEDIS_btn_Confirmar = By.xpath("//android.widget.Button[@resource-id=\"android:id/button1\" and @text=\"CONFIRMAR\"]"); //usado para CEDIS_LeyendaCajaRegistrada, CEDIS_LeyendaCanastillaNoEncontradoPedido, CEDIS_LeyendaCodigoNoEncontradoPedido y CEDIS_LeyendaCanastillaRegistrada
    
    private By CEDIS_btn_Confir = By.xpath("//android.widget.Button[@resource-id='android:id/button1' and @text='CONFIRMAR']");

    //android.widget.Button[@resource-id="android:id/button1"]
    //Mensajes con opcion de Aceptar
    private By CEDIS_LeyendaCodigoNoValido = By.xpath("//android.widget.TextView[@resource-id=\"android:id/message\" and @text=\"El código de contenedor ingresado no es válido\"]");
    private By CEDIS_LeyendaMarcarfaltante =By.xpath("//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/textViewCenter\" and @text=\"No se puede marcar todo el pedido como faltante, favor de corroborar los bultos del pedido.\"]");
    private By CEDIS_LeyendaNingunaCaja =By.xpath("//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/textViewCenter\" and @text=\"No se ha ingresado ninguna caja del\r\n"
    		+ "pedido, por lo que no se puede\r\n"
    		+ "concluir la entrega. Favor de ingresar\r\n"
    		+ "las cajas correspondientes del pedido.\"]");
    private By CEDIS_LeyendaNingunaCanastillaSecos =By.xpath("//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/textViewCenter\" and @text=\"No se ha ingresado ninguna\r\n"
    		+ "canastilla de secos del pedido, por lo\r\n"
    		+ "que no se puede concluir la entrega.\r\n"
    		+ "Favor de ingresar las canastillas\r\n"
    		+ "correspondientes del pedido.\"]");
    private By CEDIS_LeyendaNingunaCanastillaRefrijerado =By.xpath("//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/textViewCenter\" and @text=\"No se ha ingresado ninguna canastilla\r\n"
    		+ "de refrigerados del pedido, por lo que\r\n"
    		+ "no se puede concluir la entrega. Favor\r\n"
    		+ "de ingresar las canastillas\r\n"
    		+ "correspondientes del pedido.\"]");
    private By CEDIS_LeyendaCodigoRegistrado =By.xpath("//android.widget.TextView[@resource-id=\"android:id/message\" and @text=\"El código ya se encuentra registrado en la lista\"]");
    private By CEDIS_LeyendaCodigoEnCero =By.xpath("//android.widget.TextView[@resource-id=\"android:id/message\" and@text=\"No se pueden quedar códigos con 0 dañados y/o sobrantes\"]");
    private By CEDIS_LeyendaDanadosMayor =By.xpath("//android.widget.TextView[@resource-id=\"android:id/message\" and @text=\"La cantidad de dañados no puede ser mayor al total del pedido recibido\"]");
    private By CEDIS_btn_Aceptar =By.xpath("//android.widget.Button[@resource-id=\"android:id/button1\" and @text=\"ACEPTAR\"]");
    private By CEDIS_LeyendaTodoComoFaltante = By.xpath("//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/textViewCenter\" and @text =\"No se puede marcar todo el pedido como faltante, favor de corroborar los bultos del pedido.\"]");
    
  //Mensajes con opcion de Si y No
    private By CEDIS_LeyendaCajaSinEtiqueta = By.xpath("//android.widget.TextView[@resource-id=\"android:id/message\" and @text=\"¿Hay cajas sin etiqueta/dañada?\"]");
    private By CEDIS_LeyendaIngresarArtDanado = By.xpath("//android.widget.TextView[@resource-id=\"android:id/message\"]");
    private By CEDIS_LeyendaEntrCajaSinEtiqueta = By.xpath("//android.widget.TextView[@resource-id=\"android:id/message\" and @text=\"¿Desea dar entrada a Caja Sin Etiqueta?\"]");

    private By CEDIS_btn_Si = By.xpath("//android.widget.Button[@resource-id=\"android:id/button1\" and @text=\"SI\"]");
    private By CEDIS_btn_No = By.xpath("//android.widget.Button[@resource-id=\"android:id/button2\" and @text=\"NO\"]");     
    
    //Seccion de Cajas sin etiquetas 
    private By CEDIS_LeyendaCajaSinEtiquetaDana = By.xpath("//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/tvTitleWOlabel\" and @text=\"CAJAS SIN ETIQUETAS/ETIQUETAS DAÑADAS\"]");
    private By CEDIS_leyeNumCajaDana = By.xpath("//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/labelnumberCaja\"]");
    private By CEDIS_leyeDescArtCajaDana = By.xpath("//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/txtNameArticulo\"]");
    private By CEDIS_btnRegArtCajaDana = By.xpath("//android.widget.Button[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/btnRegister\" and  @text=\"REGISTRAR\"]");
    private By CEDIS_CheckBoxArticuloNoEscaneable = By.xpath("//android.widget.CheckBox[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/cbNoScan\"]");
    private By CEDIS_CampoCodigoIngresadoCajaSinEtiqueta = By.xpath("//android.widget.EditText[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/EscanearArticuloE\"]");
    private By CEDIS_CampoImagenTomadaCajaSinEtiqueta = By.xpath("//android.widget.EditText[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/etImageNamePhotoIncludeDamaged\"]");
    private By CEDIS_ArticuloSinetiqueta = By.xpath("(//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/txtNameArticulo\"])[1]");
    //el mensaje de ¿Desea dar entrada a Caja Sin Etiqueta? se encuentra en el objeto de CEDIS_LeyendaEntrCajaSinEtiqueta
    private By CEDIS_btnCancelarRegArtCajaDana = By.xpath("//android.widget.ImageButton[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/cancel_reception_button\"]");
    private By CEDIS_chkArtiNoEscaneable = By.xpath("//android.widget.CheckBox[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/cbNoScan\" and @text=\"ARTÍCULO NO ESCANEABLE\" and @checked=\"false\"]");
    private By CEDIS_chkArtiNoEscaneableSeleccionado = By.xpath("//android.widget.CheckBox[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/cbNoScan\" and @text=\"ARTÍCULO NO ESCANEABLE\" and @checked=\"true\"]");
    private By CEDIS_ArtiNoEscaneable = By.xpath("//android.widget.EditText[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/EscanearArticuloE\"]");
    //el mensaje de No se ha escaneado el artículo.Favor de escanear el artículo para continuar. se muestra en el objeto de CEDIS_LeyendaNoEscaneadoArticulo    
    private By CEDIS_EvidenciaArtiNoEscaneable = By.xpath("//android.widget.EditText[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/etImageNamePhotoIncludeDamaged\" and @text=\"Evidencia del artículo\"]");
    private By CEDIS_FotoArtiNoEscaneable = By.xpath("//android.widget.ImageButton[@content-desc=\"ContentDescription\"]");
    private By CEDIS_FotoArtiNoEscaneableTablet = By.id("com.femco.oxxo.operaciones.mitienda.debug:id/ivCameraIncludeDamaged");
    private By CEDIS_tomarFotoArtiNoEscaneable = By.xpath("//android.widget.ImageView[@content-desc=\"Obturador\"]");
    private By CEDIS_confirmarFotoArtiNoEscaneable = By.xpath("//android.widget.ImageView[@content-desc=\"Listo\"]");    
    private By CEDIS_btnContinuarCajaSinEtiqueta = By.xpath("//android.widget.Button[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/btnFinishWOLabel\" and @text=\"CONTINUAR\"]");
    //el mensaje de ¿Está seguro que ha terminado de realizar la recepción de los bultos del pedido? se encuentra en el objeto de CEDIS_LeyendaSegTermBultoPed
    //El mensaje de No se ha tomado la evidencia del artículo ni escaneado el artículo. Favor de escanear y tomar la imagen de evidencia del artículo para continuar en el objeto CEDIS_LeyendaNoTomadoEvidenciaArticuloyEscaneadoSobrante
    //El mensaje de No se ha tomado la evidencia del artículo. Favor de tomar la imagen de evidencia del artículo para continuar. en el objeto CEDIS_LeyendaNoTomadoEvidenciaArticuloSobrante           
        
    //Seccion Sobrantes
    private By CEDIS_ContenedorSobrantes = By.id("com.femco.oxxo.operaciones.mitienda.debug:id/rvLeftover");
    private By CEDIS_LeyendaSobrantes = By.xpath("//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/tvTitleLeftoverDamaged\" and @text=\"SOBRANTES\"]");
    private By CEDIS_IngresarContenedorSobrante = By.xpath("//android.widget.EditText[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/editTextNoScanLeftOvers\" and @text=\"Ingresa código de contenedor\"]");
    private By CEDIS_CodigoSobrante = By.xpath("//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/tvCode_leftovers\"]");
    private By CEDIS_EliminarCodigoSobrante = By.xpath("//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/tvDelete_leftovers\" and @text=\"ELIMINAR CÓDIGO\"]");    
    private By CEDIS_TotalSobrantes = By.xpath("//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/tvTitleLeftover\" and @text=\"Total Sobrantes\"]");
    private By CEDIS_CantidadTotalSobrantes = By.xpath("//android.widget.EditText[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/numTotalLeftover\"]");
    private By CEDIS_ContinuarSobrantes = By.xpath("//android.widget.Button[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/btnFinishLeftoverDamaged\" and @text=\"CONTINUAR\"]");
    //el mensaje de El código ya se encuentra registrado en la lista se encuentra en el objeto de CEDIS_LeyendaCodigoRegistrado
    private By CEDIS_LeyendaCodigoPertecenePedido = By.xpath("//android.widget.TextView[@resource-id=\"android:id/message\" and @text=\"El código que ha escaneado pertenece al pedido, por eso no puede clasificarse como sobrante.\"]");
    private By CEDIS_AceptarCodigoPertecenePedido = By.xpath("//android.widget.Button[@resource-id=\"android:id/button1\" and @text=\"ACEPTAR\"]");
  
  //RICARDO HDZ 21/10/2024
  	private By CEDIS_IngresarContenedorSobrantes = By.xpath("//android.widget.EditText[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/editTextNoScanLeftOvers\"]");
  	

    //Sección Dañados
    private By CEDIS_LeyendaDanados = By.xpath("//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/tvTitleDamaged\" and @text=\"DAÑADOS\"]");
    private By CEDIS_AnadirArticuloDanados = By.xpath("//android.widget.Button[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/btnAddDamaged\" and @text=\"AÑADIR ARTÍCULO\"]");
    private By CEDIS_SkuArtDanado = By.xpath("//android.widget.EditText[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/edtAddDamaged\" and @text=\"Escribe el SKU o el nombre del artículo\"]");
    private By CEDIS_primerSkuSeleccionado = By.xpath("//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/textView\" and @index=\"0\"]");
    //el campo para el mensaje de "Ingresaste el siguiente artículo:744607588805 NEW MIX VAMPIRO 350ML ¿Deseas continuar?" se encuentra en el objeto de CEDIS_LeyendaIngresarArtDanado en la parte de arriba
    private By CEDIS_lbl_codigoArticuloDanado = By.xpath("//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/tvCode\"]");
    private By CEDIS_btn_EliminarArtDanado = By.xpath("//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/tvDelete\" and @text=\"ELIMINAR CÓDIGO\"]");
    private By CEDIS_btn_MenosArtDanado = By.xpath("//android.widget.ImageButton[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/btnLessDamaged\"]");
    private By CEDIS_btn_MasArtDanado = By.xpath("//android.widget.ImageButton[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/btnPlusDamaged\"]");
    private By CEDIS_btn_MasArtDanadoDos = By.xpath("(//android.widget.ImageButton[@content-desc=\"ContentDescription\"])[4]");
    private By CEDIS_campoDanado = By.xpath("//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/etCountDamaged\"]");
    private By CEDIS_LeyendaTotalDanados = By.xpath("//android.widget.TextView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/tvTitleDamagedT\" and @text=\"Total Dañados\"]");
    private By CEDIS_CantidadTotalDanados = By.xpath("//android.widget.EditText[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/numTotalDamaged\"]");
    //el mensaje de El código ya se encuentra registrado en la lista se encuentra en el objeto de CEDIS_LeyendaCodigoRegistrado
    //el mensaje de No se pueden quedar códigos con 0 dañados y/o sobrantes se encuentra en el objeto de CEDIS_LeyendaCodigoEnCero
    //el mensaje de La cantidad de dañados no puede ser mayor al total del pedido recibido en el objeto de CEDIS_LeyendaDanadosMayor
    //El mensaje de ¿Está seguro que ha terminado de capturar los Sobrantes y Dañados del pedido? se encuentra en el objeto de CEDIS_LeyendaTerminarSobrantesDanados
    
    
    private By CEDIS_TerminarDanados = By.xpath("//android.widget.Button[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/btnFinishDamaged\" and @text=\"TERMINAR\"]");
    
    private By CEDIS_TerminarDanadosNuevo = By.xpath("//android.widget.Button[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/btnFinishDamaged\"]");
    
    //Sección mensajes
    private By CEDIS_LeyendaArticuloDanadoNoPedido = By.xpath("//android.widget.TextView[@resource-id=\"android:id/message\" and @text=\"El artículo no se encuentra en el pedido\"]");
    private By CEDIS_AceptarArticuloDanadoNoPedido = By.xpath("//android.widget.Button[@resource-id=\"android:id/button1\" and @text=\"ACEPTAR\"]");
    
    private By CEDIS_LeyendaSeguroSalir = By.xpath("//android.widget.TextView[@resource-id=\"android:id/message\" and @text=\"¿Estas seguro que deseas salir? Se guardarán solo las fotografías de entrega y los bultos escaneados hasta el momento.\"]");
    private By CEDIS_SalirSeguroSalir = By.xpath("//android.widget.Button[@resource-id=\"android:id/button1\" and @text=\"SALIR\"]");
    private By CEDIS_CancelarSeguroSalir = By.xpath("//android.widget.Button[@resource-id=\"android:id/button2\" and @text=\"CANCELAR\"]");
    
    
    /**    
   OBJETOS PENDIENTES
   private By CEDIS_SeccionDetallePedido = AppOxxo.MobileObject("com.femco.oxxo.operaciones.mitienda.debug:id/DetallePedido","nativeclass:=android\.widget\.LinearLayout");
   private By CEDIS_DetalleArtCajSecRef = By.xpath("com.femco.oxxo.operaciones.mitienda.debug:id/Tcontenedor","text:=.*");        
    */


   /**
	 * Constructor
	 *****************************************************************************************
	 * Constructor de la clase B02_RecepcionPedidosCEDIS
	 *
	 * @param au            (AppiumUtil): objeto de la clase
	 * @param testCase      (TestCase): objeto de la clase
	 * @param baseExecution (BaseExecution): objeto de la clase
	 * @author Juan Ibarra
	 */
	
    public B02_RecepcionPedidosCEDIS(TestCase testCase, AppiumUtil au, BaseExecution baseExecution) {
        this.testCase = testCase;
        this.au = au;
        this.baseExecution = baseExecution;
    }
    
    /**
     * Método para validar encabezado recepcion de pedido CEDIS
     *
     * Este método valida que todos los elementos clave del encabezado de la pantalla "Recepción de Pedido CEDIS" estén correctamente visibles y habilitados 
     * si alguno de los campos obligatorios no se encuentra, el caso de prueba se marca como fallido.
     *
     * @author Juan Ibarra
     * @date 
     * @throws FrameworkException
     */
    
    public void ValidacionEncabezado() throws IOException {
    	//Funcion que permite validar que se muestran los campo correspondientes al encabezado de la pantalla  Creado: Juan Ibarra
    	if(au.driver.findElement(CEDIS_LeyendaRecepcionPedidosCEDIS).isEnabled()==false) {
    		baseExecution.addStep("Se falla el caso");        
    		Assert.fail("No se ha encontrado el Titulo pantalla RECEPCIÓN DE PEDIDO CEDIS en la parte de inicio", null);
    	}    	
    	if (au.driver.findElements(CEDIS_Fecha).isEmpty()!= false) {    		
    		baseExecution.addStep("Se falla el caso");        
    		Assert.fail("No se ha encontrado el campo de Fecha de la pantalla de RECEPCIÓN DE PEDIDO CEDIS en la parte de inicio", null);
    	}    	
    	if(au.driver.findElements(CEDIS_ValorFecha).isEmpty()!= false) {
    		baseExecution.addStep("Se falla el caso");        
    		Assert.fail("No se ha encontrado el campo de Valor Fecha de la pantalla de RECEPCIÓN DE PEDIDO CEDIS en la parte de inicio", null);
    	}
    	if(au.driver.findElements(CEDIS_Ruta).isEmpty()!= false) {
    		baseExecution.addStep("Se falla el caso");        
    		Assert.fail("No se ha encontrado el campo de Ruta de la pantalla de RECEPCIÓN DE PEDIDO CEDIS en la parte de inicio", null);
    	}
    	if(au.driver.findElements(CEDIS_RutaDinamica).isEmpty()!= false) {
    		baseExecution.addStep("Se falla el caso");        
    		Assert.fail("No se ha encontrado el campo de Ruta Dinamica de la pantalla de RECEPCIÓN DE PEDIDO CEDIS en la parte de inicio", null);
    	}
    	if(au.driver.findElements(CEDIS_CRTienda).isEmpty()!= false) {
    		baseExecution.addStep("Se falla el caso");        
    		Assert.fail("No se ha encontrado el campo de CR Tienda de la pantalla de RECEPCIÓN DE PEDIDO CEDIS en la parte de inicio", null);
    	}
    	if(au.driver.findElements(CEDIS_NombreTienda).isEmpty()!= false) {
    		baseExecution.addStep("Se falla el caso");        
    		Assert.fail("No se ha encontrado el campo de Nombre Tienda de la pantalla de RECEPCIÓN DE PEDIDO CEDIS en la parte de inicio", null);
    	}
    	if(au.driver.findElements(CEDIS_Lider).isEmpty()!= false) {
    		baseExecution.addStep("Se falla el caso");        
    		Assert.fail("No se ha encontrado el campo de Lider de la pantalla de RECEPCIÓN DE PEDIDO CEDIS en la parte de inicio", null);
    	}
    	if(au.driver.findElements(CEDIS_Transferencia).isEmpty()!= false) {
    		baseExecution.addStep("Se falla el caso");        
    		Assert.fail("No se ha encontrado el campo de Transferencia de la pantalla de RECEPCIÓN DE PEDIDO CEDIS en la parte de inicio", null);
    	}    	
    	System.out.println("Despliegue de la pantalla: Recepcion de Pedido CEDIS - Correcto");
    }
    
    /**
     * Método para validar la toma de 3 fotografias
     *
     * Realiza la acción de tomar tres fotografías de evidencia durante el proceso de recepción de pedido en la aplicación móvil 
     * si ya existen tres fotos cargadas, se detiene el proceso de tomar fotografia.
     *
     * @author Juan Ibarra
     * @date 
     * @throws FrameworkException
     */
    
    public void Tomar3Fotos()
    {  	
    	//Funcion para tomar las 3 fotografias en la recepción del pedido Creado: Juan Ibarra
    	for (int i = 0; i <= 2; i++) {
    		au.click(CEDIS_btn_agregarEvidencia);
    		boolean fotosmaximas;
        	fotosmaximas= au.driver.findElements(CEDIS_LeyendMaximoFoto).isEmpty(); //Se agrega a un booleano donde defina si el objeto se muestra en pantalla
        	//Se valida que el objeto se muestra en pantalla
        	if (fotosmaximas != true ) {
        		au.click(CEDIS_AceptarLeyendaFotoObligatoria);
        		break;//Se cancela el proceso de tomar las fotografias por identificar que ya se cuenta con las 3 fotos agregadas, se continua con el caso de prueba
        	}
    		au.click(CEDIS_btn_tomarFoto);
    		au.click(CEDIS_btn_confirmarFoto);
    		}
    }
    
    /**
     * Método para registrar sin etiqueta
     *
     * Realiza el registro de un artículo sin etiqueta o con etiqueta dañada durante el proceso de recepción de pedidos en la app, si ya se cuenta con 3 fotografias se cancela el tomar fotografias..
     *
     * @author Juan Ibarra
     * @date 
     * @throws FrameworkException
     */
    
    public void RegistrarSinEtiqueta() throws InterruptedException
    {
    	//Funcion para registrar el árticulo que se mencione como Caja Sin Etiqueta/Etiqueta Dañada Creado: Juan Ibarra
    	boolean botonRegistrar;
    	botonRegistrar= au.driver.findElements(CEDIS_btnRegArtCajaDana).isEmpty(); //Se agrega a un booleano donde defina si el objeto se muestra en pantalla    	
        //Se valida que el objeto se muestra en pantalla
        if (botonRegistrar != true ) {
        	au.click(CEDIS_btnRegArtCajaDana);
        	au.click(CEDIS_btn_Si);
        	au.click(CEDIS_chkArtiNoEscaneable);
        	Thread.sleep(1000);
        	au.click(CEDIS_FotoArtiNoEscaneable);
        	au.click(CEDIS_tomarFotoArtiNoEscaneable);
        	au.click(CEDIS_confirmarFotoArtiNoEscaneable);
        	//break;//Se cancela el proceso de tomar las fotografias por identificar que ya se cuenta con las 3 fotos agregadas, se continua con el caso de prueba
        	}      
    }
    
    /**
     * Método para registar sin etiqueta en tablet
     *
     * registro de un artículo sin etiqueta o con etiqueta dañada en dispositivo tablet de la aplicación de recepción de pedidos y toma fotografias si ya se cuenta con 3 fotografias se cancela el proceso de tomar fotografias.
     *
     * @author Juan Ibarra
     * @date 
     * @throws FrameworkException
     */
    
    public void RegistrarSinEtiquetaTablet() throws InterruptedException
    {
    	//Funcion para registrar el árticulo que se mencione como Caja Sin Etiqueta/Etiqueta Dañada Creado: Juan Ibarra
    	boolean botonRegistrar;
    	botonRegistrar= au.driver.findElements(CEDIS_btnRegArtCajaDana).isEmpty(); //Se agrega a un booleano donde defina si el objeto se muestra en pantalla    	
        //Se valida que el objeto se muestra en pantalla
        if (botonRegistrar != true ) {
        	au.click(CEDIS_btnRegArtCajaDana);
        	au.click(CEDIS_btn_Si);
        	Thread.sleep(1000);
        	au.scrollToElement(CEDIS_btnContinuarCajaSinEtiqueta);
        	au.click(CEDIS_chkArtiNoEscaneable);
        	Thread.sleep(1000);
        	au.click(CEDIS_FotoArtiNoEscaneableTablet);
        	au.click(CEDIS_tomarFotoArtiNoEscaneable);
        	au.click(CEDIS_confirmarFotoArtiNoEscaneable);
        	//break;//Se cancela el proceso de tomar las fotografias por identificar que ya se cuenta con las 3 fotos agregadas, se continua con el caso de prueba
        	}      
    }
    
    /**
     * Método para validar el despliegue de la seccion de cajas sin etiqueta
     *
     * Este método tiene como objetivo verificar que la sección "Cajas Sin Etiqueta, Etiqueta Dañada" se visualice correctamente.
     *
     * @author Juan Ibarra
     * @date 
     * @throws FrameworkException
     */
    
    public void ValidarDespliegueSeccionCajasSinEtiqueta() throws InterruptedException {
    	// Descripción: Método para validar el despliegue/visualización de la seccion cajas sin etiqueta en pantalla.
	    // Autor: Jesús Marcelo Gutiérrez Bravo
	    // Fecha: 12/02/2025

	    // Esperar 1 segundo para permitir la carga de elementos en la pantalla
	    Thread.sleep(1000);
	    
	    // Verificar si el primer elemento está visible en pantalla
	    WebElement Elemento = au.driver.findElement(CEDIS_LeyendaCajaSinEtiquetaDana);
	    if (!Elemento.isDisplayed()) {
	        // Si el primer elemento no está visible, registrar el fallo y tomar captura de pantalla
	        baseExecution.addStep("No se ha desplegado correctamente la sección a validar");        
	        Assert.fail("No se ha desplegado correctamente la sección a validar", null);
	        testCase.addScreenShotCurrentStep(au, "testImage");
	    }     
	    System.out.println("Despliegue de la sección: Cajas Sin Etiqueta / Etiqueta Danada - Correcto");
    }
    
    /**
     * Método para validar despliegue de seccion de sobrantes
     *
     * Método para validar el despliegue/visualización de la seccion sobrantes en pantalla.
     *
     * @author Jesús Marcelo Gutiérrez Bravo
     * @date   19/02/2025
     * @throws FrameworkException
     */
    
    public void ValidarDespliegueSeccionSobrantes() throws InterruptedException {
    	// Descripción: Método para validar el despliegue/visualización de la seccion sobrantes en pantalla.
	    // Autor: Jesús Marcelo Gutiérrez Bravo
	    // Fecha: 19/02/2025

	    // Esperar 1 segundo para permitir la carga de elementos en la pantalla
	    Thread.sleep(1000);
	    
	    // Verificar el elemento está visible en pantalla
	    WebElement Elemento = au.driver.findElement(CEDIS_LeyendaSobrantes);
	    if (!Elemento.isDisplayed()) {
	        // Si el elemento no está visible, registrar el fallo y tomar captura de pantalla
	        baseExecution.addStep("No se ha desplegado correctamente la sección a validar");        
	        Assert.fail("No se ha desplegado correctamente la sección a validar", null);
	        testCase.addScreenShotCurrentStep(au, "testImage");
	    }     
	    System.out.println("Despliegue de la sección: Sobrantes - Correcto");
    }
    
    /**
     * Método para validar despliegue de seccion dañados
     *
     * Método para validar el despliegue/visualización de la seccion dañados en pantalla.
     *
     * @author Jesús Marcelo Gutiérrez Bravo
     * @date   19/02/2025
     * @throws FrameworkException
     */
    
    public void ValidarDespliegueSeccionDanados() throws InterruptedException {
    	// Descripción: Método para validar el despliegue/visualización de la seccion dañados en pantalla.
	    // Autor: Jesús Marcelo Gutiérrez Bravo
	    // Fecha: 19/02/2025

	    // Esperar 1 segundo para permitir la carga de elementos en la pantalla
	    Thread.sleep(1000);
	    
	    // Verificar el elemento está visible en pantalla
	    WebElement Elemento = au.driver.findElement(CEDIS_LeyendaDanados);
	    if (!Elemento.isDisplayed()) {
	        // Si el elemento no está visible, registrar el fallo y tomar captura de pantalla
	        baseExecution.addStep("No se ha desplegado correctamente la sección a validar");        
	        Assert.fail("No se ha desplegado correctamente la sección a validar", null);
	        testCase.addScreenShotCurrentStep(au, "testImage");
	    }     
	    System.out.println("Despliegue de la sección: Dañados - Correcto");
    }
    
    /**
     * Método para validar ingreso cajas sin etiqueta
     *
     * Método para validar el despliegue/visualización de la seccion cajas sin etiqueta en pantalla.
     *
     * @author Jesús Marcelo Gutiérrez Bravo
     * @date   12/02/2025
     * @throws FrameworkException
     */
    
    public void ValidarIngresoCajaSinEtiqueta() throws InterruptedException {
    	// Descripción: Método para validar el despliegue/visualización de la seccion cajas sin etiqueta en pantalla.
	    // Autor: Jesús Marcelo Gutiérrez Bravo
	    // Fecha: 12/02/2025

	    Thread.sleep(1000);
	    
	    WebElement Elemento = au.driver.findElement(CEDIS_CheckBoxArticuloNoEscaneable);
	    if (Elemento.isEnabled()) {
	        baseExecution.addStep("No se ha seleccionado correctamente el checkbox");        
	        Assert.fail("No se ha seleccionado correctamente el checkbox", null);
	        testCase.addScreenShotCurrentStep(au, "testImage");
	    }     
	    
	    WebElement Elemento2 = au.driver.findElement(CEDIS_CampoCodigoIngresadoCajaSinEtiqueta);
	    String MensajeDesplegado1 = Elemento2.getText(); // Capturamos el texto del mensaje actual en pantalla

	    if (!(MensajeDesplegado1 != "Escanear el código de barras del artículo")) {
	        baseExecution.addStep("No se ha cargado correctamente el codigo en su campo correspondiente");
	        testCase.addScreenShotCurrentStep(au, "testImage");
	        Assert.fail("No se ha cargado correctamente el codigo en su campo correspondiente");	    
	    }
	    
	    WebElement Elemento3 = au.driver.findElement(CEDIS_CampoCodigoIngresadoCajaSinEtiqueta);
	    String MensajeDesplegado2 = Elemento3.getText(); // Capturamos el texto del mensaje actual en pantalla

	    if (!(MensajeDesplegado2 != "Evidencia del artículo")) {
	        baseExecution.addStep("No se ha cargado correctamente la imagen en su campo correspondiente");
	        testCase.addScreenShotCurrentStep(au, "testImage");
	        Assert.fail("No se ha cargado correctamente la imagen en su campo correspondiente");	    
	    }
	    
	    System.out.println("Verificado (Checkbox, Codigo y Evidencia) - Correctos");
    }
    
    /**
     * Método para validar ingreso de sobrantes
     *
     * Valida que el total de sobrantes mostrado en la aplicación coincida con el total esperado (pasado como parámetro).
     *
     * @author Jesús Marcelo Gutiérrez Bravo
     * @date   19/02/2025
     * @throws FrameworkException
     */
    
    public void ValidarIngresoSobrantes(int totalSobrantesCorrecto) throws InterruptedException {
        // Descripción: Valida que el total de sobrantes mostrado en la aplicación
        // coincida con el total esperado (pasado como parámetro).
        // Autor: Jesús Marcelo Gutiérrez Bravo
        // Fecha: 19/02/2025

        // Espera para que se carguen los elementos en pantalla
        Thread.sleep(1000);

        try {
            // Localiza el elemento que muestra el total de sobrantes
            WebElement totalSobrantesElemento = au.driver.findElement(CEDIS_CantidadTotalSobrantes);
            
            // Convierte el texto obtenido en el elemento a un número entero
            int totalSobrantes = Integer.parseInt(totalSobrantesElemento.getText().trim());
            
            // Compara el total obtenido con el valor esperado pasado por parámetro
            if (totalSobrantes != totalSobrantesCorrecto) {
                // Si no coinciden, agrega un paso en la ejecución y falla la prueba
                baseExecution.addStep("El valor del total de sobrantes es incorrecto: " 
                    + totalSobrantes + ". Se esperaba: " + totalSobrantesCorrecto);
                testCase.addScreenShotCurrentStep(au, "testImage");
                Assert.fail("El valor del total de sobrantes es incorrecto: " 
                    + totalSobrantes + ". Se esperaba: " + totalSobrantesCorrecto, null);
            }
        } catch (NoSuchElementException e) {
            // Si no se encuentra el elemento en la pantalla, agrega el paso y falla la prueba
            baseExecution.addStep("No se encontró el elemento para validar el total de sobrantes.");
            testCase.addScreenShotCurrentStep(au, "testImage");
            Assert.fail("No se encontró el elemento para validar el total de sobrantes.", e);
        } catch (NumberFormatException e) {
            // Si el texto del total no se puede convertir a número, agrega el paso y falla la prueba
            baseExecution.addStep("El valor del total de sobrantes no es un número válido: " + e.getMessage());
            testCase.addScreenShotCurrentStep(au, "testImage");
            Assert.fail("El valor del total de sobrantes no es un número válido: " + e.getMessage(), e);
        }
        
        // Indica en consola que la validación fue correcta
        System.out.println("Verificación del ingreso vs total de sobrantes: Correctos");
    }

    /**
     * Método para validar la captura de contenedor
     *
     * el ingreso de uno o varios códigos de contenedor (posiblemente SKUs o identificadores) en una aplicación móvil, 
     * y gestiona la interacción con posibles mensajes de confirmación.
     *
     * @author Juan Ibarra
     * @date 
     * @throws FrameworkException
     */
           
    public void CapturaContenedor(String caprturarContenedor) throws IOException, InterruptedException
    {
    	//Funcion para realizar la captura en el campo de Contenedor  Creado: Juan Ibarra    	
    	boolean botonRegistrar;
    	botonRegistrar= au.driver.findElements(CEDIS_IngresarContenedor).isEmpty(); //Se agrega a un booleano donde defina si el objeto se muestra en pantalla
    	
    	if (botonRegistrar != false ) {
    		au.scrollToElement(CEDIS_IngresarContenedor);    		
    	}
    	   	
    	String datos = caprturarContenedor;
        // Dividir la cadena en un array usando la coma como delimitador
        String[] arrayDatos = datos.split(",");

        // Imprimir el contenido del array
        for (String sku : arrayDatos) {
        	au.driver.hideKeyboard();
            au.sendKeys(CEDIS_IngresarContenedor, sku);
            //Thread.sleep(1000);
            //au.click(CEDIS_IngresarContenedor); Nota.-En las versiones mayores a la APK 3.10.20 ya no se requiere esta línea 
            //Runtime.getRuntime().exec("adb shell input keyevent 66");//realiza el enter en la handheld Nota.-En las versiones mayores a la APK 3.10.20 ya no se requiere esta línea ya que al momento de que se ingresa el dato se recibe por la handheld
            boolean Confirmar;
            Confirmar = au.driver.findElements(CEDIS_btn_Confirmar).isEmpty(); //Se agrega a un booleano donde defina si el objeto se muestra en pantalla
            if (Confirmar != true ) {
            	System.out.println("Se muestra mensaje para el articulo " + sku);
            	au.click(CEDIS_btn_Confirmar);            	    			
     		}            
        }   	
    }
    
    /**
     * Método para realizar deslizamiento hacia arriba
     *
     * Ejecutar un gesto de deslizamiento hacia arriba en una aplicación Android para intentar mostrar un elemento que no está visible inicialmente.
     *
     * @author Juan Ibarra
     * @date 
     * @throws FrameworkException
     */
    
    public void DeslizarArriba() throws IOException
    {  	
    	//By pantalla = By.xpath("/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout");
    	//au.click(pantalla);    	
    	au.click(CEDIS_SeccionRefrigerados);
    	boolean Fecha;
    	Fecha = au.driver.findElements(CEDIS_Fecha).isEmpty(); //Se agrega a un booleano donde defina si el objeto se muestra en pantalla    	
    	if (Fecha != false ) {        
        	Runtime.getRuntime().exec("adb shell input swipe 100 500 100 1450 100");// realiza la acción de deslizar hacia arriba		
 		}    	
    }    
    
    /**
     * Método para terminar hasta resumen de entrega
     *
     * Realiza secuencia de pasos hasta llegar a la pantalla "Resumen de Entrega", sin capturar evidencia
     *
     * @author Juan Ibarra
     * @date 
     * @throws FrameworkException
     */
    
    public void TerminarHastaResumen() throws InterruptedException
    {  	
    	//Funcion en la cual se avanza sin tomar evidencia hasta llegar a la pantalla de Resumen de Entrega Creado: Juan Ibarra
    	au.scrollToElement(CEDIS_btn_Terminar);
    	au.click(CEDIS_btn_Terminar); 
    	Thread.sleep(300);
    	if(au.driver.findElement(CEDIS_btn_Confirmar).isEnabled()) {
    		au.click(CEDIS_btn_Confirmar);
    	}
    	Thread.sleep(300);
    	//if(au.driver.findElement(CEDIS_btn_No).isDisplayed()) {
    	if (au.driver.findElements(CEDIS_btn_No).isEmpty()!= true) {//Actualización de validación en botones a mostrar
    		au.click(CEDIS_btn_No);
    	}
    	Thread.sleep(300);
    	//if(au.driver.findElement(CEDIS_btn_Confirmar).isEnabled()) {
    	if (au.driver.findElements(CEDIS_btn_Confirmar).isEmpty()!= true) {//Actualización de validación en botones a mostrar
    		au.click(CEDIS_btn_Confirmar);
    	}	
    	au.scrollToElement(CEDIS_ContinuarSobrantes);
    	au.click(CEDIS_ContinuarSobrantes);
    	au.scrollToElement(CEDIS_TerminarDanados);
    	au.click(CEDIS_TerminarDanados);
    	Thread.sleep(300);
    	//if(au.driver.findElement(CEDIS_btn_Confirmar).isEnabled()) {
    	if (au.driver.findElements(CEDIS_btn_Confirmar).isEmpty()!= true) {//Actualización de validación en botones a mostrar
    		au.click(CEDIS_btn_Confirmar);
    	}    	
    }
    
    /**
     * Método para seleccionar objeto en pantalla
     *
     * realizar acción específica (como hacer clic o hacer scroll y luego clic) sobre un objeto de la interfaz móvil, con base en el nombre recibido como parámetro.
     *
     * @author Ricardo Hdz
     * @date   21/10/2024
     * @throws FrameworkException
     */
    
    public void SeleccionarObjeto(String ObjetoPantalla)
    {  	
    	//Funcion para buscar objeto y dar click especifico en la pantalla Creado: Juan Ibarra
    	switch (ObjetoPantalla) {
    	
    	case "Agregar Evidencia":
    		au.scrollToElement(CEDIS_btn_agregarEvidencia); //Se agrego linea para que busque el objeto antes de darle click Juan 26/11/24
    		au.click(CEDIS_btn_agregarEvidencia);
            break;
    	case "Ingresar Contenedor":
    		au.scrollToElement(CEDIS_IngresarContenedor); //Se agrego linea para que busque el objeto antes de darle click Juan 26/11/24
    		au.click(CEDIS_IngresarContenedor);
            break;
    	case "Tomar Foto":    		
    		au.click(CEDIS_btn_tomarFoto);
            break;
    	case "Cancelar Foto":    		
    		au.click(CEDIS_btn_cancelarFoto);
            break;
    	case "Repetir Foto":    		
    		au.click(CEDIS_btn_repetirFoto);
            break;
    	case "Confirmar Foto":    		
    		au.click(CEDIS_btn_confirmarFoto);
            break;    	
    	case "Mostrar Detalles":    		
    		au.scrollToElement(CEDIS_btn_MostrarDetalles); // Se agrego linea para que busque el objeto antes de darle click Marcelo 27/01/25
    		au.click(CEDIS_btn_MostrarDetalles);
            break;
    	case "Ocultar Detalles":
    		au.scrollToElement(CEDIS_btn_OcultarDetalles); // Se agrego linea para que busque el objeto antes de darle click Marcelo 27/01/25
        	au.click(CEDIS_btn_OcultarDetalles);
            break;            
    	case "Cancelar":
        	au.click(CEDIS_btn_Cancelar);
            break;
    	case "Confirmar":
        	au.click(CEDIS_btn_Confirmar);
            break;
    	case "Aceptar":
        	au.click(CEDIS_btn_Aceptar);
            break;
    	case "Si":
        	au.click(CEDIS_btn_Si);
            break;
    	case "No":
        	au.click(CEDIS_btn_No);
            break;            
        case "Terminar":
    		au.scrollToElement(CEDIS_btn_Terminar); // Se agrego linea para que busque el objeto antes de darle click Marcelo 27/01/25
        	au.click(CEDIS_btn_Terminar);
            break;
        //Dirigirse a boton Terminar recepcion RICARDO 21/10/2024
        case "Terminar Recepcion":
    		au.scrollToElement(CEDIS_btn_Terminar);
            break;
        //Presionar boton Registrar Cajas sin etiqueta RICARDO 21/10/2024
        case "Registrar":
        	au.scrollToElement(CEDIS_btnRegArtCajaDana); //Se agrego linea para que busque el objeto antes de darle click Juan 26/11/24
        	au.click(CEDIS_btnRegArtCajaDana);
        	break;
        case "Primer SkuSeleccionado":
        	au.click(CEDIS_primerSkuSeleccionado);
        	break;
        case "Registrar Articulo Dañado":
        	au.click(CEDIS_btnRegArtCajaDana);
        	break;
        case "Check Articulo No Escaneable":
        	au.click(CEDIS_chkArtiNoEscaneable);
        	break;
        case "Foto Articulo No Escaneable":
        	au.click(CEDIS_FotoArtiNoEscaneable);
        	break;
        case "Tomar Foto Articulo NoEscaneable":
        	au.click(CEDIS_tomarFotoArtiNoEscaneable);
        	break;
        case "Confirmar Foto Articulo No Escaneable":
        	au.click(CEDIS_confirmarFotoArtiNoEscaneable);
        	break;
        case "Aceptar Articulo Danado No Pedido":
        	au.click(CEDIS_AceptarArticuloDanadoNoPedido);
        	break;
        case "Aceptar Codigo Pertecene Pedido":
        	au.click(CEDIS_AceptarCodigoPertecenePedido);
        	break; 
        //Ir a  CONTINUAR RICARDO 21/10/2024
        case "Terminar Cajas Sin Etiqueta":
    		au.scrollToElement(CEDIS_btnContinuarCajaSinEtiqueta);
        	au.click(CEDIS_btnContinuarCajaSinEtiqueta);
        	break;
        //Presionar CONTINUAR RICARDO 21/10/2024
        case "Continuar a Faltantes":
        	au.click(CEDIS_btnContinuarCajaSinEtiqueta);
        	break;
        case "Continuar Sobrantes":
    		au.scrollToElement(CEDIS_ContinuarSobrantes);
        	au.click(CEDIS_ContinuarSobrantes);
        	break;
        case "Continuar Dañados":
    		au.scrollToElement(CEDIS_ContinuarSobrantes);
        	au.click(CEDIS_ContinuarSobrantes);
        	break;
      	  //Presionar Añadir + Articulo dañado RICARDO 22/10/2024
        case "Aumentar Dañado":
        	au.click(CEDIS_btn_MasArtDanado);
        	break;
        case "Aumentar Dañado Dos":
        	au.click(CEDIS_btn_MasArtDanadoDos);
        	break;
        //Presionar Terminar Articulo dañado RICARDO 22/10/2024
        case "Terminar Dañados":
        	au.scrollToElement(CEDIS_TerminarDanados);
        	au.click(CEDIS_TerminarDanados);
        	break;
        //Presionar Confirmar al termina dañado y sobrantes RICARDO 22/10/2024        	
        case "Confirmar Dañados":
        	au.click(CEDIS_btn_Confir);
        	break;
      	//Presionar Salir del mensaje de Desea Salir Juan 11/02/2025        	
        case "Desea Salir":
           	au.click(CEDIS_SalirSeguroSalir);
           	break;
        //Presionar Cancelar del mensaje de Desea Salir Juan 11/02/2025        	
        case "Cancelar Salir":
        	au.click(CEDIS_CancelarSeguroSalir);
        	break;
        default:
            baseExecution.addStep("Se falla el caso");
            //Se agrega el paso a mostrar en el PDF
    		Assert.fail("No se ha encontrado el objeto "+ ObjetoPantalla, null);
    		//Se agrega un mensaje a mostrar del error, en caso de no ponerlo regresaria leyenda de null
    	}    	
    }
    
    /**
     * Método para mover la panatalla a seccion especifica
     *
     * Permitir el desplazamiento dentro de una pantalla de la aplicación móvil hasta una sección específica, identificada por su nombre lógico.
     *
     * @author Juan Ibarra
     * @date   18-10-2024
     * @throws FrameworkException
     */
    
    public void MoverSeccion(String Seccion) throws IOException, InterruptedException {
    	//Funcion que permite mover la pantalla a una sección especifica de la pantalla Creado: Juan Ibarra Fecha:18-10-2024
    	switch (Seccion) {
    	case "Contenedor":
    		au.scrollToElement(CEDIS_TotalEntregadoRefrigerados);
        break;
        case "Detalle Faltantes":       	
    			au.scrollToElement(CEDIS_DetallesRefrigerados);    			
		break;
        case "Sobrantes":       	
			au.scrollToElement(CEDIS_TotalSobrantes);
			break;
        case "Dañados":       	
			au.scrollToElement(CEDIS_LeyendaTotalDanados);
			break;
        case "Campo Ingresar Codigo Contenedor":       	
			au.scrollToElement(CEDIS_CampoIngresarCodigoContenedor);
			break;
        case "Terminar":
    		au.scrollToElement(CEDIS_btn_Terminar);
    		break;
        case "Cajas sin etiqueta":
        	au.scrollToElement(CEDIS_LeyendaCajaSinEtiquetaDana);
        	Runtime.getRuntime().exec("adb shell input swipe 100 500 130 200 150");
        	Thread.sleep(3000);
        	break;
        case "Leyenda Sobrantes": //Vanessa Torres 18/02/25
        	au.scrollToElement(CEDIS_LeyendaSobrantes);
        	Thread.sleep(1000);
        	break;
        
        default:
        baseExecution.addStep("Se falla el caso");
        //Se agrega el paso a mostrar en el PDF
		Assert.fail("No se ha encontrado la sección "+ Seccion + " mencionada", null);
		//Se agrega un mensaje a mostrar del error, en caso de no ponerlo regresaria leyenda de null
		}
    }
    
    /**
     * Método para seleccionar articulos
     *
     * realiza la selección de un artículo específico en la sección de "Añadir Artículos Dañados", validando si pertenece al pedido y si ya ha sido registrado.
     *
     * @author Juan Ibarra
     * @date   18-10-2024
     * @throws FrameworkException
     */
    
    public void SeleccionarArticulo(String sku, String tomaEvidencia, String descPaso) throws IOException, InterruptedException {
    	//Funcion que permite seleccionar el articulo especificado del SKU capturado en añadir Articulos Dañados en Creado: Juan Ibarra Fecha:18-10-2024    	
    	au.scrollToElement(CEDIS_AnadirArticuloDanados);
    	if (tomaEvidencia.equals("tomaEvidencia")) {
    		au.click(CEDIS_AnadirArticuloDanados);    		    			
    		baseExecution.addStep(descPaso);
    		au.driver.hideKeyboard();
    		Runtime.getRuntime().exec("adb shell input text "+ sku);//Capturar texto cambiando en este ejemplo el numero 789549785    		
            Thread.sleep(500);
            testCase.addScreenShotCurrentStep(au, "testImage");
            au.click(CEDIS_primerSkuSeleccionado);            
            Thread.sleep(500);
            testCase.addScreenShotCurrentStep(au, "testImage");
            au.click(CEDIS_btn_Si);
            if (au.driver.findElements(CEDIS_LeyendaArticuloDanadoNoPedido).isEmpty()!= true) {            	
            	baseExecution.addStep("Se falla el caso");        		
        		Assert.fail("El artículo "+ sku + " no se encuentra en el pedido", null);
            }
            if (au.driver.findElements(CEDIS_LeyendaCodigoRegistrado).isEmpty()!= true) {            	
            	baseExecution.addStep("Se falla el caso");        		
        		Assert.fail("El código "+ sku + " ya se encuentra registrado en la lista", null);
            }
    		}else {    			
    			au.click(CEDIS_AnadirArticuloDanados);    			
    			Runtime.getRuntime().exec("adb shell input text "+ sku);//Capturar texto cambiando en este ejemplo el numero 789549785                
                //Thread.sleep(500);
                au.click(CEDIS_primerSkuSeleccionado);                
                //Thread.sleep(500);
                au.click(CEDIS_btn_Si);
                if (au.driver.findElements(CEDIS_LeyendaArticuloDanadoNoPedido).isEmpty()!= true) {                	
                	baseExecution.addStep("Se falla el caso");        		
            		Assert.fail("El artículo "+ sku + " no se encuentra en el pedido", null);
                }
                if (au.driver.findElements(CEDIS_LeyendaCodigoRegistrado).isEmpty()!= true) {                	
                	baseExecution.addStep("Se falla el caso");        		
            		Assert.fail("El código "+ sku + " ya se encuentra registrado en la lista", null);
                }        		     	    	
    		}
    }
    
    /**
     * Método para capturar contenedor de sobrantes
     *
     * realiza la captura de uno o más códigos en el campo "Contenedor Sobrantes" en una aplicación móvil, utilizando entrada directa por ADB y validaciones de interfaz.
     *
     * @author Ricardo Hdz
     * @date   21/10/2024
     * @throws FrameworkException
     */
    
    //Ricardo ContenedorSobrantes 21/10/2024
    public void CapturaContenedorSobrantes(String caprturarContenedor) throws IOException, InterruptedException
    {
    	//Funcion para realizar la captura en el campo de Contenedor  Creado: Ricardo Hdz   	
    	boolean botonRegistrar;
    	botonRegistrar= au.driver.findElements(CEDIS_IngresarContenedorSobrantes).isEmpty(); //Se agrega a un booleano donde defina si el objeto se muestra en pantalla
    	
    	if (botonRegistrar != false ) {
    		au.scrollToElement(CEDIS_IngresarContenedorSobrantes);    		
    	}
    	   	
    	String datos = caprturarContenedor;
        // Dividir la cadena en un array usando la coma como delimitador
        String[] arrayDatos = datos.split(",");

        // Imprimir el contenido del array
        for (String sku : arrayDatos) {
        	au.driver.hideKeyboard();
            au.sendKeys(CEDIS_IngresarContenedorSobrantes, sku);
            //Thread.sleep(1000);
            au.click(CEDIS_IngresarContenedorSobrantes);            
            Runtime.getRuntime().exec("adb shell input keyevent 66");//realiza el enter en la handheld
            Runtime.getRuntime().exec("adb shell input keyevent 4");// realiza la acción de Back en la handheld
            boolean Confirmar;
            Confirmar = au.driver.findElements(CEDIS_ContinuarSobrantes).isEmpty(); //Se agrega a un booleano donde defina si el objeto se muestra en pantalla
            if (Confirmar != true ) {
            	System.out.println("Se muestra mensaje para el articulo " + sku);
            	//au.click(CEDIS_ContinuarSobrantes);            	    			
     		}            
        }   	
    }
    
    /**
     * Método para obtener el valor de la fecha en el encabezado
     *
     * Obtener y devolver el valor (texto) de la fecha mostrada en el encabezado de una pantalla dentro de una app móvil.
     *
     * @author Juan Ibarra
     * @date   22/10/2024
     * @throws FrameworkException
     */
    
    public String ValorFecha() throws IOException, InterruptedException {
    	//Funcion que permite obtener el valor de la fecha en el encabezado 	Creado: Juan Ibarra Fecha Creación:22-10-2024    	   	
    	WebElement fecha = au.driver.findElement(CEDIS_ValorFecha);
    	String valorFecha = fecha.getAttribute("text");    	
    	return valorFecha;        
    }
    
    /**
     * Método para obtener valor de ruta dinamica 
     *
     * Obtener y devolver solo el valor numérico o identificador de la Ruta Dinámica mostrado en el encabezado de la aplicación.
     *
     * @author Juan Ibarra
     * @date   22-10-2024 
     * @throws FrameworkException
     */
    
    public String ValorRutaDinamica() throws IOException, InterruptedException {
    	//Funcion que permite obtener el valor de Ruta Dinamica en el encabezado 	Creado: Juan Ibarra Fecha Creación:22-10-2024    	   	
    	WebElement ruta = au.driver.findElement(CEDIS_RutaDinamica);
        String valorRuta = ruta.getAttribute("text");
        String RutaDinamica = valorRuta.substring(15);        
        return RutaDinamica;        
    }
    
    /**
     * Método para obtener valor de CR en el encabezado
     *
     * Obtener el valor del CR mostrado en el encabezado de la aplicación móvil, devolviendo únicamente la parte numérica o dinámica.
     *
     * @author Juan Ibarra
     * @date   22-10-2024 
     * @throws FrameworkException
     */
    
    public String ValorCR() throws IOException, InterruptedException {
    	//Funcion que permite obtener el valor de CR en el encabezado 	Creado: Juan Ibarra Fecha Creación:22-10-2024    	   	
    	WebElement CR = au.driver.findElement(CEDIS_CRTienda);
        String valorCR = CR.getAttribute("text");
        String DatoCR = valorCR.substring(11);        
        return DatoCR;        
    }
    
    /**
     * Método para obtener valor de transferencia
     *
     * Obtener el número o código de Transferencia mostrado en el encabezado de la aplicación móvil, extrayendo únicamente el valor dinámico.
     *
     * @author Juan Ibarra
     * @date   22-10-2024 
     * @throws FrameworkException
     */
    
    public String ValorTransferencia() throws IOException, InterruptedException {
    	//Funcion que permite obtener el valor de la Transferencia mencionada en el encabezado 	Creado: Juan Ibarra Fecha Creación:22-10-2024
    	WebElement Transferencia = au.driver.findElement(CEDIS_Transferencia);
        String valorTransferencia = Transferencia.getAttribute("text");
        String DatoTransferencia = valorTransferencia.substring(15);        
        return DatoTransferencia;    
    }
    
    /**
     * Método para recepcionar una caja
     *
     * Función para recepcionar la primer caja que se encuentre en el listado de mostrar detalles. 
     * Si no hay cajas, no truena el script ya que tiene varios manejos de errores para solucionar esto.
     * El único pre-requisito es tener las 3 fotos de evidencia ya tomadas.   
     *
     * @author Marcelo Gutiérrez
     * @date   23-10-2024
     * @throws FrameworkException
     */
    
    public void recepcionarUnaCaja() throws InterruptedException, IOException {
        // Función para recepcionar la primer caja que se encuentre en el listado de mostrar detalles. 
        // Si no hay cajas, no truena el script ya que tiene varios manejos de errores para solucionar esto.
        // El único pre-requisito es tener las 3 fotos de evidencia ya tomadas.
        // Creado: Marcelo Gutiérrez Fecha: 23-10-2024
        
        // Iniciar proceso de recepción de pedido
        System.out.println("Iniciando el proceso de recepción de una caja del pedido...");

        // Hacer scroll hasta el botón "Terminar"
        System.out.println("Haciendo scroll hasta el botón 'Mostrar Detalles'...");
        au.scrollToElement(AppiumBy.id("com.femco.oxxo.operaciones.mitienda.debug:id/details"));
        Thread.sleep(1000); // Esperar 1000ms para asegurarse de que el scroll se complete
        Runtime.getRuntime().exec("adb shell input swipe 20 1003 20 900 90");
        Thread.sleep(1000); // Esperar 1000ms para asegurarse de que el scroll se complete
        System.out.println("Scroll completado al botón 'Mostrar Detalles'.");

        // Verificar si el botón "Mostrar Detalles" ya ha sido presionado
        System.out.println("Verificando el estado del botón 'Mostrar Detalles'...");
        try {
            // Buscar el elemento que indica el estado actual de los detalles
            WebElement detallesElemento = au.driver.findElement(AppiumBy.xpath("//android.widget.TextView[@resource-id='com.femco.oxxo.operaciones.mitienda.debug:id/details']"));
            String estadoDetallesTexto = detallesElemento.getText();
            
            if (estadoDetallesTexto.equals("OCULTAR DETALLES")) {
                System.out.println("El botón ya ha sido presionado, se omite el clic en 'Mostrar Detalles'.");
            } else {
                // Hacer clic en el botón "Mostrar Detalles"
                System.out.println("Haciendo clic en 'Mostrar Detalles'...");
                au.click(CEDIS_btn_MostrarDetalles);
                Thread.sleep(1000); // Esperar a que el clic se procese
                System.out.println("Clic en 'Mostrar Detalles' realizado.");
            }
        } catch (NoSuchElementException e) {
            // Si no se encuentra el elemento que indica el estado, proceder a hacer clic
            System.out.println("No se encontró el estado del botón 'Mostrar Detalles'. Procediendo a hacer clic.");
            System.out.println("Haciendo clic en 'Mostrar Detalles'...");
            au.click(CEDIS_btn_MostrarDetalles);
            Thread.sleep(1000); // Esperar a que el clic se procese
            System.out.println("Clic en 'Mostrar Detalles' realizado.");
        }

        // Localizar el elemento que tiene el texto "Cajas"
        System.out.println("Buscando el elemento con el texto 'Cajas'...");
        Runtime.getRuntime().exec("adb shell input swipe 20 1003 20 900 90");
        Thread.sleep(1000); // Esperar a que el clic se procese
        WebElement elementoCajas;
        try {
            elementoCajas = au.driver.findElement(AppiumBy.xpath("//android.widget.TextView[@text='Cajas']"));
            System.out.println("Elemento 'Cajas' encontrado.");
        } catch (NoSuchElementException e) {
            System.out.println("No se encontró el elemento 'Cajas'. Finalizando proceso.");
            return; // Salir si no se encuentra el elemento
        }

        // Obtener el hermano que contiene el número de cajas
        System.out.println("Obteniendo el elemento que contiene el número total de cajas...");
        WebElement numeroCajasElemento;
        try {
            numeroCajasElemento = au.driver.findElement(AppiumBy.xpath("//android.widget.TextView[@resource-id='com.femco.oxxo.operaciones.mitienda.debug:id/lblCajasDet']"));
            System.out.println("Elemento que contiene el número de cajas encontrado.");
        } catch (NoSuchElementException e) {
            System.out.println("No se encontró el elemento que contiene el número de cajas. Finalizando proceso.");
            return; // Salir si no se encuentra el elemento
        }

        // Extraer el texto que contiene el número de cajas
        String numeroCajasTexto = numeroCajasElemento.getText();
        int numeroCajas = Integer.parseInt(numeroCajasTexto.trim()); // Convertir el texto a número
        System.out.println("Número de cajas encontrado: " + numeroCajas);

        // Validar si el número de cajas es 0
        if (numeroCajas == 0) {
            System.out.println("No hay cajas para recepcionar (número de cajas es 0). Finalizando proceso.");
            return; // Salir si no hay cajas
        }

        System.out.println("Número total de cajas encontradas: " + numeroCajas);

        // Verificar si el contenedor de cajas está presente antes de hacer scroll
        try {
            // Hacer scroll hasta el contenedor de las cajas
            System.out.println("Haciendo scroll hasta el contenedor de las cajas...");
            au.scrollToElement(AppiumBy.id("com.femco.oxxo.operaciones.mitienda.debug:id/recyclerDetail"));
            Thread.sleep(300); // Esperar a que el scroll se complete
            System.out.println("Scroll completado al contenedor de cajas.");

            // Ejecutar swipe para asegurar visibilidad
            System.out.println("Ejecutando swipe para mejorar visibilidad...");
            Runtime.getRuntime().exec("adb shell input swipe 20 1003 20 900 90");
            Thread.sleep(300); // Esperar 300ms para que el swipe se complete
            System.out.println("Swipe realizado con éxito.");

            // Obtener todas las cajas dentro del contenedor
            System.out.println("Obteniendo lista de cajas...");
            List<WebElement> cajas = au.driver.findElements(AppiumBy.xpath("//android.widget.FrameLayout[@resource-id='com.femco.oxxo.operaciones.mitienda.debug:id/card_view']"));
            Thread.sleep(500); // Esperar a que se carguen las cajas
            System.out.println("Número de cajas encontradas en el contenedor: " + cajas.size());

            // Crear una lista para almacenar los códigos
            ArrayList<String> codigosCajas = new ArrayList<>();

            // Iterar sobre las cajas y extraer los códigos
            System.out.println("Extrayendo códigos de las cajas...");
            if (!cajas.isEmpty()) { // Verifica si hay cajas disponibles
                WebElement caja = cajas.get(0); // Solo toma la primera caja
                try {
                    String codigo = caja.findElement(AppiumBy.xpath(".//android.widget.LinearLayout/android.widget.TextView[2]")).getText();
                    codigosCajas.add(codigo);
                    System.out.println("Código de la caja a recepcionarse: " + codigo);
                } catch (NoSuchElementException e) {
                    System.out.println("Error al encontrar el código en la caja.");
                }
            } else {
                System.out.println("No se encontraron cajas para procesar.");
                return; // Salir si no hay cajas
            }

            // Deslizar hacia arriba
            System.out.println("Deslizando hacia arriba...");
            Runtime.getRuntime().exec("adb shell input swipe 100 500 100 1450 100");
            Thread.sleep(500); // Esperar 500ms para que el scroll se complete
            System.out.println("Deslizamiento hacia arriba completado.");

            // Imprimir los códigos de las cajas seleccionadas
            System.out.println("Código de la caja seleccionada para ingresar: " + codigosCajas);

            // Llamar al método CapturaContenedor para la caja
            for (String codigo : codigosCajas) {
                System.out.println("Ingresando código: " + codigo);
                CapturaContenedor(codigo); // Llamada al método CapturaContenedor
                Thread.sleep(500); // Esperar 500ms entre capturas por seguridad
                au.driver.hideKeyboard(); // Ocultar teclado después de ingresar el código
                System.out.println("Código ingresado y teclado ocultado.");
            }

        } catch (NoSuchElementException e) {
            System.out.println("El contenedor de cajas no está presente en la interfaz. Finalizando proceso.");
            return; // Salir si el contenedor no está presente
        }

        // Finalizar proceso
        System.out.println("\n--------- Finalizacion de método para ingresar una caja del pedido ---------\n");
        // Fin del método recepcionarUnaCaja
    }
    
    /**
     * Método para recepcionar una canastilla de secos
     *
     * Función para recepcionar la primer canastilla del tipo "Secos" que se encuentre en el listado de mostrar detalles.
     * Si no hay canastillas secos, no truena el script ya que tiene varios manejos de errores para solucionarlo.
     * El único pre-requisito es tener las 3 fotos de evidencia ya tomadas.
     *
     * @author Marcelo Gutiérrez
     * @date   24-10-2024
     * @throws FrameworkException
     */
    
    public void recepcionarUnaCanastillaSecos() throws InterruptedException, IOException {
    	// Función para recepcionar la primer canastilla del tipo "Secos" que se encuentre en el listado de mostrar detalles. 
    	// Si no hay canastillas secos, no truena el script ya que tiene varios manejos de errores para solucionarlo.
    	// El único pre-requisito es tener las 3 fotos de evidencia ya tomadas.
    	// Creado: Marcelo Gutiérrez Fecha: 24-10-2024

    	// Iniciar proceso de recepción de canastillas secos
    	System.out.println("Iniciando el proceso de recepción de una canastilla de secos...");

    	// Hacer scroll hasta el botón "Mostrar Detalles"
    	System.out.println("Haciendo scroll hasta el botón 'Mostrar Detalles'...");
    	au.scrollToElement(AppiumBy.id("com.femco.oxxo.operaciones.mitienda.debug:id/details"));
    	Thread.sleep(1000); // Esperar 1000ms para asegurarse de que el scroll se complete
    	Runtime.getRuntime().exec("adb shell input swipe 20 1003 20 900 85");
    	Thread.sleep(1000); // Esperar 1000ms para asegurarse de que el scroll se complete
    	Runtime.getRuntime().exec("adb shell input swipe 20 1003 20 900 85");
    	Thread.sleep(1000); // Esperar 1000ms para asegurarse de que el scroll se complete
    	System.out.println("Scroll completado al botón 'Mostrar Detalles'.");

    	// Verificar si el botón "Mostrar Detalles" ya ha sido presionado
    	System.out.println("Verificando el estado del botón 'Mostrar Detalles'...");
    	try {
    	WebElement detallesElemento = au.driver.findElement(AppiumBy.xpath("//android.widget.TextView[@resource-id='com.femco.oxxo.operaciones.mitienda.debug:id/details']"));
    	String estadoDetallesTexto = detallesElemento.getText();
    	if (estadoDetallesTexto.equals("OCULTAR DETALLES")) {
    	System.out.println("El botón ya ha sido presionado, se omite el clic en 'Mostrar Detalles'.");
    	} else {
    	System.out.println("Haciendo clic en 'Mostrar Detalles'...");
    	au.click(CEDIS_btn_MostrarDetalles);
    	Thread.sleep(1000); // Esperar a que el clic se procese
    	System.out.println("Clic en 'Mostrar Detalles' realizado.");
    	}
    	} catch (NoSuchElementException e) {
    	System.out.println("No se encontró el estado del botón 'Mostrar Detalles'. Procediendo a hacer clic.");
    	au.click(CEDIS_btn_MostrarDetalles);
    	Thread.sleep(1000); // Esperar a que el clic se procese
    	System.out.println("Clic en 'Mostrar Detalles' realizado.");
    	}

    	// Localizar el elemento que tiene el texto "Secos"
    	System.out.println("Buscando el elemento con el texto 'Secos'...");
        au.scrollToElement(AppiumBy.xpath("(//android.widget.TextView[@text=\"Secos\"])[1]"));
    	Thread.sleep(1000); // Esperar a que el clic se procese
    	WebElement elementoSecos;
    	try {
    	elementoSecos = au.driver.findElement(AppiumBy.xpath("(//android.widget.TextView[@text=\"Secos\"])[1]"));
    	System.out.println("Elemento 'Secos' encontrado.");
    	} catch (NoSuchElementException e) {
    	System.out.println("No se encontró el elemento 'Secos'. Finalizando proceso.");
    	return; // Salir si no se encuentra el elemento
    	}

    	// Obtener el elemento que contiene el número de canastillas de secos
    	System.out.println("Obteniendo el elemento que contiene el número total de canastillas secos...");
    	WebElement numeroSecosElemento;
    	try {
    	numeroSecosElemento = au.driver.findElement(AppiumBy.xpath("//android.widget.TextView[@resource-id='com.femco.oxxo.operaciones.mitienda.debug:id/lblSecosDet']"));
    	System.out.println("Elemento que contiene el número de canastillas secos encontrado.");
    	} catch (NoSuchElementException e) {
    	System.out.println("No se encontró el elemento que contiene el número de canastillas secos. Finalizando proceso.");
    	return; // Salir si no se encuentra el elemento
    	}

    	// Extraer el texto que contiene el número de canastillas de secos
    	String numeroSecosTexto = numeroSecosElemento.getText();
    	int numeroSecos = Integer.parseInt(numeroSecosTexto.trim()); // Convertir el texto a número
    	System.out.println("Número de canastillas secos encontrado: " + numeroSecos);

    	// Validar si el número de canastillas es 0
    	if (numeroSecos == 0) {
    	System.out.println("No hay canastillas secos para recepcionar (número de canastillas es 0). Finalizando proceso.");
    	return; // Salir si no hay canastillas
    	}

    	System.out.println("Número total de canastillas secos encontradas: " + numeroSecos);

    	// Verificar si el contenedor de canastillas está presente antes de hacer scroll
    	try {
    	System.out.println("Haciendo scroll hasta el contenedor de las canastillas secos...");
    	au.scrollToElement(AppiumBy.id("com.femco.oxxo.operaciones.mitienda.debug:id/recyclerDetailSecos"));
    	Thread.sleep(300); // Esperar a que el scroll se complete
    	System.out.println("Scroll completado al contenedor de canastillas secos.");

    	// Ejecutar swipe para asegurar visibilidad
    	System.out.println("Ejecutando swipe para mejorar visibilidad...");
    	Runtime.getRuntime().exec("adb shell input swipe 20 1003 20 900 80");
    	Thread.sleep(300); // Esperar 300ms para que el swipe se complete
    	System.out.println("Swipe realizado con éxito.");

    	// Obtener todas las canastillas dentro del contenedor
    	System.out.println("Obteniendo lista de canastillas...");
    	List<WebElement> canastillas = au.driver.findElements(AppiumBy.xpath("//androidx.recyclerview.widget.RecyclerView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/recyclerDetailSecos\"]]"));
    	Thread.sleep(500); // Esperar a que se carguen las canastillas
    	System.out.println("Número de canastillas encontradas en el contenedor: " + canastillas.size());

    	// Crear una lista para almacenar los códigos
    	ArrayList<String> codigosCanastillas = new ArrayList<>();

    	// Iterar sobre las canastillas y extraer los códigos
    	System.out.println("Extrayendo códigos de las canastillas...");
    	if (!canastillas.isEmpty()) { // Verifica si hay canastillas disponibles
    	WebElement canastilla = canastillas.get(0); // Solo toma la primera canastilla
    	try {
    	String codigo = canastilla.findElement(AppiumBy.xpath(".//android.widget.LinearLayout/android.widget.TextView[2]")).getText();
    	codigosCanastillas.add(codigo);
    	System.out.println("Código de la canastilla a recepcionarse: " + codigo);
    	} catch (NoSuchElementException e) {
    	System.out.println("Error al encontrar el código en la canastilla.");
    	}
    	} else {
    	System.out.println("No se encontraron canastillas para procesar.");
    	return; // Salir si no hay canastillas
    	}

    	// Deslizar hacia arriba
    	System.out.println("Deslizando hacia arriba...");
    	Runtime.getRuntime().exec("adb shell input swipe 100 500 100 1450 100");
    	Thread.sleep(500); // Esperar 500ms para que el scroll se complete
    	System.out.println("Deslizamiento hacia arriba completado.");

    	// Imprimir los códigos de las canastillas seleccionadas
    	System.out.println("Código de la canastilla seleccionada para ingresar: " + codigosCanastillas);

    	// Llamar al método CapturaContenedor para la canastilla
    	for (String codigo : codigosCanastillas) {
    	System.out.println("Ingresando código: " + codigo);
    	CapturaContenedor(codigo); // Llamada al método CapturaContenedor
    	Thread.sleep(500); // Esperar 500ms entre capturas por seguridad
    	au.driver.hideKeyboard(); // Ocultar teclado después de ingresar el código
    	System.out.println("Código ingresado y teclado ocultado.");
    	}

    	} catch (NoSuchElementException e) {
    	System.out.println("El contenedor de canastillas secos no está presente en la interfaz. Finalizando proceso.");
    	return; // Salir si el contenedor no está presente
    	}

    	// Finalizar proceso
    	System.out.println("\n--------- Finalización del método para ingresar una canastilla de secos ---------\n");
    	// Fin del método recepcionarUnaCanastillaSecos
    	}
    
    /**
     * Método para recepcionar una canastilla de refrigerados
     *
     * Función para recepcionar la primer canastilla del tipo "Refrigerados" que se encuentre en el listado de mostrar detalles. 
     * Si no hay canastillas refrigerados, no truena el script ya que tiene varios manejos de errores para solucionarlo 
     * El único pre-requisito es tener las 3 fotos de evidencia ya tomadas.
     *
     * @author Marcelo Gutiérrez
     * @date   24-10-2024
     * @throws FrameworkException
     */
    
    	public void recepcionarUnaCanastillaRefrigerados() throws InterruptedException, IOException {
    	// Función para recepcionar la primer canastilla del tipo "Refrigerados" que se encuentre en el listado de mostrar detalles. 
        // Si no hay canastillas refrigerados, no truena el script ya que tiene varios manejos de errores para solucionarlo.
        // El único pre-requisito es tener las 3 fotos de evidencia ya tomadas.
        // Creado: Marcelo Gutiérrez Fecha: 24-10-2024

    	// Iniciar proceso de recepción de canastilla de refrigerados
    	System.out.println("Iniciando el proceso de recepción de una canastilla de refrigerados del pedido...");

    	// Hacer scroll hasta el botón "Mostrar Detalles"
    	System.out.println("Haciendo scroll hasta el botón 'Mostrar Detalles'...");
    	au.scrollToElement(AppiumBy.id("com.femco.oxxo.operaciones.mitienda.debug:id/details"));
    	Thread.sleep(1000); // Esperar 1000ms para asegurarse de que el scroll se complete
    	Runtime.getRuntime().exec("adb shell input swipe 20 1003 20 900 90");
    	Thread.sleep(1000); // Esperar 1000ms para asegurarse de que el scroll se complete
    	System.out.println("Scroll completado al botón 'Mostrar Detalles'.");

    	// Verificar si el botón "Mostrar Detalles" ya ha sido presionado
    	System.out.println("Verificando el estado del botón 'Mostrar Detalles'...");
    	try {
    	WebElement detallesElemento = au.driver.findElement(AppiumBy.xpath("//android.widget.TextView[@resource-id='com.femco.oxxo.operaciones.mitienda.debug:id/details']"));
    	String estadoDetallesTexto = detallesElemento.getText();
    	if (estadoDetallesTexto.equals("OCULTAR DETALLES")) {
    	System.out.println("El botón ya ha sido presionado, se omite el clic en 'Mostrar Detalles'.");
    	} else {
    	System.out.println("Haciendo clic en 'Mostrar Detalles'...");
    	au.click(CEDIS_btn_MostrarDetalles);
    	Thread.sleep(1000); // Esperar a que el clic se procese
    	System.out.println("Clic en 'Mostrar Detalles' realizado.");
    	}
    	} catch (NoSuchElementException e) {
    	System.out.println("No se encontró el estado del botón 'Mostrar Detalles'. Procediendo a hacer clic.");
    	System.out.println("Haciendo clic en 'Mostrar Detalles'...");
    	au.click(CEDIS_btn_MostrarDetalles);
    	Thread.sleep(1000); // Esperar a que el clic se procese
    	System.out.println("Clic en 'Mostrar Detalles' realizado.");
    	}

    	// Localizar el elemento que tiene el texto "Refrigerados"
    	System.out.println("Buscando el elemento con el texto 'Refrigerados'...");
    	Runtime.getRuntime().exec("adb shell input swipe 20 1003 20 900 90");
    	Thread.sleep(1000); // Esperar a que se procese el swipe
    	WebElement elementoRefrigerados;
    	try {
    	elementoRefrigerados = au.driver.findElement(AppiumBy.xpath("(//android.widget.TextView[@text='Refrigerados'])[1]"));
    	System.out.println("Elemento 'Refrigerados' encontrado.");
    	} catch (NoSuchElementException e) {
    	System.out.println("No se encontró el elemento 'Refrigerados'. Finalizando proceso.");
    	return; // Salir si no se encuentra el elemento
    	}

    	// Obtener el hermano que contiene el número de canastillas de refrigerados
    	System.out.println("Obteniendo el elemento que contiene el número total de canastillas de refrigerados...");
    	WebElement numeroCanastillasElemento;
    	try {
    	numeroCanastillasElemento = au.driver.findElement(AppiumBy.xpath("//android.widget.TextView[@resource-id='com.femco.oxxo.operaciones.mitienda.debug:id/lblRefriDet']"));
    	System.out.println("Elemento que contiene el número de canastillas de refrigerados encontrado.");
    	} catch (NoSuchElementException e) {
    	System.out.println("No se encontró el elemento que contiene el número de canastillas de refrigerados. Finalizando proceso.");
    	return; // Salir si no se encuentra el elemento
    	}

    	// Extraer el texto que contiene el número de canastillas de refrigerados
    	String numeroCanastillasTexto = numeroCanastillasElemento.getText();
    	int numeroCanastillas = Integer.parseInt(numeroCanastillasTexto.trim()); // Convertir el texto a número
    	System.out.println("Número de canastillas de refrigerados encontrado: " + numeroCanastillas);

    	// Validar si el número de canastillas es 0
    	if (numeroCanastillas == 0) {
    	System.out.println("No hay canastillas de refrigerados para recepcionar (número de canastillas es 0). Finalizando proceso.");
    	return; // Salir si no hay canastillas
    	}

    	System.out.println("Número total de canastillas de refrigerados encontradas: " + numeroCanastillas);

    	// Verificar si el contenedor de canastillas de refrigerados está presente antes de hacer scroll
    	try {
    	System.out.println("Haciendo scroll hasta el contenedor de las canastillas de refrigerados...");
    	au.scrollToElement(AppiumBy.id("com.femco.oxxo.operaciones.mitienda.debug:id/recyclerDetail"));
    	Thread.sleep(300); // Esperar a que el scroll se complete
    	System.out.println("Scroll completado al contenedor de canastillas de refrigerados.");

    	// Ejecutar swipe para asegurar visibilidad
    	System.out.println("Ejecutando swipe para mejorar visibilidad...");
    	Runtime.getRuntime().exec("adb shell input swipe 20 1003 20 900 90");
    	Thread.sleep(300); // Esperar 300ms para que el swipe se complete
    	System.out.println("Swipe realizado con éxito.");

    	// Obtener todas las canastillas de refrigerados dentro del contenedor
    	System.out.println("Obteniendo lista de canastillas de refrigerados...");
    	List<WebElement> canastillas = au.driver.findElements(AppiumBy.xpath("//android.widget.FrameLayout[@resource-id='com.femco.oxxo.operaciones.mitienda.debug:id/card_view']"));
    	Thread.sleep(500); // Esperar a que se carguen las canastillas
    	System.out.println("Número de canastillas de refrigerados encontradas en el contenedor: " + canastillas.size());

    	// Crear una lista para almacenar los códigos de las canastillas
    	ArrayList<String> codigosCanastillas = new ArrayList<>();

    	// Iterar sobre las canastillas y extraer los códigos
    	System.out.println("Extrayendo códigos de las canastillas de refrigerados...");
    	if (!canastillas.isEmpty()) { // Verificar si hay canastillas disponibles
    	WebElement canastilla = canastillas.get(0); // Solo toma la primera canastilla
    	try {
    	String codigo = canastilla.findElement(AppiumBy.xpath(".//android.widget.LinearLayout/android.widget.TextView[2]")).getText();
    	codigosCanastillas.add(codigo);
    	System.out.println("Código de la canastilla de refrigerados a recepcionarse: " + codigo);
    	} catch (NoSuchElementException e) {
    	System.out.println("Error al encontrar el código en la canastilla de refrigerados.");
    	}
    	} else {
    	System.out.println("No se encontraron canastillas de refrigerados para procesar.");
    	return; // Salir si no hay canastillas
    	}

    	// Deslizar hacia arriba
    	System.out.println("Deslizando hacia arriba...");
    	Runtime.getRuntime().exec("adb shell input swipe 100 500 100 1450 100");
    	Thread.sleep(500); // Esperar 500ms para que el scroll se complete
    	System.out.println("Deslizamiento hacia arriba completado.");

    	// Imprimir los códigos de las canastillas seleccionadas
    	System.out.println("Código de la canastilla seleccionada para ingresar: " + codigosCanastillas);

    	// Llamar al método CapturaContenedor para la canastilla
    	for (String codigo : codigosCanastillas) {
    	System.out.println("Ingresando código: " + codigo);
    	CapturaContenedor(codigo); // Llamada al método CapturaContenedor
    	Thread.sleep(500); // Esperar 500ms entre capturas por seguridad
    	au.driver.hideKeyboard(); // Ocultar teclado después de ingresar el código
    	System.out.println("Código ingresado y teclado ocultado.");
    	}

    	} catch (NoSuchElementException e) {
    	System.out.println("El contenedor de canastillas de refrigerados no está presente en la interfaz. Finalizando proceso.");
    	return; // Salir si el contenedor no está presente
    	}

    	// Finalizar proceso
    	System.out.println("\n--------- Finalización de método para ingresar una canastilla de refrigerados del pedido ---------\n");
    	// Fin del método recepcionarUnaCanastillaRefrigerados
    	}  
    	
    	/**
         * Método para validar  mensaje de canastilla que no pertenece a pedido
         *
         * Verificar que se muestre el mensaje de advertencia correcto cuando una canastilla ingresada no pertenece al pedido en curso.
         *
         * @author Ricardo Hdz
         * @date   30/01/2025
         * @throws FrameworkException
         */
    	
    	 public void ValidacionMsjCanastillaNoPertenecePedido() throws InterruptedException { //ricardo 30/01/2025
 	        // Obtener el texto mostrado en la aplicación
 	        WebElement elemento = au.driver.findElement(CEDIS_LeyendaCanastillaNoEncontradoPedido);
 	        String mensajeMostrado = elemento.getText().trim(); // Eliminar espacios al inicio y al final
 	        // Texto esperado
 	        String txtWarning = "La canastilla que ha ingresado no se encuentra dentro del pedido";
 	               
 	        // Normalizar los textos para comparar sin espacios adicionales
 	        String mensajeMostradoNormalizado = mensajeMostrado.replaceAll("\\s+", " ").trim();
 	        String txtWarningNormalizado = txtWarning.replaceAll("\\s+", " ").trim();

 	        // Imprimir mensajes para depuración
 	        System.out.println("Mensaje mostrado (normalizado): " + mensajeMostradoNormalizado);
 	        System.out.println("Mensaje esperado (normalizado): " + txtWarningNormalizado);

 	        // Validar si el mensaje mostrado no coincide con el mensaje esperado
 	       Thread.sleep(3000);
 	        if (!mensajeMostradoNormalizado.equals(txtWarningNormalizado)) {
 	            System.out.println("El mensaje es incorrecto.");
 	            // Registrar evidencia cuando el mensaje es incorrecto
 	            testCase.addTextEvidenceCurrentStep("Se muestra el mensaje de manera incorrecta.");
 	            // Usar un assertion para fallar la prueba si el mensaje es incorrecto
 	            Assert.fail("El mensaje mostrado no coincide con el mensaje esperado.");
 	        } else {
 	            System.out.println("Despliegue del mensaje - Correcto");
 	            // Registrar evidencia cuando el mensaje es correcto
 	        }
    	 }
    	 
    	 /**
    	     * Método para validar mensaje
    	     *
    	     * Verificar que el mensaje mostrado al intentar marcar todo un pedido como faltante sea el correcto.
    	     *
    	     * @author Vanessa Torres
    	     * @date 
    	     * @throws FrameworkException
    	     */
    	 

    public void ValidarMensaje() throws InterruptedException {
    	
    	//Funcion para validar el texto de un mensaje, posiblemente este metodo sea cambiado 
    	//Vanessa Torres
    		 
    	Thread.sleep(2000);
	    System.out.println("Esperando 2 segundo antes de obtener la descripción del artículo.");

	    // Obtener el texto del mensaje
	    String mensaje = au.driver.findElement(CEDIS_LeyendaTodoComoFaltante).getText();
	    System.out.println("Mensaje obtenido: " + mensaje);

	    // mensaje esperado
	    String mensajetexto = "No se puede marcar todo el pedido como faltante, favor de corroborar los bultos del pedido.";
	    System.out.println("Texto esperado: " + mensajetexto);

	    // Validar si el mensaje es el correcto
	    if (mensaje.equals(mensajetexto)) {
	        System.out.println("Se muestra el mensaje de manera correcta.");
	    } else {
	        System.err.println("Se muestra el mensaje de manera incorrecta.");
	        testCase.addTextEvidenceCurrentStep("Se muestra mensaje de manera incorrecta");
	    }
    	 };
    	 
    	 /**
    	     * Método para continuar hasta el resumen
    	     *
    	     * realizar la navegación desde la sección de cajas sin etiqueta hasta llegar a la pantalla final de resumen de entrega.
    	     *
    	     * @author Juan Ibarra
    	     * @date 
    	     * @throws FrameworkException
    	     */
    	 
    public void ContinuarHastaResumen() throws InterruptedException
    	    {  	
    	    	//Funcion en la cual se avanza desde cajas sin etiqueta hasta el resumen
    	    	au.scrollToElement(CEDIS_btnContinuarCajaSinEtiqueta);
    	    	au.click(CEDIS_btnContinuarCajaSinEtiqueta); 
    	    	Thread.sleep(300);
    	    	if(au.driver.findElement(CEDIS_btn_Confirmar).isEnabled()) {
    	    		au.click(CEDIS_btn_Confirmar);
    	    	}
    	    	Thread.sleep(300);
    	    	//if(au.driver.findElement(CEDIS_btn_No).isDisplayed()) {
    	    	if (au.driver.findElements(CEDIS_btn_No).isEmpty()!= true) {//Actualización de validación en botones a mostrar
    	    		au.click(CEDIS_btn_No);
    	    	}
    	    	Thread.sleep(300);
    	    	//if(au.driver.findElement(CEDIS_btn_Confirmar).isEnabled()) {
    	    	if (au.driver.findElements(CEDIS_btn_Confirmar).isEmpty()!= true) {//Actualización de validación en botones a mostrar
    	    		au.click(CEDIS_btn_Confirmar);
    	    	}	
    	    	au.scrollToElement(CEDIS_ContinuarSobrantes);
    	    	au.click(CEDIS_ContinuarSobrantes);
    	    	au.scrollToElement(CEDIS_TerminarDanados);
    	    	au.click(CEDIS_TerminarDanados);
    	    	Thread.sleep(300);
    	    	//if(au.driver.findElement(CEDIS_btn_Confirmar).isEnabled()) {
    	    	if (au.driver.findElements(CEDIS_btn_Confirmar).isEmpty()!= true) {//Actualización de validación en botones a mostrar
    	    		au.click(CEDIS_btn_Confirmar);
    	    	}    	
    	    }
    
    /**
     * Método para validar articulos sin etiqueta
     *
     * Este método obtiene el texto que corresponde a la información del artículo sin etiqueta en la pantalla actual de la aplicación.
     *
     * @author Juan Ibarra
     * @date 
     * @throws FrameworkException
     */
    
    public void ArticuloSinEtiqueta() {
    	
    	String articulosinetiqueta = au.driver.findElement(CEDIS_ArticuloSinetiqueta).getText();
    	
    }
    
    /**
     * Método para validar teclas
     *
     * Enviar una secuencia de caracteres simulando la pulsación física de teclas en un dispositivo Android usando eventos de teclado (key events) a través de Appium.
     *
     * @author Juan Ibarra
     * @date 
     * @throws FrameworkException
     */

	public void sendKeysUsingKeyEvents(AppiumUtil au, String text) {
	    Map<Character, AndroidKey> keyMap = new HashMap<>();
	    keyMap.put('0', AndroidKey.DIGIT_0);
	    keyMap.put('1', AndroidKey.DIGIT_1);
	    keyMap.put('2', AndroidKey.DIGIT_2);
	    keyMap.put('3', AndroidKey.DIGIT_3);
	    keyMap.put('4', AndroidKey.DIGIT_4);
	    keyMap.put('5', AndroidKey.DIGIT_5);
	    keyMap.put('6', AndroidKey.DIGIT_6);
	    keyMap.put('7', AndroidKey.DIGIT_7);
	    keyMap.put('8', AndroidKey.DIGIT_8);
	    keyMap.put('9', AndroidKey.DIGIT_9);
	    keyMap.put('B', AndroidKey.BACK);
	    keyMap.put('E', AndroidKey.ENTER);
	 
	    for (char c : text.toCharArray()) {
	        AndroidKey key = keyMap.get(c);
	        if (key != null) {
	            au.driver.pressKey(new KeyEvent(key));
	        }
	    }
	}
	
	/**
     * Método para validar deslizar arriba
     *
     * realizar deslizamiento hacia arriba en un dispositivo Android mediante un comando ADB para desplazar la pantalla hacia arriba.
     *
     * @author Juan Ibarra
     * @date 
     * @throws FrameworkException
     */
	
	public void subir () throws IOException, InterruptedException {
		
		Runtime.getRuntime().exec("adb shell input swipe 100 180 100 950 200");
		Thread.sleep(2000);
		
	}

	/**
     * Método para validar mensaje de salida
     *
     * Funcion que permite validar la descripción del mensaje de Desea Salir 
     *
     * @author Juan Ibarra
     * @date   12-Feb-2025
     * @throws FrameworkException
     */
	
    public void ValidarMensajeSalida() throws InterruptedException{  	
    	/*Funcion que permite validar la descripción del mensaje de Desea Salir 	
    	Creado: Juan Ibarra		
    	Fecha Creación: 12-Feb-2025
    	 */    	
    	Thread.sleep(1000);
        WebElement elemento = au.driver.findElement(CEDIS_LeyendaSeguroSalir);
        String mensajeMostrado = elemento.getText().trim(); // Eliminar espacios al inicio y al final
        // Texto esperado
        String txtWarning = "¿Estas seguro que deseas salir? Se guardarán solo las fotografías de entrega y los bultos escaneados hasta el momento.";

        // Normalizar los textos para comparar sin espacios adicionales
        String mensajeMostradoNormalizado = mensajeMostrado.replaceAll("\\s+", " ").trim();
        String txtWarningNormalizado = txtWarning.replaceAll("\\s+", " ").trim();

        // Imprimir mensajes para depuración
        System.out.println("Mensaje mostrado (normalizado): " + mensajeMostradoNormalizado);
        System.out.println("Mensaje esperado (normalizado): " + txtWarningNormalizado);

        // Validar si el mensaje mostrado no coincide con el mensaje esperado
        if (!mensajeMostradoNormalizado.equals(txtWarningNormalizado)) {
        	baseExecution.addStep("El mensaje es incorrecto");        
    		Assert.fail("El mensaje mostrado no coincide con el mensaje esperado, se muestra " +mensajeMostradoNormalizado , null);    		
        } else {
            System.out.println("El mensaje es correcto.");
        }    
 
      }
    
    /**
     * Método para validar  guardado de las tres fotografias
     *
     * Valida que el contenedor de evidencias tenga tres fotografías (elementos de tipo android.view.ViewGroup).
     *
     * @author Jesús Marcelo Gutiérrez Bravo
     * @date   19/02/2025
     * @throws FrameworkException
     */
    
    public void ValidarGuardadoDeLasTresFotografias() throws InterruptedException {
        // Descripción: Valida que el contenedor de evidencias tenga tres fotografías (elementos de tipo android.view.ViewGroup).
        // Autor: Jesús Marcelo Gutiérrez Bravo
        // Fecha: 19/02/2025

        Thread.sleep(1000);
        
        try {
            WebElement contenedor = au.driver.findElement(CEDIS_ContenedorPadreDeLasTresFotografiasDeEvidencia);
            List<WebElement> evidencias = contenedor.findElements(By.className("android.view.ViewGroup"));
            int cantidad = evidencias.size();
            
            if (cantidad == 3) {
                System.out.println("Despliegue de tres evidencias - Correcto");
            } else if (cantidad == 1 || cantidad == 2) {
                // Si solo se han desplegado 1 o 2 evidencias, se considera fallo porque deberían ser tres
                baseExecution.addStep("Solo se ha desplegado " + cantidad + " evidencia cuando deberían ser tres.");
                Assert.fail("Solo se ha desplegado " + cantidad + " evidencia cuando deberían ser tres.");
                testCase.addScreenShotCurrentStep(au, "testImage");
            } else {
                // Si no se ha desplegado ninguna evidencia
                baseExecution.addStep("No se ha desplegado ninguna evidencia por recepcionar");
                Assert.fail("No se ha desplegado ninguna evidencia por recepcionar");
                testCase.addScreenShotCurrentStep(au, "testImage");
            }
        } catch (NoSuchElementException e) {
            baseExecution.addStep("No se encontró el contenedor de evidencias.");
            Assert.fail("No se encontró el contenedor de evidencias.");
            testCase.addScreenShotCurrentStep(au, "testImage");
        }
    }
    
    /**
     * Método para validar aumento de bulto en total de entrega
     *
     * Valida que el Total Entregado sea mayor a 0, es decir, que se hayan aumentado la cantidad de bultos.
     *
     * @author Jesús Marcelo Gutiérrez Bravo
     * @date   19/02/2025
     * @throws FrameworkException
     */
    
    public void ValidarAumentoDeBultosEnTotalEntregado() throws InterruptedException {
        // Descripción: Valida que el Total Entregado sea mayor a 0, es decir, que se hayan aumentado la cantidad de bultos.
        // Autor: Jesús Marcelo Gutiérrez Bravo
        // Fecha: 19/02/2025

        Thread.sleep(1000);
        
        try {
            // Localiza el elemento que contiene el Total Entregado de bultos
            WebElement elemento = au.driver.findElement(CEDIS_TotalEntregadoBultos);
            // Obtiene el texto y lo limpia de espacios en blanco
            String valorTexto = elemento.getText().trim();
            // Convierte el texto a número
            int totalBultos = Integer.parseInt(valorTexto);
            
            // Lógica de validación
            if (totalBultos > 0) {
                System.out.println("Total Entregado de bultos: " + totalBultos + " mayor a 0, Correcto");
            } else {
                baseExecution.addStep("El total entregado de bultos es " + totalBultos + ", se esperaba un valor mayor a 0.");
                Assert.fail("El total entregado de bultos es " + totalBultos + ", se esperaba un valor mayor a 0.");
                testCase.addScreenShotCurrentStep(au, "testImage");
            }
        } catch (NoSuchElementException e) {
            baseExecution.addStep("No se encontró el elemento de Total Entregado de bultos.");
            Assert.fail("No se encontró el elemento de Total Entregado de bultos.");
            testCase.addScreenShotCurrentStep(au, "testImage");
        } catch (NumberFormatException e) {
            baseExecution.addStep("El valor del Total Entregado de bultos no es un número válido: " + e.getMessage());
            Assert.fail("El valor del Total Entregado de bultos no es un número válido: " + e.getMessage());
            testCase.addScreenShotCurrentStep(au, "testImage");
        }
    }
    
    /**
     * Método para validar  ingreso de dañados
     *
     * Método para validar la pantalla "Resumen de Devolución" verificando la tabla de Contenedores.
     * Se revisan las leyendas de cada sección y se valida que la suma de los valores sea correcta.
     *
     * @author Jesus Marcelo Gutierrez Bravo
     * @date   27-01-2025
     * @throws FrameworkException
     */
    
    public void ValidacionIngresoDanados(int CantidadDanadoCorrecto) throws IOException, InterruptedException {
        // Descripción: Método para validar la pantalla "Resumen de Devolución" verificando la tabla de Contenedores.
        // Se revisan las leyendas de cada sección y se valida que la suma de los valores sea correcta.
        // Autor: Jesus Marcelo Gutierrez Bravo
        // Fecha Creación: 27-01-2025

        // Se inicializa WebDriverWait con un tiempo de espera máximo de 10 segundos
        WebDriverWait wait = new WebDriverWait(au.driver, Duration.ofSeconds(10));
        int CantidadDanadoTotal = obtenerValorNumerico(wait, CEDIS_CantidadTotalDanados);

        // 🔹 Validar que la suma de "Tapas de Canastillas" + "Envases Vacíos" sea igual al "Total de Contenedores"
        if (CantidadDanadoTotal != CantidadDanadoCorrecto) {
        	 baseExecution.addStep("El valor del Total de Dañados es Incorrecto: " + CantidadDanadoTotal + ". Se esperaba: " + CantidadDanadoCorrecto);
             Assert.fail("El valor del Total de Dañados es Incorrecto: " + CantidadDanadoTotal + ". Se esperaba: " + CantidadDanadoCorrecto);
             testCase.addScreenShotCurrentStep(au, "testImage");
        }
        System.out.println("Despliegue del ingreso y total: Dañados - Correcto");
    }
    
    private int obtenerValorNumerico(WebDriverWait wait, By locator) {
        // Esperar hasta que el elemento sea visible y obtener su texto
        WebElement elemento = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
        String texto = elemento.getText().trim();

        // Intentar convertir el texto a número
        try {
            return Integer.parseInt(texto);
        } catch (NumberFormatException e) {
            baseExecution.addStep("Error al convertir el valor: " + texto);
            Assert.fail("El valor obtenido no es un número válido: " + texto);
            return 0; // Este retorno nunca se ejecutará porque `Assert.fail()` detendrá la ejecución
        }
    }
    
    /**
     * Método para validar seccion contenedor
     *
     * Validar que la sección de "Contenedor" esté visible en la pantalla de la aplicación.
     *
     * @author Juan Ibarra
     * @date 
     * @throws FrameworkException
     */
    
	public void ValidarSeccionContenedor() {
		WebElement Elemento = au.driver.findElement(CEDIS_LeyendaContenedor);
	    if (!Elemento.isDisplayed()) {
	        // Si el elemento no está visible, registrar el fallo y tomar captura de pantalla
	        baseExecution.addStep("No se ha desplegado correctamente la seccion: Contenedor");        
	        Assert.fail("No se ha desplegado correctamente la seccion: Contenedor");
	        testCase.addScreenShotCurrentStep(au, "testImage");
	    }     
	    System.out.println("Despliegue de la seccion Contenedor - Correcto");
	}
	
	/**
     * Método para validar teclado desplegado
     *
     * Verificar si el teclado virtual del dispositivo está desplegado en el momento esperado.
     *
     * @author Juan Ibarra
     * @date 
     * @throws FrameworkException
     */
	
	public void validarTecladoDesplegado() {
	    try {
	        // Obtener la altura del teclado en pantalla
	        boolean isKeyboardShown = au.driver.isKeyboardShown();
	        
	        if (isKeyboardShown) {
	            System.out.println("Despliegue del Teclado - Correcto");
	        } else {
	            System.out.println("\nEl teclado NO está desplegado.");
	            // Registrar fallo en el caso de prueba
	            baseExecution.addStep("El teclado no está desplegado cuando se esperaba que lo estuviera.");
	            Assert.fail("El teclado no está desplegado.");
	            testCase.addScreenShotCurrentStep(au, "testImage");
	        }
	    } catch (Exception e) {
	        // Capturar cualquier error al intentar detectar el teclado
	        System.out.println("Error al intentar validar el estado del teclado: " + e.getMessage());
	        baseExecution.addStep("Error al validar el teclado: " + e.getMessage());
	        Assert.fail("Error inesperado al validar el teclado.");
	    }
	}
}    