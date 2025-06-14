package stack;

public class StackSewa {
    private NodeSewa top;
    private int size;

    public StackSewa() {
        this.top = null;
        this.size = 0;
    }

    public void push(int idPenyewaan) {
        NodeSewa newNode = new NodeSewa(idPenyewaan);
        newNode.next = top;
        top = newNode;
        size++;
    }

    public Integer pop() {
        if (top == null) return null;
        int id = top.idPenyewaan;
        top = top.next;
        size--;
        return id;
    }

    public Integer peek() {
        return (top != null) ? top.idPenyewaan : null;
    }

    public boolean isEmpty() {
        return top == null;
    }

    public int getSize() {
        return size;
    }
}
