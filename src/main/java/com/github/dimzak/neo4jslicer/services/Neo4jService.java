package com.github.dimzak.neo4jslicer.services;

import com.github.dimzak.neo4jslicer.helpers.MiscHelpers;
import org.neo4j.driver.v1.Driver;
import org.neo4j.driver.v1.GraphDatabase;
import org.neo4j.driver.v1.Session;
import org.neo4j.driver.v1.StatementResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.PreDestroy;

/**
 * Service for all neo4j related tasks
 */
@Service
public class Neo4jService {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    /**
     * Get neo4j driver for a given bolt uri
     *
     * @param uri
     * @return {@link Driver}
     */
    public Driver getNeo4jDriver(String uri) {
        Driver driver = GraphDatabase.driver(uri);
        log.info("Connected to neo4j instance with uri: " + uri);
        return driver;
    }

    public String constructApocExportQuery(String query, String filePath) {
        return "CALL apoc.export.cypher.query(\"" + MiscHelpers.escapeDoubleQuotes(query) + "\",\"" + filePath + "\",{format: \"plain\"});";

    }

    public Session openSession(Driver driver) {
        return driver.session();
    }

    public StatementResult runCommand(Session session, String command) {
        StatementResult statementResult = null;
        try {
            statementResult = session.run(command);
        } catch (Exception e) {
            log.error(e.getLocalizedMessage());
        }
        return statementResult;
    }

    public void close(Session session, Driver driver) {
        try {
            if (session != null) {
                session.close();
            }
            if (driver != null) {
                driver.close();
            }
        } catch (Exception e) {
            log.error("Exception while closing neo4j connection, trying to close driver one more time");
            if (driver != null) {
                driver.close();
            }
        }
    }
}
