package ch.wiss.wiss_quiz.payload.request;

import jakarta.validation.constraints.NotBlank;

public class CategoryRequest {

    private int id;
    
    @NotBlank
    private String name;

    public CategoryRequest(){

    }

    public CategoryRequest(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public void setId(int id) {
        this.id = id;
    }
    public void setName(String name) {
        this.name = name;
    }
    public int getId() {
        return id;
    }
    public String getName() {
        return name;
    }



}
