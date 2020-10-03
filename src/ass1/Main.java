package ass1;

import java.util.*;

public class Main {

    public static void main(String[] args) {
        RotorMachine rotorMachine = new RotorMachine(1, 4);

        System.out.println("Enter 1 to encrypt a string");
        System.out.println("Enter 2 to show cylinders' state");
        System.out.println("Enter 3 to quit the program");
        Scanner input = new Scanner(System.in);

        while (true) {
            System.out.println("Please enter your command");
            int command = input.nextInt();
            switch (command) {
                case 1:
                    System.out.println("Please enter your string");
                    input.nextLine();  // Consume newline left-over
                    String stringToEncrypt = input.nextLine();
                    String result = rotorMachine.encryptString(stringToEncrypt);
                    System.out.println(result);
                    break;
                case 2:
                    rotorMachine.printCylindersState();
                    break;
                case 3:
                    return;
            }
        }
    }
}
class RotorMachine {
    Cylinder innerCylinder;
    Cylinder outerCylinder;

    public RotorMachine(int innerRotationRate, int outerRotationRate) {
        this.innerCylinder = new Cylinder(innerRotationRate);
        this.outerCylinder = new Cylinder(outerRotationRate);
    }

    public String encryptString(String stringToEncrypt) {
        StringBuilder sb = new StringBuilder();
        for (char c : stringToEncrypt.toCharArray()) {
            if (Character.isLetter(c)) {
                Character outerResult = this.outerCylinder.encryptCharacter(c);
                Character innerResult = this.innerCylinder.encryptCharacter(outerResult);
                sb.append(innerResult);
            } else {
                sb.append(c);
            }
        }
        return sb.toString();
    }

    public void printCylindersState() {
        System.out.println("Outer Cylinder State:");
        outerCylinder.printCylinderState();
        System.out.println("Inner Cylinder State:");
        innerCylinder.printCylinderState();
    }
}

class Cylinder {
    // x means 1 rotation after x calculations (x>=1)
    int rotationRate;
    int calculations;
    // x means input[n] -> output[n + x] (0<=x<=25)
    int rotation;
    String elements = "abcdefghijklmnopqrstuvwxyz";
    int roundSize = elements.length();

    List<Character> input = new ArrayList<>();
    List<Character> output = new ArrayList<>();

    public Cylinder(int rotation_rate) {
        // rotation rate is user-defined
        this.rotationRate = rotation_rate;
        this.calculations = 0;
        this.rotation = 0;

        for (char c : elements.toCharArray()) {
            this.input.add(c);
            this.output.add(c);
        }
        // initialize mappings randomly
        Collections.shuffle(this.input);
        Collections.shuffle(this.output);
    }

    public Character encryptCharacter(Character c) {
        c = Character.toLowerCase(c);
        Character result = this.output.get((this.input.indexOf(c) + this.rotation) % this.roundSize);
        this.calculations ++;
        this.rotation = this.calculations / this.rotationRate;
        return result;
    }

    public void printCylinderState() {
        for (int i = 0; i <= this.roundSize; i++) {
            System.out.println(this.input.get(i) + " -> "
                    + this.output.get((i + this.rotation) % this.roundSize));
        }
    }
}
