import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class test {
    public static void main(String[] args) {
//        Long a = 128L;
//        Long b = 128L;
//        System.out.println(a == b);
//        System.out.println("a==0      " + (a == 1233L));
//        System.out.println("a.equals(0) " + (a.equals(1233L)));
        LocalDateTime parse = LocalDateTime.parse("2022-11-18 18:00:00", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        System.out.println(parse);

    }
}
