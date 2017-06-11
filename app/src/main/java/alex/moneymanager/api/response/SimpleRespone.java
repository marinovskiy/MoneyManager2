package alex.moneymanager.api.response;

public class SimpleRespone {

    private String msg;

    public SimpleRespone() {
    }

    public SimpleRespone(String msg) {
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}