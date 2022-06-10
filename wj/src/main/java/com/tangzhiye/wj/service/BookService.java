package com.tangzhiye.wj.service;

import com.tangzhiye.wj.dao.BookDao;
import com.tangzhiye.wj.pojo.Book;
import com.tangzhiye.wj.pojo.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {

    @Autowired
    private BookDao bookDao;

    @Autowired
    private CategoryService categoryService;

    public List<Book> list(){
        Sort sort = Sort.by(Sort.Direction.DESC,"id");
        return bookDao.findAll(sort);
    }

    public List<Book> listByCategory(int cid){
        Category category = categoryService.get(cid);
        return bookDao.findAllByCategory(category);
    }

    public List<Book> searchByTitleOrAuthor(String keyword1, String keyword2){
        return bookDao.findByTitleLikeOrAuthorLike('%'+keyword1+'%','%'+keyword2+'%');
    }

    public void addOrUpdate(Book book){
        bookDao.save(book);
    }

    public void deleteById(int id){
        bookDao.deleteById(id);
    }


}
