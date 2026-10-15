// ABSTRACT PARENT CLASS: USER
abstract class User {
    private String userId;
    private String name;

    public User(String userId, String name) {
        this.userId = userId;
        this.name = name;
    }

    public String getUserId() {
        return userId;
    }

    public String getName() {
        return name;
    }
    
    public void displayInfo(){
      System.out.print("ID: " + getUserId());
      System.out.print("Name: " +getName());
    }

    public abstract String getRole();
}
