package com.example.lms.controller;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
import com.example.lms.entity.FineInVoice;
import com.example.lms.entity.Vendor;
import com.example.lms.service.BookService;
import com.example.lms.service.VendorService;

@RestController
@RequestMapping("/api/v1")
public class VendorController {

	@Autowired
	BookService bookService;

	@Autowired
	VendorService vendorService;

	@PostMapping("/addVendor")
	public ResponseEntity<Object> addVendor(@RequestBody Vendor vendorRequest) {
		// Fetch the Book entity from the database using the bookId from the request
		Book book = bookService.getBookById(vendorRequest.getBook().getBookId());
		if (book == null) {
			return new ResponseEntity<>("Book is not present or Invalid Request", HttpStatus.NOT_FOUND);
		}

		if (book.isAvailable()) {
			if (book.getAvailableBooks() >= vendorRequest.getQuantity()) {
				int remainingBooks = book.getAvailableBooks() - vendorRequest.getQuantity();
				book.setAvailableBooks(remainingBooks);
				if (remainingBooks == 0)
					book.setAvailable(false);
				bookService.updateBookById(vendorRequest.getBook().getBookId(), book);

			} else
				return new ResponseEntity<>("Requested Books are Out of Stock!!!", HttpStatus.NOT_ACCEPTABLE);
		} else
			return new ResponseEntity<>("Requested Books are Out of Stock!!!", HttpStatus.NOT_ACCEPTABLE);

		vendorService.saveOrUpdate(vendorRequest);

		// Return the CREATED status
		return new ResponseEntity<>("Vendor bought the book successfully", HttpStatus.CREATED);
	}


	@PatchMapping("/updateVendorOverDueStatus/{vendorId}")
	public ResponseEntity<Object> updateOverDueAmount(@PathVariable int vendorId) {
		Vendor vendor = vendorService.getVendorById(vendorId);
		vendor.setOverDueAmount(0);
		vendorService.saveOrUpdate(vendor);
		return new ResponseEntity<>(vendor, HttpStatus.ACCEPTED);
	}

	@GetMapping("/getVendorsByBookId/{bookId}")
	public ResponseEntity<List<Vendor>> findVendorByBookId(@PathVariable int bookId) {
		List<Vendor> vendors = vendorService.getAllVendorsByBookId(bookId);

		if (vendors.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<>(vendors, HttpStatus.ACCEPTED);
	}

	@PatchMapping("/updateBookReturnStatus/{vendorId}/{returnedDate}")
	public ResponseEntity<Object> updateBookReturnStatus(@PathVariable int vendorId,
			@PathVariable LocalDateTime returnedDate) {
		Vendor vendor = vendorService.updateBookReturnedDate(vendorId, returnedDate);
		// vendor.setBook(null);
		System.out.println(vendor.getOverDueAmount());
		if (vendor.getOverDueAmount() > 0) {
			// return new ResponseEntity<>("");
			FineInVoice invoice = new FineInVoice(vendor.getVendorName(), "Rs. " + vendor.overDueAmount);
			return new ResponseEntity<>(invoice, HttpStatus.ACCEPTED);
		}
		return new ResponseEntity<>("Book has been returned successfully with no due", HttpStatus.ACCEPTED);
	}

	@PutMapping("/updateVendorByName")
	public ResponseEntity<Object> updateVendorByName(@RequestParam String vendorName,
			@RequestBody Vendor vendorRequest) {
		List<Vendor> vendor = vendorService.getVendorByName(vendorName);
		Vendor vendors;
		System.out.println(vendor.size());
		if (vendor.size() >0) {
			for (Vendor existingVendor : vendor) {
				if (existingVendor.getOverDueAmount() > 0)
					return new ResponseEntity<>(
							"The current vendor has a overdue amount for the bookId"
									+ existingVendor.getBook().getBookId() + "pay this to buy a book",
							HttpStatus.NOT_ACCEPTABLE);
				Book book = bookService.getBookById(vendorRequest.getBook().getBookId());
				if (existingVendor.getBook().getBookId() == vendorRequest.getBook().getBookId()) {
					if (book.isAvailable()) {
						if (book.getAvailableBooks() >= existingVendor.getQuantity()) {
							int remainingBooks = book.getAvailableBooks() - existingVendor.getQuantity();
							book.setAvailableBooks(remainingBooks);
							if (remainingBooks == 0)
								book.setAvailable(false);
							bookService.updateBookById(existingVendor.getBook().getBookId(), book);

						} else
							return new ResponseEntity<>("Requested Books are Out of Stock!!!",
									HttpStatus.NOT_ACCEPTABLE);
					} else
						return new ResponseEntity<>("Requested Books are Out of Stock!!!", HttpStatus.NOT_ACCEPTABLE);
					// vendors.setVendorName(vendor.getVendorName());
					existingVendor.setQuantity(existingVendor.getQuantity() + vendorRequest.getQuantity());
					existingVendor.setBookedDate(vendorRequest.getBookedDate());
					vendorService.saveOrUpdate(existingVendor);
					return new ResponseEntity<>(vendor, HttpStatus.ACCEPTED);
				} else {
					if (book.isAvailable()) {
						if (book.getAvailableBooks() >= vendorRequest.getQuantity()) {
							int remainingBooks = book.getAvailableBooks() - vendorRequest.getQuantity();
							book.setAvailableBooks(remainingBooks);
							if (remainingBooks == 0)
								book.setAvailable(false);
							bookService.updateBookById(vendorRequest.getBook().getBookId(), book);

						} else
							return new ResponseEntity<>("Requested Books are Out of Stock!!!",
									HttpStatus.NOT_ACCEPTABLE);
					} else
						return new ResponseEntity<>("Requested Books are Out of Stock!!!", HttpStatus.NOT_ACCEPTABLE);

					vendorRequest.setVendorName(vendorName);
					vendorService.saveOrUpdate(vendorRequest);

					// Return the CREATED status
					return new ResponseEntity<>("Vendor bought the book successfully", HttpStatus.CREATED);
				}
			}

		}
		return new ResponseEntity<>("There is no Vendor with the specified vendor name", HttpStatus.NOT_FOUND);
	}

}
