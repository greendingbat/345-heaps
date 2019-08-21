
public class Proj02_HeapSort implements Proj01_Sort {

	private boolean debug;

	public Proj02_HeapSort(boolean debug) {
		this.debug = debug;
	}

	@Override
	public void sort(Comparable[] arr) {
		// Create a MaxHeap from the array, then remove the max value from the MaxHeap
		// and stick it on the end of the array, working inwards until we reach index 0,
		// at which point the array is sorted
		Proj02_MaxHeap heap = new Proj02_MaxHeap(debug, arr);
		int i = arr.length - 1;
		while (i > 0) {
			arr[i] = heap.removeMax();
			i--;
		}

	}

}
