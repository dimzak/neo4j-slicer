package com.github.dimzak.neo4jslicer.modes;

import com.github.dimzak.neo4jslicer.helpers.FileHelpers;
import com.github.dimzak.neo4jslicer.services.Neo4jService;
import org.apache.commons.cli.CommandLine;
import org.neo4j.driver.v1.Driver;
import org.neo4j.driver.v1.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.FileReader;

import static java.lang.System.exit;

@Component
public class ImportModeRunner implements ModeRunner {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private Neo4jService neo4jService;

    private Driver driver;

    @Override
    public void run(CommandLine commandLineArgs) {
        log.info("Import mode activated!");
        String file_path = commandLineArgs.getOptionValue("f");
        String bolt_url = commandLineArgs.getOptionValue("i");

        driver = neo4jService.getNeo4jDriver(bolt_url);

        if(!FileHelpers.fileExists(file_path)) {
            log.error("File doesn't exist: " + file_path);
            exit(1);
        }

        long lineCount = FileHelpers.getFileLineCount(file_path);
        long currentCount =0;

        Session currentSession = null;
        try {
            currentSession = neo4jService.openSession(driver);

            BufferedReader reader = new BufferedReader(new FileReader(file_path));
            String line;
            while ((line = reader.readLine()) != null) {
                currentCount = currentCount + 1;
                neo4jService.runCommand(currentSession, line);
                log.info("Executed command at index: " + currentCount + " from a total of " + lineCount);
            }
            reader.close();
        } catch (Exception e) {
            log.error("Exception occurred trying to read from "+ file_path);
            log.error(e.getLocalizedMessage());
        } finally {
            neo4jService.close(currentSession, driver);
        }
    }
}
