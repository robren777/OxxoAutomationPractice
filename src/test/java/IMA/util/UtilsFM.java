package IMA.util;



import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;



public class UtilsFM {
    
    public UtilsFM() {
        
    }
    
    public String getDateTodayddMMYYYY() {
        DateTimeFormatter dtf3 = DateTimeFormatter.ofPattern("MMMM");
        String month = dtf3.format(LocalDateTime.now()).toUpperCase().substring(0, 2);
        dtf3 = DateTimeFormatter.ofPattern("dd");
        String day = dtf3.format(LocalDateTime.now());
        dtf3 = DateTimeFormatter.ofPattern("yyyy");
        String year = dtf3.format(LocalDateTime.now());
        return String.format("%s-%s-%s", day,month,year);
    }



}