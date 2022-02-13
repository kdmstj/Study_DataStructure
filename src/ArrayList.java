import Interface_form.List;

import java.util.Arrays;

public class ArrayList<E> implements List<E> {

    private static final int DEFAULT_CAPACITY = 10; //최소 용적 크기
    private static final Object[] EMPTY_ARRAY = {};  //빈배열

    private int size; // 요소 개수

    Object[] array; //요소를 담을 배열

    //생성자1(초기 공간 할당하지 않음)
    public ArrayList(){
        this.array = EMPTY_ARRAY;
        this.size = 0;
    }

    //생성자2 (초기 공간 할당함.)
    public ArrayList(int capacity){
        this.array = new Object[capacity];
        this.size = 0;
    }

    //용적은 외부에서 접근하면 데이터의 손상을 야기할 수 있으므로 private으로 접근을 제한함.
    private void resize(){
        int array_capacity = array.length;

        //capacity = 0일 경우,
        if(Arrays.equals(array,EMPTY_ARRAY)){
            array = new Object[DEFAULT_CAPACITY];
            return;
        };

        //용량이 꽉 찰 경우,
        if(size == array_capacity){
            int new_capacity = array_capacity *2;

            array = Arrays.copyOf(array,new_capacity);
            return;
        }
        //용적의 절반 미만으로 요소가 차지하고 있을 경우
        if(size < (array_capacity/2)){
            int new_capacity = array_capacity / 2;

            array = Arrays.copyOf(array, Math.max(new_capacity, DEFAULT_CAPACITY));
            return;
        }
    }

    @Override
    public boolean add(E value) {
        addLast(value);
        return true;
    }

    public void addLast(E value){
        //꽉 차있는 상태라면, 용적을 재할당한다.
        if(size == array.length){
            resize();
        }
        array[size] = value; //마지막 위치에 요소를 추가한다.
        size++;
    }

    @Override
    public void add(int index, E value) {
        if(index > size || index < 0){ //영역을 벗어난 경우
            throw new IndexOutOfBoundsException();
        }
        if(index == size){//index가 마지막 위치라면, addLast메소드로 요소를 추가한다.
            addLast(value);
        }
        else{
            if(size == array.length){//꽉 차 있다면, 용적 재할당
                resize();
            }
            for(int i = size; i > index; i--){//index기준 후자에 있는 모든 요소들을 할칸씩 뒤로 밀기
                array[i] = array[i-1];
            }

            array[index] = value; //index위치에 요소 할당
            size++;
        }

    }
    public void addFirst(E value){//앞서 만들었던 중간 삽입에서 index를 0으로 보내면 된다.
        add(0,value);
    }

    @Override
    @SuppressWarnings("unchecked")
    public E remove(int index) {
        if(index >= size || index < 0){
            throw new IndexOutOfBoundsException();
        }

        E element = (E)array[index]; //삭제될 요소를 반환하기 위해서 임시로 담아둔다.
        array[index] = null; //index에 해당하는 배열값을 null값으로 삭제한다.

        for(int i = index ; i < size ; i++){
            array[i] = array[i+1];
            array[i+1] = null;
        }
        size--;
        resize();
        return element;
        //원본 데이터 타입으로 반환하기 위해 E타입으로 캐스팅해준다.

    }

    @Override
    public boolean remove(Object value) {
        if(indexOf(value)>0){
            int index = indexOf(value);
            remove(index);
            return true;
        }else{
            return false;
        }




    }


    @SuppressWarnings("unchecked")
    @Override
    public E get(int index) {
        if(index >= size || index < 0) {//범위 벗어나면 예외가 발생한다.
            throw new IndexOutOfBoundsException();
        }
        //Object 타입에서 E타입으로 캐스팅 후 반환
        return (E)array[index];
    }

    @Override
    public void set(int index, E value) {
        if (index >= size || index < 0){
            throw new IndexOutOfBoundsException();
        }else{
            array[index] = value;
        }
    }

    @Override
    public boolean contains(Object value) {

        if(indexOf(value) >= 0){
            return true;
        }else{
            return false;
        }
    }

    @Override
    public int indexOf(Object value) {
        //사용자가 찾고자하는 요소(value)의 위치(index)를 반환하는 메소드
        for(int i = 0 ; i < array.length; i++){
            if(array[i].equals(value)){
                return i;
            }
        }

        return -1;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0; //요소가 0개일 경우, 비어있다는 의미이므로 true반환
    }

    @Override
    public void clear() {
        for(int i = 0 ; i < size ; i++){
            array[i] = null;
        }
        size = 0;
        resize();

    }
}
