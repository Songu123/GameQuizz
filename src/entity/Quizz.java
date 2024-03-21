package entity;

public class Quizz {
    private int id;
    private String name;
    private int userId;


    public Quizz(int id, String name, int userId){
        this.id = id;
        this.name = name;
        this.userId = userId;
    }

    public int getId(){
        return this.id;
    }

    public void setId(int id){
        this.id = id;
    }

    public String getName(){
        return this.name;
    }

    public void setName(String name){
        this.name = name;
    }

    public int getUserId(){
        return this.userId;
    }

    public void setUserId(int userId){
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "Quizz{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", userId='" + userId + '\'' +
                '}';
    }
}
