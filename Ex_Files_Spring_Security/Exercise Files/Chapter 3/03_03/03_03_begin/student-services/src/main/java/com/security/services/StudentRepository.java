package com.security.services;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Frank P. Moley III.
 */
@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
}
