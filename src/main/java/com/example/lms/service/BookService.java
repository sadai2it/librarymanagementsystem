package com.example.lms.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.lms.entity.Book;
import com.example.lms.repository.BookRepository;

@Component
public class BookService {
	
	@Autowired
	public BookRepository bookRepository;
	
	public List<Book> getAllBooks(){
		return bookRepository.findAll();
	}
	
	public void saveBook(Book book) {
		bookRepository.save(book);
	}
	
	public Book getBookById(int bookId) {
		Optional<Book> books = bookRepository.findById(bookId);
		if(books.isPresent())
			return books.get();
		return null;
	}

	public Book updateBookById(int bookId, Book book) {
		// TODO Auto-generated method stub
		Book books = bookRepository.findById(bookId).get();
		if(books != null) {
			books.setBookName(book.getBookName());
			books.setAvailable(book.isAvailable());
			books.setAvailableBooks(book.getAvailableBooks());
			bookRepository.save(books);
			return books;
		}
		return null;
	}

	public Book updateBookByName(int bookId, String bookName) {
		// TODO Auto-generated method stub
		Book books = bookRepository.findById(bookId).get();
		if(books != null) {
			books.setBookName(bookName);
			bookRepository.save(books);
			return books;
		}
		return null;
	}
	
	public Book updateBookAvailabilityStatus(int bookId, boolean isAvailable) {
		// TODO Auto-generated method stub
		Book books = bookRepository.findById(bookId).get();
		if(books != null) {
			books.setAvailable(isAvailable);
			bookRepository.save(books);
			return books;
		}
		return null;
	}

	public int deletedBookById(int bookId) {
		// TODO Auto-generated method stub
		Book book = bookRepository.findById(bookId).get();
		if(book == null)
			return -1;
		int quant = book.getTotalBooks() - book.getAvailableBooks();
		System.out.println(quant);
		if(quant != 0)
			return -2;
		bookRepository.deleteById(bookId);
		return book.getBookId();
		
	}

}
