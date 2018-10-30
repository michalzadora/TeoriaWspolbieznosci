public class Buffer {

    private BufferCell[] cells;
    private int bufferSize;

    public Buffer(int bufferSize) {
        this.bufferSize = bufferSize;
        this.cells = new BufferCell[bufferSize];
        for (int i = 0; i < bufferSize; i++)
            this.cells[i] = new BufferCell();

    }


    int getResource(int resourceID, int ID) throws InterruptedException {
        return this.cells[resourceID].getResource(ID);
    }

    void setResource(int resourceID, int value) {
        this.cells[resourceID].setResource(value);
    }

    void releaseResource(int resourceID, int ID) {
        this.cells[resourceID].releaseResource(ID);
    }

}
