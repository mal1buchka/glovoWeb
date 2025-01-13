//package kg.org.glovoweb.Security;
//
//
//import io.jsonwebtoken.io.Decoders;
//import io.jsonwebtoken.security.Keys;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Component;
//
//import javax.crypto.SecretKey;
//
//@Slf4j
//@Component
//public class JwtCore {
//
//    //14.01, чутка подустал на днях допишу
//    private final SecretKey accessSecretKey;
//    private final SecretKey refreshSecretKey;
//
//    private final int accessExpiration;
//    private final int refreshExpiration;
//
//    public JwtCore(
//            @Value("${jwt.access.secret}") String jwtAccess,
//            @Value("${jwt.refresh.secret}") String jwtRefresh,
//            @Value("${jwt.access.expiration}") int accessExpiration,
//            @Value("${jwt.refresh.expiration}") int refreshExpiration
//    ) {
//        this.accessSecretKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtAccess));;
//        this.refreshSecretKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtRefresh));;
//        this.accessExpiration = accessExpiration;
//        this.refreshExpiration = refreshExpiration;
//    }
//}
