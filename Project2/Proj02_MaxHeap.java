import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

public class Proj02_MaxHeap {

	private Comparable[] data;
	private int n; // number of elements being used in the array
	private boolean debug;
	private int dotNum; // number to keep track of how many .dot files have been created

	/// NOTE: Dot files are only created AFTER operations (build, insert, remove)
	/// generating dot files during bubbleUp and bubbleDown got overwhelming

	public Proj02_MaxHeap(boolean debug, Comparable[] data) {
		// Constructor which takes an array of data and builds a MaxHeap
		this.data = data;
		this.n = data.length;
		this.debug = debug;
		this.dotNum = 0;
		buildMaxHeap();
		if (debug) {
			generateDotFile();
		}
	}

	public Proj02_MaxHeap(boolean debug) {
		// Default constructor
		this.data = new Comparable[4];
		this.n = 0;
		this.debug = debug;
		this.dotNum = 0;
	}

	private void buildMaxHeap() {
		int i = parentOf(n - 1); // this is the index of the first parent node
		while (i >= 0) {
			bubbleDown(i);
			i--;
		}
	}

	public void insert(Comparable newVal) {
		// grow the array first if this insert will make our data too big
		if (n + 1 > data.length) {
			growArray();
		}
		// add data at the end of the array (last leaf node) and bubble it up into place
		data[n] = newVal;
		bubbleUp(n);
		n++;
		if (debug) {
			generateDotFile();
		}
	}

	private void growArray() {
		// Allocate a new array of twice the original size and populate it
		Comparable[] newData = new Comparable[data.length * 2];
		int i = 0;
		for (Comparable element : data) {
			newData[i] = element;
			i++;
		}
		data = newData;
	}

	public Comparable removeMax() {
		// Swap max down to last leaf
		Comparable max = data[0];
		swap(0, n - 1);
		n--;
		// bubble new root down (kick it root down)
		bubbleDown(0);
		if (debug) {
			generateDotFile();
		}
		return max;
	}

	private void bubbleUp(int index) {
		// Swap element up until it's no longer greater than its parent, or until its
		// the root
		while (data[parentOf(index)].compareTo(data[index]) < 0) {
			swap(parentOf(index), index);
			index = parentOf(index);
		}
	}

	private void bubbleDown(int index) {
		// Swap element with its largest child until it is no longer larger than either
		// child
		int childL = (index * 2) + 1;
		int childR = childL + 1;

		while (childL < n) {
			if (childR >= n) {
				// if there's only a left child, swap with it if it's bigger
				if (data[childL].compareTo(data[index]) > 0) {
					swap(index, childL);
				}
				index = childL;
			} else if (data[childL].compareTo(data[childR]) > 0) {
				// if both children exist and the left is bigger than the right, swap left if
				// it's bigger
				if (data[childL].compareTo(data[index]) > 0) {
					swap(index, childL);
				}
				index = childL;
			} else {
				// else swap right if it's bigger
				if (data[childR].compareTo(data[index]) > 0) {
					swap(index, childR);
				}
				index = childR;
			}
			childL = (index * 2) + 1;
			childR = childL + 1;
		}
	}

	private int parentOf(int index) {
		// Given the index of a "node" in the array, return the index of its parent
		return (index - 1) / 2;
	}

	private void swap(int i1, int i2) {
		// Simple helper function to swap the elements at two given indices
		Comparable temp = data[i1];
		data[i1] = data[i2];
		data[i2] = temp;
	}

	private void generateDotFile() {
		// This code generates a .dot file representing the current state of the heap.
		// Nodes are represented as their indices, with their content value displayed as
		// a label.
		// This is to keep wonky things from happening with duplicate values
		String fileName = "dotFile" + dotNum + ".dot";
		File dotFile = new File(fileName);
		PrintWriter out;
		try {
			out = new PrintWriter(dotFile);
			out.println("digraph");
			out.println("{");
			for (int i = 0; i < n; i++) {
				out.println("\tindex_" + i + " [label =\"" + data[i] + "\"];");
			}
			int i = 0;
			while (i < n) {
				if ((i * 2 + 1) < n) {
					out.println("\tindex_" + i + " -> " + "index_" + ((i * 2) + 1) + ";");
				}
				if ((i * 2 + 2) < n) {
					out.println("\tindex_" + i + " -> " + "index_" + ((i * 2) + 2) + ";");
				}
				i++;
			}

			out.println("}");
			out.close();
			dotNum++;
		} catch (FileNotFoundException e) {
			System.out.println("could not create " + fileName);
		}

	}

	public void dump(PrintWriter out) {
		// Write current heap to output
		if (n == 0) {
			out.printf("\n");
		} else {
			for (int i = 0; i < n - 1; i++) {
				out.printf("%d ", data[i]);
			}
			out.printf("%d\n", data[n - 1]);
		}

	}

}
