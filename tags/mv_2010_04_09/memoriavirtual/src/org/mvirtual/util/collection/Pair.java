package org.mvirtual.util.collection;

public class Pair<T1, T2>
{
	private T1 first;
	private T2 second;

	public Pair()
	{
		first = null;
		second = null;
	}

	public Pair(T1 a, T2 b)
	{
		first = a;
		second = b;
	}

	public void setFirst(T1 a) {
		first = a;
	}

	public void setSecond(T2 b) {
		second = b;
	}

	public T1 getFirst() {
		return first;
	}

	public T2 getSecond() {
		return second;
	}
}
