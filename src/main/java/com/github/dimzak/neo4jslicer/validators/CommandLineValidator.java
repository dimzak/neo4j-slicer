package com.github.dimzak.neo4jslicer.validators;

import com.github.dimzak.neo4jslicer.constants.Mode;
import com.github.dimzak.neo4jslicer.helpers.MiscHelpers;
import org.apache.commons.cli.CommandLine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import static java.lang.System.exit;

/**
 * Validator for script's command line args
 */
@Component
public class CommandLineValidator {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    public Mode getMode(CommandLine cmd) {
        Mode mode = null;
        if(cmd.hasOption("m")) {
            try {
                mode = MiscHelpers.getMode(cmd.getOptionValue("m"));
            } catch(Exception e) {
                log.error("Unknown mode, exiting");
                exit(1);

            }
        } else {
            log.error("Mode [-m] is not set");
        }
        return mode;
    }

    public boolean validateOptions(CommandLine cmd, Mode m) {
        boolean validationResult = true;
        if(m.equals(Mode.ALL) || m.equals(Mode.IMPORT)) {

            // neo4j instance check
            if(cmd.hasOption("i")) {
                if(!cmd.getOptionValue("i").startsWith("bolt://")) {
                    log.error("Import neo4j bolt url is not valid [-i]");
                    validationResult = false;
                }

            } else {
                log.error("Import neo4j bolt url not found [-i] while using mode:" + m);
                validationResult = false;
            }
        }

        if(m.equals(Mode.ALL) || m.equals(Mode.EXPORT)) {

            // neo4j instance check
            if(cmd.hasOption("e")) {
                if(!cmd.getOptionValue("e").startsWith("bolt://")) {
                    log.error("Export neo4j bolt url is not valid [-e]");
                    validationResult = false;
                }

            } else {
                log.error("Export neo4j bolt url not found [-e] while using mode:" + m);
                validationResult = false;
            }

            // All modes need export.cypher file
            if (!cmd.hasOption("q")) {
                log.error("Cypher match query not specified [-q]");
                validationResult = false;
            }

        }

        // All modes need export.cypher file
        if (!cmd.hasOption("f")) {
            log.error("Export cypher file not specified [-f]");
            validationResult = false;
        }
        return validationResult;
    }
}
