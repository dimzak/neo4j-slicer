package com.github.dimzak.neo4jslicer.modes;

import org.apache.commons.cli.CommandLine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AllModeRunner implements ModeRunner {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ExportModeRunner exportModeRunner;

    @Autowired
    private ImportModeRunner importModeRunner;

    @Override
    public void run(CommandLine commandLineArgs) {
        log.info("All mode activated");

        exportModeRunner.run(commandLineArgs);
        importModeRunner.run(commandLineArgs);

    }
}
