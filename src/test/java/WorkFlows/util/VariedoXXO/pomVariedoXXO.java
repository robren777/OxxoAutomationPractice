package WorkFlows.util.VariedoXXO;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Coordinates;
import org.openqa.selenium.interactions.Locatable;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import modelo.TestCase;
import utils.SikuliUtil;
import utils.selenium.SeleniumUtil;

public class pomVariedoXXO {
	SeleniumUtil u;
	TestCase testCase;
	private WebDriverWait wait;
	SikuliUtil sikuli = new SikuliUtil();
	String pathImages = sikuli.PatternPath("SipreOXXO");
	
	/// WEB ELEMENTS SIPRE Filtros Identificación del artículo
			By filtroSuperGrupo = By.id("superGrupo");
			By filtroCategoria = By.id("categoria ");
			By filtroSubCategoria = By.id("subCategoria");
			By filtroSegmento = By.id("segmento ");
			By filtroSubSegmento = By.id("subSegmento");
			By filtroStoreOrderMultiple = By.id("storeOrderMultiple");
			By filtroUnidadMedida = By.id("unidadMedida");
			By filtroUnidadVenta = By.id("unidadVenta");
			
			By filtroCodigoFiscal = By.id("codigoFiscal");
			By txtPrecioSugerido = By.id("precioSugerido");
			By ChBoxBara = By.id("isBara");
			By imgLupaBase = By.id("ahrefbase");
			By imgLupaExcluir = By.id("ahrefexcluir");
			By imgLupaPeatonal = By.id("ahrefpeatonal");
			By imgLupaTurOasis = By.id("ahreftur/oasis");
			By imgLupaMixtas = By.id("ahrefmixtas");
			By imgLupaPresentacion = By.id("ahrefpresentacion");
			By ReglaPrecio = By.cssSelector("tr.noneSelect:nth-child(1)");
			By BotonAceptarReglaPrecio = By.xpath("//*[contains(text(),'Aceptar')]");
			By ReglaPresentacion = By.cssSelector("tr.noneSelect:nth-child(4) > td:nth-child(1)");
			By BotonAceptarReglaPresentacion = By.xpath("//*[contains(text(),'Aceptar')]");
			By txtContenido = By.id("contenido");
			By filtroUnidadMedidaContenido = By.id("unidadMedidaContenido");
			
			By imgLupaMarcaProveedor = By.id("ahrefmarcaProveedor");
			By filtroMarcaProveedor = By.id("marcaProveedor");
			By imgLupaTextSearchProveedor = By.id("ahreftextSearch");
			By ReglaMarcaProveedor = By.cssSelector("tr.noneSelect:nth-child(1) > td:nth-child(1)");
			By BotonAceptarReglaMarcaProveedor = By.xpath("//*[contains(text(),'Aceptar')]");
			By imgLupaMarcaArticulo = By.id("ahrefmarcaArticulo");
			By filtroMarcaArticulo = By.id("marcaArticulo");
			By imgLupaTextSearchArticulo = By.id("ahreftextSearch");
			By ReglaMarcaArticulo = By.cssSelector("tr.noneSelect:nth-child(1) > td:nth-child(1)");
			By BotonAceptarReglaMarcaArticulo = By.xpath("//*[contains(text(),'Aceptar')]");
			By rbMermaSi = By.id("rbMermaSi");
			By rbCandadoNo = By.id("rbCandadoNo");
			By rbPackItemNo = By.id("rbPackItemNo");
			By BotonSiguinte = By.id("btnSaveFormSelenium");
			By txtudaLibre = By.id("udaLibre");
			By modalMensajeOK = By.xpath("//div[@class='modal-body ng-binding ng-scope']");
			By BotonOK = By.xpath("//button[@name='dismisSeleneiumOK']");
			By tableObjetivo = By.cssSelector("table#tblCO > tbody > tr");
		
	// WEB ELEMENTS ULTIMUS 
	By loadiCargaArchivo = By.xpath("//img[@src='images/loadingAnimation.gif']");
		
		
	public pomVariedoXXO(SeleniumUtil u, TestCase testCase ) {
		super();
		this.u = u;
		this.wait = new WebDriverWait(this.u.getDriver(), Duration.ofSeconds(15));
		this.testCase = testCase;
	}
	
	
	
	/**
	 * Espera a que el elemento se muestre, lo resalta y le da click.
	 * @param opcion    -> Nombre del elemento
	 * @param elementBy -> By Element
	 * @author Misraim Hernandez
	 */
	public void clickOpcion(String opcion, By elementBy) {
		try {
			wait.until(ExpectedConditions.elementToBeClickable(elementBy));
			u.highLight(elementBy);
			u.unhighLight(elementBy);
			u.click(elementBy);
			u.hardWait(1);
		} catch (Exception e) {
			System.out.println(" -> " + e);
			throw new AssertionError("Fallo al dar clic en: " + opcion);
		}
	}
	
	/**
	 * Metodo para hacer un scrollTo hasta un objeto determido
	 * @param ByElement -> By Element
	 * @author Misraim Hernandez
	 */
	public void scrollToElement(By ByElement) {
        try {
            WebElement element = u.getDriver().findElement(ByElement);
            Coordinates coordinate = ((Locatable) element).getCoordinates();
            coordinate.onPage();
            coordinate.inViewPort();
        } catch (Exception e) {
            try {
                WebElement m = u.getDriver().findElement(ByElement);
                ((JavascriptExecutor) u.getDriver()).executeScript("arguments[0].scrollIntoView(true);", m);
            } catch (Exception e2) {
                System.out.println(" -> No se pudo scrollear: " + e);
            }
        }
        
    }
	
	
	/**
	 * Metodo para seleccionar un valor de una lista web
	 * @param opcion - Nombre para identificar el campo 
	 * @param valor - Valor a seleccionara dentro de la lista
	 * @param elementBy - Identificador de lista web
	 * @author Misraim Hernandez 
	 */
	public void selectOption(String opcion, String valor, By elementBy) {
        try {
            Select select = new Select(u.getDriver().findElement(elementBy));
            select.selectByVisibleText(valor);
            u.hardWait(1);
        } catch (Exception e) {
            System.out.println(" -> " + e);
            throw new AssertionError("Fallo al seleccionar: " + opcion);
        }
    }
	
	/**
	 * Espera a que desaparezca  modal de procesando .
	 * @author Misraim Hernandez 
	 */
	public void loadingUltimusCargaArchivo() {
		try {
			u.hardWait(2);
			while (u.getDriver().findElement(loadiCargaArchivo).isDisplayed()) {
				System.out.println("loading...");
				u.hardWait(2);
			}
		} catch (Exception e) {

		}
	}
	
	/**
	 * Metodo para seleccionar y borrar un valor de un campo de texto
	 * @param inputBy -> By Element
	 * @author Misraim Hernandez
	 */
	public void clearInput(By inputBy) {
		try {
			WebElement toClear = u.getDriver().findElement(inputBy);
			toClear.sendKeys(Keys.CONTROL + "a");
			toClear.sendKeys(Keys.DELETE);
			
		} catch (Exception e) {
			
			System.out.print("Fallo al limpiar el campo: " + e);
		}
	}	
	
	/**
	 * Metodo para seleccionar un elemento tipo check
	 * @param check -> By Element
	 * @param opcion -> True o False
	 * @author Misraim Hernandez
	 */
	public void selectCheck(By check, Boolean opcion) {
		try {
			if (u.getDriver().findElement(check).isEnabled()) {
				if (opcion && !u.getDriver().findElement(check).isSelected()) {
					u.click(check);
				} 
				else if (!opcion && u.getDriver().findElement(check).isSelected()) {
					u.click(check);
				}
			}
			
			} catch (Exception e) {
				System.out.println("" + e);
				throw new AssertionError("Fallo al seleccionar: \"" + opcion + "\"");
				}
		}

	/**
	 * Metodo para seleccionar los filtrso del apartado Identificacion del Artículo
	 * @param superGrupo
	 * @param categoria
	 * @param subCategoria
	 * @param segmento
	 * @param subSegmento
	 * @param storeOrderMultiple
	 * @param unidadMedida
	 * @param UnidadVenta
	 * @param codigoFiscal
	 * @param precioSugerido
	 * @author Misraim Hernandez
	 */
	public void SelecionarFiltrosIdentificacionArtículo(String superGrupo, String categoria, String subCategoria, String segmento,String subSegmento,String storeOrderMultiple,String unidadMedida,String UnidadVenta, String codigoFiscal, String precioSugerido) {
		try {
			// Estructura de Categoría
			selectOption("SuperGrupo", superGrupo,filtroSuperGrupo);
			loadingUltimusCargaArchivo();
			selectOption("Categoria", categoria,filtroCategoria);
			loadingUltimusCargaArchivo();
			selectOption("Subcategoria", subCategoria, filtroSubCategoria);
			loadingUltimusCargaArchivo();
			selectOption("Segmento", segmento, filtroSegmento);
			loadingUltimusCargaArchivo();
			selectOption("SubSegmento", subSegmento, filtroSubSegmento);
			loadingUltimusCargaArchivo();
			selectOption("Store Order Multiple", storeOrderMultiple, filtroStoreOrderMultiple);
			loadingUltimusCargaArchivo();
			selectOption("Unidad de Medida", unidadMedida, filtroUnidadMedida);
			loadingUltimusCargaArchivo();
			selectOption("Unidad de Venta", UnidadVenta, filtroUnidadVenta);
			
			//Información comercial para la cadena
			loadingUltimusCargaArchivo();
			selectOption("Codigo Fiscal", codigoFiscal, filtroCodigoFiscal);
			loadingUltimusCargaArchivo();
			
			clearInput(txtPrecioSugerido);
			u.hardWait(1);
			u.sendKeys(txtPrecioSugerido, precioSugerido);
		
			selectCheck(ChBoxBara, false);
			
			u.click(imgLupaBase);
			loadingUltimusCargaArchivo();
			u.click(ReglaPrecio);
			u.click(BotonAceptarReglaPrecio);
			
			u.click(imgLupaExcluir);
			loadingUltimusCargaArchivo();
			u.click(ReglaPrecio);
			u.click(BotonAceptarReglaPrecio);
			
			u.click(imgLupaPeatonal);
			loadingUltimusCargaArchivo();
			u.click(ReglaPrecio);
			u.click(BotonAceptarReglaPrecio);
			
			u.click(imgLupaTurOasis);
			loadingUltimusCargaArchivo();
			u.click(ReglaPrecio);
			u.click(BotonAceptarReglaPrecio);
			
			u.click(imgLupaMixtas);
			loadingUltimusCargaArchivo();
			u.click(ReglaPrecio);
			u.click(BotonAceptarReglaPrecio);
			
			
		} catch (Exception e) {
			System.out.println(" -> " + e);
			throw new AssertionError("Fallo al Selecionar en apartado Identificación del articulo");
		}
	
	}	
	
	/**
	 * Metodo para seleccionar los filtrso del apartado Informacion del Contenido
	 * @param contenido
	 * @param unidadMedidaContenido
	 * @author Misraim Hernandez
	 */
	public void SelecionarFiltrosInformacionContenido(String contenido, String unidadMedidaContenido) {
		try {
			// Información de contenido
		
			u.click(imgLupaPresentacion);
			loadingUltimusCargaArchivo();
			u.click(ReglaPresentacion);
			u.click(BotonAceptarReglaPresentacion);
			
			u.highLight(txtContenido);
			u.sendKeys(txtContenido, contenido);
			
			selectOption("Unidad de medida de Contenido", unidadMedidaContenido,filtroUnidadMedidaContenido);
			loadingUltimusCargaArchivo();
			
			
		} catch (Exception e) {
			System.out.println(" -> " + e);
			throw new AssertionError("Fallo al Selecionar en apartado Información de contenido");
		}
	
	}	
	
	/**
	 * Metodo para seleccionar los filtrso del apartado Marcas Descriptivas y Muestra Fisica
	 * @param marcaProveedor
	 * @param marcaArticulo
	 * @param udaLibre
	 * @author Misraim Hernandez
	 */
	public void SelecionarFiltrosMarcasDescriptivasMuestraFisica(String marcaProveedor, String marcaArticulo,String udaLibre) {
		try {
			// Marcas descriptivas de la muestra física 
			
			u.click(imgLupaMarcaProveedor);
			loadingUltimusCargaArchivo();
			selectOption("Marca Proveedor", marcaProveedor,filtroMarcaProveedor);
			u.click(imgLupaTextSearchProveedor);
			loadingUltimusCargaArchivo();
			u.click(ReglaMarcaProveedor);
			u.click(BotonAceptarReglaMarcaProveedor);
			loadingUltimusCargaArchivo();
			
			u.click(imgLupaMarcaArticulo);
			loadingUltimusCargaArchivo();
			selectOption("Marca Articulo", marcaArticulo,filtroMarcaArticulo);
			u.click(imgLupaTextSearchArticulo);
			loadingUltimusCargaArchivo();
			u.click(ReglaMarcaArticulo);
			u.click(BotonAceptarReglaMarcaArticulo);
			loadingUltimusCargaArchivo();
			
			u.click(rbMermaSi);
			u.click(rbCandadoNo);
			u.click(rbCandadoNo);
			u.sendKeys(txtudaLibre, udaLibre);
			
			
			
		} catch (Exception e) {
			System.out.println(" -> " + e);
			throw new AssertionError("Fallo al Selecionar en apartado Marcas descriptivas de la muestra física");
		}
	
	}
	
	/**
	 * Metodo para hacer clik en el boton ok de la modal datos guardado correctamente
	 * @author Misraim Hernandez
	 */
	public void clickBotonOK() {
		String textValue= u.getText(modalMensajeOK);
		System.out.println(textValue);
		
		if (textValue.contains("guardado correctamente")) {
			u.click(BotonOK);
		} else {
			throw new AssertionError("Información incorrecta");
			
		}
		
	}
	
	
	
	
}

