package com.example.lms.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "vendor")
public class Vendor {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "vendor_id")
	public int vendorId;
	
	@Column(name = "vendor_name")
	public String vendorName;
	
	@Column(name = "booked_date")
	public LocalDateTime bookedDate;
	
	@Column(name = "return_date")
	public LocalDateTime returnedDate;
	
	@Column(name = "dueDate")
	public LocalDateTime dueDate; // Is Due to 7 days, ->fine amount to be paid -> invoice has to be created 
	
	@Column(name = "over_due_amount")
	public int overDueAmount;
	
	// Fine amount column
	@ManyToOne
    @JoinColumn(name = "book_id", nullable = false)
    private Book book;
	
	@Column(name = "quantity")
	public int quantity;
	
	public Vendor() {}

	public int getVendorId() {
		return vendorId;
	}

	public void setVendorId(int vendorId) {
		this.vendorId = vendorId;
	}

	public String getVendorName() {
		return vendorName;
	}

	public void setVendorName(String vendorName) {
		this.vendorName = vendorName;
	}

	public LocalDateTime getBookedDate() {
		return bookedDate;
	}

	public void setBookedDate(LocalDateTime bookedDate) {
		this.bookedDate = bookedDate;
	}

	public LocalDateTime getReturnedDate() {
		return returnedDate;
	}

	public void setReturnedDate(LocalDateTime returnedDate) {
		this.returnedDate = returnedDate;
	}

	public LocalDateTime getDueDate() {
		return dueDate;
	}

	public void setDueDate(LocalDateTime dueDate) {
		this.dueDate = dueDate;
	}

	public int getOverDueAmount() {
		return overDueAmount;
	}

	public void setOverDueAmount(int overDueAmount) {
		this.overDueAmount = overDueAmount;
	}
	
	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public Book getBook() {
		return book;
	}

	public void setBook(Book book) {
		this.book = book;
	}

	@Override
	public String toString() {
		return "Vendor [vendorId=" + vendorId + ", vendorName=" + vendorName + ", bookedDate=" + bookedDate
				+ ", returnedDate=" + returnedDate + ", dueDate=" + dueDate + ", overDueAmount=" + overDueAmount
				+ ", book=" + book + ", quantity=" + quantity + "]";
	}

	
}
