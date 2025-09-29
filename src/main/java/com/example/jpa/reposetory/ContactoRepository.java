package com.example.jpa.reposetory;

import com.example.jpa.models.ContactoModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContactoRepository extends JpaRepository<ContactoModel,Long> {
}
