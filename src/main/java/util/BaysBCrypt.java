package util;

import org.springframework.security.crypto.bcrypt.BCrypt;

public class BaysBCrypt {
    private final int rounds;

    public BaysBCrypt(int rounds) {
        this.rounds = rounds;
    }

    public String hash(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt(rounds));
    }

    public boolean verifyHash(String password, String hash) {
        return BCrypt.checkpw(password, hash);
    }

}