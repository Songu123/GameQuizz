public class Quizz {
    private int id;
    private String name;
    private String userId;

    public Quizz(){}

    public Quizz(int id, String name, String userId){
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

    public String getUserId(){
        return this.userId;
    }

    public void setUserId(String userId){
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
