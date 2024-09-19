package com.example.lms.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.lms.entity.Book;
import com.example.lms.service.BookService;

@RestController
@RequestMapping("/api/v2")
public class BookController {
	
	@Autowired
	BookService bookService;
	
	@PostMapping("/addBook")
	public ResponseEntity<Integer> addBook(@RequestBody Book book){
		bookService.saveBook(book);
		return new ResponseEntity<>(book.getBookId(), HttpStatus.CREATED);
	}
	
	@GetMapping("/getBook/{bookId}")
	public ResponseEntity<Object> getBook(@PathVariable int bookId){
		Book book = bookService.getBookById(bookId);
		if(book == null) {
			System.out.println("Not Present");
			return new ResponseEntity<>("The book which you are searching for is not there", HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(book, HttpStatus.ACCEPTED);
	}
	
	@GetMapping("/getAllBooks")
	public ResponseEntity<List<Book>> getAllBooks(){
		List<Book> books = bookService.getAllBooks();
		return new ResponseEntity<>(books, HttpStatus.OK);
	}
	
	@PutMapping("/updateBook/{bookId}")
	public ResponseEntity<Object> updateBookById(@PathVariable int bookId, @RequestBody Book book){
		Book books = bookService.updateBookById(bookId, book);
		if(books == null) {
			System.out.println("Not Present");
			return new ResponseEntity<>("The book which you are searching for is not there", HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(books.getBookName(), HttpStatus.ACCEPTED);
	}
	
	@PatchMapping("/updateBookName/{bookId}/{bookName}")
	public ResponseEntity<Object> updateBookName(@PathVariable int bookId, @PathVariable String bookName) {
	    try {
	        Book book = bookService.updateBookByName(bookId, bookName);

	        if (book == null) {
	            return new ResponseEntity<>("The book which you are searching for is not there", HttpStatus.NOT_FOUND);
	        }

	        return new ResponseEntity<>(book.getBookName(), HttpStatus.ACCEPTED);
	    } catch (Exception e) {
	        return new ResponseEntity<>("An error occurred while updating the book name.", HttpStatus.INTERNAL_SERVER_ERROR);
	    }
	}

//	@PatchMapping("/updateBookAvailabilityStatus")
//	public ResponseEntity<Object> updateBookAvailabilityStatus(@RequestParam int bookId, @RequestParam boolean isAvailable) {
//	    try {
//	        Book book = bookService.updateBookAvailabilityStatus(bookId, isAvailable);
//
//	        if (book == null) {
//	            return new ResponseEntity<>("The book which you are searching for is not there", HttpStatus.NOT_FOUND);
//	        }
//
//	        return new ResponseEntity<>(book.getBookName(), HttpStatus.ACCEPTED);
//	    } catch (Exception e) {
//	        return new ResponseEntity<>("An error occurred while updating the book name.", HttpStatus.INTERNAL_SERVER_ERROR);
//	    }
//	}
	
	@DeleteMapping("/deleteBook/{bookId}")
	public ResponseEntity<Object> deleteBookById(@PathVariable int bookId){
		//All Books are returned
		// If fixed and available are smae then we can only delete -> no one has booked or everything has been returned.
			
			int deletedBookId = bookService.deletedBookById(bookId);
			if(deletedBookId == -1) {
				 return new ResponseEntity<>("An error occurred while deleting the book", HttpStatus.NOT_FOUND);
			}else if (deletedBookId == -2) {
				 return new ResponseEntity<>("Some books are not returned from vendor or All books are not available to delete", HttpStatus.NOT_ACCEPTABLE);
			}
			return new ResponseEntity<>("Book has been deleted successfully", HttpStatus.OK);
	}

	
}
