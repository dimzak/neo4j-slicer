package com.github.dimzak.neo4jslicer;

import com.github.dimzak.neo4jslicer.constants.Mode;
import com.github.dimzak.neo4jslicer.modes.AllModeRunner;
import com.github.dimzak.neo4jslicer.modes.ExportModeRunner;
import com.github.dimzak.neo4jslicer.modes.ImportModeRunner;
import com.github.dimzak.neo4jslicer.modes.ModeRunner;
import com.github.dimzak.neo4jslicer.validators.CommandLineValidator;
import org.apache.commons.cli.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import static java.lang.System.exit;


@SpringBootApplication
public class SpringBootConsoleApplication implements CommandLineRunner {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private CommandLineValidator commandLineValidator;

    @Autowired
    private AllModeRunner allModeRunner;

    @Autowired
    private ImportModeRunner importModeRunner;

    @Autowired
    private ExportModeRunner exportModeRunner;

    public static void main(String[] args) throws Exception {

        SpringApplication app = new SpringApplication(SpringBootConsoleApplication.class);
        app.setBannerMode(Banner.Mode.OFF);
        app.setLogStartupInfo(false);
        app.run(args);
    }

    @Override
    public void run(String... args) throws Exception {
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
                .desc("Cypher Match Query")
                .build();
        options.addOption(qOption);


        if(args.length==0) {
            // automatically generate the help statement
            HelpFormatter formatter = new HelpFormatter();
            formatter.printHelp("neo4jslicer", options);
            exit(0);
        }

        log.info("Neo4j slicer started!");

        //***Parsing Stage***
        //Create a parser
        CommandLineParser parser = new DefaultParser();

        //parse the options passed as command line arguments
        CommandLine cmd = parser.parse(options, args);

        Mode mode = commandLineValidator.getMode(cmd);

        if(mode==null) {
            exit(0);
        }

        // Do a basic validation before proceeding
        if(!commandLineValidator.validateOptions(cmd, mode)) {
            exit(0);
        }

        ModeRunner modeRunner = chooseModeRunner(mode);

        // Run forest run
        modeRunner.run(cmd);

        log.info("That's all folks!");
        exit(0);


    }

    private ModeRunner chooseModeRunner(Mode mode) {
        ModeRunner modeRunner = null;
        switch (mode) {
            case ALL:
                modeRunner = allModeRunner;
                break;
            case EXPORT:
                modeRunner = exportModeRunner;
                break;
            case IMPORT:
                modeRunner = importModeRunner;
                break;

        }
        return modeRunner;

    }
}