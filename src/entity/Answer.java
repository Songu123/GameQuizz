public class Answer {
    private int id;
    private String content;
    private int questionId;
    private boolean isCorrect;

    public Answer() {
    }

    public Answer(int id, String content, int questionId, boolean isCorrect) {
        this.id = id;
        this.content = content;
        this.questionId = questionId;
        this.isCorrect = isCorrect;
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

    public void setContent(String content) {
        this.content = content;
    }

    public int getQuestionId(){
        return questionId;
    }

    public void setQuestionId(int questionId){
        this.questionId = questionId;
    }

    public boolean isCorrect() {
        return isCorrect;
    }

    public void setCorrect(boolean correct) {
        isCorrect = correct;
    }

    @Override
    public String toString() {
        return "Answer{" +
                "id=" + id +
                ", content='" + content + '\'' +
                ", questionId=" + questionId +
                ", isCorrect=" + isCorrect +
                '}';
    }
}
