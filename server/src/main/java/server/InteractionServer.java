package server;

import lib.Request;
import lib.Answer;

import java.io.*;
import java.net.*;
import java.nio.ByteBuffer;

public class InteractionServer {
    private InetAddress address;
    private int port;
    private static final DatagramSocket socket;

    static {
        try {
            socket = new DatagramSocket(new InetSocketAddress(5000));
        } catch (SocketException e) {
            throw new RuntimeException(e);
        }
    }

    private static final int BUFFER_SIZE = 1024;

    public Request getRequest() {
        Request request = null;
        try {
            DatagramPacket packet = getDatagram();
            setReturnAddress(packet);
            request = unpackDatagram(packet);
        } catch (ClassNotFoundException | IOException e) {
            e.printStackTrace();
        }
        return request;
    }


    private DatagramPacket getDatagram() throws IOException {
        DatagramPacket packet = new DatagramPacket(new byte[BUFFER_SIZE], BUFFER_SIZE);
        socket.receive(packet);
        return packet;
    }

    private void setReturnAddress(DatagramPacket datagramPacket) {
        this.address = datagramPacket.getAddress();
        this.port = datagramPacket.getPort();
    }

    private Request unpackDatagram(DatagramPacket packet) throws IOException, ClassNotFoundException {
        ObjectInputStream input = new ObjectInputStream(new ByteArrayInputStream(packet.getData()));
        return (Request) input.readObject();
    }


    public void sendAnswer(Answer answer) {
        try {
            byte[] serializedAnswer = getSerializedAnswer(answer);
            sendCycle(serializedAnswer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private byte[] getSerializedAnswer(Answer answer) throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ObjectOutputStream output = new ObjectOutputStream(out);
        output.writeObject(answer);
        return out.toByteArray();
    }

    private void sendCycle(byte[] serializedAnswer) throws IOException {
        ByteBuffer buffer = ByteBuffer.wrap(serializedAnswer);
        while (buffer.hasRemaining()) {
            int bufferSize = Math.min(buffer.capacity() - buffer.position(), BUFFER_SIZE);
            byte[] transportArr = new byte[bufferSize];
            buffer.get(transportArr);
            sendADatagram(transportArr);
        }
        sendAStopSymbol();
    }

    private void sendAStopSymbol() throws IOException {
        sendADatagram(new byte[10]);
    }

    private void sendADatagram(byte[] data) throws IOException {
        DatagramPacket packet = new DatagramPacket(data, data.length, address, port);
        socket.send(packet);
    }


}
