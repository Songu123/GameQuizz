package entity;
public class ResultDetail {
    private int id;
    private int resultId;
    private int questionId;
    private int answerId;
    private boolean isTrue;

    public ResultDetail(int id, int resultId, int questionId, int answerId, boolean isTrue) {
        this.id = id;
        this.resultId = resultId;
        this.questionId = questionId;
        this.answerId = answerId;
        this.isTrue = isTrue;
    }

    public ResultDetail() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getResultId() {
        return resultId;
    }

    public void setResultId(int resultId) {
        this.resultId = resultId;
    }

    public int getQuestionId() {
        return questionId;
    }

    public void setQuestionId(int questionId) {
        this.questionId = questionId;
    }

    public int getAnswerId() {
        return answerId;
    }

    public void setAnswerId(int answerId) {
        this.answerId = answerId;
    }

    public boolean isTrue() {
        return isTrue;
    }

    public void setTrue(boolean aTrue) {
        isTrue = aTrue;
    }

    @Override
    public String toString() {
        return "ResultDetail{" +
                "id=" + id +
                ", resultId=" + resultId +
                ", questionId=" + questionId +
                ", answerId=" + answerId +
                ", isTrue=" + isTrue +
                '}';
    }
}
