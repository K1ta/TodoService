package hello;

import java.io.Serializable;

public class ListItem implements Serializable {

    private Long id;
    private String data;

    public ListItem(Long id, String data) {
        this.id = id;
        this.data = data;
    }

    public Long getId() {
        return id;
    }

    public String getData() {
        return data;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setData(String data) {
        this.data = data;
    }
}
