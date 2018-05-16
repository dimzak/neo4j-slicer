package com.github.dimzak.neo4jslicer.testHelpers;

import org.apache.commons.cli.*;
import org.springframework.stereotype.Component;

@Component
public class TestHelpers {

    public CommandLine getCommandLineFromargs(String[] args) {

        // todo code duplication
        Options options = new Options();

        Option mOption = Option.builder()
                .longOpt("m")
                .argName("mode" )
                .hasArg()
                .desc("Choose mode between import, export, all")
                .build();
        options.addOption(mOption);

        Option iOption = Option.builder()
                .longOpt("i")
                .argName("import" )
                .hasArg()
                .desc("Import neo4j bolt url")
                .build();
        options.addOption(iOption);

        Option eOption = Option.builder()
                .longOpt("e")
                .argName("export" )
                .hasArg()
                .desc("Export neo4j bolt url")
                .build();
        options.addOption(eOption);

        Option fOption = Option.builder()
                .longOpt("f")
                .argName("file" )
                .hasArg()
                .desc("Cypher file location")
                .build();
        options.addOption(fOption);

        Option qOption = Option.builder()
                .longOpt("q")
                .argName("query" )
                .hasArg()
                .desc("Cypher Match Query - assume return is *")
                .build();
        options.addOption(qOption);

        CommandLineParser parser = new DefaultParser();

        //parse the options passed as command line arguments
        try {
            return parser.parse(options, args);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }
}
