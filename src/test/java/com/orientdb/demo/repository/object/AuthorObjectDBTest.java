package com.orientdb.demo.repository.object;

import com.orientdb.demo.domain.Author;
import com.orientdb.demo.domain.Book;
import com.orientechnologies.orient.core.id.ORID;
import com.orientechnologies.orient.core.id.ORecordId;
import com.orientechnologies.orient.core.sql.query.OSQLSynchQuery;
import com.orientechnologies.orient.object.db.OObjectDatabaseTx;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.*;

import static org.junit.Assert.assertEquals;

/**
 * See http://orientdb.com/docs/2.0/orientdb.wiki/Object-Database.html
 * http://orientdb.com/docs/2.0/orientdb.wiki/Object-2-Record-Java-Binding.html
 *
 * @author Erik Pragt
 */
public class AuthorObjectDBTest {

    private OObjectDatabaseTx db;

    @Before
    public void setUp() {

        db = new OObjectDatabaseTx("memory:test");

        if (db.exists()) {
            db.open("admin", "admin");
        } else {
            db.create();
        }

        db.setAutomaticSchemaGeneration(true);
        db.getEntityManager().registerEntityClasses("com.orientdb.demo.domain");

        /*
        OClass book = db.getMetadata().getSchema().createClass(Book.class);
        book.createProperty("title", OType.STRING);
        book.createProperty("pages", OType.INTEGER);
        book.createProperty("publicationDate", OType.DATETIME);

        OClass author = db.getMetadata().getSchema().createClass(Author.class);
        author.createProperty("name", OType.STRING);
        author.createProperty("books", OType.LINKLIST, book);
        author.createIndex("nameIdx", OClass.INDEX_TYPE.UNIQUE, "name");

        db.getEntityManager().registerEntityClass(Book.class);
        db.getEntityManager().registerEntityClass(Author.class);
        */
    }

    @After
    public void tearDown() {
        db.drop();
    }


    @Test
    public void storeNewProxyAuthor() {

        Author saved = db.save(db.newInstance(Author.class, "Erik Pragt", new ArrayList<Book>()));

        assertEquals("Erik Pragt", saved.getName());
        assertEquals(1, db.countClass(Author.class));
    }

    @Test
    public void storeNewPojoAuthor() {

        Author saved = db.save(new Author("Erik Pragt", Collections.emptyList()));

        assertEquals("Erik Pragt", saved.getName());
        assertEquals(1, db.countClass(Author.class));
    }

    @Test
    public void loadByRid() {
        Author saved = db.save(new Author("Erik Pragt", Collections.emptyList()));

        ORID rid = saved.getRid();
        Author rAuthor = db.load(rid);
        assertEquals("Erik Pragt", rAuthor.getName());
    }

    @Test
    public void findByParameterQuery() {
        db.save(new Author("Erik Pragt", Collections.emptyList()));

        List<Author> authors = db.query(new OSQLSynchQuery<Author>("select * from Author where name = ?"), "Erik Pragt");

        assertEquals(1, authors.size());
    }

    @Test
    public void findByNamedParameterQuery() {
        db.save(new Author("Erik Pragt", Collections.emptyList()));

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("name", "Erik Pragt");

        List<Author> authors = db.query(new OSQLSynchQuery<Author>("select * from Author where name = :name"), parameters);

        assertEquals(1, authors.size());
    }

    @Test
    public void findByNamedParameterCommandQuery() {
        db.save(new Author("Erik Pragt", Collections.emptyList()));

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("name", "Erik Pragt");

        List<Author> authors = db.command(new OSQLSynchQuery<Author>("select * from Author where name = :name")).execute(parameters);

        assertEquals(1, authors.size());
    }

    // TODO: update


    @Test
    public void deleteProxyAuthor() {
        Author saved = db.save(db.newInstance(Author.class, "Erik Pragt", new ArrayList<Book>()));
        assertEquals(1, db.countClass(Author.class));

        db.delete(saved);
        assertEquals(0, db.countClass(Author.class));
    }

    @Test
    public void deleteRidAuthor() {
        Author saved = db.save(new Author("Erik Pragt", Collections.emptyList()));
        assertEquals(1, db.countClass(Author.class));

        ORecordId rid = (ORecordId) saved.getRid();

        db.delete(rid);
        assertEquals(0, db.countClass(Author.class));
    }

}