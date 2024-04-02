package entity;

public class ResultUser {
    private String username;
    private int quantityCorrect;
    private int quantityQuestion;
    private String Date;

    public ResultUser(String username, int quantityCorrect, int quantityQuestion, String date) {
        this.username = username;
        this.quantityCorrect = quantityCorrect;
        this.quantityQuestion = quantityQuestion;
        Date = date;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getQuantityCorrect() {
        return quantityCorrect;
    }

    public void setQuantityCorrect(int quantityCorrect) {
        this.quantityCorrect = quantityCorrect;
    }

    public int getQuantityQuestion() {
        return quantityQuestion;
    }

    public void setQuantityQuestion(int quantityQuestion) {
        this.quantityQuestion = quantityQuestion;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    @Override
    public String toString() {
        return "ResultUser{" +
                "username='" + username + '\'' +
                ", quantityCorrect=" + quantityCorrect +
                ", quantityQuestion=" + quantityQuestion +
                ", Date='" + Date + '\'' +
                '}';
    }
}
