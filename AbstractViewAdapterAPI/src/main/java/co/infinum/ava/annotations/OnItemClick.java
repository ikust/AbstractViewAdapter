package co.infinum.ava.annotations;

/**
 * Created by ivan on 02/02/14.
 */
public @interface OnItemClick {

    /**
     * Resource ID of the ListView to inject the click listener into.
     *
     * @return
     */
    int value();
}
