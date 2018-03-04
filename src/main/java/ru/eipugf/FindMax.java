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
import java.nio.file.Paths;
import java.util.stream.Stream;

/**
 * <p>The main class of the application for
 * finding n maximal numbers from a large file.</p>
 *
 * @author Evgeny Karsskiy
 * @since 0.0.1
 */
@CommandLine.Command(version = FindMax.VERSION,
        header = "%nFind Max console util%n",
        description = "Program for finding the maximum numbers in a large file",
        footer = "%nSee email:eipugf@gmail.com", showDefaultValues = true)
public class FindMax implements Runnable {
    /**
     * Application version.
     */
    public static final String VERSION = "Maximums finder application v0.0.1";

    /**
     * Path to file.
     */
    @CommandLine.Option(names = "--file",
            description = "Path to file")
    private String file;

    /**
     * Desirable number of maximums. default value 1.
     */
    @CommandLine.Option(names = "--nmax",
            description = "The number of maximums")
    private int nmax = 1;

    /**
     * Help call flag.
     */
    @CommandLine.Option(names = {"-h", "-H", "--help"}, usageHelp = true,
            description = "Print usage help and exit.")
    private boolean usageHelpRequested;

    /**
     * Version call flag.
     */
    @CommandLine.Option(names = {"-V", "-v", "--version"}, versionHelp = true,
            description = "Print version information and exit.")
    private boolean versionHelpRequested;

    /**
     * App entry point.
     *
     * @param args console params
     */
    public static void main(final String[] args) {
        CommandLine.run(new FindMax(), System.out, args);
    }

    /**
     * <p>App logic.
     * 1. version request - printing app version.
     * 2. help request or empty params - printing instruction.
     * 3. reading and finding maximums from file.
     * </p>
     */
    @Override
    public final void run() {
        if (versionHelpRequested) {
            new CommandLine(this).printVersionHelp(System.err);
        } else if (usageHelpRequested || file == null || nmax <= 0) {
            new CommandLine(this).usage(System.err);
        } else {
            try (Stream<String> stream = Files.lines(Paths.get(file))) {
                StreamEx.of(stream)
                        .filter(StringUtils::isNumeric)
                        .mapToLong(Long::parseLong)
                        .collect(CollectorsEx.collectNMax(nmax))
                        .stream()
                        .forEach(System.out::println);
            } catch (IOException ex) {
                System.err.println(ex);
            }
        }
    }
}
