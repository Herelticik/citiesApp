package client;

import lib.Answer;
import lib.Request;
import lib.UserData;
import lib.element.City;
import lib.element.CityBuilder;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.util.stream.IntStream;

public class InteractionClient {
    DatagramChannel channel;
    UserData userData;
    InetSocketAddress address;
    private static final int BUFFER_SIZE = 1024;

    public InteractionClient(String address, int port) {
        try {
            channel = DatagramChannel.open();
            this.address = new InetSocketAddress(address, port);
            channel.connect(this.address);
            channel.configureBlocking(false);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setUserData(UserData userData) {
        this.userData = userData;
    }

    public void sendRequest(Request request) throws IOException {
        setSender(request);
        byte[] serializedRequest = getSerializedRequest(request);
        ByteBuffer message = ByteBuffer.wrap(serializedRequest);
        channel.write(message);
    }

    private void setSender(Request request) {
        if (request.getUserData() == null) {
            request.setUserData(userData);
        }
        try {
            request.getCommand().setSender(userData.getLogin());
            request.getCommand().setElement(
                    new CityBuilder(request.getCommand().getElement())
                            .creator(userData.getLogin())
                            .build()
            );
        } catch (NullPointerException ignored) {
        }
    }

    private byte[] getSerializedRequest(Request request) throws IOException {
        ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
        ObjectOutputStream objectStream = new ObjectOutputStream(byteStream);
        objectStream.writeObject(request);
        return byteStream.toByteArray();
    }

    public Answer getAnswer() throws IOException, ClassNotFoundException {
        Answer answer = null;
        try {
            byte[] serializedAnswer = getSerializedAnswer();
            answer = unpackAnswer(serializedAnswer);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return answer;
    }

    private byte[] getSerializedAnswer() throws IOException {
        ByteArrayOutputStream objectCollector = new ByteArrayOutputStream();
        ByteBuffer transportBuffer = ByteBuffer.allocate(BUFFER_SIZE);
        messageWaiting(transportBuffer);
        while (true) {
            if (isStopSymbol(transportBuffer)) {
                break;
            } else {
                objectCollector.write(transportBuffer.array());
                updateBufferData(transportBuffer);
            }
        }
        return objectCollector.toByteArray();
    }

    private Answer unpackAnswer(byte[] serializedAnswer) throws IOException, ClassNotFoundException {
        ObjectInputStream input = new ObjectInputStream(new ByteArrayInputStream(serializedAnswer));
        return (Answer) input.readObject();
    }

    private void messageWaiting(ByteBuffer buffer) throws IOException {
        while (true) {
            SocketAddress receiveFlag = channel.receive(buffer);
            if (receiveFlag != null) {
                break;
            }
        }
    }

    private boolean isStopSymbol(ByteBuffer inputValues) {
        return IntStream
                .range(0, inputValues.array().length)
                .map((x) -> inputValues.array()[x])
                .limit(10)
                .filter(x -> x == 0)
                .count() == 10;
    }

    private void updateBufferData(ByteBuffer buffer) throws IOException {
        buffer.clear();
        channel.receive(buffer);
    }
}
