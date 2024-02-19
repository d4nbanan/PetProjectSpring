import com.example.PetProjectSpring.auth.types.TokenPayload;
import com.example.PetProjectSpring.auth.dto.TokenPayloadDto;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.stereotype.Service;
import com.example.PetProjectSpring.common.funcs.GetHashFromClass;

import java.util.Date;
import java.util.HashMap;

@Service
public class JwtService {

    private final String SECRET_KEY = "A7bDCnjy6cAn8GmQQyC9NNpoFQNUJFZ47sr0MuMVVEQuLIbMsZ";
    private final long Default_Token_Lifetime_Access = 7200000; // in ms
    private final long Default_Token_Lifetime_Refresh = 2592000000L; // in ms

    private String generateToken() {

    }

    public String generateTokens(TokenPayloadDto payload) {
        Claims claimsAccess = Jwts.claims().setSubject(payload.sub);

        Date issuedAt_Access = new Date();
        claimsAccess.setIssuedAt(issuedAt_Access);

        Date expiresAt = new Date(issuedAt_Access.getTime() + this.Default_Token_Lifetime_Access);
        claimsAccess.setExpiration(expiresAt);

        HashMap<String, String> map = GetHashFromClass.getHash(payload.getClass());
        claimsAccess.putAll();
    }

    public TokenPayload validate(String token) {

    }
}
