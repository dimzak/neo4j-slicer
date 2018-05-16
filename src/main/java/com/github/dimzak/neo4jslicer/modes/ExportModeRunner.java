package com.github.dimzak.neo4jslicer.modes;

import com.github.dimzak.neo4jslicer.services.Neo4jService;
import org.apache.commons.cli.CommandLine;
import org.neo4j.driver.v1.Driver;
import org.neo4j.driver.v1.Session;
import org.neo4j.driver.v1.StatementResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ExportModeRunner implements ModeRunner {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private Neo4jService neo4jService;

    private Driver driver;

    @Override
    public void run(CommandLine commandLineArgs) {
        log.info("Export mode activated");

        String bolt_url = commandLineArgs.getOptionValue("e");
        String query = commandLineArgs.getOptionValue("q");
        String filePath = commandLineArgs.getOptionValue("f");

        Session currentSession = null;

        try {
            driver = neo4jService.getNeo4jDriver(bolt_url);
            currentSession = neo4jService.openSession(driver);

            String apocQuery = neo4jService.constructApocExportQuery(query, filePath);

            StatementResult result = neo4jService.runCommand(currentSession,apocQuery);
            log.info(result.summary().toString());

        } catch (Exception e) {
            log.error(e.getLocalizedMessage());
        } finally {
            neo4jService.close(currentSession, driver);
        }



    }
}
