package org.example.io;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

// todo 生产环境需要提供最大内存限制设置
// todo 生产环境需要考虑并发一致性
public class SplittableInputStream extends InputStream {
    @Override
    public int read() throws IOException {
        return multiSource.read(myId);
    }

    /**
     * Almost an input stream: The read-method takes an id.
     */
    static class MultiplexedSource {
        static int MIN_BUF = 4096;

        // Underlying source
        private InputStream source;

        // Read positions of each SplittableInputStream
        private List<Integer> readPositions = new ArrayList<>();

        // Data to be read by the SplittableInputStreams
        int[] buffer = new int[MIN_BUF];

        // Last valid position in buffer
        int writePosition = 0;

        public MultiplexedSource(InputStream source) {
            this.source = source;
        }

        // Add a multiplexed reader. Return new reader id.
        int addSource(int splitId) {
            readPositions.add(splitId == -1 ? 0 : readPositions.get(splitId));
            return readPositions.size() - 1;
        }

        // Make room for more data (and drop data that has been read by
        // all readers)
        private void readjustBuffer() {
            int from = Collections.min(readPositions);
            int to = Collections.max(readPositions);
            int activeDataLength = to - from;
            boolean move = false;
            if (writePosition >= buffer.length) {
                if (from > buffer.length / 4 && activeDataLength < buffer.length) {
                    // 平移
                    System.arraycopy(buffer, from, buffer, 0, activeDataLength);
                    move = true;
                } else {
                    // 扩容
                    int newLength = Math.max(activeDataLength * 2, MIN_BUF);
                    int[] newBuf = new int[newLength];
                    System.arraycopy(buffer, from, newBuf, 0, activeDataLength);
                    buffer = newBuf;
                    move = true;
                }
            } else if (buffer.length > MIN_BUF && activeDataLength < buffer.length / 4) {
                // 缩容
                int newLength = Math.max(activeDataLength * 2, MIN_BUF);
                int[] newBuf = new int[newLength];
                System.arraycopy(buffer, from, newBuf, 0, activeDataLength);
                buffer = newBuf;
                move = true;
            }
            if (move) {
                for (int i = 0; i < readPositions.size(); i++) {
                    readPositions.set(i, readPositions.get(i) - from);
                }
                writePosition -= from;
            }
        }

        // Read and advance position for given reader
        public int read(int readerId) throws IOException {
            // read data
            if (readPositions.get(readerId) >= writePosition) {
                readjustBuffer();
                buffer[writePosition++] = source.read();
            }
            // use buffer data
            int pos = readPositions.get(readerId);
            int b = buffer[pos];
            if (b != -1)
                readPositions.set(readerId, pos + 1);
            return b;
        }
    }

    // Non-root fields
    MultiplexedSource multiSource;
    int myId;

    // Public constructor: Used for first SplittableInputStream
    public SplittableInputStream(InputStream source) {
        multiSource = new MultiplexedSource(source);
        myId = multiSource.addSource(-1);
    }

    // Private constructor: Used in split()
    private SplittableInputStream(MultiplexedSource multiSource, int splitId) {
        this.multiSource = multiSource;
        myId = multiSource.addSource(splitId);
    }

    // Returns a new InputStream that will read bytes from this position
    // onwards.
    public SplittableInputStream split() {
        return new SplittableInputStream(multiSource, myId);
    }
}
