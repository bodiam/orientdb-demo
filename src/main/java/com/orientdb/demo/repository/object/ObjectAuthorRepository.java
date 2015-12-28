package com.orientdb.demo.repository.object;

import com.orientdb.demo.domain.Author;
import com.orientechnologies.orient.core.db.OPartitionedDatabasePool;
import com.orientechnologies.orient.core.id.ORID;
import com.orientechnologies.orient.core.sql.query.OSQLSynchQuery;
import com.orientechnologies.orient.object.db.OObjectDatabaseTx;
import sun.security.provider.certpath.Vertex;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

/**
 * @author Erik Pragt
 */
public class ObjectAuthorRepository {

    private final OPartitionedDatabasePool pool;

    public ObjectAuthorRepository() {
        pool = new OPartitionedDatabasePool("memory:test", "admin", "admin");
    }

    public OObjectDatabaseTx getConnection() {
        return new OObjectDatabaseTx(pool.acquire());
    }

    public long count() {
        try (OObjectDatabaseTx db = new OObjectDatabaseTx(pool.acquire())) {
            return db.countClass(Author.class);
        }
    }

    public void delete(ORID rid) {
        try (OObjectDatabaseTx db = new OObjectDatabaseTx(pool.acquire())) {
            db.delete(rid);
        }
    }

    public void delete(Iterable<? extends Author> entities) {
        try (OObjectDatabaseTx db = new OObjectDatabaseTx(pool.acquire())) {
            for (Author entity : entities) {
                db.delete(entity);
            }
        }
    }

    public void delete(Author author) {
        try (OObjectDatabaseTx db = new OObjectDatabaseTx(pool.acquire())) {
            db.delete(author);
        }
    }

    public boolean exists(ORID id) {
        try (OObjectDatabaseTx db = new OObjectDatabaseTx(pool.acquire())) {
            return db.load(id) != null;
        }
    }

    public Iterable<Author> findAll() {
        try (OObjectDatabaseTx db = new OObjectDatabaseTx(pool.acquire())) {
            return db.browseClass(Author.class);
        }
    }

    public Iterable<Author> findAll(Iterable<ORID> ids) {
        try (OObjectDatabaseTx db = new OObjectDatabaseTx(pool.acquire())) {
//            return db.command(new OSQLSynchQuery<Author>("select from Author where @rid in (?)")).execute(ids.toString());
            return db.command(new OSQLSynchQuery<Author>("select from " +ids.toString())).execute();
        }
    }

    public Author findOne(ORID id) {
        try (OObjectDatabaseTx db = new OObjectDatabaseTx(pool.acquire())) {
            return db.load(id);
        }
    }

    public void save(Iterable<Author> entities) {
        try (OObjectDatabaseTx db = new OObjectDatabaseTx(pool.acquire())) {
            for (Author entity : entities) {
                db.save(entity);
            }
        }
    }

    public void save(Author author) {
        try (OObjectDatabaseTx db = new OObjectDatabaseTx(pool.acquire())) {
            db.save(author);
        }

    }
}
