package com.tanghziye.wj.dao;

import com.tanghziye.wj.pojo.Book;
import com.tanghziye.wj.pojo.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookDao extends JpaRepository<Book, Integer> {

//    Book findByCategory(Category category);

    List<Book> findAllByCategory(Category category);

    List<Book> findByTitleLikeOrAuthorLike(String keyword1, String keyword2);
}
