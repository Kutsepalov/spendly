package com.acceleron.spendly.core.enricher;

public interface Enricher <T> {

    T enrich(T object);
}
