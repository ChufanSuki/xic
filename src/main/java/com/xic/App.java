package com.xic;

import java.util.concurrent.Callable;
import java.io.File;
import java.nio.file.Path;

import picocli.CommandLine;
import picocli.CommandLine.Option;
import picocli.CommandLine.Parameters;;

@CommandLine.Command (name = "xic", version = "xic 1.0", description = "xic compiler")
public class App implements Callable<Integer> 
{
    @Option(names = "--help", usageHelp = true, description = "Print a synopsis of options.")
    private boolean help = false;

    @Option(names = {"-l", "--lex"}, description = " Generate output from lexical analysis")
    private boolean lex = false;

    @Parameters(arity = "1..*", paramLabel =  "FILE", description = "File(s) to process.")
    private File[] inputFiles;

    @Option(names = "-D", defaultValue = ".", description = "Specify where to place generated diagnostic files.")
    Path outputPath;

    @Override
    public Integer call() throws Exception {

        return 0;
    }

    public static void main( String... args )
    {
        int exitCode = new CommandLine(new App()).execute(args);
        System.exit(exitCode);
    }
}
