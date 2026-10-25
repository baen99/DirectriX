package de.baen.directrix.util;

public abstract class AbstractDirectrixException extends RuntimeException {

    /*
        Java Constructors are NOT inherited! They have to be declared for each class or the default
        implicit is used. super calls the parent constructor but it's not passed on into this class.
        So I cannot call the RuntimeException constructor from subclasses of AbstractDirectrixException,
        only in the direct parent.
    */
    public AbstractDirectrixException(String message) {
        super(message);
    }

}
