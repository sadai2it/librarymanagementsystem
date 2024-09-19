package com.example.lms.service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.lms.entity.Book;
import com.example.lms.entity.Vendor;
import com.example.lms.repository.VendorRepository;

@Component
public class VendorService {

	@Autowired
	public VendorRepository vendorRepository;
	
	@Autowired
	public BookService bookService;
	
	
	public void saveOrUpdate(Vendor vendor) {
		vendor.setDueDate(vendor.getBookedDate().plusDays(7));
		vendorRepository.save(vendor);
	}


	public List<Vendor> getAllVendorsByBookId(int bookId) {
	    return vendorRepository.findAllByBook_BookId(bookId);
	}


	public Vendor updateBookReturnedDate(int vendorId, LocalDateTime returnedDate) {
		// TODO Auto-generated method stub
		Vendor vendor = vendorRepository.findById(vendorId).get();
		vendor.setReturnedDate(returnedDate);
		int days = (int) (ChronoUnit.DAYS.between(vendor.getDueDate(), returnedDate));
		if(days > 0) {
			vendor.setOverDueAmount(days * 2);
		}
		Book books = bookService.getBookById(vendor.getBook().getBookId());
		books.setAvailableBooks(books.getAvailableBooks() + vendor.getQuantity());
		books.setAvailable(true);
		vendor.setQuantity(0);
		bookService.saveBook(books);
		vendorRepository.save(vendor);
		return vendor;
	}


	public Vendor getVendorById(int vendorId) {
		// TODO Auto-generated method stub
		return vendorRepository.findById(vendorId).get();
	}


	public List<Vendor> getVendorByName(String vendorName) {
		// TODO Auto-generated method stub
		List<Vendor> vendors = vendorRepository.findByVendorName(vendorName);
		return vendors;
	}
	

}

