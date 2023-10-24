import java.util.Queue;
import java.util.concurrent.Exchanger;

import static java.lang.System.out;
import static java.util.concurrent.TimeUnit.SECONDS;
import static java.util.stream.IntStream.range;

public final class ProducingTask extends ExchangingTask {
    private final ExchangedObjectFactory objectFactory;
    private final int producedObjectCount;

    public ProducingTask(final Exchanger<Queue<ExchangedObject>> exchanger,
                         final ExchangedObjectFactory objectFactory,
                         final int producedObjectCount) {
        super(exchanger);
        this.objectFactory = objectFactory;
        this.producedObjectCount = producedObjectCount;
    }

    @Override
    protected void handle(final Queue<ExchangedObject> objects) {
        range(0, this.producedObjectCount)
                .mapToObj(i -> this.produceObject())
                .peek(object -> out.printf("%s is being produced", object))
                .forEach(objects::add);
    }

    private ExchangedObject produceObject() {
        try {
            SECONDS.sleep(2);
            return this.objectFactory.create();
        } catch (final InterruptedException cause) {
            throw new RuntimeException(cause);
        }
    }
}
