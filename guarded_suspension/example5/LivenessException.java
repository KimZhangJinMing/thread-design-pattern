package guarded_suspension.example5;



public class LivenessException extends RuntimeException{

    public LivenessException(String msg) {
        super(msg);
    }
}
