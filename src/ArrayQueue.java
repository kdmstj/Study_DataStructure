import Interface_form.Queue;

public class ArrayQueue<E> implements Queue<E> {

    private static final int DEFAULT_CAPACITY = 64; //최소(기본) 용적 크기

    private Object[] array; //요소를 담을 배열
    private int size; //요소 개수

    private int front; //시작 인덱스를 가리키는 변수(빈공간 임을 유의해야함.)
    private int rear; //마지막 요소의 인덱스를 가리키는 변수

    //생성자1 (초기 용적 할당을 안할 경우)
    //ArrayQueue<Integer> q = new ArrayQueue<>();
    public ArrayQueue(){
        this.array = new Object[DEFAULT_CAPACITY];
        this.size = 0;
        this.front = 0;
        this.rear = 0;
    }

    //생성자2 (초기 용적 할당을 할 경우)
    //ArrayQueue<Integer> q = new ArrayQueue<>(100);
    public ArrayQueue(int capacity){
        this.array = new Object[capacity];
        this.size = 0;
        this.front = 0;
        this.rear = 0;
    }

    private void resize(int newCapacity){
        int arrayCapacity = array.length;
        Object[] newArray = new Object[newCapacity]; //용적을 변경한 배열

        /*
        i = new array index
        j = original array
        index 요소 개수(size)만큼 새 배열에 값 복사
         */
        for(int i = 1, j = front+1; i <= size; i++, j++){
            newArray[i] = array[j % arrayCapacity];
        }
        this.array = null;
        this.array = newArray; //새 배열을 기존 array의 배열로 덮어 씌움
        front = 0;
        rear = size;
    }


    @Override
    public boolean offer(E e) {
        //용적이 찼을 경우
        if((rear +1) % array.length == front ){
            resize(array.length *2);
        }

        rear = (rear+1) % array.length; //rear을 rear의 다음 위치로 갱신한다.
        array[rear] = e;
        size++;

        return true;
    }

    @Override
    public E poll() {
        //front+1위치에 있는 요소를 삭제
        if(front == rear){
            //삭제할 요소가 없는 경우
            return null;
        }
        front = front+1;

        @SuppressWarnings("unchecked")
        E item = (E)array[front]; //반환할 데이터를 임시로 저장한다.
        array[front] = null; //해당 front의 데이터를 삭제한다.
        size--;

        if(array.length > DEFAULT_CAPACITY && size < (array.length /4)){

            //아무리 작아도 최소 용적 미만으로 줄이지는 않도록 한다.
            resize(Math.max(DEFAULT_CAPACITY, array.length/2));
        }


        return item;
    }

    @Override
    public E peek() {
        return null;
    }
}
