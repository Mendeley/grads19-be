package grads19.services;

import grads19.batch.Conference;

import java.util.List;

public interface ElasticSearchService {
    boolean createIndices();

    void insertData(List<Conference> conferences);

    boolean deleteIndices();
}
