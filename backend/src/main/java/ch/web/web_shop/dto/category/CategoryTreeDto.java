package ch.web.web_shop.dto.category;


import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

public class CategoryTreeDto {
  @Getter
  private long id;
   @Getter
   private String name;
    @Getter
   private final List<CategoryTreeDto> subCategory;

    public CategoryTreeDto(long id, String name) {
        this.id = id;
        this.name = name;
        this.subCategory = new ArrayList<>();
    }

    public void addChildCategory(CategoryTreeDto sub) {
        subCategory.add(sub);
    }

}
