package com.github.dimzak.neo4jslicer.modes;

import org.apache.commons.cli.CommandLine;

import java.io.IOException;

public interface ModeRunner {

    void run(CommandLine commandLineArgs) throws IOException;
}
