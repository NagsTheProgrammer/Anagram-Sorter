// SinglyLinkedList is a singly linked list implementation for use in the main java class
// referenec: https://www.youtube.com/watch?v=uijdYLYAc1U

public class SinglyLinkedList {

    // Node is a private class implemented for use in the SinglyLinkedList class
    private static class Node{

        private String element;
        private Node nextNode;

        // Node constructor
        public Node(String e, Node nN){
            this.element = e;
            this.nextNode = nN;
        }

        public String getElement(){
            return this.element;
        }

        public Node getNextNode(){
            return this.nextNode;
        }

        public void setNextNode(Node n){
            this.nextNode = n;
        }
    }

    private Node head = null;
    private Node tail = null;
    private int size = 0;
    private Node currentNode = head;

    // SinglyLinkedList constructor
    public SinglyLinkedList(){};

    public int getSize(){
        return this.size;
    }

    public boolean isEmpty(){
        return this.size == 0;
    }

    public String getFirstElement(){
        if (isEmpty())
            return null;
        return head.getElement();
    }

    public String getLastElement(){
        if(isEmpty())
            return null;
        return tail.getElement();
    }

    public void addToFront(String e){
        this.head = new Node(e, head);
        if (isEmpty()) {
            this.tail = this.head;
            this.currentNode = head;
        }
        this.size++;
    }

    public void addToBack(String e){
        if (isEmpty()){
            this.tail = new Node(e, null);
            this.head = this.tail;
            this.currentNode = this.head;
        }
        else {
            this.tail.setNextNode(new Node(e, null));
            this.tail = this.tail.getNextNode();
        }
        this.size++;
    }

    public String getNextElement(){
        if (isEmpty())
            return null;
        return currentNode.getElement();
    }

    public Node getNextNode(){
        if (isEmpty())
            return null;
        return this.currentNode.getNextNode();
    }

    public Node getCurrentNode(){
        if (isEmpty())
            return null;
        return this.currentNode;
    }

    /*
    * iterateNode iterates through nodes in a SinglyLinkedList
     */
    public void iterateNode(){
        if (isEmpty())
            this.currentNode = null;
        else if (this.currentNode != tail)
            this.currentNode = this.currentNode.nextNode;
    }

    /*
    * resetCurrentNode resets the currentNode to the head of a SinglyLinkedList
     */
    public void resetCurrentNode(){
        this.currentNode = this.head;
    }
}
