package com.keycollect.utils;

public class ByteArray {
    private byte[] array;
    private int position;
    public final int length;
    public ByteArray(int capacity) {
        this(new byte[capacity]);
    }
    public ByteArray(byte[] array) {
        this.array = array;
        length = array.length;
    }
    public int position() {
        return position;
    }
    public void position(int position) {
        this.position = position;
    }
    public byte[] array() {
        return array;
    }
    public byte readByte() {
        byte value = array[position];
        position++;
        return value;
    }
    public int readUnsignedByte() {
        return Byte.toUnsignedInt(readByte());
    }
    public short readShort() {
        return (short)((readByte() << 8) | readByte());
    }
    public int readUnsignedShort() {
        return Short.toUnsignedInt(readShort());
    }
    public int readInt() {
        return (readShort() << 16) | readShort();
    }
    public long readLong() {
        return ((long)readInt() << 32) | readInt();
    }
    public float readFloat() {
        return Float.intBitsToFloat(readInt());
    }
    public double readDouble() {
        return Double.longBitsToDouble(readLong());
    }
    public boolean readBoolean() {
        return readByte() != 0;
    }
    public char readChar() {
        return (char)readUnsignedByte();
    }
    public String readString(int length) {
        String string = "";
        for (int i = 0; i < length; i++) {
            string += readChar();
        }
        return string;
    }
    public byte[] readArray(int length) {
        byte[] array = new byte[length];
        for (int i = 0; i < length; i++) {
            array[i] = readByte();
        }
        return array;
    }
    public <T extends Enum<T>> T readEnum(Class<T> enumType) {
        return enumType.getEnumConstants()[readUnsignedByte()];
    }
    public void writeByte(byte value) {
        array[position] = value;
        position++;
    }
    public void writeByte(int value) {
        writeByte((byte)value);
    }
    public void writeShort(short value) {
        writeByte(value >> 8);
        writeByte(value);
    }
    public void writeShort(int value) {
        writeShort((short)value);
    }
    public void writeInt(int value) {
        writeShort(value >> 16);
        writeShort(value);
    }
    public void writeLong(long value) {
        writeInt((int)(value >> 32));
        writeShort((int)value);
    }
    public void writeFloat(float value) {
        writeInt(Float.floatToIntBits(value));
    }
    public void writeDouble(double value) {
        writeLong(Double.doubleToLongBits(value));
    }
    public void writeBoolean(boolean value) {
        writeByte(value ? 1 : 0);
    }
    public void writeChar(char value) {
        writeByte((byte)value);
    }
    public void writeString(String value) {
        for (char character : value.toCharArray()) {
            writeChar(character);
        }
    }
    public void writeArray(byte[] array) {
        for (byte value : array) {
            writeByte(value);
        }
    }
    public void writeEnum(Enum<?> value) {
        writeByte(value.ordinal());
    }
}