package org.agileengine.callboard.persistence.dao;

import org.agileengine.callboard.application.configuration.TestConfig;
import org.dbunit.database.DatabaseDataSourceConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.IDataSet;
import org.dbunit.operation.DatabaseOperation;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.jdbc.datasource.init.DatabasePopulatorUtils;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import javax.sql.DataSource;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {TestConfig.class})
public abstract class AbstractDAOTest extends AbstractTransactionalJUnit4SpringContextTests {

    @Autowired
    private DataSource dataSource;

    @Autowired
    private ResourceLoader loader;

    @Before
    public void setUp() throws Exception {
        ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
        populator.addScript(loader.getResource("schema.sql"));
        DatabasePopulatorUtils.execute(populator, dataSource);

        IDatabaseConnection dbConn = new DatabaseDataSourceConnection(dataSource);
        DatabaseOperation.CLEAN_INSERT.execute(dbConn, getDataSet());
    }

    protected abstract IDataSet getDataSet() throws Exception;

    protected abstract void create();
    protected abstract void update();
    protected abstract void remove();
    protected abstract void findById();
    protected abstract void findAll();
    protected abstract void findLast();
}
