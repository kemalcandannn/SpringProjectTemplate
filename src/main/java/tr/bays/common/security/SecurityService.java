package tr.bays.common.security;

public interface SecurityService {

	String findLoggedInUsername();

	void autoLogin(String username, String password);

}