package com.artcources.artistica.model.binding;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class CoursesSearchBindingModel {

    @NotBlank(message = "Keyword should not be an empty string")
    @Size(min=3,max=20, message = "Keyword length must be between 3 and 20 characters!")
    private String keyword;

    public CoursesSearchBindingModel() {
    }

    public String getKeyword() {
        return keyword;
    }

    public CoursesSearchBindingModel setKeyword(String keyword) {
        this.keyword = keyword;
        return this;
    }
}
