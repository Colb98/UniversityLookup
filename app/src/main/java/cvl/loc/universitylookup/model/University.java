package cvl.loc.universitylookup.model;

import com.google.firebase.database.PropertyName;

public class University {
    String name;
    String addr;
    @PropertyName("image_uri")
    String imageUri;

    public University(){

    }

    public University(String name, String addr){
        this.name = name;
        this.addr = addr;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }

    public String getImageUri(){
        return imageUri;
    }

    public void setImageUri(String imageUri){
        this.imageUri = imageUri;
    }
}
