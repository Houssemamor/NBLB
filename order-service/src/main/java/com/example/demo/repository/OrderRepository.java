package com.example.demo.repository;

import jakarta.persistence.metamodel.SingularAttribute;
import com.example.demo.model.Order;
import org.springframework.data.jpa.domain.AbstractPersistable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long> {


    List<Order> id(long id);

    Optional<Order> findById(SingularAttribute<AbstractPersistable, Serializable> id);

    void deleteById(SingularAttribute<AbstractPersistable, Serializable> id);
}