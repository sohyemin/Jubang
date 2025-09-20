package HelloWorld.Jubang.security;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
@ToString
public class UserDTO extends User {

    private String email;
    private String name;
    private String password;

    public UserDTO(String email, String password, String name, Collection<? extends GrantedAuthority> authorities) {
        super(email, password, authorities);
        this.email = email;
        this.name = name;
        this.password = password;
    }

    public Map<String, Object> getClaims() {

        Map<String, Object> dataMap = new HashMap<>();

        dataMap.put("email", this.email);
        dataMap.put("password", this.password);
        dataMap.put("name", this.name);

        return dataMap;
    }
}
