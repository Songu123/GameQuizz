package entity;

//import java.util.String;

public class Result {
    private int id;
    private int userId;
    private int quizId;
    private String time;

    public Result() {
    }
    public Result(int id, int userId, int quizId, String time) {
        this.id = id;
        this.userId = userId;
        this.quizId = quizId;
        this.time = time;
    }

    public Result(int userId, int quizId, String time) {
        this.userId = userId;
        this.quizId = quizId;
        this.time = time;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getQuizId() {
        return quizId;
    }

    public void setQuizId(int quizId) {
        this.quizId = quizId;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "Result{" +
                "id=" + id +
                ", userId=" + userId +
                ", quizId=" + quizId +
                ", time=" + time +
                '}';
    }
}
