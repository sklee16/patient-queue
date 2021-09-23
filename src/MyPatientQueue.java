import java.sql.SQLOutput;
import java.util.jar.JarOutputStream;

/**
 * A Patient queue implementation using a dynamically-sized circular array.
 * 
 * @author Sue Lee February 3, 2020
 */
public class MyPatientQueue{
	// instance variables
	// TODO ATTENTION: CODE NEEDED HERE
	// declare instance variables
	Patient[] patientQueue;
	int head;
	int tail;
	int size;
	// -----

	// constructor
	public MyPatientQueue() {
		// TODO ATTENTION: CODE NEEDED HERE
		// initialize instance variables
		int arrayLength = 7;
		patientQueue = new Patient[7];
		head = 0;
		tail = 0;

		// -----
	}

	// functions
	/**
	 * @return the number of patients in the queue
	 */
	public int size() {
		// TODO ATTENTION: CODE NEEDED HERE
		// return the number of patients in the queue

		return this.size;
	}

	/**
	 * add patient to end of queue.
	 * @param p - Patient to add to queue
	 */
	public void enqueue(Patient p) {
		// TODO ATTENTION: CODE NEEDED HERE
		// add patient to end of queue
		patientQueue[this.tail] = p;
		this.tail = (this.tail + 1) % patientQueue.length;
		this.size++;

		// resize array, if needed
		if (this.size == patientQueue.length) {
			Patient[] newPatientQueue = new Patient[patientQueue.length * 2];
			for (int i = 0; i < this.size; i++) {
				newPatientQueue[i] = patientQueue[this.head];
				this.head = (this.head + 1) % patientQueue.length;
			}
			this.head = 0;
			this.tail = size;
			patientQueue = newPatientQueue;
		}
	}

	/**
	 * remove and return next patient from the queue
	 * @return patient at front of queue, null if queue is empty
	 */
	public Patient dequeue() {
		// TODO ATTENTION: CODE NEEDED HERE
		// remove and return the patient at the head of the queue

		Patient dequePatient = patientQueue[head];

		if (patientQueue[head] == null) {
			return null;
		} else {
			patientQueue[head] = null;
			head = (head + 1) % patientQueue.length;
			size--;

			// resize array, if needed
			if (size <= (1 / 4) * patientQueue.length) {
				Patient[] newPatientQueue = new Patient[patientQueue.length / 2];
				for (int i = head; i < size; i++) {
					newPatientQueue[i] = patientQueue[head];
					head = (head + 1) % patientQueue.length;
				}
				head = 0;
				tail = size;
				patientQueue = newPatientQueue;
			}
			return dequePatient;
		}
	}

	/**
	 * return, but do not remove, the patient at index i
	 * @param i - index of patient to return
	 * @return patient at index i, or null if no such element
	 */
	public Patient get(int i) {
		// TODO ATTENTION: CODE NEEDED HERE
		// return, but do not remove, the patient at index i

		int index;
		if (i >= 0 && i <= size) {
			index = (head + i) % patientQueue.length;
		} else {
			return null;
		}

		if (patientQueue[index] == null) {
			return null;
		} else {
			return patientQueue[index];
		}
		// -----
	}

	/**
	 * add patient to front of queue
	 * @param p - patient being added to queue
	 */
	public void push(Patient p) {
		// TODO ATTENTION: CODE NEEDED HERE
		// add Patient p to front of queue

		//O(1)
		for (int i = size; i > 0; i--) {
			patientQueue[(head + i) % patientQueue.length] = patientQueue[(head + i - 1 ) % patientQueue.length];
		}

		patientQueue[head] = p;
		size++;
		tail = (tail + 1) % patientQueue.length;

		// resize array, if needed
		if (size == patientQueue.length) {
			Patient[] newPatientQueue = new Patient[patientQueue.length * 2];
			for (int i = 0; i < size; i++) {
				newPatientQueue[i] = patientQueue[head];
				head = (head + 1) % patientQueue.length;
			}
			head = 0;
			tail = size;
			patientQueue = newPatientQueue;
		}
		// -----
	}

	/**
	 * remove and return patient at index i from queue
	 * @param i - index of patient to remove
	 * @return patient at index i, null if no such element
	 */
	public Patient dequeue(int i) {
		// TODO ATTENTION: CODE NEEDED HERE
		// remove and return Patient at index i from queue

		int index;
		if (i < 0 || i >= size) { //index > size need to be checked?
			return null;
		} else {
			index = (head + i) % patientQueue.length;

			Patient patient = patientQueue[index];
			patientQueue[index] = null;

			// shift patients down to fill hole left by removed patient
			//determine the number of patients to be shifted
			int move = 0;
			if (head > tail) {
				move = tail + patientQueue.length - index;
			} else {
				move = tail - index;
			}

			for (int j = 0; j < move; j++) {
				if (j == move - 1) {
					patientQueue[(index + 1 + j) % patientQueue.length] = null;
				} else {
					patientQueue[(index + j) % patientQueue.length] = patientQueue[(index + 1 + j) % patientQueue.length];
				}
			}
			tail = (move + index - 1) % patientQueue.length;

			// resize array, if needed
			if (size <= (1 / 4) * patientQueue.length) {
				Patient[] newPatientQueue = new Patient[patientQueue.length / 2];
				for (int j = 0; j < size; j++) {
					newPatientQueue[j] = patientQueue[head];
					head = (head + 1) % patientQueue.length;
				}
				head = 0;
				tail = size;
				patientQueue = newPatientQueue;
			}
			size--;
			return patient;
		}
	}
}
