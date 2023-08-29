package ch.web.web_shop.dto.image;

public class ResponseFile {

    String Url;
    String alt;


    public ResponseFile(String url, String alt) {
        Url = url;
        this.alt = alt;
    }

    public String getUrl() {
        return Url;
    }

    public void setUrl(String url) {
        Url = url;
    }

    public String getAlt() {
        return alt;
    }

    public void setAlt(String alt) {
        this.alt = alt;
    }
}
