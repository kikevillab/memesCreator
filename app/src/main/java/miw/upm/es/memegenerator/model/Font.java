package miw.upm.es.memegenerator.model;

/**
 * Created by Enrique on 10/11/2016.
 */

public class Font {

    private long id;
    private String name;

    public Font(String name) {
        this.name = name;
    }

    public Font(long id, String name) {
        this.id = id;
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Font{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
