package single_threaded_execution.example3;

public class UserThread extends Thread {

    private String name;
    private String birthPlace;
    private Gate gate;

    public UserThread(Gate gate, String name, String birthPlace) {
        this.gate = gate;
        this.name = name;
        this.birthPlace = birthPlace;
    }

    @Override
    public void run() {
        System.out.println(this.name + "开始通过Gate...");
        while(true) {
            gate.pass(this.name,this.birthPlace);
        }
    }
}
