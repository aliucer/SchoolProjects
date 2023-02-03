import java.util.HashSet;
import java.util.NoSuchElementException;

public class FactoryImpl implements Factory{
	
	private Holder first;
	private Holder last;
	private Integer size;
	
	StringBuilder str = new StringBuilder();
	
	public FactoryImpl(Holder first, Holder last, Integer size) {
		super();
		this.first = first;
		this.last = last;
		this.size = size;
		
	}

	@Override
	public void addFirst(Product product) {
		Holder holder = new Holder(null, product, first);
		if(size==0) {
			first=holder;
			last=holder;
		}
		else {
			first.setPreviousHolder(holder);
			setFirst(holder);
		}
		size++;
	}

	@Override
	public void addLast(Product product) {
		if(size==0) {
			addFirst(product);
			return;
		}
		Holder holder = new Holder(last, product, null);
		last.setNextHolder(holder);
		setLast(holder);
		size++;
	}

	@Override
	public Product removeFirst() throws NoSuchElementException {
		if(size==0) throw new NoSuchElementException();
		if(size==1) { 
			Holder first = getFirst();
			setFirst(null);
			last=null;
			size=0;
			return first.getProduct();
		}
		else {
			Holder first = getFirst();
			Holder second = getFirst().getNextHolder();
			getFirst().setNextHolder(null);
			second.setPreviousHolder(null);
			setFirst(second);
			size--;

			// TODO Auto-generated method stub
			return first.getProduct();
		}
	}

	@Override
	public Product removeLast() throws NoSuchElementException {
		if(size==0) throw new NoSuchElementException();
		if(size<=1) {
			return removeFirst();
		}
		Holder temp = getLast();
		Holder beforeLast = getLast().getPreviousHolder();
		getLast().setPreviousHolder(null);
		beforeLast.setNextHolder(null);
		setLast(beforeLast);
		size--;
		// TODO Auto-generated method stub
		return temp.getProduct();
	}

	@Override
	public Product find(int id) throws NoSuchElementException {
		Holder temp = first;
		for(int i=0; i<size; i++) {
			if(temp.getProduct().getId()==id) {
				return temp.getProduct();
			}
			if(temp.getNextHolder()!=null) {
				temp = temp.getNextHolder();
			}
			else {
				throw new NoSuchElementException();
			}
		}
		throw new NoSuchElementException();
	}

	@Override
	public Product update(int id, Integer value) throws NoSuchElementException {
		Product product = find(id);
		Product copy = null;
		if(product!=null) {
			copy = new Product(product.getId(), product.getValue());
			product.setValue(value);
		}
		return copy;
	}

	@Override
	public Product get(int index) throws IndexOutOfBoundsException {
		if(index>=size||index<0) {
			throw new IndexOutOfBoundsException();
		}
		Holder holder = getFirst();
		for(int i=0; i<index;i++) {
			holder = holder.getNextHolder();
		}
		return holder.getProduct();
	}

	@Override
	public void add(int index, Product product) throws IndexOutOfBoundsException {
		if(index>size || index<0) {
			throw new IndexOutOfBoundsException();
		}
		if(index == 0) {
			addFirst(product);
		}
		else if(index == size) {
			addLast(product);
		}
		else {
			Holder holder = getFirst();
			for(int i=0; i<index;i++) {
				holder = holder.getNextHolder();
			}
			Holder prevHolder = holder.getPreviousHolder();
			Holder newHolder = new Holder(prevHolder, product, holder);
			prevHolder.setNextHolder(newHolder);
			holder.setPreviousHolder(newHolder);
			size++;	
		}	
	}

	@Override
	public Product removeIndex(int index) throws IndexOutOfBoundsException {
		if(index>=size||index<0) throw new IndexOutOfBoundsException();
		if(index == 0) {
			return removeFirst();
		}
		else if(index == size-1) {
			return removeLast();
		}
		else {
			Holder holder = getFirst();
			for(int i=0; i<index;i++) {
				holder = holder.getNextHolder();
			}
			Holder prevHolder = holder.getPreviousHolder();
			Holder nextHolder = holder.getNextHolder();
			prevHolder.setNextHolder(nextHolder);
			nextHolder.setPreviousHolder(prevHolder);
			size--;
			return holder.getProduct();
		}
	}

	@Override
	public Product removeProduct(int value) throws NoSuchElementException {
		Holder holder = first;
		if(size==1) {
			if(value==holder.getProduct().getValue()) {
				return removeFirst();
			}
			else {
				throw new NoSuchElementException();
			}
		}
		while(holder!=null && holder.getProduct().getValue()!=value ) {
			holder= holder.getNextHolder();
		}
		if(holder == null) {
			throw new NoSuchElementException();
		}
		else if(holder.getPreviousHolder()==null) {
			return removeFirst();
		}
		else if(holder.getNextHolder()==null) {
			return removeLast();
		}
		Holder prev = holder.getPreviousHolder();
		Holder next = holder.getNextHolder();
		
		prev.setNextHolder(next);
		next.setPreviousHolder(prev);
		holder.setNextHolder(null);
		holder.setPreviousHolder(null);
		size--;
		return holder.getProduct();
	}
	public int findIndex(int ID) {
		Holder hold = first;
		int index = 0;
		while(hold!=null) {
			if(ID==hold.getProduct().getId()) {
				return index;
			}
			hold = hold.getNextHolder();
			index++;
		}
		return -1;
	}

	@Override
	public int filterDuplicates() {
		if(size<=1) return 0;
		int count = 0;
		HashSet<Integer> set = new HashSet<>();
		Holder holder = first;
		while(holder!=null) {
			if(set.contains(holder.getProduct().getValue())) {
				Holder prev = holder.getPreviousHolder();
				Holder next = holder.getNextHolder();
				if(prev==null) {
					removeFirst();
					count++;
				}
				else if(next==null) {
					removeLast();
					count++;
				}
				else {
					prev.setNextHolder(next);
					next.setPreviousHolder(prev);
					holder.setNextHolder(null);
					holder.setPreviousHolder(null);
					size--;
					count++;
				}
				holder = next;
			}
			else {
				set.add(holder.getProduct().getValue());
				holder = holder.getNextHolder();
			}
		}
		return count;
	}

	@Override
	public void reverse() {
		if(size<=1) return;
		Holder current = first;
		Holder next = first.getNextHolder();
		Holder next2;
		if(size>1) {
			first.setNextHolder(null);
			while(next!=null) {
				next2 = next.getNextHolder();
				current.setPreviousHolder(next);
				next.setNextHolder(current);
				current = next;
				next = next2;
			}
			last.setPreviousHolder(null);
			Holder temp = first;
			first = last;
			last = temp;
		}
	}

	public Holder getFirst() {
		return first;
	}

	public void setFirst(Holder first) {
		this.first = first;
	}

	public Holder getLast() {
		return last;
	}

	public void setLast(Holder last) {
		this.last = last;
	}

	public Integer getSize() {
		return size;
	}

	public void setSize(Integer size) {
		this.size = size;
	}

	@Override
	public String toString() {
		StringBuilder str = new StringBuilder();
		str.append("{");
		Holder h = first;
		while(h!=null) {
			str.append(h + ",");
			h=h.getNextHolder();
		}
		if(size>0) {
			str.deleteCharAt(str.length()-1);
		}
		str.append("}");
		return String.valueOf(str);
	}
}