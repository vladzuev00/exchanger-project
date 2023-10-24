public final class ExchangedObjectFactory {
    private long nextId;

    public ExchangedObject create() {
        return new ExchangedObject(this.nextId++);
    }
}
