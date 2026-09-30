package WEB.cometa.om;

import org.openqa.selenium.By;

public class faqsDirectosElements {

    public By opcionFaqsDirectos = By.xpath("//a[@href='/PortalCometa/faqs/directos/home'] | //button[.//span[normalize-space()='FAQS Directos'] or normalize-space()='FAQS Directos']");
    public By contenidoFaqsDirectos = By.xpath("//*[contains(normalize-space(), 'Preguntas Frecuentes') or contains(normalize-space(), 'FAQS Directos')]");
    public By botonCrearNuevaPregunta = By.xpath("//button[.//span[normalize-space()='Crear nueva pregunta'] or normalize-space()='Crear nueva pregunta']");
    public By campoPregunta = By.xpath("//input[@formcontrolname='pregunta' or @name='pregunta' or @placeholder='Pregunta'] | //textarea[@formcontrolname='pregunta' or @name='pregunta' or @placeholder='Pregunta'] | //label[contains(normalize-space(), 'Pregunta')]/following::*[self::input or self::textarea][1]");
    public By campoRespuesta = By.xpath("//input[@formcontrolname='respuesta' or @name='respuesta' or @placeholder='Respuesta'] | //textarea[@formcontrolname='respuesta' or @name='respuesta' or @placeholder='Respuesta'] | //label[contains(normalize-space(), 'Respuesta')]/following::*[self::input or self::textarea][1]");
    public By botonGuardar = By.xpath("//button[.//span[normalize-space()='Guardar'] or normalize-space()='Guardar']");
    public By botonConfirmarEliminar = By.xpath("//mat-dialog-container//button[.//span[normalize-space()='Eliminar'] or normalize-space()='Eliminar']");
    public By tarjetasFaq = By.cssSelector(".faq-card");
}