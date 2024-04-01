package entity;

public class ListQuizz {
    private String nameQuizz;
    private String nameAuthor;

    public ListQuizz(String nameQuizz, String nameAuthor) {
        this.nameQuizz = nameQuizz;
        this.nameAuthor = nameAuthor;
    }

    public String getNameQuizz() {
        return nameQuizz;
    }

    public void setNameQuizz(String nameQuizz) {
        this.nameQuizz = nameQuizz;
    }

    public String getNameAuthor() {
        return nameAuthor;
    }

    public void setNameAuthor(String nameAuthor) {
        this.nameAuthor = nameAuthor;
    }
}
