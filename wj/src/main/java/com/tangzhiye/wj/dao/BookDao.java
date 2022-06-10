package com.tangzhiye.wj.dao;

import com.tangzhiye.wj.pojo.Book;
import com.tangzhiye.wj.pojo.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookDao extends JpaRepository<Book, Integer> {

//    Book findByCategory(Category category);

    List<Book> findAllByCategory(Category category);

    List<Book> findByTitleLikeOrAuthorLike(String keyword1, String keyword2);
}
