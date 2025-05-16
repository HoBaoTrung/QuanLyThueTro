package com.codegym.quanlythuetro.service;

import com.codegym.quanlythuetro.model.RentalRoom;

import java.sql.SQLException;

public interface InsertDAO<E>
{
   boolean insert(RentalRoom room)throws SQLException;
}
