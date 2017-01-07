
package runway;
public class ArrayQueue<Plane>{

	protected int frontIndex = 0;

	protected int endIndex = -1;

	protected int currentSize = 0;

	protected int maxSize;

	protected Plane array[ ];

	public ArrayQueue(int max) {
		maxSize = max;
		array = (Plane[ ]) new Object[maxSize];
	}

	
	public boolean empty() {
		boolean emptyArray = (currentSize == 0);
		return emptyArray;
	}

	boolean full() {
		boolean fullArray = (currentSize == maxSize);
		return fullArray;
	}

	public boolean offer(Plane element) {
	
		if (!this.full()) {
			endIndex = (endIndex + 1) % maxSize;
			array[endIndex] = element;
			currentSize++;
			return true;
		}
		return false;
	}

	public Plane poll() {
		Plane element = null;
	
		if (!this.empty()) {
		
			element = array[frontIndex];
			frontIndex = (frontIndex + 1) % maxSize;
			currentSize--;
		}
		return element;
	}

	public Plane peek() {
		Plane element = null;
		if (!this.empty()) {
			element = array[frontIndex];
		}
		return element;
	}

}
