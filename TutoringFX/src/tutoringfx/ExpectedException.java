package tutoringfx;

/**
 * ExpectedException
 * 
 * Used to customize the way exceptions are thrown in order to output them
 * using an alert window.
 * 
 * @author Jacob
 */ 
class ExpectedException extends Exception {
    ExpectedException(String message) {
        super(message);
    }
}
