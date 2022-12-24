import java.time.LocalDateTime;
import java.time.ZoneOffset;

public class test {
    public static void main(String[] args) {
        System.out.println(LocalDateTime.now().toEpochSecond(ZoneOffset.of("+8")));
    }
}
