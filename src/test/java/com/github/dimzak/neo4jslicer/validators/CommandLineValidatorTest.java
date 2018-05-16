package com.github.dimzak.neo4jslicer.validators;

import com.github.dimzak.neo4jslicer.constants.Mode;
import com.github.dimzak.neo4jslicer.testHelpers.TestHelpers;
import org.apache.commons.cli.CommandLine;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class CommandLineValidatorTest {

    private TestHelpers testHelpers = new TestHelpers();

    private CommandLineValidator commandLineValidator = new CommandLineValidator();

    @Test
    public void getModeNullCMD() {
        String[] input = new String[0];
        CommandLine commandLine = testHelpers.getCommandLineFromargs(input);
        assertEquals(null, commandLineValidator.getMode(commandLine));
    }

    @Test
    public void getModeCorrectCMD() {
        String[] input = { "-m=import"};
        CommandLine commandLine = testHelpers.getCommandLineFromargs(input);
        assertEquals(Mode.IMPORT, commandLineValidator.getMode(commandLine));

        input = new String[]{"-m=export"};
        commandLine = testHelpers.getCommandLineFromargs(input);
        assertEquals(Mode.EXPORT, commandLineValidator.getMode(commandLine));

        input = new String[]{"-m=all"};
        commandLine = testHelpers.getCommandLineFromargs(input);
        assertEquals(Mode.ALL, commandLineValidator.getMode(commandLine));
    }

    @Test
    public void validateOptionsTest() {
        String[] input = {"-e=bolt://neo4j:neo4j@127.0.0.1", "-i=bolt://neo4j:neo4j@127.0.0.1", "-q=\"MATCH (a:Address) RETURN *\"", "-f=export.cypher"};
        CommandLine commandLine = testHelpers.getCommandLineFromargs(input);
        assertEquals(true, commandLineValidator.validateOptions(commandLine, Mode.EXPORT));
        assertEquals(true, commandLineValidator.validateOptions(commandLine, Mode.IMPORT));
        assertEquals(true, commandLineValidator.validateOptions(commandLine, Mode.ALL));

        input = new String[]{"-i=bolt://neo4j:neo4j@127.0.0.1", "-q=\"MATCH (a:Address) RETURN *\"", "-f=export.cypher"};
        commandLine = testHelpers.getCommandLineFromargs(input);
        assertEquals(false, commandLineValidator.validateOptions(commandLine, Mode.EXPORT));
        assertEquals(true, commandLineValidator.validateOptions(commandLine, Mode.IMPORT));
        assertEquals(false, commandLineValidator.validateOptions(commandLine, Mode.ALL));

        input = new String[]{"-e=bolt://neo4j:neo4j@127.0.0.1","-i=bolt://neo4j:neo4j@127.0.0.1", "-q=\"MATCH (a:Address) RETURN *\""};
        commandLine = testHelpers.getCommandLineFromargs(input);
        assertEquals(false, commandLineValidator.validateOptions(commandLine, Mode.EXPORT));
        assertEquals(false, commandLineValidator.validateOptions(commandLine, Mode.IMPORT));
        assertEquals(false, commandLineValidator.validateOptions(commandLine, Mode.ALL));

        input = new String[]{"-e=bolt://neo4j:neo4j@127.0.0.1","-i=bolt://neo4j:neo4j@127.0.0.1", "-f=export.cypher"};
        commandLine = testHelpers.getCommandLineFromargs(input);
        assertEquals(false, commandLineValidator.validateOptions(commandLine, Mode.EXPORT));
        assertEquals(true, commandLineValidator.validateOptions(commandLine, Mode.IMPORT));

        input = new String[]{"-q=\"MATCH (a:Address) RETURN *\"", "-f=export.cypher"};
        commandLine = testHelpers.getCommandLineFromargs(input);
        assertEquals(false, commandLineValidator.validateOptions(commandLine, Mode.EXPORT));

        input = new String[]{"-e=http://neo4j:neo4j@127.0.0.1", "-i=http://neo4j:neo4j@127.0.0.1", "-q=\"MATCH (a:Address) RETURN *\"", "-f=export.cypher"};
        commandLine = testHelpers.getCommandLineFromargs(input);
        assertEquals(false, commandLineValidator.validateOptions(commandLine, Mode.EXPORT));
        assertEquals(false, commandLineValidator.validateOptions(commandLine, Mode.IMPORT));
    }
}
