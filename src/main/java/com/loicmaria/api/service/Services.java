package com.loicmaria.api.service;

import lombok.Data;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.Iterator;

@Data
public abstract class Services<U, T, S extends JpaRepository<U, Integer>> {

    @Autowired
    public ModelMapper modelMapper;

    @Autowired
    public S repository;

    public abstract U convertDtoToEntity(T val);

    public abstract T convertEntityToDto(U val);

    public Collection<T> convertCollectionToDto(Collection<U> val){
        Collection<T> test2 = null;
        Iterator<U> test = val.iterator();

        while (test.hasNext()){
            test2.add(convertEntityToDto(test.next()));
        }

        return test2;
    }



    public Collection<T> getter() {
        Collection<U> val = repository.findAll();
        Collection<T> val2 = convertCollectionToDto(val);
        return val2;
    }


    public T get(int id) {
        T val = this.convertEntityToDto(repository.findById(id).get());
        return val;
    }


    public T save(T val) {
        U val2 = this.convertDtoToEntity(val);
        repository.save(val2);
        val = this.convertEntityToDto(val2);
        return val;
    }


    public void delete(int id) {
        repository.deleteById(id);
    }
}
