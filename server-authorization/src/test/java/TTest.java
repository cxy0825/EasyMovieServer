import cn.hutool.jwt.JWT;
import cn.hutool.jwt.JWTUtil;

public class TTest {
    public static void main(String[] args) {
        String key = "zxc5646@#@$";
        String token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJJRCI6MTAwMDEsInBob25lIjoiMTU4Njg2NjI5MzciLCJwb3dlciI6MTAwLCJpYXQiOjE2Njk2Mjg3NzUsImV4cCI6MTY2OTYyODc3NX0.356uFM3625C5KZYJ4YZ28RMoAWRmel97HHawWARYPsQ";
        String token2 = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJJRCI6MTAwMDEsInBob25lIjoiMTU4Njg2NjI5MzciLCJwb3dlciI6MTAwLCJpYXQiOjE2Njk2Mjk2NTEsImV4cCI6MTY2OTYyOTk1MX0.xgj7qmsUUpYV7Ee9BEQXdGgFcf38oGVCmbPG6jFZcM4";
        JWT jwt = JWTUtil.parseToken(token2);
        System.out.println(jwt.getPayload());
        System.out.println(System.currentTimeMillis());
        System.out.println(jwt.getPayload("exp"));
//        boolean verify = JWTUtil.verify(token2, key.getBytes());
//        try {
//            System.out.println(JWTValidator.of(jwt).validateDate(DateUtil.date()));
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//
//        System.out.println(verify);
//

    }
}
