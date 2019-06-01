import java.util.*;
import java.io.*;

public class VectorQuantizer {
	public static int EuclidDistance(Vector<Integer> x, Vector<Integer> y, int factor) {
		int distance = 0;
		for (int i = 0; i < x.size(); i++)
			distance += Math.pow(x.get(i) - y.get(i) + factor, 2);

		return (int) Math.sqrt(distance);

	}

	public static Vector<Integer> getAverages(Vector<Vector<Integer>> in) {
		Vector<Integer> res = new Vector<Integer>();
		int sum[] = new int[in.get(0).size()];

		for (Vector<Integer> v : in) {
			for (int i = 0; i < v.size(); i++)
				sum[i] += v.get(i);
		}
		for (int i = 0; i < sum.length; i++) {
			res.add(sum[i] / in.size());
		}
		return res;
	}

	public static void quantization(int level, Vector<Vector<Integer>> data, Vector<Vector<Integer>> q) {
		if (level == 1 || data.size() == 0) {
			if (data.size() > 0)
				q.add(getAverages(data));
			return;
		}
		// Split
		Vector<Vector<Integer>> leftVectors = new Vector<>();
		Vector<Vector<Integer>> rightVectors = new Vector<>();

		// Calculate Average Vector
		Vector<Integer> mean = getAverages(data);

		// Calculate Euclidean Distance
		for (Vector<Integer> vec : data) {
			int eDistance1 = EuclidDistance(vec, mean, 1);
			int eDistance2 = EuclidDistance(vec, mean, -1);
			// Add To Right OR Left Vector
			if (eDistance1 >= eDistance2)
				leftVectors.add(vec);
			else
				rightVectors.add(vec);
		}

		// Recurse
		quantization(level / 2, leftVectors, q);
		quantization(level / 2, rightVectors, q);

	}

	public static Vector<Integer> getIndices(Vector<Vector<Integer>> data, Vector<Vector<Integer>> q) {
		Vector<Integer> indices = new Vector<Integer>();
		int index = 0;
		for (Vector<Integer> v : data) {
			int nearest = EuclidDistance(v, q.get(0), 0);
			for (int i = 0; i < v.size(); i++) {
				int temp = EuclidDistance(v, q.get(i), 0);
				if (temp < nearest) {
					nearest = temp;
					index = i;
				}
			}
			indices.add(index);
		}

		return indices;
	}

	static Formatter x;

	public static void openning() {
		try {
			x = new Formatter("compression.txt");
		} catch (Exception e) {

		}

	}

	public static void writting(String in) {
		x.format("%s", in);
		x.format("%n");

	}

	public static void closing() {
		x.close();
	}

	public static void Compression(int h, int w, int cbs, String path) throws IOException {
		Vector<Vector<Integer>> Vectors = new Vector<>();
		int[][] image = ImageRW.readImage(path);

		for (int i = 0; i < 400; i += h) {
			for (int j = 0; j < 400; j += w) {
				Vectors.add(new Vector<>());
				for (int x = i; x < i + h; x++) {
					for (int y = j; y < j + w; y++) {
						Vectors.lastElement().add(image[x][y]);
					}
				}
			}
		}

		Vector<Vector<Integer>> Quantized = new Vector<>();

		quantization(cbs, Vectors, Quantized);

		Vector<Integer> Indices = getIndices(Vectors, Quantized);

		openning();
		FileWriter ff = new FileWriter("indices.txt");

		for (Vector<Integer> vec : Quantized)

		{
			String input = "";

			for (int i = 0; i < vec.size(); i++) {
				input += vec.get(i) + " ";
			}
			writting(input);
		}
		closing();
		String in = "";
		for (int i = 0; i < Indices.size(); i++) {
			in += Indices.get(i) + " ";
		}
		ff.write(String.valueOf(in));

	}

	static Scanner y;

	public static void opened() {
		try {
			y = new Scanner(new File("compression.txt"));
		} catch (Exception e) {
			System.out.println("can't open file");
		}
	}

	public static void closed() {
		y.close();
	}

	public static void Decompression(int h, int w, int cbs) throws IOException, ClassNotFoundException {
		Vector<Vector<Integer>> Quantized = new Vector<Vector<Integer>>();
		Vector<Integer> Indices = new Vector<Integer>();
		opened();
		for (int i = 0; i < cbs; i++) {

			String level = y.nextLine();
			String[] arr = level.split(" ");
			// System.out.println(arr[0]);
			// Integer.parseInt(arr[0]);
			Vector<Integer> adding = new Vector<Integer>();

			for (int j = 0; j < arr.length; j++) {
				// System.out.println(arr.length);
				int add = Integer.valueOf(arr[j]);
				// System.out.println(add);
				adding.add(add);
			}
			// System.out.println(adding);
			Quantized.addElement(adding);
			// Quantized.add(adding);
			// System.out.println(Quantized);

		}

		for (Vector<Integer> l : Quantized) {
			// System.out.println(l.size());
			// System.out.println(l);

		}
		closed();
		File file = new File("indices.txt");

		BufferedReader br = new BufferedReader(new FileReader(file));
		String input = "";
		input += br.readLine();
		br.close();
		// System.out.println(input);
		String[] splt = input.split(" ");
		// System.out.println(splt[0]);
		String decompcode = "";
		for (int i = 0; i < splt.length; i++) {
			// System.out.println(splt.length);
			// System.out.println(splt[i]);
			int add = Integer.parseInt(splt[i]);
			Indices.add(add);

		}

		int reImg[][] = new int[400][400];

		for (int i = 0; i < Indices.size(); i++) {
			int x = i/ (400 / w);
			int y = i % (400 / w);
			x *= h;
			y *= w;
			int v = 0;

			for (int j = x; j < x + h; j++) {
				for (int k = y; k < y + w; k++) {
					reImg[j][k] = Quantized.get(Indices.get(i)).get(v++);
				}
			}
		}
	ImageRW.writeImage(reImg, 400, 400, "E:\\Decompressed.jpg");

	}

	public static void main(String[] args) throws IOException, ClassNotFoundException {

		//Compression(2, 2, 8, "E:\\coin.jpg");
		//Decompression(2, 2, 8);

	}
}