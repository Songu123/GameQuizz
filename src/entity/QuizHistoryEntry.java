package entity;

public class QuizHistoryEntry {
    private String quizName;
    private int questionCount;
    private int correctAnswers;
    private String quizDate;

    public QuizHistoryEntry(String quizName, int questionCount, int correctAnswers, String quizDate) {
        this.quizName = quizName;
        this.questionCount = questionCount;
        this.correctAnswers = correctAnswers;
        this.quizDate = quizDate;
    }

    public QuizHistoryEntry() {
    }

    public String getQuizName() {
        return quizName;
    }

    public void setQuizName(String quizName) {
        this.quizName = quizName;
    }

    public int getQuestionCount() {
        return questionCount;
    }

    public void setQuestionCount(int questionCount) {
        this.questionCount = questionCount;
    }

    public int getCorrectAnswers() {
        return correctAnswers;
    }

    public void setCorrectAnswers(int correctAnswers) {
        this.correctAnswers = correctAnswers;
    }

    public String getQuizDate() {
        return quizDate;
    }

    public void setQuizDate(String quizDate) {
        this.quizDate = quizDate;
    }
}