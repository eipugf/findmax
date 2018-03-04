package groovy.ru.eipugf

import ru.eipugf.FindMax
import spock.lang.Shared
import spock.lang.Specification
import spock.lang.Unroll

/**
 * Spec for MaxFind.
 */
class FindMaxSpec extends Specification {
    @Shared
    def resDir = 'src/test/resources'

    @Shared
    def outContent = new ByteArrayOutputStream()

    @Shared
    def errContent = new ByteArrayOutputStream()


    def setupSpec(){
        System.setOut(new PrintStream(outContent))
        System.setErr(new PrintStream(errContent))
    }

    @Unroll
    def "test FindMax #file nmax #nmax stdOut=#result stdErr=#stdErr" () {
        setup:
        outContent.reset()
        errContent.reset()
        when:
        def args = (String [])["--file=$resDir/$file","--nmax=$nmax"]
        FindMax.main(args)
        def stdOutStr = outContent.toString()
        def stdErrStr = errContent.toString()
        then:
        stdOutStr == result
        stdErrStr == stdErr
        where:
        file                    | nmax | stdErr|| result
        'test_correct_file.txt' | 5    | ''    || '66\n77\n88\n99\n100\n'
        'test_correct_file.txt' | 1    | ''    || '100\n'
        'test_empty.txt'        | 5    | ''    || ''
        'test_not_correct.txt'  | 5    | ''    || ''
        'test_with_err_str.txt' | 3    | ''    || '20\n60\n100\n'
        'not_exist_file.txt'    | 3    |
                "java.nio.file.NoSuchFileException: $resDir/$file\n" || ''
    }

    @Unroll
    def "test main app version with args #args" () {
        setup:
        outContent.reset()
        errContent.reset()
        when:
        FindMax.main((String[])args)
        def stdOutStr = outContent.toString()
        then:
        stdOutStr == result
        where:
        args            || result
        ['-V']          || "$FindMax.VERSION\n"
        ['-v']          || "$FindMax.VERSION\n"
        ['--version']   || "$FindMax.VERSION\n"
    }

}
