package com.example.lms.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.lms.entity.Vendor;

public interface VendorRepository extends JpaRepository<Vendor, Integer> {
    List<Vendor> findAllByBook_BookId(int bookId);

	List<Vendor> findByVendorName(String vendorName);
}

