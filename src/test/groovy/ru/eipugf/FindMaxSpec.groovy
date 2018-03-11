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

package ru.eipugf

import spock.lang.Shared
import spock.lang.Specification
import spock.lang.Unroll

/**
 * Spec for MaxFind.
 *
 * @author Evgeny Karsskiy
 * @see {@link ru.eipugf.FindMax}
 * @since 0.0.1
 */
class FindMaxSpec extends Specification {
    /**
     * Resources dir path.
     */
    @Shared
    def resDir = 'src/test/resources'

    /**
     * Std output stream.
     */
    @Shared
    def outContent = new ByteArrayOutputStream()

    /**
     * Std error stream.
     */
    @Shared
    def errContent = new ByteArrayOutputStream()

    /**
     * Setup spec for all tests. Redirected stderr and stdout.
     */
    def setupSpec() {
        System.setOut(new PrintStream(outContent))
        System.setErr(new PrintStream(errContent))
    }

    /**
     * Tests for {@link ru.eipugf.FindMax#main(String [] args) )}.
     */
    @Unroll
    def "test FindMax #file nmax #nmax stdOut=#result stdErr=#stdErr"() {
        setup:
        outContent.reset()
        errContent.reset()
        when:
        def args = (String[]) ["--file=$resDir/$file", "--nmax=$nmax"]
        FindMax.main(args)
        def stdOutStr = outContent.toString()
        def stdErrStr = errContent.toString()
        then:
        stdOutStr == result
        stdErrStr == stdErr
        where:
        file                    | nmax | result                  || stdErr
        'test_correct_file.txt' | 5    | '66\n77\n88\n99\n100\n' || ''
        'test_correct_file.txt' | 1    | '100\n'                 || ''
        'test_empty.txt'        | 5    | ''                      || ''
        'test_not_correct.txt'  | 5    | ''                      || ''
        'test_with_err_str.txt' | 3    | '20\n60\n100\n'         || ''
        'test_with_err_str.txt' | -10  | ''                      || 'Parameter --nmax must be greater zero.\n'
        'not_exist_file.txt'    | 3    | ''                      || "java.nio.file.NoSuchFileException: $resDir/$file\n"
    }

    /**
     * Tests for {@link ru.eipugf.FindMax#main(String [] args) )}.
     *
     */
    @Unroll
    def "test main app version with args #args"() {
        setup:
        outContent.reset()
        errContent.reset()
        when:
        FindMax.main((String[]) args)
        def stdOutStr = outContent.toString()
        then:
        stdOutStr == result
        where:
        args          || result
        ['-V']        || "$FindMax.VERSION\n"
        ['-v']        || "$FindMax.VERSION\n"
        ['--version'] || "$FindMax.VERSION\n"
    }

}
