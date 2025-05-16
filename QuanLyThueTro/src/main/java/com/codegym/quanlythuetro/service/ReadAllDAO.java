package com.codegym.quanlythuetro.service;

import java.sql.SQLException;
import java.util.List;

public interface ReadAllDAO<E> {
    List<E> findAll() throws SQLException;
}
