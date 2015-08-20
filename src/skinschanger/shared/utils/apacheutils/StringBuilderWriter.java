/*    */package skinschanger.shared.utils.apacheutils;

/*    */
/*    */import java.io.Serializable;
/*    */
import java.io.Writer;

/*    */
/*    */public class StringBuilderWriter extends Writer
/*    */implements Serializable
/*    */{
	/*    */private static final long serialVersionUID = 658835771565808301L;
	/* 10 */private final StringBuilder builder = new StringBuilder();

	/*    */
	/*    */public Writer append(char value)
	/*    */{
		/* 14 */this.builder.append(value);
		/* 15 */return this;
		/*    */}

	/*    */
	/*    */public Writer append(CharSequence value)
	/*    */{
		/* 20 */this.builder.append(value);
		/* 21 */return this;
		/*    */}

	/*    */
	/*    */public Writer append(CharSequence value, int start, int end)
	/*    */{
		/* 26 */this.builder.append(value, start, end);
		/* 27 */return this;
		/*    */}

	/*    */
	/*    */public void close()
	/*    */{
		/*    */}

	/*    */
	/*    */public void flush()
	/*    */{
		/*    */}

	/*    */
	/*    */public void write(String value)
	/*    */{
		/* 40 */if (value != null)
			/* 41 */this.builder.append(value);
		/*    */}

	/*    */
	/*    */public void write(char[] value, int offset, int length)
	/*    */{
		/* 47 */if (value != null)
			/* 48 */this.builder.append(value, offset, length);
		/*    */}

	/*    */
	/*    */public StringBuilder getBuilder()
	/*    */{
		/* 53 */return this.builder;
		/*    */}

	/*    */
	/*    */public String toString()
	/*    */{
		/* 58 */return this.builder.toString();
		/*    */}
	/*    */
}

/*
 * Location: C:\Users\Meano\Desktop\SkinsChanger.jar Qualified Name:
 * skinschanger.shared.utils.apacheutils.StringBuilderWriter JD-Core Version:
 * 0.6.2
 */