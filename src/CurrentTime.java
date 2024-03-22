import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class CurrentTime {

    private String timeQuizz;
    public static String getCurrentTime(){
        LocalDateTime currentDateTime = LocalDateTime.now();

//         Define a formatter for the desired date format
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

//         Format the current date and time using the formatter
        String formattedDateTime = currentDateTime.format(formatter);
        System.out.println(formattedDateTime);
        System.out.println(currentDateTime);
        return formattedDateTime;
    }
}
