import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * This class is used to read from text files.
 * 
 * @author Mr. Shelby
 *
 */
public class TextReader {
	private String str = System.getProperty("line.separator");
	char lineSep = str.charAt(0);
	private String filename;

	public TextReader(String filename) {
		this.filename = filename;
	}

	// only works for files where the lines and chars per line are the same
	public String[][] get2DArray() {
		FileReader reader = null;
		int i = 0;
		String[][] result;
		try {
			int lines = countLines() + 1;
			result = new String[lines][lines];
			reader = new FileReader(filename);
			Scanner in = new Scanner(reader);
			while (in.hasNext()) {
				String input = in.nextLine();
				result[i] = input.split("");
				i++;
			}
		} catch (IOException ex) {
			System.out.println("File cannot be read.");
			return null;
		} finally {
			try {
				reader.close();
			} catch (IOException ex) {
				System.out.println("File cannot be closed.");
				return null;
			}
		}

		return result;
	}

	public int countLines() throws IOException {
		InputStream is = new BufferedInputStream(new FileInputStream(filename));
		try {
			byte[] c = new byte[1024];
			int count = 0;
			int readChars = 0;
			boolean empty = true;
			while ((readChars = is.read(c)) != -1) {
				empty = false;
				for (int i = 0; i < readChars; ++i) {
					if (c[i] == lineSep) {
						++count;
					}
				}
			}
			return (count == 0 && !empty) ? 1 : count;
		} finally {
			is.close();
		}
	}

}
