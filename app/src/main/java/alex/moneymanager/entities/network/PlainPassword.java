package alex.moneymanager.entities.network;

public class PlainPassword {

    private String password;

    private String confirmPassword;

    public PlainPassword() {
    }

    public PlainPassword(String password, String confirmPassword) {
        this.password = password;
        this.confirmPassword = confirmPassword;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    @Override
    public String toString() {
        return "PlainPassword{" +
                "password='" + password + '\'' +
                ", confirmPassword='" + confirmPassword + '\'' +
                '}';
    }
}