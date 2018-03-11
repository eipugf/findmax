/*
 * Copyright 2018 Evgeny Karsskiy
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package ru.eipugf;

import one.util.streamex.StreamEx;
import org.apache.commons.lang3.StringUtils;
import picocli.CommandLine;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Stream;

/**
 * Console application interface for searching n maximal long numbers for a
 * large file.
 * This class implements the function of displaying reference information
 * about the application and the version of the application.
 *
 * @author Evgeny Karsskiy
 * @since 0.0.1
 */
@CommandLine.Command(
        name = "find-max",
        version = FindMax.VERSION,
        header = "%nFind Max console util%n",
        description = "Program for finding the maximum numbers in a large file",
        footer = "%nSee email:eipugf@gmail.com", showDefaultValues = true)
public class FindMax implements Runnable {
    /**
     * This field contains the version of the console application for the
     * version in the help information.
     */
    public static final String VERSION = "Maximums finder application v0.0.1";

    /**
     * The path to the file in which the numbers are row-by-line.
     * This parameter is required.
     */
    @CommandLine.Option(names = "--file",
            required = true,
            description = "Path to file")
    private Path pathToFile;

    /**
     * Contains Desired number of maximum long elements to be found in the
     * file.
     */
    @CommandLine.Option(names = "--nmax",
            description = "The number of maximums")
    private int numberOfMaximums = 1;

    /**
     * {@code true} if the help is called.
     */
    @CommandLine.Option(names = {"-h", "-H", "--help"}, usageHelp = true,
            description = "Print usage help and exit.")
    private boolean usageHelpRequested;

    /**
     * {@code true} if a version help is called.
     */
    @CommandLine.Option(names = {"-V", "-v", "--version"}, versionHelp = true,
            description = "Print version information and exit.")
    private boolean versionHelpRequested;

    /**
     * The main function of the application.
     *
     * @param args Parameters entered from the command line.
     */
    public static void main(final String[] args) {
        CommandLine.run(new FindMax(), System.out, args);
    }

    /**
     * In this method, the file is read and the search for the largest
     * elements is performed.
     * The output is done in std out.
     */
    @Override
    public final void run() {
        if (numberOfMaximums <= 0) {
            System.err.println("Parameter --nmax must be greater zero.");
            return;
        }
        try (Stream<String> stream = Files.lines(pathToFile)) {
            StreamEx.of(stream)
                    .filter(StringUtils::isNumeric)
                    .mapToLong(Long::parseLong)
                    .collect(CollectorsEx.collectNMax(numberOfMaximums))
                    .stream()
                    .forEach(System.out::println);
        } catch (IOException ex) {
            System.err.println(ex);
        }
    }
}
