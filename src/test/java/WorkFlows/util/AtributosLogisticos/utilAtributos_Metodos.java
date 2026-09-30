package WorkFlows.util.AtributosLogisticos;

import java.io.File;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.sikuli.script.FindFailed;
import org.sikuli.script.Pattern;
import org.sikuli.script.Screen;

import WorkFlows.AtributosLogisticos.ActualizacionBPM.MTC_FT_002_FlujoFA_CedisProveedorExistente_ActualizacionBPM;
import modelo.TestCase;
import utils.SikuliUtil;
import utils.selenium.SeleniumUtil;

public class utilAtributos_Metodos {
	SeleniumUtil u;
	TestCase testCase;
	
	private MTC_FT_002_FlujoFA_CedisProveedorExistente_ActualizacionBPM pom ;
	
	private SikuliUtil sikuli = new SikuliUtil();
    private Screen screen = new Screen();    
    
    private WebDriverWait wait;
    private Pattern p1;
    private String s1;
    private String folder = "WorkFlows"+File.separator+"AtributosLogisticos";
	
	
	/**
	 * Constructor
	 ******************************************************************************************/
	
	public utilAtributos_Metodos(SeleniumUtil u, TestCase testCase) {
		super();
		this.u = u;
		this.testCase = testCase;
		
	}
	
	
	 
	
	By selectAltaArticulos = By.cssSelector("#ddtopmenubar > ul:nth-child(1) > li:nth-child(2) > a:nth-child(1)");
	By selectAltaProductos = By.linkText("Alta de productos");
	
	By buttonNuevo = By.xpath("/html/body/form[1]/table[3]//tr/td[1]");
	By buttonAlert = By.id("#form\\:growl_container");
	By radioAcepto = By.cssSelector("#form\\:chkAcepto > tbody:nth-child(1) > tr:nth-child(1) > td:nth-child(1) > div:nth-child(1) > div:nth-child(2)");
	
	By chkCb = By.cssSelector("#form\\:chkTipoCodigo > tbody:nth-child(1) > tr:nth-child(1) > td:nth-child(2) > label:nth-child(1)");
	By inputCb = By.xpath("//*[@id=\"form:txtCodigoBarras\"]");
	By inputDescripcionCorta = By.id("form:txtDescCorta");
	By inputDescripcion = By.id("form:txtDesc");
	By selectSuperGrupo = By.cssSelector("#form\\:cmbSuperGpo_label");
	By selectCategoria = By.id("form:cmbCategoria_label");
	By inputArticuloEspejo = By.id("form:txtArtEspejoSimi");
	
	/// Informacion Logistica///
		By selectTipoNegocio = By.id("form:cmbTipoNegocio_label");
		By selectAlmacenado = By.id("form:cmbAlmacenadoProducto_label");
		By selectTipoProducto = By.id("form:cmbTipoProducto_label");
		By inputVida = By.id("form:txtVidaProd");
		By checkBoxDescuento = By.cssSelector("#form\\:chkDevolucion > tbody:nth-child(1) > tr:nth-child(1) > td:nth-child(1) > div:nth-child(1) > div:nth-child(2)");
		By checkBoxRecoleccion = By.xpath("/html/body/form[1]/table[4]/tbody/tr[2]/td/table/tbody/tr[1]/td[2]/div/div[2]/table[3]/tbody/tr[3]/td/div/div/table/tbody/tr/td[3]/div/div[2]");
		By inputCostoNeto = By.id("form:txtCostoNetoPieza");
		By inputVentaTienda = By.id("form:txtVentaProm");
		By inputCostoCaja = By.id("form:txtCostoNetoCaja");
		
		///Informacion comercial y de contenido///
		By selectFrecuenciaVisita = By.id("form:cmbfrecuencia_label");
		By selectPresentacion = By.id("form:cmbPresentacion_label");
		By inputContenido = By.id("form:txtContenido");
		By selectMedidaContenido = By.id("form:cmbUnidad_label");
		
		/// Propuesta logistica por articulo///
		By inputPlazoPago = By.id("form:txtPlazoPagoDias");
		By inputClausula = By.id("form:txtClausulas");
		By inputComentarios = By.id("form:txtComentarios");

		///Marcas descriptivas de la muestra fisica////
		By selectMarcaProveedor = By.id("form:cmbMcaProv_label");
		By selectMarcaArticulo = By.id("form:cmbMcaArt_label");
		By buttonFoto = By.id("form:btnFoto");
		By buttonContinuarFoto = By.id("loadFile:j_idt329");
		By buttonBrowseFoto = By.id("loadFile:flImagenSoporte");
		By buttonAgregarFoto = By.id("loadFile:chargeBtnSoporte");
		By buttonCbFoto = By.id("form:btnCod");
		By buttonContinuarCb = By.id("loadFile:j_idt340");
		By buttonCarta = By.id("form:btnCarta");
		
		/// Guardar y continuar///
		By buttonGuardarProducto = By.id("form:btnGrdDatosBas"); 
		By buttonSigProducto = By.id("form:btnSiguiente");
		
		//Seleccion de alcance
		By chkSurtido = By.cssSelector("#form\\:idViaSurtido > tbody:nth-child(1) > tr:nth-child(1) > td:nth-child(1) > div:nth-child(1) > div:nth-child(2)");
		By listaFuente = By.id("form:listaFuentes");
		By chkTipoPlaza = By.cssSelector("#form\\:idTipoPlz > tbody:nth-child(1) > tr:nth-child(1) > td:nth-child(1) > div:nth-child(1) > div:nth-child(2)");
		By listaPlazas = By.id("form:j_idt55");
		
		//Descuentos
		By inputCostoBruto = By.id("form:costo");
		By buttonCapturar = By.id("form:j_idt86");
		By chkDescuento = By.cssSelector("#form\\:tblAlcances\\:0\\:j_idt113 > div:nth-child(2)");
		By buttonAgregarDescuento = By.id("form:j_idt91");
		
		By buttonSiguienteAlcance = By.id("form:j_idt143");
		
		///Informacion de la fuente de abasto///
		// Volumetria
		By inputFondoEach = By.id("form:j_idt45:eachFondo");
		By inputAnchoEach = By.id("form:j_idt45:eachA");
		By inputAltoEach = By.id("form:j_idt45:eachAl");
		By inputPesoBrutoEach = By.id("form:j_idt45:eachPB");
		By inputPesoNetoEach = By.id("form:j_idt45:eachPN");
		By buttonFotoEach = By.cssSelector("#form\\:j_idt45\\:j_idt67 > span:nth-child(1)");
		
		//Inner
		By inputFondoInner = By.id("form:j_idt45:innerF");
		By inputAnchoInner = By.id("form:j_idt45:innerA");
		By inputAltoInner = By.id("form:j_idt45:innerAl");
		By inputPesoBrutoInner = By.id("form:j_idt45:innerPB");
		By inputPesoNetoInner = By.id("form:j_idt45:innerPN");
		By buttonFotoInner = By.id("form:j_idt45:j_idt93");
		

	
	public void InformacionArticulo(String CodigoDeBarras, String DescripcionCorta, String Descripcion, String SuperGrupo, String Categoria, String	articuloEspejo, String TipoDeNegocio, String AlmacenadoDeProducto, String TipoDeProducto, String TiempoVida, String CostoPorPieza, String	VentaEsperada, String CostoPorCaja, String FrecuenciaDeVisitaSemanal, String Presentacion, String Contenido, String UnidadMedidaContenido, String PlazoPago, String	ClausulasRevisar, String Comentarios){
		try {
			u.highLight(selectAltaArticulos);
			u.mouseOver(selectAltaArticulos);
			testCase.addTextEvidenceCurrentStep("Seleccionar la opcion Alta de Productos");
			//u.highLight(selectAltaProductos);
			testCase.addScreenShotCurrentStep("Alta Productos");
			u.mouseOverAndClick(selectAltaProductos);
			
			u.hardWait(10);
			
			try {
				WebElement frame = u.getDriver().findElement(By.cssSelector("#contentFrame > iframe:nth-child(2)"));
				u.getDriver().switchTo().frame(frame);
			}
			catch(Exception e) {
				System.out.println(" -> " + e);
			}
			
			
			u.waitWebElement(buttonNuevo, 30, 2);
			//testCase.addScreenShotCurrentStep("Seleccionar el boton Nuevo");
			u.click(buttonNuevo);
			//clickBtnNuevo();
			
			
			try {
				WebElement frame = u.getDriver().findElement(By.cssSelector("#contentFrame > iframe:nth-child(2)"));
				u.getDriver().switchTo().frame(frame);
			}
			catch(Exception e) {
				System.out.println(" -> " + e);
			}

			u.waitWebElement(radioAcepto, 110, 2);
			testCase.addTextEvidenceCurrentStep("Seleccionar la opcion Acepto");
			u.highLight(radioAcepto);
			u.click(radioAcepto);
			//clickBtnAcepto();
			 
			u.hardWait(3);
			u.click(chkCb);
			//clickBtnTipoCB();
			
			//WebElement Forms = u.getDriver().findElement(By.id("form"));
			//u.getDriver().switchTo().frame(Forms);
			u.highLight(inputCb);	
			u.sendKeys(inputCb, CodigoDeBarras);
			//inputCodigoB(CodigoDeBarras);
			
			u.highLight(inputDescripcionCorta);
			u.sendKeys(inputDescripcionCorta, DescripcionCorta);
			//inputDescrip(Descripcion);
			
			u.highLight(inputDescripcion);
			u.sendKeys(inputDescripcion, Descripcion);
			//inputDescripcionC(DescripcionCorta);

			
			u.click(selectSuperGrupo);
			By opcionSCategoria = By.xpath("//div[@id='form:cmbSuperGpo_panel']//li[text()='Dulces']");
			u.click(opcionSCategoria);
			//selectSuperG("");
			
			u.hardWait(2);
			u.click(selectCategoria);
			By opcionCategoria = By.xpath("//div[@id='form:cmbCategoria_panel']//li[text()='Chicles']");
			u.click(opcionCategoria);
			//selectCategoria("cho");
			
			u.highLight(inputArticuloEspejo);
			u.sendKeys(inputArticuloEspejo, articuloEspejo);
			
			u.click(selectTipoNegocio);
			By opcionTipoN = By.xpath("//div[@id='form:cmbTipoNegocio_panel']//li[text()='Oxxo']");
			u.click(opcionTipoN);
			
			u.click(selectAlmacenado);
			By opcionAlmacenado = By.xpath("//div[@id='form:cmbAlmacenadoProducto_panel']//li[text()='Seco']");
			u.click(opcionAlmacenado);
			
			u.click(selectTipoProducto);
			By opcionTipoP = By.xpath("//div[@id='form:cmbTipoProducto_panel']//li[text()='De l�nea']");
			u.click(opcionTipoP);
			
			u.highLight(inputVida);
			u.sendKeys(inputVida, TiempoVida);
			
			u.click(checkBoxDescuento);
			
			u.waitWebElement(checkBoxRecoleccion, 30, 2);
			u.click(checkBoxRecoleccion);
			
			u.sendKeys(inputCostoNeto, CostoPorPieza);
			
			u.sendKeys(inputVentaTienda, VentaEsperada);
			
			u.sendKeys(inputCostoCaja, CostoPorCaja);
			
			u.scrollToElement(selectFrecuenciaVisita);
			u.click(selectFrecuenciaVisita);
			By opcionFrecuenciaV = By.xpath("//div[@id='form:cmbfrecuencia_panel']//li[text()='2']");
			u.click(opcionFrecuenciaV);
			
			u.click(selectPresentacion);
			By opcionPresentacion = By.xpath("//div[@id='form:cmbPresentacion_panel']//li[text()='bolsa']");
			u.click(opcionPresentacion);
			
			u.highLight(inputContenido);
			u.sendKeys(inputContenido, Contenido);
			
			u.click(selectMedidaContenido);
			By opcionMedida = By.xpath("//div[@id='form:cmbUnidad_panel']//li[text()='Grams']");
			u.click(opcionMedida);
			
			u.highLight(inputPlazoPago);
			u.sendKeys(inputPlazoPago, PlazoPago);
			
			u.highLight(inputClausula);
			u.sendKeys(inputClausula, ClausulasRevisar);
			
			u.highLight(inputComentarios);
			u.sendKeys(inputComentarios, Comentarios);
			
			u.click(selectMarcaProveedor);
			By opcionProveedor = By.xpath("//div[@id='form:cmbMcaProv_panel']//li[text()='Arcoiris']");
			u.click(opcionProveedor);
			u.click(selectMarcaArticulo);
			By opcionArticulo = By.xpath("//div[@id='form:cmbMcaArt_panel']//li[text()='Arcor']");
			u.click(opcionArticulo);
			
			u.waitWebElement(buttonFoto, 60, 2);
			u.click(buttonFoto);
				
			
			try {
				WebElement frame = u.getDriver().findElement(By.cssSelector("#contentFrame > iframe:nth-child(2)"));
				u.getDriver().switchTo().frame(frame);
			}
			catch(Exception e) {
				System.out.println(" -> " + e);
			}
			
			u.click(buttonContinuarFoto);
			
			//u.getDriver().findElement(By.id("loadFile:flImagenSoporte")).sendKeys("C:\\Users\\68939\\Downloads\\ricolino.jpg");
			
			u.highLight(buttonBrowseFoto);
			u.sendKeys(buttonBrowseFoto, "C:\\Projects\\WF\\FrameworkWorkFlows\\Config\\Input\\Files\\AtributosLogisticos\\ActualizacionBPM\\imgTest.jpg");
			u.click(buttonAgregarFoto);
			
			/*
			WebElement buttonCbFoto = wait.until(ExpectedConditions.elementToBeClickable(By.id("form:btnCod")));
			buttonCbFoto.click();
			*/
			
			/*try {
				WebElement frame = u.getDriver().findElement(By.cssSelector("#contentFrame > iframe:nth-child(2)"));
				u.getDriver().switchTo().frame(frame);
			}
			catch(Exception e) {
				System.out.println(" -> " + e);
			}
			*/
			u.hardWait(5);
			u.scrollToElement(buttonFoto);
			u.waitWebElement(buttonContinuarCb, 30, 3);
			u.click(buttonContinuarCb);
		
		
		}
		catch(Exception e) {
			System.out.println(" -> " + e);
            throw new AssertionError("Error en la captura de informacion del articulo---> "+e);
		}
	}
	
	public void clickBtnNuevo() throws FindFailed, InterruptedException {        
        s1 = sikuli.PatternPath(folder);
        p1 = sikuli.PatternImage(s1 + "botonNuevo.png");
        screen.wait(p1, 30.0);
        screen.click();
        
    }
	
	public void clickBtnAcepto() throws FindFailed, InterruptedException {        
        s1 = sikuli.PatternPath(folder);
        p1 = sikuli.PatternImage(s1 + "botonAcepto.png");
        screen.wait(p1, 30.0);
        screen.click();
        
    }
	
	
	/**
	 * Selecciona el valor esperado en el combo.
	 * 
	 * @param categoria
	 * @author 1000073289
	 */
	
	public void selectSuperGrupo(String supergrupo) {
		selectOption("SuperGrupo", supergrupo, selectSuperGrupo);
	}
	
	public void selectCategoria(String categoria) {
		selectOption("Categoria", categoria, selectCategoria);
	}
	
	/**
	 * Selecciona en el combo el valor esperado.
	 * 
	 * @param opcion    -> Nombre del select
	 * @param valor     -> Valor a seleccionar
	 * @param elementBy -> By Select
	 * @author 1000073289
	 */
	public void selectOption(String opcion, String valor, By elementBy) {
		try {
			Select select = new Select(u.getDriver().findElement(elementBy));
			select.selectByVisibleText(valor);
			u.hardWait(1);
		} catch (Exception e) {
			System.out.println(" -> " + e);
			throw new AssertionError("Falla al seleccionar: " + opcion);
		}
	
	}
	
}