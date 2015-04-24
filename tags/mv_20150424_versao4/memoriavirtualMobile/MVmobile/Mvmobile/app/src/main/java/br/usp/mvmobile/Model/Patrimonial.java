package br.usp.mvmobile.Model;

/**
 * Created by User on 01/10/2014.
 */
public class Patrimonial {
    private String name;
    private String author;

    public Patrimonial(String name, String author) {
        this.name = name;
        this.author = author;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }
}
