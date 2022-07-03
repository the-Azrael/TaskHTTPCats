public class Cat {
    private String id;
    private String text;
    private String type;
    private String user;
    private int upvotes;

    public Cat() {
        //
    }

    public Cat(String id, String text, String type, String user, int upvotes) {
        this.id = id;
        this.text = text;
        this.type = type;
        this.user = user;
        this.upvotes = upvotes;
    }

    @Override
    public String toString() {
        return "Cat {" +
                "[id] = " + this.id + ";" +
                " [text] = " + this.text + ";" +
                " [type] = " + this.type + ";" +
                " [user] = " + this.user + ";" +
                " [upvotes] = " + this.upvotes +
                " }";
    }

    public String getId() {
        return id;
    }

    public String getText() {
        return text;
    }

    public String getType() {
        return type;
    }

    public int getUpvotes() {
        return upvotes;
    }

    public String getUser() {
        return user;
    }
}
