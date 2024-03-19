package entity;

public class Question {
    private int id;
    private String content;
    private int quizId;

    public Question() {
    }

    public Question(int id, String content, int quizId) {
        this.id = id;
        this.content = content;
        this.quizId = quizId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public int getQuizzes_id() {
        return quizId;
    }

    public void setQuizzes_id(int quizId) {
        this.quizId = quizId;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "Question{" +
                "id=" + id +
                ", content='" + content + '\'' +
                ", quizId=" + quizId +
                '}';
    }
}
