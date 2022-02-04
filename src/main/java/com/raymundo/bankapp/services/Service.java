package com.raymundo.bankapp.services;

import java.util.List;

public interface Service<T, V> {

    void create(T dto);

    void update(T dto);

    T get(V id);

    List<T> getAll();

    void delete(V id);

}
