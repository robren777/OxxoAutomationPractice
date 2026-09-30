package WEB.cometa.om;

import org.openqa.selenium.By;

public class faqsCedisElements {

    public By opcionFaqsCedis = By.xpath("(//a[@href='/PortalCometa/faqs/cedis/home'] | //a[contains(@href,'/faqs/cedis')] | //a[contains(@href,'faqs') and contains(@href,'cedis')] | //button[.//span[normalize-space()='FAQS CEDIS'] or normalize-space()='FAQS CEDIS'] | //span[normalize-space()='FAQS CEDIS']/ancestor::a[1] | //span[normalize-space()='FAQS CEDIS']/ancestor::button[1])[1]");
    public By contenidoFaqsCedis = By.xpath("//*[contains(normalize-space(), 'Preguntas Frecuentes') or contains(normalize-space(), 'FAQS CEDIS')]");
    public By botonCrearNuevaPregunta = By.xpath("//button[.//span[normalize-space()='Crear nueva pregunta'] or normalize-space()='Crear nueva pregunta']");
    public By tarjetasFaqCedis = By.cssSelector(".faq-card");
}