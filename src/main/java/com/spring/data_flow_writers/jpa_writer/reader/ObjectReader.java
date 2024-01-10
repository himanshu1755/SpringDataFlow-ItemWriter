package com.spring.data_flow_writers.jpa_writer.reader;

import org.springframework.batch.item.ItemReader;

public class ObjectReader<T> implements ItemReader<T> {

    private boolean objectRead = false;
    private T objectToRead;

    public ObjectReader(T object) {
        this.objectToRead = object;
    }

    @Override
    public T read() {
        if (!objectRead) {
            objectRead = true;
            return objectToRead;
        }
        return null; // Return null to indicate no more items
    }
}
