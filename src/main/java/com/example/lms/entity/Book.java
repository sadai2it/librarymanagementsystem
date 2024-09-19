package com.example.lms.entity;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "books")
public class Book {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "book_id")
	public int bookId;

	@Column(name = "book_name")
	public String bookName;

	@Column(name = "is_available")
	public boolean isAvailable;
	
	//count -> number of books, FIxed Quant and Available Quant -> 0 then is AVialbel as false
	
	@Column(name = "total_books")
	public int totalBooks;
	
	@Column(name = "available_books")
	public int availableBooks;
	
	@OneToMany(mappedBy = "book", cascade = CascadeType.ALL)
	private List<Vendor> vendors;

	public int getTotalBooks() {
		return totalBooks;
	}

	public void setTotalBooks(int totalBooks) {
		this.totalBooks = totalBooks;
	}

	public int getAvailableBooks() {
		return availableBooks;
	}

	public void setAvailableBooks(int availableBooks) {
		this.availableBooks = availableBooks;
	}


	public int getBookId() {
		return bookId;
	}

	public void setBookId(int bookId) {
		this.bookId = bookId;
	}

	public String getBookName() {
		return bookName;
	}

	public void setBookName(String bookName) {
		this.bookName = bookName;
	}

	public boolean isAvailable() {
		return isAvailable;
	}

	public void setAvailable(boolean isAvailable) {
		this.isAvailable = isAvailable;
	}

}
