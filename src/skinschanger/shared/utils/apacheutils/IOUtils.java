/*    */package skinschanger.shared.utils.apacheutils;

/*    */
/*    */import java.io.IOException;
/*    */
import java.io.InputStream;
/*    */
import java.io.InputStreamReader;
/*    */
import java.io.Reader;
/*    */
import java.io.Writer;
/*    */
import java.nio.charset.Charset;

/*    */
/*    */public class IOUtils
/*    */{
	/*    */public static String toString(InputStream is, Charset charset)
	/*    */throws IOException
	/*    */{
		/* 13 */InputStreamReader input = new InputStreamReader(is, charset);
		/* 14 */StringBuilderWriter sw = new StringBuilderWriter();
		/* 15 */copy(input, sw);
		/* 16 */return sw.toString();
		/*    */}

	/*    */
	/*    */private static int copy(Reader input, Writer output)
			throws IOException {
		/* 20 */long count = copyLarge(input, output);
		/* 21 */if (count > 2147483647L) {
			/* 22 */return -1;
			/*    */}
		/* 24 */return (int) count;
		/*    */}

	/*    */
	/*    */private static long copyLarge(Reader input, Writer output)
			throws IOException {
		/* 28 */return copyLarge(input, output, new char[4096]);
		/*    */}

	/*    */
	/*    */private static long copyLarge(Reader input, Writer output, char[] buffer)
			throws IOException {
		/* 32 */long count = 0L;
		/* 33 */int n = 0;
		/* 34 */while (-1 != (n = input.read(buffer))) {
			/* 35 */output.write(buffer, 0, n);
			/* 36 */count += n;
			/*    */}
		/* 38 */return count;
		/*    */}

	/*    */
	/*    */public static void closeQuietly(InputStream is) {
		/* 42 */if (is == null)
			/* 43 */return;
		/*    */try
		/*    */{
			/* 46 */is.close();
			/*    */}
		/*    */catch (IOException localIOException)
		/*    */{
			/*    */}
		/*    */}
	/*    */
}

/*
 * Location: C:\Users\Meano\Desktop\SkinsChanger.jar Qualified Name:
 * skinschanger.shared.utils.apacheutils.IOUtils JD-Core Version: 0.6.2
 */