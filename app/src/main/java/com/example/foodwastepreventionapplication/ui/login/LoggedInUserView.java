package  com.example.foodwastepreventionapplication.ui.login;

/**
 * Class exposing authenticated user details to the UI.
 */
class LoggedInUserView {
    private String displayName;
    private Integer Id;
    //... other data fields that may be accessible to the UI

    LoggedInUserView(String displayName, Integer Id) {
        this.displayName = displayName;
        this.Id = Id;
    }

    String getDisplayName() {
        return displayName;
    }

    Integer getId(){
        return Id;
    }
}