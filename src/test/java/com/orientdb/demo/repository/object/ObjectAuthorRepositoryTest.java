package com.orientdb.demo.repository.object;

import com.orientdb.demo.domain.Author;
import com.orientechnologies.orient.core.id.ORecordId;
import com.orientechnologies.orient.object.db.OObjectDatabaseTx;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class ObjectAuthorRepositoryTest {

    private ObjectAuthorRepository repository = new ObjectAuthorRepository();
    private Author author;

    @Before
    public void setUp() {

        OObjectDatabaseTx db = new OObjectDatabaseTx("memory:test");

        if (db.exists()) {
            db.open("admin", "admin");
        } else {
            db.create();
        }

        db.setAutomaticSchemaGeneration(true);
        db.getEntityManager().registerEntityClasses("com.orientdb.demo.domain");

        setupData();
    }

    private void setupData() {
        author = repository.getConnection().save(new Author("Erik", Collections.emptyList()));
    }

    @After
    public void tearDown() {
        repository.getConnection().drop();
//        db.drop();   // TODO:BUG?
    }

    @Test
    public void testCount() {
        assertEquals(1, repository.count());
    }


    @Test
    public void testDeleteByRid() throws Exception {
        assertEquals(1, repository.count());
        repository.delete(author.getRid());
        assertEquals(0, repository.count());
    }

    @Test
    public void testDeleteByAuthorList() throws Exception {
        assertEquals(1, repository.count());
        repository.delete(Collections.singletonList(author));
        assertEquals(0, repository.count());
    }

    @Test
    public void testDeleteByAuthor() throws Exception {
        assertEquals(1, repository.count());
        repository.delete(author);
        assertEquals(0, repository.count());
    }

    @Test
    public void testExists() throws Exception {
        assertTrue(repository.exists(author.getRid()));
        assertFalse(repository.exists(new ORecordId("0:1234567890")));
    }

    @Test
    public void testFindAll() throws Exception {
        assertThat(repository.findAll()).hasSize(1);
    }

    @Test
    public void testFindAllByRids() throws Exception {
        assertThat(repository.findAll(Arrays.asList(author.getRid(),new ORecordId("0:1234567890")))).hasSize(1);
        assertThat(repository.findAll(Collections.singletonList(new ORecordId("0:1234567890")))).hasSize(0);
    }

    @Test
    public void testFindOne() throws Exception {
        assertThat(repository.findOne(author.getRid())).isNotNull();
        assertThat(repository.findOne(new ORecordId("0:1234567890"))).isNull();
    }

    @Test
    public void testSaveMultiple() throws Exception {

    }

    @Test
    public void testSaveOne() throws Exception {

    }
}