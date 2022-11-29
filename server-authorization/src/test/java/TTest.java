import cn.hutool.core.date.DateUtil;
import cn.hutool.jwt.JWT;

import java.nio.charset.StandardCharsets;

public class TTest {
    public static void main(String[] args) {
        String token = JWT.create()
                .setPayload("ID", 1)
                .setPayload("phone", "15868662937")
                .setPayload("cinemaID", 1) //对应的影院ID 普通管理员需要
                .setPayload("power", 1)
                .setPayload("type", "admin") //最高管理员
                .setIssuedAt(DateUtil.date())//签发时间
                .setExpiresAt(DateUtil.offsetMonth(DateUtil.date(), 24))//过期时间
                .setKey("zxc5646@#@$".getBytes(StandardCharsets.UTF_8))
                .sign();
        System.out.println(token);

    }
}
