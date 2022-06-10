package com.tangzhiye.wj.controller;

import com.tangzhiye.wj.pojo.Book;
import com.tangzhiye.wj.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

@RestController
public class LibraryController {

    @Autowired
    private BookService bookService;

    @GetMapping("/api/books")
    public List<Book> list(){
        return bookService.list();
    }

    @GetMapping("/api/books/category/{cid}")
    public List<Book> listByCategory(@PathVariable int cid){
        if (cid == 0) {
            return bookService.list();
        }
        return bookService.listByCategory(cid);
    }

    @GetMapping("api/books/search")
    public List<Book> searchByTitleOrAuthor(@RequestParam("keywords") String keywords){
        keywords = keywords.trim();
        if ("".equals(keywords)) {
            return bookService.list();
        } else {
            return bookService.searchByTitleOrAuthor(keywords,keywords);
        }

    }

    @PostMapping("/api/books")
    public Book addOrUpdate(@RequestBody Book book){
        bookService.addOrUpdate(book);
        return book;
    }

    @DeleteMapping("/api/books/{id}")
    public void delete(@PathVariable int id){
        bookService.deleteById(id);
    }

    @PostMapping("/api/covers")
    public String coverUpload(@RequestParam MultipartFile file){
        String folder = "D:/workspace/img";
        File imageFolder = new File(folder);
        String newImgName = UUID.randomUUID() + file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf('.'));
        File newFile = new File(imageFolder, newImgName);
        if (!newFile.getParentFile().exists()) newFile.getParentFile().mkdirs();
        try {
            file.transferTo(newFile);
            String imgURL = "http://localhost:8443/api/file/" + newFile.getName();
            return imgURL;
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
    }
}
