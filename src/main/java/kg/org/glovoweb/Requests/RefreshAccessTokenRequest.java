package kg.org.glovoweb.Requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class RefreshAccessTokenRequest {
    private String refreshToken;
    private String email;
}
