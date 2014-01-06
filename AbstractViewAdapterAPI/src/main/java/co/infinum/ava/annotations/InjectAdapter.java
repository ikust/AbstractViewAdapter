package co.infinum.ava.annotations;

/**
 * Created by ivan on 06/01/14.
 */
public @interface InjectAdapter {

    /**
     * Type of the Objects that will be "injected" into adapter.
     *
     * @return
     */
    Class<?> type();
}
