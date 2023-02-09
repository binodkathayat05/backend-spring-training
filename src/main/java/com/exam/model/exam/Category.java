package com.exam.model.exam;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name="category")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long cid;
    private String title;
    private String description;
    @OneToMany(mappedBy = "category",cascade = CascadeType.ALL)
    @JsonIgnore
    private Set<Quiz> quizzes=new LinkedHashSet<>();

    //JsonIgnore is used to solve the n+1 issue since when we call Quiz then quiz call category then again category call quiz soon.
    // mappedBy is used to make only one column in category table
    //fetch=FetchType.Eager is used to load data Quiz as well when we load category, If we need to call Quiz separately we can use Lazy
    //cascade=cascadeType.All is used for when we save Category then Quiz also save, when we delete category also deleted.


    public Long getCid() {
        return cid;
    }

    public void setCid(Long cid) {
        this.cid = cid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<Quiz> getQuizzes() {
        return quizzes;
    }

    public void setQuizzes(Set<Quiz> quizzes) {
        this.quizzes = quizzes;
    }


    public Category() {
    }

    public Category(String title, String description, Set<Quiz> quizzes) {
        this.title = title;
        this.description = description;
        this.quizzes = quizzes;
    }

    @Override
    public String toString() {
        return "Category{" +
                "cid=" + cid +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", quizzes=" + quizzes +
                '}';
    }
}
